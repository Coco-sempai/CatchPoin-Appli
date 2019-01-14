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
    private int depart;

    @SerializedName("arrive")
    private String arrive;

    @SerializedName("descriptionPoint")
    private String descriptionPoint;

    @SerializedName("titrePoint")
    private String titrePoint;

    @SerializedName("parcoursId")
    private String parcoursId;

    public Point() {
    }

    public Point(int id_point, String nom_parcours, double longitude, int latitude, int depart, String arrive, String descriptionPoint, String titrePoint, String parcoursId) {
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

    public int getDepart() {
        return depart;
    }

    public void setDepart(int depart) {
        this.depart = depart;
    }

    public String getArrive() {
        return arrive;
    }

    public void setArrive(String arrive) {
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

    public String getParcoursId() {
        return parcoursId;
    }

    public void setParcoursId(String parcoursId) {
        this.parcoursId = parcoursId;
    }
}
