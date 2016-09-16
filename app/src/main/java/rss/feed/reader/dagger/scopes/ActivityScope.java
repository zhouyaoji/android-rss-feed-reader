package rss.feed.reader.dagger.scopes;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Custom Dagger 2 scope that is used to separate activity- and application-level dependencies
 * <p/>
 * Created by Orest Guziy on 16.09.16.
 */
@Scope
@Documented
@Retention(value = RetentionPolicy.RUNTIME)
public @interface ActivityScope {
}
