package com.edu.book.infrastructure.util;

import com.edu.book.infrastructure.enums.CommonFileTypeEnum;

import java.io.IOException;
import java.io.InputStream;

public class FileTypeUtil {

    public static CommonFileTypeEnum getFileType(InputStream is) throws IOException {
        byte[] src = new byte[28];
        is.read(src, 0, 28);
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v).toUpperCase();
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        CommonFileTypeEnum[] fileTypes = CommonFileTypeEnum.values();
        for (CommonFileTypeEnum fileType : fileTypes) {
            if (stringBuilder.toString().startsWith(fileType.getValue())) {
                return fileType;
            }
        }
        return null;
    }

}
