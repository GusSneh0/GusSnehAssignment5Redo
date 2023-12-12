package com.example.assignment5;



import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class ProfileListView extends AppCompatActivity {
    private TextView TextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_list_view);
        TextView = findViewById(R.id.profileTV);
    }
}
