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

public class frmmunicipioestadisticas extends Activity implements B4AActivity{
	public static frmmunicipioestadisticas mostCurrent;
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
			processBA = new BA(this.getApplicationContext(), null, null, "appear.pnud.preservamos", "appear.pnud.preservamos.frmmunicipioestadisticas");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
            
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (frmmunicipioestadisticas).");
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
		activityBA = new BA(this, layout, processBA, "appear.pnud.preservamos", "appear.pnud.preservamos.frmmunicipioestadisticas");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "appear.pnud.preservamos.frmmunicipioestadisticas", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density, mostCurrent);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (frmmunicipioestadisticas) Create " + (isFirst ? "(first time)" : "") + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (frmmunicipioestadisticas) Resume **");
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
		return frmmunicipioestadisticas.class;
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
            BA.LogInfo("** Activity (frmmunicipioestadisticas) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
        else
            BA.LogInfo("** Activity (frmmunicipioestadisticas) Pause event (activity is not paused). **");
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
            frmmunicipioestadisticas mc = mostCurrent;
			if (mc == null || mc != activity.get())
				return;
			processBA.setActivityPaused(false);
            BA.LogInfo("** Activity (frmmunicipioestadisticas) Resume **");
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
public static double _valorestado = 0;
public static double _hidropromedio = 0;
public static double _aguapromedio = 0;
public static double _biopromedio = 0;
public static double _usospromedio = 0;
public static double _exoticaspromedio = 0;
public static anywheresoftware.b4a.objects.collections.List _listahidro_promedio = null;
public static anywheresoftware.b4a.objects.collections.List _listaagua_promedio = null;
public static anywheresoftware.b4a.objects.collections.List _listabio_promedio = null;
public static anywheresoftware.b4a.objects.collections.List _listausos_promedio = null;
public static anywheresoftware.b4a.objects.collections.List _listaexoticas_promedio = null;
public anywheresoftware.b4a.objects.B4XViewWrapper.XUI _xui = null;
public appear.pnud.preservamos.gauge _gauge_hidro = null;
public appear.pnud.preservamos.gauge _gauge_agua = null;
public appear.pnud.preservamos.gauge _gauge_bio = null;
public appear.pnud.preservamos.gauge _gauge_usos = null;
public appear.pnud.preservamos.gauge _gauge_exoticas = null;
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
public static String  _activity_create(boolean _firsttime) throws Exception{
int _k = 0;
int _l = 0;
int _m = 0;
int _n = 0;
int _o = 0;
 //BA.debugLineNum = 34;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
 //BA.debugLineNum = 35;BA.debugLine="Activity.LoadLayout(\"layMainMunicipio_estadistica";
mostCurrent._activity.LoadLayout("layMainMunicipio_estadisticas",mostCurrent.activityBA);
 //BA.debugLineNum = 36;BA.debugLine="ProgressDialogShow(\"Cargando detalle del municipi";
anywheresoftware.b4a.keywords.Common.ProgressDialogShow(mostCurrent.activityBA,BA.ObjectToCharSequence("Cargando detalle del municipio, por favor aguarde..."));
 //BA.debugLineNum = 37;BA.debugLine="ToastMessageShow(\"Cargando detalle del municipio,";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Cargando detalle del municipio, por favor aguarde..."),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 38;BA.debugLine="valorEstado = 0";
_valorestado = 0;
 //BA.debugLineNum = 39;BA.debugLine="hidroPromedio = 0";
_hidropromedio = 0;
 //BA.debugLineNum = 40;BA.debugLine="aguaPromedio = 0";
_aguapromedio = 0;
 //BA.debugLineNum = 41;BA.debugLine="bioPromedio = 0";
_biopromedio = 0;
 //BA.debugLineNum = 42;BA.debugLine="UsosPromedio = 0";
_usospromedio = 0;
 //BA.debugLineNum = 43;BA.debugLine="ExoticasPromedio = 0";
_exoticaspromedio = 0;
 //BA.debugLineNum = 46;BA.debugLine="Try";
try { //BA.debugLineNum = 47;BA.debugLine="For k = 0 To listaHidro_Promedio.Size - 1";
{
final int step11 = 1;
final int limit11 = (int) (_listahidro_promedio.getSize()-1);
_k = (int) (0) ;
for (;_k <= limit11 ;_k = _k + step11 ) {
 //BA.debugLineNum = 48;BA.debugLine="If IsNumber(listaHidro_Promedio.Get(k)) Then";
if (anywheresoftware.b4a.keywords.Common.IsNumber(BA.ObjectToString(_listahidro_promedio.Get(_k)))) { 
 //BA.debugLineNum = 49;BA.debugLine="hidroPromedio = hidroPromedio + listaHidro_Pro";
_hidropromedio = _hidropromedio+(double)(BA.ObjectToNumber(_listahidro_promedio.Get(_k)));
 };
 }
};
 //BA.debugLineNum = 52;BA.debugLine="hidroPromedio = Round2(hidroPromedio / listaHidr";
_hidropromedio = anywheresoftware.b4a.keywords.Common.Round2(_hidropromedio/(double)_listahidro_promedio.getSize(),(int) (2));
 //BA.debugLineNum = 53;BA.debugLine="Log(\"hidroPromedio: \" & hidroPromedio)";
anywheresoftware.b4a.keywords.Common.LogImpl("035782675","hidroPromedio: "+BA.NumberToString(_hidropromedio),0);
 //BA.debugLineNum = 55;BA.debugLine="For l = 0 To listaAgua_Promedio.Size - 1";
{
final int step18 = 1;
final int limit18 = (int) (_listaagua_promedio.getSize()-1);
_l = (int) (0) ;
for (;_l <= limit18 ;_l = _l + step18 ) {
 //BA.debugLineNum = 56;BA.debugLine="If IsNumber(listaAgua_Promedio.Get(l)) Then";
if (anywheresoftware.b4a.keywords.Common.IsNumber(BA.ObjectToString(_listaagua_promedio.Get(_l)))) { 
 //BA.debugLineNum = 57;BA.debugLine="aguaPromedio = aguaPromedio + listaAgua_Promed";
_aguapromedio = _aguapromedio+(double)(BA.ObjectToNumber(_listaagua_promedio.Get(_l)));
 };
 }
};
 //BA.debugLineNum = 60;BA.debugLine="aguaPromedio = Round2(aguaPromedio / listaAgua_P";
_aguapromedio = anywheresoftware.b4a.keywords.Common.Round2(_aguapromedio/(double)_listaagua_promedio.getSize(),(int) (2));
 //BA.debugLineNum = 61;BA.debugLine="Log(\"aguaPromedio: \" & aguaPromedio)";
anywheresoftware.b4a.keywords.Common.LogImpl("035782683","aguaPromedio: "+BA.NumberToString(_aguapromedio),0);
 //BA.debugLineNum = 63;BA.debugLine="For m = 0 To listaBio_Promedio.Size - 1";
{
final int step25 = 1;
final int limit25 = (int) (_listabio_promedio.getSize()-1);
_m = (int) (0) ;
for (;_m <= limit25 ;_m = _m + step25 ) {
 //BA.debugLineNum = 64;BA.debugLine="If IsNumber(listaBio_Promedio.Get(m)) Then";
if (anywheresoftware.b4a.keywords.Common.IsNumber(BA.ObjectToString(_listabio_promedio.Get(_m)))) { 
 //BA.debugLineNum = 65;BA.debugLine="bioPromedio = bioPromedio + listaBio_Promedio.";
_biopromedio = _biopromedio+(double)(BA.ObjectToNumber(_listabio_promedio.Get(_m)));
 };
 }
};
 //BA.debugLineNum = 68;BA.debugLine="bioPromedio = Round2(bioPromedio / listaBio_Prom";
_biopromedio = anywheresoftware.b4a.keywords.Common.Round2(_biopromedio/(double)_listabio_promedio.getSize(),(int) (2));
 //BA.debugLineNum = 69;BA.debugLine="Log(\"bioPromedio: \" & bioPromedio)";
anywheresoftware.b4a.keywords.Common.LogImpl("035782691","bioPromedio: "+BA.NumberToString(_biopromedio),0);
 //BA.debugLineNum = 71;BA.debugLine="For n = 0 To listaUsos_Promedio.Size - 1";
{
final int step32 = 1;
final int limit32 = (int) (_listausos_promedio.getSize()-1);
_n = (int) (0) ;
for (;_n <= limit32 ;_n = _n + step32 ) {
 //BA.debugLineNum = 72;BA.debugLine="If IsNumber(listaUsos_Promedio.Get(n)) Then";
if (anywheresoftware.b4a.keywords.Common.IsNumber(BA.ObjectToString(_listausos_promedio.Get(_n)))) { 
 //BA.debugLineNum = 73;BA.debugLine="UsosPromedio = UsosPromedio + listaUsos_Promed";
_usospromedio = _usospromedio+(double)(BA.ObjectToNumber(_listausos_promedio.Get(_n)));
 };
 }
};
 //BA.debugLineNum = 76;BA.debugLine="UsosPromedio = Round2(UsosPromedio / listaUsos_P";
_usospromedio = anywheresoftware.b4a.keywords.Common.Round2(_usospromedio/(double)_listausos_promedio.getSize(),(int) (2));
 //BA.debugLineNum = 77;BA.debugLine="Log(\"UsosPromedio: \" & UsosPromedio)";
anywheresoftware.b4a.keywords.Common.LogImpl("035782699","UsosPromedio: "+BA.NumberToString(_usospromedio),0);
 //BA.debugLineNum = 79;BA.debugLine="For o = 0 To listaExoticas_Promedio.Size - 1";
{
final int step39 = 1;
final int limit39 = (int) (_listaexoticas_promedio.getSize()-1);
_o = (int) (0) ;
for (;_o <= limit39 ;_o = _o + step39 ) {
 //BA.debugLineNum = 80;BA.debugLine="If IsNumber(listaExoticas_Promedio.Get(o)) Then";
if (anywheresoftware.b4a.keywords.Common.IsNumber(BA.ObjectToString(_listaexoticas_promedio.Get(_o)))) { 
 //BA.debugLineNum = 81;BA.debugLine="ExoticasPromedio = ExoticasPromedio + listaExo";
_exoticaspromedio = _exoticaspromedio+(double)(BA.ObjectToNumber(_listaexoticas_promedio.Get(_o)));
 };
 }
};
 //BA.debugLineNum = 84;BA.debugLine="ExoticasPromedio = Round2(ExoticasPromedio / lis";
_exoticaspromedio = anywheresoftware.b4a.keywords.Common.Round2(_exoticaspromedio/(double)_listaexoticas_promedio.getSize(),(int) (2));
 //BA.debugLineNum = 85;BA.debugLine="Log(\"ExoticasPromedio: \" & ExoticasPromedio)";
anywheresoftware.b4a.keywords.Common.LogImpl("035782707","ExoticasPromedio: "+BA.NumberToString(_exoticaspromedio),0);
 //BA.debugLineNum = 88;BA.debugLine="gauge_Hidro.SetRanges(Array(gauge_Hidro.CreateRa";
mostCurrent._gauge_hidro._setranges /*String*/ (anywheresoftware.b4a.keywords.Common.ArrayToList(new Object[]{(Object)(mostCurrent._gauge_hidro._createrange /*appear.pnud.preservamos.gauge._gaugerange*/ ((float) (0),(float) (20),mostCurrent._xui.Color_DarkGray)),(Object)(mostCurrent._gauge_hidro._createrange /*appear.pnud.preservamos.gauge._gaugerange*/ ((float) (20),(float) (40),mostCurrent._xui.Color_Red)),(Object)(mostCurrent._gauge_hidro._createrange /*appear.pnud.preservamos.gauge._gaugerange*/ ((float) (40),(float) (60),mostCurrent._xui.Color_Yellow)),(Object)(mostCurrent._gauge_hidro._createrange /*appear.pnud.preservamos.gauge._gaugerange*/ ((float) (60),(float) (80),mostCurrent._xui.Color_Green)),(Object)(mostCurrent._gauge_hidro._createrange /*appear.pnud.preservamos.gauge._gaugerange*/ ((float) (80),(float) (100),mostCurrent._xui.Color_Blue))}));
 //BA.debugLineNum = 89;BA.debugLine="gauge_Hidro.CurrentValue = 0";
mostCurrent._gauge_hidro._setcurrentvalue /*float*/ ((float) (0));
 //BA.debugLineNum = 90;BA.debugLine="gauge_Hidro.CurrentValue = hidroPromedio * 10";
mostCurrent._gauge_hidro._setcurrentvalue /*float*/ ((float) (_hidropromedio*10));
 //BA.debugLineNum = 92;BA.debugLine="gauge_Agua.SetRanges(Array(gauge_Agua.CreateRang";
mostCurrent._gauge_agua._setranges /*String*/ (anywheresoftware.b4a.keywords.Common.ArrayToList(new Object[]{(Object)(mostCurrent._gauge_agua._createrange /*appear.pnud.preservamos.gauge._gaugerange*/ ((float) (0),(float) (20),mostCurrent._xui.Color_DarkGray)),(Object)(mostCurrent._gauge_agua._createrange /*appear.pnud.preservamos.gauge._gaugerange*/ ((float) (20),(float) (40),mostCurrent._xui.Color_Red)),(Object)(mostCurrent._gauge_agua._createrange /*appear.pnud.preservamos.gauge._gaugerange*/ ((float) (40),(float) (60),mostCurrent._xui.Color_Yellow)),(Object)(mostCurrent._gauge_agua._createrange /*appear.pnud.preservamos.gauge._gaugerange*/ ((float) (60),(float) (80),mostCurrent._xui.Color_Green)),(Object)(mostCurrent._gauge_agua._createrange /*appear.pnud.preservamos.gauge._gaugerange*/ ((float) (80),(float) (100),mostCurrent._xui.Color_Blue))}));
 //BA.debugLineNum = 93;BA.debugLine="gauge_Agua.CurrentValue = 0";
mostCurrent._gauge_agua._setcurrentvalue /*float*/ ((float) (0));
 //BA.debugLineNum = 94;BA.debugLine="gauge_Agua.CurrentValue = aguaPromedio * 10";
mostCurrent._gauge_agua._setcurrentvalue /*float*/ ((float) (_aguapromedio*10));
 //BA.debugLineNum = 96;BA.debugLine="gauge_Bio.SetRanges(Array(gauge_Bio.CreateRange(";
mostCurrent._gauge_bio._setranges /*String*/ (anywheresoftware.b4a.keywords.Common.ArrayToList(new Object[]{(Object)(mostCurrent._gauge_bio._createrange /*appear.pnud.preservamos.gauge._gaugerange*/ ((float) (0),(float) (20),mostCurrent._xui.Color_DarkGray)),(Object)(mostCurrent._gauge_bio._createrange /*appear.pnud.preservamos.gauge._gaugerange*/ ((float) (20),(float) (40),mostCurrent._xui.Color_Red)),(Object)(mostCurrent._gauge_bio._createrange /*appear.pnud.preservamos.gauge._gaugerange*/ ((float) (40),(float) (60),mostCurrent._xui.Color_Yellow)),(Object)(mostCurrent._gauge_bio._createrange /*appear.pnud.preservamos.gauge._gaugerange*/ ((float) (60),(float) (80),mostCurrent._xui.Color_Green)),(Object)(mostCurrent._gauge_bio._createrange /*appear.pnud.preservamos.gauge._gaugerange*/ ((float) (80),(float) (100),mostCurrent._xui.Color_Blue))}));
 //BA.debugLineNum = 97;BA.debugLine="gauge_Bio.CurrentValue = 0";
mostCurrent._gauge_bio._setcurrentvalue /*float*/ ((float) (0));
 //BA.debugLineNum = 98;BA.debugLine="gauge_Bio.CurrentValue = bioPromedio * 10";
mostCurrent._gauge_bio._setcurrentvalue /*float*/ ((float) (_biopromedio*10));
 //BA.debugLineNum = 100;BA.debugLine="gauge_Usos.SetRanges(Array(gauge_Usos.CreateRang";
mostCurrent._gauge_usos._setranges /*String*/ (anywheresoftware.b4a.keywords.Common.ArrayToList(new Object[]{(Object)(mostCurrent._gauge_usos._createrange /*appear.pnud.preservamos.gauge._gaugerange*/ ((float) (0),(float) (20),mostCurrent._xui.Color_DarkGray)),(Object)(mostCurrent._gauge_usos._createrange /*appear.pnud.preservamos.gauge._gaugerange*/ ((float) (20),(float) (40),mostCurrent._xui.Color_Red)),(Object)(mostCurrent._gauge_usos._createrange /*appear.pnud.preservamos.gauge._gaugerange*/ ((float) (40),(float) (60),mostCurrent._xui.Color_Yellow)),(Object)(mostCurrent._gauge_usos._createrange /*appear.pnud.preservamos.gauge._gaugerange*/ ((float) (60),(float) (80),mostCurrent._xui.Color_Green)),(Object)(mostCurrent._gauge_usos._createrange /*appear.pnud.preservamos.gauge._gaugerange*/ ((float) (80),(float) (100),mostCurrent._xui.Color_Blue))}));
 //BA.debugLineNum = 101;BA.debugLine="gauge_Usos.CurrentValue = 0";
mostCurrent._gauge_usos._setcurrentvalue /*float*/ ((float) (0));
 //BA.debugLineNum = 102;BA.debugLine="gauge_Usos.CurrentValue = UsosPromedio * 10";
mostCurrent._gauge_usos._setcurrentvalue /*float*/ ((float) (_usospromedio*10));
 //BA.debugLineNum = 104;BA.debugLine="gauge_Exoticas.SetRanges(Array(gauge_Exoticas.Cr";
mostCurrent._gauge_exoticas._setranges /*String*/ (anywheresoftware.b4a.keywords.Common.ArrayToList(new Object[]{(Object)(mostCurrent._gauge_exoticas._createrange /*appear.pnud.preservamos.gauge._gaugerange*/ ((float) (0),(float) (20),mostCurrent._xui.Color_DarkGray)),(Object)(mostCurrent._gauge_exoticas._createrange /*appear.pnud.preservamos.gauge._gaugerange*/ ((float) (20),(float) (40),mostCurrent._xui.Color_Red)),(Object)(mostCurrent._gauge_exoticas._createrange /*appear.pnud.preservamos.gauge._gaugerange*/ ((float) (40),(float) (60),mostCurrent._xui.Color_Yellow)),(Object)(mostCurrent._gauge_exoticas._createrange /*appear.pnud.preservamos.gauge._gaugerange*/ ((float) (60),(float) (80),mostCurrent._xui.Color_Green)),(Object)(mostCurrent._gauge_exoticas._createrange /*appear.pnud.preservamos.gauge._gaugerange*/ ((float) (80),(float) (100),mostCurrent._xui.Color_Blue))}));
 //BA.debugLineNum = 105;BA.debugLine="gauge_Exoticas.CurrentValue = 0";
mostCurrent._gauge_exoticas._setcurrentvalue /*float*/ ((float) (0));
 //BA.debugLineNum = 106;BA.debugLine="gauge_Exoticas.CurrentValue = ExoticasPromedio *";
mostCurrent._gauge_exoticas._setcurrentvalue /*float*/ ((float) (_exoticaspromedio*10));
 } 
       catch (Exception e62) {
			processBA.setLastException(e62); //BA.debugLineNum = 108;BA.debugLine="ToastMessageShow(\"No se pudieron cargar las esta";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("No se pudieron cargar las estadísticas detalladas de este municipio :("),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 109;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("035782731",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA)),0);
 //BA.debugLineNum = 110;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 111;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 };
 //BA.debugLineNum = 114;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 //BA.debugLineNum = 117;BA.debugLine="End Sub";
return "";
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 123;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 125;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 119;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 121;BA.debugLine="End Sub";
return "";
}
public static String  _btncerrarstats_click() throws Exception{
 //BA.debugLineNum = 128;BA.debugLine="Private Sub btnCerrarStats_Click";
 //BA.debugLineNum = 129;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 130;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 131;BA.debugLine="End Sub";
return "";
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 22;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 25;BA.debugLine="Private xui As XUI";
mostCurrent._xui = new anywheresoftware.b4a.objects.B4XViewWrapper.XUI();
 //BA.debugLineNum = 26;BA.debugLine="Private gauge_Hidro As Gauge";
mostCurrent._gauge_hidro = new appear.pnud.preservamos.gauge();
 //BA.debugLineNum = 27;BA.debugLine="Private gauge_Agua As Gauge";
mostCurrent._gauge_agua = new appear.pnud.preservamos.gauge();
 //BA.debugLineNum = 28;BA.debugLine="Private gauge_Bio As Gauge";
mostCurrent._gauge_bio = new appear.pnud.preservamos.gauge();
 //BA.debugLineNum = 29;BA.debugLine="Private gauge_Usos As Gauge";
mostCurrent._gauge_usos = new appear.pnud.preservamos.gauge();
 //BA.debugLineNum = 30;BA.debugLine="Private gauge_Exoticas As Gauge";
mostCurrent._gauge_exoticas = new appear.pnud.preservamos.gauge();
 //BA.debugLineNum = 32;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 6;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 9;BA.debugLine="Dim valorEstado As Double";
_valorestado = 0;
 //BA.debugLineNum = 10;BA.debugLine="Dim hidroPromedio As Double";
_hidropromedio = 0;
 //BA.debugLineNum = 11;BA.debugLine="Dim aguaPromedio As Double";
_aguapromedio = 0;
 //BA.debugLineNum = 12;BA.debugLine="Dim bioPromedio As Double";
_biopromedio = 0;
 //BA.debugLineNum = 13;BA.debugLine="Dim UsosPromedio As Double";
_usospromedio = 0;
 //BA.debugLineNum = 14;BA.debugLine="Dim ExoticasPromedio As Double";
_exoticaspromedio = 0;
 //BA.debugLineNum = 15;BA.debugLine="Dim listaHidro_Promedio As List";
_listahidro_promedio = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 16;BA.debugLine="Dim listaAgua_Promedio As List";
_listaagua_promedio = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 17;BA.debugLine="Dim listaBio_Promedio As List";
_listabio_promedio = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 18;BA.debugLine="Dim listaUsos_Promedio As List";
_listausos_promedio = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 19;BA.debugLine="Dim listaExoticas_Promedio As List";
_listaexoticas_promedio = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 20;BA.debugLine="End Sub";
return "";
}
}
