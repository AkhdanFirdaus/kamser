package com.example.kamusfilsafat;

import android.app.Activity;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class TambahKata extends Activity{
	private SQLiteDatabase db = null;
	
	private EditText txtKeyword;
	private EditText txtDefinition;	
	private DatabaseHelper datakamus = null;
	public static final String KEYWORD = "keyword";
	public static final String DEFINITION = "definition";
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		datakamus = new DatabaseHelper(this);
		db = datakamus.getWritableDatabase();
		setContentView(R.layout.tambahkata);
		txtKeyword = (EditText) findViewById(R.id.txtKeyword);
		txtDefinition = (EditText) findViewById(R.id.txtDefinition);
		
	}
	
	public void saveData(View view) {
		String keyword = txtKeyword.getText().toString();
		String definition = txtDefinition.getText().toString();
		
		ContentValues cv = new ContentValues();
		cv.put(KEYWORD, keyword);
		cv.put(DEFINITION, definition);
		if (db.insert("kamus", KEYWORD, cv)>0) {
			Toast.makeText(getBaseContext(), "Save Data Success", Toast.LENGTH_SHORT).show();	
		} else {
			Toast.makeText(getBaseContext(), "Save Data Gagal", Toast.LENGTH_SHORT).show();
		}
	}
	
	public void onDestroy() {
		super.onDestroy();
		db.close();
	}
}
