package com.fascinations;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class SignupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Window window = getWindow();

// clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.pink));


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        final EditText et_email = findViewById(R.id.et_email);
        final EditText et_pwd = findViewById(R.id.et_pwd);
        final EditText et_fn = findViewById(R.id.et_fn);
        final EditText et_ln = findViewById(R.id.et_ln);


        Button btn_reg = findViewById(R.id.btn_reg);
        btn_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et_fn.getText().toString().length() > 0 && et_ln.getText().toString().length() > 0 &&
                        et_email.getText().toString().length() > 0 && et_pwd.getText().toString().length() > 0){
                    RequestParams rp = new RequestParams();
                    rp.add("name", et_fn.getText().toString() + et_ln.getText().toString());
                    rp.add("email", et_email.getText().toString());
                    rp.add("password",et_pwd.getText().toString());
                    rp.add("c_password",et_pwd.getText().toString());


                    HttpUtils.post("/register", rp, new JsonHttpResponseHandler(){
                                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                                    Log.d("asd", "---------------- this is response : " + response);
                                    try {
                                        JSONObject serverResp = new JSONObject(response.toString());
                                        JSONObject tokenJsonObject = serverResp.getJSONObject("success");
                                        String token = tokenJsonObject.getString("token");
                                        SharedPreferences sp = getSharedPreferences("F",MODE_PRIVATE);
                                        sp.edit().putString("token",token).apply();
                                        startActivity(new Intent(SignupActivity.this, MainActivity.class));
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }

                                @Override
                                public void onSuccess(int statusCode, Header[] headers, JSONArray timeline) {
                                    Log.d("asd", "---------------- this is response : " + timeline);
                                }

                                @Override
                                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                                    super.onFailure(statusCode, headers, throwable, errorResponse);
                                    Toast.makeText(SignupActivity.this, "Check fields",Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                                    super.onFailure(statusCode, headers, responseString, throwable);
                                }
                            }
                    );
                }else{
                    Toast.makeText(SignupActivity.this, "Enter all fields.",Toast.LENGTH_SHORT).show();
                }
            }
        });

        TextView tv_login = findViewById(R.id.tv_login);
        tv_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        TextView terms = findViewById(R.id.terms_condition);
        terms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://www.fascinations.net/terms-conditions");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

    }
}
