package com.example.lg.work6;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ListViewCompat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView lv;
    TextView tv;
    ArrayList<Rest_Info> infolist = new ArrayList<>();
    ArrayAdapter<Rest_Info> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = (TextView)findViewById(R.id.tv);
        initlist();
    }
    void initlist(){
        lv = (ListView)findViewById(R.id.listview);
        adapter = new ArrayAdapter<Rest_Info>(this,android.R.layout.simple_list_item_1,
                infolist);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(),Main3Activity.class);
                Rest_Info rest_info = infolist.get(position);
                intent.putExtra("info",rest_info);
                startActivity(intent);
            }
        });
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("삭제확인")
                        .setMessage("선택한 맛집을 정말 삭제할거에요?")
                        .setNegativeButton("취소",null)
                        .setPositiveButton("삭제", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                infolist.remove(position);
                                adapter.notifyDataSetChanged();
                                tv.setText("맛집 리스트("+Integer.toString(infolist.size())+"개)");
                            }
                        }).show();
                return true;
            }
        });
    }
    public void onClick(View v){
        Intent intent = new Intent(this,Main2Activity.class);
        startActivityForResult(intent,100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 100){
            if(resultCode == RESULT_OK){
                Rest_Info rest_info = data.getParcelableExtra("info");
                infolist.add(rest_info);
                adapter.notifyDataSetChanged();
                tv.setText("맛집 리스트("+Integer.toString(infolist.size())+"개)");
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
