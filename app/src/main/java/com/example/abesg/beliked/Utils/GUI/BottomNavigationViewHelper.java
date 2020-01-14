package com.example.abesg.beliked.Utils.GUI;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.util.Log;
import android.view.MenuItem;

import com.example.abesg.beliked.Activities.Home.HomeActivity;
import com.example.abesg.beliked.Activities.Likes.LikesActivity;
import com.example.abesg.beliked.Activities.Profile.ProfileActivity;
import com.example.abesg.beliked.R;
import com.example.abesg.beliked.Activities.Search.SearchActivity;
import com.example.abesg.beliked.Activities.Share.ShareActivity;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

public class BottomNavigationViewHelper {

    private static final String TAG = "BottomNavigationViewHel";

    public static void setupBottomNavigationView(BottomNavigationViewEx bottomNavigationViewEx){
        Log.d(TAG, "setupBottomNavigationView: Setting up BottomNavigationView");
        bottomNavigationViewEx.enableAnimation(false);
        bottomNavigationViewEx.enableItemShiftingMode(false);
        bottomNavigationViewEx.enableShiftingMode(false);
        bottomNavigationViewEx.setTextVisibility(false);
    }

    public static void enableNavigation(final Context context, final Activity callingActivity, BottomNavigationViewEx view){
        view.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent intent=null;
                switch (item.getItemId()){
                    case R.id.ic_home:
                        if(!context.getClass().equals(HomeActivity.class))
                        intent= new Intent(context, HomeActivity.class);//ACTIVITY_NUM = 0
                        break;

                    case R.id.ic_search:
                        if(!context.getClass().equals(SearchActivity.class))
                        intent  = new Intent(context, SearchActivity.class);//ACTIVITY_NUM = 1;
                        break;

                    case R.id.ic_share:
                        if(!context.getClass().equals(ShareActivity.class))
                        intent = new Intent(context, ShareActivity.class);//ACTIVITY_NUM = 2
                        break;

                    case R.id.ic_likes:
                        if(!context.getClass().equals(LikesActivity.class))
                        intent = new Intent(context, LikesActivity.class);//ACTIVITY_NUM = 3
                        break;

                    case R.id.ic_profile:
                        if(!context.getClass().equals(ProfileActivity.class))
                        intent = new Intent(context, ProfileActivity.class);//ACTIVITY_NUM = 4
                        break;
                }
                if(intent!=null) {
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    context.startActivity(intent);
                    callingActivity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                }

                return false;
            }
        });
    }
}