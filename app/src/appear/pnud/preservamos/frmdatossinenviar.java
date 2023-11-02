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

public class frmdatossinenviar extends Activity implements B4AActivity{
	public static frmdatossinenviar mostCurrent;
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
			processBA = new BA(this.getApplicationContext(), null, null, "appear.pnud.preservamos", "appear.pnud.preservamos.frmdatossinenviar");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
            
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (frmdatossinenviar).");
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
		activityBA = new BA(this, layout, processBA, "appear.pnud.preservamos", "appear.pnud.preservamos.frmdatossinenviar");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "appear.pnud.preservamos.frmdatossinenviar", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density, mostCurrent);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (frmdatossinenviar) Create " + (isFirst ? "(first time)" : "") + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (frmdatossinenviar) Resume **");
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
		return frmdatossinenviar.class;
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
            BA.LogInfo("** Activity (frmdatossinenviar) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
        else
            BA.LogInfo("** Activity (frmdatossinenviar) Pause event (activity is not paused). **");
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
            frmdatossinenviar mc = mostCurrent;
			if (mc == null || mc != activity.get())
				return;
			processBA.setActivityPaused(false);
            BA.LogInfo("** Activity (frmdatossinenviar) Resume **");
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
public static anywheresoftware.b4h.okhttp.OkHttpClientWrapper _hc = null;
public static anywheresoftware.b4a.objects.streams.File.OutputStreamWrapper _out = null;
public static anywheresoftware.b4a.phone.Phone.ContentChooser _cc = null;
public static String _nuevalatlng = "";
public static boolean _notificacion = false;
public static String _serveridnum = "";
public static boolean _hayanteriores = false;
public static boolean _hayanteriores_residuos = false;
public static boolean _hayanteriores_hidro = false;
public static com.spinter.uploadfilephp.UploadFilePhp _up1 = null;
public static String _foto1 = "";
public static String _foto2 = "";
public static String _foto3 = "";
public static String _foto4 = "";
public static String _foto5 = "";
public static anywheresoftware.b4a.objects.Timer _timerenvio = null;
public static String _geopartido = "";
public static uk.co.martinpearman.b4a.location.GeocoderWrapper _georef = null;
public anywheresoftware.b4a.objects.RuntimePermissions _rp = null;
public anywheresoftware.b4a.objects.ListViewWrapper _listview1 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btncerrar = null;
public anywheresoftware.b4a.objects.TabStripViewPager _tabstripdatosanteriores = null;
public static String _tabactual = "";
public anywheresoftware.b4a.objects.LabelWrapper _lblfecha = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbltipo = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbllat = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblcalidad = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblnotas = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbllng = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper _chkpublico = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper _chkenviado = null;
public anywheresoftware.b4a.objects.LabelWrapper _chkvalidadoexpertos = null;
public anywheresoftware.b4a.objects.LabelWrapper _chkonline = null;
public anywheresoftware.b4a.objects.LabelWrapper _chkenviadobar = null;
public anywheresoftware.b4a.objects.HorizontalScrollViewWrapper _scvfotos = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblnombreenvio = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblbarra = null;
public static String _currentdatoid = "";
public static String _currentdatotipo = "";
public anywheresoftware.b4a.objects.ImageViewWrapper _foto1view = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _foto2view = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _foto3view = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _foto4view = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _foto5view = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _fotogrande = null;
public anywheresoftware.b4a.objects.LabelWrapper _fondogris = null;
public anywheresoftware.b4a.objects.ButtonWrapper _foto1borrar = null;
public anywheresoftware.b4a.objects.ButtonWrapper _foto2borrar = null;
public anywheresoftware.b4a.objects.ButtonWrapper _foto3borrar = null;
public anywheresoftware.b4a.objects.ButtonWrapper _foto4borrar = null;
public anywheresoftware.b4a.objects.ButtonWrapper _foto5borrar = null;
public static String _fotoadjuntar = "";
public anywheresoftware.b4a.objects.ButtonWrapper _btnenviar = null;
public static String _idproyectoenviar = "";
public anywheresoftware.b4a.objects.ButtonWrapper _btncambiarpublico = null;
public static boolean _newmarcador = false;
public static int _numbertasks = 0;
public anywheresoftware.b4a.agraham.dialogs.InputDialog.DateDialog _dated = null;
public static boolean _hubocambiosdatos = false;
public static boolean _hubocambiosfotos = false;
public anywheresoftware.b4a.objects.LabelWrapper _fondoblanco = null;
public anywheresoftware.b4a.objects.LabelWrapper _foto1prg = null;
public anywheresoftware.b4a.objects.LabelWrapper _foto2prg = null;
public anywheresoftware.b4a.objects.LabelWrapper _foto3prg = null;
public anywheresoftware.b4a.objects.LabelWrapper _foto4prg = null;
public anywheresoftware.b4a.objects.LabelWrapper _foto5prg = null;
public anywheresoftware.b4a.objects.ProgressBarWrapper _progressbar1 = null;
public anywheresoftware.b4a.objects.ProgressBarWrapper _progressbar2 = null;
public anywheresoftware.b4a.objects.ProgressBarWrapper _progressbar3 = null;
public anywheresoftware.b4a.objects.ProgressBarWrapper _progressbar4 = null;
public anywheresoftware.b4a.objects.ProgressBarWrapper _progressbar5 = null;
public static int _totalfotos = 0;
public static boolean _foto1sent = false;
public static boolean _foto2sent = false;
public static boolean _foto3sent = false;
public static boolean _foto4sent = false;
public static boolean _foto5sent = false;
public static int _fotosenviadas = 0;
public anywheresoftware.b4a.objects.LabelWrapper _label9 = null;
public anywheresoftware.b4a.objects.LabelWrapper _label6 = null;
public anywheresoftware.b4a.objects.LabelWrapper _label7 = null;
public anywheresoftware.b4a.objects.LabelWrapper _label8 = null;
public anywheresoftware.b4a.objects.LabelWrapper _label10 = null;
public anywheresoftware.b4a.objects.LabelWrapper _label12 = null;
public anywheresoftware.b4a.objects.LabelWrapper _label3 = null;
public anywheresoftware.b4a.objects.LabelWrapper _label2 = null;
public anywheresoftware.b4a.objects.LabelWrapper _label13 = null;
public anywheresoftware.b4a.objects.ListViewWrapper _lstenviosrealizados = null;
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
 //BA.debugLineNum = 132;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
 //BA.debugLineNum = 133;BA.debugLine="Activity.LoadLayout(\"layPerfilDatosAnteriores\")";
mostCurrent._activity.LoadLayout("layPerfilDatosAnteriores",mostCurrent.activityBA);
 //BA.debugLineNum = 136;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 137;BA.debugLine="tabStripDatosAnteriores.LoadLayout(\"layPerfilDat";
mostCurrent._tabstripdatosanteriores.LoadLayout("layPerfilDatosAnteriores_Lista",BA.ObjectToCharSequence("Datos sin enviar"));
 //BA.debugLineNum = 138;BA.debugLine="tabStripDatosAnteriores.LoadLayout(\"layPerfilDat";
mostCurrent._tabstripdatosanteriores.LoadLayout("layPerfilDatosAnteriores_Detalle",BA.ObjectToCharSequence("Detalle"));
 };
 //BA.debugLineNum = 143;BA.debugLine="foto1view.Initialize(\"foto1view\")";
mostCurrent._foto1view.Initialize(mostCurrent.activityBA,"foto1view");
 //BA.debugLineNum = 144;BA.debugLine="foto2view.Initialize(\"foto2view\")";
mostCurrent._foto2view.Initialize(mostCurrent.activityBA,"foto2view");
 //BA.debugLineNum = 145;BA.debugLine="foto3view.Initialize(\"foto2view\")";
mostCurrent._foto3view.Initialize(mostCurrent.activityBA,"foto2view");
 //BA.debugLineNum = 146;BA.debugLine="foto4view.Initialize(\"foto2view\")";
mostCurrent._foto4view.Initialize(mostCurrent.activityBA,"foto2view");
 //BA.debugLineNum = 147;BA.debugLine="foto5view.Initialize(\"foto2view\")";
mostCurrent._foto5view.Initialize(mostCurrent.activityBA,"foto2view");
 //BA.debugLineNum = 148;BA.debugLine="foto1Borrar.Initialize(\"foto1Borrar\")";
mostCurrent._foto1borrar.Initialize(mostCurrent.activityBA,"foto1Borrar");
 //BA.debugLineNum = 149;BA.debugLine="foto2Borrar.Initialize(\"foto2Borrar\")";
mostCurrent._foto2borrar.Initialize(mostCurrent.activityBA,"foto2Borrar");
 //BA.debugLineNum = 150;BA.debugLine="foto3Borrar.Initialize(\"foto2Borrar\")";
mostCurrent._foto3borrar.Initialize(mostCurrent.activityBA,"foto2Borrar");
 //BA.debugLineNum = 151;BA.debugLine="foto4Borrar.Initialize(\"foto2Borrar\")";
mostCurrent._foto4borrar.Initialize(mostCurrent.activityBA,"foto2Borrar");
 //BA.debugLineNum = 152;BA.debugLine="foto5Borrar.Initialize(\"foto2Borrar\")";
mostCurrent._foto5borrar.Initialize(mostCurrent.activityBA,"foto2Borrar");
 //BA.debugLineNum = 153;BA.debugLine="scvFotos.Panel.AddView(foto1view, 0dip, 0dip,scvF";
mostCurrent._scvfotos.getPanel().AddView((android.view.View)(mostCurrent._foto1view.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (0)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (0)),(int) (mostCurrent._scvfotos.getWidth()/(double)2),mostCurrent._scvfotos.getHeight());
 //BA.debugLineNum = 154;BA.debugLine="scvFotos.Panel.AddView(foto2view, (scvFotos.Width";
mostCurrent._scvfotos.getPanel().AddView((android.view.View)(mostCurrent._foto2view.getObject()),(int) ((mostCurrent._scvfotos.getWidth()/(double)2)*1),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (0)),(int) (mostCurrent._scvfotos.getWidth()/(double)2),mostCurrent._scvfotos.getHeight());
 //BA.debugLineNum = 155;BA.debugLine="scvFotos.Panel.AddView(foto3view, (scvFotos.Width";
mostCurrent._scvfotos.getPanel().AddView((android.view.View)(mostCurrent._foto3view.getObject()),(int) ((mostCurrent._scvfotos.getWidth()/(double)2)*2),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (0)),(int) (mostCurrent._scvfotos.getWidth()/(double)2),mostCurrent._scvfotos.getHeight());
 //BA.debugLineNum = 156;BA.debugLine="scvFotos.Panel.AddView(foto4view, (scvFotos.Width";
mostCurrent._scvfotos.getPanel().AddView((android.view.View)(mostCurrent._foto4view.getObject()),(int) ((mostCurrent._scvfotos.getWidth()/(double)2)*3),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (0)),(int) (mostCurrent._scvfotos.getWidth()/(double)2),mostCurrent._scvfotos.getHeight());
 //BA.debugLineNum = 157;BA.debugLine="scvFotos.Panel.AddView(foto5view, (scvFotos.Width";
mostCurrent._scvfotos.getPanel().AddView((android.view.View)(mostCurrent._foto5view.getObject()),(int) ((mostCurrent._scvfotos.getWidth()/(double)2)*4),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (0)),(int) (mostCurrent._scvfotos.getWidth()/(double)2),mostCurrent._scvfotos.getHeight());
 //BA.debugLineNum = 159;BA.debugLine="If hc.IsInitialized = False Then";
if (_hc.IsInitialized()==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 160;BA.debugLine="hc.Initialize(\"hc\")";
_hc.Initialize("hc");
 };
 //BA.debugLineNum = 163;BA.debugLine="Up1.B4A_log=True";
_up1.B4A_log = anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 164;BA.debugLine="Up1.Initialize(\"Up1\")";
_up1.Initialize(processBA,"Up1");
 //BA.debugLineNum = 167;BA.debugLine="End Sub";
return "";
}
public static boolean  _activity_keypress(int _keycode) throws Exception{
 //BA.debugLineNum = 177;BA.debugLine="Sub Activity_KeyPress (KeyCode As Int) As Boolean";
 //BA.debugLineNum = 179;BA.debugLine="If KeyCode = KeyCodes.KEYCODE_BACK Then";
if (_keycode==anywheresoftware.b4a.keywords.Common.KeyCodes.KEYCODE_BACK) { 
 //BA.debugLineNum = 180;BA.debugLine="If tabActual = \"Lista\" Then";
if ((mostCurrent._tabactual).equals("Lista")) { 
 //BA.debugLineNum = 181;BA.debugLine="Activity.finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 182;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 }else if((mostCurrent._tabactual).equals("Detalle")) { 
 //BA.debugLineNum = 184;BA.debugLine="tabStripDatosAnteriores.ScrollTo(0,True)";
mostCurrent._tabstripdatosanteriores.ScrollTo((int) (0),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 185;BA.debugLine="tabActual = \"Lista\"";
mostCurrent._tabactual = "Lista";
 //BA.debugLineNum = 186;BA.debugLine="Return True";
if (true) return anywheresoftware.b4a.keywords.Common.True;
 }else {
 //BA.debugLineNum = 188;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 189;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 };
 };
 //BA.debugLineNum = 192;BA.debugLine="End Sub";
return false;
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 174;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 176;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 168;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 169;BA.debugLine="If nuevalatlng = \"\" And notificacion = False Then";
if ((_nuevalatlng).equals("") && _notificacion==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 170;BA.debugLine="ListarDatos";
_listardatos();
 };
 //BA.debugLineNum = 173;BA.debugLine="End Sub";
return "";
}
public static void  _btncambiarfecha_click() throws Exception{
ResumableSub_btnCambiarFecha_Click rsub = new ResumableSub_btnCambiarFecha_Click(null);
rsub.resume(processBA, null);
}
public static class ResumableSub_btnCambiarFecha_Click extends BA.ResumableSub {
public ResumableSub_btnCambiarFecha_Click(appear.pnud.preservamos.frmdatossinenviar parent) {
this.parent = parent;
}
appear.pnud.preservamos.frmdatossinenviar parent;
int _result = 0;
String _fechanueva = "";
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
 //BA.debugLineNum = 1414;BA.debugLine="If Main.lang = \"es\" Then";
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
 //BA.debugLineNum = 1415;BA.debugLine="Msgbox2Async(\"Desea cambiar la fecha de este dat";
anywheresoftware.b4a.keywords.Common.Msgbox2Async(BA.ObjectToCharSequence("Desea cambiar la fecha de este dato?"),BA.ObjectToCharSequence("Cambiar fecha"),"Si","No","",(anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper(), (android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null)),processBA,anywheresoftware.b4a.keywords.Common.False);
 if (true) break;

case 5:
//C
this.state = 6;
 //BA.debugLineNum = 1417;BA.debugLine="Msgbox2Async(\"Do you want to change the date for";
anywheresoftware.b4a.keywords.Common.Msgbox2Async(BA.ObjectToCharSequence("Do you want to change the date for this report?"),BA.ObjectToCharSequence("Change date"),"Yes","No","",(anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper(), (android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null)),processBA,anywheresoftware.b4a.keywords.Common.False);
 if (true) break;

case 6:
//C
this.state = 7;
;
 //BA.debugLineNum = 1419;BA.debugLine="Wait For Msgbox_Result (Result As Int)";
anywheresoftware.b4a.keywords.Common.WaitFor("msgbox_result", processBA, this, null);
this.state = 21;
return;
case 21:
//C
this.state = 7;
_result = (Integer) result[0];
;
 //BA.debugLineNum = 1420;BA.debugLine="If Result = DialogResponse.POSITIVE Then	'Muestra";
if (true) break;

case 7:
//if
this.state = 20;
if (_result==anywheresoftware.b4a.keywords.Common.DialogResponse.POSITIVE) { 
this.state = 9;
}else {
this.state = 19;
}if (true) break;

case 9:
//C
this.state = 10;
 //BA.debugLineNum = 1421;BA.debugLine="Dim fechanueva As String";
_fechanueva = "";
 //BA.debugLineNum = 1422;BA.debugLine="dated.ShowCalendar = True";
parent.mostCurrent._dated.ShowCalendar = anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 1423;BA.debugLine="dated.Year = DateTime.GetYear(DateTime.now)";
parent.mostCurrent._dated.setYear(anywheresoftware.b4a.keywords.Common.DateTime.GetYear(anywheresoftware.b4a.keywords.Common.DateTime.getNow()));
 //BA.debugLineNum = 1424;BA.debugLine="dated.Month = DateTime.GetMonth(DateTime.now)";
parent.mostCurrent._dated.setMonth(anywheresoftware.b4a.keywords.Common.DateTime.GetMonth(anywheresoftware.b4a.keywords.Common.DateTime.getNow()));
 //BA.debugLineNum = 1425;BA.debugLine="dated.DayOfMonth = DateTime.GetDayOfMonth(DateTi";
parent.mostCurrent._dated.setDayOfMonth(anywheresoftware.b4a.keywords.Common.DateTime.GetDayOfMonth(anywheresoftware.b4a.keywords.Common.DateTime.getNow()));
 //BA.debugLineNum = 1426;BA.debugLine="dated.Show(\"Elija la nueva fecha para este dato\"";
parent.mostCurrent._dated.Show("Elija la nueva fecha para este dato","Cambio de fecha","Ok","Cancelar","",mostCurrent.activityBA,(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null));
 //BA.debugLineNum = 1427;BA.debugLine="fechanueva = dated.DayOfMonth & \"-\" & dated.Mont";
_fechanueva = BA.NumberToString(parent.mostCurrent._dated.getDayOfMonth())+"-"+BA.NumberToString(parent.mostCurrent._dated.getMonth())+"-"+BA.NumberToString(parent.mostCurrent._dated.getYear());
 //BA.debugLineNum = 1428;BA.debugLine="ToastMessageShow(\"Nueva fecha: \" & fechanueva, F";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Nueva fecha: "+_fechanueva),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1430;BA.debugLine="Dim Map1 As Map";
_map1 = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 1431;BA.debugLine="Map1.Initialize";
_map1.Initialize();
 //BA.debugLineNum = 1432;BA.debugLine="Map1.Put(\"Id\", currentDatoId)";
_map1.Put((Object)("Id"),(Object)(parent.mostCurrent._currentdatoid));
 //BA.debugLineNum = 1434;BA.debugLine="If 	currentDatoTipo = \"habitat\" Then";
if (true) break;

case 10:
//if
this.state = 17;
if ((parent.mostCurrent._currentdatotipo).equals("habitat")) { 
this.state = 12;
}else if((parent.mostCurrent._currentdatotipo).equals("residuos")) { 
this.state = 14;
}else if((parent.mostCurrent._currentdatotipo).equals("hidro")) { 
this.state = 16;
}if (true) break;

case 12:
//C
this.state = 17;
 //BA.debugLineNum = 1435;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"evals\", \"g";
parent.mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,parent.mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals","georeferencedDate",(Object)(_fechanueva),_map1);
 //BA.debugLineNum = 1437;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"evals\", \"e";
parent.mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,parent.mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals","evalsent",(Object)("no"),_map1);
 if (true) break;

case 14:
//C
this.state = 17;
 //BA.debugLineNum = 1439;BA.debugLine="DBUtils.UpdateRecord(Starter.modulo_residuos_sq";
parent.mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,parent.mostCurrent._starter._modulo_residuos_sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals_residuos","georeferencedDate",(Object)(_fechanueva),_map1);
 //BA.debugLineNum = 1441;BA.debugLine="DBUtils.UpdateRecord(Starter.modulo_residuos_sq";
parent.mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,parent.mostCurrent._starter._modulo_residuos_sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals_residuos","evalsent",(Object)("no"),_map1);
 if (true) break;

case 16:
//C
this.state = 17;
 //BA.debugLineNum = 1443;BA.debugLine="DBUtils.UpdateRecord(Starter.modulo_hidro_sqlDB";
parent.mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,parent.mostCurrent._starter._modulo_hidro_sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals_hidro","georeferencedDate",(Object)(_fechanueva),_map1);
 //BA.debugLineNum = 1445;BA.debugLine="DBUtils.UpdateRecord(Starter.modulo_hidro_sqlDB";
parent.mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,parent.mostCurrent._starter._modulo_hidro_sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals_hidro","evalsent",(Object)("no"),_map1);
 if (true) break;

case 17:
//C
this.state = 20;
;
 //BA.debugLineNum = 1449;BA.debugLine="lblFecha.Text = fechanueva";
parent.mostCurrent._lblfecha.setText(BA.ObjectToCharSequence(_fechanueva));
 //BA.debugLineNum = 1450;BA.debugLine="btnEnviar.Visible = True";
parent.mostCurrent._btnenviar.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1451;BA.debugLine="NewMarcador = False";
parent._newmarcador = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 1452;BA.debugLine="hubocambiosDatos = True";
parent._hubocambiosdatos = anywheresoftware.b4a.keywords.Common.True;
 if (true) break;

case 19:
//C
this.state = 20;
 //BA.debugLineNum = 1454;BA.debugLine="Return";
if (true) return ;
 if (true) break;

case 20:
//C
this.state = -1;
;
 //BA.debugLineNum = 1456;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public static void  _msgbox_result(int _result) throws Exception{
}
public static String  _btncambiarpublico_click() throws Exception{
anywheresoftware.b4a.objects.collections.Map _map1 = null;
 //BA.debugLineNum = 1518;BA.debugLine="Sub btnCambiarPublico_Click";
 //BA.debugLineNum = 1520;BA.debugLine="If chkPublico.Checked = True Then";
if (mostCurrent._chkpublico.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 1521;BA.debugLine="chkPublico.Checked = False";
mostCurrent._chkpublico.setChecked(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1522;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 1523;BA.debugLine="chkPublico.Text = \"Dato privado\"";
mostCurrent._chkpublico.setText(BA.ObjectToCharSequence("Dato privado"));
 //BA.debugLineNum = 1524;BA.debugLine="btnCambiarPublico.Text = \"Convertir en público\"";
mostCurrent._btncambiarpublico.setText(BA.ObjectToCharSequence("Convertir en público"));
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 1526;BA.debugLine="chkPublico.Text = \"Private report\"";
mostCurrent._chkpublico.setText(BA.ObjectToCharSequence("Private report"));
 //BA.debugLineNum = 1527;BA.debugLine="btnCambiarPublico.Text = \"Make report public\"";
mostCurrent._btncambiarpublico.setText(BA.ObjectToCharSequence("Make report public"));
 };
 //BA.debugLineNum = 1529;BA.debugLine="Dim Map1 As Map";
_map1 = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 1530;BA.debugLine="Map1.Initialize";
_map1.Initialize();
 //BA.debugLineNum = 1531;BA.debugLine="Map1.Put(\"Id\", currentDatoId)";
_map1.Put((Object)("Id"),(Object)(mostCurrent._currentdatoid));
 //BA.debugLineNum = 1534;BA.debugLine="If 	currentDatoTipo = \"habitat\" Then";
if ((mostCurrent._currentdatotipo).equals("habitat")) { 
 //BA.debugLineNum = 1535;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"evals\", \"p";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals","privado",(Object)("si"),_map1);
 //BA.debugLineNum = 1536;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"evals\", \"e";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals","evalsent",(Object)("no"),_map1);
 }else if((mostCurrent._currentdatotipo).equals("residuos")) { 
 //BA.debugLineNum = 1538;BA.debugLine="DBUtils.UpdateRecord(Starter.modulo_residuos_sq";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._modulo_residuos_sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals_residuos","privado",(Object)("si"),_map1);
 //BA.debugLineNum = 1539;BA.debugLine="DBUtils.UpdateRecord(Starter.modulo_residuos_sq";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._modulo_residuos_sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals_residuos","evalsent",(Object)("no"),_map1);
 }else if((mostCurrent._currentdatotipo).equals("hidro")) { 
 //BA.debugLineNum = 1541;BA.debugLine="DBUtils.UpdateRecord(Starter.modulo_hidro_sqlDB";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._modulo_hidro_sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals_hidro","privado",(Object)("si"),_map1);
 //BA.debugLineNum = 1542;BA.debugLine="DBUtils.UpdateRecord(Starter.modulo_hidro_sqlDB";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._modulo_hidro_sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals_hidro","evalsent",(Object)("no"),_map1);
 };
 //BA.debugLineNum = 1544;BA.debugLine="NewMarcador = False";
_newmarcador = anywheresoftware.b4a.keywords.Common.False;
 }else {
 //BA.debugLineNum = 1546;BA.debugLine="chkPublico.Checked = True";
mostCurrent._chkpublico.setChecked(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1547;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 1548;BA.debugLine="chkPublico.Text = \"Dato público\"";
mostCurrent._chkpublico.setText(BA.ObjectToCharSequence("Dato público"));
 //BA.debugLineNum = 1549;BA.debugLine="btnCambiarPublico.Text = \"Convertir en privado\"";
mostCurrent._btncambiarpublico.setText(BA.ObjectToCharSequence("Convertir en privado"));
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 1551;BA.debugLine="chkPublico.Text = \"Public report\"";
mostCurrent._chkpublico.setText(BA.ObjectToCharSequence("Public report"));
 //BA.debugLineNum = 1552;BA.debugLine="btnCambiarPublico.Text = \"Make report private\"";
mostCurrent._btncambiarpublico.setText(BA.ObjectToCharSequence("Make report private"));
 };
 //BA.debugLineNum = 1554;BA.debugLine="Dim Map1 As Map";
_map1 = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 1555;BA.debugLine="Map1.Initialize";
_map1.Initialize();
 //BA.debugLineNum = 1556;BA.debugLine="Map1.Put(\"Id\", currentDatoId)";
_map1.Put((Object)("Id"),(Object)(mostCurrent._currentdatoid));
 //BA.debugLineNum = 1557;BA.debugLine="If 	currentDatoTipo = \"habitat\" Then";
if ((mostCurrent._currentdatotipo).equals("habitat")) { 
 //BA.debugLineNum = 1558;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"evals\", \"p";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals","privado",(Object)("no"),_map1);
 //BA.debugLineNum = 1559;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"evals\", \"e";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals","evalsent",(Object)("no"),_map1);
 }else if((mostCurrent._currentdatotipo).equals("residuos")) { 
 //BA.debugLineNum = 1561;BA.debugLine="DBUtils.UpdateRecord(Starter.modulo_residuos_sq";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._modulo_residuos_sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals_residuos","privado",(Object)("no"),_map1);
 //BA.debugLineNum = 1562;BA.debugLine="DBUtils.UpdateRecord(Starter.modulo_residuos_sq";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._modulo_residuos_sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals_residuos","evalsent",(Object)("no"),_map1);
 }else if((mostCurrent._currentdatotipo).equals("hidro")) { 
 //BA.debugLineNum = 1564;BA.debugLine="DBUtils.UpdateRecord(Starter.modulo_hidro_sqlDB";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._modulo_hidro_sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals_hidro","privado",(Object)("no"),_map1);
 //BA.debugLineNum = 1565;BA.debugLine="DBUtils.UpdateRecord(Starter.modulo_hidro_sqlDB";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._modulo_hidro_sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals_hidro","evalsent",(Object)("no"),_map1);
 };
 //BA.debugLineNum = 1567;BA.debugLine="NewMarcador = False";
_newmarcador = anywheresoftware.b4a.keywords.Common.False;
 };
 //BA.debugLineNum = 1569;BA.debugLine="btnEnviar.Visible = True";
mostCurrent._btnenviar.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1570;BA.debugLine="hubocambiosDatos = True";
_hubocambiosdatos = anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 1571;BA.debugLine="End Sub";
return "";
}
public static void  _btncambiarubicacion_click() throws Exception{
ResumableSub_btnCambiarUbicacion_Click rsub = new ResumableSub_btnCambiarUbicacion_Click(null);
rsub.resume(processBA, null);
}
public static class ResumableSub_btnCambiarUbicacion_Click extends BA.ResumableSub {
public ResumableSub_btnCambiarUbicacion_Click(appear.pnud.preservamos.frmdatossinenviar parent) {
this.parent = parent;
}
appear.pnud.preservamos.frmdatossinenviar parent;
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
 //BA.debugLineNum = 1458;BA.debugLine="If Main.lang = \"es\" Then";
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
 //BA.debugLineNum = 1459;BA.debugLine="Msgbox2Async(\"Desea cambiar la ubicación de este";
anywheresoftware.b4a.keywords.Common.Msgbox2Async(BA.ObjectToCharSequence("Desea cambiar la ubicación de este dato?"),BA.ObjectToCharSequence("Cambiar ubicación"),"Si","No","",(anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper(), (android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null)),processBA,anywheresoftware.b4a.keywords.Common.False);
 if (true) break;

case 5:
//C
this.state = 6;
 //BA.debugLineNum = 1461;BA.debugLine="Msgbox2Async(\"Do you want to change the location";
anywheresoftware.b4a.keywords.Common.Msgbox2Async(BA.ObjectToCharSequence("Do you want to change the location for this report?"),BA.ObjectToCharSequence("Change location"),"Yes","No","",(anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper(), (android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null)),processBA,anywheresoftware.b4a.keywords.Common.False);
 if (true) break;

case 6:
//C
this.state = 7;
;
 //BA.debugLineNum = 1463;BA.debugLine="Wait For Msgbox_Result (Result As Int)";
anywheresoftware.b4a.keywords.Common.WaitFor("msgbox_result", processBA, this, null);
this.state = 13;
return;
case 13:
//C
this.state = 7;
_result = (Integer) result[0];
;
 //BA.debugLineNum = 1464;BA.debugLine="If Result = DialogResponse.POSITIVE Then";
if (true) break;

case 7:
//if
this.state = 12;
if (_result==anywheresoftware.b4a.keywords.Common.DialogResponse.POSITIVE) { 
this.state = 9;
}else {
this.state = 11;
}if (true) break;

case 9:
//C
this.state = 12;
 //BA.debugLineNum = 1465;BA.debugLine="nuevalatlng = \"\"";
parent._nuevalatlng = "";
 //BA.debugLineNum = 1466;BA.debugLine="frmLocalizacion.origen = \"cambio\"";
parent.mostCurrent._frmlocalizacion._origen /*String*/  = "cambio";
 //BA.debugLineNum = 1467;BA.debugLine="hubocambiosDatos = True";
parent._hubocambiosdatos = anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 1468;BA.debugLine="StartActivity(frmLocalizacion)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(parent.mostCurrent._frmlocalizacion.getObject()));
 if (true) break;

case 11:
//C
this.state = 12;
 //BA.debugLineNum = 1471;BA.debugLine="Return";
if (true) return ;
 if (true) break;

case 12:
//C
this.state = -1;
;
 //BA.debugLineNum = 1473;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public static String  _btncerrar_click() throws Exception{
 //BA.debugLineNum = 220;BA.debugLine="Sub btnCerrar_Click";
 //BA.debugLineNum = 221;BA.debugLine="If tabStripDatosAnteriores.CurrentPage = 0 Then";
if (mostCurrent._tabstripdatosanteriores.getCurrentPage()==0) { 
 //BA.debugLineNum = 222;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 223;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 }else {
 //BA.debugLineNum = 225;BA.debugLine="tabStripDatosAnteriores.ScrollTo(0,True)";
mostCurrent._tabstripdatosanteriores.ScrollTo((int) (0),anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 229;BA.debugLine="End Sub";
return "";
}
public static String  _btnenviar_click() throws Exception{
 //BA.debugLineNum = 1585;BA.debugLine="Sub btnEnviar_Click";
 //BA.debugLineNum = 1587;BA.debugLine="ProgressDialogShow2(\"Enviando dato...\", False)";
anywheresoftware.b4a.keywords.Common.ProgressDialogShow2(mostCurrent.activityBA,BA.ObjectToCharSequence("Enviando dato..."),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1588;BA.debugLine="Log(\"Chequeando internet\")";
anywheresoftware.b4a.keywords.Common.LogImpl("443581443","Chequeando internet",0);
 //BA.debugLineNum = 1589;BA.debugLine="CheckInternet";
_checkinternet();
 //BA.debugLineNum = 1591;BA.debugLine="End Sub";
return "";
}
public static String  _buscarmunicipio() throws Exception{
double _geolatitude = 0;
double _geolongitude = 0;
 //BA.debugLineNum = 1630;BA.debugLine="Sub BuscarMunicipio";
 //BA.debugLineNum = 1632;BA.debugLine="georef.Initialize(\"georef\")";
_georef.Initialize(processBA,"georef");
 //BA.debugLineNum = 1633;BA.debugLine="Dim GeoLatitude, GeoLongitude As Double";
_geolatitude = 0;
_geolongitude = 0;
 //BA.debugLineNum = 1634;BA.debugLine="GeoLatitude = lblLat.Text";
_geolatitude = (double)(Double.parseDouble(mostCurrent._lbllat.getText()));
 //BA.debugLineNum = 1635;BA.debugLine="GeoLongitude = lblLng.Text";
_geolongitude = (double)(Double.parseDouble(mostCurrent._lbllng.getText()));
 //BA.debugLineNum = 1636;BA.debugLine="georef.GetFromLocation(GeoLatitude, GeoLongitude,";
_georef.GetFromLocation(processBA,_geolatitude,_geolongitude,(int) (10),anywheresoftware.b4a.keywords.Common.Null);
 //BA.debugLineNum = 1639;BA.debugLine="End Sub";
return "";
}
public static String  _cambiarubicacion() throws Exception{
String _newlat = "";
String _newlng = "";
String[] _arr = null;
anywheresoftware.b4a.objects.collections.Map _map1 = null;
 //BA.debugLineNum = 1474;BA.debugLine="Sub CambiarUbicacion";
 //BA.debugLineNum = 1475;BA.debugLine="Dim newlat As String";
_newlat = "";
 //BA.debugLineNum = 1476;BA.debugLine="Dim newlng As String";
_newlng = "";
 //BA.debugLineNum = 1478;BA.debugLine="If nuevalatlng = \"\" Then";
if ((_nuevalatlng).equals("")) { 
 //BA.debugLineNum = 1479;BA.debugLine="Return";
if (true) return "";
 }else {
 //BA.debugLineNum = 1481;BA.debugLine="Dim arr() As String = Regex.Split(\"_\", nuevalatl";
_arr = anywheresoftware.b4a.keywords.Common.Regex.Split("_",_nuevalatlng);
 //BA.debugLineNum = 1482;BA.debugLine="newlat = arr(0)";
_newlat = _arr[(int) (0)];
 //BA.debugLineNum = 1483;BA.debugLine="newlng = arr(1)";
_newlng = _arr[(int) (1)];
 };
 //BA.debugLineNum = 1487;BA.debugLine="Dim Map1 As Map";
_map1 = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 1488;BA.debugLine="Map1.Initialize";
_map1.Initialize();
 //BA.debugLineNum = 1489;BA.debugLine="Map1.Put(\"Id\", currentDatoId)";
_map1.Put((Object)("Id"),(Object)(mostCurrent._currentdatoid));
 //BA.debugLineNum = 1491;BA.debugLine="If 	currentDatoTipo = \"habitat\" Then";
if ((mostCurrent._currentdatotipo).equals("habitat")) { 
 //BA.debugLineNum = 1492;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"evals\", \"de";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals","decimalLatitude",(Object)(_newlat),_map1);
 //BA.debugLineNum = 1493;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"evals\", \"de";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals","decimalLongitude",(Object)(_newlng),_map1);
 //BA.debugLineNum = 1495;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"evals\", \"ev";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals","evalsent",(Object)("no"),_map1);
 }else if((mostCurrent._currentdatotipo).equals("residuos")) { 
 //BA.debugLineNum = 1497;BA.debugLine="DBUtils.UpdateRecord(Starter.modulo_residuos_sql";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._modulo_residuos_sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals_residuos","decimalLatitude",(Object)(_newlat),_map1);
 //BA.debugLineNum = 1498;BA.debugLine="DBUtils.UpdateRecord(Starter.modulo_residuos_sql";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._modulo_residuos_sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals_residuos","decimalLongitude",(Object)(_newlng),_map1);
 //BA.debugLineNum = 1500;BA.debugLine="DBUtils.UpdateRecord(Starter.modulo_residuos_sql";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._modulo_residuos_sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals_residuos","evalsent",(Object)("no"),_map1);
 }else if((mostCurrent._currentdatotipo).equals("hidro")) { 
 //BA.debugLineNum = 1502;BA.debugLine="DBUtils.UpdateRecord(Starter.modulo_hidro_sqlDB,";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._modulo_hidro_sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals_hidro","decimalLatitude",(Object)(_newlat),_map1);
 //BA.debugLineNum = 1503;BA.debugLine="DBUtils.UpdateRecord(Starter.modulo_hidro_sqlDB,";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._modulo_hidro_sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals_hidro","decimalLongitude",(Object)(_newlng),_map1);
 //BA.debugLineNum = 1505;BA.debugLine="DBUtils.UpdateRecord(Starter.modulo_hidro_sqlDB,";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._modulo_hidro_sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals_hidro","evalsent",(Object)("no"),_map1);
 };
 //BA.debugLineNum = 1508;BA.debugLine="lblLat.text = newlat";
mostCurrent._lbllat.setText(BA.ObjectToCharSequence(_newlat));
 //BA.debugLineNum = 1509;BA.debugLine="lblLng.text = newlng";
mostCurrent._lbllng.setText(BA.ObjectToCharSequence(_newlng));
 //BA.debugLineNum = 1510;BA.debugLine="nuevalatlng = \"\"";
_nuevalatlng = "";
 //BA.debugLineNum = 1512;BA.debugLine="btnEnviar.Visible = True";
mostCurrent._btnenviar.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1513;BA.debugLine="NewMarcador = False";
_newmarcador = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 1514;BA.debugLine="End Sub";
return "";
}
public static String  _cc_result(boolean _success,String _dir,String _filename) throws Exception{
anywheresoftware.b4a.objects.collections.Map _map1 = null;
String _fotonombredestino = "";
 //BA.debugLineNum = 1352;BA.debugLine="Sub CC_Result (Success As Boolean, Dir As String,";
 //BA.debugLineNum = 1353;BA.debugLine="If Success = True Then";
if (_success==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 1356;BA.debugLine="Dim Map1 As Map";
_map1 = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 1357;BA.debugLine="Map1.Initialize";
_map1.Initialize();
 //BA.debugLineNum = 1358;BA.debugLine="Map1.Put(\"Id\", IdProyectoEnviar)";
_map1.Put((Object)("Id"),(Object)(mostCurrent._idproyectoenviar));
 //BA.debugLineNum = 1359;BA.debugLine="DateTime.DateFormat = \"dd-MM-yyyy\"";
anywheresoftware.b4a.keywords.Common.DateTime.setDateFormat("dd-MM-yyyy");
 //BA.debugLineNum = 1360;BA.debugLine="Dim fotoNombreDestino As String";
_fotonombredestino = "";
 //BA.debugLineNum = 1361;BA.debugLine="hubocambiosFotos = True";
_hubocambiosfotos = anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 1362;BA.debugLine="btnEnviar.Visible = True";
mostCurrent._btnenviar.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1363;BA.debugLine="If fotoAdjuntar = \"foto1\" Then";
if ((mostCurrent._fotoadjuntar).equals("foto1")) { 
 //BA.debugLineNum = 1364;BA.debugLine="fotoNombreDestino = Form_Main.fullidcurrentproj";
_fotonombredestino = mostCurrent._form_main._fullidcurrentproject /*String*/ +"_1";
 //BA.debugLineNum = 1366;BA.debugLine="File.Copy(Dir, FileName, rp.GetSafeDirDefaultEx";
anywheresoftware.b4a.keywords.Common.File.Copy(_dir,_filename,mostCurrent._rp.GetSafeDirDefaultExternal("PreserVamos"),_fotonombredestino+".jpg");
 //BA.debugLineNum = 1368;BA.debugLine="If 	currentDatoTipo = \"habitat\" Then";
if ((mostCurrent._currentdatotipo).equals("habitat")) { 
 //BA.debugLineNum = 1369;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"evals\", \"";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals","foto1",(Object)(_fotonombredestino),_map1);
 }else if((mostCurrent._currentdatotipo).equals("residuos")) { 
 //BA.debugLineNum = 1371;BA.debugLine="DBUtils.UpdateRecord(Starter.modulo_hidro_sqlD";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._modulo_hidro_sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals_residuos","foto1",(Object)(_fotonombredestino),_map1);
 }else if((mostCurrent._currentdatotipo).equals("hidro")) { 
 //BA.debugLineNum = 1373;BA.debugLine="DBUtils.UpdateRecord(Starter.modulo_hidro_sqlD";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._modulo_hidro_sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals_hidro","foto1",(Object)(_fotonombredestino),_map1);
 };
 //BA.debugLineNum = 1375;BA.debugLine="foto1view.Bitmap= LoadBitmapSample(rp.GetSafeDi";
mostCurrent._foto1view.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(mostCurrent._rp.GetSafeDirDefaultExternal("PreserVamos"),_fotonombredestino+".jpg",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 }else if((mostCurrent._fotoadjuntar).equals("foto2")) { 
 //BA.debugLineNum = 1378;BA.debugLine="fotoNombreDestino = Form_Main.fullidcurrentproj";
_fotonombredestino = mostCurrent._form_main._fullidcurrentproject /*String*/ +"_2";
 //BA.debugLineNum = 1379;BA.debugLine="File.Copy(Dir, FileName, rp.GetSafeDirDefaultEx";
anywheresoftware.b4a.keywords.Common.File.Copy(_dir,_filename,mostCurrent._rp.GetSafeDirDefaultExternal("PreserVamos"),_fotonombredestino+".jpg");
 //BA.debugLineNum = 1380;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"evals\", \"f";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals","foto2",(Object)(_fotonombredestino),_map1);
 //BA.debugLineNum = 1381;BA.debugLine="foto2view.Bitmap= LoadBitmapSample(rp.GetSafeDi";
mostCurrent._foto2view.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(mostCurrent._rp.GetSafeDirDefaultExternal("PreserVamos"),_fotonombredestino+".jpg",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 }else if((mostCurrent._fotoadjuntar).equals("foto3")) { 
 //BA.debugLineNum = 1383;BA.debugLine="fotoNombreDestino = Form_Main.fullidcurrentproj";
_fotonombredestino = mostCurrent._form_main._fullidcurrentproject /*String*/ +"_3";
 //BA.debugLineNum = 1384;BA.debugLine="File.Copy(Dir, FileName, rp.GetSafeDirDefaultEx";
anywheresoftware.b4a.keywords.Common.File.Copy(_dir,_filename,mostCurrent._rp.GetSafeDirDefaultExternal("PreserVamos"),_fotonombredestino+".jpg");
 //BA.debugLineNum = 1385;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"evals\", \"f";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals","foto3",(Object)(_fotonombredestino),_map1);
 //BA.debugLineNum = 1386;BA.debugLine="foto3view.Bitmap= LoadBitmapSample(rp.GetSafeDi";
mostCurrent._foto3view.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(mostCurrent._rp.GetSafeDirDefaultExternal("PreserVamos"),_fotonombredestino+".jpg",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 }else if((mostCurrent._fotoadjuntar).equals("foto4")) { 
 //BA.debugLineNum = 1388;BA.debugLine="fotoNombreDestino = Form_Main.fullidcurrentproj";
_fotonombredestino = mostCurrent._form_main._fullidcurrentproject /*String*/ +"_4";
 //BA.debugLineNum = 1389;BA.debugLine="File.Copy(Dir, FileName, rp.GetSafeDirDefaultEx";
anywheresoftware.b4a.keywords.Common.File.Copy(_dir,_filename,mostCurrent._rp.GetSafeDirDefaultExternal("PreserVamos"),_fotonombredestino+".jpg");
 //BA.debugLineNum = 1390;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"evals\", \"f";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals","foto4",(Object)(_fotonombredestino),_map1);
 //BA.debugLineNum = 1391;BA.debugLine="foto4view.Bitmap= LoadBitmapSample(rp.GetSafeDi";
mostCurrent._foto4view.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(mostCurrent._rp.GetSafeDirDefaultExternal("PreserVamos"),_fotonombredestino+".jpg",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 }else if((mostCurrent._fotoadjuntar).equals("foto5")) { 
 //BA.debugLineNum = 1393;BA.debugLine="fotoNombreDestino = Form_Main.fullidcurrentproj";
_fotonombredestino = mostCurrent._form_main._fullidcurrentproject /*String*/ +"_5";
 //BA.debugLineNum = 1394;BA.debugLine="File.Copy(Dir, FileName, rp.GetSafeDirDefaultEx";
anywheresoftware.b4a.keywords.Common.File.Copy(_dir,_filename,mostCurrent._rp.GetSafeDirDefaultExternal("PreserVamos"),_fotonombredestino+".jpg");
 //BA.debugLineNum = 1395;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"evals\", \"f";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals","foto5",(Object)(_fotonombredestino),_map1);
 //BA.debugLineNum = 1396;BA.debugLine="foto5view.Bitmap= LoadBitmapSample(rp.GetSafeDi";
mostCurrent._foto5view.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(mostCurrent._rp.GetSafeDirDefaultExternal("PreserVamos"),_fotonombredestino+".jpg",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 };
 };
 //BA.debugLineNum = 1401;BA.debugLine="End Sub";
return "";
}
public static String  _checkinternet() throws Exception{
appear.pnud.preservamos.downloadservice._downloaddata _dd = null;
 //BA.debugLineNum = 1596;BA.debugLine="Sub CheckInternet";
 //BA.debugLineNum = 1597;BA.debugLine="Dim dd As DownloadData";
_dd = new appear.pnud.preservamos.downloadservice._downloaddata();
 //BA.debugLineNum = 1598;BA.debugLine="dd.url = Main.serverPath &  \"/\" & Main.serverConn";
_dd.url /*String*/  = mostCurrent._main._serverpath /*String*/ +"/"+mostCurrent._main._serverconnectionfolder /*String*/ +"/connecttest.php";
 //BA.debugLineNum = 1599;BA.debugLine="dd.EventName = \"TestInternet\"";
_dd.EventName /*String*/  = "TestInternet";
 //BA.debugLineNum = 1600;BA.debugLine="dd.Target = Me";
_dd.Target /*Object*/  = frmdatossinenviar.getObject();
 //BA.debugLineNum = 1601;BA.debugLine="CallSubDelayed2(DownloadService, \"StartDownload\",";
anywheresoftware.b4a.keywords.Common.CallSubDelayed2(processBA,(Object)(mostCurrent._downloadservice.getObject()),"StartDownload",(Object)(_dd));
 //BA.debugLineNum = 1602;BA.debugLine="End Sub";
return "";
}
public static String  _enviardatos(String _proyectonumero) throws Exception{
String _username = "";
String _useremail = "";
String _dateandtime = "";
String _nombresitio = "";
String _tiporio = "";
String _lat = "";
String _lng = "";
String _gpsdetect = "";
String _wifidetect = "";
String _mapadetect = "";
String _partido = "";
String _valorcalidad = "";
String _valorns = "";
String _valorind1 = "";
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
String _ind_pvm_1 = "";
String _ind_pvm_2 = "";
String _ind_pvm_3 = "";
String _ind_pvm_4 = "";
String _ind_pvm_5 = "";
String _ind_pvm_6 = "";
String _ind_pvm_7 = "";
String _ind_pvm_8 = "";
String _ind_pvm_9 = "";
String _ind_pvm_10 = "";
String _ind_pvm_11 = "";
String _ind_pvm_12 = "";
String _notas = "";
String _bingo = "";
String _terminado = "";
String _privado = "";
String _estadovalidacion = "";
String _deviceid = "";
String _serveridupdate = "";
anywheresoftware.b4a.objects.collections.Map _datosmap = null;
int _conf = 0;
String _dirweb = "";
appear.pnud.preservamos.downloadservice._downloaddata _dd = null;
 //BA.debugLineNum = 1666;BA.debugLine="Sub EnviarDatos(proyectonumero As String)";
 //BA.debugLineNum = 1670;BA.debugLine="Dim username,useremail,dateandtime,nombresitio,ti";
_username = "";
_useremail = "";
_dateandtime = "";
_nombresitio = "";
_tiporio = "";
_lat = "";
_lng = "";
_gpsdetect = "";
_wifidetect = "";
_mapadetect = "";
_partido = "";
 //BA.debugLineNum = 1671;BA.debugLine="Dim valorcalidad, valorNS, valorind1,valorind2,va";
_valorcalidad = "";
_valorns = "";
_valorind1 = "";
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
 //BA.debugLineNum = 1673;BA.debugLine="Dim ind_pvm_1, ind_pvm_2, ind_pvm_3, ind_pvm_4, i";
_ind_pvm_1 = "";
_ind_pvm_2 = "";
_ind_pvm_3 = "";
_ind_pvm_4 = "";
_ind_pvm_5 = "";
_ind_pvm_6 = "";
_ind_pvm_7 = "";
_ind_pvm_8 = "";
_ind_pvm_9 = "";
_ind_pvm_10 = "";
_ind_pvm_11 = "";
_ind_pvm_12 = "";
 //BA.debugLineNum = 1674;BA.debugLine="Dim notas,bingo As String";
_notas = "";
_bingo = "";
 //BA.debugLineNum = 1675;BA.debugLine="Dim terminado, privado,estadovalidacion, deviceID";
_terminado = "";
_privado = "";
_estadovalidacion = "";
_deviceid = "";
_serveridupdate = "";
 //BA.debugLineNum = 1679;BA.debugLine="Dim datosMap As Map";
_datosmap = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 1680;BA.debugLine="datosMap.Initialize";
_datosmap.Initialize();
 //BA.debugLineNum = 1682;BA.debugLine="If 	currentDatoTipo = \"habitat\" Then";
if ((mostCurrent._currentdatotipo).equals("habitat")) { 
 //BA.debugLineNum = 1683;BA.debugLine="datosMap = DBUtils.ExecuteMap(Starter.sqlDB, \"SE";
_datosmap = mostCurrent._dbutils._executemap /*anywheresoftware.b4a.objects.collections.Map*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"SELECT * FROM evals WHERE Id=?",new String[]{_proyectonumero});
 }else if((mostCurrent._currentdatotipo).equals("residuos")) { 
 //BA.debugLineNum = 1685;BA.debugLine="datosMap = DBUtils.ExecuteMap(Starter.modulo_res";
_datosmap = mostCurrent._dbutils._executemap /*anywheresoftware.b4a.objects.collections.Map*/ (mostCurrent.activityBA,mostCurrent._starter._modulo_residuos_sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"SELECT * FROM evals_residuos WHERE Id=?",new String[]{_proyectonumero});
 }else if((mostCurrent._currentdatotipo).equals("hidro")) { 
 //BA.debugLineNum = 1687;BA.debugLine="datosMap = DBUtils.ExecuteMap(Starter.modulo_hid";
_datosmap = mostCurrent._dbutils._executemap /*anywheresoftware.b4a.objects.collections.Map*/ (mostCurrent.activityBA,mostCurrent._starter._modulo_hidro_sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"SELECT * FROM evals_hidro WHERE Id=?",new String[]{_proyectonumero});
 };
 //BA.debugLineNum = 1690;BA.debugLine="If datosMap = Null Or datosMap.IsInitialized = Fa";
if (_datosmap== null || _datosmap.IsInitialized()==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 1691;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 1692;BA.debugLine="ToastMessageShow(\"Error cargando el análisis\",";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Error cargando el análisis"),anywheresoftware.b4a.keywords.Common.False);
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 1694;BA.debugLine="ToastMessageShow(\"Error loading the report\", Fa";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Error loading the report"),anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 1696;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 //BA.debugLineNum = 1697;BA.debugLine="Return";
if (true) return "";
 }else {
 //BA.debugLineNum = 1706;BA.debugLine="username = datosMap.Get(\"usuario\")";
_username = BA.ObjectToString(_datosmap.Get((Object)("usuario")));
 //BA.debugLineNum = 1707;BA.debugLine="useremail = Main.strUserEmail";
_useremail = mostCurrent._main._struseremail /*String*/ ;
 //BA.debugLineNum = 1708;BA.debugLine="dateandtime = datosMap.Get(\"georeferenceddate\")";
_dateandtime = BA.ObjectToString(_datosmap.Get((Object)("georeferenceddate")));
 //BA.debugLineNum = 1709;BA.debugLine="nombresitio = datosMap.Get(\"nombresitio\")";
_nombresitio = BA.ObjectToString(_datosmap.Get((Object)("nombresitio")));
 //BA.debugLineNum = 1710;BA.debugLine="tiporio = datosMap.Get(\"tiporio\")";
_tiporio = BA.ObjectToString(_datosmap.Get((Object)("tiporio")));
 //BA.debugLineNum = 1711;BA.debugLine="lat = datosMap.Get(\"decimallatitude\")";
_lat = BA.ObjectToString(_datosmap.Get((Object)("decimallatitude")));
 //BA.debugLineNum = 1712;BA.debugLine="lng = datosMap.Get(\"decimallongitude\")";
_lng = BA.ObjectToString(_datosmap.Get((Object)("decimallongitude")));
 //BA.debugLineNum = 1713;BA.debugLine="gpsdetect = datosMap.Get(\"gpsdetect\")";
_gpsdetect = BA.ObjectToString(_datosmap.Get((Object)("gpsdetect")));
 //BA.debugLineNum = 1714;BA.debugLine="wifidetect = datosMap.Get(\"wifidetect\")";
_wifidetect = BA.ObjectToString(_datosmap.Get((Object)("wifidetect")));
 //BA.debugLineNum = 1715;BA.debugLine="mapadetect = datosMap.Get(\"mapadetect\")";
_mapadetect = BA.ObjectToString(_datosmap.Get((Object)("mapadetect")));
 //BA.debugLineNum = 1719;BA.debugLine="valorcalidad = datosMap.Get(\"valorcalidad\")";
_valorcalidad = BA.ObjectToString(_datosmap.Get((Object)("valorcalidad")));
 //BA.debugLineNum = 1720;BA.debugLine="valorNS = datosMap.Get(\"valorind20\")";
_valorns = BA.ObjectToString(_datosmap.Get((Object)("valorind20")));
 //BA.debugLineNum = 1721;BA.debugLine="valorind1 = datosMap.Get(\"valorind1\")";
_valorind1 = BA.ObjectToString(_datosmap.Get((Object)("valorind1")));
 //BA.debugLineNum = 1722;BA.debugLine="valorind2 = datosMap.Get(\"valorind2\")";
_valorind2 = BA.ObjectToString(_datosmap.Get((Object)("valorind2")));
 //BA.debugLineNum = 1723;BA.debugLine="valorind3 = datosMap.Get(\"valorind3\")";
_valorind3 = BA.ObjectToString(_datosmap.Get((Object)("valorind3")));
 //BA.debugLineNum = 1724;BA.debugLine="valorind4 = datosMap.Get(\"valorind4\")";
_valorind4 = BA.ObjectToString(_datosmap.Get((Object)("valorind4")));
 //BA.debugLineNum = 1725;BA.debugLine="valorind5 = datosMap.Get(\"valorind5\")";
_valorind5 = BA.ObjectToString(_datosmap.Get((Object)("valorind5")));
 //BA.debugLineNum = 1726;BA.debugLine="valorind6 = datosMap.Get(\"valorind6\")";
_valorind6 = BA.ObjectToString(_datosmap.Get((Object)("valorind6")));
 //BA.debugLineNum = 1727;BA.debugLine="valorind7 = datosMap.Get(\"valorind7\")";
_valorind7 = BA.ObjectToString(_datosmap.Get((Object)("valorind7")));
 //BA.debugLineNum = 1728;BA.debugLine="valorind8 = datosMap.Get(\"valorind8\")";
_valorind8 = BA.ObjectToString(_datosmap.Get((Object)("valorind8")));
 //BA.debugLineNum = 1729;BA.debugLine="valorind9 = datosMap.Get(\"valorind9\")";
_valorind9 = BA.ObjectToString(_datosmap.Get((Object)("valorind9")));
 //BA.debugLineNum = 1730;BA.debugLine="valorind10 = datosMap.Get(\"valorind10\")";
_valorind10 = BA.ObjectToString(_datosmap.Get((Object)("valorind10")));
 //BA.debugLineNum = 1731;BA.debugLine="valorind11 = datosMap.Get(\"valorind11\")";
_valorind11 = BA.ObjectToString(_datosmap.Get((Object)("valorind11")));
 //BA.debugLineNum = 1732;BA.debugLine="valorind12 = datosMap.Get(\"valorind12\")";
_valorind12 = BA.ObjectToString(_datosmap.Get((Object)("valorind12")));
 //BA.debugLineNum = 1733;BA.debugLine="valorind13 = datosMap.Get(\"valorind13\")";
_valorind13 = BA.ObjectToString(_datosmap.Get((Object)("valorind13")));
 //BA.debugLineNum = 1734;BA.debugLine="valorind14 = datosMap.Get(\"valorind14\")";
_valorind14 = BA.ObjectToString(_datosmap.Get((Object)("valorind14")));
 //BA.debugLineNum = 1735;BA.debugLine="valorind15 = datosMap.Get(\"valorind15\")";
_valorind15 = BA.ObjectToString(_datosmap.Get((Object)("valorind15")));
 //BA.debugLineNum = 1736;BA.debugLine="valorind16 = datosMap.Get(\"valorind16\")";
_valorind16 = BA.ObjectToString(_datosmap.Get((Object)("valorind16")));
 //BA.debugLineNum = 1737;BA.debugLine="valorind17 = datosMap.Get(\"valorind17\")";
_valorind17 = BA.ObjectToString(_datosmap.Get((Object)("valorind17")));
 //BA.debugLineNum = 1738;BA.debugLine="valorind18 = datosMap.Get(\"valorind18\")";
_valorind18 = BA.ObjectToString(_datosmap.Get((Object)("valorind18")));
 //BA.debugLineNum = 1739;BA.debugLine="valorind19 = datosMap.Get(\"valorind19\")";
_valorind19 = BA.ObjectToString(_datosmap.Get((Object)("valorind19")));
 //BA.debugLineNum = 1740;BA.debugLine="ind_pvm_1 = datosMap.Get(\"ind_pvm_1\")";
_ind_pvm_1 = BA.ObjectToString(_datosmap.Get((Object)("ind_pvm_1")));
 //BA.debugLineNum = 1741;BA.debugLine="ind_pvm_2 = datosMap.Get(\"ind_pvm_2\")";
_ind_pvm_2 = BA.ObjectToString(_datosmap.Get((Object)("ind_pvm_2")));
 //BA.debugLineNum = 1742;BA.debugLine="ind_pvm_3 = datosMap.Get(\"ind_pvm_3\")";
_ind_pvm_3 = BA.ObjectToString(_datosmap.Get((Object)("ind_pvm_3")));
 //BA.debugLineNum = 1743;BA.debugLine="ind_pvm_4 = datosMap.Get(\"ind_pvm_4\")";
_ind_pvm_4 = BA.ObjectToString(_datosmap.Get((Object)("ind_pvm_4")));
 //BA.debugLineNum = 1744;BA.debugLine="ind_pvm_5 = datosMap.Get(\"ind_pvm_5\")";
_ind_pvm_5 = BA.ObjectToString(_datosmap.Get((Object)("ind_pvm_5")));
 //BA.debugLineNum = 1745;BA.debugLine="ind_pvm_6 = datosMap.Get(\"ind_pvm_6\")";
_ind_pvm_6 = BA.ObjectToString(_datosmap.Get((Object)("ind_pvm_6")));
 //BA.debugLineNum = 1746;BA.debugLine="ind_pvm_7 = datosMap.Get(\"ind_pvm_7\")";
_ind_pvm_7 = BA.ObjectToString(_datosmap.Get((Object)("ind_pvm_7")));
 //BA.debugLineNum = 1747;BA.debugLine="ind_pvm_8 = datosMap.Get(\"ind_pvm_8\")";
_ind_pvm_8 = BA.ObjectToString(_datosmap.Get((Object)("ind_pvm_8")));
 //BA.debugLineNum = 1748;BA.debugLine="ind_pvm_9 = datosMap.Get(\"ind_pvm_9\")";
_ind_pvm_9 = BA.ObjectToString(_datosmap.Get((Object)("ind_pvm_9")));
 //BA.debugLineNum = 1749;BA.debugLine="ind_pvm_10 = datosMap.Get(\"ind_pvm_10\")";
_ind_pvm_10 = BA.ObjectToString(_datosmap.Get((Object)("ind_pvm_10")));
 //BA.debugLineNum = 1750;BA.debugLine="ind_pvm_11 = datosMap.Get(\"ind_pvm_11\")";
_ind_pvm_11 = BA.ObjectToString(_datosmap.Get((Object)("ind_pvm_11")));
 //BA.debugLineNum = 1751;BA.debugLine="ind_pvm_12 = datosMap.Get(\"ind_pvm_12\")";
_ind_pvm_12 = BA.ObjectToString(_datosmap.Get((Object)("ind_pvm_12")));
 //BA.debugLineNum = 1752;BA.debugLine="notas = datosMap.Get(\"notas\")";
_notas = BA.ObjectToString(_datosmap.Get((Object)("notas")));
 //BA.debugLineNum = 1753;BA.debugLine="bingo = datosMap.Get(\"bingo\")";
_bingo = BA.ObjectToString(_datosmap.Get((Object)("bingo")));
 //BA.debugLineNum = 1754;BA.debugLine="foto1 = datosMap.Get(\"foto1\")";
_foto1 = BA.ObjectToString(_datosmap.Get((Object)("foto1")));
 //BA.debugLineNum = 1755;BA.debugLine="foto2 = datosMap.Get(\"foto2\")";
_foto2 = BA.ObjectToString(_datosmap.Get((Object)("foto2")));
 //BA.debugLineNum = 1756;BA.debugLine="foto3 = datosMap.Get(\"foto3\")";
_foto3 = BA.ObjectToString(_datosmap.Get((Object)("foto3")));
 //BA.debugLineNum = 1757;BA.debugLine="foto4 = datosMap.Get(\"foto4\")";
_foto4 = BA.ObjectToString(_datosmap.Get((Object)("foto4")));
 //BA.debugLineNum = 1758;BA.debugLine="foto5 = datosMap.Get(\"foto5\")";
_foto5 = BA.ObjectToString(_datosmap.Get((Object)("foto5")));
 //BA.debugLineNum = 1759;BA.debugLine="terminado = datosMap.Get(\"terminado\")";
_terminado = BA.ObjectToString(_datosmap.Get((Object)("terminado")));
 //BA.debugLineNum = 1760;BA.debugLine="privado = datosMap.Get(\"privado\")";
_privado = BA.ObjectToString(_datosmap.Get((Object)("privado")));
 //BA.debugLineNum = 1762;BA.debugLine="If privado = Null Or privado = \"null\" Or privado";
if (_privado== null || (_privado).equals("null") || (_privado).equals("")) { 
 //BA.debugLineNum = 1763;BA.debugLine="privado = \"no\"";
_privado = "no";
 };
 //BA.debugLineNum = 1765;BA.debugLine="estadovalidacion = datosMap.Get(\"estadovalidacio";
_estadovalidacion = BA.ObjectToString(_datosmap.Get((Object)("estadovalidacion")));
 //BA.debugLineNum = 1766;BA.debugLine="If estadovalidacion = \"null\" Then";
if ((_estadovalidacion).equals("null")) { 
 //BA.debugLineNum = 1767;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 1768;BA.debugLine="estadovalidacion = \"No Verificado\"";
_estadovalidacion = "No Verificado";
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 1770;BA.debugLine="estadovalidacion = \"Unverified\"";
_estadovalidacion = "Unverified";
 };
 };
 //BA.debugLineNum = 1774;BA.debugLine="serverIdupdate = datosMap.Get(\"serverid\")";
_serveridupdate = BA.ObjectToString(_datosmap.Get((Object)("serverid")));
 //BA.debugLineNum = 1775;BA.debugLine="If NewMarcador = False And (serverIdupdate = Nul";
if (_newmarcador==anywheresoftware.b4a.keywords.Common.False && (_serveridupdate== null || (_serveridupdate).equals("null"))) { 
 //BA.debugLineNum = 1776;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 1777;BA.debugLine="ToastMessageShow(\"No se puede actualizar el da";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("No se puede actualizar el dato"),anywheresoftware.b4a.keywords.Common.False);
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 1779;BA.debugLine="ToastMessageShow(\"The report cannot be updated";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("The report cannot be updated"),anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 1782;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 1785;BA.debugLine="Log(serverIdupdate)";
anywheresoftware.b4a.keywords.Common.LogImpl("443909239",_serveridupdate,0);
 //BA.debugLineNum = 1786;BA.debugLine="If serverIdupdate = \"null\" Or serverIdupdate = \"";
if ((_serveridupdate).equals("null") || (_serveridupdate).equals("") || (_serveridupdate).equals("") || _serveridupdate== null) { 
 //BA.debugLineNum = 1787;BA.debugLine="NewMarcador = True";
_newmarcador = anywheresoftware.b4a.keywords.Common.True;
 }else {
 //BA.debugLineNum = 1789;BA.debugLine="NewMarcador = False";
_newmarcador = anywheresoftware.b4a.keywords.Common.False;
 };
 };
 //BA.debugLineNum = 1797;BA.debugLine="If terminado <> \"si\" Then";
if ((_terminado).equals("si") == false) { 
 //BA.debugLineNum = 1798;BA.debugLine="Dim conf As Int";
_conf = 0;
 //BA.debugLineNum = 1800;BA.debugLine="Select conf";
switch (BA.switchObjectToInt(_conf,anywheresoftware.b4a.keywords.Common.DialogResponse.NEGATIVE,anywheresoftware.b4a.keywords.Common.DialogResponse.POSITIVE)) {
case 0: {
 //BA.debugLineNum = 1802;BA.debugLine="Return";
if (true) return "";
 break; }
case 1: {
 //BA.debugLineNum = 1804;BA.debugLine="Return";
if (true) return "";
 break; }
}
;
 };
 //BA.debugLineNum = 1808;BA.debugLine="Dim dirWeb As String";
_dirweb = "";
 //BA.debugLineNum = 1809;BA.debugLine="If NewMarcador = True Then";
if (_newmarcador==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 1810;BA.debugLine="dirWeb = Main.serverPath &  \"/\" & Main.serverCon";
_dirweb = mostCurrent._main._serverpath /*String*/ +"/"+mostCurrent._main._serverconnectionfolder /*String*/ +"/addpuntomapa.php";
 }else {
 //BA.debugLineNum = 1812;BA.debugLine="dirWeb = Main.serverPath &  \"/\" & Main.serverCon";
_dirweb = mostCurrent._main._serverpath /*String*/ +"/"+mostCurrent._main._serverconnectionfolder /*String*/ +"/updatepuntomapa.php";
 };
 //BA.debugLineNum = 1815;BA.debugLine="Log(\"Comienza envio de datos\")";
anywheresoftware.b4a.keywords.Common.LogImpl("443909269","Comienza envio de datos",0);
 //BA.debugLineNum = 1817;BA.debugLine="Dim dd As DownloadData";
_dd = new appear.pnud.preservamos.downloadservice._downloaddata();
 //BA.debugLineNum = 1819;BA.debugLine="dd.url = dirWeb & \"?\" & _ 	\"username=\" & username";
_dd.url /*String*/  = _dirweb+"?"+"username="+_username+"&"+"useremail="+_useremail+"&"+"deviceID="+mostCurrent._main._deviceid /*String*/ +"&"+"dateandtime="+_dateandtime+"&"+"nombresitio="+_nombresitio+"&"+"lat="+_lat+"&"+"tiporio="+_tiporio+"&"+"indice="+_valorcalidad+"&"+"precision="+_valorns+"&"+"valorind1="+_valorind1+"&"+"valorind2="+_valorind2+"&"+"valorind3="+_valorind3+"&"+"valorind4="+_valorind4+"&"+"valorind5="+_valorind5+"&"+"valorind6="+_valorind6+"&"+"valorind7="+_valorind7+"&"+"valorind8="+_valorind8+"&"+"valorind9="+_valorind9+"&"+"valorind10="+_valorind10+"&"+"valorind11="+_valorind11+"&"+"valorind12="+_valorind12+"&"+"valorind13="+_valorind13+"&"+"valorind14="+_valorind14+"&"+"valorind15="+_valorind15+"&"+"valorind16="+_valorind16+"&"+"valorind17="+_valorind17+"&"+"valorind18="+_valorind18+"&"+"valorind19="+_valorind19+"&"+"ind_pvm_1="+_ind_pvm_1+"&"+"ind_pvm_2="+_ind_pvm_2+"&"+"ind_pvm_3="+_ind_pvm_3+"&"+"ind_pvm_4="+_ind_pvm_4+"&"+"ind_pvm_5="+_ind_pvm_5+"&"+"ind_pvm_6="+_ind_pvm_6+"&"+"ind_pvm_7="+_ind_pvm_7+"&"+"ind_pvm_8="+_ind_pvm_8+"&"+"ind_pvm_9="+_ind_pvm_9+"&"+"ind_pvm_10="+_ind_pvm_10+"&"+"ind_pvm_11="+_ind_pvm_11+"&"+"ind_pvm_12="+_ind_pvm_12+"&"+"foto1path="+_foto1+"&"+"foto2path="+_foto2+"&"+"foto3path="+_foto3+"&"+"foto4path="+_foto4+"&"+"foto5path="+_foto5+"&"+"terminado="+_terminado+"&"+"verificado="+_estadovalidacion+"&"+"privado="+_privado+"&"+"bingo="+_bingo+"&"+"notas="+_notas+"&"+"partido="+_partido+"&"+"mapadetect="+_mapadetect+"&"+"wifidetect="+_wifidetect+"&"+"gpsdetect="+_gpsdetect+"&"+"serverId="+_serveridupdate;
 //BA.debugLineNum = 1871;BA.debugLine="dd.EventName = \"EnviarDatos\"";
_dd.EventName /*String*/  = "EnviarDatos";
 //BA.debugLineNum = 1872;BA.debugLine="dd.Target = Me";
_dd.Target /*Object*/  = frmdatossinenviar.getObject();
 //BA.debugLineNum = 1873;BA.debugLine="CallSubDelayed2(DownloadService, \"StartDownload\",";
anywheresoftware.b4a.keywords.Common.CallSubDelayed2(processBA,(Object)(mostCurrent._downloadservice.getObject()),"StartDownload",(Object)(_dd));
 //BA.debugLineNum = 1875;BA.debugLine="End Sub";
return "";
}
public static void  _enviardatos_complete(appear.pnud.preservamos.httpjob _job) throws Exception{
ResumableSub_EnviarDatos_Complete rsub = new ResumableSub_EnviarDatos_Complete(null,_job);
rsub.resume(processBA, null);
}
public static class ResumableSub_EnviarDatos_Complete extends BA.ResumableSub {
public ResumableSub_EnviarDatos_Complete(appear.pnud.preservamos.frmdatossinenviar parent,appear.pnud.preservamos.httpjob _job) {
this.parent = parent;
this._job = _job;
}
appear.pnud.preservamos.frmdatossinenviar parent;
appear.pnud.preservamos.httpjob _job;
String _ret = "";
String _act = "";
anywheresoftware.b4a.objects.collections.JSONParser _parser = null;
int _result = 0;
anywheresoftware.b4a.objects.collections.Map _nd = null;
String _serverid = "";
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
 //BA.debugLineNum = 1877;BA.debugLine="Log(\"Datos enviados : \" & Job.Success)";
anywheresoftware.b4a.keywords.Common.LogImpl("443974657","Datos enviados : "+BA.ObjectToString(_job._success /*boolean*/ ),0);
 //BA.debugLineNum = 1878;BA.debugLine="If Job.Success = True Then";
if (true) break;

case 1:
//if
this.state = 84;
if (_job._success /*boolean*/ ==anywheresoftware.b4a.keywords.Common.True) { 
this.state = 3;
}else {
this.state = 77;
}if (true) break;

case 3:
//C
this.state = 4;
 //BA.debugLineNum = 1879;BA.debugLine="Dim ret As String";
_ret = "";
 //BA.debugLineNum = 1880;BA.debugLine="Dim act As String";
_act = "";
 //BA.debugLineNum = 1881;BA.debugLine="ret = Job.GetString";
_ret = _job._getstring /*String*/ ();
 //BA.debugLineNum = 1882;BA.debugLine="Dim parser As JSONParser";
_parser = new anywheresoftware.b4a.objects.collections.JSONParser();
 //BA.debugLineNum = 1883;BA.debugLine="parser.Initialize(ret)";
_parser.Initialize(_ret);
 //BA.debugLineNum = 1884;BA.debugLine="act = parser.NextValue";
_act = BA.ObjectToString(_parser.NextValue());
 //BA.debugLineNum = 1885;BA.debugLine="Log(act)";
anywheresoftware.b4a.keywords.Common.LogImpl("443974665",_act,0);
 //BA.debugLineNum = 1886;BA.debugLine="If act = \"Not Found\" Then";
if (true) break;

case 4:
//if
this.state = 75;
if ((_act).equals("Not Found")) { 
this.state = 6;
}else if((_act).equals("Error")) { 
this.state = 14;
}else if((_act).equals("Marcadores")) { 
this.state = 22;
}else if((_act).equals("MarcadorActualizado")) { 
this.state = 40;
}else if((_act).equals("MarcadorAgregado")) { 
this.state = 61;
}if (true) break;

case 6:
//C
this.state = 7;
 //BA.debugLineNum = 1887;BA.debugLine="If Main.lang = \"es\" Then";
if (true) break;

case 7:
//if
this.state = 12;
if ((parent.mostCurrent._main._lang /*String*/ ).equals("es")) { 
this.state = 9;
}else if((parent.mostCurrent._main._lang /*String*/ ).equals("en")) { 
this.state = 11;
}if (true) break;

case 9:
//C
this.state = 12;
 //BA.debugLineNum = 1888;BA.debugLine="ToastMessageShow(\"Error en la carga de marcado";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Error en la carga de marcadores"),anywheresoftware.b4a.keywords.Common.True);
 if (true) break;

case 11:
//C
this.state = 12;
 //BA.debugLineNum = 1890;BA.debugLine="ToastMessageShow(\"Error in loading reports\", T";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Error in loading reports"),anywheresoftware.b4a.keywords.Common.True);
 if (true) break;

case 12:
//C
this.state = 75;
;
 //BA.debugLineNum = 1893;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 if (true) break;

case 14:
//C
this.state = 15;
 //BA.debugLineNum = 1895;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 //BA.debugLineNum = 1896;BA.debugLine="Msgbox2Async(\"Hubo un problema con el envío, pr";
anywheresoftware.b4a.keywords.Common.Msgbox2Async(BA.ObjectToCharSequence("Hubo un problema con el envío, probablemente por la baja señal de internet en tu zona. ¿Deseas intentar de nuevo, o guardar el dato y enviarlo luego?"),BA.ObjectToCharSequence("Acceso a internet débil"),"Intenta de nuevo","","Guarda el dato, lo envío luego",(anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper(), (android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null)),processBA,anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1897;BA.debugLine="Wait For Msgbox_Result (Result As Int)";
anywheresoftware.b4a.keywords.Common.WaitFor("msgbox_result", processBA, this, null);
this.state = 85;
return;
case 85:
//C
this.state = 15;
_result = (Integer) result[0];
;
 //BA.debugLineNum = 1898;BA.debugLine="If Result = DialogResponse.POSITIVE Then";
if (true) break;

case 15:
//if
this.state = 20;
if (_result==anywheresoftware.b4a.keywords.Common.DialogResponse.POSITIVE) { 
this.state = 17;
}else if(_result==anywheresoftware.b4a.keywords.Common.DialogResponse.NEGATIVE) { 
this.state = 19;
}if (true) break;

case 17:
//C
this.state = 20;
 //BA.debugLineNum = 1899;BA.debugLine="EnviarDatos(IdProyectoEnviar)";
_enviardatos(parent.mostCurrent._idproyectoenviar);
 if (true) break;

case 19:
//C
this.state = 20;
 //BA.debugLineNum = 1901;BA.debugLine="ToastMessageShow(\"Se guardó el dato\", True)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Se guardó el dato"),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1902;BA.debugLine="TimerEnvio.Enabled = False";
parent._timerenvio.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1903;BA.debugLine="Activity.RemoveAllViews";
parent.mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 1904;BA.debugLine="Activity.Finish";
parent.mostCurrent._activity.Finish();
 if (true) break;

case 20:
//C
this.state = 75;
;
 if (true) break;

case 22:
//C
this.state = 23;
 //BA.debugLineNum = 1907;BA.debugLine="Dim nd As Map";
_nd = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 1908;BA.debugLine="nd = parser.NextObject";
_nd = _parser.NextObject();
 //BA.debugLineNum = 1909;BA.debugLine="Dim serverID As String";
_serverid = "";
 //BA.debugLineNum = 1910;BA.debugLine="serverID = nd.Get(\"serverId\")";
_serverid = BA.ObjectToString(_nd.Get((Object)("serverId")));
 //BA.debugLineNum = 1913;BA.debugLine="Dim Map1 As Map";
_map1 = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 1914;BA.debugLine="Map1.Initialize";
_map1.Initialize();
 //BA.debugLineNum = 1915;BA.debugLine="Map1.Put(\"Id\", IdProyectoEnviar)";
_map1.Put((Object)("Id"),(Object)(parent.mostCurrent._idproyectoenviar));
 //BA.debugLineNum = 1917;BA.debugLine="If 	currentDatoTipo = \"habitat\" Then";
if (true) break;

case 23:
//if
this.state = 30;
if ((parent.mostCurrent._currentdatotipo).equals("habitat")) { 
this.state = 25;
}else if((parent.mostCurrent._currentdatotipo).equals("residuos")) { 
this.state = 27;
}else if((parent.mostCurrent._currentdatotipo).equals("hidro")) { 
this.state = 29;
}if (true) break;

case 25:
//C
this.state = 30;
 //BA.debugLineNum = 1918;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"evals\", \"";
parent.mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,parent.mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals","evalsent",(Object)("si"),_map1);
 //BA.debugLineNum = 1919;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"evals\", \"";
parent.mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,parent.mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals","serverId",(Object)(_serverid),_map1);
 if (true) break;

case 27:
//C
this.state = 30;
 //BA.debugLineNum = 1921;BA.debugLine="DBUtils.UpdateRecord(Starter.modulo_residuos_s";
parent.mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,parent.mostCurrent._starter._modulo_residuos_sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals_residuos","evalsent",(Object)("si"),_map1);
 //BA.debugLineNum = 1922;BA.debugLine="DBUtils.UpdateRecord(Starter.modulo_residuos_s";
parent.mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,parent.mostCurrent._starter._modulo_residuos_sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals_residuos","serverId",(Object)(_serverid),_map1);
 if (true) break;

case 29:
//C
this.state = 30;
 //BA.debugLineNum = 1924;BA.debugLine="DBUtils.UpdateRecord(Starter.modulo_hidro_sqlD";
parent.mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,parent.mostCurrent._starter._modulo_hidro_sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals_hidro","evalsent",(Object)("si"),_map1);
 //BA.debugLineNum = 1925;BA.debugLine="DBUtils.UpdateRecord(Starter.modulo_hidro_sqlD";
parent.mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,parent.mostCurrent._starter._modulo_hidro_sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals_hidro","serverId",(Object)(_serverid),_map1);
 if (true) break;
;
 //BA.debugLineNum = 1929;BA.debugLine="If Main.lang = \"es\" Then";

case 30:
//if
this.state = 35;
if ((parent.mostCurrent._main._lang /*String*/ ).equals("es")) { 
this.state = 32;
}else if((parent.mostCurrent._main._lang /*String*/ ).equals("en")) { 
this.state = 34;
}if (true) break;

case 32:
//C
this.state = 35;
 //BA.debugLineNum = 1930;BA.debugLine="ToastMessageShow(\"Datos enviados, enviando fot";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Datos enviados, enviando fotos"),anywheresoftware.b4a.keywords.Common.False);
 if (true) break;

case 34:
//C
this.state = 35;
 //BA.debugLineNum = 1932;BA.debugLine="ToastMessageShow(\"Report sent, sending photos\"";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Report sent, sending photos"),anywheresoftware.b4a.keywords.Common.False);
 if (true) break;
;
 //BA.debugLineNum = 1935;BA.debugLine="If hubocambiosFotos = True Then";

case 35:
//if
this.state = 38;
if (parent._hubocambiosfotos==anywheresoftware.b4a.keywords.Common.True) { 
this.state = 37;
}if (true) break;

case 37:
//C
this.state = 38;
 //BA.debugLineNum = 1936;BA.debugLine="EnviarFotos";
_enviarfotos();
 if (true) break;

case 38:
//C
this.state = 75;
;
 if (true) break;

case 40:
//C
this.state = 41;
 //BA.debugLineNum = 1943;BA.debugLine="Dim Map1 As Map";
_map1 = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 1944;BA.debugLine="Map1.Initialize";
_map1.Initialize();
 //BA.debugLineNum = 1945;BA.debugLine="Map1.Put(\"Id\", IdProyectoEnviar)";
_map1.Put((Object)("Id"),(Object)(parent.mostCurrent._idproyectoenviar));
 //BA.debugLineNum = 1947;BA.debugLine="If 	currentDatoTipo = \"habitat\" Then";
if (true) break;

case 41:
//if
this.state = 48;
if ((parent.mostCurrent._currentdatotipo).equals("habitat")) { 
this.state = 43;
}else if((parent.mostCurrent._currentdatotipo).equals("residuos")) { 
this.state = 45;
}else if((parent.mostCurrent._currentdatotipo).equals("hidro")) { 
this.state = 47;
}if (true) break;

case 43:
//C
this.state = 48;
 //BA.debugLineNum = 1948;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"evals\", \"";
parent.mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,parent.mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals","evalsent",(Object)("si"),_map1);
 if (true) break;

case 45:
//C
this.state = 48;
 //BA.debugLineNum = 1950;BA.debugLine="DBUtils.UpdateRecord(Starter.modulo_residuos_s";
parent.mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,parent.mostCurrent._starter._modulo_residuos_sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals_residuos","evalsent",(Object)("si"),_map1);
 if (true) break;

case 47:
//C
this.state = 48;
 //BA.debugLineNum = 1952;BA.debugLine="DBUtils.UpdateRecord(Starter.modulo_hidro_sqlD";
parent.mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,parent.mostCurrent._starter._modulo_hidro_sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals_hidro","evalsent",(Object)("si"),_map1);
 if (true) break;
;
 //BA.debugLineNum = 1956;BA.debugLine="If Main.lang = \"es\" Then";

case 48:
//if
this.state = 53;
if ((parent.mostCurrent._main._lang /*String*/ ).equals("es")) { 
this.state = 50;
}else if((parent.mostCurrent._main._lang /*String*/ ).equals("en")) { 
this.state = 52;
}if (true) break;

case 50:
//C
this.state = 53;
 if (true) break;

case 52:
//C
this.state = 53;
 if (true) break;

case 53:
//C
this.state = 54;
;
 //BA.debugLineNum = 1961;BA.debugLine="ToastMessageShow(\"Datos actualizados\", False)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Datos actualizados"),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1965;BA.debugLine="If hubocambiosFotos = True Then";
if (true) break;

case 54:
//if
this.state = 59;
if (parent._hubocambiosfotos==anywheresoftware.b4a.keywords.Common.True) { 
this.state = 56;
}else {
this.state = 58;
}if (true) break;

case 56:
//C
this.state = 59;
 //BA.debugLineNum = 1966;BA.debugLine="EnviarFotos";
_enviarfotos();
 if (true) break;

case 58:
//C
this.state = 59;
 //BA.debugLineNum = 1968;BA.debugLine="ToastMessageShow(\"Evaluación enviada\", False)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Evaluación enviada"),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1969;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 //BA.debugLineNum = 1970;BA.debugLine="TimerEnvio.Enabled = False";
parent._timerenvio.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1971;BA.debugLine="Activity.RemoveAllViews";
parent.mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 1972;BA.debugLine="Activity.Finish";
parent.mostCurrent._activity.Finish();
 if (true) break;

case 59:
//C
this.state = 75;
;
 //BA.debugLineNum = 1974;BA.debugLine="Job.Release";
_job._release /*String*/ ();
 if (true) break;

case 61:
//C
this.state = 62;
 //BA.debugLineNum = 1977;BA.debugLine="Dim nd As Map";
_nd = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 1978;BA.debugLine="nd = parser.NextObject";
_nd = _parser.NextObject();
 //BA.debugLineNum = 1979;BA.debugLine="Dim serverID As String";
_serverid = "";
 //BA.debugLineNum = 1980;BA.debugLine="serverID = nd.Get(\"serverId\")";
_serverid = BA.ObjectToString(_nd.Get((Object)("serverId")));
 //BA.debugLineNum = 1983;BA.debugLine="Dim Map1 As Map";
_map1 = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 1984;BA.debugLine="Map1.Initialize";
_map1.Initialize();
 //BA.debugLineNum = 1986;BA.debugLine="Map1.Put(\"Id\", IdProyectoEnviar)";
_map1.Put((Object)("Id"),(Object)(parent.mostCurrent._idproyectoenviar));
 //BA.debugLineNum = 1988;BA.debugLine="If 	currentDatoTipo = \"habitat\" Then";
if (true) break;

case 62:
//if
this.state = 69;
if ((parent.mostCurrent._currentdatotipo).equals("habitat")) { 
this.state = 64;
}else if((parent.mostCurrent._currentdatotipo).equals("residuos")) { 
this.state = 66;
}else if((parent.mostCurrent._currentdatotipo).equals("hidro")) { 
this.state = 68;
}if (true) break;

case 64:
//C
this.state = 69;
 //BA.debugLineNum = 1989;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"evals\", \"";
parent.mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,parent.mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals","evalsent",(Object)("si"),_map1);
 //BA.debugLineNum = 1990;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"evals\", \"";
parent.mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,parent.mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals","serverId",(Object)(_serverid),_map1);
 if (true) break;

case 66:
//C
this.state = 69;
 //BA.debugLineNum = 1992;BA.debugLine="DBUtils.UpdateRecord(Starter.modulo_residuos_s";
parent.mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,parent.mostCurrent._starter._modulo_residuos_sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals_residuos","evalsent",(Object)("si"),_map1);
 //BA.debugLineNum = 1993;BA.debugLine="DBUtils.UpdateRecord(Starter.modulo_residuos_s";
parent.mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,parent.mostCurrent._starter._modulo_residuos_sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals_residuos","serverId",(Object)(_serverid),_map1);
 if (true) break;

case 68:
//C
this.state = 69;
 //BA.debugLineNum = 1995;BA.debugLine="DBUtils.UpdateRecord(Starter.modulo_hidro_sqlD";
parent.mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,parent.mostCurrent._starter._modulo_hidro_sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals_hidro","evalsent",(Object)("si"),_map1);
 //BA.debugLineNum = 1996;BA.debugLine="DBUtils.UpdateRecord(Starter.modulo_hidro_sqlD";
parent.mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,parent.mostCurrent._starter._modulo_hidro_sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals_hidro","serverId",(Object)(_serverid),_map1);
 if (true) break;
;
 //BA.debugLineNum = 2000;BA.debugLine="If Main.lang = \"es\" Then";

case 69:
//if
this.state = 74;
if ((parent.mostCurrent._main._lang /*String*/ ).equals("es")) { 
this.state = 71;
}else if((parent.mostCurrent._main._lang /*String*/ ).equals("en")) { 
this.state = 73;
}if (true) break;

case 71:
//C
this.state = 74;
 //BA.debugLineNum = 2001;BA.debugLine="ToastMessageShow(\"Datos enviados, enviando fot";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Datos enviados, enviando fotos"),anywheresoftware.b4a.keywords.Common.False);
 if (true) break;

case 73:
//C
this.state = 74;
 //BA.debugLineNum = 2003;BA.debugLine="ToastMessageShow(\"Report sent, sending photos\"";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Report sent, sending photos"),anywheresoftware.b4a.keywords.Common.False);
 if (true) break;

case 74:
//C
this.state = 75;
;
 //BA.debugLineNum = 2007;BA.debugLine="EnviarFotos";
_enviarfotos();
 if (true) break;

case 75:
//C
this.state = 84;
;
 if (true) break;

case 77:
//C
this.state = 78;
 //BA.debugLineNum = 2012;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 //BA.debugLineNum = 2013;BA.debugLine="Log(\"envio datos not ok\")";
anywheresoftware.b4a.keywords.Common.LogImpl("443974793","envio datos not ok",0);
 //BA.debugLineNum = 2014;BA.debugLine="If Main.lang = \"es\" Then";
if (true) break;

case 78:
//if
this.state = 83;
if ((parent.mostCurrent._main._lang /*String*/ ).equals("es")) { 
this.state = 80;
}else if((parent.mostCurrent._main._lang /*String*/ ).equals("en")) { 
this.state = 82;
}if (true) break;

case 80:
//C
this.state = 83;
 //BA.debugLineNum = 2015;BA.debugLine="MsgboxAsync(\"Al parecer hay un problema en nues";
anywheresoftware.b4a.keywords.Common.MsgboxAsync(BA.ObjectToCharSequence("Al parecer hay un problema en nuestros servidores, lo solucionaremos pronto!"),BA.ObjectToCharSequence("Mala mía"),processBA);
 if (true) break;

case 82:
//C
this.state = 83;
 //BA.debugLineNum = 2017;BA.debugLine="MsgboxAsync(\"There seems to be a problem with o";
anywheresoftware.b4a.keywords.Common.MsgboxAsync(BA.ObjectToCharSequence("There seems to be a problem with our servers, we will solve it soon!"),BA.ObjectToCharSequence("My bad"),processBA);
 if (true) break;

case 83:
//C
this.state = 84;
;
 if (true) break;

case 84:
//C
this.state = -1;
;
 //BA.debugLineNum = 2022;BA.debugLine="Job.Release";
_job._release /*String*/ ();
 //BA.debugLineNum = 2023;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public static String  _enviarfotos() throws Exception{
 //BA.debugLineNum = 2030;BA.debugLine="Sub EnviarFotos";
 //BA.debugLineNum = 2034;BA.debugLine="ToastMessageShow(\"Enviando fotos actualizadas\", F";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Enviando fotos actualizadas"),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 2035;BA.debugLine="fondoblanco.Initialize(\"fondoblanco\")";
mostCurrent._fondoblanco.Initialize(mostCurrent.activityBA,"fondoblanco");
 //BA.debugLineNum = 2036;BA.debugLine="fondoblanco.Color = Colors.ARGB(190, 255,255,255)";
mostCurrent._fondoblanco.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (190),(int) (255),(int) (255),(int) (255)));
 //BA.debugLineNum = 2037;BA.debugLine="Activity.AddView(fondoblanco, 0,0, 100%x, 100%y)";
mostCurrent._activity.AddView((android.view.View)(mostCurrent._fondoblanco.getObject()),(int) (0),(int) (0),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA));
 //BA.debugLineNum = 2039;BA.debugLine="If foto1 <> \"null\" Then";
if ((_foto1).equals("null") == false) { 
 //BA.debugLineNum = 2040;BA.debugLine="ProgressBar1.Initialize(\"ProgressBar1\")";
mostCurrent._progressbar1.Initialize(mostCurrent.activityBA,"ProgressBar1");
 //BA.debugLineNum = 2041;BA.debugLine="foto1Prg.Initialize(\"\")";
mostCurrent._foto1prg.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 2042;BA.debugLine="Activity.AddView(ProgressBar1, 50%x, 30%y, 30%x,";
mostCurrent._activity.AddView((android.view.View)(mostCurrent._progressbar1.getObject()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (50),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (30),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (30),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5)));
 //BA.debugLineNum = 2043;BA.debugLine="Activity.AddView(foto1Prg, 15%x, 30%y, 50%x, 20d";
mostCurrent._activity.AddView((android.view.View)(mostCurrent._foto1prg.getObject()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (15),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (30),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (50),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (20)));
 //BA.debugLineNum = 2044;BA.debugLine="foto1Prg.Text = \"Subiendo Foto 1:\"";
mostCurrent._foto1prg.setText(BA.ObjectToCharSequence("Subiendo Foto 1:"));
 //BA.debugLineNum = 2045;BA.debugLine="foto1Prg.TextColor = Colors.Black";
mostCurrent._foto1prg.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 2046;BA.debugLine="foto1Prg.TextSize = 14";
mostCurrent._foto1prg.setTextSize((float) (14));
 //BA.debugLineNum = 2047;BA.debugLine="totalFotos = totalFotos + 1";
_totalfotos = (int) (_totalfotos+1);
 };
 //BA.debugLineNum = 2049;BA.debugLine="If foto2 <>  \"null\" Then";
if ((_foto2).equals("null") == false) { 
 //BA.debugLineNum = 2050;BA.debugLine="ProgressBar2.Initialize(\"ProgressBar2\")";
mostCurrent._progressbar2.Initialize(mostCurrent.activityBA,"ProgressBar2");
 //BA.debugLineNum = 2051;BA.debugLine="foto2Prg.Initialize(\"\")";
mostCurrent._foto2prg.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 2052;BA.debugLine="Activity.AddView(ProgressBar2, 50%x, 40%y, 30%x,";
mostCurrent._activity.AddView((android.view.View)(mostCurrent._progressbar2.getObject()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (50),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (40),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (30),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5)));
 //BA.debugLineNum = 2053;BA.debugLine="Activity.AddView(foto2Prg, 15%x, 40%y, 50%x, 20d";
mostCurrent._activity.AddView((android.view.View)(mostCurrent._foto2prg.getObject()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (15),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (40),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (50),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (20)));
 //BA.debugLineNum = 2054;BA.debugLine="foto2Prg.Text = \"Subiendo Foto 2:\"";
mostCurrent._foto2prg.setText(BA.ObjectToCharSequence("Subiendo Foto 2:"));
 //BA.debugLineNum = 2055;BA.debugLine="foto2Prg.TextColor = Colors.Black";
mostCurrent._foto2prg.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 2056;BA.debugLine="foto2Prg.TextSize = 14";
mostCurrent._foto2prg.setTextSize((float) (14));
 //BA.debugLineNum = 2057;BA.debugLine="totalFotos = totalFotos + 1";
_totalfotos = (int) (_totalfotos+1);
 };
 //BA.debugLineNum = 2059;BA.debugLine="If foto3 <> \"null\" Then";
if ((_foto3).equals("null") == false) { 
 //BA.debugLineNum = 2060;BA.debugLine="ProgressBar3.Initialize(\"ProgressBar3\")";
mostCurrent._progressbar3.Initialize(mostCurrent.activityBA,"ProgressBar3");
 //BA.debugLineNum = 2061;BA.debugLine="foto3Prg.Initialize(\"\")";
mostCurrent._foto3prg.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 2062;BA.debugLine="Activity.AddView(ProgressBar3, 50%x, 30%y, 30%x,";
mostCurrent._activity.AddView((android.view.View)(mostCurrent._progressbar3.getObject()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (50),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (30),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (30),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5)));
 //BA.debugLineNum = 2063;BA.debugLine="Activity.AddView(foto3Prg, 15%x, 50%y, 50%x, 20d";
mostCurrent._activity.AddView((android.view.View)(mostCurrent._foto3prg.getObject()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (15),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (50),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (50),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (20)));
 //BA.debugLineNum = 2064;BA.debugLine="foto3Prg.Text = \"Subiendo Foto 3:\"";
mostCurrent._foto3prg.setText(BA.ObjectToCharSequence("Subiendo Foto 3:"));
 //BA.debugLineNum = 2065;BA.debugLine="foto3Prg.TextColor = Colors.Black";
mostCurrent._foto3prg.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 2066;BA.debugLine="foto3Prg.TextSize = 14";
mostCurrent._foto3prg.setTextSize((float) (14));
 //BA.debugLineNum = 2067;BA.debugLine="totalFotos = totalFotos + 1";
_totalfotos = (int) (_totalfotos+1);
 };
 //BA.debugLineNum = 2069;BA.debugLine="If foto4 <> \"null\" Then";
if ((_foto4).equals("null") == false) { 
 //BA.debugLineNum = 2070;BA.debugLine="ProgressBar4.Initialize(\"ProgressBar4\")";
mostCurrent._progressbar4.Initialize(mostCurrent.activityBA,"ProgressBar4");
 //BA.debugLineNum = 2071;BA.debugLine="foto4Prg.Initialize(\"\")";
mostCurrent._foto4prg.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 2072;BA.debugLine="Activity.AddView(ProgressBar4, 50%x, 30%y, 30%x,";
mostCurrent._activity.AddView((android.view.View)(mostCurrent._progressbar4.getObject()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (50),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (30),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (30),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5)));
 //BA.debugLineNum = 2073;BA.debugLine="Activity.AddView(foto4Prg, 15%x, 60%y, 50%x, 20d";
mostCurrent._activity.AddView((android.view.View)(mostCurrent._foto4prg.getObject()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (15),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (60),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (50),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (20)));
 //BA.debugLineNum = 2074;BA.debugLine="foto4Prg.Text = \"Subiendo Foto 4:\"";
mostCurrent._foto4prg.setText(BA.ObjectToCharSequence("Subiendo Foto 4:"));
 //BA.debugLineNum = 2075;BA.debugLine="foto4Prg.TextColor = Colors.Black";
mostCurrent._foto4prg.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 2076;BA.debugLine="foto4Prg.TextSize = 14";
mostCurrent._foto4prg.setTextSize((float) (14));
 //BA.debugLineNum = 2077;BA.debugLine="totalFotos = totalFotos + 1";
_totalfotos = (int) (_totalfotos+1);
 };
 //BA.debugLineNum = 2079;BA.debugLine="If foto5 <> \"null\" Then";
if ((_foto5).equals("null") == false) { 
 //BA.debugLineNum = 2080;BA.debugLine="ProgressBar5.Initialize(\"ProgressBar5\")";
mostCurrent._progressbar5.Initialize(mostCurrent.activityBA,"ProgressBar5");
 //BA.debugLineNum = 2081;BA.debugLine="foto5Prg.Initialize(\"\")";
mostCurrent._foto5prg.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 2082;BA.debugLine="Activity.AddView(ProgressBar1, 50%x, 30%y, 30%x,";
mostCurrent._activity.AddView((android.view.View)(mostCurrent._progressbar1.getObject()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (50),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (30),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (30),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5)));
 //BA.debugLineNum = 2083;BA.debugLine="Activity.AddView(foto1Prg, 15%x, 70%y, 50%x, 20d";
mostCurrent._activity.AddView((android.view.View)(mostCurrent._foto1prg.getObject()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (15),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (70),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (50),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (20)));
 //BA.debugLineNum = 2084;BA.debugLine="foto5Prg.Text = \"Subiendo Foto 5:\"";
mostCurrent._foto5prg.setText(BA.ObjectToCharSequence("Subiendo Foto 5:"));
 //BA.debugLineNum = 2085;BA.debugLine="foto5Prg.TextColor = Colors.Black";
mostCurrent._foto5prg.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 2086;BA.debugLine="foto5Prg.TextSize = 14";
mostCurrent._foto5prg.setTextSize((float) (14));
 //BA.debugLineNum = 2087;BA.debugLine="totalFotos = totalFotos + 1";
_totalfotos = (int) (_totalfotos+1);
 };
 //BA.debugLineNum = 2091;BA.debugLine="TimerEnvio.Initialize(\"TimerEnvio\", 1000)";
_timerenvio.Initialize(processBA,"TimerEnvio",(long) (1000));
 //BA.debugLineNum = 2092;BA.debugLine="TimerEnvio.Enabled = True";
_timerenvio.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 2095;BA.debugLine="If File.Exists(rp.GetSafeDirDefaultExternal(\"Pres";
if (anywheresoftware.b4a.keywords.Common.File.Exists(mostCurrent._rp.GetSafeDirDefaultExternal("PreserVamos"),_foto1+".jpg") && _foto1sent==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 2097;BA.debugLine="Log(\"Enviando foto 1 \")";
anywheresoftware.b4a.keywords.Common.LogImpl("444040259","Enviando foto 1 ",0);
 //BA.debugLineNum = 2098;BA.debugLine="Up1.doFileUpload(ProgressBar1,Null,rp.GetSafeDir";
_up1.doFileUpload(processBA,(android.widget.ProgressBar)(mostCurrent._progressbar1.getObject()),(android.widget.TextView)(anywheresoftware.b4a.keywords.Common.Null),mostCurrent._rp.GetSafeDirDefaultExternal("PreserVamos")+"/"+_foto1+".jpg",mostCurrent._main._serverpath /*String*/ +"/"+mostCurrent._main._serverconnectionfolder /*String*/ +"/upload_file.php");
 }else {
 //BA.debugLineNum = 2100;BA.debugLine="Log(\"no foto 1\")";
anywheresoftware.b4a.keywords.Common.LogImpl("444040262","no foto 1",0);
 };
 //BA.debugLineNum = 2103;BA.debugLine="End Sub";
return "";
}
public static void  _fondoblanco_click() throws Exception{
ResumableSub_fondoblanco_Click rsub = new ResumableSub_fondoblanco_Click(null);
rsub.resume(processBA, null);
}
public static class ResumableSub_fondoblanco_Click extends BA.ResumableSub {
public ResumableSub_fondoblanco_Click(appear.pnud.preservamos.frmdatossinenviar parent) {
this.parent = parent;
}
appear.pnud.preservamos.frmdatossinenviar parent;
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
 //BA.debugLineNum = 2106;BA.debugLine="If Main.lang = \"es\" Then";
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
 //BA.debugLineNum = 2107;BA.debugLine="Msgbox2Async(\"Se están enviando las fotografías,";
anywheresoftware.b4a.keywords.Common.Msgbox2Async(BA.ObjectToCharSequence("Se están enviando las fotografías, desea cancelar?"),BA.ObjectToCharSequence("Cancelar?"),"Si, cancelar","No!",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Null),(anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper(), (android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null)),processBA,anywheresoftware.b4a.keywords.Common.False);
 if (true) break;

case 5:
//C
this.state = 6;
 //BA.debugLineNum = 2109;BA.debugLine="Msgbox2Async(\"Photos are being uploaded, do you";
anywheresoftware.b4a.keywords.Common.Msgbox2Async(BA.ObjectToCharSequence("Photos are being uploaded, do you wish to cancel?"),BA.ObjectToCharSequence("Cancel?"),"Yes, cancel","No!",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Null),(anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper(), (android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null)),processBA,anywheresoftware.b4a.keywords.Common.False);
 if (true) break;

case 6:
//C
this.state = 7;
;
 //BA.debugLineNum = 2111;BA.debugLine="Wait For Msgbox_Result (Result As Int)";
anywheresoftware.b4a.keywords.Common.WaitFor("msgbox_result", processBA, this, null);
this.state = 11;
return;
case 11:
//C
this.state = 7;
_result = (Integer) result[0];
;
 //BA.debugLineNum = 2112;BA.debugLine="If Result = DialogResponse.POSITIVE Then";
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
 //BA.debugLineNum = 2113;BA.debugLine="Up1.UploadKill";
parent._up1.UploadKill(processBA);
 //BA.debugLineNum = 2115;BA.debugLine="Activity.RemoveAllViews";
parent.mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 2116;BA.debugLine="Activity.Finish";
parent.mostCurrent._activity.Finish();
 //BA.debugLineNum = 2117;BA.debugLine="StartActivity(Form_Main)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(parent.mostCurrent._form_main.getObject()));
 //BA.debugLineNum = 2118;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 if (true) break;

case 10:
//C
this.state = -1;
;
 //BA.debugLineNum = 2120;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public static String  _fondogris_click() throws Exception{
 //BA.debugLineNum = 1219;BA.debugLine="Sub fondogris_Click";
 //BA.debugLineNum = 1220;BA.debugLine="fotogrande.RemoveView";
mostCurrent._fotogrande.RemoveView();
 //BA.debugLineNum = 1221;BA.debugLine="foto1Borrar.RemoveView";
mostCurrent._foto1borrar.RemoveView();
 //BA.debugLineNum = 1222;BA.debugLine="foto2Borrar.RemoveView";
mostCurrent._foto2borrar.RemoveView();
 //BA.debugLineNum = 1223;BA.debugLine="fondogris.RemoveView";
mostCurrent._fondogris.RemoveView();
 //BA.debugLineNum = 1224;BA.debugLine="btnCerrar.Visible = True";
mostCurrent._btncerrar.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1225;BA.debugLine="End Sub";
return "";
}
public static void  _foto1borrar_click() throws Exception{
ResumableSub_foto1Borrar_Click rsub = new ResumableSub_foto1Borrar_Click(null);
rsub.resume(processBA, null);
}
public static class ResumableSub_foto1Borrar_Click extends BA.ResumableSub {
public ResumableSub_foto1Borrar_Click(appear.pnud.preservamos.frmdatossinenviar parent) {
this.parent = parent;
}
appear.pnud.preservamos.frmdatossinenviar parent;
int _result = 0;
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
 //BA.debugLineNum = 1235;BA.debugLine="If Main.lang = \"es\" Then";
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
 //BA.debugLineNum = 1236;BA.debugLine="Msgbox2Async(\"Seguro que desea quitar esta foto";
anywheresoftware.b4a.keywords.Common.Msgbox2Async(BA.ObjectToCharSequence("Seguro que desea quitar esta foto de este registro?"),BA.ObjectToCharSequence("Quitar foto"),"Si","No","",(anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper(), (android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null)),processBA,anywheresoftware.b4a.keywords.Common.False);
 if (true) break;

case 5:
//C
this.state = 6;
 //BA.debugLineNum = 1238;BA.debugLine="Msgbox2Async(\"Sure that you want to remove this";
anywheresoftware.b4a.keywords.Common.Msgbox2Async(BA.ObjectToCharSequence("Sure that you want to remove this photo from this report?"),BA.ObjectToCharSequence("Remove photo"),"Yes","No","",(anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper(), (android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null)),processBA,anywheresoftware.b4a.keywords.Common.False);
 if (true) break;

case 6:
//C
this.state = 7;
;
 //BA.debugLineNum = 1240;BA.debugLine="Wait For Msgbox_Result (Result As Int)";
anywheresoftware.b4a.keywords.Common.WaitFor("msgbox_result", processBA, this, null);
this.state = 21;
return;
case 21:
//C
this.state = 7;
_result = (Integer) result[0];
;
 //BA.debugLineNum = 1241;BA.debugLine="If Result = DialogResponse.POSITIVE Then";
if (true) break;

case 7:
//if
this.state = 20;
if (_result==anywheresoftware.b4a.keywords.Common.DialogResponse.POSITIVE) { 
this.state = 9;
}else {
this.state = 19;
}if (true) break;

case 9:
//C
this.state = 10;
 //BA.debugLineNum = 1242;BA.debugLine="Dim Map1 As Map";
_map1 = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 1243;BA.debugLine="Map1.Initialize";
_map1.Initialize();
 //BA.debugLineNum = 1244;BA.debugLine="Map1.Put(\"Id\", currentDatoId)";
_map1.Put((Object)("Id"),(Object)(parent.mostCurrent._currentdatoid));
 //BA.debugLineNum = 1245;BA.debugLine="If 	currentDatoTipo = \"habitat\" Then";
if (true) break;

case 10:
//if
this.state = 17;
if ((parent.mostCurrent._currentdatotipo).equals("habitat")) { 
this.state = 12;
}else if((parent.mostCurrent._currentdatotipo).equals("residuos")) { 
this.state = 14;
}else if((parent.mostCurrent._currentdatotipo).equals("hidro")) { 
this.state = 16;
}if (true) break;

case 12:
//C
this.state = 17;
 //BA.debugLineNum = 1246;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"evals\", \"f";
parent.mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,parent.mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals","foto1",anywheresoftware.b4a.keywords.Common.Null,_map1);
 if (true) break;

case 14:
//C
this.state = 17;
 //BA.debugLineNum = 1248;BA.debugLine="DBUtils.UpdateRecord(Starter.modulo_hidro_sqlDB";
parent.mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,parent.mostCurrent._starter._modulo_hidro_sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals_residuos","foto1",anywheresoftware.b4a.keywords.Common.Null,_map1);
 if (true) break;

case 16:
//C
this.state = 17;
 //BA.debugLineNum = 1250;BA.debugLine="DBUtils.UpdateRecord(Starter.modulo_hidro_sqlDB";
parent.mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,parent.mostCurrent._starter._modulo_hidro_sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals_hidro","foto1",anywheresoftware.b4a.keywords.Common.Null,_map1);
 if (true) break;

case 17:
//C
this.state = 20;
;
 //BA.debugLineNum = 1253;BA.debugLine="foto1view.Bitmap = Null";
parent.mostCurrent._foto1view.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null));
 //BA.debugLineNum = 1254;BA.debugLine="foto1Borrar.RemoveView";
parent.mostCurrent._foto1borrar.RemoveView();
 //BA.debugLineNum = 1255;BA.debugLine="fotogrande.RemoveView";
parent.mostCurrent._fotogrande.RemoveView();
 //BA.debugLineNum = 1256;BA.debugLine="hubocambiosFotos = True";
parent._hubocambiosfotos = anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 1257;BA.debugLine="btnEnviar.Visible = True";
parent.mostCurrent._btnenviar.setVisible(anywheresoftware.b4a.keywords.Common.True);
 if (true) break;

case 19:
//C
this.state = 20;
 //BA.debugLineNum = 1259;BA.debugLine="Return";
if (true) return ;
 if (true) break;

case 20:
//C
this.state = -1;
;
 //BA.debugLineNum = 1262;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public static String  _foto1view_click() throws Exception{
 //BA.debugLineNum = 1090;BA.debugLine="Sub foto1view_Click";
 //BA.debugLineNum = 1093;BA.debugLine="If foto1view.Bitmap = Null Then";
if (mostCurrent._foto1view.getBitmap()== null) { 
 //BA.debugLineNum = 1094;BA.debugLine="fotoAdjuntar = \"foto1\"";
mostCurrent._fotoadjuntar = "foto1";
 //BA.debugLineNum = 1095;BA.debugLine="CC.Initialize(\"CC\")";
_cc.Initialize("CC");
 //BA.debugLineNum = 1096;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 1097;BA.debugLine="CC.Show(\"image/\", \"Elija la foto\")";
_cc.Show(processBA,"image/","Elija la foto");
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 1099;BA.debugLine="CC.Show(\"image/\", \"Choose the photo\")";
_cc.Show(processBA,"image/","Choose the photo");
 };
 }else {
 //BA.debugLineNum = 1103;BA.debugLine="fondogris.Initialize(\"fondogris\")";
mostCurrent._fondogris.Initialize(mostCurrent.activityBA,"fondogris");
 //BA.debugLineNum = 1104;BA.debugLine="fondogris.Color = Colors.ARGB(150,0,0,0)";
mostCurrent._fondogris.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (150),(int) (0),(int) (0),(int) (0)));
 //BA.debugLineNum = 1105;BA.debugLine="Activity.AddView(fondogris, 0, 0, 100%x, 100%y)";
mostCurrent._activity.AddView((android.view.View)(mostCurrent._fondogris.getObject()),(int) (0),(int) (0),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA));
 //BA.debugLineNum = 1107;BA.debugLine="fotogrande.Initialize(\"fotogrande\")";
mostCurrent._fotogrande.Initialize(mostCurrent.activityBA,"fotogrande");
 //BA.debugLineNum = 1108;BA.debugLine="fotogrande.Bitmap = foto1view.Bitmap";
mostCurrent._fotogrande.setBitmap(mostCurrent._foto1view.getBitmap());
 //BA.debugLineNum = 1109;BA.debugLine="Log(foto1view.Width)";
anywheresoftware.b4a.keywords.Common.LogImpl("442467347",BA.NumberToString(mostCurrent._foto1view.getWidth()),0);
 //BA.debugLineNum = 1110;BA.debugLine="fotogrande.Gravity = Gravity.fill";
mostCurrent._fotogrande.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.FILL);
 //BA.debugLineNum = 1111;BA.debugLine="Activity.AddView(fotogrande, 10%x, 10%y, 80%x, 8";
mostCurrent._activity.AddView((android.view.View)(mostCurrent._fotogrande.getObject()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (10),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (10),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (80),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (80),mostCurrent.activityBA));
 //BA.debugLineNum = 1113;BA.debugLine="Activity.AddView(foto1Borrar, 10dip, 10dip, 120d";
mostCurrent._activity.AddView((android.view.View)(mostCurrent._foto1borrar.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (120)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (40)));
 //BA.debugLineNum = 1114;BA.debugLine="btnCerrar.Visible = False";
mostCurrent._btncerrar.setVisible(anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 1117;BA.debugLine="End Sub";
return "";
}
public static void  _foto2borrar_click() throws Exception{
ResumableSub_foto2Borrar_Click rsub = new ResumableSub_foto2Borrar_Click(null);
rsub.resume(processBA, null);
}
public static class ResumableSub_foto2Borrar_Click extends BA.ResumableSub {
public ResumableSub_foto2Borrar_Click(appear.pnud.preservamos.frmdatossinenviar parent) {
this.parent = parent;
}
appear.pnud.preservamos.frmdatossinenviar parent;
int _result = 0;
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
 //BA.debugLineNum = 1264;BA.debugLine="If Main.lang = \"es\" Then";
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
 //BA.debugLineNum = 1265;BA.debugLine="Msgbox2Async(\"Seguro que desea quitar esta foto";
anywheresoftware.b4a.keywords.Common.Msgbox2Async(BA.ObjectToCharSequence("Seguro que desea quitar esta foto de este registro?"),BA.ObjectToCharSequence("Quitar foto"),"Si","No","",(anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper(), (android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null)),processBA,anywheresoftware.b4a.keywords.Common.False);
 if (true) break;

case 5:
//C
this.state = 6;
 //BA.debugLineNum = 1267;BA.debugLine="Msgbox2Async(\"Sure that you want to remove this";
anywheresoftware.b4a.keywords.Common.Msgbox2Async(BA.ObjectToCharSequence("Sure that you want to remove this photo from this report?"),BA.ObjectToCharSequence("Remove photo"),"Yes","No","",(anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper(), (android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null)),processBA,anywheresoftware.b4a.keywords.Common.False);
 if (true) break;

case 6:
//C
this.state = 7;
;
 //BA.debugLineNum = 1269;BA.debugLine="Wait For Msgbox_Result (Result As Int)";
anywheresoftware.b4a.keywords.Common.WaitFor("msgbox_result", processBA, this, null);
this.state = 13;
return;
case 13:
//C
this.state = 7;
_result = (Integer) result[0];
;
 //BA.debugLineNum = 1270;BA.debugLine="If Result = DialogResponse.POSITIVE Then";
if (true) break;

case 7:
//if
this.state = 12;
if (_result==anywheresoftware.b4a.keywords.Common.DialogResponse.POSITIVE) { 
this.state = 9;
}else {
this.state = 11;
}if (true) break;

case 9:
//C
this.state = 12;
 //BA.debugLineNum = 1271;BA.debugLine="Dim Map1 As Map";
_map1 = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 1272;BA.debugLine="Map1.Initialize";
_map1.Initialize();
 //BA.debugLineNum = 1273;BA.debugLine="Map1.Put(\"Id\", currentDatoId)";
_map1.Put((Object)("Id"),(Object)(parent.mostCurrent._currentdatoid));
 //BA.debugLineNum = 1274;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"evals\", \"fo";
parent.mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,parent.mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals","foto2",anywheresoftware.b4a.keywords.Common.Null,_map1);
 //BA.debugLineNum = 1275;BA.debugLine="foto2view.Bitmap = Null";
parent.mostCurrent._foto2view.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null));
 //BA.debugLineNum = 1276;BA.debugLine="foto2Borrar.RemoveView";
parent.mostCurrent._foto2borrar.RemoveView();
 //BA.debugLineNum = 1277;BA.debugLine="fotogrande.RemoveView";
parent.mostCurrent._fotogrande.RemoveView();
 //BA.debugLineNum = 1278;BA.debugLine="hubocambiosFotos = True";
parent._hubocambiosfotos = anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 1279;BA.debugLine="btnEnviar.Visible = True";
parent.mostCurrent._btnenviar.setVisible(anywheresoftware.b4a.keywords.Common.True);
 if (true) break;

case 11:
//C
this.state = 12;
 //BA.debugLineNum = 1281;BA.debugLine="Return";
if (true) return ;
 if (true) break;

case 12:
//C
this.state = -1;
;
 //BA.debugLineNum = 1284;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public static String  _foto2view_click() throws Exception{
 //BA.debugLineNum = 1118;BA.debugLine="Sub foto2view_Click";
 //BA.debugLineNum = 1119;BA.debugLine="If foto2view.Bitmap = Null Then";
if (mostCurrent._foto2view.getBitmap()== null) { 
 //BA.debugLineNum = 1120;BA.debugLine="fotoAdjuntar = \"foto2\"";
mostCurrent._fotoadjuntar = "foto2";
 //BA.debugLineNum = 1121;BA.debugLine="CC.Initialize(\"CC\")";
_cc.Initialize("CC");
 //BA.debugLineNum = 1122;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 1123;BA.debugLine="CC.Show(\"image/\", \"Elija la foto\")";
_cc.Show(processBA,"image/","Elija la foto");
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 1125;BA.debugLine="CC.Show(\"image/\", \"Choose the photo\")";
_cc.Show(processBA,"image/","Choose the photo");
 };
 }else {
 //BA.debugLineNum = 1130;BA.debugLine="fondogris.Initialize(\"fondogris\")";
mostCurrent._fondogris.Initialize(mostCurrent.activityBA,"fondogris");
 //BA.debugLineNum = 1131;BA.debugLine="fondogris.Color = Colors.ARGB(150,0,0,0)";
mostCurrent._fondogris.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (150),(int) (0),(int) (0),(int) (0)));
 //BA.debugLineNum = 1132;BA.debugLine="Activity.AddView(fondogris, 0, 0, 100%x, 100%y)";
mostCurrent._activity.AddView((android.view.View)(mostCurrent._fondogris.getObject()),(int) (0),(int) (0),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA));
 //BA.debugLineNum = 1134;BA.debugLine="fotogrande.Initialize(\"fotogrande\")";
mostCurrent._fotogrande.Initialize(mostCurrent.activityBA,"fotogrande");
 //BA.debugLineNum = 1135;BA.debugLine="fotogrande.Bitmap = foto2view.Bitmap";
mostCurrent._fotogrande.setBitmap(mostCurrent._foto2view.getBitmap());
 //BA.debugLineNum = 1136;BA.debugLine="fotogrande.Gravity = Gravity.fill";
mostCurrent._fotogrande.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.FILL);
 //BA.debugLineNum = 1137;BA.debugLine="Activity.AddView(fotogrande, 10%x, 10%y, 80%x, 8";
mostCurrent._activity.AddView((android.view.View)(mostCurrent._fotogrande.getObject()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (10),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (10),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (80),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (80),mostCurrent.activityBA));
 //BA.debugLineNum = 1139;BA.debugLine="Activity.AddView(foto2Borrar, 10dip, 10dip, 120d";
mostCurrent._activity.AddView((android.view.View)(mostCurrent._foto2borrar.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (120)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (40)));
 //BA.debugLineNum = 1140;BA.debugLine="btnCerrar.Visible = False";
mostCurrent._btncerrar.setVisible(anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 1142;BA.debugLine="End Sub";
return "";
}
public static void  _foto3borrar_click() throws Exception{
ResumableSub_foto3Borrar_Click rsub = new ResumableSub_foto3Borrar_Click(null);
rsub.resume(processBA, null);
}
public static class ResumableSub_foto3Borrar_Click extends BA.ResumableSub {
public ResumableSub_foto3Borrar_Click(appear.pnud.preservamos.frmdatossinenviar parent) {
this.parent = parent;
}
appear.pnud.preservamos.frmdatossinenviar parent;
int _result = 0;
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
 //BA.debugLineNum = 1286;BA.debugLine="If Main.lang = \"es\" Then";
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
 //BA.debugLineNum = 1287;BA.debugLine="Msgbox2Async(\"Seguro que desea quitar esta foto";
anywheresoftware.b4a.keywords.Common.Msgbox2Async(BA.ObjectToCharSequence("Seguro que desea quitar esta foto de este registro?"),BA.ObjectToCharSequence("Quitar foto"),"Si","No","",(anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper(), (android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null)),processBA,anywheresoftware.b4a.keywords.Common.False);
 if (true) break;

case 5:
//C
this.state = 6;
 //BA.debugLineNum = 1289;BA.debugLine="Msgbox2Async(\"Sure that you want to remove this";
anywheresoftware.b4a.keywords.Common.Msgbox2Async(BA.ObjectToCharSequence("Sure that you want to remove this photo from this report?"),BA.ObjectToCharSequence("Remove photo"),"Yes","No","",(anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper(), (android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null)),processBA,anywheresoftware.b4a.keywords.Common.False);
 if (true) break;

case 6:
//C
this.state = 7;
;
 //BA.debugLineNum = 1291;BA.debugLine="Wait For Msgbox_Result (Result As Int)";
anywheresoftware.b4a.keywords.Common.WaitFor("msgbox_result", processBA, this, null);
this.state = 13;
return;
case 13:
//C
this.state = 7;
_result = (Integer) result[0];
;
 //BA.debugLineNum = 1292;BA.debugLine="If Result = DialogResponse.POSITIVE Then";
if (true) break;

case 7:
//if
this.state = 12;
if (_result==anywheresoftware.b4a.keywords.Common.DialogResponse.POSITIVE) { 
this.state = 9;
}else {
this.state = 11;
}if (true) break;

case 9:
//C
this.state = 12;
 //BA.debugLineNum = 1293;BA.debugLine="Dim Map1 As Map";
_map1 = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 1294;BA.debugLine="Map1.Initialize";
_map1.Initialize();
 //BA.debugLineNum = 1295;BA.debugLine="Map1.Put(\"Id\", currentDatoId)";
_map1.Put((Object)("Id"),(Object)(parent.mostCurrent._currentdatoid));
 //BA.debugLineNum = 1296;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"evals\", \"fo";
parent.mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,parent.mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals","foto3",anywheresoftware.b4a.keywords.Common.Null,_map1);
 //BA.debugLineNum = 1297;BA.debugLine="foto3view.Bitmap = Null";
parent.mostCurrent._foto3view.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null));
 //BA.debugLineNum = 1298;BA.debugLine="foto3Borrar.RemoveView";
parent.mostCurrent._foto3borrar.RemoveView();
 //BA.debugLineNum = 1299;BA.debugLine="fotogrande.RemoveView";
parent.mostCurrent._fotogrande.RemoveView();
 //BA.debugLineNum = 1300;BA.debugLine="hubocambiosFotos = True";
parent._hubocambiosfotos = anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 1301;BA.debugLine="btnEnviar.Visible = True";
parent.mostCurrent._btnenviar.setVisible(anywheresoftware.b4a.keywords.Common.True);
 if (true) break;

case 11:
//C
this.state = 12;
 //BA.debugLineNum = 1303;BA.debugLine="Return";
if (true) return ;
 if (true) break;

case 12:
//C
this.state = -1;
;
 //BA.debugLineNum = 1306;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public static String  _foto3view_click() throws Exception{
 //BA.debugLineNum = 1143;BA.debugLine="Sub foto3view_Click";
 //BA.debugLineNum = 1144;BA.debugLine="If foto3view.Bitmap = Null Then";
if (mostCurrent._foto3view.getBitmap()== null) { 
 //BA.debugLineNum = 1145;BA.debugLine="fotoAdjuntar = \"foto3\"";
mostCurrent._fotoadjuntar = "foto3";
 //BA.debugLineNum = 1146;BA.debugLine="CC.Initialize(\"CC\")";
_cc.Initialize("CC");
 //BA.debugLineNum = 1147;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 1148;BA.debugLine="CC.Show(\"image/\", \"Elija la foto\")";
_cc.Show(processBA,"image/","Elija la foto");
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 1150;BA.debugLine="CC.Show(\"image/\", \"Choose the photo\")";
_cc.Show(processBA,"image/","Choose the photo");
 };
 }else {
 //BA.debugLineNum = 1155;BA.debugLine="fondogris.Initialize(\"fondogris\")";
mostCurrent._fondogris.Initialize(mostCurrent.activityBA,"fondogris");
 //BA.debugLineNum = 1156;BA.debugLine="fondogris.Color = Colors.ARGB(150,0,0,0)";
mostCurrent._fondogris.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (150),(int) (0),(int) (0),(int) (0)));
 //BA.debugLineNum = 1157;BA.debugLine="Activity.AddView(fondogris, 0, 0, 100%x, 100%y)";
mostCurrent._activity.AddView((android.view.View)(mostCurrent._fondogris.getObject()),(int) (0),(int) (0),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA));
 //BA.debugLineNum = 1159;BA.debugLine="fotogrande.Initialize(\"fotogrande\")";
mostCurrent._fotogrande.Initialize(mostCurrent.activityBA,"fotogrande");
 //BA.debugLineNum = 1160;BA.debugLine="fotogrande.Bitmap = foto2view.Bitmap";
mostCurrent._fotogrande.setBitmap(mostCurrent._foto2view.getBitmap());
 //BA.debugLineNum = 1161;BA.debugLine="fotogrande.Gravity = Gravity.fill";
mostCurrent._fotogrande.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.FILL);
 //BA.debugLineNum = 1162;BA.debugLine="Activity.AddView(fotogrande, 10%x, 10%y, 80%x, 8";
mostCurrent._activity.AddView((android.view.View)(mostCurrent._fotogrande.getObject()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (10),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (10),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (80),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (80),mostCurrent.activityBA));
 //BA.debugLineNum = 1164;BA.debugLine="Activity.AddView(foto3Borrar, 10dip, 10dip, 120d";
mostCurrent._activity.AddView((android.view.View)(mostCurrent._foto3borrar.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (120)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (40)));
 //BA.debugLineNum = 1165;BA.debugLine="btnCerrar.Visible = False";
mostCurrent._btncerrar.setVisible(anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 1167;BA.debugLine="End Sub";
return "";
}
public static void  _foto4borrar_click() throws Exception{
ResumableSub_foto4Borrar_Click rsub = new ResumableSub_foto4Borrar_Click(null);
rsub.resume(processBA, null);
}
public static class ResumableSub_foto4Borrar_Click extends BA.ResumableSub {
public ResumableSub_foto4Borrar_Click(appear.pnud.preservamos.frmdatossinenviar parent) {
this.parent = parent;
}
appear.pnud.preservamos.frmdatossinenviar parent;
int _result = 0;
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
 //BA.debugLineNum = 1308;BA.debugLine="If Main.lang = \"es\" Then";
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
 //BA.debugLineNum = 1309;BA.debugLine="Msgbox2Async(\"Seguro que desea quitar esta foto";
anywheresoftware.b4a.keywords.Common.Msgbox2Async(BA.ObjectToCharSequence("Seguro que desea quitar esta foto de este registro?"),BA.ObjectToCharSequence("Quitar foto"),"Si","No","",(anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper(), (android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null)),processBA,anywheresoftware.b4a.keywords.Common.False);
 if (true) break;

case 5:
//C
this.state = 6;
 //BA.debugLineNum = 1311;BA.debugLine="Msgbox2Async(\"Sure that you want to remove this";
anywheresoftware.b4a.keywords.Common.Msgbox2Async(BA.ObjectToCharSequence("Sure that you want to remove this photo from this report?"),BA.ObjectToCharSequence("Remove photo"),"Yes","No","",(anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper(), (android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null)),processBA,anywheresoftware.b4a.keywords.Common.False);
 if (true) break;

case 6:
//C
this.state = 7;
;
 //BA.debugLineNum = 1313;BA.debugLine="Wait For Msgbox_Result (Result As Int)";
anywheresoftware.b4a.keywords.Common.WaitFor("msgbox_result", processBA, this, null);
this.state = 13;
return;
case 13:
//C
this.state = 7;
_result = (Integer) result[0];
;
 //BA.debugLineNum = 1314;BA.debugLine="If Result = DialogResponse.POSITIVE Then";
if (true) break;

case 7:
//if
this.state = 12;
if (_result==anywheresoftware.b4a.keywords.Common.DialogResponse.POSITIVE) { 
this.state = 9;
}else {
this.state = 11;
}if (true) break;

case 9:
//C
this.state = 12;
 //BA.debugLineNum = 1316;BA.debugLine="Dim Map1 As Map";
_map1 = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 1317;BA.debugLine="Map1.Initialize";
_map1.Initialize();
 //BA.debugLineNum = 1318;BA.debugLine="Map1.Put(\"Id\", currentDatoId)";
_map1.Put((Object)("Id"),(Object)(parent.mostCurrent._currentdatoid));
 //BA.debugLineNum = 1319;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"evals\", \"fo";
parent.mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,parent.mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals","foto4",anywheresoftware.b4a.keywords.Common.Null,_map1);
 //BA.debugLineNum = 1320;BA.debugLine="foto4view.Bitmap = Null";
parent.mostCurrent._foto4view.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null));
 //BA.debugLineNum = 1321;BA.debugLine="foto4Borrar.RemoveView";
parent.mostCurrent._foto4borrar.RemoveView();
 //BA.debugLineNum = 1322;BA.debugLine="fotogrande.RemoveView";
parent.mostCurrent._fotogrande.RemoveView();
 //BA.debugLineNum = 1323;BA.debugLine="hubocambiosFotos = True";
parent._hubocambiosfotos = anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 1324;BA.debugLine="btnEnviar.Visible = True";
parent.mostCurrent._btnenviar.setVisible(anywheresoftware.b4a.keywords.Common.True);
 if (true) break;

case 11:
//C
this.state = 12;
 //BA.debugLineNum = 1326;BA.debugLine="Return";
if (true) return ;
 if (true) break;

case 12:
//C
this.state = -1;
;
 //BA.debugLineNum = 1329;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public static String  _foto4view_click() throws Exception{
 //BA.debugLineNum = 1168;BA.debugLine="Sub foto4view_Click";
 //BA.debugLineNum = 1169;BA.debugLine="If foto4view.Bitmap = Null Then";
if (mostCurrent._foto4view.getBitmap()== null) { 
 //BA.debugLineNum = 1170;BA.debugLine="fotoAdjuntar = \"foto4\"";
mostCurrent._fotoadjuntar = "foto4";
 //BA.debugLineNum = 1171;BA.debugLine="CC.Initialize(\"CC\")";
_cc.Initialize("CC");
 //BA.debugLineNum = 1172;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 1173;BA.debugLine="CC.Show(\"image/\", \"Elija la foto\")";
_cc.Show(processBA,"image/","Elija la foto");
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 1175;BA.debugLine="CC.Show(\"image/\", \"Choose the photo\")";
_cc.Show(processBA,"image/","Choose the photo");
 };
 }else {
 //BA.debugLineNum = 1180;BA.debugLine="fondogris.Initialize(\"fondogris\")";
mostCurrent._fondogris.Initialize(mostCurrent.activityBA,"fondogris");
 //BA.debugLineNum = 1181;BA.debugLine="fondogris.Color = Colors.ARGB(150,0,0,0)";
mostCurrent._fondogris.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (150),(int) (0),(int) (0),(int) (0)));
 //BA.debugLineNum = 1182;BA.debugLine="Activity.AddView(fondogris, 0, 0, 100%x, 100%y)";
mostCurrent._activity.AddView((android.view.View)(mostCurrent._fondogris.getObject()),(int) (0),(int) (0),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA));
 //BA.debugLineNum = 1184;BA.debugLine="fotogrande.Initialize(\"fotogrande\")";
mostCurrent._fotogrande.Initialize(mostCurrent.activityBA,"fotogrande");
 //BA.debugLineNum = 1185;BA.debugLine="fotogrande.Bitmap = foto4view.Bitmap";
mostCurrent._fotogrande.setBitmap(mostCurrent._foto4view.getBitmap());
 //BA.debugLineNum = 1186;BA.debugLine="fotogrande.Gravity = Gravity.fill";
mostCurrent._fotogrande.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.FILL);
 //BA.debugLineNum = 1187;BA.debugLine="Activity.AddView(fotogrande, 10%x, 10%y, 80%x, 8";
mostCurrent._activity.AddView((android.view.View)(mostCurrent._fotogrande.getObject()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (10),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (10),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (80),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (80),mostCurrent.activityBA));
 //BA.debugLineNum = 1189;BA.debugLine="Activity.AddView(foto4Borrar, 10dip, 10dip, 120d";
mostCurrent._activity.AddView((android.view.View)(mostCurrent._foto4borrar.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (120)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (40)));
 //BA.debugLineNum = 1190;BA.debugLine="btnCerrar.Visible = False";
mostCurrent._btncerrar.setVisible(anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 1192;BA.debugLine="End Sub";
return "";
}
public static void  _foto5borrar_click() throws Exception{
ResumableSub_foto5Borrar_Click rsub = new ResumableSub_foto5Borrar_Click(null);
rsub.resume(processBA, null);
}
public static class ResumableSub_foto5Borrar_Click extends BA.ResumableSub {
public ResumableSub_foto5Borrar_Click(appear.pnud.preservamos.frmdatossinenviar parent) {
this.parent = parent;
}
appear.pnud.preservamos.frmdatossinenviar parent;
int _result = 0;
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
 //BA.debugLineNum = 1331;BA.debugLine="If Main.lang = \"es\" Then";
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
 //BA.debugLineNum = 1332;BA.debugLine="Msgbox2Async(\"Seguro que desea quitar esta foto";
anywheresoftware.b4a.keywords.Common.Msgbox2Async(BA.ObjectToCharSequence("Seguro que desea quitar esta foto de este registro?"),BA.ObjectToCharSequence("Quitar foto"),"Si","No","",(anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper(), (android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null)),processBA,anywheresoftware.b4a.keywords.Common.False);
 if (true) break;

case 5:
//C
this.state = 6;
 //BA.debugLineNum = 1334;BA.debugLine="Msgbox2Async(\"Sure that you want to remove this";
anywheresoftware.b4a.keywords.Common.Msgbox2Async(BA.ObjectToCharSequence("Sure that you want to remove this photo from this report?"),BA.ObjectToCharSequence("Remove photo"),"Yes","No","",(anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper(), (android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null)),processBA,anywheresoftware.b4a.keywords.Common.False);
 if (true) break;

case 6:
//C
this.state = 7;
;
 //BA.debugLineNum = 1336;BA.debugLine="Wait For Msgbox_Result (Result As Int)";
anywheresoftware.b4a.keywords.Common.WaitFor("msgbox_result", processBA, this, null);
this.state = 13;
return;
case 13:
//C
this.state = 7;
_result = (Integer) result[0];
;
 //BA.debugLineNum = 1337;BA.debugLine="If Result = DialogResponse.POSITIVE Then";
if (true) break;

case 7:
//if
this.state = 12;
if (_result==anywheresoftware.b4a.keywords.Common.DialogResponse.POSITIVE) { 
this.state = 9;
}else {
this.state = 11;
}if (true) break;

case 9:
//C
this.state = 12;
 //BA.debugLineNum = 1338;BA.debugLine="Dim Map1 As Map";
_map1 = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 1339;BA.debugLine="Map1.Initialize";
_map1.Initialize();
 //BA.debugLineNum = 1340;BA.debugLine="Map1.Put(\"Id\", currentDatoId)";
_map1.Put((Object)("Id"),(Object)(parent.mostCurrent._currentdatoid));
 //BA.debugLineNum = 1341;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"evals\", \"fo";
parent.mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,parent.mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals","foto5",anywheresoftware.b4a.keywords.Common.Null,_map1);
 //BA.debugLineNum = 1342;BA.debugLine="foto5view.Bitmap = Null";
parent.mostCurrent._foto5view.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null));
 //BA.debugLineNum = 1343;BA.debugLine="foto5Borrar.RemoveView";
parent.mostCurrent._foto5borrar.RemoveView();
 //BA.debugLineNum = 1344;BA.debugLine="fotogrande.RemoveView";
parent.mostCurrent._fotogrande.RemoveView();
 //BA.debugLineNum = 1345;BA.debugLine="hubocambiosFotos = True";
parent._hubocambiosfotos = anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 1346;BA.debugLine="btnEnviar.Visible = True";
parent.mostCurrent._btnenviar.setVisible(anywheresoftware.b4a.keywords.Common.True);
 if (true) break;

case 11:
//C
this.state = 12;
 //BA.debugLineNum = 1348;BA.debugLine="Return";
if (true) return ;
 if (true) break;

case 12:
//C
this.state = -1;
;
 //BA.debugLineNum = 1351;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public static String  _foto5view_click() throws Exception{
 //BA.debugLineNum = 1193;BA.debugLine="Sub foto5view_Click";
 //BA.debugLineNum = 1194;BA.debugLine="If foto5view.Bitmap = Null Then";
if (mostCurrent._foto5view.getBitmap()== null) { 
 //BA.debugLineNum = 1195;BA.debugLine="fotoAdjuntar = \"foto5\"";
mostCurrent._fotoadjuntar = "foto5";
 //BA.debugLineNum = 1196;BA.debugLine="CC.Initialize(\"CC\")";
_cc.Initialize("CC");
 //BA.debugLineNum = 1197;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 1198;BA.debugLine="CC.Show(\"image/\", \"Elija la foto\")";
_cc.Show(processBA,"image/","Elija la foto");
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 1200;BA.debugLine="CC.Show(\"image/\", \"Choose the photo\")";
_cc.Show(processBA,"image/","Choose the photo");
 };
 }else {
 //BA.debugLineNum = 1205;BA.debugLine="fondogris.Initialize(\"fondogris\")";
mostCurrent._fondogris.Initialize(mostCurrent.activityBA,"fondogris");
 //BA.debugLineNum = 1206;BA.debugLine="fondogris.Color = Colors.ARGB(150,0,0,0)";
mostCurrent._fondogris.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (150),(int) (0),(int) (0),(int) (0)));
 //BA.debugLineNum = 1207;BA.debugLine="Activity.AddView(fondogris, 0, 0, 100%x, 100%y)";
mostCurrent._activity.AddView((android.view.View)(mostCurrent._fondogris.getObject()),(int) (0),(int) (0),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA));
 //BA.debugLineNum = 1209;BA.debugLine="fotogrande.Initialize(\"fotogrande\")";
mostCurrent._fotogrande.Initialize(mostCurrent.activityBA,"fotogrande");
 //BA.debugLineNum = 1210;BA.debugLine="fotogrande.Bitmap = foto5view.Bitmap";
mostCurrent._fotogrande.setBitmap(mostCurrent._foto5view.getBitmap());
 //BA.debugLineNum = 1211;BA.debugLine="fotogrande.Gravity = Gravity.fill";
mostCurrent._fotogrande.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.FILL);
 //BA.debugLineNum = 1212;BA.debugLine="Activity.AddView(fotogrande, 10%x, 10%y, 80%x, 8";
mostCurrent._activity.AddView((android.view.View)(mostCurrent._fotogrande.getObject()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (10),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (10),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (80),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (80),mostCurrent.activityBA));
 //BA.debugLineNum = 1214;BA.debugLine="Activity.AddView(foto5Borrar, 10dip, 10dip, 40di";
mostCurrent._activity.AddView((android.view.View)(mostCurrent._foto5borrar.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (40)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (40)));
 //BA.debugLineNum = 1215;BA.debugLine="btnCerrar.Visible = False";
mostCurrent._btncerrar.setVisible(anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 1217;BA.debugLine="End Sub";
return "";
}
public static String  _fotogrande_click() throws Exception{
 //BA.debugLineNum = 1226;BA.debugLine="Sub fotogrande_Click";
 //BA.debugLineNum = 1227;BA.debugLine="fotogrande.RemoveView";
mostCurrent._fotogrande.RemoveView();
 //BA.debugLineNum = 1228;BA.debugLine="foto1Borrar.RemoveView";
mostCurrent._foto1borrar.RemoveView();
 //BA.debugLineNum = 1229;BA.debugLine="foto2Borrar.RemoveView";
mostCurrent._foto2borrar.RemoveView();
 //BA.debugLineNum = 1230;BA.debugLine="fondogris.RemoveView";
mostCurrent._fondogris.RemoveView();
 //BA.debugLineNum = 1231;BA.debugLine="btnCerrar.Visible = True";
mostCurrent._btncerrar.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1232;BA.debugLine="End Sub";
return "";
}
public static String  _georef_geocodedone(uk.co.martinpearman.b4a.location.AddressWrapper[] _results,Object _tag) throws Exception{
uk.co.martinpearman.b4a.location.AddressWrapper _address1 = null;
int _i = 0;
 //BA.debugLineNum = 1641;BA.debugLine="Sub georef_GeocodeDone(Results() As Address, Tag A";
 //BA.debugLineNum = 1642;BA.debugLine="If Results.Length>0 Then";
if (_results.length>0) { 
 //BA.debugLineNum = 1643;BA.debugLine="Dim Address1 As Address";
_address1 = new uk.co.martinpearman.b4a.location.AddressWrapper();
 //BA.debugLineNum = 1644;BA.debugLine="Dim i As Int";
_i = 0;
 //BA.debugLineNum = 1645;BA.debugLine="For i=0 To Results.Length-1";
{
final int step4 = 1;
final int limit4 = (int) (_results.length-1);
_i = (int) (0) ;
for (;_i <= limit4 ;_i = _i + step4 ) {
 //BA.debugLineNum = 1646;BA.debugLine="Address1=Results(i)";
_address1 = _results[_i];
 //BA.debugLineNum = 1648;BA.debugLine="Log(Address1)";
anywheresoftware.b4a.keywords.Common.LogImpl("443843591",BA.ObjectToString(_address1),0);
 //BA.debugLineNum = 1649;BA.debugLine="If Address1.SubAdminArea <> \"\" And Address1.Sub";
if ((_address1.getSubAdminArea()).equals("") == false && _address1.getSubAdminArea()!= null) { 
 //BA.debugLineNum = 1650;BA.debugLine="Log(Address1.SubAdminArea) ' se queda con el p";
anywheresoftware.b4a.keywords.Common.LogImpl("443843593",_address1.getSubAdminArea(),0);
 //BA.debugLineNum = 1651;BA.debugLine="Geopartido = Address1.SubAdminArea";
_geopartido = _address1.getSubAdminArea();
 //BA.debugLineNum = 1652;BA.debugLine="Exit";
if (true) break;
 };
 }
};
 }else {
 //BA.debugLineNum = 1656;BA.debugLine="ToastMessageShow(\"No se reconoció el municipio\",";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("No se reconoció el municipio"),anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 1660;BA.debugLine="If hubocambiosDatos = True Or chkEnviado.Checked";
if (_hubocambiosdatos==anywheresoftware.b4a.keywords.Common.True || mostCurrent._chkenviado.getChecked()==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 1661;BA.debugLine="EnviarDatos(IdProyectoEnviar)";
_enviardatos(mostCurrent._idproyectoenviar);
 };
 //BA.debugLineNum = 1663;BA.debugLine="End Sub";
return "";
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 35;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 36;BA.debugLine="Private rp As RuntimePermissions";
mostCurrent._rp = new anywheresoftware.b4a.objects.RuntimePermissions();
 //BA.debugLineNum = 38;BA.debugLine="Dim ListView1 As ListView";
mostCurrent._listview1 = new anywheresoftware.b4a.objects.ListViewWrapper();
 //BA.debugLineNum = 40;BA.debugLine="Private btnCerrar As Button";
mostCurrent._btncerrar = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 43;BA.debugLine="Private tabStripDatosAnteriores As TabStrip";
mostCurrent._tabstripdatosanteriores = new anywheresoftware.b4a.objects.TabStripViewPager();
 //BA.debugLineNum = 44;BA.debugLine="Dim tabActual As String";
mostCurrent._tabactual = "";
 //BA.debugLineNum = 47;BA.debugLine="Private lblFecha As Label";
mostCurrent._lblfecha = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 48;BA.debugLine="Private lblTipo As Label";
mostCurrent._lbltipo = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 49;BA.debugLine="Private lblLat As Label";
mostCurrent._lbllat = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 50;BA.debugLine="Private lblCalidad As Label";
mostCurrent._lblcalidad = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 51;BA.debugLine="Private lblNotas As Label";
mostCurrent._lblnotas = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 52;BA.debugLine="Private lblLng As Label";
mostCurrent._lbllng = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 53;BA.debugLine="Private chkPublico As CheckBox";
mostCurrent._chkpublico = new anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper();
 //BA.debugLineNum = 54;BA.debugLine="Private chkEnviado As CheckBox";
mostCurrent._chkenviado = new anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper();
 //BA.debugLineNum = 55;BA.debugLine="Private chkValidadoExpertos As Label";
mostCurrent._chkvalidadoexpertos = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 56;BA.debugLine="Private chkOnline As Label";
mostCurrent._chkonline = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 57;BA.debugLine="Private chkEnviadoBar As Label";
mostCurrent._chkenviadobar = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 58;BA.debugLine="Private scvFotos As HorizontalScrollView";
mostCurrent._scvfotos = new anywheresoftware.b4a.objects.HorizontalScrollViewWrapper();
 //BA.debugLineNum = 59;BA.debugLine="Private lblNombreEnvio As Label";
mostCurrent._lblnombreenvio = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 60;BA.debugLine="Private lblBarra As Label";
mostCurrent._lblbarra = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 61;BA.debugLine="Dim currentDatoId As String";
mostCurrent._currentdatoid = "";
 //BA.debugLineNum = 63;BA.debugLine="Dim currentDatoTipo As String";
mostCurrent._currentdatotipo = "";
 //BA.debugLineNum = 66;BA.debugLine="Dim foto1view As ImageView";
mostCurrent._foto1view = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 67;BA.debugLine="Dim foto2view As ImageView";
mostCurrent._foto2view = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 68;BA.debugLine="Dim foto3view As ImageView";
mostCurrent._foto3view = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 69;BA.debugLine="Dim foto4view As ImageView";
mostCurrent._foto4view = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 70;BA.debugLine="Dim foto5view As ImageView";
mostCurrent._foto5view = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 71;BA.debugLine="Dim fotogrande As ImageView";
mostCurrent._fotogrande = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 72;BA.debugLine="Dim fondogris As Label";
mostCurrent._fondogris = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 73;BA.debugLine="Dim foto1Borrar As Button";
mostCurrent._foto1borrar = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 74;BA.debugLine="Dim foto2Borrar As Button";
mostCurrent._foto2borrar = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 75;BA.debugLine="Dim foto3Borrar As Button";
mostCurrent._foto3borrar = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 76;BA.debugLine="Dim foto4Borrar As Button";
mostCurrent._foto4borrar = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 77;BA.debugLine="Dim foto5Borrar As Button";
mostCurrent._foto5borrar = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 78;BA.debugLine="Dim fotoAdjuntar As String";
mostCurrent._fotoadjuntar = "";
 //BA.debugLineNum = 81;BA.debugLine="Private btnEnviar As Button";
mostCurrent._btnenviar = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 82;BA.debugLine="Dim IdProyectoEnviar As String";
mostCurrent._idproyectoenviar = "";
 //BA.debugLineNum = 83;BA.debugLine="Private btnCambiarPublico As Button";
mostCurrent._btncambiarpublico = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 84;BA.debugLine="Dim NewMarcador As Boolean = True";
_newmarcador = anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 85;BA.debugLine="Dim numberTasks As Int";
_numbertasks = 0;
 //BA.debugLineNum = 87;BA.debugLine="Dim dated As DateDialog";
mostCurrent._dated = new anywheresoftware.b4a.agraham.dialogs.InputDialog.DateDialog();
 //BA.debugLineNum = 91;BA.debugLine="Private hubocambiosDatos As Boolean";
_hubocambiosdatos = false;
 //BA.debugLineNum = 92;BA.debugLine="Private hubocambiosFotos As Boolean";
_hubocambiosfotos = false;
 //BA.debugLineNum = 93;BA.debugLine="Private fondoblanco As Label";
mostCurrent._fondoblanco = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 94;BA.debugLine="Private foto1Prg As Label";
mostCurrent._foto1prg = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 95;BA.debugLine="Private foto2Prg As Label";
mostCurrent._foto2prg = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 96;BA.debugLine="Private foto3Prg As Label";
mostCurrent._foto3prg = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 97;BA.debugLine="Private foto4Prg As Label";
mostCurrent._foto4prg = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 98;BA.debugLine="Private foto5Prg As Label";
mostCurrent._foto5prg = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 99;BA.debugLine="Private ProgressBar1 As ProgressBar";
mostCurrent._progressbar1 = new anywheresoftware.b4a.objects.ProgressBarWrapper();
 //BA.debugLineNum = 100;BA.debugLine="Private ProgressBar2 As ProgressBar";
mostCurrent._progressbar2 = new anywheresoftware.b4a.objects.ProgressBarWrapper();
 //BA.debugLineNum = 101;BA.debugLine="Private ProgressBar3 As ProgressBar";
mostCurrent._progressbar3 = new anywheresoftware.b4a.objects.ProgressBarWrapper();
 //BA.debugLineNum = 102;BA.debugLine="Private ProgressBar4 As ProgressBar";
mostCurrent._progressbar4 = new anywheresoftware.b4a.objects.ProgressBarWrapper();
 //BA.debugLineNum = 103;BA.debugLine="Private ProgressBar5 As ProgressBar";
mostCurrent._progressbar5 = new anywheresoftware.b4a.objects.ProgressBarWrapper();
 //BA.debugLineNum = 105;BA.debugLine="Private totalFotos As Int";
_totalfotos = 0;
 //BA.debugLineNum = 106;BA.debugLine="Private foto1Sent As Boolean";
_foto1sent = false;
 //BA.debugLineNum = 107;BA.debugLine="Private foto2Sent As Boolean";
_foto2sent = false;
 //BA.debugLineNum = 108;BA.debugLine="Private foto3Sent As Boolean";
_foto3sent = false;
 //BA.debugLineNum = 109;BA.debugLine="Private foto4Sent As Boolean";
_foto4sent = false;
 //BA.debugLineNum = 110;BA.debugLine="Private foto5Sent As Boolean";
_foto5sent = false;
 //BA.debugLineNum = 112;BA.debugLine="Dim fotosEnviadas As Int";
_fotosenviadas = 0;
 //BA.debugLineNum = 114;BA.debugLine="Private Label9 As Label";
mostCurrent._label9 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 119;BA.debugLine="Private Label6 As Label";
mostCurrent._label6 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 120;BA.debugLine="Private Label7 As Label";
mostCurrent._label7 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 121;BA.debugLine="Private Label8 As Label";
mostCurrent._label8 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 122;BA.debugLine="Private Label10 As Label";
mostCurrent._label10 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 123;BA.debugLine="Private Label12 As Label";
mostCurrent._label12 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 124;BA.debugLine="Private Label3 As Label";
mostCurrent._label3 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 125;BA.debugLine="Private Label2 As Label";
mostCurrent._label2 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 126;BA.debugLine="Private Label13 As Label";
mostCurrent._label13 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 129;BA.debugLine="Private lstEnviosRealizados As ListView";
mostCurrent._lstenviosrealizados = new anywheresoftware.b4a.objects.ListViewWrapper();
 //BA.debugLineNum = 130;BA.debugLine="End Sub";
return "";
}
public static String  _listardatos() throws Exception{
anywheresoftware.b4a.objects.collections.List _listmaps = null;
int _r = 0;
anywheresoftware.b4a.objects.collections.Map _rmap = null;
String _esent = "";
String _latdato = "";
String _lngdato = "";
String _fechadato = "";
String _iddato = "";
anywheresoftware.b4a.objects.collections.List _listmaps_residuos = null;
anywheresoftware.b4a.objects.collections.List _listmaps_hidro = null;
 //BA.debugLineNum = 237;BA.debugLine="Sub ListarDatos";
 //BA.debugLineNum = 238;BA.debugLine="ListView1.Clear";
mostCurrent._listview1.Clear();
 //BA.debugLineNum = 239;BA.debugLine="ListView1.Color = Colors.White";
mostCurrent._listview1.setColor(anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 240;BA.debugLine="ListView1.TwoLinesAndBitmap.Label.Width = ListVie";
mostCurrent._listview1.getTwoLinesAndBitmap().Label.setWidth((int) (mostCurrent._listview1.getWidth()-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (50))));
 //BA.debugLineNum = 241;BA.debugLine="ListView1.TwoLinesAndBitmap.SecondLabel.Width = L";
mostCurrent._listview1.getTwoLinesAndBitmap().SecondLabel.setWidth((int) (mostCurrent._listview1.getWidth()-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (50))));
 //BA.debugLineNum = 242;BA.debugLine="ListView1.TwoLinesAndBitmap.Label.TextColor = Col";
mostCurrent._listview1.getTwoLinesAndBitmap().Label.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 243;BA.debugLine="ListView1.TwoLinesAndBitmap.SecondLabel.TextColor";
mostCurrent._listview1.getTwoLinesAndBitmap().SecondLabel.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (150),(int) (0),(int) (0),(int) (0)));
 //BA.debugLineNum = 244;BA.debugLine="ListView1.TwoLinesAndBitmap.Label.TextSize = 14";
mostCurrent._listview1.getTwoLinesAndBitmap().Label.setTextSize((float) (14));
 //BA.debugLineNum = 245;BA.debugLine="ListView1.TwoLinesAndBitmap.SecondLabel.TextSize";
mostCurrent._listview1.getTwoLinesAndBitmap().SecondLabel.setTextSize((float) (14));
 //BA.debugLineNum = 246;BA.debugLine="ListView1.TwoLinesAndBitmap.ImageView.Left = List";
mostCurrent._listview1.getTwoLinesAndBitmap().ImageView.setLeft((int) (mostCurrent._listview1.getWidth()-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (50))));
 //BA.debugLineNum = 247;BA.debugLine="ListView1.TwoLinesAndBitmap.ImageView.Gravity = G";
mostCurrent._listview1.getTwoLinesAndBitmap().ImageView.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.CENTER);
 //BA.debugLineNum = 248;BA.debugLine="ListView1.TwoLinesAndBitmap.ImageView.Width = 20d";
mostCurrent._listview1.getTwoLinesAndBitmap().ImageView.setWidth(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (20)));
 //BA.debugLineNum = 249;BA.debugLine="ListView1.TwoLinesAndBitmap.ImageView.Height = 20";
mostCurrent._listview1.getTwoLinesAndBitmap().ImageView.setHeight(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (20)));
 //BA.debugLineNum = 250;BA.debugLine="ListView1.TwoLinesAndBitmap.ImageView.top = (List";
mostCurrent._listview1.getTwoLinesAndBitmap().ImageView.setTop((int) ((mostCurrent._listview1.getTwoLinesAndBitmap().getItemHeight()/(double)2)-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10))));
 //BA.debugLineNum = 251;BA.debugLine="currentDatoId = \"\"";
mostCurrent._currentdatoid = "";
 //BA.debugLineNum = 255;BA.debugLine="Dim listmaps As List";
_listmaps = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 256;BA.debugLine="listmaps.Initialize";
_listmaps.Initialize();
 //BA.debugLineNum = 257;BA.debugLine="listmaps = DBUtils.ExecuteListOfMaps(Starter.sqlD";
_listmaps = mostCurrent._dbutils._executelistofmaps /*anywheresoftware.b4a.objects.collections.List*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"SELECT * FROM evals",(String[])(anywheresoftware.b4a.keywords.Common.Null),(int) (0));
 //BA.debugLineNum = 260;BA.debugLine="If listmaps = Null Or listmaps.IsInitialized = Fa";
if (_listmaps== null || _listmaps.IsInitialized()==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 261;BA.debugLine="hayAnteriores = False";
_hayanteriores = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 262;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 };
 }else {
 //BA.debugLineNum = 269;BA.debugLine="If listmaps.Size = 0 Then";
if (_listmaps.getSize()==0) { 
 //BA.debugLineNum = 271;BA.debugLine="hayAnteriores = False";
_hayanteriores = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 272;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 };
 //BA.debugLineNum = 277;BA.debugLine="tabStripDatosAnteriores.ScrollTo(0,True)";
mostCurrent._tabstripdatosanteriores.ScrollTo((int) (0),anywheresoftware.b4a.keywords.Common.True);
 }else {
 //BA.debugLineNum = 280;BA.debugLine="hayAnteriores = True";
_hayanteriores = anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 281;BA.debugLine="Try";
try { //BA.debugLineNum = 282;BA.debugLine="For r = 0 To listmaps.Size -1";
{
final int step33 = 1;
final int limit33 = (int) (_listmaps.getSize()-1);
_r = (int) (0) ;
for (;_r <= limit33 ;_r = _r + step33 ) {
 //BA.debugLineNum = 283;BA.debugLine="Dim rmap As Map";
_rmap = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 284;BA.debugLine="rmap = listmaps.Get(r)";
_rmap = (anywheresoftware.b4a.objects.collections.Map) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.collections.Map(), (java.util.Map)(_listmaps.Get(_r)));
 //BA.debugLineNum = 285;BA.debugLine="Dim esent As String = rmap.Get(\"evalsent\")";
_esent = BA.ObjectToString(_rmap.Get((Object)("evalsent")));
 //BA.debugLineNum = 286;BA.debugLine="Dim latdato As String = rmap.Get(\"decimallati";
_latdato = BA.ObjectToString(_rmap.Get((Object)("decimallatitude")));
 //BA.debugLineNum = 287;BA.debugLine="Dim lngdato As String = rmap.Get(\"decimallong";
_lngdato = BA.ObjectToString(_rmap.Get((Object)("decimallongitude")));
 //BA.debugLineNum = 288;BA.debugLine="Dim fechadato As String = rmap.Get(\"georefere";
_fechadato = BA.ObjectToString(_rmap.Get((Object)("georeferenceddate")));
 //BA.debugLineNum = 289;BA.debugLine="Dim iddato As String = rmap.Get(\"id\")";
_iddato = BA.ObjectToString(_rmap.Get((Object)("id")));
 //BA.debugLineNum = 291;BA.debugLine="If fechadato = \"\" Or fechadato = \"null\" Then";
if ((_fechadato).equals("") || (_fechadato).equals("null")) { 
 //BA.debugLineNum = 292;BA.debugLine="fechadato = \"Sin fecha\"";
_fechadato = "Sin fecha";
 };
 //BA.debugLineNum = 294;BA.debugLine="If latdato = \"null\" Or latdato = \"\" Then";
if ((_latdato).equals("null") || (_latdato).equals("")) { 
 //BA.debugLineNum = 295;BA.debugLine="latdato = \"Sin latitud\"";
_latdato = "Sin latitud";
 };
 //BA.debugLineNum = 297;BA.debugLine="If lngdato = \"null\"  Or lngdato = \"\" Then";
if ((_lngdato).equals("null") || (_lngdato).equals("")) { 
 //BA.debugLineNum = 298;BA.debugLine="lngdato = \"Sin longitud\"";
_lngdato = "Sin longitud";
 };
 //BA.debugLineNum = 300;BA.debugLine="If esent = \"si\" Then";
if ((_esent).equals("si")) { 
 }else {
 //BA.debugLineNum = 305;BA.debugLine="ListView1.AddTwoLinesAndBitmap2(\"HÁBITAT - F";
mostCurrent._listview1.AddTwoLinesAndBitmap2(BA.ObjectToCharSequence("HÁBITAT - Fecha: "+_fechadato),BA.ObjectToCharSequence(_latdato+"/"+_lngdato),(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"not_sent.png").getObject()),(Object)("habitat_"+_iddato));
 };
 }
};
 } 
       catch (Exception e56) {
			processBA.setLastException(e56); //BA.debugLineNum = 310;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 311;BA.debugLine="ToastMessageShow(\"Ha habido un error cargando";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Ha habido un error cargando sus datos sin enviar, intente de nuevo!"),anywheresoftware.b4a.keywords.Common.False);
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 313;BA.debugLine="ToastMessageShow(\"There's been an error, plea";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("There's been an error, please try again!"),anywheresoftware.b4a.keywords.Common.False);
 };
 };
 };
 };
 //BA.debugLineNum = 321;BA.debugLine="Dim listmaps_residuos As List";
_listmaps_residuos = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 322;BA.debugLine="listmaps_residuos.Initialize";
_listmaps_residuos.Initialize();
 //BA.debugLineNum = 323;BA.debugLine="listmaps_residuos = DBUtils.ExecuteListOfMaps(Sta";
_listmaps_residuos = mostCurrent._dbutils._executelistofmaps /*anywheresoftware.b4a.objects.collections.List*/ (mostCurrent.activityBA,mostCurrent._starter._modulo_residuos_sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"SELECT * FROM evals_residuos",(String[])(anywheresoftware.b4a.keywords.Common.Null),(int) (0));
 //BA.debugLineNum = 326;BA.debugLine="If listmaps_residuos = Null Or listmaps_residuos.";
if (_listmaps_residuos== null || _listmaps_residuos.IsInitialized()==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 327;BA.debugLine="hayAnteriores_residuos = False";
_hayanteriores_residuos = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 328;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 };
 }else {
 //BA.debugLineNum = 332;BA.debugLine="If listmaps_residuos.Size = 0 Then";
if (_listmaps_residuos.getSize()==0) { 
 //BA.debugLineNum = 334;BA.debugLine="hayAnteriores_residuos = False";
_hayanteriores_residuos = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 335;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 };
 //BA.debugLineNum = 340;BA.debugLine="tabStripDatosAnteriores.ScrollTo(0,True)";
mostCurrent._tabstripdatosanteriores.ScrollTo((int) (0),anywheresoftware.b4a.keywords.Common.True);
 }else {
 //BA.debugLineNum = 343;BA.debugLine="hayAnteriores_residuos = True";
_hayanteriores_residuos = anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 344;BA.debugLine="Try";
try { //BA.debugLineNum = 345;BA.debugLine="For r = 0 To listmaps_residuos.Size -1";
{
final int step81 = 1;
final int limit81 = (int) (_listmaps_residuos.getSize()-1);
_r = (int) (0) ;
for (;_r <= limit81 ;_r = _r + step81 ) {
 //BA.debugLineNum = 346;BA.debugLine="Dim rmap As Map";
_rmap = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 347;BA.debugLine="rmap = listmaps_residuos.Get(r)";
_rmap = (anywheresoftware.b4a.objects.collections.Map) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.collections.Map(), (java.util.Map)(_listmaps_residuos.Get(_r)));
 //BA.debugLineNum = 348;BA.debugLine="Dim esent As String = rmap.Get(\"evalsent\")";
_esent = BA.ObjectToString(_rmap.Get((Object)("evalsent")));
 //BA.debugLineNum = 349;BA.debugLine="Dim latdato As String = rmap.Get(\"decimallati";
_latdato = BA.ObjectToString(_rmap.Get((Object)("decimallatitude")));
 //BA.debugLineNum = 350;BA.debugLine="Dim lngdato As String = rmap.Get(\"decimallong";
_lngdato = BA.ObjectToString(_rmap.Get((Object)("decimallongitude")));
 //BA.debugLineNum = 351;BA.debugLine="Dim fechadato As String = rmap.Get(\"georefere";
_fechadato = BA.ObjectToString(_rmap.Get((Object)("georeferenceddate")));
 //BA.debugLineNum = 352;BA.debugLine="Dim iddato As String = rmap.Get(\"id\")";
_iddato = BA.ObjectToString(_rmap.Get((Object)("id")));
 //BA.debugLineNum = 354;BA.debugLine="If fechadato = \"\" Or fechadato = \"null\" Then";
if ((_fechadato).equals("") || (_fechadato).equals("null")) { 
 //BA.debugLineNum = 355;BA.debugLine="fechadato = \"Sin fecha\"";
_fechadato = "Sin fecha";
 };
 //BA.debugLineNum = 357;BA.debugLine="If latdato = \"null\" Or latdato = \"\" Then";
if ((_latdato).equals("null") || (_latdato).equals("")) { 
 //BA.debugLineNum = 358;BA.debugLine="latdato = \"Sin latitud\"";
_latdato = "Sin latitud";
 };
 //BA.debugLineNum = 360;BA.debugLine="If lngdato = \"null\"  Or lngdato = \"\" Then";
if ((_lngdato).equals("null") || (_lngdato).equals("")) { 
 //BA.debugLineNum = 361;BA.debugLine="lngdato = \"Sin longitud\"";
_lngdato = "Sin longitud";
 };
 //BA.debugLineNum = 363;BA.debugLine="If esent = \"si\" Then";
if ((_esent).equals("si")) { 
 }else {
 //BA.debugLineNum = 368;BA.debugLine="ListView1.AddTwoLinesAndBitmap2(\"RESIDUOS -";
mostCurrent._listview1.AddTwoLinesAndBitmap2(BA.ObjectToCharSequence("RESIDUOS - Fecha: "+_fechadato),BA.ObjectToCharSequence(_latdato+"/"+_lngdato),(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"not_sent.png").getObject()),(Object)("residuos_"+_iddato));
 };
 }
};
 } 
       catch (Exception e104) {
			processBA.setLastException(e104); //BA.debugLineNum = 373;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 374;BA.debugLine="ToastMessageShow(\"Ha habido un error cargando";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Ha habido un error cargando sus datos sin enviar, intente de nuevo!"),anywheresoftware.b4a.keywords.Common.False);
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 376;BA.debugLine="ToastMessageShow(\"There's been an error, plea";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("There's been an error, please try again!"),anywheresoftware.b4a.keywords.Common.False);
 };
 };
 };
 };
 //BA.debugLineNum = 386;BA.debugLine="Dim listmaps_hidro As List";
_listmaps_hidro = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 387;BA.debugLine="listmaps_hidro.Initialize";
_listmaps_hidro.Initialize();
 //BA.debugLineNum = 388;BA.debugLine="listmaps_hidro = DBUtils.ExecuteListOfMaps(Starte";
_listmaps_hidro = mostCurrent._dbutils._executelistofmaps /*anywheresoftware.b4a.objects.collections.List*/ (mostCurrent.activityBA,mostCurrent._starter._modulo_hidro_sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"SELECT * FROM evals_hidro",(String[])(anywheresoftware.b4a.keywords.Common.Null),(int) (0));
 //BA.debugLineNum = 391;BA.debugLine="If listmaps_hidro = Null Or listmaps_hidro.IsInit";
if (_listmaps_hidro== null || _listmaps_hidro.IsInitialized()==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 392;BA.debugLine="hayAnteriores_hidro = False";
_hayanteriores_hidro = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 393;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 };
 }else {
 //BA.debugLineNum = 397;BA.debugLine="If listmaps_hidro.Size = 0 Then";
if (_listmaps_hidro.getSize()==0) { 
 //BA.debugLineNum = 399;BA.debugLine="hayAnteriores_hidro = False";
_hayanteriores_hidro = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 400;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 };
 //BA.debugLineNum = 405;BA.debugLine="tabStripDatosAnteriores.ScrollTo(0,True)";
mostCurrent._tabstripdatosanteriores.ScrollTo((int) (0),anywheresoftware.b4a.keywords.Common.True);
 }else {
 //BA.debugLineNum = 410;BA.debugLine="hayAnteriores_hidro = True";
_hayanteriores_hidro = anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 411;BA.debugLine="Try";
try { //BA.debugLineNum = 412;BA.debugLine="For r = 0 To listmaps_hidro.Size -1";
{
final int step129 = 1;
final int limit129 = (int) (_listmaps_hidro.getSize()-1);
_r = (int) (0) ;
for (;_r <= limit129 ;_r = _r + step129 ) {
 //BA.debugLineNum = 413;BA.debugLine="Dim rmap As Map";
_rmap = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 414;BA.debugLine="rmap = listmaps_hidro.Get(r)";
_rmap = (anywheresoftware.b4a.objects.collections.Map) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.collections.Map(), (java.util.Map)(_listmaps_hidro.Get(_r)));
 //BA.debugLineNum = 415;BA.debugLine="Dim esent As String = rmap.Get(\"evalsent\")";
_esent = BA.ObjectToString(_rmap.Get((Object)("evalsent")));
 //BA.debugLineNum = 416;BA.debugLine="Dim latdato As String = rmap.Get(\"decimallati";
_latdato = BA.ObjectToString(_rmap.Get((Object)("decimallatitude")));
 //BA.debugLineNum = 417;BA.debugLine="Dim lngdato As String = rmap.Get(\"decimallong";
_lngdato = BA.ObjectToString(_rmap.Get((Object)("decimallongitude")));
 //BA.debugLineNum = 418;BA.debugLine="Dim fechadato As String = rmap.Get(\"georefere";
_fechadato = BA.ObjectToString(_rmap.Get((Object)("georeferenceddate")));
 //BA.debugLineNum = 419;BA.debugLine="Dim iddato As String = rmap.Get(\"id\")";
_iddato = BA.ObjectToString(_rmap.Get((Object)("id")));
 //BA.debugLineNum = 421;BA.debugLine="If fechadato = \"\" Or fechadato = \"null\" Then";
if ((_fechadato).equals("") || (_fechadato).equals("null")) { 
 //BA.debugLineNum = 422;BA.debugLine="fechadato = \"Sin fecha\"";
_fechadato = "Sin fecha";
 };
 //BA.debugLineNum = 424;BA.debugLine="If latdato = \"null\" Or latdato = \"\" Then";
if ((_latdato).equals("null") || (_latdato).equals("")) { 
 //BA.debugLineNum = 425;BA.debugLine="latdato = \"Sin latitud\"";
_latdato = "Sin latitud";
 };
 //BA.debugLineNum = 427;BA.debugLine="If lngdato = \"null\"  Or lngdato = \"\" Then";
if ((_lngdato).equals("null") || (_lngdato).equals("")) { 
 //BA.debugLineNum = 428;BA.debugLine="lngdato = \"Sin longitud\"";
_lngdato = "Sin longitud";
 };
 //BA.debugLineNum = 430;BA.debugLine="If esent = \"si\" Then";
if ((_esent).equals("si")) { 
 }else {
 //BA.debugLineNum = 435;BA.debugLine="ListView1.AddTwoLinesAndBitmap2(\"HIDRO - Fec";
mostCurrent._listview1.AddTwoLinesAndBitmap2(BA.ObjectToCharSequence("HIDRO - Fecha: "+_fechadato),BA.ObjectToCharSequence(_latdato+"/"+_lngdato),(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"not_sent.png").getObject()),(Object)("hidro_"+_iddato));
 };
 }
};
 } 
       catch (Exception e152) {
			processBA.setLastException(e152); //BA.debugLineNum = 440;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 441;BA.debugLine="ToastMessageShow(\"Ha habido un error cargando";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Ha habido un error cargando sus datos sin enviar, intente de nuevo!"),anywheresoftware.b4a.keywords.Common.False);
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 443;BA.debugLine="ToastMessageShow(\"There's been an error, plea";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("There's been an error, please try again!"),anywheresoftware.b4a.keywords.Common.False);
 };
 };
 };
 };
 //BA.debugLineNum = 454;BA.debugLine="End Sub";
return "";
}
public static String  _listview1_itemclick(int _position,Object _value) throws Exception{
 //BA.debugLineNum = 456;BA.debugLine="Sub ListView1_ItemClick (Position As Int, Value As";
 //BA.debugLineNum = 458;BA.debugLine="currentDatoId = Value";
mostCurrent._currentdatoid = BA.ObjectToString(_value);
 //BA.debugLineNum = 459;BA.debugLine="VerDetalles(currentDatoId, False)";
_verdetalles(mostCurrent._currentdatoid,anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 460;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 6;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 7;BA.debugLine="Dim hc As OkHttpClient";
_hc = new anywheresoftware.b4h.okhttp.OkHttpClientWrapper();
 //BA.debugLineNum = 8;BA.debugLine="Dim out As OutputStream";
_out = new anywheresoftware.b4a.objects.streams.File.OutputStreamWrapper();
 //BA.debugLineNum = 9;BA.debugLine="Dim CC As ContentChooser";
_cc = new anywheresoftware.b4a.phone.Phone.ContentChooser();
 //BA.debugLineNum = 12;BA.debugLine="Dim nuevalatlng As String";
_nuevalatlng = "";
 //BA.debugLineNum = 15;BA.debugLine="Dim notificacion As Boolean";
_notificacion = false;
 //BA.debugLineNum = 16;BA.debugLine="Dim serverIdNum As String";
_serveridnum = "";
 //BA.debugLineNum = 17;BA.debugLine="Dim hayAnteriores As Boolean";
_hayanteriores = false;
 //BA.debugLineNum = 18;BA.debugLine="Dim hayAnteriores_residuos As Boolean";
_hayanteriores_residuos = false;
 //BA.debugLineNum = 19;BA.debugLine="Dim hayAnteriores_hidro As Boolean";
_hayanteriores_hidro = false;
 //BA.debugLineNum = 22;BA.debugLine="Dim Up1 As UploadFilePhp";
_up1 = new com.spinter.uploadfilephp.UploadFilePhp();
 //BA.debugLineNum = 23;BA.debugLine="Private foto1 As String";
_foto1 = "";
 //BA.debugLineNum = 24;BA.debugLine="Private foto2 As String";
_foto2 = "";
 //BA.debugLineNum = 25;BA.debugLine="Private foto3 As String";
_foto3 = "";
 //BA.debugLineNum = 26;BA.debugLine="Private foto4 As String";
_foto4 = "";
 //BA.debugLineNum = 27;BA.debugLine="Private foto5 As String";
_foto5 = "";
 //BA.debugLineNum = 28;BA.debugLine="Private TimerEnvio As Timer";
_timerenvio = new anywheresoftware.b4a.objects.Timer();
 //BA.debugLineNum = 31;BA.debugLine="Dim Geopartido As String";
_geopartido = "";
 //BA.debugLineNum = 32;BA.debugLine="Dim georef As Geocoder";
_georef = new uk.co.martinpearman.b4a.location.GeocoderWrapper();
 //BA.debugLineNum = 33;BA.debugLine="End Sub";
return "";
}
public static String  _tabstripdatosanteriores_pageselected(int _position) throws Exception{
 //BA.debugLineNum = 195;BA.debugLine="Sub tabStripDatosAnteriores_PageSelected (Position";
 //BA.debugLineNum = 196;BA.debugLine="If Position = 0 Then";
if (_position==0) { 
 //BA.debugLineNum = 197;BA.debugLine="foto1Borrar.RemoveView";
mostCurrent._foto1borrar.RemoveView();
 //BA.debugLineNum = 198;BA.debugLine="foto2Borrar.RemoveView";
mostCurrent._foto2borrar.RemoveView();
 //BA.debugLineNum = 199;BA.debugLine="ListarDatos";
_listardatos();
 //BA.debugLineNum = 200;BA.debugLine="NewMarcador = True";
_newmarcador = anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 201;BA.debugLine="btnCerrar.Text = \"\"";
mostCurrent._btncerrar.setText(BA.ObjectToCharSequence(""));
 }else if(_position==1) { 
 //BA.debugLineNum = 203;BA.debugLine="If hayAnteriores = False Then";
if (_hayanteriores==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 204;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 205;BA.debugLine="ToastMessageShow(\"No tenés datos sin enviar...";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("No tenés datos sin enviar..."),anywheresoftware.b4a.keywords.Common.False);
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 207;BA.debugLine="ToastMessageShow(\"There are no previous report";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("There are no previous reports..."),anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 209;BA.debugLine="tabStripDatosAnteriores.ScrollTo(0,True)";
mostCurrent._tabstripdatosanteriores.ScrollTo((int) (0),anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 211;BA.debugLine="If currentDatoId = \"\" Then";
if ((mostCurrent._currentdatoid).equals("")) { 
 //BA.debugLineNum = 212;BA.debugLine="tabStripDatosAnteriores.ScrollTo(0,True)";
mostCurrent._tabstripdatosanteriores.ScrollTo((int) (0),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 213;BA.debugLine="ToastMessageShow(\"Debes seleccionar un dato par";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Debes seleccionar un dato para ver sus detalles"),anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 215;BA.debugLine="btnCerrar.Text = \"\"";
mostCurrent._btncerrar.setText(BA.ObjectToCharSequence(""));
 };
 //BA.debugLineNum = 217;BA.debugLine="End Sub";
return "";
}
public static String  _testinternet_complete(appear.pnud.preservamos.httpjob _job) throws Exception{
 //BA.debugLineNum = 1604;BA.debugLine="Sub TestInternet_Complete(Job As HttpJob)";
 //BA.debugLineNum = 1605;BA.debugLine="Log(\"Chequeo de internet: \" & Job.Success)";
anywheresoftware.b4a.keywords.Common.LogImpl("443712513","Chequeo de internet: "+BA.ObjectToString(_job._success /*boolean*/ ),0);
 //BA.debugLineNum = 1606;BA.debugLine="If Job.Success = True Then";
if (_job._success /*boolean*/ ==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 1608;BA.debugLine="Main.hayinternet = True";
mostCurrent._main._hayinternet /*boolean*/  = anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 1610;BA.debugLine="Job.Release";
_job._release /*String*/ ();
 //BA.debugLineNum = 1612;BA.debugLine="BuscarMunicipio";
_buscarmunicipio();
 }else {
 //BA.debugLineNum = 1617;BA.debugLine="Main.hayinternet = False";
mostCurrent._main._hayinternet /*boolean*/  = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 1618;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 1619;BA.debugLine="ToastMessageShow(\"No hay conexión a internet, p";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("No hay conexión a internet, prueba cuando estés conectado!"),anywheresoftware.b4a.keywords.Common.False);
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 1621;BA.debugLine="ToastMessageShow(\"There is no internet, try aga";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("There is no internet, try again when you're connected!"),anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 1623;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 //BA.debugLineNum = 1624;BA.debugLine="Job.Release";
_job._release /*String*/ ();
 //BA.debugLineNum = 1625;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 1626;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 };
 //BA.debugLineNum = 1628;BA.debugLine="End Sub";
return "";
}
public static String  _timerenvio_tick() throws Exception{
anywheresoftware.b4a.objects.collections.Map _map1 = null;
 //BA.debugLineNum = 2123;BA.debugLine="Sub TimerEnvio_Tick";
 //BA.debugLineNum = 2124;BA.debugLine="If fotosEnviadas = totalFotos Then";
if (_fotosenviadas==_totalfotos) { 
 //BA.debugLineNum = 2125;BA.debugLine="Log(\"TODAS LAS FOTOS FUERON ENVIADAS CORRECTAMEN";
anywheresoftware.b4a.keywords.Common.LogImpl("444171266","TODAS LAS FOTOS FUERON ENVIADAS CORRECTAMENTE",0);
 //BA.debugLineNum = 2126;BA.debugLine="Up1.UploadKill";
_up1.UploadKill(processBA);
 //BA.debugLineNum = 2131;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 };
 //BA.debugLineNum = 2136;BA.debugLine="ToastMessageShow(\"Evaluación enviada\", False)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Evaluación enviada"),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 2138;BA.debugLine="Dim Map1 As Map";
_map1 = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 2139;BA.debugLine="Map1.Initialize";
_map1.Initialize();
 //BA.debugLineNum = 2140;BA.debugLine="Map1.Put(\"Id\", IdProyectoEnviar)";
_map1.Put((Object)("Id"),(Object)(mostCurrent._idproyectoenviar));
 //BA.debugLineNum = 2142;BA.debugLine="If 	currentDatoTipo = \"habitat\" Then";
if ((mostCurrent._currentdatotipo).equals("habitat")) { 
 //BA.debugLineNum = 2143;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"evals\", \"f";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals","foto1sent",(Object)("si"),_map1);
 //BA.debugLineNum = 2144;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"evals\", \"f";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals","foto2sent",(Object)("si"),_map1);
 //BA.debugLineNum = 2145;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"evals\", \"f";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals","foto3sent",(Object)("si"),_map1);
 //BA.debugLineNum = 2146;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"evals\", \"f";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals","foto4sent",(Object)("si"),_map1);
 //BA.debugLineNum = 2147;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"evals\", \"f";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals","foto5sent",(Object)("si"),_map1);
 }else if((mostCurrent._currentdatotipo).equals("residuos")) { 
 //BA.debugLineNum = 2149;BA.debugLine="DBUtils.UpdateRecord(Starter.modulo_residuos_sq";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._modulo_residuos_sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals_residuos","foto1sent",(Object)("si"),_map1);
 }else if((mostCurrent._currentdatotipo).equals("hidro")) { 
 //BA.debugLineNum = 2151;BA.debugLine="DBUtils.UpdateRecord(Starter.modulo_hidro_sqlDB";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._modulo_hidro_sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals_hidro","foto1sent",(Object)("si"),_map1);
 };
 //BA.debugLineNum = 2154;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 2155;BA.debugLine="ToastMessageShow(\"Evaluación enviada\", False)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Evaluación enviada"),anywheresoftware.b4a.keywords.Common.False);
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 2157;BA.debugLine="ToastMessageShow(\"Report sent\", False)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Report sent"),anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 2160;BA.debugLine="TimerEnvio.Enabled = False";
_timerenvio.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 2161;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 2162;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 2163;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 };
 //BA.debugLineNum = 2165;BA.debugLine="End Sub";
return "";
}
public static String  _up1_sendfile(String _value) throws Exception{
 //BA.debugLineNum = 2172;BA.debugLine="Sub Up1_sendFile (value As String)";
 //BA.debugLineNum = 2173;BA.debugLine="Log(\"sendfile event:\" & value)";
anywheresoftware.b4a.keywords.Common.LogImpl("444302337","sendfile event:"+_value,0);
 //BA.debugLineNum = 2174;BA.debugLine="If value = \"success\" Then";
if ((_value).equals("success")) { 
 //BA.debugLineNum = 2177;BA.debugLine="If fotosEnviadas = 0 And totalFotos = 1 Then";
if (_fotosenviadas==0 && _totalfotos==1) { 
 //BA.debugLineNum = 2178;BA.debugLine="fotosEnviadas = 1";
_fotosenviadas = (int) (1);
 //BA.debugLineNum = 2179;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 2181;BA.debugLine="If fotosEnviadas = 1 And totalFotos = 2 Then";
if (_fotosenviadas==1 && _totalfotos==2) { 
 //BA.debugLineNum = 2182;BA.debugLine="fotosEnviadas = 2";
_fotosenviadas = (int) (2);
 //BA.debugLineNum = 2183;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 2185;BA.debugLine="If fotosEnviadas = 2 And totalFotos = 3 Then";
if (_fotosenviadas==2 && _totalfotos==3) { 
 //BA.debugLineNum = 2186;BA.debugLine="fotosEnviadas = 3";
_fotosenviadas = (int) (3);
 //BA.debugLineNum = 2187;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 2189;BA.debugLine="If fotosEnviadas = 3 And totalFotos = 4 Then";
if (_fotosenviadas==3 && _totalfotos==4) { 
 //BA.debugLineNum = 2190;BA.debugLine="fotosEnviadas = 4";
_fotosenviadas = (int) (4);
 //BA.debugLineNum = 2191;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 2193;BA.debugLine="If fotosEnviadas = 4 And totalFotos = 5 Then";
if (_fotosenviadas==4 && _totalfotos==5) { 
 //BA.debugLineNum = 2194;BA.debugLine="fotosEnviadas = 5";
_fotosenviadas = (int) (5);
 //BA.debugLineNum = 2195;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 2199;BA.debugLine="If fotosEnviadas = 0 And totalFotos > 1 Then";
if (_fotosenviadas==0 && _totalfotos>1) { 
 //BA.debugLineNum = 2200;BA.debugLine="fotosEnviadas = 1";
_fotosenviadas = (int) (1);
 //BA.debugLineNum = 2201;BA.debugLine="If File.Exists(rp.GetSafeDirDefaultExternal(\"Pr";
if (anywheresoftware.b4a.keywords.Common.File.Exists(mostCurrent._rp.GetSafeDirDefaultExternal("PreserVamos"),_foto2+".jpg") && _foto2sent==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 2202;BA.debugLine="Log(\"Enviando foto 2 \")";
anywheresoftware.b4a.keywords.Common.LogImpl("444302366","Enviando foto 2 ",0);
 //BA.debugLineNum = 2203;BA.debugLine="ProgressBar2.Progress  = 0";
mostCurrent._progressbar2.setProgress((int) (0));
 //BA.debugLineNum = 2204;BA.debugLine="Up1.doFileUpload(ProgressBar2,Null,rp.GetSafeD";
_up1.doFileUpload(processBA,(android.widget.ProgressBar)(mostCurrent._progressbar2.getObject()),(android.widget.TextView)(anywheresoftware.b4a.keywords.Common.Null),mostCurrent._rp.GetSafeDirDefaultExternal("PreserVamos")+"/"+_foto2+".jpg",mostCurrent._main._serverpath /*String*/ +"/"+mostCurrent._main._serverconnectionfolder /*String*/ +"/upload_file.php");
 }else {
 //BA.debugLineNum = 2206;BA.debugLine="Log(\"no foto 2\")";
anywheresoftware.b4a.keywords.Common.LogImpl("444302370","no foto 2",0);
 };
 };
 //BA.debugLineNum = 2209;BA.debugLine="If fotosEnviadas = 1 And totalFotos > 2 Then";
if (_fotosenviadas==1 && _totalfotos>2) { 
 //BA.debugLineNum = 2210;BA.debugLine="fotosEnviadas = 2";
_fotosenviadas = (int) (2);
 //BA.debugLineNum = 2211;BA.debugLine="If File.Exists(rp.GetSafeDirDefaultExternal(\"Pr";
if (anywheresoftware.b4a.keywords.Common.File.Exists(mostCurrent._rp.GetSafeDirDefaultExternal("PreserVamos"),_foto3+".jpg") && _foto3sent==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 2212;BA.debugLine="Log(\"Enviando foto 3 \")";
anywheresoftware.b4a.keywords.Common.LogImpl("444302376","Enviando foto 3 ",0);
 //BA.debugLineNum = 2213;BA.debugLine="ProgressBar3.Progress  = 0";
mostCurrent._progressbar3.setProgress((int) (0));
 //BA.debugLineNum = 2214;BA.debugLine="Up1.doFileUpload(ProgressBar3,Null,rp.GetSafeD";
_up1.doFileUpload(processBA,(android.widget.ProgressBar)(mostCurrent._progressbar3.getObject()),(android.widget.TextView)(anywheresoftware.b4a.keywords.Common.Null),mostCurrent._rp.GetSafeDirDefaultExternal("PreserVamos")+"/"+_foto3+".jpg",mostCurrent._main._serverpath /*String*/ +"/"+mostCurrent._main._serverconnectionfolder /*String*/ +"/upload_file.php");
 }else {
 //BA.debugLineNum = 2216;BA.debugLine="Log(\"no foto 3\")";
anywheresoftware.b4a.keywords.Common.LogImpl("444302380","no foto 3",0);
 };
 };
 //BA.debugLineNum = 2219;BA.debugLine="If fotosEnviadas = 2 And totalFotos > 3 Then";
if (_fotosenviadas==2 && _totalfotos>3) { 
 //BA.debugLineNum = 2220;BA.debugLine="fotosEnviadas = 3";
_fotosenviadas = (int) (3);
 //BA.debugLineNum = 2221;BA.debugLine="If File.Exists(rp.GetSafeDirDefaultExternal(\"Pr";
if (anywheresoftware.b4a.keywords.Common.File.Exists(mostCurrent._rp.GetSafeDirDefaultExternal("PreserVamos"),_foto4+".jpg") && _foto4sent==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 2222;BA.debugLine="Log(\"Enviando foto 4 \")";
anywheresoftware.b4a.keywords.Common.LogImpl("444302386","Enviando foto 4 ",0);
 //BA.debugLineNum = 2223;BA.debugLine="ProgressBar4.Progress  = 0";
mostCurrent._progressbar4.setProgress((int) (0));
 //BA.debugLineNum = 2224;BA.debugLine="Up1.doFileUpload(ProgressBar4,Null,rp.GetSafeD";
_up1.doFileUpload(processBA,(android.widget.ProgressBar)(mostCurrent._progressbar4.getObject()),(android.widget.TextView)(anywheresoftware.b4a.keywords.Common.Null),mostCurrent._rp.GetSafeDirDefaultExternal("PreserVamos")+"/"+_foto4+".jpg",mostCurrent._main._serverpath /*String*/ +"/"+mostCurrent._main._serverconnectionfolder /*String*/ +"/upload_file.php");
 }else {
 //BA.debugLineNum = 2226;BA.debugLine="Log(\"no foto 4\")";
anywheresoftware.b4a.keywords.Common.LogImpl("444302390","no foto 4",0);
 };
 };
 //BA.debugLineNum = 2229;BA.debugLine="If fotosEnviadas = 3 And totalFotos > 4 Then";
if (_fotosenviadas==3 && _totalfotos>4) { 
 //BA.debugLineNum = 2230;BA.debugLine="fotosEnviadas = 4";
_fotosenviadas = (int) (4);
 //BA.debugLineNum = 2231;BA.debugLine="If File.Exists(rp.GetSafeDirDefaultExternal(\"Pr";
if (anywheresoftware.b4a.keywords.Common.File.Exists(mostCurrent._rp.GetSafeDirDefaultExternal("PreserVamos"),_foto5+".jpg") && _foto5sent==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 2232;BA.debugLine="Log(\"Enviando foto 5 \")";
anywheresoftware.b4a.keywords.Common.LogImpl("444302396","Enviando foto 5 ",0);
 //BA.debugLineNum = 2233;BA.debugLine="ProgressBar5.Progress  = 0";
mostCurrent._progressbar5.setProgress((int) (0));
 //BA.debugLineNum = 2234;BA.debugLine="Up1.doFileUpload(ProgressBar5,Null,rp.GetSafeD";
_up1.doFileUpload(processBA,(android.widget.ProgressBar)(mostCurrent._progressbar5.getObject()),(android.widget.TextView)(anywheresoftware.b4a.keywords.Common.Null),mostCurrent._rp.GetSafeDirDefaultExternal("PreserVamos")+"/"+_foto5+".jpg",mostCurrent._main._serverpath /*String*/ +"/"+mostCurrent._main._serverconnectionfolder /*String*/ +"/upload_file.php");
 }else {
 //BA.debugLineNum = 2236;BA.debugLine="Log(\"no foto 5\")";
anywheresoftware.b4a.keywords.Common.LogImpl("444302400","no foto 5",0);
 };
 };
 }else if((_value).equals("Error!")) { 
 //BA.debugLineNum = 2242;BA.debugLine="Log(\"FOTO error\")";
anywheresoftware.b4a.keywords.Common.LogImpl("444302406","FOTO error",0);
 //BA.debugLineNum = 2243;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 2244;BA.debugLine="MsgboxAsync(\"Ha habido un error en el envío. Re";
anywheresoftware.b4a.keywords.Common.MsgboxAsync(BA.ObjectToCharSequence("Ha habido un error en el envío. Revisa tu conexión a Internet e intenta de nuevo desde 'Datos sin enviar'"),BA.ObjectToCharSequence("Oops!"),processBA);
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 2246;BA.debugLine="MsgboxAsync(\"There has been an error during the";
anywheresoftware.b4a.keywords.Common.MsgboxAsync(BA.ObjectToCharSequence("There has been an error during the upload. Check your internet connection and try again"),BA.ObjectToCharSequence("Oops!"),processBA);
 };
 //BA.debugLineNum = 2248;BA.debugLine="Up1.UploadKill";
_up1.UploadKill(processBA);
 //BA.debugLineNum = 2253;BA.debugLine="TimerEnvio.Enabled = False";
_timerenvio.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 2254;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 2255;BA.debugLine="Activity_Create(False)";
_activity_create(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 2256;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 };
 //BA.debugLineNum = 2258;BA.debugLine="End Sub";
return "";
}
public static String  _up1_statusupload(String _value) throws Exception{
 //BA.debugLineNum = 2168;BA.debugLine="Sub Up1_statusUpload (value As String)";
 //BA.debugLineNum = 2170;BA.debugLine="End Sub";
return "";
}
public static String  _up1_statusvisible(boolean _onoff,String _value) throws Exception{
 //BA.debugLineNum = 2259;BA.debugLine="Sub Up1_statusVISIBLE (onoff As Boolean,value As S";
 //BA.debugLineNum = 2261;BA.debugLine="End Sub";
return "";
}
public static String  _verdetalles(String _datoid,boolean _serverid) throws Exception{
String[] _arrid = null;
String _tiporegistro = "";
anywheresoftware.b4a.objects.collections.Map _datomap = null;
String _datoenviado = "";
String _datorevisado = "";
String _datoprivado = "";
String _foto1path = "";
String _foto2path = "";
String _foto3path = "";
String _foto4path = "";
String _foto5path = "";
anywheresoftware.b4a.objects.drawable.ColorDrawable _cd = null;
anywheresoftware.b4a.objects.LabelWrapper _cdplus1 = null;
anywheresoftware.b4a.objects.LabelWrapper _cdplus2 = null;
anywheresoftware.b4a.objects.collections.Map _datomap_residuos = null;
anywheresoftware.b4a.objects.collections.Map _datomap_hidro = null;
 //BA.debugLineNum = 463;BA.debugLine="Sub VerDetalles(datoID As String, serverId As Bool";
 //BA.debugLineNum = 465;BA.debugLine="Dim arrID() As String = Regex.Split(\"_\",datoID)";
_arrid = anywheresoftware.b4a.keywords.Common.Regex.Split("_",_datoid);
 //BA.debugLineNum = 466;BA.debugLine="Dim tiporegistro As String";
_tiporegistro = "";
 //BA.debugLineNum = 467;BA.debugLine="Log(arrID)";
anywheresoftware.b4a.keywords.Common.LogImpl("442401796",BA.ObjectToString(_arrid),0);
 //BA.debugLineNum = 468;BA.debugLine="tiporegistro = arrID(0)";
_tiporegistro = _arrid[(int) (0)];
 //BA.debugLineNum = 469;BA.debugLine="datoID = arrID(1)";
_datoid = _arrid[(int) (1)];
 //BA.debugLineNum = 471;BA.debugLine="If tiporegistro = \"enviado\" Then";
if ((_tiporegistro).equals("enviado")) { 
 //BA.debugLineNum = 472;BA.debugLine="ToastMessageShow(\"Por ahora, no se pueden editar";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Por ahora, no se pueden editar los análisis ya enviados!"),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 473;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 476;BA.debugLine="If tiporegistro = \"habitat\" Then";
if ((_tiporegistro).equals("habitat")) { 
 //BA.debugLineNum = 478;BA.debugLine="currentDatoTipo = \"habitat\"";
mostCurrent._currentdatotipo = "habitat";
 //BA.debugLineNum = 479;BA.debugLine="Dim datoMap As Map";
_datomap = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 480;BA.debugLine="datoMap.Initialize";
_datomap.Initialize();
 //BA.debugLineNum = 481;BA.debugLine="If serverId = False Then";
if (_serverid==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 482;BA.debugLine="datoMap = DBUtils.ExecuteMap(Starter.sqlDB, \"SE";
_datomap = mostCurrent._dbutils._executemap /*anywheresoftware.b4a.objects.collections.Map*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"SELECT * FROM evals WHERE Id=?",new String[]{_datoid});
 }else {
 //BA.debugLineNum = 484;BA.debugLine="datoMap = DBUtils.ExecuteMap(Starter.sqlDB, \"SE";
_datomap = mostCurrent._dbutils._executemap /*anywheresoftware.b4a.objects.collections.Map*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"SELECT * FROM evals WHERE serverid=?",new String[]{_datoid});
 //BA.debugLineNum = 485;BA.debugLine="hayAnteriores = True";
_hayanteriores = anywheresoftware.b4a.keywords.Common.True;
 };
 //BA.debugLineNum = 489;BA.debugLine="Dim datoenviado As String";
_datoenviado = "";
 //BA.debugLineNum = 490;BA.debugLine="datoenviado = datoMap.Get(\"evalsent\")";
_datoenviado = BA.ObjectToString(_datomap.Get((Object)("evalsent")));
 //BA.debugLineNum = 491;BA.debugLine="If datoenviado = \"si\" Then";
if ((_datoenviado).equals("si")) { 
 //BA.debugLineNum = 498;BA.debugLine="btnEnviar.Visible = False";
mostCurrent._btnenviar.setVisible(anywheresoftware.b4a.keywords.Common.False);
 }else {
 //BA.debugLineNum = 511;BA.debugLine="btnEnviar.Visible = True";
mostCurrent._btnenviar.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 513;BA.debugLine="chkEnviado.Checked = False";
mostCurrent._chkenviado.setChecked(anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 518;BA.debugLine="If datoMap = Null Or datoMap.IsInitialized = Fal";
if (_datomap== null || _datomap.IsInitialized()==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 519;BA.debugLine="tabStripDatosAnteriores.ScrollTo(0,True)";
mostCurrent._tabstripdatosanteriores.ScrollTo((int) (0),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 520;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 521;BA.debugLine="ToastMessageShow(\"Dato no encontrado...\", Fals";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Dato no encontrado..."),anywheresoftware.b4a.keywords.Common.False);
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 523;BA.debugLine="ToastMessageShow(\"Report not found...\", False)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Report not found..."),anywheresoftware.b4a.keywords.Common.False);
 };
 }else {
 //BA.debugLineNum = 527;BA.debugLine="IdProyectoEnviar = datoID";
mostCurrent._idproyectoenviar = _datoid;
 //BA.debugLineNum = 529;BA.debugLine="lblNombreEnvio.Text = datoMap.Get(\"georeference";
mostCurrent._lblnombreenvio.setText(BA.ObjectToCharSequence(_datomap.Get((Object)("georeferenceddate"))));
 //BA.debugLineNum = 530;BA.debugLine="lblFecha.Text = \"Análisis de hábitat: \" & datoM";
mostCurrent._lblfecha.setText(BA.ObjectToCharSequence("Análisis de hábitat: "+BA.ObjectToString(_datomap.Get((Object)("georeferenceddate")))));
 //BA.debugLineNum = 531;BA.debugLine="lblCalidad.Text = datoMap.Get(\"valorcalidad\")";
mostCurrent._lblcalidad.setText(BA.ObjectToCharSequence(_datomap.Get((Object)("valorcalidad"))));
 //BA.debugLineNum = 532;BA.debugLine="lblCalidad.Visible= True";
mostCurrent._lblcalidad.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 533;BA.debugLine="Label9.Visible = True";
mostCurrent._label9.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 534;BA.debugLine="lblNotas.Text = datoMap.Get(\"notas\")";
mostCurrent._lblnotas.setText(BA.ObjectToCharSequence(_datomap.Get((Object)("notas"))));
 //BA.debugLineNum = 535;BA.debugLine="lblLat.Text = datoMap.Get(\"decimallatitude\")";
mostCurrent._lbllat.setText(BA.ObjectToCharSequence(_datomap.Get((Object)("decimallatitude"))));
 //BA.debugLineNum = 536;BA.debugLine="lblLng.Text = datoMap.Get(\"decimallongitude\")";
mostCurrent._lbllng.setText(BA.ObjectToCharSequence(_datomap.Get((Object)("decimallongitude"))));
 //BA.debugLineNum = 537;BA.debugLine="lblTipo.Text = datoMap.Get(\"tiporio\")";
mostCurrent._lbltipo.setText(BA.ObjectToCharSequence(_datomap.Get((Object)("tiporio"))));
 //BA.debugLineNum = 538;BA.debugLine="If lblTipo.Text = \"llanura\" Then";
if ((mostCurrent._lbltipo.getText()).equals("llanura")) { 
 //BA.debugLineNum = 539;BA.debugLine="lblTipo.Text = \"Río o arroyo\"";
mostCurrent._lbltipo.setText(BA.ObjectToCharSequence("Río o arroyo"));
 }else if((mostCurrent._lbltipo.getText()).equals("laguna")) { 
 //BA.debugLineNum = 541;BA.debugLine="lblTipo.Text = \"Laguna\"";
mostCurrent._lbltipo.setText(BA.ObjectToCharSequence("Laguna"));
 }else if((mostCurrent._lbltipo.getText()).equals("sierras")) { 
 //BA.debugLineNum = 543;BA.debugLine="lblTipo.Text = \"Río serrano\"";
mostCurrent._lbltipo.setText(BA.ObjectToCharSequence("Río serrano"));
 }else if((mostCurrent._lbltipo.getText()).equals("sierras_laguna")) { 
 //BA.debugLineNum = 545;BA.debugLine="lblTipo.Text = \"Laguna serrana\"";
mostCurrent._lbltipo.setText(BA.ObjectToCharSequence("Laguna serrana"));
 };
 //BA.debugLineNum = 547;BA.debugLine="lblNotas.Visible = True";
mostCurrent._lblnotas.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 548;BA.debugLine="Label10.Visible = True";
mostCurrent._label10.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 549;BA.debugLine="lblFecha.TextColor = Colors.ARGB(255,76,159,56)";
mostCurrent._lblfecha.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (76),(int) (159),(int) (56)));
 //BA.debugLineNum = 550;BA.debugLine="lblCalidad.TextColor = Colors.ARGB(255,76,159,5";
mostCurrent._lblcalidad.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (76),(int) (159),(int) (56)));
 //BA.debugLineNum = 551;BA.debugLine="lblLat.TextColor = Colors.ARGB(255,76,159,56)";
mostCurrent._lbllat.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (76),(int) (159),(int) (56)));
 //BA.debugLineNum = 552;BA.debugLine="lblLng.TextColor = Colors.ARGB(255,76,159,56)";
mostCurrent._lbllng.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (76),(int) (159),(int) (56)));
 //BA.debugLineNum = 553;BA.debugLine="lblTipo.TextColor = Colors.ARGB(255,76,159,56)";
mostCurrent._lbltipo.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (76),(int) (159),(int) (56)));
 //BA.debugLineNum = 555;BA.debugLine="If lblNombreEnvio.Text = \"null\" Then";
if ((mostCurrent._lblnombreenvio.getText()).equals("null")) { 
 //BA.debugLineNum = 556;BA.debugLine="lblNombreEnvio.Text = \"Sin nombre\"";
mostCurrent._lblnombreenvio.setText(BA.ObjectToCharSequence("Sin nombre"));
 };
 //BA.debugLineNum = 558;BA.debugLine="If lblFecha.Text = \"null\" Then";
if ((mostCurrent._lblfecha.getText()).equals("null")) { 
 //BA.debugLineNum = 559;BA.debugLine="lblFecha.Text = \"Sin fecha\"";
mostCurrent._lblfecha.setText(BA.ObjectToCharSequence("Sin fecha"));
 //BA.debugLineNum = 560;BA.debugLine="lblFecha.TextColor = Colors.red";
mostCurrent._lblfecha.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Red);
 };
 //BA.debugLineNum = 562;BA.debugLine="If lblCalidad.Text = \"null\" Then";
if ((mostCurrent._lblcalidad.getText()).equals("null")) { 
 //BA.debugLineNum = 563;BA.debugLine="lblCalidad.Text = \"Calidad no establecida\"";
mostCurrent._lblcalidad.setText(BA.ObjectToCharSequence("Calidad no establecida"));
 //BA.debugLineNum = 564;BA.debugLine="lblCalidad.TextColor = Colors.red";
mostCurrent._lblcalidad.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Red);
 };
 //BA.debugLineNum = 566;BA.debugLine="If lblNotas.Text = \"null\" Then";
if ((mostCurrent._lblnotas.getText()).equals("null")) { 
 //BA.debugLineNum = 567;BA.debugLine="lblNotas.Text = \"Sin notas\"";
mostCurrent._lblnotas.setText(BA.ObjectToCharSequence("Sin notas"));
 };
 //BA.debugLineNum = 569;BA.debugLine="If lblLat.Text = \"null\" Then";
if ((mostCurrent._lbllat.getText()).equals("null")) { 
 //BA.debugLineNum = 570;BA.debugLine="lblLat.Text = \"Sin latitud\"";
mostCurrent._lbllat.setText(BA.ObjectToCharSequence("Sin latitud"));
 //BA.debugLineNum = 571;BA.debugLine="lblLat.TextColor = Colors.red";
mostCurrent._lbllat.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Red);
 };
 //BA.debugLineNum = 573;BA.debugLine="If lblLng.Text = \"null\" Then";
if ((mostCurrent._lbllng.getText()).equals("null")) { 
 //BA.debugLineNum = 574;BA.debugLine="lblLng.Text = \"Sin longitud\"";
mostCurrent._lbllng.setText(BA.ObjectToCharSequence("Sin longitud"));
 //BA.debugLineNum = 575;BA.debugLine="lblLng.TextColor = Colors.red";
mostCurrent._lbllng.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Red);
 };
 //BA.debugLineNum = 577;BA.debugLine="If lblTipo.Text = \"null\" Then";
if ((mostCurrent._lbltipo.getText()).equals("null")) { 
 //BA.debugLineNum = 578;BA.debugLine="lblTipo.Text = \"Tipo no establecido\"";
mostCurrent._lbltipo.setText(BA.ObjectToCharSequence("Tipo no establecido"));
 //BA.debugLineNum = 579;BA.debugLine="lblTipo.TextColor = Colors.red";
mostCurrent._lbltipo.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Red);
 };
 //BA.debugLineNum = 582;BA.debugLine="Dim datorevisado As String";
_datorevisado = "";
 //BA.debugLineNum = 583;BA.debugLine="datorevisado = datoMap.Get(\"estadovalidacion\")";
_datorevisado = BA.ObjectToString(_datomap.Get((Object)("estadovalidacion")));
 //BA.debugLineNum = 584;BA.debugLine="If datorevisado = \"validado\" Then";
if ((_datorevisado).equals("validado")) { 
 //BA.debugLineNum = 585;BA.debugLine="chkEnviado.Visible = False";
mostCurrent._chkenviado.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 587;BA.debugLine="btnEnviar.Visible = False";
mostCurrent._btnenviar.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 588;BA.debugLine="chkEnviado.Checked = True";
mostCurrent._chkenviado.setChecked(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 589;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 590;BA.debugLine="chkEnviado.Text = \" Enviado\"";
mostCurrent._chkenviado.setText(BA.ObjectToCharSequence(" Enviado"));
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 592;BA.debugLine="chkEnviado.Text = \" Sent\"";
mostCurrent._chkenviado.setText(BA.ObjectToCharSequence(" Sent"));
 };
 //BA.debugLineNum = 594;BA.debugLine="chkValidadoExpertos.Text = \"\"";
mostCurrent._chkvalidadoexpertos.setText(BA.ObjectToCharSequence(""));
 //BA.debugLineNum = 595;BA.debugLine="chkValidadoExpertos.Color = Colors.ARGB(255,76";
mostCurrent._chkvalidadoexpertos.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (76),(int) (159),(int) (56)));
 };
 //BA.debugLineNum = 602;BA.debugLine="Dim datoprivado As String";
_datoprivado = "";
 //BA.debugLineNum = 603;BA.debugLine="datoprivado = datoMap.Get(\"privado\")";
_datoprivado = BA.ObjectToString(_datomap.Get((Object)("privado")));
 //BA.debugLineNum = 604;BA.debugLine="If datoprivado = \"no\" Or datoprivado = \"\" Or da";
if ((_datoprivado).equals("no") || (_datoprivado).equals("") || (_datoprivado).equals("null")) { 
 //BA.debugLineNum = 605;BA.debugLine="chkPublico.Checked = True";
mostCurrent._chkpublico.setChecked(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 606;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 607;BA.debugLine="chkPublico.Text = \"Dato público\"";
mostCurrent._chkpublico.setText(BA.ObjectToCharSequence("Dato público"));
 //BA.debugLineNum = 608;BA.debugLine="btnCambiarPublico.Text = \"Cambiar a dato priv";
mostCurrent._btncambiarpublico.setText(BA.ObjectToCharSequence("Cambiar a dato privado"));
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 610;BA.debugLine="chkPublico.Text = \"Public report\"";
mostCurrent._chkpublico.setText(BA.ObjectToCharSequence("Public report"));
 //BA.debugLineNum = 611;BA.debugLine="btnCambiarPublico.Text = \"Make report private";
mostCurrent._btncambiarpublico.setText(BA.ObjectToCharSequence("Make report private"));
 };
 }else {
 //BA.debugLineNum = 614;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 615;BA.debugLine="chkPublico.Text = \"Dato privado\"";
mostCurrent._chkpublico.setText(BA.ObjectToCharSequence("Dato privado"));
 //BA.debugLineNum = 616;BA.debugLine="btnCambiarPublico.Text = \"Cambiar a dato públ";
mostCurrent._btncambiarpublico.setText(BA.ObjectToCharSequence("Cambiar a dato público"));
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 618;BA.debugLine="chkPublico.Text = \"Private report\"";
mostCurrent._chkpublico.setText(BA.ObjectToCharSequence("Private report"));
 //BA.debugLineNum = 619;BA.debugLine="btnCambiarPublico.Text = \"Make report public\"";
mostCurrent._btncambiarpublico.setText(BA.ObjectToCharSequence("Make report public"));
 };
 //BA.debugLineNum = 622;BA.debugLine="chkPublico.Checked = False";
mostCurrent._chkpublico.setChecked(anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 628;BA.debugLine="foto1view.Bitmap = Null";
mostCurrent._foto1view.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null));
 //BA.debugLineNum = 629;BA.debugLine="foto2view.Bitmap = Null";
mostCurrent._foto2view.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null));
 //BA.debugLineNum = 630;BA.debugLine="foto3view.Bitmap = Null";
mostCurrent._foto3view.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null));
 //BA.debugLineNum = 631;BA.debugLine="foto4view.Bitmap = Null";
mostCurrent._foto4view.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null));
 //BA.debugLineNum = 632;BA.debugLine="foto5view.Bitmap = Null";
mostCurrent._foto5view.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null));
 //BA.debugLineNum = 633;BA.debugLine="Dim foto1path As String";
_foto1path = "";
 //BA.debugLineNum = 634;BA.debugLine="Dim foto2path As String";
_foto2path = "";
 //BA.debugLineNum = 635;BA.debugLine="Dim foto3path As String";
_foto3path = "";
 //BA.debugLineNum = 636;BA.debugLine="Dim foto4path As String";
_foto4path = "";
 //BA.debugLineNum = 637;BA.debugLine="Dim foto5path As String";
_foto5path = "";
 //BA.debugLineNum = 638;BA.debugLine="foto1path = datoMap.Get(\"foto1\") & \".jpg\"";
_foto1path = BA.ObjectToString(_datomap.Get((Object)("foto1")))+".jpg";
 //BA.debugLineNum = 639;BA.debugLine="foto2path = datoMap.Get(\"foto2\") & \".jpg\"";
_foto2path = BA.ObjectToString(_datomap.Get((Object)("foto2")))+".jpg";
 //BA.debugLineNum = 640;BA.debugLine="foto3path = datoMap.Get(\"foto2\") & \".jpg\"";
_foto3path = BA.ObjectToString(_datomap.Get((Object)("foto2")))+".jpg";
 //BA.debugLineNum = 641;BA.debugLine="foto4path = datoMap.Get(\"foto4\") & \".jpg\"";
_foto4path = BA.ObjectToString(_datomap.Get((Object)("foto4")))+".jpg";
 //BA.debugLineNum = 642;BA.debugLine="foto5path = datoMap.Get(\"foto5\") & \".jpg\"";
_foto5path = BA.ObjectToString(_datomap.Get((Object)("foto5")))+".jpg";
 //BA.debugLineNum = 643;BA.debugLine="scvFotos.Panel.Invalidate";
mostCurrent._scvfotos.getPanel().Invalidate();
 //BA.debugLineNum = 646;BA.debugLine="foto1Borrar.Color = Colors.ARGB(150,255,255,255";
mostCurrent._foto1borrar.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (150),(int) (255),(int) (255),(int) (255)));
 //BA.debugLineNum = 647;BA.debugLine="foto2Borrar.Color = Colors.ARGB(150,255,255,255";
mostCurrent._foto2borrar.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (150),(int) (255),(int) (255),(int) (255)));
 //BA.debugLineNum = 648;BA.debugLine="foto3Borrar.Color = Colors.ARGB(150,255,255,255";
mostCurrent._foto3borrar.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (150),(int) (255),(int) (255),(int) (255)));
 //BA.debugLineNum = 649;BA.debugLine="foto4Borrar.Color = Colors.ARGB(150,255,255,255";
mostCurrent._foto4borrar.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (150),(int) (255),(int) (255),(int) (255)));
 //BA.debugLineNum = 650;BA.debugLine="foto5Borrar.Color = Colors.ARGB(150,255,255,255";
mostCurrent._foto5borrar.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (150),(int) (255),(int) (255),(int) (255)));
 //BA.debugLineNum = 651;BA.debugLine="foto1Borrar.Text = \"Eliminar imagen\"";
mostCurrent._foto1borrar.setText(BA.ObjectToCharSequence("Eliminar imagen"));
 //BA.debugLineNum = 652;BA.debugLine="foto2Borrar.Text = \"Eliminar imagen\"";
mostCurrent._foto2borrar.setText(BA.ObjectToCharSequence("Eliminar imagen"));
 //BA.debugLineNum = 653;BA.debugLine="foto3Borrar.Text = \"Eliminar imagen\"";
mostCurrent._foto3borrar.setText(BA.ObjectToCharSequence("Eliminar imagen"));
 //BA.debugLineNum = 654;BA.debugLine="foto4Borrar.Text = \"Eliminar imagen\"";
mostCurrent._foto4borrar.setText(BA.ObjectToCharSequence("Eliminar imagen"));
 //BA.debugLineNum = 655;BA.debugLine="foto5Borrar.Text = \"Eliminar imagen\"";
mostCurrent._foto5borrar.setText(BA.ObjectToCharSequence("Eliminar imagen"));
 //BA.debugLineNum = 656;BA.debugLine="foto1Borrar.TextColor = Colors.Black";
mostCurrent._foto1borrar.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 657;BA.debugLine="foto2Borrar.TextColor = Colors.Black";
mostCurrent._foto2borrar.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 658;BA.debugLine="foto4Borrar.TextColor = Colors.Black";
mostCurrent._foto4borrar.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 659;BA.debugLine="foto5Borrar.TextColor = Colors.Black";
mostCurrent._foto5borrar.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 661;BA.debugLine="foto1view.Gravity=Gravity.FILL";
mostCurrent._foto1view.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.FILL);
 //BA.debugLineNum = 662;BA.debugLine="foto2view.Gravity=Gravity.FILL";
mostCurrent._foto2view.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.FILL);
 //BA.debugLineNum = 663;BA.debugLine="foto3view.Gravity=Gravity.FILL";
mostCurrent._foto3view.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.FILL);
 //BA.debugLineNum = 664;BA.debugLine="foto4view.Gravity=Gravity.FILL";
mostCurrent._foto4view.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.FILL);
 //BA.debugLineNum = 665;BA.debugLine="foto5view.Gravity=Gravity.FILL";
mostCurrent._foto5view.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.FILL);
 //BA.debugLineNum = 668;BA.debugLine="Dim cd As ColorDrawable";
_cd = new anywheresoftware.b4a.objects.drawable.ColorDrawable();
 //BA.debugLineNum = 669;BA.debugLine="cd.Initialize2(Colors.Transparent,10dip,2dip,Co";
_cd.Initialize2(anywheresoftware.b4a.keywords.Common.Colors.Transparent,anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (2)),anywheresoftware.b4a.keywords.Common.Colors.LightGray);
 //BA.debugLineNum = 673;BA.debugLine="If File.Exists(rp.GetSafeDirDefaultExternal(\"Pr";
if (anywheresoftware.b4a.keywords.Common.File.Exists(mostCurrent._rp.GetSafeDirDefaultExternal("PreserVamos"),_foto1path)) { 
 //BA.debugLineNum = 674;BA.debugLine="foto1view.Bitmap= LoadBitmapSample( rp.GetSafe";
mostCurrent._foto1view.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(mostCurrent._rp.GetSafeDirDefaultExternal("PreserVamos"),_foto1path,anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 }else {
 //BA.debugLineNum = 676;BA.debugLine="foto1view.Background=cd";
mostCurrent._foto1view.setBackground((android.graphics.drawable.Drawable)(_cd.getObject()));
 //BA.debugLineNum = 677;BA.debugLine="Dim cdplus1 As Label";
_cdplus1 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 678;BA.debugLine="cdplus1.Initialize(\"\")";
_cdplus1.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 679;BA.debugLine="cdplus1.Text = \"+\"";
_cdplus1.setText(BA.ObjectToCharSequence("+"));
 //BA.debugLineNum = 680;BA.debugLine="cdplus1.TextSize = 48";
_cdplus1.setTextSize((float) (48));
 //BA.debugLineNum = 681;BA.debugLine="cdplus1.TextColor = Colors.LightGray";
_cdplus1.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.LightGray);
 //BA.debugLineNum = 682;BA.debugLine="cdplus1.Gravity = Bit.Or(Gravity.CENTER_VERTIC";
_cdplus1.setGravity(anywheresoftware.b4a.keywords.Common.Bit.Or(anywheresoftware.b4a.keywords.Common.Gravity.CENTER_VERTICAL,anywheresoftware.b4a.keywords.Common.Gravity.CENTER_HORIZONTAL));
 //BA.debugLineNum = 683;BA.debugLine="scvFotos.Panel.AddView(cdplus1, foto1view.Left";
mostCurrent._scvfotos.getPanel().AddView((android.view.View)(_cdplus1.getObject()),mostCurrent._foto1view.getLeft(),mostCurrent._foto1view.getTop(),mostCurrent._foto1view.getWidth(),mostCurrent._foto1view.getHeight());
 };
 //BA.debugLineNum = 685;BA.debugLine="If File.Exists( rp.GetSafeDirDefaultExternal(\"P";
if (anywheresoftware.b4a.keywords.Common.File.Exists(mostCurrent._rp.GetSafeDirDefaultExternal("PreserVamos"),_foto2path)) { 
 //BA.debugLineNum = 686;BA.debugLine="foto2view.Bitmap= LoadBitmapSample( rp.GetSafe";
mostCurrent._foto2view.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(mostCurrent._rp.GetSafeDirDefaultExternal("PreserVamos"),_foto2path,anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (1005),mostCurrent.activityBA)).getObject()));
 }else {
 //BA.debugLineNum = 688;BA.debugLine="foto2view.Background=cd";
mostCurrent._foto2view.setBackground((android.graphics.drawable.Drawable)(_cd.getObject()));
 //BA.debugLineNum = 689;BA.debugLine="Dim cdplus2 As Label";
_cdplus2 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 690;BA.debugLine="cdplus2.Initialize(\"\")";
_cdplus2.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 691;BA.debugLine="cdplus2.Text = \"+\"";
_cdplus2.setText(BA.ObjectToCharSequence("+"));
 //BA.debugLineNum = 692;BA.debugLine="cdplus2.TextSize = 48";
_cdplus2.setTextSize((float) (48));
 //BA.debugLineNum = 693;BA.debugLine="cdplus2.TextColor = Colors.LightGray";
_cdplus2.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.LightGray);
 //BA.debugLineNum = 694;BA.debugLine="cdplus2.Gravity = Bit.Or(Gravity.CENTER_VERTIC";
_cdplus2.setGravity(anywheresoftware.b4a.keywords.Common.Bit.Or(anywheresoftware.b4a.keywords.Common.Gravity.CENTER_VERTICAL,anywheresoftware.b4a.keywords.Common.Gravity.CENTER_HORIZONTAL));
 //BA.debugLineNum = 695;BA.debugLine="scvFotos.Panel.AddView(cdplus2, foto2view.Left";
mostCurrent._scvfotos.getPanel().AddView((android.view.View)(_cdplus2.getObject()),mostCurrent._foto2view.getLeft(),mostCurrent._foto2view.getTop(),mostCurrent._foto2view.getWidth(),mostCurrent._foto2view.getHeight());
 };
 //BA.debugLineNum = 697;BA.debugLine="If File.Exists( rp.GetSafeDirDefaultExternal(\"P";
if (anywheresoftware.b4a.keywords.Common.File.Exists(mostCurrent._rp.GetSafeDirDefaultExternal("PreserVamos"),_foto3path)) { 
 //BA.debugLineNum = 698;BA.debugLine="foto3view.Bitmap= LoadBitmapSample( rp.GetSafe";
mostCurrent._foto3view.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(mostCurrent._rp.GetSafeDirDefaultExternal("PreserVamos"),_foto3path,anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (1005),mostCurrent.activityBA)).getObject()));
 }else {
 //BA.debugLineNum = 700;BA.debugLine="foto3view.Background=cd";
mostCurrent._foto3view.setBackground((android.graphics.drawable.Drawable)(_cd.getObject()));
 //BA.debugLineNum = 701;BA.debugLine="Dim cdplus2 As Label";
_cdplus2 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 702;BA.debugLine="cdplus2.Initialize(\"\")";
_cdplus2.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 703;BA.debugLine="cdplus2.Text = \"+\"";
_cdplus2.setText(BA.ObjectToCharSequence("+"));
 //BA.debugLineNum = 704;BA.debugLine="cdplus2.TextSize = 48";
_cdplus2.setTextSize((float) (48));
 //BA.debugLineNum = 705;BA.debugLine="cdplus2.TextColor = Colors.LightGray";
_cdplus2.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.LightGray);
 //BA.debugLineNum = 706;BA.debugLine="cdplus2.Gravity = Bit.Or(Gravity.CENTER_VERTIC";
_cdplus2.setGravity(anywheresoftware.b4a.keywords.Common.Bit.Or(anywheresoftware.b4a.keywords.Common.Gravity.CENTER_VERTICAL,anywheresoftware.b4a.keywords.Common.Gravity.CENTER_HORIZONTAL));
 //BA.debugLineNum = 707;BA.debugLine="scvFotos.Panel.AddView(cdplus2, foto3view.Left";
mostCurrent._scvfotos.getPanel().AddView((android.view.View)(_cdplus2.getObject()),mostCurrent._foto3view.getLeft(),mostCurrent._foto3view.getTop(),mostCurrent._foto3view.getWidth(),mostCurrent._foto3view.getHeight());
 };
 //BA.debugLineNum = 709;BA.debugLine="If File.Exists(rp.GetSafeDirDefaultExternal(\"Pr";
if (anywheresoftware.b4a.keywords.Common.File.Exists(mostCurrent._rp.GetSafeDirDefaultExternal("PreserVamos"),_foto4path)) { 
 //BA.debugLineNum = 711;BA.debugLine="foto4view.Bitmap= LoadBitmapSample( rp.GetSafe";
mostCurrent._foto4view.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(mostCurrent._rp.GetSafeDirDefaultExternal("PreserVamos"),_foto4path,anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (1005),mostCurrent.activityBA)).getObject()));
 }else {
 //BA.debugLineNum = 713;BA.debugLine="foto4view.Background=cd";
mostCurrent._foto4view.setBackground((android.graphics.drawable.Drawable)(_cd.getObject()));
 //BA.debugLineNum = 714;BA.debugLine="Dim cdplus2 As Label";
_cdplus2 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 715;BA.debugLine="cdplus2.Initialize(\"\")";
_cdplus2.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 716;BA.debugLine="cdplus2.Text = \"+\"";
_cdplus2.setText(BA.ObjectToCharSequence("+"));
 //BA.debugLineNum = 717;BA.debugLine="cdplus2.TextSize = 48";
_cdplus2.setTextSize((float) (48));
 //BA.debugLineNum = 718;BA.debugLine="cdplus2.TextColor = Colors.LightGray";
_cdplus2.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.LightGray);
 //BA.debugLineNum = 719;BA.debugLine="cdplus2.Gravity = Bit.Or(Gravity.CENTER_VERTIC";
_cdplus2.setGravity(anywheresoftware.b4a.keywords.Common.Bit.Or(anywheresoftware.b4a.keywords.Common.Gravity.CENTER_VERTICAL,anywheresoftware.b4a.keywords.Common.Gravity.CENTER_HORIZONTAL));
 //BA.debugLineNum = 720;BA.debugLine="scvFotos.Panel.AddView(cdplus2, foto4view.Left";
mostCurrent._scvfotos.getPanel().AddView((android.view.View)(_cdplus2.getObject()),mostCurrent._foto4view.getLeft(),mostCurrent._foto4view.getTop(),mostCurrent._foto4view.getWidth(),mostCurrent._foto4view.getHeight());
 };
 //BA.debugLineNum = 722;BA.debugLine="If File.Exists( rp.GetSafeDirDefaultExternal(\"P";
if (anywheresoftware.b4a.keywords.Common.File.Exists(mostCurrent._rp.GetSafeDirDefaultExternal("PreserVamos"),_foto5path)) { 
 //BA.debugLineNum = 723;BA.debugLine="foto5view.Bitmap= LoadBitmapSample( rp.GetSafe";
mostCurrent._foto5view.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(mostCurrent._rp.GetSafeDirDefaultExternal("PreserVamos"),_foto5path,anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (1005),mostCurrent.activityBA)).getObject()));
 }else {
 //BA.debugLineNum = 725;BA.debugLine="foto5view.Background=cd";
mostCurrent._foto5view.setBackground((android.graphics.drawable.Drawable)(_cd.getObject()));
 //BA.debugLineNum = 726;BA.debugLine="Dim cdplus2 As Label";
_cdplus2 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 727;BA.debugLine="cdplus2.Initialize(\"\")";
_cdplus2.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 728;BA.debugLine="cdplus2.Text = \"+\"";
_cdplus2.setText(BA.ObjectToCharSequence("+"));
 //BA.debugLineNum = 729;BA.debugLine="cdplus2.TextSize = 48";
_cdplus2.setTextSize((float) (48));
 //BA.debugLineNum = 730;BA.debugLine="cdplus2.TextColor = Colors.LightGray";
_cdplus2.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.LightGray);
 //BA.debugLineNum = 731;BA.debugLine="cdplus2.Gravity = Bit.Or(Gravity.CENTER_VERTIC";
_cdplus2.setGravity(anywheresoftware.b4a.keywords.Common.Bit.Or(anywheresoftware.b4a.keywords.Common.Gravity.CENTER_VERTICAL,anywheresoftware.b4a.keywords.Common.Gravity.CENTER_HORIZONTAL));
 //BA.debugLineNum = 732;BA.debugLine="scvFotos.Panel.AddView(cdplus2, foto5view.Left";
mostCurrent._scvfotos.getPanel().AddView((android.view.View)(_cdplus2.getObject()),mostCurrent._foto5view.getLeft(),mostCurrent._foto5view.getTop(),mostCurrent._foto5view.getWidth(),mostCurrent._foto5view.getHeight());
 };
 };
 }else if((_tiporegistro).equals("residuos")) { 
 //BA.debugLineNum = 737;BA.debugLine="currentDatoTipo = \"residuos\"";
mostCurrent._currentdatotipo = "residuos";
 //BA.debugLineNum = 738;BA.debugLine="Dim datoMap_residuos As Map";
_datomap_residuos = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 739;BA.debugLine="datoMap_residuos.Initialize";
_datomap_residuos.Initialize();
 //BA.debugLineNum = 740;BA.debugLine="If serverId = False Then";
if (_serverid==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 741;BA.debugLine="datoMap_residuos = DBUtils.ExecuteMap(Starter.m";
_datomap_residuos = mostCurrent._dbutils._executemap /*anywheresoftware.b4a.objects.collections.Map*/ (mostCurrent.activityBA,mostCurrent._starter._modulo_residuos_sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"SELECT * FROM evals_residuos WHERE Id=?",new String[]{_datoid});
 }else {
 //BA.debugLineNum = 743;BA.debugLine="datoMap_residuos = DBUtils.ExecuteMap(Starter.m";
_datomap_residuos = mostCurrent._dbutils._executemap /*anywheresoftware.b4a.objects.collections.Map*/ (mostCurrent.activityBA,mostCurrent._starter._modulo_residuos_sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"SELECT * FROM evals_residuos WHERE serverid=?",new String[]{_datoid});
 //BA.debugLineNum = 744;BA.debugLine="hayAnteriores_residuos = True";
_hayanteriores_residuos = anywheresoftware.b4a.keywords.Common.True;
 };
 //BA.debugLineNum = 748;BA.debugLine="Dim datoenviado As String";
_datoenviado = "";
 //BA.debugLineNum = 749;BA.debugLine="datoenviado = datoMap_residuos.Get(\"evalsent\")";
_datoenviado = BA.ObjectToString(_datomap_residuos.Get((Object)("evalsent")));
 //BA.debugLineNum = 750;BA.debugLine="If datoenviado = \"si\" Then";
if ((_datoenviado).equals("si")) { 
 //BA.debugLineNum = 757;BA.debugLine="btnEnviar.Visible = False";
mostCurrent._btnenviar.setVisible(anywheresoftware.b4a.keywords.Common.False);
 }else {
 //BA.debugLineNum = 765;BA.debugLine="btnEnviar.Visible = True";
mostCurrent._btnenviar.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 767;BA.debugLine="chkEnviado.Checked = False";
mostCurrent._chkenviado.setChecked(anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 772;BA.debugLine="If datoMap_residuos = Null Or datoMap_residuos.I";
if (_datomap_residuos== null || _datomap_residuos.IsInitialized()==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 773;BA.debugLine="tabStripDatosAnteriores.ScrollTo(0,True)";
mostCurrent._tabstripdatosanteriores.ScrollTo((int) (0),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 774;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 775;BA.debugLine="ToastMessageShow(\"Dato no encontrado...\", Fals";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Dato no encontrado..."),anywheresoftware.b4a.keywords.Common.False);
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 777;BA.debugLine="ToastMessageShow(\"Report not found...\", False)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Report not found..."),anywheresoftware.b4a.keywords.Common.False);
 };
 }else {
 //BA.debugLineNum = 781;BA.debugLine="IdProyectoEnviar = datoID";
mostCurrent._idproyectoenviar = _datoid;
 //BA.debugLineNum = 783;BA.debugLine="lblNombreEnvio.Text = datoMap_residuos.Get(\"geo";
mostCurrent._lblnombreenvio.setText(BA.ObjectToCharSequence(_datomap_residuos.Get((Object)("georeferenceddate"))));
 //BA.debugLineNum = 784;BA.debugLine="lblFecha.Text = \"Análisis de residuos: \" & dato";
mostCurrent._lblfecha.setText(BA.ObjectToCharSequence("Análisis de residuos: "+BA.ObjectToString(_datomap_residuos.Get((Object)("georeferenceddate")))));
 //BA.debugLineNum = 785;BA.debugLine="lblCalidad.Visible= False";
mostCurrent._lblcalidad.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 786;BA.debugLine="Label9.Visible = False";
mostCurrent._label9.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 787;BA.debugLine="lblNotas.Text = datoMap_residuos.Get(\"notas\")";
mostCurrent._lblnotas.setText(BA.ObjectToCharSequence(_datomap_residuos.Get((Object)("notas"))));
 //BA.debugLineNum = 788;BA.debugLine="lblLat.Text = datoMap_residuos.Get(\"decimallati";
mostCurrent._lbllat.setText(BA.ObjectToCharSequence(_datomap_residuos.Get((Object)("decimallatitude"))));
 //BA.debugLineNum = 789;BA.debugLine="lblLng.Text = datoMap_residuos.Get(\"decimallong";
mostCurrent._lbllng.setText(BA.ObjectToCharSequence(_datomap_residuos.Get((Object)("decimallongitude"))));
 //BA.debugLineNum = 790;BA.debugLine="lblTipo.Text = datoMap_residuos.Get(\"tiporio\")";
mostCurrent._lbltipo.setText(BA.ObjectToCharSequence(_datomap_residuos.Get((Object)("tiporio"))));
 //BA.debugLineNum = 791;BA.debugLine="If lblTipo.Text = \"llanura\" Then";
if ((mostCurrent._lbltipo.getText()).equals("llanura")) { 
 //BA.debugLineNum = 792;BA.debugLine="lblTipo.Text = \"Río o arroyo\"";
mostCurrent._lbltipo.setText(BA.ObjectToCharSequence("Río o arroyo"));
 }else if((mostCurrent._lbltipo.getText()).equals("laguna")) { 
 //BA.debugLineNum = 794;BA.debugLine="lblTipo.Text = \"Laguna\"";
mostCurrent._lbltipo.setText(BA.ObjectToCharSequence("Laguna"));
 };
 //BA.debugLineNum = 796;BA.debugLine="lblNotas.Visible = True";
mostCurrent._lblnotas.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 797;BA.debugLine="Label10.Visible = True";
mostCurrent._label10.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 798;BA.debugLine="lblFecha.TextColor = Colors.ARGB(255,76,159,56)";
mostCurrent._lblfecha.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (76),(int) (159),(int) (56)));
 //BA.debugLineNum = 799;BA.debugLine="lblCalidad.TextColor = Colors.ARGB(255,76,159,5";
mostCurrent._lblcalidad.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (76),(int) (159),(int) (56)));
 //BA.debugLineNum = 800;BA.debugLine="lblLat.TextColor = Colors.ARGB(255,76,159,56)";
mostCurrent._lbllat.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (76),(int) (159),(int) (56)));
 //BA.debugLineNum = 801;BA.debugLine="lblLng.TextColor = Colors.ARGB(255,76,159,56)";
mostCurrent._lbllng.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (76),(int) (159),(int) (56)));
 //BA.debugLineNum = 802;BA.debugLine="lblTipo.TextColor = Colors.ARGB(255,76,159,56)";
mostCurrent._lbltipo.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (76),(int) (159),(int) (56)));
 //BA.debugLineNum = 804;BA.debugLine="If lblNombreEnvio.Text = \"null\" Then";
if ((mostCurrent._lblnombreenvio.getText()).equals("null")) { 
 //BA.debugLineNum = 805;BA.debugLine="lblNombreEnvio.Text = \"Sin nombre\"";
mostCurrent._lblnombreenvio.setText(BA.ObjectToCharSequence("Sin nombre"));
 };
 //BA.debugLineNum = 807;BA.debugLine="If lblFecha.Text = \"null\" Then";
if ((mostCurrent._lblfecha.getText()).equals("null")) { 
 //BA.debugLineNum = 808;BA.debugLine="lblFecha.Text = \"Sin fecha\"";
mostCurrent._lblfecha.setText(BA.ObjectToCharSequence("Sin fecha"));
 //BA.debugLineNum = 809;BA.debugLine="lblFecha.TextColor = Colors.red";
mostCurrent._lblfecha.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Red);
 };
 //BA.debugLineNum = 811;BA.debugLine="If lblCalidad.Text = \"null\" Then";
if ((mostCurrent._lblcalidad.getText()).equals("null")) { 
 //BA.debugLineNum = 812;BA.debugLine="lblCalidad.Text = \"Calidad no establecida\"";
mostCurrent._lblcalidad.setText(BA.ObjectToCharSequence("Calidad no establecida"));
 //BA.debugLineNum = 813;BA.debugLine="lblCalidad.TextColor = Colors.red";
mostCurrent._lblcalidad.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Red);
 };
 //BA.debugLineNum = 815;BA.debugLine="If lblNotas.Text = \"null\" Then";
if ((mostCurrent._lblnotas.getText()).equals("null")) { 
 //BA.debugLineNum = 816;BA.debugLine="lblNotas.Text = \"Sin notas\"";
mostCurrent._lblnotas.setText(BA.ObjectToCharSequence("Sin notas"));
 };
 //BA.debugLineNum = 818;BA.debugLine="If lblLat.Text = \"null\" Then";
if ((mostCurrent._lbllat.getText()).equals("null")) { 
 //BA.debugLineNum = 819;BA.debugLine="lblLat.Text = \"Sin latitud\"";
mostCurrent._lbllat.setText(BA.ObjectToCharSequence("Sin latitud"));
 //BA.debugLineNum = 820;BA.debugLine="lblLat.TextColor = Colors.red";
mostCurrent._lbllat.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Red);
 };
 //BA.debugLineNum = 822;BA.debugLine="If lblLng.Text = \"null\" Then";
if ((mostCurrent._lbllng.getText()).equals("null")) { 
 //BA.debugLineNum = 823;BA.debugLine="lblLng.Text = \"Sin longitud\"";
mostCurrent._lbllng.setText(BA.ObjectToCharSequence("Sin longitud"));
 //BA.debugLineNum = 824;BA.debugLine="lblLng.TextColor = Colors.red";
mostCurrent._lbllng.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Red);
 };
 //BA.debugLineNum = 826;BA.debugLine="If lblTipo.Text = \"null\" Then";
if ((mostCurrent._lbltipo.getText()).equals("null")) { 
 //BA.debugLineNum = 827;BA.debugLine="lblTipo.Text = \"Tipo no establecido\"";
mostCurrent._lbltipo.setText(BA.ObjectToCharSequence("Tipo no establecido"));
 //BA.debugLineNum = 828;BA.debugLine="lblTipo.TextColor = Colors.red";
mostCurrent._lbltipo.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Red);
 };
 //BA.debugLineNum = 831;BA.debugLine="Dim datorevisado As String";
_datorevisado = "";
 //BA.debugLineNum = 832;BA.debugLine="datorevisado = datoMap_residuos.Get(\"estadovali";
_datorevisado = BA.ObjectToString(_datomap_residuos.Get((Object)("estadovalidacion")));
 //BA.debugLineNum = 833;BA.debugLine="If datorevisado = \"validado\" Then";
if ((_datorevisado).equals("validado")) { 
 //BA.debugLineNum = 834;BA.debugLine="chkEnviado.Visible = False";
mostCurrent._chkenviado.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 836;BA.debugLine="btnEnviar.Visible = False";
mostCurrent._btnenviar.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 837;BA.debugLine="chkEnviado.Checked = True";
mostCurrent._chkenviado.setChecked(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 838;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 839;BA.debugLine="chkEnviado.Text = \" Enviado\"";
mostCurrent._chkenviado.setText(BA.ObjectToCharSequence(" Enviado"));
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 841;BA.debugLine="chkEnviado.Text = \" Sent\"";
mostCurrent._chkenviado.setText(BA.ObjectToCharSequence(" Sent"));
 };
 //BA.debugLineNum = 843;BA.debugLine="chkValidadoExpertos.Text = \"\"";
mostCurrent._chkvalidadoexpertos.setText(BA.ObjectToCharSequence(""));
 //BA.debugLineNum = 844;BA.debugLine="chkValidadoExpertos.Color = Colors.ARGB(255,76";
mostCurrent._chkvalidadoexpertos.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (76),(int) (159),(int) (56)));
 };
 //BA.debugLineNum = 849;BA.debugLine="Dim datoprivado As String";
_datoprivado = "";
 //BA.debugLineNum = 850;BA.debugLine="datoprivado = datoMap_residuos.Get(\"privado\")";
_datoprivado = BA.ObjectToString(_datomap_residuos.Get((Object)("privado")));
 //BA.debugLineNum = 851;BA.debugLine="If datoprivado = \"no\" Or datoprivado = \"\" Or da";
if ((_datoprivado).equals("no") || (_datoprivado).equals("") || (_datoprivado).equals("null")) { 
 //BA.debugLineNum = 852;BA.debugLine="chkPublico.Checked = True";
mostCurrent._chkpublico.setChecked(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 853;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 854;BA.debugLine="chkPublico.Text = \"Dato público\"";
mostCurrent._chkpublico.setText(BA.ObjectToCharSequence("Dato público"));
 //BA.debugLineNum = 855;BA.debugLine="btnCambiarPublico.Text = \"Cambiar a dato priv";
mostCurrent._btncambiarpublico.setText(BA.ObjectToCharSequence("Cambiar a dato privado"));
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 857;BA.debugLine="chkPublico.Text = \"Public report\"";
mostCurrent._chkpublico.setText(BA.ObjectToCharSequence("Public report"));
 //BA.debugLineNum = 858;BA.debugLine="btnCambiarPublico.Text = \"Make report private";
mostCurrent._btncambiarpublico.setText(BA.ObjectToCharSequence("Make report private"));
 };
 }else {
 //BA.debugLineNum = 861;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 862;BA.debugLine="chkPublico.Text = \"Dato privado\"";
mostCurrent._chkpublico.setText(BA.ObjectToCharSequence("Dato privado"));
 //BA.debugLineNum = 863;BA.debugLine="btnCambiarPublico.Text = \"Cambiar a dato públ";
mostCurrent._btncambiarpublico.setText(BA.ObjectToCharSequence("Cambiar a dato público"));
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 865;BA.debugLine="chkPublico.Text = \"Private report\"";
mostCurrent._chkpublico.setText(BA.ObjectToCharSequence("Private report"));
 //BA.debugLineNum = 866;BA.debugLine="btnCambiarPublico.Text = \"Make report public\"";
mostCurrent._btncambiarpublico.setText(BA.ObjectToCharSequence("Make report public"));
 };
 //BA.debugLineNum = 869;BA.debugLine="chkPublico.Checked = False";
mostCurrent._chkpublico.setChecked(anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 873;BA.debugLine="foto1view.Bitmap = Null";
mostCurrent._foto1view.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null));
 //BA.debugLineNum = 874;BA.debugLine="Dim foto1path As String";
_foto1path = "";
 //BA.debugLineNum = 875;BA.debugLine="foto1path = datoMap_residuos.Get(\"foto1\") & \".j";
_foto1path = BA.ObjectToString(_datomap_residuos.Get((Object)("foto1")))+".jpg";
 //BA.debugLineNum = 876;BA.debugLine="scvFotos.Panel.Invalidate";
mostCurrent._scvfotos.getPanel().Invalidate();
 //BA.debugLineNum = 879;BA.debugLine="foto1Borrar.Color = Colors.ARGB(150,255,255,255";
mostCurrent._foto1borrar.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (150),(int) (255),(int) (255),(int) (255)));
 //BA.debugLineNum = 880;BA.debugLine="foto1Borrar.Text = \"Eliminar imagen\"";
mostCurrent._foto1borrar.setText(BA.ObjectToCharSequence("Eliminar imagen"));
 //BA.debugLineNum = 881;BA.debugLine="foto1Borrar.TextColor = Colors.Black";
mostCurrent._foto1borrar.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 882;BA.debugLine="foto1view.Gravity=Gravity.FILL";
mostCurrent._foto1view.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.FILL);
 //BA.debugLineNum = 885;BA.debugLine="Dim cd As ColorDrawable";
_cd = new anywheresoftware.b4a.objects.drawable.ColorDrawable();
 //BA.debugLineNum = 886;BA.debugLine="cd.Initialize2(Colors.Transparent,10dip,2dip,Co";
_cd.Initialize2(anywheresoftware.b4a.keywords.Common.Colors.Transparent,anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (2)),anywheresoftware.b4a.keywords.Common.Colors.LightGray);
 //BA.debugLineNum = 889;BA.debugLine="If File.Exists(rp.GetSafeDirDefaultExternal(\"Pr";
if (anywheresoftware.b4a.keywords.Common.File.Exists(mostCurrent._rp.GetSafeDirDefaultExternal("PreserVamos"),_foto1path)) { 
 //BA.debugLineNum = 890;BA.debugLine="foto1view.Bitmap= LoadBitmapSample( rp.GetSafe";
mostCurrent._foto1view.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(mostCurrent._rp.GetSafeDirDefaultExternal("PreserVamos"),_foto1path,anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 }else {
 //BA.debugLineNum = 892;BA.debugLine="foto1view.Background=cd";
mostCurrent._foto1view.setBackground((android.graphics.drawable.Drawable)(_cd.getObject()));
 //BA.debugLineNum = 893;BA.debugLine="Dim cdplus1 As Label";
_cdplus1 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 894;BA.debugLine="cdplus1.Initialize(\"\")";
_cdplus1.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 895;BA.debugLine="cdplus1.Text = \"+\"";
_cdplus1.setText(BA.ObjectToCharSequence("+"));
 //BA.debugLineNum = 896;BA.debugLine="cdplus1.TextSize = 48";
_cdplus1.setTextSize((float) (48));
 //BA.debugLineNum = 897;BA.debugLine="cdplus1.TextColor = Colors.LightGray";
_cdplus1.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.LightGray);
 //BA.debugLineNum = 898;BA.debugLine="cdplus1.Gravity = Bit.Or(Gravity.CENTER_VERTIC";
_cdplus1.setGravity(anywheresoftware.b4a.keywords.Common.Bit.Or(anywheresoftware.b4a.keywords.Common.Gravity.CENTER_VERTICAL,anywheresoftware.b4a.keywords.Common.Gravity.CENTER_HORIZONTAL));
 //BA.debugLineNum = 899;BA.debugLine="scvFotos.Panel.AddView(cdplus1, foto1view.Left";
mostCurrent._scvfotos.getPanel().AddView((android.view.View)(_cdplus1.getObject()),mostCurrent._foto1view.getLeft(),mostCurrent._foto1view.getTop(),mostCurrent._foto1view.getWidth(),mostCurrent._foto1view.getHeight());
 };
 };
 }else if((_tiporegistro).equals("hidro")) { 
 //BA.debugLineNum = 905;BA.debugLine="currentDatoTipo = \"hidro\"";
mostCurrent._currentdatotipo = "hidro";
 //BA.debugLineNum = 906;BA.debugLine="Dim datoMap_hidro As Map";
_datomap_hidro = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 907;BA.debugLine="datoMap_hidro.Initialize";
_datomap_hidro.Initialize();
 //BA.debugLineNum = 908;BA.debugLine="If serverId = False Then";
if (_serverid==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 909;BA.debugLine="datoMap_hidro = DBUtils.ExecuteMap(Starter.modu";
_datomap_hidro = mostCurrent._dbutils._executemap /*anywheresoftware.b4a.objects.collections.Map*/ (mostCurrent.activityBA,mostCurrent._starter._modulo_hidro_sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"SELECT * FROM evals_hidro WHERE Id=?",new String[]{_datoid});
 }else {
 //BA.debugLineNum = 911;BA.debugLine="datoMap_hidro = DBUtils.ExecuteMap(Starter.modu";
_datomap_hidro = mostCurrent._dbutils._executemap /*anywheresoftware.b4a.objects.collections.Map*/ (mostCurrent.activityBA,mostCurrent._starter._modulo_hidro_sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"SELECT * FROM evals_residuos WHERE serverid=?",new String[]{_datoid});
 //BA.debugLineNum = 912;BA.debugLine="hayAnteriores_hidro = True";
_hayanteriores_hidro = anywheresoftware.b4a.keywords.Common.True;
 };
 //BA.debugLineNum = 916;BA.debugLine="Dim datoenviado As String";
_datoenviado = "";
 //BA.debugLineNum = 917;BA.debugLine="datoenviado = datoMap_hidro.Get(\"evalsent\")";
_datoenviado = BA.ObjectToString(_datomap_hidro.Get((Object)("evalsent")));
 //BA.debugLineNum = 918;BA.debugLine="If datoenviado = \"si\" Then";
if ((_datoenviado).equals("si")) { 
 //BA.debugLineNum = 919;BA.debugLine="chkEnviado.Visible = False";
mostCurrent._chkenviado.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 920;BA.debugLine="chkEnviado.Checked = True";
mostCurrent._chkenviado.setChecked(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 921;BA.debugLine="chkEnviadoBar.Text = \"\"";
mostCurrent._chkenviadobar.setText(BA.ObjectToCharSequence(""));
 //BA.debugLineNum = 922;BA.debugLine="chkEnviadoBar.TextColor = Colors.ARGB(255,76,15";
mostCurrent._chkenviadobar.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (76),(int) (159),(int) (56)));
 //BA.debugLineNum = 923;BA.debugLine="chkOnline.Text = \"\"";
mostCurrent._chkonline.setText(BA.ObjectToCharSequence(""));
 //BA.debugLineNum = 924;BA.debugLine="chkOnline.TextColor = Colors.ARGB(255,76,159,56";
mostCurrent._chkonline.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (76),(int) (159),(int) (56)));
 //BA.debugLineNum = 925;BA.debugLine="btnEnviar.Visible = False";
mostCurrent._btnenviar.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 927;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 928;BA.debugLine="chkEnviado.Text = \" Enviado\"";
mostCurrent._chkenviado.setText(BA.ObjectToCharSequence(" Enviado"));
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 930;BA.debugLine="chkEnviado.Text = \" Sent\"";
mostCurrent._chkenviado.setText(BA.ObjectToCharSequence(" Sent"));
 };
 }else {
 //BA.debugLineNum = 933;BA.debugLine="btnEnviar.Visible = True";
mostCurrent._btnenviar.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 934;BA.debugLine="chkEnviado.Visible = False";
mostCurrent._chkenviado.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 935;BA.debugLine="chkEnviado.Checked = False";
mostCurrent._chkenviado.setChecked(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 936;BA.debugLine="chkEnviadoBar.Text = \"\"";
mostCurrent._chkenviadobar.setText(BA.ObjectToCharSequence(""));
 //BA.debugLineNum = 937;BA.debugLine="chkEnviadoBar.TextColor = Colors.ARGB(255,68,68";
mostCurrent._chkenviadobar.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (68),(int) (68),(int) (68)));
 };
 //BA.debugLineNum = 940;BA.debugLine="If datoMap_hidro = Null Or datoMap_hidro.IsIniti";
if (_datomap_hidro== null || _datomap_hidro.IsInitialized()==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 941;BA.debugLine="tabStripDatosAnteriores.ScrollTo(0,True)";
mostCurrent._tabstripdatosanteriores.ScrollTo((int) (0),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 942;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 943;BA.debugLine="ToastMessageShow(\"Dato no encontrado...\", Fals";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Dato no encontrado..."),anywheresoftware.b4a.keywords.Common.False);
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 945;BA.debugLine="ToastMessageShow(\"Report not found...\", False)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Report not found..."),anywheresoftware.b4a.keywords.Common.False);
 };
 }else {
 //BA.debugLineNum = 949;BA.debugLine="IdProyectoEnviar = datoID";
mostCurrent._idproyectoenviar = _datoid;
 //BA.debugLineNum = 951;BA.debugLine="lblNombreEnvio.Text = datoMap_hidro.Get(\"georef";
mostCurrent._lblnombreenvio.setText(BA.ObjectToCharSequence(_datomap_hidro.Get((Object)("georeferenceddate"))));
 //BA.debugLineNum = 952;BA.debugLine="lblFecha.Text = \"Análisis hidrológico: \" & dato";
mostCurrent._lblfecha.setText(BA.ObjectToCharSequence("Análisis hidrológico: "+BA.ObjectToString(_datomap_hidro.Get((Object)("georeferenceddate")))));
 //BA.debugLineNum = 953;BA.debugLine="lblCalidad.Visible= False";
mostCurrent._lblcalidad.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 954;BA.debugLine="Label9.Visible = False";
mostCurrent._label9.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 955;BA.debugLine="lblNotas.Text = datoMap_hidro.Get(\"notas\")";
mostCurrent._lblnotas.setText(BA.ObjectToCharSequence(_datomap_hidro.Get((Object)("notas"))));
 //BA.debugLineNum = 956;BA.debugLine="lblLat.Text = datoMap_hidro.Get(\"decimallatitud";
mostCurrent._lbllat.setText(BA.ObjectToCharSequence(_datomap_hidro.Get((Object)("decimallatitude"))));
 //BA.debugLineNum = 957;BA.debugLine="lblLng.Text = datoMap_hidro.Get(\"decimallongitu";
mostCurrent._lbllng.setText(BA.ObjectToCharSequence(_datomap_hidro.Get((Object)("decimallongitude"))));
 //BA.debugLineNum = 958;BA.debugLine="lblTipo.Text = datoMap_hidro.Get(\"tiporio\")";
mostCurrent._lbltipo.setText(BA.ObjectToCharSequence(_datomap_hidro.Get((Object)("tiporio"))));
 //BA.debugLineNum = 959;BA.debugLine="If lblTipo.Text = \"llanura\" Then";
if ((mostCurrent._lbltipo.getText()).equals("llanura")) { 
 //BA.debugLineNum = 960;BA.debugLine="lblTipo.Text = \"Río o arroyo\"";
mostCurrent._lbltipo.setText(BA.ObjectToCharSequence("Río o arroyo"));
 }else if((mostCurrent._lbltipo.getText()).equals("laguna")) { 
 //BA.debugLineNum = 962;BA.debugLine="lblTipo.Text = \"Laguna\"";
mostCurrent._lbltipo.setText(BA.ObjectToCharSequence("Laguna"));
 };
 //BA.debugLineNum = 964;BA.debugLine="lblNotas.Visible = True";
mostCurrent._lblnotas.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 965;BA.debugLine="Label10.Visible = True";
mostCurrent._label10.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 966;BA.debugLine="lblFecha.TextColor = Colors.ARGB(255,76,159,56)";
mostCurrent._lblfecha.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (76),(int) (159),(int) (56)));
 //BA.debugLineNum = 967;BA.debugLine="lblCalidad.TextColor = Colors.ARGB(255,76,159,5";
mostCurrent._lblcalidad.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (76),(int) (159),(int) (56)));
 //BA.debugLineNum = 968;BA.debugLine="lblLat.TextColor = Colors.ARGB(255,76,159,56)";
mostCurrent._lbllat.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (76),(int) (159),(int) (56)));
 //BA.debugLineNum = 969;BA.debugLine="lblLng.TextColor = Colors.ARGB(255,76,159,56)";
mostCurrent._lbllng.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (76),(int) (159),(int) (56)));
 //BA.debugLineNum = 970;BA.debugLine="lblTipo.TextColor = Colors.ARGB(255,76,159,56)";
mostCurrent._lbltipo.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (76),(int) (159),(int) (56)));
 //BA.debugLineNum = 972;BA.debugLine="If lblNombreEnvio.Text = \"null\" Then";
if ((mostCurrent._lblnombreenvio.getText()).equals("null")) { 
 //BA.debugLineNum = 973;BA.debugLine="lblNombreEnvio.Text = \"Sin nombre\"";
mostCurrent._lblnombreenvio.setText(BA.ObjectToCharSequence("Sin nombre"));
 };
 //BA.debugLineNum = 975;BA.debugLine="If lblFecha.Text = \"null\" Then";
if ((mostCurrent._lblfecha.getText()).equals("null")) { 
 //BA.debugLineNum = 976;BA.debugLine="lblFecha.Text = \"Sin fecha\"";
mostCurrent._lblfecha.setText(BA.ObjectToCharSequence("Sin fecha"));
 //BA.debugLineNum = 977;BA.debugLine="lblFecha.TextColor = Colors.red";
mostCurrent._lblfecha.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Red);
 };
 //BA.debugLineNum = 979;BA.debugLine="If lblCalidad.Text = \"null\" Then";
if ((mostCurrent._lblcalidad.getText()).equals("null")) { 
 //BA.debugLineNum = 980;BA.debugLine="lblCalidad.Text = \"Calidad no establecida\"";
mostCurrent._lblcalidad.setText(BA.ObjectToCharSequence("Calidad no establecida"));
 //BA.debugLineNum = 981;BA.debugLine="lblCalidad.TextColor = Colors.red";
mostCurrent._lblcalidad.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Red);
 };
 //BA.debugLineNum = 983;BA.debugLine="If lblNotas.Text = \"null\" Then";
if ((mostCurrent._lblnotas.getText()).equals("null")) { 
 //BA.debugLineNum = 984;BA.debugLine="lblNotas.Text = \"Sin notas\"";
mostCurrent._lblnotas.setText(BA.ObjectToCharSequence("Sin notas"));
 };
 //BA.debugLineNum = 986;BA.debugLine="If lblLat.Text = \"null\" Then";
if ((mostCurrent._lbllat.getText()).equals("null")) { 
 //BA.debugLineNum = 987;BA.debugLine="lblLat.Text = \"Sin latitud\"";
mostCurrent._lbllat.setText(BA.ObjectToCharSequence("Sin latitud"));
 //BA.debugLineNum = 988;BA.debugLine="lblLat.TextColor = Colors.red";
mostCurrent._lbllat.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Red);
 };
 //BA.debugLineNum = 990;BA.debugLine="If lblLng.Text = \"null\" Then";
if ((mostCurrent._lbllng.getText()).equals("null")) { 
 //BA.debugLineNum = 991;BA.debugLine="lblLng.Text = \"Sin longitud\"";
mostCurrent._lbllng.setText(BA.ObjectToCharSequence("Sin longitud"));
 //BA.debugLineNum = 992;BA.debugLine="lblLng.TextColor = Colors.red";
mostCurrent._lbllng.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Red);
 };
 //BA.debugLineNum = 994;BA.debugLine="If lblTipo.Text = \"null\" Then";
if ((mostCurrent._lbltipo.getText()).equals("null")) { 
 //BA.debugLineNum = 995;BA.debugLine="lblTipo.Text = \"Tipo no establecido\"";
mostCurrent._lbltipo.setText(BA.ObjectToCharSequence("Tipo no establecido"));
 //BA.debugLineNum = 996;BA.debugLine="lblTipo.TextColor = Colors.red";
mostCurrent._lbltipo.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Red);
 };
 //BA.debugLineNum = 999;BA.debugLine="Dim datorevisado As String";
_datorevisado = "";
 //BA.debugLineNum = 1000;BA.debugLine="datorevisado = datoMap_hidro.Get(\"estadovalidac";
_datorevisado = BA.ObjectToString(_datomap_hidro.Get((Object)("estadovalidacion")));
 //BA.debugLineNum = 1001;BA.debugLine="If datorevisado = \"validado\" Then";
if ((_datorevisado).equals("validado")) { 
 //BA.debugLineNum = 1002;BA.debugLine="chkEnviado.Visible = False";
mostCurrent._chkenviado.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1004;BA.debugLine="btnEnviar.Visible = False";
mostCurrent._btnenviar.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1005;BA.debugLine="chkEnviado.Checked = True";
mostCurrent._chkenviado.setChecked(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1006;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 1007;BA.debugLine="chkEnviado.Text = \" Enviado\"";
mostCurrent._chkenviado.setText(BA.ObjectToCharSequence(" Enviado"));
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 1009;BA.debugLine="chkEnviado.Text = \" Sent\"";
mostCurrent._chkenviado.setText(BA.ObjectToCharSequence(" Sent"));
 };
 //BA.debugLineNum = 1011;BA.debugLine="chkValidadoExpertos.Text = \"\"";
mostCurrent._chkvalidadoexpertos.setText(BA.ObjectToCharSequence(""));
 //BA.debugLineNum = 1012;BA.debugLine="chkValidadoExpertos.Color = Colors.ARGB(255,76";
mostCurrent._chkvalidadoexpertos.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (76),(int) (159),(int) (56)));
 };
 //BA.debugLineNum = 1017;BA.debugLine="Dim datoprivado As String";
_datoprivado = "";
 //BA.debugLineNum = 1018;BA.debugLine="datoprivado = datoMap_hidro.Get(\"privado\")";
_datoprivado = BA.ObjectToString(_datomap_hidro.Get((Object)("privado")));
 //BA.debugLineNum = 1019;BA.debugLine="If datoprivado = \"no\" Or datoprivado = \"\" Or da";
if ((_datoprivado).equals("no") || (_datoprivado).equals("") || (_datoprivado).equals("null")) { 
 //BA.debugLineNum = 1020;BA.debugLine="chkPublico.Checked = True";
mostCurrent._chkpublico.setChecked(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1021;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 1022;BA.debugLine="chkPublico.Text = \"Dato público\"";
mostCurrent._chkpublico.setText(BA.ObjectToCharSequence("Dato público"));
 //BA.debugLineNum = 1023;BA.debugLine="btnCambiarPublico.Text = \"Cambiar a dato priv";
mostCurrent._btncambiarpublico.setText(BA.ObjectToCharSequence("Cambiar a dato privado"));
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 1025;BA.debugLine="chkPublico.Text = \"Public report\"";
mostCurrent._chkpublico.setText(BA.ObjectToCharSequence("Public report"));
 //BA.debugLineNum = 1026;BA.debugLine="btnCambiarPublico.Text = \"Make report private";
mostCurrent._btncambiarpublico.setText(BA.ObjectToCharSequence("Make report private"));
 };
 }else {
 //BA.debugLineNum = 1029;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 1030;BA.debugLine="chkPublico.Text = \"Dato privado\"";
mostCurrent._chkpublico.setText(BA.ObjectToCharSequence("Dato privado"));
 //BA.debugLineNum = 1031;BA.debugLine="btnCambiarPublico.Text = \"Cambiar a dato públ";
mostCurrent._btncambiarpublico.setText(BA.ObjectToCharSequence("Cambiar a dato público"));
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 1033;BA.debugLine="chkPublico.Text = \"Private report\"";
mostCurrent._chkpublico.setText(BA.ObjectToCharSequence("Private report"));
 //BA.debugLineNum = 1034;BA.debugLine="btnCambiarPublico.Text = \"Make report public\"";
mostCurrent._btncambiarpublico.setText(BA.ObjectToCharSequence("Make report public"));
 };
 //BA.debugLineNum = 1037;BA.debugLine="chkPublico.Checked = False";
mostCurrent._chkpublico.setChecked(anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 1041;BA.debugLine="foto1view.Bitmap = Null";
mostCurrent._foto1view.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null));
 //BA.debugLineNum = 1042;BA.debugLine="Dim foto1path As String";
_foto1path = "";
 //BA.debugLineNum = 1043;BA.debugLine="foto1path = datoMap_hidro.Get(\"foto1\") & \".jpg\"";
_foto1path = BA.ObjectToString(_datomap_hidro.Get((Object)("foto1")))+".jpg";
 //BA.debugLineNum = 1044;BA.debugLine="scvFotos.Panel.Invalidate";
mostCurrent._scvfotos.getPanel().Invalidate();
 //BA.debugLineNum = 1047;BA.debugLine="foto1Borrar.Color = Colors.ARGB(150,255,255,255";
mostCurrent._foto1borrar.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (150),(int) (255),(int) (255),(int) (255)));
 //BA.debugLineNum = 1048;BA.debugLine="foto1Borrar.Text = \"Eliminar imagen\"";
mostCurrent._foto1borrar.setText(BA.ObjectToCharSequence("Eliminar imagen"));
 //BA.debugLineNum = 1049;BA.debugLine="foto1Borrar.TextColor = Colors.Black";
mostCurrent._foto1borrar.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 1050;BA.debugLine="foto1view.Gravity=Gravity.FILL";
mostCurrent._foto1view.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.FILL);
 //BA.debugLineNum = 1053;BA.debugLine="Dim cd As ColorDrawable";
_cd = new anywheresoftware.b4a.objects.drawable.ColorDrawable();
 //BA.debugLineNum = 1054;BA.debugLine="cd.Initialize2(Colors.Transparent,10dip,2dip,Co";
_cd.Initialize2(anywheresoftware.b4a.keywords.Common.Colors.Transparent,anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (2)),anywheresoftware.b4a.keywords.Common.Colors.LightGray);
 //BA.debugLineNum = 1057;BA.debugLine="If File.Exists(rp.GetSafeDirDefaultExternal(\"Pr";
if (anywheresoftware.b4a.keywords.Common.File.Exists(mostCurrent._rp.GetSafeDirDefaultExternal("PreserVamos"),_foto1path)) { 
 //BA.debugLineNum = 1058;BA.debugLine="foto1view.Bitmap= LoadBitmapSample( rp.GetSafe";
mostCurrent._foto1view.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(mostCurrent._rp.GetSafeDirDefaultExternal("PreserVamos"),_foto1path,anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 }else {
 //BA.debugLineNum = 1060;BA.debugLine="foto1view.Background=cd";
mostCurrent._foto1view.setBackground((android.graphics.drawable.Drawable)(_cd.getObject()));
 //BA.debugLineNum = 1061;BA.debugLine="Dim cdplus1 As Label";
_cdplus1 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 1062;BA.debugLine="cdplus1.Initialize(\"\")";
_cdplus1.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 1063;BA.debugLine="cdplus1.Text = \"+\"";
_cdplus1.setText(BA.ObjectToCharSequence("+"));
 //BA.debugLineNum = 1064;BA.debugLine="cdplus1.TextSize = 48";
_cdplus1.setTextSize((float) (48));
 //BA.debugLineNum = 1065;BA.debugLine="cdplus1.TextColor = Colors.LightGray";
_cdplus1.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.LightGray);
 //BA.debugLineNum = 1066;BA.debugLine="cdplus1.Gravity = Bit.Or(Gravity.CENTER_VERTIC";
_cdplus1.setGravity(anywheresoftware.b4a.keywords.Common.Bit.Or(anywheresoftware.b4a.keywords.Common.Gravity.CENTER_VERTICAL,anywheresoftware.b4a.keywords.Common.Gravity.CENTER_HORIZONTAL));
 //BA.debugLineNum = 1067;BA.debugLine="scvFotos.Panel.AddView(cdplus1, foto1view.Left";
mostCurrent._scvfotos.getPanel().AddView((android.view.View)(_cdplus1.getObject()),mostCurrent._foto1view.getLeft(),mostCurrent._foto1view.getTop(),mostCurrent._foto1view.getWidth(),mostCurrent._foto1view.getHeight());
 };
 };
 };
 //BA.debugLineNum = 1075;BA.debugLine="tabStripDatosAnteriores.ScrollTo(1,True)";
mostCurrent._tabstripdatosanteriores.ScrollTo((int) (1),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1076;BA.debugLine="tabActual = \"Detalle\"";
mostCurrent._tabactual = "Detalle";
 //BA.debugLineNum = 1077;BA.debugLine="notificacion = False";
_notificacion = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 1078;BA.debugLine="serverId  = False";
_serverid = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 1080;BA.debugLine="End Sub";
return "";
}
}
