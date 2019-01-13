package com.iut.catchpoint.catchpoint;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class SettingsActivity extends AppCompatActivity {

    private ImageView arrow_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        arrow_back = findViewById(R.id.arrow_back);
    }

    public void goBack(View view){
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }

    public void disconnect(View view){
        Intent intent = new Intent(this, LogActivity.class);
        startActivity(intent);
    }

    public void showProfil(View view){
        Intent intent = new Intent(this, ProfilActivity.class);
        startActivity(intent);
    }
}
