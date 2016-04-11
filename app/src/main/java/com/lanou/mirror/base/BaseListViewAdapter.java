package com.lanou.mirror.base;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by Yi on 16/4/1.
 */
public abstract class BaseListViewAdapter<T> extends BaseAdapter {

    protected List<T> data;
    protected Context context;

    public BaseListViewAdapter(List<T> data, Context context) {
        this.data = data;
        this.context = context;
    }

    //适配器增加数据方法
    protected void addData(List<T> list) {
        data.addAll(list);
        notifyDataSetChanged();
    }


    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public T getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(getItemResource(), null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        return getItemVIew(position, viewHolder, convertView);
    }

    //绑定组件抽象方法
    protected abstract View getItemVIew(int position, ViewHolder holder, View convertView);

    //载入布局的抽象方法
    protected abstract int getItemResource();


    protected class ViewHolder {
        private SparseArray<View> views = new SparseArray<>();
        private View convertView;

        public ViewHolder(View convertView) {
            this.convertView = convertView;
        }
        public <T extends View> T getView(int resId) {
            View view = views.get(resId);
            if (view == null) {
                view = convertView.findViewById(resId);
                views.put(resId, view);
            }
            return (T) view;
        }
    }
}
