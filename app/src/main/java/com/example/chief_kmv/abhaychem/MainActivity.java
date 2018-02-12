package com.example.chief_kmv.abhaychem;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void OrderSubmit(View view) {
        Intent getForm=new Intent(this,SecondActivity.class);
        startActivity(getForm);
    }
    public void RawSubmit(View view) {
        Intent getForm=new Intent(this,ThirdActivity.class);
        startActivity(getForm);
    }
    public void ExpSubmit(View view) {
        Intent getForm=new Intent(this,FourthActivity.class);
        startActivity(getForm);
    }
}
