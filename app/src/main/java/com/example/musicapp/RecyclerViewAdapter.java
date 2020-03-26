package com.example.musicapp;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> implements Filterable {
    private static final String TAG = "RecyclerViewAdapter";

    private List<musicItem> mExampleList;
    private List<musicItem> exampleListFull;
    private Context mContext;

    public RecyclerViewAdapter(Context Context, List<musicItem> ExampleList) {
        this.mExampleList = ExampleList;
        exampleListFull = new ArrayList<>(mExampleList);
        this.mContext = Context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_listitem, parent, false);

        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called.");
        Glide.with(mContext).asBitmap().load(mExampleList.get(position).getImageUrl()).into(holder.image);
        holder.albumName.setText(mExampleList.get(position).getAlbumName());
        holder.artistName.setText(mExampleList.get(position).getArtistName());
        holder.year.setText(mExampleList.get(position).getYear());
        holder.parentLayout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: click on : " + mExampleList.get(position).getArtistName());
                Toast.makeText(mContext, mExampleList.get(position).getArtistName(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(mContext, IndividualActivity.class);
                intent.putExtra("image_url", mExampleList.get(position).getImageUrl());
                intent.putExtra("year", mExampleList.get(position).getYear());
                intent.putExtra("albumName", mExampleList.get(position).getAlbumName());
                intent.putExtra("artistName", mExampleList.get(position).getArtistName());
                intent.putExtra("description", mExampleList.get(position).getDescription());
                intent.putExtra("trailer", mExampleList.get(position).getTrailer());
                mContext.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return mExampleList.size() ;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        CircleImageView image;
        TextView albumName;
        TextView year;
        TextView artistName;

        RelativeLayout parentLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            albumName = itemView.findViewById(R.id.albumsName);
            year = itemView.findViewById(R.id.year);
            artistName = itemView.findViewById(R.id.artistName);
            parentLayout = itemView.findViewById(R.id.parent_layout);
        }
    }

    @Override
    public Filter getFilter() {
        return exampleFilter;
    }

    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<musicItem> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(exampleListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (musicItem item : exampleListFull) {
                    if (item.getArtistName().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            mExampleList.clear();
            mExampleList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };
}
