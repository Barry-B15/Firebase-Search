package com.example.barry.firebasesearch;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.like.LikeButton;
import com.like.OnAnimationEndListener;
import com.like.OnLikeListener;

/**
 * https://www.youtube.com/watch?v=b_tz8kbFUsU&t=603s
 * 1. create new project
 * 2. create the ui in main.xml
 * 3. go to style.xml, edit to NoActionBar
 * 4. Go to build.gradle, add recyclerView and sync
 * 5. add the recycler view to the ui
 * 6. Declare the views here and initialize them
 * 7. Add firebase to the project. Tools > firebase >  real time db >save and retrieve data >
 * connect to firebase
 * add Realtime db to project
 * <p>
 * ################################ HANDLE JSON #######################
 * Click on the 3 dots on the right top of the app in FireBase
 * click: View SDK setup instruction (read)
 * or
 * click: settings
 * <p>
 * download google json servives to the pc > go to pc downloads, copy the file to
 * _ on the left panel > change to Project > app > highlight app > paste
 * &&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&
 * <p>
 * 8. create the user_node in fb with prop: image, name, and status.
 * see video at 10 - 13 mins
 * e.g. Users
 * 01
 * image: "click on the image in fb, copy the link here in db section"
 * name:
 * status:
 * For better ui the circular image ui is recommended
 *
 * 9. Add fb ui dependences, google firebaseui :
     * We need the fb ui toretrieve the list of the data
     * https://github.com/firebase/FirebaseUI-Android
     * copy and paste this in the gradle.build
     * // FirebaseUI for Firebase Realtime Database
     * implementation 'com.firebaseui:firebase-ui-database:3.1.2'
     * make sure to correct the version or it won't work
     * we are using firebase_database 11.0.4 so scroll down on the github page and see the
     * version that matches, adjust accordingly (2.3.0 for this work)
     * Sync
 *
 * 10. Create a new layout "list_layout" with 3 elements (1 Imageview, 2 textViews for name and status)
 *
 * 11. Create a Users class to enable us get data from the db
 * add 3 strings to it since we have 3 (image, status, name) in our database
 * they must match exactly as the keys in the db
 * they are case sensitive so be careful with the letters
 *
 * 12. Inside here create a public class UsersViewHolder to extend RecyclerView.ViewHolder
 *
 * 13. Add onClick listener to the onCreate() method and call firebaseUserSearch
 *
 * 14. create the firebaseUserSearch() method
 * 15. create a new method "setDetails" in our UsersView Holder to
 * set values to the list_layout that we created
 *
 * 15.3 Use glide , google glide, from the page copy and paste
 * in the gradle.project > repository
 * mavenCentral()
 * google()
 *
 * in gradle.app > dependences
 * implementation 'com.github.bumptech.glide:glide:4.4.0'
 * annotationProcessor 'com.github.bumptech.glide:compiler:4.4.0'
 * for the to work use lower version, change 4.4.0 to 4.0.0
 *
 * 16. set the adapter at the end of firebaseUserSearch
 *
 * 17. For the search, set a query for when the user push the search button
 * put the query in firebaseUserSearch: Query firebaseSearchQuery....
 *
 * &&&&&&&&& LikeBtn &&&&&&&&&&&&&&&&&&&&&&
 * 1. go to the ff github page Gradle Dependency
 *          https://github.com/jd-alexander/LikeButton
     * copy and paste in the Project build.gradle as follows:
     * allprojects {
     * repository {
     * maven { url "https://jitpack.io" }
     * }
     * }
 *
 * Then go back copy and paste the ff in the app gradle.build > dependencies
 * compile 'com.github.jd-alexander:LikeButton:0.2.3'
 * SYNC
 * <p>
 * 2. Go back, under "basic Usage " copy the xml data and paste in the xml file where you want the icons
 * Edit the id as needed for each icon
 * <p>
 * 3. declare and init the buttons in Main.java
 * <p>
 * 4. Go to Users,
 * i. add "like" to the String on top
 * ii. add "like" to the Users constructor
 * iii. add the like getters/setters
 * <p>
 * 5. Have the MainActivity implement OnLikeListener, OnAnimationListener
 * - this will give an error
 * - alt + enter and impement method
 * - This will auto create/Override Liked, UnLiked, and OnAnimationEnd methods
 * - add toast there
 * 6. Add the view to setDetails
 * and set the default like
 * 7. add the model to populateViewHolder() method under ViewHolder.setDetails()
 * 8. RUN
 * <p>
 * //@@@@@@ To click 2 c bigger image @@@@@@
 * 1. create a new "Gallery activity "
 * 2. Edit the xml file as necessary
 * 3. Add the getters and setters in Users() class
 * 4. add the views to the setDetails()
 *      also add onClick listener to setDetails()
 * 5. call the model in the Adapter under populateViewHolder()
 * 6. Code GalleryActivity class to get incoming intent
 */

//for like button, have this class implement onAnimationEndListener, onLikeListener
public class MainActivity extends AppCompatActivity implements
        OnAnimationEndListener, OnLikeListener {

    private static final String TAG = "MainActivity";

    private EditText mSearchField;
    private ImageButton mSearchButton;

    private RecyclerView mResultList;

    //14.0 db ref
    private DatabaseReference mUserDatabase;

    //@GlideModule
    //public final class MyAppGlideModule extends AppGlideModule {}

    //&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&
    // declare the like button
    LikeButton heartButton;
    //&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //14.1 assign the db ref            "Users"(must be same as our node in fb db)
        mUserDatabase = FirebaseDatabase.getInstance().getReference("Users");

        mSearchField = (EditText) findViewById(R.id.etSearchView);
        mSearchButton = (ImageButton) findViewById(R.id.searchBtn);
        mResultList = (RecyclerView) findViewById(R.id.result_list);

        //&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&
        // init the like button
        heartButton = (LikeButton) findViewById(R.id.heart_button);

        // set the listener to this activity
        //heartButton.setOnLikeListener(this);
        //heartButton.setOnAnimationEndListener(this);

        // default state of the button
        // heartButton.setLiked(false);
        //&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&

        //15.4 set the result
        mResultList.setHasFixedSize(true);
        mResultList.setLayoutManager(new LinearLayoutManager(this)); // set a new layout mgr
        // next, go to firebaseUserSearch, set the adapter 16.0

        // 13.1 Add onClickListener
        mSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //17.1 create a string for search to start with
                String searchText = mSearchField.getText().toString();

                // 13.2 call the firebaseUserSearch()
                // 17.2 pass the string just created
                firebaseUserSearch(searchText); // alt+enter > create the method
            }
        });

        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    // create the firebaseUserSearch method and def the recyclerAdapter
    // 13.3
    // 17.3 pass the string from above 17.1
    private void firebaseUserSearch(String searchText) {

        Toast.makeText(MainActivity.this, "Started search", Toast.LENGTH_SHORT).show();

        //17.0 Add query for name to search names
        Query firebaseSearchQuery = mUserDatabase.orderByChild("name") // search the name
                .startAt(searchText) // starting at (provide the text to start with, create a string in the onClick listener
                .endAt(searchText + "\uf8ff"); // end at the text we typed in + a unicode provided by fb for search
        // more info on the unicode at start search text, end search text plus unicode
        // it checks if any name in our db matches with the typed in name
        // the unicode ensures to check and match if any name or letter in the db matches the query


        //13.4 create the Firebase recycler adapter  and pass in the model class and the view holder
        FirebaseRecyclerAdapter<Users, UsersViewHolder> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<Users, UsersViewHolder>(
                        Users.class, // pass in the class we created
                        R.layout.list_layout, // the layout holding the list
                        UsersViewHolder.class, // pass in the ViewHolder
                        mUserDatabase  //14.2 the db reference, create this at the top
                ) {
                    @Override
                    protected void populateViewHolder(UsersViewHolder viewHolder,
                                                      Users model, int position) {
                        //15.2 call setDetails() passing in the models to get all items from db
                        viewHolder.setDetails(getApplicationContext(),
                                model.getName(),
                                model.getStatus(),
                                model.getImage(),
                                model.getLike(),    // &&&&&&&&& LikeBtn &&&&&&&&&&&&&&&&&&&&&&
                                model.getImageV()); //@@@@@@ to click 2 c bigger image @@@@@@
                    }
                };

        // 16.0 set the adapter
        mResultList.setAdapter(firebaseRecyclerAdapter);
        // now make sure to set rules in Firebase appropriately
        // Run
    }

    //&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&
    @Override
    public void liked(LikeButton likeButton) {

        Toast.makeText(this, "Liked!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void unLiked(LikeButton likeButton) {

        Toast.makeText(this, "Disliked!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAnimationEnd(LikeButton likeButton) {

        Log.d(TAG, "onAnimationEnd for %s: " + likeButton);
    }
    //&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&

    //12.1.create a public class UsersViewHolder to extend RecyclerView.ViewHolder
    // the view holder for our adapter
    //12.2. has an error, fix it by creating a constructor for the new class
    //12.3. define a view in the class, and assign it in the constructor
    public static class UsersViewHolder extends RecyclerView.ViewHolder {

        // def the view
        View mView;

        // create the constructor
        public UsersViewHolder(View itemView) {
            super(itemView);

            // assign the view
            mView = itemView;
        }

        // 15. create setDetails and def the fields we have in the layout in it
        public void setDetails(final Context ctx,
                               final String userName,
                               final String userStatus,
                               final String userImage,
                               String userLiked, //$$$$$$$$$$$$$$$$$$$$ for Like Btn
                               final String userImageV //@@@@@@ to click 2 c bigger image @@@@@@
        ) {
            //15.1
            final TextView user_name = (TextView) mView.findViewById(R.id.profileName);
            TextView user_status = (TextView) mView.findViewById(R.id.profileStatus);
            ImageView user_image = (ImageView) mView.findViewById(R.id.profileImage);

            // &&&&&&&&& LikeBtn &&&&&&&&&&&&&&&&&&&&&&
            LikeButton user_liked = (LikeButton) mView.findViewById(R.id.heart_button);

            //@@@@@@ CLICK TO SEE BIGGER IMAGE  @@@@@@
            final ImageView bigImage = (ImageView) mView.findViewById(R.id.imageV);
            // now go call this method in populateViewHolder

            //15.3 set the values to the fields passing in models in our populate ViewHolder()
            user_name.setText(userName);
            user_status.setText(userStatus);
            user_liked.setLiked(false);  // &&&&&&&&& LikeBtn &&&&&&&&&&&&&&&&&&&&&&

            //@@@@@@ to click 2 c bigger image @@@@@@
            //ii. add a request option. This sets the images to small and beautiful
            // It is important to do this otherwise our image will come out weird
            RequestOptions requestOptions = new RequestOptions()
                    .placeholder(R.drawable.ic_launcher_background); // ref a dummy image
            // now apply this requestOptions in Glide below

            // attach an onClick listener to the image
            mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d(TAG, "onClick: clicked on:" + user_name);
                    Toast.makeText(ctx, "You clicked: " + userName, Toast.LENGTH_SHORT).show();

                    // Add onclick listener to listen for click on images to open larger images
                    Intent galleryIntent = new Intent(ctx, GalleryActivity.class);
                    galleryIntent.putExtra("image_url", userImage);
                    galleryIntent.putExtra("image_name", userName);
                    galleryIntent.putExtra("image_status", userStatus);
                    ctx.startActivity(galleryIntent);
                    // but here we're using mContext.startActivity() cos we're in an adapter class which
                    // doesn't know the context of where we're trying to start the activity from,
                    // so we need to ref the context 1st before calling startActivity()
                }
            });

            // for the image we use the glide lib, google for link, follow to github,
            // copy the required and paste in gradle.build, use appropriate version, better lower
            Glide.with(ctx).load(userImage).apply(requestOptions).into(user_image);
            // load(the url to load) .into(where to load url)
            // now go set the results in onCreate() 15.4
        }
    }

    /*private EditText mSearchField;
    private ImageButton mSearchBtn;

    private RecyclerView mResultList;

    private DatabaseReference mUserDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mUserDatabase = FirebaseDatabase.getInstance().getReference("Users");


        mSearchField = (EditText) findViewById(R.id.search_field);
        mSearchBtn = (ImageButton) findViewById(R.id.search_btn);

        mResultList = (RecyclerView) findViewById(R.id.result_list);
        mResultList.setHasFixedSize(true);
        mResultList.setLayoutManager(new LinearLayoutManager(this));

        mSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String searchText = mSearchField.getText().toString();

                firebaseUserSearch(searchText);

            }
        });

    }

    private void firebaseUserSearch(String searchText) {

        Toast.makeText(MainActivity.this, "Started Search", Toast.LENGTH_LONG).show();

        Query firebaseSearchQuery = mUserDatabase.orderByChild("name").startAt(searchText).endAt(searchText + "\uf8ff");

        FirebaseRecyclerAdapter<Users, UsersViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Users, UsersViewHolder>(

                Users.class,
                R.layout.list_layout,
                UsersViewHolder.class,
                firebaseSearchQuery

        ) {
            @Override
            protected void populateViewHolder(UsersViewHolder viewHolder, Users model, int position) {


                viewHolder.setDetails(getApplicationContext(), model.getName(), model.getStatus(), model.getImage());

            }
        };

        mResultList.setAdapter(firebaseRecyclerAdapter);

    }


    // View Holder Class

    public static class UsersViewHolder extends RecyclerView.ViewHolder {

        View mView;

        public UsersViewHolder(View itemView) {
            super(itemView);

            mView = itemView;

        }

        public void setDetails(Context ctx, String userName, String userStatus, String userImage){

            TextView user_name = (TextView) mView.findViewById(R.id.name_text);
            TextView user_status = (TextView) mView.findViewById(R.id.status_text);
            ImageView user_image = (ImageView) mView.findViewById(R.id.profile_image);


            user_name.setText(userName);
            user_status.setText(userStatus);

            Glide.with(ctx).load(userImage).into(user_image);

        }

    }
    */
}
