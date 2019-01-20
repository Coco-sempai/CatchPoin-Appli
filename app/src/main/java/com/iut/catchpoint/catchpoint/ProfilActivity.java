package com.iut.catchpoint.catchpoint;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.TextView;

public class ProfilActivity extends AppCompatActivity implements DialogProfil.DialogProfilListener {

    private ImageView arrow_back;

    private TextView textViewUsername;
    private Button button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);

        arrow_back = findViewById(R.id.arrow_back);
        TableRow row = findViewById(R.id.nomDialog);

        textViewUsername = (TextView) findViewById(R.id.text_nom_alert);

        row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });

    }

    public void openDialog(){
        DialogProfil dialogProfil = new DialogProfil();

        dialogProfil.show(getSupportFragmentManager(), "Dialogue profil");
    }

    public void goBack(View view){
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }

    @Override
    public void applyTexts(String username) {
        textViewUsername.setText(username);
    }
}
