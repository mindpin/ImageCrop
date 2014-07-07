package com.mindpin.android.imagecrop.samples;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;

import android.widget.ImageView;
import com.mindpin.android.imagecrop.ImageCropActivity;
import com.mindpin.android.imagecrop.samples.BuildConfig;
import com.mindpin.android.imagecrop.samples.R;

public class AppXxxActivity extends ImageCropActivity {
    private static final String TAG = "AppXxxActivity";
    ImageView iv_photo;
    Bitmap bitmap = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iv_photo = (ImageView) findViewById(R.id.iv_photo);
    }

    @Override
    public void process_croped_image(Bitmap image) {
        bitmap = image;
        iv_photo.setImageBitmap(image);
    }

    public int get_croped_image_width(){
        return 160;
    }

    public int get_croped_image_height(){
        return 90;
    }

    public int get_crop_frame_aspectX(){
        return 16;
    }

    public int get_crop_frame_aspectY(){
        return 9;
    }
}
