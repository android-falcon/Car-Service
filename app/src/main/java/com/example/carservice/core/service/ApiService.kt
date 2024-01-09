package com.example.carservice.core.service

import com.example.carservice.feature.addTicket.domain.model.CarResponse
import com.example.carservice.feature.addTicket.domain.model.StatusModel
import com.example.carservice.feature.auth.domain.model.UserModel
import com.example.carservice.feature.detail.domain.model.ServiceResponse
import com.example.carservice.feature.detail.domain.model.TimeModel
import com.example.carservice.feature.employee.domain.model.EmployeeModel
import com.example.carservice.feature.home.domain.model.TicketBody
import com.example.carservice.feature.home.domain.model.TicketResponse
import com.example.carservice.feature.sammaryTicket.domain.model.EmployeeResponse
import com.example.carservice.feature.services.domain.model.ServiceBodyModel
import com.example.carservice.feature.services.domain.model.ServiceDtlModel
import com.example.carservice.feature.services.domain.model.ServiceModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {
    @GET("/van.dll/GetCSSUserInfo")
//    @GET("/GetCSSUserInfo")
    suspend fun getUserInfo(
        @Query("CONO") cono: String,
        @Query("USERNO") userno: String
    ): Response<List<UserModel>>

    @GET("/van.dll/GetCssAllTicketByStatues")
//    @GET("/GetCssAllTicketByStatues")
    suspend fun getTicketByStatus(
        @Query("cono") cono: String,
        @Query("STATUS") status: String
    ): Response<List<TicketResponse>>

    @GET("/van.dll/GetCssAllTicket")
//    @GET("/GetCssAllTicket")
    suspend fun getTicketsByDate(
        @Query("CONO") cono: String,
        @Query("VHFDATE") vhfDate: String
    ): Response<List<TicketResponse>>

    @GET("/van.dll/GetCssEmployee")
//    @GET("/GetCssEmployee")
    suspend fun getEmployee(
        @Query("CONO") cono: String,
        @Query("PHASE") phase: String
    ): Response<List<EmployeeModel>>

    @GET("/van.dll/GetCssServices")
//    @GET("/GetCssServices")
    suspend fun getService(@Query("CONO") cono: String): Response<List<ServiceModel>>


     @GET("/van.dll/SaveNewTicket")
//    @GET("/SaveNewTicket")
    suspend fun saveTicket(
        @Query("CONO") cono: String,
        @Query("PHONE_NUMBER") phoneNumber: String,
        @Query("CUSTOMER_NAME") customerName: String,
        @Query("CUSTOMER_TYPE") customerType: String,
        @Query("CAR_ID") carId: String,
        @Query("CAR_TYPE") carType: String,
        @Query("CAR_COLOR") carColor: String,
        @Query("HOWMANY") howMany: String,
        @Query("NOTE") note: String,
        @Query("USER_NO") userNumber: String,
        @Query("SERVICES") service: String,
        @Query("CAR_MODEL") carModel: String,

        ): Response<StatusModel>

     @GET("/van.dll/GetCssTicketService")
//    @GET("/GetCssTicketService")
    suspend fun getServiceByVoucherNumber(
        @Query("CONO") cono: String,
        @Query("VHFNO") voucherNumber: String
    ): Response<List<ServiceResponse>>

    @GET("/van.dll/StartTicket")
//    @GET("/StartTicket")
    suspend fun startTicket(
        @Query("CONO") cono: String,
        @Query("TEAM") team: String,
        @Query("TICKETNO") voucherNumber: String,
        @Query("PHASE") phase: String
    ): Response<StatusModel>

    @GET("/van.dll/EndTicket")
//    @GET("/EndTicket")
    suspend fun endTicket(
        @Query("cono") cono: String,
        @Query("TICKETNO") ticketNumber: String
    ): Response<StatusModel>

     @GET("/van.dll/CancelTicket")
//    @GET("/CancelTicket")
    suspend fun cancelTicket(
        @Query("cono") cono: String,
        @Query("TICKETNO") ticketNumber: String,
        @Query("NOTE") note: String
    ): Response<StatusModel>

    @GET("/van.dll/GetCustomerInfo")
//    @GET("/GetCustomerInfo")
    suspend fun getCustomerByPhoneNumber(
        @Query("cono") cono: String,
        @Query("PHONE") ticketNumber: String,
    ): Response<List<CarResponse>>

    @GET("/van.dll/GetCustomerInfo")
//    @GET("/GetCustomerInfo")
    suspend fun getCustomerByPlateNumber(
        @Query("cono") cono: String,
        @Query("PLATE") plateNumber: String
    ): Response<List<CarResponse>>

     @GET("/van.dll/GetCssTicketTeam")
//    @GET("/GetCssTicketTeam")
    suspend fun getTeamList(
        @Query("cono") cono: String,
        @Query("VHFNO") voucherNumber: String
    ): Response<List<EmployeeResponse>>

    @GET("/van.dll/GetTicket")
//    @GET("/GetTicket")
    suspend fun getTime(
        @Query("CONO") cono: String,
        @Query("VHFNO") voucherNumber: String
    ): Response<List<TimeModel>>
}


