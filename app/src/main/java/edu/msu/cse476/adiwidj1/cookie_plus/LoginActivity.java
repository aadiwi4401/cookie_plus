package edu.msu.cse476.adiwidj1.cookie_plus;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button switchToSignupButton = findViewById(R.id.switchToSignup);
        switchToSignupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                openSignupPage();
            }
        });

        Button backToMainMenuButton = findViewById(R.id.backToMainMenu);
        backToMainMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Close the activity and exit the application
            }
        });
    }

    public void openSignupPage() {
        Intent intent = new Intent(this, SignupActivity.class);
        startActivity(intent);
    }
}
