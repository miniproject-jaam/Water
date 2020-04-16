package com.example.water;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.app.ProgressDialog;
import android.content.Intent;
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

public class CustomerLoginRegisterActivity extends AppCompatActivity {
    private TextView CreateCustomerAccount;
    private TextView TitleCustomer;
    private Button LoginCustomerButton;
    private Button RegisterCustomerButton;
    private EditText CustomerEmail;
    private EditText CustomerPassword;
    //    private DatabaseReference customersDatabaseRef;
    private FirebaseAuth mAuth;
    //    private FirebaseAuth.AuthStateListener firebaseAuthListner;
    private ProgressDialog loadingBar;
//    private FirebaseUser currentUser;
//    String currentUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_login_register);

        mAuth = FirebaseAuth.getInstance();

//        firebaseAuthListner = new FirebaseAuth.AuthStateListener() {
//            @Override
//            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth)
//            {
//                currentUser = FirebaseAuth.getInstance().getCurrentUser();
//
//                if(currentUser != null)
//                {
//                    Intent intent = new Intent(CustomerLoginRegisterActivity.this, CustomersMapActivity.class);
//                    startActivity(intent);
//                }
//            }
//        };

        CreateCustomerAccount = findViewById(R.id.customer_register_link);
        TitleCustomer = findViewById(R.id.customer_status);
        LoginCustomerButton = findViewById(R.id.customer_login_btn);
        RegisterCustomerButton = findViewById(R.id.customer_register_btn);
        CustomerEmail = findViewById(R.id.customer_email);
        CustomerPassword = findViewById(R.id.customer_password);
        loadingBar = new ProgressDialog(this);

        RegisterCustomerButton.setVisibility(View.INVISIBLE);
        RegisterCustomerButton.setEnabled(false);

        CreateCustomerAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CreateCustomerAccount.setVisibility(View.INVISIBLE);
                LoginCustomerButton.setVisibility(View.INVISIBLE);
                TitleCustomer.setText("Customer Registration");

                RegisterCustomerButton.setVisibility(View.VISIBLE);
                RegisterCustomerButton.setEnabled(true);
            }
        });
        RegisterCustomerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = CustomerEmail.getText().toString();
                String password = CustomerPassword.getText().toString();
                RegisterCustomer(email, password);

            }
        });


        LoginCustomerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                String email = CustomerEmail.getText().toString();
                String password = CustomerPassword.getText().toString();

                SignInCustomer(email, password);
            }
        });
    }

    private void SignInCustomer(String email, String password) {
        if(TextUtils.isEmpty(email))
        {
            Toast.makeText(CustomerLoginRegisterActivity.this, "Please write your Email...", Toast.LENGTH_SHORT).show();
        }

        if(TextUtils.isEmpty(password))
        {
            Toast.makeText(CustomerLoginRegisterActivity.this, "Please write your Password...", Toast.LENGTH_SHORT).show();
        }

        else
        {
            loadingBar.setTitle("Customer Login :");
            loadingBar.setMessage("Please wait,while we are checking your credentials...");
            loadingBar.show();

            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task)
                {
                    if(task.isSuccessful())
                    {
                        Toast.makeText(CustomerLoginRegisterActivity.this, "Sign In , Successful...", Toast.LENGTH_SHORT).show();

//                                Intent intent = new Intent(CustomerLoginRegisterActivity.this, CustomersMapActivity.class);
//                                startActivity(intent);

                        loadingBar.dismiss();
                    }
                    else
                    {
                        Toast.makeText(CustomerLoginRegisterActivity.this, "Error Occurred, while Signing In... ", Toast.LENGTH_SHORT).show();

                        loadingBar.dismiss();
                    }
                }
            });
        }
    }

    private void RegisterCustomer(String email, String password) {

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(CustomerLoginRegisterActivity.this, "Please write your Email...", Toast.LENGTH_SHORT).show();
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(CustomerLoginRegisterActivity.this, "Please write your Password...", Toast.LENGTH_SHORT).show();
        } else {
            loadingBar.setTitle("Customer Registration :");
            loadingBar.setMessage("Please wait while we register you...");
            loadingBar.show();

            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(CustomerLoginRegisterActivity.this, "Customer register successful ", Toast.LENGTH_SHORT).show();
//                        currentUserId = mAuth.getCurrentUser().getUid();
//                        customersDatabaseRef = FirebaseDatabase.getInstance().getReference().child("Users").child("Customers").child(currentUserId);
//                        customersDatabaseRef.setValue(true);
//
//                        Intent intent = new Intent(CustomerLoginRegisterActivity.this, CustomersMapActivity.class);
//                        startActivity(intent);

                        loadingBar.dismiss();
                    } else {
                        Toast.makeText(CustomerLoginRegisterActivity.this, "Please Try Again. Error Occurred, while registering... ", Toast.LENGTH_SHORT).show();

                        loadingBar.dismiss();
                    }
                }
            });
        }
    }
}
