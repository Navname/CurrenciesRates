package ru.maxim.spring.mvc.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import ru.maxim.spring.mvc.DateUtil;
import ru.maxim.spring.mvc.canvasjsChart.MyChart;
import ru.maxim.spring.mvc.exceptions.NotfoundException;
import ru.maxim.spring.mvc.model.Greeting;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class GreetingController {

    public static void getCurrencies(Model model) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> responseBody = restTemplate.getForEntity("https://www.nbrb.by/api/exrates/currencies", String.class);
            String[] responseRow = responseBody.getBody().split("},");
            TreeSet<String> currencies = new TreeSet<>();

            for (int i = 0; i < responseRow.length; i++) {
                int beginIndex = responseRow[i].indexOf("Cur_Abbreviation") + 19;
                String curAbbreviation = responseRow[i].substring(beginIndex, beginIndex + 3);
                currencies.add(curAbbreviation);
            }
            model.addAttribute("currencies", currencies);
        } catch (Exception e) {
            System.out.println("------------------------------------------------");
            System.out.println("Error: " + Arrays.toString(e.getStackTrace()));
            System.out.println("------------------------------------------------");
            throw new NotfoundException();
        }
    }

    public static void getRates(String selectedCur, String date1, String date2, Model model) {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-M-dd", Locale.ENGLISH);

        Date date1Date = null;
        Date date2Date = null;
        Date currentDate = new Date();
        try {
            date1Date = formatter.parse(date1);
            date2Date = formatter.parse(date2);
            if (date1Date.after(date2Date)) {
                Date temp = date1Date;
                date1Date = date2Date;
                date2Date = temp;
            }
            if (date2Date.after(currentDate)) {
                date2Date = formatter.parse(formatter.format(currentDate));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        ArrayList<Date> datesArr = new ArrayList<>();
        datesArr.add(date1Date);

        Date iterDate = date1Date;
        iterDate = DateUtil.addDays(iterDate, 1);

        while (iterDate.after(date1Date) && iterDate.before(date2Date)) {
            datesArr.add(iterDate);
            iterDate = DateUtil.addDays(iterDate, 1);
        }
        datesArr.add(date2Date);
        model.addAttribute("datesArr", datesArr);

        ArrayList<String> rates = new ArrayList<>();

        for (Date d : datesArr) {
            String dateString = formatter.format(d);
            try {
                String myURL = "https://www.nbrb.by/api/exrates/rates/" + selectedCur + "?parammode=2&ondate=" + dateString + "&periodicity=0";
                RestTemplate restTemplate = new RestTemplate();
                ResponseEntity<String> responseBody = restTemplate.getForEntity(myURL, String.class);

                System.out.println("Date: " + dateString + " Abbreviation: " + selectedCur);

                int beginIndex = responseBody.getBody().indexOf("Cur_OfficialRate") + 18;
                int endIndex = responseBody.getBody().indexOf("}", beginIndex);
                String rate = responseBody.getBody().substring(beginIndex, endIndex);
                rates.add(rate);
            } catch (Exception e) {
                System.out.println("------------------------------------------------");
                System.out.println("Error: " + Arrays.toString(e.getStackTrace()));
                System.out.println("------------------------------------------------");
                throw new NotfoundException();
            }
        }

        if (rates.size() == 0) {
            throw new NotfoundException();
        }

        model.addAttribute("rates", rates);
        List<List<Map<Object, Object>>> canvasjsDataList = MyChart.getCanvasjsDataList(model);
        model.addAttribute("dataPointsList", canvasjsDataList);
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String greetingForm(Model model) {
        GreetingController.getCurrencies(model);
        model.addAttribute("greeting", new Greeting());
        return "th_form.html";
    }

    @RequestMapping(value = "/greeting", method = RequestMethod.GET)
    public String greetingForm2(Model model) {
        GreetingController.getCurrencies(model);
        model.addAttribute("greeting", new Greeting());
        return "th_form.html";
    }

    @RequestMapping(value = "/greeting", method = RequestMethod.POST)
    public String greetingSubmit(@RequestParam(value = "cur") String selectedCur, @RequestParam(value = "date1") String selectedDate1,
                                 @RequestParam(value = "date2") String selectedDate2, @ModelAttribute Greeting greeting, Model model) {
        model.addAttribute("selectedCur", selectedCur);
        model.addAttribute("selectedDate1", selectedDate1);
        model.addAttribute("selectedDate2", selectedDate2);
        GreetingController.getRates(selectedCur, selectedDate1, selectedDate2, model);

        model.addAttribute("greeting", greeting);
        return "graph.jsp";
    }

}