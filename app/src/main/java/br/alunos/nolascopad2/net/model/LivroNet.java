package br.alunos.nolascopad2.net.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

import br.alunos.nolascopad2.models.Livro;

public class LivroNet implements Parcelable
{
    public String titulo;
    public int npages;
    public String desc;
    public String user;
    public String lastedit;
    public int ncaps;
    public boolean isprivate;
    public List<CapituloNet> capitulos = new ArrayList<>();

    public final static LivroNetCreator CREATOR = new LivroNetCreator();


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

    public static LivroNet fromLivro (Livro livro)
    {
        LivroNet livroNet = new LivroNet();

        livroNet.titulo = livro.titulo;
        livroNet.desc = livro.desc;
        livroNet.isprivate = livro.isprivate;
        livroNet.ncaps = livro.ncaps;
        livroNet.lastedit = livro.lastedit;
        livroNet.npages = livro.npages;

        return livroNet;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i)
    {
        parcel.writeString(titulo);
        parcel.writeInt(npages);
        parcel.writeString(desc);
        parcel.writeString(user);
        parcel.writeString(lastedit);
        parcel.writeInt(ncaps);
        parcel.writeInt((isprivate) ? 1 : 0);
    }

    public static class LivroNetCreator implements Parcelable.Creator<LivroNet>
    {

        @Override
        public LivroNet createFromParcel(Parcel parcel)
        {
            LivroNet net = new LivroNet();

            net.titulo = parcel.readString();
            net.npages = parcel.readInt();
            net.desc = parcel.readString();
            net.user = parcel.readString();
            net.lastedit = parcel.readString();
            net.ncaps = parcel.readInt();
            net.isprivate = parcel.readInt() == 1;


            return null;
        }

        @Override
        public LivroNet[] newArray(int i) {
            return new LivroNet[i];
        }
    }
}
