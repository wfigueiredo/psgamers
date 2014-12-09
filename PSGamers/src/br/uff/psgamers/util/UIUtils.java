package br.uff.psgamers.util;

import br.uff.psgamers.R;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;

public class UIUtils {

	/**
     * Creates a new ProgressDialog with custom settings.
     */
	public static ProgressDialog createProgressDialog(Context context, String message){
    	
		ProgressDialog progressDialog = null; 
		
		progressDialog = new ProgressDialog(context);
		progressDialog.setMessage(message);
		progressDialog.setCancelable(false);
    	progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
    	
    	return progressDialog;
    }
	
	/*
	 * Creates an Alert Dialog, used to display general errors.
	 */
	public static void createErrorDialog(Context context, String message) {
		
		final AlertDialog.Builder adb = new AlertDialog.Builder(context);
        adb.setTitle(context.getResources().getString(R.string.error));
        adb.setMessage(message);
        adb.setIcon(android.R.drawable.ic_dialog_alert);
        adb.setPositiveButton(context.getResources().getString(R.string.returnMsg), new OnClickListener() {
			
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
        
        AlertDialog alertDialog = adb.create();
        alertDialog.show();
	}
}