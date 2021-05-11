package com.example.exampleapplication;

import android.animation.ValueAnimator;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Insets;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowInsets;
import android.view.WindowInsetsAnimation;
import android.view.WindowInsetsAnimationControlListener;
import android.view.WindowInsetsAnimationController;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.UiThread;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.AlarmManagerCompat;

import java.util.List;

import static android.view.WindowInsetsAnimation.Callback.DISPATCH_MODE_STOP;

public class Main2Activity extends AppCompatActivity {

    Button start;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        alarmManagerExample();

//        if (Build.VERSION.SDK_INT >= 30) {
//            android11Example();
//        }
//        simpleAnimation();
//        upiPaymentTriggerExample();
//        circularDialExample();
        popNeoExample();
    }

    private void popNeoExample() {
        setContentView(R.layout.pop_neo_layout);
    }

    private void upiPaymentTriggerExample() {
        payUsingUpi();
    }

    @RequiresApi(30)
    private void android11Example() {
        Log.d("ANDROID_VERSION", Build.VERSION.SDK_INT + "");
        getWindow().setDecorFitsSystemWindows(false);
        final WindowInsetsAnimationController[] animationController = {null};

        WindowInsetsAnimationControlListener listener = new WindowInsetsAnimationControlListener() {
            @Override
            public void onReady(@NonNull WindowInsetsAnimationController windowInsetsAnimationController, int i) {
                animationController[0] = windowInsetsAnimationController;
            }

            @Override
            public void onFinished(@NonNull WindowInsetsAnimationController windowInsetsAnimationController) {

            }

            @Override
            public void onCancelled(@Nullable WindowInsetsAnimationController windowInsetsAnimationController) {
                animationController[0] = null;
            }
        };

        setTheme(R.style.KeyboardAnimationTheme);
        setContentView(R.layout.layouta_alarm_manager);

        getWindow().getInsetsController().controlWindowInsetsAnimation(
                WindowInsets.Type.ime(), -1, null, null, listener);
        Button inputLayout = findViewById(R.id.button);


        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) inputLayout.getLayoutParams();
        int inputLayoutMarginBottom = layoutParams.bottomMargin;

        inputLayout.setWindowInsetsAnimationCallback(
                new WindowInsetsAnimation.Callback(DISPATCH_MODE_STOP) {
                    @NonNull
                    @Override
                    public WindowInsets onProgress(@NonNull WindowInsets windowInsets, @NonNull List<WindowInsetsAnimation> list) {
                        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) layoutParams;
                        params.bottomMargin = inputLayoutMarginBottom + windowInsets.getInsets(WindowInsets.Type.ime()).bottom;
                        return windowInsets;
                    }
                });

        inputLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ValueAnimator widthAnimator = ValueAnimator.ofInt(0, 500);
                widthAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        int animatedValue = (int) animation.getAnimatedValue();
                        animationController[0].setInsetsAndAlpha(Insets.of(0, 0, 0, animatedValue), 1f, 0f);

                    }
                });
                widthAnimator.start();
            }
        });
    }

    private void alarmManagerExample() {
        setContentView(R.layout.layouta_alarm_manager);

        start = findViewById(R.id.button);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startAlert();
            }
        });
    }

    public void startAlert() {
        EditText text = findViewById(R.id.time);
        int i = Integer.parseInt(text.getText().toString());
        Intent intent = new Intent(this, AlarmBroadcastReceiver.class);
        AlarmManager manager = (AlarmManager) this.getSystemService(ALARM_SERVICE);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                this, 112341, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        manager.setExact(AlarmManager.RTC_WAKEUP, (long) i * 1000, pendingIntent);


    }

    public void simpleAnimation() {
        setContentView(R.layout.layout_simple_animation);
        TextView textView = findViewById(R.id.mainText);
        Animation translateToTop = AnimationUtils
                .loadAnimation(this, R.anim.translate_to_top);

        textView.startAnimation(translateToTop);
    }

    public void payUsingUpi() {

        Uri uri = Uri.parse("upi://pay").buildUpon()
                .appendQueryParameter("pa", "ayush.7scor@okhdfc")
                .appendQueryParameter("pn", "ayush bansal")
                .appendQueryParameter("tn", "Trial payment ")
                .appendQueryParameter("am", "2")
                .appendQueryParameter("cu", "INR")
                .build();


        Intent upiPayIntent = new Intent(Intent.ACTION_VIEW);
        upiPayIntent.setData(uri);

        // will always show a dialog to user to choose an app
        Intent chooser = Intent.createChooser(upiPayIntent, "Pay with");

        // check if intent resolves
        if (null != chooser.resolveActivity(getPackageManager())) {
            startActivityForResult(chooser, 0);
        } else {
            Toast.makeText(this,
                    "No UPI app found, please install one to continue", Toast.LENGTH_SHORT).show();
        }

    }

    public void circularDialExample() {
        setContentView(R.layout.circulardial_layout);
    }
}