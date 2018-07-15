package wikipedia.demo.wikipediasample.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import wikipedia.demo.wikipediasample.models.Page;
import wikipedia.demo.wikipediasample.models.SearchResult;

@Database(entities = {Page.class, SearchResult.class}, version = 1, exportSchema = false)
public abstract class WikiDb extends RoomDatabase {
    abstract public PageDao pageDao();

    abstract public SearchResultDao searchResultDao();
}

