package com.iSay1.roamstick.property.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.iSay1.roamstick.R
import com.iSay1.roamstick.data.model.response.PropertyData

class PropertyListAdapter(
    val context: Context, var propertiesList: MutableList<PropertyData?>
) : RecyclerView.Adapter<PropertyListAdapter.ViewHolder>() {

    lateinit var propertyListListener: PropertyListListener

    var selectedTpIndex: Int = -1

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvAddress: TextView
        val tvPropertyName: TextView
        val tvNumberOfProperties: TextView
        val ivPropertyOptions: ImageView

        init {
            tvAddress = view.findViewById(R.id.tv_address)
            tvPropertyName = view.findViewById(R.id.tv_property_name)
            tvNumberOfProperties = view.findViewById(R.id.tv_number_of_properties)
            ivPropertyOptions = view.findViewById(R.id.iv_property_options)
        }
    }

    public interface PropertyListListener {
        fun onItemClick(statesData: PropertyData?)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.lay_property_row, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.tvPropertyName.text = propertiesList[position]!!.Name
        holder.tvAddress.text =
            propertiesList[position]!!.AddressLine1 + ", " + propertiesList[position]!!.AddressLine2 + "," + propertiesList[position]!!.AddressLine3 + "," + propertiesList[position]!!.Landmark + "," + propertiesList[position]!!.Pincode


        if (propertiesList[position]!!.PropertyType!=null) {
            holder.tvNumberOfProperties.text = propertiesList[position]!!.PropertyType!!.size.toString()
        }else{
            holder.tvNumberOfProperties.text = "0"
        }
        holder.ivPropertyOptions.setOnClickListener {
            propertyListListener.onItemClick(propertiesList[position])
        }
    }

    override fun getItemCount(): Int {
        return propertiesList.size
    }

    fun setData(_items: List<PropertyData?>) {
        propertiesList.clear();
        if (_items != null) propertiesList.addAll(_items)
        notifyDataSetChanged()
    }

    fun setClickListener(mPropertyListListener: PropertyListListener) {
        this.propertyListListener = mPropertyListListener
    }


    /*@Keep
    data class AddThirdPartyDeviceAdapterModel(
        var updateEvent: AddThirdPartyDevicesFragment.ViewOnClick,
        var position: Int,
        var deviceData: ThirdPartyDevices,
    )*/

}