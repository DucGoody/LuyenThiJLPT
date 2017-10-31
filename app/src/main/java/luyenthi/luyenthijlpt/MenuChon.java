package luyenthi.luyenthijlpt;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MenuChon extends AppCompatActivity {
    // khai báo cardView, intent, Bundle,capdo
    private CardView c1,c2,c3,c4;
    private Intent intent;
    private Bundle bundle;
    private String capdo="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_chon);
        // gọi mũi tên quay trở về ở toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        anhXa();
        // dùng bundle để bắt lại dữ liệu
        bundle=getIntent().getExtras();
        capdo=bundle.getString("capdo");
        // thiết lập tiêu đề cho toolbar
        setTitle("Chọn phần thi - "+capdo);
    }

    // phương thức tham chiều tới CarView trong activity_menu_chon
    // xử lý sự kiện chọn
    private void anhXa(){
        c1=(CardView)findViewById(R.id.cTuVung);
        c2=(CardView)findViewById(R.id.cNguPhap);
        c3=(CardView)findViewById(R.id.cDocHieu);
        c4=(CardView)findViewById(R.id.cNgheHieu);
        c1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // chuyển activity và bắn dữ liệu (từ vựng, cấp độ) qua activity ShowDe
                intent=new Intent(MenuChon.this,ShowDe.class);
                intent.putExtra("kynang","Từ vựng");
                intent.putExtra("capdo2",capdo);
                startActivity(intent);
            }
        });
        c2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent=new Intent(MenuChon.this,ShowDe.class);
                    intent.putExtra("kynang","Ngữ pháp");
                intent.putExtra("capdo2",capdo);
                startActivity(intent);
            }
        });
        c3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent=new Intent(MenuChon.this,ShowDe.class);
                intent.putExtra("kynang","Đọc hiểu");
                intent.putExtra("capdo2",capdo);
                startActivity(intent);
            }
        });
        c4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent=new Intent(MenuChon.this,ShowDe.class);
                intent.putExtra("kynang","Nghe hiểu");
                intent.putExtra("capdo2",capdo);
                startActivity(intent);
            }
        });
    }


    // sự kiện chọn để quay lại
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }
}
