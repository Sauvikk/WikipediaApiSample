package wikipedia.demo.wikipediasample.ui.userSearchHistory;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.DaggerAppCompatActivity;
import wikipedia.demo.wikipediasample.R;
import wikipedia.demo.wikipediasample.ui.listSearchResult.ListSearchResultActivity;

public class UserSearchHistoryActivity extends DaggerAppCompatActivity {

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.list_view)
    ListView listView;
    @BindView(R.id.empty_view)
    TextView emptyView;

    private ArrayAdapter<String> adapter;
    private List<String> searchHistory;
    private UserSearchHistoryViewModel userSearchHistoryViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_search_history);
        ButterKnife.bind(this);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(getString(R.string.search_history));
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        searchHistory = new ArrayList<>();
        userSearchHistoryViewModel = ViewModelProviders.of(this, viewModelFactory).get(UserSearchHistoryViewModel.class);
        setUpListView();
        subscribeToViewModel();
    }

    private void subscribeToViewModel() {
        userSearchHistoryViewModel.getResult().observe(this, listResource -> {
            switch (listResource.getStatus()) {
                case LOADING:
                    progressBar.setVisibility(View.VISIBLE);
                    emptyView.setVisibility(View.GONE);
                    listView.setVisibility(View.GONE);
                    break;
                case ERROR:
                    emptyView.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                    listView.setVisibility(View.GONE);
                    break;
                case SUCCESS:
                    if (listResource.getData() != null && listResource.getData().size() != 0) {
                        listView.setVisibility(View.VISIBLE);
                        emptyView.setVisibility(View.GONE);
                        progressBar.setVisibility(View.GONE);
                        searchHistory.clear();
                        searchHistory.addAll(listResource.getData());
                        adapter.notifyDataSetChanged();
                    } else {
                        emptyView.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.GONE);
                        listView.setVisibility(View.GONE);
                    }
                    break;
            }
        });
    }

    private void setUpListView() {
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, searchHistory);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener((parent, view, position, id) -> {
            ListSearchResultActivity.startActivity(this, searchHistory.get(position));
            finish();
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
