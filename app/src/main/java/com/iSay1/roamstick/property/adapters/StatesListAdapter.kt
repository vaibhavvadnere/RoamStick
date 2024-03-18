package com.iSay1.roamstick.property.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.iSay1.roamstick.R
import com.iSay1.roamstick.data.model.response.StatesData

class StatesListAdapter(
    val context: Context, var statesDataList: MutableList<StatesData?>
) : RecyclerView.Adapter<StatesListAdapter.ViewHolder>() {

    var selectedTpIndex: Int = -1

    lateinit var careAlertListener: CareAlertListener

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvTitle: TextView
        val cbSelect: CheckBox

        init {
            tvTitle = view.findViewById(R.id.tv_title)
            cbSelect = view.findViewById(R.id.check_box_select)

        }
    }

    public interface CareAlertListener {
        fun onItemClick(statesData: StatesData?, isChecked: Boolean)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.lay_select_item_with_checkbox, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.cbSelect.isChecked = statesDataList[position]!!.selected

        holder.tvTitle.text = statesDataList[position]!!.StateName

        holder.cbSelect.setOnClickListener {

            Log.e("cbSelectDeviceState", "  :  " + holder.cbSelect.isChecked)

            statesDataList[position]!!.selected = holder.cbSelect.isChecked

            careAlertListener.onItemClick(
                statesDataList[position], holder.cbSelect.isChecked
            )
        }

        /* holder.itemView.setOnClickListener {
             Log.e("cbSelectDeviceState", "  : item :  " + holder.cbSelect.isChecked)
 
             complimentaryDataList[position]!!.selected = holder.cbSelect.isChecked
 
             careAlertListener.onItemClick(complimentaryDataList[position], holder.cbSelect.isChecked)
 
         }*/

    }

    override fun getItemCount(): Int {
        return statesDataList.size
    }

    fun setData(_items: List<StatesData?>) {
        statesDataList.clear();
        if (_items != null) statesDataList.addAll(_items)
        notifyDataSetChanged()
    }

    fun setClickListener(mCareAlertListener: CareAlertListener) {
        this.careAlertListener = mCareAlertListener
    }


    /*@Keep
    data class AddThirdPartyDeviceAdapterModel(
        var updateEvent: AddThirdPartyDevicesFragment.ViewOnClick,
        var position: Int,
        var deviceData: ThirdPartyDevices,
    )*/

}