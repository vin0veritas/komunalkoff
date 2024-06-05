package com.example.komunalkoff;

import android.app.AlertDialog;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.PaymentViewHolder> {

    private List<Payment> paymentList;
    private DatabaseHelper dbHelper;
    private Context context;

    public HistoryAdapter(List<Payment> paymentList, Context context) {
        this.paymentList = paymentList;
        this.context = context;
        dbHelper = new DatabaseHelper(context);
    }

    @NonNull
    @Override
    public PaymentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_item, parent, false);
        return new PaymentViewHolder(itemView);
    }

    @Override
    // поля платежа в соответсвии textview
    public void onBindViewHolder(@NonNull PaymentViewHolder holder, int position) {
        Payment payment = paymentList.get(position);
        holder.serviceType.setText(payment.getServiceType());
        holder.amount.setText(String.valueOf(payment.getAmount()));
        holder.date.setText(payment.getDate());

        //в зависимости от типа устанавливаем ед измерения
        String costUnit;
        switch (payment.getServiceType()) {
            case "Отопление":
                costUnit = "р/Гкал";
                break;
            case "Вода":
                costUnit = "р/л^3";
                break;
            case "Свет":
                costUnit = "р/кВт*ч";
                break;
            case "Водоотведение":
                costUnit = "р/л^3";
                break;
            default:
                costUnit = "";
                break;
        }
        holder.costUnit.setText(costUnit);

        // слушатель клика
        holder.itemView.setOnClickListener(v -> new AlertDialog.Builder(context)
                .setTitle("Удалить платеж")
                .setMessage("Вы точно хотите удалить платеж из истории?")
                .setPositiveButton("Да", (dialog, which) -> {
                    removePayment(position);
                })
                .setNegativeButton("Нет", null)
                .show());
    }

    private void removePayment(int position) {
        Payment payment = paymentList.get(position);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(DatabaseHelper.TABLE_PAYMENTS, DatabaseHelper.COLUMN_DATE + "=? AND " + DatabaseHelper.COLUMN_AMOUNT + "=?",
                new String[]{payment.getDate(), String.valueOf(payment.getAmount())});
        paymentList.remove(position);
        notifyItemRemoved(position);
        db.close();
    }

    @Override
    public int getItemCount() {
        return paymentList.size();
    }

    public static class PaymentViewHolder extends RecyclerView.ViewHolder {
        public TextView serviceType, amount, date, costUnit;

        public PaymentViewHolder(@NonNull View itemView) {
            super(itemView);
            // отображения информации о платежах
            serviceType = itemView.findViewById(R.id.type_komm);
            amount = itemView.findViewById(R.id.summ_komm);
            date = itemView.findViewById(R.id.date_komm);
            costUnit = itemView.findViewById(R.id.cost_komm);
        }
    }
}
