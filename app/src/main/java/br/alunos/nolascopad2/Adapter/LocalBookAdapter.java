package br.alunos.nolascopad2.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
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
import br.alunos.nolascopad2.models.Livro;
import br.alunos.nolascopad2.models.LivroDAO;

public class LocalBookAdapter extends RecyclerView.Adapter<LocalBookAdapter.PostViewHolder> {
    private ArrayList<Livro> livros;
    private ArrayList<Boolean> likeds;
    private Dialog dialog;
    private int currentUserId;
    private int selecteditem;
    private LivroDAO livroDAO;
    private Context parentcontext;
    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        parentcontext = parent.getContext();
        View postitem = LayoutInflater.from(parent.getContext()).inflate(R.layout.booklist_layout,parent,false);
        return new PostViewHolder(postitem);
    }

    public LocalBookAdapter(ArrayList<Livro> livros, int currentUserId) {
        this.livros = livros;
        this.currentUserId = currentUserId;
        Log.w("Teste","Adapter definido");
    }

    @Override
    public void onBindViewHolder(@NonNull final PostViewHolder holder, final int position) {
        Log.w("Teste","chegou no viewholder binder " +position);
        Livro livro = livros.get((livros.size() - position)-1);
        holder.bookname.setText(livro.titulo);
        holder.description.setText(livro.desc);
        holder.datetime.setText("Última vez modificado: " + livro.lastedit);
        holder.entirebook.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                dialog = new Dialog(parentcontext);
                dialog.setContentView(R.layout.bookpopup);
                livroDAO = new LivroDAO(parentcontext);
                ImageView closebtn = dialog.findViewById(R.id.closepop);
                TextView itemnome = dialog.findViewById(R.id.booklistnamepop);
                TextView itemdate = dialog.findViewById(R.id.booklistdatetimepop);
                TextView itemcapcount = dialog.findViewById(R.id.capnumberpop);
                TextView itemdesc = dialog.findViewById(R.id.booklistdescriptionpop);
                Button edititem = dialog.findViewById(R.id.editbuttonpop);
                Button deleteitem = dialog.findViewById(R.id.deletebuttonpop);
                itemnome.setText(livros.get((livros.size() - position)-1).titulo);
                itemdate.setText("Última vez modificado: " + livros.get((livros.size() - position)-1).lastedit);
                itemdesc.setText(livros.get((livros.size() - position)-1).desc);
                itemcapcount.setText(String.valueOf(livroDAO.getNCaps(livros.get((livros.size() - position)-1).id)));

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
                        intento.putExtra("WhichOne",1);
                        intento.putExtra("BookId",livros.get(selecteditem).id);
                        parentcontext.startActivity(intento);
                    }
                });
                dialog.show();
                return true;
            }
        });
        holder.entirebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intento = new Intent(parentcontext, CapsScreen.class);
                intento.putExtra("BookId",livros.get((livros.size() - position)-1).id);
                parentcontext.startActivity(intento);
            }
        });
    }

    @Override
    public int getItemCount() {
        return livros.size();
    }

    public class PostViewHolder extends RecyclerView.ViewHolder{
        private TextView bookname;
        private TextView description;
        private TextView datetime;
        private ImageView settsbtn;
        private LinearLayout entirebook;
        public PostViewHolder(@NonNull final View itemView) {
            super(itemView);
            Log.w("Teste","chegou no viewholder");
            bookname = (TextView) itemView.findViewById(R.id.booklistname);
            datetime = (TextView) itemView.findViewById(R.id.booklistdatetime);
            description = (TextView) itemView.findViewById(R.id.booklistdesc);
            settsbtn = (ImageView) itemView.findViewById(R.id.settingsbtn);
            entirebook = (LinearLayout) itemView.findViewById(R.id.entirebook);
        }
    }
}
