package com.example.telephonedirectory.GetData;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.telephonedirectory.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SearchAdapter extends BaseAdapter {

    Context context;
    LayoutInflater layoutInflater;
    private ArrayList<Phone> phoneArrayList;
    private List<Phone> phones = null;

    public SearchAdapter(Context context, List<Phone> phones) {
        this.context = context;
        this.phones = phones;
        layoutInflater = LayoutInflater.from(context);
        this.phoneArrayList = new ArrayList<>();
        this.phoneArrayList.addAll(phones);
    }
    public class ViewHolder {
        TextView name;
    }

    @Override
    public int getCount() {
        return phones.size();
    }

    @Override
    public Object getItem(int i) {
        return phones.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        final ViewHolder holder;
        if (view == null){
            holder = new ViewHolder();
            view = layoutInflater.inflate(R.layout.layout_list_telephone,null);

            holder.name = view.findViewById(R.id.contactName);
            view.setTag(holder);

        }else holder = (ViewHolder) view.getTag();

        holder.name.setText(phones.get(position).getName());
        return view;
    }
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        phones.clear();
        if (charText.length() == 0) {
            phoneArrayList.addAll(phones);
        } else {
            for (Phone phone : phones) {
                if (phone.getName().toLowerCase(Locale.getDefault()).contains(charText)) {
                    phones.add(phone);
                }
            }
        }
        notifyDataSetChanged();
    }
}
