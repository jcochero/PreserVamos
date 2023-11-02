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

public class inatcheck extends Activity implements B4AActivity{
	public static inatcheck mostCurrent;
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
			processBA = new BA(this.getApplicationContext(), null, null, "appear.pnud.preservamos", "appear.pnud.preservamos.inatcheck");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
            
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (inatcheck).");
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
		activityBA = new BA(this, layout, processBA, "appear.pnud.preservamos", "appear.pnud.preservamos.inatcheck");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "appear.pnud.preservamos.inatcheck", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density, mostCurrent);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (inatcheck) Create " + (isFirst ? "(first time)" : "") + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (inatcheck) Resume **");
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
		return inatcheck.class;
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
            BA.LogInfo("** Activity (inatcheck) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
        else
            BA.LogInfo("** Activity (inatcheck) Pause event (activity is not paused). **");
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
            inatcheck mc = mostCurrent;
			if (mc == null || mc != activity.get())
				return;
			processBA.setActivityPaused(false);
            BA.LogInfo("** Activity (inatcheck) Resume **");
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
public static anywheresoftware.b4a.objects.collections.List _imagelinks = null;
public static anywheresoftware.b4a.objects.collections.List _prefered_common_namelist = null;
public static anywheresoftware.b4a.objects.collections.List _iconicnamelist = null;
public static anywheresoftware.b4a.objects.collections.List _scientific_namelist = null;
public static anywheresoftware.b4a.objects.collections.List _wikilinklist = null;
public static anywheresoftware.b4a.objects.collections.List _threatlist = null;
public static anywheresoftware.b4a.objects.collections.List _attributionlist = null;
public static anywheresoftware.b4a.objects.RuntimePermissions _rp = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbltitulochecklist = null;
public static String _currentscreen = "";
public anywheresoftware.b4a.objects.ButtonWrapper _butplantas = null;
public anywheresoftware.b4a.objects.ButtonWrapper _butfungi = null;
public anywheresoftware.b4a.objects.ButtonWrapper _butaves = null;
public anywheresoftware.b4a.objects.ButtonWrapper _butbugs = null;
public anywheresoftware.b4a.objects.ButtonWrapper _butpeces = null;
public anywheresoftware.b4a.objects.ButtonWrapper _butmammals = null;
public static String _nelat = "";
public static String _nelng = "";
public static String _swlat = "";
public static String _swlng = "";
public b4a.example3.customlistview _clvchecklist = null;
public anywheresoftware.b4a.phone.Phone _phone1 = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblnombrecomun = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblnombrecientifico = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imageview1 = null;
public anywheresoftware.b4a.objects.collections.List _imageviews = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbllat = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbllon = null;
public anywheresoftware.b4a.objects.MapFragmentWrapper.GoogleMapWrapper _gmap = null;
public anywheresoftware.b4a.objects.MapFragmentWrapper _mapfragment1 = null;
public anywheresoftware.b4a.objects.MapFragmentWrapper.LatLngWrapper _mylocation = null;
public anywheresoftware.b4a.objects.B4XViewWrapper.XUI _xui = null;
public anywheresoftware.b4a.objects.collections.Map _m = null;
public anywheresoftware.b4a.objects.SeekBarWrapper _seekbar1 = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblradiotitle = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblradio = null;
public static double _radiovalue = 0;
public anywheresoftware.b4a.objects.LabelWrapper _lblmasinfowiki_detail = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imageview1_detail = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblnombrecientifico_detail = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblnombrecomun_detail = null;
public static String _lblwikiurl = "";
public static String _lblnombrecomundetail = "";
public anywheresoftware.b4a.objects.LabelWrapper _lblatribucion_detail = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper _chkimagenes = null;
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
public appear.pnud.preservamos.frmtiporeporte _frmtiporeporte = null;
public appear.pnud.preservamos.httputils2service _httputils2service = null;
public appear.pnud.preservamos.imagedownloader _imagedownloader = null;
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
 //BA.debugLineNum = 77;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
 //BA.debugLineNum = 79;BA.debugLine="phone1.SetScreenOrientation(1)";
mostCurrent._phone1.SetScreenOrientation(processBA,(int) (1));
 //BA.debugLineNum = 81;BA.debugLine="Activity.LoadLayout(\"layCheckLists_Home\")";
mostCurrent._activity.LoadLayout("layCheckLists_Home",mostCurrent.activityBA);
 //BA.debugLineNum = 83;BA.debugLine="currentScreen = \"CheckListHome\"";
mostCurrent._currentscreen = "CheckListHome";
 //BA.debugLineNum = 84;BA.debugLine="radioValue = 0.1";
_radiovalue = 0.1;
 //BA.debugLineNum = 85;BA.debugLine="SeekBar1.Value = 11";
mostCurrent._seekbar1.setValue((int) (11));
 //BA.debugLineNum = 87;BA.debugLine="End Sub";
return "";
}
public static boolean  _activity_keypress(int _keycode) throws Exception{
 //BA.debugLineNum = 95;BA.debugLine="Sub Activity_KeyPress (KeyCode As Int) As Boolean";
 //BA.debugLineNum = 96;BA.debugLine="If KeyCode = KeyCodes.KEYCODE_BACK Then";
if (_keycode==anywheresoftware.b4a.keywords.Common.KeyCodes.KEYCODE_BACK) { 
 //BA.debugLineNum = 97;BA.debugLine="If currentScreen = \"CheckListHome\" Then";
if ((mostCurrent._currentscreen).equals("CheckListHome")) { 
 //BA.debugLineNum = 98;BA.debugLine="Activity.finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 99;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 100;BA.debugLine="Return True";
if (true) return anywheresoftware.b4a.keywords.Common.True;
 }else if((mostCurrent._currentscreen).equals("CheckListList")) { 
 //BA.debugLineNum = 102;BA.debugLine="CallSub(ImageDownloader, \"CancelEverything\")";
anywheresoftware.b4a.keywords.Common.CallSubNew(processBA,(Object)(mostCurrent._imagedownloader.getObject()),"CancelEverything");
 //BA.debugLineNum = 103;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 104;BA.debugLine="Activity.LoadLayout(\"layCheckLists_Home\")";
mostCurrent._activity.LoadLayout("layCheckLists_Home",mostCurrent.activityBA);
 //BA.debugLineNum = 105;BA.debugLine="currentScreen = \"CheckListHome\"";
mostCurrent._currentscreen = "CheckListHome";
 //BA.debugLineNum = 106;BA.debugLine="Return True";
if (true) return anywheresoftware.b4a.keywords.Common.True;
 };
 };
 //BA.debugLineNum = 110;BA.debugLine="End Sub";
return false;
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 91;BA.debugLine="Sub Activity_Pause(UserClosed As Boolean)";
 //BA.debugLineNum = 92;BA.debugLine="CallSub(ImageDownloader, \"ActivityIsPaused\")";
anywheresoftware.b4a.keywords.Common.CallSubNew(processBA,(Object)(mostCurrent._imagedownloader.getObject()),"ActivityIsPaused");
 //BA.debugLineNum = 93;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 88;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 89;BA.debugLine="SeekBar1.Value = radioValue * 100";
mostCurrent._seekbar1.setValue((int) (_radiovalue*100));
 //BA.debugLineNum = 90;BA.debugLine="End Sub";
return "";
}
public static String  _btncerrarcheclist_click() throws Exception{
 //BA.debugLineNum = 452;BA.debugLine="Sub btnCerrarChecList_Click";
 //BA.debugLineNum = 453;BA.debugLine="CallSub(ImageDownloader, \"CancelEverything\")";
anywheresoftware.b4a.keywords.Common.CallSubNew(processBA,(Object)(mostCurrent._imagedownloader.getObject()),"CancelEverything");
 //BA.debugLineNum = 454;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 455;BA.debugLine="Activity.LoadLayout(\"layCheckLists_Home\")";
mostCurrent._activity.LoadLayout("layCheckLists_Home",mostCurrent.activityBA);
 //BA.debugLineNum = 456;BA.debugLine="currentScreen = \"CheckListHome\"";
mostCurrent._currentscreen = "CheckListHome";
 //BA.debugLineNum = 457;BA.debugLine="End Sub";
return "";
}
public static String  _btncerrarckecklisthome_click() throws Exception{
 //BA.debugLineNum = 459;BA.debugLine="Sub btnCerrarCkeckListHome_Click";
 //BA.debugLineNum = 460;BA.debugLine="CallSub(ImageDownloader, \"CancelEverything\")";
anywheresoftware.b4a.keywords.Common.CallSubNew(processBA,(Object)(mostCurrent._imagedownloader.getObject()),"CancelEverything");
 //BA.debugLineNum = 461;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 462;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 463;BA.debugLine="End Sub";
return "";
}
public static String  _btncerraritem_click() throws Exception{
 //BA.debugLineNum = 421;BA.debugLine="Private Sub btnCerrarItem_Click";
 //BA.debugLineNum = 422;BA.debugLine="Activity.RemoveViewAt(Activity.NumberOfViews - 1)";
mostCurrent._activity.RemoveViewAt((int) (mostCurrent._activity.getNumberOfViews()-1));
 //BA.debugLineNum = 423;BA.debugLine="Activity.RemoveViewAt(Activity.NumberOfViews - 1)";
mostCurrent._activity.RemoveViewAt((int) (mostCurrent._activity.getNumberOfViews()-1));
 //BA.debugLineNum = 424;BA.debugLine="Activity.RemoveViewAt(Activity.NumberOfViews - 1)";
mostCurrent._activity.RemoveViewAt((int) (mostCurrent._activity.getNumberOfViews()-1));
 //BA.debugLineNum = 425;BA.debugLine="End Sub";
return "";
}
public static String  _builditems(b4a.example3.customlistview _clv) throws Exception{
int _i = 0;
anywheresoftware.b4a.objects.PanelWrapper _pa = null;
anywheresoftware.b4a.objects.collections.Map _valuemap = null;
 //BA.debugLineNum = 323;BA.debugLine="Sub BuildItems (clv As CustomListView)";
 //BA.debugLineNum = 324;BA.debugLine="If Imagelinks.Size = 0 Then Return";
if (_imagelinks.getSize()==0) { 
if (true) return "";};
 //BA.debugLineNum = 326;BA.debugLine="clv.Clear";
_clv._clear();
 //BA.debugLineNum = 328;BA.debugLine="m.Initialize";
mostCurrent._m.Initialize();
 //BA.debugLineNum = 329;BA.debugLine="For i = 0 To Imagelinks.Size - 1";
{
final int step4 = 1;
final int limit4 = (int) (_imagelinks.getSize()-1);
_i = (int) (0) ;
for (;_i <= limit4 ;_i = _i + step4 ) {
 //BA.debugLineNum = 330;BA.debugLine="Dim pA As Panel";
_pa = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 331;BA.debugLine="pA.Initialize(\"\")";
_pa.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 332;BA.debugLine="Dim valueMap As Map";
_valuemap = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 333;BA.debugLine="valueMap.Initialize";
_valuemap.Initialize();
 //BA.debugLineNum = 334;BA.debugLine="valueMap.Put(\"nombrecomun\", prefered_common_name";
_valuemap.Put((Object)("nombrecomun"),_prefered_common_namelist.Get(_i));
 //BA.debugLineNum = 335;BA.debugLine="valueMap.Put(\"nombrecientifico\", scientific_name";
_valuemap.Put((Object)("nombrecientifico"),_scientific_namelist.Get(_i));
 //BA.debugLineNum = 336;BA.debugLine="valueMap.Put(\"threatlevel\", threatList.Get(i))";
_valuemap.Put((Object)("threatlevel"),_threatlist.Get(_i));
 //BA.debugLineNum = 337;BA.debugLine="valueMap.Put(\"wikilink\", wikilinkList.Get(i))";
_valuemap.Put((Object)("wikilink"),_wikilinklist.Get(_i));
 //BA.debugLineNum = 338;BA.debugLine="valueMap.Put(\"attribution\", attributionList.Get(";
_valuemap.Put((Object)("attribution"),_attributionlist.Get(_i));
 //BA.debugLineNum = 339;BA.debugLine="valueMap.Put(\"medium_photo\", Imagelinks.Get(i))";
_valuemap.Put((Object)("medium_photo"),_imagelinks.Get(_i));
 //BA.debugLineNum = 340;BA.debugLine="clv.Add(CreateListItem(valueMap, clv.AsView.Widt";
_clv._add((anywheresoftware.b4a.objects.B4XViewWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.B4XViewWrapper(), (java.lang.Object)(_createlistitem(_valuemap,_clv._asview().getWidth(),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (100))).getObject())),(Object)(_valuemap.getObject()));
 }
};
 //BA.debugLineNum = 343;BA.debugLine="If chkImagenes.Checked = True Then";
if (mostCurrent._chkimagenes.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 344;BA.debugLine="CallSubDelayed2(ImageDownloader, \"Download\", m)";
anywheresoftware.b4a.keywords.Common.CallSubDelayed2(processBA,(Object)(mostCurrent._imagedownloader.getObject()),"Download",(Object)(mostCurrent._m));
 };
 //BA.debugLineNum = 347;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 //BA.debugLineNum = 349;BA.debugLine="End Sub";
return "";
}
public static String  _butaves_click() throws Exception{
 //BA.debugLineNum = 140;BA.debugLine="Sub butAves_Click";
 //BA.debugLineNum = 141;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 142;BA.debugLine="ProgressDialogShow2(\"Buscando especies de aves r";
anywheresoftware.b4a.keywords.Common.ProgressDialogShow2(mostCurrent.activityBA,BA.ObjectToCharSequence("Buscando especies de aves reportadas en la zona..."),anywheresoftware.b4a.keywords.Common.False);
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 144;BA.debugLine="ProgressDialogShow2(\"Searching for species of bi";
anywheresoftware.b4a.keywords.Common.ProgressDialogShow2(mostCurrent.activityBA,BA.ObjectToCharSequence("Searching for species of birds reported in the area..."),anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 146;BA.debugLine="GetLocation(\"Aves\")";
_getlocation("Aves");
 //BA.debugLineNum = 147;BA.debugLine="End Sub";
return "";
}
public static String  _butbugs_click() throws Exception{
 //BA.debugLineNum = 148;BA.debugLine="Sub butBugs_Click";
 //BA.debugLineNum = 149;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 150;BA.debugLine="ProgressDialogShow2(\"Buscando especies de invert";
anywheresoftware.b4a.keywords.Common.ProgressDialogShow2(mostCurrent.activityBA,BA.ObjectToCharSequence("Buscando especies de invertebrados reportados en la zona..."),anywheresoftware.b4a.keywords.Common.False);
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 152;BA.debugLine="ProgressDialogShow2(\"Searching for species of in";
anywheresoftware.b4a.keywords.Common.ProgressDialogShow2(mostCurrent.activityBA,BA.ObjectToCharSequence("Searching for species of invertebrates reported in the area..."),anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 154;BA.debugLine="GetLocation(\"Insecta,Arachnida,Mollusca\")";
_getlocation("Insecta,Arachnida,Mollusca");
 //BA.debugLineNum = 156;BA.debugLine="End Sub";
return "";
}
public static String  _butfungi_click() throws Exception{
 //BA.debugLineNum = 157;BA.debugLine="Sub butFungi_Click";
 //BA.debugLineNum = 158;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 159;BA.debugLine="ProgressDialogShow2(\"Buscando especies de microo";
anywheresoftware.b4a.keywords.Common.ProgressDialogShow2(mostCurrent.activityBA,BA.ObjectToCharSequence("Buscando especies de microorganismos y hongos reportados en la zona..."),anywheresoftware.b4a.keywords.Common.False);
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 161;BA.debugLine="ProgressDialogShow2(\"Searching for species of mi";
anywheresoftware.b4a.keywords.Common.ProgressDialogShow2(mostCurrent.activityBA,BA.ObjectToCharSequence("Searching for species of microorganisms and fungi reported in the area..."),anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 163;BA.debugLine="GetLocation(\"Protozoa,Fungi\")";
_getlocation("Protozoa,Fungi");
 //BA.debugLineNum = 165;BA.debugLine="End Sub";
return "";
}
public static String  _butmammals_click() throws Exception{
 //BA.debugLineNum = 123;BA.debugLine="Sub butMammals_Click";
 //BA.debugLineNum = 124;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 125;BA.debugLine="ProgressDialogShow2(\"Buscando especies de mamífe";
anywheresoftware.b4a.keywords.Common.ProgressDialogShow2(mostCurrent.activityBA,BA.ObjectToCharSequence("Buscando especies de mamíferos reportados en la zona..."),anywheresoftware.b4a.keywords.Common.False);
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 127;BA.debugLine="ProgressDialogShow2(\"Searching for species of ma";
anywheresoftware.b4a.keywords.Common.ProgressDialogShow2(mostCurrent.activityBA,BA.ObjectToCharSequence("Searching for species of mammals reported in the area..."),anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 130;BA.debugLine="GetLocation(\"Mammalia\")";
_getlocation("Mammalia");
 //BA.debugLineNum = 131;BA.debugLine="End Sub";
return "";
}
public static String  _butpeces_click() throws Exception{
 //BA.debugLineNum = 132;BA.debugLine="Sub butPeces_Click";
 //BA.debugLineNum = 133;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 134;BA.debugLine="ProgressDialogShow2(\"Buscando especies de peces,";
anywheresoftware.b4a.keywords.Common.ProgressDialogShow2(mostCurrent.activityBA,BA.ObjectToCharSequence("Buscando especies de peces, reptiles y anfíbios reportados en la zona..."),anywheresoftware.b4a.keywords.Common.False);
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 136;BA.debugLine="ProgressDialogShow2(\"Searching for species of fi";
anywheresoftware.b4a.keywords.Common.ProgressDialogShow2(mostCurrent.activityBA,BA.ObjectToCharSequence("Searching for species of fishes, reptiles and amphibians reported in the area..."),anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 138;BA.debugLine="GetLocation(\"Actinopterigii,Reptilia,Amphibia\")";
_getlocation("Actinopterigii,Reptilia,Amphibia");
 //BA.debugLineNum = 139;BA.debugLine="End Sub";
return "";
}
public static String  _butplantas_click() throws Exception{
 //BA.debugLineNum = 166;BA.debugLine="Sub butPlantas_Click";
 //BA.debugLineNum = 168;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 169;BA.debugLine="ProgressDialogShow2(\"Buscando especies de planta";
anywheresoftware.b4a.keywords.Common.ProgressDialogShow2(mostCurrent.activityBA,BA.ObjectToCharSequence("Buscando especies de plantas reportadas en la zona..."),anywheresoftware.b4a.keywords.Common.False);
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 171;BA.debugLine="ProgressDialogShow2(\"Searching for species of pl";
anywheresoftware.b4a.keywords.Common.ProgressDialogShow2(mostCurrent.activityBA,BA.ObjectToCharSequence("Searching for species of plants reported in the area..."),anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 173;BA.debugLine="GetLocation(\"Plantae\")";
_getlocation("Plantae");
 //BA.debugLineNum = 174;BA.debugLine="End Sub";
return "";
}
public static String  _clvchecklist_itemclick(int _index,Object _value) throws Exception{
anywheresoftware.b4a.objects.collections.Map _valuemap = null;
anywheresoftware.b4a.objects.B4XViewWrapper _pnl = null;
Object _v = null;
anywheresoftware.b4a.objects.ImageViewWrapper _vv = null;
 //BA.debugLineNum = 366;BA.debugLine="Sub clvCheckList_ItemClick (Index As Int, Value As";
 //BA.debugLineNum = 367;BA.debugLine="If chkImagenes.Checked = True Then";
if (mostCurrent._chkimagenes.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 369;BA.debugLine="Dim valuemap As Map";
_valuemap = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 370;BA.debugLine="valuemap.Initialize";
_valuemap.Initialize();
 //BA.debugLineNum = 371;BA.debugLine="valuemap = Value";
_valuemap = (anywheresoftware.b4a.objects.collections.Map) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.collections.Map(), (java.util.Map)(_value));
 //BA.debugLineNum = 373;BA.debugLine="Activity.LoadLayout(\"laychecklist_item_detail\")";
mostCurrent._activity.LoadLayout("laychecklist_item_detail",mostCurrent.activityBA);
 //BA.debugLineNum = 374;BA.debugLine="lblNombreCientifico_Detail.Text = valuemap.Get(\"";
mostCurrent._lblnombrecientifico_detail.setText(BA.ObjectToCharSequence(_valuemap.Get((Object)("nombrecientifico"))));
 //BA.debugLineNum = 375;BA.debugLine="lblNombreComun_Detail.Text = valuemap.Get(\"nombr";
mostCurrent._lblnombrecomun_detail.setText(BA.ObjectToCharSequence(_valuemap.Get((Object)("nombrecomun"))));
 //BA.debugLineNum = 378;BA.debugLine="Dim pnl As B4XView = clvCheckList.GetPanel(Index";
_pnl = new anywheresoftware.b4a.objects.B4XViewWrapper();
_pnl = mostCurrent._clvchecklist._getpanel(_index);
 //BA.debugLineNum = 379;BA.debugLine="For Each v As Object In pnl.GetAllViewsRecursive";
{
final anywheresoftware.b4a.BA.IterableList group9 = _pnl.GetAllViewsRecursive();
final int groupLen9 = group9.getSize()
;int index9 = 0;
;
for (; index9 < groupLen9;index9++){
_v = group9.Get(index9);
 //BA.debugLineNum = 380;BA.debugLine="If V Is ImageView Then";
if (_v instanceof android.widget.ImageView) { 
 //BA.debugLineNum = 381;BA.debugLine="Dim vv = v As ImageView";
_vv = new anywheresoftware.b4a.objects.ImageViewWrapper();
_vv = (anywheresoftware.b4a.objects.ImageViewWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.ImageViewWrapper(), (android.widget.ImageView)(_v));
 //BA.debugLineNum = 382;BA.debugLine="ImageView1_Detail.Bitmap = vv.Bitmap";
mostCurrent._imageview1_detail.setBitmap(_vv.getBitmap());
 };
 }
};
 //BA.debugLineNum = 385;BA.debugLine="lblAtribucion_Detail.Text = valuemap.Get(\"attrib";
mostCurrent._lblatribucion_detail.setText(BA.ObjectToCharSequence(_valuemap.Get((Object)("attribution"))));
 //BA.debugLineNum = 387;BA.debugLine="lblWikiURL = valuemap.Get(\"wikilink\")";
mostCurrent._lblwikiurl = BA.ObjectToString(_valuemap.Get((Object)("wikilink")));
 //BA.debugLineNum = 388;BA.debugLine="lblNombreComunDetail = valuemap.Get(\"nombrecomun";
mostCurrent._lblnombrecomundetail = BA.ObjectToString(_valuemap.Get((Object)("nombrecomun")));
 };
 //BA.debugLineNum = 392;BA.debugLine="End Sub";
return "";
}
public static anywheresoftware.b4a.objects.PanelWrapper  _createlistitem(anywheresoftware.b4a.objects.collections.Map _vm,int _width,int _height) throws Exception{
anywheresoftware.b4a.objects.B4XViewWrapper _p = null;
 //BA.debugLineNum = 352;BA.debugLine="Sub CreateListItem(vm As Map, Width As Int, Height";
 //BA.debugLineNum = 353;BA.debugLine="Dim p As B4XView = xui.CreatePanel(\"\")";
_p = new anywheresoftware.b4a.objects.B4XViewWrapper();
_p = mostCurrent._xui.CreatePanel(processBA,"");
 //BA.debugLineNum = 354;BA.debugLine="p.SetLayoutAnimated(0, 0, 0, Width, Height)";
_p.SetLayoutAnimated((int) (0),(int) (0),(int) (0),_width,_height);
 //BA.debugLineNum = 355;BA.debugLine="p.LoadLayout(\"layCheckList_Item\")";
_p.LoadLayout("layCheckList_Item",mostCurrent.activityBA);
 //BA.debugLineNum = 357;BA.debugLine="lblNombreComun.Text = vm.Get(\"nombrecomun\")";
mostCurrent._lblnombrecomun.setText(BA.ObjectToCharSequence(_vm.Get((Object)("nombrecomun"))));
 //BA.debugLineNum = 358;BA.debugLine="lblNombreCientifico.Text = vm.Get(\"nombrecientifi";
mostCurrent._lblnombrecientifico.setText(BA.ObjectToCharSequence(_vm.Get((Object)("nombrecientifico"))));
 //BA.debugLineNum = 361;BA.debugLine="m.Put(ImageView1, vm.Get(\"medium_photo\"))";
mostCurrent._m.Put((Object)(mostCurrent._imageview1.getObject()),_vm.Get((Object)("medium_photo")));
 //BA.debugLineNum = 362;BA.debugLine="Return p";
if (true) return (anywheresoftware.b4a.objects.PanelWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.PanelWrapper(), (android.view.ViewGroup)(_p.getObject()));
 //BA.debugLineNum = 363;BA.debugLine="End Sub";
return null;
}
public static String  _getinattaxa_complete(appear.pnud.preservamos.httpjob _job) throws Exception{
anywheresoftware.b4a.objects.collections.JSONParser _json = null;
anywheresoftware.b4a.objects.collections.Map _map1 = null;
anywheresoftware.b4a.objects.collections.Map _ma = null;
anywheresoftware.b4a.objects.collections.Map _msp = null;
anywheresoftware.b4a.objects.collections.Map _mimg = null;
anywheresoftware.b4a.objects.collections.List _menuitems = null;
int _i = 0;
String _iconicname = "";
String _prefered_common_name = "";
String _threatlevel = "";
String _scientificname = "";
String _wikilink = "";
String _attribution = "";
 //BA.debugLineNum = 274;BA.debugLine="Sub GetiNatTaxa_Complete(Job As HttpJob)";
 //BA.debugLineNum = 275;BA.debugLine="Log(\"GetiNatTaxa messages: \" & Job.Success)";
anywheresoftware.b4a.keywords.Common.LogImpl("454657025","GetiNatTaxa messages: "+BA.ObjectToString(_job._success /*boolean*/ ),0);
 //BA.debugLineNum = 276;BA.debugLine="If Job.Success = True Then";
if (_job._success /*boolean*/ ==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 277;BA.debugLine="Dim JSON As JSONParser";
_json = new anywheresoftware.b4a.objects.collections.JSONParser();
 //BA.debugLineNum = 278;BA.debugLine="Dim Map1 As Map";
_map1 = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 279;BA.debugLine="JSON.Initialize(Job.GetString)";
_json.Initialize(_job._getstring /*String*/ ());
 //BA.debugLineNum = 280;BA.debugLine="Map1 = JSON.NextObject";
_map1 = _json.NextObject();
 //BA.debugLineNum = 281;BA.debugLine="Dim ma As Map 'helper map for navigating results";
_ma = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 282;BA.debugLine="Dim msp As Map ' map para la especie";
_msp = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 283;BA.debugLine="Dim mimg As Map 'map para la imagen";
_mimg = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 284;BA.debugLine="Dim MenuItems As List";
_menuitems = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 285;BA.debugLine="MenuItems = Map1.Get(\"results\")";
_menuitems = (anywheresoftware.b4a.objects.collections.List) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.collections.List(), (java.util.List)(_map1.Get((Object)("results"))));
 //BA.debugLineNum = 286;BA.debugLine="For i = 0 To MenuItems.Size - 1";
{
final int step12 = 1;
final int limit12 = (int) (_menuitems.getSize()-1);
_i = (int) (0) ;
for (;_i <= limit12 ;_i = _i + step12 ) {
 //BA.debugLineNum = 287;BA.debugLine="ma = MenuItems.Get(i)";
_ma = (anywheresoftware.b4a.objects.collections.Map) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.collections.Map(), (java.util.Map)(_menuitems.Get(_i)));
 //BA.debugLineNum = 289;BA.debugLine="msp = ma.Get(\"taxon\")";
_msp = (anywheresoftware.b4a.objects.collections.Map) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.collections.Map(), (java.util.Map)(_ma.Get((Object)("taxon"))));
 //BA.debugLineNum = 290;BA.debugLine="mimg = msp.Get(\"default_photo\")";
_mimg = (anywheresoftware.b4a.objects.collections.Map) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.collections.Map(), (java.util.Map)(_msp.Get((Object)("default_photo"))));
 //BA.debugLineNum = 291;BA.debugLine="Dim iconicname As String";
_iconicname = "";
 //BA.debugLineNum = 292;BA.debugLine="Dim prefered_common_name As String";
_prefered_common_name = "";
 //BA.debugLineNum = 293;BA.debugLine="Dim threatlevel As String";
_threatlevel = "";
 //BA.debugLineNum = 294;BA.debugLine="Dim scientificname As String";
_scientificname = "";
 //BA.debugLineNum = 295;BA.debugLine="Dim wikilink As String";
_wikilink = "";
 //BA.debugLineNum = 296;BA.debugLine="Dim attribution As String";
_attribution = "";
 //BA.debugLineNum = 297;BA.debugLine="iconicname = msp.Get(\"iconic_taxon_name\")";
_iconicname = BA.ObjectToString(_msp.Get((Object)("iconic_taxon_name")));
 //BA.debugLineNum = 298;BA.debugLine="prefered_common_name = msp.Get(\"preferred_commo";
_prefered_common_name = BA.ObjectToString(_msp.Get((Object)("preferred_common_name")));
 //BA.debugLineNum = 299;BA.debugLine="threatlevel = msp.Get(\"threatened\")";
_threatlevel = BA.ObjectToString(_msp.Get((Object)("threatened")));
 //BA.debugLineNum = 300;BA.debugLine="scientificname = msp.Get(\"name\")";
_scientificname = BA.ObjectToString(_msp.Get((Object)("name")));
 //BA.debugLineNum = 301;BA.debugLine="wikilink = msp.Get(\"wikipedia_url\")";
_wikilink = BA.ObjectToString(_msp.Get((Object)("wikipedia_url")));
 //BA.debugLineNum = 302;BA.debugLine="attribution  = mimg.Get(\"attribution\")";
_attribution = BA.ObjectToString(_mimg.Get((Object)("attribution")));
 //BA.debugLineNum = 303;BA.debugLine="If prefered_common_name = \"null\" Then";
if ((_prefered_common_name).equals("null")) { 
 //BA.debugLineNum = 304;BA.debugLine="prefered_common_name = msp.Get(\"name\")";
_prefered_common_name = BA.ObjectToString(_msp.Get((Object)("name")));
 };
 //BA.debugLineNum = 306;BA.debugLine="Imagelinks.Add(mimg.Get(\"medium_url\"))";
_imagelinks.Add(_mimg.Get((Object)("medium_url")));
 //BA.debugLineNum = 307;BA.debugLine="prefered_common_nameList.Add(prefered_common_na";
_prefered_common_namelist.Add((Object)(_prefered_common_name));
 //BA.debugLineNum = 308;BA.debugLine="iconicnameList.Add(iconicname)";
_iconicnamelist.Add((Object)(_iconicname));
 //BA.debugLineNum = 309;BA.debugLine="threatList.Add(threatlevel)";
_threatlist.Add((Object)(_threatlevel));
 //BA.debugLineNum = 310;BA.debugLine="scientific_nameList.Add(scientificname)";
_scientific_namelist.Add((Object)(_scientificname));
 //BA.debugLineNum = 311;BA.debugLine="wikilinkList.Add(wikilink)";
_wikilinklist.Add((Object)(_wikilink));
 //BA.debugLineNum = 312;BA.debugLine="attributionList.Add(attribution)";
_attributionlist.Add((Object)(_attribution));
 }
};
 //BA.debugLineNum = 315;BA.debugLine="BuildItems(clvCheckList)";
_builditems(mostCurrent._clvchecklist);
 }else {
 //BA.debugLineNum = 317;BA.debugLine="Log(Job.ErrorMessage)";
anywheresoftware.b4a.keywords.Common.LogImpl("454657067",_job._errormessage /*String*/ ,0);
 };
 //BA.debugLineNum = 319;BA.debugLine="End Sub";
return "";
}
public static void  _getlocation(String _taxa) throws Exception{
ResumableSub_GetLocation rsub = new ResumableSub_GetLocation(null,_taxa);
rsub.resume(processBA, null);
}
public static class ResumableSub_GetLocation extends BA.ResumableSub {
public ResumableSub_GetLocation(appear.pnud.preservamos.inatcheck parent,String _taxa) {
this.parent = parent;
this._taxa = _taxa;
}
appear.pnud.preservamos.inatcheck parent;
String _taxa;

@Override
public void resume(BA ba, Object[] result) throws Exception{

    while (true) {
        switch (state) {
            case -1:
return;

case 0:
//C
this.state = 1;
 //BA.debugLineNum = 182;BA.debugLine="gmap = MapFragment1.GetMap";
parent.mostCurrent._gmap = parent.mostCurrent._mapfragment1.GetMap();
 //BA.debugLineNum = 183;BA.debugLine="gmap.MyLocationEnabled = True";
parent.mostCurrent._gmap.setMyLocationEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 184;BA.debugLine="If gmap.IsInitialized = False Then";
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
 //BA.debugLineNum = 185;BA.debugLine="ToastMessageShow(\"Error initializing map.\", True";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Error initializing map."),anywheresoftware.b4a.keywords.Common.True);
 if (true) break;

case 5:
//C
this.state = 6;
 //BA.debugLineNum = 187;BA.debugLine="Do While Not(gmap.MyLocation.IsInitialized)";
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
 //BA.debugLineNum = 188;BA.debugLine="Sleep(0)";
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
 //BA.debugLineNum = 193;BA.debugLine="myLocation = gmap.MyLocation";
parent.mostCurrent._mylocation = parent.mostCurrent._gmap.getMyLocation();
 //BA.debugLineNum = 194;BA.debugLine="Log(myLocation.Latitude)";
anywheresoftware.b4a.keywords.Common.LogImpl("454525968",BA.NumberToString(parent.mostCurrent._mylocation.getLatitude()),0);
 //BA.debugLineNum = 195;BA.debugLine="Log(myLocation.Longitude)";
anywheresoftware.b4a.keywords.Common.LogImpl("454525969",BA.NumberToString(parent.mostCurrent._mylocation.getLongitude()),0);
 //BA.debugLineNum = 196;BA.debugLine="GetTaxaiNat(taxa)";
_gettaxainat(_taxa);
 //BA.debugLineNum = 197;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public static String  _gettaxainat(String _taxa) throws Exception{
appear.pnud.preservamos.downloadservice._downloaddata _dd = null;
 //BA.debugLineNum = 200;BA.debugLine="Sub GetTaxaiNat(taxa As String)";
 //BA.debugLineNum = 203;BA.debugLine="If myLocation.IsInitialized = False Then";
if (mostCurrent._mylocation.IsInitialized()==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 204;BA.debugLine="ToastMessageShow(\"Ubicación no detectada, intent";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Ubicación no detectada, intente de nuevo!"),anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 209;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 210;BA.debugLine="Activity.LoadLayout(\"layCheckList\")";
mostCurrent._activity.LoadLayout("layCheckList",mostCurrent.activityBA);
 //BA.debugLineNum = 211;BA.debugLine="currentScreen = \"CheckListList\"";
mostCurrent._currentscreen = "CheckListList";
 //BA.debugLineNum = 212;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 213;BA.debugLine="If taxa = \"Mammalia\" Then";
if ((_taxa).equals("Mammalia")) { 
 //BA.debugLineNum = 214;BA.debugLine="lblTituloCheckList.Text = \"Mamíferos\"";
mostCurrent._lbltitulochecklist.setText(BA.ObjectToCharSequence("Mamíferos"));
 }else if((_taxa).equals("Actinopterigii,Reptilia,Amphibia")) { 
 //BA.debugLineNum = 216;BA.debugLine="lblTituloCheckList.Text = \"Peces, reptiles y an";
mostCurrent._lbltitulochecklist.setText(BA.ObjectToCharSequence("Peces, reptiles y anfíbios"));
 }else if((_taxa).equals("Aves")) { 
 //BA.debugLineNum = 218;BA.debugLine="lblTituloCheckList.Text = taxa";
mostCurrent._lbltitulochecklist.setText(BA.ObjectToCharSequence(_taxa));
 }else if((_taxa).equals("Insecta,Arachnida,Mollusca")) { 
 //BA.debugLineNum = 220;BA.debugLine="lblTituloCheckList.Text = \"Invertebrados\"";
mostCurrent._lbltitulochecklist.setText(BA.ObjectToCharSequence("Invertebrados"));
 }else if((_taxa).equals("Protozoa,Fungi")) { 
 //BA.debugLineNum = 222;BA.debugLine="lblTituloCheckList.Text = \"Microorganismos y ho";
mostCurrent._lbltitulochecklist.setText(BA.ObjectToCharSequence("Microorganismos y hongos"));
 }else if((_taxa).equals("Plantae")) { 
 //BA.debugLineNum = 224;BA.debugLine="lblTituloCheckList.Text = \"Plantas\"";
mostCurrent._lbltitulochecklist.setText(BA.ObjectToCharSequence("Plantas"));
 };
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 227;BA.debugLine="If taxa = \"Mammalia\" Then";
if ((_taxa).equals("Mammalia")) { 
 //BA.debugLineNum = 228;BA.debugLine="lblTituloCheckList.Text = \"Mammals\"";
mostCurrent._lbltitulochecklist.setText(BA.ObjectToCharSequence("Mammals"));
 }else if((_taxa).equals("Actinopterigii,Reptilia,Amphibia")) { 
 //BA.debugLineNum = 230;BA.debugLine="lblTituloCheckList.Text = \"Fish, reptiles & amp";
mostCurrent._lbltitulochecklist.setText(BA.ObjectToCharSequence("Fish, reptiles & amphibians"));
 }else if((_taxa).equals("Aves")) { 
 //BA.debugLineNum = 232;BA.debugLine="lblTituloCheckList.Text = \"Birds\"";
mostCurrent._lbltitulochecklist.setText(BA.ObjectToCharSequence("Birds"));
 }else if((_taxa).equals("Insecta,Arachnida,Mollusca")) { 
 //BA.debugLineNum = 234;BA.debugLine="lblTituloCheckList.Text = \"Invertebrates\"";
mostCurrent._lbltitulochecklist.setText(BA.ObjectToCharSequence("Invertebrates"));
 }else if((_taxa).equals("Protozoa,Fungi")) { 
 //BA.debugLineNum = 236;BA.debugLine="lblTituloCheckList.Text = \"Microorganisms & fun";
mostCurrent._lbltitulochecklist.setText(BA.ObjectToCharSequence("Microorganisms & fungi"));
 }else if((_taxa).equals("Plantae")) { 
 //BA.debugLineNum = 238;BA.debugLine="lblTituloCheckList.Text = \"Plants\"";
mostCurrent._lbltitulochecklist.setText(BA.ObjectToCharSequence("Plants"));
 };
 };
 //BA.debugLineNum = 243;BA.debugLine="Log(\"radioValue:\" & radioValue)";
anywheresoftware.b4a.keywords.Common.LogImpl("454591531","radioValue:"+BA.NumberToString(_radiovalue),0);
 //BA.debugLineNum = 246;BA.debugLine="nelat = myLocation.Latitude + radioValue";
mostCurrent._nelat = BA.NumberToString(mostCurrent._mylocation.getLatitude()+_radiovalue);
 //BA.debugLineNum = 247;BA.debugLine="nelng = myLocation.Longitude + radioValue";
mostCurrent._nelng = BA.NumberToString(mostCurrent._mylocation.getLongitude()+_radiovalue);
 //BA.debugLineNum = 248;BA.debugLine="swlat = myLocation.Latitude - radioValue";
mostCurrent._swlat = BA.NumberToString(mostCurrent._mylocation.getLatitude()-_radiovalue);
 //BA.debugLineNum = 249;BA.debugLine="swlng = myLocation.Longitude - radioValue";
mostCurrent._swlng = BA.NumberToString(mostCurrent._mylocation.getLongitude()-_radiovalue);
 //BA.debugLineNum = 253;BA.debugLine="ImageViews.Initialize";
mostCurrent._imageviews.Initialize();
 //BA.debugLineNum = 254;BA.debugLine="prefered_common_nameList.Initialize";
_prefered_common_namelist.Initialize();
 //BA.debugLineNum = 255;BA.debugLine="iconicnameList.Initialize";
_iconicnamelist.Initialize();
 //BA.debugLineNum = 256;BA.debugLine="threatList.Initialize";
_threatlist.Initialize();
 //BA.debugLineNum = 257;BA.debugLine="scientific_nameList.Initialize";
_scientific_namelist.Initialize();
 //BA.debugLineNum = 258;BA.debugLine="wikilinkList.Initialize";
_wikilinklist.Initialize();
 //BA.debugLineNum = 259;BA.debugLine="attributionList.Initialize";
_attributionlist.Initialize();
 //BA.debugLineNum = 260;BA.debugLine="Imagelinks.Initialize";
_imagelinks.Initialize();
 //BA.debugLineNum = 266;BA.debugLine="Dim dd As DownloadData";
_dd = new appear.pnud.preservamos.downloadservice._downloaddata();
 //BA.debugLineNum = 267;BA.debugLine="dd.url = \"https://api.inaturalist.org/v1/observat";
_dd.url /*String*/  = "https://api.inaturalist.org/v1/observations/species_counts?preferred_place_id=7190&locale=es&iconic_taxa="+_taxa+"&quality_grade=research&reviewed=true&nelat="+mostCurrent._nelat+"&nelng="+mostCurrent._nelng+"&swlat="+mostCurrent._swlat+"&swlng="+mostCurrent._swlng;
 //BA.debugLineNum = 268;BA.debugLine="dd.EventName = \"GetiNatTaxa\"";
_dd.EventName /*String*/  = "GetiNatTaxa";
 //BA.debugLineNum = 269;BA.debugLine="dd.Target = Me";
_dd.Target /*Object*/  = inatcheck.getObject();
 //BA.debugLineNum = 270;BA.debugLine="CallSubDelayed2(DownloadService, \"StartDownload\",";
anywheresoftware.b4a.keywords.Common.CallSubDelayed2(processBA,(Object)(mostCurrent._downloadservice.getObject()),"StartDownload",(Object)(_dd));
 //BA.debugLineNum = 273;BA.debugLine="End Sub";
return "";
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 20;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 23;BA.debugLine="Private lblTituloCheckList As Label";
mostCurrent._lbltitulochecklist = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 24;BA.debugLine="Private currentScreen As String";
mostCurrent._currentscreen = "";
 //BA.debugLineNum = 25;BA.debugLine="Private butPlantas As Button";
mostCurrent._butplantas = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 26;BA.debugLine="Private butFungi As Button";
mostCurrent._butfungi = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 27;BA.debugLine="Private butAves As Button";
mostCurrent._butaves = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 28;BA.debugLine="Private butBugs As Button";
mostCurrent._butbugs = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 29;BA.debugLine="Private butPeces As Button";
mostCurrent._butpeces = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 30;BA.debugLine="Private butMammals As Button";
mostCurrent._butmammals = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 33;BA.debugLine="Dim nelat As String";
mostCurrent._nelat = "";
 //BA.debugLineNum = 34;BA.debugLine="Dim nelng As String";
mostCurrent._nelng = "";
 //BA.debugLineNum = 35;BA.debugLine="Dim swlat As String";
mostCurrent._swlat = "";
 //BA.debugLineNum = 36;BA.debugLine="Dim swlng As String";
mostCurrent._swlng = "";
 //BA.debugLineNum = 39;BA.debugLine="Dim clvCheckList As CustomListView";
mostCurrent._clvchecklist = new b4a.example3.customlistview();
 //BA.debugLineNum = 41;BA.debugLine="Dim phone1 As Phone";
mostCurrent._phone1 = new anywheresoftware.b4a.phone.Phone();
 //BA.debugLineNum = 42;BA.debugLine="Dim lblNombreComun As Label";
mostCurrent._lblnombrecomun = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 43;BA.debugLine="Dim lblNombreCientifico As Label";
mostCurrent._lblnombrecientifico = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 45;BA.debugLine="Dim ImageView1 As ImageView";
mostCurrent._imageview1 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 46;BA.debugLine="Dim ImageViews As List";
mostCurrent._imageviews = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 49;BA.debugLine="Dim lblLat As Label";
mostCurrent._lbllat = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 50;BA.debugLine="Dim lblLon As Label";
mostCurrent._lbllon = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 53;BA.debugLine="Private gmap As GoogleMap";
mostCurrent._gmap = new anywheresoftware.b4a.objects.MapFragmentWrapper.GoogleMapWrapper();
 //BA.debugLineNum = 54;BA.debugLine="Private MapFragment1 As MapFragment";
mostCurrent._mapfragment1 = new anywheresoftware.b4a.objects.MapFragmentWrapper();
 //BA.debugLineNum = 55;BA.debugLine="Dim myLocation As LatLng";
mostCurrent._mylocation = new anywheresoftware.b4a.objects.MapFragmentWrapper.LatLngWrapper();
 //BA.debugLineNum = 57;BA.debugLine="Dim rp As RuntimePermissions";
_rp = new anywheresoftware.b4a.objects.RuntimePermissions();
 //BA.debugLineNum = 59;BA.debugLine="Dim xui As XUI";
mostCurrent._xui = new anywheresoftware.b4a.objects.B4XViewWrapper.XUI();
 //BA.debugLineNum = 60;BA.debugLine="Dim m As Map";
mostCurrent._m = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 61;BA.debugLine="Private SeekBar1 As SeekBar";
mostCurrent._seekbar1 = new anywheresoftware.b4a.objects.SeekBarWrapper();
 //BA.debugLineNum = 62;BA.debugLine="Private lblRadioTitle As Label";
mostCurrent._lblradiotitle = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 63;BA.debugLine="Private lblRadio As Label";
mostCurrent._lblradio = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 64;BA.debugLine="Private radioValue As Double";
_radiovalue = 0;
 //BA.debugLineNum = 66;BA.debugLine="Private lblMasInfoWiki_Detail As Label";
mostCurrent._lblmasinfowiki_detail = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 67;BA.debugLine="Private ImageView1_Detail As ImageView";
mostCurrent._imageview1_detail = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 68;BA.debugLine="Private lblNombreCientifico_Detail As Label";
mostCurrent._lblnombrecientifico_detail = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 69;BA.debugLine="Private lblNombreComun_Detail As Label";
mostCurrent._lblnombrecomun_detail = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 71;BA.debugLine="Dim lblWikiURL As String";
mostCurrent._lblwikiurl = "";
 //BA.debugLineNum = 72;BA.debugLine="Dim lblNombreComunDetail As String";
mostCurrent._lblnombrecomundetail = "";
 //BA.debugLineNum = 74;BA.debugLine="Private lblAtribucion_Detail As Label";
mostCurrent._lblatribucion_detail = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 75;BA.debugLine="Private chkImagenes As CheckBox";
mostCurrent._chkimagenes = new anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper();
 //BA.debugLineNum = 76;BA.debugLine="End Sub";
return "";
}
public static String  _imageview1_detail_click() throws Exception{
 //BA.debugLineNum = 406;BA.debugLine="Private Sub ImageView1_Detail_Click";
 //BA.debugLineNum = 408;BA.debugLine="End Sub";
return "";
}
public static void  _lblmasinfowiki_detail_click() throws Exception{
ResumableSub_lblMasInfoWiki_Detail_Click rsub = new ResumableSub_lblMasInfoWiki_Detail_Click(null);
rsub.resume(processBA, null);
}
public static class ResumableSub_lblMasInfoWiki_Detail_Click extends BA.ResumableSub {
public ResumableSub_lblMasInfoWiki_Detail_Click(appear.pnud.preservamos.inatcheck parent) {
this.parent = parent;
}
appear.pnud.preservamos.inatcheck parent;
int _result = 0;
anywheresoftware.b4a.phone.Phone.PhoneIntents _pi = null;

@Override
public void resume(BA ba, Object[] result) throws Exception{

    while (true) {
        switch (state) {
            case -1:
return;

case 0:
//C
this.state = 1;
 //BA.debugLineNum = 411;BA.debugLine="Msgbox2Async(\"¿Desea más información sobre este o";
anywheresoftware.b4a.keywords.Common.Msgbox2Async(BA.ObjectToCharSequence("¿Desea más información sobre este organismo?"),BA.ObjectToCharSequence(parent.mostCurrent._lblnombrecomundetail),"Si, abre Wikipedia","No gracias","",(anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper(), (android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null)),processBA,anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 412;BA.debugLine="Wait For Msgbox_Result (Result As Int)";
anywheresoftware.b4a.keywords.Common.WaitFor("msgbox_result", processBA, this, null);
this.state = 5;
return;
case 5:
//C
this.state = 1;
_result = (Integer) result[0];
;
 //BA.debugLineNum = 413;BA.debugLine="If Result = DialogResponse.POSITIVE Then";
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
 //BA.debugLineNum = 414;BA.debugLine="Dim pi As PhoneIntents";
_pi = new anywheresoftware.b4a.phone.Phone.PhoneIntents();
 //BA.debugLineNum = 415;BA.debugLine="StartActivity(pi.OpenBrowser(lblWikiURL))";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(_pi.OpenBrowser(parent.mostCurrent._lblwikiurl)));
 if (true) break;

case 4:
//C
this.state = -1;
;
 //BA.debugLineNum = 418;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public static void  _msgbox_result(int _result) throws Exception{
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 4;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 6;BA.debugLine="Private Imagelinks As List";
_imagelinks = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 7;BA.debugLine="Private prefered_common_nameList As List";
_prefered_common_namelist = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 8;BA.debugLine="Private iconicnameList As List";
_iconicnamelist = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 9;BA.debugLine="Private scientific_nameList As List";
_scientific_namelist = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 10;BA.debugLine="Private wikilinkList As List";
_wikilinklist = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 11;BA.debugLine="Private threatList As List";
_threatlist = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 12;BA.debugLine="Private attributionList As List";
_attributionlist = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 18;BA.debugLine="Dim rp As RuntimePermissions";
_rp = new anywheresoftware.b4a.objects.RuntimePermissions();
 //BA.debugLineNum = 19;BA.debugLine="End Sub";
return "";
}
public static String  _seekbar1_valuechanged(int _value,boolean _userchanged) throws Exception{
 //BA.debugLineNum = 437;BA.debugLine="Private Sub SeekBar1_ValueChanged (Value As Int, U";
 //BA.debugLineNum = 438;BA.debugLine="lblRadio.Text = SeekBar1.Value & \"km (aprox)\"";
mostCurrent._lblradio.setText(BA.ObjectToCharSequence(BA.NumberToString(mostCurrent._seekbar1.getValue())+"km (aprox)"));
 //BA.debugLineNum = 439;BA.debugLine="radioValue = SeekBar1.Value / 100";
_radiovalue = mostCurrent._seekbar1.getValue()/(double)100;
 //BA.debugLineNum = 440;BA.debugLine="If Value = 0 Then";
if (_value==0) { 
 //BA.debugLineNum = 441;BA.debugLine="SeekBar1.Value = 1";
mostCurrent._seekbar1.setValue((int) (1));
 };
 //BA.debugLineNum = 443;BA.debugLine="End Sub";
return "";
}
}
