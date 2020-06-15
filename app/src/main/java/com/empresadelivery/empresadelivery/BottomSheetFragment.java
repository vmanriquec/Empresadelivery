package com.empresadelivery.empresadelivery;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class BottomSheetFragment extends BottomSheetDialogFragment {

    public static BottomSheetFragment newInstance() {
        BottomSheetFragment fragment = new BottomSheetFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setupDialog(Dialog dialog, int style) {
        View contentView = View.inflate(getContext(), R.layout.fragment_bottomsheet, null);
        dialog.setContentView(contentView);
        ((View) contentView.getParent()).setBackgroundColor(getResources().getColor(android.R.color.transparent));

        TextView edit_text =(TextView) contentView.findViewById(R.id.cabecerashet);
        TextView nombreusuario =(TextView) contentView.findViewById(R.id.nombreusuarioshet);
        ImageView imagencito =(ImageView) contentView.findViewById(R.id.imageView4);

        Bundle bundle = getArguments();
        String imagen = bundle.getString("imagen");

        String test = bundle.getString("test");
        String usuario = bundle.getString("nombreusuario");
        Glide.with(getContext()).
                load(imagen).into(imagencito);
        nombreusuario.setText(usuario);



        edit_text.setText(test); // so this code is no working



    }

}