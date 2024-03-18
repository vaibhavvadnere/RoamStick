package com.iSay1.roamstick.data.api;


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

public class ApiHelper {
    private final ApiService mApiService;

    public ApiHelper(ApiService mApiService) {
        this.mApiService = mApiService;
    }

    public Call<CreateUserResponse> creteUser(CreateUserRequest createUserRequest) {
        return mApiService.creteUser(createUserRequest);
    }

    public Call<CreateUserResponse> verifyOtp(VerifyOtpRequest verifyOtpRequest) {
        return mApiService.verifyOtp(verifyOtpRequest);
    }

    public Call<LoginResponse> loginUser(LoginRequest loginRequest) {
        return mApiService.loginUser(loginRequest);
    }

    public Call<PropertyTypesResponse> getPropertyTypes() {
        return mApiService.getPropertyTypes();
    }

    public Call<UnitChargesTypesResponse> getUnitChargesTypes() {
        return mApiService.getUnitChargesTypes();
    }

    public Call<AmenitiesResponse> getAminities() {
        return mApiService.getAminities();
    }

    public Call<ComplimentariesResponse> getComplimentary() {
        return mApiService.getComplimentary();
    }

    public Call<StatesResponse> getStates() {
        return mApiService.getStates();
    }

    public Call<AddPropertyResponse> addProperty(AddPropertyRequest addPropertyRequest) {
        return mApiService.addProperty(addPropertyRequest);
    }

    public Call<UpdatePropertyResponse> updateProperty(String propertyID, AddPropertyRequest addPropertyRequest) {
        return mApiService.updateProperty(propertyID, addPropertyRequest);
    }

    public Call<PropertyDetailsResponse> getProperties(String userId) {
        return mApiService.getProperties(userId);
    }
    public Call<PropertyTypesDetailsResponse> getPropertyTypesDetails(String propertyId) {
        return mApiService.getPropertyTypesDetails(propertyId);
    }

    public Call<BaseResponse> deleteProperty(DeletePropertyRequest deletePropertyRequest) {
        return mApiService.deleteProperty(deletePropertyRequest);
    }

    public Call<CaretakerDetailsResponse> getCaretakers(String userId) {
        return mApiService.getCaretakers(userId);
    }

    public Call<BaseResponse> addCareTaker(AddCareTakerRequest addCareTakerRequest) {
        return mApiService.addCareTaker(addCareTakerRequest);
    }

    public Call<BaseResponse> updateCaretaker(String careTakerId,AddCareTakerRequest addCareTakerRequest) {
        return mApiService.updateCaretaker(careTakerId,addCareTakerRequest);
    }

    public Call<BaseResponse> deleteCareTaker(DeleteCaretakerRequest deleteCaretakerRequest) {
        return mApiService.deleteCareTaker(deleteCaretakerRequest);
    }

    public Call<BaseResponse> addPropertyTypes(AddPropertyTypesRequest addPropertyTypesRequest) {
        return mApiService.addPropertyTypes(addPropertyTypesRequest);
    }

    public Call<BaseResponse> deletePropertyType(DeletePropertyTypesRequest deletePropertyTypesRequest) {
        return mApiService.deletePropertyType(deletePropertyTypesRequest);
    }

    public Call<BaseResponse> updatePropertyTypes(String propertyId, String propertyTypeId, AddPropertyTypesRequest addPropertyTypesRequest) {
        return mApiService.updatePropertyTypes(propertyId, propertyTypeId, addPropertyTypesRequest);
    }

}
