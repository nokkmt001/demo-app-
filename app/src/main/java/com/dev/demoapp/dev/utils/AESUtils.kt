package com.dev.demoapp.dev.utils

import android.util.Base64
import java.io.UnsupportedEncodingException
import javax.crypto.Cipher
import javax.crypto.SecretKey
import javax.crypto.spec.SecretKeySpec

object AESUtils {
    private val keyValue = byteArrayOf(
        't'.code.toByte(),
        'i'.code.toByte(),
        'h'.code.toByte(),
        'a'.code.toByte(),
        '2'.code.toByte(),
        '0'.code.toByte(),
        '0'.code.toByte(),
        '3'.code.toByte(),
        '1'.code.toByte(),
        '0'.code.toByte(),
        '0'.code.toByte(),
        '5'.code.toByte(),
        '2'.code.toByte(),
        '0'.code.toByte(),
        '1'.code.toByte(),
        '9'.code.toByte()
    )
    private const val HEX = "0123456789ABCDEF"

    @Throws(Exception::class)
    fun encrypt(cleartext: String): String {
        if (cleartext.isEmpty()) return cleartext
        val rawKey = rawKey
        val result = encrypt(rawKey, cleartext.toByteArray())
        return toHex(result)
    }

    @Throws(Exception::class)
    fun decrypt(encrypted: String): String {
        if (encrypted.isEmpty()) return encrypted
        val enc = toByte(encrypted)
        val result = decrypt(enc)
        return String(result)
    }

    @get:Throws(Exception::class)
    val rawKey: ByteArray
        get() {
            val key: SecretKey =
                SecretKeySpec(keyValue, "AES")
            return key.encoded
        }

    @Throws(Exception::class)
    fun encrypt(raw: ByteArray?, clear: ByteArray?): ByteArray {
        val skeySpec: SecretKey =
            SecretKeySpec(raw, "AES")
        val cipher = Cipher.getInstance("AES")
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec)
        return cipher.doFinal(clear)
    }

    @Throws(Exception::class)
    fun decrypt(encrypted: ByteArray?): ByteArray {
        val skeySpec: SecretKey =
            SecretKeySpec(keyValue, "AES")
        val cipher = Cipher.getInstance("AES")
        cipher.init(Cipher.DECRYPT_MODE, skeySpec)
        return cipher.doFinal(encrypted)
    }

    fun toByte(hexString: String): ByteArray {
        val len = hexString.length / 2
        val result = ByteArray(len)
        for (i in 0 until len) result[i] = Integer.valueOf(
            hexString.substring(2 * i, 2 * i + 2),
            16
        ).toByte()
        return result
    }

    fun toHex(buf: ByteArray?): String {
        if (buf == null) return ""
        val result = StringBuffer(2 * buf.size)
        for (i in buf.indices) {
            appendHex(result, buf[i])
        }
        return result.toString()
    }

    fun appendHex(sb: StringBuffer, b: Byte) {
        sb.append(HEX[b.toInt() shr 4 and 0x0f]).append(HEX[b.toInt() and 0x0f])
    }

    fun encodePassWord(pass: String): String? {
        val data: ByteArray
        val passTotal: String = ContainsUtils.key_pass + pass
        var passEncode: String? = passTotal
        return try {
            data = passTotal.toByteArray(charset("UTF-8"))
            encodeBase64(data).also {
                passEncode = it
            }
        } catch (e: UnsupportedEncodingException) {
            passEncode
        }
    }

    fun encodeBase64(data: ByteArray?): String? {
        return Base64.encodeToString(data, Base64.DEFAULT)
    }

}
