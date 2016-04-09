package com.lanou.mirror.activity;

import android.util.Log;
import android.widget.ListView;

import com.lanou.mirror.R;
import com.lanou.mirror.adapter.MyAllAddressAdapter;
import com.lanou.mirror.base.BaseActivity;
import com.lanou.mirror.bean.MyAllAddressBeans;
import com.lanou.mirror.listener.OkHttpNetHelperListener;
import com.lanou.mirror.listener.UrlListener;
import com.lanou.mirror.tools.OkHttpNetHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Yi on 16/4/8.
 */
public class MyAllAddressActivity extends BaseActivity implements UrlListener, OkHttpNetHelperListener<MyAllAddressBeans> {

    private ListView listView;
    private MyAllAddressAdapter adapter;
    // private List<MyAllAddressBeans> data;
    private String token = "08f46330b2634ed9ddb0dbf9be876379";//付翼的电话 token

    @Override
    protected int setContent() {
        return R.layout.activity_myalladdress;
    }

    @Override
    protected void initView() {
        listView = bindView(R.id.activity_myaddress_listview);

    }

    @Override
    protected void initData() {
        HashMap<String, String> params = new HashMap<>();
        params.put("token", token);
        params.put("device_type", "3");
        params.put("page", "");

        //data = new ArrayList<>();
        OkHttpNetHelper.getOkHttpNetHelper().postRequest(USER_ADDRESS_LIST_URL, params, MyAllAddressBeans.class, this);
    }

    @Override
    public void requestSucceed(String result, MyAllAddressBeans bean) {
        //  data.add(bean);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                //Log.d("??", "" + data.get(0).getData().getList().size());
            }
        });

        adapter = new MyAllAddressAdapter(bean.getData().getList(), this);
        listView.setAdapter(adapter);
    }

    @Override
    public void requestFailed(String cause) {

    }
}
