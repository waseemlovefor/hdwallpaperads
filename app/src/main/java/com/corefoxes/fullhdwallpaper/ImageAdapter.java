package com.high.fullhdwallpaper;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageHolder> implements Filterable {
    Context context;
    List<PhotoList> photoLists=new ArrayList<>();
    List<PhotoList> fullphotolist=new ArrayList<>();

    public ImageAdapter(Context context, List<PhotoList> photoLists) {
        this.context = context;
        this.photoLists = photoLists;
        this.fullphotolist=photoLists;
    }

    @NonNull
    @Override
    public ImageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.listitem,parent,false);
        return new ImageHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageHolder holder, int position) {
        PhotoList list=photoLists.get(position);
        Glide.with(context).load(list.getMediumurl()).into(holder.imageView);
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,SecondActivityActivity.class);
                intent.putExtra("waseem",photoLists.get(position).getLarageurl());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return photoLists.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }
    Filter filter=new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<PhotoList> Filterlist=new ArrayList<>();
            if (constraint.toString().isEmpty()){
             Filterlist.addAll(photoLists);
            }else {
                 for (PhotoList list:photoLists){
                     if (list.toString().toLowerCase().contains(constraint.toString().toLowerCase())){
                  Filterlist.add(list);
                     }
                 }
            }
            FilterResults filterResults=new FilterResults();
             filterResults.values=Filterlist;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
         photoLists.clear();
         photoLists.addAll((Collection<? extends PhotoList>)results.values);
         notifyDataSetChanged();
        }
    };

    class ImageHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        public ImageHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.imageview);
        }
    }
}
