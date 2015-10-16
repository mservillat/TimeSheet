package br.com.mowa.timesheet.utils;

import android.annotation.TargetApi;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.CardView;
import android.widget.FrameLayout;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import br.com.mowa.timesheet.adapter.ProjectRecyclerviewAdapter;
import br.com.mowa.timesheet.adapter.TasksRecyclerviewAdapter;

/**
 * Created by walky on 10/15/15.
 */
public class AnimationsUtil {

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static void animate(TasksRecyclerviewAdapter.ItemViewHolder holder) {
        YoYo.with(Techniques.ZoomInDown).duration(1000).playOn(holder.container);
//        AnimatorSet animatorSet = new AnimatorSet();
//        ObjectAnimator animatorTranslateX;
//        ObjectAnimator animatorTranslateY = ObjectAnimator.ofFloat(holder.container, "translationY", goesDown == true ? 600 : -600, 0);
//
//        if (parAndImpa) {
//            animatorTranslateX = ObjectAnimator.ofFloat(holder.container, "translationX", -90, 90, -50, 50, -30, 30, -10, 10, -5, 5, 0);
//        } else {
//            animatorTranslateX = ObjectAnimator.ofFloat(holder.container, "translationX", 90, -90, 50, -50, 30, -30, 10, -10, 5, -5, 0);
//        }
//
//
//        animatorTranslateY.setDuration(1500);
//        animatorTranslateX.setDuration(1500);
//        animatorSet.playTogether(animatorTranslateX, animatorTranslateY);
//        animatorSet.start();
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static void animate(ProjectRecyclerviewAdapter.ItemViewHolder holder) {

        YoYo.with(Techniques.ZoomInDown).duration(1000).playOn(holder.container);
    }



    public static void animateCard(CardView card) {
        YoYo.with(Techniques.Landing).duration(3000).playOn(card);
    }

    public static void animateFrameLayout(FrameLayout layout) {
        YoYo.with(Techniques.Landing).duration(2000).playOn(layout);
    }


    public static void animateFloatingButton(FloatingActionButton button) {
        YoYo.with(Techniques.BounceInLeft).duration(3500).playOn(button);
    }
}
