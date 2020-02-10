package ru.job4j.weatherkt.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.info_day.view.*
import ru.job4j.weatherkt.R
import ru.job4j.weatherkt.adapters.DaysAdapter.DayHolder
import ru.job4j.weatherkt.model.Day
import ru.job4j.weatherkt.utils.BgColorSetter
import ru.job4j.weatherkt.utils.Utils
import java.util.*

/**
 * @author Dmitry Kolganov (mailto:dmk78@inbox.ru)
 * @version $Id$
 * @since 01.12.2019
 */
class DaysAdapter() : RecyclerView.Adapter<DayHolder>() {
    private var days: ArrayList<Day> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, i: Int): DaysAdapter.DayHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.info_day, parent, false)
        return DaysAdapter.DayHolder(view)
    }

    fun setData(daysList: List<Day>) {
        days.clear()
        days.addAll(convertToShort(daysList))
        notifyDataSetChanged()
    }

    private fun convertToShort(days: List<Day>): List<Day> {
        var baseData = days[0].date
        var minTemp = +100f
        var maxTemp = -100f
        val result: MutableList<Day> = ArrayList()
        for (i in days.indices) {
            val day = days[i]
            val tmp = day.date
            day.date = tmp
            if (day.date == baseData) {
                if (day.main.minTemp < minTemp) {
                    minTemp = day.main.minTemp
                }
                if (day.main.maxTemp > maxTemp) {
                    maxTemp = day.main.maxTemp
                }
                continue
            } else {
                val dayPrev = days[i - 1]
                baseData = day.date
                dayPrev.main.maxTemp = maxTemp
                dayPrev.main.minTemp = minTemp
                maxTemp = -100f
                minTemp = +100f
                result.add(dayPrev)
            }
        }
        return result
    }

    override fun onBindViewHolder(holder: DayHolder, i: Int) {
        val day = days[i]
        holder.setup(day)
    }

    override fun getItemCount(): Int {
        return days.size
    }

    class DayHolder(override val containerView: View) :
        RecyclerView.ViewHolder(containerView),
        LayoutContainer {
        fun setup(day: Day) {
            with(containerView) {

                textViewDayDate.text = day.date
                textViewDayTempMax.text = Math.round(day.main.maxTemp).toString() + " C"
                textViewDayTempMin.text = Math.round(day.main.minTemp).toString() + " C"
                imageViewDay.setImageResource(
                    Utils.getStringIdentifier(
                        context,
                        "i" + day.weather[0].getIcon(),
                        "drawable"
                    )
                )
                dayBg.setBackgroundResource(BgColorSetter.set(Math.round(day.main.maxTemp).toFloat()))
            }
        }
    }

}