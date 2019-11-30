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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

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
import br.alunos.nolascopad2.net.WsConnector;
import br.alunos.nolascopad2.net.model.CapituloNet;
import br.alunos.nolascopad2.net.model.LivroNet;

public class ListLocalBooks extends Fragment {
    private UserDAO userDAO;
    private LivroDAO livroDAO;
    private RecyclerView livroRecyclerView;
    private ArrayList<Livro> livros;

    private LocalBookAdapter bookAdapter;

    public String loggeduser;

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
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_list_local_books, container, false);
        FloatingActionButton newbookbtn = view.findViewById(R.id.addbookfab);
        FloatingActionButton cloudbtn = view.findViewById(R.id.cloudfab);

        userDAO = new UserDAO(view.getContext());
        livroDAO = new LivroDAO(view.getContext());


        SharedPreferences preferences = getActivity().getSharedPreferences(LoginScreen.SAVED_USER,0);
        loggeduser = preferences.getString("LoggedUserEmail", null);

        int userid = userDAO.getUserIDFromDBbyEmail(loggeduser);

        livros = new ArrayList<>();

        bookAdapter = new LocalBookAdapter(livros,userid);
        livroRecyclerView = view.findViewById(R.id.livrosRecyclerView);
        livroRecyclerView.setAdapter(bookAdapter);

        //Layout
        RecyclerView.LayoutManager postlayout = new LinearLayoutManager(view.getContext());
        livroRecyclerView.setLayoutManager(postlayout);

        //Adapter

        newbookbtn.setOnClickListener(v -> {
            CreateBook fragment = new CreateBook();
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.listcreateframe,fragment);
            transaction.commit();
        });

        cloudbtn.setOnClickListener(v -> new PutLivroTask().execute());

        return view;
    }

    public void onButtonPressed(Uri uri)
    {
        if (mListener != null)
        {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        new GetLivroTask().execute(loggeduser);
    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
    }

    @Override
    public void onDetach()
    {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener
    {
        void onFragmentInteraction(Uri uri);
    }

    public class GetLivroTask extends AsyncTask<String, Void, List<Livro>>
    {

        @Override
        protected List<Livro> doInBackground(String... strings)
        {
            UserDAO userDAO = new UserDAO(getActivity());
            LivroDAO livroDAO = new LivroDAO(getActivity());
            String email  = strings[0];

            int userid = userDAO.getUserIDFromDBbyEmail(email);

            if (livroDAO.getUserLivro(userid).size() > 0)
            {
                return livroDAO.getUserLivro(userid);
            }

            if (!WsConnector.checkInternetConection())
            {
                InformationDialogFragment.newInstance("Erro", "Não foi possível conectar-se ao servidor.", "Ok")
                        .show(getFragmentManager(), "Info");
                return null;
            }

            List<LivroNet> livroNets = LivroWs.getLivrosUser(email);

            for (LivroNet l : livroNets)
            {
                Livro livro = l.toLivro();
                livro.userid = userid;
                int livroId = livroDAO.saveLivroAndReturnId(livro);
                for (CapituloNet c: l.capitulos)
                {
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
            return livroDAO.getUserLivro(userid);
        }

        @Override
        protected void onPostExecute(List<Livro> livros)
        {
            if (livros == null)
                livros = new ArrayList<>();
            bookAdapter.livros = livros;
            bookAdapter.notifyDataSetChanged();
            super.onPostExecute(livros);
        }
    }

    public class PutLivroTask extends AsyncTask<Void, Void, Boolean>
    {
        @Override
        protected Boolean doInBackground(Void... voids)
        {
            if (!WsConnector.checkInternetConection())
            {
                InformationDialogFragment.newInstance("Erro", "Não foi possível conectar-se ao servidor.", "Ok")
                        .show(getFragmentManager(), "Info");
                return false;
            }

            LivroDAO livroDAO = new LivroDAO(getActivity());
            UserDAO userDAO = new UserDAO(getActivity());

            List<Livro> livros = livroDAO.getUserLivro(userDAO.getUserIDFromDBbyEmail(loggeduser));

            List<LivroNet> livroNets = new ArrayList<>();

            for (Livro l : livros)
            {
                LivroNet livroNet = LivroNet.fromLivro(l);
                livroNet.user = loggeduser;

                for (Capitulo c : livroDAO.getLivroCaps(l.id))
                {
                    CapituloNet capituloNet = CapituloNet.fromCapitulo(c, livroDAO.getCapituloPag(c.id));
                    livroNet.capitulos.add(capituloNet);
                }

                livroNets.add(livroNet);
            }

            if (!LivroWs.putLivros(loggeduser, livroNets).equalsIgnoreCase("Ok"))
            {
                InformationDialogFragment.newInstance("Erro", "Ocorreu um erro ao fazer o upload dos livros.", "Ok")
                        .show(getFragmentManager(), "Info");
                return false;
            }

            return true;
        }

        @Override
        protected void onPostExecute(Boolean deuCerto)
        {
            if (deuCerto)
            {
                Snackbar.make(getView(), R.string.book_put_success, Snackbar.LENGTH_SHORT)
                        .show();
            }
            super.onPostExecute(deuCerto);
        }
    }
}
