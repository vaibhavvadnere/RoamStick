package com.iSay1.roamstick.property.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.iSay1.roamstick.Constants
import com.iSay1.roamstick.R
import com.iSay1.roamstick.data.model.response.AmenitiesData
import com.iSay1.roamstick.data.model.response.PropertyTypeData

class AmenitiesListAdapter(
    val context: Context, var amenitiesDataList: MutableList<AmenitiesData?>,

    ) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var selectedTpIndex: Int = -1

    lateinit var careAlertListener: CareAlertListener

    lateinit var rowViewType: String

    // Define constants for view types
    private val TYPE_ONE = 1
    private val TYPE_TWO = 2

    inner class VerticalViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(amenitiesData: AmenitiesData) {

            if (amenitiesData.selected != null) {
                cbSelect.isChecked = amenitiesData.selected!!
            } else {
                cbSelect.isChecked = false
            }

            tvTitle.text = amenitiesData.Amenity

            cbSelect.setOnClickListener {

                Log.e("cbSelectDeviceState", "  :  " + cbSelect.isChecked)

                amenitiesDataList[position]!!.selected = cbSelect.isChecked

                careAlertListener.onItemClick(amenitiesDataList[position], cbSelect.isChecked)
            }
        }

        val tvTitle: TextView
        val cbSelect: CheckBox

        init {
            tvTitle = view.findViewById(R.id.tv_title)
            cbSelect = view.findViewById(R.id.check_box_select)

        }
    }

    inner class HorizontalViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(amenitiesData: AmenitiesData) {
            tvTitle.text = amenitiesData.Amenity

            ivRemove.setOnClickListener {

                careAlertListener.onItemRemove(amenitiesData)
            }

        }

        val tvTitle: TextView
        val ivRemove: ImageView

        init {
            tvTitle = view.findViewById(R.id.tv_title)
            ivRemove = view.findViewById(R.id.iv_remove)

        }
    }

    public interface CareAlertListener {
        fun onItemClick(amenitiesData: AmenitiesData?, isChecked: Boolean)
        fun onItemRemove(amenitiesData: AmenitiesData?)
    }


    override fun getItemViewType(position: Int): Int {
        Log.e("rowViewTypeLogs", " :  " + rowViewType)
        return when (rowViewType) {
            "vertical" -> {
                TYPE_ONE
            }

            "horizontal" -> {
                TYPE_TWO
            }

            else -> {
                TYPE_ONE
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return when (viewType) {
            TYPE_ONE -> {

                Log.e("rowTypeReturn", " : Type_One :")
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.lay_select_item_with_checkbox, parent, false)
                VerticalViewHolder(view)
            }

            TYPE_TWO -> {

                Log.e("rowTypeReturn", " : Type_Two :")
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.lay_selected_item, parent, false)
                HorizontalViewHolder(view)
            }

            else -> throw IllegalArgumentException("Invalid view type")
        }

        /*
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.lay_select_item_with_checkbox, parent, false)
        return ViewHolder(view)*/
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        when (holder.itemViewType) {
            TYPE_ONE -> {
                val item = amenitiesDataList[position] as AmenitiesData
                (holder as VerticalViewHolder).bind(item)
            }

            TYPE_TWO -> {
                val item = amenitiesDataList[position] as AmenitiesData
                (holder as HorizontalViewHolder).bind(item)
            }
        }


        /* holder.itemView.setOnClickListener {
             Log.e("cbSelectDeviceState", "  : item :  " + holder.cbSelect.isChecked)

             amenitiesDataList[position]!!.selected = holder.cbSelect.isChecked

             careAlertListener.onItemClick(amenitiesDataList[position], holder.cbSelect.isChecked)

         }*/

    }

    override fun getItemCount(): Int {
        return amenitiesDataList.size
    }

    fun setData(_items: List<AmenitiesData?>) {
        amenitiesDataList.clear();
        if (_items != null) amenitiesDataList.addAll(_items)
        notifyDataSetChanged()
    }

    fun setClickListener(mCareAlertListener: CareAlertListener) {
        this.careAlertListener = mCareAlertListener
    }

    fun setViewType(mViewType: String) {
        this.rowViewType = mViewType
    }


    /*@Keep
    data class AddThirdPartyDeviceAdapterModel(
        var updateEvent: AddThirdPartyDevicesFragment.ViewOnClick,
        var position: Int,
        var deviceData: ThirdPartyDevices,
    )*/

}