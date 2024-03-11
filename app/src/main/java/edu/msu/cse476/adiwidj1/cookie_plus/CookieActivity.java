package edu.msu.cse476.adiwidj1.cookie_plus;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class CookieActivity extends AppCompatActivity {

    private int counter = 0;

    private TextView counterTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cookie);

        Button buttonCounter = findViewById(R.id.buttonCounter);
        counterTextView = findViewById(R.id.cookieClickerCounter);
        updateCounter();


        buttonCounter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                counter++;
                updateCounter();
            }
        });
    }

    void updateCounter()
    {
        String counterTextFormat = getString(R.string.cookieCounterPlaceholderText);

        // set the actual values for the placeholders
        String counterText = String.format(counterTextFormat, getString(R.string.cookieClickerCounter), counter);

        // set the formatted text to the TextView
        counterTextView.setText(counterText);

    }
    public void goBackToMainMenu(View view) {
        finish();
    }


}