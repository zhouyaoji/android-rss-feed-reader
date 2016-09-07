package rss.feed.reader.ui.activities;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import rss.feed.reader.R;
import rss.feed.reader.ui.base.BaseActivity;
import rss.feed.reader.utils.ImageUtils;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
