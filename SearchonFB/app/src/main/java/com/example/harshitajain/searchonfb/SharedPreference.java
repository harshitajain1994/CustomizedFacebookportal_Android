package com.example.harshitajain.searchonfb;

/**
 * Created by harshitajain on 4/18/17.
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import com.google.gson.Gson;

public class SharedPreference implements FavsInterface{

        public static final String PREFS_NAME = "User_obj_APP";
        public static final String FAVORITES = "User_obj_Favorite1_user";


        public SharedPreference() {
            super();
        }

        // This four methods are used for maintaining favorites.
        public void saveFavorites(Context context, List<User_obj> favorites) {
            SharedPreferences settings;

            settings = context.getSharedPreferences(PREFS_NAME,
                    Context.MODE_PRIVATE);
            Editor editor = settings.edit();

            Gson gson = new Gson();
            String jsonFavorites = gson.toJson(favorites);

            editor.putString(FAVORITES, jsonFavorites);
            //System.out.println("Saved"+jsonFavorites);
            editor.commit();
        }

        public void addFavorite(Context context, User_obj User_obj) {
            //System.out.println("addingthis to favs" + User_obj.getId());
            List<User_obj> favorites = getFavorites(context);
            if (favorites == null)
                favorites = new ArrayList<User_obj>();
            favorites.add(User_obj);
            saveFavorites(context, favorites);
        }

        public void removeFavorite(Context context, User_obj User_obj) {
            //System.out.println("removing to favs" + User_obj.getId());
            ArrayList<User_obj> favorites = getFavorites(context);
            if (favorites != null) {
                favorites.remove(User_obj);
                saveFavorites(context, favorites);
            }

        }

        public ArrayList<User_obj> getFavorites(Context context) {
            SharedPreferences settings;
            List<User_obj> favorites;

            settings = context.getSharedPreferences(PREFS_NAME,
                    Context.MODE_PRIVATE);

            if (settings.contains(FAVORITES)) {

                String jsonFavorites = settings.getString(FAVORITES, null);
                Gson gson = new Gson();
                User_obj[] favoriteItems = gson.fromJson(jsonFavorites,
                        User_obj[].class);

                favorites = Arrays.asList(favoriteItems);
                favorites = new ArrayList<User_obj>(favorites);
            } else
                return null;
            //System.out.println("Favorites Stored"+favorites);
            return (ArrayList<User_obj>) favorites;
        }

    public String getFavoritesString(Context context) {
        SharedPreferences settings;
        String jsonFavorites;

        settings = context.getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);

        if (settings.contains(FAVORITES)) {
             jsonFavorites = settings.getString(FAVORITES, null);
        } else
            return null;
        jsonFavorites = "{\"data\":" + jsonFavorites + "}";
        //System.out.println("Favorites Stored"+jsonFavorites);
        return jsonFavorites;
    }


}
