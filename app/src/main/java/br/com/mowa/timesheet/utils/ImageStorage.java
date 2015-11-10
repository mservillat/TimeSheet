package br.com.mowa.timesheet.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;

import br.com.mowa.timesheet.TimeSheetApplication;

/**
 * Created by walky on 11/9/15.
 */
public class ImageStorage {
    public static final String DIRECTORY = "time_sheet";
    private static final Context context = TimeSheetApplication.getAppContext();
    public static String saveToSdCard(Bitmap bitmap, String fileName) {

        String stored = null;

//        File sdcard = Environment.getExternalStorageDirectory() ;
//        File path = Environment.getDataDirectory();
        File myDir = context.getDir(DIRECTORY, Context.MODE_PRIVATE);
        File folder = new File(myDir, fileName + ".jpg");
        folder.mkdir();
        File file = new File(folder.getAbsoluteFile(), fileName + ".jpg") ;
        if (file.exists())
            return stored ;

        try {
            FileOutputStream out = new FileOutputStream(folder);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();

            stored = "success";
            Log.d("walkyima", " b " + stored);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stored;
    }

    public static File getImage(String imageName) {

        File mediaImage = null;
        try {
            String root = Environment.getDataDirectory().toString();
            File myDir = new File(root);
            if (!myDir.exists())
                return null;

            mediaImage = new File(myDir.getPath() + "/" + DIRECTORY +"/"+imageName + ".jpg");
            Log.d("walkyimage", "get image  " + mediaImage.getPath());
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return mediaImage;
    }



    public static boolean checkifImageExists(String imageName)
    {
        Bitmap b = null ;
        File file = ImageStorage.getImage(imageName);
        String path = file.getPath();
        Log.d("walkyima", " check  " + path);

        if (path != null) {
            final BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            b = BitmapFactory.decodeFile(path, options);
            Log.d("walkyimage", "não null");
        }

        if(b == null ||  b.equals("")) {
            Log.d("walkyimage", "null");
            return false ;
        }
        return true ;
    }
}
