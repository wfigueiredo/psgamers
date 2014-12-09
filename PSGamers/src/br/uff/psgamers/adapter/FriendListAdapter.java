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

import com.krobothsoftware.psn.model.PsnFriendData;

public class FriendListAdapter extends BaseAdapter {

	private Context context;
	private List<PsnFriendData> psnFriendList;
	
	public FriendListAdapter(Context context, List<PsnFriendData> psnFriendList) {
		
		this.context = context;
		this.psnFriendList = psnFriendList;
	}
	
	public int getCount() {
		return psnFriendList.size();
	}

	public Object getItem(int position) {
		return psnFriendList.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		
		ViewHolder viewHolder = null;
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		if (convertView == null) {
			
			convertView = inflater.inflate(R.layout.friend_list_row, null);
			
			// Hold the view objects in an object, so they don't need to be re-fetched.
			viewHolder = new ViewHolder();
			viewHolder.psnFriendAvatar = (ImageView) convertView.findViewById(R.id.psnFriendAvatar);
			viewHolder.psnFriendStatus = (ImageView) convertView.findViewById(R.id.psnFriendStatusIcon);
			viewHolder.psnFriendId = (TextView) convertView.findViewById(R.id.psnFriendId);
			viewHolder.psnFriendLevel = (TextView) convertView.findViewById(R.id.psnFriendLevel);
			viewHolder.psnGamePlaying = (ImageView) convertView.findViewById(R.id.psnGamePlayingIcon);
			viewHolder.psnGamePlayingTitle = (TextView) convertView.findViewById(R.id.psnGamePlaying);
		
			convertView.setTag(viewHolder);
		}
		else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		
		PsnFriendData psnFriendData = psnFriendList.get(position);
		
		if (psnFriendData != null) {
			
			// Friend Avatar
			DownloadImageTask downloadGameImageTask = new DownloadImageTask(viewHolder.psnFriendAvatar);
			downloadGameImageTask.execute(psnFriendData.getAvatar());

			String currentGame = psnFriendData.getCurrentGame();
			
			// Friend Status
			switch (psnFriendData.getCurrentStatus()) {
			
				case ONLINE:
					viewHolder.psnFriendStatus.setImageResource(R.drawable.psn_status_online);

					// Display joystick icon, if currently playing.
					if (currentGame != null) {
						viewHolder.psnGamePlaying.setVisibility(View.VISIBLE);
					}
					break;
					
				case OFFLINE:
					viewHolder.psnFriendStatus.setImageResource(R.drawable.psn_status_offline);
					viewHolder.psnGamePlaying.setVisibility(View.INVISIBLE);
					break;
					
				case AWAY:
					viewHolder.psnFriendStatus.setImageResource(R.drawable.psn_status_away);
					break;
					
				case PENDING_RESPONSE:
					viewHolder.psnFriendStatus.setImageResource(R.drawable.psn_status_pending);
					viewHolder.psnGamePlaying.setVisibility(View.INVISIBLE);
					break;
	
				default:
					break;
			}
			
			// Friend PSN-ID
			viewHolder.psnFriendId.setText(psnFriendData.getPsnId());
			
			// Friend Level
			viewHolder.psnFriendLevel.setText(Integer.toString(psnFriendData.getLevel()));
			
			// Current game
			viewHolder.psnGamePlayingTitle.setText(currentGame);
		}
		
		return convertView;
	}

	protected static class ViewHolder {
		
		protected ImageView psnFriendAvatar;
		protected ImageView psnFriendStatus;
		protected TextView psnFriendId;
		protected TextView psnFriendLevel;
		protected ImageView psnGamePlaying;
		protected TextView psnGamePlayingTitle;
	}
}