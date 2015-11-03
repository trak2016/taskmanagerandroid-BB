package apps.sstarzak.taskmanager.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

import apps.sstarzak.taskmanager.R;
import butterknife.Bind;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    @Bind(R.id.btn_login)
    Button btn_login;

    @Bind(R.id.et_login)
    EditText et_login;

    @Bind(R.id.et_password)
    EditText et_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);

        btn_login.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        ParseUser.logInInBackground(
                et_login.getText().toString(),
                et_password.getText().toString(), new LogInCallback() {
                    @Override
                    public void done(ParseUser user, ParseException e) {
                        if(e != null) {
                            Log.d("Login error", e.getMessage());
                        } else {
                            startActivity(new Intent(getApplicationContext(),TaskListActivity.class));
                            LoginActivity.this.finish();
                        }
                    }
                }
        );
    }
}
