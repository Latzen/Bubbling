package de.bubbling.game.Dialogs;

import android.app.Dialog;
import android.content.Context;
import android.widget.Button;
import android.widget.CheckBox;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import de.bubbling.game.activities.R;

import static com.google.android.gms.common.GooglePlayServicesUtil.isGooglePlayServicesAvailable;

/**
 * Created with IntelliJ IDEA.
 * User: Andreas
 * Date: 06.08.13
 * Time: 20:42
 * To change this template use File | Settings | File Templates.
 */
public class LoginDialog extends Dialog {

    final Context context;
    private CheckBox checkBox;
    private SignInButton signInButton;
    private Button cancelButton, signInSDK10;


    public LoginDialog(final Context context) {
        super(context);

        setTitle(context.getString(R.string.dialog_login_title));
        this.context = context;
        int result = isGooglePlayServicesAvailable(context);
        if ((ConnectionResult.SERVICE_VERSION_UPDATE_REQUIRED == result)) {
            setContentView(R.layout.loginlayoutsdk10);
            setCanceledOnTouchOutside(false);
            cancelButton = (Button) findViewById(R.id.logout);
            checkBox = (CheckBox) findViewById(R.id.doNotShow);
            signInSDK10 = (Button) findViewById(R.id.sign_in);
        } else {
            setContentView(R.layout.loginlayout);
            setCanceledOnTouchOutside(false);
            signInButton = (SignInButton) findViewById(R.id.sign_in);
            cancelButton = (Button) findViewById(R.id.logout);
            checkBox = (CheckBox) findViewById(R.id.doNotShow);
        }
        setCanceledOnTouchOutside(false);

    }

    public CheckBox getCheckBox() {
        return checkBox;
    }

    public SignInButton getSignInButton() {
        return signInButton;
    }

    public Button getCancelButton() {
        return cancelButton;
    }

    public Button getSignInSDK10() {
        return signInSDK10;
    }
}

