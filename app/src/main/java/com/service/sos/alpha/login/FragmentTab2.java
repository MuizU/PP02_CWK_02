package com.service.sos.alpha.login;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.service.sos.alpha.MainActivity;
import com.service.sos.alpha.R;

public class FragmentTab2 extends Fragment {
    private EditText inputEmail, inputPassword;
    private FirebaseAuth auth;
    private ProgressBar progressBar;
    private Button btnSignup, btnLogin, btnReset;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login_tab2,container,false);
        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();

        if (auth.getCurrentUser() != null) {
            startActivity(new Intent(getActivity(), MainActivity.class));
            getActivity().finish();
        }
        inputEmail =  view.findViewById(R.id.friend_email);
        inputPassword =  view.findViewById(R.id.friend_password);
        progressBar =  view.findViewById(R.id.friend_progressBar);
        btnSignup =  view.findViewById(R.id.friend_btn_signup);
        btnLogin =  view.findViewById(R.id.friend_btn_login);
        btnReset =  view.findViewById(R.id.friend_btn_reset_password);
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), SignupActivity.class));
            }
        });
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ResetPasswordActivity.class));
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = inputEmail.getText().toString();
                final String password = inputPassword.getText().toString();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getActivity().getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getActivity().getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                //authenticate user
                auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                progressBar.setVisibility(View.GONE);
                                if (!task.isSuccessful()) {
                                    // there was an error
                                    if (password.length() < 6) {
                                        inputPassword.setError(getString(R.string.minimum_password));
                                    } else {
                                        Toast.makeText(getContext(), getString(R.string.auth_failed), Toast.LENGTH_LONG).show();
                                    }
                                } else {
                                    if (auth.getCurrentUser().isEmailVerified()) {
                                    DatabaseReference mDatabaseReference = FirebaseDatabase.getInstance().getReference("user");
                                    DatabaseReference databaseReference = mDatabaseReference.child(auth.getCurrentUser().getUid());
                                    DatabaseReference ownerTypeReference = databaseReference.child("account_type");
                                    ownerTypeReference.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            if (dataSnapshot.getValue(String.class).equals("Friend")||dataSnapshot.getValue(String.class).equals("Dual")){
                                                Toast.makeText(getContext(),"Login Success!",Toast.LENGTH_SHORT).show();
                                                startActivity(new Intent(getActivity(),MainActivity.class));
                                            }else if (dataSnapshot.getValue(String.class).equals("Owner")){
                                                Toast.makeText(getContext(),"Invalid Account Type!\nPlease go to the Owners Login Tab!",Toast.LENGTH_SHORT).show();

                                            }
                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {

                                        }
                                    });

                                    }else {
                                        new AlertDialog.Builder(getContext()).setTitle("Email not verified!").setMessage("Please verify your email to continue!").setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                return;
                                            }
                                        }).setNegativeButton("Sign up", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                startActivity(new Intent(getActivity(),SignupActivity.class));
                                            }
                                        }).show();
                                    }
                                }

                            }
                        });
            }
        });
        return view;
    }

}
