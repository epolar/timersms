package xyz.eraise.timersms.tasks;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.FrameLayout;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import xyz.eraise.timersms.R;
import xyz.eraise.timersms.data.source.SMSRepository;
import xyz.eraise.timersms.data.source.local.LocalTasksDataSource;
import xyz.eraise.timersms.utils.ActivityUtils;

/**
 * 创建日期： 2016/4/14.
 */
public class TasksActivity extends AppCompatActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.content_frame)
    FrameLayout contentFrame;
    @Bind(R.id.btn_add_task)
    FloatingActionButton btnAddTask;
    @Bind(R.id.coordinator_layout)
    CoordinatorLayout coordinatorLayout;
    @Bind(R.id.nav_view)
    NavigationView navView;
    @Bind(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks);
        ButterKnife.bind(this);

        // 设置Toolbar
        setSupportActionBar(toolbar);
        ActionBar _actionBar = getSupportActionBar();
        _actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        _actionBar.setDisplayHomeAsUpEnabled(true);

        // 设置菜单栏显示
        drawerLayout.setStatusBarBackground(R.color.colorPrimary);
        if (null != navView) {
            setupNavView();
        }

        // 把 Fragment 添加到界面上
        TasksFragment _tasksFragment =
                (TasksFragment) getSupportFragmentManager().findFragmentById(R.id.content_frame);
        if (null == _tasksFragment) {
            _tasksFragment = new TasksFragment();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), _tasksFragment, R.id.content_frame);
        }

        _tasksFragment.setPresenter(new TasksPresenter(_tasksFragment, new SMSRepository(new LocalTasksDataSource(getApplicationContext()))));

    }

    private void setupNavView() {
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.list_navigation_menu_item:
                        break;
                    case R.id.statistics_navigation_menu_item:
                        // TODO: 2016/4/14 打开发送统计日志页面
                        break;
                    default:
                        break;
                }
                item.setChecked(true);
                drawerLayout.closeDrawers();
                return true;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.btn_add_task)
    public void onClick() {
    }
}
