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
请参考示例

## 注意事项
1. 2.2、2.3虚拟机测试《拍照裁剪》拍照时会出错，2.3真机正常。

## 参考
* [lorensiuswlt/AndroidImageCrop][1]

[1]: https://github.com/lorensiuswlt/AndroidImageCrop
