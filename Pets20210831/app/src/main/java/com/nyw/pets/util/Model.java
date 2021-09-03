package com.nyw.pets.util;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by monking-macbook
 * on 2017/7/9
 * in TestForInteger
 * description: use a dialog to introduce this class
 */

public class Model implements Parcelable {
    public String date;
    public int number;

    public Model(String date, int number) {
        this.date = date;
        this.number = number;
    }

    @Override
    public String toString() {
        return "Model{" +
                "date='" + date + '\'' +
                ", number=" + number +
                '}';
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.date);
        dest.writeInt(this.number);
    }

    protected Model(Parcel in) {
        this.date = in.readString();
        this.number = in.readInt();
    }

    public static final Creator<Model> CREATOR = new Creator<Model>() {
        @Override
        public Model createFromParcel(Parcel source) {
            return new Model(source);
        }

        @Override
        public Model[] newArray(int size) {
            return new Model[size];
        }
    };
}
