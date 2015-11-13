package br.com.mowa.timesheet.utils;

import android.content.Context;
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
    public static final String LOG_TAG = "ImageDownload";
    private String requestUrl, imageName;
    private ImageView imageView;
    private Bitmap bitmap ;
    private FileOutputStream fos;
    private OnImageDownloadListener listener;
    private Context context;

    public interface OnImageDownloadListener {
        void onImageDownloadFinishListener(Bitmap imageDownload, String url);
    }

    public ImageDownload(Context context, String requestUrl, ImageView view, String imageName, OnImageDownloadListener listener) {
        this.requestUrl = requestUrl;
        this.imageView = view;
        this.imageName = imageName ;
        this.listener = listener;
        this.context = context;
    }

    @Override
    protected Object doInBackground(Object... objects) {
        try {
            URL url = new URL(requestUrl);
            URLConnection conn = url.openConnection();
            conn.setRequestProperty("X-Api-Token", "QdqV9mQud/PT0LiiEW4zpuJeuCWfEsToMqiuto98XyT5U48CymfdXW/m5us+Tcx9dewwJuiYOYi2JFdd8qD0Rw==");
            conn.setRequestProperty("Content-Type", "application/json;charset=utf-8");
            bitmap = BitmapFactory.decodeStream(conn.getInputStream());
            Log.d(LOG_TAG, "downloading");

            ImageStorage.saveInternalStorage(context, bitmap, requestUrl);

        } catch (Exception ex) {
        }
        return null;
    }

    @Override
    protected void onPostExecute(Object o) {
        listener.onImageDownloadFinishListener(bitmap, requestUrl);
    }

}
