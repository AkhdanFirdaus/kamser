package com.example.kamusfilsafat;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class DaftarKata extends Activity {
	private DatabaseHelper dbhelper;
	private SQLiteDatabase db = null;
	private ListView listContent = null;
	private static final int EDIT_ID= Menu.FIRST + 1;
	private static final int DELETE_ID = Menu.FIRST + 2;
	
	private Cursor kamusCursor = null;
	CustomCursorAdapter adapter;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		dbhelper = new DatabaseHelper(this);
		
		setContentView(R.layout.daftarkata);
		listContent = (ListView) findViewById(R.id.list1);
		isDataListView();
		registerForContextMenu(listContent);
	}
	
	private void isDataListView() {
		try {
			db = dbhelper.getWritableDatabase();
			
			kamusCursor = db.query("kamus", new String[] {"_id", "keyword", "definition"}, "_id>0", null, null, null, null);
			
			String[] from = new String[] {"keyword", "definition"};
			int[] to = new int[] {R.id.keyword, R.id.definition};
			
			adapter = new CustomCursorAdapter(this, R.layout.row, kamusCursor, from, to);
			listContent.setAdapter(adapter);			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (db != null && db.isOpen()) {
//				db.close();
			}
		}
	}
	
	@Override 
	public void onDestroy() {
		super.onDestroy();
		try {
			kamusCursor.close();
		} catch (Exception e) {
			
		}
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
		menu.add(Menu.NONE, DELETE_ID, Menu.NONE, "Hapus").setIcon(R.drawable.ic_launcher).setAlphabeticShortcut('e');
		menu.add(Menu.NONE, EDIT_ID, Menu.NONE, "Edit").setIcon(R.drawable.ic_launcher).setAlphabeticShortcut('d');
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		switch(item.getItemId()) {
		case DELETE_ID:
			AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
			delete(info.id);
			return (true);
			
		case EDIT_ID:
			AdapterView.AdapterContextMenuInfo infox = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
			Cursor ckamusCursor = db.rawQuery("SELECT _ID, keyword, definition FROM kamus where _ID=" + infox.id, null);
			ckamusCursor.moveToFirst();
			edit(infox.id, ckamusCursor.getString(1), ckamusCursor.getString(2));
			return (true);
		}
		
		return (super.onOptionsItemSelected(item));
	}
	
	private void edit(long id, String pkeyword, String pdefinition) {
		LayoutInflater inflater = LayoutInflater.from(this);
		View addView = inflater.inflate(R.layout.edit, null);
		EditText ekeyword = (EditText) addView.findViewById(R.id.keyword);
		EditText edefinition = (EditText) addView.findViewById(R.id.definition);
		ekeyword.setText(pkeyword);
		edefinition.setText(pdefinition);
		
		final DialogWrapper wrapper = new DialogWrapper(addView);
		final long xid = id;
		
		new AlertDialog.Builder(this)
			.setTitle(R.string.add_title)
			.setView(addView)
			.setPositiveButton(R.string.save, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int whichButton) {
					processEdit(wrapper, xid);
				}
			}).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int whichButton) {
					
				}
			}).show();
	}	
	
	private void delete(final long rowId) {
		if (rowId > 0) {
			new AlertDialog.Builder(this)
				.setTitle(R.string.delte_title)
				.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener()  {
					
					public void onClick(DialogInterface dialog, int whichButton) {
						processDelete(rowId);
						
					}
				}).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
					
					public void onClick(DialogInterface dialog, int whichButton) {
												
					}
				}).show();
		}
	}
	
	private void processEdit(DialogWrapper wrapper, long id) {
		ContentValues values = new ContentValues(2);
		values.put("keyword", wrapper.getkeyword());
		values.put("definition", wrapper.getdefintion());
		db.update("kamus", values, "_id=" + id, null);
		
		kamusCursor.requery();
	}
	
	private void processDelete(long rowId) {
		String[] args = {String.valueOf(rowId)};
		
		db.delete("kamus", "_ID=?", args);
		kamusCursor.requery();
	}
	
	class DialogWrapper {
		EditText keywordField = null;
		EditText definitionField = null;
		View base = null;
		
		DialogWrapper(View base) {
			this.base = base;
			keywordField = (EditText) base.findViewById(R.id.keyword);
		}
		
		String getkeyword() {
			return (getkeywordField().getText().toString());
		}
		
		String getdefintion() {
			return (getdefinitionField().getText().toString());
		}
		
		private EditText getkeywordField() {
			if (keywordField == null) {
				keywordField = (EditText) base.findViewById(R.id.keyword);						
			}
			return (keywordField);
		}
		
		private EditText getdefinitionField() {
			if (definitionField == null) {
				definitionField = (EditText) base.findViewById(R.id.definition);							
			}
			return (definitionField);
		}
	}
	
	protected class CustomCursorAdapter extends SimpleCursorAdapter {
		private int layout;
		private LayoutInflater inflater;
		private Context context;
		
		public CustomCursorAdapter(Context context, int layout, Cursor c, String[] from, int[] to) {
			super(context, layout, c, from, to);
			this.layout = layout;
			this.context = context;
			inflater = LayoutInflater.from(context);
		}
		
		@Override
		public View newView(Context context, Cursor cursor, ViewGroup parent) {
			Log.d("NewView", "*****xxx");
			
			View v = inflater.inflate(R.layout.row, parent, false);
			
			return v;
		}
		
		public void bindView(View v, Context context, Cursor c) {
			String keyword = c.getString(1);
			String definition = c.getString(2);
			
			TextView name_text = (TextView) v.findViewById(R.id.keyword);
			TextView id_text = (TextView) v.findViewById(R.id.definition);
			
			id_text.setText(definition);
			
			if (name_text != null) {
				name_text.setText(keyword);
			}
		}
	}

}