package br.uff.psgamers.activity;

import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import br.uff.psgamers.R;
import br.uff.psgamers.constant.Constants;
import br.uff.psgamers.task.DownloadImageTask;
import br.uff.psgamers.util.PsnProfileUtils;

import com.krobothsoftware.psn.model.PsnFriendData;

public class FriendProfileActivity extends Activity {

	private int trophyCount;
	private PsnFriendData psnFriendData;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.friend_profile);
		
		psnFriendData = (PsnFriendData) getIntent().getExtras().getSerializable(Constants.EXTRA_CLIENT_PROFILE);
		
		// ------------------------------ PSN Profile data ----------------------------------------- //
		fillFriendProfileData(psnFriendData);
	
		// ------------------------------- Trophy Ladder ------------------------------------------ //
		Button trophyLadderBtn = (Button) findViewById(R.id.friendTrophyLadder);
		trophyLadderBtn.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				createTrophyLadderDialog(psnFriendData);
			}
		});
	}
	
	private void fillFriendProfileData(PsnFriendData psnFriendData) {
		
		// Friend Avatar
		ImageView avatar = (ImageView) findViewById(R.id.psnFriendAvatar);
		DownloadImageTask downloadImageTask = new DownloadImageTask(avatar);
		downloadImageTask.execute(psnFriendData.getAvatar());
		
		// PSN Plus
		ImageView psnPlus = (ImageView) findViewById(R.id.psnFriendPlus);
		if (!psnFriendData.isPlayStationPlus()) {
			psnPlus.setVisibility(View.INVISIBLE);
		}
		
		// Comment
		TextView friendComment = (TextView) findViewById(R.id.psnFriendAboutMe);
		String comment = psnFriendData.getComment();
		
		if (comment != null) {
			friendComment.setText(comment);
		}
		else {
			friendComment.setVisibility(View.INVISIBLE);
		}
		
		// PSN ID
		TextView psnId = (TextView) findViewById(R.id.psnId);
		psnId.setText(psnFriendData.getPsnId());
		
		// PSN Level
		TextView psnLevel = (TextView) findViewById(R.id.psnLevel);
		psnLevel.setText(Integer.toString(psnFriendData.getLevel()));
		
		// Trophy Count
		int platinum = psnFriendData.getPlatinum();
		int gold = psnFriendData.getGold();
		int silver = psnFriendData.getSilver();
		int bronze = psnFriendData.getBronze();
		trophyCount = platinum + gold + silver + bronze;
		
		TextView psnTrophyCount = (TextView) findViewById(R.id.psnFriendTrophyCount);
		psnTrophyCount.setText(Integer.toString(trophyCount));
		
		// PSN Progress bar
//		int trophyPoints = TrophyCalculator.getTrophyPoints(bronze, silver, gold, platinum);
//		double progress = TrophyCalculator.getProgress(trophyPoints);
//		int progressValue = ((Long) Math.round(progress)).intValue();

//		ProgressBar psnProgressBar = (ProgressBar) findViewById(R.id.psnFriendProgressBar);
//		psnProgressBar.setProgress(progressValue);
		
		// PSN Progress percent
//		TextView progressPercent = (TextView) findViewById(R.id.psnProgressPercent);
//		progressPercent.setText(Integer.toString(progressValue));
		
		// Current status
		Map<String, Object> friendStatusDetails = PsnProfileUtils.getFriendStatusDetails(psnFriendData.getCurrentStatus());
		
		ImageView psnStatusIcon = (ImageView) findViewById(R.id.psnFriendStatusIcon);
		psnStatusIcon.setImageResource((Integer) friendStatusDetails.get(Constants.STATUS_IMAGE));

		TextView psnStatus = (TextView) findViewById(R.id.psnFriendCurrentPresence);
		String status = (String) friendStatusDetails.get(Constants.STATUS_TYPE);
		psnStatus.setText(status);
		
		ImageView controllerIcon = (ImageView) findViewById(R.id.psnFriendGamePlayingIcon);
		if (status.equals(Constants.STATUS_OFFLINE) || status.equals(Constants.STATUS_PENDING_RESPONSE)) {
			controllerIcon.setVisibility(View.INVISIBLE);
		}
		
		// Current game, if any.
		TextView currentGame = (TextView) findViewById(R.id.psnFriendCurrentGamePlayed);
		currentGame.setText(psnFriendData.getCurrentGame());
	}

	private void createTrophyLadderDialog(PsnFriendData psnFriendData) {
		
		LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View trophyLadderView = inflater.inflate(R.layout.trophy_ladder, null);
		
		int platinum = psnFriendData.getPlatinum();
		int gold = psnFriendData.getGold();
		int silver = psnFriendData.getSilver();
		int bronze = psnFriendData.getBronze();
		
		fillTrophyCount(platinum, gold, silver, bronze, trophyCount, trophyLadderView);
		
		AlertDialog.Builder builder;
		AlertDialog alertDialog;

		builder = new AlertDialog.Builder(FriendProfileActivity.this);
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

	private void fillTrophyCount(int platinum, int gold, int silver, int bronze, int total, View trophyLadderView) {
		
		TextView platinumCount = (TextView) trophyLadderView.findViewById(R.id.platinumTrophyCount);
		platinumCount.setText(Integer.toString(platinum));

		TextView goldCount = (TextView) trophyLadderView.findViewById(R.id.goldTrophyCount);
		goldCount.setText(Integer.toString(gold));

		TextView silverCount = (TextView) trophyLadderView.findViewById(R.id.silverTrophyCount);
		silverCount.setText(Integer.toString(silver));

		TextView bronzeCount = (TextView) trophyLadderView.findViewById(R.id.bronzeTrophyCount);
		bronzeCount.setText(Integer.toString(bronze));

		TextView totalCount = (TextView) trophyLadderView.findViewById(R.id.totalTrophies);
		totalCount.setText(Integer.toString(total));
	}
}