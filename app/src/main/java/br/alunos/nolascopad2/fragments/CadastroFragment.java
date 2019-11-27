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

public class CadastroFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    UserDAO userDAO;
    private TextView nometext;
    private TextView emailtext;
    private TextView senhatext;
    private TextView confirmatext;
    private Button cadbtn, lognewbtn;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public CadastroFragment() {
        // Required empty public constructor
    }

    public static CadastroFragment newInstance(String param1, String param2) {
        CadastroFragment fragment = new CadastroFragment();
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
        final View view = inflater.inflate(R.layout.fragment_cadastro, container, false);
        userDAO = new UserDAO(getActivity().getApplicationContext());
        nometext = view.findViewById(R.id.nometext);
        emailtext = view.findViewById(R.id.emailtextedit);
        senhatext = view.findViewById(R.id.senhatextedit);
        confirmatext = view.findViewById(R.id.confirmatext);
        cadbtn = view.findViewById(R.id.cadbtn);
        lognewbtn = view.findViewById(R.id.newlogbtn);

        lognewbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginFragment fragment = new LoginFragment();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.framelog,fragment);
                transaction.commit();
            }
        });

        cadbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = new User();
                user.nome = nometext.getText().toString();
                user.email = emailtext.getText().toString();
                user.senha = senhatext.getText().toString();
                String confirmasenha = confirmatext.getText().toString();
                if(user.nome==""||user.email==""||user.senha==""){
                    Toast.makeText(view.getContext(),"Campos foram deixados em branco",Toast.LENGTH_LONG).show();
                }
                else {
                    if(user.nome.length()>=5||user.email.length()>=12||user.senha.length()>=6){
                        if(user.email.contains("@")&&(user.email.contains(".com")||user.email.contains(".br"))){
                            if(!userDAO.searchUserByEmail(user.email)){
                                if(user.senha.equals(confirmasenha)){
                                    try {
                                        new CadastroTask().execute(user);
                                        userDAO.saveUser(user);
                                        SharedPreferences preferences =  getActivity().getSharedPreferences(LoginScreen.SAVED_USER, 0);
                                        SharedPreferences.Editor editor = preferences.edit();

                                        editor.putInt("LoggedUserId", userDAO.getUserIDFromDBbyEmail(user.email));
                                        editor.commit();
                                        Log.w("Teste","User salvo");
                                        Intent intent = new Intent(getActivity(), HomeScreen.class);
                                        startActivity(intent);
                                        getActivity().finish();
                                    }catch (Exception e){
                                        e.printStackTrace();
                                        Toast.makeText(getActivity().getApplicationContext(),"Não foi possivel efetuar o cadastro, veja o que deu errado aí, mas também pode ter sido erro nosso, então desculpa se for :)",Toast.LENGTH_LONG).show();
                                    }
                                } else Toast.makeText(getActivity().getApplicationContext(),"Senhas não batem",Toast.LENGTH_LONG).show();
                            }else  Toast.makeText(getActivity().getApplicationContext(),"Usuário já cadastrado",Toast.LENGTH_LONG).show();
                        }else Toast.makeText(getActivity().getApplicationContext(),"Email inválido inserido",Toast.LENGTH_LONG).show();
                    }else Toast.makeText(getActivity().getApplicationContext(),"Dados inválidos(Provavelmente por serem curtos demais)",Toast.LENGTH_LONG).show();
                }
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

    public class CadastroTask extends AsyncTask<User, Void, Void>
    {
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(User... users)
        {
            UserWs.post(users[0]);
            return null;
        }
    }

}
