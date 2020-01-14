package com.example.abesg.beliked.Utils.Animation;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;

/**
 * Created by abesg on 04/01/2019.
 */

public class Animation_IconToggle {

    private static final String TAG = "Animation_IconToggle";

    private static final DecelerateInterpolator DECCELERATE_INTERPOLATOR = new DecelerateInterpolator();
    private static final AccelerateInterpolator ACCELERATE_INTERPOLATOR = new AccelerateInterpolator();

    public ImageView untoggled, toggled;

    public Animation_IconToggle(ImageView untoggled, ImageView toggled) {
        this.untoggled = untoggled;
        this.toggled = toggled;
    }

    public void toggle(){
        Log.d(TAG, "toggle: toggling icon.");

        AnimatorSet animationSet =  new AnimatorSet();

        if(toggled.getVisibility() == View.VISIBLE){
            Log.d(TAG, "toggle: toggling icon off.");
            toggled.setScaleX(0.1f);
            toggled.setScaleY(0.1f);

            ObjectAnimator scaleDownY = ObjectAnimator.ofFloat(toggled, "scaleY", 1f, 0f);
            scaleDownY.setDuration(300);
            scaleDownY.setInterpolator(ACCELERATE_INTERPOLATOR);

            ObjectAnimator scaleDownX = ObjectAnimator.ofFloat(toggled, "scaleX", 1f, 0f);
            scaleDownX.setDuration(300);
            scaleDownX.setInterpolator(ACCELERATE_INTERPOLATOR);

            toggled.setVisibility(View.GONE);
            untoggled.setVisibility(View.VISIBLE);

            animationSet.playTogether(scaleDownY, scaleDownX);
        }

        else if(toggled.getVisibility() == View.GONE){
            Log.d(TAG, "toggle: toggling icon on.");
            toggled.setScaleX(0.1f);
            toggled.setScaleY(0.1f);

            ObjectAnimator scaleDownY = ObjectAnimator.ofFloat(toggled, "scaleY", 0.1f, 1f);
            scaleDownY.setDuration(300);
            scaleDownY.setInterpolator(DECCELERATE_INTERPOLATOR);

            ObjectAnimator scaleDownX = ObjectAnimator.ofFloat(toggled, "scaleX", 0.1f, 1f);
            scaleDownX.setDuration(300);
            scaleDownX.setInterpolator(DECCELERATE_INTERPOLATOR);

            toggled.setVisibility(View.VISIBLE);
            untoggled.setVisibility(View.GONE);

            animationSet.playTogether(scaleDownY, scaleDownX);
        }

        animationSet.start();
    }
}

