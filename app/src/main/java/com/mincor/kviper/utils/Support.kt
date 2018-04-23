package com.mincor.kviper.utils

import android.content.res.Resources
import android.os.Build
import android.text.TextUtils
import android.view.View
import android.view.ViewGroup
import java.io.IOException


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

    /*fun pxToDp(px: Int): Int {
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
