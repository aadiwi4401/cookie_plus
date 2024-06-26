package edu.msu.cse476.adiwidj1.cookie_plus;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private EditText emailTextView, passwordTextView;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailTextView = findViewById(R.id.loginEmail);
        passwordTextView = findViewById(R.id.loginPassword);

        mAuth = FirebaseAuth.getInstance();

        Button loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(v -> loginUser());

        Button switchToSignupButton = findViewById(R.id.switchToSignup);
        switchToSignupButton.setOnClickListener(v -> {
            finish();
            openSignupPage();
        });

        Button backToMainMenuButton = findViewById(R.id.backToMainMenu);
        backToMainMenuButton.setOnClickListener(v -> finish());
    }


    public void openSignupPage() {
        Intent intent = new Intent(this, SignupActivity.class);
        startActivity(intent);
    }

    public void openCookiePage() {
        Intent intent = new Intent(this, CookieActivity.class);
        startActivity(intent);
    }

    private void loginUser() {
        String email, password;
        email = emailTextView.getText().toString();
        password = passwordTextView.getText().toString();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(),
                "Please enter email", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(),
                    "Please enter password", Toast.LENGTH_LONG).show();
           return;
        }

        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(new OnCompleteListener<AuthResult>()
            {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task)
                {
                   if (task.isSuccessful()) {
                        Toast.makeText(getApplicationContext(),
                                "Logged in", Toast.LENGTH_LONG).show();
                        openCookiePage();
                        finish();
                    }
                    else {
                       Toast.makeText(getApplicationContext(),
                                "Failed to log in", Toast.LENGTH_LONG).show();
                    }
               }
            });
    }
}
