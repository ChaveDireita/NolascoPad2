package br.alunos.nolascopad2.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import br.alunos.nolascopad2.R;
import br.alunos.nolascopad2.activities.HomeScreen;
import br.alunos.nolascopad2.activities.LoginScreen;
import br.alunos.nolascopad2.database.LivroDAO;
import br.alunos.nolascopad2.models.Livro;
import br.alunos.nolascopad2.models.User;
import br.alunos.nolascopad2.database.UserDAO;
import br.alunos.nolascopad2.net.UserWs;

public class LoginFragment extends Fragment {
    private TextView emailtext;
    private TextView senhatext;
    private Button logbtn,
                   cadnewbtn;
    private UserDAO userDAO;

    private OnFragmentInteractionListener mListener;

    public LoginFragment() {
        // Required empty public constructor
    }

    public static LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new LoginFragment();
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        emailtext = view.findViewById(R.id.emaillogintext);
        userDAO = new UserDAO(getActivity().getApplicationContext());
        senhatext = view.findViewById(R.id.senhalogintext);
        logbtn = view.findViewById(R.id.logbtn);
        logbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = new User();
                user.email = emailtext.getText().toString();
                user.senha = senhatext.getText().toString();
                if(user.email.equalsIgnoreCase("")||user.senha.equalsIgnoreCase("")){
                    Toast.makeText(getActivity().getApplicationContext(),"Campos foram deixados em branco",Toast.LENGTH_LONG).show();
                }else{
                    if(userDAO.searchUserByEmailAndPassword(user.email,user.senha)){
                        //int userdaoid = userDAO.getUserIDFromDBbyEmail(user.email);
                        SharedPreferences preferences = getActivity().getSharedPreferences(LoginScreen.SAVED_USER, 0);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putInt("LoggedUserId", userDAO.getUserIDFromDBbyEmail(user.email));
                        editor.commit();
                        Intent intent = new Intent(getActivity(), HomeScreen.class);
                        startActivity(intent);
                        getActivity().finish();
                    } else Toast.makeText(getActivity().getApplicationContext(),"Usuário e/ou senha incorretos >:(",Toast.LENGTH_LONG).show();
                }
            }
        });
        cadnewbtn = view.findViewById(R.id.newcadbtn);

        cadnewbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CadastroFragment fragment = new CadastroFragment();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.framelog,fragment);
                transaction.commit();
            }
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

    public interface OnFragmentInteractionListener
    {
        void onFragmentInteraction(Uri uri);
    }

    public class LivroTask extends AsyncTask<Integer, Void, Void>
    {

        @Override
        protected Void doInBackground(Integer... ints)
        {
            LivroDAO dao = new LivroDAO(getActivity());
            int count = dao.getUserLivro(ints[0]).size();

            if (count == 0)
            {

            }

            return null;
        }
    }

}
