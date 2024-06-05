package com.example.komunalkoff;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private PieChart pieChart;
    private DatabaseHelper dbHelper;
    private SharedPreferences sharedPreferences;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbHelper = new DatabaseHelper(getActivity());
        sharedPreferences = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        pieChart = view.findViewById(R.id.pieChart);

        setupPieChart();
        loadChartData();

        return view;
    }

    //диаграмма
    private void setupPieChart() {
        pieChart.getDescription().setEnabled(false);
        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColor(android.R.color.transparent);
        pieChart.setTransparentCircleAlpha(0);
        pieChart.setEntryLabelTextSize(12f);
        pieChart.setEntryLabelColor(Color.BLACK); // Устанавливаем цвет текста как в приложении
    }

    // проверка что пользователь вошел
    private void loadChartData() {
        boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);
        if (!isLoggedIn) {
            pieChart.clear();
            return;
        }

        // получение данных из бд
        List<PieEntry> entries = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT service_type, SUM(amount) FROM payments GROUP BY service_type", null);

        int[] colors = {Color.BLUE, Color.GREEN, Color.RED, Color.YELLOW, Color.MAGENTA, Color.CYAN};

        if (cursor != null && cursor.moveToFirst()) {
            int colorIndex = 0;
            do {
                String serviceType = cursor.getString(0);
                float amount = cursor.getFloat(1);
                entries.add(new PieEntry(amount, serviceType));
                colorIndex++;
            } while (cursor.moveToNext());
        }

        if (cursor != null) {
            cursor.close();
        }

        PieDataSet dataSet = new PieDataSet(entries, "");
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        dataSet.setValueTextSize(12f);
        dataSet.setValueTextColor(Color.BLACK);

        // создание и установка данных для диаграммы
        PieData data = new PieData(dataSet);
        pieChart.setData(data);

        // Обновляем диаграмму
        pieChart.notifyDataSetChanged();
        pieChart.invalidate(); // Обновление диаграммы
    }

    @Override
    public void onResume() {
        super.onResume();
        loadChartData(); // Обновляем данные диаграммы при возврате к фрагменту
    }
}
