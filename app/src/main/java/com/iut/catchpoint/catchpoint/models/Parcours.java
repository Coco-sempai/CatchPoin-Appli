package com.iut.catchpoint.catchpoint.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Parcours implements Serializable {

    @SerializedName("iParcours")
    private int id_parcours;

    @SerializedName("nomParcours")
    private String nom_parcours;

    @SerializedName("distance")
    private double distance;

    @SerializedName("difficulte")
    private int difficulte;

    @SerializedName("duree")
    private int duree;

    @SerializedName("descriptionParcours")
    private String description_parcours;

    private Point[] listPoint;

    public Parcours() {}

    public Parcours(int id, String nom_parcours, double distance, int difficulte, int duree, String description_parcours, Point[] listPoint) {
        this.id_parcours = id;
        this.nom_parcours = nom_parcours;
        this.distance = distance;
        this.difficulte = difficulte;
        this.duree = duree;
        this.description_parcours = description_parcours;
        this.listPoint = listPoint;
    }

    public int getId() {
        return id_parcours;
    }

    public void setId(int id) {
        this.id_parcours = id;
    }

    public String getNom_parcours() {
        return nom_parcours;
    }

    public void setNom_parcours(String nom_parcours) {
        this.nom_parcours = nom_parcours;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public int getDifficulte() {
        return difficulte;
    }

    public void setDifficulte(int difficulte) {
        this.difficulte = difficulte;
    }

    public int getDuree() {
        return duree;
    }

    public void setDuree(int duree) {
        this.duree = duree;
    }

    public String getDescription_parcours() {
        return description_parcours;
    }

    public void setDescription_parcours(String description_parcours) {
        this.description_parcours = description_parcours;
    }

    public int getId_parcours() {
        return id_parcours;
    }

    public void setId_parcours(int id_parcours) {
        this.id_parcours = id_parcours;
    }

    public Point[] getListPoint() {
        return listPoint;
    }

    public void setListPoint(Point[] listPoint) {
        this.listPoint = listPoint;
    }
}
