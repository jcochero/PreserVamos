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
        
        BA.LogInfo("** Activity (main) Create, isFirst = " + isFirst + " **");
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
public static String _serverversion = "";
public static String _servernewstitulo = "";
public static String _servernewstext = "";
public static String _versionactual = "";
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
public static String _screenshotpath = "";
public static anywheresoftware.b4a.objects.RuntimePermissions _rp = null;
public static anywheresoftware.b4a.objects.Timer _timer1 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnoffline = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblestado = null;
public anywheresoftware.b4a.sql.SQL.CursorWrapper _cursor1 = null;
public appear.pnud.preservamos.b4xgifview _logo_load_gif = null;
public b4a.example.dateutils _dateutils = null;
public appear.pnud.preservamos.form_main _form_main = null;
public appear.pnud.preservamos.starter _starter = null;
public appear.pnud.preservamos.inatcheck _inatcheck = null;
public appear.pnud.preservamos.frmlocalizacion _frmlocalizacion = null;
public appear.pnud.preservamos.reporte_envio _reporte_envio = null;
public appear.pnud.preservamos.alertas _alertas = null;
public appear.pnud.preservamos.register _register = null;
public appear.pnud.preservamos.frmeditprofile _frmeditprofile = null;
public appear.pnud.preservamos.alerta_fotos _alerta_fotos = null;
public appear.pnud.preservamos.form_reporte _form_reporte = null;
public appear.pnud.preservamos.aprender_muestreo _aprender_muestreo = null;
public appear.pnud.preservamos.dbutils _dbutils = null;
public appear.pnud.preservamos.downloadservice _downloadservice = null;
public appear.pnud.preservamos.firebasemessaging _firebasemessaging = null;
public appear.pnud.preservamos.frmabout _frmabout = null;
public appear.pnud.preservamos.frmdatosanteriores _frmdatosanteriores = null;
public appear.pnud.preservamos.frmfelicitaciones _frmfelicitaciones = null;
public appear.pnud.preservamos.frmmapa _frmmapa = null;
public appear.pnud.preservamos.frmperfil _frmperfil = null;
public appear.pnud.preservamos.frmpoliticadatos _frmpoliticadatos = null;
public appear.pnud.preservamos.httputils2service _httputils2service = null;
public appear.pnud.preservamos.imagedownloader _imagedownloader = null;
public appear.pnud.preservamos.reporte_fotos _reporte_fotos = null;
public appear.pnud.preservamos.reporte_habitat_laguna _reporte_habitat_laguna = null;
public appear.pnud.preservamos.reporte_habitat_rio _reporte_habitat_rio = null;
public appear.pnud.preservamos.uploadfiles _uploadfiles = null;
public appear.pnud.preservamos.utilidades _utilidades = null;
public appear.pnud.preservamos.xuiviewsutils _xuiviewsutils = null;

public static boolean isAnyActivityVisible() {
    boolean vis = false;
vis = vis | (main.mostCurrent != null);
vis = vis | (form_main.mostCurrent != null);
vis = vis | (inatcheck.mostCurrent != null);
vis = vis | (frmlocalizacion.mostCurrent != null);
vis = vis | (reporte_envio.mostCurrent != null);
vis = vis | (alertas.mostCurrent != null);
vis = vis | (register.mostCurrent != null);
vis = vis | (frmeditprofile.mostCurrent != null);
vis = vis | (alerta_fotos.mostCurrent != null);
vis = vis | (form_reporte.mostCurrent != null);
vis = vis | (aprender_muestreo.mostCurrent != null);
vis = vis | (frmabout.mostCurrent != null);
vis = vis | (frmdatosanteriores.mostCurrent != null);
vis = vis | (frmfelicitaciones.mostCurrent != null);
vis = vis | (frmmapa.mostCurrent != null);
vis = vis | (frmperfil.mostCurrent != null);
vis = vis | (frmpoliticadatos.mostCurrent != null);
vis = vis | (reporte_fotos.mostCurrent != null);
vis = vis | (reporte_habitat_laguna.mostCurrent != null);
vis = vis | (reporte_habitat_rio.mostCurrent != null);
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
 //BA.debugLineNum = 118;BA.debugLine="Dim p As Phone";
_p = new anywheresoftware.b4a.phone.Phone();
 //BA.debugLineNum = 119;BA.debugLine="p.SetScreenOrientation(1)";
_p.SetScreenOrientation(processBA,(int) (1));
 //BA.debugLineNum = 123;BA.debugLine="Sleep(500)";
anywheresoftware.b4a.keywords.Common.Sleep(mostCurrent.activityBA,this,(int) (500));
this.state = 16;
return;
case 16:
//C
this.state = 1;
;
 //BA.debugLineNum = 127;BA.debugLine="BuscarConfiguracion";
_buscarconfiguracion();
 //BA.debugLineNum = 129;BA.debugLine="Activity.LoadLayout(\"layload\")";
parent.mostCurrent._activity.LoadLayout("layload",mostCurrent.activityBA);
 //BA.debugLineNum = 134;BA.debugLine="Starter.dbdir = DBUtils.CopyDBFromAssets(\"preserv";
parent.mostCurrent._starter._dbdir /*String*/  = parent.mostCurrent._dbutils._copydbfromassets /*String*/ (mostCurrent.activityBA,"preservamosdb.db");
 //BA.debugLineNum = 136;BA.debugLine="Starter.savedir = rp.GetSafeDirDefaultExternal(\"\"";
parent.mostCurrent._starter._savedir /*String*/  = parent._rp.GetSafeDirDefaultExternal("");
 //BA.debugLineNum = 139;BA.debugLine="If File.Exists(Starter.savedir & \"/PreserVamos/\",";
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
 //BA.debugLineNum = 140;BA.debugLine="File.MakeDir(Starter.savedir, \"PreserVamos\")";
anywheresoftware.b4a.keywords.Common.File.MakeDir(parent.mostCurrent._starter._savedir /*String*/ ,"PreserVamos");
 if (true) break;
;
 //BA.debugLineNum = 146;BA.debugLine="If File.Exists(Starter.savedir & \"/PreserVamos/se";

case 4:
//if
this.state = 7;
if (anywheresoftware.b4a.keywords.Common.File.Exists(parent.mostCurrent._starter._savedir /*String*/ +"/PreserVamos/sent/","")==anywheresoftware.b4a.keywords.Common.False) { 
this.state = 6;
}if (true) break;

case 6:
//C
this.state = 7;
 //BA.debugLineNum = 147;BA.debugLine="File.MakeDir(Starter.savedir & \"/PreserVamos/\",";
anywheresoftware.b4a.keywords.Common.File.MakeDir(parent.mostCurrent._starter._savedir /*String*/ +"/PreserVamos/","sent");
 if (true) break;

case 7:
//C
this.state = 8;
;
 //BA.debugLineNum = 153;BA.debugLine="logo_load_gif.SetGif(File.DirAssets, \"Preservamos";
parent.mostCurrent._logo_load_gif._setgif /*String*/ (anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"Preservamos_Logo_Ani.gif");
 //BA.debugLineNum = 155;BA.debugLine="For Each permission As String In Array(rp.PERMISS";
if (true) break;

case 8:
//for
this.state = 15;
group15 = new Object[]{(Object)(parent._rp.PERMISSION_ACCESS_FINE_LOCATION),(Object)(parent._rp.PERMISSION_CAMERA)};
index15 = 0;
groupLen15 = group15.length;
this.state = 17;
if (true) break;

case 17:
//C
this.state = 15;
if (index15 < groupLen15) {
this.state = 10;
_permission = BA.ObjectToString(group15[index15]);}
if (true) break;

case 18:
//C
this.state = 17;
index15++;
if (true) break;

case 10:
//C
this.state = 11;
 //BA.debugLineNum = 156;BA.debugLine="rp.CheckAndRequest(permission)";
parent._rp.CheckAndRequest(processBA,_permission);
 //BA.debugLineNum = 157;BA.debugLine="Wait For Activity_PermissionResult (permission A";
anywheresoftware.b4a.keywords.Common.WaitFor("activity_permissionresult", processBA, this, null);
this.state = 19;
return;
case 19:
//C
this.state = 11;
_permission = (String) result[0];
_result = (Boolean) result[1];
;
 //BA.debugLineNum = 158;BA.debugLine="If Result = False Then";
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
 //BA.debugLineNum = 159;BA.debugLine="ToastMessageShow(\"Para usar la app, se necesita";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Para usar la app, se necesitan permisos para usar la cámara, el GPS y para guardar archivos en tu móvil"),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 160;BA.debugLine="Activity.Finish";
parent.mostCurrent._activity.Finish();
 //BA.debugLineNum = 161;BA.debugLine="Return";
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
 //BA.debugLineNum = 166;BA.debugLine="Sleep(1500)";
anywheresoftware.b4a.keywords.Common.Sleep(mostCurrent.activityBA,this,(int) (1500));
this.state = 20;
return;
case 20:
//C
this.state = -1;
;
 //BA.debugLineNum = 172;BA.debugLine="lblEstado.Text = \"Comprobando conexión a internet";
parent.mostCurrent._lblestado.setText(BA.ObjectToCharSequence("Comprobando conexión a internet"));
 //BA.debugLineNum = 173;BA.debugLine="btnOffline.Text = \"Trabajar sin conexión\"";
parent.mostCurrent._btnoffline.setText(BA.ObjectToCharSequence("Trabajar sin conexión"));
 //BA.debugLineNum = 174;BA.debugLine="timer1.Initialize(\"Timer1\", 1000)";
parent._timer1.Initialize(processBA,"Timer1",(long) (1000));
 //BA.debugLineNum = 175;BA.debugLine="timer1.Enabled = True";
parent._timer1.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 177;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public static void  _activity_permissionresult(String _permission,boolean _result) throws Exception{
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 185;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 187;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 178;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 181;BA.debugLine="CheckForGooglePlayServices";
_checkforgoogleplayservices();
 //BA.debugLineNum = 184;BA.debugLine="End Sub";
return "";
}
public static String  _btnoffline_click() throws Exception{
 //BA.debugLineNum = 360;BA.debugLine="Sub btnOffline_Click";
 //BA.debugLineNum = 363;BA.debugLine="CallSubDelayed2(DownloadService, \"CancelDownload\"";
anywheresoftware.b4a.keywords.Common.CallSubDelayed2(processBA,(Object)(mostCurrent._downloadservice.getObject()),"CancelDownload",(Object)(_serverpath+"/"+_serverconnectionfolder+"/connecttest.php"));
 //BA.debugLineNum = 365;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 366;BA.debugLine="If lang = \"es\" Then";
if ((_lang).equals("es")) { 
 //BA.debugLineNum = 367;BA.debugLine="ToastMessageShow(\"Has seleccionado el modo sin c";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Has seleccionado el modo sin conexión. PreserVamos iniciará con el último usuario que utilizaste, pero no podrás ver ni cargar puntos hasta que no te conectes."),anywheresoftware.b4a.keywords.Common.False);
 }else if((_lang).equals("en")) { 
 };
 //BA.debugLineNum = 371;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 372;BA.debugLine="StartActivity(Form_Main)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._form_main.getObject()));
 //BA.debugLineNum = 375;BA.debugLine="End Sub";
return "";
}
public static String  _buscarconfiguracion() throws Exception{
anywheresoftware.b4a.objects.collections.Map _deviceidmap = null;
String _deviceactual = "";
anywheresoftware.b4a.objects.collections.Map _usermap = null;
 //BA.debugLineNum = 220;BA.debugLine="Sub BuscarConfiguracion";
 //BA.debugLineNum = 221;BA.debugLine="lang = \"es\"";
_lang = "es";
 //BA.debugLineNum = 224;BA.debugLine="Dim deviceIdMap As Map";
_deviceidmap = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 225;BA.debugLine="deviceIdMap.Initialize";
_deviceidmap.Initialize();
 //BA.debugLineNum = 226;BA.debugLine="deviceIdMap = DBUtils.ExecuteMap(Starter.sqlDB, \"";
_deviceidmap = mostCurrent._dbutils._executemap /*anywheresoftware.b4a.objects.collections.Map*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"SELECT deviceID FROM appconfig WHERE configID = '1'",(String[])(anywheresoftware.b4a.keywords.Common.Null));
 //BA.debugLineNum = 227;BA.debugLine="If deviceIdMap = Null Or deviceIdMap.IsInitialize";
if (_deviceidmap== null || _deviceidmap.IsInitialized()==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 228;BA.debugLine="deviceID = \"noID\"";
_deviceid = "noID";
 }else {
 //BA.debugLineNum = 230;BA.debugLine="deviceID = deviceIdMap.Get(\"deviceID\")";
_deviceid = BA.ObjectToString(_deviceidmap.Get((Object)("deviceID")));
 //BA.debugLineNum = 231;BA.debugLine="Log(\"deviceID de DB:\" & deviceID)";
anywheresoftware.b4a.keywords.Common.LogImpl("522544395","deviceID de DB:"+_deviceid,0);
 //BA.debugLineNum = 232;BA.debugLine="Log(\"deviceID de codigo:\" & utilidades.GetDevice";
anywheresoftware.b4a.keywords.Common.LogImpl("522544396","deviceID de codigo:"+mostCurrent._utilidades._getdeviceid /*String*/ (mostCurrent.activityBA),0);
 };
 //BA.debugLineNum = 236;BA.debugLine="Dim deviceactual As String";
_deviceactual = "";
 //BA.debugLineNum = 237;BA.debugLine="deviceactual = utilidades.GetDeviceId";
_deviceactual = mostCurrent._utilidades._getdeviceid /*String*/ (mostCurrent.activityBA);
 //BA.debugLineNum = 238;BA.debugLine="If deviceactual <> deviceID Then";
if ((_deviceactual).equals(_deviceid) == false) { 
 //BA.debugLineNum = 239;BA.debugLine="deviceID = deviceactual";
_deviceid = _deviceactual;
 };
 //BA.debugLineNum = 243;BA.debugLine="Dim userMap As Map";
_usermap = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 244;BA.debugLine="userMap.Initialize";
_usermap.Initialize();
 //BA.debugLineNum = 245;BA.debugLine="userMap = DBUtils.ExecuteMap(Starter.sqlDB, \"SELE";
_usermap = mostCurrent._dbutils._executemap /*anywheresoftware.b4a.objects.collections.Map*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"SELECT * FROM userconfig WHERE lastUser=?",new String[]{"si"});
 //BA.debugLineNum = 246;BA.debugLine="If userMap = Null Or userMap.IsInitialized = Fals";
if (_usermap== null || _usermap.IsInitialized()==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 247;BA.debugLine="username = \"None\"";
_username = "None";
 //BA.debugLineNum = 248;BA.debugLine="pass = \"None\"";
_pass = "None";
 }else {
 //BA.debugLineNum = 250;BA.debugLine="username = userMap.Get(\"username\")";
_username = BA.ObjectToString(_usermap.Get((Object)("username")));
 //BA.debugLineNum = 251;BA.debugLine="strUserName = username";
_strusername = _username;
 //BA.debugLineNum = 252;BA.debugLine="pass = userMap.Get(\"pass\")";
_pass = BA.ObjectToString(_usermap.Get((Object)("pass")));
 //BA.debugLineNum = 253;BA.debugLine="strUserLocation = userMap.Get(\"userlocation\")";
_struserlocation = BA.ObjectToString(_usermap.Get((Object)("userlocation")));
 //BA.debugLineNum = 254;BA.debugLine="strUserFullName = userMap.Get(\"username\")";
_struserfullname = BA.ObjectToString(_usermap.Get((Object)("username")));
 //BA.debugLineNum = 255;BA.debugLine="strUserOrg = userMap.Get(\"userorg\")";
_struserorg = BA.ObjectToString(_usermap.Get((Object)("userorg")));
 //BA.debugLineNum = 256;BA.debugLine="strUserTipoUsuario = userMap.Get(\"usertipousuari";
_strusertipousuario = BA.ObjectToString(_usermap.Get((Object)("usertipousuario")));
 //BA.debugLineNum = 257;BA.debugLine="userFechadeNacimiento = userMap.Get(\"fechadenaci";
_userfechadenacimiento = BA.ObjectToString(_usermap.Get((Object)("fechadenacimiento")));
 //BA.debugLineNum = 258;BA.debugLine="userSexo = userMap.Get(\"sexo\")";
_usersexo = BA.ObjectToString(_usermap.Get((Object)("sexo")));
 //BA.debugLineNum = 259;BA.debugLine="strUserEmail = userMap.Get(\"useremail\")";
_struseremail = BA.ObjectToString(_usermap.Get((Object)("useremail")));
 };
 //BA.debugLineNum = 263;BA.debugLine="End Sub";
return "";
}
public static boolean  _checkforgoogleplayservices() throws Exception{
anywheresoftware.b4j.object.JavaObject _googleapiavailablity = null;
anywheresoftware.b4j.object.JavaObject _context = null;
 //BA.debugLineNum = 190;BA.debugLine="Sub CheckForGooglePlayServices As Boolean";
 //BA.debugLineNum = 191;BA.debugLine="Dim GoogleApiAvailablity As JavaObject";
_googleapiavailablity = new anywheresoftware.b4j.object.JavaObject();
 //BA.debugLineNum = 192;BA.debugLine="GoogleApiAvailablity = GoogleApiAvailablity.Initi";
_googleapiavailablity = (anywheresoftware.b4j.object.JavaObject) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4j.object.JavaObject(), (java.lang.Object)(_googleapiavailablity.InitializeStatic("com.google.android.gms.common.GoogleApiAvailability").RunMethod("getInstance",(Object[])(anywheresoftware.b4a.keywords.Common.Null))));
 //BA.debugLineNum = 193;BA.debugLine="Dim context As JavaObject";
_context = new anywheresoftware.b4j.object.JavaObject();
 //BA.debugLineNum = 194;BA.debugLine="context.InitializeContext";
_context.InitializeContext(processBA);
 //BA.debugLineNum = 195;BA.debugLine="If GoogleApiAvailablity.RunMethod(\"isGooglePlaySe";
if ((_googleapiavailablity.RunMethod("isGooglePlayServicesAvailable",new Object[]{(Object)(_context.getObject())})).equals((Object)(0)) == false) { 
 //BA.debugLineNum = 196;BA.debugLine="GoogleApiAvailablity.RunMethod(\"makeGooglePlaySe";
_googleapiavailablity.RunMethod("makeGooglePlayServicesAvailable",new Object[]{(Object)(_context.getObject())});
 //BA.debugLineNum = 197;BA.debugLine="Return False";
if (true) return anywheresoftware.b4a.keywords.Common.False;
 };
 //BA.debugLineNum = 199;BA.debugLine="Return True";
if (true) return anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 200;BA.debugLine="End Sub";
return false;
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 104;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 108;BA.debugLine="Private btnOffline As Button";
mostCurrent._btnoffline = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 109;BA.debugLine="Private lblEstado As Label";
mostCurrent._lblestado = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 112;BA.debugLine="Dim Cursor1 As Cursor";
mostCurrent._cursor1 = new anywheresoftware.b4a.sql.SQL.CursorWrapper();
 //BA.debugLineNum = 113;BA.debugLine="Private logo_load_gif As B4XGifView";
mostCurrent._logo_load_gif = new appear.pnud.preservamos.b4xgifview();
 //BA.debugLineNum = 114;BA.debugLine="End Sub";
return "";
}

public static void initializeProcessGlobals() {
    
    if (main.processGlobalsRun == false) {
	    main.processGlobalsRun = true;
		try {
		        b4a.example.dateutils._process_globals();
main._process_globals();
form_main._process_globals();
starter._process_globals();
inatcheck._process_globals();
frmlocalizacion._process_globals();
reporte_envio._process_globals();
alertas._process_globals();
register._process_globals();
frmeditprofile._process_globals();
alerta_fotos._process_globals();
form_reporte._process_globals();
aprender_muestreo._process_globals();
dbutils._process_globals();
downloadservice._process_globals();
firebasemessaging._process_globals();
frmabout._process_globals();
frmdatosanteriores._process_globals();
frmfelicitaciones._process_globals();
frmmapa._process_globals();
frmperfil._process_globals();
frmpoliticadatos._process_globals();
httputils2service._process_globals();
imagedownloader._process_globals();
reporte_fotos._process_globals();
reporte_habitat_laguna._process_globals();
reporte_habitat_rio._process_globals();
uploadfiles._process_globals();
utilidades._process_globals();
xuiviewsutils._process_globals();
		
        } catch (Exception e) {
			throw new RuntimeException(e);
		}
    }
}public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 31;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 40;BA.debugLine="Dim deviceID As String";
_deviceid = "";
 //BA.debugLineNum = 42;BA.debugLine="Dim lang As String";
_lang = "";
 //BA.debugLineNum = 44;BA.debugLine="Dim username As String";
_username = "";
 //BA.debugLineNum = 45;BA.debugLine="Dim pass As String";
_pass = "";
 //BA.debugLineNum = 47;BA.debugLine="Dim serverversion As String";
_serverversion = "";
 //BA.debugLineNum = 48;BA.debugLine="Dim servernewstitulo As String";
_servernewstitulo = "";
 //BA.debugLineNum = 49;BA.debugLine="Dim servernewstext As String";
_servernewstext = "";
 //BA.debugLineNum = 50;BA.debugLine="Dim versionactual As String";
_versionactual = "";
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
 //BA.debugLineNum = 65;BA.debugLine="Dim numfotosok As String  = 0";
_numfotosok = BA.NumberToString(0);
 //BA.debugLineNum = 66;BA.debugLine="Dim numevalsok As String  = 0";
_numevalsok = BA.NumberToString(0);
 //BA.debugLineNum = 67;BA.debugLine="Dim username As String";
_username = "";
 //BA.debugLineNum = 68;BA.debugLine="Dim pass As String";
_pass = "";
 //BA.debugLineNum = 69;BA.debugLine="Dim msjprivadouser As String";
_msjprivadouser = "";
 //BA.debugLineNum = 70;BA.debugLine="Dim userlang As String = \"es\"";
_userlang = "es";
 //BA.debugLineNum = 71;BA.debugLine="Dim evalsOK As String";
_evalsok = "";
 //BA.debugLineNum = 72;BA.debugLine="Dim evalsCorregidas As String";
_evalscorregidas = "";
 //BA.debugLineNum = 73;BA.debugLine="Dim evalsInvalidas As String";
_evalsinvalidas = "";
 //BA.debugLineNum = 74;BA.debugLine="Dim evalsSospechosas As String";
_evalssospechosas = "";
 //BA.debugLineNum = 75;BA.debugLine="Dim validacionTotal As String";
_validaciontotal = "";
 //BA.debugLineNum = 76;BA.debugLine="Dim userCategoria As String";
_usercategoria = "";
 //BA.debugLineNum = 79;BA.debugLine="Dim puntostotales As String = 0";
_puntostotales = BA.NumberToString(0);
 //BA.debugLineNum = 80;BA.debugLine="Dim puntosnumfotos As String = 0";
_puntosnumfotos = BA.NumberToString(0);
 //BA.debugLineNum = 81;BA.debugLine="Dim puntosnumevals As String = 0";
_puntosnumevals = BA.NumberToString(0);
 //BA.debugLineNum = 82;BA.debugLine="Dim numriollanura As String = 0";
_numriollanura = BA.NumberToString(0);
 //BA.debugLineNum = 83;BA.debugLine="Dim numriomontana As String = 0";
_numriomontana = BA.NumberToString(0);
 //BA.debugLineNum = 84;BA.debugLine="Dim numlaguna As String = 0";
_numlaguna = BA.NumberToString(0);
 //BA.debugLineNum = 85;BA.debugLine="Dim numestuario As String = 0";
_numestuario = BA.NumberToString(0);
 //BA.debugLineNum = 86;BA.debugLine="Dim numshares As String = 0";
_numshares = BA.NumberToString(0);
 //BA.debugLineNum = 87;BA.debugLine="Dim latitud As String";
_latitud = "";
 //BA.debugLineNum = 88;BA.debugLine="Dim longitud As String";
_longitud = "";
 //BA.debugLineNum = 89;BA.debugLine="Dim dateandtime As String";
_dateandtime = "";
 //BA.debugLineNum = 90;BA.debugLine="Dim currentproject As String";
_currentproject = "";
 //BA.debugLineNum = 93;BA.debugLine="Dim screenshotpath As String";
_screenshotpath = "";
 //BA.debugLineNum = 96;BA.debugLine="Dim rp As RuntimePermissions";
_rp = new anywheresoftware.b4a.objects.RuntimePermissions();
 //BA.debugLineNum = 97;BA.debugLine="Private timer1 As Timer";
_timer1 = new anywheresoftware.b4a.objects.Timer();
 //BA.debugLineNum = 103;BA.debugLine="End Sub";
return "";
}
public static String  _testinternet() throws Exception{
appear.pnud.preservamos.downloadservice._downloaddata _dd = null;
 //BA.debugLineNum = 270;BA.debugLine="Sub TestInternet";
 //BA.debugLineNum = 271;BA.debugLine="Log(\"Testing internet\")";
anywheresoftware.b4a.keywords.Common.LogImpl("522609921","Testing internet",0);
 //BA.debugLineNum = 272;BA.debugLine="Dim dd As DownloadData";
_dd = new appear.pnud.preservamos.downloadservice._downloaddata();
 //BA.debugLineNum = 273;BA.debugLine="dd.url = serverPath & \"/\" & serverConnectionFolde";
_dd.url /*String*/  = _serverpath+"/"+_serverconnectionfolder+"/connecttest.php";
 //BA.debugLineNum = 274;BA.debugLine="dd.EventName = \"TestInternet\"";
_dd.EventName /*String*/  = "TestInternet";
 //BA.debugLineNum = 275;BA.debugLine="dd.Target = Me";
_dd.Target /*Object*/  = main.getObject();
 //BA.debugLineNum = 277;BA.debugLine="CallSubDelayed2(DownloadService, \"StartDownload\",";
anywheresoftware.b4a.keywords.Common.CallSubDelayed2(processBA,(Object)(mostCurrent._downloadservice.getObject()),"StartDownload",(Object)(_dd));
 //BA.debugLineNum = 278;BA.debugLine="End Sub";
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
anywheresoftware.b4a.objects.collections.Map _nd = null;
int _result = 0;
anywheresoftware.b4a.objects.IntentWrapper _market = null;
String _uri = "";

@Override
public void resume(BA ba, Object[] result) throws Exception{

    while (true) {
        switch (state) {
            case -1:
return;

case 0:
//C
this.state = 1;
 //BA.debugLineNum = 281;BA.debugLine="Log(\"Job completed: \" & Job.Success)";
anywheresoftware.b4a.keywords.Common.LogImpl("522675457","Job completed: "+BA.ObjectToString(_job._success /*boolean*/ ),0);
 //BA.debugLineNum = 282;BA.debugLine="If Job.Success = True Then";
if (true) break;

case 1:
//if
this.state = 30;
if (_job._success /*boolean*/ ==anywheresoftware.b4a.keywords.Common.True) { 
this.state = 3;
}else {
this.state = 25;
}if (true) break;

case 3:
//C
this.state = 4;
 //BA.debugLineNum = 283;BA.debugLine="Log(\"Internet test successful\")";
anywheresoftware.b4a.keywords.Common.LogImpl("522675459","Internet test successful",0);
 //BA.debugLineNum = 284;BA.debugLine="versionactual = Application.VersionCode";
parent._versionactual = BA.NumberToString(anywheresoftware.b4a.keywords.Common.Application.getVersionCode());
 //BA.debugLineNum = 285;BA.debugLine="Dim ret As String";
_ret = "";
 //BA.debugLineNum = 286;BA.debugLine="Dim act As String";
_act = "";
 //BA.debugLineNum = 287;BA.debugLine="ret = Job.GetString";
_ret = _job._getstring /*String*/ ();
 //BA.debugLineNum = 288;BA.debugLine="Dim parser As JSONParser";
_parser = new anywheresoftware.b4a.objects.collections.JSONParser();
 //BA.debugLineNum = 289;BA.debugLine="parser.Initialize(ret)";
_parser.Initialize(_ret);
 //BA.debugLineNum = 290;BA.debugLine="act = parser.NextValue";
_act = BA.ObjectToString(_parser.NextValue());
 //BA.debugLineNum = 291;BA.debugLine="If act = \"Connected\" Then";
if (true) break;

case 4:
//if
this.state = 23;
if ((_act).equals("Connected")) { 
this.state = 6;
}if (true) break;

case 6:
//C
this.state = 7;
 //BA.debugLineNum = 294;BA.debugLine="Dim nd As Map";
_nd = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 295;BA.debugLine="nd = parser.NextObject";
_nd = _parser.NextObject();
 //BA.debugLineNum = 296;BA.debugLine="serverversion = nd.Get(\"currentversion\")";
parent._serverversion = BA.ObjectToString(_nd.Get((Object)("currentversion")));
 //BA.debugLineNum = 297;BA.debugLine="servernewstitulo = nd.Get(\"newstitulo\")";
parent._servernewstitulo = BA.ObjectToString(_nd.Get((Object)("newstitulo")));
 //BA.debugLineNum = 298;BA.debugLine="servernewstext = nd.Get(\"newstext\")";
parent._servernewstext = BA.ObjectToString(_nd.Get((Object)("newstext")));
 //BA.debugLineNum = 299;BA.debugLine="If serverversion > versionactual Then";
if (true) break;

case 7:
//if
this.state = 22;
if ((double)(Double.parseDouble(parent._serverversion))>(double)(Double.parseDouble(parent._versionactual))) { 
this.state = 9;
}else {
this.state = 17;
}if (true) break;

case 9:
//C
this.state = 10;
 //BA.debugLineNum = 300;BA.debugLine="Msgbox2Async(\"Para continuar, debe descargar u";
anywheresoftware.b4a.keywords.Common.Msgbox2Async(BA.ObjectToCharSequence("Para continuar, debe descargar una actualización importante"),BA.ObjectToCharSequence("Actualización"),"Ir a GooglePlay","Lo haré después","",(anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper(), (android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null)),processBA,anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 301;BA.debugLine="Wait For Msgbox_Result (Result As Int)";
anywheresoftware.b4a.keywords.Common.WaitFor("msgbox_result", processBA, this, null);
this.state = 31;
return;
case 31:
//C
this.state = 10;
_result = (Integer) result[0];
;
 //BA.debugLineNum = 302;BA.debugLine="If Result = DialogResponse.POSITIVE Then";
if (true) break;

case 10:
//if
this.state = 15;
if (_result==anywheresoftware.b4a.keywords.Common.DialogResponse.POSITIVE) { 
this.state = 12;
}else {
this.state = 14;
}if (true) break;

case 12:
//C
this.state = 15;
 //BA.debugLineNum = 304;BA.debugLine="Dim market As Intent, uri As String";
_market = new anywheresoftware.b4a.objects.IntentWrapper();
_uri = "";
 //BA.debugLineNum = 305;BA.debugLine="uri=\"market://details?id=ilpla.appear\"";
_uri = "market://details?id=ilpla.appear";
 //BA.debugLineNum = 306;BA.debugLine="market.Initialize(market.ACTION_VIEW,uri)";
_market.Initialize(_market.ACTION_VIEW,_uri);
 //BA.debugLineNum = 307;BA.debugLine="StartActivity(market)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(_market.getObject()));
 //BA.debugLineNum = 308;BA.debugLine="Log(\"Yes, update\")";
anywheresoftware.b4a.keywords.Common.LogImpl("522675484","Yes, update",0);
 //BA.debugLineNum = 310;BA.debugLine="Activity.RemoveAllViews";
parent.mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 311;BA.debugLine="Activity.Finish";
parent.mostCurrent._activity.Finish();
 //BA.debugLineNum = 312;BA.debugLine="StartActivity(Form_Main)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(parent.mostCurrent._form_main.getObject()));
 if (true) break;

case 14:
//C
this.state = 15;
 //BA.debugLineNum = 314;BA.debugLine="Log(\"No, no update\")";
anywheresoftware.b4a.keywords.Common.LogImpl("522675490","No, no update",0);
 //BA.debugLineNum = 316;BA.debugLine="Activity.RemoveAllViews";
parent.mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 317;BA.debugLine="Activity.Finish";
parent.mostCurrent._activity.Finish();
 //BA.debugLineNum = 318;BA.debugLine="StartActivity(Form_Main)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(parent.mostCurrent._form_main.getObject()));
 if (true) break;

case 15:
//C
this.state = 22;
;
 if (true) break;

case 17:
//C
this.state = 18;
 //BA.debugLineNum = 322;BA.debugLine="If servernewstitulo <> \"\" And servernewstitulo";
if (true) break;

case 18:
//if
this.state = 21;
if ((parent._servernewstitulo).equals("") == false && (parent._servernewstitulo).equals("Nada") == false) { 
this.state = 20;
}if (true) break;

case 20:
//C
this.state = 21;
 //BA.debugLineNum = 323;BA.debugLine="MsgboxAsync(servernewstext, servernewstitulo)";
anywheresoftware.b4a.keywords.Common.MsgboxAsync(BA.ObjectToCharSequence(parent._servernewstext),BA.ObjectToCharSequence(parent._servernewstitulo),processBA);
 if (true) break;

case 21:
//C
this.state = 22;
;
 //BA.debugLineNum = 329;BA.debugLine="Activity.RemoveAllViews";
parent.mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 330;BA.debugLine="Activity.Finish";
parent.mostCurrent._activity.Finish();
 //BA.debugLineNum = 331;BA.debugLine="StartActivity(Form_Main)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(parent.mostCurrent._form_main.getObject()));
 if (true) break;

case 22:
//C
this.state = 23;
;
 if (true) break;

case 23:
//C
this.state = 30;
;
 if (true) break;

case 25:
//C
this.state = 26;
 //BA.debugLineNum = 336;BA.debugLine="Msgbox2Async(\"No tienes conexión a Internet. Pre";
anywheresoftware.b4a.keywords.Common.Msgbox2Async(BA.ObjectToCharSequence("No tienes conexión a Internet. PreserVamos iniciará con el último usuario que utilizaste, pero no podrás ver ni cargar puntos hasta que no te conectes!"),BA.ObjectToCharSequence("Advertencia"),"Ok, entiendo","","",(anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper(), (android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null)),processBA,anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 337;BA.debugLine="Wait For Msgbox_Result (Result As Int)";
anywheresoftware.b4a.keywords.Common.WaitFor("msgbox_result", processBA, this, null);
this.state = 32;
return;
case 32:
//C
this.state = 26;
_result = (Integer) result[0];
;
 //BA.debugLineNum = 338;BA.debugLine="If Result = DialogResponse.POSITIVE Then";
if (true) break;

case 26:
//if
this.state = 29;
if (_result==anywheresoftware.b4a.keywords.Common.DialogResponse.POSITIVE) { 
this.state = 28;
}if (true) break;

case 28:
//C
this.state = 29;
 //BA.debugLineNum = 339;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 //BA.debugLineNum = 341;BA.debugLine="Activity.RemoveAllViews";
parent.mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 342;BA.debugLine="Activity.Finish";
parent.mostCurrent._activity.Finish();
 //BA.debugLineNum = 343;BA.debugLine="StartActivity(Form_Main)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(parent.mostCurrent._form_main.getObject()));
 if (true) break;

case 29:
//C
this.state = 30;
;
 if (true) break;

case 30:
//C
this.state = -1;
;
 //BA.debugLineNum = 350;BA.debugLine="Job.Release";
_job._release /*String*/ ();
 //BA.debugLineNum = 351;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public static void  _msgbox_result(int _result) throws Exception{
}
public static String  _timer1_tick() throws Exception{
 //BA.debugLineNum = 207;BA.debugLine="Sub timer1_Tick";
 //BA.debugLineNum = 208;BA.debugLine="If lang = \"es\" Then";
if ((_lang).equals("es")) { 
 //BA.debugLineNum = 209;BA.debugLine="lblEstado.Text = \"Comprobando conexión a interne";
mostCurrent._lblestado.setText(BA.ObjectToCharSequence("Comprobando conexión a internet"));
 }else if((_lang).equals("en")) { 
 //BA.debugLineNum = 211;BA.debugLine="lblEstado.Text = \"Checking the connection to the";
mostCurrent._lblestado.setText(BA.ObjectToCharSequence("Checking the connection to the internet"));
 };
 //BA.debugLineNum = 216;BA.debugLine="TestInternet";
_testinternet();
 //BA.debugLineNum = 218;BA.debugLine="timer1.Enabled = False";
_timer1.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 219;BA.debugLine="End Sub";
return "";
}
}
