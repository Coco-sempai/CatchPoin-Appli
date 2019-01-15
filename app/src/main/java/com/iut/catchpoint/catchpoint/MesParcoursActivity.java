package com.iut.catchpoint.catchpoint;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.iut.catchpoint.catchpoint.models.Parcours;
import com.iut.catchpoint.catchpoint.models.Point;

import java.util.ArrayList;
import java.util.List;

public class MesParcoursActivity extends AppCompatActivity {

    public final String URL="http://josephazar.net/Students.php";
    private Parcours[] mesParcours;
    private List<Parcours> mesParcoursList = new ArrayList<>();
    private RecyclerView recyclerView;
    private MesparcoursAdapter mAdapter;
    private Parcours[] tabParcours;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mes_parcours);

        Intent intent = getIntent();
        tabParcours = (Parcours[]) intent.getSerializableExtra("parcours");

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_parcours);
        mAdapter = new MesparcoursAdapter(tabParcours);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(mAdapter);
        //prepareParcoursData();
    }

    private void prepareParcoursData() {
        //TODO a faire dans maps activity et envoyer les data ici avec des putExtra
        StringRequest request=new StringRequest(com.android.volley.Request.Method.GET, URL, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                mesParcours=new Gson().fromJson(response,Parcours[].class);
//                for(Parcours parcours: mesParcours){
//                    Parcours st= new Parcours();
//                    mesParcoursList.add(st);
//                }
                mAdapter.notifyDataSetChanged();
            }
            }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
    }
        });
        ConnectionManager.getInstance(this).add(request);
    }

    public void goBack(View view){
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }
}
