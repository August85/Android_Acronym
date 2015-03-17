package com.ak.acronym;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ak.acronym.R;
import com.ak.acronym.database.AcronymDataSource;

public class AddToDatabaseActivity extends Activity {

	private AcronymDataSource acronymDataSource;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_addtodatabase);
		TextView textView = (TextView) findViewById(R.id.textview_addtodatabase_activity);
		textView.setText(R.string.addtodatabase_textview);
	}

	public void add(View view) {
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

		EditText editText1 = (EditText) findViewById(R.id.edittext_acronym_addtodatabase_activity);
		String acronym_input = editText1.getText().toString();

		EditText editText2 = (EditText) findViewById(R.id.edittext_value_addtodatabase_activity);
		String value_input = editText2.getText().toString();

		if (acronym_input.isEmpty() || value_input.isEmpty()) {
			Toast.makeText(this, "Enter valid Acronyms and Values.",
					Toast.LENGTH_LONG).show();
			return;
		}

		acronymDataSource = new AcronymDataSource(this);
		acronymDataSource.open();
		String addedAcronym = acronymDataSource.createAcronym(acronym_input,
				value_input);
		Log.d("acced Acronym: ", addedAcronym);

		TextView textView = (TextView) findViewById(R.id.textview_addtodatabase_activity);
		textView.setText("New Acronym added: \n" + addedAcronym);

		editText1.setText("");
		editText2.setText("");
		acronymDataSource.close();
	}

	@Override
	public void onBackPressed() {
		doExit();
	}

	public void doExit() {
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(
				AddToDatabaseActivity.this);

		alertDialog.setPositiveButton("Yes", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				finish();
			}
		});

		alertDialog.setNegativeButton("No", null);

		alertDialog.setMessage("Do you want to exit?");
		alertDialog.setTitle(R.string.app_name);
		alertDialog.show();

	}

	@Override
	protected void onResume() {
		super.onResume();
		EditText editText1 = (EditText) findViewById(R.id.edittext_acronym_addtodatabase_activity);	
		EditText editText2 = (EditText) findViewById(R.id.edittext_value_addtodatabase_activity);
		editText1.setText("");
		editText2.setText("");
		TextView textView = (TextView) findViewById(R.id.textview_addtodatabase_activity);
		textView.setText("");
	}

	
}
