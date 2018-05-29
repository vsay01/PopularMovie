package com.udacity.popularmovie.util;

import android.graphics.Bitmap;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class ImageUtils {
    public static String saveImage(Bitmap image, long movieId) {
        String savedImagePath = null;
        String imageFileName = "JPEG_" + movieId + ".jpg";
        File storageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                + "/movie");
        boolean success = true;
        if (!storageDir.exists()) {
            success = storageDir.mkdirs();
        }
        if (success) {
            File imageFile = new File(storageDir, imageFileName);
            savedImagePath = imageFile.getAbsolutePath();
            try {
                OutputStream fOut = new FileOutputStream(imageFile);
                image.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
                fOut.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

            // Add the image to the system gallery
            //galleryAddPic(context, savedImagePath);
        }
        return savedImagePath;
    }

    /*public static void galleryAddPic(Context context, String imagePath) {
        if (context != null) {
            Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            File f = new File(imagePath);
            Uri contentUri = Uri.fromFile(f);
            mediaScanIntent.setData(contentUri);
            context.sendBroadcast(mediaScanIntent);
        }
    }*/
}
