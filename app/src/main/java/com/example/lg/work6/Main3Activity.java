package com.example.lg.work6;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class Main3Activity extends AppCompatActivity {
    TextView txtname,etmenu1,etmenu2,etmenu3,tvtel,tvURL,tvRegdate;
    ImageView imgno;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        init();
    }

    void init(){
        imgno = (ImageView)findViewById(R.id.imgno);
        txtname = (TextView)findViewById(R.id.txtname);
        etmenu1 = (TextView)findViewById(R.id.etmenu1);
        etmenu2 = (TextView)findViewById(R.id.etmenu2);
        etmenu3 = (TextView)findViewById(R.id.etmenu3);
        tvtel = (TextView)findViewById(R.id.tvTel);
        tvURL = (TextView)findViewById(R.id.tvURL);
        tvRegdate = (TextView)findViewById(R.id.tvRegdate);

        Intent intent = getIntent();
        Rest_Info rest_info = intent.getParcelableExtra("info");
        setpicture(rest_info.getCate_no());
        txtname.setText(rest_info.getName());
        etmenu1.setText(rest_info.getMenu()[0]);
        etmenu2.setText(rest_info.getMenu()[1]);
        etmenu3.setText(rest_info.getMenu()[2]);
        tvtel.setText(rest_info.getPhone());
        tvURL.setText(rest_info.getUrl());
        tvRegdate.setText(rest_info.getReg_date());
    }

    public void onClick(View v){
        if(v.getId()==R.id.btnback){
            finish();
        }
        else if(v.getId()==R.id.imageView2){
            startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:/"+ tvtel.getText().toString())));
        }
        else if(v.getId()==R.id.imageView3){
            startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse(tvURL.getText().toString())));
        }
    }
    void setpicture(int a){
        if(a==1){
            imgno.setImageResource(R.drawable.chicken);
        }
        else if(a==2){
            imgno.setImageResource(R.drawable.pizza);
        }
        else if(a==3){
            imgno.setImageResource(R.drawable.hamburger);
        }
    }
}
