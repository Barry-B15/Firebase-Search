package com.example.barry.firebasesearch;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

//@@@@@@ to click 2 c bigger image @@@@@@
// get the click from the recycler to open big image when clicked
public class GalleryActivity extends AppCompatActivity {

    private static final String TAG = "GalleryActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        Log.d(TAG, "onCreate: started.");

        getIncomingIntent();
    }

    private void getIncomingIntent() {
        Log.d(TAG, "getIncomingIntent: checking for incoming intent");

        if (getIntent().hasExtra("image_url")
                && getIntent().hasExtra("image_name")
                && getIntent().hasExtra("image_status")) {
            Log.d(TAG, "getIncomingIntent: found intent extras.");

            String imageUrl = getIntent().getStringExtra("image_url");
            String imageName = getIntent().getStringExtra("image_name");
            String userStatus = getIntent().getStringExtra("image_status");

            // call the setImage widget
            setImage(imageUrl, imageName, userStatus);
        }
    }

    //4. set the extras to the widget
    // This is what shows on the page for users to see
    private void setImage(String imageUrl, String imageName, String userStatus) {
        Log.d(TAG, "setImage: setting the image and name to widgets.");

        // set user name
        TextView name = findViewById(R.id.image_description);
        Log.d(TAG, "setImage: image description set " + name);
        name.setText(imageName);

        // set user status
        TextView status = findViewById(R.id.profileStatus);
        Log.d(TAG, "setImage: profile status " + status);
        status.setText(userStatus);

        // set user image
        ImageView image = findViewById(R.id.imageV);
        Glide.with(this)
                .asBitmap()
                .load(imageUrl)
                .into(image);
    }

}
