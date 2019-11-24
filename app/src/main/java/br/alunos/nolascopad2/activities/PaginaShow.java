package br.alunos.nolascopad2.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Date;

import br.alunos.nolascopad2.R;
import br.alunos.nolascopad2.models.Capitulo;
import br.alunos.nolascopad2.models.Livro;
import br.alunos.nolascopad2.models.LivroDAO;
import br.alunos.nolascopad2.models.Pagina;

public class PaginaShow extends AppCompatActivity {

    private LivroDAO livroDAO;
    private Pagina page,temppage;
    private EditText content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagina_show);

        Intent intent = getIntent();
        int currentcap = intent.getIntExtra("CapId",-1);
        livroDAO = new LivroDAO(getApplicationContext());
        page = livroDAO.getCapituloPag(currentcap);
        temppage = page;
        TextView capname = (TextView) findViewById(R.id.capname);
        content = (EditText) findViewById(R.id.pagecontent);
        capname.setText(livroDAO.getCapituloFromDB(currentcap).titulo);
        content.setText(page.text);
        capname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                temppage.text = content.getText().toString();
                livroDAO.editPage(temppage);
                Capitulo tempcap = livroDAO.getCapituloFromDB(temppage.capid);
                tempcap.lastedit = new SimpleDateFormat("dd/MM/yyyy - HH:mm").format(new Date());
                livroDAO.editCap(tempcap);
                Livro templivro = livroDAO.getLivroFromDB(tempcap.livroid);
                templivro.lastedit = new SimpleDateFormat("dd/MM/yyyy - HH:mm").format(new Date());
                livroDAO.editLivro(templivro);
                finish();
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        temppage.text = content.getText().toString();
        livroDAO.editPage(temppage);
        Capitulo tempcap = livroDAO.getCapituloFromDB(temppage.capid);
        tempcap.lastedit = new SimpleDateFormat("dd/MM/yyyy - HH:mm").format(new Date());
        livroDAO.editCap(tempcap);
        Livro templivro = livroDAO.getLivroFromDB(tempcap.livroid);
        templivro.lastedit = new SimpleDateFormat("dd/MM/yyyy - HH:mm").format(new Date());
        livroDAO.editLivro(templivro);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        temppage.text = content.getText().toString();
        livroDAO.editPage(temppage);
        Capitulo tempcap = livroDAO.getCapituloFromDB(temppage.capid);
        tempcap.lastedit = new SimpleDateFormat("dd/MM/yyyy - HH:mm").format(new Date());
        livroDAO.editCap(tempcap);
        Livro templivro = livroDAO.getLivroFromDB(tempcap.livroid);
        templivro.lastedit = new SimpleDateFormat("dd/MM/yyyy - HH:mm").format(new Date());
        livroDAO.editLivro(templivro);
    }
}
