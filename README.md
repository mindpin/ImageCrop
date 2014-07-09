Android ImageCrop
===========
头像截取组件

## 如何引用此组件：
### 安装
```
git clone https://github.com/mindpin/ImageCrop
cd ImageCrop
mvn clean install
```

### maven引用
在maven项目，pom.xml添加以下依赖引用：

```
<dependency>
<groupId>com.mindpin.android.imagecrop</groupId>
<artifactId>imagecrop</artifactId>
<version>0.1.0-SNAPSHOT</version>
<type>apklib</type>
</dependency>
```

### android权限设置
```
<uses-permission android:name="android.permission.CAMERA" />
<uses-permission android:name="android.permission.FLASHLIGHT" />
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
<uses-feature android:name="android.hardware.camera" />
<uses-feature android:name="android.hardware.camera.autofocus" />
```

## 使用说明
### Activity继承ImageCropActivity，并实现相应函数
```
public class AppXxxActivity extends ImageCropActivity {
    public void process_croped_image(Bitmap image) {
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
```

### layout对组件设置点击事件： select_image
```
<Button
  android:layout_width="match_parent"
  android:layout_height="wrap_content"
  android:text="@string/crop_image"
  android:onClick="select_image"
  android:id="@+id/btn_crop_image"
/>
```

详细请参考示例

## 参考
* [lorensiuswlt/AndroidImageCrop][1]

[1]: https://github.com/lorensiuswlt/AndroidImageCrop
