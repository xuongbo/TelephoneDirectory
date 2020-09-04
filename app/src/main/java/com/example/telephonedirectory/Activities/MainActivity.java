package com.example.telephonedirectory.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.SearchManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.telephonedirectory.GetData.Adapter;
import com.example.telephonedirectory.GetData.GenerateNameAndPhoneNumber;
import com.example.telephonedirectory.GetData.Phone;
import com.example.telephonedirectory.R;
import com.example.telephonedirectory.Validation.Validation;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class MainActivity extends AppCompatActivity {

    public TextView textView;

    public TextView phoneNumber;

    public SearchView searchView;

    public List<Phone> phones = new GenerateNameAndPhoneNumber().nameGenerate();;

    private static final String CHAR_LIST = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            changeList();
        }
    };
    BroadcastReceiver returnMainActivity = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            delete();
        }
    };
    public Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.Name);
        phoneNumber = findViewById(R.id.phoneNumber);
        adapter = new Adapter(this,phones);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        registerReceiver(this.broadcastReceiver, new IntentFilter("update"));
        registerReceiver(this.returnMainActivity,new IntentFilter("return"));

        ImageView addButton = findViewById(R.id.add);
        addButton.setOnClickListener(view -> addContact());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.getFilter().filter(query);
                adapter.notifyDataSetChanged();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                adapter.getFilter().filter(query);
                adapter.notifyDataSetChanged();
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_search) return true;
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        // close search view on back button pressed
        if (!searchView.isIconified()) {
            searchView.setIconified(true);
            return;
        }
        super.onBackPressed();
    }

    public void changeList() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String telephone = preferences.getString("telephone","no id");
        String name = preferences.getString("name","no id");
        String oldPhone = preferences.getString("oldPhone","no id");

        for (Phone phone : phones) {
            if (phone.getPhoneNumber().equals(oldPhone)) {
                phone.setPhoneNumber(telephone);
                phone.setName(name);
                break;
            }
        }
        adapter.notifyDataSetChanged();
    }
    public void delete(){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        int position = preferences.getInt("position",-1);
        phones.remove(position);
        adapter.notifyItemRemoved(position);
        adapter.notifyDataSetChanged();
    }

    public void addContact(){
        AtomicBoolean check = new AtomicBoolean(true);
        View view = findViewById(R.id.mainView);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE);
        View view1 = inflater.inflate(R.layout.popup_add,null);
        int width = RelativeLayout.LayoutParams.WRAP_CONTENT;
        int height = RelativeLayout.LayoutParams.WRAP_CONTENT;
        PopupWindow popupWindow = new PopupWindow(view1,width,height,true);
        popupWindow.showAtLocation(view, Gravity.CENTER,0,0);

        EditText telephone = view1.findViewById(R.id.telephone);
        EditText firstName = view1.findViewById(R.id.firstChange);
        EditText lastName = view1.findViewById(R.id.LastChange);

        Button okAdd = view1.findViewById(R.id.okAdd);
        okAdd.setOnClickListener(view2 -> {

            Phone phone = new Phone();
            String phoneNumber = telephone.getText().toString();
            String name = firstName.getText()+" " +lastName.getText();
            check.set(Validation.isNumber(phoneNumber));
            if (!check.get()){
                Toast.makeText(getApplicationContext(),"Please insert Phone Number properly",Toast.LENGTH_SHORT).show();
            }else {
                check.set(Validation.isString(name));
                if (!check.get()) {
                    Toast.makeText(getApplicationContext(), "The name does not contains any number", Toast.LENGTH_SHORT).show();
                } else {
                    phone.setPhoneNumber(telephone.getText().toString());
                    phone.setName(firstName.getText() + " " + lastName.getText());
                    phones.add(1, phone);
                    adapter.notifyItemInserted(1);
                    //close the Pop-up
                    popupWindow.dismiss();
                }
            }
        });

        Button cancelAdd = view1.findViewById(R.id.cancelAdd);
        cancelAdd.setOnClickListener(view22 -> popupWindow.dismiss());

    }


}