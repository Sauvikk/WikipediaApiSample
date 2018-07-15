package wikipedia.demo.wikipediasample.repository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.exceptions.Exceptions;
import wikipedia.demo.wikipediasample.db.WikiDb;

public class UserSearchHistoryRepository {

    private final WikiDb wikiDb;

    @Inject
    public UserSearchHistoryRepository(WikiDb wikiDb) {
        this.wikiDb = wikiDb;
    }

    public Single<List<String>> getSearchHistory() {
        return wikiDb.searchResultDao().getLastTenSearches();
    }

}
