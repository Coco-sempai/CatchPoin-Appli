package com.iut.catchpoint.catchpoint;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.net.URL;

public class LogActivity extends AppCompatActivity {

    private EditText login;
    private EditText mdp;
    private Button connecter;
    private Button inscrire;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);


        login = (EditText) findViewById(R.id.login);
        mdp = (EditText) findViewById(R.id.motDePasse);
        connecter = (Button) findViewById(R.id.connecter);
        inscrire = (Button) findViewById(R.id.inscrire);

        connecter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str_login ="";
                String str_mdp ="";

                Boolean info_valable = true;

                String admin = "root";

                if((!login.getText().toString().trim().equals("")) && (login.getText().toString().equals(admin))){
                    str_login = login.getText().toString().trim();
                }else {
                    info_valable = false;
                }

                if(!mdp.getText().toString().trim().equals("")){
                    str_mdp = mdp.getText().toString().trim();
                }else {
                    info_valable = false;
                }

                if(!info_valable){
                    Toast.makeText(getApplicationContext(), "il manque des infos ou le login n'existe pas !", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                    intent.putExtra("login", str_login);
                    intent.putExtra("motDePasse", str_mdp);
                    finish();
                    startActivity(intent);
                }

            }
        });

       inscrire.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               Intent intent = new Intent(Intent.ACTION_VIEW);
               intent.setData(Uri.parse("http://www.google.fr"));
               startActivity(intent);
           }
       });
    }
}
