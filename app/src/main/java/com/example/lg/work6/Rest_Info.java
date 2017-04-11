package com.example.lg.work6;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by LG on 2017-04-06.
 */

public class Rest_Info implements Parcelable{
    private String name;
    private String phone;
    private String[] menu = new String[3];
    private String url;
    private String reg_date;
    private int cate_no;
    public Rest_Info(String name, String phone, String[] menu, String url, String reg_date, int cate_no){
        this.name = name;
        this.phone = phone;
        this.menu = menu;
        this.url = url;
        this.reg_date = reg_date;
        this.cate_no = cate_no;
    }

    protected Rest_Info(Parcel in) {
        name = in.readString();
        phone = in.readString();
        menu = in.createStringArray();
        url = in.readString();
        reg_date = in.readString();
        cate_no = in.readInt();
    }

    public static final Creator<Rest_Info> CREATOR = new Creator<Rest_Info>() {
        @Override
        public Rest_Info createFromParcel(Parcel in) {
            return new Rest_Info(in);
        }

        @Override
        public Rest_Info[] newArray(int size) {
            return new Rest_Info[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(phone);
        dest.writeStringArray(menu);
        dest.writeString(url);
        dest.writeString(reg_date);
        dest.writeInt(cate_no);
    }

    @Override
    public String toString() {
        return this.name.toString();
    }
    public String getName(){
        return this.name;
    }
    public String getPhone(){
        return this.phone;
    }
    public String[] getMenu(){
        return this.menu;
    }
    public String getUrl(){
        return this.url;
    }
    public String getReg_date(){
        return this.reg_date;
    }
    public int getCate_no(){
        return this.cate_no;
    }
}
