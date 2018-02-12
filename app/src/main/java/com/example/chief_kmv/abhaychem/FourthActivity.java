package com.example.chief_kmv.abhaychem;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

/**
 * Created by chief_kmv on 29/12/17.
 */

public class FourthActivity extends Activity {
    private DatabaseReference firebase;
    private Button upload;
    private EditText cash;
    private EditText cost;
    private EditText desc;
    private EditText date;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fourth_layout);
        upload=findViewById(R.id.upload);
        cash=findViewById(R.id.cash);
        cost=findViewById(R.id.cost);
        desc=findViewById(R.id.description);
        date=findViewById(R.id.date);
        firebase= FirebaseDatabase.getInstance().getReference().child("Expenses Details");
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String exp_cost=cost.getText().toString().trim();
                final String exp_cash=cash.getText().toString().trim();
                final String description=desc.getText().toString().trim();
                final String exp_date=date.getText().toString().trim();
                boolean fetched=false;
                String fet_bool=Boolean.toString(fetched);
                HashMap<String,String> datamap=new HashMap<String, String>();
                datamap.put("Cost:",exp_cost);
                datamap.put("Cash in Hand",exp_cash);
                datamap.put("Description",description);
                datamap.put("Date",exp_date);
                datamap.put("Fetched",fet_bool);
                firebase.push().setValue(datamap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (exp_cash.isEmpty() || exp_cost.isEmpty() || description.isEmpty() || exp_date.isEmpty()) {
                            Toast.makeText(FourthActivity.this, "Please Enter all the values.....", Toast.LENGTH_LONG).show();
                        } else if (task.isSuccessful()) {
                            Toast.makeText(FourthActivity.this, "Stored.....", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(FourthActivity.this, "Error.....", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });
    }
    public void OnBack(View view) {
        Intent getForm=new Intent(this,MainActivity.class);
        startActivity(getForm);
    }
}
