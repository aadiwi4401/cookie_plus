package edu.msu.cse476.adiwidj1.cookie_plus;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class CookieActivity extends AppCompatActivity {

    private int counter = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cookie);

        Button buttonCounter = findViewById(R.id.buttonCounter);

        buttonCounter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counter++;
            }
        });
    }

    public void goBackToMainMenu(View view) {
        finish();
    }


}