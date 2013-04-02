package com.example.database;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class AddEditTelescope extends Activity {
	
	 private long rowID; 
	 private EditText nameEt;
	 private EditText aperEt;
	 private EditText groundEt;
	   
	   @Override
	   public void onCreate(Bundle savedInstanceState) 
	   {
	      super.onCreate(savedInstanceState); 
	      setContentView(R.layout.add_telescope);

	      nameEt = (EditText) findViewById(R.id.nameEdit);
	      aperEt = (EditText) findViewById(R.id.aperEdit);
	      groundEt = (EditText) findViewById(R.id.groundEdit);
	      
	      Bundle extras = getIntent().getExtras(); 
	      
	      if (extras != null)
	      {
	         rowID = extras.getLong("row_id");
	         nameEt.setText(extras.getString("name"));  
	         aperEt.setText(extras.getString("aper"));  
	         groundEt.setText(extras.getString("ground"));  
	      }
	      
	      Button saveButton =(Button) findViewById(R.id.saveBtn);
	      saveButton.setOnClickListener(new OnClickListener() {
			
	    	  public void onClick(View v) 
	          {
	             if (nameEt.getText().length() != 0)
	             {
	                AsyncTask<Object, Object, Object> saveContactTask = 
	                   new AsyncTask<Object, Object, Object>() 
	                   {
	                      @Override
	                      protected Object doInBackground(Object... params) 
	                      {
	                         saveContact();
	                         return null;
	                      }
	          
	                      @Override
	                      protected void onPostExecute(Object result) 
	                      {
	                         finish();
	                      }
	                   }; 
	                  
	                saveContactTask.execute((Object[]) null); 
	             }
	             
	             else
	             {
	                AlertDialog.Builder alert = new AlertDialog.Builder(AddEditTelescope.this);
	                alert.setTitle(R.string.errorTitle); 
	                alert.setMessage(R.string.errorMessage);
	                alert.setPositiveButton(R.string.errorButton, null); 
	                alert.show();
	             }
	          } 
		 });
	   }
	   
	   private void saveContact() 
	   {
	      DatabaseConnector dbConnector = new DatabaseConnector(this);

	      if (getIntent().getExtras() == null)
	      {
	    	  dbConnector.insertContact(nameEt.getText().toString(),
	    			  aperEt.getText().toString(),
	    			  groundEt.getText().toString());
	      }
	      else
	      {
	         dbConnector.updateContact(rowID,
	            nameEt.getText().toString(),
	            aperEt.getText().toString(), 
	            groundEt.getText().toString());
	      }
	   }
	}
