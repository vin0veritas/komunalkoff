package com.example.komunalkoff;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RateFragment extends Fragment {

    private TextInputEditText lightValueDec, waterValueDec, hotValueDec, stockValueDec;
    private Button rateSaveBtn;
    private DatabaseHelper dbHelper;
    private FirebaseAuth auth;
    private FirebaseUser currentUser;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auth = FirebaseAuth.getInstance();
        currentUser = auth.getCurrentUser();
        dbHelper = new DatabaseHelper(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rate, container, false);

        lightValueDec = view.findViewById(R.id.lightValueDec);
        waterValueDec = view.findViewById(R.id.waterValueDec);
        hotValueDec = view.findViewById(R.id.hotValueDec);
        stockValueDec = view.findViewById(R.id.stockValueDec);
        rateSaveBtn = view.findViewById(R.id.rateSaveBtn);

        rateSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveRates();
            }
        });

        if (currentUser != null) {
            loadRates();
        } else {
            Toast.makeText(getActivity(), "User not logged in", Toast.LENGTH_SHORT).show();
        }

        return view;
    }

    private void saveRates() {
        String light = lightValueDec.getText().toString().trim();
        String water = waterValueDec.getText().toString().trim();
        String heating = hotValueDec.getText().toString().trim();
        String stock = stockValueDec.getText().toString().trim();

        if (TextUtils.isEmpty(light) || TextUtils.isEmpty(water) || TextUtils.isEmpty(heating) || TextUtils.isEmpty(stock)) {
            Toast.makeText(getActivity(), "Заполните все поля", Toast.LENGTH_SHORT).show();
            return;
        }

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_USER_ID, currentUser.getUid());
        values.put(DatabaseHelper.COLUMN_LIGHT, Double.parseDouble(light));
        values.put(DatabaseHelper.COLUMN_WATER, Double.parseDouble(water));
        values.put(DatabaseHelper.COLUMN_HEATING, Double.parseDouble(heating));
        values.put(DatabaseHelper.COLUMN_STOCK, Double.parseDouble(stock));

        long newRowId = db.insertWithOnConflict(DatabaseHelper.TABLE_RATES, null, values, SQLiteDatabase.CONFLICT_REPLACE);

        if (newRowId == -1) {
            Toast.makeText(getActivity(), "Ошибка сохранения", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getActivity(), "Данные сохранены", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadRates() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.query(
                DatabaseHelper.TABLE_RATES,
                null,
                DatabaseHelper.COLUMN_USER_ID + " = ?",
                new String[]{currentUser.getUid()},
                null,
                null,
                null
        );

        if (cursor != null && cursor.moveToFirst()) {
            lightValueDec.setText(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_LIGHT)));
            waterValueDec.setText(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_WATER)));
            hotValueDec.setText(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_HEATING)));
            stockValueDec.setText(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_STOCK)));
            cursor.close();
        }
    }
}
