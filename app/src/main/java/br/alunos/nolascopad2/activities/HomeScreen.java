package br.alunos.nolascopad2.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import br.alunos.nolascopad2.R;
import br.alunos.nolascopad2.fragments.CreateBook;
import br.alunos.nolascopad2.fragments.ListLocalBooks;

public class HomeScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        ListLocalBooks fragment = new ListLocalBooks();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.listcreateframe,fragment);
        transaction.commit();
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
    }
}
