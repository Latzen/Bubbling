package de.bubbling.game.components;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;
import com.google.android.gms.games.GamesClient;
import com.google.android.gms.games.leaderboard.OnScoreSubmittedListener;
import com.google.android.gms.games.leaderboard.SubmitScoreResult;
import de.bubbling.game.activities.R;
import de.bubbling.game.difficulty.DifficultyProperties;

/**
 * Created with IntelliJ IDEA.
 * User: Andreas
 * Date: 11.08.13
 * Time: 11:21
 * To change this template use File | Settings | File Templates.
 */
public class GooglePlayServiceController {

    GamesClient gamesClient;
    MyPreferenceManager manager;
    Context context;

    private static int ACHIEVEMENT_50K = 50000, ACHIEVEMENT_25K = 25000, ACHIEVEMENT_10K, ACHIEVEMENT_STROKE = 10;
    private static int ACHIEVEMENT_10_MIN_PLAYED = 10, ACHIEVEMENT_60_MIN_PLAYED = 60;

    public GooglePlayServiceController(GamesClient gamesClient, MyPreferenceManager manager, Context context) {
        this.gamesClient = gamesClient;
        this.manager = manager;
        this.context = context;
    }

    public void submitHighScore(long score) {

        String leaderboard = getLeaderBoardForDifficulty(manager, context);

        gamesClient.submitScoreImmediate(new OnScoreSubmittedListener() {
            @Override
            public void onScoreSubmitted(int i, SubmitScoreResult submitScoreResult) {
                Log.d("Submitted Score", "--" + submitScoreResult.toString());
                if (submitScoreResult.getStatusCode() == GamesClient.STATUS_OK)
                    Toast.makeText(context, "Uploaded Highscore!", 2000).show();
                else {
                    Toast.makeText(context, "Google Service unavailable", 2000).show();
                }
            }
        }, leaderboard, score);
    }

    public static String getLeaderBoardForDifficulty(MyPreferenceManager manager, Context context) {
        DifficultyProperties.Difficulty diff = DifficultyProperties.matchWithString(manager.getDifficulty());
        String leaderboard = "";
        switch (diff) {
            case normal:
                leaderboard = context.getResources().getString(R.string.leaderboard_normal);
                break;
            case hard:
                leaderboard = context.getResources().getString(R.string.leaderboard_hard);
                break;
            default:
                leaderboard = context.getResources().getString(R.string.leaderboard_easy);
        }
        return leaderboard;
    }

    public void checkForUnlockedAchievements(GameProgress gameProgress) {
        if (gameProgress.score > ACHIEVEMENT_10K) {
            gamesClient.unlockAchievement(context.getString(R.string.ten_thousand));
        }
        if (gameProgress.score > ACHIEVEMENT_25K) {
            gamesClient.unlockAchievement(context.getString(R.string.twenty_five_thousand));
        }
        if (gameProgress.score > ACHIEVEMENT_50K) {
            gamesClient.unlockAchievement(context.getString(R.string.fifty_thousand));
        }
        if (gameProgress.getStrokesInARow() > ACHIEVEMENT_STROKE) {
            gamesClient.unlockAchievement(context.getString(R.string.perfect));
        }

        if(manager.getTimeAsMinute()>=ACHIEVEMENT_10_MIN_PLAYED){
            gamesClient.unlockAchievement(context.getString(R.string.bubbling_friend));
        }
        if(manager.getTimeAsMinute()>=ACHIEVEMENT_60_MIN_PLAYED){
            gamesClient.unlockAchievement(context.getString(R.string.bubbling_addicted));
        }
        if (gameProgress.getStageReached() >= LevelDesigner.MAXIMAL_STAGE) {
            gamesClient.unlockAchievement(context.getString(R.string.reachlaststage));
        }
    }
}





