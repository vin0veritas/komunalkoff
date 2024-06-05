package com.example.komunalkoff;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class HistoryFragment extends Fragment {

    private RecyclerView recyclerView;
    private HistoryAdapter adapter;
    private List<Payment> paymentList;
    private DatabaseHelper dbHelper;
    private FirebaseAuth mAuth;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbHelper = new DatabaseHelper(getActivity());
        mAuth = FirebaseAuth.getInstance();
    }

    @Nullable
    @Override
    // создание макета фрагмента и иего иницеализация его адаптера
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history, container, false);
        recyclerView = view.findViewById(R.id.history_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        paymentList = new ArrayList<>();
        adapter = new HistoryAdapter(paymentList, getContext());
        recyclerView.setAdapter(adapter);

        loadPaymentsFromDatabase();

        return view;
    }

    //загузка бл о платежах
    private void loadPaymentsFromDatabase() {
        paymentList.clear();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        FirebaseUser user = mAuth.getCurrentUser();

        if (user != null) {
            String userId = user.getUid();
            Cursor cursor = db.rawQuery("SELECT service_type, amount, date FROM payments WHERE user_id = ?", new String[]{userId});

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    String serviceType = cursor.getString(0);
                    double amount = cursor.getDouble(1);
                    String date = cursor.getString(2);

                    paymentList.add(new Payment(serviceType, amount, date));
                } while (cursor.moveToNext());
                cursor.close();
            } else {
                Toast.makeText(getActivity(), "No payments found", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getActivity(), "User not authenticated", Toast.LENGTH_SHORT).show();
        }
        adapter.notifyDataSetChanged();
    }
}
