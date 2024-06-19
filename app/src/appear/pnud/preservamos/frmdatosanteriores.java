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
public ResumableSub_Activity_Create(appear.pnud.preservamos.frmdatosanteriores parent,boolean _firsttime) {
this.parent = parent;
this._firsttime = _firsttime;
}
appear.pnud.preservamos.frmdatosanteriores parent;
boolean _firsttime;
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
 //BA.debugLineNum = 34;BA.debugLine="Activity.LoadLayout(\"layPerfilDatosAnteriores_Map";
parent.mostCurrent._activity.LoadLayout("layPerfilDatosAnteriores_Mapa",mostCurrent.activityBA);
 //BA.debugLineNum = 35;BA.debugLine="hayAnteriores = False";
parent._hayanteriores = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 37;BA.debugLine="Wait For (get_Datos_Online) Complete (Completed A";
anywheresoftware.b4a.keywords.Common.WaitFor("complete", processBA, this, _get_datos_online());
this.state = 7;
return;
case 7:
//C
this.state = 1;
_completed = (Boolean) result[0];
;
 //BA.debugLineNum = 39;BA.debugLine="If Completed = True Then";
if (true) break;

case 1:
//if
this.state = 6;
if (_completed==anywheresoftware.b4a.keywords.Common.True) { 
this.state = 3;
}else {
this.state = 5;
}if (true) break;

case 3:
//C
this.state = 6;
 //BA.debugLineNum = 40;BA.debugLine="cargar_datos_online";
_cargar_datos_online();
 if (true) break;

case 5:
//C
this.state = 6;
 //BA.debugLineNum = 42;BA.debugLine="MsgboxAsync(\"No se encuentran datos enviados ant";
anywheresoftware.b4a.keywords.Common.MsgboxAsync(BA.ObjectToCharSequence("No se encuentran datos enviados anteriormente"),BA.ObjectToCharSequence(":("),processBA);
 if (true) break;

case 6:
//C
this.state = -1;
;
 //BA.debugLineNum = 46;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public static void  _complete(boolean _completed) throws Exception{
}
public static boolean  _activity_keypress(int _keycode) throws Exception{
 //BA.debugLineNum = 53;BA.debugLine="Sub Activity_KeyPress (KeyCode As Int) As Boolean";
 //BA.debugLineNum = 55;BA.debugLine="If KeyCode = KeyCodes.KEYCODE_BACK Then";
if (_keycode==anywheresoftware.b4a.keywords.Common.KeyCodes.KEYCODE_BACK) { 
 //BA.debugLineNum = 56;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 57;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 };
 //BA.debugLineNum = 59;BA.debugLine="End Sub";
return false;
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 50;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 52;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 47;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 49;BA.debugLine="End Sub";
return "";
}
public static String  _btncerrar_click() throws Exception{
 //BA.debugLineNum = 62;BA.debugLine="Sub btnCerrar_Click";
 //BA.debugLineNum = 63;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 64;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 65;BA.debugLine="End Sub";
return "";
}
public static String  _btnverdatosanterioresmapa_click() throws Exception{
 //BA.debugLineNum = 233;BA.debugLine="Private Sub btnVerDatosAnterioresMapa_Click";
 //BA.debugLineNum = 234;BA.debugLine="frmMapa.origen = \"datos_anteriores\"";
mostCurrent._frmmapa._origen /*String*/  = "datos_anteriores";
 //BA.debugLineNum = 235;BA.debugLine="StartActivity(frmMapa)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._frmmapa.getObject()));
 //BA.debugLineNum = 236;BA.debugLine="End Sub";
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
 //BA.debugLineNum = 171;BA.debugLine="Sub cargar_datos_online";
 //BA.debugLineNum = 172;BA.debugLine="lstEnviosRealizados.Clear";
mostCurrent._lstenviosrealizados.Clear();
 //BA.debugLineNum = 173;BA.debugLine="lstEnviosRealizados.Color = Colors.White";
mostCurrent._lstenviosrealizados.setColor(anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 174;BA.debugLine="lstEnviosRealizados.TwoLinesAndBitmap.Label.Width";
mostCurrent._lstenviosrealizados.getTwoLinesAndBitmap().Label.setWidth((int) (mostCurrent._lstenviosrealizados.getWidth()-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (50))));
 //BA.debugLineNum = 175;BA.debugLine="lstEnviosRealizados.TwoLinesAndBitmap.SecondLabel";
mostCurrent._lstenviosrealizados.getTwoLinesAndBitmap().SecondLabel.setWidth((int) (mostCurrent._lstenviosrealizados.getWidth()-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (50))));
 //BA.debugLineNum = 176;BA.debugLine="lstEnviosRealizados.TwoLinesAndBitmap.Label.TextC";
mostCurrent._lstenviosrealizados.getTwoLinesAndBitmap().Label.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 177;BA.debugLine="lstEnviosRealizados.TwoLinesAndBitmap.SecondLabel";
mostCurrent._lstenviosrealizados.getTwoLinesAndBitmap().SecondLabel.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (150),(int) (0),(int) (0),(int) (0)));
 //BA.debugLineNum = 178;BA.debugLine="lstEnviosRealizados.TwoLinesAndBitmap.Label.TextS";
mostCurrent._lstenviosrealizados.getTwoLinesAndBitmap().Label.setTextSize((float) (14));
 //BA.debugLineNum = 179;BA.debugLine="lstEnviosRealizados.TwoLinesAndBitmap.SecondLabel";
mostCurrent._lstenviosrealizados.getTwoLinesAndBitmap().SecondLabel.setTextSize((float) (14));
 //BA.debugLineNum = 180;BA.debugLine="lstEnviosRealizados.TwoLinesAndBitmap.ImageView.L";
mostCurrent._lstenviosrealizados.getTwoLinesAndBitmap().ImageView.setLeft((int) (mostCurrent._lstenviosrealizados.getWidth()-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (50))));
 //BA.debugLineNum = 181;BA.debugLine="lstEnviosRealizados.TwoLinesAndBitmap.ImageView.G";
mostCurrent._lstenviosrealizados.getTwoLinesAndBitmap().ImageView.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.CENTER);
 //BA.debugLineNum = 182;BA.debugLine="lstEnviosRealizados.TwoLinesAndBitmap.ImageView.W";
mostCurrent._lstenviosrealizados.getTwoLinesAndBitmap().ImageView.setWidth(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (20)));
 //BA.debugLineNum = 183;BA.debugLine="lstEnviosRealizados.TwoLinesAndBitmap.ImageView.H";
mostCurrent._lstenviosrealizados.getTwoLinesAndBitmap().ImageView.setHeight(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (20)));
 //BA.debugLineNum = 184;BA.debugLine="lstEnviosRealizados.TwoLinesAndBitmap.ImageView.t";
mostCurrent._lstenviosrealizados.getTwoLinesAndBitmap().ImageView.setTop((int) ((mostCurrent._lstenviosrealizados.getTwoLinesAndBitmap().getItemHeight()/(double)2)-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10))));
 //BA.debugLineNum = 186;BA.debugLine="Dim listmaps As List";
_listmaps = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 187;BA.debugLine="listmaps.Initialize";
_listmaps.Initialize();
 //BA.debugLineNum = 188;BA.debugLine="listmaps = Main.user_markers_list";
_listmaps = mostCurrent._main._user_markers_list /*anywheresoftware.b4a.objects.collections.List*/ ;
 //BA.debugLineNum = 192;BA.debugLine="If listmaps = Null Or listmaps.IsInitialized = Fa";
if (_listmaps== null || _listmaps.IsInitialized()==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 193;BA.debugLine="hayAnteriores = False";
_hayanteriores = anywheresoftware.b4a.keywords.Common.False;
 }else {
 //BA.debugLineNum = 195;BA.debugLine="If listmaps.Size = 0 Then";
if (_listmaps.getSize()==0) { 
 //BA.debugLineNum = 197;BA.debugLine="hayAnteriores = False";
_hayanteriores = anywheresoftware.b4a.keywords.Common.False;
 }else {
 //BA.debugLineNum = 200;BA.debugLine="hayAnteriores = True";
_hayanteriores = anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 201;BA.debugLine="Try";
try { //BA.debugLineNum = 202;BA.debugLine="For r = 0 To listmaps.Size -1";
{
final int step25 = 1;
final int limit25 = (int) (_listmaps.getSize()-1);
_r = (int) (0) ;
for (;_r <= limit25 ;_r = _r + step25 ) {
 //BA.debugLineNum = 203;BA.debugLine="Dim rmap As Map";
_rmap = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 204;BA.debugLine="rmap = listmaps.Get(r)";
_rmap = (anywheresoftware.b4a.objects.collections.Map) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.collections.Map(), (java.util.Map)(_listmaps.Get(_r)));
 //BA.debugLineNum = 205;BA.debugLine="Dim latdato As String = rmap.Get(\"lat\")";
_latdato = BA.ObjectToString(_rmap.Get((Object)("lat")));
 //BA.debugLineNum = 206;BA.debugLine="Dim lngdato As String = rmap.Get(\"lng\")";
_lngdato = BA.ObjectToString(_rmap.Get((Object)("lng")));
 //BA.debugLineNum = 207;BA.debugLine="Dim fechadato As String = rmap.Get(\"dateandti";
_fechadato = BA.ObjectToString(_rmap.Get((Object)("dateandtime")));
 //BA.debugLineNum = 208;BA.debugLine="Dim iddato As String = rmap.Get(\"id\")";
_iddato = BA.ObjectToString(_rmap.Get((Object)("id")));
 //BA.debugLineNum = 209;BA.debugLine="Dim partidodato As String = rmap.Get(\"partido";
_partidodato = BA.ObjectToString(_rmap.Get((Object)("partido")));
 //BA.debugLineNum = 211;BA.debugLine="If fechadato = \"\" Or fechadato = \"null\" Then";
if ((_fechadato).equals("") || (_fechadato).equals("null")) { 
 //BA.debugLineNum = 212;BA.debugLine="fechadato = \"Sin fecha\"";
_fechadato = "Sin fecha";
 };
 //BA.debugLineNum = 214;BA.debugLine="If latdato = \"null\" Or latdato = \"\" Then";
if ((_latdato).equals("null") || (_latdato).equals("")) { 
 //BA.debugLineNum = 215;BA.debugLine="latdato = \"Sin latitud\"";
_latdato = "Sin latitud";
 };
 //BA.debugLineNum = 217;BA.debugLine="If lngdato = \"null\"  Or lngdato = \"\" Then";
if ((_lngdato).equals("null") || (_lngdato).equals("")) { 
 //BA.debugLineNum = 218;BA.debugLine="lngdato = \"Sin longitud\"";
_lngdato = "Sin longitud";
 };
 //BA.debugLineNum = 221;BA.debugLine="lstEnviosRealizados.AddTwoLinesAndBitmap2(\"HÁ";
mostCurrent._lstenviosrealizados.AddTwoLinesAndBitmap2(BA.ObjectToCharSequence("HÁBITAT - Fecha: "+_fechadato),BA.ObjectToCharSequence(_latdato+"/"+_lngdato+"- "+_partidodato),(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"check.png").getObject()),(Object)("enviado_0"));
 }
};
 } 
       catch (Exception e45) {
			processBA.setLastException(e45); //BA.debugLineNum = 224;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 225;BA.debugLine="ToastMessageShow(\"Ha habido un error cargando";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Ha habido un error cargando sus datos sin enviar, intente de nuevo!"),anywheresoftware.b4a.keywords.Common.False);
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 227;BA.debugLine="ToastMessageShow(\"There's been an error, plea";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("There's been an error, please try again!"),anywheresoftware.b4a.keywords.Common.False);
 };
 };
 };
 };
 //BA.debugLineNum = 232;BA.debugLine="End Sub";
return "";
}
public static anywheresoftware.b4a.keywords.Common.ResumableSubWrapper  _get_datos_online() throws Exception{
ResumableSub_get_Datos_Online rsub = new ResumableSub_get_Datos_Online(null);
rsub.resume(processBA, null);
return (anywheresoftware.b4a.keywords.Common.ResumableSubWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.keywords.Common.ResumableSubWrapper(), rsub);
}
public static class ResumableSub_get_Datos_Online extends BA.ResumableSub {
public ResumableSub_get_Datos_Online(appear.pnud.preservamos.frmdatosanteriores parent) {
this.parent = parent;
}
appear.pnud.preservamos.frmdatosanteriores parent;
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
 //BA.debugLineNum = 76;BA.debugLine="If Main.hayinternet = True Then";
if (true) break;

case 1:
//if
this.state = 40;
if (parent.mostCurrent._main._hayinternet /*boolean*/ ==anywheresoftware.b4a.keywords.Common.True) { 
this.state = 3;
}if (true) break;

case 3:
//C
this.state = 4;
 //BA.debugLineNum = 78;BA.debugLine="Log(\"Getting puntaje\")";
anywheresoftware.b4a.keywords.Common.LogImpl("032964611","Getting puntaje",0);
 //BA.debugLineNum = 79;BA.debugLine="ToastMessageShow(\"Buscando datos anteriores onli";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Buscando datos anteriores online..."),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 80;BA.debugLine="Dim j As HttpJob";
_j = new appear.pnud.preservamos.httpjob();
 //BA.debugLineNum = 81;BA.debugLine="j.Initialize(\"\", Me)";
_j._initialize /*String*/ (processBA,"",frmdatosanteriores.getObject());
 //BA.debugLineNum = 82;BA.debugLine="Dim loginPath As String =  Main.serverPath & \"/\"";
_loginpath = parent.mostCurrent._main._serverpath /*String*/ +"/"+parent.mostCurrent._main._serverconnectionfolder /*String*/ +"/getPuntaje.php?useremail="+parent.mostCurrent._main._struseremail /*String*/ ;
 //BA.debugLineNum = 83;BA.debugLine="j.Download(loginPath)";
_j._download /*String*/ (_loginpath);
 //BA.debugLineNum = 84;BA.debugLine="Wait For (j) JobDone(j As HttpJob)";
anywheresoftware.b4a.keywords.Common.WaitFor("jobdone", processBA, this, (Object)(_j));
this.state = 41;
return;
case 41:
//C
this.state = 4;
_j = (appear.pnud.preservamos.httpjob) result[0];
;
 //BA.debugLineNum = 86;BA.debugLine="If j.Success Then";
if (true) break;

case 4:
//if
this.state = 39;
if (_j._success /*boolean*/ ) { 
this.state = 6;
}else {
this.state = 38;
}if (true) break;

case 6:
//C
this.state = 7;
 //BA.debugLineNum = 89;BA.debugLine="Dim ret As String";
_ret = "";
 //BA.debugLineNum = 90;BA.debugLine="Dim act As String";
_act = "";
 //BA.debugLineNum = 91;BA.debugLine="Dim new_user_markers As Map";
_new_user_markers = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 92;BA.debugLine="Dim new_evals_residuos As Map";
_new_evals_residuos = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 93;BA.debugLine="Dim new_evals_hidro As Map";
_new_evals_hidro = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 94;BA.debugLine="ret = j.GetString";
_ret = _j._getstring /*String*/ ();
 //BA.debugLineNum = 95;BA.debugLine="Dim parser As JSONParser";
_parser = new anywheresoftware.b4a.objects.collections.JSONParser();
 //BA.debugLineNum = 96;BA.debugLine="parser.Initialize(ret)";
_parser.Initialize(_ret);
 //BA.debugLineNum = 97;BA.debugLine="act = parser.NextValue";
_act = BA.ObjectToString(_parser.NextValue());
 //BA.debugLineNum = 100;BA.debugLine="Main.puntostotales = 0";
parent.mostCurrent._main._puntostotales /*String*/  = BA.NumberToString(0);
 //BA.debugLineNum = 101;BA.debugLine="Main.puntos_markers = 0";
parent.mostCurrent._main._puntos_markers /*int*/  = (int) (0);
 //BA.debugLineNum = 102;BA.debugLine="Main.puntostotales = 0";
parent.mostCurrent._main._puntostotales /*String*/  = BA.NumberToString(0);
 //BA.debugLineNum = 103;BA.debugLine="Main.puntos_evals_hidro = 0";
parent.mostCurrent._main._puntos_evals_hidro /*int*/  = (int) (0);
 //BA.debugLineNum = 104;BA.debugLine="Main.puntos_evals_hidro_laguna = 0";
parent.mostCurrent._main._puntos_evals_hidro_laguna /*int*/  = (int) (0);
 //BA.debugLineNum = 105;BA.debugLine="Main.puntos_evals_hidro_llanura = 0";
parent.mostCurrent._main._puntos_evals_hidro_llanura /*int*/  = (int) (0);
 //BA.debugLineNum = 106;BA.debugLine="Main.puntos_evals_hidro_sierras = 0";
parent.mostCurrent._main._puntos_evals_hidro_sierras /*int*/  = (int) (0);
 //BA.debugLineNum = 107;BA.debugLine="Main.puntos_evals_residuos = 0";
parent.mostCurrent._main._puntos_evals_residuos /*int*/  = (int) (0);
 //BA.debugLineNum = 108;BA.debugLine="Main.puntos_evals_residuos_laguna = 0";
parent.mostCurrent._main._puntos_evals_residuos_laguna /*int*/  = (int) (0);
 //BA.debugLineNum = 109;BA.debugLine="Main.puntos_evals_residuos_llanura = 0";
parent.mostCurrent._main._puntos_evals_residuos_llanura /*int*/  = (int) (0);
 //BA.debugLineNum = 110;BA.debugLine="Main.puntos_evals_residuos_sierras = 0";
parent.mostCurrent._main._puntos_evals_residuos_sierras /*int*/  = (int) (0);
 //BA.debugLineNum = 111;BA.debugLine="Main.user_markers_list.Initialize";
parent.mostCurrent._main._user_markers_list /*anywheresoftware.b4a.objects.collections.List*/ .Initialize();
 //BA.debugLineNum = 112;BA.debugLine="Main.user_evals_hidro_list.Initialize";
parent.mostCurrent._main._user_evals_hidro_list /*anywheresoftware.b4a.objects.collections.List*/ .Initialize();
 //BA.debugLineNum = 113;BA.debugLine="Main.user_evals_residuos_list.Initialize";
parent.mostCurrent._main._user_evals_residuos_list /*anywheresoftware.b4a.objects.collections.List*/ .Initialize();
 //BA.debugLineNum = 115;BA.debugLine="If act = \"Not Found\" Then";
if (true) break;

case 7:
//if
this.state = 36;
if ((_act).equals("Not Found")) { 
this.state = 9;
}else if((_act).equals("GetPuntajeOk")) { 
this.state = 17;
}if (true) break;

case 9:
//C
this.state = 10;
 //BA.debugLineNum = 116;BA.debugLine="If Main.lang = \"es\" Then";
if (true) break;

case 10:
//if
this.state = 15;
if ((parent.mostCurrent._main._lang /*String*/ ).equals("es")) { 
this.state = 12;
}else if((parent.mostCurrent._main._lang /*String*/ ).equals("en")) { 
this.state = 14;
}if (true) break;

case 12:
//C
this.state = 15;
 //BA.debugLineNum = 117;BA.debugLine="ToastMessageShow(\"Error recuperando los punto";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Error recuperando los puntos"),anywheresoftware.b4a.keywords.Common.False);
 if (true) break;

case 14:
//C
this.state = 15;
 //BA.debugLineNum = 119;BA.debugLine="ToastMessageShow(\"Error recovering points\", F";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Error recovering points"),anywheresoftware.b4a.keywords.Common.False);
 if (true) break;

case 15:
//C
this.state = 36;
;
 //BA.debugLineNum = 121;BA.debugLine="j.Release";
_j._release /*String*/ ();
 //BA.debugLineNum = 122;BA.debugLine="Return False";
if (true) {
anywheresoftware.b4a.keywords.Common.ReturnFromResumableSub(this,(Object)(anywheresoftware.b4a.keywords.Common.False));return;};
 if (true) break;

case 17:
//C
this.state = 18;
 //BA.debugLineNum = 125;BA.debugLine="Try";
if (true) break;

case 18:
//try
this.state = 35;
this.catchState = 34;
this.state = 20;
if (true) break;

case 20:
//C
this.state = 21;
this.catchState = 34;
 //BA.debugLineNum = 126;BA.debugLine="Dim markersList As List";
_markerslist = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 127;BA.debugLine="markersList.Initialize()";
_markerslist.Initialize();
 //BA.debugLineNum = 128;BA.debugLine="markersList = parser.NextArray";
_markerslist = _parser.NextArray();
 //BA.debugLineNum = 129;BA.debugLine="Main.user_markers_list = markersList";
parent.mostCurrent._main._user_markers_list /*anywheresoftware.b4a.objects.collections.List*/  = _markerslist;
 //BA.debugLineNum = 130;BA.debugLine="If markersList.Size > 0 Then";
if (true) break;

case 21:
//if
this.state = 24;
if (_markerslist.getSize()>0) { 
this.state = 23;
}if (true) break;

case 23:
//C
this.state = 24;
 //BA.debugLineNum = 131;BA.debugLine="hayAnteriores = True";
parent._hayanteriores = anywheresoftware.b4a.keywords.Common.True;
 if (true) break;

case 24:
//C
this.state = 25;
;
 //BA.debugLineNum = 134;BA.debugLine="Dim evals_residuosList As List";
_evals_residuoslist = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 135;BA.debugLine="evals_residuosList.Initialize()";
_evals_residuoslist.Initialize();
 //BA.debugLineNum = 136;BA.debugLine="evals_residuosList = parser.NextArray";
_evals_residuoslist = _parser.NextArray();
 //BA.debugLineNum = 137;BA.debugLine="Main.user_evals_residuos_list = evals_residuo";
parent.mostCurrent._main._user_evals_residuos_list /*anywheresoftware.b4a.objects.collections.List*/  = _evals_residuoslist;
 //BA.debugLineNum = 138;BA.debugLine="If evals_residuosList.Size > 0 Then";
if (true) break;

case 25:
//if
this.state = 28;
if (_evals_residuoslist.getSize()>0) { 
this.state = 27;
}if (true) break;

case 27:
//C
this.state = 28;
 //BA.debugLineNum = 139;BA.debugLine="hayAnteriores = True";
parent._hayanteriores = anywheresoftware.b4a.keywords.Common.True;
 if (true) break;

case 28:
//C
this.state = 29;
;
 //BA.debugLineNum = 142;BA.debugLine="Dim evals_hidroList As List";
_evals_hidrolist = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 143;BA.debugLine="evals_hidroList.Initialize()";
_evals_hidrolist.Initialize();
 //BA.debugLineNum = 144;BA.debugLine="evals_hidroList = parser.NextArray";
_evals_hidrolist = _parser.NextArray();
 //BA.debugLineNum = 145;BA.debugLine="Main.user_evals_hidro_list = evals_hidroList";
parent.mostCurrent._main._user_evals_hidro_list /*anywheresoftware.b4a.objects.collections.List*/  = _evals_hidrolist;
 //BA.debugLineNum = 146;BA.debugLine="If evals_hidroList.Size > 0 Then";
if (true) break;

case 29:
//if
this.state = 32;
if (_evals_hidrolist.getSize()>0) { 
this.state = 31;
}if (true) break;

case 31:
//C
this.state = 32;
 //BA.debugLineNum = 147;BA.debugLine="hayAnteriores = True";
parent._hayanteriores = anywheresoftware.b4a.keywords.Common.True;
 if (true) break;

case 32:
//C
this.state = 35;
;
 //BA.debugLineNum = 151;BA.debugLine="j.Release";
_j._release /*String*/ ();
 //BA.debugLineNum = 152;BA.debugLine="Return True";
if (true) {
anywheresoftware.b4a.keywords.Common.ReturnFromResumableSub(this,(Object)(anywheresoftware.b4a.keywords.Common.True));return;};
 if (true) break;

case 34:
//C
this.state = 35;
this.catchState = 0;
 //BA.debugLineNum = 154;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("032964687",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA)),0);
 //BA.debugLineNum = 155;BA.debugLine="Log(\"error en puntajes\")";
anywheresoftware.b4a.keywords.Common.LogImpl("032964688","error en puntajes",0);
 //BA.debugLineNum = 156;BA.debugLine="ToastMessageShow(\"No se pudo consultar el per";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("No se pudo consultar el perfil del participante... intente de nuevo luego!"),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 158;BA.debugLine="j.Release";
_j._release /*String*/ ();
 //BA.debugLineNum = 159;BA.debugLine="Return False";
if (true) {
anywheresoftware.b4a.keywords.Common.ReturnFromResumableSub(this,(Object)(anywheresoftware.b4a.keywords.Common.False));return;};
 if (true) break;
if (true) break;

case 35:
//C
this.state = 36;
this.catchState = 0;
;
 if (true) break;

case 36:
//C
this.state = 39;
;
 if (true) break;

case 38:
//C
this.state = 39;
 //BA.debugLineNum = 164;BA.debugLine="j.Release";
_j._release /*String*/ ();
 //BA.debugLineNum = 165;BA.debugLine="Return False";
if (true) {
anywheresoftware.b4a.keywords.Common.ReturnFromResumableSub(this,(Object)(anywheresoftware.b4a.keywords.Common.False));return;};
 if (true) break;

case 39:
//C
this.state = 40;
;
 //BA.debugLineNum = 167;BA.debugLine="Return False";
if (true) {
anywheresoftware.b4a.keywords.Common.ReturnFromResumableSub(this,(Object)(anywheresoftware.b4a.keywords.Common.False));return;};
 if (true) break;

case 40:
//C
this.state = -1;
;
 //BA.debugLineNum = 169;BA.debugLine="End Sub";
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
 //BA.debugLineNum = 237;BA.debugLine="Private Sub lstEnviosRealizados_ItemClick (Positio";
 //BA.debugLineNum = 239;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 6;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 8;BA.debugLine="End Sub";
return "";
}
}
