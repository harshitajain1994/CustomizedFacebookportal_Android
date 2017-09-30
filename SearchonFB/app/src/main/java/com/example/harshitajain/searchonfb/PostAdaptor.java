package com.example.harshitajain.searchonfb;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import com.squareup.picasso.Picasso;



import java.util.ArrayList;

/**
 * Created by harshitajain on 4/17/17.
 */

public class PostAdaptor extends ArrayAdapter {
    ArrayList<Post_obj> post_arr ;
    String user_name = null;
    String user_pic = null;

    public PostAdaptor(@NonNull Context context,  ArrayList<Post_obj> post_arr, String user_name, String user_pic) {
        super(context,R.layout.row_layout_posts, post_arr);
        this.post_arr =post_arr;
        this.user_name = user_name;
        this.user_pic = user_pic;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater1 = LayoutInflater.from(getContext());

        View customView = inflater1.inflate(R.layout.row_layout_posts, parent, false);
//        System.out.println("breaking1");
        Post_obj obj = post_arr.get(position);
//        String new_msg =
//        String new_created_time = ;
//        System.out.println("breaking2");
        TextView cur_msg = (TextView) customView.findViewById(R.id.message);
//        System.out.println("breaking3");
        TextView cur_created_time = (TextView) customView.findViewById(R.id.created_time);
        ((TextView) customView.findViewById(R.id.user_name)).setText(user_name);

        Picasso.with(this.getContext()).load(user_pic).into((ImageView) customView.findViewById(R.id.user_profile_pic));
//        System.out.println("breaking4");
        cur_msg.setText( obj.getMsg());
        cur_created_time.setText(obj.getCreated_time());

        return customView;
    }
}
