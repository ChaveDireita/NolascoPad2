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

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashScreen.this, LoginScreen.class));
                finish();
            }
        }, 2000);
    }
}
