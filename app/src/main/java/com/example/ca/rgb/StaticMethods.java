package com.example.ca.rgb;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.Button;

import com.example.ca.rgb.Interfaces.APIaddTopRank;
import com.example.ca.rgb.Interfaces.APIgetID;
import com.example.ca.rgb.RetrofitPoziv.RetrofitAddTopRank;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

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

    public static int getProfileFirst(Context context) {
        SharedPreferences profileFirst = context.getSharedPreferences("ProfileFirst", 0);
        int pF = profileFirst.getInt("ProfileFirst", 0);
        return pF;
    }

    public static int getPlayIntro(Context context) {
        SharedPreferences playIntro = context.getSharedPreferences("PlayIntro", 0);
        int pI = playIntro.getInt("PlayIntro", 0);
        return pI;
    }

    public static String getName(Context context) {
        SharedPreferences namePreference = context.getSharedPreferences("name", 0);
        String name = namePreference.getString("name", "");
        return name;
    }

    public static String getAvatar(Context context) {
        SharedPreferences settings = context.getSharedPreferences("avatar", 0);
        String avatar = settings.getString("avatar", "avatar1");
        return avatar;
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

    public static void setAvatar(Context context, String avatar) {
        SharedPreferences Avatar = context.getSharedPreferences("avatar", 0);
        SharedPreferences.Editor editorA = Avatar.edit();
        editorA.putString("avatar", avatar);
        editorA.apply();
    }

    public static void setProfileFirst(Context context, int pF) {
        SharedPreferences profileFirst = context.getSharedPreferences("ProfileFirst", 0);
        SharedPreferences.Editor editorPF = profileFirst.edit();
        editorPF.putInt("ProfileFirst", pF);
        editorPF.apply();
    }

    public static void setPlayIntro(Context context, int pI) {
        SharedPreferences playIntro = context.getSharedPreferences("PlayIntro", 0);
        SharedPreferences.Editor editorPI = playIntro.edit();
        editorPI.putInt("PlayIntro", pI);
        editorPI.apply();
    }

    public static void setPoints(Context context, int points) {
        SharedPreferences pointsPreferences = context.getSharedPreferences("Points", 0);
        SharedPreferences.Editor editorMS = pointsPreferences.edit();
        editorMS.putInt("Points", points);
        editorMS.apply();
        addNewTopRank(context);
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

        if (points >= 10 && points < 100) {
            return 1;
        } else if (points >= 100 && points < 400) {
            return 2;
        } else if (points >= 400 && points < 700) {
            return 3;
        } else if (points >= 700 && points < 1000) {
            return 4;
        } else if (points >= 1000 && points < 3000) {
            return 5;
        } else if (points >= 3000 && points < 5000) {
            return 6;
        } else if (points >= 5000) {
            return 7;
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
                ret = "Talented";
                break;
            case 6:
                ret = "Professional";
                break;
            case 7:
                ret = "Insane";
                break;
            default:
                ret = "Beginner";
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
                right = "10";
                break;
            case 1:
                right = "100";
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
            case 5:
                right = "3000";
                break;
            case 6:
                right = "5000";
                break;
        }

        if(stars != 7){
            ret = left + "/" + right;
        }else{
            ret = left;
        }

        return ret;
    }

    public static boolean isConnectedToNetwork(Context context) {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        boolean isConnected = false;
        if (connectivityManager != null) {
            NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
            isConnected = (activeNetwork != null) && (activeNetwork.isConnectedOrConnecting());
        }

        return isConnected;
    }

    public static void addNewTopRank(Context context){
        APIaddTopRank api = RetrofitAddTopRank.getApi();
        Call<String> call;

        SharedPreferences settings = context.getApplicationContext().getSharedPreferences("ID",0);
        int ID = settings.getInt("ID",0);

        if(ID == 0){
            setID(context);
            settings = context.getApplicationContext().getSharedPreferences("ID", 0);
            ID = settings.getInt("ID", 0);
        }

        settings = context.getApplicationContext().getSharedPreferences("Points",0);
        int points = settings.getInt("Points",0);

        settings = context.getApplicationContext().getSharedPreferences("name",0);
        String name = settings.getString("name","");

        settings = context.getApplicationContext().getSharedPreferences("avatar",0);
        String avatar = settings.getString("avatar","");


        call = api.setQuery(ID,name,avatar,points);


        settings = context.getApplicationContext().getSharedPreferences("firstTime", 0);
        SharedPreferences.Editor editor = settings.edit();

        editor.putInt("firstTime", 1);
        // Apply the edits!
        editor.apply();
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {

                } else {

                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }

    public static boolean checkRewardDate(){
        // Get Current Date Time
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        String getCurrentDateTime = sdf.format(c.getTime());
        String getMyTime="04/18/2019";

        if (getCurrentDateTime.compareTo(getMyTime) < 0)
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    static void setID(Context context) {
        final Context context1 = context;
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(ScalarsConverterFactory.create())
                .baseUrl(DomainName.getIstance())
                .build();

        APIgetID scalarService = retrofit.create(APIgetID.class);
        Call<String> stringCall = scalarService.getStringResponse("/RGB/getID.php");
        stringCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    String responseString = response.body();
                    try {
                        JSONObject object = new JSONObject(responseString);
                        JSONArray Jarray = object.getJSONArray("data");
                        for (int i = 0; i < Jarray.length(); i++) {
                            JSONObject Jasonobject = Jarray.getJSONObject(i);

                            int ID = Integer.parseInt(Jasonobject.get("ID").toString());
                            ID++;

                            SharedPreferences settings = context1.getApplicationContext().getSharedPreferences("ID", 0);
                            SharedPreferences.Editor editor = settings.edit();

                            editor.putInt("ID", ID);
                            // Apply the edits!
                            editor.apply();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    // todo: do something with the response string
                } else {

                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }
}
