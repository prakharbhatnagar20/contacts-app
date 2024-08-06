package com.user.contacts.network

import com.user.contacts.model.Contacts
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface ContactsApi {
    @GET("v0/b/user-contacts-ade83.appspot.com/o/contacts.json?alt=media&token=3e76df8d-7482-4e97-b987-f73d4283c0c5")
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