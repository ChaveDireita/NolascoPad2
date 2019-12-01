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
import br.alunos.nolascopad2.fragments.ListPublicBooks;
import br.alunos.nolascopad2.fragments.PublicCaps;
import br.alunos.nolascopad2.models.Livro;
import br.alunos.nolascopad2.database.LivroDAO;
import br.alunos.nolascopad2.database.UserDAO;
import br.alunos.nolascopad2.net.model.LivroNet;

public class PublicBookAdapter extends RecyclerView.Adapter<PublicBookAdapter.PostViewHolder> {
    private List<LivroNet> livros;
    private ArrayList<Boolean> likeds;
    private Dialog dialog;
    private String currentUser;
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

    public PublicBookAdapter(List<LivroNet> livros, String currentUser) {
        this.livros = livros;
        this.currentUser = currentUser;
        Log.w("Teste","Adapter definido");
    }

    @Override
    public void onBindViewHolder(@NonNull final PostViewHolder holder, final int position) {
        Log.w("Teste","chegou no viewholder binder " +position);
        final LivroNet livro = livros.get((livros.size() - position)-1);
        holder.bookname.setText(livro.titulo);
        holder.description.setText(livro.desc);
        holder.datetime.setText("Última vez modificado: " + livro.lastedit);
        holder.creatorname.setText("Criador: " + livro.user);

        holder.entirebook.setOnLongClickListener(v -> {
            dialog = new Dialog(parentcontext);
            dialog.setContentView(R.layout.bookpopup);
            livroDAO = new LivroDAO(parentcontext);
            ImageView closebtn = dialog.findViewById(R.id.pbclosepop);
            TextView itemnome = dialog.findViewById(R.id.pbbooklistnamepop);
            TextView itemdate = dialog.findViewById(R.id.pbbooklistdatetimepop);
            TextView itemcapcount = dialog.findViewById(R.id.pbcapnumberpop);
            TextView itemdesc = dialog.findViewById(R.id.pbbooklistdescriptionpop);
            itemnome.setText(livros.get((livros.size() - position)-1).titulo);
            itemdate.setText("Última vez modificado: " + livros.get((livros.size() - position)-1).lastedit);
            itemdesc.setText(livros.get((livros.size() - position)-1).desc);
            itemcapcount.setText(String.valueOf(livros.get((livros.size() - position)-1).capitulos.size()));

            closebtn.setOnClickListener(v1 -> dialog.dismiss());
            dialog.show();
            return true;
        });
        holder.entirebook.setOnClickListener(v -> {
            PublicCaps fragment = new PublicCaps();
            Bundle bundle = new Bundle();
            bundle.putParcelable("CurrentBook", livro);
            fragment.setArguments(bundle);
            FragmentTransaction transaction = ((AppCompatActivity)parentcontext).getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.listpublicframe,fragment);
            transaction.commit();
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
            bookname = itemView.findViewById(R.id.pbbooklistname);
            datetime = itemView.findViewById(R.id.pbbooklistdatetime);
            description = itemView.findViewById(R.id.pbbooklistdesc);
            settsbtn = itemView.findViewById(R.id.pbsettingsbtn);
            entirebook = itemView.findViewById(R.id.pbentirebook);
            creatorname = itemView.findViewById(R.id.pbbooklistcreator);
        }
    }
}
