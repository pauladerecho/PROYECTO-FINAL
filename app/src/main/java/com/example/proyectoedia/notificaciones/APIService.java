package com.example.proyectoedia.notificaciones;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIService {

    @Headers({
            "Content-Type:application/json",
            "Authorization:key=AAAABW5r2dI:APA91bFEhVKYCD-dTWE8D_MXNxJ-JorFGfMXqMY6eMs5KPguAst5-8BW1ttsxoeoXKUa2tufyLh76UW3rtGcPdU2mPWY26fIzm2LxyDJ7N8shwL-ukx8fmSYqs_xqpJs4AmPdUseGS3J"
    })

    @POST("fcm/send")
    Call<Response> sendNotification(@Body Sender body);
}
