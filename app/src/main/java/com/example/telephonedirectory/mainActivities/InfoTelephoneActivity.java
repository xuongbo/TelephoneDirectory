package com.example.telephonedirectory.mainActivities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.telephonedirectory.R;

public class InfoTelephoneActivity extends AppCompatActivity {

    public String[] data = new String[2];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_telephone);
        data = getIntent().getStringArrayExtra("data");
        TextView phoneNumber = findViewById(R.id.number);
        TextView contactName = findViewById(R.id.contactName);
        TextView firstName = findViewById(R.id.firstName);
        TextView lastName = findViewById(R.id.lastName);
        phoneNumber.setText(data[1]);
        contactName.setText(data[0]);
        String[] name = data[0].split(" ",2);
        firstName.setText(name[0]);
        lastName.setText(name[1]);

    }

    //Open edit Pop-up
    public void editData(View view){
        view = findViewById(R.id.infoView);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE);
        View view1 = inflater.inflate(R.layout.popup_edit,null);
        int width = RelativeLayout.LayoutParams.WRAP_CONTENT;
        int height = RelativeLayout.LayoutParams.WRAP_CONTENT;
        PopupWindow popupWindow = new PopupWindow(view1,width,height,true);
        popupWindow.showAtLocation(view, Gravity.CENTER,0,0);

        //How to avoid same code in other methods ?
        EditText telephone = view1.findViewById(R.id.telephone);
        EditText firstName = view1.findViewById(R.id.firstChange);
        EditText lastName = view1.findViewById(R.id.LastChange);

        //Set data to the EditBox
        String[] name = data[0].split(" ",2);
        telephone.setText(data[1]);
        firstName.setText(name[0]);
        lastName.setText(name[1]);

        //Cancel if nothing change
        Button cancel = view1.findViewById(R.id.cancel);
        cancel.setOnClickListener(view2 -> popupWindow.dismiss());

        //Accept all changes
        Button ok = view1.findViewById(R.id.ok);
        ok.setOnClickListener(view22 -> {
            TextView phoneNumber = findViewById(R.id.number);
            TextView contactName = findViewById(R.id.contactName);
            TextView newFirstName = findViewById(R.id.firstName);
            TextView newLastName = findViewById(R.id.lastName);


            phoneNumber.setText(telephone.getText());
            contactName.setText(firstName.getText()+" "+(lastName.getText().toString()));
            newFirstName.setText(firstName.getText());
            newLastName.setText(lastName.getText());
            saveChange(telephone.getText().toString(),firstName.getText()+" "+(lastName.getText().toString()),data[1]);
            data[1] = telephone.getText().toString();
            data[0] = firstName.getText()+" "+(lastName.getText().toString());
            popupWindow.dismiss();

        });
    }

    public void saveChange(String telephone, String name,String oldPhone){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("telephone",telephone).apply();
        editor.putString("name",name).apply();
        editor.putString("oldPhone",oldPhone).apply();
        sendBroadcast(new Intent().setAction("update"));
    }

    public void deleteContact(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete Contact");
        builder.setMessage("Are you sure you want to delete this contact");
        builder.setCancelable(false);

        builder.setPositiveButton("Yes", (dialogInterface, i) -> {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            SharedPreferences.Editor editor = preferences.edit();
            Bundle bundle = getIntent().getExtras();
            editor.putInt("position",bundle.getInt("position")).apply();
            sendBroadcast(new Intent().setAction("return"));
            finish();
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
       AlertDialog alert = builder.create();
       alert.show();
    }
}