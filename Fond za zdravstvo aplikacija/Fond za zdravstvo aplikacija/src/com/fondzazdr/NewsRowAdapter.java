package com.fondzazdr;

import java.util.ArrayList;
import java.util.List;

import com.samir.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class NewsRowAdapter extends ArrayAdapter<Item> {

	private Activity activity;
	private List<Item> items = null;
	private Item objBean;
	private int row;
	private ArrayList<Item> listpicOrigin;

	public NewsRowAdapter(Activity act, int resource, List<Item> arrayList) {
		super(act, resource, arrayList);
		this.activity = act;
		this.row = resource;
		this.items = arrayList;
		this.listpicOrigin = new ArrayList<Item>();
		this.listpicOrigin.addAll(arrayList);
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

		// holder.idlek = (TextView) view.findViewById(R.id.idnalekot);
		holder.nazivlek = (TextView) view.findViewById(R.id.nazivnalekot);
		holder.idlek = (TextView) view.findViewById(R.id.idlek);
		// holder.cena = (TextView) view.findViewById(R.id.cena);
		// holder.idlek.setText(Html.fromHtml(objBean.getid()));
		holder.nazivlek.setText(Html.fromHtml(objBean.getnaziv()));
		holder.idlek.setText(Html.fromHtml(objBean.getid()));
		// holder.cena.setText(Html.fromHtml(objBean.getcena()));

		return view;
	}

	@SuppressLint("DefaultLocale")
	public void filterr(String charText) {
		charText = charText.toLowerCase();
		this.items.clear();
		if (charText.length() == 0) {
			this.items.addAll(listpicOrigin);
		} else {
			for (Item pic : listpicOrigin) {
				if (pic.getnaziv().toLowerCase().contains(charText)) {
					this.items.add(pic);
				}
			}
		}

		notifyDataSetChanged();
	}

	public class ViewHolder {
		public TextView idlek, nazivlek, proizvoditel, cena;
	}

}