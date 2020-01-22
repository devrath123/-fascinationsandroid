package com.fascinations;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.fascinations.ui.CouponBean;
import com.squareup.picasso.Picasso;

public class CouponDetailsActivity extends AppCompatActivity {

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
        setContentView(R.layout.activity_coupon_details);

        CouponBean couponBean = getIntent().getParcelableExtra("coupon");

        ImageView imageView = findViewById(R.id.image);
        TextView coupon = findViewById(R.id.coupon);
        TextView details = findViewById(R.id.details);
        ImageView back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        coupon.setText(couponBean.getCoupon_name());
        details.setText(couponBean.getCoupon_detail());
        Picasso.get()
                .load(couponBean.getCoupon_image())
                .into(imageView);
    }
}
