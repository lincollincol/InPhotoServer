package com.linc.di

import com.linc.utils.ImageUtils
import com.linc.utils.JWTUtils
import org.koin.dsl.module
import java.security.SecureRandom
import java.util.*

val utilsModule = module {

    fun provideSecureRandom() = SecureRandom()
    fun provideBase64UrlEncoder() = Base64.getUrlEncoder()

    single<SecureRandom> { provideSecureRandom() }
    single<Base64.Encoder> { provideBase64UrlEncoder() }
    single<JWTUtils> { JWTUtils() }
    single<ImageUtils> { ImageUtils() }

}