package com.flycode.healthbloom.utils;

import android.graphics.Bitmap;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Random;

public class FileUtils {

    public static String saveImage(Bitmap finalBitmap,String path) {
        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + path);

        myDir.mkdirs();

        //TODO: make incremental instead of random number
        Random generator = new Random();
        int n = 10000;
        n = generator.nextInt(n);

        String fname = "Image-"+ n +".jpg";
        File file = new File(myDir, fname);
        if (file.exists ())
            file.delete ();

        try {
            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();

            return fname;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean deleteImage(String imagePath) {
        File file = new File(imagePath);
        return file.exists() && file.delete();
    }
}
