package wikipedia.demo.wikipediasample;

import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;
import wikipedia.demo.wikipediasample.di.DaggerAppComponent;

public class WikipediaSampleApplication extends DaggerApplication {

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerAppComponent.builder().application(this).build();
    }
}
