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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by chief_kmv on 29/12/17.
 */

public class ThirdActivity extends Activity{
    private Button upload;
    private EditText raw;
    private EditText weight;
    private EditText gate;
    private EditText desc;
    private DatabaseReference firebase;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.third_activity);
        upload=(Button)findViewById(R.id.upload);
        raw=(EditText)findViewById(R.id.prod_name);
        weight=findViewById(R.id.weight);
        gate=findViewById(R.id.gate);
        desc=findViewById(R.id.desc);
        firebase= FirebaseDatabase.getInstance().getReference().child("Raw Details");
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String name=raw.getText().toString().trim();
                final String description=desc.getText().toString().trim();
                final String gatepass=gate.getText().toString().trim();
                final String weight_raw=weight.getText().toString().trim();
                boolean delivery=false;
                String del_bool=Boolean.toString(delivery);
                SimpleDateFormat df=new SimpleDateFormat("dd/MM/yyyy");
                Date today= Calendar.getInstance().getTime();
                boolean fetched=false;
                String fet_bool=Boolean.toString(fetched);
                String order_date=df.format(today);
                HashMap<String,String> datamap=new HashMap<String, String>();
                datamap.put("GatePass",gatepass);
                datamap.put("Raw Material",name);
                datamap.put("Description",description);
                datamap.put("Weight",weight_raw);
                datamap.put("Order Date:",order_date);
                datamap.put("Delivery",del_bool);
                datamap.put("Fetched",fet_bool);
                firebase.push().setValue(datamap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (gatepass.isEmpty() || name.isEmpty() || weight_raw.isEmpty() || description.isEmpty()) {
                            Toast.makeText(ThirdActivity.this, "Please Enter all the values.....", Toast.LENGTH_LONG).show();
                        } else if (task.isSuccessful()) {
                            Toast.makeText(ThirdActivity.this, "Stored.....", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(ThirdActivity.this, "Error.....", Toast.LENGTH_LONG).show();
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
