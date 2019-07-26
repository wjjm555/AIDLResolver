package com.jmc.aidlresolverlibrary.model;

import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Size;
import android.util.SizeF;
import android.util.SparseArray;

import java.io.Serializable;
import java.util.ArrayList;

public class MessageBean implements Parcelable {

    private int what;

    private String target;

    private Bundle data;

    private MessageBean() {

    }

    protected MessageBean(Parcel in) {
        what = in.readInt();
        target = in.readString();
        data = in.readBundle();
    }

    public static final Creator<MessageBean> CREATOR = new Creator<MessageBean>() {
        @Override
        public MessageBean createFromParcel(Parcel in) {
            return new MessageBean(in);
        }

        @Override
        public MessageBean[] newArray(int size) {
            return new MessageBean[size];
        }
    };

    public Bundle getData() {
        return data;
    }

    public String getTarget() {
        return target;
    }

    public int getWhat() {
        return what;
    }

    private void setTarget(String target) {
        this.target = target;
    }

    private void setData(Bundle data) {
        this.data = data;
    }

    private void setWhat(int what) {
        this.what = what;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(what);
        dest.writeString(target);
        dest.writeBundle(data);
    }

    public static final class Builder {

        Bundle data = new Bundle();

        String target;

        int what;

        public MessageBean build() {
            MessageBean bean = new MessageBean();
            bean.target = target;
            bean.setData(data);
            bean.setWhat(what);
            return bean;
        }

        public Builder setWhat(int what) {
            this.what = what;
            return this;
        }

        public Builder setTarget(String target) {
            this.target = target;
            return this;
        }

        public Builder putBoolean(String key, boolean value) {
            data.putBoolean(key, value);
            return this;
        }

        public Builder putInt(String key, int value) {
            data.putInt(key, value);
            return this;
        }

        public Builder putLong(String key, long value) {
            data.putLong(key, value);
            return this;
        }

        public Builder putDouble(String key, double value) {
            data.putDouble(key, value);
            return this;
        }

        public Builder putString(String key, String value) {
            data.putString(key, value);
            return this;
        }

        public Builder putBooleanArray(String key, boolean[] value) {
            data.putBooleanArray(key, value);
            return this;
        }

        public Builder putIntArray(String key, int[] value) {
            data.putIntArray(key, value);
            return this;
        }

        public Builder putLongArray(String key, long[] value) {
            data.putLongArray(key, value);
            return this;
        }

        public Builder putDoubleArray(String key, double[] value) {
            data.putDoubleArray(key, value);
            return this;
        }

        public Builder putStringArray(String key, String[] value) {
            data.putStringArray(key, value);
            return this;
        }

        public Builder putByte(String key, byte value) {
            data.putByte(key, value);
            return this;
        }

        public Builder putChar(String key, char value) {
            data.putChar(key, value);
            return this;
        }

        public Builder putShort(String key, short value) {
            data.putShort(key, value);
            return this;
        }

        public Builder putFloat(String key, float value) {
            data.putFloat(key, value);
            return this;
        }

        public Builder putCharSequence(String key, CharSequence value) {
            data.putCharSequence(key, value);
            return this;
        }

        public Builder putParcelable(String key, Parcelable value) {
            data.putParcelable(key, value);
            return this;
        }

        public Builder putSize(String key, Size value) {
            data.putSize(key, value);
            return this;
        }

        public Builder putSizeF(String key, SizeF value) {
            data.putSizeF(key, value);
            return this;
        }

        public Builder putParcelableArray(String key, Parcelable[] value) {
            data.putParcelableArray(key, value);
            return this;
        }

        public Builder putParcelableArrayList(String key, ArrayList<? extends Parcelable> value) {
            data.putParcelableArrayList(key, value);
            return this;
        }

        public Builder putSparseParcelableArray(String key, SparseArray<? extends Parcelable> value) {
            data.putSparseParcelableArray(key, value);
            return this;
        }

        public Builder putIntegerArrayList(String key, ArrayList<Integer> value) {
            data.putIntegerArrayList(key, value);
            return this;
        }

        public Builder putStringArrayList(String key, ArrayList<String> value) {
            data.putStringArrayList(key, value);
            return this;
        }

        public Builder putCharSequenceArrayList(String key, ArrayList<CharSequence> value) {
            data.putCharSequenceArrayList(key, value);
            return this;
        }

        public Builder putSerializable(String key, Serializable value) {
            data.putSerializable(key, value);
            return this;
        }

        public Builder putByteArray(String key, byte[] value) {
            data.putByteArray(key, value);
            return this;
        }

        public Builder putShortArray(String key, short[] value) {
            data.putShortArray(key, value);
            return this;
        }

        public Builder putCharArray(String key, char[] value) {
            data.putCharArray(key, value);
            return this;
        }

        public Builder putFloatArray(String key, float[] value) {
            data.putFloatArray(key, value);
            return this;
        }

        public Builder putCharSequenceArray(String key, CharSequence[] value) {
            data.putCharSequenceArray(key, value);
            return this;
        }


        public Builder putBundle(String key, Bundle value) {
            data.putBundle(key, value);
            return this;
        }

        public Builder putBinder(String key, IBinder value) {
            data.putBinder(key, value);
            return this;
        }

    }
}
