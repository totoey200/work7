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

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView lv;
    Button delete;
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
                if(search.length() > 0){
                    int length = infolist.size();
                    showlist.clear();
                    for(int i=0;i<length;i++){
                        if(infolist.get(i).getName().contains(search)){
                            showlist.add(infolist.get(i));
                        }
                    }
                }
                else{
                    showlist.clear();
                    showlist.addAll(infolist);
                }
                restAdapter.notifyDataSetChanged();
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
                                            showlist.remove(i);
                                        }
                                    }
                                    delete.setText("선택");
                                    lv.setChoiceMode(ListView.CHOICE_MODE_NONE);
                                    restAdapter.showcheckbox(false);
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
}
