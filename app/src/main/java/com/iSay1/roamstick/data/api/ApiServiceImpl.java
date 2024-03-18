package com.iSay1.roamstick.data.api;


import com.iSay1.roamstick.data.httpclient.HttpClient;
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

public class ApiServiceImpl implements ApiService {

    @Override
    public Call<CreateUserResponse> creteUser(CreateUserRequest createUserRequest) {
        return HttpClient.getHttpApi().creteUser(createUserRequest);
    }

    @Override
    public Call<CreateUserResponse> verifyOtp(VerifyOtpRequest verifyOtpRequest) {
        return HttpClient.getHttpApi().verifyOtp(verifyOtpRequest);
    }

    @Override
    public Call<LoginResponse> loginUser(LoginRequest loginRequest) {
        return HttpClient.getHttpApi().loginUser(loginRequest);
    }

    @Override
    public Call<PropertyTypesResponse> getPropertyTypes() {
        return HttpClient.getHttpApi().getPropertyTypes();
    }

    @Override
    public Call<UnitChargesTypesResponse> getUnitChargesTypes() {
        return HttpClient.getHttpApi().getUnitChargesTypes();
    }

    @Override
    public Call<AmenitiesResponse> getAminities() {
        return HttpClient.getHttpApi().getAminities();
    }

    @Override
    public Call<ComplimentariesResponse> getComplimentary() {
        return HttpClient.getHttpApi().getComplimentary();
    }

    @Override
    public Call<StatesResponse> getStates() {
        return HttpClient.getHttpApi().getStates();
    }

    @Override
    public Call<AddPropertyResponse> addProperty(AddPropertyRequest addPropertyRequest) {
        return HttpClient.getHttpApi().addProperty(addPropertyRequest);
    }

    @Override
    public Call<UpdatePropertyResponse> updateProperty(String propertyId, AddPropertyRequest addPropertyRequest) {
        return HttpClient.getHttpApi().updateProperty(propertyId, addPropertyRequest);
    }

    @Override
    public Call<PropertyDetailsResponse> getProperties(String userId) {
        return HttpClient.getHttpApi().getProperties(userId);
    }

    @Override
    public Call<PropertyTypesDetailsResponse> getPropertyTypesDetails(String propertyId) {
        return HttpClient.getHttpApi().getPropertyTypesDetails(propertyId);
    }

    @Override
    public Call<BaseResponse> deleteProperty(DeletePropertyRequest deletePropertyRequest) {
        return HttpClient.getHttpApi().deleteProperty(deletePropertyRequest);
    }

    @Override
    public Call<CaretakerDetailsResponse> getCaretakers(String userId) {
        return HttpClient.getHttpApi().getCaretakers(userId);
    }

    @Override
    public Call<BaseResponse> addCareTaker(AddCareTakerRequest addCareTakerRequest) {
        return HttpClient.getHttpApi().addCareTaker(addCareTakerRequest);
    }

    @Override
    public Call<BaseResponse> updateCaretaker(String careTakerId, AddCareTakerRequest addCareTakerRequest) {
        return HttpClient.getHttpApi().updateCaretaker(careTakerId, addCareTakerRequest);
    }

    @Override
    public Call<BaseResponse> deleteCareTaker(DeleteCaretakerRequest deleteCaretakerRequest) {
        return HttpClient.getHttpApi().deleteCareTaker(deleteCaretakerRequest);
    }

    @Override
    public Call<BaseResponse> addPropertyTypes(AddPropertyTypesRequest addPropertyTypesRequest) {
        return HttpClient.getHttpApi().addPropertyTypes(addPropertyTypesRequest);
    }

    @Override
    public Call<BaseResponse> deletePropertyType(DeletePropertyTypesRequest deletePropertyTypesRequest) {
        return HttpClient.getHttpApi().deletePropertyType(deletePropertyTypesRequest);
    }

    @Override
    public Call<BaseResponse> updatePropertyTypes(String propertyId, String propertyTypeId, AddPropertyTypesRequest addPropertyTypesRequest) {
        return HttpClient.getHttpApi().updatePropertyTypes(propertyId, propertyTypeId, addPropertyTypesRequest);
    }

}
