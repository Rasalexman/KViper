package ru.fortgroup.dpru.utils

import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.support.v4.content.FileProvider
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory
import android.text.TextUtils
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.request.target.BitmapImageViewTarget
import ru.fortgroup.dpru.BuildConfig
import ru.fortgroup.dpru.common.errors.ELogger
import ru.fortgroup.dpru.consts.Consts
import ru.fortgroup.dpru.consts.Consts.FORMATER_AUTHOR_DATE
import ru.fortgroup.dpru.consts.MenuScreens
import ru.fortgroup.dpru.models.common.DpPreferense
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


/**
 * Created by Admin on 01.12.2016.
 */
object Support {
    /*fun style(view: View, value: Int) {
        view.scaleX = value.toFloat()
        view.scaleY = value.toFloat()
        view.alpha = value.toFloat()
    }*/

    /*fun getImageUri(inContext: Context, inImage: Bitmap): Uri {
        val bytes = ByteArrayOutputStream()
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path = Images.Media.insertImage(inContext.contentResolver, inImage, "Title", null)
        return Uri.parse(path)
    }*/

    fun getScreenNameByPosition() = when (DpPreferense.selectedScreenId) {
        MenuScreens.SCREEN_MAIN.screenID -> MenuScreens.SCREEN_MAIN.tag
        MenuScreens.SCREEN_NEWS.screenID -> MenuScreens.SCREEN_NEWS.tag
        MenuScreens.SCREEN_SEARCH.screenID -> MenuScreens.SCREEN_SEARCH.tag
        MenuScreens.SCREEN_BOOKMARKS.screenID -> MenuScreens.SCREEN_BOOKMARKS.tag
        MenuScreens.SCREEN_MENU.screenID -> MenuScreens.SCREEN_MENU.tag
        else -> MenuScreens.SCREEN_ARTICLE.tag
    }

    fun getEmailAgentIntent():Intent {
        val messageBody = "Клиент: №${DpPreferense.clientId}\n" +
                "Модель телефона: ${Support.getDeviceName()}\n" +
                "Версия Android: ${Build.VERSION.RELEASE}\n" +
                "API: ${Build.VERSION.SDK_INT}\n" +
                "Версия приложения: ${BuildConfig.VERSION_NAME}\n" +
                "Дата: ${FORMATER_AUTHOR_DATE.format(Calendar.getInstance().time)}" +
                "\n" +
                "Просьба не удалять и не изменять эту информацию из письма, она очень важна для нас. Спасибо."

        val intent = Intent(Intent.ACTION_SENDTO)
        intent.data = Uri.parse("mailto:")
        intent.putExtra(Intent.EXTRA_EMAIL, arrayOf("android@dp.ru"))
        intent.putExtra(Intent.EXTRA_SUBJECT, "Обратная связь")
        intent.putExtra(Intent.EXTRA_TEXT, messageBody)
        return intent
    }

    /*fun getRoundedImageTarget(context:Context, imageView: ImageView,
                              radius:Float): BitmapImageViewTarget
        = object : BitmapImageViewTarget(imageView) {
            override fun setResource(resource: Bitmap?) {
                val circularBitmapDrawable = RoundedBitmapDrawableFactory.create(context.resources, resource)
                circularBitmapDrawable.cornerRadius = radius
                imageView.setImageDrawable(circularBitmapDrawable)
            }
        }*/

    /*fun dpToPx(dp: Int): Int {
        return (dp * Resources.getSystem().displayMetrics.density).toInt()
    }*/

    /** Returns the consumer friendly device name  */
    fun getDeviceName(): String {
        val manufacturer = Build.MANUFACTURER
        val model = Build.MODEL
        return if (model.startsWith(manufacturer)) {
            capitalize(model)
        } else capitalize(manufacturer) + " " + model
    }

    /**
     * DISABLE ALL CHILD IN VIEWGROUP
     * @param enable
     * @param vg
     */
    fun disableEnableControls(enable: Boolean, vg: ViewGroup) {
        var child: View
        val sz = vg.childCount
        for (i in 0 until sz) {
            child = vg.getChildAt(i)
            child.isEnabled = enable
            if (child is ViewGroup) {
                disableEnableControls(enable, child)
            }
        }
    }


    private fun capitalize(str: String): String {
        if (TextUtils.isEmpty(str)) {
            return str
        }
        val arr = str.toCharArray()
        var capitalizeNext = true

        val phrase = StringBuilder()
        for (c in arr) {
            if (capitalizeNext && Character.isLetter(c)) {
                phrase.append(Character.toUpperCase(c))
                capitalizeNext = false
                continue
            } else if (Character.isWhitespace(c)) {
                capitalizeNext = true
            }
            phrase.append(c)
        }

        return phrase.toString()
    }

   /* fun pxToDp(px: Int): Int {
        return (px / Resources.getSystem().displayMetrics.density).toInt()
    }*/

    /**
     * DISABLE ALL CHILD IN VIEWGROUP
     * @param enable
     * @param vg
     */
    /*fun disableEnableControls(enable: Boolean, vg: ViewGroup) {
        var child: View
        for (i in 0 until vg.childCount) {
            child = vg.getChildAt(i)
            child.isEnabled = enable
            if (child is ViewGroup) {
                disableEnableControls(enable, child)
            }
        }
    }*/

    /*fun randInt(min: Int, max: Int): Int {
        val rand = Random()
        return rand.nextInt(max - min + 1) + min
    }*/

    // Method when launching drawable within Glide
    fun getUriFromBitmap(context: Context, bmp: Bitmap): Uri? {

        // Store image to default external storage directory

        return try {
            // Use methods on Context to access package-specific directories on external storage.
            // This way, you don't need to request external read/write permission.
            // See https://youtu.be/5xVh-7ywKpE?t=25m25s
            // getExternalFilesDir(Environment.DIRECTORY_PICTURES)
            val file = File(context.externalCacheDir, "share_image_" + System.currentTimeMillis() + ".jpeg")
            val out = FileOutputStream(file)
            bmp.compress(Bitmap.CompressFormat.JPEG, 90, out)
            out.close()

            // wrap File object into a content provider. NOTE: authority here should match authority in manifest declaration
            try {
                if (Build.VERSION.SDK_INT < 24) Uri.fromFile(file) else FileProvider.getUriForFile(context, "ru.fortgroup.dpru.share.social.dp.provider", file)
            } catch (e: Exception) {
                ELogger.logErrorEvent("ERROR Exception Support.getUriFromBitmap", e)
                null
            }

            // use this version for API >= 24
            // **Note:** For API < 24, you may use bmpUri = Uri.fromFile(file);

        } catch (e: IOException) {
            ELogger.logErrorEvent("ERROR IOException Support.getUriFromBitmap", e)
            null
        } catch (e: FileNotFoundException) {
            ELogger.logErrorEvent("ERROR FileNotFoundException Support.getUriFromBitmap", e)
            null
        }
    }

    /*@Synchronized
    fun getBetweenTime(dateStr: String, pattern: String): String {
        var beetweenStr = ""
        var messageDate = Date()
        var dateformater: SimpleDateFormat? = null
        try {
            dateformater = SimpleDateFormat(pattern)
            messageDate = dateformater.parse(dateStr)
        } catch (e: ParseException) {
        }

        val diff = Date().time - messageDate.time
        val secBetween = TimeUnit.SECONDS.convert(diff, TimeUnit.MILLISECONDS)
        val minsBetween = TimeUnit.MINUTES.convert(diff, TimeUnit.MILLISECONDS)
        val hoursBetween = TimeUnit.HOURS.convert(diff, TimeUnit.MILLISECONDS)
        val daysBetween = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS)

        if (minsBetween < 60) {
            if (minsBetween < 1) {
                if (secBetween > 30)
                    beetweenStr = secBetween.toString() + " " + MainApplication.instance.getString(R.string.back_less_sec)
                else
                    beetweenStr = MainApplication.instance.getString(R.string.back_now)
            } else {
                beetweenStr = minsBetween.toString() + " " + MainApplication.instance.getString(R.string.back_less_min)
            }
        } else if (hoursBetween >= 1 && hoursBetween < 24) {
            beetweenStr = hoursBetween.toString() + " " + MainApplication.instance.getString(R.string.back_less_hour)
        } else if (daysBetween > 365) {
            dateformater = SimpleDateFormat("dd MMM yyyy", myDateFormatSymbols)
            beetweenStr = dateformater.format(messageDate)
        } else {
            dateformater = SimpleDateFormat("dd MMM", myDateFormatSymbols)
            beetweenStr = dateformater.format(messageDate)
        }

        return beetweenStr
    }*/

    /*var myDateFormatSymbols: DateFormatSymbols = object : DateFormatSymbols() {
        override fun getMonths(): Array<String> {
            return MainApplication.instance.getResources().getStringArray(R.array.month_short)
        }

        override fun getShortMonths(): Array<String> {
            return MainApplication.instance.getResources().getStringArray(R.array.month_short)
        }
    }*/

    /**
     * GET LOADED IMG FROM GALLERY
     * @param uri
     * @param parent
     * @return
     * @throws IOException
     */
    /*@Throws(IOException::class)
    fun getBitmapFromUri(uri: Uri, parent: Activity): Bitmap {
        val parcelFileDescriptor = parent.contentResolver.openFileDescriptor(uri, "r")
        val fileDescriptor = parcelFileDescriptor!!.fileDescriptor
        val image = BitmapFactory.decodeFileDescriptor(fileDescriptor)
        parcelFileDescriptor.close()
        return image
    }*/

    /**
     * GET BYTES FROM BITMAP
     * @param bitmap
     * @return
     */
    /*fun getByteArrayfromBitmap(bitmap: Bitmap): ByteArray {
        val bos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, bos)
        return bos.toByteArray()
    }*/

    /**
     * CONVERT BYTES TO BITMAP
     * @param bitmap
     * @return
     */
    /*fun getBitmapfromByteArray(bitmap: ByteArray): Bitmap {
        return BitmapFactory.decodeByteArray(bitmap, 0, bitmap.size)
    }*/
}
