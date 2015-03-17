package com.ak.acronym;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;

import com.ak.acronym.R;

public class SplashActivity extends Activity {
	private long delay = 2000;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.splash_activity);
		TimerTask task = new TimerTask() {
			@Override
			public void run() {
				finish();
				Intent intent = new Intent().setClass(SplashActivity.this,MyTabActivity.class);
				startActivity(intent);
			}
		};
		Timer timer = new Timer();
		timer.schedule(task, delay);
	}

}
