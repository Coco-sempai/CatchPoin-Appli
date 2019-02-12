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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.iut.catchpoint.catchpoint.models.Parcours;

import org.w3c.dom.Text;

import java.util.Objects;

public class DialogParcours extends Dialog  {

    private Parcours parcours;
    private int height;
    private int width;
    OnMyDialogResult mDialogResult; // the callback


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
        TextView idParcours = (TextView) findViewById(R.id.idParcours);
        Button catchit = (Button) findViewById(R.id.catchItButton);

        title.setText(parcours.getNom_parcours());
        description.setText(parcours.getDescription_parcours());
        duree.setText(String.valueOf(parcours.getDuree())+"min");
        distance.setText(parcours.getDistance()+"km");
        difficulty.setText("Difficulté : "+parcours.getDifficulte());
        duree.setText(String.valueOf(parcours.getDuree()));

        catchit.setOnClickListener(new catchItListener());

        getWindow().setLayout(width,height);
    }

    private class catchItListener implements android.view.View.OnClickListener {
        @Override
        public void onClick(View v) {
            if( mDialogResult != null ){
                mDialogResult.finish(String.valueOf(parcours.getId()));
            }
            DialogParcours.this.dismiss();
        }
    }

    public void setDialogResult(OnMyDialogResult dialogResult){
        mDialogResult = dialogResult;
    }

    public interface OnMyDialogResult{
        void finish(String result);
    }
}