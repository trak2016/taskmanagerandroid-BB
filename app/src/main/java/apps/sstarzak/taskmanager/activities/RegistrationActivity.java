package apps.sstarzak.taskmanager.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import apps.sstarzak.taskmanager.R;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegistrationActivity extends AppCompatActivity {

    @Bind(R.id.et_login)
    EditText et_login;

    @Bind(R.id.et_password)
    EditText et_password;

    @Bind(R.id.et_email)
    EditText et_email;

    @OnClick(R.id.btn_register)
    public void register() {
        final ParseUser parseUser = new ParseUser();
        parseUser.setUsername(et_login.getText().toString());
        parseUser.setPassword(et_password.getText().toString());
        parseUser.setEmail(et_email.getText().toString());

        parseUser.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {
                if (e != null) {
                    Log.d("Registration Error", e.getMessage());
                } else {
                    ParseUser.logInInBackground(et_login.getText().toString(),
                            et_password.getText().toString(), new LogInCallback() {
                                @Override
                                public void done(ParseUser user, ParseException e) {
                                    startActivity(new Intent(getApplicationContext(),
                                            TaskListActivity.class));
                                    RegistrationActivity.this.finish();
                                }
                            });
                }
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        ButterKnife.bind(this);
    }
}
