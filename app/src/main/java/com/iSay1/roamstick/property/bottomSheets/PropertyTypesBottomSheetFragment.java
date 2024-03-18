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
import com.iSay1.roamstick.data.model.response.PropertyTypesList;

public class PropertyTypesBottomSheetFragment extends BottomSheetDialogFragment {

    private PropertyTypesList propertyType;

    public interface PropertyTypesBottomSheetListener {

        void onDeleteClicked(PropertyTypesList propertyType);

        void onEditPropertyType(PropertyTypesList propertyType);
        void onPropertyImages(PropertyTypesList propertyType);

    }

    public void setPropertyData(PropertyTypesList mPropertyType) {
        this.propertyType = mPropertyType;
    }

    private PropertyTypesBottomSheetListener propertyTypesBottomSheetListener;

    public void registerListener(PropertyTypesBottomSheetListener mPropertyTypesBottomSheetListener) {
        this.propertyTypesBottomSheetListener = mPropertyTypesBottomSheetListener;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new BottomSheetDialog(getContext(), R.style.MyTransparentBottomSheetDialogTheme);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.bottom_sheet_property_types, container, false);

        Button btnEdit = v.findViewById(R.id.btn_edit);
        Button btnDelete = v.findViewById(R.id.btn_delete);
        Button btnImages = v.findViewById(R.id.btn_images);
        Button btnCancel = v.findViewById(R.id.btn_cancel);

        TextView tvPropertyName = v.findViewById(R.id.tv_property_name);

        tvPropertyName.setText(propertyType.getTypeName());

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();

                propertyTypesBottomSheetListener.onEditPropertyType(propertyType);
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        btnImages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                propertyTypesBottomSheetListener.onPropertyImages(propertyType);

            }
        });

        btnDelete.setOnClickListener(view -> {
            dismiss();

            propertyTypesBottomSheetListener.onDeleteClicked(propertyType);
        });


        return v;
    }
}


