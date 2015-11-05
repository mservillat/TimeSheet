package br.com.mowa.timesheet.utils;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v8.renderscript.Allocation;
import android.support.v8.renderscript.Element;
import android.support.v8.renderscript.RenderScript;
import android.support.v8.renderscript.ScriptIntrinsicBlur;

import br.com.mowa.timesheet.TimeSheetApplication;
import br.com.mowa.timesheet.timesheet.R;

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
            Resources res = (Resources) params[0];
            int resId = (int) params[1];
            int reqWidth = (int) params[2];
            int reqHeight = (int) params[3];


            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeResource(res, R.drawable.image_perfil, options);

            ImageUtils.calculateInSampleSize(options, reqWidth, reqHeight);

            Bitmap bitmap = ImageUtils.decodeSampledBitmapFromResource(res, R.drawable.image_perfil, reqWidth, reqHeight);

            RenderScript rs = RenderScript.create(TimeSheetApplication.getAppContext());

            final Allocation input = Allocation.createFromBitmap(rs, bitmap);
            final Allocation output = Allocation.createTyped(rs, input.getType());
            final ScriptIntrinsicBlur script = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
            script.setRadius(8f);
            script.setInput(input);
            script.forEach(output);
            output.copyTo(bitmap);
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            listener.onImageFinish(bitmap);
        }
    }



    public void ImageRenderBlur(Resources res, int resId, int reqWidth, int reqHeight, OnImageUtilsFinish onImageFinish) {
        this.listener = onImageFinish;
        new ImageUtilsAsyncTask().execute(res, resId, reqWidth, reqHeight);
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

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }


    private static Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
                                                          int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

}
