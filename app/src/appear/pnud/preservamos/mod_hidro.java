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
 //BA.debugLineNum = 107;BA.debugLine="Sub Activity_KeyPress (KeyCode As Int) As Boolean";
 //BA.debugLineNum = 108;BA.debugLine="If KeyCode = KeyCodes.KEYCODE_BACK Then";
if (_keycode==anywheresoftware.b4a.keywords.Common.KeyCodes.KEYCODE_BACK) { 
 //BA.debugLineNum = 109;BA.debugLine="closeAppMsgBox";
_closeappmsgbox();
 //BA.debugLineNum = 110;BA.debugLine="Return True";
if (true) return anywheresoftware.b4a.keywords.Common.True;
 }else {
 //BA.debugLineNum = 112;BA.debugLine="Return False";
if (true) return anywheresoftware.b4a.keywords.Common.False;
 };
 //BA.debugLineNum = 114;BA.debugLine="End Sub";
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
 //BA.debugLineNum = 325;BA.debugLine="Sub ActualizarCoordenadas";
 //BA.debugLineNum = 326;BA.debugLine="coordenadas_string = \"Coordenadas: \" & Round2(lat";
mostCurrent._coordenadas_string = "Coordenadas: "+BA.NumberToString(anywheresoftware.b4a.keywords.Common.Round2((double)(Double.parseDouble(_lat)),(int) (3)))+" | "+BA.NumberToString(anywheresoftware.b4a.keywords.Common.Round2((double)(Double.parseDouble(_lng)),(int) (3)));
 //BA.debugLineNum = 327;BA.debugLine="lblCoordenadasCarga.Text = coordenadas_string";
mostCurrent._lblcoordenadascarga.setText(BA.ObjectToCharSequence(mostCurrent._coordenadas_string));
 //BA.debugLineNum = 328;BA.debugLine="If foto1 = \"\" Then";
if ((mostCurrent._foto1).equals("")) { 
 //BA.debugLineNum = 330;BA.debugLine="If tiporio = \"limnimetro\" Then";
if ((mostCurrent._tiporio).equals("limnimetro")) { 
 //BA.debugLineNum = 331;BA.debugLine="pnlAltura.Visible = True";
mostCurrent._pnlaltura.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 332;BA.debugLine="panelSeeks.Visible = False";
mostCurrent._panelseeks.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 333;BA.debugLine="lblAlturaManual.Visible = False";
mostCurrent._lblalturamanual.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 334;BA.debugLine="lblAlturaManualTitle.Visible = False";
mostCurrent._lblalturamanualtitle.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 335;BA.debugLine="imgAlturaManual.visible = False";
mostCurrent._imgalturamanual.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 337;BA.debugLine="fondogris.Initialize(\"fondogris\")";
mostCurrent._fondogris.Initialize(mostCurrent.activityBA,"fondogris");
 //BA.debugLineNum = 338;BA.debugLine="fondogris.Color = Colors.ARGB(150,0,0,0)";
mostCurrent._fondogris.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (150),(int) (0),(int) (0),(int) (0)));
 //BA.debugLineNum = 339;BA.debugLine="Activity.AddView(fondogris, 0, 0, 100%x, 100%y)";
mostCurrent._activity.AddView((android.view.View)(mostCurrent._fondogris.getObject()),(int) (0),(int) (0),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA));
 //BA.debugLineNum = 341;BA.debugLine="panelComoFunciona.Initialize(\"\")";
mostCurrent._panelcomofunciona.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 342;BA.debugLine="panelComoFunciona.LoadLayout(\"dialog_comoFuncio";
mostCurrent._panelcomofunciona.LoadLayout("dialog_comoFunciona",mostCurrent.activityBA);
 //BA.debugLineNum = 343;BA.debugLine="Activity.AddView(panelComoFunciona, 10%x, 20%y,";
mostCurrent._activity.AddView((android.view.View)(mostCurrent._panelcomofunciona.getObject()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (10),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (20),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (80),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (80),mostCurrent.activityBA));
 //BA.debugLineNum = 344;BA.debugLine="lblComoFuncionaContenido.Text = \"Si encontraste";
mostCurrent._lblcomofuncionacontenido.setText(BA.ObjectToCharSequence("Si encontraste una varilla blanca y negra para medir la altura del río (limnímetro), toma una foto adonde se vea tanto la varilla como la altura del agua."));
 //BA.debugLineNum = 345;BA.debugLine="lblComoFuncionaTitle.Text = \"¡Encontraste un li";
mostCurrent._lblcomofuncionatitle.setText(BA.ObjectToCharSequence("¡Encontraste un limnímetro!"));
 }else {
 //BA.debugLineNum = 348;BA.debugLine="pnlAltura.Visible = False";
mostCurrent._pnlaltura.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 349;BA.debugLine="panelSeeks.Visible = True";
mostCurrent._panelseeks.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 350;BA.debugLine="lblAlturaManual.Visible = True";
mostCurrent._lblalturamanual.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 351;BA.debugLine="imgAlturaManual.visible = True";
mostCurrent._imgalturamanual.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 352;BA.debugLine="lblAlturaManualTitle.Visible = True";
mostCurrent._lblalturamanualtitle.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 354;BA.debugLine="barV1.Initialize(Me, panelSeeks, \"barV1\")";
mostCurrent._barv1._initialize /*String*/ (mostCurrent.activityBA,mod_hidro.getObject(),(Object)(mostCurrent._panelseeks.getObject()),"barV1");
 //BA.debugLineNum = 355;BA.debugLine="barV1.SetColors(Colors.LightGray, Colors.ARGB(2";
mostCurrent._barv1._setcolors /*String*/ (anywheresoftware.b4a.keywords.Common.Colors.LightGray,anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (66),(int) (132),(int) (246)),anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (28),(int) (52),(int) (94)),anywheresoftware.b4a.keywords.Common.Colors.Gray,anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 356;BA.debugLine="barV1.SetInitValues(0, 100, 50) 'V min, V max,";
mostCurrent._barv1._setinitvalues /*String*/ ((int) (0),(int) (100),(int) (50));
 //BA.debugLineNum = 357;BA.debugLine="barV1.SetShape(barV1.cursorType_RECTANGLE, 70di";
mostCurrent._barv1._setshape /*String*/ (mostCurrent._barv1._cursortype_rectangle /*String*/ (),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (70)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (2)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (70)));
 //BA.debugLineNum = 358;BA.debugLine="barV1.ShowBar(0, 0, 70dip,imgAlturaManual.Heigh";
mostCurrent._barv1._showbar /*String*/ ((int) (0),(int) (0),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (70)),mostCurrent._imgalturamanual.getHeight(),mostCurrent._barv1._orientation_vertical /*String*/ ());
 //BA.debugLineNum = 359;BA.debugLine="labelBarV1.Initialize(\"\")";
mostCurrent._labelbarv1.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 361;BA.debugLine="panelSeeks.AddView(labelBarV1, 17%x, 5%x, 18%x,";
mostCurrent._panelseeks.AddView((android.view.View)(mostCurrent._labelbarv1.getObject()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (17),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (5),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (18),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (8),mostCurrent.activityBA));
 //BA.debugLineNum = 363;BA.debugLine="StartActivity(mod_Hidro_Fotos)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._mod_hidro_fotos.getObject()));
 };
 };
 //BA.debugLineNum = 367;BA.debugLine="End Sub";
return "";
}
public static String  _actualizarfotos() throws Exception{
 //BA.debugLineNum = 399;BA.debugLine="Sub ActualizarFotos";
 //BA.debugLineNum = 400;BA.debugLine="If tiporio = \"limnimetro\" Then";
if ((mostCurrent._tiporio).equals("limnimetro")) { 
 //BA.debugLineNum = 401;BA.debugLine="imgHidro.Bitmap = Null";
mostCurrent._imghidro.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null));
 //BA.debugLineNum = 402;BA.debugLine="imgHidro.Bitmap = LoadBitmapSample(Starter.saved";
mostCurrent._imghidro.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(mostCurrent._starter._savedir /*String*/ ,_foto1_path+".jpg",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 };
 //BA.debugLineNum = 405;BA.debugLine="End Sub";
return "";
}
public static String  _barv1_valuechanged(int _value) throws Exception{
 //BA.debugLineNum = 369;BA.debugLine="Sub barV1_ValueChanged(Value As Int)";
 //BA.debugLineNum = 370;BA.debugLine="labelBarV1.Text = \"\"";
mostCurrent._labelbarv1.setText(BA.ObjectToCharSequence(""));
 //BA.debugLineNum = 371;BA.debugLine="If Value =0 Then";
if (_value==0) { 
 //BA.debugLineNum = 372;BA.debugLine="lblAlturaManual.Text= \"Río seco\"";
mostCurrent._lblalturamanual.setText(BA.ObjectToCharSequence("Río seco"));
 //BA.debugLineNum = 373;BA.debugLine="imgAlturaManual.Bitmap = Null";
mostCurrent._imgalturamanual.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null));
 //BA.debugLineNum = 374;BA.debugLine="imgAlturaManual.Bitmap = LoadBitmapSample(File.D";
mostCurrent._imgalturamanual.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"hidro_seco.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 }else if(_value<=10 && _value>0) { 
 //BA.debugLineNum = 376;BA.debugLine="lblAlturaManual.Text= \"Río casi seco\"";
mostCurrent._lblalturamanual.setText(BA.ObjectToCharSequence("Río casi seco"));
 //BA.debugLineNum = 377;BA.debugLine="imgAlturaManual.Bitmap = Null";
mostCurrent._imgalturamanual.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null));
 //BA.debugLineNum = 378;BA.debugLine="imgAlturaManual.Bitmap = LoadBitmapSample(File.D";
mostCurrent._imgalturamanual.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"hidro_muybajo.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 }else if(_value>10 && _value<=50) { 
 //BA.debugLineNum = 380;BA.debugLine="lblAlturaManual.Text= \"Río con altura normal\"";
mostCurrent._lblalturamanual.setText(BA.ObjectToCharSequence("Río con altura normal"));
 //BA.debugLineNum = 381;BA.debugLine="imgAlturaManual.Bitmap = Null";
mostCurrent._imgalturamanual.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null));
 //BA.debugLineNum = 382;BA.debugLine="imgAlturaManual.Bitmap = LoadBitmapSample(File.D";
mostCurrent._imgalturamanual.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"hidro_bajo.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 }else if(_value>50 && _value<=80) { 
 //BA.debugLineNum = 384;BA.debugLine="lblAlturaManual.Text= \"Río alto\"";
mostCurrent._lblalturamanual.setText(BA.ObjectToCharSequence("Río alto"));
 //BA.debugLineNum = 385;BA.debugLine="imgAlturaManual.Bitmap = Null";
mostCurrent._imgalturamanual.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null));
 //BA.debugLineNum = 386;BA.debugLine="imgAlturaManual.Bitmap = LoadBitmapSample(File.D";
mostCurrent._imgalturamanual.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"hidro_normal.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 }else if(_value>80 && _value<100) { 
 //BA.debugLineNum = 388;BA.debugLine="lblAlturaManual.Text= \"Río crecido\"";
mostCurrent._lblalturamanual.setText(BA.ObjectToCharSequence("Río crecido"));
 //BA.debugLineNum = 389;BA.debugLine="imgAlturaManual.Bitmap = Null";
mostCurrent._imgalturamanual.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null));
 //BA.debugLineNum = 390;BA.debugLine="imgAlturaManual.Bitmap = LoadBitmapSample(File.D";
mostCurrent._imgalturamanual.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"hidro_alto.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 }else if(_value==100) { 
 //BA.debugLineNum = 392;BA.debugLine="lblAlturaManual.Text= \"Río desbordado\"";
mostCurrent._lblalturamanual.setText(BA.ObjectToCharSequence("Río desbordado"));
 //BA.debugLineNum = 393;BA.debugLine="imgAlturaManual.Bitmap = Null";
mostCurrent._imgalturamanual.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null));
 //BA.debugLineNum = 394;BA.debugLine="imgAlturaManual.Bitmap = LoadBitmapSample(File.D";
mostCurrent._imgalturamanual.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"hidro_desbordado.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 };
 //BA.debugLineNum = 396;BA.debugLine="End Sub";
return "";
}
public static String  _btncomenzarmodulo_click() throws Exception{
 //BA.debugLineNum = 209;BA.debugLine="Private Sub btnComenzarModulo_Click";
 //BA.debugLineNum = 210;BA.debugLine="cargar_basicos";
_cargar_basicos();
 //BA.debugLineNum = 211;BA.debugLine="End Sub";
return "";
}
public static String  _btneditcoordenadas_click() throws Exception{
 //BA.debugLineNum = 418;BA.debugLine="Private Sub btnEditCoordenadas_Click";
 //BA.debugLineNum = 420;BA.debugLine="frmLocalizacion.origen = \"modulo_hidro\"";
mostCurrent._frmlocalizacion._origen /*String*/  = "modulo_hidro";
 //BA.debugLineNum = 421;BA.debugLine="StartActivity(frmLocalizacion)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._frmlocalizacion.getObject()));
 //BA.debugLineNum = 422;BA.debugLine="End Sub";
return "";
}
public static String  _btnnext_click() throws Exception{
 //BA.debugLineNum = 169;BA.debugLine="Private Sub btnNext_Click";
 //BA.debugLineNum = 170;BA.debugLine="If tabContainer.CurrentPage = 0 Then";
if (mostCurrent._tabcontainer.getCurrentPage()==0) { 
 //BA.debugLineNum = 171;BA.debugLine="tabContainer.ScrollTo(1, True)";
mostCurrent._tabcontainer.ScrollTo((int) (1),anywheresoftware.b4a.keywords.Common.True);
 }else if(mostCurrent._tabcontainer.getCurrentPage()==1) { 
 //BA.debugLineNum = 173;BA.debugLine="tabContainer.ScrollTo(2, True)";
mostCurrent._tabcontainer.ScrollTo((int) (2),anywheresoftware.b4a.keywords.Common.True);
 }else if(mostCurrent._tabcontainer.getCurrentPage()==2) { 
 //BA.debugLineNum = 175;BA.debugLine="tabContainer.ScrollTo(3, True)";
mostCurrent._tabcontainer.ScrollTo((int) (3),anywheresoftware.b4a.keywords.Common.True);
 }else if(mostCurrent._tabcontainer.getCurrentPage()==3) { 
 //BA.debugLineNum = 177;BA.debugLine="tabContainer.ScrollTo(4, True)";
mostCurrent._tabcontainer.ScrollTo((int) (4),anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 179;BA.debugLine="End Sub";
return "";
}
public static String  _btnokbasico1_click() throws Exception{
 //BA.debugLineNum = 236;BA.debugLine="Private Sub btnOkBasico1_Click";
 //BA.debugLineNum = 237;BA.debugLine="create_reporte";
_create_reporte();
 //BA.debugLineNum = 238;BA.debugLine="End Sub";
return "";
}
public static String  _btnokcarga_click() throws Exception{
anywheresoftware.b4a.objects.collections.Map _map1 = null;
 //BA.debugLineNum = 454;BA.debugLine="Private Sub btnOkCarga_Click";
 //BA.debugLineNum = 455;BA.debugLine="ProgressBar1.Visible = False";
mostCurrent._progressbar1.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 456;BA.debugLine="btnOkCarga.Enabled = False";
mostCurrent._btnokcarga.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 457;BA.debugLine="btnOkCarga.Text = \"Enviando información... espere";
mostCurrent._btnokcarga.setText(BA.ObjectToCharSequence("Enviando información... espere"));
 //BA.debugLineNum = 460;BA.debugLine="Try";
try { //BA.debugLineNum = 461;BA.debugLine="Dim Map1 As Map";
_map1 = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 462;BA.debugLine="Map1.Initialize";
_map1.Initialize();
 //BA.debugLineNum = 463;BA.debugLine="Log(\"currentproject: \" & Main.currentproject)";
anywheresoftware.b4a.keywords.Common.LogImpl("042860553","currentproject: "+mostCurrent._main._currentproject /*String*/ ,0);
 //BA.debugLineNum = 464;BA.debugLine="Map1.Put(\"Id\", Main.currentproject)";
_map1.Put((Object)("Id"),(Object)(mostCurrent._main._currentproject /*String*/ ));
 //BA.debugLineNum = 465;BA.debugLine="DBUtils.UpdateRecord(Starter.modulo_hidro_sqlDB,";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._modulo_hidro_sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals_hidro","tiporio",(Object)(mostCurrent._tiporio),_map1);
 //BA.debugLineNum = 466;BA.debugLine="If tiporio = \"limnimetro\" Then";
if ((mostCurrent._tiporio).equals("limnimetro")) { 
 //BA.debugLineNum = 467;BA.debugLine="DBUtils.UpdateRecord(Starter.modulo_hidro_sqlDB";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._modulo_hidro_sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals_hidro","valorind1",(Object)(mostCurrent._txtaltura._gettext /*String*/ ()),_map1);
 }else {
 //BA.debugLineNum = 469;BA.debugLine="DBUtils.UpdateRecord(Starter.modulo_hidro_sqlDB";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._modulo_hidro_sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals_hidro","valorind2",(Object)(mostCurrent._barv1._getseekvalue /*int*/ ()),_map1);
 };
 //BA.debugLineNum = 471;BA.debugLine="DBUtils.UpdateRecord(Starter.modulo_hidro_sqlDB,";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._modulo_hidro_sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals_hidro","decimalLatitude",(Object)(_lat),_map1);
 //BA.debugLineNum = 472;BA.debugLine="DBUtils.UpdateRecord(Starter.modulo_hidro_sqlDB,";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._modulo_hidro_sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals_hidro","decimalLongitude",(Object)(_lng),_map1);
 //BA.debugLineNum = 473;BA.debugLine="DBUtils.UpdateRecord(Starter.modulo_hidro_sqlDB,";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._modulo_hidro_sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals_hidro","georeferencedDate",(Object)(anywheresoftware.b4a.keywords.Common.DateTime.Date(anywheresoftware.b4a.keywords.Common.DateTime.getNow())),_map1);
 //BA.debugLineNum = 474;BA.debugLine="DBUtils.UpdateRecord(Starter.modulo_hidro_sqlDB,";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._modulo_hidro_sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals_hidro","foto1",(Object)(_foto1_path),_map1);
 //BA.debugLineNum = 477;BA.debugLine="DBUtils.UpdateRecord(Starter.modulo_hidro_sqlDB,";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._modulo_hidro_sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals_hidro","terminado",(Object)("si"),_map1);
 //BA.debugLineNum = 479;BA.debugLine="enviar_mod";
_enviar_mod();
 } 
       catch (Exception e22) {
			processBA.setLastException(e22); //BA.debugLineNum = 481;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("042860571",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA)),0);
 //BA.debugLineNum = 482;BA.debugLine="ToastMessageShow(\"No se pudieron guardar los dat";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("No se pudieron guardar los datos"),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 483;BA.debugLine="btnOkCarga.Enabled = True";
mostCurrent._btnokcarga.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 484;BA.debugLine="btnOkCarga.Text = \"¡Finalizar y enviar!\"";
mostCurrent._btnokcarga.setText(BA.ObjectToCharSequence("¡Finalizar y enviar!"));
 };
 //BA.debugLineNum = 487;BA.debugLine="End Sub";
return "";
}
public static void  _btnprev_click() throws Exception{
ResumableSub_btnPrev_Click rsub = new ResumableSub_btnPrev_Click(null);
rsub.resume(processBA, null);
}
public static class ResumableSub_btnPrev_Click extends BA.ResumableSub {
public ResumableSub_btnPrev_Click(appear.pnud.preservamos.mod_hidro parent) {
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
 //BA.debugLineNum = 181;BA.debugLine="If tabContainer.CurrentPage = 0 Then";
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
 //BA.debugLineNum = 182;BA.debugLine="Msgbox2Async(\"¿Cancelar el análisis?\", \"¡Se perd";
anywheresoftware.b4a.keywords.Common.Msgbox2Async(BA.ObjectToCharSequence("¿Cancelar el análisis?"),BA.ObjectToCharSequence("¡Se perderá lo cargado!"),"Si","","No",(anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper(), (android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null)),processBA,anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 183;BA.debugLine="Wait For Msgbox_Result (Result As Int)";
anywheresoftware.b4a.keywords.Common.WaitFor("msgbox_result", processBA, this, null);
this.state = 17;
return;
case 17:
//C
this.state = 4;
_result = (Integer) result[0];
;
 //BA.debugLineNum = 184;BA.debugLine="If Result = DialogResponse.POSITIVE Then";
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
 //BA.debugLineNum = 185;BA.debugLine="Activity.finish";
parent.mostCurrent._activity.Finish();
 //BA.debugLineNum = 186;BA.debugLine="Activity.RemoveAllViews";
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
 //BA.debugLineNum = 189;BA.debugLine="tabContainer.ScrollTo(0, True)";
parent.mostCurrent._tabcontainer.ScrollTo((int) (0),anywheresoftware.b4a.keywords.Common.True);
 if (true) break;

case 11:
//C
this.state = 16;
 //BA.debugLineNum = 191;BA.debugLine="tabContainer.ScrollTo(1, True)";
parent.mostCurrent._tabcontainer.ScrollTo((int) (1),anywheresoftware.b4a.keywords.Common.True);
 if (true) break;

case 13:
//C
this.state = 16;
 //BA.debugLineNum = 193;BA.debugLine="tabContainer.ScrollTo(2, True)";
parent.mostCurrent._tabcontainer.ScrollTo((int) (2),anywheresoftware.b4a.keywords.Common.True);
 if (true) break;

case 15:
//C
this.state = 16;
 //BA.debugLineNum = 195;BA.debugLine="tabContainer.ScrollTo(3, True)";
parent.mostCurrent._tabcontainer.ScrollTo((int) (3),anywheresoftware.b4a.keywords.Common.True);
 if (true) break;

case 16:
//C
this.state = -1;
;
 //BA.debugLineNum = 198;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public static void  _msgbox_result(int _result) throws Exception{
}
public static String  _cargar_basicos() throws Exception{
 //BA.debugLineNum = 212;BA.debugLine="Private Sub cargar_basicos";
 //BA.debugLineNum = 213;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 214;BA.debugLine="Activity.LoadLayout(\"mod_hidro_basico\")";
mostCurrent._activity.LoadLayout("mod_hidro_basico",mostCurrent.activityBA);
 //BA.debugLineNum = 218;BA.debugLine="End Sub";
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
 //BA.debugLineNum = 117;BA.debugLine="If Main.lang = \"es\" Then";
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
 //BA.debugLineNum = 118;BA.debugLine="Msgbox2Async(\"¿Cancelar el análisis?\", \"¡Se perd";
anywheresoftware.b4a.keywords.Common.Msgbox2Async(BA.ObjectToCharSequence("¿Cancelar el análisis?"),BA.ObjectToCharSequence("¡Se perderá lo cargado!"),"Si","","No",(anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper(), (android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null)),processBA,anywheresoftware.b4a.keywords.Common.False);
 if (true) break;

case 5:
//C
this.state = 6;
 //BA.debugLineNum = 120;BA.debugLine="Msgbox2Async(\"Back to the beginning?\", \"Exit\", \"";
anywheresoftware.b4a.keywords.Common.Msgbox2Async(BA.ObjectToCharSequence("Back to the beginning?"),BA.ObjectToCharSequence("Exit"),"Yes","","No",(anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper(), (android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null)),processBA,anywheresoftware.b4a.keywords.Common.False);
 if (true) break;

case 6:
//C
this.state = 7;
;
 //BA.debugLineNum = 122;BA.debugLine="Wait For Msgbox_Result (Result As Int)";
anywheresoftware.b4a.keywords.Common.WaitFor("msgbox_result", processBA, this, null);
this.state = 11;
return;
case 11:
//C
this.state = 7;
_result = (Integer) result[0];
;
 //BA.debugLineNum = 123;BA.debugLine="If Result = DialogResponse.POSITIVE Then";
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
 //BA.debugLineNum = 124;BA.debugLine="Activity.finish";
parent.mostCurrent._activity.Finish();
 //BA.debugLineNum = 125;BA.debugLine="Activity.RemoveAllViews";
parent.mostCurrent._activity.RemoveAllViews();
 if (true) break;

case 10:
//C
this.state = -1;
;
 //BA.debugLineNum = 127;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public static String  _create_reporte() throws Exception{
anywheresoftware.b4a.objects.collections.List _neweval = null;
anywheresoftware.b4a.objects.collections.Map _m = null;
anywheresoftware.b4a.objects.collections.Map _currentprojectmap = null;
String _usernamenoaccent = "";
anywheresoftware.b4a.objects.collections.Map _map1 = null;
 //BA.debugLineNum = 239;BA.debugLine="Sub create_reporte";
 //BA.debugLineNum = 242;BA.debugLine="Dim newEval As List";
_neweval = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 243;BA.debugLine="newEval.Initialize";
_neweval.Initialize();
 //BA.debugLineNum = 244;BA.debugLine="Dim m As Map";
_m = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 245;BA.debugLine="m.Initialize";
_m.Initialize();
 //BA.debugLineNum = 246;BA.debugLine="m.Put(\"useremail\", Main.strUserEmail)";
_m.Put((Object)("useremail"),(Object)(mostCurrent._main._struseremail /*String*/ ));
 //BA.debugLineNum = 247;BA.debugLine="newEval.Add(m)";
_neweval.Add((Object)(_m.getObject()));
 //BA.debugLineNum = 248;BA.debugLine="If Starter.modulo_hidro_sqlDB = Null Then";
if (mostCurrent._starter._modulo_hidro_sqldb /*anywheresoftware.b4a.sql.SQL*/ == null) { 
 //BA.debugLineNum = 249;BA.debugLine="Starter.modulo_hidro_sqlDB.Initialize(Starter.mo";
mostCurrent._starter._modulo_hidro_sqldb /*anywheresoftware.b4a.sql.SQL*/ .Initialize(BA.ObjectToString(mostCurrent._starter._modulo_hidro_sqldb /*anywheresoftware.b4a.sql.SQL*/ ),"preservamos_hidro_db.db",anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 251;BA.debugLine="Try";
try { //BA.debugLineNum = 252;BA.debugLine="DBUtils.InsertMaps(Starter.modulo_hidro_sqlDB,\"e";
mostCurrent._dbutils._insertmaps /*String*/ (mostCurrent.activityBA,mostCurrent._starter._modulo_hidro_sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals_hidro",_neweval);
 } 
       catch (Exception e13) {
			processBA.setLastException(e13); //BA.debugLineNum = 254;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("042336271",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA)),0);
 //BA.debugLineNum = 255;BA.debugLine="ToastMessageShow(\"Hubo un error agregando su inf";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Hubo un error agregando su informe, intente de nuevo"),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 256;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 261;BA.debugLine="Dim currentprojectMap As Map";
_currentprojectmap = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 262;BA.debugLine="currentprojectMap.Initialize";
_currentprojectmap.Initialize();
 //BA.debugLineNum = 263;BA.debugLine="Try";
try { //BA.debugLineNum = 264;BA.debugLine="currentprojectMap = DBUtils.ExecuteMap(Starter.m";
_currentprojectmap = mostCurrent._dbutils._executemap /*anywheresoftware.b4a.objects.collections.Map*/ (mostCurrent.activityBA,mostCurrent._starter._modulo_hidro_sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"SELECT * FROM evals_hidro ORDER BY id DESC LIMIT 1",(String[])(anywheresoftware.b4a.keywords.Common.Null));
 //BA.debugLineNum = 265;BA.debugLine="Dim usernameNoAccent As String";
_usernamenoaccent = "";
 //BA.debugLineNum = 266;BA.debugLine="usernameNoAccent = Main.username.Replace(\"á\", \"a";
_usernamenoaccent = mostCurrent._main._username /*String*/ .replace("á","a");
 //BA.debugLineNum = 267;BA.debugLine="usernameNoAccent = Main.username.Replace(\"é\", \"e";
_usernamenoaccent = mostCurrent._main._username /*String*/ .replace("é","e");
 //BA.debugLineNum = 268;BA.debugLine="usernameNoAccent = Main.username.Replace(\"í\", \"i";
_usernamenoaccent = mostCurrent._main._username /*String*/ .replace("í","i");
 //BA.debugLineNum = 269;BA.debugLine="usernameNoAccent = Main.username.Replace(\"ó\", \"o";
_usernamenoaccent = mostCurrent._main._username /*String*/ .replace("ó","o");
 //BA.debugLineNum = 270;BA.debugLine="usernameNoAccent = Main.username.Replace(\"ú\", \"u";
_usernamenoaccent = mostCurrent._main._username /*String*/ .replace("ú","u");
 //BA.debugLineNum = 271;BA.debugLine="usernameNoAccent = Main.username.Replace(\"ñ\", \"n";
_usernamenoaccent = mostCurrent._main._username /*String*/ .replace("ñ","n");
 //BA.debugLineNum = 272;BA.debugLine="usernameNoAccent = Main.username.Replace(\"@\", \"a";
_usernamenoaccent = mostCurrent._main._username /*String*/ .replace("@","ae");
 //BA.debugLineNum = 274;BA.debugLine="If currentprojectMap = Null Or currentprojectMap";
if (_currentprojectmap== null || _currentprojectmap.IsInitialized()==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 275;BA.debugLine="currentproject = 1";
_currentproject = BA.NumberToString(1);
 //BA.debugLineNum = 276;BA.debugLine="Main.currentproject = currentproject";
mostCurrent._main._currentproject /*String*/  = _currentproject;
 //BA.debugLineNum = 277;BA.debugLine="Dim Map1 As Map";
_map1 = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 278;BA.debugLine="Map1.Initialize";
_map1.Initialize();
 //BA.debugLineNum = 279;BA.debugLine="Map1.Put(\"Id\", currentproject)";
_map1.Put((Object)("Id"),(Object)(_currentproject));
 //BA.debugLineNum = 280;BA.debugLine="DateTime.DateFormat = \"dd-MM-yyyy\"";
anywheresoftware.b4a.keywords.Common.DateTime.setDateFormat("dd-MM-yyyy");
 //BA.debugLineNum = 281;BA.debugLine="datecurrentproject = DateTime.Date(DateTime.Now";
_datecurrentproject = anywheresoftware.b4a.keywords.Common.DateTime.Date(anywheresoftware.b4a.keywords.Common.DateTime.getNow());
 //BA.debugLineNum = 283;BA.debugLine="fullidcurrentproject = usernameNoAccent & \"_\" &";
_fullidcurrentproject = _usernamenoaccent+"_"+_currentproject+"_"+_datecurrentproject;
 //BA.debugLineNum = 284;BA.debugLine="DBUtils.UpdateRecord(Starter.modulo_hidro_sqlDB";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._modulo_hidro_sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals_hidro","fullID",(Object)(_fullidcurrentproject),_map1);
 //BA.debugLineNum = 285;BA.debugLine="DBUtils.UpdateRecord(Starter.modulo_hidro_sqlDB";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._modulo_hidro_sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals_hidro","terminado",(Object)("no"),_map1);
 //BA.debugLineNum = 286;BA.debugLine="DBUtils.UpdateRecord(Starter.modulo_hidro_sqlDB";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._modulo_hidro_sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals_hidro","evalsent",(Object)("no"),_map1);
 }else {
 //BA.debugLineNum = 288;BA.debugLine="currentproject = currentprojectMap.Get(\"id\")";
_currentproject = BA.ObjectToString(_currentprojectmap.Get((Object)("id")));
 //BA.debugLineNum = 289;BA.debugLine="Main.currentproject = currentproject";
mostCurrent._main._currentproject /*String*/  = _currentproject;
 //BA.debugLineNum = 290;BA.debugLine="Dim Map1 As Map";
_map1 = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 291;BA.debugLine="Map1.Initialize";
_map1.Initialize();
 //BA.debugLineNum = 292;BA.debugLine="Map1.Put(\"Id\", currentproject)";
_map1.Put((Object)("Id"),(Object)(_currentproject));
 //BA.debugLineNum = 293;BA.debugLine="DateTime.DateFormat = \"dd-MM-yyyy\"";
anywheresoftware.b4a.keywords.Common.DateTime.setDateFormat("dd-MM-yyyy");
 //BA.debugLineNum = 294;BA.debugLine="datecurrentproject = DateTime.Date(DateTime.Now";
_datecurrentproject = anywheresoftware.b4a.keywords.Common.DateTime.Date(anywheresoftware.b4a.keywords.Common.DateTime.getNow());
 //BA.debugLineNum = 295;BA.debugLine="fullidcurrentproject = usernameNoAccent & \"_\" &";
_fullidcurrentproject = _usernamenoaccent+"_"+_currentproject+"_"+_datecurrentproject;
 //BA.debugLineNum = 296;BA.debugLine="DBUtils.UpdateRecord(Starter.modulo_hidro_sqlDB";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._modulo_hidro_sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals_hidro","fullID",(Object)(_fullidcurrentproject),_map1);
 //BA.debugLineNum = 297;BA.debugLine="DBUtils.UpdateRecord(Starter.modulo_hidro_sqlDB";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._modulo_hidro_sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals_hidro","terminado",(Object)("no"),_map1);
 //BA.debugLineNum = 298;BA.debugLine="DBUtils.UpdateRecord(Starter.modulo_hidro_sqlDB";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._modulo_hidro_sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals_hidro","evalsent",(Object)("no"),_map1);
 };
 } 
       catch (Exception e55) {
			processBA.setLastException(e55); //BA.debugLineNum = 301;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("042336318",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA)),0);
 //BA.debugLineNum = 302;BA.debugLine="ToastMessageShow(\"Hubo un error, intente de nuev";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Hubo un error, intente de nuevo"),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 303;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 305;BA.debugLine="Log(\"currentproject: \" & Main.currentproject)";
anywheresoftware.b4a.keywords.Common.LogImpl("042336322","currentproject: "+mostCurrent._main._currentproject /*String*/ ,0);
 //BA.debugLineNum = 308;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 309;BA.debugLine="Activity.LoadLayout(\"mod_hidro_carga\")";
mostCurrent._activity.LoadLayout("mod_hidro_carga",mostCurrent.activityBA);
 //BA.debugLineNum = 312;BA.debugLine="frmLocalizacion.origen = \"modulo_hidro\"";
mostCurrent._frmlocalizacion._origen /*String*/  = "modulo_hidro";
 //BA.debugLineNum = 313;BA.debugLine="StartActivity(frmLocalizacion)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._frmlocalizacion.getObject()));
 //BA.debugLineNum = 315;BA.debugLine="End Sub";
return "";
}
public static void  _enviar_mod() throws Exception{
ResumableSub_enviar_mod rsub = new ResumableSub_enviar_mod(null);
rsub.resume(processBA, null);
}
public static class ResumableSub_enviar_mod extends BA.ResumableSub {
public ResumableSub_enviar_mod(appear.pnud.preservamos.mod_hidro parent) {
this.parent = parent;
}
appear.pnud.preservamos.mod_hidro parent;
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
 //BA.debugLineNum = 500;BA.debugLine="If Main.username = \"guest\" Or Main.username = \"No";
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
 //BA.debugLineNum = 501;BA.debugLine="ToastMessageShow(\"No estás registrado/a, pero ig";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("No estás registrado/a, pero igual se enviará tu reporte!"),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 502;BA.debugLine="Dim RndStr As String";
_rndstr = "";
 //BA.debugLineNum = 503;BA.debugLine="RndStr = utilidades.RandomString(6)";
_rndstr = parent.mostCurrent._utilidades._randomstring /*String*/ (mostCurrent.activityBA,(int) (6));
 //BA.debugLineNum = 504;BA.debugLine="Main.username = \"guest_\" & RndStr";
parent.mostCurrent._main._username /*String*/  = "guest_"+_rndstr;
 if (true) break;

case 4:
//C
this.state = 5;
;
 //BA.debugLineNum = 507;BA.debugLine="ProgressBar1.Progress = 0";
parent.mostCurrent._progressbar1.setProgress((int) (0));
 //BA.debugLineNum = 511;BA.debugLine="Wait For (EnvioDatos(Main.currentproject)) Comple";
anywheresoftware.b4a.keywords.Common.WaitFor("complete", processBA, this, _enviodatos(parent.mostCurrent._main._currentproject /*String*/ ));
this.state = 11;
return;
case 11:
//C
this.state = 5;
_completed = (Boolean) result[0];
;
 //BA.debugLineNum = 513;BA.debugLine="If Completed = True Then";
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
 //BA.debugLineNum = 515;BA.debugLine="EnviarFotos";
_enviarfotos();
 if (true) break;

case 9:
//C
this.state = 10;
 //BA.debugLineNum = 517;BA.debugLine="TimerEnvio.Enabled = False";
parent._timerenvio.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 518;BA.debugLine="Activity.RemoveAllViews";
parent.mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 519;BA.debugLine="Activity.finish";
parent.mostCurrent._activity.Finish();
 if (true) break;

case 10:
//C
this.state = -1;
;
 //BA.debugLineNum = 526;BA.debugLine="End Sub";
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
 //BA.debugLineNum = 718;BA.debugLine="Sub EnviarFotos";
 //BA.debugLineNum = 721;BA.debugLine="If foto1_path <> \"null\" Then";
if ((_foto1_path).equals("null") == false) { 
 //BA.debugLineNum = 722;BA.debugLine="totalFotos = totalFotos + 1";
_totalfotos = (int) (_totalfotos+1);
 //BA.debugLineNum = 723;BA.debugLine="ProgressBar1.Visible = False";
mostCurrent._progressbar1.setVisible(anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 727;BA.debugLine="TimerEnvio.Initialize(\"TimerEnvio\", 1000)";
_timerenvio.Initialize(processBA,"TimerEnvio",(long) (1000));
 //BA.debugLineNum = 728;BA.debugLine="TimerEnvio.Enabled = True";
_timerenvio.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 730;BA.debugLine="Dim usr As String";
_usr = "";
 //BA.debugLineNum = 731;BA.debugLine="Dim pss As String";
_pss = "";
 //BA.debugLineNum = 732;BA.debugLine="usr = \"\"";
_usr = "";
 //BA.debugLineNum = 733;BA.debugLine="pss = \"\"";
_pss = "";
 //BA.debugLineNum = 735;BA.debugLine="Up1.B4A_log=True";
_up1.B4A_log = anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 736;BA.debugLine="Up1.Initialize(\"Up1\")";
_up1.Initialize(processBA,"Up1");
 //BA.debugLineNum = 739;BA.debugLine="If File.Exists(Starter.savedir, foto1_path & \".jp";
if (anywheresoftware.b4a.keywords.Common.File.Exists(mostCurrent._starter._savedir /*String*/ ,_foto1_path+".jpg") && _foto1sent==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 740;BA.debugLine="Log(\"Enviando foto\")";
anywheresoftware.b4a.keywords.Common.LogImpl("043057174","Enviando foto",0);
 //BA.debugLineNum = 741;BA.debugLine="Log(\"Enviando foto 1 \")";
anywheresoftware.b4a.keywords.Common.LogImpl("043057175","Enviando foto 1 ",0);
 //BA.debugLineNum = 742;BA.debugLine="Dim filetoUpload As String";
_filetoupload = "";
 //BA.debugLineNum = 743;BA.debugLine="filetoUpload = Starter.savedir & \"/\" & foto1_pat";
_filetoupload = mostCurrent._starter._savedir /*String*/ +"/"+_foto1_path+".jpg";
 //BA.debugLineNum = 744;BA.debugLine="Up1.doFileUpload(ProgressBar1,Null,filetoUpload,";
_up1.doFileUpload(processBA,(android.widget.ProgressBar)(mostCurrent._progressbar1.getObject()),(android.widget.TextView)(anywheresoftware.b4a.keywords.Common.Null),_filetoupload,mostCurrent._main._serverpath /*String*/ +"/"+mostCurrent._main._serverconnectionfolder /*String*/ +"/upload_file.php?usr="+_usr+"&pss="+_pss);
 //BA.debugLineNum = 745;BA.debugLine="ProgressBar1.Visible = False";
mostCurrent._progressbar1.setVisible(anywheresoftware.b4a.keywords.Common.False);
 }else {
 //BA.debugLineNum = 748;BA.debugLine="Log(\"no foto 1\")";
anywheresoftware.b4a.keywords.Common.LogImpl("043057182","no foto 1",0);
 };
 //BA.debugLineNum = 752;BA.debugLine="End Sub";
return "";
}
public static anywheresoftware.b4a.keywords.Common.ResumableSubWrapper  _enviodatos(String _proyectonumero) throws Exception{
ResumableSub_EnvioDatos rsub = new ResumableSub_EnvioDatos(null,_proyectonumero);
rsub.resume(processBA, null);
return (anywheresoftware.b4a.keywords.Common.ResumableSubWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.keywords.Common.ResumableSubWrapper(), rsub);
}
public static class ResumableSub_EnvioDatos extends BA.ResumableSub {
public ResumableSub_EnvioDatos(appear.pnud.preservamos.mod_hidro parent,String _proyectonumero) {
this.parent = parent;
this._proyectonumero = _proyectonumero;
}
appear.pnud.preservamos.mod_hidro parent;
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
 //BA.debugLineNum = 530;BA.debugLine="proyectoIDenviar = proyectonumero";
parent.mostCurrent._proyectoidenviar = _proyectonumero;
 //BA.debugLineNum = 532;BA.debugLine="Dim username, useremail, dateandtime,nombresitio,";
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
 //BA.debugLineNum = 533;BA.debugLine="Dim valorcalidad, valorNS, valorind1,valorind2,va";
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
 //BA.debugLineNum = 536;BA.debugLine="Dim notas,bingo As String";
_notas = "";
_bingo = "";
 //BA.debugLineNum = 537;BA.debugLine="Dim terminado, privado,estadovalidacion, deviceID";
_terminado = "";
_privado = "";
_estadovalidacion = "";
_deviceid = "";
 //BA.debugLineNum = 539;BA.debugLine="Dim datosMap As Map";
_datosmap = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 540;BA.debugLine="datosMap.Initialize";
_datosmap.Initialize();
 //BA.debugLineNum = 541;BA.debugLine="datosMap = DBUtils.ExecuteMap(Starter.modulo_hidr";
_datosmap = parent.mostCurrent._dbutils._executemap /*anywheresoftware.b4a.objects.collections.Map*/ (mostCurrent.activityBA,parent.mostCurrent._starter._modulo_hidro_sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"SELECT * FROM evals_hidro WHERE Id=?",new String[]{_proyectonumero});
 //BA.debugLineNum = 543;BA.debugLine="If datosMap = Null Or datosMap.IsInitialized = Fa";
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
 //BA.debugLineNum = 544;BA.debugLine="ToastMessageShow(\"Error cargando el análisis\", F";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Error cargando el análisis"),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 545;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 //BA.debugLineNum = 546;BA.debugLine="Return False";
if (true) {
anywheresoftware.b4a.keywords.Common.ReturnFromResumableSub(this,(Object)(anywheresoftware.b4a.keywords.Common.False));return;};
 if (true) break;

case 5:
//C
this.state = 6;
 //BA.debugLineNum = 549;BA.debugLine="username = datosMap.Get(\"usuario\")";
_username = BA.ObjectToString(_datosmap.Get((Object)("usuario")));
 //BA.debugLineNum = 550;BA.debugLine="useremail = Main.strUserEmail";
_useremail = parent.mostCurrent._main._struseremail /*String*/ ;
 //BA.debugLineNum = 551;BA.debugLine="dateandtime = datosMap.Get(\"georeferenceddate\")";
_dateandtime = BA.ObjectToString(_datosmap.Get((Object)("georeferenceddate")));
 //BA.debugLineNum = 552;BA.debugLine="nombresitio = datosMap.Get(\"nombresitio\")";
_nombresitio = BA.ObjectToString(_datosmap.Get((Object)("nombresitio")));
 //BA.debugLineNum = 553;BA.debugLine="tiporio = datosMap.Get(\"tiporio\")";
parent.mostCurrent._tiporio = BA.ObjectToString(_datosmap.Get((Object)("tiporio")));
 //BA.debugLineNum = 554;BA.debugLine="lat = datosMap.Get(\"decimallatitude\")";
parent._lat = BA.ObjectToString(_datosmap.Get((Object)("decimallatitude")));
 //BA.debugLineNum = 555;BA.debugLine="lng = datosMap.Get(\"decimallongitude\")";
parent._lng = BA.ObjectToString(_datosmap.Get((Object)("decimallongitude")));
 //BA.debugLineNum = 556;BA.debugLine="gpsdetect = datosMap.Get(\"gpsdetect\")";
_gpsdetect = BA.ObjectToString(_datosmap.Get((Object)("gpsdetect")));
 //BA.debugLineNum = 557;BA.debugLine="wifidetect = datosMap.Get(\"wifidetect\")";
_wifidetect = BA.ObjectToString(_datosmap.Get((Object)("wifidetect")));
 //BA.debugLineNum = 558;BA.debugLine="mapadetect = datosMap.Get(\"mapadetect\")";
_mapadetect = BA.ObjectToString(_datosmap.Get((Object)("mapadetect")));
 //BA.debugLineNum = 559;BA.debugLine="partido = Geopartido";
_partido = parent._geopartido;
 //BA.debugLineNum = 560;BA.debugLine="valorcalidad = datosMap.Get(\"valorcalidad\")";
_valorcalidad = BA.ObjectToString(_datosmap.Get((Object)("valorcalidad")));
 //BA.debugLineNum = 561;BA.debugLine="valorNS = datosMap.Get(\"valorind20\")";
_valorns = BA.ObjectToString(_datosmap.Get((Object)("valorind20")));
 //BA.debugLineNum = 562;BA.debugLine="valorind1 = datosMap.Get(\"valorind1\")";
parent.mostCurrent._valorind1 = BA.ObjectToString(_datosmap.Get((Object)("valorind1")));
 //BA.debugLineNum = 563;BA.debugLine="valorind2 = datosMap.Get(\"valorind2\")";
_valorind2 = BA.ObjectToString(_datosmap.Get((Object)("valorind2")));
 //BA.debugLineNum = 564;BA.debugLine="valorind3 = datosMap.Get(\"valorind3\")";
_valorind3 = BA.ObjectToString(_datosmap.Get((Object)("valorind3")));
 //BA.debugLineNum = 565;BA.debugLine="valorind4 = datosMap.Get(\"valorind4\")";
_valorind4 = BA.ObjectToString(_datosmap.Get((Object)("valorind4")));
 //BA.debugLineNum = 566;BA.debugLine="valorind5 = datosMap.Get(\"valorind5\")";
_valorind5 = BA.ObjectToString(_datosmap.Get((Object)("valorind5")));
 //BA.debugLineNum = 567;BA.debugLine="valorind6 = datosMap.Get(\"valorind6\")";
_valorind6 = BA.ObjectToString(_datosmap.Get((Object)("valorind6")));
 //BA.debugLineNum = 568;BA.debugLine="valorind7 = datosMap.Get(\"valorind7\")";
_valorind7 = BA.ObjectToString(_datosmap.Get((Object)("valorind7")));
 //BA.debugLineNum = 569;BA.debugLine="valorind8 = datosMap.Get(\"valorind8\")";
_valorind8 = BA.ObjectToString(_datosmap.Get((Object)("valorind8")));
 //BA.debugLineNum = 570;BA.debugLine="valorind9 = datosMap.Get(\"valorind9\")";
_valorind9 = BA.ObjectToString(_datosmap.Get((Object)("valorind9")));
 //BA.debugLineNum = 571;BA.debugLine="valorind10 = datosMap.Get(\"valorind10\")";
_valorind10 = BA.ObjectToString(_datosmap.Get((Object)("valorind10")));
 //BA.debugLineNum = 572;BA.debugLine="valorind11 = datosMap.Get(\"valorind11\")";
_valorind11 = BA.ObjectToString(_datosmap.Get((Object)("valorind11")));
 //BA.debugLineNum = 573;BA.debugLine="valorind12 = datosMap.Get(\"valorind12\")";
_valorind12 = BA.ObjectToString(_datosmap.Get((Object)("valorind12")));
 //BA.debugLineNum = 574;BA.debugLine="valorind13 = datosMap.Get(\"valorind13\")";
_valorind13 = BA.ObjectToString(_datosmap.Get((Object)("valorind13")));
 //BA.debugLineNum = 575;BA.debugLine="valorind14 = datosMap.Get(\"valorind14\")";
_valorind14 = BA.ObjectToString(_datosmap.Get((Object)("valorind14")));
 //BA.debugLineNum = 576;BA.debugLine="valorind15 = datosMap.Get(\"valorind15\")";
_valorind15 = BA.ObjectToString(_datosmap.Get((Object)("valorind15")));
 //BA.debugLineNum = 577;BA.debugLine="valorind16 = datosMap.Get(\"valorind16\")";
_valorind16 = BA.ObjectToString(_datosmap.Get((Object)("valorind16")));
 //BA.debugLineNum = 578;BA.debugLine="valorind17 = datosMap.Get(\"valorind17\")";
_valorind17 = BA.ObjectToString(_datosmap.Get((Object)("valorind17")));
 //BA.debugLineNum = 579;BA.debugLine="valorind18 = datosMap.Get(\"valorind18\")";
_valorind18 = BA.ObjectToString(_datosmap.Get((Object)("valorind18")));
 //BA.debugLineNum = 580;BA.debugLine="valorind19 = datosMap.Get(\"valorind19\")";
_valorind19 = BA.ObjectToString(_datosmap.Get((Object)("valorind19")));
 //BA.debugLineNum = 582;BA.debugLine="notas = datosMap.Get(\"notas\")";
_notas = BA.ObjectToString(_datosmap.Get((Object)("notas")));
 //BA.debugLineNum = 583;BA.debugLine="bingo = datosMap.Get(\"bingo\")";
_bingo = BA.ObjectToString(_datosmap.Get((Object)("bingo")));
 //BA.debugLineNum = 584;BA.debugLine="foto1 = datosMap.Get(\"foto1\")";
parent.mostCurrent._foto1 = BA.ObjectToString(_datosmap.Get((Object)("foto1")));
 //BA.debugLineNum = 585;BA.debugLine="terminado = datosMap.Get(\"terminado\")";
_terminado = BA.ObjectToString(_datosmap.Get((Object)("terminado")));
 //BA.debugLineNum = 586;BA.debugLine="privado = datosMap.Get(\"privado\")";
_privado = BA.ObjectToString(_datosmap.Get((Object)("privado")));
 //BA.debugLineNum = 587;BA.debugLine="If privado = Null Or privado = \"null\" Or privado";
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
 //BA.debugLineNum = 588;BA.debugLine="privado = \"no\"";
_privado = "no";
 if (true) break;

case 9:
//C
this.state = 10;
;
 //BA.debugLineNum = 590;BA.debugLine="estadovalidacion = datosMap.Get(\"estadovalidacio";
_estadovalidacion = BA.ObjectToString(_datosmap.Get((Object)("estadovalidacion")));
 //BA.debugLineNum = 591;BA.debugLine="If estadovalidacion = \"null\" Then";
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
 //BA.debugLineNum = 592;BA.debugLine="estadovalidacion = \"No Verificado\"";
_estadovalidacion = "No Verificado";
 if (true) break;

case 13:
//C
this.state = 14;
;
 //BA.debugLineNum = 594;BA.debugLine="deviceID = datosMap.Get(\"deviceID\")";
_deviceid = BA.ObjectToString(_datosmap.Get((Object)("deviceID")));
 //BA.debugLineNum = 595;BA.debugLine="If deviceID = Null Or deviceID = \"\" Or deviceID";
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
 //BA.debugLineNum = 596;BA.debugLine="deviceID = utilidades.GetDeviceId";
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
 //BA.debugLineNum = 602;BA.debugLine="Log(\"Comienza envio de datos\")";
anywheresoftware.b4a.keywords.Common.LogImpl("042991690","Comienza envio de datos",0);
 //BA.debugLineNum = 603;BA.debugLine="Dim j As HttpJob";
_j = new appear.pnud.preservamos.httpjob();
 //BA.debugLineNum = 604;BA.debugLine="j.Initialize(\"\", Me)";
_j._initialize /*String*/ (processBA,"",mod_hidro.getObject());
 //BA.debugLineNum = 605;BA.debugLine="Dim loginPath As String = Main.serverPath & \"/\" &";
_loginpath = parent.mostCurrent._main._serverpath /*String*/ +"/"+parent.mostCurrent._main._serverconnectionfolder /*String*/ +"/add_mod_hidro.php?"+"username="+_username+"&"+"useremail="+_useremail+"&"+"deviceID="+parent.mostCurrent._main._deviceid /*String*/ +"&"+"dateandtime="+_dateandtime+"&"+"nombresitio="+_nombresitio+"&"+"lat="+parent._lat+"&"+"lng="+parent._lng+"&"+"tiporio="+parent.mostCurrent._tiporio+"&"+"indice="+_valorcalidad+"&"+"precision="+_valorns+"&"+"valorind1="+parent.mostCurrent._valorind1+"&"+"valorind2="+_valorind2+"&"+"valorind3="+_valorind3+"&"+"valorind4="+_valorind4+"&"+"valorind5="+_valorind5+"&"+"valorind6="+_valorind6+"&"+"valorind7="+_valorind7+"&"+"valorind8="+_valorind8+"&"+"valorind9="+_valorind9+"&"+"valorind10="+_valorind10+"&"+"valorind11="+_valorind11+"&"+"valorind12="+_valorind12+"&"+"valorind13="+_valorind13+"&"+"valorind14="+_valorind14+"&"+"valorind15="+_valorind15+"&"+"valorind16="+_valorind16+"&"+"valorind17="+_valorind17+"&"+"valorind18="+_valorind18+"&"+"valorind19="+_valorind19+"&"+"foto1path="+parent.mostCurrent._foto1+"&"+"terminado="+_terminado+"&"+"verificado="+_estadovalidacion+"&"+"privado="+_privado+"&"+"bingo="+_bingo+"&"+"notas="+_notas+"&"+"partido="+_partido+"&"+"mapadetect="+_mapadetect+"&"+"wifidetect="+_wifidetect+"&"+"gpsdetect="+_gpsdetect;
 //BA.debugLineNum = 647;BA.debugLine="j.Download(loginPath)";
_j._download /*String*/ (_loginpath);
 //BA.debugLineNum = 648;BA.debugLine="Wait For (j) JobDone(j As HttpJob)";
anywheresoftware.b4a.keywords.Common.WaitFor("jobdone", processBA, this, (Object)(_j));
this.state = 51;
return;
case 51:
//C
this.state = 19;
_j = (appear.pnud.preservamos.httpjob) result[0];
;
 //BA.debugLineNum = 650;BA.debugLine="If j.Success Then";
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
 //BA.debugLineNum = 651;BA.debugLine="Dim ret As String";
_ret = "";
 //BA.debugLineNum = 652;BA.debugLine="Dim act As String";
_act = "";
 //BA.debugLineNum = 653;BA.debugLine="ret = j.GetString";
_ret = _j._getstring /*String*/ ();
 //BA.debugLineNum = 654;BA.debugLine="Dim parser As JSONParser";
_parser = new anywheresoftware.b4a.objects.collections.JSONParser();
 //BA.debugLineNum = 655;BA.debugLine="parser.Initialize(ret)";
_parser.Initialize(_ret);
 //BA.debugLineNum = 656;BA.debugLine="act = parser.NextValue";
_act = BA.ObjectToString(_parser.NextValue());
 //BA.debugLineNum = 657;BA.debugLine="If act = \"Not Found\" Then";
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
 //BA.debugLineNum = 658;BA.debugLine="If Main.lang = \"es\" Then";
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
 //BA.debugLineNum = 659;BA.debugLine="ToastMessageShow(\"Error subiendo el dato\", Tru";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Error subiendo el dato"),anywheresoftware.b4a.keywords.Common.True);
 if (true) break;

case 29:
//C
this.state = 30;
 //BA.debugLineNum = 661;BA.debugLine="ToastMessageShow(\"Error loading markers\", True";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Error loading markers"),anywheresoftware.b4a.keywords.Common.True);
 if (true) break;

case 30:
//C
this.state = 41;
;
 //BA.debugLineNum = 663;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 //BA.debugLineNum = 664;BA.debugLine="j.Release";
_j._release /*String*/ ();
 //BA.debugLineNum = 665;BA.debugLine="Return False";
if (true) {
anywheresoftware.b4a.keywords.Common.ReturnFromResumableSub(this,(Object)(anywheresoftware.b4a.keywords.Common.False));return;};
 if (true) break;

case 32:
//C
this.state = 41;
 //BA.debugLineNum = 667;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 //BA.debugLineNum = 668;BA.debugLine="MsgboxAsync(\"Al parecer hay un problema en nues";
anywheresoftware.b4a.keywords.Common.MsgboxAsync(BA.ObjectToCharSequence("Al parecer hay un problema en nuestros servidores, lo solucionaremos pronto!"),BA.ObjectToCharSequence("Mala mía"),processBA);
 //BA.debugLineNum = 669;BA.debugLine="j.Release";
_j._release /*String*/ ();
 //BA.debugLineNum = 670;BA.debugLine="Return False";
if (true) {
anywheresoftware.b4a.keywords.Common.ReturnFromResumableSub(this,(Object)(anywheresoftware.b4a.keywords.Common.False));return;};
 if (true) break;

case 34:
//C
this.state = 35;
 //BA.debugLineNum = 673;BA.debugLine="Dim nd As Map";
_nd = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 674;BA.debugLine="nd = parser.NextObject";
_nd = _parser.NextObject();
 //BA.debugLineNum = 675;BA.debugLine="Dim serverID As String";
_serverid = "";
 //BA.debugLineNum = 676;BA.debugLine="serverID = nd.Get(\"serverId\")";
_serverid = BA.ObjectToString(_nd.Get((Object)("serverId")));
 //BA.debugLineNum = 679;BA.debugLine="Dim Map1 As Map";
_map1 = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 680;BA.debugLine="Map1.Initialize";
_map1.Initialize();
 //BA.debugLineNum = 681;BA.debugLine="Map1.Put(\"Id\", Main.currentproject)";
_map1.Put((Object)("Id"),(Object)(parent.mostCurrent._main._currentproject /*String*/ ));
 //BA.debugLineNum = 682;BA.debugLine="DBUtils.UpdateRecord(Starter.modulo_hidro_sqlDB";
parent.mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,parent.mostCurrent._starter._modulo_hidro_sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals_hidro","evalsent",(Object)("si"),_map1);
 //BA.debugLineNum = 683;BA.debugLine="DBUtils.UpdateRecord(Starter.modulo_hidro_sqlDB";
parent.mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,parent.mostCurrent._starter._modulo_hidro_sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals_hidro","serverId",(Object)(_serverid),_map1);
 //BA.debugLineNum = 684;BA.debugLine="If Main.lang = \"es\" Then";
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
 //BA.debugLineNum = 685;BA.debugLine="ToastMessageShow(\"Datos enviados, enviando fot";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Datos enviados, enviando fotos"),anywheresoftware.b4a.keywords.Common.False);
 if (true) break;

case 39:
//C
this.state = 40;
 //BA.debugLineNum = 687;BA.debugLine="ToastMessageShow(\"Report sent, sending photos\"";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Report sent, sending photos"),anywheresoftware.b4a.keywords.Common.False);
 if (true) break;

case 40:
//C
this.state = 41;
;
 //BA.debugLineNum = 689;BA.debugLine="j.Release";
_j._release /*String*/ ();
 //BA.debugLineNum = 690;BA.debugLine="Return True";
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
 //BA.debugLineNum = 694;BA.debugLine="Log(\"envio datos not ok\")";
anywheresoftware.b4a.keywords.Common.LogImpl("042991782","envio datos not ok",0);
 //BA.debugLineNum = 695;BA.debugLine="If Main.lang = \"es\" Then";
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
 //BA.debugLineNum = 696;BA.debugLine="MsgboxAsync(\"Al parecer hay un problema en nues";
anywheresoftware.b4a.keywords.Common.MsgboxAsync(BA.ObjectToCharSequence("Al parecer hay un problema en nuestros servidores, lo solucionaremos pronto!"),BA.ObjectToCharSequence("Mala mía"),processBA);
 if (true) break;

case 48:
//C
this.state = 49;
 //BA.debugLineNum = 698;BA.debugLine="MsgboxAsync(\"There seems to be a problem with o";
anywheresoftware.b4a.keywords.Common.MsgboxAsync(BA.ObjectToCharSequence("There seems to be a problem with our servers, we will solve it soon!"),BA.ObjectToCharSequence("My bad"),processBA);
 if (true) break;

case 49:
//C
this.state = 50;
;
 //BA.debugLineNum = 700;BA.debugLine="btnOkCarga.Enabled = True";
parent.mostCurrent._btnokcarga.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 701;BA.debugLine="btnOkCarga.Text = \"¡Finalizar y enviar!\"";
parent.mostCurrent._btnokcarga.setText(BA.ObjectToCharSequence("¡Finalizar y enviar!"));
 //BA.debugLineNum = 703;BA.debugLine="j.Release";
_j._release /*String*/ ();
 //BA.debugLineNum = 704;BA.debugLine="Return False";
if (true) {
anywheresoftware.b4a.keywords.Common.ReturnFromResumableSub(this,(Object)(anywheresoftware.b4a.keywords.Common.False));return;};
 if (true) break;

case 50:
//C
this.state = -1;
;
 //BA.debugLineNum = 707;BA.debugLine="Return True";
if (true) {
anywheresoftware.b4a.keywords.Common.ReturnFromResumableSub(this,(Object)(anywheresoftware.b4a.keywords.Common.True));return;};
 //BA.debugLineNum = 711;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public static void  _jobdone(appear.pnud.preservamos.httpjob _j) throws Exception{
}
public static String  _fondogris_click() throws Exception{
 //BA.debugLineNum = 412;BA.debugLine="Private Sub fondogris_Click";
 //BA.debugLineNum = 415;BA.debugLine="End Sub";
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
 //BA.debugLineNum = 425;BA.debugLine="Private Sub imgHidro_Click";
 //BA.debugLineNum = 427;BA.debugLine="End Sub";
return "";
}
public static String  _imglimnimetro_click() throws Exception{
 //BA.debugLineNum = 223;BA.debugLine="Private Sub imgLimnimetro_Click";
 //BA.debugLineNum = 224;BA.debugLine="tiporio = \"limnimetro\"";
mostCurrent._tiporio = "limnimetro";
 //BA.debugLineNum = 225;BA.debugLine="lblRojo.Left = imgLimnimetro.Left";
mostCurrent._lblrojo.setLeft(mostCurrent._imglimnimetro.getLeft());
 //BA.debugLineNum = 226;BA.debugLine="lblRojo.Visible = True";
mostCurrent._lblrojo.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 227;BA.debugLine="End Sub";
return "";
}
public static String  _imgnolimnimetro_click() throws Exception{
 //BA.debugLineNum = 228;BA.debugLine="Private Sub imgNoLimnimetro_Click";
 //BA.debugLineNum = 229;BA.debugLine="tiporio = \"nolimnimetro\"";
mostCurrent._tiporio = "nolimnimetro";
 //BA.debugLineNum = 230;BA.debugLine="lblRojo.Left = imgNoLimnimetro.Left";
mostCurrent._lblrojo.setLeft(mostCurrent._imgnolimnimetro.getLeft());
 //BA.debugLineNum = 231;BA.debugLine="lblRojo.Visible = True";
mostCurrent._lblrojo.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 232;BA.debugLine="End Sub";
return "";
}
public static String  _lblcomofuncionaentendido_click() throws Exception{
 //BA.debugLineNum = 407;BA.debugLine="Private Sub lblComoFuncionaEntendido_Click";
 //BA.debugLineNum = 408;BA.debugLine="panelComoFunciona.RemoveView";
mostCurrent._panelcomofunciona.RemoveView();
 //BA.debugLineNum = 409;BA.debugLine="fondogris.RemoveView";
mostCurrent._fondogris.RemoveView();
 //BA.debugLineNum = 410;BA.debugLine="StartActivity(mod_Hidro_Fotos)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._mod_hidro_fotos.getObject()));
 //BA.debugLineNum = 411;BA.debugLine="End Sub";
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
 //BA.debugLineNum = 136;BA.debugLine="Private Sub tabContainer_PageSelected (Position As";
 //BA.debugLineNum = 138;BA.debugLine="If Position = 2 Then";
if (_position==2) { 
 //BA.debugLineNum = 139;BA.debugLine="btnNext.Visible = True";
mostCurrent._btnnext.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 140;BA.debugLine="btnPrev.Visible = True";
mostCurrent._btnprev.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 141;BA.debugLine="lblCirculoPos4.Color = Colors.RGB(76,159,56)";
mostCurrent._lblcirculopos4.setColor(anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (76),(int) (159),(int) (56)));
 //BA.debugLineNum = 142;BA.debugLine="lblCirculoPos3.Color = Colors.RGB(139,226,63)";
mostCurrent._lblcirculopos3.setColor(anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (139),(int) (226),(int) (63)));
 //BA.debugLineNum = 143;BA.debugLine="lblCirculoPos2.Color = Colors.RGB(76,159,56)";
mostCurrent._lblcirculopos2.setColor(anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (76),(int) (159),(int) (56)));
 //BA.debugLineNum = 144;BA.debugLine="lblCirculoPos1.Color = Colors.RGB(76,159,56)";
mostCurrent._lblcirculopos1.setColor(anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (76),(int) (159),(int) (56)));
 }else if(_position==0) { 
 //BA.debugLineNum = 146;BA.debugLine="btnPrev.Visible = False";
mostCurrent._btnprev.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 147;BA.debugLine="btnNext.Visible = True";
mostCurrent._btnnext.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 148;BA.debugLine="lblCirculoPos1.Color = Colors.RGB(139,226,63)";
mostCurrent._lblcirculopos1.setColor(anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (139),(int) (226),(int) (63)));
 //BA.debugLineNum = 149;BA.debugLine="lblCirculoPos2.Color = Colors.RGB(76,159,56)";
mostCurrent._lblcirculopos2.setColor(anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (76),(int) (159),(int) (56)));
 //BA.debugLineNum = 150;BA.debugLine="lblCirculoPos3.Color = Colors.RGB(76,159,56)";
mostCurrent._lblcirculopos3.setColor(anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (76),(int) (159),(int) (56)));
 //BA.debugLineNum = 151;BA.debugLine="lblCirculoPos4.Color = Colors.RGB(76,159,56)";
mostCurrent._lblcirculopos4.setColor(anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (76),(int) (159),(int) (56)));
 }else if(_position==1) { 
 //BA.debugLineNum = 153;BA.debugLine="btnPrev.Visible = True";
mostCurrent._btnprev.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 154;BA.debugLine="btnNext.Visible = True";
mostCurrent._btnnext.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 155;BA.debugLine="lblCirculoPos2.Color = Colors.RGB(139,226,63)";
mostCurrent._lblcirculopos2.setColor(anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (139),(int) (226),(int) (63)));
 //BA.debugLineNum = 156;BA.debugLine="lblCirculoPos3.Color = Colors.RGB(76,159,56)";
mostCurrent._lblcirculopos3.setColor(anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (76),(int) (159),(int) (56)));
 //BA.debugLineNum = 157;BA.debugLine="lblCirculoPos1.Color = Colors.RGB(76,159,56)";
mostCurrent._lblcirculopos1.setColor(anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (76),(int) (159),(int) (56)));
 //BA.debugLineNum = 158;BA.debugLine="lblCirculoPos4.Color = Colors.RGB(76,159,56)";
mostCurrent._lblcirculopos4.setColor(anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (76),(int) (159),(int) (56)));
 }else if(_position==3) { 
 //BA.debugLineNum = 160;BA.debugLine="btnPrev.Visible = True";
mostCurrent._btnprev.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 161;BA.debugLine="btnNext.Visible = False";
mostCurrent._btnnext.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 162;BA.debugLine="lblCirculoPos4.Color = Colors.RGB(139,226,63)";
mostCurrent._lblcirculopos4.setColor(anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (139),(int) (226),(int) (63)));
 //BA.debugLineNum = 163;BA.debugLine="lblCirculoPos3.Color = Colors.RGB(76,159,56)";
mostCurrent._lblcirculopos3.setColor(anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (76),(int) (159),(int) (56)));
 //BA.debugLineNum = 164;BA.debugLine="lblCirculoPos1.Color = Colors.RGB(76,159,56)";
mostCurrent._lblcirculopos1.setColor(anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (76),(int) (159),(int) (56)));
 //BA.debugLineNum = 165;BA.debugLine="lblCirculoPos2.Color = Colors.RGB(76,159,56)";
mostCurrent._lblcirculopos2.setColor(anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (76),(int) (159),(int) (56)));
 };
 //BA.debugLineNum = 168;BA.debugLine="End Sub";
return "";
}
public static String  _timerenvio_tick() throws Exception{
anywheresoftware.b4a.objects.collections.Map _map1 = null;
 //BA.debugLineNum = 755;BA.debugLine="Sub TimerEnvio_Tick";
 //BA.debugLineNum = 756;BA.debugLine="If fotosEnviadas = totalFotos Then";
if (_fotosenviadas==_totalfotos) { 
 //BA.debugLineNum = 757;BA.debugLine="Log(\"TODAS LAS FOTOS FUERON ENVIADAS CORRECTAMEN";
anywheresoftware.b4a.keywords.Common.LogImpl("043122690","TODAS LAS FOTOS FUERON ENVIADAS CORRECTAMENTE",0);
 //BA.debugLineNum = 758;BA.debugLine="Up1.UploadKill";
_up1.UploadKill(processBA);
 //BA.debugLineNum = 760;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 761;BA.debugLine="ToastMessageShow(\"Evaluación enviada\", False)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Evaluación enviada"),anywheresoftware.b4a.keywords.Common.False);
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 763;BA.debugLine="ToastMessageShow(\"Report sent\", False)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Report sent"),anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 767;BA.debugLine="Dim Map1 As Map";
_map1 = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 768;BA.debugLine="Map1.Initialize";
_map1.Initialize();
 //BA.debugLineNum = 769;BA.debugLine="Map1.Put(\"Id\", Main.currentproject)";
_map1.Put((Object)("Id"),(Object)(mostCurrent._main._currentproject /*String*/ ));
 //BA.debugLineNum = 770;BA.debugLine="DBUtils.UpdateRecord(Starter.modulo_hidro_sqlDB,";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._modulo_hidro_sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals_hidro","foto1sent",(Object)("si"),_map1);
 //BA.debugLineNum = 772;BA.debugLine="frmFelicitaciones.tiporio = tiporio";
mostCurrent._frmfelicitaciones._tiporio /*String*/  = mostCurrent._tiporio;
 //BA.debugLineNum = 773;BA.debugLine="StartActivity(frmFelicitaciones)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._frmfelicitaciones.getObject()));
 //BA.debugLineNum = 775;BA.debugLine="TimerEnvio.Enabled = False";
_timerenvio.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 776;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 777;BA.debugLine="Activity.finish";
mostCurrent._activity.Finish();
 };
 //BA.debugLineNum = 779;BA.debugLine="End Sub";
return "";
}
public static String  _up1_sendfile(String _value) throws Exception{
 //BA.debugLineNum = 786;BA.debugLine="Sub Up1_sendFile (value As String)";
 //BA.debugLineNum = 787;BA.debugLine="Log(\"sendfile event:\" & value)";
anywheresoftware.b4a.keywords.Common.LogImpl("043253761","sendfile event:"+_value,0);
 //BA.debugLineNum = 788;BA.debugLine="If value = \"success\" Then";
if ((_value).equals("success")) { 
 //BA.debugLineNum = 790;BA.debugLine="If fotosEnviadas = 0 And totalFotos >= 1 Then";
if (_fotosenviadas==0 && _totalfotos>=1) { 
 //BA.debugLineNum = 791;BA.debugLine="fotosEnviadas = 1";
_fotosenviadas = (int) (1);
 //BA.debugLineNum = 792;BA.debugLine="Return";
if (true) return "";
 };
 }else if((_value).equals("Error!")) { 
 //BA.debugLineNum = 796;BA.debugLine="Log(\"FOTO error\")";
anywheresoftware.b4a.keywords.Common.LogImpl("043253770","FOTO error",0);
 //BA.debugLineNum = 797;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 798;BA.debugLine="MsgboxAsync(\"Ha habido un error en el envío. Re";
anywheresoftware.b4a.keywords.Common.MsgboxAsync(BA.ObjectToCharSequence("Ha habido un error en el envío. Revisa tu conexión a Internet e intenta de nuevo desde 'Datos sin enviar'"),BA.ObjectToCharSequence("Oops!"),processBA);
 };
 //BA.debugLineNum = 800;BA.debugLine="Up1.UploadKill";
_up1.UploadKill(processBA);
 //BA.debugLineNum = 801;BA.debugLine="TimerEnvio.Enabled = False";
_timerenvio.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 802;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 803;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 804;BA.debugLine="Activity_Create(False)";
_activity_create(anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 807;BA.debugLine="End Sub";
return "";
}
public static String  _up1_statusupload(String _value) throws Exception{
 //BA.debugLineNum = 782;BA.debugLine="Sub Up1_statusUpload (value As String)";
 //BA.debugLineNum = 784;BA.debugLine="End Sub";
return "";
}
public static String  _up1_statusvisible(boolean _onoff,String _value) throws Exception{
 //BA.debugLineNum = 808;BA.debugLine="Sub Up1_statusVISIBLE (onoff As Boolean,value As S";
 //BA.debugLineNum = 810;BA.debugLine="End Sub";
return "";
}
}
