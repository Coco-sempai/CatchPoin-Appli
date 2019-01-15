package com.iut.catchpoint.catchpoint;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.iut.catchpoint.catchpoint.models.Parcours;
import com.iut.catchpoint.catchpoint.models.Point;

public class MapsActivity extends FragmentActivity implements GoogleMap.OnMyLocationButtonClickListener,
        GoogleMap.OnMyLocationClickListener,
        OnMapReadyCallback, ActivityCompat.OnRequestPermissionsResultCallback, LocationListener {

    public final String URL_PARCOURS = "http://localhost:8000/api/parcours";
    public final String URL_DEPART = "http://localhost:8000/api/parcours/depart";

    private GoogleMap mMap;
    private FrameLayout coordinatorLayout;
    private ImageView settings__view;
    private Point[] pointDepart;
    private Parcours[] tabParcours;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        coordinatorLayout = (FrameLayout) findViewById(R.id.map);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        settings__view = findViewById(R.id.settings);


        StringRequest request=new StringRequest(com.android.volley.Request.Method.GET,
                URL_DEPART, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                pointDepart=new Gson().fromJson(response,Point[].class);
                //TODO Chargement de tout les parcours en même temps ou juste quand on clic sur un point ?

                //Log.d("pointDepart",pointDepart[0].getDescriptionPoint());
                Location pointLocation;
                for(Point point:pointDepart) {
                    pointLocation = new Location("test");
                    pointLocation.setLatitude(point.getLatitude());
                    pointLocation.setLongitude((point.getLongitude()));
                    //drawMarker(pointLocation,point.getParcoursId());
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("VolleyError", String.valueOf(error));
            }
        });
        ConnectionManager.getInstance(this).add(request);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        ////////// PARCOURS DE TEST //////////
        tabParcours = new Parcours[3];
        Parcours parcours = new Parcours(1, "Premier parcours", 12.5, 3, 55, "Mon premier parcours trop bien");
        tabParcours[0] = parcours;
        parcours = new Parcours(2, "Deuxième parcours", 5.48, 1, 20, "Mon Deuxieme parcours trop bien");
        tabParcours[1] = parcours;
        parcours = new Parcours(3, "Troisième parcours", 8.9, 3, 35, "Mon Troisième parcours trop bien");
        tabParcours[2] = parcours;
        ///////////////////////////////////////////////////////////////

        ////////// POINT DU PARCOURS TEST //////////
        Point[] tabPoint = new Point[5];
        for(int i=1;i<=tabPoint.length;i++){
            tabPoint[i-1] = new Point(i,"Point "+i,6.855+i*0.01,47.640+i*0.01,false,false,"Le prochain point se trouve à etc...",1);
        }
        tabParcours[0].setListPoint(tabPoint);

        for(int i=1;i<=tabPoint.length;i++){
            tabPoint[i-1] = new Point(i,"Point "+i,6.859+i*0.01,47.649+i*0.01,false,false,"Le prochain point se trouve à etc...",2);

        }
        tabParcours[1].setListPoint(tabPoint);

        for(int i=1;i<=tabPoint.length;i++){
            tabPoint[i-1] = new Point(i,"Point "+i,6.855+i*0.01,47.645+i*0.01,false,false,"Le prochain point se trouve à etc...",3);
        }
        tabParcours[2].setListPoint(tabPoint);
        ///////////////////////////////////////////////////////////////

        ////////// POINT DE DEPART TEST //////////
        pointDepart = new Point[3];
        Point test = new Point(1,"Depart 1",6.850,47.640,true,false,"Le prochain point se trouve à etc...",1);
        pointDepart[0] = test;
        test = new Point(2,"Depart 2",6.859,47.649,true,false,"Le prochain point se trouve à etc...",2);
        pointDepart[1] = test;
        test = new Point(3,"Depart 3",6.855,47.645,true,false,"Le prochain point se trouve à etc...",3);
        pointDepart[2] = test;
        Location pointLocation = new Location("test");
        for(Point point:pointDepart) {
            pointLocation.setProvider(point.getTitrePoint());
            pointLocation.setLatitude(point.getLatitude());
            pointLocation.setLongitude((point.getLongitude()));
            drawMarker(pointLocation,point.getParcoursId());
        }
        ///////////////////////////////////////////////////////////////

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {
                explain();
            } else {
                askForPermission();
            }
        } else {
            mMap.setMyLocationEnabled(true);
            mMap.setOnMyLocationButtonClickListener(this);
            mMap.setOnMyLocationClickListener(this);
            mMap.getUiSettings().setMyLocationButtonEnabled(true);
        }
    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {
        String message = "Latitude: "+location.getLatitude() +"\nLongitude: "+location.getLongitude();
        Toast.makeText(this, "Votre position:\n" + message, Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onMyLocationButtonClick() {
        Toast.makeText(this, "MyLocation button clicked", Toast.LENGTH_SHORT).show();
        return false;
    }

    private void askForPermission() {
        requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 2);
    }

    private void explain() {
        Snackbar.make(coordinatorLayout, "Cette permission est nécessaire pour la géolocalisation", Snackbar.LENGTH_LONG).setAction("Activer", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                askForPermission();
            }
        }).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 2) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                mMap.setMyLocationEnabled(true);
                mMap.setOnMyLocationButtonClickListener(this);
                mMap.setOnMyLocationClickListener(this);
                mMap.getUiSettings().setMyLocationButtonEnabled(true);
            }
            else
            {
                explain();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void drawMarker(final Location location, int id) {
        if(mMap!=null) {
            //mMap.clear();
            LatLng gps = new LatLng(location.getLatitude(), location.getLongitude());
            mMap.addMarker(new MarkerOptions()
                    .position(gps)
                    .snippet(String.valueOf(id))
                    .title(location.getProvider()));
            mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {
                    mMap.clear();
                    for(Point point:tabParcours[Integer.parseInt(marker.getSnippet())-1].getListPoint()) {
                        LatLng gps = new LatLng(point.getLatitude(), point.getLongitude());
                        mMap.addMarker(new MarkerOptions()
                                .position(gps)
                                .title(point.getTitrePoint()));
                    }
                    return true;
                }
            });
            //mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(gps, 12));
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        Snackbar.make(coordinatorLayout, "Welcome to AndroidHive", Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    public void openSettings(View view) {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.menu_filtre:
                    Log.v("itemSelected", "Filtres");
                    return true;
                case R.id.menu_parcours:
                    Intent intentParcours = new Intent(getBaseContext(), MesParcoursActivity.class);
                    intentParcours.putExtra("parcours",tabParcours);
                    startActivity(intentParcours);
                    return true;
                case R.id.menu_profil:
                    Intent intentProfil = new Intent(getBaseContext(), ProfilActivity.class);
                    startActivity(intentProfil);
                    return true;
                case R.id.menu_recherche:
                    Log.v("itemSelected", "Recherche");
                    return true;
            }
            return false;
        }
    };
}
