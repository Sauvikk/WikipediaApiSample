package wikipedia.demo.wikipediasample.di;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import wikipedia.demo.wikipediasample.ui.listSearchResult.ListSearchResultActivity;
import wikipedia.demo.wikipediasample.ui.userSearchHistory.UserSearchHistoryActivity;

@Module
public abstract class ActivityBindingModule {

    @ContributesAndroidInjector
    abstract ListSearchResultActivity listSearchResultActivity();

    @ContributesAndroidInjector
    abstract UserSearchHistoryActivity userSearchHistoryActivity();
}
