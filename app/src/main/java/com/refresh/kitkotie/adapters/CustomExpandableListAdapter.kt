package com.refresh.kitkotie.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.refresh.kitkotie.R
import com.refresh.kitkotie.activities.DetailsAct

class CustomExpandableListAdapter(
    private val context: Context,
    private val groupList: List<String>,
    private val childMap: Map<String, String>
) : BaseExpandableListAdapter() {

    override fun getGroupCount() = groupList.size

    override fun getChildrenCount(groupPosition: Int) = 1

    override fun getGroup(groupPosition: Int) = groupList[groupPosition]

    override fun getChild(groupPosition: Int, childPosition: Int) =
        childMap[groupList[groupPosition]]

    override fun getGroupId(groupPosition: Int) = groupPosition.toLong()

    override fun getChildId(groupPosition: Int, childPosition: Int) = childPosition.toLong()

    override fun hasStableIds() = false

    override fun getGroupView(
        groupPosition: Int, isExpanded: Boolean, convertView: View?, parent: ViewGroup?
    ): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.group_item, parent, false)
        val textView = view.findViewById<TextView>(R.id.groupTitle)
        val indicator = view.findViewById<ImageView>(R.id.groupIndicator)
        textView.text = getGroup(groupPosition)
        // Toggle arrow
        if (isExpanded) {
            indicator.setImageResource(R.drawable.baseline_keyboard_arrow_up_24)
        } else {
            indicator.setImageResource(R.drawable.baseline_keyboard_arrow_down_24)
        }
        return view
    }

    override fun getChildView(
        groupPosition: Int, childPosition: Int, isLastChild: Boolean, convertView: View?, parent: ViewGroup?
    ): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.child_item, parent, false)
        val textView = view.findViewById<TextView>(R.id.childText)
        val button = view.findViewById<Button>(R.id.childButton)

        textView.text = getChild(groupPosition, childPosition)

        button.setOnClickListener {
            Toast.makeText(context, "Clicked button in ${getGroup(groupPosition)}", Toast.LENGTH_SHORT).show()
            val intent = Intent(context, DetailsAct::class.java)
            intent.putExtra("group_title", getGroup(groupPosition) as String)
            context.startActivity(intent)
        }

        return view
    }

    override fun isChildSelectable(groupPosition: Int, childPosition: Int) = true
}