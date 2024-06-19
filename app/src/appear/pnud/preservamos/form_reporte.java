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

public class form_reporte extends Activity implements B4AActivity{
	public static form_reporte mostCurrent;
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
			processBA = new BA(this.getApplicationContext(), null, null, "appear.pnud.preservamos", "appear.pnud.preservamos.form_reporte");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
            
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (form_reporte).");
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
		activityBA = new BA(this, layout, processBA, "appear.pnud.preservamos", "appear.pnud.preservamos.form_reporte");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "appear.pnud.preservamos.form_reporte", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density, mostCurrent);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (form_reporte) Create " + (isFirst ? "(first time)" : "") + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (form_reporte) Resume **");
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
		return form_reporte.class;
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
            BA.LogInfo("** Activity (form_reporte) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
        else
            BA.LogInfo("** Activity (form_reporte) Pause event (activity is not paused). **");
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
            form_reporte mc = mostCurrent;
			if (mc == null || mc != activity.get())
				return;
			processBA.setActivityPaused(false);
            BA.LogInfo("** Activity (form_reporte) Resume **");
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
public static String _opcionelegida = "";
public static String _tipocuerpo = "";
public anywheresoftware.b4a.objects.ImageViewWrapper _imgrio = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imglaguna = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnoktipo = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbllaguna = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbltipocuerpo = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblllanura = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblrio = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imgllanura = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imgsierra = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblsierra = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblrojoambiente = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbltipoambiente = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblrojotipocuerpo = null;
public b4a.example.dateutils _dateutils = null;
public appear.pnud.preservamos.main _main = null;
public appear.pnud.preservamos.form_main _form_main = null;
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
public static String  _activity_create(boolean _firsttime) throws Exception{
 //BA.debugLineNum = 39;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
 //BA.debugLineNum = 41;BA.debugLine="Activity.LoadLayout(\"layReporte_Basicos\")";
mostCurrent._activity.LoadLayout("layReporte_Basicos",mostCurrent.activityBA);
 //BA.debugLineNum = 42;BA.debugLine="Load_Reporte_Basicos";
_load_reporte_basicos();
 //BA.debugLineNum = 43;BA.debugLine="End Sub";
return "";
}
public static boolean  _activity_keypress(int _keycode) throws Exception{
 //BA.debugLineNum = 52;BA.debugLine="Sub Activity_KeyPress (KeyCode As Int) As Boolean";
 //BA.debugLineNum = 53;BA.debugLine="If KeyCode = KeyCodes.KEYCODE_BACK Then";
if (_keycode==anywheresoftware.b4a.keywords.Common.KeyCodes.KEYCODE_BACK) { 
 //BA.debugLineNum = 54;BA.debugLine="closeAppMsgBox";
_closeappmsgbox();
 //BA.debugLineNum = 55;BA.debugLine="Return True";
if (true) return anywheresoftware.b4a.keywords.Common.True;
 }else {
 //BA.debugLineNum = 57;BA.debugLine="Return False";
if (true) return anywheresoftware.b4a.keywords.Common.False;
 };
 //BA.debugLineNum = 59;BA.debugLine="End Sub";
return false;
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 49;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 50;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 45;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 47;BA.debugLine="End Sub";
return "";
}
public static String  _btnoktipo_click() throws Exception{
anywheresoftware.b4a.objects.collections.Map _map1 = null;
 //BA.debugLineNum = 350;BA.debugLine="Sub btnOkTipo_Click";
 //BA.debugLineNum = 351;BA.debugLine="If lblRojoAmbiente.Visible = False Then";
if (mostCurrent._lblrojoambiente.getVisible()==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 352;BA.debugLine="ToastMessageShow(\"¡Debes elegir un tipo de ambie";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("¡Debes elegir un tipo de ambiente a analizar!"),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 353;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 355;BA.debugLine="Log(\"tipo ambiente: \" & opcionElegida)";
anywheresoftware.b4a.keywords.Common.LogImpl("07536645","tipo ambiente: "+mostCurrent._opcionelegida,0);
 //BA.debugLineNum = 358;BA.debugLine="Dim Map1 As Map";
_map1 = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 359;BA.debugLine="Map1.Initialize";
_map1.Initialize();
 //BA.debugLineNum = 360;BA.debugLine="Map1.Put(\"Id\", currentproject)";
_map1.Put((Object)("Id"),(Object)(_currentproject));
 //BA.debugLineNum = 361;BA.debugLine="If opcionElegida = \"llanura\" Or opcionElegida = \"";
if ((mostCurrent._opcionelegida).equals("llanura") || (mostCurrent._opcionelegida).equals("")) { 
 //BA.debugLineNum = 362;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"evals\", \"ti";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals","tiporio",(Object)("llanura"),_map1);
 //BA.debugLineNum = 363;BA.debugLine="tipoAmbiente = \"llanura\"";
_tipoambiente = "llanura";
 }else if((mostCurrent._opcionelegida).equals("laguna")) { 
 //BA.debugLineNum = 365;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"evals\", \"ti";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals","tiporio",(Object)("laguna"),_map1);
 //BA.debugLineNum = 366;BA.debugLine="tipoAmbiente = \"laguna\"";
_tipoambiente = "laguna";
 }else if((mostCurrent._opcionelegida).equals("sierras")) { 
 //BA.debugLineNum = 368;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"evals\", \"ti";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals","tiporio",(Object)("sierras"),_map1);
 //BA.debugLineNum = 369;BA.debugLine="tipoAmbiente = \"sierras\"";
_tipoambiente = "sierras";
 }else if((mostCurrent._opcionelegida).equals("sierras_laguna")) { 
 //BA.debugLineNum = 371;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"evals\", \"ti";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals","tiporio",(Object)("sierras_laguna"),_map1);
 //BA.debugLineNum = 372;BA.debugLine="tipoAmbiente = \"sierras_laguna\"";
_tipoambiente = "sierras_laguna";
 }else {
 };
 //BA.debugLineNum = 384;BA.debugLine="frmLocalizacion.currentproject = currentproject";
mostCurrent._frmlocalizacion._currentproject /*String*/  = _currentproject;
 //BA.debugLineNum = 385;BA.debugLine="frmLocalizacion.origen = \"Reporte_Localizacion\"";
mostCurrent._frmlocalizacion._origen /*String*/  = "Reporte_Localizacion";
 //BA.debugLineNum = 386;BA.debugLine="frmLocalizacion.tipoAmbiente = tipoAmbiente";
mostCurrent._frmlocalizacion._tipoambiente /*String*/  = _tipoambiente;
 //BA.debugLineNum = 387;BA.debugLine="frmLocalizacion.tipoDetect = \"GPSdetect\"";
mostCurrent._frmlocalizacion._tipodetect /*String*/  = "GPSdetect";
 //BA.debugLineNum = 390;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 391;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 392;BA.debugLine="StartActivity(frmLocalizacion)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._frmlocalizacion.getObject()));
 //BA.debugLineNum = 394;BA.debugLine="End Sub";
return "";
}
public static void  _closeappmsgbox() throws Exception{
ResumableSub_closeAppMsgBox rsub = new ResumableSub_closeAppMsgBox(null);
rsub.resume(processBA, null);
}
public static class ResumableSub_closeAppMsgBox extends BA.ResumableSub {
public ResumableSub_closeAppMsgBox(appear.pnud.preservamos.form_reporte parent) {
this.parent = parent;
}
appear.pnud.preservamos.form_reporte parent;
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
 //BA.debugLineNum = 62;BA.debugLine="Msgbox2Async(\"¿Cancelar el análisis?\", \"¡Se perde";
anywheresoftware.b4a.keywords.Common.Msgbox2Async(BA.ObjectToCharSequence("¿Cancelar el análisis?"),BA.ObjectToCharSequence("¡Se perderá lo cargado!"),"Si","","No",(anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper(), (android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null)),processBA,anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 63;BA.debugLine="Wait For Msgbox_Result (Result As Int)";
anywheresoftware.b4a.keywords.Common.WaitFor("msgbox_result", processBA, this, null);
this.state = 5;
return;
case 5:
//C
this.state = 1;
_result = (Integer) result[0];
;
 //BA.debugLineNum = 64;BA.debugLine="If Result = DialogResponse.POSITIVE Then";
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
 //BA.debugLineNum = 65;BA.debugLine="Activity.finish";
parent.mostCurrent._activity.Finish();
 //BA.debugLineNum = 66;BA.debugLine="Activity.RemoveAllViews";
parent.mostCurrent._activity.RemoveAllViews();
 if (true) break;

case 4:
//C
this.state = -1;
;
 //BA.debugLineNum = 68;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public static void  _msgbox_result(int _result) throws Exception{
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 16;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 18;BA.debugLine="Dim opcionElegida As String";
mostCurrent._opcionelegida = "";
 //BA.debugLineNum = 19;BA.debugLine="Dim tipocuerpo As String";
mostCurrent._tipocuerpo = "";
 //BA.debugLineNum = 21;BA.debugLine="Private imgRio As ImageView";
mostCurrent._imgrio = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 22;BA.debugLine="Private imgLaguna As ImageView";
mostCurrent._imglaguna = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 25;BA.debugLine="Private btnOkTipo As Button";
mostCurrent._btnoktipo = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 26;BA.debugLine="Private lblLaguna As Label";
mostCurrent._lbllaguna = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 27;BA.debugLine="Private lblTipoCuerpo As Label";
mostCurrent._lbltipocuerpo = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 28;BA.debugLine="Private lblLlanura As Label";
mostCurrent._lblllanura = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 30;BA.debugLine="Private lblRio As Label";
mostCurrent._lblrio = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 31;BA.debugLine="Private imgLlanura As ImageView";
mostCurrent._imgllanura = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 32;BA.debugLine="Private imgSierra As ImageView";
mostCurrent._imgsierra = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 33;BA.debugLine="Private lblSierra As Label";
mostCurrent._lblsierra = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 34;BA.debugLine="Private lblRojoAmbiente As Label";
mostCurrent._lblrojoambiente = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 35;BA.debugLine="Private lblTipoAmbiente As Label";
mostCurrent._lbltipoambiente = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 36;BA.debugLine="Private lblRojoTipoCuerpo As Label";
mostCurrent._lblrojotipocuerpo = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 37;BA.debugLine="End Sub";
return "";
}
public static String  _imglaguna_click() throws Exception{
 //BA.debugLineNum = 242;BA.debugLine="Sub imgLaguna_Click";
 //BA.debugLineNum = 243;BA.debugLine="lblRojoTipoCuerpo.Visible = True";
mostCurrent._lblrojotipocuerpo.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 244;BA.debugLine="lblRojoTipoCuerpo.Left = imgLaguna.Left";
mostCurrent._lblrojotipocuerpo.setLeft(mostCurrent._imglaguna.getLeft());
 //BA.debugLineNum = 245;BA.debugLine="tipocuerpo = \"laguna\"";
mostCurrent._tipocuerpo = "laguna";
 //BA.debugLineNum = 246;BA.debugLine="If lblRojoAmbiente.Visible = False Then";
if (mostCurrent._lblrojoambiente.getVisible()==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 247;BA.debugLine="lblLlanura.Visible = False";
mostCurrent._lblllanura.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 248;BA.debugLine="lblSierra.Visible = False";
mostCurrent._lblsierra.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 249;BA.debugLine="lblTipoAmbiente.Visible = False";
mostCurrent._lbltipoambiente.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 250;BA.debugLine="imgSierra.Visible = False";
mostCurrent._imgsierra.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 251;BA.debugLine="imgLlanura.Visible = False";
mostCurrent._imgllanura.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 252;BA.debugLine="lblLlanura.SetVisibleAnimated(300, True)";
mostCurrent._lblllanura.SetVisibleAnimated((int) (300),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 253;BA.debugLine="lblTipoAmbiente.SetVisibleAnimated(300, True)";
mostCurrent._lbltipoambiente.SetVisibleAnimated((int) (300),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 254;BA.debugLine="imgLlanura.SetVisibleAnimated(300, True)";
mostCurrent._imgllanura.SetVisibleAnimated((int) (300),anywheresoftware.b4a.keywords.Common.True);
 }else {
 //BA.debugLineNum = 256;BA.debugLine="lblRojoAmbiente.Visible = False";
mostCurrent._lblrojoambiente.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 257;BA.debugLine="opcionElegida = \"\"";
mostCurrent._opcionelegida = "";
 //BA.debugLineNum = 259;BA.debugLine="lblLlanura.Visible = False";
mostCurrent._lblllanura.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 260;BA.debugLine="lblSierra.Visible = False";
mostCurrent._lblsierra.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 261;BA.debugLine="lblTipoAmbiente.Visible = False";
mostCurrent._lbltipoambiente.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 262;BA.debugLine="imgSierra.Visible = False";
mostCurrent._imgsierra.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 263;BA.debugLine="imgLlanura.Visible = False";
mostCurrent._imgllanura.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 265;BA.debugLine="lblLlanura.SetVisibleAnimated(300, True)";
mostCurrent._lblllanura.SetVisibleAnimated((int) (300),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 266;BA.debugLine="lblSierra.SetVisibleAnimated(300, True)";
mostCurrent._lblsierra.SetVisibleAnimated((int) (300),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 267;BA.debugLine="lblTipoAmbiente.SetVisibleAnimated(300, True)";
mostCurrent._lbltipoambiente.SetVisibleAnimated((int) (300),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 268;BA.debugLine="imgSierra.SetVisibleAnimated(300, True)";
mostCurrent._imgsierra.SetVisibleAnimated((int) (300),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 269;BA.debugLine="imgLlanura.SetVisibleAnimated(300, True)";
mostCurrent._imgllanura.SetVisibleAnimated((int) (300),anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 272;BA.debugLine="End Sub";
return "";
}
public static String  _imgllanura_click() throws Exception{
 //BA.debugLineNum = 317;BA.debugLine="Private Sub imgLlanura_Click";
 //BA.debugLineNum = 318;BA.debugLine="If tipocuerpo = \"rio\" Then";
if ((mostCurrent._tipocuerpo).equals("rio")) { 
 //BA.debugLineNum = 319;BA.debugLine="opcionElegida = \"llanura\"";
mostCurrent._opcionelegida = "llanura";
 }else if((mostCurrent._tipocuerpo).equals("laguna")) { 
 //BA.debugLineNum = 321;BA.debugLine="opcionElegida = \"laguna\"";
mostCurrent._opcionelegida = "laguna";
 };
 //BA.debugLineNum = 323;BA.debugLine="lblRojoAmbiente.Visible = True";
mostCurrent._lblrojoambiente.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 324;BA.debugLine="lblRojoAmbiente.Left = imgLlanura.Left";
mostCurrent._lblrojoambiente.setLeft(mostCurrent._imgllanura.getLeft());
 //BA.debugLineNum = 325;BA.debugLine="End Sub";
return "";
}
public static String  _imgrio_click() throws Exception{
 //BA.debugLineNum = 273;BA.debugLine="Sub imgRio_Click";
 //BA.debugLineNum = 274;BA.debugLine="lblRojoTipoCuerpo.Visible = True";
mostCurrent._lblrojotipocuerpo.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 275;BA.debugLine="lblRojoTipoCuerpo.Left = imgRio.Left";
mostCurrent._lblrojotipocuerpo.setLeft(mostCurrent._imgrio.getLeft());
 //BA.debugLineNum = 276;BA.debugLine="tipocuerpo = \"rio\"";
mostCurrent._tipocuerpo = "rio";
 //BA.debugLineNum = 277;BA.debugLine="If lblRojoAmbiente.Visible = False Then";
if (mostCurrent._lblrojoambiente.getVisible()==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 278;BA.debugLine="lblLlanura.Visible = False";
mostCurrent._lblllanura.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 279;BA.debugLine="lblSierra.Visible = False";
mostCurrent._lblsierra.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 280;BA.debugLine="lblTipoAmbiente.Visible = False";
mostCurrent._lbltipoambiente.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 281;BA.debugLine="imgSierra.Visible = False";
mostCurrent._imgsierra.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 282;BA.debugLine="imgLlanura.Visible = False";
mostCurrent._imgllanura.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 283;BA.debugLine="lblLlanura.SetVisibleAnimated(300, True)";
mostCurrent._lblllanura.SetVisibleAnimated((int) (300),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 284;BA.debugLine="lblSierra.SetVisibleAnimated(300, True)";
mostCurrent._lblsierra.SetVisibleAnimated((int) (300),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 285;BA.debugLine="lblTipoAmbiente.SetVisibleAnimated(300, True)";
mostCurrent._lbltipoambiente.SetVisibleAnimated((int) (300),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 286;BA.debugLine="imgSierra.SetVisibleAnimated(300, True)";
mostCurrent._imgsierra.SetVisibleAnimated((int) (300),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 287;BA.debugLine="imgLlanura.SetVisibleAnimated(300, True)";
mostCurrent._imgllanura.SetVisibleAnimated((int) (300),anywheresoftware.b4a.keywords.Common.True);
 }else {
 //BA.debugLineNum = 289;BA.debugLine="lblRojoAmbiente.Visible = False";
mostCurrent._lblrojoambiente.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 290;BA.debugLine="opcionElegida = \"\"";
mostCurrent._opcionelegida = "";
 //BA.debugLineNum = 291;BA.debugLine="lblLlanura.Visible = False";
mostCurrent._lblllanura.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 292;BA.debugLine="lblSierra.Visible = False";
mostCurrent._lblsierra.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 293;BA.debugLine="lblTipoAmbiente.Visible = False";
mostCurrent._lbltipoambiente.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 294;BA.debugLine="imgSierra.Visible = False";
mostCurrent._imgsierra.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 295;BA.debugLine="imgLlanura.Visible = False";
mostCurrent._imgllanura.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 297;BA.debugLine="lblLlanura.SetVisibleAnimated(300, True)";
mostCurrent._lblllanura.SetVisibleAnimated((int) (300),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 298;BA.debugLine="lblSierra.SetVisibleAnimated(300, True)";
mostCurrent._lblsierra.SetVisibleAnimated((int) (300),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 299;BA.debugLine="lblTipoAmbiente.SetVisibleAnimated(300, True)";
mostCurrent._lbltipoambiente.SetVisibleAnimated((int) (300),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 300;BA.debugLine="imgSierra.SetVisibleAnimated(300, True)";
mostCurrent._imgsierra.SetVisibleAnimated((int) (300),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 301;BA.debugLine="imgLlanura.SetVisibleAnimated(300, True)";
mostCurrent._imgllanura.SetVisibleAnimated((int) (300),anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 304;BA.debugLine="End Sub";
return "";
}
public static String  _imgsierra_click() throws Exception{
 //BA.debugLineNum = 337;BA.debugLine="Private Sub imgSierra_Click";
 //BA.debugLineNum = 338;BA.debugLine="If tipocuerpo = \"rio\" Then";
if ((mostCurrent._tipocuerpo).equals("rio")) { 
 //BA.debugLineNum = 339;BA.debugLine="opcionElegida = \"sierras\"";
mostCurrent._opcionelegida = "sierras";
 }else if((mostCurrent._tipocuerpo).equals("laguna")) { 
 //BA.debugLineNum = 341;BA.debugLine="opcionElegida = \"sierras_laguna\"";
mostCurrent._opcionelegida = "sierras_laguna";
 };
 //BA.debugLineNum = 343;BA.debugLine="lblRojoAmbiente.Visible = True";
mostCurrent._lblrojoambiente.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 344;BA.debugLine="lblRojoAmbiente.Left = imgSierra.Left";
mostCurrent._lblrojoambiente.setLeft(mostCurrent._imgsierra.getLeft());
 //BA.debugLineNum = 345;BA.debugLine="End Sub";
return "";
}
public static String  _lblllanura_click() throws Exception{
 //BA.debugLineNum = 306;BA.debugLine="Private Sub lblLlanura_Click";
 //BA.debugLineNum = 307;BA.debugLine="If tipocuerpo = \"rio\" Then";
if ((mostCurrent._tipocuerpo).equals("rio")) { 
 //BA.debugLineNum = 308;BA.debugLine="opcionElegida = \"llanura\"";
mostCurrent._opcionelegida = "llanura";
 }else if((mostCurrent._tipocuerpo).equals("laguna")) { 
 //BA.debugLineNum = 310;BA.debugLine="opcionElegida = \"laguna\"";
mostCurrent._opcionelegida = "laguna";
 };
 //BA.debugLineNum = 313;BA.debugLine="lblRojoAmbiente.Visible = True";
mostCurrent._lblrojoambiente.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 314;BA.debugLine="lblRojoAmbiente.Left = imgLlanura.Left";
mostCurrent._lblrojoambiente.setLeft(mostCurrent._imgllanura.getLeft());
 //BA.debugLineNum = 315;BA.debugLine="End Sub";
return "";
}
public static String  _lblsierra_click() throws Exception{
 //BA.debugLineNum = 327;BA.debugLine="Private Sub lblSierra_Click";
 //BA.debugLineNum = 328;BA.debugLine="If tipocuerpo = \"rio\" Then";
if ((mostCurrent._tipocuerpo).equals("rio")) { 
 //BA.debugLineNum = 329;BA.debugLine="opcionElegida = \"sierras\"";
mostCurrent._opcionelegida = "sierras";
 }else if((mostCurrent._tipocuerpo).equals("laguna")) { 
 //BA.debugLineNum = 331;BA.debugLine="opcionElegida = \"sierras_laguna\"";
mostCurrent._opcionelegida = "sierras_laguna";
 };
 //BA.debugLineNum = 333;BA.debugLine="lblRojoAmbiente.Visible = True";
mostCurrent._lblrojoambiente.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 334;BA.debugLine="lblRojoAmbiente.Left = imgSierra.Left";
mostCurrent._lblrojoambiente.setLeft(mostCurrent._imgsierra.getLeft());
 //BA.debugLineNum = 335;BA.debugLine="End Sub";
return "";
}
public static void  _load_reporte_basicos() throws Exception{
ResumableSub_Load_Reporte_Basicos rsub = new ResumableSub_Load_Reporte_Basicos(null);
rsub.resume(processBA, null);
}
public static class ResumableSub_Load_Reporte_Basicos extends BA.ResumableSub {
public ResumableSub_Load_Reporte_Basicos(appear.pnud.preservamos.form_reporte parent) {
this.parent = parent;
}
appear.pnud.preservamos.form_reporte parent;
int _result = 0;
anywheresoftware.b4a.objects.collections.List _neweval = null;
anywheresoftware.b4a.objects.collections.Map _m = null;
anywheresoftware.b4a.objects.collections.Map _currentprojectmap = null;
String _usernamenoaccent = "";
anywheresoftware.b4a.objects.collections.Map _map1 = null;

@Override
public void resume(BA ba, Object[] result) throws Exception{

    while (true) {
try {

        switch (state) {
            case -1:
return;

case 0:
//C
this.state = 1;
 //BA.debugLineNum = 80;BA.debugLine="If Main.currentEcoregion = \"Altos Andes\" Then";
if (true) break;

case 1:
//if
this.state = 86;
if ((parent.mostCurrent._main._currentecoregion /*String*/ ).equals("Altos Andes")) { 
this.state = 3;
}else if((parent.mostCurrent._main._currentecoregion /*String*/ ).equals("Puna")) { 
this.state = 9;
}else if((parent.mostCurrent._main._currentecoregion /*String*/ ).equals("Monte de sierras y bolsones")) { 
this.state = 15;
}else if((parent.mostCurrent._main._currentecoregion /*String*/ ).equals("Selva de las yungas")) { 
this.state = 21;
}else if((parent.mostCurrent._main._currentecoregion /*String*/ ).equals("Chaco seco")) { 
this.state = 27;
}else if((parent.mostCurrent._main._currentecoregion /*String*/ ).equals("Chaco humedo")) { 
this.state = 29;
}else if((parent.mostCurrent._main._currentecoregion /*String*/ ).equals("Selva paranaense")) { 
this.state = 35;
}else if((parent.mostCurrent._main._currentecoregion /*String*/ ).equals("Esteros del Ibera")) { 
this.state = 41;
}else if((parent.mostCurrent._main._currentecoregion /*String*/ ).equals("Campos y malezales")) { 
this.state = 47;
}else if((parent.mostCurrent._main._currentecoregion /*String*/ ).equals("Delta e islas del Parana")) { 
this.state = 53;
}else if((parent.mostCurrent._main._currentecoregion /*String*/ ).equals("Espinal")) { 
this.state = 59;
}else if((parent.mostCurrent._main._currentecoregion /*String*/ ).equals("Pampa")) { 
this.state = 65;
}else if((parent.mostCurrent._main._currentecoregion /*String*/ ).equals("Monte de llanuras y mesetas")) { 
this.state = 67;
}else if((parent.mostCurrent._main._currentecoregion /*String*/ ).equals("Estepa patagonica")) { 
this.state = 73;
}else if((parent.mostCurrent._main._currentecoregion /*String*/ ).equals("Bosques patagonicos")) { 
this.state = 79;
}else if((parent.mostCurrent._main._currentecoregion /*String*/ ).equals("")) { 
this.state = 85;
}if (true) break;

case 3:
//C
this.state = 4;
 //BA.debugLineNum = 81;BA.debugLine="Msgbox2Async(\"El análisis de hábitat no fue cali";
anywheresoftware.b4a.keywords.Common.Msgbox2Async(BA.ObjectToCharSequence("El análisis de hábitat no fue calibrado en la ecorregión en la que te encuentras. Es probable que algunas preguntas no tengan sentido y que los resultados no sean precisos. ¿Deseas seguir igual?"),BA.ObjectToCharSequence("¡Atención!"),"Seguir","","No, volver",(anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper(), (android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null)),processBA,anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 82;BA.debugLine="Wait For Msgbox_Result (Result As Int)";
anywheresoftware.b4a.keywords.Common.WaitFor("msgbox_result", processBA, this, null);
this.state = 103;
return;
case 103:
//C
this.state = 4;
_result = (Integer) result[0];
;
 //BA.debugLineNum = 83;BA.debugLine="If Result = DialogResponse.NEGATIVE Then";
if (true) break;

case 4:
//if
this.state = 7;
if (_result==anywheresoftware.b4a.keywords.Common.DialogResponse.NEGATIVE) { 
this.state = 6;
}if (true) break;

case 6:
//C
this.state = 7;
 //BA.debugLineNum = 84;BA.debugLine="Activity.finish";
parent.mostCurrent._activity.Finish();
 //BA.debugLineNum = 85;BA.debugLine="Activity.RemoveAllViews";
parent.mostCurrent._activity.RemoveAllViews();
 if (true) break;

case 7:
//C
this.state = 86;
;
 if (true) break;

case 9:
//C
this.state = 10;
 //BA.debugLineNum = 88;BA.debugLine="Msgbox2Async(\"El análisis de hábitat no fue cali";
anywheresoftware.b4a.keywords.Common.Msgbox2Async(BA.ObjectToCharSequence("El análisis de hábitat no fue calibrado en la ecorregión en la que te encuentras. Es probable que algunas preguntas no tengan sentido y que los resultados no sean precisos. ¿Deseas seguir igual?"),BA.ObjectToCharSequence("¡Atención!"),"Seguir","","No, volver",(anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper(), (android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null)),processBA,anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 89;BA.debugLine="Wait For Msgbox_Result (Result As Int)";
anywheresoftware.b4a.keywords.Common.WaitFor("msgbox_result", processBA, this, null);
this.state = 104;
return;
case 104:
//C
this.state = 10;
_result = (Integer) result[0];
;
 //BA.debugLineNum = 90;BA.debugLine="If Result = DialogResponse.NEGATIVE Then";
if (true) break;

case 10:
//if
this.state = 13;
if (_result==anywheresoftware.b4a.keywords.Common.DialogResponse.NEGATIVE) { 
this.state = 12;
}if (true) break;

case 12:
//C
this.state = 13;
 //BA.debugLineNum = 91;BA.debugLine="Activity.finish";
parent.mostCurrent._activity.Finish();
 //BA.debugLineNum = 92;BA.debugLine="Activity.RemoveAllViews";
parent.mostCurrent._activity.RemoveAllViews();
 if (true) break;

case 13:
//C
this.state = 86;
;
 if (true) break;

case 15:
//C
this.state = 16;
 //BA.debugLineNum = 95;BA.debugLine="Msgbox2Async(\"El análisis de hábitat no fue cali";
anywheresoftware.b4a.keywords.Common.Msgbox2Async(BA.ObjectToCharSequence("El análisis de hábitat no fue calibrado en la ecorregión en la que te encuentras. Es probable que algunas preguntas no tengan sentido y que los resultados no sean precisos. ¿Deseas seguir igual?"),BA.ObjectToCharSequence("¡Atención!"),"Seguir","","No, volver",(anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper(), (android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null)),processBA,anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 96;BA.debugLine="Wait For Msgbox_Result (Result As Int)";
anywheresoftware.b4a.keywords.Common.WaitFor("msgbox_result", processBA, this, null);
this.state = 105;
return;
case 105:
//C
this.state = 16;
_result = (Integer) result[0];
;
 //BA.debugLineNum = 97;BA.debugLine="If Result = DialogResponse.NEGATIVE Then";
if (true) break;

case 16:
//if
this.state = 19;
if (_result==anywheresoftware.b4a.keywords.Common.DialogResponse.NEGATIVE) { 
this.state = 18;
}if (true) break;

case 18:
//C
this.state = 19;
 //BA.debugLineNum = 98;BA.debugLine="Activity.finish";
parent.mostCurrent._activity.Finish();
 //BA.debugLineNum = 99;BA.debugLine="Activity.RemoveAllViews";
parent.mostCurrent._activity.RemoveAllViews();
 if (true) break;

case 19:
//C
this.state = 86;
;
 if (true) break;

case 21:
//C
this.state = 22;
 //BA.debugLineNum = 102;BA.debugLine="Msgbox2Async(\"El análisis de hábitat no fue cali";
anywheresoftware.b4a.keywords.Common.Msgbox2Async(BA.ObjectToCharSequence("El análisis de hábitat no fue calibrado en la ecorregión en la que te encuentras. Es probable que algunas preguntas no tengan sentido y que los resultados no sean precisos. ¿Deseas seguir igual?"),BA.ObjectToCharSequence("¡Atención!"),"Seguir","","No, volver",(anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper(), (android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null)),processBA,anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 103;BA.debugLine="Wait For Msgbox_Result (Result As Int)";
anywheresoftware.b4a.keywords.Common.WaitFor("msgbox_result", processBA, this, null);
this.state = 106;
return;
case 106:
//C
this.state = 22;
_result = (Integer) result[0];
;
 //BA.debugLineNum = 104;BA.debugLine="If Result = DialogResponse.NEGATIVE Then";
if (true) break;

case 22:
//if
this.state = 25;
if (_result==anywheresoftware.b4a.keywords.Common.DialogResponse.NEGATIVE) { 
this.state = 24;
}if (true) break;

case 24:
//C
this.state = 25;
 //BA.debugLineNum = 105;BA.debugLine="Activity.finish";
parent.mostCurrent._activity.Finish();
 //BA.debugLineNum = 106;BA.debugLine="Activity.RemoveAllViews";
parent.mostCurrent._activity.RemoveAllViews();
 if (true) break;

case 25:
//C
this.state = 86;
;
 if (true) break;

case 27:
//C
this.state = 86;
 if (true) break;

case 29:
//C
this.state = 30;
 //BA.debugLineNum = 111;BA.debugLine="Msgbox2Async(\"El análisis de hábitat no fue cali";
anywheresoftware.b4a.keywords.Common.Msgbox2Async(BA.ObjectToCharSequence("El análisis de hábitat no fue calibrado en la ecorregión en la que te encuentras. Es probable que algunas preguntas no tengan sentido y que los resultados no sean precisos. ¿Deseas seguir igual?"),BA.ObjectToCharSequence("¡Atención!"),"Seguir","","No, volver",(anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper(), (android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null)),processBA,anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 112;BA.debugLine="Wait For Msgbox_Result (Result As Int)";
anywheresoftware.b4a.keywords.Common.WaitFor("msgbox_result", processBA, this, null);
this.state = 107;
return;
case 107:
//C
this.state = 30;
_result = (Integer) result[0];
;
 //BA.debugLineNum = 113;BA.debugLine="If Result = DialogResponse.NEGATIVE Then";
if (true) break;

case 30:
//if
this.state = 33;
if (_result==anywheresoftware.b4a.keywords.Common.DialogResponse.NEGATIVE) { 
this.state = 32;
}if (true) break;

case 32:
//C
this.state = 33;
 //BA.debugLineNum = 114;BA.debugLine="Activity.finish";
parent.mostCurrent._activity.Finish();
 //BA.debugLineNum = 115;BA.debugLine="Activity.RemoveAllViews";
parent.mostCurrent._activity.RemoveAllViews();
 if (true) break;

case 33:
//C
this.state = 86;
;
 if (true) break;

case 35:
//C
this.state = 36;
 //BA.debugLineNum = 118;BA.debugLine="Msgbox2Async(\"El análisis de hábitat no fue cali";
anywheresoftware.b4a.keywords.Common.Msgbox2Async(BA.ObjectToCharSequence("El análisis de hábitat no fue calibrado en la ecorregión en la que te encuentras. Es probable que algunas preguntas no tengan sentido y que los resultados no sean precisos. ¿Deseas seguir igual?"),BA.ObjectToCharSequence("¡Atención!"),"Seguir","","No, volver",(anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper(), (android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null)),processBA,anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 119;BA.debugLine="Wait For Msgbox_Result (Result As Int)";
anywheresoftware.b4a.keywords.Common.WaitFor("msgbox_result", processBA, this, null);
this.state = 108;
return;
case 108:
//C
this.state = 36;
_result = (Integer) result[0];
;
 //BA.debugLineNum = 120;BA.debugLine="If Result = DialogResponse.NEGATIVE Then";
if (true) break;

case 36:
//if
this.state = 39;
if (_result==anywheresoftware.b4a.keywords.Common.DialogResponse.NEGATIVE) { 
this.state = 38;
}if (true) break;

case 38:
//C
this.state = 39;
 //BA.debugLineNum = 121;BA.debugLine="Activity.finish";
parent.mostCurrent._activity.Finish();
 //BA.debugLineNum = 122;BA.debugLine="Activity.RemoveAllViews";
parent.mostCurrent._activity.RemoveAllViews();
 if (true) break;

case 39:
//C
this.state = 86;
;
 if (true) break;

case 41:
//C
this.state = 42;
 //BA.debugLineNum = 125;BA.debugLine="Msgbox2Async(\"El análisis de hábitat no fue cali";
anywheresoftware.b4a.keywords.Common.Msgbox2Async(BA.ObjectToCharSequence("El análisis de hábitat no fue calibrado en la ecorregión en la que te encuentras. Es probable que algunas preguntas no tengan sentido y que los resultados no sean precisos. ¿Deseas seguir igual?"),BA.ObjectToCharSequence("¡Atención!"),"Seguir","","No, volver",(anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper(), (android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null)),processBA,anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 126;BA.debugLine="Wait For Msgbox_Result (Result As Int)";
anywheresoftware.b4a.keywords.Common.WaitFor("msgbox_result", processBA, this, null);
this.state = 109;
return;
case 109:
//C
this.state = 42;
_result = (Integer) result[0];
;
 //BA.debugLineNum = 127;BA.debugLine="If Result = DialogResponse.NEGATIVE Then";
if (true) break;

case 42:
//if
this.state = 45;
if (_result==anywheresoftware.b4a.keywords.Common.DialogResponse.NEGATIVE) { 
this.state = 44;
}if (true) break;

case 44:
//C
this.state = 45;
 //BA.debugLineNum = 128;BA.debugLine="Activity.finish";
parent.mostCurrent._activity.Finish();
 //BA.debugLineNum = 129;BA.debugLine="Activity.RemoveAllViews";
parent.mostCurrent._activity.RemoveAllViews();
 if (true) break;

case 45:
//C
this.state = 86;
;
 if (true) break;

case 47:
//C
this.state = 48;
 //BA.debugLineNum = 132;BA.debugLine="Msgbox2Async(\"El análisis de hábitat no fue cali";
anywheresoftware.b4a.keywords.Common.Msgbox2Async(BA.ObjectToCharSequence("El análisis de hábitat no fue calibrado en la ecorregión en la que te encuentras. Es probable que algunas preguntas no tengan sentido y que los resultados no sean precisos. ¿Deseas seguir igual?"),BA.ObjectToCharSequence("¡Atención!"),"Seguir","","No, volver",(anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper(), (android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null)),processBA,anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 133;BA.debugLine="Wait For Msgbox_Result (Result As Int)";
anywheresoftware.b4a.keywords.Common.WaitFor("msgbox_result", processBA, this, null);
this.state = 110;
return;
case 110:
//C
this.state = 48;
_result = (Integer) result[0];
;
 //BA.debugLineNum = 134;BA.debugLine="If Result = DialogResponse.NEGATIVE Then";
if (true) break;

case 48:
//if
this.state = 51;
if (_result==anywheresoftware.b4a.keywords.Common.DialogResponse.NEGATIVE) { 
this.state = 50;
}if (true) break;

case 50:
//C
this.state = 51;
 //BA.debugLineNum = 135;BA.debugLine="Activity.finish";
parent.mostCurrent._activity.Finish();
 //BA.debugLineNum = 136;BA.debugLine="Activity.RemoveAllViews";
parent.mostCurrent._activity.RemoveAllViews();
 if (true) break;

case 51:
//C
this.state = 86;
;
 if (true) break;

case 53:
//C
this.state = 54;
 //BA.debugLineNum = 139;BA.debugLine="Msgbox2Async(\"El análisis de hábitat no fue cali";
anywheresoftware.b4a.keywords.Common.Msgbox2Async(BA.ObjectToCharSequence("El análisis de hábitat no fue calibrado en la ecorregión en la que te encuentras. Es probable que algunas preguntas no tengan sentido y que los resultados no sean precisos. ¿Deseas seguir igual?"),BA.ObjectToCharSequence("¡Atención!"),"Seguir","","No, volver",(anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper(), (android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null)),processBA,anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 140;BA.debugLine="Wait For Msgbox_Result (Result As Int)";
anywheresoftware.b4a.keywords.Common.WaitFor("msgbox_result", processBA, this, null);
this.state = 111;
return;
case 111:
//C
this.state = 54;
_result = (Integer) result[0];
;
 //BA.debugLineNum = 141;BA.debugLine="If Result = DialogResponse.NEGATIVE Then";
if (true) break;

case 54:
//if
this.state = 57;
if (_result==anywheresoftware.b4a.keywords.Common.DialogResponse.NEGATIVE) { 
this.state = 56;
}if (true) break;

case 56:
//C
this.state = 57;
 //BA.debugLineNum = 142;BA.debugLine="Activity.finish";
parent.mostCurrent._activity.Finish();
 //BA.debugLineNum = 143;BA.debugLine="Activity.RemoveAllViews";
parent.mostCurrent._activity.RemoveAllViews();
 if (true) break;

case 57:
//C
this.state = 86;
;
 if (true) break;

case 59:
//C
this.state = 60;
 //BA.debugLineNum = 146;BA.debugLine="Msgbox2Async(\"El análisis de hábitat no fue cali";
anywheresoftware.b4a.keywords.Common.Msgbox2Async(BA.ObjectToCharSequence("El análisis de hábitat no fue calibrado en la ecorregión en la que te encuentras. Es probable que algunas preguntas no tengan sentido y que los resultados no sean precisos. ¿Deseas seguir igual?"),BA.ObjectToCharSequence("¡Atención!"),"Seguir","","No, volver",(anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper(), (android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null)),processBA,anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 147;BA.debugLine="Wait For Msgbox_Result (Result As Int)";
anywheresoftware.b4a.keywords.Common.WaitFor("msgbox_result", processBA, this, null);
this.state = 112;
return;
case 112:
//C
this.state = 60;
_result = (Integer) result[0];
;
 //BA.debugLineNum = 148;BA.debugLine="If Result = DialogResponse.NEGATIVE Then";
if (true) break;

case 60:
//if
this.state = 63;
if (_result==anywheresoftware.b4a.keywords.Common.DialogResponse.NEGATIVE) { 
this.state = 62;
}if (true) break;

case 62:
//C
this.state = 63;
 //BA.debugLineNum = 149;BA.debugLine="Activity.finish";
parent.mostCurrent._activity.Finish();
 //BA.debugLineNum = 150;BA.debugLine="Activity.RemoveAllViews";
parent.mostCurrent._activity.RemoveAllViews();
 if (true) break;

case 63:
//C
this.state = 86;
;
 if (true) break;

case 65:
//C
this.state = 86;
 if (true) break;

case 67:
//C
this.state = 68;
 //BA.debugLineNum = 155;BA.debugLine="Msgbox2Async(\"El análisis de hábitat no fue cali";
anywheresoftware.b4a.keywords.Common.Msgbox2Async(BA.ObjectToCharSequence("El análisis de hábitat no fue calibrado en la ecorregión en la que te encuentras. Es probable que algunas preguntas no tengan sentido y que los resultados no sean precisos. ¿Deseas seguir igual?"),BA.ObjectToCharSequence("¡Atención!"),"Seguir","","No, volver",(anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper(), (android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null)),processBA,anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 156;BA.debugLine="Wait For Msgbox_Result (Result As Int)";
anywheresoftware.b4a.keywords.Common.WaitFor("msgbox_result", processBA, this, null);
this.state = 113;
return;
case 113:
//C
this.state = 68;
_result = (Integer) result[0];
;
 //BA.debugLineNum = 157;BA.debugLine="If Result = DialogResponse.NEGATIVE Then";
if (true) break;

case 68:
//if
this.state = 71;
if (_result==anywheresoftware.b4a.keywords.Common.DialogResponse.NEGATIVE) { 
this.state = 70;
}if (true) break;

case 70:
//C
this.state = 71;
 //BA.debugLineNum = 158;BA.debugLine="Activity.finish";
parent.mostCurrent._activity.Finish();
 //BA.debugLineNum = 159;BA.debugLine="Activity.RemoveAllViews";
parent.mostCurrent._activity.RemoveAllViews();
 if (true) break;

case 71:
//C
this.state = 86;
;
 if (true) break;

case 73:
//C
this.state = 74;
 //BA.debugLineNum = 162;BA.debugLine="Msgbox2Async(\"El análisis de hábitat no fue cali";
anywheresoftware.b4a.keywords.Common.Msgbox2Async(BA.ObjectToCharSequence("El análisis de hábitat no fue calibrado en la ecorregión en la que te encuentras. Es probable que algunas preguntas no tengan sentido y que los resultados no sean precisos. ¿Deseas seguir igual?"),BA.ObjectToCharSequence("¡Atención!"),"Seguir","","No, volver",(anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper(), (android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null)),processBA,anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 163;BA.debugLine="Wait For Msgbox_Result (Result As Int)";
anywheresoftware.b4a.keywords.Common.WaitFor("msgbox_result", processBA, this, null);
this.state = 114;
return;
case 114:
//C
this.state = 74;
_result = (Integer) result[0];
;
 //BA.debugLineNum = 164;BA.debugLine="If Result = DialogResponse.NEGATIVE Then";
if (true) break;

case 74:
//if
this.state = 77;
if (_result==anywheresoftware.b4a.keywords.Common.DialogResponse.NEGATIVE) { 
this.state = 76;
}if (true) break;

case 76:
//C
this.state = 77;
 //BA.debugLineNum = 165;BA.debugLine="Activity.finish";
parent.mostCurrent._activity.Finish();
 //BA.debugLineNum = 166;BA.debugLine="Activity.RemoveAllViews";
parent.mostCurrent._activity.RemoveAllViews();
 if (true) break;

case 77:
//C
this.state = 86;
;
 if (true) break;

case 79:
//C
this.state = 80;
 //BA.debugLineNum = 169;BA.debugLine="Msgbox2Async(\"El análisis de hábitat no fue cali";
anywheresoftware.b4a.keywords.Common.Msgbox2Async(BA.ObjectToCharSequence("El análisis de hábitat no fue calibrado en la ecorregión en la que te encuentras. Es probable que algunas preguntas no tengan sentido y que los resultados no sean precisos. ¿Deseas seguir igual?"),BA.ObjectToCharSequence("¡Atención!"),"Seguir","","No, volver",(anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper(), (android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null)),processBA,anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 170;BA.debugLine="Wait For Msgbox_Result (Result As Int)";
anywheresoftware.b4a.keywords.Common.WaitFor("msgbox_result", processBA, this, null);
this.state = 115;
return;
case 115:
//C
this.state = 80;
_result = (Integer) result[0];
;
 //BA.debugLineNum = 171;BA.debugLine="If Result = DialogResponse.NEGATIVE Then";
if (true) break;

case 80:
//if
this.state = 83;
if (_result==anywheresoftware.b4a.keywords.Common.DialogResponse.NEGATIVE) { 
this.state = 82;
}if (true) break;

case 82:
//C
this.state = 83;
 //BA.debugLineNum = 172;BA.debugLine="Activity.finish";
parent.mostCurrent._activity.Finish();
 //BA.debugLineNum = 173;BA.debugLine="Activity.RemoveAllViews";
parent.mostCurrent._activity.RemoveAllViews();
 if (true) break;

case 83:
//C
this.state = 86;
;
 if (true) break;

case 85:
//C
this.state = 86;
 if (true) break;

case 86:
//C
this.state = 87;
;
 //BA.debugLineNum = 182;BA.debugLine="Dim newEval As List";
_neweval = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 183;BA.debugLine="newEval.Initialize";
_neweval.Initialize();
 //BA.debugLineNum = 184;BA.debugLine="Dim m As Map";
_m = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 185;BA.debugLine="m.Initialize";
_m.Initialize();
 //BA.debugLineNum = 186;BA.debugLine="m.Put(\"usuario\", Main.strUserEmail)";
_m.Put((Object)("usuario"),(Object)(parent.mostCurrent._main._struseremail /*String*/ ));
 //BA.debugLineNum = 187;BA.debugLine="newEval.Add(m)";
_neweval.Add((Object)(_m.getObject()));
 //BA.debugLineNum = 188;BA.debugLine="If Starter.sqlDB = Null Then";
if (true) break;

case 87:
//if
this.state = 90;
if (parent.mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ == null) { 
this.state = 89;
}if (true) break;

case 89:
//C
this.state = 90;
 //BA.debugLineNum = 189;BA.debugLine="Starter.sqlDB.Initialize(Starter.dbdir, \"preserv";
parent.mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ .Initialize(parent.mostCurrent._starter._dbdir /*String*/ ,"preservamosdb.db",anywheresoftware.b4a.keywords.Common.False);
 if (true) break;

case 90:
//C
this.state = 91;
;
 //BA.debugLineNum = 191;BA.debugLine="DBUtils.InsertMaps(Starter.sqlDB,\"evals\", newEval";
parent.mostCurrent._dbutils._insertmaps /*String*/ (mostCurrent.activityBA,parent.mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals",_neweval);
 //BA.debugLineNum = 195;BA.debugLine="Dim currentprojectMap As Map";
_currentprojectmap = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 196;BA.debugLine="currentprojectMap.Initialize";
_currentprojectmap.Initialize();
 //BA.debugLineNum = 197;BA.debugLine="Try";
if (true) break;

case 91:
//try
this.state = 102;
this.catchState = 101;
this.state = 93;
if (true) break;

case 93:
//C
this.state = 94;
this.catchState = 101;
 //BA.debugLineNum = 198;BA.debugLine="currentprojectMap = DBUtils.ExecuteMap(Starter.s";
_currentprojectmap = parent.mostCurrent._dbutils._executemap /*anywheresoftware.b4a.objects.collections.Map*/ (mostCurrent.activityBA,parent.mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"SELECT * FROM evals ORDER BY id DESC LIMIT 1",(String[])(anywheresoftware.b4a.keywords.Common.Null));
 //BA.debugLineNum = 199;BA.debugLine="Dim usernameNoAccent As String";
_usernamenoaccent = "";
 //BA.debugLineNum = 200;BA.debugLine="usernameNoAccent = Main.username.Replace(\"á\", \"a";
_usernamenoaccent = parent.mostCurrent._main._username /*String*/ .replace("á","a");
 //BA.debugLineNum = 201;BA.debugLine="usernameNoAccent = Main.username.Replace(\"é\", \"e";
_usernamenoaccent = parent.mostCurrent._main._username /*String*/ .replace("é","e");
 //BA.debugLineNum = 202;BA.debugLine="usernameNoAccent = Main.username.Replace(\"í\", \"i";
_usernamenoaccent = parent.mostCurrent._main._username /*String*/ .replace("í","i");
 //BA.debugLineNum = 203;BA.debugLine="usernameNoAccent = Main.username.Replace(\"ó\", \"o";
_usernamenoaccent = parent.mostCurrent._main._username /*String*/ .replace("ó","o");
 //BA.debugLineNum = 204;BA.debugLine="usernameNoAccent = Main.username.Replace(\"ú\", \"u";
_usernamenoaccent = parent.mostCurrent._main._username /*String*/ .replace("ú","u");
 //BA.debugLineNum = 205;BA.debugLine="usernameNoAccent = Main.username.Replace(\"ñ\", \"n";
_usernamenoaccent = parent.mostCurrent._main._username /*String*/ .replace("ñ","n");
 //BA.debugLineNum = 206;BA.debugLine="usernameNoAccent = Main.username.Replace(\"@\", \"a";
_usernamenoaccent = parent.mostCurrent._main._username /*String*/ .replace("@","ae");
 //BA.debugLineNum = 208;BA.debugLine="If currentprojectMap = Null Or currentprojectMap";
if (true) break;

case 94:
//if
this.state = 99;
if (_currentprojectmap== null || _currentprojectmap.IsInitialized()==anywheresoftware.b4a.keywords.Common.False) { 
this.state = 96;
}else {
this.state = 98;
}if (true) break;

case 96:
//C
this.state = 99;
 //BA.debugLineNum = 209;BA.debugLine="currentproject = 1";
parent._currentproject = BA.NumberToString(1);
 //BA.debugLineNum = 210;BA.debugLine="Main.currentproject = currentproject";
parent.mostCurrent._main._currentproject /*String*/  = parent._currentproject;
 //BA.debugLineNum = 211;BA.debugLine="Dim Map1 As Map";
_map1 = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 212;BA.debugLine="Map1.Initialize";
_map1.Initialize();
 //BA.debugLineNum = 213;BA.debugLine="Map1.Put(\"Id\", currentproject)";
_map1.Put((Object)("Id"),(Object)(parent._currentproject));
 //BA.debugLineNum = 214;BA.debugLine="DateTime.DateFormat = \"dd-MM-yyyy\"";
anywheresoftware.b4a.keywords.Common.DateTime.setDateFormat("dd-MM-yyyy");
 //BA.debugLineNum = 215;BA.debugLine="datecurrentproject = DateTime.Date(DateTime.Now";
parent._datecurrentproject = anywheresoftware.b4a.keywords.Common.DateTime.Date(anywheresoftware.b4a.keywords.Common.DateTime.getNow());
 //BA.debugLineNum = 217;BA.debugLine="fullidcurrentproject = usernameNoAccent & \"_\" &";
parent._fullidcurrentproject = _usernamenoaccent+"_"+parent._currentproject+"_"+parent._datecurrentproject;
 //BA.debugLineNum = 218;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"evals\", \"f";
parent.mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,parent.mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals","fullID",(Object)(parent._fullidcurrentproject),_map1);
 //BA.debugLineNum = 219;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"evals\", \"t";
parent.mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,parent.mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals","terminado",(Object)("no"),_map1);
 //BA.debugLineNum = 220;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"evals\", \"e";
parent.mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,parent.mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals","evalsent",(Object)("no"),_map1);
 if (true) break;

case 98:
//C
this.state = 99;
 //BA.debugLineNum = 222;BA.debugLine="currentproject = currentprojectMap.Get(\"id\")";
parent._currentproject = BA.ObjectToString(_currentprojectmap.Get((Object)("id")));
 //BA.debugLineNum = 223;BA.debugLine="Main.currentproject = currentproject";
parent.mostCurrent._main._currentproject /*String*/  = parent._currentproject;
 //BA.debugLineNum = 224;BA.debugLine="Dim Map1 As Map";
_map1 = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 225;BA.debugLine="Map1.Initialize";
_map1.Initialize();
 //BA.debugLineNum = 226;BA.debugLine="Map1.Put(\"Id\", currentproject)";
_map1.Put((Object)("Id"),(Object)(parent._currentproject));
 //BA.debugLineNum = 227;BA.debugLine="DateTime.DateFormat = \"dd-MM-yyyy\"";
anywheresoftware.b4a.keywords.Common.DateTime.setDateFormat("dd-MM-yyyy");
 //BA.debugLineNum = 228;BA.debugLine="datecurrentproject = DateTime.Date(DateTime.Now";
parent._datecurrentproject = anywheresoftware.b4a.keywords.Common.DateTime.Date(anywheresoftware.b4a.keywords.Common.DateTime.getNow());
 //BA.debugLineNum = 229;BA.debugLine="fullidcurrentproject = usernameNoAccent & \"_\" &";
parent._fullidcurrentproject = _usernamenoaccent+"_"+parent._currentproject+"_"+parent._datecurrentproject;
 //BA.debugLineNum = 230;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"evals\", \"f";
parent.mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,parent.mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals","fullID",(Object)(parent._fullidcurrentproject),_map1);
 //BA.debugLineNum = 231;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"evals\", \"t";
parent.mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,parent.mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals","terminado",(Object)("no"),_map1);
 //BA.debugLineNum = 232;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"evals\", \"e";
parent.mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,parent.mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals","evalsent",(Object)("no"),_map1);
 if (true) break;

case 99:
//C
this.state = 102;
;
 if (true) break;

case 101:
//C
this.state = 102;
this.catchState = 0;
 //BA.debugLineNum = 235;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("07078045",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA)),0);
 //BA.debugLineNum = 236;BA.debugLine="ToastMessageShow(\"Hubo un error, intente de nuev";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Hubo un error, intente de nuevo"),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 237;BA.debugLine="Return";
if (true) return ;
 if (true) break;
if (true) break;

case 102:
//C
this.state = -1;
this.catchState = 0;
;
 //BA.debugLineNum = 240;BA.debugLine="End Sub";
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
 //BA.debugLineNum = 14;BA.debugLine="End Sub";
return "";
}
}
