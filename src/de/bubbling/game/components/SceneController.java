package de.bubbling.game.components;

import android.app.Activity;
import android.os.Message;
import de.bubbling.game.activities.BubblingGameActivity;

import java.util.Observable;

/**
 * Created with IntelliJ IDEA.
 * User: Andreas
 * Date: 26.07.13
 * Time: 15:27
 * To change this template use File | Settings | File Templates.
 */
public class SceneController extends Observable {
    public static SceneController sInstance = new SceneController();
    private Activity activity;

    public void updateObservers(Object update) {
        setChanged();
        notifyObservers(update);
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public void lostGame(GameProgress gameProgress) {
        BubblingGameActivity gameActivity = (BubblingGameActivity) activity;
        Message msg = new Message();
        msg.obj = gameProgress;
        gameActivity.sendMessage(msg);
    }

    public void changeScene(Scene scene) {
        BubblingGameActivity gameActivity = (BubblingGameActivity) activity;
        Message msg = new Message();
        msg.obj = scene;
        gameActivity.sendMessage(msg);
    }
}
