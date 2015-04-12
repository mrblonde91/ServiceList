package com.example.serviceproject;

import java.util.List;

import android.content.ClipData.Item;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ServiceAdapter extends BaseAdapter {

public ServiceAdapter(Context context, int textViewResourceId) {
    super(context, textViewResourceId);
}

public ServiceAdapter(Context context, int resource, List<Item> items) {
    super(context, resource, items);
}

@Override
public View getView(int position, View convertView, ViewGroup parent) {

    View v = convertView;

    if (v == null) {

        LayoutInflater vi;
        vi = LayoutInflater.from(getContext());
        v = vi.inflate(R.layout.rows, null);

    }

    Item p = getItem(position);

    if (p != null) {

        TextView name = (TextView) v.findViewById(R.id.name);
        TextView description= (TextView) v.findViewById(R.id.description);
        TextView url = (TextView) v.findViewById(R.id.address);
        TextView type = (TextView) v.findViewById(R.id.type);

        if (name != null) {
           name.setText(p.getName());
        }
        if (description != null) {

            description.setText(p.getDescription());
        }
        if (url != null) {

            url.setText(p.getUrl());
        }
        if (url != null) {

            type.setText(p.getType());
        }
    }

    return v;

}
}