package br.alunos.nolascopad2.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import br.alunos.nolascopad2.adapter.LocalBookAdapter;
import br.alunos.nolascopad2.R;
import br.alunos.nolascopad2.activities.LoginScreen;
import br.alunos.nolascopad2.models.Capitulo;
import br.alunos.nolascopad2.models.Livro;
import br.alunos.nolascopad2.database.LivroDAO;
import br.alunos.nolascopad2.models.Pagina;
import br.alunos.nolascopad2.models.User;
import br.alunos.nolascopad2.database.UserDAO;
import br.alunos.nolascopad2.net.LivroWs;
import br.alunos.nolascopad2.net.model.CapituloNet;
import br.alunos.nolascopad2.net.model.LivroNet;

public class ListLocalBooks extends Fragment {
    private UserDAO userDAO;
    private LivroDAO livroDAO;
    private RecyclerView livroRecyclerView;
    private ArrayList<Livro> livros;

    private OnFragmentInteractionListener mListener;

    public ListLocalBooks()
    {
    }

    public static ListLocalBooks newInstance(String param1, String param2) {
        ListLocalBooks fragment = new ListLocalBooks();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_local_books, container, false);
        FloatingActionButton newbookbtn = (FloatingActionButton)  view.findViewById(R.id.addbookfab);
        userDAO = new UserDAO(view.getContext());
        livroDAO = new LivroDAO(view.getContext());


        SharedPreferences preferences = getActivity().getSharedPreferences(LoginScreen.SAVED_USER,0);
        String loggeduser = preferences.getString("LoggedUserEmail", null);

        int userid = userDAO.getUserIDFromDBbyEmail(loggeduser);

        livros = livroDAO.getUserLivro(userid);

        livroRecyclerView = view.findViewById(R.id.livrosRecyclerView);

        //Layout
        RecyclerView.LayoutManager postlayout = new LinearLayoutManager(view.getContext());
        livroRecyclerView.setLayoutManager(postlayout);

        //Adapter
        LocalBookAdapter bookAdapter = new LocalBookAdapter(livros,userid);
        livroRecyclerView.setAdapter(bookAdapter);

        newbookbtn.setOnClickListener(v -> {
            CreateBook fragment = new CreateBook();
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.listcreateframe,fragment);
            transaction.commit();
        });

        return view;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    public class LivroTask extends AsyncTask<String, Void, Void>
    {

        @Override
        protected Void doInBackground(String... strings)
        {
            UserDAO userDAO = new UserDAO(getActivity());
            LivroDAO livroDAO = new LivroDAO(getActivity());
            String email  = strings[0];

            int userid = userDAO.getUserIDFromDBbyEmail(email);

            if (livroDAO.getUserLivro(userid).size() > 0)
                return null;

            List<LivroNet> livroNets = LivroWs.getLivrosUser(email);

            for (LivroNet l : livroNets)
            {
                Livro livro = l.toLivro();
                livro.userid = userid;
                int livroId = livroDAO.saveLivroAndReturnId(livro);
                for (CapituloNet c: l.capitulos) {
                    Capitulo capitulo = new Capitulo();
                    Pagina pagina = new Pagina();

                    capitulo.titulo = c.titulo;
                    capitulo.npages = c.npages;
                    capitulo.lastedit = c.lastedit;
                    capitulo.desc = c.desc;
                    capitulo.livroid = livroId;
                    pagina.text = c.pagina;
                    livroDAO.saveCapitulo(capitulo, pagina);
                }
            }

            return null;
        }
    }
}
