package br.uff.psgamers.activity;

import android.app.Activity;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import br.uff.psgamers.R;
import br.uff.psgamers.connectivity.ConnectivityHelper;
import br.uff.psgamers.preferences.PSNUserPreferences;
import br.uff.psgamers.task.PSNLoginTask;
import br.uff.psgamers.util.UIUtils;

public class LoginActivity extends Activity {
	
	private PSNLoginTask psnLoginTask;
	private CheckBox rememberEmail;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        setContentView(R.layout.login);
        
        final Resources resources = getResources();
        final EditText emailInput = (EditText) findViewById(R.id.emailInput);
        final EditText passwordInput = (EditText) findViewById(R.id.passwordInput);
        rememberEmail = (CheckBox) findViewById(R.id.rememberEmail);
        
        // Remember Email section
        TextView useAnotherAccount = (TextView) findViewById(R.id.useAnotherAccount);
        SpannableString spanString = new SpannableString(useAnotherAccount.getText());
        spanString.setSpan(new UnderlineSpan(), 0, spanString.length(), 0);
        useAnotherAccount.setText(spanString);
        useAnotherAccount.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				
				// Make 'Remember Email' checkbox visible again;
				v.setVisibility(View.INVISIBLE);
				rememberEmail.setVisibility(View.VISIBLE);

				// Clean previous typed Email and Password;
				emailInput.setText(null);
				passwordInput.setText(null);
				emailInput.setEnabled(true);
			}
		});

        final boolean rememberEmailPreference = PSNUserPreferences.getRememberEmailPreference();
		
        if (rememberEmailPreference) {
        	
        	emailInput.setText(PSNUserPreferences.getEmailPreference());
        	emailInput.setEnabled(false);
        	rememberEmail.setVisibility(View.INVISIBLE);
        }
        else {
        	
        	useAnotherAccount.setVisibility(View.INVISIBLE);
        }
    	
    	// Login section
        Button submitLogin = (Button) findViewById(R.id.submitLogin);
    	submitLogin.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				
				ConnectivityHelper connectivityHelper = new ConnectivityHelper(LoginActivity.this);
    			
    			if (connectivityHelper.isDeviceOnline()) {
    				
    				String psnMail = emailInput.getText().toString();
    				String psnPassword = passwordInput.getText().toString();
    				
    				if (!psnMail.trim().isEmpty() || !psnPassword.trim().isEmpty()) {
    					
    					String[] taskParams = null;
    					
    					if (rememberEmailPreference || rememberEmail.isChecked()) {
    						
    						taskParams = new String[]{psnMail, psnPassword, Boolean.toString(Boolean.TRUE)};
    					}
    					else {
    						
    						taskParams = new String[]{psnMail, psnPassword, Boolean.toString(Boolean.FALSE)};
    					}
    					
    					// 			[Screen configuration issues]
    					//
    					// This prevents the task to restart if device rotation occurs.
    					psnLoginTask = (PSNLoginTask) getLastNonConfigurationInstance();
    					
    					if (psnLoginTask == null) {
    						
    						psnLoginTask = new PSNLoginTask(LoginActivity.this);
    						psnLoginTask.execute(taskParams);
    					}
    					else {
    						
    						psnLoginTask.attach(LoginActivity.this);
    					}
    				}
    				else {
    					UIUtils.createErrorDialog(LoginActivity.this, resources.getString(R.string.provide_login_fields));
    				}
    				
    			}
    			else {
    				UIUtils.createErrorDialog(LoginActivity.this, resources.getString(R.string.wifi_not_enabled));
    			}
			}
		});
    }
    
    /**
     * Ignore orientation change to keep activity from restarting.
     */
    @Override
    public void onConfigurationChanged(final Configuration newConfig) {
        
        super.onConfigurationChanged(newConfig);
    }
    
    
    @Override
    public Object onRetainNonConfigurationInstance() {
    	
    	psnLoginTask.detach();
    	
    	return psnLoginTask;
    }
}