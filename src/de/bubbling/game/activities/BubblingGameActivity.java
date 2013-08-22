package de.bubbling.game.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.Toast;
import basegameutils.BaseGameActivity;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;

import de.bubbling.game.Dialogs.GameOverDialog;
import de.bubbling.game.Dialogs.LoginDialog;
import de.bubbling.game.Dialogs.StartDialog;
import de.bubbling.game.components.*;
import de.bubbling.game.difficulty.DifficultyProperties;
import de.bubbling.game.views.GameView;
import de.bubbling.game.views.InformationView;


import static com.google.android.gms.common.GooglePlayServicesUtil.isGooglePlayServicesAvailable;


/**
 * Created with IntelliJ IDEA.
 * User: Andreas
 * Date: 26.07.13
 * Time: 15:50
 * To change this template use File | Settings | File Templates.
 */
public class BubblingGameActivity extends BaseGameActivity {


    private static final int LOST_GAME_DIALOG = 1;
    private static final int START_GAME = 2;
    private static final int LOGIN_DIALOG = 3;

    private BubblingGameMaster gameMaster;
    private GameView gameView;
    private InformationView informationView;
    private Handler gameHandler;
    private int width, height;
    private SharedPreferences prefs;
    private MyPreferenceManager stats;
    private GooglePlayServiceController serviceController;
    private int GOOGLE_PLAY;
    private boolean lostGameDialogActive;
    private RelativeLayout layout;

    public void onCreate(Bundle savedInstanceState) {
        if (android.os.Build.VERSION.SDK_INT <= 14) {
            setTheme(android.R.style.Theme_NoTitleBar_Fullscreen);
        } else {
            setTheme(android.R.style.Theme_Holo_Light_NoActionBar_Fullscreen);
        }
        super.onCreate(savedInstanceState);
        initializeGUI();
        initializeHandler();
        GOOGLE_PLAY = isGooglePlayServicesAvailable(BubblingGameActivity.this);
        overridePendingTransition(R.anim.slide_in_to_left, R.anim.slide_out_to_left);
        SceneController.sInstance.setActivity(this);
        stats = new MyPreferenceManager(this);
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        serviceController = new GooglePlayServiceController(getGamesClient(), stats, this);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        boolean doNotShowAgain = prefs.getBoolean(getResources().getString(R.string.pref_donotshowAgain), false);
        boolean loggedIn = stats.getLoggedIn();
        if ((ConnectionResult.SUCCESS == GOOGLE_PLAY) && !(doNotShowAgain || loggedIn)) {
            showGameDialog(LOGIN_DIALOG);
        } else {
            showStartGameDialog();
        }
    }

    public void showStartGameDialog() {
        setTheme(android.R.style.Theme_NoTitleBar_Fullscreen);
        showGameDialog(START_GAME);
    }

    private void initializeGUI() {
        layout = new RelativeLayout(this);
        layout.setBackgroundResource(R.drawable.backgroundgray);
        Display display = ((WindowManager) getSystemService(WINDOW_SERVICE)).getDefaultDisplay();
        width = display.getWidth();
        height = display.getHeight();

        informationView = new InformationView(this, width, height);
        gameView = new GameView(this, width, height);
        layout.addView(informationView);

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(width, height);
        params.setMargins(0, height / GameView.HEIGHT_DIVISOR, 0, 0);
        layout.addView(gameView, params);

        setContentView(layout);
        SceneController.sInstance.addObserver(informationView);
        SceneController.sInstance.addObserver(gameView);

    }

    private void initializeHandler() {
        gameHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.obj instanceof GameProgress) {
                    GameProgress gameProgress = (GameProgress) msg.obj;
                    if (isSignedIn() && stats.getBoolean(getString(R.string.pref_keepLogin))) {
                        serviceController.checkForUnlockedAchievements(gameProgress);
                        uploadCurrentHighscore(gameProgress.getScore());
                    }
                    showGameDialog(LOST_GAME_DIALOG, gameProgress);
                }else if(msg.obj instanceof Scene){
                    Scene s = (Scene) msg.obj;
                    layout.setBackgroundResource(s.getBackground());
                    Toast.makeText(BubblingGameActivity.this, s.getName(), 1000).show();
                }
            }
        };

    }

    private void showGameDialog(int id) {
        showGameDialog(id, null);
    }

    private void showGameDialog(int id, GameProgress gameProgress) {
        switch (id) {
            case LOST_GAME_DIALOG:
                long score = prefs.getLong(getString(R.string.pref_score), 0); //0 is the default value
                stats.updateStats(gameProgress);
                if (score < gameProgress.getScore()) {
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putLong(getString(R.string.pref_score), gameProgress.getScore()).commit();
                    new GameOverDialog(this, this, gameProgress.getScore(), true).show();
                } else {
                    new GameOverDialog(this, this, gameProgress.getScore(), false).show();
                }
                break;
            case START_GAME:
                new StartDialog(this, this).show();
                break;
            case LOGIN_DIALOG:
                final LoginDialog loginDialog = new LoginDialog(this);
                final SharedPreferences finalPrefs = this.prefs;
                loginDialog.getSignInButton().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!(ConnectionResult.SUCCESS == GOOGLE_PLAY)) {

                        }
                        connect();
                        finalPrefs.edit().putBoolean(getResources().getString(R.string.pref_donotshowAgain), loginDialog.getCheckBox().isChecked()).commit();
                        loginDialog.dismiss();
                    }
                });
                loginDialog.getCancelButton().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finalPrefs.edit().putBoolean(getResources().getString(R.string.pref_donotshowAgain), loginDialog.getCheckBox().isChecked()).commit();
                        loginDialog.dismiss();
                    }
                });
                if (!lostGameDialogActive) {
                    loginDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            showStartGameDialog();
                        }
                    });
                }
                loginDialog.show();
                break;
        }
    }

    public void uploadPressed(long score) {

        if (ConnectionResult.SUCCESS != GOOGLE_PLAY) {
            Toast.makeText(this, "You need to update your Google Play Service", 2000).show();
            return;
        }
        if (stats.getLoggedIn()) {
            uploadCurrentHighscore(score);
        } else {
            if (android.os.Build.VERSION.SDK_INT <= 14) {
                setTheme(android.R.style.Theme_NoTitleBar_Fullscreen);
            } else {
                setTheme(android.R.style.Theme_Holo_Light_NoActionBar_Fullscreen);
            }
            lostGameDialogActive = true;

            showGameDialog(LOGIN_DIALOG);
        }
    }

    public void sendMessage(Message msg) {
        gameHandler.sendMessage(msg);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (gameMaster != null)
            gameMaster.stopGame();

        getGamesClient().disconnect();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            gameView.checkHit(event);
        }
        return super.onTouchEvent(event);
    }

    public void startGame() {
        lostGameDialogActive = false;
        gameMaster = new BubblingGameMaster(
                DifficultyProperties.matchWithString("normal"),
                width, height / GameView.HEIGHT_DIVISOR * GameView.HEIGHT_MULTIPLIKATOR);
        new Thread(gameMaster).start();
    }

    public void connect() {
        try {
            beginUserInitiatedSignIn();
            getGamesClient().connect();
            stats.setLoggedIn(true);
        } catch (Exception e) {
            Log.e("BubblingGame", e.toString());
        }
    }

    @Override
    public void onSignInFailed() {
        stats.setLoggedIn(false);
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

    public void uploadCurrentHighscore(long score) {
        try {
            if (getGamesClient() != null && isSignedIn()) {
                if (!getGamesClient().isConnected())
                    getGamesClient().reconnect();
                serviceController.submitHighScore(score);
            } else {
                Toast.makeText(this, "Google Service unavailable, or not logged in", 2000).show();
            }
        } catch (SecurityException e) {
            Log.e("BubblingGame", e.toString());
            Toast.makeText(this, "Google Service unavailable, or not logged in", 2000).show();
        }
    }
}