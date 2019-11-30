package br.alunos.nolascopad2.net.model;

import br.alunos.nolascopad2.models.Capitulo;
import br.alunos.nolascopad2.models.Pagina;

public class CapituloNet
{
    public String titulo;
    public String desc;
    public int npages;
    public String lastedit;
    public String pagina;

    public static CapituloNet fromCapitulo(Capitulo capitulo, Pagina pagina)
    {
        CapituloNet capituloNet = new CapituloNet();

        capituloNet.titulo = capitulo.titulo;
        capituloNet.pagina = pagina.text;
        capituloNet.desc = capitulo.desc;
        capituloNet.lastedit = capitulo.lastedit;
        capituloNet.npages = capitulo.npages;

        return capituloNet;
    }

}
