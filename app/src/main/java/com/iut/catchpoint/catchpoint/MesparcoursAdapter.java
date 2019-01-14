package com.iut.catchpoint.catchpoint;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.iut.catchpoint.catchpoint.models.Parcours;

import java.util.List;

public class MesparcoursAdapter extends RecyclerView.Adapter<MesparcoursAdapter.MyViewHolder> {
    private List<Parcours> mesParcoursList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name;

        public MyViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.txt_name_parcours);
        }
    }

    public MesparcoursAdapter(List<Parcours> mesParcoursList) {
        this.mesParcoursList = mesParcoursList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.mesparcours_list_row, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Parcours parc = mesParcoursList.get(position);
        holder.name.setText(parc.getNom_parcours());
    }

    @Override
    public int getItemCount() {
        return mesParcoursList.size();
    }
}
