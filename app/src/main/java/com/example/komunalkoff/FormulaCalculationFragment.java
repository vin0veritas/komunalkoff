package com.example.komunalkoff;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.widget.TextView;

public class FormulaCalculationFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_license_agreement, container, false);
        TextView textView = view.findViewById(R.id.textView);
        textView.setText(getDeveloperInfoText());
        return view;
    }

    private String getDeveloperInfoText() {
        return "1.1.1. Расчет платы за холодное водоснабжение\n" +
                "Расчет платы за холодное водоснабжение регулируется приложением №2 к Правилам предоставления коммунальных услуг собственникам и пользователям помещений в многоквартирных домах и жилых домов, утвержденным постановлением Правительства Российской Федерации от 06.05.2011 №354. Формула расчета выглядит следующим образом:\n" +
                "P = V× T\n" +
                "где:\n" +
                "• P — плата за холодное водоснабжение.\n" +
                "• V — объем потребленной холодной воды за месяц (в кубических метрах).\n" +
                "• T — тариф на холодное водоснабжение, установленный Региональной энергетической комиссией.\n\n" +
                "Пример расчета:\n" +
                "• Объем потребленной холодной воды за месяц: 5 куб.м.\n" +
                "• Тариф на холодное водоснабжение: 30 руб./куб.м.\n" +
                "P = 5 куб.м. × 30 руб./куб.м. = 150 руб.\n\n" +
                "1.1.2. Расчет платы за горячее водоснабжение\n" +
                "Аналогично холодному водоснабжению, расчет платы за горячее водоснабжение также регулируется приложением №2 к Правилам предоставления коммунальных услуг и рассчитывается по следующей формуле:\n" +
                "P = V× T\n" +
                "где:\n" +
                "• P — плата за горячее водоснабжение.\n" +
                "• V — объем потребленной горячей воды за месяц (в кубических метрах).\n" +
                "• T — тариф на горячее водоснабжение, установленный Региональной энергетической комиссией.\n\n" +
                "Пример расчета:\n" +
                "• Объем потребленной горячей воды за месяц: 10 куб.м.\n" +
                "• Тариф на горячее водоснабжение: 50 руб./куб.м.\n" +
                "P = 10 куб.м. × 50 руб./куб.м. = 500 руб.\n\n" +
                "1.1.3. Расчет платы за электроэнергию\n" +
                "Расчет платы за электроэнергию также производится в соответствии с формулой, указанной в приложении №2 к Правилам предоставления коммунальных услуг:\n" +
                "P = V× T\n" +
                "где:\n" +
                "• P — плата за электроэнергию.\n" +
                "• V — объем потребленной электроэнергии за месяц (в киловатт-часах).\n" +
                "• T — тариф на электроэнергию, установленный Региональной энергетической комиссией.\n\n" +
                "Пример расчета:\n" +
                "• Объем потребленной электроэнергии за месяц: 150 кВт·ч.\n" +
                "• Тариф на электроэнергию: 4 руб./кВт·ч.\n" +
                "P = 150 кВт·ч × 4 руб./кВт·ч = 600 руб.\n\n" +
                "1.1.4. Расчет платы за водоотведение\n" +
                "Плата за водоотведение включает в себя отвод бытовых стоков из жилого дома (канализацию), транспортировку их до очистных сооружений, последующую очистку и утилизацию. Расчет производится по следующей формуле:\n" +
                "P = V× T\n" +
                "где:\n" +
                "• P — плата за водоотведение.\n" +
                "• V — суммарный объем холодной и горячей воды, потребленной за месяц (в кубических метрах).\n" +
                "• T — тариф на водоотведение, установленный Региональной энергетической комиссией.\n\n" +
                "Пример расчета:\n" +
                "• Объем потребленной холодной воды за месяц: 5 куб.м.\n" +
                "• Объем потребленной горячей воды за месяц: 10 куб.м.\n" +
                "• Тариф на водоотведение: 20,52 руб./куб.м.\n" +
                "V = 5 куб.м. + 10 куб.м. = 15 куб.м.\n" +
                "P = 15 куб.м. × 20,52 руб./куб.м. = 307,80 руб.";
    }

}
