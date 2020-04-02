package com.kodingindonesia.mycrud.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.kodingindonesia.mycrud.R;
import com.kodingindonesia.mycrud.datamaster.TampilSemuaTanah;
import com.kodingindonesia.mycrud.data.DataModelTanah;

import java.util.List;

/**
 * Created by KUNCORO on 23/04/2017.
 */

public class Adapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<DataModelTanah> item;

    public Adapter(Activity activity, List<DataModelTanah> item) {
        this.activity = activity;
        this.item = item;
    }

    public Adapter(TampilSemuaTanah activity, List<com.kodingindonesia.mycrud.datamaster.DataModelTanah> listData) {


    }


    @Override
    public int getCount() {
        return item.size();
    }

    @Override
    public Object getItem(int location) {
        return item.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null)
            convertView = inflater.inflate(R.layout.list_item, null);

        TextView txt_nama = (TextView) convertView.findViewById(R.id.name);

        txt_nama.setText(item.get(position).getNama());

        return convertView;
    }
}
