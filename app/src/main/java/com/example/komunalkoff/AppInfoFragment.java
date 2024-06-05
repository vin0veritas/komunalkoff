package com.example.komunalkoff;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class AppInfoFragment extends Fragment {

    private ListView listView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_app_info, container, false);

        listView = view.findViewById(R.id.listView);
        String[] items = {
                "Информация о приложении",
                "Информация о разработчике",
                "Лицензионное соглашение",
                "Расчет формул коммунальных услуг"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, items);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Fragment fragment = null;
                switch (position) {
                    case 0:
                        fragment = new AppDetailsFragment();
                        break;
                    case 1:
                        fragment = new DeveloperInfoFragment();
                        break;
                    case 2:
                        fragment = new LicenseAgreementFragment();
                        break;
                    case 3:
                        fragment = new FormulaCalculationFragment();
                        break;
                }

                if (fragment != null) {
                    getParentFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, fragment)
                            .addToBackStack(null)
                            .commit();
                }
            }
        });

        return view;
    }
}
