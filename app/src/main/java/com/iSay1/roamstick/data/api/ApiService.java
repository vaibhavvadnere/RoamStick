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

public interface ApiService {

    Call<CreateUserResponse> creteUser(CreateUserRequest createUserRequest);
    Call<CreateUserResponse> verifyOtp(VerifyOtpRequest verifyOtpRequest);

    Call<LoginResponse> loginUser(LoginRequest loginRequest);

    Call<PropertyTypesResponse> getPropertyTypes();

    Call<UnitChargesTypesResponse> getUnitChargesTypes();

    Call<AmenitiesResponse> getAminities();

    Call<ComplimentariesResponse> getComplimentary();

    Call<StatesResponse> getStates();

    Call<AddPropertyResponse> addProperty(AddPropertyRequest addPropertyRequest);

    Call<UpdatePropertyResponse> updateProperty(String propertyId, AddPropertyRequest addPropertyRequest);

    Call<PropertyDetailsResponse> getProperties(String userId);
    Call<PropertyTypesDetailsResponse> getPropertyTypesDetails(String propertyId);

    Call<BaseResponse> deleteProperty(DeletePropertyRequest deletePropertyRequest);

    Call<CaretakerDetailsResponse> getCaretakers(String userId);

    Call<BaseResponse> addCareTaker(AddCareTakerRequest addCareTakerRequest);

    Call<BaseResponse> updateCaretaker(String careTakerId, AddCareTakerRequest addCareTakerRequest);

    Call<BaseResponse> deleteCareTaker(DeleteCaretakerRequest deleteCaretakerRequest);

    Call<BaseResponse> addPropertyTypes(AddPropertyTypesRequest addPropertyTypesRequest);

    Call<BaseResponse> deletePropertyType(DeletePropertyTypesRequest deletePropertyTypesRequest);

    Call<BaseResponse> updatePropertyTypes(String propertyId, String propertyTypeId, AddPropertyTypesRequest addPropertyTypesRequest);
}


