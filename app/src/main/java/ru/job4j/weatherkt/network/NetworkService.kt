package ru.job4j.weatherkt.network

import android.text.TextUtils
import androidx.lifecycle.MutableLiveData

import com.google.android.libraries.places.api.model.Place
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import ru.job4j.weatherkt.model.CurrentWeather
import ru.job4j.weatherkt.model.FiveDaysWeather
import ru.job4j.weatherkt.utils.Constants

/**
 * @author Dmitry Kolganov (mailto:dmk78@inbox.ru)
 * @version $Id$
 * @since 01.12.2019
 */
class NetworkService {
    private val mRetrofit: Retrofit

    init {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val client =
            OkHttpClient.Builder().addInterceptor(interceptor).build()
        mRetrofit = Retrofit.Builder()
            .client(client)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }


    fun getCurWeather(place: Place, callback: MutableLiveData<CurrentWeather>) {
        jSONApi.getCurrentWeatherByCoord(
            place.latLng!!.latitude, place.latLng!!.longitude,
            Constants.key, Constants.units, Constants.lang
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : DisposableSingleObserver<CurrentWeather?>() {
                override fun onSuccess(currentWeather: CurrentWeather) {
                    if (!TextUtils.isEmpty(place.name)) {
                        currentWeather.cityName = place.name
                    }
                    currentWeather.latLng = place.latLng
                    callback.postValue(currentWeather)
                }

                override fun onError(e: Throwable) {}
            })
    }

    fun getFiveDaysWeather(
        place: Place,
        callback: MutableLiveData<FiveDaysWeather>
    ) {
        jSONApi.getFiveDaysWeather(
            place.latLng!!.latitude, place.latLng!!.longitude,
            Constants.key, Constants.units, Constants.lang
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : DisposableSingleObserver<FiveDaysWeather?>() {
                override fun onSuccess(fiveDaysWeather: FiveDaysWeather) {
                    fiveDaysWeather.calculateDateTime()
                    callback.postValue(fiveDaysWeather)
                }

                override fun onError(e: Throwable) {}
            })
    }


    val jSONApi: JsonPlaceHolderApi
        get() = mRetrofit.create(JsonPlaceHolderApi::class.java)


}