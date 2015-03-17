package com.ak.acronym;

import java.util.Iterator;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ak.acronym.R;
import com.ak.acronym.database.AcronymData;
import com.ak.acronym.database.AcronymDataSource;

public class SearchActivity extends Activity {

	private AcronymDataSource acronymDataSource;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		TextView textView = (TextView) findViewById(R.id.textview_search_activity);
		textView.setText(R.string.search_edittext);
		EditText editText = (EditText) findViewById(R.id.edittext_search_activity);
		editText.setText("");

	}

	public void search(View view) {

		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

		AcronymData acronymData;
		acronymDataSource = new AcronymDataSource(this);
		acronymDataSource.open();

		
		EditText editText = (EditText) findViewById(R.id.edittext_search_activity);
		String searchString = editText.getText().toString();
		searchString = searchString.toUpperCase();
		
		if (searchString.isEmpty() ) {
			Toast.makeText(this, "Enter valid values to be searched.",
					Toast.LENGTH_LONG).show();
			return;
		}


		TextView textView = (TextView) findViewById(R.id.textview_search_activity);
		textView.setText("\n");

		boolean stringFound = false;
		List<AcronymData> values = acronymDataSource.getEverything();
		Iterator<AcronymData> iterator = values.iterator();
		while (iterator.hasNext()) {
			acronymData = iterator.next();
			if (acronymData.getAcronym().equalsIgnoreCase(searchString)) {
				stringFound = true;
				String text = acronymData.getAcronym() + " = "
						+ acronymData.getValue() + "\n";
				textView.append(text);
			}
		}
		if (!stringFound) {
			textView.setText("Sorry no Acronyms Found");
		}
		acronymDataSource.close();
	}
	
	@Override
	public void onBackPressed() {
		doExit();
	}

	public void doExit() {
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(
				SearchActivity.this);

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
		EditText editText = (EditText) findViewById(R.id.edittext_search_activity);
		editText.setText("");
		TextView textView = (TextView) findViewById(R.id.textview_search_activity);
		textView.setText("");
		
	}

	
}
