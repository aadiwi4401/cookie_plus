package edu.msu.cse476.adiwidj1.cookie_plus;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class CookieActivity extends AppCompatActivity {

    private int counter = 0;
    private TextView counterTextView;
    private FrameLayout frameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cookie);

        frameLayout = findViewById(R.id.cookieContainer);
        Button buttonCounter = findViewById(R.id.buttonCounter);
        counterTextView = findViewById(R.id.cookieClickerCounter);
        updateCounter();

        buttonCounter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counter++;
                updateCounter();
                addCookieImageView();
            }
        });
    }
    private void addCookieImageView() {
        ImageView imageView = new ImageView(this);
        imageView.setImageResource(R.drawable.cookie);

        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.WRAP_CONTENT,
                FrameLayout.LayoutParams.WRAP_CONTENT
        );

        layoutParams.leftMargin = (int)(Math.random() * (frameLayout.getWidth() - imageView.getDrawable().getIntrinsicWidth()));
        layoutParams.topMargin = (int)(Math.random() * (frameLayout.getHeight() - imageView.getDrawable().getIntrinsicHeight()));

        imageView.setLayoutParams(layoutParams);

        frameLayout.addView(imageView, 0);
    }


    void updateCounter() {
        String counterTextFormat = getString(R.string.cookieCounterPlaceholderText);
        String counterText = String.format(counterTextFormat, counter);

        counterTextView.setText(counterText);
    }

    public void goBackToMainMenu(View view) {
        finish();
    }
}
