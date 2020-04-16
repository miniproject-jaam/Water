package com.example.water;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.app.ProgressDialog;
import android.content.Intent;
import androidx.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DriverLoginRegisterActivity extends AppCompatActivity
{
    private TextView CreateDriverAccount;
    private TextView TitleDriver;
    private Button LoginDriverButton;
    private Button RegisterDriverButton;
    private EditText DriverEmail;
    private EditText DriverPassword;
//    private DatabaseReference driversDatabaseRef;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthListner;
    private ProgressDialog loadingBar;
//    private FirebaseUser currentUser;
//    String currentUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_login_register);

        mAuth = FirebaseAuth.getInstance();
//        firebaseAuthListner = new FirebaseAuth.AuthStateListener() {
//            @Override
//            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth)
//            {
//                currentUser = FirebaseAuth.getInstance().getCurrentUser();
//
//                if(currentUser != null)
//                {
//                    Intent intent = new Intent(DriverLoginRegisterActivity.this, DriverMapActivity.class);
//                    startActivity(intent);
//                }
//            }
//        };

        CreateDriverAccount = findViewById(R.id.create_driver_account);
        TitleDriver = findViewById(R.id.titlr_driver);
        LoginDriverButton = findViewById(R.id.login_driver_btn);
        RegisterDriverButton = findViewById(R.id.register_driver_btn);
        DriverEmail = findViewById(R.id.driver_email);
        DriverPassword = findViewById(R.id.driver_password);
        loadingBar = new ProgressDialog(this);


        RegisterDriverButton.setVisibility(View.INVISIBLE);
        RegisterDriverButton.setEnabled(false);

        CreateDriverAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                CreateDriverAccount.setVisibility(View.INVISIBLE);
                LoginDriverButton.setVisibility(View.INVISIBLE);
                TitleDriver.setText("Driver Registration");

                RegisterDriverButton.setVisibility(View.VISIBLE);
                RegisterDriverButton.setEnabled(true);
            }
        });


        RegisterDriverButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                String email = DriverEmail.getText().toString();
                String password = DriverPassword.getText().toString();

                RegisterDriver(email,password);


            }
        });

        LoginDriverButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                String email = DriverEmail.getText().toString();
                String password = DriverPassword.getText().toString();
                SignInDriver(email,password);
            }
        });
    }

    private void SignInDriver(String email, String password) {
        if(TextUtils.isEmpty(email))
        {
            Toast.makeText(DriverLoginRegisterActivity.this, "Please write your Email...", Toast.LENGTH_SHORT).show();
        }

        if(TextUtils.isEmpty(password))
        {
            Toast.makeText(DriverLoginRegisterActivity.this, "Please write your Password...", Toast.LENGTH_SHORT).show();
        }

        else
        {
            loadingBar.setTitle("Driver Login :");
            loadingBar.setMessage("Please wait,while we are checking your credentials...");
            loadingBar.show();

            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task)
                {
                    if(task.isSuccessful())
                    {
                        Toast.makeText(DriverLoginRegisterActivity.this, "Sign In , Successful...", Toast.LENGTH_SHORT).show();

//                        Intent intent = new Intent(DriverLoginRegisterActivity.this, DriverMapActivity.class);
//                        startActivity(intent);
                    }
                    else
                    {
                        Toast.makeText(DriverLoginRegisterActivity.this, "Error Occurred, while Signing In... ", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void RegisterDriver(String email, String password) {
        if(TextUtils.isEmpty(email))
        {
            Toast.makeText(DriverLoginRegisterActivity.this, "Please write your Email...", Toast.LENGTH_SHORT).show();
        }

        if(TextUtils.isEmpty(password))
        {
            Toast.makeText(DriverLoginRegisterActivity.this, "Please write your Password...", Toast.LENGTH_SHORT).show();
        }

        else
        {
                    loadingBar.setTitle("Driver Registration");
                    loadingBar.setMessage("Please wait, while we register your data...");
                    loadingBar.show();

            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task)
                {
                    if(task.isSuccessful())
                    {
                        Toast.makeText(DriverLoginRegisterActivity.this, "Driver register successful ", Toast.LENGTH_SHORT).show();

//                                currentUserId = mAuth.getCurrentUser().getUid();
//                                driversDatabaseRef = FirebaseDatabase.getInstance().getReference().child("Users").child("Drivers").child(currentUserId);
//                                driversDatabaseRef.setValue(true);
//
//                                Intent intent = new Intent(DriverLoginRegisterActivity.this, DriverMapActivity.class);
//                                startActivity(intent);
                                loadingBar.dismiss();
                    }
                    else
                    {
                        Toast.makeText(DriverLoginRegisterActivity.this, "Please Try Again. Error Occurred, while registering... ", Toast.LENGTH_SHORT).show();

                                loadingBar.dismiss();
                    }
                }
            });
        }
    }


//    @Override
//    protected void onStart()
//    {
//        super.onStart();
//
//        mAuth.addAuthStateListener(firebaseAuthListner);
//    }
//
//
//    @Override
//    protected void onStop()
//    {
//        super.onStop();
//
//        mAuth.removeAuthStateListener(firebaseAuthListner);
//    }
}
