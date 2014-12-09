package br.uff.psgamers.activity;

import java.io.IOException;
import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import br.uff.psgamers.R;
import br.uff.psgamers.adapter.TrophyListAdapter;
import br.uff.psgamers.application.PSNApplication;
import br.uff.psgamers.constant.Constants;
import br.uff.psgamers.task.DownloadImageTask;
import br.uff.psgamers.util.UIUtils;

import com.krobothsoftware.psn.PlayStationNetworkException;
import com.krobothsoftware.psn.PlayStationNetworkLoginException;
import com.krobothsoftware.psn.model.PsnTrophyData;

public class TrophyListActivity extends Activity {

	private ListView trophyListView;
	private ProgressDialog progressDialog;
	
	@Override
	@SuppressWarnings("unchecked")
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.trophy_list);

		progressDialog = UIUtils.createProgressDialog(TrophyListActivity.this, getResources().getString(R.string.retrieving_trophy_info));
		progressDialog.show();
		
		Bundle extras = getIntent().getExtras();
		
		final String gameTitleDisplay = extras.getString(Constants.EXTRA_GAME_TITLE);
		final String gameImageUrl = extras.getString(Constants.EXTRA_GAME_IMAGE);
		final String titleLinkId = extras.getString(Constants.EXTRA_TITLE_LINK_ID);
		
		List<PsnTrophyData> clientTrophyList = null;
		
		try {
			clientTrophyList = ((PSNApplication) getApplication()).getPsnClient().getClientTrophyList(titleLinkId);
		} catch (PlayStationNetworkException e) {
			Log.e(Constants.LOG_TAG_PSGAMERS_PSN_EXCEPTION, e.getMessage());
		} catch (IOException e) {
			Log.e(Constants.LOG_TAG_PSGAMERS_PSN_IOEXCEPTION, e.getMessage());
		} catch (PlayStationNetworkLoginException e) {
			Log.e(Constants.LOG_TAG_PSGAMERS_PSN_LOGIN_EXCEPTION, e.getMessage());
		}
		
		// Game Image
		ImageView gameImageView = (ImageView) findViewById(R.id.gameImage);
		DownloadImageTask downloadImageTask = new DownloadImageTask(gameImageView);
		downloadImageTask.execute(gameImageUrl);
		
		// Game Title
		TextView gameTitle = (TextView) findViewById(R.id.gameTitle);
		gameTitle.setText(gameTitleDisplay);
		
		trophyListView = (ListView) findViewById(R.id.trophyList);
		new TrophyListTask().execute(clientTrophyList);   // Filling trophy list adapter
		trophyListView.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View arg1, int position, long arg3) {
				
				PsnTrophyData psnTrophyData = (PsnTrophyData) parent.getItemAtPosition(position);
				Intent intent = new Intent(Constants.ACTION_ACTIVITY_TROPHY_DETAILS);
				intent.putExtra(Constants.EXTRA_TROPHY_DATA, psnTrophyData);
				intent.putExtra(Constants.EXTRA_GAME_TITLE, gameTitleDisplay);
				intent.putExtra(Constants.EXTRA_GAME_IMAGE, gameImageUrl);
				
				startActivity(intent);
			}
		});
	}
	
	private class TrophyListTask extends AsyncTask<List<PsnTrophyData>, Void, TrophyListAdapter> {

		@Override
		protected TrophyListAdapter doInBackground(List<PsnTrophyData>... trophyList) {
			return new TrophyListAdapter(TrophyListActivity.this, trophyList[0]);
		}
		
		@Override
		protected void onPostExecute(TrophyListAdapter trophyListAdapter) {
			progressDialog.dismiss();
			trophyListView.setAdapter(trophyListAdapter);
		}
	}	
}