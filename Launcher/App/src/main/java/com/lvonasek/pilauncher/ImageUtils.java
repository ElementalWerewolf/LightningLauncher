package com.lvonasek.pilauncher;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;

import com.esafirm.imagepicker.features.ImagePicker;

import java.io.File;
import java.io.FileOutputStream;

public class ImageUtils {

    public static Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float) width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }

    public static void saveBitmap(Bitmap bitmap, File file) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void showImagePicker(Activity activity, int requestCode) {
        String[] permissions = {
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        };
        boolean read = activity.checkSelfPermission(permissions[0]) == PackageManager.PERMISSION_GRANTED;
        boolean write = activity.checkSelfPermission(permissions[1]) == PackageManager.PERMISSION_GRANTED;
        if (read && write) {
            ImagePicker picker = ImagePicker.create(activity).single();
            picker.showCamera(false);
            picker.start(requestCode);
        } else {
            activity.requestPermissions(permissions, 0);
        }
    }
}
