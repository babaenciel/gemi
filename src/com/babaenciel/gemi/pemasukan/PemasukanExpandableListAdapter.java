package com.babaenciel.gemi.pemasukan;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;

import com.babaenciel.gemi.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

public class PemasukanExpandableListAdapter extends BaseExpandableListAdapter {

	//private String[] groups = { "Parent1", "Parent2","Parent3" };
	//private String[][] children = { { "Child1","Child1","Child1","Child1" },{ "Child2","Child2","Child2" }, { "Child3" },{ "Child4" }, { "Child5" } };
	private ArrayList<String> groups;
	private ArrayList<ArrayList<PemasukanObject>> children;
	private Context context;
	DecimalFormatSymbols simbol = new DecimalFormatSymbols();
	DecimalFormat customFormat;
	TextView judulChild;
	TextView tanggalChild;
	TextView nominalChild;
	TextView judulGroup;
	
	public PemasukanExpandableListAdapter(Context context, ArrayList<String> groups, ArrayList<ArrayList<PemasukanObject>> children) {
		this.context = context;
		this.groups = groups;
		this.children = children;
		simbol = new DecimalFormatSymbols(); //diformat dulu hasilnya biar ada titiknya		
		simbol.setGroupingSeparator('.');
		customFormat = new DecimalFormat("###,###,###",simbol);
	}
	
	@Override
	public Object getChild(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		//return children[groupPosition][childPosition];
		return children.get(groupPosition).get(childPosition);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return children.get(groupPosition).get(childPosition).id_pemasukan;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		
		if(convertView == null) {
			/*childHolder = new ChildHolder();
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.child_rows_pemasukan, null);
			childHolder.judul = (TextView) convertView.findViewById(R.id.judul_pemasukan);
			childHolder.nominal = (TextView) convertView.findViewById(R.id.nominal_pemasukan);
			childHolder.tanggal = (TextView) convertView.findViewById(R.id.tanggal_pemasukan);*/
			
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.child_rows_pemasukan, null);
			
		}		
				
		//childHolder.judul.setText(children[groupPosition][childPosition]);
		/*judulChild = (TextView) convertView.findViewById(R.id.judul_pemasukan);
		judulChild.setText(children[groupPosition][childPosition]);*/
		judulChild = (TextView) convertView.findViewById(R.id.judul_pemasukan);
		tanggalChild = (TextView) convertView.findViewById(R.id.tanggal_pemasukan);
		nominalChild = (TextView) convertView.findViewById(R.id.nominal_pemasukan);
		judulChild.setText(children.get(groupPosition).get(childPosition).nama);
		tanggalChild.setText(children.get(groupPosition).get(childPosition).tanggal);		
		nominalChild.setText(customFormat.format(children.get(groupPosition).get(childPosition).nominal));
		return convertView;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		//return children[groupPosition].length;
		return children.get(groupPosition).size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		// TODO Auto-generated method stub
		//return groups[groupPosition];
		return groups.get(groupPosition);
	}

	@Override
	public int getGroupCount() {
		// TODO Auto-generated method stub
		//return groups.length;
		return groups.size();
	}

	@Override
	public long getGroupId(int groupPosition) {
		// TODO Auto-generated method stub
		return groupPosition;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		
		if(convertView == null) {
			/*groupHolder = new GroupHolder();
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.group_rows_pemasukan, null);
			groupHolder.judul = (TextView) convertView.findViewById(R.id.pemasukan_expandablelist_group_text);*/
			
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.group_rows_pemasukan, null);
			
		}
		
		//groupHolder.judul.setText(groups[groupPosition]);
		judulGroup = (TextView) convertView.findViewById(R.id.pemasukan_expandablelist_group_text);
		//judulGroup.setText(groups[groupPosition]);
		judulGroup.setText(groups.get(groupPosition));
		return convertView;
	}

	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return true;
	}
	
	/*public static class ChildHolder {
		TextView judul;
		TextView tanggal;
		TextView nominal;
	}
	
	public static class GroupHolder {
		TextView judul;
	}*/

}
