package com.dongdong.app;


import android.animation.ObjectAnimator;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;

import com.dongdong.zxingscan.CaptureActivity;
import com.dongdong.zxingscan.DecodeFormatManager;
import com.dongdong.zxingscan.ZoomListenerView;
import com.google.zxing.Result;

public class MainActivity extends CaptureActivity {

    private ImageView scanLine;
    private ObjectAnimator scanLineAnim;
    private ImageView showDecodeIV;
    private TextView toPhoto;
    private ZoomListenerView zoomView;


    @Override
    protected int getLayoutViewId() {
        return R.layout.activity_main;
    }

    @Override
    protected void doInitView() {
        scanLine = getId(R.id.scanLine);
        showDecodeIV = getId(R.id.showDecodeIV);
        toPhoto = getId(R.id.toPhoto);
        zoomView = getId(R.id.setZoomView);
    }

    @Override
    protected void doInitDate() {
        zoomView.setScaleListener(new ZoomListenerView.ScaleListener() {
            @Override
            public void onScaleFactor(float factor) {
                if (factor > 1) {
                    int v = (int) (getMaxZoom() * 0.02);
                    setThisZoom(
                     (getThisZoom() + v) > getMaxZoom() ? getMaxZoom() : getThisZoom() + v);
                    setZoom(getThisZoom());
                } else {
                    int v = (int) (getMaxZoom() * 0.02);
                    setThisZoom( (getThisZoom() - v) < 0 ? 0 : getThisZoom() - v);
                    setZoom(getThisZoom());
                }
            }
        });
        startLineAnim();
        showDecodeIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDecodeIV.setVisibility(View.GONE);
                restartPreviewAfterDelay(0L);
            }
        });

        toPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });
    }

    @Override
    protected void onResume() {
        if (scanLineAnim != null) {
            scanLineAnim.resume();
        }
        super.onResume();
    }

    @Override
    protected void onPause() {
        if (scanLineAnim != null) {
            scanLineAnim.pause();
        }
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        if (scanLineAnim != null) {
            scanLineAnim.end();
            scanLine.clearAnimation();
        }
        super.onDestroy();

    }

    @Override
    protected void onDecodeResult(Result rawResult, Bitmap barcode, float scaleFactor) {
        Log.d("扫码结果：", rawResult.getText());
        if (barcode != null) {
            showDecodeIV.setImageBitmap(barcode);
            showDecodeIV.setVisibility(View.VISIBLE);
        }

    }

    @Override
    protected DecodeFormatManager.TYPE getDecodeFormatType() {
        return DecodeFormatManager.TYPE.ONE_AND_QR;
    }


    private void startLineAnim() {
        scanLineAnim = ObjectAnimator.ofFloat(scanLine, "translationY", 0f, dip2px(232f),
                0f);
        scanLineAnim.setDuration(4000);
        scanLineAnim.setRepeatCount(Animation.INFINITE);
        scanLineAnim.start();

    }

    public int dip2px(float dpValue) {
        float scale = 0;
        if (0 == scale) {
            scale = getResources().getDisplayMetrics().density;
        }
        return (int) (dpValue * scale + 0.5f);
    }
}
