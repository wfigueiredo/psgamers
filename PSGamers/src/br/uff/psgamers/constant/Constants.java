package br.uff.psgamers.constant;

import br.uff.psgamers.R;


public class Constants {
	
	// Activity Filters
	public final static String ACTION_ACTIVITY_LOGIN = "ACTIVITY_LOGIN";
	public final static String ACTION_ACTIVITY_HOME = "ACTIVITY_HOME";
	public final static String ACTION_ACTIVITY_FRIEND_LIST = "ACTIVITY_FRIEND_LIST";
	public final static String ACTION_ACTIVITY_GAME_LIST = "ACTIVITY_GAME_LIST";
	public final static String ACTION_ACTIVITY_TROPHY_LIST = "ACTIVITY_TROPHY_LIST";
	public static final String ACTION_ACTIVITY_TROPHY_DETAILS = "ACTIVITY_TROPHY_DETAILS";
	public static final String ACTION_ACTIVITY_FRIEND_LIST_TAB = "ACTIVITY_FRIEND_LIST_TAB";
	public static final String ACTION_ACTIVITY_FRIEND_TAB = "ACTIVITY_FRIEND_TAB";
	public static final String ACTION_ACTIVITY_FRIEND_PROFILE = "ACTIVITY_FRIEND_PROFILE";
	public static final String ACTION_ACTIVITY_FRIEND_GAME_LIST = "ACTIVITY_FRIEND_GAME_LIST";
	public static final String ACTION_ACTIVITY_FRIEND_GAME_COMPARE = "ACTIVITY_FRIEND_GAME_COMPARE";
	
	// Extras
	public final static String EXTRA_CLIENT_PROFILE = "CLIENT_PROFILE";
	public final static String EXTRA_PSN_ID = "PSN_ID";
	public final static String EXTRA_CLIENT_GAME_LIST = "CLIENT_GAME_LIST"; 
	public static final String EXTRA_CLIENT_FRIEND_LIST = "CLIENT_FRIEND_LIST"; 
	public static final String EXTRA_GAME_ID = "GAME_ID"; 
	public static final String EXTRA_GAME_TITLE = "GAME_TITLE"; 
	public static final String EXTRA_GAME_IMAGE = "GAME_IMAGE"; 
	public static final String EXTRA_TITLE_LINK_ID = "TITLE_LINK_ID"; 
	public static final String EXTRA_TROPHY_DATA = "TROPHY_DATA"; 
	public static final String EXTRA_TROPHY_LIST = "TROPHY_LIST";
	public static final String EXTRA_CLIENT_FRIEND_LIST_STATUS = "FRIEND_LIST_STATUS";
	
	// Log Tags
	public final static String LOG_TAG_PSGAMERS_INFO = "LOG_TAG_PSGAMERS_INFO";
	public final static String LOG_TAG_PSGAMERS_ERROR = "LOG_TAG_PSGAMERS_ERROR";
	public final static String LOG_TAG_PSGAMERS_PSN_UNAUTHORIZED = "LOG_TAG_PSGAMERS_PSN_UNAUTHORIZED";
	public final static String LOG_TAG_PSGAMERS_PSN_UNAVAILABLE = "LOG_TAG_PSGAMERS_PSN_UNAVAILABLE";
	public final static String LOG_TAG_PSGAMERS_PSN_LOGIN_EXCEPTION = "LOG_TAG_PSGAMERS_PSN_LOGIN_EXCEPTION";
	public final static String LOG_TAG_PSGAMERS_PSN_EXCEPTION = "LOG_TAG_PSGAMERS_PSN_EXCEPTION";
	public final static String LOG_TAG_PSGAMERS_PSN_IOEXCEPTION = "LOG_TAG_PSGAMERS_PSN_IOEXCEPTION";
	public final static String LOG_TAG_PSGAMERS_PSN_UNREACHABLE = "LOG_TAG_PSGAMERS_PSN_UNREACHABLE";
	
	// Trophy Types
	public final static String TROPHY_TYPE_GRADE = "Trophy Type Grade";
	public final static String TROPHY_TYPE_IMAGE = "Trophy Type Image";
	public final static String TROPHY_TYPE_PLATINUM = "Platinum";
	public final static String TROPHY_TYPE_GOLD = "Gold";
	public final static String TROPHY_TYPE_SILVER = "Silver";
	public final static String TROPHY_TYPE_BRONZE = "Bronze";
	public final static String TROPHY_TYPE_HIDDEN = "Hidden Trophy";
	
	// Status Types
	public final static String STATUS_TYPE = "Current status";
	public final static String STATUS_IMAGE = "Status Image";
	public final static String STATUS_ONLINE = "Online";
	public final static String STATUS_OFFLINE = "Offline";
	public final static String STATUS_AWAY = "Away";
	public final static String STATUS_PENDING_RESPONSE = "Pending response";
	
	// Resources
	public final static int[] ACTION_LIST_THUMBNAILS = {R.drawable.psn_friends_icon, R.drawable.ps3_controller_icon};
}