package com.lanou.mirror.activity;

import android.widget.ListView;

import com.lanou.mirror.R;
import com.lanou.mirror.adapter.MyAllAddressAdapter;
import com.lanou.mirror.base.BaseActivity;

/**
 * Created by Yi on 16/4/8.
 */
public class MyAllAddressActivity extends BaseActivity {
    private ListView listView;
    private MyAllAddressAdapter adapter;
    @Override
    protected int setContent() {
        return R.layout.activity_myalladdress;
    }

    @Override
    protected void initView() {
        listView = bindView(R.id.activity_myaddress_listview);
        adapter = new MyAllAddressAdapter();
        listView.setAdapter(adapter);

    }

    @Override
    protected void initData() {

    }
}
