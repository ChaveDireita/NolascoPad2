package br.alunos.nolascopad2.net.model;

import android.os.Parcel;
import android.os.Parcelable;

import br.alunos.nolascopad2.models.Capitulo;
import br.alunos.nolascopad2.models.Pagina;

public class CapituloNet implements Parcelable
{
    public String titulo;
    public String desc;
    public int npages;
    public String lastedit;
    public String pagina;

    public static final CapituloNetCreator CREATOR = new CapituloNetCreator();

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(titulo);
        parcel.writeString(desc);
        parcel.writeInt(npages);
        parcel.writeString(lastedit);
        parcel.writeString(pagina);
    }

    public static class CapituloNetCreator implements Parcelable.Creator<CapituloNet>
    {

        @Override
        public CapituloNet createFromParcel(Parcel parcel) {
            CapituloNet net = new CapituloNet();

            net.titulo = parcel.readString();
            net.desc = parcel.readString();
            net.npages = parcel.readInt();
            net.lastedit = parcel.readString();
            net.pagina = parcel.readString();

            return null;
        }

        @Override
        public CapituloNet[] newArray(int i) {
            return new CapituloNet[i];
        }
    }
}
