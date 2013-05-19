package uw.cse403.minion;


import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.support.v4.app.NavUtils;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

public class CharCreateMainActivity extends Activity {
	private static final String CHARACTER_ID = "cid";
	
	private static final String PHP_ADDRESS = "http://homes.cs.washington.edu/~elefse/uploadCharacter.php";
	private String username;
	
	private Character newChar = null;
	private long charID;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_char_create_main);
		username = SaveSharedPreference.getPersistentUserName(CharCreateMainActivity.this);
		if(newChar == null){
			newChar = new Character();
		}
		try {
			charID = this.getIntent().getExtras().getLong(CHARACTER_ID);
		} catch (Exception e) {
			charID = CharacterDataSource.getNewID();
		}
		// Show the Up button in the action bar.
		setupActionBar();
	}

	/**
	 * Set up the {@link android.app.ActionBar}, if the API is available.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.char_create_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void gotoBasicInfo(View view) {
		Intent intent = new Intent(this, BasicInfoActivity.class);
		intent.putExtra(CHARACTER_ID, charID);
		startActivity(intent);
	}
	
	public void gotoAbilityScores(View view) {
		Intent intent = new Intent(this, AbilityScoresActivity.class);
		intent.putExtra(CHARACTER_ID, charID);
		startActivity(intent);
	}
	
	public void gotoSkills(View view) {
		Intent intent = new Intent(this, SkillsActivity.class);
		intent.putExtra(CHARACTER_ID, charID);
		startActivity(intent);
	}
	
	public void gotoCombat(View view) {
		Intent intent = new Intent(this, CombatActivity.class);
		intent.putExtra(CHARACTER_ID, charID);
		startActivity(intent);
	}
	
	public void gotoSavingThrows(View view) {
		Intent intent = new Intent(this, SavingThrowsActivity.class);
		intent.putExtra(CHARACTER_ID, charID);
		startActivity(intent);
	}
	
	public void gotoCharacterList(View view) {
    	// Checks for internet connectivity
    	if (ConnectionChecker.hasConnection(this)) {
    	    // Updates login credentials on remote database
    		UploadCharacterTask task = new UploadCharacterTask(this);
    	    task.execute(username);
    	} else {
    		
    	}
	}

	private class UploadCharacterTask extends AsyncTask<String, Void, String> {
		private Context context;
		
		private UploadCharacterTask (Context context) {
			this.context = context;
		}
		
	    /**
	     * Makes the HTTP request and returns the result as a String.
	     */
	    protected String doInBackground(String... args) {
	    	CharacterDescription baseInfo = new CharacterDescription(charID);
	    	
	    	JSONObject basicInfo = new JSONObject();
	    	try {
	    		long id = charID;
				basicInfo.put("local_id", id);
		    	basicInfo.put("name", baseInfo.name);
		    	
		    	basicInfo.put("alignment", baseInfo.alignment);
		    	basicInfo.put("level", baseInfo.level);
		    	basicInfo.put("diety", baseInfo.deity);
		    	basicInfo.put("homeland", baseInfo.homeLand);
		    	basicInfo.put("race", baseInfo.race);
		    	basicInfo.put("size", baseInfo.size);
		    	basicInfo.put("gender", baseInfo.gender);
		    	basicInfo.put("age", baseInfo.age);
		    	basicInfo.put("height", baseInfo.height);
		    	basicInfo.put("weight", baseInfo.weight);
		    	basicInfo.put("hair", baseInfo.hair);
		    	basicInfo.put("eyes", baseInfo.eyes);
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	    	
	    	Ability[] abilities = new Ability[6];
			abilities[0] = new Ability(charID, AbilityName.STRENGTH);
			abilities[1] = new Ability(charID, AbilityName.DEXTERITY);
			abilities[2] = new Ability(charID, AbilityName.CONSTITUTION);
			abilities[3] = new Ability(charID, AbilityName.INTELLIGENCE);
			abilities[4] = new Ability(charID, AbilityName.WISDOM);
			abilities[5] = new Ability(charID, AbilityName.CHARISMA);
			
			JSONObject abilityObject = new JSONObject();
	    	JSONArray abilityScores = new JSONArray();
	    	try {
	    		for(int i = 0; i < 6; i++) {
			    	JSONObject ability = new JSONObject();
			    	ability.put("name", abilities[i].getName());
			    	ability.put("score", abilities[i].getScore());
			    	ability.put("mod", abilities[i].getMod());
			    	abilityScores.put(ability);
	    		}
	    		abilityObject.put("abilities", abilityScores);
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	    	
	    	
			/*JSONObject skillsObject = new JSONObject();
	    	JSONArray abilityScores = new JSONArray();
	    	try {
	    		for(int i = 0; i < 6; i++) {
			    	JSONObject ability = new JSONObject();
			    	ability.put("name", abilities[i].getName());
			    	ability.put("score", abilities[i].getScore());
			    	ability.put("mod", abilities[i].getMod());
			    	abilityScores.put(ability);
	    		}
	    		abilityObject.put("abilities", abilityScores);
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}*/

	    	
	        //the data to send
	        ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
	        postParameters.add(new BasicNameValuePair("username", username));
	        postParameters.add(new BasicNameValuePair("basicInfo", basicInfo.toString()));
	        postParameters.add(new BasicNameValuePair("abilities", abilityObject.toString()));
	        //postParameters.add(new BasicNameValuePair("skills", skillsObject.toString()));
	        //Log.i("JSON", skillsObject.toString());
			String result = null;
	        
	        //http post
			String res;
	        try{
	        	result = CustomHttpClient.executeHttpPost(PHP_ADDRESS, postParameters);
	        	res = result.toString();    
	        	res = res.replaceAll("\\s+", "");   
	        } catch (Exception e) {   
	        	res = e.toString();
	        }
	        return res;
	    }
	 
	    /**
	     * Parses the String result and directs to the correct Activity
	     */
	    protected void onPostExecute(String result) {
        	Intent intent = new Intent(context, CharactersActivity.class);
        	intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
	    }
	}	 
}
