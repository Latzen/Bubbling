package de.bubbling.game.Dialogs;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import de.bubbling.game.activities.BubblingGameActivity;
import de.bubbling.game.activities.R;

import static android.graphics.BitmapFactory.decodeResource;

/**
 * Created with IntelliJ IDEA.
 * User: Andreas
 * Date: 05.08.13
 * Time: 13:59
 * To change this template use File | Settings | File Templates.
 */
public class GameOverDialog extends Dialog {

    private final BubblingGameActivity gameActivity;
    ImageButton okButton, uploadButton;
    Context context;

    public GameOverDialog(final Context context, final BubblingGameActivity gameActivity, final long score, boolean newHighscore) {
        super(context);
        this.gameActivity = gameActivity;
        this.context = context;
        setContentView(R.layout.customgameoverdialog);
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        TextView text = (TextView) findViewById(R.id.highscoretext);

        if (newHighscore) {
            text.setText("New Highscore: ");
        } else {
            text.setText("score: ");
        }

        TextView scoreText = (TextView) findViewById(R.id.score);
        scoreText.setText("" + score);

        setCancelable(true);
        setCanceledOnTouchOutside(false);
        okButton = (ImageButton) findViewById(R.id.dialogOK);

        Bitmap image = decodeResource(context.getResources(), R.drawable.gameoverdialogrestart);
        okButton.setImageBitmap(image);

        okButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ImageButton okButton = (ImageButton) v;
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    Bitmap image = decodeResource(v.getResources(), R.drawable.gameoverdialogrestartpressed);
                    okButton.setImageBitmap(image);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    Bitmap image = decodeResource(v.getResources(), R.drawable.gameoverdialogrestart);
                    okButton.setImageBitmap(image);
                    GameOverDialog.this.dismiss();
                    gameActivity.startGame();
                }
                return true;//To change body of implemented methods use File | Settings | File Templates.
            }
        });

        uploadButton = (ImageButton) findViewById(R.id.dialogCancel);
        image = decodeResource(context.getResources(), R.drawable.gameoverdialogupload);
        uploadButton.setImageBitmap(image);
        uploadButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ImageButton okButton = (ImageButton) v;
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    Bitmap image = decodeResource(v.getResources(), R.drawable.gameoverdialoguploadpressed);
                    okButton.setImageBitmap(image);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    Bitmap image = decodeResource(v.getResources(), R.drawable.gameoverdialogupload);
                    okButton.setImageBitmap(image);
                    gameActivity.uploadPressed(score);
                }
                return true;
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        gameActivity.onBackPressed();
    }
}
