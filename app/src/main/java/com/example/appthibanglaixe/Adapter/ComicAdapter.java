package com.example.appthibanglaixe.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.appthibanglaixe.R;
import com.example.appthibanglaixe.model.Comic;

import java.util.List;

public class ComicAdapter extends RecyclerView.Adapter<ComicAdapter.ComicViewHolder> {

    private List<Comic> comicList;
    private Context context;
    private OnItemClickListener onItemClickListener;

    public ComicAdapter(Context context, List<Comic> comicList) {
        this.context = context;
        this.comicList = comicList;
    }

    public interface OnItemClickListener {
        void onItemClick(Comic comic);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ComicViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_cus_bode, parent, false);
        return new ComicViewHolder(view, context, onItemClickListener, comicList);
    }

    @Override
    public void onBindViewHolder(@NonNull ComicViewHolder holder, int position) {
        Comic comic = comicList.get(position);
        holder.bind(comic);
    }

    @Override
    public int getItemCount() {
        return comicList.size();
    }

    public static class ComicViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView titleTextView;
        TextView descriptionTextView;
        Context context;

        public ComicViewHolder(@NonNull View itemView, Context context, OnItemClickListener onItemClickListener, List<Comic> comicList) {
            super(itemView);
            this.context = context;
            imageView = itemView.findViewById(R.id.imageView_comic);
            titleTextView = itemView.findViewById(R.id.textView_title);
            descriptionTextView = itemView.findViewById(R.id.textView_description);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && onItemClickListener != null) {
                        onItemClickListener.onItemClick(comicList.get(position));
                    }
                }
            });
        }

        public void bind(Comic comic) {
            Glide.with(context)
                    .load(comic.getImageUrl())
                    .error(R.drawable.icon)
                    .into(imageView);
            titleTextView.setText(comic.getTitle());
            descriptionTextView.setText(comic.getDescription());
        }
    }
}
