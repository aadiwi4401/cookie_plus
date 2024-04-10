package edu.msu.cse476.adiwidj1.cookie_plus;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

public class ShopActivity extends CookieActivity {
    private final SetOptions mergeOptions = SetOptions.merge();

    public int currentClicks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        getUserClicks();

        Button plus2Button = findViewById(R.id.plus2Button);
        plus2Button.setOnClickListener(v -> {
            if (currentClicks < 10) {
                Toast.makeText(ShopActivity.this, "Not enough cookies!", Toast.LENGTH_SHORT).show();
            } else {
                finish();

                Intent intent = new Intent(ShopActivity.this, CookieActivity.class);
                intent.putExtra("cookieModifier", 1);
                startActivity(intent);

                subtractFromUserClicks(10);
            }
        });

        Button plus3Button = findViewById(R.id.plus3Button);
        plus3Button.setOnClickListener(v -> {
            if (currentClicks < 20) {
                Toast.makeText(ShopActivity.this, "Not enough cookies!", Toast.LENGTH_SHORT).show();
            } else {
                finish();

                Intent intent = new Intent(ShopActivity.this, CookieActivity.class);
                intent.putExtra("cookieModifier", 2);
                startActivity(intent);

                subtractFromUserClicks(20);
            }
        });

        Button backToCookieButton = findViewById(R.id.backToCookie);
        backToCookieButton.setOnClickListener(v -> {
            finish();

            Intent intent = new Intent(ShopActivity.this, CookieActivity.class);
            intent.putExtra("cookieModifier", 0);
            startActivity(intent);
        });
    }

    private void getUserClicks() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String userId = user.getUid();
            db.collection("userData")
                .document(userId)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        Long numCookiesClicked = documentSnapshot.getLong("numCookiesClicked");
                        if (numCookiesClicked != null) {
                            currentClicks = numCookiesClicked.intValue();
                        }
                    }
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(ShopActivity.this, "Failed to get user clicks",
                            Toast.LENGTH_SHORT).show();
                });
        }
    }

    private void subtractFromUserClicks(int subtractedValue) {
        currentClicks -= subtractedValue;
        StoreUserClicks();
    }

    private void StoreUserClicks() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String userId = user.getUid();
            Map<String, Object> userClicks = new HashMap<>();
            userClicks.put("numCookiesClicked", currentClicks);

            db.collection("userData")
                    .document(userId)  // Use user's ID as document ID
                    .set(userClicks, mergeOptions);
        }
    }
}
