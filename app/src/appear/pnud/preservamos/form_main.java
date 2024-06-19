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
public static uk.co.martinpearman.b4a.location.GeocoderWrapper _georef = null;
public static String _geopartido = "";
public anywheresoftware.b4a.objects.RuntimePermissions _rp = null;
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
public anywheresoftware.b4a.objects.LabelWrapper _lblhayinternet = null;
public anywheresoftware.b4a.objects.MapFragmentWrapper.GoogleMapWrapper _gmap = null;
public anywheresoftware.b4a.objects.MapFragmentWrapper _mapfragment1 = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblecorregiontitle = null;
public anywheresoftware.b4a.objects.PanelWrapper _pnloverlay = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblhaygeoref = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblestado = null;
public appear.pnud.preservamos.b4xgifview _logo_load_gif = null;
public anywheresoftware.b4a.objects.B4XViewWrapper _bottom_drawer_overlay = null;
public anywheresoftware.b4a.objects.B4XViewWrapper _pnlbottom = null;
public static boolean _bottom_drawer_overlayvisible = false;
public static String _selectedtest = "";
public anywheresoftware.b4a.objects.ButtonWrapper _btnstarttest = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblrequisitosanalisisvalor = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbltiempoanalisisvalor = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbldificultadvalor = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbldescripcionanalisis = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblpremioanalisisvalor = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbltituloanalisis = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblecorregionsubtitle = null;
public appear.pnud.preservamos.b4xdialog _cust_dialog = null;
public anywheresoftware.b4a.objects.PanelWrapper _fondoinfoterrain = null;
public anywheresoftware.b4a.objects.LabelWrapper _imginfoterrain = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblinfoterrain_title = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnokterraininfo = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblinfoterrain_descripcion = null;
public anywheresoftware.b4a.objects.SpinnerWrapper _spnecoregion = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblecoregionbanda = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imgecorregionmapa = null;
public b4a.example.dateutils _dateutils = null;
public appear.pnud.preservamos.main _main = null;
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
public ResumableSub_Activity_Create(appear.pnud.preservamos.form_main parent,boolean _firsttime) {
this.parent = parent;
this._firsttime = _firsttime;
}
appear.pnud.preservamos.form_main parent;
boolean _firsttime;
int _timeoutruns = 0;
boolean _completed = false;
anywheresoftware.b4a.objects.PanelWrapper _pa = null;

@Override
public void resume(BA ba, Object[] result) throws Exception{

    while (true) {
        switch (state) {
            case -1:
return;

case 0:
//C
this.state = 1;
 //BA.debugLineNum = 210;BA.debugLine="p.SetScreenOrientation(1)";
parent.mostCurrent._p.SetScreenOrientation(processBA,(int) (1));
 //BA.debugLineNum = 212;BA.debugLine="If FirstTime Then";
if (true) break;

case 1:
//if
this.state = 8;
if (_firsttime) { 
this.state = 3;
}if (true) break;

case 3:
//C
this.state = 4;
 //BA.debugLineNum = 213;BA.debugLine="If File.Exists(rp.GetSafeDirDefaultExternal(\"\"),";
if (true) break;

case 4:
//if
this.state = 7;
if (anywheresoftware.b4a.keywords.Common.File.Exists(parent.mostCurrent._rp.GetSafeDirDefaultExternal(""),"PreserVamos")==anywheresoftware.b4a.keywords.Common.False) { 
this.state = 6;
}if (true) break;

case 6:
//C
this.state = 7;
 //BA.debugLineNum = 214;BA.debugLine="File.MakeDir(rp.GetSafeDirDefaultExternal(\"\"),";
anywheresoftware.b4a.keywords.Common.File.MakeDir(parent.mostCurrent._rp.GetSafeDirDefaultExternal(""),"PreserVamos");
 //BA.debugLineNum = 215;BA.debugLine="Log(\"Folder exists\")";
anywheresoftware.b4a.keywords.Common.LogImpl("0655367","Folder exists",0);
 if (true) break;

case 7:
//C
this.state = 8;
;
 //BA.debugLineNum = 217;BA.debugLine="Starter.savedir = rp.GetSafeDirDefaultExternal(\"";
parent.mostCurrent._starter._savedir /*String*/  = parent.mostCurrent._rp.GetSafeDirDefaultExternal("PreserVamos");
 if (true) break;

case 8:
//C
this.state = 9;
;
 //BA.debugLineNum = 220;BA.debugLine="Log(\"userName in Form_Main:\" & Main.username)";
anywheresoftware.b4a.keywords.Common.LogImpl("0655372","userName in Form_Main:"+parent.mostCurrent._main._username /*String*/ ,0);
 //BA.debugLineNum = 223;BA.debugLine="Activity.RemoveAllViews";
parent.mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 224;BA.debugLine="Activity.LoadLayout(\"layMain\")";
parent.mostCurrent._activity.LoadLayout("layMain",mostCurrent.activityBA);
 //BA.debugLineNum = 228;BA.debugLine="logo_load_gif.SetGif(File.DirAssets, \"Preservamos";
parent.mostCurrent._logo_load_gif._setgif /*String*/ (anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"Preservamos_Logo_Ani.gif");
 //BA.debugLineNum = 232;BA.debugLine="Wait For MapFragment1_Ready";
anywheresoftware.b4a.keywords.Common.WaitFor("mapfragment1_ready", processBA, this, null);
this.state = 71;
return;
case 71:
//C
this.state = 9;
;
 //BA.debugLineNum = 233;BA.debugLine="gmap = MapFragment1.GetMap";
parent.mostCurrent._gmap = parent.mostCurrent._mapfragment1.GetMap();
 //BA.debugLineNum = 234;BA.debugLine="gmap.MyLocationEnabled = True";
parent.mostCurrent._gmap.setMyLocationEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 236;BA.debugLine="lblEstado.Text = \"Buscando tu ubicación\"";
parent.mostCurrent._lblestado.setText(BA.ObjectToCharSequence("Buscando tu ubicación"));
 //BA.debugLineNum = 238;BA.debugLine="If gmap.IsInitialized Then";
if (true) break;

case 9:
//if
this.state = 18;
if (parent.mostCurrent._gmap.IsInitialized()) { 
this.state = 11;
}else {
this.state = 17;
}if (true) break;

case 11:
//C
this.state = 12;
 //BA.debugLineNum = 239;BA.debugLine="Dim timeoutruns As Int = 0";
_timeoutruns = (int) (0);
 //BA.debugLineNum = 240;BA.debugLine="Do While Not(gmap.MyLocation.IsInitialized) And";
if (true) break;

case 12:
//do while
this.state = 15;
while (anywheresoftware.b4a.keywords.Common.Not(parent.mostCurrent._gmap.getMyLocation().IsInitialized()) && _timeoutruns<500000) {
this.state = 14;
if (true) break;
}
if (true) break;

case 14:
//C
this.state = 12;
 //BA.debugLineNum = 241;BA.debugLine="Sleep(0)";
anywheresoftware.b4a.keywords.Common.Sleep(mostCurrent.activityBA,this,(int) (0));
this.state = 72;
return;
case 72:
//C
this.state = 12;
;
 //BA.debugLineNum = 242;BA.debugLine="timeoutruns = timeoutruns + 1";
_timeoutruns = (int) (_timeoutruns+1);
 if (true) break;

case 15:
//C
this.state = 18;
;
 //BA.debugLineNum = 245;BA.debugLine="Log(\"timeoutruns:\" & timeoutruns)";
anywheresoftware.b4a.keywords.Common.LogImpl("0655397","timeoutruns:"+BA.NumberToString(_timeoutruns),0);
 if (true) break;

case 17:
//C
this.state = 18;
 //BA.debugLineNum = 247;BA.debugLine="ToastMessageShow(\"Error iniciando el mapa.\", Tru";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Error iniciando el mapa."),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 248;BA.debugLine="Log(\"Error initalizing gmap\")";
anywheresoftware.b4a.keywords.Common.LogImpl("0655400","Error initalizing gmap",0);
 if (true) break;
;
 //BA.debugLineNum = 251;BA.debugLine="If gmap.MyLocation.IsInitialized Then";

case 18:
//if
this.state = 21;
if (parent.mostCurrent._gmap.getMyLocation().IsInitialized()) { 
this.state = 20;
}if (true) break;

case 20:
//C
this.state = 21;
 //BA.debugLineNum = 252;BA.debugLine="Main.latitud = gmap.MyLocation.Latitude";
parent.mostCurrent._main._latitud /*String*/  = BA.NumberToString(parent.mostCurrent._gmap.getMyLocation().getLatitude());
 //BA.debugLineNum = 253;BA.debugLine="Main.longitud = gmap.MyLocation.Longitude";
parent.mostCurrent._main._longitud /*String*/  = BA.NumberToString(parent.mostCurrent._gmap.getMyLocation().getLongitude());
 //BA.debugLineNum = 254;BA.debugLine="Log(\"Georreferenciación OK\")";
anywheresoftware.b4a.keywords.Common.LogImpl("0655406","Georreferenciación OK",0);
 //BA.debugLineNum = 255;BA.debugLine="Log(\"latitud: \" & Main.latitud & \"//longitud: \"";
anywheresoftware.b4a.keywords.Common.LogImpl("0655407","latitud: "+parent.mostCurrent._main._latitud /*String*/ +"//longitud: "+parent.mostCurrent._main._longitud /*String*/ ,0);
 if (true) break;

case 21:
//C
this.state = 22;
;
 //BA.debugLineNum = 260;BA.debugLine="Log(\"Main.latitud: \" & Main.latitud)";
anywheresoftware.b4a.keywords.Common.LogImpl("0655412","Main.latitud: "+parent.mostCurrent._main._latitud /*String*/ ,0);
 //BA.debugLineNum = 261;BA.debugLine="If Main.latitud <> \"\" And Main.latitud <> \"null\"";
if (true) break;

case 22:
//if
this.state = 33;
if ((parent.mostCurrent._main._latitud /*String*/ ).equals("") == false && (parent.mostCurrent._main._latitud /*String*/ ).equals("null") == false && (parent.mostCurrent._main._longitud /*String*/ ).equals("") == false && (parent.mostCurrent._main._longitud /*String*/ ).equals("null") == false) { 
this.state = 24;
}else {
this.state = 32;
}if (true) break;

case 24:
//C
this.state = 25;
 //BA.debugLineNum = 262;BA.debugLine="lblEstado.Text = \"Buscando tu ecorregión...\"";
parent.mostCurrent._lblestado.setText(BA.ObjectToCharSequence("Buscando tu ecorregión..."));
 //BA.debugLineNum = 263;BA.debugLine="Log(\"Buscando ecoregion\")";
anywheresoftware.b4a.keywords.Common.LogImpl("0655415","Buscando ecoregion",0);
 //BA.debugLineNum = 264;BA.debugLine="Wait For (Buscar_Ecoregion) Complete(Completed A";
anywheresoftware.b4a.keywords.Common.WaitFor("complete", processBA, this, _buscar_ecoregion());
this.state = 73;
return;
case 73:
//C
this.state = 25;
_completed = (Boolean) result[0];
;
 //BA.debugLineNum = 265;BA.debugLine="If Completed = True Then";
if (true) break;

case 25:
//if
this.state = 30;
if (_completed==anywheresoftware.b4a.keywords.Common.True) { 
this.state = 27;
}else {
this.state = 29;
}if (true) break;

case 27:
//C
this.state = 30;
 //BA.debugLineNum = 266;BA.debugLine="lblEstado.Text = \"Estás en la ecorregión \" & Ma";
parent.mostCurrent._lblestado.setText(BA.ObjectToCharSequence("Estás en la ecorregión "+parent.mostCurrent._main._currentecoregion /*String*/ ));
 if (true) break;

case 29:
//C
this.state = 30;
 //BA.debugLineNum = 269;BA.debugLine="Main.currentEcoregion = \"\"";
parent.mostCurrent._main._currentecoregion /*String*/  = "";
 //BA.debugLineNum = 270;BA.debugLine="Main.currentSubEcoregion = \"\"";
parent.mostCurrent._main._currentsubecoregion /*String*/  = "";
 //BA.debugLineNum = 271;BA.debugLine="lblEstado.Text = \"No se detectó tu ecorregión..";
parent.mostCurrent._lblestado.setText(BA.ObjectToCharSequence("No se detectó tu ecorregión..."));
 if (true) break;

case 30:
//C
this.state = 33;
;
 if (true) break;

case 32:
//C
this.state = 33;
 //BA.debugLineNum = 275;BA.debugLine="Log(\"No hay coordenadas, no puede buscar ecorreg";
anywheresoftware.b4a.keywords.Common.LogImpl("0655427","No hay coordenadas, no puede buscar ecorregion",0);
 //BA.debugLineNum = 276;BA.debugLine="Main.currentEcoregion = \"\"";
parent.mostCurrent._main._currentecoregion /*String*/  = "";
 //BA.debugLineNum = 277;BA.debugLine="Main.currentSubEcoregion = \"\"";
parent.mostCurrent._main._currentsubecoregion /*String*/  = "";
 if (true) break;
;
 //BA.debugLineNum = 281;BA.debugLine="If Main.latitud <> \"\" And Main.latitud <> \"null\"";

case 33:
//if
this.state = 44;
if ((parent.mostCurrent._main._latitud /*String*/ ).equals("") == false && (parent.mostCurrent._main._latitud /*String*/ ).equals("null") == false && (parent.mostCurrent._main._longitud /*String*/ ).equals("") == false && (parent.mostCurrent._main._longitud /*String*/ ).equals("null") == false) { 
this.state = 35;
}else {
this.state = 43;
}if (true) break;

case 35:
//C
this.state = 36;
 //BA.debugLineNum = 282;BA.debugLine="Wait For (buscarReverseGeoref) Complete (Complet";
anywheresoftware.b4a.keywords.Common.WaitFor("complete", processBA, this, _buscarreversegeoref());
this.state = 74;
return;
case 74:
//C
this.state = 36;
_completed = (Boolean) result[0];
;
 //BA.debugLineNum = 283;BA.debugLine="If Completed = True Then";
if (true) break;

case 36:
//if
this.state = 41;
if (_completed==anywheresoftware.b4a.keywords.Common.True) { 
this.state = 38;
}else {
this.state = 40;
}if (true) break;

case 38:
//C
this.state = 41;
 //BA.debugLineNum = 284;BA.debugLine="Log(\"georef terminada\")";
anywheresoftware.b4a.keywords.Common.LogImpl("0655436","georef terminada",0);
 if (true) break;

case 40:
//C
this.state = 41;
 //BA.debugLineNum = 286;BA.debugLine="Log(\"georef fallo\")";
anywheresoftware.b4a.keywords.Common.LogImpl("0655438","georef fallo",0);
 if (true) break;

case 41:
//C
this.state = 44;
;
 if (true) break;

case 43:
//C
this.state = 44;
 //BA.debugLineNum = 290;BA.debugLine="lblHayGeoref.Visible = False";
parent.mostCurrent._lblhaygeoref.setVisible(anywheresoftware.b4a.keywords.Common.False);
 if (true) break;

case 44:
//C
this.state = 45;
;
 //BA.debugLineNum = 294;BA.debugLine="Wait For (CrearMenu) Complete (Completed As Boole";
anywheresoftware.b4a.keywords.Common.WaitFor("complete", processBA, this, _crearmenu());
this.state = 75;
return;
case 75:
//C
this.state = 45;
_completed = (Boolean) result[0];
;
 //BA.debugLineNum = 297;BA.debugLine="tabStripMain.LoadLayout(\"layMainExplorar\", \"\")";
parent.mostCurrent._tabstripmain.LoadLayout("layMainExplorar",BA.ObjectToCharSequence(""));
 //BA.debugLineNum = 298;BA.debugLine="tabStripMain.LoadLayout(\"layMainMunicipio\", \"\")";
parent.mostCurrent._tabstripmain.LoadLayout("layMainMunicipio",BA.ObjectToCharSequence(""));
 //BA.debugLineNum = 299;BA.debugLine="tabStripMain.LoadLayout(\"layMainReportar\", \"\")";
parent.mostCurrent._tabstripmain.LoadLayout("layMainReportar",BA.ObjectToCharSequence(""));
 //BA.debugLineNum = 300;BA.debugLine="tabStripMain.LoadLayout(\"layProfile\", \"\")";
parent.mostCurrent._tabstripmain.LoadLayout("layProfile",BA.ObjectToCharSequence(""));
 //BA.debugLineNum = 301;BA.debugLine="tabStripMain.ScrollTo(2,False)";
parent.mostCurrent._tabstripmain.ScrollTo((int) (2),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 302;BA.debugLine="scrReportar_Modulos.Panel.LoadLayout(\"layMainRepo";
parent.mostCurrent._scrreportar_modulos.getPanel().LoadLayout("layMainReportar_Modulos",mostCurrent.activityBA);
 //BA.debugLineNum = 305;BA.debugLine="Bottom_Drawer_Overlay = xui.CreatePanel(\"Bottom_D";
parent.mostCurrent._bottom_drawer_overlay = parent.mostCurrent._xui.CreatePanel(processBA,"Bottom_Drawer_Overlay");
 //BA.debugLineNum = 306;BA.debugLine="Bottom_Drawer_Overlay.Visible = False";
parent.mostCurrent._bottom_drawer_overlay.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 307;BA.debugLine="Dim pa As Panel = Bottom_Drawer_Overlay";
_pa = new anywheresoftware.b4a.objects.PanelWrapper();
_pa = (anywheresoftware.b4a.objects.PanelWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.PanelWrapper(), (android.view.ViewGroup)(parent.mostCurrent._bottom_drawer_overlay.getObject()));
 //BA.debugLineNum = 308;BA.debugLine="pa.Elevation = 5dip";
_pa.setElevation((float) (anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 //BA.debugLineNum = 309;BA.debugLine="Activity.AddView(Bottom_Drawer_Overlay, 0, 0, 100";
parent.mostCurrent._activity.AddView((android.view.View)(parent.mostCurrent._bottom_drawer_overlay.getObject()),(int) (0),(int) (0),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA));
 //BA.debugLineNum = 310;BA.debugLine="Bottom_Drawer_Overlay.LoadLayout(\"Bottom_drawer_o";
parent.mostCurrent._bottom_drawer_overlay.LoadLayout("Bottom_drawer_overlay",mostCurrent.activityBA);
 //BA.debugLineNum = 311;BA.debugLine="pnlBottom.LoadLayout(\"Bottom_drawer_drawer\")";
parent.mostCurrent._pnlbottom.LoadLayout("Bottom_drawer_drawer",mostCurrent.activityBA);
 //BA.debugLineNum = 312;BA.debugLine="pnlBottom.SetColorAndBorder(xui.Color_White, 0, 0";
parent.mostCurrent._pnlbottom.SetColorAndBorder(parent.mostCurrent._xui.Color_White,(int) (0),(int) (0),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (15)));
 //BA.debugLineNum = 315;BA.debugLine="lblEstado.Text = \"Buscando datos del participante";
parent.mostCurrent._lblestado.setText(BA.ObjectToCharSequence("Buscando datos del participante"));
 //BA.debugLineNum = 316;BA.debugLine="If Main.strUserEmail = \"\" Or Main.strUserEmail =";
if (true) break;

case 45:
//if
this.state = 50;
if ((parent.mostCurrent._main._struseremail /*String*/ ).equals("") || parent.mostCurrent._main._struseremail /*String*/ == null || (parent.mostCurrent._main._struseremail /*String*/ ).equals("null")) { 
this.state = 47;
}else {
this.state = 49;
}if (true) break;

case 47:
//C
this.state = 50;
 //BA.debugLineNum = 317;BA.debugLine="StartActivity(register)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(parent.mostCurrent._register.getObject()));
 if (true) break;

case 49:
//C
this.state = 50;
 //BA.debugLineNum = 319;BA.debugLine="Wait For (getPuntaje) Complete (Completed As Boo";
anywheresoftware.b4a.keywords.Common.WaitFor("complete", processBA, this, _getpuntaje());
this.state = 76;
return;
case 76:
//C
this.state = 50;
_completed = (Boolean) result[0];
;
 if (true) break;

case 50:
//C
this.state = 51;
;
 //BA.debugLineNum = 323;BA.debugLine="Log(\"Main.currentEcoregion: \" & Main.currentEcore";
anywheresoftware.b4a.keywords.Common.LogImpl("0655475","Main.currentEcoregion: "+parent.mostCurrent._main._currentecoregion /*String*/ ,0);
 //BA.debugLineNum = 324;BA.debugLine="If Main.currentEcoregion <> \"\" Then";
if (true) break;

case 51:
//if
this.state = 56;
if ((parent.mostCurrent._main._currentecoregion /*String*/ ).equals("") == false) { 
this.state = 53;
}else {
this.state = 55;
}if (true) break;

case 53:
//C
this.state = 56;
 //BA.debugLineNum = 325;BA.debugLine="lblEcorregionTitle.Text = \"Estás en la ecorregió";
parent.mostCurrent._lblecorregiontitle.setText(BA.ObjectToCharSequence("Estás en la ecorregión "+parent.mostCurrent._main._currentecoregion /*String*/ ));
 //BA.debugLineNum = 326;BA.debugLine="lblHayGeoref.Visible = False";
parent.mostCurrent._lblhaygeoref.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 327;BA.debugLine="setEcoregion";
_setecoregion();
 if (true) break;

case 55:
//C
this.state = 56;
 //BA.debugLineNum = 329;BA.debugLine="lblEcorregionTitle.Text = \"No se detectó tu ecor";
parent.mostCurrent._lblecorregiontitle.setText(BA.ObjectToCharSequence("No se detectó tu ecorregión..."));
 //BA.debugLineNum = 330;BA.debugLine="lblHayGeoref.Visible = True";
parent.mostCurrent._lblhaygeoref.setVisible(anywheresoftware.b4a.keywords.Common.True);
 if (true) break;

case 56:
//C
this.state = 57;
;
 //BA.debugLineNum = 334;BA.debugLine="Log(\"Hay internet: \" & Main.hayinternet)";
anywheresoftware.b4a.keywords.Common.LogImpl("0655486","Hay internet: "+BA.ObjectToString(parent.mostCurrent._main._hayinternet /*boolean*/ ),0);
 //BA.debugLineNum = 335;BA.debugLine="If Main.hayinternet = False Then";
if (true) break;

case 57:
//if
this.state = 62;
if (parent.mostCurrent._main._hayinternet /*boolean*/ ==anywheresoftware.b4a.keywords.Common.False) { 
this.state = 59;
}else {
this.state = 61;
}if (true) break;

case 59:
//C
this.state = 62;
 //BA.debugLineNum = 336;BA.debugLine="lblHayInternet.Visible = True";
parent.mostCurrent._lblhayinternet.setVisible(anywheresoftware.b4a.keywords.Common.True);
 if (true) break;

case 61:
//C
this.state = 62;
 //BA.debugLineNum = 338;BA.debugLine="lblHayInternet.Visible = False";
parent.mostCurrent._lblhayinternet.setVisible(anywheresoftware.b4a.keywords.Common.False);
 if (true) break;
;
 //BA.debugLineNum = 341;BA.debugLine="If Main.latitud <> \"\" And Main.longitud <> \"\" The";

case 62:
//if
this.state = 67;
if ((parent.mostCurrent._main._latitud /*String*/ ).equals("") == false && (parent.mostCurrent._main._longitud /*String*/ ).equals("") == false) { 
this.state = 64;
}else {
this.state = 66;
}if (true) break;

case 64:
//C
this.state = 67;
 //BA.debugLineNum = 342;BA.debugLine="lblHayGeoref.Visible = False";
parent.mostCurrent._lblhaygeoref.setVisible(anywheresoftware.b4a.keywords.Common.False);
 if (true) break;

case 66:
//C
this.state = 67;
 //BA.debugLineNum = 344;BA.debugLine="lblHayGeoref.Visible = True";
parent.mostCurrent._lblhaygeoref.setVisible(anywheresoftware.b4a.keywords.Common.True);
 if (true) break;
;
 //BA.debugLineNum = 348;BA.debugLine="If pnlOverlay.IsInitialized Then";

case 67:
//if
this.state = 70;
if (parent.mostCurrent._pnloverlay.IsInitialized()) { 
this.state = 69;
}if (true) break;

case 69:
//C
this.state = 70;
 //BA.debugLineNum = 349;BA.debugLine="Log(\"cerrando overlay\")";
anywheresoftware.b4a.keywords.Common.LogImpl("0655501","cerrando overlay",0);
 //BA.debugLineNum = 350;BA.debugLine="pnlOverlay.SetVisibleAnimated(600,False)";
parent.mostCurrent._pnloverlay.SetVisibleAnimated((int) (600),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 351;BA.debugLine="Sleep (600)";
anywheresoftware.b4a.keywords.Common.Sleep(mostCurrent.activityBA,this,(int) (600));
this.state = 77;
return;
case 77:
//C
this.state = 70;
;
 //BA.debugLineNum = 352;BA.debugLine="pnlOverlay.RemoveView";
parent.mostCurrent._pnloverlay.RemoveView();
 if (true) break;

case 70:
//C
this.state = -1;
;
 //BA.debugLineNum = 355;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public static void  _mapfragment1_ready() throws Exception{
}
public static void  _complete(boolean _completed) throws Exception{
}
public static boolean  _activity_keypress(int _keycode) throws Exception{
 //BA.debugLineNum = 398;BA.debugLine="Sub Activity_KeyPress (KeyCode As Int) As Boolean";
 //BA.debugLineNum = 399;BA.debugLine="If KeyCode = KeyCodes.KEYCODE_BACK Then";
if (_keycode==anywheresoftware.b4a.keywords.Common.KeyCodes.KEYCODE_BACK) { 
 //BA.debugLineNum = 400;BA.debugLine="If Drawer.LeftOpen Then";
if (mostCurrent._drawer._getleftopen /*boolean*/ ()) { 
 //BA.debugLineNum = 401;BA.debugLine="Drawer.LeftOpen = False";
mostCurrent._drawer._setleftopen /*boolean*/ (anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 402;BA.debugLine="Return True";
if (true) return anywheresoftware.b4a.keywords.Common.True;
 }else {
 //BA.debugLineNum = 404;BA.debugLine="closeAppMsgBox";
_closeappmsgbox();
 //BA.debugLineNum = 405;BA.debugLine="Return True";
if (true) return anywheresoftware.b4a.keywords.Common.True;
 };
 }else {
 //BA.debugLineNum = 409;BA.debugLine="Return False";
if (true) return anywheresoftware.b4a.keywords.Common.False;
 };
 //BA.debugLineNum = 413;BA.debugLine="End Sub";
return false;
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 395;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 397;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 358;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 362;BA.debugLine="Log(\"Hay internet: \" & Main.hayinternet)";
anywheresoftware.b4a.keywords.Common.LogImpl("0720900","Hay internet: "+BA.ObjectToString(mostCurrent._main._hayinternet /*boolean*/ ),0);
 //BA.debugLineNum = 363;BA.debugLine="If Main.hayinternet = False Then";
if (mostCurrent._main._hayinternet /*boolean*/ ==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 364;BA.debugLine="lblHayInternet.Visible = True";
mostCurrent._lblhayinternet.setVisible(anywheresoftware.b4a.keywords.Common.True);
 }else {
 //BA.debugLineNum = 366;BA.debugLine="lblHayInternet.Visible = False";
mostCurrent._lblhayinternet.setVisible(anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 369;BA.debugLine="If Main.latitud <> \"\" And Main.longitud <> \"\" The";
if ((mostCurrent._main._latitud /*String*/ ).equals("") == false && (mostCurrent._main._longitud /*String*/ ).equals("") == false) { 
 //BA.debugLineNum = 370;BA.debugLine="lblHayGeoref.Visible = False";
mostCurrent._lblhaygeoref.setVisible(anywheresoftware.b4a.keywords.Common.False);
 }else {
 //BA.debugLineNum = 372;BA.debugLine="lblHayGeoref.Visible = True";
mostCurrent._lblhaygeoref.setVisible(anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 394;BA.debugLine="End Sub";
return "";
}
public static String  _bottom_drawer_overlay_click() throws Exception{
 //BA.debugLineNum = 554;BA.debugLine="Sub Bottom_Drawer_Overlay_Click";
 //BA.debugLineNum = 555;BA.debugLine="If Bottom_Drawer_OverlayVisible = False Then Retu";
if (_bottom_drawer_overlayvisible==anywheresoftware.b4a.keywords.Common.False) { 
if (true) return "";};
 //BA.debugLineNum = 556;BA.debugLine="ToggleDrawerState";
_toggledrawerstate();
 //BA.debugLineNum = 557;BA.debugLine="End Sub";
return "";
}
public static String  _btnabout_click() throws Exception{
 //BA.debugLineNum = 495;BA.debugLine="Sub btnAbout_Click";
 //BA.debugLineNum = 496;BA.debugLine="StartActivity(frmAbout)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._frmabout.getObject()));
 //BA.debugLineNum = 497;BA.debugLine="End Sub";
return "";
}
public static String  _btnabririnat_click() throws Exception{
 //BA.debugLineNum = 1499;BA.debugLine="Private Sub btnAbrirINat_Click";
 //BA.debugLineNum = 1500;BA.debugLine="If Main.hayinternet = True Then";
if (mostCurrent._main._hayinternet /*boolean*/ ==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 1501;BA.debugLine="StartActivity(iNatCheck)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._inatcheck.getObject()));
 }else {
 //BA.debugLineNum = 1503;BA.debugLine="ToastMessageShow(\"Necesitas estar conectado a in";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Necesitas estar conectado a internet para usar esta herramienta"),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1504;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 1507;BA.debugLine="End Sub";
return "";
}
public static String  _btnabrirmapa_click() throws Exception{
 //BA.debugLineNum = 1495;BA.debugLine="Private Sub btnAbrirMapa_Click";
 //BA.debugLineNum = 1496;BA.debugLine="frmMapa.origen=\"main\"";
mostCurrent._frmmapa._origen /*String*/  = "main";
 //BA.debugLineNum = 1497;BA.debugLine="StartActivity(frmMapa)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._frmmapa.getObject()));
 //BA.debugLineNum = 1498;BA.debugLine="End Sub";
return "";
}
public static void  _btnactualizar_click() throws Exception{
ResumableSub_btnActualizar_Click rsub = new ResumableSub_btnActualizar_Click(null);
rsub.resume(processBA, null);
}
public static class ResumableSub_btnActualizar_Click extends BA.ResumableSub {
public ResumableSub_btnActualizar_Click(appear.pnud.preservamos.form_main parent) {
this.parent = parent;
}
appear.pnud.preservamos.form_main parent;
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
 //BA.debugLineNum = 2254;BA.debugLine="ToastMessageShow(\"Actualizando puntaje...\", False";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Actualizando puntaje..."),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 2255;BA.debugLine="Wait For (getPuntaje) Complete (Completed As Bool";
anywheresoftware.b4a.keywords.Common.WaitFor("complete", processBA, this, _getpuntaje());
this.state = 23;
return;
case 23:
//C
this.state = 1;
_completed = (Boolean) result[0];
;
 //BA.debugLineNum = 2256;BA.debugLine="If Completed = False Then";
if (true) break;

case 1:
//if
this.state = 6;
if (_completed==anywheresoftware.b4a.keywords.Common.False) { 
this.state = 3;
}else {
this.state = 5;
}if (true) break;

case 3:
//C
this.state = 6;
 //BA.debugLineNum = 2257;BA.debugLine="ToastMessageShow(\"No se encuentra tu puntaje...\"";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("No se encuentra tu puntaje..."),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 2258;BA.debugLine="lblNivel.Visible = False";
parent.mostCurrent._lblnivel.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 2259;BA.debugLine="lblPuntos_Explicacion_1.Visible = False";
parent.mostCurrent._lblpuntos_explicacion_1.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 2260;BA.debugLine="lblPuntos_Explicacion_2.Visible = False";
parent.mostCurrent._lblpuntos_explicacion_2.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 2261;BA.debugLine="lblPuntos_Explicacion_3.Visible = False";
parent.mostCurrent._lblpuntos_explicacion_3.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 2262;BA.debugLine="pgbLevel.Visible = False";
parent.mostCurrent._pgblevel.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 2263;BA.debugLine="lblPerfil_puntos_totales.Visible = True";
parent.mostCurrent._lblperfil_puntos_totales.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 2264;BA.debugLine="lblPerfil_puntos_totales.Text = \"No se encuentra";
parent.mostCurrent._lblperfil_puntos_totales.setText(BA.ObjectToCharSequence("No se encuentran puntos para tu usuario aún. Completa análisis para ganar puntos!"));
 //BA.debugLineNum = 2265;BA.debugLine="Return";
if (true) return ;
 if (true) break;

case 5:
//C
this.state = 6;
 //BA.debugLineNum = 2267;BA.debugLine="lblNivel.Visible = True";
parent.mostCurrent._lblnivel.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 2268;BA.debugLine="lblPuntos_Explicacion_1.Visible = True";
parent.mostCurrent._lblpuntos_explicacion_1.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 2269;BA.debugLine="lblPuntos_Explicacion_2.Visible = True";
parent.mostCurrent._lblpuntos_explicacion_2.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 2270;BA.debugLine="lblPuntos_Explicacion_3.Visible = True";
parent.mostCurrent._lblpuntos_explicacion_3.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 2271;BA.debugLine="pgbLevel.Visible = True";
parent.mostCurrent._pgblevel.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 2272;BA.debugLine="lblPerfil_puntos_totales.Visible = True";
parent.mostCurrent._lblperfil_puntos_totales.setVisible(anywheresoftware.b4a.keywords.Common.True);
 if (true) break;

case 6:
//C
this.state = 7;
;
 //BA.debugLineNum = 2274;BA.debugLine="Wait For (calcular_puntos_ambientes) Complete (Co";
anywheresoftware.b4a.keywords.Common.WaitFor("complete", processBA, this, _calcular_puntos_ambientes());
this.state = 24;
return;
case 24:
//C
this.state = 7;
_completed = (Boolean) result[0];
;
 //BA.debugLineNum = 2275;BA.debugLine="If Completed = False Then";
if (true) break;

case 7:
//if
this.state = 10;
if (_completed==anywheresoftware.b4a.keywords.Common.False) { 
this.state = 9;
}if (true) break;

case 9:
//C
this.state = 10;
 //BA.debugLineNum = 2276;BA.debugLine="Return";
if (true) return ;
 if (true) break;

case 10:
//C
this.state = 11;
;
 //BA.debugLineNum = 2278;BA.debugLine="Wait For (cargarNivel) Complete (Completed As Boo";
anywheresoftware.b4a.keywords.Common.WaitFor("complete", processBA, this, _cargarnivel());
this.state = 25;
return;
case 25:
//C
this.state = 11;
_completed = (Boolean) result[0];
;
 //BA.debugLineNum = 2279;BA.debugLine="If Completed = False Then";
if (true) break;

case 11:
//if
this.state = 14;
if (_completed==anywheresoftware.b4a.keywords.Common.False) { 
this.state = 13;
}if (true) break;

case 13:
//C
this.state = 14;
 //BA.debugLineNum = 2280;BA.debugLine="Return";
if (true) return ;
 if (true) break;

case 14:
//C
this.state = 15;
;
 //BA.debugLineNum = 2282;BA.debugLine="Wait For (cargarMedallas) Complete (Completed As";
anywheresoftware.b4a.keywords.Common.WaitFor("complete", processBA, this, _cargarmedallas());
this.state = 26;
return;
case 26:
//C
this.state = 15;
_completed = (Boolean) result[0];
;
 //BA.debugLineNum = 2283;BA.debugLine="If Completed = False Then";
if (true) break;

case 15:
//if
this.state = 18;
if (_completed==anywheresoftware.b4a.keywords.Common.False) { 
this.state = 17;
}if (true) break;

case 17:
//C
this.state = 18;
 //BA.debugLineNum = 2284;BA.debugLine="Return";
if (true) return ;
 if (true) break;

case 18:
//C
this.state = 19;
;
 //BA.debugLineNum = 2286;BA.debugLine="Wait For (update_perfil) Complete (Completed As B";
anywheresoftware.b4a.keywords.Common.WaitFor("complete", processBA, this, _update_perfil());
this.state = 27;
return;
case 27:
//C
this.state = 19;
_completed = (Boolean) result[0];
;
 //BA.debugLineNum = 2287;BA.debugLine="If Completed = False Then";
if (true) break;

case 19:
//if
this.state = 22;
if (_completed==anywheresoftware.b4a.keywords.Common.False) { 
this.state = 21;
}if (true) break;

case 21:
//C
this.state = 22;
 //BA.debugLineNum = 2288;BA.debugLine="Return";
if (true) return ;
 if (true) break;

case 22:
//C
this.state = -1;
;
 //BA.debugLineNum = 2290;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public static String  _btnanalizar_click() throws Exception{
 //BA.debugLineNum = 607;BA.debugLine="Private Sub btnAnalizar_Click";
 //BA.debugLineNum = 608;BA.debugLine="tabStripMain.ScrollTo(2, True)";
mostCurrent._tabstripmain.ScrollTo((int) (2),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 610;BA.debugLine="End Sub";
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
 //BA.debugLineNum = 1785;BA.debugLine="Main.strUserOrg = \"\"";
parent.mostCurrent._main._struserorg /*String*/  = "";
 //BA.debugLineNum = 1786;BA.debugLine="Msgbox2Async(\"¿Querés cambiar de municipio selecc";
anywheresoftware.b4a.keywords.Common.Msgbox2Async(BA.ObjectToCharSequence("¿Querés cambiar de municipio seleccionado?"),BA.ObjectToCharSequence("¿De acuerdo?"),"Si, cambiar!","","Cancelar",(anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper(), (android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null)),processBA,anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1787;BA.debugLine="Wait For Msgbox_Result (Result As Int)";
anywheresoftware.b4a.keywords.Common.WaitFor("msgbox_result", processBA, this, null);
this.state = 5;
return;
case 5:
//C
this.state = 1;
_result = (Integer) result[0];
;
 //BA.debugLineNum = 1788;BA.debugLine="If Result = DialogResponse.POSITIVE Then";
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
 //BA.debugLineNum = 1789;BA.debugLine="frmLocalizacion.origen = \"main\"";
parent.mostCurrent._frmlocalizacion._origen /*String*/  = "main";
 //BA.debugLineNum = 1790;BA.debugLine="StartActivity(frmLocalizacion)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(parent.mostCurrent._frmlocalizacion.getObject()));
 if (true) break;

case 4:
//C
this.state = -1;
;
 //BA.debugLineNum = 1792;BA.debugLine="End Sub";
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
 //BA.debugLineNum = 482;BA.debugLine="Msgbox2Async(\"Desea cerrar la sesión? Ingresar co";
anywheresoftware.b4a.keywords.Common.Msgbox2Async(BA.ObjectToCharSequence("Desea cerrar la sesión? Ingresar con otro usuario requiere de internet!"),BA.ObjectToCharSequence("Seguro?"),"Si, tengo internet","","No, no tengo internet ahora",(anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper(), (android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null)),processBA,anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 483;BA.debugLine="Wait For Msgbox_Result (Result As Int)";
anywheresoftware.b4a.keywords.Common.WaitFor("msgbox_result", processBA, this, null);
this.state = 5;
return;
case 5:
//C
this.state = 1;
_result = (Integer) result[0];
;
 //BA.debugLineNum = 484;BA.debugLine="If Result = DialogResponse.POSITIVE Then";
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
 //BA.debugLineNum = 485;BA.debugLine="Main.strUserID = \"\"";
parent.mostCurrent._main._struserid /*String*/  = "";
 //BA.debugLineNum = 486;BA.debugLine="Main.strUserName = \"\"";
parent.mostCurrent._main._strusername /*String*/  = "";
 //BA.debugLineNum = 487;BA.debugLine="Main.strUserLocation = \"\"";
parent.mostCurrent._main._struserlocation /*String*/  = "";
 //BA.debugLineNum = 488;BA.debugLine="Main.strUserEmail = \"\"";
parent.mostCurrent._main._struseremail /*String*/  = "";
 //BA.debugLineNum = 489;BA.debugLine="Main.strUserOrg = \"\"";
parent.mostCurrent._main._struserorg /*String*/  = "";
 //BA.debugLineNum = 490;BA.debugLine="Main.username = \"\"";
parent.mostCurrent._main._username /*String*/  = "";
 //BA.debugLineNum = 491;BA.debugLine="Activity.RemoveAllViews";
parent.mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 492;BA.debugLine="Activity.finish";
parent.mostCurrent._activity.Finish();
 if (true) break;

case 4:
//C
this.state = -1;
;
 //BA.debugLineNum = 494;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public static String  _btndatosanteriores_click() throws Exception{
 //BA.debugLineNum = 530;BA.debugLine="Sub btnDatosAnteriores_Click";
 //BA.debugLineNum = 531;BA.debugLine="StartActivity(frmDatosAnteriores)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._frmdatosanteriores.getObject()));
 //BA.debugLineNum = 532;BA.debugLine="End Sub";
return "";
}
public static String  _btndatossinenviar_click() throws Exception{
 //BA.debugLineNum = 526;BA.debugLine="Sub btnDatosSinEnviar_Click";
 //BA.debugLineNum = 527;BA.debugLine="StartActivity(frmDatosSinEnviar)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._frmdatossinenviar.getObject()));
 //BA.debugLineNum = 528;BA.debugLine="End Sub";
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
 //BA.debugLineNum = 499;BA.debugLine="If Main.username = \"guest\" Or Main.username = \"No";
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
 //BA.debugLineNum = 501;BA.debugLine="Msgbox2Async(\"Necesita estar registrado para ver";
anywheresoftware.b4a.keywords.Common.Msgbox2Async(BA.ObjectToCharSequence("Necesita estar registrado para ver su perfil, ¿desea hacer ingresar ahora?"),BA.ObjectToCharSequence("Ingreso"),"Si, tengo internet","","No, no tengo internet ahora",(anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper(), (android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null)),processBA,anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 502;BA.debugLine="Wait For Msgbox_Result (Result As Int)";
anywheresoftware.b4a.keywords.Common.WaitFor("msgbox_result", processBA, this, null);
this.state = 11;
return;
case 11:
//C
this.state = 4;
_result = (Integer) result[0];
;
 //BA.debugLineNum = 503;BA.debugLine="If Result = DialogResponse.POSITIVE Then";
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
 //BA.debugLineNum = 504;BA.debugLine="Main.strUserID = \"\"";
parent.mostCurrent._main._struserid /*String*/  = "";
 //BA.debugLineNum = 505;BA.debugLine="Main.strUserName = \"\"";
parent.mostCurrent._main._strusername /*String*/  = "";
 //BA.debugLineNum = 506;BA.debugLine="Main.strUserLocation = \"\"";
parent.mostCurrent._main._struserlocation /*String*/  = "";
 //BA.debugLineNum = 507;BA.debugLine="Main.strUserEmail = \"\"";
parent.mostCurrent._main._struseremail /*String*/  = "";
 //BA.debugLineNum = 508;BA.debugLine="Main.strUserOrg = \"\"";
parent.mostCurrent._main._struserorg /*String*/  = "";
 //BA.debugLineNum = 509;BA.debugLine="Main.username = \"\"";
parent.mostCurrent._main._username /*String*/  = "";
 //BA.debugLineNum = 510;BA.debugLine="Activity.RemoveAllViews";
parent.mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 511;BA.debugLine="Activity.finish";
parent.mostCurrent._activity.Finish();
 //BA.debugLineNum = 512;BA.debugLine="StartActivity(register)";
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
 //BA.debugLineNum = 515;BA.debugLine="StartActivity(frmEditProfile)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(parent.mostCurrent._frmeditprofile.getObject()));
 if (true) break;

case 10:
//C
this.state = -1;
;
 //BA.debugLineNum = 517;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public static String  _btnhelpecoregion_click() throws Exception{
 //BA.debugLineNum = 1228;BA.debugLine="Private Sub btnHelpEcoregion_Click";
 //BA.debugLineNum = 1229;BA.debugLine="Show_Terrain_Info";
_show_terrain_info();
 //BA.debugLineNum = 1230;BA.debugLine="End Sub";
return "";
}
public static String  _btninformacion_click() throws Exception{
 //BA.debugLineNum = 600;BA.debugLine="Private Sub btnInformacion_Click";
 //BA.debugLineNum = 601;BA.debugLine="tabStripMain.ScrollTo(1, True)";
mostCurrent._tabstripmain.ScrollTo((int) (1),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 603;BA.debugLine="End Sub";
return "";
}
public static String  _btnmapa_click() throws Exception{
 //BA.debugLineNum = 572;BA.debugLine="Private Sub btnMapa_Click";
 //BA.debugLineNum = 573;BA.debugLine="tabStripMain.ScrollTo(0, True)";
mostCurrent._tabstripmain.ScrollTo((int) (0),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 574;BA.debugLine="End Sub";
return "";
}
public static String  _btnmasinfomunicipio_click() throws Exception{
 //BA.debugLineNum = 1802;BA.debugLine="Private Sub btnMasInfoMunicipio_Click";
 //BA.debugLineNum = 1803;BA.debugLine="StartActivity(pi.OpenBrowser(municipioURL))";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._pi.OpenBrowser(mostCurrent._municipiourl)));
 //BA.debugLineNum = 1804;BA.debugLine="End Sub";
return "";
}
public static String  _btnmenu_main_click() throws Exception{
 //BA.debugLineNum = 451;BA.debugLine="Sub btnMenu_Main_Click";
 //BA.debugLineNum = 452;BA.debugLine="Drawer.LeftOpen = True";
mostCurrent._drawer._setleftopen /*boolean*/ (anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 453;BA.debugLine="If Main.username = \"guest\" Or Main.username = \"No";
if ((mostCurrent._main._username /*String*/ ).equals("guest") || (mostCurrent._main._username /*String*/ ).equals("None") || (mostCurrent._main._username /*String*/ ).equals("")) { 
 //BA.debugLineNum = 454;BA.debugLine="btnCerrarSesion.Text = \"Registrarse!\"";
mostCurrent._btncerrarsesion.setText(BA.ObjectToCharSequence("Registrarse!"));
 }else {
 //BA.debugLineNum = 456;BA.debugLine="btnCerrarSesion.Visible = False";
mostCurrent._btncerrarsesion.setVisible(anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 458;BA.debugLine="End Sub";
return "";
}
public static String  _btnmuestreos_click() throws Exception{
 //BA.debugLineNum = 518;BA.debugLine="Sub btnMuestreos_Click";
 //BA.debugLineNum = 519;BA.debugLine="Aprender_Muestreo.origen = \"menu\"";
mostCurrent._aprender_muestreo._origen /*String*/  = "menu";
 //BA.debugLineNum = 520;BA.debugLine="StartActivity(Aprender_Muestreo)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._aprender_muestreo.getObject()));
 //BA.debugLineNum = 521;BA.debugLine="End Sub";
return "";
}
public static String  _btnmunicipiofb_click() throws Exception{
 //BA.debugLineNum = 1811;BA.debugLine="Private Sub btnMunicipioFB_Click";
 //BA.debugLineNum = 1812;BA.debugLine="StartActivity(pi.OpenBrowser(municipioFB))";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._pi.OpenBrowser(mostCurrent._municipiofb)));
 //BA.debugLineNum = 1813;BA.debugLine="End Sub";
return "";
}
public static String  _btnmunicipioig_click() throws Exception{
 //BA.debugLineNum = 1805;BA.debugLine="Private Sub btnMunicipioIG_Click";
 //BA.debugLineNum = 1806;BA.debugLine="StartActivity(pi.OpenBrowser(municipioIG))";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._pi.OpenBrowser(mostCurrent._municipioig)));
 //BA.debugLineNum = 1807;BA.debugLine="End Sub";
return "";
}
public static String  _btnmunicipiotw_click() throws Exception{
 //BA.debugLineNum = 1814;BA.debugLine="Private Sub btnMunicipioTW_Click";
 //BA.debugLineNum = 1815;BA.debugLine="StartActivity(pi.OpenBrowser(municipioTW))";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._pi.OpenBrowser(mostCurrent._municipiotw)));
 //BA.debugLineNum = 1816;BA.debugLine="End Sub";
return "";
}
public static String  _btnmunicipioyt_click() throws Exception{
 //BA.debugLineNum = 1808;BA.debugLine="Private Sub btnMunicipioYT_Click";
 //BA.debugLineNum = 1809;BA.debugLine="StartActivity(pi.OpenBrowser(municipioYT))";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._pi.OpenBrowser(mostCurrent._municipioyt)));
 //BA.debugLineNum = 1810;BA.debugLine="End Sub";
return "";
}
public static String  _btnokterraininfo_click() throws Exception{
 //BA.debugLineNum = 1443;BA.debugLine="Private Sub btnOkTerrainInfo_Click";
 //BA.debugLineNum = 1444;BA.debugLine="cust_dialog.Close(xui.DialogResponse_Positive)";
mostCurrent._cust_dialog._close /*boolean*/ (mostCurrent._xui.DialogResponse_Positive);
 //BA.debugLineNum = 1445;BA.debugLine="End Sub";
return "";
}
public static String  _btnperfil_click() throws Exception{
 //BA.debugLineNum = 617;BA.debugLine="Private Sub btnPerfil_Click";
 //BA.debugLineNum = 618;BA.debugLine="tabStripMain.ScrollTo(3, True)";
mostCurrent._tabstripmain.ScrollTo((int) (3),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 619;BA.debugLine="End Sub";
return "";
}
public static String  _btnpoliticadatos_click() throws Exception{
 //BA.debugLineNum = 522;BA.debugLine="Sub btnPoliticaDatos_Click";
 //BA.debugLineNum = 523;BA.debugLine="StartActivity(frmPoliticaDatos)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._frmpoliticadatos.getObject()));
 //BA.debugLineNum = 525;BA.debugLine="End Sub";
return "";
}
public static String  _btnreportar_click() throws Exception{
 //BA.debugLineNum = 1138;BA.debugLine="Sub btnReportar_Click";
 //BA.debugLineNum = 1156;BA.debugLine="End Sub";
return "";
}
public static String  _btnshare_click() throws Exception{
com.madelephantstudios.MESShareLibrary.MESShareLibrary _share = null;
 //BA.debugLineNum = 2298;BA.debugLine="Sub btnShare_Click";
 //BA.debugLineNum = 2301;BA.debugLine="Dim share As MESShareLibrary";
_share = new com.madelephantstudios.MESShareLibrary.MESShareLibrary();
 //BA.debugLineNum = 2302;BA.debugLine="File.Copy(File.DirAssets, imagetoshare, File.DirD";
anywheresoftware.b4a.keywords.Common.File.Copy(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._imagetoshare,anywheresoftware.b4a.keywords.Common.File.getDirDefaultExternal(),"Logo_preservamos_small.png");
 //BA.debugLineNum = 2303;BA.debugLine="share.sharebinary(\"file://\" & Starter.savedir & \"";
_share.sharebinary(mostCurrent.activityBA,"file://"+mostCurrent._starter._savedir /*String*/ +"/Logo_preservamos_small.png","image/png","PreserVamos - Ciencia Ciudadana","Participo de PreserVamos y gané esta medalla!");
 //BA.debugLineNum = 2315;BA.debugLine="pnlShare.RemoveView";
mostCurrent._pnlshare.RemoveView();
 //BA.debugLineNum = 2316;BA.debugLine="fondogris_share.RemoveView";
mostCurrent._fondogris_share.RemoveView();
 //BA.debugLineNum = 2374;BA.debugLine="End Sub";
return "";
}
public static String  _btnstarttest_click() throws Exception{
String _rndstr = "";
 //BA.debugLineNum = 1199;BA.debugLine="Private Sub btnStartTest_Click";
 //BA.debugLineNum = 1202;BA.debugLine="If Main.username = \"guest\" Or Main.username = \"No";
if ((mostCurrent._main._username /*String*/ ).equals("guest") || (mostCurrent._main._username /*String*/ ).equals("None") || (mostCurrent._main._username /*String*/ ).equals("")) { 
 //BA.debugLineNum = 1203;BA.debugLine="Dim RndStr As String";
_rndstr = "";
 //BA.debugLineNum = 1204;BA.debugLine="RndStr = utilidades.RandomString(6)";
_rndstr = mostCurrent._utilidades._randomstring /*String*/ (mostCurrent.activityBA,(int) (6));
 //BA.debugLineNum = 1205;BA.debugLine="Main.username = \"guest_\" & RndStr";
mostCurrent._main._username /*String*/  = "guest_"+_rndstr;
 };
 //BA.debugLineNum = 1208;BA.debugLine="If selectedTest = \"hidro\" Then";
if ((mostCurrent._selectedtest).equals("hidro")) { 
 //BA.debugLineNum = 1209;BA.debugLine="tipoAnalisis = \"hidro\"";
mostCurrent._tipoanalisis = "hidro";
 //BA.debugLineNum = 1210;BA.debugLine="Form_Reporte.origen = \"Form_Main\"";
mostCurrent._form_reporte._origen /*String*/  = "Form_Main";
 //BA.debugLineNum = 1211;BA.debugLine="StartActivity(mod_Hidro)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._mod_hidro.getObject()));
 }else if((mostCurrent._selectedtest).equals("residuos")) { 
 //BA.debugLineNum = 1213;BA.debugLine="tipoAnalisis = \"residuos\"";
mostCurrent._tipoanalisis = "residuos";
 //BA.debugLineNum = 1214;BA.debugLine="Form_Reporte.origen = \"Form_Main\"";
mostCurrent._form_reporte._origen /*String*/  = "Form_Main";
 //BA.debugLineNum = 1215;BA.debugLine="StartActivity(mod_Residuos)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._mod_residuos.getObject()));
 }else if((mostCurrent._selectedtest).equals("habitat")) { 
 //BA.debugLineNum = 1217;BA.debugLine="tipoAnalisis = \"habitat\"";
mostCurrent._tipoanalisis = "habitat";
 //BA.debugLineNum = 1218;BA.debugLine="Form_Reporte.origen = \"Form_Main\"";
mostCurrent._form_reporte._origen /*String*/  = "Form_Main";
 //BA.debugLineNum = 1220;BA.debugLine="If File.Exists(Starter.savedir & \"/no_aprender.t";
if (anywheresoftware.b4a.keywords.Common.File.Exists(mostCurrent._starter._savedir /*String*/ +"/no_aprender.txt","")==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 1221;BA.debugLine="Aprender_Muestreo.origen = \"main\"";
mostCurrent._aprender_muestreo._origen /*String*/  = "main";
 //BA.debugLineNum = 1222;BA.debugLine="StartActivity(Aprender_Muestreo)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._aprender_muestreo.getObject()));
 }else {
 //BA.debugLineNum = 1224;BA.debugLine="StartActivity(Form_Reporte)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._form_reporte.getObject()));
 };
 };
 //BA.debugLineNum = 1227;BA.debugLine="End Sub";
return "";
}
public static String  _btnverdatosmunicipio_click() throws Exception{
 //BA.debugLineNum = 1793;BA.debugLine="Private Sub btnVerDatosMunicipio_Click";
 //BA.debugLineNum = 1794;BA.debugLine="If Municipios_numresults <> \"0\" Then";
if (_municipios_numresults!=(double)(Double.parseDouble("0"))) { 
 //BA.debugLineNum = 1795;BA.debugLine="frmMapa.datosMunicipio_string = Municipios_parse";
mostCurrent._frmmapa._datosmunicipio_string /*String*/  = mostCurrent._municipios_parser_string;
 //BA.debugLineNum = 1796;BA.debugLine="frmMapa.origen=\"municipio\"";
mostCurrent._frmmapa._origen /*String*/  = "municipio";
 //BA.debugLineNum = 1797;BA.debugLine="StartActivity(frmMapa)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._frmmapa.getObject()));
 }else {
 //BA.debugLineNum = 1799;BA.debugLine="ToastMessageShow(\"No hay envíos de ese municipio";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("No hay envíos de ese municipio todavia"),anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 1801;BA.debugLine="End Sub";
return "";
}
public static String  _btnverestadisticasmunicipio_click() throws Exception{
 //BA.debugLineNum = 1838;BA.debugLine="Private Sub btnVerEstadisticasMunicipio_Click";
 //BA.debugLineNum = 1839;BA.debugLine="If Municipios_numresults <> \"0\" Then";
if (_municipios_numresults!=(double)(Double.parseDouble("0"))) { 
 //BA.debugLineNum = 1840;BA.debugLine="frmMunicipioEstadisticas.listaAgua_Promedio = li";
mostCurrent._frmmunicipioestadisticas._listaagua_promedio /*anywheresoftware.b4a.objects.collections.List*/  = mostCurrent._listaagua_promedio;
 //BA.debugLineNum = 1841;BA.debugLine="frmMunicipioEstadisticas.listaBio_Promedio = lis";
mostCurrent._frmmunicipioestadisticas._listabio_promedio /*anywheresoftware.b4a.objects.collections.List*/  = mostCurrent._listabio_promedio;
 //BA.debugLineNum = 1842;BA.debugLine="frmMunicipioEstadisticas.listaHidro_Promedio = l";
mostCurrent._frmmunicipioestadisticas._listahidro_promedio /*anywheresoftware.b4a.objects.collections.List*/  = mostCurrent._listahidro_promedio;
 //BA.debugLineNum = 1843;BA.debugLine="frmMunicipioEstadisticas.listaUsos_Promedio = li";
mostCurrent._frmmunicipioestadisticas._listausos_promedio /*anywheresoftware.b4a.objects.collections.List*/  = mostCurrent._listausos_promedio;
 //BA.debugLineNum = 1844;BA.debugLine="frmMunicipioEstadisticas.listaExoticas_Promedio";
mostCurrent._frmmunicipioestadisticas._listaexoticas_promedio /*anywheresoftware.b4a.objects.collections.List*/  = mostCurrent._listaexoticas_promedio;
 //BA.debugLineNum = 1845;BA.debugLine="ToastMessageShow(\"Cargando detalle del municipio";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Cargando detalle del municipio, por favor aguarde..."),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1846;BA.debugLine="StartActivity(frmMunicipioEstadisticas)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._frmmunicipioestadisticas.getObject()));
 }else {
 //BA.debugLineNum = 1848;BA.debugLine="ToastMessageShow(\"No hay envíos de ese municipio";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("No hay envíos de ese municipio todavia"),anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 1850;BA.debugLine="End Sub";
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
 //BA.debugLineNum = 461;BA.debugLine="If Main.username = \"guest\" Or Main.username = \"No";
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
 //BA.debugLineNum = 463;BA.debugLine="Msgbox2Async(\"Necesita estar registrado para ver";
anywheresoftware.b4a.keywords.Common.Msgbox2Async(BA.ObjectToCharSequence("Necesita estar registrado para ver su perfil, ¿desea hacer ingresar ahora?"),BA.ObjectToCharSequence("Ingreso"),"Si, tengo internet","","No, no tengo internet ahora",(anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper(), (android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null)),processBA,anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 464;BA.debugLine="Wait For Msgbox_Result (Result As Int)";
anywheresoftware.b4a.keywords.Common.WaitFor("msgbox_result", processBA, this, null);
this.state = 11;
return;
case 11:
//C
this.state = 4;
_result = (Integer) result[0];
;
 //BA.debugLineNum = 465;BA.debugLine="If Result = DialogResponse.POSITIVE Then";
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
 //BA.debugLineNum = 466;BA.debugLine="Main.strUserID = \"\"";
parent.mostCurrent._main._struserid /*String*/  = "";
 //BA.debugLineNum = 467;BA.debugLine="Main.strUserName = \"\"";
parent.mostCurrent._main._strusername /*String*/  = "";
 //BA.debugLineNum = 468;BA.debugLine="Main.strUserLocation = \"\"";
parent.mostCurrent._main._struserlocation /*String*/  = "";
 //BA.debugLineNum = 469;BA.debugLine="Main.strUserEmail = \"\"";
parent.mostCurrent._main._struseremail /*String*/  = "";
 //BA.debugLineNum = 470;BA.debugLine="Main.strUserOrg = \"\"";
parent.mostCurrent._main._struserorg /*String*/  = "";
 //BA.debugLineNum = 471;BA.debugLine="Main.username = \"\"";
parent.mostCurrent._main._username /*String*/  = "";
 //BA.debugLineNum = 472;BA.debugLine="Activity.RemoveAllViews";
parent.mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 473;BA.debugLine="Activity.finish";
parent.mostCurrent._activity.Finish();
 //BA.debugLineNum = 474;BA.debugLine="StartActivity(register)";
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
 //BA.debugLineNum = 477;BA.debugLine="StartActivity(frmEditProfile)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(parent.mostCurrent._frmeditprofile.getObject()));
 if (true) break;

case 10:
//C
this.state = -1;
;
 //BA.debugLineNum = 479;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public static anywheresoftware.b4a.keywords.Common.ResumableSubWrapper  _buscar_ecoregion() throws Exception{
ResumableSub_Buscar_Ecoregion rsub = new ResumableSub_Buscar_Ecoregion(null);
rsub.resume(processBA, null);
return (anywheresoftware.b4a.keywords.Common.ResumableSubWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.keywords.Common.ResumableSubWrapper(), rsub);
}
public static class ResumableSub_Buscar_Ecoregion extends BA.ResumableSub {
public ResumableSub_Buscar_Ecoregion(appear.pnud.preservamos.form_main parent) {
this.parent = parent;
}
appear.pnud.preservamos.form_main parent;
appear.pnud.preservamos.httpjob _j = null;
String _ret = "";
anywheresoftware.b4a.objects.collections.Map _act = null;
anywheresoftware.b4a.objects.collections.JSONParser _parser = null;

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
 //BA.debugLineNum = 2484;BA.debugLine="Dim j As HttpJob";
_j = new appear.pnud.preservamos.httpjob();
 //BA.debugLineNum = 2485;BA.debugLine="j.Initialize(\"\", Me)";
_j._initialize /*String*/ (processBA,"",form_main.getObject());
 //BA.debugLineNum = 2486;BA.debugLine="j.Download(Main.serverPath & \"/\" & Main.serverCon";
_j._download /*String*/ (parent.mostCurrent._main._serverpath /*String*/ +"/"+parent.mostCurrent._main._serverconnectionfolder /*String*/ +"/get_ecoregion.php?user_latitude="+parent.mostCurrent._main._latitud /*String*/ +"&user_longitude="+parent.mostCurrent._main._longitud /*String*/ );
 //BA.debugLineNum = 2487;BA.debugLine="Wait For (j) JobDone(j As HttpJob)";
anywheresoftware.b4a.keywords.Common.WaitFor("jobdone", processBA, this, (Object)(_j));
this.state = 19;
return;
case 19:
//C
this.state = 1;
_j = (appear.pnud.preservamos.httpjob) result[0];
;
 //BA.debugLineNum = 2488;BA.debugLine="Log(\"Get_terrain messages: \" & j.Success)";
anywheresoftware.b4a.keywords.Common.LogImpl("05111814","Get_terrain messages: "+BA.ObjectToString(_j._success /*boolean*/ ),0);
 //BA.debugLineNum = 2489;BA.debugLine="If j.Success Then";
if (true) break;

case 1:
//if
this.state = 18;
if (_j._success /*boolean*/ ) { 
this.state = 3;
}else {
this.state = 17;
}if (true) break;

case 3:
//C
this.state = 4;
 //BA.debugLineNum = 2490;BA.debugLine="Main.hayinternet = True";
parent.mostCurrent._main._hayinternet /*boolean*/  = anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 2491;BA.debugLine="Try";
if (true) break;

case 4:
//try
this.state = 15;
this.catchState = 14;
this.state = 6;
if (true) break;

case 6:
//C
this.state = 7;
this.catchState = 14;
 //BA.debugLineNum = 2493;BA.debugLine="Dim ret As String";
_ret = "";
 //BA.debugLineNum = 2494;BA.debugLine="Dim act As Map";
_act = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 2495;BA.debugLine="ret = j.GetString";
_ret = _j._getstring /*String*/ ();
 //BA.debugLineNum = 2496;BA.debugLine="Dim parser As JSONParser";
_parser = new anywheresoftware.b4a.objects.collections.JSONParser();
 //BA.debugLineNum = 2497;BA.debugLine="parser.Initialize(ret)";
_parser.Initialize(_ret);
 //BA.debugLineNum = 2498;BA.debugLine="act = parser.NextObject";
_act = _parser.NextObject();
 //BA.debugLineNum = 2501;BA.debugLine="Main.currentEcoregion = act.Get(\"ecoregion\")";
parent.mostCurrent._main._currentecoregion /*String*/  = BA.ObjectToString(_act.Get((Object)("ecoregion")));
 //BA.debugLineNum = 2502;BA.debugLine="Main.currentSubEcoregion = act.Get(\"subecoreg\")";
parent.mostCurrent._main._currentsubecoregion /*String*/  = BA.ObjectToString(_act.Get((Object)("subecoreg")));
 //BA.debugLineNum = 2503;BA.debugLine="Log(\"userEcoregion: \" & Main.currentEcoregion)";
anywheresoftware.b4a.keywords.Common.LogImpl("05111829","userEcoregion: "+parent.mostCurrent._main._currentecoregion /*String*/ ,0);
 //BA.debugLineNum = 2504;BA.debugLine="Log(\"userSubecoregion: \" & Main.currentSubEcore";
anywheresoftware.b4a.keywords.Common.LogImpl("05111830","userSubecoregion: "+parent.mostCurrent._main._currentsubecoregion /*String*/ ,0);
 //BA.debugLineNum = 2507;BA.debugLine="If Main.currentEcoregion = Null Then";
if (true) break;

case 7:
//if
this.state = 12;
if (parent.mostCurrent._main._currentecoregion /*String*/ == null) { 
this.state = 9;
}else {
this.state = 11;
}if (true) break;

case 9:
//C
this.state = 12;
 //BA.debugLineNum = 2508;BA.debugLine="ToastMessageShow(\"No se encontró tu ecorregión";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("No se encontró tu ecorregión"),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 2509;BA.debugLine="lblHayGeoref.Visible = True";
parent.mostCurrent._lblhaygeoref.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 2510;BA.debugLine="j.Release";
_j._release /*String*/ ();
 //BA.debugLineNum = 2511;BA.debugLine="Return False";
if (true) {
anywheresoftware.b4a.keywords.Common.ReturnFromResumableSub(this,(Object)(anywheresoftware.b4a.keywords.Common.False));return;};
 if (true) break;

case 11:
//C
this.state = 12;
 //BA.debugLineNum = 2513;BA.debugLine="lblHayGeoref.Visible = False";
parent.mostCurrent._lblhaygeoref.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 2514;BA.debugLine="j.Release";
_j._release /*String*/ ();
 //BA.debugLineNum = 2515;BA.debugLine="Return True";
if (true) {
anywheresoftware.b4a.keywords.Common.ReturnFromResumableSub(this,(Object)(anywheresoftware.b4a.keywords.Common.True));return;};
 if (true) break;

case 12:
//C
this.state = 15;
;
 if (true) break;

case 14:
//C
this.state = 15;
this.catchState = 0;
 //BA.debugLineNum = 2518;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("05111844",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA)),0);
 //BA.debugLineNum = 2519;BA.debugLine="lblHayGeoref.Visible = True";
parent.mostCurrent._lblhaygeoref.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 2520;BA.debugLine="j.Release";
_j._release /*String*/ ();
 //BA.debugLineNum = 2521;BA.debugLine="Return False";
if (true) {
anywheresoftware.b4a.keywords.Common.ReturnFromResumableSub(this,(Object)(anywheresoftware.b4a.keywords.Common.False));return;};
 if (true) break;
if (true) break;

case 15:
//C
this.state = 18;
this.catchState = 0;
;
 if (true) break;

case 17:
//C
this.state = 18;
 //BA.debugLineNum = 2525;BA.debugLine="Main.hayinternet = False";
parent.mostCurrent._main._hayinternet /*boolean*/  = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 2526;BA.debugLine="ToastMessageShow(\"No se encontró tu ecorregión\",";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("No se encontró tu ecorregión"),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 2527;BA.debugLine="lblHayGeoref.Visible = True";
parent.mostCurrent._lblhaygeoref.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 2528;BA.debugLine="lblHayInternet.Visible = True";
parent.mostCurrent._lblhayinternet.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 2529;BA.debugLine="j.Release";
_j._release /*String*/ ();
 //BA.debugLineNum = 2530;BA.debugLine="Return False";
if (true) {
anywheresoftware.b4a.keywords.Common.ReturnFromResumableSub(this,(Object)(anywheresoftware.b4a.keywords.Common.False));return;};
 if (true) break;

case 18:
//C
this.state = -1;
;
 //BA.debugLineNum = 2533;BA.debugLine="End Sub";
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
public static anywheresoftware.b4a.keywords.Common.ResumableSubWrapper  _buscarlocalizacion() throws Exception{
ResumableSub_BuscarLocalizacion rsub = new ResumableSub_BuscarLocalizacion(null);
rsub.resume(processBA, null);
return (anywheresoftware.b4a.keywords.Common.ResumableSubWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.keywords.Common.ResumableSubWrapper(), rsub);
}
public static class ResumableSub_BuscarLocalizacion extends BA.ResumableSub {
public ResumableSub_BuscarLocalizacion(appear.pnud.preservamos.form_main parent) {
this.parent = parent;
}
appear.pnud.preservamos.form_main parent;
int _timeoutruns = 0;

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
 //BA.debugLineNum = 2662;BA.debugLine="Wait For MapFragment1_Ready";
anywheresoftware.b4a.keywords.Common.WaitFor("mapfragment1_ready", processBA, this, null);
this.state = 16;
return;
case 16:
//C
this.state = 1;
;
 //BA.debugLineNum = 2663;BA.debugLine="Sleep(400) 'si no lo hago dormir, no encuentra";
anywheresoftware.b4a.keywords.Common.Sleep(mostCurrent.activityBA,this,(int) (400));
this.state = 17;
return;
case 17:
//C
this.state = 1;
;
 //BA.debugLineNum = 2664;BA.debugLine="gmap = MapFragment1.GetMap";
parent.mostCurrent._gmap = parent.mostCurrent._mapfragment1.GetMap();
 //BA.debugLineNum = 2665;BA.debugLine="gmap.MyLocationEnabled = True";
parent.mostCurrent._gmap.setMyLocationEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 2666;BA.debugLine="lblEstado.Text = \"Buscando tu ubicación\"";
parent.mostCurrent._lblestado.setText(BA.ObjectToCharSequence("Buscando tu ubicación"));
 //BA.debugLineNum = 2668;BA.debugLine="If gmap.IsInitialized = False Then";
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
 //BA.debugLineNum = 2669;BA.debugLine="ToastMessageShow(\"Error initializing map.\", True";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Error initializing map."),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 2670;BA.debugLine="Log(\"Error initalizing gmap\")";
anywheresoftware.b4a.keywords.Common.LogImpl("05242889","Error initalizing gmap",0);
 //BA.debugLineNum = 2671;BA.debugLine="Return False";
if (true) {
anywheresoftware.b4a.keywords.Common.ReturnFromResumableSub(this,(Object)(anywheresoftware.b4a.keywords.Common.False));return;};
 if (true) break;

case 5:
//C
this.state = 6;
 //BA.debugLineNum = 2673;BA.debugLine="Dim timeoutruns As Int = 0";
_timeoutruns = (int) (0);
 //BA.debugLineNum = 2674;BA.debugLine="Do While Not(gmap.MyLocation.IsInitialized) And";
if (true) break;

case 6:
//do while
this.state = 9;
while (anywheresoftware.b4a.keywords.Common.Not(parent.mostCurrent._gmap.getMyLocation().IsInitialized()) && _timeoutruns<400) {
this.state = 8;
if (true) break;
}
if (true) break;

case 8:
//C
this.state = 6;
 //BA.debugLineNum = 2675;BA.debugLine="Sleep(0)";
anywheresoftware.b4a.keywords.Common.Sleep(mostCurrent.activityBA,this,(int) (0));
this.state = 18;
return;
case 18:
//C
this.state = 6;
;
 //BA.debugLineNum = 2676;BA.debugLine="timeoutruns = timeoutruns + 1";
_timeoutruns = (int) (_timeoutruns+1);
 if (true) break;

case 9:
//C
this.state = 10;
;
 if (true) break;
;
 //BA.debugLineNum = 2681;BA.debugLine="If gmap.MyLocation.IsInitialized Then";

case 10:
//if
this.state = 15;
if (parent.mostCurrent._gmap.getMyLocation().IsInitialized()) { 
this.state = 12;
}else {
this.state = 14;
}if (true) break;

case 12:
//C
this.state = 15;
 //BA.debugLineNum = 2682;BA.debugLine="Main.latitud = gmap.MyLocation.Latitude";
parent.mostCurrent._main._latitud /*String*/  = BA.NumberToString(parent.mostCurrent._gmap.getMyLocation().getLatitude());
 //BA.debugLineNum = 2683;BA.debugLine="Main.longitud = gmap.MyLocation.Longitude";
parent.mostCurrent._main._longitud /*String*/  = BA.NumberToString(parent.mostCurrent._gmap.getMyLocation().getLongitude());
 //BA.debugLineNum = 2684;BA.debugLine="Log(\"Georreferenciación OK\")";
anywheresoftware.b4a.keywords.Common.LogImpl("05242903","Georreferenciación OK",0);
 //BA.debugLineNum = 2685;BA.debugLine="Log(\"latitud: \" & Main.latitud & \"//longitud: \"";
anywheresoftware.b4a.keywords.Common.LogImpl("05242904","latitud: "+parent.mostCurrent._main._latitud /*String*/ +"//longitud: "+parent.mostCurrent._main._longitud /*String*/ ,0);
 //BA.debugLineNum = 2686;BA.debugLine="Return True";
if (true) {
anywheresoftware.b4a.keywords.Common.ReturnFromResumableSub(this,(Object)(anywheresoftware.b4a.keywords.Common.True));return;};
 if (true) break;

case 14:
//C
this.state = 15;
 //BA.debugLineNum = 2688;BA.debugLine="Return False";
if (true) {
anywheresoftware.b4a.keywords.Common.ReturnFromResumableSub(this,(Object)(anywheresoftware.b4a.keywords.Common.False));return;};
 if (true) break;

case 15:
//C
this.state = -1;
;
 //BA.debugLineNum = 2691;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public static anywheresoftware.b4a.keywords.Common.ResumableSubWrapper  _buscarreversegeoref() throws Exception{
ResumableSub_buscarReverseGeoref rsub = new ResumableSub_buscarReverseGeoref(null);
rsub.resume(processBA, null);
return (anywheresoftware.b4a.keywords.Common.ResumableSubWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.keywords.Common.ResumableSubWrapper(), rsub);
}
public static class ResumableSub_buscarReverseGeoref extends BA.ResumableSub {
public ResumableSub_buscarReverseGeoref(appear.pnud.preservamos.form_main parent) {
this.parent = parent;
}
appear.pnud.preservamos.form_main parent;
int _wait = 0;
double _geolatitude = 0;
double _geolongitude = 0;

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
 //BA.debugLineNum = 2701;BA.debugLine="Log(\"Georeferenciando!\")";
anywheresoftware.b4a.keywords.Common.LogImpl("05308418","Georeferenciando!",0);
 //BA.debugLineNum = 2703;BA.debugLine="If gmap.IsInitialized = False Then";
if (true) break;

case 1:
//if
this.state = 21;
if (parent.mostCurrent._gmap.IsInitialized()==anywheresoftware.b4a.keywords.Common.False) { 
this.state = 3;
}else {
this.state = 5;
}if (true) break;

case 3:
//C
this.state = 21;
 //BA.debugLineNum = 2704;BA.debugLine="ToastMessageShow(\"Error initializing map.\", True";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Error initializing map."),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 2705;BA.debugLine="Log(\"Gmap not initialized\")";
anywheresoftware.b4a.keywords.Common.LogImpl("05308422","Gmap not initialized",0);
 //BA.debugLineNum = 2706;BA.debugLine="Main.latitud = Null";
parent.mostCurrent._main._latitud /*String*/  = BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Null);
 //BA.debugLineNum = 2707;BA.debugLine="Main.longitud = Null";
parent.mostCurrent._main._longitud /*String*/  = BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Null);
 //BA.debugLineNum = 2708;BA.debugLine="lblHayGeoref.Visible = True";
parent.mostCurrent._lblhaygeoref.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 2709;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 //BA.debugLineNum = 2710;BA.debugLine="Return False";
if (true) {
anywheresoftware.b4a.keywords.Common.ReturnFromResumableSub(this,(Object)(anywheresoftware.b4a.keywords.Common.False));return;};
 if (true) break;

case 5:
//C
this.state = 6;
 //BA.debugLineNum = 2713;BA.debugLine="Dim wait As Int = 0";
_wait = (int) (0);
 //BA.debugLineNum = 2714;BA.debugLine="Do While wait < 1000";
if (true) break;

case 6:
//do while
this.state = 15;
while (_wait<1000) {
this.state = 8;
if (true) break;
}
if (true) break;

case 8:
//C
this.state = 9;
 //BA.debugLineNum = 2715;BA.debugLine="If gmap.MyLocation.IsInitialized = False Then";
if (true) break;

case 9:
//if
this.state = 14;
if (parent.mostCurrent._gmap.getMyLocation().IsInitialized()==anywheresoftware.b4a.keywords.Common.False) { 
this.state = 11;
}else {
this.state = 13;
}if (true) break;

case 11:
//C
this.state = 14;
 //BA.debugLineNum = 2716;BA.debugLine="Sleep(0)";
anywheresoftware.b4a.keywords.Common.Sleep(mostCurrent.activityBA,this,(int) (0));
this.state = 22;
return;
case 22:
//C
this.state = 14;
;
 //BA.debugLineNum = 2717;BA.debugLine="wait = wait +1";
_wait = (int) (_wait+1);
 if (true) break;

case 13:
//C
this.state = 14;
 //BA.debugLineNum = 2719;BA.debugLine="wait = 1000";
_wait = (int) (1000);
 if (true) break;

case 14:
//C
this.state = 6;
;
 if (true) break;
;
 //BA.debugLineNum = 2725;BA.debugLine="If gmap.MyLocation.IsInitialized = True Then";

case 15:
//if
this.state = 20;
if (parent.mostCurrent._gmap.getMyLocation().IsInitialized()==anywheresoftware.b4a.keywords.Common.True) { 
this.state = 17;
}else {
this.state = 19;
}if (true) break;

case 17:
//C
this.state = 20;
 //BA.debugLineNum = 2726;BA.debugLine="Log(\"hay coordenadas\")";
anywheresoftware.b4a.keywords.Common.LogImpl("05308443","hay coordenadas",0);
 //BA.debugLineNum = 2727;BA.debugLine="Main.latitud = gmap.MyLocation.Latitude";
parent.mostCurrent._main._latitud /*String*/  = BA.NumberToString(parent.mostCurrent._gmap.getMyLocation().getLatitude());
 //BA.debugLineNum = 2728;BA.debugLine="Main.longitud = gmap.MyLocation.Longitude";
parent.mostCurrent._main._longitud /*String*/  = BA.NumberToString(parent.mostCurrent._gmap.getMyLocation().getLongitude());
 //BA.debugLineNum = 2729;BA.debugLine="lblHayGeoref.Visible = False";
parent.mostCurrent._lblhaygeoref.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 2731;BA.debugLine="georef.Initialize(\"georef\")";
parent._georef.Initialize(processBA,"georef");
 //BA.debugLineNum = 2732;BA.debugLine="Dim GeoLatitude, GeoLongitude As Double";
_geolatitude = 0;
_geolongitude = 0;
 //BA.debugLineNum = 2733;BA.debugLine="GeoLatitude = Main.latitud";
_geolatitude = (double)(Double.parseDouble(parent.mostCurrent._main._latitud /*String*/ ));
 //BA.debugLineNum = 2734;BA.debugLine="GeoLongitude = Main.longitud";
_geolongitude = (double)(Double.parseDouble(parent.mostCurrent._main._longitud /*String*/ ));
 //BA.debugLineNum = 2735;BA.debugLine="georef.GetFromLocation(GeoLatitude, GeoLongitud";
parent._georef.GetFromLocation(processBA,_geolatitude,_geolongitude,(int) (10),anywheresoftware.b4a.keywords.Common.Null);
 //BA.debugLineNum = 2736;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 //BA.debugLineNum = 2737;BA.debugLine="Return True";
if (true) {
anywheresoftware.b4a.keywords.Common.ReturnFromResumableSub(this,(Object)(anywheresoftware.b4a.keywords.Common.True));return;};
 if (true) break;

case 19:
//C
this.state = 20;
 //BA.debugLineNum = 2739;BA.debugLine="ToastMessageShow(\"No se encontraron tus coorden";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("No se encontraron tus coordenadas..."),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 2740;BA.debugLine="Log(\"No hay coordenadas\")";
anywheresoftware.b4a.keywords.Common.LogImpl("05308457","No hay coordenadas",0);
 //BA.debugLineNum = 2741;BA.debugLine="Main.latitud = Null";
parent.mostCurrent._main._latitud /*String*/  = BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Null);
 //BA.debugLineNum = 2742;BA.debugLine="Main.longitud = Null";
parent.mostCurrent._main._longitud /*String*/  = BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Null);
 //BA.debugLineNum = 2743;BA.debugLine="lblHayGeoref.Visible = True";
parent.mostCurrent._lblhaygeoref.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 2744;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 //BA.debugLineNum = 2745;BA.debugLine="Return False";
if (true) {
anywheresoftware.b4a.keywords.Common.ReturnFromResumableSub(this,(Object)(anywheresoftware.b4a.keywords.Common.False));return;};
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
 //BA.debugLineNum = 2750;BA.debugLine="End Sub";
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
 //BA.debugLineNum = 717;BA.debugLine="icoMapa.TextColor = Colors.ARGB(255,51,51,51)";
parent.mostCurrent._icomapa.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (51),(int) (51),(int) (51)));
 //BA.debugLineNum = 718;BA.debugLine="icoAnalizar.TextColor = Colors.ARGB(255,51,51,51)";
parent.mostCurrent._icoanalizar.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (51),(int) (51),(int) (51)));
 //BA.debugLineNum = 719;BA.debugLine="icoInformacion.TextColor = Colors.ARGB(255,51,51,";
parent.mostCurrent._icoinformacion.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (51),(int) (51),(int) (51)));
 //BA.debugLineNum = 720;BA.debugLine="icoPerfil.TextColor = Colors.ARGB(255,51,51,51)";
parent.mostCurrent._icoperfil.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (51),(int) (51),(int) (51)));
 //BA.debugLineNum = 721;BA.debugLine="icoMapa.TextSize = 40";
parent.mostCurrent._icomapa.setTextSize((float) (40));
 //BA.debugLineNum = 722;BA.debugLine="icoAnalizar.TextSize = 40";
parent.mostCurrent._icoanalizar.setTextSize((float) (40));
 //BA.debugLineNum = 723;BA.debugLine="icoInformacion.TextSize = 40";
parent.mostCurrent._icoinformacion.setTextSize((float) (40));
 //BA.debugLineNum = 724;BA.debugLine="icoPerfil.TextSize = 40";
parent.mostCurrent._icoperfil.setTextSize((float) (40));
 //BA.debugLineNum = 725;BA.debugLine="btnMapa.Text = \"Explorar\"";
parent.mostCurrent._btnmapa.setText(BA.ObjectToCharSequence("Explorar"));
 //BA.debugLineNum = 726;BA.debugLine="btnInformacion.Text = \"Municipio\"";
parent.mostCurrent._btninformacion.setText(BA.ObjectToCharSequence("Municipio"));
 //BA.debugLineNum = 727;BA.debugLine="btnAnalizar.Text = \"Analizar\"";
parent.mostCurrent._btnanalizar.setText(BA.ObjectToCharSequence("Analizar"));
 //BA.debugLineNum = 728;BA.debugLine="btnPerfil.Text = \"Perfil\"";
parent.mostCurrent._btnperfil.setText(BA.ObjectToCharSequence("Perfil"));
 //BA.debugLineNum = 730;BA.debugLine="icoAlerta.SetTextColorAnimated(300, Colors.white)";
parent.mostCurrent._icoalerta.SetTextColorAnimated((int) (300),anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 731;BA.debugLine="icoAlerta.SetTextSizeAnimated(300,50)";
parent.mostCurrent._icoalerta.SetTextSizeAnimated((int) (300),(float) (50));
 //BA.debugLineNum = 732;BA.debugLine="btnCirculo.SetLayoutAnimated(300, (icoAlerta.Left";
parent.mostCurrent._btncirculo.SetLayoutAnimated((int) (300),(int) ((parent.mostCurrent._icoalerta.getLeft()-((parent.mostCurrent._btncirculo.getWidth()-parent.mostCurrent._icoalerta.getWidth())/(double)2))),parent.mostCurrent._btncirculo.getTop(),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (80)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (80)));
 //BA.debugLineNum = 733;BA.debugLine="btnPerfil.Text = \"\"";
parent.mostCurrent._btnperfil.setText(BA.ObjectToCharSequence(""));
 //BA.debugLineNum = 736;BA.debugLine="Msgbox2Async(\"Podrás emitir un alerta verde sobre";
anywheresoftware.b4a.keywords.Common.Msgbox2Async(BA.ObjectToCharSequence("Podrás emitir un alerta verde sobre algún evento ecológico, como una floración de algas, una mortandad de peces, o un derrame industrial!"),BA.ObjectToCharSequence("¡ALERTA VERDE!"),"Proseguir!","","Cancelar alerta",(anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper(), (android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null)),processBA,anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 737;BA.debugLine="Wait For Msgbox_Result (Result As Int)";
anywheresoftware.b4a.keywords.Common.WaitFor("msgbox_result", processBA, this, null);
this.state = 17;
return;
case 17:
//C
this.state = 1;
_result = (Integer) result[0];
;
 //BA.debugLineNum = 738;BA.debugLine="If Result = DialogResponse.POSITIVE Then";
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
 //BA.debugLineNum = 739;BA.debugLine="StartActivity(Alertas)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(parent.mostCurrent._alertas.getObject()));
 if (true) break;

case 5:
//C
this.state = 6;
 //BA.debugLineNum = 741;BA.debugLine="If tabStripMain.CurrentPage = 0 Then";
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
 //BA.debugLineNum = 742;BA.debugLine="icoAlerta.TextColor = Colors.ARGB(255,51,51,51)";
parent.mostCurrent._icoalerta.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (51),(int) (51),(int) (51)));
 //BA.debugLineNum = 743;BA.debugLine="icoAnalizar.TextColor = Colors.ARGB(255,51,51,5";
parent.mostCurrent._icoanalizar.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (51),(int) (51),(int) (51)));
 //BA.debugLineNum = 744;BA.debugLine="icoInformacion.TextColor = Colors.ARGB(255,51,5";
parent.mostCurrent._icoinformacion.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (51),(int) (51),(int) (51)));
 //BA.debugLineNum = 745;BA.debugLine="icoPerfil.TextColor = Colors.ARGB(255,51,51,51)";
parent.mostCurrent._icoperfil.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (51),(int) (51),(int) (51)));
 //BA.debugLineNum = 746;BA.debugLine="icoAlerta.TextSize = 40";
parent.mostCurrent._icoalerta.setTextSize((float) (40));
 //BA.debugLineNum = 747;BA.debugLine="icoAnalizar.TextSize = 40";
parent.mostCurrent._icoanalizar.setTextSize((float) (40));
 //BA.debugLineNum = 748;BA.debugLine="icoInformacion.TextSize = 40";
parent.mostCurrent._icoinformacion.setTextSize((float) (40));
 //BA.debugLineNum = 749;BA.debugLine="icoPerfil.TextSize = 40";
parent.mostCurrent._icoperfil.setTextSize((float) (40));
 //BA.debugLineNum = 750;BA.debugLine="btnInformacion.Text = \"Municipio\"";
parent.mostCurrent._btninformacion.setText(BA.ObjectToCharSequence("Municipio"));
 //BA.debugLineNum = 751;BA.debugLine="btnAnalizar.Text = \"Analizar\"";
parent.mostCurrent._btnanalizar.setText(BA.ObjectToCharSequence("Analizar"));
 //BA.debugLineNum = 752;BA.debugLine="btnPerfil.Text  = \"Perfil\"";
parent.mostCurrent._btnperfil.setText(BA.ObjectToCharSequence("Perfil"));
 //BA.debugLineNum = 754;BA.debugLine="icoMapa.SetTextColorAnimated(300, Colors.white)";
parent.mostCurrent._icomapa.SetTextColorAnimated((int) (300),anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 755;BA.debugLine="icoMapa.SetTextSizeAnimated(300,50)";
parent.mostCurrent._icomapa.SetTextSizeAnimated((int) (300),(float) (50));
 //BA.debugLineNum = 756;BA.debugLine="btnCirculo.SetLayoutAnimated(300, (icoMapa.Left";
parent.mostCurrent._btncirculo.SetLayoutAnimated((int) (300),(int) ((parent.mostCurrent._icomapa.getLeft()-((parent.mostCurrent._btncirculo.getWidth()-parent.mostCurrent._icomapa.getWidth())/(double)2))),parent.mostCurrent._btncirculo.getTop(),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (80)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (80)));
 //BA.debugLineNum = 757;BA.debugLine="btnMapa.Text = \"";
parent.mostCurrent._btnmapa.setText(BA.ObjectToCharSequence(""));
 if (true) break;

case 10:
//C
this.state = 15;
 //BA.debugLineNum = 759;BA.debugLine="icoAlerta.TextColor = Colors.ARGB(255,51,51,51)";
parent.mostCurrent._icoalerta.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (51),(int) (51),(int) (51)));
 //BA.debugLineNum = 760;BA.debugLine="icoAnalizar.TextColor = Colors.ARGB(255,51,51,5";
parent.mostCurrent._icoanalizar.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (51),(int) (51),(int) (51)));
 //BA.debugLineNum = 761;BA.debugLine="icoMapa.TextColor = Colors.ARGB(255,51,51,51)";
parent.mostCurrent._icomapa.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (51),(int) (51),(int) (51)));
 //BA.debugLineNum = 762;BA.debugLine="icoPerfil.TextColor = Colors.ARGB(255,51,51,51)";
parent.mostCurrent._icoperfil.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (51),(int) (51),(int) (51)));
 //BA.debugLineNum = 763;BA.debugLine="icoAlerta.TextSize = 40";
parent.mostCurrent._icoalerta.setTextSize((float) (40));
 //BA.debugLineNum = 764;BA.debugLine="icoAnalizar.TextSize = 40";
parent.mostCurrent._icoanalizar.setTextSize((float) (40));
 //BA.debugLineNum = 765;BA.debugLine="icoMapa.TextSize = 40";
parent.mostCurrent._icomapa.setTextSize((float) (40));
 //BA.debugLineNum = 766;BA.debugLine="icoPerfil.TextSize = 40";
parent.mostCurrent._icoperfil.setTextSize((float) (40));
 //BA.debugLineNum = 767;BA.debugLine="btnMapa.Text = \"Explorar\"";
parent.mostCurrent._btnmapa.setText(BA.ObjectToCharSequence("Explorar"));
 //BA.debugLineNum = 768;BA.debugLine="btnAnalizar.Text = \"Analizar\"";
parent.mostCurrent._btnanalizar.setText(BA.ObjectToCharSequence("Analizar"));
 //BA.debugLineNum = 769;BA.debugLine="btnPerfil.Text  = \"Perfil\"";
parent.mostCurrent._btnperfil.setText(BA.ObjectToCharSequence("Perfil"));
 //BA.debugLineNum = 771;BA.debugLine="icoInformacion.SetTextColorAnimated(300, Colors";
parent.mostCurrent._icoinformacion.SetTextColorAnimated((int) (300),anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 772;BA.debugLine="icoInformacion.SetTextSizeAnimated(300,50)";
parent.mostCurrent._icoinformacion.SetTextSizeAnimated((int) (300),(float) (50));
 //BA.debugLineNum = 773;BA.debugLine="btnCirculo.SetLayoutAnimated(300, (btnInformaci";
parent.mostCurrent._btncirculo.SetLayoutAnimated((int) (300),(int) ((parent.mostCurrent._btninformacion.getLeft()-((parent.mostCurrent._btncirculo.getWidth()-parent.mostCurrent._btninformacion.getWidth())/(double)2))),parent.mostCurrent._btncirculo.getTop(),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (80)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (80)));
 //BA.debugLineNum = 774;BA.debugLine="btnInformacion.Text = \"\"";
parent.mostCurrent._btninformacion.setText(BA.ObjectToCharSequence(""));
 if (true) break;

case 12:
//C
this.state = 15;
 //BA.debugLineNum = 776;BA.debugLine="icoMapa.TextColor = Colors.ARGB(255,51,51,51)";
parent.mostCurrent._icomapa.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (51),(int) (51),(int) (51)));
 //BA.debugLineNum = 777;BA.debugLine="icoInformacion.TextColor = Colors.ARGB(255,51,5";
parent.mostCurrent._icoinformacion.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (51),(int) (51),(int) (51)));
 //BA.debugLineNum = 778;BA.debugLine="icoPerfil.TextColor = Colors.ARGB(255,51,51,51)";
parent.mostCurrent._icoperfil.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (51),(int) (51),(int) (51)));
 //BA.debugLineNum = 779;BA.debugLine="icoMapa.TextSize = 40";
parent.mostCurrent._icomapa.setTextSize((float) (40));
 //BA.debugLineNum = 780;BA.debugLine="icoInformacion.TextSize = 40";
parent.mostCurrent._icoinformacion.setTextSize((float) (40));
 //BA.debugLineNum = 781;BA.debugLine="icoPerfil.TextSize = 40";
parent.mostCurrent._icoperfil.setTextSize((float) (40));
 //BA.debugLineNum = 782;BA.debugLine="btnMapa.Text = \"Explorar\"";
parent.mostCurrent._btnmapa.setText(BA.ObjectToCharSequence("Explorar"));
 //BA.debugLineNum = 783;BA.debugLine="btnInformacion.Text = \"Municipio\"";
parent.mostCurrent._btninformacion.setText(BA.ObjectToCharSequence("Municipio"));
 //BA.debugLineNum = 784;BA.debugLine="btnPerfil.Text  = \"Perfil\"";
parent.mostCurrent._btnperfil.setText(BA.ObjectToCharSequence("Perfil"));
 //BA.debugLineNum = 786;BA.debugLine="icoAnalizar.SetTextColorAnimated(300, Colors.wh";
parent.mostCurrent._icoanalizar.SetTextColorAnimated((int) (300),anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 787;BA.debugLine="icoAnalizar.SetTextSizeAnimated(300,50)";
parent.mostCurrent._icoanalizar.SetTextSizeAnimated((int) (300),(float) (50));
 //BA.debugLineNum = 788;BA.debugLine="btnCirculo.SetLayoutAnimated(300, (btnAnalizar.";
parent.mostCurrent._btncirculo.SetLayoutAnimated((int) (300),(int) ((parent.mostCurrent._btnanalizar.getLeft()-((parent.mostCurrent._btncirculo.getWidth()-parent.mostCurrent._btnanalizar.getWidth())/(double)2))),parent.mostCurrent._btncirculo.getTop(),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (80)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (80)));
 //BA.debugLineNum = 789;BA.debugLine="btnAnalizar.Text = \"\"";
parent.mostCurrent._btnanalizar.setText(BA.ObjectToCharSequence(""));
 if (true) break;

case 14:
//C
this.state = 15;
 //BA.debugLineNum = 791;BA.debugLine="icoMapa.TextColor = Colors.ARGB(255,51,51,51)";
parent.mostCurrent._icomapa.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (51),(int) (51),(int) (51)));
 //BA.debugLineNum = 792;BA.debugLine="icoAnalizar.TextColor = Colors.ARGB(255,51,51,5";
parent.mostCurrent._icoanalizar.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (51),(int) (51),(int) (51)));
 //BA.debugLineNum = 793;BA.debugLine="icoInformacion.TextColor = Colors.ARGB(255,51,5";
parent.mostCurrent._icoinformacion.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (51),(int) (51),(int) (51)));
 //BA.debugLineNum = 794;BA.debugLine="icoAlerta.TextColor = Colors.ARGB(255,51,51,51)";
parent.mostCurrent._icoalerta.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (51),(int) (51),(int) (51)));
 //BA.debugLineNum = 795;BA.debugLine="icoMapa.TextSize = 40";
parent.mostCurrent._icomapa.setTextSize((float) (40));
 //BA.debugLineNum = 796;BA.debugLine="icoAnalizar.TextSize = 40";
parent.mostCurrent._icoanalizar.setTextSize((float) (40));
 //BA.debugLineNum = 797;BA.debugLine="icoInformacion.TextSize = 40";
parent.mostCurrent._icoinformacion.setTextSize((float) (40));
 //BA.debugLineNum = 798;BA.debugLine="icoAlerta.TextSize = 40";
parent.mostCurrent._icoalerta.setTextSize((float) (40));
 //BA.debugLineNum = 799;BA.debugLine="btnMapa.Text = \"Explorar\"";
parent.mostCurrent._btnmapa.setText(BA.ObjectToCharSequence("Explorar"));
 //BA.debugLineNum = 800;BA.debugLine="btnInformacion.Text = \"Municipio\"";
parent.mostCurrent._btninformacion.setText(BA.ObjectToCharSequence("Municipio"));
 //BA.debugLineNum = 801;BA.debugLine="btnAnalizar.Text = \"Analizar\"";
parent.mostCurrent._btnanalizar.setText(BA.ObjectToCharSequence("Analizar"));
 //BA.debugLineNum = 803;BA.debugLine="icoPerfil.SetTextColorAnimated(300, Colors.whit";
parent.mostCurrent._icoperfil.SetTextColorAnimated((int) (300),anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 804;BA.debugLine="icoPerfil.SetTextSizeAnimated(300,50)";
parent.mostCurrent._icoperfil.SetTextSizeAnimated((int) (300),(float) (50));
 //BA.debugLineNum = 805;BA.debugLine="btnCirculo.SetLayoutAnimated(300, (icoPerfil.Le";
parent.mostCurrent._btncirculo.SetLayoutAnimated((int) (300),(int) ((parent.mostCurrent._icoperfil.getLeft()-((parent.mostCurrent._btncirculo.getWidth()-parent.mostCurrent._icoperfil.getWidth())/(double)2))),parent.mostCurrent._btncirculo.getTop(),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (80)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (80)));
 //BA.debugLineNum = 806;BA.debugLine="btnPerfil.Text = \"\"";
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
 //BA.debugLineNum = 809;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public static anywheresoftware.b4a.keywords.Common.ResumableSubWrapper  _calcular_puntos_ambientes() throws Exception{
ResumableSub_calcular_puntos_ambientes rsub = new ResumableSub_calcular_puntos_ambientes(null);
rsub.resume(processBA, null);
return (anywheresoftware.b4a.keywords.Common.ResumableSubWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.keywords.Common.ResumableSubWrapper(), rsub);
}
public static class ResumableSub_calcular_puntos_ambientes extends BA.ResumableSub {
public ResumableSub_calcular_puntos_ambientes(appear.pnud.preservamos.form_main parent) {
this.parent = parent;
}
appear.pnud.preservamos.form_main parent;
anywheresoftware.b4a.objects.collections.List _usermarkers = null;
int _i = 0;
anywheresoftware.b4a.objects.collections.Map _usermarker = null;
anywheresoftware.b4a.objects.collections.List _usermarkers_residuos = null;
anywheresoftware.b4a.objects.collections.List _usermarkers_hidro = null;
int step5;
int limit5;
int step26;
int limit26;
int step46;
int limit46;

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
 //BA.debugLineNum = 1959;BA.debugLine="Log(\"calculando list\")";
anywheresoftware.b4a.keywords.Common.LogImpl("04325377","calculando list",0);
 //BA.debugLineNum = 1961;BA.debugLine="Try";
if (true) break;

case 1:
//try
this.state = 18;
this.catchState = 17;
this.state = 3;
if (true) break;

case 3:
//C
this.state = 4;
this.catchState = 17;
 //BA.debugLineNum = 1963;BA.debugLine="Dim usermarkers As List";
_usermarkers = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 1964;BA.debugLine="usermarkers = Main.user_markers_list";
_usermarkers = parent.mostCurrent._main._user_markers_list /*anywheresoftware.b4a.objects.collections.List*/ ;
 //BA.debugLineNum = 1965;BA.debugLine="For i = 0 To usermarkers.Size - 1";
if (true) break;

case 4:
//for
this.state = 15;
step5 = 1;
limit5 = (int) (_usermarkers.getSize()-1);
_i = (int) (0) ;
this.state = 53;
if (true) break;

case 53:
//C
this.state = 15;
if ((step5 > 0 && _i <= limit5) || (step5 < 0 && _i >= limit5)) this.state = 6;
if (true) break;

case 54:
//C
this.state = 53;
_i = ((int)(0 + _i + step5)) ;
if (true) break;

case 6:
//C
this.state = 7;
 //BA.debugLineNum = 1966;BA.debugLine="Dim usermarker As Map";
_usermarker = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 1968;BA.debugLine="usermarker = usermarkers.Get(i)";
_usermarker = (anywheresoftware.b4a.objects.collections.Map) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.collections.Map(), (java.util.Map)(_usermarkers.Get(_i)));
 //BA.debugLineNum = 1969;BA.debugLine="If usermarker.Get(\"tiporio\") = \"Llanura\" Or use";
if (true) break;

case 7:
//if
this.state = 14;
if ((_usermarker.Get((Object)("tiporio"))).equals((Object)("Llanura")) || (_usermarker.Get((Object)("tiporio"))).equals((Object)("llanura"))) { 
this.state = 9;
}else if((_usermarker.Get((Object)("tiporio"))).equals((Object)("Laguna")) || (_usermarker.Get((Object)("tiporio"))).equals((Object)("laguna"))) { 
this.state = 11;
}else if((_usermarker.Get((Object)("tiporio"))).equals((Object)("Sierras")) || (_usermarker.Get((Object)("tiporio"))).equals((Object)("sierras"))) { 
this.state = 13;
}if (true) break;

case 9:
//C
this.state = 14;
 //BA.debugLineNum = 1970;BA.debugLine="Main.puntos_markers_llanura = Main.puntos_mark";
parent.mostCurrent._main._puntos_markers_llanura /*int*/  = (int) (parent.mostCurrent._main._puntos_markers_llanura /*int*/ +1);
 if (true) break;

case 11:
//C
this.state = 14;
 //BA.debugLineNum = 1972;BA.debugLine="Main.puntos_markers_laguna = Main.puntos_marke";
parent.mostCurrent._main._puntos_markers_laguna /*int*/  = (int) (parent.mostCurrent._main._puntos_markers_laguna /*int*/ +1);
 if (true) break;

case 13:
//C
this.state = 14;
 //BA.debugLineNum = 1974;BA.debugLine="Main.puntos_markers_sierras = Main.puntos_mark";
parent.mostCurrent._main._puntos_markers_sierras /*int*/  = (int) (parent.mostCurrent._main._puntos_markers_sierras /*int*/ +1);
 if (true) break;

case 14:
//C
this.state = 54;
;
 //BA.debugLineNum = 1977;BA.debugLine="Main.puntostotales = Main.puntostotales + 2";
parent.mostCurrent._main._puntostotales /*String*/  = BA.NumberToString((double)(Double.parseDouble(parent.mostCurrent._main._puntostotales /*String*/ ))+2);
 //BA.debugLineNum = 1978;BA.debugLine="Main.puntos_markers = Main.puntos_markers + 1";
parent.mostCurrent._main._puntos_markers /*int*/  = (int) (parent.mostCurrent._main._puntos_markers /*int*/ +1);
 if (true) break;
if (true) break;

case 15:
//C
this.state = 18;
;
 //BA.debugLineNum = 1980;BA.debugLine="Log(\"Main.puntos_markers\" & Main.puntos_markers)";
anywheresoftware.b4a.keywords.Common.LogImpl("04325398","Main.puntos_markers"+BA.NumberToString(parent.mostCurrent._main._puntos_markers /*int*/ ),0);
 if (true) break;

case 17:
//C
this.state = 18;
this.catchState = 0;
 //BA.debugLineNum = 1982;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("04325400",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA)),0);
 //BA.debugLineNum = 1983;BA.debugLine="Log(\"Error calculando_puntos_ambientes\")";
anywheresoftware.b4a.keywords.Common.LogImpl("04325401","Error calculando_puntos_ambientes",0);
 if (true) break;
if (true) break;
;
 //BA.debugLineNum = 1987;BA.debugLine="Try";

case 18:
//try
this.state = 35;
this.catchState = 0;
this.catchState = 34;
this.state = 20;
if (true) break;

case 20:
//C
this.state = 21;
this.catchState = 34;
 //BA.debugLineNum = 1989;BA.debugLine="Dim usermarkers_residuos As List";
_usermarkers_residuos = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 1990;BA.debugLine="usermarkers_residuos = Main.user_evals_residuos_";
_usermarkers_residuos = parent.mostCurrent._main._user_evals_residuos_list /*anywheresoftware.b4a.objects.collections.List*/ ;
 //BA.debugLineNum = 1991;BA.debugLine="For i = 0 To usermarkers_residuos.Size - 1";
if (true) break;

case 21:
//for
this.state = 32;
step26 = 1;
limit26 = (int) (_usermarkers_residuos.getSize()-1);
_i = (int) (0) ;
this.state = 55;
if (true) break;

case 55:
//C
this.state = 32;
if ((step26 > 0 && _i <= limit26) || (step26 < 0 && _i >= limit26)) this.state = 23;
if (true) break;

case 56:
//C
this.state = 55;
_i = ((int)(0 + _i + step26)) ;
if (true) break;

case 23:
//C
this.state = 24;
 //BA.debugLineNum = 1992;BA.debugLine="Dim usermarker As Map";
_usermarker = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 1993;BA.debugLine="usermarker = usermarkers_residuos.Get(i)";
_usermarker = (anywheresoftware.b4a.objects.collections.Map) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.collections.Map(), (java.util.Map)(_usermarkers_residuos.Get(_i)));
 //BA.debugLineNum = 1994;BA.debugLine="If usermarker.Get(\"tiporio\") = \"llanura\" Or use";
if (true) break;

case 24:
//if
this.state = 31;
if ((_usermarker.Get((Object)("tiporio"))).equals((Object)("llanura")) || (_usermarker.Get((Object)("tiporio"))).equals((Object)("llanura"))) { 
this.state = 26;
}else if((_usermarker.Get((Object)("tiporio"))).equals((Object)("laguna")) || (_usermarker.Get((Object)("tiporio"))).equals((Object)("laguna"))) { 
this.state = 28;
}else if((_usermarker.Get((Object)("tiporio"))).equals((Object)("sierras")) || (_usermarker.Get((Object)("tiporio"))).equals((Object)("sierras"))) { 
this.state = 30;
}if (true) break;

case 26:
//C
this.state = 31;
 //BA.debugLineNum = 1995;BA.debugLine="Main.puntos_evals_residuos_llanura = Main.punt";
parent.mostCurrent._main._puntos_evals_residuos_llanura /*int*/  = (int) (parent.mostCurrent._main._puntos_evals_residuos_llanura /*int*/ +1);
 if (true) break;

case 28:
//C
this.state = 31;
 //BA.debugLineNum = 1997;BA.debugLine="Main.puntos_evals_residuos_laguna = Main.punto";
parent.mostCurrent._main._puntos_evals_residuos_laguna /*int*/  = (int) (parent.mostCurrent._main._puntos_evals_residuos_laguna /*int*/ +1);
 if (true) break;

case 30:
//C
this.state = 31;
 //BA.debugLineNum = 1999;BA.debugLine="Main.puntos_evals_residuos_sierras = Main.punt";
parent.mostCurrent._main._puntos_evals_residuos_sierras /*int*/  = (int) (parent.mostCurrent._main._puntos_evals_residuos_sierras /*int*/ +1);
 if (true) break;

case 31:
//C
this.state = 56;
;
 //BA.debugLineNum = 2002;BA.debugLine="Main.puntostotales = Main.puntostotales + 1";
parent.mostCurrent._main._puntostotales /*String*/  = BA.NumberToString((double)(Double.parseDouble(parent.mostCurrent._main._puntostotales /*String*/ ))+1);
 //BA.debugLineNum = 2003;BA.debugLine="Main.puntos_evals_residuos = Main.puntos_evals_";
parent.mostCurrent._main._puntos_evals_residuos /*int*/  = (int) (parent.mostCurrent._main._puntos_evals_residuos /*int*/ +1);
 if (true) break;
if (true) break;

case 32:
//C
this.state = 35;
;
 if (true) break;

case 34:
//C
this.state = 35;
this.catchState = 0;
 //BA.debugLineNum = 2006;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("04325424",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA)),0);
 //BA.debugLineNum = 2007;BA.debugLine="Log(\"Error calculando_puntos_ambientes\")";
anywheresoftware.b4a.keywords.Common.LogImpl("04325425","Error calculando_puntos_ambientes",0);
 if (true) break;
if (true) break;
;
 //BA.debugLineNum = 2011;BA.debugLine="Try";

case 35:
//try
this.state = 52;
this.catchState = 0;
this.catchState = 51;
this.state = 37;
if (true) break;

case 37:
//C
this.state = 38;
this.catchState = 51;
 //BA.debugLineNum = 2013;BA.debugLine="Dim usermarkers_hidro As List";
_usermarkers_hidro = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 2014;BA.debugLine="usermarkers_hidro = Main.user_evals_hidro_list";
_usermarkers_hidro = parent.mostCurrent._main._user_evals_hidro_list /*anywheresoftware.b4a.objects.collections.List*/ ;
 //BA.debugLineNum = 2015;BA.debugLine="For i = 0 To usermarkers_hidro.Size - 1";
if (true) break;

case 38:
//for
this.state = 49;
step46 = 1;
limit46 = (int) (_usermarkers_hidro.getSize()-1);
_i = (int) (0) ;
this.state = 57;
if (true) break;

case 57:
//C
this.state = 49;
if ((step46 > 0 && _i <= limit46) || (step46 < 0 && _i >= limit46)) this.state = 40;
if (true) break;

case 58:
//C
this.state = 57;
_i = ((int)(0 + _i + step46)) ;
if (true) break;

case 40:
//C
this.state = 41;
 //BA.debugLineNum = 2016;BA.debugLine="Dim usermarker As Map";
_usermarker = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 2017;BA.debugLine="usermarker = usermarkers_hidro.Get(i)";
_usermarker = (anywheresoftware.b4a.objects.collections.Map) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.collections.Map(), (java.util.Map)(_usermarkers_hidro.Get(_i)));
 //BA.debugLineNum = 2018;BA.debugLine="If usermarker.Get(\"tiporio\") = \"llanura\" Or use";
if (true) break;

case 41:
//if
this.state = 48;
if ((_usermarker.Get((Object)("tiporio"))).equals((Object)("llanura")) || (_usermarker.Get((Object)("tiporio"))).equals((Object)("llanura"))) { 
this.state = 43;
}else if((_usermarker.Get((Object)("tiporio"))).equals((Object)("laguna")) || (_usermarker.Get((Object)("tiporio"))).equals((Object)("laguna"))) { 
this.state = 45;
}else if((_usermarker.Get((Object)("tiporio"))).equals((Object)("sierras")) || (_usermarker.Get((Object)("tiporio"))).equals((Object)("sierras"))) { 
this.state = 47;
}if (true) break;

case 43:
//C
this.state = 48;
 //BA.debugLineNum = 2019;BA.debugLine="Main.puntos_evals_hidro_llanura = Main.puntos_";
parent.mostCurrent._main._puntos_evals_hidro_llanura /*int*/  = (int) (parent.mostCurrent._main._puntos_evals_hidro_llanura /*int*/ +1);
 if (true) break;

case 45:
//C
this.state = 48;
 //BA.debugLineNum = 2021;BA.debugLine="Main.puntos_evals_hidro_laguna = Main.puntos_e";
parent.mostCurrent._main._puntos_evals_hidro_laguna /*int*/  = (int) (parent.mostCurrent._main._puntos_evals_hidro_laguna /*int*/ +1);
 if (true) break;

case 47:
//C
this.state = 48;
 //BA.debugLineNum = 2023;BA.debugLine="Main.puntos_evals_hidro_sierras = Main.puntos_";
parent.mostCurrent._main._puntos_evals_hidro_sierras /*int*/  = (int) (parent.mostCurrent._main._puntos_evals_hidro_sierras /*int*/ +1);
 if (true) break;

case 48:
//C
this.state = 58;
;
 //BA.debugLineNum = 2026;BA.debugLine="Main.puntostotales = Main.puntostotales + 1";
parent.mostCurrent._main._puntostotales /*String*/  = BA.NumberToString((double)(Double.parseDouble(parent.mostCurrent._main._puntostotales /*String*/ ))+1);
 //BA.debugLineNum = 2027;BA.debugLine="Main.puntos_evals_hidro = Main.puntos_evals_hid";
parent.mostCurrent._main._puntos_evals_hidro /*int*/  = (int) (parent.mostCurrent._main._puntos_evals_hidro /*int*/ +1);
 if (true) break;
if (true) break;

case 49:
//C
this.state = 52;
;
 if (true) break;

case 51:
//C
this.state = 52;
this.catchState = 0;
 //BA.debugLineNum = 2030;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("04325448",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA)),0);
 //BA.debugLineNum = 2031;BA.debugLine="Log(\"Error calculando_puntos_ambientes\")";
anywheresoftware.b4a.keywords.Common.LogImpl("04325449","Error calculando_puntos_ambientes",0);
 if (true) break;
if (true) break;

case 52:
//C
this.state = -1;
this.catchState = 0;
;
 //BA.debugLineNum = 2034;BA.debugLine="Return True";
if (true) {
anywheresoftware.b4a.keywords.Common.ReturnFromResumableSub(this,(Object)(anywheresoftware.b4a.keywords.Common.True));return;};
 //BA.debugLineNum = 2036;BA.debugLine="End Sub";
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
public static anywheresoftware.b4a.keywords.Common.ResumableSubWrapper  _cargarmedallas() throws Exception{
ResumableSub_cargarMedallas rsub = new ResumableSub_cargarMedallas(null);
rsub.resume(processBA, null);
return (anywheresoftware.b4a.keywords.Common.ResumableSubWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.keywords.Common.ResumableSubWrapper(), rsub);
}
public static class ResumableSub_cargarMedallas extends BA.ResumableSub {
public ResumableSub_cargarMedallas(appear.pnud.preservamos.form_main parent) {
this.parent = parent;
}
appear.pnud.preservamos.form_main parent;
int _analisishechos = 0;

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
 //BA.debugLineNum = 2066;BA.debugLine="lstAchievements.Clear";
parent.mostCurrent._lstachievements.Clear();
 //BA.debugLineNum = 2067;BA.debugLine="lstAchievements.TwoLinesAndBitmap.ItemHeight = 13";
parent.mostCurrent._lstachievements.getTwoLinesAndBitmap().setItemHeight(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (130)));
 //BA.debugLineNum = 2068;BA.debugLine="lstAchievements.TwoLinesAndBitmap.ImageView.Left";
parent.mostCurrent._lstachievements.getTwoLinesAndBitmap().ImageView.setLeft(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (0)));
 //BA.debugLineNum = 2069;BA.debugLine="lstAchievements.TwoLinesAndBitmap.ImageView.Gravi";
parent.mostCurrent._lstachievements.getTwoLinesAndBitmap().ImageView.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.FILL);
 //BA.debugLineNum = 2070;BA.debugLine="lstAchievements.TwoLinesAndBitmap.ImageView.Width";
parent.mostCurrent._lstachievements.getTwoLinesAndBitmap().ImageView.setWidth(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (130)));
 //BA.debugLineNum = 2071;BA.debugLine="lstAchievements.TwoLinesAndBitmap.ImageView.Heigh";
parent.mostCurrent._lstachievements.getTwoLinesAndBitmap().ImageView.setHeight(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (130)));
 //BA.debugLineNum = 2072;BA.debugLine="lstAchievements.TwoLinesAndBitmap.Label.Visible =";
parent.mostCurrent._lstachievements.getTwoLinesAndBitmap().Label.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 2073;BA.debugLine="lstAchievements.TwoLinesAndBitmap.Secondlabel.Vis";
parent.mostCurrent._lstachievements.getTwoLinesAndBitmap().SecondLabel.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 2076;BA.debugLine="Dim analisishechos As Int";
_analisishechos = 0;
 //BA.debugLineNum = 2077;BA.debugLine="analisishechos = Main.puntos_markers + Main.punto";
_analisishechos = (int) (parent.mostCurrent._main._puntos_markers /*int*/ +parent.mostCurrent._main._puntos_evals_residuos /*int*/ +parent.mostCurrent._main._puntos_evals_hidro /*int*/ );
 //BA.debugLineNum = 2079;BA.debugLine="If analisishechos > 1 Then";
if (true) break;

case 1:
//if
this.state = 4;
if (_analisishechos>1) { 
this.state = 3;
}if (true) break;

case 3:
//C
this.state = 4;
 //BA.debugLineNum = 2080;BA.debugLine="lstAchievements.AddTwoLinesAndBitmap2(\"Novato\",";
parent.mostCurrent._lstachievements.AddTwoLinesAndBitmap2(BA.ObjectToCharSequence("Novato"),BA.ObjectToCharSequence("Un análisis ambiental"),(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),parent.mostCurrent._main._lang /*String*/ +"-novato.png").getObject()),(Object)("Novato"));
 if (true) break;
;
 //BA.debugLineNum = 2082;BA.debugLine="If analisishechos >= 3 Then";

case 4:
//if
this.state = 7;
if (_analisishechos>=3) { 
this.state = 6;
}if (true) break;

case 6:
//C
this.state = 7;
 //BA.debugLineNum = 2083;BA.debugLine="lstAchievements.AddTwoLinesAndBitmap2(\"Aprendiz\"";
parent.mostCurrent._lstachievements.AddTwoLinesAndBitmap2(BA.ObjectToCharSequence("Aprendiz"),BA.ObjectToCharSequence("Tres análisis ambientales"),(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),parent.mostCurrent._main._lang /*String*/ +"-aprendiz.png").getObject()),(Object)("Aprendiz"));
 if (true) break;
;
 //BA.debugLineNum = 2085;BA.debugLine="If analisishechos >= 5 Then";

case 7:
//if
this.state = 10;
if (_analisishechos>=5) { 
this.state = 9;
}if (true) break;

case 9:
//C
this.state = 10;
 //BA.debugLineNum = 2086;BA.debugLine="lstAchievements.AddTwoLinesAndBitmap2(\"Aventurer";
parent.mostCurrent._lstachievements.AddTwoLinesAndBitmap2(BA.ObjectToCharSequence("Aventurero"),BA.ObjectToCharSequence("Cinco análisis ambientales"),(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),parent.mostCurrent._main._lang /*String*/ +"-aventurero.png").getObject()),(Object)("Aventurero"));
 if (true) break;
;
 //BA.debugLineNum = 2088;BA.debugLine="If analisishechos >= 15 Then";

case 10:
//if
this.state = 13;
if (_analisishechos>=15) { 
this.state = 12;
}if (true) break;

case 12:
//C
this.state = 13;
 //BA.debugLineNum = 2089;BA.debugLine="lstAchievements.AddTwoLinesAndBitmap2(\"Explorado";
parent.mostCurrent._lstachievements.AddTwoLinesAndBitmap2(BA.ObjectToCharSequence("Explorador"),BA.ObjectToCharSequence("Quince análisis ambientales"),(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),parent.mostCurrent._main._lang /*String*/ +"-explorador.png").getObject()),(Object)("Explorador"));
 if (true) break;
;
 //BA.debugLineNum = 2091;BA.debugLine="If analisishechos >= 50 Then";

case 13:
//if
this.state = 16;
if (_analisishechos>=50) { 
this.state = 15;
}if (true) break;

case 15:
//C
this.state = 16;
 //BA.debugLineNum = 2092;BA.debugLine="lstAchievements.AddTwoLinesAndBitmap2(\"Legendari";
parent.mostCurrent._lstachievements.AddTwoLinesAndBitmap2(BA.ObjectToCharSequence("Legendario"),BA.ObjectToCharSequence("Treinta análisis ambientales!"),(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),parent.mostCurrent._main._lang /*String*/ +"-legendario.png").getObject()),(Object)("Legendario"));
 if (true) break;
;
 //BA.debugLineNum = 2095;BA.debugLine="If Main.puntos_evals_residuos >= 1 Then";

case 16:
//if
this.state = 19;
if (parent.mostCurrent._main._puntos_evals_residuos /*int*/ >=1) { 
this.state = 18;
}if (true) break;

case 18:
//C
this.state = 19;
 //BA.debugLineNum = 2096;BA.debugLine="lstAchievements.AddTwoLinesAndBitmap2(\"Residuos";
parent.mostCurrent._lstachievements.AddTwoLinesAndBitmap2(BA.ObjectToCharSequence("Residuos 1"),BA.ObjectToCharSequence("Completaste tu primer análisis de residuos"),(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),parent.mostCurrent._main._lang /*String*/ +"-residuos-1.png").getObject()),(Object)("residuos-1"));
 if (true) break;
;
 //BA.debugLineNum = 2098;BA.debugLine="If Main.puntos_evals_residuos >= 3 Then";

case 19:
//if
this.state = 22;
if (parent.mostCurrent._main._puntos_evals_residuos /*int*/ >=3) { 
this.state = 21;
}if (true) break;

case 21:
//C
this.state = 22;
 //BA.debugLineNum = 2099;BA.debugLine="lstAchievements.AddTwoLinesAndBitmap2(\"Residuos";
parent.mostCurrent._lstachievements.AddTwoLinesAndBitmap2(BA.ObjectToCharSequence("Residuos 2"),BA.ObjectToCharSequence("Completaste tres análisis de residuos"),(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),parent.mostCurrent._main._lang /*String*/ +"-residuos-2.png").getObject()),(Object)("residuos-2"));
 if (true) break;
;
 //BA.debugLineNum = 2101;BA.debugLine="If Main.puntos_evals_residuos >= 10 Then";

case 22:
//if
this.state = 25;
if (parent.mostCurrent._main._puntos_evals_residuos /*int*/ >=10) { 
this.state = 24;
}if (true) break;

case 24:
//C
this.state = 25;
 //BA.debugLineNum = 2102;BA.debugLine="lstAchievements.AddTwoLinesAndBitmap2(\"Residuos";
parent.mostCurrent._lstachievements.AddTwoLinesAndBitmap2(BA.ObjectToCharSequence("Residuos 3"),BA.ObjectToCharSequence("Completaste diez análisis de residuos!"),(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),parent.mostCurrent._main._lang /*String*/ +"-residuos-3.png").getObject()),(Object)("residuos-3"));
 if (true) break;
;
 //BA.debugLineNum = 2105;BA.debugLine="If Main.puntos_evals_hidro >= 1 Then";

case 25:
//if
this.state = 28;
if (parent.mostCurrent._main._puntos_evals_hidro /*int*/ >=1) { 
this.state = 27;
}if (true) break;

case 27:
//C
this.state = 28;
 //BA.debugLineNum = 2106;BA.debugLine="lstAchievements.AddTwoLinesAndBitmap2(\"Hidro 1\",";
parent.mostCurrent._lstachievements.AddTwoLinesAndBitmap2(BA.ObjectToCharSequence("Hidro 1"),BA.ObjectToCharSequence("Completaste tu primer análisis de hidro"),(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),parent.mostCurrent._main._lang /*String*/ +"-hidro-1.png").getObject()),(Object)("hidro-1"));
 if (true) break;
;
 //BA.debugLineNum = 2108;BA.debugLine="If Main.puntos_evals_hidro >= 3 Then";

case 28:
//if
this.state = 31;
if (parent.mostCurrent._main._puntos_evals_hidro /*int*/ >=3) { 
this.state = 30;
}if (true) break;

case 30:
//C
this.state = 31;
 //BA.debugLineNum = 2109;BA.debugLine="lstAchievements.AddTwoLinesAndBitmap2(\"Hidro 2\",";
parent.mostCurrent._lstachievements.AddTwoLinesAndBitmap2(BA.ObjectToCharSequence("Hidro 2"),BA.ObjectToCharSequence("Completaste tres análisis de hidro"),(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),parent.mostCurrent._main._lang /*String*/ +"-hidro-2.png").getObject()),(Object)("hidro-2"));
 if (true) break;
;
 //BA.debugLineNum = 2111;BA.debugLine="If Main.puntos_evals_hidro >= 10 Then";

case 31:
//if
this.state = 34;
if (parent.mostCurrent._main._puntos_evals_hidro /*int*/ >=10) { 
this.state = 33;
}if (true) break;

case 33:
//C
this.state = 34;
 //BA.debugLineNum = 2112;BA.debugLine="lstAchievements.AddTwoLinesAndBitmap2(\"Hidro 3\",";
parent.mostCurrent._lstachievements.AddTwoLinesAndBitmap2(BA.ObjectToCharSequence("Hidro 3"),BA.ObjectToCharSequence("Completaste diez análisis de hidro!"),(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),parent.mostCurrent._main._lang /*String*/ +"-hidro-3.png").getObject()),(Object)("hidro-3"));
 if (true) break;
;
 //BA.debugLineNum = 2116;BA.debugLine="If Main.puntos_markers_llanura >= 1 Then";

case 34:
//if
this.state = 37;
if (parent.mostCurrent._main._puntos_markers_llanura /*int*/ >=1) { 
this.state = 36;
}if (true) break;

case 36:
//C
this.state = 37;
 //BA.debugLineNum = 2117;BA.debugLine="lstAchievements.AddTwoLinesAndBitmap2(\"Llanura\",";
parent.mostCurrent._lstachievements.AddTwoLinesAndBitmap2(BA.ObjectToCharSequence("Llanura"),BA.ObjectToCharSequence("Completaste tu primera evaluación de un río de llanura"),(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),parent.mostCurrent._main._lang /*String*/ +"-llanura.png").getObject()),(Object)("llanura"));
 if (true) break;
;
 //BA.debugLineNum = 2119;BA.debugLine="If Main.puntos_markers_sierras >= 1 Then";

case 37:
//if
this.state = 40;
if (parent.mostCurrent._main._puntos_markers_sierras /*int*/ >=1) { 
this.state = 39;
}if (true) break;

case 39:
//C
this.state = 40;
 //BA.debugLineNum = 2120;BA.debugLine="lstAchievements.AddTwoLinesAndBitmap2(\"Montaña\",";
parent.mostCurrent._lstachievements.AddTwoLinesAndBitmap2(BA.ObjectToCharSequence("Montaña"),BA.ObjectToCharSequence("Completaste tu primera evaluación de un río de sierras de Córdoba"),(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),parent.mostCurrent._main._lang /*String*/ +"-montana.png").getObject()),(Object)("montana"));
 if (true) break;
;
 //BA.debugLineNum = 2122;BA.debugLine="If Main.puntos_markers_laguna >= 1 Then";

case 40:
//if
this.state = 43;
if (parent.mostCurrent._main._puntos_markers_laguna /*int*/ >=1) { 
this.state = 42;
}if (true) break;

case 42:
//C
this.state = 43;
 //BA.debugLineNum = 2123;BA.debugLine="lstAchievements.AddTwoLinesAndBitmap2(\"Laguna\",";
parent.mostCurrent._lstachievements.AddTwoLinesAndBitmap2(BA.ObjectToCharSequence("Laguna"),BA.ObjectToCharSequence("Completaste tu primera evaluación de una laguna"),(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),parent.mostCurrent._main._lang /*String*/ +"-laguna.png").getObject()),(Object)("laguna"));
 if (true) break;
;
 //BA.debugLineNum = 2125;BA.debugLine="If Main.puntos_markers_laguna >= 1 And Main.numes";

case 43:
//if
this.state = 46;
if (parent.mostCurrent._main._puntos_markers_laguna /*int*/ >=1 && (double)(Double.parseDouble(parent.mostCurrent._main._numestuario /*String*/ ))>=1 && parent.mostCurrent._main._puntos_markers_sierras /*int*/ >=1 && parent.mostCurrent._main._puntos_markers_llanura /*int*/ >=1) { 
this.state = 45;
}if (true) break;

case 45:
//C
this.state = 46;
 //BA.debugLineNum = 2126;BA.debugLine="lstAchievements.AddTwoLinesAndBitmap2(\"Maestro d";
parent.mostCurrent._lstachievements.AddTwoLinesAndBitmap2(BA.ObjectToCharSequence("Maestro de todos los ambientes"),BA.ObjectToCharSequence("Completaste una evaluación de cada ambiente"),(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),parent.mostCurrent._main._lang /*String*/ +"-maestrodeambientes.png").getObject()),(Object)("maestrodeambientes"));
 if (true) break;

case 46:
//C
this.state = -1;
;
 //BA.debugLineNum = 2128;BA.debugLine="Return True";
if (true) {
anywheresoftware.b4a.keywords.Common.ReturnFromResumableSub(this,(Object)(anywheresoftware.b4a.keywords.Common.True));return;};
 //BA.debugLineNum = 2130;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public static anywheresoftware.b4a.keywords.Common.ResumableSubWrapper  _cargarnivel() throws Exception{
ResumableSub_cargarNivel rsub = new ResumableSub_cargarNivel(null);
rsub.resume(processBA, null);
return (anywheresoftware.b4a.keywords.Common.ResumableSubWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.keywords.Common.ResumableSubWrapper(), rsub);
}
public static class ResumableSub_cargarNivel extends BA.ResumableSub {
public ResumableSub_cargarNivel(appear.pnud.preservamos.form_main parent) {
this.parent = parent;
}
appear.pnud.preservamos.form_main parent;
anywheresoftware.b4a.objects.drawable.ColorDrawable _gd = null;
anywheresoftware.b4a.objects.drawable.ColorDrawable _cd = null;
double _nivelfull = 0;
int _nivela = 0;
double _resto = 0;
int _nivelsiguiente = 0;

@Override
public void resume(BA ba, Object[] result) throws Exception{

    while (true) {
        switch (state) {
            case -1:
{
anywheresoftware.b4a.keywords.Common.ReturnFromResumableSub(this,null);return;}
case 0:
//C
this.state = -1;
 //BA.debugLineNum = 2040;BA.debugLine="Dim gd As ColorDrawable";
_gd = new anywheresoftware.b4a.objects.drawable.ColorDrawable();
 //BA.debugLineNum = 2041;BA.debugLine="Dim cd As ColorDrawable";
_cd = new anywheresoftware.b4a.objects.drawable.ColorDrawable();
 //BA.debugLineNum = 2042;BA.debugLine="gd.Initialize(Colors.DarkGray,10dip)";
_gd.Initialize(anywheresoftware.b4a.keywords.Common.Colors.DarkGray,anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10)));
 //BA.debugLineNum = 2043;BA.debugLine="cd.Initialize(Colors.LightGray, 10dip)";
_cd.Initialize(anywheresoftware.b4a.keywords.Common.Colors.LightGray,anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10)));
 //BA.debugLineNum = 2044;BA.debugLine="utilidades.SetProgressDrawable(pgbLevel, gd, cd)";
parent.mostCurrent._utilidades._setprogressdrawable /*String*/ (mostCurrent.activityBA,parent.mostCurrent._pgblevel,(Object)(_gd.getObject()),(Object)(_cd.getObject()));
 //BA.debugLineNum = 2047;BA.debugLine="Dim nivelfull As Double";
_nivelfull = 0;
 //BA.debugLineNum = 2048;BA.debugLine="Dim nivela As Int";
_nivela = 0;
 //BA.debugLineNum = 2049;BA.debugLine="Dim resto As Double";
_resto = 0;
 //BA.debugLineNum = 2050;BA.debugLine="Dim nivelsiguiente As Int";
_nivelsiguiente = 0;
 //BA.debugLineNum = 2052;BA.debugLine="nivelfull = 0.5 + (0.5*Sqrt(1+8*Main.puntostotale";
_nivelfull = 0.5+(0.5*anywheresoftware.b4a.keywords.Common.Sqrt(1+8*(double)(Double.parseDouble(parent.mostCurrent._main._puntostotales /*String*/ ))));
 //BA.debugLineNum = 2053;BA.debugLine="nivela = Floor(nivelfull)";
_nivela = (int) (anywheresoftware.b4a.keywords.Common.Floor(_nivelfull));
 //BA.debugLineNum = 2054;BA.debugLine="nivelsiguiente = nivela + 1";
_nivelsiguiente = (int) (_nivela+1);
 //BA.debugLineNum = 2055;BA.debugLine="resto = Round2(Abs(nivelsiguiente - nivelfull) *";
_resto = anywheresoftware.b4a.keywords.Common.Round2(anywheresoftware.b4a.keywords.Common.Abs(_nivelsiguiente-_nivelfull)*100,(int) (0));
 //BA.debugLineNum = 2056;BA.debugLine="pgbLevel.Progress = 100 - resto";
parent.mostCurrent._pgblevel.setProgress((int) (100-_resto));
 //BA.debugLineNum = 2057;BA.debugLine="lblNivel.Text = nivela";
parent.mostCurrent._lblnivel.setText(BA.ObjectToCharSequence(_nivela));
 //BA.debugLineNum = 2058;BA.debugLine="lblPerfil_puntos_totales.Text = \"Nivel \" & nivela";
parent.mostCurrent._lblperfil_puntos_totales.setText(BA.ObjectToCharSequence("Nivel "+BA.NumberToString(_nivela)));
 //BA.debugLineNum = 2059;BA.debugLine="nivel = nivela";
parent.mostCurrent._nivel = BA.NumberToString(_nivela);
 //BA.debugLineNum = 2061;BA.debugLine="Return True";
if (true) {
anywheresoftware.b4a.keywords.Common.ReturnFromResumableSub(this,(Object)(anywheresoftware.b4a.keywords.Common.True));return;};
 //BA.debugLineNum = 2062;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
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
 //BA.debugLineNum = 416;BA.debugLine="Msgbox2Async(\"Cerrar PreserVamos?\", \"SALIR\", \"Si\"";
anywheresoftware.b4a.keywords.Common.Msgbox2Async(BA.ObjectToCharSequence("Cerrar PreserVamos?"),BA.ObjectToCharSequence("SALIR"),"Si","","No",(anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper(), (android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null)),processBA,anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 417;BA.debugLine="Wait For Msgbox_Result (Result As Int)";
anywheresoftware.b4a.keywords.Common.WaitFor("msgbox_result", processBA, this, null);
this.state = 5;
return;
case 5:
//C
this.state = 1;
_result = (Integer) result[0];
;
 //BA.debugLineNum = 418;BA.debugLine="If Result = DialogResponse.POSITIVE Then";
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
 //BA.debugLineNum = 419;BA.debugLine="Activity.RemoveAllViews";
parent.mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 420;BA.debugLine="Activity.finish";
parent.mostCurrent._activity.Finish();
 //BA.debugLineNum = 421;BA.debugLine="ExitApplication";
anywheresoftware.b4a.keywords.Common.ExitApplication();
 if (true) break;

case 4:
//C
this.state = -1;
;
 //BA.debugLineNum = 423;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public static anywheresoftware.b4a.keywords.Common.ResumableSubWrapper  _crearmenu() throws Exception{
ResumableSub_CrearMenu rsub = new ResumableSub_CrearMenu(null);
rsub.resume(processBA, null);
return (anywheresoftware.b4a.keywords.Common.ResumableSubWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.keywords.Common.ResumableSubWrapper(), rsub);
}
public static class ResumableSub_CrearMenu extends BA.ResumableSub {
public ResumableSub_CrearMenu(appear.pnud.preservamos.form_main parent) {
this.parent = parent;
}
appear.pnud.preservamos.form_main parent;

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
 //BA.debugLineNum = 433;BA.debugLine="Drawer.Initialize(Me, \"Drawer\", Activity, 300dip)";
parent.mostCurrent._drawer._initialize /*String*/ (mostCurrent.activityBA,form_main.getObject(),"Drawer",(anywheresoftware.b4a.objects.B4XViewWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.B4XViewWrapper(), (java.lang.Object)(parent.mostCurrent._activity.getObject())),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (300)));
 //BA.debugLineNum = 434;BA.debugLine="Drawer.CenterPanel.LoadLayout(\"layMain\")";
parent.mostCurrent._drawer._getcenterpanel /*anywheresoftware.b4a.objects.B4XViewWrapper*/ ().LoadLayout("layMain",mostCurrent.activityBA);
 //BA.debugLineNum = 435;BA.debugLine="Drawer.LeftPanel.LoadLayout(\"frmSideNav\")";
parent.mostCurrent._drawer._getleftpanel /*anywheresoftware.b4a.objects.B4XViewWrapper*/ ().LoadLayout("frmSideNav",mostCurrent.activityBA);
 //BA.debugLineNum = 437;BA.debugLine="If Main.username = \"guest\" Or Main.username = \"No";
if (true) break;

case 1:
//if
this.state = 6;
if ((parent.mostCurrent._main._username /*String*/ ).equals("guest") || (parent.mostCurrent._main._username /*String*/ ).equals("None") || (parent.mostCurrent._main._username /*String*/ ).equals("")) { 
this.state = 3;
}else {
this.state = 5;
}if (true) break;

case 3:
//C
this.state = 6;
 //BA.debugLineNum = 438;BA.debugLine="btnCerrarSesion.Text = \"Iniciar sesión\"";
parent.mostCurrent._btncerrarsesion.setText(BA.ObjectToCharSequence("Iniciar sesión"));
 //BA.debugLineNum = 439;BA.debugLine="lblUserName.Visible = False";
parent.mostCurrent._lblusername.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 440;BA.debugLine="lblRegistrate.Visible = True";
parent.mostCurrent._lblregistrate.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 441;BA.debugLine="btnEditUser.Visible = False";
parent.mostCurrent._btnedituser.setVisible(anywheresoftware.b4a.keywords.Common.False);
 if (true) break;

case 5:
//C
this.state = 6;
 //BA.debugLineNum = 443;BA.debugLine="lblUserName.Text = Main.username";
parent.mostCurrent._lblusername.setText(BA.ObjectToCharSequence(parent.mostCurrent._main._username /*String*/ ));
 //BA.debugLineNum = 444;BA.debugLine="btnCerrarSesion.Text = \"Cerrar sesión\"";
parent.mostCurrent._btncerrarsesion.setText(BA.ObjectToCharSequence("Cerrar sesión"));
 //BA.debugLineNum = 445;BA.debugLine="lblRegistrate.Visible = False";
parent.mostCurrent._lblregistrate.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 446;BA.debugLine="btnEditUser.Visible = True";
parent.mostCurrent._btnedituser.setVisible(anywheresoftware.b4a.keywords.Common.True);
 if (true) break;

case 6:
//C
this.state = -1;
;
 //BA.debugLineNum = 449;BA.debugLine="Return True";
if (true) {
anywheresoftware.b4a.keywords.Common.ReturnFromResumableSub(this,(Object)(anywheresoftware.b4a.keywords.Common.True));return;};
 //BA.debugLineNum = 450;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public static String  _fondogris_click() throws Exception{
 //BA.debugLineNum = 1474;BA.debugLine="Private Sub fondogris_Click";
 //BA.debugLineNum = 1475;BA.debugLine="panelComoFunciona.RemoveView";
mostCurrent._panelcomofunciona.RemoveView();
 //BA.debugLineNum = 1476;BA.debugLine="fondogris.RemoveView";
mostCurrent._fondogris.RemoveView();
 //BA.debugLineNum = 1477;BA.debugLine="End Sub";
return "";
}
public static String  _fondogris_share_click() throws Exception{
 //BA.debugLineNum = 2386;BA.debugLine="Sub fondogris_share_Click";
 //BA.debugLineNum = 2387;BA.debugLine="pnlShare.RemoveView";
mostCurrent._pnlshare.RemoveView();
 //BA.debugLineNum = 2388;BA.debugLine="fondogris_share.RemoveView";
mostCurrent._fondogris_share.RemoveView();
 //BA.debugLineNum = 2389;BA.debugLine="End Sub";
return "";
}
public static String  _georef_geocodedone(uk.co.martinpearman.b4a.location.AddressWrapper[] _results,Object _tag) throws Exception{
uk.co.martinpearman.b4a.location.AddressWrapper _address1 = null;
int _i = 0;
String _localidad_new = "";
 //BA.debugLineNum = 2751;BA.debugLine="Sub georef_GeocodeDone(Results() As Address, Tag A";
 //BA.debugLineNum = 2752;BA.debugLine="If Results.Length>0 Then";
if (_results.length>0) { 
 //BA.debugLineNum = 2753;BA.debugLine="Dim Address1 As Address";
_address1 = new uk.co.martinpearman.b4a.location.AddressWrapper();
 //BA.debugLineNum = 2754;BA.debugLine="Dim i As Int = 0";
_i = (int) (0);
 //BA.debugLineNum = 2755;BA.debugLine="Address1=Results(i)";
_address1 = _results[_i];
 //BA.debugLineNum = 2758;BA.debugLine="Log(Address1)";
anywheresoftware.b4a.keywords.Common.LogImpl("05373959",BA.ObjectToString(_address1),0);
 //BA.debugLineNum = 2759;BA.debugLine="Try";
try { //BA.debugLineNum = 2760;BA.debugLine="If Address1.SubAdminArea <> \"\" And Address1.Sub";
if ((_address1.getSubAdminArea()).equals("") == false && _address1.getSubAdminArea()!= null) { 
 //BA.debugLineNum = 2761;BA.debugLine="Log(\"Subadminarea:\" & Address1.SubAdminArea) '";
anywheresoftware.b4a.keywords.Common.LogImpl("05373962","Subadminarea:"+_address1.getSubAdminArea(),0);
 //BA.debugLineNum = 2762;BA.debugLine="Dim localidad_new As String";
_localidad_new = "";
 //BA.debugLineNum = 2763;BA.debugLine="If Address1.Locality = Null Then";
if (_address1.getLocality()== null) { 
 //BA.debugLineNum = 2764;BA.debugLine="localidad_new = \"\"";
_localidad_new = "";
 }else {
 //BA.debugLineNum = 2766;BA.debugLine="localidad_new = Address1.Locality";
_localidad_new = _address1.getLocality();
 };
 //BA.debugLineNum = 2768;BA.debugLine="Geopartido = Address1.SubAdminArea & \"-\" & loc";
_geopartido = _address1.getSubAdminArea()+"-"+_localidad_new;
 //BA.debugLineNum = 2769;BA.debugLine="Main.strUserOrg = Geopartido";
mostCurrent._main._struserorg /*String*/  = _geopartido;
 //BA.debugLineNum = 2770;BA.debugLine="Log(Geopartido)";
anywheresoftware.b4a.keywords.Common.LogImpl("05373971",_geopartido,0);
 //BA.debugLineNum = 2771;BA.debugLine="lblHayGeoref.Visible = False";
mostCurrent._lblhaygeoref.setVisible(anywheresoftware.b4a.keywords.Common.False);
 };
 } 
       catch (Exception e21) {
			processBA.setLastException(e21); //BA.debugLineNum = 2774;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("05373975",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA)),0);
 //BA.debugLineNum = 2775;BA.debugLine="Geopartido = \"\"";
_geopartido = "";
 //BA.debugLineNum = 2776;BA.debugLine="Main.strUserOrg = \"\"";
mostCurrent._main._struserorg /*String*/  = "";
 //BA.debugLineNum = 2777;BA.debugLine="lblHayGeoref.Visible = True";
mostCurrent._lblhaygeoref.setVisible(anywheresoftware.b4a.keywords.Common.True);
 };
 }else {
 //BA.debugLineNum = 2781;BA.debugLine="lblHayGeoref.Visible = True";
mostCurrent._lblhaygeoref.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 2782;BA.debugLine="Main.strUserOrg =\"\"";
mostCurrent._main._struserorg /*String*/  = "";
 //BA.debugLineNum = 2783;BA.debugLine="Geopartido = \"\"";
_geopartido = "";
 };
 //BA.debugLineNum = 2785;BA.debugLine="End Sub";
return "";
}
public static anywheresoftware.b4a.keywords.Common.ResumableSubWrapper  _getpuntaje() throws Exception{
ResumableSub_getPuntaje rsub = new ResumableSub_getPuntaje(null);
rsub.resume(processBA, null);
return (anywheresoftware.b4a.keywords.Common.ResumableSubWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.keywords.Common.ResumableSubWrapper(), rsub);
}
public static class ResumableSub_getPuntaje extends BA.ResumableSub {
public ResumableSub_getPuntaje(appear.pnud.preservamos.form_main parent) {
this.parent = parent;
}
appear.pnud.preservamos.form_main parent;
appear.pnud.preservamos.httpjob _j = null;
String _loginpath = "";
String _ret = "";
String _act = "";
anywheresoftware.b4a.objects.collections.Map _new_user_markers = null;
anywheresoftware.b4a.objects.collections.Map _new_evals_residuos = null;
anywheresoftware.b4a.objects.collections.Map _new_evals_hidro = null;
anywheresoftware.b4a.objects.collections.JSONParser _parser = null;
anywheresoftware.b4a.objects.collections.List _markerslist = null;
anywheresoftware.b4a.objects.collections.List _evals_residuoslist = null;
anywheresoftware.b4a.objects.collections.List _evals_hidrolist = null;

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
 //BA.debugLineNum = 1865;BA.debugLine="Log(\"Getting puntaje\")";
anywheresoftware.b4a.keywords.Common.LogImpl("04259842","Getting puntaje",0);
 //BA.debugLineNum = 1866;BA.debugLine="Dim j As HttpJob";
_j = new appear.pnud.preservamos.httpjob();
 //BA.debugLineNum = 1867;BA.debugLine="j.Initialize(\"\", Me)";
_j._initialize /*String*/ (processBA,"",form_main.getObject());
 //BA.debugLineNum = 1868;BA.debugLine="Dim loginPath As String = Main.serverPath & \"/\"";
_loginpath = parent.mostCurrent._main._serverpath /*String*/ +"/"+parent.mostCurrent._main._serverconnectionfolder /*String*/ +"/getPuntaje.php?useremail="+parent.mostCurrent._main._struseremail /*String*/ ;
 //BA.debugLineNum = 1869;BA.debugLine="j.Download(loginPath)";
_j._download /*String*/ (_loginpath);
 //BA.debugLineNum = 1870;BA.debugLine="Wait For (j) JobDone(j As HttpJob)";
anywheresoftware.b4a.keywords.Common.WaitFor("jobdone", processBA, this, (Object)(_j));
this.state = 25;
return;
case 25:
//C
this.state = 1;
_j = (appear.pnud.preservamos.httpjob) result[0];
;
 //BA.debugLineNum = 1872;BA.debugLine="If j.Success Then";
if (true) break;

case 1:
//if
this.state = 24;
if (_j._success /*boolean*/ ) { 
this.state = 3;
}else {
this.state = 19;
}if (true) break;

case 3:
//C
this.state = 4;
 //BA.debugLineNum = 1873;BA.debugLine="Main.hayinternet = True";
parent.mostCurrent._main._hayinternet /*boolean*/  = anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 1875;BA.debugLine="Dim ret As String";
_ret = "";
 //BA.debugLineNum = 1876;BA.debugLine="Dim act As String";
_act = "";
 //BA.debugLineNum = 1877;BA.debugLine="Dim new_user_markers As Map";
_new_user_markers = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 1878;BA.debugLine="Dim new_evals_residuos As Map";
_new_evals_residuos = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 1879;BA.debugLine="Dim new_evals_hidro As Map";
_new_evals_hidro = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 1880;BA.debugLine="ret = j.GetString";
_ret = _j._getstring /*String*/ ();
 //BA.debugLineNum = 1881;BA.debugLine="Dim parser As JSONParser";
_parser = new anywheresoftware.b4a.objects.collections.JSONParser();
 //BA.debugLineNum = 1882;BA.debugLine="parser.Initialize(ret)";
_parser.Initialize(_ret);
 //BA.debugLineNum = 1883;BA.debugLine="act = parser.NextValue";
_act = BA.ObjectToString(_parser.NextValue());
 //BA.debugLineNum = 1886;BA.debugLine="Main.puntostotales = 0";
parent.mostCurrent._main._puntostotales /*String*/  = BA.NumberToString(0);
 //BA.debugLineNum = 1887;BA.debugLine="Main.puntos_markers = 0";
parent.mostCurrent._main._puntos_markers /*int*/  = (int) (0);
 //BA.debugLineNum = 1888;BA.debugLine="Main.puntostotales = 0";
parent.mostCurrent._main._puntostotales /*String*/  = BA.NumberToString(0);
 //BA.debugLineNum = 1889;BA.debugLine="Main.puntos_evals_hidro = 0";
parent.mostCurrent._main._puntos_evals_hidro /*int*/  = (int) (0);
 //BA.debugLineNum = 1890;BA.debugLine="Main.puntos_evals_hidro_laguna = 0";
parent.mostCurrent._main._puntos_evals_hidro_laguna /*int*/  = (int) (0);
 //BA.debugLineNum = 1891;BA.debugLine="Main.puntos_evals_hidro_llanura = 0";
parent.mostCurrent._main._puntos_evals_hidro_llanura /*int*/  = (int) (0);
 //BA.debugLineNum = 1892;BA.debugLine="Main.puntos_evals_hidro_sierras = 0";
parent.mostCurrent._main._puntos_evals_hidro_sierras /*int*/  = (int) (0);
 //BA.debugLineNum = 1893;BA.debugLine="Main.puntos_evals_residuos = 0";
parent.mostCurrent._main._puntos_evals_residuos /*int*/  = (int) (0);
 //BA.debugLineNum = 1894;BA.debugLine="Main.puntos_evals_residuos_laguna = 0";
parent.mostCurrent._main._puntos_evals_residuos_laguna /*int*/  = (int) (0);
 //BA.debugLineNum = 1895;BA.debugLine="Main.puntos_evals_residuos_llanura = 0";
parent.mostCurrent._main._puntos_evals_residuos_llanura /*int*/  = (int) (0);
 //BA.debugLineNum = 1896;BA.debugLine="Main.puntos_evals_residuos_sierras = 0";
parent.mostCurrent._main._puntos_evals_residuos_sierras /*int*/  = (int) (0);
 //BA.debugLineNum = 1897;BA.debugLine="Main.user_markers_list.Initialize";
parent.mostCurrent._main._user_markers_list /*anywheresoftware.b4a.objects.collections.List*/ .Initialize();
 //BA.debugLineNum = 1898;BA.debugLine="Main.user_evals_hidro_list.Initialize";
parent.mostCurrent._main._user_evals_hidro_list /*anywheresoftware.b4a.objects.collections.List*/ .Initialize();
 //BA.debugLineNum = 1899;BA.debugLine="Main.user_evals_residuos_list.Initialize";
parent.mostCurrent._main._user_evals_residuos_list /*anywheresoftware.b4a.objects.collections.List*/ .Initialize();
 //BA.debugLineNum = 1901;BA.debugLine="If act = \"Not Found\" Then";
if (true) break;

case 4:
//if
this.state = 17;
if ((_act).equals("Not Found")) { 
this.state = 6;
}else if((_act).equals("Error")) { 
this.state = 8;
}else if((_act).equals("GetPuntajeOk")) { 
this.state = 10;
}if (true) break;

case 6:
//C
this.state = 17;
 //BA.debugLineNum = 1902;BA.debugLine="Main.hayinternet = True";
parent.mostCurrent._main._hayinternet /*boolean*/  = anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 1903;BA.debugLine="j.Release";
_j._release /*String*/ ();
 //BA.debugLineNum = 1904;BA.debugLine="Return False";
if (true) {
anywheresoftware.b4a.keywords.Common.ReturnFromResumableSub(this,(Object)(anywheresoftware.b4a.keywords.Common.False));return;};
 if (true) break;

case 8:
//C
this.state = 17;
 //BA.debugLineNum = 1906;BA.debugLine="Main.hayinternet = False";
parent.mostCurrent._main._hayinternet /*boolean*/  = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 1907;BA.debugLine="j.Release";
_j._release /*String*/ ();
 //BA.debugLineNum = 1908;BA.debugLine="Return False";
if (true) {
anywheresoftware.b4a.keywords.Common.ReturnFromResumableSub(this,(Object)(anywheresoftware.b4a.keywords.Common.False));return;};
 if (true) break;

case 10:
//C
this.state = 11;
 //BA.debugLineNum = 1912;BA.debugLine="Try";
if (true) break;

case 11:
//try
this.state = 16;
this.catchState = 15;
this.state = 13;
if (true) break;

case 13:
//C
this.state = 16;
this.catchState = 15;
 //BA.debugLineNum = 1914;BA.debugLine="Dim markersList As List";
_markerslist = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 1915;BA.debugLine="markersList.Initialize()";
_markerslist.Initialize();
 //BA.debugLineNum = 1916;BA.debugLine="markersList = parser.NextArray";
_markerslist = _parser.NextArray();
 //BA.debugLineNum = 1917;BA.debugLine="Main.user_markers_list = markersList";
parent.mostCurrent._main._user_markers_list /*anywheresoftware.b4a.objects.collections.List*/  = _markerslist;
 //BA.debugLineNum = 1919;BA.debugLine="Dim evals_residuosList As List";
_evals_residuoslist = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 1920;BA.debugLine="evals_residuosList.Initialize()";
_evals_residuoslist.Initialize();
 //BA.debugLineNum = 1921;BA.debugLine="evals_residuosList = parser.NextArray";
_evals_residuoslist = _parser.NextArray();
 //BA.debugLineNum = 1922;BA.debugLine="Main.user_evals_residuos_list = evals_residuo";
parent.mostCurrent._main._user_evals_residuos_list /*anywheresoftware.b4a.objects.collections.List*/  = _evals_residuoslist;
 //BA.debugLineNum = 1924;BA.debugLine="Dim evals_hidroList As List";
_evals_hidrolist = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 1925;BA.debugLine="evals_hidroList.Initialize()";
_evals_hidrolist.Initialize();
 //BA.debugLineNum = 1926;BA.debugLine="evals_hidroList = parser.NextArray";
_evals_hidrolist = _parser.NextArray();
 //BA.debugLineNum = 1927;BA.debugLine="Main.user_evals_hidro_list = evals_hidroList";
parent.mostCurrent._main._user_evals_hidro_list /*anywheresoftware.b4a.objects.collections.List*/  = _evals_hidrolist;
 //BA.debugLineNum = 1928;BA.debugLine="j.Release";
_j._release /*String*/ ();
 //BA.debugLineNum = 1929;BA.debugLine="Return True";
if (true) {
anywheresoftware.b4a.keywords.Common.ReturnFromResumableSub(this,(Object)(anywheresoftware.b4a.keywords.Common.True));return;};
 if (true) break;

case 15:
//C
this.state = 16;
this.catchState = 0;
 //BA.debugLineNum = 1932;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("04259909",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA)),0);
 //BA.debugLineNum = 1933;BA.debugLine="Log(\"error en puntajes\")";
anywheresoftware.b4a.keywords.Common.LogImpl("04259910","error en puntajes",0);
 //BA.debugLineNum = 1934;BA.debugLine="ToastMessageShow(\"No se pudo consultar el per";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("No se pudo consultar el perfil del participante... intente de nuevo luego!"),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1935;BA.debugLine="j.Release";
_j._release /*String*/ ();
 //BA.debugLineNum = 1936;BA.debugLine="Return False";
if (true) {
anywheresoftware.b4a.keywords.Common.ReturnFromResumableSub(this,(Object)(anywheresoftware.b4a.keywords.Common.False));return;};
 if (true) break;
if (true) break;

case 16:
//C
this.state = 17;
this.catchState = 0;
;
 if (true) break;

case 17:
//C
this.state = 24;
;
 if (true) break;

case 19:
//C
this.state = 20;
 //BA.debugLineNum = 1943;BA.debugLine="Main.hayinternet = False";
parent.mostCurrent._main._hayinternet /*boolean*/  = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 1944;BA.debugLine="lblHayInternet.Visible = True";
parent.mostCurrent._lblhayinternet.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1945;BA.debugLine="MsgboxAsync(\"No se puede conectar correctamente";
anywheresoftware.b4a.keywords.Common.MsgboxAsync(BA.ObjectToCharSequence("No se puede conectar correctamente a Internet, algunas funciones estarán restringidas hasta que lo soluciones"),BA.ObjectToCharSequence("Oops!"),processBA);
 //BA.debugLineNum = 1946;BA.debugLine="If tabStripMain.CurrentPage <> 2 Then";
if (true) break;

case 20:
//if
this.state = 23;
if (parent.mostCurrent._tabstripmain.getCurrentPage()!=2) { 
this.state = 22;
}if (true) break;

case 22:
//C
this.state = 23;
 //BA.debugLineNum = 1947;BA.debugLine="tabStripMain.ScrollTo(2,True)";
parent.mostCurrent._tabstripmain.ScrollTo((int) (2),anywheresoftware.b4a.keywords.Common.True);
 if (true) break;

case 23:
//C
this.state = 24;
;
 //BA.debugLineNum = 1949;BA.debugLine="j.Release";
_j._release /*String*/ ();
 //BA.debugLineNum = 1950;BA.debugLine="Return False";
if (true) {
anywheresoftware.b4a.keywords.Common.ReturnFromResumableSub(this,(Object)(anywheresoftware.b4a.keywords.Common.False));return;};
 if (true) break;

case 24:
//C
this.state = -1;
;
 //BA.debugLineNum = 1957;BA.debugLine="End Sub";
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
public static String  _globals() throws Exception{
 //BA.debugLineNum = 16;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 17;BA.debugLine="Dim rp As RuntimePermissions";
mostCurrent._rp = new anywheresoftware.b4a.objects.RuntimePermissions();
 //BA.debugLineNum = 20;BA.debugLine="Dim p As Phone";
mostCurrent._p = new anywheresoftware.b4a.phone.Phone();
 //BA.debugLineNum = 21;BA.debugLine="Dim pi As PhoneIntents";
mostCurrent._pi = new anywheresoftware.b4a.phone.Phone.PhoneIntents();
 //BA.debugLineNum = 25;BA.debugLine="Private pgbNivel As ProgressBar";
mostCurrent._pgbnivel = new anywheresoftware.b4a.objects.ProgressBarWrapper();
 //BA.debugLineNum = 26;BA.debugLine="Private lblLevel As Label";
mostCurrent._lbllevel = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 29;BA.debugLine="Private tabStripMain As TabStrip";
mostCurrent._tabstripmain = new anywheresoftware.b4a.objects.TabStripViewPager();
 //BA.debugLineNum = 30;BA.debugLine="Private icoMapa As Label";
mostCurrent._icomapa = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 31;BA.debugLine="Private icoInformacion As Label";
mostCurrent._icoinformacion = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 32;BA.debugLine="Private icoAnalizar As Label";
mostCurrent._icoanalizar = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 33;BA.debugLine="Private icoPerfil As Label";
mostCurrent._icoperfil = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 34;BA.debugLine="Private icoAlerta As Label";
mostCurrent._icoalerta = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 35;BA.debugLine="Private btnMapa As Label";
mostCurrent._btnmapa = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 36;BA.debugLine="Private btnInformacion As Label";
mostCurrent._btninformacion = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 37;BA.debugLine="Private btnCirculo As Label";
mostCurrent._btncirculo = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 38;BA.debugLine="Private btnAnalizar As Label";
mostCurrent._btnanalizar = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 39;BA.debugLine="Private btnPerfil As Label";
mostCurrent._btnperfil = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 40;BA.debugLine="Private btnAlerta As Label";
mostCurrent._btnalerta = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 44;BA.debugLine="Private lblAnalizar As Label";
mostCurrent._lblanalizar = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 45;BA.debugLine="Private btnReportar As Button";
mostCurrent._btnreportar = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 46;BA.debugLine="Private lblComoFuncionaAnalizar As Label";
mostCurrent._lblcomofuncionaanalizar = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 47;BA.debugLine="Private tipoAnalisis As String";
mostCurrent._tipoanalisis = "";
 //BA.debugLineNum = 48;BA.debugLine="Private pnlHabitat As Panel";
mostCurrent._pnlhabitat = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 49;BA.debugLine="Private pnlResiduos As Panel";
mostCurrent._pnlresiduos = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 50;BA.debugLine="Private pnlHidro As Panel";
mostCurrent._pnlhidro = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 53;BA.debugLine="Private btnAbrirMapa As Button";
mostCurrent._btnabrirmapa = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 54;BA.debugLine="Private btnDetectar As Button";
mostCurrent._btndetectar = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 55;BA.debugLine="Private lblLat As Label";
mostCurrent._lbllat = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 56;BA.debugLine="Private lblLon As Label";
mostCurrent._lbllon = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 57;BA.debugLine="Private btnZoomAll As Button";
mostCurrent._btnzoomall = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 58;BA.debugLine="Dim MarkerExport As List";
mostCurrent._markerexport = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 60;BA.debugLine="Dim MarkerInfo As Label";
mostCurrent._markerinfo = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 61;BA.debugLine="Private pnlMapa As Panel";
mostCurrent._pnlmapa = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 62;BA.debugLine="Dim fondoblanco As Label";
mostCurrent._fondoblanco = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 63;BA.debugLine="Dim detectandoLabel As Label";
mostCurrent._detectandolabel = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 67;BA.debugLine="Dim firstuse As String";
mostCurrent._firstuse = "";
 //BA.debugLineNum = 68;BA.debugLine="Dim fondogris As Panel";
mostCurrent._fondogris = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 69;BA.debugLine="Dim panelComoFunciona As Panel";
mostCurrent._panelcomofunciona = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 71;BA.debugLine="Dim tutorialEtapa As Int";
_tutorialetapa = 0;
 //BA.debugLineNum = 72;BA.debugLine="Private btnMenu_Main As Label";
mostCurrent._btnmenu_main = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 76;BA.debugLine="Dim Drawer As B4XDrawer";
mostCurrent._drawer = new appear.pnud.preservamos.b4xdrawer();
 //BA.debugLineNum = 77;BA.debugLine="Private btnCerrarSesion As Label";
mostCurrent._btncerrarsesion = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 78;BA.debugLine="Private btnEditUser As Label";
mostCurrent._btnedituser = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 79;BA.debugLine="Private btnverMedallas As Label";
mostCurrent._btnvermedallas = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 80;BA.debugLine="Private lblUserName As Label";
mostCurrent._lblusername = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 81;BA.debugLine="Private lblRegistrate As Label";
mostCurrent._lblregistrate = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 82;BA.debugLine="Private btnVerPerfil As Label";
mostCurrent._btnverperfil = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 83;BA.debugLine="Private btnAbout As Label";
mostCurrent._btnabout = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 84;BA.debugLine="Private btnPoliticaDatos As Label";
mostCurrent._btnpoliticadatos = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 85;BA.debugLine="Private btnDatosAnteriores As Label";
mostCurrent._btndatosanteriores = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 86;BA.debugLine="Private btnMuestreos As Label";
mostCurrent._btnmuestreos = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 90;BA.debugLine="Private lblTitleMunicipio As Label";
mostCurrent._lbltitlemunicipio = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 91;BA.debugLine="Private WebView1 As WebView";
mostCurrent._webview1 = new anywheresoftware.b4a.objects.WebViewWrapper();
 //BA.debugLineNum = 92;BA.debugLine="Private lblTitleMunicipio As Label";
mostCurrent._lbltitlemunicipio = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 93;BA.debugLine="Private lblMunicipio_texto_principal As CustomLis";
mostCurrent._lblmunicipio_texto_principal = new b4a.example3.customlistview();
 //BA.debugLineNum = 94;BA.debugLine="Private lblMunicipio_puntos_totales As Label";
mostCurrent._lblmunicipio_puntos_totales = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 95;BA.debugLine="Private btnMasInfoMunicipio As ImageView";
mostCurrent._btnmasinfomunicipio = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 96;BA.debugLine="Private imgLogoMunicipio As ImageView";
mostCurrent._imglogomunicipio = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 97;BA.debugLine="Private gauge_Partido As Gauge";
mostCurrent._gauge_partido = new appear.pnud.preservamos.gauge();
 //BA.debugLineNum = 98;BA.debugLine="Private xui As XUI";
mostCurrent._xui = new anywheresoftware.b4a.objects.B4XViewWrapper.XUI();
 //BA.debugLineNum = 99;BA.debugLine="Private lblMunicipio_estado As Label";
mostCurrent._lblmunicipio_estado = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 100;BA.debugLine="Dim Municipios_numresults As Int";
_municipios_numresults = 0;
 //BA.debugLineNum = 101;BA.debugLine="Dim Municipios_parser_string As String";
mostCurrent._municipios_parser_string = "";
 //BA.debugLineNum = 102;BA.debugLine="Dim municipioURL As String";
mostCurrent._municipiourl = "";
 //BA.debugLineNum = 103;BA.debugLine="Dim municipioFB As String";
mostCurrent._municipiofb = "";
 //BA.debugLineNum = 104;BA.debugLine="Dim municipioIG As String";
mostCurrent._municipioig = "";
 //BA.debugLineNum = 105;BA.debugLine="Dim municipioYT As String";
mostCurrent._municipioyt = "";
 //BA.debugLineNum = 106;BA.debugLine="Dim municipioTW As String";
mostCurrent._municipiotw = "";
 //BA.debugLineNum = 107;BA.debugLine="Private btnMunicipioTW As ImageView";
mostCurrent._btnmunicipiotw = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 108;BA.debugLine="Private btnMunicipioFB As ImageView";
mostCurrent._btnmunicipiofb = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 109;BA.debugLine="Private btnMunicipioYT As ImageView";
mostCurrent._btnmunicipioyt = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 110;BA.debugLine="Private btnMunicipioIG As ImageView";
mostCurrent._btnmunicipioig = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 111;BA.debugLine="Private btnVerDatosMunicipio As Button";
mostCurrent._btnverdatosmunicipio = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 112;BA.debugLine="Private btnVerEstadisticasMunicipio As Button";
mostCurrent._btnverestadisticasmunicipio = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 113;BA.debugLine="Private btnCambiarMunicipio As Button";
mostCurrent._btncambiarmunicipio = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 115;BA.debugLine="Dim valorEstado As Double";
_valorestado = 0;
 //BA.debugLineNum = 116;BA.debugLine="Dim hidroPromedio As Double";
_hidropromedio = 0;
 //BA.debugLineNum = 117;BA.debugLine="Dim aguaPromedio As Double";
_aguapromedio = 0;
 //BA.debugLineNum = 118;BA.debugLine="Dim bioPromedio As Double";
_biopromedio = 0;
 //BA.debugLineNum = 119;BA.debugLine="Dim UsosPromedio As Double";
_usospromedio = 0;
 //BA.debugLineNum = 120;BA.debugLine="Dim ExoticasPromedio As Double";
_exoticaspromedio = 0;
 //BA.debugLineNum = 121;BA.debugLine="Dim listaPromedio As List";
mostCurrent._listapromedio = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 122;BA.debugLine="Dim listaHidro_Promedio As List";
mostCurrent._listahidro_promedio = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 123;BA.debugLine="Dim listaAgua_Promedio As List";
mostCurrent._listaagua_promedio = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 124;BA.debugLine="Dim listaBio_Promedio As List";
mostCurrent._listabio_promedio = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 125;BA.debugLine="Dim listaUsos_Promedio As List";
mostCurrent._listausos_promedio = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 126;BA.debugLine="Dim listaExoticas_Promedio As List";
mostCurrent._listaexoticas_promedio = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 131;BA.debugLine="Private lblComoFuncionaContenido As Label";
mostCurrent._lblcomofuncionacontenido = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 132;BA.debugLine="Private lblComoFuncionaTitle As Label";
mostCurrent._lblcomofuncionatitle = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 135;BA.debugLine="Private lblPuntos_Explicacion_1 As Label";
mostCurrent._lblpuntos_explicacion_1 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 136;BA.debugLine="Private lblPuntos_Explicacion_2 As Label";
mostCurrent._lblpuntos_explicacion_2 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 137;BA.debugLine="Private lblPuntos_Explicacion_3 As Label";
mostCurrent._lblpuntos_explicacion_3 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 138;BA.debugLine="Private btnActualizar As Button";
mostCurrent._btnactualizar = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 139;BA.debugLine="Private lblTitleProfile As Label";
mostCurrent._lbltitleprofile = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 140;BA.debugLine="Private lblPerfil_puntos_totales As Label";
mostCurrent._lblperfil_puntos_totales = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 141;BA.debugLine="Private lblPuntos_Titulo_1 As Label";
mostCurrent._lblpuntos_titulo_1 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 142;BA.debugLine="Private lblPuntos_Titulo_2 As Label";
mostCurrent._lblpuntos_titulo_2 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 143;BA.debugLine="Private lblPuntos_Titulo_3 As Label";
mostCurrent._lblpuntos_titulo_3 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 144;BA.debugLine="Private imgHabitat As Button";
mostCurrent._imghabitat = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 145;BA.debugLine="Private imgBasura As Button";
mostCurrent._imgbasura = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 146;BA.debugLine="Private imgHidro As Button";
mostCurrent._imghidro = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 150;BA.debugLine="Private pgbLevel As ProgressBar";
mostCurrent._pgblevel = new anywheresoftware.b4a.objects.ProgressBarWrapper();
 //BA.debugLineNum = 152;BA.debugLine="Private lstAchievements As ListView";
mostCurrent._lstachievements = new anywheresoftware.b4a.objects.ListViewWrapper();
 //BA.debugLineNum = 153;BA.debugLine="Private lblNivel As Label";
mostCurrent._lblnivel = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 154;BA.debugLine="Private btnShare As Button";
mostCurrent._btnshare = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 155;BA.debugLine="Private btnShareOthers As Button";
mostCurrent._btnshareothers = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 156;BA.debugLine="Private lblTitulo As Label";
mostCurrent._lbltitulo = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 157;BA.debugLine="Private imgBadge As ImageView";
mostCurrent._imgbadge = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 158;BA.debugLine="Private nivel As String";
mostCurrent._nivel = "";
 //BA.debugLineNum = 159;BA.debugLine="Private imagetoshare As String";
mostCurrent._imagetoshare = "";
 //BA.debugLineNum = 160;BA.debugLine="Dim fondogris_share As Panel";
mostCurrent._fondogris_share = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 161;BA.debugLine="Private pnlShare As Panel";
mostCurrent._pnlshare = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 162;BA.debugLine="Private imgBadgeLevel As ImageView";
mostCurrent._imgbadgelevel = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 165;BA.debugLine="Private lblMunicipios_reportes As Label";
mostCurrent._lblmunicipios_reportes = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 166;BA.debugLine="Private scrReportar_Modulos As ScrollView";
mostCurrent._scrreportar_modulos = new anywheresoftware.b4a.objects.ScrollViewWrapper();
 //BA.debugLineNum = 171;BA.debugLine="Private lblHayInternet As Label";
mostCurrent._lblhayinternet = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 172;BA.debugLine="Private gmap As GoogleMap";
mostCurrent._gmap = new anywheresoftware.b4a.objects.MapFragmentWrapper.GoogleMapWrapper();
 //BA.debugLineNum = 173;BA.debugLine="Private MapFragment1 As MapFragment";
mostCurrent._mapfragment1 = new anywheresoftware.b4a.objects.MapFragmentWrapper();
 //BA.debugLineNum = 174;BA.debugLine="Private lblEcorregionTitle As Label";
mostCurrent._lblecorregiontitle = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 175;BA.debugLine="Private pnlOverlay As Panel";
mostCurrent._pnloverlay = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 176;BA.debugLine="Private lblHayGeoref As Label";
mostCurrent._lblhaygeoref = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 177;BA.debugLine="Private lblEstado As Label";
mostCurrent._lblestado = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 178;BA.debugLine="Private logo_load_gif As B4XGifView";
mostCurrent._logo_load_gif = new appear.pnud.preservamos.b4xgifview();
 //BA.debugLineNum = 181;BA.debugLine="Private Bottom_Drawer_Overlay As B4XView";
mostCurrent._bottom_drawer_overlay = new anywheresoftware.b4a.objects.B4XViewWrapper();
 //BA.debugLineNum = 182;BA.debugLine="Private xui As XUI";
mostCurrent._xui = new anywheresoftware.b4a.objects.B4XViewWrapper.XUI();
 //BA.debugLineNum = 183;BA.debugLine="Private pnlBottom As B4XView";
mostCurrent._pnlbottom = new anywheresoftware.b4a.objects.B4XViewWrapper();
 //BA.debugLineNum = 184;BA.debugLine="Private Bottom_Drawer_OverlayVisible As Boolean";
_bottom_drawer_overlayvisible = false;
 //BA.debugLineNum = 185;BA.debugLine="Private selectedTest As String";
mostCurrent._selectedtest = "";
 //BA.debugLineNum = 186;BA.debugLine="Private btnStartTest As Button";
mostCurrent._btnstarttest = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 187;BA.debugLine="Private lblRequisitosAnalisisValor As Label";
mostCurrent._lblrequisitosanalisisvalor = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 188;BA.debugLine="Private lblTiempoAnalisisValor As Label";
mostCurrent._lbltiempoanalisisvalor = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 189;BA.debugLine="Private lblDificultadValor As Label";
mostCurrent._lbldificultadvalor = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 190;BA.debugLine="Private lblDescripcionAnalisis As Label";
mostCurrent._lbldescripcionanalisis = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 191;BA.debugLine="Private lblPremioAnalisisValor As Label";
mostCurrent._lblpremioanalisisvalor = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 192;BA.debugLine="Private lblTituloAnalisis As Label";
mostCurrent._lbltituloanalisis = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 193;BA.debugLine="Private lblEcorregionSubTitle As Label";
mostCurrent._lblecorregionsubtitle = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 196;BA.debugLine="Dim cust_dialog As B4XDialog";
mostCurrent._cust_dialog = new appear.pnud.preservamos.b4xdialog();
 //BA.debugLineNum = 197;BA.debugLine="Private fondoInfoTerrain As Panel";
mostCurrent._fondoinfoterrain = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 198;BA.debugLine="Private imgInfoTerrain As Label";
mostCurrent._imginfoterrain = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 199;BA.debugLine="Private lblInfoTerrain_Title As Label";
mostCurrent._lblinfoterrain_title = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 200;BA.debugLine="Private btnOkTerrainInfo As Button";
mostCurrent._btnokterraininfo = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 201;BA.debugLine="Private lblInfoTerrain_Descripcion As Label";
mostCurrent._lblinfoterrain_descripcion = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 202;BA.debugLine="Private spnEcoregion As Spinner";
mostCurrent._spnecoregion = new anywheresoftware.b4a.objects.SpinnerWrapper();
 //BA.debugLineNum = 203;BA.debugLine="Private lblEcoregionBanda As Label";
mostCurrent._lblecoregionbanda = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 204;BA.debugLine="Private imgEcorregionMapa As ImageView";
mostCurrent._imgecorregionmapa = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 207;BA.debugLine="End Sub";
return "";
}
public static String  _gotoinformacion() throws Exception{
 //BA.debugLineNum = 578;BA.debugLine="Sub gotoInformacion 'necesario para cuando termina";
 //BA.debugLineNum = 579;BA.debugLine="tabStripMain.ScrollTo(1, True)";
mostCurrent._tabstripmain.ScrollTo((int) (1),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 585;BA.debugLine="icoAnalizar.TextColor = Colors.ARGB(255,51,51,51)";
mostCurrent._icoanalizar.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (51),(int) (51),(int) (51)));
 //BA.debugLineNum = 586;BA.debugLine="icoMapa.TextColor = Colors.ARGB(255,51,51,51)";
mostCurrent._icomapa.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (51),(int) (51),(int) (51)));
 //BA.debugLineNum = 587;BA.debugLine="icoPerfil.TextColor = Colors.ARGB(255,51,51,51)";
mostCurrent._icoperfil.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (51),(int) (51),(int) (51)));
 //BA.debugLineNum = 588;BA.debugLine="icoAlerta.TextSize = 40";
mostCurrent._icoalerta.setTextSize((float) (40));
 //BA.debugLineNum = 589;BA.debugLine="icoAnalizar.TextSize = 40";
mostCurrent._icoanalizar.setTextSize((float) (40));
 //BA.debugLineNum = 590;BA.debugLine="icoMapa.TextSize = 40";
mostCurrent._icomapa.setTextSize((float) (40));
 //BA.debugLineNum = 591;BA.debugLine="icoPerfil.TextSize = 40";
mostCurrent._icoperfil.setTextSize((float) (40));
 //BA.debugLineNum = 592;BA.debugLine="btnMapa.Text = \"Explorar\"";
mostCurrent._btnmapa.setText(BA.ObjectToCharSequence("Explorar"));
 //BA.debugLineNum = 593;BA.debugLine="btnAnalizar.Text = \"Analizar\"";
mostCurrent._btnanalizar.setText(BA.ObjectToCharSequence("Analizar"));
 //BA.debugLineNum = 594;BA.debugLine="btnPerfil.Text  = \"Perfil\"";
mostCurrent._btnperfil.setText(BA.ObjectToCharSequence("Perfil"));
 //BA.debugLineNum = 595;BA.debugLine="icoInformacion.SetTextColorAnimated(300, Colors.w";
mostCurrent._icoinformacion.SetTextColorAnimated((int) (300),anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 596;BA.debugLine="icoInformacion.SetTextSizeAnimated(300,50)";
mostCurrent._icoinformacion.SetTextSizeAnimated((int) (300),(float) (50));
 //BA.debugLineNum = 597;BA.debugLine="btnCirculo.SetLayoutAnimated(300, (btnInformacion";
mostCurrent._btncirculo.SetLayoutAnimated((int) (300),(int) ((mostCurrent._btninformacion.getLeft()-((mostCurrent._btncirculo.getWidth()-mostCurrent._btninformacion.getWidth())/(double)2))),mostCurrent._btncirculo.getTop(),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (80)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (80)));
 //BA.debugLineNum = 598;BA.debugLine="btnInformacion.Text = \"\"";
mostCurrent._btninformacion.setText(BA.ObjectToCharSequence(""));
 //BA.debugLineNum = 599;BA.debugLine="End Sub";
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
 //BA.debugLineNum = 622;BA.debugLine="icoMapa.TextColor = Colors.ARGB(255,51,51,51)";
parent.mostCurrent._icomapa.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (51),(int) (51),(int) (51)));
 //BA.debugLineNum = 623;BA.debugLine="icoAnalizar.TextColor = Colors.ARGB(255,51,51,51)";
parent.mostCurrent._icoanalizar.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (51),(int) (51),(int) (51)));
 //BA.debugLineNum = 624;BA.debugLine="icoInformacion.TextColor = Colors.ARGB(255,51,51,";
parent.mostCurrent._icoinformacion.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (51),(int) (51),(int) (51)));
 //BA.debugLineNum = 625;BA.debugLine="icoPerfil.TextColor = Colors.ARGB(255,51,51,51)";
parent.mostCurrent._icoperfil.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (51),(int) (51),(int) (51)));
 //BA.debugLineNum = 626;BA.debugLine="icoMapa.TextSize = 40";
parent.mostCurrent._icomapa.setTextSize((float) (40));
 //BA.debugLineNum = 627;BA.debugLine="icoAnalizar.TextSize = 40";
parent.mostCurrent._icoanalizar.setTextSize((float) (40));
 //BA.debugLineNum = 628;BA.debugLine="icoInformacion.TextSize = 40";
parent.mostCurrent._icoinformacion.setTextSize((float) (40));
 //BA.debugLineNum = 629;BA.debugLine="icoPerfil.TextSize = 40";
parent.mostCurrent._icoperfil.setTextSize((float) (40));
 //BA.debugLineNum = 630;BA.debugLine="btnMapa.Text = \"Explorar\"";
parent.mostCurrent._btnmapa.setText(BA.ObjectToCharSequence("Explorar"));
 //BA.debugLineNum = 631;BA.debugLine="btnInformacion.Text = \"Municipio\"";
parent.mostCurrent._btninformacion.setText(BA.ObjectToCharSequence("Municipio"));
 //BA.debugLineNum = 632;BA.debugLine="btnAnalizar.Text = \"Analizar\"";
parent.mostCurrent._btnanalizar.setText(BA.ObjectToCharSequence("Analizar"));
 //BA.debugLineNum = 633;BA.debugLine="btnPerfil.Text = \"Perfil\"";
parent.mostCurrent._btnperfil.setText(BA.ObjectToCharSequence("Perfil"));
 //BA.debugLineNum = 635;BA.debugLine="icoAlerta.SetTextColorAnimated(300, Colors.white)";
parent.mostCurrent._icoalerta.SetTextColorAnimated((int) (300),anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 636;BA.debugLine="icoAlerta.SetTextSizeAnimated(300,50)";
parent.mostCurrent._icoalerta.SetTextSizeAnimated((int) (300),(float) (50));
 //BA.debugLineNum = 637;BA.debugLine="btnCirculo.SetLayoutAnimated(300, (icoAlerta.Left";
parent.mostCurrent._btncirculo.SetLayoutAnimated((int) (300),(int) ((parent.mostCurrent._icoalerta.getLeft()-((parent.mostCurrent._btncirculo.getWidth()-parent.mostCurrent._icoalerta.getWidth())/(double)2))),parent.mostCurrent._btncirculo.getTop(),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (80)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (80)));
 //BA.debugLineNum = 638;BA.debugLine="icoAlerta.Text = \"\"";
parent.mostCurrent._icoalerta.setText(BA.ObjectToCharSequence(""));
 //BA.debugLineNum = 641;BA.debugLine="Msgbox2Async(\"Podrás emitir un alerta verde sobre";
anywheresoftware.b4a.keywords.Common.Msgbox2Async(BA.ObjectToCharSequence("Podrás emitir un alerta verde sobre algún evento ecológico, como una floración de algas, una mortandad de peces, o un derrame industrial!"),BA.ObjectToCharSequence("¡ALERTA VERDE!"),"Proseguir!","","Cancelar alerta",(anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper(), (android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null)),processBA,anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 642;BA.debugLine="Wait For Msgbox_Result (Result As Int)";
anywheresoftware.b4a.keywords.Common.WaitFor("msgbox_result", processBA, this, null);
this.state = 17;
return;
case 17:
//C
this.state = 1;
_result = (Integer) result[0];
;
 //BA.debugLineNum = 643;BA.debugLine="If Result = DialogResponse.POSITIVE Then";
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
 //BA.debugLineNum = 644;BA.debugLine="StartActivity(Alertas)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(parent.mostCurrent._alertas.getObject()));
 if (true) break;

case 5:
//C
this.state = 6;
 //BA.debugLineNum = 646;BA.debugLine="If tabStripMain.CurrentPage = 0 Then";
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
 //BA.debugLineNum = 647;BA.debugLine="icoAlerta.TextColor = Colors.ARGB(255,51,51,51)";
parent.mostCurrent._icoalerta.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (51),(int) (51),(int) (51)));
 //BA.debugLineNum = 648;BA.debugLine="icoAnalizar.TextColor = Colors.ARGB(255,51,51,5";
parent.mostCurrent._icoanalizar.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (51),(int) (51),(int) (51)));
 //BA.debugLineNum = 649;BA.debugLine="icoInformacion.TextColor = Colors.ARGB(255,51,5";
parent.mostCurrent._icoinformacion.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (51),(int) (51),(int) (51)));
 //BA.debugLineNum = 650;BA.debugLine="icoPerfil.TextColor = Colors.ARGB(255,51,51,51)";
parent.mostCurrent._icoperfil.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (51),(int) (51),(int) (51)));
 //BA.debugLineNum = 651;BA.debugLine="icoAlerta.TextSize = 40";
parent.mostCurrent._icoalerta.setTextSize((float) (40));
 //BA.debugLineNum = 652;BA.debugLine="icoAnalizar.TextSize = 40";
parent.mostCurrent._icoanalizar.setTextSize((float) (40));
 //BA.debugLineNum = 653;BA.debugLine="icoInformacion.TextSize = 40";
parent.mostCurrent._icoinformacion.setTextSize((float) (40));
 //BA.debugLineNum = 654;BA.debugLine="icoPerfil.TextSize = 40";
parent.mostCurrent._icoperfil.setTextSize((float) (40));
 //BA.debugLineNum = 655;BA.debugLine="btnInformacion.Text = \"Municipio\"";
parent.mostCurrent._btninformacion.setText(BA.ObjectToCharSequence("Municipio"));
 //BA.debugLineNum = 656;BA.debugLine="btnAnalizar.Text = \"Analizar\"";
parent.mostCurrent._btnanalizar.setText(BA.ObjectToCharSequence("Analizar"));
 //BA.debugLineNum = 657;BA.debugLine="btnPerfil.Text  = \"Perfil\"";
parent.mostCurrent._btnperfil.setText(BA.ObjectToCharSequence("Perfil"));
 //BA.debugLineNum = 659;BA.debugLine="icoMapa.SetTextColorAnimated(300, Colors.white)";
parent.mostCurrent._icomapa.SetTextColorAnimated((int) (300),anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 660;BA.debugLine="icoMapa.SetTextSizeAnimated(300,50)";
parent.mostCurrent._icomapa.SetTextSizeAnimated((int) (300),(float) (50));
 //BA.debugLineNum = 661;BA.debugLine="btnCirculo.SetLayoutAnimated(300, (icoMapa.Left";
parent.mostCurrent._btncirculo.SetLayoutAnimated((int) (300),(int) ((parent.mostCurrent._icomapa.getLeft()-((parent.mostCurrent._btncirculo.getWidth()-parent.mostCurrent._icomapa.getWidth())/(double)2))),parent.mostCurrent._btncirculo.getTop(),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (80)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (80)));
 //BA.debugLineNum = 662;BA.debugLine="btnMapa.Text = \"";
parent.mostCurrent._btnmapa.setText(BA.ObjectToCharSequence(""));
 if (true) break;

case 10:
//C
this.state = 15;
 //BA.debugLineNum = 664;BA.debugLine="icoAlerta.TextColor = Colors.ARGB(255,51,51,51)";
parent.mostCurrent._icoalerta.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (51),(int) (51),(int) (51)));
 //BA.debugLineNum = 665;BA.debugLine="icoAnalizar.TextColor = Colors.ARGB(255,51,51,5";
parent.mostCurrent._icoanalizar.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (51),(int) (51),(int) (51)));
 //BA.debugLineNum = 666;BA.debugLine="icoMapa.TextColor = Colors.ARGB(255,51,51,51)";
parent.mostCurrent._icomapa.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (51),(int) (51),(int) (51)));
 //BA.debugLineNum = 667;BA.debugLine="icoPerfil.TextColor = Colors.ARGB(255,51,51,51)";
parent.mostCurrent._icoperfil.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (51),(int) (51),(int) (51)));
 //BA.debugLineNum = 668;BA.debugLine="icoAlerta.TextSize = 40";
parent.mostCurrent._icoalerta.setTextSize((float) (40));
 //BA.debugLineNum = 669;BA.debugLine="icoAnalizar.TextSize = 40";
parent.mostCurrent._icoanalizar.setTextSize((float) (40));
 //BA.debugLineNum = 670;BA.debugLine="icoMapa.TextSize = 40";
parent.mostCurrent._icomapa.setTextSize((float) (40));
 //BA.debugLineNum = 671;BA.debugLine="icoPerfil.TextSize = 40";
parent.mostCurrent._icoperfil.setTextSize((float) (40));
 //BA.debugLineNum = 672;BA.debugLine="btnMapa.Text = \"Explorar\"";
parent.mostCurrent._btnmapa.setText(BA.ObjectToCharSequence("Explorar"));
 //BA.debugLineNum = 673;BA.debugLine="btnAnalizar.Text = \"Analizar\"";
parent.mostCurrent._btnanalizar.setText(BA.ObjectToCharSequence("Analizar"));
 //BA.debugLineNum = 674;BA.debugLine="btnPerfil.Text  = \"Perfil\"";
parent.mostCurrent._btnperfil.setText(BA.ObjectToCharSequence("Perfil"));
 //BA.debugLineNum = 676;BA.debugLine="icoInformacion.SetTextColorAnimated(300, Colors";
parent.mostCurrent._icoinformacion.SetTextColorAnimated((int) (300),anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 677;BA.debugLine="icoInformacion.SetTextSizeAnimated(300,50)";
parent.mostCurrent._icoinformacion.SetTextSizeAnimated((int) (300),(float) (50));
 //BA.debugLineNum = 678;BA.debugLine="btnCirculo.SetLayoutAnimated(300, (btnInformaci";
parent.mostCurrent._btncirculo.SetLayoutAnimated((int) (300),(int) ((parent.mostCurrent._btninformacion.getLeft()-((parent.mostCurrent._btncirculo.getWidth()-parent.mostCurrent._btninformacion.getWidth())/(double)2))),parent.mostCurrent._btncirculo.getTop(),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (80)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (80)));
 //BA.debugLineNum = 679;BA.debugLine="btnInformacion.Text = \"\"";
parent.mostCurrent._btninformacion.setText(BA.ObjectToCharSequence(""));
 if (true) break;

case 12:
//C
this.state = 15;
 //BA.debugLineNum = 681;BA.debugLine="icoMapa.TextColor = Colors.ARGB(255,51,51,51)";
parent.mostCurrent._icomapa.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (51),(int) (51),(int) (51)));
 //BA.debugLineNum = 682;BA.debugLine="icoInformacion.TextColor = Colors.ARGB(255,51,5";
parent.mostCurrent._icoinformacion.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (51),(int) (51),(int) (51)));
 //BA.debugLineNum = 683;BA.debugLine="icoPerfil.TextColor = Colors.ARGB(255,51,51,51)";
parent.mostCurrent._icoperfil.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (51),(int) (51),(int) (51)));
 //BA.debugLineNum = 684;BA.debugLine="icoMapa.TextSize = 40";
parent.mostCurrent._icomapa.setTextSize((float) (40));
 //BA.debugLineNum = 685;BA.debugLine="icoInformacion.TextSize = 40";
parent.mostCurrent._icoinformacion.setTextSize((float) (40));
 //BA.debugLineNum = 686;BA.debugLine="icoPerfil.TextSize = 40";
parent.mostCurrent._icoperfil.setTextSize((float) (40));
 //BA.debugLineNum = 687;BA.debugLine="btnMapa.Text = \"Explorar\"";
parent.mostCurrent._btnmapa.setText(BA.ObjectToCharSequence("Explorar"));
 //BA.debugLineNum = 688;BA.debugLine="btnInformacion.Text = \"Municipio\"";
parent.mostCurrent._btninformacion.setText(BA.ObjectToCharSequence("Municipio"));
 //BA.debugLineNum = 689;BA.debugLine="btnPerfil.Text  = \"Perfil\"";
parent.mostCurrent._btnperfil.setText(BA.ObjectToCharSequence("Perfil"));
 //BA.debugLineNum = 691;BA.debugLine="icoAnalizar.SetTextColorAnimated(300, Colors.wh";
parent.mostCurrent._icoanalizar.SetTextColorAnimated((int) (300),anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 692;BA.debugLine="icoAnalizar.SetTextSizeAnimated(300,50)";
parent.mostCurrent._icoanalizar.SetTextSizeAnimated((int) (300),(float) (50));
 //BA.debugLineNum = 693;BA.debugLine="btnCirculo.SetLayoutAnimated(300, (btnAnalizar.";
parent.mostCurrent._btncirculo.SetLayoutAnimated((int) (300),(int) ((parent.mostCurrent._btnanalizar.getLeft()-((parent.mostCurrent._btncirculo.getWidth()-parent.mostCurrent._btnanalizar.getWidth())/(double)2))),parent.mostCurrent._btncirculo.getTop(),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (80)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (80)));
 //BA.debugLineNum = 694;BA.debugLine="btnAnalizar.Text = \"\"";
parent.mostCurrent._btnanalizar.setText(BA.ObjectToCharSequence(""));
 if (true) break;

case 14:
//C
this.state = 15;
 //BA.debugLineNum = 696;BA.debugLine="icoMapa.TextColor = Colors.ARGB(255,51,51,51)";
parent.mostCurrent._icomapa.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (51),(int) (51),(int) (51)));
 //BA.debugLineNum = 697;BA.debugLine="icoAnalizar.TextColor = Colors.ARGB(255,51,51,5";
parent.mostCurrent._icoanalizar.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (51),(int) (51),(int) (51)));
 //BA.debugLineNum = 698;BA.debugLine="icoInformacion.TextColor = Colors.ARGB(255,51,5";
parent.mostCurrent._icoinformacion.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (51),(int) (51),(int) (51)));
 //BA.debugLineNum = 699;BA.debugLine="icoAlerta.TextColor = Colors.ARGB(255,51,51,51)";
parent.mostCurrent._icoalerta.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (51),(int) (51),(int) (51)));
 //BA.debugLineNum = 700;BA.debugLine="icoMapa.TextSize = 40";
parent.mostCurrent._icomapa.setTextSize((float) (40));
 //BA.debugLineNum = 701;BA.debugLine="icoAnalizar.TextSize = 40";
parent.mostCurrent._icoanalizar.setTextSize((float) (40));
 //BA.debugLineNum = 702;BA.debugLine="icoInformacion.TextSize = 40";
parent.mostCurrent._icoinformacion.setTextSize((float) (40));
 //BA.debugLineNum = 703;BA.debugLine="icoAlerta.TextSize = 40";
parent.mostCurrent._icoalerta.setTextSize((float) (40));
 //BA.debugLineNum = 704;BA.debugLine="btnMapa.Text = \"Explorar\"";
parent.mostCurrent._btnmapa.setText(BA.ObjectToCharSequence("Explorar"));
 //BA.debugLineNum = 705;BA.debugLine="btnInformacion.Text = \"Municipio\"";
parent.mostCurrent._btninformacion.setText(BA.ObjectToCharSequence("Municipio"));
 //BA.debugLineNum = 706;BA.debugLine="btnAnalizar.Text = \"Analizar\"";
parent.mostCurrent._btnanalizar.setText(BA.ObjectToCharSequence("Analizar"));
 //BA.debugLineNum = 708;BA.debugLine="icoPerfil.SetTextColorAnimated(300, Colors.whit";
parent.mostCurrent._icoperfil.SetTextColorAnimated((int) (300),anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 709;BA.debugLine="icoPerfil.SetTextSizeAnimated(300,50)";
parent.mostCurrent._icoperfil.SetTextSizeAnimated((int) (300),(float) (50));
 //BA.debugLineNum = 710;BA.debugLine="btnCirculo.SetLayoutAnimated(300, (icoPerfil.Le";
parent.mostCurrent._btncirculo.SetLayoutAnimated((int) (300),(int) ((parent.mostCurrent._icoperfil.getLeft()-((parent.mostCurrent._btncirculo.getWidth()-parent.mostCurrent._icoperfil.getWidth())/(double)2))),parent.mostCurrent._btncirculo.getTop(),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (80)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (80)));
 //BA.debugLineNum = 711;BA.debugLine="btnPerfil.Text = \"\"";
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
 //BA.debugLineNum = 714;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public static String  _icoanalizar_click() throws Exception{
 //BA.debugLineNum = 611;BA.debugLine="Private Sub icoAnalizar_Click";
 //BA.debugLineNum = 612;BA.debugLine="tabStripMain.ScrollTo(2, True)";
mostCurrent._tabstripmain.ScrollTo((int) (2),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 613;BA.debugLine="End Sub";
return "";
}
public static String  _icoinformacion_click() throws Exception{
 //BA.debugLineNum = 604;BA.debugLine="Private Sub icoInformacion_Click";
 //BA.debugLineNum = 605;BA.debugLine="tabStripMain.ScrollTo(1, True)";
mostCurrent._tabstripmain.ScrollTo((int) (1),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 606;BA.debugLine="End Sub";
return "";
}
public static String  _icomapa_click() throws Exception{
 //BA.debugLineNum = 575;BA.debugLine="Private Sub icoMapa_Click";
 //BA.debugLineNum = 576;BA.debugLine="tabStripMain.ScrollTo(0, True)";
mostCurrent._tabstripmain.ScrollTo((int) (0),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 577;BA.debugLine="End Sub";
return "";
}
public static String  _icoperfil_click() throws Exception{
 //BA.debugLineNum = 614;BA.debugLine="Private Sub icoPerfil_Click";
 //BA.debugLineNum = 615;BA.debugLine="tabStripMain.ScrollTo(3, True)";
mostCurrent._tabstripmain.ScrollTo((int) (3),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 616;BA.debugLine="End Sub";
return "";
}
public static String  _imgbadgelevel_click() throws Exception{
 //BA.debugLineNum = 2212;BA.debugLine="Sub imgBadgeLevel_Click ()";
 //BA.debugLineNum = 2216;BA.debugLine="End Sub";
return "";
}
public static String  _imgbasura_click() throws Exception{
 //BA.debugLineNum = 1173;BA.debugLine="Private Sub imgBasura_Click";
 //BA.debugLineNum = 1174;BA.debugLine="selectedTest = \"residuos\"";
mostCurrent._selectedtest = "residuos";
 //BA.debugLineNum = 1176;BA.debugLine="lblTituloAnalisis.Text = \"Analizá los residuos\"";
mostCurrent._lbltituloanalisis.setText(BA.ObjectToCharSequence("Analizá los residuos"));
 //BA.debugLineNum = 1177;BA.debugLine="lblDescripcionAnalisis.Text = \"Caminá un poco por";
mostCurrent._lbldescripcionanalisis.setText(BA.ObjectToCharSequence("Caminá un poco por la orilla del río o lago y señalá la cantidad de residuos y basura que encuentres"));
 //BA.debugLineNum = 1178;BA.debugLine="lblPremioAnalisisValor.Text = \"1\"";
mostCurrent._lblpremioanalisisvalor.setText(BA.ObjectToCharSequence("1"));
 //BA.debugLineNum = 1179;BA.debugLine="lblDificultadValor.Text = \"Baja\"";
mostCurrent._lbldificultadvalor.setText(BA.ObjectToCharSequence("Baja"));
 //BA.debugLineNum = 1180;BA.debugLine="lblTiempoAnalisisValor.Text = \"5 minutos\"";
mostCurrent._lbltiempoanalisisvalor.setText(BA.ObjectToCharSequence("5 minutos"));
 //BA.debugLineNum = 1181;BA.debugLine="lblRequisitosAnalisisValor.Text = \"Ninguno\"";
mostCurrent._lblrequisitosanalisisvalor.setText(BA.ObjectToCharSequence("Ninguno"));
 //BA.debugLineNum = 1182;BA.debugLine="ToggleDrawerState";
_toggledrawerstate();
 //BA.debugLineNum = 1183;BA.debugLine="End Sub";
return "";
}
public static String  _imghabitat_click() throws Exception{
 //BA.debugLineNum = 1184;BA.debugLine="Private Sub imgHabitat_Click";
 //BA.debugLineNum = 1185;BA.debugLine="selectedTest = \"habitat\"";
mostCurrent._selectedtest = "habitat";
 //BA.debugLineNum = 1187;BA.debugLine="lblTituloAnalisis.Text = \"Analizá el hábitat\"";
mostCurrent._lbltituloanalisis.setText(BA.ObjectToCharSequence("Analizá el hábitat"));
 //BA.debugLineNum = 1188;BA.debugLine="lblDescripcionAnalisis.Text = \"Contestá unas preg";
mostCurrent._lbldescripcionanalisis.setText(BA.ObjectToCharSequence("Contestá unas preguntas sencillas, tomá unas fotos y analiza el estado del hábitat acuático"));
 //BA.debugLineNum = 1189;BA.debugLine="lblPremioAnalisisValor.Text = \"2\"";
mostCurrent._lblpremioanalisisvalor.setText(BA.ObjectToCharSequence("2"));
 //BA.debugLineNum = 1190;BA.debugLine="lblDificultadValor.Text = \"Baja\"";
mostCurrent._lbldificultadvalor.setText(BA.ObjectToCharSequence("Baja"));
 //BA.debugLineNum = 1191;BA.debugLine="lblTiempoAnalisisValor.Text = \"5-8 minutos\"";
mostCurrent._lbltiempoanalisisvalor.setText(BA.ObjectToCharSequence("5-8 minutos"));
 //BA.debugLineNum = 1192;BA.debugLine="lblRequisitosAnalisisValor.Text = \"Ninguno\"";
mostCurrent._lblrequisitosanalisisvalor.setText(BA.ObjectToCharSequence("Ninguno"));
 //BA.debugLineNum = 1194;BA.debugLine="ToggleDrawerState";
_toggledrawerstate();
 //BA.debugLineNum = 1195;BA.debugLine="End Sub";
return "";
}
public static String  _imghidro_click() throws Exception{
 //BA.debugLineNum = 1160;BA.debugLine="Private Sub imgHidro_Click";
 //BA.debugLineNum = 1162;BA.debugLine="selectedTest = \"hidro\"";
mostCurrent._selectedtest = "hidro";
 //BA.debugLineNum = 1164;BA.debugLine="lblTituloAnalisis.Text = \"Analizá la profundidad\"";
mostCurrent._lbltituloanalisis.setText(BA.ObjectToCharSequence("Analizá la profundidad"));
 //BA.debugLineNum = 1165;BA.debugLine="lblDescripcionAnalisis.Text = \"Contestá unas preg";
mostCurrent._lbldescripcionanalisis.setText(BA.ObjectToCharSequence("Contestá unas preguntas sencillas y estudia la profundidad de tu río"));
 //BA.debugLineNum = 1166;BA.debugLine="lblPremioAnalisisValor.Text = \"1\"";
mostCurrent._lblpremioanalisisvalor.setText(BA.ObjectToCharSequence("1"));
 //BA.debugLineNum = 1167;BA.debugLine="lblDificultadValor.Text = \"Baja\"";
mostCurrent._lbldificultadvalor.setText(BA.ObjectToCharSequence("Baja"));
 //BA.debugLineNum = 1168;BA.debugLine="lblTiempoAnalisisValor.Text = \"5 minutos\"";
mostCurrent._lbltiempoanalisisvalor.setText(BA.ObjectToCharSequence("5 minutos"));
 //BA.debugLineNum = 1169;BA.debugLine="lblRequisitosAnalisisValor.Text = \"Ninguno / Limn";
mostCurrent._lblrequisitosanalisisvalor.setText(BA.ObjectToCharSequence("Ninguno / Limnímetro (si hay en el sitio!)"));
 //BA.debugLineNum = 1170;BA.debugLine="ToggleDrawerState";
_toggledrawerstate();
 //BA.debugLineNum = 1172;BA.debugLine="End Sub";
return "";
}
public static String  _lblcomofuncionaanalizar_click() throws Exception{
 //BA.debugLineNum = 1459;BA.debugLine="Sub lblComoFuncionaAnalizar_Click";
 //BA.debugLineNum = 1462;BA.debugLine="fondogris.Initialize(\"fondogris\")";
mostCurrent._fondogris.Initialize(mostCurrent.activityBA,"fondogris");
 //BA.debugLineNum = 1463;BA.debugLine="fondogris.Color = Colors.ARGB(150,0,0,0)";
mostCurrent._fondogris.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (150),(int) (0),(int) (0),(int) (0)));
 //BA.debugLineNum = 1464;BA.debugLine="Activity.AddView(fondogris, 0, 0, 100%x, 100%y)";
mostCurrent._activity.AddView((android.view.View)(mostCurrent._fondogris.getObject()),(int) (0),(int) (0),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA));
 //BA.debugLineNum = 1466;BA.debugLine="panelComoFunciona.Initialize(\"\")";
mostCurrent._panelcomofunciona.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 1467;BA.debugLine="panelComoFunciona.LoadLayout(\"dialog_comoFunciona";
mostCurrent._panelcomofunciona.LoadLayout("dialog_comoFunciona",mostCurrent.activityBA);
 //BA.debugLineNum = 1468;BA.debugLine="Activity.AddView(panelComoFunciona, 10%x, 20%y, 8";
mostCurrent._activity.AddView((android.view.View)(mostCurrent._panelcomofunciona.getObject()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (10),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (20),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (80),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (80),mostCurrent.activityBA));
 //BA.debugLineNum = 1469;BA.debugLine="End Sub";
return "";
}
public static String  _lblcomofuncionaentendido_click() throws Exception{
 //BA.debugLineNum = 1470;BA.debugLine="Private Sub lblComoFuncionaEntendido_Click";
 //BA.debugLineNum = 1471;BA.debugLine="panelComoFunciona.RemoveView";
mostCurrent._panelcomofunciona.RemoveView();
 //BA.debugLineNum = 1472;BA.debugLine="fondogris.RemoveView";
mostCurrent._fondogris.RemoveView();
 //BA.debugLineNum = 1473;BA.debugLine="End Sub";
return "";
}
public static void  _lblhaygeoref_click() throws Exception{
ResumableSub_lblHayGeoref_Click rsub = new ResumableSub_lblHayGeoref_Click(null);
rsub.resume(processBA, null);
}
public static class ResumableSub_lblHayGeoref_Click extends BA.ResumableSub {
public ResumableSub_lblHayGeoref_Click(appear.pnud.preservamos.form_main parent) {
this.parent = parent;
}
appear.pnud.preservamos.form_main parent;
int _result = 0;
int _timeoutruns = 0;
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
 //BA.debugLineNum = 2536;BA.debugLine="Log(\"latitud:\" & Main.latitud)";
anywheresoftware.b4a.keywords.Common.LogImpl("05177346","latitud:"+parent.mostCurrent._main._latitud /*String*/ ,0);
 //BA.debugLineNum = 2537;BA.debugLine="Log(\"longitud:\" & Main.longitud)";
anywheresoftware.b4a.keywords.Common.LogImpl("05177347","longitud:"+parent.mostCurrent._main._longitud /*String*/ ,0);
 //BA.debugLineNum = 2538;BA.debugLine="Log(\"currentEcoregion:\" & Main.currentEcoregion)";
anywheresoftware.b4a.keywords.Common.LogImpl("05177348","currentEcoregion:"+parent.mostCurrent._main._currentecoregion /*String*/ ,0);
 //BA.debugLineNum = 2540;BA.debugLine="If Main.latitud <> \"\" And Main.longitud <> \"\" The";
if (true) break;

case 1:
//if
this.state = 6;
if ((parent.mostCurrent._main._latitud /*String*/ ).equals("") == false && (parent.mostCurrent._main._longitud /*String*/ ).equals("") == false) { 
this.state = 3;
}else {
this.state = 5;
}if (true) break;

case 3:
//C
this.state = 6;
 //BA.debugLineNum = 2542;BA.debugLine="Msgbox2Async(\"Encontramos tus coordenadas, pero";
anywheresoftware.b4a.keywords.Common.Msgbox2Async(BA.ObjectToCharSequence("Encontramos tus coordenadas, pero no tu ecorregión. ¿Quieres intentar encontrar tu ecorregión automáticamente?"),BA.ObjectToCharSequence("Ecorregión"),"Si","","No",(anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper(), (android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null)),processBA,anywheresoftware.b4a.keywords.Common.False);
 if (true) break;

case 5:
//C
this.state = 6;
 //BA.debugLineNum = 2544;BA.debugLine="Msgbox2Async(\"No encontramos tus coordenadas ni";
anywheresoftware.b4a.keywords.Common.Msgbox2Async(BA.ObjectToCharSequence("No encontramos tus coordenadas ni tu ecorregión. ¿Quieres intentar encontrarlas automáticamente?"),BA.ObjectToCharSequence("Localización"),"Si","","No",(anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper(), (android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null)),processBA,anywheresoftware.b4a.keywords.Common.False);
 if (true) break;

case 6:
//C
this.state = 7;
;
 //BA.debugLineNum = 2546;BA.debugLine="Wait For Msgbox_Result (Result As Int)";
anywheresoftware.b4a.keywords.Common.WaitFor("msgbox_result", processBA, this, null);
this.state = 69;
return;
case 69:
//C
this.state = 7;
_result = (Integer) result[0];
;
 //BA.debugLineNum = 2547;BA.debugLine="If Result = DialogResponse.NEGATIVE Then";
if (true) break;

case 7:
//if
this.state = 68;
if (_result==anywheresoftware.b4a.keywords.Common.DialogResponse.NEGATIVE) { 
this.state = 9;
}else {
this.state = 11;
}if (true) break;

case 9:
//C
this.state = 68;
 //BA.debugLineNum = 2548;BA.debugLine="Return";
if (true) return ;
 if (true) break;

case 11:
//C
this.state = 12;
 //BA.debugLineNum = 2553;BA.debugLine="If gmap.IsInitialized = False Then";
if (true) break;

case 12:
//if
this.state = 67;
if (parent.mostCurrent._gmap.IsInitialized()==anywheresoftware.b4a.keywords.Common.False) { 
this.state = 14;
}else {
this.state = 29;
}if (true) break;

case 14:
//C
this.state = 15;
 //BA.debugLineNum = 2556;BA.debugLine="Wait For MapFragment1_Ready";
anywheresoftware.b4a.keywords.Common.WaitFor("mapfragment1_ready", processBA, this, null);
this.state = 70;
return;
case 70:
//C
this.state = 15;
;
 //BA.debugLineNum = 2557;BA.debugLine="gmap = MapFragment1.GetMap";
parent.mostCurrent._gmap = parent.mostCurrent._mapfragment1.GetMap();
 //BA.debugLineNum = 2558;BA.debugLine="gmap.MyLocationEnabled = True";
parent.mostCurrent._gmap.setMyLocationEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 2559;BA.debugLine="lblEstado.Text = \"Buscando tu ubicación\"";
parent.mostCurrent._lblestado.setText(BA.ObjectToCharSequence("Buscando tu ubicación"));
 //BA.debugLineNum = 2561;BA.debugLine="If gmap.IsInitialized = False Then";
if (true) break;

case 15:
//if
this.state = 24;
if (parent.mostCurrent._gmap.IsInitialized()==anywheresoftware.b4a.keywords.Common.False) { 
this.state = 17;
}else {
this.state = 19;
}if (true) break;

case 17:
//C
this.state = 24;
 //BA.debugLineNum = 2562;BA.debugLine="ToastMessageShow(\"Error initializing map.\", Tr";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Error initializing map."),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 2563;BA.debugLine="Log(\"Error initalizing gmap\")";
anywheresoftware.b4a.keywords.Common.LogImpl("05177373","Error initalizing gmap",0);
 if (true) break;

case 19:
//C
this.state = 20;
 //BA.debugLineNum = 2565;BA.debugLine="Dim timeoutruns As Int = 0";
_timeoutruns = (int) (0);
 //BA.debugLineNum = 2566;BA.debugLine="Do While Not(gmap.MyLocation.IsInitialized) An";
if (true) break;

case 20:
//do while
this.state = 23;
while (anywheresoftware.b4a.keywords.Common.Not(parent.mostCurrent._gmap.getMyLocation().IsInitialized()) && _timeoutruns<400) {
this.state = 22;
if (true) break;
}
if (true) break;

case 22:
//C
this.state = 20;
 //BA.debugLineNum = 2567;BA.debugLine="Sleep(0)";
anywheresoftware.b4a.keywords.Common.Sleep(mostCurrent.activityBA,this,(int) (0));
this.state = 71;
return;
case 71:
//C
this.state = 20;
;
 //BA.debugLineNum = 2568;BA.debugLine="timeoutruns = timeoutruns + 1";
_timeoutruns = (int) (_timeoutruns+1);
 if (true) break;

case 23:
//C
this.state = 24;
;
 if (true) break;
;
 //BA.debugLineNum = 2573;BA.debugLine="If gmap.MyLocation.IsInitialized Then";

case 24:
//if
this.state = 27;
if (parent.mostCurrent._gmap.getMyLocation().IsInitialized()) { 
this.state = 26;
}if (true) break;

case 26:
//C
this.state = 27;
 //BA.debugLineNum = 2574;BA.debugLine="Main.latitud = gmap.MyLocation.Latitude";
parent.mostCurrent._main._latitud /*String*/  = BA.NumberToString(parent.mostCurrent._gmap.getMyLocation().getLatitude());
 //BA.debugLineNum = 2575;BA.debugLine="Main.longitud = gmap.MyLocation.Longitude";
parent.mostCurrent._main._longitud /*String*/  = BA.NumberToString(parent.mostCurrent._gmap.getMyLocation().getLongitude());
 //BA.debugLineNum = 2576;BA.debugLine="Log(\"Georreferenciación OK\")";
anywheresoftware.b4a.keywords.Common.LogImpl("05177386","Georreferenciación OK",0);
 //BA.debugLineNum = 2577;BA.debugLine="Log(\"latitud: \" & Main.latitud & \"//longitud:";
anywheresoftware.b4a.keywords.Common.LogImpl("05177387","latitud: "+parent.mostCurrent._main._latitud /*String*/ +"//longitud: "+parent.mostCurrent._main._longitud /*String*/ ,0);
 if (true) break;

case 27:
//C
this.state = 67;
;
 if (true) break;

case 29:
//C
this.state = 30;
 //BA.debugLineNum = 2582;BA.debugLine="If gmap.MyLocation.IsInitialized Then";
if (true) break;

case 30:
//if
this.state = 44;
if (parent.mostCurrent._gmap.getMyLocation().IsInitialized()) { 
this.state = 32;
}else {
this.state = 34;
}if (true) break;

case 32:
//C
this.state = 44;
 //BA.debugLineNum = 2583;BA.debugLine="Log(\"coordenadas encontradas\")";
anywheresoftware.b4a.keywords.Common.LogImpl("05177393","coordenadas encontradas",0);
 //BA.debugLineNum = 2584;BA.debugLine="Main.latitud = gmap.MyLocation.Latitude";
parent.mostCurrent._main._latitud /*String*/  = BA.NumberToString(parent.mostCurrent._gmap.getMyLocation().getLatitude());
 //BA.debugLineNum = 2585;BA.debugLine="Main.longitud = gmap.MyLocation.Longitude";
parent.mostCurrent._main._longitud /*String*/  = BA.NumberToString(parent.mostCurrent._gmap.getMyLocation().getLongitude());
 //BA.debugLineNum = 2586;BA.debugLine="ToastMessageShow(\"Coordenadas encontradas, bus";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Coordenadas encontradas, buscando ecorregión..."),anywheresoftware.b4a.keywords.Common.False);
 if (true) break;

case 34:
//C
this.state = 35;
 //BA.debugLineNum = 2589;BA.debugLine="ProgressDialogShow2(\"Buscando tu ubicación...\"";
anywheresoftware.b4a.keywords.Common.ProgressDialogShow2(mostCurrent.activityBA,BA.ObjectToCharSequence("Buscando tu ubicación..."),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 2590;BA.debugLine="gmap = MapFragment1.GetMap";
parent.mostCurrent._gmap = parent.mostCurrent._mapfragment1.GetMap();
 //BA.debugLineNum = 2591;BA.debugLine="gmap.MyLocationEnabled = True";
parent.mostCurrent._gmap.setMyLocationEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 2593;BA.debugLine="Dim timeoutruns As Int = 0";
_timeoutruns = (int) (0);
 //BA.debugLineNum = 2594;BA.debugLine="Do While Not(gmap.MyLocation.IsInitialized) An";
if (true) break;

case 35:
//do while
this.state = 38;
while (anywheresoftware.b4a.keywords.Common.Not(parent.mostCurrent._gmap.getMyLocation().IsInitialized()) && _timeoutruns<400) {
this.state = 37;
if (true) break;
}
if (true) break;

case 37:
//C
this.state = 35;
 //BA.debugLineNum = 2595;BA.debugLine="Sleep(0)";
anywheresoftware.b4a.keywords.Common.Sleep(mostCurrent.activityBA,this,(int) (0));
this.state = 72;
return;
case 72:
//C
this.state = 35;
;
 //BA.debugLineNum = 2596;BA.debugLine="timeoutruns = timeoutruns + 1";
_timeoutruns = (int) (_timeoutruns+1);
 if (true) break;
;
 //BA.debugLineNum = 2600;BA.debugLine="If gmap.MyLocation.IsInitialized Then";

case 38:
//if
this.state = 43;
if (parent.mostCurrent._gmap.getMyLocation().IsInitialized()) { 
this.state = 40;
}else {
this.state = 42;
}if (true) break;

case 40:
//C
this.state = 43;
 //BA.debugLineNum = 2601;BA.debugLine="Main.latitud = gmap.MyLocation.Latitude";
parent.mostCurrent._main._latitud /*String*/  = BA.NumberToString(parent.mostCurrent._gmap.getMyLocation().getLatitude());
 //BA.debugLineNum = 2602;BA.debugLine="Main.longitud = gmap.MyLocation.Longitude";
parent.mostCurrent._main._longitud /*String*/  = BA.NumberToString(parent.mostCurrent._gmap.getMyLocation().getLongitude());
 //BA.debugLineNum = 2603;BA.debugLine="Log(\"Georreferenciación OK\")";
anywheresoftware.b4a.keywords.Common.LogImpl("05177413","Georreferenciación OK",0);
 //BA.debugLineNum = 2604;BA.debugLine="Log(\"latitud: \" & Main.latitud & \"//longitud:";
anywheresoftware.b4a.keywords.Common.LogImpl("05177414","latitud: "+parent.mostCurrent._main._latitud /*String*/ +"//longitud: "+parent.mostCurrent._main._longitud /*String*/ ,0);
 if (true) break;

case 42:
//C
this.state = 43;
 //BA.debugLineNum = 2606;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 //BA.debugLineNum = 2607;BA.debugLine="ToastMessageShow(\"No se pudo encontrar tu ubi";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("No se pudo encontrar tu ubicación..."),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 2608;BA.debugLine="Return";
if (true) return ;
 if (true) break;

case 43:
//C
this.state = 44;
;
 if (true) break;
;
 //BA.debugLineNum = 2616;BA.debugLine="If Main.latitud <> \"\" And Main.latitud <> \"null";

case 44:
//if
this.state = 55;
if ((parent.mostCurrent._main._latitud /*String*/ ).equals("") == false && (parent.mostCurrent._main._latitud /*String*/ ).equals("null") == false && (parent.mostCurrent._main._longitud /*String*/ ).equals("") == false && (parent.mostCurrent._main._longitud /*String*/ ).equals("null") == false) { 
this.state = 46;
}else {
this.state = 54;
}if (true) break;

case 46:
//C
this.state = 47;
 //BA.debugLineNum = 2617;BA.debugLine="ProgressDialogShow2(\"Buscando municipio\", Fals";
anywheresoftware.b4a.keywords.Common.ProgressDialogShow2(mostCurrent.activityBA,BA.ObjectToCharSequence("Buscando municipio"),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 2618;BA.debugLine="Wait For (buscarReverseGeoref) Complete (Compl";
anywheresoftware.b4a.keywords.Common.WaitFor("complete", processBA, this, _buscarreversegeoref());
this.state = 73;
return;
case 73:
//C
this.state = 47;
_completed = (Boolean) result[0];
;
 //BA.debugLineNum = 2619;BA.debugLine="If Completed = True Then";
if (true) break;

case 47:
//if
this.state = 52;
if (_completed==anywheresoftware.b4a.keywords.Common.True) { 
this.state = 49;
}else {
this.state = 51;
}if (true) break;

case 49:
//C
this.state = 52;
 //BA.debugLineNum = 2620;BA.debugLine="Log(\"ReverseGeo done\")";
anywheresoftware.b4a.keywords.Common.LogImpl("05177430","ReverseGeo done",0);
 if (true) break;

case 51:
//C
this.state = 52;
 //BA.debugLineNum = 2622;BA.debugLine="ToastMessageShow(\"No se reconoció tu municipi";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("No se reconoció tu municipio"),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 2623;BA.debugLine="Log(\"ReverseGeo failed\")";
anywheresoftware.b4a.keywords.Common.LogImpl("05177433","ReverseGeo failed",0);
 if (true) break;

case 52:
//C
this.state = 55;
;
 if (true) break;

case 54:
//C
this.state = 55;
 //BA.debugLineNum = 2626;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 //BA.debugLineNum = 2627;BA.debugLine="ToastMessageShow(\"No se encontraron tus coorde";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("No se encontraron tus coordenadas, no se puede saber tu ecorregión o municipio"),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 2628;BA.debugLine="Return";
if (true) return ;
 if (true) break;
;
 //BA.debugLineNum = 2632;BA.debugLine="If Main.latitud <> \"\" And Main.latitud <> \"null";

case 55:
//if
this.state = 66;
if ((parent.mostCurrent._main._latitud /*String*/ ).equals("") == false && (parent.mostCurrent._main._latitud /*String*/ ).equals("null") == false && (parent.mostCurrent._main._longitud /*String*/ ).equals("") == false && (parent.mostCurrent._main._longitud /*String*/ ).equals("null") == false) { 
this.state = 57;
}else {
this.state = 65;
}if (true) break;

case 57:
//C
this.state = 58;
 //BA.debugLineNum = 2633;BA.debugLine="Log(\"Buscando ecoregion\")";
anywheresoftware.b4a.keywords.Common.LogImpl("05177443","Buscando ecoregion",0);
 //BA.debugLineNum = 2634;BA.debugLine="ProgressDialogShow2(\"Buscando ecorregión\", Fal";
anywheresoftware.b4a.keywords.Common.ProgressDialogShow2(mostCurrent.activityBA,BA.ObjectToCharSequence("Buscando ecorregión"),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 2635;BA.debugLine="Wait For (Buscar_Ecoregion) Complete(Completed";
anywheresoftware.b4a.keywords.Common.WaitFor("complete", processBA, this, _buscar_ecoregion());
this.state = 74;
return;
case 74:
//C
this.state = 58;
_completed = (Boolean) result[0];
;
 //BA.debugLineNum = 2636;BA.debugLine="If Completed = False Then";
if (true) break;

case 58:
//if
this.state = 63;
if (_completed==anywheresoftware.b4a.keywords.Common.False) { 
this.state = 60;
}else {
this.state = 62;
}if (true) break;

case 60:
//C
this.state = 63;
 //BA.debugLineNum = 2638;BA.debugLine="Main.currentEcoregion = \"\"";
parent.mostCurrent._main._currentecoregion /*String*/  = "";
 //BA.debugLineNum = 2639;BA.debugLine="Main.currentSubEcoregion = \"\"";
parent.mostCurrent._main._currentsubecoregion /*String*/  = "";
 //BA.debugLineNum = 2640;BA.debugLine="lblHayGeoref.Visible = True";
parent.mostCurrent._lblhaygeoref.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 2641;BA.debugLine="lblEcorregionTitle.Text = \"No se detectó tu e";
parent.mostCurrent._lblecorregiontitle.setText(BA.ObjectToCharSequence("No se detectó tu ecorregión..."));
 //BA.debugLineNum = 2642;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 if (true) break;

case 62:
//C
this.state = 63;
 //BA.debugLineNum = 2644;BA.debugLine="lblHayGeoref.Visible = False";
parent.mostCurrent._lblhaygeoref.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 2645;BA.debugLine="lblEcorregionTitle.Text = \"Estás en la ecorre";
parent.mostCurrent._lblecorregiontitle.setText(BA.ObjectToCharSequence("Estás en la ecorregión "+parent.mostCurrent._main._currentecoregion /*String*/ ));
 //BA.debugLineNum = 2646;BA.debugLine="setEcoregion";
_setecoregion();
 //BA.debugLineNum = 2647;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 if (true) break;

case 63:
//C
this.state = 66;
;
 if (true) break;

case 65:
//C
this.state = 66;
 //BA.debugLineNum = 2650;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 //BA.debugLineNum = 2651;BA.debugLine="ToastMessageShow(\"No se encontraron tus coorde";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("No se encontraron tus coordenadas, no se puede saber tu ecorregión o municipio"),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 2652;BA.debugLine="Return";
if (true) return ;
 if (true) break;

case 66:
//C
this.state = 67;
;
 if (true) break;

case 67:
//C
this.state = 68;
;
 if (true) break;

case 68:
//C
this.state = -1;
;
 //BA.debugLineNum = 2660;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public static void  _lblhayinternet_click() throws Exception{
ResumableSub_lblHayInternet_Click rsub = new ResumableSub_lblHayInternet_Click(null);
rsub.resume(processBA, null);
}
public static class ResumableSub_lblHayInternet_Click extends BA.ResumableSub {
public ResumableSub_lblHayInternet_Click(appear.pnud.preservamos.form_main parent) {
this.parent = parent;
}
appear.pnud.preservamos.form_main parent;
int _result = 0;
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
 //BA.debugLineNum = 2402;BA.debugLine="Msgbox2Async(\"¿Quieres intentar conectar PreserVa";
anywheresoftware.b4a.keywords.Common.Msgbox2Async(BA.ObjectToCharSequence("¿Quieres intentar conectar PreserVamos a internet?"),BA.ObjectToCharSequence("¿Tienes conexión a internet?"),"Si","","No",(anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper(), (android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null)),processBA,anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 2403;BA.debugLine="Wait For Msgbox_Result (Result As Int)";
anywheresoftware.b4a.keywords.Common.WaitFor("msgbox_result", processBA, this, null);
this.state = 23;
return;
case 23:
//C
this.state = 1;
_result = (Integer) result[0];
;
 //BA.debugLineNum = 2404;BA.debugLine="If Result = DialogResponse.POSITIVE Then";
if (true) break;

case 1:
//if
this.state = 22;
if (_result==anywheresoftware.b4a.keywords.Common.DialogResponse.POSITIVE) { 
this.state = 3;
}if (true) break;

case 3:
//C
this.state = 4;
 //BA.debugLineNum = 2406;BA.debugLine="Wait For (TestInternet) Complete (Completed As B";
anywheresoftware.b4a.keywords.Common.WaitFor("complete", processBA, this, _testinternet());
this.state = 24;
return;
case 24:
//C
this.state = 4;
_completed = (Boolean) result[0];
;
 //BA.debugLineNum = 2408;BA.debugLine="If Completed = False Then";
if (true) break;

case 4:
//if
this.state = 21;
if (_completed==anywheresoftware.b4a.keywords.Common.False) { 
this.state = 6;
}else {
this.state = 8;
}if (true) break;

case 6:
//C
this.state = 21;
 //BA.debugLineNum = 2409;BA.debugLine="ToastMessageShow(\"No hay conexión a internet\",";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("No hay conexión a internet"),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 2410;BA.debugLine="Main.hayinternet = False";
parent.mostCurrent._main._hayinternet /*boolean*/  = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 2411;BA.debugLine="lblHayInternet.Visible = True";
parent.mostCurrent._lblhayinternet.setVisible(anywheresoftware.b4a.keywords.Common.True);
 if (true) break;

case 8:
//C
this.state = 9;
 //BA.debugLineNum = 2413;BA.debugLine="ToastMessageShow(\"Hay conexión a internet!\", Fa";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Hay conexión a internet!"),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 2414;BA.debugLine="Main.hayinternet = True";
parent.mostCurrent._main._hayinternet /*boolean*/  = anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 2415;BA.debugLine="lblHayInternet.Visible = False";
parent.mostCurrent._lblhayinternet.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 2418;BA.debugLine="If Main.latitud <> \"\" And Main.latitud <> \"null";
if (true) break;

case 9:
//if
this.state = 20;
if ((parent.mostCurrent._main._latitud /*String*/ ).equals("") == false && (parent.mostCurrent._main._latitud /*String*/ ).equals("null") == false && (parent.mostCurrent._main._longitud /*String*/ ).equals("") == false && (parent.mostCurrent._main._longitud /*String*/ ).equals("null") == false) { 
this.state = 11;
}else {
this.state = 19;
}if (true) break;

case 11:
//C
this.state = 12;
 //BA.debugLineNum = 2419;BA.debugLine="Log(\"Buscando ecoregion\")";
anywheresoftware.b4a.keywords.Common.LogImpl("04980755","Buscando ecoregion",0);
 //BA.debugLineNum = 2420;BA.debugLine="ProgressDialogShow2(\"Buscando ecorregión\", Fal";
anywheresoftware.b4a.keywords.Common.ProgressDialogShow2(mostCurrent.activityBA,BA.ObjectToCharSequence("Buscando ecorregión"),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 2421;BA.debugLine="Wait For (Buscar_Ecoregion) Complete(Completed";
anywheresoftware.b4a.keywords.Common.WaitFor("complete", processBA, this, _buscar_ecoregion());
this.state = 25;
return;
case 25:
//C
this.state = 12;
_completed = (Boolean) result[0];
;
 //BA.debugLineNum = 2422;BA.debugLine="If Completed = False Then";
if (true) break;

case 12:
//if
this.state = 17;
if (_completed==anywheresoftware.b4a.keywords.Common.False) { 
this.state = 14;
}else {
this.state = 16;
}if (true) break;

case 14:
//C
this.state = 17;
 //BA.debugLineNum = 2424;BA.debugLine="Main.currentEcoregion = \"\"";
parent.mostCurrent._main._currentecoregion /*String*/  = "";
 //BA.debugLineNum = 2425;BA.debugLine="Main.currentSubEcoregion = \"\"";
parent.mostCurrent._main._currentsubecoregion /*String*/  = "";
 //BA.debugLineNum = 2426;BA.debugLine="lblHayGeoref.Visible = True";
parent.mostCurrent._lblhaygeoref.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 2427;BA.debugLine="lblEcorregionTitle.Text = \"No se detectó tu e";
parent.mostCurrent._lblecorregiontitle.setText(BA.ObjectToCharSequence("No se detectó tu ecorregión..."));
 //BA.debugLineNum = 2428;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 if (true) break;

case 16:
//C
this.state = 17;
 //BA.debugLineNum = 2430;BA.debugLine="lblHayGeoref.Visible = False";
parent.mostCurrent._lblhaygeoref.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 2431;BA.debugLine="lblEcorregionTitle.Text = \"Estás en la ecorre";
parent.mostCurrent._lblecorregiontitle.setText(BA.ObjectToCharSequence("Estás en la ecorregión "+parent.mostCurrent._main._currentecoregion /*String*/ ));
 //BA.debugLineNum = 2432;BA.debugLine="setEcoregion";
_setecoregion();
 //BA.debugLineNum = 2433;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 if (true) break;

case 17:
//C
this.state = 20;
;
 if (true) break;

case 19:
//C
this.state = 20;
 //BA.debugLineNum = 2436;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 //BA.debugLineNum = 2437;BA.debugLine="ToastMessageShow(\"No se encontraron tus coorde";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("No se encontraron tus coordenadas, no se puede saber tu ecorregión o municipio"),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 2438;BA.debugLine="Return";
if (true) return ;
 if (true) break;

case 20:
//C
this.state = 21;
;
 if (true) break;

case 21:
//C
this.state = 22;
;
 if (true) break;

case 22:
//C
this.state = -1;
;
 //BA.debugLineNum = 2445;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public static String  _lblmunicipio_puntos_totales_click() throws Exception{
 //BA.debugLineNum = 1817;BA.debugLine="Sub lblMunicipio_puntos_totales_Click";
 //BA.debugLineNum = 1820;BA.debugLine="fondogris.Initialize(\"fondogris\")";
mostCurrent._fondogris.Initialize(mostCurrent.activityBA,"fondogris");
 //BA.debugLineNum = 1821;BA.debugLine="fondogris.Color = Colors.ARGB(150,0,0,0)";
mostCurrent._fondogris.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (150),(int) (0),(int) (0),(int) (0)));
 //BA.debugLineNum = 1822;BA.debugLine="Activity.AddView(fondogris, 0, 0, 100%x, 100%y)";
mostCurrent._activity.AddView((android.view.View)(mostCurrent._fondogris.getObject()),(int) (0),(int) (0),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA));
 //BA.debugLineNum = 1824;BA.debugLine="panelComoFunciona.Initialize(\"\")";
mostCurrent._panelcomofunciona.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 1825;BA.debugLine="panelComoFunciona.LoadLayout(\"dialog_comoFunciona";
mostCurrent._panelcomofunciona.LoadLayout("dialog_comoFunciona",mostCurrent.activityBA);
 //BA.debugLineNum = 1826;BA.debugLine="Activity.AddView(panelComoFunciona, 10%x, 20%y, 8";
mostCurrent._activity.AddView((android.view.View)(mostCurrent._panelcomofunciona.getObject()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (10),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (20),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (80),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (80),mostCurrent.activityBA));
 //BA.debugLineNum = 1827;BA.debugLine="lblComoFuncionaContenido.text = \"Cada vez que un";
mostCurrent._lblcomofuncionacontenido.setText(BA.ObjectToCharSequence("Cada vez que un miembro de este municipio realiza un análisis del hábitat, suma puntos para toda la comunidad!"+anywheresoftware.b4a.keywords.Common.CRLF+anywheresoftware.b4a.keywords.Common.CRLF+"Esos puntos podrán ser canjeados por beneficios para el municipio :)"));
 //BA.debugLineNum = 1828;BA.debugLine="lblComoFuncionaTitle.text = \"Esfuerzo colaborativ";
mostCurrent._lblcomofuncionatitle.setText(BA.ObjectToCharSequence("Esfuerzo colaborativo"));
 //BA.debugLineNum = 1829;BA.debugLine="End Sub";
return "";
}
public static String  _lblnocompartir_click() throws Exception{
 //BA.debugLineNum = 2375;BA.debugLine="Sub lblNoCompartir_Click";
 //BA.debugLineNum = 2376;BA.debugLine="pnlShare.RemoveView";
mostCurrent._pnlshare.RemoveView();
 //BA.debugLineNum = 2377;BA.debugLine="fondogris_share.RemoveView";
mostCurrent._fondogris_share.RemoveView();
 //BA.debugLineNum = 2378;BA.debugLine="End Sub";
return "";
}
public static String  _lstachievements_itemclick(int _position,Object _value) throws Exception{
 //BA.debugLineNum = 2217;BA.debugLine="Sub lstAchievements_ItemClick (Position As Int, Va";
 //BA.debugLineNum = 2218;BA.debugLine="If Value = \"Novato\" Then";
if ((_value).equals((Object)("Novato"))) { 
 //BA.debugLineNum = 2219;BA.debugLine="ToastMessageShow(\"Completaste un análisis ambien";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Completaste un análisis ambiental!"),anywheresoftware.b4a.keywords.Common.False);
 }else if((_value).equals((Object)("Aprendiz"))) { 
 //BA.debugLineNum = 2221;BA.debugLine="ToastMessageShow(\"Completaste tres análisis ambi";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Completaste tres análisis ambientales!"),anywheresoftware.b4a.keywords.Common.False);
 }else if((_value).equals((Object)("Aventurero"))) { 
 //BA.debugLineNum = 2223;BA.debugLine="ToastMessageShow(\"Completaste cinco análisis amb";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Completaste cinco análisis ambientales!"),anywheresoftware.b4a.keywords.Common.False);
 }else if((_value).equals((Object)("Explorador"))) { 
 //BA.debugLineNum = 2225;BA.debugLine="ToastMessageShow(\"Completaste quince análisis am";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Completaste quince análisis ambientales!"),anywheresoftware.b4a.keywords.Common.False);
 }else if((_value).equals((Object)("Legendario"))) { 
 //BA.debugLineNum = 2227;BA.debugLine="ToastMessageShow(\"Completaste treinta análisis a";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Completaste treinta análisis ambiental!"),anywheresoftware.b4a.keywords.Common.False);
 }else if((_value).equals((Object)("hidro-1"))) { 
 //BA.debugLineNum = 2229;BA.debugLine="ToastMessageShow(\"Completaste un análisis de hid";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Completaste un análisis de hidrología!"),anywheresoftware.b4a.keywords.Common.False);
 }else if((_value).equals((Object)("hidro-2"))) { 
 //BA.debugLineNum = 2231;BA.debugLine="ToastMessageShow(\"Completaste tres análisis de h";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Completaste tres análisis de hidrología!"),anywheresoftware.b4a.keywords.Common.False);
 }else if((_value).equals((Object)("hidro-3"))) { 
 //BA.debugLineNum = 2233;BA.debugLine="ToastMessageShow(\"Completaste diez análisis de h";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Completaste diez análisis de hidrología!"),anywheresoftware.b4a.keywords.Common.False);
 }else if((_value).equals((Object)("residuos-1"))) { 
 //BA.debugLineNum = 2235;BA.debugLine="ToastMessageShow(\"Completaste un análisis de res";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Completaste un análisis de residuos!"),anywheresoftware.b4a.keywords.Common.False);
 }else if((_value).equals((Object)("residuos-2"))) { 
 //BA.debugLineNum = 2237;BA.debugLine="ToastMessageShow(\"Completaste tres análisis de r";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Completaste tres análisis de residuos!"),anywheresoftware.b4a.keywords.Common.False);
 }else if((_value).equals((Object)("residuos-3"))) { 
 //BA.debugLineNum = 2239;BA.debugLine="ToastMessageShow(\"Completaste diez análisis de r";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Completaste diez análisis de residuos!"),anywheresoftware.b4a.keywords.Common.False);
 }else if((_value).equals((Object)("llanura"))) { 
 //BA.debugLineNum = 2241;BA.debugLine="ToastMessageShow(\"Completaste tu primera evaluac";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Completaste tu primera evaluación de un río de llanura"),anywheresoftware.b4a.keywords.Common.False);
 }else if((_value).equals((Object)("laguna"))) { 
 //BA.debugLineNum = 2243;BA.debugLine="ToastMessageShow(\"Completaste tu primera evaluac";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Completaste tu primera evaluación de una laguna"),anywheresoftware.b4a.keywords.Common.False);
 }else if((_value).equals((Object)("montana"))) { 
 //BA.debugLineNum = 2245;BA.debugLine="ToastMessageShow(\"Completaste tu primera evaluac";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Completaste tu primera evaluación de un río de sierras de Córdoba"),anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 2252;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 6;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 9;BA.debugLine="Dim IsGuest As Boolean";
_isguest = false;
 //BA.debugLineNum = 10;BA.debugLine="Dim fullidcurrentproject As String";
_fullidcurrentproject = "";
 //BA.debugLineNum = 12;BA.debugLine="Dim georef As Geocoder";
_georef = new uk.co.martinpearman.b4a.location.GeocoderWrapper();
 //BA.debugLineNum = 13;BA.debugLine="Dim Geopartido As String";
_geopartido = "";
 //BA.debugLineNum = 14;BA.debugLine="End Sub";
return "";
}
public static void  _setecoregion() throws Exception{
ResumableSub_setEcoregion rsub = new ResumableSub_setEcoregion(null);
rsub.resume(processBA, null);
}
public static class ResumableSub_setEcoregion extends BA.ResumableSub {
public ResumableSub_setEcoregion(appear.pnud.preservamos.form_main parent) {
this.parent = parent;
}
appear.pnud.preservamos.form_main parent;
anywheresoftware.b4a.objects.B4XViewWrapper _x = null;

@Override
public void resume(BA ba, Object[] result) throws Exception{

    while (true) {
        switch (state) {
            case -1:
return;

case 0:
//C
this.state = 1;
 //BA.debugLineNum = 984;BA.debugLine="lblEcorregionTitle.Text = \"Estás en la ecorregión";
parent.mostCurrent._lblecorregiontitle.setText(BA.ObjectToCharSequence("Estás en la ecorregión "+anywheresoftware.b4a.keywords.Common.CRLF+parent.mostCurrent._main._currentecoregion /*String*/ ));
 //BA.debugLineNum = 985;BA.debugLine="lblEcorregionSubTitle.Text = \"Herramientas para t";
parent.mostCurrent._lblecorregionsubtitle.setText(BA.ObjectToCharSequence("Herramientas para tu ecorregión"));
 //BA.debugLineNum = 986;BA.debugLine="Dim x As B4XView = lblEcoregionBanda 'XUI library";
_x = new anywheresoftware.b4a.objects.B4XViewWrapper();
_x = (anywheresoftware.b4a.objects.B4XViewWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.B4XViewWrapper(), (java.lang.Object)(parent.mostCurrent._lblecoregionbanda.getObject()));
 //BA.debugLineNum = 987;BA.debugLine="Log(\"Setting ecorregion\")";
anywheresoftware.b4a.keywords.Common.LogImpl("02621444","Setting ecorregion",0);
 //BA.debugLineNum = 988;BA.debugLine="If Main.currentEcoregion = \"Altos Andes\" Then";
if (true) break;

case 1:
//if
this.state = 34;
if ((parent.mostCurrent._main._currentecoregion /*String*/ ).equals("Altos Andes")) { 
this.state = 3;
}else if((parent.mostCurrent._main._currentecoregion /*String*/ ).equals("Puna")) { 
this.state = 5;
}else if((parent.mostCurrent._main._currentecoregion /*String*/ ).equals("Monte de sierras y bolsones")) { 
this.state = 7;
}else if((parent.mostCurrent._main._currentecoregion /*String*/ ).equals("Selva de las yungas")) { 
this.state = 9;
}else if((parent.mostCurrent._main._currentecoregion /*String*/ ).equals("Chaco seco")) { 
this.state = 11;
}else if((parent.mostCurrent._main._currentecoregion /*String*/ ).equals("Chaco humedo")) { 
this.state = 13;
}else if((parent.mostCurrent._main._currentecoregion /*String*/ ).equals("Selva paranaense")) { 
this.state = 15;
}else if((parent.mostCurrent._main._currentecoregion /*String*/ ).equals("Esteros del Ibera")) { 
this.state = 17;
}else if((parent.mostCurrent._main._currentecoregion /*String*/ ).equals("Campos y malezales")) { 
this.state = 19;
}else if((parent.mostCurrent._main._currentecoregion /*String*/ ).equals("Delta e islas del Parana")) { 
this.state = 21;
}else if((parent.mostCurrent._main._currentecoregion /*String*/ ).equals("Espinal")) { 
this.state = 23;
}else if((parent.mostCurrent._main._currentecoregion /*String*/ ).equals("Pampa")) { 
this.state = 25;
}else if((parent.mostCurrent._main._currentecoregion /*String*/ ).equals("Monte de llanuras y mesetas")) { 
this.state = 27;
}else if((parent.mostCurrent._main._currentecoregion /*String*/ ).equals("Estepa patagonica")) { 
this.state = 29;
}else if((parent.mostCurrent._main._currentecoregion /*String*/ ).equals("Bosques patagonicos")) { 
this.state = 31;
}else if((parent.mostCurrent._main._currentecoregion /*String*/ ).equals("")) { 
this.state = 33;
}if (true) break;

case 3:
//C
this.state = 34;
 //BA.debugLineNum = 990;BA.debugLine="x.SetColorAnimated(300, Colors.White, Colors.ARG";
_x.SetColorAnimated((int) (300),anywheresoftware.b4a.keywords.Common.Colors.White,anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (95),(int) (186),(int) (191)));
 //BA.debugLineNum = 991;BA.debugLine="lblEcorregionTitle.TextColor = Colors.ARGB(255,9";
parent.mostCurrent._lblecorregiontitle.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (95),(int) (186),(int) (191)));
 //BA.debugLineNum = 993;BA.debugLine="MsgboxAsync(\"No hay herramientas especializadas";
anywheresoftware.b4a.keywords.Common.MsgboxAsync(BA.ObjectToCharSequence("No hay herramientas especializadas para tu ecorregión aún. Pero podrás usar las herramientas generales de PreserVamos!"),BA.ObjectToCharSequence("Ecorregión aún no estudiada"),processBA);
 //BA.debugLineNum = 994;BA.debugLine="pnlHabitat.Visible = True";
parent.mostCurrent._pnlhabitat.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 995;BA.debugLine="pnlHidro.Visible = True";
parent.mostCurrent._pnlhidro.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 996;BA.debugLine="pnlResiduos.Visible = True";
parent.mostCurrent._pnlresiduos.setVisible(anywheresoftware.b4a.keywords.Common.True);
 if (true) break;

case 5:
//C
this.state = 34;
 //BA.debugLineNum = 999;BA.debugLine="x.SetColorAnimated(300, Colors.White, Colors.ARG";
_x.SetColorAnimated((int) (300),anywheresoftware.b4a.keywords.Common.Colors.White,anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (95),(int) (186),(int) (191)));
 //BA.debugLineNum = 1000;BA.debugLine="lblEcorregionTitle.TextColor =Colors.ARGB(255,95";
parent.mostCurrent._lblecorregiontitle.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (95),(int) (186),(int) (191)));
 //BA.debugLineNum = 1002;BA.debugLine="MsgboxAsync(\"No hay herramientas especializadas";
anywheresoftware.b4a.keywords.Common.MsgboxAsync(BA.ObjectToCharSequence("No hay herramientas especializadas para tu ecorregión aún. Pero podrás usar las herramientas generales de PreserVamos!"),BA.ObjectToCharSequence("Ecorregión aún no estudiada"),processBA);
 //BA.debugLineNum = 1003;BA.debugLine="pnlHabitat.Visible = True";
parent.mostCurrent._pnlhabitat.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1004;BA.debugLine="pnlHidro.Visible = True";
parent.mostCurrent._pnlhidro.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1005;BA.debugLine="pnlResiduos.Visible = True";
parent.mostCurrent._pnlresiduos.setVisible(anywheresoftware.b4a.keywords.Common.True);
 if (true) break;

case 7:
//C
this.state = 34;
 //BA.debugLineNum = 1008;BA.debugLine="x.SetColorAnimated(300, Colors.White, Colors.ARG";
_x.SetColorAnimated((int) (300),anywheresoftware.b4a.keywords.Common.Colors.White,anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (227),(int) (172),(int) (108)));
 //BA.debugLineNum = 1009;BA.debugLine="lblEcorregionTitle.TextColor =Colors.ARGB(255,22";
parent.mostCurrent._lblecorregiontitle.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (227),(int) (172),(int) (108)));
 //BA.debugLineNum = 1011;BA.debugLine="MsgboxAsync(\"No hay herramientas especializadas";
anywheresoftware.b4a.keywords.Common.MsgboxAsync(BA.ObjectToCharSequence("No hay herramientas especializadas para tu ecorregión aún. Pero podrás usar las herramientas generales de PreserVamos!"),BA.ObjectToCharSequence("Ecorregión aún no estudiada"),processBA);
 //BA.debugLineNum = 1012;BA.debugLine="pnlHabitat.Visible = True";
parent.mostCurrent._pnlhabitat.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1013;BA.debugLine="pnlHidro.Visible = True";
parent.mostCurrent._pnlhidro.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1014;BA.debugLine="pnlResiduos.Visible = True";
parent.mostCurrent._pnlresiduos.setVisible(anywheresoftware.b4a.keywords.Common.True);
 if (true) break;

case 9:
//C
this.state = 34;
 //BA.debugLineNum = 1017;BA.debugLine="x.SetColorAnimated(300, Colors.White, Colors.ARG";
_x.SetColorAnimated((int) (300),anywheresoftware.b4a.keywords.Common.Colors.White,anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (237),(int) (0),(int) (140)));
 //BA.debugLineNum = 1018;BA.debugLine="lblEcorregionTitle.TextColor = Colors.ARGB(255,2";
parent.mostCurrent._lblecorregiontitle.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (237),(int) (0),(int) (140)));
 //BA.debugLineNum = 1020;BA.debugLine="MsgboxAsync(\"No hay herramientas especializadas";
anywheresoftware.b4a.keywords.Common.MsgboxAsync(BA.ObjectToCharSequence("No hay herramientas especializadas para tu ecorregión aún. Pero podrás usar las herramientas generales de PreserVamos!"),BA.ObjectToCharSequence("Ecorregión aún no estudiada"),processBA);
 //BA.debugLineNum = 1021;BA.debugLine="pnlHabitat.Visible = True";
parent.mostCurrent._pnlhabitat.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1022;BA.debugLine="pnlHidro.Visible = True";
parent.mostCurrent._pnlhidro.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1023;BA.debugLine="pnlResiduos.Visible = True";
parent.mostCurrent._pnlresiduos.setVisible(anywheresoftware.b4a.keywords.Common.True);
 if (true) break;

case 11:
//C
this.state = 34;
 //BA.debugLineNum = 1026;BA.debugLine="x.SetColorAnimated(300, Colors.White, Colors.ARG";
_x.SetColorAnimated((int) (300),anywheresoftware.b4a.keywords.Common.Colors.White,anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (246),(int) (130),(int) (31)));
 //BA.debugLineNum = 1027;BA.debugLine="lblEcorregionTitle.TextColor = Colors.ARGB(255,2";
parent.mostCurrent._lblecorregiontitle.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (246),(int) (130),(int) (31)));
 //BA.debugLineNum = 1029;BA.debugLine="pnlHabitat.Visible = True";
parent.mostCurrent._pnlhabitat.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1030;BA.debugLine="pnlHidro.Visible = True";
parent.mostCurrent._pnlhidro.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1031;BA.debugLine="pnlResiduos.Visible = True";
parent.mostCurrent._pnlresiduos.setVisible(anywheresoftware.b4a.keywords.Common.True);
 if (true) break;

case 13:
//C
this.state = 34;
 //BA.debugLineNum = 1034;BA.debugLine="x.SetColorAnimated(300, Colors.White, Colors.ARG";
_x.SetColorAnimated((int) (300),anywheresoftware.b4a.keywords.Common.Colors.White,anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (202),(int) (108),(int) (56)));
 //BA.debugLineNum = 1035;BA.debugLine="lblEcorregionTitle.TextColor = Colors.ARGB(255,2";
parent.mostCurrent._lblecorregiontitle.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (202),(int) (108),(int) (56)));
 //BA.debugLineNum = 1037;BA.debugLine="MsgboxAsync(\"No hay herramientas especializadas";
anywheresoftware.b4a.keywords.Common.MsgboxAsync(BA.ObjectToCharSequence("No hay herramientas especializadas para tu ecorregión aún. Pero podrás usar las herramientas generales de PreserVamos!"),BA.ObjectToCharSequence("Ecorregión aún no estudiada"),processBA);
 //BA.debugLineNum = 1038;BA.debugLine="pnlHabitat.Visible = True";
parent.mostCurrent._pnlhabitat.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1039;BA.debugLine="pnlHidro.Visible = True";
parent.mostCurrent._pnlhidro.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1040;BA.debugLine="pnlResiduos.Visible = True";
parent.mostCurrent._pnlresiduos.setVisible(anywheresoftware.b4a.keywords.Common.True);
 if (true) break;

case 15:
//C
this.state = 34;
 //BA.debugLineNum = 1043;BA.debugLine="lblEcoregionBanda.SetColorAnimated(300, Colors.W";
parent.mostCurrent._lblecoregionbanda.SetColorAnimated((int) (300),anywheresoftware.b4a.keywords.Common.Colors.White,anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (237),(int) (27),(int) (36)));
 //BA.debugLineNum = 1044;BA.debugLine="lblEcorregionTitle.TextColor = Colors.ARGB(255,2";
parent.mostCurrent._lblecorregiontitle.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (237),(int) (27),(int) (36)));
 //BA.debugLineNum = 1046;BA.debugLine="MsgboxAsync(\"No hay herramientas especializadas";
anywheresoftware.b4a.keywords.Common.MsgboxAsync(BA.ObjectToCharSequence("No hay herramientas especializadas para tu ecorregión aún. Pero podrás usar las herramientas generales de PreserVamos!"),BA.ObjectToCharSequence("Ecorregión aún no estudiada"),processBA);
 //BA.debugLineNum = 1047;BA.debugLine="pnlHabitat.Visible = True";
parent.mostCurrent._pnlhabitat.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1048;BA.debugLine="pnlHidro.Visible = True";
parent.mostCurrent._pnlhidro.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1049;BA.debugLine="pnlResiduos.Visible = True";
parent.mostCurrent._pnlresiduos.setVisible(anywheresoftware.b4a.keywords.Common.True);
 if (true) break;

case 17:
//C
this.state = 34;
 //BA.debugLineNum = 1052;BA.debugLine="x.SetColorAnimated(300, Colors.White, Colors.ARG";
_x.SetColorAnimated((int) (300),anywheresoftware.b4a.keywords.Common.Colors.White,anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (255),(int) (202),(int) (8)));
 //BA.debugLineNum = 1053;BA.debugLine="lblEcorregionTitle.TextColor = Colors.ARGB(255,2";
parent.mostCurrent._lblecorregiontitle.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (255),(int) (202),(int) (8)));
 //BA.debugLineNum = 1055;BA.debugLine="Msgbox(\"No hay herramientas especializadas para";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence("No hay herramientas especializadas para tu ecorregión aún. Pero podrás usar las herramientas generales de PreserVamos!"),BA.ObjectToCharSequence("Ecorregión aún no estudiada"),mostCurrent.activityBA);
 //BA.debugLineNum = 1056;BA.debugLine="pnlHabitat.Visible = True";
parent.mostCurrent._pnlhabitat.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1057;BA.debugLine="pnlHidro.Visible = True";
parent.mostCurrent._pnlhidro.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1058;BA.debugLine="pnlResiduos.Visible = True";
parent.mostCurrent._pnlresiduos.setVisible(anywheresoftware.b4a.keywords.Common.True);
 if (true) break;

case 19:
//C
this.state = 34;
 //BA.debugLineNum = 1061;BA.debugLine="x.SetColorAnimated(300, Colors.White, Colors.ARG";
_x.SetColorAnimated((int) (300),anywheresoftware.b4a.keywords.Common.Colors.White,anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (247),(int) (170),(int) (146)));
 //BA.debugLineNum = 1062;BA.debugLine="lblEcorregionTitle.TextColor = Colors.ARGB(255,2";
parent.mostCurrent._lblecorregiontitle.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (247),(int) (170),(int) (146)));
 //BA.debugLineNum = 1064;BA.debugLine="MsgboxAsync(\"No hay herramientas especializadas";
anywheresoftware.b4a.keywords.Common.MsgboxAsync(BA.ObjectToCharSequence("No hay herramientas especializadas para tu ecorregión aún. Pero podrás usar las herramientas generales de PreserVamos!"),BA.ObjectToCharSequence("Ecorregión aún no estudiada"),processBA);
 //BA.debugLineNum = 1065;BA.debugLine="pnlHabitat.Visible = True";
parent.mostCurrent._pnlhabitat.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1066;BA.debugLine="pnlHidro.Visible = True";
parent.mostCurrent._pnlhidro.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1067;BA.debugLine="pnlResiduos.Visible = True";
parent.mostCurrent._pnlresiduos.setVisible(anywheresoftware.b4a.keywords.Common.True);
 if (true) break;

case 21:
//C
this.state = 34;
 //BA.debugLineNum = 1070;BA.debugLine="x.SetColorAnimated(300, Colors.White, Colors.ARG";
_x.SetColorAnimated((int) (300),anywheresoftware.b4a.keywords.Common.Colors.White,anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (71),(int) (94),(int) (136)));
 //BA.debugLineNum = 1071;BA.debugLine="lblEcorregionTitle.TextColor = Colors.ARGB(255,7";
parent.mostCurrent._lblecorregiontitle.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (71),(int) (94),(int) (136)));
 //BA.debugLineNum = 1073;BA.debugLine="MsgboxAsync(\"No hay herramientas especializadas";
anywheresoftware.b4a.keywords.Common.MsgboxAsync(BA.ObjectToCharSequence("No hay herramientas especializadas para tu ecorregión aún. Pero podrás usar las herramientas generales de PreserVamos!"),BA.ObjectToCharSequence("Ecorregión aún no estudiada"),processBA);
 //BA.debugLineNum = 1074;BA.debugLine="pnlHabitat.Visible = True";
parent.mostCurrent._pnlhabitat.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1075;BA.debugLine="pnlHidro.Visible = True";
parent.mostCurrent._pnlhidro.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1076;BA.debugLine="pnlResiduos.Visible = True";
parent.mostCurrent._pnlresiduos.setVisible(anywheresoftware.b4a.keywords.Common.True);
 if (true) break;

case 23:
//C
this.state = 34;
 //BA.debugLineNum = 1079;BA.debugLine="x.SetColorAnimated(300, Colors.White, Colors.ARG";
_x.SetColorAnimated((int) (300),anywheresoftware.b4a.keywords.Common.Colors.White,anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (117),(int) (188),(int) (92)));
 //BA.debugLineNum = 1080;BA.debugLine="lblEcorregionTitle.TextColor = Colors.ARGB(255,1";
parent.mostCurrent._lblecorregiontitle.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (117),(int) (188),(int) (92)));
 //BA.debugLineNum = 1082;BA.debugLine="MsgboxAsync(\"No hay herramientas especializadas";
anywheresoftware.b4a.keywords.Common.MsgboxAsync(BA.ObjectToCharSequence("No hay herramientas especializadas para tu ecorregión aún. Pero podrás usar las herramientas generales de PreserVamos!"),BA.ObjectToCharSequence("Ecorregión aún no estudiada"),processBA);
 //BA.debugLineNum = 1083;BA.debugLine="pnlHabitat.Visible = True";
parent.mostCurrent._pnlhabitat.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1084;BA.debugLine="pnlHidro.Visible = True";
parent.mostCurrent._pnlhidro.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1085;BA.debugLine="pnlResiduos.Visible = True";
parent.mostCurrent._pnlresiduos.setVisible(anywheresoftware.b4a.keywords.Common.True);
 if (true) break;

case 25:
//C
this.state = 34;
 //BA.debugLineNum = 1088;BA.debugLine="x.SetColorAnimated(300, Colors.White, Colors.ARG";
_x.SetColorAnimated((int) (300),anywheresoftware.b4a.keywords.Common.Colors.White,anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (0),(int) (152),(int) (79)));
 //BA.debugLineNum = 1089;BA.debugLine="lblEcorregionTitle.TextColor = Colors.ARGB(255,0";
parent.mostCurrent._lblecorregiontitle.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (0),(int) (152),(int) (79)));
 //BA.debugLineNum = 1091;BA.debugLine="pnlHabitat.Visible = True";
parent.mostCurrent._pnlhabitat.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1092;BA.debugLine="pnlHidro.Visible = True";
parent.mostCurrent._pnlhidro.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1093;BA.debugLine="pnlResiduos.Visible = True";
parent.mostCurrent._pnlresiduos.setVisible(anywheresoftware.b4a.keywords.Common.True);
 if (true) break;

case 27:
//C
this.state = 34;
 //BA.debugLineNum = 1096;BA.debugLine="x.SetColorAnimated(300, Colors.White, Colors.ARG";
_x.SetColorAnimated((int) (300),anywheresoftware.b4a.keywords.Common.Colors.White,anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (254),(int) (242),(int) (0)));
 //BA.debugLineNum = 1097;BA.debugLine="lblEcorregionTitle.TextColor = Colors.ARGB(255,2";
parent.mostCurrent._lblecorregiontitle.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (254),(int) (242),(int) (0)));
 //BA.debugLineNum = 1099;BA.debugLine="MsgboxAsync(\"No hay herramientas especializadas";
anywheresoftware.b4a.keywords.Common.MsgboxAsync(BA.ObjectToCharSequence("No hay herramientas especializadas para tu ecorregión aún. Pero podrás usar las herramientas generales de PreserVamos!"),BA.ObjectToCharSequence("Ecorregión aún no estudiada"),processBA);
 //BA.debugLineNum = 1100;BA.debugLine="pnlHabitat.Visible = True";
parent.mostCurrent._pnlhabitat.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1101;BA.debugLine="pnlHidro.Visible = True";
parent.mostCurrent._pnlhidro.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1102;BA.debugLine="pnlResiduos.Visible = True";
parent.mostCurrent._pnlresiduos.setVisible(anywheresoftware.b4a.keywords.Common.True);
 if (true) break;

case 29:
//C
this.state = 34;
 //BA.debugLineNum = 1105;BA.debugLine="x.SetColorAnimated(300, Colors.White, Colors.ARG";
_x.SetColorAnimated((int) (300),anywheresoftware.b4a.keywords.Common.Colors.White,anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (155),(int) (81),(int) (36)));
 //BA.debugLineNum = 1106;BA.debugLine="lblEcorregionTitle.TextColor = Colors.ARGB(255,1";
parent.mostCurrent._lblecorregiontitle.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (155),(int) (81),(int) (36)));
 //BA.debugLineNum = 1108;BA.debugLine="MsgboxAsync(\"No hay herramientas especializadas";
anywheresoftware.b4a.keywords.Common.MsgboxAsync(BA.ObjectToCharSequence("No hay herramientas especializadas para tu ecorregión aún. Pero podrás usar las herramientas generales de PreserVamos!"),BA.ObjectToCharSequence("Ecorregión aún no estudiada"),processBA);
 //BA.debugLineNum = 1109;BA.debugLine="pnlHabitat.Visible = True";
parent.mostCurrent._pnlhabitat.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1110;BA.debugLine="pnlHidro.Visible = True";
parent.mostCurrent._pnlhidro.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1111;BA.debugLine="pnlResiduos.Visible = True";
parent.mostCurrent._pnlresiduos.setVisible(anywheresoftware.b4a.keywords.Common.True);
 if (true) break;

case 31:
//C
this.state = 34;
 //BA.debugLineNum = 1114;BA.debugLine="x.SetColorAnimated(300, Colors.White, Colors.ARG";
_x.SetColorAnimated((int) (300),anywheresoftware.b4a.keywords.Common.Colors.White,anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (58),(int) (132),(int) (119)));
 //BA.debugLineNum = 1115;BA.debugLine="lblEcorregionTitle.TextColor = Colors.ARGB(255,5";
parent.mostCurrent._lblecorregiontitle.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (58),(int) (132),(int) (119)));
 //BA.debugLineNum = 1118;BA.debugLine="MsgboxAsync(\"No hay herramientas especializadas";
anywheresoftware.b4a.keywords.Common.MsgboxAsync(BA.ObjectToCharSequence("No hay herramientas especializadas para tu ecorregión aún. Pero podrás usar las herramientas generales de PreserVamos!"),BA.ObjectToCharSequence("Ecorregión aún no estudiada"),processBA);
 //BA.debugLineNum = 1119;BA.debugLine="pnlHabitat.Visible = True";
parent.mostCurrent._pnlhabitat.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1120;BA.debugLine="pnlHidro.Visible = True";
parent.mostCurrent._pnlhidro.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1121;BA.debugLine="pnlResiduos.Visible = True";
parent.mostCurrent._pnlresiduos.setVisible(anywheresoftware.b4a.keywords.Common.True);
 if (true) break;

case 33:
//C
this.state = 34;
 //BA.debugLineNum = 1123;BA.debugLine="x.SetColorAnimated(300, Colors.White, Colors.ARG";
_x.SetColorAnimated((int) (300),anywheresoftware.b4a.keywords.Common.Colors.White,anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (130),(int) (130),(int) (130)));
 //BA.debugLineNum = 1124;BA.debugLine="lblEcorregionTitle.TextColor = Colors.ARGB(255,1";
parent.mostCurrent._lblecorregiontitle.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (130),(int) (130),(int) (130)));
 //BA.debugLineNum = 1125;BA.debugLine="lblEcorregionTitle.Text = \"No sabemos en que eco";
parent.mostCurrent._lblecorregiontitle.setText(BA.ObjectToCharSequence("No sabemos en que ecorregión te encuentras"));
 //BA.debugLineNum = 1127;BA.debugLine="MsgboxAsync(\"No hay herramientas especializadas";
anywheresoftware.b4a.keywords.Common.MsgboxAsync(BA.ObjectToCharSequence("No hay herramientas especializadas para tu ecorregión aún. Pero podrás usar las herramientas generales de PreserVamos!"),BA.ObjectToCharSequence("Ecorregión aún no estudiada"),processBA);
 //BA.debugLineNum = 1128;BA.debugLine="lblEcorregionSubTitle.Text = \"Herramientas gener";
parent.mostCurrent._lblecorregionsubtitle.setText(BA.ObjectToCharSequence("Herramientas generales"));
 //BA.debugLineNum = 1129;BA.debugLine="pnlHabitat.Visible = True";
parent.mostCurrent._pnlhabitat.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1130;BA.debugLine="pnlHidro.Visible = True";
parent.mostCurrent._pnlhidro.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1131;BA.debugLine="pnlResiduos.Visible = True";
parent.mostCurrent._pnlresiduos.setVisible(anywheresoftware.b4a.keywords.Common.True);
 if (true) break;

case 34:
//C
this.state = -1;
;
 //BA.debugLineNum = 1133;BA.debugLine="Sleep(100)";
anywheresoftware.b4a.keywords.Common.Sleep(mostCurrent.activityBA,this,(int) (100));
this.state = 35;
return;
case 35:
//C
this.state = -1;
;
 //BA.debugLineNum = 1137;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public static void  _show_terrain_info() throws Exception{
ResumableSub_Show_Terrain_Info rsub = new ResumableSub_Show_Terrain_Info(null);
rsub.resume(processBA, null);
}
public static class ResumableSub_Show_Terrain_Info extends BA.ResumableSub {
public ResumableSub_Show_Terrain_Info(appear.pnud.preservamos.form_main parent) {
this.parent = parent;
}
appear.pnud.preservamos.form_main parent;
anywheresoftware.b4a.objects.B4XViewWrapper _panel_layout = null;
Object _sf = null;
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
 //BA.debugLineNum = 1232;BA.debugLine="cust_dialog.VisibleAnimationDuration = 1200";
parent.mostCurrent._cust_dialog._visibleanimationduration /*int*/  = (int) (1200);
 //BA.debugLineNum = 1233;BA.debugLine="cust_dialog.Initialize(Activity)";
parent.mostCurrent._cust_dialog._initialize /*String*/ (mostCurrent.activityBA,(anywheresoftware.b4a.objects.B4XViewWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.B4XViewWrapper(), (java.lang.Object)(parent.mostCurrent._activity.getObject())));
 //BA.debugLineNum = 1234;BA.debugLine="cust_dialog.BackgroundColor = Colors.White";
parent.mostCurrent._cust_dialog._backgroundcolor /*int*/  = anywheresoftware.b4a.keywords.Common.Colors.White;
 //BA.debugLineNum = 1235;BA.debugLine="cust_dialog.TitleBarColor = Colors.White";
parent.mostCurrent._cust_dialog._titlebarcolor /*int*/  = anywheresoftware.b4a.keywords.Common.Colors.White;
 //BA.debugLineNum = 1236;BA.debugLine="cust_dialog.ButtonsHeight = 0dip";
parent.mostCurrent._cust_dialog._buttonsheight /*int*/  = anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (0));
 //BA.debugLineNum = 1237;BA.debugLine="cust_dialog.TitleBarHeight = 0dip";
parent.mostCurrent._cust_dialog._titlebarheight /*int*/  = anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (0));
 //BA.debugLineNum = 1238;BA.debugLine="cust_dialog.BorderCornersRadius = 8dip";
parent.mostCurrent._cust_dialog._bordercornersradius /*int*/  = anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (8));
 //BA.debugLineNum = 1239;BA.debugLine="cust_dialog.PutAtTop = False";
parent.mostCurrent._cust_dialog._putattop /*boolean*/  = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 1241;BA.debugLine="Dim panel_layout As B4XView = xui.CreatePanel(\"\")";
_panel_layout = new anywheresoftware.b4a.objects.B4XViewWrapper();
_panel_layout = parent.mostCurrent._xui.CreatePanel(processBA,"");
 //BA.debugLineNum = 1242;BA.debugLine="panel_layout.SetLayoutAnimated(500, 0, 300dip, 95";
_panel_layout.SetLayoutAnimated((int) (500),(int) (0),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (300)),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (95),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (80),mostCurrent.activityBA));
 //BA.debugLineNum = 1243;BA.debugLine="panel_layout.LoadLayout(\"info_ecoregion\")";
_panel_layout.LoadLayout("info_ecoregion",mostCurrent.activityBA);
 //BA.debugLineNum = 1245;BA.debugLine="lblInfoTerrain_Title.Text = \"Te encontrás en la E";
parent.mostCurrent._lblinfoterrain_title.setText(BA.ObjectToCharSequence("Te encontrás en la Ecorregión "+parent.mostCurrent._main._currentecoregion /*String*/ +anywheresoftware.b4a.keywords.Common.CRLF+anywheresoftware.b4a.keywords.Common.CRLF+"Subecorregión "+parent.mostCurrent._main._currentecoregion /*String*/ ));
 //BA.debugLineNum = 1248;BA.debugLine="spnEcoregion.Add (\"Altos Andes\")";
parent.mostCurrent._spnecoregion.Add("Altos Andes");
 //BA.debugLineNum = 1249;BA.debugLine="spnEcoregion.Add (\"Puna\")";
parent.mostCurrent._spnecoregion.Add("Puna");
 //BA.debugLineNum = 1250;BA.debugLine="spnEcoregion.Add (\"Monte de sierras y bolsones\")";
parent.mostCurrent._spnecoregion.Add("Monte de sierras y bolsones");
 //BA.debugLineNum = 1251;BA.debugLine="spnEcoregion.Add (\"Selva de las yungas\")";
parent.mostCurrent._spnecoregion.Add("Selva de las yungas");
 //BA.debugLineNum = 1252;BA.debugLine="spnEcoregion.Add (\"Chaco seco\")";
parent.mostCurrent._spnecoregion.Add("Chaco seco");
 //BA.debugLineNum = 1253;BA.debugLine="spnEcoregion.Add (\"Chaco humedo\")";
parent.mostCurrent._spnecoregion.Add("Chaco humedo");
 //BA.debugLineNum = 1254;BA.debugLine="spnEcoregion.Add (\"Selva paranaense\")";
parent.mostCurrent._spnecoregion.Add("Selva paranaense");
 //BA.debugLineNum = 1255;BA.debugLine="spnEcoregion.Add (\"Esteros del Ibera\")";
parent.mostCurrent._spnecoregion.Add("Esteros del Ibera");
 //BA.debugLineNum = 1256;BA.debugLine="spnEcoregion.Add (\"Campos y malezales\")";
parent.mostCurrent._spnecoregion.Add("Campos y malezales");
 //BA.debugLineNum = 1257;BA.debugLine="spnEcoregion.Add (\"Delta e islas del Parana\")";
parent.mostCurrent._spnecoregion.Add("Delta e islas del Parana");
 //BA.debugLineNum = 1258;BA.debugLine="spnEcoregion.Add (\"Espinal\")";
parent.mostCurrent._spnecoregion.Add("Espinal");
 //BA.debugLineNum = 1259;BA.debugLine="spnEcoregion.Add (\"Pampa\")";
parent.mostCurrent._spnecoregion.Add("Pampa");
 //BA.debugLineNum = 1260;BA.debugLine="spnEcoregion.Add (\"Monte de llanuras y mesetas\")";
parent.mostCurrent._spnecoregion.Add("Monte de llanuras y mesetas");
 //BA.debugLineNum = 1261;BA.debugLine="spnEcoregion.Add (\"Estepa patagonica\")";
parent.mostCurrent._spnecoregion.Add("Estepa patagonica");
 //BA.debugLineNum = 1262;BA.debugLine="spnEcoregion.Add (\"Bosques patagonicos\")";
parent.mostCurrent._spnecoregion.Add("Bosques patagonicos");
 //BA.debugLineNum = 1263;BA.debugLine="spnEcoregion.Add (\"No lo sé...\")";
parent.mostCurrent._spnecoregion.Add("No lo sé...");
 //BA.debugLineNum = 1265;BA.debugLine="spnEcoregion.SelectedIndex = spnEcoregion.IndexOf";
parent.mostCurrent._spnecoregion.setSelectedIndex(parent.mostCurrent._spnecoregion.IndexOf(parent.mostCurrent._main._currentecoregion /*String*/ ));
 //BA.debugLineNum = 1268;BA.debugLine="If Main.currentEcoregion = \"Altos Andes\" Then";
if (true) break;

case 1:
//if
this.state = 34;
if ((parent.mostCurrent._main._currentecoregion /*String*/ ).equals("Altos Andes")) { 
this.state = 3;
}else if((parent.mostCurrent._main._currentecoregion /*String*/ ).equals("Puna")) { 
this.state = 5;
}else if((parent.mostCurrent._main._currentecoregion /*String*/ ).equals("Monte de sierras y bolsones")) { 
this.state = 7;
}else if((parent.mostCurrent._main._currentecoregion /*String*/ ).equals("Selva de las yungas")) { 
this.state = 9;
}else if((parent.mostCurrent._main._currentecoregion /*String*/ ).equals("Chaco seco")) { 
this.state = 11;
}else if((parent.mostCurrent._main._currentecoregion /*String*/ ).equals("Chaco humedo")) { 
this.state = 13;
}else if((parent.mostCurrent._main._currentecoregion /*String*/ ).equals("Selva paranaense")) { 
this.state = 15;
}else if((parent.mostCurrent._main._currentecoregion /*String*/ ).equals("Esteros del Ibera")) { 
this.state = 17;
}else if((parent.mostCurrent._main._currentecoregion /*String*/ ).equals("Campos y malezales")) { 
this.state = 19;
}else if((parent.mostCurrent._main._currentecoregion /*String*/ ).equals("Delta e islas del Parana")) { 
this.state = 21;
}else if((parent.mostCurrent._main._currentecoregion /*String*/ ).equals("Espinal")) { 
this.state = 23;
}else if((parent.mostCurrent._main._currentecoregion /*String*/ ).equals("Pampa")) { 
this.state = 25;
}else if((parent.mostCurrent._main._currentecoregion /*String*/ ).equals("Monte de llanuras y mesetas")) { 
this.state = 27;
}else if((parent.mostCurrent._main._currentecoregion /*String*/ ).equals("Estepa patagonica")) { 
this.state = 29;
}else if((parent.mostCurrent._main._currentecoregion /*String*/ ).equals("Bosques patagonicos")) { 
this.state = 31;
}else if((parent.mostCurrent._main._currentecoregion /*String*/ ).equals("")) { 
this.state = 33;
}if (true) break;

case 3:
//C
this.state = 34;
 //BA.debugLineNum = 1270;BA.debugLine="fondoInfoTerrain.Color = Colors.White";
parent.mostCurrent._fondoinfoterrain.setColor(anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 1271;BA.debugLine="imgInfoTerrain.Color = Colors.ARGB(255,95,186,19";
parent.mostCurrent._imginfoterrain.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (95),(int) (186),(int) (191)));
 //BA.debugLineNum = 1272;BA.debugLine="lblEcoregionBanda.Color = Colors.ARGB(255,95,186";
parent.mostCurrent._lblecoregionbanda.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (95),(int) (186),(int) (191)));
 //BA.debugLineNum = 1273;BA.debugLine="imgEcorregionMapa.Bitmap = LoadBitmapResize(File";
parent.mostCurrent._imgecorregionmapa.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapResize(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"altos_andes.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.True).getObject()));
 //BA.debugLineNum = 1276;BA.debugLine="lblInfoTerrain_Descripcion.Text = \"Abarca 12.300";
parent.mostCurrent._lblinfoterrain_descripcion.setText(BA.ObjectToCharSequence("Abarca 12.300.000 hectáreas que se extienden desde la frontera con Bolivia hasta el norte de Neuquén, "+"donde las cumbres cordilleranas soportan un clima extremadamente árido, frío y de feroces vientos."+anywheresoftware.b4a.keywords.Common.CRLF+"Las especies más características de su flora son las estepas de iros y coirones (pastos de hojas dura y punzante) que se encuentran en los faldeos de pendiente suave. "+"La vegetación se va empobreciendo montaña arriba, hasta desaparecer en las rocas. Sin embargo, asombrosos verdores sorprenden en aquellos valles y hondonadas en donde se junta el agua de deshielo."+anywheresoftware.b4a.keywords.Common.CRLF+"El chinchillón, el zorro colorado y el cóndor son algunos representantes de la fauna que habitan esta región."));
 if (true) break;

case 5:
//C
this.state = 34;
 //BA.debugLineNum = 1284;BA.debugLine="fondoInfoTerrain.Color = Colors.White";
parent.mostCurrent._fondoinfoterrain.setColor(anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 1285;BA.debugLine="imgInfoTerrain.Color = Colors.ARGB(255,95,186,19";
parent.mostCurrent._imginfoterrain.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (95),(int) (186),(int) (191)));
 //BA.debugLineNum = 1286;BA.debugLine="lblEcoregionBanda.Color = Colors.ARGB(255,95,186";
parent.mostCurrent._lblecoregionbanda.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (95),(int) (186),(int) (191)));
 //BA.debugLineNum = 1287;BA.debugLine="imgEcorregionMapa.Bitmap = LoadBitmapResize(File";
parent.mostCurrent._imgecorregionmapa.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapResize(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"puna.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.True).getObject()));
 //BA.debugLineNum = 1288;BA.debugLine="lblInfoTerrain_Descripcion.Text = \"Sus 3.800 met";
parent.mostCurrent._lblinfoterrain_descripcion.setText(BA.ObjectToCharSequence("Sus 3.800 metros de altitud promedio la convierte en la segunda altiplanicie de la Tierra detrás del Tibet."+anywheresoftware.b4a.keywords.Common.CRLF+"Desde Jujuy hasta el norte de San Juan se extienden sus cerca de 9.300.000 hectáreas salpicadas de salinas y lagunas salobres de escasa profundidad. Una gran cantidad de aves acuáticas se congregan en estos espejos, entre las que se destacan tres especies de flamenco."+anywheresoftware.b4a.keywords.Common.CRLF+"La vegetación típica de la región está compuesta por una estepa de arbustos bajos dominada por tolas y tolillas, en la que se pueden ver vicuñas y suris. En las laderas y quebradas, zonas con mayor humedad, se encuentra la queñoa, el único árbol puneño."));
 if (true) break;

case 7:
//C
this.state = 34;
 //BA.debugLineNum = 1296;BA.debugLine="fondoInfoTerrain.Color = Colors.White";
parent.mostCurrent._fondoinfoterrain.setColor(anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 1297;BA.debugLine="imgInfoTerrain.Color = Colors.ARGB(255,227,172,1";
parent.mostCurrent._imginfoterrain.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (227),(int) (172),(int) (108)));
 //BA.debugLineNum = 1298;BA.debugLine="lblEcoregionBanda.Color = Colors.ARGB(255,227,17";
parent.mostCurrent._lblecoregionbanda.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (227),(int) (172),(int) (108)));
 //BA.debugLineNum = 1299;BA.debugLine="imgEcorregionMapa.Bitmap = LoadBitmapResize(File";
parent.mostCurrent._imgecorregionmapa.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapResize(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"monte_sierras_bolsones.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.True).getObject()));
 //BA.debugLineNum = 1300;BA.debugLine="lblInfoTerrain_Descripcion.Text = \"Sus aproximad";
parent.mostCurrent._lblinfoterrain_descripcion.setText(BA.ObjectToCharSequence("Sus aproximadamente 11.700.000 hectáreas se extienden paralelamente a la Cordillera de los Andes, desde Jujuy hasta el norte de Mendoza. Es una estepa arbustiva tendida sobre valles intermontanos, bolsones y laderas serranas."+anywheresoftware.b4a.keywords.Common.CRLF+"Pese a que recibe escasas precipitaciones (hasta 200 mm anuales), en aquellas zonas con agua subterránea prosperan algunas especies arbóreas, como el algarrobo."+anywheresoftware.b4a.keywords.Common.CRLF+"Esta región exclusiva de Argentina alberga guanacos, tortugas terrestres, pumas, vizcachas, el pichiciego menor, la boa de las vizcacheras y el águila coronada."));
 if (true) break;

case 9:
//C
this.state = 34;
 //BA.debugLineNum = 1307;BA.debugLine="fondoInfoTerrain.Color = Colors.White";
parent.mostCurrent._fondoinfoterrain.setColor(anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 1308;BA.debugLine="imgInfoTerrain.Color = Colors.ARGB(255,237,0,140";
parent.mostCurrent._imginfoterrain.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (237),(int) (0),(int) (140)));
 //BA.debugLineNum = 1309;BA.debugLine="lblEcoregionBanda.Color = Colors.ARGB(255,237,0,";
parent.mostCurrent._lblecoregionbanda.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (237),(int) (0),(int) (140)));
 //BA.debugLineNum = 1310;BA.debugLine="imgEcorregionMapa.Bitmap = LoadBitmapResize(File";
parent.mostCurrent._imgecorregionmapa.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapResize(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"selva_yungas.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.True).getObject()));
 //BA.debugLineNum = 1311;BA.debugLine="lblInfoTerrain_Descripcion.Text = \"Es el apéndic";
parent.mostCurrent._lblinfoterrain_descripcion.setText(BA.ObjectToCharSequence("Es el apéndice austral de la selva que baja desde Venezuela a través de las laderas orientales de los Andes."+anywheresoftware.b4a.keywords.Common.CRLF+"La Puna y los bosques chaqueños están separados por sus 4.700.000 hectáreas emplazadas sobre diversos encadenamientos serranos."+anywheresoftware.b4a.keywords.Common.CRLF+"En esta selva hay entre 900 y 2.500 mm anuales de lluvia. El intrincado ecosistema cumple un papel irremplazable en la regulación hídrica de nuestro noroeste."+anywheresoftware.b4a.keywords.Common.CRLF+"El loro alisero y la ardilla roja son algunas especies faunísticas exclusivas que se encuentran en esta ecorregión."));
 if (true) break;

case 11:
//C
this.state = 34;
 //BA.debugLineNum = 1318;BA.debugLine="fondoInfoTerrain.Color = Colors.White";
parent.mostCurrent._fondoinfoterrain.setColor(anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 1319;BA.debugLine="imgInfoTerrain.Color = Colors.ARGB(255,246,130,3";
parent.mostCurrent._imginfoterrain.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (246),(int) (130),(int) (31)));
 //BA.debugLineNum = 1320;BA.debugLine="imgEcorregionMapa.Bitmap = LoadBitmapResize(File";
parent.mostCurrent._imgecorregionmapa.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapResize(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"chaco_seco.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.True).getObject()));
 //BA.debugLineNum = 1321;BA.debugLine="lblInfoTerrain_Descripcion.Text = \"Abarca 49.300";
parent.mostCurrent._lblinfoterrain_descripcion.setText(BA.ObjectToCharSequence("Abarca 49.300.000 hectáreas cubiertas por bosques xerófilos, apenas interrumpida por pastizales, cardonales y alguna salina."+anywheresoftware.b4a.keywords.Common.CRLF+"La ecorregión ocupa la mayor parte de la llanura chaqueña y los faldeos serranos que la bordean por el sur y el oeste."+anywheresoftware.b4a.keywords.Common.CRLF+"El quebracho colorado santiagueño es el árbol emblemático, imponente y con una madera de gran fortaleza. Entre las especies faunísticas, se encuentra el yaguareté y dos fósiles vivientes: el tatú carreta y el chancho quimilero."));
 if (true) break;

case 13:
//C
this.state = 34;
 //BA.debugLineNum = 1327;BA.debugLine="fondoInfoTerrain.Color = Colors.White";
parent.mostCurrent._fondoinfoterrain.setColor(anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 1328;BA.debugLine="imgInfoTerrain.Color = Colors.ARGB(255,202,108,5";
parent.mostCurrent._imginfoterrain.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (202),(int) (108),(int) (56)));
 //BA.debugLineNum = 1329;BA.debugLine="lblEcoregionBanda.Color = Colors.ARGB(255,202,10";
parent.mostCurrent._lblecoregionbanda.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (202),(int) (108),(int) (56)));
 //BA.debugLineNum = 1330;BA.debugLine="imgEcorregionMapa.Bitmap = LoadBitmapResize(File";
parent.mostCurrent._imgecorregionmapa.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapResize(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"chaco_humedo.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.True).getObject()));
 //BA.debugLineNum = 1331;BA.debugLine="lblInfoTerrain_Descripcion.Text = \"Sobre los río";
parent.mostCurrent._lblinfoterrain_descripcion.setText(BA.ObjectToCharSequence("Sobre los ríos Paraná y Paraguay se extienden sus 11.900.000 hectáreas ricas por la cercanía de la florestas misioneras y de un generoso régimen pluvial de hasta 1.200 mm anuales."+anywheresoftware.b4a.keywords.Common.CRLF+"Conviven bosques cerrados, sabanas salpicadas de palmeras, cañadas, esteros y lagunas."+anywheresoftware.b4a.keywords.Common.CRLF+"Entre la fauna pueden encontrarse boas, yacarés, monos, corzuelas, carpinchos, pecaríes y una infinidad de aves acuáticas."));
 if (true) break;

case 15:
//C
this.state = 34;
 //BA.debugLineNum = 1337;BA.debugLine="fondoInfoTerrain.Color = Colors.White";
parent.mostCurrent._fondoinfoterrain.setColor(anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 1338;BA.debugLine="imgInfoTerrain.Color = Colors.ARGB(255,237,27,36";
parent.mostCurrent._imginfoterrain.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (237),(int) (27),(int) (36)));
 //BA.debugLineNum = 1339;BA.debugLine="imgInfoTerrain.Color = Colors.ARGB(255,237,27,36";
parent.mostCurrent._imginfoterrain.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (237),(int) (27),(int) (36)));
 //BA.debugLineNum = 1340;BA.debugLine="imgEcorregionMapa.Bitmap = LoadBitmapResize(File";
parent.mostCurrent._imgecorregionmapa.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapResize(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"selva_paranaense.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.True).getObject()));
 //BA.debugLineNum = 1341;BA.debugLine="lblInfoTerrain_Descripcion.Text = \"Más de dos mi";
parent.mostCurrent._lblinfoterrain_descripcion.setText(BA.ObjectToCharSequence("Más de dos mil especies botánicas superiores y un millar de animales vertebrados hacen de esta región la de mayor biodiversidad y complejidad ecológica en Argentina."+anywheresoftware.b4a.keywords.Common.CRLF+"Esto se debe al constante influjo de humedad y las temperaturas elevadas que allí imperan, provenientes de los vientos del Atlántico y la latitud subtropical. "+anywheresoftware.b4a.keywords.Common.CRLF+"Originalmente cubría 100.000.000 de hectáreas al sur de Brasil, el oriente de Paraguay y el noreste de Argentina. Hoy en día perduran tan sólo unas 2.700.000 hectáreas, de las cuales Misiones alberga el núcleo mejor conservado."+anywheresoftware.b4a.keywords.Common.CRLF+"Entre la fauna se destacan el yaguareté, el águila harpía, el zorro pitoco y la corzuela enana."));
 if (true) break;

case 17:
//C
this.state = 34;
 //BA.debugLineNum = 1348;BA.debugLine="fondoInfoTerrain.Color = Colors.White";
parent.mostCurrent._fondoinfoterrain.setColor(anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 1349;BA.debugLine="imgInfoTerrain.Color = Colors.ARGB(255,255,202,8";
parent.mostCurrent._imginfoterrain.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (255),(int) (202),(int) (8)));
 //BA.debugLineNum = 1350;BA.debugLine="lblEcoregionBanda.Color = Colors.ARGB(255,255,20";
parent.mostCurrent._lblecoregionbanda.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (255),(int) (202),(int) (8)));
 //BA.debugLineNum = 1351;BA.debugLine="imgEcorregionMapa.Bitmap = LoadBitmapResize(File";
parent.mostCurrent._imgecorregionmapa.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapResize(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"esteros_ibera.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.True).getObject()));
 //BA.debugLineNum = 1352;BA.debugLine="lblInfoTerrain_Descripcion.Text = \"Lagunas, este";
parent.mostCurrent._lblinfoterrain_descripcion.setText(BA.ObjectToCharSequence("Lagunas, esteros y bañados mezclan sus aguas en estas 3.900.000 hectáreas de poca profundidad, testimonio del paso del río Paraná hace unos 8 mil años. Se trata de uno de los reservorios de agua dulce más grandes del mundo."+anywheresoftware.b4a.keywords.Common.CRLF+"Una porción de los 1500 mm de lluvia anuales que recibe regresa al cielo o fluye hacia el Paraná medio a través del río Corriente, único desagüe del sistema."+anywheresoftware.b4a.keywords.Common.CRLF+"Con una flora abigarrada, la fauna no se queda atrás, incluyendo a especies en peligro como el ciervo de los pantanos."));
 if (true) break;

case 19:
//C
this.state = 34;
 //BA.debugLineNum = 1358;BA.debugLine="fondoInfoTerrain.Color = Colors.White";
parent.mostCurrent._fondoinfoterrain.setColor(anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 1359;BA.debugLine="imgInfoTerrain.Color = Colors.ARGB(255,247,170,1";
parent.mostCurrent._imginfoterrain.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (247),(int) (170),(int) (146)));
 //BA.debugLineNum = 1360;BA.debugLine="lblEcoregionBanda.Color = Colors.ARGB(255,247,17";
parent.mostCurrent._lblecoregionbanda.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (247),(int) (170),(int) (146)));
 //BA.debugLineNum = 1361;BA.debugLine="imgEcorregionMapa.Bitmap = LoadBitmapResize(File";
parent.mostCurrent._imgecorregionmapa.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapResize(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"campos_malezales.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.True).getObject()));
 //BA.debugLineNum = 1362;BA.debugLine="lblInfoTerrain_Descripcion.Text = \"Sus cerca de";
parent.mostCurrent._lblinfoterrain_descripcion.setText(BA.ObjectToCharSequence("Sus cerca de 2.700.000 hectáreas de pastizales y pajonales están ubicadas en el sur de Misiones y el este de Corrientes, vinculando la Selva Paranaense con los bosques del Espinal."+anywheresoftware.b4a.keywords.Common.CRLF+"La uniformidad del paisaje se ve interrumpida por algunas isletas de monte, conocidas como “mogotes”, y la selva en galería que acompaña los cursos fluviales. "+anywheresoftware.b4a.keywords.Common.CRLF+"Su clima subtropical húmedo favorece la presencia de una rica variedad de plantas herbáceas, como la flechilla, el espartillo amargo, la paja colorada y el pasto jesuita."));
 if (true) break;

case 21:
//C
this.state = 34;
 //BA.debugLineNum = 1368;BA.debugLine="fondoInfoTerrain.Color = Colors.White";
parent.mostCurrent._fondoinfoterrain.setColor(anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 1369;BA.debugLine="imgInfoTerrain.Color = Colors.ARGB(255,71,94,136";
parent.mostCurrent._imginfoterrain.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (71),(int) (94),(int) (136)));
 //BA.debugLineNum = 1370;BA.debugLine="lblEcoregionBanda.Color = Colors.ARGB(255,71,94,";
parent.mostCurrent._lblecoregionbanda.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (71),(int) (94),(int) (136)));
 //BA.debugLineNum = 1371;BA.debugLine="imgEcorregionMapa.Bitmap = LoadBitmapResize(File";
parent.mostCurrent._imgecorregionmapa.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapResize(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"delta_islas_parana.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.True).getObject()));
 //BA.debugLineNum = 1372;BA.debugLine="lblInfoTerrain_Descripcion.Text = \"Abarca el lab";
parent.mostCurrent._lblinfoterrain_descripcion.setText(BA.ObjectToCharSequence("Abarca el laberíntico Delta y las islas ubicadas en los ríos Paraná y Paraguay."+anywheresoftware.b4a.keywords.Common.CRLF+"Las 5.600.000 hectáreas de humedales conforman un fabuloso corredor biológico."+anywheresoftware.b4a.keywords.Common.CRLF+"Muchas especies del Chaco y la Selva Paranaense se abrieron paso hasta las templadas latitudes de esta ecorregión favorecida por aguas que brindan humedad, atemperan los picos termométricos y sirven de transporte."));
 if (true) break;

case 23:
//C
this.state = 34;
 //BA.debugLineNum = 1378;BA.debugLine="fondoInfoTerrain.Color = Colors.White";
parent.mostCurrent._fondoinfoterrain.setColor(anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 1379;BA.debugLine="imgInfoTerrain.Color = Colors.ARGB(255,117,188,9";
parent.mostCurrent._imginfoterrain.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (117),(int) (188),(int) (92)));
 //BA.debugLineNum = 1380;BA.debugLine="lblEcoregionBanda.Color = Colors.ARGB(255,117,18";
parent.mostCurrent._lblecoregionbanda.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (117),(int) (188),(int) (92)));
 //BA.debugLineNum = 1381;BA.debugLine="imgEcorregionMapa.Bitmap = LoadBitmapResize(File";
parent.mostCurrent._imgecorregionmapa.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapResize(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"espinal.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.True).getObject()));
 //BA.debugLineNum = 1382;BA.debugLine="lblInfoTerrain_Descripcion.Text = \"Esta formació";
parent.mostCurrent._lblinfoterrain_descripcion.setText(BA.ObjectToCharSequence("Esta formación de bosques armados de espinas típicos de ambientes secos, alternada con sabanas, estepas de gramíneas y palmares, representa la herradura arbórea que ciñe al pastizal pampeano."+anywheresoftware.b4a.keywords.Common.CRLF+"En sus 29.950.000 hectáreas, que corren desde la cálida y húmeda Mesopotamia hasta las secas comarcas del centro de La Pampa y el sur bonaerense, pasando por llanuras levemente onduladas y serranías, predominan los algarrobos."));
 if (true) break;

case 25:
//C
this.state = 34;
 //BA.debugLineNum = 1387;BA.debugLine="fondoInfoTerrain.Color = Colors.White";
parent.mostCurrent._fondoinfoterrain.setColor(anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 1388;BA.debugLine="imgInfoTerrain.Color = Colors.ARGB(255,0,152,79)";
parent.mostCurrent._imginfoterrain.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (0),(int) (152),(int) (79)));
 //BA.debugLineNum = 1389;BA.debugLine="lblEcoregionBanda.Color = Colors.ARGB(255,0,152,";
parent.mostCurrent._lblecoregionbanda.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (0),(int) (152),(int) (79)));
 //BA.debugLineNum = 1390;BA.debugLine="imgEcorregionMapa.Bitmap = LoadBitmapResize(File";
parent.mostCurrent._imgecorregionmapa.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapResize(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"pampa.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.True).getObject()));
 //BA.debugLineNum = 1391;BA.debugLine="lblInfoTerrain_Descripcion.Text = \"Sus 39.700.00";
parent.mostCurrent._lblinfoterrain_descripcion.setText(BA.ObjectToCharSequence("Sus 39.700.000 hectáreas componen una de las llanuras más fértiles del planeta, producto de sedimentos andinos."+anywheresoftware.b4a.keywords.Common.CRLF+"Sólo las sierras de la Ventana y de Tandil, al sur, interrumpen el llano paisaje. Goza de temperaturas benignas y lluvias bastante bien repartidas a lo largo del año."+anywheresoftware.b4a.keywords.Common.CRLF+"Es recorrida por algunas lagunas y por un puñado de ríos lentos y meandrosos."));
 if (true) break;

case 27:
//C
this.state = 34;
 //BA.debugLineNum = 1398;BA.debugLine="fondoInfoTerrain.Color = Colors.White";
parent.mostCurrent._fondoinfoterrain.setColor(anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 1399;BA.debugLine="imgInfoTerrain.Color = Colors.ARGB(255,254,242,0";
parent.mostCurrent._imginfoterrain.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (254),(int) (242),(int) (0)));
 //BA.debugLineNum = 1400;BA.debugLine="lblEcoregionBanda.Color = Colors.ARGB(255,254,24";
parent.mostCurrent._lblecoregionbanda.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (254),(int) (242),(int) (0)));
 //BA.debugLineNum = 1401;BA.debugLine="imgEcorregionMapa.Bitmap = LoadBitmapResize(File";
parent.mostCurrent._imgecorregionmapa.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapResize(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"monte_llanuras_mesetas.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.True).getObject()));
 //BA.debugLineNum = 1402;BA.debugLine="lblInfoTerrain_Descripcion.Text = \"Se trata de l";
parent.mostCurrent._lblinfoterrain_descripcion.setText(BA.ObjectToCharSequence("Se trata de la franja más árida del país, que abarca unas 35.400.000 hectáreas."+anywheresoftware.b4a.keywords.Common.CRLF+"Desde Mendoza, el Monte tuerce hacia el océano Atlántico, pasando entre el Espinal y la Estepa Patagónica hasta llegar a la costa del extremo sur de Buenos Aires, Río Negro y Chubut."+anywheresoftware.b4a.keywords.Common.CRLF+"Prevalecen los llanos y mesetas escalonadas. Sus jarillales hospedan maras, cuises, pumas, guanacos, zorros grises y choiques."));
 if (true) break;

case 29:
//C
this.state = 34;
 //BA.debugLineNum = 1408;BA.debugLine="fondoInfoTerrain.Color = Colors.White";
parent.mostCurrent._fondoinfoterrain.setColor(anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 1409;BA.debugLine="imgInfoTerrain.Color = Colors.ARGB(255,155,81,36";
parent.mostCurrent._imginfoterrain.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (155),(int) (81),(int) (36)));
 //BA.debugLineNum = 1410;BA.debugLine="lblEcoregionBanda.Color = Colors.ARGB(255,155,81";
parent.mostCurrent._lblecoregionbanda.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (155),(int) (81),(int) (36)));
 //BA.debugLineNum = 1411;BA.debugLine="imgEcorregionMapa.Bitmap = LoadBitmapResize(File";
parent.mostCurrent._imgecorregionmapa.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapResize(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"estepa_patagonica.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.True).getObject()));
 //BA.debugLineNum = 1412;BA.debugLine="lblInfoTerrain_Descripcion.Text = \"Son más de 54";
parent.mostCurrent._lblinfoterrain_descripcion.setText(BA.ObjectToCharSequence("Son más de 54.200.000 hectáreas que recorren desde los Andes hasta el Atlántico y, en el sentido de los meridianos, corren desde los campos volcánicos de la Payunia, al sureste de Mendoza, hasta los pastizales del norte de Tierra del Fuego."+anywheresoftware.b4a.keywords.Common.CRLF+"Los suelos pobres, las escasas precipitaciones, las bajas temperaturas, las heladas durante casi todo el año y los vientos que superan los 100 kilómetros por hora no impiden que arbustos achaparrados y pastos ralos cubran la mayor parte de esta región."+anywheresoftware.b4a.keywords.Common.CRLF+"Si bien la Estepa se destaca por la abundancia de formas de vida exclusivas, comparte especies y géneros con la Puna y los Altos Andes."));
 if (true) break;

case 31:
//C
this.state = 34;
 //BA.debugLineNum = 1418;BA.debugLine="fondoInfoTerrain.Color = Colors.White";
parent.mostCurrent._fondoinfoterrain.setColor(anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 1419;BA.debugLine="imgInfoTerrain.Color = Colors.ARGB(255,58,132,11";
parent.mostCurrent._imginfoterrain.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (58),(int) (132),(int) (119)));
 //BA.debugLineNum = 1420;BA.debugLine="lblEcoregionBanda.Color = Colors.ARGB(255,58,132";
parent.mostCurrent._lblecoregionbanda.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (58),(int) (132),(int) (119)));
 //BA.debugLineNum = 1421;BA.debugLine="imgEcorregionMapa.Bitmap = LoadBitmapResize(File";
parent.mostCurrent._imgecorregionmapa.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapResize(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"bosques_patagonicos.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.True).getObject()));
 //BA.debugLineNum = 1422;BA.debugLine="lblInfoTerrain_Descripcion.Text = \"Se trata de u";
parent.mostCurrent._lblinfoterrain_descripcion.setText(BA.ObjectToCharSequence("Se trata de una delgada franja boscosa que se extiende por unas 6.450.000 hectáreas desde el norte de Neuquén hasta Tierra del Fuego y la Isla de los Estados. "+anywheresoftware.b4a.keywords.Common.CRLF+"La flora aquí presente posee parientes en Australia, Nueva Zelanda y la Antártida debido a la unión que hubo en el pasado de las masas continentales. "+anywheresoftware.b4a.keywords.Common.CRLF+"También se los llama Bosques Subantárticos y albergan una abundante cantidad de especies exclusivas tales como el pudú, uno de los ciervos más pequeños del mundo."));
 if (true) break;

case 33:
//C
this.state = 34;
 //BA.debugLineNum = 1427;BA.debugLine="fondoInfoTerrain.Color = Colors.White";
parent.mostCurrent._fondoinfoterrain.setColor(anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 1428;BA.debugLine="imgInfoTerrain.Color = Colors.ARGB(255,130,130,1";
parent.mostCurrent._imginfoterrain.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (130),(int) (130),(int) (130)));
 //BA.debugLineNum = 1429;BA.debugLine="lblEcoregionBanda.Color = Colors.ARGB(255,130,13";
parent.mostCurrent._lblecoregionbanda.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (130),(int) (130),(int) (130)));
 //BA.debugLineNum = 1430;BA.debugLine="imgEcorregionMapa.Bitmap = LoadBitmapResize(File";
parent.mostCurrent._imgecorregionmapa.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapResize(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"full_map_argentina.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.True).getObject()));
 //BA.debugLineNum = 1431;BA.debugLine="imgInfoTerrain.Text = \"\"";
parent.mostCurrent._imginfoterrain.setText(BA.ObjectToCharSequence(""));
 //BA.debugLineNum = 1432;BA.debugLine="lblInfoTerrain_Title.Text = \"No sabemos en que e";
parent.mostCurrent._lblinfoterrain_title.setText(BA.ObjectToCharSequence("No sabemos en que ecorregión te encuentras"));
 //BA.debugLineNum = 1433;BA.debugLine="lblInfoTerrain_Descripcion.Text = \"No pudimos de";
parent.mostCurrent._lblinfoterrain_descripcion.setText(BA.ObjectToCharSequence("No pudimos detectar automáticamente en que ecorregión te encuentras... Si la sabes, seleccionala del menú, así te mostraremos herramientas específicas para estudiar tu ambiente!"));
 if (true) break;

case 34:
//C
this.state = -1;
;
 //BA.debugLineNum = 1438;BA.debugLine="Dim sf As Object = cust_dialog.ShowCustom(panel_l";
_sf = parent.mostCurrent._cust_dialog._showcustom /*anywheresoftware.b4a.keywords.Common.ResumableSubWrapper*/ (_panel_layout,(Object)(""),(Object)(""),(Object)(""));
 //BA.debugLineNum = 1439;BA.debugLine="Wait For (sf) Complete (Result As Int)";
anywheresoftware.b4a.keywords.Common.WaitFor("complete", processBA, this, _sf);
this.state = 35;
return;
case 35:
//C
this.state = -1;
_result = (Integer) result[0];
;
 //BA.debugLineNum = 1441;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public static void  _spnecoregion_itemclick(int _position,Object _value) throws Exception{
ResumableSub_spnEcoregion_ItemClick rsub = new ResumableSub_spnEcoregion_ItemClick(null,_position,_value);
rsub.resume(processBA, null);
}
public static class ResumableSub_spnEcoregion_ItemClick extends BA.ResumableSub {
public ResumableSub_spnEcoregion_ItemClick(appear.pnud.preservamos.form_main parent,int _position,Object _value) {
this.parent = parent;
this._position = _position;
this._value = _value;
}
appear.pnud.preservamos.form_main parent;
int _position;
Object _value;
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
 //BA.debugLineNum = 1448;BA.debugLine="Msgbox2Async(\"¿Deseas cambiar manualmente tu ecor";
anywheresoftware.b4a.keywords.Common.Msgbox2Async(BA.ObjectToCharSequence("¿Deseas cambiar manualmente tu ecorregión?"),BA.ObjectToCharSequence("Cambiar ecorregión"),"Si","","No",(anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper(), (android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null)),processBA,anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1449;BA.debugLine="Wait For Msgbox_Result (Result As Int)";
anywheresoftware.b4a.keywords.Common.WaitFor("msgbox_result", processBA, this, null);
this.state = 9;
return;
case 9:
//C
this.state = 1;
_result = (Integer) result[0];
;
 //BA.debugLineNum = 1450;BA.debugLine="If Result = DialogResponse.POSITIVE Then";
if (true) break;

case 1:
//if
this.state = 8;
if (_result==anywheresoftware.b4a.keywords.Common.DialogResponse.POSITIVE) { 
this.state = 3;
}if (true) break;

case 3:
//C
this.state = 4;
 //BA.debugLineNum = 1451;BA.debugLine="If Value <> \"No lo sé...\" Then";
if (true) break;

case 4:
//if
this.state = 7;
if ((_value).equals((Object)("No lo sé...")) == false) { 
this.state = 6;
}if (true) break;

case 6:
//C
this.state = 7;
 //BA.debugLineNum = 1452;BA.debugLine="cust_dialog.Close(xui.DialogResponse_Positive)";
parent.mostCurrent._cust_dialog._close /*boolean*/ (parent.mostCurrent._xui.DialogResponse_Positive);
 //BA.debugLineNum = 1453;BA.debugLine="Main.currentEcoregion = Value";
parent.mostCurrent._main._currentecoregion /*String*/  = BA.ObjectToString(_value);
 //BA.debugLineNum = 1454;BA.debugLine="setEcoregion";
_setecoregion();
 if (true) break;

case 7:
//C
this.state = 8;
;
 if (true) break;

case 8:
//C
this.state = -1;
;
 //BA.debugLineNum = 1457;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public static String  _tabresettoanalizar() throws Exception{
 //BA.debugLineNum = 965;BA.debugLine="Sub tabResetToAnalizar";
 //BA.debugLineNum = 966;BA.debugLine="tabStripMain.ScrollTo(2, True)";
mostCurrent._tabstripmain.ScrollTo((int) (2),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 967;BA.debugLine="tabStripMain_PageSelected(2)";
_tabstripmain_pageselected((int) (2));
 //BA.debugLineNum = 968;BA.debugLine="End Sub";
return "";
}
public static String  _tabstripmain_pageselected(int _position) throws Exception{
 //BA.debugLineNum = 810;BA.debugLine="Sub tabStripMain_PageSelected (Position As Int)";
 //BA.debugLineNum = 812;BA.debugLine="If Position = 0 Then";
if (_position==0) { 
 //BA.debugLineNum = 815;BA.debugLine="icoAnalizar.TextColor = Colors.ARGB(255,51,51,51";
mostCurrent._icoanalizar.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (51),(int) (51),(int) (51)));
 //BA.debugLineNum = 816;BA.debugLine="icoInformacion.TextColor = Colors.ARGB(255,51,51";
mostCurrent._icoinformacion.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (51),(int) (51),(int) (51)));
 //BA.debugLineNum = 817;BA.debugLine="icoPerfil.TextColor = Colors.ARGB(255,51,51,51)";
mostCurrent._icoperfil.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (51),(int) (51),(int) (51)));
 //BA.debugLineNum = 818;BA.debugLine="icoAnalizar.TextSize = 40";
mostCurrent._icoanalizar.setTextSize((float) (40));
 //BA.debugLineNum = 819;BA.debugLine="icoInformacion.TextSize = 40";
mostCurrent._icoinformacion.setTextSize((float) (40));
 //BA.debugLineNum = 820;BA.debugLine="icoPerfil.TextSize = 40";
mostCurrent._icoperfil.setTextSize((float) (40));
 //BA.debugLineNum = 821;BA.debugLine="btnInformacion.Text = \"Municipio\"";
mostCurrent._btninformacion.setText(BA.ObjectToCharSequence("Municipio"));
 //BA.debugLineNum = 822;BA.debugLine="btnAnalizar.Text = \"Analizar\"";
mostCurrent._btnanalizar.setText(BA.ObjectToCharSequence("Analizar"));
 //BA.debugLineNum = 823;BA.debugLine="btnPerfil.Text  = \"Perfil\"";
mostCurrent._btnperfil.setText(BA.ObjectToCharSequence("Perfil"));
 //BA.debugLineNum = 825;BA.debugLine="icoMapa.SetTextColorAnimated(300, Colors.white)";
mostCurrent._icomapa.SetTextColorAnimated((int) (300),anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 826;BA.debugLine="icoMapa.SetTextSizeAnimated(300,50)";
mostCurrent._icomapa.SetTextSizeAnimated((int) (300),(float) (50));
 //BA.debugLineNum = 827;BA.debugLine="btnCirculo.SetLayoutAnimated(300, (icoMapa.Left";
mostCurrent._btncirculo.SetLayoutAnimated((int) (300),(int) ((mostCurrent._icomapa.getLeft()-((mostCurrent._btncirculo.getWidth()-mostCurrent._icomapa.getWidth())/(double)2))),mostCurrent._btncirculo.getTop(),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (80)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (80)));
 //BA.debugLineNum = 828;BA.debugLine="btnMapa.Text = \"\"";
mostCurrent._btnmapa.setText(BA.ObjectToCharSequence(""));
 }else if(_position==1) { 
 //BA.debugLineNum = 831;BA.debugLine="icoAnalizar.TextColor = Colors.ARGB(255,51,51,51";
mostCurrent._icoanalizar.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (51),(int) (51),(int) (51)));
 //BA.debugLineNum = 832;BA.debugLine="icoMapa.TextColor = Colors.ARGB(255,51,51,51)";
mostCurrent._icomapa.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (51),(int) (51),(int) (51)));
 //BA.debugLineNum = 833;BA.debugLine="icoPerfil.TextColor = Colors.ARGB(255,51,51,51)";
mostCurrent._icoperfil.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (51),(int) (51),(int) (51)));
 //BA.debugLineNum = 834;BA.debugLine="icoAlerta.TextSize = 40";
mostCurrent._icoalerta.setTextSize((float) (40));
 //BA.debugLineNum = 835;BA.debugLine="icoAnalizar.TextSize = 40";
mostCurrent._icoanalizar.setTextSize((float) (40));
 //BA.debugLineNum = 836;BA.debugLine="icoMapa.TextSize = 40";
mostCurrent._icomapa.setTextSize((float) (40));
 //BA.debugLineNum = 837;BA.debugLine="icoPerfil.TextSize = 40";
mostCurrent._icoperfil.setTextSize((float) (40));
 //BA.debugLineNum = 838;BA.debugLine="btnMapa.Text = \"Explorar\"";
mostCurrent._btnmapa.setText(BA.ObjectToCharSequence("Explorar"));
 //BA.debugLineNum = 839;BA.debugLine="btnAnalizar.Text = \"Analizar\"";
mostCurrent._btnanalizar.setText(BA.ObjectToCharSequence("Analizar"));
 //BA.debugLineNum = 840;BA.debugLine="btnPerfil.Text  = \"Perfil\"";
mostCurrent._btnperfil.setText(BA.ObjectToCharSequence("Perfil"));
 //BA.debugLineNum = 842;BA.debugLine="icoInformacion.SetTextColorAnimated(300, Colors.";
mostCurrent._icoinformacion.SetTextColorAnimated((int) (300),anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 843;BA.debugLine="icoInformacion.SetTextSizeAnimated(300,50)";
mostCurrent._icoinformacion.SetTextSizeAnimated((int) (300),(float) (50));
 //BA.debugLineNum = 844;BA.debugLine="btnCirculo.SetLayoutAnimated(300, (btnInformacio";
mostCurrent._btncirculo.SetLayoutAnimated((int) (300),(int) ((mostCurrent._btninformacion.getLeft()-((mostCurrent._btncirculo.getWidth()-mostCurrent._btninformacion.getWidth())/(double)2))),mostCurrent._btncirculo.getTop(),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (80)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (80)));
 //BA.debugLineNum = 845;BA.debugLine="btnInformacion.Text = \"\"";
mostCurrent._btninformacion.setText(BA.ObjectToCharSequence(""));
 //BA.debugLineNum = 848;BA.debugLine="If Main.strUserOrg = \"\" Then";
if ((mostCurrent._main._struserorg /*String*/ ).equals("")) { 
 //BA.debugLineNum = 849;BA.debugLine="imgLogoMunicipio.Visible = False";
mostCurrent._imglogomunicipio.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 850;BA.debugLine="gauge_Partido.mBase.Visible = False";
mostCurrent._gauge_partido._mbase /*anywheresoftware.b4a.objects.B4XViewWrapper*/ .setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 851;BA.debugLine="lblMunicipios_reportes.Visible = False";
mostCurrent._lblmunicipios_reportes.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 852;BA.debugLine="btnVerDatosMunicipio.Visible = False";
mostCurrent._btnverdatosmunicipio.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 853;BA.debugLine="btnVerEstadisticasMunicipio.Visible = False";
mostCurrent._btnverestadisticasmunicipio.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 854;BA.debugLine="lblMunicipio_puntos_totales.Visible = False";
mostCurrent._lblmunicipio_puntos_totales.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 855;BA.debugLine="btnMunicipioTW.Visible = False";
mostCurrent._btnmunicipiotw.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 856;BA.debugLine="btnMunicipioFB.Visible = False";
mostCurrent._btnmunicipiofb.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 857;BA.debugLine="btnMunicipioIG.Visible = False";
mostCurrent._btnmunicipioig.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 858;BA.debugLine="btnMunicipioYT.Visible = False";
mostCurrent._btnmunicipioyt.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 859;BA.debugLine="btnMasInfoMunicipio.Visible = False";
mostCurrent._btnmasinfomunicipio.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 860;BA.debugLine="btnCambiarMunicipio.Text = \"Seleccionar\"";
mostCurrent._btncambiarmunicipio.setText(BA.ObjectToCharSequence("Seleccionar"));
 //BA.debugLineNum = 861;BA.debugLine="lblMunicipio_texto_principal.Clear";
mostCurrent._lblmunicipio_texto_principal._clear();
 //BA.debugLineNum = 862;BA.debugLine="lblMunicipio_texto_principal.AddTextItem(\"Aún n";
mostCurrent._lblmunicipio_texto_principal._addtextitem((Object)("Aún no has seleccionado tu municipio, presioná el botón 'Seleccionar', abajo, para localizarte en el mapa"),(Object)(""));
 }else {
 //BA.debugLineNum = 864;BA.debugLine="If Main.hayinternet = True Then";
if (mostCurrent._main._hayinternet /*boolean*/ ==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 865;BA.debugLine="update_Municipio";
_update_municipio();
 }else {
 //BA.debugLineNum = 867;BA.debugLine="imgLogoMunicipio.Visible = False";
mostCurrent._imglogomunicipio.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 868;BA.debugLine="gauge_Partido.mBase.Visible = False";
mostCurrent._gauge_partido._mbase /*anywheresoftware.b4a.objects.B4XViewWrapper*/ .setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 869;BA.debugLine="lblMunicipios_reportes.Visible = False";
mostCurrent._lblmunicipios_reportes.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 870;BA.debugLine="btnVerDatosMunicipio.Visible = False";
mostCurrent._btnverdatosmunicipio.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 871;BA.debugLine="btnVerEstadisticasMunicipio.Visible = False";
mostCurrent._btnverestadisticasmunicipio.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 872;BA.debugLine="lblMunicipio_puntos_totales.Visible = False";
mostCurrent._lblmunicipio_puntos_totales.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 873;BA.debugLine="btnMunicipioTW.Visible = False";
mostCurrent._btnmunicipiotw.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 874;BA.debugLine="btnMunicipioFB.Visible = False";
mostCurrent._btnmunicipiofb.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 875;BA.debugLine="btnMunicipioIG.Visible = False";
mostCurrent._btnmunicipioig.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 876;BA.debugLine="btnMunicipioYT.Visible = False";
mostCurrent._btnmunicipioyt.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 877;BA.debugLine="btnMasInfoMunicipio.Visible = False";
mostCurrent._btnmasinfomunicipio.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 878;BA.debugLine="btnCambiarMunicipio.Visible = False";
mostCurrent._btncambiarmunicipio.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 879;BA.debugLine="lblMunicipio_texto_principal.Clear";
mostCurrent._lblmunicipio_texto_principal._clear();
 //BA.debugLineNum = 880;BA.debugLine="lblMunicipio_texto_principal.AddTextItem(\"No t";
mostCurrent._lblmunicipio_texto_principal._addtextitem((Object)("No tienes internet, no puedes ver la información específica de este municipio"),(Object)(""));
 };
 };
 }else if(_position==2) { 
 //BA.debugLineNum = 888;BA.debugLine="icoMapa.TextColor = Colors.ARGB(255,51,51,51)";
mostCurrent._icomapa.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (51),(int) (51),(int) (51)));
 //BA.debugLineNum = 889;BA.debugLine="icoInformacion.TextColor = Colors.ARGB(255,51,51";
mostCurrent._icoinformacion.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (51),(int) (51),(int) (51)));
 //BA.debugLineNum = 890;BA.debugLine="icoPerfil.TextColor = Colors.ARGB(255,51,51,51)";
mostCurrent._icoperfil.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (51),(int) (51),(int) (51)));
 //BA.debugLineNum = 891;BA.debugLine="icoMapa.TextSize = 40";
mostCurrent._icomapa.setTextSize((float) (40));
 //BA.debugLineNum = 892;BA.debugLine="icoInformacion.TextSize = 40";
mostCurrent._icoinformacion.setTextSize((float) (40));
 //BA.debugLineNum = 893;BA.debugLine="icoPerfil.TextSize = 40";
mostCurrent._icoperfil.setTextSize((float) (40));
 //BA.debugLineNum = 894;BA.debugLine="btnMapa.Text = \"Explorar\"";
mostCurrent._btnmapa.setText(BA.ObjectToCharSequence("Explorar"));
 //BA.debugLineNum = 895;BA.debugLine="btnInformacion.Text = \"Municipio\"";
mostCurrent._btninformacion.setText(BA.ObjectToCharSequence("Municipio"));
 //BA.debugLineNum = 896;BA.debugLine="btnPerfil.Text  = \"Perfil\"";
mostCurrent._btnperfil.setText(BA.ObjectToCharSequence("Perfil"));
 //BA.debugLineNum = 899;BA.debugLine="icoAnalizar.SetTextColorAnimated(300, Colors.whi";
mostCurrent._icoanalizar.SetTextColorAnimated((int) (300),anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 900;BA.debugLine="icoAnalizar.SetTextSizeAnimated(300,50)";
mostCurrent._icoanalizar.SetTextSizeAnimated((int) (300),(float) (50));
 //BA.debugLineNum = 901;BA.debugLine="btnCirculo.SetLayoutAnimated(300, (btnAnalizar.L";
mostCurrent._btncirculo.SetLayoutAnimated((int) (300),(int) ((mostCurrent._btnanalizar.getLeft()-((mostCurrent._btncirculo.getWidth()-mostCurrent._btnanalizar.getWidth())/(double)2))),mostCurrent._btncirculo.getTop(),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (80)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (80)));
 //BA.debugLineNum = 902;BA.debugLine="btnAnalizar.Text = \"\"";
mostCurrent._btnanalizar.setText(BA.ObjectToCharSequence(""));
 }else if(_position==3) { 
 //BA.debugLineNum = 906;BA.debugLine="If Main.hayinternet = False Then";
if (mostCurrent._main._hayinternet /*boolean*/ ==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 907;BA.debugLine="lblHayInternet.Visible = True";
mostCurrent._lblhayinternet.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 909;BA.debugLine="ToastMessageShow(\"Necesitas internet para ver t";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Necesitas internet para ver tu perfil"),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 910;BA.debugLine="tabStripMain.ScrollTo(2,True)";
mostCurrent._tabstripmain.ScrollTo((int) (2),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 911;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 915;BA.debugLine="If Main.username = \"guest\" Or Main.username = \"N";
if ((mostCurrent._main._username /*String*/ ).equals("guest") || (mostCurrent._main._username /*String*/ ).equals("None") || (mostCurrent._main._username /*String*/ ).equals("") || (mostCurrent._main._username /*String*/ ).equals("null") || (mostCurrent._main._username /*String*/ ).equals("Null")) { 
 //BA.debugLineNum = 916;BA.debugLine="lblNivel.Visible = False";
mostCurrent._lblnivel.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 917;BA.debugLine="lblPuntos_Explicacion_1.Visible = False";
mostCurrent._lblpuntos_explicacion_1.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 918;BA.debugLine="lblPuntos_Explicacion_2.Visible = False";
mostCurrent._lblpuntos_explicacion_2.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 919;BA.debugLine="lblPuntos_Explicacion_3.Visible = False";
mostCurrent._lblpuntos_explicacion_3.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 920;BA.debugLine="btnActualizar.visible = False";
mostCurrent._btnactualizar.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 921;BA.debugLine="pgbLevel.Visible= False";
mostCurrent._pgblevel.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 922;BA.debugLine="lblPerfil_puntos_totales.Text = \"Necesita estar";
mostCurrent._lblperfil_puntos_totales.setText(BA.ObjectToCharSequence("Necesita estar registrado para ver su perfil"));
 //BA.debugLineNum = 923;BA.debugLine="lblTitleProfile.Text = \":(\"";
mostCurrent._lbltitleprofile.setText(BA.ObjectToCharSequence(":("));
 }else {
 //BA.debugLineNum = 925;BA.debugLine="lblNivel.Visible = True";
mostCurrent._lblnivel.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 926;BA.debugLine="lblPuntos_Explicacion_1.Visible = True";
mostCurrent._lblpuntos_explicacion_1.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 927;BA.debugLine="lblPuntos_Explicacion_2.Visible = True";
mostCurrent._lblpuntos_explicacion_2.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 928;BA.debugLine="lblPuntos_Explicacion_3.Visible = True";
mostCurrent._lblpuntos_explicacion_3.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 929;BA.debugLine="btnActualizar.visible = True";
mostCurrent._btnactualizar.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 930;BA.debugLine="pgbLevel.Visible= True";
mostCurrent._pgblevel.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 931;BA.debugLine="lblPerfil_puntos_totales.Text = \"\"";
mostCurrent._lblperfil_puntos_totales.setText(BA.ObjectToCharSequence(""));
 //BA.debugLineNum = 932;BA.debugLine="lblTitleProfile.Text = \"\"";
mostCurrent._lbltitleprofile.setText(BA.ObjectToCharSequence(""));
 //BA.debugLineNum = 933;BA.debugLine="If lblNivel.Text = \"\" Then";
if ((mostCurrent._lblnivel.getText()).equals("")) { 
 //BA.debugLineNum = 934;BA.debugLine="lblPerfil_puntos_totales.Text = \"Cargando...\"";
mostCurrent._lblperfil_puntos_totales.setText(BA.ObjectToCharSequence("Cargando..."));
 //BA.debugLineNum = 935;BA.debugLine="btnActualizar_Click";
_btnactualizar_click();
 };
 };
 //BA.debugLineNum = 949;BA.debugLine="icoMapa.TextColor = Colors.ARGB(255,51,51,51)";
mostCurrent._icomapa.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (51),(int) (51),(int) (51)));
 //BA.debugLineNum = 950;BA.debugLine="icoAnalizar.TextColor = Colors.ARGB(255,51,51,51";
mostCurrent._icoanalizar.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (51),(int) (51),(int) (51)));
 //BA.debugLineNum = 951;BA.debugLine="icoInformacion.TextColor = Colors.ARGB(255,51,51";
mostCurrent._icoinformacion.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (51),(int) (51),(int) (51)));
 //BA.debugLineNum = 952;BA.debugLine="icoMapa.TextSize = 40";
mostCurrent._icomapa.setTextSize((float) (40));
 //BA.debugLineNum = 953;BA.debugLine="icoAnalizar.TextSize = 40";
mostCurrent._icoanalizar.setTextSize((float) (40));
 //BA.debugLineNum = 954;BA.debugLine="icoInformacion.TextSize = 40";
mostCurrent._icoinformacion.setTextSize((float) (40));
 //BA.debugLineNum = 955;BA.debugLine="btnMapa.Text = \"Explorar\"";
mostCurrent._btnmapa.setText(BA.ObjectToCharSequence("Explorar"));
 //BA.debugLineNum = 956;BA.debugLine="btnInformacion.Text = \"Municipio\"";
mostCurrent._btninformacion.setText(BA.ObjectToCharSequence("Municipio"));
 //BA.debugLineNum = 957;BA.debugLine="btnAnalizar.Text = \"Analizar\"";
mostCurrent._btnanalizar.setText(BA.ObjectToCharSequence("Analizar"));
 //BA.debugLineNum = 959;BA.debugLine="icoPerfil.SetTextColorAnimated(300, Colors.white";
mostCurrent._icoperfil.SetTextColorAnimated((int) (300),anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 960;BA.debugLine="icoPerfil.SetTextSizeAnimated(300,50)";
mostCurrent._icoperfil.SetTextSizeAnimated((int) (300),(float) (50));
 //BA.debugLineNum = 961;BA.debugLine="btnCirculo.SetLayoutAnimated(300, (icoPerfil.Lef";
mostCurrent._btncirculo.SetLayoutAnimated((int) (300),(int) ((mostCurrent._icoperfil.getLeft()-((mostCurrent._btncirculo.getWidth()-mostCurrent._icoperfil.getWidth())/(double)2))),mostCurrent._btncirculo.getTop(),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (80)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (80)));
 //BA.debugLineNum = 962;BA.debugLine="btnPerfil.Text = \"\"";
mostCurrent._btnperfil.setText(BA.ObjectToCharSequence(""));
 };
 //BA.debugLineNum = 964;BA.debugLine="End Sub";
return "";
}
public static anywheresoftware.b4a.keywords.Common.ResumableSubWrapper  _testinternet() throws Exception{
ResumableSub_TestInternet rsub = new ResumableSub_TestInternet(null);
rsub.resume(processBA, null);
return (anywheresoftware.b4a.keywords.Common.ResumableSubWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.keywords.Common.ResumableSubWrapper(), rsub);
}
public static class ResumableSub_TestInternet extends BA.ResumableSub {
public ResumableSub_TestInternet(appear.pnud.preservamos.form_main parent) {
this.parent = parent;
}
appear.pnud.preservamos.form_main parent;
appear.pnud.preservamos.httpjob _j = null;
String _loginpath = "";
String _ret = "";
String _act = "";
anywheresoftware.b4a.objects.collections.JSONParser _parser = null;

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
 //BA.debugLineNum = 2447;BA.debugLine="Log(\"Testing internet\")";
anywheresoftware.b4a.keywords.Common.LogImpl("05046273","Testing internet",0);
 //BA.debugLineNum = 2448;BA.debugLine="Dim j As HttpJob";
_j = new appear.pnud.preservamos.httpjob();
 //BA.debugLineNum = 2449;BA.debugLine="j.Initialize(\"\", Me)";
_j._initialize /*String*/ (processBA,"",form_main.getObject());
 //BA.debugLineNum = 2450;BA.debugLine="Dim loginPath As String = Main.serverPath & \"/\" &";
_loginpath = parent.mostCurrent._main._serverpath /*String*/ +"/"+parent.mostCurrent._main._serverconnectionfolder /*String*/ +"/connecttest.php";
 //BA.debugLineNum = 2451;BA.debugLine="j.Download(loginPath)";
_j._download /*String*/ (_loginpath);
 //BA.debugLineNum = 2452;BA.debugLine="Wait For (j) JobDone(j As HttpJob)";
anywheresoftware.b4a.keywords.Common.WaitFor("jobdone", processBA, this, (Object)(_j));
this.state = 13;
return;
case 13:
//C
this.state = 1;
_j = (appear.pnud.preservamos.httpjob) result[0];
;
 //BA.debugLineNum = 2454;BA.debugLine="If j.Success Then";
if (true) break;

case 1:
//if
this.state = 12;
if (_j._success /*boolean*/ ) { 
this.state = 3;
}else {
this.state = 11;
}if (true) break;

case 3:
//C
this.state = 4;
 //BA.debugLineNum = 2455;BA.debugLine="Log(\"Internet test successful\")";
anywheresoftware.b4a.keywords.Common.LogImpl("05046281","Internet test successful",0);
 //BA.debugLineNum = 2456;BA.debugLine="Main.versionactual = Application.VersionCode";
parent.mostCurrent._main._versionactual /*String*/  = BA.NumberToString(anywheresoftware.b4a.keywords.Common.Application.getVersionCode());
 //BA.debugLineNum = 2457;BA.debugLine="Dim ret As String";
_ret = "";
 //BA.debugLineNum = 2458;BA.debugLine="Dim act As String";
_act = "";
 //BA.debugLineNum = 2459;BA.debugLine="ret = j.GetString";
_ret = _j._getstring /*String*/ ();
 //BA.debugLineNum = 2460;BA.debugLine="Dim parser As JSONParser";
_parser = new anywheresoftware.b4a.objects.collections.JSONParser();
 //BA.debugLineNum = 2461;BA.debugLine="parser.Initialize(ret)";
_parser.Initialize(_ret);
 //BA.debugLineNum = 2462;BA.debugLine="act = parser.NextValue";
_act = BA.ObjectToString(_parser.NextValue());
 //BA.debugLineNum = 2463;BA.debugLine="If act = \"Connected\" Then";
if (true) break;

case 4:
//if
this.state = 9;
if ((_act).equals("Connected")) { 
this.state = 6;
}else {
this.state = 8;
}if (true) break;

case 6:
//C
this.state = 9;
 //BA.debugLineNum = 2464;BA.debugLine="j.Release";
_j._release /*String*/ ();
 //BA.debugLineNum = 2465;BA.debugLine="Return True";
if (true) {
anywheresoftware.b4a.keywords.Common.ReturnFromResumableSub(this,(Object)(anywheresoftware.b4a.keywords.Common.True));return;};
 if (true) break;

case 8:
//C
this.state = 9;
 //BA.debugLineNum = 2467;BA.debugLine="j.Release";
_j._release /*String*/ ();
 //BA.debugLineNum = 2468;BA.debugLine="Return False";
if (true) {
anywheresoftware.b4a.keywords.Common.ReturnFromResumableSub(this,(Object)(anywheresoftware.b4a.keywords.Common.False));return;};
 if (true) break;

case 9:
//C
this.state = 12;
;
 if (true) break;

case 11:
//C
this.state = 12;
 //BA.debugLineNum = 2471;BA.debugLine="j.Release";
_j._release /*String*/ ();
 //BA.debugLineNum = 2472;BA.debugLine="Return False";
if (true) {
anywheresoftware.b4a.keywords.Common.ReturnFromResumableSub(this,(Object)(anywheresoftware.b4a.keywords.Common.False));return;};
 if (true) break;

case 12:
//C
this.state = -1;
;
 //BA.debugLineNum = 2475;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public static String  _toggledrawerstate() throws Exception{
int _duration = 0;
int _pnlbottomvisibleheight = 0;
 //BA.debugLineNum = 541;BA.debugLine="Sub ToggleDrawerState";
 //BA.debugLineNum = 542;BA.debugLine="Dim Duration As Int = 300";
_duration = (int) (300);
 //BA.debugLineNum = 543;BA.debugLine="Bottom_Drawer_OverlayVisible = Not(Bottom_Drawer_";
_bottom_drawer_overlayvisible = anywheresoftware.b4a.keywords.Common.Not(_bottom_drawer_overlayvisible);
 //BA.debugLineNum = 544;BA.debugLine="Bottom_Drawer_Overlay.SetVisibleAnimated(Duration";
mostCurrent._bottom_drawer_overlay.SetVisibleAnimated(_duration,_bottom_drawer_overlayvisible);
 //BA.debugLineNum = 545;BA.debugLine="Dim pnlBottomVisibleHeight As Int = (pnlBottom.He";
_pnlbottomvisibleheight = (int) ((mostCurrent._pnlbottom.getHeight()-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (8))));
 //BA.debugLineNum = 546;BA.debugLine="If Bottom_Drawer_OverlayVisible Then";
if (_bottom_drawer_overlayvisible) { 
 //BA.debugLineNum = 547;BA.debugLine="pnlBottom.SetLayoutAnimated(0, 0, pnlBottom.Pare";
mostCurrent._pnlbottom.SetLayoutAnimated((int) (0),(int) (0),mostCurrent._pnlbottom.getParent().getHeight(),mostCurrent._pnlbottom.getWidth(),mostCurrent._pnlbottom.getHeight());
 //BA.debugLineNum = 548;BA.debugLine="pnlBottom.SetLayoutAnimated(Duration, 0, pnlBott";
mostCurrent._pnlbottom.SetLayoutAnimated(_duration,(int) (0),(int) (mostCurrent._pnlbottom.getParent().getHeight()-_pnlbottomvisibleheight),mostCurrent._pnlbottom.getWidth(),mostCurrent._pnlbottom.getHeight());
 }else {
 //BA.debugLineNum = 550;BA.debugLine="pnlBottom.SetLayoutAnimated(0, 0, pnlBottom.Pare";
mostCurrent._pnlbottom.SetLayoutAnimated((int) (0),(int) (0),(int) (mostCurrent._pnlbottom.getParent().getHeight()-_pnlbottomvisibleheight),mostCurrent._pnlbottom.getWidth(),mostCurrent._pnlbottom.getHeight());
 //BA.debugLineNum = 551;BA.debugLine="pnlBottom.SetLayoutAnimated(Duration, 0, pnlBott";
mostCurrent._pnlbottom.SetLayoutAnimated(_duration,(int) (0),mostCurrent._pnlbottom.getParent().getHeight(),mostCurrent._pnlbottom.getWidth(),mostCurrent._pnlbottom.getHeight());
 };
 //BA.debugLineNum = 553;BA.debugLine="End Sub";
return "";
}
public static anywheresoftware.b4a.keywords.Common.ResumableSubWrapper  _update_municipio() throws Exception{
ResumableSub_update_Municipio rsub = new ResumableSub_update_Municipio(null);
rsub.resume(processBA, null);
return (anywheresoftware.b4a.keywords.Common.ResumableSubWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.keywords.Common.ResumableSubWrapper(), rsub);
}
public static class ResumableSub_update_Municipio extends BA.ResumableSub {
public ResumableSub_update_Municipio(appear.pnud.preservamos.form_main parent) {
this.parent = parent;
}
appear.pnud.preservamos.form_main parent;
String _str_municipio = "";
appear.pnud.preservamos.httpjob _j = null;
String _loginpath = "";
String _act = "";
anywheresoftware.b4a.objects.collections.JSONParser _municipios_parser = null;
anywheresoftware.b4a.objects.collections.Map _nd = null;
int _i = 0;
anywheresoftware.b4a.objects.collections.Map _newpunto = null;
int _l = 0;
int _m = 0;
int _result = 0;
int step74;
int limit74;
int step92;
int limit92;
int step151;
int limit151;
int step169;
int limit169;

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
 //BA.debugLineNum = 1518;BA.debugLine="Dim str_municipio As String";
_str_municipio = "";
 //BA.debugLineNum = 1519;BA.debugLine="str_municipio = Main.strUserOrg.SubString2(Main.s";
_str_municipio = parent.mostCurrent._main._struserorg /*String*/ .substring((int) (parent.mostCurrent._main._struserorg /*String*/ .indexOf("-")+1),parent.mostCurrent._main._struserorg /*String*/ .length());
 //BA.debugLineNum = 1521;BA.debugLine="lblTitleMunicipio.Text = str_municipio";
parent.mostCurrent._lbltitlemunicipio.setText(BA.ObjectToCharSequence(_str_municipio));
 //BA.debugLineNum = 1522;BA.debugLine="Log(\"updating Municipio\")";
anywheresoftware.b4a.keywords.Common.LogImpl("03604485","updating Municipio",0);
 //BA.debugLineNum = 1524;BA.debugLine="Dim j As HttpJob";
_j = new appear.pnud.preservamos.httpjob();
 //BA.debugLineNum = 1525;BA.debugLine="j.Initialize(\"\", Me)";
_j._initialize /*String*/ (processBA,"",form_main.getObject());
 //BA.debugLineNum = 1526;BA.debugLine="Dim loginPath As String = Main.serverPath & \"/\" &";
_loginpath = parent.mostCurrent._main._serverpath /*String*/ +"/"+parent.mostCurrent._main._serverconnectionfolder /*String*/ +"/getPartidos.php?nombre_partido="+_str_municipio;
 //BA.debugLineNum = 1527;BA.debugLine="j.Download(loginPath)";
_j._download /*String*/ (_loginpath);
 //BA.debugLineNum = 1528;BA.debugLine="Wait For (j) JobDone(j As HttpJob)";
anywheresoftware.b4a.keywords.Common.WaitFor("jobdone", processBA, this, (Object)(_j));
this.state = 102;
return;
case 102:
//C
this.state = 1;
_j = (appear.pnud.preservamos.httpjob) result[0];
;
 //BA.debugLineNum = 1530;BA.debugLine="If j.Success Then";
if (true) break;

case 1:
//if
this.state = 101;
if (_j._success /*boolean*/ ) { 
this.state = 3;
}else {
this.state = 96;
}if (true) break;

case 3:
//C
this.state = 4;
 //BA.debugLineNum = 1531;BA.debugLine="Log(\"Partido buscado\")";
anywheresoftware.b4a.keywords.Common.LogImpl("03604494","Partido buscado",0);
 //BA.debugLineNum = 1532;BA.debugLine="Dim act As String";
_act = "";
 //BA.debugLineNum = 1533;BA.debugLine="Municipios_parser_string = j.GetString";
parent.mostCurrent._municipios_parser_string = _j._getstring /*String*/ ();
 //BA.debugLineNum = 1534;BA.debugLine="Dim Municipios_parser As JSONParser 'para enviar";
_municipios_parser = new anywheresoftware.b4a.objects.collections.JSONParser();
 //BA.debugLineNum = 1535;BA.debugLine="Municipios_parser.Initialize(Municipios_parser_s";
_municipios_parser.Initialize(parent.mostCurrent._municipios_parser_string);
 //BA.debugLineNum = 1536;BA.debugLine="act = Municipios_parser.NextValue";
_act = BA.ObjectToString(_municipios_parser.NextValue());
 //BA.debugLineNum = 1537;BA.debugLine="If act = \"Partido OK\" Then";
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
 //BA.debugLineNum = 1538;BA.debugLine="ToastMessageShow(\"Cargando información del muni";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Cargando información del municipio"),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1540;BA.debugLine="Log(\"Partido existente\")";
anywheresoftware.b4a.keywords.Common.LogImpl("03604503","Partido existente",0);
 //BA.debugLineNum = 1541;BA.debugLine="Dim nd As Map";
_nd = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 1542;BA.debugLine="nd = Municipios_parser.NextObject";
_nd = _municipios_parser.NextObject();
 //BA.debugLineNum = 1543;BA.debugLine="lblMunicipio_texto_principal.Clear";
parent.mostCurrent._lblmunicipio_texto_principal._clear();
 //BA.debugLineNum = 1544;BA.debugLine="lblMunicipio_texto_principal.AddTextItem(nd.Get";
parent.mostCurrent._lblmunicipio_texto_principal._addtextitem(_nd.Get((Object)("texto_principal")),(Object)(0));
 //BA.debugLineNum = 1545;BA.debugLine="If nd.Get(\"texto_principal\") = \"\" Then";
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
 //BA.debugLineNum = 1546;BA.debugLine="lblMunicipio_texto_principal.AddTextItem(\"Este";
parent.mostCurrent._lblmunicipio_texto_principal._addtextitem((Object)("Este municipio aún no ha incorporado una descripción de sus políticas ambientales."),(Object)(0));
 if (true) break;
;
 //BA.debugLineNum = 1549;BA.debugLine="If nd.Get(\"texto_secundario\") <> \"\" Then";

case 10:
//if
this.state = 13;
if ((_nd.Get((Object)("texto_secundario"))).equals((Object)("")) == false) { 
this.state = 12;
}if (true) break;

case 12:
//C
this.state = 13;
 //BA.debugLineNum = 1550;BA.debugLine="lblMunicipio_texto_principal.AddTextItem(nd.Ge";
parent.mostCurrent._lblmunicipio_texto_principal._addtextitem(_nd.Get((Object)("texto_secundario")),(Object)(0));
 if (true) break;

case 13:
//C
this.state = 14;
;
 //BA.debugLineNum = 1552;BA.debugLine="lblMunicipio_puntos_totales.Text = nd.Get(\"punt";
parent.mostCurrent._lblmunicipio_puntos_totales.setText(BA.ObjectToCharSequence(_nd.Get((Object)("puntos_totales"))));
 //BA.debugLineNum = 1553;BA.debugLine="lblMunicipio_puntos_totales.Visible = True";
parent.mostCurrent._lblmunicipio_puntos_totales.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1554;BA.debugLine="btnVerDatosMunicipio.Visible = True";
parent.mostCurrent._btnverdatosmunicipio.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1555;BA.debugLine="btnVerEstadisticasMunicipio.Visible = True";
parent.mostCurrent._btnverestadisticasmunicipio.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1557;BA.debugLine="If nd.Get(\"link1\") <> \"\" Then";
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
 //BA.debugLineNum = 1558;BA.debugLine="btnMasInfoMunicipio.Visible = True";
parent.mostCurrent._btnmasinfomunicipio.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1559;BA.debugLine="municipioURL = nd.Get(\"link1\")";
parent.mostCurrent._municipiourl = BA.ObjectToString(_nd.Get((Object)("link1")));
 if (true) break;

case 18:
//C
this.state = 19;
 //BA.debugLineNum = 1561;BA.debugLine="btnMasInfoMunicipio.Visible = False";
parent.mostCurrent._btnmasinfomunicipio.setVisible(anywheresoftware.b4a.keywords.Common.False);
 if (true) break;
;
 //BA.debugLineNum = 1564;BA.debugLine="If nd.Get(\"link2\") <> \"\" Then";

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
 //BA.debugLineNum = 1565;BA.debugLine="btnMunicipioFB.Visible = True";
parent.mostCurrent._btnmunicipiofb.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1566;BA.debugLine="municipioFB = nd.Get(\"link2\")";
parent.mostCurrent._municipiofb = BA.ObjectToString(_nd.Get((Object)("link2")));
 if (true) break;

case 23:
//C
this.state = 24;
 //BA.debugLineNum = 1568;BA.debugLine="btnMunicipioFB.Visible = False";
parent.mostCurrent._btnmunicipiofb.setVisible(anywheresoftware.b4a.keywords.Common.False);
 if (true) break;
;
 //BA.debugLineNum = 1571;BA.debugLine="If nd.Get(\"link3\") <> \"\" Then";

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
 //BA.debugLineNum = 1572;BA.debugLine="btnMunicipioIG.Visible = True";
parent.mostCurrent._btnmunicipioig.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1573;BA.debugLine="municipioIG = nd.Get(\"link3\")";
parent.mostCurrent._municipioig = BA.ObjectToString(_nd.Get((Object)("link3")));
 if (true) break;

case 28:
//C
this.state = 29;
 //BA.debugLineNum = 1575;BA.debugLine="btnMunicipioIG.Visible = False";
parent.mostCurrent._btnmunicipioig.setVisible(anywheresoftware.b4a.keywords.Common.False);
 if (true) break;
;
 //BA.debugLineNum = 1578;BA.debugLine="If nd.Get(\"link4\") <> \"\" Then";

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
 //BA.debugLineNum = 1579;BA.debugLine="btnMunicipioTW.Visible = True";
parent.mostCurrent._btnmunicipiotw.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1580;BA.debugLine="municipioTW = nd.Get(\"link4\")";
parent.mostCurrent._municipiotw = BA.ObjectToString(_nd.Get((Object)("link4")));
 if (true) break;

case 33:
//C
this.state = 34;
 //BA.debugLineNum = 1582;BA.debugLine="btnMunicipioTW.Visible = False";
parent.mostCurrent._btnmunicipiotw.setVisible(anywheresoftware.b4a.keywords.Common.False);
 if (true) break;
;
 //BA.debugLineNum = 1586;BA.debugLine="If Municipios_parser.NextValue = \"Con reportes\"";

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
 //BA.debugLineNum = 1587;BA.debugLine="Log(\"Con reportes\")";
anywheresoftware.b4a.keywords.Common.LogImpl("03604550","Con reportes",0);
 //BA.debugLineNum = 1588;BA.debugLine="Municipios_numresults = Municipios_parser.Next";
parent._municipios_numresults = (int)(BA.ObjectToNumber(_municipios_parser.NextValue()));
 //BA.debugLineNum = 1589;BA.debugLine="lblMunicipio_puntos_totales.Text = Municipios_";
parent.mostCurrent._lblmunicipio_puntos_totales.setText(BA.ObjectToCharSequence(parent._municipios_numresults));
 //BA.debugLineNum = 1590;BA.debugLine="Log(\"Marcadores en el municipio: \" & Municipio";
anywheresoftware.b4a.keywords.Common.LogImpl("03604553","Marcadores en el municipio: "+BA.NumberToString(parent._municipios_numresults),0);
 //BA.debugLineNum = 1594;BA.debugLine="listaPromedio.Initialize";
parent.mostCurrent._listapromedio.Initialize();
 //BA.debugLineNum = 1595;BA.debugLine="listaHidro_Promedio.Initialize";
parent.mostCurrent._listahidro_promedio.Initialize();
 //BA.debugLineNum = 1596;BA.debugLine="listaAgua_Promedio.Initialize";
parent.mostCurrent._listaagua_promedio.Initialize();
 //BA.debugLineNum = 1597;BA.debugLine="listaBio_Promedio.Initialize";
parent.mostCurrent._listabio_promedio.Initialize();
 //BA.debugLineNum = 1598;BA.debugLine="listaUsos_Promedio.Initialize";
parent.mostCurrent._listausos_promedio.Initialize();
 //BA.debugLineNum = 1599;BA.debugLine="listaExoticas_Promedio.Initialize";
parent.mostCurrent._listaexoticas_promedio.Initialize();
 //BA.debugLineNum = 1602;BA.debugLine="Dim hidroPromedio As Double";
parent._hidropromedio = 0;
 //BA.debugLineNum = 1603;BA.debugLine="Dim aguaPromedio As Double";
parent._aguapromedio = 0;
 //BA.debugLineNum = 1604;BA.debugLine="Dim bioPromedio As Double";
parent._biopromedio = 0;
 //BA.debugLineNum = 1605;BA.debugLine="Dim UsosPromedio As Double";
parent._usospromedio = 0;
 //BA.debugLineNum = 1606;BA.debugLine="Dim ExoticasPromedio As Double";
parent._exoticaspromedio = 0;
 //BA.debugLineNum = 1609;BA.debugLine="For i = 0 To Municipios_numresults - 1";
if (true) break;

case 37:
//for
this.state = 40;
step74 = 1;
limit74 = (int) (parent._municipios_numresults-1);
_i = (int) (0) ;
this.state = 103;
if (true) break;

case 103:
//C
this.state = 40;
if ((step74 > 0 && _i <= limit74) || (step74 < 0 && _i >= limit74)) this.state = 39;
if (true) break;

case 104:
//C
this.state = 103;
_i = ((int)(0 + _i + step74)) ;
if (true) break;

case 39:
//C
this.state = 104;
 //BA.debugLineNum = 1611;BA.debugLine="Dim newpunto As Map";
_newpunto = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 1612;BA.debugLine="newpunto = Municipios_parser.NextObject";
_newpunto = _municipios_parser.NextObject();
 //BA.debugLineNum = 1613;BA.debugLine="listaPromedio.Add(newpunto.Get(\"indice\"))";
parent.mostCurrent._listapromedio.Add(_newpunto.Get((Object)("indice")));
 //BA.debugLineNum = 1614;BA.debugLine="listaHidro_Promedio.Add(newpunto.Get(\"valorin";
parent.mostCurrent._listahidro_promedio.Add(_newpunto.Get((Object)("valorind9")));
 //BA.debugLineNum = 1615;BA.debugLine="listaHidro_Promedio.Add(newpunto.Get(\"valorin";
parent.mostCurrent._listahidro_promedio.Add(_newpunto.Get((Object)("valorind10")));
 //BA.debugLineNum = 1616;BA.debugLine="listaAgua_Promedio.Add(newpunto.Get(\"valorind";
parent.mostCurrent._listaagua_promedio.Add(_newpunto.Get((Object)("valorind5")));
 //BA.debugLineNum = 1617;BA.debugLine="listaAgua_Promedio.Add(newpunto.Get(\"valorind";
parent.mostCurrent._listaagua_promedio.Add(_newpunto.Get((Object)("valorind6")));
 //BA.debugLineNum = 1618;BA.debugLine="listaAgua_Promedio.Add(newpunto.Get(\"valorind";
parent.mostCurrent._listaagua_promedio.Add(_newpunto.Get((Object)("valorind7")));
 //BA.debugLineNum = 1619;BA.debugLine="listaAgua_Promedio.Add(newpunto.Get(\"ind_pvm_";
parent.mostCurrent._listaagua_promedio.Add(_newpunto.Get((Object)("ind_pvm_1")));
 //BA.debugLineNum = 1620;BA.debugLine="listaBio_Promedio.Add(newpunto.Get(\"ind_pvm_2";
parent.mostCurrent._listabio_promedio.Add(_newpunto.Get((Object)("ind_pvm_2")));
 //BA.debugLineNum = 1621;BA.debugLine="listaBio_Promedio.Add(newpunto.Get(\"valorind3";
parent.mostCurrent._listabio_promedio.Add(_newpunto.Get((Object)("valorind3")));
 //BA.debugLineNum = 1622;BA.debugLine="listaBio_Promedio.Add(newpunto.Get(\"valorind4";
parent.mostCurrent._listabio_promedio.Add(_newpunto.Get((Object)("valorind4")));
 //BA.debugLineNum = 1623;BA.debugLine="listaUsos_Promedio.Add(newpunto.Get(\"valorind";
parent.mostCurrent._listausos_promedio.Add(_newpunto.Get((Object)("valorind1")));
 //BA.debugLineNum = 1624;BA.debugLine="listaUsos_Promedio.Add(newpunto.Get(\"valorind";
parent.mostCurrent._listausos_promedio.Add(_newpunto.Get((Object)("valorind2")));
 //BA.debugLineNum = 1625;BA.debugLine="listaExoticas_Promedio.Add(newpunto.Get(\"ind_";
parent.mostCurrent._listaexoticas_promedio.Add(_newpunto.Get((Object)("ind_pvm_3")));
 //BA.debugLineNum = 1626;BA.debugLine="listaExoticas_Promedio.Add(newpunto.Get(\"ind_";
parent.mostCurrent._listaexoticas_promedio.Add(_newpunto.Get((Object)("ind_pvm_4")));
 if (true) break;
if (true) break;
;
 //BA.debugLineNum = 1631;BA.debugLine="For l = 0 To listaPromedio.Size - 1";

case 40:
//for
this.state = 47;
step92 = 1;
limit92 = (int) (parent.mostCurrent._listapromedio.getSize()-1);
_l = (int) (0) ;
this.state = 105;
if (true) break;

case 105:
//C
this.state = 47;
if ((step92 > 0 && _l <= limit92) || (step92 < 0 && _l >= limit92)) this.state = 42;
if (true) break;

case 106:
//C
this.state = 105;
_l = ((int)(0 + _l + step92)) ;
if (true) break;

case 42:
//C
this.state = 43;
 //BA.debugLineNum = 1632;BA.debugLine="If listaPromedio.Get(l) <> \"\" Then";
if (true) break;

case 43:
//if
this.state = 46;
if ((parent.mostCurrent._listapromedio.Get(_l)).equals((Object)("")) == false) { 
this.state = 45;
}if (true) break;

case 45:
//C
this.state = 46;
 //BA.debugLineNum = 1633;BA.debugLine="valorEstado = valorEstado + listaPromedio.Ge";
parent._valorestado = parent._valorestado+(double)(BA.ObjectToNumber(parent.mostCurrent._listapromedio.Get(_l)));
 if (true) break;

case 46:
//C
this.state = 106;
;
 if (true) break;
if (true) break;

case 47:
//C
this.state = 48;
;
 //BA.debugLineNum = 1636;BA.debugLine="valorEstado = Round2(valorEstado / listaPromed";
parent._valorestado = anywheresoftware.b4a.keywords.Common.Round2(parent._valorestado/(double)parent.mostCurrent._listapromedio.getSize(),(int) (2));
 //BA.debugLineNum = 1637;BA.debugLine="Log(\"valorEstado: \" & valorEstado)";
anywheresoftware.b4a.keywords.Common.LogImpl("03604600","valorEstado: "+BA.NumberToString(parent._valorestado),0);
 //BA.debugLineNum = 1642;BA.debugLine="gauge_Partido.mBase.Visible = True";
parent.mostCurrent._gauge_partido._mbase /*anywheresoftware.b4a.objects.B4XViewWrapper*/ .setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1643;BA.debugLine="lblMunicipios_reportes.Visible = True";
parent.mostCurrent._lblmunicipios_reportes.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1644;BA.debugLine="gauge_Partido.SetRanges(Array(gauge_Partido.Cr";
parent.mostCurrent._gauge_partido._setranges /*String*/ (anywheresoftware.b4a.keywords.Common.ArrayToList(new Object[]{(Object)(parent.mostCurrent._gauge_partido._createrange /*appear.pnud.preservamos.gauge._gaugerange*/ ((float) (0),(float) (20),parent.mostCurrent._xui.Color_DarkGray)),(Object)(parent.mostCurrent._gauge_partido._createrange /*appear.pnud.preservamos.gauge._gaugerange*/ ((float) (20),(float) (40),parent.mostCurrent._xui.Color_Red)),(Object)(parent.mostCurrent._gauge_partido._createrange /*appear.pnud.preservamos.gauge._gaugerange*/ ((float) (40),(float) (60),parent.mostCurrent._xui.Color_Yellow)),(Object)(parent.mostCurrent._gauge_partido._createrange /*appear.pnud.preservamos.gauge._gaugerange*/ ((float) (60),(float) (80),parent.mostCurrent._xui.Color_Green)),(Object)(parent.mostCurrent._gauge_partido._createrange /*appear.pnud.preservamos.gauge._gaugerange*/ ((float) (80),(float) (100),parent.mostCurrent._xui.Color_Blue))}));
 //BA.debugLineNum = 1645;BA.debugLine="gauge_Partido.CurrentValue = 0";
parent.mostCurrent._gauge_partido._setcurrentvalue /*float*/ ((float) (0));
 //BA.debugLineNum = 1646;BA.debugLine="gauge_Partido.CurrentValue = valorEstado";
parent.mostCurrent._gauge_partido._setcurrentvalue /*float*/ ((float) (parent._valorestado));
 //BA.debugLineNum = 1648;BA.debugLine="If valorEstado < 20 Then";
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
 //BA.debugLineNum = 1649;BA.debugLine="lblMunicipio_estado.Text = \"Estado general: M";
parent.mostCurrent._lblmunicipio_estado.setText(BA.ObjectToCharSequence("Estado general: Muy malo"));
 if (true) break;

case 52:
//C
this.state = 59;
 //BA.debugLineNum = 1651;BA.debugLine="lblMunicipio_estado.Text = \"Estado general: M";
parent.mostCurrent._lblmunicipio_estado.setText(BA.ObjectToCharSequence("Estado general: Malo"));
 if (true) break;

case 54:
//C
this.state = 59;
 //BA.debugLineNum = 1653;BA.debugLine="lblMunicipio_estado.Text = \"Estado general: R";
parent.mostCurrent._lblmunicipio_estado.setText(BA.ObjectToCharSequence("Estado general: Regular"));
 if (true) break;

case 56:
//C
this.state = 59;
 //BA.debugLineNum = 1655;BA.debugLine="lblMunicipio_estado.Text = \"Estado general: B";
parent.mostCurrent._lblmunicipio_estado.setText(BA.ObjectToCharSequence("Estado general: Bueno"));
 if (true) break;

case 58:
//C
this.state = 59;
 //BA.debugLineNum = 1657;BA.debugLine="lblMunicipio_estado.Text = \"Estado general: M";
parent.mostCurrent._lblmunicipio_estado.setText(BA.ObjectToCharSequence("Estado general: Muy bueno!"));
 if (true) break;

case 59:
//C
this.state = 62;
;
 //BA.debugLineNum = 1659;BA.debugLine="lblMunicipio_estado.Text = lblMunicipio_estado";
parent.mostCurrent._lblmunicipio_estado.setText(BA.ObjectToCharSequence(parent.mostCurrent._lblmunicipio_estado.getText()));
 if (true) break;

case 61:
//C
this.state = 62;
 //BA.debugLineNum = 1661;BA.debugLine="Log(\"No tiene reportes\")";
anywheresoftware.b4a.keywords.Common.LogImpl("03604624","No tiene reportes",0);
 //BA.debugLineNum = 1662;BA.debugLine="btnVerDatosMunicipio.Visible = False";
parent.mostCurrent._btnverdatosmunicipio.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1663;BA.debugLine="btnVerEstadisticasMunicipio.Visible = False";
parent.mostCurrent._btnverestadisticasmunicipio.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1664;BA.debugLine="lblMunicipio_puntos_totales.Visible = False";
parent.mostCurrent._lblmunicipio_puntos_totales.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1665;BA.debugLine="lblMunicipio_estado.Text = \"Estado general: De";
parent.mostCurrent._lblmunicipio_estado.setText(BA.ObjectToCharSequence("Estado general: Desconocido"));
 //BA.debugLineNum = 1667;BA.debugLine="gauge_Partido.mBase.Visible = True";
parent.mostCurrent._gauge_partido._mbase /*anywheresoftware.b4a.objects.B4XViewWrapper*/ .setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1668;BA.debugLine="lblMunicipios_reportes.Visible = True";
parent.mostCurrent._lblmunicipios_reportes.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1669;BA.debugLine="gauge_Partido.SetRanges(Array(gauge_Partido.Cr";
parent.mostCurrent._gauge_partido._setranges /*String*/ (anywheresoftware.b4a.keywords.Common.ArrayToList(new Object[]{(Object)(parent.mostCurrent._gauge_partido._createrange /*appear.pnud.preservamos.gauge._gaugerange*/ ((float) (0),(float) (20),parent.mostCurrent._xui.Color_DarkGray)),(Object)(parent.mostCurrent._gauge_partido._createrange /*appear.pnud.preservamos.gauge._gaugerange*/ ((float) (20),(float) (40),parent.mostCurrent._xui.Color_Red)),(Object)(parent.mostCurrent._gauge_partido._createrange /*appear.pnud.preservamos.gauge._gaugerange*/ ((float) (40),(float) (60),parent.mostCurrent._xui.Color_Yellow)),(Object)(parent.mostCurrent._gauge_partido._createrange /*appear.pnud.preservamos.gauge._gaugerange*/ ((float) (60),(float) (80),parent.mostCurrent._xui.Color_Green)),(Object)(parent.mostCurrent._gauge_partido._createrange /*appear.pnud.preservamos.gauge._gaugerange*/ ((float) (80),(float) (100),parent.mostCurrent._xui.Color_Blue))}));
 //BA.debugLineNum = 1670;BA.debugLine="gauge_Partido.CurrentValue = 0";
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
 //BA.debugLineNum = 1674;BA.debugLine="Log(\"Partido NO existente:\" & str_municipio)";
anywheresoftware.b4a.keywords.Common.LogImpl("03604637","Partido NO existente:"+_str_municipio,0);
 //BA.debugLineNum = 1675;BA.debugLine="lblMunicipio_texto_principal.Clear";
parent.mostCurrent._lblmunicipio_texto_principal._clear();
 //BA.debugLineNum = 1676;BA.debugLine="lblMunicipio_texto_principal.AddTextItem(\"Este";
parent.mostCurrent._lblmunicipio_texto_principal._addtextitem((Object)("Este municipio aún no ha incorporado una descripción de sus políticas ambientales."),(Object)(0));
 //BA.debugLineNum = 1677;BA.debugLine="btnMasInfoMunicipio.Visible = False";
parent.mostCurrent._btnmasinfomunicipio.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1681;BA.debugLine="If Municipios_parser.NextValue = \"Con reportes\"";
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
 //BA.debugLineNum = 1682;BA.debugLine="Log(\"Con reportes\")";
anywheresoftware.b4a.keywords.Common.LogImpl("03604645","Con reportes",0);
 //BA.debugLineNum = 1683;BA.debugLine="Municipios_numresults = Municipios_parser.Next";
parent._municipios_numresults = (int)(BA.ObjectToNumber(_municipios_parser.NextValue()));
 //BA.debugLineNum = 1684;BA.debugLine="lblMunicipio_puntos_totales.Text = Municipios_";
parent.mostCurrent._lblmunicipio_puntos_totales.setText(BA.ObjectToCharSequence(parent._municipios_numresults));
 //BA.debugLineNum = 1685;BA.debugLine="Log(\"Marcadores en el municipio: \" & Municipio";
anywheresoftware.b4a.keywords.Common.LogImpl("03604648","Marcadores en el municipio: "+BA.NumberToString(parent._municipios_numresults),0);
 //BA.debugLineNum = 1687;BA.debugLine="lblMunicipio_puntos_totales.Visible = True";
parent.mostCurrent._lblmunicipio_puntos_totales.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1688;BA.debugLine="btnVerDatosMunicipio.Visible = True";
parent.mostCurrent._btnverdatosmunicipio.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1689;BA.debugLine="btnVerEstadisticasMunicipio.Visible = True";
parent.mostCurrent._btnverestadisticasmunicipio.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1692;BA.debugLine="listaPromedio.Initialize";
parent.mostCurrent._listapromedio.Initialize();
 //BA.debugLineNum = 1693;BA.debugLine="listaHidro_Promedio.Initialize";
parent.mostCurrent._listahidro_promedio.Initialize();
 //BA.debugLineNum = 1694;BA.debugLine="listaAgua_Promedio.Initialize";
parent.mostCurrent._listaagua_promedio.Initialize();
 //BA.debugLineNum = 1695;BA.debugLine="listaBio_Promedio.Initialize";
parent.mostCurrent._listabio_promedio.Initialize();
 //BA.debugLineNum = 1696;BA.debugLine="listaUsos_Promedio.Initialize";
parent.mostCurrent._listausos_promedio.Initialize();
 //BA.debugLineNum = 1697;BA.debugLine="listaExoticas_Promedio.Initialize";
parent.mostCurrent._listaexoticas_promedio.Initialize();
 //BA.debugLineNum = 1700;BA.debugLine="Dim hidroPromedio As Double";
parent._hidropromedio = 0;
 //BA.debugLineNum = 1701;BA.debugLine="Dim aguaPromedio As Double";
parent._aguapromedio = 0;
 //BA.debugLineNum = 1702;BA.debugLine="Dim bioPromedio As Double";
parent._biopromedio = 0;
 //BA.debugLineNum = 1703;BA.debugLine="Dim UsosPromedio As Double";
parent._usospromedio = 0;
 //BA.debugLineNum = 1704;BA.debugLine="Dim ExoticasPromedio As Double";
parent._exoticaspromedio = 0;
 //BA.debugLineNum = 1707;BA.debugLine="For i = 0 To Municipios_numresults - 1";
if (true) break;

case 68:
//for
this.state = 71;
step151 = 1;
limit151 = (int) (parent._municipios_numresults-1);
_i = (int) (0) ;
this.state = 107;
if (true) break;

case 107:
//C
this.state = 71;
if ((step151 > 0 && _i <= limit151) || (step151 < 0 && _i >= limit151)) this.state = 70;
if (true) break;

case 108:
//C
this.state = 107;
_i = ((int)(0 + _i + step151)) ;
if (true) break;

case 70:
//C
this.state = 108;
 //BA.debugLineNum = 1709;BA.debugLine="Dim newpunto As Map";
_newpunto = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 1710;BA.debugLine="newpunto = Municipios_parser.NextObject";
_newpunto = _municipios_parser.NextObject();
 //BA.debugLineNum = 1711;BA.debugLine="listaPromedio.Add(newpunto.Get(\"indice\"))";
parent.mostCurrent._listapromedio.Add(_newpunto.Get((Object)("indice")));
 //BA.debugLineNum = 1712;BA.debugLine="listaHidro_Promedio.Add(newpunto.Get(\"valorin";
parent.mostCurrent._listahidro_promedio.Add(_newpunto.Get((Object)("valorind9")));
 //BA.debugLineNum = 1713;BA.debugLine="listaHidro_Promedio.Add(newpunto.Get(\"valorin";
parent.mostCurrent._listahidro_promedio.Add(_newpunto.Get((Object)("valorind10")));
 //BA.debugLineNum = 1714;BA.debugLine="listaAgua_Promedio.Add(newpunto.Get(\"valorind";
parent.mostCurrent._listaagua_promedio.Add(_newpunto.Get((Object)("valorind5")));
 //BA.debugLineNum = 1715;BA.debugLine="listaAgua_Promedio.Add(newpunto.Get(\"valorind";
parent.mostCurrent._listaagua_promedio.Add(_newpunto.Get((Object)("valorind6")));
 //BA.debugLineNum = 1716;BA.debugLine="listaAgua_Promedio.Add(newpunto.Get(\"valorind";
parent.mostCurrent._listaagua_promedio.Add(_newpunto.Get((Object)("valorind7")));
 //BA.debugLineNum = 1717;BA.debugLine="listaAgua_Promedio.Add(newpunto.Get(\"ind_pvm_";
parent.mostCurrent._listaagua_promedio.Add(_newpunto.Get((Object)("ind_pvm_1")));
 //BA.debugLineNum = 1718;BA.debugLine="listaBio_Promedio.Add(newpunto.Get(\"ind_pvm_2";
parent.mostCurrent._listabio_promedio.Add(_newpunto.Get((Object)("ind_pvm_2")));
 //BA.debugLineNum = 1719;BA.debugLine="listaBio_Promedio.Add(newpunto.Get(\"valorind3";
parent.mostCurrent._listabio_promedio.Add(_newpunto.Get((Object)("valorind3")));
 //BA.debugLineNum = 1720;BA.debugLine="listaBio_Promedio.Add(newpunto.Get(\"valorind4";
parent.mostCurrent._listabio_promedio.Add(_newpunto.Get((Object)("valorind4")));
 //BA.debugLineNum = 1721;BA.debugLine="listaUsos_Promedio.Add(newpunto.Get(\"valorind";
parent.mostCurrent._listausos_promedio.Add(_newpunto.Get((Object)("valorind1")));
 //BA.debugLineNum = 1722;BA.debugLine="listaUsos_Promedio.Add(newpunto.Get(\"valorind";
parent.mostCurrent._listausos_promedio.Add(_newpunto.Get((Object)("valorind2")));
 //BA.debugLineNum = 1723;BA.debugLine="listaExoticas_Promedio.Add(newpunto.Get(\"ind_";
parent.mostCurrent._listaexoticas_promedio.Add(_newpunto.Get((Object)("ind_pvm_3")));
 //BA.debugLineNum = 1724;BA.debugLine="listaExoticas_Promedio.Add(newpunto.Get(\"ind_";
parent.mostCurrent._listaexoticas_promedio.Add(_newpunto.Get((Object)("ind_pvm_4")));
 if (true) break;
if (true) break;
;
 //BA.debugLineNum = 1728;BA.debugLine="For m = 0 To listaPromedio.Size - 1";

case 71:
//for
this.state = 78;
step169 = 1;
limit169 = (int) (parent.mostCurrent._listapromedio.getSize()-1);
_m = (int) (0) ;
this.state = 109;
if (true) break;

case 109:
//C
this.state = 78;
if ((step169 > 0 && _m <= limit169) || (step169 < 0 && _m >= limit169)) this.state = 73;
if (true) break;

case 110:
//C
this.state = 109;
_m = ((int)(0 + _m + step169)) ;
if (true) break;

case 73:
//C
this.state = 74;
 //BA.debugLineNum = 1729;BA.debugLine="If listaPromedio.Get(m) <> \"\" Then";
if (true) break;

case 74:
//if
this.state = 77;
if ((parent.mostCurrent._listapromedio.Get(_m)).equals((Object)("")) == false) { 
this.state = 76;
}if (true) break;

case 76:
//C
this.state = 77;
 //BA.debugLineNum = 1730;BA.debugLine="valorEstado = valorEstado + listaPromedio.Ge";
parent._valorestado = parent._valorestado+(double)(BA.ObjectToNumber(parent.mostCurrent._listapromedio.Get(_m)));
 if (true) break;

case 77:
//C
this.state = 110;
;
 if (true) break;
if (true) break;

case 78:
//C
this.state = 79;
;
 //BA.debugLineNum = 1733;BA.debugLine="valorEstado = Round2(valorEstado / listaPromed";
parent._valorestado = anywheresoftware.b4a.keywords.Common.Round2(parent._valorestado/(double)parent.mostCurrent._listapromedio.getSize(),(int) (2));
 //BA.debugLineNum = 1734;BA.debugLine="Log(\"valorEstado: \" & valorEstado)";
anywheresoftware.b4a.keywords.Common.LogImpl("03604697","valorEstado: "+BA.NumberToString(parent._valorestado),0);
 //BA.debugLineNum = 1738;BA.debugLine="gauge_Partido.mBase.Visible = True";
parent.mostCurrent._gauge_partido._mbase /*anywheresoftware.b4a.objects.B4XViewWrapper*/ .setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1739;BA.debugLine="lblMunicipios_reportes.Visible = True";
parent.mostCurrent._lblmunicipios_reportes.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1740;BA.debugLine="gauge_Partido.SetRanges(Array(gauge_Partido.Cr";
parent.mostCurrent._gauge_partido._setranges /*String*/ (anywheresoftware.b4a.keywords.Common.ArrayToList(new Object[]{(Object)(parent.mostCurrent._gauge_partido._createrange /*appear.pnud.preservamos.gauge._gaugerange*/ ((float) (0),(float) (20),parent.mostCurrent._xui.Color_DarkGray)),(Object)(parent.mostCurrent._gauge_partido._createrange /*appear.pnud.preservamos.gauge._gaugerange*/ ((float) (20),(float) (40),parent.mostCurrent._xui.Color_Red)),(Object)(parent.mostCurrent._gauge_partido._createrange /*appear.pnud.preservamos.gauge._gaugerange*/ ((float) (40),(float) (60),parent.mostCurrent._xui.Color_Yellow)),(Object)(parent.mostCurrent._gauge_partido._createrange /*appear.pnud.preservamos.gauge._gaugerange*/ ((float) (60),(float) (80),parent.mostCurrent._xui.Color_Green)),(Object)(parent.mostCurrent._gauge_partido._createrange /*appear.pnud.preservamos.gauge._gaugerange*/ ((float) (80),(float) (100),parent.mostCurrent._xui.Color_Blue))}));
 //BA.debugLineNum = 1741;BA.debugLine="gauge_Partido.CurrentValue = 0";
parent.mostCurrent._gauge_partido._setcurrentvalue /*float*/ ((float) (0));
 //BA.debugLineNum = 1742;BA.debugLine="gauge_Partido.CurrentValue = valorEstado";
parent.mostCurrent._gauge_partido._setcurrentvalue /*float*/ ((float) (parent._valorestado));
 //BA.debugLineNum = 1744;BA.debugLine="If valorEstado < 20 Then";
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
 //BA.debugLineNum = 1745;BA.debugLine="lblMunicipio_estado.Text = \"Estado general: M";
parent.mostCurrent._lblmunicipio_estado.setText(BA.ObjectToCharSequence("Estado general: Muy malo"));
 if (true) break;

case 83:
//C
this.state = 90;
 //BA.debugLineNum = 1747;BA.debugLine="lblMunicipio_estado.Text = \"Estado general: M";
parent.mostCurrent._lblmunicipio_estado.setText(BA.ObjectToCharSequence("Estado general: Malo"));
 if (true) break;

case 85:
//C
this.state = 90;
 //BA.debugLineNum = 1749;BA.debugLine="lblMunicipio_estado.Text = \"Estado general: R";
parent.mostCurrent._lblmunicipio_estado.setText(BA.ObjectToCharSequence("Estado general: Regular"));
 if (true) break;

case 87:
//C
this.state = 90;
 //BA.debugLineNum = 1751;BA.debugLine="lblMunicipio_estado.Text = \"Estado general: B";
parent.mostCurrent._lblmunicipio_estado.setText(BA.ObjectToCharSequence("Estado general: Bueno"));
 if (true) break;

case 89:
//C
this.state = 90;
 //BA.debugLineNum = 1753;BA.debugLine="lblMunicipio_estado.Text = \"Estado general: M";
parent.mostCurrent._lblmunicipio_estado.setText(BA.ObjectToCharSequence("Estado general: Muy bueno!"));
 if (true) break;

case 90:
//C
this.state = 93;
;
 //BA.debugLineNum = 1755;BA.debugLine="lblMunicipio_estado.Text = lblMunicipio_estado";
parent.mostCurrent._lblmunicipio_estado.setText(BA.ObjectToCharSequence(parent.mostCurrent._lblmunicipio_estado.getText()));
 if (true) break;

case 92:
//C
this.state = 93;
 //BA.debugLineNum = 1757;BA.debugLine="Log(\"No tiene reportes\")";
anywheresoftware.b4a.keywords.Common.LogImpl("03604720","No tiene reportes",0);
 //BA.debugLineNum = 1758;BA.debugLine="btnVerDatosMunicipio.Visible = False";
parent.mostCurrent._btnverdatosmunicipio.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1759;BA.debugLine="btnVerEstadisticasMunicipio.Visible = False";
parent.mostCurrent._btnverestadisticasmunicipio.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1760;BA.debugLine="lblMunicipio_puntos_totales.Visible = False";
parent.mostCurrent._lblmunicipio_puntos_totales.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1761;BA.debugLine="lblMunicipio_estado.Text = \"Estado general: De";
parent.mostCurrent._lblmunicipio_estado.setText(BA.ObjectToCharSequence("Estado general: Desconocido"));
 //BA.debugLineNum = 1763;BA.debugLine="gauge_Partido.mBase.Visible = True";
parent.mostCurrent._gauge_partido._mbase /*anywheresoftware.b4a.objects.B4XViewWrapper*/ .setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1764;BA.debugLine="gauge_Partido.SetRanges(Array(gauge_Partido.Cr";
parent.mostCurrent._gauge_partido._setranges /*String*/ (anywheresoftware.b4a.keywords.Common.ArrayToList(new Object[]{(Object)(parent.mostCurrent._gauge_partido._createrange /*appear.pnud.preservamos.gauge._gaugerange*/ ((float) (0),(float) (20),parent.mostCurrent._xui.Color_DarkGray)),(Object)(parent.mostCurrent._gauge_partido._createrange /*appear.pnud.preservamos.gauge._gaugerange*/ ((float) (20),(float) (40),parent.mostCurrent._xui.Color_Red)),(Object)(parent.mostCurrent._gauge_partido._createrange /*appear.pnud.preservamos.gauge._gaugerange*/ ((float) (40),(float) (60),parent.mostCurrent._xui.Color_Yellow)),(Object)(parent.mostCurrent._gauge_partido._createrange /*appear.pnud.preservamos.gauge._gaugerange*/ ((float) (60),(float) (80),parent.mostCurrent._xui.Color_Green)),(Object)(parent.mostCurrent._gauge_partido._createrange /*appear.pnud.preservamos.gauge._gaugerange*/ ((float) (80),(float) (100),parent.mostCurrent._xui.Color_Blue))}));
 //BA.debugLineNum = 1765;BA.debugLine="gauge_Partido.CurrentValue = 0";
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
 //BA.debugLineNum = 1771;BA.debugLine="Msgbox2Async(\"No tenés conexión a Internet. No s";
anywheresoftware.b4a.keywords.Common.Msgbox2Async(BA.ObjectToCharSequence("No tenés conexión a Internet. No se puede buscar información sobre este municipio"),BA.ObjectToCharSequence("Advertencia"),"Ok, entiendo","","",(anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper(), (android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null)),processBA,anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1772;BA.debugLine="Wait For Msgbox_Result (Result As Int)";
anywheresoftware.b4a.keywords.Common.WaitFor("msgbox_result", processBA, this, null);
this.state = 111;
return;
case 111:
//C
this.state = 97;
_result = (Integer) result[0];
;
 //BA.debugLineNum = 1773;BA.debugLine="If Result = DialogResponse.POSITIVE Then";
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
 //BA.debugLineNum = 1774;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 if (true) break;

case 100:
//C
this.state = 101;
;
 //BA.debugLineNum = 1776;BA.debugLine="Return False";
if (true) {
anywheresoftware.b4a.keywords.Common.ReturnFromResumableSub(this,(Object)(anywheresoftware.b4a.keywords.Common.False));return;};
 if (true) break;

case 101:
//C
this.state = -1;
;
 //BA.debugLineNum = 1779;BA.debugLine="j.Release";
_j._release /*String*/ ();
 //BA.debugLineNum = 1780;BA.debugLine="Return True";
if (true) {
anywheresoftware.b4a.keywords.Common.ReturnFromResumableSub(this,(Object)(anywheresoftware.b4a.keywords.Common.True));return;};
 //BA.debugLineNum = 1782;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public static anywheresoftware.b4a.keywords.Common.ResumableSubWrapper  _update_perfil() throws Exception{
ResumableSub_update_perfil rsub = new ResumableSub_update_perfil(null);
rsub.resume(processBA, null);
return (anywheresoftware.b4a.keywords.Common.ResumableSubWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.keywords.Common.ResumableSubWrapper(), rsub);
}
public static class ResumableSub_update_perfil extends BA.ResumableSub {
public ResumableSub_update_perfil(appear.pnud.preservamos.form_main parent) {
this.parent = parent;
}
appear.pnud.preservamos.form_main parent;
int _result = 0;
boolean _completed = false;

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
 //BA.debugLineNum = 2132;BA.debugLine="If Main.hayinternet = True Then";
if (true) break;

case 1:
//if
this.state = 34;
if (parent.mostCurrent._main._hayinternet /*boolean*/ ==anywheresoftware.b4a.keywords.Common.True) { 
this.state = 3;
}else {
this.state = 5;
}if (true) break;

case 3:
//C
this.state = 34;
 //BA.debugLineNum = 2133;BA.debugLine="lblTitleProfile.Text = Main.username";
parent.mostCurrent._lbltitleprofile.setText(BA.ObjectToCharSequence(parent.mostCurrent._main._username /*String*/ ));
 //BA.debugLineNum = 2134;BA.debugLine="lblPerfil_puntos_totales.Text = Main.puntostotal";
parent.mostCurrent._lblperfil_puntos_totales.setText(BA.ObjectToCharSequence(parent.mostCurrent._main._puntostotales /*String*/ +" puntos"));
 //BA.debugLineNum = 2135;BA.debugLine="lblPuntos_Titulo_1.Text = Main.puntos_markers";
parent.mostCurrent._lblpuntos_titulo_1.setText(BA.ObjectToCharSequence(parent.mostCurrent._main._puntos_markers /*int*/ ));
 //BA.debugLineNum = 2136;BA.debugLine="lblPuntos_Titulo_2.Text = Main.puntos_evals_resi";
parent.mostCurrent._lblpuntos_titulo_2.setText(BA.ObjectToCharSequence(parent.mostCurrent._main._puntos_evals_residuos /*int*/ ));
 //BA.debugLineNum = 2137;BA.debugLine="lblPuntos_Titulo_3.Text = Main.puntos_evals_hidr";
parent.mostCurrent._lblpuntos_titulo_3.setText(BA.ObjectToCharSequence(parent.mostCurrent._main._puntos_evals_hidro /*int*/ ));
 //BA.debugLineNum = 2138;BA.debugLine="lblPerfil_puntos_totales.Visible = True";
parent.mostCurrent._lblperfil_puntos_totales.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 2139;BA.debugLine="lblPuntos_Titulo_1.Visible = True";
parent.mostCurrent._lblpuntos_titulo_1.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 2140;BA.debugLine="lblPuntos_Titulo_2.Visible = True";
parent.mostCurrent._lblpuntos_titulo_2.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 2141;BA.debugLine="lblPuntos_Titulo_3.Visible = True";
parent.mostCurrent._lblpuntos_titulo_3.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 2142;BA.debugLine="lblPuntos_Explicacion_1.Visible = True";
parent.mostCurrent._lblpuntos_explicacion_1.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 2143;BA.debugLine="lblPuntos_Explicacion_2.Visible = True";
parent.mostCurrent._lblpuntos_explicacion_2.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 2144;BA.debugLine="lblPuntos_Explicacion_3.Visible = True";
parent.mostCurrent._lblpuntos_explicacion_3.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 2145;BA.debugLine="lblNivel.Visible = True";
parent.mostCurrent._lblnivel.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 2146;BA.debugLine="lblPerfil_puntos_totales.Visible = True";
parent.mostCurrent._lblperfil_puntos_totales.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 2147;BA.debugLine="pgbLevel.Visible = True";
parent.mostCurrent._pgblevel.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 2148;BA.debugLine="Return True";
if (true) {
anywheresoftware.b4a.keywords.Common.ReturnFromResumableSub(this,(Object)(anywheresoftware.b4a.keywords.Common.True));return;};
 if (true) break;

case 5:
//C
this.state = 6;
 //BA.debugLineNum = 2151;BA.debugLine="lblTitleProfile.Visible = True";
parent.mostCurrent._lbltitleprofile.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 2152;BA.debugLine="lblTitleProfile.Text = \"\"";
parent.mostCurrent._lbltitleprofile.setText(BA.ObjectToCharSequence(""));
 //BA.debugLineNum = 2153;BA.debugLine="lblPerfil_puntos_totales.Visible = False";
parent.mostCurrent._lblperfil_puntos_totales.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 2154;BA.debugLine="lblPuntos_Titulo_1.Visible = False";
parent.mostCurrent._lblpuntos_titulo_1.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 2155;BA.debugLine="lblPuntos_Titulo_2.Visible = False";
parent.mostCurrent._lblpuntos_titulo_2.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 2156;BA.debugLine="lblPuntos_Titulo_3.Visible = False";
parent.mostCurrent._lblpuntos_titulo_3.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 2157;BA.debugLine="lblPuntos_Explicacion_1.Visible = False";
parent.mostCurrent._lblpuntos_explicacion_1.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 2158;BA.debugLine="lblPuntos_Explicacion_2.Visible = False";
parent.mostCurrent._lblpuntos_explicacion_2.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 2159;BA.debugLine="lblPuntos_Explicacion_3.Visible = False";
parent.mostCurrent._lblpuntos_explicacion_3.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 2160;BA.debugLine="lblNivel.Visible = False";
parent.mostCurrent._lblnivel.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 2161;BA.debugLine="lblPerfil_puntos_totales.Visible = False";
parent.mostCurrent._lblperfil_puntos_totales.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 2162;BA.debugLine="pgbLevel.Visible = False";
parent.mostCurrent._pgblevel.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 2163;BA.debugLine="Msgbox2Async(\"No se puede buscar información de";
anywheresoftware.b4a.keywords.Common.Msgbox2Async(BA.ObjectToCharSequence("No se puede buscar información de tu perfil sin internet"),BA.ObjectToCharSequence("Conectate a internet"),"Ok, entiendo","","Intentar conectar",(anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper(), (android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null)),processBA,anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 2164;BA.debugLine="Wait For Msgbox_Result (Result As Int)";
anywheresoftware.b4a.keywords.Common.WaitFor("msgbox_result", processBA, this, null);
this.state = 35;
return;
case 35:
//C
this.state = 6;
_result = (Integer) result[0];
;
 //BA.debugLineNum = 2165;BA.debugLine="If Result = DialogResponse.POSITIVE Then";
if (true) break;

case 6:
//if
this.state = 33;
if (_result==anywheresoftware.b4a.keywords.Common.DialogResponse.POSITIVE) { 
this.state = 8;
}else if(_result==anywheresoftware.b4a.keywords.Common.DialogResponse.NEGATIVE) { 
this.state = 10;
}if (true) break;

case 8:
//C
this.state = 33;
 //BA.debugLineNum = 2166;BA.debugLine="tabStripMain.ScrollTo(2, True)";
parent.mostCurrent._tabstripmain.ScrollTo((int) (2),anywheresoftware.b4a.keywords.Common.True);
 if (true) break;

case 10:
//C
this.state = 11;
 //BA.debugLineNum = 2168;BA.debugLine="Main.hayinternet = True";
parent.mostCurrent._main._hayinternet /*boolean*/  = anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 2169;BA.debugLine="Wait For (getPuntaje) Complete (Completed As Bo";
anywheresoftware.b4a.keywords.Common.WaitFor("complete", processBA, this, _getpuntaje());
this.state = 36;
return;
case 36:
//C
this.state = 11;
_completed = (Boolean) result[0];
;
 //BA.debugLineNum = 2170;BA.debugLine="If Completed = False Then";
if (true) break;

case 11:
//if
this.state = 16;
if (_completed==anywheresoftware.b4a.keywords.Common.False) { 
this.state = 13;
}else {
this.state = 15;
}if (true) break;

case 13:
//C
this.state = 16;
 //BA.debugLineNum = 2171;BA.debugLine="ToastMessageShow(\"No se encuentra tu puntaje..";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("No se encuentra tu puntaje..."),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 2172;BA.debugLine="lblNivel.Visible = False";
parent.mostCurrent._lblnivel.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 2173;BA.debugLine="lblPuntos_Explicacion_1.Visible = False";
parent.mostCurrent._lblpuntos_explicacion_1.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 2174;BA.debugLine="lblPuntos_Explicacion_2.Visible = False";
parent.mostCurrent._lblpuntos_explicacion_2.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 2175;BA.debugLine="lblPuntos_Explicacion_3.Visible = False";
parent.mostCurrent._lblpuntos_explicacion_3.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 2176;BA.debugLine="pgbLevel.Visible = False";
parent.mostCurrent._pgblevel.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 2177;BA.debugLine="lblPerfil_puntos_totales.Visible = True";
parent.mostCurrent._lblperfil_puntos_totales.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 2178;BA.debugLine="lblPerfil_puntos_totales.Text = \"No se encuent";
parent.mostCurrent._lblperfil_puntos_totales.setText(BA.ObjectToCharSequence("No se encuentran puntos para tu usuario aún. Completa análisis para ganar puntos!"));
 //BA.debugLineNum = 2179;BA.debugLine="Return";
if (true) {
anywheresoftware.b4a.keywords.Common.ReturnFromResumableSub(this,null);return;};
 if (true) break;

case 15:
//C
this.state = 16;
 //BA.debugLineNum = 2181;BA.debugLine="lblNivel.Visible = True";
parent.mostCurrent._lblnivel.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 2182;BA.debugLine="lblPuntos_Explicacion_1.Visible = True";
parent.mostCurrent._lblpuntos_explicacion_1.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 2183;BA.debugLine="lblPuntos_Explicacion_2.Visible = True";
parent.mostCurrent._lblpuntos_explicacion_2.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 2184;BA.debugLine="lblPuntos_Explicacion_3.Visible = True";
parent.mostCurrent._lblpuntos_explicacion_3.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 2185;BA.debugLine="pgbLevel.Visible = True";
parent.mostCurrent._pgblevel.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 2186;BA.debugLine="lblPerfil_puntos_totales.Visible = True";
parent.mostCurrent._lblperfil_puntos_totales.setVisible(anywheresoftware.b4a.keywords.Common.True);
 if (true) break;

case 16:
//C
this.state = 17;
;
 //BA.debugLineNum = 2188;BA.debugLine="Wait For (calcular_puntos_ambientes) Complete (";
anywheresoftware.b4a.keywords.Common.WaitFor("complete", processBA, this, _calcular_puntos_ambientes());
this.state = 37;
return;
case 37:
//C
this.state = 17;
_completed = (Boolean) result[0];
;
 //BA.debugLineNum = 2189;BA.debugLine="If Completed = False Then";
if (true) break;

case 17:
//if
this.state = 20;
if (_completed==anywheresoftware.b4a.keywords.Common.False) { 
this.state = 19;
}if (true) break;

case 19:
//C
this.state = 20;
 //BA.debugLineNum = 2190;BA.debugLine="Return False";
if (true) {
anywheresoftware.b4a.keywords.Common.ReturnFromResumableSub(this,(Object)(anywheresoftware.b4a.keywords.Common.False));return;};
 if (true) break;

case 20:
//C
this.state = 21;
;
 //BA.debugLineNum = 2192;BA.debugLine="Wait For (cargarNivel) Complete (Completed As B";
anywheresoftware.b4a.keywords.Common.WaitFor("complete", processBA, this, _cargarnivel());
this.state = 38;
return;
case 38:
//C
this.state = 21;
_completed = (Boolean) result[0];
;
 //BA.debugLineNum = 2193;BA.debugLine="If Completed = False Then";
if (true) break;

case 21:
//if
this.state = 24;
if (_completed==anywheresoftware.b4a.keywords.Common.False) { 
this.state = 23;
}if (true) break;

case 23:
//C
this.state = 24;
 //BA.debugLineNum = 2194;BA.debugLine="Return False";
if (true) {
anywheresoftware.b4a.keywords.Common.ReturnFromResumableSub(this,(Object)(anywheresoftware.b4a.keywords.Common.False));return;};
 if (true) break;

case 24:
//C
this.state = 25;
;
 //BA.debugLineNum = 2196;BA.debugLine="Wait For (cargarMedallas) Complete (Completed A";
anywheresoftware.b4a.keywords.Common.WaitFor("complete", processBA, this, _cargarmedallas());
this.state = 39;
return;
case 39:
//C
this.state = 25;
_completed = (Boolean) result[0];
;
 //BA.debugLineNum = 2197;BA.debugLine="If Completed = False Then";
if (true) break;

case 25:
//if
this.state = 28;
if (_completed==anywheresoftware.b4a.keywords.Common.False) { 
this.state = 27;
}if (true) break;

case 27:
//C
this.state = 28;
 //BA.debugLineNum = 2198;BA.debugLine="Return False";
if (true) {
anywheresoftware.b4a.keywords.Common.ReturnFromResumableSub(this,(Object)(anywheresoftware.b4a.keywords.Common.False));return;};
 if (true) break;

case 28:
//C
this.state = 29;
;
 //BA.debugLineNum = 2200;BA.debugLine="Wait For (update_perfil) Complete (Completed As";
anywheresoftware.b4a.keywords.Common.WaitFor("complete", processBA, this, _update_perfil());
this.state = 40;
return;
case 40:
//C
this.state = 29;
_completed = (Boolean) result[0];
;
 //BA.debugLineNum = 2201;BA.debugLine="If Completed = False Then";
if (true) break;

case 29:
//if
this.state = 32;
if (_completed==anywheresoftware.b4a.keywords.Common.False) { 
this.state = 31;
}if (true) break;

case 31:
//C
this.state = 32;
 //BA.debugLineNum = 2202;BA.debugLine="Return False";
if (true) {
anywheresoftware.b4a.keywords.Common.ReturnFromResumableSub(this,(Object)(anywheresoftware.b4a.keywords.Common.False));return;};
 if (true) break;

case 32:
//C
this.state = 33;
;
 //BA.debugLineNum = 2204;BA.debugLine="Return True";
if (true) {
anywheresoftware.b4a.keywords.Common.ReturnFromResumableSub(this,(Object)(anywheresoftware.b4a.keywords.Common.True));return;};
 if (true) break;

case 33:
//C
this.state = 34;
;
 //BA.debugLineNum = 2206;BA.debugLine="Return False";
if (true) {
anywheresoftware.b4a.keywords.Common.ReturnFromResumableSub(this,(Object)(anywheresoftware.b4a.keywords.Common.False));return;};
 if (true) break;

case 34:
//C
this.state = -1;
;
 //BA.debugLineNum = 2210;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
}
