package com.lanou.mirror.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.lanou.mirror.R;

/**
 * Created by dllo on 16/4/5.
 */
public class AddAddressActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView submitTv;
    private EditText nameEt, numberEt, addressEt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addaddress);
        submitTv = (TextView) findViewById(R.id.activity_addaddress_submitaddress);
        submitTv.setOnClickListener(this);
        nameEt = (EditText) findViewById(R.id.activity_addaddress_name_et);
        numberEt = (EditText) findViewById(R.id.activity_addaddress_number_et);
        addressEt = (EditText) findViewById(R.id.activity_addaddress_address_et);

    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        intent.putExtra("name", nameEt.getText().toString());
        intent.putExtra("number", numberEt.getText().toString());
        intent.putExtra("address", addressEt.getText().toString());
        if (nameEt.getText().length() > 0 && numberEt.getText().length() > 0 && addressEt.getText().length() > 0) {
            setResult(202, intent);
            finish();

        } else {
            Toast.makeText(this, "请完整的填写", Toast.LENGTH_SHORT).show();
        }
    }
}
