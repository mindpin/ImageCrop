package com.mindpin.android.imagecrop;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dd on 14-7-7.
 */
public abstract class ImageCropActivity extends Activity {
    private Uri mImageCaptureUri;

    private static final int PICK_FROM_CAMERA = 1;
    private static final int CROP_FROM_CAMERA = 2;
    private static final int PICK_FROM_FILE = 3;
    AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final String[] items = new String[]{"从摄像头拍摄一张图片", "从本地选取一张图片"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.select_dialog_item, items);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("选择图片");
        builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) { //pick from camera
                if (item == 0) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    File file = new File(Environment.getExternalStorageDirectory() + "/tmp/");
                    file.mkdirs();
                    mImageCaptureUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory() + "/tmp/",
                            "tmp_avatar_" + String.valueOf(System.currentTimeMillis())));
                    intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, mImageCaptureUri);
                    try {
                        intent.putExtra("return-data", true);

                        startActivityForResult(intent, PICK_FROM_CAMERA);
                    } catch (ActivityNotFoundException e) {
                        e.printStackTrace();
                    }
                } else { //pick from file
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent, "Complete action using"), PICK_FROM_FILE);
                }
            }
        });
        dialog = builder.create();
    }

    // 点击选择图片按钮时的事件处理方法，需要组件实现这个方法
    //  <Button
    //      android:onClick="select_image"
    //      android:text="选择需要裁切的图片"
    //      />
    public void select_image(View view) {
        dialog.show();
    }

    // 使用组件时，需要实现这个方法
    // 这个方法的运行时机是
    // 图片裁切好后，组件会调用这个方法
    // 方便使用组件时，自定义增加裁切好后的逻辑
    public abstract void process_croped_image(Bitmap image);

    // 使用组件时，需要实现这个方法
    // 这个方法的运行时机是
    // 设置裁切后的图片的宽度（单位：像素）
    public abstract int get_croped_image_width();

    // 使用组件时，需要实现这个方法
    // 这个方法的运行时机是
    // 设置裁切后的图片的高度（单位：像素）
    public abstract int get_croped_image_height();

    // 使用组件时，需要实现这两个方法
    // 这个方法的运行时机是
    // 设置裁切框的宽高比
    public abstract int get_crop_frame_aspectX();

    public abstract int get_crop_frame_aspectY();


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) return;

        switch (requestCode) {
            case PICK_FROM_CAMERA:
                doCrop();
                break;

            case PICK_FROM_FILE:
                mImageCaptureUri = data.getData();
                doCrop();
                break;

            case CROP_FROM_CAMERA:
                Bundle extras = data.getExtras();
                if (extras != null) {
                    Bitmap photo = extras.getParcelable("data");
                    process_croped_image(photo);
                }
                File f = new File(mImageCaptureUri.getPath());
                if (f.exists()) f.delete();
                break;
        }
    }

    private void doCrop() {
        final ArrayList<CropOption> cropOptions = new ArrayList<CropOption>();
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setType("image/*");
        List<ResolveInfo> list = getPackageManager().queryIntentActivities(intent, 0);
        int size = list.size();
        if (size == 0) {
            Toast.makeText(this, "Can not find image crop app", Toast.LENGTH_SHORT).show();
            return;
        } else {
            intent.setData(mImageCaptureUri);
            intent.putExtra("outputX", get_croped_image_width());
            intent.putExtra("outputY", get_croped_image_height());
            intent.putExtra("aspectX", get_crop_frame_aspectX());
            intent.putExtra("aspectY", get_crop_frame_aspectY());
            intent.putExtra("scale", true);
            intent.putExtra("return-data", true);
            if (size == 1) {
                Intent i = new Intent(intent);
                ResolveInfo res = list.get(0);
                i.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
                startActivityForResult(i, CROP_FROM_CAMERA);
            } else {
                for (ResolveInfo res : list) {
                    final CropOption co = new CropOption();
                    co.title = getPackageManager().getApplicationLabel(res.activityInfo.applicationInfo);
                    co.icon = getPackageManager().getApplicationIcon(res.activityInfo.applicationInfo);
                    co.appIntent = new Intent(intent);
                    co.appIntent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
                    cropOptions.add(co);
                }
                CropOptionAdapter adapter = new CropOptionAdapter(getApplicationContext(), cropOptions);
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Choose Crop App");
                builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        startActivityForResult(cropOptions.get(item).appIntent, CROP_FROM_CAMERA);
                    }
                });
                builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        if (mImageCaptureUri != null) {
                            getContentResolver().delete(mImageCaptureUri, null, null);
                            mImageCaptureUri = null;
                        }
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        }
    }
}
