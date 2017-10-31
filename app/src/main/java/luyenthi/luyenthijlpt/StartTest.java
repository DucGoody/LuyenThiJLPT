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
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
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

public class StartTest extends AppCompatActivity {

    private LinearLayout l2, llResult1;

    private ImageView imgMenu, imgBack, imgNext;

    private int tongs;

    private TextView txtvTime;

    private TextView txtvCauHoi2, txtvCurrent, txtvDiem;

    private Button btn1, btn2, btn3, btn4, btnSubmit;

    private DatabaseHelper db;

    private ThuocTinh thuocTinh;

    private List<String> listCauHoi;

    private CountDownTimer mCountDownTimer;

    private ListView lv;

    private int i = 0;
    // khai báo TextToSpeech nhân diện text để phát ra âm thanh
    public TextToSpeech textToSpeech;
    private String capdo, kynnag;

    private String made;

    private String da1, da2, da3, da4, da;
    // lớp xử lý thời gian VD: sau 0.5s chuyển câu khác
    private Handler handler = new Handler();
    private ArrayList<ThuocTinh> arrayList2;
    private int i2 = 1;

    private int diem = 0;
    // listView hiển thị kết quả
    private ListView lvResult1;
    // khai báo img để thi bằng giọng nói
    private ImageView img;
    private TextView txtvNoi;
    private final int REQ_CODE_SPEECH_INPUT = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_test);

        init();
        // next câu hỏi
        imgNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (i2 < tongs) {
                    i2++;
                    getQ(i2);
                }
            }
        });
        // trở lại câu hỏi trước
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (i2 > 1) {
                    i2--;
                    getQ(i2);
                }
            }
        });
        // hiển thị và ẩn menu
        imgMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (i == 0) {
                    l2.setVisibility(View.VISIBLE);
                    i = 1;
                } else if (i == 1) {
                    l2.setVisibility(View.GONE);
                    i = 0;
                }
            }
        });
        // khơi tạo lớp DatabaseHelper
        db = new DatabaseHelper(this);

        db.copyDB();
        // thực thi các phương thức
        loadCauHoi();
        getQ(i2);
        setTime();
        toSpeak();

        // duyệt và tạo list câu hỏi với số câu hỏi tương ứng với độ dài tongs
        for (int i2 = 0; i2 < tongs; i2++) {
            int length = i2 + 1;
            listCauHoi.add("Câu " + length);
        }

    }

    private void init() {
        // dùng bundle để bắt dữ liệu lại từ activity trước chuyển tới
        Bundle bundle = getIntent().getExtras();
        capdo = bundle.getString("capdo3");
        kynnag = bundle.getString("kynang3");
        made = bundle.getString("made3");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // thiết lập tiêu đề cho toolbar
        setTitle("Test " + kynnag + " - " + capdo + " " + made);
        // tham chiều các control tới các control trong layout activity_start_test
        txtvTime = (TextView) findViewById(R.id.txtvTime);
        txtvCauHoi2 = (TextView) findViewById(R.id.txtvCauHoi);
        txtvCurrent = (TextView) findViewById(R.id.txtvCurrentCauHoi);
        btn1 = (Button) findViewById(R.id.btnDaA);
        btn2 = (Button) findViewById(R.id.btnDaB);
        btn3 = (Button) findViewById(R.id.btnDaC);
        btn4 = (Button) findViewById(R.id.btnDaD);
        txtvDiem = (TextView) findViewById(R.id.txtvDiem11);
        imgMenu = (ImageView) findViewById(R.id.imgMenu);
        lv = (ListView) findViewById(R.id.lvMenuTuVung);
        imgBack = (ImageView) findViewById(R.id.imgBack);
        imgNext = (ImageView) findViewById(R.id.imgNext);
        btnSubmit = (Button) findViewById(R.id.btnSubmit);
        llResult1 = (LinearLayout) findViewById(R.id.llResult1);
        l2 = (LinearLayout) findViewById(R.id.llTuVung);
        lvResult1 = (ListView) findViewById(R.id.lvResult1);
        img=(ImageView)findViewById(R.id.imgNoi1);
        txtvNoi=(TextView) findViewById(R.id.txtvNhanBiet1);

        // khơi tạo danh sách chưa thuocTinh
        arrayList2 = new ArrayList<>();
        // sử kiện chọn nút submit
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewResult();
            }
        });

        // chọn loa thì cho phép nói
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                promptSpeechInput();
            }
        });
    }

    // phương thức khởi tạo textToSpeak
    public void toSpeak() {
        textToSpeech = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    // gán kiểu tiếng cho speak
                    textToSpeech.setLanguage(Locale.JAPANESE);
                }

            }
        });

    }
    @Override
    public void onBackPressed() {
        textToSpeech.shutdown();
        textToSpeech.stop();
        super.onBackPressed();
    }

    // phương thức hiển thị kết quả và điểm số
    private void viewResult() {
        // hiển thị layout kết quả
        llResult1.setVisibility(View.VISIBLE);
        // đổ dử liệu vào lisView thông qua ResultAdapter1
        ResultAdapter1 adapter = new ResultAdapter1(getApplicationContext(), R.layout.item_result, arrayList2);
        lvResult1.setAdapter(adapter);
        // hiển thị điểm
        txtvDiem.setText("Điểm số: " + diem + "/" + tongs + " câu đúng.");
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

            // lấy ra các đáp án
            da1 = thuocTinh.getDaA();
            da2 = thuocTinh.getDaB();
            da3 = thuocTinh.getDaC();
            da4 = thuocTinh.getDaD();
            da = thuocTinh.getDaDung();

            // hiển thị vị trí câu hỏi
            txtvCurrent.setText(index + "/" + tongs);

            // setText cho các button
            btn1.setText(da1);
            btn2.setText(da2);
            btn3.setText(da3);
            btn4.setText(da4);
            // hiển thị câu hỏi
            txtvCauHoi2.setText(thuocTinh.getCauHoi());

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

            // lần lượt xử lý chọn 4 đáp án
            btn1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    thuocTinh.setDa(da1);
                    btn1.setBackgroundColor(getResources().getColor(R.color.mau1));
                    if (da1.equals(da)) {
                        // show snackbar hiển thị câu tl
                        Snackbar.make(v, "Bạn đã trả lời đúng, câu " + i2, Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();

                        diem += 1;
                    } else {
                        Snackbar.make(v, "Đáp án câu "+i2+" là: " + da, Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                    // tăng vị tí câu hỏi lên 1
                    i2 ++;
                    // sau 0.5s sẽ load câu hỏi tiếp theo
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            // gọi phương thức get câu hỏi
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
                    if (da2.equals(da)) {
                        Snackbar.make(v, "Bạn đã trả lời đúng, câu " + i2, Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                        diem += 1;
                    } else {
                        Snackbar.make(v, "Đáp án câu "+i2+" là: " + da, Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                    i2 ++;
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
                    if (da3.equals(da)) {
                        Snackbar.make(v, "Bạn đã trả lời đúng, câu " + i2, Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                        diem += 1;
                    } else {
                        Snackbar.make(v, "Đáp án câu "+i2+" là: " + da, Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                    i2 ++;
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
                    if (da4.equals(da)) {
                        Snackbar.make(v, "Bạn đã trả lời đúng, câu " + i2, Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                        diem += 1;
                    } else {
                        Snackbar.make(v, "Đáp án câu "+i2+" là: " + da, Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                    i2++;
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
        listCauHoi = new ArrayList<>();
        String query = "SELECT MaCauHoi,CauHoi,DapAnA,DapAnB,DapAnC,DapAnD,DapAnDung,NoiDung, Script,TenDeThi,TenCapDo,TenKyNang,Anh,DapAn  FROM CapDo,CauHoi,DeThi,KyNang\n" +
                "WHERE DeThi.MaDeThi=CauHoi.MaDe and DeThi.MaCapDo = CapDo.MaCapDo and DeThi.MaKyNang = KyNang.MaKyNang AND CapDo.TenCapDo = '"+capdo+"' AND KyNang.TenKyNang='"+kynnag+"' AND TenDeThi='"+made+"'";


        Cursor cursor = db.getCursor(query);


        tongs = cursor.getCount();

        cursor.moveToFirst();
        // duyệt và lấy ra các thuộc tính tương ứng với các cột trong csdl
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
        //đổ dữ liệu vào listView thông qua ArrayAdapter
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, listCauHoi);
        lv.setAdapter(adapter2);
        // sự kiện chọn câu hỏi trong danh sách câu hỏi
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // lấy ra câu hỏi tại vị trí tương ứng
                i2=position+1;
                getQ(i2);
                i=0;
                l2.setVisibility(View.GONE);

            }
        });

    }

    // phương thức thiết lập time theo cấp độ
    private void setTime() {
        if (capdo.equalsIgnoreCase("N1") && kynnag.equalsIgnoreCase("Từ vựng")) {
            setCurrntTime(40);
        } else if (capdo.equalsIgnoreCase("N2") && kynnag.equalsIgnoreCase("Từ vựng")) {
            setCurrntTime(35);
        } else if (capdo.equalsIgnoreCase("N3") && kynnag.equalsIgnoreCase("Từ vựng")) {
            setCurrntTime(30);
        } else if (capdo.equalsIgnoreCase("N4") && kynnag.equalsIgnoreCase("Từ vựng")) {
            setCurrntTime(30);
        } else if (capdo.equalsIgnoreCase("N5") && kynnag.equalsIgnoreCase("Từ vựng")) {
            setCurrntTime(25);
        }
    }

    // cho time giảm
    private void setCurrntTime(int time) {
        mCountDownTimer = new CountDownTimer(time * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                // time <10s sẽ chuyển màu xanh
                if ((millisUntilFinished / 1000) <= 10) {
                    txtvTime.setTextColor(Color.GREEN);
                    txtvTime.setText(String.valueOf(millisUntilFinished / 1000));

                } else if ((millisUntilFinished / 1000) > 10) {
                    // còn ngược lại sẽ chữ trắng bt
                    txtvTime.setText(String.valueOf(millisUntilFinished / 1000));
                }
            }

            @Override
            public void onFinish() {
                // thời gian hết hiển thị kết quả
                viewResult();
            }
        }.start();
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

                    if (!txtvNoi.getText().toString().equals("") && txtvNoi.getText().toString().equals(da)){
                        diem += 1;
                        Toast.makeText(getApplicationContext(),"Chính xác",Toast.LENGTH_SHORT).show();
                        i2++;
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                getQ(i2);
                                txtvNoi.setText("");
                            }
                        }, 500);
                    }else {
                        Toast.makeText(getApplicationContext(),"Đáp án đúng là: "+da,Toast.LENGTH_SHORT).show();
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
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        finish();
        textToSpeech.stop();
        textToSpeech.shutdown();
        Intent intent = new Intent(getApplicationContext(), ShowDe.class);
        // đưa dữ liệu kèm theo cấp độ và kỹ năng
        intent.putExtra("capdo2", capdo);
        intent.putExtra("kynang", kynnag);
        startActivity(intent);
        return super.onOptionsItemSelected(item);
    }
    // lớp để custom listView kết quả
    class ResultAdapter1 extends ArrayAdapter<ThuocTinh> {
        //nhập id của layout
        private int re;

        private Context context;

        private ArrayList<ThuocTinh> arrayList;


        @Override
        public int getCount() {
            return arrayList.size();
        }

        // khởi tạo contructer
        public ResultAdapter1(@NonNull Context context, @LayoutRes int resource, @NonNull List<ThuocTinh> objects) {
            super(context, resource, objects);
            this.re = resource;
            this.context = context;
            arrayList = (ArrayList<ThuocTinh>) objects;
        }

        //
        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            // khai báo và gán view
            View v = convertView;

            final ViewHolder viewHolder;

            if (v == null) {

                LayoutInflater inflater;
                inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                v = inflater.inflate(R.layout.item_result, null);

                viewHolder = new ViewHolder();
                // tham chiếu các control tới control tương ứng trong item_result
                viewHolder.txtvsttch = (TextView) v.findViewById(R.id.txtvSTT2);
                viewHolder.txtvndch = (TextView) v.findViewById(R.id.txtvND2);
                viewHolder.imgloach = (ImageView) v.findViewById(R.id.imgLoaCH2);
                viewHolder.txtvsttda = (TextView) v.findViewById(R.id.txtvSTTDA2);
                viewHolder.txtvda = (TextView) v.findViewById(R.id.txtvDA2);
                viewHolder.imgloada = (ImageView) v.findViewById(R.id.imgLoadDA2);
                viewHolder.txtvsttND = (TextView) v.findViewById(R.id.txtvSTTND2);
                viewHolder.txtvndch2 = (TextView) v.findViewById(R.id.txtvNDch2);
                // setTag cho v
                v.setTag(viewHolder);
            } else {
                // nếu v khác null
                //convertView.getTag
                viewHolder = (ViewHolder) convertView.getTag();
            }
            // lấy ra đối tượng tại vị trí positin
            final ThuocTinh song = arrayList.get(position);

            int c = position + 1;

            viewHolder.txtvsttch.setText("Câu " + c);
            viewHolder.txtvsttND.setText("Nội dung ");

            viewHolder.txtvsttND.setVisibility(View.GONE);
            viewHolder.txtvsttda.setText("Đáp án " + c);
            // thiết lập câu hỏi cho txtvndch
            viewHolder.txtvndch.setText(song.getCauHoi());
            viewHolder.txtvda.setText(song.getDaDung());


            viewHolder.txtvndch2.setVisibility(View.GONE);
            // sự kiện chọn loa để phát ra âm thanh với text là viewHolder.txtvndch.getText().toString()
            viewHolder.imgloach.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!viewHolder.txtvndch.getText().toString().equals("")) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            textToSpeech.speak(viewHolder.txtvndch.getText().toString(), TextToSpeech.QUEUE_FLUSH, null, null);

                        }
                    }
                }
            });
            // sự kiện chọn loa để phát ra âm thanh với text là: viewHolder.txtvda.getText().toString()
            viewHolder.imgloada.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!viewHolder.txtvda.getText().toString().equals("")) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            textToSpeech.speak(viewHolder.txtvda.getText().toString(), TextToSpeech.QUEUE_FLUSH, null, null);
                        }
                    }
                }
            });


            return v;
        }

        // khai báo các control
        class ViewHolder {
            TextView txtvsttch, txtvndch;
            TextView txtvsttND, txtvndch2;
            TextView txtvsttda, txtvda;
            ImageView imgloach, imgloada;

        }
    }
}
