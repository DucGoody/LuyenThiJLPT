package luyenthi.luyenthijlpt;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;



public class DatabaseHelper extends SQLiteOpenHelper {

    private final Context context;

    private SQLiteDatabase db;

    private static final String DB_NAME="lt2.sqlite";

    private static final String DB_PATH="/data/data/luyenthi.luyenthijlpt/databases/"+DB_NAME;

    private static final int  VERSION =1;

    // phương thức khởi tạo 1 tham số
    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
        this.context=context;
    }

    // phương thức mở CSDL
    public SQLiteDatabase openDB(){
        db=SQLiteDatabase.openDatabase(DB_PATH,null,SQLiteDatabase.OPEN_READWRITE);
        return db;
    }

    public void closeDB(){
        db.close();
    }

    //thực hiện câu lệnh SQL
    public void excuteSQL(String q){
        openDB();
        db.execSQL(q);
        closeDB();
    }

    // thực hiện truy vấn trả về đối số
    public Cursor getCursor(String q){
        openDB();
        return  db.rawQuery(q,null);
    }

    //sao chép CSDL
    public void copyDB(){
        // biến kiểm tra db
        boolean check=false;
        try{

            File file=new File(DB_PATH);
            // nếu file rỗng
            check=file.exists();
            if (check){
                // đóng db
                this.close();
            }
            // nếu file tồn tại
            else if (!check){

                this.getReadableDatabase();

                // đọc dữ liệu trong db với link tới db
                InputStream input=context.getAssets().open(DB_NAME);

                String outFile=DB_PATH;

                OutputStream output=new FileOutputStream(outFile);

                byte[]buffer=new byte[1024];
                int length;
                // độ dài của đầu vào >0
                while((length=input.read(buffer))>0){
                    // ghi vào path
                    output.write(buffer,0,length);
                }
                output.flush();
                output.close();
                input.close();
            }
        }catch (Exception e){e.printStackTrace();}
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
