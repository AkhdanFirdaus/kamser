package com.example.kamusfilsafat;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainApp extends Activity {
	
	private SQLiteDatabase db = null;
	private Cursor kamusCursor = null;
	private EditText txtKeyword;
	private EditText txtDefinition;
	private DatabaseHelper datakamus = null;
	public static final String KEYWORD = "keyword";
	public static final String DEFINITION = "definition";
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		datakamus = new DatabaseHelper(this);
		db = datakamus.getWritableDatabase();		
		
		setContentView(R.layout.activity_main_app);
		
		txtKeyword = (EditText) findViewById(R.id.txtKeyword);
		txtDefinition = (EditText) findViewById(R.id.txtDefinition);
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_app, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		try {
			kamusCursor.close();
			db.close();
		} catch (Exception e) {
			
		}
	}
	
	public void getDefinition(View v) {
		String textKeyword = "";
		String textDefinition = "";
		String thekeyword = txtKeyword.getText().toString();
		
		kamusCursor = db.rawQuery("SELECT _ID, KEYWORD, DEFINITION "
				+ "FROM kamus WHERE KEYWORD='" + thekeyword
				+ "' ORDER BY KEYWORD", null);
		
		if (kamusCursor.moveToFirst()) {
			for (; !kamusCursor.isAfterLast(); kamusCursor.moveToNext()) {
				textDefinition = kamusCursor.getString(2);
			}
		} else {
			Toast.makeText(getBaseContext(), "Definisi tidak ditemukan", Toast.LENGTH_SHORT).show();
		}
		
		txtDefinition.setText(textDefinition);
	}
	
}
