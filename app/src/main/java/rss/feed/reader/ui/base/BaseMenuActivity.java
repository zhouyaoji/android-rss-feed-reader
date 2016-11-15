package rss.feed.reader.ui.base;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import rss.feed.reader.Navigation;
import rss.feed.reader.R;

/**
 * Base activity that should be used as parent if host activity has left-side menu.
 * Left-side menu implementation relies on {@link android.support.v4.widget.DrawerLayout}.
 * In order to use it, the host activity should match following condition:
 * - view is set in {@link android.support.v7.app.AppCompatActivity#onCreate(Bundle)} method.
 * - the root view is {@link android.support.v4.widget.DrawerLayout} view
 * - the root view matches all required condition from
 * https://developer.android.com/training/implementing-navigation/nav-drawer.html#DrawerLayout
 * - root view has drawer_layout id.
 * - view tree contains {@link NavigationView} element with left_side_menu id
 * that will be replaced with left-side menu view
 * <p>
 * Note: if action bar is NOT added, then menu item will be omitted. But if action bar is added
 * as toolbar then it should have base_toolbar id
 * <p>
 * Created by Orest Guziy on 16.10.16.
 */

public abstract class BaseMenuActivity extends BaseActivity {

    @BindView(R.id.drawer_layout)
    DrawerLayout mMenuLayout;

    @BindView(R.id.left_side_menu)
    NavigationView mMenuView;

    private ActionBarDrawerToggle mMenuToggle;

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        if (mMenuLayout == null) {
            throw new IllegalStateException("Activity must have DrawerLayout view");
        }

        if (mMenuView == null) {
            throw new IllegalStateException("Activity must have view with left_side_menu id");
        }

        initActionBarToggle();
        initNavigationDrawer();
    }

    private void initActionBarToggle() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            Toolbar toolbar = (Toolbar) findViewById(R.id.base_toolbar);

            mMenuToggle = new ActionBarDrawerToggle(this, mMenuLayout, toolbar,
                    R.string.menu_toggle_open, R.string.menu_toggle_close);

            mMenuLayout.addDrawerListener(mMenuToggle);

            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);

            mMenuToggle.syncState();
        }
    }

    private void initNavigationDrawer() {
        mMenuView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                return handleNavigation(item);
            }
        });

        mMenuLayout.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerOpened(View drawerView) {
                setUpNavigationViewContent();
            }
        });

        View headerView = mMenuView.getHeaderView(0);

        ImageView iconView = (ImageView) headerView.findViewById(R.id.menu_user_icon);
        iconView.setImageResource(R.drawable.ic_android_black_24dp);

        ImageView iconSmallView = (ImageView) headerView.findViewById(R.id.menu_user_icon_small);
        iconSmallView.setImageResource(R.drawable.ic_android_black_24dp);

        TextView usernameView = (TextView) headerView.findViewById(R.id.menu_user_name);
        usernameView.setText(R.string.menu_default_username);

        setUpNavigationViewContent();
    }

    private boolean handleNavigation(@NonNull MenuItem item) {
        if (item.getGroupId() == R.id.menu_news_list_item) {
            mMenuView.setCheckedItem(item.getItemId());
            onNewsItemSelected(item);
            mMenuLayout.closeDrawer(mMenuView, true);
            return true;
        }

        mMenuLayout.closeDrawer(mMenuView, true);
        int id = item.getItemId();
        switch (id) {
            case R.id.menu_all_news_item:
                loadAllNews();
                return true;
            case R.id.menu_settings_item:
                Navigation.toSettingsActivity(this);
                return true;
            case R.id.menu_help_item:
                return true;
            case R.id.menu_feedback_item:
                return true;
            default:
                return false;
        }
    }

    public abstract void onNewsItemSelected(@NonNull MenuItem item);

    public abstract void loadAllNews();

    // TODO: add updating menu instead of fully clearing it
    public abstract void setUpNavigationViewContent();

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (mMenuToggle != null) {
            mMenuToggle.onConfigurationChanged(newConfig);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mMenuToggle != null && mMenuToggle.onOptionsItemSelected(item)) {
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    protected Menu getMenu() {
        return mMenuView.getMenu();
    }
}
