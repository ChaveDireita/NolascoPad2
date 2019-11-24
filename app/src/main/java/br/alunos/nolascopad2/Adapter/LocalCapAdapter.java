package br.alunos.nolascopad2.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import br.alunos.nolascopad2.R;
import br.alunos.nolascopad2.activities.CapsScreen;
import br.alunos.nolascopad2.activities.GeneralEditActivity;
import br.alunos.nolascopad2.activities.PaginaShow;
import br.alunos.nolascopad2.models.Capitulo;
import br.alunos.nolascopad2.models.Livro;
import br.alunos.nolascopad2.models.LivroDAO;

public class LocalCapAdapter extends RecyclerView.Adapter<LocalCapAdapter.PostViewHolder> {
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

    public LocalCapAdapter(ArrayList<Capitulo> capitulos, int currentBookId) {
        this.capitulos = capitulos;
        this.currentBookId = currentBookId;
        Log.w("Teste","Adapter definido");
    }

    @Override
    public void onBindViewHolder(@NonNull final PostViewHolder holder, final int position) {
        Log.w("Teste","chegou no viewholder binder " +position);
        Capitulo capitulo = capitulos.get(position);
            holder.capnametext.setText(capitulo.titulo);
            holder.descriptiontext.setText(capitulo.desc);
            holder.datetimetext.setText("Última vez modificado: " + capitulo.lastedit);
            holder.entirecaptext.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    dialog = new Dialog(parentcontext);
                    dialog.setContentView(R.layout.cappopup);
                    livroDAO = new LivroDAO(parentcontext);
                    TextView itemnome = dialog.findViewById(R.id.caplistnamepop);
                    TextView itemdate = dialog.findViewById(R.id.caplistdatetimepop);
                    TextView itemdesc = dialog.findViewById(R.id.caplistdescriptionpop);
                    ImageView closebtn = dialog.findViewById(R.id.capclosepop);
                    Button edititem = dialog.findViewById(R.id.capeditbuttonpop);
                    Button deleteitem = dialog.findViewById(R.id.capdeletebuttonpop);
                    itemnome.setText(capitulos.get(position).titulo);
                    itemdate.setText(capitulos.get(position).lastedit);
                    itemdesc.setText(capitulos.get(position).desc);
                    closebtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                    edititem.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intento = new Intent(parentcontext, GeneralEditActivity.class);
                            intento.putExtra("WhichOne",2);
                            intento.putExtra("CapId",capitulos.get(position).id);
                            parentcontext.startActivity(intento);
                        }
                    });
                    dialog.show();
                    return true;
                }
            });
            holder.entirecaptext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intento = new Intent(parentcontext, PaginaShow.class);
                    intento.putExtra("CapId",capitulos.get(position).id);
                    parentcontext.startActivity(intento);
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
