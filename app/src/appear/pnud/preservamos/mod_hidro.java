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

public class mod_hidro extends Activity implements B4AActivity{
	public static mod_hidro mostCurrent;
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
			processBA = new BA(this.getApplicationContext(), null, null, "appear.pnud.preservamos", "appear.pnud.preservamos.mod_hidro");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
            
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (mod_hidro).");
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
		activityBA = new BA(this, layout, processBA, "appear.pnud.preservamos", "appear.pnud.preservamos.mod_hidro");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "appear.pnud.preservamos.mod_hidro", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density, mostCurrent);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (mod_hidro) Create " + (isFirst ? "(first time)" : "") + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (mod_hidro) Resume **");
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
		return mod_hidro.class;
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
            BA.LogInfo("** Activity (mod_hidro) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
        else
            BA.LogInfo("** Activity (mod_hidro) Pause event (activity is not paused). **");
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
            mod_hidro mc = mostCurrent;
			if (mc == null || mc != activity.get())
				return;
			processBA.setActivityPaused(false);
            BA.LogInfo("** Activity (mod_hidro) Resume **");
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
public anywheresoftware.b4a.objects.ButtonWrapper _btnokbasico1 = null;
public static String _coordenadas_string = "";
public anywheresoftware.b4a.objects.LabelWrapper _lblfecha = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblcoordenadascarga = null;
public anywheresoftware.b4a.objects.PanelWrapper _fondogris = null;
public anywheresoftware.b4a.objects.PanelWrapper _panelcomofunciona = null;
public static String _valorind1 = "";
public static String _tiporio = "";
public static String _proyectoidenviar = "";
public anywheresoftware.b4a.objects.collections.List _files = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblcoordenadastitle = null;
public anywheresoftware.b4a.objects.LabelWrapper _borderfotos1 = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imgfoto1 = null;
public anywheresoftware.b4a.objects.ProgressBarWrapper _progressbar1 = null;
public static String _foto1 = "";
public static int _totalfotos = 0;
public static boolean _foto1sent = false;
public static int _fotosenviadas = 0;
public anywheresoftware.b4a.phone.Phone.PhoneWakeState _pw = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imglisto = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imglimnimetro = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imgnolimnimetro = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbllimnimetro = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblnolimnimetro = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imghidro = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblcomofuncionatitle = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblcomofuncionacontenido = null;
public anywheresoftware.b4a.objects.PanelWrapper _pnlaltura = null;
public appear.pnud.preservamos.hvseekbar _barv1 = null;
public anywheresoftware.b4a.objects.LabelWrapper _labelbarv1 = null;
public anywheresoftware.b4a.objects.LabelWrapper _labelbarv2 = null;
public anywheresoftware.b4a.objects.LabelWrapper _labelbarv3 = null;
public anywheresoftware.b4a.objects.LabelWrapper _labelbarh1 = null;
public anywheresoftware.b4a.objects.LabelWrapper _labelbarh2 = null;
public anywheresoftware.b4a.objects.LabelWrapper _labelbarh3 = null;
public anywheresoftware.b4a.objects.PanelWrapper _panelseeks = null;
public appear.pnud.preservamos.b4xfloattextfield _txtaltura = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imgalturamanual = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblalturamanual = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblalturamanualtitle = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnokcarga = null;
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
public appear.pnud.preservamos.frmlocalizacion _frmlocalizacion = null;
public appear.pnud.preservamos.frmmapa _frmmapa = null;
public appear.pnud.preservamos.frmmunicipioestadisticas _frmmunicipioestadisticas = null;
public appear.pnud.preservamos.frmpoliticadatos _frmpoliticadatos = null;
public appear.pnud.preservamos.frmtiporeporte _frmtiporeporte = null;
public appear.pnud.preservamos.httputils2service _httputils2service = null;
public appear.pnud.preservamos.imagedownloader _imagedownloader = null;
public appear.pnud.preservamos.inatcheck _inatcheck = null;
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
 //BA.debugLineNum = 86;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
 //BA.debugLineNum = 88;BA.debugLine="Activity.LoadLayout(\"mod_hidro_container\")";
mostCurrent._activity.LoadLayout("mod_hidro_container",mostCurrent.activityBA);
 //BA.debugLineNum = 90;BA.debugLine="tabContainer.LoadLayout(\"mod_hidro_instruccion_1\"";
mostCurrent._tabcontainer.LoadLayout("mod_hidro_instruccion_1",BA.ObjectToCharSequence("1"));
 //BA.debugLineNum = 91;BA.debugLine="tabContainer.LoadLayout(\"mod_hidro_instruccion_2\"";
mostCurrent._tabcontainer.LoadLayout("mod_hidro_instruccion_2",BA.ObjectToCharSequence("2"));
 //BA.debugLineNum = 92;BA.debugLine="tabContainer.LoadLayout(\"mod_hidro_instruccion_3\"";
mostCurrent._tabcontainer.LoadLayout("mod_hidro_instruccion_3",BA.ObjectToCharSequence("3"));
 //BA.debugLineNum = 93;BA.debugLine="tabContainer.LoadLayout(\"mod_hidro_instruccion_4\"";
mostCurrent._tabcontainer.LoadLayout("mod_hidro_instruccion_4",BA.ObjectToCharSequence("4"));
 //BA.debugLineNum = 94;BA.debugLine="foto1 = \"\"";
mostCurrent._foto1 = "";
 //BA.debugLineNum = 97;BA.debugLine="End Sub";
return "";
}
public static boolean  _activity_keypress(int _keycode) throws Exception{
 //BA.debugLineNum = 108;BA.debugLine="Sub Activity_KeyPress (KeyCode As Int) As Boolean";
 //BA.debugLineNum = 109;BA.debugLine="If KeyCode = KeyCodes.KEYCODE_BACK Then";
if (_keycode==anywheresoftware.b4a.keywords.Common.KeyCodes.KEYCODE_BACK) { 
 //BA.debugLineNum = 110;BA.debugLine="closeAppMsgBox";
_closeappmsgbox();
 //BA.debugLineNum = 111;BA.debugLine="Return True";
if (true) return anywheresoftware.b4a.keywords.Common.True;
 }else {
 //BA.debugLineNum = 113;BA.debugLine="Return False";
if (true) return anywheresoftware.b4a.keywords.Common.False;
 };
 //BA.debugLineNum = 115;BA.debugLine="End Sub";
return false;
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 103;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 105;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 99;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 101;BA.debugLine="End Sub";
return "";
}
public static String  _actualizarcoordenadas() throws Exception{
 //BA.debugLineNum = 319;BA.debugLine="Sub ActualizarCoordenadas";
 //BA.debugLineNum = 320;BA.debugLine="coordenadas_string = \"Coordenadas: \" & Round2(lat";
mostCurrent._coordenadas_string = "Coordenadas: "+BA.NumberToString(anywheresoftware.b4a.keywords.Common.Round2((double)(Double.parseDouble(_lat)),(int) (3)))+" | "+BA.NumberToString(anywheresoftware.b4a.keywords.Common.Round2((double)(Double.parseDouble(_lng)),(int) (3)));
 //BA.debugLineNum = 321;BA.debugLine="lblCoordenadasCarga.Text = coordenadas_string";
mostCurrent._lblcoordenadascarga.setText(BA.ObjectToCharSequence(mostCurrent._coordenadas_string));
 //BA.debugLineNum = 322;BA.debugLine="If foto1 = \"\" Then";
if ((mostCurrent._foto1).equals("")) { 
 //BA.debugLineNum = 324;BA.debugLine="If tiporio = \"limnimetro\" Then";
if ((mostCurrent._tiporio).equals("limnimetro")) { 
 //BA.debugLineNum = 325;BA.debugLine="pnlAltura.Visible = True";
mostCurrent._pnlaltura.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 326;BA.debugLine="panelSeeks.Visible = False";
mostCurrent._panelseeks.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 327;BA.debugLine="lblAlturaManual.Visible = False";
mostCurrent._lblalturamanual.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 328;BA.debugLine="lblAlturaManualTitle.Visible = False";
mostCurrent._lblalturamanualtitle.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 329;BA.debugLine="imgAlturaManual.visible = False";
mostCurrent._imgalturamanual.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 331;BA.debugLine="fondogris.Initialize(\"fondogris\")";
mostCurrent._fondogris.Initialize(mostCurrent.activityBA,"fondogris");
 //BA.debugLineNum = 332;BA.debugLine="fondogris.Color = Colors.ARGB(150,0,0,0)";
mostCurrent._fondogris.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (150),(int) (0),(int) (0),(int) (0)));
 //BA.debugLineNum = 333;BA.debugLine="Activity.AddView(fondogris, 0, 0, 100%x, 100%y)";
mostCurrent._activity.AddView((android.view.View)(mostCurrent._fondogris.getObject()),(int) (0),(int) (0),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA));
 //BA.debugLineNum = 335;BA.debugLine="panelComoFunciona.Initialize(\"\")";
mostCurrent._panelcomofunciona.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 336;BA.debugLine="panelComoFunciona.LoadLayout(\"dialog_comoFuncio";
mostCurrent._panelcomofunciona.LoadLayout("dialog_comoFunciona",mostCurrent.activityBA);
 //BA.debugLineNum = 337;BA.debugLine="Activity.AddView(panelComoFunciona, 10%x, 20%y,";
mostCurrent._activity.AddView((android.view.View)(mostCurrent._panelcomofunciona.getObject()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (10),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (20),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (80),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (80),mostCurrent.activityBA));
 //BA.debugLineNum = 338;BA.debugLine="lblComoFuncionaContenido.Text = \"Si encontraste";
mostCurrent._lblcomofuncionacontenido.setText(BA.ObjectToCharSequence("Si encontraste una varilla blanca y negra para medir la altura del río (limnímetro), toma una foto adonde se vea tanto la varilla como la altura del agua."));
 //BA.debugLineNum = 339;BA.debugLine="lblComoFuncionaTitle.Text = \"¡Encontraste un li";
mostCurrent._lblcomofuncionatitle.setText(BA.ObjectToCharSequence("¡Encontraste un limnímetro!"));
 }else {
 //BA.debugLineNum = 342;BA.debugLine="pnlAltura.Visible = False";
mostCurrent._pnlaltura.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 343;BA.debugLine="panelSeeks.Visible = True";
mostCurrent._panelseeks.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 344;BA.debugLine="lblAlturaManual.Visible = True";
mostCurrent._lblalturamanual.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 345;BA.debugLine="imgAlturaManual.visible = True";
mostCurrent._imgalturamanual.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 346;BA.debugLine="lblAlturaManualTitle.Visible = True";
mostCurrent._lblalturamanualtitle.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 348;BA.debugLine="barV1.Initialize(Me, panelSeeks, \"barV1\")";
mostCurrent._barv1._initialize /*String*/ (mostCurrent.activityBA,mod_hidro.getObject(),(Object)(mostCurrent._panelseeks.getObject()),"barV1");
 //BA.debugLineNum = 349;BA.debugLine="barV1.SetColors(Colors.LightGray, Colors.ARGB(2";
mostCurrent._barv1._setcolors /*String*/ (anywheresoftware.b4a.keywords.Common.Colors.LightGray,anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (66),(int) (132),(int) (246)),anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (28),(int) (52),(int) (94)),anywheresoftware.b4a.keywords.Common.Colors.Gray,anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 350;BA.debugLine="barV1.SetInitValues(0, 100, 50) 'V min, V max,";
mostCurrent._barv1._setinitvalues /*String*/ ((int) (0),(int) (100),(int) (50));
 //BA.debugLineNum = 351;BA.debugLine="barV1.SetShape(barV1.cursorType_RECTANGLE, 70di";
mostCurrent._barv1._setshape /*String*/ (mostCurrent._barv1._cursortype_rectangle /*String*/ (),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (70)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (2)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (70)));
 //BA.debugLineNum = 352;BA.debugLine="barV1.ShowBar(0, 0, 70dip,imgAlturaManual.Heigh";
mostCurrent._barv1._showbar /*String*/ ((int) (0),(int) (0),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (70)),mostCurrent._imgalturamanual.getHeight(),mostCurrent._barv1._orientation_vertical /*String*/ ());
 //BA.debugLineNum = 353;BA.debugLine="labelBarV1.Initialize(\"\")";
mostCurrent._labelbarv1.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 355;BA.debugLine="panelSeeks.AddView(labelBarV1, 17%x, 5%x, 18%x,";
mostCurrent._panelseeks.AddView((android.view.View)(mostCurrent._labelbarv1.getObject()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (17),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (5),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (18),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (8),mostCurrent.activityBA));
 //BA.debugLineNum = 357;BA.debugLine="StartActivity(mod_Hidro_Fotos)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._mod_hidro_fotos.getObject()));
 };
 };
 //BA.debugLineNum = 361;BA.debugLine="End Sub";
return "";
}
public static String  _actualizarfotos() throws Exception{
 //BA.debugLineNum = 393;BA.debugLine="Sub ActualizarFotos";
 //BA.debugLineNum = 394;BA.debugLine="If tiporio = \"limnimetro\" Then";
if ((mostCurrent._tiporio).equals("limnimetro")) { 
 //BA.debugLineNum = 395;BA.debugLine="imgHidro.Bitmap = Null";
mostCurrent._imghidro.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null));
 //BA.debugLineNum = 396;BA.debugLine="imgHidro.Bitmap = LoadBitmapSample(Starter.saved";
mostCurrent._imghidro.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(mostCurrent._starter._savedir /*String*/ ,_foto1_path+".jpg",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 };
 //BA.debugLineNum = 399;BA.debugLine="End Sub";
return "";
}
public static String  _barv1_valuechanged(int _value) throws Exception{
 //BA.debugLineNum = 363;BA.debugLine="Sub barV1_ValueChanged(Value As Int)";
 //BA.debugLineNum = 364;BA.debugLine="labelBarV1.Text = \"\"";
mostCurrent._labelbarv1.setText(BA.ObjectToCharSequence(""));
 //BA.debugLineNum = 365;BA.debugLine="If Value =0 Then";
if (_value==0) { 
 //BA.debugLineNum = 366;BA.debugLine="lblAlturaManual.Text= \"Río seco\"";
mostCurrent._lblalturamanual.setText(BA.ObjectToCharSequence("Río seco"));
 //BA.debugLineNum = 367;BA.debugLine="imgAlturaManual.Bitmap = Null";
mostCurrent._imgalturamanual.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null));
 //BA.debugLineNum = 368;BA.debugLine="imgAlturaManual.Bitmap = LoadBitmapSample(File.D";
mostCurrent._imgalturamanual.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"hidro_seco.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 }else if(_value<=10 && _value>0) { 
 //BA.debugLineNum = 370;BA.debugLine="lblAlturaManual.Text= \"Río casi seco\"";
mostCurrent._lblalturamanual.setText(BA.ObjectToCharSequence("Río casi seco"));
 //BA.debugLineNum = 371;BA.debugLine="imgAlturaManual.Bitmap = Null";
mostCurrent._imgalturamanual.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null));
 //BA.debugLineNum = 372;BA.debugLine="imgAlturaManual.Bitmap = LoadBitmapSample(File.D";
mostCurrent._imgalturamanual.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"hidro_muybajo.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 }else if(_value>10 && _value<=50) { 
 //BA.debugLineNum = 374;BA.debugLine="lblAlturaManual.Text= \"Río con altura normal\"";
mostCurrent._lblalturamanual.setText(BA.ObjectToCharSequence("Río con altura normal"));
 //BA.debugLineNum = 375;BA.debugLine="imgAlturaManual.Bitmap = Null";
mostCurrent._imgalturamanual.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null));
 //BA.debugLineNum = 376;BA.debugLine="imgAlturaManual.Bitmap = LoadBitmapSample(File.D";
mostCurrent._imgalturamanual.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"hidro_bajo.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 }else if(_value>50 && _value<=80) { 
 //BA.debugLineNum = 378;BA.debugLine="lblAlturaManual.Text= \"Río alto\"";
mostCurrent._lblalturamanual.setText(BA.ObjectToCharSequence("Río alto"));
 //BA.debugLineNum = 379;BA.debugLine="imgAlturaManual.Bitmap = Null";
mostCurrent._imgalturamanual.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null));
 //BA.debugLineNum = 380;BA.debugLine="imgAlturaManual.Bitmap = LoadBitmapSample(File.D";
mostCurrent._imgalturamanual.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"hidro_normal.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 }else if(_value>80 && _value<100) { 
 //BA.debugLineNum = 382;BA.debugLine="lblAlturaManual.Text= \"Río crecido\"";
mostCurrent._lblalturamanual.setText(BA.ObjectToCharSequence("Río crecido"));
 //BA.debugLineNum = 383;BA.debugLine="imgAlturaManual.Bitmap = Null";
mostCurrent._imgalturamanual.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null));
 //BA.debugLineNum = 384;BA.debugLine="imgAlturaManual.Bitmap = LoadBitmapSample(File.D";
mostCurrent._imgalturamanual.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"hidro_alto.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 }else if(_value==100) { 
 //BA.debugLineNum = 386;BA.debugLine="lblAlturaManual.Text= \"Río desbordado\"";
mostCurrent._lblalturamanual.setText(BA.ObjectToCharSequence("Río desbordado"));
 //BA.debugLineNum = 387;BA.debugLine="imgAlturaManual.Bitmap = Null";
mostCurrent._imgalturamanual.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null));
 //BA.debugLineNum = 388;BA.debugLine="imgAlturaManual.Bitmap = LoadBitmapSample(File.D";
mostCurrent._imgalturamanual.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"hidro_desbordado.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 };
 //BA.debugLineNum = 390;BA.debugLine="End Sub";
return "";
}
public static String  _btncomenzarmodulo_click() throws Exception{
 //BA.debugLineNum = 203;BA.debugLine="Private Sub btnComenzarModulo_Click";
 //BA.debugLineNum = 204;BA.debugLine="cargar_basicos";
_cargar_basicos();
 //BA.debugLineNum = 205;BA.debugLine="End Sub";
return "";
}
public static String  _btneditcoordenadas_click() throws Exception{
 //BA.debugLineNum = 412;BA.debugLine="Private Sub btnEditCoordenadas_Click";
 //BA.debugLineNum = 414;BA.debugLine="frmLocalizacion.origen = \"modulo_hidro\"";
mostCurrent._frmlocalizacion._origen /*String*/  = "modulo_hidro";
 //BA.debugLineNum = 415;BA.debugLine="StartActivity(frmLocalizacion)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._frmlocalizacion.getObject()));
 //BA.debugLineNum = 416;BA.debugLine="End Sub";
return "";
}
public static String  _btnnext_click() throws Exception{
 //BA.debugLineNum = 170;BA.debugLine="Private Sub btnNext_Click";
 //BA.debugLineNum = 171;BA.debugLine="If tabContainer.CurrentPage = 0 Then";
if (mostCurrent._tabcontainer.getCurrentPage()==0) { 
 //BA.debugLineNum = 172;BA.debugLine="tabContainer.ScrollTo(1, True)";
mostCurrent._tabcontainer.ScrollTo((int) (1),anywheresoftware.b4a.keywords.Common.True);
 }else if(mostCurrent._tabcontainer.getCurrentPage()==1) { 
 //BA.debugLineNum = 174;BA.debugLine="tabContainer.ScrollTo(2, True)";
mostCurrent._tabcontainer.ScrollTo((int) (2),anywheresoftware.b4a.keywords.Common.True);
 }else if(mostCurrent._tabcontainer.getCurrentPage()==2) { 
 //BA.debugLineNum = 176;BA.debugLine="tabContainer.ScrollTo(3, True)";
mostCurrent._tabcontainer.ScrollTo((int) (3),anywheresoftware.b4a.keywords.Common.True);
 }else if(mostCurrent._tabcontainer.getCurrentPage()==3) { 
 //BA.debugLineNum = 178;BA.debugLine="tabContainer.ScrollTo(4, True)";
mostCurrent._tabcontainer.ScrollTo((int) (4),anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 180;BA.debugLine="End Sub";
return "";
}
public static String  _btnokbasico1_click() throws Exception{
 //BA.debugLineNum = 230;BA.debugLine="Private Sub btnOkBasico1_Click";
 //BA.debugLineNum = 231;BA.debugLine="create_reporte";
_create_reporte();
 //BA.debugLineNum = 232;BA.debugLine="End Sub";
return "";
}
public static String  _btnokcarga_click() throws Exception{
anywheresoftware.b4a.objects.collections.Map _map1 = null;
 //BA.debugLineNum = 448;BA.debugLine="Private Sub btnOkCarga_Click";
 //BA.debugLineNum = 449;BA.debugLine="ProgressBar1.Visible = False";
mostCurrent._progressbar1.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 450;BA.debugLine="btnOkCarga.Enabled = False";
mostCurrent._btnokcarga.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 451;BA.debugLine="btnOkCarga.Text = \"Enviando información... espere";
mostCurrent._btnokcarga.setText(BA.ObjectToCharSequence("Enviando información... espere"));
 //BA.debugLineNum = 454;BA.debugLine="Try";
try { //BA.debugLineNum = 455;BA.debugLine="Dim Map1 As Map";
_map1 = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 456;BA.debugLine="Map1.Initialize";
_map1.Initialize();
 //BA.debugLineNum = 457;BA.debugLine="Log(\"currentproject: \" & Main.currentproject)";
anywheresoftware.b4a.keywords.Common.LogImpl("456819721","currentproject: "+mostCurrent._main._currentproject /*String*/ ,0);
 //BA.debugLineNum = 458;BA.debugLine="Map1.Put(\"Id\", Main.currentproject)";
_map1.Put((Object)("Id"),(Object)(mostCurrent._main._currentproject /*String*/ ));
 //BA.debugLineNum = 459;BA.debugLine="DBUtils.UpdateRecord(Starter.modulo_hidro_sqlDB,";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._modulo_hidro_sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals_hidro","tiporio",(Object)(mostCurrent._tiporio),_map1);
 //BA.debugLineNum = 460;BA.debugLine="If tiporio = \"limnimetro\" Then";
if ((mostCurrent._tiporio).equals("limnimetro")) { 
 //BA.debugLineNum = 461;BA.debugLine="DBUtils.UpdateRecord(Starter.modulo_hidro_sqlDB";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._modulo_hidro_sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals_hidro","valorind1",(Object)(mostCurrent._txtaltura._gettext /*String*/ ()),_map1);
 }else {
 //BA.debugLineNum = 463;BA.debugLine="DBUtils.UpdateRecord(Starter.modulo_hidro_sqlDB";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._modulo_hidro_sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals_hidro","valorind2",(Object)(mostCurrent._barv1._getseekvalue /*int*/ ()),_map1);
 };
 //BA.debugLineNum = 465;BA.debugLine="DBUtils.UpdateRecord(Starter.modulo_hidro_sqlDB,";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._modulo_hidro_sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals_hidro","decimalLatitude",(Object)(_lat),_map1);
 //BA.debugLineNum = 466;BA.debugLine="DBUtils.UpdateRecord(Starter.modulo_hidro_sqlDB,";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._modulo_hidro_sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals_hidro","decimalLongitude",(Object)(_lng),_map1);
 //BA.debugLineNum = 467;BA.debugLine="DBUtils.UpdateRecord(Starter.modulo_hidro_sqlDB,";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._modulo_hidro_sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals_hidro","georeferencedDate",(Object)(anywheresoftware.b4a.keywords.Common.DateTime.Date(anywheresoftware.b4a.keywords.Common.DateTime.getNow())),_map1);
 //BA.debugLineNum = 468;BA.debugLine="DBUtils.UpdateRecord(Starter.modulo_hidro_sqlDB,";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._modulo_hidro_sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals_hidro","foto1",(Object)(_foto1_path),_map1);
 //BA.debugLineNum = 471;BA.debugLine="DBUtils.UpdateRecord(Starter.modulo_hidro_sqlDB,";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._modulo_hidro_sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals_hidro","terminado",(Object)("si"),_map1);
 //BA.debugLineNum = 473;BA.debugLine="enviar_mod";
_enviar_mod();
 } 
       catch (Exception e22) {
			processBA.setLastException(e22); //BA.debugLineNum = 475;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("456819739",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA)),0);
 //BA.debugLineNum = 476;BA.debugLine="ToastMessageShow(\"No se pudieron guardar los dat";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("No se pudieron guardar los datos"),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 477;BA.debugLine="btnOkCarga.Enabled = True";
mostCurrent._btnokcarga.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 478;BA.debugLine="btnOkCarga.Text = \"¡Finalizar y enviar!\"";
mostCurrent._btnokcarga.setText(BA.ObjectToCharSequence("¡Finalizar y enviar!"));
 };
 //BA.debugLineNum = 481;BA.debugLine="End Sub";
return "";
}
public static String  _btnprev_click() throws Exception{
 //BA.debugLineNum = 181;BA.debugLine="Private Sub btnPrev_Click";
 //BA.debugLineNum = 182;BA.debugLine="If tabContainer.CurrentPage = 1 Then";
if (mostCurrent._tabcontainer.getCurrentPage()==1) { 
 //BA.debugLineNum = 183;BA.debugLine="tabContainer.ScrollTo(0, True)";
mostCurrent._tabcontainer.ScrollTo((int) (0),anywheresoftware.b4a.keywords.Common.True);
 }else if(mostCurrent._tabcontainer.getCurrentPage()==2) { 
 //BA.debugLineNum = 185;BA.debugLine="tabContainer.ScrollTo(1, True)";
mostCurrent._tabcontainer.ScrollTo((int) (1),anywheresoftware.b4a.keywords.Common.True);
 }else if(mostCurrent._tabcontainer.getCurrentPage()==3) { 
 //BA.debugLineNum = 187;BA.debugLine="tabContainer.ScrollTo(2, True)";
mostCurrent._tabcontainer.ScrollTo((int) (2),anywheresoftware.b4a.keywords.Common.True);
 }else if(mostCurrent._tabcontainer.getCurrentPage()==4) { 
 //BA.debugLineNum = 189;BA.debugLine="tabContainer.ScrollTo(3, True)";
mostCurrent._tabcontainer.ScrollTo((int) (3),anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 192;BA.debugLine="End Sub";
return "";
}
public static String  _cargar_basicos() throws Exception{
 //BA.debugLineNum = 206;BA.debugLine="Private Sub cargar_basicos";
 //BA.debugLineNum = 207;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 208;BA.debugLine="Activity.LoadLayout(\"mod_hidro_basico\")";
mostCurrent._activity.LoadLayout("mod_hidro_basico",mostCurrent.activityBA);
 //BA.debugLineNum = 212;BA.debugLine="End Sub";
return "";
}
public static String  _checkinternet() throws Exception{
appear.pnud.preservamos.downloadservice._downloaddata _dd = null;
 //BA.debugLineNum = 509;BA.debugLine="Sub CheckInternet";
 //BA.debugLineNum = 510;BA.debugLine="Dim dd As DownloadData";
_dd = new appear.pnud.preservamos.downloadservice._downloaddata();
 //BA.debugLineNum = 511;BA.debugLine="dd.url = Main.serverPath &  \"/\" & Main.serverConn";
_dd.url /*String*/  = mostCurrent._main._serverpath /*String*/ +"/"+mostCurrent._main._serverconnectionfolder /*String*/ +"/connecttest.php";
 //BA.debugLineNum = 512;BA.debugLine="dd.EventName = \"TestInternet\"";
_dd.EventName /*String*/  = "TestInternet";
 //BA.debugLineNum = 513;BA.debugLine="dd.Target = Me";
_dd.Target /*Object*/  = mod_hidro.getObject();
 //BA.debugLineNum = 514;BA.debugLine="CallSubDelayed2(DownloadService, \"StartDownload\",";
anywheresoftware.b4a.keywords.Common.CallSubDelayed2(processBA,(Object)(mostCurrent._downloadservice.getObject()),"StartDownload",(Object)(_dd));
 //BA.debugLineNum = 515;BA.debugLine="End Sub";
return "";
}
public static void  _closeappmsgbox() throws Exception{
ResumableSub_closeAppMsgBox rsub = new ResumableSub_closeAppMsgBox(null);
rsub.resume(processBA, null);
}
public static class ResumableSub_closeAppMsgBox extends BA.ResumableSub {
public ResumableSub_closeAppMsgBox(appear.pnud.preservamos.mod_hidro parent) {
this.parent = parent;
}
appear.pnud.preservamos.mod_hidro parent;
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
 //BA.debugLineNum = 118;BA.debugLine="If Main.lang = \"es\" Then";
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
 //BA.debugLineNum = 119;BA.debugLine="Msgbox2Async(\"Cerrar evaluación?\", \"Atención: Pe";
anywheresoftware.b4a.keywords.Common.Msgbox2Async(BA.ObjectToCharSequence("Cerrar evaluación?"),BA.ObjectToCharSequence("Atención: Perderá lo cargado!"),"Si","","No",(anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper(), (android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null)),processBA,anywheresoftware.b4a.keywords.Common.False);
 if (true) break;

case 5:
//C
this.state = 6;
 //BA.debugLineNum = 121;BA.debugLine="Msgbox2Async(\"Back to the beginning?\", \"Exit\", \"";
anywheresoftware.b4a.keywords.Common.Msgbox2Async(BA.ObjectToCharSequence("Back to the beginning?"),BA.ObjectToCharSequence("Exit"),"Yes","","No",(anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper(), (android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null)),processBA,anywheresoftware.b4a.keywords.Common.False);
 if (true) break;

case 6:
//C
this.state = 7;
;
 //BA.debugLineNum = 123;BA.debugLine="Wait For Msgbox_Result (Result As Int)";
anywheresoftware.b4a.keywords.Common.WaitFor("msgbox_result", processBA, this, null);
this.state = 11;
return;
case 11:
//C
this.state = 7;
_result = (Integer) result[0];
;
 //BA.debugLineNum = 124;BA.debugLine="If Result = DialogResponse.POSITIVE Then";
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
 //BA.debugLineNum = 125;BA.debugLine="Activity.finish";
parent.mostCurrent._activity.Finish();
 //BA.debugLineNum = 126;BA.debugLine="Activity.RemoveAllViews";
parent.mostCurrent._activity.RemoveAllViews();
 if (true) break;

case 10:
//C
this.state = -1;
;
 //BA.debugLineNum = 128;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public static void  _msgbox_result(int _result) throws Exception{
}
public static String  _create_reporte() throws Exception{
anywheresoftware.b4a.objects.collections.List _neweval = null;
anywheresoftware.b4a.objects.collections.Map _m = null;
anywheresoftware.b4a.objects.collections.Map _currentprojectmap = null;
String _usernamenoaccent = "";
anywheresoftware.b4a.objects.collections.Map _map1 = null;
 //BA.debugLineNum = 233;BA.debugLine="Sub create_reporte";
 //BA.debugLineNum = 236;BA.debugLine="Dim newEval As List";
_neweval = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 237;BA.debugLine="newEval.Initialize";
_neweval.Initialize();
 //BA.debugLineNum = 238;BA.debugLine="Dim m As Map";
_m = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 239;BA.debugLine="m.Initialize";
_m.Initialize();
 //BA.debugLineNum = 240;BA.debugLine="m.Put(\"useremail\", Main.strUserEmail)";
_m.Put((Object)("useremail"),(Object)(mostCurrent._main._struseremail /*String*/ ));
 //BA.debugLineNum = 241;BA.debugLine="newEval.Add(m)";
_neweval.Add((Object)(_m.getObject()));
 //BA.debugLineNum = 242;BA.debugLine="If Starter.modulo_hidro_sqlDB = Null Then";
if (mostCurrent._starter._modulo_hidro_sqldb /*anywheresoftware.b4a.sql.SQL*/ == null) { 
 //BA.debugLineNum = 243;BA.debugLine="Starter.modulo_hidro_sqlDB.Initialize(Starter.mo";
mostCurrent._starter._modulo_hidro_sqldb /*anywheresoftware.b4a.sql.SQL*/ .Initialize(BA.ObjectToString(mostCurrent._starter._modulo_hidro_sqldb /*anywheresoftware.b4a.sql.SQL*/ ),"preservamos_hidro_db.db",anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 245;BA.debugLine="Try";
try { //BA.debugLineNum = 246;BA.debugLine="DBUtils.InsertMaps(Starter.modulo_hidro_sqlDB,\"e";
mostCurrent._dbutils._insertmaps /*String*/ (mostCurrent.activityBA,mostCurrent._starter._modulo_hidro_sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals_hidro",_neweval);
 } 
       catch (Exception e13) {
			processBA.setLastException(e13); //BA.debugLineNum = 248;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("456295439",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA)),0);
 //BA.debugLineNum = 249;BA.debugLine="ToastMessageShow(\"Hubo un error agregando su inf";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Hubo un error agregando su informe, intente de nuevo"),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 250;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 255;BA.debugLine="Dim currentprojectMap As Map";
_currentprojectmap = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 256;BA.debugLine="currentprojectMap.Initialize";
_currentprojectmap.Initialize();
 //BA.debugLineNum = 257;BA.debugLine="Try";
try { //BA.debugLineNum = 258;BA.debugLine="currentprojectMap = DBUtils.ExecuteMap(Starter.m";
_currentprojectmap = mostCurrent._dbutils._executemap /*anywheresoftware.b4a.objects.collections.Map*/ (mostCurrent.activityBA,mostCurrent._starter._modulo_hidro_sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"SELECT * FROM evals_hidro ORDER BY id DESC LIMIT 1",(String[])(anywheresoftware.b4a.keywords.Common.Null));
 //BA.debugLineNum = 259;BA.debugLine="Dim usernameNoAccent As String";
_usernamenoaccent = "";
 //BA.debugLineNum = 260;BA.debugLine="usernameNoAccent = Main.username.Replace(\"á\", \"a";
_usernamenoaccent = mostCurrent._main._username /*String*/ .replace("á","a");
 //BA.debugLineNum = 261;BA.debugLine="usernameNoAccent = Main.username.Replace(\"é\", \"e";
_usernamenoaccent = mostCurrent._main._username /*String*/ .replace("é","e");
 //BA.debugLineNum = 262;BA.debugLine="usernameNoAccent = Main.username.Replace(\"í\", \"i";
_usernamenoaccent = mostCurrent._main._username /*String*/ .replace("í","i");
 //BA.debugLineNum = 263;BA.debugLine="usernameNoAccent = Main.username.Replace(\"ó\", \"o";
_usernamenoaccent = mostCurrent._main._username /*String*/ .replace("ó","o");
 //BA.debugLineNum = 264;BA.debugLine="usernameNoAccent = Main.username.Replace(\"ú\", \"u";
_usernamenoaccent = mostCurrent._main._username /*String*/ .replace("ú","u");
 //BA.debugLineNum = 265;BA.debugLine="usernameNoAccent = Main.username.Replace(\"ñ\", \"n";
_usernamenoaccent = mostCurrent._main._username /*String*/ .replace("ñ","n");
 //BA.debugLineNum = 266;BA.debugLine="usernameNoAccent = Main.username.Replace(\"@\", \"a";
_usernamenoaccent = mostCurrent._main._username /*String*/ .replace("@","ae");
 //BA.debugLineNum = 268;BA.debugLine="If currentprojectMap = Null Or currentprojectMap";
if (_currentprojectmap== null || _currentprojectmap.IsInitialized()==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 269;BA.debugLine="currentproject = 1";
_currentproject = BA.NumberToString(1);
 //BA.debugLineNum = 270;BA.debugLine="Main.currentproject = currentproject";
mostCurrent._main._currentproject /*String*/  = _currentproject;
 //BA.debugLineNum = 271;BA.debugLine="Dim Map1 As Map";
_map1 = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 272;BA.debugLine="Map1.Initialize";
_map1.Initialize();
 //BA.debugLineNum = 273;BA.debugLine="Map1.Put(\"Id\", currentproject)";
_map1.Put((Object)("Id"),(Object)(_currentproject));
 //BA.debugLineNum = 274;BA.debugLine="DateTime.DateFormat = \"dd-MM-yyyy\"";
anywheresoftware.b4a.keywords.Common.DateTime.setDateFormat("dd-MM-yyyy");
 //BA.debugLineNum = 275;BA.debugLine="datecurrentproject = DateTime.Date(DateTime.Now";
_datecurrentproject = anywheresoftware.b4a.keywords.Common.DateTime.Date(anywheresoftware.b4a.keywords.Common.DateTime.getNow());
 //BA.debugLineNum = 277;BA.debugLine="fullidcurrentproject = usernameNoAccent & \"_\" &";
_fullidcurrentproject = _usernamenoaccent+"_"+_currentproject+"_"+_datecurrentproject;
 //BA.debugLineNum = 278;BA.debugLine="DBUtils.UpdateRecord(Starter.modulo_hidro_sqlDB";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._modulo_hidro_sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals_hidro","fullID",(Object)(_fullidcurrentproject),_map1);
 //BA.debugLineNum = 279;BA.debugLine="DBUtils.UpdateRecord(Starter.modulo_hidro_sqlDB";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._modulo_hidro_sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals_hidro","terminado",(Object)("no"),_map1);
 //BA.debugLineNum = 280;BA.debugLine="DBUtils.UpdateRecord(Starter.modulo_hidro_sqlDB";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._modulo_hidro_sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals_hidro","evalsent",(Object)("no"),_map1);
 }else {
 //BA.debugLineNum = 282;BA.debugLine="currentproject = currentprojectMap.Get(\"id\")";
_currentproject = BA.ObjectToString(_currentprojectmap.Get((Object)("id")));
 //BA.debugLineNum = 283;BA.debugLine="Main.currentproject = currentproject";
mostCurrent._main._currentproject /*String*/  = _currentproject;
 //BA.debugLineNum = 284;BA.debugLine="Dim Map1 As Map";
_map1 = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 285;BA.debugLine="Map1.Initialize";
_map1.Initialize();
 //BA.debugLineNum = 286;BA.debugLine="Map1.Put(\"Id\", currentproject)";
_map1.Put((Object)("Id"),(Object)(_currentproject));
 //BA.debugLineNum = 287;BA.debugLine="DateTime.DateFormat = \"dd-MM-yyyy\"";
anywheresoftware.b4a.keywords.Common.DateTime.setDateFormat("dd-MM-yyyy");
 //BA.debugLineNum = 288;BA.debugLine="datecurrentproject = DateTime.Date(DateTime.Now";
_datecurrentproject = anywheresoftware.b4a.keywords.Common.DateTime.Date(anywheresoftware.b4a.keywords.Common.DateTime.getNow());
 //BA.debugLineNum = 289;BA.debugLine="fullidcurrentproject = usernameNoAccent & \"_\" &";
_fullidcurrentproject = _usernamenoaccent+"_"+_currentproject+"_"+_datecurrentproject;
 //BA.debugLineNum = 290;BA.debugLine="DBUtils.UpdateRecord(Starter.modulo_hidro_sqlDB";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._modulo_hidro_sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals_hidro","fullID",(Object)(_fullidcurrentproject),_map1);
 //BA.debugLineNum = 291;BA.debugLine="DBUtils.UpdateRecord(Starter.modulo_hidro_sqlDB";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._modulo_hidro_sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals_hidro","terminado",(Object)("no"),_map1);
 //BA.debugLineNum = 292;BA.debugLine="DBUtils.UpdateRecord(Starter.modulo_hidro_sqlDB";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._modulo_hidro_sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals_hidro","evalsent",(Object)("no"),_map1);
 };
 } 
       catch (Exception e55) {
			processBA.setLastException(e55); //BA.debugLineNum = 295;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("456295486",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA)),0);
 //BA.debugLineNum = 296;BA.debugLine="ToastMessageShow(\"Hubo un error, intente de nuev";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Hubo un error, intente de nuevo"),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 297;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 299;BA.debugLine="Log(\"currentproject: \" & Main.currentproject)";
anywheresoftware.b4a.keywords.Common.LogImpl("456295490","currentproject: "+mostCurrent._main._currentproject /*String*/ ,0);
 //BA.debugLineNum = 302;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 303;BA.debugLine="Activity.LoadLayout(\"mod_hidro_carga\")";
mostCurrent._activity.LoadLayout("mod_hidro_carga",mostCurrent.activityBA);
 //BA.debugLineNum = 306;BA.debugLine="frmLocalizacion.origen = \"modulo_hidro\"";
mostCurrent._frmlocalizacion._origen /*String*/  = "modulo_hidro";
 //BA.debugLineNum = 307;BA.debugLine="StartActivity(frmLocalizacion)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._frmlocalizacion.getObject()));
 //BA.debugLineNum = 309;BA.debugLine="End Sub";
return "";
}
public static String  _enviar_mod() throws Exception{
String _rndstr = "";
 //BA.debugLineNum = 493;BA.debugLine="Sub enviar_mod";
 //BA.debugLineNum = 494;BA.debugLine="If Main.username = \"guest\" Or Main.username = \"No";
if ((mostCurrent._main._username /*String*/ ).equals("guest") || (mostCurrent._main._username /*String*/ ).equals("None")) { 
 //BA.debugLineNum = 495;BA.debugLine="ToastMessageShow(\"No estás registrado/a, pero ig";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("No estás registrado/a, pero igual se enviará tu reporte!"),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 496;BA.debugLine="Dim RndStr As String";
_rndstr = "";
 //BA.debugLineNum = 497;BA.debugLine="RndStr = utilidades.RandomString(6)";
_rndstr = mostCurrent._utilidades._randomstring /*String*/ (mostCurrent.activityBA,(int) (6));
 //BA.debugLineNum = 498;BA.debugLine="Main.username = \"guest_\" & RndStr";
mostCurrent._main._username /*String*/  = "guest_"+_rndstr;
 };
 //BA.debugLineNum = 501;BA.debugLine="ProgressBar1.Progress = 0";
mostCurrent._progressbar1.setProgress((int) (0));
 //BA.debugLineNum = 505;BA.debugLine="CheckInternet";
_checkinternet();
 //BA.debugLineNum = 506;BA.debugLine="End Sub";
return "";
}
public static void  _enviardatos_complete(appear.pnud.preservamos.httpjob _job) throws Exception{
ResumableSub_EnviarDatos_Complete rsub = new ResumableSub_EnviarDatos_Complete(null,_job);
rsub.resume(processBA, null);
}
public static class ResumableSub_EnviarDatos_Complete extends BA.ResumableSub {
public ResumableSub_EnviarDatos_Complete(appear.pnud.preservamos.mod_hidro parent,appear.pnud.preservamos.httpjob _job) {
this.parent = parent;
this._job = _job;
}
appear.pnud.preservamos.mod_hidro parent;
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
 //BA.debugLineNum = 665;BA.debugLine="Log(\"Datos enviados : \" & Job.Success)";
anywheresoftware.b4a.keywords.Common.LogImpl("457147393","Datos enviados : "+BA.ObjectToString(_job._success /*boolean*/ ),0);
 //BA.debugLineNum = 666;BA.debugLine="If Job.Success = True Then";
if (true) break;

case 1:
//if
this.state = 38;
if (_job._success /*boolean*/ ==anywheresoftware.b4a.keywords.Common.True) { 
this.state = 3;
}else {
this.state = 31;
}if (true) break;

case 3:
//C
this.state = 4;
 //BA.debugLineNum = 667;BA.debugLine="Dim ret As String";
_ret = "";
 //BA.debugLineNum = 668;BA.debugLine="Dim act As String";
_act = "";
 //BA.debugLineNum = 669;BA.debugLine="ret = Job.GetString";
_ret = _job._getstring /*String*/ ();
 //BA.debugLineNum = 670;BA.debugLine="Dim parser As JSONParser";
_parser = new anywheresoftware.b4a.objects.collections.JSONParser();
 //BA.debugLineNum = 671;BA.debugLine="parser.Initialize(ret)";
_parser.Initialize(_ret);
 //BA.debugLineNum = 672;BA.debugLine="act = parser.NextValue";
_act = BA.ObjectToString(_parser.NextValue());
 //BA.debugLineNum = 673;BA.debugLine="If act = \"Not Found\" Then";
if (true) break;

case 4:
//if
this.state = 29;
if ((_act).equals("Not Found")) { 
this.state = 6;
}else if((_act).equals("Error")) { 
this.state = 14;
}else if((_act).equals("Mod_Dato_Agregado")) { 
this.state = 22;
}if (true) break;

case 6:
//C
this.state = 7;
 //BA.debugLineNum = 674;BA.debugLine="If Main.lang = \"es\" Then";
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
 //BA.debugLineNum = 675;BA.debugLine="ToastMessageShow(\"Error subiendo el dato\", Tru";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Error subiendo el dato"),anywheresoftware.b4a.keywords.Common.True);
 if (true) break;

case 11:
//C
this.state = 12;
 //BA.debugLineNum = 677;BA.debugLine="ToastMessageShow(\"Error loading markers\", True";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Error loading markers"),anywheresoftware.b4a.keywords.Common.True);
 if (true) break;

case 12:
//C
this.state = 29;
;
 //BA.debugLineNum = 679;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 if (true) break;

case 14:
//C
this.state = 15;
 //BA.debugLineNum = 681;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 //BA.debugLineNum = 682;BA.debugLine="Msgbox2Async(\"Hubo un problema con el envío, pr";
anywheresoftware.b4a.keywords.Common.Msgbox2Async(BA.ObjectToCharSequence("Hubo un problema con el envío, probablemente por la baja señal de internet en tu zona. ¿Deseas intentar de nuevo, o guardar el dato y enviarlo luego?"),BA.ObjectToCharSequence("Acceso a internet débil"),"Intenta de nuevo","","Guarda el dato, lo envío luego",(anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper(), (android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null)),processBA,anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 683;BA.debugLine="Wait For Msgbox_Result (Result As Int)";
anywheresoftware.b4a.keywords.Common.WaitFor("msgbox_result", processBA, this, null);
this.state = 39;
return;
case 39:
//C
this.state = 15;
_result = (Integer) result[0];
;
 //BA.debugLineNum = 684;BA.debugLine="If Result = DialogResponse.POSITIVE Then";
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
 //BA.debugLineNum = 685;BA.debugLine="EnvioDatos(Main.currentproject)";
_enviodatos(parent.mostCurrent._main._currentproject /*String*/ );
 if (true) break;

case 19:
//C
this.state = 20;
 //BA.debugLineNum = 687;BA.debugLine="ToastMessageShow(\"Se guardó el dato\", True)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Se guardó el dato"),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 688;BA.debugLine="TimerEnvio.Enabled = False";
parent._timerenvio.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 689;BA.debugLine="Activity.RemoveAllViews";
parent.mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 690;BA.debugLine="Activity.finish";
parent.mostCurrent._activity.Finish();
 if (true) break;

case 20:
//C
this.state = 29;
;
 if (true) break;

case 22:
//C
this.state = 23;
 //BA.debugLineNum = 694;BA.debugLine="Dim nd As Map";
_nd = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 695;BA.debugLine="nd = parser.NextObject";
_nd = _parser.NextObject();
 //BA.debugLineNum = 696;BA.debugLine="Dim serverID As String";
_serverid = "";
 //BA.debugLineNum = 697;BA.debugLine="serverID = nd.Get(\"serverId\")";
_serverid = BA.ObjectToString(_nd.Get((Object)("serverId")));
 //BA.debugLineNum = 700;BA.debugLine="Dim Map1 As Map";
_map1 = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 701;BA.debugLine="Map1.Initialize";
_map1.Initialize();
 //BA.debugLineNum = 702;BA.debugLine="Map1.Put(\"Id\", Main.currentproject)";
_map1.Put((Object)("Id"),(Object)(parent.mostCurrent._main._currentproject /*String*/ ));
 //BA.debugLineNum = 703;BA.debugLine="DBUtils.UpdateRecord(Starter.modulo_hidro_sqlDB";
parent.mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,parent.mostCurrent._starter._modulo_hidro_sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals_hidro","evalsent",(Object)("si"),_map1);
 //BA.debugLineNum = 704;BA.debugLine="DBUtils.UpdateRecord(Starter.modulo_hidro_sqlDB";
parent.mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,parent.mostCurrent._starter._modulo_hidro_sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals_hidro","serverId",(Object)(_serverid),_map1);
 //BA.debugLineNum = 705;BA.debugLine="If Main.lang = \"es\" Then";
if (true) break;

case 23:
//if
this.state = 28;
if ((parent.mostCurrent._main._lang /*String*/ ).equals("es")) { 
this.state = 25;
}else if((parent.mostCurrent._main._lang /*String*/ ).equals("en")) { 
this.state = 27;
}if (true) break;

case 25:
//C
this.state = 28;
 //BA.debugLineNum = 706;BA.debugLine="ToastMessageShow(\"Datos enviados, enviando fot";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Datos enviados, enviando fotos"),anywheresoftware.b4a.keywords.Common.False);
 if (true) break;

case 27:
//C
this.state = 28;
 //BA.debugLineNum = 708;BA.debugLine="ToastMessageShow(\"Report sent, sending photos\"";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Report sent, sending photos"),anywheresoftware.b4a.keywords.Common.False);
 if (true) break;

case 28:
//C
this.state = 29;
;
 //BA.debugLineNum = 712;BA.debugLine="EnviarFotos";
_enviarfotos();
 if (true) break;

case 29:
//C
this.state = 38;
;
 if (true) break;

case 31:
//C
this.state = 32;
 //BA.debugLineNum = 715;BA.debugLine="Log(\"envio datos not ok\")";
anywheresoftware.b4a.keywords.Common.LogImpl("457147443","envio datos not ok",0);
 //BA.debugLineNum = 716;BA.debugLine="If Main.lang = \"es\" Then";
if (true) break;

case 32:
//if
this.state = 37;
if ((parent.mostCurrent._main._lang /*String*/ ).equals("es")) { 
this.state = 34;
}else if((parent.mostCurrent._main._lang /*String*/ ).equals("en")) { 
this.state = 36;
}if (true) break;

case 34:
//C
this.state = 37;
 //BA.debugLineNum = 717;BA.debugLine="MsgboxAsync(\"Al parecer hay un problema en nues";
anywheresoftware.b4a.keywords.Common.MsgboxAsync(BA.ObjectToCharSequence("Al parecer hay un problema en nuestros servidores, lo solucionaremos pronto!"),BA.ObjectToCharSequence("Mala mía"),processBA);
 if (true) break;

case 36:
//C
this.state = 37;
 //BA.debugLineNum = 719;BA.debugLine="MsgboxAsync(\"There seems to be a problem with o";
anywheresoftware.b4a.keywords.Common.MsgboxAsync(BA.ObjectToCharSequence("There seems to be a problem with our servers, we will solve it soon!"),BA.ObjectToCharSequence("My bad"),processBA);
 if (true) break;

case 37:
//C
this.state = 38;
;
 //BA.debugLineNum = 721;BA.debugLine="btnOkCarga.Enabled = True";
parent.mostCurrent._btnokcarga.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 722;BA.debugLine="btnOkCarga.Text = \"¡Finalizar y enviar!\"";
parent.mostCurrent._btnokcarga.setText(BA.ObjectToCharSequence("¡Finalizar y enviar!"));
 if (true) break;

case 38:
//C
this.state = -1;
;
 //BA.debugLineNum = 725;BA.debugLine="Job.Release";
_job._release /*String*/ ();
 //BA.debugLineNum = 726;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public static String  _enviarfotos() throws Exception{
String _usr = "";
String _pss = "";
String _filetoupload = "";
 //BA.debugLineNum = 733;BA.debugLine="Sub EnviarFotos";
 //BA.debugLineNum = 736;BA.debugLine="If foto1_path <> \"null\" Then";
if ((_foto1_path).equals("null") == false) { 
 //BA.debugLineNum = 737;BA.debugLine="totalFotos = totalFotos + 1";
_totalfotos = (int) (_totalfotos+1);
 //BA.debugLineNum = 738;BA.debugLine="ProgressBar1.Visible = False";
mostCurrent._progressbar1.setVisible(anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 742;BA.debugLine="TimerEnvio.Initialize(\"TimerEnvio\", 1000)";
_timerenvio.Initialize(processBA,"TimerEnvio",(long) (1000));
 //BA.debugLineNum = 743;BA.debugLine="TimerEnvio.Enabled = True";
_timerenvio.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 745;BA.debugLine="Dim usr As String";
_usr = "";
 //BA.debugLineNum = 746;BA.debugLine="Dim pss As String";
_pss = "";
 //BA.debugLineNum = 747;BA.debugLine="usr = \"\"";
_usr = "";
 //BA.debugLineNum = 748;BA.debugLine="pss = \"\"";
_pss = "";
 //BA.debugLineNum = 750;BA.debugLine="Up1.B4A_log=True";
_up1.B4A_log = anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 751;BA.debugLine="Up1.Initialize(\"Up1\")";
_up1.Initialize(processBA,"Up1");
 //BA.debugLineNum = 754;BA.debugLine="If File.Exists(Starter.savedir, foto1_path & \".jp";
if (anywheresoftware.b4a.keywords.Common.File.Exists(mostCurrent._starter._savedir /*String*/ ,_foto1_path+".jpg") && _foto1sent==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 755;BA.debugLine="Log(\"Enviando foto\")";
anywheresoftware.b4a.keywords.Common.LogImpl("457212950","Enviando foto",0);
 //BA.debugLineNum = 756;BA.debugLine="Log(\"Enviando foto 1 \")";
anywheresoftware.b4a.keywords.Common.LogImpl("457212951","Enviando foto 1 ",0);
 //BA.debugLineNum = 757;BA.debugLine="Dim filetoUpload As String";
_filetoupload = "";
 //BA.debugLineNum = 758;BA.debugLine="filetoUpload = Starter.savedir & \"/\" & foto1_pat";
_filetoupload = mostCurrent._starter._savedir /*String*/ +"/"+_foto1_path+".jpg";
 //BA.debugLineNum = 759;BA.debugLine="Up1.doFileUpload(ProgressBar1,Null,filetoUpload,";
_up1.doFileUpload(processBA,(android.widget.ProgressBar)(mostCurrent._progressbar1.getObject()),(android.widget.TextView)(anywheresoftware.b4a.keywords.Common.Null),_filetoupload,mostCurrent._main._serverpath /*String*/ +"/"+mostCurrent._main._serverconnectionfolder /*String*/ +"/upload_file.php?usr="+_usr+"&pss="+_pss);
 //BA.debugLineNum = 760;BA.debugLine="ProgressBar1.Visible = False";
mostCurrent._progressbar1.setVisible(anywheresoftware.b4a.keywords.Common.False);
 }else {
 //BA.debugLineNum = 763;BA.debugLine="Log(\"no foto 1\")";
anywheresoftware.b4a.keywords.Common.LogImpl("457212958","no foto 1",0);
 };
 //BA.debugLineNum = 767;BA.debugLine="End Sub";
return "";
}
public static String  _enviodatos(String _proyectonumero) throws Exception{
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
appear.pnud.preservamos.downloadservice._downloaddata _dd = null;
 //BA.debugLineNum = 540;BA.debugLine="Sub EnvioDatos(proyectonumero As String)";
 //BA.debugLineNum = 542;BA.debugLine="proyectoIDenviar = proyectonumero";
mostCurrent._proyectoidenviar = _proyectonumero;
 //BA.debugLineNum = 544;BA.debugLine="Dim username, useremail, dateandtime,nombresitio,";
_username = "";
_useremail = "";
_dateandtime = "";
_nombresitio = "";
mostCurrent._tiporio = "";
_lat = "";
_lng = "";
_gpsdetect = "";
_wifidetect = "";
_mapadetect = "";
_partido = "";
 //BA.debugLineNum = 545;BA.debugLine="Dim valorcalidad, valorNS, valorind1,valorind2,va";
_valorcalidad = "";
_valorns = "";
mostCurrent._valorind1 = "";
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
 //BA.debugLineNum = 548;BA.debugLine="Dim notas,bingo As String";
_notas = "";
_bingo = "";
 //BA.debugLineNum = 549;BA.debugLine="Dim terminado, privado,estadovalidacion, deviceID";
_terminado = "";
_privado = "";
_estadovalidacion = "";
_deviceid = "";
 //BA.debugLineNum = 551;BA.debugLine="Dim datosMap As Map";
_datosmap = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 552;BA.debugLine="datosMap.Initialize";
_datosmap.Initialize();
 //BA.debugLineNum = 553;BA.debugLine="datosMap = DBUtils.ExecuteMap(Starter.modulo_hidr";
_datosmap = mostCurrent._dbutils._executemap /*anywheresoftware.b4a.objects.collections.Map*/ (mostCurrent.activityBA,mostCurrent._starter._modulo_hidro_sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"SELECT * FROM evals_hidro WHERE Id=?",new String[]{_proyectonumero});
 //BA.debugLineNum = 555;BA.debugLine="If datosMap = Null Or datosMap.IsInitialized = Fa";
if (_datosmap== null || _datosmap.IsInitialized()==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 556;BA.debugLine="ToastMessageShow(\"Error cargando el análisis\", F";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Error cargando el análisis"),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 557;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 //BA.debugLineNum = 558;BA.debugLine="Return";
if (true) return "";
 }else {
 //BA.debugLineNum = 561;BA.debugLine="username = datosMap.Get(\"usuario\")";
_username = BA.ObjectToString(_datosmap.Get((Object)("usuario")));
 //BA.debugLineNum = 562;BA.debugLine="useremail = Main.strUserEmail";
_useremail = mostCurrent._main._struseremail /*String*/ ;
 //BA.debugLineNum = 563;BA.debugLine="dateandtime = datosMap.Get(\"georeferenceddate\")";
_dateandtime = BA.ObjectToString(_datosmap.Get((Object)("georeferenceddate")));
 //BA.debugLineNum = 564;BA.debugLine="nombresitio = datosMap.Get(\"nombresitio\")";
_nombresitio = BA.ObjectToString(_datosmap.Get((Object)("nombresitio")));
 //BA.debugLineNum = 565;BA.debugLine="tiporio = datosMap.Get(\"tiporio\")";
mostCurrent._tiporio = BA.ObjectToString(_datosmap.Get((Object)("tiporio")));
 //BA.debugLineNum = 566;BA.debugLine="lat = datosMap.Get(\"decimallatitude\")";
_lat = BA.ObjectToString(_datosmap.Get((Object)("decimallatitude")));
 //BA.debugLineNum = 567;BA.debugLine="lng = datosMap.Get(\"decimallongitude\")";
_lng = BA.ObjectToString(_datosmap.Get((Object)("decimallongitude")));
 //BA.debugLineNum = 568;BA.debugLine="gpsdetect = datosMap.Get(\"gpsdetect\")";
_gpsdetect = BA.ObjectToString(_datosmap.Get((Object)("gpsdetect")));
 //BA.debugLineNum = 569;BA.debugLine="wifidetect = datosMap.Get(\"wifidetect\")";
_wifidetect = BA.ObjectToString(_datosmap.Get((Object)("wifidetect")));
 //BA.debugLineNum = 570;BA.debugLine="mapadetect = datosMap.Get(\"mapadetect\")";
_mapadetect = BA.ObjectToString(_datosmap.Get((Object)("mapadetect")));
 //BA.debugLineNum = 571;BA.debugLine="partido = Geopartido";
_partido = _geopartido;
 //BA.debugLineNum = 572;BA.debugLine="valorcalidad = datosMap.Get(\"valorcalidad\")";
_valorcalidad = BA.ObjectToString(_datosmap.Get((Object)("valorcalidad")));
 //BA.debugLineNum = 573;BA.debugLine="valorNS = datosMap.Get(\"valorind20\")";
_valorns = BA.ObjectToString(_datosmap.Get((Object)("valorind20")));
 //BA.debugLineNum = 574;BA.debugLine="valorind1 = datosMap.Get(\"valorind1\")";
mostCurrent._valorind1 = BA.ObjectToString(_datosmap.Get((Object)("valorind1")));
 //BA.debugLineNum = 575;BA.debugLine="valorind2 = datosMap.Get(\"valorind2\")";
_valorind2 = BA.ObjectToString(_datosmap.Get((Object)("valorind2")));
 //BA.debugLineNum = 576;BA.debugLine="valorind3 = datosMap.Get(\"valorind3\")";
_valorind3 = BA.ObjectToString(_datosmap.Get((Object)("valorind3")));
 //BA.debugLineNum = 577;BA.debugLine="valorind4 = datosMap.Get(\"valorind4\")";
_valorind4 = BA.ObjectToString(_datosmap.Get((Object)("valorind4")));
 //BA.debugLineNum = 578;BA.debugLine="valorind5 = datosMap.Get(\"valorind5\")";
_valorind5 = BA.ObjectToString(_datosmap.Get((Object)("valorind5")));
 //BA.debugLineNum = 579;BA.debugLine="valorind6 = datosMap.Get(\"valorind6\")";
_valorind6 = BA.ObjectToString(_datosmap.Get((Object)("valorind6")));
 //BA.debugLineNum = 580;BA.debugLine="valorind7 = datosMap.Get(\"valorind7\")";
_valorind7 = BA.ObjectToString(_datosmap.Get((Object)("valorind7")));
 //BA.debugLineNum = 581;BA.debugLine="valorind8 = datosMap.Get(\"valorind8\")";
_valorind8 = BA.ObjectToString(_datosmap.Get((Object)("valorind8")));
 //BA.debugLineNum = 582;BA.debugLine="valorind9 = datosMap.Get(\"valorind9\")";
_valorind9 = BA.ObjectToString(_datosmap.Get((Object)("valorind9")));
 //BA.debugLineNum = 583;BA.debugLine="valorind10 = datosMap.Get(\"valorind10\")";
_valorind10 = BA.ObjectToString(_datosmap.Get((Object)("valorind10")));
 //BA.debugLineNum = 584;BA.debugLine="valorind11 = datosMap.Get(\"valorind11\")";
_valorind11 = BA.ObjectToString(_datosmap.Get((Object)("valorind11")));
 //BA.debugLineNum = 585;BA.debugLine="valorind12 = datosMap.Get(\"valorind12\")";
_valorind12 = BA.ObjectToString(_datosmap.Get((Object)("valorind12")));
 //BA.debugLineNum = 586;BA.debugLine="valorind13 = datosMap.Get(\"valorind13\")";
_valorind13 = BA.ObjectToString(_datosmap.Get((Object)("valorind13")));
 //BA.debugLineNum = 587;BA.debugLine="valorind14 = datosMap.Get(\"valorind14\")";
_valorind14 = BA.ObjectToString(_datosmap.Get((Object)("valorind14")));
 //BA.debugLineNum = 588;BA.debugLine="valorind15 = datosMap.Get(\"valorind15\")";
_valorind15 = BA.ObjectToString(_datosmap.Get((Object)("valorind15")));
 //BA.debugLineNum = 589;BA.debugLine="valorind16 = datosMap.Get(\"valorind16\")";
_valorind16 = BA.ObjectToString(_datosmap.Get((Object)("valorind16")));
 //BA.debugLineNum = 590;BA.debugLine="valorind17 = datosMap.Get(\"valorind17\")";
_valorind17 = BA.ObjectToString(_datosmap.Get((Object)("valorind17")));
 //BA.debugLineNum = 591;BA.debugLine="valorind18 = datosMap.Get(\"valorind18\")";
_valorind18 = BA.ObjectToString(_datosmap.Get((Object)("valorind18")));
 //BA.debugLineNum = 592;BA.debugLine="valorind19 = datosMap.Get(\"valorind19\")";
_valorind19 = BA.ObjectToString(_datosmap.Get((Object)("valorind19")));
 //BA.debugLineNum = 594;BA.debugLine="notas = datosMap.Get(\"notas\")";
_notas = BA.ObjectToString(_datosmap.Get((Object)("notas")));
 //BA.debugLineNum = 595;BA.debugLine="bingo = datosMap.Get(\"bingo\")";
_bingo = BA.ObjectToString(_datosmap.Get((Object)("bingo")));
 //BA.debugLineNum = 596;BA.debugLine="foto1 = datosMap.Get(\"foto1\")";
mostCurrent._foto1 = BA.ObjectToString(_datosmap.Get((Object)("foto1")));
 //BA.debugLineNum = 597;BA.debugLine="terminado = datosMap.Get(\"terminado\")";
_terminado = BA.ObjectToString(_datosmap.Get((Object)("terminado")));
 //BA.debugLineNum = 598;BA.debugLine="privado = datosMap.Get(\"privado\")";
_privado = BA.ObjectToString(_datosmap.Get((Object)("privado")));
 //BA.debugLineNum = 599;BA.debugLine="If privado = Null Or privado = \"null\" Or privado";
if (_privado== null || (_privado).equals("null") || (_privado).equals("")) { 
 //BA.debugLineNum = 600;BA.debugLine="privado = \"no\"";
_privado = "no";
 };
 //BA.debugLineNum = 602;BA.debugLine="estadovalidacion = datosMap.Get(\"estadovalidacio";
_estadovalidacion = BA.ObjectToString(_datosmap.Get((Object)("estadovalidacion")));
 //BA.debugLineNum = 603;BA.debugLine="If estadovalidacion = \"null\" Then";
if ((_estadovalidacion).equals("null")) { 
 //BA.debugLineNum = 604;BA.debugLine="estadovalidacion = \"No Verificado\"";
_estadovalidacion = "No Verificado";
 };
 //BA.debugLineNum = 606;BA.debugLine="deviceID = datosMap.Get(\"deviceID\")";
_deviceid = BA.ObjectToString(_datosmap.Get((Object)("deviceID")));
 //BA.debugLineNum = 607;BA.debugLine="If deviceID = Null Or deviceID = \"\" Or deviceID";
if (_deviceid== null || (_deviceid).equals("") || (_deviceid).equals("null")) { 
 //BA.debugLineNum = 608;BA.debugLine="deviceID = utilidades.GetDeviceId";
_deviceid = mostCurrent._utilidades._getdeviceid /*String*/ (mostCurrent.activityBA);
 };
 };
 //BA.debugLineNum = 614;BA.debugLine="Log(\"Comienza envio de datos\")";
anywheresoftware.b4a.keywords.Common.LogImpl("457081930","Comienza envio de datos",0);
 //BA.debugLineNum = 615;BA.debugLine="Dim dd As DownloadData";
_dd = new appear.pnud.preservamos.downloadservice._downloaddata();
 //BA.debugLineNum = 616;BA.debugLine="dd.url = Main.serverPath & \"/\" & Main.serverConne";
_dd.url /*String*/  = mostCurrent._main._serverpath /*String*/ +"/"+mostCurrent._main._serverconnectionfolder /*String*/ +"/add_mod_hidro.php?"+"username="+_username+"&"+"useremail="+_useremail+"&"+"deviceID="+mostCurrent._main._deviceid /*String*/ +"&"+"dateandtime="+_dateandtime+"&"+"nombresitio="+_nombresitio+"&"+"lat="+_lat+"&"+"lng="+_lng+"&"+"tiporio="+mostCurrent._tiporio+"&"+"indice="+_valorcalidad+"&"+"precision="+_valorns+"&"+"valorind1="+mostCurrent._valorind1+"&"+"valorind2="+_valorind2+"&"+"valorind3="+_valorind3+"&"+"valorind4="+_valorind4+"&"+"valorind5="+_valorind5+"&"+"valorind6="+_valorind6+"&"+"valorind7="+_valorind7+"&"+"valorind8="+_valorind8+"&"+"valorind9="+_valorind9+"&"+"valorind10="+_valorind10+"&"+"valorind11="+_valorind11+"&"+"valorind12="+_valorind12+"&"+"valorind13="+_valorind13+"&"+"valorind14="+_valorind14+"&"+"valorind15="+_valorind15+"&"+"valorind16="+_valorind16+"&"+"valorind17="+_valorind17+"&"+"valorind18="+_valorind18+"&"+"valorind19="+_valorind19+"&"+"foto1path="+mostCurrent._foto1+"&"+"terminado="+_terminado+"&"+"verificado="+_estadovalidacion+"&"+"privado="+_privado+"&"+"bingo="+_bingo+"&"+"notas="+_notas+"&"+"partido="+_partido+"&"+"mapadetect="+_mapadetect+"&"+"wifidetect="+_wifidetect+"&"+"gpsdetect="+_gpsdetect;
 //BA.debugLineNum = 657;BA.debugLine="dd.EventName = \"EnviarDatos\"";
_dd.EventName /*String*/  = "EnviarDatos";
 //BA.debugLineNum = 658;BA.debugLine="Log(dd.url)";
anywheresoftware.b4a.keywords.Common.LogImpl("457081974",_dd.url /*String*/ ,0);
 //BA.debugLineNum = 659;BA.debugLine="dd.Target = Me";
_dd.Target /*Object*/  = mod_hidro.getObject();
 //BA.debugLineNum = 660;BA.debugLine="CallSubDelayed2(DownloadService, \"StartDownload\",";
anywheresoftware.b4a.keywords.Common.CallSubDelayed2(processBA,(Object)(mostCurrent._downloadservice.getObject()),"StartDownload",(Object)(_dd));
 //BA.debugLineNum = 663;BA.debugLine="End Sub";
return "";
}
public static String  _fondogris_click() throws Exception{
 //BA.debugLineNum = 406;BA.debugLine="Private Sub fondogris_Click";
 //BA.debugLineNum = 409;BA.debugLine="End Sub";
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
 //BA.debugLineNum = 37;BA.debugLine="Private btnOkBasico1 As Button";
mostCurrent._btnokbasico1 = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 38;BA.debugLine="Dim coordenadas_string As String";
mostCurrent._coordenadas_string = "";
 //BA.debugLineNum = 40;BA.debugLine="Private lblFecha As Label";
mostCurrent._lblfecha = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 41;BA.debugLine="Private lblCoordenadasCarga As Label";
mostCurrent._lblcoordenadascarga = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 44;BA.debugLine="Dim fondogris As Panel";
mostCurrent._fondogris = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 45;BA.debugLine="Dim panelComoFunciona As Panel";
mostCurrent._panelcomofunciona = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 49;BA.debugLine="Dim valorind1 As String";
mostCurrent._valorind1 = "";
 //BA.debugLineNum = 50;BA.debugLine="Dim tiporio As String";
mostCurrent._tiporio = "";
 //BA.debugLineNum = 53;BA.debugLine="Dim proyectoIDenviar As String";
mostCurrent._proyectoidenviar = "";
 //BA.debugLineNum = 54;BA.debugLine="Dim files As List";
mostCurrent._files = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 55;BA.debugLine="Private lblCoordenadasTitle As Label";
mostCurrent._lblcoordenadastitle = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 56;BA.debugLine="Private borderFotos1 As Label";
mostCurrent._borderfotos1 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 57;BA.debugLine="Private imgFoto1 As ImageView";
mostCurrent._imgfoto1 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 58;BA.debugLine="Private ProgressBar1 As ProgressBar";
mostCurrent._progressbar1 = new anywheresoftware.b4a.objects.ProgressBarWrapper();
 //BA.debugLineNum = 59;BA.debugLine="Dim foto1 As String";
mostCurrent._foto1 = "";
 //BA.debugLineNum = 60;BA.debugLine="Private totalFotos As Int";
_totalfotos = 0;
 //BA.debugLineNum = 61;BA.debugLine="Private foto1Sent As Boolean = False";
_foto1sent = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 62;BA.debugLine="Dim fotosEnviadas As Int";
_fotosenviadas = 0;
 //BA.debugLineNum = 63;BA.debugLine="Dim pw As PhoneWakeState";
mostCurrent._pw = new anywheresoftware.b4a.phone.Phone.PhoneWakeState();
 //BA.debugLineNum = 64;BA.debugLine="Private imgListo As ImageView";
mostCurrent._imglisto = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 65;BA.debugLine="Private imgLimnimetro As ImageView";
mostCurrent._imglimnimetro = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 66;BA.debugLine="Private imgNoLimnimetro As ImageView";
mostCurrent._imgnolimnimetro = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 67;BA.debugLine="Private lblLimnimetro As Label";
mostCurrent._lbllimnimetro = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 68;BA.debugLine="Private lblNoLimnimetro As Label";
mostCurrent._lblnolimnimetro = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 69;BA.debugLine="Private imgHidro As ImageView";
mostCurrent._imghidro = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 70;BA.debugLine="Private lblComoFuncionaTitle As Label";
mostCurrent._lblcomofuncionatitle = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 71;BA.debugLine="Private lblComoFuncionaContenido As Label";
mostCurrent._lblcomofuncionacontenido = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 72;BA.debugLine="Private pnlAltura As Panel";
mostCurrent._pnlaltura = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 75;BA.debugLine="Dim barV1 As HVSeekbar";
mostCurrent._barv1 = new appear.pnud.preservamos.hvseekbar();
 //BA.debugLineNum = 76;BA.debugLine="Dim labelBarV1, labelBarV2, labelBarV3, labelBarH";
mostCurrent._labelbarv1 = new anywheresoftware.b4a.objects.LabelWrapper();
mostCurrent._labelbarv2 = new anywheresoftware.b4a.objects.LabelWrapper();
mostCurrent._labelbarv3 = new anywheresoftware.b4a.objects.LabelWrapper();
mostCurrent._labelbarh1 = new anywheresoftware.b4a.objects.LabelWrapper();
mostCurrent._labelbarh2 = new anywheresoftware.b4a.objects.LabelWrapper();
mostCurrent._labelbarh3 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 77;BA.debugLine="Dim panelSeeks As Panel";
mostCurrent._panelseeks = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 79;BA.debugLine="Private txtAltura As B4XFloatTextField";
mostCurrent._txtaltura = new appear.pnud.preservamos.b4xfloattextfield();
 //BA.debugLineNum = 80;BA.debugLine="Private imgAlturaManual As ImageView";
mostCurrent._imgalturamanual = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 81;BA.debugLine="Private lblAlturaManual As Label";
mostCurrent._lblalturamanual = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 82;BA.debugLine="Private lblAlturaManualTitle As Label";
mostCurrent._lblalturamanualtitle = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 83;BA.debugLine="Private btnOkCarga As Button";
mostCurrent._btnokcarga = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 84;BA.debugLine="End Sub";
return "";
}
public static String  _imghidro_click() throws Exception{
 //BA.debugLineNum = 419;BA.debugLine="Private Sub imgHidro_Click";
 //BA.debugLineNum = 421;BA.debugLine="End Sub";
return "";
}
public static String  _imglimnimetro_click() throws Exception{
 //BA.debugLineNum = 217;BA.debugLine="Private Sub imgLimnimetro_Click";
 //BA.debugLineNum = 218;BA.debugLine="tiporio = \"limnimetro\"";
mostCurrent._tiporio = "limnimetro";
 //BA.debugLineNum = 219;BA.debugLine="lblRojo.Left = imgLimnimetro.Left";
mostCurrent._lblrojo.setLeft(mostCurrent._imglimnimetro.getLeft());
 //BA.debugLineNum = 220;BA.debugLine="lblRojo.Visible = True";
mostCurrent._lblrojo.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 221;BA.debugLine="End Sub";
return "";
}
public static String  _imgnolimnimetro_click() throws Exception{
 //BA.debugLineNum = 222;BA.debugLine="Private Sub imgNoLimnimetro_Click";
 //BA.debugLineNum = 223;BA.debugLine="tiporio = \"nolimnimetro\"";
mostCurrent._tiporio = "nolimnimetro";
 //BA.debugLineNum = 224;BA.debugLine="lblRojo.Left = imgNoLimnimetro.Left";
mostCurrent._lblrojo.setLeft(mostCurrent._imgnolimnimetro.getLeft());
 //BA.debugLineNum = 225;BA.debugLine="lblRojo.Visible = True";
mostCurrent._lblrojo.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 226;BA.debugLine="End Sub";
return "";
}
public static String  _lblcomofuncionaentendido_click() throws Exception{
 //BA.debugLineNum = 401;BA.debugLine="Private Sub lblComoFuncionaEntendido_Click";
 //BA.debugLineNum = 402;BA.debugLine="panelComoFunciona.RemoveView";
mostCurrent._panelcomofunciona.RemoveView();
 //BA.debugLineNum = 403;BA.debugLine="fondogris.RemoveView";
mostCurrent._fondogris.RemoveView();
 //BA.debugLineNum = 404;BA.debugLine="StartActivity(mod_Hidro_Fotos)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._mod_hidro_fotos.getObject()));
 //BA.debugLineNum = 405;BA.debugLine="End Sub";
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
 //BA.debugLineNum = 137;BA.debugLine="Private Sub tabContainer_PageSelected (Position As";
 //BA.debugLineNum = 139;BA.debugLine="If Position = 2 Then";
if (_position==2) { 
 //BA.debugLineNum = 140;BA.debugLine="btnNext.Visible = True";
mostCurrent._btnnext.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 141;BA.debugLine="btnPrev.Visible = True";
mostCurrent._btnprev.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 142;BA.debugLine="lblCirculoPos4.Color = Colors.RGB(76,159,56)";
mostCurrent._lblcirculopos4.setColor(anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (76),(int) (159),(int) (56)));
 //BA.debugLineNum = 143;BA.debugLine="lblCirculoPos3.Color = Colors.RGB(139,226,63)";
mostCurrent._lblcirculopos3.setColor(anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (139),(int) (226),(int) (63)));
 //BA.debugLineNum = 144;BA.debugLine="lblCirculoPos2.Color = Colors.RGB(76,159,56)";
mostCurrent._lblcirculopos2.setColor(anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (76),(int) (159),(int) (56)));
 //BA.debugLineNum = 145;BA.debugLine="lblCirculoPos1.Color = Colors.RGB(76,159,56)";
mostCurrent._lblcirculopos1.setColor(anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (76),(int) (159),(int) (56)));
 }else if(_position==0) { 
 //BA.debugLineNum = 147;BA.debugLine="btnPrev.Visible = False";
mostCurrent._btnprev.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 148;BA.debugLine="btnNext.Visible = True";
mostCurrent._btnnext.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 149;BA.debugLine="lblCirculoPos1.Color = Colors.RGB(139,226,63)";
mostCurrent._lblcirculopos1.setColor(anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (139),(int) (226),(int) (63)));
 //BA.debugLineNum = 150;BA.debugLine="lblCirculoPos2.Color = Colors.RGB(76,159,56)";
mostCurrent._lblcirculopos2.setColor(anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (76),(int) (159),(int) (56)));
 //BA.debugLineNum = 151;BA.debugLine="lblCirculoPos3.Color = Colors.RGB(76,159,56)";
mostCurrent._lblcirculopos3.setColor(anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (76),(int) (159),(int) (56)));
 //BA.debugLineNum = 152;BA.debugLine="lblCirculoPos4.Color = Colors.RGB(76,159,56)";
mostCurrent._lblcirculopos4.setColor(anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (76),(int) (159),(int) (56)));
 }else if(_position==1) { 
 //BA.debugLineNum = 154;BA.debugLine="btnPrev.Visible = True";
mostCurrent._btnprev.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 155;BA.debugLine="btnNext.Visible = True";
mostCurrent._btnnext.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 156;BA.debugLine="lblCirculoPos2.Color = Colors.RGB(139,226,63)";
mostCurrent._lblcirculopos2.setColor(anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (139),(int) (226),(int) (63)));
 //BA.debugLineNum = 157;BA.debugLine="lblCirculoPos3.Color = Colors.RGB(76,159,56)";
mostCurrent._lblcirculopos3.setColor(anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (76),(int) (159),(int) (56)));
 //BA.debugLineNum = 158;BA.debugLine="lblCirculoPos1.Color = Colors.RGB(76,159,56)";
mostCurrent._lblcirculopos1.setColor(anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (76),(int) (159),(int) (56)));
 //BA.debugLineNum = 159;BA.debugLine="lblCirculoPos4.Color = Colors.RGB(76,159,56)";
mostCurrent._lblcirculopos4.setColor(anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (76),(int) (159),(int) (56)));
 }else if(_position==3) { 
 //BA.debugLineNum = 161;BA.debugLine="btnPrev.Visible = True";
mostCurrent._btnprev.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 162;BA.debugLine="btnNext.Visible = False";
mostCurrent._btnnext.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 163;BA.debugLine="lblCirculoPos4.Color = Colors.RGB(139,226,63)";
mostCurrent._lblcirculopos4.setColor(anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (139),(int) (226),(int) (63)));
 //BA.debugLineNum = 164;BA.debugLine="lblCirculoPos3.Color = Colors.RGB(76,159,56)";
mostCurrent._lblcirculopos3.setColor(anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (76),(int) (159),(int) (56)));
 //BA.debugLineNum = 165;BA.debugLine="lblCirculoPos1.Color = Colors.RGB(76,159,56)";
mostCurrent._lblcirculopos1.setColor(anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (76),(int) (159),(int) (56)));
 //BA.debugLineNum = 166;BA.debugLine="lblCirculoPos2.Color = Colors.RGB(76,159,56)";
mostCurrent._lblcirculopos2.setColor(anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (76),(int) (159),(int) (56)));
 };
 //BA.debugLineNum = 169;BA.debugLine="End Sub";
return "";
}
public static void  _testinternet_complete(appear.pnud.preservamos.httpjob _job) throws Exception{
ResumableSub_TestInternet_Complete rsub = new ResumableSub_TestInternet_Complete(null,_job);
rsub.resume(processBA, null);
}
public static class ResumableSub_TestInternet_Complete extends BA.ResumableSub {
public ResumableSub_TestInternet_Complete(appear.pnud.preservamos.mod_hidro parent,appear.pnud.preservamos.httpjob _job) {
this.parent = parent;
this._job = _job;
}
appear.pnud.preservamos.mod_hidro parent;
appear.pnud.preservamos.httpjob _job;
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
 //BA.debugLineNum = 518;BA.debugLine="Log(\"Chequeo de internet: \" & Job.Success)";
anywheresoftware.b4a.keywords.Common.LogImpl("457016321","Chequeo de internet: "+BA.ObjectToString(_job._success /*boolean*/ ),0);
 //BA.debugLineNum = 519;BA.debugLine="If Job.Success = True Then";
if (true) break;

case 1:
//if
this.state = 10;
if (_job._success /*boolean*/ ==anywheresoftware.b4a.keywords.Common.True) { 
this.state = 3;
}else {
this.state = 5;
}if (true) break;

case 3:
//C
this.state = 10;
 //BA.debugLineNum = 521;BA.debugLine="Job.Release";
_job._release /*String*/ ();
 //BA.debugLineNum = 523;BA.debugLine="Main.hayinternet = True";
parent.mostCurrent._main._hayinternet /*boolean*/  = anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 525;BA.debugLine="EnvioDatos(Main.currentproject)";
_enviodatos(parent.mostCurrent._main._currentproject /*String*/ );
 if (true) break;

case 5:
//C
this.state = 6;
 //BA.debugLineNum = 528;BA.debugLine="Main.hayinternet = False";
parent.mostCurrent._main._hayinternet /*boolean*/  = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 529;BA.debugLine="Msgbox2Async(\"No tenés conexión a Internet, prue";
anywheresoftware.b4a.keywords.Common.Msgbox2Async(BA.ObjectToCharSequence("No tenés conexión a Internet, prueba enviar tu dato cuando estés conectado, desde el Menú > Datos sin enviar"),BA.ObjectToCharSequence("No hay internet"),"Ok, entiendo","","",(anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper(), (android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null)),processBA,anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 530;BA.debugLine="Wait For Msgbox_Result (Result As Int)";
anywheresoftware.b4a.keywords.Common.WaitFor("msgbox_result", processBA, this, null);
this.state = 11;
return;
case 11:
//C
this.state = 6;
_result = (Integer) result[0];
;
 //BA.debugLineNum = 531;BA.debugLine="If Result = DialogResponse.POSITIVE Then";
if (true) break;

case 6:
//if
this.state = 9;
if (_result==anywheresoftware.b4a.keywords.Common.DialogResponse.POSITIVE) { 
this.state = 8;
}if (true) break;

case 8:
//C
this.state = 9;
 //BA.debugLineNum = 532;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 //BA.debugLineNum = 533;BA.debugLine="Job.Release";
_job._release /*String*/ ();
 //BA.debugLineNum = 534;BA.debugLine="Activity.Finish";
parent.mostCurrent._activity.Finish();
 //BA.debugLineNum = 535;BA.debugLine="Activity.RemoveAllViews";
parent.mostCurrent._activity.RemoveAllViews();
 if (true) break;

case 9:
//C
this.state = 10;
;
 if (true) break;

case 10:
//C
this.state = -1;
;
 //BA.debugLineNum = 538;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public static String  _timerenvio_tick() throws Exception{
anywheresoftware.b4a.objects.collections.Map _map1 = null;
 //BA.debugLineNum = 770;BA.debugLine="Sub TimerEnvio_Tick";
 //BA.debugLineNum = 771;BA.debugLine="If fotosEnviadas = totalFotos Then";
if (_fotosenviadas==_totalfotos) { 
 //BA.debugLineNum = 772;BA.debugLine="Log(\"TODAS LAS FOTOS FUERON ENVIADAS CORRECTAMEN";
anywheresoftware.b4a.keywords.Common.LogImpl("457278466","TODAS LAS FOTOS FUERON ENVIADAS CORRECTAMENTE",0);
 //BA.debugLineNum = 773;BA.debugLine="Up1.UploadKill";
_up1.UploadKill(processBA);
 //BA.debugLineNum = 775;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 776;BA.debugLine="ToastMessageShow(\"Evaluación enviada\", False)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Evaluación enviada"),anywheresoftware.b4a.keywords.Common.False);
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 778;BA.debugLine="ToastMessageShow(\"Report sent\", False)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Report sent"),anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 782;BA.debugLine="Dim Map1 As Map";
_map1 = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 783;BA.debugLine="Map1.Initialize";
_map1.Initialize();
 //BA.debugLineNum = 784;BA.debugLine="Map1.Put(\"Id\", Main.currentproject)";
_map1.Put((Object)("Id"),(Object)(mostCurrent._main._currentproject /*String*/ ));
 //BA.debugLineNum = 785;BA.debugLine="DBUtils.UpdateRecord(Starter.modulo_hidro_sqlDB,";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._modulo_hidro_sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals_hidro","foto1sent",(Object)("si"),_map1);
 //BA.debugLineNum = 787;BA.debugLine="frmFelicitaciones.tiporio = tiporio";
mostCurrent._frmfelicitaciones._tiporio /*String*/  = mostCurrent._tiporio;
 //BA.debugLineNum = 788;BA.debugLine="StartActivity(frmFelicitaciones)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._frmfelicitaciones.getObject()));
 //BA.debugLineNum = 790;BA.debugLine="TimerEnvio.Enabled = False";
_timerenvio.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 791;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 792;BA.debugLine="Activity.finish";
mostCurrent._activity.Finish();
 };
 //BA.debugLineNum = 794;BA.debugLine="End Sub";
return "";
}
public static String  _up1_sendfile(String _value) throws Exception{
 //BA.debugLineNum = 801;BA.debugLine="Sub Up1_sendFile (value As String)";
 //BA.debugLineNum = 802;BA.debugLine="Log(\"sendfile event:\" & value)";
anywheresoftware.b4a.keywords.Common.LogImpl("457409537","sendfile event:"+_value,0);
 //BA.debugLineNum = 803;BA.debugLine="If value = \"success\" Then";
if ((_value).equals("success")) { 
 //BA.debugLineNum = 805;BA.debugLine="If fotosEnviadas = 0 And totalFotos >= 1 Then";
if (_fotosenviadas==0 && _totalfotos>=1) { 
 //BA.debugLineNum = 806;BA.debugLine="fotosEnviadas = 1";
_fotosenviadas = (int) (1);
 //BA.debugLineNum = 807;BA.debugLine="Return";
if (true) return "";
 };
 }else if((_value).equals("Error!")) { 
 //BA.debugLineNum = 811;BA.debugLine="Log(\"FOTO error\")";
anywheresoftware.b4a.keywords.Common.LogImpl("457409546","FOTO error",0);
 //BA.debugLineNum = 812;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 813;BA.debugLine="MsgboxAsync(\"Ha habido un error en el envío. Re";
anywheresoftware.b4a.keywords.Common.MsgboxAsync(BA.ObjectToCharSequence("Ha habido un error en el envío. Revisa tu conexión a Internet e intenta de nuevo desde 'Datos sin enviar'"),BA.ObjectToCharSequence("Oops!"),processBA);
 };
 //BA.debugLineNum = 815;BA.debugLine="Up1.UploadKill";
_up1.UploadKill(processBA);
 //BA.debugLineNum = 816;BA.debugLine="TimerEnvio.Enabled = False";
_timerenvio.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 817;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 818;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 819;BA.debugLine="Activity_Create(False)";
_activity_create(anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 822;BA.debugLine="End Sub";
return "";
}
public static String  _up1_statusupload(String _value) throws Exception{
 //BA.debugLineNum = 797;BA.debugLine="Sub Up1_statusUpload (value As String)";
 //BA.debugLineNum = 799;BA.debugLine="End Sub";
return "";
}
public static String  _up1_statusvisible(boolean _onoff,String _value) throws Exception{
 //BA.debugLineNum = 823;BA.debugLine="Sub Up1_statusVISIBLE (onoff As Boolean,value As S";
 //BA.debugLineNum = 825;BA.debugLine="End Sub";
return "";
}
}
