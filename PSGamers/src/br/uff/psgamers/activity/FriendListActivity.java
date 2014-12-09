package br.uff.psgamers.activity;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import br.uff.psgamers.R;
import br.uff.psgamers.adapter.FriendListAdapter;
import br.uff.psgamers.application.PSNApplication;
import br.uff.psgamers.constant.Constants;
import br.uff.psgamers.util.FriendUtils;
import br.uff.psgamers.util.UIUtils;

import com.krobothsoftware.psn.PlayStationNetworkException;
import com.krobothsoftware.psn.PlayStationNetworkLoginException;
import com.krobothsoftware.psn.client.PlayStationNetworkClient;
import com.krobothsoftware.psn.model.FriendStatus;
import com.krobothsoftware.psn.model.PsnFriendData;

public class FriendListActivity extends Activity implements OnItemClickListener {

	private Resources resources;
	
//	private int friendCount;
	private FriendStatus[] friendStatuses;
	private List<PsnFriendData> psnFriendList;
	private List<FriendStatus> statusesList;
	private List<PsnFriendData> psnFriendsByStatus;
	
	// UI Components
	private ListView friendStatusListView;
	private ProgressDialog progressDialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.friend_list);
		
		resources = getResources();
		
		friendStatuses = (FriendStatus[]) getIntent().getExtras().getSerializable(Constants.EXTRA_CLIENT_FRIEND_LIST_STATUS);
		statusesList = Arrays.asList(friendStatuses);

		// Execute AsyncTask
		FriendListTask friendListTask = new FriendListTask();
		friendListTask.execute();
		
		TextView friendListStatusDisplay = (TextView) findViewById(R.id.statusFriendsTitlebar);

		if (!statusesList.contains(FriendStatus.OFFLINE)) {
			friendListStatusDisplay.setText(resources.getString(R.string.online)); 
			//" (" + Integer.toString(friendCount) + ")");
		}
		else {
			friendListStatusDisplay.setText(resources.getString(R.string.offline)); 
			//" (" + Integer.toString(friendCount) + ")");
		}
		
		friendStatusListView = (ListView) findViewById(R.id.friendStatusList);
		friendStatusListView.setOnItemClickListener(this);
	}
	
	private class FriendListTask extends AsyncTask<Void, Void, FriendListAdapter> {

		@Override
		protected void onPreExecute() {
			progressDialog = UIUtils.createProgressDialog(FriendListActivity.this, resources.getString(R.string.retrieving_friend_info));
			progressDialog.show();
		}	
		
		@Override
		protected FriendListAdapter doInBackground(Void... params) {
			
			PlayStationNetworkClient psnClient = null;
			
			try {
				psnClient = ((PSNApplication) getApplication()).getPsnClient();
				psnFriendList = psnClient.getClientFriendList();
			} catch (IOException e) {
				Log.e(Constants.LOG_TAG_PSGAMERS_PSN_IOEXCEPTION, e.getMessage());
			} 
			catch (PlayStationNetworkException e) {
				Log.e(Constants.LOG_TAG_PSGAMERS_PSN_EXCEPTION, e.getMessage());
			} 
			catch (PlayStationNetworkLoginException e) {
				Log.e(Constants.LOG_TAG_PSGAMERS_PSN_LOGIN_EXCEPTION, e.getMessage());
			}
			
			psnFriendsByStatus = FriendUtils.getFriendsByStatus(psnFriendList, statusesList);
			
			return new FriendListAdapter(FriendListActivity.this, psnFriendsByStatus);
		}
		
		@Override
		protected void onPostExecute(FriendListAdapter friendListAdapter) {
			friendStatusListView.setAdapter(friendListAdapter);
			progressDialog.dismiss();
		}
	}

	public void onItemClick(AdapterView<?> parent, View arg1, int position, long arg3) {
				
		PsnFriendData psnFriend = (PsnFriendData) parent.getItemAtPosition(position);
		
		Intent intent = new Intent(Constants.ACTION_ACTIVITY_FRIEND_TAB);
		intent.putExtra(Constants.EXTRA_CLIENT_PROFILE, psnFriend);
		
		startActivity(intent);
	}
}