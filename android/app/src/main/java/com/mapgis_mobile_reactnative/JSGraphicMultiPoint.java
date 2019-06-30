package com.mapgis_mobile_reactnative;

import android.graphics.Bitmap;
import android.graphics.PointF;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;
import com.zondy.mapgis.android.graphic.Graphic;
import com.zondy.mapgis.android.graphic.GraphicCircle;
import com.zondy.mapgis.android.graphic.GraphicImage;
import com.zondy.mapgis.android.graphic.GraphicMultiPoint;
import com.zondy.mapgis.core.geometry.Dot;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @content 多点图形对象Native组件
 * @author fjl 2019-6-30 下午2:52:36
 */
public class JSGraphicMultiPoint extends JSGraphic {
    public static final String REACT_CLASS = "JSGraphicMultiPoint";
    public static Map<String, GraphicMultiPoint> mGraphicMultiPointList = new HashMap<String, GraphicMultiPoint>();


    public JSGraphicMultiPoint(ReactApplicationContext context) {
        super(context);
    }

    @Override
    public String getName() {
        return REACT_CLASS;
    }

    public static GraphicMultiPoint getObjFromList(String id){
        return mGraphicMultiPointList.get(id);
    }


    public static String registerId(GraphicMultiPoint obj) {
        for (Map.Entry entry : mGraphicMultiPointList.entrySet()) {
            if (obj.equals(entry.getValue())) {
                String id = (String) entry.getKey();
                mGraphicMultiPointList.put(id, obj);
                return (String) entry.getKey();
            }
        }

        Calendar calendar = Calendar.getInstance();
        String id = Long.toString(calendar.getTimeInMillis());
        mGraphicMultiPointList.put(id, obj);
        return id;
    }

    @ReactMethod
    public void createObj(Promise promise){
        try{
            GraphicMultiPoint GraphicMultiPoint = new GraphicMultiPoint();
            String GraphicMultiPointId = registerId(GraphicMultiPoint);

            WritableMap map = Arguments.createMap();
            map.putString("GraphicMultiPointId",GraphicMultiPointId);
            promise.resolve(map);
        }catch (Exception e){
            promise.reject(e);
        }
    }

    @ReactMethod
    public void setPoints(String GraphicMultiPointId, ReadableArray pointArray, Promise promise){
        try{
            GraphicMultiPoint GraphicMultiPoint = getObjFromList(GraphicMultiPointId);
            ArrayList<Dot> dotLst = new ArrayList();
            if(GraphicMultiPoint != null)
            {
                for(int i =0; i < pointArray.size();i++)
                {
                    ReadableMap readable = pointArray.getMap(i);
                    String keyStr = readable.getString("_MGDotId");
                    dotLst.add (JSDot.getObjFromList(keyStr));
                }

            }

            GraphicMultiPoint.setPoints(dotLst);

        }catch (Exception e){
            promise.reject(e);
        }
    }

    @ReactMethod
    public void getPoints(String GraphicMultiPointId,Promise promise){
        try{
            GraphicMultiPoint GraphicMultiPoint = getObjFromList(GraphicMultiPointId);
            Dot[] dotLst = GraphicMultiPoint.getPoints();
            String dotID = "";
            WritableArray arr = Arguments.createArray();
            if(dotLst.length > 0)
            {
                for (int i = 0;i < dotLst.length; i++)
                {
                    dotID = JSDot.registerId(dotLst[i]);
                    arr.pushString(dotID);
                }
            }
            WritableMap map = Arguments.createMap();
            map.putArray("dotsArr",arr);
            promise.resolve(map);
        }catch (Exception e){
            promise.reject(e);
        }
    }

    @ReactMethod
    public void getPointCount(String GraphicMultiPointId,Promise promise){
        try{
            GraphicMultiPoint GraphicMultiPoint = getObjFromList(GraphicMultiPointId);
            int pointCount = GraphicMultiPoint.getPointCount();

            promise.resolve(pointCount);
        }catch (Exception e){
            promise.reject(e);
        }
    }

    @ReactMethod
    public void getPoint(String GraphicMultiPointId,int index,Promise promise){
        try{
            GraphicMultiPoint GraphicMultiPoint = getObjFromList(GraphicMultiPointId);
            Dot dot = GraphicMultiPoint.getPoint(index);

            String dotID = JSDot.registerId(dot);

            WritableMap map = Arguments.createMap();
            map.putString("dotID",dotID);
            promise.resolve(map);

        }catch (Exception e){
            promise.reject(e);
        }
    }

    @ReactMethod
    public void setPointSize(String GraphicMultiPointId,double size,Promise promise){
        try{
            GraphicMultiPoint GraphicMultiPoint = getObjFromList(GraphicMultiPointId);
            GraphicMultiPoint.setPointSize(size);

        }catch (Exception e){
            promise.reject(e);
        }
    }

    @ReactMethod
    public void getPointSize(String GraphicMultiPointId,Promise promise){
        try{
            GraphicMultiPoint GraphicMultiPoint = getObjFromList(GraphicMultiPointId);
            double pointSize = GraphicMultiPoint.getPointSize();

            promise.resolve(pointSize);
        }catch (Exception e){
            promise.reject(e);
        }
    }


    @ReactMethod
    public void appendPoint(String GraphicMultiPointId,String dotID,Promise promise){
        try{
            GraphicMultiPoint GraphicMultiPoint = getObjFromList(GraphicMultiPointId);
            Dot dot = JSDot.getObjFromList(dotID);
            GraphicMultiPoint.appendPoint(dot);

        }catch (Exception e){
            promise.reject(e);
        }
    }
    @ReactMethod
    public void appendPoints(String GraphicMultiPointId,ReadableArray pointArray,Promise promise){
        try{
            GraphicMultiPoint GraphicMultiPoint = getObjFromList(GraphicMultiPointId);

            if(GraphicMultiPoint != null)
            {
                Dot[] dotLst = new Dot[pointArray.size()];
                for(int i =0; i < pointArray.size();i++)
                {
                    ReadableMap readable = pointArray.getMap(i);
                    String keyStr = readable.getString("_MGDotId");
                    dotLst[i]  = JSDot.getObjFromList(keyStr);
                }
                GraphicMultiPoint.appendPoints(dotLst);
            }

        }catch (Exception e){
            promise.reject(e);
        }
    }

    @ReactMethod
    public void updatePoint(String GraphicMultiPointId,int index,String dotID,Promise promise){
        try{
            GraphicMultiPoint GraphicMultiPoint = getObjFromList(GraphicMultiPointId);
            Dot dot = JSDot.getObjFromList(dotID);
            GraphicMultiPoint.updatePoint(index,dot);

        }catch (Exception e){
            promise.reject(e);
        }
    }

    @ReactMethod
    public void removePoint(String GraphicMultiPointId,int index,Promise promise){
        try{
            GraphicMultiPoint GraphicMultiPoint = getObjFromList(GraphicMultiPointId);
           GraphicMultiPoint.removePoint(index);

        }catch (Exception e){
            promise.reject(e);
        }
    }

    @ReactMethod
    public void removeAllPoints(String GraphicMultiPointId,Promise promise){
        try{
            GraphicMultiPoint GraphicMultiPoint = getObjFromList(GraphicMultiPointId);
            GraphicMultiPoint.removeAllPoints();

        }catch (Exception e){
            promise.reject(e);
        }
    }

    @ReactMethod
    public void insertPoint(String GraphicMultiPointId,int index,String dotID,Promise promise){
        try{
            GraphicMultiPoint GraphicMultiPoint = getObjFromList(GraphicMultiPointId);
            Dot dot = JSDot.getObjFromList(dotID);
            GraphicMultiPoint.insertPoint(index,dot);

        }catch (Exception e){
            promise.reject(e);
        }
    }
}
