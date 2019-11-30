package br.alunos.nolascopad2.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
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
import br.alunos.nolascopad2.models.Capitulo;
import br.alunos.nolascopad2.models.Livro;
import br.alunos.nolascopad2.database.LivroDAO;

public class ListLocalCaps extends Fragment
{
    private RecyclerView capRecyclerView;
    private ArrayList<Capitulo> capitulos;
    private int currentbook = -1;
    private LivroDAO livroDAO;
    private FloatingActionButton newcap;
    private OnFragmentInteractionListener mListener;

    public ListLocalCaps()
    {
    }

    public static ListLocalCaps newInstance(String param1, String param2) {
        ListLocalCaps fragment = new ListLocalCaps();

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
        View view = inflater.inflate(R.layout.fragment_list_local_caps, container, false);
        TextView booktitle = view.findViewById(R.id.booktitleshower);
        Bundle bundle = getArguments();
        Livro livro = new Livro();
                try{
                    currentbook = bundle.getInt("CurrentBook");
                    livroDAO = new LivroDAO(getActivity().getApplicationContext());
                    livro = livroDAO.getLivroFromDB(currentbook);
                    capitulos = livroDAO.getLivroCaps(livro.id);
                }catch(Exception e){
                    capitulos = new ArrayList<>();
                }
        if(capitulos.size()==0){
            TextView nulltext = view.findViewById(R.id.nullmessagelocalcaps);
            nulltext.setVisibility(View.VISIBLE);
        }else{
            capRecyclerView = view.findViewById(R.id.capsRecyclerView);
            //Layout
            RecyclerView.LayoutManager postlayout = new LinearLayoutManager(view.getContext());
            capRecyclerView.setLayoutManager(postlayout);
            //Adapter
            LocalCapAdapter capAdapter = new LocalCapAdapter(capitulos,livro.id);
            capRecyclerView.setAdapter(capAdapter);
        }
        booktitle.setText(livro.titulo);
        newcap = view.findViewById(R.id.addcapfab);
        newcap.setOnClickListener(v -> {
            CreateCap fragment = new CreateCap();
            Bundle bundle1 = new Bundle();
            bundle1.putInt("CurrentBook",currentbook);
            fragment.setArguments(bundle1);
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.listcreatecapsframe,fragment);
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
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
