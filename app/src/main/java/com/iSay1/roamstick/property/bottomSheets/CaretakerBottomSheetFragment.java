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
import com.iSay1.roamstick.data.model.response.CaretakerData;
import com.iSay1.roamstick.data.model.response.PropertyData;

public class CaretakerBottomSheetFragment extends BottomSheetDialogFragment {

    private CaretakerData caretakerData;

    public interface CaretakerBottomSheetListener {

        void onDeleteClicked(CaretakerData caretakerData);

        void onEditProperty(CaretakerData caretakerData);

    }

    public void setCaretakerData(CaretakerData mCaretakerData) {
        this.caretakerData = mCaretakerData;
    }

    private CaretakerBottomSheetListener caretakerBottomSheetListener;

    public void registerListener(CaretakerBottomSheetListener mCaretakerBottomSheetListener) {
        this.caretakerBottomSheetListener = mCaretakerBottomSheetListener;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new BottomSheetDialog(getContext(), R.style.MyTransparentBottomSheetDialogTheme);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.bottom_sheet_caretaker, container, false);

        Button btnEdit = v.findViewById(R.id.btn_edit);
        Button btnDelete = v.findViewById(R.id.btn_delete);
        Button btnCancel = v.findViewById(R.id.btn_cancel);

        TextView tvPropertyName = v.findViewById(R.id.tv_property_name);

        tvPropertyName.setText(caretakerData.getFirstName() + " " + caretakerData.getLastName());

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();

                caretakerBottomSheetListener.onEditProperty(caretakerData);
            }
        });


        btnDelete.setOnClickListener(view -> {
            dismiss();

            caretakerBottomSheetListener.onDeleteClicked(caretakerData);
        });

        btnCancel.setOnClickListener(view -> {
            dismiss();
        });


        return v;
    }
}


