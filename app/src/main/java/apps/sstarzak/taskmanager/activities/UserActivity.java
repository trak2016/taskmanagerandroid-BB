package apps.sstarzak.taskmanager.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import apps.sstarzak.taskmanager.R;
import butterknife.Bind;
import butterknife.ButterKnife;

public class UserActivity extends AppCompatActivity implements View.OnClickListener{

    @Bind(R.id.create)
    Button create;

    @Bind(R.id.login)
    Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        ButterKnife.bind(this);

        create.setOnClickListener(this);
        login.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.create: startActivity(new Intent(this, RegistrationActivity.class)); break;
            case R.id.login: startActivity(new Intent(this, LoginActivity.class)); break;
        }
    }
}
