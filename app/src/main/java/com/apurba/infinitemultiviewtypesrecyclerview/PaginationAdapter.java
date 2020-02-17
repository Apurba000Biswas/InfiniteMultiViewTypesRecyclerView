package com.apurba.infinitemultiviewtypesrecyclerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
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
    private boolean isAnimate;

    public PaginationAdapter(){
        dataSet = new ArrayList<>();
        isLoadingAdded = false;
    }


    public void addAll(List<DataItem> dataSet){
        this.dataSet.addAll(dataSet);
        isAnimate = false;
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

    public void setAnimationTrue(){
        isAnimate = true;
    }


    public void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = dataSet.size() - 1;
        if (position>=0){
            DataItem item = dataSet.get(position);
            if (item != null) {
                dataSet.remove(position);
                notifyItemRemoved(position);
            }
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
        private Context context;
        private View root;

        DataItemViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name);
            context = itemView.getContext();
            root = itemView.findViewById(R.id.item_holder);
        }

        void bindView(DataItem dataItem){
            String name = dataItem.getName() + " " + dataItem.getId();
            tvName.setText(name);
            if (isAnimate)animateView();
        }

        private void animateView(){
            float offset =  context.getResources().getDimensionPixelSize(R.dimen.offset_y);
            Interpolator interpolator =
                    AnimationUtils.loadInterpolator(context, android.R.interpolator.fast_out_slow_in);

            root.setVisibility(View.VISIBLE);
            root.setTranslationY(offset);
            root.setAlpha(0.85f);
            // then animate back to natural position
            root.animate()
                    .translationY(0f)
                    .alpha(1f)
                    .setInterpolator(interpolator)
                    .setDuration(600)
                    .start();
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
