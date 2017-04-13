package com.example.lg.work6;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by LG on 2017-04-13.
 */

public class RestAdapter extends BaseAdapter {
    boolean check=false;
    ArrayList<Rest_Info> data = new ArrayList<>();
    Context context;
    Rest_Info rest_info;
    public RestAdapter(Context context, ArrayList<Rest_Info> data){
        this.context = context;
        this.data = data;
    }
    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item,null);
        }
        ImageView img = (ImageView)convertView.findViewById(R.id.list_image);
        TextView name = (TextView)convertView.findViewById(R.id.list_name);
        TextView phone = (TextView)convertView.findViewById(R.id.list_phone);
        CheckBox checkBox = (CheckBox)convertView.findViewById(R.id.checkbox);
        rest_info = data.get(position);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    rest_info.setDelete_check(1);
                }
                else{
                    rest_info.setDelete_check(0);
                }
            }
        });
        setimage(img,rest_info);
        name.setText(rest_info.getName());
        phone.setText(rest_info.getPhone());
        if(check){
            checkBox.setVisibility(View.VISIBLE);
        }
        else{
            checkBox.setVisibility(View.INVISIBLE);
        }
        return convertView;
    }
    public void setimage(ImageView imgview,Rest_Info rest_info){
        if(rest_info.getCate_no()==1)
            imgview.setImageResource(R.drawable.chicken);
        else if(rest_info.getCate_no()==2)
            imgview.setImageResource(R.drawable.pizza);
        else if(rest_info.getCate_no()==3)
            imgview.setImageResource(R.drawable.hamburger);
    }

    Comparator<Rest_Info> nameAsc = new Comparator<Rest_Info>() {
        @Override
        public int compare(Rest_Info o1, Rest_Info o2) {
            return o1.getName().compareTo(o2.getName());
        }
    };
    Comparator<Rest_Info> typeAsc = new Comparator<Rest_Info>() {
        @Override
        public int compare(Rest_Info o1, Rest_Info o2) {
            return o1.getCate_no()-o2.getCate_no();
        }
    };
    public void namesort(){
        Collections.sort(data,nameAsc);
        this.notifyDataSetChanged();
    }
    public void typesort(){
        Collections.sort(data,typeAsc);
        this.notifyDataSetChanged();
    }
    public void showcheckbox(boolean check){
        this.check = check;
        this.notifyDataSetChanged();
    }
}
