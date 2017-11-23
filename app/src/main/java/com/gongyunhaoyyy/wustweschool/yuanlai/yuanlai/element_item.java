package com.gongyunhaoyyy.wustweschool.yuanlai.yuanlai;

import android.graphics.Bitmap;

import java.util.UUID;

/**
 * Created by 99460 on 2017/10/14.
 */

public class element_item {

    private String author_name;

    private String text;

    private Bitmap bitmap;

    private String url;

    private String news_title;

    private String news_time;

    public void setAuthor_name(String author_name){
        this.author_name = author_name;
    }

    public String getAuthor_name(){
        return author_name;
    }

    public  void  setUrl(String url){
        this.url = url;
    }

    public String getUrl(){
        return url;
    }

    public void setImage(Bitmap bitmap){
        this.bitmap = bitmap;
    }

    public Bitmap getBitmap(){
        return bitmap;
    }

    public String getText(){
        return text;
    }

    public void setText(String text){
        this.text = text;
    }

    public void setNews_title(String news_title){
        this.news_title = news_title;
    }

    public String getNews_title() {
        return news_title;
    }

    public void setNews_time(String news_time){
        this.news_time = news_time;
    }

    public String getNews_time() {
        return news_time;
    }

    public element_item(String text){
        this.text = text;
    }

}
