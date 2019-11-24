package br.alunos.nolascopad2.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import br.alunos.nolascopad2.R;
import br.alunos.nolascopad2.fragments.LoginFragment;

public class LoginScreen extends AppCompatActivity {
    public static final String SAVED_USER = "user_data";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
        SharedPreferences preferences = getSharedPreferences(LoginScreen.SAVED_USER, 0);
        if(preferences.getInt("LoggedUserId",-1)>=0){
            Intent intent = new Intent(this,HomeScreen.class);
            startActivity(intent);
            finish();
        }
        LoginFragment fragment = new LoginFragment();
        FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction();
        transaction.add(R.id.framelog,fragment);
        transaction.commit();
    }
}
