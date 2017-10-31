package luyenthi.luyenthijlpt;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.Handler;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class StartTestNgheHieu extends AppCompatActivity {
    private String n, kn;
    private LinearLayout l1, llresult;
    private ImageView imgMenu, imgBack, imgNext, imgAnh, imgloa;
    private int tongs;
    private TextView txtvTime;
    private TextView txtvCurrent2, txtvDiem;
    private Button btn1, btn2, btn3, btn4, btnSubmit;
    private DatabaseHelper db;
    private ThuocTinh thuocTinh;
    private List<String> listCauHoi;
    private CountDownTimer mCountDownTimer;
    private ListView lv;
    private ImageView imga, imgb, imgc, imgd;
    private int i = 0;
    public TextToSpeech textToSpeech;
    private String made;
    private int id;
    private String da1, da2, da3, da4, da,speak;
    private Handler handler = new Handler();
    private ArrayList<ThuocTinh> arrayList2;
    private int i2 = 1;
    private int diem = 0;
    // hiển thị kết quả
    private ListView lvResult;
    // khai báo img để thi bằng giọng nói
    private TextView txtvNoi;
    private final int REQ_CODE_SPEECH_INPUT = 100;
//    private static int save = -1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_test_nghe_hieu);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Bundle bundle = getIntent().getExtras();
        n = bundle.getString("capdo3");
        kn = bundle.getString("kynang3");
        made = bundle.getString("made3");
        setTitle("Test " + kn + " - " + n + " đề " + made);
        db = new DatabaseHelper(this);
        db.copyDB();
        arrayList2 = new ArrayList<>();
        loadCauHoi();
        init2();

    }

    private void init2() {
        txtvTime = (TextView) findViewById(R.id.txtvTime4);
        btn1 = (Button) findViewById(R.id.btn33333333A);
        btn2 = (Button) findViewById(R.id.btn33333333B);
        btn3 = (Button) findViewById(R.id.btn33333333C);
        btn4 = (Button) findViewById(R.id.btn33333333D);
        imgMenu = (ImageView) findViewById(R.id.imgMenu4);
        imgAnh = (ImageView) findViewById(R.id.img333333333);
        lv = (ListView) findViewById(R.id.lvMenu4);
        imgBack = (ImageView) findViewById(R.id.imgBack4);
        imgNext = (ImageView) findViewById(R.id.imgNext4);
        btnSubmit = (Button) findViewById(R.id.btnSubmit4);
        imga = (ImageView) findViewById(R.id.imgLoadA4);
        imgb = (ImageView) findViewById(R.id.imgLoadB4);
        imgc = (ImageView) findViewById(R.id.imgLoadC4);
        imgd = (ImageView) findViewById(R.id.imgLoadD4);
        l1 = (LinearLayout) findViewById(R.id.f4);
        txtvDiem = (TextView) findViewById(R.id.txtvdiem4);
        lvResult = (ListView) findViewById(R.id.lvResult14);
        llresult = (LinearLayout) findViewById(R.id.llResult14);
        txtvCurrent2 = (TextView) findViewById(R.id.txtvCurrent33333333);
        imgloa = (ImageView) findViewById(R.id.imgLoaScript2);

        txtvNoi=(TextView) findViewById(R.id.txtvNhanBiet4);
        toSpeak();
        setTime();
        getQ(i2);
        init();
    }

    private void init() {
        imga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    textToSpeech.speak(btn1.getText().toString(), TextToSpeech.QUEUE_FLUSH, null, null);
                }
            }
        });
        imgb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    textToSpeech.speak(btn2.getText().toString(), TextToSpeech.QUEUE_FLUSH, null, null);
                }
            }
        });
        imgc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    textToSpeech.speak(btn3.getText().toString(), TextToSpeech.QUEUE_FLUSH, null, null);
                }
            }
        });
        imgd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    textToSpeech.speak(btn4.getText().toString(), TextToSpeech.QUEUE_FLUSH, null, null);
                }
            }
        });
        imgNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stop();
                if (i2 < tongs) {
                    i2++;
                    getQ(i2);
                }
            }
        });
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stop();
                if (i2 > 1) {
                    i2--;
                    getQ(i2);
                }
            }
        });
        imgMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (i == 0) {
                    l1.setVisibility(View.VISIBLE);
                    i = 1;
                } else if (i == 1) {
                    l1.setVisibility(View.GONE);
                    i = 0;
                }
            }
        });
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewResult();
            }
        });
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>
                (this, android.R.layout.simple_list_item_1, listCauHoi) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView tv = (TextView) view.findViewById(android.R.id.text1);
                tv.setTextColor(Color.WHITE);
                return view;
            }
        };
        lv.setAdapter(adapter2);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                i2=position+1;
                getQ(i2);
                l1.setVisibility(View.GONE);

//                //nếu chọn thì sẽ thay đổi màu item đó
//                parent.getChildAt(position).setBackgroundColor(getResources().getColor(R.color.mau1));
//                // thiết lập các item còn lại là 1 màu
//                if (save != -1 && save != position){
//                    parent.getChildAt(save).setBackgroundColor(getResources().getColor(R.color.colorPrimary));
//                }
//
//                save = position;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        textToSpeech.shutdown();
        textToSpeech.stop();
        finish();
        Intent intent = new Intent(getApplicationContext(), ShowDe.class);
        intent.putExtra("capdo2", n);
        intent.putExtra("kynang", kn);
        startActivity(intent);
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        textToSpeech.shutdown();
        textToSpeech.stop();
        super.onBackPressed();
    }

    private void loadCauHoi() {
        arrayList2 = new ArrayList<>();
        listCauHoi = new ArrayList<>();
        String query = "SELECT MaCauHoi,CauHoi,DapAnA,DapAnB,DapAnC,DapAnD,DapAnDung,NoiDung, Script,TenDeThi,TenCapDo,TenKyNang,Anh,DapAn  FROM CapDo,CauHoi,DeThi,KyNang\n" +
                "WHERE DeThi.MaDeThi = CauHoi.MaDe and DeThi.MaCapDo = CapDo.MaCapDo and DeThi.MaKyNang = KyNang.MaKyNang AND CapDo.TenCapDo = '" + n + "'  AND  KyNang.TenKyNang = '" + kn + "' AND TenDeThi = '" + made + "'";
        Cursor cursor = db.getCursor(query);
        tongs = cursor.getCount();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            thuocTinh = new ThuocTinh(cursor.getInt(0), cursor.getString(1), cursor.getString(2)
                    , cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6),
                    cursor.getString(7), cursor.getString(8), cursor.getString(9), cursor.getString(10), cursor.getString(11),
                    cursor.getString(12),cursor.getString(13));
            arrayList2.add(thuocTinh);
            cursor.moveToNext();
        }
        cursor.close();
        db.close();
        for (int i2 = 0; i2 < tongs; i2++) {
            int length = i2 + 1;
            listCauHoi.add("Câu " + length);
        }

    }

    public void toSpeak() {
        textToSpeech = new TextToSpeech(StartTestNgheHieu.this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    textToSpeech.setLanguage(Locale.JAPANESE);
                }
            }
        });
    }

    private void speak(String tt) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            textToSpeech.speak(tt, TextToSpeech.QUEUE_FLUSH, null, null);
        }
    }
    private void stop(){
        textToSpeech.stop();
    }
    public void getQ(int index) {
        if (index > 0 && index <= tongs) {
            btn1.setBackgroundColor(getResources().getColor(R.color.mau_button));
            btn2.setBackgroundColor(getResources().getColor(R.color.mau_button));
            btn3.setBackgroundColor(getResources().getColor(R.color.mau_button));
            btn4.setBackgroundColor(getResources().getColor(R.color.mau_button));
            btn1.setEnabled(true);
            btn2.setEnabled(true);
            btn3.setEnabled(true);
            btn4.setEnabled(true);
            // Lấy ra câu hỏi trong arraylist và hiển thị lên màn hình.
            thuocTinh = arrayList2.get(index - 1);
            da1 = thuocTinh.getDaA();
            da2 = thuocTinh.getDaB();
            da3 = thuocTinh.getDaC();
            da4 = thuocTinh.getDaD();
            da = thuocTinh.getDaDung();
            speak = thuocTinh.getScript();
            speak(thuocTinh.getScript());
            String s=thuocTinh.getHinhAnh()+"";
            if (s.length()>4) {
                id = getResources().getIdentifier(thuocTinh.getHinhAnh(), "drawable", getPackageName());
                imgAnh.setImageResource(id);
                imgAnh.setVisibility(View.VISIBLE);
            }else {
                id = getResources().getIdentifier("cau27", "drawable", getPackageName());
                imgAnh.setImageResource(id);
                imgAnh.setVisibility(View.GONE);
            }

            txtvCurrent2.setText(index + "/" + tongs);
            btn1.setText(da1);
            btn2.setText(da2);
            btn3.setText(da3);
            btn4.setText(da4);
            speak(thuocTinh.getScript());
            imgloa.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    speak(thuocTinh.getScript());
                }
            });

            if (thuocTinh.getDa().equals(thuocTinh.getDaA())){
                btn1.setBackgroundColor(getResources().getColor(R.color.mau1));
                btn1.setEnabled(false);
            }
            if (thuocTinh.getDa().equals(thuocTinh.getDaB())){
                btn2.setBackgroundColor(getResources().getColor(R.color.mau1));
                btn2.setEnabled(false);
            }
            if (thuocTinh.getDa().equals(thuocTinh.getDaC())){
                btn3.setBackgroundColor(getResources().getColor(R.color.mau1));
                btn3.setEnabled(false);
            }
            if (thuocTinh.getDa().equals(thuocTinh.getDaD())) {
                btn4.setBackgroundColor(getResources().getColor(R.color.mau1));
                btn4.setEnabled(false);
            }
            btn1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    thuocTinh.setDa(da1);
                    btn1.setBackgroundColor(getResources().getColor(R.color.mau1));
                    stop();
                    if (da1.equals(da)) {
                        Snackbar.make(v, "Bạn đã trả lời đúng câu " +i2, Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                        diem += 1;
                    } else {
                        Snackbar.make(v, "Đáp án câu "+i2+" là: " + da, Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                    i2 += 1;
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            getQ(i2);
                        }
                    }, 500);

                }
            });
            btn2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    thuocTinh.setDa(da2);
                    btn2.setBackgroundColor(getResources().getColor(R.color.mau1));
                    stop();
                    if (da2.equals(da)) {
                        Snackbar.make(v, "Bạn đã trả lời đúng câu " +i2, Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                        diem += 1;
                    } else {
                        Snackbar.make(v, "Đáp án câu "+i2+" là: " + da, Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                    i2 += 1;
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            getQ(i2);
                        }
                    }, 500);
                }
            });
            btn3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    thuocTinh.setDa(da3);
                    btn3.setBackgroundColor(getResources().getColor(R.color.mau1));
                    stop();
                    if (da3.equals(da)) {
                        Snackbar.make(v, "Bạn đã trả lời đúng câu " +i2, Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                        diem += 1;
                    } else {
                        Snackbar.make(v, "Đáp án câu "+i2+" là: " + da, Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                    i2 += 1;
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            getQ(i2);
                        }
                    }, 500);
                }
            });
            btn4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    thuocTinh.setDa(da4);
                    btn4.setBackgroundColor(getResources().getColor(R.color.mau1));
                    stop();
                    if (da4.equals(da)) {
                        Snackbar.make(v, "Bạn đã trả lời đúng câu " +i2, Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                        diem += 1;
                    } else {
                        Snackbar.make(v, "Đáp án câu "+i2+" là: " + da, Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                    i2 += 1;
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            getQ(i2);
                        }
                    }, 500);
                }
            });

        }
    }


    private void setTime() {
        if (n.equalsIgnoreCase("N1") && kn.equalsIgnoreCase("Nghe hiểu")) {
            setCurrntTime(60);
        } else if (n.equalsIgnoreCase("N2") && kn.equalsIgnoreCase("Nghe hiểu")) {
            setCurrntTime(50);
        } else if (n.equalsIgnoreCase("N3") && kn.equalsIgnoreCase("Nghe hiểu")) {
            setCurrntTime(40);
        } else if (n.equalsIgnoreCase("N4") && kn.equalsIgnoreCase("Nghe hiểu")) {
            setCurrntTime(35);
        } else if (n.equalsIgnoreCase("N5") && kn.equalsIgnoreCase("Nghe hiểu")) {
            setCurrntTime(30);
        }

    }

    private void viewResult() {
        stop();
        llresult.setVisibility(View.VISIBLE);
        ResultAdapter4 adapter = new ResultAdapter4(this, R.layout.item_result4, arrayList2);
        lvResult.setAdapter(adapter);
        txtvDiem.setText("Điểm số: " + diem + "/" + tongs + " câu đúng.");
    }

    private void setCurrntTime(int time) {
        mCountDownTimer = new CountDownTimer(time * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                if ((millisUntilFinished / 1000) >= 10) {
                    txtvTime.setText(String.valueOf(millisUntilFinished / 1000));
                } else {
                    txtvTime.setTextColor(Color.GREEN);
                    txtvTime.setText(String.valueOf(millisUntilFinished / 1000));
                }
            }

            @Override
            public void onFinish() {
                viewResult();
            }
        }.start();
    }
    class ResultAdapter4 extends ArrayAdapter<ThuocTinh> {
        private int re;
        private Context context;
        private ArrayList<ThuocTinh> arrayList;


        @Override
        public int getCount() {
            return arrayList.size();
        }

        public ResultAdapter4(@NonNull Context context, @LayoutRes int resource, @NonNull List<ThuocTinh> objects) {
            super(context, resource, objects);
            this.re = resource;
            this.context = context;
            arrayList = (ArrayList<ThuocTinh>) objects;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View v = convertView;
            final ViewHolder viewHolder;
            if (v == null) {
                LayoutInflater inflater;
                inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = inflater.inflate(R.layout.item_result4, null);
                viewHolder = new ViewHolder();
                viewHolder.txtvsttch = (TextView) v.findViewById(R.id.textView5);
                viewHolder.imgCauhoi = (ImageView) v.findViewById(R.id.imageView4);
                viewHolder.txtvsttda = (TextView) v.findViewById(R.id.textView8);
                viewHolder.txtvda = (TextView) v.findViewById(R.id.textView7);
                viewHolder.txtvscript = (TextView) v.findViewById(R.id.txtvchuthich2222);
                viewHolder.txtvndscript = (TextView) v.findViewById(R.id.txtvScriptnd);
                viewHolder.imgloada = (ImageView) v.findViewById(R.id.imgloa4);
                viewHolder.imgLoaScipt=(ImageView)v.findViewById(R.id.imgloadScipt);
                v.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            final ThuocTinh song = arrayList.get(position);
            int c = position + 1;
            viewHolder.txtvsttch.setText("Câu " + c);
            viewHolder.txtvsttda.setText("ĐA:" + c);
            viewHolder.txtvda.setText(song.getDaDung());
            viewHolder.txtvscript.setText("Nội dung nghe " + c);
            viewHolder.txtvndscript.setText(song.getScript());
            String s=song.getHinhAnh()+"";
            if (s.length()>4) {
                id = getResources().getIdentifier(song.getHinhAnh(), "drawable", getPackageName());
                viewHolder.imgCauhoi.setImageResource(id);
                viewHolder.imgCauhoi.setVisibility(View.VISIBLE);
            }else {
                id = getResources().getIdentifier("cau27", "drawable", getPackageName());
                viewHolder.imgCauhoi.setImageResource(id);
                viewHolder.imgCauhoi.setVisibility(View.GONE);
            }
            viewHolder.imgloada.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!viewHolder.txtvda.getText().toString().equals(""))
                    speak(viewHolder.txtvda.getText().toString());
                }
            });
            viewHolder.imgLoaScipt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!viewHolder.txtvndscript.getText().toString().equals(""))
                    speak(viewHolder.txtvndscript.getText().toString());
                }
            });
            return v;
        }

        class ViewHolder {
            TextView txtvscript,txtvndscript;
            TextView txtvsttch;
            TextView txtvsttda, txtvda;
            ImageView imgCauhoi, imgloada,imgLoaScipt;

        }
    }
}
