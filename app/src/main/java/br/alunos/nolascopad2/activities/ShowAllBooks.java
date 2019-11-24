package br.alunos.nolascopad2.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import br.alunos.nolascopad2.R;
import br.alunos.nolascopad2.fragments.ListLocalBooks;
import br.alunos.nolascopad2.fragments.ListPublicBooks;

public class ShowAllBooks extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all_books);
        ListPublicBooks fragment = new ListPublicBooks();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.listpublicframe,fragment);
        transaction.commit();
    }
}
