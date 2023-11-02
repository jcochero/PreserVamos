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

public class frmdatosanteriores extends Activity implements B4AActivity{
	public static frmdatosanteriores mostCurrent;
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
			processBA = new BA(this.getApplicationContext(), null, null, "appear.pnud.preservamos", "appear.pnud.preservamos.frmdatosanteriores");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
            
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (frmdatosanteriores).");
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
		activityBA = new BA(this, layout, processBA, "appear.pnud.preservamos", "appear.pnud.preservamos.frmdatosanteriores");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "appear.pnud.preservamos.frmdatosanteriores", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density, mostCurrent);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (frmdatosanteriores) Create " + (isFirst ? "(first time)" : "") + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (frmdatosanteriores) Resume **");
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
		return frmdatosanteriores.class;
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
            BA.LogInfo("** Activity (frmdatosanteriores) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
        else
            BA.LogInfo("** Activity (frmdatosanteriores) Pause event (activity is not paused). **");
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
            frmdatosanteriores mc = mostCurrent;
			if (mc == null || mc != activity.get())
				return;
			processBA.setActivityPaused(false);
            BA.LogInfo("** Activity (frmdatosanteriores) Resume **");
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
public anywheresoftware.b4a.objects.ButtonWrapper _btncerrar = null;
public anywheresoftware.b4a.objects.LabelWrapper _label6 = null;
public anywheresoftware.b4a.objects.LabelWrapper _label7 = null;
public anywheresoftware.b4a.objects.LabelWrapper _label8 = null;
public anywheresoftware.b4a.objects.LabelWrapper _label10 = null;
public anywheresoftware.b4a.objects.LabelWrapper _label12 = null;
public anywheresoftware.b4a.objects.LabelWrapper _label3 = null;
public anywheresoftware.b4a.objects.LabelWrapper _label2 = null;
public anywheresoftware.b4a.objects.LabelWrapper _label13 = null;
public static boolean _hayanteriores = false;
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
 //BA.debugLineNum = 33;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
 //BA.debugLineNum = 34;BA.debugLine="Activity.LoadLayout(\"layPerfilDatosAnteriores_Map";
mostCurrent._activity.LoadLayout("layPerfilDatosAnteriores_Mapa",mostCurrent.activityBA);
 //BA.debugLineNum = 35;BA.debugLine="hayAnteriores = False";
_hayanteriores = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 36;BA.debugLine="get_Datos_Online";
_get_datos_online();
 //BA.debugLineNum = 37;BA.debugLine="End Sub";
return "";
}
public static boolean  _activity_keypress(int _keycode) throws Exception{
 //BA.debugLineNum = 44;BA.debugLine="Sub Activity_KeyPress (KeyCode As Int) As Boolean";
 //BA.debugLineNum = 46;BA.debugLine="If KeyCode = KeyCodes.KEYCODE_BACK Then";
if (_keycode==anywheresoftware.b4a.keywords.Common.KeyCodes.KEYCODE_BACK) { 
 //BA.debugLineNum = 47;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 48;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 };
 //BA.debugLineNum = 50;BA.debugLine="End Sub";
return false;
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 41;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 43;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 38;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 40;BA.debugLine="End Sub";
return "";
}
public static String  _btncerrar_click() throws Exception{
 //BA.debugLineNum = 53;BA.debugLine="Sub btnCerrar_Click";
 //BA.debugLineNum = 54;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 55;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 56;BA.debugLine="End Sub";
return "";
}
public static String  _btnverdatosanterioresmapa_click() throws Exception{
 //BA.debugLineNum = 225;BA.debugLine="Private Sub btnVerDatosAnterioresMapa_Click";
 //BA.debugLineNum = 226;BA.debugLine="frmMapa.origen = \"datos_anteriores\"";
mostCurrent._frmmapa._origen /*String*/  = "datos_anteriores";
 //BA.debugLineNum = 227;BA.debugLine="StartActivity(frmMapa)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._frmmapa.getObject()));
 //BA.debugLineNum = 228;BA.debugLine="End Sub";
return "";
}
public static String  _cargar_datos_online() throws Exception{
anywheresoftware.b4a.objects.collections.List _listmaps = null;
int _r = 0;
anywheresoftware.b4a.objects.collections.Map _rmap = null;
String _latdato = "";
String _lngdato = "";
String _fechadato = "";
String _iddato = "";
String _partidodato = "";
 //BA.debugLineNum = 163;BA.debugLine="Sub cargar_datos_online";
 //BA.debugLineNum = 164;BA.debugLine="lstEnviosRealizados.Clear";
mostCurrent._lstenviosrealizados.Clear();
 //BA.debugLineNum = 165;BA.debugLine="lstEnviosRealizados.Color = Colors.White";
mostCurrent._lstenviosrealizados.setColor(anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 166;BA.debugLine="lstEnviosRealizados.TwoLinesAndBitmap.Label.Width";
mostCurrent._lstenviosrealizados.getTwoLinesAndBitmap().Label.setWidth((int) (mostCurrent._lstenviosrealizados.getWidth()-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (50))));
 //BA.debugLineNum = 167;BA.debugLine="lstEnviosRealizados.TwoLinesAndBitmap.SecondLabel";
mostCurrent._lstenviosrealizados.getTwoLinesAndBitmap().SecondLabel.setWidth((int) (mostCurrent._lstenviosrealizados.getWidth()-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (50))));
 //BA.debugLineNum = 168;BA.debugLine="lstEnviosRealizados.TwoLinesAndBitmap.Label.TextC";
mostCurrent._lstenviosrealizados.getTwoLinesAndBitmap().Label.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 169;BA.debugLine="lstEnviosRealizados.TwoLinesAndBitmap.SecondLabel";
mostCurrent._lstenviosrealizados.getTwoLinesAndBitmap().SecondLabel.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (150),(int) (0),(int) (0),(int) (0)));
 //BA.debugLineNum = 170;BA.debugLine="lstEnviosRealizados.TwoLinesAndBitmap.Label.TextS";
mostCurrent._lstenviosrealizados.getTwoLinesAndBitmap().Label.setTextSize((float) (14));
 //BA.debugLineNum = 171;BA.debugLine="lstEnviosRealizados.TwoLinesAndBitmap.SecondLabel";
mostCurrent._lstenviosrealizados.getTwoLinesAndBitmap().SecondLabel.setTextSize((float) (14));
 //BA.debugLineNum = 172;BA.debugLine="lstEnviosRealizados.TwoLinesAndBitmap.ImageView.L";
mostCurrent._lstenviosrealizados.getTwoLinesAndBitmap().ImageView.setLeft((int) (mostCurrent._lstenviosrealizados.getWidth()-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (50))));
 //BA.debugLineNum = 173;BA.debugLine="lstEnviosRealizados.TwoLinesAndBitmap.ImageView.G";
mostCurrent._lstenviosrealizados.getTwoLinesAndBitmap().ImageView.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.CENTER);
 //BA.debugLineNum = 174;BA.debugLine="lstEnviosRealizados.TwoLinesAndBitmap.ImageView.W";
mostCurrent._lstenviosrealizados.getTwoLinesAndBitmap().ImageView.setWidth(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (20)));
 //BA.debugLineNum = 175;BA.debugLine="lstEnviosRealizados.TwoLinesAndBitmap.ImageView.H";
mostCurrent._lstenviosrealizados.getTwoLinesAndBitmap().ImageView.setHeight(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (20)));
 //BA.debugLineNum = 176;BA.debugLine="lstEnviosRealizados.TwoLinesAndBitmap.ImageView.t";
mostCurrent._lstenviosrealizados.getTwoLinesAndBitmap().ImageView.setTop((int) ((mostCurrent._lstenviosrealizados.getTwoLinesAndBitmap().getItemHeight()/(double)2)-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10))));
 //BA.debugLineNum = 178;BA.debugLine="Dim listmaps As List";
_listmaps = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 179;BA.debugLine="listmaps.Initialize";
_listmaps.Initialize();
 //BA.debugLineNum = 180;BA.debugLine="listmaps = Main.user_markers_list";
_listmaps = mostCurrent._main._user_markers_list /*anywheresoftware.b4a.objects.collections.List*/ ;
 //BA.debugLineNum = 184;BA.debugLine="If listmaps = Null Or listmaps.IsInitialized = Fa";
if (_listmaps== null || _listmaps.IsInitialized()==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 185;BA.debugLine="hayAnteriores = False";
_hayanteriores = anywheresoftware.b4a.keywords.Common.False;
 }else {
 //BA.debugLineNum = 187;BA.debugLine="If listmaps.Size = 0 Then";
if (_listmaps.getSize()==0) { 
 //BA.debugLineNum = 189;BA.debugLine="hayAnteriores = False";
_hayanteriores = anywheresoftware.b4a.keywords.Common.False;
 }else {
 //BA.debugLineNum = 192;BA.debugLine="hayAnteriores = True";
_hayanteriores = anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 193;BA.debugLine="Try";
try { //BA.debugLineNum = 194;BA.debugLine="For r = 0 To listmaps.Size -1";
{
final int step25 = 1;
final int limit25 = (int) (_listmaps.getSize()-1);
_r = (int) (0) ;
for (;_r <= limit25 ;_r = _r + step25 ) {
 //BA.debugLineNum = 195;BA.debugLine="Dim rmap As Map";
_rmap = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 196;BA.debugLine="rmap = listmaps.Get(r)";
_rmap = (anywheresoftware.b4a.objects.collections.Map) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.collections.Map(), (java.util.Map)(_listmaps.Get(_r)));
 //BA.debugLineNum = 197;BA.debugLine="Dim latdato As String = rmap.Get(\"lat\")";
_latdato = BA.ObjectToString(_rmap.Get((Object)("lat")));
 //BA.debugLineNum = 198;BA.debugLine="Dim lngdato As String = rmap.Get(\"lng\")";
_lngdato = BA.ObjectToString(_rmap.Get((Object)("lng")));
 //BA.debugLineNum = 199;BA.debugLine="Dim fechadato As String = rmap.Get(\"dateandti";
_fechadato = BA.ObjectToString(_rmap.Get((Object)("dateandtime")));
 //BA.debugLineNum = 200;BA.debugLine="Dim iddato As String = rmap.Get(\"id\")";
_iddato = BA.ObjectToString(_rmap.Get((Object)("id")));
 //BA.debugLineNum = 201;BA.debugLine="Dim partidodato As String = rmap.Get(\"partido";
_partidodato = BA.ObjectToString(_rmap.Get((Object)("partido")));
 //BA.debugLineNum = 203;BA.debugLine="If fechadato = \"\" Or fechadato = \"null\" Then";
if ((_fechadato).equals("") || (_fechadato).equals("null")) { 
 //BA.debugLineNum = 204;BA.debugLine="fechadato = \"Sin fecha\"";
_fechadato = "Sin fecha";
 };
 //BA.debugLineNum = 206;BA.debugLine="If latdato = \"null\" Or latdato = \"\" Then";
if ((_latdato).equals("null") || (_latdato).equals("")) { 
 //BA.debugLineNum = 207;BA.debugLine="latdato = \"Sin latitud\"";
_latdato = "Sin latitud";
 };
 //BA.debugLineNum = 209;BA.debugLine="If lngdato = \"null\"  Or lngdato = \"\" Then";
if ((_lngdato).equals("null") || (_lngdato).equals("")) { 
 //BA.debugLineNum = 210;BA.debugLine="lngdato = \"Sin longitud\"";
_lngdato = "Sin longitud";
 };
 //BA.debugLineNum = 213;BA.debugLine="lstEnviosRealizados.AddTwoLinesAndBitmap2(\"HÁ";
mostCurrent._lstenviosrealizados.AddTwoLinesAndBitmap2(BA.ObjectToCharSequence("HÁBITAT - Fecha: "+_fechadato),BA.ObjectToCharSequence(_latdato+"/"+_lngdato+"- "+_partidodato),(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"check.png").getObject()),(Object)("enviado_0"));
 }
};
 } 
       catch (Exception e45) {
			processBA.setLastException(e45); //BA.debugLineNum = 216;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 217;BA.debugLine="ToastMessageShow(\"Ha habido un error cargando";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Ha habido un error cargando sus datos sin enviar, intente de nuevo!"),anywheresoftware.b4a.keywords.Common.False);
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 219;BA.debugLine="ToastMessageShow(\"There's been an error, plea";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("There's been an error, please try again!"),anywheresoftware.b4a.keywords.Common.False);
 };
 };
 };
 };
 //BA.debugLineNum = 224;BA.debugLine="End Sub";
return "";
}
public static String  _get_datos_online() throws Exception{
appear.pnud.preservamos.downloadservice._downloaddata _dd = null;
 //BA.debugLineNum = 66;BA.debugLine="Sub get_Datos_Online";
 //BA.debugLineNum = 67;BA.debugLine="If Main.hayinternet = True Then";
if (mostCurrent._main._hayinternet /*boolean*/ ==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 68;BA.debugLine="Log(\"Getting puntaje\")";
anywheresoftware.b4a.keywords.Common.LogImpl("441418754","Getting puntaje",0);
 //BA.debugLineNum = 69;BA.debugLine="ToastMessageShow(\"Buscando datos anteriores onli";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Buscando datos anteriores online..."),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 70;BA.debugLine="Dim dd As DownloadData";
_dd = new appear.pnud.preservamos.downloadservice._downloaddata();
 //BA.debugLineNum = 71;BA.debugLine="dd.url = Main.serverPath & \"/\" & Main.serverConn";
_dd.url /*String*/  = mostCurrent._main._serverpath /*String*/ +"/"+mostCurrent._main._serverconnectionfolder /*String*/ +"/getPuntaje.php?useremail="+mostCurrent._main._struseremail /*String*/ ;
 //BA.debugLineNum = 72;BA.debugLine="Log(\"Testing: \" & dd.url)";
anywheresoftware.b4a.keywords.Common.LogImpl("441418758","Testing: "+_dd.url /*String*/ ,0);
 //BA.debugLineNum = 73;BA.debugLine="dd.EventName = \"get_Datos_Online\"";
_dd.EventName /*String*/  = "get_Datos_Online";
 //BA.debugLineNum = 74;BA.debugLine="dd.Target = Me";
_dd.Target /*Object*/  = frmdatosanteriores.getObject();
 //BA.debugLineNum = 75;BA.debugLine="CallSubDelayed2(DownloadService, \"StartDownload\"";
anywheresoftware.b4a.keywords.Common.CallSubDelayed2(processBA,(Object)(mostCurrent._downloadservice.getObject()),"StartDownload",(Object)(_dd));
 };
 //BA.debugLineNum = 77;BA.debugLine="End Sub";
return "";
}
public static void  _get_datos_online_complete(appear.pnud.preservamos.httpjob _job) throws Exception{
ResumableSub_get_Datos_Online_Complete rsub = new ResumableSub_get_Datos_Online_Complete(null,_job);
rsub.resume(processBA, null);
}
public static class ResumableSub_get_Datos_Online_Complete extends BA.ResumableSub {
public ResumableSub_get_Datos_Online_Complete(appear.pnud.preservamos.frmdatosanteriores parent,appear.pnud.preservamos.httpjob _job) {
this.parent = parent;
this._job = _job;
}
appear.pnud.preservamos.frmdatosanteriores parent;
appear.pnud.preservamos.httpjob _job;
String _ret = "";
String _act = "";
anywheresoftware.b4a.objects.collections.Map _new_user_markers = null;
anywheresoftware.b4a.objects.collections.Map _new_evals_residuos = null;
anywheresoftware.b4a.objects.collections.Map _new_evals_hidro = null;
anywheresoftware.b4a.objects.collections.JSONParser _parser = null;
anywheresoftware.b4a.objects.collections.List _markerslist = null;
anywheresoftware.b4a.objects.collections.List _evals_residuoslist = null;
anywheresoftware.b4a.objects.collections.List _evals_hidrolist = null;
int _result = 0;

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
 //BA.debugLineNum = 79;BA.debugLine="Log(\"get_Datos_Online: \" & Job.Success)";
anywheresoftware.b4a.keywords.Common.LogImpl("441484289","get_Datos_Online: "+BA.ObjectToString(_job._success /*boolean*/ ),0);
 //BA.debugLineNum = 80;BA.debugLine="If Job.Success = True Then";
if (true) break;

case 1:
//if
this.state = 44;
if (_job._success /*boolean*/ ==anywheresoftware.b4a.keywords.Common.True) { 
this.state = 3;
}else {
this.state = 35;
}if (true) break;

case 3:
//C
this.state = 4;
 //BA.debugLineNum = 83;BA.debugLine="Dim ret As String";
_ret = "";
 //BA.debugLineNum = 84;BA.debugLine="Dim act As String";
_act = "";
 //BA.debugLineNum = 85;BA.debugLine="Dim new_user_markers As Map";
_new_user_markers = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 86;BA.debugLine="Dim new_evals_residuos As Map";
_new_evals_residuos = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 87;BA.debugLine="Dim new_evals_hidro As Map";
_new_evals_hidro = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 88;BA.debugLine="ret = Job.GetString";
_ret = _job._getstring /*String*/ ();
 //BA.debugLineNum = 89;BA.debugLine="Dim parser As JSONParser";
_parser = new anywheresoftware.b4a.objects.collections.JSONParser();
 //BA.debugLineNum = 90;BA.debugLine="parser.Initialize(ret)";
_parser.Initialize(_ret);
 //BA.debugLineNum = 91;BA.debugLine="act = parser.NextValue";
_act = BA.ObjectToString(_parser.NextValue());
 //BA.debugLineNum = 94;BA.debugLine="Main.puntostotales = 0";
parent.mostCurrent._main._puntostotales /*String*/  = BA.NumberToString(0);
 //BA.debugLineNum = 95;BA.debugLine="Main.puntos_markers = 0";
parent.mostCurrent._main._puntos_markers /*int*/  = (int) (0);
 //BA.debugLineNum = 96;BA.debugLine="Main.puntostotales = 0";
parent.mostCurrent._main._puntostotales /*String*/  = BA.NumberToString(0);
 //BA.debugLineNum = 97;BA.debugLine="Main.puntos_evals_hidro = 0";
parent.mostCurrent._main._puntos_evals_hidro /*int*/  = (int) (0);
 //BA.debugLineNum = 98;BA.debugLine="Main.puntos_evals_hidro_laguna = 0";
parent.mostCurrent._main._puntos_evals_hidro_laguna /*int*/  = (int) (0);
 //BA.debugLineNum = 99;BA.debugLine="Main.puntos_evals_hidro_llanura = 0";
parent.mostCurrent._main._puntos_evals_hidro_llanura /*int*/  = (int) (0);
 //BA.debugLineNum = 100;BA.debugLine="Main.puntos_evals_hidro_sierras = 0";
parent.mostCurrent._main._puntos_evals_hidro_sierras /*int*/  = (int) (0);
 //BA.debugLineNum = 101;BA.debugLine="Main.puntos_evals_residuos = 0";
parent.mostCurrent._main._puntos_evals_residuos /*int*/  = (int) (0);
 //BA.debugLineNum = 102;BA.debugLine="Main.puntos_evals_residuos_laguna = 0";
parent.mostCurrent._main._puntos_evals_residuos_laguna /*int*/  = (int) (0);
 //BA.debugLineNum = 103;BA.debugLine="Main.puntos_evals_residuos_llanura = 0";
parent.mostCurrent._main._puntos_evals_residuos_llanura /*int*/  = (int) (0);
 //BA.debugLineNum = 104;BA.debugLine="Main.puntos_evals_residuos_sierras = 0";
parent.mostCurrent._main._puntos_evals_residuos_sierras /*int*/  = (int) (0);
 //BA.debugLineNum = 105;BA.debugLine="Main.user_markers_list.Initialize";
parent.mostCurrent._main._user_markers_list /*anywheresoftware.b4a.objects.collections.List*/ .Initialize();
 //BA.debugLineNum = 106;BA.debugLine="Main.user_evals_hidro_list.Initialize";
parent.mostCurrent._main._user_evals_hidro_list /*anywheresoftware.b4a.objects.collections.List*/ .Initialize();
 //BA.debugLineNum = 107;BA.debugLine="Main.user_evals_residuos_list.Initialize";
parent.mostCurrent._main._user_evals_residuos_list /*anywheresoftware.b4a.objects.collections.List*/ .Initialize();
 //BA.debugLineNum = 109;BA.debugLine="If act = \"Not Found\" Then";
if (true) break;

case 4:
//if
this.state = 33;
if ((_act).equals("Not Found")) { 
this.state = 6;
}else if((_act).equals("GetPuntajeOk")) { 
this.state = 14;
}if (true) break;

case 6:
//C
this.state = 7;
 //BA.debugLineNum = 110;BA.debugLine="If Main.lang = \"es\" Then";
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
 //BA.debugLineNum = 111;BA.debugLine="ToastMessageShow(\"Error recuperando los puntos";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Error recuperando los puntos"),anywheresoftware.b4a.keywords.Common.False);
 if (true) break;

case 11:
//C
this.state = 12;
 //BA.debugLineNum = 113;BA.debugLine="ToastMessageShow(\"Error recovering points\", Fa";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Error recovering points"),anywheresoftware.b4a.keywords.Common.False);
 if (true) break;

case 12:
//C
this.state = 33;
;
 if (true) break;

case 14:
//C
this.state = 15;
 //BA.debugLineNum = 117;BA.debugLine="Try";
if (true) break;

case 15:
//try
this.state = 32;
this.catchState = 31;
this.state = 17;
if (true) break;

case 17:
//C
this.state = 18;
this.catchState = 31;
 //BA.debugLineNum = 118;BA.debugLine="Dim markersList As List";
_markerslist = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 119;BA.debugLine="markersList.Initialize()";
_markerslist.Initialize();
 //BA.debugLineNum = 120;BA.debugLine="markersList = parser.NextArray";
_markerslist = _parser.NextArray();
 //BA.debugLineNum = 121;BA.debugLine="Main.user_markers_list = markersList";
parent.mostCurrent._main._user_markers_list /*anywheresoftware.b4a.objects.collections.List*/  = _markerslist;
 //BA.debugLineNum = 122;BA.debugLine="If markersList.Size > 0 Then";
if (true) break;

case 18:
//if
this.state = 21;
if (_markerslist.getSize()>0) { 
this.state = 20;
}if (true) break;

case 20:
//C
this.state = 21;
 //BA.debugLineNum = 123;BA.debugLine="hayAnteriores = True";
parent._hayanteriores = anywheresoftware.b4a.keywords.Common.True;
 if (true) break;

case 21:
//C
this.state = 22;
;
 //BA.debugLineNum = 126;BA.debugLine="Dim evals_residuosList As List";
_evals_residuoslist = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 127;BA.debugLine="evals_residuosList.Initialize()";
_evals_residuoslist.Initialize();
 //BA.debugLineNum = 128;BA.debugLine="evals_residuosList = parser.NextArray";
_evals_residuoslist = _parser.NextArray();
 //BA.debugLineNum = 129;BA.debugLine="Main.user_evals_residuos_list = evals_residuos";
parent.mostCurrent._main._user_evals_residuos_list /*anywheresoftware.b4a.objects.collections.List*/  = _evals_residuoslist;
 //BA.debugLineNum = 130;BA.debugLine="If evals_residuosList.Size > 0 Then";
if (true) break;

case 22:
//if
this.state = 25;
if (_evals_residuoslist.getSize()>0) { 
this.state = 24;
}if (true) break;

case 24:
//C
this.state = 25;
 //BA.debugLineNum = 131;BA.debugLine="hayAnteriores = True";
parent._hayanteriores = anywheresoftware.b4a.keywords.Common.True;
 if (true) break;

case 25:
//C
this.state = 26;
;
 //BA.debugLineNum = 134;BA.debugLine="Dim evals_hidroList As List";
_evals_hidrolist = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 135;BA.debugLine="evals_hidroList.Initialize()";
_evals_hidrolist.Initialize();
 //BA.debugLineNum = 136;BA.debugLine="evals_hidroList = parser.NextArray";
_evals_hidrolist = _parser.NextArray();
 //BA.debugLineNum = 137;BA.debugLine="Main.user_evals_hidro_list = evals_hidroList";
parent.mostCurrent._main._user_evals_hidro_list /*anywheresoftware.b4a.objects.collections.List*/  = _evals_hidrolist;
 //BA.debugLineNum = 138;BA.debugLine="If evals_hidroList.Size > 0 Then";
if (true) break;

case 26:
//if
this.state = 29;
if (_evals_hidrolist.getSize()>0) { 
this.state = 28;
}if (true) break;

case 28:
//C
this.state = 29;
 //BA.debugLineNum = 139;BA.debugLine="hayAnteriores = True";
parent._hayanteriores = anywheresoftware.b4a.keywords.Common.True;
 if (true) break;

case 29:
//C
this.state = 32;
;
 //BA.debugLineNum = 142;BA.debugLine="cargar_datos_online";
_cargar_datos_online();
 if (true) break;

case 31:
//C
this.state = 32;
this.catchState = 0;
 //BA.debugLineNum = 144;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("441484354",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA)),0);
 //BA.debugLineNum = 145;BA.debugLine="Log(\"error en puntajes\")";
anywheresoftware.b4a.keywords.Common.LogImpl("441484355","error en puntajes",0);
 //BA.debugLineNum = 146;BA.debugLine="ToastMessageShow(\"No se pudo consultar el perf";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("No se pudo consultar el perfil del participante... intente de nuevo luego!"),anywheresoftware.b4a.keywords.Common.False);
 if (true) break;
if (true) break;

case 32:
//C
this.state = 33;
this.catchState = 0;
;
 if (true) break;

case 33:
//C
this.state = 44;
;
 if (true) break;

case 35:
//C
this.state = 36;
 //BA.debugLineNum = 152;BA.debugLine="If Main.lang = \"es\" Then";
if (true) break;

case 36:
//if
this.state = 43;
if ((parent.mostCurrent._main._lang /*String*/ ).equals("es")) { 
this.state = 38;
}if (true) break;

case 38:
//C
this.state = 39;
 //BA.debugLineNum = 153;BA.debugLine="Msgbox2Async(\"Para ver tus análisis enviados, n";
anywheresoftware.b4a.keywords.Common.Msgbox2Async(BA.ObjectToCharSequence("Para ver tus análisis enviados, necesitas tener conexión a internet"),BA.ObjectToCharSequence("No hay señal :("),"Ok","","",(anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper(), (android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null)),processBA,anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 154;BA.debugLine="Wait For Msgbox_Result (Result As Int)";
anywheresoftware.b4a.keywords.Common.WaitFor("msgbox_result", processBA, this, null);
this.state = 45;
return;
case 45:
//C
this.state = 39;
_result = (Integer) result[0];
;
 //BA.debugLineNum = 155;BA.debugLine="If Result = DialogResponse.POSITIVE Then";
if (true) break;

case 39:
//if
this.state = 42;
if (_result==anywheresoftware.b4a.keywords.Common.DialogResponse.POSITIVE) { 
this.state = 41;
}if (true) break;

case 41:
//C
this.state = 42;
 //BA.debugLineNum = 156;BA.debugLine="Activity.RemoveAllViews";
parent.mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 157;BA.debugLine="Activity.Finish";
parent.mostCurrent._activity.Finish();
 if (true) break;

case 42:
//C
this.state = 43;
;
 if (true) break;

case 43:
//C
this.state = 44;
;
 if (true) break;

case 44:
//C
this.state = -1;
;
 //BA.debugLineNum = 161;BA.debugLine="Job.Release";
_job._release /*String*/ ();
 //BA.debugLineNum = 162;BA.debugLine="End Sub";
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
public static void  _msgbox_result(int _result) throws Exception{
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 10;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 14;BA.debugLine="Private btnCerrar As Button";
mostCurrent._btncerrar = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 20;BA.debugLine="Private Label6 As Label";
mostCurrent._label6 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 21;BA.debugLine="Private Label7 As Label";
mostCurrent._label7 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 22;BA.debugLine="Private Label8 As Label";
mostCurrent._label8 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 23;BA.debugLine="Private Label10 As Label";
mostCurrent._label10 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 24;BA.debugLine="Private Label12 As Label";
mostCurrent._label12 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 25;BA.debugLine="Private Label3 As Label";
mostCurrent._label3 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 26;BA.debugLine="Private Label2 As Label";
mostCurrent._label2 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 27;BA.debugLine="Private Label13 As Label";
mostCurrent._label13 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 29;BA.debugLine="Dim hayAnteriores As Boolean";
_hayanteriores = false;
 //BA.debugLineNum = 30;BA.debugLine="Private lstEnviosRealizados As ListView";
mostCurrent._lstenviosrealizados = new anywheresoftware.b4a.objects.ListViewWrapper();
 //BA.debugLineNum = 31;BA.debugLine="End Sub";
return "";
}
public static String  _lstenviosrealizados_itemclick(int _position,Object _value) throws Exception{
 //BA.debugLineNum = 229;BA.debugLine="Private Sub lstEnviosRealizados_ItemClick (Positio";
 //BA.debugLineNum = 231;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 6;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 8;BA.debugLine="End Sub";
return "";
}
}
