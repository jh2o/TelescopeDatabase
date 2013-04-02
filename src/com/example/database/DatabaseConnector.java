package com.example.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;


public class DatabaseConnector {
	
	private static final String DB_NAME = "dataTelescope";
	private SQLiteDatabase database;
	private DatabaseOpenHelper dbOpenHelper;
	   
	public DatabaseConnector(Context context) {
		dbOpenHelper = new DatabaseOpenHelper(context, DB_NAME, null, 1);
	}
	
	   public void open() throws SQLException 
	   {
	      //open database in reading/writing mode
	      database = dbOpenHelper.getWritableDatabase();
	   } 

	   public void close() 
	   {
	      if (database != null)
	         database.close();
	   }	   
	   
	   public void insertContact(String name, String aper, String ground) 
			   {
			      ContentValues newCon = new ContentValues();
			      newCon.put("name", name);
			      newCon.put("aper", aper);
			      newCon.put("ground", ground);

			      open();
			      database.insert("telescope", null, newCon);
			      close();
			   }

			
			   public void updateContact(long id, String name, String aper,String ground) 
			   {
			      ContentValues editCon = new ContentValues();
			      editCon.put("name", name);
			      editCon.put("aper", aper);
			      editCon.put("ground", ground);

			      open();
			      database.update("telescope", editCon, "_id=" + id, null);
			      close();
			   }

			  
			   public Cursor getAllContacts() 
			   {
			      return database.query("telescope", new String[] {"_id", "name"}, 
			         null, null, null, null, "name");
			   }

			   public Cursor getOneContact(long id) 
			   {
			      return database.query("telescope", null, "_id=" + id, null, null, null, null);
			   }
			   
			   public void deleteContact(long id) 
			   {
			      open(); 
			      database.delete("telescope", "_id=" + id, null);
			      close();
			   }
}
