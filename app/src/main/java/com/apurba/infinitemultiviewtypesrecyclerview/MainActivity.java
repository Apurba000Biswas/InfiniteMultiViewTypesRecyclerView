package com.apurba.infinitemultiviewtypesrecyclerview;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import java.util.List;

public class MainActivity extends BaseActivity implements RESTController.TheCatApiResponseListener{

    private PaginationAdapter adapter;
    private LinearLayoutManager linearLayoutManager;
    private View progressBar;



    private static final int PAGE_START = 0;

    private boolean isLoading = false;
    private boolean isLastPage = false;

    private MainViewModel mainViewModel;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setNotificationBarBlackNWhite();

        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);

        progressBar = findViewById(R.id.loading_indicator);
        setRecyclerView();


        //setDealView();
    }


    private void setRecyclerView(){
        RecyclerView recyclerView = findViewById(R.id.rv_data_list);
        recyclerView.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        adapter = new PaginationAdapter(this);
        recyclerView.setAdapter(adapter);

        recyclerView.addOnScrollListener( new PaginationScrollListener(linearLayoutManager) {
            @Override
            protected void loadMoreItems() {
                loadNextPage();
            }

            @Override
            public boolean isLastPage() {
                return isLastPage;
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }

            @Override
            public void scrolled() {
                adapter.setAnimationTrue();
            }
        });

        loadFirstPage();
        mainViewModel.getCats().observe(this, new Observer<List<DataItem>>() {
            @Override
            public void onChanged(List<DataItem> dataItems) {
                if (dataItems != null && !dataItems.isEmpty()){
                    adapter.addAll(dataItems);
                    adapter.removeLoadingFooter();
                    progressBar.setVisibility(View.GONE);
                    isLoading = false;
                }

            }
        });
    }

    private void loadFirstPage() {
        //RESTController.makeCatImageRequest(20, this);
        //VirtualRESTAPi.getNextData(PAGE_START, this);
        //mainViewModel.loadNext(4);
        progressBar.setVisibility(View.VISIBLE);
        isLoading = true;
    }

    private void loadNextPage() {
        //RESTController.makeCatImageRequest(20, this);
        //VirtualRESTAPi.getNextData(adapter.getLastId()+1, this);
        adapter.addLoadingFooter();
        mainViewModel.loadNext(4);

        isLoading = true;
    }



    private void setDealView(){
        //Deal dealView = findViewById(R.id.deal_view);
        //dealView.setHeader("Apurba");
        //dealView.setContent("Demo Content");
    }


    @Override
    public void onApiResponse(List<DataItem> pageData) {
        progressBar.setVisibility(View.GONE);
        isLoading = false;
        adapter.removeLoadingFooter();
        if (pageData == null){
            adapter.notifyDataSetChanged();
            isLastPage = true;
            return;
        }
        //adapter.removeLoadingFooter();
        adapter.addAll(pageData);
    }
}
