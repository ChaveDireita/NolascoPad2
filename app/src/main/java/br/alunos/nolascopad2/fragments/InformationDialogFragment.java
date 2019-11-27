package br.alunos.nolascopad2.fragments;

import androidx.fragment.app.DialogFragment;

public class InformationDialogFragment extends DialogFragment
{
    public String title;
    public String message;
    public String button;

    public InformationDialogFragment()
    {
    }

    public static InformationDialogFragment newInstance(String title, String message, String button)
    {
        InformationDialogFragment fragment = new InformationDialogFragment();

        fragment.title = title;
        fragment.message = message;
        fragment.button = button;
        
        return fragment;
    }


}
