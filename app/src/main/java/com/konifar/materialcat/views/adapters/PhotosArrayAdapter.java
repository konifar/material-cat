package com.konifar.materialcat.views.adapters;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import com.konifar.materialcat.R;
import com.konifar.materialcat.models.pojo.Photo;
import com.konifar.materialcat.views.AspectRatioImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class PhotosArrayAdapter extends ArrayAdapter<Photo> {

    private int lastPosition = -1;

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

        view.setTag(holder);

        bindData(holder, getItem(pos));
        initListeners(holder, pos, view, parent);

        if (pos > 1) startAnimation(view, pos);

        return view;
    }

    private void initListeners(final ViewHolder holder, final int pos, final View view, final ViewGroup parent) {
        holder.ripple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((GridView) parent).performItemClick(view, pos, 0L);
            }
        });
    }

    private void bindData(ViewHolder holder, Photo photo) {
        String imageUrl = photo.getImageUrl();

        if (holder.imgPreview.getTag() == null || !holder.imgPreview.getTag().equals(imageUrl)) {
            Picasso.with(getContext())
                    .load(imageUrl)
                    .placeholder(R.color.theme50)
                    .into(holder.imgPreview);
            holder.imgPreview.setTag(imageUrl);
        }
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

    void startAnimation(View view, int pos) {
        if (lastPosition < pos) {
            Animation anim = AnimationUtils.loadAnimation(getContext(), R.anim.item_scale_in);
            view.startAnimation(anim);
            lastPosition = pos;
        }
    }

    static class ViewHolder {
        @Bind(R.id.img_preview)
        AspectRatioImageView imgPreview;
        @Bind(R.id.ripple)
        View ripple;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

}
