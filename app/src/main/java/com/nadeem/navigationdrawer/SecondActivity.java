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
import com.codetroopers.betterpickers.timepicker.TimePickerBuilder;

public class SecondActivity extends BaseActivity {
	private String[] navMenuTitles;
	private TypedArray navMenuIcons;
	private ImageButton boton;
	private TextView texto;
	private ProgressBar barra;
	public static final long MILLIS_TO_MINUTES = 60000;
	public static final long MILLS_TO_HOURS = 3600000;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_second);
		navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items); // load

		HmsPickerBuilder hpb = new HmsPickerBuilder()
				.setFragmentManager(getSupportFragmentManager())
				.setStyleResId(R.style.BetterPickersDialogFragment);
		hpb.show();

		Log.d("data timepicker", hpb.toString());

		texto = (TextView) findViewById(R.id.tv);
		barra= (ProgressBar) findViewById(R.id.circularProgressbar);
		boton = (ImageButton) findViewById(R.id.imageButton);

		boton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				barra.setProgress(100);
				CountDownTimer myCountDownTimer = new MyCountDownTimer(31000, 1000);
				barra.setMax(30000);
				myCountDownTimer.start();

			}
		});



		navMenuIcons = getResources()
				.obtainTypedArray(R.array.nav_drawer_icons);// load icons from
		// strings.xml

		set(navMenuTitles, navMenuIcons);

}
public class MyCountDownTimer extends CountDownTimer {

	public MyCountDownTimer(long millisInFuture, long countDownInterval) {
		super(millisInFuture, countDownInterval);
	}

	@Override
	public void onTick(long millisUntilFinished) {

		int seconds = (int) (millisUntilFinished / 1000) % 60;
		int minutes = (int) ((millisUntilFinished / (MILLIS_TO_MINUTES)) % 60);
		//int hours = (int) ((since / (MILLS_TO_HOURS)) % 24); //this resets to  0 after 24 hour!
		int hours = (int) ((millisUntilFinished / (MILLS_TO_HOURS))); //this does not reset to 0!

		texto.setText(String.format("%02d:%02d:%02d"
				, hours, minutes, seconds));
		barra.setProgress((int)millisUntilFinished);
	}

	@Override
	public void onFinish() {

		texto.setText("00:00:00");
		barra.setProgress(0);
	}

}


}
