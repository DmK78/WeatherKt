package ru.job4j.weatherkt.weather

import android.app.Application
import android.location.Location
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.model.Place
import ru.job4j.weatherkt.App
import ru.job4j.weatherkt.location.MyLocationImpl
import ru.job4j.weatherkt.model.CurrentWeather
import ru.job4j.weatherkt.model.FiveDaysWeather
import ru.job4j.weatherkt.network.NetworkService
import ru.job4j.weatherkt.sharedprefs.PlacePreferencesImpl
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

    fun updateWeatherByGeo() {
        val location: Location? = locationService.getLocation()

        val place =
            Place.builder().setLatLng(LatLng(location?.latitude!!, location.longitude))
                .build()
        updateWeatherByPlace(place)
    }

    val savedPlace: Place
        get() = placePreferences.loadPlace()

    val currentWeatherLiveDataResponse: MutableLiveData<CurrentWeather>
        get() = currentWeatherLiveData


    val fiveDaysWeatherLiveDataResponse: MutableLiveData<FiveDaysWeather>
        get() = fiveDaysWeatherLiveData

    fun updateWeatherByPlace(place: Place) {
      //  placePreferences.savePlace(place)
        networkService.getCurWeather(place, currentWeatherLiveData)


        networkService.getFiveDaysWeather(place, fiveDaysWeatherLiveData)
    }

    init {
        App.component.injectTo(this)
        if (!placePreferences.loadPlace().name.isNullOrBlank()) {
            updateWeatherByPlace(placePreferences.loadPlace())
        }

        /*if (TextUtils.isEmpty(place.name)) {
            onClickGeo()
        } else
            updateWeather(placePreferences.loadPlace())*/


    }
}