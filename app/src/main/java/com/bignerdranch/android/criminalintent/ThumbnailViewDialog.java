package com.bignerdranch.android.criminalintent;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;

public class ThumbnailViewDialog extends DialogFragment {
    public static final String EXTRA_IMAGE =
            "com.bignerdranch.android.criminalintent.image";
    private static final String ARG_IMAGE = "image";
    private Button mDismissButton;
    private ImageView mPhotoView;
    private File mPhotoFile;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_image, container, false);
        getDialog().setTitle(R.string.image_dialog_title);
        mPhotoFile = (File)getArguments().getSerializable(ARG_IMAGE);

        mPhotoView = (ImageView) v.findViewById(R.id.dialogImageView);
        mPhotoView.setImageBitmap(getPhotoFileToBitmap(mPhotoFile));

        mDismissButton = (Button) v.findViewById(R.id.dismissImageButton);
        mDismissButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        return v;
    }

    public static ThumbnailViewDialog newInstance(File photoFile) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_IMAGE, photoFile);

        ThumbnailViewDialog fragment = new ThumbnailViewDialog();
        fragment.setArguments(args);
        return fragment;
    }

    private void sendResult(int resultCode, File photoFile) {
        if (getTargetFragment() == null) {
            return;
        }

        Intent intent = new Intent();
        intent.putExtra(EXTRA_IMAGE, photoFile);

        getTargetFragment()
                .onActivityResult(getTargetRequestCode(), resultCode, intent);
    }

    private Bitmap getPhotoFileToBitmap(File photoFile) {
        if (photoFile == null || !photoFile.exists()) {
            return null;
        } else {
            Bitmap bitmap = PictureUtils.getScaledBitmap(
                    photoFile.getPath(), getActivity());
            return bitmap;
        }
    }
}
