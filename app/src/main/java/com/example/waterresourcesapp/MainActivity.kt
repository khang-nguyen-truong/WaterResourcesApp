package com.example.waterresourcesapp

import android.graphics.Typeface
import android.os.Bundle
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.widget.TableRow
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.waterresourcesapp.databinding.ActivityMainBinding
import java.io.Serializable
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    class WaterResource(
        val name: String,
        val location: String,
        val type: String,
    ) : Serializable

    private lateinit var binding: ActivityMainBinding
    private lateinit var waterResourcesList: MutableList<WaterResource>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        waterResourcesList = mutableListOf(
            WaterResource("River Ganges", "Varanasi, India", "River"),
            WaterResource("Lake Michigan", "Chicago, Illinois, USA", "Lake"),
            WaterResource("Nile River", "Cairo, Egypt", "Canal"),
            WaterResource("Colorado River", "Grand Canyon, Arizona, USA", "Reservoir"),
            WaterResource("Lake Baikal", "Siberia, Russia", "Pond"),
        )
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        displayTableHeaders()
        displayTableAllRows()
    }

    fun buttonAddWaterResourceOnClick(view: View) {
        val waterResourceName = binding.editTextName.text.toString()
        val location = binding.editTextLocation.text.toString()
        val type = binding.editTextType.text.toString()

        // check if any field is blank
        if (waterResourceName.isBlank() or location.isBlank() or type.isBlank()) {
            Toast.makeText(this, R.string.box_blank, Toast.LENGTH_LONG).show()
        } else {
            // check if water resource already exists
            if (waterResourcesList.any{
                    it.name.equals(waterResourceName, true)
                }) {
                Toast.makeText(this, R.string.resource_already_exists, Toast.LENGTH_LONG).show()
            }
            // add resource after going through every check
            else {
                waterResourcesList.add(WaterResource(waterResourceName, location, type))
                displayTableAllRows()

                Toast.makeText(this, R.string.resource_added_success, Toast.LENGTH_LONG).show()
                binding.editTextName.text.clear()
                binding.editTextLocation.text.clear()
                binding.editTextType.text.clear()
            }
        }
    }

    private fun displayTableHeaders() {
        // show the headers
        val row = TableRow(this)

        val waterResourceName = createTextView(getString(R.string.water_resource_title))
        waterResourceName.setTypeface(null, Typeface.BOLD)
        val location = createTextView(getString(R.string.location_title))
        location.setTypeface(null, Typeface.BOLD)
        val type = createTextView(getString(R.string.type_title))
        type.setTypeface(null, Typeface.BOLD)

        row.addView(waterResourceName)
        row.addView(location)
        row.addView(type)

        binding.tableWaterResources.addView(row)
    }

    private fun displayTableAllRows() {
        // clear every row
        binding.tableWaterResources.removeViews(1, binding.tableWaterResources.childCount - 1)
        // show new rows one by one
        for (waterResource in waterResourcesList) {
            displayTableOneRow(waterResource)
        }
    }

    private fun displayTableOneRow(waterResource: WaterResource) {
        // show one row
        val row = TableRow(this)

        val waterResourceName = createTextView(waterResource.name)
        val location = createTextView(waterResource.location)
        val type = createTextView(waterResource.type)

        row.addView(waterResourceName)
        row.addView(location)
        row.addView(type)

        binding.tableWaterResources.addView(row)
    }

    private fun createTextView(text: String): TextView {
        // get a string and return a textView
        val textView = TextView(this)
        textView.text = text
        textView.setPadding(10, 10, 10, 10)

        return textView
    }

}
