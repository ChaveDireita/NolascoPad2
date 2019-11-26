package br.alunos.nolascopad2.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;

import java.util.List;

import br.alunos.nolascopad2.R;
import br.alunos.nolascopad2.database.UserDAO;
import br.alunos.nolascopad2.models.User;
import br.alunos.nolascopad2.net.UserWs;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new CarregarUsuariosTask().execute();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashScreen.this, LoginScreen.class));
                finish();
            }
        }, 2000);
    }

    public void feedDatabase (List<User> users)
    {
        UserDAO dao = new UserDAO(this);
        for (User u : users)
            if (!dao.searchUserByEmail(u.email))
                dao.saveUser(u);

    }

    public class CarregarUsuariosTask extends AsyncTask<Void, Void, List<User>>
    {

        @Override
        protected List<User> doInBackground(Void... voids)
        {
            return UserWs.getAll();
        }

        @Override
        protected void onPostExecute(List<User> users)
        {
            feedDatabase(users);
            super.onPostExecute(users);
        }
    }
}
