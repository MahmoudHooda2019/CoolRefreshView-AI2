package com.aemo.pollrefresh;


import android.widget.Toast;
import com.google.appinventor.components.annotations.DesignerProperty;
import com.google.appinventor.components.annotations.SimpleEvent;
import com.google.appinventor.components.annotations.SimpleFunction;
import com.google.appinventor.components.annotations.SimpleProperty;
import com.google.appinventor.components.common.PropertyTypeConstants;
import com.google.appinventor.components.runtime.*;
import com.google.appinventor.components.runtime.util.YailList;

import android.content.res.Resources;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.R.color;
import jp.co.recruit_lifestyle.android.widget.WaveSwipeRefreshLayout;

public class CoolRefreshView extends AndroidNonvisibleComponent {


  private static final String LOG_TAG = "SwipeRefresh";

  private ComponentContainer container;
  private Resources res;

  private WaveSwipeRefreshLayout srl;

  private boolean enabled = true;
  private boolean nestedScrollingEnabled = true;
  private int waveColor = 0xFFFAFAFA;
  private YailList colorList;

  private int topOffset = 0;
  private int dropHeight = 700;
  private int radius = 15;

  public CoolRefreshView(ComponentContainer container) {
    super(container.$form());
    Log.d(LOG_TAG, "SwipeRefresh Created");

    this.container = container;
    res = container.$context().getResources();

    SchemeColorsList(YailList.makeList(new Object[]{
            _Color_holo_blue_bright(),
            _Color_holo_green_light(),
            _Color_holo_orange_light(),
            _Color_holo_red_light()
    }));
  }

  @SimpleEvent
  public void OnError(String error,String errorFrom) {
    EventDispatcher.dispatchEvent(this, "OnError",error,errorFrom);
  }
  @SimpleEvent
  public void OnRefresh() {
    EventDispatcher.dispatchEvent(this, "OnRefresh");
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  @SimpleFunction
  public void CancelRefreshing() {
    if (srl != null) {
      Refreshing(false);
    }
  }
  @SimpleFunction
  public boolean CanScrollUp(){
      return srl.canChildScrollUp();
  }
  /////////////////////////////////////////////////////////////////////////////////////////////////
  @SimpleProperty
  public void Refreshing(boolean refreshing) {
    if (srl != null) {
      srl.setRefreshing(refreshing);
    }
  }
  @SimpleProperty
  public boolean Refreshing() {
    if (srl != null) {
      return srl.isRefreshing();
    }
    return false;
  }
  /////////////////////////////////////////////////////////////////////////////////////////////////
  @SimpleProperty
  @DesignerProperty(editorType = PropertyTypeConstants.PROPERTY_TYPE_BOOLEAN, defaultValue = "True")
  public void Enabled(boolean enabled) {
    this.enabled = enabled;
    if (srl != null) {
      srl.setEnabled(enabled);
    }
  }
  @SimpleProperty
  public boolean Enabled() {
    return enabled;
  }
  /////////////////////////////////////////////////////////////////////////////////////////////////
  @SimpleProperty
  @DesignerProperty(editorType = PropertyTypeConstants.PROPERTY_TYPE_BOOLEAN, defaultValue = "True")
  public void NestedScrollingEnabled(boolean enabled) {
    this.nestedScrollingEnabled = enabled;
    if (srl != null) {
      srl.setNestedScrollingEnabled(enabled);
    }
  }
  @SimpleProperty
  public boolean NestedScrollingEnabled() {
    if (srl != null) {
      return srl.isNestedScrollingEnabled();
    }
    return nestedScrollingEnabled;
  }
  /////////////////////////////////////////////////////////////////////////////////////////////////
  @SimpleProperty(description = "set top Offset refresh layout")
  @DesignerProperty(editorType = PropertyTypeConstants.PROPERTY_TYPE_NON_NEGATIVE_INTEGER, defaultValue = "0")
  public void TopOffSet(int topOffset) {
    this.topOffset = topOffset;
    if (srl != null) {
      srl.setTopOffsetOfWave(topOffset);
    }
  }
  @SimpleProperty
  public int TopOffSet() {
    return topOffset;
  }
  /////////////////////////////////////////////////////////////////////////////////////////////////
  @SimpleProperty(description = "set MaxDropHeight refresh layout")
  @DesignerProperty(editorType = PropertyTypeConstants.PROPERTY_TYPE_NON_NEGATIVE_INTEGER, defaultValue = "700")
  public void MaxDropHeight(int dropHeight) {
    this.dropHeight = dropHeight;
    if (srl != null) {
      srl.setMaxDropHeight(dropHeight);
    }
  }
  @SimpleProperty
  public int MaxDropHeight() {
    return dropHeight;
  }
  /////////////////////////////////////////////////////////////////////////////////////////////////
  @SimpleProperty(description = "set radius of refresh layout")
  @DesignerProperty(editorType = PropertyTypeConstants.PROPERTY_TYPE_NON_NEGATIVE_INTEGER, defaultValue = "15")
  public void ShadowRadius(int radius) {
    this.radius = radius;
    if (srl != null) {
      srl.setShadowRadius(radius);
    }
  }
  @SimpleProperty
  public int ShadowRadius() {
    return radius;
  }
  /////////////////////////////////////////////////////////////////////////////////////////////////
  @SimpleProperty(description = "have to set the color of refresh layout")
  @DesignerProperty(editorType = PropertyTypeConstants.PROPERTY_TYPE_COLOR, defaultValue = Component.DEFAULT_VALUE_COLOR_DEFAULT)
  public void RefreshColor(int color) {
    if(color == Component.COLOR_DEFAULT) {
      color = 0xFFFAFAFA;
    }
    this.waveColor = color;
    if (srl != null) {
      srl.setWaveColor(color);
    }
  }
  @SimpleProperty
  public int RefreshColor() {
    return waveColor;
  }
  /////////////////////////////////////////////////////////////////////////////////////////////////
  @SimpleProperty
  public void SchemeColorsList(YailList list) {
    if(list == null) {
      return;
    }
    if (srl != null) {
      this.colorList = list;
      int[] color = new int[list.size()];
      for(int i = list.size()-1; i >= 0; i--) {  //avoid calling size() for many times
        color[i] = Integer.parseInt(list.getString(i));
      }
      srl.setColorSchemeColors(color);
    }
  }
  @SimpleProperty
  public YailList SchemeColorsList() {
    return colorList;
  }
  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////
  /*
  @SimpleFunction(description = "Vertical Scroll Arrangement allowed only")
  public void RegisterArrangement(VerticalArrangement arrangement) {
    register(arrangement);
  }

  @SimpleFunction(description = "Vertical Scroll Arrangement allowed only")
  public void RegisterVSArrangement(VerticalScrollArrangement vsArrangement) {
    register(vsArrangement);
  }
  @SimpleFunction(description = "Vertical Scroll Arrangement allowed only")
  public void RegisterListView(ListView listView) {
    register(listView);
  }

   */

  @SimpleFunction
  public void Initialize(Object view){
    if (view instanceof VerticalArrangement || view instanceof VerticalScrollArrangement || view instanceof ListView){
      register((AndroidViewComponent) view);
    }else {
      OnError("can't register your view try VerticalArrangement or VerticalScrollArrangement or ListView","Initialize view");
    }
  }

  private void register(AndroidViewComponent component) {
    if (srl != null) {
      return;
    }
    srl = new WaveSwipeRefreshLayout(container.$context());

    Enabled(Enabled());
    NestedScrollingEnabled(NestedScrollingEnabled());
    TopOffSet(TopOffSet());
    MaxDropHeight(MaxDropHeight());
    ShadowRadius(ShadowRadius());
    RefreshColor(RefreshColor());
    SchemeColorsList(SchemeColorsList());

    srl.setOnRefreshListener(new WaveSwipeRefreshLayout.OnRefreshListener() {
      @Override
      public void onRefresh() {
        OnRefresh();
      }
    });

    View child = component.getView();
    ViewGroup vg = (ViewGroup) child.getParent();
    if (vg.getChildCount() <= 0) {
      return;
    }
    vg.addView(srl, vg.indexOfChild(child));
    vg.removeView(child);
    srl.addView(child);
  }

  public int _Color_holo_blue_bright() {
    return res.getColor(color.holo_blue_bright);
  }
  public int _Color_holo_green_light() {
    return res.getColor(color.holo_green_light);
  }
  public int _Color_holo_orange_light() {
    return res.getColor(color.holo_orange_light);
  }
  public int _Color_holo_red_light() {
    return res.getColor(color.holo_red_light);
  }


}