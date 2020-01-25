package com.fascinations;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class LoginActivity extends AppCompatActivity {

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
        setContentView(R.layout.activity_login);

        final EditText et_email = findViewById(R.id.et_email);
        final EditText et_pwd = findViewById(R.id.et_pwd);

        Button btn_login = findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et_email.getText().toString().length() > 0 && et_pwd.getText().toString().length() > 0){
                    RequestParams rp = new RequestParams();
                    rp.add("email", et_email.getText().toString());
                    rp.add("password",et_pwd.getText().toString());

                    HttpUtils.post("/login", rp, new JsonHttpResponseHandler(){
                                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                                    Log.d("asd", "---------------- this is response : " + response);
                                    try {
                                        JSONObject serverResp = new JSONObject(response.toString());
                                        JSONObject tokenJsonObject = serverResp.getJSONObject("success");
                                        String token = tokenJsonObject.getString("token");
                                        SharedPreferences sp = getSharedPreferences("F",MODE_PRIVATE);
                                        sp.edit().putString("token",token).apply();
                                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
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
                            Toast.makeText(LoginActivity.this, "Check Email & Password",Toast.LENGTH_SHORT).show();
                        }

                        @Override
                                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                                    super.onFailure(statusCode, headers, responseString, throwable);
                                }
                            }
                    );
                }else{
                    Toast.makeText(LoginActivity.this, "Enter both Email & Password",Toast.LENGTH_SHORT).show();
                }
            }
        });

        TextView tv_signup = findViewById(R.id.tv_signup);
        tv_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, SignupActivity.class));
            }
        });

        TextView forgot_pwd = findViewById(R.id.forgot_pwd);
        forgot_pwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("http://www.storiesforgames.com/fascinations/password/reset");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
    }

}
