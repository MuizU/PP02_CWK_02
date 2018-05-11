package com.service.sos.alpha;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    EditText et_email, et_password;
    private FirebaseAuth auth;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        et_email = findViewById(R.id.email);
        et_password = findViewById(R.id.password);
        auth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);

    }

    public void signIn(View view) {
        String email = et_email.getText().toString();
        String password = et_password.getText().toString();
        progressDialog.setMessage("Signing in....");
        progressDialog.show();


    }
}
