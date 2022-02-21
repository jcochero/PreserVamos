package appear.pnud.preservamos;


import anywheresoftware.b4a.B4AMenuItem;
import android.app.Activity;
import android.os.Bundle;
import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.BALayout;
import anywheresoftware.b4a.B4AActivity;
import anywheresoftware.b4a.ObjectWrapper;
import anywheresoftware.b4a.objects.ActivityWrapper;
import java.lang.reflect.InvocationTargetException;
import anywheresoftware.b4a.B4AUncaughtException;
import anywheresoftware.b4a.debug.*;
import java.lang.ref.WeakReference;

public class frmlocalizacion extends Activity implements B4AActivity{
	public static frmlocalizacion mostCurrent;
	static boolean afterFirstLayout;
	static boolean isFirst = true;
    private static boolean processGlobalsRun = false;
	BALayout layout;
	public static BA processBA;
	BA activityBA;
    ActivityWrapper _activity;
    java.util.ArrayList<B4AMenuItem> menuItems;
	public static final boolean fullScreen = false;
	public static final boolean includeTitle = false;
    public static WeakReference<Activity> previousOne;
    public static boolean dontPause;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        mostCurrent = this;
		if (processBA == null) {
			processBA = new BA(this.getApplicationContext(), null, null, "appear.pnud.preservamos", "appear.pnud.preservamos.frmlocalizacion");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
            
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (frmlocalizacion).");
				p.finish();
			}
		}
        processBA.setActivityPaused(true);
        processBA.runHook("oncreate", this, null);
		if (!includeTitle) {
        	this.getWindow().requestFeature(android.view.Window.FEATURE_NO_TITLE);
        }
        if (fullScreen) {
        	getWindow().setFlags(android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN,   
        			android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
		
        processBA.sharedProcessBA.activityBA = null;
		layout = new BALayout(this);
		setContentView(layout);
		afterFirstLayout = false;
        WaitForLayout wl = new WaitForLayout();
        if (anywheresoftware.b4a.objects.ServiceHelper.StarterHelper.startFromActivity(this, processBA, wl, false))
		    BA.handler.postDelayed(wl, 5);

	}
	static class WaitForLayout implements Runnable {
		public void run() {
			if (afterFirstLayout)
				return;
			if (mostCurrent == null)
				return;
            
			if (mostCurrent.layout.getWidth() == 0) {
				BA.handler.postDelayed(this, 5);
				return;
			}
			mostCurrent.layout.getLayoutParams().height = mostCurrent.layout.getHeight();
			mostCurrent.layout.getLayoutParams().width = mostCurrent.layout.getWidth();
			afterFirstLayout = true;
			mostCurrent.afterFirstLayout();
		}
	}
	private void afterFirstLayout() {
        if (this != mostCurrent)
			return;
		activityBA = new BA(this, layout, processBA, "appear.pnud.preservamos", "appear.pnud.preservamos.frmlocalizacion");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "appear.pnud.preservamos.frmlocalizacion", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density, mostCurrent);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (frmlocalizacion) Create, isFirst = " + isFirst + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (frmlocalizacion) Resume **");
        processBA.raiseEvent(null, "activity_resume");
        if (android.os.Build.VERSION.SDK_INT >= 11) {
			try {
				android.app.Activity.class.getMethod("invalidateOptionsMenu").invoke(this,(Object[]) null);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}
	public void addMenuItem(B4AMenuItem item) {
		if (menuItems == null)
			menuItems = new java.util.ArrayList<B4AMenuItem>();
		menuItems.add(item);
	}
	@Override
	public boolean onCreateOptionsMenu(android.view.Menu menu) {
		super.onCreateOptionsMenu(menu);
        try {
            if (processBA.subExists("activity_actionbarhomeclick")) {
                Class.forName("android.app.ActionBar").getMethod("setHomeButtonEnabled", boolean.class).invoke(
                    getClass().getMethod("getActionBar").invoke(this), true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (processBA.runHook("oncreateoptionsmenu", this, new Object[] {menu}))
            return true;
		if (menuItems == null)
			return false;
		for (B4AMenuItem bmi : menuItems) {
			android.view.MenuItem mi = menu.add(bmi.title);
			if (bmi.drawable != null)
				mi.setIcon(bmi.drawable);
            if (android.os.Build.VERSION.SDK_INT >= 11) {
				try {
                    if (bmi.addToBar) {
				        android.view.MenuItem.class.getMethod("setShowAsAction", int.class).invoke(mi, 1);
                    }
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			mi.setOnMenuItemClickListener(new B4AMenuItemsClickListener(bmi.eventName.toLowerCase(BA.cul)));
		}
        
		return true;
	}   
 @Override
 public boolean onOptionsItemSelected(android.view.MenuItem item) {
    if (item.getItemId() == 16908332) {
        processBA.raiseEvent(null, "activity_actionbarhomeclick");
        return true;
    }
    else
        return super.onOptionsItemSelected(item); 
}
@Override
 public boolean onPrepareOptionsMenu(android.view.Menu menu) {
    super.onPrepareOptionsMenu(menu);
    processBA.runHook("onprepareoptionsmenu", this, new Object[] {menu});
    return true;
    
 }
 protected void onStart() {
    super.onStart();
    processBA.runHook("onstart", this, null);
}
 protected void onStop() {
    super.onStop();
    processBA.runHook("onstop", this, null);
}
    public void onWindowFocusChanged(boolean hasFocus) {
       super.onWindowFocusChanged(hasFocus);
       if (processBA.subExists("activity_windowfocuschanged"))
           processBA.raiseEvent2(null, true, "activity_windowfocuschanged", false, hasFocus);
    }
	private class B4AMenuItemsClickListener implements android.view.MenuItem.OnMenuItemClickListener {
		private final String eventName;
		public B4AMenuItemsClickListener(String eventName) {
			this.eventName = eventName;
		}
		public boolean onMenuItemClick(android.view.MenuItem item) {
			processBA.raiseEventFromUI(item.getTitle(), eventName + "_click");
			return true;
		}
	}
    public static Class<?> getObject() {
		return frmlocalizacion.class;
	}
    private Boolean onKeySubExist = null;
    private Boolean onKeyUpSubExist = null;
	@Override
	public boolean onKeyDown(int keyCode, android.view.KeyEvent event) {
        if (processBA.runHook("onkeydown", this, new Object[] {keyCode, event}))
            return true;
		if (onKeySubExist == null)
			onKeySubExist = processBA.subExists("activity_keypress");
		if (onKeySubExist) {
			if (keyCode == anywheresoftware.b4a.keywords.constants.KeyCodes.KEYCODE_BACK &&
					android.os.Build.VERSION.SDK_INT >= 18) {
				HandleKeyDelayed hk = new HandleKeyDelayed();
				hk.kc = keyCode;
				BA.handler.post(hk);
				return true;
			}
			else {
				boolean res = new HandleKeyDelayed().runDirectly(keyCode);
				if (res)
					return true;
			}
		}
		return super.onKeyDown(keyCode, event);
	}
	private class HandleKeyDelayed implements Runnable {
		int kc;
		public void run() {
			runDirectly(kc);
		}
		public boolean runDirectly(int keyCode) {
			Boolean res =  (Boolean)processBA.raiseEvent2(_activity, false, "activity_keypress", false, keyCode);
			if (res == null || res == true) {
                return true;
            }
            else if (keyCode == anywheresoftware.b4a.keywords.constants.KeyCodes.KEYCODE_BACK) {
				finish();
				return true;
			}
            return false;
		}
		
	}
    @Override
	public boolean onKeyUp(int keyCode, android.view.KeyEvent event) {
        if (processBA.runHook("onkeyup", this, new Object[] {keyCode, event}))
            return true;
		if (onKeyUpSubExist == null)
			onKeyUpSubExist = processBA.subExists("activity_keyup");
		if (onKeyUpSubExist) {
			Boolean res =  (Boolean)processBA.raiseEvent2(_activity, false, "activity_keyup", false, keyCode);
			if (res == null || res == true)
				return true;
		}
		return super.onKeyUp(keyCode, event);
	}
	@Override
	public void onNewIntent(android.content.Intent intent) {
        super.onNewIntent(intent);
		this.setIntent(intent);
        processBA.runHook("onnewintent", this, new Object[] {intent});
	}
    @Override 
	public void onPause() {
		super.onPause();
        if (_activity == null)
            return;
        if (this != mostCurrent)
			return;
		anywheresoftware.b4a.Msgbox.dismiss(true);
        if (!dontPause)
            BA.LogInfo("** Activity (frmlocalizacion) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
        else
            BA.LogInfo("** Activity (frmlocalizacion) Pause event (activity is not paused). **");
        if (mostCurrent != null)
            processBA.raiseEvent2(_activity, true, "activity_pause", false, activityBA.activity.isFinishing());		
        if (!dontPause) {
            processBA.setActivityPaused(true);
            mostCurrent = null;
        }

        if (!activityBA.activity.isFinishing())
			previousOne = new WeakReference<Activity>(this);
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        processBA.runHook("onpause", this, null);
	}

	@Override
	public void onDestroy() {
        super.onDestroy();
		previousOne = null;
        processBA.runHook("ondestroy", this, null);
	}
    @Override 
	public void onResume() {
		super.onResume();
        mostCurrent = this;
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (activityBA != null) { //will be null during activity create (which waits for AfterLayout).
        	ResumeMessage rm = new ResumeMessage(mostCurrent);
        	BA.handler.post(rm);
        }
        processBA.runHook("onresume", this, null);
	}
    private static class ResumeMessage implements Runnable {
    	private final WeakReference<Activity> activity;
    	public ResumeMessage(Activity activity) {
    		this.activity = new WeakReference<Activity>(activity);
    	}
		public void run() {
            frmlocalizacion mc = mostCurrent;
			if (mc == null || mc != activity.get())
				return;
			processBA.setActivityPaused(false);
            BA.LogInfo("** Activity (frmlocalizacion) Resume **");
            if (mc != mostCurrent)
                return;
		    processBA.raiseEvent(mc._activity, "activity_resume", (Object[])null);
		}
    }
	@Override
	protected void onActivityResult(int requestCode, int resultCode,
	      android.content.Intent data) {
		processBA.onActivityResult(requestCode, resultCode, data);
        processBA.runHook("onactivityresult", this, new Object[] {requestCode, resultCode});
	}
	private static void initializeGlobals() {
		processBA.raiseEvent2(null, true, "globals", false, (Object[])null);
	}
    public void onRequestPermissionsResult(int requestCode,
        String permissions[], int[] grantResults) {
        for (int i = 0;i < permissions.length;i++) {
            Object[] o = new Object[] {permissions[i], grantResults[i] == 0};
            processBA.raiseEventFromDifferentThread(null,null, 0, "activity_permissionresult", true, o);
        }
            
    }

public anywheresoftware.b4a.keywords.Common __c = null;
public static String _tipodetect = "";
public static String _origen = "";
public static String _tipoambiente = "";
public static String _currentproject = "";
public anywheresoftware.b4a.objects.LabelWrapper _lbllat = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbllon = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btncontinuarlocalizacion = null;
public anywheresoftware.b4a.objects.MapFragmentWrapper.GoogleMapWrapper _gmap = null;
public anywheresoftware.b4a.objects.MapFragmentWrapper _mapfragment1 = null;
public anywheresoftware.b4a.objects.MapFragmentWrapper.MarkerWrapper _mymarker = null;
public anywheresoftware.b4a.objects.RuntimePermissions _rp = null;
public anywheresoftware.b4a.phone.Phone _p = null;
public b4a.example.dateutils _dateutils = null;
public appear.pnud.preservamos.main _main = null;
public appear.pnud.preservamos.form_main _form_main = null;
public appear.pnud.preservamos.reporte_fotos _reporte_fotos = null;
public appear.pnud.preservamos.dbutils _dbutils = null;
public appear.pnud.preservamos.reporte_habitat_laguna _reporte_habitat_laguna = null;
public appear.pnud.preservamos.reporte_habitat_rio _reporte_habitat_rio = null;
public appear.pnud.preservamos.aprender_muestreo _aprender_muestreo = null;
public appear.pnud.preservamos.downloadservice _downloadservice = null;
public appear.pnud.preservamos.firebasemessaging _firebasemessaging = null;
public appear.pnud.preservamos.form_reporte _form_reporte = null;
public appear.pnud.preservamos.frmabout _frmabout = null;
public appear.pnud.preservamos.frmdatosanteriores _frmdatosanteriores = null;
public appear.pnud.preservamos.frmeditprofile _frmeditprofile = null;
public appear.pnud.preservamos.frmfelicitaciones _frmfelicitaciones = null;
public appear.pnud.preservamos.frmperfil _frmperfil = null;
public appear.pnud.preservamos.frmpoliticadatos _frmpoliticadatos = null;
public appear.pnud.preservamos.httputils2service _httputils2service = null;
public appear.pnud.preservamos.register _register = null;
public appear.pnud.preservamos.reporte_envio _reporte_envio = null;
public appear.pnud.preservamos.starter _starter = null;
public appear.pnud.preservamos.uploadfiles _uploadfiles = null;
public appear.pnud.preservamos.utilidades _utilidades = null;
public appear.pnud.preservamos.xuiviewsutils _xuiviewsutils = null;

public static void initializeProcessGlobals() {
             try {
                Class.forName(BA.applicationContext.getPackageName() + ".main").getMethod("initializeProcessGlobals").invoke(null, null);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
}
public static void  _activity_create(boolean _firsttime) throws Exception{
ResumableSub_Activity_Create rsub = new ResumableSub_Activity_Create(null,_firsttime);
rsub.resume(processBA, null);
}
public static class ResumableSub_Activity_Create extends BA.ResumableSub {
public ResumableSub_Activity_Create(appear.pnud.preservamos.frmlocalizacion parent,boolean _firsttime) {
this.parent = parent;
this._firsttime = _firsttime;
}
appear.pnud.preservamos.frmlocalizacion parent;
boolean _firsttime;

@Override
public void resume(BA ba, Object[] result) throws Exception{

    while (true) {
        switch (state) {
            case -1:
return;

case 0:
//C
this.state = 1;
 //BA.debugLineNum = 31;BA.debugLine="currentproject = Form_Reporte.currentproject";
parent._currentproject = parent.mostCurrent._form_reporte._currentproject /*String*/ ;
 //BA.debugLineNum = 33;BA.debugLine="tipoAmbiente = Form_Reporte.tipoAmbiente";
parent._tipoambiente = parent.mostCurrent._form_reporte._tipoambiente /*String*/ ;
 //BA.debugLineNum = 34;BA.debugLine="tipoDetect = \"GPSdetect\"";
parent._tipodetect = "GPSdetect";
 //BA.debugLineNum = 36;BA.debugLine="Activity.LoadLayout(\"Google_Map\")";
parent.mostCurrent._activity.LoadLayout("Google_Map",mostCurrent.activityBA);
 //BA.debugLineNum = 39;BA.debugLine="Wait For MapFragment1_Ready";
anywheresoftware.b4a.keywords.Common.WaitFor("mapfragment1_ready", processBA, this, null);
this.state = 11;
return;
case 11:
//C
this.state = 1;
;
 //BA.debugLineNum = 40;BA.debugLine="gmap = MapFragment1.GetMap";
parent.mostCurrent._gmap = parent.mostCurrent._mapfragment1.GetMap();
 //BA.debugLineNum = 41;BA.debugLine="gmap.MyLocationEnabled = True";
parent.mostCurrent._gmap.setMyLocationEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 43;BA.debugLine="If gmap.IsInitialized = False Then";
if (true) break;

case 1:
//if
this.state = 10;
if (parent.mostCurrent._gmap.IsInitialized()==anywheresoftware.b4a.keywords.Common.False) { 
this.state = 3;
}else {
this.state = 5;
}if (true) break;

case 3:
//C
this.state = 10;
 //BA.debugLineNum = 44;BA.debugLine="ToastMessageShow(\"Error initializing map.\", True";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Error initializing map."),anywheresoftware.b4a.keywords.Common.True);
 if (true) break;

case 5:
//C
this.state = 6;
 //BA.debugLineNum = 46;BA.debugLine="Do While Not(gmap.MyLocation.IsInitialized)";
if (true) break;

case 6:
//do while
this.state = 9;
while (anywheresoftware.b4a.keywords.Common.Not(parent.mostCurrent._gmap.getMyLocation().IsInitialized())) {
this.state = 8;
if (true) break;
}
if (true) break;

case 8:
//C
this.state = 6;
 //BA.debugLineNum = 47;BA.debugLine="Sleep(0)";
anywheresoftware.b4a.keywords.Common.Sleep(mostCurrent.activityBA,this,(int) (0));
this.state = 12;
return;
case 12:
//C
this.state = 6;
;
 if (true) break;

case 9:
//C
this.state = 10;
;
 //BA.debugLineNum = 49;BA.debugLine="CargarMapa";
_cargarmapa();
 if (true) break;

case 10:
//C
this.state = -1;
;
 //BA.debugLineNum = 52;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public static void  _mapfragment1_ready() throws Exception{
}
public static boolean  _activity_keypress(int _keycode) throws Exception{
 //BA.debugLineNum = 53;BA.debugLine="Sub Activity_KeyPress (KeyCode As Int) As Boolean";
 //BA.debugLineNum = 54;BA.debugLine="If KeyCode = KeyCodes.KEYCODE_BACK Then";
if (_keycode==anywheresoftware.b4a.keywords.Common.KeyCodes.KEYCODE_BACK) { 
 //BA.debugLineNum = 55;BA.debugLine="Activity.finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 56;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 };
 //BA.debugLineNum = 58;BA.debugLine="End Sub";
return false;
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 62;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 65;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 59;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 61;BA.debugLine="End Sub";
return "";
}
public static String  _btncontinuarlocalizacion_click() throws Exception{
anywheresoftware.b4a.objects.collections.Map _map1 = null;
 //BA.debugLineNum = 102;BA.debugLine="Sub btnContinuarLocalizacion_Click";
 //BA.debugLineNum = 103;BA.debugLine="If lblLat.IsInitialized = False Then";
if (mostCurrent._lbllat.IsInitialized()==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 104;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 106;BA.debugLine="lblLat.Text = myMarker.Position.Latitude";
mostCurrent._lbllat.setText(BA.ObjectToCharSequence(mostCurrent._mymarker.getPosition().getLatitude()));
 //BA.debugLineNum = 107;BA.debugLine="lblLon.Text = myMarker.Position.Longitude";
mostCurrent._lbllon.setText(BA.ObjectToCharSequence(mostCurrent._mymarker.getPosition().getLongitude()));
 //BA.debugLineNum = 109;BA.debugLine="If lblLat.Text = \"\" Or lblLat.Text = \"0\" Then";
if ((mostCurrent._lbllat.getText()).equals("") || (mostCurrent._lbllat.getText()).equals("0")) { 
 //BA.debugLineNum = 110;BA.debugLine="lblLat.Text = myMarker.Position.Latitude";
mostCurrent._lbllat.setText(BA.ObjectToCharSequence(mostCurrent._mymarker.getPosition().getLatitude()));
 //BA.debugLineNum = 111;BA.debugLine="lblLon.Text = myMarker.Position.Longitude";
mostCurrent._lbllon.setText(BA.ObjectToCharSequence(mostCurrent._mymarker.getPosition().getLongitude()));
 };
 //BA.debugLineNum = 115;BA.debugLine="If origen = \"cambio\" Then";
if ((_origen).equals("cambio")) { 
 //BA.debugLineNum = 116;BA.debugLine="frmDatosAnteriores.nuevalatlng = lblLat.Text & \"";
mostCurrent._frmdatosanteriores._nuevalatlng /*String*/  = mostCurrent._lbllat.getText()+"_"+mostCurrent._lbllon.getText();
 //BA.debugLineNum = 117;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 118;BA.debugLine="frmDatosAnteriores.notificacion = False";
mostCurrent._frmdatosanteriores._notificacion /*boolean*/  = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 119;BA.debugLine="CallSubDelayed(frmDatosAnteriores, \"CambiarUbica";
anywheresoftware.b4a.keywords.Common.CallSubDelayed(processBA,(Object)(mostCurrent._frmdatosanteriores.getObject()),"CambiarUbicacion");
 //BA.debugLineNum = 121;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 122;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 126;BA.debugLine="If lblLat.Text = \"\" Or lblLon.Text = \"\" Then";
if ((mostCurrent._lbllat.getText()).equals("") || (mostCurrent._lbllon.getText()).equals("")) { 
 //BA.debugLineNum = 127;BA.debugLine="ToastMessageShow(\"No se han detectado tus coord";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("No se han detectado tus coordenadas, intenta de nuevo!"),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 128;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 130;BA.debugLine="Dim Map1 As Map";
_map1 = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 131;BA.debugLine="Map1.Initialize";
_map1.Initialize();
 //BA.debugLineNum = 132;BA.debugLine="Map1.Put(\"Id\", Main.currentproject)";
_map1.Put((Object)("Id"),(Object)(mostCurrent._main._currentproject /*String*/ ));
 //BA.debugLineNum = 134;BA.debugLine="Main.latitud = lblLat.Text";
mostCurrent._main._latitud /*String*/  = mostCurrent._lbllat.getText();
 //BA.debugLineNum = 135;BA.debugLine="Main.longitud = lblLon.Text";
mostCurrent._main._longitud /*String*/  = mostCurrent._lbllon.getText();
 //BA.debugLineNum = 136;BA.debugLine="Main.dateandtime = DateTime.Date(DateTime.Now)";
mostCurrent._main._dateandtime /*String*/  = anywheresoftware.b4a.keywords.Common.DateTime.Date(anywheresoftware.b4a.keywords.Common.DateTime.getNow());
 //BA.debugLineNum = 138;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"evals\", \"dec";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals","decimallatitude",(Object)(mostCurrent._lbllat.getText()),_map1);
 //BA.debugLineNum = 139;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"evals\", \"dec";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals","decimallongitude",(Object)(mostCurrent._lbllon.getText()),_map1);
 //BA.debugLineNum = 140;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"evals\", \"geo";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals","georeferenceddate",(Object)(anywheresoftware.b4a.keywords.Common.DateTime.Date(anywheresoftware.b4a.keywords.Common.DateTime.getNow())),_map1);
 //BA.debugLineNum = 142;BA.debugLine="If tipoDetect = \"GPSdetect\" Then";
if ((_tipodetect).equals("GPSdetect")) { 
 //BA.debugLineNum = 143;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"evals\", \"gp";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals","gpsDetect",(Object)("si"),_map1);
 }else if((_tipodetect).equals("MAPAdetect")) { 
 //BA.debugLineNum = 145;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"evals\", \"ma";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals","mapaDetect",(Object)("si"),_map1);
 };
 //BA.debugLineNum = 150;BA.debugLine="ProgressDialogShow2(\"Cargando cuestionario...\", F";
anywheresoftware.b4a.keywords.Common.ProgressDialogShow2(mostCurrent.activityBA,BA.ObjectToCharSequence("Cargando cuestionario..."),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 151;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 152;BA.debugLine="Load_Reporte_Ambientes";
_load_reporte_ambientes();
 //BA.debugLineNum = 154;BA.debugLine="End Sub";
return "";
}
public static String  _cargarmapa() throws Exception{
anywheresoftware.b4a.objects.MapFragmentWrapper.LatLngWrapper _mylocation = null;
anywheresoftware.b4a.objects.MapFragmentWrapper.CameraPositionWrapper _cp = null;
uk.co.martinpearman.b4a.googlemapsextras.GoogleMapsExtras _googlemapsextras1 = null;
uk.co.martinpearman.b4a.com.google.android.gms.maps.googlemap.OnMarkerDragListener _onmarkerdraglistener1 = null;
 //BA.debugLineNum = 68;BA.debugLine="Sub CargarMapa";
 //BA.debugLineNum = 70;BA.debugLine="Dim myLocation As LatLng";
_mylocation = new anywheresoftware.b4a.objects.MapFragmentWrapper.LatLngWrapper();
 //BA.debugLineNum = 71;BA.debugLine="myLocation = gmap.MyLocation";
_mylocation = mostCurrent._gmap.getMyLocation();
 //BA.debugLineNum = 73;BA.debugLine="If myLocation.IsInitialized = False Then";
if (_mylocation.IsInitialized()==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 74;BA.debugLine="myLocation.Initialize(\"-34.9204950\",\"-57.9535660";
_mylocation.Initialize((double)(Double.parseDouble("-34.9204950")),(double)(Double.parseDouble("-57.9535660")));
 };
 //BA.debugLineNum = 76;BA.debugLine="Dim cp As CameraPosition";
_cp = new anywheresoftware.b4a.objects.MapFragmentWrapper.CameraPositionWrapper();
 //BA.debugLineNum = 77;BA.debugLine="cp.Initialize(myLocation.Latitude, myLocation.Lon";
_cp.Initialize(_mylocation.getLatitude(),_mylocation.getLongitude(),(float) (16));
 //BA.debugLineNum = 78;BA.debugLine="gmap.AnimateCamera(cp)";
mostCurrent._gmap.AnimateCamera((com.google.android.gms.maps.model.CameraPosition)(_cp.getObject()));
 //BA.debugLineNum = 79;BA.debugLine="gmap.MapType=gmap.MAP_TYPE_HYBRID";
mostCurrent._gmap.setMapType(mostCurrent._gmap.MAP_TYPE_HYBRID);
 //BA.debugLineNum = 82;BA.debugLine="myMarker=gmap.AddMarker(myLocation.Latitude, myLo";
mostCurrent._mymarker = mostCurrent._gmap.AddMarker(_mylocation.getLatitude(),_mylocation.getLongitude(),"Arrastra el punto");
 //BA.debugLineNum = 83;BA.debugLine="myMarker.Draggable=True";
mostCurrent._mymarker.setDraggable(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 86;BA.debugLine="Dim GoogleMapsExtras1 As GoogleMapsExtras";
_googlemapsextras1 = new uk.co.martinpearman.b4a.googlemapsextras.GoogleMapsExtras();
 //BA.debugLineNum = 88;BA.debugLine="Dim OnMarkerDragListener1 As OnMarkerDragListener";
_onmarkerdraglistener1 = new uk.co.martinpearman.b4a.com.google.android.gms.maps.googlemap.OnMarkerDragListener();
 //BA.debugLineNum = 89;BA.debugLine="OnMarkerDragListener1.Initialize(\"OnMarkerDragLis";
_onmarkerdraglistener1.Initialize(mostCurrent.activityBA,"OnMarkerDragListener1");
 //BA.debugLineNum = 90;BA.debugLine="If OnMarkerDragListener1.IsInitialized Then";
if (_onmarkerdraglistener1.IsInitialized()) { 
 //BA.debugLineNum = 91;BA.debugLine="GoogleMapsExtras1.SetOnMarkerDragListener(gmap,";
_googlemapsextras1.SetOnMarkerDragListener((com.google.android.gms.maps.GoogleMap)(mostCurrent._gmap.getObject()),(com.google.android.gms.maps.GoogleMap.OnMarkerDragListener)(_onmarkerdraglistener1.getObject()));
 }else {
 //BA.debugLineNum = 93;BA.debugLine="Log(\"OnMarkerDragListener1 is not initialized -";
anywheresoftware.b4a.keywords.Common.LogImpl("66488089","OnMarkerDragListener1 is not initialized - check that the Activity contains at least one callback Sub",0);
 };
 //BA.debugLineNum = 96;BA.debugLine="End Sub";
return "";
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 13;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 15;BA.debugLine="Private lblLat As Label";
mostCurrent._lbllat = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 16;BA.debugLine="Private lblLon As Label";
mostCurrent._lbllon = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 17;BA.debugLine="Private btnContinuarLocalizacion As Button";
mostCurrent._btncontinuarlocalizacion = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 20;BA.debugLine="Private gmap As GoogleMap";
mostCurrent._gmap = new anywheresoftware.b4a.objects.MapFragmentWrapper.GoogleMapWrapper();
 //BA.debugLineNum = 21;BA.debugLine="Private MapFragment1 As MapFragment";
mostCurrent._mapfragment1 = new anywheresoftware.b4a.objects.MapFragmentWrapper();
 //BA.debugLineNum = 22;BA.debugLine="Dim myMarker As Marker";
mostCurrent._mymarker = new anywheresoftware.b4a.objects.MapFragmentWrapper.MarkerWrapper();
 //BA.debugLineNum = 25;BA.debugLine="Dim rp As RuntimePermissions";
mostCurrent._rp = new anywheresoftware.b4a.objects.RuntimePermissions();
 //BA.debugLineNum = 26;BA.debugLine="Dim p As Phone";
mostCurrent._p = new anywheresoftware.b4a.phone.Phone();
 //BA.debugLineNum = 29;BA.debugLine="End Sub";
return "";
}
public static String  _load_reporte_ambientes() throws Exception{
 //BA.debugLineNum = 162;BA.debugLine="Sub Load_Reporte_Ambientes";
 //BA.debugLineNum = 164;BA.debugLine="If tipoAmbiente = \"llanura\" Then";
if ((_tipoambiente).equals("llanura")) { 
 //BA.debugLineNum = 165;BA.debugLine="StartActivity(Reporte_Habitat_Rio)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._reporte_habitat_rio.getObject()));
 //BA.debugLineNum = 166;BA.debugLine="Reporte_Habitat_Rio.tiporio = \"llanura\"";
mostCurrent._reporte_habitat_rio._tiporio /*String*/  = "llanura";
 }else if((_tipoambiente).equals("laguna")) { 
 //BA.debugLineNum = 168;BA.debugLine="StartActivity(Reporte_Habitat_Laguna)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._reporte_habitat_laguna.getObject()));
 //BA.debugLineNum = 169;BA.debugLine="Reporte_Habitat_Laguna.tiporio = \"laguna\"";
mostCurrent._reporte_habitat_laguna._tiporio /*String*/  = "laguna";
 };
 //BA.debugLineNum = 171;BA.debugLine="Activity.finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 172;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 174;BA.debugLine="End Sub";
return "";
}
public static String  _onmarkerdraglistener1_drag(anywheresoftware.b4a.objects.MapFragmentWrapper.MarkerWrapper _marker1) throws Exception{
 //BA.debugLineNum = 178;BA.debugLine="Sub OnMarkerDragListener1_Drag(Marker1 As Marker)";
 //BA.debugLineNum = 179;BA.debugLine="End Sub";
return "";
}
public static String  _onmarkerdraglistener1_dragend(anywheresoftware.b4a.objects.MapFragmentWrapper.MarkerWrapper _marker1) throws Exception{
 //BA.debugLineNum = 181;BA.debugLine="Sub OnMarkerDragListener1_DragEnd(Marker1 As Marke";
 //BA.debugLineNum = 183;BA.debugLine="lblLat.Text = myMarker.Position.Latitude";
mostCurrent._lbllat.setText(BA.ObjectToCharSequence(mostCurrent._mymarker.getPosition().getLatitude()));
 //BA.debugLineNum = 184;BA.debugLine="lblLon.Text = myMarker.Position.Longitude";
mostCurrent._lbllon.setText(BA.ObjectToCharSequence(mostCurrent._mymarker.getPosition().getLongitude()));
 //BA.debugLineNum = 185;BA.debugLine="End Sub";
return "";
}
public static String  _onmarkerdraglistener1_dragstart(anywheresoftware.b4a.objects.MapFragmentWrapper.MarkerWrapper _marker1) throws Exception{
 //BA.debugLineNum = 187;BA.debugLine="Sub OnMarkerDragListener1_DragStart(Marker1 As Mar";
 //BA.debugLineNum = 189;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 6;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 7;BA.debugLine="Dim tipoDetect As String";
_tipodetect = "";
 //BA.debugLineNum = 9;BA.debugLine="Dim origen As String";
_origen = "";
 //BA.debugLineNum = 10;BA.debugLine="Dim tipoAmbiente As String";
_tipoambiente = "";
 //BA.debugLineNum = 11;BA.debugLine="Dim currentproject As String";
_currentproject = "";
 //BA.debugLineNum = 12;BA.debugLine="End Sub";
return "";
}
}
