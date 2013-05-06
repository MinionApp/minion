package com.example.myfirstapp;

import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;

public class MainActivity extends ListActivity {
	private CharacterDataSource datasource;
	private RefDataSource dsRef;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        datasource = new CharacterDataSource(this);
	    datasource.open();
	    
	    //testing
	    dsRef = new RefDataSource(this);
	    dsRef.open();
	    System.out.println("dsRef created, calling get all characters");
	    //dsRef.getAllCharacters();

	    //List<Character> values = datasource.getAllCharacters();
	    List<Character> values = dsRef.getAllCharacters();

	    // Use the SimpleCursorAdapter to show the
	    // elements in a ListView
	    System.out.println("MAKING ADAPTER");
	    ArrayAdapter<Character> adapter = new ArrayAdapter<Character>(this,
	        android.R.layout.simple_list_item_1, values);
	    System.out.println("SETTING ADAPTER");
	    setListAdapter(adapter);
	    System.out.println("DONE SETTING ADAPTER");
    }

 // Will be called via the onClick attribute
 	// of the buttons in main.xml
 	public void onClick(View view) {
 	  @SuppressWarnings("unchecked")
 	  ArrayAdapter<Character> adapter = (ArrayAdapter<Character>) getListAdapter();
 	  if(adapter.getCount()<1){
// 		  Character character = datasource.createCharacter("Danny McSizzle");
// 		  adapter.add(character);
// 		  adapter.notifyDataSetChanged();
 	  }else{
 		  while(adapter.getCount() > 1){
 			  Character character = (Character)getListAdapter().getItem(0);
 			  datasource.deleteCharacter(character);
 			  adapter.remove(character);
 		  }
 	  }
 	  
 	  	//dsRef.getAllCharacters();
 	}

 	@Override
 	protected void onResume() {
 	  datasource.open();
 	  dsRef.open();
 	  super.onResume();
 	}

 	@Override
 	protected void onPause() {
 	  datasource.close();
 	  dsRef.close();
 	  super.onPause();
 	}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    public void gotoHome(View view) {
    	Intent intent = new Intent(this, HomeActivity.class);
    	startActivity(intent);
    }
}
