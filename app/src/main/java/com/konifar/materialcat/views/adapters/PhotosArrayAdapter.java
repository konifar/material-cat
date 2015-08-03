package com.konifar.materialcat.views.adapters;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.konifar.materialcat.R;
import com.konifar.materialcat.models.pojo.Photo;
import com.konifar.materialcat.views.AspectRatioImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class PhotosArrayAdapter extends ArrayAdapter<Photo> {

    public PhotosArrayAdapter(Context context) {
        super(context, R.layout.item_photo, new ArrayList<Photo>());
    }

    @Override
    public View getView(int pos, View view, ViewGroup parent) {
        ViewHolder holder;

        if (view == null || view.getTag() == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.item_photo, parent, false);
            holder = new ViewHolder(view);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        final Photo photo = getItem(pos);
        String imageUrl = photo.getImageUrl();

        if (holder.imgPreview.getTag() == null || !holder.imgPreview.getTag().equals(imageUrl)) {
            Picasso.with(getContext())
                    .load(imageUrl)
                    .into(holder.imgPreview);
            holder.imgPreview.setTag(imageUrl);
        }

        view.setTag(holder);

        return view;
    }

    public void addAll(List<Photo> photos) {
        if (photos == null) return;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            super.addAll(photos);
        } else {
            for (Photo item : photos) {
                super.add(item);
            }
        }
    }

    static class ViewHolder {
        @Bind(R.id.img_preview)
        AspectRatioImageView imgPreview;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

}
