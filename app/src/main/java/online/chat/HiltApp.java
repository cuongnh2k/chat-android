package online.chat;

import android.app.Application;

import dagger.hilt.android.HiltAndroidApp;
import online.chat.utils.NotLoggingTree;
import timber.log.Timber;

/**
 * @author hieutt (tora262)
 */
@HiltAndroidApp
public class HiltApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        if (!BuildConfig.DEBUG) {
            Timber.plant(new NotLoggingTree());
        } else {
            Timber.plant(new Timber.DebugTree());
        }
    }
}
