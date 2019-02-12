package com.iut.catchpoint.catchpoint;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.iut.catchpoint.catchpoint.models.Parcours;
import com.iut.catchpoint.catchpoint.models.Point;

public class MapsActivity extends FragmentActivity implements GoogleMap.OnMyLocationButtonClickListener,
        GoogleMap.OnMyLocationClickListener,
        OnMapReadyCallback, ActivityCompat.OnRequestPermissionsResultCallback, LocationListener {

    public final String URL_PARCOURS = "http://10.0.2.2:8000/api/parcours";
    public final String URL_DEPART = "http://10.0.2.2:8000/api/parcours/depart";
    public String URL_POINTS = "http://10.0.2.2:8000/api/parcours/points/";

    private GoogleMap mMap;
    private FrameLayout coordinatorLayout;
    private ImageView settings__view;

    private Point[] pointDepart; // Contient tout les points de départs de tous les parcours
    private Parcours[] tabParcours; // Contient tout les parcours
    private Point[] pointsParcours; // Contient tout les points du parcours auquel on joue

    private BottomNavigationView navigation; // Bar de navigation principale

    private int screenHeight; // Hauteur de l'écran
    private int screenWidth; // Largeur de l'écran

    private Location pointLocation; // Sert à l'initialisation des points
    private Point currentPoint; // Point actuel lorsqu'on joue
    private int currentPointIndex; // Index du point actuel dans le tableau de pointsParcours

    private View bar_parcours; // Bottom bar du lorsqu'on joue un parcours
    private ImageView abandon_icon; // item de bar_parcours
    private Button catchIt; // item de bar_parcours
    private ImageView indice_icon; // item de bar_parcours
    private TextView distanceNavigationBar; // item de bar_parcours

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        coordinatorLayout = (FrameLayout) findViewById(R.id.map);
        settings__view = findViewById(R.id.settings);

        // Window size
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        screenHeight = metrics.heightPixels;
        screenWidth = metrics.widthPixels;

        // Bottom bar principale
        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        // Bottom bar_parcours
        bar_parcours = findViewById(R.id.bar_parcours);
        abandon_icon = findViewById(R.id.abandonner_icon);
        catchIt = findViewById(R.id.catchit);
        indice_icon = findViewById(R.id.indice_icon);
        distanceNavigationBar = findViewById(R.id.distance);
        abandon_icon.setOnClickListener(abandonner);
        indice_icon.setOnClickListener(showIndice);
        catchIt.setOnClickListener(checkPosition);

        StringRequest requestParcours = new StringRequest(com.android.volley.Request.Method.GET,
                URL_PARCOURS, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                tabParcours = new Gson().fromJson(response, Parcours[].class);
                StringRequest requestPoint = new StringRequest(com.android.volley.Request.Method.GET,
                        URL_DEPART, new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        pointDepart = new Gson().fromJson(response, Point[].class);
                        drawAllDepart();
                    }
                }, new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("VolleyError", String.valueOf(error));
                    }
                });
                requestPoint.setRetryPolicy(new DefaultRetryPolicy(
                        5000,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                ConnectionManager.getInstance(getBaseContext()).add(requestPoint);
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("VolleyError", String.valueOf(error));
            }
        });
        requestParcours.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        ConnectionManager.getInstance(this).add(requestParcours);
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
        //mMapFragment.getView().getLayoutParams().height=screenHeight-410;
//        ////////// PARCOURS DE TEST //////////
//        tabParcours = new Parcours[3];
//        Parcours parcours = new Parcours(1, "Premier parcours", 12.5, 3, 55, "Mon premier parcours trop bien");
//        tabParcours[0] = parcours;
//        parcours = new Parcours(2, "Deuxième parcours", 5.48, 1, 20, "Mon Deuxieme parcours trop bien");
//        tabParcours[1] = parcours;
//        parcours = new Parcours(3, "Troisième parcours", 8.9, 3, 35, "Mon Troisième parcours trop bien");
//        tabParcours[2] = parcours;
//        ///////////////////////////////////////////////////////////////
//
//        ////////// POINT DE DEPART TEST //////////
//        pointDepart = new Point[3];
//        Point test = new Point(1,"Depart 1",6.850,47.640,true,false,"Le prochain point se trouve à etc...",tabParcours[0]);
//        pointDepart[0] = test;
//        test = new Point(2,"Depart 2",6.859,47.649,true,false,"Le prochain point se trouve à etc...",tabParcours[1]);
//        pointDepart[1] = test;
//        test = new Point(3,"Depart 3",6.855,47.645,true,false,"Le prochain point se trouve à etc...",tabParcours[2]);
//        pointDepart[2] = test;
//        Location pointLocation = new Location("test");
//        for(Point point:pointDepart) {
//            pointLocation.setProvider(point.getTitrePoint());
//            pointLocation.setLatitude(point.getLatitude());
//            pointLocation.setLongitude((point.getLongitude()));
//            drawMarker(pointLocation,point.getParcoursId().getId());
//        }
//        ///////////////////////////////////////////////////////////////
//
//        ////////// POINT DU PARCOURS TEST //////////
//        Point[] tabPoint0 = new Point[5];
//        for(int i=1;i<=tabPoint0.length;i++){
//            if(i==1){
//                tabPoint0[0]=pointDepart[0];
//            } else {
//                tabPoint0[i - 1] = new Point(i, "Point toto" + i, 6.855 + i * 0.01, 47.640 + i * 0.01, false, false, "Le prochain point se trouve à etc...", tabParcours[0]);
//            }
//        }
//        tabParcours[0].setListPoint(tabPoint0);
//
//        Point[] tabPoint1 = new Point[5];
//        for(int i=1;i<=tabPoint1.length;i++){
//            if(i==1){
//                tabPoint1[0]=pointDepart[1];
//            } else {
//                tabPoint1[i - 1] = new Point(i, "Point " + i, 6.859 + i * 0.01, 47.649 + i * 0.01, false, false, "Le prochain point se trouve à etc...", tabParcours[1]);
//            }
//        }
//        tabParcours[1].setListPoint(tabPoint1);
//
//        Point[] tabPoint2 = new Point[5];
//        for(int i=1;i<=tabPoint2.length;i++){
//            if(i==1){
//                tabPoint2[0]=pointDepart[2];
//            } else {
//                tabPoint2[i - 1] = new Point(i, "Point " + i, 6.855 + i * 0.01, 47.645 + i * 0.01, false, false, "Le prochain point se trouve à etc...", tabParcours[2]);
//            }
//        }
//        tabParcours[2].setListPoint(tabPoint2);
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
            setLocationManager();
        }
    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {
        String message = "Latitude: " + location.getLatitude() + "\nLongitude: " + location.getLongitude();
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
                setLocationManager();
            } else {
                explain();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void drawMarker(Location location, int id) {
        if (mMap != null) {
            //mMap.clear();
            Bitmap bitmapIcon = drawableToBitmap(getResources().getDrawable(R.drawable.ic_compass));

            LatLng gps = new LatLng(location.getLatitude(), location.getLongitude());
            mMap.addMarker(new MarkerOptions()
                    .position(gps)
                    .icon(BitmapDescriptorFactory.fromBitmap(bitmapIcon))
                    .snippet(String.valueOf(id))
                    .title(location.getProvider()));
            mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {
                    Parcours parcoursInfo = new Parcours();
                    for (Parcours p : tabParcours) {
                        if (p.getId() == Integer.parseInt(marker.getSnippet())) {
                            parcoursInfo = p;
                        }
                    }
                    DialogParcours dialogParcours = new DialogParcours(MapsActivity.this, parcoursInfo, screenHeight, screenWidth);
                    dialogParcours.show();
                    dialogParcours.setDialogResult(new DialogParcours.OnMyDialogResult() {
                        public void finish(String result) {
                            mMap.clear();
                            StringRequest requestPoint = new StringRequest(com.android.volley.Request.Method.GET,
                                    URL_POINTS + result, new com.android.volley.Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    pointsParcours = new Gson().fromJson(response, Point[].class);
                                    Location pointDepart;
                                    pointDepart = new Location(pointsParcours[0].getTitrePoint());
                                    pointDepart.setLatitude(pointsParcours[0].getLatitude());
                                    pointDepart.setLongitude((pointsParcours[0].getLongitude()));
                                    drawDepart(pointDepart, pointsParcours[0].getDescriptionPoint());
                                    currentPointIndex = 0;
                                    currentPoint = pointsParcours[currentPointIndex];
                                    navigation.setVisibility(View.GONE);
                                    bar_parcours.setVisibility(View.VISIBLE);
                                }
                            }, new com.android.volley.Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Log.d("VolleyError", String.valueOf(error));
                                }
                            });
                            requestPoint.setRetryPolicy(new DefaultRetryPolicy(
                                    10000,
                                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                            ConnectionManager.getInstance(getBaseContext()).add(requestPoint);
                        }
                    });
                    return true;
                }
            });
            //mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(gps, 12));
        }
    }

    private void drawDepart(Location location, String descriptionPoint) {
        if (mMap != null) {
            //mMap.clear();
            LatLng gps = new LatLng(location.getLatitude(), location.getLongitude());
            mMap.addMarker(new MarkerOptions()
                    .position(gps)
                    .snippet(descriptionPoint)
                    .title(location.getProvider()));
            mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {
                    Toast.makeText(getBaseContext(), "Rejoignez le point de départ", Toast.LENGTH_LONG).show();
                    marker.showInfoWindow();
                    return true;
                }
            });
        }
    }

    private void drawAllDepart(){
        if(mMap != null){
            for (Point point : pointDepart) {
                pointLocation = new Location("test");
                pointLocation.setLatitude(point.getLatitude());
                pointLocation.setLongitude((point.getLongitude()));
                drawMarker(pointLocation, point.getParcoursId().getId());
            }
        }
    }

    @Override
    public void onLocationChanged(Location location) {

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

    public static Bitmap drawableToBitmap(Drawable drawable) {
        Bitmap bitmap = null;

        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            if (bitmapDrawable.getBitmap() != null) {
                return bitmapDrawable.getBitmap();
            }
        }

        if (drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
            bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888); // Single color bitmap will be created of 1x1 pixel
        } else {
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    public void setLocationManager() {
        LocationManager locationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);
        LocationListener locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                // Called when a new location is found by the network location provider.
                Log.v("newLocation", "IN ON LOCATION CHANGE, lat=" + location.getLatitude() + ", lon=" + location.getLongitude());
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            public void onProviderEnabled(String provider) {
            }

            public void onProviderDisabled(String provider) {
            }
        };
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
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
                    intentParcours.putExtra("parcours", tabParcours);
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

    View.OnClickListener abandonner = new View.OnClickListener() {
        public void onClick(View v) {
            DialogAbandon dialogAbandon = new DialogAbandon(MapsActivity.this, screenHeight, screenWidth);
            dialogAbandon.show();
            dialogAbandon.setDialogResult(new DialogAbandon.OnMyDialogResult() {
                public void finish(String result) {
                    if(result.equals("yes")) {
                        mMap.clear();
                        drawAllDepart();
                        navigation.setVisibility(View.VISIBLE);
                        bar_parcours.setVisibility(View.GONE);
                    }
                }
            });
        }
    };

    View.OnClickListener showIndice = new View.OnClickListener() {
        public void onClick(View v) {
            AlertDialog.Builder builder = new AlertDialog.Builder(MapsActivity.this);
            builder.setTitle(currentPoint.getTitrePoint())
                    .setMessage(currentPoint.getDescriptionPoint())
                    .show();
        }
    };

    View.OnClickListener checkPosition = new View.OnClickListener() {
        public void onClick(View v) {
            //if(currentPointIndex == 0) mMap.clear();
            // TODO Si currentLocation = currentPoint Location alors on passe on point suivant, sinon toast avec message

            // Affichage du point validé
            Location pointValide;
            pointValide = new Location(pointsParcours[currentPointIndex].getTitrePoint());
            pointValide.setLatitude(pointsParcours[currentPointIndex].getLatitude());
            pointValide.setLongitude((pointsParcours[currentPointIndex].getLongitude()));
            drawDepart(pointValide, pointsParcours[currentPointIndex].getDescriptionPoint());

            currentPointIndex++;

            if(currentPointIndex >= pointsParcours.length){
                // Fin du parcours, affichage du temps écoulé ?
                AlertDialog.Builder builder = new AlertDialog.Builder(MapsActivity.this);
                builder.setTitle("Parcours terminé")
                        .setMessage("Bravo vous avez terminé le parcours : "+currentPoint.getParcoursId().getNom_parcours())
                        .show();
                mMap.clear();
                drawAllDepart();
                navigation.setVisibility(View.VISIBLE);
                bar_parcours.setVisibility(View.GONE);
            } else {
                // Affichage du point suivant (pour le test)
                Location pointSuivant;
                pointSuivant = new Location(pointsParcours[currentPointIndex].getTitrePoint());
                pointSuivant.setLatitude(pointsParcours[currentPointIndex].getLatitude());
                pointSuivant.setLongitude((pointsParcours[currentPointIndex].getLongitude()));
                drawDepart(pointSuivant, pointsParcours[currentPointIndex].getDescriptionPoint());

                currentPoint = pointsParcours[currentPointIndex];
            }
        }
    };
}
