package br.alunos.nolascopad2.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import br.alunos.nolascopad2.adapter.PublicBookAdapter;
import br.alunos.nolascopad2.R;
import br.alunos.nolascopad2.activities.LoginScreen;
import br.alunos.nolascopad2.database.UserDAO;
import br.alunos.nolascopad2.models.Livro;
import br.alunos.nolascopad2.database.LivroDAO;
import br.alunos.nolascopad2.net.LivroWs;
import br.alunos.nolascopad2.net.model.LivroNet;

public class ListPublicBooks extends Fragment {

    private OnFragmentInteractionListener mListener;

    public ListPublicBooks()
    {
    }

    public static ListPublicBooks newInstance(String param1, String param2) {
        ListPublicBooks fragment = new ListPublicBooks();
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
        View view = inflater.inflate(R.layout.fragment_list_public_books, container, false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        SharedPreferences preferences = getActivity().getSharedPreferences(LoginScreen.SAVED_USER,0);
        String loggeduser = preferences.getString("LoggedUserEmail",null);

        new GetLivrosNotUserTask().execute(loggeduser);
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

    public class GetLivrosNotUserTask extends AsyncTask<String, Void, List<LivroNet>>
    {
        public String user;

        @Override
        protected List<LivroNet> doInBackground(String... strings)
        {
            user = strings[0];
            return LivroWs.getLivrosNotUser(user);
        }

        @Override
        protected void onPostExecute(List<LivroNet> livroNets)
        {
            super.onPostExecute(livroNets);

            RecyclerView livroRecyclerView = getActivity().findViewById(R.id.pblivrosRecyclerView);
            //Layout
            RecyclerView.LayoutManager postlayout = new LinearLayoutManager(getActivity());
            livroRecyclerView.setLayoutManager(postlayout);
            //Adapter
            PublicBookAdapter bookAdapter = new PublicBookAdapter(livroNets, user);
            livroRecyclerView.setAdapter(bookAdapter);

        }
    }
}
