package luyenthi.luyenthijlpt;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.CardView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    // khởi tạo CardView
    private CardView cardView5, cardView1, cardView2, cardView3, cardView4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // tham chiều tới toolbar trong layout
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar,R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // tham chiếu tới CardView trong activity_main
        // và xử lý sự kiện chọn cardview
        cardView5 = (CardView) findViewById(R.id.cardView5);
        cardView5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                capDo2("N5");
            }
        });
        cardView4 = (CardView) findViewById(R.id.cardView4);
        cardView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                capDo2("N4");
            }
        });
        cardView3 = (CardView) findViewById(R.id.cardView3);
        cardView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                capDo2("N3");
            }
        });
        cardView2 = (CardView) findViewById(R.id.cardView2);
        cardView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                capDo2("N2");
            }
        });
        cardView1 = (CardView) findViewById(R.id.cardView1);
        cardView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                capDo2("N1");
            }
        });
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
// phương thức chuyển activity và đưa dữ liệu qua tới MenuCHon
    private void capDo2(String c) {
        Intent intent = new Intent(getApplicationContext(), MenuChon.class);
        intent.putExtra("capdo", c);
        startActivity(intent);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // chọn từng item trong menu
        int id = item.getItemId();
        switch (id) {
            case R.id.nav_n5:
                capDo2("N5");
                break;
            case R.id.nav_n4:
                capDo2("N4");
                break;
            case R.id.nav_n3:
                capDo2("N3");
                break;
            case R.id.nav_n2:
                capDo2("N2");
                break;
            case R.id.nav_n1:
                capDo2("N1");
                break;
            case R.id.nav_game:
                Intent intent3=new Intent(getApplicationContext(),Game.class);
                startActivity(intent3);
                break;
            case R.id.nav_cautucdethi:
                Intent intent=new Intent(getApplicationContext(),ShowCauTrucTaiLieu.class);
                startActivity(intent);
                break;
            case R.id.nav_chise:
                share();
                break;
            case R.id.nav_thongtinungdung:
                Intent intent2=new Intent(getApplicationContext(),TTUD.class);
                startActivity(intent2);
                break;
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    private void share() {
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Ứng dụng luyện thi JLPT!");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Ứng dụng rất hay giúp ích cho việc học tiếng nhật mọi người ủng hộ mình nhé.");
        startActivity(Intent.createChooser(sharingIntent, "Share"));
    }

}
