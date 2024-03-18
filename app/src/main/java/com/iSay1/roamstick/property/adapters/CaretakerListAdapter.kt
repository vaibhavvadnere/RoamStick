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
import com.iSay1.roamstick.R
import com.iSay1.roamstick.data.model.response.AmenitiesData
import com.iSay1.roamstick.data.model.response.CaretakerData
import com.iSay1.roamstick.data.model.response.StatesData

class CaretakerListAdapter(
    val context: Context, var careTakerDataList: MutableList<CaretakerData?>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var selectedTpIndex: Int = -1

    lateinit var caretakerListener: CaretakerListener

    lateinit var rowViewType: String

    // Define constants for view types
    private val TYPE_ONE = 1
    private val TYPE_TWO = 2


    inner class VerticalViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(caretakerData: CaretakerData) {

            cbSelect.isChecked = caretakerData.Selected

            tvTitle.text = caretakerData.FirstName + " " + caretakerData.LastName

            cbSelect.setOnClickListener {
                Log.e("cbSelectCaretaker", "  :  " + cbSelect.isChecked)

                caretakerData.Selected = cbSelect.isChecked

                caretakerListener.onItemClick(
                    caretakerData, cbSelect.isChecked
                )
            }
        }

        val tvTitle: TextView
        val cbSelect: CheckBox

        init {
            tvTitle = view.findViewById(R.id.tv_title)
            cbSelect = view.findViewById(R.id.check_box_select)

        }
    }

    inner class DetailedViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(caretakerData: CaretakerData) {
            tvCaretakerName.text = caretakerData.FirstName + " " + caretakerData.LastName
            tvPhoneNumber.text = caretakerData.MobileNumber

            tvAddress.text =
                caretakerData.Address + ", " + caretakerData.CityDistrictTown + ", " + caretakerData.Landmark + ", " + caretakerData.StateId

            ivCaretakerOptions.setOnClickListener {
                caretakerListener.onItemClick(caretakerData, true)
            }

        }

        val tvCaretakerName: TextView
        val tvPhoneNumber: TextView
        val tvAddress: TextView
        val ivCaretakerOptions: ImageView

        init {
            tvCaretakerName = view.findViewById(R.id.tv_caretaker_name)
            tvPhoneNumber = view.findViewById(R.id.tv_phone_number)
            tvAddress = view.findViewById(R.id.tv_address)
            ivCaretakerOptions = view.findViewById(R.id.iv_caretaker_options)

        }
    }


    /* class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
         val tvTitle: TextView
         val cbSelect: CheckBox

         init {
             tvTitle = view.findViewById(R.id.tv_title)
             cbSelect = view.findViewById(R.id.check_box_select)

         }
     }*/

    public interface CaretakerListener {
        fun onItemClick(caretakerData: CaretakerData?, isChecked: Boolean)
    }

    override fun getItemViewType(position: Int): Int {
        Log.e("rowViewTypeLogs", " :  " + rowViewType)
        return when (rowViewType) {
            "selective" -> {
                TYPE_ONE
            }

            "detailed" -> {
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
                val view = LayoutInflater.from(parent.context).inflate(R.layout.lay_select_item_with_checkbox, parent, false)
                VerticalViewHolder(view)
            }

            TYPE_TWO -> {

                Log.e("rowTypeReturn", " : Type_Two :")
                val view = LayoutInflater.from(parent.context).inflate(R.layout.lay_row_caretaker_item, parent, false)
                DetailedViewHolder(view)
            }

            else -> throw IllegalArgumentException("Invalid view type")
        }


        /* val view = LayoutInflater.from(parent.context).inflate(R.layout.lay_select_item_with_checkbox, parent, false)
         return ViewHolder(view)*/
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        when (holder.itemViewType) {
            TYPE_ONE -> {
                val item = careTakerDataList[position] as CaretakerData
                (holder as VerticalViewHolder).bind(item)
            }

            TYPE_TWO -> {
                val item = careTakerDataList[position] as CaretakerData
                (holder as DetailedViewHolder).bind(item)
            }
        }

        /*holder.cbSelect.isChecked = careTakerDataList[position]!!.Selected

        holder.tvTitle.text = careTakerDataList[position]!!.FirstName + " " + careTakerDataList[position]!!.LastName

        holder.cbSelect.setOnClickListener {

            Log.e("cbSelectCaretaker", "  :  " + holder.cbSelect.isChecked)

            careTakerDataList[position]!!.Selected = holder.cbSelect.isChecked

            caretakerListener.onItemClick(
                careTakerDataList[position], holder.cbSelect.isChecked
            )
        }*/

    }

    override fun getItemCount(): Int {
        return careTakerDataList.size
    }

    fun setData(_items: List<CaretakerData?>) {
        careTakerDataList.clear();
        if (_items != null) careTakerDataList.addAll(_items)
        notifyDataSetChanged()
    }

    fun setViewType(mViewType: String) {
        this.rowViewType = mViewType
    }

    fun setClickListener(mCaretakerListener: CaretakerListener) {
        this.caretakerListener = mCaretakerListener
    }


    /*@Keep
    data class AddThirdPartyDeviceAdapterModel(
        var updateEvent: AddThirdPartyDevicesFragment.ViewOnClick,
        var position: Int,
        var deviceData: ThirdPartyDevices,
    )*/

}