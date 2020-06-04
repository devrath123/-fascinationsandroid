package com.fascinations.ui.home;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.fascinations.BannerPagerAdapter;
import com.fascinations.CouponAdapter;
import com.fascinations.R;
import com.fascinations.ui.CouponBean;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeFragment extends Fragment {


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);
        final ListView listView = root.findViewById(R.id.list_view);
        final ViewPager viewPager = root.findViewById(R.id.view_pager);

        // Banners API Call

        SharedPreferences sp = getActivity().getSharedPreferences("F", Context.MODE_PRIVATE);
        final String token = sp.getString("token", "");

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("Authorization", "Bearer " + token);
            jsonObject.put("Accept", "application/json");
        }catch (Exception e){
            e.printStackTrace();
        }
        RequestQueue queue = Volley.newRequestQueue(getActivity());

        final List<String> bannerList = new ArrayList<>();

        JsonObjectRequest bannerJsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, "http://fascinations.streetkart.in/api/banners",null ,new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            Log.i("Response : ", response.toString());
                            bannerList.clear();
                            JSONArray jsonArray = response.getJSONArray("bannerlist");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                bannerList.add(jsonObject.getString("banner_url"));
                            }

                            BannerPagerAdapter bannerPagerAdapter = new BannerPagerAdapter(getActivity(), bannerList);
                            viewPager.setAdapter(bannerPagerAdapter);

                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("Error : ",error.toString());

                    }
                })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("Authorization", "Bearer " + token);
                params.put("Accept", "application/json");

                return params;
            }
        };

        queue.add(bannerJsonObjectRequest);


        // Coupons API call

        final List<CouponBean> couponBeanList = new ArrayList<>();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, "http://fascinations.streetkart.in/api/getallcoupon",null ,new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            couponBeanList.clear();
                            JSONArray jsonArray = response.getJSONArray("couponlist");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                CouponBean couponBean = new CouponBean();
                                couponBean.setId(jsonObject.getInt("id"));
                                couponBean.setCoupon_detail(jsonObject.getString("coupon_detail"));
                                couponBean.setCoupon_name(jsonObject.getString("coupon_name"));
                                couponBean.setCoupon_image(jsonObject.getString("coupon_image"));

                                couponBeanList.add(couponBean);

                            }

                            CouponAdapter couponAdapter = new CouponAdapter(getActivity(), couponBeanList);
                            listView.setAdapter(couponAdapter);
                            couponAdapter.notifyDataSetChanged();

                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("Error : ",error.toString());

                    }
                })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("Authorization", "Bearer " + token);
                params.put("Accept", "application/json");

                return params;
            }
        };

        queue.add(jsonObjectRequest);



        return root;
    }
}