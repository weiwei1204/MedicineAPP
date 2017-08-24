package com.example.carrie.carrie_test1;

import android.app.Activity;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by jonathan on 2017/8/17.
 */

public class BloodPressure implements Parcelable{
    public int id;
    public String member_id;
    public String highmmhg;
    public String lowmmhg;
    public String bpm;
    public String savetime;
    public BloodPressure(int id, String member_id, String highmmhg, String lowmmhg, String bpm,  String savetime) {
        this.id = id;
        this.member_id = member_id;
        this.highmmhg = highmmhg;
        this.lowmmhg = lowmmhg;
        this.bpm = bpm;
        this.savetime = savetime;
    }


    protected BloodPressure(Parcel in) {
        id = in.readInt();
        member_id = in.readString();
        highmmhg = in.readString();
        lowmmhg = in.readString();
        bpm = in.readString();
        savetime = in.readString();
    }

    public static final Creator<BloodPressure> CREATOR = new Creator<BloodPressure>() {
        @Override
        public BloodPressure createFromParcel(Parcel in) {
            return new BloodPressure(in);
        }

        @Override
        public BloodPressure[] newArray(int size) {
            return new BloodPressure[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMember_id() {
        return member_id;
    }

    public void setMember_id(String member_id) {
        this.member_id = member_id;
    }

    public String getHighmmhg() {
        return highmmhg;
    }

    public void setHighmmhg(String highmmhg) {
        this.highmmhg = highmmhg;
    }

    public String getLowmmhg() {
        return lowmmhg;
    }

    public void setLowmmhg(String lowmmhg) {
        this.lowmmhg = lowmmhg;
    }

    public String getBpm() {
        return bpm;
    }

    public void setBpm(String bpm) {
        this.bpm = bpm;
    }

    public String getSavetime() {
        return savetime;
    }

    public void setSavetime(String savetime) {
        this.savetime = savetime;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(member_id);
        dest.writeString(highmmhg);
        dest.writeString(lowmmhg);
        dest.writeString(bpm);
        dest.writeString(savetime);
    }
}
