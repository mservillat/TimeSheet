package br.com.mowa.timesheet.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.File;

/**
 * Created by walky on 11/5/15.
 */
public class ImageUtils {
    private OnImageUtilsFinish listener;

    public interface OnImageUtilsFinish {
        void onImageFinish(Bitmap bitmap);
    }

    public class ImageUtilsAsyncTask extends AsyncTask<Object, Bitmap, Bitmap> {
        @Override
        protected Bitmap doInBackground(Object... params) {
            File file = (File) params[0];
            int reqWidth = (int) params[1];
            int reqHeight = (int) params[2];


            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(file.getPath(), options);

            calculateInSampleSize(options, reqWidth, reqHeight);

            Bitmap bitmap = decodeSampledBitmapFromResource(file,reqWidth, reqHeight);

//            RenderScript rs = RenderScript.create(TimeSheetApplication.getAppContext());
//
//            final Allocation input = Allocation.createFromBitmap(rs, bitmap);
//            final Allocation output = Allocation.createTyped(rs, input.getType());
//            final ScriptIntrinsicBlur script = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
//            script.setRadius(8f);
//            script.setInput(input);
//            script.forEach(output);
//            output.copyTo(bitmap);
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            listener.onImageFinish(bitmap);
        }
    }



    public void ImageRenderBlur(File file, int reqWidth, int reqHeight, OnImageUtilsFinish onImageFinish) {
        this.listener = onImageFinish;
        new ImageUtilsAsyncTask().execute(file, reqWidth, reqHeight);
    }



    private static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;


            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }


    public static Bitmap decodeSampledBitmapFromResource(File file,
                                                          int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(file.getPath(), options);
        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(file.getPath(), options);
    }


}
