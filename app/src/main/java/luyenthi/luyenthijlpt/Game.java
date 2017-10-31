package luyenthi.luyenthijlpt;

import android.content.DialogInterface;
import android.database.Cursor;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class Game extends AppCompatActivity {
    private ProgressBar progressBar;
    private TextView txtvTime,txtvCurrent,txtvTrue,txtvFalse,txtvCauHoi,txtvNoiDung,txtvdiem;
    private Button btn1,btn2,btn3,btn4;
    private LinearLayout ll1,ll2;
    private ImageView imgAnh;
    private CountDownTimer countDownTimer;
    private int dung=0,sai=0,current=1,diem=0;
    private String da1,da2,da3,da4,da;
    private int id;
    private Handler handler=new Handler();
    //
    private DatabaseHelper db;
    private ArrayList<String>arrayList;
    private ThuocTinh thuocTinh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Quiz game");
        init();
        click();
        getCauHoi();
    }
    private void init(){
        progressBar=(ProgressBar)findViewById(R.id.progressbar);
        txtvCauHoi=(TextView)findViewById(R.id.txtvCauHoigame);
        txtvNoiDung=(TextView)findViewById(R.id.txtvNoiDungCauHoigame);
        txtvTime=(TextView)findViewById(R.id.txtvTimegame);
        txtvTrue=(TextView)findViewById(R.id.txtvdung);
        txtvFalse=(TextView)findViewById(R.id.txtvsai);
        txtvCurrent=(TextView)findViewById(R.id.txtvCurrent);
        txtvdiem=(TextView)findViewById(R.id.txtvdiem);
        btn1=(Button)findViewById(R.id.btnDaAgame);
        btn2=(Button)findViewById(R.id.btnDaBgame);
        btn3=(Button)findViewById(R.id.btnDaCgame);
        btn4=(Button)findViewById(R.id.btnDaDgame);
        imgAnh=(ImageView)findViewById(R.id.imgAnhgame);
        ll1=(LinearLayout)findViewById(R.id.llgame1);
        ll2=(LinearLayout)findViewById(R.id.llgame2);
        db=new DatabaseHelper(this);
        arrayList=new ArrayList<>();

    }
    private void click(){
        txtvTrue.setText(dung+"");
        txtvFalse.setText(sai+"");
        txtvdiem.setText(diem+"");
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countDownTimer.cancel();
                if (da1.equals(da)) {
                    dung++;
                    diem+=5;
                    txtvdiem.setText(diem+"");
                    txtvTrue.setText(dung + "");
                } else {
                    sai++;
                    txtvFalse.setText(sai + "");
                }
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getCauHoi();
                    }
                },500);

            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countDownTimer.cancel();
                if (da2.equals(da)) {
                    dung++;
                    diem+=5;
                    txtvdiem.setText(diem+"");
                    txtvTrue.setText(dung + "");
                } else {
                    sai++;
                    txtvFalse.setText(sai + "");
                }
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getCauHoi();
                    }
                },500);
            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countDownTimer.cancel();
                if (da3.equals(da)) {
                    dung++;
                    diem+=5;
                    txtvdiem.setText(diem+"");
                    txtvTrue.setText(dung + "");
                } else {
                    sai++;
                    txtvFalse.setText(sai + "");
                }
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getCauHoi();
                    }
                },500);
            }
        });
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countDownTimer.cancel();
                if (da4.equals(da)) {
                    dung++;
                    diem+=5;
                    txtvdiem.setText(diem+"");
                    txtvTrue.setText(dung + "");
                } else {
                    sai++;
                    txtvFalse.setText(sai + "");
                }
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getCauHoi();
                    }
                },500);
            }
        });
    }
    private void getCauHoi() {
        String query = "SELECT MaCauHoi,CauHoi,DapAnA,DapAnB,DapAnC,DapAnD,DapAnDung,NoiDung, Script,TenDeThi,TenCapDo,TenKyNang,Anh  FROM CapDo,CauHoi,DeThi,KyNang\n" +
                "WHERE DeThi.MaDeThi = CauHoi.MaDe and DeThi.MaCapDo = CapDo.MaCapDo and DeThi.MaKyNang = KyNang.MaKyNang AND KyNang.TenKyNang = 'Từ vựng' OR KyNang.TenKyNang = 'ngữ pháp' OR KyNang.TenKyNang = 'Đọc hiểu' AND Anh IS Null";
        Cursor cursor = db.getCursor(query);
        int tongs=cursor.getCount();
        if (current < 20) {
            Random random = new Random();
            current++;
            txtvCurrent.setText(current + "/20");
            int vitrich = 0;
            do {
                vitrich = random.nextInt(tongs);
                cursor.moveToPosition(vitrich);
                thuocTinh = new ThuocTinh(cursor.getInt(0), cursor.getString(1), cursor.getString(2)
                        , cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6),
                        cursor.getString(7), cursor.getString(8), cursor.getString(9), cursor.getString(10), cursor.getString(11),
                        cursor.getString(12));
            } while (arrayList.contains(String.valueOf(thuocTinh.getMaCauHoi())));
            arrayList.add(String.valueOf(thuocTinh.getMaCauHoi()));
            txtvCauHoi.setText(cursor.getString(1));
            // lấy ra câu hỏi và hình ảnh
            String a = thuocTinh.getCauHoi()+"";
            String b= thuocTinh.getNoiDung() + "";

            imgAnh.setVisibility(View.GONE);
            // kiểm tra nếu độ dài a>0 (tồn tại câu hỏi) và hình ảnh >4 (tồn tại hình ảnh)
            // hiển thị hình ảnh, nội dung và câu hỏi
            if (a.length() > 0 && b.length()<=4) {
                txtvCauHoi.setText(thuocTinh.getCauHoi());
                txtvNoiDung.setText(thuocTinh.getNoiDung());
                // hiển thị control
                txtvCauHoi.setVisibility(View.VISIBLE);
                ll2.setVisibility(View.VISIBLE);
                txtvNoiDung.setVisibility(View.VISIBLE);
                // kiểm tra nếu độ dài a<=0 ( không tồn tại câu hỏi) và hình ảnh >4 (tồn tại hình ảnh)
                // hiển thị hình ảnh, nội dung
            } else if (a.length() <= 0 && b.length()>4 ) {
                txtvCauHoi.setText(a);
                txtvNoiDung.setText(thuocTinh.getNoiDung());
                ll1.setVisibility(View.VISIBLE);
                // ẩn đi control hiển thị câu hỏi
                txtvCauHoi.setVisibility(View.VISIBLE);
                ll2.setVisibility(View.VISIBLE);
                txtvNoiDung.setVisibility(View.VISIBLE);

            } else  {
                txtvCauHoi.setText(thuocTinh.getCauHoi());
                txtvNoiDung.setText(thuocTinh.getNoiDung());
                txtvCauHoi.setVisibility(View.VISIBLE);
                ll1.setVisibility(View.VISIBLE);
                ll2.setVisibility(View.VISIBLE);
                txtvNoiDung.setVisibility(View.VISIBLE);
            }

            // hiển thị vị trí câu hỏi
            da1=cursor.getString(2);
            da2=cursor.getString(3);
            da3=cursor.getString(4);
            da4=cursor.getString(5);
            da=cursor.getString(6);
            btn1.setText(da1);
            btn2.setText(da2);
            btn3.setText(da3);
            btn4.setText(da4);
            start();
        } else {
            android.app.AlertDialog.Builder dialog = new android.app.AlertDialog.Builder(this);
            dialog.setTitle("Hoàn thành.");
            dialog.setMessage("Điểm:"+diem);
            dialog.setPositiveButton("Đồng ý.", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    finish();
                }
            });
            dialog.create().show();

        }
    }
    private void start(){
        countDownTimer=new CountDownTimer(15000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                txtvTime.setText(millisUntilFinished/1000+"");
                int progress = (int) (millisUntilFinished/1000);
                progressBar.setProgress(progress);
            }

            @Override
            public void onFinish() {
                getCauHoi();
                sai++;
                txtvFalse.setText(sai + "");
            }
        }.start();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }
}
