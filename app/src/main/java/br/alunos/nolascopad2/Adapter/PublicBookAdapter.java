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
import br.alunos.nolascopad2.activities.CapsScreen;
import br.alunos.nolascopad2.activities.GeneralEditActivity;
import br.alunos.nolascopad2.fragments.ListPublicBooks;
import br.alunos.nolascopad2.models.Livro;
import br.alunos.nolascopad2.models.LivroDAO;
import br.alunos.nolascopad2.models.UserDAO;

public class PublicBookAdapter extends RecyclerView.Adapter<PublicBookAdapter.PostViewHolder> {
    private ArrayList<Livro> livros;
    private ArrayList<Boolean> likeds;
    private Dialog dialog;
    private int currentUserId;
    private int selecteditem;
    private LivroDAO livroDAO;
    private UserDAO userDAO;
    private Context parentcontext;
    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        parentcontext = parent.getContext();
        View postitem = LayoutInflater.from(parent.getContext()).inflate(R.layout.public_book_list,parent,false);
        return new PostViewHolder(postitem);
    }

    public PublicBookAdapter(ArrayList<Livro> livros, int currentUserId) {
        this.livros = livros;
        this.currentUserId = currentUserId;
        Log.w("Teste","Adapter definido");
    }

    @Override
    public void onBindViewHolder(@NonNull final PostViewHolder holder, final int position) {
        Log.w("Teste","chegou no viewholder binder " +position);
        final Livro livro = livros.get((livros.size() - position)-1);
        holder.bookname.setText(livro.titulo);
        holder.description.setText(livro.desc);
        holder.datetime.setText("Última vez modificado: " + livro.lastedit);
        holder.creatorname.setText("Criador: " + userDAO.getUserFromDB(currentUserId).nome);

        holder.entirebook.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                dialog = new Dialog(parentcontext);
                dialog.setContentView(R.layout.bookpopup);
                livroDAO = new LivroDAO(parentcontext);
                ImageView closebtn = dialog.findViewById(R.id.pbclosepop);
                TextView itemnome = dialog.findViewById(R.id.pbbooklistnamepop);
                TextView itemdate = dialog.findViewById(R.id.pbbooklistdatetimepop);
                TextView itemcapcount = dialog.findViewById(R.id.pbcapnumberpop);
                TextView itemdesc = dialog.findViewById(R.id.pbbooklistdescriptionpop);
                TextView itemcreator = dialog.findViewById(R.id.pbbooklistcreatorpop);
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
                dialog.show();
                return true;
            }
        });
        holder.entirebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListPublicBooks fragment = new ListPublicBooks();
                Bundle bundle = new Bundle();
                bundle.putInt("CurrentBook",livro.id);
                fragment.setArguments(bundle);
                FragmentTransaction transaction = ((AppCompatActivity)parentcontext).getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.listpublicframe,fragment);
                transaction.commit();
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
        private TextView creatorname;
        public PostViewHolder(@NonNull final View itemView) {
            super(itemView);
            Log.w("Teste","chegou no viewholder");
            userDAO = new UserDAO(parentcontext);
            bookname = (TextView) itemView.findViewById(R.id.pbbooklistname);
            datetime = (TextView) itemView.findViewById(R.id.pbbooklistdatetime);
            description = (TextView) itemView.findViewById(R.id.pbbooklistdesc);
            settsbtn = (ImageView) itemView.findViewById(R.id.pbsettingsbtn);
            entirebook = (LinearLayout) itemView.findViewById(R.id.pbentirebook);
            creatorname = (TextView) itemView.findViewById(R.id.pbbooklistcreatorpop);
        }
    }
}
