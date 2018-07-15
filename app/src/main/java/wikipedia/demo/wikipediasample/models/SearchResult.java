package wikipedia.demo.wikipediasample.models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.NonNull;

import java.util.List;


import wikipedia.demo.wikipediasample.db.WikiTypeConverter;

@Entity(indices = @Index(value = "query", unique = true))
@TypeConverters(WikiTypeConverter.class)
public class SearchResult {

    @PrimaryKey(autoGenerate = true)
    private final int searchId;
    public final String query;
    public final List<Integer> pageIds;

    public SearchResult(int searchId, String query, List<Integer> pageIds) {
        this.searchId = searchId;
        this.query = query;
        this.pageIds = pageIds;
    }

    public List<Integer> getPageIds() {
        return pageIds;
    }

    public int getSearchId() {
        return searchId;
    }
}
