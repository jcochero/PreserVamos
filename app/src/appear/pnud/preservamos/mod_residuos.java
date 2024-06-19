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

public class mod_residuos extends Activity implements B4AActivity{
	public static mod_residuos mostCurrent;
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
			processBA = new BA(this.getApplicationContext(), null, null, "appear.pnud.preservamos", "appear.pnud.preservamos.mod_residuos");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
            
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (mod_residuos).");
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
		activityBA = new BA(this, layout, processBA, "appear.pnud.preservamos", "appear.pnud.preservamos.mod_residuos");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "appear.pnud.preservamos.mod_residuos", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density, mostCurrent);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (mod_residuos) Create " + (isFirst ? "(first time)" : "") + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (mod_residuos) Resume **");
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
		return mod_residuos.class;
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
            BA.LogInfo("** Activity (mod_residuos) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
        else
            BA.LogInfo("** Activity (mod_residuos) Pause event (activity is not paused). **");
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
            mod_residuos mc = mostCurrent;
			if (mc == null || mc != activity.get())
				return;
			processBA.setActivityPaused(false);
            BA.LogInfo("** Activity (mod_residuos) Resume **");
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
public static String _currentproject = "";
public static String _fullidcurrentproject = "";
public static String _datecurrentproject = "";
public static String _tipoambiente = "";
public static String _origen = "";
public static String _geopartido = "";
public static String _lat = "";
public static String _lng = "";
public static String _foto1_path = "";
public static anywheresoftware.b4a.objects.Timer _timerenvio = null;
public static com.spinter.uploadfilephp.UploadFilePhp _up1 = null;
public anywheresoftware.b4a.objects.TabStripViewPager _tabcontainer = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblcirculopos1 = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblcirculopos2 = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblcirculopos3 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnnext = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnprev = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblcirculopos4 = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblrojo = null;
public anywheresoftware.b4a.objects.ScrollViewWrapper _scrollusos = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnokbasico1 = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblrojo_q5_1 = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imgriollanura = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imglaguna = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imgq5_1_a = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imgq5_1_b = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imgq5_1_d = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imgq5_1_c = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imgq5_1_e = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imgq5_1_f = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imgq5_1_h = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imgq5_1_g = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imgq5_1_i = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imgq5_1_j = null;
public static String _coordenadas_string = "";
public anywheresoftware.b4a.objects.LabelWrapper _lblfecha = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblcoordenadascarga = null;
public anywheresoftware.b4a.objects.PanelWrapper _fondogris = null;
public anywheresoftware.b4a.objects.PanelWrapper _panelcomofunciona = null;
public static int _listtotal = 0;
public static int _listplastico1 = 0;
public static int _listplastico2 = 0;
public static int _listplastico3 = 0;
public static int _listplastico4 = 0;
public static int _listplastico5 = 0;
public static int _listplastico6 = 0;
public static int _listplastico7 = 0;
public static int _listplastico8 = 0;
public static int _listplastico9 = 0;
public static int _listcarton = 0;
public static int _listmetales = 0;
public static int _listvidrios = 0;
public static int _listotros = 0;
public anywheresoftware.b4a.objects.PanelWrapper _panelsubplasticos = null;
public appear.pnud.preservamos.b4xplusminus _b4xplusminus1 = null;
public appear.pnud.preservamos.b4xplusminus _b4xplusminus2 = null;
public appear.pnud.preservamos.b4xplusminus _b4xplusminus3 = null;
public appear.pnud.preservamos.b4xplusminus _b4xplusminus4 = null;
public appear.pnud.preservamos.b4xplusminus _b4xplusminus5 = null;
public appear.pnud.preservamos.b4xplusminus _b4xplusminus6 = null;
public appear.pnud.preservamos.b4xplusminus _b4xplusminus7 = null;
public appear.pnud.preservamos.b4xplusminus _b4xplusminus8 = null;
public appear.pnud.preservamos.b4xplusminus _b4xplusminus9 = null;
public appear.pnud.preservamos.b4xplusminus _b4xplusminus10 = null;
public appear.pnud.preservamos.b4xplusminus _b4xplusminus11 = null;
public appear.pnud.preservamos.b4xplusminus _b4xplusminus12 = null;
public appear.pnud.preservamos.b4xplusminus _b4xplusminus13 = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblcontadorcarga = null;
public anywheresoftware.b4a.objects.ScrollViewWrapper _scrollview1 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnplasticos = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbltiempocarga = null;
public static String _valorind1 = "";
public static String _tiporio = "";
public anywheresoftware.b4a.objects.Timer _timercarga = null;
public static int _timecarga = 0;
public anywheresoftware.b4a.objects.LabelWrapper _lbltiempocarga_resumen = null;
public appear.pnud.preservamos.b4xfloattextfield _txtdistanciacaminada = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btncarton = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnmetales = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnotros = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnvidrios = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnokcarga = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnguiacategorias = null;
public static String _proyectoidenviar = "";
public anywheresoftware.b4a.objects.collections.List _files = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblcoordenadastitle = null;
public anywheresoftware.b4a.objects.LabelWrapper _borderfotos1 = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imgfoto1 = null;
public anywheresoftware.b4a.objects.ProgressBarWrapper _progressbar1 = null;
public anywheresoftware.b4a.objects.ListViewWrapper _listresumen = null;
public static String _foto1 = "";
public static int _totalfotos = 0;
public static boolean _foto1sent = false;
public appear.pnud.preservamos.circularprogressbar _foto1progressbar = null;
public static int _fotosenviadas = 0;
public anywheresoftware.b4a.phone.Phone.PhoneWakeState _pw = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imglisto = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnokresumen1 = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblstatusresiduos = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblresumenlisttitle = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnverdetalle = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblcontadorresumen = null;
public anywheresoftware.b4a.objects.LabelWrapper _label1 = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblpiezas = null;
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
public appear.pnud.preservamos.frmmapa _frmmapa = null;
public appear.pnud.preservamos.frmmunicipioestadisticas _frmmunicipioestadisticas = null;
public appear.pnud.preservamos.frmpoliticadatos _frmpoliticadatos = null;
public appear.pnud.preservamos.frmtiporeporte _frmtiporeporte = null;
public appear.pnud.preservamos.imagedownloader _imagedownloader = null;
public appear.pnud.preservamos.inatcheck _inatcheck = null;
public appear.pnud.preservamos.mod_hidro _mod_hidro = null;
public appear.pnud.preservamos.mod_hidro_fotos _mod_hidro_fotos = null;
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
public static String  _activity_create(boolean _firsttime) throws Exception{
 //BA.debugLineNum = 132;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
 //BA.debugLineNum = 134;BA.debugLine="Activity.LoadLayout(\"mod_residuos_container\")";
mostCurrent._activity.LoadLayout("mod_residuos_container",mostCurrent.activityBA);
 //BA.debugLineNum = 135;BA.debugLine="tiporio = \"\"";
mostCurrent._tiporio = "";
 //BA.debugLineNum = 136;BA.debugLine="valorind1 = \"\"";
mostCurrent._valorind1 = "";
 //BA.debugLineNum = 138;BA.debugLine="tabContainer.LoadLayout(\"mod_residuos_instruccion";
mostCurrent._tabcontainer.LoadLayout("mod_residuos_instruccion_1",BA.ObjectToCharSequence("1"));
 //BA.debugLineNum = 139;BA.debugLine="tabContainer.LoadLayout(\"mod_residuos_instruccion";
mostCurrent._tabcontainer.LoadLayout("mod_residuos_instruccion_2",BA.ObjectToCharSequence("2"));
 //BA.debugLineNum = 140;BA.debugLine="tabContainer.LoadLayout(\"mod_residuos_instruccion";
mostCurrent._tabcontainer.LoadLayout("mod_residuos_instruccion_3",BA.ObjectToCharSequence("3"));
 //BA.debugLineNum = 141;BA.debugLine="tabContainer.LoadLayout(\"mod_residuos_instruccion";
mostCurrent._tabcontainer.LoadLayout("mod_residuos_instruccion_4",BA.ObjectToCharSequence("4"));
 //BA.debugLineNum = 144;BA.debugLine="End Sub";
return "";
}
public static boolean  _activity_keypress(int _keycode) throws Exception{
 //BA.debugLineNum = 155;BA.debugLine="Sub Activity_KeyPress (KeyCode As Int) As Boolean";
 //BA.debugLineNum = 156;BA.debugLine="If KeyCode = KeyCodes.KEYCODE_BACK Then";
if (_keycode==anywheresoftware.b4a.keywords.Common.KeyCodes.KEYCODE_BACK) { 
 //BA.debugLineNum = 157;BA.debugLine="closeAppMsgBox";
_closeappmsgbox();
 //BA.debugLineNum = 158;BA.debugLine="Return True";
if (true) return anywheresoftware.b4a.keywords.Common.True;
 }else {
 //BA.debugLineNum = 160;BA.debugLine="Return False";
if (true) return anywheresoftware.b4a.keywords.Common.False;
 };
 //BA.debugLineNum = 162;BA.debugLine="End Sub";
return false;
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 150;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 152;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 146;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 148;BA.debugLine="End Sub";
return "";
}
public static String  _actualizarcoordenadas() throws Exception{
 //BA.debugLineNum = 454;BA.debugLine="Sub ActualizarCoordenadas";
 //BA.debugLineNum = 455;BA.debugLine="coordenadas_string = \"Coordenadas: \" & Round2(lat";
mostCurrent._coordenadas_string = "Coordenadas: "+BA.NumberToString(anywheresoftware.b4a.keywords.Common.Round2((double)(Double.parseDouble(_lat)),(int) (3)))+" | "+BA.NumberToString(anywheresoftware.b4a.keywords.Common.Round2((double)(Double.parseDouble(_lng)),(int) (3)));
 //BA.debugLineNum = 456;BA.debugLine="lblCoordenadasCarga.Text = coordenadas_string";
mostCurrent._lblcoordenadascarga.setText(BA.ObjectToCharSequence(mostCurrent._coordenadas_string));
 //BA.debugLineNum = 457;BA.debugLine="If foto1 = \"\" Then";
if ((mostCurrent._foto1).equals("")) { 
 //BA.debugLineNum = 458;BA.debugLine="StartActivity(mod_Residuos_Fotos)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._mod_residuos_fotos.getObject()));
 };
 //BA.debugLineNum = 460;BA.debugLine="End Sub";
return "";
}
public static String  _b4xplusminus1_valuechanged(Object _value) throws Exception{
 //BA.debugLineNum = 816;BA.debugLine="Private Sub B4XPlusMinus1_ValueChanged (Value As O";
 //BA.debugLineNum = 817;BA.debugLine="listCarton = Value";
_listcarton = (int)(BA.ObjectToNumber(_value));
 //BA.debugLineNum = 818;BA.debugLine="End Sub";
return "";
}
public static String  _b4xplusminus10_valuechanged(Object _value) throws Exception{
 //BA.debugLineNum = 780;BA.debugLine="Private Sub B4XPlusMinus10_ValueChanged (Value As";
 //BA.debugLineNum = 781;BA.debugLine="listPlastico7 = Value";
_listplastico7 = (int)(BA.ObjectToNumber(_value));
 //BA.debugLineNum = 782;BA.debugLine="End Sub";
return "";
}
public static String  _b4xplusminus11_valuechanged(Object _value) throws Exception{
 //BA.debugLineNum = 776;BA.debugLine="Private Sub B4XPlusMinus11_ValueChanged (Value As";
 //BA.debugLineNum = 777;BA.debugLine="listPlastico8 = Value";
_listplastico8 = (int)(BA.ObjectToNumber(_value));
 //BA.debugLineNum = 778;BA.debugLine="End Sub";
return "";
}
public static String  _b4xplusminus12_valuechanged(Object _value) throws Exception{
 //BA.debugLineNum = 772;BA.debugLine="Private Sub B4XPlusMinus12_ValueChanged (Value As";
 //BA.debugLineNum = 773;BA.debugLine="listPlastico9 = Value";
_listplastico9 = (int)(BA.ObjectToNumber(_value));
 //BA.debugLineNum = 774;BA.debugLine="End Sub";
return "";
}
public static String  _b4xplusminus13_valuechanged(Object _value) throws Exception{
 //BA.debugLineNum = 768;BA.debugLine="Private Sub B4XPlusMinus13_ValueChanged (Value As";
 //BA.debugLineNum = 769;BA.debugLine="listOtros = Value";
_listotros = (int)(BA.ObjectToNumber(_value));
 //BA.debugLineNum = 770;BA.debugLine="End Sub";
return "";
}
public static String  _b4xplusminus2_valuechanged(Object _value) throws Exception{
 //BA.debugLineNum = 812;BA.debugLine="Private Sub B4XPlusMinus2_ValueChanged (Value As O";
 //BA.debugLineNum = 813;BA.debugLine="listMetales = Value";
_listmetales = (int)(BA.ObjectToNumber(_value));
 //BA.debugLineNum = 814;BA.debugLine="End Sub";
return "";
}
public static String  _b4xplusminus3_valuechanged(Object _value) throws Exception{
 //BA.debugLineNum = 808;BA.debugLine="Private Sub B4XPlusMinus3_ValueChanged (Value As O";
 //BA.debugLineNum = 809;BA.debugLine="listVidrios = Value";
_listvidrios = (int)(BA.ObjectToNumber(_value));
 //BA.debugLineNum = 810;BA.debugLine="End Sub";
return "";
}
public static String  _b4xplusminus4_valuechanged(Object _value) throws Exception{
 //BA.debugLineNum = 804;BA.debugLine="Private Sub B4XPlusMinus4_ValueChanged (Value As O";
 //BA.debugLineNum = 805;BA.debugLine="listPlastico1 = Value";
_listplastico1 = (int)(BA.ObjectToNumber(_value));
 //BA.debugLineNum = 806;BA.debugLine="End Sub";
return "";
}
public static String  _b4xplusminus5_valuechanged(Object _value) throws Exception{
 //BA.debugLineNum = 800;BA.debugLine="Private Sub B4XPlusMinus5_ValueChanged (Value As O";
 //BA.debugLineNum = 801;BA.debugLine="listPlastico2 = Value";
_listplastico2 = (int)(BA.ObjectToNumber(_value));
 //BA.debugLineNum = 802;BA.debugLine="End Sub";
return "";
}
public static String  _b4xplusminus6_valuechanged(Object _value) throws Exception{
 //BA.debugLineNum = 796;BA.debugLine="Private Sub B4XPlusMinus6_ValueChanged (Value As O";
 //BA.debugLineNum = 797;BA.debugLine="listPlastico3 = Value";
_listplastico3 = (int)(BA.ObjectToNumber(_value));
 //BA.debugLineNum = 798;BA.debugLine="End Sub";
return "";
}
public static String  _b4xplusminus7_valuechanged(Object _value) throws Exception{
 //BA.debugLineNum = 792;BA.debugLine="Private Sub B4XPlusMinus7_ValueChanged (Value As O";
 //BA.debugLineNum = 793;BA.debugLine="listPlastico4 = Value";
_listplastico4 = (int)(BA.ObjectToNumber(_value));
 //BA.debugLineNum = 794;BA.debugLine="End Sub";
return "";
}
public static String  _b4xplusminus8_valuechanged(Object _value) throws Exception{
 //BA.debugLineNum = 788;BA.debugLine="Private Sub B4XPlusMinus8_ValueChanged (Value As O";
 //BA.debugLineNum = 789;BA.debugLine="listPlastico5 = Value";
_listplastico5 = (int)(BA.ObjectToNumber(_value));
 //BA.debugLineNum = 790;BA.debugLine="End Sub";
return "";
}
public static String  _b4xplusminus9_valuechanged(Object _value) throws Exception{
 //BA.debugLineNum = 784;BA.debugLine="Private Sub B4XPlusMinus9_ValueChanged (Value As O";
 //BA.debugLineNum = 785;BA.debugLine="listPlastico6 = Value";
_listplastico6 = (int)(BA.ObjectToNumber(_value));
 //BA.debugLineNum = 786;BA.debugLine="End Sub";
return "";
}
public static String  _btnarticulodomestico_click() throws Exception{
 //BA.debugLineNum = 657;BA.debugLine="Private Sub btnArticuloDomestico_Click";
 //BA.debugLineNum = 658;BA.debugLine="listTotal = listTotal + 1";
_listtotal = (int) (_listtotal+1);
 //BA.debugLineNum = 659;BA.debugLine="lblContadorCarga.Text = listTotal";
mostCurrent._lblcontadorcarga.setText(BA.ObjectToCharSequence(_listtotal));
 //BA.debugLineNum = 660;BA.debugLine="listPlastico2 = listPlastico2 + 1";
_listplastico2 = (int) (_listplastico2+1);
 //BA.debugLineNum = 661;BA.debugLine="panelSubPlasticos.RemoveView";
mostCurrent._panelsubplasticos.RemoveView();
 //BA.debugLineNum = 662;BA.debugLine="fondogris.RemoveView";
mostCurrent._fondogris.RemoveView();
 //BA.debugLineNum = 663;BA.debugLine="lblStatusResiduos.Text = \"Residuo de tipo 'Artícu";
mostCurrent._lblstatusresiduos.setText(BA.ObjectToCharSequence("Residuo de tipo 'Artículo doméstico' agregado"));
 //BA.debugLineNum = 664;BA.debugLine="btnPlasticos.enabled = True";
mostCurrent._btnplasticos.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 665;BA.debugLine="btnMetales.Visible = True";
mostCurrent._btnmetales.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 666;BA.debugLine="btnCarton.Visible = True";
mostCurrent._btncarton.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 667;BA.debugLine="btnVidrios.Visible= True";
mostCurrent._btnvidrios.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 668;BA.debugLine="btnOtros.Visible = True";
mostCurrent._btnotros.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 669;BA.debugLine="btnPlasticos.Visible = True";
mostCurrent._btnplasticos.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 670;BA.debugLine="btnGuiaCategorias.Visible = True";
mostCurrent._btnguiacategorias.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 671;BA.debugLine="End Sub";
return "";
}
public static String  _btnarticuloshigiene_click() throws Exception{
 //BA.debugLineNum = 673;BA.debugLine="Private Sub btnArticulosHigiene_Click";
 //BA.debugLineNum = 674;BA.debugLine="listTotal = listTotal + 1";
_listtotal = (int) (_listtotal+1);
 //BA.debugLineNum = 675;BA.debugLine="lblContadorCarga.Text = listTotal";
mostCurrent._lblcontadorcarga.setText(BA.ObjectToCharSequence(_listtotal));
 //BA.debugLineNum = 676;BA.debugLine="listPlastico1 = listPlastico1 + 1";
_listplastico1 = (int) (_listplastico1+1);
 //BA.debugLineNum = 677;BA.debugLine="panelSubPlasticos.RemoveView";
mostCurrent._panelsubplasticos.RemoveView();
 //BA.debugLineNum = 678;BA.debugLine="fondogris.RemoveView";
mostCurrent._fondogris.RemoveView();
 //BA.debugLineNum = 679;BA.debugLine="lblStatusResiduos.Text = \"Residuo de tipo 'Artícu";
mostCurrent._lblstatusresiduos.setText(BA.ObjectToCharSequence("Residuo de tipo 'Artículo de higiene' agregado"));
 //BA.debugLineNum = 680;BA.debugLine="btnPlasticos.enabled = True";
mostCurrent._btnplasticos.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 681;BA.debugLine="btnMetales.Visible = True";
mostCurrent._btnmetales.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 682;BA.debugLine="btnCarton.Visible = True";
mostCurrent._btncarton.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 683;BA.debugLine="btnVidrios.Visible= True";
mostCurrent._btnvidrios.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 684;BA.debugLine="btnOtros.Visible = True";
mostCurrent._btnotros.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 685;BA.debugLine="btnPlasticos.Visible = True";
mostCurrent._btnplasticos.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 686;BA.debugLine="btnGuiaCategorias.Visible = True";
mostCurrent._btnguiacategorias.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 687;BA.debugLine="End Sub";
return "";
}
public static String  _btnbotella_click() throws Exception{
 //BA.debugLineNum = 625;BA.debugLine="Private Sub btnBotella_Click";
 //BA.debugLineNum = 626;BA.debugLine="listTotal = listTotal + 1";
_listtotal = (int) (_listtotal+1);
 //BA.debugLineNum = 627;BA.debugLine="lblContadorCarga.Text = listTotal";
mostCurrent._lblcontadorcarga.setText(BA.ObjectToCharSequence(_listtotal));
 //BA.debugLineNum = 628;BA.debugLine="listPlastico4 = listPlastico4 + 1";
_listplastico4 = (int) (_listplastico4+1);
 //BA.debugLineNum = 629;BA.debugLine="panelSubPlasticos.RemoveView";
mostCurrent._panelsubplasticos.RemoveView();
 //BA.debugLineNum = 630;BA.debugLine="fondogris.RemoveView";
mostCurrent._fondogris.RemoveView();
 //BA.debugLineNum = 631;BA.debugLine="lblStatusResiduos.Text = \"Residuo de tipo 'Botell";
mostCurrent._lblstatusresiduos.setText(BA.ObjectToCharSequence("Residuo de tipo 'Botella' agregado"));
 //BA.debugLineNum = 632;BA.debugLine="btnPlasticos.enabled = True";
mostCurrent._btnplasticos.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 633;BA.debugLine="btnMetales.Visible = True";
mostCurrent._btnmetales.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 634;BA.debugLine="btnCarton.Visible = True";
mostCurrent._btncarton.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 635;BA.debugLine="btnVidrios.Visible= True";
mostCurrent._btnvidrios.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 636;BA.debugLine="btnOtros.Visible = True";
mostCurrent._btnotros.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 637;BA.debugLine="btnPlasticos.Visible = True";
mostCurrent._btnplasticos.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 638;BA.debugLine="btnGuiaCategorias.Visible = True";
mostCurrent._btnguiacategorias.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 639;BA.debugLine="End Sub";
return "";
}
public static String  _btncarton_click() throws Exception{
 //BA.debugLineNum = 514;BA.debugLine="Private Sub btnCarton_Click";
 //BA.debugLineNum = 515;BA.debugLine="listTotal = listTotal + 1";
_listtotal = (int) (_listtotal+1);
 //BA.debugLineNum = 516;BA.debugLine="lblContadorCarga.Text = listTotal";
mostCurrent._lblcontadorcarga.setText(BA.ObjectToCharSequence(_listtotal));
 //BA.debugLineNum = 517;BA.debugLine="listCarton = listCarton +1";
_listcarton = (int) (_listcarton+1);
 //BA.debugLineNum = 518;BA.debugLine="lblStatusResiduos.Text = \"Residuo de tipo 'Cartón";
mostCurrent._lblstatusresiduos.setText(BA.ObjectToCharSequence("Residuo de tipo 'Cartón o papel' agregado"));
 //BA.debugLineNum = 519;BA.debugLine="End Sub";
return "";
}
public static String  _btncigarrillo_click() throws Exception{
 //BA.debugLineNum = 577;BA.debugLine="Private Sub btnCigarrillo_Click";
 //BA.debugLineNum = 578;BA.debugLine="listTotal = listTotal + 1";
_listtotal = (int) (_listtotal+1);
 //BA.debugLineNum = 579;BA.debugLine="lblContadorCarga.Text = listTotal";
mostCurrent._lblcontadorcarga.setText(BA.ObjectToCharSequence(_listtotal));
 //BA.debugLineNum = 580;BA.debugLine="listPlastico7 = listPlastico7 + 1";
_listplastico7 = (int) (_listplastico7+1);
 //BA.debugLineNum = 581;BA.debugLine="panelSubPlasticos.RemoveView";
mostCurrent._panelsubplasticos.RemoveView();
 //BA.debugLineNum = 582;BA.debugLine="fondogris.RemoveView";
mostCurrent._fondogris.RemoveView();
 //BA.debugLineNum = 583;BA.debugLine="lblStatusResiduos.Text = \"Residuo de tipo 'Cigarr";
mostCurrent._lblstatusresiduos.setText(BA.ObjectToCharSequence("Residuo de tipo 'Cigarrillo' agregado"));
 //BA.debugLineNum = 584;BA.debugLine="btnPlasticos.enabled = True";
mostCurrent._btnplasticos.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 585;BA.debugLine="btnMetales.Visible = True";
mostCurrent._btnmetales.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 586;BA.debugLine="btnCarton.Visible = True";
mostCurrent._btncarton.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 587;BA.debugLine="btnVidrios.Visible= True";
mostCurrent._btnvidrios.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 588;BA.debugLine="btnOtros.Visible = True";
mostCurrent._btnotros.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 589;BA.debugLine="btnPlasticos.Visible = True";
mostCurrent._btnplasticos.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 590;BA.debugLine="btnGuiaCategorias.Visible = True";
mostCurrent._btnguiacategorias.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 591;BA.debugLine="End Sub";
return "";
}
public static String  _btncomenzarmodulo_click() throws Exception{
 //BA.debugLineNum = 259;BA.debugLine="Private Sub btnComenzarModulo_Click";
 //BA.debugLineNum = 260;BA.debugLine="cargar_basicos";
_cargar_basicos();
 //BA.debugLineNum = 261;BA.debugLine="End Sub";
return "";
}
public static String  _btndiezfragmentos_click() throws Exception{
 //BA.debugLineNum = 545;BA.debugLine="Private Sub btnDiezFragmentos_Click";
 //BA.debugLineNum = 546;BA.debugLine="listTotal = listTotal + 1";
_listtotal = (int) (_listtotal+1);
 //BA.debugLineNum = 547;BA.debugLine="lblContadorCarga.Text = listTotal";
mostCurrent._lblcontadorcarga.setText(BA.ObjectToCharSequence(_listtotal));
 //BA.debugLineNum = 548;BA.debugLine="listPlastico9 = listPlastico9 + 1";
_listplastico9 = (int) (_listplastico9+1);
 //BA.debugLineNum = 549;BA.debugLine="panelSubPlasticos.RemoveView";
mostCurrent._panelsubplasticos.RemoveView();
 //BA.debugLineNum = 550;BA.debugLine="fondogris.RemoveView";
mostCurrent._fondogris.RemoveView();
 //BA.debugLineNum = 551;BA.debugLine="lblStatusResiduos.Text = \"Residuo de tipo 'Diez f";
mostCurrent._lblstatusresiduos.setText(BA.ObjectToCharSequence("Residuo de tipo 'Diez fragmentos' agregado"));
 //BA.debugLineNum = 552;BA.debugLine="btnPlasticos.enabled = True";
mostCurrent._btnplasticos.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 553;BA.debugLine="btnMetales.Visible = True";
mostCurrent._btnmetales.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 554;BA.debugLine="btnCarton.Visible = True";
mostCurrent._btncarton.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 555;BA.debugLine="btnVidrios.Visible= True";
mostCurrent._btnvidrios.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 556;BA.debugLine="btnOtros.Visible = True";
mostCurrent._btnotros.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 557;BA.debugLine="btnPlasticos.Visible = True";
mostCurrent._btnplasticos.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 558;BA.debugLine="btnGuiaCategorias.Visible = True";
mostCurrent._btnguiacategorias.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 559;BA.debugLine="End Sub";
return "";
}
public static String  _btneditcoordenadas_click() throws Exception{
 //BA.debugLineNum = 462;BA.debugLine="Private Sub btnEditCoordenadas_Click";
 //BA.debugLineNum = 464;BA.debugLine="frmLocalizacion.origen = \"modulo_residuos\"";
mostCurrent._frmlocalizacion._origen /*String*/  = "modulo_residuos";
 //BA.debugLineNum = 465;BA.debugLine="StartActivity(frmLocalizacion)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._frmlocalizacion.getObject()));
 //BA.debugLineNum = 466;BA.debugLine="End Sub";
return "";
}
public static String  _btnenvases_click() throws Exception{
 //BA.debugLineNum = 641;BA.debugLine="Private Sub btnEnvases_Click";
 //BA.debugLineNum = 642;BA.debugLine="listTotal = listTotal + 1";
_listtotal = (int) (_listtotal+1);
 //BA.debugLineNum = 643;BA.debugLine="lblContadorCarga.Text = listTotal";
mostCurrent._lblcontadorcarga.setText(BA.ObjectToCharSequence(_listtotal));
 //BA.debugLineNum = 644;BA.debugLine="listPlastico3 = listPlastico3 + 1";
_listplastico3 = (int) (_listplastico3+1);
 //BA.debugLineNum = 645;BA.debugLine="panelSubPlasticos.RemoveView";
mostCurrent._panelsubplasticos.RemoveView();
 //BA.debugLineNum = 646;BA.debugLine="fondogris.RemoveView";
mostCurrent._fondogris.RemoveView();
 //BA.debugLineNum = 647;BA.debugLine="lblStatusResiduos.Text = \"Residuo de tipo 'Envase";
mostCurrent._lblstatusresiduos.setText(BA.ObjectToCharSequence("Residuo de tipo 'Envases de comida' agregado"));
 //BA.debugLineNum = 648;BA.debugLine="btnPlasticos.enabled = True";
mostCurrent._btnplasticos.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 649;BA.debugLine="btnMetales.Visible = True";
mostCurrent._btnmetales.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 650;BA.debugLine="btnCarton.Visible = True";
mostCurrent._btncarton.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 651;BA.debugLine="btnVidrios.Visible= True";
mostCurrent._btnvidrios.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 652;BA.debugLine="btnOtros.Visible = True";
mostCurrent._btnotros.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 653;BA.debugLine="btnPlasticos.Visible = True";
mostCurrent._btnplasticos.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 654;BA.debugLine="btnGuiaCategorias.Visible = True";
mostCurrent._btnguiacategorias.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 655;BA.debugLine="End Sub";
return "";
}
public static String  _btnguiacategorias_click() throws Exception{
 //BA.debugLineNum = 477;BA.debugLine="Private Sub btnGuiaCategorias_Click";
 //BA.debugLineNum = 480;BA.debugLine="End Sub";
return "";
}
public static String  _btnmetales_click() throws Exception{
 //BA.debugLineNum = 507;BA.debugLine="Private Sub btnMetales_Click";
 //BA.debugLineNum = 508;BA.debugLine="listTotal = listTotal + 1";
_listtotal = (int) (_listtotal+1);
 //BA.debugLineNum = 509;BA.debugLine="lblContadorCarga.Text = listTotal";
mostCurrent._lblcontadorcarga.setText(BA.ObjectToCharSequence(_listtotal));
 //BA.debugLineNum = 510;BA.debugLine="listMetales = listMetales + 1";
_listmetales = (int) (_listmetales+1);
 //BA.debugLineNum = 511;BA.debugLine="lblStatusResiduos.Text = \"Residuo de tipo 'Metal'";
mostCurrent._lblstatusresiduos.setText(BA.ObjectToCharSequence("Residuo de tipo 'Metal' agregado"));
 //BA.debugLineNum = 512;BA.debugLine="End Sub";
return "";
}
public static String  _btnnext_click() throws Exception{
 //BA.debugLineNum = 219;BA.debugLine="Private Sub btnNext_Click";
 //BA.debugLineNum = 220;BA.debugLine="If tabContainer.CurrentPage = 0 Then";
if (mostCurrent._tabcontainer.getCurrentPage()==0) { 
 //BA.debugLineNum = 221;BA.debugLine="tabContainer.ScrollTo(1, True)";
mostCurrent._tabcontainer.ScrollTo((int) (1),anywheresoftware.b4a.keywords.Common.True);
 }else if(mostCurrent._tabcontainer.getCurrentPage()==1) { 
 //BA.debugLineNum = 223;BA.debugLine="tabContainer.ScrollTo(2, True)";
mostCurrent._tabcontainer.ScrollTo((int) (2),anywheresoftware.b4a.keywords.Common.True);
 }else if(mostCurrent._tabcontainer.getCurrentPage()==2) { 
 //BA.debugLineNum = 225;BA.debugLine="tabContainer.ScrollTo(3, True)";
mostCurrent._tabcontainer.ScrollTo((int) (3),anywheresoftware.b4a.keywords.Common.True);
 }else if(mostCurrent._tabcontainer.getCurrentPage()==3) { 
 //BA.debugLineNum = 227;BA.debugLine="tabContainer.ScrollTo(4, True)";
mostCurrent._tabcontainer.ScrollTo((int) (4),anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 229;BA.debugLine="End Sub";
return "";
}
public static String  _btnokbasico1_click() throws Exception{
 //BA.debugLineNum = 345;BA.debugLine="Private Sub btnOkBasico1_Click";
 //BA.debugLineNum = 346;BA.debugLine="If tiporio = \"\" Then";
if ((mostCurrent._tiporio).equals("")) { 
 //BA.debugLineNum = 347;BA.debugLine="ToastMessageShow(\"Elije un tipo de ambiente ante";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Elije un tipo de ambiente antes de seguir"),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 348;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 350;BA.debugLine="If valorind1 = \"\" Then";
if ((mostCurrent._valorind1).equals("")) { 
 //BA.debugLineNum = 351;BA.debugLine="ToastMessageShow(\"Elije un uso del suelo en tu z";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Elije un uso del suelo en tu zona antes de seguir"),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 352;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 354;BA.debugLine="create_reporte";
_create_reporte();
 //BA.debugLineNum = 355;BA.debugLine="End Sub";
return "";
}
public static String  _btnokcarga_click() throws Exception{
 //BA.debugLineNum = 689;BA.debugLine="Private Sub btnOkCarga_Click";
 //BA.debugLineNum = 693;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 694;BA.debugLine="Activity.LoadLayout(\"mod_residuos_resumen\")";
mostCurrent._activity.LoadLayout("mod_residuos_resumen",mostCurrent.activityBA);
 //BA.debugLineNum = 695;BA.debugLine="lblContadorResumen.Text = lblContadorCarga.Text";
mostCurrent._lblcontadorresumen.setText(BA.ObjectToCharSequence(mostCurrent._lblcontadorcarga.getText()));
 //BA.debugLineNum = 697;BA.debugLine="timerCarga.Enabled = False";
mostCurrent._timercarga.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 698;BA.debugLine="lblTiempoCarga_Resumen.Text = \"Tiempo de relevami";
mostCurrent._lbltiempocarga_resumen.setText(BA.ObjectToCharSequence("Tiempo de relevamiento: "+BA.NumberToString(_timecarga)+" segundos"));
 //BA.debugLineNum = 700;BA.debugLine="lblFecha.Text = datecurrentproject";
mostCurrent._lblfecha.setText(BA.ObjectToCharSequence(_datecurrentproject));
 //BA.debugLineNum = 701;BA.debugLine="lblCoordenadasTitle.Text = lblCoordenadasCarga.Te";
mostCurrent._lblcoordenadastitle.setText(BA.ObjectToCharSequence(mostCurrent._lblcoordenadascarga.getText()));
 //BA.debugLineNum = 702;BA.debugLine="ScrollView1.Panel.LoadLayout(\"mod_residuos_resume";
mostCurrent._scrollview1.getPanel().LoadLayout("mod_residuos_resumen_list",mostCurrent.activityBA);
 //BA.debugLineNum = 704;BA.debugLine="ProgressBar1.Visible = False";
mostCurrent._progressbar1.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 705;BA.debugLine="borderFotos1.Visible = True";
mostCurrent._borderfotos1.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 706;BA.debugLine="imgFoto1.Visible = True";
mostCurrent._imgfoto1.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 707;BA.debugLine="If File.Exists(Starter.savedir, foto1_path & \".jp";
if (anywheresoftware.b4a.keywords.Common.File.Exists(mostCurrent._starter._savedir /*String*/ ,_foto1_path+".jpg")) { 
 //BA.debugLineNum = 708;BA.debugLine="imgFoto1.Bitmap = Null";
mostCurrent._imgfoto1.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null));
 //BA.debugLineNum = 709;BA.debugLine="imgFoto1.Bitmap = LoadBitmapSample(Starter.saved";
mostCurrent._imgfoto1.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(mostCurrent._starter._savedir /*String*/ ,_foto1_path+".jpg",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 710;BA.debugLine="imgFoto1.Visible = True";
mostCurrent._imgfoto1.setVisible(anywheresoftware.b4a.keywords.Common.True);
 }else {
 //BA.debugLineNum = 712;BA.debugLine="imgFoto1.Visible = False";
mostCurrent._imgfoto1.setVisible(anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 716;BA.debugLine="B4XPlusMinus13.SetNumericRange(0,999, 1)";
mostCurrent._b4xplusminus13._setnumericrange /*String*/ (0,999,1);
 //BA.debugLineNum = 717;BA.debugLine="B4XPlusMinus12.SetNumericRange(0,999, 1)";
mostCurrent._b4xplusminus12._setnumericrange /*String*/ (0,999,1);
 //BA.debugLineNum = 718;BA.debugLine="B4XPlusMinus11.SetNumericRange(0,999, 1)";
mostCurrent._b4xplusminus11._setnumericrange /*String*/ (0,999,1);
 //BA.debugLineNum = 719;BA.debugLine="B4XPlusMinus10.SetNumericRange(0,999, 1)";
mostCurrent._b4xplusminus10._setnumericrange /*String*/ (0,999,1);
 //BA.debugLineNum = 720;BA.debugLine="B4XPlusMinus9.SetNumericRange(0,999, 1)";
mostCurrent._b4xplusminus9._setnumericrange /*String*/ (0,999,1);
 //BA.debugLineNum = 721;BA.debugLine="B4XPlusMinus8.SetNumericRange(0,999, 1)";
mostCurrent._b4xplusminus8._setnumericrange /*String*/ (0,999,1);
 //BA.debugLineNum = 722;BA.debugLine="B4XPlusMinus7.SetNumericRange(0,999, 1)";
mostCurrent._b4xplusminus7._setnumericrange /*String*/ (0,999,1);
 //BA.debugLineNum = 723;BA.debugLine="B4XPlusMinus6.SetNumericRange(0,999, 1)";
mostCurrent._b4xplusminus6._setnumericrange /*String*/ (0,999,1);
 //BA.debugLineNum = 724;BA.debugLine="B4XPlusMinus5.SetNumericRange(0,999, 1)";
mostCurrent._b4xplusminus5._setnumericrange /*String*/ (0,999,1);
 //BA.debugLineNum = 725;BA.debugLine="B4XPlusMinus4.SetNumericRange(0,999, 1)";
mostCurrent._b4xplusminus4._setnumericrange /*String*/ (0,999,1);
 //BA.debugLineNum = 726;BA.debugLine="B4XPlusMinus3.SetNumericRange(0,999, 1)";
mostCurrent._b4xplusminus3._setnumericrange /*String*/ (0,999,1);
 //BA.debugLineNum = 727;BA.debugLine="B4XPlusMinus2.SetNumericRange(0,999, 1)";
mostCurrent._b4xplusminus2._setnumericrange /*String*/ (0,999,1);
 //BA.debugLineNum = 728;BA.debugLine="B4XPlusMinus1.SetNumericRange(0,999, 1)";
mostCurrent._b4xplusminus1._setnumericrange /*String*/ (0,999,1);
 //BA.debugLineNum = 730;BA.debugLine="B4XPlusMinus13.MainLabel.Text = listOtros";
mostCurrent._b4xplusminus13._mainlabel /*anywheresoftware.b4a.objects.B4XViewWrapper*/ .setText(BA.ObjectToCharSequence(_listotros));
 //BA.debugLineNum = 731;BA.debugLine="B4XPlusMinus12.MainLabel.Text = listPlastico9";
mostCurrent._b4xplusminus12._mainlabel /*anywheresoftware.b4a.objects.B4XViewWrapper*/ .setText(BA.ObjectToCharSequence(_listplastico9));
 //BA.debugLineNum = 732;BA.debugLine="B4XPlusMinus11.MainLabel.Text = listPlastico8";
mostCurrent._b4xplusminus11._mainlabel /*anywheresoftware.b4a.objects.B4XViewWrapper*/ .setText(BA.ObjectToCharSequence(_listplastico8));
 //BA.debugLineNum = 733;BA.debugLine="B4XPlusMinus10.MainLabel.Text = listPlastico7";
mostCurrent._b4xplusminus10._mainlabel /*anywheresoftware.b4a.objects.B4XViewWrapper*/ .setText(BA.ObjectToCharSequence(_listplastico7));
 //BA.debugLineNum = 734;BA.debugLine="B4XPlusMinus9.MainLabel.Text = listPlastico6";
mostCurrent._b4xplusminus9._mainlabel /*anywheresoftware.b4a.objects.B4XViewWrapper*/ .setText(BA.ObjectToCharSequence(_listplastico6));
 //BA.debugLineNum = 735;BA.debugLine="B4XPlusMinus8.MainLabel.Text = listPlastico5";
mostCurrent._b4xplusminus8._mainlabel /*anywheresoftware.b4a.objects.B4XViewWrapper*/ .setText(BA.ObjectToCharSequence(_listplastico5));
 //BA.debugLineNum = 736;BA.debugLine="B4XPlusMinus7.MainLabel.Text = listPlastico4";
mostCurrent._b4xplusminus7._mainlabel /*anywheresoftware.b4a.objects.B4XViewWrapper*/ .setText(BA.ObjectToCharSequence(_listplastico4));
 //BA.debugLineNum = 737;BA.debugLine="B4XPlusMinus6.MainLabel.Text = listPlastico3";
mostCurrent._b4xplusminus6._mainlabel /*anywheresoftware.b4a.objects.B4XViewWrapper*/ .setText(BA.ObjectToCharSequence(_listplastico3));
 //BA.debugLineNum = 738;BA.debugLine="B4XPlusMinus5.MainLabel.Text = listPlastico2";
mostCurrent._b4xplusminus5._mainlabel /*anywheresoftware.b4a.objects.B4XViewWrapper*/ .setText(BA.ObjectToCharSequence(_listplastico2));
 //BA.debugLineNum = 739;BA.debugLine="B4XPlusMinus4.MainLabel.Text = listPlastico1";
mostCurrent._b4xplusminus4._mainlabel /*anywheresoftware.b4a.objects.B4XViewWrapper*/ .setText(BA.ObjectToCharSequence(_listplastico1));
 //BA.debugLineNum = 740;BA.debugLine="B4XPlusMinus3.MainLabel.Text = listVidrios";
mostCurrent._b4xplusminus3._mainlabel /*anywheresoftware.b4a.objects.B4XViewWrapper*/ .setText(BA.ObjectToCharSequence(_listvidrios));
 //BA.debugLineNum = 741;BA.debugLine="B4XPlusMinus2.MainLabel.Text = listMetales";
mostCurrent._b4xplusminus2._mainlabel /*anywheresoftware.b4a.objects.B4XViewWrapper*/ .setText(BA.ObjectToCharSequence(_listmetales));
 //BA.debugLineNum = 742;BA.debugLine="B4XPlusMinus1.MainLabel.Text = listCarton";
mostCurrent._b4xplusminus1._mainlabel /*anywheresoftware.b4a.objects.B4XViewWrapper*/ .setText(BA.ObjectToCharSequence(_listcarton));
 //BA.debugLineNum = 744;BA.debugLine="End Sub";
return "";
}
public static String  _btnokresumen1_click() throws Exception{
anywheresoftware.b4a.objects.collections.Map _map1 = null;
 //BA.debugLineNum = 820;BA.debugLine="Private Sub btnOkResumen1_Click";
 //BA.debugLineNum = 822;BA.debugLine="If txtDistanciaCaminada.Text = \"\" Then";
if ((mostCurrent._txtdistanciacaminada._gettext /*String*/ ()).equals("")) { 
 //BA.debugLineNum = 823;BA.debugLine="ToastMessageShow(\"Ingresa la distancia aproximad";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Ingresa la distancia aproximada que caminaste mientras contabas los residuos!"),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 824;BA.debugLine="txtDistanciaCaminada.RequestFocusAndShowKeyboard";
mostCurrent._txtdistanciacaminada._requestfocusandshowkeyboard /*String*/ ();
 //BA.debugLineNum = 825;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 828;BA.debugLine="btnOkResumen1.Enabled = False";
mostCurrent._btnokresumen1.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 829;BA.debugLine="btnOkResumen1.Text = \"Enviando información... esp";
mostCurrent._btnokresumen1.setText(BA.ObjectToCharSequence("Enviando información... espere"));
 //BA.debugLineNum = 832;BA.debugLine="Try";
try { //BA.debugLineNum = 833;BA.debugLine="Dim Map1 As Map";
_map1 = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 834;BA.debugLine="Map1.Initialize";
_map1.Initialize();
 //BA.debugLineNum = 835;BA.debugLine="Log(\"currentproject: \" & Main.currentproject)";
anywheresoftware.b4a.keywords.Common.LogImpl("048955407","currentproject: "+mostCurrent._main._currentproject /*String*/ ,0);
 //BA.debugLineNum = 836;BA.debugLine="Map1.Put(\"Id\", Main.currentproject)";
_map1.Put((Object)("Id"),(Object)(mostCurrent._main._currentproject /*String*/ ));
 //BA.debugLineNum = 837;BA.debugLine="DBUtils.UpdateRecord(Starter.modulo_residuos_sql";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._modulo_residuos_sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals_residuos","tiporio",(Object)(mostCurrent._tiporio),_map1);
 //BA.debugLineNum = 838;BA.debugLine="DBUtils.UpdateRecord(Starter.modulo_residuos_sql";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._modulo_residuos_sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals_residuos","valorind1",(Object)(_listplastico1),_map1);
 //BA.debugLineNum = 839;BA.debugLine="DBUtils.UpdateRecord(Starter.modulo_residuos_sql";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._modulo_residuos_sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals_residuos","valorind2",(Object)(_listplastico2),_map1);
 //BA.debugLineNum = 840;BA.debugLine="DBUtils.UpdateRecord(Starter.modulo_residuos_sql";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._modulo_residuos_sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals_residuos","valorind3",(Object)(_listplastico3),_map1);
 //BA.debugLineNum = 841;BA.debugLine="DBUtils.UpdateRecord(Starter.modulo_residuos_sql";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._modulo_residuos_sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals_residuos","valorind4",(Object)(_listplastico4),_map1);
 //BA.debugLineNum = 842;BA.debugLine="DBUtils.UpdateRecord(Starter.modulo_residuos_sql";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._modulo_residuos_sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals_residuos","valorind5",(Object)(_listplastico5),_map1);
 //BA.debugLineNum = 843;BA.debugLine="DBUtils.UpdateRecord(Starter.modulo_residuos_sql";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._modulo_residuos_sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals_residuos","valorind6",(Object)(_listplastico6),_map1);
 //BA.debugLineNum = 844;BA.debugLine="DBUtils.UpdateRecord(Starter.modulo_residuos_sql";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._modulo_residuos_sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals_residuos","valorind7",(Object)(_listplastico7),_map1);
 //BA.debugLineNum = 845;BA.debugLine="DBUtils.UpdateRecord(Starter.modulo_residuos_sql";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._modulo_residuos_sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals_residuos","valorind8",(Object)(_listplastico8),_map1);
 //BA.debugLineNum = 846;BA.debugLine="DBUtils.UpdateRecord(Starter.modulo_residuos_sql";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._modulo_residuos_sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals_residuos","valorind9",(Object)(_listplastico9),_map1);
 //BA.debugLineNum = 847;BA.debugLine="DBUtils.UpdateRecord(Starter.modulo_residuos_sql";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._modulo_residuos_sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals_residuos","valorind10",(Object)(_listvidrios),_map1);
 //BA.debugLineNum = 848;BA.debugLine="DBUtils.UpdateRecord(Starter.modulo_residuos_sql";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._modulo_residuos_sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals_residuos","valorind11",(Object)(_listmetales),_map1);
 //BA.debugLineNum = 849;BA.debugLine="DBUtils.UpdateRecord(Starter.modulo_residuos_sql";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._modulo_residuos_sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals_residuos","valorind12",(Object)(_listcarton),_map1);
 //BA.debugLineNum = 850;BA.debugLine="DBUtils.UpdateRecord(Starter.modulo_residuos_sql";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._modulo_residuos_sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals_residuos","valorind13",(Object)(_listotros),_map1);
 //BA.debugLineNum = 852;BA.debugLine="DBUtils.UpdateRecord(Starter.modulo_residuos_sql";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._modulo_residuos_sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals_residuos","decimalLatitude",(Object)(_lat),_map1);
 //BA.debugLineNum = 853;BA.debugLine="DBUtils.UpdateRecord(Starter.modulo_residuos_sql";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._modulo_residuos_sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals_residuos","decimalLongitude",(Object)(_lng),_map1);
 //BA.debugLineNum = 854;BA.debugLine="DBUtils.UpdateRecord(Starter.modulo_residuos_sql";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._modulo_residuos_sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals_residuos","georeferencedDate",(Object)(anywheresoftware.b4a.keywords.Common.DateTime.Date(anywheresoftware.b4a.keywords.Common.DateTime.getNow())),_map1);
 //BA.debugLineNum = 855;BA.debugLine="DBUtils.UpdateRecord(Starter.modulo_residuos_sql";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._modulo_residuos_sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals_residuos","foto1",(Object)(_foto1_path),_map1);
 //BA.debugLineNum = 856;BA.debugLine="DBUtils.UpdateRecord(Starter.modulo_residuos_sql";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._modulo_residuos_sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals_residuos","valorind14",(Object)(mostCurrent._txtdistanciacaminada._gettext /*String*/ ()),_map1);
 //BA.debugLineNum = 859;BA.debugLine="DBUtils.UpdateRecord(Starter.modulo_residuos_sql";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._modulo_residuos_sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals_residuos","terminado",(Object)("si"),_map1);
 //BA.debugLineNum = 861;BA.debugLine="enviar_mod";
_enviar_mod();
 } 
       catch (Exception e35) {
			processBA.setLastException(e35); //BA.debugLineNum = 863;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("048955435",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA)),0);
 //BA.debugLineNum = 864;BA.debugLine="ToastMessageShow(\"No se pudieron guardar los dat";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("No se pudieron guardar los datos"),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 865;BA.debugLine="btnOkResumen1.Enabled = True";
mostCurrent._btnokresumen1.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 866;BA.debugLine="btnOkResumen1.Text = \"¡Finalizar y enviar!\"";
mostCurrent._btnokresumen1.setText(BA.ObjectToCharSequence("¡Finalizar y enviar!"));
 };
 //BA.debugLineNum = 869;BA.debugLine="End Sub";
return "";
}
public static String  _btnotros_click() throws Exception{
 //BA.debugLineNum = 493;BA.debugLine="Private Sub btnOtros_Click";
 //BA.debugLineNum = 494;BA.debugLine="listTotal = listTotal + 1";
_listtotal = (int) (_listtotal+1);
 //BA.debugLineNum = 495;BA.debugLine="lblContadorCarga.Text = listTotal";
mostCurrent._lblcontadorcarga.setText(BA.ObjectToCharSequence(_listtotal));
 //BA.debugLineNum = 496;BA.debugLine="listOtros = listOtros + 1";
_listotros = (int) (_listotros+1);
 //BA.debugLineNum = 497;BA.debugLine="lblStatusResiduos.Text = \"Residuo de tipo 'Otro'";
mostCurrent._lblstatusresiduos.setText(BA.ObjectToCharSequence("Residuo de tipo 'Otro' agregado"));
 //BA.debugLineNum = 498;BA.debugLine="End Sub";
return "";
}
public static String  _btnpesca_click() throws Exception{
 //BA.debugLineNum = 593;BA.debugLine="Private Sub btnPesca_Click";
 //BA.debugLineNum = 594;BA.debugLine="listTotal = listTotal + 1";
_listtotal = (int) (_listtotal+1);
 //BA.debugLineNum = 595;BA.debugLine="lblContadorCarga.Text = listTotal";
mostCurrent._lblcontadorcarga.setText(BA.ObjectToCharSequence(_listtotal));
 //BA.debugLineNum = 596;BA.debugLine="listPlastico6 = listPlastico6 + 1";
_listplastico6 = (int) (_listplastico6+1);
 //BA.debugLineNum = 597;BA.debugLine="panelSubPlasticos.RemoveView";
mostCurrent._panelsubplasticos.RemoveView();
 //BA.debugLineNum = 598;BA.debugLine="fondogris.RemoveView";
mostCurrent._fondogris.RemoveView();
 //BA.debugLineNum = 599;BA.debugLine="lblStatusResiduos.Text = \"Residuo de tipo 'Pesca";
mostCurrent._lblstatusresiduos.setText(BA.ObjectToCharSequence("Residuo de tipo 'Pesca o caza' agregado"));
 //BA.debugLineNum = 600;BA.debugLine="btnPlasticos.enabled = True";
mostCurrent._btnplasticos.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 601;BA.debugLine="btnMetales.Visible = True";
mostCurrent._btnmetales.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 602;BA.debugLine="btnCarton.Visible = True";
mostCurrent._btncarton.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 603;BA.debugLine="btnVidrios.Visible= True";
mostCurrent._btnvidrios.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 604;BA.debugLine="btnOtros.Visible = True";
mostCurrent._btnotros.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 605;BA.debugLine="btnPlasticos.Visible = True";
mostCurrent._btnplasticos.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 606;BA.debugLine="btnGuiaCategorias.Visible = True";
mostCurrent._btnguiacategorias.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 607;BA.debugLine="End Sub";
return "";
}
public static String  _btnplasticos_click() throws Exception{
 //BA.debugLineNum = 521;BA.debugLine="Private Sub btnPlasticos_Click";
 //BA.debugLineNum = 523;BA.debugLine="fondogris.Initialize(\"fondogris\")";
mostCurrent._fondogris.Initialize(mostCurrent.activityBA,"fondogris");
 //BA.debugLineNum = 524;BA.debugLine="fondogris.Color = Colors.ARGB(150,0,0,0)";
mostCurrent._fondogris.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (150),(int) (0),(int) (0),(int) (0)));
 //BA.debugLineNum = 525;BA.debugLine="Activity.AddView(fondogris, 0, 0, 100%x, 100%y)";
mostCurrent._activity.AddView((android.view.View)(mostCurrent._fondogris.getObject()),(int) (0),(int) (0),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA));
 //BA.debugLineNum = 527;BA.debugLine="btnPlasticos.enabled = False";
mostCurrent._btnplasticos.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 528;BA.debugLine="panelSubPlasticos.Initialize(\"\")";
mostCurrent._panelsubplasticos.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 529;BA.debugLine="panelSubPlasticos.LoadLayout(\"mod_residuos_carga_";
mostCurrent._panelsubplasticos.LoadLayout("mod_residuos_carga_plasticos",mostCurrent.activityBA);
 //BA.debugLineNum = 530;BA.debugLine="btnMetales.Visible = False";
mostCurrent._btnmetales.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 531;BA.debugLine="btnCarton.Visible = False";
mostCurrent._btncarton.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 532;BA.debugLine="btnVidrios.Visible= False";
mostCurrent._btnvidrios.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 533;BA.debugLine="btnOtros.Visible = False";
mostCurrent._btnotros.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 534;BA.debugLine="btnPlasticos.Visible = False";
mostCurrent._btnplasticos.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 535;BA.debugLine="btnGuiaCategorias.Visible = False";
mostCurrent._btnguiacategorias.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 536;BA.debugLine="Activity.AddView(panelSubPlasticos, 5%x, 20%y, 95";
mostCurrent._activity.AddView((android.view.View)(mostCurrent._panelsubplasticos.getObject()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (5),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (20),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (95),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (80),mostCurrent.activityBA));
 //BA.debugLineNum = 537;BA.debugLine="End Sub";
return "";
}
public static void  _btnprev_click() throws Exception{
ResumableSub_btnPrev_Click rsub = new ResumableSub_btnPrev_Click(null);
rsub.resume(processBA, null);
}
public static class ResumableSub_btnPrev_Click extends BA.ResumableSub {
public ResumableSub_btnPrev_Click(appear.pnud.preservamos.mod_residuos parent) {
this.parent = parent;
}
appear.pnud.preservamos.mod_residuos parent;
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
 //BA.debugLineNum = 231;BA.debugLine="If tabContainer.CurrentPage = 0 Then";
if (true) break;

case 1:
//if
this.state = 16;
if (parent.mostCurrent._tabcontainer.getCurrentPage()==0) { 
this.state = 3;
}else if(parent.mostCurrent._tabcontainer.getCurrentPage()==1) { 
this.state = 9;
}else if(parent.mostCurrent._tabcontainer.getCurrentPage()==2) { 
this.state = 11;
}else if(parent.mostCurrent._tabcontainer.getCurrentPage()==3) { 
this.state = 13;
}else if(parent.mostCurrent._tabcontainer.getCurrentPage()==4) { 
this.state = 15;
}if (true) break;

case 3:
//C
this.state = 4;
 //BA.debugLineNum = 232;BA.debugLine="Msgbox2Async(\"¿Cancelar el análisis?\", \"¡Se perd";
anywheresoftware.b4a.keywords.Common.Msgbox2Async(BA.ObjectToCharSequence("¿Cancelar el análisis?"),BA.ObjectToCharSequence("¡Se perderá lo cargado!"),"Si","","No",(anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper(), (android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null)),processBA,anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 233;BA.debugLine="Wait For Msgbox_Result (Result As Int)";
anywheresoftware.b4a.keywords.Common.WaitFor("msgbox_result", processBA, this, null);
this.state = 17;
return;
case 17:
//C
this.state = 4;
_result = (Integer) result[0];
;
 //BA.debugLineNum = 234;BA.debugLine="If Result = DialogResponse.POSITIVE Then";
if (true) break;

case 4:
//if
this.state = 7;
if (_result==anywheresoftware.b4a.keywords.Common.DialogResponse.POSITIVE) { 
this.state = 6;
}if (true) break;

case 6:
//C
this.state = 7;
 //BA.debugLineNum = 235;BA.debugLine="Activity.finish";
parent.mostCurrent._activity.Finish();
 //BA.debugLineNum = 236;BA.debugLine="Activity.RemoveAllViews";
parent.mostCurrent._activity.RemoveAllViews();
 if (true) break;

case 7:
//C
this.state = 16;
;
 if (true) break;

case 9:
//C
this.state = 16;
 //BA.debugLineNum = 239;BA.debugLine="tabContainer.ScrollTo(0, True)";
parent.mostCurrent._tabcontainer.ScrollTo((int) (0),anywheresoftware.b4a.keywords.Common.True);
 if (true) break;

case 11:
//C
this.state = 16;
 //BA.debugLineNum = 241;BA.debugLine="tabContainer.ScrollTo(1, True)";
parent.mostCurrent._tabcontainer.ScrollTo((int) (1),anywheresoftware.b4a.keywords.Common.True);
 if (true) break;

case 13:
//C
this.state = 16;
 //BA.debugLineNum = 243;BA.debugLine="tabContainer.ScrollTo(2, True)";
parent.mostCurrent._tabcontainer.ScrollTo((int) (2),anywheresoftware.b4a.keywords.Common.True);
 if (true) break;

case 15:
//C
this.state = 16;
 //BA.debugLineNum = 245;BA.debugLine="tabContainer.ScrollTo(3, True)";
parent.mostCurrent._tabcontainer.ScrollTo((int) (3),anywheresoftware.b4a.keywords.Common.True);
 if (true) break;

case 16:
//C
this.state = -1;
;
 //BA.debugLineNum = 248;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public static void  _msgbox_result(int _result) throws Exception{
}
public static String  _btnresiduosagro_click() throws Exception{
 //BA.debugLineNum = 609;BA.debugLine="Private Sub btnResiduosAgro_Click";
 //BA.debugLineNum = 610;BA.debugLine="listTotal = listTotal + 1";
_listtotal = (int) (_listtotal+1);
 //BA.debugLineNum = 611;BA.debugLine="lblContadorCarga.Text = listTotal";
mostCurrent._lblcontadorcarga.setText(BA.ObjectToCharSequence(_listtotal));
 //BA.debugLineNum = 612;BA.debugLine="listPlastico5 = listPlastico5 + 1";
_listplastico5 = (int) (_listplastico5+1);
 //BA.debugLineNum = 613;BA.debugLine="panelSubPlasticos.RemoveView";
mostCurrent._panelsubplasticos.RemoveView();
 //BA.debugLineNum = 614;BA.debugLine="fondogris.RemoveView";
mostCurrent._fondogris.RemoveView();
 //BA.debugLineNum = 615;BA.debugLine="lblStatusResiduos.Text = \"Residuo de tipo 'Residu";
mostCurrent._lblstatusresiduos.setText(BA.ObjectToCharSequence("Residuo de tipo 'Residuo del agro' agregado"));
 //BA.debugLineNum = 616;BA.debugLine="btnPlasticos.enabled = True";
mostCurrent._btnplasticos.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 617;BA.debugLine="btnMetales.Visible = True";
mostCurrent._btnmetales.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 618;BA.debugLine="btnCarton.Visible = True";
mostCurrent._btncarton.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 619;BA.debugLine="btnVidrios.Visible= True";
mostCurrent._btnvidrios.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 620;BA.debugLine="btnOtros.Visible = True";
mostCurrent._btnotros.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 621;BA.debugLine="btnPlasticos.Visible = True";
mostCurrent._btnplasticos.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 622;BA.debugLine="btnGuiaCategorias.Visible = True";
mostCurrent._btnguiacategorias.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 623;BA.debugLine="End Sub";
return "";
}
public static String  _btnunfragmento_click() throws Exception{
 //BA.debugLineNum = 561;BA.debugLine="Private Sub btnUnFragmento_Click";
 //BA.debugLineNum = 562;BA.debugLine="listTotal = listTotal + 1";
_listtotal = (int) (_listtotal+1);
 //BA.debugLineNum = 563;BA.debugLine="lblContadorCarga.Text = listTotal";
mostCurrent._lblcontadorcarga.setText(BA.ObjectToCharSequence(_listtotal));
 //BA.debugLineNum = 564;BA.debugLine="listPlastico8 = listPlastico8 + 1";
_listplastico8 = (int) (_listplastico8+1);
 //BA.debugLineNum = 565;BA.debugLine="panelSubPlasticos.RemoveView";
mostCurrent._panelsubplasticos.RemoveView();
 //BA.debugLineNum = 566;BA.debugLine="fondogris.RemoveView";
mostCurrent._fondogris.RemoveView();
 //BA.debugLineNum = 567;BA.debugLine="lblStatusResiduos.Text = \"Residuo de tipo 'Un fra";
mostCurrent._lblstatusresiduos.setText(BA.ObjectToCharSequence("Residuo de tipo 'Un fragmento' agregado"));
 //BA.debugLineNum = 568;BA.debugLine="btnPlasticos.enabled = True";
mostCurrent._btnplasticos.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 569;BA.debugLine="btnMetales.Visible = True";
mostCurrent._btnmetales.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 570;BA.debugLine="btnCarton.Visible = True";
mostCurrent._btncarton.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 571;BA.debugLine="btnVidrios.Visible= True";
mostCurrent._btnvidrios.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 572;BA.debugLine="btnOtros.Visible = True";
mostCurrent._btnotros.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 573;BA.debugLine="btnPlasticos.Visible = True";
mostCurrent._btnplasticos.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 574;BA.debugLine="btnGuiaCategorias.Visible = True";
mostCurrent._btnguiacategorias.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 575;BA.debugLine="End Sub";
return "";
}
public static String  _btnverdetalle_click() throws Exception{
 //BA.debugLineNum = 759;BA.debugLine="Private Sub btnVerDetalle_Click";
 //BA.debugLineNum = 760;BA.debugLine="btnVerDetalle.Visible = False";
mostCurrent._btnverdetalle.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 761;BA.debugLine="btnVerDetalle.SetVisibleAnimated(500,False)";
mostCurrent._btnverdetalle.SetVisibleAnimated((int) (500),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 762;BA.debugLine="lblContadorResumen.SetVisibleAnimated(500,False)";
mostCurrent._lblcontadorresumen.SetVisibleAnimated((int) (500),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 763;BA.debugLine="lblPiezas.SetVisibleAnimated(500,False)";
mostCurrent._lblpiezas.SetVisibleAnimated((int) (500),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 764;BA.debugLine="lblResumenListTitle.SetVisibleAnimated(500,True)";
mostCurrent._lblresumenlisttitle.SetVisibleAnimated((int) (500),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 765;BA.debugLine="ScrollView1.SetVisibleAnimated(500,True)";
mostCurrent._scrollview1.SetVisibleAnimated((int) (500),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 766;BA.debugLine="End Sub";
return "";
}
public static String  _btnvidrios_click() throws Exception{
 //BA.debugLineNum = 500;BA.debugLine="Private Sub btnVidrios_Click";
 //BA.debugLineNum = 501;BA.debugLine="listTotal = listTotal + 1";
_listtotal = (int) (_listtotal+1);
 //BA.debugLineNum = 502;BA.debugLine="lblContadorCarga.Text = listTotal";
mostCurrent._lblcontadorcarga.setText(BA.ObjectToCharSequence(_listtotal));
 //BA.debugLineNum = 503;BA.debugLine="listVidrios = listVidrios + 1";
_listvidrios = (int) (_listvidrios+1);
 //BA.debugLineNum = 504;BA.debugLine="lblStatusResiduos.Text = \"Residuo de tipo 'Vidrio";
mostCurrent._lblstatusresiduos.setText(BA.ObjectToCharSequence("Residuo de tipo 'Vidrio' agregado"));
 //BA.debugLineNum = 505;BA.debugLine="End Sub";
return "";
}
public static String  _cargar_basicos() throws Exception{
 //BA.debugLineNum = 262;BA.debugLine="Private Sub cargar_basicos";
 //BA.debugLineNum = 263;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 264;BA.debugLine="Activity.LoadLayout(\"mod_residuos_basico\")";
mostCurrent._activity.LoadLayout("mod_residuos_basico",mostCurrent.activityBA);
 //BA.debugLineNum = 266;BA.debugLine="scrollUsos.Panel.LoadLayout(\"pnlQ5_usos\")";
mostCurrent._scrollusos.getPanel().LoadLayout("pnlQ5_usos",mostCurrent.activityBA);
 //BA.debugLineNum = 267;BA.debugLine="scrollUsos.Panel.Height = imgQ5_1_i.Height + imgQ";
mostCurrent._scrollusos.getPanel().setHeight((int) (mostCurrent._imgq5_1_i.getHeight()+mostCurrent._imgq5_1_i.getTop()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (50))));
 //BA.debugLineNum = 268;BA.debugLine="End Sub";
return "";
}
public static void  _closeappmsgbox() throws Exception{
ResumableSub_closeAppMsgBox rsub = new ResumableSub_closeAppMsgBox(null);
rsub.resume(processBA, null);
}
public static class ResumableSub_closeAppMsgBox extends BA.ResumableSub {
public ResumableSub_closeAppMsgBox(appear.pnud.preservamos.mod_residuos parent) {
this.parent = parent;
}
appear.pnud.preservamos.mod_residuos parent;
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
 //BA.debugLineNum = 165;BA.debugLine="If Main.lang = \"es\" Then";
if (true) break;

case 1:
//if
this.state = 6;
if ((parent.mostCurrent._main._lang /*String*/ ).equals("es")) { 
this.state = 3;
}else if((parent.mostCurrent._main._lang /*String*/ ).equals("en")) { 
this.state = 5;
}if (true) break;

case 3:
//C
this.state = 6;
 //BA.debugLineNum = 166;BA.debugLine="Msgbox2Async(\"Cerrar evaluación?\", \"Atención: Pe";
anywheresoftware.b4a.keywords.Common.Msgbox2Async(BA.ObjectToCharSequence("Cerrar evaluación?"),BA.ObjectToCharSequence("Atención: Perderá lo cargado!"),"Si","","No",(anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper(), (android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null)),processBA,anywheresoftware.b4a.keywords.Common.False);
 if (true) break;

case 5:
//C
this.state = 6;
 //BA.debugLineNum = 168;BA.debugLine="Msgbox2Async(\"Back to the beginning?\", \"Exit\", \"";
anywheresoftware.b4a.keywords.Common.Msgbox2Async(BA.ObjectToCharSequence("Back to the beginning?"),BA.ObjectToCharSequence("Exit"),"Yes","","No",(anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper(), (android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null)),processBA,anywheresoftware.b4a.keywords.Common.False);
 if (true) break;

case 6:
//C
this.state = 7;
;
 //BA.debugLineNum = 170;BA.debugLine="Wait For Msgbox_Result (Result As Int)";
anywheresoftware.b4a.keywords.Common.WaitFor("msgbox_result", processBA, this, null);
this.state = 11;
return;
case 11:
//C
this.state = 7;
_result = (Integer) result[0];
;
 //BA.debugLineNum = 171;BA.debugLine="If Result = DialogResponse.POSITIVE Then";
if (true) break;

case 7:
//if
this.state = 10;
if (_result==anywheresoftware.b4a.keywords.Common.DialogResponse.POSITIVE) { 
this.state = 9;
}if (true) break;

case 9:
//C
this.state = 10;
 //BA.debugLineNum = 172;BA.debugLine="timerCarga.Enabled = False";
parent.mostCurrent._timercarga.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 173;BA.debugLine="Activity.finish";
parent.mostCurrent._activity.Finish();
 //BA.debugLineNum = 174;BA.debugLine="Activity.RemoveAllViews";
parent.mostCurrent._activity.RemoveAllViews();
 if (true) break;

case 10:
//C
this.state = -1;
;
 //BA.debugLineNum = 176;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public static String  _comenzartimer() throws Exception{
 //BA.debugLineNum = 482;BA.debugLine="Sub ComenzarTimer";
 //BA.debugLineNum = 484;BA.debugLine="timerCarga.Initialize(\"timerCarga\", 1000)";
mostCurrent._timercarga.Initialize(processBA,"timerCarga",(long) (1000));
 //BA.debugLineNum = 485;BA.debugLine="timerCarga.Enabled = True";
mostCurrent._timercarga.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 486;BA.debugLine="End Sub";
return "";
}
public static String  _create_reporte() throws Exception{
anywheresoftware.b4a.objects.collections.List _neweval = null;
anywheresoftware.b4a.objects.collections.Map _m = null;
anywheresoftware.b4a.objects.collections.Map _currentprojectmap = null;
String _usernamenoaccent = "";
anywheresoftware.b4a.objects.collections.Map _map1 = null;
 //BA.debugLineNum = 356;BA.debugLine="Sub create_reporte";
 //BA.debugLineNum = 359;BA.debugLine="Dim newEval As List";
_neweval = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 360;BA.debugLine="newEval.Initialize";
_neweval.Initialize();
 //BA.debugLineNum = 361;BA.debugLine="Dim m As Map";
_m = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 362;BA.debugLine="m.Initialize";
_m.Initialize();
 //BA.debugLineNum = 363;BA.debugLine="m.Put(\"useremail\", Main.strUserEmail)";
_m.Put((Object)("useremail"),(Object)(mostCurrent._main._struseremail /*String*/ ));
 //BA.debugLineNum = 364;BA.debugLine="newEval.Add(m)";
_neweval.Add((Object)(_m.getObject()));
 //BA.debugLineNum = 365;BA.debugLine="If Starter.modulo_residuos_sqlDB = Null Then";
if (mostCurrent._starter._modulo_residuos_sqldb /*anywheresoftware.b4a.sql.SQL*/ == null) { 
 //BA.debugLineNum = 366;BA.debugLine="Starter.modulo_residuos_sqlDB.Initialize(Starter";
mostCurrent._starter._modulo_residuos_sqldb /*anywheresoftware.b4a.sql.SQL*/ .Initialize(BA.ObjectToString(mostCurrent._starter._modulo_residuos_sqldb /*anywheresoftware.b4a.sql.SQL*/ ),"preservamos_residuos_db.db",anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 368;BA.debugLine="Try";
try { //BA.debugLineNum = 369;BA.debugLine="DBUtils.InsertMaps(Starter.modulo_residuos_sqlDB";
mostCurrent._dbutils._insertmaps /*String*/ (mostCurrent.activityBA,mostCurrent._starter._modulo_residuos_sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals_residuos",_neweval);
 } 
       catch (Exception e13) {
			processBA.setLastException(e13); //BA.debugLineNum = 371;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("046596111",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA)),0);
 //BA.debugLineNum = 372;BA.debugLine="ToastMessageShow(\"Hubo un error agregando su inf";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Hubo un error agregando su informe, intente de nuevo"),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 373;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 378;BA.debugLine="Dim currentprojectMap As Map";
_currentprojectmap = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 379;BA.debugLine="currentprojectMap.Initialize";
_currentprojectmap.Initialize();
 //BA.debugLineNum = 380;BA.debugLine="Try";
try { //BA.debugLineNum = 381;BA.debugLine="currentprojectMap = DBUtils.ExecuteMap(Starter.m";
_currentprojectmap = mostCurrent._dbutils._executemap /*anywheresoftware.b4a.objects.collections.Map*/ (mostCurrent.activityBA,mostCurrent._starter._modulo_residuos_sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"SELECT * FROM evals_residuos ORDER BY id DESC LIMIT 1",(String[])(anywheresoftware.b4a.keywords.Common.Null));
 //BA.debugLineNum = 382;BA.debugLine="Dim usernameNoAccent As String";
_usernamenoaccent = "";
 //BA.debugLineNum = 383;BA.debugLine="usernameNoAccent = Main.username.Replace(\"á\", \"a";
_usernamenoaccent = mostCurrent._main._username /*String*/ .replace("á","a");
 //BA.debugLineNum = 384;BA.debugLine="usernameNoAccent = Main.username.Replace(\"é\", \"e";
_usernamenoaccent = mostCurrent._main._username /*String*/ .replace("é","e");
 //BA.debugLineNum = 385;BA.debugLine="usernameNoAccent = Main.username.Replace(\"í\", \"i";
_usernamenoaccent = mostCurrent._main._username /*String*/ .replace("í","i");
 //BA.debugLineNum = 386;BA.debugLine="usernameNoAccent = Main.username.Replace(\"ó\", \"o";
_usernamenoaccent = mostCurrent._main._username /*String*/ .replace("ó","o");
 //BA.debugLineNum = 387;BA.debugLine="usernameNoAccent = Main.username.Replace(\"ú\", \"u";
_usernamenoaccent = mostCurrent._main._username /*String*/ .replace("ú","u");
 //BA.debugLineNum = 388;BA.debugLine="usernameNoAccent = Main.username.Replace(\"ñ\", \"n";
_usernamenoaccent = mostCurrent._main._username /*String*/ .replace("ñ","n");
 //BA.debugLineNum = 389;BA.debugLine="usernameNoAccent = Main.username.Replace(\"@\", \"a";
_usernamenoaccent = mostCurrent._main._username /*String*/ .replace("@","ae");
 //BA.debugLineNum = 391;BA.debugLine="If currentprojectMap = Null Or currentprojectMap";
if (_currentprojectmap== null || _currentprojectmap.IsInitialized()==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 392;BA.debugLine="currentproject = 1";
_currentproject = BA.NumberToString(1);
 //BA.debugLineNum = 393;BA.debugLine="Main.currentproject = currentproject";
mostCurrent._main._currentproject /*String*/  = _currentproject;
 //BA.debugLineNum = 394;BA.debugLine="Dim Map1 As Map";
_map1 = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 395;BA.debugLine="Map1.Initialize";
_map1.Initialize();
 //BA.debugLineNum = 396;BA.debugLine="Map1.Put(\"Id\", currentproject)";
_map1.Put((Object)("Id"),(Object)(_currentproject));
 //BA.debugLineNum = 397;BA.debugLine="DateTime.DateFormat = \"dd-MM-yyyy\"";
anywheresoftware.b4a.keywords.Common.DateTime.setDateFormat("dd-MM-yyyy");
 //BA.debugLineNum = 398;BA.debugLine="datecurrentproject = DateTime.Date(DateTime.Now";
_datecurrentproject = anywheresoftware.b4a.keywords.Common.DateTime.Date(anywheresoftware.b4a.keywords.Common.DateTime.getNow());
 //BA.debugLineNum = 400;BA.debugLine="fullidcurrentproject = usernameNoAccent & \"_\" &";
_fullidcurrentproject = _usernamenoaccent+"_"+_currentproject+"_"+_datecurrentproject;
 //BA.debugLineNum = 401;BA.debugLine="DBUtils.UpdateRecord(Starter.modulo_residuos_sq";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._modulo_residuos_sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals_residuos","fullID",(Object)(_fullidcurrentproject),_map1);
 //BA.debugLineNum = 402;BA.debugLine="DBUtils.UpdateRecord(Starter.modulo_residuos_sq";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._modulo_residuos_sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals_residuos","terminado",(Object)("no"),_map1);
 //BA.debugLineNum = 403;BA.debugLine="DBUtils.UpdateRecord(Starter.modulo_residuos_sq";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._modulo_residuos_sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals_residuos","evalsent",(Object)("no"),_map1);
 }else {
 //BA.debugLineNum = 405;BA.debugLine="currentproject = currentprojectMap.Get(\"id\")";
_currentproject = BA.ObjectToString(_currentprojectmap.Get((Object)("id")));
 //BA.debugLineNum = 406;BA.debugLine="Main.currentproject = currentproject";
mostCurrent._main._currentproject /*String*/  = _currentproject;
 //BA.debugLineNum = 407;BA.debugLine="Dim Map1 As Map";
_map1 = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 408;BA.debugLine="Map1.Initialize";
_map1.Initialize();
 //BA.debugLineNum = 409;BA.debugLine="Map1.Put(\"Id\", currentproject)";
_map1.Put((Object)("Id"),(Object)(_currentproject));
 //BA.debugLineNum = 410;BA.debugLine="DateTime.DateFormat = \"dd-MM-yyyy\"";
anywheresoftware.b4a.keywords.Common.DateTime.setDateFormat("dd-MM-yyyy");
 //BA.debugLineNum = 411;BA.debugLine="datecurrentproject = DateTime.Date(DateTime.Now";
_datecurrentproject = anywheresoftware.b4a.keywords.Common.DateTime.Date(anywheresoftware.b4a.keywords.Common.DateTime.getNow());
 //BA.debugLineNum = 412;BA.debugLine="fullidcurrentproject = usernameNoAccent & \"_\" &";
_fullidcurrentproject = _usernamenoaccent+"_"+_currentproject+"_"+_datecurrentproject;
 //BA.debugLineNum = 413;BA.debugLine="DBUtils.UpdateRecord(Starter.modulo_residuos_sq";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._modulo_residuos_sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals_residuos","fullID",(Object)(_fullidcurrentproject),_map1);
 //BA.debugLineNum = 414;BA.debugLine="DBUtils.UpdateRecord(Starter.modulo_residuos_sq";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._modulo_residuos_sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals_residuos","terminado",(Object)("no"),_map1);
 //BA.debugLineNum = 415;BA.debugLine="DBUtils.UpdateRecord(Starter.modulo_residuos_sq";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._modulo_residuos_sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals_residuos","evalsent",(Object)("no"),_map1);
 };
 } 
       catch (Exception e55) {
			processBA.setLastException(e55); //BA.debugLineNum = 418;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("046596158",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA)),0);
 //BA.debugLineNum = 419;BA.debugLine="ToastMessageShow(\"Hubo un error, intente de nuev";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Hubo un error, intente de nuevo"),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 420;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 422;BA.debugLine="Log(\"currentproject: \" & Main.currentproject)";
anywheresoftware.b4a.keywords.Common.LogImpl("046596162","currentproject: "+mostCurrent._main._currentproject /*String*/ ,0);
 //BA.debugLineNum = 425;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 427;BA.debugLine="Activity.LoadLayout(\"mod_residuos_carga\")";
mostCurrent._activity.LoadLayout("mod_residuos_carga",mostCurrent.activityBA);
 //BA.debugLineNum = 441;BA.debugLine="frmLocalizacion.origen = \"modulo_residuos\"";
mostCurrent._frmlocalizacion._origen /*String*/  = "modulo_residuos";
 //BA.debugLineNum = 442;BA.debugLine="StartActivity(frmLocalizacion)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._frmlocalizacion.getObject()));
 //BA.debugLineNum = 444;BA.debugLine="End Sub";
return "";
}
public static void  _enviar_mod() throws Exception{
ResumableSub_enviar_mod rsub = new ResumableSub_enviar_mod(null);
rsub.resume(processBA, null);
}
public static class ResumableSub_enviar_mod extends BA.ResumableSub {
public ResumableSub_enviar_mod(appear.pnud.preservamos.mod_residuos parent) {
this.parent = parent;
}
appear.pnud.preservamos.mod_residuos parent;
String _rndstr = "";
boolean _completed = false;

@Override
public void resume(BA ba, Object[] result) throws Exception{

    while (true) {
        switch (state) {
            case -1:
return;

case 0:
//C
this.state = 1;
 //BA.debugLineNum = 882;BA.debugLine="If Main.username = \"guest\" Or Main.username = \"No";
if (true) break;

case 1:
//if
this.state = 4;
if ((parent.mostCurrent._main._username /*String*/ ).equals("guest") || (parent.mostCurrent._main._username /*String*/ ).equals("None")) { 
this.state = 3;
}if (true) break;

case 3:
//C
this.state = 4;
 //BA.debugLineNum = 883;BA.debugLine="ToastMessageShow(\"No estás registrado/a, pero ig";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("No estás registrado/a, pero igual se enviará tu reporte!"),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 884;BA.debugLine="Dim RndStr As String";
_rndstr = "";
 //BA.debugLineNum = 885;BA.debugLine="RndStr = utilidades.RandomString(6)";
_rndstr = parent.mostCurrent._utilidades._randomstring /*String*/ (mostCurrent.activityBA,(int) (6));
 //BA.debugLineNum = 886;BA.debugLine="Main.username = \"guest_\" & RndStr";
parent.mostCurrent._main._username /*String*/  = "guest_"+_rndstr;
 if (true) break;

case 4:
//C
this.state = 5;
;
 //BA.debugLineNum = 889;BA.debugLine="ProgressBar1.Progress = 0";
parent.mostCurrent._progressbar1.setProgress((int) (0));
 //BA.debugLineNum = 892;BA.debugLine="Wait For (EnvioDatos(Main.currentproject)) Comple";
anywheresoftware.b4a.keywords.Common.WaitFor("complete", processBA, this, _enviodatos(parent.mostCurrent._main._currentproject /*String*/ ));
this.state = 11;
return;
case 11:
//C
this.state = 5;
_completed = (Boolean) result[0];
;
 //BA.debugLineNum = 894;BA.debugLine="If Completed = True Then";
if (true) break;

case 5:
//if
this.state = 10;
if (_completed==anywheresoftware.b4a.keywords.Common.True) { 
this.state = 7;
}else {
this.state = 9;
}if (true) break;

case 7:
//C
this.state = 10;
 //BA.debugLineNum = 896;BA.debugLine="EnviarFotos";
_enviarfotos();
 if (true) break;

case 9:
//C
this.state = 10;
 //BA.debugLineNum = 898;BA.debugLine="TimerEnvio.Enabled = False";
parent._timerenvio.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 899;BA.debugLine="Activity.RemoveAllViews";
parent.mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 900;BA.debugLine="Activity.finish";
parent.mostCurrent._activity.Finish();
 if (true) break;

case 10:
//C
this.state = -1;
;
 //BA.debugLineNum = 902;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public static void  _complete(boolean _completed) throws Exception{
}
public static String  _enviarfotos() throws Exception{
String _usr = "";
String _pss = "";
String _filetoupload = "";
 //BA.debugLineNum = 1090;BA.debugLine="Sub EnviarFotos";
 //BA.debugLineNum = 1093;BA.debugLine="If foto1 <> \"null\" Then";
if ((mostCurrent._foto1).equals("null") == false) { 
 //BA.debugLineNum = 1094;BA.debugLine="totalFotos = totalFotos + 1";
_totalfotos = (int) (_totalfotos+1);
 //BA.debugLineNum = 1095;BA.debugLine="ProgressBar1.Visible = False";
mostCurrent._progressbar1.setVisible(anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 1099;BA.debugLine="TimerEnvio.Initialize(\"TimerEnvio\", 1000)";
_timerenvio.Initialize(processBA,"TimerEnvio",(long) (1000));
 //BA.debugLineNum = 1100;BA.debugLine="TimerEnvio.Enabled = True";
_timerenvio.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1102;BA.debugLine="Dim usr As String";
_usr = "";
 //BA.debugLineNum = 1103;BA.debugLine="Dim pss As String";
_pss = "";
 //BA.debugLineNum = 1104;BA.debugLine="usr = \"\"";
_usr = "";
 //BA.debugLineNum = 1105;BA.debugLine="pss = \"\"";
_pss = "";
 //BA.debugLineNum = 1107;BA.debugLine="Up1.B4A_log=True";
_up1.B4A_log = anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 1108;BA.debugLine="Up1.Initialize(\"Up1\")";
_up1.Initialize(processBA,"Up1");
 //BA.debugLineNum = 1111;BA.debugLine="If File.Exists(Starter.savedir, foto1 & \".jpg\") A";
if (anywheresoftware.b4a.keywords.Common.File.Exists(mostCurrent._starter._savedir /*String*/ ,mostCurrent._foto1+".jpg") && _foto1sent==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 1112;BA.debugLine="Log(\"Enviando foto\")";
anywheresoftware.b4a.keywords.Common.LogImpl("049152022","Enviando foto",0);
 //BA.debugLineNum = 1113;BA.debugLine="Log(\"Enviando foto 1 \")";
anywheresoftware.b4a.keywords.Common.LogImpl("049152023","Enviando foto 1 ",0);
 //BA.debugLineNum = 1115;BA.debugLine="foto1ProgressBar.Visible = True";
mostCurrent._foto1progressbar._setvisible /*boolean*/ (anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1116;BA.debugLine="Dim filetoUpload As String";
_filetoupload = "";
 //BA.debugLineNum = 1117;BA.debugLine="filetoUpload = Starter.savedir & \"/\" & foto1 & \"";
_filetoupload = mostCurrent._starter._savedir /*String*/ +"/"+mostCurrent._foto1+".jpg";
 //BA.debugLineNum = 1118;BA.debugLine="Up1.doFileUpload(ProgressBar1,Null,filetoUpload,";
_up1.doFileUpload(processBA,(android.widget.ProgressBar)(mostCurrent._progressbar1.getObject()),(android.widget.TextView)(anywheresoftware.b4a.keywords.Common.Null),_filetoupload,mostCurrent._main._serverpath /*String*/ +"/"+mostCurrent._main._serverconnectionfolder /*String*/ +"/upload_file.php?usr="+_usr+"&pss="+_pss);
 //BA.debugLineNum = 1119;BA.debugLine="ProgressBar1.Visible = False";
mostCurrent._progressbar1.setVisible(anywheresoftware.b4a.keywords.Common.False);
 }else {
 //BA.debugLineNum = 1122;BA.debugLine="Log(\"no foto 1\")";
anywheresoftware.b4a.keywords.Common.LogImpl("049152032","no foto 1",0);
 };
 //BA.debugLineNum = 1126;BA.debugLine="End Sub";
return "";
}
public static anywheresoftware.b4a.keywords.Common.ResumableSubWrapper  _enviodatos(String _proyectonumero) throws Exception{
ResumableSub_EnvioDatos rsub = new ResumableSub_EnvioDatos(null,_proyectonumero);
rsub.resume(processBA, null);
return (anywheresoftware.b4a.keywords.Common.ResumableSubWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.keywords.Common.ResumableSubWrapper(), rsub);
}
public static class ResumableSub_EnvioDatos extends BA.ResumableSub {
public ResumableSub_EnvioDatos(appear.pnud.preservamos.mod_residuos parent,String _proyectonumero) {
this.parent = parent;
this._proyectonumero = _proyectonumero;
}
appear.pnud.preservamos.mod_residuos parent;
String _proyectonumero;
String _username = "";
String _useremail = "";
String _dateandtime = "";
String _nombresitio = "";
String _gpsdetect = "";
String _wifidetect = "";
String _mapadetect = "";
String _partido = "";
String _valorcalidad = "";
String _valorns = "";
String _valorind2 = "";
String _valorind3 = "";
String _valorind4 = "";
String _valorind5 = "";
String _valorind6 = "";
String _valorind7 = "";
String _valorind8 = "";
String _valorind9 = "";
String _valorind10 = "";
String _valorind11 = "";
String _valorind12 = "";
String _valorind13 = "";
String _valorind14 = "";
String _valorind15 = "";
String _valorind16 = "";
String _valorind17 = "";
String _valorind18 = "";
String _valorind19 = "";
String _notas = "";
String _bingo = "";
String _terminado = "";
String _privado = "";
String _estadovalidacion = "";
String _deviceid = "";
anywheresoftware.b4a.objects.collections.Map _datosmap = null;
appear.pnud.preservamos.httpjob _j = null;
String _loginpath = "";
String _ret = "";
String _act = "";
anywheresoftware.b4a.objects.collections.JSONParser _parser = null;
anywheresoftware.b4a.objects.collections.Map _nd = null;
String _serverid = "";
anywheresoftware.b4a.objects.collections.Map _map1 = null;

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
 //BA.debugLineNum = 906;BA.debugLine="proyectoIDenviar = proyectonumero";
parent.mostCurrent._proyectoidenviar = _proyectonumero;
 //BA.debugLineNum = 908;BA.debugLine="Dim username, useremail, dateandtime,nombresitio,";
_username = "";
_useremail = "";
_dateandtime = "";
_nombresitio = "";
parent.mostCurrent._tiporio = "";
parent._lat = "";
parent._lng = "";
_gpsdetect = "";
_wifidetect = "";
_mapadetect = "";
_partido = "";
 //BA.debugLineNum = 909;BA.debugLine="Dim valorcalidad, valorNS, valorind1,valorind2,va";
_valorcalidad = "";
_valorns = "";
parent.mostCurrent._valorind1 = "";
_valorind2 = "";
_valorind3 = "";
_valorind4 = "";
_valorind5 = "";
_valorind6 = "";
_valorind7 = "";
_valorind8 = "";
_valorind9 = "";
_valorind10 = "";
_valorind11 = "";
_valorind12 = "";
_valorind13 = "";
_valorind14 = "";
_valorind15 = "";
_valorind16 = "";
_valorind17 = "";
_valorind18 = "";
_valorind19 = "";
 //BA.debugLineNum = 912;BA.debugLine="Dim notas,bingo As String";
_notas = "";
_bingo = "";
 //BA.debugLineNum = 913;BA.debugLine="Dim terminado, privado,estadovalidacion, deviceID";
_terminado = "";
_privado = "";
_estadovalidacion = "";
_deviceid = "";
 //BA.debugLineNum = 915;BA.debugLine="Dim datosMap As Map";
_datosmap = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 916;BA.debugLine="datosMap.Initialize";
_datosmap.Initialize();
 //BA.debugLineNum = 917;BA.debugLine="datosMap = DBUtils.ExecuteMap(Starter.modulo_resi";
_datosmap = parent.mostCurrent._dbutils._executemap /*anywheresoftware.b4a.objects.collections.Map*/ (mostCurrent.activityBA,parent.mostCurrent._starter._modulo_residuos_sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"SELECT * FROM evals_residuos WHERE Id=?",new String[]{_proyectonumero});
 //BA.debugLineNum = 919;BA.debugLine="If datosMap = Null Or datosMap.IsInitialized = Fa";
if (true) break;

case 1:
//if
this.state = 18;
if (_datosmap== null || _datosmap.IsInitialized()==anywheresoftware.b4a.keywords.Common.False) { 
this.state = 3;
}else {
this.state = 5;
}if (true) break;

case 3:
//C
this.state = 18;
 //BA.debugLineNum = 920;BA.debugLine="ToastMessageShow(\"Error cargando el análisis\", F";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Error cargando el análisis"),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 921;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 //BA.debugLineNum = 922;BA.debugLine="Return False";
if (true) {
anywheresoftware.b4a.keywords.Common.ReturnFromResumableSub(this,(Object)(anywheresoftware.b4a.keywords.Common.False));return;};
 if (true) break;

case 5:
//C
this.state = 6;
 //BA.debugLineNum = 926;BA.debugLine="username = datosMap.Get(\"usuario\")";
_username = BA.ObjectToString(_datosmap.Get((Object)("usuario")));
 //BA.debugLineNum = 927;BA.debugLine="useremail = Main.strUserEmail";
_useremail = parent.mostCurrent._main._struseremail /*String*/ ;
 //BA.debugLineNum = 928;BA.debugLine="dateandtime = datosMap.Get(\"georeferenceddate\")";
_dateandtime = BA.ObjectToString(_datosmap.Get((Object)("georeferenceddate")));
 //BA.debugLineNum = 929;BA.debugLine="nombresitio = datosMap.Get(\"nombresitio\")";
_nombresitio = BA.ObjectToString(_datosmap.Get((Object)("nombresitio")));
 //BA.debugLineNum = 930;BA.debugLine="tiporio = datosMap.Get(\"tiporio\")";
parent.mostCurrent._tiporio = BA.ObjectToString(_datosmap.Get((Object)("tiporio")));
 //BA.debugLineNum = 931;BA.debugLine="lat = datosMap.Get(\"decimallatitude\")";
parent._lat = BA.ObjectToString(_datosmap.Get((Object)("decimallatitude")));
 //BA.debugLineNum = 932;BA.debugLine="lng = datosMap.Get(\"decimallongitude\")";
parent._lng = BA.ObjectToString(_datosmap.Get((Object)("decimallongitude")));
 //BA.debugLineNum = 933;BA.debugLine="gpsdetect = datosMap.Get(\"gpsdetect\")";
_gpsdetect = BA.ObjectToString(_datosmap.Get((Object)("gpsdetect")));
 //BA.debugLineNum = 934;BA.debugLine="wifidetect = datosMap.Get(\"wifidetect\")";
_wifidetect = BA.ObjectToString(_datosmap.Get((Object)("wifidetect")));
 //BA.debugLineNum = 935;BA.debugLine="mapadetect = datosMap.Get(\"mapadetect\")";
_mapadetect = BA.ObjectToString(_datosmap.Get((Object)("mapadetect")));
 //BA.debugLineNum = 936;BA.debugLine="partido = Geopartido";
_partido = parent._geopartido;
 //BA.debugLineNum = 937;BA.debugLine="valorcalidad = datosMap.Get(\"valorcalidad\")";
_valorcalidad = BA.ObjectToString(_datosmap.Get((Object)("valorcalidad")));
 //BA.debugLineNum = 938;BA.debugLine="valorNS = datosMap.Get(\"valorind20\")";
_valorns = BA.ObjectToString(_datosmap.Get((Object)("valorind20")));
 //BA.debugLineNum = 939;BA.debugLine="valorind1 = datosMap.Get(\"valorind1\")";
parent.mostCurrent._valorind1 = BA.ObjectToString(_datosmap.Get((Object)("valorind1")));
 //BA.debugLineNum = 940;BA.debugLine="valorind2 = datosMap.Get(\"valorind2\")";
_valorind2 = BA.ObjectToString(_datosmap.Get((Object)("valorind2")));
 //BA.debugLineNum = 941;BA.debugLine="valorind3 = datosMap.Get(\"valorind3\")";
_valorind3 = BA.ObjectToString(_datosmap.Get((Object)("valorind3")));
 //BA.debugLineNum = 942;BA.debugLine="valorind4 = datosMap.Get(\"valorind4\")";
_valorind4 = BA.ObjectToString(_datosmap.Get((Object)("valorind4")));
 //BA.debugLineNum = 943;BA.debugLine="valorind5 = datosMap.Get(\"valorind5\")";
_valorind5 = BA.ObjectToString(_datosmap.Get((Object)("valorind5")));
 //BA.debugLineNum = 944;BA.debugLine="valorind6 = datosMap.Get(\"valorind6\")";
_valorind6 = BA.ObjectToString(_datosmap.Get((Object)("valorind6")));
 //BA.debugLineNum = 945;BA.debugLine="valorind7 = datosMap.Get(\"valorind7\")";
_valorind7 = BA.ObjectToString(_datosmap.Get((Object)("valorind7")));
 //BA.debugLineNum = 946;BA.debugLine="valorind8 = datosMap.Get(\"valorind8\")";
_valorind8 = BA.ObjectToString(_datosmap.Get((Object)("valorind8")));
 //BA.debugLineNum = 947;BA.debugLine="valorind9 = datosMap.Get(\"valorind9\")";
_valorind9 = BA.ObjectToString(_datosmap.Get((Object)("valorind9")));
 //BA.debugLineNum = 948;BA.debugLine="valorind10 = datosMap.Get(\"valorind10\")";
_valorind10 = BA.ObjectToString(_datosmap.Get((Object)("valorind10")));
 //BA.debugLineNum = 949;BA.debugLine="valorind11 = datosMap.Get(\"valorind11\")";
_valorind11 = BA.ObjectToString(_datosmap.Get((Object)("valorind11")));
 //BA.debugLineNum = 950;BA.debugLine="valorind12 = datosMap.Get(\"valorind12\")";
_valorind12 = BA.ObjectToString(_datosmap.Get((Object)("valorind12")));
 //BA.debugLineNum = 951;BA.debugLine="valorind13 = datosMap.Get(\"valorind13\")";
_valorind13 = BA.ObjectToString(_datosmap.Get((Object)("valorind13")));
 //BA.debugLineNum = 952;BA.debugLine="valorind14 = datosMap.Get(\"valorind14\")";
_valorind14 = BA.ObjectToString(_datosmap.Get((Object)("valorind14")));
 //BA.debugLineNum = 953;BA.debugLine="valorind15 = datosMap.Get(\"valorind15\")";
_valorind15 = BA.ObjectToString(_datosmap.Get((Object)("valorind15")));
 //BA.debugLineNum = 954;BA.debugLine="valorind16 = datosMap.Get(\"valorind16\")";
_valorind16 = BA.ObjectToString(_datosmap.Get((Object)("valorind16")));
 //BA.debugLineNum = 955;BA.debugLine="valorind17 = datosMap.Get(\"valorind17\")";
_valorind17 = BA.ObjectToString(_datosmap.Get((Object)("valorind17")));
 //BA.debugLineNum = 956;BA.debugLine="valorind18 = datosMap.Get(\"valorind18\")";
_valorind18 = BA.ObjectToString(_datosmap.Get((Object)("valorind18")));
 //BA.debugLineNum = 957;BA.debugLine="valorind19 = datosMap.Get(\"valorind19\")";
_valorind19 = BA.ObjectToString(_datosmap.Get((Object)("valorind19")));
 //BA.debugLineNum = 959;BA.debugLine="notas = datosMap.Get(\"notas\")";
_notas = BA.ObjectToString(_datosmap.Get((Object)("notas")));
 //BA.debugLineNum = 960;BA.debugLine="bingo = datosMap.Get(\"bingo\")";
_bingo = BA.ObjectToString(_datosmap.Get((Object)("bingo")));
 //BA.debugLineNum = 961;BA.debugLine="foto1 = datosMap.Get(\"foto1\")";
parent.mostCurrent._foto1 = BA.ObjectToString(_datosmap.Get((Object)("foto1")));
 //BA.debugLineNum = 962;BA.debugLine="terminado = datosMap.Get(\"terminado\")";
_terminado = BA.ObjectToString(_datosmap.Get((Object)("terminado")));
 //BA.debugLineNum = 963;BA.debugLine="privado = datosMap.Get(\"privado\")";
_privado = BA.ObjectToString(_datosmap.Get((Object)("privado")));
 //BA.debugLineNum = 964;BA.debugLine="If privado = Null Or privado = \"null\" Or privado";
if (true) break;

case 6:
//if
this.state = 9;
if (_privado== null || (_privado).equals("null") || (_privado).equals("")) { 
this.state = 8;
}if (true) break;

case 8:
//C
this.state = 9;
 //BA.debugLineNum = 965;BA.debugLine="privado = \"no\"";
_privado = "no";
 if (true) break;

case 9:
//C
this.state = 10;
;
 //BA.debugLineNum = 967;BA.debugLine="estadovalidacion = datosMap.Get(\"estadovalidacio";
_estadovalidacion = BA.ObjectToString(_datosmap.Get((Object)("estadovalidacion")));
 //BA.debugLineNum = 968;BA.debugLine="If estadovalidacion = \"null\" Then";
if (true) break;

case 10:
//if
this.state = 13;
if ((_estadovalidacion).equals("null")) { 
this.state = 12;
}if (true) break;

case 12:
//C
this.state = 13;
 //BA.debugLineNum = 969;BA.debugLine="estadovalidacion = \"No Verificado\"";
_estadovalidacion = "No Verificado";
 if (true) break;

case 13:
//C
this.state = 14;
;
 //BA.debugLineNum = 971;BA.debugLine="deviceID = datosMap.Get(\"deviceID\")";
_deviceid = BA.ObjectToString(_datosmap.Get((Object)("deviceID")));
 //BA.debugLineNum = 972;BA.debugLine="If deviceID = Null Or deviceID = \"\" Or deviceID";
if (true) break;

case 14:
//if
this.state = 17;
if (_deviceid== null || (_deviceid).equals("") || (_deviceid).equals("null")) { 
this.state = 16;
}if (true) break;

case 16:
//C
this.state = 17;
 //BA.debugLineNum = 973;BA.debugLine="deviceID = utilidades.GetDeviceId";
_deviceid = parent.mostCurrent._utilidades._getdeviceid /*String*/ (mostCurrent.activityBA);
 if (true) break;

case 17:
//C
this.state = 18;
;
 if (true) break;

case 18:
//C
this.state = 19;
;
 //BA.debugLineNum = 978;BA.debugLine="Dim j As HttpJob";
_j = new appear.pnud.preservamos.httpjob();
 //BA.debugLineNum = 979;BA.debugLine="j.Initialize(\"\", Me)";
_j._initialize /*String*/ (processBA,"",mod_residuos.getObject());
 //BA.debugLineNum = 980;BA.debugLine="Dim loginPath As String = Main.serverPath & \"/\" &";
_loginpath = parent.mostCurrent._main._serverpath /*String*/ +"/"+parent.mostCurrent._main._serverconnectionfolder /*String*/ +"/add_mod_residuos.php?"+"username="+_username+"&"+"useremail="+_useremail+"&"+"deviceID="+parent.mostCurrent._main._deviceid /*String*/ +"&"+"dateandtime="+_dateandtime+"&"+"nombresitio="+_nombresitio+"&"+"lat="+parent._lat+"&"+"lng="+parent._lng+"&"+"tiporio="+parent.mostCurrent._tiporio+"&"+"indice="+_valorcalidad+"&"+"precision="+_valorns+"&"+"valorind1="+parent.mostCurrent._valorind1+"&"+"valorind2="+_valorind2+"&"+"valorind3="+_valorind3+"&"+"valorind4="+_valorind4+"&"+"valorind5="+_valorind5+"&"+"valorind6="+_valorind6+"&"+"valorind7="+_valorind7+"&"+"valorind8="+_valorind8+"&"+"valorind9="+_valorind9+"&"+"valorind10="+_valorind10+"&"+"valorind11="+_valorind11+"&"+"valorind12="+_valorind12+"&"+"valorind13="+_valorind13+"&"+"valorind14="+_valorind14+"&"+"valorind15="+_valorind15+"&"+"valorind16="+_valorind16+"&"+"valorind17="+_valorind17+"&"+"valorind18="+_valorind18+"&"+"valorind19="+_valorind19+"&"+"foto1path="+parent.mostCurrent._foto1+"&"+"terminado="+_terminado+"&"+"verificado="+_estadovalidacion+"&"+"privado="+_privado+"&"+"bingo="+_bingo+"&"+"notas="+_notas+"&"+"partido="+_partido+"&"+"mapadetect="+_mapadetect+"&"+"wifidetect="+_wifidetect+"&"+"gpsdetect="+_gpsdetect;
 //BA.debugLineNum = 1021;BA.debugLine="j.Download(loginPath)";
_j._download /*String*/ (_loginpath);
 //BA.debugLineNum = 1022;BA.debugLine="Wait For (j) JobDone(j As HttpJob)";
anywheresoftware.b4a.keywords.Common.WaitFor("jobdone", processBA, this, (Object)(_j));
this.state = 51;
return;
case 51:
//C
this.state = 19;
_j = (appear.pnud.preservamos.httpjob) result[0];
;
 //BA.debugLineNum = 1024;BA.debugLine="If j.Success Then";
if (true) break;

case 19:
//if
this.state = 50;
if (_j._success /*boolean*/ ) { 
this.state = 21;
}else {
this.state = 43;
}if (true) break;

case 21:
//C
this.state = 22;
 //BA.debugLineNum = 1025;BA.debugLine="Dim ret As String";
_ret = "";
 //BA.debugLineNum = 1026;BA.debugLine="Dim act As String";
_act = "";
 //BA.debugLineNum = 1027;BA.debugLine="ret = j.GetString";
_ret = _j._getstring /*String*/ ();
 //BA.debugLineNum = 1028;BA.debugLine="Dim parser As JSONParser";
_parser = new anywheresoftware.b4a.objects.collections.JSONParser();
 //BA.debugLineNum = 1029;BA.debugLine="parser.Initialize(ret)";
_parser.Initialize(_ret);
 //BA.debugLineNum = 1030;BA.debugLine="act = parser.NextValue";
_act = BA.ObjectToString(_parser.NextValue());
 //BA.debugLineNum = 1031;BA.debugLine="If act = \"Not Found\" Then";
if (true) break;

case 22:
//if
this.state = 41;
if ((_act).equals("Not Found")) { 
this.state = 24;
}else if((_act).equals("Error")) { 
this.state = 32;
}else if((_act).equals("Mod_Dato_Agregado")) { 
this.state = 34;
}if (true) break;

case 24:
//C
this.state = 25;
 //BA.debugLineNum = 1032;BA.debugLine="If Main.lang = \"es\" Then";
if (true) break;

case 25:
//if
this.state = 30;
if ((parent.mostCurrent._main._lang /*String*/ ).equals("es")) { 
this.state = 27;
}else if((parent.mostCurrent._main._lang /*String*/ ).equals("en")) { 
this.state = 29;
}if (true) break;

case 27:
//C
this.state = 30;
 //BA.debugLineNum = 1033;BA.debugLine="ToastMessageShow(\"Error subiendo el dato\", Tru";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Error subiendo el dato"),anywheresoftware.b4a.keywords.Common.True);
 if (true) break;

case 29:
//C
this.state = 30;
 //BA.debugLineNum = 1035;BA.debugLine="ToastMessageShow(\"Error loading markers\", True";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Error loading markers"),anywheresoftware.b4a.keywords.Common.True);
 if (true) break;

case 30:
//C
this.state = 41;
;
 //BA.debugLineNum = 1037;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 //BA.debugLineNum = 1038;BA.debugLine="j.Release";
_j._release /*String*/ ();
 //BA.debugLineNum = 1039;BA.debugLine="Return False";
if (true) {
anywheresoftware.b4a.keywords.Common.ReturnFromResumableSub(this,(Object)(anywheresoftware.b4a.keywords.Common.False));return;};
 if (true) break;

case 32:
//C
this.state = 41;
 //BA.debugLineNum = 1041;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 //BA.debugLineNum = 1042;BA.debugLine="MsgboxAsync(\"Al parecer hay un problema en nues";
anywheresoftware.b4a.keywords.Common.MsgboxAsync(BA.ObjectToCharSequence("Al parecer hay un problema en nuestros servidores, lo solucionaremos pronto!"),BA.ObjectToCharSequence("Mala mía"),processBA);
 //BA.debugLineNum = 1043;BA.debugLine="j.Release";
_j._release /*String*/ ();
 //BA.debugLineNum = 1044;BA.debugLine="Return False";
if (true) {
anywheresoftware.b4a.keywords.Common.ReturnFromResumableSub(this,(Object)(anywheresoftware.b4a.keywords.Common.False));return;};
 if (true) break;

case 34:
//C
this.state = 35;
 //BA.debugLineNum = 1047;BA.debugLine="Dim nd As Map";
_nd = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 1048;BA.debugLine="nd = parser.NextObject";
_nd = _parser.NextObject();
 //BA.debugLineNum = 1049;BA.debugLine="Dim serverID As String";
_serverid = "";
 //BA.debugLineNum = 1050;BA.debugLine="serverID = nd.Get(\"serverId\")";
_serverid = BA.ObjectToString(_nd.Get((Object)("serverId")));
 //BA.debugLineNum = 1053;BA.debugLine="Dim Map1 As Map";
_map1 = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 1054;BA.debugLine="Map1.Initialize";
_map1.Initialize();
 //BA.debugLineNum = 1055;BA.debugLine="Map1.Put(\"Id\", Main.currentproject)";
_map1.Put((Object)("Id"),(Object)(parent.mostCurrent._main._currentproject /*String*/ ));
 //BA.debugLineNum = 1056;BA.debugLine="DBUtils.UpdateRecord(Starter.modulo_residuos_sq";
parent.mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,parent.mostCurrent._starter._modulo_residuos_sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals_residuos","evalsent",(Object)("si"),_map1);
 //BA.debugLineNum = 1057;BA.debugLine="DBUtils.UpdateRecord(Starter.modulo_residuos_sq";
parent.mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,parent.mostCurrent._starter._modulo_residuos_sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals_residuos","serverId",(Object)(_serverid),_map1);
 //BA.debugLineNum = 1058;BA.debugLine="If Main.lang = \"es\" Then";
if (true) break;

case 35:
//if
this.state = 40;
if ((parent.mostCurrent._main._lang /*String*/ ).equals("es")) { 
this.state = 37;
}else if((parent.mostCurrent._main._lang /*String*/ ).equals("en")) { 
this.state = 39;
}if (true) break;

case 37:
//C
this.state = 40;
 //BA.debugLineNum = 1059;BA.debugLine="ToastMessageShow(\"Datos enviados, enviando fot";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Datos enviados, enviando fotos"),anywheresoftware.b4a.keywords.Common.False);
 if (true) break;

case 39:
//C
this.state = 40;
 //BA.debugLineNum = 1061;BA.debugLine="ToastMessageShow(\"Report sent, sending photos\"";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Report sent, sending photos"),anywheresoftware.b4a.keywords.Common.False);
 if (true) break;

case 40:
//C
this.state = 41;
;
 //BA.debugLineNum = 1063;BA.debugLine="j.Release";
_j._release /*String*/ ();
 //BA.debugLineNum = 1064;BA.debugLine="Return True";
if (true) {
anywheresoftware.b4a.keywords.Common.ReturnFromResumableSub(this,(Object)(anywheresoftware.b4a.keywords.Common.True));return;};
 if (true) break;

case 41:
//C
this.state = 50;
;
 if (true) break;

case 43:
//C
this.state = 44;
 //BA.debugLineNum = 1068;BA.debugLine="Log(\"envio datos not ok\")";
anywheresoftware.b4a.keywords.Common.LogImpl("049086628","envio datos not ok",0);
 //BA.debugLineNum = 1069;BA.debugLine="If Main.lang = \"es\" Then";
if (true) break;

case 44:
//if
this.state = 49;
if ((parent.mostCurrent._main._lang /*String*/ ).equals("es")) { 
this.state = 46;
}else if((parent.mostCurrent._main._lang /*String*/ ).equals("en")) { 
this.state = 48;
}if (true) break;

case 46:
//C
this.state = 49;
 //BA.debugLineNum = 1070;BA.debugLine="MsgboxAsync(\"Al parecer hay un problema en nues";
anywheresoftware.b4a.keywords.Common.MsgboxAsync(BA.ObjectToCharSequence("Al parecer hay un problema en nuestros servidores, lo solucionaremos pronto!"),BA.ObjectToCharSequence("Mala mía"),processBA);
 if (true) break;

case 48:
//C
this.state = 49;
 //BA.debugLineNum = 1072;BA.debugLine="MsgboxAsync(\"There seems to be a problem with o";
anywheresoftware.b4a.keywords.Common.MsgboxAsync(BA.ObjectToCharSequence("There seems to be a problem with our servers, we will solve it soon!"),BA.ObjectToCharSequence("My bad"),processBA);
 if (true) break;

case 49:
//C
this.state = 50;
;
 //BA.debugLineNum = 1074;BA.debugLine="btnOkResumen1.Enabled = True";
parent.mostCurrent._btnokresumen1.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1075;BA.debugLine="btnOkResumen1.Text = \"¡Finalizar y enviar!\"";
parent.mostCurrent._btnokresumen1.setText(BA.ObjectToCharSequence("¡Finalizar y enviar!"));
 //BA.debugLineNum = 1077;BA.debugLine="j.Release";
_j._release /*String*/ ();
 //BA.debugLineNum = 1078;BA.debugLine="Return False";
if (true) {
anywheresoftware.b4a.keywords.Common.ReturnFromResumableSub(this,(Object)(anywheresoftware.b4a.keywords.Common.False));return;};
 if (true) break;

case 50:
//C
this.state = -1;
;
 //BA.debugLineNum = 1082;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public static void  _jobdone(appear.pnud.preservamos.httpjob _j) throws Exception{
}
public static String  _fondogris_click() throws Exception{
 //BA.debugLineNum = 539;BA.debugLine="Private Sub fondogris_Click";
 //BA.debugLineNum = 542;BA.debugLine="End Sub";
return "";
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 25;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 27;BA.debugLine="Private tabContainer As TabStrip";
mostCurrent._tabcontainer = new anywheresoftware.b4a.objects.TabStripViewPager();
 //BA.debugLineNum = 28;BA.debugLine="Private lblCirculoPos1 As Label";
mostCurrent._lblcirculopos1 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 29;BA.debugLine="Private lblCirculoPos2 As Label";
mostCurrent._lblcirculopos2 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 30;BA.debugLine="Private lblCirculoPos3 As Label";
mostCurrent._lblcirculopos3 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 31;BA.debugLine="Private btnNext As Button";
mostCurrent._btnnext = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 32;BA.debugLine="Private btnPrev As Button";
mostCurrent._btnprev = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 33;BA.debugLine="Private lblCirculoPos4 As Label";
mostCurrent._lblcirculopos4 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 35;BA.debugLine="Private lblRojo As Label";
mostCurrent._lblrojo = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 36;BA.debugLine="Private scrollUsos As ScrollView";
mostCurrent._scrollusos = new anywheresoftware.b4a.objects.ScrollViewWrapper();
 //BA.debugLineNum = 37;BA.debugLine="Private btnOkBasico1 As Button";
mostCurrent._btnokbasico1 = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 38;BA.debugLine="Private lblRojo_Q5_1 As Label";
mostCurrent._lblrojo_q5_1 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 39;BA.debugLine="Private imgRioLlanura As ImageView";
mostCurrent._imgriollanura = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 40;BA.debugLine="Private imgLaguna As ImageView";
mostCurrent._imglaguna = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 41;BA.debugLine="Private imgQ5_1_a As ImageView";
mostCurrent._imgq5_1_a = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 42;BA.debugLine="Private imgQ5_1_b As ImageView";
mostCurrent._imgq5_1_b = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 43;BA.debugLine="Private imgQ5_1_d As ImageView";
mostCurrent._imgq5_1_d = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 44;BA.debugLine="Private imgQ5_1_c As ImageView";
mostCurrent._imgq5_1_c = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 45;BA.debugLine="Private imgQ5_1_e As ImageView";
mostCurrent._imgq5_1_e = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 46;BA.debugLine="Private imgQ5_1_f As ImageView";
mostCurrent._imgq5_1_f = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 47;BA.debugLine="Private imgQ5_1_h As ImageView";
mostCurrent._imgq5_1_h = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 48;BA.debugLine="Private imgQ5_1_g As ImageView";
mostCurrent._imgq5_1_g = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 49;BA.debugLine="Private imgQ5_1_i As ImageView";
mostCurrent._imgq5_1_i = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 50;BA.debugLine="Private imgQ5_1_j As ImageView";
mostCurrent._imgq5_1_j = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 51;BA.debugLine="Dim coordenadas_string As String";
mostCurrent._coordenadas_string = "";
 //BA.debugLineNum = 53;BA.debugLine="Private lblFecha As Label";
mostCurrent._lblfecha = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 54;BA.debugLine="Private lblCoordenadasCarga As Label";
mostCurrent._lblcoordenadascarga = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 57;BA.debugLine="Dim fondogris As Panel";
mostCurrent._fondogris = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 58;BA.debugLine="Dim panelComoFunciona As Panel";
mostCurrent._panelcomofunciona = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 61;BA.debugLine="Dim listTotal As Int";
_listtotal = 0;
 //BA.debugLineNum = 62;BA.debugLine="Dim listPlastico1 As Int";
_listplastico1 = 0;
 //BA.debugLineNum = 63;BA.debugLine="Dim listPlastico2 As Int";
_listplastico2 = 0;
 //BA.debugLineNum = 64;BA.debugLine="Dim listPlastico3 As Int";
_listplastico3 = 0;
 //BA.debugLineNum = 65;BA.debugLine="Dim listPlastico4 As Int";
_listplastico4 = 0;
 //BA.debugLineNum = 66;BA.debugLine="Dim listPlastico5 As Int";
_listplastico5 = 0;
 //BA.debugLineNum = 67;BA.debugLine="Dim listPlastico6 As Int";
_listplastico6 = 0;
 //BA.debugLineNum = 68;BA.debugLine="Dim listPlastico7 As Int";
_listplastico7 = 0;
 //BA.debugLineNum = 69;BA.debugLine="Dim listPlastico8 As Int";
_listplastico8 = 0;
 //BA.debugLineNum = 70;BA.debugLine="Dim listPlastico9 As Int";
_listplastico9 = 0;
 //BA.debugLineNum = 71;BA.debugLine="Dim listCarton As Int";
_listcarton = 0;
 //BA.debugLineNum = 72;BA.debugLine="Dim listMetales As Int";
_listmetales = 0;
 //BA.debugLineNum = 73;BA.debugLine="Dim listVidrios As Int";
_listvidrios = 0;
 //BA.debugLineNum = 74;BA.debugLine="Dim listOtros As Int";
_listotros = 0;
 //BA.debugLineNum = 75;BA.debugLine="Dim panelSubPlasticos As Panel";
mostCurrent._panelsubplasticos = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 76;BA.debugLine="Private B4XPlusMinus1 As B4XPlusMinus";
mostCurrent._b4xplusminus1 = new appear.pnud.preservamos.b4xplusminus();
 //BA.debugLineNum = 77;BA.debugLine="Private B4XPlusMinus2 As B4XPlusMinus";
mostCurrent._b4xplusminus2 = new appear.pnud.preservamos.b4xplusminus();
 //BA.debugLineNum = 78;BA.debugLine="Private B4XPlusMinus3 As B4XPlusMinus";
mostCurrent._b4xplusminus3 = new appear.pnud.preservamos.b4xplusminus();
 //BA.debugLineNum = 79;BA.debugLine="Private B4XPlusMinus4 As B4XPlusMinus";
mostCurrent._b4xplusminus4 = new appear.pnud.preservamos.b4xplusminus();
 //BA.debugLineNum = 80;BA.debugLine="Private B4XPlusMinus5 As B4XPlusMinus";
mostCurrent._b4xplusminus5 = new appear.pnud.preservamos.b4xplusminus();
 //BA.debugLineNum = 81;BA.debugLine="Private B4XPlusMinus6 As B4XPlusMinus";
mostCurrent._b4xplusminus6 = new appear.pnud.preservamos.b4xplusminus();
 //BA.debugLineNum = 82;BA.debugLine="Private B4XPlusMinus7 As B4XPlusMinus";
mostCurrent._b4xplusminus7 = new appear.pnud.preservamos.b4xplusminus();
 //BA.debugLineNum = 83;BA.debugLine="Private B4XPlusMinus8 As B4XPlusMinus";
mostCurrent._b4xplusminus8 = new appear.pnud.preservamos.b4xplusminus();
 //BA.debugLineNum = 84;BA.debugLine="Private B4XPlusMinus9 As B4XPlusMinus";
mostCurrent._b4xplusminus9 = new appear.pnud.preservamos.b4xplusminus();
 //BA.debugLineNum = 85;BA.debugLine="Private B4XPlusMinus10 As B4XPlusMinus";
mostCurrent._b4xplusminus10 = new appear.pnud.preservamos.b4xplusminus();
 //BA.debugLineNum = 86;BA.debugLine="Private B4XPlusMinus11 As B4XPlusMinus";
mostCurrent._b4xplusminus11 = new appear.pnud.preservamos.b4xplusminus();
 //BA.debugLineNum = 87;BA.debugLine="Private B4XPlusMinus12 As B4XPlusMinus";
mostCurrent._b4xplusminus12 = new appear.pnud.preservamos.b4xplusminus();
 //BA.debugLineNum = 88;BA.debugLine="Private B4XPlusMinus13 As B4XPlusMinus";
mostCurrent._b4xplusminus13 = new appear.pnud.preservamos.b4xplusminus();
 //BA.debugLineNum = 89;BA.debugLine="Private lblContadorCarga As Label";
mostCurrent._lblcontadorcarga = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 90;BA.debugLine="Private ScrollView1 As ScrollView";
mostCurrent._scrollview1 = new anywheresoftware.b4a.objects.ScrollViewWrapper();
 //BA.debugLineNum = 91;BA.debugLine="Private btnPlasticos As Button";
mostCurrent._btnplasticos = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 92;BA.debugLine="Private lblTiempoCarga As Label";
mostCurrent._lbltiempocarga = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 93;BA.debugLine="Dim valorind1 As String";
mostCurrent._valorind1 = "";
 //BA.debugLineNum = 94;BA.debugLine="Dim tiporio As String";
mostCurrent._tiporio = "";
 //BA.debugLineNum = 95;BA.debugLine="Private timerCarga As Timer";
mostCurrent._timercarga = new anywheresoftware.b4a.objects.Timer();
 //BA.debugLineNum = 96;BA.debugLine="Dim timeCarga As Int";
_timecarga = 0;
 //BA.debugLineNum = 97;BA.debugLine="Private lblTiempoCarga_Resumen As Label";
mostCurrent._lbltiempocarga_resumen = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 98;BA.debugLine="Private txtDistanciaCaminada As B4XFloatTextField";
mostCurrent._txtdistanciacaminada = new appear.pnud.preservamos.b4xfloattextfield();
 //BA.debugLineNum = 99;BA.debugLine="Private btnCarton As Button";
mostCurrent._btncarton = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 100;BA.debugLine="Private btnMetales As Button";
mostCurrent._btnmetales = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 101;BA.debugLine="Private btnOtros As Button";
mostCurrent._btnotros = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 102;BA.debugLine="Private btnVidrios As Button";
mostCurrent._btnvidrios = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 103;BA.debugLine="Private btnOkCarga As Button";
mostCurrent._btnokcarga = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 104;BA.debugLine="Private btnGuiaCategorias As Button";
mostCurrent._btnguiacategorias = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 107;BA.debugLine="Dim proyectoIDenviar As String";
mostCurrent._proyectoidenviar = "";
 //BA.debugLineNum = 108;BA.debugLine="Dim files As List";
mostCurrent._files = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 110;BA.debugLine="Private lblCoordenadasTitle As Label";
mostCurrent._lblcoordenadastitle = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 111;BA.debugLine="Private borderFotos1 As Label";
mostCurrent._borderfotos1 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 112;BA.debugLine="Private imgFoto1 As ImageView";
mostCurrent._imgfoto1 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 113;BA.debugLine="Private ProgressBar1 As ProgressBar";
mostCurrent._progressbar1 = new anywheresoftware.b4a.objects.ProgressBarWrapper();
 //BA.debugLineNum = 114;BA.debugLine="Private listResumen As ListView";
mostCurrent._listresumen = new anywheresoftware.b4a.objects.ListViewWrapper();
 //BA.debugLineNum = 115;BA.debugLine="Dim foto1 As String";
mostCurrent._foto1 = "";
 //BA.debugLineNum = 116;BA.debugLine="Private totalFotos As Int";
_totalfotos = 0;
 //BA.debugLineNum = 117;BA.debugLine="Private foto1Sent As Boolean = False";
_foto1sent = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 118;BA.debugLine="Private ProgressBar1 As ProgressBar";
mostCurrent._progressbar1 = new anywheresoftware.b4a.objects.ProgressBarWrapper();
 //BA.debugLineNum = 119;BA.debugLine="Private foto1ProgressBar As CircularProgressBar";
mostCurrent._foto1progressbar = new appear.pnud.preservamos.circularprogressbar();
 //BA.debugLineNum = 120;BA.debugLine="Dim fotosEnviadas As Int";
_fotosenviadas = 0;
 //BA.debugLineNum = 121;BA.debugLine="Dim pw As PhoneWakeState";
mostCurrent._pw = new anywheresoftware.b4a.phone.Phone.PhoneWakeState();
 //BA.debugLineNum = 122;BA.debugLine="Private imgListo As ImageView";
mostCurrent._imglisto = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 123;BA.debugLine="Private btnOkResumen1 As Button";
mostCurrent._btnokresumen1 = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 124;BA.debugLine="Private lblStatusResiduos As Label";
mostCurrent._lblstatusresiduos = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 125;BA.debugLine="Private lblResumenListTitle As Label";
mostCurrent._lblresumenlisttitle = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 126;BA.debugLine="Private btnVerDetalle As Button";
mostCurrent._btnverdetalle = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 127;BA.debugLine="Private lblContadorResumen As Label";
mostCurrent._lblcontadorresumen = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 128;BA.debugLine="Private Label1 As Label";
mostCurrent._label1 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 129;BA.debugLine="Private lblPiezas As Label";
mostCurrent._lblpiezas = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 130;BA.debugLine="End Sub";
return "";
}
public static String  _imglaguna_click() throws Exception{
 //BA.debugLineNum = 271;BA.debugLine="Private Sub imgLaguna_Click";
 //BA.debugLineNum = 272;BA.debugLine="tiporio = \"laguna\"";
mostCurrent._tiporio = "laguna";
 //BA.debugLineNum = 273;BA.debugLine="lblRojo.Left = imgLaguna.Left";
mostCurrent._lblrojo.setLeft(mostCurrent._imglaguna.getLeft());
 //BA.debugLineNum = 274;BA.debugLine="lblRojo.Visible = True";
mostCurrent._lblrojo.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 275;BA.debugLine="End Sub";
return "";
}
public static String  _imgq5_1_a_click() throws Exception{
 //BA.debugLineNum = 283;BA.debugLine="Private Sub imgQ5_1_a_Click";
 //BA.debugLineNum = 284;BA.debugLine="valorind1 = \"industria\"";
mostCurrent._valorind1 = "industria";
 //BA.debugLineNum = 285;BA.debugLine="lblRojo_Q5_1.Left = imgQ5_1_a.Left";
mostCurrent._lblrojo_q5_1.setLeft(mostCurrent._imgq5_1_a.getLeft());
 //BA.debugLineNum = 286;BA.debugLine="lblRojo_Q5_1.Top = imgQ5_1_a.Top + imgQ5_1_a.Heig";
mostCurrent._lblrojo_q5_1.setTop((int) (mostCurrent._imgq5_1_a.getTop()+mostCurrent._imgq5_1_a.getHeight()/(double)2));
 //BA.debugLineNum = 287;BA.debugLine="lblRojo_Q5_1.Visible = True";
mostCurrent._lblrojo_q5_1.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 288;BA.debugLine="End Sub";
return "";
}
public static String  _imgq5_1_b_click() throws Exception{
 //BA.debugLineNum = 289;BA.debugLine="Private Sub imgQ5_1_b_Click";
 //BA.debugLineNum = 290;BA.debugLine="valorind1 = \"ciudad\"";
mostCurrent._valorind1 = "ciudad";
 //BA.debugLineNum = 291;BA.debugLine="lblRojo_Q5_1.Left = imgQ5_1_b.Left";
mostCurrent._lblrojo_q5_1.setLeft(mostCurrent._imgq5_1_b.getLeft());
 //BA.debugLineNum = 292;BA.debugLine="lblRojo_Q5_1.Top = imgQ5_1_b.Top + imgQ5_1_b.Heig";
mostCurrent._lblrojo_q5_1.setTop((int) (mostCurrent._imgq5_1_b.getTop()+mostCurrent._imgq5_1_b.getHeight()/(double)2));
 //BA.debugLineNum = 293;BA.debugLine="lblRojo_Q5_1.Visible = True";
mostCurrent._lblrojo_q5_1.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 294;BA.debugLine="End Sub";
return "";
}
public static String  _imgq5_1_c_click() throws Exception{
 //BA.debugLineNum = 295;BA.debugLine="Private Sub imgQ5_1_c_Click";
 //BA.debugLineNum = 296;BA.debugLine="valorind1 = \"barrio\"";
mostCurrent._valorind1 = "barrio";
 //BA.debugLineNum = 297;BA.debugLine="lblRojo_Q5_1.Left = imgQ5_1_c.Left";
mostCurrent._lblrojo_q5_1.setLeft(mostCurrent._imgq5_1_c.getLeft());
 //BA.debugLineNum = 298;BA.debugLine="lblRojo_Q5_1.Top = imgQ5_1_c.Top + imgQ5_1_c.Heig";
mostCurrent._lblrojo_q5_1.setTop((int) (mostCurrent._imgq5_1_c.getTop()+mostCurrent._imgq5_1_c.getHeight()/(double)2));
 //BA.debugLineNum = 299;BA.debugLine="lblRojo_Q5_1.Visible = True";
mostCurrent._lblrojo_q5_1.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 300;BA.debugLine="End Sub";
return "";
}
public static String  _imgq5_1_d_click() throws Exception{
 //BA.debugLineNum = 301;BA.debugLine="Private Sub imgQ5_1_d_Click";
 //BA.debugLineNum = 302;BA.debugLine="valorind1 = \"reserva\"";
mostCurrent._valorind1 = "reserva";
 //BA.debugLineNum = 303;BA.debugLine="lblRojo_Q5_1.Left = imgQ5_1_d.Left";
mostCurrent._lblrojo_q5_1.setLeft(mostCurrent._imgq5_1_d.getLeft());
 //BA.debugLineNum = 304;BA.debugLine="lblRojo_Q5_1.Top = imgQ5_1_d.Top + imgQ5_1_d.Heig";
mostCurrent._lblrojo_q5_1.setTop((int) (mostCurrent._imgq5_1_d.getTop()+mostCurrent._imgq5_1_d.getHeight()/(double)2));
 //BA.debugLineNum = 305;BA.debugLine="lblRojo_Q5_1.Visible = True";
mostCurrent._lblrojo_q5_1.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 306;BA.debugLine="End Sub";
return "";
}
public static String  _imgq5_1_e_click() throws Exception{
 //BA.debugLineNum = 307;BA.debugLine="Private Sub imgQ5_1_e_Click";
 //BA.debugLineNum = 308;BA.debugLine="valorind1 = \"monte\"";
mostCurrent._valorind1 = "monte";
 //BA.debugLineNum = 309;BA.debugLine="lblRojo_Q5_1.Left = imgQ5_1_e.Left";
mostCurrent._lblrojo_q5_1.setLeft(mostCurrent._imgq5_1_e.getLeft());
 //BA.debugLineNum = 310;BA.debugLine="lblRojo_Q5_1.Top = imgQ5_1_e.Top + imgQ5_1_e.Heig";
mostCurrent._lblrojo_q5_1.setTop((int) (mostCurrent._imgq5_1_e.getTop()+mostCurrent._imgq5_1_e.getHeight()/(double)2));
 //BA.debugLineNum = 311;BA.debugLine="lblRojo_Q5_1.Visible = True";
mostCurrent._lblrojo_q5_1.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 312;BA.debugLine="End Sub";
return "";
}
public static String  _imgq5_1_f_click() throws Exception{
 //BA.debugLineNum = 313;BA.debugLine="Private Sub imgQ5_1_f_Click";
 //BA.debugLineNum = 314;BA.debugLine="valorind1 = \"plaza\"";
mostCurrent._valorind1 = "plaza";
 //BA.debugLineNum = 315;BA.debugLine="lblRojo_Q5_1.Left = imgQ5_1_f.Left";
mostCurrent._lblrojo_q5_1.setLeft(mostCurrent._imgq5_1_f.getLeft());
 //BA.debugLineNum = 316;BA.debugLine="lblRojo_Q5_1.Top = imgQ5_1_f.Top + imgQ5_1_f.Heig";
mostCurrent._lblrojo_q5_1.setTop((int) (mostCurrent._imgq5_1_f.getTop()+mostCurrent._imgq5_1_f.getHeight()/(double)2));
 //BA.debugLineNum = 317;BA.debugLine="lblRojo_Q5_1.Visible = True";
mostCurrent._lblrojo_q5_1.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 318;BA.debugLine="End Sub";
return "";
}
public static String  _imgq5_1_g_click() throws Exception{
 //BA.debugLineNum = 319;BA.debugLine="Private Sub imgQ5_1_g_Click";
 //BA.debugLineNum = 320;BA.debugLine="valorind1 = \"basural\"";
mostCurrent._valorind1 = "basural";
 //BA.debugLineNum = 321;BA.debugLine="lblRojo_Q5_1.Left = imgQ5_1_g.Left";
mostCurrent._lblrojo_q5_1.setLeft(mostCurrent._imgq5_1_g.getLeft());
 //BA.debugLineNum = 322;BA.debugLine="lblRojo_Q5_1.Top = imgQ5_1_g.Top + imgQ5_1_g.Heig";
mostCurrent._lblrojo_q5_1.setTop((int) (mostCurrent._imgq5_1_g.getTop()+mostCurrent._imgq5_1_g.getHeight()/(double)2));
 //BA.debugLineNum = 323;BA.debugLine="lblRojo_Q5_1.Visible = True";
mostCurrent._lblrojo_q5_1.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 324;BA.debugLine="End Sub";
return "";
}
public static String  _imgq5_1_h_click() throws Exception{
 //BA.debugLineNum = 325;BA.debugLine="Private Sub imgQ5_1_h_Click";
 //BA.debugLineNum = 326;BA.debugLine="valorind1 = \"campo\"";
mostCurrent._valorind1 = "campo";
 //BA.debugLineNum = 327;BA.debugLine="lblRojo_Q5_1.Left = imgQ5_1_h.Left";
mostCurrent._lblrojo_q5_1.setLeft(mostCurrent._imgq5_1_h.getLeft());
 //BA.debugLineNum = 328;BA.debugLine="lblRojo_Q5_1.Top = imgQ5_1_h.Top + imgQ5_1_h.Heig";
mostCurrent._lblrojo_q5_1.setTop((int) (mostCurrent._imgq5_1_h.getTop()+mostCurrent._imgq5_1_h.getHeight()/(double)2));
 //BA.debugLineNum = 329;BA.debugLine="lblRojo_Q5_1.Visible = True";
mostCurrent._lblrojo_q5_1.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 330;BA.debugLine="End Sub";
return "";
}
public static String  _imgq5_1_i_click() throws Exception{
 //BA.debugLineNum = 331;BA.debugLine="Private Sub imgQ5_1_i_Click";
 //BA.debugLineNum = 332;BA.debugLine="valorind1 = \"NS\"";
mostCurrent._valorind1 = "NS";
 //BA.debugLineNum = 333;BA.debugLine="lblRojo_Q5_1.Left = imgQ5_1_i.Left";
mostCurrent._lblrojo_q5_1.setLeft(mostCurrent._imgq5_1_i.getLeft());
 //BA.debugLineNum = 334;BA.debugLine="lblRojo_Q5_1.Top = imgQ5_1_i.Top + imgQ5_1_i.Heig";
mostCurrent._lblrojo_q5_1.setTop((int) (mostCurrent._imgq5_1_i.getTop()+mostCurrent._imgq5_1_i.getHeight()/(double)2));
 //BA.debugLineNum = 335;BA.debugLine="lblRojo_Q5_1.Visible = True";
mostCurrent._lblrojo_q5_1.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 336;BA.debugLine="End Sub";
return "";
}
public static String  _imgq5_1_j_click() throws Exception{
 //BA.debugLineNum = 337;BA.debugLine="Private Sub imgQ5_1_j_Click";
 //BA.debugLineNum = 338;BA.debugLine="valorind1 = 0";
mostCurrent._valorind1 = BA.NumberToString(0);
 //BA.debugLineNum = 339;BA.debugLine="lblRojo_Q5_1.Left = imgQ5_1_j.Left";
mostCurrent._lblrojo_q5_1.setLeft(mostCurrent._imgq5_1_j.getLeft());
 //BA.debugLineNum = 340;BA.debugLine="lblRojo_Q5_1.Top = imgQ5_1_j.Top + imgQ5_1_j.Heig";
mostCurrent._lblrojo_q5_1.setTop((int) (mostCurrent._imgq5_1_j.getTop()+mostCurrent._imgq5_1_j.getHeight()/(double)2));
 //BA.debugLineNum = 341;BA.debugLine="lblRojo_Q5_1.Visible = True";
mostCurrent._lblrojo_q5_1.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 342;BA.debugLine="End Sub";
return "";
}
public static String  _imgriollanura_click() throws Exception{
 //BA.debugLineNum = 276;BA.debugLine="Private Sub imgRioLlanura_Click";
 //BA.debugLineNum = 277;BA.debugLine="tiporio = \"llanura\"";
mostCurrent._tiporio = "llanura";
 //BA.debugLineNum = 278;BA.debugLine="lblRojo.Left = imgRioLlanura.Left";
mostCurrent._lblrojo.setLeft(mostCurrent._imgriollanura.getLeft());
 //BA.debugLineNum = 279;BA.debugLine="lblRojo.Visible = True";
mostCurrent._lblrojo.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 280;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 6;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 8;BA.debugLine="Dim currentproject As String";
_currentproject = "";
 //BA.debugLineNum = 9;BA.debugLine="Dim fullidcurrentproject As String";
_fullidcurrentproject = "";
 //BA.debugLineNum = 10;BA.debugLine="Dim datecurrentproject As String";
_datecurrentproject = "";
 //BA.debugLineNum = 12;BA.debugLine="Dim tipoAmbiente As String";
_tipoambiente = "";
 //BA.debugLineNum = 13;BA.debugLine="Dim origen As String";
_origen = "";
 //BA.debugLineNum = 14;BA.debugLine="Dim Geopartido As String";
_geopartido = "";
 //BA.debugLineNum = 15;BA.debugLine="Dim lat As String";
_lat = "";
 //BA.debugLineNum = 16;BA.debugLine="Dim lng As String";
_lng = "";
 //BA.debugLineNum = 18;BA.debugLine="Dim foto1_path As String";
_foto1_path = "";
 //BA.debugLineNum = 20;BA.debugLine="Private TimerEnvio As Timer";
_timerenvio = new anywheresoftware.b4a.objects.Timer();
 //BA.debugLineNum = 22;BA.debugLine="Dim Up1 As UploadFilePhp";
_up1 = new com.spinter.uploadfilephp.UploadFilePhp();
 //BA.debugLineNum = 23;BA.debugLine="End Sub";
return "";
}
public static String  _tabcontainer_pageselected(int _position) throws Exception{
 //BA.debugLineNum = 186;BA.debugLine="Private Sub tabContainer_PageSelected (Position As";
 //BA.debugLineNum = 188;BA.debugLine="If Position = 2 Then";
if (_position==2) { 
 //BA.debugLineNum = 189;BA.debugLine="btnNext.Visible = True";
mostCurrent._btnnext.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 190;BA.debugLine="btnPrev.Visible = True";
mostCurrent._btnprev.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 191;BA.debugLine="lblCirculoPos4.Color = Colors.RGB(76,159,56)";
mostCurrent._lblcirculopos4.setColor(anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (76),(int) (159),(int) (56)));
 //BA.debugLineNum = 192;BA.debugLine="lblCirculoPos3.Color = Colors.RGB(139,226,63)";
mostCurrent._lblcirculopos3.setColor(anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (139),(int) (226),(int) (63)));
 //BA.debugLineNum = 193;BA.debugLine="lblCirculoPos2.Color = Colors.RGB(76,159,56)";
mostCurrent._lblcirculopos2.setColor(anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (76),(int) (159),(int) (56)));
 //BA.debugLineNum = 194;BA.debugLine="lblCirculoPos1.Color = Colors.RGB(76,159,56)";
mostCurrent._lblcirculopos1.setColor(anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (76),(int) (159),(int) (56)));
 }else if(_position==0) { 
 //BA.debugLineNum = 196;BA.debugLine="btnPrev.Visible = False";
mostCurrent._btnprev.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 197;BA.debugLine="btnNext.Visible = True";
mostCurrent._btnnext.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 198;BA.debugLine="lblCirculoPos1.Color = Colors.RGB(139,226,63)";
mostCurrent._lblcirculopos1.setColor(anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (139),(int) (226),(int) (63)));
 //BA.debugLineNum = 199;BA.debugLine="lblCirculoPos2.Color = Colors.RGB(76,159,56)";
mostCurrent._lblcirculopos2.setColor(anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (76),(int) (159),(int) (56)));
 //BA.debugLineNum = 200;BA.debugLine="lblCirculoPos3.Color = Colors.RGB(76,159,56)";
mostCurrent._lblcirculopos3.setColor(anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (76),(int) (159),(int) (56)));
 //BA.debugLineNum = 201;BA.debugLine="lblCirculoPos4.Color = Colors.RGB(76,159,56)";
mostCurrent._lblcirculopos4.setColor(anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (76),(int) (159),(int) (56)));
 }else if(_position==1) { 
 //BA.debugLineNum = 203;BA.debugLine="btnPrev.Visible = True";
mostCurrent._btnprev.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 204;BA.debugLine="btnNext.Visible = True";
mostCurrent._btnnext.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 205;BA.debugLine="lblCirculoPos2.Color = Colors.RGB(139,226,63)";
mostCurrent._lblcirculopos2.setColor(anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (139),(int) (226),(int) (63)));
 //BA.debugLineNum = 206;BA.debugLine="lblCirculoPos3.Color = Colors.RGB(76,159,56)";
mostCurrent._lblcirculopos3.setColor(anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (76),(int) (159),(int) (56)));
 //BA.debugLineNum = 207;BA.debugLine="lblCirculoPos1.Color = Colors.RGB(76,159,56)";
mostCurrent._lblcirculopos1.setColor(anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (76),(int) (159),(int) (56)));
 //BA.debugLineNum = 208;BA.debugLine="lblCirculoPos4.Color = Colors.RGB(76,159,56)";
mostCurrent._lblcirculopos4.setColor(anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (76),(int) (159),(int) (56)));
 }else if(_position==3) { 
 //BA.debugLineNum = 210;BA.debugLine="btnPrev.Visible = True";
mostCurrent._btnprev.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 211;BA.debugLine="btnNext.Visible = False";
mostCurrent._btnnext.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 212;BA.debugLine="lblCirculoPos4.Color = Colors.RGB(139,226,63)";
mostCurrent._lblcirculopos4.setColor(anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (139),(int) (226),(int) (63)));
 //BA.debugLineNum = 213;BA.debugLine="lblCirculoPos3.Color = Colors.RGB(76,159,56)";
mostCurrent._lblcirculopos3.setColor(anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (76),(int) (159),(int) (56)));
 //BA.debugLineNum = 214;BA.debugLine="lblCirculoPos1.Color = Colors.RGB(76,159,56)";
mostCurrent._lblcirculopos1.setColor(anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (76),(int) (159),(int) (56)));
 //BA.debugLineNum = 215;BA.debugLine="lblCirculoPos2.Color = Colors.RGB(76,159,56)";
mostCurrent._lblcirculopos2.setColor(anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (76),(int) (159),(int) (56)));
 };
 //BA.debugLineNum = 218;BA.debugLine="End Sub";
return "";
}
public static String  _timercarga_tick() throws Exception{
 //BA.debugLineNum = 488;BA.debugLine="Sub timerCarga_Tick";
 //BA.debugLineNum = 489;BA.debugLine="timeCarga = timeCarga + 1";
_timecarga = (int) (_timecarga+1);
 //BA.debugLineNum = 490;BA.debugLine="lblTiempoCarga.Text = \"Tiempo de relevamiento: \"";
mostCurrent._lbltiempocarga.setText(BA.ObjectToCharSequence("Tiempo de relevamiento: "+BA.NumberToString(_timecarga)+" segundos"));
 //BA.debugLineNum = 491;BA.debugLine="End Sub";
return "";
}
public static String  _timerenvio_tick() throws Exception{
anywheresoftware.b4a.objects.collections.Map _map1 = null;
 //BA.debugLineNum = 1129;BA.debugLine="Sub TimerEnvio_Tick";
 //BA.debugLineNum = 1130;BA.debugLine="foto1ProgressBar.Value = ProgressBar1.Progress";
mostCurrent._foto1progressbar._setvalue /*float*/ ((float) (mostCurrent._progressbar1.getProgress()));
 //BA.debugLineNum = 1131;BA.debugLine="If fotosEnviadas = totalFotos Then";
if (_fotosenviadas==_totalfotos) { 
 //BA.debugLineNum = 1132;BA.debugLine="Log(\"TODAS LAS FOTOS FUERON ENVIADAS CORRECTAMEN";
anywheresoftware.b4a.keywords.Common.LogImpl("049217539","TODAS LAS FOTOS FUERON ENVIADAS CORRECTAMENTE",0);
 //BA.debugLineNum = 1133;BA.debugLine="Up1.UploadKill";
_up1.UploadKill(processBA);
 //BA.debugLineNum = 1135;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 1136;BA.debugLine="ToastMessageShow(\"Evaluación enviada\", False)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Evaluación enviada"),anywheresoftware.b4a.keywords.Common.False);
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 1138;BA.debugLine="ToastMessageShow(\"Report sent\", False)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Report sent"),anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 1142;BA.debugLine="Dim Map1 As Map";
_map1 = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 1143;BA.debugLine="Map1.Initialize";
_map1.Initialize();
 //BA.debugLineNum = 1144;BA.debugLine="Map1.Put(\"Id\", Main.currentproject)";
_map1.Put((Object)("Id"),(Object)(mostCurrent._main._currentproject /*String*/ ));
 //BA.debugLineNum = 1145;BA.debugLine="DBUtils.UpdateRecord(Starter.modulo_residuos_sql";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._modulo_residuos_sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals_residuos","foto1sent",(Object)("si"),_map1);
 //BA.debugLineNum = 1147;BA.debugLine="frmFelicitaciones.tiporio = tiporio";
mostCurrent._frmfelicitaciones._tiporio /*String*/  = mostCurrent._tiporio;
 //BA.debugLineNum = 1148;BA.debugLine="StartActivity(frmFelicitaciones)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._frmfelicitaciones.getObject()));
 //BA.debugLineNum = 1150;BA.debugLine="TimerEnvio.Enabled = False";
_timerenvio.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1151;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 1152;BA.debugLine="Activity.finish";
mostCurrent._activity.Finish();
 };
 //BA.debugLineNum = 1154;BA.debugLine="End Sub";
return "";
}
public static String  _up1_sendfile(String _value) throws Exception{
 //BA.debugLineNum = 1161;BA.debugLine="Sub Up1_sendFile (value As String)";
 //BA.debugLineNum = 1162;BA.debugLine="Log(\"sendfile event:\" & value)";
anywheresoftware.b4a.keywords.Common.LogImpl("049348609","sendfile event:"+_value,0);
 //BA.debugLineNum = 1163;BA.debugLine="If value = \"success\" Then";
if ((_value).equals("success")) { 
 //BA.debugLineNum = 1165;BA.debugLine="If fotosEnviadas = 0 And totalFotos >= 1 Then";
if (_fotosenviadas==0 && _totalfotos>=1) { 
 //BA.debugLineNum = 1166;BA.debugLine="fotosEnviadas = 1";
_fotosenviadas = (int) (1);
 //BA.debugLineNum = 1167;BA.debugLine="Return";
if (true) return "";
 };
 }else if((_value).equals("Error!")) { 
 //BA.debugLineNum = 1171;BA.debugLine="Log(\"FOTO error\")";
anywheresoftware.b4a.keywords.Common.LogImpl("049348618","FOTO error",0);
 //BA.debugLineNum = 1172;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 1173;BA.debugLine="MsgboxAsync(\"Ha habido un error en el envío. Re";
anywheresoftware.b4a.keywords.Common.MsgboxAsync(BA.ObjectToCharSequence("Ha habido un error en el envío. Revisa tu conexión a Internet e intenta de nuevo desde 'Datos sin enviar'"),BA.ObjectToCharSequence("Oops!"),processBA);
 };
 //BA.debugLineNum = 1175;BA.debugLine="Up1.UploadKill";
_up1.UploadKill(processBA);
 //BA.debugLineNum = 1176;BA.debugLine="TimerEnvio.Enabled = False";
_timerenvio.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1177;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 1178;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 1179;BA.debugLine="Activity_Create(False)";
_activity_create(anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 1182;BA.debugLine="End Sub";
return "";
}
public static String  _up1_statusupload(String _value) throws Exception{
 //BA.debugLineNum = 1157;BA.debugLine="Sub Up1_statusUpload (value As String)";
 //BA.debugLineNum = 1159;BA.debugLine="End Sub";
return "";
}
public static String  _up1_statusvisible(boolean _onoff,String _value) throws Exception{
 //BA.debugLineNum = 1183;BA.debugLine="Sub Up1_statusVISIBLE (onoff As Boolean,value As S";
 //BA.debugLineNum = 1185;BA.debugLine="End Sub";
return "";
}
}
