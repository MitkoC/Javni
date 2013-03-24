package com.example.javnimobilnimrezilab1;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Customadapter extends ArrayAdapter<Vreme>{

    Context context; 
    int layoutResourceId;    
    Vreme data[] = null;
   
    public Customadapter(Context context, int layoutResourceId, Vreme[] data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        VremeHolder holder = null;
        
        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            
            holder = new VremeHolder();
            holder.imgIcon = (ImageView)row.findViewById(R.id.imgIcon);
            holder.txtTitle = (TextView)row.findViewById(R.id.txtTitle);
         //   holder.txtTitle2 = (TextView)row.findViewById(R.id.txtTitle2);
            row.setTag(holder);
        }
        else
        {
            holder = (VremeHolder)row.getTag();
        }
        
        Vreme vreme = data[position];
        holder.txtTitle.setText(vreme.grad);
        
        holder.imgIcon.setImageResource(vreme.icon);
        
        return row;
        
        
        
    }
    
    static class VremeHolder
    {
        
		ImageView imgIcon;
        TextView txtTitle;
        //TextView txtTitle2;
    }
}
