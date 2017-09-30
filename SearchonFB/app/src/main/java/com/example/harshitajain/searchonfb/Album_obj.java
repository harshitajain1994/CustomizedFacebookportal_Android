package com.example.harshitajain.searchonfb;

import java.util.ArrayList;

/**
 * Created by harshitajain on 4/17/17.
 */

public class Album_obj {


        String name;
        ArrayList<String> album_pic_urls;

    {
        album_pic_urls = new ArrayList<String>();
    }

    Album_obj(String name){
            this.name = name;
        }

        void add_pic_to_album(String s){
            this.album_pic_urls.add(s);
            //System.out.println("addin gthe data" + s);
            return;
        }

        ArrayList<String> getAlbum_pic_urls(){
            return this.album_pic_urls;
        }

        String getName(){
            return this.name;
        }


}
