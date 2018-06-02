package com.service.sos.alpha.login;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.service.sos.alpha.MainActivity;
import com.service.sos.alpha.R;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.service.sos.alpha.chat.model.User;

public class SignupActivity extends AppCompatActivity {

    private EditText inputEmail, inputPassword, inputConfirmPassword, fName, lName,input_NIC, mobNumber;
    private Button  btnSignUp,signInButton;
    private ProgressBar progressBar;
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth mAuth;
    private DatabaseReference myRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) throws NumberFormatException {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        //Get Firebase auth instance
        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();
        btnSignUp = findViewById(R.id.sign_up_button);
        inputEmail = findViewById(R.id.sign_up_email);
        inputPassword = findViewById(R.id.sign_up_Password);
        inputConfirmPassword = findViewById(R.id.confirm_password);
        fName = findViewById(R.id.f_name);
        lName = findViewById(R.id.l_name);
        input_NIC = findViewById(R.id.sign_up_enter_nic);
        mobNumber = findViewById(R.id.mob_number);
        signInButton = findViewById(R.id.sign_in_button);
        progressBar = findViewById(R.id.sign_up_progressBar);



            btnSignUp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) throws NumberFormatException {
                    String firstName = fName.getText().toString().trim();
                    String lastName = lName.getText().toString().trim();
                    String email = inputEmail.getText().toString().trim();
                    String password = inputPassword.getText().toString().trim();
                    String cPassword = inputConfirmPassword.getText().toString().trim();
                    String mobileNumber = mobNumber.getText().toString();
                    final String nIC = input_NIC.getText().toString().trim();
                     if (TextUtils.isEmpty(mobileNumber)&&TextUtils.isEmpty(firstName)&&TextUtils.isEmpty(lastName)&&TextUtils.isEmpty(email)&&TextUtils.isEmpty(password)&&TextUtils.isEmpty(cPassword)){
                        toastMessage("Please Fill All Fields!");
                        return;
                    }
                    if (mobileNumber.length()!=10||mobileNumber.charAt(0)!='0'|| mobileNumber.charAt(1)!='7'){
                         toastMessage("Please enter a valid mobile number!");
                         return;
                    }

                     if (TextUtils.isEmpty(mobileNumber)){
                         toastMessage("Enter a mobile number!");
                         return;
                    }
                    if (mobileNumber.length() != 10 ) {
                         toastMessage("Enter a valid mobile number!");
                    return;
                    }


                    if (nIC.length() != 10) {
                        toastMessage("Enter a valid NIC/Passport ID!");
                        return;
                    }
                    if (TextUtils.isEmpty(nIC)) {
                        toastMessage("Enter a  NIC/Passport ID!");
                        return;
                    }
                    if (TextUtils.isEmpty(firstName)) {
                        toastMessage("Enter your First Name!");
                        return;
                    }
                    if (TextUtils.isEmpty(lastName)) {
                        toastMessage("Enter your Last Name!");
                        return;
                    }

                    if (!password.equals(cPassword)) {
                        toastMessage("Passwords do not match. Please enter matching passwords!");
                        return;
                    }
                    if (TextUtils.isEmpty(email)) {
                        toastMessage("Enter email address!");
                        return;
                    }

                    if (TextUtils.isEmpty(password)) {
                        toastMessage("Enter password!");
                        return;
                    }

                    if (password.length() < 8) {
                        toastMessage("Password too short, enter minimum 6 characters!");
                        return;
                    }

                    progressBar.setVisibility(View.VISIBLE);
                    //create userAccount
                    final UserAccount userAccount = new UserAccount(firstName+" "+lastName, email, mobileNumber, nIC);
                    mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            mAuth.getCurrentUser().sendEmailVerification();

                            if (!task.isSuccessful()) {
                                Toast.makeText(SignupActivity.this, "Authentication failed." + task.getException(),
                                        Toast.LENGTH_SHORT).show();
                            } else {
                                FirebaseUser user = task.getResult().getUser();
                                AddUserInfo(userAccount,myRef,user.getUid());
                                Toast.makeText(SignupActivity.this, "User is Added", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(SignupActivity.this, MainActivity.class));
                                finish();
                            }
                        }
                    });




                }
            });
            signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignupActivity.this, LoginActivity.class));
            }
        });
    }
    protected  void toastMessage(String message){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }
    private void AddUserInfo(UserAccount userAccount, DatabaseReference mDatabaseReference,String uid) {
        mDatabaseReference.child("user").child(uid).setValue(userAccount);
    }
    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }
}