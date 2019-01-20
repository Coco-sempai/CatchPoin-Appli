package com.iut.catchpoint.catchpoint.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Point implements Serializable {

    @SerializedName("idPoint")
    private int id_point;

    @SerializedName("longitude")
    private double longitude;

    @SerializedName("latitude")
    private double latitude;

    @SerializedName("depart")
    private boolean depart;

    @SerializedName("arrive")
    private boolean arrive;

    @SerializedName("descriptionPoint")
    private String descriptionPoint;

    @SerializedName("titrePoint")
    private String titrePoint;

    @SerializedName("parcoursId")
    private Parcours parcoursId;

    public Point() {
    }

    public Point(int id_point, String titrePoint, double longitude, double latitude, boolean depart, boolean arrive, String descriptionPoint, Parcours parcoursId) {
        this.id_point = id_point;
        this.titrePoint = titrePoint;
        this.longitude = longitude;
        this.latitude = latitude;
        this.depart = depart;
        this.arrive = arrive;
        this.descriptionPoint = descriptionPoint;
        this.parcoursId = parcoursId;
    }

    public int getId_point() {
        return id_point;
    }

    public void setId_point(int id_point) {
        this.id_point = id_point;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public boolean isDepart() {
        return depart;
    }

    public void setDepart(boolean depart) {
        this.depart = depart;
    }

    public boolean isArrive() {
        return arrive;
    }

    public void setArrive(boolean arrive) {
        this.arrive = arrive;
    }

    public String getDescriptionPoint() {
        return descriptionPoint;
    }

    public void setDescriptionPoint(String descriptionPoint) {
        this.descriptionPoint = descriptionPoint;
    }

    public String getTitrePoint() {
        return titrePoint;
    }

    public void setTitrePoint(String titrePoint) {
        this.titrePoint = titrePoint;
    }

    public Parcours getParcoursId() {
        return parcoursId;
    }

    public void setParcoursId(Parcours parcoursId) {
        this.parcoursId = parcoursId;
    }
}