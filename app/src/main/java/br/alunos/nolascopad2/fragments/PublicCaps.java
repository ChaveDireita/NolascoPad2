package br.alunos.nolascopad2.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import br.alunos.nolascopad2.adapter.LocalCapAdapter;
import br.alunos.nolascopad2.R;
import br.alunos.nolascopad2.adapter.PublicCapAdapter;
import br.alunos.nolascopad2.models.Capitulo;
import br.alunos.nolascopad2.models.Livro;
import br.alunos.nolascopad2.database.LivroDAO;
import br.alunos.nolascopad2.net.model.LivroNet;

public class PublicCaps extends Fragment {
    private RecyclerView capRecyclerView;

    private OnFragmentInteractionListener mListener;

    public PublicCaps() {
    }

    public static PublicCaps newInstance(String param1, String param2) {
        PublicCaps fragment = new PublicCaps();
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

        View view = inflater.inflate(R.layout.fragment_public_caps, container, false);
        TextView booktitle = view.findViewById(R.id.pbbooktitleshower);
        Bundle bundle = getArguments();
        LivroNet livro = bundle.getParcelable("CurrentBook");

        if(livro.capitulos.size()==0){
            TextView nulltext = view.findViewById(R.id.pbnullmessagelocalcaps);
            nulltext.setVisibility(View.VISIBLE);
        }else{
            capRecyclerView = view.findViewById(R.id.pbcapsRecyclerView);
            //Layout
            RecyclerView.LayoutManager postlayout = new LinearLayoutManager(view.getContext());
            capRecyclerView.setLayoutManager(postlayout);
            //Adapter
            PublicCapAdapter capAdapter = new PublicCapAdapter(livro.capitulos,livro);
            capRecyclerView.setAdapter(capAdapter);
        }
        booktitle.setText(livro.titulo);
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
}
