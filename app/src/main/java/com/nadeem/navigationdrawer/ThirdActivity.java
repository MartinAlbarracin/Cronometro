package com.nadeem.navigationdrawer;

import android.animation.ObjectAnimator;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.codetroopers.betterpickers.hmspicker.HmsPickerBuilder;
import com.codetroopers.betterpickers.hmspicker.HmsPickerDialogFragment;
//import com.codetroopers.betterpickers.sample.R;
//import com.codetroopers.betterpickers.sample.activity.BaseSampleActivity;

public class ThirdActivity extends BaseActivity implements HmsPickerDialogFragment.HmsPickerDialogHandlerV2 {

	private String[] navMenuTitles;
	private TypedArray navMenuIcons;
	private ImageButton boton;
	private TextView texto;
	private ProgressBar barra;
	public static final long MILLIS_TO_MINUTES = 60000;
	public static final long MILLS_TO_HOURS = 3600000;
	private TextView mResultTextView;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_third);
		navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items); // load

		mResultTextView = (TextView) findViewById(R.id.text);
		Button button = (Button) findViewById(R.id.button);

		mResultTextView.setText(R.string.no_value);
		button.setText(R.string.hms_picker_set);

		button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				HmsPickerBuilder hpb = new HmsPickerBuilder()
						.setFragmentManager(getSupportFragmentManager())
						.setStyleResId(R.style.BetterPickersDialogFragment);
				hpb.show();
			}
		});


		navMenuIcons = getResources()
				.obtainTypedArray(R.array.nav_drawer_icons);// load icons from
		// strings.xml

		set(navMenuTitles, navMenuIcons);

	}

	@Override
	public void onDialogHmsSet ( int reference, boolean isNegative, int hours, int minutes,
								 int seconds){
		mResultTextView.setText(getString(R.string.hms_picker_result_value_multiline, hours, minutes, seconds, isNegative));

	}



}

