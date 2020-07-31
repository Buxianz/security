package com.rbi.security.tool;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Decoder;

public class Base64Util {

    public static MultipartFile base64ToFile(String base64) {
        try {
            String[] baseStrs = base64.split(",");

            BASE64Decoder decoder = new BASE64Decoder();
            byte[] b = new byte[0];
            b = decoder.decodeBuffer(baseStrs[1]);

            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {
                    b[i] += 256;
                }
            }
            return new BASE64DecodedMultipartFile(b, baseStrs[0]);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    public static MultipartFile[] base64ToListFile(List<String> list) throws IOException {
        List<MultipartFile> list1 = new ArrayList<>();
        for (int j = 0; j < list.size(); j++) {
            String base64 = list.get(j);
            String[] baseStrs = base64.split(",");
            BASE64Decoder decoder = new BASE64Decoder();
            byte[] b = new byte[0];
            b = decoder.decodeBuffer(baseStrs[1]);
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {
                    b[i] += 256;
                }
            }
            list1.add(new BASE64DecodedMultipartFile(b, baseStrs[0]));
        }
        MultipartFile[] s=new MultipartFile[list1.size()];
        list1.toArray(s);
        return s;
    }
}
