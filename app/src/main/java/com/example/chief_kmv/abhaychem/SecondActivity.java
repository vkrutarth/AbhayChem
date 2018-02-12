package com.example.chief_kmv.abhaychem;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.DatePicker;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import android.app.DatePickerDialog;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

/**
 * Created by chief_kmv on 29/12/17.
 */

public class SecondActivity extends AppCompatActivity  {
    private Button firebase_btn;
    private DatabaseReference firebase;
    private EditText prod_name;
    private EditText amount;
    private EditText area;
    private EditText del_date;
    private EditText order_id;
    private Button order_date;
    private TextView kmv;
    Calendar cal;
    DatePickerDialog d1;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_layout);
        firebase_btn=(Button)findViewById(R.id.firebase_btn);
        amount=(EditText)findViewById(R.id.quantity);
        prod_name=(EditText)findViewById(R.id.product);
        area=(EditText)findViewById(R.id.village);
        del_date=(EditText)findViewById(R.id.date_box);
        order_id=findViewById(R.id.orderid);
        order_date=findViewById(R.id.order_date_box);
        kmv=findViewById(R.id.abc);


        order_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cal=Calendar.getInstance();
                int day=cal.get(Calendar.DAY_OF_MONTH);
                int month=cal.get(Calendar.MONTH);
                int year=cal.get(Calendar.YEAR);

                d1=new DatePickerDialog(SecondActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        kmv.setText(dayOfMonth +"/"+(month+1) +"/" + year);
                    }
                },day,month,year);
                d1.show();
            }
        });



        firebase= FirebaseDatabase.getInstance().getReference().child("Order Details");


        firebase_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                Date today = Calendar.getInstance().getTime();
                String order_date = df.format(today);
                final String name = prod_name.getText().toString().trim();
                final String quantity = amount.getText().toString().trim();
                String id1 = order_id.getText().toString().trim();
                int strid1 = Integer.parseInt(id1);
                int strid2 = strid1 * 10000 + year;
                final String id2 = Integer.toString(strid2);
                final String village = area.getText().toString().trim();
                final String date = del_date.getText().toString().trim();
                boolean delivery = false;
                boolean fetched = false;
                String del_bool = Boolean.toString(delivery);
                String fet_bool = Boolean.toString(fetched);
                HashMap<String, String> datamap = new HashMap<String, String>();
                datamap.put("Id", id2);
                datamap.put("Customer Name", "Customer");
                datamap.put("Mob No", "+91XXXXXXXXXX");
                datamap.put("Product Name", name);
                datamap.put("Quantity", quantity);
                datamap.put("Village", village);
                datamap.put("Order Date", order_date);
                datamap.put("Delivery Date", date);
                datamap.put("Delivered", del_bool);
                datamap.put("Fetched", fet_bool);
                if (id2.isEmpty() || name.isEmpty() || quantity.isEmpty() || village.isEmpty() || date.isEmpty()) {
                    Toast.makeText(SecondActivity.this, "Please Enter all the values.....", Toast.LENGTH_LONG).show();
                } else{
                    firebase.push().setValue(datamap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(SecondActivity.this, "Stored.....", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(SecondActivity.this, "Error.....", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }
        });
    }


    public void OnBack(View view) {
        Intent getForm=new Intent(this,MainActivity.class);
        startActivity(getForm);
    }
}
