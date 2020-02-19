package com.apurba.infinitemultiviewtypesrecyclerview;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

public class PaginationAdapter extends RecyclerView.Adapter < RecyclerView.ViewHolder > {

    private static final int LOADING_VIEW_TYPE = 1;
    private static final int ITEM_VIEW_TYPE = 0;
    private static final int HEADER_SEARCH_VIEW_TYPE = 2;
    private static final int CATEGORY_VIEW_TYPE = 3;

    private List<DataItem> dataSet;
    private boolean isLoadingAdded;
    private boolean isAnimate;

    private Context mContext;

    public PaginationAdapter(Context context){
        dataSet = new ArrayList<>();
        isLoadingAdded = false;
        mContext = context;
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
        //notifyItemInserted(dataSet.size()-1);
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
        if (position == 0){
            return HEADER_SEARCH_VIEW_TYPE;
        }else if((position == dataSet.size()+1 && isLoadingAdded)){
            return LOADING_VIEW_TYPE;
        }else if(position == 1){
            return CATEGORY_VIEW_TYPE;
        } else{
            return ITEM_VIEW_TYPE;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == ITEM_VIEW_TYPE){
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.data_item, parent, false);
            return new DataItemViewHolder(view);
        }else if (viewType == LOADING_VIEW_TYPE){
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.loading_view, parent, false);
            return new LoadingViewHolder(view);
        }else if (viewType == HEADER_SEARCH_VIEW_TYPE){
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.search_view, parent, false);
            return new SearchViewHolder(view);
        }else{
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.category_view, parent, false);
            return new CategoryViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        int viewType = getItemViewType(position);
        Log.d("Adapter", "Position : " + position);
        if (viewType == ITEM_VIEW_TYPE){
            DataItemViewHolder viewHolder = (DataItemViewHolder) holder;
            viewHolder.bindView(dataSet.get(position-2));
        }else if (viewType == HEADER_SEARCH_VIEW_TYPE){
            SearchViewHolder viewHolder = (SearchViewHolder) holder;
            viewHolder.bindView();
        }else if (viewType == CATEGORY_VIEW_TYPE){
            CategoryViewHolder viewHolder = (CategoryViewHolder) holder;
            viewHolder.bindView();
        }
    }

    @Override
    public int getItemCount() {
        return dataSet == null ? 0 : dataSet.size()+2;
    }


    class DataItemViewHolder extends RecyclerView.ViewHolder{
        private ImageView imageView;
        private View root;

        DataItemViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.iv_image);
            root = itemView.findViewById(R.id.item_holder);
        }

        void bindView(DataItem dataItem){
            if (dataItem.getName() != null && !dataItem.getName().isEmpty()){
                Picasso.get()
                        .load(dataItem.getName())
                        .into(imageView);
            }

            if (isAnimate)animateView(root);
        }


    }

    private void animateView(View root){
        float offset =  mContext.getResources().getDimensionPixelSize(R.dimen.offset_y);
        Interpolator interpolator =
                AnimationUtils.loadInterpolator(mContext, android.R.interpolator.fast_out_slow_in);

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

    class LoadingViewHolder extends RecyclerView.ViewHolder{

        LoadingViewHolder(@NonNull View itemView) {
            super(itemView);
            ProgressBar loadingIndicator = itemView.findViewById(R.id.loading_indicator);
            loadingIndicator.setVisibility(View.VISIBLE);
        }
    }

    class SearchViewHolder extends RecyclerView.ViewHolder{
        private View searchHolder;
        public SearchViewHolder(@NonNull View itemView) {
            super(itemView);
            searchHolder = itemView.findViewById(R.id.search_holder);
        }
        void bindView(){
            if (isAnimate)animateView(searchHolder);
        }
    }

    class CategoryViewHolder extends RecyclerView.ViewHolder{

        View root;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            root = itemView.findViewById(R.id.cat_holder);
        }

        void bindView(){
            if (isAnimate) animateView(root);
        }
    }


}
