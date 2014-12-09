package br.uff.psgamers.task;

import java.io.IOException;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import br.uff.psgamers.R;
import br.uff.psgamers.application.PSNApplication;
import br.uff.psgamers.constant.Constants;
import br.uff.psgamers.preferences.PSNUserPreferences;
import br.uff.psgamers.util.UIUtils;

import com.krobothsoftware.commons.progress.ProgressListener;
import com.krobothsoftware.psn.PlayStationNetworkException;
import com.krobothsoftware.psn.PlayStationNetworkLoginException;
import com.krobothsoftware.psn.client.PlayStationNetworkClient;

public class PSNLoginTask extends AsyncTask<String, Void, PlayStationNetworkClient> {

	private Context context;
	private ProgressDialog progressDialog;
	
	private String psnEmail;
	private String psnPassword;
	
	private PSNLoginError psnLoginError;
	
	private enum PSNLoginError {
		
		PSNUnavailableError, PSNLoginError, PSNUnauthorizedError, PSNUnreachableError, PSNError
	}
	
	public PSNLoginTask(Context context) {
		this.context = context;
	}
	
	@Override
	protected void onPreExecute() {
		progressDialog = UIUtils.createProgressDialog(context, context.getResources().getString(R.string.retrievingPSNUserData));
		progressDialog.show();
	}
	
	@Override
	protected PlayStationNetworkClient doInBackground(String... params) {
		
		psnEmail = params[0];
		psnPassword = params[1];
		
		PlayStationNetworkClient psnClient = new PlayStationNetworkClient();
		
		ProgressListener progressListener = new ProgressListener() {
			
			public void onProgressUpdate(float value, String text) {
			}
			
			public int getProgressLength() {
				return 0;
			}
		};
		
		try {
			psnClient.init();
			psnClient.clientLogin(progressListener, psnEmail, psnPassword);
		} catch (IOException e) {
			setPsnLoginError(PSNLoginError.PSNUnreachableError);
			Log.e(Constants.LOG_TAG_PSGAMERS_PSN_IOEXCEPTION, e.getMessage());
		} catch (PlayStationNetworkException e) {
			setPsnLoginError(PSNLoginError.PSNUnavailableError);
			Log.e(Constants.LOG_TAG_PSGAMERS_PSN_EXCEPTION, e.getMessage());
		} catch (PlayStationNetworkLoginException e) {
			setPsnLoginError(PSNLoginError.PSNLoginError);
			Log.e(Constants.LOG_TAG_PSGAMERS_PSN_LOGIN_EXCEPTION, e.getMessage());
		}
		
		// Remember email preference
		boolean rememberEmail = Boolean.parseBoolean(params[2]);
		PSNUserPreferences.commitRememberEmailPreference(rememberEmail, psnEmail);
		
		return (psnClient.isClientLoggedIn() ? psnClient : null);
	}
	
	@Override
	protected void onPostExecute(PlayStationNetworkClient psnClient) {
		
//		---------------------------------------------------------------------------
//		TODO: Future changes will include this routine.
//
//		if (!PSNUserPreferences.getLinkedAccountPreference()) {
//			PSNUserPreferences.commitUserAccountPreferences(psnEmail, psnPassword);
//		}
//		---------------------------------------------------------------------------
		
		if (psnClient != null) {
			
			// Maintaining PlayStationNetworkClient global-scope.
			PSNApplication.getInstance().setPsnClientUK(psnClient);

			progressDialog.dismiss();
			Intent intent = new Intent(Constants.ACTION_ACTIVITY_HOME);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			context.startActivity(intent);
		}
		else {
			
			switch (getPsnLoginError()) {
			
				case PSNUnavailableError:
					UIUtils.createErrorDialog(context, context.getResources().getString(R.string.psn_unavailable_error));
					break;
					
				case PSNLoginError:
					UIUtils.createErrorDialog(context, context.getResources().getString(R.string.psn_login_error));
					break;
					
				case PSNUnreachableError:
					UIUtils.createErrorDialog(context, context.getResources().getString(R.string.psn_unreachable_error));
					break;
					
				default:
					UIUtils.createErrorDialog(context, context.getResources().getString(R.string.login_not_successful));
					break;
			}
			
			progressDialog.dismiss();
		}
	}
	
	public void attach(Context context) {
		this.context = context;
	}
	
	public void detach() {
		context = null;
	}

	private PSNLoginError getPsnLoginError() {
		return psnLoginError;
	}
	
	private void setPsnLoginError(PSNLoginError psnLoginError) {
		this.psnLoginError = psnLoginError;
	}
}