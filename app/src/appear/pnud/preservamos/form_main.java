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
public static boolean _isguest = false;
public static String _fullidcurrentproject = "";
public static String _geopartido = "";
public anywheresoftware.b4a.objects.RuntimePermissions _rp = null;
public static boolean _formmainloaded = false;
public anywheresoftware.b4a.phone.Phone _p = null;
public anywheresoftware.b4a.phone.Phone.PhoneIntents _pi = null;
public anywheresoftware.b4a.objects.ProgressBarWrapper _pgbnivel = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbllevel = null;
public anywheresoftware.b4a.objects.TabStripViewPager _tabstripmain = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblanalizar = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnreportar = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblcomofuncionaanalizar = null;
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
public anywheresoftware.b4a.objects.LabelWrapper _lblmunicipiocontent = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _btnmasinfomunicipio = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imglogomunicipio = null;
public static String _municipiourl = "";
public static String _municipiofb = "";
public static String _municipioig = "";
public static String _municipioyt = "";
public static String _municipiotw = "";
public anywheresoftware.b4a.objects.ImageViewWrapper _btnmunicipiotw = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _btnmunicipiofb = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _btnmunicipioyt = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _btnmunicipioig = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbllocalizacionwhitecover = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnabrirmapa = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblline2 = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblline1 = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblline3 = null;
public b4a.example.dateutils _dateutils = null;
public appear.pnud.preservamos.main _main = null;
public appear.pnud.preservamos.starter _starter = null;
public appear.pnud.preservamos.inatcheck _inatcheck = null;
public appear.pnud.preservamos.frmlocalizacion _frmlocalizacion = null;
public appear.pnud.preservamos.reporte_envio _reporte_envio = null;
public appear.pnud.preservamos.alertas _alertas = null;
public appear.pnud.preservamos.register _register = null;
public appear.pnud.preservamos.frmeditprofile _frmeditprofile = null;
public appear.pnud.preservamos.alerta_fotos _alerta_fotos = null;
public appear.pnud.preservamos.form_reporte _form_reporte = null;
public appear.pnud.preservamos.aprender_muestreo _aprender_muestreo = null;
public appear.pnud.preservamos.dbutils _dbutils = null;
public appear.pnud.preservamos.downloadservice _downloadservice = null;
public appear.pnud.preservamos.firebasemessaging _firebasemessaging = null;
public appear.pnud.preservamos.frmabout _frmabout = null;
public appear.pnud.preservamos.frmdatosanteriores _frmdatosanteriores = null;
public appear.pnud.preservamos.frmfelicitaciones _frmfelicitaciones = null;
public appear.pnud.preservamos.frmmapa _frmmapa = null;
public appear.pnud.preservamos.frmperfil _frmperfil = null;
public appear.pnud.preservamos.frmpoliticadatos _frmpoliticadatos = null;
public appear.pnud.preservamos.httputils2service _httputils2service = null;
public appear.pnud.preservamos.imagedownloader _imagedownloader = null;
public appear.pnud.preservamos.reporte_fotos _reporte_fotos = null;
public appear.pnud.preservamos.reporte_habitat_laguna _reporte_habitat_laguna = null;
public appear.pnud.preservamos.reporte_habitat_rio _reporte_habitat_rio = null;
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
 //BA.debugLineNum = 95;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
 //BA.debugLineNum = 104;BA.debugLine="p.SetScreenOrientation(1)";
mostCurrent._p.SetScreenOrientation(processBA,(int) (1));
 //BA.debugLineNum = 105;BA.debugLine="If Main.username = \"guest\" Or Main.username = \"No";
if ((mostCurrent._main._username /*String*/ ).equals("guest") || (mostCurrent._main._username /*String*/ ).equals("None")) { 
 //BA.debugLineNum = 106;BA.debugLine="StartActivity(register)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._register.getObject()));
 };
 //BA.debugLineNum = 109;BA.debugLine="If File.Exists(rp.GetSafeDirDefaultExternal(\"\"),";
if (anywheresoftware.b4a.keywords.Common.File.Exists(mostCurrent._rp.GetSafeDirDefaultExternal(""),"PreserVamos")==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 110;BA.debugLine="File.MakeDir(rp.GetSafeDirDefaultExternal(\"\"), \"";
anywheresoftware.b4a.keywords.Common.File.MakeDir(mostCurrent._rp.GetSafeDirDefaultExternal(""),"PreserVamos");
 //BA.debugLineNum = 111;BA.debugLine="Log(\"Folder exists\")";
anywheresoftware.b4a.keywords.Common.LogImpl("5458768","Folder exists",0);
 };
 //BA.debugLineNum = 113;BA.debugLine="Starter.savedir = rp.GetSafeDirDefaultExternal(\"P";
mostCurrent._starter._savedir /*String*/  = mostCurrent._rp.GetSafeDirDefaultExternal("PreserVamos");
 //BA.debugLineNum = 115;BA.debugLine="LoadForm_Main";
_loadform_main();
 //BA.debugLineNum = 118;BA.debugLine="End Sub";
return "";
}
public static boolean  _activity_keypress(int _keycode) throws Exception{
 //BA.debugLineNum = 137;BA.debugLine="Sub Activity_KeyPress (KeyCode As Int) As Boolean";
 //BA.debugLineNum = 138;BA.debugLine="If KeyCode = KeyCodes.KEYCODE_BACK Then";
if (_keycode==anywheresoftware.b4a.keywords.Common.KeyCodes.KEYCODE_BACK) { 
 //BA.debugLineNum = 139;BA.debugLine="If Drawer.LeftOpen Then";
if (mostCurrent._drawer._getleftopen /*boolean*/ ()) { 
 //BA.debugLineNum = 140;BA.debugLine="Drawer.LeftOpen = False";
mostCurrent._drawer._setleftopen /*boolean*/ (anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 141;BA.debugLine="Return True";
if (true) return anywheresoftware.b4a.keywords.Common.True;
 }else {
 //BA.debugLineNum = 143;BA.debugLine="closeAppMsgBox";
_closeappmsgbox();
 //BA.debugLineNum = 144;BA.debugLine="Return True";
if (true) return anywheresoftware.b4a.keywords.Common.True;
 };
 }else {
 //BA.debugLineNum = 148;BA.debugLine="Return False";
if (true) return anywheresoftware.b4a.keywords.Common.False;
 };
 //BA.debugLineNum = 152;BA.debugLine="End Sub";
return false;
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 134;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 136;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 119;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 121;BA.debugLine="If Main.username = \"guest\" Or Main.username = \"No";
if ((mostCurrent._main._username /*String*/ ).equals("guest") || (mostCurrent._main._username /*String*/ ).equals("None")) { 
 }else {
 //BA.debugLineNum = 124;BA.debugLine="lblUserName.Text = Main.username";
mostCurrent._lblusername.setText(BA.ObjectToCharSequence(mostCurrent._main._username /*String*/ ));
 //BA.debugLineNum = 125;BA.debugLine="lblUserName.Visible = True";
mostCurrent._lblusername.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 126;BA.debugLine="btnCerrarSesion.Text = \"Cerrar sesión\"";
mostCurrent._btncerrarsesion.setText(BA.ObjectToCharSequence("Cerrar sesión"));
 //BA.debugLineNum = 127;BA.debugLine="lblRegistrate.Visible = False";
mostCurrent._lblregistrate.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 128;BA.debugLine="btnEditUser.Visible = True";
mostCurrent._btnedituser.setVisible(anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 133;BA.debugLine="End Sub";
return "";
}
public static String  _btnabout_click() throws Exception{
 //BA.debugLineNum = 220;BA.debugLine="Sub btnAbout_Click";
 //BA.debugLineNum = 221;BA.debugLine="StartActivity(frmAbout)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._frmabout.getObject()));
 //BA.debugLineNum = 222;BA.debugLine="End Sub";
return "";
}
public static String  _btnabririnat_click() throws Exception{
 //BA.debugLineNum = 430;BA.debugLine="Private Sub btnAbrirINat_Click";
 //BA.debugLineNum = 431;BA.debugLine="StartActivity(iNatCheck)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._inatcheck.getObject()));
 //BA.debugLineNum = 432;BA.debugLine="End Sub";
return "";
}
public static String  _btnabrirmapa_click() throws Exception{
 //BA.debugLineNum = 424;BA.debugLine="Private Sub btnAbrirMapa_Click";
 //BA.debugLineNum = 425;BA.debugLine="StartActivity(frmMapa)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._frmmapa.getObject()));
 //BA.debugLineNum = 426;BA.debugLine="End Sub";
return "";
}
public static void  _btnalerta_click() throws Exception{
ResumableSub_btnAlerta_Click rsub = new ResumableSub_btnAlerta_Click(null);
rsub.resume(processBA, null);
}
public static class ResumableSub_btnAlerta_Click extends BA.ResumableSub {
public ResumableSub_btnAlerta_Click(appear.pnud.preservamos.form_main parent) {
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
 //BA.debugLineNum = 405;BA.debugLine="Msgbox2Async(\"Podrás emitir un alerta sobre algún";
anywheresoftware.b4a.keywords.Common.Msgbox2Async(BA.ObjectToCharSequence("Podrás emitir un alerta sobre algún evento ecológico, como una floración de algas, una mortandad de peces, o un derrame industrial!"),BA.ObjectToCharSequence("¡ALERTA!"),"Proseguir!","","Cancelar alerta",(anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper(), (android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null)),processBA,anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 406;BA.debugLine="Wait For Msgbox_Result (Result As Int)";
anywheresoftware.b4a.keywords.Common.WaitFor("msgbox_result", processBA, this, null);
this.state = 5;
return;
case 5:
//C
this.state = 1;
_result = (Integer) result[0];
;
 //BA.debugLineNum = 407;BA.debugLine="If Result = DialogResponse.POSITIVE Then";
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
 //BA.debugLineNum = 408;BA.debugLine="StartActivity(Alertas)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(parent.mostCurrent._alertas.getObject()));
 if (true) break;

case 4:
//C
this.state = -1;
;
 //BA.debugLineNum = 410;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public static void  _msgbox_result(int _result) throws Exception{
}
public static String  _btnanalizar_click() throws Exception{
 //BA.debugLineNum = 301;BA.debugLine="Private Sub btnAnalizar_Click";
 //BA.debugLineNum = 302;BA.debugLine="lblLine1.Visible = False";
mostCurrent._lblline1.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 303;BA.debugLine="lblLine2.Visible = True";
mostCurrent._lblline2.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 304;BA.debugLine="lblLine3.Visible = False";
mostCurrent._lblline3.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 305;BA.debugLine="tabStripMain.ScrollTo(1, True)";
mostCurrent._tabstripmain.ScrollTo((int) (1),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 306;BA.debugLine="End Sub";
return "";
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
 //BA.debugLineNum = 207;BA.debugLine="Msgbox2Async(\"Desea cerrar la sesión? Ingresar co";
anywheresoftware.b4a.keywords.Common.Msgbox2Async(BA.ObjectToCharSequence("Desea cerrar la sesión? Ingresar con otro usuario requiere de internet!"),BA.ObjectToCharSequence("Seguro?"),"Si, tengo internet","","No, no tengo internet ahora",(anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper(), (android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null)),processBA,anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 208;BA.debugLine="Wait For Msgbox_Result (Result As Int)";
anywheresoftware.b4a.keywords.Common.WaitFor("msgbox_result", processBA, this, null);
this.state = 5;
return;
case 5:
//C
this.state = 1;
_result = (Integer) result[0];
;
 //BA.debugLineNum = 209;BA.debugLine="If Result = DialogResponse.POSITIVE Then";
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
 //BA.debugLineNum = 210;BA.debugLine="Main.strUserID = \"\"";
parent.mostCurrent._main._struserid /*String*/  = "";
 //BA.debugLineNum = 211;BA.debugLine="Main.strUserName = \"\"";
parent.mostCurrent._main._strusername /*String*/  = "";
 //BA.debugLineNum = 212;BA.debugLine="Main.strUserLocation = \"\"";
parent.mostCurrent._main._struserlocation /*String*/  = "";
 //BA.debugLineNum = 213;BA.debugLine="Main.strUserEmail = \"\"";
parent.mostCurrent._main._struseremail /*String*/  = "";
 //BA.debugLineNum = 214;BA.debugLine="Main.strUserOrg = \"\"";
parent.mostCurrent._main._struserorg /*String*/  = "";
 //BA.debugLineNum = 215;BA.debugLine="Main.username = \"\"";
parent.mostCurrent._main._username /*String*/  = "";
 //BA.debugLineNum = 216;BA.debugLine="Activity.RemoveAllViews";
parent.mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 217;BA.debugLine="Activity.finish";
parent.mostCurrent._activity.Finish();
 if (true) break;

case 4:
//C
this.state = -1;
;
 //BA.debugLineNum = 219;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public static String  _btndatosanteriores_click() throws Exception{
 //BA.debugLineNum = 238;BA.debugLine="Sub btnDatosAnteriores_Click";
 //BA.debugLineNum = 239;BA.debugLine="StartActivity(frmDatosAnteriores)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._frmdatosanteriores.getObject()));
 //BA.debugLineNum = 240;BA.debugLine="End Sub";
return "";
}
public static String  _btnedituser_click() throws Exception{
 //BA.debugLineNum = 223;BA.debugLine="Sub btnEditUser_Click";
 //BA.debugLineNum = 224;BA.debugLine="If Main.username = \"guest\" Or Main.username = \"No";
if ((mostCurrent._main._username /*String*/ ).equals("guest") || (mostCurrent._main._username /*String*/ ).equals("None") || (mostCurrent._main._username /*String*/ ).equals("")) { 
 //BA.debugLineNum = 225;BA.debugLine="ToastMessageShow(\"Necesita estar registrado para";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Necesita estar registrado para ver su perfil"),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 226;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 228;BA.debugLine="StartActivity(frmEditProfile)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._frmeditprofile.getObject()));
 //BA.debugLineNum = 230;BA.debugLine="End Sub";
return "";
}
public static String  _btninformacion_click() throws Exception{
 //BA.debugLineNum = 289;BA.debugLine="Private Sub btnInformacion_Click";
 //BA.debugLineNum = 290;BA.debugLine="lblLine1.Visible = False";
mostCurrent._lblline1.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 291;BA.debugLine="lblLine2.Visible = False";
mostCurrent._lblline2.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 292;BA.debugLine="lblLine3.Visible = True";
mostCurrent._lblline3.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 293;BA.debugLine="tabStripMain.ScrollTo(2, True)";
mostCurrent._tabstripmain.ScrollTo((int) (2),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 294;BA.debugLine="End Sub";
return "";
}
public static String  _btnmapa_click() throws Exception{
 //BA.debugLineNum = 295;BA.debugLine="Private Sub btnMapa_Click";
 //BA.debugLineNum = 296;BA.debugLine="lblLine1.Visible = True";
mostCurrent._lblline1.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 297;BA.debugLine="lblLine2.Visible = False";
mostCurrent._lblline2.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 298;BA.debugLine="lblLine3.Visible = False";
mostCurrent._lblline3.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 299;BA.debugLine="tabStripMain.ScrollTo(0, True)";
mostCurrent._tabstripmain.ScrollTo((int) (0),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 300;BA.debugLine="End Sub";
return "";
}
public static String  _btnmasinfomunicipio_click() throws Exception{
 //BA.debugLineNum = 512;BA.debugLine="Private Sub btnMasInfoMunicipio_Click";
 //BA.debugLineNum = 513;BA.debugLine="StartActivity(pi.OpenBrowser(municipioURL))";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._pi.OpenBrowser(mostCurrent._municipiourl)));
 //BA.debugLineNum = 514;BA.debugLine="End Sub";
return "";
}
public static String  _btnmenu_main_click() throws Exception{
 //BA.debugLineNum = 189;BA.debugLine="Sub btnMenu_Main_Click";
 //BA.debugLineNum = 190;BA.debugLine="Drawer.LeftOpen = True";
mostCurrent._drawer._setleftopen /*boolean*/ (anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 191;BA.debugLine="If Main.username = \"guest\" Or Main.username = \"No";
if ((mostCurrent._main._username /*String*/ ).equals("guest") || (mostCurrent._main._username /*String*/ ).equals("None") || (mostCurrent._main._username /*String*/ ).equals("")) { 
 //BA.debugLineNum = 192;BA.debugLine="btnCerrarSesion.Text = \"Registrarse!\"";
mostCurrent._btncerrarsesion.setText(BA.ObjectToCharSequence("Registrarse!"));
 }else {
 //BA.debugLineNum = 194;BA.debugLine="btnCerrarSesion.Visible = False";
mostCurrent._btncerrarsesion.setVisible(anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 196;BA.debugLine="End Sub";
return "";
}
public static String  _btnmuestreos_click() throws Exception{
 //BA.debugLineNum = 231;BA.debugLine="Sub btnMuestreos_Click";
 //BA.debugLineNum = 232;BA.debugLine="StartActivity(Aprender_Muestreo)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._aprender_muestreo.getObject()));
 //BA.debugLineNum = 233;BA.debugLine="End Sub";
return "";
}
public static String  _btnmunicipiofb_click() throws Exception{
 //BA.debugLineNum = 526;BA.debugLine="Private Sub btnMunicipioFB_Click";
 //BA.debugLineNum = 527;BA.debugLine="StartActivity(pi.OpenBrowser(municipioFB))";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._pi.OpenBrowser(mostCurrent._municipiofb)));
 //BA.debugLineNum = 528;BA.debugLine="End Sub";
return "";
}
public static String  _btnmunicipioig_click() throws Exception{
 //BA.debugLineNum = 518;BA.debugLine="Private Sub btnMunicipioIG_Click";
 //BA.debugLineNum = 519;BA.debugLine="StartActivity(pi.OpenBrowser(municipioIG))";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._pi.OpenBrowser(mostCurrent._municipioig)));
 //BA.debugLineNum = 520;BA.debugLine="End Sub";
return "";
}
public static String  _btnmunicipiotw_click() throws Exception{
 //BA.debugLineNum = 530;BA.debugLine="Private Sub btnMunicipioTW_Click";
 //BA.debugLineNum = 531;BA.debugLine="StartActivity(pi.OpenBrowser(municipioTW))";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._pi.OpenBrowser(mostCurrent._municipiotw)));
 //BA.debugLineNum = 532;BA.debugLine="End Sub";
return "";
}
public static String  _btnmunicipioyt_click() throws Exception{
 //BA.debugLineNum = 522;BA.debugLine="Private Sub btnMunicipioYT_Click";
 //BA.debugLineNum = 523;BA.debugLine="StartActivity(pi.OpenBrowser(municipioYT))";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._pi.OpenBrowser(mostCurrent._municipioyt)));
 //BA.debugLineNum = 524;BA.debugLine="End Sub";
return "";
}
public static String  _btnpoliticadatos_click() throws Exception{
 //BA.debugLineNum = 234;BA.debugLine="Sub btnPoliticaDatos_Click";
 //BA.debugLineNum = 235;BA.debugLine="StartActivity(frmPoliticaDatos)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._frmpoliticadatos.getObject()));
 //BA.debugLineNum = 237;BA.debugLine="End Sub";
return "";
}
public static String  _btnreportar_click() throws Exception{
String _rndstr = "";
 //BA.debugLineNum = 351;BA.debugLine="Sub btnReportar_Click";
 //BA.debugLineNum = 354;BA.debugLine="If Main.username = \"guest\" Or Main.username = \"No";
if ((mostCurrent._main._username /*String*/ ).equals("guest") || (mostCurrent._main._username /*String*/ ).equals("None") || (mostCurrent._main._username /*String*/ ).equals("")) { 
 //BA.debugLineNum = 355;BA.debugLine="Dim RndStr As String";
_rndstr = "";
 //BA.debugLineNum = 356;BA.debugLine="RndStr = utilidades.RandomString(6)";
_rndstr = mostCurrent._utilidades._randomstring /*String*/ (mostCurrent.activityBA,(int) (6));
 //BA.debugLineNum = 357;BA.debugLine="Main.username = \"guest_\" & RndStr";
mostCurrent._main._username /*String*/  = "guest_"+_rndstr;
 }else {
 //BA.debugLineNum = 373;BA.debugLine="Form_Reporte.origen = \"Form_Main\"";
mostCurrent._form_reporte._origen /*String*/  = "Form_Main";
 //BA.debugLineNum = 374;BA.debugLine="StartActivity(Form_Reporte)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._form_reporte.getObject()));
 };
 //BA.debugLineNum = 377;BA.debugLine="End Sub";
return "";
}
public static String  _btnverperfil_click() throws Exception{
 //BA.debugLineNum = 198;BA.debugLine="Sub btnVerPerfil_Click";
 //BA.debugLineNum = 199;BA.debugLine="If Main.username = \"guest\" Or Main.username = \"No";
if ((mostCurrent._main._username /*String*/ ).equals("guest") || (mostCurrent._main._username /*String*/ ).equals("None") || (mostCurrent._main._username /*String*/ ).equals("")) { 
 //BA.debugLineNum = 200;BA.debugLine="ToastMessageShow(\"Necesita estar registrado para";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Necesita estar registrado para ver su perfil"),anywheresoftware.b4a.keywords.Common.False);
 }else {
 //BA.debugLineNum = 202;BA.debugLine="StartActivity(frmEditProfile)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._frmeditprofile.getObject()));
 };
 //BA.debugLineNum = 204;BA.debugLine="End Sub";
return "";
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
if ((mostCurrent._main._username /*String*/ ).equals("guest") || (mostCurrent._main._username /*String*/ ).equals("None") || (mostCurrent._main._username /*String*/ ).equals("")) { 
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
public static String  _fondogris_click() throws Exception{
 //BA.debugLineNum = 399;BA.debugLine="Private Sub fondogris_Click";
 //BA.debugLineNum = 400;BA.debugLine="panelComoFunciona.RemoveView";
mostCurrent._panelcomofunciona.RemoveView();
 //BA.debugLineNum = 401;BA.debugLine="fondogris.RemoveView";
mostCurrent._fondogris.RemoveView();
 //BA.debugLineNum = 402;BA.debugLine="End Sub";
return "";
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 14;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 15;BA.debugLine="Dim rp As RuntimePermissions";
mostCurrent._rp = new anywheresoftware.b4a.objects.RuntimePermissions();
 //BA.debugLineNum = 17;BA.debugLine="Dim FormMainloaded As Boolean = False";
_formmainloaded = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 18;BA.debugLine="Dim p As Phone";
mostCurrent._p = new anywheresoftware.b4a.phone.Phone();
 //BA.debugLineNum = 19;BA.debugLine="Dim pi As PhoneIntents";
mostCurrent._pi = new anywheresoftware.b4a.phone.Phone.PhoneIntents();
 //BA.debugLineNum = 23;BA.debugLine="Private pgbNivel As ProgressBar";
mostCurrent._pgbnivel = new anywheresoftware.b4a.objects.ProgressBarWrapper();
 //BA.debugLineNum = 24;BA.debugLine="Private lblLevel As Label";
mostCurrent._lbllevel = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 27;BA.debugLine="Private tabStripMain As TabStrip";
mostCurrent._tabstripmain = new anywheresoftware.b4a.objects.TabStripViewPager();
 //BA.debugLineNum = 30;BA.debugLine="Private lblAnalizar As Label";
mostCurrent._lblanalizar = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 31;BA.debugLine="Private btnReportar As Button";
mostCurrent._btnreportar = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 32;BA.debugLine="Private lblComoFuncionaAnalizar As Label";
mostCurrent._lblcomofuncionaanalizar = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 36;BA.debugLine="Private btnDetectar As Button";
mostCurrent._btndetectar = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 37;BA.debugLine="Private lblLat As Label";
mostCurrent._lbllat = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 38;BA.debugLine="Private lblLon As Label";
mostCurrent._lbllon = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 39;BA.debugLine="Private btnZoomAll As Button";
mostCurrent._btnzoomall = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 40;BA.debugLine="Dim MarkerExport As List";
mostCurrent._markerexport = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 42;BA.debugLine="Dim MarkerInfo As Label";
mostCurrent._markerinfo = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 43;BA.debugLine="Private pnlMapa As Panel";
mostCurrent._pnlmapa = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 44;BA.debugLine="Dim fondoblanco As Label";
mostCurrent._fondoblanco = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 45;BA.debugLine="Dim detectandoLabel As Label";
mostCurrent._detectandolabel = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 49;BA.debugLine="Dim firstuse As String";
mostCurrent._firstuse = "";
 //BA.debugLineNum = 50;BA.debugLine="Dim fondogris As Panel";
mostCurrent._fondogris = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 51;BA.debugLine="Dim panelComoFunciona As Panel";
mostCurrent._panelcomofunciona = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 53;BA.debugLine="Dim tutorialEtapa As Int";
_tutorialetapa = 0;
 //BA.debugLineNum = 54;BA.debugLine="Private btnMenu_Main As Label";
mostCurrent._btnmenu_main = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 58;BA.debugLine="Dim Drawer As B4XDrawer";
mostCurrent._drawer = new appear.pnud.preservamos.b4xdrawer();
 //BA.debugLineNum = 59;BA.debugLine="Private btnCerrarSesion As Label";
mostCurrent._btncerrarsesion = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 60;BA.debugLine="Private btnEditUser As Label";
mostCurrent._btnedituser = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 61;BA.debugLine="Private btnverMedallas As Label";
mostCurrent._btnvermedallas = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 62;BA.debugLine="Private lblUserName As Label";
mostCurrent._lblusername = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 63;BA.debugLine="Private lblRegistrate As Label";
mostCurrent._lblregistrate = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 64;BA.debugLine="Private btnVerPerfil As Label";
mostCurrent._btnverperfil = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 65;BA.debugLine="Private btnAbout As Label";
mostCurrent._btnabout = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 66;BA.debugLine="Private btnPoliticaDatos As Label";
mostCurrent._btnpoliticadatos = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 67;BA.debugLine="Private btnDatosAnteriores As Label";
mostCurrent._btndatosanteriores = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 68;BA.debugLine="Private btnMuestreos As Label";
mostCurrent._btnmuestreos = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 72;BA.debugLine="Private lblTitleMunicipio As Label";
mostCurrent._lbltitlemunicipio = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 73;BA.debugLine="Private WebView1 As WebView";
mostCurrent._webview1 = new anywheresoftware.b4a.objects.WebViewWrapper();
 //BA.debugLineNum = 74;BA.debugLine="Private lblMunicipioContent As Label";
mostCurrent._lblmunicipiocontent = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 75;BA.debugLine="Private btnMasInfoMunicipio As ImageView";
mostCurrent._btnmasinfomunicipio = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 76;BA.debugLine="Private imgLogoMunicipio As ImageView";
mostCurrent._imglogomunicipio = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 77;BA.debugLine="Dim municipioURL As String";
mostCurrent._municipiourl = "";
 //BA.debugLineNum = 78;BA.debugLine="Dim municipioFB As String";
mostCurrent._municipiofb = "";
 //BA.debugLineNum = 79;BA.debugLine="Dim municipioIG As String";
mostCurrent._municipioig = "";
 //BA.debugLineNum = 80;BA.debugLine="Dim municipioYT As String";
mostCurrent._municipioyt = "";
 //BA.debugLineNum = 81;BA.debugLine="Dim municipioTW As String";
mostCurrent._municipiotw = "";
 //BA.debugLineNum = 82;BA.debugLine="Private btnMunicipioTW As ImageView";
mostCurrent._btnmunicipiotw = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 83;BA.debugLine="Private btnMunicipioFB As ImageView";
mostCurrent._btnmunicipiofb = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 84;BA.debugLine="Private btnMunicipioYT As ImageView";
mostCurrent._btnmunicipioyt = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 85;BA.debugLine="Private btnMunicipioIG As ImageView";
mostCurrent._btnmunicipioig = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 87;BA.debugLine="Private lblLocalizacionWhiteCover As Label";
mostCurrent._lbllocalizacionwhitecover = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 88;BA.debugLine="Private btnAbrirMapa As Button";
mostCurrent._btnabrirmapa = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 90;BA.debugLine="Private lblLine2 As Label";
mostCurrent._lblline2 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 91;BA.debugLine="Private lblLine1 As Label";
mostCurrent._lblline1 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 92;BA.debugLine="Private lblLine3 As Label";
mostCurrent._lblline3 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 93;BA.debugLine="End Sub";
return "";
}
public static String  _gotoinformacion() throws Exception{
 //BA.debugLineNum = 308;BA.debugLine="Sub gotoInformacion";
 //BA.debugLineNum = 309;BA.debugLine="tabStripMain.ScrollTo(2, True)";
mostCurrent._tabstripmain.ScrollTo((int) (2),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 310;BA.debugLine="lblLine1.Visible = False";
mostCurrent._lblline1.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 311;BA.debugLine="lblLine2.Visible = False";
mostCurrent._lblline2.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 312;BA.debugLine="lblLine3.Visible = True";
mostCurrent._lblline3.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 313;BA.debugLine="End Sub";
return "";
}
public static void  _lblcambiarmunicipio_click() throws Exception{
ResumableSub_lblCambiarMunicipio_Click rsub = new ResumableSub_lblCambiarMunicipio_Click(null);
rsub.resume(processBA, null);
}
public static class ResumableSub_lblCambiarMunicipio_Click extends BA.ResumableSub {
public ResumableSub_lblCambiarMunicipio_Click(appear.pnud.preservamos.form_main parent) {
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
 //BA.debugLineNum = 503;BA.debugLine="Main.strUserOrg = \"\"";
parent.mostCurrent._main._struserorg /*String*/  = "";
 //BA.debugLineNum = 504;BA.debugLine="Msgbox2Async(\"¿Deseas cambiar de municipio selecc";
anywheresoftware.b4a.keywords.Common.Msgbox2Async(BA.ObjectToCharSequence("¿Deseas cambiar de municipio seleccionado?"),BA.ObjectToCharSequence("¿De acuerdo?"),"Si, cambiar!","","Cancelar",(anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper(), (android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null)),processBA,anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 505;BA.debugLine="Wait For Msgbox_Result (Result As Int)";
anywheresoftware.b4a.keywords.Common.WaitFor("msgbox_result", processBA, this, null);
this.state = 5;
return;
case 5:
//C
this.state = 1;
_result = (Integer) result[0];
;
 //BA.debugLineNum = 506;BA.debugLine="If Result = DialogResponse.POSITIVE Then";
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
 //BA.debugLineNum = 507;BA.debugLine="frmLocalizacion.origen = \"main\"";
parent.mostCurrent._frmlocalizacion._origen /*String*/  = "main";
 //BA.debugLineNum = 508;BA.debugLine="StartActivity(frmLocalizacion)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(parent.mostCurrent._frmlocalizacion.getObject()));
 if (true) break;

case 4:
//C
this.state = -1;
;
 //BA.debugLineNum = 510;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public static String  _lblcomofuncionaanalizar_click() throws Exception{
 //BA.debugLineNum = 379;BA.debugLine="Sub lblComoFuncionaAnalizar_Click";
 //BA.debugLineNum = 382;BA.debugLine="fondogris.Initialize(\"fondogris\")";
mostCurrent._fondogris.Initialize(mostCurrent.activityBA,"fondogris");
 //BA.debugLineNum = 383;BA.debugLine="fondogris.Color = Colors.ARGB(150,0,0,0)";
mostCurrent._fondogris.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (150),(int) (0),(int) (0),(int) (0)));
 //BA.debugLineNum = 384;BA.debugLine="Activity.AddView(fondogris, 0, 0, 100%x, 100%y)";
mostCurrent._activity.AddView((android.view.View)(mostCurrent._fondogris.getObject()),(int) (0),(int) (0),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA));
 //BA.debugLineNum = 386;BA.debugLine="panelComoFunciona.Initialize(\"\")";
mostCurrent._panelcomofunciona.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 387;BA.debugLine="panelComoFunciona.LoadLayout(\"dialog_comoFunciona";
mostCurrent._panelcomofunciona.LoadLayout("dialog_comoFunciona",mostCurrent.activityBA);
 //BA.debugLineNum = 388;BA.debugLine="Activity.AddView(panelComoFunciona, 10%x, 20%y, 8";
mostCurrent._activity.AddView((android.view.View)(mostCurrent._panelcomofunciona.getObject()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (10),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (20),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (80),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (80),mostCurrent.activityBA));
 //BA.debugLineNum = 391;BA.debugLine="End Sub";
return "";
}
public static String  _lblcomofuncionaentendido_click() throws Exception{
 //BA.debugLineNum = 394;BA.debugLine="Private Sub lblComoFuncionaEntendido_Click";
 //BA.debugLineNum = 395;BA.debugLine="panelComoFunciona.RemoveView";
mostCurrent._panelcomofunciona.RemoveView();
 //BA.debugLineNum = 396;BA.debugLine="fondogris.RemoveView";
mostCurrent._fondogris.RemoveView();
 //BA.debugLineNum = 397;BA.debugLine="End Sub";
return "";
}
public static String  _loadform_main() throws Exception{
 //BA.debugLineNum = 263;BA.debugLine="Sub LoadForm_Main";
 //BA.debugLineNum = 264;BA.debugLine="Log(\"userName in LoadForm_Main:\" & Main.username)";
anywheresoftware.b4a.keywords.Common.LogImpl("523527425","userName in LoadForm_Main:"+mostCurrent._main._username /*String*/ ,0);
 //BA.debugLineNum = 265;BA.debugLine="Log(\"firstuse in LoadForm_Main:\" & firstuse)";
anywheresoftware.b4a.keywords.Common.LogImpl("523527426","firstuse in LoadForm_Main:"+mostCurrent._firstuse,0);
 //BA.debugLineNum = 268;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 269;BA.debugLine="Activity.LoadLayout(\"layMain\")";
mostCurrent._activity.LoadLayout("layMain",mostCurrent.activityBA);
 //BA.debugLineNum = 272;BA.debugLine="CrearMenu";
_crearmenu();
 //BA.debugLineNum = 275;BA.debugLine="tabStripMain.LoadLayout(\"layMainExplorar\", \"\")";
mostCurrent._tabstripmain.LoadLayout("layMainExplorar",BA.ObjectToCharSequence(""));
 //BA.debugLineNum = 276;BA.debugLine="tabStripMain.LoadLayout(\"layMainReportar\", \"\")";
mostCurrent._tabstripmain.LoadLayout("layMainReportar",BA.ObjectToCharSequence(""));
 //BA.debugLineNum = 277;BA.debugLine="tabStripMain.LoadLayout(\"layMainMunicipio\", \"\")";
mostCurrent._tabstripmain.LoadLayout("layMainMunicipio",BA.ObjectToCharSequence(""));
 //BA.debugLineNum = 278;BA.debugLine="tabStripMain.ScrollTo(1,False)";
mostCurrent._tabstripmain.ScrollTo((int) (1),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 280;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 6;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 9;BA.debugLine="Dim IsGuest As Boolean";
_isguest = false;
 //BA.debugLineNum = 10;BA.debugLine="Dim fullidcurrentproject As String";
_fullidcurrentproject = "";
 //BA.debugLineNum = 11;BA.debugLine="Dim Geopartido As String";
_geopartido = "";
 //BA.debugLineNum = 12;BA.debugLine="End Sub";
return "";
}
public static void  _tabstripmain_pageselected(int _position) throws Exception{
ResumableSub_tabStripMain_PageSelected rsub = new ResumableSub_tabStripMain_PageSelected(null,_position);
rsub.resume(processBA, null);
}
public static class ResumableSub_tabStripMain_PageSelected extends BA.ResumableSub {
public ResumableSub_tabStripMain_PageSelected(appear.pnud.preservamos.form_main parent,int _position) {
this.parent = parent;
this._position = _position;
}
appear.pnud.preservamos.form_main parent;
int _position;
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
 //BA.debugLineNum = 315;BA.debugLine="If Position = 0 Then";
if (true) break;

case 1:
//if
this.state = 20;
if (_position==0) { 
this.state = 3;
}else if(_position==1) { 
this.state = 5;
}else if(_position==2) { 
this.state = 7;
}if (true) break;

case 3:
//C
this.state = 20;
 //BA.debugLineNum = 316;BA.debugLine="lblLine1.Visible = True";
parent.mostCurrent._lblline1.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 317;BA.debugLine="lblLine2.Visible = False";
parent.mostCurrent._lblline2.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 318;BA.debugLine="lblLine3.Visible = False";
parent.mostCurrent._lblline3.setVisible(anywheresoftware.b4a.keywords.Common.False);
 if (true) break;

case 5:
//C
this.state = 20;
 //BA.debugLineNum = 320;BA.debugLine="lblLine1.Visible = False";
parent.mostCurrent._lblline1.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 321;BA.debugLine="lblLine2.Visible = True";
parent.mostCurrent._lblline2.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 322;BA.debugLine="lblLine3.Visible = False";
parent.mostCurrent._lblline3.setVisible(anywheresoftware.b4a.keywords.Common.False);
 if (true) break;

case 7:
//C
this.state = 8;
 //BA.debugLineNum = 325;BA.debugLine="If Main.strUserOrg = \"\" Then";
if (true) break;

case 8:
//if
this.state = 19;
if ((parent.mostCurrent._main._struserorg /*String*/ ).equals("")) { 
this.state = 10;
}else {
this.state = 18;
}if (true) break;

case 10:
//C
this.state = 11;
 //BA.debugLineNum = 326;BA.debugLine="imgLogoMunicipio.Visible = False";
parent.mostCurrent._imglogomunicipio.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 327;BA.debugLine="Msgbox2Async(\"Para conocer información sobre es";
anywheresoftware.b4a.keywords.Common.Msgbox2Async(BA.ObjectToCharSequence("Para conocer información sobre este municipio, necesitamos conocer tu ubicación"),BA.ObjectToCharSequence("¿De acuerdo?"),"Proseguir!","","Cancelar",(anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper(), (android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null)),processBA,anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 328;BA.debugLine="Wait For Msgbox_Result (Result As Int)";
anywheresoftware.b4a.keywords.Common.WaitFor("msgbox_result", processBA, this, null);
this.state = 21;
return;
case 21:
//C
this.state = 11;
_result = (Integer) result[0];
;
 //BA.debugLineNum = 329;BA.debugLine="If Result = DialogResponse.POSITIVE Then";
if (true) break;

case 11:
//if
this.state = 16;
if (_result==anywheresoftware.b4a.keywords.Common.DialogResponse.POSITIVE) { 
this.state = 13;
}else {
this.state = 15;
}if (true) break;

case 13:
//C
this.state = 16;
 //BA.debugLineNum = 330;BA.debugLine="frmLocalizacion.origen = \"main\"";
parent.mostCurrent._frmlocalizacion._origen /*String*/  = "main";
 //BA.debugLineNum = 331;BA.debugLine="StartActivity(frmLocalizacion)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(parent.mostCurrent._frmlocalizacion.getObject()));
 if (true) break;

case 15:
//C
this.state = 16;
 //BA.debugLineNum = 333;BA.debugLine="tabStripMain.ScrollTo(1,True)";
parent.mostCurrent._tabstripmain.ScrollTo((int) (1),anywheresoftware.b4a.keywords.Common.True);
 if (true) break;

case 16:
//C
this.state = 19;
;
 if (true) break;

case 18:
//C
this.state = 19;
 //BA.debugLineNum = 336;BA.debugLine="update_Municipio";
_update_municipio();
 if (true) break;

case 19:
//C
this.state = 20;
;
 //BA.debugLineNum = 338;BA.debugLine="lblLine1.Visible = False";
parent.mostCurrent._lblline1.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 339;BA.debugLine="lblLine2.Visible = False";
parent.mostCurrent._lblline2.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 340;BA.debugLine="lblLine3.Visible = True";
parent.mostCurrent._lblline3.setVisible(anywheresoftware.b4a.keywords.Common.True);
 if (true) break;

case 20:
//C
this.state = -1;
;
 //BA.debugLineNum = 342;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public static String  _update_municipio() throws Exception{
 //BA.debugLineNum = 439;BA.debugLine="Sub update_Municipio";
 //BA.debugLineNum = 440;BA.debugLine="lblTitleMunicipio.Text = Main.strUserOrg";
mostCurrent._lbltitlemunicipio.setText(BA.ObjectToCharSequence(mostCurrent._main._struserorg /*String*/ ));
 //BA.debugLineNum = 441;BA.debugLine="imgLogoMunicipio.Visible = True";
mostCurrent._imglogomunicipio.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 443;BA.debugLine="If Main.strUserOrg = \"Balcarce\" Then";
if ((mostCurrent._main._struserorg /*String*/ ).equals("Balcarce")) { 
 //BA.debugLineNum = 444;BA.debugLine="imgLogoMunicipio.Bitmap = LoadBitmap(File.DirAss";
mostCurrent._imglogomunicipio.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"balcarce.png").getObject()));
 //BA.debugLineNum = 445;BA.debugLine="imgLogoMunicipio.Height = 70dip";
mostCurrent._imglogomunicipio.setHeight(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (70)));
 //BA.debugLineNum = 446;BA.debugLine="imgLogoMunicipio.Width = 70dip";
mostCurrent._imglogomunicipio.setWidth(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (70)));
 //BA.debugLineNum = 448;BA.debugLine="WebView1.Visible = True";
mostCurrent._webview1.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 449;BA.debugLine="lblMunicipioContent.Visible = False";
mostCurrent._lblmunicipiocontent.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 450;BA.debugLine="municipioURL = \"https://www.balcarce.gob.ar\"";
mostCurrent._municipiourl = "https://www.balcarce.gob.ar";
 //BA.debugLineNum = 451;BA.debugLine="btnMasInfoMunicipio.visible = True";
mostCurrent._btnmasinfomunicipio.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 452;BA.debugLine="btnMunicipioFB.Visible = False";
mostCurrent._btnmunicipiofb.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 453;BA.debugLine="btnMunicipioYT.Visible = False";
mostCurrent._btnmunicipioyt.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 454;BA.debugLine="btnMunicipioIG.Visible = False";
mostCurrent._btnmunicipioig.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 455;BA.debugLine="btnMunicipioTW.Visible = False";
mostCurrent._btnmunicipiotw.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 456;BA.debugLine="imgLogoMunicipio.Visible= True";
mostCurrent._imglogomunicipio.setVisible(anywheresoftware.b4a.keywords.Common.True);
 }else if((mostCurrent._main._struserorg /*String*/ ).equals("Mercedes")) { 
 //BA.debugLineNum = 458;BA.debugLine="imgLogoMunicipio.Bitmap = LoadBitmap(File.DirAss";
mostCurrent._imglogomunicipio.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"mercedes.jpg").getObject()));
 //BA.debugLineNum = 459;BA.debugLine="imgLogoMunicipio.Height = 70dip";
mostCurrent._imglogomunicipio.setHeight(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (70)));
 //BA.debugLineNum = 460;BA.debugLine="imgLogoMunicipio.Width = 120dip";
mostCurrent._imglogomunicipio.setWidth(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (120)));
 //BA.debugLineNum = 461;BA.debugLine="WebView1.loadhtml(File.ReadString(File.DirAssets";
mostCurrent._webview1.LoadHtml(anywheresoftware.b4a.keywords.Common.File.ReadString(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"mercedes.html"));
 //BA.debugLineNum = 462;BA.debugLine="WebView1.Visible = True";
mostCurrent._webview1.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 463;BA.debugLine="lblMunicipioContent.Visible = False";
mostCurrent._lblmunicipiocontent.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 464;BA.debugLine="municipioURL = \"https://nw.mercedes.gob.ar\"";
mostCurrent._municipiourl = "https://nw.mercedes.gob.ar";
 //BA.debugLineNum = 465;BA.debugLine="btnMasInfoMunicipio.visible = True";
mostCurrent._btnmasinfomunicipio.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 466;BA.debugLine="btnMunicipioFB.Visible = True";
mostCurrent._btnmunicipiofb.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 467;BA.debugLine="btnMunicipioYT.Visible = True";
mostCurrent._btnmunicipioyt.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 468;BA.debugLine="btnMunicipioIG.Visible = True";
mostCurrent._btnmunicipioig.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 469;BA.debugLine="municipioYT = \"https://www.youtube.com/channel/U";
mostCurrent._municipioyt = "https://www.youtube.com/channel/UCy6RSyBaOtPCYCnbKJyYtng/videos";
 //BA.debugLineNum = 470;BA.debugLine="municipioIG = \"https://www.instagram.com/mercede";
mostCurrent._municipioig = "https://www.instagram.com/mercedes_sustentable";
 //BA.debugLineNum = 471;BA.debugLine="municipioFB = \"https://www.facebook.com/Mercedes";
mostCurrent._municipiofb = "https://www.facebook.com/Mercedes.Sustentable";
 //BA.debugLineNum = 472;BA.debugLine="btnMunicipioTW.Visible = False";
mostCurrent._btnmunicipiotw.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 473;BA.debugLine="imgLogoMunicipio.Visible= True";
mostCurrent._imglogomunicipio.setVisible(anywheresoftware.b4a.keywords.Common.True);
 }else if((mostCurrent._main._struserorg /*String*/ ).equals("San Antonio de Areco") || (mostCurrent._main._struserorg /*String*/ ).equals("San Antonio De Areco")) { 
 //BA.debugLineNum = 475;BA.debugLine="imgLogoMunicipio.Bitmap = LoadBitmap(File.DirAss";
mostCurrent._imglogomunicipio.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"sanantoniodeareco.png").getObject()));
 //BA.debugLineNum = 476;BA.debugLine="imgLogoMunicipio.Height = 70dip";
mostCurrent._imglogomunicipio.setHeight(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (70)));
 //BA.debugLineNum = 477;BA.debugLine="imgLogoMunicipio.Width = 70dip";
mostCurrent._imglogomunicipio.setWidth(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (70)));
 //BA.debugLineNum = 478;BA.debugLine="WebView1.loadhtml(File.ReadString(File.DirAssets";
mostCurrent._webview1.LoadHtml(anywheresoftware.b4a.keywords.Common.File.ReadString(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"areco.html"));
 //BA.debugLineNum = 479;BA.debugLine="WebView1.Visible = True";
mostCurrent._webview1.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 480;BA.debugLine="lblMunicipioContent.Visible = False";
mostCurrent._lblmunicipiocontent.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 481;BA.debugLine="municipioURL = \"https://www.areco.gob.ar\"";
mostCurrent._municipiourl = "https://www.areco.gob.ar";
 //BA.debugLineNum = 482;BA.debugLine="btnMasInfoMunicipio.visible = True";
mostCurrent._btnmasinfomunicipio.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 483;BA.debugLine="btnMunicipioFB.Visible = False";
mostCurrent._btnmunicipiofb.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 484;BA.debugLine="btnMunicipioYT.Visible = False";
mostCurrent._btnmunicipioyt.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 485;BA.debugLine="btnMunicipioIG.Visible = False";
mostCurrent._btnmunicipioig.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 486;BA.debugLine="btnMunicipioTW.Visible = False";
mostCurrent._btnmunicipiotw.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 487;BA.debugLine="imgLogoMunicipio.Visible= True";
mostCurrent._imglogomunicipio.setVisible(anywheresoftware.b4a.keywords.Common.True);
 }else {
 //BA.debugLineNum = 489;BA.debugLine="imgLogoMunicipio.Visible = False";
mostCurrent._imglogomunicipio.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 490;BA.debugLine="lblTitleMunicipio. Text = Main.strUserOrg";
mostCurrent._lbltitlemunicipio.setText(BA.ObjectToCharSequence(mostCurrent._main._struserorg /*String*/ ));
 //BA.debugLineNum = 491;BA.debugLine="WebView1.Visible = False";
mostCurrent._webview1.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 492;BA.debugLine="lblMunicipioContent.Visible = True";
mostCurrent._lblmunicipiocontent.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 493;BA.debugLine="btnMunicipioFB.Visible = False";
mostCurrent._btnmunicipiofb.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 494;BA.debugLine="btnMunicipioYT.Visible = False";
mostCurrent._btnmunicipioyt.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 495;BA.debugLine="btnMunicipioIG.Visible = False";
mostCurrent._btnmunicipioig.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 496;BA.debugLine="btnMunicipioTW.Visible = False";
mostCurrent._btnmunicipiotw.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 497;BA.debugLine="btnMasInfoMunicipio.Visible = False";
mostCurrent._btnmasinfomunicipio.setVisible(anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 499;BA.debugLine="End Sub";
return "";
}
}
