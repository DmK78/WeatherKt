package ru.job4j.weatherkt.weather

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.gms.common.api.Status
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import kotlinx.android.synthetic.main.activity_weather.*
import kotlinx.android.synthetic.main.activity_weather.view.*
import ru.job4j.weatherkt.R
import ru.job4j.weatherkt.adapters.DaysAdapter
import ru.job4j.weatherkt.adapters.HoursAdapter
import ru.job4j.weatherkt.model.CurrentWeather
import ru.job4j.weatherkt.model.Day
import ru.job4j.weatherkt.model.FiveDaysWeather
import ru.job4j.weatherkt.utils.BgColorSetter
import ru.job4j.weatherkt.utils.Utils
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt


/**
 * @author Dmitry Kolganov (mailto:dmk78@inbox.ru)
 * @version $Id$
 * @since 01.12.2019
 */
class WeatherFragment : Fragment() {
    private val REQUEST_LOCATION_PERMISSION = 1
    private lateinit var daysAdapter: DaysAdapter
    private lateinit var hoursAdapter: HoursAdapter
    private lateinit var mSwipeRefreshLayout: SwipeRefreshLayout
    private lateinit var viewModel: WeatherViewModel

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.activity_weather, container, false)
        checkLocPermissions()
        viewModel = ViewModelProviders.of(this).get(WeatherViewModel::class.java)
        setupAdapters(view)
        mSwipeRefreshLayout = view.findViewById(R.id.swipe)
        mSwipeRefreshLayout.setOnRefreshListener {
            updateUi(viewModel.savedPlace)
        }
        Places.initialize(context!!, getString(R.string.google_maps_key))
        val search =
            this.childFragmentManager.findFragmentById(R.id.autocomplete_fragment) as AutocompleteSupportFragment?
        search!!.setPlaceFields(
            Arrays.asList(
                Place.Field.ID,
                Place.Field.NAME,
                Place.Field.LAT_LNG
            )
        )
        search.setOnPlaceSelectedListener(object : PlaceSelectionListener {
            override fun onPlaceSelected(place: Place) {
                updateUi(place)
            }

            override fun onError(status: Status) {
                Log.i(
                    WeatherFragment::class.java.name,
                    "An error occurred: $status"
                )
            }
        })
        view.imageViewGetLocation.setOnClickListener { viewModel.onClickGeo() }
        viewModel.currentWeatherLiveDataResponse
            .observe(viewLifecycleOwner, Observer<CurrentWeather> { weather: CurrentWeather? ->
                if (weather != null) {
                    val date = Date()
                    date.time = weather.dt!!.toLong() * 1000
                    val formatForDateNow =
                        SimpleDateFormat("dd.MM.yyyy EEE")
                    textViewCurrentCity.text = "${formatForDateNow.format(date)}\n${weather.cityName}, ${weather.sys?.country}"
                    textViewCurrentTemp.text = "${weather.main!!.temp.roundToInt()} C"
                    textViewCurrentTempMin.text = "${weather.main.minTemp.roundToInt()} C"
                    textViewPressure.text = "${weather.main.pressure.roundToInt()} мм"
                    textViewWeatherDesc.text = weather.weather!![0].description
                    textViewWindSpeed.text = "${weather.wind!!.speed} м/с"
                    textViewHumidity.text = "${resources.getString(R.string.humidity)} ${weather.main.humidity.roundToInt()} %"
                    imageViewCurrent.setImageResource(
                        Utils.getStringIdentifier(
                            context,
                            "i" + weather.weather[0].icon,
                            "drawable"
                        )
                    )
                    imageViewWind.animate().rotation(weather.wind.degree)
                        .setDuration(1000).start()
                    bgMain.setBackgroundResource(BgColorSetter.set(weather.main.maxTemp))

                    mSwipeRefreshLayout.isRefreshing = false


                }
            })
        viewModel.fiveDaysWeatherLiveDataResponse.observe(
            this,
            Observer<FiveDaysWeather> { fiveDaysWeather: FiveDaysWeather? ->
                fiveDaysWeather?.let {
                    val dayList: List<Day>? = fiveDaysWeather.days
                    dayList?.let {
                        hoursAdapter.setData(it)
                        daysAdapter.setData(it)
                    }
                }
            }
        )
        val place = viewModel.savedPlace
        if (TextUtils.isEmpty(place.name)) {
            viewModel.onClickGeo()
        } else updateUi(place)
        return view
    }

    private fun setupAdapters(view: View) {
        val recyclerViewDays = view.findViewById(R.id.resyclerDays) as RecyclerView
        recyclerViewDays.layoutManager = LinearLayoutManager(context)
        daysAdapter = DaysAdapter()
        recyclerViewDays.adapter = daysAdapter

        val recyclerViewHours = view.findViewById(R.id.recyclerHours) as RecyclerView

        recyclerViewHours.layoutManager = LinearLayoutManager(
            context, LinearLayoutManager.HORIZONTAL, false
        )
        hoursAdapter = HoursAdapter()
        recyclerViewHours.adapter = hoursAdapter

    }

    private fun updateUi(place: Place) {
        mSwipeRefreshLayout.isRefreshing = true
        viewModel.updateWeather(place)
    }

    fun checkLocPermissions() {
        if (ContextCompat.checkSelfPermission(
                context!!,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
            != PackageManager.PERMISSION_GRANTED ||
            ContextCompat.checkSelfPermission(
                context!!,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                activity!!,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_LOCATION_PERMISSION
            )
            ActivityCompat.requestPermissions(
                activity!!,
                arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),
                REQUEST_LOCATION_PERMISSION
            )
        }
    }
}