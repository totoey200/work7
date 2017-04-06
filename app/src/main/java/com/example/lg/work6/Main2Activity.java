package com.example.lg.work6;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Main2Activity extends AppCompatActivity {
    EditText etname,ettel,etmenu1,etmenu2,etmenu3,etaddr;
    RadioButton radio1,radio2,radio3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        init();
    }

    void init(){
        etname = (EditText)findViewById(R.id.etname);
        ettel = (EditText)findViewById(R.id.ettel);
        etmenu1 = (EditText)findViewById(R.id.etmenu1);
        etmenu2 = (EditText)findViewById(R.id.etmenu2);
        etmenu3 = (EditText)findViewById(R.id.etmenu3);
        etaddr = (EditText)findViewById(R.id.etaddr);
        radio1 = (RadioButton)findViewById(R.id.radio1);
        radio2 = (RadioButton)findViewById(R.id.radio2);
        radio3 = (RadioButton)findViewById(R.id.radio3);
    }

    public void onClick(View v){
        if(v.getId()==R.id.btnCancel){
            Intent intent = new Intent();
            intent.putExtra("info",100);
            setResult(RESULT_CANCELED);
            finish();
        }
        if(v.getId()==R.id.btnAdd){
            Intent intent = new Intent();
            intent.putExtra("info",100);
            String[] menulist = {convert(etmenu1),convert(etmenu2),convert(etmenu3)};
            DateFormat df = new SimpleDateFormat("yyyyMMdd");
            String date = df.format(Calendar.getInstance().getTime());
            Rest_Info rest_info = new Rest_Info(convert(etname),convert(ettel),menulist,convert(etaddr),
                    date,cate_num(radio1,radio2,radio3));
            intent.putExtra("info",rest_info);
            setResult(RESULT_OK,intent);
            finish();
        }
    }
    public String convert(EditText et){
        return et.getText().toString();
    }
    public int cate_num(RadioButton r1,RadioButton r2, RadioButton r3){
        if(r1.isChecked()){
            return 1;
        }
        else if(r2.isChecked()){
            return 2;
        }
        else if(r3.isChecked()){
            return 3;
        }
        return 1;
    }
}
