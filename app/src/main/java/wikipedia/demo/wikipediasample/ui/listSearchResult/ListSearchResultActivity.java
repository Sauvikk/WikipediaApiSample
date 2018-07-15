package wikipedia.demo.wikipediasample.ui.listSearchResult;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.DaggerAppCompatActivity;
import wikipedia.demo.wikipediasample.R;
import wikipedia.demo.wikipediasample.models.Page;
import wikipedia.demo.wikipediasample.ui.userSearchHistory.UserSearchHistoryActivity;
import wikipedia.demo.wikipediasample.ui.wikiWebView.WebViewActivity;

public class ListSearchResultActivity extends DaggerAppCompatActivity {

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.empty_view)
    View emptyView;
    @BindView(R.id.error_view)
    TextView errorView;

    private ListSearchResultViewModel listSearchResultViewModel;
    private ListSearchResultAdapter listSearchResultAdapter;
    private SearchView searchView;

    private final static String QUERY = "query";

    public static void startActivity(Context context, String query) {
        Intent i = new Intent(context, ListSearchResultActivity.class);
        i.putExtra(QUERY, query);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_search_result_activity_layout);
        ButterKnife.bind(this);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(getString(R.string.wikipedia));
        }
        listSearchResultViewModel = ViewModelProviders.of(this, viewModelFactory).get(ListSearchResultViewModel.class);
        setUpRecyclerView();
        subscribeToViewModel();
        if (getIntent().getStringExtra(QUERY) != null) {
            listSearchResultViewModel.loadResults(getIntent().getStringExtra(QUERY));
        }
    }

    private void subscribeToViewModel() {
        listSearchResultViewModel.getResult().observe(this, listResource -> {
            switch (listResource.getStatus()) {
                case LOADING:
                    progressBar.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                    emptyView.setVisibility(View.GONE);
                    errorView.setVisibility(View.GONE);
                    break;
                case ERROR:
                    progressBar.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.GONE);
                    emptyView.setVisibility(View.GONE);
                    errorView.setVisibility(View.VISIBLE);
                    errorView.setText(getString(listResource.getMessageId()));
                    break;
                case SUCCESS:
                    if (listResource.getData() != null && listResource.getData().size() != 0) {
                        listSearchResultAdapter.addData(listResource.getData());
                        progressBar.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                        emptyView.setVisibility(View.GONE);
                        errorView.setVisibility(View.GONE);
                    } else {
                        progressBar.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.GONE);
                        emptyView.setVisibility(View.VISIBLE);
                        errorView.setVisibility(View.GONE);
                    }
                    break;
            }
        });
    }

    private void setUpRecyclerView() {
        listSearchResultAdapter = new ListSearchResultAdapter();
        recyclerView.setAdapter(listSearchResultAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        listSearchResultAdapter.setItemClickListener(page -> WebViewActivity.startActivity(this, page.getFullUrl()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        MenuItem searchMenuItem = menu.findItem(R.id.search);
        searchView = (SearchView) searchMenuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (!query.isEmpty()) {
                    listSearchResultViewModel.loadResults(query);
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.history) {
            Intent i = new Intent(this, UserSearchHistoryActivity.class);
            startActivity(i);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        listSearchResultAdapter.setItemClickListener(null);
        searchView.setOnQueryTextListener(null);
        super.onDestroy();
    }
}
