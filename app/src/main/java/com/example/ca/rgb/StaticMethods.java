package com.example.ca.rgb;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.Switch;

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

    public static int getMusic(Context context) {
        SharedPreferences MusicSound = context.getSharedPreferences("Music", 0);
        int music = MusicSound.getInt("Music", 0);
        return music;
    }

    public static int getSound(Context context) {
        SharedPreferences MusicSound = context.getSharedPreferences("Sound", 0);
        int sound = MusicSound.getInt("Sound", 0);
        return sound;
    }

    public static String getName(Context context) {
        SharedPreferences namePreference = context.getSharedPreferences("name", 0);
        String name = namePreference.getString("name", "");
        return name;
    }

    public static int getPoints(Context context) {
        SharedPreferences pointsPreferences = context.getSharedPreferences("Points", 0);
        int sound = pointsPreferences.getInt("Points", 0);
        return sound;
    }

    public static void setMusic(Context context, int music) {
        SharedPreferences MusicSound = context.getSharedPreferences("Music", 0);
        SharedPreferences.Editor editorMS = MusicSound.edit();
        editorMS.putInt("Music", music);
        editorMS.apply();
    }

    public static void setSound(Context context, int sound) {
        SharedPreferences MusicSound = context.getSharedPreferences("Sound", 0);
        SharedPreferences.Editor editorMS = MusicSound.edit();
        editorMS.putInt("Sound", sound);
        editorMS.apply();
    }

    public static void setPoints(Context context, int points) {
        SharedPreferences pointsPreferences = context.getSharedPreferences("Points", 0);
        SharedPreferences.Editor editorMS = pointsPreferences.edit();
        editorMS.putInt("Points", points);
        editorMS.apply();
    }

    public static void switchButton(Button b, String color) {
        switch (color) {
            case "red":
                b.setBackgroundResource(R.drawable.buttonred);
                b.setTag("red");
                break;
            case "green":
                b.setBackgroundResource(R.drawable.buttongreen);
                b.setTag("green");
                break;
            case "blue":
                b.setBackgroundResource(R.drawable.buttonblue);
                b.setTag("blue");
                break;
            case "purple":
                b.setBackgroundResource(R.drawable.buttonpurple);
                b.setTag("purple");
                break;
            case "yellow":
                b.setBackgroundResource(R.drawable.buttonyellow);
                b.setTag("yellow");
                break;
            case "black":
                b.setBackgroundResource(R.drawable.buttonblack);
                b.setTag("black");
                break;
            case "orange":
                b.setBackgroundResource(R.drawable.buttonorange);
                b.setTag("orange");
                break;
            case "white":
                b.setBackgroundResource(R.drawable.buttonwhite);
                b.setTag("white");
                break;
        }
    }

    public static int getStars(Context context) {
        int points = getPoints(context);

        if (points >= 2 && points <= 200) {
            return 1;
        } else if (points > 200 && points <= 400) {
            return 2;
        } else if (points > 400 && points <= 700) {
            return 3;
        } else if (points > 700 && points <= 1000) {
            return 4;
        } else if (points > 1000) {
            return 5;
        }

        return 0;
    }

    public static String getRanking(Context context) {
        int stars = getStars(context);
        String ret = "";

        switch (stars) {
            case 1:
                ret = "Newbee";
                break;
            case 2:
                ret = "Casual";
                break;
            case 3:
                ret = "Regular";
                break;
            case 4:
                ret = "Experienced";
                break;
            case 5:
                ret = "Professional";
                break;
            default:
                ret = "Beginer";
                break;
        }

        return ret;
    }

    public static String retPoints(Context context){
        String ret = "";
        int stars = getStars(context);
        String left = String.valueOf(getPoints(context));
        String right = "";

        switch (stars){
            case 0:
                right = "2";
                break;
            case 1:
                right = "200";
                break;
            case 2:
                right = "400";
                break;
            case 3:
                right = "700";
                break;
            case 4:
                right = "1000";
                break;
        }

        if(stars != 5){
            ret = left + "/" + right;
        }else{
            ret = left;
        }

        return ret;
    }
}
