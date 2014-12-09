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
import android.widget.ListView;
import br.uff.psgamers.R;
import br.uff.psgamers.adapter.GameListAdapter;
import br.uff.psgamers.application.PSNApplication;
import br.uff.psgamers.constant.Constants;
import br.uff.psgamers.util.UIUtils;

import com.krobothsoftware.psn.PlayStationNetworkException;
import com.krobothsoftware.psn.PlayStationNetworkLoginException;
import com.krobothsoftware.psn.client.PlayStationNetworkClient;
import com.krobothsoftware.psn.model.PsnGameData;
import com.krobothsoftware.psn.model.PsnTrophyData;

public class GameListActivity extends Activity {

	private ProgressDialog progressDialog;
	private ListView gameListView;

	@Override
	@SuppressWarnings("unchecked")
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.game_list);
		
		progressDialog = UIUtils.createProgressDialog(GameListActivity.this, getResources().getString(R.string.retrieving_game_info));
		progressDialog.show();
		
		final PSNApplication psnApplication = (PSNApplication) getApplication();
		List<PsnGameData> gameList = null;
		
		try {
			gameList = psnApplication.getPsnClient().getClientGameList();
		} catch (PlayStationNetworkException e) {
			Log.e(Constants.LOG_TAG_PSGAMERS_PSN_EXCEPTION, e.getMessage());
		} catch (IOException e) {
			Log.e(Constants.LOG_TAG_PSGAMERS_PSN_IOEXCEPTION, e.getMessage());
		} catch (PlayStationNetworkLoginException e) {
			Log.e(Constants.LOG_TAG_PSGAMERS_PSN_LOGIN_EXCEPTION, e.getMessage());
		}
		
		gameListView = (ListView) findViewById(R.id.gameList);
		new GameListTask().execute(gameList);  // Filling game list adapter
		gameListView.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View arg1, int position, long arg3) {
				
				PsnGameData psnGameData = (PsnGameData) parent.getItemAtPosition(position);
				PlayStationNetworkClient psnClient = psnApplication.getPsnClient();
				List<PsnTrophyData> trophyList = null;
				
				try {
					trophyList = psnClient.getClientTrophyList(psnGameData.getTitleLinkId());
				} catch (IOException e) {
					Log.e(Constants.LOG_TAG_PSGAMERS_PSN_IOEXCEPTION, e.getMessage());
				} catch (PlayStationNetworkException e) {
					Log.e(Constants.LOG_TAG_PSGAMERS_PSN_EXCEPTION, e.getMessage());
				} catch (PlayStationNetworkLoginException e) {
					Log.e(Constants.LOG_TAG_PSGAMERS_PSN_LOGIN_EXCEPTION, e.getMessage());
				}
				
				Intent intent = new Intent(Constants.ACTION_ACTIVITY_TROPHY_LIST);
				intent.putExtra(Constants.EXTRA_GAME_ID, psnGameData.getGameId());
				intent.putExtra(Constants.EXTRA_GAME_TITLE, psnGameData.getName());
				intent.putExtra(Constants.EXTRA_GAME_IMAGE, psnGameData.getGameImage());
				intent.putExtra(Constants.EXTRA_TITLE_LINK_ID, psnGameData.getTitleLinkId());
				
				psnApplication.setPsnTrophyList(trophyList);
				
				startActivity(intent);
			}
		});
	}
	
	private class GameListTask extends AsyncTask<List<PsnGameData>, Void, GameListAdapter> {

		@Override
		protected GameListAdapter doInBackground(List<PsnGameData>... psnGameList) {
			return new GameListAdapter(GameListActivity.this, psnGameList[0]);
		}
		
		@Override
		protected void onPostExecute(GameListAdapter gameListAdapter) {
			gameListView.setAdapter(gameListAdapter);
			progressDialog.dismiss();
		}
	}
}