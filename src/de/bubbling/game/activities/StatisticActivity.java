package de.bubbling.game.activities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.*;
import android.widget.*;
import basegameutils.BaseGameActivity;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import de.bubbling.game.Dialogs.LoginDialog;
import de.bubbling.game.components.GooglePlayServiceController;
import de.bubbling.game.components.MyPreferenceManager;
import de.bubbling.game.difficulty.DifficultyProperties;

import static android.graphics.BitmapFactory.decodeResource;
import static com.google.android.gms.common.GooglePlayServicesUtil.isGooglePlayServicesAvailable;

/**
 * Created with IntelliJ IDEA.
 * User: Andreas
 * Date: 05.08.13
 * Time: 14:54
 * To change this template use File | Settings | File Templates.
 */
public class StatisticActivity extends BaseGameActivity {

    private static final int LOGIN_DIALOG = 3;
    private static final int CHOOSE_DIFFICULTY = 1;
    private static int GOOGLE_PLAY;
    boolean connected;
    SharedPreferences prefs;
    private MyPreferenceManager stats;

    public void onCreate(Bundle savedInstanceState) {
        if (android.os.Build.VERSION.SDK_INT <= 14) {
            setTheme(android.R.style.Theme_NoTitleBar_Fullscreen);
        } else {
            setTheme(android.R.style.Theme_Holo_Light_NoActionBar_Fullscreen);
        }
        super.onCreate(savedInstanceState);
        GOOGLE_PLAY = isGooglePlayServicesAvailable(StatisticActivity.this);
        setContentView(R.layout.statisticlayout);
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        stats = new MyPreferenceManager(this);
        TextView overallStrokes = (TextView) findViewById(R.id.strokesOverall);
        overallStrokes.setText(getString(R.string.help_strokesoveral) + stats.getStrokesOverall());
        //easy
        TextView scoreViewEasy = (TextView) findViewById(R.id.currenthighscore_easy);
        scoreViewEasy.setText("" + stats.getLong(getString(R.string.leaderboard_normal)));
        TextView strokesinarow = (TextView) findViewById(R.id.perfectStrokesInRow);
        strokesinarow.setText(getString(R.string.help_strokesinrow) + stats.getLongestPerfectStroke());
        //normal maybe later
        /*TextView scoreViewNormal = (TextView) findViewById(R.id.currenthighscore_normal);
        scoreViewNormal.setText("" + stats.getLong(getString(R.string.leaderboard_normal)));
        //hard
        TextView scoreViewHard = (TextView) findViewById(R.id.currenthighscore_hard);
        scoreViewHard.setText("" + stats.getLong(getString(R.string.leaderboard_hard)));*/

        TextView perfect = (TextView) findViewById(R.id.perfectStrokes);
        perfect.setText(getString(R.string.help_perfect) + stats.getPerfectStrokes());

        TextView time = (TextView) findViewById(R.id.timePlayed);
        int min = 0, hh = 0;
        long sec = stats.getTimePlayed();
        if (sec > 60) {
            min = (int) sec / 60;
            sec = sec % 60;
            if (min > 60) {
                hh = min / 60;
                min = min % 60;
            }
        }
        String ss = sec < 10 ? "0" + sec : Long.toString(sec);
        String sm = min < 10 ? "0" + min : Integer.toString(min);
        String sh = hh < 10 ? "0" + hh : Integer.toString(hh);
        time.setText(getString(R.string.help_timeplayed) + sh + ":" + sm + ":" + ss);


        if (getGamesClient() != null) {
            if (getGamesClient().isConnected()) getGamesClient().disconnect();

            getGamesClient().registerConnectionCallbacks(new GooglePlayServicesClient.ConnectionCallbacks() {
                @Override
                public void onConnected(Bundle bundle) {
                    connected = true;
                }

                @Override
                public void onDisconnected() {
                }
            });

            if (prefs.getBoolean(String.valueOf(R.string.pref_keepLogin), false)) {
                getGamesClient().connect();
            }
        }


        Button showLeaderboardButton = (Button) findViewById(R.id.showleaderboard);
        showLeaderboardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ConnectionResult.SUCCESS != GOOGLE_PLAY) {
                    Toast.makeText(StatisticActivity.this, "You need to update your Google Play Service", 2000).show();
                    return;
                }
                if (connected || stats.getLoggedIn()) {
                    // if(!stats.getRemDiff()){
                    //     showDialog(CHOOSE_DIFFICULTY);
                    // }
                    // else{
                    try {
                        startActivityForResult(getGamesClient().
                                getLeaderboardIntent(getString(R.string.leaderboard_normal)), 1);
                    } catch (Exception e) {
                        Log.e("HelpAcitivity", e.toString());
                    }
                    // }

                } else {
                    showDialog(LOGIN_DIALOG);
                    Toast.makeText(StatisticActivity.this, "Could not connect to Google Play Games", 2000);
                }
            }
        });

    }

    @Override
    public void onSignInFailed() {
    }

    @Override
    public void onSignInSucceeded() {
        if (stats.getLogout()) {
            signOut();
            getGamesClient().disconnect();
            stats.setLogoutOnNextConnect(false);
            stats.setLoggedIn(false);
        }
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case LOGIN_DIALOG:
                final LoginDialog loginDialog = new LoginDialog(this);
                final SharedPreferences finalPrefs = this.prefs;
                if (android.os.Build.VERSION.SDK_INT <= 14) {
                    int result = isGooglePlayServicesAvailable(StatisticActivity.this);
                    if (!(ConnectionResult.SUCCESS == result)) {

                    }
                    try {
                        beginUserInitiatedSignIn();
                        getGamesClient().connect();
                        stats.setLoggedIn(true);
                    } catch (Exception e) {
                        Log.e("HelpActivity", e.toString());
                    }
                    finalPrefs.edit().putBoolean(getResources().getString(R.string.pref_donotshowAgain), loginDialog.getCheckBox().isChecked()).commit();
                    loginDialog.dismiss();
                } else {
                    loginDialog.getSignInButton().setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            int result = isGooglePlayServicesAvailable(StatisticActivity.this);
                            if (!(ConnectionResult.SUCCESS == result)) {

                            }
                            try {
                                beginUserInitiatedSignIn();
                                getGamesClient().connect();
                            } catch (Exception e) {
                                Log.e("HelpActivity", e.toString());
                            }
                            finalPrefs.edit().putBoolean(getResources().getString(R.string.pref_donotshowAgain), loginDialog.getCheckBox().isChecked()).commit();
                            loginDialog.dismiss();
                        }
                    });
                }
                loginDialog.getCancelButton().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finalPrefs.edit().putBoolean(getResources().getString(R.string.pref_donotshowAgain), loginDialog.getCheckBox().isChecked()).commit();
                        loginDialog.dismiss();
                    }
                });
                loginDialog.show();
                break;
            case CHOOSE_DIFFICULTY:
                View chooseView = View.inflate(this, R.layout.choosediff, null);
                ListAdapter adapter = new ArrayAdapter<String>(getApplicationContext(),
                        android.R.layout.simple_list_item_1, DifficultyProperties.difficulties) {
                    @Override
                    public View getView(int position, View convertView, ViewGroup parent) {
                        View view = super.getView(position, convertView, parent);

                        TextView textView = (TextView) view.findViewById(android.R.id.text1);
                        switch (position) {
                            case 0:
                                textView.setTextColor(Color.BLUE);
                                break;
                            case 1:
                                textView.setTextColor(Color.RED);
                                break;
                            case 2:
                                textView.setTextColor(Color.BLACK);
                        }
                        return view;
                    }
                };
                final CheckBox remember = (CheckBox) chooseView.findViewById(R.id.remembermydecision);
                ListView diff = (ListView) chooseView.findViewById(R.id.diffList);
                diff.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        stats.setDifficulty(DifficultyProperties.difficulties[position]);
                        stats.setRemDiff(remember.isChecked());
                        dismissDialog(CHOOSE_DIFFICULTY);
                        startActivityForResult(getGamesClient().
                                getLeaderboardIntent(GooglePlayServiceController.
                                        getLeaderBoardForDifficulty(stats, StatisticActivity.this)), 1);
                    }
                });
                diff.setAdapter(adapter);
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Choose difficulty");
                builder.setView(chooseView);
                return builder.create();
        }
        return super.onCreateDialog(id);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        getGamesClient().disconnect();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu:
                startActivity(new Intent(this, PrefActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}