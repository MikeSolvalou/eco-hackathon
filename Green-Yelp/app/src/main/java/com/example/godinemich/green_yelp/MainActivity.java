package com.example.godinemich.green_yelp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //test comment
        mainMenu();
    }


    public void mainMenu(){
        setContentView(R.layout.activity_main);     //splash screen

        //connect splash screen buttons
        Button btn1=(Button)findViewById(R.id.button);
        btn1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                TextView txt=(TextView)findViewById(R.id.txt);
                txt.setText("Pressed");
            }
        });

    }

}
