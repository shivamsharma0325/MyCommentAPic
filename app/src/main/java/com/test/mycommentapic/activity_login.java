package com.test.mycommentapic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import javax.annotation.Nullable;

public class activity_login extends AppCompatActivity implements View.OnClickListener {
    Button login;
    Button register;
    EditText pwd;
    TextView email;
    ProgressBar progress;
    FirebaseFirestore db;
    public  boolean auth=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email=findViewById(R.id.txt_email);
        pwd=findViewById(R.id.txt_pwd);
        progress=findViewById(R.id.prg_bar);
        login = findViewById(R.id.btn_login);
        register = findViewById(R.id.btn_register);

        db= FirebaseFirestore.getInstance();
        login.setOnClickListener(this);
        register.setOnClickListener(this);

    }

    public void onClick(View v){
        switch(v.getId()){
            case R.id.btn_login:
                if(email.getText().toString().equals("")){
                    Toast.makeText(activity_login.this, "Please enter valid email", Toast.LENGTH_SHORT).show();
                }else if( pwd.getText().toString().equals("")){
                    Toast.makeText(activity_login.this, "Please enter valid password", Toast.LENGTH_SHORT).show();
                }
                db.collection("users")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if(task.isSuccessful()){
                                    for(QueryDocumentSnapshot doc : task.getResult()){
                                        if(email.getText().toString().equalsIgnoreCase(doc.getString("Email")) & pwd.getText().toString().equalsIgnoreCase(doc.getString("Password"))) {
                                            Toast.makeText(activity_login.this,  "Success!" , Toast.LENGTH_SHORT).show();
                                            finish();
                                            Intent home = new Intent(activity_login.this, MainActivity.class);
                                            startActivity(home);
                                            auth=true;
                                              break;

                                        }
                                        else
                                           auth=false;
                                    }
                                    if(!auth)
                                    Toast.makeText(activity_login.this, "Invalid Login Credentials", Toast.LENGTH_SHORT).show();


                                }
                            }
                        });


                break;

            case R.id.btn_register:
                Intent register_view=new Intent(activity_login.this,activity_register.class);
                startActivity(register_view);
                break;
        }
    }



}