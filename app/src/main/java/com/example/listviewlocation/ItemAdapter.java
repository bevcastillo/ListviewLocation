package com.example.listviewlocation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ItemAdapter extends BaseAdapter {

    Context context;
    ArrayList<Places> list;

    public ItemAdapter(Context context, ArrayList<Places> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if(convertView == null){
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.custom_listview,null,true);

            holder.textname = (TextView) convertView.findViewById(R.id.textViewLoc);
            holder.textlatitude = (TextView)convertView.findViewById(R.id.textLat);
            holder.textlongitude = (TextView)convertView.findViewById(R.id.textLng);

            convertView.setTag(holder);
        }else{
            //the getTag returns the ViewHolder object set as a tag
            holder = (ViewHolder)convertView.getTag();
        }

        //display
        holder.textname.setText(list.get(position).getName());
        holder.textlatitude.setText(list.get(position).getLatitude());
        holder.textlongitude.setText(list.get(position).getLongitude());

        return convertView;
    }

    private class ViewHolder{
        protected TextView textname, textlatitude, textlongitude;
    }
}
