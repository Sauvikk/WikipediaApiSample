package wikipedia.demo.wikipediasample.di;

import android.app.Application;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;
import wikipedia.demo.wikipediasample.WikipediaSampleApplication;


@Singleton
@Component(modules = {
        AppModule.class,
        ActivityBindingModule.class,
        AndroidSupportInjectionModule.class,
})
public interface AppComponent extends AndroidInjector<WikipediaSampleApplication> {
    @Component.Builder
    interface Builder {
        @BindsInstance
        AppComponent.Builder application(Application application);

        AppComponent build();
    }
}
