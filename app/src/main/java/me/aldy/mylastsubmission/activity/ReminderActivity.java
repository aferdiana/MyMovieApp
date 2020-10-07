package me.aldy.mylastsubmission.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import me.aldy.mylastsubmission.R;
import me.aldy.mylastsubmission.service.AlarmReceiver;

public class ReminderActivity extends AppCompatActivity{
        public static final String SHAREDPREF_KEY = "sharedpref key";
        public static final String DAILY_KEY = "daily_key";
        public static final String RELEASE_KEY = "release_key";
        private AlarmReceiver alarmReceiver;
        Switch swDaily, swRelease;
        SharedPreferences myPreference;
        SharedPreferences.Editor editor;
        boolean dailyReminder = false;
        boolean releaseReminder = false;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_reminder);

            alarmReceiver = new AlarmReceiver();
            myPreference = getApplicationContext().getSharedPreferences(SHAREDPREF_KEY, Context.MODE_PRIVATE);

            swDaily = findViewById(R.id.switch_daily);
            swRelease = findViewById(R.id.switch_release);

            checkDailyReminder();
            checkReleaseReminder();

            setDailyReminder();
            setReleaseReminder();
        }

        void checkDailyReminder(){
            dailyReminder = (myPreference.getBoolean(DAILY_KEY, false));
            swDaily.setChecked(dailyReminder);
        }

        void setDailyReminder() {
            swDaily.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (b){
                        editor = myPreference.edit();
                        dailyReminder = true;
                        editor.putBoolean(DAILY_KEY, dailyReminder);
                        editor.apply();
                        alarmReceiver.setDailyReminder(getApplicationContext(), AlarmReceiver.TYPE_REPEATING);
                        Toast.makeText(getApplicationContext(), getString(R.string.daily_on), Toast.LENGTH_SHORT).show();
                    }else {
                        editor = myPreference.edit();
                        dailyReminder = false;
                        editor.putBoolean(DAILY_KEY, dailyReminder);
                        editor.apply();
                        alarmReceiver.cancelDailyRemainder(getApplicationContext());
                        Toast.makeText(getApplicationContext(), getString(R.string.daily_off), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

        void checkReleaseReminder(){
            releaseReminder = myPreference.getBoolean(RELEASE_KEY, false);
            swRelease.setChecked(releaseReminder);
        }

        void setReleaseReminder() {
            swRelease.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (b){
                        editor = myPreference.edit();
                        releaseReminder = true;
                        editor.putBoolean(RELEASE_KEY, releaseReminder);
                        editor.apply();
                        alarmReceiver.setReleaseReminder(getApplicationContext(), AlarmReceiver.TYPE_RELEASE);
                        Toast.makeText(getApplicationContext(), getString(R.string.release_on), Toast.LENGTH_SHORT).show();
                    }else {
                        editor = myPreference.edit();
                        releaseReminder = false;
                        editor.putBoolean(RELEASE_KEY, releaseReminder);
                        editor.apply();
                        alarmReceiver.cancelReleaseReminder(getApplicationContext());
                        Toast.makeText(getApplicationContext(), getString(R.string.release_off), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
