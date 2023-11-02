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

public class frmmapa extends Activity implements B4AActivity{
	public static frmmapa mostCurrent;
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
			processBA = new BA(this.getApplicationContext(), null, null, "appear.pnud.preservamos", "appear.pnud.preservamos.frmmapa");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
            
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (frmmapa).");
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
		activityBA = new BA(this, layout, processBA, "appear.pnud.preservamos", "appear.pnud.preservamos.frmmapa");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "appear.pnud.preservamos.frmmapa", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density, mostCurrent);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (frmmapa) Create " + (isFirst ? "(first time)" : "") + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (frmmapa) Resume **");
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
		return frmmapa.class;
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
            BA.LogInfo("** Activity (frmmapa) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
        else
            BA.LogInfo("** Activity (frmmapa) Pause event (activity is not paused). **");
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
            frmmapa mc = mostCurrent;
			if (mc == null || mc != activity.get())
				return;
			processBA.setActivityPaused(false);
            BA.LogInfo("** Activity (frmmapa) Resume **");
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
public static String _datosmunicipio_string = "";
public static anywheresoftware.b4a.objects.collections.JSONParser _datosmunicipio_parser = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbllat = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbllon = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btncontinuarlocalizacion = null;
public anywheresoftware.b4a.objects.MapFragmentWrapper.GoogleMapWrapper _gmap = null;
public anywheresoftware.b4a.objects.MapFragmentWrapper _mapfragment1 = null;
public anywheresoftware.b4a.objects.MapFragmentWrapper.MarkerWrapper _mymarker = null;
public anywheresoftware.b4a.objects.RuntimePermissions _rp = null;
public anywheresoftware.b4a.phone.Phone _p = null;
public anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper _markerred = null;
public anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper _markerorange = null;
public anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper _markeryellow = null;
public anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper _markergreen = null;
public anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper _markerblue = null;
public anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper _markergray = null;
public b4a.example.dateutils _dateutils = null;
public appear.pnud.preservamos.main _main = null;
public appear.pnud.preservamos.form_main _form_main = null;
public appear.pnud.preservamos.frmabout _frmabout = null;
public appear.pnud.preservamos.alerta_fotos _alerta_fotos = null;
public appear.pnud.preservamos.alertas _alertas = null;
public appear.pnud.preservamos.aprender_muestreo _aprender_muestreo = null;
public appear.pnud.preservamos.dbutils _dbutils = null;
public appear.pnud.preservamos.downloadservice _downloadservice = null;
public appear.pnud.preservamos.firebasemessaging _firebasemessaging = null;
public appear.pnud.preservamos.form_reporte _form_reporte = null;
public appear.pnud.preservamos.frmdatosanteriores _frmdatosanteriores = null;
public appear.pnud.preservamos.frmdatossinenviar _frmdatossinenviar = null;
public appear.pnud.preservamos.frmeditprofile _frmeditprofile = null;
public appear.pnud.preservamos.frmfelicitaciones _frmfelicitaciones = null;
public appear.pnud.preservamos.frmlocalizacion _frmlocalizacion = null;
public appear.pnud.preservamos.frmmunicipioestadisticas _frmmunicipioestadisticas = null;
public appear.pnud.preservamos.frmpoliticadatos _frmpoliticadatos = null;
public appear.pnud.preservamos.frmtiporeporte _frmtiporeporte = null;
public appear.pnud.preservamos.httputils2service _httputils2service = null;
public appear.pnud.preservamos.imagedownloader _imagedownloader = null;
public appear.pnud.preservamos.inatcheck _inatcheck = null;
public appear.pnud.preservamos.mod_hidro _mod_hidro = null;
public appear.pnud.preservamos.mod_hidro_fotos _mod_hidro_fotos = null;
public appear.pnud.preservamos.mod_residuos _mod_residuos = null;
public appear.pnud.preservamos.mod_residuos_fotos _mod_residuos_fotos = null;
public appear.pnud.preservamos.register _register = null;
public appear.pnud.preservamos.reporte_envio _reporte_envio = null;
public appear.pnud.preservamos.reporte_fotos _reporte_fotos = null;
public appear.pnud.preservamos.reporte_habitat_laguna _reporte_habitat_laguna = null;
public appear.pnud.preservamos.reporte_habitat_rio _reporte_habitat_rio = null;
public appear.pnud.preservamos.reporte_habitat_rio_bu _reporte_habitat_rio_bu = null;
public appear.pnud.preservamos.reporte_habitat_rio_sierras _reporte_habitat_rio_sierras = null;
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
public ResumableSub_Activity_Create(appear.pnud.preservamos.frmmapa parent,boolean _firsttime) {
this.parent = parent;
this._firsttime = _firsttime;
}
appear.pnud.preservamos.frmmapa parent;
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
 //BA.debugLineNum = 44;BA.debugLine="Activity.LoadLayout(\"Google_Map_Main\")";
parent.mostCurrent._activity.LoadLayout("Google_Map_Main",mostCurrent.activityBA);
 //BA.debugLineNum = 45;BA.debugLine="If origen = \"municipio\" Then";
if (true) break;

case 1:
//if
this.state = 6;
if ((parent._origen).equals("municipio")) { 
this.state = 3;
}else {
this.state = 5;
}if (true) break;

case 3:
//C
this.state = 6;
 //BA.debugLineNum = 46;BA.debugLine="ProgressDialogShow(\"Cargando datos del municipio";
anywheresoftware.b4a.keywords.Common.ProgressDialogShow(mostCurrent.activityBA,BA.ObjectToCharSequence("Cargando datos del municipio..."));
 if (true) break;

case 5:
//C
this.state = 6;
 //BA.debugLineNum = 48;BA.debugLine="ProgressDialogShow(\"Buscando puntos cercanos...\"";
anywheresoftware.b4a.keywords.Common.ProgressDialogShow(mostCurrent.activityBA,BA.ObjectToCharSequence("Buscando puntos cercanos..."));
 if (true) break;

case 6:
//C
this.state = 7;
;
 //BA.debugLineNum = 52;BA.debugLine="Wait For MapFragment1_Ready";
anywheresoftware.b4a.keywords.Common.WaitFor("mapfragment1_ready", processBA, this, null);
this.state = 17;
return;
case 17:
//C
this.state = 7;
;
 //BA.debugLineNum = 53;BA.debugLine="gmap = MapFragment1.GetMap";
parent.mostCurrent._gmap = parent.mostCurrent._mapfragment1.GetMap();
 //BA.debugLineNum = 54;BA.debugLine="gmap.MyLocationEnabled = True";
parent.mostCurrent._gmap.setMyLocationEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 56;BA.debugLine="If gmap.IsInitialized = False Then";
if (true) break;

case 7:
//if
this.state = 16;
if (parent.mostCurrent._gmap.IsInitialized()==anywheresoftware.b4a.keywords.Common.False) { 
this.state = 9;
}else {
this.state = 11;
}if (true) break;

case 9:
//C
this.state = 16;
 //BA.debugLineNum = 57;BA.debugLine="ToastMessageShow(\"Error initializing map.\", True";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Error initializing map."),anywheresoftware.b4a.keywords.Common.True);
 if (true) break;

case 11:
//C
this.state = 12;
 //BA.debugLineNum = 59;BA.debugLine="Do While Not(gmap.MyLocation.IsInitialized)";
if (true) break;

case 12:
//do while
this.state = 15;
while (anywheresoftware.b4a.keywords.Common.Not(parent.mostCurrent._gmap.getMyLocation().IsInitialized())) {
this.state = 14;
if (true) break;
}
if (true) break;

case 14:
//C
this.state = 12;
 //BA.debugLineNum = 60;BA.debugLine="Sleep(0)";
anywheresoftware.b4a.keywords.Common.Sleep(mostCurrent.activityBA,this,(int) (0));
this.state = 18;
return;
case 18:
//C
this.state = 12;
;
 if (true) break;

case 15:
//C
this.state = 16;
;
 //BA.debugLineNum = 62;BA.debugLine="CargarMapa";
_cargarmapa();
 if (true) break;

case 16:
//C
this.state = -1;
;
 //BA.debugLineNum = 65;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public static void  _mapfragment1_ready() throws Exception{
}
public static boolean  _activity_keypress(int _keycode) throws Exception{
 //BA.debugLineNum = 66;BA.debugLine="Sub Activity_KeyPress (KeyCode As Int) As Boolean";
 //BA.debugLineNum = 67;BA.debugLine="If KeyCode = KeyCodes.KEYCODE_BACK Then";
if (_keycode==anywheresoftware.b4a.keywords.Common.KeyCodes.KEYCODE_BACK) { 
 //BA.debugLineNum = 68;BA.debugLine="Activity.finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 69;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 };
 //BA.debugLineNum = 71;BA.debugLine="End Sub";
return false;
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 75;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 78;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 72;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 74;BA.debugLine="End Sub";
return "";
}
public static String  _btncerrarmapa_click() throws Exception{
 //BA.debugLineNum = 395;BA.debugLine="Private Sub btnCerrarMapa_Click";
 //BA.debugLineNum = 396;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 397;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 398;BA.debugLine="End Sub";
return "";
}
public static String  _cargarmapa() throws Exception{
String _act = "";
anywheresoftware.b4a.objects.collections.Map _nd = null;
anywheresoftware.b4a.objects.collections.List _latlist = null;
anywheresoftware.b4a.objects.collections.List _lnglist = null;
String _numresults = "";
int _i = 0;
anywheresoftware.b4a.objects.collections.Map _newpunto = null;
double _sitiolat = 0;
double _sitiolong = 0;
String _sitioindice = "";
String _sitiocontribucion = "";
uk.co.martinpearman.b4a.googlemapsextras.GoogleMapsExtras _googlemapsextras1 = null;
uk.co.martinpearman.b4a.com.google.android.gms.maps.CameraUpdateFactory _cameraupdatefactory1 = null;
uk.co.martinpearman.b4a.com.google.android.gms.maps.CameraUpdate _cameraupdate1 = null;
uk.co.martinpearman.b4a.com.google.android.gms.maps.model.LatLngBounds _llbounds = null;
anywheresoftware.b4a.objects.MapFragmentWrapper.LatLngWrapper _sw = null;
anywheresoftware.b4a.objects.MapFragmentWrapper.LatLngWrapper _ne = null;
anywheresoftware.b4a.objects.collections.List _listadatosanteriores = null;
int _m = 0;
anywheresoftware.b4a.objects.MapFragmentWrapper.LatLngWrapper _mylocation = null;
anywheresoftware.b4a.objects.MapFragmentWrapper.CameraPositionWrapper _cp = null;
 //BA.debugLineNum = 80;BA.debugLine="Sub CargarMapa";
 //BA.debugLineNum = 81;BA.debugLine="markerred.Initialize(File.DirAssets, \"marker_red.";
mostCurrent._markerred.Initialize(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"marker_red.png");
 //BA.debugLineNum = 82;BA.debugLine="markerorange.Initialize(File.DirAssets, \"marker_o";
mostCurrent._markerorange.Initialize(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"marker_orange.png");
 //BA.debugLineNum = 83;BA.debugLine="markeryellow.Initialize(File.DirAssets, \"marker_y";
mostCurrent._markeryellow.Initialize(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"marker_yellow.png");
 //BA.debugLineNum = 84;BA.debugLine="markergreen.Initialize(File.DirAssets, \"marker_gr";
mostCurrent._markergreen.Initialize(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"marker_green.png");
 //BA.debugLineNum = 85;BA.debugLine="markerblue.Initialize(File.DirAssets, \"marker_blu";
mostCurrent._markerblue.Initialize(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"marker_blue.png");
 //BA.debugLineNum = 86;BA.debugLine="markergray.Initialize(File.DirAssets, \"marker_gra";
mostCurrent._markergray.Initialize(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"marker_gray.png");
 //BA.debugLineNum = 89;BA.debugLine="gmap.MapType=gmap.MAP_TYPE_HYBRID";
mostCurrent._gmap.setMapType(mostCurrent._gmap.MAP_TYPE_HYBRID);
 //BA.debugLineNum = 92;BA.debugLine="If origen = \"municipio\" Then";
if ((_origen).equals("municipio")) { 
 //BA.debugLineNum = 93;BA.debugLine="Dim act As String";
_act = "";
 //BA.debugLineNum = 94;BA.debugLine="datosMunicipio_parser.Initialize(datosMunicipio_";
_datosmunicipio_parser.Initialize(_datosmunicipio_string);
 //BA.debugLineNum = 95;BA.debugLine="act = datosMunicipio_parser.NextValue";
_act = BA.ObjectToString(_datosmunicipio_parser.NextValue());
 //BA.debugLineNum = 96;BA.debugLine="If act = \"Partido OK\" Then";
if ((_act).equals("Partido OK")) { 
 //BA.debugLineNum = 97;BA.debugLine="Log(\"Cargando puntos del partido\")";
anywheresoftware.b4a.keywords.Common.LogImpl("447775761","Cargando puntos del partido",0);
 //BA.debugLineNum = 98;BA.debugLine="Dim nd As Map";
_nd = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 99;BA.debugLine="nd = datosMunicipio_parser.NextObject";
_nd = _datosmunicipio_parser.NextObject();
 //BA.debugLineNum = 101;BA.debugLine="Dim latlist As List";
_latlist = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 102;BA.debugLine="Dim lnglist As List";
_lnglist = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 103;BA.debugLine="latlist.Initialize";
_latlist.Initialize();
 //BA.debugLineNum = 104;BA.debugLine="lnglist.Initialize";
_lnglist.Initialize();
 //BA.debugLineNum = 105;BA.debugLine="If datosMunicipio_parser.NextValue = \"Con repor";
if ((_datosmunicipio_parser.NextValue()).equals((Object)("Con reportes"))) { 
 //BA.debugLineNum = 106;BA.debugLine="Log(\"Con reportes\")";
anywheresoftware.b4a.keywords.Common.LogImpl("447775770","Con reportes",0);
 //BA.debugLineNum = 107;BA.debugLine="Dim numresults As String";
_numresults = "";
 //BA.debugLineNum = 108;BA.debugLine="numresults = datosMunicipio_parser.NextValue";
_numresults = BA.ObjectToString(_datosmunicipio_parser.NextValue());
 //BA.debugLineNum = 109;BA.debugLine="For i = 0 To numresults - 1";
{
final int step24 = 1;
final int limit24 = (int) ((double)(Double.parseDouble(_numresults))-1);
_i = (int) (0) ;
for (;_i <= limit24 ;_i = _i + step24 ) {
 //BA.debugLineNum = 111;BA.debugLine="Dim newpunto As Map";
_newpunto = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 112;BA.debugLine="newpunto = datosMunicipio_parser.NextObject";
_newpunto = _datosmunicipio_parser.NextObject();
 //BA.debugLineNum = 114;BA.debugLine="Dim sitiolat As Double = newpunto.Get(\"lat\")";
_sitiolat = (double)(BA.ObjectToNumber(_newpunto.Get((Object)("lat"))));
 //BA.debugLineNum = 115;BA.debugLine="latlist.Add(newpunto.Get(\"lat\"))";
_latlist.Add(_newpunto.Get((Object)("lat")));
 //BA.debugLineNum = 116;BA.debugLine="Dim sitiolong As Double = newpunto.Get(\"lng\")";
_sitiolong = (double)(BA.ObjectToNumber(_newpunto.Get((Object)("lng"))));
 //BA.debugLineNum = 117;BA.debugLine="lnglist.Add(newpunto.Get(\"lng\"))";
_lnglist.Add(_newpunto.Get((Object)("lng")));
 //BA.debugLineNum = 118;BA.debugLine="Dim sitioindice As String = newpunto.Get(\"ind";
_sitioindice = BA.ObjectToString(_newpunto.Get((Object)("indice")));
 //BA.debugLineNum = 119;BA.debugLine="Dim sitiocontribucion As String = newpunto.Ge";
_sitiocontribucion = BA.ObjectToString(_newpunto.Get((Object)("username")));
 //BA.debugLineNum = 121;BA.debugLine="If sitiocontribucion.Contains(\"@\") Then";
if (_sitiocontribucion.contains("@")) { 
 //BA.debugLineNum = 122;BA.debugLine="sitiocontribucion = sitiocontribucion.SubStr";
_sitiocontribucion = _sitiocontribucion.substring((int) (0),_sitiocontribucion.indexOf("@"));
 };
 //BA.debugLineNum = 125;BA.debugLine="If sitiolat <> \"0\" And sitiolong <> \"0\" Then";
if (_sitiolat!=(double)(Double.parseDouble("0")) && _sitiolong!=(double)(Double.parseDouble("0"))) { 
 //BA.debugLineNum = 126;BA.debugLine="If sitioindice > 0 And sitioindice < 20 Then";
if ((double)(Double.parseDouble(_sitioindice))>0 && (double)(Double.parseDouble(_sitioindice))<20) { 
 //BA.debugLineNum = 127;BA.debugLine="gmap.AddMarker2(sitiolat,sitiolong,sitioind";
mostCurrent._gmap.AddMarker2(_sitiolat,_sitiolong,_sitioindice,mostCurrent._gmap.HUE_RED);
 }else if((double)(Double.parseDouble(_sitioindice))>20 && (double)(Double.parseDouble(_sitioindice))<40) { 
 //BA.debugLineNum = 129;BA.debugLine="gmap.AddMarker2(sitiolat,sitiolong,sitioind";
mostCurrent._gmap.AddMarker2(_sitiolat,_sitiolong,_sitioindice,mostCurrent._gmap.HUE_ORANGE);
 }else if((double)(Double.parseDouble(_sitioindice))>40 && (double)(Double.parseDouble(_sitioindice))<60) { 
 //BA.debugLineNum = 131;BA.debugLine="gmap.AddMarker2(sitiolat,sitiolong,sitioind";
mostCurrent._gmap.AddMarker2(_sitiolat,_sitiolong,_sitioindice,mostCurrent._gmap.HUE_YELLOW);
 }else if((double)(Double.parseDouble(_sitioindice))>60 && (double)(Double.parseDouble(_sitioindice))<80) { 
 //BA.debugLineNum = 133;BA.debugLine="gmap.AddMarker2(sitiolat,sitiolong,sitioind";
mostCurrent._gmap.AddMarker2(_sitiolat,_sitiolong,_sitioindice,mostCurrent._gmap.HUE_GREEN);
 }else if((double)(Double.parseDouble(_sitioindice))>80 && (double)(Double.parseDouble(_sitioindice))<100) { 
 //BA.debugLineNum = 135;BA.debugLine="gmap.AddMarker2(sitiolat,sitiolong,sitioind";
mostCurrent._gmap.AddMarker2(_sitiolat,_sitiolong,_sitioindice,mostCurrent._gmap.HUE_BLUE);
 }else {
 //BA.debugLineNum = 137;BA.debugLine="gmap.AddMarker2(sitiolat,sitiolong,sitioind";
mostCurrent._gmap.AddMarker2(_sitiolat,_sitiolong,_sitioindice,mostCurrent._gmap.HUE_AZURE);
 };
 }else {
 //BA.debugLineNum = 140;BA.debugLine="Log(\"Punto no agregado\")";
anywheresoftware.b4a.keywords.Common.LogImpl("447775804","Punto no agregado",0);
 };
 }
};
 }else {
 //BA.debugLineNum = 144;BA.debugLine="ToastMessageShow(\"No hay envíos de ese municip";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("No hay envíos de ese municipio todavia"),anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 148;BA.debugLine="Dim GoogleMapsExtras1 As GoogleMapsExtras";
_googlemapsextras1 = new uk.co.martinpearman.b4a.googlemapsextras.GoogleMapsExtras();
 //BA.debugLineNum = 149;BA.debugLine="Dim CameraUpdateFactory1 As CameraUpdateFactory";
_cameraupdatefactory1 = new uk.co.martinpearman.b4a.com.google.android.gms.maps.CameraUpdateFactory();
 //BA.debugLineNum = 150;BA.debugLine="Dim CameraUpdate1 As CameraUpdate";
_cameraupdate1 = new uk.co.martinpearman.b4a.com.google.android.gms.maps.CameraUpdate();
 //BA.debugLineNum = 151;BA.debugLine="Dim llbounds As LatLngBounds";
_llbounds = new uk.co.martinpearman.b4a.com.google.android.gms.maps.model.LatLngBounds();
 //BA.debugLineNum = 152;BA.debugLine="Dim sw, ne As LatLng 'your southwest and northe";
_sw = new anywheresoftware.b4a.objects.MapFragmentWrapper.LatLngWrapper();
_ne = new anywheresoftware.b4a.objects.MapFragmentWrapper.LatLngWrapper();
 //BA.debugLineNum = 153;BA.debugLine="latlist.Sort(False)";
_latlist.Sort(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 154;BA.debugLine="lnglist.Sort(False)";
_lnglist.Sort(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 155;BA.debugLine="sw.Initialize(latlist.Get(0), lnglist.Get(0))";
_sw.Initialize((double)(BA.ObjectToNumber(_latlist.Get((int) (0)))),(double)(BA.ObjectToNumber(_lnglist.Get((int) (0)))));
 //BA.debugLineNum = 156;BA.debugLine="latlist.Sort(True)";
_latlist.Sort(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 157;BA.debugLine="lnglist.Sort(True)";
_lnglist.Sort(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 158;BA.debugLine="ne.Initialize(latlist.Get(0), lnglist.Get(0))";
_ne.Initialize((double)(BA.ObjectToNumber(_latlist.Get((int) (0)))),(double)(BA.ObjectToNumber(_lnglist.Get((int) (0)))));
 //BA.debugLineNum = 159;BA.debugLine="Log(GoogleMapsExtras1.GetProjection(gmap).GetVi";
anywheresoftware.b4a.keywords.Common.LogImpl("447775823",BA.ObjectToString(_googlemapsextras1.GetProjection((com.google.android.gms.maps.GoogleMap)(mostCurrent._gmap.getObject())).GetVisibleRegion().getLatLngBounds()),0);
 //BA.debugLineNum = 160;BA.debugLine="llbounds.Initialize(sw, ne)";
_llbounds.Initialize((com.google.android.gms.maps.model.LatLng)(_sw.getObject()),(com.google.android.gms.maps.model.LatLng)(_ne.getObject()));
 //BA.debugLineNum = 161;BA.debugLine="CameraUpdate1=CameraUpdateFactory1.NewLatLngBou";
_cameraupdate1 = _cameraupdatefactory1.NewLatLngBounds((com.google.android.gms.maps.model.LatLngBounds)(_llbounds.getObject()),(int) (10));
 //BA.debugLineNum = 162;BA.debugLine="GoogleMapsExtras1.MoveCamera(gmap, CameraUpdate";
_googlemapsextras1.MoveCamera((com.google.android.gms.maps.GoogleMap)(mostCurrent._gmap.getObject()),(com.google.android.gms.maps.CameraUpdate)(_cameraupdate1.getObject()));
 }else {
 //BA.debugLineNum = 164;BA.debugLine="Log(\"Partido NO existente:\" & Main.strUserOrg)";
anywheresoftware.b4a.keywords.Common.LogImpl("447775828","Partido NO existente:"+mostCurrent._main._struserorg /*String*/ ,0);
 //BA.debugLineNum = 167;BA.debugLine="If datosMunicipio_parser.NextValue = \"Con repor";
if ((_datosmunicipio_parser.NextValue()).equals((Object)("Con reportes"))) { 
 //BA.debugLineNum = 169;BA.debugLine="Dim latlist As List";
_latlist = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 170;BA.debugLine="Dim lnglist As List";
_lnglist = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 171;BA.debugLine="latlist.Initialize";
_latlist.Initialize();
 //BA.debugLineNum = 172;BA.debugLine="lnglist.Initialize";
_lnglist.Initialize();
 //BA.debugLineNum = 173;BA.debugLine="Log(\"Con reportes\")";
anywheresoftware.b4a.keywords.Common.LogImpl("447775837","Con reportes",0);
 //BA.debugLineNum = 174;BA.debugLine="Dim numresults As String";
_numresults = "";
 //BA.debugLineNum = 175;BA.debugLine="numresults = datosMunicipio_parser.NextValue";
_numresults = BA.ObjectToString(_datosmunicipio_parser.NextValue());
 //BA.debugLineNum = 176;BA.debugLine="For i = 0 To numresults - 1";
{
final int step82 = 1;
final int limit82 = (int) ((double)(Double.parseDouble(_numresults))-1);
_i = (int) (0) ;
for (;_i <= limit82 ;_i = _i + step82 ) {
 //BA.debugLineNum = 178;BA.debugLine="Dim newpunto As Map";
_newpunto = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 179;BA.debugLine="newpunto = datosMunicipio_parser.NextObject";
_newpunto = _datosmunicipio_parser.NextObject();
 //BA.debugLineNum = 181;BA.debugLine="Dim sitiolat As Double = newpunto.Get(\"lat\")";
_sitiolat = (double)(BA.ObjectToNumber(_newpunto.Get((Object)("lat"))));
 //BA.debugLineNum = 182;BA.debugLine="latlist.Add(newpunto.Get(\"lat\"))";
_latlist.Add(_newpunto.Get((Object)("lat")));
 //BA.debugLineNum = 183;BA.debugLine="Dim sitiolong As Double = newpunto.Get(\"lng\")";
_sitiolong = (double)(BA.ObjectToNumber(_newpunto.Get((Object)("lng"))));
 //BA.debugLineNum = 184;BA.debugLine="lnglist.Add(newpunto.Get(\"lng\"))";
_lnglist.Add(_newpunto.Get((Object)("lng")));
 //BA.debugLineNum = 185;BA.debugLine="Dim sitioindice As String = newpunto.Get(\"ind";
_sitioindice = BA.ObjectToString(_newpunto.Get((Object)("indice")));
 //BA.debugLineNum = 186;BA.debugLine="Dim sitiocontribucion As String = newpunto.Ge";
_sitiocontribucion = BA.ObjectToString(_newpunto.Get((Object)("username")));
 //BA.debugLineNum = 188;BA.debugLine="If sitiocontribucion.Contains(\"@\") Then";
if (_sitiocontribucion.contains("@")) { 
 //BA.debugLineNum = 189;BA.debugLine="sitiocontribucion = sitiocontribucion.SubStr";
_sitiocontribucion = _sitiocontribucion.substring((int) (0),_sitiocontribucion.indexOf("@"));
 };
 //BA.debugLineNum = 192;BA.debugLine="If sitiolat <> \"0\" And sitiolong <> \"0\" Then";
if (_sitiolat!=(double)(Double.parseDouble("0")) && _sitiolong!=(double)(Double.parseDouble("0"))) { 
 //BA.debugLineNum = 193;BA.debugLine="If sitioindice > 0 And sitioindice < 20 Then";
if ((double)(Double.parseDouble(_sitioindice))>0 && (double)(Double.parseDouble(_sitioindice))<20) { 
 //BA.debugLineNum = 194;BA.debugLine="gmap.AddMarker2(sitiolat,sitiolong,sitioind";
mostCurrent._gmap.AddMarker2(_sitiolat,_sitiolong,_sitioindice,mostCurrent._gmap.HUE_RED);
 }else if((double)(Double.parseDouble(_sitioindice))>20 && (double)(Double.parseDouble(_sitioindice))<40) { 
 //BA.debugLineNum = 196;BA.debugLine="gmap.AddMarker2(sitiolat,sitiolong,sitioind";
mostCurrent._gmap.AddMarker2(_sitiolat,_sitiolong,_sitioindice,mostCurrent._gmap.HUE_ORANGE);
 }else if((double)(Double.parseDouble(_sitioindice))>40 && (double)(Double.parseDouble(_sitioindice))<60) { 
 //BA.debugLineNum = 198;BA.debugLine="gmap.AddMarker2(sitiolat,sitiolong,sitioind";
mostCurrent._gmap.AddMarker2(_sitiolat,_sitiolong,_sitioindice,mostCurrent._gmap.HUE_YELLOW);
 }else if((double)(Double.parseDouble(_sitioindice))>60 && (double)(Double.parseDouble(_sitioindice))<80) { 
 //BA.debugLineNum = 200;BA.debugLine="gmap.AddMarker2(sitiolat,sitiolong,sitioind";
mostCurrent._gmap.AddMarker2(_sitiolat,_sitiolong,_sitioindice,mostCurrent._gmap.HUE_GREEN);
 }else if((double)(Double.parseDouble(_sitioindice))>80 && (double)(Double.parseDouble(_sitioindice))<100) { 
 //BA.debugLineNum = 202;BA.debugLine="gmap.AddMarker2(sitiolat,sitiolong,sitioind";
mostCurrent._gmap.AddMarker2(_sitiolat,_sitiolong,_sitioindice,mostCurrent._gmap.HUE_BLUE);
 }else {
 //BA.debugLineNum = 204;BA.debugLine="gmap.AddMarker2(sitiolat,sitiolong,sitioind";
mostCurrent._gmap.AddMarker2(_sitiolat,_sitiolong,_sitioindice,mostCurrent._gmap.HUE_AZURE);
 };
 }else {
 //BA.debugLineNum = 207;BA.debugLine="Log(\"Punto no agregado\")";
anywheresoftware.b4a.keywords.Common.LogImpl("447775871","Punto no agregado",0);
 };
 }
};
 //BA.debugLineNum = 211;BA.debugLine="Dim GoogleMapsExtras1 As GoogleMapsExtras";
_googlemapsextras1 = new uk.co.martinpearman.b4a.googlemapsextras.GoogleMapsExtras();
 //BA.debugLineNum = 212;BA.debugLine="Dim CameraUpdateFactory1 As CameraUpdateFactor";
_cameraupdatefactory1 = new uk.co.martinpearman.b4a.com.google.android.gms.maps.CameraUpdateFactory();
 //BA.debugLineNum = 213;BA.debugLine="Dim CameraUpdate1 As CameraUpdate";
_cameraupdate1 = new uk.co.martinpearman.b4a.com.google.android.gms.maps.CameraUpdate();
 //BA.debugLineNum = 214;BA.debugLine="Dim llbounds As LatLngBounds";
_llbounds = new uk.co.martinpearman.b4a.com.google.android.gms.maps.model.LatLngBounds();
 //BA.debugLineNum = 215;BA.debugLine="Dim sw, ne As LatLng 'your southwest and north";
_sw = new anywheresoftware.b4a.objects.MapFragmentWrapper.LatLngWrapper();
_ne = new anywheresoftware.b4a.objects.MapFragmentWrapper.LatLngWrapper();
 //BA.debugLineNum = 216;BA.debugLine="latlist.Sort(False)";
_latlist.Sort(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 217;BA.debugLine="lnglist.Sort(False)";
_lnglist.Sort(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 218;BA.debugLine="sw.Initialize(latlist.Get(0), lnglist.Get(0))";
_sw.Initialize((double)(BA.ObjectToNumber(_latlist.Get((int) (0)))),(double)(BA.ObjectToNumber(_lnglist.Get((int) (0)))));
 //BA.debugLineNum = 219;BA.debugLine="latlist.Sort(True)";
_latlist.Sort(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 220;BA.debugLine="lnglist.Sort(True)";
_lnglist.Sort(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 221;BA.debugLine="ne.Initialize(latlist.Get(0), lnglist.Get(0))";
_ne.Initialize((double)(BA.ObjectToNumber(_latlist.Get((int) (0)))),(double)(BA.ObjectToNumber(_lnglist.Get((int) (0)))));
 //BA.debugLineNum = 222;BA.debugLine="Log(GoogleMapsExtras1.GetProjection(gmap).GetV";
anywheresoftware.b4a.keywords.Common.LogImpl("447775886",BA.ObjectToString(_googlemapsextras1.GetProjection((com.google.android.gms.maps.GoogleMap)(mostCurrent._gmap.getObject())).GetVisibleRegion().getLatLngBounds()),0);
 //BA.debugLineNum = 223;BA.debugLine="llbounds.Initialize(sw, ne)";
_llbounds.Initialize((com.google.android.gms.maps.model.LatLng)(_sw.getObject()),(com.google.android.gms.maps.model.LatLng)(_ne.getObject()));
 //BA.debugLineNum = 224;BA.debugLine="CameraUpdate1=CameraUpdateFactory1.NewLatLngBo";
_cameraupdate1 = _cameraupdatefactory1.NewLatLngBounds((com.google.android.gms.maps.model.LatLngBounds)(_llbounds.getObject()),(int) (10));
 //BA.debugLineNum = 225;BA.debugLine="GoogleMapsExtras1.MoveCamera(gmap, CameraUpdat";
_googlemapsextras1.MoveCamera((com.google.android.gms.maps.GoogleMap)(mostCurrent._gmap.getObject()),(com.google.android.gms.maps.CameraUpdate)(_cameraupdate1.getObject()));
 }else {
 //BA.debugLineNum = 228;BA.debugLine="Log(\"No tiene reportes\")";
anywheresoftware.b4a.keywords.Common.LogImpl("447775892","No tiene reportes",0);
 //BA.debugLineNum = 229;BA.debugLine="Activity.Finish()";
mostCurrent._activity.Finish();
 };
 };
 //BA.debugLineNum = 233;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 }else if((_origen).equals("datos_anteriores")) { 
 //BA.debugLineNum = 236;BA.debugLine="Dim listaDatosAnteriores As List";
_listadatosanteriores = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 237;BA.debugLine="listaDatosAnteriores.Initialize";
_listadatosanteriores.Initialize();
 //BA.debugLineNum = 238;BA.debugLine="listaDatosAnteriores = Main.user_markers_list";
_listadatosanteriores = mostCurrent._main._user_markers_list /*anywheresoftware.b4a.objects.collections.List*/ ;
 //BA.debugLineNum = 240;BA.debugLine="Dim latlist As List";
_latlist = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 241;BA.debugLine="Dim lnglist As List";
_lnglist = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 242;BA.debugLine="latlist.Initialize";
_latlist.Initialize();
 //BA.debugLineNum = 243;BA.debugLine="lnglist.Initialize";
_lnglist.Initialize();
 //BA.debugLineNum = 244;BA.debugLine="For m = 0 To listaDatosAnteriores.Size - 1";
{
final int step141 = 1;
final int limit141 = (int) (_listadatosanteriores.getSize()-1);
_m = (int) (0) ;
for (;_m <= limit141 ;_m = _m + step141 ) {
 //BA.debugLineNum = 246;BA.debugLine="Dim newpunto As Map";
_newpunto = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 247;BA.debugLine="newpunto = listaDatosAnteriores.Get(m)";
_newpunto = (anywheresoftware.b4a.objects.collections.Map) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.collections.Map(), (java.util.Map)(_listadatosanteriores.Get(_m)));
 //BA.debugLineNum = 250;BA.debugLine="Dim sitiolat As Double = newpunto.Get(\"lat\")";
_sitiolat = (double)(BA.ObjectToNumber(_newpunto.Get((Object)("lat"))));
 //BA.debugLineNum = 251;BA.debugLine="latlist.Add(newpunto.Get(\"lat\"))";
_latlist.Add(_newpunto.Get((Object)("lat")));
 //BA.debugLineNum = 252;BA.debugLine="Dim sitiolong As Double = newpunto.Get(\"lng\")";
_sitiolong = (double)(BA.ObjectToNumber(_newpunto.Get((Object)("lng"))));
 //BA.debugLineNum = 253;BA.debugLine="lnglist.Add(newpunto.Get(\"lng\"))";
_lnglist.Add(_newpunto.Get((Object)("lng")));
 //BA.debugLineNum = 254;BA.debugLine="Dim sitioindice As String = newpunto.Get(\"indic";
_sitioindice = BA.ObjectToString(_newpunto.Get((Object)("indice")));
 //BA.debugLineNum = 256;BA.debugLine="If sitiolat <> \"0\" And sitiolong <> \"0\" Then";
if (_sitiolat!=(double)(Double.parseDouble("0")) && _sitiolong!=(double)(Double.parseDouble("0"))) { 
 //BA.debugLineNum = 257;BA.debugLine="If sitioindice > 0 And sitioindice < 20 Then";
if ((double)(Double.parseDouble(_sitioindice))>0 && (double)(Double.parseDouble(_sitioindice))<20) { 
 //BA.debugLineNum = 258;BA.debugLine="gmap.AddMarker2(sitiolat,sitiolong,sitioindic";
mostCurrent._gmap.AddMarker2(_sitiolat,_sitiolong,_sitioindice,mostCurrent._gmap.HUE_RED);
 }else if((double)(Double.parseDouble(_sitioindice))>20 && (double)(Double.parseDouble(_sitioindice))<40) { 
 //BA.debugLineNum = 260;BA.debugLine="gmap.AddMarker2(sitiolat,sitiolong,sitioindic";
mostCurrent._gmap.AddMarker2(_sitiolat,_sitiolong,_sitioindice,mostCurrent._gmap.HUE_ORANGE);
 }else if((double)(Double.parseDouble(_sitioindice))>40 && (double)(Double.parseDouble(_sitioindice))<60) { 
 //BA.debugLineNum = 262;BA.debugLine="gmap.AddMarker2(sitiolat,sitiolong,sitioindic";
mostCurrent._gmap.AddMarker2(_sitiolat,_sitiolong,_sitioindice,mostCurrent._gmap.HUE_YELLOW);
 }else if((double)(Double.parseDouble(_sitioindice))>60 && (double)(Double.parseDouble(_sitioindice))<80) { 
 //BA.debugLineNum = 264;BA.debugLine="gmap.AddMarker2(sitiolat,sitiolong,sitioindic";
mostCurrent._gmap.AddMarker2(_sitiolat,_sitiolong,_sitioindice,mostCurrent._gmap.HUE_GREEN);
 }else if((double)(Double.parseDouble(_sitioindice))>80 && (double)(Double.parseDouble(_sitioindice))<100) { 
 //BA.debugLineNum = 266;BA.debugLine="gmap.AddMarker2(sitiolat,sitiolong,sitioindic";
mostCurrent._gmap.AddMarker2(_sitiolat,_sitiolong,_sitioindice,mostCurrent._gmap.HUE_BLUE);
 }else {
 //BA.debugLineNum = 268;BA.debugLine="gmap.AddMarker2(sitiolat,sitiolong,sitioindic";
mostCurrent._gmap.AddMarker2(_sitiolat,_sitiolong,_sitioindice,mostCurrent._gmap.HUE_AZURE);
 };
 }else {
 //BA.debugLineNum = 271;BA.debugLine="Log(\"Punto no agregado\")";
anywheresoftware.b4a.keywords.Common.LogImpl("447775935","Punto no agregado",0);
 };
 }
};
 //BA.debugLineNum = 275;BA.debugLine="Dim GoogleMapsExtras1 As GoogleMapsExtras";
_googlemapsextras1 = new uk.co.martinpearman.b4a.googlemapsextras.GoogleMapsExtras();
 //BA.debugLineNum = 276;BA.debugLine="Dim CameraUpdateFactory1 As CameraUpdateFactory";
_cameraupdatefactory1 = new uk.co.martinpearman.b4a.com.google.android.gms.maps.CameraUpdateFactory();
 //BA.debugLineNum = 277;BA.debugLine="Dim CameraUpdate1 As CameraUpdate";
_cameraupdate1 = new uk.co.martinpearman.b4a.com.google.android.gms.maps.CameraUpdate();
 //BA.debugLineNum = 278;BA.debugLine="Dim llbounds As LatLngBounds";
_llbounds = new uk.co.martinpearman.b4a.com.google.android.gms.maps.model.LatLngBounds();
 //BA.debugLineNum = 279;BA.debugLine="Dim sw, ne As LatLng 'your southwest and northea";
_sw = new anywheresoftware.b4a.objects.MapFragmentWrapper.LatLngWrapper();
_ne = new anywheresoftware.b4a.objects.MapFragmentWrapper.LatLngWrapper();
 //BA.debugLineNum = 280;BA.debugLine="latlist.Sort(False)";
_latlist.Sort(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 281;BA.debugLine="lnglist.Sort(False)";
_lnglist.Sort(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 282;BA.debugLine="sw.Initialize(latlist.Get(0), lnglist.Get(0))";
_sw.Initialize((double)(BA.ObjectToNumber(_latlist.Get((int) (0)))),(double)(BA.ObjectToNumber(_lnglist.Get((int) (0)))));
 //BA.debugLineNum = 283;BA.debugLine="latlist.Sort(True)";
_latlist.Sort(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 284;BA.debugLine="lnglist.Sort(True)";
_lnglist.Sort(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 285;BA.debugLine="ne.Initialize(latlist.Get(0), lnglist.Get(0))";
_ne.Initialize((double)(BA.ObjectToNumber(_latlist.Get((int) (0)))),(double)(BA.ObjectToNumber(_lnglist.Get((int) (0)))));
 //BA.debugLineNum = 286;BA.debugLine="Log(GoogleMapsExtras1.GetProjection(gmap).GetVis";
anywheresoftware.b4a.keywords.Common.LogImpl("447775950",BA.ObjectToString(_googlemapsextras1.GetProjection((com.google.android.gms.maps.GoogleMap)(mostCurrent._gmap.getObject())).GetVisibleRegion().getLatLngBounds()),0);
 //BA.debugLineNum = 287;BA.debugLine="llbounds.Initialize(sw, ne)";
_llbounds.Initialize((com.google.android.gms.maps.model.LatLng)(_sw.getObject()),(com.google.android.gms.maps.model.LatLng)(_ne.getObject()));
 //BA.debugLineNum = 288;BA.debugLine="CameraUpdate1=CameraUpdateFactory1.NewLatLngBoun";
_cameraupdate1 = _cameraupdatefactory1.NewLatLngBounds((com.google.android.gms.maps.model.LatLngBounds)(_llbounds.getObject()),(int) (10));
 //BA.debugLineNum = 289;BA.debugLine="GoogleMapsExtras1.MoveCamera(gmap, CameraUpdate1";
_googlemapsextras1.MoveCamera((com.google.android.gms.maps.GoogleMap)(mostCurrent._gmap.getObject()),(com.google.android.gms.maps.CameraUpdate)(_cameraupdate1.getObject()));
 //BA.debugLineNum = 290;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 }else {
 //BA.debugLineNum = 292;BA.debugLine="Dim myLocation As LatLng";
_mylocation = new anywheresoftware.b4a.objects.MapFragmentWrapper.LatLngWrapper();
 //BA.debugLineNum = 293;BA.debugLine="myLocation = gmap.MyLocation";
_mylocation = mostCurrent._gmap.getMyLocation();
 //BA.debugLineNum = 295;BA.debugLine="If myLocation.IsInitialized = False Then";
if (_mylocation.IsInitialized()==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 296;BA.debugLine="myLocation.Initialize(\"-34.9204950\",\"-57.953566";
_mylocation.Initialize((double)(Double.parseDouble("-34.9204950")),(double)(Double.parseDouble("-57.9535660")));
 };
 //BA.debugLineNum = 298;BA.debugLine="Dim cp As CameraPosition";
_cp = new anywheresoftware.b4a.objects.MapFragmentWrapper.CameraPositionWrapper();
 //BA.debugLineNum = 299;BA.debugLine="cp.Initialize(myLocation.Latitude, myLocation.Lo";
_cp.Initialize(_mylocation.getLatitude(),_mylocation.getLongitude(),(float) (16));
 //BA.debugLineNum = 300;BA.debugLine="gmap.AnimateCamera(cp)";
mostCurrent._gmap.AnimateCamera((com.google.android.gms.maps.model.CameraPosition)(_cp.getObject()));
 //BA.debugLineNum = 301;BA.debugLine="gmap.MapType=gmap.MAP_TYPE_HYBRID";
mostCurrent._gmap.setMapType(mostCurrent._gmap.MAP_TYPE_HYBRID);
 //BA.debugLineNum = 302;BA.debugLine="GetMiMapa";
_getmimapa();
 };
 //BA.debugLineNum = 305;BA.debugLine="End Sub";
return "";
}
public static String  _getmimapa() throws Exception{
appear.pnud.preservamos.downloadservice._downloaddata _dd = null;
 //BA.debugLineNum = 311;BA.debugLine="Sub GetMiMapa";
 //BA.debugLineNum = 315;BA.debugLine="Dim dd As DownloadData";
_dd = new appear.pnud.preservamos.downloadservice._downloaddata();
 //BA.debugLineNum = 316;BA.debugLine="dd.url = \"https://preservamos.ar/connect_app/geta";
_dd.url /*String*/  = "https://preservamos.ar/connect_app/getallmapa.php";
 //BA.debugLineNum = 317;BA.debugLine="dd.EventName = \"GetMiMapa\"";
_dd.EventName /*String*/  = "GetMiMapa";
 //BA.debugLineNum = 318;BA.debugLine="dd.Target = Me";
_dd.Target /*Object*/  = frmmapa.getObject();
 //BA.debugLineNum = 319;BA.debugLine="CallSubDelayed2(DownloadService, \"StartDownload\",";
anywheresoftware.b4a.keywords.Common.CallSubDelayed2(processBA,(Object)(mostCurrent._downloadservice.getObject()),"StartDownload",(Object)(_dd));
 //BA.debugLineNum = 321;BA.debugLine="End Sub";
return "";
}
public static String  _getmimapa_complete(appear.pnud.preservamos.httpjob _job) throws Exception{
String _ret = "";
String _act = "";
anywheresoftware.b4a.objects.collections.JSONParser _parser = null;
String _numresults = "";
int _i = 0;
anywheresoftware.b4a.objects.collections.Map _newpunto = null;
double _sitiolat = 0;
double _sitiolong = 0;
String _sitioindice = "";
String _sitiotiporio = "";
String _sitiocontribucion = "";
 //BA.debugLineNum = 322;BA.debugLine="Sub GetMiMapa_Complete(Job As HttpJob)";
 //BA.debugLineNum = 323;BA.debugLine="Log(\"GetMapa messages: \" & Job.Success)";
anywheresoftware.b4a.keywords.Common.LogImpl("447906817","GetMapa messages: "+BA.ObjectToString(_job._success /*boolean*/ ),0);
 //BA.debugLineNum = 324;BA.debugLine="If Job.Success = True Then";
if (_job._success /*boolean*/ ==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 326;BA.debugLine="Dim ret As String";
_ret = "";
 //BA.debugLineNum = 327;BA.debugLine="Dim act As String";
_act = "";
 //BA.debugLineNum = 328;BA.debugLine="ret = Job.GetString";
_ret = _job._getstring /*String*/ ();
 //BA.debugLineNum = 329;BA.debugLine="Dim parser As JSONParser";
_parser = new anywheresoftware.b4a.objects.collections.JSONParser();
 //BA.debugLineNum = 330;BA.debugLine="parser.Initialize(ret)";
_parser.Initialize(_ret);
 //BA.debugLineNum = 331;BA.debugLine="act = parser.NextValue";
_act = BA.ObjectToString(_parser.NextValue());
 //BA.debugLineNum = 332;BA.debugLine="If act = \"Not Found\" Then";
if ((_act).equals("Not Found")) { 
 //BA.debugLineNum = 333;BA.debugLine="ToastMessageShow(\"No encuentro tus sitios anter";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("No encuentro tus sitios anteriores, prueba luego"),anywheresoftware.b4a.keywords.Common.True);
 }else if((_act).equals("Error")) { 
 //BA.debugLineNum = 335;BA.debugLine="ToastMessageShow(\"No encuentro tus sitios anter";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("No encuentro tus sitios anteriores, prueba luego"),anywheresoftware.b4a.keywords.Common.True);
 }else if((_act).equals("GetMapaOk")) { 
 //BA.debugLineNum = 338;BA.debugLine="Dim numresults As String";
_numresults = "";
 //BA.debugLineNum = 339;BA.debugLine="numresults = parser.NextValue";
_numresults = BA.ObjectToString(_parser.NextValue());
 //BA.debugLineNum = 341;BA.debugLine="For i = 0 To numresults - 1";
{
final int step16 = 1;
final int limit16 = (int) ((double)(Double.parseDouble(_numresults))-1);
_i = (int) (0) ;
for (;_i <= limit16 ;_i = _i + step16 ) {
 //BA.debugLineNum = 343;BA.debugLine="Dim newpunto As Map";
_newpunto = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 344;BA.debugLine="newpunto = parser.NextObject";
_newpunto = _parser.NextObject();
 //BA.debugLineNum = 346;BA.debugLine="Dim sitiolat As Double = newpunto.Get(\"lat\")";
_sitiolat = (double)(BA.ObjectToNumber(_newpunto.Get((Object)("lat"))));
 //BA.debugLineNum = 347;BA.debugLine="Dim sitiolong As Double = newpunto.Get(\"lng\")";
_sitiolong = (double)(BA.ObjectToNumber(_newpunto.Get((Object)("lng"))));
 //BA.debugLineNum = 348;BA.debugLine="Dim sitioindice As String = newpunto.Get(\"indi";
_sitioindice = BA.ObjectToString(_newpunto.Get((Object)("indice")));
 //BA.debugLineNum = 349;BA.debugLine="Dim sitiotiporio As String = newpunto.Get(\"tip";
_sitiotiporio = BA.ObjectToString(_newpunto.Get((Object)("tiporio")));
 //BA.debugLineNum = 350;BA.debugLine="Dim sitiocontribucion As String = newpunto.Get";
_sitiocontribucion = BA.ObjectToString(_newpunto.Get((Object)("username")));
 //BA.debugLineNum = 352;BA.debugLine="If sitiocontribucion.Contains(\"@\") Then";
if (_sitiocontribucion.contains("@")) { 
 //BA.debugLineNum = 353;BA.debugLine="sitiocontribucion = sitiocontribucion.SubStri";
_sitiocontribucion = _sitiocontribucion.substring((int) (0),_sitiocontribucion.indexOf("@"));
 };
 //BA.debugLineNum = 357;BA.debugLine="If sitiolat <> \"0\" And sitiolong <> \"0\" Then";
if (_sitiolat!=(double)(Double.parseDouble("0")) && _sitiolong!=(double)(Double.parseDouble("0"))) { 
 //BA.debugLineNum = 358;BA.debugLine="If sitioindice > 0 And sitioindice < 20 Then";
if ((double)(Double.parseDouble(_sitioindice))>0 && (double)(Double.parseDouble(_sitioindice))<20) { 
 //BA.debugLineNum = 360;BA.debugLine="gmap.AddMarker2(sitiolat,sitiolong,sitioindi";
mostCurrent._gmap.AddMarker2(_sitiolat,_sitiolong,_sitioindice,mostCurrent._gmap.HUE_RED);
 }else if((double)(Double.parseDouble(_sitioindice))>20 && (double)(Double.parseDouble(_sitioindice))<40) { 
 //BA.debugLineNum = 363;BA.debugLine="gmap.AddMarker2(sitiolat,sitiolong,sitioindi";
mostCurrent._gmap.AddMarker2(_sitiolat,_sitiolong,_sitioindice,mostCurrent._gmap.HUE_ORANGE);
 }else if((double)(Double.parseDouble(_sitioindice))>40 && (double)(Double.parseDouble(_sitioindice))<60) { 
 //BA.debugLineNum = 366;BA.debugLine="gmap.AddMarker2(sitiolat,sitiolong,sitioindi";
mostCurrent._gmap.AddMarker2(_sitiolat,_sitiolong,_sitioindice,mostCurrent._gmap.HUE_YELLOW);
 }else if((double)(Double.parseDouble(_sitioindice))>60 && (double)(Double.parseDouble(_sitioindice))<80) { 
 //BA.debugLineNum = 369;BA.debugLine="gmap.AddMarker2(sitiolat,sitiolong,sitioindi";
mostCurrent._gmap.AddMarker2(_sitiolat,_sitiolong,_sitioindice,mostCurrent._gmap.HUE_GREEN);
 }else if((double)(Double.parseDouble(_sitioindice))>80 && (double)(Double.parseDouble(_sitioindice))<100) { 
 //BA.debugLineNum = 372;BA.debugLine="gmap.AddMarker2(sitiolat,sitiolong,sitioindi";
mostCurrent._gmap.AddMarker2(_sitiolat,_sitiolong,_sitioindice,mostCurrent._gmap.HUE_BLUE);
 }else {
 //BA.debugLineNum = 375;BA.debugLine="gmap.AddMarker2(sitiolat,sitiolong,sitioindi";
mostCurrent._gmap.AddMarker2(_sitiolat,_sitiolong,_sitioindice,mostCurrent._gmap.HUE_AZURE);
 };
 }else {
 //BA.debugLineNum = 378;BA.debugLine="Log(\"Punto no agregado\")";
anywheresoftware.b4a.keywords.Common.LogImpl("447906872","Punto no agregado",0);
 };
 }
};
 };
 }else {
 //BA.debugLineNum = 384;BA.debugLine="Log(\"GetMapa not ok\")";
anywheresoftware.b4a.keywords.Common.LogImpl("447906878","GetMapa not ok",0);
 //BA.debugLineNum = 385;BA.debugLine="ToastMessageShow(\"Necesitas tener conexión a int";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Necesitas tener conexión a internet para ver el mapa, ¡intentalo luego!"),BA.ObjectToBoolean("No conectado"));
 };
 //BA.debugLineNum = 387;BA.debugLine="Job.Release";
_job._release /*String*/ ();
 //BA.debugLineNum = 388;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 //BA.debugLineNum = 391;BA.debugLine="End Sub";
return "";
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 16;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 18;BA.debugLine="Private lblLat As Label";
mostCurrent._lbllat = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 19;BA.debugLine="Private lblLon As Label";
mostCurrent._lbllon = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 20;BA.debugLine="Private btnContinuarLocalizacion As Button";
mostCurrent._btncontinuarlocalizacion = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 23;BA.debugLine="Private gmap As GoogleMap";
mostCurrent._gmap = new anywheresoftware.b4a.objects.MapFragmentWrapper.GoogleMapWrapper();
 //BA.debugLineNum = 25;BA.debugLine="Private MapFragment1 As MapFragment";
mostCurrent._mapfragment1 = new anywheresoftware.b4a.objects.MapFragmentWrapper();
 //BA.debugLineNum = 26;BA.debugLine="Dim myMarker As Marker";
mostCurrent._mymarker = new anywheresoftware.b4a.objects.MapFragmentWrapper.MarkerWrapper();
 //BA.debugLineNum = 29;BA.debugLine="Dim rp As RuntimePermissions";
mostCurrent._rp = new anywheresoftware.b4a.objects.RuntimePermissions();
 //BA.debugLineNum = 30;BA.debugLine="Dim p As Phone";
mostCurrent._p = new anywheresoftware.b4a.phone.Phone();
 //BA.debugLineNum = 34;BA.debugLine="Private markerred As Bitmap";
mostCurrent._markerred = new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper();
 //BA.debugLineNum = 35;BA.debugLine="Private markerorange As Bitmap";
mostCurrent._markerorange = new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper();
 //BA.debugLineNum = 36;BA.debugLine="Private markeryellow As Bitmap";
mostCurrent._markeryellow = new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper();
 //BA.debugLineNum = 37;BA.debugLine="Private markergreen As Bitmap";
mostCurrent._markergreen = new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper();
 //BA.debugLineNum = 38;BA.debugLine="Private markerblue As Bitmap";
mostCurrent._markerblue = new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper();
 //BA.debugLineNum = 39;BA.debugLine="Private markergray As Bitmap";
mostCurrent._markergray = new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper();
 //BA.debugLineNum = 42;BA.debugLine="End Sub";
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
 //BA.debugLineNum = 13;BA.debugLine="Dim datosMunicipio_string As String";
_datosmunicipio_string = "";
 //BA.debugLineNum = 14;BA.debugLine="Dim datosMunicipio_parser As JSONParser";
_datosmunicipio_parser = new anywheresoftware.b4a.objects.collections.JSONParser();
 //BA.debugLineNum = 15;BA.debugLine="End Sub";
return "";
}
}
