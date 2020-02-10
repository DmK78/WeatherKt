package ru.job4j.weatherkt.weather

import android.app.Application
import android.location.Location
import android.text.TextUtils
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.model.Place
import ru.job4j.weatherkt.App
import ru.job4j.weatherkt.model.CurrentWeather
import ru.job4j.weatherkt.model.FiveDaysWeather
import ru.job4j.weatherkt.network.NetworkService
import ru.job4j.weatherkt.utils.MyLocationImpl
import ru.job4j.weatherkt.utils.PlacePreferencesImpl
import javax.inject.Inject

class WeatherViewModel(application: Application) : AndroidViewModel(application) {
    @Inject
    lateinit var placePreferences: PlacePreferencesImpl
    @Inject
    lateinit var networkService: NetworkService
    @Inject
    lateinit var locationService: MyLocationImpl
    private val currentWeatherLiveData = MutableLiveData<CurrentWeather>()
    private val fiveDaysWeatherLiveData = MutableLiveData<FiveDaysWeather>()

    fun onClickGeo() {
        val location: Location = locationService.getLocation()
        val place =
            Place.builder().setLatLng(LatLng(location.latitude, location.longitude))
                .build()
        updateWeather(place)
    }

    val savedPlace: Place
        get() = placePreferences.loadPlace()

    val currentWeatherLiveDataResponse: MutableLiveData<CurrentWeather>
        get() = currentWeatherLiveData


    val fiveDaysWeatherLiveDataResponse: MutableLiveData<FiveDaysWeather>
        get() = fiveDaysWeatherLiveData

    fun updateWeather(place: Place) {
        placePreferences.savePlace(place)
        networkService.getCurWeather(place, currentWeatherLiveData)
        networkService.getFiveDaysWeather(place, fiveDaysWeatherLiveData)
    }

    init {
        App.component.inject(this)
        val place = placePreferences.loadPlace()
        if (TextUtils.isEmpty(place.name)) {
            onClickGeo()
        } else
            updateWeather(placePreferences.loadPlace())


    }
}