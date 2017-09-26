package com.telehuz.doittest;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.telehuz.doittest.model.data.Image;
import com.telehuz.doittest.presenter.MyImageViewPresenter;
import com.telehuz.doittest.view.holder.MyImageViewHolder;

import java.util.ArrayList;
import java.util.List;

public class ImagesListAdapter extends RecyclerView.Adapter<MyImageViewHolder> {

    private final SparseArray<MyImageViewPresenter> presenters;
    private final List<Image> models;

    public ImagesListAdapter() {
        presenters = new SparseArray<>();
        models = new ArrayList<>();
    }

    public void clearAndAddAll(List<Image> images) {
        models.clear();
        presenters.clear();

        for (Image image : images) {
            addInternal(image);
        }

        notifyDataSetChanged();
    }

    @NonNull
    protected MyImageViewPresenter createPresenter(@NonNull Image model) {
        return new MyImageViewPresenter(model);
    }

    private void addInternal(Image item) {

        models.add(item);
        presenters.put(getModelId(item), createPresenter(item));
    }

    @Override
    public MyImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyImageViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image, parent, false));
    }

    @Override
    public void onBindViewHolder(MyImageViewHolder holder, int position) {

        holder.bindPresenter(getPresenter(getItem(position)));
    }

    @Override
    public void onViewRecycled(MyImageViewHolder holder) {
        super.onViewRecycled(holder);

        holder.unbindPresenter();
    }

    @Override
    public boolean onFailedToRecycleView(MyImageViewHolder holder) {
        // Sometimes, if animations are running on the itemView's children, the RecyclerView won't
        // be able to recycle the view. We should still unbind the presenter.
        holder.unbindPresenter();

        return super.onFailedToRecycleView(holder);
    }

    @NonNull private MyImageViewPresenter getPresenter(@NonNull Image model) {
        return presenters.get(getModelId(model));
    }

    private int getModelId(@NonNull Image model) {
        return model.getId();
    }

    private Image getItem(int position) {
        return models.get(position);
    }

    @Override
    public int getItemCount() {
        return models.size();
    }
}