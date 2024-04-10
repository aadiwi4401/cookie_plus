package edu.msu.cse476.adiwidj1.cookie_plus;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

public class LeaderBoardsActivity extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    FirebaseAuth users = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboards);

        TextView firstPlace = (TextView) findViewById(R.id.firstPlace);
        TextView secondPlace = (TextView) findViewById(R.id.secondPlace);
        TextView thirdPlace = (TextView) findViewById(R.id.thirdPlace);
        TextView fourthPlace = (TextView) findViewById(R.id.fourthPlace);
        TextView fifthPlace = (TextView) findViewById(R.id.fifthPlace);


        String first = "Adrian";
        String second = "Marshal";
        String third = "Pranav";
        String fourth = "David";
        String fifth = "Fred";

        firstPlace.setText(first);
        secondPlace.setText(second);
        thirdPlace.setText(third);
        fourthPlace.setText(fourth);
        fifthPlace.setText(fifth);
    }

    public void goBackToMainMenu(View view) {
        finish();
    }
}
