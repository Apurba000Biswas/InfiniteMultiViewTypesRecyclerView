package com.apurba.infinitemultiviewtypesrecyclerview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PaginationAdapter extends RecyclerView.Adapter < RecyclerView.ViewHolder > {

    private static final int LOADING_VIEW_TYPE = 1;
    private static final int ITEM_VIEW_TYPE = 0;

    private List<DataItem> dataSet;
    private boolean isLoadingAdded;

    public PaginationAdapter(){
        dataSet = new ArrayList<>();
        isLoadingAdded = false;
    }


    public void addAll(List<DataItem> dataSet){
        this.dataSet.addAll(dataSet);
        notifyDataSetChanged();
    }

    public void clearAll(){
        dataSet.clear();
        notifyDataSetChanged();
    }

    public int getLastId(){
        return dataSet.get(dataSet.size()-1).getId();
    }

    public void addLoadingFooter() {
        isLoadingAdded = true;
        dataSet.add(new DataItem("", -1));
        notifyDataSetChanged();
    }


    public void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = dataSet.size() - 1;
        DataItem item = dataSet.get(position);
        if (item != null) {
            dataSet.remove(position);
            notifyItemRemoved(position);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return (position == dataSet.size() - 1 && isLoadingAdded) ? LOADING_VIEW_TYPE : ITEM_VIEW_TYPE;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == ITEM_VIEW_TYPE){
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.data_item, parent, false);
            return new DataItemViewHolder(view);
        }else{
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.loading_view, parent, false);
            return new LoadingViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        int viewType = getItemViewType(position);
        if (viewType == ITEM_VIEW_TYPE){
            DataItemViewHolder viewHolder = (DataItemViewHolder) holder;
            viewHolder.bindView(dataSet.get(position));
        }
    }


    class DataItemViewHolder extends RecyclerView.ViewHolder{
        private TextView tvName;

        DataItemViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name);
        }

        void bindView(DataItem dataItem){
            String name = dataItem.getName() + " " + dataItem.getId();
            tvName.setText(name);
        }
    }

    class LoadingViewHolder extends RecyclerView.ViewHolder{

        LoadingViewHolder(@NonNull View itemView) {
            super(itemView);
            ProgressBar loadingIndicator = itemView.findViewById(R.id.loading_indicator);
            loadingIndicator.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return dataSet == null ? 0 : dataSet.size();
    }
}
