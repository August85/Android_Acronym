package com.ak.acronym.database;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class AcronymDataSource {

	// Database fields
	private SQLiteDatabase database;
	private AcronymDataHelper dbHelper;
	private String[] allColumns = { AcronymDataHelper.ACRONYM_ID,
			AcronymDataHelper.ACRONYM_VALUE, AcronymDataHelper.ACRONYM };

	public AcronymDataSource(Context context) {
		dbHelper = new AcronymDataHelper(context);
	}

	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}

	public void close() {
		dbHelper.close();
	}

	public void deletefullDatabase() {

	}

	public String createAcronym(String acronym, String value) {
		//first check if this record is already present in database
		List<AcronymData> list = this.getEverything();
		AcronymData data;
		Iterator<AcronymData> iterator = list.iterator();
		while (iterator.hasNext()) {
			data = iterator.next();
			if(data.getAcronym().equals(acronym) && data.getValue().equals(value)) {
				return data.getAcronym();
			}
		}
		
		ContentValues values = new ContentValues();
		values.put(AcronymDataHelper.ACRONYM_VALUE, acronym);
		values.put(AcronymDataHelper.ACRONYM, value);
		Log.w("Parameter 'acronym'", String.valueOf(acronym));
		Log.w("Parameter 'value", String.valueOf(value));

		Log.w("ContentValues:", String.valueOf(values));
		this.open();
		long insertId = database.insert(AcronymDataHelper.TABLE_ACRONYMS, null,
				values);
		Log.w("Insert ID:", String.valueOf(insertId));
		database.close();
		this.open();
		Cursor cursor = database.query(AcronymDataHelper.TABLE_ACRONYMS,
				allColumns, AcronymDataHelper.ACRONYM_ID + "=" + insertId,
				null, null, null, null);
		cursor.moveToFirst();
		AcronymData newAcronym = cursorToData(cursor);
		Log.w("Receipt:", String.valueOf(newAcronym));
		Log.w("Acronym ID:", String.valueOf(newAcronym.getId()));
		Log.w("Acronym :", String.valueOf(newAcronym.getAcronym()));
		Log.w("Acronym Value:",
				String.valueOf(newAcronym.getValue()));

		cursor.close();
		database.close();

		return newAcronym.getValue();
	}

	/*
	 * public ReceiptData createReceipt(String receipt) { ContentValues values =
	 * new ContentValues(); values.put(ReceiptDataHelper.RECEIPT_DATA, receipt);
	 * long insertId = database.insert(ReceiptDataHelper.TABLE_RECEIPTS, null,
	 * values); Cursor cursor = database.query(ReceiptDataHelper.TABLE_RECEIPTS,
	 * allColumns, ReceiptDataHelper.RECEIPT_ID + " = " + insertId, null, null,
	 * null, null); cursor.moveToFirst(); ReceiptData newComment =
	 * cursorToData(cursor); cursor.close(); return newComment; }
	 */

	public void deleteAcronym(AcronymData acronym) {
		long id = acronym.getId();
		if (!database.isOpen()) {
			this.open();
		}
		Log.w("Delete:", "acronym deleted with id: " + id);
		database.delete(AcronymDataHelper.TABLE_ACRONYMS,
				AcronymDataHelper.ACRONYM_ID + " = " + id, null);
		database.close();
	}

	public void deleteAcronym(long id) {
		if (!database.isOpen()) {
			this.open();
		}
		try {
			Log.w("Delete:", "acronym deleted with id: " + id);

			database.delete(AcronymDataHelper.TABLE_ACRONYMS,
					AcronymDataHelper.ACRONYM_ID + " = " + id, null);
			database.close();
		} catch (Exception e) {
			Log.w("Delete:" , "acronym deletion operation failed on id:" + id);
		}
	}

	public List<AcronymData> getEverything() {
		List<AcronymData> acronyms = new ArrayList<AcronymData>();

		if (! database.isOpen()) {
			this.open();
		}
		Cursor cursor = database.query(AcronymDataHelper.TABLE_ACRONYMS,
				allColumns, null, null, null, null, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			AcronymData data = cursorToData(cursor);
			acronyms.add(data);
			cursor.moveToNext();
		}
		cursor.close();
		database.close();
		return acronyms;
	}

	public List<String> getAllValues() {
		List<String> values = new ArrayList<String>();
		if (!database.isOpen()) {
			this.open();
		}
		Cursor cursor = database.query(AcronymDataHelper.TABLE_ACRONYMS,
				allColumns, null, null, null, null, null);
		// String one = cursor.getString(0);
		// String two = cursor.getString(1);
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			AcronymData receipt = cursorToData(cursor);
			String desc = receipt.getValue();
			values.add(desc);
			cursor.moveToNext();
		}
		cursor.close();
		database.close();
		return values;
	}
	public List<String> getAllAcronyms() {
		List<String> values = new ArrayList<String>();
		if (!database.isOpen()) {
			this.open();
		}
		Cursor cursor = database.query(AcronymDataHelper.TABLE_ACRONYMS,
				allColumns, null, null, null, null, null);
		// String one = cursor.getString(0);
		// String two = cursor.getString(1);
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			AcronymData receipt = cursorToData(cursor);
			String desc = receipt.getAcronym();
			values.add(desc);
			cursor.moveToNext();
		}
		cursor.close();
		database.close();
		return values;
	}


	public long findIdForValue(String value) {
		long id = 0;
		boolean flag_loop = true;
		if (!database.isOpen()) {
			this.open();
		}
		Cursor cursor = database.query(AcronymDataHelper.TABLE_ACRONYMS,
				allColumns, null, null, null, null, null);
		cursor.moveToFirst();
		while (!cursor.isAfterLast() && flag_loop == true) {
			AcronymData acronym = cursorToData(cursor);
			String d = acronym.getValue();
			if (d.equals(value)) {
				id = acronym.getId();
				flag_loop = false;
			}
			cursor.moveToNext();
		}
		cursor.close();
		database.close();
		return id;
	}

	private AcronymData cursorToData(Cursor cursor) {
		AcronymData acronym = new AcronymData();
		acronym.setId(cursor.getLong(0));
		acronym.setAcronym(cursor.getString(1));
		acronym.setValue(cursor.getString(2));
		return acronym;
	}

	public String getValue(long id) {
		String vlaue = null;
		boolean loop_flag = true;
		if (!database.isOpen()) {
			this.open();
		}
		Cursor cursor = database.query(AcronymDataHelper.TABLE_ACRONYMS,
				allColumns, null, null, null, null, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast() && loop_flag == true) {
			AcronymData aData = cursorToData(cursor);
			if (aData.getId() == id) {
				vlaue = aData.getValue();
				loop_flag = false;
			}
			cursor.moveToNext();
		}
		cursor.close();
		database.close();
		return vlaue;
	}

	public String getAcronym(long id) {
		String acronym = null;
		boolean loop_flag = true;
		if (!database.isOpen()) {
			this.open();
		}
		Cursor cursor = database.query(AcronymDataHelper.TABLE_ACRONYMS,
				allColumns, null, null, null, null, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast() && loop_flag == true) {
			AcronymData receipt = cursorToData(cursor);
			if (receipt.getId() == id) {
				acronym = receipt.getAcronym();
				loop_flag = false;
			}
			cursor.moveToNext();
		}
		cursor.close();
		database.close();
		return acronym;
	}

	public boolean deleteAll() {

		if( !database.isOpen()) {
			this.open();
		}
		try {
			database.delete(AcronymDataHelper.TABLE_ACRONYMS, null, null);
			database.close();
			return true;
		} catch (Exception e) {
			database.close();
			return false;
		}
	}
}