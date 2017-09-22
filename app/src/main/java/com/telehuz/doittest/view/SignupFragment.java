package com.telehuz.doittest.view;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.telehuz.doittest.R;
import com.telehuz.doittest.presenter.SignupPresenterImpl;
import com.telehuz.doittest.util.FileUtils;

import java.io.File;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.app.Activity.RESULT_OK;

public class SignupFragment extends Fragment implements SignupView {

    public static final int IMAGE_RESULT = 0;

    @BindView(R.id.fragment_signup_button_signup)
    Button signupButton;

    @BindView(R.id.fragment_signup_edit_username)
    EditText editUsername;

    @BindView(R.id.fragment_signup_edit_email)
    EditText editEmail;

    @BindView(R.id.fragment_signup_edit_password)
    EditText editPassword;

    @BindView(R.id.fragment_signup_image_user)
    ImageView imageUser;

    @BindView(R.id.fragment_signup_progress)
    ProgressBar progressBar;

    SignupPresenterImpl presenter;

    File userImageFile;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_signup, container, false);

        ButterKnife.bind(this, view);

        presenter = new SignupPresenterImpl(this);

        imageUser.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), IMAGE_RESULT);
        });

        signupButton.setOnClickListener(v -> presenter.onSignupButtonClick(editUsername.getText().toString(), editEmail.getText().toString(),
                    editPassword.getText().toString(), userImageFile));

        return  view;
    }

    @Override
    public void onStop() {
        super.onStop();

        if (presenter != null) {
            presenter.onStop();
        }
    }

    @Override
    public void startMainActivity() {
        Intent intent = new Intent(getActivity(), MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == IMAGE_RESULT && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri uri = data.getData();

            userImageFile = FileUtils.getFile(getContext(), uri);

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), uri);
                imageUser.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void showProgress(boolean show) {
        if (show)
            progressBar.setVisibility(View.VISIBLE);
        else
            progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showError(String error) {
        makeToast(error);
    }

    @Override
    public void resetErrors() {

        editUsername.setError(null);
        editEmail.setError(null);
        editPassword.setError(null);
    }

    @Override
    public void showUsernameError(int error) {

        editUsername.setError(getString(error));
        editUsername.requestFocus();
    }

    @Override
    public void showEmailError(int error) {

        editEmail.setError(getString(error));
        editEmail.requestFocus();
    }

    @Override
    public void showPasswordError(int error) {

        editPassword.setError(getString(error));
        editPassword.requestFocus();
    }

    private void makeToast(String text) {
        Toast toast = Toast.makeText(getContext(), text, Toast.LENGTH_SHORT);
        toast.show();
    }
}
