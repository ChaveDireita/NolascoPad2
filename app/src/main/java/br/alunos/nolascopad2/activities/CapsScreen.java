package br.alunos.nolascopad2.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;

import br.alunos.nolascopad2.R;
import br.alunos.nolascopad2.fragments.ListLocalCaps;

public class CapsScreen extends AppCompatActivity {
    public int bookid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caps_screen);
        Intent intento = getIntent();
        bookid = intento.getIntExtra("BookId",-1);
        ListLocalCaps fragment = new ListLocalCaps();
        Bundle bundle = new Bundle();
        bundle.putInt("CurrentBook",bookid);
        fragment.setArguments(bundle);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.listcreatecapsframe,fragment);
        transaction.commit();
    }
}
