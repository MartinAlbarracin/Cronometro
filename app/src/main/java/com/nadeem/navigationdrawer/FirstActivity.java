package com.nadeem.navigationdrawer;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class FirstActivity extends BaseActivity {
	private String[] navMenuTitles;
	private TypedArray navMenuIcons;
	private TextView mTvTimer;


	public static final String START_TIME = "START_TIME";
	/**
	 * Same story, but to tell whether the Chronometer was running or not
	 */
	public static final String CHRONO_WAS_RUNNING = "CHRONO_WAS_RUNNING";
	/**
	 * Same story, but if chronometer was stopped, we dont want to lose the stop time shows in
	 * the tv_timer
	 */
	public static final String TV_TIMER_TEXT = "TV_TIMER_TEXT";
	/**
	 * Same story, we don't want to lose recorded laps
	 */
	public static final String ET_LAPST_TEXT = "ET_LAPST_TEXT";
	/**
	 * Same story...keeps the value of the lap counter
	 */
	public static final String LAP_COUNTER  = "LAP_COUNTER";

	private long timeWhenStopped = 0;

	//Member variables to access UI Elements
	Button mBtnStart, mBtnLap, mBtnStop; //buttons
	EditText mEtLaps; //laps text view
	ScrollView mSvLaps; //scroll view which wraps the et_laps

	//keep track of how many times btn_lap was clicked
	int mLapCounter = 1;

	//Instance of Chronometer
	Chronometer mChrono;

	//Thread for chronometer
	Thread mThreadChrono;

	//Reference to the MainActivity (this class!)
	Context mContext;

	int flag;



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_first);
		navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items); // load
																					// titles
																					// from
																					// strings.xml
		mContext = this;

		mBtnStart = (Button) findViewById(R.id.btn_start);
		mBtnLap = (Button) findViewById(R.id.btn_lap);
		mBtnStop = (Button) findViewById(R.id.btn_stop);

		mTvTimer = (TextView) findViewById(R.id.tv_timer);
		mEtLaps = (EditText) findViewById(R.id.et_laps);
		mEtLaps.setEnabled(false); //prevent the et_laps to be editable
		mSvLaps = (ScrollView) findViewById(R.id.sv_lap);

		mBtnStart.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//if the chronometer has not been instantiated before...
				if (flag == 1) {
					if (mChrono != null) {
						mBtnStart.setEnabled(false);
						mBtnLap.setEnabled(false);
						mBtnStop.setEnabled(true);
						//stop the chronometer
						mChrono.stop();
						//stop the thread
						mThreadChrono.interrupt();
						mThreadChrono = null;
						flag = 0;
					}
				} else {
					if (mChrono == null) {
						//instantiate the chronometer
						//
						mChrono = new Chronometer(mContext);
						mThreadChrono = new Thread(mChrono);
						mBtnStart.setText("stop");
						flag = 1;
						mThreadChrono.start();
						//start the chronom
						mChrono.start();
						mBtnStop.setEnabled(false);
					} else {
						mThreadChrono = new Thread(mChrono);
						mThreadChrono.start();
						mChrono.start();
						mBtnStart.setText("stop");
						flag = 1;
					}
				}
			}
		});
		//btn_stop click handler
		mBtnStop.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//if the chronometer had been instantiated before...
				if (mChrono != null) {
					//stop the chronometer
					mChrono = null;
					mEtLaps.setText("");
					mTvTimer.setText("00:00:00:000");
					mBtnStart.setEnabled(true);
					mBtnStart.setText("Start");
					mBtnLap.setEnabled(true);
					mLapCounter=1;
				}
			}
		});

		mBtnLap.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				//if chrono is not running we shouldn't capture the lap!
				if (mChrono == null) {
					Toast.makeText(mContext, "Your Application has not started", Toast.LENGTH_SHORT).show();
					return; //do nothing!
				}
				//we just simply copy the current text of tv_timer and append it to et_laps
				mEtLaps.append("LAP " + String.valueOf(mLapCounter++)
						+ "   " + mTvTimer.getText() + "\n");

				//scroll to the bottom of et_laps
				mSvLaps.post(new Runnable() {
					@Override
					public void run() {
						mSvLaps.smoothScrollTo(0, mEtLaps.getBottom());
					}
				});
			}
		});

		navMenuIcons = getResources()
				.obtainTypedArray(R.array.nav_drawer_icons);// load icons from
															// strings.xml

		set(navMenuTitles, navMenuIcons);
	}
	public void updateTimerText(final String time) {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				mTvTimer.setText(time);
			}
		});
	}
}
