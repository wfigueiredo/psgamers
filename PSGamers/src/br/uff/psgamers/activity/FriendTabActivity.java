package br.uff.psgamers.activity;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import br.uff.psgamers.R;
import br.uff.psgamers.constant.Constants;

import com.krobothsoftware.psn.model.PsnFriendData;

public class FriendTabActivity extends TabActivity {

	private final static String TAB_SPEC_FRIEND_PROFILE = "Friend Profile";
	private final static String TAB_SPEC_FRIEND_GAME_LIST = "Friend Games";
	private final static String TAB_SPEC_FRIEND_GAME_COMPARE = "Friend Game Compare";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.friend_details);
		
		Bundle extras = getIntent().getExtras();
		Resources resources = getResources();
		TabHost tabHost = getTabHost();
		
		PsnFriendData psnFriendData = (PsnFriendData) extras.getSerializable(Constants.EXTRA_CLIENT_PROFILE);
		
		// --------------------------- Friend Profile Tab --------------------------------
		TabSpec friendProfileSpec = tabHost.newTabSpec(TAB_SPEC_FRIEND_PROFILE);
		friendProfileSpec.setIndicator(resources.getString(R.string.profile), resources.getDrawable(R.drawable.psn_user_icon));
		Intent intentFriendProfile = new Intent(Constants.ACTION_ACTIVITY_FRIEND_PROFILE);
		intentFriendProfile.putExtra(Constants.EXTRA_CLIENT_PROFILE, psnFriendData);
		friendProfileSpec.setContent(intentFriendProfile);
		
		// --------------------------- Friend Games Tab --------------------------------
		TabSpec friendGameListSpec = tabHost.newTabSpec(TAB_SPEC_FRIEND_GAME_LIST);
		friendGameListSpec.setIndicator(resources.getString(R.string.games), resources.getDrawable(R.drawable.ps3_controller));
		Intent intentFriendGameList = new Intent(Constants.ACTION_ACTIVITY_FRIEND_GAME_LIST);
		intentFriendGameList.putExtra(Constants.EXTRA_PSN_ID, psnFriendData.getPsnId());
		
		friendGameListSpec.setContent(intentFriendGameList);

		// --------------------------- Friend Games Compare Tab --------------------------------
		TabSpec friendGameCompareSpec = tabHost.newTabSpec(TAB_SPEC_FRIEND_GAME_COMPARE);
		friendGameCompareSpec.setIndicator(resources.getString(R.string.compare), resources.getDrawable(R.drawable.game_compare_icon));
		Intent intentGameCompare = new Intent(Constants.ACTION_ACTIVITY_FRIEND_GAME_COMPARE);
		intentFriendGameList.putExtra(Constants.EXTRA_PSN_ID, psnFriendData.getPsnId());
		
		friendGameCompareSpec.setContent(intentGameCompare);
		
		// Adding all TabSpec to TabHost
        tabHost.addTab(friendProfileSpec); 			// Adding friend profile tab
        tabHost.addTab(friendGameListSpec); 		// Adding friend game list tab
//        tabHost.addTab(friendGameCompareSpec); 		// Adding friend game compare tab
        
        // Friend profile is the default tab.
        tabHost.setCurrentTab(0);					
	}
}