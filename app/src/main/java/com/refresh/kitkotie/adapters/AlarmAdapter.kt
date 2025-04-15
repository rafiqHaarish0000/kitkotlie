package com.refresh.kitkotie.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.Switch
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.refresh.kitkotie.datam.Alarm
import com.refresh.kitkotie.R

class AlarmAdapter(private val alarms: MutableList<Alarm>) :
    RecyclerView.Adapter<AlarmAdapter.AlarmViewHolder>() {

    class AlarmViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val timeText: TextView = itemView.findViewById(R.id.alarmTime)
        val ringtoneText: TextView = itemView.findViewById(R.id.alarmRingtone)
        val alarmSwitch: Switch = itemView.findViewById(R.id.alarmSwitch)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlarmViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_alarm, parent, false)
        return AlarmViewHolder(view)
    }

    override fun onBindViewHolder(holder: AlarmViewHolder, position: Int) {
        val alarm = alarms[position]
        holder.timeText.text = alarm.time
        holder.ringtoneText.text = alarm.ringtoneName
        holder.alarmSwitch.isChecked = alarm.isOn
    }
    fun removeItem(position: Int) {
        alarms.removeAt(position)
        notifyItemRemoved(position)
    }

    override fun getItemCount(): Int = alarms.size
}