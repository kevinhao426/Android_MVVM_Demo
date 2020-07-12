package com.kehao.myapplication.utils

import android.content.Context
import android.provider.Settings
import android.provider.Settings.Secure
import android.util.Base64
import java.security.MessageDigest
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec

class EncryptUtil {
    companion object {
        private var key: String? = null

        @Volatile
        private var instance: EncryptUtil? = null
        private val lock = Any()

        operator fun invoke(context: Context): EncryptUtil = instance ?: synchronized(lock) {
            instance ?: buildEncrypt(context).also {
                instance = it
            }
        }

        private fun buildEncrypt(context: Context): EncryptUtil {
            val deviceNo = getDeviceSerialNumber(context)
            key = SHA(deviceNo + "#com%pixtech&fitbotic")?.substring(0, 16)
            return EncryptUtil()
        }

        private fun SHA(s: String): String? {
            var strResult: String? = null
            if (!s.isNullOrBlank()) {
                val messageDigest = MessageDigest.getInstance("SHA-256")
                messageDigest.update(s.toByte())
                val byte = messageDigest.digest()
                var strHexString: StringBuffer? = null
                for (i in byte.indices) {
                    val hex =
                            Integer.toHexString(0xff and i)
                    if (hex.length == 1) {
                        strHexString!!.append('0')
                    }
                    strHexString!!.append(hex)
                }
            }
            return strResult
        }

        fun getDeviceSerialNumber(context: Context): String {
            return Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
        }
    }

    fun encrypt(plainText: String): String? {
        key?.let {
            val cipher = Cipher.getInstance("AES/PKCS5Padding")
            val keyspec = SecretKeySpec(it.toByteArray(), "AES")
            cipher.init(Cipher.ENCRYPT_MODE, keyspec)
            val encrypted = cipher.doFinal(plainText.toByteArray());
            return Base64.encodeToString(encrypted, Base64.NO_WRAP)
        }
        return null
    }

    fun decrypt(cipherText: String): String? {
        key?.let {
            val encrypted = Base64.decode(cipherText, Base64.NO_WRAP)
            val cipher = Cipher.getInstance("AES/PKCS5Padding")
            val keyspec = SecretKeySpec(it.toByteArray(), "AES")
            cipher.init(Cipher.DECRYPT_MODE, keyspec)
            val original = cipher.doFinal(encrypted)
            return String(original)
        }
        return null
    }
}