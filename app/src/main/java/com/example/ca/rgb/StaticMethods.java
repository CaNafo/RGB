package com.example.ca.rgb;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.ScaleAnimation;

public class StaticMethods {
    public static void fadeInAnimation(final View view, long animationDuration) {
        Animation fadeIn = new AlphaAnimation(0, 1);
        fadeIn.setInterpolator(new DecelerateInterpolator());
        fadeIn.setDuration(animationDuration);
        fadeIn.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                view.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });

        view.startAnimation(fadeIn);
    }

    public static void fadeOutAnimation(final View view, long animationDuration) {
        Animation fadeOut = new AlphaAnimation(1, 0);
        fadeOut.setInterpolator(new AccelerateInterpolator());
        fadeOut.setStartOffset(animationDuration);
        fadeOut.setDuration(animationDuration);
        fadeOut.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                view.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });

        view.startAnimation(fadeOut);
    }

    public static void scaleView(View v, float startScale, float endScale) {
        Animation anim = new ScaleAnimation(
                1f, 1f, // Start and end values for the X axis scaling
                startScale, endScale, // Start and end values for the Y axis scaling
                Animation.RELATIVE_TO_SELF, 0f, // Pivot point of X scaling
                Animation.RELATIVE_TO_SELF, 1f); // Pivot point of Y scaling
        anim.setFillAfter(true); // Needed to keep the result of the animation
        anim.setDuration(1000);
        v.startAnimation(anim);
    }

    public static int getMusic(Context context){
        SharedPreferences MusicSound = context.getSharedPreferences("Music", 0);
        int music = MusicSound.getInt("Music", 0);
        return music;
    }

    public static int getSound(Context context){
        SharedPreferences MusicSound = context.getSharedPreferences("Sound", 0);
        int sound = MusicSound.getInt("Sound", 0);
        return sound;
    }

    public static void setMusic(Context context, int music){
        SharedPreferences MusicSound = context.getSharedPreferences("Music", 0);
        SharedPreferences.Editor editorMS = MusicSound.edit();
        editorMS.putInt("Music", music);
        editorMS.apply();
    }

    public static void setSound(Context context, int sound){
        SharedPreferences MusicSound = context.getSharedPreferences("Sound", 0);
        SharedPreferences.Editor editorMS = MusicSound.edit();
        editorMS.putInt("Sound", sound);
        editorMS.apply();
    }
}
