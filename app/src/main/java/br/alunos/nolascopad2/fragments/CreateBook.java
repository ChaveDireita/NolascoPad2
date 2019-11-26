package br.alunos.nolascopad2.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import br.alunos.nolascopad2.activities.HomeScreen;
import br.alunos.nolascopad2.activities.LoginScreen;
import br.alunos.nolascopad2.models.Livro;
import br.alunos.nolascopad2.database.LivroDAO;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CreateBook.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CreateBook#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreateBook extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private LivroDAO livroDAO;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public CreateBook() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CreateBook.
     */
    // TODO: Rename and change types and number of parameters
    public static CreateBook newInstance(String param1, String param2) {
        CreateBook fragment = new CreateBook();
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
        View view = inflater.inflate(R.layout.fragment_create_book, container, false);
        final EditText booktitulo = (EditText) view.findViewById(R.id.createbooknamefield);
        final EditText bookdesc = (EditText) view.findViewById(R.id.descricaosetfieldpost);
        final CheckBox box = (CheckBox) view.findViewById(R.id.privatecheck);
        ImageView view1 = (ImageView) view.findViewById(R.id.backbtn);
        livroDAO = new LivroDAO(getActivity().getApplicationContext());
        Button creatbtn = (Button) view.findViewById(R.id.posbtn);
        creatbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Livro livro = new Livro();
                livro.desc = bookdesc.getText().toString();
                livro.titulo = booktitulo.getText().toString();
                livro.isprivate= box.isChecked();
                SharedPreferences preferences = getActivity().getSharedPreferences(LoginScreen.SAVED_USER, 0);
                livro.userid = preferences.getInt("LoggedUserId",-1);
                livro.lastedit = new SimpleDateFormat("dd/MM/yyyy - HH:mm").format(new Date());

                if(livro.titulo==""||livro.desc==""){
                    Toast.makeText(getActivity().getApplicationContext(),"Campos foram deixados em branco",Toast.LENGTH_LONG).show();
                }
                else {
                    String booktitle = livro.titulo;
                    livroDAO.saveLivro(livro);
                    Intent intento = new Intent(getContext(), HomeScreen.class);
                    getContext().startActivity(intento);
                    getActivity().finish();
                }
            }
        });
        view1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListLocalBooks fragment = new ListLocalBooks();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.listcreateframe,fragment);
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
