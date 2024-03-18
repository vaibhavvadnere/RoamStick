package com.iSay1.roamstick.data.repositry;


import com.iSay1.roamstick.data.api.ApiHelper;
import com.iSay1.roamstick.data.model.request.AddCareTakerRequest;
import com.iSay1.roamstick.data.model.request.AddPropertyRequest;
import com.iSay1.roamstick.data.model.request.AddPropertyTypesRequest;
import com.iSay1.roamstick.data.model.request.CreateUserRequest;
import com.iSay1.roamstick.data.model.request.DeleteCaretakerRequest;
import com.iSay1.roamstick.data.model.request.DeletePropertyRequest;
import com.iSay1.roamstick.data.model.request.DeletePropertyTypesRequest;
import com.iSay1.roamstick.data.model.request.LoginRequest;
import com.iSay1.roamstick.data.model.request.VerifyOtpRequest;
import com.iSay1.roamstick.data.model.response.AddPropertyResponse;
import com.iSay1.roamstick.data.model.response.AmenitiesResponse;
import com.iSay1.roamstick.data.model.response.BaseResponse;
import com.iSay1.roamstick.data.model.response.CaretakerDetailsResponse;
import com.iSay1.roamstick.data.model.response.ComplimentariesResponse;
import com.iSay1.roamstick.data.model.response.CreateUserResponse;
import com.iSay1.roamstick.data.model.response.LoginResponse;
import com.iSay1.roamstick.data.model.response.PropertyDetailsResponse;
import com.iSay1.roamstick.data.model.response.PropertyTypesDetailsResponse;
import com.iSay1.roamstick.data.model.response.PropertyTypesResponse;
import com.iSay1.roamstick.data.model.response.StatesResponse;
import com.iSay1.roamstick.data.model.response.UnitChargesTypesResponse;
import com.iSay1.roamstick.data.model.response.UpdatePropertyResponse;

import retrofit2.Call;

public class MainRepo {
    private final ApiHelper mApiHelper;

    public MainRepo(ApiHelper mApiHelper) {
        this.mApiHelper = mApiHelper;
    }

    public Call<CreateUserResponse> creteUser(CreateUserRequest createUserRequest) {
        return mApiHelper.creteUser(createUserRequest);
    }

    public Call<CreateUserResponse> verifyOtp(VerifyOtpRequest verifyOtpRequest) {
        return mApiHelper.verifyOtp(verifyOtpRequest);
    }

    public Call<LoginResponse> loginUser(LoginRequest user) {
        return mApiHelper.loginUser(user);
    }

    public Call<PropertyTypesResponse> getPropertyTypes() {
        return mApiHelper.getPropertyTypes();
    }

    public Call<UnitChargesTypesResponse> getUnitChargesTypes() {
        return mApiHelper.getUnitChargesTypes();
    }

    public Call<AmenitiesResponse> getAminities() {
        return mApiHelper.getAminities();
    }

    public Call<ComplimentariesResponse> getComplimentary() {
        return mApiHelper.getComplimentary();
    }

    public Call<StatesResponse> getStates() {
        return mApiHelper.getStates();
    }

    public Call<AddPropertyResponse> addProperty(AddPropertyRequest addPropertyRequest) {
        return mApiHelper.addProperty(addPropertyRequest);
    }

    public Call<UpdatePropertyResponse> updateProperty(String propertyID, AddPropertyRequest addPropertyRequest) {
        return mApiHelper.updateProperty(propertyID, addPropertyRequest);
    }

    public Call<PropertyDetailsResponse> getProperties(String userId) {
        return mApiHelper.getProperties(userId);
    }

    public Call<PropertyTypesDetailsResponse> getPropertyTypesDetails(String propertyId) {
        return mApiHelper.getPropertyTypesDetails(propertyId);
    }

    public Call<BaseResponse> deleteProperty(DeletePropertyRequest deletePropertyRequest) {
        return mApiHelper.deleteProperty(deletePropertyRequest);
    }

    public Call<CaretakerDetailsResponse> getCaretakers(String userId) {
        return mApiHelper.getCaretakers(userId);
    }

    public Call<BaseResponse> addCareTaker(AddCareTakerRequest addCareTaker) {
        return mApiHelper.addCareTaker(addCareTaker);
    }

    public Call<BaseResponse> updateCaretaker(String careTakerId, AddCareTakerRequest addCareTaker) {
        return mApiHelper.updateCaretaker(careTakerId, addCareTaker);
    }

    public Call<BaseResponse> deleteCareTaker(DeleteCaretakerRequest deleteCaretakerRequest) {
        return mApiHelper.deleteCareTaker(deleteCaretakerRequest);
    }

    public Call<BaseResponse> addPropertyTypes(AddPropertyTypesRequest addPropertyTypesRequest) {
        return mApiHelper.addPropertyTypes(addPropertyTypesRequest);
    }

    public Call<BaseResponse> deletePropertyType(DeletePropertyTypesRequest deletePropertyTypesRequest) {
        return mApiHelper.deletePropertyType(deletePropertyTypesRequest);
    }

    public Call<BaseResponse> updatePropertyTypes(String propertyId, String propertyTypeId, AddPropertyTypesRequest addPropertyTypesRequest) {
        return mApiHelper.updatePropertyTypes(propertyId, propertyTypeId, addPropertyTypesRequest);
    }
}
