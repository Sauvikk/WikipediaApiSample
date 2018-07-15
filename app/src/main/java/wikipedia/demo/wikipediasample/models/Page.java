
package wikipedia.demo.wikipediasample.models;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.TypeConverters;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import wikipedia.demo.wikipediasample.db.WikiTypeConverter;

@Entity(primaryKeys = "pageId", indices = @Index("pageId"))
@TypeConverters(WikiTypeConverter.class)
public class Page {

    @SerializedName("pageid")
    private int pageId;
    private String title;
    @Embedded(prefix = "thumbnail_")
    private Thumbnail thumbnail;
    @Embedded(prefix = "terms_")
    private Terms terms;
    @SerializedName("fullurl")
    private String fullUrl;

    public String getTitle() {
        return title;
    }

    public Thumbnail getThumbnail() {
        return thumbnail;
    }

    public Terms getTerms() {
        return terms;
    }

    public String getFullUrl() {
        return fullUrl;
    }

    public int getPageId() {
        return pageId;
    }

    public void setPageId(int pageId) {
        this.pageId = pageId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setThumbnail(Thumbnail thumbnail) {
        this.thumbnail = thumbnail;
    }

    public void setTerms(Terms terms) {
        this.terms = terms;
    }

    public void setFullUrl(String fullUrl) {
        this.fullUrl = fullUrl;
    }

    public static class Terms {
        private List<String> description = null;

        public List<String> getDescription() {
            return description;
        }

        public void setDescription(List<String> description) {
            this.description = description;
        }
    }

    public static class Thumbnail {
        private String source;

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }
    }
}
