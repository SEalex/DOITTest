package com.telehuz.doittest.view.activity;

import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.telehuz.doittest.ImagesListAdapter;
import com.telehuz.doittest.R;
import com.telehuz.doittest.model.data.MyImages;
import com.telehuz.doittest.presenter.MainPresenter;
import com.telehuz.doittest.view.MainView;
import com.telehuz.doittest.view.fragment.NewImageFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements MainView {

    @BindView(R.id.image_gif)
    ImageView gifImageView;

    @BindView(R.id.button_add)
    ImageView addButton;

    @BindView(R.id.button_gif)
    ImageView gifButton;

    @BindView(R.id.activity_main_progress_bar)
    ProgressBar progressBar;

    @BindView(R.id.recycler_view)
    RecyclerView listView;

    @BindView(R.id.image_gif_layout)
    RelativeLayout gifLayout;

    MainPresenter presenter;

    ImagesListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        adapter = new ImagesListAdapter();
        listView.setLayoutManager(new GridLayoutManager(this, 3));
        listView.setAdapter(adapter);

        presenter = new MainPresenter(this);
        presenter.loadImages();

        addButton.setOnClickListener(v -> {
            presenter.onAddImageClick();
        });

        gifButton.setOnClickListener(v -> {
           presenter.onShowGifClick();
        });
    }

    @Override
    public void showGifLayout() {
        gifLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void showGif(String url) {
        Glide.with(this)
                .load(url)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        showProgress(false);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        showProgress(false);
                        return false;
                    }
                })
                .into(gifImageView);
    }

    @Override
    public void closeGif() {
        Glide.with(this).clear(gifImageView);
        gifLayout.setVisibility(View.GONE);
    }

    @Override
    public boolean isGifLayoutVisible() {
        return gifLayout.getVisibility() == View.VISIBLE;
    }

    @Override
    public void onBackPressed() {
        presenter.onBackPressed();
    }

    @Override
    public void goBack() {
        super.onBackPressed();
    }

    @Override
    public void showProgress(boolean show) {
        if (show) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void showError(String error) {
        makeToast(error);
    }

    @Override
    public void showImages(MyImages images) {
        adapter.clearAndAddAll(images.getImages());
    }

    @Override
    public void openNewImageFragment() {
        openFragment(new NewImageFragment());
    }

    private void openFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragment_container, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void makeToast(String text) {
        Toast toast = Toast.makeText(this, text, Toast.LENGTH_SHORT);
        toast.show();
    }

    public MainPresenter getPresenter() {
        return presenter;
    }
}
