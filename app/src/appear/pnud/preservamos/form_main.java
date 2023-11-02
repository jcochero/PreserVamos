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

public class form_main extends Activity implements B4AActivity{
	public static form_main mostCurrent;
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
			processBA = new BA(this.getApplicationContext(), null, null, "appear.pnud.preservamos", "appear.pnud.preservamos.form_main");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
            
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (form_main).");
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
		activityBA = new BA(this, layout, processBA, "appear.pnud.preservamos", "appear.pnud.preservamos.form_main");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "appear.pnud.preservamos.form_main", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density, mostCurrent);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (form_main) Create " + (isFirst ? "(first time)" : "") + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (form_main) Resume **");
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
		return form_main.class;
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
            BA.LogInfo("** Activity (form_main) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
        else
            BA.LogInfo("** Activity (form_main) Pause event (activity is not paused). **");
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
            form_main mc = mostCurrent;
			if (mc == null || mc != activity.get())
				return;
			processBA.setActivityPaused(false);
            BA.LogInfo("** Activity (form_main) Resume **");
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
public static boolean _isguest = false;
public static String _fullidcurrentproject = "";
public static String _geopartido = "";
public anywheresoftware.b4a.objects.RuntimePermissions _rp = null;
public static boolean _formmainloaded = false;
public anywheresoftware.b4a.phone.Phone _p = null;
public anywheresoftware.b4a.phone.Phone.PhoneIntents _pi = null;
public anywheresoftware.b4a.objects.ProgressBarWrapper _pgbnivel = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbllevel = null;
public anywheresoftware.b4a.objects.TabStripViewPager _tabstripmain = null;
public anywheresoftware.b4a.objects.LabelWrapper _icomapa = null;
public anywheresoftware.b4a.objects.LabelWrapper _icoinformacion = null;
public anywheresoftware.b4a.objects.LabelWrapper _icoanalizar = null;
public anywheresoftware.b4a.objects.LabelWrapper _icoperfil = null;
public anywheresoftware.b4a.objects.LabelWrapper _icoalerta = null;
public anywheresoftware.b4a.objects.LabelWrapper _btnmapa = null;
public anywheresoftware.b4a.objects.LabelWrapper _btninformacion = null;
public anywheresoftware.b4a.objects.LabelWrapper _btncirculo = null;
public anywheresoftware.b4a.objects.LabelWrapper _btnanalizar = null;
public anywheresoftware.b4a.objects.LabelWrapper _btnperfil = null;
public anywheresoftware.b4a.objects.LabelWrapper _btnalerta = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblanalizar = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnreportar = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblcomofuncionaanalizar = null;
public anywheresoftware.b4a.objects.PanelWrapper _pnldescripcionanalisis = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbltituloanalisis = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblpremioanalisis = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbldescripcionanalisis = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblpuntosanalisis = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btncomenzaranalisis = null;
public static String _tipoanalisis = "";
public anywheresoftware.b4a.objects.PanelWrapper _pnlhabitat = null;
public anywheresoftware.b4a.objects.PanelWrapper _pnlresiduos = null;
public anywheresoftware.b4a.objects.PanelWrapper _pnlhidro = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnabrirmapa = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btndetectar = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbllat = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbllon = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnzoomall = null;
public anywheresoftware.b4a.objects.collections.List _markerexport = null;
public anywheresoftware.b4a.objects.LabelWrapper _markerinfo = null;
public anywheresoftware.b4a.objects.PanelWrapper _pnlmapa = null;
public anywheresoftware.b4a.objects.LabelWrapper _fondoblanco = null;
public anywheresoftware.b4a.objects.LabelWrapper _detectandolabel = null;
public static String _firstuse = "";
public anywheresoftware.b4a.objects.PanelWrapper _fondogris = null;
public anywheresoftware.b4a.objects.PanelWrapper _panelcomofunciona = null;
public static int _tutorialetapa = 0;
public anywheresoftware.b4a.objects.LabelWrapper _btnmenu_main = null;
public appear.pnud.preservamos.b4xdrawer _drawer = null;
public anywheresoftware.b4a.objects.LabelWrapper _btncerrarsesion = null;
public anywheresoftware.b4a.objects.LabelWrapper _btnedituser = null;
public anywheresoftware.b4a.objects.LabelWrapper _btnvermedallas = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblusername = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblregistrate = null;
public anywheresoftware.b4a.objects.LabelWrapper _btnverperfil = null;
public anywheresoftware.b4a.objects.LabelWrapper _btnabout = null;
public anywheresoftware.b4a.objects.LabelWrapper _btnpoliticadatos = null;
public anywheresoftware.b4a.objects.LabelWrapper _btndatosanteriores = null;
public anywheresoftware.b4a.objects.LabelWrapper _btnmuestreos = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbltitlemunicipio = null;
public anywheresoftware.b4a.objects.WebViewWrapper _webview1 = null;
public b4a.example3.customlistview _lblmunicipio_texto_principal = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblmunicipio_puntos_totales = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _btnmasinfomunicipio = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imglogomunicipio = null;
public appear.pnud.preservamos.gauge _gauge_partido = null;
public anywheresoftware.b4a.objects.B4XViewWrapper.XUI _xui = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblmunicipio_estado = null;
public static int _municipios_numresults = 0;
public static String _municipios_parser_string = "";
public static String _municipiourl = "";
public static String _municipiofb = "";
public static String _municipioig = "";
public static String _municipioyt = "";
public static String _municipiotw = "";
public anywheresoftware.b4a.objects.ImageViewWrapper _btnmunicipiotw = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _btnmunicipiofb = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _btnmunicipioyt = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _btnmunicipioig = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnverdatosmunicipio = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnverestadisticasmunicipio = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btncambiarmunicipio = null;
public static double _valorestado = 0;
public static double _hidropromedio = 0;
public static double _aguapromedio = 0;
public static double _biopromedio = 0;
public static double _usospromedio = 0;
public static double _exoticaspromedio = 0;
public anywheresoftware.b4a.objects.collections.List _listapromedio = null;
public anywheresoftware.b4a.objects.collections.List _listahidro_promedio = null;
public anywheresoftware.b4a.objects.collections.List _listaagua_promedio = null;
public anywheresoftware.b4a.objects.collections.List _listabio_promedio = null;
public anywheresoftware.b4a.objects.collections.List _listausos_promedio = null;
public anywheresoftware.b4a.objects.collections.List _listaexoticas_promedio = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblcomofuncionacontenido = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblcomofuncionatitle = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblpuntos_explicacion_1 = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblpuntos_explicacion_2 = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblpuntos_explicacion_3 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnactualizar = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbltitleprofile = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblperfil_puntos_totales = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblpuntos_titulo_1 = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblpuntos_titulo_2 = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblpuntos_titulo_3 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _imghabitat = null;
public anywheresoftware.b4a.objects.ButtonWrapper _imgbasura = null;
public anywheresoftware.b4a.objects.ButtonWrapper _imghidro = null;
public anywheresoftware.b4a.objects.ProgressBarWrapper _pgblevel = null;
public anywheresoftware.b4a.objects.ListViewWrapper _lstachievements = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblnivel = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnshare = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnshareothers = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbltitulo = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imgbadge = null;
public static String _nivel = "";
public static String _imagetoshare = "";
public anywheresoftware.b4a.objects.PanelWrapper _fondogris_share = null;
public anywheresoftware.b4a.objects.PanelWrapper _pnlshare = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imgbadgelevel = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblmunicipios_reportes = null;
public anywheresoftware.b4a.objects.ScrollViewWrapper _scrreportar_modulos = null;
public b4a.example.dateutils _dateutils = null;
public appear.pnud.preservamos.main _main = null;
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

public static void initializeProcessGlobals() {
             try {
                Class.forName(BA.applicationContext.getPackageName() + ".main").getMethod("initializeProcessGlobals").invoke(null, null);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
}
public static String  _activity_create(boolean _firsttime) throws Exception{
String _rndstr = "";
 //BA.debugLineNum = 174;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
 //BA.debugLineNum = 183;BA.debugLine="p.SetScreenOrientation(1)";
mostCurrent._p.SetScreenOrientation(processBA,(int) (1));
 //BA.debugLineNum = 186;BA.debugLine="If Main.username = \"guest\" Or Main.username = \"No";
if ((mostCurrent._main._username /*String*/ ).equals("guest") || (mostCurrent._main._username /*String*/ ).equals("None")) { 
 //BA.debugLineNum = 188;BA.debugLine="If Main.hayinternet = True Then";
if (mostCurrent._main._hayinternet /*boolean*/ ==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 190;BA.debugLine="StartActivity(register)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._register.getObject()));
 }else {
 //BA.debugLineNum = 193;BA.debugLine="Dim RndStr As String";
_rndstr = "";
 //BA.debugLineNum = 194;BA.debugLine="RndStr = utilidades.RandomString(6)";
_rndstr = mostCurrent._utilidades._randomstring /*String*/ (mostCurrent.activityBA,(int) (6));
 //BA.debugLineNum = 195;BA.debugLine="Main.username = \"guest_\" & RndStr";
mostCurrent._main._username /*String*/  = "guest_"+_rndstr;
 };
 };
 //BA.debugLineNum = 199;BA.debugLine="If File.Exists(rp.GetSafeDirDefaultExternal(\"\"),";
if (anywheresoftware.b4a.keywords.Common.File.Exists(mostCurrent._rp.GetSafeDirDefaultExternal(""),"PreserVamos")==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 200;BA.debugLine="File.MakeDir(rp.GetSafeDirDefaultExternal(\"\"), \"";
anywheresoftware.b4a.keywords.Common.File.MakeDir(mostCurrent._rp.GetSafeDirDefaultExternal(""),"PreserVamos");
 //BA.debugLineNum = 201;BA.debugLine="Log(\"Folder exists\")";
anywheresoftware.b4a.keywords.Common.LogImpl("4458779","Folder exists",0);
 };
 //BA.debugLineNum = 203;BA.debugLine="Starter.savedir = rp.GetSafeDirDefaultExternal(\"P";
mostCurrent._starter._savedir /*String*/  = mostCurrent._rp.GetSafeDirDefaultExternal("PreserVamos");
 //BA.debugLineNum = 206;BA.debugLine="LoadForm_Main";
_loadform_main();
 //BA.debugLineNum = 209;BA.debugLine="End Sub";
return "";
}
public static boolean  _activity_keypress(int _keycode) throws Exception{
 //BA.debugLineNum = 227;BA.debugLine="Sub Activity_KeyPress (KeyCode As Int) As Boolean";
 //BA.debugLineNum = 228;BA.debugLine="If KeyCode = KeyCodes.KEYCODE_BACK Then";
if (_keycode==anywheresoftware.b4a.keywords.Common.KeyCodes.KEYCODE_BACK) { 
 //BA.debugLineNum = 229;BA.debugLine="If Drawer.LeftOpen Then";
if (mostCurrent._drawer._getleftopen /*boolean*/ ()) { 
 //BA.debugLineNum = 230;BA.debugLine="Drawer.LeftOpen = False";
mostCurrent._drawer._setleftopen /*boolean*/ (anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 231;BA.debugLine="Return True";
if (true) return anywheresoftware.b4a.keywords.Common.True;
 }else {
 //BA.debugLineNum = 233;BA.debugLine="closeAppMsgBox";
_closeappmsgbox();
 //BA.debugLineNum = 234;BA.debugLine="Return True";
if (true) return anywheresoftware.b4a.keywords.Common.True;
 };
 }else {
 //BA.debugLineNum = 238;BA.debugLine="Return False";
if (true) return anywheresoftware.b4a.keywords.Common.False;
 };
 //BA.debugLineNum = 242;BA.debugLine="End Sub";
return false;
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 224;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 226;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 210;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 212;BA.debugLine="If Main.username = \"guest\" Or Main.username = \"No";
if ((mostCurrent._main._username /*String*/ ).equals("guest") || (mostCurrent._main._username /*String*/ ).equals("None")) { 
 }else {
 //BA.debugLineNum = 215;BA.debugLine="lblUserName.Text = Main.username";
mostCurrent._lblusername.setText(BA.ObjectToCharSequence(mostCurrent._main._username /*String*/ ));
 //BA.debugLineNum = 216;BA.debugLine="lblUserName.Visible = True";
mostCurrent._lblusername.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 217;BA.debugLine="btnCerrarSesion.Text = \"Cerrar sesión\"";
mostCurrent._btncerrarsesion.setText(BA.ObjectToCharSequence("Cerrar sesión"));
 //BA.debugLineNum = 218;BA.debugLine="lblRegistrate.Visible = False";
mostCurrent._lblregistrate.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 219;BA.debugLine="btnEditUser.Visible = True";
mostCurrent._btnedituser.setVisible(anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 223;BA.debugLine="End Sub";
return "";
}
public static String  _btnabout_click() throws Exception{
 //BA.debugLineNum = 322;BA.debugLine="Sub btnAbout_Click";
 //BA.debugLineNum = 323;BA.debugLine="StartActivity(frmAbout)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._frmabout.getObject()));
 //BA.debugLineNum = 324;BA.debugLine="End Sub";
return "";
}
public static String  _btnabririnat_click() throws Exception{
 //BA.debugLineNum = 1028;BA.debugLine="Private Sub btnAbrirINat_Click";
 //BA.debugLineNum = 1029;BA.debugLine="StartActivity(iNatCheck)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._inatcheck.getObject()));
 //BA.debugLineNum = 1030;BA.debugLine="End Sub";
return "";
}
public static String  _btnabrirmapa_click() throws Exception{
 //BA.debugLineNum = 1021;BA.debugLine="Private Sub btnAbrirMapa_Click";
 //BA.debugLineNum = 1022;BA.debugLine="frmMapa.origen=\"main\"";
mostCurrent._frmmapa._origen /*String*/  = "main";
 //BA.debugLineNum = 1023;BA.debugLine="StartActivity(frmMapa)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._frmmapa.getObject()));
 //BA.debugLineNum = 1024;BA.debugLine="End Sub";
return "";
}
public static String  _btnactualizar_click() throws Exception{
 //BA.debugLineNum = 1760;BA.debugLine="Private Sub btnActualizar_Click";
 //BA.debugLineNum = 1761;BA.debugLine="ToastMessageShow(\"Actualizando puntaje...\", False";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Actualizando puntaje..."),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1762;BA.debugLine="getPuntaje";
_getpuntaje();
 //BA.debugLineNum = 1764;BA.debugLine="End Sub";
return "";
}
public static String  _btnanalizar_click() throws Exception{
 //BA.debugLineNum = 454;BA.debugLine="Private Sub btnAnalizar_Click";
 //BA.debugLineNum = 455;BA.debugLine="tabStripMain.ScrollTo(2, True)";
mostCurrent._tabstripmain.ScrollTo((int) (2),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 457;BA.debugLine="End Sub";
return "";
}
public static void  _btncambiarmunicipio_click() throws Exception{
ResumableSub_btnCambiarMunicipio_Click rsub = new ResumableSub_btnCambiarMunicipio_Click(null);
rsub.resume(processBA, null);
}
public static class ResumableSub_btnCambiarMunicipio_Click extends BA.ResumableSub {
public ResumableSub_btnCambiarMunicipio_Click(appear.pnud.preservamos.form_main parent) {
this.parent = parent;
}
appear.pnud.preservamos.form_main parent;
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
 //BA.debugLineNum = 1319;BA.debugLine="Main.strUserOrg = \"\"";
parent.mostCurrent._main._struserorg /*String*/  = "";
 //BA.debugLineNum = 1320;BA.debugLine="Msgbox2Async(\"¿Querés cambiar de municipio selecc";
anywheresoftware.b4a.keywords.Common.Msgbox2Async(BA.ObjectToCharSequence("¿Querés cambiar de municipio seleccionado?"),BA.ObjectToCharSequence("¿De acuerdo?"),"Si, cambiar!","","Cancelar",(anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper(), (android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null)),processBA,anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1321;BA.debugLine="Wait For Msgbox_Result (Result As Int)";
anywheresoftware.b4a.keywords.Common.WaitFor("msgbox_result", processBA, this, null);
this.state = 5;
return;
case 5:
//C
this.state = 1;
_result = (Integer) result[0];
;
 //BA.debugLineNum = 1322;BA.debugLine="If Result = DialogResponse.POSITIVE Then";
if (true) break;

case 1:
//if
this.state = 4;
if (_result==anywheresoftware.b4a.keywords.Common.DialogResponse.POSITIVE) { 
this.state = 3;
}if (true) break;

case 3:
//C
this.state = 4;
 //BA.debugLineNum = 1323;BA.debugLine="frmLocalizacion.origen = \"main\"";
parent.mostCurrent._frmlocalizacion._origen /*String*/  = "main";
 //BA.debugLineNum = 1324;BA.debugLine="StartActivity(frmLocalizacion)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(parent.mostCurrent._frmlocalizacion.getObject()));
 if (true) break;

case 4:
//C
this.state = -1;
;
 //BA.debugLineNum = 1326;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public static void  _msgbox_result(int _result) throws Exception{
}
public static void  _btncerrarsesion_click() throws Exception{
ResumableSub_btnCerrarSesion_Click rsub = new ResumableSub_btnCerrarSesion_Click(null);
rsub.resume(processBA, null);
}
public static class ResumableSub_btnCerrarSesion_Click extends BA.ResumableSub {
public ResumableSub_btnCerrarSesion_Click(appear.pnud.preservamos.form_main parent) {
this.parent = parent;
}
appear.pnud.preservamos.form_main parent;
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
 //BA.debugLineNum = 309;BA.debugLine="Msgbox2Async(\"Desea cerrar la sesión? Ingresar co";
anywheresoftware.b4a.keywords.Common.Msgbox2Async(BA.ObjectToCharSequence("Desea cerrar la sesión? Ingresar con otro usuario requiere de internet!"),BA.ObjectToCharSequence("Seguro?"),"Si, tengo internet","","No, no tengo internet ahora",(anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper(), (android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null)),processBA,anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 310;BA.debugLine="Wait For Msgbox_Result (Result As Int)";
anywheresoftware.b4a.keywords.Common.WaitFor("msgbox_result", processBA, this, null);
this.state = 5;
return;
case 5:
//C
this.state = 1;
_result = (Integer) result[0];
;
 //BA.debugLineNum = 311;BA.debugLine="If Result = DialogResponse.POSITIVE Then";
if (true) break;

case 1:
//if
this.state = 4;
if (_result==anywheresoftware.b4a.keywords.Common.DialogResponse.POSITIVE) { 
this.state = 3;
}if (true) break;

case 3:
//C
this.state = 4;
 //BA.debugLineNum = 312;BA.debugLine="Main.strUserID = \"\"";
parent.mostCurrent._main._struserid /*String*/  = "";
 //BA.debugLineNum = 313;BA.debugLine="Main.strUserName = \"\"";
parent.mostCurrent._main._strusername /*String*/  = "";
 //BA.debugLineNum = 314;BA.debugLine="Main.strUserLocation = \"\"";
parent.mostCurrent._main._struserlocation /*String*/  = "";
 //BA.debugLineNum = 315;BA.debugLine="Main.strUserEmail = \"\"";
parent.mostCurrent._main._struseremail /*String*/  = "";
 //BA.debugLineNum = 316;BA.debugLine="Main.strUserOrg = \"\"";
parent.mostCurrent._main._struserorg /*String*/  = "";
 //BA.debugLineNum = 317;BA.debugLine="Main.username = \"\"";
parent.mostCurrent._main._username /*String*/  = "";
 //BA.debugLineNum = 318;BA.debugLine="Activity.RemoveAllViews";
parent.mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 319;BA.debugLine="Activity.finish";
parent.mostCurrent._activity.Finish();
 if (true) break;

case 4:
//C
this.state = -1;
;
 //BA.debugLineNum = 321;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public static String  _btncomenzaranalisis1_click() throws Exception{
String _rndstr = "";
 //BA.debugLineNum = 850;BA.debugLine="Private Sub btnComenzarAnalisis1_Click";
 //BA.debugLineNum = 853;BA.debugLine="If Main.username = \"guest\" Or Main.username = \"No";
if ((mostCurrent._main._username /*String*/ ).equals("guest") || (mostCurrent._main._username /*String*/ ).equals("None") || (mostCurrent._main._username /*String*/ ).equals("")) { 
 //BA.debugLineNum = 854;BA.debugLine="Dim RndStr As String";
_rndstr = "";
 //BA.debugLineNum = 855;BA.debugLine="RndStr = utilidades.RandomString(6)";
_rndstr = mostCurrent._utilidades._randomstring /*String*/ (mostCurrent.activityBA,(int) (6));
 //BA.debugLineNum = 856;BA.debugLine="Main.username = \"guest_\" & RndStr";
mostCurrent._main._username /*String*/  = "guest_"+_rndstr;
 };
 //BA.debugLineNum = 858;BA.debugLine="tipoAnalisis = \"habitat\"";
mostCurrent._tipoanalisis = "habitat";
 //BA.debugLineNum = 859;BA.debugLine="Form_Reporte.origen = \"Form_Main\"";
mostCurrent._form_reporte._origen /*String*/  = "Form_Main";
 //BA.debugLineNum = 862;BA.debugLine="If File.Exists(Starter.savedir & \"/no_aprender.tx";
if (anywheresoftware.b4a.keywords.Common.File.Exists(mostCurrent._starter._savedir /*String*/ +"/no_aprender.txt","")==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 863;BA.debugLine="Aprender_Muestreo.origen = \"main\"";
mostCurrent._aprender_muestreo._origen /*String*/  = "main";
 //BA.debugLineNum = 864;BA.debugLine="StartActivity(Aprender_Muestreo)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._aprender_muestreo.getObject()));
 }else {
 //BA.debugLineNum = 866;BA.debugLine="StartActivity(Form_Reporte)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._form_reporte.getObject()));
 };
 //BA.debugLineNum = 868;BA.debugLine="End Sub";
return "";
}
public static String  _btncomenzaranalisis2_click() throws Exception{
String _rndstr = "";
 //BA.debugLineNum = 870;BA.debugLine="Private Sub btnComenzarAnalisis2_Click";
 //BA.debugLineNum = 873;BA.debugLine="If Main.username = \"guest\" Or Main.username = \"No";
if ((mostCurrent._main._username /*String*/ ).equals("guest") || (mostCurrent._main._username /*String*/ ).equals("None") || (mostCurrent._main._username /*String*/ ).equals("")) { 
 //BA.debugLineNum = 874;BA.debugLine="Dim RndStr As String";
_rndstr = "";
 //BA.debugLineNum = 875;BA.debugLine="RndStr = utilidades.RandomString(6)";
_rndstr = mostCurrent._utilidades._randomstring /*String*/ (mostCurrent.activityBA,(int) (6));
 //BA.debugLineNum = 876;BA.debugLine="Main.username = \"guest_\" & RndStr";
mostCurrent._main._username /*String*/  = "guest_"+_rndstr;
 };
 //BA.debugLineNum = 878;BA.debugLine="tipoAnalisis = \"residuos\"";
mostCurrent._tipoanalisis = "residuos";
 //BA.debugLineNum = 879;BA.debugLine="Form_Reporte.origen = \"Form_Main\"";
mostCurrent._form_reporte._origen /*String*/  = "Form_Main";
 //BA.debugLineNum = 880;BA.debugLine="StartActivity(mod_Residuos)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._mod_residuos.getObject()));
 //BA.debugLineNum = 882;BA.debugLine="End Sub";
return "";
}
public static String  _btncomenzaranalisis3_click() throws Exception{
String _rndstr = "";
 //BA.debugLineNum = 885;BA.debugLine="Private Sub btnComenzarAnalisis3_Click";
 //BA.debugLineNum = 888;BA.debugLine="If Main.username = \"guest\" Or Main.username = \"No";
if ((mostCurrent._main._username /*String*/ ).equals("guest") || (mostCurrent._main._username /*String*/ ).equals("None") || (mostCurrent._main._username /*String*/ ).equals("")) { 
 //BA.debugLineNum = 889;BA.debugLine="Dim RndStr As String";
_rndstr = "";
 //BA.debugLineNum = 890;BA.debugLine="RndStr = utilidades.RandomString(6)";
_rndstr = mostCurrent._utilidades._randomstring /*String*/ (mostCurrent.activityBA,(int) (6));
 //BA.debugLineNum = 891;BA.debugLine="Main.username = \"guest_\" & RndStr";
mostCurrent._main._username /*String*/  = "guest_"+_rndstr;
 };
 //BA.debugLineNum = 893;BA.debugLine="tipoAnalisis = \"hidro\"";
mostCurrent._tipoanalisis = "hidro";
 //BA.debugLineNum = 894;BA.debugLine="Form_Reporte.origen = \"Form_Main\"";
mostCurrent._form_reporte._origen /*String*/  = "Form_Main";
 //BA.debugLineNum = 895;BA.debugLine="StartActivity(mod_Hidro)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._mod_hidro.getObject()));
 //BA.debugLineNum = 897;BA.debugLine="End Sub";
return "";
}
public static String  _btndatosanteriores_click() throws Exception{
 //BA.debugLineNum = 357;BA.debugLine="Sub btnDatosAnteriores_Click";
 //BA.debugLineNum = 358;BA.debugLine="StartActivity(frmDatosAnteriores)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._frmdatosanteriores.getObject()));
 //BA.debugLineNum = 359;BA.debugLine="End Sub";
return "";
}
public static String  _btndatossinenviar_click() throws Exception{
 //BA.debugLineNum = 353;BA.debugLine="Sub btnDatosSinEnviar_Click";
 //BA.debugLineNum = 354;BA.debugLine="StartActivity(frmDatosSinEnviar)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._frmdatossinenviar.getObject()));
 //BA.debugLineNum = 355;BA.debugLine="End Sub";
return "";
}
public static void  _btnedituser_click() throws Exception{
ResumableSub_btnEditUser_Click rsub = new ResumableSub_btnEditUser_Click(null);
rsub.resume(processBA, null);
}
public static class ResumableSub_btnEditUser_Click extends BA.ResumableSub {
public ResumableSub_btnEditUser_Click(appear.pnud.preservamos.form_main parent) {
this.parent = parent;
}
appear.pnud.preservamos.form_main parent;
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
 //BA.debugLineNum = 326;BA.debugLine="If Main.username = \"guest\" Or Main.username = \"No";
if (true) break;

case 1:
//if
this.state = 10;
if ((parent.mostCurrent._main._username /*String*/ ).equals("guest") || (parent.mostCurrent._main._username /*String*/ ).equals("None") || (parent.mostCurrent._main._username /*String*/ ).equals("") || (parent.mostCurrent._main._username /*String*/ ).equals("null") || parent.mostCurrent._main._username /*String*/ == null) { 
this.state = 3;
}else {
this.state = 9;
}if (true) break;

case 3:
//C
this.state = 4;
 //BA.debugLineNum = 328;BA.debugLine="Msgbox2Async(\"Necesita estar registrado para ver";
anywheresoftware.b4a.keywords.Common.Msgbox2Async(BA.ObjectToCharSequence("Necesita estar registrado para ver su perfil, ¿desea hacer ingresar ahora?"),BA.ObjectToCharSequence("Ingreso"),"Si, tengo internet","","No, no tengo internet ahora",(anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper(), (android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null)),processBA,anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 329;BA.debugLine="Wait For Msgbox_Result (Result As Int)";
anywheresoftware.b4a.keywords.Common.WaitFor("msgbox_result", processBA, this, null);
this.state = 11;
return;
case 11:
//C
this.state = 4;
_result = (Integer) result[0];
;
 //BA.debugLineNum = 330;BA.debugLine="If Result = DialogResponse.POSITIVE Then";
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
 //BA.debugLineNum = 331;BA.debugLine="Main.strUserID = \"\"";
parent.mostCurrent._main._struserid /*String*/  = "";
 //BA.debugLineNum = 332;BA.debugLine="Main.strUserName = \"\"";
parent.mostCurrent._main._strusername /*String*/  = "";
 //BA.debugLineNum = 333;BA.debugLine="Main.strUserLocation = \"\"";
parent.mostCurrent._main._struserlocation /*String*/  = "";
 //BA.debugLineNum = 334;BA.debugLine="Main.strUserEmail = \"\"";
parent.mostCurrent._main._struseremail /*String*/  = "";
 //BA.debugLineNum = 335;BA.debugLine="Main.strUserOrg = \"\"";
parent.mostCurrent._main._struserorg /*String*/  = "";
 //BA.debugLineNum = 336;BA.debugLine="Main.username = \"\"";
parent.mostCurrent._main._username /*String*/  = "";
 //BA.debugLineNum = 337;BA.debugLine="Activity.RemoveAllViews";
parent.mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 338;BA.debugLine="Activity.finish";
parent.mostCurrent._activity.Finish();
 //BA.debugLineNum = 339;BA.debugLine="StartActivity(register)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(parent.mostCurrent._register.getObject()));
 if (true) break;

case 7:
//C
this.state = 10;
;
 if (true) break;

case 9:
//C
this.state = 10;
 //BA.debugLineNum = 342;BA.debugLine="StartActivity(frmEditProfile)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(parent.mostCurrent._frmeditprofile.getObject()));
 if (true) break;

case 10:
//C
this.state = -1;
;
 //BA.debugLineNum = 344;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public static String  _btninformacion_click() throws Exception{
 //BA.debugLineNum = 447;BA.debugLine="Private Sub btnInformacion_Click";
 //BA.debugLineNum = 448;BA.debugLine="tabStripMain.ScrollTo(1, True)";
mostCurrent._tabstripmain.ScrollTo((int) (1),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 450;BA.debugLine="End Sub";
return "";
}
public static String  _btnmapa_click() throws Exception{
 //BA.debugLineNum = 415;BA.debugLine="Private Sub btnMapa_Click";
 //BA.debugLineNum = 416;BA.debugLine="tabStripMain.ScrollTo(0, True)";
mostCurrent._tabstripmain.ScrollTo((int) (0),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 417;BA.debugLine="End Sub";
return "";
}
public static String  _btnmasinfomunicipio_click() throws Exception{
 //BA.debugLineNum = 1339;BA.debugLine="Private Sub btnMasInfoMunicipio_Click";
 //BA.debugLineNum = 1340;BA.debugLine="StartActivity(pi.OpenBrowser(municipioURL))";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._pi.OpenBrowser(mostCurrent._municipiourl)));
 //BA.debugLineNum = 1341;BA.debugLine="End Sub";
return "";
}
public static String  _btnmenu_main_click() throws Exception{
 //BA.debugLineNum = 278;BA.debugLine="Sub btnMenu_Main_Click";
 //BA.debugLineNum = 279;BA.debugLine="Drawer.LeftOpen = True";
mostCurrent._drawer._setleftopen /*boolean*/ (anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 280;BA.debugLine="If Main.username = \"guest\" Or Main.username = \"No";
if ((mostCurrent._main._username /*String*/ ).equals("guest") || (mostCurrent._main._username /*String*/ ).equals("None") || (mostCurrent._main._username /*String*/ ).equals("")) { 
 //BA.debugLineNum = 281;BA.debugLine="btnCerrarSesion.Text = \"Registrarse!\"";
mostCurrent._btncerrarsesion.setText(BA.ObjectToCharSequence("Registrarse!"));
 }else {
 //BA.debugLineNum = 283;BA.debugLine="btnCerrarSesion.Visible = False";
mostCurrent._btncerrarsesion.setVisible(anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 285;BA.debugLine="End Sub";
return "";
}
public static String  _btnmuestreos_click() throws Exception{
 //BA.debugLineNum = 345;BA.debugLine="Sub btnMuestreos_Click";
 //BA.debugLineNum = 346;BA.debugLine="Aprender_Muestreo.origen = \"menu\"";
mostCurrent._aprender_muestreo._origen /*String*/  = "menu";
 //BA.debugLineNum = 347;BA.debugLine="StartActivity(Aprender_Muestreo)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._aprender_muestreo.getObject()));
 //BA.debugLineNum = 348;BA.debugLine="End Sub";
return "";
}
public static String  _btnmunicipiofb_click() throws Exception{
 //BA.debugLineNum = 1351;BA.debugLine="Private Sub btnMunicipioFB_Click";
 //BA.debugLineNum = 1352;BA.debugLine="StartActivity(pi.OpenBrowser(municipioFB))";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._pi.OpenBrowser(mostCurrent._municipiofb)));
 //BA.debugLineNum = 1353;BA.debugLine="End Sub";
return "";
}
public static String  _btnmunicipioig_click() throws Exception{
 //BA.debugLineNum = 1343;BA.debugLine="Private Sub btnMunicipioIG_Click";
 //BA.debugLineNum = 1344;BA.debugLine="StartActivity(pi.OpenBrowser(municipioIG))";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._pi.OpenBrowser(mostCurrent._municipioig)));
 //BA.debugLineNum = 1345;BA.debugLine="End Sub";
return "";
}
public static String  _btnmunicipiotw_click() throws Exception{
 //BA.debugLineNum = 1355;BA.debugLine="Private Sub btnMunicipioTW_Click";
 //BA.debugLineNum = 1356;BA.debugLine="StartActivity(pi.OpenBrowser(municipioTW))";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._pi.OpenBrowser(mostCurrent._municipiotw)));
 //BA.debugLineNum = 1357;BA.debugLine="End Sub";
return "";
}
public static String  _btnmunicipioyt_click() throws Exception{
 //BA.debugLineNum = 1347;BA.debugLine="Private Sub btnMunicipioYT_Click";
 //BA.debugLineNum = 1348;BA.debugLine="StartActivity(pi.OpenBrowser(municipioYT))";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._pi.OpenBrowser(mostCurrent._municipioyt)));
 //BA.debugLineNum = 1349;BA.debugLine="End Sub";
return "";
}
public static String  _btnperfil_click() throws Exception{
 //BA.debugLineNum = 464;BA.debugLine="Private Sub btnPerfil_Click";
 //BA.debugLineNum = 465;BA.debugLine="tabStripMain.ScrollTo(3, True)";
mostCurrent._tabstripmain.ScrollTo((int) (3),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 466;BA.debugLine="End Sub";
return "";
}
public static String  _btnpoliticadatos_click() throws Exception{
 //BA.debugLineNum = 349;BA.debugLine="Sub btnPoliticaDatos_Click";
 //BA.debugLineNum = 350;BA.debugLine="StartActivity(frmPoliticaDatos)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._frmpoliticadatos.getObject()));
 //BA.debugLineNum = 352;BA.debugLine="End Sub";
return "";
}
public static String  _btnreportar_click() throws Exception{
 //BA.debugLineNum = 840;BA.debugLine="Sub btnReportar_Click";
 //BA.debugLineNum = 841;BA.debugLine="scrReportar_Modulos.visible = True";
mostCurrent._scrreportar_modulos.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 842;BA.debugLine="pnlHabitat.SetVisibleAnimated(300, True)";
mostCurrent._pnlhabitat.SetVisibleAnimated((int) (300),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 843;BA.debugLine="pnlResiduos.SetVisibleAnimated(700, True)";
mostCurrent._pnlresiduos.SetVisibleAnimated((int) (700),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 844;BA.debugLine="pnlHidro.SetVisibleAnimated(1200, True)";
mostCurrent._pnlhidro.SetVisibleAnimated((int) (1200),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 846;BA.debugLine="lblAnalizar.SetLayoutAnimated(300,lblAnalizar.Lef";
mostCurrent._lblanalizar.SetLayoutAnimated((int) (300),mostCurrent._lblanalizar.getLeft(),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10)),mostCurrent._lblanalizar.getWidth(),mostCurrent._lblanalizar.getHeight());
 //BA.debugLineNum = 847;BA.debugLine="btnReportar.SetLayoutAnimated(300,btnReportar.Lef";
mostCurrent._btnreportar.SetLayoutAnimated((int) (300),mostCurrent._btnreportar.getLeft(),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (60)),mostCurrent._btnreportar.getWidth(),mostCurrent._btnreportar.getHeight());
 //BA.debugLineNum = 848;BA.debugLine="End Sub";
return "";
}
public static String  _btnshare_click() throws Exception{
com.madelephantstudios.MESShareLibrary.MESShareLibrary _share = null;
 //BA.debugLineNum = 1772;BA.debugLine="Sub btnShare_Click";
 //BA.debugLineNum = 1775;BA.debugLine="Dim share As MESShareLibrary";
_share = new com.madelephantstudios.MESShareLibrary.MESShareLibrary();
 //BA.debugLineNum = 1776;BA.debugLine="File.Copy(File.DirAssets, imagetoshare, File.DirD";
anywheresoftware.b4a.keywords.Common.File.Copy(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._imagetoshare,anywheresoftware.b4a.keywords.Common.File.getDirDefaultExternal(),"Logo_preservamos_small.png");
 //BA.debugLineNum = 1777;BA.debugLine="share.sharebinary(\"file://\" & Starter.savedir & \"";
_share.sharebinary(mostCurrent.activityBA,"file://"+mostCurrent._starter._savedir /*String*/ +"/Logo_preservamos_small.png","image/png","PreserVamos - Ciencia Ciudadana","Participo de PreserVamos y gané esta medalla!");
 //BA.debugLineNum = 1789;BA.debugLine="pnlShare.RemoveView";
mostCurrent._pnlshare.RemoveView();
 //BA.debugLineNum = 1790;BA.debugLine="fondogris_share.RemoveView";
mostCurrent._fondogris_share.RemoveView();
 //BA.debugLineNum = 1848;BA.debugLine="End Sub";
return "";
}
public static String  _btnverdatosmunicipio_click() throws Exception{
 //BA.debugLineNum = 1328;BA.debugLine="Private Sub btnVerDatosMunicipio_Click";
 //BA.debugLineNum = 1329;BA.debugLine="If Municipios_numresults <> \"0\" Then";
if (_municipios_numresults!=(double)(Double.parseDouble("0"))) { 
 //BA.debugLineNum = 1330;BA.debugLine="frmMapa.datosMunicipio_string = Municipios_parse";
mostCurrent._frmmapa._datosmunicipio_string /*String*/  = mostCurrent._municipios_parser_string;
 //BA.debugLineNum = 1331;BA.debugLine="frmMapa.origen=\"municipio\"";
mostCurrent._frmmapa._origen /*String*/  = "municipio";
 //BA.debugLineNum = 1332;BA.debugLine="StartActivity(frmMapa)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._frmmapa.getObject()));
 }else {
 //BA.debugLineNum = 1334;BA.debugLine="ToastMessageShow(\"No hay envíos de ese municipio";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("No hay envíos de ese municipio todavia"),anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 1336;BA.debugLine="End Sub";
return "";
}
public static String  _btnverestadisticasmunicipio_click() throws Exception{
 //BA.debugLineNum = 1382;BA.debugLine="Private Sub btnVerEstadisticasMunicipio_Click";
 //BA.debugLineNum = 1383;BA.debugLine="If Municipios_numresults <> \"0\" Then";
if (_municipios_numresults!=(double)(Double.parseDouble("0"))) { 
 //BA.debugLineNum = 1384;BA.debugLine="frmMunicipioEstadisticas.listaAgua_Promedio = li";
mostCurrent._frmmunicipioestadisticas._listaagua_promedio /*anywheresoftware.b4a.objects.collections.List*/  = mostCurrent._listaagua_promedio;
 //BA.debugLineNum = 1385;BA.debugLine="frmMunicipioEstadisticas.listaBio_Promedio = lis";
mostCurrent._frmmunicipioestadisticas._listabio_promedio /*anywheresoftware.b4a.objects.collections.List*/  = mostCurrent._listabio_promedio;
 //BA.debugLineNum = 1386;BA.debugLine="frmMunicipioEstadisticas.listaHidro_Promedio = l";
mostCurrent._frmmunicipioestadisticas._listahidro_promedio /*anywheresoftware.b4a.objects.collections.List*/  = mostCurrent._listahidro_promedio;
 //BA.debugLineNum = 1387;BA.debugLine="frmMunicipioEstadisticas.listaUsos_Promedio = li";
mostCurrent._frmmunicipioestadisticas._listausos_promedio /*anywheresoftware.b4a.objects.collections.List*/  = mostCurrent._listausos_promedio;
 //BA.debugLineNum = 1388;BA.debugLine="frmMunicipioEstadisticas.listaExoticas_Promedio";
mostCurrent._frmmunicipioestadisticas._listaexoticas_promedio /*anywheresoftware.b4a.objects.collections.List*/  = mostCurrent._listaexoticas_promedio;
 //BA.debugLineNum = 1389;BA.debugLine="ToastMessageShow(\"Cargando detalle del municipio";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Cargando detalle del municipio, por favor aguarde..."),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1390;BA.debugLine="StartActivity(frmMunicipioEstadisticas)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._frmmunicipioestadisticas.getObject()));
 }else {
 //BA.debugLineNum = 1392;BA.debugLine="ToastMessageShow(\"No hay envíos de ese municipio";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("No hay envíos de ese municipio todavia"),anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 1394;BA.debugLine="End Sub";
return "";
}
public static void  _btnverperfil_click() throws Exception{
ResumableSub_btnVerPerfil_Click rsub = new ResumableSub_btnVerPerfil_Click(null);
rsub.resume(processBA, null);
}
public static class ResumableSub_btnVerPerfil_Click extends BA.ResumableSub {
public ResumableSub_btnVerPerfil_Click(appear.pnud.preservamos.form_main parent) {
this.parent = parent;
}
appear.pnud.preservamos.form_main parent;
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
 //BA.debugLineNum = 288;BA.debugLine="If Main.username = \"guest\" Or Main.username = \"No";
if (true) break;

case 1:
//if
this.state = 10;
if ((parent.mostCurrent._main._username /*String*/ ).equals("guest") || (parent.mostCurrent._main._username /*String*/ ).equals("None") || (parent.mostCurrent._main._username /*String*/ ).equals("") || (parent.mostCurrent._main._username /*String*/ ).equals("null") || parent.mostCurrent._main._username /*String*/ == null) { 
this.state = 3;
}else {
this.state = 9;
}if (true) break;

case 3:
//C
this.state = 4;
 //BA.debugLineNum = 290;BA.debugLine="Msgbox2Async(\"Necesita estar registrado para ver";
anywheresoftware.b4a.keywords.Common.Msgbox2Async(BA.ObjectToCharSequence("Necesita estar registrado para ver su perfil, ¿desea hacer ingresar ahora?"),BA.ObjectToCharSequence("Ingreso"),"Si, tengo internet","","No, no tengo internet ahora",(anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper(), (android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null)),processBA,anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 291;BA.debugLine="Wait For Msgbox_Result (Result As Int)";
anywheresoftware.b4a.keywords.Common.WaitFor("msgbox_result", processBA, this, null);
this.state = 11;
return;
case 11:
//C
this.state = 4;
_result = (Integer) result[0];
;
 //BA.debugLineNum = 292;BA.debugLine="If Result = DialogResponse.POSITIVE Then";
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
 //BA.debugLineNum = 293;BA.debugLine="Main.strUserID = \"\"";
parent.mostCurrent._main._struserid /*String*/  = "";
 //BA.debugLineNum = 294;BA.debugLine="Main.strUserName = \"\"";
parent.mostCurrent._main._strusername /*String*/  = "";
 //BA.debugLineNum = 295;BA.debugLine="Main.strUserLocation = \"\"";
parent.mostCurrent._main._struserlocation /*String*/  = "";
 //BA.debugLineNum = 296;BA.debugLine="Main.strUserEmail = \"\"";
parent.mostCurrent._main._struseremail /*String*/  = "";
 //BA.debugLineNum = 297;BA.debugLine="Main.strUserOrg = \"\"";
parent.mostCurrent._main._struserorg /*String*/  = "";
 //BA.debugLineNum = 298;BA.debugLine="Main.username = \"\"";
parent.mostCurrent._main._username /*String*/  = "";
 //BA.debugLineNum = 299;BA.debugLine="Activity.RemoveAllViews";
parent.mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 300;BA.debugLine="Activity.finish";
parent.mostCurrent._activity.Finish();
 //BA.debugLineNum = 301;BA.debugLine="StartActivity(register)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(parent.mostCurrent._register.getObject()));
 if (true) break;

case 7:
//C
this.state = 10;
;
 if (true) break;

case 9:
//C
this.state = 10;
 //BA.debugLineNum = 304;BA.debugLine="StartActivity(frmEditProfile)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(parent.mostCurrent._frmeditprofile.getObject()));
 if (true) break;

case 10:
//C
this.state = -1;
;
 //BA.debugLineNum = 306;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public static void  _butalerta_click() throws Exception{
ResumableSub_butAlerta_Click rsub = new ResumableSub_butAlerta_Click(null);
rsub.resume(processBA, null);
}
public static class ResumableSub_butAlerta_Click extends BA.ResumableSub {
public ResumableSub_butAlerta_Click(appear.pnud.preservamos.form_main parent) {
this.parent = parent;
}
appear.pnud.preservamos.form_main parent;
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
 //BA.debugLineNum = 564;BA.debugLine="icoMapa.TextColor = Colors.ARGB(255,51,51,51)";
parent.mostCurrent._icomapa.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (51),(int) (51),(int) (51)));
 //BA.debugLineNum = 565;BA.debugLine="icoAnalizar.TextColor = Colors.ARGB(255,51,51,51)";
parent.mostCurrent._icoanalizar.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (51),(int) (51),(int) (51)));
 //BA.debugLineNum = 566;BA.debugLine="icoInformacion.TextColor = Colors.ARGB(255,51,51,";
parent.mostCurrent._icoinformacion.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (51),(int) (51),(int) (51)));
 //BA.debugLineNum = 567;BA.debugLine="icoPerfil.TextColor = Colors.ARGB(255,51,51,51)";
parent.mostCurrent._icoperfil.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (51),(int) (51),(int) (51)));
 //BA.debugLineNum = 568;BA.debugLine="icoMapa.TextSize = 40";
parent.mostCurrent._icomapa.setTextSize((float) (40));
 //BA.debugLineNum = 569;BA.debugLine="icoAnalizar.TextSize = 40";
parent.mostCurrent._icoanalizar.setTextSize((float) (40));
 //BA.debugLineNum = 570;BA.debugLine="icoInformacion.TextSize = 40";
parent.mostCurrent._icoinformacion.setTextSize((float) (40));
 //BA.debugLineNum = 571;BA.debugLine="icoPerfil.TextSize = 40";
parent.mostCurrent._icoperfil.setTextSize((float) (40));
 //BA.debugLineNum = 572;BA.debugLine="btnMapa.Text = \"Explorar\"";
parent.mostCurrent._btnmapa.setText(BA.ObjectToCharSequence("Explorar"));
 //BA.debugLineNum = 573;BA.debugLine="btnInformacion.Text = \"Municipio\"";
parent.mostCurrent._btninformacion.setText(BA.ObjectToCharSequence("Municipio"));
 //BA.debugLineNum = 574;BA.debugLine="btnAnalizar.Text = \"Analizar\"";
parent.mostCurrent._btnanalizar.setText(BA.ObjectToCharSequence("Analizar"));
 //BA.debugLineNum = 575;BA.debugLine="btnPerfil.Text = \"Perfil\"";
parent.mostCurrent._btnperfil.setText(BA.ObjectToCharSequence("Perfil"));
 //BA.debugLineNum = 577;BA.debugLine="icoAlerta.SetTextColorAnimated(300, Colors.white)";
parent.mostCurrent._icoalerta.SetTextColorAnimated((int) (300),anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 578;BA.debugLine="icoAlerta.SetTextSizeAnimated(300,50)";
parent.mostCurrent._icoalerta.SetTextSizeAnimated((int) (300),(float) (50));
 //BA.debugLineNum = 579;BA.debugLine="btnCirculo.SetLayoutAnimated(300, (icoAlerta.Left";
parent.mostCurrent._btncirculo.SetLayoutAnimated((int) (300),(int) ((parent.mostCurrent._icoalerta.getLeft()-((parent.mostCurrent._btncirculo.getWidth()-parent.mostCurrent._icoalerta.getWidth())/(double)2))),parent.mostCurrent._btncirculo.getTop(),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (80)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (80)));
 //BA.debugLineNum = 580;BA.debugLine="btnPerfil.Text = \"\"";
parent.mostCurrent._btnperfil.setText(BA.ObjectToCharSequence(""));
 //BA.debugLineNum = 583;BA.debugLine="Msgbox2Async(\"Podrás emitir un alerta verde sobre";
anywheresoftware.b4a.keywords.Common.Msgbox2Async(BA.ObjectToCharSequence("Podrás emitir un alerta verde sobre algún evento ecológico, como una floración de algas, una mortandad de peces, o un derrame industrial!"),BA.ObjectToCharSequence("¡ALERTA VERDE!"),"Proseguir!","","Cancelar alerta",(anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper(), (android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null)),processBA,anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 584;BA.debugLine="Wait For Msgbox_Result (Result As Int)";
anywheresoftware.b4a.keywords.Common.WaitFor("msgbox_result", processBA, this, null);
this.state = 17;
return;
case 17:
//C
this.state = 1;
_result = (Integer) result[0];
;
 //BA.debugLineNum = 585;BA.debugLine="If Result = DialogResponse.POSITIVE Then";
if (true) break;

case 1:
//if
this.state = 16;
if (_result==anywheresoftware.b4a.keywords.Common.DialogResponse.POSITIVE) { 
this.state = 3;
}else {
this.state = 5;
}if (true) break;

case 3:
//C
this.state = 16;
 //BA.debugLineNum = 586;BA.debugLine="StartActivity(Alertas)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(parent.mostCurrent._alertas.getObject()));
 if (true) break;

case 5:
//C
this.state = 6;
 //BA.debugLineNum = 588;BA.debugLine="If tabStripMain.CurrentPage = 0 Then";
if (true) break;

case 6:
//if
this.state = 15;
if (parent.mostCurrent._tabstripmain.getCurrentPage()==0) { 
this.state = 8;
}else if(parent.mostCurrent._tabstripmain.getCurrentPage()==1) { 
this.state = 10;
}else if(parent.mostCurrent._tabstripmain.getCurrentPage()==2) { 
this.state = 12;
}else if(parent.mostCurrent._tabstripmain.getCurrentPage()==3) { 
this.state = 14;
}if (true) break;

case 8:
//C
this.state = 15;
 //BA.debugLineNum = 589;BA.debugLine="icoAlerta.TextColor = Colors.ARGB(255,51,51,51)";
parent.mostCurrent._icoalerta.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (51),(int) (51),(int) (51)));
 //BA.debugLineNum = 590;BA.debugLine="icoAnalizar.TextColor = Colors.ARGB(255,51,51,5";
parent.mostCurrent._icoanalizar.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (51),(int) (51),(int) (51)));
 //BA.debugLineNum = 591;BA.debugLine="icoInformacion.TextColor = Colors.ARGB(255,51,5";
parent.mostCurrent._icoinformacion.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (51),(int) (51),(int) (51)));
 //BA.debugLineNum = 592;BA.debugLine="icoPerfil.TextColor = Colors.ARGB(255,51,51,51)";
parent.mostCurrent._icoperfil.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (51),(int) (51),(int) (51)));
 //BA.debugLineNum = 593;BA.debugLine="icoAlerta.TextSize = 40";
parent.mostCurrent._icoalerta.setTextSize((float) (40));
 //BA.debugLineNum = 594;BA.debugLine="icoAnalizar.TextSize = 40";
parent.mostCurrent._icoanalizar.setTextSize((float) (40));
 //BA.debugLineNum = 595;BA.debugLine="icoInformacion.TextSize = 40";
parent.mostCurrent._icoinformacion.setTextSize((float) (40));
 //BA.debugLineNum = 596;BA.debugLine="icoPerfil.TextSize = 40";
parent.mostCurrent._icoperfil.setTextSize((float) (40));
 //BA.debugLineNum = 597;BA.debugLine="btnInformacion.Text = \"Municipio\"";
parent.mostCurrent._btninformacion.setText(BA.ObjectToCharSequence("Municipio"));
 //BA.debugLineNum = 598;BA.debugLine="btnAnalizar.Text = \"Analizar\"";
parent.mostCurrent._btnanalizar.setText(BA.ObjectToCharSequence("Analizar"));
 //BA.debugLineNum = 599;BA.debugLine="btnPerfil.Text  = \"Perfil\"";
parent.mostCurrent._btnperfil.setText(BA.ObjectToCharSequence("Perfil"));
 //BA.debugLineNum = 601;BA.debugLine="icoMapa.SetTextColorAnimated(300, Colors.white)";
parent.mostCurrent._icomapa.SetTextColorAnimated((int) (300),anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 602;BA.debugLine="icoMapa.SetTextSizeAnimated(300,50)";
parent.mostCurrent._icomapa.SetTextSizeAnimated((int) (300),(float) (50));
 //BA.debugLineNum = 603;BA.debugLine="btnCirculo.SetLayoutAnimated(300, (icoMapa.Left";
parent.mostCurrent._btncirculo.SetLayoutAnimated((int) (300),(int) ((parent.mostCurrent._icomapa.getLeft()-((parent.mostCurrent._btncirculo.getWidth()-parent.mostCurrent._icomapa.getWidth())/(double)2))),parent.mostCurrent._btncirculo.getTop(),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (80)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (80)));
 //BA.debugLineNum = 604;BA.debugLine="btnMapa.Text = \"";
parent.mostCurrent._btnmapa.setText(BA.ObjectToCharSequence(""));
 if (true) break;

case 10:
//C
this.state = 15;
 //BA.debugLineNum = 606;BA.debugLine="icoAlerta.TextColor = Colors.ARGB(255,51,51,51)";
parent.mostCurrent._icoalerta.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (51),(int) (51),(int) (51)));
 //BA.debugLineNum = 607;BA.debugLine="icoAnalizar.TextColor = Colors.ARGB(255,51,51,5";
parent.mostCurrent._icoanalizar.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (51),(int) (51),(int) (51)));
 //BA.debugLineNum = 608;BA.debugLine="icoMapa.TextColor = Colors.ARGB(255,51,51,51)";
parent.mostCurrent._icomapa.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (51),(int) (51),(int) (51)));
 //BA.debugLineNum = 609;BA.debugLine="icoPerfil.TextColor = Colors.ARGB(255,51,51,51)";
parent.mostCurrent._icoperfil.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (51),(int) (51),(int) (51)));
 //BA.debugLineNum = 610;BA.debugLine="icoAlerta.TextSize = 40";
parent.mostCurrent._icoalerta.setTextSize((float) (40));
 //BA.debugLineNum = 611;BA.debugLine="icoAnalizar.TextSize = 40";
parent.mostCurrent._icoanalizar.setTextSize((float) (40));
 //BA.debugLineNum = 612;BA.debugLine="icoMapa.TextSize = 40";
parent.mostCurrent._icomapa.setTextSize((float) (40));
 //BA.debugLineNum = 613;BA.debugLine="icoPerfil.TextSize = 40";
parent.mostCurrent._icoperfil.setTextSize((float) (40));
 //BA.debugLineNum = 614;BA.debugLine="btnMapa.Text = \"Explorar\"";
parent.mostCurrent._btnmapa.setText(BA.ObjectToCharSequence("Explorar"));
 //BA.debugLineNum = 615;BA.debugLine="btnAnalizar.Text = \"Analizar\"";
parent.mostCurrent._btnanalizar.setText(BA.ObjectToCharSequence("Analizar"));
 //BA.debugLineNum = 616;BA.debugLine="btnPerfil.Text  = \"Perfil\"";
parent.mostCurrent._btnperfil.setText(BA.ObjectToCharSequence("Perfil"));
 //BA.debugLineNum = 618;BA.debugLine="icoInformacion.SetTextColorAnimated(300, Colors";
parent.mostCurrent._icoinformacion.SetTextColorAnimated((int) (300),anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 619;BA.debugLine="icoInformacion.SetTextSizeAnimated(300,50)";
parent.mostCurrent._icoinformacion.SetTextSizeAnimated((int) (300),(float) (50));
 //BA.debugLineNum = 620;BA.debugLine="btnCirculo.SetLayoutAnimated(300, (btnInformaci";
parent.mostCurrent._btncirculo.SetLayoutAnimated((int) (300),(int) ((parent.mostCurrent._btninformacion.getLeft()-((parent.mostCurrent._btncirculo.getWidth()-parent.mostCurrent._btninformacion.getWidth())/(double)2))),parent.mostCurrent._btncirculo.getTop(),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (80)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (80)));
 //BA.debugLineNum = 621;BA.debugLine="btnInformacion.Text = \"\"";
parent.mostCurrent._btninformacion.setText(BA.ObjectToCharSequence(""));
 if (true) break;

case 12:
//C
this.state = 15;
 //BA.debugLineNum = 623;BA.debugLine="icoMapa.TextColor = Colors.ARGB(255,51,51,51)";
parent.mostCurrent._icomapa.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (51),(int) (51),(int) (51)));
 //BA.debugLineNum = 624;BA.debugLine="icoInformacion.TextColor = Colors.ARGB(255,51,5";
parent.mostCurrent._icoinformacion.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (51),(int) (51),(int) (51)));
 //BA.debugLineNum = 625;BA.debugLine="icoPerfil.TextColor = Colors.ARGB(255,51,51,51)";
parent.mostCurrent._icoperfil.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (51),(int) (51),(int) (51)));
 //BA.debugLineNum = 626;BA.debugLine="icoMapa.TextSize = 40";
parent.mostCurrent._icomapa.setTextSize((float) (40));
 //BA.debugLineNum = 627;BA.debugLine="icoInformacion.TextSize = 40";
parent.mostCurrent._icoinformacion.setTextSize((float) (40));
 //BA.debugLineNum = 628;BA.debugLine="icoPerfil.TextSize = 40";
parent.mostCurrent._icoperfil.setTextSize((float) (40));
 //BA.debugLineNum = 629;BA.debugLine="btnMapa.Text = \"Explorar\"";
parent.mostCurrent._btnmapa.setText(BA.ObjectToCharSequence("Explorar"));
 //BA.debugLineNum = 630;BA.debugLine="btnInformacion.Text = \"Municipio\"";
parent.mostCurrent._btninformacion.setText(BA.ObjectToCharSequence("Municipio"));
 //BA.debugLineNum = 631;BA.debugLine="btnPerfil.Text  = \"Perfil\"";
parent.mostCurrent._btnperfil.setText(BA.ObjectToCharSequence("Perfil"));
 //BA.debugLineNum = 633;BA.debugLine="icoAnalizar.SetTextColorAnimated(300, Colors.wh";
parent.mostCurrent._icoanalizar.SetTextColorAnimated((int) (300),anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 634;BA.debugLine="icoAnalizar.SetTextSizeAnimated(300,50)";
parent.mostCurrent._icoanalizar.SetTextSizeAnimated((int) (300),(float) (50));
 //BA.debugLineNum = 635;BA.debugLine="btnCirculo.SetLayoutAnimated(300, (btnAnalizar.";
parent.mostCurrent._btncirculo.SetLayoutAnimated((int) (300),(int) ((parent.mostCurrent._btnanalizar.getLeft()-((parent.mostCurrent._btncirculo.getWidth()-parent.mostCurrent._btnanalizar.getWidth())/(double)2))),parent.mostCurrent._btncirculo.getTop(),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (80)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (80)));
 //BA.debugLineNum = 636;BA.debugLine="btnAnalizar.Text = \"\"";
parent.mostCurrent._btnanalizar.setText(BA.ObjectToCharSequence(""));
 if (true) break;

case 14:
//C
this.state = 15;
 //BA.debugLineNum = 638;BA.debugLine="icoMapa.TextColor = Colors.ARGB(255,51,51,51)";
parent.mostCurrent._icomapa.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (51),(int) (51),(int) (51)));
 //BA.debugLineNum = 639;BA.debugLine="icoAnalizar.TextColor = Colors.ARGB(255,51,51,5";
parent.mostCurrent._icoanalizar.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (51),(int) (51),(int) (51)));
 //BA.debugLineNum = 640;BA.debugLine="icoInformacion.TextColor = Colors.ARGB(255,51,5";
parent.mostCurrent._icoinformacion.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (51),(int) (51),(int) (51)));
 //BA.debugLineNum = 641;BA.debugLine="icoAlerta.TextColor = Colors.ARGB(255,51,51,51)";
parent.mostCurrent._icoalerta.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (51),(int) (51),(int) (51)));
 //BA.debugLineNum = 642;BA.debugLine="icoMapa.TextSize = 40";
parent.mostCurrent._icomapa.setTextSize((float) (40));
 //BA.debugLineNum = 643;BA.debugLine="icoAnalizar.TextSize = 40";
parent.mostCurrent._icoanalizar.setTextSize((float) (40));
 //BA.debugLineNum = 644;BA.debugLine="icoInformacion.TextSize = 40";
parent.mostCurrent._icoinformacion.setTextSize((float) (40));
 //BA.debugLineNum = 645;BA.debugLine="icoAlerta.TextSize = 40";
parent.mostCurrent._icoalerta.setTextSize((float) (40));
 //BA.debugLineNum = 646;BA.debugLine="btnMapa.Text = \"Explorar\"";
parent.mostCurrent._btnmapa.setText(BA.ObjectToCharSequence("Explorar"));
 //BA.debugLineNum = 647;BA.debugLine="btnInformacion.Text = \"Municipio\"";
parent.mostCurrent._btninformacion.setText(BA.ObjectToCharSequence("Municipio"));
 //BA.debugLineNum = 648;BA.debugLine="btnAnalizar.Text = \"Analizar\"";
parent.mostCurrent._btnanalizar.setText(BA.ObjectToCharSequence("Analizar"));
 //BA.debugLineNum = 650;BA.debugLine="icoPerfil.SetTextColorAnimated(300, Colors.whit";
parent.mostCurrent._icoperfil.SetTextColorAnimated((int) (300),anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 651;BA.debugLine="icoPerfil.SetTextSizeAnimated(300,50)";
parent.mostCurrent._icoperfil.SetTextSizeAnimated((int) (300),(float) (50));
 //BA.debugLineNum = 652;BA.debugLine="btnCirculo.SetLayoutAnimated(300, (icoPerfil.Le";
parent.mostCurrent._btncirculo.SetLayoutAnimated((int) (300),(int) ((parent.mostCurrent._icoperfil.getLeft()-((parent.mostCurrent._btncirculo.getWidth()-parent.mostCurrent._icoperfil.getWidth())/(double)2))),parent.mostCurrent._btncirculo.getTop(),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (80)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (80)));
 //BA.debugLineNum = 653;BA.debugLine="btnPerfil.Text = \"\"";
parent.mostCurrent._btnperfil.setText(BA.ObjectToCharSequence(""));
 if (true) break;

case 15:
//C
this.state = 16;
;
 if (true) break;

case 16:
//C
this.state = -1;
;
 //BA.debugLineNum = 656;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public static String  _calcular_puntos_ambientes() throws Exception{
anywheresoftware.b4a.objects.collections.List _usermarkers = null;
int _i = 0;
anywheresoftware.b4a.objects.collections.Map _usermarker = null;
anywheresoftware.b4a.objects.collections.List _usermarkers_residuos = null;
anywheresoftware.b4a.objects.collections.List _usermarkers_hidro = null;
 //BA.debugLineNum = 1498;BA.debugLine="Sub calcular_puntos_ambientes";
 //BA.debugLineNum = 1499;BA.debugLine="Log(\"calculando list\")";
anywheresoftware.b4a.keywords.Common.LogImpl("43801089","calculando list",0);
 //BA.debugLineNum = 1501;BA.debugLine="Try";
try { //BA.debugLineNum = 1503;BA.debugLine="Dim usermarkers As List";
_usermarkers = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 1504;BA.debugLine="usermarkers = Main.user_markers_list";
_usermarkers = mostCurrent._main._user_markers_list /*anywheresoftware.b4a.objects.collections.List*/ ;
 //BA.debugLineNum = 1505;BA.debugLine="For i = 0 To usermarkers.Size - 1";
{
final int step5 = 1;
final int limit5 = (int) (_usermarkers.getSize()-1);
_i = (int) (0) ;
for (;_i <= limit5 ;_i = _i + step5 ) {
 //BA.debugLineNum = 1506;BA.debugLine="Dim usermarker As Map";
_usermarker = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 1508;BA.debugLine="usermarker = usermarkers.Get(i)";
_usermarker = (anywheresoftware.b4a.objects.collections.Map) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.collections.Map(), (java.util.Map)(_usermarkers.Get(_i)));
 //BA.debugLineNum = 1509;BA.debugLine="If usermarker.Get(\"tiporio\") = \"Llanura\" Or user";
if ((_usermarker.Get((Object)("tiporio"))).equals((Object)("Llanura")) || (_usermarker.Get((Object)("tiporio"))).equals((Object)("llanura"))) { 
 //BA.debugLineNum = 1510;BA.debugLine="Main.puntos_markers_llanura = Main.puntos_mark";
mostCurrent._main._puntos_markers_llanura /*int*/  = (int) (mostCurrent._main._puntos_markers_llanura /*int*/ +1);
 }else if((_usermarker.Get((Object)("tiporio"))).equals((Object)("Laguna")) || (_usermarker.Get((Object)("tiporio"))).equals((Object)("laguna"))) { 
 //BA.debugLineNum = 1512;BA.debugLine="Main.puntos_markers_laguna = Main.puntos_marke";
mostCurrent._main._puntos_markers_laguna /*int*/  = (int) (mostCurrent._main._puntos_markers_laguna /*int*/ +1);
 }else if((_usermarker.Get((Object)("tiporio"))).equals((Object)("Sierras")) || (_usermarker.Get((Object)("tiporio"))).equals((Object)("sierras"))) { 
 //BA.debugLineNum = 1514;BA.debugLine="Main.puntos_markers_sierras = Main.puntos_mark";
mostCurrent._main._puntos_markers_sierras /*int*/  = (int) (mostCurrent._main._puntos_markers_sierras /*int*/ +1);
 };
 //BA.debugLineNum = 1517;BA.debugLine="Main.puntostotales = Main.puntostotales + 2";
mostCurrent._main._puntostotales /*String*/  = BA.NumberToString((double)(Double.parseDouble(mostCurrent._main._puntostotales /*String*/ ))+2);
 //BA.debugLineNum = 1518;BA.debugLine="Main.puntos_markers = Main.puntos_markers + 1";
mostCurrent._main._puntos_markers /*int*/  = (int) (mostCurrent._main._puntos_markers /*int*/ +1);
 }
};
 //BA.debugLineNum = 1520;BA.debugLine="Log(\"Main.puntos_markers\" & Main.puntos_markers)";
anywheresoftware.b4a.keywords.Common.LogImpl("43801110","Main.puntos_markers"+BA.NumberToString(mostCurrent._main._puntos_markers /*int*/ ),0);
 } 
       catch (Exception e20) {
			processBA.setLastException(e20); //BA.debugLineNum = 1522;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("43801112",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA)),0);
 };
 //BA.debugLineNum = 1526;BA.debugLine="Try";
try { //BA.debugLineNum = 1528;BA.debugLine="Dim usermarkers_residuos As List";
_usermarkers_residuos = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 1529;BA.debugLine="usermarkers_residuos = Main.user_evals_residuos_";
_usermarkers_residuos = mostCurrent._main._user_evals_residuos_list /*anywheresoftware.b4a.objects.collections.List*/ ;
 //BA.debugLineNum = 1530;BA.debugLine="For i = 0 To usermarkers_residuos.Size - 1";
{
final int step25 = 1;
final int limit25 = (int) (_usermarkers_residuos.getSize()-1);
_i = (int) (0) ;
for (;_i <= limit25 ;_i = _i + step25 ) {
 //BA.debugLineNum = 1531;BA.debugLine="Dim usermarker As Map";
_usermarker = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 1532;BA.debugLine="usermarker = usermarkers_residuos.Get(i)";
_usermarker = (anywheresoftware.b4a.objects.collections.Map) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.collections.Map(), (java.util.Map)(_usermarkers_residuos.Get(_i)));
 //BA.debugLineNum = 1533;BA.debugLine="If usermarker.Get(\"tiporio\") = \"llanura\" Or use";
if ((_usermarker.Get((Object)("tiporio"))).equals((Object)("llanura")) || (_usermarker.Get((Object)("tiporio"))).equals((Object)("llanura"))) { 
 //BA.debugLineNum = 1534;BA.debugLine="Main.puntos_evals_residuos_llanura = Main.punt";
mostCurrent._main._puntos_evals_residuos_llanura /*int*/  = (int) (mostCurrent._main._puntos_evals_residuos_llanura /*int*/ +1);
 }else if((_usermarker.Get((Object)("tiporio"))).equals((Object)("laguna")) || (_usermarker.Get((Object)("tiporio"))).equals((Object)("laguna"))) { 
 //BA.debugLineNum = 1536;BA.debugLine="Main.puntos_evals_residuos_laguna = Main.punto";
mostCurrent._main._puntos_evals_residuos_laguna /*int*/  = (int) (mostCurrent._main._puntos_evals_residuos_laguna /*int*/ +1);
 }else if((_usermarker.Get((Object)("tiporio"))).equals((Object)("sierras")) || (_usermarker.Get((Object)("tiporio"))).equals((Object)("sierras"))) { 
 //BA.debugLineNum = 1538;BA.debugLine="Main.puntos_evals_residuos_sierras = Main.punt";
mostCurrent._main._puntos_evals_residuos_sierras /*int*/  = (int) (mostCurrent._main._puntos_evals_residuos_sierras /*int*/ +1);
 };
 //BA.debugLineNum = 1541;BA.debugLine="Main.puntostotales = Main.puntostotales + 1";
mostCurrent._main._puntostotales /*String*/  = BA.NumberToString((double)(Double.parseDouble(mostCurrent._main._puntostotales /*String*/ ))+1);
 //BA.debugLineNum = 1542;BA.debugLine="Main.puntos_evals_residuos = Main.puntos_evals_";
mostCurrent._main._puntos_evals_residuos /*int*/  = (int) (mostCurrent._main._puntos_evals_residuos /*int*/ +1);
 }
};
 } 
       catch (Exception e39) {
			processBA.setLastException(e39); //BA.debugLineNum = 1545;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("43801135",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA)),0);
 };
 //BA.debugLineNum = 1549;BA.debugLine="Try";
try { //BA.debugLineNum = 1551;BA.debugLine="Dim usermarkers_hidro As List";
_usermarkers_hidro = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 1552;BA.debugLine="usermarkers_hidro = Main.user_evals_hidro_list";
_usermarkers_hidro = mostCurrent._main._user_evals_hidro_list /*anywheresoftware.b4a.objects.collections.List*/ ;
 //BA.debugLineNum = 1553;BA.debugLine="For i = 0 To usermarkers_hidro.Size - 1";
{
final int step44 = 1;
final int limit44 = (int) (_usermarkers_hidro.getSize()-1);
_i = (int) (0) ;
for (;_i <= limit44 ;_i = _i + step44 ) {
 //BA.debugLineNum = 1554;BA.debugLine="Dim usermarker As Map";
_usermarker = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 1555;BA.debugLine="usermarker = usermarkers_hidro.Get(i)";
_usermarker = (anywheresoftware.b4a.objects.collections.Map) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.collections.Map(), (java.util.Map)(_usermarkers_hidro.Get(_i)));
 //BA.debugLineNum = 1556;BA.debugLine="If usermarker.Get(\"tiporio\") = \"llanura\" Or use";
if ((_usermarker.Get((Object)("tiporio"))).equals((Object)("llanura")) || (_usermarker.Get((Object)("tiporio"))).equals((Object)("llanura"))) { 
 //BA.debugLineNum = 1557;BA.debugLine="Main.puntos_evals_hidro_llanura = Main.puntos_";
mostCurrent._main._puntos_evals_hidro_llanura /*int*/  = (int) (mostCurrent._main._puntos_evals_hidro_llanura /*int*/ +1);
 }else if((_usermarker.Get((Object)("tiporio"))).equals((Object)("laguna")) || (_usermarker.Get((Object)("tiporio"))).equals((Object)("laguna"))) { 
 //BA.debugLineNum = 1559;BA.debugLine="Main.puntos_evals_hidro_laguna = Main.puntos_e";
mostCurrent._main._puntos_evals_hidro_laguna /*int*/  = (int) (mostCurrent._main._puntos_evals_hidro_laguna /*int*/ +1);
 }else if((_usermarker.Get((Object)("tiporio"))).equals((Object)("sierras")) || (_usermarker.Get((Object)("tiporio"))).equals((Object)("sierras"))) { 
 //BA.debugLineNum = 1561;BA.debugLine="Main.puntos_evals_hidro_sierras = Main.puntos_";
mostCurrent._main._puntos_evals_hidro_sierras /*int*/  = (int) (mostCurrent._main._puntos_evals_hidro_sierras /*int*/ +1);
 };
 //BA.debugLineNum = 1564;BA.debugLine="Main.puntostotales = Main.puntostotales + 1";
mostCurrent._main._puntostotales /*String*/  = BA.NumberToString((double)(Double.parseDouble(mostCurrent._main._puntostotales /*String*/ ))+1);
 //BA.debugLineNum = 1565;BA.debugLine="Main.puntos_evals_hidro = Main.puntos_evals_hid";
mostCurrent._main._puntos_evals_hidro /*int*/  = (int) (mostCurrent._main._puntos_evals_hidro /*int*/ +1);
 }
};
 } 
       catch (Exception e58) {
			processBA.setLastException(e58); //BA.debugLineNum = 1568;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("43801158",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA)),0);
 };
 //BA.debugLineNum = 1571;BA.debugLine="cargarNivel";
_cargarnivel();
 //BA.debugLineNum = 1573;BA.debugLine="End Sub";
return "";
}
public static String  _cargarmedallas() throws Exception{
int _analisishechos = 0;
 //BA.debugLineNum = 1604;BA.debugLine="Sub cargarMedallas";
 //BA.debugLineNum = 1607;BA.debugLine="lstAchievements.Clear";
mostCurrent._lstachievements.Clear();
 //BA.debugLineNum = 1608;BA.debugLine="lstAchievements.TwoLinesAndBitmap.ItemHeight = 13";
mostCurrent._lstachievements.getTwoLinesAndBitmap().setItemHeight(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (130)));
 //BA.debugLineNum = 1609;BA.debugLine="lstAchievements.TwoLinesAndBitmap.ImageView.Left";
mostCurrent._lstachievements.getTwoLinesAndBitmap().ImageView.setLeft(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (0)));
 //BA.debugLineNum = 1610;BA.debugLine="lstAchievements.TwoLinesAndBitmap.ImageView.Gravi";
mostCurrent._lstachievements.getTwoLinesAndBitmap().ImageView.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.FILL);
 //BA.debugLineNum = 1611;BA.debugLine="lstAchievements.TwoLinesAndBitmap.ImageView.Width";
mostCurrent._lstachievements.getTwoLinesAndBitmap().ImageView.setWidth(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (130)));
 //BA.debugLineNum = 1612;BA.debugLine="lstAchievements.TwoLinesAndBitmap.ImageView.Heigh";
mostCurrent._lstachievements.getTwoLinesAndBitmap().ImageView.setHeight(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (130)));
 //BA.debugLineNum = 1613;BA.debugLine="lstAchievements.TwoLinesAndBitmap.Label.Visible =";
mostCurrent._lstachievements.getTwoLinesAndBitmap().Label.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1614;BA.debugLine="lstAchievements.TwoLinesAndBitmap.Secondlabel.Vis";
mostCurrent._lstachievements.getTwoLinesAndBitmap().SecondLabel.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1617;BA.debugLine="Dim analisishechos As Int";
_analisishechos = 0;
 //BA.debugLineNum = 1618;BA.debugLine="analisishechos = Main.puntos_markers + Main.punto";
_analisishechos = (int) (mostCurrent._main._puntos_markers /*int*/ +mostCurrent._main._puntos_evals_residuos /*int*/ +mostCurrent._main._puntos_evals_hidro /*int*/ );
 //BA.debugLineNum = 1620;BA.debugLine="If analisishechos > 1 Then";
if (_analisishechos>1) { 
 //BA.debugLineNum = 1621;BA.debugLine="lstAchievements.AddTwoLinesAndBitmap2(\"Novato\",";
mostCurrent._lstachievements.AddTwoLinesAndBitmap2(BA.ObjectToCharSequence("Novato"),BA.ObjectToCharSequence("Un análisis ambiental"),(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._main._lang /*String*/ +"-novato.png").getObject()),(Object)("Novato"));
 };
 //BA.debugLineNum = 1623;BA.debugLine="If analisishechos >= 3 Then";
if (_analisishechos>=3) { 
 //BA.debugLineNum = 1624;BA.debugLine="lstAchievements.AddTwoLinesAndBitmap2(\"Aprendiz\"";
mostCurrent._lstachievements.AddTwoLinesAndBitmap2(BA.ObjectToCharSequence("Aprendiz"),BA.ObjectToCharSequence("Tres análisis ambientales"),(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._main._lang /*String*/ +"-aprendiz.png").getObject()),(Object)("Aprendiz"));
 };
 //BA.debugLineNum = 1626;BA.debugLine="If analisishechos >= 5 Then";
if (_analisishechos>=5) { 
 //BA.debugLineNum = 1627;BA.debugLine="lstAchievements.AddTwoLinesAndBitmap2(\"Aventurer";
mostCurrent._lstachievements.AddTwoLinesAndBitmap2(BA.ObjectToCharSequence("Aventurero"),BA.ObjectToCharSequence("Cinco análisis ambientales"),(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._main._lang /*String*/ +"-aventurero.png").getObject()),(Object)("Aventurero"));
 };
 //BA.debugLineNum = 1629;BA.debugLine="If analisishechos >= 15 Then";
if (_analisishechos>=15) { 
 //BA.debugLineNum = 1630;BA.debugLine="lstAchievements.AddTwoLinesAndBitmap2(\"Explorado";
mostCurrent._lstachievements.AddTwoLinesAndBitmap2(BA.ObjectToCharSequence("Explorador"),BA.ObjectToCharSequence("Quince análisis ambientales"),(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._main._lang /*String*/ +"-explorador.png").getObject()),(Object)("Explorador"));
 };
 //BA.debugLineNum = 1632;BA.debugLine="If analisishechos >= 50 Then";
if (_analisishechos>=50) { 
 //BA.debugLineNum = 1633;BA.debugLine="lstAchievements.AddTwoLinesAndBitmap2(\"Legendari";
mostCurrent._lstachievements.AddTwoLinesAndBitmap2(BA.ObjectToCharSequence("Legendario"),BA.ObjectToCharSequence("Treinta análisis ambientales!"),(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._main._lang /*String*/ +"-legendario.png").getObject()),(Object)("Legendario"));
 };
 //BA.debugLineNum = 1636;BA.debugLine="If Main.puntos_evals_residuos >= 1 Then";
if (mostCurrent._main._puntos_evals_residuos /*int*/ >=1) { 
 //BA.debugLineNum = 1637;BA.debugLine="lstAchievements.AddTwoLinesAndBitmap2(\"Residuos";
mostCurrent._lstachievements.AddTwoLinesAndBitmap2(BA.ObjectToCharSequence("Residuos 1"),BA.ObjectToCharSequence("Completaste tu primer análisis de residuos"),(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._main._lang /*String*/ +"-residuos-1.png").getObject()),(Object)("residuos-1"));
 };
 //BA.debugLineNum = 1639;BA.debugLine="If Main.puntos_evals_residuos >= 3 Then";
if (mostCurrent._main._puntos_evals_residuos /*int*/ >=3) { 
 //BA.debugLineNum = 1640;BA.debugLine="lstAchievements.AddTwoLinesAndBitmap2(\"Residuos";
mostCurrent._lstachievements.AddTwoLinesAndBitmap2(BA.ObjectToCharSequence("Residuos 2"),BA.ObjectToCharSequence("Completaste tres análisis de residuos"),(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._main._lang /*String*/ +"-residuos-2.png").getObject()),(Object)("residuos-2"));
 };
 //BA.debugLineNum = 1642;BA.debugLine="If Main.puntos_evals_residuos >= 10 Then";
if (mostCurrent._main._puntos_evals_residuos /*int*/ >=10) { 
 //BA.debugLineNum = 1643;BA.debugLine="lstAchievements.AddTwoLinesAndBitmap2(\"Residuos";
mostCurrent._lstachievements.AddTwoLinesAndBitmap2(BA.ObjectToCharSequence("Residuos 3"),BA.ObjectToCharSequence("Completaste diez análisis de residuos!"),(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._main._lang /*String*/ +"-residuos-3.png").getObject()),(Object)("residuos-3"));
 };
 //BA.debugLineNum = 1646;BA.debugLine="If Main.puntos_evals_hidro >= 1 Then";
if (mostCurrent._main._puntos_evals_hidro /*int*/ >=1) { 
 //BA.debugLineNum = 1647;BA.debugLine="lstAchievements.AddTwoLinesAndBitmap2(\"Hidro 1\",";
mostCurrent._lstachievements.AddTwoLinesAndBitmap2(BA.ObjectToCharSequence("Hidro 1"),BA.ObjectToCharSequence("Completaste tu primer análisis de hidro"),(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._main._lang /*String*/ +"-hidro-1.png").getObject()),(Object)("hidro-1"));
 };
 //BA.debugLineNum = 1649;BA.debugLine="If Main.puntos_evals_hidro >= 3 Then";
if (mostCurrent._main._puntos_evals_hidro /*int*/ >=3) { 
 //BA.debugLineNum = 1650;BA.debugLine="lstAchievements.AddTwoLinesAndBitmap2(\"Hidro 2\",";
mostCurrent._lstachievements.AddTwoLinesAndBitmap2(BA.ObjectToCharSequence("Hidro 2"),BA.ObjectToCharSequence("Completaste tres análisis de hidro"),(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._main._lang /*String*/ +"-hidro-2.png").getObject()),(Object)("hidro-2"));
 };
 //BA.debugLineNum = 1652;BA.debugLine="If Main.puntos_evals_hidro >= 10 Then";
if (mostCurrent._main._puntos_evals_hidro /*int*/ >=10) { 
 //BA.debugLineNum = 1653;BA.debugLine="lstAchievements.AddTwoLinesAndBitmap2(\"Hidro 3\",";
mostCurrent._lstachievements.AddTwoLinesAndBitmap2(BA.ObjectToCharSequence("Hidro 3"),BA.ObjectToCharSequence("Completaste diez análisis de hidro!"),(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._main._lang /*String*/ +"-hidro-3.png").getObject()),(Object)("hidro-3"));
 };
 //BA.debugLineNum = 1657;BA.debugLine="If Main.puntos_markers_llanura >= 1 Then";
if (mostCurrent._main._puntos_markers_llanura /*int*/ >=1) { 
 //BA.debugLineNum = 1658;BA.debugLine="lstAchievements.AddTwoLinesAndBitmap2(\"Llanura\"";
mostCurrent._lstachievements.AddTwoLinesAndBitmap2(BA.ObjectToCharSequence("Llanura"),BA.ObjectToCharSequence("Completaste tu primera evaluación de un río de llanura"),(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._main._lang /*String*/ +"-llanura.png").getObject()),(Object)("llanura"));
 };
 //BA.debugLineNum = 1660;BA.debugLine="If Main.puntos_markers_sierras >= 1 Then";
if (mostCurrent._main._puntos_markers_sierras /*int*/ >=1) { 
 //BA.debugLineNum = 1661;BA.debugLine="lstAchievements.AddTwoLinesAndBitmap2(\"Montaña\"";
mostCurrent._lstachievements.AddTwoLinesAndBitmap2(BA.ObjectToCharSequence("Montaña"),BA.ObjectToCharSequence("Completaste tu primera evaluación de un río de sierras de Córdoba"),(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._main._lang /*String*/ +"-montana.png").getObject()),(Object)("montana"));
 };
 //BA.debugLineNum = 1663;BA.debugLine="If Main.puntos_markers_laguna >= 1 Then";
if (mostCurrent._main._puntos_markers_laguna /*int*/ >=1) { 
 //BA.debugLineNum = 1664;BA.debugLine="lstAchievements.AddTwoLinesAndBitmap2(\"Laguna\",";
mostCurrent._lstachievements.AddTwoLinesAndBitmap2(BA.ObjectToCharSequence("Laguna"),BA.ObjectToCharSequence("Completaste tu primera evaluación de una laguna"),(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._main._lang /*String*/ +"-laguna.png").getObject()),(Object)("laguna"));
 };
 //BA.debugLineNum = 1666;BA.debugLine="If Main.puntos_markers_laguna >= 1 And Main.numes";
if (mostCurrent._main._puntos_markers_laguna /*int*/ >=1 && (double)(Double.parseDouble(mostCurrent._main._numestuario /*String*/ ))>=1 && mostCurrent._main._puntos_markers_sierras /*int*/ >=1 && mostCurrent._main._puntos_markers_llanura /*int*/ >=1) { 
 //BA.debugLineNum = 1667;BA.debugLine="lstAchievements.AddTwoLinesAndBitmap2(\"Maestro";
mostCurrent._lstachievements.AddTwoLinesAndBitmap2(BA.ObjectToCharSequence("Maestro de todos los ambientes"),BA.ObjectToCharSequence("Completaste una evaluación de cada ambiente"),(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._main._lang /*String*/ +"-maestrodeambientes.png").getObject()),(Object)("maestrodeambientes"));
 };
 //BA.debugLineNum = 1669;BA.debugLine="update_perfil";
_update_perfil();
 //BA.debugLineNum = 1670;BA.debugLine="End Sub";
return "";
}
public static String  _cargarnivel() throws Exception{
anywheresoftware.b4a.objects.drawable.ColorDrawable _gd = null;
anywheresoftware.b4a.objects.drawable.ColorDrawable _cd = null;
double _nivelfull = 0;
int _nivela = 0;
double _resto = 0;
int _nivelsiguiente = 0;
 //BA.debugLineNum = 1577;BA.debugLine="Sub cargarNivel";
 //BA.debugLineNum = 1580;BA.debugLine="Dim gd As ColorDrawable";
_gd = new anywheresoftware.b4a.objects.drawable.ColorDrawable();
 //BA.debugLineNum = 1581;BA.debugLine="Dim cd As ColorDrawable";
_cd = new anywheresoftware.b4a.objects.drawable.ColorDrawable();
 //BA.debugLineNum = 1582;BA.debugLine="gd.Initialize(Colors.DarkGray,10dip)";
_gd.Initialize(anywheresoftware.b4a.keywords.Common.Colors.DarkGray,anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10)));
 //BA.debugLineNum = 1583;BA.debugLine="cd.Initialize(Colors.LightGray, 10dip)";
_cd.Initialize(anywheresoftware.b4a.keywords.Common.Colors.LightGray,anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10)));
 //BA.debugLineNum = 1584;BA.debugLine="utilidades.SetProgressDrawable(pgbLevel, gd, cd)";
mostCurrent._utilidades._setprogressdrawable /*String*/ (mostCurrent.activityBA,mostCurrent._pgblevel,(Object)(_gd.getObject()),(Object)(_cd.getObject()));
 //BA.debugLineNum = 1587;BA.debugLine="Dim nivelfull As Double";
_nivelfull = 0;
 //BA.debugLineNum = 1588;BA.debugLine="Dim nivela As Int";
_nivela = 0;
 //BA.debugLineNum = 1589;BA.debugLine="Dim resto As Double";
_resto = 0;
 //BA.debugLineNum = 1590;BA.debugLine="Dim nivelsiguiente As Int";
_nivelsiguiente = 0;
 //BA.debugLineNum = 1592;BA.debugLine="nivelfull = 0.5 + (0.5*Sqrt(1+8*Main.puntostotale";
_nivelfull = 0.5+(0.5*anywheresoftware.b4a.keywords.Common.Sqrt(1+8*(double)(Double.parseDouble(mostCurrent._main._puntostotales /*String*/ ))));
 //BA.debugLineNum = 1593;BA.debugLine="nivela = Floor(nivelfull)";
_nivela = (int) (anywheresoftware.b4a.keywords.Common.Floor(_nivelfull));
 //BA.debugLineNum = 1594;BA.debugLine="nivelsiguiente = nivela + 1";
_nivelsiguiente = (int) (_nivela+1);
 //BA.debugLineNum = 1595;BA.debugLine="resto = Round2(Abs(nivelsiguiente - nivelfull) *";
_resto = anywheresoftware.b4a.keywords.Common.Round2(anywheresoftware.b4a.keywords.Common.Abs(_nivelsiguiente-_nivelfull)*100,(int) (0));
 //BA.debugLineNum = 1596;BA.debugLine="pgbLevel.Progress = 100 - resto";
mostCurrent._pgblevel.setProgress((int) (100-_resto));
 //BA.debugLineNum = 1597;BA.debugLine="lblNivel.Text = nivela";
mostCurrent._lblnivel.setText(BA.ObjectToCharSequence(_nivela));
 //BA.debugLineNum = 1598;BA.debugLine="lblPerfil_puntos_totales.Text = \"Nivel \" & nivela";
mostCurrent._lblperfil_puntos_totales.setText(BA.ObjectToCharSequence("Nivel "+BA.NumberToString(_nivela)));
 //BA.debugLineNum = 1599;BA.debugLine="nivel = nivela";
mostCurrent._nivel = BA.NumberToString(_nivela);
 //BA.debugLineNum = 1601;BA.debugLine="cargarMedallas";
_cargarmedallas();
 //BA.debugLineNum = 1603;BA.debugLine="End Sub";
return "";
}
public static void  _closeappmsgbox() throws Exception{
ResumableSub_closeAppMsgBox rsub = new ResumableSub_closeAppMsgBox(null);
rsub.resume(processBA, null);
}
public static class ResumableSub_closeAppMsgBox extends BA.ResumableSub {
public ResumableSub_closeAppMsgBox(appear.pnud.preservamos.form_main parent) {
this.parent = parent;
}
appear.pnud.preservamos.form_main parent;
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
 //BA.debugLineNum = 245;BA.debugLine="Msgbox2Async(\"Cerrar PreserVamos?\", \"SALIR\", \"Si\"";
anywheresoftware.b4a.keywords.Common.Msgbox2Async(BA.ObjectToCharSequence("Cerrar PreserVamos?"),BA.ObjectToCharSequence("SALIR"),"Si","","No",(anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper(), (android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null)),processBA,anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 246;BA.debugLine="Wait For Msgbox_Result (Result As Int)";
anywheresoftware.b4a.keywords.Common.WaitFor("msgbox_result", processBA, this, null);
this.state = 5;
return;
case 5:
//C
this.state = 1;
_result = (Integer) result[0];
;
 //BA.debugLineNum = 247;BA.debugLine="If Result = DialogResponse.POSITIVE Then";
if (true) break;

case 1:
//if
this.state = 4;
if (_result==anywheresoftware.b4a.keywords.Common.DialogResponse.POSITIVE) { 
this.state = 3;
}if (true) break;

case 3:
//C
this.state = 4;
 //BA.debugLineNum = 248;BA.debugLine="Activity.RemoveAllViews";
parent.mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 249;BA.debugLine="Activity.finish";
parent.mostCurrent._activity.Finish();
 //BA.debugLineNum = 250;BA.debugLine="ExitApplication";
anywheresoftware.b4a.keywords.Common.ExitApplication();
 if (true) break;

case 4:
//C
this.state = -1;
;
 //BA.debugLineNum = 252;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public static String  _crearmenu() throws Exception{
 //BA.debugLineNum = 260;BA.debugLine="Sub CrearMenu";
 //BA.debugLineNum = 262;BA.debugLine="Drawer.Initialize(Me, \"Drawer\", Activity, 300dip)";
mostCurrent._drawer._initialize /*String*/ (mostCurrent.activityBA,form_main.getObject(),"Drawer",(anywheresoftware.b4a.objects.B4XViewWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.B4XViewWrapper(), (java.lang.Object)(mostCurrent._activity.getObject())),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (300)));
 //BA.debugLineNum = 263;BA.debugLine="Drawer.CenterPanel.LoadLayout(\"layMain\")";
mostCurrent._drawer._getcenterpanel /*anywheresoftware.b4a.objects.B4XViewWrapper*/ ().LoadLayout("layMain",mostCurrent.activityBA);
 //BA.debugLineNum = 264;BA.debugLine="Drawer.LeftPanel.LoadLayout(\"frmSideNav\")";
mostCurrent._drawer._getleftpanel /*anywheresoftware.b4a.objects.B4XViewWrapper*/ ().LoadLayout("frmSideNav",mostCurrent.activityBA);
 //BA.debugLineNum = 266;BA.debugLine="If Main.username = \"guest\" Or Main.username = \"No";
if ((mostCurrent._main._username /*String*/ ).equals("guest") || (mostCurrent._main._username /*String*/ ).equals("None") || (mostCurrent._main._username /*String*/ ).equals("")) { 
 //BA.debugLineNum = 267;BA.debugLine="btnCerrarSesion.Text = \"Iniciar sesión\"";
mostCurrent._btncerrarsesion.setText(BA.ObjectToCharSequence("Iniciar sesión"));
 //BA.debugLineNum = 268;BA.debugLine="lblUserName.Visible = False";
mostCurrent._lblusername.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 269;BA.debugLine="lblRegistrate.Visible = True";
mostCurrent._lblregistrate.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 270;BA.debugLine="btnEditUser.Visible = False";
mostCurrent._btnedituser.setVisible(anywheresoftware.b4a.keywords.Common.False);
 }else {
 //BA.debugLineNum = 272;BA.debugLine="lblUserName.Text = Main.username";
mostCurrent._lblusername.setText(BA.ObjectToCharSequence(mostCurrent._main._username /*String*/ ));
 //BA.debugLineNum = 273;BA.debugLine="btnCerrarSesion.Text = \"Cerrar sesión\"";
mostCurrent._btncerrarsesion.setText(BA.ObjectToCharSequence("Cerrar sesión"));
 //BA.debugLineNum = 274;BA.debugLine="lblRegistrate.Visible = False";
mostCurrent._lblregistrate.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 275;BA.debugLine="btnEditUser.Visible = True";
mostCurrent._btnedituser.setVisible(anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 277;BA.debugLine="End Sub";
return "";
}
public static String  _fondogris_click() throws Exception{
 //BA.debugLineNum = 992;BA.debugLine="Private Sub fondogris_Click";
 //BA.debugLineNum = 993;BA.debugLine="panelComoFunciona.RemoveView";
mostCurrent._panelcomofunciona.RemoveView();
 //BA.debugLineNum = 994;BA.debugLine="fondogris.RemoveView";
mostCurrent._fondogris.RemoveView();
 //BA.debugLineNum = 995;BA.debugLine="End Sub";
return "";
}
public static String  _fondogris_share_click() throws Exception{
 //BA.debugLineNum = 1882;BA.debugLine="Sub fondogris_share_Click";
 //BA.debugLineNum = 1883;BA.debugLine="pnlShare.RemoveView";
mostCurrent._pnlshare.RemoveView();
 //BA.debugLineNum = 1884;BA.debugLine="fondogris_share.RemoveView";
mostCurrent._fondogris_share.RemoveView();
 //BA.debugLineNum = 1885;BA.debugLine="End Sub";
return "";
}
public static void  _getpartidos_complete(appear.pnud.preservamos.httpjob _job) throws Exception{
ResumableSub_getPartidos_Complete rsub = new ResumableSub_getPartidos_Complete(null,_job);
rsub.resume(processBA, null);
}
public static class ResumableSub_getPartidos_Complete extends BA.ResumableSub {
public ResumableSub_getPartidos_Complete(appear.pnud.preservamos.form_main parent,appear.pnud.preservamos.httpjob _job) {
this.parent = parent;
this._job = _job;
}
appear.pnud.preservamos.form_main parent;
appear.pnud.preservamos.httpjob _job;
String _act = "";
anywheresoftware.b4a.objects.collections.JSONParser _municipios_parser = null;
anywheresoftware.b4a.objects.collections.Map _nd = null;
int _i = 0;
anywheresoftware.b4a.objects.collections.Map _newpunto = null;
int _j = 0;
int _result = 0;
int step67;
int limit67;
int step85;
int limit85;
int step144;
int limit144;
int step162;
int limit162;

@Override
public void resume(BA ba, Object[] result) throws Exception{

    while (true) {
        switch (state) {
            case -1:
return;

case 0:
//C
this.state = 1;
 //BA.debugLineNum = 1052;BA.debugLine="Log(\"Job completed: \" & Job.Success)";
anywheresoftware.b4a.keywords.Common.LogImpl("43014657","Job completed: "+BA.ObjectToString(_job._success /*boolean*/ ),0);
 //BA.debugLineNum = 1053;BA.debugLine="If Job.Success = True Then";
if (true) break;

case 1:
//if
this.state = 101;
if (_job._success /*boolean*/ ==anywheresoftware.b4a.keywords.Common.True) { 
this.state = 3;
}else {
this.state = 96;
}if (true) break;

case 3:
//C
this.state = 4;
 //BA.debugLineNum = 1054;BA.debugLine="Log(\"Partido buscado\")";
anywheresoftware.b4a.keywords.Common.LogImpl("43014659","Partido buscado",0);
 //BA.debugLineNum = 1055;BA.debugLine="Dim act As String";
_act = "";
 //BA.debugLineNum = 1056;BA.debugLine="Municipios_parser_string = Job.GetString";
parent.mostCurrent._municipios_parser_string = _job._getstring /*String*/ ();
 //BA.debugLineNum = 1057;BA.debugLine="Dim Municipios_parser As JSONParser 'para enviar";
_municipios_parser = new anywheresoftware.b4a.objects.collections.JSONParser();
 //BA.debugLineNum = 1058;BA.debugLine="Municipios_parser.Initialize(Municipios_parser_s";
_municipios_parser.Initialize(parent.mostCurrent._municipios_parser_string);
 //BA.debugLineNum = 1059;BA.debugLine="act = Municipios_parser.NextValue";
_act = BA.ObjectToString(_municipios_parser.NextValue());
 //BA.debugLineNum = 1060;BA.debugLine="If act = \"Partido OK\" Then";
if (true) break;

case 4:
//if
this.state = 94;
if ((_act).equals("Partido OK")) { 
this.state = 6;
}else {
this.state = 64;
}if (true) break;

case 6:
//C
this.state = 7;
 //BA.debugLineNum = 1061;BA.debugLine="ToastMessageShow(\"Cargando información del muni";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Cargando información del municipio"),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1062;BA.debugLine="btnCambiarMunicipio.Text = \"Cambiar municipio\"";
parent.mostCurrent._btncambiarmunicipio.setText(BA.ObjectToCharSequence("Cambiar municipio"));
 //BA.debugLineNum = 1063;BA.debugLine="Log(\"Partido existente\")";
anywheresoftware.b4a.keywords.Common.LogImpl("43014668","Partido existente",0);
 //BA.debugLineNum = 1064;BA.debugLine="Dim nd As Map";
_nd = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 1065;BA.debugLine="nd = Municipios_parser.NextObject";
_nd = _municipios_parser.NextObject();
 //BA.debugLineNum = 1066;BA.debugLine="lblMunicipio_texto_principal.Clear";
parent.mostCurrent._lblmunicipio_texto_principal._clear();
 //BA.debugLineNum = 1067;BA.debugLine="lblMunicipio_texto_principal.AddTextItem(nd.Get";
parent.mostCurrent._lblmunicipio_texto_principal._addtextitem(_nd.Get((Object)("texto_principal")),(Object)(0));
 //BA.debugLineNum = 1068;BA.debugLine="If nd.Get(\"texto_principal\") = \"\" Then";
if (true) break;

case 7:
//if
this.state = 10;
if ((_nd.Get((Object)("texto_principal"))).equals((Object)(""))) { 
this.state = 9;
}if (true) break;

case 9:
//C
this.state = 10;
 //BA.debugLineNum = 1069;BA.debugLine="lblMunicipio_texto_principal.AddTextItem(\"Este";
parent.mostCurrent._lblmunicipio_texto_principal._addtextitem((Object)("Este municipio aún no ha incorporado una descripción de sus políticas ambientales."),(Object)(0));
 if (true) break;
;
 //BA.debugLineNum = 1072;BA.debugLine="If nd.Get(\"texto_secundario\") <> \"\" Then";

case 10:
//if
this.state = 13;
if ((_nd.Get((Object)("texto_secundario"))).equals((Object)("")) == false) { 
this.state = 12;
}if (true) break;

case 12:
//C
this.state = 13;
 //BA.debugLineNum = 1073;BA.debugLine="lblMunicipio_texto_principal.AddTextItem(nd.Ge";
parent.mostCurrent._lblmunicipio_texto_principal._addtextitem(_nd.Get((Object)("texto_secundario")),(Object)(0));
 if (true) break;

case 13:
//C
this.state = 14;
;
 //BA.debugLineNum = 1075;BA.debugLine="lblMunicipio_puntos_totales.Text = nd.Get(\"punt";
parent.mostCurrent._lblmunicipio_puntos_totales.setText(BA.ObjectToCharSequence(_nd.Get((Object)("puntos_totales"))));
 //BA.debugLineNum = 1076;BA.debugLine="lblMunicipio_puntos_totales.Visible = True";
parent.mostCurrent._lblmunicipio_puntos_totales.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1077;BA.debugLine="btnVerDatosMunicipio.Visible = True";
parent.mostCurrent._btnverdatosmunicipio.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1078;BA.debugLine="btnVerEstadisticasMunicipio.Visible = True";
parent.mostCurrent._btnverestadisticasmunicipio.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1080;BA.debugLine="If nd.Get(\"link1\") <> \"\" Then";
if (true) break;

case 14:
//if
this.state = 19;
if ((_nd.Get((Object)("link1"))).equals((Object)("")) == false) { 
this.state = 16;
}else {
this.state = 18;
}if (true) break;

case 16:
//C
this.state = 19;
 //BA.debugLineNum = 1081;BA.debugLine="btnMasInfoMunicipio.Visible = True";
parent.mostCurrent._btnmasinfomunicipio.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1082;BA.debugLine="municipioURL = nd.Get(\"link1\")";
parent.mostCurrent._municipiourl = BA.ObjectToString(_nd.Get((Object)("link1")));
 if (true) break;

case 18:
//C
this.state = 19;
 //BA.debugLineNum = 1084;BA.debugLine="btnMasInfoMunicipio.Visible = False";
parent.mostCurrent._btnmasinfomunicipio.setVisible(anywheresoftware.b4a.keywords.Common.False);
 if (true) break;
;
 //BA.debugLineNum = 1087;BA.debugLine="If nd.Get(\"link2\") <> \"\" Then";

case 19:
//if
this.state = 24;
if ((_nd.Get((Object)("link2"))).equals((Object)("")) == false) { 
this.state = 21;
}else {
this.state = 23;
}if (true) break;

case 21:
//C
this.state = 24;
 //BA.debugLineNum = 1088;BA.debugLine="btnMunicipioFB.Visible = True";
parent.mostCurrent._btnmunicipiofb.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1089;BA.debugLine="municipioFB = nd.Get(\"link2\")";
parent.mostCurrent._municipiofb = BA.ObjectToString(_nd.Get((Object)("link2")));
 if (true) break;

case 23:
//C
this.state = 24;
 //BA.debugLineNum = 1091;BA.debugLine="btnMunicipioFB.Visible = False";
parent.mostCurrent._btnmunicipiofb.setVisible(anywheresoftware.b4a.keywords.Common.False);
 if (true) break;
;
 //BA.debugLineNum = 1094;BA.debugLine="If nd.Get(\"link3\") <> \"\" Then";

case 24:
//if
this.state = 29;
if ((_nd.Get((Object)("link3"))).equals((Object)("")) == false) { 
this.state = 26;
}else {
this.state = 28;
}if (true) break;

case 26:
//C
this.state = 29;
 //BA.debugLineNum = 1095;BA.debugLine="btnMunicipioIG.Visible = True";
parent.mostCurrent._btnmunicipioig.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1096;BA.debugLine="municipioIG = nd.Get(\"link3\")";
parent.mostCurrent._municipioig = BA.ObjectToString(_nd.Get((Object)("link3")));
 if (true) break;

case 28:
//C
this.state = 29;
 //BA.debugLineNum = 1098;BA.debugLine="btnMunicipioIG.Visible = False";
parent.mostCurrent._btnmunicipioig.setVisible(anywheresoftware.b4a.keywords.Common.False);
 if (true) break;
;
 //BA.debugLineNum = 1101;BA.debugLine="If nd.Get(\"link4\") <> \"\" Then";

case 29:
//if
this.state = 34;
if ((_nd.Get((Object)("link4"))).equals((Object)("")) == false) { 
this.state = 31;
}else {
this.state = 33;
}if (true) break;

case 31:
//C
this.state = 34;
 //BA.debugLineNum = 1102;BA.debugLine="btnMunicipioTW.Visible = True";
parent.mostCurrent._btnmunicipiotw.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1103;BA.debugLine="municipioTW = nd.Get(\"link4\")";
parent.mostCurrent._municipiotw = BA.ObjectToString(_nd.Get((Object)("link4")));
 if (true) break;

case 33:
//C
this.state = 34;
 //BA.debugLineNum = 1105;BA.debugLine="btnMunicipioTW.Visible = False";
parent.mostCurrent._btnmunicipiotw.setVisible(anywheresoftware.b4a.keywords.Common.False);
 if (true) break;
;
 //BA.debugLineNum = 1109;BA.debugLine="If Municipios_parser.NextValue = \"Con reportes\"";

case 34:
//if
this.state = 62;
if ((_municipios_parser.NextValue()).equals((Object)("Con reportes"))) { 
this.state = 36;
}else {
this.state = 61;
}if (true) break;

case 36:
//C
this.state = 37;
 //BA.debugLineNum = 1110;BA.debugLine="Log(\"Con reportes\")";
anywheresoftware.b4a.keywords.Common.LogImpl("43014715","Con reportes",0);
 //BA.debugLineNum = 1111;BA.debugLine="Municipios_numresults = Municipios_parser.Next";
parent._municipios_numresults = (int)(BA.ObjectToNumber(_municipios_parser.NextValue()));
 //BA.debugLineNum = 1112;BA.debugLine="lblMunicipio_puntos_totales.Text = Municipios_";
parent.mostCurrent._lblmunicipio_puntos_totales.setText(BA.ObjectToCharSequence(parent._municipios_numresults));
 //BA.debugLineNum = 1113;BA.debugLine="Log(\"Marcadores en el municipio: \" & Municipio";
anywheresoftware.b4a.keywords.Common.LogImpl("43014718","Marcadores en el municipio: "+BA.NumberToString(parent._municipios_numresults),0);
 //BA.debugLineNum = 1117;BA.debugLine="listaPromedio.Initialize";
parent.mostCurrent._listapromedio.Initialize();
 //BA.debugLineNum = 1118;BA.debugLine="listaHidro_Promedio.Initialize";
parent.mostCurrent._listahidro_promedio.Initialize();
 //BA.debugLineNum = 1119;BA.debugLine="listaAgua_Promedio.Initialize";
parent.mostCurrent._listaagua_promedio.Initialize();
 //BA.debugLineNum = 1120;BA.debugLine="listaBio_Promedio.Initialize";
parent.mostCurrent._listabio_promedio.Initialize();
 //BA.debugLineNum = 1121;BA.debugLine="listaUsos_Promedio.Initialize";
parent.mostCurrent._listausos_promedio.Initialize();
 //BA.debugLineNum = 1122;BA.debugLine="listaExoticas_Promedio.Initialize";
parent.mostCurrent._listaexoticas_promedio.Initialize();
 //BA.debugLineNum = 1125;BA.debugLine="Dim hidroPromedio As Double";
parent._hidropromedio = 0;
 //BA.debugLineNum = 1126;BA.debugLine="Dim aguaPromedio As Double";
parent._aguapromedio = 0;
 //BA.debugLineNum = 1127;BA.debugLine="Dim bioPromedio As Double";
parent._biopromedio = 0;
 //BA.debugLineNum = 1128;BA.debugLine="Dim UsosPromedio As Double";
parent._usospromedio = 0;
 //BA.debugLineNum = 1129;BA.debugLine="Dim ExoticasPromedio As Double";
parent._exoticaspromedio = 0;
 //BA.debugLineNum = 1132;BA.debugLine="For i = 0 To Municipios_numresults - 1";
if (true) break;

case 37:
//for
this.state = 40;
step67 = 1;
limit67 = (int) (parent._municipios_numresults-1);
_i = (int) (0) ;
this.state = 102;
if (true) break;

case 102:
//C
this.state = 40;
if ((step67 > 0 && _i <= limit67) || (step67 < 0 && _i >= limit67)) this.state = 39;
if (true) break;

case 103:
//C
this.state = 102;
_i = ((int)(0 + _i + step67)) ;
if (true) break;

case 39:
//C
this.state = 103;
 //BA.debugLineNum = 1134;BA.debugLine="Dim newpunto As Map";
_newpunto = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 1135;BA.debugLine="newpunto = Municipios_parser.NextObject";
_newpunto = _municipios_parser.NextObject();
 //BA.debugLineNum = 1136;BA.debugLine="listaPromedio.Add(newpunto.Get(\"indice\"))";
parent.mostCurrent._listapromedio.Add(_newpunto.Get((Object)("indice")));
 //BA.debugLineNum = 1137;BA.debugLine="listaHidro_Promedio.Add(newpunto.Get(\"valorin";
parent.mostCurrent._listahidro_promedio.Add(_newpunto.Get((Object)("valorind9")));
 //BA.debugLineNum = 1138;BA.debugLine="listaHidro_Promedio.Add(newpunto.Get(\"valorin";
parent.mostCurrent._listahidro_promedio.Add(_newpunto.Get((Object)("valorind10")));
 //BA.debugLineNum = 1139;BA.debugLine="listaAgua_Promedio.Add(newpunto.Get(\"valorind";
parent.mostCurrent._listaagua_promedio.Add(_newpunto.Get((Object)("valorind5")));
 //BA.debugLineNum = 1140;BA.debugLine="listaAgua_Promedio.Add(newpunto.Get(\"valorind";
parent.mostCurrent._listaagua_promedio.Add(_newpunto.Get((Object)("valorind6")));
 //BA.debugLineNum = 1141;BA.debugLine="listaAgua_Promedio.Add(newpunto.Get(\"valorind";
parent.mostCurrent._listaagua_promedio.Add(_newpunto.Get((Object)("valorind7")));
 //BA.debugLineNum = 1142;BA.debugLine="listaAgua_Promedio.Add(newpunto.Get(\"ind_pvm_";
parent.mostCurrent._listaagua_promedio.Add(_newpunto.Get((Object)("ind_pvm_1")));
 //BA.debugLineNum = 1143;BA.debugLine="listaBio_Promedio.Add(newpunto.Get(\"ind_pvm_2";
parent.mostCurrent._listabio_promedio.Add(_newpunto.Get((Object)("ind_pvm_2")));
 //BA.debugLineNum = 1144;BA.debugLine="listaBio_Promedio.Add(newpunto.Get(\"valorind3";
parent.mostCurrent._listabio_promedio.Add(_newpunto.Get((Object)("valorind3")));
 //BA.debugLineNum = 1145;BA.debugLine="listaBio_Promedio.Add(newpunto.Get(\"valorind4";
parent.mostCurrent._listabio_promedio.Add(_newpunto.Get((Object)("valorind4")));
 //BA.debugLineNum = 1146;BA.debugLine="listaUsos_Promedio.Add(newpunto.Get(\"valorind";
parent.mostCurrent._listausos_promedio.Add(_newpunto.Get((Object)("valorind1")));
 //BA.debugLineNum = 1147;BA.debugLine="listaUsos_Promedio.Add(newpunto.Get(\"valorind";
parent.mostCurrent._listausos_promedio.Add(_newpunto.Get((Object)("valorind2")));
 //BA.debugLineNum = 1148;BA.debugLine="listaExoticas_Promedio.Add(newpunto.Get(\"ind_";
parent.mostCurrent._listaexoticas_promedio.Add(_newpunto.Get((Object)("ind_pvm_3")));
 //BA.debugLineNum = 1149;BA.debugLine="listaExoticas_Promedio.Add(newpunto.Get(\"ind_";
parent.mostCurrent._listaexoticas_promedio.Add(_newpunto.Get((Object)("ind_pvm_4")));
 if (true) break;
if (true) break;
;
 //BA.debugLineNum = 1161;BA.debugLine="For j = 0 To listaPromedio.Size - 1";

case 40:
//for
this.state = 47;
step85 = 1;
limit85 = (int) (parent.mostCurrent._listapromedio.getSize()-1);
_j = (int) (0) ;
this.state = 104;
if (true) break;

case 104:
//C
this.state = 47;
if ((step85 > 0 && _j <= limit85) || (step85 < 0 && _j >= limit85)) this.state = 42;
if (true) break;

case 105:
//C
this.state = 104;
_j = ((int)(0 + _j + step85)) ;
if (true) break;

case 42:
//C
this.state = 43;
 //BA.debugLineNum = 1162;BA.debugLine="If listaPromedio.Get(j) <> \"\" Then";
if (true) break;

case 43:
//if
this.state = 46;
if ((parent.mostCurrent._listapromedio.Get(_j)).equals((Object)("")) == false) { 
this.state = 45;
}if (true) break;

case 45:
//C
this.state = 46;
 //BA.debugLineNum = 1163;BA.debugLine="valorEstado = valorEstado + listaPromedio.Ge";
parent._valorestado = parent._valorestado+(double)(BA.ObjectToNumber(parent.mostCurrent._listapromedio.Get(_j)));
 if (true) break;

case 46:
//C
this.state = 105;
;
 if (true) break;
if (true) break;

case 47:
//C
this.state = 48;
;
 //BA.debugLineNum = 1166;BA.debugLine="valorEstado = Round2(valorEstado / listaPromed";
parent._valorestado = anywheresoftware.b4a.keywords.Common.Round2(parent._valorestado/(double)parent.mostCurrent._listapromedio.getSize(),(int) (2));
 //BA.debugLineNum = 1167;BA.debugLine="Log(\"valorEstado: \" & valorEstado)";
anywheresoftware.b4a.keywords.Common.LogImpl("43014772","valorEstado: "+BA.NumberToString(parent._valorestado),0);
 //BA.debugLineNum = 1172;BA.debugLine="gauge_Partido.mBase.Visible = True";
parent.mostCurrent._gauge_partido._mbase /*anywheresoftware.b4a.objects.B4XViewWrapper*/ .setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1173;BA.debugLine="lblMunicipios_reportes.Visible = True";
parent.mostCurrent._lblmunicipios_reportes.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1174;BA.debugLine="gauge_Partido.SetRanges(Array(gauge_Partido.Cr";
parent.mostCurrent._gauge_partido._setranges /*String*/ (anywheresoftware.b4a.keywords.Common.ArrayToList(new Object[]{(Object)(parent.mostCurrent._gauge_partido._createrange /*appear.pnud.preservamos.gauge._gaugerange*/ ((float) (0),(float) (20),parent.mostCurrent._xui.Color_DarkGray)),(Object)(parent.mostCurrent._gauge_partido._createrange /*appear.pnud.preservamos.gauge._gaugerange*/ ((float) (20),(float) (40),parent.mostCurrent._xui.Color_Red)),(Object)(parent.mostCurrent._gauge_partido._createrange /*appear.pnud.preservamos.gauge._gaugerange*/ ((float) (40),(float) (60),parent.mostCurrent._xui.Color_Yellow)),(Object)(parent.mostCurrent._gauge_partido._createrange /*appear.pnud.preservamos.gauge._gaugerange*/ ((float) (60),(float) (80),parent.mostCurrent._xui.Color_Green)),(Object)(parent.mostCurrent._gauge_partido._createrange /*appear.pnud.preservamos.gauge._gaugerange*/ ((float) (80),(float) (100),parent.mostCurrent._xui.Color_Blue))}));
 //BA.debugLineNum = 1175;BA.debugLine="gauge_Partido.CurrentValue = 0";
parent.mostCurrent._gauge_partido._setcurrentvalue /*float*/ ((float) (0));
 //BA.debugLineNum = 1176;BA.debugLine="gauge_Partido.CurrentValue = valorEstado";
parent.mostCurrent._gauge_partido._setcurrentvalue /*float*/ ((float) (parent._valorestado));
 //BA.debugLineNum = 1178;BA.debugLine="If valorEstado < 20 Then";
if (true) break;

case 48:
//if
this.state = 59;
if (parent._valorestado<20) { 
this.state = 50;
}else if(parent._valorestado>=20 && parent._valorestado<40) { 
this.state = 52;
}else if(parent._valorestado>=40 && parent._valorestado<60) { 
this.state = 54;
}else if(parent._valorestado>=60 && parent._valorestado<80) { 
this.state = 56;
}else if(parent._valorestado>=80) { 
this.state = 58;
}if (true) break;

case 50:
//C
this.state = 59;
 //BA.debugLineNum = 1179;BA.debugLine="lblMunicipio_estado.Text = \"Estado general: M";
parent.mostCurrent._lblmunicipio_estado.setText(BA.ObjectToCharSequence("Estado general: Muy malo"));
 if (true) break;

case 52:
//C
this.state = 59;
 //BA.debugLineNum = 1181;BA.debugLine="lblMunicipio_estado.Text = \"Estado general: M";
parent.mostCurrent._lblmunicipio_estado.setText(BA.ObjectToCharSequence("Estado general: Malo"));
 if (true) break;

case 54:
//C
this.state = 59;
 //BA.debugLineNum = 1183;BA.debugLine="lblMunicipio_estado.Text = \"Estado general: R";
parent.mostCurrent._lblmunicipio_estado.setText(BA.ObjectToCharSequence("Estado general: Regular"));
 if (true) break;

case 56:
//C
this.state = 59;
 //BA.debugLineNum = 1185;BA.debugLine="lblMunicipio_estado.Text = \"Estado general: B";
parent.mostCurrent._lblmunicipio_estado.setText(BA.ObjectToCharSequence("Estado general: Bueno"));
 if (true) break;

case 58:
//C
this.state = 59;
 //BA.debugLineNum = 1187;BA.debugLine="lblMunicipio_estado.Text = \"Estado general: M";
parent.mostCurrent._lblmunicipio_estado.setText(BA.ObjectToCharSequence("Estado general: Muy bueno!"));
 if (true) break;

case 59:
//C
this.state = 62;
;
 //BA.debugLineNum = 1189;BA.debugLine="lblMunicipio_estado.Text = lblMunicipio_estado";
parent.mostCurrent._lblmunicipio_estado.setText(BA.ObjectToCharSequence(parent.mostCurrent._lblmunicipio_estado.getText()));
 if (true) break;

case 61:
//C
this.state = 62;
 //BA.debugLineNum = 1191;BA.debugLine="Log(\"No tiene reportes\")";
anywheresoftware.b4a.keywords.Common.LogImpl("43014796","No tiene reportes",0);
 //BA.debugLineNum = 1192;BA.debugLine="btnVerDatosMunicipio.Visible = False";
parent.mostCurrent._btnverdatosmunicipio.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1193;BA.debugLine="btnVerEstadisticasMunicipio.Visible = False";
parent.mostCurrent._btnverestadisticasmunicipio.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1194;BA.debugLine="lblMunicipio_puntos_totales.Visible = False";
parent.mostCurrent._lblmunicipio_puntos_totales.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1195;BA.debugLine="lblMunicipio_estado.Text = \"Estado general: De";
parent.mostCurrent._lblmunicipio_estado.setText(BA.ObjectToCharSequence("Estado general: Desconocido"));
 //BA.debugLineNum = 1197;BA.debugLine="gauge_Partido.mBase.Visible = True";
parent.mostCurrent._gauge_partido._mbase /*anywheresoftware.b4a.objects.B4XViewWrapper*/ .setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1198;BA.debugLine="lblMunicipios_reportes.Visible = True";
parent.mostCurrent._lblmunicipios_reportes.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1199;BA.debugLine="gauge_Partido.SetRanges(Array(gauge_Partido.Cr";
parent.mostCurrent._gauge_partido._setranges /*String*/ (anywheresoftware.b4a.keywords.Common.ArrayToList(new Object[]{(Object)(parent.mostCurrent._gauge_partido._createrange /*appear.pnud.preservamos.gauge._gaugerange*/ ((float) (0),(float) (20),parent.mostCurrent._xui.Color_DarkGray)),(Object)(parent.mostCurrent._gauge_partido._createrange /*appear.pnud.preservamos.gauge._gaugerange*/ ((float) (20),(float) (40),parent.mostCurrent._xui.Color_Red)),(Object)(parent.mostCurrent._gauge_partido._createrange /*appear.pnud.preservamos.gauge._gaugerange*/ ((float) (40),(float) (60),parent.mostCurrent._xui.Color_Yellow)),(Object)(parent.mostCurrent._gauge_partido._createrange /*appear.pnud.preservamos.gauge._gaugerange*/ ((float) (60),(float) (80),parent.mostCurrent._xui.Color_Green)),(Object)(parent.mostCurrent._gauge_partido._createrange /*appear.pnud.preservamos.gauge._gaugerange*/ ((float) (80),(float) (100),parent.mostCurrent._xui.Color_Blue))}));
 //BA.debugLineNum = 1200;BA.debugLine="gauge_Partido.CurrentValue = 0";
parent.mostCurrent._gauge_partido._setcurrentvalue /*float*/ ((float) (0));
 if (true) break;

case 62:
//C
this.state = 94;
;
 if (true) break;

case 64:
//C
this.state = 65;
 //BA.debugLineNum = 1204;BA.debugLine="Log(\"Partido NO existente:\" & Main.strUserOrg)";
anywheresoftware.b4a.keywords.Common.LogImpl("43014809","Partido NO existente:"+parent.mostCurrent._main._struserorg /*String*/ ,0);
 //BA.debugLineNum = 1205;BA.debugLine="lblMunicipio_texto_principal.Clear";
parent.mostCurrent._lblmunicipio_texto_principal._clear();
 //BA.debugLineNum = 1206;BA.debugLine="lblMunicipio_texto_principal.AddTextItem(\"Este";
parent.mostCurrent._lblmunicipio_texto_principal._addtextitem((Object)("Este municipio aún no ha incorporado una descripción de sus políticas ambientales."),(Object)(0));
 //BA.debugLineNum = 1207;BA.debugLine="btnMasInfoMunicipio.Visible = False";
parent.mostCurrent._btnmasinfomunicipio.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1211;BA.debugLine="If Municipios_parser.NextValue = \"Con reportes\"";
if (true) break;

case 65:
//if
this.state = 93;
if ((_municipios_parser.NextValue()).equals((Object)("Con reportes"))) { 
this.state = 67;
}else {
this.state = 92;
}if (true) break;

case 67:
//C
this.state = 68;
 //BA.debugLineNum = 1212;BA.debugLine="Log(\"Con reportes\")";
anywheresoftware.b4a.keywords.Common.LogImpl("43014817","Con reportes",0);
 //BA.debugLineNum = 1213;BA.debugLine="Municipios_numresults = Municipios_parser.Next";
parent._municipios_numresults = (int)(BA.ObjectToNumber(_municipios_parser.NextValue()));
 //BA.debugLineNum = 1214;BA.debugLine="lblMunicipio_puntos_totales.Text = Municipios_";
parent.mostCurrent._lblmunicipio_puntos_totales.setText(BA.ObjectToCharSequence(parent._municipios_numresults));
 //BA.debugLineNum = 1215;BA.debugLine="Log(\"Marcadores en el municipio: \" & Municipio";
anywheresoftware.b4a.keywords.Common.LogImpl("43014820","Marcadores en el municipio: "+BA.NumberToString(parent._municipios_numresults),0);
 //BA.debugLineNum = 1217;BA.debugLine="lblMunicipio_puntos_totales.Visible = True";
parent.mostCurrent._lblmunicipio_puntos_totales.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1218;BA.debugLine="btnVerDatosMunicipio.Visible = True";
parent.mostCurrent._btnverdatosmunicipio.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1219;BA.debugLine="btnVerEstadisticasMunicipio.Visible = True";
parent.mostCurrent._btnverestadisticasmunicipio.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1222;BA.debugLine="listaPromedio.Initialize";
parent.mostCurrent._listapromedio.Initialize();
 //BA.debugLineNum = 1223;BA.debugLine="listaHidro_Promedio.Initialize";
parent.mostCurrent._listahidro_promedio.Initialize();
 //BA.debugLineNum = 1224;BA.debugLine="listaAgua_Promedio.Initialize";
parent.mostCurrent._listaagua_promedio.Initialize();
 //BA.debugLineNum = 1225;BA.debugLine="listaBio_Promedio.Initialize";
parent.mostCurrent._listabio_promedio.Initialize();
 //BA.debugLineNum = 1226;BA.debugLine="listaUsos_Promedio.Initialize";
parent.mostCurrent._listausos_promedio.Initialize();
 //BA.debugLineNum = 1227;BA.debugLine="listaExoticas_Promedio.Initialize";
parent.mostCurrent._listaexoticas_promedio.Initialize();
 //BA.debugLineNum = 1230;BA.debugLine="Dim hidroPromedio As Double";
parent._hidropromedio = 0;
 //BA.debugLineNum = 1231;BA.debugLine="Dim aguaPromedio As Double";
parent._aguapromedio = 0;
 //BA.debugLineNum = 1232;BA.debugLine="Dim bioPromedio As Double";
parent._biopromedio = 0;
 //BA.debugLineNum = 1233;BA.debugLine="Dim UsosPromedio As Double";
parent._usospromedio = 0;
 //BA.debugLineNum = 1234;BA.debugLine="Dim ExoticasPromedio As Double";
parent._exoticaspromedio = 0;
 //BA.debugLineNum = 1237;BA.debugLine="For i = 0 To Municipios_numresults - 1";
if (true) break;

case 68:
//for
this.state = 71;
step144 = 1;
limit144 = (int) (parent._municipios_numresults-1);
_i = (int) (0) ;
this.state = 106;
if (true) break;

case 106:
//C
this.state = 71;
if ((step144 > 0 && _i <= limit144) || (step144 < 0 && _i >= limit144)) this.state = 70;
if (true) break;

case 107:
//C
this.state = 106;
_i = ((int)(0 + _i + step144)) ;
if (true) break;

case 70:
//C
this.state = 107;
 //BA.debugLineNum = 1239;BA.debugLine="Dim newpunto As Map";
_newpunto = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 1240;BA.debugLine="newpunto = Municipios_parser.NextObject";
_newpunto = _municipios_parser.NextObject();
 //BA.debugLineNum = 1241;BA.debugLine="listaPromedio.Add(newpunto.Get(\"indice\"))";
parent.mostCurrent._listapromedio.Add(_newpunto.Get((Object)("indice")));
 //BA.debugLineNum = 1242;BA.debugLine="listaHidro_Promedio.Add(newpunto.Get(\"valorin";
parent.mostCurrent._listahidro_promedio.Add(_newpunto.Get((Object)("valorind9")));
 //BA.debugLineNum = 1243;BA.debugLine="listaHidro_Promedio.Add(newpunto.Get(\"valorin";
parent.mostCurrent._listahidro_promedio.Add(_newpunto.Get((Object)("valorind10")));
 //BA.debugLineNum = 1244;BA.debugLine="listaAgua_Promedio.Add(newpunto.Get(\"valorind";
parent.mostCurrent._listaagua_promedio.Add(_newpunto.Get((Object)("valorind5")));
 //BA.debugLineNum = 1245;BA.debugLine="listaAgua_Promedio.Add(newpunto.Get(\"valorind";
parent.mostCurrent._listaagua_promedio.Add(_newpunto.Get((Object)("valorind6")));
 //BA.debugLineNum = 1246;BA.debugLine="listaAgua_Promedio.Add(newpunto.Get(\"valorind";
parent.mostCurrent._listaagua_promedio.Add(_newpunto.Get((Object)("valorind7")));
 //BA.debugLineNum = 1247;BA.debugLine="listaAgua_Promedio.Add(newpunto.Get(\"ind_pvm_";
parent.mostCurrent._listaagua_promedio.Add(_newpunto.Get((Object)("ind_pvm_1")));
 //BA.debugLineNum = 1248;BA.debugLine="listaBio_Promedio.Add(newpunto.Get(\"ind_pvm_2";
parent.mostCurrent._listabio_promedio.Add(_newpunto.Get((Object)("ind_pvm_2")));
 //BA.debugLineNum = 1249;BA.debugLine="listaBio_Promedio.Add(newpunto.Get(\"valorind3";
parent.mostCurrent._listabio_promedio.Add(_newpunto.Get((Object)("valorind3")));
 //BA.debugLineNum = 1250;BA.debugLine="listaBio_Promedio.Add(newpunto.Get(\"valorind4";
parent.mostCurrent._listabio_promedio.Add(_newpunto.Get((Object)("valorind4")));
 //BA.debugLineNum = 1251;BA.debugLine="listaUsos_Promedio.Add(newpunto.Get(\"valorind";
parent.mostCurrent._listausos_promedio.Add(_newpunto.Get((Object)("valorind1")));
 //BA.debugLineNum = 1252;BA.debugLine="listaUsos_Promedio.Add(newpunto.Get(\"valorind";
parent.mostCurrent._listausos_promedio.Add(_newpunto.Get((Object)("valorind2")));
 //BA.debugLineNum = 1253;BA.debugLine="listaExoticas_Promedio.Add(newpunto.Get(\"ind_";
parent.mostCurrent._listaexoticas_promedio.Add(_newpunto.Get((Object)("ind_pvm_3")));
 //BA.debugLineNum = 1254;BA.debugLine="listaExoticas_Promedio.Add(newpunto.Get(\"ind_";
parent.mostCurrent._listaexoticas_promedio.Add(_newpunto.Get((Object)("ind_pvm_4")));
 if (true) break;
if (true) break;
;
 //BA.debugLineNum = 1258;BA.debugLine="For j = 0 To listaPromedio.Size - 1";

case 71:
//for
this.state = 78;
step162 = 1;
limit162 = (int) (parent.mostCurrent._listapromedio.getSize()-1);
_j = (int) (0) ;
this.state = 108;
if (true) break;

case 108:
//C
this.state = 78;
if ((step162 > 0 && _j <= limit162) || (step162 < 0 && _j >= limit162)) this.state = 73;
if (true) break;

case 109:
//C
this.state = 108;
_j = ((int)(0 + _j + step162)) ;
if (true) break;

case 73:
//C
this.state = 74;
 //BA.debugLineNum = 1259;BA.debugLine="If listaPromedio.Get(j) <> \"\" Then";
if (true) break;

case 74:
//if
this.state = 77;
if ((parent.mostCurrent._listapromedio.Get(_j)).equals((Object)("")) == false) { 
this.state = 76;
}if (true) break;

case 76:
//C
this.state = 77;
 //BA.debugLineNum = 1260;BA.debugLine="valorEstado = valorEstado + listaPromedio.Ge";
parent._valorestado = parent._valorestado+(double)(BA.ObjectToNumber(parent.mostCurrent._listapromedio.Get(_j)));
 if (true) break;

case 77:
//C
this.state = 109;
;
 if (true) break;
if (true) break;

case 78:
//C
this.state = 79;
;
 //BA.debugLineNum = 1263;BA.debugLine="valorEstado = Round2(valorEstado / listaPromed";
parent._valorestado = anywheresoftware.b4a.keywords.Common.Round2(parent._valorestado/(double)parent.mostCurrent._listapromedio.getSize(),(int) (2));
 //BA.debugLineNum = 1264;BA.debugLine="Log(\"valorEstado: \" & valorEstado)";
anywheresoftware.b4a.keywords.Common.LogImpl("43014869","valorEstado: "+BA.NumberToString(parent._valorestado),0);
 //BA.debugLineNum = 1275;BA.debugLine="gauge_Partido.mBase.Visible = True";
parent.mostCurrent._gauge_partido._mbase /*anywheresoftware.b4a.objects.B4XViewWrapper*/ .setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1276;BA.debugLine="lblMunicipios_reportes.Visible = True";
parent.mostCurrent._lblmunicipios_reportes.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1277;BA.debugLine="gauge_Partido.SetRanges(Array(gauge_Partido.Cr";
parent.mostCurrent._gauge_partido._setranges /*String*/ (anywheresoftware.b4a.keywords.Common.ArrayToList(new Object[]{(Object)(parent.mostCurrent._gauge_partido._createrange /*appear.pnud.preservamos.gauge._gaugerange*/ ((float) (0),(float) (20),parent.mostCurrent._xui.Color_DarkGray)),(Object)(parent.mostCurrent._gauge_partido._createrange /*appear.pnud.preservamos.gauge._gaugerange*/ ((float) (20),(float) (40),parent.mostCurrent._xui.Color_Red)),(Object)(parent.mostCurrent._gauge_partido._createrange /*appear.pnud.preservamos.gauge._gaugerange*/ ((float) (40),(float) (60),parent.mostCurrent._xui.Color_Yellow)),(Object)(parent.mostCurrent._gauge_partido._createrange /*appear.pnud.preservamos.gauge._gaugerange*/ ((float) (60),(float) (80),parent.mostCurrent._xui.Color_Green)),(Object)(parent.mostCurrent._gauge_partido._createrange /*appear.pnud.preservamos.gauge._gaugerange*/ ((float) (80),(float) (100),parent.mostCurrent._xui.Color_Blue))}));
 //BA.debugLineNum = 1278;BA.debugLine="gauge_Partido.CurrentValue = 0";
parent.mostCurrent._gauge_partido._setcurrentvalue /*float*/ ((float) (0));
 //BA.debugLineNum = 1279;BA.debugLine="gauge_Partido.CurrentValue = valorEstado";
parent.mostCurrent._gauge_partido._setcurrentvalue /*float*/ ((float) (parent._valorestado));
 //BA.debugLineNum = 1281;BA.debugLine="If valorEstado < 20 Then";
if (true) break;

case 79:
//if
this.state = 90;
if (parent._valorestado<20) { 
this.state = 81;
}else if(parent._valorestado>=20 && parent._valorestado<40) { 
this.state = 83;
}else if(parent._valorestado>=40 && parent._valorestado<60) { 
this.state = 85;
}else if(parent._valorestado>=60 && parent._valorestado<80) { 
this.state = 87;
}else if(parent._valorestado>=80) { 
this.state = 89;
}if (true) break;

case 81:
//C
this.state = 90;
 //BA.debugLineNum = 1282;BA.debugLine="lblMunicipio_estado.Text = \"Estado general: M";
parent.mostCurrent._lblmunicipio_estado.setText(BA.ObjectToCharSequence("Estado general: Muy malo"));
 if (true) break;

case 83:
//C
this.state = 90;
 //BA.debugLineNum = 1284;BA.debugLine="lblMunicipio_estado.Text = \"Estado general: M";
parent.mostCurrent._lblmunicipio_estado.setText(BA.ObjectToCharSequence("Estado general: Malo"));
 if (true) break;

case 85:
//C
this.state = 90;
 //BA.debugLineNum = 1286;BA.debugLine="lblMunicipio_estado.Text = \"Estado general: R";
parent.mostCurrent._lblmunicipio_estado.setText(BA.ObjectToCharSequence("Estado general: Regular"));
 if (true) break;

case 87:
//C
this.state = 90;
 //BA.debugLineNum = 1288;BA.debugLine="lblMunicipio_estado.Text = \"Estado general: B";
parent.mostCurrent._lblmunicipio_estado.setText(BA.ObjectToCharSequence("Estado general: Bueno"));
 if (true) break;

case 89:
//C
this.state = 90;
 //BA.debugLineNum = 1290;BA.debugLine="lblMunicipio_estado.Text = \"Estado general: M";
parent.mostCurrent._lblmunicipio_estado.setText(BA.ObjectToCharSequence("Estado general: Muy bueno!"));
 if (true) break;

case 90:
//C
this.state = 93;
;
 //BA.debugLineNum = 1292;BA.debugLine="lblMunicipio_estado.Text = lblMunicipio_estado";
parent.mostCurrent._lblmunicipio_estado.setText(BA.ObjectToCharSequence(parent.mostCurrent._lblmunicipio_estado.getText()));
 if (true) break;

case 92:
//C
this.state = 93;
 //BA.debugLineNum = 1294;BA.debugLine="Log(\"No tiene reportes\")";
anywheresoftware.b4a.keywords.Common.LogImpl("43014899","No tiene reportes",0);
 //BA.debugLineNum = 1295;BA.debugLine="btnVerDatosMunicipio.Visible = False";
parent.mostCurrent._btnverdatosmunicipio.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1296;BA.debugLine="btnVerEstadisticasMunicipio.Visible = False";
parent.mostCurrent._btnverestadisticasmunicipio.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1297;BA.debugLine="lblMunicipio_puntos_totales.Visible = False";
parent.mostCurrent._lblmunicipio_puntos_totales.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1298;BA.debugLine="lblMunicipio_estado.Text = \"Estado general: De";
parent.mostCurrent._lblmunicipio_estado.setText(BA.ObjectToCharSequence("Estado general: Desconocido"));
 //BA.debugLineNum = 1300;BA.debugLine="gauge_Partido.mBase.Visible = True";
parent.mostCurrent._gauge_partido._mbase /*anywheresoftware.b4a.objects.B4XViewWrapper*/ .setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1301;BA.debugLine="gauge_Partido.SetRanges(Array(gauge_Partido.Cr";
parent.mostCurrent._gauge_partido._setranges /*String*/ (anywheresoftware.b4a.keywords.Common.ArrayToList(new Object[]{(Object)(parent.mostCurrent._gauge_partido._createrange /*appear.pnud.preservamos.gauge._gaugerange*/ ((float) (0),(float) (20),parent.mostCurrent._xui.Color_DarkGray)),(Object)(parent.mostCurrent._gauge_partido._createrange /*appear.pnud.preservamos.gauge._gaugerange*/ ((float) (20),(float) (40),parent.mostCurrent._xui.Color_Red)),(Object)(parent.mostCurrent._gauge_partido._createrange /*appear.pnud.preservamos.gauge._gaugerange*/ ((float) (40),(float) (60),parent.mostCurrent._xui.Color_Yellow)),(Object)(parent.mostCurrent._gauge_partido._createrange /*appear.pnud.preservamos.gauge._gaugerange*/ ((float) (60),(float) (80),parent.mostCurrent._xui.Color_Green)),(Object)(parent.mostCurrent._gauge_partido._createrange /*appear.pnud.preservamos.gauge._gaugerange*/ ((float) (80),(float) (100),parent.mostCurrent._xui.Color_Blue))}));
 //BA.debugLineNum = 1302;BA.debugLine="gauge_Partido.CurrentValue = 0";
parent.mostCurrent._gauge_partido._setcurrentvalue /*float*/ ((float) (0));
 if (true) break;

case 93:
//C
this.state = 94;
;
 if (true) break;

case 94:
//C
this.state = 101;
;
 if (true) break;

case 96:
//C
this.state = 97;
 //BA.debugLineNum = 1308;BA.debugLine="Msgbox2Async(\"No tenés conexión a Internet. No s";
anywheresoftware.b4a.keywords.Common.Msgbox2Async(BA.ObjectToCharSequence("No tenés conexión a Internet. No se puede buscar información sobre este municipio"),BA.ObjectToCharSequence("Advertencia"),"Ok, entiendo","","",(anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper(), (android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null)),processBA,anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1309;BA.debugLine="Wait For Msgbox_Result (Result As Int)";
anywheresoftware.b4a.keywords.Common.WaitFor("msgbox_result", processBA, this, null);
this.state = 110;
return;
case 110:
//C
this.state = 97;
_result = (Integer) result[0];
;
 //BA.debugLineNum = 1310;BA.debugLine="If Result = DialogResponse.POSITIVE Then";
if (true) break;

case 97:
//if
this.state = 100;
if (_result==anywheresoftware.b4a.keywords.Common.DialogResponse.POSITIVE) { 
this.state = 99;
}if (true) break;

case 99:
//C
this.state = 100;
 //BA.debugLineNum = 1311;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 if (true) break;

case 100:
//C
this.state = 101;
;
 if (true) break;

case 101:
//C
this.state = -1;
;
 //BA.debugLineNum = 1315;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public static String  _getpuntaje() throws Exception{
appear.pnud.preservamos.downloadservice._downloaddata _dd = null;
 //BA.debugLineNum = 1407;BA.debugLine="Sub getPuntaje";
 //BA.debugLineNum = 1408;BA.debugLine="If Main.hayinternet = True Then";
if (mostCurrent._main._hayinternet /*boolean*/ ==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 1409;BA.debugLine="Log(\"Getting puntaje\")";
anywheresoftware.b4a.keywords.Common.LogImpl("43670018","Getting puntaje",0);
 //BA.debugLineNum = 1410;BA.debugLine="Dim dd As DownloadData";
_dd = new appear.pnud.preservamos.downloadservice._downloaddata();
 //BA.debugLineNum = 1411;BA.debugLine="dd.url = Main.serverPath & \"/\" & Main.serverConn";
_dd.url /*String*/  = mostCurrent._main._serverpath /*String*/ +"/"+mostCurrent._main._serverconnectionfolder /*String*/ +"/getPuntaje.php?useremail="+mostCurrent._main._struseremail /*String*/ ;
 //BA.debugLineNum = 1412;BA.debugLine="Log(\"Testing: \" & dd.url)";
anywheresoftware.b4a.keywords.Common.LogImpl("43670021","Testing: "+_dd.url /*String*/ ,0);
 //BA.debugLineNum = 1413;BA.debugLine="dd.EventName = \"getPuntaje\"";
_dd.EventName /*String*/  = "getPuntaje";
 //BA.debugLineNum = 1414;BA.debugLine="dd.Target = Me";
_dd.Target /*Object*/  = form_main.getObject();
 //BA.debugLineNum = 1415;BA.debugLine="CallSubDelayed2(DownloadService, \"StartDownload\"";
anywheresoftware.b4a.keywords.Common.CallSubDelayed2(processBA,(Object)(mostCurrent._downloadservice.getObject()),"StartDownload",(Object)(_dd));
 };
 //BA.debugLineNum = 1417;BA.debugLine="End Sub";
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
 //BA.debugLineNum = 1418;BA.debugLine="Sub getPuntaje_Complete(Job As HttpJob)";
 //BA.debugLineNum = 1419;BA.debugLine="Log(\"getPuntaje: \" & Job.Success)";
anywheresoftware.b4a.keywords.Common.LogImpl("43735553","getPuntaje: "+BA.ObjectToString(_job._success /*boolean*/ ),0);
 //BA.debugLineNum = 1420;BA.debugLine="If Job.Success = True Then";
if (_job._success /*boolean*/ ==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 1421;BA.debugLine="Main.hayinternet = True";
mostCurrent._main._hayinternet /*boolean*/  = anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 1423;BA.debugLine="Dim ret As String";
_ret = "";
 //BA.debugLineNum = 1424;BA.debugLine="Dim act As String";
_act = "";
 //BA.debugLineNum = 1425;BA.debugLine="Dim new_user_markers As Map";
_new_user_markers = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 1426;BA.debugLine="Dim new_evals_residuos As Map";
_new_evals_residuos = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 1427;BA.debugLine="Dim new_evals_hidro As Map";
_new_evals_hidro = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 1428;BA.debugLine="ret = Job.GetString";
_ret = _job._getstring /*String*/ ();
 //BA.debugLineNum = 1429;BA.debugLine="Dim parser As JSONParser";
_parser = new anywheresoftware.b4a.objects.collections.JSONParser();
 //BA.debugLineNum = 1430;BA.debugLine="parser.Initialize(ret)";
_parser.Initialize(_ret);
 //BA.debugLineNum = 1431;BA.debugLine="act = parser.NextValue";
_act = BA.ObjectToString(_parser.NextValue());
 //BA.debugLineNum = 1434;BA.debugLine="Main.puntostotales = 0";
mostCurrent._main._puntostotales /*String*/  = BA.NumberToString(0);
 //BA.debugLineNum = 1435;BA.debugLine="Main.puntos_markers = 0";
mostCurrent._main._puntos_markers /*int*/  = (int) (0);
 //BA.debugLineNum = 1436;BA.debugLine="Main.puntostotales = 0";
mostCurrent._main._puntostotales /*String*/  = BA.NumberToString(0);
 //BA.debugLineNum = 1437;BA.debugLine="Main.puntos_evals_hidro = 0";
mostCurrent._main._puntos_evals_hidro /*int*/  = (int) (0);
 //BA.debugLineNum = 1438;BA.debugLine="Main.puntos_evals_hidro_laguna = 0";
mostCurrent._main._puntos_evals_hidro_laguna /*int*/  = (int) (0);
 //BA.debugLineNum = 1439;BA.debugLine="Main.puntos_evals_hidro_llanura = 0";
mostCurrent._main._puntos_evals_hidro_llanura /*int*/  = (int) (0);
 //BA.debugLineNum = 1440;BA.debugLine="Main.puntos_evals_hidro_sierras = 0";
mostCurrent._main._puntos_evals_hidro_sierras /*int*/  = (int) (0);
 //BA.debugLineNum = 1441;BA.debugLine="Main.puntos_evals_residuos = 0";
mostCurrent._main._puntos_evals_residuos /*int*/  = (int) (0);
 //BA.debugLineNum = 1442;BA.debugLine="Main.puntos_evals_residuos_laguna = 0";
mostCurrent._main._puntos_evals_residuos_laguna /*int*/  = (int) (0);
 //BA.debugLineNum = 1443;BA.debugLine="Main.puntos_evals_residuos_llanura = 0";
mostCurrent._main._puntos_evals_residuos_llanura /*int*/  = (int) (0);
 //BA.debugLineNum = 1444;BA.debugLine="Main.puntos_evals_residuos_sierras = 0";
mostCurrent._main._puntos_evals_residuos_sierras /*int*/  = (int) (0);
 //BA.debugLineNum = 1445;BA.debugLine="Main.user_markers_list.Initialize";
mostCurrent._main._user_markers_list /*anywheresoftware.b4a.objects.collections.List*/ .Initialize();
 //BA.debugLineNum = 1446;BA.debugLine="Main.user_evals_hidro_list.Initialize";
mostCurrent._main._user_evals_hidro_list /*anywheresoftware.b4a.objects.collections.List*/ .Initialize();
 //BA.debugLineNum = 1447;BA.debugLine="Main.user_evals_residuos_list.Initialize";
mostCurrent._main._user_evals_residuos_list /*anywheresoftware.b4a.objects.collections.List*/ .Initialize();
 //BA.debugLineNum = 1449;BA.debugLine="If act = \"Not Found\" Then";
if ((_act).equals("Not Found")) { 
 //BA.debugLineNum = 1450;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 1451;BA.debugLine="ToastMessageShow(\"Error recuperando los puntos";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Error recuperando los puntos"),anywheresoftware.b4a.keywords.Common.False);
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 1453;BA.debugLine="ToastMessageShow(\"Error recovering points\", Fa";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Error recovering points"),anywheresoftware.b4a.keywords.Common.False);
 };
 }else if((_act).equals("GetPuntajeOk")) { 
 //BA.debugLineNum = 1458;BA.debugLine="Try";
try { //BA.debugLineNum = 1460;BA.debugLine="Dim markersList As List";
_markerslist = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 1461;BA.debugLine="markersList.Initialize()";
_markerslist.Initialize();
 //BA.debugLineNum = 1462;BA.debugLine="markersList = parser.NextArray";
_markerslist = _parser.NextArray();
 //BA.debugLineNum = 1463;BA.debugLine="Main.user_markers_list = markersList";
mostCurrent._main._user_markers_list /*anywheresoftware.b4a.objects.collections.List*/  = _markerslist;
 //BA.debugLineNum = 1465;BA.debugLine="Dim evals_residuosList As List";
_evals_residuoslist = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 1466;BA.debugLine="evals_residuosList.Initialize()";
_evals_residuoslist.Initialize();
 //BA.debugLineNum = 1467;BA.debugLine="evals_residuosList = parser.NextArray";
_evals_residuoslist = _parser.NextArray();
 //BA.debugLineNum = 1468;BA.debugLine="Main.user_evals_residuos_list = evals_residuos";
mostCurrent._main._user_evals_residuos_list /*anywheresoftware.b4a.objects.collections.List*/  = _evals_residuoslist;
 //BA.debugLineNum = 1470;BA.debugLine="Dim evals_hidroList As List";
_evals_hidrolist = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 1471;BA.debugLine="evals_hidroList.Initialize()";
_evals_hidrolist.Initialize();
 //BA.debugLineNum = 1472;BA.debugLine="evals_hidroList = parser.NextArray";
_evals_hidrolist = _parser.NextArray();
 //BA.debugLineNum = 1473;BA.debugLine="Main.user_evals_hidro_list = evals_hidroList";
mostCurrent._main._user_evals_hidro_list /*anywheresoftware.b4a.objects.collections.List*/  = _evals_hidrolist;
 //BA.debugLineNum = 1475;BA.debugLine="calcular_puntos_ambientes";
_calcular_puntos_ambientes();
 } 
       catch (Exception e49) {
			processBA.setLastException(e49); //BA.debugLineNum = 1477;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("43735611",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA)),0);
 //BA.debugLineNum = 1478;BA.debugLine="Log(\"error en puntajes\")";
anywheresoftware.b4a.keywords.Common.LogImpl("43735612","error en puntajes",0);
 //BA.debugLineNum = 1479;BA.debugLine="ToastMessageShow(\"No se pudo consultar el perf";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("No se pudo consultar el perfil del participante... intente de nuevo luego!"),anywheresoftware.b4a.keywords.Common.False);
 };
 };
 }else {
 //BA.debugLineNum = 1487;BA.debugLine="Main.hayinternet = False";
mostCurrent._main._hayinternet /*boolean*/  = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 1488;BA.debugLine="MsgboxAsync(\"Compruebe su conexión a Internet!\",";
anywheresoftware.b4a.keywords.Common.MsgboxAsync(BA.ObjectToCharSequence("Compruebe su conexión a Internet!"),BA.ObjectToCharSequence("Oops!"),processBA);
 //BA.debugLineNum = 1489;BA.debugLine="If tabStripMain.CurrentPage <> 2 Then";
if (mostCurrent._tabstripmain.getCurrentPage()!=2) { 
 //BA.debugLineNum = 1490;BA.debugLine="tabStripMain.ScrollTo(2,True)";
mostCurrent._tabstripmain.ScrollTo((int) (2),anywheresoftware.b4a.keywords.Common.True);
 };
 };
 //BA.debugLineNum = 1495;BA.debugLine="Job.Release";
_job._release /*String*/ ();
 //BA.debugLineNum = 1496;BA.debugLine="End Sub";
return "";
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 14;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 15;BA.debugLine="Dim rp As RuntimePermissions";
mostCurrent._rp = new anywheresoftware.b4a.objects.RuntimePermissions();
 //BA.debugLineNum = 17;BA.debugLine="Dim FormMainloaded As Boolean = False";
_formmainloaded = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 18;BA.debugLine="Dim p As Phone";
mostCurrent._p = new anywheresoftware.b4a.phone.Phone();
 //BA.debugLineNum = 19;BA.debugLine="Dim pi As PhoneIntents";
mostCurrent._pi = new anywheresoftware.b4a.phone.Phone.PhoneIntents();
 //BA.debugLineNum = 23;BA.debugLine="Private pgbNivel As ProgressBar";
mostCurrent._pgbnivel = new anywheresoftware.b4a.objects.ProgressBarWrapper();
 //BA.debugLineNum = 24;BA.debugLine="Private lblLevel As Label";
mostCurrent._lbllevel = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 27;BA.debugLine="Private tabStripMain As TabStrip";
mostCurrent._tabstripmain = new anywheresoftware.b4a.objects.TabStripViewPager();
 //BA.debugLineNum = 28;BA.debugLine="Private icoMapa As Label";
mostCurrent._icomapa = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 29;BA.debugLine="Private icoInformacion As Label";
mostCurrent._icoinformacion = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 30;BA.debugLine="Private icoAnalizar As Label";
mostCurrent._icoanalizar = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 31;BA.debugLine="Private icoPerfil As Label";
mostCurrent._icoperfil = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 32;BA.debugLine="Private icoAlerta As Label";
mostCurrent._icoalerta = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 33;BA.debugLine="Private btnMapa As Label";
mostCurrent._btnmapa = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 34;BA.debugLine="Private btnInformacion As Label";
mostCurrent._btninformacion = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 35;BA.debugLine="Private btnCirculo As Label";
mostCurrent._btncirculo = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 36;BA.debugLine="Private btnAnalizar As Label";
mostCurrent._btnanalizar = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 37;BA.debugLine="Private btnPerfil As Label";
mostCurrent._btnperfil = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 38;BA.debugLine="Private btnAlerta As Label";
mostCurrent._btnalerta = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 42;BA.debugLine="Private lblAnalizar As Label";
mostCurrent._lblanalizar = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 43;BA.debugLine="Private btnReportar As Button";
mostCurrent._btnreportar = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 44;BA.debugLine="Private lblComoFuncionaAnalizar As Label";
mostCurrent._lblcomofuncionaanalizar = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 45;BA.debugLine="Private pnlDescripcionAnalisis As Panel";
mostCurrent._pnldescripcionanalisis = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 46;BA.debugLine="Private lblTituloAnalisis As Label";
mostCurrent._lbltituloanalisis = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 47;BA.debugLine="Private lblPremioAnalisis As Label";
mostCurrent._lblpremioanalisis = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 48;BA.debugLine="Private lblDescripcionAnalisis As Label";
mostCurrent._lbldescripcionanalisis = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 49;BA.debugLine="Private lblPuntosAnalisis As Label";
mostCurrent._lblpuntosanalisis = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 50;BA.debugLine="Private btnComenzarAnalisis As Button";
mostCurrent._btncomenzaranalisis = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 51;BA.debugLine="Private tipoAnalisis As String";
mostCurrent._tipoanalisis = "";
 //BA.debugLineNum = 53;BA.debugLine="Private pnlHabitat As Panel";
mostCurrent._pnlhabitat = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 54;BA.debugLine="Private pnlResiduos As Panel";
mostCurrent._pnlresiduos = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 55;BA.debugLine="Private pnlHidro As Panel";
mostCurrent._pnlhidro = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 58;BA.debugLine="Private btnAbrirMapa As Button";
mostCurrent._btnabrirmapa = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 59;BA.debugLine="Private btnDetectar As Button";
mostCurrent._btndetectar = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 60;BA.debugLine="Private lblLat As Label";
mostCurrent._lbllat = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 61;BA.debugLine="Private lblLon As Label";
mostCurrent._lbllon = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 62;BA.debugLine="Private btnZoomAll As Button";
mostCurrent._btnzoomall = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 63;BA.debugLine="Dim MarkerExport As List";
mostCurrent._markerexport = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 65;BA.debugLine="Dim MarkerInfo As Label";
mostCurrent._markerinfo = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 66;BA.debugLine="Private pnlMapa As Panel";
mostCurrent._pnlmapa = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 67;BA.debugLine="Dim fondoblanco As Label";
mostCurrent._fondoblanco = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 68;BA.debugLine="Dim detectandoLabel As Label";
mostCurrent._detectandolabel = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 72;BA.debugLine="Dim firstuse As String";
mostCurrent._firstuse = "";
 //BA.debugLineNum = 73;BA.debugLine="Dim fondogris As Panel";
mostCurrent._fondogris = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 74;BA.debugLine="Dim panelComoFunciona As Panel";
mostCurrent._panelcomofunciona = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 76;BA.debugLine="Dim tutorialEtapa As Int";
_tutorialetapa = 0;
 //BA.debugLineNum = 77;BA.debugLine="Private btnMenu_Main As Label";
mostCurrent._btnmenu_main = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 81;BA.debugLine="Dim Drawer As B4XDrawer";
mostCurrent._drawer = new appear.pnud.preservamos.b4xdrawer();
 //BA.debugLineNum = 82;BA.debugLine="Private btnCerrarSesion As Label";
mostCurrent._btncerrarsesion = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 83;BA.debugLine="Private btnEditUser As Label";
mostCurrent._btnedituser = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 84;BA.debugLine="Private btnverMedallas As Label";
mostCurrent._btnvermedallas = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 85;BA.debugLine="Private lblUserName As Label";
mostCurrent._lblusername = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 86;BA.debugLine="Private lblRegistrate As Label";
mostCurrent._lblregistrate = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 87;BA.debugLine="Private btnVerPerfil As Label";
mostCurrent._btnverperfil = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 88;BA.debugLine="Private btnAbout As Label";
mostCurrent._btnabout = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 89;BA.debugLine="Private btnPoliticaDatos As Label";
mostCurrent._btnpoliticadatos = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 90;BA.debugLine="Private btnDatosAnteriores As Label";
mostCurrent._btndatosanteriores = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 91;BA.debugLine="Private btnMuestreos As Label";
mostCurrent._btnmuestreos = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 95;BA.debugLine="Private lblTitleMunicipio As Label";
mostCurrent._lbltitlemunicipio = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 96;BA.debugLine="Private WebView1 As WebView";
mostCurrent._webview1 = new anywheresoftware.b4a.objects.WebViewWrapper();
 //BA.debugLineNum = 97;BA.debugLine="Private lblTitleMunicipio As Label";
mostCurrent._lbltitlemunicipio = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 98;BA.debugLine="Private lblMunicipio_texto_principal As CustomLis";
mostCurrent._lblmunicipio_texto_principal = new b4a.example3.customlistview();
 //BA.debugLineNum = 99;BA.debugLine="Private lblMunicipio_puntos_totales As Label";
mostCurrent._lblmunicipio_puntos_totales = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 100;BA.debugLine="Private btnMasInfoMunicipio As ImageView";
mostCurrent._btnmasinfomunicipio = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 101;BA.debugLine="Private imgLogoMunicipio As ImageView";
mostCurrent._imglogomunicipio = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 102;BA.debugLine="Private gauge_Partido As Gauge";
mostCurrent._gauge_partido = new appear.pnud.preservamos.gauge();
 //BA.debugLineNum = 103;BA.debugLine="Private xui As XUI";
mostCurrent._xui = new anywheresoftware.b4a.objects.B4XViewWrapper.XUI();
 //BA.debugLineNum = 104;BA.debugLine="Private lblMunicipio_estado As Label";
mostCurrent._lblmunicipio_estado = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 105;BA.debugLine="Dim Municipios_numresults As Int";
_municipios_numresults = 0;
 //BA.debugLineNum = 106;BA.debugLine="Dim Municipios_parser_string As String";
mostCurrent._municipios_parser_string = "";
 //BA.debugLineNum = 107;BA.debugLine="Dim municipioURL As String";
mostCurrent._municipiourl = "";
 //BA.debugLineNum = 108;BA.debugLine="Dim municipioFB As String";
mostCurrent._municipiofb = "";
 //BA.debugLineNum = 109;BA.debugLine="Dim municipioIG As String";
mostCurrent._municipioig = "";
 //BA.debugLineNum = 110;BA.debugLine="Dim municipioYT As String";
mostCurrent._municipioyt = "";
 //BA.debugLineNum = 111;BA.debugLine="Dim municipioTW As String";
mostCurrent._municipiotw = "";
 //BA.debugLineNum = 112;BA.debugLine="Private btnMunicipioTW As ImageView";
mostCurrent._btnmunicipiotw = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 113;BA.debugLine="Private btnMunicipioFB As ImageView";
mostCurrent._btnmunicipiofb = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 114;BA.debugLine="Private btnMunicipioYT As ImageView";
mostCurrent._btnmunicipioyt = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 115;BA.debugLine="Private btnMunicipioIG As ImageView";
mostCurrent._btnmunicipioig = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 116;BA.debugLine="Private btnVerDatosMunicipio As Button";
mostCurrent._btnverdatosmunicipio = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 117;BA.debugLine="Private btnVerEstadisticasMunicipio As Button";
mostCurrent._btnverestadisticasmunicipio = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 118;BA.debugLine="Private btnCambiarMunicipio As Button";
mostCurrent._btncambiarmunicipio = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 120;BA.debugLine="Dim valorEstado As Double";
_valorestado = 0;
 //BA.debugLineNum = 121;BA.debugLine="Dim hidroPromedio As Double";
_hidropromedio = 0;
 //BA.debugLineNum = 122;BA.debugLine="Dim aguaPromedio As Double";
_aguapromedio = 0;
 //BA.debugLineNum = 123;BA.debugLine="Dim bioPromedio As Double";
_biopromedio = 0;
 //BA.debugLineNum = 124;BA.debugLine="Dim UsosPromedio As Double";
_usospromedio = 0;
 //BA.debugLineNum = 125;BA.debugLine="Dim ExoticasPromedio As Double";
_exoticaspromedio = 0;
 //BA.debugLineNum = 126;BA.debugLine="Dim listaPromedio As List";
mostCurrent._listapromedio = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 127;BA.debugLine="Dim listaHidro_Promedio As List";
mostCurrent._listahidro_promedio = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 128;BA.debugLine="Dim listaAgua_Promedio As List";
mostCurrent._listaagua_promedio = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 129;BA.debugLine="Dim listaBio_Promedio As List";
mostCurrent._listabio_promedio = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 130;BA.debugLine="Dim listaUsos_Promedio As List";
mostCurrent._listausos_promedio = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 131;BA.debugLine="Dim listaExoticas_Promedio As List";
mostCurrent._listaexoticas_promedio = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 136;BA.debugLine="Private lblComoFuncionaContenido As Label";
mostCurrent._lblcomofuncionacontenido = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 137;BA.debugLine="Private lblComoFuncionaTitle As Label";
mostCurrent._lblcomofuncionatitle = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 140;BA.debugLine="Private lblPuntos_Explicacion_1 As Label";
mostCurrent._lblpuntos_explicacion_1 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 141;BA.debugLine="Private lblPuntos_Explicacion_2 As Label";
mostCurrent._lblpuntos_explicacion_2 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 142;BA.debugLine="Private lblPuntos_Explicacion_3 As Label";
mostCurrent._lblpuntos_explicacion_3 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 143;BA.debugLine="Private btnActualizar As Button";
mostCurrent._btnactualizar = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 144;BA.debugLine="Private lblTitleProfile As Label";
mostCurrent._lbltitleprofile = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 145;BA.debugLine="Private lblPerfil_puntos_totales As Label";
mostCurrent._lblperfil_puntos_totales = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 146;BA.debugLine="Private lblPuntos_Titulo_1 As Label";
mostCurrent._lblpuntos_titulo_1 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 147;BA.debugLine="Private lblPuntos_Titulo_2 As Label";
mostCurrent._lblpuntos_titulo_2 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 148;BA.debugLine="Private lblPuntos_Titulo_3 As Label";
mostCurrent._lblpuntos_titulo_3 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 149;BA.debugLine="Private imgHabitat As Button";
mostCurrent._imghabitat = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 150;BA.debugLine="Private imgBasura As Button";
mostCurrent._imgbasura = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 151;BA.debugLine="Private imgHidro As Button";
mostCurrent._imghidro = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 155;BA.debugLine="Private pgbLevel As ProgressBar";
mostCurrent._pgblevel = new anywheresoftware.b4a.objects.ProgressBarWrapper();
 //BA.debugLineNum = 157;BA.debugLine="Private lstAchievements As ListView";
mostCurrent._lstachievements = new anywheresoftware.b4a.objects.ListViewWrapper();
 //BA.debugLineNum = 158;BA.debugLine="Private lblNivel As Label";
mostCurrent._lblnivel = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 159;BA.debugLine="Private btnShare As Button";
mostCurrent._btnshare = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 160;BA.debugLine="Private btnShareOthers As Button";
mostCurrent._btnshareothers = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 161;BA.debugLine="Private lblTitulo As Label";
mostCurrent._lbltitulo = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 162;BA.debugLine="Private imgBadge As ImageView";
mostCurrent._imgbadge = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 163;BA.debugLine="Private nivel As String";
mostCurrent._nivel = "";
 //BA.debugLineNum = 164;BA.debugLine="Private imagetoshare As String";
mostCurrent._imagetoshare = "";
 //BA.debugLineNum = 165;BA.debugLine="Dim fondogris_share As Panel";
mostCurrent._fondogris_share = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 166;BA.debugLine="Private pnlShare As Panel";
mostCurrent._pnlshare = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 167;BA.debugLine="Private imgBadgeLevel As ImageView";
mostCurrent._imgbadgelevel = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 170;BA.debugLine="Private lblMunicipios_reportes As Label";
mostCurrent._lblmunicipios_reportes = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 171;BA.debugLine="Private scrReportar_Modulos As ScrollView";
mostCurrent._scrreportar_modulos = new anywheresoftware.b4a.objects.ScrollViewWrapper();
 //BA.debugLineNum = 173;BA.debugLine="End Sub";
return "";
}
public static String  _gotoinformacion() throws Exception{
 //BA.debugLineNum = 421;BA.debugLine="Sub gotoInformacion 'necesario para cuando termina";
 //BA.debugLineNum = 422;BA.debugLine="tabStripMain.ScrollTo(1, True)";
mostCurrent._tabstripmain.ScrollTo((int) (1),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 426;BA.debugLine="pnlHidro.Visible = False";
mostCurrent._pnlhidro.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 427;BA.debugLine="pnlHabitat.Visible = False";
mostCurrent._pnlhabitat.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 428;BA.debugLine="pnlResiduos.Visible = False";
mostCurrent._pnlresiduos.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 429;BA.debugLine="pnlDescripcionAnalisis.Visible = False";
mostCurrent._pnldescripcionanalisis.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 430;BA.debugLine="btnReportar.Top = 160dip";
mostCurrent._btnreportar.setTop(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (160)));
 //BA.debugLineNum = 431;BA.debugLine="lblAnalizar.Top = btnReportar.Top - lblAnalizar.H";
mostCurrent._lblanalizar.setTop((int) (mostCurrent._btnreportar.getTop()-mostCurrent._lblanalizar.getHeight()-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (20))));
 //BA.debugLineNum = 432;BA.debugLine="icoAnalizar.TextColor = Colors.ARGB(255,51,51,51)";
mostCurrent._icoanalizar.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (51),(int) (51),(int) (51)));
 //BA.debugLineNum = 433;BA.debugLine="icoMapa.TextColor = Colors.ARGB(255,51,51,51)";
mostCurrent._icomapa.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (51),(int) (51),(int) (51)));
 //BA.debugLineNum = 434;BA.debugLine="icoPerfil.TextColor = Colors.ARGB(255,51,51,51)";
mostCurrent._icoperfil.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (51),(int) (51),(int) (51)));
 //BA.debugLineNum = 435;BA.debugLine="icoAlerta.TextSize = 40";
mostCurrent._icoalerta.setTextSize((float) (40));
 //BA.debugLineNum = 436;BA.debugLine="icoAnalizar.TextSize = 40";
mostCurrent._icoanalizar.setTextSize((float) (40));
 //BA.debugLineNum = 437;BA.debugLine="icoMapa.TextSize = 40";
mostCurrent._icomapa.setTextSize((float) (40));
 //BA.debugLineNum = 438;BA.debugLine="icoPerfil.TextSize = 40";
mostCurrent._icoperfil.setTextSize((float) (40));
 //BA.debugLineNum = 439;BA.debugLine="btnMapa.Text = \"Explorar\"";
mostCurrent._btnmapa.setText(BA.ObjectToCharSequence("Explorar"));
 //BA.debugLineNum = 440;BA.debugLine="btnAnalizar.Text = \"Analizar\"";
mostCurrent._btnanalizar.setText(BA.ObjectToCharSequence("Analizar"));
 //BA.debugLineNum = 441;BA.debugLine="btnPerfil.Text  = \"Perfil\"";
mostCurrent._btnperfil.setText(BA.ObjectToCharSequence("Perfil"));
 //BA.debugLineNum = 442;BA.debugLine="icoInformacion.SetTextColorAnimated(300, Colors.w";
mostCurrent._icoinformacion.SetTextColorAnimated((int) (300),anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 443;BA.debugLine="icoInformacion.SetTextSizeAnimated(300,50)";
mostCurrent._icoinformacion.SetTextSizeAnimated((int) (300),(float) (50));
 //BA.debugLineNum = 444;BA.debugLine="btnCirculo.SetLayoutAnimated(300, (btnInformacion";
mostCurrent._btncirculo.SetLayoutAnimated((int) (300),(int) ((mostCurrent._btninformacion.getLeft()-((mostCurrent._btncirculo.getWidth()-mostCurrent._btninformacion.getWidth())/(double)2))),mostCurrent._btncirculo.getTop(),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (80)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (80)));
 //BA.debugLineNum = 445;BA.debugLine="btnInformacion.Text = \"\"";
mostCurrent._btninformacion.setText(BA.ObjectToCharSequence(""));
 //BA.debugLineNum = 446;BA.debugLine="End Sub";
return "";
}
public static void  _icoalerta_click() throws Exception{
ResumableSub_icoAlerta_Click rsub = new ResumableSub_icoAlerta_Click(null);
rsub.resume(processBA, null);
}
public static class ResumableSub_icoAlerta_Click extends BA.ResumableSub {
public ResumableSub_icoAlerta_Click(appear.pnud.preservamos.form_main parent) {
this.parent = parent;
}
appear.pnud.preservamos.form_main parent;
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
 //BA.debugLineNum = 469;BA.debugLine="icoMapa.TextColor = Colors.ARGB(255,51,51,51)";
parent.mostCurrent._icomapa.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (51),(int) (51),(int) (51)));
 //BA.debugLineNum = 470;BA.debugLine="icoAnalizar.TextColor = Colors.ARGB(255,51,51,51)";
parent.mostCurrent._icoanalizar.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (51),(int) (51),(int) (51)));
 //BA.debugLineNum = 471;BA.debugLine="icoInformacion.TextColor = Colors.ARGB(255,51,51,";
parent.mostCurrent._icoinformacion.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (51),(int) (51),(int) (51)));
 //BA.debugLineNum = 472;BA.debugLine="icoPerfil.TextColor = Colors.ARGB(255,51,51,51)";
parent.mostCurrent._icoperfil.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (51),(int) (51),(int) (51)));
 //BA.debugLineNum = 473;BA.debugLine="icoMapa.TextSize = 40";
parent.mostCurrent._icomapa.setTextSize((float) (40));
 //BA.debugLineNum = 474;BA.debugLine="icoAnalizar.TextSize = 40";
parent.mostCurrent._icoanalizar.setTextSize((float) (40));
 //BA.debugLineNum = 475;BA.debugLine="icoInformacion.TextSize = 40";
parent.mostCurrent._icoinformacion.setTextSize((float) (40));
 //BA.debugLineNum = 476;BA.debugLine="icoPerfil.TextSize = 40";
parent.mostCurrent._icoperfil.setTextSize((float) (40));
 //BA.debugLineNum = 477;BA.debugLine="btnMapa.Text = \"Explorar\"";
parent.mostCurrent._btnmapa.setText(BA.ObjectToCharSequence("Explorar"));
 //BA.debugLineNum = 478;BA.debugLine="btnInformacion.Text = \"Municipio\"";
parent.mostCurrent._btninformacion.setText(BA.ObjectToCharSequence("Municipio"));
 //BA.debugLineNum = 479;BA.debugLine="btnAnalizar.Text = \"Analizar\"";
parent.mostCurrent._btnanalizar.setText(BA.ObjectToCharSequence("Analizar"));
 //BA.debugLineNum = 480;BA.debugLine="btnPerfil.Text = \"Perfil\"";
parent.mostCurrent._btnperfil.setText(BA.ObjectToCharSequence("Perfil"));
 //BA.debugLineNum = 482;BA.debugLine="icoAlerta.SetTextColorAnimated(300, Colors.white)";
parent.mostCurrent._icoalerta.SetTextColorAnimated((int) (300),anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 483;BA.debugLine="icoAlerta.SetTextSizeAnimated(300,50)";
parent.mostCurrent._icoalerta.SetTextSizeAnimated((int) (300),(float) (50));
 //BA.debugLineNum = 484;BA.debugLine="btnCirculo.SetLayoutAnimated(300, (icoAlerta.Left";
parent.mostCurrent._btncirculo.SetLayoutAnimated((int) (300),(int) ((parent.mostCurrent._icoalerta.getLeft()-((parent.mostCurrent._btncirculo.getWidth()-parent.mostCurrent._icoalerta.getWidth())/(double)2))),parent.mostCurrent._btncirculo.getTop(),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (80)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (80)));
 //BA.debugLineNum = 485;BA.debugLine="icoAlerta.Text = \"\"";
parent.mostCurrent._icoalerta.setText(BA.ObjectToCharSequence(""));
 //BA.debugLineNum = 488;BA.debugLine="Msgbox2Async(\"Podrás emitir un alerta verde sobre";
anywheresoftware.b4a.keywords.Common.Msgbox2Async(BA.ObjectToCharSequence("Podrás emitir un alerta verde sobre algún evento ecológico, como una floración de algas, una mortandad de peces, o un derrame industrial!"),BA.ObjectToCharSequence("¡ALERTA VERDE!"),"Proseguir!","","Cancelar alerta",(anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper(), (android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null)),processBA,anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 489;BA.debugLine="Wait For Msgbox_Result (Result As Int)";
anywheresoftware.b4a.keywords.Common.WaitFor("msgbox_result", processBA, this, null);
this.state = 17;
return;
case 17:
//C
this.state = 1;
_result = (Integer) result[0];
;
 //BA.debugLineNum = 490;BA.debugLine="If Result = DialogResponse.POSITIVE Then";
if (true) break;

case 1:
//if
this.state = 16;
if (_result==anywheresoftware.b4a.keywords.Common.DialogResponse.POSITIVE) { 
this.state = 3;
}else {
this.state = 5;
}if (true) break;

case 3:
//C
this.state = 16;
 //BA.debugLineNum = 491;BA.debugLine="StartActivity(Alertas)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(parent.mostCurrent._alertas.getObject()));
 if (true) break;

case 5:
//C
this.state = 6;
 //BA.debugLineNum = 493;BA.debugLine="If tabStripMain.CurrentPage = 0 Then";
if (true) break;

case 6:
//if
this.state = 15;
if (parent.mostCurrent._tabstripmain.getCurrentPage()==0) { 
this.state = 8;
}else if(parent.mostCurrent._tabstripmain.getCurrentPage()==1) { 
this.state = 10;
}else if(parent.mostCurrent._tabstripmain.getCurrentPage()==2) { 
this.state = 12;
}else if(parent.mostCurrent._tabstripmain.getCurrentPage()==3) { 
this.state = 14;
}if (true) break;

case 8:
//C
this.state = 15;
 //BA.debugLineNum = 494;BA.debugLine="icoAlerta.TextColor = Colors.ARGB(255,51,51,51)";
parent.mostCurrent._icoalerta.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (51),(int) (51),(int) (51)));
 //BA.debugLineNum = 495;BA.debugLine="icoAnalizar.TextColor = Colors.ARGB(255,51,51,5";
parent.mostCurrent._icoanalizar.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (51),(int) (51),(int) (51)));
 //BA.debugLineNum = 496;BA.debugLine="icoInformacion.TextColor = Colors.ARGB(255,51,5";
parent.mostCurrent._icoinformacion.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (51),(int) (51),(int) (51)));
 //BA.debugLineNum = 497;BA.debugLine="icoPerfil.TextColor = Colors.ARGB(255,51,51,51)";
parent.mostCurrent._icoperfil.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (51),(int) (51),(int) (51)));
 //BA.debugLineNum = 498;BA.debugLine="icoAlerta.TextSize = 40";
parent.mostCurrent._icoalerta.setTextSize((float) (40));
 //BA.debugLineNum = 499;BA.debugLine="icoAnalizar.TextSize = 40";
parent.mostCurrent._icoanalizar.setTextSize((float) (40));
 //BA.debugLineNum = 500;BA.debugLine="icoInformacion.TextSize = 40";
parent.mostCurrent._icoinformacion.setTextSize((float) (40));
 //BA.debugLineNum = 501;BA.debugLine="icoPerfil.TextSize = 40";
parent.mostCurrent._icoperfil.setTextSize((float) (40));
 //BA.debugLineNum = 502;BA.debugLine="btnInformacion.Text = \"Municipio\"";
parent.mostCurrent._btninformacion.setText(BA.ObjectToCharSequence("Municipio"));
 //BA.debugLineNum = 503;BA.debugLine="btnAnalizar.Text = \"Analizar\"";
parent.mostCurrent._btnanalizar.setText(BA.ObjectToCharSequence("Analizar"));
 //BA.debugLineNum = 504;BA.debugLine="btnPerfil.Text  = \"Perfil\"";
parent.mostCurrent._btnperfil.setText(BA.ObjectToCharSequence("Perfil"));
 //BA.debugLineNum = 506;BA.debugLine="icoMapa.SetTextColorAnimated(300, Colors.white)";
parent.mostCurrent._icomapa.SetTextColorAnimated((int) (300),anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 507;BA.debugLine="icoMapa.SetTextSizeAnimated(300,50)";
parent.mostCurrent._icomapa.SetTextSizeAnimated((int) (300),(float) (50));
 //BA.debugLineNum = 508;BA.debugLine="btnCirculo.SetLayoutAnimated(300, (icoMapa.Left";
parent.mostCurrent._btncirculo.SetLayoutAnimated((int) (300),(int) ((parent.mostCurrent._icomapa.getLeft()-((parent.mostCurrent._btncirculo.getWidth()-parent.mostCurrent._icomapa.getWidth())/(double)2))),parent.mostCurrent._btncirculo.getTop(),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (80)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (80)));
 //BA.debugLineNum = 509;BA.debugLine="btnMapa.Text = \"";
parent.mostCurrent._btnmapa.setText(BA.ObjectToCharSequence(""));
 if (true) break;

case 10:
//C
this.state = 15;
 //BA.debugLineNum = 511;BA.debugLine="icoAlerta.TextColor = Colors.ARGB(255,51,51,51)";
parent.mostCurrent._icoalerta.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (51),(int) (51),(int) (51)));
 //BA.debugLineNum = 512;BA.debugLine="icoAnalizar.TextColor = Colors.ARGB(255,51,51,5";
parent.mostCurrent._icoanalizar.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (51),(int) (51),(int) (51)));
 //BA.debugLineNum = 513;BA.debugLine="icoMapa.TextColor = Colors.ARGB(255,51,51,51)";
parent.mostCurrent._icomapa.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (51),(int) (51),(int) (51)));
 //BA.debugLineNum = 514;BA.debugLine="icoPerfil.TextColor = Colors.ARGB(255,51,51,51)";
parent.mostCurrent._icoperfil.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (51),(int) (51),(int) (51)));
 //BA.debugLineNum = 515;BA.debugLine="icoAlerta.TextSize = 40";
parent.mostCurrent._icoalerta.setTextSize((float) (40));
 //BA.debugLineNum = 516;BA.debugLine="icoAnalizar.TextSize = 40";
parent.mostCurrent._icoanalizar.setTextSize((float) (40));
 //BA.debugLineNum = 517;BA.debugLine="icoMapa.TextSize = 40";
parent.mostCurrent._icomapa.setTextSize((float) (40));
 //BA.debugLineNum = 518;BA.debugLine="icoPerfil.TextSize = 40";
parent.mostCurrent._icoperfil.setTextSize((float) (40));
 //BA.debugLineNum = 519;BA.debugLine="btnMapa.Text = \"Explorar\"";
parent.mostCurrent._btnmapa.setText(BA.ObjectToCharSequence("Explorar"));
 //BA.debugLineNum = 520;BA.debugLine="btnAnalizar.Text = \"Analizar\"";
parent.mostCurrent._btnanalizar.setText(BA.ObjectToCharSequence("Analizar"));
 //BA.debugLineNum = 521;BA.debugLine="btnPerfil.Text  = \"Perfil\"";
parent.mostCurrent._btnperfil.setText(BA.ObjectToCharSequence("Perfil"));
 //BA.debugLineNum = 523;BA.debugLine="icoInformacion.SetTextColorAnimated(300, Colors";
parent.mostCurrent._icoinformacion.SetTextColorAnimated((int) (300),anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 524;BA.debugLine="icoInformacion.SetTextSizeAnimated(300,50)";
parent.mostCurrent._icoinformacion.SetTextSizeAnimated((int) (300),(float) (50));
 //BA.debugLineNum = 525;BA.debugLine="btnCirculo.SetLayoutAnimated(300, (btnInformaci";
parent.mostCurrent._btncirculo.SetLayoutAnimated((int) (300),(int) ((parent.mostCurrent._btninformacion.getLeft()-((parent.mostCurrent._btncirculo.getWidth()-parent.mostCurrent._btninformacion.getWidth())/(double)2))),parent.mostCurrent._btncirculo.getTop(),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (80)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (80)));
 //BA.debugLineNum = 526;BA.debugLine="btnInformacion.Text = \"\"";
parent.mostCurrent._btninformacion.setText(BA.ObjectToCharSequence(""));
 if (true) break;

case 12:
//C
this.state = 15;
 //BA.debugLineNum = 528;BA.debugLine="icoMapa.TextColor = Colors.ARGB(255,51,51,51)";
parent.mostCurrent._icomapa.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (51),(int) (51),(int) (51)));
 //BA.debugLineNum = 529;BA.debugLine="icoInformacion.TextColor = Colors.ARGB(255,51,5";
parent.mostCurrent._icoinformacion.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (51),(int) (51),(int) (51)));
 //BA.debugLineNum = 530;BA.debugLine="icoPerfil.TextColor = Colors.ARGB(255,51,51,51)";
parent.mostCurrent._icoperfil.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (51),(int) (51),(int) (51)));
 //BA.debugLineNum = 531;BA.debugLine="icoMapa.TextSize = 40";
parent.mostCurrent._icomapa.setTextSize((float) (40));
 //BA.debugLineNum = 532;BA.debugLine="icoInformacion.TextSize = 40";
parent.mostCurrent._icoinformacion.setTextSize((float) (40));
 //BA.debugLineNum = 533;BA.debugLine="icoPerfil.TextSize = 40";
parent.mostCurrent._icoperfil.setTextSize((float) (40));
 //BA.debugLineNum = 534;BA.debugLine="btnMapa.Text = \"Explorar\"";
parent.mostCurrent._btnmapa.setText(BA.ObjectToCharSequence("Explorar"));
 //BA.debugLineNum = 535;BA.debugLine="btnInformacion.Text = \"Municipio\"";
parent.mostCurrent._btninformacion.setText(BA.ObjectToCharSequence("Municipio"));
 //BA.debugLineNum = 536;BA.debugLine="btnPerfil.Text  = \"Perfil\"";
parent.mostCurrent._btnperfil.setText(BA.ObjectToCharSequence("Perfil"));
 //BA.debugLineNum = 538;BA.debugLine="icoAnalizar.SetTextColorAnimated(300, Colors.wh";
parent.mostCurrent._icoanalizar.SetTextColorAnimated((int) (300),anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 539;BA.debugLine="icoAnalizar.SetTextSizeAnimated(300,50)";
parent.mostCurrent._icoanalizar.SetTextSizeAnimated((int) (300),(float) (50));
 //BA.debugLineNum = 540;BA.debugLine="btnCirculo.SetLayoutAnimated(300, (btnAnalizar.";
parent.mostCurrent._btncirculo.SetLayoutAnimated((int) (300),(int) ((parent.mostCurrent._btnanalizar.getLeft()-((parent.mostCurrent._btncirculo.getWidth()-parent.mostCurrent._btnanalizar.getWidth())/(double)2))),parent.mostCurrent._btncirculo.getTop(),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (80)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (80)));
 //BA.debugLineNum = 541;BA.debugLine="btnAnalizar.Text = \"\"";
parent.mostCurrent._btnanalizar.setText(BA.ObjectToCharSequence(""));
 if (true) break;

case 14:
//C
this.state = 15;
 //BA.debugLineNum = 543;BA.debugLine="icoMapa.TextColor = Colors.ARGB(255,51,51,51)";
parent.mostCurrent._icomapa.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (51),(int) (51),(int) (51)));
 //BA.debugLineNum = 544;BA.debugLine="icoAnalizar.TextColor = Colors.ARGB(255,51,51,5";
parent.mostCurrent._icoanalizar.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (51),(int) (51),(int) (51)));
 //BA.debugLineNum = 545;BA.debugLine="icoInformacion.TextColor = Colors.ARGB(255,51,5";
parent.mostCurrent._icoinformacion.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (51),(int) (51),(int) (51)));
 //BA.debugLineNum = 546;BA.debugLine="icoAlerta.TextColor = Colors.ARGB(255,51,51,51)";
parent.mostCurrent._icoalerta.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (51),(int) (51),(int) (51)));
 //BA.debugLineNum = 547;BA.debugLine="icoMapa.TextSize = 40";
parent.mostCurrent._icomapa.setTextSize((float) (40));
 //BA.debugLineNum = 548;BA.debugLine="icoAnalizar.TextSize = 40";
parent.mostCurrent._icoanalizar.setTextSize((float) (40));
 //BA.debugLineNum = 549;BA.debugLine="icoInformacion.TextSize = 40";
parent.mostCurrent._icoinformacion.setTextSize((float) (40));
 //BA.debugLineNum = 550;BA.debugLine="icoAlerta.TextSize = 40";
parent.mostCurrent._icoalerta.setTextSize((float) (40));
 //BA.debugLineNum = 551;BA.debugLine="btnMapa.Text = \"Explorar\"";
parent.mostCurrent._btnmapa.setText(BA.ObjectToCharSequence("Explorar"));
 //BA.debugLineNum = 552;BA.debugLine="btnInformacion.Text = \"Municipio\"";
parent.mostCurrent._btninformacion.setText(BA.ObjectToCharSequence("Municipio"));
 //BA.debugLineNum = 553;BA.debugLine="btnAnalizar.Text = \"Analizar\"";
parent.mostCurrent._btnanalizar.setText(BA.ObjectToCharSequence("Analizar"));
 //BA.debugLineNum = 555;BA.debugLine="icoPerfil.SetTextColorAnimated(300, Colors.whit";
parent.mostCurrent._icoperfil.SetTextColorAnimated((int) (300),anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 556;BA.debugLine="icoPerfil.SetTextSizeAnimated(300,50)";
parent.mostCurrent._icoperfil.SetTextSizeAnimated((int) (300),(float) (50));
 //BA.debugLineNum = 557;BA.debugLine="btnCirculo.SetLayoutAnimated(300, (icoPerfil.Le";
parent.mostCurrent._btncirculo.SetLayoutAnimated((int) (300),(int) ((parent.mostCurrent._icoperfil.getLeft()-((parent.mostCurrent._btncirculo.getWidth()-parent.mostCurrent._icoperfil.getWidth())/(double)2))),parent.mostCurrent._btncirculo.getTop(),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (80)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (80)));
 //BA.debugLineNum = 558;BA.debugLine="btnPerfil.Text = \"\"";
parent.mostCurrent._btnperfil.setText(BA.ObjectToCharSequence(""));
 if (true) break;

case 15:
//C
this.state = 16;
;
 if (true) break;

case 16:
//C
this.state = -1;
;
 //BA.debugLineNum = 561;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public static String  _icoanalizar_click() throws Exception{
 //BA.debugLineNum = 458;BA.debugLine="Private Sub icoAnalizar_Click";
 //BA.debugLineNum = 459;BA.debugLine="tabStripMain.ScrollTo(2, True)";
mostCurrent._tabstripmain.ScrollTo((int) (2),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 460;BA.debugLine="End Sub";
return "";
}
public static String  _icoinformacion_click() throws Exception{
 //BA.debugLineNum = 451;BA.debugLine="Private Sub icoInformacion_Click";
 //BA.debugLineNum = 452;BA.debugLine="tabStripMain.ScrollTo(1, True)";
mostCurrent._tabstripmain.ScrollTo((int) (1),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 453;BA.debugLine="End Sub";
return "";
}
public static String  _icomapa_click() throws Exception{
 //BA.debugLineNum = 418;BA.debugLine="Private Sub icoMapa_Click";
 //BA.debugLineNum = 419;BA.debugLine="tabStripMain.ScrollTo(0, True)";
mostCurrent._tabstripmain.ScrollTo((int) (0),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 420;BA.debugLine="End Sub";
return "";
}
public static String  _icoperfil_click() throws Exception{
 //BA.debugLineNum = 461;BA.debugLine="Private Sub icoPerfil_Click";
 //BA.debugLineNum = 462;BA.debugLine="tabStripMain.ScrollTo(3, True)";
mostCurrent._tabstripmain.ScrollTo((int) (3),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 463;BA.debugLine="End Sub";
return "";
}
public static String  _imgbadgelevel_click() throws Exception{
 //BA.debugLineNum = 1717;BA.debugLine="Sub imgBadgeLevel_Click ()";
 //BA.debugLineNum = 1721;BA.debugLine="End Sub";
return "";
}
public static String  _lblcomofuncionaanalizar_click() throws Exception{
 //BA.debugLineNum = 977;BA.debugLine="Sub lblComoFuncionaAnalizar_Click";
 //BA.debugLineNum = 980;BA.debugLine="fondogris.Initialize(\"fondogris\")";
mostCurrent._fondogris.Initialize(mostCurrent.activityBA,"fondogris");
 //BA.debugLineNum = 981;BA.debugLine="fondogris.Color = Colors.ARGB(150,0,0,0)";
mostCurrent._fondogris.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (150),(int) (0),(int) (0),(int) (0)));
 //BA.debugLineNum = 982;BA.debugLine="Activity.AddView(fondogris, 0, 0, 100%x, 100%y)";
mostCurrent._activity.AddView((android.view.View)(mostCurrent._fondogris.getObject()),(int) (0),(int) (0),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA));
 //BA.debugLineNum = 984;BA.debugLine="panelComoFunciona.Initialize(\"\")";
mostCurrent._panelcomofunciona.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 985;BA.debugLine="panelComoFunciona.LoadLayout(\"dialog_comoFunciona";
mostCurrent._panelcomofunciona.LoadLayout("dialog_comoFunciona",mostCurrent.activityBA);
 //BA.debugLineNum = 986;BA.debugLine="Activity.AddView(panelComoFunciona, 10%x, 20%y, 8";
mostCurrent._activity.AddView((android.view.View)(mostCurrent._panelcomofunciona.getObject()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (10),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (20),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (80),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (80),mostCurrent.activityBA));
 //BA.debugLineNum = 987;BA.debugLine="End Sub";
return "";
}
public static String  _lblcomofuncionaentendido_click() throws Exception{
 //BA.debugLineNum = 988;BA.debugLine="Private Sub lblComoFuncionaEntendido_Click";
 //BA.debugLineNum = 989;BA.debugLine="panelComoFunciona.RemoveView";
mostCurrent._panelcomofunciona.RemoveView();
 //BA.debugLineNum = 990;BA.debugLine="fondogris.RemoveView";
mostCurrent._fondogris.RemoveView();
 //BA.debugLineNum = 991;BA.debugLine="End Sub";
return "";
}
public static String  _lblmunicipio_puntos_totales_click() throws Exception{
 //BA.debugLineNum = 1360;BA.debugLine="Sub lblMunicipio_puntos_totales_Click";
 //BA.debugLineNum = 1363;BA.debugLine="fondogris.Initialize(\"fondogris\")";
mostCurrent._fondogris.Initialize(mostCurrent.activityBA,"fondogris");
 //BA.debugLineNum = 1364;BA.debugLine="fondogris.Color = Colors.ARGB(150,0,0,0)";
mostCurrent._fondogris.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (150),(int) (0),(int) (0),(int) (0)));
 //BA.debugLineNum = 1365;BA.debugLine="Activity.AddView(fondogris, 0, 0, 100%x, 100%y)";
mostCurrent._activity.AddView((android.view.View)(mostCurrent._fondogris.getObject()),(int) (0),(int) (0),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA));
 //BA.debugLineNum = 1367;BA.debugLine="panelComoFunciona.Initialize(\"\")";
mostCurrent._panelcomofunciona.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 1368;BA.debugLine="panelComoFunciona.LoadLayout(\"dialog_comoFunciona";
mostCurrent._panelcomofunciona.LoadLayout("dialog_comoFunciona",mostCurrent.activityBA);
 //BA.debugLineNum = 1369;BA.debugLine="Activity.AddView(panelComoFunciona, 10%x, 20%y, 8";
mostCurrent._activity.AddView((android.view.View)(mostCurrent._panelcomofunciona.getObject()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (10),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (20),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (80),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (80),mostCurrent.activityBA));
 //BA.debugLineNum = 1370;BA.debugLine="lblComoFuncionaContenido.text = \"Cada vez que un";
mostCurrent._lblcomofuncionacontenido.setText(BA.ObjectToCharSequence("Cada vez que un miembro de este municipio realiza un análisis del hábitat, suma puntos para toda la comunidad!"+anywheresoftware.b4a.keywords.Common.CRLF+anywheresoftware.b4a.keywords.Common.CRLF+"Esos puntos podrán ser canjeados por beneficios para el municipio :)"));
 //BA.debugLineNum = 1371;BA.debugLine="lblComoFuncionaTitle.text = \"Esfuerzo colaborativ";
mostCurrent._lblcomofuncionatitle.setText(BA.ObjectToCharSequence("Esfuerzo colaborativo"));
 //BA.debugLineNum = 1372;BA.debugLine="End Sub";
return "";
}
public static String  _lblnocompartir_click() throws Exception{
 //BA.debugLineNum = 1871;BA.debugLine="Sub lblNoCompartir_Click";
 //BA.debugLineNum = 1872;BA.debugLine="pnlShare.RemoveView";
mostCurrent._pnlshare.RemoveView();
 //BA.debugLineNum = 1873;BA.debugLine="fondogris_share.RemoveView";
mostCurrent._fondogris_share.RemoveView();
 //BA.debugLineNum = 1874;BA.debugLine="End Sub";
return "";
}
public static String  _loadform_main() throws Exception{
 //BA.debugLineNum = 375;BA.debugLine="Sub LoadForm_Main";
 //BA.debugLineNum = 376;BA.debugLine="Log(\"userName in LoadForm_Main:\" & Main.username)";
anywheresoftware.b4a.keywords.Common.LogImpl("41441793","userName in LoadForm_Main:"+mostCurrent._main._username /*String*/ ,0);
 //BA.debugLineNum = 377;BA.debugLine="Log(\"firstuse in LoadForm_Main:\" & firstuse)";
anywheresoftware.b4a.keywords.Common.LogImpl("41441794","firstuse in LoadForm_Main:"+mostCurrent._firstuse,0);
 //BA.debugLineNum = 380;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 381;BA.debugLine="Activity.LoadLayout(\"layMain\")";
mostCurrent._activity.LoadLayout("layMain",mostCurrent.activityBA);
 //BA.debugLineNum = 384;BA.debugLine="CrearMenu";
_crearmenu();
 //BA.debugLineNum = 388;BA.debugLine="tabStripMain.LoadLayout(\"layMainExplorar\", \"\")";
mostCurrent._tabstripmain.LoadLayout("layMainExplorar",BA.ObjectToCharSequence(""));
 //BA.debugLineNum = 389;BA.debugLine="tabStripMain.LoadLayout(\"layMainMunicipio\", \"\")";
mostCurrent._tabstripmain.LoadLayout("layMainMunicipio",BA.ObjectToCharSequence(""));
 //BA.debugLineNum = 390;BA.debugLine="tabStripMain.LoadLayout(\"layMainReportar\", \"\")";
mostCurrent._tabstripmain.LoadLayout("layMainReportar",BA.ObjectToCharSequence(""));
 //BA.debugLineNum = 391;BA.debugLine="tabStripMain.LoadLayout(\"layProfile\", \"\")";
mostCurrent._tabstripmain.LoadLayout("layProfile",BA.ObjectToCharSequence(""));
 //BA.debugLineNum = 392;BA.debugLine="tabStripMain.ScrollTo(2,False)";
mostCurrent._tabstripmain.ScrollTo((int) (2),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 393;BA.debugLine="scrReportar_Modulos.Panel.LoadLayout(\"layMainRepo";
mostCurrent._scrreportar_modulos.getPanel().LoadLayout("layMainReportar_Modulos",mostCurrent.activityBA);
 //BA.debugLineNum = 396;BA.debugLine="If Main.username <> \"None\" And Main.username <> \"";
if ((mostCurrent._main._username /*String*/ ).equals("None") == false && (mostCurrent._main._username /*String*/ ).equals("") == false && mostCurrent._main._username /*String*/ != null && (mostCurrent._main._username /*String*/ ).equals("null") == false) { 
 };
 //BA.debugLineNum = 401;BA.debugLine="End Sub";
return "";
}
public static String  _lstachievements_itemclick(int _position,Object _value) throws Exception{
 //BA.debugLineNum = 1722;BA.debugLine="Sub lstAchievements_ItemClick (Position As Int, Va";
 //BA.debugLineNum = 1723;BA.debugLine="If Value = \"Novato\" Then";
if ((_value).equals((Object)("Novato"))) { 
 //BA.debugLineNum = 1724;BA.debugLine="ToastMessageShow(\"Completaste un análisis ambien";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Completaste un análisis ambiental!"),anywheresoftware.b4a.keywords.Common.False);
 }else if((_value).equals((Object)("Aprendiz"))) { 
 //BA.debugLineNum = 1726;BA.debugLine="ToastMessageShow(\"Completaste tres análisis ambi";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Completaste tres análisis ambientales!"),anywheresoftware.b4a.keywords.Common.False);
 }else if((_value).equals((Object)("Aventurero"))) { 
 //BA.debugLineNum = 1728;BA.debugLine="ToastMessageShow(\"Completaste cinco análisis amb";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Completaste cinco análisis ambientales!"),anywheresoftware.b4a.keywords.Common.False);
 }else if((_value).equals((Object)("Explorador"))) { 
 //BA.debugLineNum = 1730;BA.debugLine="ToastMessageShow(\"Completaste quince análisis am";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Completaste quince análisis ambientales!"),anywheresoftware.b4a.keywords.Common.False);
 }else if((_value).equals((Object)("Legendario"))) { 
 //BA.debugLineNum = 1732;BA.debugLine="ToastMessageShow(\"Completaste treinta análisis a";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Completaste treinta análisis ambiental!"),anywheresoftware.b4a.keywords.Common.False);
 }else if((_value).equals((Object)("hidro-1"))) { 
 //BA.debugLineNum = 1734;BA.debugLine="ToastMessageShow(\"Completaste un análisis de hid";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Completaste un análisis de hidrología!"),anywheresoftware.b4a.keywords.Common.False);
 }else if((_value).equals((Object)("hidro-2"))) { 
 //BA.debugLineNum = 1736;BA.debugLine="ToastMessageShow(\"Completaste tres análisis de h";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Completaste tres análisis de hidrología!"),anywheresoftware.b4a.keywords.Common.False);
 }else if((_value).equals((Object)("hidro-3"))) { 
 //BA.debugLineNum = 1738;BA.debugLine="ToastMessageShow(\"Completaste diez análisis de h";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Completaste diez análisis de hidrología!"),anywheresoftware.b4a.keywords.Common.False);
 }else if((_value).equals((Object)("residuos-1"))) { 
 //BA.debugLineNum = 1740;BA.debugLine="ToastMessageShow(\"Completaste un análisis de res";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Completaste un análisis de residuos!"),anywheresoftware.b4a.keywords.Common.False);
 }else if((_value).equals((Object)("residuos-2"))) { 
 //BA.debugLineNum = 1742;BA.debugLine="ToastMessageShow(\"Completaste tres análisis de r";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Completaste tres análisis de residuos!"),anywheresoftware.b4a.keywords.Common.False);
 }else if((_value).equals((Object)("residuos-3"))) { 
 //BA.debugLineNum = 1744;BA.debugLine="ToastMessageShow(\"Completaste diez análisis de r";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Completaste diez análisis de residuos!"),anywheresoftware.b4a.keywords.Common.False);
 }else if((_value).equals((Object)("llanura"))) { 
 //BA.debugLineNum = 1746;BA.debugLine="ToastMessageShow(\"Completaste tu primera evaluac";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Completaste tu primera evaluación de un río de llanura"),anywheresoftware.b4a.keywords.Common.False);
 }else if((_value).equals((Object)("laguna"))) { 
 //BA.debugLineNum = 1748;BA.debugLine="ToastMessageShow(\"Completaste tu primera evaluac";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Completaste tu primera evaluación de una laguna"),anywheresoftware.b4a.keywords.Common.False);
 }else if((_value).equals((Object)("montana"))) { 
 //BA.debugLineNum = 1750;BA.debugLine="ToastMessageShow(\"Completaste tu primera evaluac";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Completaste tu primera evaluación de un río de sierras de Córdoba"),anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 1757;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 6;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 9;BA.debugLine="Dim IsGuest As Boolean";
_isguest = false;
 //BA.debugLineNum = 10;BA.debugLine="Dim fullidcurrentproject As String";
_fullidcurrentproject = "";
 //BA.debugLineNum = 11;BA.debugLine="Dim Geopartido As String";
_geopartido = "";
 //BA.debugLineNum = 12;BA.debugLine="End Sub";
return "";
}
public static String  _tabresettoanalizar() throws Exception{
 //BA.debugLineNum = 821;BA.debugLine="Sub tabResetToAnalizar";
 //BA.debugLineNum = 822;BA.debugLine="tabStripMain.ScrollTo(2, True)";
mostCurrent._tabstripmain.ScrollTo((int) (2),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 823;BA.debugLine="tabStripMain_PageSelected(2)";
_tabstripmain_pageselected((int) (2));
 //BA.debugLineNum = 824;BA.debugLine="End Sub";
return "";
}
public static String  _tabstripmain_pageselected(int _position) throws Exception{
 //BA.debugLineNum = 657;BA.debugLine="Sub tabStripMain_PageSelected (Position As Int)";
 //BA.debugLineNum = 658;BA.debugLine="Log(\"position:\" & Position)";
anywheresoftware.b4a.keywords.Common.LogImpl("42228225","position:"+BA.NumberToString(_position),0);
 //BA.debugLineNum = 659;BA.debugLine="If Position = 0 Then";
if (_position==0) { 
 //BA.debugLineNum = 664;BA.debugLine="pnlHidro.Visible = False";
mostCurrent._pnlhidro.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 665;BA.debugLine="pnlHabitat.Visible = False";
mostCurrent._pnlhabitat.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 666;BA.debugLine="pnlResiduos.Visible = False";
mostCurrent._pnlresiduos.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 667;BA.debugLine="pnlDescripcionAnalisis.Visible = False";
mostCurrent._pnldescripcionanalisis.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 668;BA.debugLine="lblAnalizar.Top = 90dip";
mostCurrent._lblanalizar.setTop(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (90)));
 //BA.debugLineNum = 669;BA.debugLine="btnReportar.Top = 160dip";
mostCurrent._btnreportar.setTop(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (160)));
 //BA.debugLineNum = 671;BA.debugLine="icoAnalizar.TextColor = Colors.ARGB(255,51,51,51";
mostCurrent._icoanalizar.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (51),(int) (51),(int) (51)));
 //BA.debugLineNum = 672;BA.debugLine="icoInformacion.TextColor = Colors.ARGB(255,51,51";
mostCurrent._icoinformacion.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (51),(int) (51),(int) (51)));
 //BA.debugLineNum = 673;BA.debugLine="icoPerfil.TextColor = Colors.ARGB(255,51,51,51)";
mostCurrent._icoperfil.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (51),(int) (51),(int) (51)));
 //BA.debugLineNum = 674;BA.debugLine="icoAnalizar.TextSize = 40";
mostCurrent._icoanalizar.setTextSize((float) (40));
 //BA.debugLineNum = 675;BA.debugLine="icoInformacion.TextSize = 40";
mostCurrent._icoinformacion.setTextSize((float) (40));
 //BA.debugLineNum = 676;BA.debugLine="icoPerfil.TextSize = 40";
mostCurrent._icoperfil.setTextSize((float) (40));
 //BA.debugLineNum = 677;BA.debugLine="btnInformacion.Text = \"Municipio\"";
mostCurrent._btninformacion.setText(BA.ObjectToCharSequence("Municipio"));
 //BA.debugLineNum = 678;BA.debugLine="btnAnalizar.Text = \"Analizar\"";
mostCurrent._btnanalizar.setText(BA.ObjectToCharSequence("Analizar"));
 //BA.debugLineNum = 679;BA.debugLine="btnPerfil.Text  = \"Perfil\"";
mostCurrent._btnperfil.setText(BA.ObjectToCharSequence("Perfil"));
 //BA.debugLineNum = 681;BA.debugLine="icoMapa.SetTextColorAnimated(300, Colors.white)";
mostCurrent._icomapa.SetTextColorAnimated((int) (300),anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 682;BA.debugLine="icoMapa.SetTextSizeAnimated(300,50)";
mostCurrent._icomapa.SetTextSizeAnimated((int) (300),(float) (50));
 //BA.debugLineNum = 683;BA.debugLine="btnCirculo.SetLayoutAnimated(300, (icoMapa.Left";
mostCurrent._btncirculo.SetLayoutAnimated((int) (300),(int) ((mostCurrent._icomapa.getLeft()-((mostCurrent._btncirculo.getWidth()-mostCurrent._icomapa.getWidth())/(double)2))),mostCurrent._btncirculo.getTop(),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (80)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (80)));
 //BA.debugLineNum = 684;BA.debugLine="btnMapa.Text = \"\"";
mostCurrent._btnmapa.setText(BA.ObjectToCharSequence(""));
 }else if(_position==1) { 
 //BA.debugLineNum = 690;BA.debugLine="pnlHidro.Visible = False";
mostCurrent._pnlhidro.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 691;BA.debugLine="pnlHabitat.Visible = False";
mostCurrent._pnlhabitat.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 692;BA.debugLine="pnlResiduos.Visible = False";
mostCurrent._pnlresiduos.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 693;BA.debugLine="pnlDescripcionAnalisis.Visible = False";
mostCurrent._pnldescripcionanalisis.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 694;BA.debugLine="btnReportar.Top = 160dip";
mostCurrent._btnreportar.setTop(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (160)));
 //BA.debugLineNum = 695;BA.debugLine="lblAnalizar.Top = btnReportar.Top - lblAnalizar.";
mostCurrent._lblanalizar.setTop((int) (mostCurrent._btnreportar.getTop()-mostCurrent._lblanalizar.getHeight()-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (20))));
 //BA.debugLineNum = 698;BA.debugLine="icoAnalizar.TextColor = Colors.ARGB(255,51,51,51";
mostCurrent._icoanalizar.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (51),(int) (51),(int) (51)));
 //BA.debugLineNum = 699;BA.debugLine="icoMapa.TextColor = Colors.ARGB(255,51,51,51)";
mostCurrent._icomapa.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (51),(int) (51),(int) (51)));
 //BA.debugLineNum = 700;BA.debugLine="icoPerfil.TextColor = Colors.ARGB(255,51,51,51)";
mostCurrent._icoperfil.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (51),(int) (51),(int) (51)));
 //BA.debugLineNum = 701;BA.debugLine="icoAlerta.TextSize = 40";
mostCurrent._icoalerta.setTextSize((float) (40));
 //BA.debugLineNum = 702;BA.debugLine="icoAnalizar.TextSize = 40";
mostCurrent._icoanalizar.setTextSize((float) (40));
 //BA.debugLineNum = 703;BA.debugLine="icoMapa.TextSize = 40";
mostCurrent._icomapa.setTextSize((float) (40));
 //BA.debugLineNum = 704;BA.debugLine="icoPerfil.TextSize = 40";
mostCurrent._icoperfil.setTextSize((float) (40));
 //BA.debugLineNum = 705;BA.debugLine="btnMapa.Text = \"Explorar\"";
mostCurrent._btnmapa.setText(BA.ObjectToCharSequence("Explorar"));
 //BA.debugLineNum = 706;BA.debugLine="btnAnalizar.Text = \"Analizar\"";
mostCurrent._btnanalizar.setText(BA.ObjectToCharSequence("Analizar"));
 //BA.debugLineNum = 707;BA.debugLine="btnPerfil.Text  = \"Perfil\"";
mostCurrent._btnperfil.setText(BA.ObjectToCharSequence("Perfil"));
 //BA.debugLineNum = 709;BA.debugLine="icoInformacion.SetTextColorAnimated(300, Colors.";
mostCurrent._icoinformacion.SetTextColorAnimated((int) (300),anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 710;BA.debugLine="icoInformacion.SetTextSizeAnimated(300,50)";
mostCurrent._icoinformacion.SetTextSizeAnimated((int) (300),(float) (50));
 //BA.debugLineNum = 711;BA.debugLine="btnCirculo.SetLayoutAnimated(300, (btnInformacio";
mostCurrent._btncirculo.SetLayoutAnimated((int) (300),(int) ((mostCurrent._btninformacion.getLeft()-((mostCurrent._btncirculo.getWidth()-mostCurrent._btninformacion.getWidth())/(double)2))),mostCurrent._btncirculo.getTop(),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (80)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (80)));
 //BA.debugLineNum = 712;BA.debugLine="btnInformacion.Text = \"\"";
mostCurrent._btninformacion.setText(BA.ObjectToCharSequence(""));
 //BA.debugLineNum = 715;BA.debugLine="If Main.strUserOrg = \"\" Then";
if ((mostCurrent._main._struserorg /*String*/ ).equals("")) { 
 //BA.debugLineNum = 716;BA.debugLine="imgLogoMunicipio.Visible = False";
mostCurrent._imglogomunicipio.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 717;BA.debugLine="gauge_Partido.mBase.Visible = False";
mostCurrent._gauge_partido._mbase /*anywheresoftware.b4a.objects.B4XViewWrapper*/ .setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 718;BA.debugLine="lblMunicipios_reportes.Visible = False";
mostCurrent._lblmunicipios_reportes.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 719;BA.debugLine="btnVerDatosMunicipio.Visible = False";
mostCurrent._btnverdatosmunicipio.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 720;BA.debugLine="btnVerEstadisticasMunicipio.Visible = False";
mostCurrent._btnverestadisticasmunicipio.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 721;BA.debugLine="lblMunicipio_puntos_totales.Visible = False";
mostCurrent._lblmunicipio_puntos_totales.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 722;BA.debugLine="btnMunicipioTW.Visible = False";
mostCurrent._btnmunicipiotw.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 723;BA.debugLine="btnMunicipioFB.Visible = False";
mostCurrent._btnmunicipiofb.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 724;BA.debugLine="btnMunicipioIG.Visible = False";
mostCurrent._btnmunicipioig.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 725;BA.debugLine="btnMunicipioYT.Visible = False";
mostCurrent._btnmunicipioyt.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 726;BA.debugLine="btnMasInfoMunicipio.Visible = False";
mostCurrent._btnmasinfomunicipio.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 727;BA.debugLine="btnCambiarMunicipio.Text = \"Seleccionar\"";
mostCurrent._btncambiarmunicipio.setText(BA.ObjectToCharSequence("Seleccionar"));
 //BA.debugLineNum = 728;BA.debugLine="lblMunicipio_texto_principal.Clear";
mostCurrent._lblmunicipio_texto_principal._clear();
 //BA.debugLineNum = 729;BA.debugLine="lblMunicipio_texto_principal.AddTextItem(\"Aún n";
mostCurrent._lblmunicipio_texto_principal._addtextitem((Object)("Aún no has seleccionado tu municipio, presioná el botón 'Seleccionar', abajo, para localizarte en el mapa"),(Object)(""));
 }else {
 //BA.debugLineNum = 747;BA.debugLine="update_Municipio";
_update_municipio();
 };
 }else if(_position==2) { 
 //BA.debugLineNum = 753;BA.debugLine="icoMapa.TextColor = Colors.ARGB(255,51,51,51)";
mostCurrent._icomapa.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (51),(int) (51),(int) (51)));
 //BA.debugLineNum = 754;BA.debugLine="icoInformacion.TextColor = Colors.ARGB(255,51,51";
mostCurrent._icoinformacion.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (51),(int) (51),(int) (51)));
 //BA.debugLineNum = 755;BA.debugLine="icoPerfil.TextColor = Colors.ARGB(255,51,51,51)";
mostCurrent._icoperfil.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (51),(int) (51),(int) (51)));
 //BA.debugLineNum = 756;BA.debugLine="icoMapa.TextSize = 40";
mostCurrent._icomapa.setTextSize((float) (40));
 //BA.debugLineNum = 757;BA.debugLine="icoInformacion.TextSize = 40";
mostCurrent._icoinformacion.setTextSize((float) (40));
 //BA.debugLineNum = 758;BA.debugLine="icoPerfil.TextSize = 40";
mostCurrent._icoperfil.setTextSize((float) (40));
 //BA.debugLineNum = 759;BA.debugLine="btnMapa.Text = \"Explorar\"";
mostCurrent._btnmapa.setText(BA.ObjectToCharSequence("Explorar"));
 //BA.debugLineNum = 760;BA.debugLine="btnInformacion.Text = \"Municipio\"";
mostCurrent._btninformacion.setText(BA.ObjectToCharSequence("Municipio"));
 //BA.debugLineNum = 761;BA.debugLine="btnPerfil.Text  = \"Perfil\"";
mostCurrent._btnperfil.setText(BA.ObjectToCharSequence("Perfil"));
 //BA.debugLineNum = 762;BA.debugLine="lblAnalizar.Top = 90dip";
mostCurrent._lblanalizar.setTop(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (90)));
 //BA.debugLineNum = 763;BA.debugLine="btnReportar.Top = 160dip";
mostCurrent._btnreportar.setTop(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (160)));
 //BA.debugLineNum = 765;BA.debugLine="icoAnalizar.SetTextColorAnimated(300, Colors.whi";
mostCurrent._icoanalizar.SetTextColorAnimated((int) (300),anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 766;BA.debugLine="icoAnalizar.SetTextSizeAnimated(300,50)";
mostCurrent._icoanalizar.SetTextSizeAnimated((int) (300),(float) (50));
 //BA.debugLineNum = 767;BA.debugLine="btnCirculo.SetLayoutAnimated(300, (btnAnalizar.L";
mostCurrent._btncirculo.SetLayoutAnimated((int) (300),(int) ((mostCurrent._btnanalizar.getLeft()-((mostCurrent._btncirculo.getWidth()-mostCurrent._btnanalizar.getWidth())/(double)2))),mostCurrent._btncirculo.getTop(),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (80)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (80)));
 //BA.debugLineNum = 768;BA.debugLine="btnAnalizar.Text = \"\"";
mostCurrent._btnanalizar.setText(BA.ObjectToCharSequence(""));
 }else if(_position==3) { 
 //BA.debugLineNum = 773;BA.debugLine="If Main.username = \"guest\" Or Main.username = \"No";
if ((mostCurrent._main._username /*String*/ ).equals("guest") || (mostCurrent._main._username /*String*/ ).equals("None") || (mostCurrent._main._username /*String*/ ).equals("") || (mostCurrent._main._username /*String*/ ).equals("null") || (mostCurrent._main._username /*String*/ ).equals("Null")) { 
 //BA.debugLineNum = 774;BA.debugLine="lblNivel.Visible = False";
mostCurrent._lblnivel.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 775;BA.debugLine="lblPuntos_Explicacion_1.Visible = False";
mostCurrent._lblpuntos_explicacion_1.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 776;BA.debugLine="lblPuntos_Explicacion_2.Visible = False";
mostCurrent._lblpuntos_explicacion_2.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 777;BA.debugLine="lblPuntos_Explicacion_3.Visible = False";
mostCurrent._lblpuntos_explicacion_3.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 778;BA.debugLine="btnActualizar.visible = False";
mostCurrent._btnactualizar.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 779;BA.debugLine="pgbLevel.Visible= False";
mostCurrent._pgblevel.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 780;BA.debugLine="lblPerfil_puntos_totales.Text = \"Necesita estar";
mostCurrent._lblperfil_puntos_totales.setText(BA.ObjectToCharSequence("Necesita estar registrado para ver su perfil"));
 //BA.debugLineNum = 781;BA.debugLine="lblTitleProfile.Text = \":(\"";
mostCurrent._lbltitleprofile.setText(BA.ObjectToCharSequence(":("));
 }else {
 //BA.debugLineNum = 783;BA.debugLine="lblNivel.Visible = True";
mostCurrent._lblnivel.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 784;BA.debugLine="lblPuntos_Explicacion_1.Visible = True";
mostCurrent._lblpuntos_explicacion_1.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 785;BA.debugLine="lblPuntos_Explicacion_2.Visible = True";
mostCurrent._lblpuntos_explicacion_2.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 786;BA.debugLine="lblPuntos_Explicacion_3.Visible = True";
mostCurrent._lblpuntos_explicacion_3.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 787;BA.debugLine="btnActualizar.visible = True";
mostCurrent._btnactualizar.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 788;BA.debugLine="pgbLevel.Visible= True";
mostCurrent._pgblevel.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 789;BA.debugLine="lblPerfil_puntos_totales.Text = \"Cargando...\"";
mostCurrent._lblperfil_puntos_totales.setText(BA.ObjectToCharSequence("Cargando..."));
 //BA.debugLineNum = 790;BA.debugLine="lblTitleProfile.Text = \"\"";
mostCurrent._lbltitleprofile.setText(BA.ObjectToCharSequence(""));
 //BA.debugLineNum = 791;BA.debugLine="calcular_puntos_ambientes";
_calcular_puntos_ambientes();
 };
 //BA.debugLineNum = 798;BA.debugLine="pnlHidro.Visible = False";
mostCurrent._pnlhidro.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 799;BA.debugLine="pnlHabitat.Visible = False";
mostCurrent._pnlhabitat.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 800;BA.debugLine="pnlResiduos.Visible = False";
mostCurrent._pnlresiduos.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 801;BA.debugLine="pnlDescripcionAnalisis.Visible = False";
mostCurrent._pnldescripcionanalisis.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 802;BA.debugLine="lblAnalizar.Top = 90dip";
mostCurrent._lblanalizar.setTop(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (90)));
 //BA.debugLineNum = 803;BA.debugLine="btnReportar.Top = 160dip";
mostCurrent._btnreportar.setTop(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (160)));
 //BA.debugLineNum = 805;BA.debugLine="icoMapa.TextColor = Colors.ARGB(255,51,51,51)";
mostCurrent._icomapa.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (51),(int) (51),(int) (51)));
 //BA.debugLineNum = 806;BA.debugLine="icoAnalizar.TextColor = Colors.ARGB(255,51,51,51";
mostCurrent._icoanalizar.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (51),(int) (51),(int) (51)));
 //BA.debugLineNum = 807;BA.debugLine="icoInformacion.TextColor = Colors.ARGB(255,51,51";
mostCurrent._icoinformacion.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (51),(int) (51),(int) (51)));
 //BA.debugLineNum = 808;BA.debugLine="icoMapa.TextSize = 40";
mostCurrent._icomapa.setTextSize((float) (40));
 //BA.debugLineNum = 809;BA.debugLine="icoAnalizar.TextSize = 40";
mostCurrent._icoanalizar.setTextSize((float) (40));
 //BA.debugLineNum = 810;BA.debugLine="icoInformacion.TextSize = 40";
mostCurrent._icoinformacion.setTextSize((float) (40));
 //BA.debugLineNum = 811;BA.debugLine="btnMapa.Text = \"Explorar\"";
mostCurrent._btnmapa.setText(BA.ObjectToCharSequence("Explorar"));
 //BA.debugLineNum = 812;BA.debugLine="btnInformacion.Text = \"Municipio\"";
mostCurrent._btninformacion.setText(BA.ObjectToCharSequence("Municipio"));
 //BA.debugLineNum = 813;BA.debugLine="btnAnalizar.Text = \"Analizar\"";
mostCurrent._btnanalizar.setText(BA.ObjectToCharSequence("Analizar"));
 //BA.debugLineNum = 815;BA.debugLine="icoPerfil.SetTextColorAnimated(300, Colors.white";
mostCurrent._icoperfil.SetTextColorAnimated((int) (300),anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 816;BA.debugLine="icoPerfil.SetTextSizeAnimated(300,50)";
mostCurrent._icoperfil.SetTextSizeAnimated((int) (300),(float) (50));
 //BA.debugLineNum = 817;BA.debugLine="btnCirculo.SetLayoutAnimated(300, (icoPerfil.Lef";
mostCurrent._btncirculo.SetLayoutAnimated((int) (300),(int) ((mostCurrent._icoperfil.getLeft()-((mostCurrent._btncirculo.getWidth()-mostCurrent._icoperfil.getWidth())/(double)2))),mostCurrent._btncirculo.getTop(),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (80)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (80)));
 //BA.debugLineNum = 818;BA.debugLine="btnPerfil.Text = \"\"";
mostCurrent._btnperfil.setText(BA.ObjectToCharSequence(""));
 };
 //BA.debugLineNum = 820;BA.debugLine="End Sub";
return "";
}
public static String  _update_municipio() throws Exception{
appear.pnud.preservamos.downloadservice._downloaddata _dd = null;
 //BA.debugLineNum = 1041;BA.debugLine="Sub update_Municipio";
 //BA.debugLineNum = 1042;BA.debugLine="lblTitleMunicipio.Text = Main.strUserOrg";
mostCurrent._lbltitlemunicipio.setText(BA.ObjectToCharSequence(mostCurrent._main._struserorg /*String*/ ));
 //BA.debugLineNum = 1043;BA.debugLine="Log(\"Testing internet\")";
anywheresoftware.b4a.keywords.Common.LogImpl("42949122","Testing internet",0);
 //BA.debugLineNum = 1044;BA.debugLine="Dim dd As DownloadData";
_dd = new appear.pnud.preservamos.downloadservice._downloaddata();
 //BA.debugLineNum = 1045;BA.debugLine="dd.url = Main.serverPath & \"/\" & Main.serverConne";
_dd.url /*String*/  = mostCurrent._main._serverpath /*String*/ +"/"+mostCurrent._main._serverconnectionfolder /*String*/ +"/getPartidos.php?nombre_partido="+mostCurrent._main._struserorg /*String*/ ;
 //BA.debugLineNum = 1046;BA.debugLine="dd.EventName = \"getPartidos\"";
_dd.EventName /*String*/  = "getPartidos";
 //BA.debugLineNum = 1047;BA.debugLine="dd.Target = Me";
_dd.Target /*Object*/  = form_main.getObject();
 //BA.debugLineNum = 1048;BA.debugLine="CallSubDelayed2(DownloadService, \"StartDownload\",";
anywheresoftware.b4a.keywords.Common.CallSubDelayed2(processBA,(Object)(mostCurrent._downloadservice.getObject()),"StartDownload",(Object)(_dd));
 //BA.debugLineNum = 1049;BA.debugLine="End Sub";
return "";
}
public static void  _update_perfil() throws Exception{
ResumableSub_update_perfil rsub = new ResumableSub_update_perfil(null);
rsub.resume(processBA, null);
}
public static class ResumableSub_update_perfil extends BA.ResumableSub {
public ResumableSub_update_perfil(appear.pnud.preservamos.form_main parent) {
this.parent = parent;
}
appear.pnud.preservamos.form_main parent;
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
 //BA.debugLineNum = 1673;BA.debugLine="If Main.hayinternet = True Then";
if (true) break;

case 1:
//if
this.state = 12;
if (parent.mostCurrent._main._hayinternet /*boolean*/ ==anywheresoftware.b4a.keywords.Common.True) { 
this.state = 3;
}else {
this.state = 5;
}if (true) break;

case 3:
//C
this.state = 12;
 //BA.debugLineNum = 1674;BA.debugLine="lblTitleProfile.Text = Main.username";
parent.mostCurrent._lbltitleprofile.setText(BA.ObjectToCharSequence(parent.mostCurrent._main._username /*String*/ ));
 //BA.debugLineNum = 1675;BA.debugLine="lblPerfil_puntos_totales.Text = Main.puntostotal";
parent.mostCurrent._lblperfil_puntos_totales.setText(BA.ObjectToCharSequence(parent.mostCurrent._main._puntostotales /*String*/ +" puntos"));
 //BA.debugLineNum = 1676;BA.debugLine="lblPuntos_Titulo_1.Text = Main.puntos_markers";
parent.mostCurrent._lblpuntos_titulo_1.setText(BA.ObjectToCharSequence(parent.mostCurrent._main._puntos_markers /*int*/ ));
 //BA.debugLineNum = 1677;BA.debugLine="lblPuntos_Titulo_2.Text = Main.puntos_evals_resi";
parent.mostCurrent._lblpuntos_titulo_2.setText(BA.ObjectToCharSequence(parent.mostCurrent._main._puntos_evals_residuos /*int*/ ));
 //BA.debugLineNum = 1678;BA.debugLine="lblPuntos_Titulo_3.Text = Main.puntos_evals_hidr";
parent.mostCurrent._lblpuntos_titulo_3.setText(BA.ObjectToCharSequence(parent.mostCurrent._main._puntos_evals_hidro /*int*/ ));
 //BA.debugLineNum = 1679;BA.debugLine="lblPerfil_puntos_totales.Visible = True";
parent.mostCurrent._lblperfil_puntos_totales.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1680;BA.debugLine="lblPuntos_Titulo_1.Visible = True";
parent.mostCurrent._lblpuntos_titulo_1.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1681;BA.debugLine="lblPuntos_Titulo_2.Visible = True";
parent.mostCurrent._lblpuntos_titulo_2.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1682;BA.debugLine="lblPuntos_Titulo_3.Visible = True";
parent.mostCurrent._lblpuntos_titulo_3.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1683;BA.debugLine="lblPuntos_Explicacion_1.Visible = True";
parent.mostCurrent._lblpuntos_explicacion_1.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1684;BA.debugLine="lblPuntos_Explicacion_2.Visible = True";
parent.mostCurrent._lblpuntos_explicacion_2.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1685;BA.debugLine="lblPuntos_Explicacion_3.Visible = True";
parent.mostCurrent._lblpuntos_explicacion_3.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1686;BA.debugLine="lblNivel.Visible = True";
parent.mostCurrent._lblnivel.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1687;BA.debugLine="lblPerfil_puntos_totales.Visible = True";
parent.mostCurrent._lblperfil_puntos_totales.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1688;BA.debugLine="pgbLevel.Visible = True";
parent.mostCurrent._pgblevel.setVisible(anywheresoftware.b4a.keywords.Common.True);
 if (true) break;

case 5:
//C
this.state = 6;
 //BA.debugLineNum = 1691;BA.debugLine="lblTitleProfile.Visible = True";
parent.mostCurrent._lbltitleprofile.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1692;BA.debugLine="lblTitleProfile.Text = \"\"";
parent.mostCurrent._lbltitleprofile.setText(BA.ObjectToCharSequence(""));
 //BA.debugLineNum = 1693;BA.debugLine="lblPerfil_puntos_totales.Visible = False";
parent.mostCurrent._lblperfil_puntos_totales.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1694;BA.debugLine="lblPuntos_Titulo_1.Visible = False";
parent.mostCurrent._lblpuntos_titulo_1.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1695;BA.debugLine="lblPuntos_Titulo_2.Visible = False";
parent.mostCurrent._lblpuntos_titulo_2.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1696;BA.debugLine="lblPuntos_Titulo_3.Visible = False";
parent.mostCurrent._lblpuntos_titulo_3.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1697;BA.debugLine="lblPuntos_Explicacion_1.Visible = False";
parent.mostCurrent._lblpuntos_explicacion_1.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1698;BA.debugLine="lblPuntos_Explicacion_2.Visible = False";
parent.mostCurrent._lblpuntos_explicacion_2.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1699;BA.debugLine="lblPuntos_Explicacion_3.Visible = False";
parent.mostCurrent._lblpuntos_explicacion_3.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1700;BA.debugLine="lblNivel.Visible = False";
parent.mostCurrent._lblnivel.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1701;BA.debugLine="lblPerfil_puntos_totales.Visible = False";
parent.mostCurrent._lblperfil_puntos_totales.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1702;BA.debugLine="pgbLevel.Visible = False";
parent.mostCurrent._pgblevel.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1703;BA.debugLine="Msgbox2Async(\"No se puede buscar información de";
anywheresoftware.b4a.keywords.Common.Msgbox2Async(BA.ObjectToCharSequence("No se puede buscar información de tu perfil sin internet"),BA.ObjectToCharSequence("Conectate a internet"),"Ok, entiendo","","Intentar conectar",(anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper(), (android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null)),processBA,anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1704;BA.debugLine="Wait For Msgbox_Result (Result As Int)";
anywheresoftware.b4a.keywords.Common.WaitFor("msgbox_result", processBA, this, null);
this.state = 13;
return;
case 13:
//C
this.state = 6;
_result = (Integer) result[0];
;
 //BA.debugLineNum = 1705;BA.debugLine="If Result = DialogResponse.POSITIVE Then";
if (true) break;

case 6:
//if
this.state = 11;
if (_result==anywheresoftware.b4a.keywords.Common.DialogResponse.POSITIVE) { 
this.state = 8;
}else if(_result==anywheresoftware.b4a.keywords.Common.DialogResponse.NEGATIVE) { 
this.state = 10;
}if (true) break;

case 8:
//C
this.state = 11;
 //BA.debugLineNum = 1706;BA.debugLine="tabStripMain.ScrollTo(2, True)";
parent.mostCurrent._tabstripmain.ScrollTo((int) (2),anywheresoftware.b4a.keywords.Common.True);
 if (true) break;

case 10:
//C
this.state = 11;
 //BA.debugLineNum = 1708;BA.debugLine="Main.hayinternet = True";
parent.mostCurrent._main._hayinternet /*boolean*/  = anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 1709;BA.debugLine="getPuntaje";
_getpuntaje();
 if (true) break;

case 11:
//C
this.state = 12;
;
 if (true) break;

case 12:
//C
this.state = -1;
;
 //BA.debugLineNum = 1715;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
}
