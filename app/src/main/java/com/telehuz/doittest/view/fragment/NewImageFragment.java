package com.telehuz.doittest.view.fragment;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.telehuz.doittest.R;
import com.telehuz.doittest.presenter.NewImagePresenter;
import com.telehuz.doittest.util.FileUtils;
import com.telehuz.doittest.view.NewImageView;
import com.telehuz.doittest.view.activity.MainActivity;

import java.io.File;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.app.Activity.RESULT_OK;

public class NewImageFragment extends Fragment implements NewImageView,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    public static final int IMAGE_RESULT = 14;
    public static final int REQUEST_LOCATION = 31;
    public static final int REQUEST_STORAGE = 33;

    @BindView(R.id.fragment_new_image_button_done)
    ImageView doneButton;

    @BindView(R.id.fragment_new_image_new_image)
    ImageView newImage;

    @BindView(R.id.fragment_new_image_edit_descripton)
    EditText editDescription;

    @BindView(R.id.fragment_new_image_edit_hashtag)
    EditText editTag;

    @BindView(R.id.fragment_new_image_progress)
    ProgressBar progressBar;

    NewImagePresenter presenter;

    File userImageFile;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_new_image, container, false);

        ButterKnife.bind(this, view);

        presenter = new NewImagePresenter(this);

        newImage.setOnClickListener(v -> {
            presenter.onAddImageClick();
        });

        doneButton.setOnClickListener(v -> {
            presenter.onDoneButtonClick();
        });

        mGoogleApiClient = new GoogleApiClient.Builder(getContext())
                // The next two lines tell the new client that “this” current class will handle connection stuff
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                //fourth line adds the LocationServices API endpoint from GooglePlayServices
                .addApi(LocationServices.API)
                .build();

        // Create the LocationRequest object
        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(10 * 1000)        // 10 seconds, in milliseconds
                .setFastestInterval(1 * 1000); // 1 second, in milliseconds

        return view;
    }

    @Override
    public void onStop() {
        super.onStop();

        if (presenter != null) {
            presenter.onStop();
        }
    }

    @Override
    public void findLocation() {
        mGoogleApiClient.connect();
    }

    @Override
    public void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), IMAGE_RESULT);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == IMAGE_RESULT && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri uri = data.getData();
            userImageFile = FileUtils.getFile(getContext(), uri);

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), uri);
                newImage.setImageBitmap(bitmap);
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

    private void makeToast(String text) {
        Toast toast = Toast.makeText(getContext(), text, Toast.LENGTH_SHORT);
        toast.show();
    }

    @Override
    public void close() {
        ((MainActivity) getActivity()).getPresenter().loadImages();
        getActivity().getSupportFragmentManager().popBackStack();
    }

    @Override
    public boolean mayRequestStorage() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        if (getActivity().checkSelfPermission(READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        if (shouldShowRequestPermissionRationale(READ_EXTERNAL_STORAGE)) {

            View rootView = getActivity().getWindow().getDecorView().findViewById(android.R.id.content);

            Snackbar.make(rootView, R.string.permission_rationale, Snackbar.LENGTH_INDEFINITE)
                    .setAction(android.R.string.ok, new View.OnClickListener() {
                        @Override
                        @TargetApi(Build.VERSION_CODES.M)
                        public void onClick(View v) {
                            requestPermissions(new String[]{READ_EXTERNAL_STORAGE}, REQUEST_STORAGE);
                        }
                    })
                    .show();
        } else {
            requestPermissions(new String[]{READ_EXTERNAL_STORAGE}, REQUEST_STORAGE);
        }
        return false;
    }

    @Override
    public boolean mayRequestLocation() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        if (getActivity().checkSelfPermission(ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        if (shouldShowRequestPermissionRationale(ACCESS_COARSE_LOCATION)) {

            View rootView = getActivity().getWindow().getDecorView().findViewById(android.R.id.content);

            Snackbar.make(rootView, R.string.permission_location, Snackbar.LENGTH_INDEFINITE)
                    .setAction(android.R.string.ok, new View.OnClickListener() {
                        @Override
                        @TargetApi(Build.VERSION_CODES.M)
                        public void onClick(View v) {
                            requestPermissions(new String[]{ACCESS_COARSE_LOCATION}, REQUEST_LOCATION);
                        }
                    })
                    .show();
        } else {
            requestPermissions(new String[]{ACCESS_COARSE_LOCATION}, REQUEST_LOCATION);
        }
        return false;
    }

    // LOCATION
    //Define a request code to send to Google Play services
    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private double currentLatitude;
    private double currentLongitude;

    /**
     * If connected get lat and long
     *
     */
    @Override
    public void onConnected(Bundle bundle) {

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M || getActivity().checkSelfPermission(ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

            if (location == null) {
                LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
                presenter.onLocationNotFound();

                Toast.makeText(getContext(), "Can't get location", Toast.LENGTH_LONG).show();

                if (mGoogleApiClient.isConnected()) {
                    LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
                    mGoogleApiClient.disconnect();
                }

            } else {
                //If everything went fine lets get latitude and longitude
                currentLatitude = location.getLatitude();
                currentLongitude = location.getLongitude();

                presenter.uploadImage(userImageFile, editDescription.getText().toString(), editTag.getText().toString(),
                        Double.valueOf(currentLatitude).floatValue(), Double.valueOf(currentLongitude).floatValue());

                if (mGoogleApiClient.isConnected()) {
                    LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
                    mGoogleApiClient.disconnect();
                }
            }
        } else {
            presenter.onLocationNotFound();
        }
    }


    @Override
    public void onConnectionSuspended(int i) {
        presenter.onLocationNotFound();
        Toast.makeText(getContext(), "Can't get location", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        presenter.onLocationNotFound();
        Toast.makeText(getContext(), "Can't get location", Toast.LENGTH_LONG).show();
    }

    /**
     * If locationChanges change lat and long
     *
     *
     * @param location
     */
    @Override
    public void onLocationChanged(Location location) {}
}
