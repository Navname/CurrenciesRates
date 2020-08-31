package ru.maxim.spring.mvc.canvasjsChart;

import org.springframework.ui.Model;

import java.text.SimpleDateFormat;
import java.util.*;


public class MyChart {

    public static List<List<Map<Object, Object>>> getCanvasjsDataList(Model model) {
        List<List<Map<Object, Object>>> list = new ArrayList<List<Map<Object, Object>>>();
        List<Map<Object, Object>> dataPoints1 = new ArrayList<Map<Object, Object>>();

        ArrayList<Date> datesArr = (ArrayList<Date>) model.getAttribute("datesArr");
        ArrayList<String> datesArrString = new ArrayList<>();

        SimpleDateFormat formatter = new SimpleDateFormat("MMM dd, yyyy", Locale.ENGLISH);

        for (Date date : datesArr) {
            String temp = formatter.format(date);
            datesArrString.add(temp);
        }

        ArrayList<String> rates = (ArrayList<String>) model.getAttribute("rates");

        int count = rates.size();
        double yVal = 100.0;

        for (int i = 0; i < count; i++) {
            yVal = Double.valueOf(rates.get(i));
            Map<Object, Object> map = new HashMap<Object, Object>();
            map.put("x", datesArrString.get(i));
            map.put("y", yVal);
            dataPoints1.add(map);
        }

        list.add(dataPoints1);
        return list;
    }
}
