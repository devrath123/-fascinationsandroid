package com.fascinations.ui;

import android.os.Parcel;
import android.os.Parcelable;

public class CouponBean implements Parcelable {
    int id;
    String coupon_name,coupon_detail,coupon_image;

    public CouponBean(){}

    protected CouponBean(Parcel in) {
        id = in.readInt();
        coupon_name = in.readString();
        coupon_detail = in.readString();
        coupon_image = in.readString();
    }

    public static final Creator<CouponBean> CREATOR = new Creator<CouponBean>() {
        @Override
        public CouponBean createFromParcel(Parcel in) {
            return new CouponBean(in);
        }

        @Override
        public CouponBean[] newArray(int size) {
            return new CouponBean[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCoupon_name() {
        return coupon_name;
    }

    public void setCoupon_name(String coupon_name) {
        this.coupon_name = coupon_name;
    }

    public String getCoupon_detail() {
        return coupon_detail;
    }

    public void setCoupon_detail(String coupon_detail) {
        this.coupon_detail = coupon_detail;
    }

    public String getCoupon_image() {
        return coupon_image;
    }

    public void setCoupon_image(String coupon_image) {
        this.coupon_image = coupon_image;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(coupon_name);
        dest.writeString(coupon_detail);
        dest.writeString(coupon_image);
    }
}
