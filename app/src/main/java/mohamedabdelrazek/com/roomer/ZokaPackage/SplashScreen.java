package mohamedabdelrazek.com.roomer.ZokaPackage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import mohamedabdelrazek.com.roomer.R;

public class SplashScreen extends AppCompatActivity {
    private  TextView textView;
    private   ImageView imageView;
    private   Animation animation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        textView = (TextView) findViewById(R.id.splash_text);
        imageView = (ImageView) findViewById(R.id.splash_image);
        animation = AnimationUtils.loadAnimation(this, R.animator.from_center);
        imageView.startAnimation(animation);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                startActivity(new Intent(SplashScreen.this, MainActivity.class)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
                overridePendingTransition(R.animator.push_up_enter, R.animator.push_up_exit);

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }
}

