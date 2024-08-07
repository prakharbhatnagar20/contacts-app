package com.user.contacts.network

import com.user.contacts.model.Contacts
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface ContactsApi {
    @GET("v0/b/user-contacts-ade83.appspot.com/o/contacts.json?alt=media&token=56ff997d-b2d1-41bd-8ba7-0e15de0bbfb0")
    suspend fun getContacts(): List<Contacts>
}

object RetrofitInstance {
    private const val BASE_URL = "https://firebasestorage.googleapis.com/"

    val api: ContactsApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ContactsApi::class.java)
    }
}