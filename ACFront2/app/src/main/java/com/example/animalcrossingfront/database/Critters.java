package com.example.animalcrossingfront.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;
@Entity
public class Critters {

    @PrimaryKey (autoGenerate = true)
    private int id;
    @ColumnInfo
    private String species;
    @ColumnInfo
    private String name;
    @ColumnInfo
    private String image;
    @ColumnInfo
    private String location;
    @ColumnInfo
    private String price;
    @ColumnInfo
    private String month;
    @ColumnInfo
    private String time;
    @ColumnInfo
    private String donated;

//    public Critters(int id, String species, String name, String image, String location, String price, String month, String time, String donated) {
//        this.id = id;
//        this.species = species;
//        this.name = name;
//        this.image = image;
//        this.location = location;
//        this.price = price;
//        this.month = month;
//        this.time = time;
//        this.donated = donated;
//    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDonated() {
        return donated;
    }

    public void setDonated(String donated) {
        this.donated = donated;
    }



}
