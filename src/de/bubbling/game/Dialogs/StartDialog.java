package de.bubbling.game.Dialogs;


import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import de.bubbling.game.activities.BubblingGameActivity;
import de.bubbling.game.activities.R;

import static android.graphics.BitmapFactory.decodeResource;

/**
 * Created with IntelliJ IDEA.
 * User: Andreas
 * Date: 05.08.13
 * Time: 12:40
 * To change this template use File | Settings | File Templates.
 */
public class StartDialog extends Dialog {

    ImageButton okButton, cancelButton;
    BubblingGameActivity gameActivity;

    public StartDialog(Context context, final BubblingGameActivity gameActivity) {
        super(context);
        this.gameActivity = gameActivity;
        setContentView(R.layout.customdialog);

        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        setCancelable(true);
        setCanceledOnTouchOutside(false);
        okButton = (ImageButton) findViewById(R.id.dialogOK);
        okButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ImageButton okButton = (ImageButton) v;
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    Bitmap image = decodeResource(v.getResources(), R.drawable.startdialogbuttonpressed);
                    okButton.setImageBitmap(image);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    Bitmap image = decodeResource(v.getResources(), R.drawable.startdialogbutton);
                    okButton.setImageBitmap(image);

                    StartDialog.this.dismiss();
                    gameActivity.startGame();
                }
                return true;//To change body of implemented methods use File | Settings | File Templates.
            }
        });

        cancelButton = (ImageButton) findViewById(R.id.dialogCancel);
        cancelButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ImageButton okButton = (ImageButton) v;
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    Bitmap image = decodeResource(v.getResources(), R.drawable.startdialogcancelpressed);
                    okButton.setImageBitmap(image);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    Bitmap image = decodeResource(v.getResources(), R.drawable.startdialogcancel);
                    okButton.setImageBitmap(image);

                    StartDialog.this.dismiss();
                    gameActivity.onBackPressed();
                }
                return true;//To change body of implemented methods use File | Settings | File Templates.
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        gameActivity.onBackPressed();
    }
}
