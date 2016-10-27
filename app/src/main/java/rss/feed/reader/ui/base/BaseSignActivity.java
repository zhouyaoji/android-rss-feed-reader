package rss.feed.reader.ui.base;

import android.support.annotation.Nullable;
import android.widget.EditText;

import rss.feed.reader.R;
import rss.feed.reader.dagger.components.ActivityComponent;

/**
 * Base class for SignIn and SignUp Activities
 * Created by omartynets on 28.10.2016.
 */
public class BaseSignActivity extends BaseActivity{

    private static final String emailPattern = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";

    @Override
    protected void injectActivity(ActivityComponent activityComponent) {
        activityComponent.inject(this);
    }

    protected boolean isValidCredentials(EditText emailField, @Nullable EditText passwordField) {
        String password = "";

        String email = emailField.getText().toString().trim();
        if (passwordField != null) {
            password = passwordField.getText().toString();
        }

        if (!email.matches(emailPattern)) {
            setEmailError(emailField);
            return false;
        }

        if (password.isEmpty()) {
            setPasswordError(passwordField);
            return false;
        }
        return true;
    }

    private void setEmailError(EditText emailField) {
        emailField.requestFocus();
        emailField.setError(getString(R.string.email_error));
    }

    private void setPasswordError(EditText passwordField) {
        passwordField.requestFocus();
        passwordField.setError(getString(R.string.password_error), null);
    }
}
