package com.test.mycommentapic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    Button btn_submit,btn_fetch;
    TextView txt_comments_output;
    EditText txt_name,txt_comment;
    FirebaseFirestore firebaseFirestore;
    DocumentReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firebaseFirestore=FirebaseFirestore.getInstance();
        ref = firebaseFirestore.collection("comments").document();

        txt_comment=findViewById(R.id.txt_comment);
        txt_name=findViewById(R.id.txt_name);
        txt_comments_output=findViewById(R.id.textview_comments);
        btn_fetch=findViewById(R.id.btn_fetchcomment);
        btn_submit=findViewById(R.id.btn_submit);
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (txt_name.getText().toString().equals("") || txt_comment.getText().toString().equals("")) {
                    Toast.makeText(MainActivity.this, "Please inout all fields", Toast.LENGTH_LONG).show();
                } else {
                add_comment();

                }
            }
        });
        btn_fetch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fetch_comments();

            }
        });

    }

    public  void add_comment(){
        ref.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {


                    Map<String, Object> reg_entry = new HashMap<>();
                    reg_entry.put("Name", txt_name.getText().toString());
                    reg_entry.put("Comment", txt_comment.getText().toString());
                    firebaseFirestore.collection("comments")
                            .add(reg_entry)
                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    Toast.makeText(MainActivity.this, "Comment Successfully added", Toast.LENGTH_SHORT).show();
                                    txt_name.setText("");
                                    txt_comment.setText("");
                                    txt_comment.requestFocus();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d("Error", e.getMessage());
                                }
                            });
                }

        });
    }
    public void fetch_comments(){
        firebaseFirestore.collection("comments").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
           if(task.isComplete()){
               String result="";
               StringBuilder stringBuilder = new StringBuilder();
            for (DocumentSnapshot documentSnapshot : task.getResult()){
                Model model =documentSnapshot.toObject(Model.class);
                stringBuilder.append("------------------------------------------------------------"+"\n");
                stringBuilder.append(model.getName()+" = "+model.getComment()+"\n");
                stringBuilder.append("----------------------------------------------------------"+"\n");
            }
            txt_comments_output.setText(stringBuilder.toString());


           }


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_SHORT).show();

            }
        });
    }
}