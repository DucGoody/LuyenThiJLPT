package luyenthi.luyenthijlpt;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;



public class DeAdapter extends ArrayAdapter<ThuocTinh> {
    private ArrayList<ThuocTinh>arrayList;

    private int res;

    private Context context1;

    // tạo contructer cho DeAdapter
    public DeAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<ThuocTinh> objects) {
        super(context, resource, objects);
        arrayList= (ArrayList<ThuocTinh>) objects;
        res=resource;
        context1=context;
    }
    // phương thức trả ra kích thước arrayList
    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        ViewHolder viewHolder;
        if (v == null) {
            // khởi tạo và gán lyaout item_chon_de
            LayoutInflater inflater;
            inflater = (LayoutInflater) context1.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.item_chon_de, null);
            viewHolder = new ViewHolder();
            // tham chiều tới textView hiển thị mã đề
            viewHolder.txtvDe = (TextView) v.findViewById(R.id.txtvMaDe);
            v.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        // gán giá trị cho textView hiển thị mã đề
        ThuocTinh ttbcc = arrayList.get(position);
        viewHolder.txtvDe.setText(ttbcc.getMade());
        return v;
    }

    static class ViewHolder {

        TextView txtvDe;
    }
}
