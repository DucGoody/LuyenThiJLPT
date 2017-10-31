package luyenthi.luyenthijlpt;


import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;



public class TLTKFragment extends Fragment {
    private View v;
    private GridView gv;
    private TLTKAdapter adapter;
    private ArrayList<ThuocTinhSach>arrayList;
    private DatabaseHelper db;
    public TLTKFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v=inflater.inflate(R.layout.fragment_tltk, container, false);
        gv=(GridView )v.findViewById(R.id.gv);
        db=new DatabaseHelper(getActivity());
        arrayList=new ArrayList<>();
        db.copyDB();
        hienthi();
        return v;
    }
    private void hienthi(){
        String q="SELECT * FROM SachThamKhao";
        Cursor c=db.getCursor(q);
        c.moveToFirst();
        while (!c.isAfterLast()){
            ThuocTinhSach tt=new ThuocTinhSach(c.getInt(0),c.getString(1),c.getString(2),c.getString(3));
            arrayList.add(tt);
            c.moveToNext();
        }
        c.close();
        adapter=new TLTKAdapter(getActivity(),R.layout.custom_gridview,arrayList);
        gv.setAdapter(adapter);
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent s=new Intent(getActivity(),ShowTaiLieu.class);
                s.putExtra("link",arrayList.get(position).getLink());
                s.putExtra("ten",arrayList.get(position).getTen());
                startActivity(s);
            }
        });
    }
    class TLTKAdapter extends ArrayAdapter<ThuocTinhSach> {
        private int re;
        private Context context;
        private ArrayList<ThuocTinhSach> arrayList;


        @Override
        public int getCount() {
            return arrayList.size();
        }

        public TLTKAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<ThuocTinhSach> objects) {
            super(context, resource, objects);
            this.re = resource;
            this.context = context;
            arrayList = (ArrayList<ThuocTinhSach>) objects;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View v = convertView;
            final ViewHolder viewHolder;
            if (v == null) {
                LayoutInflater inflater;
                inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = inflater.inflate(R.layout.custom_gridview, null);
                viewHolder = new ViewHolder();
                viewHolder.txtv = (TextView) v.findViewById(R.id.textView10);
                viewHolder.img = (ImageView) v.findViewById(R.id.imageView56);
                viewHolder.txtvlink=(TextView)v.findViewById(R.id.txtvlink);
                v.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            final ThuocTinhSach song = arrayList.get(position);
            viewHolder.txtv.setText(song.getTen());
            int id2 = getResources().getIdentifier(song.getHinhanh(), "drawable", getActivity().getPackageName());
            viewHolder.img.setImageResource(id2);
            viewHolder.txtvlink.setText(song.getLink());
            return v;
        }

        class ViewHolder {
            TextView txtv,txtvlink;
            ImageView img;

        }
    }
}
