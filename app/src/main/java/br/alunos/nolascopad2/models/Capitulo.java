package br.alunos.nolascopad2.models;

import java.io.Serializable;
import java.util.ArrayList;

public class Capitulo implements Serializable
{
    public int id;
    public String titulo;
    public String desc;
    public int livroid;
    public int npages;
    public String lastedit;
    public ArrayList<Pagina> paginas;
}
