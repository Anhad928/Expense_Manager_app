package com.example.anhadpre_expbook;

import android.os.Parcel;
import android.os.Parcelable;

public class Expense implements Parcelable {
    private int id;
    private String name;
    private String monthStarted;
    private double monthlyCharge;
    private String comment;
    private boolean checked;

    public Expense(String name, String monthStarted, double monthlyCharge, String comment) {
        this.name = name;
        this.monthStarted = monthStarted;
        this.monthlyCharge = monthlyCharge;
        this.comment = comment;

        this.checked = false; // Initialize as unchecked
    }

    // Parcelable implementation
    protected Expense(Parcel in) {
        id = in.readInt();
        name = in.readString();
        monthStarted = in.readString();
        monthlyCharge = in.readDouble();
        comment = in.readString();
        checked = in.readByte() != 0;
    }

    public static final Creator<Expense> CREATOR = new Creator<Expense>() {
        @Override
        public Expense createFromParcel(Parcel in) {
            return new Expense(in);
        }

        @Override
        public Expense[] newArray(int size) {
            return new Expense[size];
        }
    };

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

    public String getMonthStarted() {
        return monthStarted;
    }

    public void setMonthStarted(String monthStarted) {
        this.monthStarted = monthStarted;
    }

    public double getMonthlyCharge() {
        return monthlyCharge;
    }

    public void setMonthlyCharge(double monthlyCharge) {
        this.monthlyCharge = monthlyCharge;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(monthStarted);
        dest.writeDouble(monthlyCharge);
        dest.writeString(comment);
        dest.writeByte((byte) (checked ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }
}
