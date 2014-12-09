package br.uff.psgamers.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import br.uff.psgamers.R;
import br.uff.psgamers.task.DownloadImageTask;

import com.krobothsoftware.psn.model.PsnTrophyData;

public class TrophyListAdapter extends BaseAdapter {

	private Context context;
	private List<PsnTrophyData> trophyList; 
	
	public TrophyListAdapter(Context context, List<PsnTrophyData> trophyList) {
		
		this.context = context;
		this.trophyList = trophyList;
	}
	
	public int getCount() {
		return trophyList.size();
	}
	
	public Object getItem(int position) {
		return trophyList.get(position);
	}
	
	public long getItemId(int position) {
		return trophyList.get(position).getTrophyId();
	}
	
	public View getView(int position, View convertView, ViewGroup parent) {
		
		ViewHolder viewHolder = null;
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		if (convertView == null) {
			
			convertView = inflater.inflate(R.layout.trophy_list_row, null);
			
			// Hold the view objects in an object, so they don't need to be re-fetched.
			viewHolder = new ViewHolder();
			viewHolder.trophyThumbnail = (ImageView) convertView.findViewById(R.id.trophyThumbnail_list);
			viewHolder.trophyType = (ImageView) convertView.findViewById(R.id.trophyType_list);
			viewHolder.trophyTitle = (TextView) convertView.findViewById(R.id.trophyTitle_list);
			viewHolder.trophyDesc = (TextView) convertView.findViewById(R.id.trophyDesc_list);
		
			convertView.setTag(viewHolder);
		}
		else {
			
			viewHolder = (ViewHolder) convertView.getTag();
		}
		
		PsnTrophyData psnTrophyData = (PsnTrophyData) trophyList.get(position);
		
		if (psnTrophyData != null) {
			
			// Trophy Image
			if (psnTrophyData.isReceived()) {
				
				DownloadImageTask downloadGameImageTask = new DownloadImageTask(viewHolder.trophyThumbnail);
				downloadGameImageTask.execute(psnTrophyData.getTrophyImageUrl());
			}
			else {
				// User has not earned the trophy yet
				viewHolder.trophyThumbnail.setImageResource(R.drawable.locked_trophy_icon);
			}
			
			// Trophy Type
			switch (psnTrophyData.getTrophyType()) {
			
				case PLATINUM:
					viewHolder.trophyType.setImageResource(R.drawable.psn_platinum);
					break;
				
				case GOLD:
					viewHolder.trophyType.setImageResource(R.drawable.psn_gold);
					break;
					
				case SILVER:
					viewHolder.trophyType.setImageResource(R.drawable.psn_silver);
					break;
					
				case BRONZE:
					viewHolder.trophyType.setImageResource(R.drawable.psn_bronze);
					break;
	
				default:
					viewHolder.trophyType.setImageResource(R.drawable.psn_hidden);
					break;
			}
			
			if (psnTrophyData.isHidden()) {
				
				viewHolder.trophyTitle.setText(context.getResources().getString(R.string.hiddenTrophy));
			}
			else {
				
				viewHolder.trophyTitle.setText(psnTrophyData.getTrophyName());
			}
			
			viewHolder.trophyDesc.setText(psnTrophyData.getDescription());
		}
		
		return convertView;
	}

	protected static class ViewHolder {
		
		protected ImageView trophyThumbnail;
		protected ImageView trophyType;
		protected TextView trophyTitle;
		protected TextView trophyDesc;
	}
}