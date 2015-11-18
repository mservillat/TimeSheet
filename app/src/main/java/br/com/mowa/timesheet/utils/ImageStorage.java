package br.com.mowa.timesheet.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by walky on 11/9/15.
 */
public class ImageStorage {

    public static final String LOG_TAG = "ImageStorage";

    public static boolean saveInternalStorage(Context context, Bitmap bitmap, String url) {

        String fileName = Integer.toString(url.hashCode());

        File cachesDir = context.getCacheDir();

        if (cachesDir.canWrite()) {

            File imageFile = new File(cachesDir + File.separator, fileName);

                try {
                    FileOutputStream out = new FileOutputStream(imageFile);

                    bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);

                    out.flush();
                    out.close();

                    Log.d(LOG_TAG, "File " + imageFile.getPath() + " saved.");

                    return true;

                } catch (Exception e) {
                    e.printStackTrace();

                    return false;
                }

//            } else {
//                Log.d(LOG_TAG, "Don't have permission to write file " + imageFile.getPath());
//                return false;
//            }
        } else {
            Log.d(LOG_TAG, "Don't have permission to write in internal caches directory");

            File externalCachesDir = Environment.getExternalStorageDirectory();

            if (externalCachesDir.canWrite()) {
                Log.d(LOG_TAG, "Have permission to write in external directory");
            } else {
                Log.d(LOG_TAG, "Don't have permission to write in external directory");
            }

            return false;
        }
    }

    public static File getImage(Context context, String url) {

        String imageName = Integer.toString(url.hashCode());

        File cachesDir = context.getCacheDir();

        File imageFile = new File(cachesDir + File.separator, imageName);

        boolean fileExists = false;

//        if (imageFile.exists()) {
//            return imageFile;
//        } else {
//            return null;
//        }

//        fileExists = imageFile.exists();

        if (imageFile.exists()) {
            return imageFile;
        } else {
            return null;
        }

    }

    public static Bitmap getImage(Context context, String url, int reqWidth, int reqHeight) {

        String imageName = Integer.toString(url.hashCode());

        File cachesDir = context.getCacheDir();

        File imageFile = new File(cachesDir + File.separator + imageName);

        if (imageFile.exists()) {
            Log.d(LOG_TAG, "image storage exist");
            return ImageUtils.decodeSampledBitmapFromResource(imageFile, reqWidth, reqHeight);
        } else {
            return null;
        }

    }

}
