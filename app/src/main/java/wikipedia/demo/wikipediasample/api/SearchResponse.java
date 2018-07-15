
package wikipedia.demo.wikipediasample.api;

import java.util.List;

import wikipedia.demo.wikipediasample.models.Page;

public class SearchResponse {

    private Boolean batchcomplete;
    private Query query;

    public Boolean getBatchcomplete() {
        return batchcomplete;
    }

    public Query getQuery() {
        return query;
    }

    public class Query {
        private List<Page> pages = null;

        public List<Page> getPages() {
            return pages;
        }
    }

}
