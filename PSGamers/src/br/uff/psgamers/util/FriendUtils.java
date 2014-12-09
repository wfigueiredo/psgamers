package br.uff.psgamers.util;

import java.util.ArrayList;
import java.util.List;

import com.krobothsoftware.psn.model.FriendStatus;
import com.krobothsoftware.psn.model.PsnFriendData;

public class FriendUtils {

	public static List<PsnFriendData> getFriendsByStatus(List<PsnFriendData> psnFriendList, List<FriendStatus> friendStatuses) {
		
		List<PsnFriendData> officialFriends = new ArrayList<PsnFriendData>();
		
		for (PsnFriendData psnFriendData : psnFriendList) {
			
			if (friendStatuses.contains(psnFriendData.getCurrentStatus())) {
				
				officialFriends.add(psnFriendData);
			}
		}
		
		return officialFriends;
	}
}