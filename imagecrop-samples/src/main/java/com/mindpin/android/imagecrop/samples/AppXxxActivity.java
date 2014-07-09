package com.mindpin.android.imagecrop.samples;

import android.graphics.Bitmap;
import android.os.Bundle;

import android.widget.ImageView;
import com.mindpin.android.imagecrop.ImageCropActivity;

public class AppXxxActivity extends ImageCropActivity {
    ImageView iv_photo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_xxx);
        iv_photo = (ImageView) findViewById(R.id.iv_photo);
    }

    public void process_croped_image(Bitmap image) {
        iv_photo.setImageBitmap(image);
    }

    public int get_croped_image_width() {
        return 160;
    }

    public int get_croped_image_height() {
        return 90;
    }

    public int get_crop_frame_aspectX() {
        return 16;
    }

    public int get_crop_frame_aspectY() {
        return 9;
    }
}
