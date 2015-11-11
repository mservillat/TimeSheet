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
    public static String saveInternalStorage(Bitmap bitmap, String fileName) {

        String stored = null;

        File myDir = Environment.getDataDirectory();
        File folder = new File(myDir,  "/" + fileName + ".jpg");
//        folder.mkdir();
        File file = new File(folder.getAbsoluteFile(), "/" + fileName + ".jpg") ;
        if (file.exists()) {
            Log.d("walkyima", "save  EXISTE não precisa salvar--- return" );
            return stored;
        }


        try {
            FileOutputStream out = new FileOutputStream(folder);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();

            stored = "success";
            Log.d("walkyima", " save " + stored);
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

            mediaImage = new File(myDir.getPath() +"/" +imageName + ".jpg");
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

        if (path != null) {
            final BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            b = BitmapFactory.decodeFile(path, options);
            Log.d("walkyimage", "caminho existe");
        }

        if(b == null ||  b.equals("")) {
            Log.d("walkyimage", "imagem não existe");
            return false ;
        }
        return true ;
    }
}
