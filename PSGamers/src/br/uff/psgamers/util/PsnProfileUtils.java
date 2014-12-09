package br.uff.psgamers.util;

import java.util.HashMap;
import java.util.Map;

import br.uff.psgamers.R;
import br.uff.psgamers.constant.Constants;

import com.krobothsoftware.psn.model.FriendStatus;

public class PsnProfileUtils {

	public static Map<String, Object> getFriendStatusDetails(FriendStatus friendStatus) {
		
		Map<String, Object> profileDetails = new HashMap<String, Object>(2);
		
		switch (friendStatus) {

			case ONLINE:
				profileDetails.put(Constants.STATUS_TYPE, Constants.STATUS_ONLINE);
				profileDetails.put(Constants.STATUS_IMAGE, R.drawable.psn_status_online);
				break;
				
			case OFFLINE:
				profileDetails.put(Constants.STATUS_TYPE, Constants.STATUS_OFFLINE);
				profileDetails.put(Constants.STATUS_IMAGE, R.drawable.psn_status_offline);
				break;

			case AWAY:
				profileDetails.put(Constants.STATUS_TYPE, Constants.STATUS_AWAY);
				profileDetails.put(Constants.STATUS_IMAGE, R.drawable.psn_status_away);
				break;
			
			case PENDING_RESPONSE:
				profileDetails.put(Constants.STATUS_TYPE, Constants.STATUS_PENDING_RESPONSE);
				profileDetails.put(Constants.STATUS_IMAGE, R.drawable.psn_status_pending);
				break;
	
			default:
				break;
			}
		
		return profileDetails;
	}	
}