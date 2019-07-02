/*
 * Copyright (C) 2010 ZXing authors
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

import android.content.Intent;
import android.net.Uri;

import com.google.zxing.BarcodeFormat;

import java.util.Arrays;
import java.util.Collection;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

public class DecodeFormatManager {

    private static final Pattern COMMA_PATTERN = Pattern.compile(",");

    //一纬码
    private static final Set<BarcodeFormat> PRODUCT_FORMATS;//商品
    private static final Set<BarcodeFormat> INDUSTRIAL_FORMATS;//工业


    private static final Set<BarcodeFormat> ONE_D_FORMATS;

    // 二维码解码
    private static final Set<BarcodeFormat> QR_CODE_FORMATS = EnumSet.of(BarcodeFormat.QR_CODE);
    //DATA_MATRIX
    private static final Set<BarcodeFormat> DATA_MATRIX_FORMATS = EnumSet.of(BarcodeFormat.DATA_MATRIX);
    //二维码Aztec
    private static final Set<BarcodeFormat> AZTEC_FORMATS = EnumSet.of(BarcodeFormat.AZTEC);
    //PDF_417
    private static final Set<BarcodeFormat> PDF417_FORMATS = EnumSet.of(BarcodeFormat.PDF_417);

    static {
        PRODUCT_FORMATS = EnumSet.of(BarcodeFormat.UPC_A,
                BarcodeFormat.UPC_E,
                BarcodeFormat.EAN_13,
                BarcodeFormat.EAN_8,
                BarcodeFormat.RSS_14,
                BarcodeFormat.RSS_EXPANDED);
        INDUSTRIAL_FORMATS = EnumSet.of(BarcodeFormat.CODE_39,
                BarcodeFormat.CODE_93,
                BarcodeFormat.CODE_128,
                BarcodeFormat.ITF,
                BarcodeFormat.CODABAR);
        ONE_D_FORMATS = EnumSet.copyOf(PRODUCT_FORMATS);
        ONE_D_FORMATS.addAll(INDUSTRIAL_FORMATS);


    }


    public static Collection<BarcodeFormat> getOneCodeFormats() {
        return ONE_D_FORMATS;
    }

    public static Collection<BarcodeFormat> getQrCodeFormats() {
        return QR_CODE_FORMATS;
    }

    public static Collection<BarcodeFormat> getDataMatrixFormats() {
        return QR_CODE_FORMATS;
    }

    public static Collection<BarcodeFormat> getOneAndQrFormats() {
        Set<BarcodeFormat> ONE_AND_QR_FORMATS = EnumSet.copyOf(ONE_D_FORMATS);
        ONE_AND_QR_FORMATS.addAll(QR_CODE_FORMATS);
        return ONE_AND_QR_FORMATS;
    }

    public static Collection<BarcodeFormat> getAllFormats() {
        Set<BarcodeFormat> ALLFORMATS = EnumSet.copyOf(ONE_D_FORMATS);
        ALLFORMATS.addAll(QR_CODE_FORMATS);
        ALLFORMATS.addAll(DATA_MATRIX_FORMATS);
        ALLFORMATS.addAll(AZTEC_FORMATS);
        ALLFORMATS.addAll(PDF417_FORMATS);
        return ALLFORMATS;
    }

    public enum TYPE {
        ONE_CODE,
        QR_CODE,
        ONE_AND_QR,
        DATA_MATRIX_CODE,
        ALL,
    }


}
