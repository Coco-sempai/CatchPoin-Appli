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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mes_parcours);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_parcours);
        mAdapter = new MesparcoursAdapter(mesParcoursList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(mAdapter);
        prepareParcoursData();
    }

    private void prepareParcoursData() {
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
        Parcours test = new Parcours(1, "Premier parcours", 12.5, 3, 55, "Mon premier parcours trop bien");
        mesParcoursList.add(test);
        test = new Parcours(2, "Deuxième parcours", 5.48, 1, 20, "Mon Deuxieme parcours trop bien");
        mesParcoursList.add(test);
        test = new Parcours(3, "Troisième parcours", 8.9, 3, 35, "Mon Troisième parcours trop bien");
        mesParcoursList.add(test);
    }

    public void goBack(View view){
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }
}
