package com.adiputrasinaga.indonesiandictionary.Model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Adiputra on 12/01/2018.
 */

public class IdEnModel implements Parcelable {

    private int id;
    private String words;
    private String details;

    public IdEnModel(){

    }

    public IdEnModel(String words, String details) {

        this.words = words;
        this.details = details;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWords() {
        return words;
    }

    public void setWords(String words) {
        this.words = words;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeInt(this.id);
        dest.writeString(this.words);
        dest.writeString(this.details);
    }

    protected IdEnModel(Parcel in) {

        this.id = in.readInt();
        this.words = in.readString();
        this.details = in.readString();
    }

    public static final Parcelable.Creator<IdEnModel> CREATOR = new Parcelable.Creator<IdEnModel>() {

        @Override
        public IdEnModel createFromParcel(Parcel source) {
            return new IdEnModel(source);
        }

        @Override
        public IdEnModel[] newArray(int size) {
            return new IdEnModel[size];
        }
    };
}

