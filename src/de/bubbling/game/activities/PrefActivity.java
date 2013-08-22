package de.bubbling.game.activities;

import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import de.bubbling.game.components.MyPreferenceManager;

/**
 * Created with IntelliJ IDEA.
 * User: Andreas
 * Date: 07.08.13
 * Time: 09:40
 * To change this template use File | Settings | File Templates.
 */
public class PrefActivity extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (android.os.Build.VERSION.SDK_INT <= 14) {
            setTheme(android.R.style.Theme_NoTitleBar_Fullscreen);
        } else {
            setTheme(android.R.style.Theme_Holo_Light_NoActionBar_Fullscreen);
        }
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);

        Preference reset = (Preference) findPreference(getString(R.string.pref_reset));
        reset.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                new MyPreferenceManager(PrefActivity.this).resetStats();
                return true;
            }
        });

        Preference logout = (Preference) findPreference(getString(R.string.pref_logout));
        logout.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                new MyPreferenceManager(PrefActivity.this).setLogoutOnNextConnect(true);
                return true;
            }
        });
    }
}