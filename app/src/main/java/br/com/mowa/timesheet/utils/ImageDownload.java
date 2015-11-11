package br.com.mowa.timesheet.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.FileOutputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by walky on 11/9/15.
 */
public class ImageDownload extends AsyncTask<Object, Object, Object> {
    private String requestUrl, imageName;
    private ImageView imageView;
    private Bitmap bitmap ;
    private FileOutputStream fos;
    private ImageUtils.OnImageUtilsFinish finish;
    public ImageDownload(String requestUrl, ImageView view, String imageName, ImageUtils.OnImageUtilsFinish fini) {
        this.requestUrl = requestUrl;
        this.imageView = view;
        this.imageName = imageName ;
        this.finish = fini;
    }

    @Override
    protected Object doInBackground(Object... objects) {
        try {
            URL url = new URL(requestUrl);
            URLConnection conn = url.openConnection();
            conn.setRequestProperty("X-Api-Token", "QdqV9mQud/PT0LiiEW4zpuJeuCWfEsToMqiuto98XyT5U48CymfdXW/m5us+Tcx9dewwJuiYOYi2JFdd8qD0Rw==");
            conn.setRequestProperty("Content-Type", "application/json;charset=utf-8");
            bitmap = BitmapFactory.decodeStream(conn.getInputStream());
            Log.d("walkyImage", "fazendo download");
        } catch (Exception ex) {
        }
        return null;
    }

    @Override
    protected void onPostExecute(Object o) {
        if(!ImageStorage.checkifImageExists(imageName))
        {
            ImageStorage.saveInternalStorage(bitmap, imageName);
            this.finish.onImageFinish(bitmap);
//            imageView.setImageBitmap(bitmap);
        }
    }

}
