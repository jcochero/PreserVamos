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

public class main extends Activity implements B4AActivity{
	public static main mostCurrent;
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
			processBA = new BA(this.getApplicationContext(), null, null, "appear.pnud.preservamos", "appear.pnud.preservamos.main");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
            
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (main).");
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
		activityBA = new BA(this, layout, processBA, "appear.pnud.preservamos", "appear.pnud.preservamos.main");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "appear.pnud.preservamos.main", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density, mostCurrent);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (main) Create " + (isFirst ? "(first time)" : "") + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (main) Resume **");
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
		return main.class;
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
            BA.LogInfo("** Activity (main) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
        else
            BA.LogInfo("** Activity (main) Pause event (activity is not paused). **");
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
            main mc = mostCurrent;
			if (mc == null || mc != activity.get())
				return;
			processBA.setActivityPaused(false);
            BA.LogInfo("** Activity (main) Resume **");
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
public static String _deviceid = "";
public static String _lang = "";
public static String _username = "";
public static String _pass = "";
public static boolean _hayinternet = false;
public static String _serverversion = "";
public static String _servernewstitulo = "";
public static String _servernewstext = "";
public static String _versionactual = "";
public static String _server_partidos_version = "";
public static String _local_partidos_version = "";
public static String _serverpath = "";
public static String _serverconnectionfolder = "";
public static String _struserid = "";
public static String _strusername = "";
public static String _struserfullname = "";
public static String _struserlocation = "";
public static String _struseremail = "";
public static String _struserorg = "";
public static String _strusertipousuario = "";
public static String _userfechadenacimiento = "";
public static String _usersexo = "";
public static String _numfotosok = "";
public static String _numevalsok = "";
public static String _msjprivadouser = "";
public static String _userlang = "";
public static String _evalsok = "";
public static String _evalscorregidas = "";
public static String _evalsinvalidas = "";
public static String _evalssospechosas = "";
public static String _validaciontotal = "";
public static String _usercategoria = "";
public static int _puntos_markers = 0;
public static int _puntos_evals_residuos = 0;
public static int _puntos_evals_hidro = 0;
public static anywheresoftware.b4a.objects.collections.Map _user_markers = null;
public static anywheresoftware.b4a.objects.collections.Map _user_evals_residuos = null;
public static anywheresoftware.b4a.objects.collections.Map _user_evals_hidro = null;
public static anywheresoftware.b4a.objects.collections.List _user_markers_list = null;
public static anywheresoftware.b4a.objects.collections.List _user_evals_residuos_list = null;
public static anywheresoftware.b4a.objects.collections.List _user_evals_hidro_list = null;
public static int _puntos_markers_llanura = 0;
public static int _puntos_markers_sierras = 0;
public static int _puntos_markers_laguna = 0;
public static int _puntos_evals_residuos_llanura = 0;
public static int _puntos_evals_residuos_sierras = 0;
public static int _puntos_evals_residuos_laguna = 0;
public static int _puntos_evals_hidro_llanura = 0;
public static int _puntos_evals_hidro_sierras = 0;
public static int _puntos_evals_hidro_laguna = 0;
public static String _puntostotales = "";
public static String _puntosnumfotos = "";
public static String _puntosnumevals = "";
public static String _numriollanura = "";
public static String _numriomontana = "";
public static String _numlaguna = "";
public static String _numestuario = "";
public static String _numshares = "";
public static String _latitud = "";
public static String _longitud = "";
public static String _dateandtime = "";
public static String _currentecoregion = "";
public static String _currentsubecoregion = "";
public static String _currentproject = "";
public static anywheresoftware.b4a.objects.RuntimePermissions _rp = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblestado = null;
public anywheresoftware.b4a.sql.SQL.CursorWrapper _cursor1 = null;
public appear.pnud.preservamos.b4xgifview _logo_load_gif = null;
public b4a.example.dateutils _dateutils = null;
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
public appear.pnud.preservamos.frmmapa _frmmapa = null;
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

public static boolean isAnyActivityVisible() {
    boolean vis = false;
vis = vis | (main.mostCurrent != null);
vis = vis | (form_main.mostCurrent != null);
vis = vis | (form_reporte.mostCurrent != null);
vis = vis | (frmlocalizacion.mostCurrent != null);
vis = vis | (reporte_habitat_rio.mostCurrent != null);
vis = vis | (frmdatossinenviar.mostCurrent != null);
vis = vis | (reporte_envio.mostCurrent != null);
vis = vis | (alerta_fotos.mostCurrent != null);
vis = vis | (alertas.mostCurrent != null);
vis = vis | (aprender_muestreo.mostCurrent != null);
vis = vis | (frmabout.mostCurrent != null);
vis = vis | (frmdatosanteriores.mostCurrent != null);
vis = vis | (frmeditprofile.mostCurrent != null);
vis = vis | (frmfelicitaciones.mostCurrent != null);
vis = vis | (frmmapa.mostCurrent != null);
vis = vis | (frmmunicipioestadisticas.mostCurrent != null);
vis = vis | (frmpoliticadatos.mostCurrent != null);
vis = vis | (frmtiporeporte.mostCurrent != null);
vis = vis | (inatcheck.mostCurrent != null);
vis = vis | (mod_hidro.mostCurrent != null);
vis = vis | (mod_hidro_fotos.mostCurrent != null);
vis = vis | (mod_residuos.mostCurrent != null);
vis = vis | (mod_residuos_fotos.mostCurrent != null);
vis = vis | (reporte_fotos.mostCurrent != null);
vis = vis | (reporte_habitat_laguna.mostCurrent != null);
vis = vis | (reporte_habitat_rio_sierras.mostCurrent != null);
vis = vis | (reporte_habitat_rio_sierras_bu.mostCurrent != null);
vis = vis | (character_creation.mostCurrent != null);
vis = vis | (register.mostCurrent != null);
return vis;}
public static void  _activity_create(boolean _firsttime) throws Exception{
ResumableSub_Activity_Create rsub = new ResumableSub_Activity_Create(null,_firsttime);
rsub.resume(processBA, null);
}
public static class ResumableSub_Activity_Create extends BA.ResumableSub {
public ResumableSub_Activity_Create(appear.pnud.preservamos.main parent,boolean _firsttime) {
this.parent = parent;
this._firsttime = _firsttime;
}
appear.pnud.preservamos.main parent;
boolean _firsttime;
anywheresoftware.b4a.phone.Phone _p = null;
boolean _completed = false;
String _permission = "";
boolean _result = false;
Object[] group15;
int index15;
int groupLen15;

@Override
public void resume(BA ba, Object[] result) throws Exception{

    while (true) {
        switch (state) {
            case -1:
return;

case 0:
//C
this.state = 1;
 //BA.debugLineNum = 140;BA.debugLine="Dim p As Phone";
_p = new anywheresoftware.b4a.phone.Phone();
 //BA.debugLineNum = 141;BA.debugLine="p.SetScreenOrientation(1)";
_p.SetScreenOrientation(processBA,(int) (1));
 //BA.debugLineNum = 144;BA.debugLine="Sleep(500)";
anywheresoftware.b4a.keywords.Common.Sleep(mostCurrent.activityBA,this,(int) (500));
this.state = 15;
return;
case 15:
//C
this.state = 1;
;
 //BA.debugLineNum = 148;BA.debugLine="Wait For (BuscarConfiguracion) Complete (Complete";
anywheresoftware.b4a.keywords.Common.WaitFor("complete", processBA, this, _buscarconfiguracion());
this.state = 16;
return;
case 16:
//C
this.state = 1;
_completed = (Boolean) result[0];
;
 //BA.debugLineNum = 152;BA.debugLine="Starter.dbdir = DBUtils.CopyDBFromAssets(\"preserv";
parent.mostCurrent._starter._dbdir /*String*/  = parent.mostCurrent._dbutils._copydbfromassets /*String*/ (mostCurrent.activityBA,"preservamosdb.db");
 //BA.debugLineNum = 155;BA.debugLine="Starter.modulo_residuos_dbdir = DBUtils.CopyDBFro";
parent.mostCurrent._starter._modulo_residuos_dbdir /*String*/  = parent.mostCurrent._dbutils._copydbfromassets /*String*/ (mostCurrent.activityBA,"preservamos_residuos_db.db");
 //BA.debugLineNum = 157;BA.debugLine="Starter.modulo_hidro_dbdir = DBUtils.CopyDBFromAs";
parent.mostCurrent._starter._modulo_hidro_dbdir /*String*/  = parent.mostCurrent._dbutils._copydbfromassets /*String*/ (mostCurrent.activityBA,"preservamos_hidro_db.db");
 //BA.debugLineNum = 160;BA.debugLine="Starter.savedir = rp.GetSafeDirDefaultExternal(\"\"";
parent.mostCurrent._starter._savedir /*String*/  = parent._rp.GetSafeDirDefaultExternal("");
 //BA.debugLineNum = 163;BA.debugLine="If File.Exists(Starter.savedir & \"/PreserVamos/\",";
if (true) break;

case 1:
//if
this.state = 4;
if (anywheresoftware.b4a.keywords.Common.File.Exists(parent.mostCurrent._starter._savedir /*String*/ +"/PreserVamos/","")==anywheresoftware.b4a.keywords.Common.False) { 
this.state = 3;
}if (true) break;

case 3:
//C
this.state = 4;
 //BA.debugLineNum = 164;BA.debugLine="File.MakeDir(Starter.savedir, \"PreserVamos\")";
anywheresoftware.b4a.keywords.Common.File.MakeDir(parent.mostCurrent._starter._savedir /*String*/ ,"PreserVamos");
 if (true) break;
;
 //BA.debugLineNum = 170;BA.debugLine="If File.Exists(Starter.savedir & \"/PreserVamos/se";

case 4:
//if
this.state = 7;
if (anywheresoftware.b4a.keywords.Common.File.Exists(parent.mostCurrent._starter._savedir /*String*/ +"/PreserVamos/sent/","")==anywheresoftware.b4a.keywords.Common.False) { 
this.state = 6;
}if (true) break;

case 6:
//C
this.state = 7;
 //BA.debugLineNum = 171;BA.debugLine="File.MakeDir(Starter.savedir & \"/PreserVamos/\",";
anywheresoftware.b4a.keywords.Common.File.MakeDir(parent.mostCurrent._starter._savedir /*String*/ +"/PreserVamos/","sent");
 if (true) break;
;
 //BA.debugLineNum = 174;BA.debugLine="For Each permission As String In Array(rp.PERMISS";

case 7:
//for
this.state = 14;
group15 = new Object[]{(Object)(parent._rp.PERMISSION_ACCESS_FINE_LOCATION),(Object)(parent._rp.PERMISSION_CAMERA)};
index15 = 0;
groupLen15 = group15.length;
this.state = 17;
if (true) break;

case 17:
//C
this.state = 14;
if (index15 < groupLen15) {
this.state = 9;
_permission = BA.ObjectToString(group15[index15]);}
if (true) break;

case 18:
//C
this.state = 17;
index15++;
if (true) break;

case 9:
//C
this.state = 10;
 //BA.debugLineNum = 175;BA.debugLine="rp.CheckAndRequest(permission)";
parent._rp.CheckAndRequest(processBA,_permission);
 //BA.debugLineNum = 176;BA.debugLine="Wait For Activity_PermissionResult (permission A";
anywheresoftware.b4a.keywords.Common.WaitFor("activity_permissionresult", processBA, this, null);
this.state = 19;
return;
case 19:
//C
this.state = 10;
_permission = (String) result[0];
_result = (Boolean) result[1];
;
 //BA.debugLineNum = 177;BA.debugLine="If Result = False Then";
if (true) break;

case 10:
//if
this.state = 13;
if (_result==anywheresoftware.b4a.keywords.Common.False) { 
this.state = 12;
}if (true) break;

case 12:
//C
this.state = 13;
 //BA.debugLineNum = 178;BA.debugLine="ToastMessageShow(\"Para usar la app, se necesita";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Para usar la app, se necesitan permisos para usar la cámara, el GPS y para guardar archivos en tu móvil"),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 179;BA.debugLine="Activity.Finish";
parent.mostCurrent._activity.Finish();
 //BA.debugLineNum = 180;BA.debugLine="Return";
if (true) return ;
 if (true) break;

case 13:
//C
this.state = 18;
;
 if (true) break;
if (true) break;

case 14:
//C
this.state = -1;
;
 //BA.debugLineNum = 183;BA.debugLine="Sleep(1500)";
anywheresoftware.b4a.keywords.Common.Sleep(mostCurrent.activityBA,this,(int) (1500));
this.state = 20;
return;
case 20:
//C
this.state = -1;
;
 //BA.debugLineNum = 185;BA.debugLine="Activity.RemoveAllViews";
parent.mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 186;BA.debugLine="Activity.Finish";
parent.mostCurrent._activity.Finish();
 //BA.debugLineNum = 187;BA.debugLine="StartActivity(Form_Main)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(parent.mostCurrent._form_main.getObject()));
 //BA.debugLineNum = 190;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public static void  _complete(boolean _completed) throws Exception{
}
public static void  _activity_permissionresult(String _permission,boolean _result) throws Exception{
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 196;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 198;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 191;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 193;BA.debugLine="CheckForGooglePlayServices";
_checkforgoogleplayservices();
 //BA.debugLineNum = 194;BA.debugLine="End Sub";
return "";
}
public static String  _btnoffline_click() throws Exception{
 //BA.debugLineNum = 281;BA.debugLine="Sub btnOffline_Click";
 //BA.debugLineNum = 284;BA.debugLine="CallSubDelayed2(DownloadService, \"CancelDownload\"";
anywheresoftware.b4a.keywords.Common.CallSubDelayed2(processBA,(Object)(mostCurrent._downloadservice.getObject()),"CancelDownload",(Object)(_serverpath+"/"+_serverconnectionfolder+"/connecttest.php"));
 //BA.debugLineNum = 286;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 287;BA.debugLine="If lang = \"es\" Then";
if ((_lang).equals("es")) { 
 //BA.debugLineNum = 288;BA.debugLine="ToastMessageShow(\"Has seleccionado el modo sin c";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Has seleccionado el modo sin conexión. PreserVamos iniciará con el último usuario que utilizaste, pero no podrás ver ni cargar puntos hasta que no te conectes."),anywheresoftware.b4a.keywords.Common.False);
 }else if((_lang).equals("en")) { 
 };
 //BA.debugLineNum = 292;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 293;BA.debugLine="StartActivity(Form_Main)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._form_main.getObject()));
 //BA.debugLineNum = 296;BA.debugLine="End Sub";
return "";
}
public static anywheresoftware.b4a.keywords.Common.ResumableSubWrapper  _buscarconfiguracion() throws Exception{
ResumableSub_BuscarConfiguracion rsub = new ResumableSub_BuscarConfiguracion(null);
rsub.resume(processBA, null);
return (anywheresoftware.b4a.keywords.Common.ResumableSubWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.keywords.Common.ResumableSubWrapper(), rsub);
}
public static class ResumableSub_BuscarConfiguracion extends BA.ResumableSub {
public ResumableSub_BuscarConfiguracion(appear.pnud.preservamos.main parent) {
this.parent = parent;
}
appear.pnud.preservamos.main parent;
anywheresoftware.b4a.objects.collections.Map _deviceidmap = null;
String _deviceactual = "";
anywheresoftware.b4a.objects.collections.Map _usermap = null;

@Override
public void resume(BA ba, Object[] result) throws Exception{

    while (true) {
        switch (state) {
            case -1:
{
anywheresoftware.b4a.keywords.Common.ReturnFromResumableSub(this,null);return;}
case 0:
//C
this.state = 1;
 //BA.debugLineNum = 219;BA.debugLine="lang = \"es\" 'default language";
parent._lang = "es";
 //BA.debugLineNum = 222;BA.debugLine="Dim deviceIdMap As Map";
_deviceidmap = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 223;BA.debugLine="deviceIdMap.Initialize";
_deviceidmap.Initialize();
 //BA.debugLineNum = 224;BA.debugLine="deviceIdMap = DBUtils.ExecuteMap(Starter.sqlDB, \"";
_deviceidmap = parent.mostCurrent._dbutils._executemap /*anywheresoftware.b4a.objects.collections.Map*/ (mostCurrent.activityBA,parent.mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"SELECT deviceID FROM appconfig WHERE configID = '1'",(String[])(anywheresoftware.b4a.keywords.Common.Null));
 //BA.debugLineNum = 225;BA.debugLine="If deviceIdMap = Null Or deviceIdMap.IsInitialize";
if (true) break;

case 1:
//if
this.state = 6;
if (_deviceidmap== null || _deviceidmap.IsInitialized()==anywheresoftware.b4a.keywords.Common.False) { 
this.state = 3;
}else {
this.state = 5;
}if (true) break;

case 3:
//C
this.state = 6;
 //BA.debugLineNum = 226;BA.debugLine="deviceID = \"noID\"";
parent._deviceid = "noID";
 if (true) break;

case 5:
//C
this.state = 6;
 //BA.debugLineNum = 228;BA.debugLine="deviceID = deviceIdMap.Get(\"deviceID\")";
parent._deviceid = BA.ObjectToString(_deviceidmap.Get((Object)("deviceID")));
 //BA.debugLineNum = 229;BA.debugLine="Log(\"deviceID de DB:\" & deviceID)";
anywheresoftware.b4a.keywords.Common.LogImpl("0393227","deviceID de DB:"+parent._deviceid,0);
 //BA.debugLineNum = 230;BA.debugLine="Log(\"deviceID de codigo:\" & utilidades.GetDevice";
anywheresoftware.b4a.keywords.Common.LogImpl("0393228","deviceID de codigo:"+parent.mostCurrent._utilidades._getdeviceid /*String*/ (mostCurrent.activityBA),0);
 if (true) break;

case 6:
//C
this.state = 7;
;
 //BA.debugLineNum = 234;BA.debugLine="Dim deviceactual As String";
_deviceactual = "";
 //BA.debugLineNum = 235;BA.debugLine="deviceactual = utilidades.GetDeviceId";
_deviceactual = parent.mostCurrent._utilidades._getdeviceid /*String*/ (mostCurrent.activityBA);
 //BA.debugLineNum = 236;BA.debugLine="If deviceactual <> deviceID Then";
if (true) break;

case 7:
//if
this.state = 10;
if ((_deviceactual).equals(parent._deviceid) == false) { 
this.state = 9;
}if (true) break;

case 9:
//C
this.state = 10;
 //BA.debugLineNum = 237;BA.debugLine="deviceID = deviceactual";
parent._deviceid = _deviceactual;
 if (true) break;

case 10:
//C
this.state = 11;
;
 //BA.debugLineNum = 241;BA.debugLine="Dim userMap As Map";
_usermap = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 242;BA.debugLine="userMap.Initialize";
_usermap.Initialize();
 //BA.debugLineNum = 243;BA.debugLine="userMap = DBUtils.ExecuteMap(Starter.sqlDB, \"SELE";
_usermap = parent.mostCurrent._dbutils._executemap /*anywheresoftware.b4a.objects.collections.Map*/ (mostCurrent.activityBA,parent.mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"SELECT * FROM userconfig WHERE lastUser=?",new String[]{"si"});
 //BA.debugLineNum = 244;BA.debugLine="If userMap = Null Or userMap.IsInitialized = Fals";
if (true) break;

case 11:
//if
this.state = 16;
if (_usermap== null || _usermap.IsInitialized()==anywheresoftware.b4a.keywords.Common.False) { 
this.state = 13;
}else {
this.state = 15;
}if (true) break;

case 13:
//C
this.state = 16;
 //BA.debugLineNum = 245;BA.debugLine="username = \"None\"";
parent._username = "None";
 //BA.debugLineNum = 246;BA.debugLine="pass = \"None\"";
parent._pass = "None";
 if (true) break;

case 15:
//C
this.state = 16;
 //BA.debugLineNum = 248;BA.debugLine="username = userMap.Get(\"username\")";
parent._username = BA.ObjectToString(_usermap.Get((Object)("username")));
 //BA.debugLineNum = 249;BA.debugLine="strUserName = username";
parent._strusername = parent._username;
 //BA.debugLineNum = 250;BA.debugLine="pass = userMap.Get(\"pass\")";
parent._pass = BA.ObjectToString(_usermap.Get((Object)("pass")));
 //BA.debugLineNum = 251;BA.debugLine="strUserLocation = userMap.Get(\"userlocation\")";
parent._struserlocation = BA.ObjectToString(_usermap.Get((Object)("userlocation")));
 //BA.debugLineNum = 252;BA.debugLine="strUserFullName = userMap.Get(\"username\")";
parent._struserfullname = BA.ObjectToString(_usermap.Get((Object)("username")));
 //BA.debugLineNum = 253;BA.debugLine="strUserOrg = userMap.Get(\"userorg\")";
parent._struserorg = BA.ObjectToString(_usermap.Get((Object)("userorg")));
 //BA.debugLineNum = 254;BA.debugLine="strUserTipoUsuario = userMap.Get(\"usertipousuari";
parent._strusertipousuario = BA.ObjectToString(_usermap.Get((Object)("usertipousuario")));
 //BA.debugLineNum = 255;BA.debugLine="userFechadeNacimiento = userMap.Get(\"fechadenaci";
parent._userfechadenacimiento = BA.ObjectToString(_usermap.Get((Object)("fechadenacimiento")));
 //BA.debugLineNum = 256;BA.debugLine="userSexo = userMap.Get(\"sexo\")";
parent._usersexo = BA.ObjectToString(_usermap.Get((Object)("sexo")));
 //BA.debugLineNum = 257;BA.debugLine="strUserEmail = userMap.Get(\"useremail\")";
parent._struseremail = BA.ObjectToString(_usermap.Get((Object)("useremail")));
 //BA.debugLineNum = 258;BA.debugLine="Log(\"Usuario existente en DB\")";
anywheresoftware.b4a.keywords.Common.LogImpl("0393256","Usuario existente en DB",0);
 //BA.debugLineNum = 259;BA.debugLine="Log(\"username: \" & username)";
anywheresoftware.b4a.keywords.Common.LogImpl("0393257","username: "+parent._username,0);
 //BA.debugLineNum = 260;BA.debugLine="Log(\"strUserLocation: \" & strUserLocation)";
anywheresoftware.b4a.keywords.Common.LogImpl("0393258","strUserLocation: "+parent._struserlocation,0);
 //BA.debugLineNum = 261;BA.debugLine="Log(\"strUserFullName: \" & strUserFullName)";
anywheresoftware.b4a.keywords.Common.LogImpl("0393259","strUserFullName: "+parent._struserfullname,0);
 //BA.debugLineNum = 262;BA.debugLine="Log(\"strUserOrg: \" & strUserOrg)";
anywheresoftware.b4a.keywords.Common.LogImpl("0393260","strUserOrg: "+parent._struserorg,0);
 //BA.debugLineNum = 263;BA.debugLine="Log(\"strUserTipoUsuario: \" & strUserTipoUsuario)";
anywheresoftware.b4a.keywords.Common.LogImpl("0393261","strUserTipoUsuario: "+parent._strusertipousuario,0);
 //BA.debugLineNum = 264;BA.debugLine="Log(\"strUserEmail: \" & strUserEmail)";
anywheresoftware.b4a.keywords.Common.LogImpl("0393262","strUserEmail: "+parent._struseremail,0);
 if (true) break;

case 16:
//C
this.state = -1;
;
 //BA.debugLineNum = 268;BA.debugLine="Return True";
if (true) {
anywheresoftware.b4a.keywords.Common.ReturnFromResumableSub(this,(Object)(anywheresoftware.b4a.keywords.Common.True));return;};
 //BA.debugLineNum = 271;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public static boolean  _checkforgoogleplayservices() throws Exception{
anywheresoftware.b4j.object.JavaObject _googleapiavailablity = null;
anywheresoftware.b4j.object.JavaObject _context = null;
 //BA.debugLineNum = 200;BA.debugLine="Sub CheckForGooglePlayServices As Boolean";
 //BA.debugLineNum = 201;BA.debugLine="Dim GoogleApiAvailablity As JavaObject";
_googleapiavailablity = new anywheresoftware.b4j.object.JavaObject();
 //BA.debugLineNum = 202;BA.debugLine="GoogleApiAvailablity = GoogleApiAvailablity.Initi";
_googleapiavailablity = (anywheresoftware.b4j.object.JavaObject) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4j.object.JavaObject(), (java.lang.Object)(_googleapiavailablity.InitializeStatic("com.google.android.gms.common.GoogleApiAvailability").RunMethod("getInstance",(Object[])(anywheresoftware.b4a.keywords.Common.Null))));
 //BA.debugLineNum = 203;BA.debugLine="Dim context As JavaObject";
_context = new anywheresoftware.b4j.object.JavaObject();
 //BA.debugLineNum = 204;BA.debugLine="context.InitializeContext";
_context.InitializeContext(processBA);
 //BA.debugLineNum = 205;BA.debugLine="If GoogleApiAvailablity.RunMethod(\"isGooglePlaySe";
if ((_googleapiavailablity.RunMethod("isGooglePlayServicesAvailable",new Object[]{(Object)(_context.getObject())})).equals((Object)(0)) == false) { 
 //BA.debugLineNum = 206;BA.debugLine="GoogleApiAvailablity.RunMethod(\"makeGooglePlaySe";
_googleapiavailablity.RunMethod("makeGooglePlayServicesAvailable",new Object[]{(Object)(_context.getObject())});
 //BA.debugLineNum = 207;BA.debugLine="Return False";
if (true) return anywheresoftware.b4a.keywords.Common.False;
 };
 //BA.debugLineNum = 209;BA.debugLine="Return True";
if (true) return anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 210;BA.debugLine="End Sub";
return false;
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 127;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 129;BA.debugLine="Private lblEstado As Label";
mostCurrent._lblestado = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 132;BA.debugLine="Dim Cursor1 As Cursor";
mostCurrent._cursor1 = new anywheresoftware.b4a.sql.SQL.CursorWrapper();
 //BA.debugLineNum = 133;BA.debugLine="Private logo_load_gif As B4XGifView";
mostCurrent._logo_load_gif = new appear.pnud.preservamos.b4xgifview();
 //BA.debugLineNum = 136;BA.debugLine="End Sub";
return "";
}

public static void initializeProcessGlobals() {
    
    if (main.processGlobalsRun == false) {
	    main.processGlobalsRun = true;
		try {
		        b4a.example.dateutils._process_globals();
main._process_globals();
form_main._process_globals();
form_reporte._process_globals();
frmlocalizacion._process_globals();
reporte_habitat_rio._process_globals();
utilidades._process_globals();
frmdatossinenviar._process_globals();
reporte_envio._process_globals();
alerta_fotos._process_globals();
alertas._process_globals();
aprender_muestreo._process_globals();
dbutils._process_globals();
downloadservice._process_globals();
firebasemessaging._process_globals();
frmabout._process_globals();
frmdatosanteriores._process_globals();
frmeditprofile._process_globals();
frmfelicitaciones._process_globals();
frmmapa._process_globals();
frmmunicipioestadisticas._process_globals();
frmpoliticadatos._process_globals();
frmtiporeporte._process_globals();
imagedownloader._process_globals();
inatcheck._process_globals();
mod_hidro._process_globals();
mod_hidro_fotos._process_globals();
mod_residuos._process_globals();
mod_residuos_fotos._process_globals();
reporte_fotos._process_globals();
reporte_habitat_laguna._process_globals();
reporte_habitat_rio_sierras._process_globals();
reporte_habitat_rio_sierras_bu._process_globals();
starter._process_globals();
uploadfiles._process_globals();
character_creation._process_globals();
register._process_globals();
xuiviewsutils._process_globals();
httputils2service._process_globals();
		
        } catch (Exception e) {
			throw new RuntimeException(e);
		}
    }
}public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 32;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 36;BA.debugLine="Dim deviceID As String";
_deviceid = "";
 //BA.debugLineNum = 38;BA.debugLine="Dim lang As String";
_lang = "";
 //BA.debugLineNum = 40;BA.debugLine="Dim username As String";
_username = "";
 //BA.debugLineNum = 41;BA.debugLine="Dim pass As String";
_pass = "";
 //BA.debugLineNum = 43;BA.debugLine="Dim hayinternet As Boolean";
_hayinternet = false;
 //BA.debugLineNum = 45;BA.debugLine="Dim serverversion As String";
_serverversion = "";
 //BA.debugLineNum = 46;BA.debugLine="Dim servernewstitulo As String";
_servernewstitulo = "";
 //BA.debugLineNum = 47;BA.debugLine="Dim servernewstext As String";
_servernewstext = "";
 //BA.debugLineNum = 48;BA.debugLine="Dim versionactual As String";
_versionactual = "";
 //BA.debugLineNum = 49;BA.debugLine="Dim server_partidos_version As String";
_server_partidos_version = "";
 //BA.debugLineNum = 50;BA.debugLine="Dim local_partidos_version As String";
_local_partidos_version = "";
 //BA.debugLineNum = 51;BA.debugLine="Dim serverPath As String = \"https://preservamos.a";
_serverpath = "https://preservamos.ar";
 //BA.debugLineNum = 52;BA.debugLine="Dim serverConnectionFolder As String = \"connect_a";
_serverconnectionfolder = "connect_app";
 //BA.debugLineNum = 55;BA.debugLine="Dim strUserID As String";
_struserid = "";
 //BA.debugLineNum = 56;BA.debugLine="Dim strUserName As String";
_strusername = "";
 //BA.debugLineNum = 57;BA.debugLine="Dim strUserFullName As String";
_struserfullname = "";
 //BA.debugLineNum = 58;BA.debugLine="Dim strUserLocation As String";
_struserlocation = "";
 //BA.debugLineNum = 59;BA.debugLine="Dim strUserEmail As String";
_struseremail = "";
 //BA.debugLineNum = 60;BA.debugLine="Dim strUserOrg As String  ' municipio";
_struserorg = "";
 //BA.debugLineNum = 61;BA.debugLine="Dim strUserTipoUsuario As String";
_strusertipousuario = "";
 //BA.debugLineNum = 62;BA.debugLine="Dim userFechadeNacimiento As String";
_userfechadenacimiento = "";
 //BA.debugLineNum = 63;BA.debugLine="Dim userSexo As String";
_usersexo = "";
 //BA.debugLineNum = 66;BA.debugLine="Dim numfotosok As String  = 0";
_numfotosok = BA.NumberToString(0);
 //BA.debugLineNum = 67;BA.debugLine="Dim numevalsok As String  = 0";
_numevalsok = BA.NumberToString(0);
 //BA.debugLineNum = 68;BA.debugLine="Dim msjprivadouser As String";
_msjprivadouser = "";
 //BA.debugLineNum = 69;BA.debugLine="Dim userlang As String = \"es\"";
_userlang = "es";
 //BA.debugLineNum = 70;BA.debugLine="Dim evalsOK As String";
_evalsok = "";
 //BA.debugLineNum = 71;BA.debugLine="Dim evalsCorregidas As String";
_evalscorregidas = "";
 //BA.debugLineNum = 72;BA.debugLine="Dim evalsInvalidas As String";
_evalsinvalidas = "";
 //BA.debugLineNum = 73;BA.debugLine="Dim evalsSospechosas As String";
_evalssospechosas = "";
 //BA.debugLineNum = 74;BA.debugLine="Dim validacionTotal As String";
_validaciontotal = "";
 //BA.debugLineNum = 75;BA.debugLine="Dim userCategoria As String";
_usercategoria = "";
 //BA.debugLineNum = 78;BA.debugLine="Dim puntos_markers As Int = 0";
_puntos_markers = (int) (0);
 //BA.debugLineNum = 79;BA.debugLine="Dim puntos_evals_residuos As Int = 0";
_puntos_evals_residuos = (int) (0);
 //BA.debugLineNum = 80;BA.debugLine="Dim puntos_evals_hidro As Int = 0";
_puntos_evals_hidro = (int) (0);
 //BA.debugLineNum = 81;BA.debugLine="Dim user_markers As Map";
_user_markers = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 82;BA.debugLine="Dim user_evals_residuos As Map";
_user_evals_residuos = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 83;BA.debugLine="Dim user_evals_hidro As Map";
_user_evals_hidro = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 85;BA.debugLine="Dim user_markers_list As List";
_user_markers_list = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 86;BA.debugLine="Dim user_evals_residuos_list As List";
_user_evals_residuos_list = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 87;BA.debugLine="Dim user_evals_hidro_list As List";
_user_evals_hidro_list = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 91;BA.debugLine="Dim puntos_markers_llanura As Int = 0";
_puntos_markers_llanura = (int) (0);
 //BA.debugLineNum = 92;BA.debugLine="Dim puntos_markers_sierras As Int = 0";
_puntos_markers_sierras = (int) (0);
 //BA.debugLineNum = 93;BA.debugLine="Dim puntos_markers_laguna As Int = 0";
_puntos_markers_laguna = (int) (0);
 //BA.debugLineNum = 94;BA.debugLine="Dim puntos_evals_residuos_llanura As Int = 0";
_puntos_evals_residuos_llanura = (int) (0);
 //BA.debugLineNum = 95;BA.debugLine="Dim puntos_evals_residuos_sierras As Int = 0";
_puntos_evals_residuos_sierras = (int) (0);
 //BA.debugLineNum = 96;BA.debugLine="Dim puntos_evals_residuos_laguna As Int = 0";
_puntos_evals_residuos_laguna = (int) (0);
 //BA.debugLineNum = 97;BA.debugLine="Dim puntos_evals_hidro_llanura As Int = 0";
_puntos_evals_hidro_llanura = (int) (0);
 //BA.debugLineNum = 98;BA.debugLine="Dim puntos_evals_hidro_sierras As Int = 0";
_puntos_evals_hidro_sierras = (int) (0);
 //BA.debugLineNum = 99;BA.debugLine="Dim puntos_evals_hidro_laguna As Int = 0";
_puntos_evals_hidro_laguna = (int) (0);
 //BA.debugLineNum = 102;BA.debugLine="Dim puntostotales As String = 0";
_puntostotales = BA.NumberToString(0);
 //BA.debugLineNum = 103;BA.debugLine="Dim puntosnumfotos As String = 0";
_puntosnumfotos = BA.NumberToString(0);
 //BA.debugLineNum = 104;BA.debugLine="Dim puntosnumevals As String = 0";
_puntosnumevals = BA.NumberToString(0);
 //BA.debugLineNum = 105;BA.debugLine="Dim numriollanura As String = 0";
_numriollanura = BA.NumberToString(0);
 //BA.debugLineNum = 106;BA.debugLine="Dim numriomontana As String = 0";
_numriomontana = BA.NumberToString(0);
 //BA.debugLineNum = 107;BA.debugLine="Dim numlaguna As String = 0";
_numlaguna = BA.NumberToString(0);
 //BA.debugLineNum = 108;BA.debugLine="Dim numestuario As String = 0";
_numestuario = BA.NumberToString(0);
 //BA.debugLineNum = 109;BA.debugLine="Dim numshares As String = 0";
_numshares = BA.NumberToString(0);
 //BA.debugLineNum = 110;BA.debugLine="Dim latitud As String";
_latitud = "";
 //BA.debugLineNum = 111;BA.debugLine="Dim longitud As String";
_longitud = "";
 //BA.debugLineNum = 112;BA.debugLine="Dim dateandtime As String";
_dateandtime = "";
 //BA.debugLineNum = 115;BA.debugLine="Dim currentEcoregion As String";
_currentecoregion = "";
 //BA.debugLineNum = 116;BA.debugLine="Dim currentSubEcoregion As String";
_currentsubecoregion = "";
 //BA.debugLineNum = 120;BA.debugLine="Dim currentproject As String";
_currentproject = "";
 //BA.debugLineNum = 123;BA.debugLine="Dim rp As RuntimePermissions";
_rp = new anywheresoftware.b4a.objects.RuntimePermissions();
 //BA.debugLineNum = 126;BA.debugLine="End Sub";
return "";
}
}
