package ru.job4j.weatherkt.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.info_hour.view.*
import ru.job4j.weatherkt.R
import ru.job4j.weatherkt.adapters.HoursAdapter.HoursHolder
import ru.job4j.weatherkt.model.Day
import ru.job4j.weatherkt.utils.BgColorSetter
import ru.job4j.weatherkt.utils.Utils


/**
 * @author Dmitry Kolganov (mailto:dmk78@inbox.ru)
 * @version $Id$
 * @since 10.02.2019
 */
class HoursAdapter() : RecyclerView.Adapter<HoursHolder>() {
    private var days: ArrayList<Day> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, i: Int): HoursHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.info_hour, parent, false)
        return HoursHolder(view)
    }

    fun setData(daysList: List<Day>) {
        days.clear()
        days.addAll(getWeatherFor24Hours(daysList))
        notifyDataSetChanged()
    }

    private fun getWeatherFor24Hours(days: List<Day>): List<Day> {
        val result: MutableList<Day> = ArrayList()
        for (i in 0..7) {
            result.add(days[i])
        }
        return result
    }

    override fun onBindViewHolder(holder: HoursHolder, i: Int) {
        val day = days[i]
        holder.setup(day)

    }

    override fun getItemCount(): Int {
        return days.size
    }

    class HoursHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView),
        LayoutContainer {


        fun setup(day: Day) {
            with(containerView) {
                tvHourDate.text = day.date
                tvHourTime.text = day.time
                tvHourTemp.text = Math.round(day.main.temp).toString() + " C"
                tvHourDesc.text = day.weather[0].getDescription().toString()
                tvHourDesc.isSelected = true
                ivHourWeather.setImageResource(
                    Utils.getStringIdentifier(
                        context,
                        "i" + day.weather[0].getIcon(),
                        "drawable"
                    )
                )
                hourBg.setBackgroundResource(BgColorSetter.set(Math.round(day.main.maxTemp).toFloat()))
            }
        }
    }
}