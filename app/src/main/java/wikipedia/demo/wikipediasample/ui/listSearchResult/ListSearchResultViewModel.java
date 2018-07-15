package wikipedia.demo.wikipediasample.ui.listSearchResult;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.persistence.room.EmptyResultSetException;
import android.util.Log;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.exceptions.CompositeException;
import io.reactivex.schedulers.Schedulers;
import wikipedia.demo.wikipediasample.R;
import wikipedia.demo.wikipediasample.models.Page;
import wikipedia.demo.wikipediasample.models.Resource;
import wikipedia.demo.wikipediasample.repository.SearchRepository;

public class ListSearchResultViewModel extends ViewModel {

    private final SearchRepository searchRepository;

    private MutableLiveData<Resource<List<Page>>> result = new MutableLiveData<>();
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Inject
    public ListSearchResultViewModel(SearchRepository searchRepository) {
        this.searchRepository = searchRepository;
    }

    public void loadResults(String query) {
        compositeDisposable.add(searchRepository.loadData(query)
                .doOnSubscribe(subscription -> result.postValue(Resource.loading(null)))
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(pageList -> result.postValue(Resource.success(pageList)),
                        throwable -> {
                            if (throwable instanceof CompositeException && ((CompositeException) throwable).getExceptions().get(0) instanceof EmptyResultSetException) {
                                result.postValue(Resource.error(R.string.no_cached_result));
                            } else {
                                result.postValue(Resource.error(R.string.something_went_wrong));
                            }
                        })
        );
    }

    public MutableLiveData<Resource<List<Page>>> getResult() {
        return result;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.clear();
    }
}
