package edu.msu.cse476.adiwidj1.cookie_plus;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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
        updateCounter();

        currentUser = FirebaseAuth.getInstance().getCurrentUser();

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        Objects.requireNonNull(sensorManager).registerListener(sensorListener, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);
        currentAcceleration = SensorManager.GRAVITY_EARTH;
        lastAcceleration = SensorManager.GRAVITY_EARTH;

        buttonCounter.setOnClickListener(v -> {
            counter++;
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
        String counterTextFormat = getString(R.string.cookieCounterPlaceholderText);
        String counterText = String.format(counterTextFormat, counter);

        counterTextView.setText(counterText);
    }

    public void goBackToMainMenu(View view) {
        StoreUserClicks();
        finish();
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
                .set(userClicks, mergeOptions)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(CookieActivity.this, "Successful", Toast.LENGTH_SHORT)
                                .show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(CookieActivity.this, "Failed", Toast.LENGTH_SHORT)
                                .show();
                    }
                });
        }
    }

}
