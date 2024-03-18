package com.iSay1.roamstick.property.bottomSheets;


import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.iSay1.roamstick.R;
import com.iSay1.roamstick.data.model.response.PropertyData;

public class PropertyBottomSheetFragment extends BottomSheetDialogFragment {

    private PropertyData propertyData;

    public interface PropertyBottomSheetListener {
        void onPropertyType(PropertyData propertyData);

        void onDeleteClicked(PropertyData propertyData);

        void onEditProperty(PropertyData propertyData);

        void onMapLocation(PropertyData propertyData);
    }

    public void setPropertyData(PropertyData mPropertyData) {
        this.propertyData = mPropertyData;
    }

    private PropertyBottomSheetListener propertyBottomSheetListener;

    public void registerListener(PropertyBottomSheetListener mPropertyBottomSheetListener) {
        this.propertyBottomSheetListener = mPropertyBottomSheetListener;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new BottomSheetDialog(getContext(), R.style.MyTransparentBottomSheetDialogTheme);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.bottom_sheet_property, container, false);

        Button btnEdit = v.findViewById(R.id.btn_edit);
        Button btnPropertyTypes = v.findViewById(R.id.btn_property_types);
        Button btnDelete = v.findViewById(R.id.btn_delete);
        Button btnCancel = v.findViewById(R.id.btn_cancel);

        TextView tvPropertyName = v.findViewById(R.id.tv_property_name);

        tvPropertyName.setText(propertyData.getName());

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();

                propertyBottomSheetListener.onEditProperty(propertyData);
            }
        });

        btnPropertyTypes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                propertyBottomSheetListener.onPropertyType(propertyData);
            }
        });

        btnDelete.setOnClickListener(view -> {
            dismiss();

            propertyBottomSheetListener.onDeleteClicked(propertyData);
        });

        btnCancel.setOnClickListener(view -> {
            dismiss();
        });


        return v;
    }
}


