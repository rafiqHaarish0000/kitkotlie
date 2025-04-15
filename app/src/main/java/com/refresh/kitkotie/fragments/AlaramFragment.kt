package com.refresh.kitkotie.fragments

import android.app.AlarmManager
import android.app.AlertDialog
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import android.widget.TimePicker
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.refresh.kitkotie.R
import com.refresh.kitkotie.adapters.AlarmAdapter
import com.refresh.kitkotie.datam.Alarm
import com.refresh.kitkotie.receivers.AlarmReceiver
import java.util.Calendar

class AlaramFragment : Fragment(R.layout.fragment_alarm) {
    private val alarms = mutableListOf<Alarm>()
    private lateinit var alarmAdapter: AlarmAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Handle the back button in the fragment
        view.findViewById<ImageView>(R.id.backButton).setOnClickListener {
            requireActivity().onBackPressed()  // This will call onBackPressed in MainActivity
        }
        val intent = activity?.intent

        // You can now get extras or data from the Intent
        val data = intent?.getStringExtra("group_title")

        view.findViewById<TextView>(R.id.textView).setText(data)
        val addAlarmButton = view.findViewById<ImageView>(R.id.add_alarm)
        val alarmRecyclerView = view.findViewById<RecyclerView>(R.id.alarmRecyclerView)
        // Set up RecyclerView
        alarmAdapter = AlarmAdapter(alarms)
        alarmRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        alarmRecyclerView.adapter = alarmAdapter

        addAlarmButton.setOnClickListener {
            showAddAlarmDialog()
        }
        val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition

                val alarm = alarms[position]
                cancelAlarm(alarm)
                Toast.makeText(context,"Alarm deleted", Toast.LENGTH_SHORT).show()
                alarms.removeAt(position)
                alarmAdapter.notifyItemRemoved(position)
            }
        })

        itemTouchHelper.attachToRecyclerView(alarmRecyclerView)
    }
    private fun showAddAlarmDialog() {
        val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_add_alarm, null)
        val timePicker = dialogView.findViewById<TimePicker>(R.id.timePicker)
        val ringtoneSpinner = dialogView.findViewById<Spinner>(R.id.ringtoneSpinner)

        val ringtoneNames = arrayOf("Ringtone 1", "Ringtone 2", "Ringtone 3")
        val ringtoneUris = arrayOf(
            RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE),
            RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM),
            RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        )

        ringtoneSpinner.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, ringtoneNames)

        AlertDialog.Builder(requireContext())
            .setTitle("Set Alarm")
            .setView(dialogView)
            .setPositiveButton("Save") { _, _ ->
                val hour = timePicker.hour
                val minute = timePicker.minute
                val index = ringtoneSpinner.selectedItemPosition

                val selectedTime = String.format("%02d:%02d", hour, minute)
                val selectedUri = ringtoneUris[index]
                val selectedName = ringtoneNames[index]

                val alarm = Alarm(selectedTime, selectedName, true)
                alarms.add(alarm)
                alarmAdapter.notifyDataSetChanged()

                setAlarm(selectedTime, selectedUri, hour, minute)
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
    private fun setAlarm(time: String, ringtoneUri: Uri, hour: Int, minute: Int) {
        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute)
            set(Calendar.SECOND, 0)
        }

        val intent = Intent(requireContext(), AlarmReceiver::class.java).apply {
            putExtra("RINGTONE_URI", ringtoneUri)
        }

        val pendingIntent = PendingIntent.getBroadcast(
            requireContext(),
            time.hashCode(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val alarmManager = requireContext().getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
    }
    private fun cancelAlarm(alarm: Alarm) {
        val intent = Intent(requireContext(), AlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            requireContext(),
            alarm.time.hashCode(), // same as used in setAlarm
            intent,
            PendingIntent.FLAG_NO_CREATE or PendingIntent.FLAG_IMMUTABLE
        )

        if (pendingIntent != null) {
            val alarmManager = requireContext().getSystemService(Context.ALARM_SERVICE) as AlarmManager
            alarmManager.cancel(pendingIntent)
            pendingIntent.cancel()
        }
    }
}