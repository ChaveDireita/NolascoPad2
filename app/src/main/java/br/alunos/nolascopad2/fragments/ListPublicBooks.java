package br.alunos.nolascopad2.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import br.alunos.nolascopad2.adapter.PublicBookAdapter;
import br.alunos.nolascopad2.R;
import br.alunos.nolascopad2.activities.LoginScreen;
import br.alunos.nolascopad2.database.UserDAO;
import br.alunos.nolascopad2.models.Livro;
import br.alunos.nolascopad2.database.LivroDAO;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ListPublicBooks.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ListPublicBooks#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListPublicBooks extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public ListPublicBooks()
    {
    }

    public static ListPublicBooks newInstance(String param1, String param2) {
        ListPublicBooks fragment = new ListPublicBooks();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list_public_books, container, false);
        LivroDAO livroDAO = new LivroDAO(view.getContext());
        SharedPreferences preferences = getActivity().getSharedPreferences(LoginScreen.SAVED_USER,0);
        int loggeduser = new UserDAO(getActivity()).getUserIDFromDBbyEmail(preferences.getString("LoggedUserEmail",null));
        ArrayList<Livro> livros = livroDAO.getAllPublicBooks();
        Log.d("Teste",""+livros.size());
        RecyclerView livroRecyclerView = (RecyclerView) view.findViewById(R.id.pblivrosRecyclerView);
        //Layout
        RecyclerView.LayoutManager postlayout = new LinearLayoutManager(view.getContext());
        livroRecyclerView.setLayoutManager(postlayout);
        //Adapter
        PublicBookAdapter bookAdapter = new PublicBookAdapter(livros,loggeduser);
        livroRecyclerView.setAdapter(bookAdapter);
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
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
