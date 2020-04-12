package com.example.booklister;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = (Button)findViewById(R.id.button_search);
        final EditText editText = (EditText)findViewById(R.id.text_search);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String search =  editText.getText().toString();
                Intent intent = new Intent(MainActivity.this, ListingActivity.class);
                intent.putExtra("text", search);
                startActivity(intent);
            }
        });
    }
}
