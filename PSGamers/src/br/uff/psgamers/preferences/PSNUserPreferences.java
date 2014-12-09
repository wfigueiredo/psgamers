package br.uff.psgamers.preferences;

import br.uff.psgamers.application.PSNApplication;
import android.content.Context;
import android.content.SharedPreferences;

public class PSNUserPreferences {
	
	// Shared Preferences
	private final static String PREFERENCE_PSN_EMAIL = "PSN_EMAIL";
	private final static String PREFERENCE_PSN_PASSWORD = "PSN_PASSWORD";
	private final static String PREFERENCE_PSN_ACCOUNT_LINKED_TO_DEVICE = "ACCOUNT_LINKED_TO_DEVICE";
	private final static String PREFERENCE_REMEMBER_EMAIL = "REMEMBER_EMAIL";
	private final static String PREFERENCES_FILE = "psn_user_preferences_file";
	
	public static SharedPreferences getSharedPreferences() {
		
		return PSNApplication.getAppContext().getSharedPreferences(PREFERENCES_FILE, Context.MODE_PRIVATE);
	}
	
	public static void commitRememberEmailPreference(boolean rememberEmail, String psnEmail) {
		
		SharedPreferences sharedPreferences = getSharedPreferences();
		SharedPreferences.Editor editor = sharedPreferences.edit();
		
		editor.putString(PREFERENCE_PSN_EMAIL, psnEmail);
		editor.putBoolean(PREFERENCE_REMEMBER_EMAIL, rememberEmail);
		editor.commit();
	}
	
	public static void commitUserAccountPreferences(String psnEmail, String psnPassword) {
		
		SharedPreferences sharedPreferences = getSharedPreferences();
		SharedPreferences.Editor editor = sharedPreferences.edit();
		
		editor.putBoolean(PREFERENCE_PSN_ACCOUNT_LINKED_TO_DEVICE, true);
		editor.putString(PREFERENCE_PSN_EMAIL, psnEmail);
		editor.putString(PREFERENCE_PSN_PASSWORD, psnPassword);
		editor.commit();
	}
	
	public static boolean getLinkedAccountPreference() {
		
		SharedPreferences sharedPreferences = getSharedPreferences();
		
		return sharedPreferences.getBoolean(PREFERENCE_PSN_ACCOUNT_LINKED_TO_DEVICE, false);
	}
	
	public static String getEmailPreference() {
		
		SharedPreferences sharedPreferences = getSharedPreferences();
		
		return sharedPreferences.getString(PREFERENCE_PSN_EMAIL, null);
	}
	
	public static String getPasswordAccountPreference() {
		
		SharedPreferences sharedPreferences = getSharedPreferences();
		
		return sharedPreferences.getString(PREFERENCE_PSN_PASSWORD, null);
	}
	
	public static boolean getRememberEmailPreference() {
		
		SharedPreferences sharedPreferences = getSharedPreferences();
		
		return sharedPreferences.getBoolean(PREFERENCE_REMEMBER_EMAIL, false);
	}
}