package com.example.exampleapplication.adapter;

import android.graphics.Matrix;
import android.os.Handler;
import android.os.HandlerThread;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.exampleapplication.R;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

public class ImageViewerAdapter extends RecyclerView.Adapter<ImageViewerAdapter.ImageViewHolder> {
    private int[] imageAssets;
//    https://w0.pngwave.com/png/763/47/koala-bear-cuteness-koala-png-clip-art.png
//    https://w0.pngwave.com/png/297/962/polar-bear-brown-bear-american-black-bear-grizzly-bear-chicago-bears-png-clip-art.png
//    https://w0.pngwave.com/png/5/748/teddy-bear-stuffed-animals-cuddly-toys-plush-plush-png-clip-art.png

    public ImageViewerAdapter(int[] imageAssetIds) {
        imageAssets = imageAssetIds;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ImageViewHolder(
                LayoutInflater.from(
                        parent.getContext()).inflate(R.layout.layout_image_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        holder.bind(imageAssets[position]);

    }

    @Override
    public int getItemCount() {
        if (imageAssets != null) {
            return imageAssets.length;
        }
        return 0;
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private SimpleDraweeView imageview;
        private Matrix matrix;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);

            matrix = new Matrix();

            imageview = itemView.findViewById(R.id.imageView);
        }

        public void bind(int resourceId) {
            imageview.setImageResource(resourceId);
            imageview.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.imageView) {
                imageview.setScaleType(ImageView.ScaleType.MATRIX);

                matrix.setTranslate(100, 100);
                matrix.postScale(2.0f, 2.0f, 0, 0);
                imageview.setImageMatrix(matrix);

            }
        }
    }
}
