package com.example.kamusfilsafat;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MenuActivity extends ListActivity {
	String[] menuutama = new String[] {"Terjemah Kata", "Tambah Kata", "Daftar Kata", "Keluar Aplikasi"};
	
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		
		this.setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, menuutama));
	}
	
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		
		Object o = this.getListAdapter().getItem(position);
		String pilihan = o.toString();
		
		tampilkanPilihan(pilihan);
	}
	
	protected void tampilkanPilihan(String pilihan) {
		try {
			Intent i = null;
			if (pilihan.equals("Terjemah Kata")) {
				i = new Intent(this, MainApp.class);
			} 
			else if (pilihan.equals("Tambah Kata")) {
				i = new Intent(this, TambahKata.class);
			} 
			else if (pilihan.equals("Daftar Kata")) {
				i = new Intent(this, DaftarKata.class);
			}
			else if (pilihan.equals("KeluarAplikasi")) {
				finish();
			} 
			else {
				Toast.makeText(this, "Anda Memilih: " + pilihan + " , Actionnya belum dibuat", Toast.LENGTH_LONG).show();
			}
			startActivity(i);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
