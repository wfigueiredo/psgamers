package br.uff.psgamers.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import br.uff.psgamers.R;
import br.uff.psgamers.task.DownloadImageTask;

import com.krobothsoftware.psn.model.PsnGameData;

public class GameListAdapter extends BaseAdapter {

	private List<PsnGameData> gameList;
	private Context context;

	public GameListAdapter(Context context, List<PsnGameData> gameList) {
		
		this.context = context;
		this.gameList = gameList;
	}
	
	public int getCount() {
		return gameList.size();
	}
	
	public Object getItem(int index) {
		return gameList.get(index);
	}
	
	public long getItemId(int position) {
		return position;
	}
	
	public View getView(int position, View convertView, ViewGroup parent) {
		
		ViewHolder viewHolder = null;
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		if (convertView == null) {
			
			convertView = inflater.inflate(R.layout.game_list_row, null);
			
			// Hold the view objects in an object, so they don't need to be re-fetched.
			viewHolder = new ViewHolder();
			
			viewHolder.gameThumbnail = (ImageView) convertView.findViewById(R.id.gameThumbnail_list);
			viewHolder.gameTitle = (TextView) convertView.findViewById(R.id.gameTitle_list);
			viewHolder.gameProgressBar = (ProgressBar) convertView.findViewById(R.id.gameProgressBar_list);
			viewHolder.gameProgressPercent = (TextView) convertView.findViewById(R.id.gameProgressPercent);
			viewHolder.platinumTrophy = (ImageView) convertView.findViewById(R.id.platinum_trophy_list);
			viewHolder.bronzeTrophyCount = (TextView) convertView.findViewById(R.id.bronze_trophies_earned);
			viewHolder.silverTrophyCount = (TextView) convertView.findViewById(R.id.silver_trophies_earned);
			viewHolder.goldTrophyCount = (TextView) convertView.findViewById(R.id.gold_trophies_earned);
		
			convertView.setTag(viewHolder);
		}
		else {
			
			viewHolder = (ViewHolder) convertView.getTag();
		}
		
		PsnGameData psnGameData = gameList.get(position);
		
		if (psnGameData != null) {
			
			int progress = psnGameData.getProgress();
			String progressDisplay = Integer.toString(progress);

			// Game thumbnail
			DownloadImageTask downloadGameImageTask = new DownloadImageTask(viewHolder.gameThumbnail);
			downloadGameImageTask.execute(psnGameData.getGameImage());
			
			// Title
			viewHolder.gameTitle.setText(psnGameData.getName());
			
			// Visual Progress
			viewHolder.gameProgressBar.setProgress(progress);
			
			// Percentage Progress
			viewHolder.gameProgressPercent.setText(progressDisplay);
			
			// Display platinum trophy, if earned
			if (psnGameData.getPlatinum() > 0) {
				viewHolder.platinumTrophy.setVisibility(View.VISIBLE);
			}
			else {
				viewHolder.platinumTrophy.setVisibility(View.INVISIBLE);
			}
			
			// Bronze trophy count
			viewHolder.bronzeTrophyCount.setText(Integer.toString(psnGameData.getBronze()));
			
			// Silver trophy count
			viewHolder.silverTrophyCount.setText(Integer.toString(psnGameData.getSilver()));
			
			// Gold trophy count
			viewHolder.goldTrophyCount.setText(Integer.toString(psnGameData.getGold()));
		}
		
		return convertView;
	}
	
	protected static class ViewHolder {
		
		protected ImageView gameThumbnail;
		protected TextView gameTitle;
		protected ProgressBar gameProgressBar;
		protected TextView gameProgressPercent;
		protected ImageView platinumTrophy;
		protected TextView bronzeTrophyCount;
		protected TextView silverTrophyCount;
		protected TextView goldTrophyCount;
	}
}