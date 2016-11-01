package rss.feed.reader.ui.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rss.feed.reader.Navigation;
import rss.feed.reader.R;
import rss.feed.reader.ui.base.BaseSignActivity;
import rss.feed.reader.utils.MessageUtils;

/**
 * Created by omartynets on 27.10.2016.
 */
public class SignInActivity extends BaseSignActivity {

    @Inject
    public FirebaseAuth mAuth;

    @BindView(R.id.sign_in_button)
    Button mSignInButton;

    @BindView(R.id.sign_up_button_link)
    Button mSignUpButton;

    @BindView(R.id.signin_email)
    EditText mEmailField;

    @BindView(R.id.signin_password)
    EditText mPasswordField;

    @BindView(R.id.btn_link_to_reset_password)
    Button mResetPassword;

    @OnClick(R.id.sign_up_button_link)
    public void goToRegister() {
        Navigation.toSignUpActivity(this);
    }

    @OnClick(R.id.btn_link_to_reset_password)
    public void goToReset(){
        Navigation.toResetPasswordActivity(this);
    }

    @OnClick(R.id.sign_in_button)
    public void signIn() {
        if (isValidCredentials(mEmailField, mPasswordField)) {
            signIn(mEmailField.getText().toString().trim(),mPasswordField.getText().toString().trim());
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        ButterKnife.bind(this);

        getActivityComponent().inject(this);
    }


    private void signIn(String email, String password) {
        showProgress(getString(R.string.progress_mesage_authenticating));
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                hideProgress();
                if (!task.isSuccessful()) {
                    MessageUtils.showSnackBarWithCallback(findViewById(R.id.sign_in_root_layout), task.getException().getMessage());
                } else {
                    Navigation.toMainActivity(SignInActivity.this);
                    finish();
                }
            }
        });
    }
}
