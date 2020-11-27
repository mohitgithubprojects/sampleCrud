package com.example.samplecrud;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    EditText name,age,email,add,fath;
    CardView button,signout;
    private static final int PERMISSION_CODE = 100;
    Boolean perm = false;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseUser firebaseUser;
    ProgressBar progressBar;
    ConnectivityManager connectivityManager;
    NetworkInfo networkInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name = findViewById(R.id.nameEdt);
        age = findViewById(R.id.ageEdt);
        email = findViewById(R.id.emailEdt);
        add = findViewById(R.id.addEdt);
        fath = findViewById(R.id.fathEdt);
        button = findViewById(R.id.createBtn);
        signout = findViewById(R.id.signoutBtn);
        progressBar = findViewById(R.id.progress);
        progressBar.setVisibility(View.VISIBLE);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(MainActivity.this,LoginActivity.class));
                finish();
            }
        });
        // Storage Permission
        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (perm){
                    createPdf();
                }else {
                    checkPermission(PERMISSION_CODE);
                }
            }
        });

        // Check for Internet
        connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo == null || !networkInfo.isAvailable() || !networkInfo.isConnected()){
            Toast.makeText(MainActivity.this,"Internet not Connected !",Toast.LENGTH_LONG).show();
            progressBar.setVisibility(View.GONE);
        }

        // Firebase Db

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        databaseReference.keepSynced(true);
        readData();

    }
    private void readData(){
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.child("Users").child(firebaseUser.getPhoneNumber()).getValue(User.class);
                if (user == null){}
                else {
                    name.setText(user.getName());
                    age.setText(user.getAge());
                    email.setText(user.getEmail());
                    add.setText(user.getAddress());
                    fath.setText(user.getFather());
                }
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void writeNewUser(String userId, String name, String email, String age, String address, String father) {
        User user = new User(name, age, email, address, father);
        databaseReference.child("Users").child(userId).setValue(user);
    }
    public void createPdf(){

        PdfDocument pdfDocument = new PdfDocument();
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(300,600,1).create();
        PdfDocument.Page page = pdfDocument.startPage(pageInfo);

        Paint paint = new Paint();
        String namee = "Name :- "+name.getText().toString();
        String agee = "Age :- "+age.getText().toString();
        String emaill = "Email-Id :- "+email.getText().toString();
        String addd = "Address :- "+add.getText().toString();
        String fathh = "Father's Name :- "+fath.getText().toString();

        writeNewUser(firebaseUser.getPhoneNumber(), name.getText().toString(), age.getText().toString(), email.getText().toString(),
                add.getText().toString(), fath.getText().toString());

        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add(namee);
        arrayList.add(agee);
        arrayList.add(emaill);
        arrayList.add(addd);
        arrayList.add(fathh);
        int x=10,y=25;

        for (int i=0;i<arrayList.size();i++){
            page.getCanvas().drawText(arrayList.get(i), x, y, paint);
            y += paint.descent() - paint.ascent();
        }
        pdfDocument.finishPage(page);

        String directory_path = Environment.getExternalStorageDirectory().getPath() + "/SampleCrud/";
        File file = new File(directory_path);
        if (!file.exists()) {
            file.mkdirs();
        }

        String filePath = directory_path + "Resume.pdf";
        File filee = new File(filePath);
        try {
            pdfDocument.writeTo(new FileOutputStream(filee));
            Toast.makeText(MainActivity.this,"Resume saved to :- "+filePath,Toast.LENGTH_SHORT).show();
        }catch (Exception ex){
            ex.printStackTrace();
            Toast.makeText(MainActivity.this,"Error !",Toast.LENGTH_SHORT).show();
        }
        pdfDocument.close();
    }
    public void checkPermission(int requestCode)
    {
        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.READ_EXTERNAL_STORAGE) + ContextCompat
                .checkSelfPermission(MainActivity.this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {



            // Requesting the permission
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    requestCode);
        }
        else {
            createPdf();
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults)
    {
        super
                .onRequestPermissionsResult(requestCode,
                        permissions,
                        grantResults);
        switch (requestCode) {
            case PERMISSION_CODE:
                if (grantResults.length > 0) {
                    boolean cameraPermission = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    boolean recordAudio = grantResults[0] == PackageManager.PERMISSION_GRANTED;

                    if(cameraPermission && recordAudio)
                    {
                        perm = true;
                        createPdf();
                    } else {
                        perm = false;
                        Toast.makeText(MainActivity.this,
                                "Storage Permission Denied",
                                Toast.LENGTH_SHORT)
                                .show();
                    }
                }
                break;
        }
    }
}