package br.alunos.nolascopad2.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class InformationDialogFragment extends DialogFragment
{
    private String title;
    private String message;
    private String button;

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

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState)
    {
        return new AlertDialog.Builder(getActivity()).setTitle(title)
                                                     .setMessage(message)
                                                     .setPositiveButton(button, (v, i) -> {})
                                                     .create();
    }
}
