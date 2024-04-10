package edu.msu.cse476.adiwidj1.cookie_plus;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class CookieActivity extends AppCompatActivity {

    private int counter = 0;
    private TextView counterTextView;
    private FrameLayout frameLayout;


    private SensorManager sensorManager;

    private float acceleration = 10f;

    private float currentAcceleration = 0f;

    private float lastAcceleration = 0f;

    private final SetOptions mergeOptions = SetOptions.merge();

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cookie);

        frameLayout = findViewById(R.id.cookieContainer);
        Button buttonCounter = findViewById(R.id.buttonCounter);
        counterTextView = findViewById(R.id.cookieClickerCounter);

        // Receive extras from intent
        int cookieModifier = getIntent().getIntExtra("cookieModifier", 0);

        updateCounter();

        currentUser = FirebaseAuth.getInstance().getCurrentUser();

        getUserClicks();

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        Objects.requireNonNull(sensorManager).registerListener(sensorListener, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);
        currentAcceleration = SensorManager.GRAVITY_EARTH;
        lastAcceleration = SensorManager.GRAVITY_EARTH;

        Button shopButton = findViewById(R.id.shopButton);
        shopButton.setOnClickListener(v -> {
            StoreUserClicks();
            openShopPage();
            finish();
        });

        buttonCounter.setOnClickListener(v -> {
            switch (cookieModifier) {
                case 1:
                    counter += 2;
                    break;
                case 2:
                    counter += 3;
                    break;
                default:
                    counter += 1;
                    break;
            }

            updateCounter();
            addCookieImageView();
        });
    }

    private final SensorEventListener sensorListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];
            lastAcceleration = currentAcceleration;
            currentAcceleration = (float) Math.sqrt((x*x+y*y+z*z));
            float accelChange = currentAcceleration - lastAcceleration;
            acceleration = acceleration * 0.9f + accelChange;
            if (acceleration > 12)
            {
                counter++;
                updateCounter();
                addCookieImageView();
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };
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
        if (counter >= 100) {
            Toast.makeText(CookieActivity.this, "You won!",
                    Toast.LENGTH_SHORT).show();
            counter = 0;
        }
        String counterTextFormat = getString(R.string.cookieCounterPlaceholderText);
        String counterText = String.format(counterTextFormat, counter);

        counterTextView.setText(counterText);
    }

    public void goBackToMainMenu(View view) {
        StoreUserClicks();
        finish();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getActionMasked();

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                counter++;
                updateCounter();
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                counter++;
                updateCounter();
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
                break;
        }
        return super.onTouchEvent(event);
    }


    @Override
    protected void onResume()
    {
        sensorManager.registerListener(sensorListener, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);
        super.onResume();
    }
    @Override
    protected void onPause()
    {
        sensorManager.unregisterListener(sensorListener);
        super.onPause();
    }

    private void StoreUserClicks() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String userId = user.getUid();
            Map<String, Object> userClicks = new HashMap<>();
            userClicks.put("numCookiesClicked", counter);

            db.collection("userData")
                .document(userId)  // Use user's ID as document ID
                .set(userClicks, mergeOptions);
        }
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
                            counter = numCookiesClicked.intValue();
                            updateCounter();
                        }
                    }
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(CookieActivity.this, "Failed to get user clicks",
                            Toast.LENGTH_SHORT).show();
                });
        }
    }

    public void openShopPage() {
        Intent intent = new Intent(this, ShopActivity.class);
        startActivity(intent);
    }

}
