package com.nayan.doggle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Pattern;

public class ForgotPasswordActivity extends AppCompatActivity {
    EditText forgetEmail;
    TextView signInRedirect;
    AppCompatButton sendEmailbtn;
    String email;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        mAuth = FirebaseAuth.getInstance();

        forgetEmail = findViewById(R.id.forgetemail);
        signInRedirect = findViewById(R.id.signinredirect);
        signInRedirect.setOnClickListener((v) -> {
            startActivity(new Intent(ForgotPasswordActivity.this, LoginActivity.class));

        });
        sendEmailbtn = findViewById(R.id.sendemailbtn);
        sendEmailbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateEmail();
            }
        });
    }

    private void validateEmail() {
        email = forgetEmail.getText().toString();
        if (email.isEmpty() || !Pattern.matches(".+@.+\\.[a-zA-Z]{2,}", email)) {
            showError(forgetEmail, "Please enter a valid E-mail Id");
        }
        else{
            resetPass();
        }
    }

    private void showError(EditText input, String s) {
        input.setError(s);
        input.requestFocus();
    }

    private void resetPass(){
        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(getApplicationContext(), "Password Reset Link sent to your E-Mail",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(ForgotPasswordActivity.this, LoginActivity.class));
                    finish();
                }
                else{
                    Toast.makeText(getApplicationContext(), "Error encountered. Please ensure that you have entered a valid E-Mail address.",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}