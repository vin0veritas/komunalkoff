package com.example.komunalkoff;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.widget.TextView;

public class AppDetailsFragment extends Fragment {
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_license_agreement, container, false);
        TextView textView = view.findViewById(R.id.textView);
        textView.setText(getInfoAgreementText());
        return view;
    }

    private String getInfoAgreementText() {
        return "Приложение \"Коммуналка\" разработано для удобного учета и расчета коммунальных услуг для собственников и арендаторов жилых и нежилых помещений на платформе Android. Оно предоставляет пользователям возможность автоматизировать процесс учета и анализа потребления коммунальных ресурсов, что делает его незаменимым инструментом в сфере бытовых финансов.\n\n" +
                "1. Введение\n" +
                "1.1 Наименование программы\n\n" +
                "Наименование: \"Коммуналка\"\n\n" +
                "1.2 Назначение и область применения\n\n" +
                "Мобильное приложение \"Коммуналка\" предназначено для учета и расчета стоимости коммунальных услуг. Оно обеспечивает пользователей удобными инструментами для отслеживания и анализа потребления коммунальных ресурсов.\n\n" +
                "2. Требования к приложению\n" +
                "2.1 Дизайн\n\n" +
                "Минималистичный дизайн: Основные цвета – белый и синий, обеспечивающие простоту восприятия.\n" +
                "Чистый и простой интерфейс: Для лучшего взаимодействия с пользователем.\n\n" +
                "2.2 Навигация\n\n" +
                "Интуитивно понятная навигация: Легко понятные разделы приложения.\n" +
                "Минимальное количество шагов: Для получения нужной информации.\n" +
                "Удобство использования: Обеспечение комфортного пользовательского опыта.\n\n" +
                "2.3 Счетчики\n\n" +
                "Выбор типа услуги: Пользователи могут выбирать между видами услуг (водоснабжение, электроснабжение, водоотведение) или вводить собственные данные.\n" +
                "Вывод информации о затратах: На экране \"Расчет\" в графическом виде.\n\n" +
                "2.4 Тариф\n\n" +
                "Ввод тарифов: Возможность ввода цен в целочисленном формате для всех видов услуг.\n" +
                "Сохранение данных: О возможности сохранения данных о тарифе пользователя.\n\n" +
                "2.5 История\n\n" +
                "Вывод данных: В виде списка операций по счетчикам.\n\n" +
                "2.6 Расчет\n\n" +
                "Разделение суммы: Автоматическое разделение суммы за коммунальные услуги на подгруппы.\n" +
                "Вывод данных в рублях: Основано на формулах коммунальных услуг.\n\n" +
                "2.7 Ролевая модель\n\n" +
                "Незарегистрированные пользователи: Не имеют возможности изменять данные о счетчиках и вносить новые данные.\n" +
                "Приложение \"Коммуналка\" обладает всеми необходимыми функциональными возможностями для удобного учета и анализа коммунальных расходов, обеспечивая пользователям простоту использования и надежность данных.";
    }

}
