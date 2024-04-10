package edu.msu.cse476.adiwidj1.cookie_plus;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class LeaderBoardsActivity extends AppCompatActivity {

    private FirebaseFirestore db;
    private CollectionReference usersRef;

    private TextView user1Text, user2Text, user3Text, user4Text, user5Text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboards);

        db = FirebaseFirestore.getInstance();
        usersRef = db.collection("userData");

        user1Text = findViewById(R.id.user1);
        user2Text = findViewById(R.id.user2);
        user3Text = findViewById(R.id.user3);
        user4Text = findViewById(R.id.user4);
        user5Text = findViewById(R.id.user5);

        loadTopUsers();
    }

    private void loadTopUsers() {
        Query topUsersQuery = usersRef.orderBy("userWins", Query.Direction.DESCENDING).limit(5);

        topUsersQuery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    List<String> topUserEmails = new ArrayList<>();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        String email = document.getString("userEmail");
                        topUserEmails.add(email);
                    }

                    setTopUserEmails(topUserEmails);
                }
            }
        });
    }

    private void setTopUserEmails(List<String> topUserEmails) {
        user1Text.setText(topUserEmails.size() > 0 ? topUserEmails.get(0) : "");
        user2Text.setText(topUserEmails.size() > 1 ? topUserEmails.get(1) : "");
        user3Text.setText(topUserEmails.size() > 2 ? topUserEmails.get(2) : "");
        user4Text.setText(topUserEmails.size() > 3 ? topUserEmails.get(3) : "");
        user5Text.setText(topUserEmails.size() > 4 ? topUserEmails.get(4) : "");
    }

    public void goBackToMainMenu(View view) {
        finish();
    }
}