package edu.msu.cse476.adiwidj1.cookie_plus;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button startButton = findViewById(R.id.buttonStart);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLoginPage();
            }
        });

        Button leaderBoardsButton = findViewById(R.id.buttonLeadersBoard);
        leaderBoardsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { openLeaderboardsPage(); }


        });

        Button aboutButton = findViewById(R.id.buttonAbout);
        aboutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAboutPage();
            }


        });

        Button quitButton = findViewById(R.id.buttonQuit);
        quitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Close the activity and exit the application
            }
        });
    }

    public void openAboutPage() {
        Intent intent = new Intent(this, AboutActivity.class);
        startActivity(intent);
    }

    public void openLeaderboardsPage() {
        Intent intent = new Intent(this, LeaderBoardsActivity.class);
        startActivity(intent);
    }

    public void openLoginPage() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}