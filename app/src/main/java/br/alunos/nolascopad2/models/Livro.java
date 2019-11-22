package br.alunos.nolascopad2.models;

import java.io.Serializable;

public class Livro implements Serializable
{
    public int id;
    public String titulo;
    public int npages;
    public String desc;
    public int userid;
    public String lastedit;


    /*public String getTitulo()
    {
        return titulo;
    }

    public void setTitulo(String titulo)
    {
        this.titulo = titulo;
    }

    public Capitulo getCapitulo (int capitulo) throws ArrayIndexOutOfBoundsException
    {
        return capitulos.get(capitulo);
    }

    public Livro(String titulo)
    {
        this.titulo = titulo;
        this.capitulos = new ArrayList<Capitulo>();
    }

    public int adicionarCapitulo (String titulo)
    {
        capitulos.add(new Capitulo (titulo));
        return capitulos.size() - 1;
    }

    public void removerCapitulo (int capitulo) throws ArrayIndexOutOfBoundsException
    {
        capitulos.remove(capitulo);
    }

    public ArrayList<Capitulo> getCapitulos()
    {
        return capitulos;
    }

    @Override
    public String toString ()
    {
        return this.titulo;
    }*/
}
