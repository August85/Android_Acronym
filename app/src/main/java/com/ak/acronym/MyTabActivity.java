package com.ak.acronym;

import android.app.AlertDialog;
import android.app.TabActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

import com.ak.acronym.R;

@SuppressWarnings("deprecation")
public class MyTabActivity extends TabActivity {
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab_activity);

        TabHost tabHost=getTabHost();
        // no need to call TabHost.Setup()        

        TabSpec spec1=tabHost.newTabSpec("Tab 1");
        spec1.setIndicator("Acronyms");
        Intent in1=new Intent(this, AcronymActivity.class);
        spec1.setContent(in1);
        
        TabSpec spec2=tabHost.newTabSpec("Tab 2");
        spec2.setIndicator("Search");
        Intent in2=new Intent(this,SearchActivity.class);
        spec2.setContent(in2);

        TabSpec spec3=tabHost.newTabSpec("Tab 3");
        spec3.setIndicator("Add");
        Intent in3=new Intent(this,AddToDatabaseActivity.class);
        spec3.setContent(in3);
        
        tabHost.addTab(spec1);
        tabHost.addTab(spec2);
        tabHost.addTab(spec3);
        
    }
	@Override
	public void onBackPressed() {
		doExit();
	}

	public void doExit() {
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(
				MyTabActivity.this);

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

}
