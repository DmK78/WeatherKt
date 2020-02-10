package ru.job4j.weatherkt.model;


import com.google.gson.annotations.SerializedName;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Objects;

/**
 * @author Dmitry Kolganov (mailto:dmk78@inbox.ru)
 * @version $Id$
 * @since 01.12.2019
 */

public class FiveDaysWeather {


    @SerializedName("list")
    private List<Day> days = null;
    private City city;

    public List<Day> getDays() {
        return days;
    }

    public City getCity() {
        return city;
    }

    public void setDays(List<Day> days) {
        this.days = days;
    }

    public void setCity(City city) {
        this.city = city;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FiveDaysWeather that = (FiveDaysWeather) o;
        return Objects.equals(days, that.days) &&
                Objects.equals(city, that.city);
    }

    @Override
    public int hashCode() {
        return Objects.hash(days, city);
    }


    public void calculateDateTime() {
        for (Day day : days) {
            day.setTime(day.getDt_txt().substring(10, day.getDt_txt().length() - 3));
            day.setDate(day.getDt_txt().substring(8, 10) + "-" + day.getDt_txt().substring(5, 7) + "-" + day.getDt_txt().substring(0, 4));
            SimpleDateFormat fromUser = new SimpleDateFormat("dd-MM-yyyy");
            SimpleDateFormat myFormat = new SimpleDateFormat("dd MMM, EE");
            try {
                String reformattedStr = myFormat.format(fromUser.parse(day.getDate()));
                day.setDate(reformattedStr);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }
}