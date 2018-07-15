package wikipedia.demo.wikipediasample.ui.userSearchHistory;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import wikipedia.demo.wikipediasample.R;
import wikipedia.demo.wikipediasample.models.Resource;
import wikipedia.demo.wikipediasample.repository.UserSearchHistoryRepository;

public class UserSearchHistoryViewModel extends ViewModel {

    private final UserSearchHistoryRepository userSearchHistoryRepository;
    private MutableLiveData<Resource<List<String>>> result = new MutableLiveData<>();
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Inject
    public UserSearchHistoryViewModel(UserSearchHistoryRepository userSearchHistoryRepository) {
        this.userSearchHistoryRepository = userSearchHistoryRepository;
        getLastTenSearches();
    }

    private void getLastTenSearches() {
        compositeDisposable.add(userSearchHistoryRepository.getSearchHistory()
                .doOnSubscribe(subscription -> result.postValue(Resource.loading(null)))
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(strings -> result.postValue(Resource.success(strings)),
                        throwable -> result.postValue(Resource.error(R.string.something_went_wrong))));
    }

    public MutableLiveData<Resource<List<String>>> getResult() {
        return result;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.clear();
    }
}
