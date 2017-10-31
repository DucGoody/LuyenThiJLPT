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
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class StartTest2 extends AppCompatActivity {
    private LinearLayout l1, l2, ll22;
    private ImageView imgMenu, imgBack, imgNext, imgAnh;
    private int tongs;
    private TextView txtvTime, txtvDiem;
    private TextView txtvCauHoi2, txtvCurrent, txtvNoiDungCauHoi;
    private Button btn1, btn2, btn3, btn4, btnSubmit;
    private DatabaseHelper db;
    private ThuocTinh thuocTinh;
    public TextToSpeech textToSpeech;
    private List<String> listCauHoi;
    private CountDownTimer mCountDownTimer;
    private ListView lv;
    private int i = 0;
    private String capdo, kynnag;
    private String made;
    private String da1, da2, da3, da4, da;
    private Handler handler = new Handler();
    private ArrayList<ThuocTinh> arrayList2;
    int i2 = 1;
    int diem = 0;
    private LinearLayout llResult12, ll3;
    private ListView lvresult;
    // khai báo img để thi bằng giọng nói
    private ImageView img;
    private TextView txtvNoi;
    private final int REQ_CODE_SPEECH_INPUT = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_test2);
        Bundle bundle = getIntent().getExtras();
        capdo = bundle.getString("capdo3");
        kynnag = bundle.getString("kynang3");
        made = bundle.getString("made3");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Test " + kynnag + " - " + capdo + " " + made);

        init();
        imgNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (i2 < tongs) {
                    i2++;
                    getQ(i2);
                }
            }
        });
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
        loadCauHoi();
        getQ(i2);
        setTime();
        toSpeak();

        for (int i2 = 0; i2 < tongs; i2++) {
            int length = i2 + 1;
            listCauHoi.add("Câu " + length);
        }

    }

    private void init() {
        txtvDiem = (TextView) findViewById(R.id.txtvdiem2);
        txtvTime = (TextView) findViewById(R.id.txtvTime2);
        txtvCauHoi2 = (TextView) findViewById(R.id.txtvCauHoi22);
        txtvCurrent = (TextView) findViewById(R.id.txtvCurrentCauHoi2);
        btn1 = (Button) findViewById(R.id.btnDaA2);
        btn2 = (Button) findViewById(R.id.btnDaB2);
        btn3 = (Button) findViewById(R.id.btnDaC2);
        btn4 = (Button) findViewById(R.id.btnDaD2);
        imgMenu = (ImageView) findViewById(R.id.imgMenu2);
        lv = (ListView) findViewById(R.id.lvMenu2);
        imgBack = (ImageView) findViewById(R.id.imgBack2);
        imgNext = (ImageView) findViewById(R.id.imgNext2);
        btnSubmit = (Button) findViewById(R.id.btnSubmit2);
        l1 = (LinearLayout) findViewById(R.id.f2);
        l2 = (LinearLayout) findViewById(R.id.d2);
        ll22 = (LinearLayout) findViewById(R.id.ll2);
        imgAnh = (ImageView) findViewById(R.id.imgAnh2);
        llResult12 = (LinearLayout) findViewById(R.id.llResult12);
        txtvNoiDungCauHoi = (TextView) findViewById(R.id.txtvNoiDungCauHoi2);
        lvresult = (ListView) findViewById(R.id.lvResult12);
        ll3 = (LinearLayout) findViewById(R.id.llnguphap);
        img = (ImageView) findViewById(R.id.imgNoi2);
        txtvNoi = (TextView) findViewById(R.id.txtvNhanBiet2);

        db = new DatabaseHelper(this);
        db.copyDB();
        arrayList2 = new ArrayList<>();

        // chọn loa thì cho phép nói
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                promptSpeechInput();
            }
        });
    }

    public void toSpeak() {
        textToSpeech = new TextToSpeech(StartTest2.this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    textToSpeech.setLanguage(Locale.getDefault());
                    textToSpeech.setLanguage(Locale.JAPANESE);
                }
            }
        });

    }

    // xử lý hiển thị màn hình cho phép nói
    private void promptSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.JAPANESE.toString());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                "Xin mời bạn nói");
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (Exception a) {
            Toast.makeText(getApplicationContext(),
                    "Thiết bị của bạn không hỗ trợ nhập liệu nói",
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    txtvNoi.setText(result.get(0));

                    if (!txtvNoi.getText().toString().equals("") && txtvNoi.getText().toString().equals(da)) {
                        diem += 1;
                        Toast.makeText(getApplicationContext(), "Chính xác", Toast.LENGTH_SHORT).show();
                        i2++;
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                getQ(i2);
                                txtvNoi.setText("");
                            }
                        }, 500);
                    } else {
                        Toast.makeText(getApplicationContext(), "Đáp án đúng là: " + da, Toast.LENGTH_SHORT).show();
                        i2++;
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                getQ(i2);
                                txtvNoi.setText("");
                            }
                        }, 500);
                    }
                }
                break;
            }

        }
    }

    @Override
    public void onBackPressed() {
        textToSpeech.shutdown();
        textToSpeech.stop();
        super.onBackPressed();
    }

    public void getQ(int index) {
        if (index > 0 && index <= tongs) {

            // Lấy ra câu hỏi trong arraylist và hiển thị lên màn hình.
            thuocTinh = arrayList2.get(index - 1);
            btn1.setBackgroundColor(getResources().getColor(R.color.mau_button));
            btn2.setBackgroundColor(getResources().getColor(R.color.mau_button));
            btn3.setBackgroundColor(getResources().getColor(R.color.mau_button));
            btn4.setBackgroundColor(getResources().getColor(R.color.mau_button));
            btn1.setEnabled(true);
            btn2.setEnabled(true);
            btn3.setEnabled(true);
            btn4.setEnabled(true);
            da1 = thuocTinh.getDaA();
            da2 = thuocTinh.getDaB();
            da3 = thuocTinh.getDaC();
            da4 = thuocTinh.getDaD();
            da = thuocTinh.getDaDung();
            // lấy ra câu hỏi và hình ảnh
            String a = thuocTinh.getCauHoi()+"a";
            String b = thuocTinh.getNoiDung() + "";
            String c = thuocTinh.getHinhAnh() + "";

            // kiểm tra nếu độ dài a>0 (tồn tại câu hỏi) và hình ảnh >4 (tồn tại hình ảnh)
            // hiển thị hình ảnh, nội dung và câu hỏi
            ll22.setVisibility(View.GONE);
            txtvNoiDungCauHoi.setVisibility(View.GONE);

            if (c.length() <= 4) {
                int id = getResources().getIdentifier("cau27", "drawable", getPackageName());
                imgAnh.setImageResource(id);
                imgAnh.setVisibility(View.GONE);
            } else {
                int id = getResources().getIdentifier(thuocTinh.getHinhAnh(), "drawable", getPackageName());
                imgAnh.setImageResource(id);
                imgAnh.setVisibility(View.VISIBLE);
            }
           if (a.length() > 5) {
                txtvCauHoi2.setText(thuocTinh.getCauHoi());
               txtvCauHoi2.setVisibility(View.VISIBLE);
                ll3.setVisibility(View.VISIBLE);
            }else if (a.length()<=5){
               txtvCauHoi2.setText("A");
               txtvCauHoi2.setVisibility(View.GONE);
           }
            if (txtvCauHoi2.getText().equals("A") && c.length() <= 4) {
                ll3.setVisibility(View.GONE);
            }
            if (b.length() <= 4) {
                txtvNoiDungCauHoi.setText("A");
                txtvNoiDungCauHoi.setVisibility(View.GONE);
                ll22.setVisibility(View.GONE);

            } else if (b.length() > 4) {
                txtvNoiDungCauHoi.setText(thuocTinh.getNoiDung());
                txtvNoiDungCauHoi.setVisibility(View.VISIBLE);
                ll22.setVisibility(View.VISIBLE);
            }
            // hiển thị vị trí câu hỏi
            txtvCurrent.setText(index + "/" + tongs);
            btn1.setText(da1);
            btn2.setText(da2);
            btn3.setText(da3);
            btn4.setText(da4);
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
            if (thuocTinh.getDa().equals(thuocTinh.getDaD())){
                btn4.setBackgroundColor(getResources().getColor(R.color.mau1));
                btn4.setEnabled(false);
            }

            btn1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    thuocTinh.setDa(da1);
                    btn1.setBackgroundColor(getResources().getColor(R.color.mau1));
                    i2 += 1;
                    if (da1.equals(da)) {
                        Snackbar.make(v, "Bạn đã trả lời đúng câu " +i2, Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                        diem += 1;
                    } else {
                        Snackbar.make(v, "Đáp án câu "+i2+" là: " + da, Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
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
                    i2 += 1;
                    if (da2.equals(da)) {
                        Snackbar.make(v, "Bạn đã trả lời đúng câu " +i2, Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                        diem += 1;
                    } else {
                        Snackbar.make(v, "Đáp án câu "+i2+" là: " + da, Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
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
                    i2 += 1;
                    if (da3.equals(da)) {
                        Snackbar.make(v, "Bạn đã trả lời đúng câu "+i2, Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                        diem += 1;
                    } else {
                        Snackbar.make(v, "Đáp án câu "+i2+" là: " + da, Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
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
                    i2 += 1;
                    if (da4.equals(da)) {
                        Snackbar.make(v, "Bạn đã trả lời đúng câu "+i2, Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                        diem += 1;
                    } else {
                        Snackbar.make(v, "Đáp án câu "+i2+" là: " + da, Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }

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

    private void loadCauHoi() {
        arrayList2 = new ArrayList<>();
        listCauHoi = new ArrayList<>();

        String query = "SELECT MaCauHoi,CauHoi,DapAnA,DapAnB,DapAnC,DapAnD,DapAnDung,NoiDung, Script,TenDeThi,TenCapDo,TenKyNang,Anh,DapAn  FROM CapDo,CauHoi,DeThi,KyNang\n" +
                "WHERE DeThi.MaDeThi=CauHoi.MaDe and DeThi.MaCapDo = CapDo.MaCapDo and DeThi.MaKyNang = KyNang.MaKyNang and CapDo.TenCapDo='" + capdo + "' AND  KyNang.TenKyNang = '" + kynnag + "' AND TenDeThi='" + made + "'";
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
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, listCauHoi);
        lv.setAdapter(adapter2);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                i2 = position + 1;
                getQ(i2);
                i = 0;
                l1.setVisibility(View.GONE);

            }
        });
    }

    private void setTime() {
        if (capdo.equalsIgnoreCase("N1") && kynnag.equalsIgnoreCase("Ngữ pháp")) {
            setCurrntTime(35);
        } else if (capdo.equalsIgnoreCase("N2") && kynnag.equalsIgnoreCase("Ngữ pháp")) {
            setCurrntTime(35);
        } else if (capdo.equalsIgnoreCase("N3") && kynnag.equalsIgnoreCase("Ngữ pháp")) {
            setCurrntTime(35);
        } else if (capdo.equalsIgnoreCase("N4") && kynnag.equalsIgnoreCase("Ngữ pháp")) {
            setCurrntTime(30);
        } else if (capdo.equalsIgnoreCase("N5") && kynnag.equalsIgnoreCase("Ngữ pháp")) {
            setCurrntTime(25);
        }

    }

    private void setCurrntTime(int time) {
        mCountDownTimer = new CountDownTimer(time * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                if ((millisUntilFinished / 1000) <= 10) {
                    txtvTime.setTextColor(Color.GREEN);
                    txtvTime.setText(String.valueOf(millisUntilFinished / 1000));

                } else if ((millisUntilFinished / 1000) > 10) {
                    txtvTime.setText(String.valueOf(millisUntilFinished / 1000));
                }
            }

            @Override
            public void onFinish() {
                viewResult();
            }
        }.start();
    }

    private void viewResult() {
        llResult12.setVisibility(View.VISIBLE);
        ResultAdapter adapter = new ResultAdapter(getApplicationContext(), R.layout.item_result, arrayList2);
        lvresult.setAdapter(adapter);
        txtvDiem.setText("Điểm số: " + diem + "/" + tongs + " câu đúng.");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        textToSpeech.stop();
        textToSpeech.shutdown();
        Intent intent = new Intent(getApplicationContext(), ShowDe.class);
        intent.putExtra("capdo2", capdo);
        intent.putExtra("kynang", kynnag);
        startActivity(intent);
        return super.onOptionsItemSelected(item);
    }

    class ResultAdapter extends ArrayAdapter<ThuocTinh> {
        private int re;
        private Context context;
        private ArrayList<ThuocTinh> arrayList;


        @Override
        public int getCount() {
            return arrayList.size();
        }

        public ResultAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<ThuocTinh> objects) {
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
                v = inflater.inflate(R.layout.item_result, null);
                viewHolder = new ViewHolder();
                viewHolder.txtvsttch = (TextView) v.findViewById(R.id.txtvSTT2);
                viewHolder.txtvndch = (TextView) v.findViewById(R.id.txtvND2);
                viewHolder.imgloach = (ImageView) v.findViewById(R.id.imgLoaCH2);
                viewHolder.txtvsttda = (TextView) v.findViewById(R.id.txtvSTTDA2);
                viewHolder.txtvda = (TextView) v.findViewById(R.id.txtvDA2);
                viewHolder.imgloada = (ImageView) v.findViewById(R.id.imgLoadDA2);
                viewHolder.txtvsttND = (TextView) v.findViewById(R.id.txtvSTTND2);
                viewHolder.txtvndch2 = (TextView) v.findViewById(R.id.txtvNDch2);
                v.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            final ThuocTinh song = arrayList.get(position);
            int c = position + 1;
            viewHolder.txtvsttch.setText("Câu " + c);
            viewHolder.txtvsttND.setText("Nội dung " + c);
            viewHolder.txtvsttda.setText("Đáp án " + c);
            viewHolder.txtvndch.setText(song.getCauHoi());
            viewHolder.txtvda.setText(song.getDaDung());
            viewHolder.txtvndch2.setText(song.getNoiDung());
            viewHolder.imgloach.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // nếu câu hỏi khác null thì sẽ phát câu hỏi
                    if (!viewHolder.txtvndch.getText().toString().equals("")) {

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            textToSpeech.speak(viewHolder.txtvndch.getText().toString(), TextToSpeech.QUEUE_FLUSH, null, null);

                        }
                        // nếu nội dung khác null thì sẽ phát câu hỏi
                    } else if (!viewHolder.txtvndch2.getText().toString().equals("")) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            textToSpeech.speak(viewHolder.txtvndch2.getText().toString(), TextToSpeech.QUEUE_FLUSH, null, null);

                        }
                        // ngược lại sẽ phát câu hỏi
                    } else {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            textToSpeech.speak(viewHolder.txtvndch.getText().toString(), TextToSpeech.QUEUE_FLUSH, null, null);

                        }
                    }
                }
            });
            // sự kiện chọn
            viewHolder.imgloada.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // phát âm thanh đá áp
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        textToSpeech.speak(viewHolder.txtvda.getText().toString(), TextToSpeech.QUEUE_FLUSH, null, null);
                    }
                }
            });


            return v;
        }

        class ViewHolder {
            TextView txtvsttch, txtvndch;
            TextView txtvsttND, txtvndch2;
            TextView txtvsttda, txtvda;
            ImageView imgloach, imgloada;

        }
    }
}
