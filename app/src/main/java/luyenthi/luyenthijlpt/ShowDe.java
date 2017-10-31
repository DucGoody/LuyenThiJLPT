package luyenthi.luyenthijlpt;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class ShowDe extends AppCompatActivity {
    private ThuocTinh thuocTinh;
    private DatabaseHelper databaseHelper;
    private ArrayList<ThuocTinh>arrayList;
    private DeAdapter adapter;
    private ListView lv;
    private String capdo,kynnag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_de);
        lv=(ListView)findViewById(R.id.lvDe);
        arrayList=new ArrayList<>();
        databaseHelper=new DatabaseHelper(this);
        databaseHelper.copyDB();
     // dùng Bundle để bắt dữ liệu từ activity trước chuyển tới
        Bundle bundle=getIntent().getExtras();
        capdo=bundle.getString("capdo2");
        kynnag=bundle.getString("kynang");
        // gọi phím back ở toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // tên cho toolbar
        setTitle("Các đề - "+kynnag + " - "+capdo);
        show();
    }
    private void show(){
        String query = "SELECT MaCauHoi,CauHoi,DapAnA,DapAnB,DapAnC,DapAnD,DapAnDung,NoiDung, Script,TenDeThi,TenCapDo,TenKyNang,Anh  FROM CapDo,CauHoi,DeThi,KyNang\n" +
                "WHERE DeThi.MaDeThi=CauHoi.MaDe and DeThi.MaCapDo=CapDo.MaCapDo and DeThi.MaKyNang=KyNang.MaKyNang " +
                "AND CapDo.TenCapDo='"+capdo+"'  AND  KyNang.TenKyNang='"+kynnag+"' GROUP BY TenDeThi";
        // thực thị câu lệnh sql
        Cursor c=databaseHelper.getCursor(query);
        // gọi Cursor ở vị trí đầu tiên
        c.moveToFirst();
        // vòng lặp cho tới khi hết vị trí cuối cùng
        while (!c.isAfterLast()){
            // khởi tạo thuộc tính với tham số đã truyền vào tương ứng với các tham số ở câu lệnh sql trên
            thuocTinh = new ThuocTinh(c.getInt(0), c.getString(1), c.getString(2)
                    , c.getString(3), c.getString(4), c.getString(5), c.getString(6),
                    c.getString(7),c.getString(8),c.getString(9),c.getString(10),c.getString(11),
                    c.getString(12));
            // thêm thuoctinh vào arrayList
            arrayList.add(thuocTinh);
            // next cursor
            c.moveToNext();
        }
        //đóng
        c.close();
        // đổ dữ liệu vào lv thông qua adapter
        adapter=new DeAdapter(ShowDe.this,R.layout.item_chon_de,arrayList);
        // thiết lập adapter cho lv
        lv.setAdapter(adapter);
        // sự kiện chọn item trong listView
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                finish();
                // lấy ra mã đề, cấp độ, kỹ năng tại vị trị chọn
                String made2 = arrayList.get(position).getMade();
                String capdo2=arrayList.get(position).getCapDo();
                String kynang2=arrayList.get(position).getKyNang();
                if (kynang2.equals("Từ vựng")) {
                    // chuyển activity và di chuyển dữ liệu đã lấy ra theo
                    Intent intent = new Intent(getApplicationContext(), StartTest.class);
                    intent.putExtra("capdo3", capdo2);
                    intent.putExtra("kynang3", kynang2);
                    intent.putExtra("made3", made2);
                    startActivity(intent);
                }
                else if (kynang2.equals("Ngữ pháp")) {
                    Intent intent = new Intent(getApplicationContext(), StartTest2.class);
                    intent.putExtra("capdo3", capdo2);
                    intent.putExtra("kynang3", kynang2);
                    intent.putExtra("made3", made2);
                    startActivity(intent);
                }
                else   if (kynang2.equals("Đọc hiểu")) {
                    Intent intent = new Intent(getApplicationContext(), StartTestDocHieu.class);
                    intent.putExtra("capdo3", capdo2);
                    intent.putExtra("kynang3", kynang2);
                    intent.putExtra("made3", made2);
                    startActivity(intent);
                }
                else   if (kynang2.equals("Nghe hiểu")) {
                    Intent intent = new Intent(getApplicationContext(), StartTestNgheHieu.class);
                    intent.putExtra("capdo3", capdo2);
                    intent.putExtra("kynang3", kynang2);
                    intent.putExtra("made3", made2);
                    startActivity(intent);
                }
            }
        });

    }
    // sự kiện chọn để quay về
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }
}
