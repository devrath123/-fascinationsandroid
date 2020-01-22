package com.fascinations.ui.gallery;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.fascinations.HttpUtils;
import com.fascinations.LoginActivity;
import com.fascinations.MainActivity;
import com.fascinations.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.HeaderElement;

public class GalleryFragment extends Fragment {

    private GalleryViewModel galleryViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                ViewModelProviders.of(this).get(GalleryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);

        final WebView webView = root.findViewById(R.id.webview);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);

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

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, "http://www.storiesforgames.com/fascinations/api/url",null ,new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("urls");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                if (jsonObject.getString("name").equals("store locator")) {
                                    webView.loadUrl(jsonObject.getString("url"));
                                }
                            }
                        }catch (Exception e){

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

        // Http CAll

       // rp.add("Authorization", "Bearer " + token);
       // rp.add("Accept", "application/json");

        HashMap<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("Authorization", "Bearer " + token);
        paramMap.put("Accept", "application/json");
        RequestParams rp = new RequestParams(paramMap);

        AsyncHttpClient client = new AsyncHttpClient();
        client.setBasicAuth("Authorization","Bearer " + token);
        client.setBasicAuth("Accept", "application/json");

//        client.get( "http://www.storiesforgames.com/fascinations/api/url",rp,new JsonHttpResponseHandler() {
//                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
//
//                        Log.d("asd", "---------------- this is response : " + response);
//                        try {
//                            JSONObject serverResp = new JSONObject(response.toString());
//                            JSONArray jsonArray = serverResp.getJSONArray("urls");
//                            for (int i = 0; i < jsonArray.length(); i++) {
//                                JSONObject jsonObject = jsonArray.getJSONObject(i);
//                                if (jsonObject.getString("name").equals("storelocator")) {
//                                    webView.loadUrl(jsonObject.getString("url"));
//                                }
//                            }
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//
//                    @Override
//                    public void onSuccess(int statusCode, Header[] headers, JSONArray timeline) {
//                        Log.d("asd", "---------------- this is response : " + timeline);
//                    }
//
//                    @Override
//                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
//                        super.onFailure(statusCode, headers, throwable, errorResponse);
//                    }
//
//                    @Override
//                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
//                        super.onFailure(statusCode, headers, responseString, throwable);
//                    }
//                });

//        HttpUtils.get("url" ,rp, new JsonHttpResponseHandler() {
//                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
//
//                        Log.d("asd", "---------------- this is response : " + response);
//                        try {
//                            JSONObject serverResp = new JSONObject(response.toString());
//                            JSONArray jsonArray = serverResp.getJSONArray("urls");
//                            for (int i = 0; i < jsonArray.length(); i++) {
//                                JSONObject jsonObject = jsonArray.getJSONObject(i);
//                                if (jsonObject.getString("name").equals("storelocator")) {
//                                    webView.loadUrl(jsonObject.getString("url"));
//                                }
//                            }
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//
//                    @Override
//                    public void onSuccess(int statusCode, Header[] headers, JSONArray timeline) {
//                        Log.d("asd", "---------------- this is response : " + timeline);
//                    }
//
//                    @Override
//                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
//                        super.onFailure(statusCode, headers, throwable, errorResponse);
//                    }
//
//                    @Override
//                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
//                        super.onFailure(statusCode, headers, responseString, throwable);
//                    }
//                }
//        );

        return root;
    }
}