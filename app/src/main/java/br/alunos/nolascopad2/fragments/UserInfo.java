package br.alunos.nolascopad2.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import br.alunos.nolascopad2.R;
import br.alunos.nolascopad2.activities.GeneralEditActivity;
import br.alunos.nolascopad2.activities.LoginScreen;
import br.alunos.nolascopad2.activities.SplashScreen;
import br.alunos.nolascopad2.database.LivroDAO;
import br.alunos.nolascopad2.models.User;
import br.alunos.nolascopad2.database.UserDAO;

public class UserInfo extends Fragment {

    private OnFragmentInteractionListener mListener;

    public UserInfo() {
    }

    public static UserInfo newInstance(String param1, String param2) {
        UserInfo fragment = new UserInfo();
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
        UserDAO userDAO = new UserDAO(getActivity().getApplicationContext());
        LivroDAO livroDAO = new LivroDAO(getActivity().getApplicationContext());
        View view = inflater.inflate(R.layout.fragment_user_info, container, false);
        TextView usernamefield = view.findViewById(R.id.username);
        TextView useremailfield = view.findViewById(R.id.emailtextedit);
        TextView totalbooksfield = view.findViewById(R.id.totalbooks);
        TextView totalcapsfield = view.findViewById(R.id.totalcaps);
        Button exitbtn = view.findViewById(R.id.exitbtn);
        Button editbtn= view.findViewById(R.id.edituserbtn);
        SharedPreferences preferences =  getActivity().getSharedPreferences(LoginScreen.SAVED_USER, 0);
        final int currentuser = userDAO.getUserIDFromDBbyEmail(preferences.getString("LoggedUserEmail",null));
        User loggeduser = userDAO.getUserFromDB(currentuser);
        usernamefield.setText(loggeduser.nome);
        useremailfield.setText(loggeduser.email);
        totalbooksfield.setText(livroDAO.getNLivrosperuser(loggeduser.id)+" Livros criados até o momento");
        totalcapsfield.setText(""+livroDAO.getNCapsperuser(loggeduser.id)+" Capítulos criados até o momento");
        editbtn.setOnClickListener(v -> {
            Intent intento = new Intent(getActivity(), GeneralEditActivity.class);
            intento.putExtra("WhichOne",3);
            intento.putExtra("UserId",currentuser);
            getActivity().startActivity(intento);
        });
        exitbtn.setOnClickListener(v -> {
            Intent intento = new Intent(getActivity(), SplashScreen.class);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("LoggedUserEmail", null);
            editor.apply();
            getActivity().startActivity(intento);
            getActivity().finish();
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
