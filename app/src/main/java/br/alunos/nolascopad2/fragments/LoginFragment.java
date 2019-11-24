package br.alunos.nolascopad2.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
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
import br.alunos.nolascopad2.models.User;
import br.alunos.nolascopad2.models.UserDAO;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link LoginFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private TextView emailtext;
    private TextView senhatext;
    private Button logbtn,cadnewbtn;
    private UserDAO userDAO;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public LoginFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LoginFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new LoginFragment();
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
                if(user.email==""||user.senha==""){
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
                    }else Toast.makeText(getActivity().getApplicationContext(),"Usuário e/ou senha incorretos >:(",Toast.LENGTH_LONG).show();
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
