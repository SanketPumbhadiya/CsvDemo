package com.example.csvdemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    EditText edtFirstName, edtMiddleName, edtLastName, edtAge;
    Button btnSubmit;
    private final int PERMISSION_REQUEST_CODE = 101;
    String path = "storage/emulated/0/Sanket";
    CsvFileDataModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtFirstName = findViewById(R.id.edt_firstname);
        edtMiddleName = findViewById(R.id.edt_middlename);
        edtLastName = findViewById(R.id.edt_lastname);
        edtAge = findViewById(R.id.edt_age);
        btnSubmit = findViewById(R.id.btn_Submit);

        requestPermissionIfNeeded();

        btnSubmit.setOnClickListener(onClickAddCsv);

//        File file = new File("storage/emulated/0/Sanket/sanket.csv");
//        deleteFileOrFolder(file);
    }

    View.OnClickListener onClickAddCsv = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            createFolder();

            String firstName = edtFirstName.getText().toString();
            String middleName = edtMiddleName.getText().toString();
            String lastName = edtLastName.getText().toString();
            String age = edtAge.getText().toString();

            if (firstName.isEmpty() && middleName.isEmpty() && lastName.isEmpty() && age.isEmpty()) {
                Toast.makeText(MainActivity.this, "Please Enter Fields", Toast.LENGTH_SHORT).show();
            } else if (firstName.isEmpty()) {
                Toast.makeText(MainActivity.this, "FirstName is Required", Toast.LENGTH_SHORT).show();
            } else if (middleName.isEmpty()) {
                Toast.makeText(MainActivity.this, "MiddleName is Required", Toast.LENGTH_SHORT).show();
            } else if (lastName.isEmpty()) {
                Toast.makeText(MainActivity.this, "LastName is Required", Toast.LENGTH_SHORT).show();
            } else if (age.isEmpty()) {
                Toast.makeText(MainActivity.this, "Age is Required", Toast.LENGTH_SHORT).show();
            } else {

                model = new CsvFileDataModel(firstName, middleName, lastName, age);

                writeFile();

                edtFirstName.setText("");
                edtMiddleName.setText("");
                edtLastName.setText("");
                edtAge.setText("");
            }
        }
    };

    public void createFolder() {
        File folder = new File(path);
        if (!folder.exists()) {
            folder.mkdir();
        }
    }

    public void deleteFileOrFolder(File folderOrFile) {
        if (folderOrFile.isDirectory()) {
            for (File childFile : folderOrFile.listFiles()) {
                deleteFileOrFolder(childFile);
            }
        }
        folderOrFile.delete();
    }

    public void writeFile() {
        String file = path + "/sanket.csv";
        try {

            FileWriter fileWriter = new FileWriter(file, true);
            fileWriter.write(model.getCsvRowLineData());
            fileWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void requestPermissionIfNeeded() {
//        int readPermission = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE);
//        int managePermission = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.MANAGE_EXTERNAL_STORAGE);
//
//        Log.d("Permission", "ReadPermission : " + readPermission);
//        Log.d("Permission", "ManagePermission : " + managePermission);

        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.MANAGE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.MANAGE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
            startActivity(new Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION));
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show();
            }
        }
    }
}