/*
 * Copyright (C) 2008 ZXing authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.dongdong.zxingscan;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.DecodeHintType;
import com.google.zxing.ResultPointCallback;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;


/**
 * This thread does all the heavy lifting of decoding the images.
 *
 * @author dswitkin@google.com (Daniel Switkin)
 */
final class DecodeThread extends Thread {

    public static final String BARCODE_BITMAP = "barcode_bitmap";
    public static final String BARCODE_SCALED_FACTOR = "barcode_scaled_factor";

    private final CaptureActivity activity;
    private final Map<DecodeHintType, Object> hints;
    private Handler handler;
    private final CountDownLatch handlerInitLatch;
    private Collection<BarcodeFormat> decodeFormats;
    private DecodeFormatManager.TYPE formatType;

    DecodeThread(CaptureActivity activity,
                 Map<DecodeHintType, ?> baseHints,
                 String characterSet,
                 ResultPointCallback resultPointCallback) {
        this.activity = activity;
        formatType = activity.getDecodeFormatType();
        handlerInitLatch = new CountDownLatch(1);

        hints = new EnumMap<>(DecodeHintType.class);
        if (baseHints != null) {
            hints.putAll(baseHints);
        }

        // The prefs can't change while the thread is running, so pick them up once here.
        decodeFormats = new ArrayList<>();


        switch (formatType) {
                case ONE_CODE:
                decodeFormats.addAll(DecodeFormatManager.getOneCodeFormats());
                break;
            case QR_CODE:
                decodeFormats.addAll(DecodeFormatManager.getQrCodeFormats());
                break;
            case ONE_AND_QR:
                decodeFormats.addAll(DecodeFormatManager.getOneAndQrFormats());
                break;
            case DATA_MATRIX_CODE:
                decodeFormats.addAll(DecodeFormatManager.getDataMatrixFormats());
                break;
            case ALL:
                decodeFormats.addAll(DecodeFormatManager.getAllFormats());
                break;
            default:
                decodeFormats.addAll(DecodeFormatManager.getOneAndQrFormats());
                break;
        }

        hints.put(DecodeHintType.POSSIBLE_FORMATS, decodeFormats);

        if (characterSet != null) {
            hints.put(DecodeHintType.CHARACTER_SET, characterSet);
        }

        if (resultPointCallback != null) {
            hints.put(DecodeHintType.NEED_RESULT_POINT_CALLBACK, resultPointCallback);
        }
        Log.i("DecodeThread", "Hints: " + hints);
    }

    Handler getHandler() {
        try {
            handlerInitLatch.await();
        } catch (InterruptedException ie) {
            // continue?
        }
        return handler;
    }

    @Override
    public void run() {
        Looper.prepare();
        handler = new DecodeHandler(activity, hints);
        handlerInitLatch.countDown();
        Looper.loop();
    }

}
