package br.alunos.nolascopad2.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.ui.AppBarConfiguration;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

import br.alunos.nolascopad2.R;
import br.alunos.nolascopad2.fragments.ListLocalBooks;
import br.alunos.nolascopad2.fragments.ListPublicBooks;

public class ShowAllBooks extends AppCompatActivity {
    private AppBarConfiguration mAppBarConfiguration;
    private DrawerLayout drawer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all_books);
        ListPublicBooks fragment = new ListPublicBooks();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.listpublicframe,fragment);
        transaction.commit();
        Toolbar toolbar = findViewById(R.id.allbookstoolbar);
        drawer = findViewById(R.id.allbooksdrawer);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Intent intent;
                switch (menuItem.getItemId()){
                    case R.id.nav_home:
                        intent = new Intent(ShowAllBooks.this,HomeScreen.class);
                        startActivity(intent);
                        finish();
                        break;
                    case R.id.nav_showuser:
                        intent = new Intent(ShowAllBooks.this,ShowUserScreen.class);
                        startActivity(intent);
                        finish();
                        break;
                }
                return true;
            }
        });

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawer,toolbar,R.string.navigation_bar_open,R.string.navigation_bar_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_allbooks,
                R.id.nav_showuser)
                .setDrawerLayout(drawer)
                .build();
    }
}
