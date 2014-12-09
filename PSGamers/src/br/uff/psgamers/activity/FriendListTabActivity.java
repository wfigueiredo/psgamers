package br.uff.psgamers.activity;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import br.uff.psgamers.R;
import br.uff.psgamers.constant.Constants;

import com.krobothsoftware.psn.model.FriendStatus;

public class FriendListTabActivity extends TabActivity {

	private final static String TAB_SPEC_ONLINE_FRIENDS = "Online";
	private final static String TAB_SPEC_OFFLINE_FRIENDS = "Offline";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.friend_list_tab);
		
		Resources resources = getResources();
		TabHost tabHost = getTabHost();
		
		// --------------------------- Online Friends Tab --------------------------------
		TabSpec onlineFriendsSpec = tabHost.newTabSpec(TAB_SPEC_ONLINE_FRIENDS);
		onlineFriendsSpec.setIndicator(resources.getString(R.string.online), resources.getDrawable(R.drawable.psn_status_online));
		Intent intentOnlineFriends = new Intent(Constants.ACTION_ACTIVITY_FRIEND_LIST);
		intentOnlineFriends.putExtra(Constants.EXTRA_CLIENT_FRIEND_LIST_STATUS, new FriendStatus[]{FriendStatus.ONLINE, FriendStatus.AWAY});
		onlineFriendsSpec.setContent(intentOnlineFriends);
		
		// --------------------------- Offline Friends Tab --------------------------------
		TabSpec offlineFriendsSpec = tabHost.newTabSpec(TAB_SPEC_OFFLINE_FRIENDS);
		offlineFriendsSpec.setIndicator(resources.getString(R.string.offline), resources.getDrawable(R.drawable.psn_status_offline));
		Intent intentOfflineFriends = new Intent(Constants.ACTION_ACTIVITY_FRIEND_LIST);
		intentOfflineFriends.putExtra(Constants.EXTRA_CLIENT_FRIEND_LIST_STATUS, new FriendStatus[]{FriendStatus.OFFLINE});
		offlineFriendsSpec.setContent(intentOfflineFriends);
		
		// Adding all TabSpec to TabHost
        tabHost.addTab(onlineFriendsSpec); 			// Adding online friends tab
        tabHost.addTab(offlineFriendsSpec); 		// Adding offline friends tab
        
        // Online Friends is the default tab.
        tabHost.setCurrentTab(0);
	}
}