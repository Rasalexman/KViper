package com.mincor.kviper.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.support.annotation.ColorRes
import android.support.annotation.DrawableRes
import android.support.v4.content.ContextCompat
import android.support.v7.view.ContextThemeWrapper
import android.view.View
import android.view.View.*
import android.view.ViewGroup
import android.view.ViewManager
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.mincor.kviper.BuildConfig
import com.raizlabs.android.dbflow.kotlinextensions.database
import com.raizlabs.android.dbflow.structure.database.transaction.ITransaction
import kotlinx.coroutines.experimental.*
import org.jetbrains.anko.custom.ankoView
import org.jetbrains.anko.displayMetrics

/**
 * Created by a.minkin on 28.09.2017.
 */

/**
 * Button with custom style
 * */
/*@SuppressLint("RestrictedApi")
inline fun ViewManager.styledButton(text: CharSequence?, styleRes: Int = 0, init: Button.() -> Unit): Button {
    return ankoView({ if (styleRes == 0) Button(it) else Button(ContextThemeWrapper(it, styleRes), null, 0) }, 0) {
        init()
        setText(text)
    }
}*/
@SuppressLint("RestrictedApi")
inline fun ViewManager.styledButton(textres: Int, styleRes: Int = 0, init: Button.() -> Unit): Button {
    return ankoView({ if (styleRes == 0) Button(it) else Button(ContextThemeWrapper(it, styleRes), null, 0) }, 0) {
        init()
        setText(textres)
    }
}

inline fun schedule(crossinline runner: suspend () -> Unit)  {
    launch(CommonPool) {
        runner()
    }
}


/**
 * Styled TextInputs
 * */
/*@SuppressLint("RestrictedApi")
inline fun ViewManager.styledAutoTextView(styleRes: Int = 0, init: AutoCompleteTextView.() -> Unit): AutoCompleteTextView {
    return ankoView({ if (styleRes == 0) AutoCompleteTextView(it) else AutoCompleteTextView(ContextThemeWrapper(it, styleRes), null, 0) }, 0) {
        init()
    }
}*/

/**
 * Radio Buttons with custom style
 * */
/*@SuppressLint("RestrictedApi")
inline fun ViewManager.styledRadioButton(textres: Int, styleRes: Int = 0, init: RadioButton.() -> Unit): RadioButton {
    return ankoView({ if (styleRes == 0) RadioButton(it) else RadioButton(ContextThemeWrapper(it, styleRes), null, 0) }, 0) {
        init()
        setText(textres)
    }
}*/

/**
 * Toggle Button Style
 * */
/*@SuppressLint("RestrictedApi")
inline fun ViewManager.styledToggleButton(textres: Int, styleRes: Int = 0, init: ToggleButton.() -> Unit): ToggleButton {
    return ankoView({ if (styleRes == 0) ToggleButton(it) else ToggleButton(ContextThemeWrapper(it, styleRes), null, 0) }, 0) {
        init()
        setText(textres)
    }
}*/

/**
 * CIRCLE IMAGE VIEW EXT
 * **/
/*inline fun ViewManager.circleImage(init: CircleImageView.()->Unit):CircleImageView{
    return ankoView({CircleImageView(it)}, theme = 0, init = init)
}*/
/*inline fun ViewManager.photoImage(init: PhotoView.()->Unit):PhotoView{
    return ankoView({PhotoView(it)}, theme = 0, init = init)
}
inline fun ViewManager.roundedImage(init:CircleImageView.()->Unit):CircleImageView{
    return ankoView({CircleImageView(it)}, theme = 0, init = init)
}*/
/*@SuppressLint("RestrictedApi")
inline fun ViewManager.gestureFabButton(styleRes: Int = 0, init: FabGestureButton.()->Unit): FabGestureButton {
    return ankoView({if (styleRes == 0) FabGestureButton(it) else FabGestureButton(ContextThemeWrapper(it, styleRes))}, theme = 0, init = init)
}*/
/*inline fun ViewManager.expandableTextView(textres: Int, init: ExpandableTextView.()->Unit):ExpandableTextView {
    return ankoView({ ExpandableTextView(it) }, 0) {
        init()
        setText(textres)
    }
}*/

/**
 * DATABASE EXECUTION
 */
inline fun <reified T : Any> ITransaction.executeAsync() = database<T>().beginTransactionAsync(this).execute()

inline fun <reified T : Any> ITransaction.executeSync() = database<T>().executeTransaction(this)

/**
 * Async with timeout
 */
suspend fun <T> Deferred<T>.await(timeout : Long, defaultValue : T) = withTimeoutOrNull(timeout) { await() } ?: defaultValue

/***
 * Custom View For somethings like lines
 * */
fun roundedBg(col:Int, corners:Float = 100f, withStroke:Boolean = false, strokeColor:Int = Color.LTGRAY, strokeWeight:Int = 2) = GradientDrawable().apply {
    shape = GradientDrawable.RECTANGLE
    cornerRadius = corners
    setColor(col)
    if(withStroke) setStroke(strokeWeight, strokeColor)
}

fun pxToDp(px: Int): Int {
    return (px / Resources.getSystem().displayMetrics.density).toInt()
}

/**
 * UTILS SECTION
 * */
fun View.drawable(@DrawableRes resource: Int): Drawable? = ContextCompat.getDrawable(context, resource)
fun View.color(@ColorRes resource: Int): Int = ContextCompat.getColor(context, resource)
fun View.string(stringRes:Int):String = context.getString(stringRes)
//fun Context.wdthProc(proc:Float):Int = (this.displayMetrics.widthPixels*proc).toInt()
fun View.wdthProc(proc:Float):Int = (context.displayMetrics.widthPixels*proc).toInt()
fun Context.hdthProc(proc:Float):Int = (this.displayMetrics.heightPixels*proc).toInt()
fun View.hdthProc(proc:Float):Int = (context.displayMetrics.heightPixels*proc).toInt()

inline fun log(lambda: () -> String?) {
    if (BuildConfig.DEBUG) {
        L.d(lambda()?:"")
    }
}

/**
 * GLIDE IMAGE LOADING
 * */
/*val Context.glide:GlideRequests
get() = GlideApp.with(this.applicationContext)
fun ImageView.load(path:String, progress:ProgressBar? = null, loaderHandler: IPhotoLoaderListener? = null){
    val layParams = this.layoutParams
    progress?.show()

    if(path.contains(".gif")){
        val requestOptions = RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.DATA)
                .override(layParams.width, layParams.height)
                .error(ContextCompat.getDrawable(context, R.drawable.image_default_gradient))

        val reqListener = object : RequestListener<GifDrawable> {
            override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<GifDrawable>?, isFirstResource: Boolean): Boolean {
                progress?.hide(true)
                loaderHandler?.onPhotoFailedHandler()
                return false
            }
            override fun onResourceReady(resource: GifDrawable?, model: Any?, target: Target<GifDrawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                progress?.hide(true)
                loaderHandler?.onPhotoLoadedHandler()
                return false
            }
        }
        context.glide.asGif().load(path).listener(reqListener).apply(requestOptions).into(this)
    }else{
        val requestOptions = RequestOptions().dontTransform()
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .dontAnimate()
                .override(layParams.width, layParams.height)
                .error(R.drawable.image_default_gradient)
                .encodeFormat(Bitmap.CompressFormat.WEBP)
                .format(DecodeFormat.PREFER_RGB_565)

        val reqListener = object : RequestListener<Bitmap> {
            override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Bitmap>?, isFirstResource: Boolean): Boolean {
                progress?.hide(true)
                loaderHandler?.onPhotoFailedHandler()
                return false
            }
            override fun onResourceReady(resource: Bitmap?, model: Any?, target: Target<Bitmap>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                progress?.hide(true)
                loaderHandler?.onPhotoLoadedHandler()
                return false
            }
        }
        context.glide.asBitmap().load(path).listener(reqListener).apply(requestOptions).into(this)
    }
}*/
/*fun ImageView.load(pathRes:Int){
    context.glide.load(pathRes).into(this)
}*/

/*fun ImageView.load(urlStr:String, transformation:BitmapTransformation, loaderHandler: IPhotoLoaderListener? = null, progress: ProgressBar? = null){
    val reqListener = object : RequestListener<Bitmap> {
        override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Bitmap>?, isFirstResource: Boolean): Boolean {
            progress?.hide(true)
            loaderHandler?.onPhotoFailedHandler()
            return false
        }
        override fun onResourceReady(resource: Bitmap?, model: Any?, target: Target<Bitmap>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
            progress?.hide(true)
            this@load.setImageBitmap(resource)
            loaderHandler?.onPhotoLoadedHandler()
            return true
        }
    }
    context.glide.asBitmap().load(urlStr).listener(reqListener).transform(transformation).into(this)
}*/

/*
fun ImageView.loadRounded(urlStr:String, progress: ProgressBar? = null) {
    val reqListener = object : RequestListener<Bitmap> {
        override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Bitmap>?, isFirstResource: Boolean): Boolean {
            progress?.hide(true)
            return false
        }
        override fun onResourceReady(resource: Bitmap?, model: Any?, target: Target<Bitmap>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
            progress?.hide(true)
            this@loadRounded.setImageBitmap(resource)
            return true
        }
    }
    context.glide.asBitmap().load(urlStr).listener(reqListener).into(Support.getRoundedImageTarget(this.context, this, 100f))
}
*/

fun ImageView.clear(){
    //context.glide.clear(this)
    this.setImageResource(0)
    this.setImageBitmap(null)
    this.setImageDrawable(null)
    this.destroyDrawingCache()
}

fun ViewGroup.clear(){
    var childView:View
    repeat(this.childCount){
        childView = this.getChildAt(it)
        when(childView){
            is ViewGroup->(childView as ViewGroup).clear()
            is ImageView -> (childView as ImageView).clear()
            is Button -> (childView as Button).setOnClickListener(null)
            is TextView -> (childView as TextView).text = null
        }
    }
}

fun Int.dpToPx():Int {
    return (this * Resources.getSystem().displayMetrics.density).toInt()
}


/**
 * Sets the view's visibility to INVISIBLE
 */
/*fun View.invisible() {
    visibility = View.INVISIBLE
}*/
/**
 * Toggle's view's visibility. If View is visible, then sets to gone. Else sets Visible
 */
fun View.toggle() {
    visibility = if (visibility == View.VISIBLE) View.GONE else View.VISIBLE
}
var View.visible
    get() = visibility == VISIBLE
    set(value) {
        visibility = if (value) VISIBLE else GONE
    }

fun View.hide(gone: Boolean = true) {
    visibility = if (gone) GONE else INVISIBLE
}
fun View.show() {
    visibility = VISIBLE
}
