package br.alunos.nolascopad2.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;

import br.alunos.nolascopad2.R;
import br.alunos.nolascopad2.fragments.EditCap;
import br.alunos.nolascopad2.fragments.EditLivro;
import br.alunos.nolascopad2.fragments.UserEdit;

public class GeneralEditActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general_edit);
        Intent intent = getIntent();
        Intent intent1 = getIntent();
        int whichOne = intent.getIntExtra("WhichOne",0);
        switch (whichOne){
            case 1:
                int bookid = intent1.getIntExtra("BookId",-1);
                EditLivro fragment = new EditLivro();
                Bundle bundle = new Bundle();
                bundle.putInt("CurrentBook",bookid);
                fragment.setArguments(bundle);
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.editframe,fragment);
                transaction.commit();
                break;
            case 2:
                int capid = intent1.getIntExtra("CapId",-1);
                EditCap capfragment = new EditCap();
                Bundle bundle2 = new Bundle();
                bundle2.putInt("CurrentCap",capid);
                capfragment.setArguments(bundle2);
                FragmentTransaction transaction2 = getSupportFragmentManager().beginTransaction();
                transaction2.replace(R.id.editframe,capfragment);
                transaction2.commit();
                break;
            case 3:
                int userid = intent1.getIntExtra("UserId",-1);
                UserEdit usershowfragment = new UserEdit();
                Bundle bundle3 = new Bundle();
                bundle3.putInt("CurrentUser",userid);
                usershowfragment.setArguments(bundle3);
                FragmentTransaction transaction3 = getSupportFragmentManager().beginTransaction();
                transaction3.replace(R.id.editframe,usershowfragment);
                transaction3.commit();
                break;
        }
    }
}
