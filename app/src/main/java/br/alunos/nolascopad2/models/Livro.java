package br.alunos.nolascopad2.models;

import java.io.Serializable;
import java.util.ArrayList;

public class Livro implements Serializable
{
    public int id;
    public String titulo;
    public int npages;
    public String desc;
    public int userid;
    public String lastedit;
    public int ncaps;
    public boolean isprivate;
}
