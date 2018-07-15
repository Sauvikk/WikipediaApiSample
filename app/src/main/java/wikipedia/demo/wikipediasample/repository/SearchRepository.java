package wikipedia.demo.wikipediasample.repository;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;
import wikipedia.demo.wikipediasample.api.ApiService;
import wikipedia.demo.wikipediasample.api.SearchResponse;
import wikipedia.demo.wikipediasample.db.WikiDb;
import wikipedia.demo.wikipediasample.models.Page;
import wikipedia.demo.wikipediasample.models.SearchResult;

public class SearchRepository {

    private final WikiDb wikiDb;
    private final ApiService apiService;
    private final Application application;

    @Inject
    public SearchRepository(WikiDb wikiDb, ApiService apiService, Application application) {
        this.wikiDb = wikiDb;
        this.apiService = apiService;
        this.application = application;
    }

    public Single<List<Page>> loadData(String query) {
        if (shouldFetchFromApi()) {
            return loadFromApi(query)
                    .subscribeOn(Schedulers.io())
                    .observeOn(Schedulers.io())
                    .doOnSuccess(response -> {
                        if (response.isSuccessful()) {
                            saveToDb(query, response.body());
                        }
                    })
                    .doOnError(Exceptions::propagate)
                    .flatMap((Function<Response<SearchResponse>, Single<List<Page>>>) response -> loadFromDb(query));

        } else {
            return loadFromDb(query);
        }
    }

    private void saveToDb(String query, SearchResponse searchResponse) {
        List<Integer> pageIds = new ArrayList<>();
        for (Page p : searchResponse.getQuery().getPages()) {
            pageIds.add(p.getPageId());
            wikiDb.pageDao().insertPage(p);
        }
        SearchResult searchResult = new SearchResult(0, query, pageIds);
        wikiDb.searchResultDao().insertSearchResult(searchResult);

    }

    private boolean shouldFetchFromApi() {
        ConnectivityManager connectivityManager = (ConnectivityManager) application.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private Single<List<Page>> loadFromDb(String query) {
        return wikiDb.searchResultDao().getSearchResult(query)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .doOnError(Exceptions::propagate)
                .flatMap((Function<SearchResult, Single<List<Page>>>) searchResult -> wikiDb.pageDao().getPagesById(searchResult.getPageIds()));
    }

    private Single<Response<SearchResponse>> loadFromApi(String query) {
        return apiService.getSearchResults(query);
    }

}
