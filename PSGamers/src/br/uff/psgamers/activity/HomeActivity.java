package br.uff.psgamers.activity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import br.uff.psgamers.R;
import br.uff.psgamers.adapter.PSNActionListAdapter;
import br.uff.psgamers.application.PSNApplication;
import br.uff.psgamers.constant.Constants;
import br.uff.psgamers.pojo.ActionListItem;
import br.uff.psgamers.task.DownloadImageTask;

import com.krobothsoftware.psn.PlayStationNetworkException;
import com.krobothsoftware.psn.client.PlayStationNetworkClient;
import com.krobothsoftware.psn.model.PsnProfileData;

public class HomeActivity extends Activity {
	
	private PsnProfileData psnProfileData;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home);
		
		// ------------------------------ PSN Profile data ----------------------------------------- //
		try {
			PlayStationNetworkClient psnClient = ((PSNApplication) getApplication()).getPsnClient();
			psnProfileData = psnClient.getOfficialProfile(psnClient.getClientJid());
			fillUserProfileData(psnProfileData, psnClient);
		} catch (PlayStationNetworkException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// ------------------------------- Trophy Ladder ------------------------------------------ //
		Button trophyLadderBtn = (Button) findViewById(R.id.trophyLadderBtn);
		trophyLadderBtn.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				createTrophyLadderDialog();
			}
		});
		
		// ------------------------------ ListView Actions ----------------------------------------- //
		ListView listViewActions = (ListView) findViewById(R.id.actionList);
		listViewActions.setAdapter(createActionListAdapter());
		listViewActions.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View arg1, int position, long arg3) {
				
				Intent intent = new Intent();
				
				switch (position) {
					
					case 0:
						intent.setAction(Constants.ACTION_ACTIVITY_FRIEND_LIST_TAB);
						break;
						
					case 1:
						intent.setAction(Constants.ACTION_ACTIVITY_GAME_LIST);
						break;
	
					default:
						intent.setAction(Constants.ACTION_ACTIVITY_HOME);
						break;
				}
				
				startActivity(intent);
			}
		});
	}
	
	private void fillUserProfileData(PsnProfileData psnProfile, PlayStationNetworkClient psnClient) {

		// PSN Avatar
		String avatarUrl = psnProfile.getAvatar();
		ImageView psnAvatar = (ImageView) findViewById(R.id.psnAvatar);
		DownloadImageTask downloadImageTask = new DownloadImageTask(psnAvatar);
		downloadImageTask.execute(avatarUrl);
		
		// PSN Plus
		ImageView psnPlusIcon = (ImageView) findViewById(R.id.psnPlus);
		if (psnProfile.hasPlayStationPlus()) {
			psnPlusIcon.setVisibility(View.VISIBLE);
		} 
		else {
			psnPlusIcon.setVisibility(View.INVISIBLE);
		}
		
		// PSN About me
		String aboutMeDisplay = psnProfile.getAboutMe();
		TextView aboutMe = (TextView) findViewById(R.id.psnAboutMe);
		
		if (aboutMeDisplay != null) {
			aboutMe.setText(aboutMeDisplay);
		}
		else {
			aboutMe.setVisibility(View.INVISIBLE);
		}
		
		// PSN Id
		String psnIdDisplay = psnClient.getClientUserInfo().getPsnId();
		TextView psnId = (TextView) findViewById(R.id.psnId);
		psnId.setText(psnIdDisplay);
		
		// PSN Level
		String psnLevelDisplay = Integer.toString(psnProfile.getLevel());
		TextView psnLevel = (TextView) findViewById(R.id.psnLevel);
		psnLevel.setText(psnLevelDisplay);
		
		// PSN Trophy Count
		String psnTrophyCountDisplay = Integer.toString(psnProfile.getTrophyCount());
		TextView psnTrophyCount = (TextView) findViewById(R.id.psnTrophyCount);
		psnTrophyCount.setText(psnTrophyCountDisplay);
		
		// PSN Progress/Progress Bar
		int progressDisplay = psnProfile.getProgress();
		
		ProgressBar psnProgress = (ProgressBar) findViewById(R.id.psnProgressBar);
		psnProgress.setProgress(progressDisplay);
		
		TextView psnProgressPercent = (TextView) findViewById(R.id.psnProgressPercent);
		psnProgressPercent.setText(Integer.toString(progressDisplay));
	}

	private PSNActionListAdapter createActionListAdapter() {
		
		Resources resources = getResources();
		
		List<ActionListItem> actionListItems = new ArrayList<ActionListItem>();
		int[] actionListThumbnails = Constants.ACTION_LIST_THUMBNAILS;
		String[] actionListHeaderItems = resources.getStringArray(R.array.action_list_header_items);
		String[] actionListSubHeaderItems = resources.getStringArray(R.array.action_list_subheader_items);
		
		for (int i = 0; i < actionListThumbnails.length; i++) {
			
			ActionListItem actionListItem = new ActionListItem(actionListThumbnails[i], 
															   actionListHeaderItems[i], 
															   actionListSubHeaderItems[i]);
			
			actionListItems.add(actionListItem);
		}
		
		return new PSNActionListAdapter(HomeActivity.this, actionListItems);
	}
	
	private void createTrophyLadderDialog() {
		
		LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View trophyLadderView = inflater.inflate(R.layout.trophy_ladder, null);
		
		fillTrophyCount(psnProfileData, trophyLadderView);
		
		AlertDialog.Builder builder;
		AlertDialog alertDialog;

		builder = new AlertDialog.Builder(HomeActivity.this);
		builder.setView(trophyLadderView);
		builder.setPositiveButton("Return", new DialogInterface.OnClickListener() {
			
			public void onClick(DialogInterface dialog, int which) {
				
				dialog.dismiss();
			}
		});
		
		alertDialog = builder.create();
		
		alertDialog.setIcon(R.drawable.psn_logo_launcher);
		alertDialog.setTitle(getResources().getString(R.string.trophyLadder));
		
		alertDialog.show();
	}

	private void fillTrophyCount(PsnProfileData psnProfileData, View trophyLadderView) {
		
		TextView platinumCount = (TextView) trophyLadderView.findViewById(R.id.platinumTrophyCount);
		platinumCount.setText(Integer.toString(psnProfileData.getPlatinum()));

		TextView goldCount = (TextView) trophyLadderView.findViewById(R.id.goldTrophyCount);
		goldCount.setText(Integer.toString(psnProfileData.getGold()));

		TextView silverCount = (TextView) trophyLadderView.findViewById(R.id.silverTrophyCount);
		silverCount.setText(Integer.toString(psnProfileData.getSilver()));

		TextView bronzeCount = (TextView) trophyLadderView.findViewById(R.id.bronzeTrophyCount);
		bronzeCount.setText(Integer.toString(psnProfileData.getBronze()));

		TextView totalCount = (TextView) trophyLadderView.findViewById(R.id.totalTrophies);
		totalCount.setText(Integer.toString(psnProfileData.getTrophyCount()));
	}
}