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

public class frmtiporeporte extends Activity implements B4AActivity{
	public static frmtiporeporte mostCurrent;
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
			processBA = new BA(this.getApplicationContext(), null, null, "appear.pnud.preservamos", "appear.pnud.preservamos.frmtiporeporte");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
            
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (frmtiporeporte).");
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
		activityBA = new BA(this, layout, processBA, "appear.pnud.preservamos", "appear.pnud.preservamos.frmtiporeporte");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "appear.pnud.preservamos.frmtiporeporte", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density, mostCurrent);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (frmtiporeporte) Create " + (isFirst ? "(first time)" : "") + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (frmtiporeporte) Resume **");
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
		return frmtiporeporte.class;
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
            BA.LogInfo("** Activity (frmtiporeporte) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
        else
            BA.LogInfo("** Activity (frmtiporeporte) Pause event (activity is not paused). **");
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
            frmtiporeporte mc = mostCurrent;
			if (mc == null || mc != activity.get())
				return;
			processBA.setActivityPaused(false);
            BA.LogInfo("** Activity (frmtiporeporte) Resume **");
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
public anywheresoftware.b4a.objects.LabelWrapper _lblrojo = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imghabitat = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imghidro = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblhidro = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblhabitat = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbltiporio = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnoktipo = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imgbasura = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblbasura = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imgavanzados = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblavanzados = null;
public static String _opcionelegida = "";
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
 //BA.debugLineNum = 26;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
 //BA.debugLineNum = 27;BA.debugLine="Activity.LoadLayout(\"layReporte\")";
mostCurrent._activity.LoadLayout("layReporte",mostCurrent.activityBA);
 //BA.debugLineNum = 29;BA.debugLine="End Sub";
return "";
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 35;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 37;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 31;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 33;BA.debugLine="End Sub";
return "";
}
public static String  _btnoktipo_click() throws Exception{
 //BA.debugLineNum = 64;BA.debugLine="Private Sub btnOkTipo_Click";
 //BA.debugLineNum = 65;BA.debugLine="If lblRojo.Visible = False Then";
if (mostCurrent._lblrojo.getVisible()==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 66;BA.debugLine="ToastMessageShow(\"¡Debes elegir un tipo de ambie";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("¡Debes elegir un tipo de ambiente a analizar!"),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 67;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 70;BA.debugLine="If opcionElegida = \"hidro\" Then";
if ((mostCurrent._opcionelegida).equals("hidro")) { 
 //BA.debugLineNum = 71;BA.debugLine="ToastMessageShow(\"Módulo de hidrología aún no di";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Módulo de hidrología aún no disponible!"),anywheresoftware.b4a.keywords.Common.False);
 }else if((mostCurrent._opcionelegida).equals("basura")) { 
 //BA.debugLineNum = 74;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 75;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 76;BA.debugLine="Form_Reporte.origen = \"Form_Main\"";
mostCurrent._form_reporte._origen /*String*/  = "Form_Main";
 //BA.debugLineNum = 77;BA.debugLine="StartActivity(mod_Residuos)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._mod_residuos.getObject()));
 }else if((mostCurrent._opcionelegida).equals("avanzados")) { 
 //BA.debugLineNum = 79;BA.debugLine="ToastMessageShow(\"Módulos avanzados aún no dispo";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Módulos avanzados aún no disponibles!"),anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 82;BA.debugLine="If opcionElegida = \"habitat\" Then";
if ((mostCurrent._opcionelegida).equals("habitat")) { 
 //BA.debugLineNum = 84;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 85;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 86;BA.debugLine="Form_Reporte.origen = \"Form_Main\"";
mostCurrent._form_reporte._origen /*String*/  = "Form_Main";
 //BA.debugLineNum = 87;BA.debugLine="StartActivity(Form_Reporte)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._form_reporte.getObject()));
 };
 //BA.debugLineNum = 90;BA.debugLine="End Sub";
return "";
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 10;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 11;BA.debugLine="Private lblRojo As Label";
mostCurrent._lblrojo = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 12;BA.debugLine="Private imgHabitat As ImageView";
mostCurrent._imghabitat = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 13;BA.debugLine="Private imgHidro As ImageView";
mostCurrent._imghidro = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 14;BA.debugLine="Private lblHidro As Label";
mostCurrent._lblhidro = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 15;BA.debugLine="Private lblHabitat As Label";
mostCurrent._lblhabitat = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 16;BA.debugLine="Private lblTipoRio As Label";
mostCurrent._lbltiporio = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 17;BA.debugLine="Private btnOkTipo As Button";
mostCurrent._btnoktipo = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 18;BA.debugLine="Private imgBasura As ImageView";
mostCurrent._imgbasura = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 19;BA.debugLine="Private lblBasura As Label";
mostCurrent._lblbasura = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 20;BA.debugLine="Private imgAvanzados As ImageView";
mostCurrent._imgavanzados = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 21;BA.debugLine="Private lblAvanzados As Label";
mostCurrent._lblavanzados = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 23;BA.debugLine="Dim opcionElegida As String";
mostCurrent._opcionelegida = "";
 //BA.debugLineNum = 24;BA.debugLine="End Sub";
return "";
}
public static String  _imgavanzados_click() throws Exception{
 //BA.debugLineNum = 57;BA.debugLine="Private Sub imgAvanzados_Click";
 //BA.debugLineNum = 58;BA.debugLine="lblRojo.Visible = True";
mostCurrent._lblrojo.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 59;BA.debugLine="lblRojo.Left = imgAvanzados.Left";
mostCurrent._lblrojo.setLeft(mostCurrent._imgavanzados.getLeft());
 //BA.debugLineNum = 60;BA.debugLine="lblRojo.Top = imgAvanzados.Top + imgHabitat.Heigh";
mostCurrent._lblrojo.setTop((int) (mostCurrent._imgavanzados.getTop()+mostCurrent._imghabitat.getHeight()/(double)2));
 //BA.debugLineNum = 61;BA.debugLine="opcionElegida = \"avanzados\"";
mostCurrent._opcionelegida = "avanzados";
 //BA.debugLineNum = 62;BA.debugLine="End Sub";
return "";
}
public static String  _imgbasura_click() throws Exception{
 //BA.debugLineNum = 51;BA.debugLine="Private Sub imgBasura_Click";
 //BA.debugLineNum = 52;BA.debugLine="lblRojo.Visible = True";
mostCurrent._lblrojo.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 53;BA.debugLine="lblRojo.Left = imgBasura.Left";
mostCurrent._lblrojo.setLeft(mostCurrent._imgbasura.getLeft());
 //BA.debugLineNum = 54;BA.debugLine="lblRojo.Top = imgBasura.Top + imgHabitat.Height /";
mostCurrent._lblrojo.setTop((int) (mostCurrent._imgbasura.getTop()+mostCurrent._imghabitat.getHeight()/(double)2));
 //BA.debugLineNum = 55;BA.debugLine="opcionElegida = \"basura\"";
mostCurrent._opcionelegida = "basura";
 //BA.debugLineNum = 56;BA.debugLine="End Sub";
return "";
}
public static String  _imghabitat_click() throws Exception{
 //BA.debugLineNum = 39;BA.debugLine="Private Sub imgHabitat_Click";
 //BA.debugLineNum = 40;BA.debugLine="lblRojo.Visible = True";
mostCurrent._lblrojo.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 41;BA.debugLine="lblRojo.Left = imgHabitat.Left";
mostCurrent._lblrojo.setLeft(mostCurrent._imghabitat.getLeft());
 //BA.debugLineNum = 42;BA.debugLine="lblRojo.Top = imgHabitat.Top + imgHabitat.Height";
mostCurrent._lblrojo.setTop((int) (mostCurrent._imghabitat.getTop()+mostCurrent._imghabitat.getHeight()/(double)2));
 //BA.debugLineNum = 43;BA.debugLine="opcionElegida = \"habitat\"";
mostCurrent._opcionelegida = "habitat";
 //BA.debugLineNum = 44;BA.debugLine="End Sub";
return "";
}
public static String  _imghidro_click() throws Exception{
 //BA.debugLineNum = 45;BA.debugLine="Private Sub imgHidro_Click";
 //BA.debugLineNum = 46;BA.debugLine="lblRojo.Visible = True";
mostCurrent._lblrojo.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 47;BA.debugLine="lblRojo.Left = imgHidro.Left";
mostCurrent._lblrojo.setLeft(mostCurrent._imghidro.getLeft());
 //BA.debugLineNum = 48;BA.debugLine="lblRojo.Top = imgHidro.Top + imgHabitat.Height /";
mostCurrent._lblrojo.setTop((int) (mostCurrent._imghidro.getTop()+mostCurrent._imghabitat.getHeight()/(double)2));
 //BA.debugLineNum = 49;BA.debugLine="opcionElegida = \"hidro\"";
mostCurrent._opcionelegida = "hidro";
 //BA.debugLineNum = 50;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 6;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 8;BA.debugLine="End Sub";
return "";
}
}
