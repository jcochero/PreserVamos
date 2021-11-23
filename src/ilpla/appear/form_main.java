package ilpla.appear;


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
			processBA = new BA(this.getApplicationContext(), null, null, "ilpla.appear", "ilpla.appear.form_main");
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
		activityBA = new BA(this, layout, processBA, "ilpla.appear", "ilpla.appear.form_main");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "ilpla.appear.form_main", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density, mostCurrent);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (form_main) Create, isFirst = " + isFirst + " **");
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
public static uk.co.martinpearman.b4a.fusedlocationprovider.FusedLocationProviderWrapper _fusedlocationprovider1 = null;
public static anywheresoftware.b4a.gps.LocationWrapper _lastlocation = null;
public static boolean _isguest = false;
public static String _fullidcurrentproject = "";
public static boolean _formmainloaded = false;
public anywheresoftware.b4a.phone.Phone _p = null;
public anywheresoftware.b4a.phone.Phone.PhoneIntents _pi = null;
public anywheresoftware.b4a.objects.ProgressBarWrapper _pgbnivel = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbllevel = null;
public anywheresoftware.b4a.objects.TabStripViewPager _tabstripmain = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblanalizar = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnreportar = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblcomofuncionaanalizar = null;
public anywheresoftware.b4a.objects.PanelWrapper _pnlmapa = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblconoce = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblrequiereinternet = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblcomofuncionaexplorar = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btndetectar = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbllat = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbllon = null;
public uk.co.martinpearman.b4a.osmdroid.views.MapView _mapview1 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnzoomall = null;
public uk.co.martinpearman.b4a.osmdroid.views.overlay.SimpleLocationOverlay _simplelocationoverlay1 = null;
public anywheresoftware.b4a.objects.collections.List _markerexport = null;
public anywheresoftware.b4a.objects.LabelWrapper _markerinfo = null;
public anywheresoftware.b4a.objects.LabelWrapper _fondoblanco = null;
public anywheresoftware.b4a.objects.LabelWrapper _detectandolabel = null;
public anywheresoftware.b4a.objects.collections.List _markerslist = null;
public uk.co.martinpearman.b4a.osmdroid.views.overlay.MarkerOverlay _markersoverlay = null;
public anywheresoftware.b4a.objects.LabelWrapper _label4 = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblmarkerinfo = null;
public static String _firstuse = "";
public anywheresoftware.b4a.objects.PanelWrapper _fondogris = null;
public static int _tutorialetapa = 0;
public anywheresoftware.b4a.objects.ButtonWrapper _btnmenu = null;
public ilpla.appear.b4xdrawer _drawer = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btncerrarsesion = null;
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
public anywheresoftware.b4a.objects.WebViewWrapper _webmunicipio = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnmasinfomunicipio = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imglogomunicipio = null;
public ilpla.appear.main _main = null;
public ilpla.appear.aprender_muestreo _aprender_muestreo = null;
public ilpla.appear.dbutils _dbutils = null;
public ilpla.appear.downloadservice _downloadservice = null;
public ilpla.appear.firebasemessaging _firebasemessaging = null;
public ilpla.appear.form_reporte _form_reporte = null;
public ilpla.appear.frmabout _frmabout = null;
public ilpla.appear.frmdatosanteriores _frmdatosanteriores = null;
public ilpla.appear.frmeditprofile _frmeditprofile = null;
public ilpla.appear.frmfelicitaciones _frmfelicitaciones = null;
public ilpla.appear.frmlocalizacion _frmlocalizacion = null;
public ilpla.appear.frmlogin _frmlogin = null;
public ilpla.appear.frmperfil _frmperfil = null;
public ilpla.appear.frmpoliticadatos _frmpoliticadatos = null;
public ilpla.appear.httputils2service _httputils2service = null;
public ilpla.appear.register _register = null;
public ilpla.appear.reporte_envio _reporte_envio = null;
public ilpla.appear.reporte_fotos _reporte_fotos = null;
public ilpla.appear.reporte_habitat_laguna _reporte_habitat_laguna = null;
public ilpla.appear.reporte_habitat_rio _reporte_habitat_rio = null;
public ilpla.appear.reporte_habitat_rio_appear _reporte_habitat_rio_appear = null;
public ilpla.appear.starter _starter = null;
public ilpla.appear.uploadfiles _uploadfiles = null;
public ilpla.appear.utilidades _utilidades = null;

public static void initializeProcessGlobals() {
             try {
                Class.forName(BA.applicationContext.getPackageName() + ".main").getMethod("initializeProcessGlobals").invoke(null, null);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
}
public static String  _activity_create(boolean _firsttime) throws Exception{
 //BA.debugLineNum = 100;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
 //BA.debugLineNum = 102;BA.debugLine="If FirstTime Then";
if (_firsttime) { 
 //BA.debugLineNum = 103;BA.debugLine="FusedLocationProvider1.Initialize(\"FusedLocation";
_fusedlocationprovider1.Initialize(processBA,"FusedLocationProvider1");
 };
 //BA.debugLineNum = 106;BA.debugLine="p.SetScreenOrientation(1)";
mostCurrent._p.SetScreenOrientation(processBA,(int) (1));
 //BA.debugLineNum = 127;BA.debugLine="LoadForm_Main";
_loadform_main();
 //BA.debugLineNum = 129;BA.debugLine="End Sub";
return "";
}
public static boolean  _activity_keypress(int _keycode) throws Exception{
 //BA.debugLineNum = 145;BA.debugLine="Sub Activity_KeyPress (KeyCode As Int) As Boolean";
 //BA.debugLineNum = 146;BA.debugLine="If KeyCode = KeyCodes.KEYCODE_BACK Then";
if (_keycode==anywheresoftware.b4a.keywords.Common.KeyCodes.KEYCODE_BACK) { 
 //BA.debugLineNum = 147;BA.debugLine="closeAppMsgBox";
_closeappmsgbox();
 //BA.debugLineNum = 148;BA.debugLine="Return True";
if (true) return anywheresoftware.b4a.keywords.Common.True;
 }else {
 //BA.debugLineNum = 150;BA.debugLine="Return False";
if (true) return anywheresoftware.b4a.keywords.Common.False;
 };
 //BA.debugLineNum = 152;BA.debugLine="End Sub";
return false;
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 142;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 144;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 130;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 131;BA.debugLine="If Main.username = \"guest\" Or Main.username = \"No";
if ((mostCurrent._main._username /*String*/ ).equals("guest") || (mostCurrent._main._username /*String*/ ).equals("None")) { 
 //BA.debugLineNum = 133;BA.debugLine="StartActivity(register)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._register.getObject()));
 }else {
 //BA.debugLineNum = 135;BA.debugLine="lblUserName.Text = Main.username";
mostCurrent._lblusername.setText(BA.ObjectToCharSequence(mostCurrent._main._username /*String*/ ));
 //BA.debugLineNum = 136;BA.debugLine="lblUserName.Visible = True";
mostCurrent._lblusername.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 137;BA.debugLine="btnCerrarSesion.Text = \"Cerrar sesión\"";
mostCurrent._btncerrarsesion.setText(BA.ObjectToCharSequence("Cerrar sesión"));
 //BA.debugLineNum = 138;BA.debugLine="lblRegistrate.Visible = False";
mostCurrent._lblregistrate.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 139;BA.debugLine="btnEditUser.Visible = True";
mostCurrent._btnedituser.setVisible(anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 141;BA.debugLine="End Sub";
return "";
}
public static String  _btnabout_click() throws Exception{
 //BA.debugLineNum = 227;BA.debugLine="Sub btnAbout_Click";
 //BA.debugLineNum = 228;BA.debugLine="StartActivity(frmAbout)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._frmabout.getObject()));
 //BA.debugLineNum = 229;BA.debugLine="End Sub";
return "";
}
public static void  _btncerrarsesion_click() throws Exception{
ResumableSub_btnCerrarSesion_Click rsub = new ResumableSub_btnCerrarSesion_Click(null);
rsub.resume(processBA, null);
}
public static class ResumableSub_btnCerrarSesion_Click extends BA.ResumableSub {
public ResumableSub_btnCerrarSesion_Click(ilpla.appear.form_main parent) {
this.parent = parent;
}
ilpla.appear.form_main parent;
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
 //BA.debugLineNum = 211;BA.debugLine="Msgbox2Async(\"Desea cerrar la sesión? Ingresar co";
anywheresoftware.b4a.keywords.Common.Msgbox2Async(BA.ObjectToCharSequence("Desea cerrar la sesión? Ingresar con otro usuario requiere de internet!"),BA.ObjectToCharSequence("Seguro?"),"Si, tengo internet","","No, no tengo internet ahora",(anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper(), (android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null)),processBA,anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 212;BA.debugLine="Wait For Msgbox_Result (Result As Int)";
anywheresoftware.b4a.keywords.Common.WaitFor("msgbox_result", processBA, this, null);
this.state = 5;
return;
case 5:
//C
this.state = 1;
_result = (Integer) result[0];
;
 //BA.debugLineNum = 213;BA.debugLine="If Result = DialogResponse.POSITIVE Then";
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
 //BA.debugLineNum = 214;BA.debugLine="Main.strUserID = \"\"";
parent.mostCurrent._main._struserid /*String*/  = "";
 //BA.debugLineNum = 215;BA.debugLine="Main.strUserName = \"\"";
parent.mostCurrent._main._strusername /*String*/  = "";
 //BA.debugLineNum = 216;BA.debugLine="Main.strUserLocation = \"\"";
parent.mostCurrent._main._struserlocation /*String*/  = "";
 //BA.debugLineNum = 217;BA.debugLine="Main.strUserEmail = \"\"";
parent.mostCurrent._main._struseremail /*String*/  = "";
 //BA.debugLineNum = 218;BA.debugLine="Main.strUserOrg = \"\"";
parent.mostCurrent._main._struserorg /*String*/  = "";
 //BA.debugLineNum = 219;BA.debugLine="Main.username = \"\"";
parent.mostCurrent._main._username /*String*/  = "";
 //BA.debugLineNum = 220;BA.debugLine="Activity.RemoveAllViews";
parent.mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 221;BA.debugLine="Activity.finish";
parent.mostCurrent._activity.Finish();
 //BA.debugLineNum = 223;BA.debugLine="CallSubDelayed(frmLogin, \"SignOutGoogle\")";
anywheresoftware.b4a.keywords.Common.CallSubDelayed(processBA,(Object)(parent.mostCurrent._frmlogin.getObject()),"SignOutGoogle");
 //BA.debugLineNum = 224;BA.debugLine="StartActivity(frmLogin)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(parent.mostCurrent._frmlogin.getObject()));
 if (true) break;

case 4:
//C
this.state = -1;
;
 //BA.debugLineNum = 226;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public static void  _msgbox_result(int _result) throws Exception{
}
public static String  _btndatosanteriores_click() throws Exception{
 //BA.debugLineNum = 248;BA.debugLine="Sub btnDatosAnteriores_Click";
 //BA.debugLineNum = 249;BA.debugLine="If Main.modooffline = False Then";
if (mostCurrent._main._modooffline /*boolean*/ ==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 250;BA.debugLine="StartActivity(frmDatosAnteriores)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._frmdatosanteriores.getObject()));
 }else {
 //BA.debugLineNum = 252;BA.debugLine="ToastMessageShow(\"No ha iniciado sesión aún\", Fal";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("No ha iniciado sesión aún"),anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 254;BA.debugLine="End Sub";
return "";
}
public static String  _btndetectar_click() throws Exception{
 //BA.debugLineNum = 475;BA.debugLine="Sub btnDetectar_Click";
 //BA.debugLineNum = 476;BA.debugLine="DetectarPosicion";
_detectarposicion();
 //BA.debugLineNum = 477;BA.debugLine="End Sub";
return "";
}
public static String  _btnedituser_click() throws Exception{
 //BA.debugLineNum = 230;BA.debugLine="Sub btnEditUser_Click";
 //BA.debugLineNum = 231;BA.debugLine="If Main.username = \"guest\" Or Main.username = \"No";
if ((mostCurrent._main._username /*String*/ ).equals("guest") || (mostCurrent._main._username /*String*/ ).equals("None")) { 
 //BA.debugLineNum = 232;BA.debugLine="ToastMessageShow(\"Necesita estar registrado para";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Necesita estar registrado para ver su perfil"),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 233;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 235;BA.debugLine="If Main.modooffline = False Then";
if (mostCurrent._main._modooffline /*boolean*/ ==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 236;BA.debugLine="StartActivity(frmEditProfile)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._frmeditprofile.getObject()));
 }else {
 //BA.debugLineNum = 238;BA.debugLine="ToastMessageShow(\"Necesita tener internet para e";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Necesita tener internet para editar su perfil"),anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 241;BA.debugLine="End Sub";
return "";
}
public static String  _btnmasinfomunicipio_click() throws Exception{
 //BA.debugLineNum = 845;BA.debugLine="Private Sub btnMasInfoMunicipio_Click";
 //BA.debugLineNum = 846;BA.debugLine="StartActivity(pi.OpenBrowser(\"https://www.faceboo";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._pi.OpenBrowser("https://www.facebook.com/appeararg/")));
 //BA.debugLineNum = 847;BA.debugLine="End Sub";
return "";
}
public static String  _btnmenu_click() throws Exception{
 //BA.debugLineNum = 189;BA.debugLine="Sub btnMenu_Click";
 //BA.debugLineNum = 190;BA.debugLine="Drawer.LeftOpen = True";
mostCurrent._drawer._setleftopen /*boolean*/ (anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 191;BA.debugLine="If Main.username = \"guest\" Or Main.username = \"No";
if ((mostCurrent._main._username /*String*/ ).equals("guest") || (mostCurrent._main._username /*String*/ ).equals("None")) { 
 //BA.debugLineNum = 192;BA.debugLine="btnCerrarSesion.Text = \"Registrarse!\"";
mostCurrent._btncerrarsesion.setText(BA.ObjectToCharSequence("Registrarse!"));
 }else {
 //BA.debugLineNum = 194;BA.debugLine="btnCerrarSesion.Visible = False";
mostCurrent._btncerrarsesion.setVisible(anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 197;BA.debugLine="End Sub";
return "";
}
public static String  _btnmuestreos_click() throws Exception{
 //BA.debugLineNum = 242;BA.debugLine="Sub btnMuestreos_Click";
 //BA.debugLineNum = 243;BA.debugLine="StartActivity(Aprender_Muestreo)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._aprender_muestreo.getObject()));
 //BA.debugLineNum = 244;BA.debugLine="End Sub";
return "";
}
public static String  _btnpoliticadatos_click() throws Exception{
 //BA.debugLineNum = 245;BA.debugLine="Sub btnPoliticaDatos_Click";
 //BA.debugLineNum = 246;BA.debugLine="StartActivity(frmPoliticaDatos)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._frmpoliticadatos.getObject()));
 //BA.debugLineNum = 247;BA.debugLine="End Sub";
return "";
}
public static String  _btnreportar_click() throws Exception{
 //BA.debugLineNum = 405;BA.debugLine="Sub btnReportar_Click";
 //BA.debugLineNum = 408;BA.debugLine="If Main.username = \"guest\" Or Main.username = \"No";
if ((mostCurrent._main._username /*String*/ ).equals("guest") || (mostCurrent._main._username /*String*/ ).equals("None") || (mostCurrent._main._username /*String*/ ).equals("")) { 
 //BA.debugLineNum = 409;BA.debugLine="ToastMessageShow(\"Necesita estar registrado para";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Necesita estar registrado para hacer un reporte"),anywheresoftware.b4a.keywords.Common.False);
 }else {
 //BA.debugLineNum = 423;BA.debugLine="Form_Reporte.origen = \"Form_Main\"";
mostCurrent._form_reporte._origen /*String*/  = "Form_Main";
 //BA.debugLineNum = 424;BA.debugLine="StartActivity(Form_Reporte)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._form_reporte.getObject()));
 };
 //BA.debugLineNum = 427;BA.debugLine="End Sub";
return "";
}
public static String  _btnverperfil_click() throws Exception{
 //BA.debugLineNum = 198;BA.debugLine="Sub btnVerPerfil_Click";
 //BA.debugLineNum = 199;BA.debugLine="If Main.username = \"guest\" Or Main.username = \"No";
if ((mostCurrent._main._username /*String*/ ).equals("guest") || (mostCurrent._main._username /*String*/ ).equals("None") || (mostCurrent._main._username /*String*/ ).equals("")) { 
 //BA.debugLineNum = 200;BA.debugLine="ToastMessageShow(\"Necesita estar registrado para";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Necesita estar registrado para ver su perfil"),anywheresoftware.b4a.keywords.Common.False);
 }else {
 //BA.debugLineNum = 202;BA.debugLine="If Main.modooffline = False Then";
if (mostCurrent._main._modooffline /*boolean*/ ==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 203;BA.debugLine="StartActivity(frmEditProfile)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._frmeditprofile.getObject()));
 }else {
 //BA.debugLineNum = 205;BA.debugLine="ToastMessageShow(\"Necesita tener internet para";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Necesita tener internet para editar su perfil"),anywheresoftware.b4a.keywords.Common.False);
 };
 };
 //BA.debugLineNum = 208;BA.debugLine="End Sub";
return "";
}
public static String  _btnzoomall_click() throws Exception{
 //BA.debugLineNum = 837;BA.debugLine="Sub btnZoomAll_Click";
 //BA.debugLineNum = 838;BA.debugLine="MapView1.GetController.SetZoom(5)";
mostCurrent._mapview1.GetController().SetZoom((int) (5));
 //BA.debugLineNum = 839;BA.debugLine="End Sub";
return "";
}
public static String  _cargarmapa() throws Exception{
uk.co.martinpearman.b4a.osmdroid.util.GeoPoint _geopoint1 = null;
 //BA.debugLineNum = 443;BA.debugLine="Sub CargarMapa";
 //BA.debugLineNum = 445;BA.debugLine="If LastLocation.IsInitialized Then";
if (_lastlocation.IsInitialized()) { 
 //BA.debugLineNum = 446;BA.debugLine="lblLat.Text = LastLocation.Latitude";
mostCurrent._lbllat.setText(BA.ObjectToCharSequence(_lastlocation.getLatitude()));
 //BA.debugLineNum = 447;BA.debugLine="lblLon.Text = LastLocation.Longitude";
mostCurrent._lbllon.setText(BA.ObjectToCharSequence(_lastlocation.getLongitude()));
 }else {
 //BA.debugLineNum = 450;BA.debugLine="lblLat.Text = \"-34.9204950\"";
mostCurrent._lbllat.setText(BA.ObjectToCharSequence("-34.9204950"));
 //BA.debugLineNum = 451;BA.debugLine="lblLon.Text = \"-57.9535660\"";
mostCurrent._lbllon.setText(BA.ObjectToCharSequence("-57.9535660"));
 };
 //BA.debugLineNum = 453;BA.debugLine="MapView1.Initialize(\"MapView1\")";
mostCurrent._mapview1.Initialize(mostCurrent.activityBA,"MapView1");
 //BA.debugLineNum = 454;BA.debugLine="MapView1.SetBuiltInZoomControls(True)";
mostCurrent._mapview1.SetBuiltInZoomControls(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 455;BA.debugLine="MapView1.SetMultiTouchControls(True)";
mostCurrent._mapview1.SetMultiTouchControls(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 457;BA.debugLine="Dim geopoint1 As OSMDroid_GeoPoint";
_geopoint1 = new uk.co.martinpearman.b4a.osmdroid.util.GeoPoint();
 //BA.debugLineNum = 458;BA.debugLine="geopoint1.Initialize(lblLat.Text,lblLon.Text)";
_geopoint1.Initialize((double)(Double.parseDouble(mostCurrent._lbllat.getText())),(double)(Double.parseDouble(mostCurrent._lbllon.getText())));
 //BA.debugLineNum = 459;BA.debugLine="MapView1.GetController.SetCenter(geopoint1)";
mostCurrent._mapview1.GetController().SetCenter((org.osmdroid.util.GeoPoint)(_geopoint1.getObject()));
 //BA.debugLineNum = 460;BA.debugLine="MapView1.GetController.SetZoom(6)";
mostCurrent._mapview1.GetController().SetZoom((int) (6));
 //BA.debugLineNum = 462;BA.debugLine="pnlMapa.RemoveAllViews";
mostCurrent._pnlmapa.RemoveAllViews();
 //BA.debugLineNum = 463;BA.debugLine="pnlMapa.AddView(MapView1, 0,0, 100%x, 100%y)";
mostCurrent._pnlmapa.AddView((android.view.View)(mostCurrent._mapview1.getObject()),(int) (0),(int) (0),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA));
 //BA.debugLineNum = 464;BA.debugLine="If SimpleLocationOverlay1.IsInitialized = False T";
if (mostCurrent._simplelocationoverlay1.IsInitialized()==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 465;BA.debugLine="SimpleLocationOverlay1.Initialize()";
mostCurrent._simplelocationoverlay1.Initialize(processBA);
 //BA.debugLineNum = 466;BA.debugLine="MapView1.GetOverlays.Add(SimpleLocationOverlay1)";
mostCurrent._mapview1.GetOverlays().Add((org.osmdroid.views.overlay.Overlay)(mostCurrent._simplelocationoverlay1.getObject()));
 //BA.debugLineNum = 467;BA.debugLine="SimpleLocationOverlay1.SetLocation(geopoint1)";
mostCurrent._simplelocationoverlay1.SetLocation((org.osmdroid.util.GeoPoint)(_geopoint1.getObject()));
 }else {
 //BA.debugLineNum = 469;BA.debugLine="MapView1.GetOverlays.Add(SimpleLocationOverlay1)";
mostCurrent._mapview1.GetOverlays().Add((org.osmdroid.views.overlay.Overlay)(mostCurrent._simplelocationoverlay1.getObject()));
 //BA.debugLineNum = 470;BA.debugLine="SimpleLocationOverlay1.SetLocation(geopoint1)";
mostCurrent._simplelocationoverlay1.SetLocation((org.osmdroid.util.GeoPoint)(_geopoint1.getObject()));
 };
 //BA.debugLineNum = 472;BA.debugLine="GetMiMapa";
_getmimapa();
 //BA.debugLineNum = 473;BA.debugLine="End Sub";
return "";
}
public static void  _closeappmsgbox() throws Exception{
ResumableSub_closeAppMsgBox rsub = new ResumableSub_closeAppMsgBox(null);
rsub.resume(processBA, null);
}
public static class ResumableSub_closeAppMsgBox extends BA.ResumableSub {
public ResumableSub_closeAppMsgBox(ilpla.appear.form_main parent) {
this.parent = parent;
}
ilpla.appear.form_main parent;
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
 //BA.debugLineNum = 155;BA.debugLine="Msgbox2Async(\"Cerrar PreserVamos?\", \"SALIR\", \"Si\"";
anywheresoftware.b4a.keywords.Common.Msgbox2Async(BA.ObjectToCharSequence("Cerrar PreserVamos?"),BA.ObjectToCharSequence("SALIR"),"Si","","No",(anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper(), (android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null)),processBA,anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 156;BA.debugLine="Wait For Msgbox_Result (Result As Int)";
anywheresoftware.b4a.keywords.Common.WaitFor("msgbox_result", processBA, this, null);
this.state = 5;
return;
case 5:
//C
this.state = 1;
_result = (Integer) result[0];
;
 //BA.debugLineNum = 157;BA.debugLine="If Result = DialogResponse.POSITIVE Then";
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
 //BA.debugLineNum = 158;BA.debugLine="Activity.RemoveAllViews";
parent.mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 159;BA.debugLine="Activity.finish";
parent.mostCurrent._activity.Finish();
 //BA.debugLineNum = 160;BA.debugLine="ExitApplication";
anywheresoftware.b4a.keywords.Common.ExitApplication();
 if (true) break;

case 4:
//C
this.state = -1;
;
 //BA.debugLineNum = 162;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public static String  _crearmenu() throws Exception{
 //BA.debugLineNum = 170;BA.debugLine="Sub CrearMenu";
 //BA.debugLineNum = 172;BA.debugLine="Drawer.Initialize(Me, \"Drawer\", Activity, 300dip)";
mostCurrent._drawer._initialize /*String*/ (mostCurrent.activityBA,form_main.getObject(),"Drawer",(anywheresoftware.b4a.objects.B4XViewWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.B4XViewWrapper(), (java.lang.Object)(mostCurrent._activity.getObject())),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (300)));
 //BA.debugLineNum = 173;BA.debugLine="Drawer.CenterPanel.LoadLayout(\"layMain\")";
mostCurrent._drawer._getcenterpanel /*anywheresoftware.b4a.objects.B4XViewWrapper*/ ().LoadLayout("layMain",mostCurrent.activityBA);
 //BA.debugLineNum = 174;BA.debugLine="Drawer.LeftPanel.LoadLayout(\"frmSideNav\")";
mostCurrent._drawer._getleftpanel /*anywheresoftware.b4a.objects.B4XViewWrapper*/ ().LoadLayout("frmSideNav",mostCurrent.activityBA);
 //BA.debugLineNum = 176;BA.debugLine="If Main.username = \"guest\" Or Main.username = \"No";
if ((mostCurrent._main._username /*String*/ ).equals("guest") || (mostCurrent._main._username /*String*/ ).equals("None")) { 
 //BA.debugLineNum = 177;BA.debugLine="btnCerrarSesion.Text = \"Iniciar sesión\"";
mostCurrent._btncerrarsesion.setText(BA.ObjectToCharSequence("Iniciar sesión"));
 //BA.debugLineNum = 178;BA.debugLine="lblUserName.Visible = False";
mostCurrent._lblusername.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 179;BA.debugLine="lblRegistrate.Visible = True";
mostCurrent._lblregistrate.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 180;BA.debugLine="btnEditUser.Visible = False";
mostCurrent._btnedituser.setVisible(anywheresoftware.b4a.keywords.Common.False);
 }else {
 //BA.debugLineNum = 182;BA.debugLine="lblUserName.Text = Main.username";
mostCurrent._lblusername.setText(BA.ObjectToCharSequence(mostCurrent._main._username /*String*/ ));
 //BA.debugLineNum = 183;BA.debugLine="btnCerrarSesion.Text = \"Cerrar sesión\"";
mostCurrent._btncerrarsesion.setText(BA.ObjectToCharSequence("Cerrar sesión"));
 //BA.debugLineNum = 184;BA.debugLine="lblRegistrate.Visible = False";
mostCurrent._lblregistrate.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 185;BA.debugLine="btnEditUser.Visible = True";
mostCurrent._btnedituser.setVisible(anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 187;BA.debugLine="End Sub";
return "";
}
public static String  _detectarposicion() throws Exception{
 //BA.debugLineNum = 479;BA.debugLine="Sub DetectarPosicion";
 //BA.debugLineNum = 482;BA.debugLine="fondoblanco.Initialize(\"fondoblanco\")";
mostCurrent._fondoblanco.Initialize(mostCurrent.activityBA,"fondoblanco");
 //BA.debugLineNum = 483;BA.debugLine="detectandoLabel.Initialize(\"\")";
mostCurrent._detectandolabel.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 484;BA.debugLine="detectandoLabel.TextColor = Colors.White";
mostCurrent._detectandolabel.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 485;BA.debugLine="detectandoLabel.Gravity = Gravity.CENTER_HORIZONT";
mostCurrent._detectandolabel.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.CENTER_HORIZONTAL);
 //BA.debugLineNum = 487;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 488;BA.debugLine="detectandoLabel.Text = \"Buscando tu posición aut";
mostCurrent._detectandolabel.setText(BA.ObjectToCharSequence("Buscando tu posición automáticamente..."));
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 490;BA.debugLine="detectandoLabel.Text = \"Looking for your positio";
mostCurrent._detectandolabel.setText(BA.ObjectToCharSequence("Looking for your position automatically..."));
 };
 //BA.debugLineNum = 494;BA.debugLine="fondoblanco.Color = Colors.ARGB(150, 64,64,64)";
mostCurrent._fondoblanco.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (150),(int) (64),(int) (64),(int) (64)));
 //BA.debugLineNum = 495;BA.debugLine="Activity.AddView(fondoblanco, 0,0, 100%x, 100%y)";
mostCurrent._activity.AddView((android.view.View)(mostCurrent._fondoblanco.getObject()),(int) (0),(int) (0),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA));
 //BA.debugLineNum = 496;BA.debugLine="Activity.AddView(detectandoLabel, 5%x, 55%y, 80%x";
mostCurrent._activity.AddView((android.view.View)(mostCurrent._detectandolabel.getObject()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (5),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (55),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (80),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (10),mostCurrent.activityBA));
 //BA.debugLineNum = 497;BA.debugLine="btnDetectar.Enabled = False";
mostCurrent._btndetectar.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 498;BA.debugLine="btnDetectar.Color = Colors.Black";
mostCurrent._btndetectar.setColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 499;BA.debugLine="btnDetectar.TextColor = Colors.White";
mostCurrent._btndetectar.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 501;BA.debugLine="If FusedLocationProvider1.IsInitialized = False T";
if (_fusedlocationprovider1.IsInitialized()==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 503;BA.debugLine="FusedLocationProvider1.Initialize(\"FusedLocation";
_fusedlocationprovider1.Initialize(processBA,"FusedLocationProviderExplorar");
 //BA.debugLineNum = 504;BA.debugLine="Log(\"init fusedlocationproviderExplorar\")";
anywheresoftware.b4a.keywords.Common.LogImpl("22097177","init fusedlocationproviderExplorar",0);
 };
 //BA.debugLineNum = 506;BA.debugLine="If FusedLocationProvider1.IsConnected = False The";
if (_fusedlocationprovider1.IsConnected()==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 507;BA.debugLine="FusedLocationProvider1.Connect";
_fusedlocationprovider1.Connect();
 //BA.debugLineNum = 508;BA.debugLine="Log(\"connecting fusedlocationproviderExplorar\")";
anywheresoftware.b4a.keywords.Common.LogImpl("22097181","connecting fusedlocationproviderExplorar",0);
 };
 //BA.debugLineNum = 510;BA.debugLine="End Sub";
return "";
}
public static String  _fondoblanco_click() throws Exception{
 //BA.debugLineNum = 512;BA.debugLine="Sub fondoblanco_Click";
 //BA.debugLineNum = 514;BA.debugLine="btnDetectar.Color = Colors.Transparent";
mostCurrent._btndetectar.setColor(anywheresoftware.b4a.keywords.Common.Colors.Transparent);
 //BA.debugLineNum = 515;BA.debugLine="btnDetectar.TextColor = Colors.black";
mostCurrent._btndetectar.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 516;BA.debugLine="btnDetectar.Enabled = True";
mostCurrent._btndetectar.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 518;BA.debugLine="fondoblanco.RemoveView";
mostCurrent._fondoblanco.RemoveView();
 //BA.debugLineNum = 519;BA.debugLine="detectandoLabel.RemoveView";
mostCurrent._detectandolabel.RemoveView();
 //BA.debugLineNum = 520;BA.debugLine="End Sub";
return "";
}
public static String  _fusedlocationprovider1_connectionfailed(int _connectionresult1) throws Exception{
 //BA.debugLineNum = 522;BA.debugLine="Sub FusedLocationProvider1_ConnectionFailed(Connec";
 //BA.debugLineNum = 523;BA.debugLine="Log(\"FusedLocationProvider1_ConnectionFailed\")";
anywheresoftware.b4a.keywords.Common.LogImpl("22228225","FusedLocationProvider1_ConnectionFailed",0);
 //BA.debugLineNum = 527;BA.debugLine="Select ConnectionResult1";
switch (BA.switchObjectToInt(_connectionresult1,_fusedlocationprovider1.ConnectionResult.NETWORK_ERROR)) {
case 0: {
 //BA.debugLineNum = 531;BA.debugLine="FusedLocationProvider1.Connect";
_fusedlocationprovider1.Connect();
 break; }
default: {
 break; }
}
;
 //BA.debugLineNum = 535;BA.debugLine="End Sub";
return "";
}
public static String  _fusedlocationprovider1_connectionsuccess() throws Exception{
uk.co.martinpearman.b4a.fusedlocationprovider.LocationRequest _locationrequest1 = null;
uk.co.martinpearman.b4a.fusedlocationprovider.LocationSettingsRequestBuilder _locationsettingsrequestbuilder1 = null;
 //BA.debugLineNum = 536;BA.debugLine="Sub FusedLocationProvider1_ConnectionSuccess";
 //BA.debugLineNum = 537;BA.debugLine="Log(\"FusedLocationProvider1_ConnectionSuccess\")";
anywheresoftware.b4a.keywords.Common.LogImpl("22293761","FusedLocationProvider1_ConnectionSuccess",0);
 //BA.debugLineNum = 538;BA.debugLine="Dim LocationRequest1 As LocationRequest";
_locationrequest1 = new uk.co.martinpearman.b4a.fusedlocationprovider.LocationRequest();
 //BA.debugLineNum = 539;BA.debugLine="LocationRequest1.Initialize";
_locationrequest1.Initialize();
 //BA.debugLineNum = 540;BA.debugLine="LocationRequest1.SetInterval(1000)	'	1000 millise";
_locationrequest1.SetInterval((long) (1000));
 //BA.debugLineNum = 542;BA.debugLine="LocationRequest1.SetPriority(LocationRequest1.Pri";
_locationrequest1.SetPriority(_locationrequest1.Priority.PRIORITY_HIGH_ACCURACY);
 //BA.debugLineNum = 543;BA.debugLine="LocationRequest1.SetSmallestDisplacement(1)	'	1 m";
_locationrequest1.SetSmallestDisplacement((float) (1));
 //BA.debugLineNum = 545;BA.debugLine="Dim LocationSettingsRequestBuilder1 As LocationSe";
_locationsettingsrequestbuilder1 = new uk.co.martinpearman.b4a.fusedlocationprovider.LocationSettingsRequestBuilder();
 //BA.debugLineNum = 546;BA.debugLine="LocationSettingsRequestBuilder1.Initialize";
_locationsettingsrequestbuilder1.Initialize();
 //BA.debugLineNum = 547;BA.debugLine="LocationSettingsRequestBuilder1.AddLocationReques";
_locationsettingsrequestbuilder1.AddLocationRequest((com.google.android.gms.location.LocationRequest)(_locationrequest1.getObject()));
 //BA.debugLineNum = 548;BA.debugLine="FusedLocationProvider1.CheckLocationSettings(Loca";
_fusedlocationprovider1.CheckLocationSettings(_locationsettingsrequestbuilder1.Build());
 //BA.debugLineNum = 550;BA.debugLine="FusedLocationProvider1.RequestLocationUpdates(Loc";
_fusedlocationprovider1.RequestLocationUpdates((com.google.android.gms.location.LocationRequest)(_locationrequest1.getObject()));
 //BA.debugLineNum = 552;BA.debugLine="btnDetectar.Color = Colors.Transparent";
mostCurrent._btndetectar.setColor(anywheresoftware.b4a.keywords.Common.Colors.Transparent);
 //BA.debugLineNum = 553;BA.debugLine="btnDetectar.TextColor = Colors.black";
mostCurrent._btndetectar.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 554;BA.debugLine="btnDetectar.Enabled = True";
mostCurrent._btndetectar.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 555;BA.debugLine="End Sub";
return "";
}
public static String  _fusedlocationprovider1_connectionsuspended(int _suspendedcause1) throws Exception{
 //BA.debugLineNum = 556;BA.debugLine="Sub FusedLocationProvider1_ConnectionSuspended(Sus";
 //BA.debugLineNum = 557;BA.debugLine="Log(\"FusedLocationProvider1_ConnectionSuspended\")";
anywheresoftware.b4a.keywords.Common.LogImpl("22359297","FusedLocationProvider1_ConnectionSuspended",0);
 //BA.debugLineNum = 561;BA.debugLine="Select SuspendedCause1";
switch (BA.switchObjectToInt(_suspendedcause1,_fusedlocationprovider1.SuspendedCause.CAUSE_NETWORK_LOST,_fusedlocationprovider1.SuspendedCause.CAUSE_SERVICE_DISCONNECTED)) {
case 0: {
 break; }
case 1: {
 break; }
}
;
 //BA.debugLineNum = 567;BA.debugLine="End Sub";
return "";
}
public static String  _fusedlocationprovider1_locationchanged(anywheresoftware.b4a.gps.LocationWrapper _location1) throws Exception{
 //BA.debugLineNum = 568;BA.debugLine="Sub FusedLocationProvider1_LocationChanged(Locatio";
 //BA.debugLineNum = 569;BA.debugLine="Log(\"FusedLocationProvider1_LocationChanged\")";
anywheresoftware.b4a.keywords.Common.LogImpl("22424833","FusedLocationProvider1_LocationChanged",0);
 //BA.debugLineNum = 570;BA.debugLine="LastLocation=Location1";
_lastlocation = _location1;
 //BA.debugLineNum = 571;BA.debugLine="Log(LastLocation.Latitude)";
anywheresoftware.b4a.keywords.Common.LogImpl("22424835",BA.NumberToString(_lastlocation.getLatitude()),0);
 //BA.debugLineNum = 572;BA.debugLine="Log(LastLocation.Longitude)";
anywheresoftware.b4a.keywords.Common.LogImpl("22424836",BA.NumberToString(_lastlocation.getLongitude()),0);
 //BA.debugLineNum = 573;BA.debugLine="UpdateUI";
_updateui();
 //BA.debugLineNum = 574;BA.debugLine="End Sub";
return "";
}
public static String  _fusedlocationprovider1_locationsettingschecked(uk.co.martinpearman.b4a.fusedlocationprovider.LocationSettingsResult _locationsettingsresult1) throws Exception{
uk.co.martinpearman.b4a.fusedlocationprovider.LocationSettingsStatus _locationsettingsstatus1 = null;
 //BA.debugLineNum = 575;BA.debugLine="Sub FusedLocationProvider1_LocationSettingsChecked";
 //BA.debugLineNum = 576;BA.debugLine="Log(\"FusedLocationProvider1_LocationSettingsCheck";
anywheresoftware.b4a.keywords.Common.LogImpl("22490369","FusedLocationProvider1_LocationSettingsChecked",0);
 //BA.debugLineNum = 577;BA.debugLine="Dim LocationSettingsStatus1 As LocationSettingsSt";
_locationsettingsstatus1 = new uk.co.martinpearman.b4a.fusedlocationprovider.LocationSettingsStatus();
_locationsettingsstatus1 = _locationsettingsresult1.GetLocationSettingsStatus();
 //BA.debugLineNum = 578;BA.debugLine="Select LocationSettingsStatus1.GetStatusCode";
switch (BA.switchObjectToInt(_locationsettingsstatus1.GetStatusCode(),_locationsettingsstatus1.StatusCodes.RESOLUTION_REQUIRED,_locationsettingsstatus1.StatusCodes.SETTINGS_CHANGE_UNAVAILABLE,_locationsettingsstatus1.StatusCodes.SUCCESS)) {
case 0: {
 //BA.debugLineNum = 580;BA.debugLine="Log(\"RESOLUTION_REQUIRED\")";
anywheresoftware.b4a.keywords.Common.LogImpl("22490373","RESOLUTION_REQUIRED",0);
 //BA.debugLineNum = 583;BA.debugLine="LocationSettingsStatus1.StartResolutionDialog(\"";
_locationsettingsstatus1.StartResolutionDialog(mostCurrent.activityBA,"LocationSettingsResult1");
 break; }
case 1: {
 //BA.debugLineNum = 585;BA.debugLine="Log(\"SETTINGS_CHANGE_UNAVAILABLE\")";
anywheresoftware.b4a.keywords.Common.LogImpl("22490378","SETTINGS_CHANGE_UNAVAILABLE",0);
 //BA.debugLineNum = 588;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 589;BA.debugLine="MsgboxAsync(\"Error, tu dispositivo no tiene lo";
anywheresoftware.b4a.keywords.Common.MsgboxAsync(BA.ObjectToCharSequence("Error, tu dispositivo no tiene localización. Busca tu posición en el mapa!"),BA.ObjectToCharSequence("Problem"),processBA);
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 591;BA.debugLine="MsgboxAsync(\"Error, your device cannot be loca";
anywheresoftware.b4a.keywords.Common.MsgboxAsync(BA.ObjectToCharSequence("Error, your device cannot be located. Find your location in the map!"),BA.ObjectToCharSequence("Problem"),processBA);
 };
 //BA.debugLineNum = 593;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 break; }
case 2: {
 //BA.debugLineNum = 595;BA.debugLine="Log(\"SUCCESS\")";
anywheresoftware.b4a.keywords.Common.LogImpl("22490388","SUCCESS",0);
 break; }
}
;
 //BA.debugLineNum = 599;BA.debugLine="End Sub";
return "";
}
public static String  _getmimapa() throws Exception{
ilpla.appear.downloadservice._downloaddata _dd = null;
 //BA.debugLineNum = 666;BA.debugLine="Sub GetMiMapa";
 //BA.debugLineNum = 667;BA.debugLine="ProgressDialogShow(\"Buscando puntos cercanos...\")";
anywheresoftware.b4a.keywords.Common.ProgressDialogShow(mostCurrent.activityBA,BA.ObjectToCharSequence("Buscando puntos cercanos..."));
 //BA.debugLineNum = 668;BA.debugLine="btnDetectar.Enabled = False";
mostCurrent._btndetectar.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 669;BA.debugLine="lblMarkerInfo.Visible = False";
mostCurrent._lblmarkerinfo.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 671;BA.debugLine="Dim dd As DownloadData";
_dd = new ilpla.appear.downloadservice._downloaddata();
 //BA.debugLineNum = 672;BA.debugLine="dd.url = Main.serverPath & \"/\" & Main.serverConne";
_dd.url /*String*/  = mostCurrent._main._serverpath /*String*/ +"/"+mostCurrent._main._serverconnectionfolder /*String*/ +"/getallmapa.php";
 //BA.debugLineNum = 673;BA.debugLine="dd.EventName = \"GetMiMapa\"";
_dd.EventName /*String*/  = "GetMiMapa";
 //BA.debugLineNum = 674;BA.debugLine="dd.Target = Me";
_dd.Target /*Object*/  = form_main.getObject();
 //BA.debugLineNum = 675;BA.debugLine="CallSubDelayed2(DownloadService, \"StartDownload\",";
anywheresoftware.b4a.keywords.Common.CallSubDelayed2(processBA,(Object)(mostCurrent._downloadservice.getObject()),"StartDownload",(Object)(_dd));
 //BA.debugLineNum = 677;BA.debugLine="End Sub";
return "";
}
public static String  _getmimapa_complete(ilpla.appear.httpjob _job) throws Exception{
String _ret = "";
String _act = "";
anywheresoftware.b4a.objects.collections.JSONParser _parser = null;
String _numresults = "";
anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper _marker_blue = null;
anywheresoftware.b4a.objects.drawable.BitmapDrawable _marker_blue_dr = null;
anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper _marker_green = null;
anywheresoftware.b4a.objects.drawable.BitmapDrawable _marker_green_dr = null;
anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper _marker_orange = null;
anywheresoftware.b4a.objects.drawable.BitmapDrawable _marker_orange_dr = null;
anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper _marker_yellow = null;
anywheresoftware.b4a.objects.drawable.BitmapDrawable _marker_yellow_dr = null;
anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper _marker_red = null;
anywheresoftware.b4a.objects.drawable.BitmapDrawable _marker_red_dr = null;
anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper _marker_gray = null;
anywheresoftware.b4a.objects.drawable.BitmapDrawable _marker_gray_dr = null;
int _i = 0;
anywheresoftware.b4a.objects.collections.Map _newpunto = null;
String _nombresitio = "";
double _sitiolat = 0;
double _sitiolong = 0;
String _sitioindice = "";
String _sitiotiporio = "";
String _sitiocontribucion = "";
uk.co.martinpearman.b4a.osmdroid.views.overlay.Marker _marker = null;
String _estadositiomarker = "";
String _markerexportstr = "";
 //BA.debugLineNum = 678;BA.debugLine="Sub GetMiMapa_Complete(Job As HttpJob)";
 //BA.debugLineNum = 679;BA.debugLine="Log(\"GetMapa messages: \" & Job.Success)";
anywheresoftware.b4a.keywords.Common.LogImpl("22752513","GetMapa messages: "+BA.ObjectToString(_job._success /*boolean*/ ),0);
 //BA.debugLineNum = 680;BA.debugLine="If Job.Success = True Then";
if (_job._success /*boolean*/ ==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 682;BA.debugLine="Dim ret As String";
_ret = "";
 //BA.debugLineNum = 683;BA.debugLine="Dim act As String";
_act = "";
 //BA.debugLineNum = 684;BA.debugLine="ret = Job.GetString";
_ret = _job._getstring /*String*/ ();
 //BA.debugLineNum = 685;BA.debugLine="Dim parser As JSONParser";
_parser = new anywheresoftware.b4a.objects.collections.JSONParser();
 //BA.debugLineNum = 686;BA.debugLine="parser.Initialize(ret)";
_parser.Initialize(_ret);
 //BA.debugLineNum = 687;BA.debugLine="act = parser.NextValue";
_act = BA.ObjectToString(_parser.NextValue());
 //BA.debugLineNum = 688;BA.debugLine="If act = \"Not Found\" Then";
if ((_act).equals("Not Found")) { 
 //BA.debugLineNum = 689;BA.debugLine="ToastMessageShow(\"No encuentro tus sitios anter";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("No encuentro tus sitios anteriores, prueba luego"),anywheresoftware.b4a.keywords.Common.True);
 }else if((_act).equals("Error")) { 
 //BA.debugLineNum = 691;BA.debugLine="ToastMessageShow(\"No encuentro tus sitios anter";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("No encuentro tus sitios anteriores, prueba luego"),anywheresoftware.b4a.keywords.Common.True);
 }else if((_act).equals("GetMapaOk")) { 
 //BA.debugLineNum = 694;BA.debugLine="Dim numresults As String";
_numresults = "";
 //BA.debugLineNum = 695;BA.debugLine="numresults = parser.NextValue";
_numresults = BA.ObjectToString(_parser.NextValue());
 //BA.debugLineNum = 700;BA.debugLine="markersOverlay.Initialize(\"markersOverlay\", Map";
mostCurrent._markersoverlay.Initialize(processBA,"markersOverlay",(org.osmdroid.views.MapView)(mostCurrent._mapview1.getObject()));
 //BA.debugLineNum = 701;BA.debugLine="MapView1.GetOverlays.Add(markersOverlay)";
mostCurrent._mapview1.GetOverlays().Add((org.osmdroid.views.overlay.Overlay)(mostCurrent._markersoverlay.getObject()));
 //BA.debugLineNum = 704;BA.debugLine="markersList.Initialize()";
mostCurrent._markerslist.Initialize();
 //BA.debugLineNum = 706;BA.debugLine="Dim marker_blue As Bitmap";
_marker_blue = new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper();
 //BA.debugLineNum = 707;BA.debugLine="marker_blue.Initialize(File.DirAssets, \"marker_";
_marker_blue.Initialize(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"marker_blue.png");
 //BA.debugLineNum = 708;BA.debugLine="Dim marker_blue_dr As BitmapDrawable";
_marker_blue_dr = new anywheresoftware.b4a.objects.drawable.BitmapDrawable();
 //BA.debugLineNum = 709;BA.debugLine="marker_blue_dr.Initialize(marker_blue)";
_marker_blue_dr.Initialize((android.graphics.Bitmap)(_marker_blue.getObject()));
 //BA.debugLineNum = 711;BA.debugLine="Dim marker_green As Bitmap";
_marker_green = new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper();
 //BA.debugLineNum = 712;BA.debugLine="marker_green.Initialize(File.DirAssets, \"marker";
_marker_green.Initialize(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"marker_green.png");
 //BA.debugLineNum = 713;BA.debugLine="Dim marker_green_dr As BitmapDrawable";
_marker_green_dr = new anywheresoftware.b4a.objects.drawable.BitmapDrawable();
 //BA.debugLineNum = 714;BA.debugLine="marker_green_dr.Initialize(marker_green)";
_marker_green_dr.Initialize((android.graphics.Bitmap)(_marker_green.getObject()));
 //BA.debugLineNum = 716;BA.debugLine="Dim marker_orange As Bitmap";
_marker_orange = new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper();
 //BA.debugLineNum = 717;BA.debugLine="marker_orange.Initialize(File.DirAssets, \"marke";
_marker_orange.Initialize(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"marker_orange.png");
 //BA.debugLineNum = 718;BA.debugLine="Dim marker_orange_dr As BitmapDrawable";
_marker_orange_dr = new anywheresoftware.b4a.objects.drawable.BitmapDrawable();
 //BA.debugLineNum = 719;BA.debugLine="marker_orange_dr.Initialize(marker_orange)";
_marker_orange_dr.Initialize((android.graphics.Bitmap)(_marker_orange.getObject()));
 //BA.debugLineNum = 721;BA.debugLine="Dim marker_yellow As Bitmap";
_marker_yellow = new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper();
 //BA.debugLineNum = 722;BA.debugLine="marker_yellow.Initialize(File.DirAssets, \"marke";
_marker_yellow.Initialize(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"marker_yellow.png");
 //BA.debugLineNum = 723;BA.debugLine="Dim marker_yellow_dr As BitmapDrawable";
_marker_yellow_dr = new anywheresoftware.b4a.objects.drawable.BitmapDrawable();
 //BA.debugLineNum = 724;BA.debugLine="marker_yellow_dr.Initialize(marker_yellow)";
_marker_yellow_dr.Initialize((android.graphics.Bitmap)(_marker_yellow.getObject()));
 //BA.debugLineNum = 726;BA.debugLine="Dim marker_red As Bitmap";
_marker_red = new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper();
 //BA.debugLineNum = 727;BA.debugLine="marker_red.Initialize(File.DirAssets, \"marker_r";
_marker_red.Initialize(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"marker_red.png");
 //BA.debugLineNum = 728;BA.debugLine="Dim marker_red_dr As BitmapDrawable";
_marker_red_dr = new anywheresoftware.b4a.objects.drawable.BitmapDrawable();
 //BA.debugLineNum = 729;BA.debugLine="marker_red_dr.Initialize(marker_red)";
_marker_red_dr.Initialize((android.graphics.Bitmap)(_marker_red.getObject()));
 //BA.debugLineNum = 731;BA.debugLine="Dim marker_gray As Bitmap";
_marker_gray = new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper();
 //BA.debugLineNum = 732;BA.debugLine="marker_gray.Initialize(File.DirAssets, \"marker_";
_marker_gray.Initialize(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"marker_gray.png");
 //BA.debugLineNum = 733;BA.debugLine="Dim marker_gray_dr As BitmapDrawable";
_marker_gray_dr = new anywheresoftware.b4a.objects.drawable.BitmapDrawable();
 //BA.debugLineNum = 734;BA.debugLine="marker_gray_dr.Initialize(marker_gray)";
_marker_gray_dr.Initialize((android.graphics.Bitmap)(_marker_gray.getObject()));
 //BA.debugLineNum = 736;BA.debugLine="If numresults = 0 Then";
if ((_numresults).equals(BA.NumberToString(0))) { 
 //BA.debugLineNum = 737;BA.debugLine="ToastMessageShow(\"Aún no has enviado ninguna e";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Aún no has enviado ninguna evaluación. Cuando lo hagas, aparecerá en este mapa!"),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 738;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 740;BA.debugLine="MarkerExport.Initialize()";
mostCurrent._markerexport.Initialize();
 //BA.debugLineNum = 742;BA.debugLine="For i = 0 To numresults - 1";
{
final int step48 = 1;
final int limit48 = (int) ((double)(Double.parseDouble(_numresults))-1);
_i = (int) (0) ;
for (;_i <= limit48 ;_i = _i + step48 ) {
 //BA.debugLineNum = 744;BA.debugLine="Dim newpunto As Map";
_newpunto = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 745;BA.debugLine="newpunto = parser.NextObject";
_newpunto = _parser.NextObject();
 //BA.debugLineNum = 746;BA.debugLine="Dim nombresitio As String = newpunto.Get(\"nomb";
_nombresitio = BA.ObjectToString(_newpunto.Get((Object)("nombresitio")));
 //BA.debugLineNum = 747;BA.debugLine="Dim sitiolat As Double = newpunto.Get(\"lat\")";
_sitiolat = (double)(BA.ObjectToNumber(_newpunto.Get((Object)("lat"))));
 //BA.debugLineNum = 748;BA.debugLine="Dim sitiolong As Double = newpunto.Get(\"lng\")";
_sitiolong = (double)(BA.ObjectToNumber(_newpunto.Get((Object)("lng"))));
 //BA.debugLineNum = 749;BA.debugLine="Dim sitioindice As String = newpunto.Get(\"indi";
_sitioindice = BA.ObjectToString(_newpunto.Get((Object)("indice")));
 //BA.debugLineNum = 750;BA.debugLine="Dim sitiotiporio As String = newpunto.Get(\"tip";
_sitiotiporio = BA.ObjectToString(_newpunto.Get((Object)("tiporio")));
 //BA.debugLineNum = 751;BA.debugLine="Dim sitiocontribucion As String = newpunto.Get";
_sitiocontribucion = BA.ObjectToString(_newpunto.Get((Object)("username")));
 //BA.debugLineNum = 753;BA.debugLine="If sitiocontribucion.Contains(\"@\") Then";
if (_sitiocontribucion.contains("@")) { 
 //BA.debugLineNum = 754;BA.debugLine="sitiocontribucion = sitiocontribucion.SubStri";
_sitiocontribucion = _sitiocontribucion.substring((int) (0),_sitiocontribucion.indexOf("@"));
 };
 //BA.debugLineNum = 756;BA.debugLine="Dim marker As OSMDroid_Marker";
_marker = new uk.co.martinpearman.b4a.osmdroid.views.overlay.Marker();
 //BA.debugLineNum = 758;BA.debugLine="If sitiolat <> \"0\" And sitiolong <> \"0\" Then";
if (_sitiolat!=(double)(Double.parseDouble("0")) && _sitiolong!=(double)(Double.parseDouble("0"))) { 
 //BA.debugLineNum = 759;BA.debugLine="Dim estadositiomarker As String = \"\"";
_estadositiomarker = "";
 //BA.debugLineNum = 760;BA.debugLine="If sitioindice < 20 Then";
if ((double)(Double.parseDouble(_sitioindice))<20) { 
 //BA.debugLineNum = 761;BA.debugLine="estadositiomarker = \"Muy malo\"";
_estadositiomarker = "Muy malo";
 //BA.debugLineNum = 762;BA.debugLine="marker.Initialize(estadositiomarker,sitiocon";
_marker.Initialize(_estadositiomarker,_sitiocontribucion,_sitiolat,_sitiolong);
 //BA.debugLineNum = 763;BA.debugLine="marker.SetMarkerIcon(marker_red_dr)";
_marker.SetMarkerIcon((android.graphics.drawable.BitmapDrawable)(_marker_red_dr.getObject()));
 }else if((double)(Double.parseDouble(_sitioindice))>=20 && (double)(Double.parseDouble(_sitioindice))<40) { 
 //BA.debugLineNum = 765;BA.debugLine="estadositiomarker = \"Malo\"";
_estadositiomarker = "Malo";
 //BA.debugLineNum = 766;BA.debugLine="marker.Initialize(estadositiomarker,sitiocon";
_marker.Initialize(_estadositiomarker,_sitiocontribucion,_sitiolat,_sitiolong);
 //BA.debugLineNum = 767;BA.debugLine="marker.SetMarkerIcon(marker_orange_dr)";
_marker.SetMarkerIcon((android.graphics.drawable.BitmapDrawable)(_marker_orange_dr.getObject()));
 }else if((double)(Double.parseDouble(_sitioindice))>=40 && (double)(Double.parseDouble(_sitioindice))<60) { 
 //BA.debugLineNum = 769;BA.debugLine="estadositiomarker = \"Regular\"";
_estadositiomarker = "Regular";
 //BA.debugLineNum = 770;BA.debugLine="marker.Initialize(estadositiomarker,sitiocon";
_marker.Initialize(_estadositiomarker,_sitiocontribucion,_sitiolat,_sitiolong);
 //BA.debugLineNum = 771;BA.debugLine="marker.SetMarkerIcon(marker_yellow_dr)";
_marker.SetMarkerIcon((android.graphics.drawable.BitmapDrawable)(_marker_yellow_dr.getObject()));
 }else if((double)(Double.parseDouble(_sitioindice))>=60 && (double)(Double.parseDouble(_sitioindice))<80) { 
 //BA.debugLineNum = 773;BA.debugLine="estadositiomarker = \"Bueno\"";
_estadositiomarker = "Bueno";
 //BA.debugLineNum = 774;BA.debugLine="marker.Initialize(estadositiomarker,sitiocon";
_marker.Initialize(_estadositiomarker,_sitiocontribucion,_sitiolat,_sitiolong);
 //BA.debugLineNum = 775;BA.debugLine="marker.SetMarkerIcon(marker_green_dr)";
_marker.SetMarkerIcon((android.graphics.drawable.BitmapDrawable)(_marker_green_dr.getObject()));
 }else if((double)(Double.parseDouble(_sitioindice))>=80) { 
 //BA.debugLineNum = 777;BA.debugLine="estadositiomarker = \"Muy bueno!\"";
_estadositiomarker = "Muy bueno!";
 //BA.debugLineNum = 778;BA.debugLine="marker.Initialize(estadositiomarker,sitiocon";
_marker.Initialize(_estadositiomarker,_sitiocontribucion,_sitiolat,_sitiolong);
 //BA.debugLineNum = 779;BA.debugLine="marker.SetMarkerIcon(marker_blue_dr)";
_marker.SetMarkerIcon((android.graphics.drawable.BitmapDrawable)(_marker_blue_dr.getObject()));
 }else {
 //BA.debugLineNum = 781;BA.debugLine="estadositiomarker = \"Indet.\"";
_estadositiomarker = "Indet.";
 //BA.debugLineNum = 782;BA.debugLine="marker.Initialize(estadositiomarker,sitiocon";
_marker.Initialize(_estadositiomarker,_sitiocontribucion,_sitiolat,_sitiolong);
 //BA.debugLineNum = 783;BA.debugLine="marker.SetMarkerIcon(marker_green_dr)";
_marker.SetMarkerIcon((android.graphics.drawable.BitmapDrawable)(_marker_green_dr.getObject()));
 };
 //BA.debugLineNum = 786;BA.debugLine="Dim MarkerExportStr As String";
_markerexportstr = "";
 //BA.debugLineNum = 787;BA.debugLine="MarkerExportStr = nombresitio & \",\" & sitiola";
_markerexportstr = _nombresitio+","+BA.NumberToString(_sitiolat)+","+BA.NumberToString(_sitiolong)+","+_estadositiomarker+","+_sitiocontribucion;
 //BA.debugLineNum = 788;BA.debugLine="MarkerExport.Add(MarkerExportStr)";
mostCurrent._markerexport.Add((Object)(_markerexportstr));
 //BA.debugLineNum = 790;BA.debugLine="markersList.Add(marker)";
mostCurrent._markerslist.Add((Object)(_marker.getObject()));
 }else {
 //BA.debugLineNum = 793;BA.debugLine="Log(\"Punto no agregado\")";
anywheresoftware.b4a.keywords.Common.LogImpl("22752627","Punto no agregado",0);
 };
 }
};
 //BA.debugLineNum = 796;BA.debugLine="markersOverlay.AddItems(markersList)";
mostCurrent._markersoverlay.AddItems(mostCurrent._markerslist);
 };
 }else {
 //BA.debugLineNum = 800;BA.debugLine="Log(\"GetMapa not ok\")";
anywheresoftware.b4a.keywords.Common.LogImpl("22752634","GetMapa not ok",0);
 //BA.debugLineNum = 801;BA.debugLine="MsgboxAsync(\"Al parecer hay un problema en nues";
anywheresoftware.b4a.keywords.Common.MsgboxAsync(BA.ObjectToCharSequence("Al parecer hay un problema en nuestros servidores, lo solucionaremos pronto!"),BA.ObjectToCharSequence("Mala mía"),processBA);
 };
 //BA.debugLineNum = 803;BA.debugLine="Job.Release";
_job._release /*String*/ ();
 //BA.debugLineNum = 804;BA.debugLine="btnDetectar.Enabled = True";
mostCurrent._btndetectar.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 805;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 //BA.debugLineNum = 807;BA.debugLine="If FusedLocationProvider1.IsConnected = False The";
if (_fusedlocationprovider1.IsConnected()==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 808;BA.debugLine="FusedLocationProvider1.Connect";
_fusedlocationprovider1.Connect();
 };
 //BA.debugLineNum = 810;BA.debugLine="End Sub";
return "";
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 21;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 24;BA.debugLine="Dim FormMainloaded As Boolean = False";
_formmainloaded = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 25;BA.debugLine="Dim p As Phone";
mostCurrent._p = new anywheresoftware.b4a.phone.Phone();
 //BA.debugLineNum = 26;BA.debugLine="Dim pi As PhoneIntents";
mostCurrent._pi = new anywheresoftware.b4a.phone.Phone.PhoneIntents();
 //BA.debugLineNum = 30;BA.debugLine="Private pgbNivel As ProgressBar";
mostCurrent._pgbnivel = new anywheresoftware.b4a.objects.ProgressBarWrapper();
 //BA.debugLineNum = 31;BA.debugLine="Private lblLevel As Label";
mostCurrent._lbllevel = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 34;BA.debugLine="Private tabStripMain As TabStrip";
mostCurrent._tabstripmain = new anywheresoftware.b4a.objects.TabStripViewPager();
 //BA.debugLineNum = 38;BA.debugLine="Private lblAnalizar As Label";
mostCurrent._lblanalizar = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 39;BA.debugLine="Private btnReportar As Button";
mostCurrent._btnreportar = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 40;BA.debugLine="Private lblComoFuncionaAnalizar As Label";
mostCurrent._lblcomofuncionaanalizar = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 43;BA.debugLine="Private pnlMapa As Panel";
mostCurrent._pnlmapa = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 47;BA.debugLine="Private lblConoce As Label";
mostCurrent._lblconoce = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 48;BA.debugLine="Private lblRequiereInternet As Label";
mostCurrent._lblrequiereinternet = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 49;BA.debugLine="Private lblComoFuncionaExplorar As Label";
mostCurrent._lblcomofuncionaexplorar = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 52;BA.debugLine="Private btnDetectar As Button";
mostCurrent._btndetectar = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 53;BA.debugLine="Private lblLat As Label";
mostCurrent._lbllat = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 54;BA.debugLine="Private lblLon As Label";
mostCurrent._lbllon = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 55;BA.debugLine="Dim MapView1 As OSMDroid_MapView";
mostCurrent._mapview1 = new uk.co.martinpearman.b4a.osmdroid.views.MapView();
 //BA.debugLineNum = 56;BA.debugLine="Private btnZoomAll As Button";
mostCurrent._btnzoomall = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 57;BA.debugLine="Dim SimpleLocationOverlay1 As OSMDroid_SimpleLoca";
mostCurrent._simplelocationoverlay1 = new uk.co.martinpearman.b4a.osmdroid.views.overlay.SimpleLocationOverlay();
 //BA.debugLineNum = 58;BA.debugLine="Dim MarkerExport As List";
mostCurrent._markerexport = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 60;BA.debugLine="Dim MarkerInfo As Label";
mostCurrent._markerinfo = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 62;BA.debugLine="Private pnlMapa As Panel";
mostCurrent._pnlmapa = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 63;BA.debugLine="Dim fondoblanco As Label";
mostCurrent._fondoblanco = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 64;BA.debugLine="Dim detectandoLabel As Label";
mostCurrent._detectandolabel = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 67;BA.debugLine="Dim markersList As List";
mostCurrent._markerslist = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 68;BA.debugLine="Dim markersOverlay As OSMDroid_MarkerOverlay";
mostCurrent._markersoverlay = new uk.co.martinpearman.b4a.osmdroid.views.overlay.MarkerOverlay();
 //BA.debugLineNum = 69;BA.debugLine="Private Label4 As Label";
mostCurrent._label4 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 70;BA.debugLine="Private lblMarkerInfo As Label";
mostCurrent._lblmarkerinfo = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 73;BA.debugLine="Dim firstuse As String";
mostCurrent._firstuse = "";
 //BA.debugLineNum = 74;BA.debugLine="Dim fondogris As Panel";
mostCurrent._fondogris = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 75;BA.debugLine="Dim tutorialEtapa As Int";
_tutorialetapa = 0;
 //BA.debugLineNum = 76;BA.debugLine="Private btnMenu As Button";
mostCurrent._btnmenu = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 80;BA.debugLine="Dim Drawer As B4XDrawer";
mostCurrent._drawer = new ilpla.appear.b4xdrawer();
 //BA.debugLineNum = 81;BA.debugLine="Private btnCerrarSesion As Button";
mostCurrent._btncerrarsesion = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 82;BA.debugLine="Private btnEditUser As Label";
mostCurrent._btnedituser = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 83;BA.debugLine="Private btnverMedallas As Label";
mostCurrent._btnvermedallas = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 84;BA.debugLine="Private lblUserName As Label";
mostCurrent._lblusername = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 85;BA.debugLine="Private lblRegistrate As Label";
mostCurrent._lblregistrate = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 86;BA.debugLine="Private btnVerPerfil As Label";
mostCurrent._btnverperfil = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 87;BA.debugLine="Private btnAbout As Label";
mostCurrent._btnabout = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 88;BA.debugLine="Private btnPoliticaDatos As Label";
mostCurrent._btnpoliticadatos = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 89;BA.debugLine="Private btnDatosAnteriores As Label";
mostCurrent._btndatosanteriores = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 90;BA.debugLine="Private btnMuestreos As Label";
mostCurrent._btnmuestreos = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 94;BA.debugLine="Private lblTitleMunicipio As Label";
mostCurrent._lbltitlemunicipio = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 95;BA.debugLine="Private webMunicipio As WebView";
mostCurrent._webmunicipio = new anywheresoftware.b4a.objects.WebViewWrapper();
 //BA.debugLineNum = 96;BA.debugLine="Private btnMasInfoMunicipio As Button";
mostCurrent._btnmasinfomunicipio = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 97;BA.debugLine="Private imgLogoMunicipio As ImageView";
mostCurrent._imglogomunicipio = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 98;BA.debugLine="End Sub";
return "";
}
public static String  _lblcomofuncionaanalizar_click() throws Exception{
 //BA.debugLineNum = 429;BA.debugLine="Sub lblComoFuncionaAnalizar_Click";
 //BA.debugLineNum = 430;BA.debugLine="utilidades.Mensaje(\"\", \"\", \"Índices ecológicos\",";
mostCurrent._utilidades._mensaje /*String*/ (mostCurrent.activityBA,"","","Índices ecológicos","Contesta unas preguntas y toma fotografías, y a través de los índices ecológicos para ambientes acuáticos calculados por PreserVamos, sabrás la calidad ambiental del lugar adonde te encuentras.","Ok!","","",anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 431;BA.debugLine="End Sub";
return "";
}
public static String  _lblmarkerinfo_click() throws Exception{
 //BA.debugLineNum = 829;BA.debugLine="Private Sub lblMarkerInfo_Click";
 //BA.debugLineNum = 830;BA.debugLine="If lblMarkerInfo.Visible = True Then";
if (mostCurrent._lblmarkerinfo.getVisible()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 831;BA.debugLine="lblMarkerInfo.Visible = False";
mostCurrent._lblmarkerinfo.setVisible(anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 833;BA.debugLine="End Sub";
return "";
}
public static String  _loadform_main() throws Exception{
 //BA.debugLineNum = 277;BA.debugLine="Sub LoadForm_Main";
 //BA.debugLineNum = 278;BA.debugLine="Log(\"userName in LoadForm_Main:\" & Main.username)";
anywheresoftware.b4a.keywords.Common.LogImpl("21703937","userName in LoadForm_Main:"+mostCurrent._main._username /*String*/ ,0);
 //BA.debugLineNum = 279;BA.debugLine="Log(\"firstuse in LoadForm_Main:\" & firstuse)";
anywheresoftware.b4a.keywords.Common.LogImpl("21703938","firstuse in LoadForm_Main:"+mostCurrent._firstuse,0);
 //BA.debugLineNum = 283;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 284;BA.debugLine="Activity.LoadLayout(\"layMain\")";
mostCurrent._activity.LoadLayout("layMain",mostCurrent.activityBA);
 //BA.debugLineNum = 287;BA.debugLine="CrearMenu";
_crearmenu();
 //BA.debugLineNum = 290;BA.debugLine="tabStripMain.LoadLayout(\"layMainReportar\", \"ANALI";
mostCurrent._tabstripmain.LoadLayout("layMainReportar",BA.ObjectToCharSequence("ANALIZAR"));
 //BA.debugLineNum = 291;BA.debugLine="tabStripMain.LoadLayout(\"layMainMap\", \"MAPA\")";
mostCurrent._tabstripmain.LoadLayout("layMainMap",BA.ObjectToCharSequence("MAPA"));
 //BA.debugLineNum = 292;BA.debugLine="tabStripMain.LoadLayout(\"layMainMunicipio\", \"TU M";
mostCurrent._tabstripmain.LoadLayout("layMainMunicipio",BA.ObjectToCharSequence("TU MUNICIPIO"));
 //BA.debugLineNum = 296;BA.debugLine="If firstuse = \"True\" Or firstuse = \"\" Or firstuse";
if ((mostCurrent._firstuse).equals("True") || (mostCurrent._firstuse).equals("") || mostCurrent._firstuse== null || (mostCurrent._main._username /*String*/ ).equals("guest") || (mostCurrent._main._username /*String*/ ).equals("None")) { 
 }else {
 };
 //BA.debugLineNum = 337;BA.debugLine="End Sub";
return "";
}
public static String  _locationsettingsresult1_resolutiondialogdismissed(boolean _locationsettingsupdated) throws Exception{
 //BA.debugLineNum = 600;BA.debugLine="Sub LocationSettingsResult1_ResolutionDialogDismis";
 //BA.debugLineNum = 601;BA.debugLine="Log(\"LocationSettingsResult1_ResolutionDialogDism";
anywheresoftware.b4a.keywords.Common.LogImpl("22555905","LocationSettingsResult1_ResolutionDialogDismissed",0);
 //BA.debugLineNum = 602;BA.debugLine="If Not(LocationSettingsUpdated) Then";
if (anywheresoftware.b4a.keywords.Common.Not(_locationsettingsupdated)) { 
 //BA.debugLineNum = 604;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 605;BA.debugLine="MsgboxAsync(\"No tienes habilitada la Localizaci";
anywheresoftware.b4a.keywords.Common.MsgboxAsync(BA.ObjectToCharSequence("No tienes habilitada la Localización, busca en el mapa tu posición"),BA.ObjectToCharSequence("Búsqueda manual"),processBA);
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 607;BA.debugLine="MsgboxAsync(\"You don't have the location servic";
anywheresoftware.b4a.keywords.Common.MsgboxAsync(BA.ObjectToCharSequence("You don't have the location services enabled, use the map to locate your position"),BA.ObjectToCharSequence("Manual search"),processBA);
 };
 //BA.debugLineNum = 612;BA.debugLine="btnDetectar.Color = Colors.Transparent";
mostCurrent._btndetectar.setColor(anywheresoftware.b4a.keywords.Common.Colors.Transparent);
 //BA.debugLineNum = 613;BA.debugLine="btnDetectar.TextColor = Colors.black";
mostCurrent._btndetectar.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 614;BA.debugLine="btnDetectar.Enabled = True";
mostCurrent._btndetectar.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 617;BA.debugLine="If FusedLocationProvider1.IsConnected = True The";
if (_fusedlocationprovider1.IsConnected()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 618;BA.debugLine="FusedLocationProvider1.DisConnect";
_fusedlocationprovider1.Disconnect();
 };
 //BA.debugLineNum = 621;BA.debugLine="If fondoblanco.IsInitialized Then";
if (mostCurrent._fondoblanco.IsInitialized()) { 
 //BA.debugLineNum = 622;BA.debugLine="fondoblanco.RemoveView";
mostCurrent._fondoblanco.RemoveView();
 //BA.debugLineNum = 623;BA.debugLine="detectandoLabel.RemoveView";
mostCurrent._detectandolabel.RemoveView();
 };
 };
 //BA.debugLineNum = 626;BA.debugLine="End Sub";
return "";
}
public static boolean  _markersoverlay_click(uk.co.martinpearman.b4a.osmdroid.views.overlay.Marker _marker1) throws Exception{
String _contribuciontext = "";
 //BA.debugLineNum = 813;BA.debugLine="Sub markersOverlay_Click(Marker1 As OSMDroid_Marke";
 //BA.debugLineNum = 814;BA.debugLine="lblMarkerInfo.Visible = True";
mostCurrent._lblmarkerinfo.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 815;BA.debugLine="MapView1.GetController.SetCenter(Marker1.GetPoint";
mostCurrent._mapview1.GetController().SetCenter((org.osmdroid.util.GeoPoint)(_marker1.GetPoint().getObject()));
 //BA.debugLineNum = 816;BA.debugLine="Dim contribuciontext As String";
_contribuciontext = "";
 //BA.debugLineNum = 817;BA.debugLine="If Marker1.GetSnippet = \"null\" Or Marker1.GetSnip";
if ((_marker1.GetSnippet()).equals("null") || (_marker1.GetSnippet()).equals("") || _marker1.GetSnippet()== null) { 
 //BA.debugLineNum = 818;BA.debugLine="contribuciontext = \"Anónimo\"";
_contribuciontext = "Anónimo";
 }else {
 //BA.debugLineNum = 820;BA.debugLine="contribuciontext = Marker1.GetSnippet";
_contribuciontext = _marker1.GetSnippet();
 };
 //BA.debugLineNum = 823;BA.debugLine="lblMarkerInfo.Text= \"Calidad: \" & Marker1.GetTitl";
mostCurrent._lblmarkerinfo.setText(BA.ObjectToCharSequence("Calidad: "+_marker1.GetTitle()+anywheresoftware.b4a.keywords.Common.CRLF+"Contribución: "+_contribuciontext));
 //BA.debugLineNum = 824;BA.debugLine="lblMarkerInfo.Width = 50%x";
mostCurrent._lblmarkerinfo.setWidth(anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (50),mostCurrent.activityBA));
 //BA.debugLineNum = 825;BA.debugLine="lblMarkerInfo.Height = 50dip";
mostCurrent._lblmarkerinfo.setHeight(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (50)));
 //BA.debugLineNum = 826;BA.debugLine="lblMarkerInfo.Left = 50%x";
mostCurrent._lblmarkerinfo.setLeft(anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (50),mostCurrent.activityBA));
 //BA.debugLineNum = 827;BA.debugLine="lblMarkerInfo.Top = 50%y";
mostCurrent._lblmarkerinfo.setTop(anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (50),mostCurrent.activityBA));
 //BA.debugLineNum = 828;BA.debugLine="End Sub";
return false;
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 6;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 10;BA.debugLine="Private FusedLocationProvider1 As FusedLocationPr";
_fusedlocationprovider1 = new uk.co.martinpearman.b4a.fusedlocationprovider.FusedLocationProviderWrapper();
 //BA.debugLineNum = 11;BA.debugLine="Private LastLocation As Location";
_lastlocation = new anywheresoftware.b4a.gps.LocationWrapper();
 //BA.debugLineNum = 14;BA.debugLine="Dim IsGuest As Boolean";
_isguest = false;
 //BA.debugLineNum = 16;BA.debugLine="Dim fullidcurrentproject As String";
_fullidcurrentproject = "";
 //BA.debugLineNum = 19;BA.debugLine="End Sub";
return "";
}
public static String  _tabstripmain_pageselected(int _position) throws Exception{
 //BA.debugLineNum = 381;BA.debugLine="Sub tabStripMain_PageSelected (Position As Int)";
 //BA.debugLineNum = 382;BA.debugLine="If Position = 1 Then";
if (_position==1) { 
 //BA.debugLineNum = 383;BA.debugLine="CargarMapa";
_cargarmapa();
 }else if(_position==2) { 
 //BA.debugLineNum = 385;BA.debugLine="lblTitleMunicipio.Text = Main.strUserOrg";
mostCurrent._lbltitlemunicipio.setText(BA.ObjectToCharSequence(mostCurrent._main._struserorg /*String*/ ));
 //BA.debugLineNum = 386;BA.debugLine="webMunicipio.LoadUrl(\"\")";
mostCurrent._webmunicipio.LoadUrl("");
 //BA.debugLineNum = 387;BA.debugLine="If Main.strUserOrg = \"Monte\" Then";
if ((mostCurrent._main._struserorg /*String*/ ).equals("Monte")) { 
 //BA.debugLineNum = 388;BA.debugLine="imgLogoMunicipio.Bitmap = LoadBitmap(File.DirAs";
mostCurrent._imglogomunicipio.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"monte.png").getObject()));
 }else if((mostCurrent._main._struserorg /*String*/ ).equals("Mercedes")) { 
 //BA.debugLineNum = 390;BA.debugLine="imgLogoMunicipio.Bitmap = LoadBitmap(File.DirAs";
mostCurrent._imglogomunicipio.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"mercedes.jpg").getObject()));
 }else if((mostCurrent._main._struserorg /*String*/ ).equals("San Antonio de Areco")) { 
 //BA.debugLineNum = 392;BA.debugLine="imgLogoMunicipio.Bitmap = LoadBitmap(File.DirAs";
mostCurrent._imglogomunicipio.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"sanantoniodeareco.png").getObject()));
 };
 };
 //BA.debugLineNum = 396;BA.debugLine="End Sub";
return "";
}
public static String  _updateui() throws Exception{
uk.co.martinpearman.b4a.osmdroid.util.GeoPoint _geopoint1 = null;
 //BA.debugLineNum = 627;BA.debugLine="Sub UpdateUI";
 //BA.debugLineNum = 628;BA.debugLine="ToastMessageShow(\"Posición encontrada!\", True)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Posición encontrada!"),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 630;BA.debugLine="lblLat.Text = LastLocation.Latitude";
mostCurrent._lbllat.setText(BA.ObjectToCharSequence(_lastlocation.getLatitude()));
 //BA.debugLineNum = 631;BA.debugLine="lblLon.Text = LastLocation.Longitude";
mostCurrent._lbllon.setText(BA.ObjectToCharSequence(_lastlocation.getLongitude()));
 //BA.debugLineNum = 632;BA.debugLine="Dim geopoint1 As OSMDroid_GeoPoint";
_geopoint1 = new uk.co.martinpearman.b4a.osmdroid.util.GeoPoint();
 //BA.debugLineNum = 633;BA.debugLine="geopoint1.Initialize(lblLat.Text,lblLon.Text)";
_geopoint1.Initialize((double)(Double.parseDouble(mostCurrent._lbllat.getText())),(double)(Double.parseDouble(mostCurrent._lbllon.getText())));
 //BA.debugLineNum = 636;BA.debugLine="If SimpleLocationOverlay1.IsInitialized = False T";
if (mostCurrent._simplelocationoverlay1.IsInitialized()==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 637;BA.debugLine="SimpleLocationOverlay1.Initialize";
mostCurrent._simplelocationoverlay1.Initialize(processBA);
 };
 //BA.debugLineNum = 639;BA.debugLine="SimpleLocationOverlay1.SetLocation(geopoint1)";
mostCurrent._simplelocationoverlay1.SetLocation((org.osmdroid.util.GeoPoint)(_geopoint1.getObject()));
 //BA.debugLineNum = 642;BA.debugLine="MapView1.GetController.SetCenter(geopoint1)";
mostCurrent._mapview1.GetController().SetCenter((org.osmdroid.util.GeoPoint)(_geopoint1.getObject()));
 //BA.debugLineNum = 643;BA.debugLine="MapView1.GetController.SetZoom(14)";
mostCurrent._mapview1.GetController().SetZoom((int) (14));
 //BA.debugLineNum = 644;BA.debugLine="btnDetectar.Color = Colors.Transparent";
mostCurrent._btndetectar.setColor(anywheresoftware.b4a.keywords.Common.Colors.Transparent);
 //BA.debugLineNum = 645;BA.debugLine="btnDetectar.TextColor = Colors.Black";
mostCurrent._btndetectar.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 647;BA.debugLine="FusedLocationProvider1.Disconnect";
_fusedlocationprovider1.Disconnect();
 //BA.debugLineNum = 648;BA.debugLine="btnDetectar.Enabled = True";
mostCurrent._btndetectar.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 650;BA.debugLine="If fondoblanco.IsInitialized Then";
if (mostCurrent._fondoblanco.IsInitialized()) { 
 //BA.debugLineNum = 651;BA.debugLine="fondoblanco.RemoveView";
mostCurrent._fondoblanco.RemoveView();
 //BA.debugLineNum = 652;BA.debugLine="detectandoLabel.RemoveView";
mostCurrent._detectandolabel.RemoveView();
 }else {
 //BA.debugLineNum = 654;BA.debugLine="btnDetectar.Color = Colors.Black";
mostCurrent._btndetectar.setColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 655;BA.debugLine="btnDetectar.TextColor = Colors.White";
mostCurrent._btndetectar.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.White);
 };
 //BA.debugLineNum = 657;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 //BA.debugLineNum = 658;BA.debugLine="End Sub";
return "";
}
}
