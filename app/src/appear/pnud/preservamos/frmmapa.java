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
public appear.pnud.preservamos.form_reporte _form_reporte = null;
public appear.pnud.preservamos.frmlocalizacion _frmlocalizacion = null;
public appear.pnud.preservamos.reporte_habitat_rio _reporte_habitat_rio = null;
public appear.pnud.preservamos.utilidades _utilidades = null;
public appear.pnud.preservamos.frmdatossinenviar _frmdatossinenviar = null;
public appear.pnud.preservamos.reporte_envio _reporte_envio = null;
public appear.pnud.preservamos.alerta_fotos _alerta_fotos = null;
public appear.pnud.preservamos.alertas _alertas = null;
public appear.pnud.preservamos.aprender_muestreo _aprender_muestreo = null;
public appear.pnud.preservamos.dbutils _dbutils = null;
public appear.pnud.preservamos.downloadservice _downloadservice = null;
public appear.pnud.preservamos.firebasemessaging _firebasemessaging = null;
public appear.pnud.preservamos.frmabout _frmabout = null;
public appear.pnud.preservamos.frmdatosanteriores _frmdatosanteriores = null;
public appear.pnud.preservamos.frmeditprofile _frmeditprofile = null;
public appear.pnud.preservamos.frmfelicitaciones _frmfelicitaciones = null;
public appear.pnud.preservamos.frmmunicipioestadisticas _frmmunicipioestadisticas = null;
public appear.pnud.preservamos.frmpoliticadatos _frmpoliticadatos = null;
public appear.pnud.preservamos.frmtiporeporte _frmtiporeporte = null;
public appear.pnud.preservamos.imagedownloader _imagedownloader = null;
public appear.pnud.preservamos.inatcheck _inatcheck = null;
public appear.pnud.preservamos.mod_hidro _mod_hidro = null;
public appear.pnud.preservamos.mod_hidro_fotos _mod_hidro_fotos = null;
public appear.pnud.preservamos.mod_residuos _mod_residuos = null;
public appear.pnud.preservamos.mod_residuos_fotos _mod_residuos_fotos = null;
public appear.pnud.preservamos.reporte_fotos _reporte_fotos = null;
public appear.pnud.preservamos.reporte_habitat_laguna _reporte_habitat_laguna = null;
public appear.pnud.preservamos.reporte_habitat_rio_sierras _reporte_habitat_rio_sierras = null;
public appear.pnud.preservamos.reporte_habitat_rio_sierras_bu _reporte_habitat_rio_sierras_bu = null;
public appear.pnud.preservamos.starter _starter = null;
public appear.pnud.preservamos.uploadfiles _uploadfiles = null;
public appear.pnud.preservamos.character_creation _character_creation = null;
public appear.pnud.preservamos.register _register = null;
public appear.pnud.preservamos.xuiviewsutils _xuiviewsutils = null;
public appear.pnud.preservamos.httputils2service _httputils2service = null;

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
this.state = 22;
return;
case 22:
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
this.state = 21;
if (parent.mostCurrent._gmap.IsInitialized()==anywheresoftware.b4a.keywords.Common.False) { 
this.state = 9;
}else {
this.state = 11;
}if (true) break;

case 9:
//C
this.state = 21;
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
this.state = 23;
return;
case 23:
//C
this.state = 12;
;
 if (true) break;
;
 //BA.debugLineNum = 62;BA.debugLine="If Main.hayinternet = True Then";

case 15:
//if
this.state = 20;
if (parent.mostCurrent._main._hayinternet /*boolean*/ ==anywheresoftware.b4a.keywords.Common.True) { 
this.state = 17;
}else {
this.state = 19;
}if (true) break;

case 17:
//C
this.state = 20;
 //BA.debugLineNum = 63;BA.debugLine="CargarMapa";
_cargarmapa();
 if (true) break;

case 19:
//C
this.state = 20;
 //BA.debugLineNum = 65;BA.debugLine="ToastMessageShow(\"Tienes que tener internet par";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Tienes que tener internet para acceder aquí!"),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 66;BA.debugLine="Activity.RemoveAllViews";
parent.mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 67;BA.debugLine="Activity.Finish";
parent.mostCurrent._activity.Finish();
 if (true) break;

case 20:
//C
this.state = 21;
;
 if (true) break;

case 21:
//C
this.state = -1;
;
 //BA.debugLineNum = 72;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public static void  _mapfragment1_ready() throws Exception{
}
public static boolean  _activity_keypress(int _keycode) throws Exception{
 //BA.debugLineNum = 73;BA.debugLine="Sub Activity_KeyPress (KeyCode As Int) As Boolean";
 //BA.debugLineNum = 74;BA.debugLine="If KeyCode = KeyCodes.KEYCODE_BACK Then";
if (_keycode==anywheresoftware.b4a.keywords.Common.KeyCodes.KEYCODE_BACK) { 
 //BA.debugLineNum = 75;BA.debugLine="Activity.finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 76;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 };
 //BA.debugLineNum = 78;BA.debugLine="End Sub";
return false;
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 82;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 85;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 79;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 81;BA.debugLine="End Sub";
return "";
}
public static String  _btncerrarmapa_click() throws Exception{
 //BA.debugLineNum = 470;BA.debugLine="Private Sub btnCerrarMapa_Click";
 //BA.debugLineNum = 471;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 472;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 473;BA.debugLine="End Sub";
return "";
}
public static void  _cargarmapa() throws Exception{
ResumableSub_CargarMapa rsub = new ResumableSub_CargarMapa(null);
rsub.resume(processBA, null);
}
public static class ResumableSub_CargarMapa extends BA.ResumableSub {
public ResumableSub_CargarMapa(appear.pnud.preservamos.frmmapa parent) {
this.parent = parent;
}
appear.pnud.preservamos.frmmapa parent;
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
boolean _completed = false;
int step24;
int limit24;
int step86;
int limit86;
int step149;
int limit149;

@Override
public void resume(BA ba, Object[] result) throws Exception{

    while (true) {
try {

        switch (state) {
            case -1:
return;

case 0:
//C
this.state = 1;
 //BA.debugLineNum = 88;BA.debugLine="markerred.Initialize(File.DirAssets, \"marker_red.";
parent.mostCurrent._markerred.Initialize(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"marker_red.png");
 //BA.debugLineNum = 89;BA.debugLine="markerorange.Initialize(File.DirAssets, \"marker_o";
parent.mostCurrent._markerorange.Initialize(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"marker_orange.png");
 //BA.debugLineNum = 90;BA.debugLine="markeryellow.Initialize(File.DirAssets, \"marker_y";
parent.mostCurrent._markeryellow.Initialize(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"marker_yellow.png");
 //BA.debugLineNum = 91;BA.debugLine="markergreen.Initialize(File.DirAssets, \"marker_gr";
parent.mostCurrent._markergreen.Initialize(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"marker_green.png");
 //BA.debugLineNum = 92;BA.debugLine="markerblue.Initialize(File.DirAssets, \"marker_blu";
parent.mostCurrent._markerblue.Initialize(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"marker_blue.png");
 //BA.debugLineNum = 93;BA.debugLine="markergray.Initialize(File.DirAssets, \"marker_gra";
parent.mostCurrent._markergray.Initialize(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"marker_gray.png");
 //BA.debugLineNum = 96;BA.debugLine="gmap.MapType=gmap.MAP_TYPE_HYBRID";
parent.mostCurrent._gmap.setMapType(parent.mostCurrent._gmap.MAP_TYPE_HYBRID);
 //BA.debugLineNum = 99;BA.debugLine="If origen = \"municipio\" Then";
if (true) break;

case 1:
//if
this.state = 144;
if ((parent._origen).equals("municipio")) { 
this.state = 3;
}else if((parent._origen).equals("datos_anteriores")) { 
this.state = 89;
}else {
this.state = 133;
}if (true) break;

case 3:
//C
this.state = 4;
 //BA.debugLineNum = 100;BA.debugLine="Dim act As String";
_act = "";
 //BA.debugLineNum = 101;BA.debugLine="datosMunicipio_parser.Initialize(datosMunicipio_";
parent._datosmunicipio_parser.Initialize(parent._datosmunicipio_string);
 //BA.debugLineNum = 102;BA.debugLine="act = datosMunicipio_parser.NextValue";
_act = BA.ObjectToString(parent._datosmunicipio_parser.NextValue());
 //BA.debugLineNum = 103;BA.debugLine="If act = \"Partido OK\" Then";
if (true) break;

case 4:
//if
this.state = 87;
if ((_act).equals("Partido OK")) { 
this.state = 6;
}else {
this.state = 47;
}if (true) break;

case 6:
//C
this.state = 7;
 //BA.debugLineNum = 104;BA.debugLine="Log(\"Cargando puntos del partido\")";
anywheresoftware.b4a.keywords.Common.LogImpl("035454993","Cargando puntos del partido",0);
 //BA.debugLineNum = 105;BA.debugLine="Dim nd As Map";
_nd = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 106;BA.debugLine="nd = datosMunicipio_parser.NextObject";
_nd = parent._datosmunicipio_parser.NextObject();
 //BA.debugLineNum = 108;BA.debugLine="Dim latlist As List";
_latlist = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 109;BA.debugLine="Dim lnglist As List";
_lnglist = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 110;BA.debugLine="latlist.Initialize";
_latlist.Initialize();
 //BA.debugLineNum = 111;BA.debugLine="lnglist.Initialize";
_lnglist.Initialize();
 //BA.debugLineNum = 112;BA.debugLine="If datosMunicipio_parser.NextValue = \"Con repor";
if (true) break;

case 7:
//if
this.state = 39;
if ((parent._datosmunicipio_parser.NextValue()).equals((Object)("Con reportes"))) { 
this.state = 9;
}else {
this.state = 38;
}if (true) break;

case 9:
//C
this.state = 10;
 //BA.debugLineNum = 113;BA.debugLine="Log(\"Con reportes\")";
anywheresoftware.b4a.keywords.Common.LogImpl("035455002","Con reportes",0);
 //BA.debugLineNum = 114;BA.debugLine="Dim numresults As String";
_numresults = "";
 //BA.debugLineNum = 115;BA.debugLine="numresults = datosMunicipio_parser.NextValue";
_numresults = BA.ObjectToString(parent._datosmunicipio_parser.NextValue());
 //BA.debugLineNum = 116;BA.debugLine="For i = 0 To numresults - 1";
if (true) break;

case 10:
//for
this.state = 36;
step24 = 1;
limit24 = (int) ((double)(Double.parseDouble(_numresults))-1);
_i = (int) (0) ;
this.state = 145;
if (true) break;

case 145:
//C
this.state = 36;
if ((step24 > 0 && _i <= limit24) || (step24 < 0 && _i >= limit24)) this.state = 12;
if (true) break;

case 146:
//C
this.state = 145;
_i = ((int)(0 + _i + step24)) ;
if (true) break;

case 12:
//C
this.state = 13;
 //BA.debugLineNum = 118;BA.debugLine="Dim newpunto As Map";
_newpunto = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 119;BA.debugLine="newpunto = datosMunicipio_parser.NextObject";
_newpunto = parent._datosmunicipio_parser.NextObject();
 //BA.debugLineNum = 121;BA.debugLine="Dim sitiolat As Double = newpunto.Get(\"lat\")";
_sitiolat = (double)(BA.ObjectToNumber(_newpunto.Get((Object)("lat"))));
 //BA.debugLineNum = 122;BA.debugLine="Dim sitiolong As Double = newpunto.Get(\"lng\")";
_sitiolong = (double)(BA.ObjectToNumber(_newpunto.Get((Object)("lng"))));
 //BA.debugLineNum = 123;BA.debugLine="Dim sitioindice As String = newpunto.Get(\"ind";
_sitioindice = BA.ObjectToString(_newpunto.Get((Object)("indice")));
 //BA.debugLineNum = 124;BA.debugLine="Dim sitiocontribucion As String = newpunto.Ge";
_sitiocontribucion = BA.ObjectToString(_newpunto.Get((Object)("username")));
 //BA.debugLineNum = 126;BA.debugLine="If sitiocontribucion.Contains(\"@\") Then";
if (true) break;

case 13:
//if
this.state = 16;
if (_sitiocontribucion.contains("@")) { 
this.state = 15;
}if (true) break;

case 15:
//C
this.state = 16;
 //BA.debugLineNum = 127;BA.debugLine="sitiocontribucion = sitiocontribucion.SubStr";
_sitiocontribucion = _sitiocontribucion.substring((int) (0),_sitiocontribucion.indexOf("@"));
 if (true) break;
;
 //BA.debugLineNum = 130;BA.debugLine="If sitiolat <> \"0\" And sitiolong <> \"0\" Then";

case 16:
//if
this.state = 35;
if (_sitiolat!=(double)(Double.parseDouble("0")) && _sitiolong!=(double)(Double.parseDouble("0"))) { 
this.state = 18;
}else {
this.state = 34;
}if (true) break;

case 18:
//C
this.state = 19;
 //BA.debugLineNum = 131;BA.debugLine="If sitioindice > 0 And sitioindice < 20 Then";
if (true) break;

case 19:
//if
this.state = 32;
if ((double)(Double.parseDouble(_sitioindice))>0 && (double)(Double.parseDouble(_sitioindice))<20) { 
this.state = 21;
}else if((double)(Double.parseDouble(_sitioindice))>20 && (double)(Double.parseDouble(_sitioindice))<40) { 
this.state = 23;
}else if((double)(Double.parseDouble(_sitioindice))>40 && (double)(Double.parseDouble(_sitioindice))<60) { 
this.state = 25;
}else if((double)(Double.parseDouble(_sitioindice))>60 && (double)(Double.parseDouble(_sitioindice))<80) { 
this.state = 27;
}else if((double)(Double.parseDouble(_sitioindice))>80 && (double)(Double.parseDouble(_sitioindice))<100) { 
this.state = 29;
}else {
this.state = 31;
}if (true) break;

case 21:
//C
this.state = 32;
 //BA.debugLineNum = 132;BA.debugLine="gmap.AddMarker2(sitiolat,sitiolong,sitioind";
parent.mostCurrent._gmap.AddMarker2(_sitiolat,_sitiolong,_sitioindice,parent.mostCurrent._gmap.HUE_RED);
 if (true) break;

case 23:
//C
this.state = 32;
 //BA.debugLineNum = 134;BA.debugLine="gmap.AddMarker2(sitiolat,sitiolong,sitioind";
parent.mostCurrent._gmap.AddMarker2(_sitiolat,_sitiolong,_sitioindice,parent.mostCurrent._gmap.HUE_ORANGE);
 if (true) break;

case 25:
//C
this.state = 32;
 //BA.debugLineNum = 136;BA.debugLine="gmap.AddMarker2(sitiolat,sitiolong,sitioind";
parent.mostCurrent._gmap.AddMarker2(_sitiolat,_sitiolong,_sitioindice,parent.mostCurrent._gmap.HUE_YELLOW);
 if (true) break;

case 27:
//C
this.state = 32;
 //BA.debugLineNum = 138;BA.debugLine="gmap.AddMarker2(sitiolat,sitiolong,sitioind";
parent.mostCurrent._gmap.AddMarker2(_sitiolat,_sitiolong,_sitioindice,parent.mostCurrent._gmap.HUE_GREEN);
 if (true) break;

case 29:
//C
this.state = 32;
 //BA.debugLineNum = 140;BA.debugLine="gmap.AddMarker2(sitiolat,sitiolong,sitioind";
parent.mostCurrent._gmap.AddMarker2(_sitiolat,_sitiolong,_sitioindice,parent.mostCurrent._gmap.HUE_BLUE);
 if (true) break;

case 31:
//C
this.state = 32;
 //BA.debugLineNum = 142;BA.debugLine="gmap.AddMarker2(sitiolat,sitiolong,sitioind";
parent.mostCurrent._gmap.AddMarker2(_sitiolat,_sitiolong,_sitioindice,parent.mostCurrent._gmap.HUE_AZURE);
 if (true) break;

case 32:
//C
this.state = 35;
;
 //BA.debugLineNum = 144;BA.debugLine="latlist.Add(newpunto.Get(\"lat\"))";
_latlist.Add(_newpunto.Get((Object)("lat")));
 //BA.debugLineNum = 145;BA.debugLine="lnglist.Add(newpunto.Get(\"lng\"))";
_lnglist.Add(_newpunto.Get((Object)("lng")));
 if (true) break;

case 34:
//C
this.state = 35;
 //BA.debugLineNum = 147;BA.debugLine="Log(\"Punto no agregado\")";
anywheresoftware.b4a.keywords.Common.LogImpl("035455036","Punto no agregado",0);
 if (true) break;

case 35:
//C
this.state = 146;
;
 if (true) break;
if (true) break;

case 36:
//C
this.state = 39;
;
 if (true) break;

case 38:
//C
this.state = 39;
 //BA.debugLineNum = 151;BA.debugLine="ToastMessageShow(\"No hay envíos de ese municip";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("No hay envíos de ese municipio todavia"),anywheresoftware.b4a.keywords.Common.False);
 if (true) break;

case 39:
//C
this.state = 40;
;
 //BA.debugLineNum = 155;BA.debugLine="Dim GoogleMapsExtras1 As GoogleMapsExtras";
_googlemapsextras1 = new uk.co.martinpearman.b4a.googlemapsextras.GoogleMapsExtras();
 //BA.debugLineNum = 156;BA.debugLine="Dim CameraUpdateFactory1 As CameraUpdateFactory";
_cameraupdatefactory1 = new uk.co.martinpearman.b4a.com.google.android.gms.maps.CameraUpdateFactory();
 //BA.debugLineNum = 157;BA.debugLine="Dim CameraUpdate1 As CameraUpdate";
_cameraupdate1 = new uk.co.martinpearman.b4a.com.google.android.gms.maps.CameraUpdate();
 //BA.debugLineNum = 158;BA.debugLine="Dim llbounds As LatLngBounds";
_llbounds = new uk.co.martinpearman.b4a.com.google.android.gms.maps.model.LatLngBounds();
 //BA.debugLineNum = 159;BA.debugLine="Dim sw, ne As LatLng 'your southwest and northe";
_sw = new anywheresoftware.b4a.objects.MapFragmentWrapper.LatLngWrapper();
_ne = new anywheresoftware.b4a.objects.MapFragmentWrapper.LatLngWrapper();
 //BA.debugLineNum = 160;BA.debugLine="latlist.Sort(False)";
_latlist.Sort(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 161;BA.debugLine="lnglist.Sort(False)";
_lnglist.Sort(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 162;BA.debugLine="sw.Initialize(latlist.Get(0), lnglist.Get(0))";
_sw.Initialize((double)(BA.ObjectToNumber(_latlist.Get((int) (0)))),(double)(BA.ObjectToNumber(_lnglist.Get((int) (0)))));
 //BA.debugLineNum = 163;BA.debugLine="latlist.Sort(True)";
_latlist.Sort(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 164;BA.debugLine="lnglist.Sort(True)";
_lnglist.Sort(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 165;BA.debugLine="ne.Initialize(latlist.Get(0), lnglist.Get(0))";
_ne.Initialize((double)(BA.ObjectToNumber(_latlist.Get((int) (0)))),(double)(BA.ObjectToNumber(_lnglist.Get((int) (0)))));
 //BA.debugLineNum = 166;BA.debugLine="Log(GoogleMapsExtras1.GetProjection(gmap).GetVi";
anywheresoftware.b4a.keywords.Common.LogImpl("035455055",BA.ObjectToString(_googlemapsextras1.GetProjection((com.google.android.gms.maps.GoogleMap)(parent.mostCurrent._gmap.getObject())).GetVisibleRegion().getLatLngBounds()),0);
 //BA.debugLineNum = 167;BA.debugLine="Try";
if (true) break;

case 40:
//try
this.state = 45;
this.catchState = 44;
this.state = 42;
if (true) break;

case 42:
//C
this.state = 45;
this.catchState = 44;
 //BA.debugLineNum = 168;BA.debugLine="llbounds.Initialize(sw, ne)";
_llbounds.Initialize((com.google.android.gms.maps.model.LatLng)(_sw.getObject()),(com.google.android.gms.maps.model.LatLng)(_ne.getObject()));
 //BA.debugLineNum = 169;BA.debugLine="CameraUpdate1=CameraUpdateFactory1.NewLatLngBo";
_cameraupdate1 = _cameraupdatefactory1.NewLatLngBounds((com.google.android.gms.maps.model.LatLngBounds)(_llbounds.getObject()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (40),mostCurrent.activityBA));
 //BA.debugLineNum = 170;BA.debugLine="GoogleMapsExtras1.MoveCamera(gmap, CameraUpdat";
_googlemapsextras1.MoveCamera((com.google.android.gms.maps.GoogleMap)(parent.mostCurrent._gmap.getObject()),(com.google.android.gms.maps.CameraUpdate)(_cameraupdate1.getObject()));
 if (true) break;

case 44:
//C
this.state = 45;
this.catchState = 0;
 //BA.debugLineNum = 172;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("035455061",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA)),0);
 if (true) break;
if (true) break;

case 45:
//C
this.state = 87;
this.catchState = 0;
;
 if (true) break;

case 47:
//C
this.state = 48;
 //BA.debugLineNum = 177;BA.debugLine="Log(\"Partido NO existente:\" & Main.strUserOrg)";
anywheresoftware.b4a.keywords.Common.LogImpl("035455066","Partido NO existente:"+parent.mostCurrent._main._struserorg /*String*/ ,0);
 //BA.debugLineNum = 180;BA.debugLine="If datosMunicipio_parser.NextValue = \"Con repor";
if (true) break;

case 48:
//if
this.state = 86;
if ((parent._datosmunicipio_parser.NextValue()).equals((Object)("Con reportes"))) { 
this.state = 50;
}else {
this.state = 85;
}if (true) break;

case 50:
//C
this.state = 51;
 //BA.debugLineNum = 182;BA.debugLine="Dim latlist As List";
_latlist = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 183;BA.debugLine="Dim lnglist As List";
_lnglist = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 184;BA.debugLine="latlist.Initialize";
_latlist.Initialize();
 //BA.debugLineNum = 185;BA.debugLine="lnglist.Initialize";
_lnglist.Initialize();
 //BA.debugLineNum = 186;BA.debugLine="Log(\"Con reportes\")";
anywheresoftware.b4a.keywords.Common.LogImpl("035455075","Con reportes",0);
 //BA.debugLineNum = 187;BA.debugLine="Dim numresults As String";
_numresults = "";
 //BA.debugLineNum = 188;BA.debugLine="numresults = datosMunicipio_parser.NextValue";
_numresults = BA.ObjectToString(parent._datosmunicipio_parser.NextValue());
 //BA.debugLineNum = 189;BA.debugLine="For i = 0 To numresults - 1";
if (true) break;

case 51:
//for
this.state = 77;
step86 = 1;
limit86 = (int) ((double)(Double.parseDouble(_numresults))-1);
_i = (int) (0) ;
this.state = 147;
if (true) break;

case 147:
//C
this.state = 77;
if ((step86 > 0 && _i <= limit86) || (step86 < 0 && _i >= limit86)) this.state = 53;
if (true) break;

case 148:
//C
this.state = 147;
_i = ((int)(0 + _i + step86)) ;
if (true) break;

case 53:
//C
this.state = 54;
 //BA.debugLineNum = 191;BA.debugLine="Dim newpunto As Map";
_newpunto = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 192;BA.debugLine="newpunto = datosMunicipio_parser.NextObject";
_newpunto = parent._datosmunicipio_parser.NextObject();
 //BA.debugLineNum = 194;BA.debugLine="Dim sitiolat As Double = newpunto.Get(\"lat\")";
_sitiolat = (double)(BA.ObjectToNumber(_newpunto.Get((Object)("lat"))));
 //BA.debugLineNum = 195;BA.debugLine="Dim sitiolong As Double = newpunto.Get(\"lng\")";
_sitiolong = (double)(BA.ObjectToNumber(_newpunto.Get((Object)("lng"))));
 //BA.debugLineNum = 196;BA.debugLine="Dim sitioindice As String = newpunto.Get(\"ind";
_sitioindice = BA.ObjectToString(_newpunto.Get((Object)("indice")));
 //BA.debugLineNum = 197;BA.debugLine="Dim sitiocontribucion As String = newpunto.Ge";
_sitiocontribucion = BA.ObjectToString(_newpunto.Get((Object)("username")));
 //BA.debugLineNum = 199;BA.debugLine="If sitiocontribucion.Contains(\"@\") Then";
if (true) break;

case 54:
//if
this.state = 57;
if (_sitiocontribucion.contains("@")) { 
this.state = 56;
}if (true) break;

case 56:
//C
this.state = 57;
 //BA.debugLineNum = 200;BA.debugLine="sitiocontribucion = sitiocontribucion.SubStr";
_sitiocontribucion = _sitiocontribucion.substring((int) (0),_sitiocontribucion.indexOf("@"));
 if (true) break;
;
 //BA.debugLineNum = 203;BA.debugLine="If sitiolat <> \"0\" And sitiolong <> \"0\" Then";

case 57:
//if
this.state = 76;
if (_sitiolat!=(double)(Double.parseDouble("0")) && _sitiolong!=(double)(Double.parseDouble("0"))) { 
this.state = 59;
}else {
this.state = 75;
}if (true) break;

case 59:
//C
this.state = 60;
 //BA.debugLineNum = 204;BA.debugLine="If sitioindice > 0 And sitioindice < 20 Then";
if (true) break;

case 60:
//if
this.state = 73;
if ((double)(Double.parseDouble(_sitioindice))>0 && (double)(Double.parseDouble(_sitioindice))<20) { 
this.state = 62;
}else if((double)(Double.parseDouble(_sitioindice))>20 && (double)(Double.parseDouble(_sitioindice))<40) { 
this.state = 64;
}else if((double)(Double.parseDouble(_sitioindice))>40 && (double)(Double.parseDouble(_sitioindice))<60) { 
this.state = 66;
}else if((double)(Double.parseDouble(_sitioindice))>60 && (double)(Double.parseDouble(_sitioindice))<80) { 
this.state = 68;
}else if((double)(Double.parseDouble(_sitioindice))>80 && (double)(Double.parseDouble(_sitioindice))<100) { 
this.state = 70;
}else {
this.state = 72;
}if (true) break;

case 62:
//C
this.state = 73;
 //BA.debugLineNum = 205;BA.debugLine="gmap.AddMarker2(sitiolat,sitiolong,sitioind";
parent.mostCurrent._gmap.AddMarker2(_sitiolat,_sitiolong,_sitioindice,parent.mostCurrent._gmap.HUE_RED);
 if (true) break;

case 64:
//C
this.state = 73;
 //BA.debugLineNum = 207;BA.debugLine="gmap.AddMarker2(sitiolat,sitiolong,sitioind";
parent.mostCurrent._gmap.AddMarker2(_sitiolat,_sitiolong,_sitioindice,parent.mostCurrent._gmap.HUE_ORANGE);
 if (true) break;

case 66:
//C
this.state = 73;
 //BA.debugLineNum = 209;BA.debugLine="gmap.AddMarker2(sitiolat,sitiolong,sitioind";
parent.mostCurrent._gmap.AddMarker2(_sitiolat,_sitiolong,_sitioindice,parent.mostCurrent._gmap.HUE_YELLOW);
 if (true) break;

case 68:
//C
this.state = 73;
 //BA.debugLineNum = 211;BA.debugLine="gmap.AddMarker2(sitiolat,sitiolong,sitioind";
parent.mostCurrent._gmap.AddMarker2(_sitiolat,_sitiolong,_sitioindice,parent.mostCurrent._gmap.HUE_GREEN);
 if (true) break;

case 70:
//C
this.state = 73;
 //BA.debugLineNum = 213;BA.debugLine="gmap.AddMarker2(sitiolat,sitiolong,sitioind";
parent.mostCurrent._gmap.AddMarker2(_sitiolat,_sitiolong,_sitioindice,parent.mostCurrent._gmap.HUE_BLUE);
 if (true) break;

case 72:
//C
this.state = 73;
 //BA.debugLineNum = 215;BA.debugLine="gmap.AddMarker2(sitiolat,sitiolong,sitioind";
parent.mostCurrent._gmap.AddMarker2(_sitiolat,_sitiolong,_sitioindice,parent.mostCurrent._gmap.HUE_AZURE);
 if (true) break;

case 73:
//C
this.state = 76;
;
 //BA.debugLineNum = 217;BA.debugLine="latlist.Add(newpunto.Get(\"lat\"))";
_latlist.Add(_newpunto.Get((Object)("lat")));
 //BA.debugLineNum = 218;BA.debugLine="lnglist.Add(newpunto.Get(\"lng\"))";
_lnglist.Add(_newpunto.Get((Object)("lng")));
 if (true) break;

case 75:
//C
this.state = 76;
 //BA.debugLineNum = 220;BA.debugLine="Log(\"Punto no agregado\")";
anywheresoftware.b4a.keywords.Common.LogImpl("035455109","Punto no agregado",0);
 if (true) break;

case 76:
//C
this.state = 148;
;
 if (true) break;
if (true) break;

case 77:
//C
this.state = 78;
;
 //BA.debugLineNum = 225;BA.debugLine="Dim GoogleMapsExtras1 As GoogleMapsExtras";
_googlemapsextras1 = new uk.co.martinpearman.b4a.googlemapsextras.GoogleMapsExtras();
 //BA.debugLineNum = 226;BA.debugLine="Dim CameraUpdateFactory1 As CameraUpdateFactor";
_cameraupdatefactory1 = new uk.co.martinpearman.b4a.com.google.android.gms.maps.CameraUpdateFactory();
 //BA.debugLineNum = 227;BA.debugLine="Dim CameraUpdate1 As CameraUpdate";
_cameraupdate1 = new uk.co.martinpearman.b4a.com.google.android.gms.maps.CameraUpdate();
 //BA.debugLineNum = 228;BA.debugLine="Dim llbounds As LatLngBounds";
_llbounds = new uk.co.martinpearman.b4a.com.google.android.gms.maps.model.LatLngBounds();
 //BA.debugLineNum = 229;BA.debugLine="Dim sw, ne As LatLng 'your southwest and north";
_sw = new anywheresoftware.b4a.objects.MapFragmentWrapper.LatLngWrapper();
_ne = new anywheresoftware.b4a.objects.MapFragmentWrapper.LatLngWrapper();
 //BA.debugLineNum = 230;BA.debugLine="latlist.Sort(False)";
_latlist.Sort(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 231;BA.debugLine="lnglist.Sort(False)";
_lnglist.Sort(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 232;BA.debugLine="sw.Initialize(latlist.Get(0), lnglist.Get(0))";
_sw.Initialize((double)(BA.ObjectToNumber(_latlist.Get((int) (0)))),(double)(BA.ObjectToNumber(_lnglist.Get((int) (0)))));
 //BA.debugLineNum = 233;BA.debugLine="latlist.Sort(True)";
_latlist.Sort(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 234;BA.debugLine="lnglist.Sort(True)";
_lnglist.Sort(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 235;BA.debugLine="ne.Initialize(latlist.Get(0), lnglist.Get(0))";
_ne.Initialize((double)(BA.ObjectToNumber(_latlist.Get((int) (0)))),(double)(BA.ObjectToNumber(_lnglist.Get((int) (0)))));
 //BA.debugLineNum = 236;BA.debugLine="Log(GoogleMapsExtras1.GetProjection(gmap).GetV";
anywheresoftware.b4a.keywords.Common.LogImpl("035455125",BA.ObjectToString(_googlemapsextras1.GetProjection((com.google.android.gms.maps.GoogleMap)(parent.mostCurrent._gmap.getObject())).GetVisibleRegion().getLatLngBounds()),0);
 //BA.debugLineNum = 237;BA.debugLine="Try";
if (true) break;

case 78:
//try
this.state = 83;
this.catchState = 82;
this.state = 80;
if (true) break;

case 80:
//C
this.state = 83;
this.catchState = 82;
 //BA.debugLineNum = 238;BA.debugLine="llbounds.Initialize(sw, ne)";
_llbounds.Initialize((com.google.android.gms.maps.model.LatLng)(_sw.getObject()),(com.google.android.gms.maps.model.LatLng)(_ne.getObject()));
 //BA.debugLineNum = 239;BA.debugLine="CameraUpdate1=CameraUpdateFactory1.NewLatLngB";
_cameraupdate1 = _cameraupdatefactory1.NewLatLngBounds((com.google.android.gms.maps.model.LatLngBounds)(_llbounds.getObject()),(int) (10));
 //BA.debugLineNum = 240;BA.debugLine="GoogleMapsExtras1.MoveCamera(gmap, CameraUpda";
_googlemapsextras1.MoveCamera((com.google.android.gms.maps.GoogleMap)(parent.mostCurrent._gmap.getObject()),(com.google.android.gms.maps.CameraUpdate)(_cameraupdate1.getObject()));
 if (true) break;

case 82:
//C
this.state = 83;
this.catchState = 0;
 //BA.debugLineNum = 242;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("035455131",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA)),0);
 if (true) break;
if (true) break;

case 83:
//C
this.state = 86;
this.catchState = 0;
;
 if (true) break;

case 85:
//C
this.state = 86;
 //BA.debugLineNum = 247;BA.debugLine="Log(\"No tiene reportes\")";
anywheresoftware.b4a.keywords.Common.LogImpl("035455136","No tiene reportes",0);
 //BA.debugLineNum = 248;BA.debugLine="Activity.Finish()";
parent.mostCurrent._activity.Finish();
 if (true) break;

case 86:
//C
this.state = 87;
;
 if (true) break;

case 87:
//C
this.state = 144;
;
 //BA.debugLineNum = 252;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 if (true) break;

case 89:
//C
this.state = 90;
 //BA.debugLineNum = 255;BA.debugLine="Dim listaDatosAnteriores As List";
_listadatosanteriores = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 256;BA.debugLine="listaDatosAnteriores.Initialize";
_listadatosanteriores.Initialize();
 //BA.debugLineNum = 257;BA.debugLine="listaDatosAnteriores = Main.user_markers_list";
_listadatosanteriores = parent.mostCurrent._main._user_markers_list /*anywheresoftware.b4a.objects.collections.List*/ ;
 //BA.debugLineNum = 259;BA.debugLine="Dim latlist As List";
_latlist = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 260;BA.debugLine="Dim lnglist As List";
_lnglist = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 261;BA.debugLine="latlist.Initialize";
_latlist.Initialize();
 //BA.debugLineNum = 262;BA.debugLine="lnglist.Initialize";
_lnglist.Initialize();
 //BA.debugLineNum = 263;BA.debugLine="For m = 0 To listaDatosAnteriores.Size - 1";
if (true) break;

case 90:
//for
this.state = 125;
step149 = 1;
limit149 = (int) (_listadatosanteriores.getSize()-1);
_m = (int) (0) ;
this.state = 149;
if (true) break;

case 149:
//C
this.state = 125;
if ((step149 > 0 && _m <= limit149) || (step149 < 0 && _m >= limit149)) this.state = 92;
if (true) break;

case 150:
//C
this.state = 149;
_m = ((int)(0 + _m + step149)) ;
if (true) break;

case 92:
//C
this.state = 93;
 //BA.debugLineNum = 265;BA.debugLine="Dim newpunto As Map";
_newpunto = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 266;BA.debugLine="newpunto = listaDatosAnteriores.Get(m)";
_newpunto = (anywheresoftware.b4a.objects.collections.Map) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.collections.Map(), (java.util.Map)(_listadatosanteriores.Get(_m)));
 //BA.debugLineNum = 269;BA.debugLine="Dim sitiolat As Double";
_sitiolat = 0;
 //BA.debugLineNum = 270;BA.debugLine="If newpunto.Get(\"lat\") <> Null Then";
if (true) break;

case 93:
//if
this.state = 98;
if (_newpunto.Get((Object)("lat"))!= null) { 
this.state = 95;
}else {
this.state = 97;
}if (true) break;

case 95:
//C
this.state = 98;
 //BA.debugLineNum = 271;BA.debugLine="sitiolat= newpunto.Get(\"lat\")";
_sitiolat = (double)(BA.ObjectToNumber(_newpunto.Get((Object)("lat"))));
 if (true) break;

case 97:
//C
this.state = 98;
 //BA.debugLineNum = 273;BA.debugLine="Return";
if (true) return ;
 if (true) break;

case 98:
//C
this.state = 99;
;
 //BA.debugLineNum = 276;BA.debugLine="Dim sitiolong As Double";
_sitiolong = 0;
 //BA.debugLineNum = 277;BA.debugLine="If newpunto.Get(\"lng\") <> Null Then";
if (true) break;

case 99:
//if
this.state = 104;
if (_newpunto.Get((Object)("lng"))!= null) { 
this.state = 101;
}else {
this.state = 103;
}if (true) break;

case 101:
//C
this.state = 104;
 //BA.debugLineNum = 278;BA.debugLine="sitiolong= newpunto.Get(\"lng\")";
_sitiolong = (double)(BA.ObjectToNumber(_newpunto.Get((Object)("lng"))));
 if (true) break;

case 103:
//C
this.state = 104;
 //BA.debugLineNum = 280;BA.debugLine="Return";
if (true) return ;
 if (true) break;

case 104:
//C
this.state = 105;
;
 //BA.debugLineNum = 283;BA.debugLine="Dim sitioindice As String = newpunto.Get(\"indic";
_sitioindice = BA.ObjectToString(_newpunto.Get((Object)("indice")));
 //BA.debugLineNum = 285;BA.debugLine="If sitiolat <> \"0\" And sitiolong <> \"0\" Then";
if (true) break;

case 105:
//if
this.state = 124;
if (_sitiolat!=(double)(Double.parseDouble("0")) && _sitiolong!=(double)(Double.parseDouble("0"))) { 
this.state = 107;
}else {
this.state = 123;
}if (true) break;

case 107:
//C
this.state = 108;
 //BA.debugLineNum = 286;BA.debugLine="If sitioindice > 0 And sitioindice < 20 Then";
if (true) break;

case 108:
//if
this.state = 121;
if ((double)(Double.parseDouble(_sitioindice))>0 && (double)(Double.parseDouble(_sitioindice))<20) { 
this.state = 110;
}else if((double)(Double.parseDouble(_sitioindice))>20 && (double)(Double.parseDouble(_sitioindice))<40) { 
this.state = 112;
}else if((double)(Double.parseDouble(_sitioindice))>40 && (double)(Double.parseDouble(_sitioindice))<60) { 
this.state = 114;
}else if((double)(Double.parseDouble(_sitioindice))>60 && (double)(Double.parseDouble(_sitioindice))<80) { 
this.state = 116;
}else if((double)(Double.parseDouble(_sitioindice))>80 && (double)(Double.parseDouble(_sitioindice))<100) { 
this.state = 118;
}else {
this.state = 120;
}if (true) break;

case 110:
//C
this.state = 121;
 //BA.debugLineNum = 287;BA.debugLine="gmap.AddMarker2(sitiolat,sitiolong,sitioindic";
parent.mostCurrent._gmap.AddMarker2(_sitiolat,_sitiolong,_sitioindice,parent.mostCurrent._gmap.HUE_RED);
 if (true) break;

case 112:
//C
this.state = 121;
 //BA.debugLineNum = 289;BA.debugLine="gmap.AddMarker2(sitiolat,sitiolong,sitioindic";
parent.mostCurrent._gmap.AddMarker2(_sitiolat,_sitiolong,_sitioindice,parent.mostCurrent._gmap.HUE_ORANGE);
 if (true) break;

case 114:
//C
this.state = 121;
 //BA.debugLineNum = 291;BA.debugLine="gmap.AddMarker2(sitiolat,sitiolong,sitioindic";
parent.mostCurrent._gmap.AddMarker2(_sitiolat,_sitiolong,_sitioindice,parent.mostCurrent._gmap.HUE_YELLOW);
 if (true) break;

case 116:
//C
this.state = 121;
 //BA.debugLineNum = 293;BA.debugLine="gmap.AddMarker2(sitiolat,sitiolong,sitioindic";
parent.mostCurrent._gmap.AddMarker2(_sitiolat,_sitiolong,_sitioindice,parent.mostCurrent._gmap.HUE_GREEN);
 if (true) break;

case 118:
//C
this.state = 121;
 //BA.debugLineNum = 295;BA.debugLine="gmap.AddMarker2(sitiolat,sitiolong,sitioindic";
parent.mostCurrent._gmap.AddMarker2(_sitiolat,_sitiolong,_sitioindice,parent.mostCurrent._gmap.HUE_BLUE);
 if (true) break;

case 120:
//C
this.state = 121;
 //BA.debugLineNum = 297;BA.debugLine="gmap.AddMarker2(sitiolat,sitiolong,sitioindic";
parent.mostCurrent._gmap.AddMarker2(_sitiolat,_sitiolong,_sitioindice,parent.mostCurrent._gmap.HUE_AZURE);
 if (true) break;

case 121:
//C
this.state = 124;
;
 //BA.debugLineNum = 299;BA.debugLine="latlist.Add(newpunto.Get(\"lat\"))";
_latlist.Add(_newpunto.Get((Object)("lat")));
 //BA.debugLineNum = 300;BA.debugLine="lnglist.Add(newpunto.Get(\"lng\"))";
_lnglist.Add(_newpunto.Get((Object)("lng")));
 if (true) break;

case 123:
//C
this.state = 124;
 //BA.debugLineNum = 302;BA.debugLine="Log(\"Punto no agregado\")";
anywheresoftware.b4a.keywords.Common.LogImpl("035455191","Punto no agregado",0);
 if (true) break;

case 124:
//C
this.state = 150;
;
 if (true) break;
if (true) break;

case 125:
//C
this.state = 126;
;
 //BA.debugLineNum = 306;BA.debugLine="Dim GoogleMapsExtras1 As GoogleMapsExtras";
_googlemapsextras1 = new uk.co.martinpearman.b4a.googlemapsextras.GoogleMapsExtras();
 //BA.debugLineNum = 307;BA.debugLine="Dim CameraUpdateFactory1 As CameraUpdateFactory";
_cameraupdatefactory1 = new uk.co.martinpearman.b4a.com.google.android.gms.maps.CameraUpdateFactory();
 //BA.debugLineNum = 308;BA.debugLine="Dim CameraUpdate1 As CameraUpdate";
_cameraupdate1 = new uk.co.martinpearman.b4a.com.google.android.gms.maps.CameraUpdate();
 //BA.debugLineNum = 309;BA.debugLine="Dim llbounds As LatLngBounds";
_llbounds = new uk.co.martinpearman.b4a.com.google.android.gms.maps.model.LatLngBounds();
 //BA.debugLineNum = 310;BA.debugLine="Dim sw, ne As LatLng 'your southwest and northea";
_sw = new anywheresoftware.b4a.objects.MapFragmentWrapper.LatLngWrapper();
_ne = new anywheresoftware.b4a.objects.MapFragmentWrapper.LatLngWrapper();
 //BA.debugLineNum = 311;BA.debugLine="latlist.Sort(False)";
_latlist.Sort(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 312;BA.debugLine="lnglist.Sort(False)";
_lnglist.Sort(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 313;BA.debugLine="sw.Initialize(latlist.Get(0), lnglist.Get(0))";
_sw.Initialize((double)(BA.ObjectToNumber(_latlist.Get((int) (0)))),(double)(BA.ObjectToNumber(_lnglist.Get((int) (0)))));
 //BA.debugLineNum = 314;BA.debugLine="latlist.Sort(True)";
_latlist.Sort(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 315;BA.debugLine="lnglist.Sort(True)";
_lnglist.Sort(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 316;BA.debugLine="ne.Initialize(latlist.Get(0), lnglist.Get(0))";
_ne.Initialize((double)(BA.ObjectToNumber(_latlist.Get((int) (0)))),(double)(BA.ObjectToNumber(_lnglist.Get((int) (0)))));
 //BA.debugLineNum = 317;BA.debugLine="Log(GoogleMapsExtras1.GetProjection(gmap).GetVis";
anywheresoftware.b4a.keywords.Common.LogImpl("035455206",BA.ObjectToString(_googlemapsextras1.GetProjection((com.google.android.gms.maps.GoogleMap)(parent.mostCurrent._gmap.getObject())).GetVisibleRegion().getLatLngBounds()),0);
 //BA.debugLineNum = 318;BA.debugLine="Try";
if (true) break;

case 126:
//try
this.state = 131;
this.catchState = 130;
this.state = 128;
if (true) break;

case 128:
//C
this.state = 131;
this.catchState = 130;
 //BA.debugLineNum = 319;BA.debugLine="llbounds.Initialize(sw, ne)";
_llbounds.Initialize((com.google.android.gms.maps.model.LatLng)(_sw.getObject()),(com.google.android.gms.maps.model.LatLng)(_ne.getObject()));
 //BA.debugLineNum = 320;BA.debugLine="CameraUpdate1=CameraUpdateFactory1.NewLatLngBou";
_cameraupdate1 = _cameraupdatefactory1.NewLatLngBounds((com.google.android.gms.maps.model.LatLngBounds)(_llbounds.getObject()),(int) (10));
 //BA.debugLineNum = 321;BA.debugLine="GoogleMapsExtras1.MoveCamera(gmap, CameraUpdate";
_googlemapsextras1.MoveCamera((com.google.android.gms.maps.GoogleMap)(parent.mostCurrent._gmap.getObject()),(com.google.android.gms.maps.CameraUpdate)(_cameraupdate1.getObject()));
 if (true) break;

case 130:
//C
this.state = 131;
this.catchState = 0;
 //BA.debugLineNum = 323;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("035455212",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA)),0);
 if (true) break;
if (true) break;

case 131:
//C
this.state = 144;
this.catchState = 0;
;
 //BA.debugLineNum = 326;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 if (true) break;

case 133:
//C
this.state = 134;
 //BA.debugLineNum = 328;BA.debugLine="Dim myLocation As LatLng";
_mylocation = new anywheresoftware.b4a.objects.MapFragmentWrapper.LatLngWrapper();
 //BA.debugLineNum = 329;BA.debugLine="myLocation = gmap.MyLocation";
_mylocation = parent.mostCurrent._gmap.getMyLocation();
 //BA.debugLineNum = 331;BA.debugLine="If myLocation.IsInitialized = False Then";
if (true) break;

case 134:
//if
this.state = 137;
if (_mylocation.IsInitialized()==anywheresoftware.b4a.keywords.Common.False) { 
this.state = 136;
}if (true) break;

case 136:
//C
this.state = 137;
 //BA.debugLineNum = 332;BA.debugLine="myLocation.Initialize(\"-34.9204950\",\"-57.953566";
_mylocation.Initialize((double)(Double.parseDouble("-34.9204950")),(double)(Double.parseDouble("-57.9535660")));
 if (true) break;

case 137:
//C
this.state = 138;
;
 //BA.debugLineNum = 334;BA.debugLine="Dim cp As CameraPosition";
_cp = new anywheresoftware.b4a.objects.MapFragmentWrapper.CameraPositionWrapper();
 //BA.debugLineNum = 335;BA.debugLine="cp.Initialize(myLocation.Latitude, myLocation.Lo";
_cp.Initialize(_mylocation.getLatitude(),_mylocation.getLongitude(),(float) (16));
 //BA.debugLineNum = 336;BA.debugLine="gmap.AnimateCamera(cp)";
parent.mostCurrent._gmap.AnimateCamera((com.google.android.gms.maps.model.CameraPosition)(_cp.getObject()));
 //BA.debugLineNum = 337;BA.debugLine="gmap.MapType=gmap.MAP_TYPE_HYBRID";
parent.mostCurrent._gmap.setMapType(parent.mostCurrent._gmap.MAP_TYPE_HYBRID);
 //BA.debugLineNum = 339;BA.debugLine="Wait For (GetMiMapa) Complete (Completed As Bool";
anywheresoftware.b4a.keywords.Common.WaitFor("complete", processBA, this, _getmimapa());
this.state = 151;
return;
case 151:
//C
this.state = 138;
_completed = (Boolean) result[0];
;
 //BA.debugLineNum = 340;BA.debugLine="If Completed = True Then";
if (true) break;

case 138:
//if
this.state = 143;
if (_completed==anywheresoftware.b4a.keywords.Common.True) { 
this.state = 140;
}else {
this.state = 142;
}if (true) break;

case 140:
//C
this.state = 143;
 //BA.debugLineNum = 341;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 if (true) break;

case 142:
//C
this.state = 143;
 //BA.debugLineNum = 343;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 //BA.debugLineNum = 344;BA.debugLine="ToastMessageShow(\"No encuentro tus sitios anter";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("No encuentro tus sitios anteriores, prueba luego"),anywheresoftware.b4a.keywords.Common.True);
 if (true) break;

case 143:
//C
this.state = 144;
;
 if (true) break;

case 144:
//C
this.state = -1;
;
 //BA.debugLineNum = 349;BA.debugLine="End Sub";
if (true) break;
}} 
       catch (Exception e0) {
			
if (catchState == 0)
    throw e0;
else {
    state = catchState;
processBA.setLastException(e0);}
            }
        }
    }
}
public static void  _complete(boolean _completed) throws Exception{
}
public static anywheresoftware.b4a.keywords.Common.ResumableSubWrapper  _getmimapa() throws Exception{
ResumableSub_GetMiMapa rsub = new ResumableSub_GetMiMapa(null);
rsub.resume(processBA, null);
return (anywheresoftware.b4a.keywords.Common.ResumableSubWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.keywords.Common.ResumableSubWrapper(), rsub);
}
public static class ResumableSub_GetMiMapa extends BA.ResumableSub {
public ResumableSub_GetMiMapa(appear.pnud.preservamos.frmmapa parent) {
this.parent = parent;
}
appear.pnud.preservamos.frmmapa parent;
appear.pnud.preservamos.httpjob _j = null;
String _loginpath = "";
String _ret = "";
String _act = "";
anywheresoftware.b4a.objects.collections.JSONParser _parser = null;
String _numresults = "";
anywheresoftware.b4a.objects.collections.List _latlist = null;
anywheresoftware.b4a.objects.collections.List _lnglist = null;
int _i = 0;
anywheresoftware.b4a.objects.collections.Map _newpunto = null;
double _sitiolat = 0;
double _sitiolong = 0;
String _sitioindice = "";
String _sitiotiporio = "";
String _sitiocontribucion = "";
uk.co.martinpearman.b4a.googlemapsextras.GoogleMapsExtras _googlemapsextras1 = null;
uk.co.martinpearman.b4a.com.google.android.gms.maps.CameraUpdateFactory _cameraupdatefactory1 = null;
uk.co.martinpearman.b4a.com.google.android.gms.maps.CameraUpdate _cameraupdate1 = null;
uk.co.martinpearman.b4a.com.google.android.gms.maps.model.LatLngBounds _llbounds = null;
anywheresoftware.b4a.objects.MapFragmentWrapper.LatLngWrapper _sw = null;
anywheresoftware.b4a.objects.MapFragmentWrapper.LatLngWrapper _ne = null;
int step27;
int limit27;

@Override
public void resume(BA ba, Object[] result) throws Exception{

    while (true) {
try {

        switch (state) {
            case -1:
{
anywheresoftware.b4a.keywords.Common.ReturnFromResumableSub(this,null);return;}
case 0:
//C
this.state = 1;
 //BA.debugLineNum = 357;BA.debugLine="Log(\"Getting mapa\")";
anywheresoftware.b4a.keywords.Common.LogImpl("035520514","Getting mapa",0);
 //BA.debugLineNum = 358;BA.debugLine="Dim j As HttpJob";
_j = new appear.pnud.preservamos.httpjob();
 //BA.debugLineNum = 359;BA.debugLine="j.Initialize(\"\", Me)";
_j._initialize /*String*/ (processBA,"",frmmapa.getObject());
 //BA.debugLineNum = 360;BA.debugLine="Dim loginPath As String = Main.serverPath & \"/\" &";
_loginpath = parent.mostCurrent._main._serverpath /*String*/ +"/"+parent.mostCurrent._main._serverconnectionfolder /*String*/ +"/getallmapa.php";
 //BA.debugLineNum = 361;BA.debugLine="j.Download(loginPath)";
_j._download /*String*/ (_loginpath);
 //BA.debugLineNum = 362;BA.debugLine="Wait For (j) JobDone(j As HttpJob)";
anywheresoftware.b4a.keywords.Common.WaitFor("jobdone", processBA, this, (Object)(_j));
this.state = 48;
return;
case 48:
//C
this.state = 1;
_j = (appear.pnud.preservamos.httpjob) result[0];
;
 //BA.debugLineNum = 364;BA.debugLine="If j.Success Then";
if (true) break;

case 1:
//if
this.state = 47;
if (_j._success /*boolean*/ ) { 
this.state = 3;
}else {
this.state = 46;
}if (true) break;

case 3:
//C
this.state = 4;
 //BA.debugLineNum = 366;BA.debugLine="Dim ret As String";
_ret = "";
 //BA.debugLineNum = 367;BA.debugLine="Dim act As String";
_act = "";
 //BA.debugLineNum = 368;BA.debugLine="ret = j.GetString";
_ret = _j._getstring /*String*/ ();
 //BA.debugLineNum = 369;BA.debugLine="Dim parser As JSONParser";
_parser = new anywheresoftware.b4a.objects.collections.JSONParser();
 //BA.debugLineNum = 370;BA.debugLine="parser.Initialize(ret)";
_parser.Initialize(_ret);
 //BA.debugLineNum = 371;BA.debugLine="act = parser.NextValue";
_act = BA.ObjectToString(_parser.NextValue());
 //BA.debugLineNum = 372;BA.debugLine="If act = \"Not Found\" Then";
if (true) break;

case 4:
//if
this.state = 44;
if ((_act).equals("Not Found")) { 
this.state = 6;
}else if((_act).equals("Error")) { 
this.state = 8;
}else if((_act).equals("GetMapaOk")) { 
this.state = 10;
}if (true) break;

case 6:
//C
this.state = 44;
 //BA.debugLineNum = 373;BA.debugLine="j.Release";
_j._release /*String*/ ();
 //BA.debugLineNum = 374;BA.debugLine="Return False";
if (true) {
anywheresoftware.b4a.keywords.Common.ReturnFromResumableSub(this,(Object)(anywheresoftware.b4a.keywords.Common.False));return;};
 if (true) break;

case 8:
//C
this.state = 44;
 //BA.debugLineNum = 376;BA.debugLine="j.Release";
_j._release /*String*/ ();
 //BA.debugLineNum = 377;BA.debugLine="Return False";
if (true) {
anywheresoftware.b4a.keywords.Common.ReturnFromResumableSub(this,(Object)(anywheresoftware.b4a.keywords.Common.False));return;};
 if (true) break;

case 10:
//C
this.state = 11;
 //BA.debugLineNum = 380;BA.debugLine="Dim numresults As String";
_numresults = "";
 //BA.debugLineNum = 381;BA.debugLine="numresults = parser.NextValue";
_numresults = BA.ObjectToString(_parser.NextValue());
 //BA.debugLineNum = 383;BA.debugLine="Dim latlist As List";
_latlist = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 384;BA.debugLine="Dim lnglist As List";
_lnglist = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 385;BA.debugLine="latlist.Initialize";
_latlist.Initialize();
 //BA.debugLineNum = 386;BA.debugLine="lnglist.Initialize";
_lnglist.Initialize();
 //BA.debugLineNum = 388;BA.debugLine="For i = 0 To numresults - 1";
if (true) break;

case 11:
//for
this.state = 37;
step27 = 1;
limit27 = (int) ((double)(Double.parseDouble(_numresults))-1);
_i = (int) (0) ;
this.state = 49;
if (true) break;

case 49:
//C
this.state = 37;
if ((step27 > 0 && _i <= limit27) || (step27 < 0 && _i >= limit27)) this.state = 13;
if (true) break;

case 50:
//C
this.state = 49;
_i = ((int)(0 + _i + step27)) ;
if (true) break;

case 13:
//C
this.state = 14;
 //BA.debugLineNum = 390;BA.debugLine="Dim newpunto As Map";
_newpunto = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 391;BA.debugLine="newpunto = parser.NextObject";
_newpunto = _parser.NextObject();
 //BA.debugLineNum = 393;BA.debugLine="Dim sitiolat As Double= newpunto.Get(\"lat\")";
_sitiolat = (double)(BA.ObjectToNumber(_newpunto.Get((Object)("lat"))));
 //BA.debugLineNum = 394;BA.debugLine="Dim sitiolong As Double= newpunto.Get(\"lng\")";
_sitiolong = (double)(BA.ObjectToNumber(_newpunto.Get((Object)("lng"))));
 //BA.debugLineNum = 396;BA.debugLine="Dim sitioindice As String = newpunto.Get(\"indi";
_sitioindice = BA.ObjectToString(_newpunto.Get((Object)("indice")));
 //BA.debugLineNum = 397;BA.debugLine="Dim sitiotiporio As String = newpunto.Get(\"tip";
_sitiotiporio = BA.ObjectToString(_newpunto.Get((Object)("tiporio")));
 //BA.debugLineNum = 398;BA.debugLine="Dim sitiocontribucion As String = newpunto.Get";
_sitiocontribucion = BA.ObjectToString(_newpunto.Get((Object)("username")));
 //BA.debugLineNum = 400;BA.debugLine="If sitiocontribucion.Contains(\"@\") Then";
if (true) break;

case 14:
//if
this.state = 17;
if (_sitiocontribucion.contains("@")) { 
this.state = 16;
}if (true) break;

case 16:
//C
this.state = 17;
 //BA.debugLineNum = 401;BA.debugLine="sitiocontribucion = sitiocontribucion.SubStri";
_sitiocontribucion = _sitiocontribucion.substring((int) (0),_sitiocontribucion.indexOf("@"));
 if (true) break;
;
 //BA.debugLineNum = 404;BA.debugLine="If sitiolat <> \"0\" And sitiolong <> \"0\" Then";

case 17:
//if
this.state = 36;
if (_sitiolat!=(double)(Double.parseDouble("0")) && _sitiolong!=(double)(Double.parseDouble("0"))) { 
this.state = 19;
}else {
this.state = 35;
}if (true) break;

case 19:
//C
this.state = 20;
 //BA.debugLineNum = 405;BA.debugLine="If sitioindice > 0 And sitioindice < 20 Then";
if (true) break;

case 20:
//if
this.state = 33;
if ((double)(Double.parseDouble(_sitioindice))>0 && (double)(Double.parseDouble(_sitioindice))<20) { 
this.state = 22;
}else if((double)(Double.parseDouble(_sitioindice))>20 && (double)(Double.parseDouble(_sitioindice))<40) { 
this.state = 24;
}else if((double)(Double.parseDouble(_sitioindice))>40 && (double)(Double.parseDouble(_sitioindice))<60) { 
this.state = 26;
}else if((double)(Double.parseDouble(_sitioindice))>60 && (double)(Double.parseDouble(_sitioindice))<80) { 
this.state = 28;
}else if((double)(Double.parseDouble(_sitioindice))>80 && (double)(Double.parseDouble(_sitioindice))<100) { 
this.state = 30;
}else {
this.state = 32;
}if (true) break;

case 22:
//C
this.state = 33;
 //BA.debugLineNum = 407;BA.debugLine="gmap.AddMarker2(sitiolat,sitiolong,sitioindi";
parent.mostCurrent._gmap.AddMarker2(_sitiolat,_sitiolong,_sitioindice,parent.mostCurrent._gmap.HUE_RED);
 if (true) break;

case 24:
//C
this.state = 33;
 //BA.debugLineNum = 410;BA.debugLine="gmap.AddMarker2(sitiolat,sitiolong,sitioindi";
parent.mostCurrent._gmap.AddMarker2(_sitiolat,_sitiolong,_sitioindice,parent.mostCurrent._gmap.HUE_ORANGE);
 if (true) break;

case 26:
//C
this.state = 33;
 //BA.debugLineNum = 413;BA.debugLine="gmap.AddMarker2(sitiolat,sitiolong,sitioindi";
parent.mostCurrent._gmap.AddMarker2(_sitiolat,_sitiolong,_sitioindice,parent.mostCurrent._gmap.HUE_YELLOW);
 if (true) break;

case 28:
//C
this.state = 33;
 //BA.debugLineNum = 416;BA.debugLine="gmap.AddMarker2(sitiolat,sitiolong,sitioindi";
parent.mostCurrent._gmap.AddMarker2(_sitiolat,_sitiolong,_sitioindice,parent.mostCurrent._gmap.HUE_GREEN);
 if (true) break;

case 30:
//C
this.state = 33;
 //BA.debugLineNum = 419;BA.debugLine="gmap.AddMarker2(sitiolat,sitiolong,sitioindi";
parent.mostCurrent._gmap.AddMarker2(_sitiolat,_sitiolong,_sitioindice,parent.mostCurrent._gmap.HUE_BLUE);
 if (true) break;

case 32:
//C
this.state = 33;
 //BA.debugLineNum = 422;BA.debugLine="gmap.AddMarker2(sitiolat,sitiolong,sitioindi";
parent.mostCurrent._gmap.AddMarker2(_sitiolat,_sitiolong,_sitioindice,parent.mostCurrent._gmap.HUE_AZURE);
 if (true) break;

case 33:
//C
this.state = 36;
;
 //BA.debugLineNum = 424;BA.debugLine="latlist.Add(newpunto.Get(\"lat\"))";
_latlist.Add(_newpunto.Get((Object)("lat")));
 //BA.debugLineNum = 425;BA.debugLine="lnglist.Add(newpunto.Get(\"lng\"))";
_lnglist.Add(_newpunto.Get((Object)("lng")));
 if (true) break;

case 35:
//C
this.state = 36;
 //BA.debugLineNum = 427;BA.debugLine="Log(\"Punto no agregado\")";
anywheresoftware.b4a.keywords.Common.LogImpl("035520584","Punto no agregado",0);
 if (true) break;

case 36:
//C
this.state = 50;
;
 if (true) break;
if (true) break;

case 37:
//C
this.state = 38;
;
 //BA.debugLineNum = 434;BA.debugLine="Dim GoogleMapsExtras1 As GoogleMapsExtras";
_googlemapsextras1 = new uk.co.martinpearman.b4a.googlemapsextras.GoogleMapsExtras();
 //BA.debugLineNum = 435;BA.debugLine="Dim CameraUpdateFactory1 As CameraUpdateFactory";
_cameraupdatefactory1 = new uk.co.martinpearman.b4a.com.google.android.gms.maps.CameraUpdateFactory();
 //BA.debugLineNum = 436;BA.debugLine="Dim CameraUpdate1 As CameraUpdate";
_cameraupdate1 = new uk.co.martinpearman.b4a.com.google.android.gms.maps.CameraUpdate();
 //BA.debugLineNum = 437;BA.debugLine="Dim llbounds As LatLngBounds";
_llbounds = new uk.co.martinpearman.b4a.com.google.android.gms.maps.model.LatLngBounds();
 //BA.debugLineNum = 438;BA.debugLine="Dim sw, ne As LatLng 'your southwest and northe";
_sw = new anywheresoftware.b4a.objects.MapFragmentWrapper.LatLngWrapper();
_ne = new anywheresoftware.b4a.objects.MapFragmentWrapper.LatLngWrapper();
 //BA.debugLineNum = 439;BA.debugLine="latlist.Sort(False)";
_latlist.Sort(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 440;BA.debugLine="lnglist.Sort(False)";
_lnglist.Sort(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 441;BA.debugLine="sw.Initialize(latlist.Get(0), lnglist.Get(0))";
_sw.Initialize((double)(BA.ObjectToNumber(_latlist.Get((int) (0)))),(double)(BA.ObjectToNumber(_lnglist.Get((int) (0)))));
 //BA.debugLineNum = 442;BA.debugLine="latlist.Sort(True)";
_latlist.Sort(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 443;BA.debugLine="lnglist.Sort(True)";
_lnglist.Sort(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 444;BA.debugLine="ne.Initialize(latlist.Get(0), lnglist.Get(0))";
_ne.Initialize((double)(BA.ObjectToNumber(_latlist.Get((int) (0)))),(double)(BA.ObjectToNumber(_lnglist.Get((int) (0)))));
 //BA.debugLineNum = 445;BA.debugLine="Log(GoogleMapsExtras1.GetProjection(gmap).GetVi";
anywheresoftware.b4a.keywords.Common.LogImpl("035520602",BA.ObjectToString(_googlemapsextras1.GetProjection((com.google.android.gms.maps.GoogleMap)(parent.mostCurrent._gmap.getObject())).GetVisibleRegion().getLatLngBounds()),0);
 //BA.debugLineNum = 446;BA.debugLine="Try";
if (true) break;

case 38:
//try
this.state = 43;
this.catchState = 42;
this.state = 40;
if (true) break;

case 40:
//C
this.state = 43;
this.catchState = 42;
 //BA.debugLineNum = 447;BA.debugLine="llbounds.Initialize(ne, sw)";
_llbounds.Initialize((com.google.android.gms.maps.model.LatLng)(_ne.getObject()),(com.google.android.gms.maps.model.LatLng)(_sw.getObject()));
 //BA.debugLineNum = 448;BA.debugLine="CameraUpdate1=CameraUpdateFactory1.NewLatLngBo";
_cameraupdate1 = _cameraupdatefactory1.NewLatLngBounds((com.google.android.gms.maps.model.LatLngBounds)(_llbounds.getObject()),(int) (10));
 //BA.debugLineNum = 449;BA.debugLine="GoogleMapsExtras1.MoveCamera(gmap, CameraUpdat";
_googlemapsextras1.MoveCamera((com.google.android.gms.maps.GoogleMap)(parent.mostCurrent._gmap.getObject()),(com.google.android.gms.maps.CameraUpdate)(_cameraupdate1.getObject()));
 if (true) break;

case 42:
//C
this.state = 43;
this.catchState = 0;
 //BA.debugLineNum = 451;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("035520608",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA)),0);
 if (true) break;
if (true) break;

case 43:
//C
this.state = 44;
this.catchState = 0;
;
 //BA.debugLineNum = 454;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 //BA.debugLineNum = 457;BA.debugLine="j.Release";
_j._release /*String*/ ();
 //BA.debugLineNum = 458;BA.debugLine="Return True";
if (true) {
anywheresoftware.b4a.keywords.Common.ReturnFromResumableSub(this,(Object)(anywheresoftware.b4a.keywords.Common.True));return;};
 if (true) break;

case 44:
//C
this.state = 47;
;
 if (true) break;

case 46:
//C
this.state = 47;
 //BA.debugLineNum = 462;BA.debugLine="Log(\"GetMapa not ok\")";
anywheresoftware.b4a.keywords.Common.LogImpl("035520619","GetMapa not ok",0);
 //BA.debugLineNum = 463;BA.debugLine="ToastMessageShow(\"Necesitas tener conexión a int";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Necesitas tener conexión a internet para ver el mapa, ¡intentalo luego!"),BA.ObjectToBoolean("No conectado"));
 //BA.debugLineNum = 464;BA.debugLine="j.Release";
_j._release /*String*/ ();
 //BA.debugLineNum = 465;BA.debugLine="Return False";
if (true) {
anywheresoftware.b4a.keywords.Common.ReturnFromResumableSub(this,(Object)(anywheresoftware.b4a.keywords.Common.False));return;};
 if (true) break;

case 47:
//C
this.state = -1;
;
 //BA.debugLineNum = 467;BA.debugLine="End Sub";
if (true) break;
}} 
       catch (Exception e0) {
			
if (catchState == 0)
    throw e0;
else {
    state = catchState;
processBA.setLastException(e0);}
            }
        }
    }
}
public static void  _jobdone(appear.pnud.preservamos.httpjob _j) throws Exception{
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
