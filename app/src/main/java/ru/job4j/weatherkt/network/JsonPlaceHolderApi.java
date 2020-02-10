package ru.job4j.weatherkt.network;



import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import ru.job4j.weatherkt.model.CurrentWeather;
import ru.job4j.weatherkt.model.FiveDaysWeather;

/**
 * @author Dmitry Kolganov (mailto:dmk78@inbox.ru)
 * @version $Id$
 * @since 01.12.2019
 */

public interface JsonPlaceHolderApi {

    @GET("weather")
    Single<CurrentWeather> getCurrentWeatherByCoord(
            @Query("lat") double latittude,
            @Query("lon") double longitude,
            @Query("appid") String apiKey,
            @Query("units") String units,
            @Query("lang") String lang
    );

    @GET("forecast")
    Single<FiveDaysWeather> getFiveDaysWeather(
            @Query("lat") double latittude,
            @Query("lon") double longitude,
            @Query("appid") String apiKey,
            @Query("units") String units,
            @Query("lang") String lang
    );

    @GET("weather")
    Call<CurrentWeather> getCurrentWeatherByCoordForVidget(
            @Query("lat") double latittude,
            @Query("lon") double longitude,
            @Query("appid") String apiKey,
            @Query("units") String units,
            @Query("lang") String lang
    );



}
