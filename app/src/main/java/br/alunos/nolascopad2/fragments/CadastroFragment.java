package br.alunos.nolascopad2.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
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
import br.alunos.nolascopad2.models.User;
import br.alunos.nolascopad2.database.UserDAO;
import br.alunos.nolascopad2.net.UserWs;
import br.alunos.nolascopad2.net.WsConnector;

public class CadastroFragment extends Fragment {
    UserDAO userDAO;
    private TextView nometext;
    private TextView emailtext;
    private TextView senhatext;
    private TextView confirmatext;
    private Button cadbtn, lognewbtn;

    private OnFragmentInteractionListener mListener;

    public CadastroFragment()
    {
    }

    public static CadastroFragment newInstance(String param1, String param2) {
        CadastroFragment fragment = new CadastroFragment();
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
        final View view = inflater.inflate(R.layout.fragment_cadastro, container, false);
        userDAO = new UserDAO(getActivity().getApplicationContext());
        nometext = view.findViewById(R.id.nometext);
        emailtext = view.findViewById(R.id.emailtextedit);
        senhatext = view.findViewById(R.id.senhatextedit);
        confirmatext = view.findViewById(R.id.confirmatext);
        cadbtn = view.findViewById(R.id.cadbtn);
        lognewbtn = view.findViewById(R.id.newlogbtn);

        lognewbtn.setOnClickListener(v -> {
            LoginFragment fragment = new LoginFragment();
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.framelog,fragment);
            transaction.commit();
        });

        cadbtn.setOnClickListener(v -> {
            User user = new User();
            user.nome = nometext.getText().toString();
            user.email = emailtext.getText().toString();
            user.senha = senhatext.getText().toString();
            String confirmasenha = confirmatext.getText().toString();

            if(user.nome.trim().equals("")||user.email.trim().equals("")||user.senha.trim().equals(""))
            {
                InformationDialogFragment.newInstance("Erro", "Campos foram deixados em branco", "Ok")
                                         .show(getFragmentManager(), "Info");
                return;
            }

            if(!(user.nome.length()>=5 || user.email.length()>=12 || user.senha.length()>=6))
            {
                InformationDialogFragment.newInstance("Erro", "Dados inválidos (Provavelmente por serem curtos demais)", "Ok")
                                         .show(getFragmentManager(), "Info");
                return;
            }

            if(!(user.email.contains("@")&&(user.email.contains(".com")||user.email.contains(".br")))){
                InformationDialogFragment.newInstance("Erro", "Email inválido inserido", "Ok")
                                         .show(getFragmentManager(), "Info");
                return;
            }

            if(userDAO.searchUserByEmail(user.email))
            {
                InformationDialogFragment.newInstance("Erro", "Usuário já cadastrado", "Ok")
                                         .show(getFragmentManager(), "Info");
                return;
            }

            if(!user.senha.equals(confirmasenha)){
                InformationDialogFragment.newInstance("Erro", "Senhas não batem", "Ok")
                                         .show(getFragmentManager(), "Info");
                return;
            }

            new CadastroTask().execute(user);
//                try {
//
//                }catch (Exception e){
//                    e.printStackTrace();
//                    Toast.makeText(getActivity().getApplicationContext(),"Não foi possivel efetuar o cadastro, veja o que deu errado aí, mas também pode ter sido erro nosso, então desculpa se for :)",Toast.LENGTH_LONG).show();
//                }
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

    public interface OnFragmentInteractionListener
    {
        void onFragmentInteraction(Uri uri);
    }

    public class CadastroTask extends AsyncTask<User, Void, Void>
    {
        @Override
        protected Void doInBackground(User... users)
        {
            User user = users[0];

            if (!WsConnector.checkInternetConection())
            {
                InformationDialogFragment.newInstance("Erro", "Não foi possível conectar-se ao servidor.", "Ok")
                        .show(getFragmentManager(), "Info");
                return null;
            }
            UserWs.post(user);

//            userDAO.saveUser(user);
            SharedPreferences preferences =  getActivity().getSharedPreferences(LoginScreen.SAVED_USER, 0);
            SharedPreferences.Editor editor = preferences.edit();

            editor.putString("LoggedUserEmail", user.email);
            editor.apply();
//            Log.w("Teste","User salvo");
            Intent intent = new Intent(getActivity(), HomeScreen.class);
            startActivity(intent);
            getActivity().finish();

            return null;
        }
    }

}
