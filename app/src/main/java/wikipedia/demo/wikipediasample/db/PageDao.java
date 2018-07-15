package wikipedia.demo.wikipediasample.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import io.reactivex.Single;
import wikipedia.demo.wikipediasample.models.Page;

@Dao
public interface PageDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertPage(Page page);

    @Query("SELECT * FROM Page WHERE pageId in (:pageIds)")
    Single<List<Page>> getPagesById(List<Integer> pageIds);
}
