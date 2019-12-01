package br.alunos.nolascopad2.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import br.alunos.nolascopad2.R;
import br.alunos.nolascopad2.database.LivroDAO;
import br.alunos.nolascopad2.models.Pagina;
import br.alunos.nolascopad2.net.model.CapituloNet;

public class PublicPageShow extends Fragment {

    private OnFragmentInteractionListener mListener;

    public PublicPageShow() {
        // Required empty public constructor
    }

    public static PublicPageShow newInstance(String param1, String param2) {
        PublicPageShow fragment = new PublicPageShow();
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
        String page;
        TextView content;
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_public_page_show, container, false);
        CapituloNet currentcap = getArguments().getParcelable("Cap");

        page = currentcap.pagina;
        TextView capname = view.findViewById(R.id.pbcapname);
        content = view.findViewById(R.id.pbpagecontent);
        capname.setText(currentcap.titulo);
        content.setText(page);
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
        void onFragmentInteraction(Uri uri);
    }
}
