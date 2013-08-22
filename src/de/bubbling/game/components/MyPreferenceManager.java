package de.bubbling.game.components;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import de.bubbling.game.activities.R;
import de.bubbling.game.difficulty.DifficultyProperties;

/**
 * Created with IntelliJ IDEA.
 * User: Andreas
 * Date: 08.08.13
 * Time: 18:37
 * To change this template use File | Settings | File Templates.
 */
public class MyPreferenceManager {
    private SharedPreferences prefs;
    private Context context;

    public MyPreferenceManager(Context context) {
        this.context = context;
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public void updateStats(GameProgress progress) {
        updateStats(progress.getTimePlayed(), progress.getPerfectStrokes(), progress.getStrokesInARow(), progress.getScore(), progress.getStokesOverall());
    }

    public void updateStats(long sessionPlayed, int perfectStrokes, int longestPerfectStroke, long score, int strokesOverall) {
        SharedPreferences.Editor edit = prefs.edit();
        edit.putLong(context.getString(R.string.pref_timePlayed), getTimePlayed() + sessionPlayed);
        edit.putInt(context.getString(R.string.pref_perfectStrokes), getPerfectStrokes() + perfectStrokes);
        edit.putInt(context.getString(R.string.pref_strokesOverall), getStrokesOverall() + strokesOverall);
        if (getLongestPerfectStroke() < longestPerfectStroke) {
            edit.putInt(context.getString(R.string.pref_longestStroke), longestPerfectStroke);
        }
        /*long scoreT = prefs.getLong(context.getString(R.string.pref_score), 0); //0 is the default value
        if(scoreT < score){
            edit.putLong(context.getString(R.string.pref_score), score);
        }*/
        updateScore(score);
        edit.commit();
    }

    private void updateScore(long score) {
        DifficultyProperties.Difficulty diff = DifficultyProperties.matchWithString(getDifficulty());
        String scoreDiff = "";
        switch (diff) {
            case normal:
                scoreDiff = context.getResources().getString(R.string.leaderboard_normal);
                break;
            case hard:
                scoreDiff = context.getResources().getString(R.string.leaderboard_hard);
                break;
            default:
                scoreDiff = context.getResources().getString(R.string.leaderboard_easy);
        }
        long currentScore = prefs.getLong(scoreDiff, 0);
        if (score > currentScore) {
            prefs.edit().putLong(scoreDiff, score).commit();
        }

    }

    public void resetStats() {
        SharedPreferences.Editor edit = prefs.edit();
        edit.putLong(context.getString(R.string.pref_timePlayed), 0);
        edit.putInt(context.getString(R.string.pref_perfectStrokes), 0);
        edit.putInt(context.getString(R.string.pref_strokesOverall), 0);
        edit.putInt(context.getString(R.string.pref_longestStroke), 0);
        edit.putLong(context.getString(R.string.leaderboard_normal), 0);
        edit.putLong(context.getString(R.string.pref_score), 0);
        edit.commit();
    }

    public long getTimePlayed() {
        return prefs.getLong(context.getString(R.string.pref_timePlayed), 0);
    }

    public int getPerfectStrokes() {
        return prefs.getInt(context.getString(R.string.pref_perfectStrokes), 0);
    }

    public int getLongestPerfectStroke() {
        return prefs.getInt(context.getString(R.string.pref_longestStroke), 0);
    }

    public int getStrokesOverall() {
        return prefs.getInt(context.getString(R.string.pref_strokesOverall), 0);
    }

    public boolean getLogout() {
        return prefs.getBoolean(context.getString(R.string.pref_logout), false);
    }

    public void setLogoutOnNextConnect(boolean bool) {
        prefs.edit().putBoolean(context.getString(R.string.pref_logout), bool).commit();
    }

    public boolean getLoggedIn() {
        return prefs.getBoolean(context.getString(R.string.pref_loggedin), false);
    }

    public void setLoggedIn(boolean bool) {
        prefs.edit().putBoolean(context.getString(R.string.pref_loggedin), bool).commit();
    }

    public String getDifficulty() {
        return prefs.getString(context.getString(R.string.pref_diff), "normal");
    }

    public void setDifficulty(String difficulty) {
        prefs.edit().putString(context.getString(R.string.pref_diff), difficulty).commit();
    }

    public boolean getRemDiff() {
        return prefs.getBoolean(context.getString(R.string.pref_rememberdiff), false);
    }

    public void setRemDiff(boolean bool) {
        prefs.edit().putBoolean(context.getString(R.string.pref_rememberdiff), bool).commit();
    }

    public String getString(String s) {
        return prefs.getString(s, "");
    }

    public long getLong(String s) {
        return prefs.getLong(s, 0);
    }

    public boolean getBoolean(String s) {
        return prefs.getBoolean(s, false);
    }
}
