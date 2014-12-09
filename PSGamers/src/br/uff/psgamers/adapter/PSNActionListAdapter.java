package br.uff.psgamers.adapter;

import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import br.uff.psgamers.R;
import br.uff.psgamers.pojo.ActionListItem;

public class PSNActionListAdapter extends BaseAdapter {

	private Context context;
	private List<ActionListItem> actionListItems;
	
	public PSNActionListAdapter(Context context, List<ActionListItem> actionListItems) {
		
		this.context = context;
		this.actionListItems = actionListItems;
	}
	
	public int getCount() {
		return actionListItems.size();
	}
	
	public Object getItem(int position) {
		return actionListItems.get(position);
	}
	
	public long getItemId(int position) {
		return position;
	}
	
	public View getView(int position, View convertView, ViewGroup parent) {
		
		ViewHolder viewHolder = null;
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		if (convertView == null) {
			
			convertView = inflater.inflate(R.layout.list_row, null);
			
			// Hold the view objects in an object, so they don't need to be re-fetched.
			viewHolder = new ViewHolder();
			viewHolder.actionListThumbnail = (ImageView) convertView.findViewById(R.id.action_list_image);
			viewHolder.actionListHeader = (TextView) convertView.findViewById(R.id.action_list_header);
			viewHolder.actionListSubheader = (TextView) convertView.findViewById(R.id.action_list_subheader);
		
			convertView.setTag(viewHolder);
		}
		else {
			
			viewHolder = (ViewHolder) convertView.getTag();
		}
		
		ActionListItem actionListItem = actionListItems.get(position);
		
		if (actionListItem != null) {
			
			Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), actionListItem.getThumbnailId());
			viewHolder.actionListThumbnail.setImageBitmap(bitmap);
			viewHolder.actionListHeader.setText(actionListItem.getHeader());
			viewHolder.actionListSubheader.setText(actionListItem.getSubheader());
		}
		
		return convertView;
	}
	
	protected static class ViewHolder {
		
		protected ImageView actionListThumbnail;
		protected TextView actionListHeader;
		protected TextView actionListSubheader;
	}

}