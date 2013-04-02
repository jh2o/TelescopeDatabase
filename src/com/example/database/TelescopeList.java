package com.example.database;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.AdapterView.OnItemClickListener;

public class TelescopeList extends ListActivity {

	
	 public static final String ROW_ID = "row_id";
	 private ListView conListView;
	 private CursorAdapter conAdapter;
	   
    @SuppressWarnings("deprecation")
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        conListView=getListView();
        conListView.setOnItemClickListener(viewConListener);
        
        // map each name to a TextView
        String[] from = new String[] { "name" };
        int[] to = new int[] { R.id.telescopeTextView };
        conAdapter = new SimpleCursorAdapter(TelescopeList.this, R.layout.telescope_list, null, from, to);
        setListAdapter(conAdapter); // set adapter
    }
    
    
    @Override
    protected void onResume() 
    {
       super.onResume();  
       new GetContacts().execute((Object[]) null);
     } 
    
    
    @SuppressWarnings("deprecation")
	@Override
    protected void onStop() 
    {
       Cursor cursor = conAdapter.getCursor();
       
       if (cursor != null) 
          cursor.deactivate();
       
       conAdapter.changeCursor(null);
       super.onStop();
    }    
    
  
    private class GetContacts extends AsyncTask<Object, Object, Cursor> 
    {
       DatabaseConnector dbConnector = new DatabaseConnector(TelescopeList.this);

       @Override
       protected Cursor doInBackground(Object... params)
       {
          dbConnector.open();
          return dbConnector.getAllContacts(); 
       } 
       
       @Override
       protected void onPostExecute(Cursor result)
       {
          conAdapter.changeCursor(result); // set the adapter's Cursor
          dbConnector.close();
       } 
    } 
       
    @Override
    public boolean onCreateOptionsMenu(Menu menu) 
    {
       super.onCreateOptionsMenu(menu);
       MenuInflater inflater = getMenuInflater();
       inflater.inflate(R.menu.telescope_menu, menu);
       return true;
    }   
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) 
    {
       Intent addContact = new Intent(TelescopeList.this, AddEditTelescope.class);
       startActivity(addContact);
       return super.onOptionsItemSelected(item);
    }
    
    OnItemClickListener viewConListener = new OnItemClickListener() 
    {
       public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) 
       {         
          Intent viewCon = new Intent(TelescopeList.this, ViewTelescope.class);
          viewCon.putExtra(ROW_ID, arg3);
          startActivity(viewCon);
       }
    };    
    
}
