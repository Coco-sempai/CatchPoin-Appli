package com.iut.catchpoint.catchpoint;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.iut.catchpoint.catchpoint.models.Parcours;

import java.util.Objects;

public class DialogParcours extends Dialog  {

    private Parcours parcours;
    private int height;
    private int width;

    public DialogParcours(Context context, Parcours parcours,int height, int width) {
        super(context);
        this.parcours = parcours;
        this.height = height-(height*30/100);
        this.width = width-(width*10/100);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_dialog_parcours);
        setTitle(parcours.getNom_parcours());
        TextView title = (TextView) findViewById(R.id.parcours_title);
        TextView description = (TextView) findViewById(R.id.desc_parcours);
        TextView difficulty = (TextView) findViewById(R.id.parcours_difficulty);
        TextView duree = (TextView) findViewById(R.id.parcours_duree);
        TextView distance = (TextView) findViewById(R.id.parcours_distance);

        title.setText(parcours.getNom_parcours());
        description.setText(parcours.getDescription_parcours());
        duree.setText(String.valueOf(parcours.getDuree())+"min");
        distance.setText(parcours.getDistance()+"km");
        difficulty.setText("Difficulté : "+parcours.getDifficulte());
        duree.setText(String.valueOf(parcours.getDuree()));

        getWindow().setLayout(width,height);
    }
}