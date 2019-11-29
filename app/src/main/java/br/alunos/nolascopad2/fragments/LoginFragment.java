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
import br.alunos.nolascopad2.net.WsConnector;

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
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        emailtext = view.findViewById(R.id.emaillogintext);
        userDAO = new UserDAO(getActivity().getApplicationContext());
        senhatext = view.findViewById(R.id.senhalogintext);
        logbtn = view.findViewById(R.id.logbtn);
        logbtn.setOnClickListener(v -> {
            User user = new User();
            user.email = emailtext.getText().toString();
            user.senha = senhatext.getText().toString();
            if(user.email.equalsIgnoreCase("")||user.senha.equalsIgnoreCase(""))
            {
                InformationDialogFragment.newInstance("Erro", "Usuário ou senha em brancos.", "Ok")
                        .show(getFragmentManager(), "Info");
                return;
            }
            new LoginTask().execute(user.email, user.senha);
        });
        cadnewbtn = view.findViewById(R.id.newcadbtn);

        cadnewbtn.setOnClickListener(v -> {
            CadastroFragment fragment = new CadastroFragment();
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.framelog,fragment);
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

    public class LoginTask extends AsyncTask<String, Void, Void>
    {

        @Override
        protected Void doInBackground(String... strings) {
            String email = strings[0];
            String senha = strings[1];

            if (!WsConnector.checkInternetConection())
            {
                InformationDialogFragment.newInstance("Erro", "Não foi possível conectar-se ao servidor.", "Ok")
                        .show(getFragmentManager(), "Info");
                return null;
            }

            if (!UserWs.auth(email, senha))
            {
                InformationDialogFragment.newInstance("Erro", "Email ou senha incorretos.", "Ok")
                        .show(getFragmentManager(), "Info");
                return null;
            }

            new UserDAO(getActivity()).saveUser(UserWs.get(email));

            SharedPreferences preferences =  getActivity().getSharedPreferences(LoginScreen.SAVED_USER, 0);
            SharedPreferences.Editor editor = preferences.edit();

            editor.putString("LoggedUserEmail", email);
            editor.apply();

            Intent intent = new Intent(getActivity(), HomeScreen.class);
            startActivity(intent);
            getActivity().finish();

            return null;
        }
    }

}
