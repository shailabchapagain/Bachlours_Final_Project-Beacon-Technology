package com.example.ipbeacons_mobile_app.ui.Info_Page;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ipbeacons_mobile_app.R;

public class InfopageActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_page);
        TextView textView = findViewById(R.id.messageText);

        textView.setText(getIntent().getStringExtra("information"));
        textView.setMovementMethod(new ScrollingMovementMethod());



    }
}
