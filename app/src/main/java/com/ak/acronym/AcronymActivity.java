package com.ak.acronym;

import java.util.List;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;

import com.ak.acronym.R;
import com.ak.acronym.database.AcronymDataSource;
import com.ak.acronym.database.AcronymData;


public class AcronymActivity extends ListActivity {

	private AcronymDataSource acronymDataSource;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_acronym);

		acronymDataSource = new AcronymDataSource(this);
		acronymDataSource.open();

		List<String> values = acronymDataSource.getAllAcronyms();
		if (values.size() == 0) {
			Toast.makeText(this, "Setting up the application for the first time.",
					Toast.LENGTH_LONG).show();
			fillUpDatabaseForTheFirstTime();
		}
		setContentView(R.layout.activity_acronym);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, values);
		setListAdapter(adapter);

		// registering for context menu
		ListView list = (ListView) findViewById(android.R.id.list);
		registerForContextMenu(list);

		// set action listeners
		this.setActionListeners();

	}

    private void setActionListeners() {

		// add action listener for simple select
		ListView listView = (ListView) findViewById(android.R.id.list);
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				//fetch the acronym value from database and show in a dialog box
				showAcronymValue( position, id);
			}
		});

        listView.setOnItemLongClickListener(new OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                                           int position, long id) {

                List<AcronymData> acronymDataList = acronymDataSource.getEverything();
                AcronymData data = acronymDataList.get(position);
                Toast.makeText(AcronymActivity.this, "Acronym: " + data.getAcronym() + " deleted.", Toast.LENGTH_LONG).show();
                acronymDataSource.deleteAcronym(data.getId());
                onCreate(null);
                return true;

            }
        });
	}
	
	public void showAcronymValue(int position, long id) {

		DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
		    @Override
		    public void onClick(DialogInterface dialog, int which) {
		        switch (which){
		        case DialogInterface.BUTTON_POSITIVE:
		            break;
		        case DialogInterface.BUTTON_NEGATIVE:
		            break;
		        }
		    }
		};
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
        List<AcronymData> acronymDataList = acronymDataSource.getEverything();
        AcronymData data = acronymDataList.get(position);
		builder.setMessage(data.getValue()).setPositiveButton("OK", dialogClickListener).show();
	
	}

    public boolean deleteAcronymValue(int position, long id) {

        return true;
    }
	
	
	@Override
	public void onBackPressed() {
		doExit();
	}

	public void doExit() {
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(
				AcronymActivity.this);

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


	
	@SuppressWarnings("unused")
	public void fillUpDatabaseForTheFirstTime() {
        String one2 = acronymDataSource.createAcronym("SRP", "Single Responsibility Principle");
        String one1 = acronymDataSource.createAcronym("OCP", "Open Closed Principle");

		 String one3 = acronymDataSource.createAcronym("LSP", "Liskov Substitution Principle");
		 String one4 = acronymDataSource.createAcronym("ISP", "Interface Segregation Principle");
		 String one5 = acronymDataSource.createAcronym("DIP", "Dependency Inversion Principle");

		 
	}
	
	@Override
	protected void onResume() {
		acronymDataSource.open();
		super.onResume();
		List<String> values = acronymDataSource.getAllAcronyms();
		if (values.size() == 0) {
			Toast.makeText(this, "Currently No Acronyms in Database",
					Toast.LENGTH_LONG).show();
		}
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, values);
		setListAdapter(adapter);
	}

	@Override
	protected void onPause() {
		acronymDataSource.close();
		super.onPause();
	}

}
