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

public class SignUpActivity extends BaseSignActivity {

    @Inject
    public FirebaseAuth mAuth;

    @BindView(R.id.sign_in_button_link)
    Button mSignInButton;

    @BindView(R.id.sign_up_button)
    Button mSignUpButton;

    @BindView(R.id._signup_email)
    EditText mEmailField;

    @BindView(R.id.signup_password)
    EditText mPasswordField;

    @OnClick(R.id.sign_in_button_link)
    public void goToLogin() {
        Navigation.toSignInActivity(this);
    }

    @OnClick(R.id.sign_up_button)
    public void signUp() {
        if (isValidCredentials(mEmailField, mPasswordField)) {
            signUp(mEmailField.getText().toString().trim(),mPasswordField.getText().toString().trim());
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        ButterKnife.bind(this);

        getActivityComponent().inject(this);
    }


    private void signUp(String email, String password) {
        showProgress(getString(R.string.progress_message_authenticating));
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                hideProgress();
                if (!task.isSuccessful()) {
                    MessageUtils.showSnackBarWithCallback(findViewById(R.id.sign_up_root_layout),task.getException().getMessage());
                } else {
                    Navigation.toMainActivity(SignUpActivity.this);
                    finish();
                }
            }
        });
    }
}
