package br.alunos.nolascopad2.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

import br.alunos.nolascopad2.R;
import br.alunos.nolascopad2.activities.CapsScreen;
import br.alunos.nolascopad2.models.Capitulo;
import br.alunos.nolascopad2.models.LivroDAO;
import br.alunos.nolascopad2.models.Pagina;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CreateCap.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CreateCap#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreateCap extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private LivroDAO livroDAO;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public CreateCap() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CreateCap.
     */
    // TODO: Rename and change types and number of parameters
    public static CreateCap newInstance(String param1, String param2) {
        CreateCap fragment = new CreateCap();
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
        View view = inflater.inflate(R.layout.fragment_create_cap, container, false);
        final EditText titulo = (EditText) view.findViewById(R.id.captitlefield);
        final EditText desc = (EditText) view.findViewById(R.id.capdescfield);
        ImageView view1 = (ImageView) view.findViewById(R.id.backcapbtn);
        livroDAO = new LivroDAO(getActivity().getApplicationContext());
        Button creatbtn = (Button) view.findViewById(R.id.addcapbtn);
        creatbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Capitulo cap = new Capitulo();
                int currentbook = -1;
                try{
                    Bundle bundle = getArguments();
                    currentbook = bundle.getInt("CurrentBook");
                }catch (Exception e){
                }
                cap.livroid = currentbook;
                cap.desc = desc.getText().toString();
                cap.titulo = titulo.getText().toString();
                cap.lastedit = new SimpleDateFormat("dd/MM/yyyy - HH:mm").format(new Date());

                if(cap.titulo==""){
                    Toast.makeText(getActivity().getApplicationContext(),"Campos foram deixados em branco",Toast.LENGTH_LONG).show();
                }
                else {
                    Pagina page = new Pagina();
                    page.text = "";
                    livroDAO.saveCapitulo(cap,page);
                    //Intent intento = new Intent(getContext(), CapsScreen.class);
                    //getContext().startActivity(intento);
                    getActivity().finish();
                }
            }
        });
        view1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListLocalCaps fragment = new ListLocalCaps();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.listcreatecapsframe,fragment);
                transaction.commit();
            }
        });
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
        /*if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }*/
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
