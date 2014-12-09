package br.uff.psgamers.activity;

import java.util.Map;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import br.uff.psgamers.R;
import br.uff.psgamers.constant.Constants;
import br.uff.psgamers.task.DownloadImageTask;
import br.uff.psgamers.util.TrophyUtils;

import com.krobothsoftware.psn.model.PsnTrophyData;

public class TrophyDetailsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.trophy_details);
		
		fillTrophyDetails();
	}

	private void fillTrophyDetails() {
		
		Bundle extras = getIntent().getExtras();
		
		// Game Image
		ImageView gameImage = (ImageView) findViewById(R.id.gameImage);
		String gameImageUrl = extras.getString(Constants.EXTRA_GAME_IMAGE);
		DownloadImageTask downloadImageTask = new DownloadImageTask(gameImage);
		downloadImageTask.execute(gameImageUrl);
		
		// Game Title
		String gameTitleDisplay = extras.getString(Constants.EXTRA_GAME_TITLE);
		TextView gameTitle = (TextView) findViewById(R.id.gameTitle);
		gameTitle.setText(gameTitleDisplay);
		
		PsnTrophyData psnTrophyData = (PsnTrophyData) extras.getSerializable(Constants.EXTRA_TROPHY_DATA);
		
		// Trophy Image
		ImageView trophyImageDetails = (ImageView) findViewById(R.id.trophyImageDetails);
		
		// Trophy Title
		TextView trophyTitleDetails = (TextView) findViewById(R.id.trophyTitleDetails);
		
		// Trophy Grade
		Map<String, Object> trophyDetailsResources = TrophyUtils.getTrophyGrade(psnTrophyData.getTrophyType());
		ImageView trophyGrade = (ImageView) findViewById(R.id.trophyTypeDetails);
		TextView trophyGradeDetails = (TextView) findViewById(R.id.trophyGradeDetails);
		
		// Trophy Timestamp
		TextView trophyEarnedTimestamp = (TextView) findViewById(R.id.trophyEarnedTimestamp);

		// Trophy Description
		TextView trophyDetails = (TextView) findViewById(R.id.trophyDetails);
		
		if (psnTrophyData.isHidden()) {
			
			// Trophy Image
			trophyImageDetails.setImageResource(R.drawable.psn_locked_trophy_icon_large);

			// Trophy Title
			trophyTitleDetails.setText(Constants.TROPHY_TYPE_HIDDEN);

			// Trophy Grade
			trophyGrade.setImageResource((Integer) trophyDetailsResources.get(Constants.TROPHY_TYPE_IMAGE));
			trophyGradeDetails.setText((String) trophyDetailsResources.get(Constants.TROPHY_TYPE_GRADE));

			// Trophy Timestamp
			trophyEarnedTimestamp.setText("-");
			
			// Trophy Description
			trophyDetails.setText("-");
		}
		else {
			
			// Trophy Image
			if (psnTrophyData.isReceived()) {
				
				downloadImageTask = new DownloadImageTask(trophyImageDetails);
				downloadImageTask.execute(psnTrophyData.getTrophyImageUrl());

				// Trophy Timestamp
				trophyEarnedTimestamp.setText(psnTrophyData.getDateEarned());
			}
			else {
				// Locked trophy
				trophyImageDetails.setImageResource(R.drawable.psn_locked_trophy_icon_large);
				
				// Trophy Timestamp
				trophyEarnedTimestamp.setText("-");
			}
			
			// Trophy Title
			trophyTitleDetails.setText(psnTrophyData.getTrophyName());

			// Trophy Grade
			trophyGrade.setImageResource((Integer) trophyDetailsResources.get(Constants.TROPHY_TYPE_IMAGE));
			trophyGradeDetails.setText((String) trophyDetailsResources.get(Constants.TROPHY_TYPE_GRADE));

			// Trophy Description
			trophyDetails.setText(psnTrophyData.getDescription());
		}
	}
}