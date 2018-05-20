package com.mincor.kviper.controllers

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.airbnb.android.airmapview.AirMapMarker
import com.airbnb.android.airmapview.AirMapView
import com.airbnb.android.airmapview.listeners.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.mincor.kviper.common.tracker.GPSTracker
import com.mincor.kviper.utils.log
import com.mincor.kviper.utils.mapView
import com.mincor.kviper.viper.baseui.BaseController
import com.mincor.weatherme.R
import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.linearLayout
import org.jetbrains.anko.matchParent
import org.kodein.di.generic.instance

class MapController : BaseController(), OnMapInitializedListener, OnInfoWindowClickListener,
        OnMapBoundsCallback, OnCameraMoveListener, OnCameraChangeListener {

    override fun getViewInstance(context: Context): View = MapViewUI().createView(AnkoContext.create(context, this))

    private var mapView: AirMapView? = null

    private val gpsTracker:GPSTracker by instance()

    override fun onAttach(view: View) {
        super.onAttach(view)
        mapView?.initialize((activity as AppCompatActivity).supportFragmentManager)
        mapView?.setOnMapInitializedListener(this)

    }

    override fun onDetach(view: View) {
        mapView?.setOnMapInitializedListener(null)
        mapView = null
        super.onDetach(view)
    }

    override fun onMapInitialized() {
        log {
            "onMapInitialized INITIALIZED"
        }

        mapView?.center = LatLng(gpsTracker.latitude, gpsTracker.longitude)
    }

    override fun onInfoWindowClick(airMarker: AirMapMarker<*>?) {

    }

    override fun onMapBoundsReady(bounds: LatLngBounds?) {
        log {
            "onMapInitialized INITIALIZED bounds $bounds"
        }
    }

    override fun onCameraMove() {

    }

    override fun onCameraChanged(latLng: LatLng?, zoom: Int) {

    }

    inner class MapViewUI : AnkoComponent<MapController> {
        override fun createView(ui: AnkoContext<MapController>): View = with(ui) {
            linearLayout {
                mapView = mapView {
                    id = R.id.map_view_id
                }.lparams(matchParent, matchParent)
                mapView
            }
        }
    }
}