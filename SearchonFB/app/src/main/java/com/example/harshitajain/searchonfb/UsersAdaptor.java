package com.example.harshitajain.searchonfb;

import android.content.Context;
import android.support.annotation.BoolRes;
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
import java.util.List;

import com.squareup.picasso.Picasso;

/**
 * Created by harshitajain on 4/15/17.
 */

public class UsersAdaptor extends ArrayAdapter {

    ArrayList<String> id_arr;
    ArrayList<String> name_arr;
    ArrayList<String> pic_url_arr;
    int cur_position;
    String tab = new String();

    public UsersAdaptor(@NonNull Context context, ArrayList<String> id_arr,ArrayList<String> name_arr, ArrayList<String> pic_url_arr, String tab) {
        super(context,R.layout.row_layout, id_arr);
        //,
        this.id_arr =id_arr;
        this.name_arr = name_arr;
        this.pic_url_arr = pic_url_arr;
        this.tab = tab;

    }

//    public UsersAdaptor(@NonNull Context context, ArrayList<String> id_arr) {
//        super(context,R.layout.row_layout ,id_arr);
//    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View customView = inflater.inflate(R.layout.row_layout, parent, false);

        String new_name = name_arr.get(position);
        String new_id = id_arr.get(position);
        String new_pic_url = pic_url_arr.get(position);

        TextView cur_name = (TextView) customView.findViewById(R.id.name);
        ImageView cur_img = (ImageView) customView.findViewById(R.id.profile_pic);

        // setting tag to details image
        ImageView cur_id = (ImageView) customView.findViewById(R.id.details);
        String tag = new String(new_id+", ; ,"+new_name+", ; ," + new_pic_url + ", ; ," + this.tab);
        //System.out.println("new string is" + tag);
        cur_id.setTag( tag);
        ImageView cur_fav = (ImageView) customView.findViewById(R.id.fav_star);



        // hcek in favs , if we have the same d=id o rnot
        Boolean present = this.checkFavorites(getContext(), new_id);
        if(present){
            cur_fav.setImageResource(R.drawable.favorites_on);
        }
        else {
            cur_fav.setImageResource(R.drawable.favorites_off);
        }


        cur_name.setText(new_name);
        //cur_id.setText(new_id);
        //cur_img.setImageResource();
        Picasso.with(this.getContext()).load(new_pic_url).into((ImageView) cur_img);
        this.cur_position = position;
        notifyDataSetChanged();
        return customView;
    }

    public boolean checkFavorites(Context context, String id){
        FavsInterface checker1 = new SharedPreference();
        FavsInterface checker2 = new SharedPreferenceGroups();
        FavsInterface checker3 = new SharedPreferencePages();
        FavsInterface checker4 = new SharedPreferencePlaces();
        FavsInterface checker5 = new SharedPreferenceEvents();
        boolean var1 = checker_support(checker1, context, id);
        boolean var2 = checker_support(checker2, context, id);
        boolean var3 = checker_support(checker3, context, id);
        boolean var4 = checker_support(checker4, context, id);
        boolean var5 = checker_support(checker5, context, id);

        if(var1 || var2|| var3 || var4 ||var5){
            return true;
        }
        return false;


    }

    public boolean checker_support(FavsInterface checker, Context context, String id){
        List<User_obj> favorites = checker.getFavorites(context);
        if(favorites != null){
            for(User_obj user_obj : favorites){
                if(user_obj.getId().equals(id)){
                    return true;
                }
            }
        }

        return false;
    }


}
