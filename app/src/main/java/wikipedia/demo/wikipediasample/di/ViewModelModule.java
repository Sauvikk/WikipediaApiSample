package wikipedia.demo.wikipediasample.di;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;
import wikipedia.demo.wikipediasample.WikipediaSampleViewModelFactory;
import wikipedia.demo.wikipediasample.ui.listSearchResult.ListSearchResultViewModel;
import wikipedia.demo.wikipediasample.ui.userSearchHistory.UserSearchHistoryViewModel;

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(ListSearchResultViewModel.class)
    abstract ViewModel bindListSearchResultViewModel(ListSearchResultViewModel listSearchResultViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(UserSearchHistoryViewModel.class)
    abstract ViewModel bindUserSearchHistoryViewModel(UserSearchHistoryViewModel userSearchHistoryViewModel);

    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(WikipediaSampleViewModelFactory factory);
}
