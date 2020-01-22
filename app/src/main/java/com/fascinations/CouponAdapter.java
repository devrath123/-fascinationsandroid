package com.fascinations;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fascinations.ui.CouponBean;

import java.util.ArrayList;
import java.util.List;

public class CouponAdapter extends BaseAdapter {

    Context context;
    List<CouponBean> couponBeanList = new ArrayList<>();

    public CouponAdapter(Context context , List<CouponBean> couponBeans){
        this.context = context;
        this.couponBeanList.addAll(couponBeans);
    }

    @Override
    public int getCount() {
        return couponBeanList.size();
    }

    @Override
    public Object getItem(int position) {
        return couponBeanList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return couponBeanList.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CouponBean couponBean = couponBeanList.get(position);
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view;
        view = layoutInflater.inflate(R.layout.coupon_row, null);
        TextView coupon, details;
        final LinearLayout ll;
        coupon = view.findViewById(R.id.coupon);
        details = view.findViewById(R.id.details);
        ll = view.findViewById(R.id.ll);
        ll.setId(position);
        ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = ll.getId();
                Intent intent = new Intent(context, CouponDetailsActivity.class);
                intent.putExtra("coupon",couponBeanList.get(pos));
                context.startActivity(intent);
            }
        });
        coupon.setText(""+couponBean.getCoupon_name());
        details.setText(""+couponBean.getCoupon_detail());
        return view;
    }
}
