package com.iSay1.roamstick.property.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.iSay1.roamstick.R
import com.iSay1.roamstick.data.model.response.PropertyTypesList

class AllPropertyTypesListAdapter(
    val context: Context, var propertyTypeDataList: MutableList<PropertyTypesList?>
) : RecyclerView.Adapter<AllPropertyTypesListAdapter.ViewHolder>() {

    var selectedTpIndex: Int = -1

    lateinit var propertyTypesListener: PropertyTypesListener

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvUnitCharges: TextView
        val tvPropertyType: TextView
        val tvUnitChargesType: TextView
        val ivPropertyTypesOptions: ImageView

        init {
            tvUnitCharges = view.findViewById(R.id.tv_unit_charges)
            tvPropertyType = view.findViewById(R.id.tv_property_type)
            tvUnitChargesType = view.findViewById(R.id.tv_unit_charges_type)
            ivPropertyTypesOptions = view.findViewById(R.id.iv_property_types_options)
        }
    }

    public interface PropertyTypesListener {
        fun onItemClick(careAlertDevices: PropertyTypesList?)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.lay_property_type_row, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.tvPropertyType.text = propertyTypeDataList[position]!!.TypeName
        holder.tvUnitCharges.text = propertyTypeDataList[position]!!.ChargesPerUnit
        holder.tvUnitChargesType.text = propertyTypeDataList[position]!!.UnitChargeType

        holder.ivPropertyTypesOptions.setOnClickListener {
            propertyTypesListener.onItemClick(propertyTypeDataList[position])
        }

        /* holder.itemView.setOnClickListener {
             Log.e("cbSelectDeviceState", "  : item :  " + holder.cbSelect.isChecked)

             propertyDataList[position]!!.selected = holder.cbSelect.isChecked

             careAlertListener.onItemClick(propertyDataList[position], holder.cbSelect.isChecked)
         }*/

    }

    override fun getItemCount(): Int {
        return propertyTypeDataList.size
    }

    fun setData(_items: List<PropertyTypesList?>) {
        propertyTypeDataList.clear();
        if (_items != null) propertyTypeDataList.addAll(_items)
        notifyDataSetChanged()
    }

    fun setClickListener(mPropertyTypesListener: PropertyTypesListener) {
        this.propertyTypesListener = mPropertyTypesListener
    }


    /*@Keep
    data class AddThirdPartyDeviceAdapterModel(
        var updateEvent: AddThirdPartyDevicesFragment.ViewOnClick,
        var position: Int,
        var deviceData: ThirdPartyDevices,
    )*/

}