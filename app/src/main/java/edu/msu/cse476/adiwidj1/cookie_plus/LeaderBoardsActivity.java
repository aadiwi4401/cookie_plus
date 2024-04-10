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

import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class LeaderBoardsActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboards);


        // Get a reference to the Firebase database
        mDatabase = FirebaseDatabase.getInstance().getReference().child("userData");

        // Query to get top 5 users based on integer attribute
        Query topUsersQuery = mDatabase.orderByChild("numCookiesClicked").limitToLast(5);

        topUsersQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Iterate through the top 5 users
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    String userName = userSnapshot.child("userData").getValue(String.class);
                    // Assuming you have TextView elements with ids "user1", "user2", ..., "user5"
                    setUserNameToTextView(userName);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle errors
            }
        });
    }

    private void setUserNameToTextView(String userName) {
        // Find corresponding TextView element and set its text
        TextView textView = findViewById(getNextTextViewId());
        textView.setText(userName);
    }

    // Helper method to get the id of the next TextView element to set the user name
    private int nextTextViewId = R.id.user1;
    private int getNextTextViewId() {
        return nextTextViewId++;
    }

    public void goBackToMainMenu(View view) {
        finish();
    }
}
