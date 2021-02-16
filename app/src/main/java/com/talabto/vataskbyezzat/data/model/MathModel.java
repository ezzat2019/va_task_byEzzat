package com.talabto.vataskbyezzat.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "math")
public class MathModel implements Parcelable {

    public static final Creator<MathModel> CREATOR = new Creator<MathModel>() {
        @Override
        public MathModel createFromParcel(Parcel in) {
            return new MathModel(in);
        }

        @Override
        public MathModel[] newArray(int size) {
            return new MathModel[size];
        }
    };
    @PrimaryKey

    @ColumnInfo(name = "id")
    int id;
    @ColumnInfo(name = "num1")
    String num1;
    @ColumnInfo(name = "num2")
    String num2;
    @ColumnInfo(name = "result")
    String result;
    @ColumnInfo(name = "time")
    String time;
    @ColumnInfo(name = "op")
    String op;
    @ColumnInfo(name = "date")
    String date;
    @ColumnInfo(name = "is_pending")
    boolean is_pending;

    public MathModel(int id, String num1, String num2, String result, String time, String op, String date, boolean is_pending) {
        this.id = id;
        this.num1 = num1;
        this.num2 = num2;
        this.result = result;
        this.time = time;
        this.op = op;
        this.date = date;
        this.is_pending = is_pending;
    }

    protected MathModel(Parcel in) {
        id = in.readInt();
        num1 = in.readString();
        num2 = in.readString();
        result = in.readString();
        time = in.readString();
        op = in.readString();
        date = in.readString();
        is_pending = in.readByte() != 0;
    }

    @Override
    public String toString() {
        return "MathModel{" +
                "id=" + id +
                ", num1='" + num1 + '\'' +
                ", num2='" + num2 + '\'' +
                ", result='" + result + '\'' +
                ", time='" + time + '\'' +
                ", op='" + op + '\'' +
                ", date='" + date + '\'' +
                ", is_pending=" + is_pending +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNum1() {
        return num1;
    }

    public void setNum1(String num1) {
        this.num1 = num1;
    }

    public String getNum2() {
        return num2;
    }

    public void setNum2(String num2) {
        this.num2 = num2;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getOp() {
        return op;
    }

    public void setOp(String op) {
        this.op = op;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isIs_pending() {
        return is_pending;
    }

    public void setIs_pending(boolean is_pending) {
        this.is_pending = is_pending;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(num1);
        dest.writeString(num2);
        dest.writeString(result);
        dest.writeString(time);
        dest.writeString(op);
        dest.writeString(date);
        dest.writeByte((byte) (is_pending ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }
}
