package br.com.mowa.timesheet.utils;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.os.Build;

import br.com.mowa.timesheet.adapter.TasksRecyclerviewAdapter;

/**
 * Created by walky on 10/15/15.
 */
public class AnimationsUtil {

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static void animate(TasksRecyclerviewAdapter.ItemViewHolder holder, boolean goesDown, boolean parAndImpa) {
        AnimatorSet animatorSet = new AnimatorSet();
        ObjectAnimator animatorTranslateX;
        ObjectAnimator animatorTranslateY = ObjectAnimator.ofFloat(holder.container, "translationY", goesDown == true ? 600 : -600, 0);
        if (parAndImpa) {
            animatorTranslateX = ObjectAnimator.ofFloat(holder.container, "translationX", -50, 50, -30, 30, -20, 20, -10, 10, -5, 5, 0);
        } else {
            animatorTranslateX = ObjectAnimator.ofFloat(holder.container, "translationX", 50, -50, 30, -30, 20, -20, 10, -10, 5, -5, 0);
        }


        animatorTranslateY.setDuration(3000);
        animatorTranslateX.setDuration(3000);
        animatorSet.playTogether(animatorTranslateX, animatorTranslateY);
        animatorSet.start();
    }
}
