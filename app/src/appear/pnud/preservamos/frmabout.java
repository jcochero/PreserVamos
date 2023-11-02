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

public class frmabout extends Activity implements B4AActivity{
	public static frmabout mostCurrent;
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
			processBA = new BA(this.getApplicationContext(), null, null, "appear.pnud.preservamos", "appear.pnud.preservamos.frmabout");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
            
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (frmabout).");
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
		activityBA = new BA(this, layout, processBA, "appear.pnud.preservamos", "appear.pnud.preservamos.frmabout");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "appear.pnud.preservamos.frmabout", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density, mostCurrent);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (frmabout) Create " + (isFirst ? "(first time)" : "") + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (frmabout) Resume **");
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
		return frmabout.class;
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
            BA.LogInfo("** Activity (frmabout) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
        else
            BA.LogInfo("** Activity (frmabout) Pause event (activity is not paused). **");
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
            frmabout mc = mostCurrent;
			if (mc == null || mc != activity.get())
				return;
			processBA.setActivityPaused(false);
            BA.LogInfo("** Activity (frmabout) Resume **");
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
public anywheresoftware.b4a.phone.Phone.PhoneIntents _p = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblversion = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _fcbicon = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imgcc = null;
public anywheresoftware.b4a.objects.TabStripViewPager _tabstripabout = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblteam = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbllicencia = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblmaintext = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblline3 = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblline1 = null;
public anywheresoftware.b4a.objects.LabelWrapper _btnsponsors = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblline2 = null;
public b4a.example.dateutils _dateutils = null;
public appear.pnud.preservamos.main _main = null;
public appear.pnud.preservamos.form_main _form_main = null;
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
 //BA.debugLineNum = 38;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
 //BA.debugLineNum = 39;BA.debugLine="Activity.LoadLayout(\"layAbout_Panels\")";
mostCurrent._activity.LoadLayout("layAbout_Panels",mostCurrent.activityBA);
 //BA.debugLineNum = 40;BA.debugLine="tabStripAbout.LoadLayout(\"layAbout_Project\", \"El";
mostCurrent._tabstripabout.LoadLayout("layAbout_Project",BA.ObjectToCharSequence("El proyecto"));
 //BA.debugLineNum = 41;BA.debugLine="tabStripAbout.LoadLayout(\"layAbout_People\", \"El e";
mostCurrent._tabstripabout.LoadLayout("layAbout_People",BA.ObjectToCharSequence("El equipo"));
 //BA.debugLineNum = 42;BA.debugLine="tabStripAbout.LoadLayout(\"layAbout_Sponsors\", \"Ap";
mostCurrent._tabstripabout.LoadLayout("layAbout_Sponsors",BA.ObjectToCharSequence("Apoyan"));
 //BA.debugLineNum = 43;BA.debugLine="tabStripAbout.ScrollTo(0,True)";
mostCurrent._tabstripabout.ScrollTo((int) (0),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 44;BA.debugLine="lblLine1.Visible = True";
mostCurrent._lblline1.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 45;BA.debugLine="lblLine3.Visible = False";
mostCurrent._lblline3.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 46;BA.debugLine="lblLine2.Visible = False";
mostCurrent._lblline2.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 47;BA.debugLine="lblVersion.Text = Application.VersionCode";
mostCurrent._lblversion.setText(BA.ObjectToCharSequence(anywheresoftware.b4a.keywords.Common.Application.getVersionCode()));
 //BA.debugLineNum = 48;BA.debugLine="End Sub";
return "";
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 53;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 55;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 50;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 51;BA.debugLine="End Sub";
return "";
}
public static String  _btncerrarabout_click() throws Exception{
 //BA.debugLineNum = 59;BA.debugLine="Sub btnCerrarAbout_Click";
 //BA.debugLineNum = 60;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 61;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 62;BA.debugLine="End Sub";
return "";
}
public static String  _btnequipo_click() throws Exception{
 //BA.debugLineNum = 110;BA.debugLine="Private Sub btnEquipo_Click";
 //BA.debugLineNum = 111;BA.debugLine="tabStripAbout.ScrollTo(1,True)";
mostCurrent._tabstripabout.ScrollTo((int) (1),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 112;BA.debugLine="lblLine1.Visible = False";
mostCurrent._lblline1.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 113;BA.debugLine="lblLine3.Visible = True";
mostCurrent._lblline3.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 114;BA.debugLine="lblLine2.Visible = False";
mostCurrent._lblline2.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 115;BA.debugLine="End Sub";
return "";
}
public static String  _btnproyecto_click() throws Exception{
 //BA.debugLineNum = 103;BA.debugLine="Private Sub btnProyecto_Click";
 //BA.debugLineNum = 104;BA.debugLine="tabStripAbout.ScrollTo(0,True)";
mostCurrent._tabstripabout.ScrollTo((int) (0),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 105;BA.debugLine="lblLine1.Visible = True";
mostCurrent._lblline1.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 106;BA.debugLine="lblLine3.Visible = False";
mostCurrent._lblline3.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 107;BA.debugLine="lblLine2.Visible = False";
mostCurrent._lblline2.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 108;BA.debugLine="End Sub";
return "";
}
public static String  _btnsponsors_click() throws Exception{
 //BA.debugLineNum = 118;BA.debugLine="Private Sub btnSponsors_Click";
 //BA.debugLineNum = 119;BA.debugLine="tabStripAbout.ScrollTo(2,True)";
mostCurrent._tabstripabout.ScrollTo((int) (2),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 120;BA.debugLine="lblLine1.Visible = False";
mostCurrent._lblline1.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 121;BA.debugLine="lblLine3.Visible = False";
mostCurrent._lblline3.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 122;BA.debugLine="lblLine2.Visible = True";
mostCurrent._lblline2.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 123;BA.debugLine="End Sub";
return "";
}
public static String  _fcbicon_click() throws Exception{
 //BA.debugLineNum = 64;BA.debugLine="Sub fcbIcon_Click";
 //BA.debugLineNum = 65;BA.debugLine="StartActivity(p.OpenBrowser(\"https://www.facebook";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._p.OpenBrowser("https://www.facebook.com/preservamos.ar/")));
 //BA.debugLineNum = 66;BA.debugLine="End Sub";
return "";
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 11;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 13;BA.debugLine="Dim p As PhoneIntents";
mostCurrent._p = new anywheresoftware.b4a.phone.Phone.PhoneIntents();
 //BA.debugLineNum = 14;BA.debugLine="Private lblVersion As Label";
mostCurrent._lblversion = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 16;BA.debugLine="Private fcbIcon As ImageView";
mostCurrent._fcbicon = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 17;BA.debugLine="Private imgCC As ImageView";
mostCurrent._imgcc = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 18;BA.debugLine="Private lblVersion As Label";
mostCurrent._lblversion = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 23;BA.debugLine="Private tabStripAbout As TabStrip";
mostCurrent._tabstripabout = new anywheresoftware.b4a.objects.TabStripViewPager();
 //BA.debugLineNum = 26;BA.debugLine="Private lblTeam As Label";
mostCurrent._lblteam = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 28;BA.debugLine="Private lblLicencia As Label";
mostCurrent._lbllicencia = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 29;BA.debugLine="Private lblMainText As Label";
mostCurrent._lblmaintext = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 32;BA.debugLine="Private lblLine3 As Label";
mostCurrent._lblline3 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 33;BA.debugLine="Private lblLine1 As Label";
mostCurrent._lblline1 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 34;BA.debugLine="Private btnSponsors As Label";
mostCurrent._btnsponsors = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 35;BA.debugLine="Private lblLine2 As Label";
mostCurrent._lblline2 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 36;BA.debugLine="End Sub";
return "";
}
public static String  _imgcc_click() throws Exception{
 //BA.debugLineNum = 96;BA.debugLine="Sub imgCC_Click";
 //BA.debugLineNum = 97;BA.debugLine="StartActivity(p.OpenBrowser(\"https://creativecomm";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._p.OpenBrowser("https://creativecommons.org/licenses/by-nc/2.5/ar/")));
 //BA.debugLineNum = 98;BA.debugLine="End Sub";
return "";
}
public static String  _imgig_click() throws Exception{
 //BA.debugLineNum = 72;BA.debugLine="Private Sub imgIG_Click";
 //BA.debugLineNum = 73;BA.debugLine="StartActivity(p.OpenBrowser(\"https://www.instagra";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._p.OpenBrowser("https://www.instagram.com/preservamos.ar/")));
 //BA.debugLineNum = 74;BA.debugLine="End Sub";
return "";
}
public static String  _imgwebsite_click() throws Exception{
 //BA.debugLineNum = 68;BA.debugLine="Sub imgWebsite_Click";
 //BA.debugLineNum = 69;BA.debugLine="StartActivity(p.OpenBrowser(\"http://www.preservam";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._p.OpenBrowser("http://www.preservamos.ar")));
 //BA.debugLineNum = 70;BA.debugLine="End Sub";
return "";
}
public static String  _label1_click() throws Exception{
 //BA.debugLineNum = 76;BA.debugLine="Sub Label1_Click";
 //BA.debugLineNum = 82;BA.debugLine="tabStripAbout.ScrollTo(0,True)";
mostCurrent._tabstripabout.ScrollTo((int) (0),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 83;BA.debugLine="lblLine1.Visible = True";
mostCurrent._lblline1.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 84;BA.debugLine="lblLine3.Visible = False";
mostCurrent._lblline3.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 85;BA.debugLine="End Sub";
return "";
}
public static String  _label3_click() throws Exception{
 //BA.debugLineNum = 87;BA.debugLine="Sub Label3_Click";
 //BA.debugLineNum = 88;BA.debugLine="tabStripAbout.ScrollTo(1,True)";
mostCurrent._tabstripabout.ScrollTo((int) (1),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 89;BA.debugLine="lblLine1.Visible = False";
mostCurrent._lblline1.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 90;BA.debugLine="lblLine2.Visible = False";
mostCurrent._lblline2.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 91;BA.debugLine="lblLine3.Visible = True";
mostCurrent._lblline3.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 92;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 6;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 9;BA.debugLine="End Sub";
return "";
}
public static String  _tabstripabout_pageselected(int _position) throws Exception{
 //BA.debugLineNum = 126;BA.debugLine="Sub tabStripAbout_PageSelected (Position As Int)";
 //BA.debugLineNum = 127;BA.debugLine="If Position = 0 Then";
if (_position==0) { 
 //BA.debugLineNum = 128;BA.debugLine="lblLine1.Visible = True";
mostCurrent._lblline1.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 129;BA.debugLine="lblLine3.Visible = False";
mostCurrent._lblline3.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 130;BA.debugLine="lblLine2.Visible = False";
mostCurrent._lblline2.setVisible(anywheresoftware.b4a.keywords.Common.False);
 }else if(_position==1) { 
 //BA.debugLineNum = 132;BA.debugLine="lblLine1.Visible = False";
mostCurrent._lblline1.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 133;BA.debugLine="lblLine3.Visible = True";
mostCurrent._lblline3.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 134;BA.debugLine="lblLine2.Visible = False";
mostCurrent._lblline2.setVisible(anywheresoftware.b4a.keywords.Common.False);
 }else if(_position==2) { 
 //BA.debugLineNum = 136;BA.debugLine="lblLine1.Visible = False";
mostCurrent._lblline1.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 137;BA.debugLine="lblLine3.Visible = False";
mostCurrent._lblline3.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 138;BA.debugLine="lblLine2.Visible = True";
mostCurrent._lblline2.setVisible(anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 140;BA.debugLine="End Sub";
return "";
}
}
