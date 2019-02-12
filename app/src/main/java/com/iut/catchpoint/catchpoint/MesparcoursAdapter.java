package com.iut.catchpoint.catchpoint;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ScaleDrawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.WrappedDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.iut.catchpoint.catchpoint.models.Parcours;

import java.util.List;

public class MesparcoursAdapter extends RecyclerView.Adapter<MesparcoursAdapter.MyViewHolder> {
    private Parcours[] tabParcours;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public TextView distance;
        public TextView duree;
        public TextView difficulte;
        public TextView descritpion;
        public ImageView delete;
        public Button catchIt;
        public ImageView fleche;
        public ImageView arrowDown;

        public MyViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.txt_name_parcours);
            distance = (TextView) view.findViewById(R.id.distance_mes_parcours);
            duree = (TextView) view.findViewById(R.id.duree_mes_parcours);
            difficulte = (TextView) view.findViewById(R.id.difficulte_mes_parcours);
            descritpion = (TextView) view.findViewById(R.id.desc_mes_parcours);
            delete = (ImageView) view.findViewById(R.id.delete_parcours);
            catchIt = (Button) view.findViewById(R.id.catchIt_mes_parcours);
            fleche = (ImageView) view.findViewById(R.id.fleche);
            arrowDown = (ImageView) view.findViewById(R.id.arrow_down);
        }
    }

    public MesparcoursAdapter(Parcours[] mesParcoursList) {
        this.tabParcours = mesParcoursList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.mesparcours_list_row, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        final Parcours parc = tabParcours[position];
        holder.name.setText(parc.getNom_parcours());
        holder.distance.setText(String.valueOf(parc.getDistance())+"km");
        holder.duree.setText(String.valueOf(parc.getDuree())+"min");
        holder.difficulte.setText("Difficulté : "+String.valueOf(parc.getDifficulte()));
        holder.descritpion.setText(parc.getDescription_parcours());
        holder.arrowDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.distance.setVisibility(View.GONE);
                holder.duree.setVisibility(View.GONE);
                holder.difficulte.setVisibility(View.GONE);
                holder.descritpion.setVisibility(View.GONE);
                holder.delete.setVisibility(View.GONE);
                holder.catchIt.setVisibility(View.GONE);
                holder.arrowDown.setVisibility(View.GONE);
                holder.fleche.setVisibility(View.VISIBLE);
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.distance.setVisibility(View.VISIBLE);
                holder.duree.setVisibility(View.VISIBLE);
                holder.difficulte.setVisibility(View.VISIBLE);
                holder.descritpion.setVisibility(View.VISIBLE);
                holder.delete.setVisibility(View.VISIBLE);
                holder.catchIt.setVisibility(View.VISIBLE);
                holder.arrowDown.setVisibility(View.VISIBLE);
                holder.fleche.setVisibility(View.GONE);
            }
        });
        holder.catchIt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), MapsActivity.class);
                intent.putExtra("parcours", parc);
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return tabParcours.length;
    }
}
