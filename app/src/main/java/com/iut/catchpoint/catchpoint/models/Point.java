package com.iut.catchpoint.catchpoint.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Point implements Serializable {

    @SerializedName("idPoint")
    private int id_point;

    @SerializedName("nomParcours")
    private String nom_parcours;

    @SerializedName("longitude")
    private double longitude;

    @SerializedName("latitude")
    private int latitude;

    @SerializedName("depart")
    private boolean depart;

    @SerializedName("arrive")
    private boolean arrive;

    @SerializedName("descriptionPoint")
    private String descriptionPoint;

    @SerializedName("titrePoint")
    private String titrePoint;

    @SerializedName("parcoursId")
    private int parcoursId;

    public Point() {
    }

    public Point(int id_point, String nom_parcours, double longitude, int latitude, boolean depart, boolean arrive, String descriptionPoint, String titrePoint, int parcoursId) {
        this.id_point = id_point;
        this.nom_parcours = nom_parcours;
        this.longitude = longitude;
        this.latitude = latitude;
        this.depart = depart;
        this.arrive = arrive;
        this.descriptionPoint = descriptionPoint;
        this.titrePoint = titrePoint;
        this.parcoursId = parcoursId;
    }

    public int getId_point() {
        return id_point;
    }

    public void setId_point(int id_point) {
        this.id_point = id_point;
    }

    public String getNom_parcours() {
        return nom_parcours;
    }

    public void setNom_parcours(String nom_parcours) {
        this.nom_parcours = nom_parcours;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public int getLatitude() {
        return latitude;
    }

    public void setLatitude(int latitude) {
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

    public int getParcoursId() {
        return parcoursId;
    }

    public void setParcoursId(int parcoursId) {
        this.parcoursId = parcoursId;
    }
}