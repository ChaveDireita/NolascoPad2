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
import br.alunos.nolascopad2.models.Capitulo;
import br.alunos.nolascopad2.models.Livro;
import br.alunos.nolascopad2.database.LivroDAO;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PublicCaps.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PublicCaps#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PublicCaps extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RecyclerView capRecyclerView;
    private ArrayList<Capitulo> capitulos;
    private int currentbook = -1;
    private LivroDAO livroDAO;
    private FloatingActionButton newcap;

    private OnFragmentInteractionListener mListener;

    public PublicCaps() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PublicCaps.
     */
    // TODO: Rename and change types and number of parameters
    public static PublicCaps newInstance(String param1, String param2) {
        PublicCaps fragment = new PublicCaps();
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
        View view = inflater.inflate(R.layout.fragment_public_caps, container, false);
        TextView booktitle = view.findViewById(R.id.pbbooktitleshower);
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
            TextView nulltext = view.findViewById(R.id.pbnullmessagelocalcaps);
            nulltext.setVisibility(View.VISIBLE);
        }else{
            capRecyclerView = (RecyclerView) view.findViewById(R.id.pbcapsRecyclerView);
            //Layout
            RecyclerView.LayoutManager postlayout = new LinearLayoutManager(view.getContext());
            capRecyclerView.setLayoutManager(postlayout);
            //Adapter
            LocalCapAdapter capAdapter = new LocalCapAdapter(capitulos,livro.id);
            capRecyclerView.setAdapter(capAdapter);
        }
        booktitle.setText(livro.titulo);
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
