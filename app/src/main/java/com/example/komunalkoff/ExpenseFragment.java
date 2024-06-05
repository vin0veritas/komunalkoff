package com.example.komunalkoff;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
// ввод и сохранение в бд
public class ExpenseFragment extends Fragment {

    private DatabaseHelper dbHelper; // для работы с бд
    private FirebaseAuth mAuth; // аутенфикация пользователя
    private AutoCompleteTextView serviceTypeDropdown; // автозаполнение для типа услуги
    private EditText valueInput; // значение расхода

    private static final String TABLE_RATES = "rates";
    private static final String COLUMN_USER_ID = "user_id";
    private static final String COLUMN_LIGHT = "light";
    private static final String COLUMN_WATER = "water";
    private static final String COLUMN_HEATING = "heating"; // отопление
    private static final String COLUMN_STOCK = "stock";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbHelper = new DatabaseHelper(getActivity());
        mAuth = FirebaseAuth.getInstance();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_expense, container, false);

        //инициализация элементов пользоватлеьского интерфеса
        serviceTypeDropdown = view.findViewById(R.id.type_komm);
        valueInput = view.findViewById(R.id.valueDec);
        Button addButton = view.findViewById(R.id.filledButton);

        //адаптер для списка с автозаполнением
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.service_types, android.R.layout.simple_dropdown_item_1line);
        serviceTypeDropdown.setAdapter(adapter);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateAndSaveExpense();
            }
        });

        return view;
    }

    private void calculateAndSaveExpense() {
        String serviceType = serviceTypeDropdown.getText().toString();
        String valueStr = valueInput.getText().toString();

        // все элементы - поля
        if (serviceType.isEmpty() || valueStr.isEmpty()) {
            Toast.makeText(getActivity(), "Заполните все поля", Toast.LENGTH_SHORT).show();
            return;
        }

        // сумма оплаты
        double value = Double.parseDouble(valueStr);
        double rate = getRateForService(serviceType);
        double amount = value * rate;

        // идентификатор пользователя
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            String userId = user.getUid();
            String currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

            // добавление записи в бд
            dbHelper.addPayment(userId, serviceType, amount, currentDate);
            Toast.makeText(getActivity(), "Расход добавлен", Toast.LENGTH_SHORT).show();
        }
    }

    public double getRateForService(String serviceType) {
        // бд в режиме чтения
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        //запрос к таблице тарифов
        Cursor cursor = db.query(TABLE_RATES,
                new String[]{COLUMN_LIGHT, COLUMN_WATER, COLUMN_HEATING, COLUMN_STOCK},
                COLUMN_USER_ID + "=?",
                new String[]{FirebaseAuth.getInstance().getCurrentUser().getUid()},
                null, null, null);

        // обработчик запроса
        if (cursor != null && cursor.moveToFirst()) {
            double rate = 0;
            int columnIndex = -1;
            switch (serviceType) {
                case "Свет":
                    columnIndex = cursor.getColumnIndex(COLUMN_LIGHT);
                    break;
                case "Вода":
                    columnIndex = cursor.getColumnIndex(COLUMN_WATER);
                    break;
                case "Отопление":
                    columnIndex = cursor.getColumnIndex(COLUMN_HEATING);
                    break;
                case "Водоотведение":
                    columnIndex = cursor.getColumnIndex(COLUMN_STOCK);
                    break;
                default:
                    columnIndex = 1;
                    break;
            }

            // получение занчения тарифа
            if (columnIndex != -1) {
                rate = cursor.getDouble(columnIndex);
            } else {
                // Обработка случая, когда столбец не найден
                Log.e("DatabaseHelper", "Столбец не найден для типа услуги: " + serviceType);
            }


            cursor.close(); //https://developer.alexanderklimov.ru/android/sqlite/cursor.php
            return rate;
        } else {
            // если не нашли тарифы - делаем возврат стандартного значения
            if (cursor != null) {
                cursor.close();
            }
            // Если не удалось найти тарифы, можно вернуть стандартное значение
            return 0; // Установим стандартное значение, вы можете изменить это по своему усмотрению
        }
    }
}

