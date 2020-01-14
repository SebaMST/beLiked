package com.example.abesg.beliked.Activities.Likes;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;

import com.example.abesg.beliked.Models.Comment;
import com.example.abesg.beliked.Models.Like;
import com.example.abesg.beliked.Models.Photo;
import com.example.abesg.beliked.R;
import com.example.abesg.beliked.Utils.FirebaseManager;
import com.example.abesg.beliked.Utils.Adapters.GridImageAdapter;
import com.example.abesg.beliked.Utils.GUI.BottomNavigationViewHelper;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by abesg on 27/12/2019.
 */

public class LikesActivity extends AppCompatActivity{
    private static final String TAG = "LikesActivity";

    private static final int ACTIVITY_NUM = 3;
    private static final int NUM_GRID_COLUMNS = 3;

    private GridView gridView;

    //firebase
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;
    private FirebaseManager mFirebaseMethods;

    private Context mContext = LikesActivity.this;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_likes);
        Log.d(TAG, "onCreate: started.");

        gridView = (GridView)findViewById(R.id.gridView);

        setupFirebaseAuth();
        setupBottomNavigationView();
        setupToolbar();
        setupGridView();
    }

    /**
     * BottomNavigationView setup
     */
    private void setupBottomNavigationView(){
        Log.d(TAG, "setupBottomNavigationView: setting up BottomNavigationView");
        BottomNavigationViewEx bottomNavigationViewEx = (BottomNavigationViewEx) findViewById(R.id.bottomNavViewBar);
        BottomNavigationViewHelper.setupBottomNavigationView(bottomNavigationViewEx);
        BottomNavigationViewHelper.enableNavigation(mContext, this,bottomNavigationViewEx);
        Menu menu = bottomNavigationViewEx.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);
    }

    private void setupGridView(){
        Log.d(TAG, "setupGridView: Setting up image grid.");

        final ArrayList<Photo> photos = new ArrayList<>();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        Query query = reference
                .child(getString(R.string.dbname_photos)).orderByChild("date_created");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for ( DataSnapshot singleSnapshot :  dataSnapshot.getChildren()){
                    DataSnapshot s2 = singleSnapshot.child("likes");
                    if(s2==null)
                        continue;
                    for(DataSnapshot shot : s2.getChildren())
                    {
                        Log.d(TAG, "onDataChange: "+shot);
                        if(shot.getValue(Like.class).getUser_id().equals(mAuth.getCurrentUser().getUid()))
                        {
                            Photo photo = new Photo();
                            Map<String, Object> objectMap = (HashMap<String, Object>) singleSnapshot.getValue();

                            try {
                                photo.setCaption(objectMap.get(getString(R.string.field_caption)).toString());
                                photo.setTags(objectMap.get(getString(R.string.field_tags)).toString());
                                photo.setPhoto_id(objectMap.get(getString(R.string.field_photo_id)).toString());
                                photo.setUser_id(objectMap.get(getString(R.string.field_user_id)).toString());
                                photo.setDate_created(objectMap.get(getString(R.string.field_date_created)).toString());
                                photo.setImage_path(objectMap.get(getString(R.string.field_image_path)).toString());

                                ArrayList<Comment> comments = new ArrayList<Comment>();
                                for (DataSnapshot dSnapshot : singleSnapshot
                                        .child(getString(R.string.field_comments)).getChildren()) {
                                    Comment comment = new Comment();
                                    comment.setUser_id(dSnapshot.getValue(Comment.class).getUser_id());
                                    comment.setComment(dSnapshot.getValue(Comment.class).getComment());
                                    comment.setDate_created(dSnapshot.getValue(Comment.class).getDate_created());
                                    comments.add(comment);
                                }

                                photo.setComments(comments);

                                List<Like> likesList = new ArrayList<Like>();
                                for (DataSnapshot dSnapshot : singleSnapshot
                                        .child(getString(R.string.field_likes)).getChildren()) {
                                    Like like = new Like();
                                    like.setUser_id(dSnapshot.getValue(Like.class).getUser_id());
                                    likesList.add(like);
                                }
                                photo.setLikes(likesList);
                                photos.add(photo);
                            }catch(NullPointerException e){
                                Log.e(TAG, "onDataChange: NullPointerException: " + e.getMessage() );
                            }
                        }
                    }

                }

                //setup our image grid
                int gridWidth = getResources().getDisplayMetrics().widthPixels;
                int imageWidth = gridWidth/NUM_GRID_COLUMNS;
                gridView.setColumnWidth(imageWidth);

                ArrayList<String> imgUrls = new ArrayList<String>();
                for(int i = 0; i < photos.size(); i++){
                    imgUrls.add(photos.get(i).getImage_path());
                }
                GridImageAdapter adapter = new GridImageAdapter(mContext,R.layout.layout_grid_imageview,
                        "", imgUrls);
                gridView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG, "onCancelled: query cancelled.");
            }
        });
    }

    private void setupToolbar(){
        ImageView backArrow = (ImageView)findViewById(R.id.backArrow);

        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: navigating to account settings.");
                finish();
            }
        });
    }

    private void setupFirebaseAuth(){
        Log.d(TAG, "setupFirebaseAuth: setting up firebase auth.");

        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();


                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };

    }


    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }


}