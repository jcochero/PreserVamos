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
        
        BA.LogInfo("** Activity (frmlocalizacion) Create " + (isFirst ? "(first time)" : "") + " **");
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
public static uk.co.martinpearman.b4a.location.GeocoderWrapper _georef = null;
public static String _geopartido = "";
public static anywheresoftware.b4a.objects.B4XViewWrapper.XUI _xui = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbllat = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbllon = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btncontinuarlocalizacion = null;
public anywheresoftware.b4a.objects.MapFragmentWrapper.GoogleMapWrapper _gmap = null;
public anywheresoftware.b4a.objects.MapFragmentWrapper _mapfragment1 = null;
public anywheresoftware.b4a.objects.MapFragmentWrapper.MarkerWrapper _mymarker = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblmunicipioauto = null;
public appear.pnud.preservamos.b4xdialog _dialog = null;
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
public appear.pnud.preservamos.frmmapa _frmmapa = null;
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
 //BA.debugLineNum = 36;BA.debugLine="currentproject = Form_Reporte.currentproject";
parent._currentproject = parent.mostCurrent._form_reporte._currentproject /*String*/ ;
 //BA.debugLineNum = 38;BA.debugLine="tipoAmbiente = Form_Reporte.tipoAmbiente";
parent._tipoambiente = parent.mostCurrent._form_reporte._tipoambiente /*String*/ ;
 //BA.debugLineNum = 39;BA.debugLine="tipoDetect = \"GPSdetect\"";
parent._tipodetect = "GPSdetect";
 //BA.debugLineNum = 41;BA.debugLine="Activity.LoadLayout(\"Google_Map\")";
parent.mostCurrent._activity.LoadLayout("Google_Map",mostCurrent.activityBA);
 //BA.debugLineNum = 43;BA.debugLine="ProgressDialogShow2(\"Buscando tu ubicación...\", F";
anywheresoftware.b4a.keywords.Common.ProgressDialogShow2(mostCurrent.activityBA,BA.ObjectToCharSequence("Buscando tu ubicación..."),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 44;BA.debugLine="Wait For MapFragment1_Ready";
anywheresoftware.b4a.keywords.Common.WaitFor("mapfragment1_ready", processBA, this, null);
this.state = 11;
return;
case 11:
//C
this.state = 1;
;
 //BA.debugLineNum = 45;BA.debugLine="gmap = MapFragment1.GetMap";
parent.mostCurrent._gmap = parent.mostCurrent._mapfragment1.GetMap();
 //BA.debugLineNum = 46;BA.debugLine="gmap.MyLocationEnabled = True";
parent.mostCurrent._gmap.setMyLocationEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 48;BA.debugLine="If gmap.IsInitialized = False Then";
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
 //BA.debugLineNum = 49;BA.debugLine="ToastMessageShow(\"Error initializing map.\", True";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Error initializing map."),anywheresoftware.b4a.keywords.Common.True);
 if (true) break;

case 5:
//C
this.state = 6;
 //BA.debugLineNum = 51;BA.debugLine="Do While Not(gmap.MyLocation.IsInitialized)";
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
 //BA.debugLineNum = 52;BA.debugLine="Sleep(0)";
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
 //BA.debugLineNum = 54;BA.debugLine="CargarMapa";
_cargarmapa();
 if (true) break;

case 10:
//C
this.state = -1;
;
 //BA.debugLineNum = 56;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public static void  _mapfragment1_ready() throws Exception{
}
public static boolean  _activity_keypress(int _keycode) throws Exception{
 //BA.debugLineNum = 58;BA.debugLine="Sub Activity_KeyPress (KeyCode As Int) As Boolean";
 //BA.debugLineNum = 59;BA.debugLine="If KeyCode = KeyCodes.KEYCODE_BACK Then";
if (_keycode==anywheresoftware.b4a.keywords.Common.KeyCodes.KEYCODE_BACK) { 
 //BA.debugLineNum = 60;BA.debugLine="Activity.finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 61;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 };
 //BA.debugLineNum = 63;BA.debugLine="End Sub";
return false;
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 67;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 70;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 64;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 66;BA.debugLine="End Sub";
return "";
}
public static void  _btncontinuarlocalizacion_click() throws Exception{
ResumableSub_btnContinuarLocalizacion_Click rsub = new ResumableSub_btnContinuarLocalizacion_Click(null);
rsub.resume(processBA, null);
}
public static class ResumableSub_btnContinuarLocalizacion_Click extends BA.ResumableSub {
public ResumableSub_btnContinuarLocalizacion_Click(appear.pnud.preservamos.frmlocalizacion parent) {
this.parent = parent;
}
appear.pnud.preservamos.frmlocalizacion parent;
int _result = 0;
appear.pnud.preservamos.b4xinputtemplate _input = null;
anywheresoftware.b4a.objects.collections.Map _map1 = null;

@Override
public void resume(BA ba, Object[] result) throws Exception{

    while (true) {
        switch (state) {
            case -1:
return;

case 0:
//C
this.state = 1;
 //BA.debugLineNum = 176;BA.debugLine="If lblLat.IsInitialized = False Then";
if (true) break;

case 1:
//if
this.state = 4;
if (parent.mostCurrent._lbllat.IsInitialized()==anywheresoftware.b4a.keywords.Common.False) { 
this.state = 3;
}if (true) break;

case 3:
//C
this.state = 4;
 //BA.debugLineNum = 177;BA.debugLine="Return";
if (true) return ;
 if (true) break;
;
 //BA.debugLineNum = 179;BA.debugLine="If myMarker.IsInitialized = False Then";

case 4:
//if
this.state = 7;
if (parent.mostCurrent._mymarker.IsInitialized()==anywheresoftware.b4a.keywords.Common.False) { 
this.state = 6;
}if (true) break;

case 6:
//C
this.state = 7;
 //BA.debugLineNum = 180;BA.debugLine="ToastMessageShow(\"Ubicación no detectada, intent";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Ubicación no detectada, intente de nuevo!"),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 181;BA.debugLine="Return";
if (true) return ;
 if (true) break;

case 7:
//C
this.state = 8;
;
 //BA.debugLineNum = 184;BA.debugLine="lblLat.Text = myMarker.Position.Latitude";
parent.mostCurrent._lbllat.setText(BA.ObjectToCharSequence(parent.mostCurrent._mymarker.getPosition().getLatitude()));
 //BA.debugLineNum = 185;BA.debugLine="lblLon.Text = myMarker.Position.Longitude";
parent.mostCurrent._lbllon.setText(BA.ObjectToCharSequence(parent.mostCurrent._mymarker.getPosition().getLongitude()));
 //BA.debugLineNum = 187;BA.debugLine="If lblLat.Text <> \"\" And lblLat.Text <> \"0\" Then";
if (true) break;

case 8:
//if
this.state = 11;
if ((parent.mostCurrent._lbllat.getText()).equals("") == false && (parent.mostCurrent._lbllat.getText()).equals("0") == false) { 
this.state = 10;
}if (true) break;

case 10:
//C
this.state = 11;
 //BA.debugLineNum = 188;BA.debugLine="lblLat.Text = myMarker.Position.Latitude";
parent.mostCurrent._lbllat.setText(BA.ObjectToCharSequence(parent.mostCurrent._mymarker.getPosition().getLatitude()));
 //BA.debugLineNum = 189;BA.debugLine="lblLon.Text = myMarker.Position.Longitude";
parent.mostCurrent._lbllon.setText(BA.ObjectToCharSequence(parent.mostCurrent._mymarker.getPosition().getLongitude()));
 if (true) break;
;
 //BA.debugLineNum = 192;BA.debugLine="If lblLat.Text = \"\" Or lblLon.Text = \"\" Then";

case 11:
//if
this.state = 14;
if ((parent.mostCurrent._lbllat.getText()).equals("") || (parent.mostCurrent._lbllon.getText()).equals("")) { 
this.state = 13;
}if (true) break;

case 13:
//C
this.state = 14;
 //BA.debugLineNum = 193;BA.debugLine="ToastMessageShow(\"No se han detectado tus coorde";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("No se han detectado tus coordenadas, intenta de nuevo!"),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 194;BA.debugLine="Return";
if (true) return ;
 if (true) break;
;
 //BA.debugLineNum = 198;BA.debugLine="If Geopartido = \"\" Then";

case 14:
//if
this.state = 41;
if ((parent._geopartido).equals("")) { 
this.state = 16;
}if (true) break;

case 16:
//C
this.state = 17;
 //BA.debugLineNum = 199;BA.debugLine="Msgbox2Async(\"No se reconoció el municipio, o no";
anywheresoftware.b4a.keywords.Common.Msgbox2Async(BA.ObjectToCharSequence("No se reconoció el municipio, o no hay suficiente señal. Intenta escribir el nombre del municipio manualmente!"),BA.ObjectToCharSequence("Municipio"),"Intentar de nuevo","","Escribiré el nombre del municipio",(anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper(), (android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null)),processBA,anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 200;BA.debugLine="Wait For Msgbox_Result (Result As Int)";
anywheresoftware.b4a.keywords.Common.WaitFor("msgbox_result", processBA, this, null);
this.state = 59;
return;
case 59:
//C
this.state = 17;
_result = (Integer) result[0];
;
 //BA.debugLineNum = 201;BA.debugLine="If Result = DialogResponse.POSITIVE Then";
if (true) break;

case 17:
//if
this.state = 40;
if (_result==anywheresoftware.b4a.keywords.Common.DialogResponse.POSITIVE) { 
this.state = 19;
}else if(_result==anywheresoftware.b4a.keywords.Common.DialogResponse.NEGATIVE) { 
this.state = 21;
}if (true) break;

case 19:
//C
this.state = 40;
 //BA.debugLineNum = 203;BA.debugLine="Return";
if (true) return ;
 if (true) break;

case 21:
//C
this.state = 22;
 //BA.debugLineNum = 206;BA.debugLine="dialog.Initialize(Activity)";
parent.mostCurrent._dialog._initialize /*String*/ (mostCurrent.activityBA,(anywheresoftware.b4a.objects.B4XViewWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.B4XViewWrapper(), (java.lang.Object)(parent.mostCurrent._activity.getObject())));
 //BA.debugLineNum = 207;BA.debugLine="dialog.Title = \"Municipio\"";
parent.mostCurrent._dialog._title /*Object*/  = (Object)("Municipio");
 //BA.debugLineNum = 208;BA.debugLine="Dim input As B4XInputTemplate";
_input = new appear.pnud.preservamos.b4xinputtemplate();
 //BA.debugLineNum = 209;BA.debugLine="input.Initialize";
_input._initialize /*String*/ (mostCurrent.activityBA);
 //BA.debugLineNum = 210;BA.debugLine="input.lblTitle.Text = \"Escribe el nombre del mu";
_input._lbltitle /*anywheresoftware.b4a.objects.B4XViewWrapper*/ .setText(BA.ObjectToCharSequence("Escribe el nombre del municipio"));
 //BA.debugLineNum = 211;BA.debugLine="input.RegexPattern = \".+\" 'require at least one";
_input._regexpattern /*String*/  = ".+";
 //BA.debugLineNum = 212;BA.debugLine="input.TextField1.TextColor = Colors.Black";
_input._textfield1 /*anywheresoftware.b4a.objects.B4XViewWrapper*/ .setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 213;BA.debugLine="input.lblTitle.TextColor = Colors.DarkGray";
_input._lbltitle /*anywheresoftware.b4a.objects.B4XViewWrapper*/ .setTextColor(anywheresoftware.b4a.keywords.Common.Colors.DarkGray);
 //BA.debugLineNum = 214;BA.debugLine="input.TextField1.TextSize = 24";
_input._textfield1 /*anywheresoftware.b4a.objects.B4XViewWrapper*/ .setTextSize((float) (24));
 //BA.debugLineNum = 215;BA.debugLine="input.TextField1.Height = 50dip";
_input._textfield1 /*anywheresoftware.b4a.objects.B4XViewWrapper*/ .setHeight(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (50)));
 //BA.debugLineNum = 216;BA.debugLine="dialog.TitleBarColor = Colors.ARGB(255,76,159,5";
parent.mostCurrent._dialog._titlebarcolor /*int*/  = anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (76),(int) (159),(int) (56));
 //BA.debugLineNum = 217;BA.debugLine="dialog.BackgroundColor = Colors.White";
parent.mostCurrent._dialog._backgroundcolor /*int*/  = anywheresoftware.b4a.keywords.Common.Colors.White;
 //BA.debugLineNum = 219;BA.debugLine="Wait For (dialog.ShowTemplate(input, \"OK\", \"\",";
anywheresoftware.b4a.keywords.Common.WaitFor("complete", processBA, this, parent.mostCurrent._dialog._showtemplate /*anywheresoftware.b4a.keywords.Common.ResumableSubWrapper*/ ((Object)(_input),(Object)("OK"),(Object)(""),(Object)("Cancelar")));
this.state = 60;
return;
case 60:
//C
this.state = 22;
_result = (Integer) result[0];
;
 //BA.debugLineNum = 220;BA.debugLine="If Result = xui.DialogResponse_Positive Then";
if (true) break;

case 22:
//if
this.state = 39;
if (_result==parent._xui.DialogResponse_Positive) { 
this.state = 24;
}else {
this.state = 38;
}if (true) break;

case 24:
//C
this.state = 25;
 //BA.debugLineNum = 221;BA.debugLine="Geopartido = input.Text";
parent._geopartido = _input._text /*String*/ ;
 //BA.debugLineNum = 222;BA.debugLine="If origen = \"alerta\" Then";
if (true) break;

case 25:
//if
this.state = 36;
if ((parent._origen).equals("alerta")) { 
this.state = 27;
}else if((parent._origen).equals("main")) { 
this.state = 29;
}else if((parent._origen).equals("modulo_residuos")) { 
this.state = 31;
}else if((parent._origen).equals("modulo_hidro")) { 
this.state = 33;
}else {
this.state = 35;
}if (true) break;

case 27:
//C
this.state = 36;
 //BA.debugLineNum = 223;BA.debugLine="Alertas.Geopartido = Geopartido";
parent.mostCurrent._alertas._geopartido /*String*/  = parent._geopartido;
 if (true) break;

case 29:
//C
this.state = 36;
 //BA.debugLineNum = 225;BA.debugLine="Form_Main.Geopartido = Geopartido";
parent.mostCurrent._form_main._geopartido /*String*/  = parent._geopartido;
 if (true) break;

case 31:
//C
this.state = 36;
 //BA.debugLineNum = 227;BA.debugLine="mod_Residuos.Geopartido = Geopartido";
parent.mostCurrent._mod_residuos._geopartido /*String*/  = parent._geopartido;
 if (true) break;

case 33:
//C
this.state = 36;
 //BA.debugLineNum = 229;BA.debugLine="mod_Hidro.Geopartido = Geopartido";
parent.mostCurrent._mod_hidro._geopartido /*String*/  = parent._geopartido;
 if (true) break;

case 35:
//C
this.state = 36;
 //BA.debugLineNum = 231;BA.debugLine="Reporte_Envio.Geopartido = Geopartido";
parent.mostCurrent._reporte_envio._geopartido /*String*/  = parent._geopartido;
 if (true) break;

case 36:
//C
this.state = 39;
;
 if (true) break;

case 38:
//C
this.state = 39;
 //BA.debugLineNum = 234;BA.debugLine="Return";
if (true) return ;
 if (true) break;

case 39:
//C
this.state = 40;
;
 if (true) break;

case 40:
//C
this.state = 41;
;
 if (true) break;
;
 //BA.debugLineNum = 242;BA.debugLine="If origen = \"cambio\" Then";

case 41:
//if
this.state = 52;
if ((parent._origen).equals("cambio")) { 
this.state = 43;
}else if((parent._origen).equals("alerta")) { 
this.state = 45;
}else if((parent._origen).equals("modulo_residuos")) { 
this.state = 47;
}else if((parent._origen).equals("modulo_hidro")) { 
this.state = 49;
}else if((parent._origen).equals("main")) { 
this.state = 51;
}if (true) break;

case 43:
//C
this.state = 52;
 //BA.debugLineNum = 243;BA.debugLine="frmDatosSinEnviar.nuevalatlng = lblLat.Text & \"_";
parent.mostCurrent._frmdatossinenviar._nuevalatlng /*String*/  = parent.mostCurrent._lbllat.getText()+"_"+parent.mostCurrent._lbllon.getText();
 //BA.debugLineNum = 244;BA.debugLine="Activity.RemoveAllViews";
parent.mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 245;BA.debugLine="frmDatosSinEnviar.notificacion = False";
parent.mostCurrent._frmdatossinenviar._notificacion /*boolean*/  = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 246;BA.debugLine="CallSubDelayed(frmDatosAnteriores, \"CambiarUbica";
anywheresoftware.b4a.keywords.Common.CallSubDelayed(processBA,(Object)(parent.mostCurrent._frmdatosanteriores.getObject()),"CambiarUbicacion");
 //BA.debugLineNum = 247;BA.debugLine="Activity.Finish";
parent.mostCurrent._activity.Finish();
 //BA.debugLineNum = 248;BA.debugLine="Return";
if (true) return ;
 if (true) break;

case 45:
//C
this.state = 52;
 //BA.debugLineNum = 250;BA.debugLine="Alertas.Alerta_lat = lblLat.Text";
parent.mostCurrent._alertas._alerta_lat /*String*/  = parent.mostCurrent._lbllat.getText();
 //BA.debugLineNum = 251;BA.debugLine="Alertas.Alerta_lng = lblLon.Text";
parent.mostCurrent._alertas._alerta_lng /*String*/  = parent.mostCurrent._lbllon.getText();
 //BA.debugLineNum = 252;BA.debugLine="Activity.RemoveAllViews";
parent.mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 253;BA.debugLine="CallSubDelayed(Alertas, \"ActualizarCoordenadas\")";
anywheresoftware.b4a.keywords.Common.CallSubDelayed(processBA,(Object)(parent.mostCurrent._alertas.getObject()),"ActualizarCoordenadas");
 //BA.debugLineNum = 254;BA.debugLine="Activity.Finish";
parent.mostCurrent._activity.Finish();
 //BA.debugLineNum = 255;BA.debugLine="Return";
if (true) return ;
 if (true) break;

case 47:
//C
this.state = 52;
 //BA.debugLineNum = 257;BA.debugLine="mod_Residuos.lat = lblLat.Text";
parent.mostCurrent._mod_residuos._lat /*String*/  = parent.mostCurrent._lbllat.getText();
 //BA.debugLineNum = 258;BA.debugLine="mod_Residuos.lng = lblLon.Text";
parent.mostCurrent._mod_residuos._lng /*String*/  = parent.mostCurrent._lbllon.getText();
 //BA.debugLineNum = 259;BA.debugLine="CallSubDelayed(mod_Residuos, \"ActualizarCoordena";
anywheresoftware.b4a.keywords.Common.CallSubDelayed(processBA,(Object)(parent.mostCurrent._mod_residuos.getObject()),"ActualizarCoordenadas");
 //BA.debugLineNum = 260;BA.debugLine="Activity.RemoveAllViews";
parent.mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 261;BA.debugLine="Activity.Finish";
parent.mostCurrent._activity.Finish();
 //BA.debugLineNum = 262;BA.debugLine="Return";
if (true) return ;
 if (true) break;

case 49:
//C
this.state = 52;
 //BA.debugLineNum = 264;BA.debugLine="mod_Hidro.lat = lblLat.Text";
parent.mostCurrent._mod_hidro._lat /*String*/  = parent.mostCurrent._lbllat.getText();
 //BA.debugLineNum = 265;BA.debugLine="mod_Hidro.lng = lblLon.Text";
parent.mostCurrent._mod_hidro._lng /*String*/  = parent.mostCurrent._lbllon.getText();
 //BA.debugLineNum = 266;BA.debugLine="CallSubDelayed(mod_Hidro, \"ActualizarCoordenadas";
anywheresoftware.b4a.keywords.Common.CallSubDelayed(processBA,(Object)(parent.mostCurrent._mod_hidro.getObject()),"ActualizarCoordenadas");
 //BA.debugLineNum = 267;BA.debugLine="Activity.RemoveAllViews";
parent.mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 268;BA.debugLine="Activity.Finish";
parent.mostCurrent._activity.Finish();
 //BA.debugLineNum = 269;BA.debugLine="Return";
if (true) return ;
 if (true) break;

case 51:
//C
this.state = 52;
 //BA.debugLineNum = 271;BA.debugLine="Main.strUserOrg = Form_Main.Geopartido";
parent.mostCurrent._main._struserorg /*String*/  = parent.mostCurrent._form_main._geopartido /*String*/ ;
 //BA.debugLineNum = 272;BA.debugLine="Activity.RemoveAllViews";
parent.mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 273;BA.debugLine="CallSubDelayed(Form_Main, \"update_Municipio\")";
anywheresoftware.b4a.keywords.Common.CallSubDelayed(processBA,(Object)(parent.mostCurrent._form_main.getObject()),"update_Municipio");
 //BA.debugLineNum = 274;BA.debugLine="Activity.Finish";
parent.mostCurrent._activity.Finish();
 //BA.debugLineNum = 275;BA.debugLine="Return";
if (true) return ;
 if (true) break;

case 52:
//C
this.state = 53;
;
 //BA.debugLineNum = 280;BA.debugLine="Dim Map1 As Map";
_map1 = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 281;BA.debugLine="Map1.Initialize";
_map1.Initialize();
 //BA.debugLineNum = 282;BA.debugLine="Map1.Put(\"Id\", Main.currentproject)";
_map1.Put((Object)("Id"),(Object)(parent.mostCurrent._main._currentproject /*String*/ ));
 //BA.debugLineNum = 284;BA.debugLine="Main.latitud = lblLat.Text";
parent.mostCurrent._main._latitud /*String*/  = parent.mostCurrent._lbllat.getText();
 //BA.debugLineNum = 285;BA.debugLine="Main.longitud = lblLon.Text";
parent.mostCurrent._main._longitud /*String*/  = parent.mostCurrent._lbllon.getText();
 //BA.debugLineNum = 286;BA.debugLine="Main.dateandtime = DateTime.Date(DateTime.Now)";
parent.mostCurrent._main._dateandtime /*String*/  = anywheresoftware.b4a.keywords.Common.DateTime.Date(anywheresoftware.b4a.keywords.Common.DateTime.getNow());
 //BA.debugLineNum = 288;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"evals\", \"dec";
parent.mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,parent.mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals","decimallatitude",(Object)(parent.mostCurrent._lbllat.getText()),_map1);
 //BA.debugLineNum = 289;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"evals\", \"dec";
parent.mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,parent.mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals","decimallongitude",(Object)(parent.mostCurrent._lbllon.getText()),_map1);
 //BA.debugLineNum = 290;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"evals\", \"geo";
parent.mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,parent.mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals","georeferenceddate",(Object)(anywheresoftware.b4a.keywords.Common.DateTime.Date(anywheresoftware.b4a.keywords.Common.DateTime.getNow())),_map1);
 //BA.debugLineNum = 292;BA.debugLine="If tipoDetect = \"GPSdetect\" Then";
if (true) break;

case 53:
//if
this.state = 58;
if ((parent._tipodetect).equals("GPSdetect")) { 
this.state = 55;
}else if((parent._tipodetect).equals("MAPAdetect")) { 
this.state = 57;
}if (true) break;

case 55:
//C
this.state = 58;
 //BA.debugLineNum = 293;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"evals\", \"gp";
parent.mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,parent.mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals","gpsDetect",(Object)("si"),_map1);
 if (true) break;

case 57:
//C
this.state = 58;
 //BA.debugLineNum = 295;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"evals\", \"ma";
parent.mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,parent.mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals","mapaDetect",(Object)("si"),_map1);
 if (true) break;

case 58:
//C
this.state = -1;
;
 //BA.debugLineNum = 300;BA.debugLine="ProgressDialogShow2(\"Cargando cuestionario...\", F";
anywheresoftware.b4a.keywords.Common.ProgressDialogShow2(mostCurrent.activityBA,BA.ObjectToCharSequence("Cargando cuestionario..."),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 301;BA.debugLine="Activity.RemoveAllViews";
parent.mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 302;BA.debugLine="Load_Reporte_Ambientes";
_load_reporte_ambientes();
 //BA.debugLineNum = 304;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public static void  _msgbox_result(int _result) throws Exception{
}
public static void  _complete(int _result) throws Exception{
}
public static String  _cargarmapa() throws Exception{
anywheresoftware.b4a.objects.MapFragmentWrapper.LatLngWrapper _mylocation = null;
double _geolatitude = 0;
double _geolongitude = 0;
anywheresoftware.b4a.objects.MapFragmentWrapper.CameraPositionWrapper _cp = null;
uk.co.martinpearman.b4a.googlemapsextras.GoogleMapsExtras _googlemapsextras1 = null;
uk.co.martinpearman.b4a.com.google.android.gms.maps.googlemap.OnMarkerDragListener _onmarkerdraglistener1 = null;
 //BA.debugLineNum = 73;BA.debugLine="Sub CargarMapa";
 //BA.debugLineNum = 75;BA.debugLine="If origen <> \"municipio\" Then";
if ((_origen).equals("municipio") == false) { 
 //BA.debugLineNum = 76;BA.debugLine="Dim myLocation As LatLng";
_mylocation = new anywheresoftware.b4a.objects.MapFragmentWrapper.LatLngWrapper();
 //BA.debugLineNum = 77;BA.debugLine="myLocation = gmap.MyLocation";
_mylocation = mostCurrent._gmap.getMyLocation();
 //BA.debugLineNum = 78;BA.debugLine="georef.Initialize(\"georef\")";
_georef.Initialize(processBA,"georef");
 //BA.debugLineNum = 79;BA.debugLine="Dim GeoLatitude, GeoLongitude As Double";
_geolatitude = 0;
_geolongitude = 0;
 //BA.debugLineNum = 80;BA.debugLine="GeoLatitude = myLocation.Latitude";
_geolatitude = _mylocation.getLatitude();
 //BA.debugLineNum = 81;BA.debugLine="GeoLongitude = myLocation.Longitude";
_geolongitude = _mylocation.getLongitude();
 //BA.debugLineNum = 82;BA.debugLine="georef.GetFromLocation(GeoLatitude, GeoLongitude";
_georef.GetFromLocation(processBA,_geolatitude,_geolongitude,(int) (10),anywheresoftware.b4a.keywords.Common.Null);
 //BA.debugLineNum = 84;BA.debugLine="If myLocation.IsInitialized = False Then";
if (_mylocation.IsInitialized()==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 85;BA.debugLine="myLocation.Initialize(\"-34.9204950\",\"-57.953566";
_mylocation.Initialize((double)(Double.parseDouble("-34.9204950")),(double)(Double.parseDouble("-57.9535660")));
 };
 //BA.debugLineNum = 87;BA.debugLine="Dim cp As CameraPosition";
_cp = new anywheresoftware.b4a.objects.MapFragmentWrapper.CameraPositionWrapper();
 //BA.debugLineNum = 88;BA.debugLine="cp.Initialize(myLocation.Latitude, myLocation.Lo";
_cp.Initialize(_mylocation.getLatitude(),_mylocation.getLongitude(),(float) (16));
 //BA.debugLineNum = 89;BA.debugLine="gmap.AnimateCamera(cp)";
mostCurrent._gmap.AnimateCamera((com.google.android.gms.maps.model.CameraPosition)(_cp.getObject()));
 //BA.debugLineNum = 90;BA.debugLine="gmap.MapType=gmap.MAP_TYPE_HYBRID";
mostCurrent._gmap.setMapType(mostCurrent._gmap.MAP_TYPE_HYBRID);
 //BA.debugLineNum = 93;BA.debugLine="myMarker=gmap.AddMarker(myLocation.Latitude, myL";
mostCurrent._mymarker = mostCurrent._gmap.AddMarker(_mylocation.getLatitude(),_mylocation.getLongitude(),"Arrastra el punto");
 //BA.debugLineNum = 94;BA.debugLine="myMarker.Draggable=True";
mostCurrent._mymarker.setDraggable(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 95;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 //BA.debugLineNum = 97;BA.debugLine="Dim GoogleMapsExtras1 As GoogleMapsExtras";
_googlemapsextras1 = new uk.co.martinpearman.b4a.googlemapsextras.GoogleMapsExtras();
 //BA.debugLineNum = 99;BA.debugLine="Dim OnMarkerDragListener1 As OnMarkerDragListene";
_onmarkerdraglistener1 = new uk.co.martinpearman.b4a.com.google.android.gms.maps.googlemap.OnMarkerDragListener();
 //BA.debugLineNum = 100;BA.debugLine="OnMarkerDragListener1.Initialize(\"OnMarkerDragLi";
_onmarkerdraglistener1.Initialize(mostCurrent.activityBA,"OnMarkerDragListener1");
 //BA.debugLineNum = 101;BA.debugLine="If OnMarkerDragListener1.IsInitialized Then";
if (_onmarkerdraglistener1.IsInitialized()) { 
 //BA.debugLineNum = 102;BA.debugLine="GoogleMapsExtras1.SetOnMarkerDragListener(gmap,";
_googlemapsextras1.SetOnMarkerDragListener((com.google.android.gms.maps.GoogleMap)(mostCurrent._gmap.getObject()),(com.google.android.gms.maps.GoogleMap.OnMarkerDragListener)(_onmarkerdraglistener1.getObject()));
 }else {
 //BA.debugLineNum = 104;BA.debugLine="Log(\"OnMarkerDragListener1 is not initialized -";
anywheresoftware.b4a.keywords.Common.LogImpl("446923807","OnMarkerDragListener1 is not initialized - check that the Activity contains at least one callback Sub",0);
 };
 }else {
 };
 //BA.debugLineNum = 112;BA.debugLine="End Sub";
return "";
}
public static String  _georef_geocodedone(uk.co.martinpearman.b4a.location.AddressWrapper[] _results,Object _tag) throws Exception{
uk.co.martinpearman.b4a.location.AddressWrapper _address1 = null;
int _i = 0;
 //BA.debugLineNum = 114;BA.debugLine="Sub georef_GeocodeDone(Results() As Address, Tag A";
 //BA.debugLineNum = 115;BA.debugLine="If Results.Length>0 Then";
if (_results.length>0) { 
 //BA.debugLineNum = 116;BA.debugLine="Dim Address1 As Address";
_address1 = new uk.co.martinpearman.b4a.location.AddressWrapper();
 //BA.debugLineNum = 117;BA.debugLine="Dim i As Int";
_i = 0;
 //BA.debugLineNum = 118;BA.debugLine="For i=0 To Results.Length-1";
{
final int step4 = 1;
final int limit4 = (int) (_results.length-1);
_i = (int) (0) ;
for (;_i <= limit4 ;_i = _i + step4 ) {
 //BA.debugLineNum = 119;BA.debugLine="Address1=Results(i)";
_address1 = _results[_i];
 //BA.debugLineNum = 122;BA.debugLine="Log(Address1)";
anywheresoftware.b4a.keywords.Common.LogImpl("446989320",BA.ObjectToString(_address1),0);
 //BA.debugLineNum = 123;BA.debugLine="Try";
try { //BA.debugLineNum = 124;BA.debugLine="If Address1.SubAdminArea <> \"\" And Address1.Su";
if ((_address1.getSubAdminArea()).equals("") == false && _address1.getSubAdminArea()!= null) { 
 //BA.debugLineNum = 125;BA.debugLine="Log(\"Subadminarea:\" & Address1.SubAdminArea)";
anywheresoftware.b4a.keywords.Common.LogImpl("446989323","Subadminarea:"+_address1.getSubAdminArea(),0);
 //BA.debugLineNum = 126;BA.debugLine="Log(\"Locality:\" & Address1.Locality)";
anywheresoftware.b4a.keywords.Common.LogImpl("446989324","Locality:"+_address1.getLocality(),0);
 //BA.debugLineNum = 127;BA.debugLine="Geopartido = Address1.SubAdminArea";
_geopartido = _address1.getSubAdminArea();
 //BA.debugLineNum = 128;BA.debugLine="If Address1.SubAdminArea = \"Colón\" Then";
if ((_address1.getSubAdminArea()).equals("Colón")) { 
 //BA.debugLineNum = 130;BA.debugLine="If Address1.Locality <> Null Then";
if (_address1.getLocality()!= null) { 
 //BA.debugLineNum = 131;BA.debugLine="If Address1.Locality = \"La Granja\" Then";
if ((_address1.getLocality()).equals("La Granja")) { 
 //BA.debugLineNum = 132;BA.debugLine="Geopartido = \"La Granja\"";
_geopartido = "La Granja";
 }else {
 //BA.debugLineNum = 134;BA.debugLine="Geopartido = Address1.SubAdminArea";
_geopartido = _address1.getSubAdminArea();
 };
 }else {
 //BA.debugLineNum = 137;BA.debugLine="Geopartido = Address1.SubAdminArea";
_geopartido = _address1.getSubAdminArea();
 };
 };
 //BA.debugLineNum = 140;BA.debugLine="lblMunicipioAuto.Text = \"Municipio:\" & Geopar";
mostCurrent._lblmunicipioauto.setText(BA.ObjectToCharSequence("Municipio:"+_geopartido));
 //BA.debugLineNum = 141;BA.debugLine="If origen = \"alerta\" Then";
if ((_origen).equals("alerta")) { 
 //BA.debugLineNum = 142;BA.debugLine="Alertas.Geopartido = Geopartido";
mostCurrent._alertas._geopartido /*String*/  = _geopartido;
 }else if((_origen).equals("main")) { 
 //BA.debugLineNum = 144;BA.debugLine="Form_Main.Geopartido = Geopartido";
mostCurrent._form_main._geopartido /*String*/  = _geopartido;
 }else if((_origen).equals("modulo_residuos")) { 
 //BA.debugLineNum = 146;BA.debugLine="mod_Residuos.Geopartido = Geopartido";
mostCurrent._mod_residuos._geopartido /*String*/  = _geopartido;
 }else if((_origen).equals("modulo_hidro")) { 
 //BA.debugLineNum = 148;BA.debugLine="mod_Hidro.Geopartido = Geopartido";
mostCurrent._mod_hidro._geopartido /*String*/  = _geopartido;
 }else {
 //BA.debugLineNum = 150;BA.debugLine="Reporte_Envio.Geopartido = Geopartido";
mostCurrent._reporte_envio._geopartido /*String*/  = _geopartido;
 };
 //BA.debugLineNum = 153;BA.debugLine="Exit";
if (true) break;
 };
 } 
       catch (Exception e38) {
			processBA.setLastException(e38); //BA.debugLineNum = 157;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("446989355",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA)),0);
 //BA.debugLineNum = 158;BA.debugLine="Geopartido = \"\"";
_geopartido = "";
 //BA.debugLineNum = 159;BA.debugLine="lblMunicipioAuto.Text = \"Municipio:\" & Geopart";
mostCurrent._lblmunicipioauto.setText(BA.ObjectToCharSequence("Municipio:"+_geopartido));
 //BA.debugLineNum = 160;BA.debugLine="ToastMessageShow(\"No se reconoció el municipio";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("No se reconoció el municipio, o no hay suficiente señal"),anywheresoftware.b4a.keywords.Common.False);
 };
 }
};
 }else {
 //BA.debugLineNum = 167;BA.debugLine="Geopartido = \"\"";
_geopartido = "";
 //BA.debugLineNum = 168;BA.debugLine="lblMunicipioAuto.Text = \"Municipio:\" & Geopartid";
mostCurrent._lblmunicipioauto.setText(BA.ObjectToCharSequence("Municipio:"+_geopartido));
 //BA.debugLineNum = 169;BA.debugLine="ToastMessageShow(\"No se reconoció el municipio,";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("No se reconoció el municipio, o no hay suficiente señal"),anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 172;BA.debugLine="End Sub";
return "";
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 20;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 22;BA.debugLine="Private lblLat As Label";
mostCurrent._lbllat = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 23;BA.debugLine="Private lblLon As Label";
mostCurrent._lbllon = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 24;BA.debugLine="Private btnContinuarLocalizacion As Button";
mostCurrent._btncontinuarlocalizacion = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 27;BA.debugLine="Private gmap As GoogleMap";
mostCurrent._gmap = new anywheresoftware.b4a.objects.MapFragmentWrapper.GoogleMapWrapper();
 //BA.debugLineNum = 28;BA.debugLine="Private MapFragment1 As MapFragment";
mostCurrent._mapfragment1 = new anywheresoftware.b4a.objects.MapFragmentWrapper();
 //BA.debugLineNum = 29;BA.debugLine="Dim myMarker As Marker";
mostCurrent._mymarker = new anywheresoftware.b4a.objects.MapFragmentWrapper.MarkerWrapper();
 //BA.debugLineNum = 30;BA.debugLine="Private lblMunicipioAuto As Label";
mostCurrent._lblmunicipioauto = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 32;BA.debugLine="Private dialog As B4XDialog";
mostCurrent._dialog = new appear.pnud.preservamos.b4xdialog();
 //BA.debugLineNum = 33;BA.debugLine="End Sub";
return "";
}
public static String  _load_reporte_ambientes() throws Exception{
 //BA.debugLineNum = 312;BA.debugLine="Sub Load_Reporte_Ambientes";
 //BA.debugLineNum = 314;BA.debugLine="If tipoAmbiente = \"llanura\" Then";
if ((_tipoambiente).equals("llanura")) { 
 //BA.debugLineNum = 315;BA.debugLine="StartActivity(Reporte_Habitat_Rio)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._reporte_habitat_rio.getObject()));
 //BA.debugLineNum = 316;BA.debugLine="Reporte_Habitat_Rio.tiporio = \"llanura\"";
mostCurrent._reporte_habitat_rio._tiporio /*String*/  = "llanura";
 }else if((_tipoambiente).equals("laguna")) { 
 //BA.debugLineNum = 318;BA.debugLine="StartActivity(Reporte_Habitat_Laguna)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._reporte_habitat_laguna.getObject()));
 //BA.debugLineNum = 319;BA.debugLine="Reporte_Habitat_Laguna.tiporio = \"laguna\"";
mostCurrent._reporte_habitat_laguna._tiporio /*String*/  = "laguna";
 }else if((_tipoambiente).equals("sierras")) { 
 //BA.debugLineNum = 321;BA.debugLine="StartActivity(Reporte_Habitat_Rio_Sierras)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._reporte_habitat_rio_sierras.getObject()));
 //BA.debugLineNum = 322;BA.debugLine="Reporte_Habitat_Rio_Sierras.tiporio = \"sierras\"";
mostCurrent._reporte_habitat_rio_sierras._tiporio /*String*/  = "sierras";
 };
 //BA.debugLineNum = 324;BA.debugLine="Activity.finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 325;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 327;BA.debugLine="End Sub";
return "";
}
public static String  _onmarkerdraglistener1_drag(anywheresoftware.b4a.objects.MapFragmentWrapper.MarkerWrapper _marker1) throws Exception{
 //BA.debugLineNum = 331;BA.debugLine="Sub OnMarkerDragListener1_Drag(Marker1 As Marker)";
 //BA.debugLineNum = 332;BA.debugLine="End Sub";
return "";
}
public static String  _onmarkerdraglistener1_dragend(anywheresoftware.b4a.objects.MapFragmentWrapper.MarkerWrapper _marker1) throws Exception{
double _geolatitude = 0;
double _geolongitude = 0;
 //BA.debugLineNum = 334;BA.debugLine="Sub OnMarkerDragListener1_DragEnd(Marker1 As Marke";
 //BA.debugLineNum = 336;BA.debugLine="lblLat.Text = myMarker.Position.Latitude";
mostCurrent._lbllat.setText(BA.ObjectToCharSequence(mostCurrent._mymarker.getPosition().getLatitude()));
 //BA.debugLineNum = 337;BA.debugLine="lblLon.Text = myMarker.Position.Longitude";
mostCurrent._lbllon.setText(BA.ObjectToCharSequence(mostCurrent._mymarker.getPosition().getLongitude()));
 //BA.debugLineNum = 338;BA.debugLine="Dim GeoLatitude, GeoLongitude As Double";
_geolatitude = 0;
_geolongitude = 0;
 //BA.debugLineNum = 339;BA.debugLine="GeoLatitude = myMarker.Position.Latitude";
_geolatitude = mostCurrent._mymarker.getPosition().getLatitude();
 //BA.debugLineNum = 340;BA.debugLine="GeoLongitude = myMarker.Position.Longitude";
_geolongitude = mostCurrent._mymarker.getPosition().getLongitude();
 //BA.debugLineNum = 342;BA.debugLine="georef.GetFromLocation(GeoLatitude, GeoLongitude,";
_georef.GetFromLocation(processBA,_geolatitude,_geolongitude,(int) (10),anywheresoftware.b4a.keywords.Common.Null);
 //BA.debugLineNum = 344;BA.debugLine="End Sub";
return "";
}
public static String  _onmarkerdraglistener1_dragstart(anywheresoftware.b4a.objects.MapFragmentWrapper.MarkerWrapper _marker1) throws Exception{
 //BA.debugLineNum = 346;BA.debugLine="Sub OnMarkerDragListener1_DragStart(Marker1 As Mar";
 //BA.debugLineNum = 348;BA.debugLine="End Sub";
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
 //BA.debugLineNum = 14;BA.debugLine="Dim georef As Geocoder";
_georef = new uk.co.martinpearman.b4a.location.GeocoderWrapper();
 //BA.debugLineNum = 15;BA.debugLine="Dim Geopartido As String";
_geopartido = "";
 //BA.debugLineNum = 18;BA.debugLine="Private xui As XUI";
_xui = new anywheresoftware.b4a.objects.B4XViewWrapper.XUI();
 //BA.debugLineNum = 19;BA.debugLine="End Sub";
return "";
}
}
