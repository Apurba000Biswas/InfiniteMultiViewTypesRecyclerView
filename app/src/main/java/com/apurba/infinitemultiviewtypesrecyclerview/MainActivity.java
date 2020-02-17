package com.apurba.infinitemultiviewtypesrecyclerview;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.List;

public class MainActivity extends BaseActivity implements VirtualRESTAPi.VirtualApiResponseListener{

    private PaginationAdapter adapter;
    private LinearLayoutManager linearLayoutManager;
    private RecyclerView recyclerView;
    private View progressBar;

    // Index from which pagination should start (0 is 1st page in our case)
    private static final int PAGE_START = 0;

    // Indicates if footer ProgressBar is shown (i.e. next page is loading)
    private boolean isLoading = false;

    // If current page is the last page (Pagination will stop after this page load)
    private boolean isLastPage = false;

    // total no. of pages to load. Initial load is page 0, after which 2 more pages will load.
    private int TOTAL_PAGES = 3;

    // indicates the current page which Pagination is fetching.
    private int currentPage = PAGE_START;

    private int lastId = PAGE_START;

    private PaginationScrollListener scrollListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setNotificationBarBlackNWhite();

        progressBar = findViewById(R.id.loading_indicator);
        setRecyclerView();


        //setDealView();
    }


    private void setRecyclerView(){
        recyclerView = findViewById(R.id.rv_data_list);
        recyclerView.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        adapter = new PaginationAdapter();
        recyclerView.setAdapter(adapter);

        scrollListener = new PaginationScrollListener(linearLayoutManager) {
            @Override
            protected void loadMoreItems() {
                loadNextPage();
            }

            @Override
            public int getTotalPageCount() {
                return TOTAL_PAGES;
            }

            @Override
            public boolean isLastPage() {
                return isLastPage;
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }
        };

        recyclerView.addOnScrollListener(scrollListener);
        loadFirstPage();
    }

    private void loadFirstPage() {
        VirtualRESTAPi.getNextData(PAGE_START, this);
        progressBar.setVisibility(View.VISIBLE);

        adapter.addLoadingFooter();
        isLoading = true;
    }

    private void loadNextPage() {

        VirtualRESTAPi.getNextData(lastId+1, this);
        adapter.addLoadingFooter();
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
            recyclerView.removeOnScrollListener(scrollListener);

            isLastPage = true;
            return;
        }
        lastId = pageData.get(pageData.size()-1).getId();
        adapter.addAll(pageData);


    }
}
