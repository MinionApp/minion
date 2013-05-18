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

public class EditGroupActivity extends Activity {

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
	
	public void finishEditing(View view) {
		// TODO Auto-generated method stub
	}
	
	public void inviteNewPlayers(View view) {
		// TODO Auto-generated method stub
	}
	
	public void removePlayerFromGroup(View view) {
		// TODO Auto-generated method stub
	}

	private class UpdateGroupInfoTask extends AsyncTask<String, Void, String> {
		
		private UpdateGroupInfoTask(Context context) {
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
	
	private class SendNewInvitesTask extends AsyncTask<String, Void, String> {
		
		private SendNewInvitesTask(Context context) {
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
	
	private class RemovePlayerTask extends AsyncTask<String, Void, String> {

		private RemovePlayerTask(Context context) {
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
