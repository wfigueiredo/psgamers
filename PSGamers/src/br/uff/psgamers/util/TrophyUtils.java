package br.uff.psgamers.util;

import java.util.HashMap;
import java.util.Map;

import br.uff.psgamers.R;
import br.uff.psgamers.constant.Constants;

import com.krobothsoftware.psn.TrophyType;

public class TrophyUtils {

	public static Map<String, Object> getTrophyGrade(TrophyType trophyType) {
		
		Map<String, Object> trophyDetails = new HashMap<String, Object>(2);
		
		switch (trophyType) {
		
			case PLATINUM:
				trophyDetails.put(Constants.TROPHY_TYPE_GRADE, Constants.TROPHY_TYPE_PLATINUM);
				trophyDetails.put(Constants.TROPHY_TYPE_IMAGE, R.drawable.psn_platinum);
				break;
			
			case GOLD:
				trophyDetails.put(Constants.TROPHY_TYPE_GRADE, Constants.TROPHY_TYPE_GOLD);
				trophyDetails.put(Constants.TROPHY_TYPE_IMAGE, R.drawable.psn_gold);
				break;
			
			case SILVER:
				trophyDetails.put(Constants.TROPHY_TYPE_GRADE, Constants.TROPHY_TYPE_SILVER);
				trophyDetails.put(Constants.TROPHY_TYPE_IMAGE, R.drawable.psn_silver);
				break;
			
			case BRONZE:
				trophyDetails.put(Constants.TROPHY_TYPE_GRADE, Constants.TROPHY_TYPE_BRONZE);
				trophyDetails.put(Constants.TROPHY_TYPE_IMAGE, R.drawable.psn_bronze);
				break;
	
			default:
				trophyDetails.put(Constants.TROPHY_TYPE_GRADE, Constants.TROPHY_TYPE_HIDDEN);
				trophyDetails.put(Constants.TROPHY_TYPE_IMAGE, R.drawable.psn_hidden);
				break;
		}
		
		return trophyDetails;
	}
}