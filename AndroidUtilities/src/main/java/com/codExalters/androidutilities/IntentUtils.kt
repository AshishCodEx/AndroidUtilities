package com.codExalters.androidutilities

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import java.net.URI
import java.net.URLEncoder


/**
 * Created by codexalters on 10/3/18.
 */
object IntentUtils {

    const val NO_INTENT = "No Intent available to handle this action."


    fun callIntent(activity: Activity, phoneNumber: String) {

        val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:$phoneNumber"))

        resolveIntentAndStartActivity(activity, intent)

    }

    private fun resolveIntentAndStartActivity(activity: Activity, intent: Intent) {
        if (intent.resolveActivity(activity.packageManager) != null) {
            startActivity(activity, intent, null)
        } else {
            Toast.makeText(activity, NO_INTENT, Toast.LENGTH_LONG).show()

        }
    }


    fun mapIntent(activity: Activity, address: String) {
        val mapIntent = Intent(
            Intent.ACTION_VIEW,
            Uri.parse(
                String.format(
                    "geo:0,0?q=%s",
                    URLEncoder.encode(address)
                )
            )
        )
        mapIntent.`package` = "com.ic_google_long.android.apps.maps"

        resolveIntentAndStartActivity(activity, mapIntent)
    }

    fun shareIntent(activity: Activity, text: String) {
        val sendIntent = Intent()
        sendIntent.action = Intent.ACTION_SEND
        sendIntent.putExtra(Intent.EXTRA_TEXT, text)
        sendIntent.type = "text/plain"

        if (sendIntent.resolveActivity(activity.packageManager) != null) {
            startActivity(
                activity,
                Intent.createChooser(sendIntent, "share with"),
                null
            )
        } else {
            Toast.makeText(activity, NO_INTENT, Toast.LENGTH_LONG).show()

        }

    }

    fun emailIntent(activity: Activity, emailAddress: String, subject: String, content: String) {
        val sendIntent = Intent(Intent.ACTION_SEND)

        sendIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(emailAddress))
        sendIntent.putExtra(Intent.EXTRA_SUBJECT, subject)
        sendIntent.putExtra(Intent.EXTRA_TEXT, content)
        sendIntent.type = "message/rfc822"

        /* val mailto = "mailto:$emailAddress" +
                 *//*  "?cc=" + "alice@example.com" +*//*
                "&subject=" + Uri.encode(subject) +
                "&body=" + Uri.encode(content)

        sendIntent.data = Uri.parse(mailto)*/

        if (sendIntent.resolveActivity(activity.packageManager) != null) {
            startActivity(
                activity,
                Intent.createChooser(sendIntent, "Email to"),
                null
            )
        } else {
            Toast.makeText(activity, NO_INTENT, Toast.LENGTH_LONG).show()

        }

    }

    fun shareContent(activity: Activity, uri: URI) {
        val shareIntent = Intent()
        shareIntent.action = Intent.ACTION_SEND
        shareIntent.putExtra(Intent.EXTRA_STREAM, uri)
        shareIntent.type = "image/jpeg"

        if (shareIntent.resolveActivity(activity.packageManager) != null) {
            startActivity(
                activity,
                Intent.createChooser(shareIntent, "share with"),
                null
            )
        } else {
            Toast.makeText(activity, NO_INTENT, Toast.LENGTH_LONG).show()

        }

    }


    fun shareMultipleContent(activity: Activity, uri: ArrayList<Uri>) {
        val imageUris = ArrayList<Uri>()
        imageUris.addAll(uri) // Add your image URIs here

        val shareIntent = Intent()
        shareIntent.action = Intent.ACTION_SEND_MULTIPLE
        shareIntent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, imageUris)
        shareIntent.type = "image/*"

        if (shareIntent.resolveActivity(activity.packageManager) != null) {
            startActivity(
                activity,
                Intent.createChooser(shareIntent, "share with"),
                null
            )
        } else {
            Toast.makeText(activity, NO_INTENT, Toast.LENGTH_LONG).show()

        }
    }

    fun browserIntent(activity: Activity, url: String) {

        val i = Intent(Intent.ACTION_VIEW)

        if (!url.contains("http://") && !url.contains("https://")) {
            val newURL = ("http://").plus(url)
            i.data = Uri.parse(newURL)
        } else {
            i.data = Uri.parse(url)
        }

        resolveIntentAndStartActivity(activity, i)


    }

    fun openGooglePlay(activity: Activity) {
        val appPackageName = activity.packageName

        val playStoreIntent =
            Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$appPackageName"))

        val browserIntent = Intent(
            Intent.ACTION_VIEW,
            Uri.parse("https://play.google.com/store/apps/details?id=$appPackageName")
        )

        when {
            playStoreIntent.resolveActivity(activity.packageManager) != null -> startActivity(
                activity,
                playStoreIntent,
                null
            )
            browserIntent.resolveActivity(activity.packageManager) != null -> startActivity(
                activity,
                browserIntent,
                null
            )
            else -> Toast.makeText(activity, NO_INTENT, Toast.LENGTH_LONG).show()
        }


    }
}


