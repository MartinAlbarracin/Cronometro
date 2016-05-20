package com.nadeem.navigationdrawer;

import android.animation.ObjectAnimator;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.codetroopers.betterpickers.hmspicker.HmsPickerBuilder;

public class SecondActivity extends BaseActivity {
	private String[] navMenuTitles;
	private TypedArray navMenuIcons;
	private ImageButton button;
	private TextView texto;
	//Instance of Chronometer
	Chronometer mChrono;

	//Thread for chronometer
	Thread mThreadChrono;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_second);
		navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items); // load
				HmsPickerBuilder hpb = new HmsPickerBuilder()
						.setFragmentManager(getSupportFragmentManager())
						.setStyleResId(R.style.BetterPickersDialogFragment);
				hpb.show();




		navMenuIcons = getResources()
				.obtainTypedArray(R.array.nav_drawer_icons);// load icons from
		// strings.xml

		set(navMenuTitles, navMenuIcons);
	}

	public void updateTimerText(final String time) {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				texto.setText(time);
			}
		});
	}
}
