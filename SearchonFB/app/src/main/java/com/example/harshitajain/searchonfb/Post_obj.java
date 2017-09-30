package com.example.harshitajain.searchonfb;

/**
 * Created by harshitajain on 4/17/17.
 */

public class Post_obj {

    String msg = null;
    String created_time = null;

    Post_obj(String msg){
        this.msg = msg;
    }

    void add_created_time(String s){
        this.created_time =s;
        return;
    }

    String getCreated_time(){
        return this.created_time;
    }

    String getMsg(){
        return this.msg;
    }
}
