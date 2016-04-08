package com.lanou.mirror.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.lanou.mirror.R;

/**
 * Created by dllo on 16/4/1.
 */
public class BuyDetailsActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView writeAddressTv, nameTv, numberTv, addressTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buydetails);
        writeAddressTv = (TextView) findViewById(R.id.activity_buydetails_writeaddress_tv);
        nameTv = (TextView) findViewById(R.id.activity_buydetails_name);
        numberTv = (TextView) findViewById(R.id.activity_buydetails_number);
        addressTv = (TextView) findViewById(R.id.activity_buydetails_address);
        writeAddressTv.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(BuyDetailsActivity.this, AddAddressActivity.class);
        startActivityForResult(intent, 101);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {


            String name = data.getStringExtra("name");
            String number = data.getStringExtra("number");
            String address = data.getStringExtra("address");
            if (number.length() > 0 && address.length() > 0) {
                addressTv.setVisibility(View.VISIBLE);
                numberTv.setVisibility(View.VISIBLE);
                addressTv.setText("地址 :" + address);
                numberTv.setText(number);
            }
            nameTv.setText("收件人 :" + name);
            writeAddressTv.setText("更改地址");
        }
    }
}
