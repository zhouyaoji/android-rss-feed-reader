package rss.feed.reader.ui.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rss.feed.reader.R;
import rss.feed.reader.ui.base.BaseSignActivity;
import rss.feed.reader.utils.MessageUtils;

/**
 * Created by omartynets on 28.10.2016.
 */
public class ResetPasswordActivity extends BaseSignActivity {

    @Inject
    public FirebaseAuth mAuth;

    private String mEmail;

    @BindView(R.id.email)
    EditText mEmailField;

    @BindView(R.id.btn_reset_password)
    Button mButtonReset;

    @OnClick(R.id.btn_reset_password)
    public void resetPassword() {

        mEmail = mEmailField.getText().toString().trim();

        resetPasswordProcess();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        ButterKnife.bind(this);

        getActivityComponent().inject(this);

    }

    private void resetPasswordProcess() {
        if (!isValidCredentials(mEmailField, null)) {
            return;
        }

        showProgress(getString(R.string.progress_message_authenticating));
        mAuth.sendPasswordResetEmail(mEmail)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        MessageUtils.showSnackBarWithCallback(findViewById(R.id.reset_password_root_layout),
                                (task.isSuccessful()) ? getString(R.string.email_sent_message) : task.getException().getMessage());
                        hideProgress();
                    }
                });
    }
}
