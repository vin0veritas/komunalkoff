package com.example.komunalkoff;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.widget.ImageView;
import android.widget.TextView;

public class DeveloperInfoFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_developer_info, container, false);
        TextView textView = view.findViewById(R.id.textView);
        textView.setText(getDeveloperInfoText());

        ImageView logoImageView = view.findViewById(R.id.logoImageView);
        logoImageView.setImageResource(R.drawable.mirea_logo);

        return view;
    }

    private String getDeveloperInfoText() {
        return "Работу выполнил студент группы ИКБО 27-22\n" +
                "Тесля Алексей Вячеславович\n\n";
    }
}
