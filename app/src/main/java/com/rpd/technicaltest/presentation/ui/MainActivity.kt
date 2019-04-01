package com.rpd.technicaltest.presentation.ui

import android.app.DatePickerDialog
import android.graphics.Color
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v4.content.ContextCompat
import android.view.View
import android.widget.DatePicker
import android.widget.TextView
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import com.rpd.technicaltest.R
import com.rpd.technicaltest.TechnicalTestApplication
import com.rpd.technicaltest.domain.rates.model.RateView
import com.rpd.technicaltest.mvp.BaseActivity
import com.rpd.technicaltest.presentation.ui.di.component.DaggerMainActivityComponent
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*

const val RESTORE_KEY_START_DATE: String = "startDateText";
const val RESTORE_KEY_END_DATE: String = "endDateText";


class MainActivity : BaseActivity<MainActivityPresenter>(), MainActivityView{
    val calStart = Calendar.getInstance()
    val calEnd = Calendar.getInstance()

    override fun getLayout(): Int = R.layout.activity_main

    override fun initInjector() {
        DaggerMainActivityComponent.builder()
            .applicationComponent((application as TechnicalTestApplication).applicationComponent)
            .build()
            .inject(this)
    }

    override fun initialiseView() {

        initDateListeners()
        updateDateInView(startDateText, calStart)
        updateDateInView(endDateText, calEnd)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        startDateText.text = savedInstanceState?.getString(RESTORE_KEY_START_DATE);
        endDateText.text = savedInstanceState?.getString(RESTORE_KEY_END_DATE);
    }

    override fun onSaveInstanceState(outState: Bundle?, outPersistentState: PersistableBundle?) {
        outState?.putString(RESTORE_KEY_START_DATE, startDateText.text.toString())
        outState?.putString(RESTORE_KEY_END_DATE, endDateText.text.toString())
        super.onSaveInstanceState(outState, outPersistentState)
    }
    
    fun initDateListeners(){
        val startDateSetListener = object : DatePickerDialog.OnDateSetListener {
            override fun onDateSet(view: DatePicker, year: Int, monthOfYear: Int,
                                   dayOfMonth: Int) {
                calStart.set(Calendar.YEAR, year)
                calStart.set(Calendar.MONTH, monthOfYear)
                calStart.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                presenter.validateDates(calStart, calEnd)
                updateDateInView(startDateText, calStart)
            }
        }

        val endDateSetListener = object : DatePickerDialog.OnDateSetListener {
            override fun onDateSet(view: DatePicker, year: Int, monthOfYear: Int,
                                   dayOfMonth: Int) {
                calEnd.set(Calendar.YEAR, year)
                calEnd.set(Calendar.MONTH, monthOfYear)
                calEnd.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                presenter.validateDates(calStart, calEnd)
                updateDateInView(endDateText, calEnd)
            }
        }

        startDateView.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                DatePickerDialog(this@MainActivity,
                    startDateSetListener,
                    calStart.get(Calendar.YEAR),
                    calStart.get(Calendar.MONTH),
                    calStart.get(Calendar.DAY_OF_MONTH)).show()
            }

        })

        endDateView.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                DatePickerDialog(this@MainActivity,
                    endDateSetListener,
                    calEnd.get(Calendar.YEAR),
                    calEnd.get(Calendar.MONTH),
                    calEnd.get(Calendar.DAY_OF_MONTH)).show()
            }

        })
    }

    fun renderChartThread(rateViews: List<RateView>) {
        val thread = Thread(Runnable {
            runOnUiThread { renderLineChart(rateViews) }
        })
        thread.start()
    }

    fun renderLineChart(rateViews: List<RateView>) {

        val entries = rateViews.mapIndexedTo(ArrayList()) { i, model -> Entry(i.toFloat(), model.rate) }
        val labels = rateViews.map { SimpleDateFormat("dd/MM").format(it.date)}
        val dataset = LineDataSet(entries, "")
        val data = LineData(dataset)
        val desc = Description()
        val legend = linechart.legend

        dataset.setDrawFilled(true)
        dataset.setDrawHighlightIndicators(true)
        dataset.lineWidth = 1.95f
        dataset.circleRadius = 5f
        dataset.color = ContextCompat.getColor(this, R.color.colorPrimaryLight)
        dataset.setDrawCircleHole(false)
        dataset.setDrawCircles(false)
        dataset.highLightColor = Color.WHITE
        dataset.setDrawValues(false)
        linechart.setDescription(desc)
        linechart.data = data
        linechart.xAxis.valueFormatter = object : ValueFormatter(){
            override fun getFormattedValue(value: Float): String {
                val position = if (value.toInt()<labels.size) (value.toInt()) else (labels.size - 1)
                return String.format("%s", labels.get(position))
            }
        }
        linechart.xAxis.position = XAxis.XAxisPosition.BOTTOM
        linechart.xAxis.setDrawGridLines(false)
        linechart.xAxis.axisLineColor = ContextCompat.getColor(this, R.color.colorPrimaryLight)
        linechart.xAxis.textColor = ContextCompat.getColor(this, R.color.colorPrimaryLight)
        linechart.axisLeft.setDrawGridLines(false)
        linechart.axisLeft.axisLineColor = ContextCompat.getColor(this, R.color.colorPrimaryLight)
        linechart.axisLeft.textColor = ContextCompat.getColor(this, R.color.colorPrimaryLight)
        linechart.axisRight.isEnabled = false;
        linechart.setDrawBorders(false)
        linechart.setDrawGridBackground(false)
        linechart.isAutoScaleMinMaxEnabled = false
        linechart.setTouchEnabled(true)
        linechart.invalidate()
        linechart.animateXY(300, 300)
        desc.text = getString(R.string.chart_desc)
        desc.textSize = resources.getDimension(R.dimen.small_text_size)
        desc.textColor = ContextCompat.getColor(this, R.color.colorPrimaryLight)
        legend.isEnabled = false
    }

    override fun resetChart() {
        linechart.invalidate()
        linechart.clear();
    }

    override fun showRates(rateViews: List<RateView>) {
        renderChartThread(rateViews)
    }

    private fun updateDateInView(view: TextView, cal:Calendar) {
        val myFormat = "dd MMM yy" // mention the format you need
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        view.text = sdf.format(cal.getTime())
    }
}
