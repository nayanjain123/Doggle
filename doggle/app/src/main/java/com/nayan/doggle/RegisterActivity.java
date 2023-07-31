package com.nayan.doggle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {
    TextView btn;
    private EditText inputUsername,inputPassword,inputEmail,inputConfirmPassword;
    AppCompatButton btnSignup;
    private FirebaseAuth mAuth;
    private ProgressDialog mLoadingBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        btn=findViewById(R.id.loginredirect);
        inputUsername=findViewById(R.id.ipusername);
        inputPassword=findViewById(R.id.ippassword);
        inputEmail=findViewById(R.id.ipemail);
        inputConfirmPassword=findViewById(R.id.ipconfirm);
        mAuth=FirebaseAuth.getInstance();
        mLoadingBar=new ProgressDialog(RegisterActivity.this);

        btnSignup=findViewById(R.id.signupbtn);
        btnSignup.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                checkCredentials();
            }});
        btn.setOnClickListener((v) -> {startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
        });

    }

    private void checkCredentials() {
        String username = inputUsername.getText().toString();
        String email = inputEmail.getText().toString();
        String password = inputPassword.getText().toString();
        String confirmpassword = inputConfirmPassword.getText().toString();

        if (email.isEmpty() || !Pattern.matches(".+@.+\\.[a-zA-Z]{2,}", email)) {
            showError(inputEmail, "Please enter a valid E-mail Id");
        } else if (username.isEmpty() || username.length() < 8 || !Pattern.matches(".*\\d.*", username) || Pattern.matches(".*\\W.*", username)) {
            showError(inputUsername, "Your username should consist of at least 8 characters, at least one digit and not have any special characters.");
        } else if (password.isEmpty() || password.length() < 8 || !Pattern.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=]).{8,}$", password)) {
            showError(inputPassword, "Your Password should consist of at least 8 characters including one uppercase, one lowercase, one digit and one special character.");
        } else if (confirmpassword.isEmpty() || !confirmpassword.equals(password)) {
            showError(inputConfirmPassword, "Your Password does not match.");
        } else {
            mLoadingBar.setTitle("Registration");
            mLoadingBar.setMessage("Validating your credentials, this may take a moment.");
            mLoadingBar.setCanceledOnTouchOutside(false);
            mLoadingBar.show();

            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful())
                    {
                        Toast.makeText(RegisterActivity.this, "Successfully Registered.", Toast.LENGTH_SHORT);
                        Intent intent=new Intent(RegisterActivity.this,LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);

                    }
                    else{
                        Toast.makeText(RegisterActivity.this,task.getException().toString(), Toast.LENGTH_SHORT);
                    }

                }
            });
        }
    }
    private void showError(EditText input, String s){
        input.setError(s);
        input.requestFocus();
    }

}