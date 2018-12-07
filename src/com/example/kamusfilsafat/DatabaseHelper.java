package com.example.kamusfilsafat;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = "dbkamus";
	public static final String KEYWORD = "keyword";
	public static final String DEFINITION = "definition";

	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, 1);
	}
	
	public void createTable(SQLiteDatabase db) {
		db.execSQL("DROP TABLE IF EXISTS kamus");
		db.execSQL("CREATE TABLE if not exists kamus ("
				+ "_id INTEGER PRIMARY KEY AUTOINCREMENT, "
				+ "keyword TEXT, "
				+ "definition TEXT);" 
		);
	}
	
	public void generateData(SQLiteDatabase db) {
		ContentValues cv = new ContentValues();
		cv.put(KEYWORD, "badan astral");
		cv.put(DEFINITION, "badan orang yang sudah meninggal yang tampak sebagai bayangan sinar yang bercahaya (bersifat metafisika); badan (roh) halus");
		db.insert("kamus", KEYWORD, cv);
		
		cv.put(KEYWORD, "dualis");
		cv.put(DEFINITION, "orang yang menganut paham dualisme");
		db.insert("kamus", KEYWORD, cv);
		
		cv.put(KEYWORD, "egois");
		cv.put(DEFINITION, "penganut teori egoisme");
		db.insert("kamus", KEYWORD, cv);
		
		cv.put(KEYWORD, "egoisme");
		cv.put(DEFINITION, "teori tentang segala perbuatan atau tindakan selalu disebabkan oleh keinginan untuk menguntungkan diri sendiri");
		db.insert("kamus", KEYWORD, cv);
		
		cv.put(KEYWORD, "eksistensialis");
		cv.put(DEFINITION, "penganut eksistensialisme");
		db.insert("kamus", KEYWORD, cv);
		
		cv.put(KEYWORD, "eksistensialisme");
		cv.put(DEFINITION, "aliran filsafat yang pahamnya berpusat pada manusia individu yang bertanggung jawab atas kemauannya yang bebas tanpa mengetahui mana yang benar dan mana yang tidak benar");
		db.insert("kamus", KEYWORD, cv);
		
		cv.put(KEYWORD, "kolektivisme");
		cv.put(DEFINITION, "ajaran atau paham yang tidak menghendaki adanya hak milik perseorangan, baik atas modal, tanah, maupun alat produksi (semua harus dijadikan milik bersama, kecuali barang konsumsi)");
		db.insert("kamus", KEYWORD, cv);
		
		cv.put(KEYWORD, "neoplatonisme");
		cv.put(DEFINITION, "aliran campuran falsafah atau ajaran Plato dan kemistikan Timur");
		db.insert("kamus", KEYWORD, cv);
		
		cv.put(KEYWORD, "organon");
		cv.put(DEFINITION, "alat untuk memperoleh dan mengatur pengetahuan");
		db.insert("kamus", KEYWORD, cv);
		
		cv.put(KEYWORD, "sekularis");
		cv.put(DEFINITION, "penganut aliran yang menghendaki agar kesusilaan atau budi pekerti tidak didasarkan pada ajaran agama");
		db.insert("kamus", KEYWORD, cv);
		
		cv.put(KEYWORD, "yang");
		cv.put(DEFINITION, "kekuatan maskulin dan positif dalam alam semesta");
		db.insert("kamus", KEYWORD, cv);
		
		cv.put(KEYWORD, "yin");
		cv.put(DEFINITION, "kekuatan feminin, negatif, dan pasif dalam alam semesta");
		db.insert("kamus", KEYWORD, cv);
				
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldBersion, int newVersion) {
		createTable(db);
		generateData(db);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		createTable(db);
		generateData(db);
	}
}
