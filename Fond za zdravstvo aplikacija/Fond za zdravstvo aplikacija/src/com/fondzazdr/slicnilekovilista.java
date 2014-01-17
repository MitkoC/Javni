package com.fondzazdr;

import java.util.List;

import com.samir.R;

import android.app.Activity;
import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class slicnilekovilista extends ArrayAdapter<Item> {

	private Activity activity;
	private List<Item> items;
	private Item objBean;
	private int row;

	public slicnilekovilista(Activity act, int resource, List<Item> arrayList) {
		super(act, resource, arrayList);
		this.activity = act;
		this.row = resource;
		this.items = arrayList;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		View view = convertView;
		ViewHolder holder;
		
		if (view == null) {
			LayoutInflater inflater = (LayoutInflater) activity
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(row, null);

			holder = new ViewHolder();
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}

		if ((items == null) || ((position + 1) > items.size()))
			return view;

		objBean = items.get(position);

	
		
		
		holder.slicni = (TextView) view.findViewById(R.id.slicni);
     	holder.idlekslicni = (TextView) view.findViewById(R.id.idleks);	
	
		holder.slicni.setText(Html.fromHtml(objBean.getslicni()));
		holder.idlekslicni.setText(Html.fromHtml(objBean.getslicniid()));
		
	
		

		return view;
	}

	public class ViewHolder {
		public TextView idlekslicni, nazivlek, proizvoditel,cena,slicni;
	}
}