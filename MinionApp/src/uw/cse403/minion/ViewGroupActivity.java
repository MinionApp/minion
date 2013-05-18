package uw.cse403.minion;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class ViewGroupActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar() {
		// TODO Auto-generated method stub
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		return false;
	}
	
	public void editGroup(View view) {
		// TODO Auto-generated method stub
	}
	
	public void acceptInvite(View view) {
		// TODO Auto-generated method stub
	}
	
	public void declineInvite(View view) {
		// TODO Auto-generated method stub
	}

	private class HasPendingInviteTask extends AsyncTask<String, Void, String> {
		
		private HasPendingInviteTask(Context context) {
			// TODO Auto-generated method stub
		}
		
	    protected String doInBackground(String... args) {
	    	// TODO Auto-generated method stub
	    	return "";
	    }
	 
	    protected void onPostExecute(String result) {
	    	// TODO Auto-generated method stub
	    }
	}
	
	private class AcceptInviteTask extends AsyncTask<String, Void, String> {
		
		private AcceptInviteTask(Context context) {
			// TODO Auto-generated method stub
		}
		
	    protected String doInBackground(String... args) {
	    	// TODO Auto-generated method stub
	    	return "";
	    }
	 
	    protected void onPostExecute(String result) {
	    	// TODO Auto-generated method stub
	    }
	 
	}
	
	private class DeclineInviteTask extends AsyncTask<String, Void, String> {

		private DeclineInviteTask(Context context) {
			// TODO Auto-generated method stub
		}
		
	    protected String doInBackground(String... args) {
	    	// TODO Auto-generated method stub
	    	return "";
	    }
	 
	    protected void onPostExecute(String result) {
	    	// TODO Auto-generated method stub
	    }
	}
}
