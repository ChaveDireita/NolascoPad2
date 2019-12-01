package br.alunos.nolascopad2.adapter;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import br.alunos.nolascopad2.R;
import br.alunos.nolascopad2.fragments.PublicPageShow;
import br.alunos.nolascopad2.database.LivroDAO;
import br.alunos.nolascopad2.net.model.CapituloNet;
import br.alunos.nolascopad2.net.model.LivroNet;

public class PublicCapAdapter extends RecyclerView.Adapter<PublicCapAdapter.PostViewHolder> {
    private List<CapituloNet> capitulos;
    private ArrayList<Boolean> likeds;
    private Dialog dialog;
    private LivroNet currentBook;
    private Context parentcontext;

    public PublicCapAdapter(List<CapituloNet> capitulos, LivroNet currentBook) {
        this.capitulos = capitulos;
        this.currentBook = currentBook;
        Log.w("Teste","Adapter definido");
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        parentcontext = parent.getContext();
        View postitem = LayoutInflater.from(parent.getContext()).inflate(R.layout.caplist_layout,parent,false);
        return new PostViewHolder(postitem);
    }

    @Override
    public void onBindViewHolder(@NonNull final PostViewHolder holder, final int position) {
        Log.w("Teste","chegou no viewholder binder " +position);
        final CapituloNet capitulo = capitulos.get(position);
        holder.capnametext.setText(capitulo.titulo);
        holder.descriptiontext.setText(capitulo.desc);
        holder.datetimetext.setText("Última vez modificado: " + capitulo.lastedit);
        holder.entirecaptext.setOnLongClickListener(v -> {
            dialog = new Dialog(parentcontext);
            dialog.setContentView(R.layout.publiccappopup);
            TextView itemnome = dialog.findViewById(R.id.pbcaplistnamepop);
            TextView itemdate = dialog.findViewById(R.id.pbcaplistdatetimepop);
            TextView itemdesc = dialog.findViewById(R.id.pbcaplistdescriptionpop);
            ImageView closebtn = dialog.findViewById(R.id.pbcapclosepop);
            itemnome.setText(capitulos.get(position).titulo);
            itemdate.setText(capitulos.get(position).lastedit);
            itemdesc.setText(capitulos.get(position).desc);
            closebtn.setOnClickListener(v1 -> dialog.dismiss());
            dialog.show();
            return true;
        });
        holder.entirecaptext.setOnClickListener(v -> {
            PublicPageShow fragment = new PublicPageShow();
            Bundle bundle = new Bundle();
            bundle.putParcelable("Cap",capitulo);
            fragment.setArguments(bundle);
            FragmentTransaction transaction = ((AppCompatActivity)parentcontext).getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.listpublicframe,fragment);
            transaction.commit();
        });
    }

    @Override
    public int getItemCount() {
        return capitulos.size();
    }

    public class PostViewHolder extends RecyclerView.ViewHolder{
        private TextView capnametext;
        private TextView descriptiontext;
        private TextView datetimetext;
        private LinearLayout entirecaptext;
        public PostViewHolder(@NonNull final View itemView) {
            super(itemView);
            Log.w("Teste","chegou no viewholder");
            capnametext = itemView.findViewById(R.id.caplistname);
            datetimetext = itemView.findViewById(R.id.caplistdatetime);
            descriptiontext = itemView.findViewById(R.id.caplistdesc);
            entirecaptext = itemView.findViewById(R.id.entirecap);
        }
    }
}
