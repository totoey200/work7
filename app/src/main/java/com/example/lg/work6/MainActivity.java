package com.example.lg.work6;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ListViewCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    ListView lv;
    Button delete,add;
    EditText search_bar;
    ArrayList<Rest_Info> infolist = new ArrayList<>();
    ArrayList<Rest_Info> showlist = new ArrayList<>();
    RestAdapter restAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("나의 맛집");
        initlist();
    }
    void initlist(){
        lv = (ListView)findViewById(R.id.listview);
        search_bar = (EditText)findViewById(R.id.search_bar);
        delete = (Button)findViewById(R.id.delete);
        add = (Button)findViewById(R.id.add);
        restAdapter = new RestAdapter(this,showlist);
        lv.setAdapter(restAdapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(),Main3Activity.class);
                Rest_Info rest_info = infolist.get(position);
                intent.putExtra("info",rest_info);
                startActivity(intent);
            }
        });
        search_bar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String search = s.toString();
                search_algo(search);
            }
        });
        add.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("데이터 추가");
                builder.setMessage("예제 데이터를 추가하시겠습니까?");
                builder.setNegativeButton("취소",null);
                builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        add_initdata();
                    }
                });
                builder.show();
                return true;
            }
        });
    }
    public void onClick(View v){
        if(v.getId() == R.id.add) {
            Intent intent = new Intent(this, Main2Activity.class);
            startActivityForResult(intent, 100);
        }
        else if(v.getId() == R.id.sort_name){
            restAdapter.namesort();
        }
        else if(v.getId() == R.id.sort_type){
            restAdapter.typesort();
        }
        else if(v.getId() == R.id.delete){
            if(delete.getText().toString().equals("선택")){
                delete.setText("삭제");
                lv.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
                restAdapter.showcheckbox(true);
            }
            else if(delete.getText().toString().equals("삭제")) {
                boolean changed = false;
                for(int i = infolist.size()-1;i>=0;i--){
                    if(infolist.get(i).getDelete_check()==1) {
                        changed = true;
                        break;
                    }
                }
                if(changed){
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("삭제확인")
                            .setIcon(R.drawable.potato)
                            .setMessage("선택한 맛집을 정말 삭제할거에요?")
                            .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    for(int i = infolist.size()-1;i>=0;i--){
                                        if(infolist.get(i).getDelete_check()==1) {
                                            infolist.get(i).setDelete_check(0);
                                            showlist.get(i).setDelete_check(0);
                                        }
                                    }
                                    delete.setText("선택");
                                    lv.setChoiceMode(ListView.CHOICE_MODE_NONE);
                                    restAdapter.showcheckbox(false);
                                }
                            })
                            .setPositiveButton("삭제", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    for(int i = infolist.size()-1;i>=0;i--){
                                        if(infolist.get(i).getDelete_check()==1) {
                                            infolist.remove(i);
                                        }
                                    }
                                    showlist.clear();
                                    showlist.addAll(infolist);
                                    delete.setText("선택");
                                    lv.setChoiceMode(ListView.CHOICE_MODE_NONE);
                                    restAdapter.showcheckbox(false);
                                    search_algo(Main2Activity.convert(search_bar));
                                }
                            }).show();
                }
                else {
                    delete.setText("선택");
                    lv.setChoiceMode(ListView.CHOICE_MODE_NONE);
                    restAdapter.showcheckbox(false);
                }
            }
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 100){
            if(resultCode == RESULT_OK){
                Rest_Info rest_info = data.getParcelableExtra("info");
                infolist.add(rest_info);
                showlist.add(rest_info);
                restAdapter.notifyDataSetChanged();
                search_bar.clearFocus();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    void add_initdata(){
        String[] menu = {"menu1(10000)","menu2(20000)","menu3(30000)"};
        DateFormat df = new SimpleDateFormat("yyyyMMdd");
        String date = df.format(Calendar.getInstance().getTime());
        Rest_Info newdata1 = new Rest_Info("chicken","01012341234",menu,"http://naver.com",date,1);
        Rest_Info newdata2 = new Rest_Info("pizza","01011111111",menu,"http://naver.com",date,2);
        Rest_Info newdata3 = new Rest_Info("hamburger","01022222222",menu,"http://naver.com",date,3);
        infolist.add(newdata1);
        showlist.add(newdata1);
        infolist.add(newdata2);
        showlist.add(newdata2);
        infolist.add(newdata3);
        showlist.add(newdata3);
        restAdapter.notifyDataSetChanged();
    }
    void search_algo(String search){
        search = search.toLowerCase();
        boolean check = false;
        if(search.length() > 0){
            int length = infolist.size();
            showlist.clear();
            for(Rest_Info rest_info:infolist){
                if(rest_info.getName().toLowerCase().contains(search)){
                    showlist.add(rest_info);
                }
            }
        }
        else{
            showlist.clear();
            showlist.addAll(infolist);
        }
        restAdapter.notifyDataSetChanged();
    }
}
