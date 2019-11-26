package br.alunos.nolascopad2.net.model;

import java.util.ArrayList;
import java.util.List;

import br.alunos.nolascopad2.models.Livro;


public class LivroNet
{
    public String titulo;
    public int npages;
    public String desc;
    public String user;
    public String lastedit;
    public int ncaps;
    public boolean isprivate;
    public List<CapituloNet> capitulos = new ArrayList<>();

    public Livro toLivro()
    {
        Livro livro = new Livro();
        livro.titulo = titulo;
        livro.desc = desc;
        livro.lastedit = lastedit;
        livro.isprivate = isprivate;
        livro.npages = npages;
        livro.ncaps = ncaps;
        return livro;
    }

}
