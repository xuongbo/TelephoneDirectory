package com.example.telephonedirectory.GetData;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.telephonedirectory.Activities.InfoTelephoneActivity;
import com.example.telephonedirectory.R;

import java.util.ArrayList;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> implements Filterable {

    public Context context;

    public List<Phone> phones;

    public List<Phone> phonesFiltered;

    public PhonesAdapterListener listener;

    public Adapter(List<Phone> phones) {
        this.phones = phones;
    }

    public Adapter(Context context, List<Phone> phones) {
        this.context = context;
        this.phones = phones;
        this.phonesFiltered = phones;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView name;
        public TextView phoneNumber;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.Name);
            phoneNumber = itemView.findViewById(R.id.phoneNumber);
            itemView.setOnClickListener(view -> listener.onPhoneSelected(phonesFiltered.
                    get(getAdapterPosition())));
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.layout_list_telephone,parent,false);
        return new ViewHolder(view);


    }

    @Override
    public void onBindViewHolder(@NonNull Adapter.ViewHolder holder, int position) {
        Phone phone = phonesFiltered.get(position);
        holder.name.setText(phone.getName());
        holder.phoneNumber.setText(String.valueOf(phone.getPhoneNumber()));
        holder.itemView.setOnClickListener(view -> {
            String[] data = new String[2];
            data[0] = phone.getName();
            data[1] = phone.getPhoneNumber();
            Context context = view.getContext();
            Intent intent = new Intent(context, InfoTelephoneActivity.class);
            intent.putExtra("data",data);
            intent.putExtra("position",position);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return phonesFiltered.size();

    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence text) {
                String string = text.toString();
                if (string.isEmpty()){
                    phonesFiltered = phones;
                }else {
                    List<Phone> filteredList = new ArrayList<>();
                    for(Phone item: phones){
                        if(item.getName().toLowerCase().contains(string) || item.getPhoneNumber().contains(text)){
                            filteredList.add(item);
                        }
                    }
                    phonesFiltered = filteredList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = phonesFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                phonesFiltered = (ArrayList<Phone>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
    public interface PhonesAdapterListener {
        void onPhoneSelected(Phone phone);
    }

}
