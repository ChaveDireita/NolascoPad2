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
import br.alunos.nolascopad2.database.UserDAO;
import br.alunos.nolascopad2.models.Livro;
import br.alunos.nolascopad2.database.LivroDAO;

public class CreateBook extends Fragment
{
    private LivroDAO livroDAO;

    private OnFragmentInteractionListener mListener;

    public CreateBook()
    {
    }

    public static CreateBook newInstance(String param1, String param2) {
        CreateBook fragment = new CreateBook();
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
        View view = inflater.inflate(R.layout.fragment_create_book, container, false);
        final EditText booktitulo = view.findViewById(R.id.createbooknamefield);
        final EditText bookdesc = view.findViewById(R.id.descricaosetfieldpost);
        final CheckBox box = view.findViewById(R.id.privatecheck);
        ImageView view1 = view.findViewById(R.id.backbtn);
        livroDAO = new LivroDAO(getActivity().getApplicationContext());
        Button creatbtn = view.findViewById(R.id.posbtn);
        creatbtn.setOnClickListener(v -> {
            Livro livro = new Livro();
            livro.desc = bookdesc.getText().toString();
            livro.titulo = booktitulo.getText().toString();
            livro.isprivate= box.isChecked();
            SharedPreferences preferences = getActivity().getSharedPreferences(LoginScreen.SAVED_USER, 0);
            livro.userid = new UserDAO(getActivity()).getUserIDFromDBbyEmail(preferences.getString("LoggedUserEmail", null));
            livro.lastedit = new SimpleDateFormat("dd/MM/yyyy - HH:mm").format(new Date());

            if(livro.titulo.equalsIgnoreCase("")||livro.desc.equalsIgnoreCase("")){
                InformationDialogFragment.newInstance("Erro", "Campos foram deixados em branco", "Ok")
                        .show(getFragmentManager(), "Info");
            }
            else {
                livroDAO.saveLivro(livro);
                Intent intento = new Intent(getContext(), HomeScreen.class);
                getContext().startActivity(intento);
                getActivity().finish();
            }
        });
        view1.setOnClickListener(v -> {
            ListLocalBooks fragment = new ListLocalBooks();
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
}
