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

public class reporte_fotos extends Activity implements B4AActivity{
	public static reporte_fotos mostCurrent;
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
			processBA = new BA(this.getApplicationContext(), null, null, "ilpla.appear", "ilpla.appear.reporte_fotos");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
            
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (reporte_fotos).");
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
		activityBA = new BA(this, layout, processBA, "ilpla.appear", "ilpla.appear.reporte_fotos");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "ilpla.appear.reporte_fotos", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density, mostCurrent);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (reporte_fotos) Create, isFirst = " + isFirst + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (reporte_fotos) Resume **");
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
		return reporte_fotos.class;
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
            BA.LogInfo("** Activity (reporte_fotos) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
        else
            BA.LogInfo("** Activity (reporte_fotos) Pause event (activity is not paused). **");
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
            reporte_fotos mc = mostCurrent;
			if (mc == null || mc != activity.get())
				return;
			processBA.setActivityPaused(false);
            BA.LogInfo("** Activity (reporte_fotos) Resume **");
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
public static boolean _frontcamera = false;
public static anywheresoftware.b4a.phone.Phone.ContentChooser _cc = null;
public anywheresoftware.b4a.objects.PanelWrapper _panel1 = null;
public ilpla.appear.cameraexclass _camex = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btntakepicture = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imgflash = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblinstruccion = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnadjuntarfoto = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btncontinuar = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imgsitio4 = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imgsitio3 = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imgsitio2 = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imgsitio1 = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imgsitio5 = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imgmenu = null;
public anywheresoftware.b4a.objects.SeekBarWrapper _seekfocus = null;
public static String _fotonombredestino = "";
public ilpla.appear.main _main = null;
public ilpla.appear.form_main _form_main = null;
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
 //BA.debugLineNum = 38;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
 //BA.debugLineNum = 39;BA.debugLine="Activity.LoadLayout(\"camera\")";
mostCurrent._activity.LoadLayout("camera",mostCurrent.activityBA);
 //BA.debugLineNum = 42;BA.debugLine="If File.ExternalWritable = True Then";
if (anywheresoftware.b4a.keywords.Common.File.getExternalWritable()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 43;BA.debugLine="If File.IsDirectory(File.DirRootExternal,\"Preser";
if (anywheresoftware.b4a.keywords.Common.File.IsDirectory(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal(),"PreserVamos/")==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 44;BA.debugLine="File.MakeDir(File.DirRootExternal, \"PreserVamos";
anywheresoftware.b4a.keywords.Common.File.MakeDir(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal(),"PreserVamos");
 };
 }else {
 //BA.debugLineNum = 47;BA.debugLine="If File.IsDirectory(File.DirInternal,\"PreserVamo";
if (anywheresoftware.b4a.keywords.Common.File.IsDirectory(anywheresoftware.b4a.keywords.Common.File.getDirInternal(),"PreserVamos/")==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 48;BA.debugLine="File.MakeDir(File.DirInternal, \"PreserVamos\")";
anywheresoftware.b4a.keywords.Common.File.MakeDir(anywheresoftware.b4a.keywords.Common.File.getDirInternal(),"PreserVamos");
 };
 };
 //BA.debugLineNum = 54;BA.debugLine="Activity.AddMenuItem(\"Adjuntar foto\", \"mnuAdjunta";
mostCurrent._activity.AddMenuItem(BA.ObjectToCharSequence("Adjuntar foto"),"mnuAdjuntar");
 //BA.debugLineNum = 55;BA.debugLine="End Sub";
return "";
}
public static boolean  _activity_keypress(int _keycode) throws Exception{
 //BA.debugLineNum = 68;BA.debugLine="Sub Activity_KeyPress (KeyCode As Int) As Boolean";
 //BA.debugLineNum = 69;BA.debugLine="If KeyCode = KeyCodes.KEYCODE_BACK Then";
if (_keycode==anywheresoftware.b4a.keywords.Common.KeyCodes.KEYCODE_BACK) { 
 //BA.debugLineNum = 70;BA.debugLine="closeAppMsgBox";
_closeappmsgbox();
 //BA.debugLineNum = 71;BA.debugLine="Return True";
if (true) return anywheresoftware.b4a.keywords.Common.True;
 }else {
 //BA.debugLineNum = 73;BA.debugLine="Return False";
if (true) return anywheresoftware.b4a.keywords.Common.False;
 };
 //BA.debugLineNum = 75;BA.debugLine="End Sub";
return false;
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 63;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 64;BA.debugLine="camEx.Release";
mostCurrent._camex._release /*String*/ ();
 //BA.debugLineNum = 66;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 57;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 59;BA.debugLine="InitializeCamera";
_initializecamera();
 //BA.debugLineNum = 60;BA.debugLine="DesignaFoto";
_designafoto();
 //BA.debugLineNum = 61;BA.debugLine="End Sub";
return "";
}
public static String  _btnadjuntarfoto_click() throws Exception{
 //BA.debugLineNum = 384;BA.debugLine="Sub btnAdjuntarFoto_Click";
 //BA.debugLineNum = 386;BA.debugLine="camEx.StopPreview";
mostCurrent._camex._stoppreview /*String*/ ();
 //BA.debugLineNum = 387;BA.debugLine="camEx.Release";
mostCurrent._camex._release /*String*/ ();
 //BA.debugLineNum = 388;BA.debugLine="DesignaFoto";
_designafoto();
 //BA.debugLineNum = 389;BA.debugLine="CC.Initialize(\"CC\")";
_cc.Initialize("CC");
 //BA.debugLineNum = 390;BA.debugLine="CC.Show(\"image/\", \"Elija la foto\")";
_cc.Show(processBA,"image/","Elija la foto");
 //BA.debugLineNum = 391;BA.debugLine="End Sub";
return "";
}
public static String  _btncontinuar_click() throws Exception{
 //BA.debugLineNum = 433;BA.debugLine="Sub btnContinuar_Click";
 //BA.debugLineNum = 435;BA.debugLine="camEx.StopPreview";
mostCurrent._camex._stoppreview /*String*/ ();
 //BA.debugLineNum = 436;BA.debugLine="camEx.Release";
mostCurrent._camex._release /*String*/ ();
 //BA.debugLineNum = 441;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 442;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 443;BA.debugLine="StartActivity(Reporte_Envio)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._reporte_envio.getObject()));
 //BA.debugLineNum = 454;BA.debugLine="End Sub";
return "";
}
public static String  _btntakepicture_click() throws Exception{
 //BA.debugLineNum = 305;BA.debugLine="Sub btnTakePicture_Click";
 //BA.debugLineNum = 306;BA.debugLine="ToastMessageShow(\"Capturando foto, espere por fav";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Capturando foto, espere por favor..."),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 307;BA.debugLine="If camEx.IsInitialized Then";
if (mostCurrent._camex.IsInitialized /*boolean*/ ()) { 
 //BA.debugLineNum = 308;BA.debugLine="camEx.TakePicture";
mostCurrent._camex._takepicture /*String*/ ();
 };
 //BA.debugLineNum = 311;BA.debugLine="End Sub";
return "";
}
public static String  _camera1_picturetaken(byte[] _data) throws Exception{
anywheresoftware.b4a.objects.collections.Map _map1 = null;
 //BA.debugLineNum = 313;BA.debugLine="Sub Camera1_PictureTaken (Data() As Byte)";
 //BA.debugLineNum = 315;BA.debugLine="camEx.SavePictureToFile(Data, File.DirRootExterna";
mostCurrent._camex._savepicturetofile /*String*/ (_data,anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/PreserVamos/",mostCurrent._fotonombredestino+".jpg");
 //BA.debugLineNum = 318;BA.debugLine="Dim Map1 As Map";
_map1 = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 319;BA.debugLine="Map1.Initialize";
_map1.Initialize();
 //BA.debugLineNum = 320;BA.debugLine="Map1.Put(\"Id\", Form_Reporte.currentproject)";
_map1.Put((Object)("Id"),(Object)(mostCurrent._form_reporte._currentproject /*String*/ ));
 //BA.debugLineNum = 321;BA.debugLine="If fotoNombreDestino.EndsWith(\"_1\") Then";
if (mostCurrent._fotonombredestino.endsWith("_1")) { 
 //BA.debugLineNum = 322;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"evals\", \"fo";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals","foto1",(Object)(mostCurrent._fotonombredestino),_map1);
 }else if(mostCurrent._fotonombredestino.endsWith("_2")) { 
 //BA.debugLineNum = 324;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"evals\", \"fo";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals","foto2",(Object)(mostCurrent._fotonombredestino),_map1);
 }else if(mostCurrent._fotonombredestino.endsWith("_3")) { 
 //BA.debugLineNum = 326;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"evals\", \"fo";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals","foto3",(Object)(mostCurrent._fotonombredestino),_map1);
 }else if(mostCurrent._fotonombredestino.endsWith("_4")) { 
 //BA.debugLineNum = 328;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"evals\", \"fo";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals","foto4",(Object)(mostCurrent._fotonombredestino),_map1);
 }else if(mostCurrent._fotonombredestino.endsWith("_5")) { 
 //BA.debugLineNum = 330;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"evals\", \"fo";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals","foto5",(Object)(mostCurrent._fotonombredestino),_map1);
 };
 //BA.debugLineNum = 335;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 //BA.debugLineNum = 336;BA.debugLine="camEx.StartPreview 'restart preview";
mostCurrent._camex._startpreview /*String*/ ();
 //BA.debugLineNum = 339;BA.debugLine="DesignaFoto";
_designafoto();
 //BA.debugLineNum = 340;BA.debugLine="End Sub";
return "";
}
public static String  _camera1_ready(boolean _success) throws Exception{
 //BA.debugLineNum = 267;BA.debugLine="Sub Camera1_Ready (Success As Boolean)";
 //BA.debugLineNum = 268;BA.debugLine="If Success Then";
if (_success) { 
 //BA.debugLineNum = 269;BA.debugLine="Log(camEx.GetSupportedPicturesSizes)";
anywheresoftware.b4a.keywords.Common.LogImpl("227197442",BA.ObjectToString(mostCurrent._camex._getsupportedpicturessizes /*ilpla.appear.cameraexclass._camerasize[]*/ ()),0);
 //BA.debugLineNum = 270;BA.debugLine="Log(camEx.GetPictureSize)";
anywheresoftware.b4a.keywords.Common.LogImpl("227197443",BA.ObjectToString(mostCurrent._camex._getpicturesize /*ilpla.appear.cameraexclass._camerasize*/ ()),0);
 //BA.debugLineNum = 271;BA.debugLine="SetMaxSize";
_setmaxsize();
 //BA.debugLineNum = 272;BA.debugLine="camEx.SetContinuousAutoFocus";
mostCurrent._camex._setcontinuousautofocus /*String*/ ();
 //BA.debugLineNum = 273;BA.debugLine="camEx.CommitParameters";
mostCurrent._camex._commitparameters /*String*/ ();
 //BA.debugLineNum = 274;BA.debugLine="camEx.StartPreview";
mostCurrent._camex._startpreview /*String*/ ();
 //BA.debugLineNum = 275;BA.debugLine="Log(camEx.GetPreviewSize)";
anywheresoftware.b4a.keywords.Common.LogImpl("227197448",BA.ObjectToString(mostCurrent._camex._getpreviewsize /*ilpla.appear.cameraexclass._camerasize*/ ()),0);
 }else {
 //BA.debugLineNum = 277;BA.debugLine="ToastMessageShow(\"Cannot open camera.\", True)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Cannot open camera."),anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 279;BA.debugLine="End Sub";
return "";
}
public static String  _cc_result(boolean _success,String _dir,String _filename) throws Exception{
anywheresoftware.b4a.objects.collections.Map _map1 = null;
 //BA.debugLineNum = 393;BA.debugLine="Sub CC_Result (Success As Boolean, Dir As String,";
 //BA.debugLineNum = 394;BA.debugLine="If Success = True Then";
if (_success==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 397;BA.debugLine="Dim Map1 As Map";
_map1 = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 398;BA.debugLine="Map1.Initialize";
_map1.Initialize();
 //BA.debugLineNum = 399;BA.debugLine="Map1.Put(\"Id\", Form_Reporte.currentproject)";
_map1.Put((Object)("Id"),(Object)(mostCurrent._form_reporte._currentproject /*String*/ ));
 //BA.debugLineNum = 400;BA.debugLine="DateTime.DateFormat = \"dd-MM-yyyy\"";
anywheresoftware.b4a.keywords.Common.DateTime.setDateFormat("dd-MM-yyyy");
 //BA.debugLineNum = 401;BA.debugLine="If fotoNombreDestino.EndsWith(\"_1\") Then";
if (mostCurrent._fotonombredestino.endsWith("_1")) { 
 //BA.debugLineNum = 402;BA.debugLine="File.Copy(Dir, FileName, File.DirRootExternal &";
anywheresoftware.b4a.keywords.Common.File.Copy(_dir,_filename,anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/PreserVamos/",mostCurrent._fotonombredestino+".jpg");
 //BA.debugLineNum = 403;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"evals\", \"f";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals","foto1",(Object)(mostCurrent._fotonombredestino),_map1);
 }else if(mostCurrent._fotonombredestino.endsWith("_2")) { 
 //BA.debugLineNum = 405;BA.debugLine="File.Copy(Dir, FileName, File.DirRootExternal &";
anywheresoftware.b4a.keywords.Common.File.Copy(_dir,_filename,anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/PreserVamos/",mostCurrent._fotonombredestino+".jpg");
 //BA.debugLineNum = 406;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"evals\", \"f";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals","foto2",(Object)(mostCurrent._fotonombredestino),_map1);
 }else if(mostCurrent._fotonombredestino.endsWith("_3")) { 
 //BA.debugLineNum = 408;BA.debugLine="File.Copy(Dir, FileName, File.DirRootExternal &";
anywheresoftware.b4a.keywords.Common.File.Copy(_dir,_filename,anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/PreserVamos/",mostCurrent._fotonombredestino+".jpg");
 //BA.debugLineNum = 409;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"evals\", \"f";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals","foto3",(Object)(mostCurrent._fotonombredestino),_map1);
 }else if(mostCurrent._fotonombredestino.endsWith("_4")) { 
 //BA.debugLineNum = 411;BA.debugLine="File.Copy(Dir, FileName, File.DirRootExternal &";
anywheresoftware.b4a.keywords.Common.File.Copy(_dir,_filename,anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/PreserVamos/",mostCurrent._fotonombredestino+".jpg");
 //BA.debugLineNum = 412;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"evals\", \"f";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals","foto4",(Object)(mostCurrent._fotonombredestino),_map1);
 }else if(mostCurrent._fotonombredestino.endsWith("_5")) { 
 //BA.debugLineNum = 414;BA.debugLine="File.Copy(Dir, FileName, File.DirRootExternal &";
anywheresoftware.b4a.keywords.Common.File.Copy(_dir,_filename,anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/PreserVamos/",mostCurrent._fotonombredestino+".jpg");
 //BA.debugLineNum = 415;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"evals\", \"f";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals","foto5",(Object)(mostCurrent._fotonombredestino),_map1);
 };
 //BA.debugLineNum = 419;BA.debugLine="DesignaFoto";
_designafoto();
 };
 //BA.debugLineNum = 421;BA.debugLine="End Sub";
return "";
}
public static void  _closeappmsgbox() throws Exception{
ResumableSub_closeAppMsgBox rsub = new ResumableSub_closeAppMsgBox(null);
rsub.resume(processBA, null);
}
public static class ResumableSub_closeAppMsgBox extends BA.ResumableSub {
public ResumableSub_closeAppMsgBox(ilpla.appear.reporte_fotos parent) {
this.parent = parent;
}
ilpla.appear.reporte_fotos parent;
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
 //BA.debugLineNum = 78;BA.debugLine="If Main.lang = \"es\" Then";
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
 //BA.debugLineNum = 79;BA.debugLine="Msgbox2Async(\"Volver al inicio?\", \"SALIR\", \"Si\",";
anywheresoftware.b4a.keywords.Common.Msgbox2Async(BA.ObjectToCharSequence("Volver al inicio?"),BA.ObjectToCharSequence("SALIR"),"Si","","No",(anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper(), (android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null)),processBA,anywheresoftware.b4a.keywords.Common.False);
 if (true) break;

case 5:
//C
this.state = 6;
 //BA.debugLineNum = 81;BA.debugLine="Msgbox2Async(\"Back to the beginning?\", \"Exit\", \"";
anywheresoftware.b4a.keywords.Common.Msgbox2Async(BA.ObjectToCharSequence("Back to the beginning?"),BA.ObjectToCharSequence("Exit"),"Yes","","No",(anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper(), (android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null)),processBA,anywheresoftware.b4a.keywords.Common.False);
 if (true) break;

case 6:
//C
this.state = 7;
;
 //BA.debugLineNum = 83;BA.debugLine="Wait For Msgbox_Result (Result As Int)";
anywheresoftware.b4a.keywords.Common.WaitFor("msgbox_result", processBA, this, null);
this.state = 11;
return;
case 11:
//C
this.state = 7;
_result = (Integer) result[0];
;
 //BA.debugLineNum = 84;BA.debugLine="If Result = DialogResponse.POSITIVE Then";
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
 //BA.debugLineNum = 85;BA.debugLine="camEx.StopPreview";
parent.mostCurrent._camex._stoppreview /*String*/ ();
 //BA.debugLineNum = 86;BA.debugLine="camEx.Release";
parent.mostCurrent._camex._release /*String*/ ();
 //BA.debugLineNum = 87;BA.debugLine="Activity.finish";
parent.mostCurrent._activity.Finish();
 //BA.debugLineNum = 88;BA.debugLine="Activity.RemoveAllViews";
parent.mostCurrent._activity.RemoveAllViews();
 if (true) break;

case 10:
//C
this.state = -1;
;
 //BA.debugLineNum = 90;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public static void  _msgbox_result(int _result) throws Exception{
}
public static void  _designafoto() throws Exception{
ResumableSub_DesignaFoto rsub = new ResumableSub_DesignaFoto(null);
rsub.resume(processBA, null);
}
public static class ResumableSub_DesignaFoto extends BA.ResumableSub {
public ResumableSub_DesignaFoto(ilpla.appear.reporte_fotos parent) {
this.parent = parent;
}
ilpla.appear.reporte_fotos parent;
anywheresoftware.b4a.objects.collections.Map _fotomap = null;
String _currentpr = "";
String _foto1str = "";
String _foto2str = "";
String _foto3str = "";
String _foto4str = "";
String _foto5str = "";

@Override
public void resume(BA ba, Object[] result) throws Exception{

    while (true) {
        switch (state) {
            case -1:
return;

case 0:
//C
this.state = 1;
 //BA.debugLineNum = 122;BA.debugLine="DateTime.DateFormat = \"dd-MM-yyyy\"";
anywheresoftware.b4a.keywords.Common.DateTime.setDateFormat("dd-MM-yyyy");
 //BA.debugLineNum = 123;BA.debugLine="ProgressDialogShow(\"Preparando cámara...\")";
anywheresoftware.b4a.keywords.Common.ProgressDialogShow(mostCurrent.activityBA,BA.ObjectToCharSequence("Preparando cámara..."));
 //BA.debugLineNum = 126;BA.debugLine="Log(\"Designando foto\")";
anywheresoftware.b4a.keywords.Common.LogImpl("227066374","Designando foto",0);
 //BA.debugLineNum = 127;BA.debugLine="Dim fotoMap As Map";
_fotomap = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 128;BA.debugLine="fotoMap.Initialize";
_fotomap.Initialize();
 //BA.debugLineNum = 129;BA.debugLine="Dim currentpr As String";
_currentpr = "";
 //BA.debugLineNum = 130;BA.debugLine="currentpr = Form_Reporte.currentproject";
_currentpr = parent.mostCurrent._form_reporte._currentproject /*String*/ ;
 //BA.debugLineNum = 131;BA.debugLine="Log(\"currentproject=\" & currentpr)";
anywheresoftware.b4a.keywords.Common.LogImpl("227066379","currentproject="+_currentpr,0);
 //BA.debugLineNum = 132;BA.debugLine="fotoMap = DBUtils.ExecuteMap(Starter.sqlDB, \"SELE";
_fotomap = parent.mostCurrent._dbutils._executemap /*anywheresoftware.b4a.objects.collections.Map*/ (mostCurrent.activityBA,parent.mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"SELECT * FROM evals WHERE Id = ?",new String[]{_currentpr});
 //BA.debugLineNum = 134;BA.debugLine="Sleep(200)";
anywheresoftware.b4a.keywords.Common.Sleep(mostCurrent.activityBA,this,(int) (200));
this.state = 66;
return;
case 66:
//C
this.state = 1;
;
 //BA.debugLineNum = 135;BA.debugLine="If fotoMap = Null Or fotoMap.IsInitialized = Fals";
if (true) break;

case 1:
//if
this.state = 65;
if (_fotomap== null || _fotomap.IsInitialized()==anywheresoftware.b4a.keywords.Common.False) { 
this.state = 3;
}else {
this.state = 5;
}if (true) break;

case 3:
//C
this.state = 65;
 //BA.debugLineNum = 136;BA.debugLine="Log(\"no hay fotomap\")";
anywheresoftware.b4a.keywords.Common.LogImpl("227066384","no hay fotomap",0);
 //BA.debugLineNum = 137;BA.debugLine="fotoNombreDestino = \"\"";
parent.mostCurrent._fotonombredestino = "";
 //BA.debugLineNum = 138;BA.debugLine="Return";
if (true) return ;
 if (true) break;

case 5:
//C
this.state = 6;
 //BA.debugLineNum = 140;BA.debugLine="Log(\"hay fotomap\")";
anywheresoftware.b4a.keywords.Common.LogImpl("227066388","hay fotomap",0);
 //BA.debugLineNum = 144;BA.debugLine="Dim foto1str As String = fotoMap.Get(\"foto1\")";
_foto1str = BA.ObjectToString(_fotomap.Get((Object)("foto1")));
 //BA.debugLineNum = 145;BA.debugLine="Dim foto2str As String = fotoMap.Get(\"foto2\")";
_foto2str = BA.ObjectToString(_fotomap.Get((Object)("foto2")));
 //BA.debugLineNum = 146;BA.debugLine="Dim foto3str As String = fotoMap.Get(\"foto3\")";
_foto3str = BA.ObjectToString(_fotomap.Get((Object)("foto3")));
 //BA.debugLineNum = 147;BA.debugLine="Dim foto4str As String = fotoMap.Get(\"foto4\")";
_foto4str = BA.ObjectToString(_fotomap.Get((Object)("foto4")));
 //BA.debugLineNum = 148;BA.debugLine="Dim foto5str As String = fotoMap.Get(\"foto5\")";
_foto5str = BA.ObjectToString(_fotomap.Get((Object)("foto5")));
 //BA.debugLineNum = 149;BA.debugLine="Log(\"Foto1:\" & foto1str)";
anywheresoftware.b4a.keywords.Common.LogImpl("227066397","Foto1:"+_foto1str,0);
 //BA.debugLineNum = 150;BA.debugLine="Log(\"Foto2:\" & foto2str)";
anywheresoftware.b4a.keywords.Common.LogImpl("227066398","Foto2:"+_foto2str,0);
 //BA.debugLineNum = 151;BA.debugLine="Log(\"Foto3:\" & foto3str)";
anywheresoftware.b4a.keywords.Common.LogImpl("227066399","Foto3:"+_foto3str,0);
 //BA.debugLineNum = 152;BA.debugLine="Log(\"Foto4:\" & foto4str)";
anywheresoftware.b4a.keywords.Common.LogImpl("227066400","Foto4:"+_foto4str,0);
 //BA.debugLineNum = 153;BA.debugLine="Log(\"Foto5:\" & foto5str)";
anywheresoftware.b4a.keywords.Common.LogImpl("227066401","Foto5:"+_foto5str,0);
 //BA.debugLineNum = 155;BA.debugLine="If foto1str = \"\" Or foto1str = \"null\" Then";
if (true) break;

case 6:
//if
this.state = 19;
if ((_foto1str).equals("") || (_foto1str).equals("null")) { 
this.state = 8;
}else if((_foto2str).equals("") || (_foto2str).equals("null")) { 
this.state = 10;
}else if((_foto3str).equals("") || (_foto3str).equals("null")) { 
this.state = 12;
}else if((_foto4str).equals("") || (_foto4str).equals("null")) { 
this.state = 14;
}else if((_foto5str).equals("") || (_foto5str).equals("null")) { 
this.state = 16;
}else {
this.state = 18;
}if (true) break;

case 8:
//C
this.state = 19;
 //BA.debugLineNum = 156;BA.debugLine="fotoNombreDestino = Form_Reporte.fullidcurrentp";
parent.mostCurrent._fotonombredestino = parent.mostCurrent._form_reporte._fullidcurrentproject /*String*/ +"_1";
 //BA.debugLineNum = 157;BA.debugLine="Log(\"Va la foto 1\")";
anywheresoftware.b4a.keywords.Common.LogImpl("227066405","Va la foto 1",0);
 //BA.debugLineNum = 158;BA.debugLine="lblInstruccion.Text = \"Foto #1\"";
parent.mostCurrent._lblinstruccion.setText(BA.ObjectToCharSequence("Foto #1"));
 if (true) break;

case 10:
//C
this.state = 19;
 //BA.debugLineNum = 160;BA.debugLine="fotoNombreDestino = Form_Reporte.fullidcurrentp";
parent.mostCurrent._fotonombredestino = parent.mostCurrent._form_reporte._fullidcurrentproject /*String*/ +"_2";
 //BA.debugLineNum = 161;BA.debugLine="Log(\"Va la foto 2\")";
anywheresoftware.b4a.keywords.Common.LogImpl("227066409","Va la foto 2",0);
 //BA.debugLineNum = 162;BA.debugLine="lblInstruccion.Text = \"Foto #2\"";
parent.mostCurrent._lblinstruccion.setText(BA.ObjectToCharSequence("Foto #2"));
 if (true) break;

case 12:
//C
this.state = 19;
 //BA.debugLineNum = 164;BA.debugLine="fotoNombreDestino = Form_Reporte.fullidcurrentp";
parent.mostCurrent._fotonombredestino = parent.mostCurrent._form_reporte._fullidcurrentproject /*String*/ +"_3";
 //BA.debugLineNum = 165;BA.debugLine="Log(\"Va la foto 3\")";
anywheresoftware.b4a.keywords.Common.LogImpl("227066413","Va la foto 3",0);
 //BA.debugLineNum = 166;BA.debugLine="lblInstruccion.Text = \"Foto #3\"";
parent.mostCurrent._lblinstruccion.setText(BA.ObjectToCharSequence("Foto #3"));
 if (true) break;

case 14:
//C
this.state = 19;
 //BA.debugLineNum = 168;BA.debugLine="fotoNombreDestino = Form_Reporte.fullidcurrentp";
parent.mostCurrent._fotonombredestino = parent.mostCurrent._form_reporte._fullidcurrentproject /*String*/ +"_4";
 //BA.debugLineNum = 169;BA.debugLine="Log(\"Va la foto 4\")";
anywheresoftware.b4a.keywords.Common.LogImpl("227066417","Va la foto 4",0);
 //BA.debugLineNum = 170;BA.debugLine="lblInstruccion.Text = \"Foto #4\"";
parent.mostCurrent._lblinstruccion.setText(BA.ObjectToCharSequence("Foto #4"));
 if (true) break;

case 16:
//C
this.state = 19;
 //BA.debugLineNum = 172;BA.debugLine="fotoNombreDestino = Form_Reporte.fullidcurrentp";
parent.mostCurrent._fotonombredestino = parent.mostCurrent._form_reporte._fullidcurrentproject /*String*/ +"_5";
 //BA.debugLineNum = 173;BA.debugLine="Log(\"Va la foto 5\")";
anywheresoftware.b4a.keywords.Common.LogImpl("227066421","Va la foto 5",0);
 //BA.debugLineNum = 174;BA.debugLine="lblInstruccion.Text = \"Foto #5\"";
parent.mostCurrent._lblinstruccion.setText(BA.ObjectToCharSequence("Foto #5"));
 if (true) break;

case 18:
//C
this.state = 19;
 //BA.debugLineNum = 176;BA.debugLine="btnAdjuntarFoto.Visible = False";
parent.mostCurrent._btnadjuntarfoto.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 177;BA.debugLine="btnContinuar.Visible = True";
parent.mostCurrent._btncontinuar.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 178;BA.debugLine="btnTakePicture.Visible = False";
parent.mostCurrent._btntakepicture.setVisible(anywheresoftware.b4a.keywords.Common.False);
 if (true) break;
;
 //BA.debugLineNum = 183;BA.debugLine="If foto1str <> \"\" And foto1str <> \"null\" Then";

case 19:
//if
this.state = 28;
if ((_foto1str).equals("") == false && (_foto1str).equals("null") == false) { 
this.state = 21;
}if (true) break;

case 21:
//C
this.state = 22;
 //BA.debugLineNum = 185;BA.debugLine="Log(\"sigue foto 1\")";
anywheresoftware.b4a.keywords.Common.LogImpl("227066433","sigue foto 1",0);
 //BA.debugLineNum = 186;BA.debugLine="imgSitio1.Visible = True";
parent.mostCurrent._imgsitio1.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 187;BA.debugLine="If File.Exists(File.DirRootExternal & \"/PreserV";
if (true) break;

case 22:
//if
this.state = 27;
if (anywheresoftware.b4a.keywords.Common.File.Exists(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/PreserVamos/",BA.ObjectToString(_fotomap.Get((Object)("foto1")))+".jpg")) { 
this.state = 24;
}else {
this.state = 26;
}if (true) break;

case 24:
//C
this.state = 27;
 //BA.debugLineNum = 188;BA.debugLine="imgSitio1.Bitmap = Null";
parent.mostCurrent._imgsitio1.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null));
 //BA.debugLineNum = 189;BA.debugLine="imgSitio1.Bitmap = LoadBitmapSample(File.DirRo";
parent.mostCurrent._imgsitio1.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/PreserVamos/",BA.ObjectToString(_fotomap.Get((Object)("foto1")))+".jpg",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 190;BA.debugLine="btnContinuar.Visible = True";
parent.mostCurrent._btncontinuar.setVisible(anywheresoftware.b4a.keywords.Common.True);
 if (true) break;

case 26:
//C
this.state = 27;
 //BA.debugLineNum = 192;BA.debugLine="imgSitio1.Visible = False";
parent.mostCurrent._imgsitio1.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 193;BA.debugLine="btnContinuar.Visible = False";
parent.mostCurrent._btncontinuar.setVisible(anywheresoftware.b4a.keywords.Common.False);
 if (true) break;

case 27:
//C
this.state = 28;
;
 if (true) break;
;
 //BA.debugLineNum = 196;BA.debugLine="If foto2str <> \"\" And foto2str <> \"null\" Then";

case 28:
//if
this.state = 37;
if ((_foto2str).equals("") == false && (_foto2str).equals("null") == false) { 
this.state = 30;
}if (true) break;

case 30:
//C
this.state = 31;
 //BA.debugLineNum = 197;BA.debugLine="Log(\"sigue foto 2\")";
anywheresoftware.b4a.keywords.Common.LogImpl("227066445","sigue foto 2",0);
 //BA.debugLineNum = 198;BA.debugLine="imgSitio2.Visible = True";
parent.mostCurrent._imgsitio2.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 199;BA.debugLine="If File.Exists(File.DirRootExternal & \"/PreserV";
if (true) break;

case 31:
//if
this.state = 36;
if (anywheresoftware.b4a.keywords.Common.File.Exists(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/PreserVamos/",BA.ObjectToString(_fotomap.Get((Object)("foto2")))+".jpg")) { 
this.state = 33;
}else {
this.state = 35;
}if (true) break;

case 33:
//C
this.state = 36;
 //BA.debugLineNum = 200;BA.debugLine="imgSitio2.Bitmap = Null";
parent.mostCurrent._imgsitio2.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null));
 //BA.debugLineNum = 201;BA.debugLine="imgSitio2.Bitmap = LoadBitmapSample(File.DirRo";
parent.mostCurrent._imgsitio2.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/PreserVamos/",BA.ObjectToString(_fotomap.Get((Object)("foto2")))+".jpg",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 202;BA.debugLine="btnContinuar.Visible = True";
parent.mostCurrent._btncontinuar.setVisible(anywheresoftware.b4a.keywords.Common.True);
 if (true) break;

case 35:
//C
this.state = 36;
 //BA.debugLineNum = 204;BA.debugLine="imgSitio2.Visible = False";
parent.mostCurrent._imgsitio2.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 205;BA.debugLine="btnContinuar.Visible = False";
parent.mostCurrent._btncontinuar.setVisible(anywheresoftware.b4a.keywords.Common.False);
 if (true) break;

case 36:
//C
this.state = 37;
;
 if (true) break;
;
 //BA.debugLineNum = 208;BA.debugLine="If foto3str <> \"\" And foto3str <> \"null\" Then";

case 37:
//if
this.state = 46;
if ((_foto3str).equals("") == false && (_foto3str).equals("null") == false) { 
this.state = 39;
}if (true) break;

case 39:
//C
this.state = 40;
 //BA.debugLineNum = 209;BA.debugLine="Log(\"sigue foto 3\")";
anywheresoftware.b4a.keywords.Common.LogImpl("227066457","sigue foto 3",0);
 //BA.debugLineNum = 210;BA.debugLine="imgSitio3.Visible = True";
parent.mostCurrent._imgsitio3.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 211;BA.debugLine="If File.Exists(File.DirRootExternal & \"/PreserV";
if (true) break;

case 40:
//if
this.state = 45;
if (anywheresoftware.b4a.keywords.Common.File.Exists(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/PreserVamos/",BA.ObjectToString(_fotomap.Get((Object)("foto3")))+".jpg")) { 
this.state = 42;
}else {
this.state = 44;
}if (true) break;

case 42:
//C
this.state = 45;
 //BA.debugLineNum = 212;BA.debugLine="imgSitio3.Bitmap = Null";
parent.mostCurrent._imgsitio3.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null));
 //BA.debugLineNum = 213;BA.debugLine="imgSitio3.Bitmap = LoadBitmapSample(File.DirRo";
parent.mostCurrent._imgsitio3.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/PreserVamos/",BA.ObjectToString(_fotomap.Get((Object)("foto3")))+".jpg",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 214;BA.debugLine="btnContinuar.Visible = True";
parent.mostCurrent._btncontinuar.setVisible(anywheresoftware.b4a.keywords.Common.True);
 if (true) break;

case 44:
//C
this.state = 45;
 //BA.debugLineNum = 216;BA.debugLine="imgSitio3.Visible = False";
parent.mostCurrent._imgsitio3.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 217;BA.debugLine="btnContinuar.Visible = False";
parent.mostCurrent._btncontinuar.setVisible(anywheresoftware.b4a.keywords.Common.False);
 if (true) break;

case 45:
//C
this.state = 46;
;
 if (true) break;
;
 //BA.debugLineNum = 220;BA.debugLine="If foto4str <> \"\" And foto4str <> \"null\" Then";

case 46:
//if
this.state = 55;
if ((_foto4str).equals("") == false && (_foto4str).equals("null") == false) { 
this.state = 48;
}if (true) break;

case 48:
//C
this.state = 49;
 //BA.debugLineNum = 221;BA.debugLine="Log(\"sigue foto 4\")";
anywheresoftware.b4a.keywords.Common.LogImpl("227066469","sigue foto 4",0);
 //BA.debugLineNum = 222;BA.debugLine="imgSitio4.Visible = True";
parent.mostCurrent._imgsitio4.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 223;BA.debugLine="If File.Exists(File.DirRootExternal & \"/PreserV";
if (true) break;

case 49:
//if
this.state = 54;
if (anywheresoftware.b4a.keywords.Common.File.Exists(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/PreserVamos/",BA.ObjectToString(_fotomap.Get((Object)("foto4")))+".jpg")) { 
this.state = 51;
}else {
this.state = 53;
}if (true) break;

case 51:
//C
this.state = 54;
 //BA.debugLineNum = 224;BA.debugLine="imgSitio4.Bitmap = Null";
parent.mostCurrent._imgsitio4.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null));
 //BA.debugLineNum = 225;BA.debugLine="imgSitio4.Bitmap = LoadBitmapSample(File.DirRo";
parent.mostCurrent._imgsitio4.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/PreserVamos/",BA.ObjectToString(_fotomap.Get((Object)("foto4")))+".jpg",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 226;BA.debugLine="btnContinuar.Visible = True";
parent.mostCurrent._btncontinuar.setVisible(anywheresoftware.b4a.keywords.Common.True);
 if (true) break;

case 53:
//C
this.state = 54;
 //BA.debugLineNum = 228;BA.debugLine="imgSitio4.Visible = False";
parent.mostCurrent._imgsitio4.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 229;BA.debugLine="btnContinuar.Visible = False";
parent.mostCurrent._btncontinuar.setVisible(anywheresoftware.b4a.keywords.Common.False);
 if (true) break;

case 54:
//C
this.state = 55;
;
 if (true) break;
;
 //BA.debugLineNum = 232;BA.debugLine="If foto5str <> \"\" And foto5str <> \"null\" Then";

case 55:
//if
this.state = 64;
if ((_foto5str).equals("") == false && (_foto5str).equals("null") == false) { 
this.state = 57;
}if (true) break;

case 57:
//C
this.state = 58;
 //BA.debugLineNum = 233;BA.debugLine="Log(\"sigue foto 5\")";
anywheresoftware.b4a.keywords.Common.LogImpl("227066481","sigue foto 5",0);
 //BA.debugLineNum = 234;BA.debugLine="imgSitio5.Visible = True";
parent.mostCurrent._imgsitio5.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 235;BA.debugLine="If File.Exists(File.DirRootExternal & \"/PreserV";
if (true) break;

case 58:
//if
this.state = 63;
if (anywheresoftware.b4a.keywords.Common.File.Exists(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/PreserVamos/",BA.ObjectToString(_fotomap.Get((Object)("foto5")))+".jpg")) { 
this.state = 60;
}else {
this.state = 62;
}if (true) break;

case 60:
//C
this.state = 63;
 //BA.debugLineNum = 236;BA.debugLine="imgSitio5.Bitmap = Null";
parent.mostCurrent._imgsitio5.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null));
 //BA.debugLineNum = 237;BA.debugLine="imgSitio5.Bitmap = LoadBitmapSample(File.DirRo";
parent.mostCurrent._imgsitio5.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/PreserVamos/",BA.ObjectToString(_fotomap.Get((Object)("foto5")))+".jpg",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 238;BA.debugLine="btnContinuar.Visible = True";
parent.mostCurrent._btncontinuar.setVisible(anywheresoftware.b4a.keywords.Common.True);
 if (true) break;

case 62:
//C
this.state = 63;
 //BA.debugLineNum = 240;BA.debugLine="imgSitio5.Visible = False";
parent.mostCurrent._imgsitio5.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 241;BA.debugLine="btnContinuar.Visible = False";
parent.mostCurrent._btncontinuar.setVisible(anywheresoftware.b4a.keywords.Common.False);
 if (true) break;

case 63:
//C
this.state = 64;
;
 if (true) break;

case 64:
//C
this.state = 65;
;
 if (true) break;

case 65:
//C
this.state = -1;
;
 //BA.debugLineNum = 246;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 //BA.debugLineNum = 248;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 13;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 18;BA.debugLine="Private Panel1 As Panel";
mostCurrent._panel1 = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 19;BA.debugLine="Private camEx As CameraExClass";
mostCurrent._camex = new ilpla.appear.cameraexclass();
 //BA.debugLineNum = 20;BA.debugLine="Private btnTakePicture As Button";
mostCurrent._btntakepicture = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 21;BA.debugLine="Private imgFlash As ImageView";
mostCurrent._imgflash = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 22;BA.debugLine="Private lblInstruccion As Label";
mostCurrent._lblinstruccion = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 23;BA.debugLine="Private btnAdjuntarFoto As Button";
mostCurrent._btnadjuntarfoto = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 24;BA.debugLine="Private btnContinuar As Button";
mostCurrent._btncontinuar = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 25;BA.debugLine="Private imgSitio4 As ImageView";
mostCurrent._imgsitio4 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 26;BA.debugLine="Private imgSitio3 As ImageView";
mostCurrent._imgsitio3 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 27;BA.debugLine="Private imgSitio2 As ImageView";
mostCurrent._imgsitio2 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 28;BA.debugLine="Private imgSitio1 As ImageView";
mostCurrent._imgsitio1 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 29;BA.debugLine="Private imgSitio5 As ImageView";
mostCurrent._imgsitio5 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 30;BA.debugLine="Private imgMenu As ImageView";
mostCurrent._imgmenu = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 31;BA.debugLine="Private seekFocus As SeekBar";
mostCurrent._seekfocus = new anywheresoftware.b4a.objects.SeekBarWrapper();
 //BA.debugLineNum = 34;BA.debugLine="Dim fotoNombreDestino As String";
mostCurrent._fotonombredestino = "";
 //BA.debugLineNum = 36;BA.debugLine="End Sub";
return "";
}
public static String  _imgflash_click() throws Exception{
float[] _f = null;
anywheresoftware.b4a.objects.collections.List _flashmodes = null;
String _flash = "";
 //BA.debugLineNum = 350;BA.debugLine="Sub imgFlash_Click";
 //BA.debugLineNum = 351;BA.debugLine="Dim f() As Float = camEx.GetFocusDistances";
_f = mostCurrent._camex._getfocusdistances /*float[]*/ ();
 //BA.debugLineNum = 352;BA.debugLine="Log(f(0) & \", \" & f(1) & \", \" & f(2))";
anywheresoftware.b4a.keywords.Common.LogImpl("227459586",BA.NumberToString(_f[(int) (0)])+", "+BA.NumberToString(_f[(int) (1)])+", "+BA.NumberToString(_f[(int) (2)]),0);
 //BA.debugLineNum = 353;BA.debugLine="Dim flashModes As List = camEx.GetSupportedFlashM";
_flashmodes = new anywheresoftware.b4a.objects.collections.List();
_flashmodes = mostCurrent._camex._getsupportedflashmodes /*anywheresoftware.b4a.objects.collections.List*/ ();
 //BA.debugLineNum = 354;BA.debugLine="If flashModes.IsInitialized = False Then";
if (_flashmodes.IsInitialized()==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 355;BA.debugLine="ToastMessageShow(\"Flash not supported.\", False)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Flash not supported."),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 356;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 358;BA.debugLine="Dim flash As String = flashModes.Get((flashModes.";
_flash = BA.ObjectToString(_flashmodes.Get((int) ((_flashmodes.IndexOf((Object)(mostCurrent._camex._getflashmode /*String*/ ()))+1)%_flashmodes.getSize())));
 //BA.debugLineNum = 359;BA.debugLine="camEx.SetFlashMode(flash)";
mostCurrent._camex._setflashmode /*String*/ (_flash);
 //BA.debugLineNum = 360;BA.debugLine="ToastMessageShow(flash, False)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence(_flash),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 361;BA.debugLine="camEx.CommitParameters";
mostCurrent._camex._commitparameters /*String*/ ();
 //BA.debugLineNum = 362;BA.debugLine="End Sub";
return "";
}
public static String  _imgmenu_click() throws Exception{
 //BA.debugLineNum = 100;BA.debugLine="Sub imgMenu_Click";
 //BA.debugLineNum = 101;BA.debugLine="Activity.OpenMenu";
mostCurrent._activity.OpenMenu();
 //BA.debugLineNum = 102;BA.debugLine="End Sub";
return "";
}
public static String  _initializecamera() throws Exception{
 //BA.debugLineNum = 258;BA.debugLine="Private Sub InitializeCamera";
 //BA.debugLineNum = 260;BA.debugLine="camEx.Initialize(Panel1, frontCamera, Me, \"Camer";
mostCurrent._camex._initialize /*String*/ (mostCurrent.activityBA,mostCurrent._panel1,_frontcamera,reporte_fotos.getObject(),"Camera1");
 //BA.debugLineNum = 263;BA.debugLine="frontCamera = camEx.Front";
_frontcamera = mostCurrent._camex._front /*boolean*/ ;
 //BA.debugLineNum = 265;BA.debugLine="End Sub";
return "";
}
public static String  _mnuadjuntar_click() throws Exception{
 //BA.debugLineNum = 103;BA.debugLine="Sub mnuAdjuntar_Click";
 //BA.debugLineNum = 105;BA.debugLine="camEx.StopPreview";
mostCurrent._camex._stoppreview /*String*/ ();
 //BA.debugLineNum = 106;BA.debugLine="camEx.Release";
mostCurrent._camex._release /*String*/ ();
 //BA.debugLineNum = 107;BA.debugLine="DesignaFoto";
_designafoto();
 //BA.debugLineNum = 108;BA.debugLine="CC.Initialize(\"CC\")";
_cc.Initialize("CC");
 //BA.debugLineNum = 109;BA.debugLine="CC.Show(\"image/\", \"Elija la foto\")";
_cc.Show(processBA,"image/","Elija la foto");
 //BA.debugLineNum = 110;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 6;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 9;BA.debugLine="Private frontCamera As Boolean = False";
_frontcamera = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 10;BA.debugLine="Dim CC As ContentChooser";
_cc = new anywheresoftware.b4a.phone.Phone.ContentChooser();
 //BA.debugLineNum = 11;BA.debugLine="End Sub";
return "";
}
public static String  _seekfocus_valuechanged(int _value,boolean _userchanged) throws Exception{
 //BA.debugLineNum = 370;BA.debugLine="Sub seekFocus_ValueChanged (Value As Int, UserChan";
 //BA.debugLineNum = 371;BA.debugLine="If UserChanged = False Or camEx.IsZoomSupported =";
if (_userchanged==anywheresoftware.b4a.keywords.Common.False || mostCurrent._camex._iszoomsupported /*boolean*/ ()==anywheresoftware.b4a.keywords.Common.False) { 
if (true) return "";};
 //BA.debugLineNum = 372;BA.debugLine="camEx.Zoom = Value / 100 * camEx.GetMaxZoom";
mostCurrent._camex._setzoom /*int*/ ((int) (_value/(double)100*mostCurrent._camex._getmaxzoom /*int*/ ()));
 //BA.debugLineNum = 373;BA.debugLine="camEx.CommitParameters";
mostCurrent._camex._commitparameters /*String*/ ();
 //BA.debugLineNum = 374;BA.debugLine="End Sub";
return "";
}
public static String  _setmaxsize() throws Exception{
ilpla.appear.cameraexclass._camerasize _mincs = null;
ilpla.appear.cameraexclass._camerasize _cs = null;
 //BA.debugLineNum = 281;BA.debugLine="Private Sub SetMaxSize";
 //BA.debugLineNum = 282;BA.debugLine="Dim minCS As CameraSize";
_mincs = new ilpla.appear.cameraexclass._camerasize();
 //BA.debugLineNum = 283;BA.debugLine="For Each cs As CameraSize In camEx.GetSupportedPi";
{
final ilpla.appear.cameraexclass._camerasize[] group2 = mostCurrent._camex._getsupportedpicturessizes /*ilpla.appear.cameraexclass._camerasize[]*/ ();
final int groupLen2 = group2.length
;int index2 = 0;
;
for (; index2 < groupLen2;index2++){
_cs = group2[index2];
 //BA.debugLineNum = 284;BA.debugLine="If minCS.Width = 0 Then";
if (_mincs.Width /*int*/ ==0) { 
 //BA.debugLineNum = 285;BA.debugLine="minCS = cs";
_mincs = _cs;
 }else {
 //BA.debugLineNum = 288;BA.debugLine="If Power(minCS.Width, 2) + Power(minCS.Height,";
if (anywheresoftware.b4a.keywords.Common.Power(_mincs.Width /*int*/ ,2)+anywheresoftware.b4a.keywords.Common.Power(_mincs.Height /*int*/ ,2)<anywheresoftware.b4a.keywords.Common.Power(_cs.Width /*int*/ ,2)+anywheresoftware.b4a.keywords.Common.Power(_cs.Height /*int*/ ,2)) { 
 //BA.debugLineNum = 289;BA.debugLine="minCS = cs";
_mincs = _cs;
 };
 };
 }
};
 //BA.debugLineNum = 293;BA.debugLine="camEx.SetPictureSize(minCS.Width, minCS.Height)";
mostCurrent._camex._setpicturesize /*String*/ (_mincs.Width /*int*/ ,_mincs.Height /*int*/ );
 //BA.debugLineNum = 294;BA.debugLine="Log(\"Selected size: \" & minCS)";
anywheresoftware.b4a.keywords.Common.LogImpl("227262989","Selected size: "+BA.ObjectToString(_mincs),0);
 //BA.debugLineNum = 295;BA.debugLine="End Sub";
return "";
}
}
