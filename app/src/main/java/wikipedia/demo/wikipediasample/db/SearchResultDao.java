package wikipedia.demo.wikipediasample.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import io.reactivex.Single;
import wikipedia.demo.wikipediasample.models.SearchResult;

@Dao
public interface SearchResultDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertSearchResult(SearchResult searchResult);

    @Query("SELECT * FROM SearchResult WHERE `query` = :query")
    Single<SearchResult> getSearchResult(String query);

    @Query("SELECT `query` FROM SearchResult ORDER BY searchId DESC LIMIT 10")
    Single<List<String>> getLastTenSearches();
}
