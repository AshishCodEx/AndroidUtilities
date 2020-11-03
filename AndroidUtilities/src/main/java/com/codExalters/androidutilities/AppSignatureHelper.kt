package com.codExalters.androidutilities

import android.annotation.SuppressLint
import android.content.Context
import android.content.ContextWrapper
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Build
import android.util.Base64
import android.util.Log
import java.nio.charset.StandardCharsets
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.*


/**
 * Created by Ashish on 11/9/20.
 */
class AppSignatureHelper(context: Context) :
    ContextWrapper(context) {


    private val TAG = AppSignatureHelper::class.java.simpleName
    private val HASH_TYPE = "SHA-256"
    private val NUM_HASHED_BYTES = 9
    private val NUM_BASE64_CHAR = 11

    private fun hash(packageName: String, signature: String): String? {
        val appInfo = "$packageName $signature"
        try {
            val messageDigest = MessageDigest.getInstance(HASH_TYPE)
            messageDigest.update(appInfo.toByteArray(StandardCharsets.UTF_8))
            var hashSignature = messageDigest.digest()

            // truncated into NUM_HASHED_BYTES
            hashSignature = Arrays.copyOfRange(hashSignature, 0, NUM_HASHED_BYTES)
            // encode into Base64
            var base64Hash =
                Base64.encodeToString(hashSignature, Base64.NO_PADDING or Base64.NO_WRAP)
            base64Hash = base64Hash.substring(0, NUM_BASE64_CHAR)
            Log.v(
                TAG + "sms_sample_test",
                String.format("pkg: %s -- hash: %s", packageName, base64Hash)
            )
            return base64Hash
        } catch (e: NoSuchAlgorithmException) {
            Log.v(TAG + "sms_sample_test", "hash:NoSuchAlgorithm", e)
        }
        return null
    }


    @SuppressLint("PackageManagerGetSignatures")
    fun getSignaturesHash(): ArrayList<String> {
        val appCodes = ArrayList<String>()

        try {
            val pm = packageManager
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {

                val packageInfo: PackageInfo =
                    pm.getPackageInfo(packageName, PackageManager.GET_SIGNING_CERTIFICATES)
                when {
                    packageInfo.signingInfo == null -> {

                    }
                    packageInfo.signingInfo.hasMultipleSigners() -> {

                        for (signature in packageInfo.signingInfo.apkContentsSigners) {
                            val hash = hash(packageName, signature.toCharsString())
                            if (hash != null) {
                                appCodes.add(String.format("%s", hash))
                            }
                        }

                    }
                    else -> {
                        for (signature in packageInfo.signingInfo.signingCertificateHistory) {
                            val hash = hash(packageName, signature.toCharsString())
                            if (hash != null) {
                                appCodes.add(String.format("%s", hash))
                            }
                        }

                    }
                }
            } else {

                val packageInfo: PackageInfo =
                    pm.getPackageInfo(packageName, PackageManager.GET_SIGNATURES)

                if (packageInfo.signatures != null && packageInfo.signatures.isNotEmpty()) {
                    for (signature in packageInfo.signatures) {

                        Log.d(
                            "AppSignatureHelper",
                            "getSignaturesHash : ${signature.toCharsString()} "
                        )

                        val hash = hash(packageName, signature.toCharsString())
                        if (hash != null) {
                            appCodes.add(String.format("%s", hash))
                        }
                    }
                }


            }
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }

        return appCodes;
    }

    fun getApplicationSignature(): List<String> {
        val signatureList: List<String>
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                // New signature
                val sig = packageManager.getPackageInfo(
                    packageName,
                    PackageManager.GET_SIGNING_CERTIFICATES
                ).signingInfo
                signatureList = if (sig.hasMultipleSigners()) {
                    // Send all with apkContentsSigners
                    sig.apkContentsSigners.map {
                        val digest = MessageDigest.getInstance("SHA")
                        digest.update(it.toByteArray())
                        bytesToHex(digest.digest())
                    }
                } else {
                    // Send one with signingCertificateHistory
                    sig.signingCertificateHistory.map {
                        val digest = MessageDigest.getInstance("SHA")
                        digest.update(it.toByteArray())
                        bytesToHex(digest.digest())
                    }
                }
            } else {
                val sig = packageManager.getPackageInfo(
                    packageName,
                    PackageManager.GET_SIGNATURES
                ).signatures
                signatureList = sig.map {
                    val digest = MessageDigest.getInstance("SHA")
                    digest.update(it.toByteArray())
                    bytesToHex(digest.digest())
                }
            }

            return signatureList
        } catch (e: Exception) {
            // Handle error
        }
        return emptyList()
    }

    private fun bytesToHex(bytes: ByteArray): String {
        val hexArray = charArrayOf(
            '0',
            '1',
            '2',
            '3',
            '4',
            '5',
            '6',
            '7',
            '8',
            '9',
            'A',
            'B',
            'C',
            'D',
            'E',
            'F'
        )
        val hexChars = CharArray(bytes.size * 2)
        var v: Int
        for (j in bytes.indices) {
            v = bytes[j].toInt() and 0xFF
            hexChars[j * 2] = hexArray[v.ushr(4)]
            hexChars[j * 2 + 1] = hexArray[v and 0x0F]
        }
        return String(hexChars)
    }
}