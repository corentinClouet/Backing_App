package com.example.android.backingapp.entities;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class Recipe implements Parcelable {

    private int id;
    private String name;
    private List<Ingredient> lstIngredient;
    private List<Step> lstStep;
    private int serving;
    private String image;

    public Recipe() {
    }

    public Recipe(int id, String name, List<Ingredient> lstIngredient, List<Step> lstStep, int serving, String image) {
        this.id = id;
        this.name = name;
        this.lstIngredient = lstIngredient;
        this.lstStep = lstStep;
        this.serving = serving;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Ingredient> getLstIngredient() {
        return lstIngredient;
    }

    public void setLstIngredient(List<Ingredient> lstIngredient) {
        this.lstIngredient = lstIngredient;
    }

    public List<Step> getLstStep() {
        return lstStep;
    }

    public void setLstStep(List<Step> lstStep) {
        this.lstStep = lstStep;
    }

    public int getServing() {
        return serving;
    }

    public void setServing(int serving) {
        this.serving = serving;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeList(this.lstIngredient);
        dest.writeList(this.lstStep);
        dest.writeInt(this.serving);
        dest.writeString(this.image);
    }

    protected Recipe(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.lstIngredient = new ArrayList<Ingredient>();
        in.readList(this.lstIngredient, Ingredient.class.getClassLoader());
        this.lstStep = new ArrayList<Step>();
        in.readList(this.lstStep, Step.class.getClassLoader());
        this.serving = in.readInt();
        this.image = in.readString();
    }

    public static final Parcelable.Creator<Recipe> CREATOR = new Parcelable.Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel source) {
            return new Recipe(source);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };
}
