package wikipedia.demo.wikipediasample.api;

import io.reactivex.Single;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    @GET("api.php?action=query&format=json&prop=pageimages|pageterms|info&generator=prefixsearch&formatversion=2&piprop=thumbnail&pithumbsize=200&pilimit=10&wbptterms=description&inprop=url&gpslimit=10")
    Single<Response<SearchResponse>> getSearchResults(@Query("gpssearch") String gpsSearch);
}
