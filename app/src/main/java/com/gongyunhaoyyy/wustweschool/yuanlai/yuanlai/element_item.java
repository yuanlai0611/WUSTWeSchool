package com.gongyunhaoyyy.wustweschool.yuanlai.yuanlai;

import java.util.UUID;

/**
 * Created by 99460 on 2017/10/14.
 */

public class element_item {

    private String element_item;

    private UUID mId;

    public UUID getId(){
        return mId;
    }

    public String toString(){
        return element_item;
    }

    public void setElement_item(String element_item){
        this.element_item = element_item;
    }

    public element_item(String element_item){
        this.element_item = element_item;
    }

}
