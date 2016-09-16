package rss.feed.reader.dagger.components;

import android.app.Activity;

import dagger.Component;
import rss.feed.reader.dagger.modules.ActivityModule;
import rss.feed.reader.dagger.scopes.ActivityScope;

/**
 * Created by Orest Guziy on 16.09.16.
 */
@ActivityScope
@Component(dependencies = {AppComponent.class}, modules = {ActivityModule.class})
public interface ActivityComponent {

    Activity activity();

}
