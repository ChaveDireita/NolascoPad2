package br.alunos.nolascopad2.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import br.alunos.nolascopad2.R;
import br.alunos.nolascopad2.activities.GeneralEditActivity;
import br.alunos.nolascopad2.activities.PaginaShow;
import br.alunos.nolascopad2.fragments.ListPublicBooks;
import br.alunos.nolascopad2.fragments.PublicPageShow;
import br.alunos.nolascopad2.models.Capitulo;
import br.alunos.nolascopad2.models.LivroDAO;

public class PublicCapAdapter extends RecyclerView.Adapter<PublicCapAdapter.PostViewHolder> {
    private ArrayList<Capitulo> capitulos;
    private ArrayList<Boolean> likeds;
    private Dialog dialog;
    private int currentBookId;
    private LivroDAO livroDAO;
    private Context parentcontext;
    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        parentcontext = parent.getContext();
        View postitem = LayoutInflater.from(parent.getContext()).inflate(R.layout.caplist_layout,parent,false);
        return new PostViewHolder(postitem);
    }

    public PublicCapAdapter(ArrayList<Capitulo> capitulos, int currentBookId) {
        this.capitulos = capitulos;
        this.currentBookId = currentBookId;
        Log.w("Teste","Adapter definido");
    }

    @Override
    public void onBindViewHolder(@NonNull final PostViewHolder holder, final int position) {
        Log.w("Teste","chegou no viewholder binder " +position);
        final Capitulo capitulo = capitulos.get(position);
        holder.capnametext.setText(capitulo.titulo);
        holder.descriptiontext.setText(capitulo.desc);
        holder.datetimetext.setText("Última vez modificado: " + capitulo.lastedit);
        holder.entirecaptext.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                dialog = new Dialog(parentcontext);
                dialog.setContentView(R.layout.publiccappopup);
                livroDAO = new LivroDAO(parentcontext);
                TextView itemnome = dialog.findViewById(R.id.pbcaplistnamepop);
                TextView itemdate = dialog.findViewById(R.id.pbcaplistdatetimepop);
                TextView itemdesc = dialog.findViewById(R.id.pbcaplistdescriptionpop);
                ImageView closebtn = dialog.findViewById(R.id.pbcapclosepop);
                itemnome.setText(capitulos.get(position).titulo);
                itemdate.setText(capitulos.get(position).lastedit);
                itemdesc.setText(capitulos.get(position).desc);
                closebtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
                return true;
            }
        });
        holder.entirecaptext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PublicPageShow fragment = new PublicPageShow();
                Bundle bundle = new Bundle();
                bundle.putInt("CapId",capitulo.id);
                fragment.setArguments(bundle);
                FragmentTransaction transaction = ((AppCompatActivity)parentcontext).getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.listpublicframe,fragment);
                transaction.commit();
            }
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
            capnametext = (TextView) itemView.findViewById(R.id.caplistname);
            datetimetext = (TextView) itemView.findViewById(R.id.caplistdatetime);
            descriptiontext = (TextView) itemView.findViewById(R.id.caplistdesc);
            entirecaptext = (LinearLayout) itemView.findViewById(R.id.entirecap);
        }
    }
}
