package br.alunos.nolascopad2.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

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
import br.alunos.nolascopad2.models.Livro;
import br.alunos.nolascopad2.database.LivroDAO;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link EditLivro.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link EditLivro#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditLivro extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private LivroDAO livroDAO;

    private OnFragmentInteractionListener mListener;

    public EditLivro() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EditLivro.
     */
    // TODO: Rename and change types and number of parameters
    public static EditLivro newInstance(String param1, String param2) {
        EditLivro fragment = new EditLivro();
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
        final EditText titletext;
        final EditText desctext;
        final ImageView backbtn;
        final CheckBox privatecheck;
        Button editlivro;
        View view = inflater.inflate(R.layout.fragment_edit_livro, container, false);
        livroDAO = new LivroDAO(getActivity().getApplicationContext());
        final Livro livro = livroDAO.getLivroFromDB(getArguments().getInt("CurrentBook"));
        titletext = view.findViewById(R.id.editbooknamefield);
        titletext.setText(livro.titulo);
        desctext = view.findViewById(R.id.editdescricaosetfieldpost);
        desctext.setText(livro.desc);
        privatecheck = view.findViewById(R.id.editprivatecheck);
        backbtn = view.findViewById(R.id.editbackbookbtn);
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intento = new Intent(getContext(), HomeScreen.class);
                getContext().startActivity(intento);
                getActivity().finish();
            }
        });
        editlivro = view.findViewById(R.id.editposbtn);
        editlivro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Livro templiv = livro;
                templiv.desc = desctext.getText().toString();
                templiv.titulo = titletext.getText().toString();
                templiv.lastedit = new SimpleDateFormat("dd/MM/yyyy - HH:mm").format(new Date());
                templiv.isprivate = privatecheck.isChecked();

                if(templiv.titulo==""||templiv.desc==""){
                    Toast.makeText(getActivity().getApplicationContext(),"Campos foram deixados em branco",Toast.LENGTH_LONG).show();
                }
                else {
                    livroDAO.editLivro(templiv);
                    Intent intento = new Intent(getContext(), HomeScreen.class);
                    getContext().startActivity(intento);
                    getActivity().finish();
                }
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
