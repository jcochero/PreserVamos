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
public anywheresoftware.b4a.objects.LabelWrapper _lblline2 = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblline1 = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblline3 = null;
public anywheresoftware.b4a.objects.MapFragmentWrapper.GoogleMapWrapper _gmap = null;
public anywheresoftware.b4a.objects.MapFragmentWrapper _mapfragment1 = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblinstruccioneslocalizacion = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btncontinuarlocalizacion = null;
public anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper _markerred = null;
public anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper _markerorange = null;
public anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper _markeryellow = null;
public anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper _markergreen = null;
public anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper _markerblue = null;
public anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper _markergray = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _btnmunicipiotw = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _btnmunicipiofb = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _btnmunicipioyt = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _btnmunicipioig = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbllocalizacionwhitecover = null;
public b4a.example.dateutils _dateutils = null;
public appear.pnud.preservamos.main _main = null;
public appear.pnud.preservamos.reporte_fotos _reporte_fotos = null;
public appear.pnud.preservamos.dbutils _dbutils = null;
public appear.pnud.preservamos.frmlocalizacion _frmlocalizacion = null;
public appear.pnud.preservamos.reporte_habitat_laguna _reporte_habitat_laguna = null;
public appear.pnud.preservamos.reporte_habitat_rio _reporte_habitat_rio = null;
public appear.pnud.preservamos.aprender_muestreo _aprender_muestreo = null;
public appear.pnud.preservamos.downloadservice _downloadservice = null;
public appear.pnud.preservamos.firebasemessaging _firebasemessaging = null;
public appear.pnud.preservamos.form_reporte _form_reporte = null;
public appear.pnud.preservamos.frmabout _frmabout = null;
public appear.pnud.preservamos.frmdatosanteriores _frmdatosanteriores = null;
public appear.pnud.preservamos.frmeditprofile _frmeditprofile = null;
public appear.pnud.preservamos.frmfelicitaciones _frmfelicitaciones = null;
public appear.pnud.preservamos.frmperfil _frmperfil = null;
public appear.pnud.preservamos.frmpoliticadatos _frmpoliticadatos = null;
public appear.pnud.preservamos.httputils2service _httputils2service = null;
public appear.pnud.preservamos.register _register = null;
public appear.pnud.preservamos.reporte_envio _reporte_envio = null;
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
 //BA.debugLineNum = 112;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
 //BA.debugLineNum = 121;BA.debugLine="p.SetScreenOrientation(1)";
mostCurrent._p.SetScreenOrientation(processBA,(int) (1));
 //BA.debugLineNum = 122;BA.debugLine="If Main.username = \"guest\" Or Main.username = \"No";
if ((mostCurrent._main._username /*String*/ ).equals("guest") || (mostCurrent._main._username /*String*/ ).equals("None")) { 
 //BA.debugLineNum = 123;BA.debugLine="StartActivity(register)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._register.getObject()));
 };
 //BA.debugLineNum = 126;BA.debugLine="LoadForm_Main";
_loadform_main();
 //BA.debugLineNum = 129;BA.debugLine="End Sub";
return "";
}
public static boolean  _activity_keypress(int _keycode) throws Exception{
 //BA.debugLineNum = 148;BA.debugLine="Sub Activity_KeyPress (KeyCode As Int) As Boolean";
 //BA.debugLineNum = 149;BA.debugLine="If KeyCode = KeyCodes.KEYCODE_BACK Then";
if (_keycode==anywheresoftware.b4a.keywords.Common.KeyCodes.KEYCODE_BACK) { 
 //BA.debugLineNum = 150;BA.debugLine="If Drawer.LeftOpen Then";
if (mostCurrent._drawer._getleftopen /*boolean*/ ()) { 
 //BA.debugLineNum = 151;BA.debugLine="Drawer.LeftOpen = False";
mostCurrent._drawer._setleftopen /*boolean*/ (anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 152;BA.debugLine="Return True";
if (true) return anywheresoftware.b4a.keywords.Common.True;
 }else {
 //BA.debugLineNum = 154;BA.debugLine="closeAppMsgBox";
_closeappmsgbox();
 //BA.debugLineNum = 155;BA.debugLine="Return True";
if (true) return anywheresoftware.b4a.keywords.Common.True;
 };
 }else {
 //BA.debugLineNum = 159;BA.debugLine="Return False";
if (true) return anywheresoftware.b4a.keywords.Common.False;
 };
 //BA.debugLineNum = 163;BA.debugLine="End Sub";
return false;
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 145;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 147;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 130;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 132;BA.debugLine="If Main.username = \"guest\" Or Main.username = \"No";
if ((mostCurrent._main._username /*String*/ ).equals("guest") || (mostCurrent._main._username /*String*/ ).equals("None")) { 
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
 //BA.debugLineNum = 144;BA.debugLine="End Sub";
return "";
}
public static String  _btnabout_click() throws Exception{
 //BA.debugLineNum = 235;BA.debugLine="Sub btnAbout_Click";
 //BA.debugLineNum = 236;BA.debugLine="StartActivity(frmAbout)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._frmabout.getObject()));
 //BA.debugLineNum = 237;BA.debugLine="End Sub";
return "";
}
public static String  _btnanalizar_click() throws Exception{
 //BA.debugLineNum = 326;BA.debugLine="Private Sub btnAnalizar_Click";
 //BA.debugLineNum = 327;BA.debugLine="lblLine1.Visible = False";
mostCurrent._lblline1.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 328;BA.debugLine="lblLine2.Visible = True";
mostCurrent._lblline2.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 329;BA.debugLine="lblLine3.Visible = False";
mostCurrent._lblline3.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 330;BA.debugLine="tabStripMain.ScrollTo(1, True)";
mostCurrent._tabstripmain.ScrollTo((int) (1),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 331;BA.debugLine="End Sub";
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
 //BA.debugLineNum = 222;BA.debugLine="Msgbox2Async(\"Desea cerrar la sesión? Ingresar co";
anywheresoftware.b4a.keywords.Common.Msgbox2Async(BA.ObjectToCharSequence("Desea cerrar la sesión? Ingresar con otro usuario requiere de internet!"),BA.ObjectToCharSequence("Seguro?"),"Si, tengo internet","","No, no tengo internet ahora",(anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper(), (android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null)),processBA,anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 223;BA.debugLine="Wait For Msgbox_Result (Result As Int)";
anywheresoftware.b4a.keywords.Common.WaitFor("msgbox_result", processBA, this, null);
this.state = 5;
return;
case 5:
//C
this.state = 1;
_result = (Integer) result[0];
;
 //BA.debugLineNum = 224;BA.debugLine="If Result = DialogResponse.POSITIVE Then";
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
 //BA.debugLineNum = 225;BA.debugLine="Main.strUserID = \"\"";
parent.mostCurrent._main._struserid /*String*/  = "";
 //BA.debugLineNum = 226;BA.debugLine="Main.strUserName = \"\"";
parent.mostCurrent._main._strusername /*String*/  = "";
 //BA.debugLineNum = 227;BA.debugLine="Main.strUserLocation = \"\"";
parent.mostCurrent._main._struserlocation /*String*/  = "";
 //BA.debugLineNum = 228;BA.debugLine="Main.strUserEmail = \"\"";
parent.mostCurrent._main._struseremail /*String*/  = "";
 //BA.debugLineNum = 229;BA.debugLine="Main.strUserOrg = \"\"";
parent.mostCurrent._main._struserorg /*String*/  = "";
 //BA.debugLineNum = 230;BA.debugLine="Main.username = \"\"";
parent.mostCurrent._main._username /*String*/  = "";
 //BA.debugLineNum = 231;BA.debugLine="Activity.RemoveAllViews";
parent.mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 232;BA.debugLine="Activity.finish";
parent.mostCurrent._activity.Finish();
 if (true) break;

case 4:
//C
this.state = -1;
;
 //BA.debugLineNum = 234;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public static void  _msgbox_result(int _result) throws Exception{
}
public static String  _btndatosanteriores_click() throws Exception{
 //BA.debugLineNum = 257;BA.debugLine="Sub btnDatosAnteriores_Click";
 //BA.debugLineNum = 258;BA.debugLine="If Main.modooffline = False Then";
if (mostCurrent._main._modooffline /*boolean*/ ==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 259;BA.debugLine="StartActivity(frmDatosAnteriores)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._frmdatosanteriores.getObject()));
 }else {
 //BA.debugLineNum = 261;BA.debugLine="ToastMessageShow(\"Necesitas estar conectado a in";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Necesitas estar conectado a internet para ver tus datos anteriores"),anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 263;BA.debugLine="End Sub";
return "";
}
public static String  _btnedituser_click() throws Exception{
 //BA.debugLineNum = 238;BA.debugLine="Sub btnEditUser_Click";
 //BA.debugLineNum = 239;BA.debugLine="If Main.username = \"guest\" Or Main.username = \"No";
if ((mostCurrent._main._username /*String*/ ).equals("guest") || (mostCurrent._main._username /*String*/ ).equals("None")) { 
 //BA.debugLineNum = 240;BA.debugLine="ToastMessageShow(\"Necesita estar registrado para";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Necesita estar registrado para ver su perfil"),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 241;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 243;BA.debugLine="If Main.modooffline = False Then";
if (mostCurrent._main._modooffline /*boolean*/ ==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 244;BA.debugLine="StartActivity(frmEditProfile)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._frmeditprofile.getObject()));
 }else {
 //BA.debugLineNum = 246;BA.debugLine="ToastMessageShow(\"Necesitas estar conectado a in";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Necesitas estar conectado a internet para editar su perfil"),anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 249;BA.debugLine="End Sub";
return "";
}
public static String  _btninformacion_click() throws Exception{
 //BA.debugLineNum = 312;BA.debugLine="Private Sub btnInformacion_Click";
 //BA.debugLineNum = 313;BA.debugLine="lblLine1.Visible = False";
mostCurrent._lblline1.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 314;BA.debugLine="lblLine2.Visible = False";
mostCurrent._lblline2.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 315;BA.debugLine="lblLine3.Visible = True";
mostCurrent._lblline3.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 316;BA.debugLine="tabStripMain.ScrollTo(2, True)";
mostCurrent._tabstripmain.ScrollTo((int) (2),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 317;BA.debugLine="End Sub";
return "";
}
public static String  _btnmapa_click() throws Exception{
 //BA.debugLineNum = 319;BA.debugLine="Private Sub btnMapa_Click";
 //BA.debugLineNum = 320;BA.debugLine="lblLine1.Visible = True";
mostCurrent._lblline1.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 321;BA.debugLine="lblLine2.Visible = False";
mostCurrent._lblline2.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 322;BA.debugLine="lblLine3.Visible = False";
mostCurrent._lblline3.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 323;BA.debugLine="tabStripMain.ScrollTo(0, True)";
mostCurrent._tabstripmain.ScrollTo((int) (0),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 324;BA.debugLine="End Sub";
return "";
}
public static String  _btnmasinfomunicipio_click() throws Exception{
 //BA.debugLineNum = 636;BA.debugLine="Private Sub btnMasInfoMunicipio_Click";
 //BA.debugLineNum = 637;BA.debugLine="StartActivity(pi.OpenBrowser(municipioURL))";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._pi.OpenBrowser(mostCurrent._municipiourl)));
 //BA.debugLineNum = 638;BA.debugLine="End Sub";
return "";
}
public static String  _btnmenu_main_click() throws Exception{
 //BA.debugLineNum = 200;BA.debugLine="Sub btnMenu_Main_Click";
 //BA.debugLineNum = 201;BA.debugLine="Drawer.LeftOpen = True";
mostCurrent._drawer._setleftopen /*boolean*/ (anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 202;BA.debugLine="If Main.username = \"guest\" Or Main.username = \"No";
if ((mostCurrent._main._username /*String*/ ).equals("guest") || (mostCurrent._main._username /*String*/ ).equals("None")) { 
 //BA.debugLineNum = 203;BA.debugLine="btnCerrarSesion.Text = \"Registrarse!\"";
mostCurrent._btncerrarsesion.setText(BA.ObjectToCharSequence("Registrarse!"));
 }else {
 //BA.debugLineNum = 205;BA.debugLine="btnCerrarSesion.Visible = False";
mostCurrent._btncerrarsesion.setVisible(anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 207;BA.debugLine="End Sub";
return "";
}
public static String  _btnmuestreos_click() throws Exception{
 //BA.debugLineNum = 250;BA.debugLine="Sub btnMuestreos_Click";
 //BA.debugLineNum = 251;BA.debugLine="StartActivity(Aprender_Muestreo)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._aprender_muestreo.getObject()));
 //BA.debugLineNum = 252;BA.debugLine="End Sub";
return "";
}
public static String  _btnmunicipiofb_click() throws Exception{
 //BA.debugLineNum = 650;BA.debugLine="Private Sub btnMunicipioFB_Click";
 //BA.debugLineNum = 651;BA.debugLine="StartActivity(pi.OpenBrowser(municipioFB))";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._pi.OpenBrowser(mostCurrent._municipiofb)));
 //BA.debugLineNum = 652;BA.debugLine="End Sub";
return "";
}
public static String  _btnmunicipioig_click() throws Exception{
 //BA.debugLineNum = 642;BA.debugLine="Private Sub btnMunicipioIG_Click";
 //BA.debugLineNum = 643;BA.debugLine="StartActivity(pi.OpenBrowser(municipioIG))";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._pi.OpenBrowser(mostCurrent._municipioig)));
 //BA.debugLineNum = 644;BA.debugLine="End Sub";
return "";
}
public static String  _btnmunicipiotw_click() throws Exception{
 //BA.debugLineNum = 654;BA.debugLine="Private Sub btnMunicipioTW_Click";
 //BA.debugLineNum = 655;BA.debugLine="StartActivity(pi.OpenBrowser(municipioTW))";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._pi.OpenBrowser(mostCurrent._municipiotw)));
 //BA.debugLineNum = 656;BA.debugLine="End Sub";
return "";
}
public static String  _btnmunicipioyt_click() throws Exception{
 //BA.debugLineNum = 646;BA.debugLine="Private Sub btnMunicipioYT_Click";
 //BA.debugLineNum = 647;BA.debugLine="StartActivity(pi.OpenBrowser(municipioYT))";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._pi.OpenBrowser(mostCurrent._municipioyt)));
 //BA.debugLineNum = 648;BA.debugLine="End Sub";
return "";
}
public static String  _btnpoliticadatos_click() throws Exception{
 //BA.debugLineNum = 253;BA.debugLine="Sub btnPoliticaDatos_Click";
 //BA.debugLineNum = 254;BA.debugLine="StartActivity(frmPoliticaDatos)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._frmpoliticadatos.getObject()));
 //BA.debugLineNum = 256;BA.debugLine="End Sub";
return "";
}
public static String  _btnreportar_click() throws Exception{
 //BA.debugLineNum = 450;BA.debugLine="Sub btnReportar_Click";
 //BA.debugLineNum = 453;BA.debugLine="If Main.username = \"guest\" Or Main.username = \"No";
if ((mostCurrent._main._username /*String*/ ).equals("guest") || (mostCurrent._main._username /*String*/ ).equals("None") || (mostCurrent._main._username /*String*/ ).equals("")) { 
 //BA.debugLineNum = 454;BA.debugLine="ToastMessageShow(\"Necesita estar registrado para";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Necesita estar registrado para hacer un reporte"),anywheresoftware.b4a.keywords.Common.False);
 }else {
 //BA.debugLineNum = 468;BA.debugLine="Form_Reporte.origen = \"Form_Main\"";
mostCurrent._form_reporte._origen /*String*/  = "Form_Main";
 //BA.debugLineNum = 469;BA.debugLine="StartActivity(Form_Reporte)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._form_reporte.getObject()));
 };
 //BA.debugLineNum = 472;BA.debugLine="End Sub";
return "";
}
public static String  _btnverperfil_click() throws Exception{
 //BA.debugLineNum = 209;BA.debugLine="Sub btnVerPerfil_Click";
 //BA.debugLineNum = 210;BA.debugLine="If Main.username = \"guest\" Or Main.username = \"No";
if ((mostCurrent._main._username /*String*/ ).equals("guest") || (mostCurrent._main._username /*String*/ ).equals("None") || (mostCurrent._main._username /*String*/ ).equals("")) { 
 //BA.debugLineNum = 211;BA.debugLine="ToastMessageShow(\"Necesita estar registrado para";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Necesita estar registrado para ver su perfil"),anywheresoftware.b4a.keywords.Common.False);
 }else {
 //BA.debugLineNum = 213;BA.debugLine="If Main.modooffline = False Then";
if (mostCurrent._main._modooffline /*boolean*/ ==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 214;BA.debugLine="StartActivity(frmEditProfile)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._frmeditprofile.getObject()));
 }else {
 //BA.debugLineNum = 216;BA.debugLine="ToastMessageShow(\"Necesita tener internet para";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Necesita tener internet para editar su perfil"),anywheresoftware.b4a.keywords.Common.False);
 };
 };
 //BA.debugLineNum = 219;BA.debugLine="End Sub";
return "";
}
public static String  _cargarmapa() throws Exception{
anywheresoftware.b4a.objects.MapFragmentWrapper.LatLngWrapper _mylocation = null;
anywheresoftware.b4a.objects.MapFragmentWrapper.CameraPositionWrapper _cp = null;
 //BA.debugLineNum = 510;BA.debugLine="Sub CargarMapa";
 //BA.debugLineNum = 511;BA.debugLine="markerred.Initialize(File.DirAssets, \"marker_red.";
mostCurrent._markerred.Initialize(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"marker_red.png");
 //BA.debugLineNum = 512;BA.debugLine="markerorange.Initialize(File.DirAssets, \"marker_o";
mostCurrent._markerorange.Initialize(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"marker_orange.png");
 //BA.debugLineNum = 513;BA.debugLine="markeryellow.Initialize(File.DirAssets, \"marker_y";
mostCurrent._markeryellow.Initialize(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"marker_yellow.png");
 //BA.debugLineNum = 514;BA.debugLine="markergreen.Initialize(File.DirAssets, \"marker_gr";
mostCurrent._markergreen.Initialize(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"marker_green.png");
 //BA.debugLineNum = 515;BA.debugLine="markerblue.Initialize(File.DirAssets, \"marker_blu";
mostCurrent._markerblue.Initialize(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"marker_blue.png");
 //BA.debugLineNum = 516;BA.debugLine="markergray.Initialize(File.DirAssets, \"marker_gra";
mostCurrent._markergray.Initialize(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"marker_gray.png");
 //BA.debugLineNum = 521;BA.debugLine="Dim myLocation As LatLng";
_mylocation = new anywheresoftware.b4a.objects.MapFragmentWrapper.LatLngWrapper();
 //BA.debugLineNum = 522;BA.debugLine="myLocation = gmap.MyLocation";
_mylocation = mostCurrent._gmap.getMyLocation();
 //BA.debugLineNum = 524;BA.debugLine="If myLocation.IsInitialized = False Then";
if (_mylocation.IsInitialized()==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 525;BA.debugLine="myLocation.Initialize(\"-34.9204950\",\"-57.9535660";
_mylocation.Initialize((double)(Double.parseDouble("-34.9204950")),(double)(Double.parseDouble("-57.9535660")));
 };
 //BA.debugLineNum = 527;BA.debugLine="Dim cp As CameraPosition";
_cp = new anywheresoftware.b4a.objects.MapFragmentWrapper.CameraPositionWrapper();
 //BA.debugLineNum = 528;BA.debugLine="cp.Initialize(myLocation.Latitude, myLocation.Lon";
_cp.Initialize(_mylocation.getLatitude(),_mylocation.getLongitude(),(float) (16));
 //BA.debugLineNum = 529;BA.debugLine="gmap.AnimateCamera(cp)";
mostCurrent._gmap.AnimateCamera((com.google.android.gms.maps.model.CameraPosition)(_cp.getObject()));
 //BA.debugLineNum = 530;BA.debugLine="gmap.MapType=gmap.MAP_TYPE_HYBRID";
mostCurrent._gmap.setMapType(mostCurrent._gmap.MAP_TYPE_HYBRID);
 //BA.debugLineNum = 534;BA.debugLine="GetMiMapa";
_getmimapa();
 //BA.debugLineNum = 535;BA.debugLine="End Sub";
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
 //BA.debugLineNum = 166;BA.debugLine="Msgbox2Async(\"Cerrar PreserVamos?\", \"SALIR\", \"Si\"";
anywheresoftware.b4a.keywords.Common.Msgbox2Async(BA.ObjectToCharSequence("Cerrar PreserVamos?"),BA.ObjectToCharSequence("SALIR"),"Si","","No",(anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper(), (android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null)),processBA,anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 167;BA.debugLine="Wait For Msgbox_Result (Result As Int)";
anywheresoftware.b4a.keywords.Common.WaitFor("msgbox_result", processBA, this, null);
this.state = 5;
return;
case 5:
//C
this.state = 1;
_result = (Integer) result[0];
;
 //BA.debugLineNum = 168;BA.debugLine="If Result = DialogResponse.POSITIVE Then";
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
 //BA.debugLineNum = 169;BA.debugLine="Activity.RemoveAllViews";
parent.mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 170;BA.debugLine="Activity.finish";
parent.mostCurrent._activity.Finish();
 //BA.debugLineNum = 171;BA.debugLine="ExitApplication";
anywheresoftware.b4a.keywords.Common.ExitApplication();
 if (true) break;

case 4:
//C
this.state = -1;
;
 //BA.debugLineNum = 173;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public static String  _crearmenu() throws Exception{
 //BA.debugLineNum = 181;BA.debugLine="Sub CrearMenu";
 //BA.debugLineNum = 183;BA.debugLine="Drawer.Initialize(Me, \"Drawer\", Activity, 300dip)";
mostCurrent._drawer._initialize /*String*/ (mostCurrent.activityBA,form_main.getObject(),"Drawer",(anywheresoftware.b4a.objects.B4XViewWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.B4XViewWrapper(), (java.lang.Object)(mostCurrent._activity.getObject())),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (300)));
 //BA.debugLineNum = 184;BA.debugLine="Drawer.CenterPanel.LoadLayout(\"layMain\")";
mostCurrent._drawer._getcenterpanel /*anywheresoftware.b4a.objects.B4XViewWrapper*/ ().LoadLayout("layMain",mostCurrent.activityBA);
 //BA.debugLineNum = 185;BA.debugLine="Drawer.LeftPanel.LoadLayout(\"frmSideNav\")";
mostCurrent._drawer._getleftpanel /*anywheresoftware.b4a.objects.B4XViewWrapper*/ ().LoadLayout("frmSideNav",mostCurrent.activityBA);
 //BA.debugLineNum = 187;BA.debugLine="If Main.username = \"guest\" Or Main.username = \"No";
if ((mostCurrent._main._username /*String*/ ).equals("guest") || (mostCurrent._main._username /*String*/ ).equals("None")) { 
 //BA.debugLineNum = 188;BA.debugLine="btnCerrarSesion.Text = \"Iniciar sesión\"";
mostCurrent._btncerrarsesion.setText(BA.ObjectToCharSequence("Iniciar sesión"));
 //BA.debugLineNum = 189;BA.debugLine="lblUserName.Visible = False";
mostCurrent._lblusername.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 190;BA.debugLine="lblRegistrate.Visible = True";
mostCurrent._lblregistrate.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 191;BA.debugLine="btnEditUser.Visible = False";
mostCurrent._btnedituser.setVisible(anywheresoftware.b4a.keywords.Common.False);
 }else {
 //BA.debugLineNum = 193;BA.debugLine="lblUserName.Text = Main.username";
mostCurrent._lblusername.setText(BA.ObjectToCharSequence(mostCurrent._main._username /*String*/ ));
 //BA.debugLineNum = 194;BA.debugLine="btnCerrarSesion.Text = \"Cerrar sesión\"";
mostCurrent._btncerrarsesion.setText(BA.ObjectToCharSequence("Cerrar sesión"));
 //BA.debugLineNum = 195;BA.debugLine="lblRegistrate.Visible = False";
mostCurrent._lblregistrate.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 196;BA.debugLine="btnEditUser.Visible = True";
mostCurrent._btnedituser.setVisible(anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 198;BA.debugLine="End Sub";
return "";
}
public static String  _fondogris_click() throws Exception{
 //BA.debugLineNum = 494;BA.debugLine="Private Sub fondogris_Click";
 //BA.debugLineNum = 495;BA.debugLine="panelComoFunciona.RemoveView";
mostCurrent._panelcomofunciona.RemoveView();
 //BA.debugLineNum = 496;BA.debugLine="fondogris.RemoveView";
mostCurrent._fondogris.RemoveView();
 //BA.debugLineNum = 497;BA.debugLine="End Sub";
return "";
}
public static String  _getmimapa() throws Exception{
appear.pnud.preservamos.downloadservice._downloaddata _dd = null;
 //BA.debugLineNum = 540;BA.debugLine="Sub GetMiMapa";
 //BA.debugLineNum = 541;BA.debugLine="ProgressDialogShow(\"Buscando puntos cercanos...\")";
anywheresoftware.b4a.keywords.Common.ProgressDialogShow(mostCurrent.activityBA,BA.ObjectToCharSequence("Buscando puntos cercanos..."));
 //BA.debugLineNum = 544;BA.debugLine="Dim dd As DownloadData";
_dd = new appear.pnud.preservamos.downloadservice._downloaddata();
 //BA.debugLineNum = 545;BA.debugLine="dd.url = \"https://preservamos.ar/connect_app/geta";
_dd.url /*String*/  = "https://preservamos.ar/connect_app/getallmapa.php";
 //BA.debugLineNum = 546;BA.debugLine="dd.EventName = \"GetMiMapa\"";
_dd.EventName /*String*/  = "GetMiMapa";
 //BA.debugLineNum = 547;BA.debugLine="dd.Target = Me";
_dd.Target /*Object*/  = form_main.getObject();
 //BA.debugLineNum = 548;BA.debugLine="CallSubDelayed2(DownloadService, \"StartDownload\",";
anywheresoftware.b4a.keywords.Common.CallSubDelayed2(processBA,(Object)(mostCurrent._downloadservice.getObject()),"StartDownload",(Object)(_dd));
 //BA.debugLineNum = 550;BA.debugLine="End Sub";
return "";
}
public static String  _getmimapa_complete(appear.pnud.preservamos.httpjob _job) throws Exception{
String _ret = "";
String _act = "";
anywheresoftware.b4a.objects.collections.JSONParser _parser = null;
String _numresults = "";
int _i = 0;
anywheresoftware.b4a.objects.collections.Map _newpunto = null;
double _sitiolat = 0;
double _sitiolong = 0;
String _sitioindice = "";
String _sitiotiporio = "";
String _sitiocontribucion = "";
 //BA.debugLineNum = 551;BA.debugLine="Sub GetMiMapa_Complete(Job As HttpJob)";
 //BA.debugLineNum = 552;BA.debugLine="Log(\"GetMapa messages: \" & Job.Success)";
anywheresoftware.b4a.keywords.Common.LogImpl("62621441","GetMapa messages: "+BA.ObjectToString(_job._success /*boolean*/ ),0);
 //BA.debugLineNum = 553;BA.debugLine="If Job.Success = True Then";
if (_job._success /*boolean*/ ==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 555;BA.debugLine="Dim ret As String";
_ret = "";
 //BA.debugLineNum = 556;BA.debugLine="Dim act As String";
_act = "";
 //BA.debugLineNum = 557;BA.debugLine="ret = Job.GetString";
_ret = _job._getstring /*String*/ ();
 //BA.debugLineNum = 558;BA.debugLine="Dim parser As JSONParser";
_parser = new anywheresoftware.b4a.objects.collections.JSONParser();
 //BA.debugLineNum = 559;BA.debugLine="parser.Initialize(ret)";
_parser.Initialize(_ret);
 //BA.debugLineNum = 560;BA.debugLine="act = parser.NextValue";
_act = BA.ObjectToString(_parser.NextValue());
 //BA.debugLineNum = 561;BA.debugLine="If act = \"Not Found\" Then";
if ((_act).equals("Not Found")) { 
 //BA.debugLineNum = 562;BA.debugLine="ToastMessageShow(\"No encuentro tus sitios anter";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("No encuentro tus sitios anteriores, prueba luego"),anywheresoftware.b4a.keywords.Common.True);
 }else if((_act).equals("Error")) { 
 //BA.debugLineNum = 564;BA.debugLine="ToastMessageShow(\"No encuentro tus sitios anter";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("No encuentro tus sitios anteriores, prueba luego"),anywheresoftware.b4a.keywords.Common.True);
 }else if((_act).equals("GetMapaOk")) { 
 //BA.debugLineNum = 567;BA.debugLine="Dim numresults As String";
_numresults = "";
 //BA.debugLineNum = 568;BA.debugLine="numresults = parser.NextValue";
_numresults = BA.ObjectToString(_parser.NextValue());
 //BA.debugLineNum = 570;BA.debugLine="For i = 0 To numresults - 1";
{
final int step16 = 1;
final int limit16 = (int) ((double)(Double.parseDouble(_numresults))-1);
_i = (int) (0) ;
for (;_i <= limit16 ;_i = _i + step16 ) {
 //BA.debugLineNum = 572;BA.debugLine="Dim newpunto As Map";
_newpunto = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 573;BA.debugLine="newpunto = parser.NextObject";
_newpunto = _parser.NextObject();
 //BA.debugLineNum = 575;BA.debugLine="Dim sitiolat As Double = newpunto.Get(\"lat\")";
_sitiolat = (double)(BA.ObjectToNumber(_newpunto.Get((Object)("lat"))));
 //BA.debugLineNum = 576;BA.debugLine="Dim sitiolong As Double = newpunto.Get(\"lng\")";
_sitiolong = (double)(BA.ObjectToNumber(_newpunto.Get((Object)("lng"))));
 //BA.debugLineNum = 577;BA.debugLine="Dim sitioindice As String = newpunto.Get(\"indi";
_sitioindice = BA.ObjectToString(_newpunto.Get((Object)("indice")));
 //BA.debugLineNum = 578;BA.debugLine="Dim sitiotiporio As String = newpunto.Get(\"tip";
_sitiotiporio = BA.ObjectToString(_newpunto.Get((Object)("tiporio")));
 //BA.debugLineNum = 579;BA.debugLine="Dim sitiocontribucion As String = newpunto.Get";
_sitiocontribucion = BA.ObjectToString(_newpunto.Get((Object)("username")));
 //BA.debugLineNum = 581;BA.debugLine="If sitiocontribucion.Contains(\"@\") Then";
if (_sitiocontribucion.contains("@")) { 
 //BA.debugLineNum = 582;BA.debugLine="sitiocontribucion = sitiocontribucion.SubStri";
_sitiocontribucion = _sitiocontribucion.substring((int) (0),_sitiocontribucion.indexOf("@"));
 };
 //BA.debugLineNum = 586;BA.debugLine="If sitiolat <> \"0\" And sitiolong <> \"0\" Then";
if (_sitiolat!=(double)(Double.parseDouble("0")) && _sitiolong!=(double)(Double.parseDouble("0"))) { 
 //BA.debugLineNum = 587;BA.debugLine="If sitioindice > 0 And sitioindice < 20 Then";
if ((double)(Double.parseDouble(_sitioindice))>0 && (double)(Double.parseDouble(_sitioindice))<20) { 
 //BA.debugLineNum = 589;BA.debugLine="gmap.AddMarker2(sitiolat,sitiolong,sitioindi";
mostCurrent._gmap.AddMarker2(_sitiolat,_sitiolong,_sitioindice,mostCurrent._gmap.HUE_RED);
 }else if((double)(Double.parseDouble(_sitioindice))>20 && (double)(Double.parseDouble(_sitioindice))<40) { 
 //BA.debugLineNum = 592;BA.debugLine="gmap.AddMarker2(sitiolat,sitiolong,sitioindi";
mostCurrent._gmap.AddMarker2(_sitiolat,_sitiolong,_sitioindice,mostCurrent._gmap.HUE_ORANGE);
 }else if((double)(Double.parseDouble(_sitioindice))>40 && (double)(Double.parseDouble(_sitioindice))<60) { 
 //BA.debugLineNum = 595;BA.debugLine="gmap.AddMarker2(sitiolat,sitiolong,sitioindi";
mostCurrent._gmap.AddMarker2(_sitiolat,_sitiolong,_sitioindice,mostCurrent._gmap.HUE_YELLOW);
 }else if((double)(Double.parseDouble(_sitioindice))>60 && (double)(Double.parseDouble(_sitioindice))<80) { 
 //BA.debugLineNum = 598;BA.debugLine="gmap.AddMarker2(sitiolat,sitiolong,sitioindi";
mostCurrent._gmap.AddMarker2(_sitiolat,_sitiolong,_sitioindice,mostCurrent._gmap.HUE_GREEN);
 }else if((double)(Double.parseDouble(_sitioindice))>80 && (double)(Double.parseDouble(_sitioindice))<100) { 
 //BA.debugLineNum = 601;BA.debugLine="gmap.AddMarker2(sitiolat,sitiolong,sitioindi";
mostCurrent._gmap.AddMarker2(_sitiolat,_sitiolong,_sitioindice,mostCurrent._gmap.HUE_BLUE);
 }else {
 //BA.debugLineNum = 604;BA.debugLine="gmap.AddMarker2(sitiolat,sitiolong,sitioindi";
mostCurrent._gmap.AddMarker2(_sitiolat,_sitiolong,_sitioindice,mostCurrent._gmap.HUE_AZURE);
 };
 }else {
 //BA.debugLineNum = 609;BA.debugLine="Log(\"Punto no agregado\")";
anywheresoftware.b4a.keywords.Common.LogImpl("62621498","Punto no agregado",0);
 };
 }
};
 };
 }else {
 //BA.debugLineNum = 615;BA.debugLine="Log(\"GetMapa not ok\")";
anywheresoftware.b4a.keywords.Common.LogImpl("62621504","GetMapa not ok",0);
 //BA.debugLineNum = 616;BA.debugLine="If Main.modooffline = True Then";
if (mostCurrent._main._modooffline /*boolean*/ ==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 617;BA.debugLine="MsgboxAsync(\"Necesitas tener conexión a interne";
anywheresoftware.b4a.keywords.Common.MsgboxAsync(BA.ObjectToCharSequence("Necesitas tener conexión a internet para ver el mapa, ¡intentalo luego!"),BA.ObjectToCharSequence("No conectado"),processBA);
 }else {
 //BA.debugLineNum = 619;BA.debugLine="MsgboxAsync(\"Al parecer hay un problema en nues";
anywheresoftware.b4a.keywords.Common.MsgboxAsync(BA.ObjectToCharSequence("Al parecer hay un problema en nuestros servidores, lo solucionaremos pronto!"),BA.ObjectToCharSequence("Mala mía"),processBA);
 };
 };
 //BA.debugLineNum = 623;BA.debugLine="Job.Release";
_job._release /*String*/ ();
 //BA.debugLineNum = 624;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 //BA.debugLineNum = 627;BA.debugLine="End Sub";
return "";
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 16;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 19;BA.debugLine="Dim FormMainloaded As Boolean = False";
_formmainloaded = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 20;BA.debugLine="Dim p As Phone";
mostCurrent._p = new anywheresoftware.b4a.phone.Phone();
 //BA.debugLineNum = 21;BA.debugLine="Dim pi As PhoneIntents";
mostCurrent._pi = new anywheresoftware.b4a.phone.Phone.PhoneIntents();
 //BA.debugLineNum = 25;BA.debugLine="Private pgbNivel As ProgressBar";
mostCurrent._pgbnivel = new anywheresoftware.b4a.objects.ProgressBarWrapper();
 //BA.debugLineNum = 26;BA.debugLine="Private lblLevel As Label";
mostCurrent._lbllevel = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 29;BA.debugLine="Private tabStripMain As TabStrip";
mostCurrent._tabstripmain = new anywheresoftware.b4a.objects.TabStripViewPager();
 //BA.debugLineNum = 32;BA.debugLine="Private lblAnalizar As Label";
mostCurrent._lblanalizar = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 33;BA.debugLine="Private btnReportar As Button";
mostCurrent._btnreportar = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 34;BA.debugLine="Private lblComoFuncionaAnalizar As Label";
mostCurrent._lblcomofuncionaanalizar = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 38;BA.debugLine="Private btnDetectar As Button";
mostCurrent._btndetectar = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 39;BA.debugLine="Private lblLat As Label";
mostCurrent._lbllat = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 40;BA.debugLine="Private lblLon As Label";
mostCurrent._lbllon = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 41;BA.debugLine="Private btnZoomAll As Button";
mostCurrent._btnzoomall = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 42;BA.debugLine="Dim MarkerExport As List";
mostCurrent._markerexport = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 44;BA.debugLine="Dim MarkerInfo As Label";
mostCurrent._markerinfo = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 45;BA.debugLine="Private pnlMapa As Panel";
mostCurrent._pnlmapa = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 46;BA.debugLine="Dim fondoblanco As Label";
mostCurrent._fondoblanco = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 47;BA.debugLine="Dim detectandoLabel As Label";
mostCurrent._detectandolabel = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 51;BA.debugLine="Dim firstuse As String";
mostCurrent._firstuse = "";
 //BA.debugLineNum = 52;BA.debugLine="Dim fondogris As Panel";
mostCurrent._fondogris = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 53;BA.debugLine="Dim panelComoFunciona As Panel";
mostCurrent._panelcomofunciona = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 55;BA.debugLine="Dim tutorialEtapa As Int";
_tutorialetapa = 0;
 //BA.debugLineNum = 56;BA.debugLine="Private btnMenu_Main As Label";
mostCurrent._btnmenu_main = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 60;BA.debugLine="Dim Drawer As B4XDrawer";
mostCurrent._drawer = new appear.pnud.preservamos.b4xdrawer();
 //BA.debugLineNum = 61;BA.debugLine="Private btnCerrarSesion As Label";
mostCurrent._btncerrarsesion = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 62;BA.debugLine="Private btnEditUser As Label";
mostCurrent._btnedituser = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 63;BA.debugLine="Private btnverMedallas As Label";
mostCurrent._btnvermedallas = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 64;BA.debugLine="Private lblUserName As Label";
mostCurrent._lblusername = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 65;BA.debugLine="Private lblRegistrate As Label";
mostCurrent._lblregistrate = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 66;BA.debugLine="Private btnVerPerfil As Label";
mostCurrent._btnverperfil = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 67;BA.debugLine="Private btnAbout As Label";
mostCurrent._btnabout = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 68;BA.debugLine="Private btnPoliticaDatos As Label";
mostCurrent._btnpoliticadatos = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 69;BA.debugLine="Private btnDatosAnteriores As Label";
mostCurrent._btndatosanteriores = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 70;BA.debugLine="Private btnMuestreos As Label";
mostCurrent._btnmuestreos = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 74;BA.debugLine="Private lblTitleMunicipio As Label";
mostCurrent._lbltitlemunicipio = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 75;BA.debugLine="Private WebView1 As WebView";
mostCurrent._webview1 = new anywheresoftware.b4a.objects.WebViewWrapper();
 //BA.debugLineNum = 76;BA.debugLine="Private lblMunicipioContent As Label";
mostCurrent._lblmunicipiocontent = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 77;BA.debugLine="Private btnMasInfoMunicipio As ImageView";
mostCurrent._btnmasinfomunicipio = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 78;BA.debugLine="Private imgLogoMunicipio As ImageView";
mostCurrent._imglogomunicipio = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 79;BA.debugLine="Dim municipioURL As String";
mostCurrent._municipiourl = "";
 //BA.debugLineNum = 80;BA.debugLine="Dim municipioFB As String";
mostCurrent._municipiofb = "";
 //BA.debugLineNum = 81;BA.debugLine="Dim municipioIG As String";
mostCurrent._municipioig = "";
 //BA.debugLineNum = 82;BA.debugLine="Dim municipioYT As String";
mostCurrent._municipioyt = "";
 //BA.debugLineNum = 83;BA.debugLine="Dim municipioTW As String";
mostCurrent._municipiotw = "";
 //BA.debugLineNum = 86;BA.debugLine="Private lblLine2 As Label";
mostCurrent._lblline2 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 87;BA.debugLine="Private lblLine1 As Label";
mostCurrent._lblline1 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 88;BA.debugLine="Private lblLine3 As Label";
mostCurrent._lblline3 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 93;BA.debugLine="Private gmap As GoogleMap";
mostCurrent._gmap = new anywheresoftware.b4a.objects.MapFragmentWrapper.GoogleMapWrapper();
 //BA.debugLineNum = 94;BA.debugLine="Private MapFragment1 As MapFragment";
mostCurrent._mapfragment1 = new anywheresoftware.b4a.objects.MapFragmentWrapper();
 //BA.debugLineNum = 95;BA.debugLine="Private lblInstruccionesLocalizacion As Label";
mostCurrent._lblinstruccioneslocalizacion = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 96;BA.debugLine="Private btnContinuarLocalizacion As Button";
mostCurrent._btncontinuarlocalizacion = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 97;BA.debugLine="Private markerred As Bitmap";
mostCurrent._markerred = new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper();
 //BA.debugLineNum = 98;BA.debugLine="Private markerorange As Bitmap";
mostCurrent._markerorange = new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper();
 //BA.debugLineNum = 99;BA.debugLine="Private markeryellow As Bitmap";
mostCurrent._markeryellow = new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper();
 //BA.debugLineNum = 100;BA.debugLine="Private markergreen As Bitmap";
mostCurrent._markergreen = new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper();
 //BA.debugLineNum = 101;BA.debugLine="Private markerblue As Bitmap";
mostCurrent._markerblue = new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper();
 //BA.debugLineNum = 102;BA.debugLine="Private markergray As Bitmap";
mostCurrent._markergray = new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper();
 //BA.debugLineNum = 104;BA.debugLine="Private btnMunicipioTW As ImageView";
mostCurrent._btnmunicipiotw = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 105;BA.debugLine="Private btnMunicipioFB As ImageView";
mostCurrent._btnmunicipiofb = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 106;BA.debugLine="Private btnMunicipioYT As ImageView";
mostCurrent._btnmunicipioyt = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 107;BA.debugLine="Private btnMunicipioIG As ImageView";
mostCurrent._btnmunicipioig = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 109;BA.debugLine="Private lblLocalizacionWhiteCover As Label";
mostCurrent._lbllocalizacionwhitecover = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 110;BA.debugLine="End Sub";
return "";
}
public static String  _gotoinformacion() throws Exception{
 //BA.debugLineNum = 347;BA.debugLine="Sub gotoInformacion";
 //BA.debugLineNum = 348;BA.debugLine="tabStripMain.ScrollTo(2, True)";
mostCurrent._tabstripmain.ScrollTo((int) (2),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 349;BA.debugLine="lblLine1.Visible = False";
mostCurrent._lblline1.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 350;BA.debugLine="lblLine2.Visible = False";
mostCurrent._lblline2.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 351;BA.debugLine="lblLine3.Visible = True";
mostCurrent._lblline3.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 352;BA.debugLine="End Sub";
return "";
}
public static String  _lblcomofuncionaanalizar_click() throws Exception{
 //BA.debugLineNum = 474;BA.debugLine="Sub lblComoFuncionaAnalizar_Click";
 //BA.debugLineNum = 477;BA.debugLine="fondogris.Initialize(\"fondogris\")";
mostCurrent._fondogris.Initialize(mostCurrent.activityBA,"fondogris");
 //BA.debugLineNum = 478;BA.debugLine="fondogris.Color = Colors.ARGB(150,0,0,0)";
mostCurrent._fondogris.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (150),(int) (0),(int) (0),(int) (0)));
 //BA.debugLineNum = 479;BA.debugLine="Activity.AddView(fondogris, 0, 0, 100%x, 100%y)";
mostCurrent._activity.AddView((android.view.View)(mostCurrent._fondogris.getObject()),(int) (0),(int) (0),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA));
 //BA.debugLineNum = 481;BA.debugLine="panelComoFunciona.Initialize(\"\")";
mostCurrent._panelcomofunciona.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 482;BA.debugLine="panelComoFunciona.LoadLayout(\"dialog_comoFunciona";
mostCurrent._panelcomofunciona.LoadLayout("dialog_comoFunciona",mostCurrent.activityBA);
 //BA.debugLineNum = 483;BA.debugLine="Activity.AddView(panelComoFunciona, 10%x, 20%y, 8";
mostCurrent._activity.AddView((android.view.View)(mostCurrent._panelcomofunciona.getObject()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (10),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (20),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (80),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (80),mostCurrent.activityBA));
 //BA.debugLineNum = 486;BA.debugLine="End Sub";
return "";
}
public static String  _lblcomofuncionaentendido_click() throws Exception{
 //BA.debugLineNum = 489;BA.debugLine="Private Sub lblComoFuncionaEntendido_Click";
 //BA.debugLineNum = 490;BA.debugLine="panelComoFunciona.RemoveView";
mostCurrent._panelcomofunciona.RemoveView();
 //BA.debugLineNum = 491;BA.debugLine="fondogris.RemoveView";
mostCurrent._fondogris.RemoveView();
 //BA.debugLineNum = 492;BA.debugLine="End Sub";
return "";
}
public static String  _loadform_main() throws Exception{
 //BA.debugLineNum = 286;BA.debugLine="Sub LoadForm_Main";
 //BA.debugLineNum = 287;BA.debugLine="Log(\"userName in LoadForm_Main:\" & Main.username)";
anywheresoftware.b4a.keywords.Common.LogImpl("61769473","userName in LoadForm_Main:"+mostCurrent._main._username /*String*/ ,0);
 //BA.debugLineNum = 288;BA.debugLine="Log(\"firstuse in LoadForm_Main:\" & firstuse)";
anywheresoftware.b4a.keywords.Common.LogImpl("61769474","firstuse in LoadForm_Main:"+mostCurrent._firstuse,0);
 //BA.debugLineNum = 291;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 292;BA.debugLine="Activity.LoadLayout(\"layMain\")";
mostCurrent._activity.LoadLayout("layMain",mostCurrent.activityBA);
 //BA.debugLineNum = 295;BA.debugLine="CrearMenu";
_crearmenu();
 //BA.debugLineNum = 298;BA.debugLine="tabStripMain.LoadLayout(\"Google_Map\", \"\")";
mostCurrent._tabstripmain.LoadLayout("Google_Map",BA.ObjectToCharSequence(""));
 //BA.debugLineNum = 299;BA.debugLine="tabStripMain.LoadLayout(\"layMainReportar\", \"\")";
mostCurrent._tabstripmain.LoadLayout("layMainReportar",BA.ObjectToCharSequence(""));
 //BA.debugLineNum = 300;BA.debugLine="tabStripMain.LoadLayout(\"layMainMunicipio\", \"\")";
mostCurrent._tabstripmain.LoadLayout("layMainMunicipio",BA.ObjectToCharSequence(""));
 //BA.debugLineNum = 301;BA.debugLine="tabStripMain.ScrollTo(1,False)";
mostCurrent._tabstripmain.ScrollTo((int) (1),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 303;BA.debugLine="End Sub";
return "";
}
public static void  _mapfragment1_ready() throws Exception{
ResumableSub_MapFragment1_Ready rsub = new ResumableSub_MapFragment1_Ready(null);
rsub.resume(processBA, null);
}
public static class ResumableSub_MapFragment1_Ready extends BA.ResumableSub {
public ResumableSub_MapFragment1_Ready(appear.pnud.preservamos.form_main parent) {
this.parent = parent;
}
appear.pnud.preservamos.form_main parent;

@Override
public void resume(BA ba, Object[] result) throws Exception{

    while (true) {
        switch (state) {
            case -1:
return;

case 0:
//C
this.state = 1;
 //BA.debugLineNum = 334;BA.debugLine="Log(\"map ready\")";
anywheresoftware.b4a.keywords.Common.LogImpl("62031617","map ready",0);
 //BA.debugLineNum = 335;BA.debugLine="gmap = MapFragment1.GetMap";
parent.mostCurrent._gmap = parent.mostCurrent._mapfragment1.GetMap();
 //BA.debugLineNum = 336;BA.debugLine="gmap.MyLocationEnabled = True";
parent.mostCurrent._gmap.setMyLocationEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 337;BA.debugLine="If gmap.IsInitialized = False Then";
if (true) break;

case 1:
//if
this.state = 10;
if (parent.mostCurrent._gmap.IsInitialized()==anywheresoftware.b4a.keywords.Common.False) { 
this.state = 3;
}else {
this.state = 5;
}if (true) break;

case 3:
//C
this.state = 10;
 //BA.debugLineNum = 338;BA.debugLine="ToastMessageShow(\"Error initializing map.\", True";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Error initializing map."),anywheresoftware.b4a.keywords.Common.True);
 if (true) break;

case 5:
//C
this.state = 6;
 //BA.debugLineNum = 340;BA.debugLine="Do While Not(gmap.MyLocation.IsInitialized)";
if (true) break;

case 6:
//do while
this.state = 9;
while (anywheresoftware.b4a.keywords.Common.Not(parent.mostCurrent._gmap.getMyLocation().IsInitialized())) {
this.state = 8;
if (true) break;
}
if (true) break;

case 8:
//C
this.state = 6;
 //BA.debugLineNum = 341;BA.debugLine="Sleep(0)";
anywheresoftware.b4a.keywords.Common.Sleep(mostCurrent.activityBA,this,(int) (0));
this.state = 11;
return;
case 11:
//C
this.state = 6;
;
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
 //BA.debugLineNum = 345;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 6;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 9;BA.debugLine="Dim IsGuest As Boolean";
_isguest = false;
 //BA.debugLineNum = 11;BA.debugLine="Dim fullidcurrentproject As String";
_fullidcurrentproject = "";
 //BA.debugLineNum = 14;BA.debugLine="End Sub";
return "";
}
public static String  _tabstripmain_pageselected(int _position) throws Exception{
 //BA.debugLineNum = 353;BA.debugLine="Sub tabStripMain_PageSelected (Position As Int)";
 //BA.debugLineNum = 354;BA.debugLine="If Position = 0 Then";
if (_position==0) { 
 //BA.debugLineNum = 355;BA.debugLine="lblLine1.Visible = True";
mostCurrent._lblline1.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 356;BA.debugLine="lblLine2.Visible = False";
mostCurrent._lblline2.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 357;BA.debugLine="lblLine3.Visible = False";
mostCurrent._lblline3.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 358;BA.debugLine="btnContinuarLocalizacion.Visible = False";
mostCurrent._btncontinuarlocalizacion.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 359;BA.debugLine="lblInstruccionesLocalizacion.Visible = False";
mostCurrent._lblinstruccioneslocalizacion.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 360;BA.debugLine="lblLocalizacionWhiteCover.Visible = False";
mostCurrent._lbllocalizacionwhitecover.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 366;BA.debugLine="If gmap.IsInitialized = False Then";
if (mostCurrent._gmap.IsInitialized()==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 367;BA.debugLine="ToastMessageShow(\"Error initializing map.\", Tru";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Error initializing map."),anywheresoftware.b4a.keywords.Common.True);
 }else {
 //BA.debugLineNum = 372;BA.debugLine="CargarMapa";
_cargarmapa();
 };
 }else if(_position==2) { 
 //BA.debugLineNum = 375;BA.debugLine="lblTitleMunicipio.Text = Main.strUserOrg";
mostCurrent._lbltitlemunicipio.setText(BA.ObjectToCharSequence(mostCurrent._main._struserorg /*String*/ ));
 //BA.debugLineNum = 377;BA.debugLine="If Main.strUserOrg = \"Balcarce\" Then";
if ((mostCurrent._main._struserorg /*String*/ ).equals("Balcarce")) { 
 //BA.debugLineNum = 378;BA.debugLine="imgLogoMunicipio.Bitmap = LoadBitmap(File.DirAs";
mostCurrent._imglogomunicipio.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"balcarce.png").getObject()));
 //BA.debugLineNum = 379;BA.debugLine="imgLogoMunicipio.Height = 70dip";
mostCurrent._imglogomunicipio.setHeight(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (70)));
 //BA.debugLineNum = 380;BA.debugLine="imgLogoMunicipio.Width = 70dip";
mostCurrent._imglogomunicipio.setWidth(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (70)));
 //BA.debugLineNum = 382;BA.debugLine="WebView1.Visible = True";
mostCurrent._webview1.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 383;BA.debugLine="lblMunicipioContent.Visible = False";
mostCurrent._lblmunicipiocontent.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 384;BA.debugLine="municipioURL = \"https://www.balcarce.gob.ar\"";
mostCurrent._municipiourl = "https://www.balcarce.gob.ar";
 //BA.debugLineNum = 385;BA.debugLine="btnMasInfoMunicipio.visible = True";
mostCurrent._btnmasinfomunicipio.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 386;BA.debugLine="btnMunicipioFB.Visible = False";
mostCurrent._btnmunicipiofb.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 387;BA.debugLine="btnMunicipioYT.Visible = False";
mostCurrent._btnmunicipioyt.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 388;BA.debugLine="btnMunicipioIG.Visible = False";
mostCurrent._btnmunicipioig.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 389;BA.debugLine="btnMunicipioTW.Visible = False";
mostCurrent._btnmunicipiotw.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 390;BA.debugLine="imgLogoMunicipio.Visible= True";
mostCurrent._imglogomunicipio.setVisible(anywheresoftware.b4a.keywords.Common.True);
 }else if((mostCurrent._main._struserorg /*String*/ ).equals("Mercedes")) { 
 //BA.debugLineNum = 392;BA.debugLine="imgLogoMunicipio.Bitmap = LoadBitmap(File.DirAs";
mostCurrent._imglogomunicipio.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"mercedes.jpg").getObject()));
 //BA.debugLineNum = 393;BA.debugLine="imgLogoMunicipio.Height = 70dip";
mostCurrent._imglogomunicipio.setHeight(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (70)));
 //BA.debugLineNum = 394;BA.debugLine="imgLogoMunicipio.Width = 120dip";
mostCurrent._imglogomunicipio.setWidth(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (120)));
 //BA.debugLineNum = 395;BA.debugLine="WebView1.loadhtml(File.ReadString(File.DirAsset";
mostCurrent._webview1.LoadHtml(anywheresoftware.b4a.keywords.Common.File.ReadString(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"mercedes.html"));
 //BA.debugLineNum = 396;BA.debugLine="WebView1.Visible = True";
mostCurrent._webview1.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 397;BA.debugLine="lblMunicipioContent.Visible = False";
mostCurrent._lblmunicipiocontent.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 398;BA.debugLine="municipioURL = \"https://nw.mercedes.gob.ar\"";
mostCurrent._municipiourl = "https://nw.mercedes.gob.ar";
 //BA.debugLineNum = 399;BA.debugLine="btnMasInfoMunicipio.visible = True";
mostCurrent._btnmasinfomunicipio.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 400;BA.debugLine="btnMunicipioFB.Visible = True";
mostCurrent._btnmunicipiofb.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 401;BA.debugLine="btnMunicipioYT.Visible = True";
mostCurrent._btnmunicipioyt.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 402;BA.debugLine="btnMunicipioIG.Visible = True";
mostCurrent._btnmunicipioig.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 403;BA.debugLine="municipioYT = \"https://www.youtube.com/channel/";
mostCurrent._municipioyt = "https://www.youtube.com/channel/UCy6RSyBaOtPCYCnbKJyYtng/videos";
 //BA.debugLineNum = 404;BA.debugLine="municipioIG = \"https://www.instagram.com/merced";
mostCurrent._municipioig = "https://www.instagram.com/mercedes_sustentable";
 //BA.debugLineNum = 405;BA.debugLine="municipioFB = \"https://www.facebook.com/Mercede";
mostCurrent._municipiofb = "https://www.facebook.com/Mercedes.Sustentable";
 //BA.debugLineNum = 406;BA.debugLine="btnMunicipioTW.Visible = False";
mostCurrent._btnmunicipiotw.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 407;BA.debugLine="imgLogoMunicipio.Visible= True";
mostCurrent._imglogomunicipio.setVisible(anywheresoftware.b4a.keywords.Common.True);
 }else if((mostCurrent._main._struserorg /*String*/ ).equals("San Antonio De Areco")) { 
 //BA.debugLineNum = 409;BA.debugLine="imgLogoMunicipio.Bitmap = LoadBitmap(File.DirAs";
mostCurrent._imglogomunicipio.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"sanantoniodeareco.png").getObject()));
 //BA.debugLineNum = 410;BA.debugLine="imgLogoMunicipio.Height = 70dip";
mostCurrent._imglogomunicipio.setHeight(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (70)));
 //BA.debugLineNum = 411;BA.debugLine="imgLogoMunicipio.Width = 70dip";
mostCurrent._imglogomunicipio.setWidth(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (70)));
 //BA.debugLineNum = 412;BA.debugLine="WebView1.loadhtml(File.ReadString(File.DirAsset";
mostCurrent._webview1.LoadHtml(anywheresoftware.b4a.keywords.Common.File.ReadString(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"areco.html"));
 //BA.debugLineNum = 413;BA.debugLine="WebView1.Visible = True";
mostCurrent._webview1.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 414;BA.debugLine="lblMunicipioContent.Visible = False";
mostCurrent._lblmunicipiocontent.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 415;BA.debugLine="municipioURL = \"https://www.areco.gob.ar\"";
mostCurrent._municipiourl = "https://www.areco.gob.ar";
 //BA.debugLineNum = 416;BA.debugLine="btnMasInfoMunicipio.visible = True";
mostCurrent._btnmasinfomunicipio.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 417;BA.debugLine="btnMunicipioFB.Visible = False";
mostCurrent._btnmunicipiofb.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 418;BA.debugLine="btnMunicipioYT.Visible = False";
mostCurrent._btnmunicipioyt.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 419;BA.debugLine="btnMunicipioIG.Visible = False";
mostCurrent._btnmunicipioig.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 420;BA.debugLine="btnMunicipioTW.Visible = False";
mostCurrent._btnmunicipiotw.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 421;BA.debugLine="imgLogoMunicipio.Visible= True";
mostCurrent._imglogomunicipio.setVisible(anywheresoftware.b4a.keywords.Common.True);
 }else {
 //BA.debugLineNum = 423;BA.debugLine="imgLogoMunicipio.Visible = False";
mostCurrent._imglogomunicipio.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 424;BA.debugLine="lblTitleMunicipio. Text = Main.strUserOrg";
mostCurrent._lbltitlemunicipio.setText(BA.ObjectToCharSequence(mostCurrent._main._struserorg /*String*/ ));
 //BA.debugLineNum = 425;BA.debugLine="WebView1.Visible = False";
mostCurrent._webview1.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 426;BA.debugLine="lblMunicipioContent.Visible = True";
mostCurrent._lblmunicipiocontent.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 427;BA.debugLine="btnMunicipioFB.Visible = False";
mostCurrent._btnmunicipiofb.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 428;BA.debugLine="btnMunicipioYT.Visible = False";
mostCurrent._btnmunicipioyt.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 429;BA.debugLine="btnMunicipioIG.Visible = False";
mostCurrent._btnmunicipioig.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 430;BA.debugLine="btnMunicipioTW.Visible = False";
mostCurrent._btnmunicipiotw.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 431;BA.debugLine="btnMasInfoMunicipio.Visible = False";
mostCurrent._btnmasinfomunicipio.setVisible(anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 433;BA.debugLine="lblLine1.Visible = False";
mostCurrent._lblline1.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 434;BA.debugLine="lblLine2.Visible = False";
mostCurrent._lblline2.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 435;BA.debugLine="lblLine3.Visible = True";
mostCurrent._lblline3.setVisible(anywheresoftware.b4a.keywords.Common.True);
 }else if(_position==2) { 
 //BA.debugLineNum = 437;BA.debugLine="lblLine1.Visible = False";
mostCurrent._lblline1.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 438;BA.debugLine="lblLine2.Visible = True";
mostCurrent._lblline2.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 439;BA.debugLine="lblLine3.Visible = False";
mostCurrent._lblline3.setVisible(anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 441;BA.debugLine="End Sub";
return "";
}
}
