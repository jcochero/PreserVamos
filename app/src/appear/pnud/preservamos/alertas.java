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

public class alertas extends Activity implements B4AActivity{
	public static alertas mostCurrent;
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
			processBA = new BA(this.getApplicationContext(), null, null, "appear.pnud.preservamos", "appear.pnud.preservamos.alertas");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
            
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (alertas).");
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
		activityBA = new BA(this, layout, processBA, "appear.pnud.preservamos", "appear.pnud.preservamos.alertas");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "appear.pnud.preservamos.alertas", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density, mostCurrent);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (alertas) Create " + (isFirst ? "(first time)" : "") + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (alertas) Resume **");
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
		return alertas.class;
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
            BA.LogInfo("** Activity (alertas) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
        else
            BA.LogInfo("** Activity (alertas) Pause event (activity is not paused). **");
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
            alertas mc = mostCurrent;
			if (mc == null || mc != activity.get())
				return;
			processBA.setActivityPaused(false);
            BA.LogInfo("** Activity (alertas) Resume **");
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
public static String _alerta_lat = "";
public static String _alerta_lng = "";
public static String _alerta_foto1 = "";
public static String _alerta_foto2 = "";
public static String _alerta_foto3 = "";
public static String _alerta_foto4 = "";
public static com.spinter.uploadfilephp.UploadFilePhp _up1 = null;
public static String _geopartido = "";
public static String _tipo_alerta = "";
public static anywheresoftware.b4a.objects.Timer _timerenvio = null;
public static String _dateandtime = "";
public static String _tiporio = "";
public static String _username = "";
public static String _notas = "";
public static String _partido = "";
public static String _telefono = "";
public anywheresoftware.b4a.objects.SpinnerWrapper _cmbmunicipio = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblrojo = null;
public anywheresoftware.b4a.objects.TabStripViewPager _tabalertas = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnprev = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnnext = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblcirculopos1 = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblcirculopos2 = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblcirculopos3 = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imgpeces = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imgderrame = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imgalgas = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblotraalerta = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imgotraalerta = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblcoordenadastitle = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imgfoto1 = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imgfoto2 = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imgfoto3 = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imgfoto4 = null;
public anywheresoftware.b4a.objects.EditTextWrapper _txttelefono = null;
public anywheresoftware.b4a.objects.EditTextWrapper _txtnotas = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnenviaralerta = null;
public anywheresoftware.b4a.phone.Phone.PhoneIntents _pi = null;
public anywheresoftware.b4a.objects.LabelWrapper _fondoblanco = null;
public anywheresoftware.b4a.objects.ProgressBarWrapper _progressbar1 = null;
public anywheresoftware.b4a.objects.ProgressBarWrapper _progressbar2 = null;
public anywheresoftware.b4a.objects.ProgressBarWrapper _progressbar3 = null;
public anywheresoftware.b4a.objects.ProgressBarWrapper _progressbar4 = null;
public static int _totalfotos = 0;
public static boolean _foto1sent = false;
public static boolean _foto2sent = false;
public static boolean _foto3sent = false;
public static boolean _foto4sent = false;
public static int _fotosenviadas = 0;
public anywheresoftware.b4a.objects.LabelWrapper _lblcaza = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imgcaza = null;
public anywheresoftware.b4a.objects.LabelWrapper _borderfotos1 = null;
public anywheresoftware.b4a.objects.LabelWrapper _borderfotos2 = null;
public anywheresoftware.b4a.objects.LabelWrapper _borderfotos3 = null;
public anywheresoftware.b4a.objects.LabelWrapper _borderfotos4 = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblfotostitle = null;
public anywheresoftware.b4a.objects.ScrollViewWrapper _scralertas = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imgdidymo = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imgfumigaciones = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imgtomasilegales = null;
public anywheresoftware.b4a.objects.PanelWrapper _pnlinstrucciones = null;
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
public static String  _activity_create(boolean _firsttime) throws Exception{
 //BA.debugLineNum = 89;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
 //BA.debugLineNum = 90;BA.debugLine="Activity.LoadLayout(\"Alertas_Main\")";
mostCurrent._activity.LoadLayout("Alertas_Main",mostCurrent.activityBA);
 //BA.debugLineNum = 92;BA.debugLine="tabAlertas.LoadLayout(\"Alertas_Tab1\", \"1\")";
mostCurrent._tabalertas.LoadLayout("Alertas_Tab1",BA.ObjectToCharSequence("1"));
 //BA.debugLineNum = 93;BA.debugLine="tabAlertas.LoadLayout(\"Alertas_Tab2\", \"2\")";
mostCurrent._tabalertas.LoadLayout("Alertas_Tab2",BA.ObjectToCharSequence("2"));
 //BA.debugLineNum = 94;BA.debugLine="tabAlertas.LoadLayout(\"Alertas_Tab3\", \"3\")";
mostCurrent._tabalertas.LoadLayout("Alertas_Tab3",BA.ObjectToCharSequence("3"));
 //BA.debugLineNum = 95;BA.debugLine="scrAlertas.Panel.LoadLayout(\"Alertas_Tipo_Alertas";
mostCurrent._scralertas.getPanel().LoadLayout("Alertas_Tipo_Alertas",mostCurrent.activityBA);
 //BA.debugLineNum = 96;BA.debugLine="scrAlertas.Panel.Height = lblOtraAlerta.Top + lbl";
mostCurrent._scralertas.getPanel().setHeight((int) (mostCurrent._lblotraalerta.getTop()+mostCurrent._lblotraalerta.getHeight()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (15))));
 //BA.debugLineNum = 97;BA.debugLine="Alerta_lat = \"\"";
_alerta_lat = "";
 //BA.debugLineNum = 98;BA.debugLine="Alerta_lng = \"\"";
_alerta_lng = "";
 //BA.debugLineNum = 99;BA.debugLine="Alerta_foto1 = \"\"";
_alerta_foto1 = "";
 //BA.debugLineNum = 100;BA.debugLine="Alerta_foto2 = \"\"";
_alerta_foto2 = "";
 //BA.debugLineNum = 101;BA.debugLine="Alerta_foto3 = \"\"";
_alerta_foto3 = "";
 //BA.debugLineNum = 102;BA.debugLine="tipo_alerta = \"\"";
_tipo_alerta = "";
 //BA.debugLineNum = 103;BA.debugLine="Up1.Initialize(\"Up1\")";
_up1.Initialize(processBA,"Up1");
 //BA.debugLineNum = 104;BA.debugLine="End Sub";
return "";
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 110;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 112;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 106;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 108;BA.debugLine="End Sub";
return "";
}
public static String  _actualizarcoordenadas() throws Exception{
 //BA.debugLineNum = 236;BA.debugLine="Sub ActualizarCoordenadas";
 //BA.debugLineNum = 237;BA.debugLine="lblCoordenadasTitle.Text = \"Coordenadas: \" & Roun";
mostCurrent._lblcoordenadastitle.setText(BA.ObjectToCharSequence("Coordenadas: "+BA.NumberToString(anywheresoftware.b4a.keywords.Common.Round2((double)(Double.parseDouble(_alerta_lat)),(int) (3)))+" /// "+BA.NumberToString(anywheresoftware.b4a.keywords.Common.Round2((double)(Double.parseDouble(_alerta_lng)),(int) (3)))));
 //BA.debugLineNum = 238;BA.debugLine="If Alerta_foto1 = \"\" And Alerta_foto2 = \"\" And Al";
if ((_alerta_foto1).equals("") && (_alerta_foto2).equals("") && (_alerta_foto3).equals("") && (_alerta_foto4).equals("") && (_tipo_alerta).equals("caza") == false) { 
 //BA.debugLineNum = 239;BA.debugLine="StartActivity(Alerta_Fotos)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._alerta_fotos.getObject()));
 };
 //BA.debugLineNum = 241;BA.debugLine="End Sub";
return "";
}
public static String  _actualizarfotos() throws Exception{
 //BA.debugLineNum = 270;BA.debugLine="Sub ActualizarFotos";
 //BA.debugLineNum = 271;BA.debugLine="If tipo_alerta = \"caza\" Then";
if ((_tipo_alerta).equals("caza")) { 
 //BA.debugLineNum = 272;BA.debugLine="ProgressBar1.Visible = False";
mostCurrent._progressbar1.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 273;BA.debugLine="ProgressBar2.Visible = False";
mostCurrent._progressbar2.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 274;BA.debugLine="ProgressBar3.Visible = False";
mostCurrent._progressbar3.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 275;BA.debugLine="ProgressBar4.Visible = False";
mostCurrent._progressbar4.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 276;BA.debugLine="borderFotos1.Visible = False";
mostCurrent._borderfotos1.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 277;BA.debugLine="borderFotos2.Visible = False";
mostCurrent._borderfotos2.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 278;BA.debugLine="borderFotos3.Visible = False";
mostCurrent._borderfotos3.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 279;BA.debugLine="borderFotos4.Visible = False";
mostCurrent._borderfotos4.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 280;BA.debugLine="imgFoto1.Visible = False";
mostCurrent._imgfoto1.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 281;BA.debugLine="imgFoto2.Visible = False";
mostCurrent._imgfoto2.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 282;BA.debugLine="imgFoto3.Visible = False";
mostCurrent._imgfoto3.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 283;BA.debugLine="imgFoto4.Visible = False";
mostCurrent._imgfoto4.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 284;BA.debugLine="lblFotosTitle.Visible = False";
mostCurrent._lblfotostitle.setVisible(anywheresoftware.b4a.keywords.Common.False);
 }else {
 //BA.debugLineNum = 286;BA.debugLine="ProgressBar1.Visible = False";
mostCurrent._progressbar1.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 287;BA.debugLine="ProgressBar2.Visible = False";
mostCurrent._progressbar2.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 288;BA.debugLine="ProgressBar3.Visible = False";
mostCurrent._progressbar3.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 289;BA.debugLine="ProgressBar4.Visible = False";
mostCurrent._progressbar4.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 290;BA.debugLine="borderFotos1.Visible = True";
mostCurrent._borderfotos1.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 291;BA.debugLine="borderFotos2.Visible = True";
mostCurrent._borderfotos2.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 292;BA.debugLine="borderFotos3.Visible = True";
mostCurrent._borderfotos3.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 293;BA.debugLine="borderFotos4.Visible = True";
mostCurrent._borderfotos4.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 294;BA.debugLine="imgFoto1.Visible = True";
mostCurrent._imgfoto1.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 295;BA.debugLine="imgFoto2.Visible = True";
mostCurrent._imgfoto2.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 296;BA.debugLine="imgFoto3.Visible = True";
mostCurrent._imgfoto3.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 297;BA.debugLine="imgFoto4.Visible = True";
mostCurrent._imgfoto4.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 298;BA.debugLine="lblFotosTitle.Visible = True";
mostCurrent._lblfotostitle.setVisible(anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 300;BA.debugLine="If File.Exists(Starter.savedir, Alerta_foto1 & \".";
if (anywheresoftware.b4a.keywords.Common.File.Exists(mostCurrent._starter._savedir /*String*/ ,_alerta_foto1+".jpg")) { 
 //BA.debugLineNum = 301;BA.debugLine="imgFoto1.Bitmap = Null";
mostCurrent._imgfoto1.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null));
 //BA.debugLineNum = 302;BA.debugLine="imgFoto1.Bitmap = LoadBitmapSample(Starter.saved";
mostCurrent._imgfoto1.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(mostCurrent._starter._savedir /*String*/ ,_alerta_foto1+".jpg",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 303;BA.debugLine="imgFoto1.Visible = True";
mostCurrent._imgfoto1.setVisible(anywheresoftware.b4a.keywords.Common.True);
 }else {
 //BA.debugLineNum = 305;BA.debugLine="imgFoto1.Visible = False";
mostCurrent._imgfoto1.setVisible(anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 307;BA.debugLine="If File.Exists(Starter.savedir, Alerta_foto2 & \".";
if (anywheresoftware.b4a.keywords.Common.File.Exists(mostCurrent._starter._savedir /*String*/ ,_alerta_foto2+".jpg")) { 
 //BA.debugLineNum = 308;BA.debugLine="imgFoto2.Bitmap = Null";
mostCurrent._imgfoto2.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null));
 //BA.debugLineNum = 309;BA.debugLine="imgFoto2.Bitmap = LoadBitmapSample(Starter.saved";
mostCurrent._imgfoto2.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(mostCurrent._starter._savedir /*String*/ ,_alerta_foto2+".jpg",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 310;BA.debugLine="imgFoto2.Visible = True";
mostCurrent._imgfoto2.setVisible(anywheresoftware.b4a.keywords.Common.True);
 }else {
 //BA.debugLineNum = 312;BA.debugLine="imgFoto2.Visible = False";
mostCurrent._imgfoto2.setVisible(anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 314;BA.debugLine="If File.Exists(Starter.savedir, Alerta_foto3 & \".";
if (anywheresoftware.b4a.keywords.Common.File.Exists(mostCurrent._starter._savedir /*String*/ ,_alerta_foto3+".jpg")) { 
 //BA.debugLineNum = 315;BA.debugLine="imgFoto3.Bitmap = Null";
mostCurrent._imgfoto3.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null));
 //BA.debugLineNum = 316;BA.debugLine="imgFoto3.Bitmap = LoadBitmapSample(Starter.saved";
mostCurrent._imgfoto3.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(mostCurrent._starter._savedir /*String*/ ,_alerta_foto3+".jpg",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 317;BA.debugLine="imgFoto3.Visible = True";
mostCurrent._imgfoto3.setVisible(anywheresoftware.b4a.keywords.Common.True);
 }else {
 //BA.debugLineNum = 320;BA.debugLine="imgFoto3.Visible = False";
mostCurrent._imgfoto3.setVisible(anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 322;BA.debugLine="If File.Exists(Starter.savedir, Alerta_foto4 & \".";
if (anywheresoftware.b4a.keywords.Common.File.Exists(mostCurrent._starter._savedir /*String*/ ,_alerta_foto4+".jpg")) { 
 //BA.debugLineNum = 323;BA.debugLine="imgFoto4.Bitmap = Null";
mostCurrent._imgfoto4.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null));
 //BA.debugLineNum = 324;BA.debugLine="imgFoto4.Bitmap = LoadBitmapSample(Starter.saved";
mostCurrent._imgfoto4.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(mostCurrent._starter._savedir /*String*/ ,_alerta_foto4+".jpg",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 325;BA.debugLine="imgFoto4.Visible = True";
mostCurrent._imgfoto4.setVisible(anywheresoftware.b4a.keywords.Common.True);
 }else {
 //BA.debugLineNum = 328;BA.debugLine="imgFoto4.Visible = False";
mostCurrent._imgfoto4.setVisible(anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 330;BA.debugLine="End Sub";
return "";
}
public static String  _btncerraralertas_click() throws Exception{
 //BA.debugLineNum = 331;BA.debugLine="Private Sub btnCerrarAlertas_Click";
 //BA.debugLineNum = 332;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 333;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 334;BA.debugLine="CallSubDelayed(Form_Main, \"tabResetToAnalizar\")";
anywheresoftware.b4a.keywords.Common.CallSubDelayed(processBA,(Object)(mostCurrent._form_main.getObject()),"tabResetToAnalizar");
 //BA.debugLineNum = 336;BA.debugLine="End Sub";
return "";
}
public static String  _btneditcoordenadas_click() throws Exception{
 //BA.debugLineNum = 187;BA.debugLine="Private Sub btnEditCoordenadas_Click";
 //BA.debugLineNum = 189;BA.debugLine="tabAlertas.ScrollTo(2, True)";
mostCurrent._tabalertas.ScrollTo((int) (2),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 190;BA.debugLine="frmLocalizacion.origen = \"alerta\"";
mostCurrent._frmlocalizacion._origen /*String*/  = "alerta";
 //BA.debugLineNum = 191;BA.debugLine="StartActivity(frmLocalizacion)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._frmlocalizacion.getObject()));
 //BA.debugLineNum = 192;BA.debugLine="End Sub";
return "";
}
public static void  _btnenviaralerta_click() throws Exception{
ResumableSub_btnEnviarAlerta_Click rsub = new ResumableSub_btnEnviarAlerta_Click(null);
rsub.resume(processBA, null);
}
public static class ResumableSub_btnEnviarAlerta_Click extends BA.ResumableSub {
public ResumableSub_btnEnviarAlerta_Click(appear.pnud.preservamos.alertas parent) {
this.parent = parent;
}
appear.pnud.preservamos.alertas parent;
int _result = 0;
boolean _completed = false;
anywheresoftware.b4a.objects.LabelWrapper _fondogris = null;
anywheresoftware.b4a.objects.LabelWrapper _pnlmunititle = null;
anywheresoftware.b4a.objects.PanelWrapper _pnlmunicipio = null;
anywheresoftware.b4a.objects.collections.List _list1 = null;
anywheresoftware.b4a.objects.ButtonWrapper _butokmunicipio = null;

@Override
public void resume(BA ba, Object[] result) throws Exception{

    while (true) {
        switch (state) {
            case -1:
return;

case 0:
//C
this.state = 1;
 //BA.debugLineNum = 342;BA.debugLine="Msgbox2Async(\"Tu localidad detectada es \" & Geopa";
anywheresoftware.b4a.keywords.Common.Msgbox2Async(BA.ObjectToCharSequence("Tu localidad detectada es "+parent._geopartido+". ¿Deseas enviar el alerta a ese municipio, o seleccionar otro?"),BA.ObjectToCharSequence("Municipio de acción"),"Si, usa ese municipio","","No, seleccionar otro",(anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper(), (android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null)),processBA,anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 343;BA.debugLine="Wait For Msgbox_Result (Result As Int)";
anywheresoftware.b4a.keywords.Common.WaitFor("msgbox_result", processBA, this, null);
this.state = 17;
return;
case 17:
//C
this.state = 1;
_result = (Integer) result[0];
;
 //BA.debugLineNum = 344;BA.debugLine="If Result = DialogResponse.POSITIVE Then";
if (true) break;

case 1:
//if
this.state = 16;
if (_result==anywheresoftware.b4a.keywords.Common.DialogResponse.POSITIVE) { 
this.state = 3;
}else if(_result==anywheresoftware.b4a.keywords.Common.DialogResponse.NEGATIVE) { 
this.state = 15;
}if (true) break;

case 3:
//C
this.state = 4;
 //BA.debugLineNum = 345;BA.debugLine="ProgressDialogShow2(\"¡Enviando alerta!\", False)";
anywheresoftware.b4a.keywords.Common.ProgressDialogShow2(mostCurrent.activityBA,BA.ObjectToCharSequence("¡Enviando alerta!"),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 346;BA.debugLine="Log(\"Chequeando internet\")";
anywheresoftware.b4a.keywords.Common.LogImpl("022413319","Chequeando internet",0);
 //BA.debugLineNum = 347;BA.debugLine="Wait For (CheckInternet) Complete (Completed As";
anywheresoftware.b4a.keywords.Common.WaitFor("complete", processBA, this, _checkinternet());
this.state = 18;
return;
case 18:
//C
this.state = 4;
_completed = (Boolean) result[0];
;
 //BA.debugLineNum = 349;BA.debugLine="If Completed = False Then";
if (true) break;

case 4:
//if
this.state = 7;
if (_completed==anywheresoftware.b4a.keywords.Common.False) { 
this.state = 6;
}if (true) break;

case 6:
//C
this.state = 7;
 //BA.debugLineNum = 350;BA.debugLine="ToastMessageShow(\"No hay internet!\", False)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("No hay internet!"),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 351;BA.debugLine="Return";
if (true) return ;
 if (true) break;

case 7:
//C
this.state = 8;
;
 //BA.debugLineNum = 354;BA.debugLine="Wait For (EnviarDatos) Complete (Completed As Bo";
anywheresoftware.b4a.keywords.Common.WaitFor("complete", processBA, this, _enviardatos());
this.state = 19;
return;
case 19:
//C
this.state = 8;
_completed = (Boolean) result[0];
;
 //BA.debugLineNum = 356;BA.debugLine="If Completed = True Then";
if (true) break;

case 8:
//if
this.state = 13;
if (_completed==anywheresoftware.b4a.keywords.Common.True) { 
this.state = 10;
}else {
this.state = 12;
}if (true) break;

case 10:
//C
this.state = 13;
 //BA.debugLineNum = 358;BA.debugLine="EnviarFotos";
_enviarfotos();
 if (true) break;

case 12:
//C
this.state = 13;
 //BA.debugLineNum = 360;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 //BA.debugLineNum = 361;BA.debugLine="ToastMessageShow(\"Se guardó el dato\", True)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Se guardó el dato"),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 362;BA.debugLine="Activity.RemoveAllViews";
parent.mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 363;BA.debugLine="Activity.Finish";
parent.mostCurrent._activity.Finish();
 if (true) break;

case 13:
//C
this.state = 16;
;
 if (true) break;

case 15:
//C
this.state = 16;
 //BA.debugLineNum = 368;BA.debugLine="Dim fondogris As Label";
_fondogris = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 369;BA.debugLine="fondogris.Initialize(\"fondogris\")";
_fondogris.Initialize(mostCurrent.activityBA,"fondogris");
 //BA.debugLineNum = 370;BA.debugLine="fondogris.Color = Colors.ARGB(150,0,0,0)";
_fondogris.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (150),(int) (0),(int) (0),(int) (0)));
 //BA.debugLineNum = 371;BA.debugLine="Activity.AddView(fondogris, 0, 0, 100%x, 100%y)";
parent.mostCurrent._activity.AddView((android.view.View)(_fondogris.getObject()),(int) (0),(int) (0),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA));
 //BA.debugLineNum = 373;BA.debugLine="Dim pnlMuniTitle As Label";
_pnlmunititle = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 374;BA.debugLine="pnlMuniTitle.Initialize(\"pnlMuniTitle\")";
_pnlmunititle.Initialize(mostCurrent.activityBA,"pnlMuniTitle");
 //BA.debugLineNum = 375;BA.debugLine="pnlMuniTitle.Text = \"Elije el municipio adonde o";
_pnlmunititle.setText(BA.ObjectToCharSequence("Elije el municipio adonde ocurre el evento"));
 //BA.debugLineNum = 376;BA.debugLine="pnlMuniTitle.TextColor = Colors.Black";
_pnlmunititle.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 377;BA.debugLine="pnlMuniTitle.TextSize = 16";
_pnlmunititle.setTextSize((float) (16));
 //BA.debugLineNum = 379;BA.debugLine="Dim pnlMunicipio As Panel";
_pnlmunicipio = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 380;BA.debugLine="pnlMunicipio.Initialize(\"pnlMunicipio\")";
_pnlmunicipio.Initialize(mostCurrent.activityBA,"pnlMunicipio");
 //BA.debugLineNum = 381;BA.debugLine="pnlMunicipio.Color = Colors.White";
_pnlmunicipio.setColor(anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 384;BA.debugLine="cmbMunicipio.Initialize(\"cmbMunicipio\")";
parent.mostCurrent._cmbmunicipio.Initialize(mostCurrent.activityBA,"cmbMunicipio");
 //BA.debugLineNum = 385;BA.debugLine="cmbMunicipio.TextColor = Colors.Black";
parent.mostCurrent._cmbmunicipio.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 386;BA.debugLine="Dim List1 As List";
_list1 = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 387;BA.debugLine="List1 = File.ReadList(File.DirAssets, \"partidos.";
_list1 = anywheresoftware.b4a.keywords.Common.File.ReadList(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"partidos.txt");
 //BA.debugLineNum = 388;BA.debugLine="cmbMunicipio.AddAll(List1)";
parent.mostCurrent._cmbmunicipio.AddAll(_list1);
 //BA.debugLineNum = 389;BA.debugLine="cmbMunicipio.SelectedIndex = cmbMunicipio.IndexO";
parent.mostCurrent._cmbmunicipio.setSelectedIndex(parent.mostCurrent._cmbmunicipio.IndexOf(parent.mostCurrent._main._struserorg /*String*/ ));
 //BA.debugLineNum = 391;BA.debugLine="Dim butOkMunicipio As Button";
_butokmunicipio = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 392;BA.debugLine="butOkMunicipio.Initialize(\"butOkMunicipio\")";
_butokmunicipio.Initialize(mostCurrent.activityBA,"butOkMunicipio");
 //BA.debugLineNum = 393;BA.debugLine="butOkMunicipio.Text = \"¡Enviar alerta!\"";
_butokmunicipio.setText(BA.ObjectToCharSequence("¡Enviar alerta!"));
 //BA.debugLineNum = 394;BA.debugLine="butOkMunicipio.Width = 25%y";
_butokmunicipio.setWidth(anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (25),mostCurrent.activityBA));
 //BA.debugLineNum = 395;BA.debugLine="butOkMunicipio.Color = Colors.RGB(244, 65, 67)";
_butokmunicipio.setColor(anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (244),(int) (65),(int) (67)));
 //BA.debugLineNum = 396;BA.debugLine="butOkMunicipio.TextColor = Colors.Black";
_butokmunicipio.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 397;BA.debugLine="butOkMunicipio.TextSize = 12";
_butokmunicipio.setTextSize((float) (12));
 //BA.debugLineNum = 399;BA.debugLine="Activity.AddView(pnlMunicipio, 10%x, 25%y, 80%x,";
parent.mostCurrent._activity.AddView((android.view.View)(_pnlmunicipio.getObject()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (10),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (25),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (80),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (30),mostCurrent.activityBA));
 //BA.debugLineNum = 400;BA.debugLine="pnlMunicipio.AddView (pnlMuniTitle, 5%x, 5%y, 70";
_pnlmunicipio.AddView((android.view.View)(_pnlmunititle.getObject()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (5),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (5),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (70),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (40)));
 //BA.debugLineNum = 401;BA.debugLine="pnlMunicipio.AddView (cmbMunicipio, 5%x, 10%y, 7";
_pnlmunicipio.AddView((android.view.View)(parent.mostCurrent._cmbmunicipio.getObject()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (5),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (10),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (70),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (60)));
 //BA.debugLineNum = 402;BA.debugLine="pnlMunicipio.AddView (butOkMunicipio, 5%x, cmbMu";
_pnlmunicipio.AddView((android.view.View)(_butokmunicipio.getObject()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (5),mostCurrent.activityBA),(int) (parent.mostCurrent._cmbmunicipio.getTop()+parent.mostCurrent._cmbmunicipio.getHeight()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (15))),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (70),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (50)));
 //BA.debugLineNum = 403;BA.debugLine="Return";
if (true) return ;
 if (true) break;

case 16:
//C
this.state = -1;
;
 //BA.debugLineNum = 405;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public static void  _msgbox_result(int _result) throws Exception{
}
public static void  _complete(boolean _completed) throws Exception{
}
public static String  _btninfocianosemaforo_click() throws Exception{
 //BA.debugLineNum = 263;BA.debugLine="Private Sub btnInfoCianosemaforo_Click";
 //BA.debugLineNum = 264;BA.debugLine="StartActivity(pi.OpenBrowser(\"https://www.gba.gob";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._pi.OpenBrowser("https://www.gba.gob.ar/cianobacterias")));
 //BA.debugLineNum = 265;BA.debugLine="End Sub";
return "";
}
public static String  _btnnext_click() throws Exception{
 //BA.debugLineNum = 195;BA.debugLine="Private Sub btnNext_Click";
 //BA.debugLineNum = 196;BA.debugLine="If tabAlertas.CurrentPage = 0 Then";
if (mostCurrent._tabalertas.getCurrentPage()==0) { 
 //BA.debugLineNum = 197;BA.debugLine="tabAlertas.ScrollTo(1, True)";
mostCurrent._tabalertas.ScrollTo((int) (1),anywheresoftware.b4a.keywords.Common.True);
 }else if(mostCurrent._tabalertas.getCurrentPage()==1) { 
 //BA.debugLineNum = 199;BA.debugLine="If lblRojo.Visible = False Then";
if (mostCurrent._lblrojo.getVisible()==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 200;BA.debugLine="ToastMessageShow(\"Debe seleccionar un tipo de a";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Debe seleccionar un tipo de alerta verde"),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 201;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 204;BA.debugLine="If tipo_alerta = \"floracion\" Then";
if ((_tipo_alerta).equals("floracion")) { 
 //BA.debugLineNum = 205;BA.debugLine="pnlInstrucciones.Visible = True";
mostCurrent._pnlinstrucciones.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 206;BA.debugLine="btnNext.Visible = False";
mostCurrent._btnnext.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 207;BA.debugLine="btnPrev.Visible = False";
mostCurrent._btnprev.setVisible(anywheresoftware.b4a.keywords.Common.False);
 }else {
 //BA.debugLineNum = 209;BA.debugLine="If Alerta_lat = \"\" Then";
if ((_alerta_lat).equals("")) { 
 //BA.debugLineNum = 211;BA.debugLine="tabAlertas.ScrollTo(2, True)";
mostCurrent._tabalertas.ScrollTo((int) (2),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 212;BA.debugLine="frmLocalizacion.origen = \"alerta\"";
mostCurrent._frmlocalizacion._origen /*String*/  = "alerta";
 //BA.debugLineNum = 213;BA.debugLine="StartActivity(frmLocalizacion)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._frmlocalizacion.getObject()));
 }else {
 //BA.debugLineNum = 215;BA.debugLine="tabAlertas.ScrollTo(2, True)";
mostCurrent._tabalertas.ScrollTo((int) (2),anywheresoftware.b4a.keywords.Common.True);
 };
 };
 };
 //BA.debugLineNum = 222;BA.debugLine="End Sub";
return "";
}
public static String  _btnprev_click() throws Exception{
 //BA.debugLineNum = 223;BA.debugLine="Private Sub btnPrev_Click";
 //BA.debugLineNum = 224;BA.debugLine="If tabAlertas.CurrentPage = 0 Then";
if (mostCurrent._tabalertas.getCurrentPage()==0) { 
 //BA.debugLineNum = 225;BA.debugLine="btnPrev.Visible = False";
mostCurrent._btnprev.setVisible(anywheresoftware.b4a.keywords.Common.False);
 }else if(mostCurrent._tabalertas.getCurrentPage()==1) { 
 //BA.debugLineNum = 227;BA.debugLine="tabAlertas.ScrollTo(0, True)";
mostCurrent._tabalertas.ScrollTo((int) (0),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 228;BA.debugLine="btnPrev.Visible = True";
mostCurrent._btnprev.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 229;BA.debugLine="btnNext.Visible = True";
mostCurrent._btnnext.setVisible(anywheresoftware.b4a.keywords.Common.True);
 }else if(mostCurrent._tabalertas.getCurrentPage()==2) { 
 //BA.debugLineNum = 231;BA.debugLine="tabAlertas.ScrollTo(1, True)";
mostCurrent._tabalertas.ScrollTo((int) (1),anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 233;BA.debugLine="End Sub";
return "";
}
public static void  _butokmunicipio_click() throws Exception{
ResumableSub_butOkMunicipio_Click rsub = new ResumableSub_butOkMunicipio_Click(null);
rsub.resume(processBA, null);
}
public static class ResumableSub_butOkMunicipio_Click extends BA.ResumableSub {
public ResumableSub_butOkMunicipio_Click(appear.pnud.preservamos.alertas parent) {
this.parent = parent;
}
appear.pnud.preservamos.alertas parent;
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
 //BA.debugLineNum = 408;BA.debugLine="If cmbMunicipio.SelectedItem = \"<Seleccionar part";
if (true) break;

case 1:
//if
this.state = 4;
if ((parent.mostCurrent._cmbmunicipio.getSelectedItem()).equals("<Seleccionar partido>") || (parent.mostCurrent._cmbmunicipio.getSelectedItem()).equals("")) { 
this.state = 3;
}if (true) break;

case 3:
//C
this.state = 4;
 //BA.debugLineNum = 409;BA.debugLine="MsgboxAsync(\"Debe seleccionar un municipio para";
anywheresoftware.b4a.keywords.Common.MsgboxAsync(BA.ObjectToCharSequence("Debe seleccionar un municipio para enviar el alerta"),BA.ObjectToCharSequence("Error"),processBA);
 //BA.debugLineNum = 410;BA.debugLine="Return";
if (true) return ;
 if (true) break;

case 4:
//C
this.state = 5;
;
 //BA.debugLineNum = 413;BA.debugLine="Msgbox2Async(\"¿Confirmas enviar el alerta verde a";
anywheresoftware.b4a.keywords.Common.Msgbox2Async(BA.ObjectToCharSequence("¿Confirmas enviar el alerta verde al municipio de "+parent.mostCurrent._cmbmunicipio.getSelectedItem()+" ?"),BA.ObjectToCharSequence("Municipio de acción"),"Si, envía el alerta verde","","No, seleccionar otro",(anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper(), (android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null)),processBA,anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 415;BA.debugLine="Wait For Msgbox_Result (Result As Int)";
anywheresoftware.b4a.keywords.Common.WaitFor("msgbox_result", processBA, this, null);
this.state = 21;
return;
case 21:
//C
this.state = 5;
_result = (Integer) result[0];
;
 //BA.debugLineNum = 416;BA.debugLine="If Result = DialogResponse.POSITIVE Then";
if (true) break;

case 5:
//if
this.state = 20;
if (_result==anywheresoftware.b4a.keywords.Common.DialogResponse.POSITIVE) { 
this.state = 7;
}else if(_result==anywheresoftware.b4a.keywords.Common.DialogResponse.NEGATIVE) { 
this.state = 19;
}if (true) break;

case 7:
//C
this.state = 8;
 //BA.debugLineNum = 417;BA.debugLine="Activity.RemoveViewAt(Activity.NumberOfViews - 1";
parent.mostCurrent._activity.RemoveViewAt((int) (parent.mostCurrent._activity.getNumberOfViews()-1));
 //BA.debugLineNum = 418;BA.debugLine="Activity.RemoveViewAt(Activity.NumberOfViews - 1";
parent.mostCurrent._activity.RemoveViewAt((int) (parent.mostCurrent._activity.getNumberOfViews()-1));
 //BA.debugLineNum = 419;BA.debugLine="ProgressDialogShow2(\"¡Enviando alerta verde!\", F";
anywheresoftware.b4a.keywords.Common.ProgressDialogShow2(mostCurrent.activityBA,BA.ObjectToCharSequence("¡Enviando alerta verde!"),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 420;BA.debugLine="Log(\"Chequeando internet\")";
anywheresoftware.b4a.keywords.Common.LogImpl("022478861","Chequeando internet",0);
 //BA.debugLineNum = 422;BA.debugLine="Wait For (CheckInternet) Complete (Completed As";
anywheresoftware.b4a.keywords.Common.WaitFor("complete", processBA, this, _checkinternet());
this.state = 22;
return;
case 22:
//C
this.state = 8;
_completed = (Boolean) result[0];
;
 //BA.debugLineNum = 424;BA.debugLine="If Completed = False Then";
if (true) break;

case 8:
//if
this.state = 11;
if (_completed==anywheresoftware.b4a.keywords.Common.False) { 
this.state = 10;
}if (true) break;

case 10:
//C
this.state = 11;
 //BA.debugLineNum = 425;BA.debugLine="ToastMessageShow(\"No hay internet!\", False)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("No hay internet!"),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 426;BA.debugLine="Return";
if (true) return ;
 if (true) break;

case 11:
//C
this.state = 12;
;
 //BA.debugLineNum = 429;BA.debugLine="Wait For (EnviarDatos) Complete (Completed As Bo";
anywheresoftware.b4a.keywords.Common.WaitFor("complete", processBA, this, _enviardatos());
this.state = 23;
return;
case 23:
//C
this.state = 12;
_completed = (Boolean) result[0];
;
 //BA.debugLineNum = 431;BA.debugLine="If Completed = True Then";
if (true) break;

case 12:
//if
this.state = 17;
if (_completed==anywheresoftware.b4a.keywords.Common.True) { 
this.state = 14;
}else {
this.state = 16;
}if (true) break;

case 14:
//C
this.state = 17;
 //BA.debugLineNum = 433;BA.debugLine="EnviarFotos";
_enviarfotos();
 if (true) break;

case 16:
//C
this.state = 17;
 //BA.debugLineNum = 435;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 //BA.debugLineNum = 436;BA.debugLine="ToastMessageShow(\"Se guardó el dato\", True)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Se guardó el dato"),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 437;BA.debugLine="Activity.RemoveAllViews";
parent.mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 438;BA.debugLine="Activity.Finish";
parent.mostCurrent._activity.Finish();
 if (true) break;

case 17:
//C
this.state = 20;
;
 if (true) break;

case 19:
//C
this.state = 20;
 //BA.debugLineNum = 442;BA.debugLine="Return";
if (true) return ;
 if (true) break;

case 20:
//C
this.state = -1;
;
 //BA.debugLineNum = 446;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public static anywheresoftware.b4a.keywords.Common.ResumableSubWrapper  _checkinternet() throws Exception{
ResumableSub_CheckInternet rsub = new ResumableSub_CheckInternet(null);
rsub.resume(processBA, null);
return (anywheresoftware.b4a.keywords.Common.ResumableSubWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.keywords.Common.ResumableSubWrapper(), rsub);
}
public static class ResumableSub_CheckInternet extends BA.ResumableSub {
public ResumableSub_CheckInternet(appear.pnud.preservamos.alertas parent) {
this.parent = parent;
}
appear.pnud.preservamos.alertas parent;
appear.pnud.preservamos.httpjob _j = null;
String _loginpath = "";
int _result = 0;

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
 //BA.debugLineNum = 456;BA.debugLine="Dim j As HttpJob";
_j = new appear.pnud.preservamos.httpjob();
 //BA.debugLineNum = 457;BA.debugLine="j.Initialize(\"\", Me)";
_j._initialize /*String*/ (processBA,"",alertas.getObject());
 //BA.debugLineNum = 458;BA.debugLine="Dim loginPath As String = Main.serverPath &  \"/\"";
_loginpath = parent.mostCurrent._main._serverpath /*String*/ +"/"+parent.mostCurrent._main._serverconnectionfolder /*String*/ +"/connecttest.php";
 //BA.debugLineNum = 459;BA.debugLine="j.Download(loginPath)";
_j._download /*String*/ (_loginpath);
 //BA.debugLineNum = 460;BA.debugLine="Wait For (j) JobDone(j As HttpJob)";
anywheresoftware.b4a.keywords.Common.WaitFor("jobdone", processBA, this, (Object)(_j));
this.state = 11;
return;
case 11:
//C
this.state = 1;
_j = (appear.pnud.preservamos.httpjob) result[0];
;
 //BA.debugLineNum = 462;BA.debugLine="If j.Success Then";
if (true) break;

case 1:
//if
this.state = 10;
if (_j._success /*boolean*/ ) { 
this.state = 3;
}else {
this.state = 5;
}if (true) break;

case 3:
//C
this.state = 10;
 //BA.debugLineNum = 464;BA.debugLine="j.Release";
_j._release /*String*/ ();
 //BA.debugLineNum = 466;BA.debugLine="Main.hayinternet = True";
parent.mostCurrent._main._hayinternet /*boolean*/  = anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 467;BA.debugLine="Return True";
if (true) {
anywheresoftware.b4a.keywords.Common.ReturnFromResumableSub(this,(Object)(anywheresoftware.b4a.keywords.Common.True));return;};
 if (true) break;

case 5:
//C
this.state = 6;
 //BA.debugLineNum = 470;BA.debugLine="Main.hayinternet = False";
parent.mostCurrent._main._hayinternet /*boolean*/  = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 471;BA.debugLine="Msgbox2Async(\"No tenés conexión a Internet, prue";
anywheresoftware.b4a.keywords.Common.Msgbox2Async(BA.ObjectToCharSequence("No tenés conexión a Internet, prueba enviar tu dato cuando estés conectado, desde el Menú > Datos sin enviar"),BA.ObjectToCharSequence("No hay internet"),"Ok, entiendo","","",(anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper(), (android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null)),processBA,anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 472;BA.debugLine="Wait For Msgbox_Result (Result As Int)";
anywheresoftware.b4a.keywords.Common.WaitFor("msgbox_result", processBA, this, null);
this.state = 12;
return;
case 12:
//C
this.state = 6;
_result = (Integer) result[0];
;
 //BA.debugLineNum = 473;BA.debugLine="If Result = DialogResponse.POSITIVE Then";
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
 //BA.debugLineNum = 474;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 if (true) break;

case 9:
//C
this.state = 10;
;
 //BA.debugLineNum = 476;BA.debugLine="j.Release";
_j._release /*String*/ ();
 //BA.debugLineNum = 477;BA.debugLine="Return False";
if (true) {
anywheresoftware.b4a.keywords.Common.ReturnFromResumableSub(this,(Object)(anywheresoftware.b4a.keywords.Common.False));return;};
 if (true) break;

case 10:
//C
this.state = -1;
;
 //BA.debugLineNum = 481;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public static void  _jobdone(appear.pnud.preservamos.httpjob _j) throws Exception{
}
public static anywheresoftware.b4a.keywords.Common.ResumableSubWrapper  _enviardatos() throws Exception{
ResumableSub_EnviarDatos rsub = new ResumableSub_EnviarDatos(null);
rsub.resume(processBA, null);
return (anywheresoftware.b4a.keywords.Common.ResumableSubWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.keywords.Common.ResumableSubWrapper(), rsub);
}
public static class ResumableSub_EnviarDatos extends BA.ResumableSub {
public ResumableSub_EnviarDatos(appear.pnud.preservamos.alertas parent) {
this.parent = parent;
}
appear.pnud.preservamos.alertas parent;
String _dirweb = "";
appear.pnud.preservamos.httpjob _j = null;
String _loginpath = "";
String _ret = "";
String _act = "";
anywheresoftware.b4a.objects.collections.JSONParser _parser = null;
int _result = 0;

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
 //BA.debugLineNum = 488;BA.debugLine="username = Main.strUserName";
parent.mostCurrent._username = parent.mostCurrent._main._strusername /*String*/ ;
 //BA.debugLineNum = 489;BA.debugLine="dateandtime = DateTime.Date(DateTime.Now)";
parent.mostCurrent._dateandtime = anywheresoftware.b4a.keywords.Common.DateTime.Date(anywheresoftware.b4a.keywords.Common.DateTime.getNow());
 //BA.debugLineNum = 490;BA.debugLine="partido = Geopartido";
parent.mostCurrent._partido = parent._geopartido;
 //BA.debugLineNum = 491;BA.debugLine="notas = txtNotas.Text";
parent.mostCurrent._notas = parent.mostCurrent._txtnotas.getText();
 //BA.debugLineNum = 492;BA.debugLine="telefono = txtTelefono.Text";
parent.mostCurrent._telefono = parent.mostCurrent._txttelefono.getText();
 //BA.debugLineNum = 494;BA.debugLine="Dim dirWeb As String";
_dirweb = "";
 //BA.debugLineNum = 495;BA.debugLine="dirWeb = Main.serverPath &  \"/\" & Main.serverConn";
_dirweb = parent.mostCurrent._main._serverpath /*String*/ +"/"+parent.mostCurrent._main._serverconnectionfolder /*String*/ +"/addAlerta.php";
 //BA.debugLineNum = 497;BA.debugLine="Log(\"Comienza envio de datos\")";
anywheresoftware.b4a.keywords.Common.LogImpl("022675468","Comienza envio de datos",0);
 //BA.debugLineNum = 500;BA.debugLine="Dim j As HttpJob";
_j = new appear.pnud.preservamos.httpjob();
 //BA.debugLineNum = 501;BA.debugLine="j.Initialize(\"\", Me)";
_j._initialize /*String*/ (processBA,"",alertas.getObject());
 //BA.debugLineNum = 502;BA.debugLine="Dim loginPath As String = dirWeb & \"?\" & _ 	\"user";
_loginpath = _dirweb+"?"+"username="+parent.mostCurrent._username+"&"+"useremail="+parent.mostCurrent._main._struseremail /*String*/ +"&"+"deviceID="+parent.mostCurrent._main._deviceid /*String*/ +"&"+"dateandtime="+parent.mostCurrent._dateandtime+"&"+"lat="+parent._alerta_lat+"&"+"lng="+parent._alerta_lng+"&"+"tipo_alerta="+parent._tipo_alerta+"&"+"tiporio="+parent.mostCurrent._tiporio+"&"+"foto1path="+parent._alerta_foto1+"&"+"foto2path="+parent._alerta_foto2+"&"+"foto3path="+parent._alerta_foto3+"&"+"foto4path="+parent._alerta_foto4+"&"+"terminado=si&"+"notas="+parent.mostCurrent._notas+"&"+"telefono="+parent.mostCurrent._telefono+"&"+"partido="+parent.mostCurrent._partido;
 //BA.debugLineNum = 521;BA.debugLine="j.Download(loginPath)";
_j._download /*String*/ (_loginpath);
 //BA.debugLineNum = 522;BA.debugLine="Wait For (j) JobDone(j As HttpJob)";
anywheresoftware.b4a.keywords.Common.WaitFor("jobdone", processBA, this, (Object)(_j));
this.state = 47;
return;
case 47:
//C
this.state = 1;
_j = (appear.pnud.preservamos.httpjob) result[0];
;
 //BA.debugLineNum = 524;BA.debugLine="If j.Success Then";
if (true) break;

case 1:
//if
this.state = 46;
if (_j._success /*boolean*/ ) { 
this.state = 3;
}else {
this.state = 45;
}if (true) break;

case 3:
//C
this.state = 4;
 //BA.debugLineNum = 525;BA.debugLine="Dim ret As String";
_ret = "";
 //BA.debugLineNum = 526;BA.debugLine="Dim act As String";
_act = "";
 //BA.debugLineNum = 527;BA.debugLine="ret = j.GetString";
_ret = _j._getstring /*String*/ ();
 //BA.debugLineNum = 528;BA.debugLine="Dim parser As JSONParser";
_parser = new anywheresoftware.b4a.objects.collections.JSONParser();
 //BA.debugLineNum = 529;BA.debugLine="parser.Initialize(ret)";
_parser.Initialize(_ret);
 //BA.debugLineNum = 530;BA.debugLine="act = parser.NextValue";
_act = BA.ObjectToString(_parser.NextValue());
 //BA.debugLineNum = 531;BA.debugLine="Log(act)";
anywheresoftware.b4a.keywords.Common.LogImpl("022675502",_act,0);
 //BA.debugLineNum = 532;BA.debugLine="If act = \"Not Found\" Then";
if (true) break;

case 4:
//if
this.state = 43;
if ((_act).equals("Not Found")) { 
this.state = 6;
}else if((_act).equals("LatLong Error")) { 
this.state = 14;
}else if((_act).equals("Error")) { 
this.state = 22;
}else if((_act).equals("AlertaAgregada")) { 
this.state = 30;
}if (true) break;

case 6:
//C
this.state = 7;
 //BA.debugLineNum = 533;BA.debugLine="If Main.lang = \"es\" Then";
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
 //BA.debugLineNum = 534;BA.debugLine="ToastMessageShow(\"Error en la carga de alerta\"";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Error en la carga de alerta"),anywheresoftware.b4a.keywords.Common.True);
 if (true) break;

case 11:
//C
this.state = 12;
 //BA.debugLineNum = 536;BA.debugLine="ToastMessageShow(\"Error in loading alert\", Tru";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Error in loading alert"),anywheresoftware.b4a.keywords.Common.True);
 if (true) break;

case 12:
//C
this.state = 43;
;
 //BA.debugLineNum = 538;BA.debugLine="j.Release";
_j._release /*String*/ ();
 //BA.debugLineNum = 539;BA.debugLine="Return False";
if (true) {
anywheresoftware.b4a.keywords.Common.ReturnFromResumableSub(this,(Object)(anywheresoftware.b4a.keywords.Common.False));return;};
 if (true) break;

case 14:
//C
this.state = 15;
 //BA.debugLineNum = 541;BA.debugLine="Log(\"LatLong Error\")";
anywheresoftware.b4a.keywords.Common.LogImpl("022675512","LatLong Error",0);
 //BA.debugLineNum = 542;BA.debugLine="If Main.lang = \"es\" Then";
if (true) break;

case 15:
//if
this.state = 20;
if ((parent.mostCurrent._main._lang /*String*/ ).equals("es")) { 
this.state = 17;
}else if((parent.mostCurrent._main._lang /*String*/ ).equals("en")) { 
this.state = 19;
}if (true) break;

case 17:
//C
this.state = 20;
 //BA.debugLineNum = 543;BA.debugLine="MsgboxAsync(\"No se registraron correctamente l";
anywheresoftware.b4a.keywords.Common.MsgboxAsync(BA.ObjectToCharSequence("No se registraron correctamente las coordenadas, intenta de nuevo!"),BA.ObjectToCharSequence("Error de coordenadas"),processBA);
 if (true) break;

case 19:
//C
this.state = 20;
 //BA.debugLineNum = 545;BA.debugLine="MsgboxAsync(\"There seems to be a problem with";
anywheresoftware.b4a.keywords.Common.MsgboxAsync(BA.ObjectToCharSequence("There seems to be a problem with our servers, try again soon!"),BA.ObjectToCharSequence("My bad"),processBA);
 if (true) break;

case 20:
//C
this.state = 43;
;
 //BA.debugLineNum = 547;BA.debugLine="j.Release";
_j._release /*String*/ ();
 //BA.debugLineNum = 548;BA.debugLine="Return False";
if (true) {
anywheresoftware.b4a.keywords.Common.ReturnFromResumableSub(this,(Object)(anywheresoftware.b4a.keywords.Common.False));return;};
 if (true) break;

case 22:
//C
this.state = 23;
 //BA.debugLineNum = 550;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 //BA.debugLineNum = 551;BA.debugLine="Msgbox2Async(\"Hubo un problema con el envío, pr";
anywheresoftware.b4a.keywords.Common.Msgbox2Async(BA.ObjectToCharSequence("Hubo un problema con el envío, probablemente por la baja señal de internet en tu zona. ¿Deseas intentar de nuevo, o guardar el dato y enviarlo luego?"),BA.ObjectToCharSequence("Acceso a internet débil"),"Intenta de nuevo","","Guarda el dato, lo envío luego",(anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper(), (android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null)),processBA,anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 552;BA.debugLine="Wait For Msgbox_Result (Result As Int)";
anywheresoftware.b4a.keywords.Common.WaitFor("msgbox_result", processBA, this, null);
this.state = 48;
return;
case 48:
//C
this.state = 23;
_result = (Integer) result[0];
;
 //BA.debugLineNum = 553;BA.debugLine="If Result = DialogResponse.POSITIVE Then";
if (true) break;

case 23:
//if
this.state = 28;
if (_result==anywheresoftware.b4a.keywords.Common.DialogResponse.POSITIVE) { 
this.state = 25;
}else if(_result==anywheresoftware.b4a.keywords.Common.DialogResponse.NEGATIVE) { 
this.state = 27;
}if (true) break;

case 25:
//C
this.state = 28;
 //BA.debugLineNum = 554;BA.debugLine="EnviarDatos";
_enviardatos();
 if (true) break;

case 27:
//C
this.state = 28;
 //BA.debugLineNum = 557;BA.debugLine="j.Release";
_j._release /*String*/ ();
 //BA.debugLineNum = 558;BA.debugLine="Return False";
if (true) {
anywheresoftware.b4a.keywords.Common.ReturnFromResumableSub(this,(Object)(anywheresoftware.b4a.keywords.Common.False));return;};
 if (true) break;

case 28:
//C
this.state = 43;
;
 if (true) break;

case 30:
//C
this.state = 31;
 //BA.debugLineNum = 562;BA.debugLine="If tipo_alerta <> \"caza\" Then";
if (true) break;

case 31:
//if
this.state = 42;
if ((parent._tipo_alerta).equals("caza") == false) { 
this.state = 33;
}else {
this.state = 41;
}if (true) break;

case 33:
//C
this.state = 34;
 //BA.debugLineNum = 563;BA.debugLine="If Main.lang = \"es\" Then";
if (true) break;

case 34:
//if
this.state = 39;
if ((parent.mostCurrent._main._lang /*String*/ ).equals("es")) { 
this.state = 36;
}else if((parent.mostCurrent._main._lang /*String*/ ).equals("en")) { 
this.state = 38;
}if (true) break;

case 36:
//C
this.state = 39;
 //BA.debugLineNum = 564;BA.debugLine="ToastMessageShow(\"Alerta enviada, enviando fo";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Alerta enviada, enviando fotos"),anywheresoftware.b4a.keywords.Common.False);
 if (true) break;

case 38:
//C
this.state = 39;
 //BA.debugLineNum = 566;BA.debugLine="ToastMessageShow(\"Report sent, sending photos";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Report sent, sending photos"),anywheresoftware.b4a.keywords.Common.False);
 if (true) break;

case 39:
//C
this.state = 42;
;
 //BA.debugLineNum = 568;BA.debugLine="j.Release";
_j._release /*String*/ ();
 //BA.debugLineNum = 569;BA.debugLine="Return True";
if (true) {
anywheresoftware.b4a.keywords.Common.ReturnFromResumableSub(this,(Object)(anywheresoftware.b4a.keywords.Common.True));return;};
 if (true) break;

case 41:
//C
this.state = 42;
 //BA.debugLineNum = 571;BA.debugLine="ToastMessageShow(\"Alerta de caza ilegal enviad";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Alerta de caza ilegal enviada!"),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 572;BA.debugLine="j.Release";
_j._release /*String*/ ();
 //BA.debugLineNum = 573;BA.debugLine="Activity.RemoveAllViews";
parent.mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 574;BA.debugLine="Activity.Finish";
parent.mostCurrent._activity.Finish();
 if (true) break;

case 42:
//C
this.state = 43;
;
 if (true) break;

case 43:
//C
this.state = 46;
;
 if (true) break;

case 45:
//C
this.state = 46;
 //BA.debugLineNum = 579;BA.debugLine="j.Release";
_j._release /*String*/ ();
 //BA.debugLineNum = 580;BA.debugLine="Return False";
if (true) {
anywheresoftware.b4a.keywords.Common.ReturnFromResumableSub(this,(Object)(anywheresoftware.b4a.keywords.Common.False));return;};
 if (true) break;

case 46:
//C
this.state = -1;
;
 //BA.debugLineNum = 586;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public static String  _enviarfotos() throws Exception{
 //BA.debugLineNum = 593;BA.debugLine="Sub EnviarFotos";
 //BA.debugLineNum = 597;BA.debugLine="ProgressDialogShow2(\"Enviando fotos\", False)";
anywheresoftware.b4a.keywords.Common.ProgressDialogShow2(mostCurrent.activityBA,BA.ObjectToCharSequence("Enviando fotos"),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 598;BA.debugLine="fondoblanco.Initialize(\"fondoblanco\")";
mostCurrent._fondoblanco.Initialize(mostCurrent.activityBA,"fondoblanco");
 //BA.debugLineNum = 599;BA.debugLine="fondoblanco.Color = Colors.ARGB(190, 255,255,255)";
mostCurrent._fondoblanco.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (190),(int) (255),(int) (255),(int) (255)));
 //BA.debugLineNum = 600;BA.debugLine="Activity.AddView(fondoblanco, 0,0, 100%x, 100%y)";
mostCurrent._activity.AddView((android.view.View)(mostCurrent._fondoblanco.getObject()),(int) (0),(int) (0),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA));
 //BA.debugLineNum = 602;BA.debugLine="If Alerta_foto1 <> \"null\" And Alerta_foto1 <> \"\"";
if ((_alerta_foto1).equals("null") == false && (_alerta_foto1).equals("") == false) { 
 //BA.debugLineNum = 603;BA.debugLine="ProgressBar1.Visible = True";
mostCurrent._progressbar1.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 604;BA.debugLine="totalFotos = totalFotos + 1";
_totalfotos = (int) (_totalfotos+1);
 };
 //BA.debugLineNum = 606;BA.debugLine="If Alerta_foto2 <> \"null\" And Alerta_foto2 <> \"\"";
if ((_alerta_foto2).equals("null") == false && (_alerta_foto2).equals("") == false) { 
 //BA.debugLineNum = 607;BA.debugLine="ProgressBar2.Visible = True";
mostCurrent._progressbar2.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 608;BA.debugLine="totalFotos = totalFotos + 1";
_totalfotos = (int) (_totalfotos+1);
 };
 //BA.debugLineNum = 610;BA.debugLine="If Alerta_foto3 <> \"null\" And Alerta_foto3 <> \"\"";
if ((_alerta_foto3).equals("null") == false && (_alerta_foto3).equals("") == false) { 
 //BA.debugLineNum = 611;BA.debugLine="ProgressBar3.Visible = True";
mostCurrent._progressbar3.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 612;BA.debugLine="totalFotos = totalFotos + 1";
_totalfotos = (int) (_totalfotos+1);
 };
 //BA.debugLineNum = 614;BA.debugLine="If Alerta_foto4 <> \"null\" And Alerta_foto4 <> \"\"";
if ((_alerta_foto4).equals("null") == false && (_alerta_foto4).equals("") == false) { 
 //BA.debugLineNum = 615;BA.debugLine="ProgressBar4.Visible = True";
mostCurrent._progressbar4.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 616;BA.debugLine="totalFotos = totalFotos + 1";
_totalfotos = (int) (_totalfotos+1);
 };
 //BA.debugLineNum = 619;BA.debugLine="TimerEnvio.Initialize(\"TimerEnvio\", 1000)";
_timerenvio.Initialize(processBA,"TimerEnvio",(long) (1000));
 //BA.debugLineNum = 620;BA.debugLine="TimerEnvio.Enabled = True";
_timerenvio.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 623;BA.debugLine="If File.Exists(Starter.savedir, Alerta_foto1 & \".";
if (anywheresoftware.b4a.keywords.Common.File.Exists(mostCurrent._starter._savedir /*String*/ ,_alerta_foto1+".jpg") && _foto1sent==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 624;BA.debugLine="Log(\"Enviando foto 1 \")";
anywheresoftware.b4a.keywords.Common.LogImpl("022741023","Enviando foto 1 ",0);
 //BA.debugLineNum = 625;BA.debugLine="Up1.doFileUpload(ProgressBar1,Null,Starter.saved";
_up1.doFileUpload(processBA,(android.widget.ProgressBar)(mostCurrent._progressbar1.getObject()),(android.widget.TextView)(anywheresoftware.b4a.keywords.Common.Null),mostCurrent._starter._savedir /*String*/ +"/"+_alerta_foto1+".jpg",mostCurrent._main._serverpath /*String*/ +"/"+mostCurrent._main._serverconnectionfolder /*String*/ +"/upload_file_alerta.php");
 }else {
 //BA.debugLineNum = 627;BA.debugLine="Log(\"no foto 1\")";
anywheresoftware.b4a.keywords.Common.LogImpl("022741026","no foto 1",0);
 };
 //BA.debugLineNum = 630;BA.debugLine="End Sub";
return "";
}
public static void  _fondoblanco_click() throws Exception{
ResumableSub_fondoblanco_Click rsub = new ResumableSub_fondoblanco_Click(null);
rsub.resume(processBA, null);
}
public static class ResumableSub_fondoblanco_Click extends BA.ResumableSub {
public ResumableSub_fondoblanco_Click(appear.pnud.preservamos.alertas parent) {
this.parent = parent;
}
appear.pnud.preservamos.alertas parent;
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
 //BA.debugLineNum = 633;BA.debugLine="If Main.lang = \"es\" Then";
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
 //BA.debugLineNum = 634;BA.debugLine="Msgbox2Async(\"Se están enviando las fotografías,";
anywheresoftware.b4a.keywords.Common.Msgbox2Async(BA.ObjectToCharSequence("Se están enviando las fotografías, desea cancelar?"),BA.ObjectToCharSequence("Cancelar?"),"Si, cancelar","No!",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Null),(anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper(), (android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null)),processBA,anywheresoftware.b4a.keywords.Common.False);
 if (true) break;

case 5:
//C
this.state = 6;
 //BA.debugLineNum = 636;BA.debugLine="Msgbox2Async(\"Photos are being uploaded, do you";
anywheresoftware.b4a.keywords.Common.Msgbox2Async(BA.ObjectToCharSequence("Photos are being uploaded, do you wish to cancel?"),BA.ObjectToCharSequence("Cancel?"),"Yes, cancel","No!",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Null),(anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper(), (android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null)),processBA,anywheresoftware.b4a.keywords.Common.False);
 if (true) break;

case 6:
//C
this.state = 7;
;
 //BA.debugLineNum = 638;BA.debugLine="Wait For Msgbox_Result (Result As Int)";
anywheresoftware.b4a.keywords.Common.WaitFor("msgbox_result", processBA, this, null);
this.state = 11;
return;
case 11:
//C
this.state = 7;
_result = (Integer) result[0];
;
 //BA.debugLineNum = 639;BA.debugLine="If Result = DialogResponse.POSITIVE Then";
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
 //BA.debugLineNum = 640;BA.debugLine="Up1.UploadKill";
parent._up1.UploadKill(processBA);
 //BA.debugLineNum = 641;BA.debugLine="Activity.RemoveAllViews";
parent.mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 642;BA.debugLine="Activity.Finish";
parent.mostCurrent._activity.Finish();
 //BA.debugLineNum = 643;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 if (true) break;

case 10:
//C
this.state = -1;
;
 //BA.debugLineNum = 645;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public static String  _fondogris_click() throws Exception{
 //BA.debugLineNum = 448;BA.debugLine="Sub fondogris_Click";
 //BA.debugLineNum = 449;BA.debugLine="Activity.RemoveViewAt(Activity.NumberOfViews - 1)";
mostCurrent._activity.RemoveViewAt((int) (mostCurrent._activity.getNumberOfViews()-1));
 //BA.debugLineNum = 450;BA.debugLine="Activity.RemoveViewAt(Activity.NumberOfViews - 1)";
mostCurrent._activity.RemoveViewAt((int) (mostCurrent._activity.getNumberOfViews()-1));
 //BA.debugLineNum = 451;BA.debugLine="End Sub";
return "";
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 22;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 25;BA.debugLine="Dim dateandtime  As String";
mostCurrent._dateandtime = "";
 //BA.debugLineNum = 26;BA.debugLine="Dim tiporio As String";
mostCurrent._tiporio = "";
 //BA.debugLineNum = 28;BA.debugLine="Dim username As String";
mostCurrent._username = "";
 //BA.debugLineNum = 29;BA.debugLine="Dim notas As String";
mostCurrent._notas = "";
 //BA.debugLineNum = 30;BA.debugLine="Dim partido As String";
mostCurrent._partido = "";
 //BA.debugLineNum = 31;BA.debugLine="Dim telefono As String";
mostCurrent._telefono = "";
 //BA.debugLineNum = 32;BA.debugLine="Dim cmbMunicipio As Spinner";
mostCurrent._cmbmunicipio = new anywheresoftware.b4a.objects.SpinnerWrapper();
 //BA.debugLineNum = 36;BA.debugLine="Private lblRojo As Label";
mostCurrent._lblrojo = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 37;BA.debugLine="Private tabAlertas As TabStrip";
mostCurrent._tabalertas = new anywheresoftware.b4a.objects.TabStripViewPager();
 //BA.debugLineNum = 38;BA.debugLine="Private btnPrev As Button";
mostCurrent._btnprev = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 39;BA.debugLine="Private btnNext As Button";
mostCurrent._btnnext = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 40;BA.debugLine="Private lblCirculoPos1 As Label";
mostCurrent._lblcirculopos1 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 41;BA.debugLine="Private lblCirculoPos2 As Label";
mostCurrent._lblcirculopos2 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 42;BA.debugLine="Private lblCirculoPos3 As Label";
mostCurrent._lblcirculopos3 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 43;BA.debugLine="Private imgPeces As ImageView";
mostCurrent._imgpeces = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 44;BA.debugLine="Private imgDerrame As ImageView";
mostCurrent._imgderrame = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 45;BA.debugLine="Private imgAlgas As ImageView";
mostCurrent._imgalgas = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 46;BA.debugLine="Private lblOtraAlerta As Label";
mostCurrent._lblotraalerta = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 47;BA.debugLine="Private imgOtraAlerta As ImageView";
mostCurrent._imgotraalerta = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 48;BA.debugLine="Private lblCoordenadasTitle As Label";
mostCurrent._lblcoordenadastitle = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 49;BA.debugLine="Private imgFoto1 As ImageView";
mostCurrent._imgfoto1 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 50;BA.debugLine="Private imgFoto2 As ImageView";
mostCurrent._imgfoto2 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 51;BA.debugLine="Private imgFoto3 As ImageView";
mostCurrent._imgfoto3 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 52;BA.debugLine="Private imgFoto4 As ImageView";
mostCurrent._imgfoto4 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 53;BA.debugLine="Private txtTelefono As EditText";
mostCurrent._txttelefono = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 54;BA.debugLine="Private txtNotas As EditText";
mostCurrent._txtnotas = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 55;BA.debugLine="Private btnEnviarAlerta As Button";
mostCurrent._btnenviaralerta = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 59;BA.debugLine="Dim pi As PhoneIntents";
mostCurrent._pi = new anywheresoftware.b4a.phone.Phone.PhoneIntents();
 //BA.debugLineNum = 60;BA.debugLine="Private fondoblanco As Label";
mostCurrent._fondoblanco = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 61;BA.debugLine="Private ProgressBar1 As ProgressBar";
mostCurrent._progressbar1 = new anywheresoftware.b4a.objects.ProgressBarWrapper();
 //BA.debugLineNum = 62;BA.debugLine="Private ProgressBar2 As ProgressBar";
mostCurrent._progressbar2 = new anywheresoftware.b4a.objects.ProgressBarWrapper();
 //BA.debugLineNum = 63;BA.debugLine="Private ProgressBar3 As ProgressBar";
mostCurrent._progressbar3 = new anywheresoftware.b4a.objects.ProgressBarWrapper();
 //BA.debugLineNum = 64;BA.debugLine="Private ProgressBar4 As ProgressBar";
mostCurrent._progressbar4 = new anywheresoftware.b4a.objects.ProgressBarWrapper();
 //BA.debugLineNum = 66;BA.debugLine="Private totalFotos As Int";
_totalfotos = 0;
 //BA.debugLineNum = 67;BA.debugLine="Private foto1Sent As Boolean";
_foto1sent = false;
 //BA.debugLineNum = 68;BA.debugLine="Private foto2Sent As Boolean";
_foto2sent = false;
 //BA.debugLineNum = 69;BA.debugLine="Private foto3Sent As Boolean";
_foto3sent = false;
 //BA.debugLineNum = 70;BA.debugLine="Private foto4Sent As Boolean";
_foto4sent = false;
 //BA.debugLineNum = 72;BA.debugLine="Dim fotosEnviadas As Int";
_fotosenviadas = 0;
 //BA.debugLineNum = 75;BA.debugLine="Private lblCaza As Label";
mostCurrent._lblcaza = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 76;BA.debugLine="Private imgCaza As ImageView";
mostCurrent._imgcaza = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 77;BA.debugLine="Private borderFotos1 As Label";
mostCurrent._borderfotos1 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 78;BA.debugLine="Private borderFotos2 As Label";
mostCurrent._borderfotos2 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 79;BA.debugLine="Private borderFotos3 As Label";
mostCurrent._borderfotos3 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 80;BA.debugLine="Private borderFotos4 As Label";
mostCurrent._borderfotos4 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 81;BA.debugLine="Private lblFotosTitle As Label";
mostCurrent._lblfotostitle = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 82;BA.debugLine="Private scrAlertas As ScrollView";
mostCurrent._scralertas = new anywheresoftware.b4a.objects.ScrollViewWrapper();
 //BA.debugLineNum = 83;BA.debugLine="Private imgDidymo As ImageView";
mostCurrent._imgdidymo = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 84;BA.debugLine="Private imgFumigaciones As ImageView";
mostCurrent._imgfumigaciones = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 85;BA.debugLine="Private imgTomasIlegales As ImageView";
mostCurrent._imgtomasilegales = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 86;BA.debugLine="Private pnlInstrucciones As Panel";
mostCurrent._pnlinstrucciones = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 87;BA.debugLine="End Sub";
return "";
}
public static String  _imgalgas_click() throws Exception{
 //BA.debugLineNum = 138;BA.debugLine="Private Sub imgAlgas_Click";
 //BA.debugLineNum = 139;BA.debugLine="lblRojo.Left = imgAlgas.left";
mostCurrent._lblrojo.setLeft(mostCurrent._imgalgas.getLeft());
 //BA.debugLineNum = 140;BA.debugLine="lblRojo.top = imgAlgas.top + imgAlgas.Height / 2";
mostCurrent._lblrojo.setTop((int) (mostCurrent._imgalgas.getTop()+mostCurrent._imgalgas.getHeight()/(double)2));
 //BA.debugLineNum = 141;BA.debugLine="lblRojo.Visible = True";
mostCurrent._lblrojo.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 142;BA.debugLine="tipo_alerta = \"floracion\"";
_tipo_alerta = "floracion";
 //BA.debugLineNum = 143;BA.debugLine="End Sub";
return "";
}
public static String  _imgcaza_click() throws Exception{
 //BA.debugLineNum = 144;BA.debugLine="Private Sub imgCaza_Click";
 //BA.debugLineNum = 145;BA.debugLine="lblRojo.Left =  imgCaza.left";
mostCurrent._lblrojo.setLeft(mostCurrent._imgcaza.getLeft());
 //BA.debugLineNum = 146;BA.debugLine="lblRojo.top =  imgCaza.top +  imgCaza.Height / 2";
mostCurrent._lblrojo.setTop((int) (mostCurrent._imgcaza.getTop()+mostCurrent._imgcaza.getHeight()/(double)2));
 //BA.debugLineNum = 147;BA.debugLine="lblRojo.Visible = True";
mostCurrent._lblrojo.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 148;BA.debugLine="tipo_alerta = \"caza\"";
_tipo_alerta = "caza";
 //BA.debugLineNum = 149;BA.debugLine="End Sub";
return "";
}
public static String  _imgderrame_click() throws Exception{
 //BA.debugLineNum = 150;BA.debugLine="Private Sub imgDerrame_Click";
 //BA.debugLineNum = 151;BA.debugLine="lblRojo.Left = imgDerrame.left";
mostCurrent._lblrojo.setLeft(mostCurrent._imgderrame.getLeft());
 //BA.debugLineNum = 152;BA.debugLine="lblRojo.top = imgDerrame.top + imgDerrame.Height";
mostCurrent._lblrojo.setTop((int) (mostCurrent._imgderrame.getTop()+mostCurrent._imgderrame.getHeight()/(double)2));
 //BA.debugLineNum = 153;BA.debugLine="lblRojo.Visible = True";
mostCurrent._lblrojo.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 154;BA.debugLine="tipo_alerta = \"derrame\"";
_tipo_alerta = "derrame";
 //BA.debugLineNum = 155;BA.debugLine="End Sub";
return "";
}
public static String  _imgdidymo_click() throws Exception{
 //BA.debugLineNum = 174;BA.debugLine="Private Sub imgDidymo_Click";
 //BA.debugLineNum = 175;BA.debugLine="lblRojo.Left = imgDidymo.left";
mostCurrent._lblrojo.setLeft(mostCurrent._imgdidymo.getLeft());
 //BA.debugLineNum = 176;BA.debugLine="lblRojo.top = imgDidymo.top + imgDidymo.Height /";
mostCurrent._lblrojo.setTop((int) (mostCurrent._imgdidymo.getTop()+mostCurrent._imgdidymo.getHeight()/(double)2));
 //BA.debugLineNum = 177;BA.debugLine="lblRojo.Visible = True";
mostCurrent._lblrojo.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 178;BA.debugLine="tipo_alerta = \"didymo\"";
_tipo_alerta = "didymo";
 //BA.debugLineNum = 179;BA.debugLine="End Sub";
return "";
}
public static String  _imgfumigaciones_click() throws Exception{
 //BA.debugLineNum = 162;BA.debugLine="Private Sub imgFumigaciones_Click";
 //BA.debugLineNum = 163;BA.debugLine="lblRojo.Left = imgFumigaciones.left";
mostCurrent._lblrojo.setLeft(mostCurrent._imgfumigaciones.getLeft());
 //BA.debugLineNum = 164;BA.debugLine="lblRojo.top = imgFumigaciones.top + imgFumigacion";
mostCurrent._lblrojo.setTop((int) (mostCurrent._imgfumigaciones.getTop()+mostCurrent._imgfumigaciones.getHeight()/(double)2));
 //BA.debugLineNum = 165;BA.debugLine="lblRojo.Visible = True";
mostCurrent._lblrojo.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 166;BA.debugLine="tipo_alerta = \"fumigacion\"";
_tipo_alerta = "fumigacion";
 //BA.debugLineNum = 167;BA.debugLine="End Sub";
return "";
}
public static String  _imgotraalerta_click() throws Exception{
 //BA.debugLineNum = 180;BA.debugLine="Private Sub imgOtraAlerta_Click";
 //BA.debugLineNum = 181;BA.debugLine="lblRojo.Left = imgOtraAlerta.left";
mostCurrent._lblrojo.setLeft(mostCurrent._imgotraalerta.getLeft());
 //BA.debugLineNum = 182;BA.debugLine="lblRojo.top = imgOtraAlerta.top + imgOtraAlerta.H";
mostCurrent._lblrojo.setTop((int) (mostCurrent._imgotraalerta.getTop()+mostCurrent._imgotraalerta.getHeight()/(double)2));
 //BA.debugLineNum = 183;BA.debugLine="lblRojo.Visible = True";
mostCurrent._lblrojo.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 184;BA.debugLine="tipo_alerta = \"otra\"";
_tipo_alerta = "otra";
 //BA.debugLineNum = 185;BA.debugLine="End Sub";
return "";
}
public static String  _imgpeces_click() throws Exception{
 //BA.debugLineNum = 156;BA.debugLine="Private Sub imgPeces_Click";
 //BA.debugLineNum = 157;BA.debugLine="lblRojo.Left = imgPeces.left";
mostCurrent._lblrojo.setLeft(mostCurrent._imgpeces.getLeft());
 //BA.debugLineNum = 158;BA.debugLine="lblRojo.top = imgPeces.top + imgPeces.Height / 2";
mostCurrent._lblrojo.setTop((int) (mostCurrent._imgpeces.getTop()+mostCurrent._imgpeces.getHeight()/(double)2));
 //BA.debugLineNum = 159;BA.debugLine="lblRojo.Visible = True";
mostCurrent._lblrojo.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 160;BA.debugLine="tipo_alerta = \"peces\"";
_tipo_alerta = "peces";
 //BA.debugLineNum = 161;BA.debugLine="End Sub";
return "";
}
public static String  _imgtomasilegales_click() throws Exception{
 //BA.debugLineNum = 168;BA.debugLine="Private Sub imgTomasIlegales_Click";
 //BA.debugLineNum = 169;BA.debugLine="lblRojo.Left = imgTomasIlegales.left";
mostCurrent._lblrojo.setLeft(mostCurrent._imgtomasilegales.getLeft());
 //BA.debugLineNum = 170;BA.debugLine="lblRojo.top = imgTomasIlegales.top + imgTomasIleg";
mostCurrent._lblrojo.setTop((int) (mostCurrent._imgtomasilegales.getTop()+mostCurrent._imgtomasilegales.getHeight()/(double)2));
 //BA.debugLineNum = 171;BA.debugLine="lblRojo.Visible = True";
mostCurrent._lblrojo.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 172;BA.debugLine="tipo_alerta = \"conexion_ilegal\"";
_tipo_alerta = "conexion_ilegal";
 //BA.debugLineNum = 173;BA.debugLine="End Sub";
return "";
}
public static String  _lblcomofuncionaentendido_click() throws Exception{
 //BA.debugLineNum = 246;BA.debugLine="Private Sub lblComoFuncionaEntendido_Click";
 //BA.debugLineNum = 247;BA.debugLine="pnlInstrucciones.Visible = False";
mostCurrent._pnlinstrucciones.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 248;BA.debugLine="btnNext.Visible = True";
mostCurrent._btnnext.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 249;BA.debugLine="btnPrev.Visible = True";
mostCurrent._btnprev.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 250;BA.debugLine="If Alerta_lat = \"\" Then";
if ((_alerta_lat).equals("")) { 
 //BA.debugLineNum = 252;BA.debugLine="tabAlertas.ScrollTo(2, True)";
mostCurrent._tabalertas.ScrollTo((int) (2),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 253;BA.debugLine="frmLocalizacion.origen = \"alerta\"";
mostCurrent._frmlocalizacion._origen /*String*/  = "alerta";
 //BA.debugLineNum = 254;BA.debugLine="StartActivity(frmLocalizacion)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._frmlocalizacion.getObject()));
 }else {
 //BA.debugLineNum = 256;BA.debugLine="tabAlertas.ScrollTo(2, True)";
mostCurrent._tabalertas.ScrollTo((int) (2),anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 258;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 6;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 9;BA.debugLine="Dim	Alerta_lat As String";
_alerta_lat = "";
 //BA.debugLineNum = 10;BA.debugLine="Dim Alerta_lng As String";
_alerta_lng = "";
 //BA.debugLineNum = 11;BA.debugLine="Dim Alerta_foto1 As String";
_alerta_foto1 = "";
 //BA.debugLineNum = 12;BA.debugLine="Dim Alerta_foto2 As String";
_alerta_foto2 = "";
 //BA.debugLineNum = 13;BA.debugLine="Dim Alerta_foto3 As String";
_alerta_foto3 = "";
 //BA.debugLineNum = 14;BA.debugLine="Dim Alerta_foto4 As String";
_alerta_foto4 = "";
 //BA.debugLineNum = 16;BA.debugLine="Dim Up1 As UploadFilePhp";
_up1 = new com.spinter.uploadfilephp.UploadFilePhp();
 //BA.debugLineNum = 17;BA.debugLine="Dim Geopartido As String";
_geopartido = "";
 //BA.debugLineNum = 18;BA.debugLine="Dim tipo_alerta As String";
_tipo_alerta = "";
 //BA.debugLineNum = 19;BA.debugLine="Private TimerEnvio As Timer";
_timerenvio = new anywheresoftware.b4a.objects.Timer();
 //BA.debugLineNum = 20;BA.debugLine="End Sub";
return "";
}
public static String  _tabalertas_pageselected(int _position) throws Exception{
 //BA.debugLineNum = 116;BA.debugLine="Private Sub tabAlertas_PageSelected (Position As I";
 //BA.debugLineNum = 117;BA.debugLine="If Position = 2 Then";
if (_position==2) { 
 //BA.debugLineNum = 118;BA.debugLine="btnNext.Visible = False";
mostCurrent._btnnext.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 119;BA.debugLine="btnPrev.Visible = True";
mostCurrent._btnprev.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 120;BA.debugLine="lblCirculoPos3.Color = Colors.RGB(112,112,112)";
mostCurrent._lblcirculopos3.setColor(anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (112),(int) (112),(int) (112)));
 //BA.debugLineNum = 121;BA.debugLine="lblCirculoPos2.Color = Colors.RGB(197,197,197)";
mostCurrent._lblcirculopos2.setColor(anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (197),(int) (197),(int) (197)));
 //BA.debugLineNum = 122;BA.debugLine="lblCirculoPos1.Color = Colors.RGB(197,197,197)";
mostCurrent._lblcirculopos1.setColor(anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (197),(int) (197),(int) (197)));
 }else if(_position==0) { 
 //BA.debugLineNum = 124;BA.debugLine="btnPrev.Visible = False";
mostCurrent._btnprev.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 125;BA.debugLine="btnNext.Visible = True";
mostCurrent._btnnext.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 126;BA.debugLine="lblCirculoPos1.Color = Colors.RGB(112,112,112)";
mostCurrent._lblcirculopos1.setColor(anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (112),(int) (112),(int) (112)));
 //BA.debugLineNum = 127;BA.debugLine="lblCirculoPos2.Color = Colors.RGB(197,197,197)";
mostCurrent._lblcirculopos2.setColor(anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (197),(int) (197),(int) (197)));
 //BA.debugLineNum = 128;BA.debugLine="lblCirculoPos3.Color = Colors.RGB(197,197,197)";
mostCurrent._lblcirculopos3.setColor(anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (197),(int) (197),(int) (197)));
 }else if(_position==1) { 
 //BA.debugLineNum = 130;BA.debugLine="btnPrev.Visible = True";
mostCurrent._btnprev.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 131;BA.debugLine="btnNext.Visible = True";
mostCurrent._btnnext.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 132;BA.debugLine="lblCirculoPos2.Color = Colors.RGB(112,112,112)";
mostCurrent._lblcirculopos2.setColor(anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (112),(int) (112),(int) (112)));
 //BA.debugLineNum = 133;BA.debugLine="lblCirculoPos3.Color = Colors.RGB(197,197,197)";
mostCurrent._lblcirculopos3.setColor(anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (197),(int) (197),(int) (197)));
 //BA.debugLineNum = 134;BA.debugLine="lblCirculoPos1.Color = Colors.RGB(197,197,197)";
mostCurrent._lblcirculopos1.setColor(anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (197),(int) (197),(int) (197)));
 };
 //BA.debugLineNum = 136;BA.debugLine="End Sub";
return "";
}
public static String  _timerenvio_tick() throws Exception{
 //BA.debugLineNum = 648;BA.debugLine="Sub TimerEnvio_Tick";
 //BA.debugLineNum = 649;BA.debugLine="If fotosEnviadas = totalFotos Then";
if (_fotosenviadas==_totalfotos) { 
 //BA.debugLineNum = 650;BA.debugLine="Log(\"TODAS LAS FOTOS FUERON ENVIADAS CORRECTAMEN";
anywheresoftware.b4a.keywords.Common.LogImpl("022872066","TODAS LAS FOTOS FUERON ENVIADAS CORRECTAMENTE",0);
 //BA.debugLineNum = 651;BA.debugLine="Up1.UploadKill";
_up1.UploadKill(processBA);
 //BA.debugLineNum = 652;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 653;BA.debugLine="ToastMessageShow(\"Alerta verde enviada!!!\", Tru";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Alerta verde enviada!!!"),anywheresoftware.b4a.keywords.Common.True);
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 };
 //BA.debugLineNum = 658;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 659;BA.debugLine="ToastMessageShow(\"Alerta verde enviada!!!\", Tru";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Alerta verde enviada!!!"),anywheresoftware.b4a.keywords.Common.True);
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 661;BA.debugLine="ToastMessageShow(\"Alert sent!!!\", True)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Alert sent!!!"),anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 663;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 //BA.debugLineNum = 664;BA.debugLine="TimerEnvio.Enabled = False";
_timerenvio.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 665;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 666;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 };
 //BA.debugLineNum = 669;BA.debugLine="End Sub";
return "";
}
public static String  _up1_sendfile(String _value) throws Exception{
 //BA.debugLineNum = 676;BA.debugLine="Sub Up1_sendFile (value As String)";
 //BA.debugLineNum = 677;BA.debugLine="Log(\"sendfile event:\" & value)";
anywheresoftware.b4a.keywords.Common.LogImpl("023003137","sendfile event:"+_value,0);
 //BA.debugLineNum = 678;BA.debugLine="If value = \"success\" Then";
if ((_value).equals("success")) { 
 //BA.debugLineNum = 681;BA.debugLine="If fotosEnviadas = 0 And totalFotos = 1 Then";
if (_fotosenviadas==0 && _totalfotos==1) { 
 //BA.debugLineNum = 682;BA.debugLine="fotosEnviadas = 1";
_fotosenviadas = (int) (1);
 //BA.debugLineNum = 683;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 685;BA.debugLine="If fotosEnviadas = 1 And totalFotos = 2 Then";
if (_fotosenviadas==1 && _totalfotos==2) { 
 //BA.debugLineNum = 686;BA.debugLine="fotosEnviadas = 2";
_fotosenviadas = (int) (2);
 //BA.debugLineNum = 687;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 689;BA.debugLine="If fotosEnviadas = 2 And totalFotos = 3 Then";
if (_fotosenviadas==2 && _totalfotos==3) { 
 //BA.debugLineNum = 690;BA.debugLine="fotosEnviadas = 3";
_fotosenviadas = (int) (3);
 //BA.debugLineNum = 691;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 693;BA.debugLine="If fotosEnviadas = 3 And totalFotos = 4 Then";
if (_fotosenviadas==3 && _totalfotos==4) { 
 //BA.debugLineNum = 694;BA.debugLine="fotosEnviadas = 4";
_fotosenviadas = (int) (4);
 //BA.debugLineNum = 695;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 697;BA.debugLine="If fotosEnviadas = 4 And totalFotos = 5 Then";
if (_fotosenviadas==4 && _totalfotos==5) { 
 //BA.debugLineNum = 698;BA.debugLine="fotosEnviadas = 5";
_fotosenviadas = (int) (5);
 //BA.debugLineNum = 699;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 703;BA.debugLine="If fotosEnviadas = 0 And totalFotos > 1 Then";
if (_fotosenviadas==0 && _totalfotos>1) { 
 //BA.debugLineNum = 704;BA.debugLine="fotosEnviadas = 1";
_fotosenviadas = (int) (1);
 //BA.debugLineNum = 705;BA.debugLine="ProgressBar1.Visible = False";
mostCurrent._progressbar1.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 706;BA.debugLine="If File.Exists(Starter.savedir, Alerta_foto2 &";
if (anywheresoftware.b4a.keywords.Common.File.Exists(mostCurrent._starter._savedir /*String*/ ,_alerta_foto2+".jpg") && _foto2sent==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 707;BA.debugLine="Log(\"Enviando foto 2 \")";
anywheresoftware.b4a.keywords.Common.LogImpl("023003167","Enviando foto 2 ",0);
 //BA.debugLineNum = 708;BA.debugLine="ProgressBar2.Progress  = 0";
mostCurrent._progressbar2.setProgress((int) (0));
 //BA.debugLineNum = 709;BA.debugLine="Up1.doFileUpload(ProgressBar2,Null,Starter.sav";
_up1.doFileUpload(processBA,(android.widget.ProgressBar)(mostCurrent._progressbar2.getObject()),(android.widget.TextView)(anywheresoftware.b4a.keywords.Common.Null),mostCurrent._starter._savedir /*String*/ +"/"+_alerta_foto2+".jpg",mostCurrent._main._serverpath /*String*/ +"/"+mostCurrent._main._serverconnectionfolder /*String*/ +"/upload_file_alerta.php");
 }else {
 //BA.debugLineNum = 711;BA.debugLine="Log(\"no foto 2\")";
anywheresoftware.b4a.keywords.Common.LogImpl("023003171","no foto 2",0);
 };
 }else if(_fotosenviadas==1 && _totalfotos>2) { 
 //BA.debugLineNum = 714;BA.debugLine="fotosEnviadas = 2";
_fotosenviadas = (int) (2);
 //BA.debugLineNum = 715;BA.debugLine="ProgressBar2.Visible = False";
mostCurrent._progressbar2.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 716;BA.debugLine="If File.Exists(Starter.savedir, Alerta_foto3 &";
if (anywheresoftware.b4a.keywords.Common.File.Exists(mostCurrent._starter._savedir /*String*/ ,_alerta_foto3+".jpg") && _foto3sent==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 717;BA.debugLine="Log(\"Enviando foto 3 \")";
anywheresoftware.b4a.keywords.Common.LogImpl("023003177","Enviando foto 3 ",0);
 //BA.debugLineNum = 718;BA.debugLine="ProgressBar3.Progress  = 0";
mostCurrent._progressbar3.setProgress((int) (0));
 //BA.debugLineNum = 719;BA.debugLine="Up1.doFileUpload(ProgressBar3,Null,Starter.sav";
_up1.doFileUpload(processBA,(android.widget.ProgressBar)(mostCurrent._progressbar3.getObject()),(android.widget.TextView)(anywheresoftware.b4a.keywords.Common.Null),mostCurrent._starter._savedir /*String*/ +"/"+_alerta_foto3+".jpg",mostCurrent._main._serverpath /*String*/ +"/"+mostCurrent._main._serverconnectionfolder /*String*/ +"/upload_file_alerta.php");
 }else {
 //BA.debugLineNum = 721;BA.debugLine="Log(\"no foto 3\")";
anywheresoftware.b4a.keywords.Common.LogImpl("023003181","no foto 3",0);
 };
 }else if(_fotosenviadas==2 && _totalfotos>3) { 
 //BA.debugLineNum = 724;BA.debugLine="fotosEnviadas = 3";
_fotosenviadas = (int) (3);
 //BA.debugLineNum = 725;BA.debugLine="ProgressBar3.Visible = False";
mostCurrent._progressbar3.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 726;BA.debugLine="If File.Exists(Starter.savedir, Alerta_foto4 &";
if (anywheresoftware.b4a.keywords.Common.File.Exists(mostCurrent._starter._savedir /*String*/ ,_alerta_foto4+".jpg") && _foto4sent==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 727;BA.debugLine="Log(\"Enviando foto 4 \")";
anywheresoftware.b4a.keywords.Common.LogImpl("023003187","Enviando foto 4 ",0);
 //BA.debugLineNum = 728;BA.debugLine="ProgressBar4.Progress  = 0";
mostCurrent._progressbar4.setProgress((int) (0));
 //BA.debugLineNum = 729;BA.debugLine="Up1.doFileUpload(ProgressBar4,Null,Starter.sav";
_up1.doFileUpload(processBA,(android.widget.ProgressBar)(mostCurrent._progressbar4.getObject()),(android.widget.TextView)(anywheresoftware.b4a.keywords.Common.Null),mostCurrent._starter._savedir /*String*/ +"/"+_alerta_foto4+".jpg",mostCurrent._main._serverpath /*String*/ +"/"+mostCurrent._main._serverconnectionfolder /*String*/ +"/upload_file_alerta.php");
 }else {
 //BA.debugLineNum = 731;BA.debugLine="Log(\"no foto 4\")";
anywheresoftware.b4a.keywords.Common.LogImpl("023003191","no foto 4",0);
 };
 };
 }else if((_value).equals("Error!")) { 
 //BA.debugLineNum = 738;BA.debugLine="Log(\"FOTO error\")";
anywheresoftware.b4a.keywords.Common.LogImpl("023003198","FOTO error",0);
 //BA.debugLineNum = 739;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 740;BA.debugLine="MsgboxAsync(\"Ha habido un error en el envío. Re";
anywheresoftware.b4a.keywords.Common.MsgboxAsync(BA.ObjectToCharSequence("Ha habido un error en el envío. Revisa tu conexión a Internet e intenta de nuevo"),BA.ObjectToCharSequence("Oops!"),processBA);
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 742;BA.debugLine="MsgboxAsync(\"There has been an error during the";
anywheresoftware.b4a.keywords.Common.MsgboxAsync(BA.ObjectToCharSequence("There has been an error during the upload. Check your internet connection and try again"),BA.ObjectToCharSequence("Oops!"),processBA);
 };
 //BA.debugLineNum = 744;BA.debugLine="Up1.UploadKill";
_up1.UploadKill(processBA);
 //BA.debugLineNum = 745;BA.debugLine="TimerEnvio.Enabled = False";
_timerenvio.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 746;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 };
 //BA.debugLineNum = 748;BA.debugLine="End Sub";
return "";
}
public static String  _up1_statusupload(String _value) throws Exception{
 //BA.debugLineNum = 672;BA.debugLine="Sub Up1_statusUpload (value As String)";
 //BA.debugLineNum = 674;BA.debugLine="End Sub";
return "";
}
public static String  _up1_statusvisible(boolean _onoff,String _value) throws Exception{
 //BA.debugLineNum = 749;BA.debugLine="Sub Up1_statusVISIBLE (onoff As Boolean,value As S";
 //BA.debugLineNum = 751;BA.debugLine="End Sub";
return "";
}
}
