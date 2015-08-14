package com.android.sample.pinmode;

import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.pushlink.android.AnnoyingPopUpStrategy;
import com.pushlink.android.PushLink;
import com.pushlink.android.StrategyEnum;

import static android.widget.Toast.LENGTH_LONG;

/*
 * This sample app will show you how screen pinning mode works with android lollipop.
 * You have to use at least a device with Android SDK 5.0.0 (means API21)
 *
 * With Pushlink the pin mode is not disable so the incoming update can't be done
 * It would be very helpful to disable every pin mode or kiosk mode if Pushlink.hasPendingUpdate() is true
 */
public class MainActivity extends AppCompatActivity {

    private Button enable;
    private Button disable;

    private TextView description;

    private ActivityManager am;


    ////////////////////////////////////////////////////////////////////////////////////////////////


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pushlinkStart();

        enable = (Button) findViewById(R.id.enable_screen_pinning_button);
        disable = (Button) findViewById(R.id.disable_screen_pinning_button);

        description = (TextView) findViewById(R.id.screen_pinning_description_text);

        am = (ActivityManager) getSystemService(ACTIVITY_SERVICE);

        enable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // ENABLE SCREEN PINNING
                screenPinning(true);
            }
        });

        disable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // DISABLE SCREEN PINNING
                screenPinning(false);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////

    private void pushlinkStart() {

        PushLink.setCurrentStrategy(StrategyEnum.ANNOYING_POPUP);
        AnnoyingPopUpStrategy aps = (AnnoyingPopUpStrategy) PushLink.getCurrentStrategy();
        aps.setPopUpMessage("Please a new update is available.");

        PushLink.start(this, R.drawable.ic_launcher, Auxiliar.API_KEY, Auxiliar.DEVICE_ID);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void screenPinning(boolean enabled) {
        int lollipop = Build.VERSION_CODES.LOLLIPOP;
        int currentSDKVersion = Build.VERSION.SDK_INT;

        if (currentSDKVersion >= lollipop) {

            if (am != null) {
                if (enabled) {
                    if (!am.isInLockTaskMode()) {
                        startLockTask();
                        description.setText(this.getResources().getString(R.string.screen_pinning_active_text));
                    }
                } else {
                    if (am.isInLockTaskMode()) {
                        stopLockTask();
                        description.setText(this.getResources().getString(R.string.screen_pinning_not_active_text));
                    }
                }
            } else {
                Toast.makeText(this, "Problem detected. Please try again.", LENGTH_LONG).show();
            }
        }
    }
}
