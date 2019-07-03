
##使用方式

###一  gradle直接引用

```
compile 'com.dongdong.zxingscan:ZxingScan:1.0.0'
```

### 二 通过module方式引用
##### 1. 复制ZxingScan目录到项目文件夹下，并在settings中声明。
##### 2. 在主项目中引用

```
compile project(':ZxingScan')
```

### 集成说明
1 创建Activity并继承CaptureActivity。
2 新建layout布局，布局中必须有SurfaceView并且id必须为preview_view

```
<SurfaceView
        android:id="@+id/preview_view"
        android:layout_width="match_parent"
        android:layout_height="match_parent"/>
```
3 在创建的Activity中实现getLayoutViewId()并返回layoutID


```
@Override
    protected int getLayoutViewId() {
        return R.layout.activity_main;
    }
```

### API方法说明
1、  onDecodeResult(Result rawResult, Bitmap barcode, float scaleFactor)
返回扫码结果信息。

2、 getDecodeFormatType()  设置扫码解析格式类型

```
ONE_CODE,  解析条码
QR_CODE, 解析二维码
ONE_AND_QR, 解析条码和二维码
DATA_MATRIX_CODE, 解析DATA_MATRIX码
ALL; 解析全部类型
```

3、 restartPreviewAfterDelay(long delayMS) 重新开启扫码delayMS为延迟开启时间。

4、 openGallery()  打开相册解析二维码图片
5、 setScanRect(Rect scanRect) 设置扫码时候的剪裁区域，可设置扫码框范围，不设置时候为全预览图识别。
6、 setTorch(boolean isLight) 闪光灯开启关闭
