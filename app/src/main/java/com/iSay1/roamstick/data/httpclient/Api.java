package com.iSay1.roamstick.data.httpclient;


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
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface Api {
    @POST("api/register")
    Call<CreateUserResponse> creteUser(@Body CreateUserRequest createUserRequest);

    @POST("api/verifyOTP")
    Call<CreateUserResponse> verifyOtp(@Body VerifyOtpRequest verifyOtpRequest);

    @POST("api/login")
    Call<LoginResponse> loginUser(@Body LoginRequest loginRequest);

    @GET("api/getPropertyTypes")
    Call<PropertyTypesResponse> getPropertyTypes();

    @GET("api/getUnitChargesTypes")
    Call<UnitChargesTypesResponse> getUnitChargesTypes();

    @GET("api/getAminities")
    Call<AmenitiesResponse> getAminities();

    @GET("api/getComplimentories")
    Call<ComplimentariesResponse> getComplimentary();

    @GET("api/getStates")
    Call<StatesResponse> getStates();

    @POST("api/addProperty")
    Call<AddPropertyResponse> addProperty(@Body AddPropertyRequest addPropertyRequest);

    @PUT("api/updateProperty")
    Call<UpdatePropertyResponse> updateProperty(@Query("PropertyId") String propertyId, @Body AddPropertyRequest addPropertyRequest);

    @GET("api/getPropertyDetails")
    Call<PropertyDetailsResponse> getProperties(@Query("userId") String userId);

    @GET("api/getPropertyTypeDetails")
    Call<PropertyTypesDetailsResponse> getPropertyTypesDetails(@Query("propertyId") String userId);

    @PUT("api/deleteProperty")
    Call<BaseResponse> deleteProperty(@Body DeletePropertyRequest deletePropertyRequest);

    @GET("api/getCareTakerDetails")
    Call<CaretakerDetailsResponse> getCaretakers(@Query("userId") String userId);

    @POST("api/addPropertyCareTaker")
    Call<BaseResponse> addCareTaker(@Body AddCareTakerRequest addCareTakerRequest);

    @POST("api/updatePropertyCareTaker")
    Call<BaseResponse> updateCaretaker(@Query("CareTakerId") String careTakerId, @Body AddCareTakerRequest addCareTakerRequest);

    @POST("api/deleteCaretaker")
    Call<BaseResponse> deleteCareTaker(@Body DeleteCaretakerRequest deleteCaretakerRequest);

    @POST("api/addPropertyTypeDetailsMapping")
    Call<BaseResponse> addPropertyTypes(@Body AddPropertyTypesRequest addPropertyTypesRequest);

    @PUT("api/deletePropertyTypeMapping")
    Call<BaseResponse> deletePropertyType(@Body DeletePropertyTypesRequest deletePropertyTypesRequest);

    @POST("api/addPropertyTypeDetailsMapping")
    Call<BaseResponse> updatePropertyTypes(@Query("PropertyId") String propertyId, @Query("ProprtyTypeDetailsId") String proprtyTypeDetailsId, @Body AddPropertyTypesRequest addPropertyTypesRequest);
}
