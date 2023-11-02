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
public static String _currentproject = "";
public static anywheresoftware.b4a.objects.RuntimePermissions _rp = null;
public static anywheresoftware.b4a.objects.Timer _timer1 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnoffline = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblestado = null;
public anywheresoftware.b4a.sql.SQL.CursorWrapper _cursor1 = null;
public appear.pnud.preservamos.b4xgifview _logo_load_gif = null;
public b4a.example.dateutils _dateutils = null;
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

public static boolean isAnyActivityVisible() {
    boolean vis = false;
vis = vis | (main.mostCurrent != null);
vis = vis | (form_main.mostCurrent != null);
vis = vis | (frmabout.mostCurrent != null);
vis = vis | (alerta_fotos.mostCurrent != null);
vis = vis | (alertas.mostCurrent != null);
vis = vis | (aprender_muestreo.mostCurrent != null);
vis = vis | (form_reporte.mostCurrent != null);
vis = vis | (frmdatosanteriores.mostCurrent != null);
vis = vis | (frmdatossinenviar.mostCurrent != null);
vis = vis | (frmeditprofile.mostCurrent != null);
vis = vis | (frmfelicitaciones.mostCurrent != null);
vis = vis | (frmlocalizacion.mostCurrent != null);
vis = vis | (frmmapa.mostCurrent != null);
vis = vis | (frmmunicipioestadisticas.mostCurrent != null);
vis = vis | (frmpoliticadatos.mostCurrent != null);
vis = vis | (frmtiporeporte.mostCurrent != null);
vis = vis | (inatcheck.mostCurrent != null);
vis = vis | (mod_hidro.mostCurrent != null);
vis = vis | (mod_hidro_fotos.mostCurrent != null);
vis = vis | (mod_residuos.mostCurrent != null);
vis = vis | (mod_residuos_fotos.mostCurrent != null);
vis = vis | (register.mostCurrent != null);
vis = vis | (reporte_envio.mostCurrent != null);
vis = vis | (reporte_fotos.mostCurrent != null);
vis = vis | (reporte_habitat_laguna.mostCurrent != null);
vis = vis | (reporte_habitat_rio.mostCurrent != null);
vis = vis | (reporte_habitat_rio_bu.mostCurrent != null);
vis = vis | (reporte_habitat_rio_sierras.mostCurrent != null);
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
String _permission = "";
boolean _result = false;
Object[] group17;
int index17;
int groupLen17;

@Override
public void resume(BA ba, Object[] result) throws Exception{

    while (true) {
        switch (state) {
            case -1:
return;

case 0:
//C
this.state = 1;
 //BA.debugLineNum = 138;BA.debugLine="Dim p As Phone";
_p = new anywheresoftware.b4a.phone.Phone();
 //BA.debugLineNum = 139;BA.debugLine="p.SetScreenOrientation(1)";
_p.SetScreenOrientation(processBA,(int) (1));
 //BA.debugLineNum = 143;BA.debugLine="Sleep(500)";
anywheresoftware.b4a.keywords.Common.Sleep(mostCurrent.activityBA,this,(int) (500));
this.state = 16;
return;
case 16:
//C
this.state = 1;
;
 //BA.debugLineNum = 147;BA.debugLine="BuscarConfiguracion";
_buscarconfiguracion();
 //BA.debugLineNum = 149;BA.debugLine="Activity.LoadLayout(\"layload\")";
parent.mostCurrent._activity.LoadLayout("layload",mostCurrent.activityBA);
 //BA.debugLineNum = 153;BA.debugLine="Starter.dbdir = DBUtils.CopyDBFromAssets(\"preserv";
parent.mostCurrent._starter._dbdir /*String*/  = parent.mostCurrent._dbutils._copydbfromassets /*String*/ (mostCurrent.activityBA,"preservamosdb.db");
 //BA.debugLineNum = 156;BA.debugLine="Starter.modulo_residuos_dbdir = DBUtils.CopyDBFr";
parent.mostCurrent._starter._modulo_residuos_dbdir /*String*/  = parent.mostCurrent._dbutils._copydbfromassets /*String*/ (mostCurrent.activityBA,"preservamos_residuos_db.db");
 //BA.debugLineNum = 158;BA.debugLine="Starter.modulo_hidro_dbdir = DBUtils.CopyDBFromAs";
parent.mostCurrent._starter._modulo_hidro_dbdir /*String*/  = parent.mostCurrent._dbutils._copydbfromassets /*String*/ (mostCurrent.activityBA,"preservamos_hidro_db.db");
 //BA.debugLineNum = 161;BA.debugLine="Starter.savedir = rp.GetSafeDirDefaultExternal(\"\"";
parent.mostCurrent._starter._savedir /*String*/  = parent._rp.GetSafeDirDefaultExternal("");
 //BA.debugLineNum = 164;BA.debugLine="If File.Exists(Starter.savedir & \"/PreserVamos/\",";
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
 //BA.debugLineNum = 165;BA.debugLine="File.MakeDir(Starter.savedir, \"PreserVamos\")";
anywheresoftware.b4a.keywords.Common.File.MakeDir(parent.mostCurrent._starter._savedir /*String*/ ,"PreserVamos");
 if (true) break;
;
 //BA.debugLineNum = 171;BA.debugLine="If File.Exists(Starter.savedir & \"/PreserVamos/se";

case 4:
//if
this.state = 7;
if (anywheresoftware.b4a.keywords.Common.File.Exists(parent.mostCurrent._starter._savedir /*String*/ +"/PreserVamos/sent/","")==anywheresoftware.b4a.keywords.Common.False) { 
this.state = 6;
}if (true) break;

case 6:
//C
this.state = 7;
 //BA.debugLineNum = 172;BA.debugLine="File.MakeDir(Starter.savedir & \"/PreserVamos/\",";
anywheresoftware.b4a.keywords.Common.File.MakeDir(parent.mostCurrent._starter._savedir /*String*/ +"/PreserVamos/","sent");
 if (true) break;

case 7:
//C
this.state = 8;
;
 //BA.debugLineNum = 177;BA.debugLine="logo_load_gif.SetGif(File.DirAssets, \"Preservamos";
parent.mostCurrent._logo_load_gif._setgif /*String*/ (anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"Preservamos_Logo_Ani.gif");
 //BA.debugLineNum = 179;BA.debugLine="For Each permission As String In Array(rp.PERMISS";
if (true) break;

case 8:
//for
this.state = 15;
group17 = new Object[]{(Object)(parent._rp.PERMISSION_ACCESS_FINE_LOCATION),(Object)(parent._rp.PERMISSION_CAMERA)};
index17 = 0;
groupLen17 = group17.length;
this.state = 17;
if (true) break;

case 17:
//C
this.state = 15;
if (index17 < groupLen17) {
this.state = 10;
_permission = BA.ObjectToString(group17[index17]);}
if (true) break;

case 18:
//C
this.state = 17;
index17++;
if (true) break;

case 10:
//C
this.state = 11;
 //BA.debugLineNum = 180;BA.debugLine="rp.CheckAndRequest(permission)";
parent._rp.CheckAndRequest(processBA,_permission);
 //BA.debugLineNum = 181;BA.debugLine="Wait For Activity_PermissionResult (permission A";
anywheresoftware.b4a.keywords.Common.WaitFor("activity_permissionresult", processBA, this, null);
this.state = 19;
return;
case 19:
//C
this.state = 11;
_permission = (String) result[0];
_result = (Boolean) result[1];
;
 //BA.debugLineNum = 182;BA.debugLine="If Result = False Then";
if (true) break;

case 11:
//if
this.state = 14;
if (_result==anywheresoftware.b4a.keywords.Common.False) { 
this.state = 13;
}if (true) break;

case 13:
//C
this.state = 14;
 //BA.debugLineNum = 183;BA.debugLine="ToastMessageShow(\"Para usar la app, se necesita";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Para usar la app, se necesitan permisos para usar la cámara, el GPS y para guardar archivos en tu móvil"),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 184;BA.debugLine="Activity.Finish";
parent.mostCurrent._activity.Finish();
 //BA.debugLineNum = 185;BA.debugLine="Return";
if (true) return ;
 if (true) break;

case 14:
//C
this.state = 18;
;
 if (true) break;
if (true) break;

case 15:
//C
this.state = -1;
;
 //BA.debugLineNum = 188;BA.debugLine="Sleep(1500)";
anywheresoftware.b4a.keywords.Common.Sleep(mostCurrent.activityBA,this,(int) (1500));
this.state = 20;
return;
case 20:
//C
this.state = -1;
;
 //BA.debugLineNum = 193;BA.debugLine="lblEstado.Text = \"Comprobando conexión a internet";
parent.mostCurrent._lblestado.setText(BA.ObjectToCharSequence("Comprobando conexión a internet"));
 //BA.debugLineNum = 194;BA.debugLine="btnOffline.Text = \"Trabajar sin conexión\"";
parent.mostCurrent._btnoffline.setText(BA.ObjectToCharSequence("Trabajar sin conexión"));
 //BA.debugLineNum = 195;BA.debugLine="timer1.Initialize(\"Timer1\", 1000)";
parent._timer1.Initialize(processBA,"Timer1",(long) (1000));
 //BA.debugLineNum = 196;BA.debugLine="timer1.Enabled = True";
parent._timer1.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 198;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public static void  _activity_permissionresult(String _permission,boolean _result) throws Exception{
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 204;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 206;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 199;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 201;BA.debugLine="CheckForGooglePlayServices";
_checkforgoogleplayservices();
 //BA.debugLineNum = 202;BA.debugLine="End Sub";
return "";
}
public static String  _btnoffline_click() throws Exception{
 //BA.debugLineNum = 526;BA.debugLine="Sub btnOffline_Click";
 //BA.debugLineNum = 529;BA.debugLine="CallSubDelayed2(DownloadService, \"CancelDownload\"";
anywheresoftware.b4a.keywords.Common.CallSubDelayed2(processBA,(Object)(mostCurrent._downloadservice.getObject()),"CancelDownload",(Object)(_serverpath+"/"+_serverconnectionfolder+"/connecttest.php"));
 //BA.debugLineNum = 531;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 532;BA.debugLine="If lang = \"es\" Then";
if ((_lang).equals("es")) { 
 //BA.debugLineNum = 533;BA.debugLine="ToastMessageShow(\"Has seleccionado el modo sin c";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Has seleccionado el modo sin conexión. PreserVamos iniciará con el último usuario que utilizaste, pero no podrás ver ni cargar puntos hasta que no te conectes."),anywheresoftware.b4a.keywords.Common.False);
 }else if((_lang).equals("en")) { 
 };
 //BA.debugLineNum = 537;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 538;BA.debugLine="StartActivity(Form_Main)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._form_main.getObject()));
 //BA.debugLineNum = 541;BA.debugLine="End Sub";
return "";
}
public static String  _buscarconfiguracion() throws Exception{
anywheresoftware.b4a.objects.collections.Map _deviceidmap = null;
String _deviceactual = "";
anywheresoftware.b4a.objects.collections.Map _usermap = null;
 //BA.debugLineNum = 237;BA.debugLine="Sub BuscarConfiguracion";
 //BA.debugLineNum = 238;BA.debugLine="lang = \"es\" 'default language";
_lang = "es";
 //BA.debugLineNum = 241;BA.debugLine="Dim deviceIdMap As Map";
_deviceidmap = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 242;BA.debugLine="deviceIdMap.Initialize";
_deviceidmap.Initialize();
 //BA.debugLineNum = 243;BA.debugLine="deviceIdMap = DBUtils.ExecuteMap(Starter.sqlDB, \"";
_deviceidmap = mostCurrent._dbutils._executemap /*anywheresoftware.b4a.objects.collections.Map*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"SELECT deviceID FROM appconfig WHERE configID = '1'",(String[])(anywheresoftware.b4a.keywords.Common.Null));
 //BA.debugLineNum = 244;BA.debugLine="If deviceIdMap = Null Or deviceIdMap.IsInitialize";
if (_deviceidmap== null || _deviceidmap.IsInitialized()==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 245;BA.debugLine="deviceID = \"noID\"";
_deviceid = "noID";
 }else {
 //BA.debugLineNum = 247;BA.debugLine="deviceID = deviceIdMap.Get(\"deviceID\")";
_deviceid = BA.ObjectToString(_deviceidmap.Get((Object)("deviceID")));
 //BA.debugLineNum = 248;BA.debugLine="Log(\"deviceID de DB:\" & deviceID)";
anywheresoftware.b4a.keywords.Common.LogImpl("426345483","deviceID de DB:"+_deviceid,0);
 //BA.debugLineNum = 249;BA.debugLine="Log(\"deviceID de codigo:\" & utilidades.GetDevice";
anywheresoftware.b4a.keywords.Common.LogImpl("426345484","deviceID de codigo:"+mostCurrent._utilidades._getdeviceid /*String*/ (mostCurrent.activityBA),0);
 };
 //BA.debugLineNum = 253;BA.debugLine="Dim deviceactual As String";
_deviceactual = "";
 //BA.debugLineNum = 254;BA.debugLine="deviceactual = utilidades.GetDeviceId";
_deviceactual = mostCurrent._utilidades._getdeviceid /*String*/ (mostCurrent.activityBA);
 //BA.debugLineNum = 255;BA.debugLine="If deviceactual <> deviceID Then";
if ((_deviceactual).equals(_deviceid) == false) { 
 //BA.debugLineNum = 256;BA.debugLine="deviceID = deviceactual";
_deviceid = _deviceactual;
 };
 //BA.debugLineNum = 260;BA.debugLine="Dim userMap As Map";
_usermap = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 261;BA.debugLine="userMap.Initialize";
_usermap.Initialize();
 //BA.debugLineNum = 262;BA.debugLine="userMap = DBUtils.ExecuteMap(Starter.sqlDB, \"SELE";
_usermap = mostCurrent._dbutils._executemap /*anywheresoftware.b4a.objects.collections.Map*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"SELECT * FROM userconfig WHERE lastUser=?",new String[]{"si"});
 //BA.debugLineNum = 263;BA.debugLine="If userMap = Null Or userMap.IsInitialized = Fals";
if (_usermap== null || _usermap.IsInitialized()==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 264;BA.debugLine="username = \"None\"";
_username = "None";
 //BA.debugLineNum = 265;BA.debugLine="pass = \"None\"";
_pass = "None";
 }else {
 //BA.debugLineNum = 267;BA.debugLine="username = userMap.Get(\"username\")";
_username = BA.ObjectToString(_usermap.Get((Object)("username")));
 //BA.debugLineNum = 268;BA.debugLine="strUserName = username";
_strusername = _username;
 //BA.debugLineNum = 269;BA.debugLine="pass = userMap.Get(\"pass\")";
_pass = BA.ObjectToString(_usermap.Get((Object)("pass")));
 //BA.debugLineNum = 270;BA.debugLine="strUserLocation = userMap.Get(\"userlocation\")";
_struserlocation = BA.ObjectToString(_usermap.Get((Object)("userlocation")));
 //BA.debugLineNum = 271;BA.debugLine="strUserFullName = userMap.Get(\"username\")";
_struserfullname = BA.ObjectToString(_usermap.Get((Object)("username")));
 //BA.debugLineNum = 272;BA.debugLine="strUserOrg = userMap.Get(\"userorg\")";
_struserorg = BA.ObjectToString(_usermap.Get((Object)("userorg")));
 //BA.debugLineNum = 273;BA.debugLine="strUserTipoUsuario = userMap.Get(\"usertipousuari";
_strusertipousuario = BA.ObjectToString(_usermap.Get((Object)("usertipousuario")));
 //BA.debugLineNum = 274;BA.debugLine="userFechadeNacimiento = userMap.Get(\"fechadenaci";
_userfechadenacimiento = BA.ObjectToString(_usermap.Get((Object)("fechadenacimiento")));
 //BA.debugLineNum = 275;BA.debugLine="userSexo = userMap.Get(\"sexo\")";
_usersexo = BA.ObjectToString(_usermap.Get((Object)("sexo")));
 //BA.debugLineNum = 276;BA.debugLine="strUserEmail = userMap.Get(\"useremail\")";
_struseremail = BA.ObjectToString(_usermap.Get((Object)("useremail")));
 //BA.debugLineNum = 277;BA.debugLine="Log(\"Usuario existente en DB\")";
anywheresoftware.b4a.keywords.Common.LogImpl("426345512","Usuario existente en DB",0);
 //BA.debugLineNum = 278;BA.debugLine="Log(\"username: \" & username)";
anywheresoftware.b4a.keywords.Common.LogImpl("426345513","username: "+_username,0);
 //BA.debugLineNum = 279;BA.debugLine="Log(\"strUserLocation: \" & strUserLocation)";
anywheresoftware.b4a.keywords.Common.LogImpl("426345514","strUserLocation: "+_struserlocation,0);
 //BA.debugLineNum = 280;BA.debugLine="Log(\"strUserFullName: \" & strUserFullName)";
anywheresoftware.b4a.keywords.Common.LogImpl("426345515","strUserFullName: "+_struserfullname,0);
 //BA.debugLineNum = 281;BA.debugLine="Log(\"strUserOrg: \" & strUserOrg)";
anywheresoftware.b4a.keywords.Common.LogImpl("426345516","strUserOrg: "+_struserorg,0);
 //BA.debugLineNum = 282;BA.debugLine="Log(\"strUserTipoUsuario: \" & strUserTipoUsuario)";
anywheresoftware.b4a.keywords.Common.LogImpl("426345517","strUserTipoUsuario: "+_strusertipousuario,0);
 //BA.debugLineNum = 283;BA.debugLine="Log(\"strUserEmail: \" & strUserEmail)";
anywheresoftware.b4a.keywords.Common.LogImpl("426345518","strUserEmail: "+_struseremail,0);
 };
 //BA.debugLineNum = 297;BA.debugLine="End Sub";
return "";
}
public static boolean  _checkforgoogleplayservices() throws Exception{
anywheresoftware.b4j.object.JavaObject _googleapiavailablity = null;
anywheresoftware.b4j.object.JavaObject _context = null;
 //BA.debugLineNum = 208;BA.debugLine="Sub CheckForGooglePlayServices As Boolean";
 //BA.debugLineNum = 209;BA.debugLine="Dim GoogleApiAvailablity As JavaObject";
_googleapiavailablity = new anywheresoftware.b4j.object.JavaObject();
 //BA.debugLineNum = 210;BA.debugLine="GoogleApiAvailablity = GoogleApiAvailablity.Initi";
_googleapiavailablity = (anywheresoftware.b4j.object.JavaObject) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4j.object.JavaObject(), (java.lang.Object)(_googleapiavailablity.InitializeStatic("com.google.android.gms.common.GoogleApiAvailability").RunMethod("getInstance",(Object[])(anywheresoftware.b4a.keywords.Common.Null))));
 //BA.debugLineNum = 211;BA.debugLine="Dim context As JavaObject";
_context = new anywheresoftware.b4j.object.JavaObject();
 //BA.debugLineNum = 212;BA.debugLine="context.InitializeContext";
_context.InitializeContext(processBA);
 //BA.debugLineNum = 213;BA.debugLine="If GoogleApiAvailablity.RunMethod(\"isGooglePlaySe";
if ((_googleapiavailablity.RunMethod("isGooglePlayServicesAvailable",new Object[]{(Object)(_context.getObject())})).equals((Object)(0)) == false) { 
 //BA.debugLineNum = 214;BA.debugLine="GoogleApiAvailablity.RunMethod(\"makeGooglePlaySe";
_googleapiavailablity.RunMethod("makeGooglePlayServicesAvailable",new Object[]{(Object)(_context.getObject())});
 //BA.debugLineNum = 215;BA.debugLine="Return False";
if (true) return anywheresoftware.b4a.keywords.Common.False;
 };
 //BA.debugLineNum = 217;BA.debugLine="Return True";
if (true) return anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 218;BA.debugLine="End Sub";
return false;
}
public static String  _getpuntaje() throws Exception{
appear.pnud.preservamos.downloadservice._downloaddata _dd = null;
 //BA.debugLineNum = 427;BA.debugLine="Sub getPuntaje";
 //BA.debugLineNum = 428;BA.debugLine="Log(\"Getting puntaje\")";
anywheresoftware.b4a.keywords.Common.LogImpl("426542081","Getting puntaje",0);
 //BA.debugLineNum = 429;BA.debugLine="Dim dd As DownloadData";
_dd = new appear.pnud.preservamos.downloadservice._downloaddata();
 //BA.debugLineNum = 430;BA.debugLine="dd.url = serverPath & \"/\" & serverConnectionFold";
_dd.url /*String*/  = _serverpath+"/"+_serverconnectionfolder+"/getPuntaje.php?useremail="+_struseremail;
 //BA.debugLineNum = 431;BA.debugLine="Log(\"Testing: \" & dd.url)";
anywheresoftware.b4a.keywords.Common.LogImpl("426542084","Testing: "+_dd.url /*String*/ ,0);
 //BA.debugLineNum = 432;BA.debugLine="dd.EventName = \"getPuntaje\"";
_dd.EventName /*String*/  = "getPuntaje";
 //BA.debugLineNum = 433;BA.debugLine="dd.Target = Me";
_dd.Target /*Object*/  = main.getObject();
 //BA.debugLineNum = 434;BA.debugLine="CallSubDelayed2(DownloadService, \"StartDownload\"";
anywheresoftware.b4a.keywords.Common.CallSubDelayed2(processBA,(Object)(mostCurrent._downloadservice.getObject()),"StartDownload",(Object)(_dd));
 //BA.debugLineNum = 435;BA.debugLine="End Sub";
return "";
}
public static String  _getpuntaje_complete(appear.pnud.preservamos.httpjob _job) throws Exception{
String _ret = "";
String _act = "";
anywheresoftware.b4a.objects.collections.Map _new_user_markers = null;
anywheresoftware.b4a.objects.collections.Map _new_evals_residuos = null;
anywheresoftware.b4a.objects.collections.Map _new_evals_hidro = null;
anywheresoftware.b4a.objects.collections.JSONParser _parser = null;
anywheresoftware.b4a.objects.collections.List _markerslist = null;
anywheresoftware.b4a.objects.collections.List _evals_residuoslist = null;
anywheresoftware.b4a.objects.collections.List _evals_hidrolist = null;
 //BA.debugLineNum = 437;BA.debugLine="Sub getPuntaje_Complete(Job As HttpJob)";
 //BA.debugLineNum = 438;BA.debugLine="Log(\"getPuntaje: \" & Job.Success)";
anywheresoftware.b4a.keywords.Common.LogImpl("426607617","getPuntaje: "+BA.ObjectToString(_job._success /*boolean*/ ),0);
 //BA.debugLineNum = 439;BA.debugLine="If Job.Success = True Then";
if (_job._success /*boolean*/ ==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 440;BA.debugLine="hayinternet = True";
_hayinternet = anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 442;BA.debugLine="Dim ret As String";
_ret = "";
 //BA.debugLineNum = 443;BA.debugLine="Dim act As String";
_act = "";
 //BA.debugLineNum = 444;BA.debugLine="Dim new_user_markers As Map";
_new_user_markers = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 445;BA.debugLine="Dim new_evals_residuos As Map";
_new_evals_residuos = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 446;BA.debugLine="Dim new_evals_hidro As Map";
_new_evals_hidro = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 447;BA.debugLine="ret = Job.GetString";
_ret = _job._getstring /*String*/ ();
 //BA.debugLineNum = 448;BA.debugLine="Dim parser As JSONParser";
_parser = new anywheresoftware.b4a.objects.collections.JSONParser();
 //BA.debugLineNum = 449;BA.debugLine="parser.Initialize(ret)";
_parser.Initialize(_ret);
 //BA.debugLineNum = 450;BA.debugLine="act = parser.NextValue";
_act = BA.ObjectToString(_parser.NextValue());
 //BA.debugLineNum = 453;BA.debugLine="puntos_markers = 0";
_puntos_markers = (int) (0);
 //BA.debugLineNum = 454;BA.debugLine="puntostotales = 0";
_puntostotales = BA.NumberToString(0);
 //BA.debugLineNum = 455;BA.debugLine="puntos_evals_hidro = 0";
_puntos_evals_hidro = (int) (0);
 //BA.debugLineNum = 456;BA.debugLine="puntos_evals_hidro_laguna = 0";
_puntos_evals_hidro_laguna = (int) (0);
 //BA.debugLineNum = 457;BA.debugLine="puntos_evals_hidro_llanura = 0";
_puntos_evals_hidro_llanura = (int) (0);
 //BA.debugLineNum = 458;BA.debugLine="puntos_evals_hidro_sierras = 0";
_puntos_evals_hidro_sierras = (int) (0);
 //BA.debugLineNum = 459;BA.debugLine="puntos_evals_residuos = 0";
_puntos_evals_residuos = (int) (0);
 //BA.debugLineNum = 460;BA.debugLine="puntos_evals_residuos_laguna = 0";
_puntos_evals_residuos_laguna = (int) (0);
 //BA.debugLineNum = 461;BA.debugLine="puntos_evals_residuos_llanura = 0";
_puntos_evals_residuos_llanura = (int) (0);
 //BA.debugLineNum = 462;BA.debugLine="puntos_evals_residuos_sierras = 0";
_puntos_evals_residuos_sierras = (int) (0);
 //BA.debugLineNum = 463;BA.debugLine="user_markers_list.Initialize";
_user_markers_list.Initialize();
 //BA.debugLineNum = 464;BA.debugLine="user_evals_hidro_list.Initialize";
_user_evals_hidro_list.Initialize();
 //BA.debugLineNum = 465;BA.debugLine="user_evals_residuos_list.Initialize";
_user_evals_residuos_list.Initialize();
 //BA.debugLineNum = 467;BA.debugLine="If act = \"Not Found\" Then";
if ((_act).equals("Not Found")) { 
 //BA.debugLineNum = 468;BA.debugLine="ToastMessageShow(\"Error recuperando los puntos";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Error recuperando los puntos"),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 469;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 470;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 471;BA.debugLine="StartActivity(Form_Main)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._form_main.getObject()));
 }else if((_act).equals("GetPuntajeOk")) { 
 //BA.debugLineNum = 475;BA.debugLine="Try";
try { //BA.debugLineNum = 477;BA.debugLine="Dim markersList As List";
_markerslist = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 478;BA.debugLine="markersList.Initialize()";
_markerslist.Initialize();
 //BA.debugLineNum = 479;BA.debugLine="markersList = parser.NextArray";
_markerslist = _parser.NextArray();
 //BA.debugLineNum = 480;BA.debugLine="user_markers_list = markersList";
_user_markers_list = _markerslist;
 //BA.debugLineNum = 482;BA.debugLine="Dim evals_residuosList As List";
_evals_residuoslist = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 483;BA.debugLine="evals_residuosList.Initialize()";
_evals_residuoslist.Initialize();
 //BA.debugLineNum = 484;BA.debugLine="evals_residuosList = parser.NextArray";
_evals_residuoslist = _parser.NextArray();
 //BA.debugLineNum = 485;BA.debugLine="user_evals_residuos_list = evals_residuosList";
_user_evals_residuos_list = _evals_residuoslist;
 //BA.debugLineNum = 487;BA.debugLine="Dim evals_hidroList As List";
_evals_hidrolist = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 488;BA.debugLine="evals_hidroList.Initialize()";
_evals_hidrolist.Initialize();
 //BA.debugLineNum = 489;BA.debugLine="evals_hidroList = parser.NextArray";
_evals_hidrolist = _parser.NextArray();
 //BA.debugLineNum = 490;BA.debugLine="user_evals_hidro_list = evals_hidroList";
_user_evals_hidro_list = _evals_hidrolist;
 //BA.debugLineNum = 492;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 493;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 494;BA.debugLine="StartActivity(Form_Main)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._form_main.getObject()));
 } 
       catch (Exception e49) {
			processBA.setLastException(e49); //BA.debugLineNum = 496;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("426607675",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA)),0);
 //BA.debugLineNum = 497;BA.debugLine="Log(\"error en puntajes\")";
anywheresoftware.b4a.keywords.Common.LogImpl("426607676","error en puntajes",0);
 //BA.debugLineNum = 498;BA.debugLine="ToastMessageShow(\"No se pudo consultar el perf";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("No se pudo consultar el perfil del participante... intente de nuevo luego!"),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 501;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 502;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 503;BA.debugLine="StartActivity(Form_Main)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._form_main.getObject()));
 };
 };
 }else {
 //BA.debugLineNum = 510;BA.debugLine="hayinternet = False";
_hayinternet = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 511;BA.debugLine="MsgboxAsync(\"Compruebe su conexión a Internet!\",";
anywheresoftware.b4a.keywords.Common.MsgboxAsync(BA.ObjectToCharSequence("Compruebe su conexión a Internet!"),BA.ObjectToCharSequence("Oops!"),processBA);
 //BA.debugLineNum = 512;BA.debugLine="StartActivity(Form_Main)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._form_main.getObject()));
 };
 //BA.debugLineNum = 515;BA.debugLine="Job.Release";
_job._release /*String*/ ();
 //BA.debugLineNum = 516;BA.debugLine="End Sub";
return "";
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 124;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 128;BA.debugLine="Private btnOffline As Button";
mostCurrent._btnoffline = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 129;BA.debugLine="Private lblEstado As Label";
mostCurrent._lblestado = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 132;BA.debugLine="Dim Cursor1 As Cursor";
mostCurrent._cursor1 = new anywheresoftware.b4a.sql.SQL.CursorWrapper();
 //BA.debugLineNum = 133;BA.debugLine="Private logo_load_gif As B4XGifView";
mostCurrent._logo_load_gif = new appear.pnud.preservamos.b4xgifview();
 //BA.debugLineNum = 134;BA.debugLine="End Sub";
return "";
}

public static void initializeProcessGlobals() {
    
    if (main.processGlobalsRun == false) {
	    main.processGlobalsRun = true;
		try {
		        b4a.example.dateutils._process_globals();
main._process_globals();
form_main._process_globals();
frmabout._process_globals();
alerta_fotos._process_globals();
alertas._process_globals();
aprender_muestreo._process_globals();
dbutils._process_globals();
downloadservice._process_globals();
firebasemessaging._process_globals();
form_reporte._process_globals();
frmdatosanteriores._process_globals();
frmdatossinenviar._process_globals();
frmeditprofile._process_globals();
frmfelicitaciones._process_globals();
frmlocalizacion._process_globals();
frmmapa._process_globals();
frmmunicipioestadisticas._process_globals();
frmpoliticadatos._process_globals();
frmtiporeporte._process_globals();
httputils2service._process_globals();
imagedownloader._process_globals();
inatcheck._process_globals();
mod_hidro._process_globals();
mod_hidro_fotos._process_globals();
mod_residuos._process_globals();
mod_residuos_fotos._process_globals();
register._process_globals();
reporte_envio._process_globals();
reporte_fotos._process_globals();
reporte_habitat_laguna._process_globals();
reporte_habitat_rio._process_globals();
reporte_habitat_rio_bu._process_globals();
reporte_habitat_rio_sierras._process_globals();
starter._process_globals();
uploadfiles._process_globals();
utilidades._process_globals();
xuiviewsutils._process_globals();
		
        } catch (Exception e) {
			throw new RuntimeException(e);
		}
    }
}public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 26;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 30;BA.debugLine="Dim deviceID As String";
_deviceid = "";
 //BA.debugLineNum = 32;BA.debugLine="Dim lang As String";
_lang = "";
 //BA.debugLineNum = 34;BA.debugLine="Dim username As String";
_username = "";
 //BA.debugLineNum = 35;BA.debugLine="Dim pass As String";
_pass = "";
 //BA.debugLineNum = 37;BA.debugLine="Dim hayinternet As Boolean";
_hayinternet = false;
 //BA.debugLineNum = 39;BA.debugLine="Dim serverversion As String";
_serverversion = "";
 //BA.debugLineNum = 40;BA.debugLine="Dim servernewstitulo As String";
_servernewstitulo = "";
 //BA.debugLineNum = 41;BA.debugLine="Dim servernewstext As String";
_servernewstext = "";
 //BA.debugLineNum = 42;BA.debugLine="Dim versionactual As String";
_versionactual = "";
 //BA.debugLineNum = 43;BA.debugLine="Dim server_partidos_version As String";
_server_partidos_version = "";
 //BA.debugLineNum = 44;BA.debugLine="Dim local_partidos_version As String";
_local_partidos_version = "";
 //BA.debugLineNum = 45;BA.debugLine="Dim serverPath As String = \"https://preservamos.a";
_serverpath = "https://preservamos.ar";
 //BA.debugLineNum = 46;BA.debugLine="Dim serverConnectionFolder As String = \"connect_a";
_serverconnectionfolder = "connect_app";
 //BA.debugLineNum = 49;BA.debugLine="Dim strUserID As String";
_struserid = "";
 //BA.debugLineNum = 50;BA.debugLine="Dim strUserName As String";
_strusername = "";
 //BA.debugLineNum = 51;BA.debugLine="Dim strUserFullName As String";
_struserfullname = "";
 //BA.debugLineNum = 52;BA.debugLine="Dim strUserLocation As String";
_struserlocation = "";
 //BA.debugLineNum = 53;BA.debugLine="Dim strUserEmail As String";
_struseremail = "";
 //BA.debugLineNum = 54;BA.debugLine="Dim strUserOrg As String  ' municipio";
_struserorg = "";
 //BA.debugLineNum = 55;BA.debugLine="Dim strUserTipoUsuario As String";
_strusertipousuario = "";
 //BA.debugLineNum = 56;BA.debugLine="Dim userFechadeNacimiento As String";
_userfechadenacimiento = "";
 //BA.debugLineNum = 57;BA.debugLine="Dim userSexo As String";
_usersexo = "";
 //BA.debugLineNum = 59;BA.debugLine="Dim numfotosok As String  = 0";
_numfotosok = BA.NumberToString(0);
 //BA.debugLineNum = 60;BA.debugLine="Dim numevalsok As String  = 0";
_numevalsok = BA.NumberToString(0);
 //BA.debugLineNum = 61;BA.debugLine="Dim username As String";
_username = "";
 //BA.debugLineNum = 62;BA.debugLine="Dim pass As String";
_pass = "";
 //BA.debugLineNum = 63;BA.debugLine="Dim msjprivadouser As String";
_msjprivadouser = "";
 //BA.debugLineNum = 64;BA.debugLine="Dim userlang As String = \"es\"";
_userlang = "es";
 //BA.debugLineNum = 65;BA.debugLine="Dim evalsOK As String";
_evalsok = "";
 //BA.debugLineNum = 66;BA.debugLine="Dim evalsCorregidas As String";
_evalscorregidas = "";
 //BA.debugLineNum = 67;BA.debugLine="Dim evalsInvalidas As String";
_evalsinvalidas = "";
 //BA.debugLineNum = 68;BA.debugLine="Dim evalsSospechosas As String";
_evalssospechosas = "";
 //BA.debugLineNum = 69;BA.debugLine="Dim validacionTotal As String";
_validaciontotal = "";
 //BA.debugLineNum = 70;BA.debugLine="Dim userCategoria As String";
_usercategoria = "";
 //BA.debugLineNum = 73;BA.debugLine="Dim puntos_markers As Int = 0";
_puntos_markers = (int) (0);
 //BA.debugLineNum = 74;BA.debugLine="Dim puntos_evals_residuos As Int = 0";
_puntos_evals_residuos = (int) (0);
 //BA.debugLineNum = 75;BA.debugLine="Dim puntos_evals_hidro As Int = 0";
_puntos_evals_hidro = (int) (0);
 //BA.debugLineNum = 76;BA.debugLine="Dim user_markers As Map";
_user_markers = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 77;BA.debugLine="Dim user_evals_residuos As Map";
_user_evals_residuos = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 78;BA.debugLine="Dim user_evals_hidro As Map";
_user_evals_hidro = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 80;BA.debugLine="Dim user_markers_list As List";
_user_markers_list = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 81;BA.debugLine="Dim user_evals_residuos_list As List";
_user_evals_residuos_list = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 82;BA.debugLine="Dim user_evals_hidro_list As List";
_user_evals_hidro_list = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 86;BA.debugLine="Dim puntos_markers_llanura As Int = 0";
_puntos_markers_llanura = (int) (0);
 //BA.debugLineNum = 87;BA.debugLine="Dim puntos_markers_sierras As Int = 0";
_puntos_markers_sierras = (int) (0);
 //BA.debugLineNum = 88;BA.debugLine="Dim puntos_markers_laguna As Int = 0";
_puntos_markers_laguna = (int) (0);
 //BA.debugLineNum = 89;BA.debugLine="Dim puntos_evals_residuos_llanura As Int = 0";
_puntos_evals_residuos_llanura = (int) (0);
 //BA.debugLineNum = 90;BA.debugLine="Dim puntos_evals_residuos_sierras As Int = 0";
_puntos_evals_residuos_sierras = (int) (0);
 //BA.debugLineNum = 91;BA.debugLine="Dim puntos_evals_residuos_laguna As Int = 0";
_puntos_evals_residuos_laguna = (int) (0);
 //BA.debugLineNum = 92;BA.debugLine="Dim puntos_evals_hidro_llanura As Int = 0";
_puntos_evals_hidro_llanura = (int) (0);
 //BA.debugLineNum = 93;BA.debugLine="Dim puntos_evals_hidro_sierras As Int = 0";
_puntos_evals_hidro_sierras = (int) (0);
 //BA.debugLineNum = 94;BA.debugLine="Dim puntos_evals_hidro_laguna As Int = 0";
_puntos_evals_hidro_laguna = (int) (0);
 //BA.debugLineNum = 97;BA.debugLine="Dim puntostotales As String = 0";
_puntostotales = BA.NumberToString(0);
 //BA.debugLineNum = 98;BA.debugLine="Dim puntosnumfotos As String = 0";
_puntosnumfotos = BA.NumberToString(0);
 //BA.debugLineNum = 99;BA.debugLine="Dim puntosnumevals As String = 0";
_puntosnumevals = BA.NumberToString(0);
 //BA.debugLineNum = 100;BA.debugLine="Dim numriollanura As String = 0";
_numriollanura = BA.NumberToString(0);
 //BA.debugLineNum = 101;BA.debugLine="Dim numriomontana As String = 0";
_numriomontana = BA.NumberToString(0);
 //BA.debugLineNum = 102;BA.debugLine="Dim numlaguna As String = 0";
_numlaguna = BA.NumberToString(0);
 //BA.debugLineNum = 103;BA.debugLine="Dim numestuario As String = 0";
_numestuario = BA.NumberToString(0);
 //BA.debugLineNum = 104;BA.debugLine="Dim numshares As String = 0";
_numshares = BA.NumberToString(0);
 //BA.debugLineNum = 105;BA.debugLine="Dim latitud As String";
_latitud = "";
 //BA.debugLineNum = 106;BA.debugLine="Dim longitud As String";
_longitud = "";
 //BA.debugLineNum = 107;BA.debugLine="Dim dateandtime As String";
_dateandtime = "";
 //BA.debugLineNum = 113;BA.debugLine="Dim currentproject As String";
_currentproject = "";
 //BA.debugLineNum = 116;BA.debugLine="Dim rp As RuntimePermissions";
_rp = new anywheresoftware.b4a.objects.RuntimePermissions();
 //BA.debugLineNum = 117;BA.debugLine="Private timer1 As Timer";
_timer1 = new anywheresoftware.b4a.objects.Timer();
 //BA.debugLineNum = 123;BA.debugLine="End Sub";
return "";
}
public static String  _testinternet() throws Exception{
appear.pnud.preservamos.downloadservice._downloaddata _dd = null;
 //BA.debugLineNum = 304;BA.debugLine="Sub TestInternet";
 //BA.debugLineNum = 305;BA.debugLine="Log(\"Testing internet\")";
anywheresoftware.b4a.keywords.Common.LogImpl("426411009","Testing internet",0);
 //BA.debugLineNum = 306;BA.debugLine="Dim dd As DownloadData";
_dd = new appear.pnud.preservamos.downloadservice._downloaddata();
 //BA.debugLineNum = 307;BA.debugLine="dd.url = serverPath & \"/\" & serverConnectionFolde";
_dd.url /*String*/  = _serverpath+"/"+_serverconnectionfolder+"/connecttest.php";
 //BA.debugLineNum = 308;BA.debugLine="dd.EventName = \"TestInternet\"";
_dd.EventName /*String*/  = "TestInternet";
 //BA.debugLineNum = 309;BA.debugLine="dd.Target = Me";
_dd.Target /*Object*/  = main.getObject();
 //BA.debugLineNum = 310;BA.debugLine="CallSubDelayed2(DownloadService, \"StartDownload\",";
anywheresoftware.b4a.keywords.Common.CallSubDelayed2(processBA,(Object)(mostCurrent._downloadservice.getObject()),"StartDownload",(Object)(_dd));
 //BA.debugLineNum = 311;BA.debugLine="End Sub";
return "";
}
public static void  _testinternet_complete(appear.pnud.preservamos.httpjob _job) throws Exception{
ResumableSub_TestInternet_Complete rsub = new ResumableSub_TestInternet_Complete(null,_job);
rsub.resume(processBA, null);
}
public static class ResumableSub_TestInternet_Complete extends BA.ResumableSub {
public ResumableSub_TestInternet_Complete(appear.pnud.preservamos.main parent,appear.pnud.preservamos.httpjob _job) {
this.parent = parent;
this._job = _job;
}
appear.pnud.preservamos.main parent;
appear.pnud.preservamos.httpjob _job;
String _ret = "";
String _act = "";
anywheresoftware.b4a.objects.collections.JSONParser _parser = null;
int _result = 0;

@Override
public void resume(BA ba, Object[] result) throws Exception{

    while (true) {
        switch (state) {
            case -1:
return;

case 0:
//C
this.state = 1;
 //BA.debugLineNum = 314;BA.debugLine="Log(\"Job completed: \" & Job.Success)";
anywheresoftware.b4a.keywords.Common.LogImpl("426476545","Job completed: "+BA.ObjectToString(_job._success /*boolean*/ ),0);
 //BA.debugLineNum = 315;BA.debugLine="If Job.Success = True Then";
if (true) break;

case 1:
//if
this.state = 20;
if (_job._success /*boolean*/ ==anywheresoftware.b4a.keywords.Common.True) { 
this.state = 3;
}else {
this.state = 15;
}if (true) break;

case 3:
//C
this.state = 4;
 //BA.debugLineNum = 316;BA.debugLine="Log(\"Internet test successful\")";
anywheresoftware.b4a.keywords.Common.LogImpl("426476547","Internet test successful",0);
 //BA.debugLineNum = 317;BA.debugLine="versionactual = Application.VersionCode";
parent._versionactual = BA.NumberToString(anywheresoftware.b4a.keywords.Common.Application.getVersionCode());
 //BA.debugLineNum = 318;BA.debugLine="Dim ret As String";
_ret = "";
 //BA.debugLineNum = 319;BA.debugLine="Dim act As String";
_act = "";
 //BA.debugLineNum = 320;BA.debugLine="ret = Job.GetString";
_ret = _job._getstring /*String*/ ();
 //BA.debugLineNum = 321;BA.debugLine="Dim parser As JSONParser";
_parser = new anywheresoftware.b4a.objects.collections.JSONParser();
 //BA.debugLineNum = 322;BA.debugLine="parser.Initialize(ret)";
_parser.Initialize(_ret);
 //BA.debugLineNum = 323;BA.debugLine="act = parser.NextValue";
_act = BA.ObjectToString(_parser.NextValue());
 //BA.debugLineNum = 324;BA.debugLine="If act = \"Connected\" Then";
if (true) break;

case 4:
//if
this.state = 13;
if ((_act).equals("Connected")) { 
this.state = 6;
}if (true) break;

case 6:
//C
this.state = 7;
 //BA.debugLineNum = 327;BA.debugLine="hayinternet = True";
parent._hayinternet = anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 331;BA.debugLine="Log(\"strUserEmail\" & strUserEmail)";
anywheresoftware.b4a.keywords.Common.LogImpl("426476562","strUserEmail"+parent._struseremail,0);
 //BA.debugLineNum = 334;BA.debugLine="If strUserEmail <> \"\" And strUserEmail <> Null";
if (true) break;

case 7:
//if
this.state = 12;
if ((parent._struseremail).equals("") == false && parent._struseremail!= null && (parent._struseremail).equals("null") == false) { 
this.state = 9;
}else {
this.state = 11;
}if (true) break;

case 9:
//C
this.state = 12;
 //BA.debugLineNum = 335;BA.debugLine="lblEstado.Text = \"Buscando perfil del particip";
parent.mostCurrent._lblestado.setText(BA.ObjectToCharSequence("Buscando perfil del participante"));
 //BA.debugLineNum = 336;BA.debugLine="getPuntaje";
_getpuntaje();
 if (true) break;

case 11:
//C
this.state = 12;
 //BA.debugLineNum = 338;BA.debugLine="Activity.RemoveAllViews";
parent.mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 339;BA.debugLine="Activity.Finish";
parent.mostCurrent._activity.Finish();
 //BA.debugLineNum = 340;BA.debugLine="StartActivity(Form_Main)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(parent.mostCurrent._form_main.getObject()));
 if (true) break;

case 12:
//C
this.state = 13;
;
 if (true) break;

case 13:
//C
this.state = 20;
;
 if (true) break;

case 15:
//C
this.state = 16;
 //BA.debugLineNum = 404;BA.debugLine="Msgbox2Async(\"No tenés conexión a Internet. Pres";
anywheresoftware.b4a.keywords.Common.Msgbox2Async(BA.ObjectToCharSequence("No tenés conexión a Internet. PreserVamos iniciará con el último usuario que utilizaste, pero no podrás ver ni enviar reportes hasta que no te conectes!"),BA.ObjectToCharSequence("Advertencia"),"Ok, entiendo","","",(anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper(), (android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null)),processBA,anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 405;BA.debugLine="Wait For Msgbox_Result (Result As Int)";
anywheresoftware.b4a.keywords.Common.WaitFor("msgbox_result", processBA, this, null);
this.state = 21;
return;
case 21:
//C
this.state = 16;
_result = (Integer) result[0];
;
 //BA.debugLineNum = 406;BA.debugLine="If Result = DialogResponse.POSITIVE Then";
if (true) break;

case 16:
//if
this.state = 19;
if (_result==anywheresoftware.b4a.keywords.Common.DialogResponse.POSITIVE) { 
this.state = 18;
}if (true) break;

case 18:
//C
this.state = 19;
 //BA.debugLineNum = 407;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 //BA.debugLineNum = 409;BA.debugLine="hayinternet = False";
parent._hayinternet = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 411;BA.debugLine="Activity.RemoveAllViews";
parent.mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 412;BA.debugLine="Activity.Finish";
parent.mostCurrent._activity.Finish();
 //BA.debugLineNum = 413;BA.debugLine="StartActivity(Form_Main)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(parent.mostCurrent._form_main.getObject()));
 if (true) break;

case 19:
//C
this.state = 20;
;
 if (true) break;

case 20:
//C
this.state = -1;
;
 //BA.debugLineNum = 416;BA.debugLine="Job.Release";
_job._release /*String*/ ();
 //BA.debugLineNum = 417;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public static void  _msgbox_result(int _result) throws Exception{
}
public static String  _timer1_tick() throws Exception{
 //BA.debugLineNum = 225;BA.debugLine="Sub timer1_Tick";
 //BA.debugLineNum = 226;BA.debugLine="If lang = \"es\" Then";
if ((_lang).equals("es")) { 
 //BA.debugLineNum = 227;BA.debugLine="lblEstado.Text = \"Comprobando conexión a interne";
mostCurrent._lblestado.setText(BA.ObjectToCharSequence("Comprobando conexión a internet"));
 }else if((_lang).equals("en")) { 
 //BA.debugLineNum = 229;BA.debugLine="lblEstado.Text = \"Checking the connection to the";
mostCurrent._lblestado.setText(BA.ObjectToCharSequence("Checking the connection to the internet"));
 };
 //BA.debugLineNum = 233;BA.debugLine="TestInternet";
_testinternet();
 //BA.debugLineNum = 235;BA.debugLine="timer1.Enabled = False";
_timer1.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 236;BA.debugLine="End Sub";
return "";
}
}
