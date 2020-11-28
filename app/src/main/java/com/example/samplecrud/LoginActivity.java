package com.example.samplecrud;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class LoginActivity extends AppCompatActivity {
    EditText editText,otpedt;
    FirebaseAuth mAuth;
    TextView textView;
    CardView login;
    String codeBySystem;
    FirebaseAuth firebaseAuth;
    String id_token;
    Boolean otpSend = false;

    @Override
    protected void onStart() {
//        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//        if (user == null){}
//        else {
//            startActivity(new Intent(LoginActivity.this,MainActivity.class));
//            finish();
//        }
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

//        editText = findViewById(R.id.editTextPhone);
//        otpedt = findViewById(R.id.editTextOtp);
//        login = findViewById(R.id.signinBtn);
//        textView = findViewById(R.id.textVieww);
//        mAuth = FirebaseAuth.getInstance();
//        otpedt.setEnabled(false);
//
//        login.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (otpSend){
//                    String code = otpedt.getText().toString();
//                    if (!code.isEmpty()){
//                        verifyCode(code);
//                    }
//                }else {
//                    if (!editText.getText().toString().isEmpty()){
//                        if (editText.getText().length() != 10){
//                            Toast.makeText(LoginActivity.this,"Please Enter a valid phone No. !",Toast.LENGTH_SHORT).show();
//                        }else {
//                            String phoneNumber = "+91"+editText.getText().toString();
//                            otpedt.setEnabled(true);
//                            textView.setText("Login");
//                            sendVerificationCodeToUser(phoneNumber);
//                        }
//                    }
//                }
//            }
//        });
//    }
//    private void sendVerificationCodeToUser(String mobileNo) {
//        otpSend = true;
//        PhoneAuthOptions options =
//                PhoneAuthOptions.newBuilder(FirebaseAuth.getInstance())
//                        .setPhoneNumber(mobileNo)       // Phone number to verify
//                        .setTimeout(120L, TimeUnit.SECONDS) // Timeout and unit
//                        .setActivity(LoginActivity.this)                 // Activity (for callback binding)
//                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
//                        .build();
//        PhoneAuthProvider.verifyPhoneNumber(options);
//    }
//
//    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
//        @Override
//        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
//            super.onCodeSent(s, forceResendingToken);
//            codeBySystem = s;
//            System.out.println("code sent");
//        }
//
//        @Override
//        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
//            System.out.println("lllll");
//            String code = phoneAuthCredential.getSmsCode();
//            if (code!=null){
//                otpedt.setText(code);
//                verifyCode(code);
//            }
//
//        }
//
//        @Override
//        public void onVerificationFailed(@NonNull FirebaseException e) {
//
////            Toast.makeText(Login_Otp.this,e.getMessage(),Toast.LENGTH_LONG).show();
//            System.out.println(e);
//
//        }
//    };
//
//    private void verifyCode(String code) {
//        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(codeBySystem,code);
//        signInWithPhoneAuthCredential(credential);
//    }
//
//    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
//        firebaseAuth = FirebaseAuth.getInstance();
//        firebaseAuth.signInWithCredential(credential)
//                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()) {
//                            getToken();
//                        } else {
//                            // Sign in failed, display a message and update the UI
//                            Log.w("TAG", "signInWithCredential:failure", task.getException());
//                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
//                                Toast.makeText(LoginActivity.this,"Verification incomplete !",Toast.LENGTH_SHORT).show();
//                                // The verification code entered was invalid
//                            }
//                        }
//                    }
//                });
//    }
//    private void getToken(){
//        FirebaseAuth firebaseAuth1 = FirebaseAuth.getInstance();
//        FirebaseUser firebaseUser = firebaseAuth1.getCurrentUser();
//        firebaseUser.getIdToken(true).addOnSuccessListener(new OnSuccessListener<GetTokenResult>() {
//            @Override
//            public void onSuccess(GetTokenResult getTokenResult) {
//                id_token = getTokenResult.getToken();
//                System.out.println(id_token);
//                startActivity(new Intent(LoginActivity.this,MainActivity.class));
//            }
//        });

    }
}