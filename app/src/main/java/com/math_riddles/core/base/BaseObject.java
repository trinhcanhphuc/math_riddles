package com.math_riddles.core.base;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * @author phuocns on 26/11/2018.
 */

public class BaseObject implements Parcelable{
    @SerializedName("id")
    protected long objectId;
    @SerializedName("description")
    protected String description;
    @SerializedName("updated")
    protected String updated;

    public BaseObject() {
        // Do nothing
    }

    public BaseObject(long mObjectId, String description) {
        this.objectId = mObjectId;
        this.description = description;
    }

    protected BaseObject(Parcel in) {
        objectId = in.readLong();
        description = in.readString();
        updated = in.readString();
    }

    public static final Creator<BaseObject> CREATOR = new Creator<BaseObject>() {
        @Override
        public BaseObject createFromParcel(Parcel in) {
            return new BaseObject(in);
        }

        @Override
        public BaseObject[] newArray(int size) {
            return new BaseObject[size];
        }
    };

    public long getObjectId() {
        return objectId;
    }

    public void setObjectId(long mObjectId) {
        this.objectId = mObjectId;
    }

    public String getObjectDescription() {
        return description;
    }

    public void setObjectDescription(String mDescription) {
        this.description = mDescription;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(objectId);
        dest.writeString(description);
        dest.writeString(updated);
    }
}

