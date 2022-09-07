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

public class alerta_fotos extends Activity implements B4AActivity{
	public static alerta_fotos mostCurrent;
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
			processBA = new BA(this.getApplicationContext(), null, null, "appear.pnud.preservamos", "appear.pnud.preservamos.alerta_fotos");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
            
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (alerta_fotos).");
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
		activityBA = new BA(this, layout, processBA, "appear.pnud.preservamos", "appear.pnud.preservamos.alerta_fotos");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "appear.pnud.preservamos.alerta_fotos", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density, mostCurrent);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (alerta_fotos) Create, isFirst = " + isFirst + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (alerta_fotos) Resume **");
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
		return alerta_fotos.class;
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
            BA.LogInfo("** Activity (alerta_fotos) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
        else
            BA.LogInfo("** Activity (alerta_fotos) Pause event (activity is not paused). **");
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
            alerta_fotos mc = mostCurrent;
			if (mc == null || mc != activity.get())
				return;
			processBA.setActivityPaused(false);
            BA.LogInfo("** Activity (alerta_fotos) Resume **");
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
public anywheresoftware.b4a.objects.ButtonWrapper _btntakepicture = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imgflash = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblinstruccion = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnadjuntarfoto = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btncontinuar = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imgmenu = null;
public static String _fotonombredestino = "";
public anywheresoftware.b4a.objects.B4XViewWrapper.XUI _xui = null;
public static boolean _videomode = false;
public static int _mytaskindex = 0;
public anywheresoftware.b4a.objects.RuntimePermissions _rp = null;
public appear.pnud.preservamos.camex2 _cam = null;
public anywheresoftware.b4a.objects.B4XViewWrapper _pnlcamera = null;
public anywheresoftware.b4a.objects.B4XViewWrapper _pnlbackground = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btneffects = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnscene = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnautoexposure = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnfocus = null;
public anywheresoftware.b4a.objects.ProgressBarWrapper _progressbar1 = null;
public static boolean _openstate = false;
public static boolean _busystate = false;
public anywheresoftware.b4a.objects.ButtonWrapper _btnrecord = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnmode = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btncamera = null;
public anywheresoftware.b4a.objects.SeekBarWrapper _barzoom = null;
public appear.pnud.preservamos.b4ximageview _b4ximageview1 = null;
public anywheresoftware.b4a.objects.PanelWrapper _pnlinstrucciones = null;
public b4a.example.dateutils _dateutils = null;
public appear.pnud.preservamos.main _main = null;
public appear.pnud.preservamos.form_main _form_main = null;
public appear.pnud.preservamos.starter _starter = null;
public appear.pnud.preservamos.inatcheck _inatcheck = null;
public appear.pnud.preservamos.frmlocalizacion _frmlocalizacion = null;
public appear.pnud.preservamos.reporte_envio _reporte_envio = null;
public appear.pnud.preservamos.alertas _alertas = null;
public appear.pnud.preservamos.register _register = null;
public appear.pnud.preservamos.frmeditprofile _frmeditprofile = null;
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
 //BA.debugLineNum = 57;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
 //BA.debugLineNum = 59;BA.debugLine="Activity.LoadLayout(\"Camera_New\")";
mostCurrent._activity.LoadLayout("Camera_New",mostCurrent.activityBA);
 //BA.debugLineNum = 60;BA.debugLine="Activity.LoadLayout(\"Camera_New_StillPicture\")";
mostCurrent._activity.LoadLayout("Camera_New_StillPicture",mostCurrent.activityBA);
 //BA.debugLineNum = 62;BA.debugLine="If Alertas.tipo_alerta = \"floracion\" Then";
if ((mostCurrent._alertas._tipo_alerta /*String*/ ).equals("floracion")) { 
 //BA.debugLineNum = 63;BA.debugLine="pnlInstrucciones.Visible = True";
mostCurrent._pnlinstrucciones.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 64;BA.debugLine="btnRecord.Visible = False";
mostCurrent._btnrecord.setVisible(anywheresoftware.b4a.keywords.Common.False);
 }else {
 //BA.debugLineNum = 66;BA.debugLine="pnlInstrucciones.Visible = False";
mostCurrent._pnlinstrucciones.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 67;BA.debugLine="cam.Initialize(pnlCamera)";
mostCurrent._cam._initialize /*String*/ (mostCurrent.activityBA,(anywheresoftware.b4a.objects.PanelWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.PanelWrapper(), (android.view.ViewGroup)(mostCurrent._pnlcamera.getObject())));
 //BA.debugLineNum = 68;BA.debugLine="Log(cam.SupportedHardwareLevel)";
anywheresoftware.b4a.keywords.Common.LogImpl("533685515",mostCurrent._cam._getsupportedhardwarelevel /*String*/ (),0);
 //BA.debugLineNum = 70;BA.debugLine="SetState(False, False, VideoMode)";
_setstate(anywheresoftware.b4a.keywords.Common.False,anywheresoftware.b4a.keywords.Common.False,_videomode);
 //BA.debugLineNum = 74;BA.debugLine="Activity.AddMenuItem(\"Adjuntar foto\", \"mnuAdjunt";
mostCurrent._activity.AddMenuItem(BA.ObjectToCharSequence("Adjuntar foto"),"mnuAdjuntar");
 //BA.debugLineNum = 75;BA.debugLine="If Alertas.tipo_alerta = \"caza\" Then";
if ((mostCurrent._alertas._tipo_alerta /*String*/ ).equals("caza")) { 
 //BA.debugLineNum = 76;BA.debugLine="lblInstruccion.Text = \"Si tienes alguna foto de";
mostCurrent._lblinstruccion.setText(BA.ObjectToCharSequence("Si tienes alguna foto del evento, agregala ahora"));
 }else {
 //BA.debugLineNum = 78;BA.debugLine="lblInstruccion.Text = \"Toma entre 1 y 4 fotos d";
mostCurrent._lblinstruccion.setText(BA.ObjectToCharSequence("Toma entre 1 y 4 fotos del evento"));
 };
 //BA.debugLineNum = 81;BA.debugLine="btnRecord.Visible = True";
mostCurrent._btnrecord.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 82;BA.debugLine="DesignaFoto";
_designafoto();
 };
 //BA.debugLineNum = 87;BA.debugLine="End Sub";
return "";
}
public static boolean  _activity_keypress(int _keycode) throws Exception{
 //BA.debugLineNum = 101;BA.debugLine="Sub Activity_KeyPress (KeyCode As Int) As Boolean";
 //BA.debugLineNum = 102;BA.debugLine="If KeyCode = KeyCodes.KEYCODE_BACK Then";
if (_keycode==anywheresoftware.b4a.keywords.Common.KeyCodes.KEYCODE_BACK) { 
 //BA.debugLineNum = 103;BA.debugLine="closeAppMsgBox";
_closeappmsgbox();
 //BA.debugLineNum = 104;BA.debugLine="Return True";
if (true) return anywheresoftware.b4a.keywords.Common.True;
 }else {
 //BA.debugLineNum = 106;BA.debugLine="Return False";
if (true) return anywheresoftware.b4a.keywords.Common.False;
 };
 //BA.debugLineNum = 108;BA.debugLine="End Sub";
return false;
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 96;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 97;BA.debugLine="cam.Stop";
mostCurrent._cam._stop /*String*/ ();
 //BA.debugLineNum = 98;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 89;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 90;BA.debugLine="If pnlInstrucciones.Visible = False Then";
if (mostCurrent._pnlinstrucciones.getVisible()==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 91;BA.debugLine="OpenCamera(frontCamera)";
_opencamera(_frontcamera);
 };
 //BA.debugLineNum = 94;BA.debugLine="End Sub";
return "";
}
public static String  _barzoom_valuechanged(int _value,boolean _userchanged) throws Exception{
anywheresoftware.b4a.objects.drawable.CanvasWrapper.RectWrapper _originalsize = null;
float _zoom = 0f;
anywheresoftware.b4a.objects.drawable.CanvasWrapper.RectWrapper _crop = null;
int _newwidth = 0;
int _newheight = 0;
 //BA.debugLineNum = 292;BA.debugLine="Sub barZoom_ValueChanged (Value As Int, UserChange";
 //BA.debugLineNum = 293;BA.debugLine="Dim OriginalSize As Rect = cam.ActiveArraySize";
_originalsize = new anywheresoftware.b4a.objects.drawable.CanvasWrapper.RectWrapper();
_originalsize = mostCurrent._cam._getactivearraysize /*anywheresoftware.b4a.objects.drawable.CanvasWrapper.RectWrapper*/ ();
 //BA.debugLineNum = 294;BA.debugLine="Dim Zoom As Float = 1 + Value / 100 * (cam.MaxDig";
_zoom = (float) (1+_value/(double)100*(mostCurrent._cam._getmaxdigitalzoom /*float*/ ()-1));
 //BA.debugLineNum = 295;BA.debugLine="Dim Crop As Rect";
_crop = new anywheresoftware.b4a.objects.drawable.CanvasWrapper.RectWrapper();
 //BA.debugLineNum = 296;BA.debugLine="Dim NewWidth As Int = OriginalSize.Width / Zoom";
_newwidth = (int) (_originalsize.getWidth()/(double)_zoom);
 //BA.debugLineNum = 297;BA.debugLine="Dim NewHeight As Int = OriginalSize.Height / Zoom";
_newheight = (int) (_originalsize.getHeight()/(double)_zoom);
 //BA.debugLineNum = 298;BA.debugLine="Crop.Initialize(OriginalSize.CenterX - NewWidth /";
_crop.Initialize((int) (_originalsize.getCenterX()-_newwidth/(double)2),(int) (_originalsize.getCenterY()-_newheight/(double)2),(int) (_originalsize.getCenterX()+_newwidth/(double)2),(int) (_originalsize.getCenterY()+_newheight/(double)2));
 //BA.debugLineNum = 300;BA.debugLine="cam.PreviewCropRegion = Crop";
mostCurrent._cam._setpreviewcropregion(_crop);
 //BA.debugLineNum = 301;BA.debugLine="cam.StartPreview(MyTaskIndex, VideoMode)";
mostCurrent._cam._startpreview /*String*/ (_mytaskindex,_videomode);
 //BA.debugLineNum = 302;BA.debugLine="End Sub";
return "";
}
public static String  _btnadjuntarfoto_click() throws Exception{
 //BA.debugLineNum = 357;BA.debugLine="Sub btnAdjuntarFoto_Click";
 //BA.debugLineNum = 359;BA.debugLine="cam.Stop";
mostCurrent._cam._stop /*String*/ ();
 //BA.debugLineNum = 361;BA.debugLine="CC.Initialize(\"CC\")";
_cc.Initialize("CC");
 //BA.debugLineNum = 362;BA.debugLine="CC.Show(\"image/\", \"Elija la foto\")";
_cc.Show(processBA,"image/","Elija la foto");
 //BA.debugLineNum = 363;BA.debugLine="End Sub";
return "";
}
public static String  _btncamera_click() throws Exception{
 //BA.debugLineNum = 201;BA.debugLine="Sub btnCamera_Click";
 //BA.debugLineNum = 202;BA.debugLine="frontCamera = Not(frontCamera)";
_frontcamera = anywheresoftware.b4a.keywords.Common.Not(_frontcamera);
 //BA.debugLineNum = 203;BA.debugLine="OpenCamera(frontCamera)";
_opencamera(_frontcamera);
 //BA.debugLineNum = 204;BA.debugLine="End Sub";
return "";
}
public static String  _btncontinuar_click() throws Exception{
 //BA.debugLineNum = 493;BA.debugLine="Sub btnContinuar_Click";
 //BA.debugLineNum = 494;BA.debugLine="cam.Stop";
mostCurrent._cam._stop /*String*/ ();
 //BA.debugLineNum = 495;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 496;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 497;BA.debugLine="CallSubDelayed(Alertas,\"ActualizarFotos\")";
anywheresoftware.b4a.keywords.Common.CallSubDelayed(processBA,(Object)(mostCurrent._alertas.getObject()),"ActualizarFotos");
 //BA.debugLineNum = 498;BA.debugLine="End Sub";
return "";
}
public static String  _btnrecord_click() throws Exception{
 //BA.debugLineNum = 213;BA.debugLine="Sub btnRecord_Click";
 //BA.debugLineNum = 214;BA.debugLine="TakePicture";
_takepicture();
 //BA.debugLineNum = 215;BA.debugLine="End Sub";
return "";
}
public static String  _cc_result(boolean _success,String _dir,String _filename) throws Exception{
anywheresoftware.b4a.objects.collections.Map _map1 = null;
 //BA.debugLineNum = 365;BA.debugLine="Sub CC_Result (Success As Boolean, Dir As String,";
 //BA.debugLineNum = 366;BA.debugLine="If Success = True Then";
if (_success==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 369;BA.debugLine="Dim Map1 As Map";
_map1 = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 370;BA.debugLine="Map1.Initialize";
_map1.Initialize();
 //BA.debugLineNum = 371;BA.debugLine="Map1.Put(\"Id\", Form_Reporte.currentproject)";
_map1.Put((Object)("Id"),(Object)(mostCurrent._form_reporte._currentproject /*String*/ ));
 //BA.debugLineNum = 372;BA.debugLine="DateTime.DateFormat = \"dd-MM-yyyy\"";
anywheresoftware.b4a.keywords.Common.DateTime.setDateFormat("dd-MM-yyyy");
 //BA.debugLineNum = 373;BA.debugLine="If fotoNombreDestino.EndsWith(\"_1\") Then";
if (mostCurrent._fotonombredestino.endsWith("_1")) { 
 //BA.debugLineNum = 374;BA.debugLine="File.Copy(Dir, FileName, Starter.savedir, fotoN";
anywheresoftware.b4a.keywords.Common.File.Copy(_dir,_filename,mostCurrent._starter._savedir /*String*/ ,mostCurrent._fotonombredestino+".jpg");
 //BA.debugLineNum = 375;BA.debugLine="Alertas.Alerta_foto1 = fotoNombreDestino";
mostCurrent._alertas._alerta_foto1 /*String*/  = mostCurrent._fotonombredestino;
 }else if(mostCurrent._fotonombredestino.endsWith("_2")) { 
 //BA.debugLineNum = 378;BA.debugLine="File.Copy(Dir, FileName, Starter.savedir, fotoN";
anywheresoftware.b4a.keywords.Common.File.Copy(_dir,_filename,mostCurrent._starter._savedir /*String*/ ,mostCurrent._fotonombredestino+".jpg");
 //BA.debugLineNum = 379;BA.debugLine="Alertas.Alerta_foto2 = fotoNombreDestino";
mostCurrent._alertas._alerta_foto2 /*String*/  = mostCurrent._fotonombredestino;
 }else if(mostCurrent._fotonombredestino.endsWith("_3")) { 
 //BA.debugLineNum = 382;BA.debugLine="File.Copy(Dir, FileName, Starter.savedir, fotoN";
anywheresoftware.b4a.keywords.Common.File.Copy(_dir,_filename,mostCurrent._starter._savedir /*String*/ ,mostCurrent._fotonombredestino+".jpg");
 //BA.debugLineNum = 383;BA.debugLine="Alertas.Alerta_foto3 = fotoNombreDestino";
mostCurrent._alertas._alerta_foto3 /*String*/  = mostCurrent._fotonombredestino;
 }else if(mostCurrent._fotonombredestino.endsWith("_4")) { 
 //BA.debugLineNum = 385;BA.debugLine="File.Copy(Dir, FileName, Starter.savedir, fotoN";
anywheresoftware.b4a.keywords.Common.File.Copy(_dir,_filename,mostCurrent._starter._savedir /*String*/ ,mostCurrent._fotonombredestino+".jpg");
 //BA.debugLineNum = 386;BA.debugLine="Alertas.Alerta_foto4 = fotoNombreDestino";
mostCurrent._alertas._alerta_foto4 /*String*/  = mostCurrent._fotonombredestino;
 };
 //BA.debugLineNum = 391;BA.debugLine="DesignaFoto";
_designafoto();
 };
 //BA.debugLineNum = 393;BA.debugLine="End Sub";
return "";
}
public static void  _closeappmsgbox() throws Exception{
ResumableSub_closeAppMsgBox rsub = new ResumableSub_closeAppMsgBox(null);
rsub.resume(processBA, null);
}
public static class ResumableSub_closeAppMsgBox extends BA.ResumableSub {
public ResumableSub_closeAppMsgBox(appear.pnud.preservamos.alerta_fotos parent) {
this.parent = parent;
}
appear.pnud.preservamos.alerta_fotos parent;
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
 //BA.debugLineNum = 111;BA.debugLine="If Main.lang = \"es\" Then";
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
 //BA.debugLineNum = 112;BA.debugLine="Msgbox2Async(\"Cerrar?\", \"SALIR\", \"Si\", \"\", \"No\",";
anywheresoftware.b4a.keywords.Common.Msgbox2Async(BA.ObjectToCharSequence("Cerrar?"),BA.ObjectToCharSequence("SALIR"),"Si","","No",(anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper(), (android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null)),processBA,anywheresoftware.b4a.keywords.Common.False);
 if (true) break;

case 5:
//C
this.state = 6;
 //BA.debugLineNum = 114;BA.debugLine="Msgbox2Async(\"Back to the beginning?\", \"Exit\", \"";
anywheresoftware.b4a.keywords.Common.Msgbox2Async(BA.ObjectToCharSequence("Back to the beginning?"),BA.ObjectToCharSequence("Exit"),"Yes","","No",(anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper(), (android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null)),processBA,anywheresoftware.b4a.keywords.Common.False);
 if (true) break;

case 6:
//C
this.state = 7;
;
 //BA.debugLineNum = 116;BA.debugLine="Wait For Msgbox_Result (Result As Int)";
anywheresoftware.b4a.keywords.Common.WaitFor("msgbox_result", processBA, this, null);
this.state = 11;
return;
case 11:
//C
this.state = 7;
_result = (Integer) result[0];
;
 //BA.debugLineNum = 117;BA.debugLine="If Result = DialogResponse.POSITIVE Then";
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
 //BA.debugLineNum = 118;BA.debugLine="cam.Stop";
parent.mostCurrent._cam._stop /*String*/ ();
 //BA.debugLineNum = 119;BA.debugLine="Activity.finish";
parent.mostCurrent._activity.Finish();
 //BA.debugLineNum = 120;BA.debugLine="Activity.RemoveAllViews";
parent.mostCurrent._activity.RemoveAllViews();
 if (true) break;

case 10:
//C
this.state = -1;
;
 //BA.debugLineNum = 122;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public static void  _msgbox_result(int _result) throws Exception{
}
public static String  _designafoto() throws Exception{
String _rndstr = "";
 //BA.debugLineNum = 406;BA.debugLine="Sub DesignaFoto";
 //BA.debugLineNum = 408;BA.debugLine="DateTime.DateFormat = \"dd-MM-yyyy\"";
anywheresoftware.b4a.keywords.Common.DateTime.setDateFormat("dd-MM-yyyy");
 //BA.debugLineNum = 409;BA.debugLine="ProgressDialogShow(\"Preparando cámara...\")";
anywheresoftware.b4a.keywords.Common.ProgressDialogShow(mostCurrent.activityBA,BA.ObjectToCharSequence("Preparando cámara..."));
 //BA.debugLineNum = 412;BA.debugLine="Log(\"Designando foto\")";
anywheresoftware.b4a.keywords.Common.LogImpl("535061766","Designando foto",0);
 //BA.debugLineNum = 422;BA.debugLine="Dim RndStr As String";
_rndstr = "";
 //BA.debugLineNum = 423;BA.debugLine="RndStr = utilidades.RandomString(6)";
_rndstr = mostCurrent._utilidades._randomstring /*String*/ (mostCurrent.activityBA,(int) (6));
 //BA.debugLineNum = 425;BA.debugLine="If Alertas.tipo_alerta = \"floracion\" Then";
if ((mostCurrent._alertas._tipo_alerta /*String*/ ).equals("floracion")) { 
 //BA.debugLineNum = 426;BA.debugLine="If Alertas.Alerta_foto1 = \"\" Then";
if ((mostCurrent._alertas._alerta_foto1 /*String*/ ).equals("")) { 
 //BA.debugLineNum = 427;BA.debugLine="fotoNombreDestino = Main.strUserEmail.Replace(\"";
mostCurrent._fotonombredestino = mostCurrent._main._struseremail /*String*/ .replace(".","_");
 //BA.debugLineNum = 428;BA.debugLine="fotoNombreDestino = fotoNombreDestino.Replace(\"";
mostCurrent._fotonombredestino = mostCurrent._fotonombredestino.replace("@","_")+"_"+anywheresoftware.b4a.keywords.Common.DateTime.Date(anywheresoftware.b4a.keywords.Common.DateTime.getNow())+_rndstr+"_1";
 //BA.debugLineNum = 429;BA.debugLine="lblInstruccion.Text = \"Foto en la orilla o cost";
mostCurrent._lblinstruccion.setText(BA.ObjectToCharSequence("Foto en la orilla o costa"));
 }else if((mostCurrent._alertas._alerta_foto2 /*String*/ ).equals("")) { 
 //BA.debugLineNum = 431;BA.debugLine="fotoNombreDestino = Main.strUserEmail.Replace(\"";
mostCurrent._fotonombredestino = mostCurrent._main._struseremail /*String*/ .replace(".","_");
 //BA.debugLineNum = 432;BA.debugLine="fotoNombreDestino = fotoNombreDestino.Replace(\"";
mostCurrent._fotonombredestino = mostCurrent._fotonombredestino.replace("@","_")+"_"+anywheresoftware.b4a.keywords.Common.DateTime.Date(anywheresoftware.b4a.keywords.Common.DateTime.getNow())+_rndstr+"_2";
 //BA.debugLineNum = 433;BA.debugLine="lblInstruccion.Text = \"Foto más alejado de la o";
mostCurrent._lblinstruccion.setText(BA.ObjectToCharSequence("Foto más alejado de la orilla"));
 }else if((mostCurrent._alertas._alerta_foto3 /*String*/ ).equals("")) { 
 //BA.debugLineNum = 435;BA.debugLine="fotoNombreDestino = Main.strUserEmail.Replace(\"";
mostCurrent._fotonombredestino = mostCurrent._main._struseremail /*String*/ .replace(".","_");
 //BA.debugLineNum = 436;BA.debugLine="fotoNombreDestino = fotoNombreDestino.Replace(\"";
mostCurrent._fotonombredestino = mostCurrent._fotonombredestino.replace("@","_")+"_"+anywheresoftware.b4a.keywords.Common.DateTime.Date(anywheresoftware.b4a.keywords.Common.DateTime.getNow())+_rndstr+"_3";
 //BA.debugLineNum = 437;BA.debugLine="lblInstruccion.Text = \"Foto hacia el horizonte\"";
mostCurrent._lblinstruccion.setText(BA.ObjectToCharSequence("Foto hacia el horizonte"));
 }else if((mostCurrent._alertas._alerta_foto4 /*String*/ ).equals("")) { 
 //BA.debugLineNum = 439;BA.debugLine="btnContinuar.Visible = True";
mostCurrent._btncontinuar.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 440;BA.debugLine="fotoNombreDestino = Main.strUserEmail.Replace(\"";
mostCurrent._fotonombredestino = mostCurrent._main._struseremail /*String*/ .replace(".","_");
 //BA.debugLineNum = 441;BA.debugLine="fotoNombreDestino = fotoNombreDestino.Replace(\"";
mostCurrent._fotonombredestino = mostCurrent._fotonombredestino.replace("@","_")+"_"+anywheresoftware.b4a.keywords.Common.DateTime.Date(anywheresoftware.b4a.keywords.Common.DateTime.getNow())+_rndstr+"_4";
 //BA.debugLineNum = 442;BA.debugLine="lblInstruccion.Text = \"Muestra de agua en frasc";
mostCurrent._lblinstruccion.setText(BA.ObjectToCharSequence("Muestra de agua en frasco transparente, dejando reposar 5 minutos"));
 }else {
 //BA.debugLineNum = 445;BA.debugLine="cam.Stop";
mostCurrent._cam._stop /*String*/ ();
 //BA.debugLineNum = 446;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 447;BA.debugLine="CallSubDelayed(\"Alertas\", \"ActualizarFotos\")";
anywheresoftware.b4a.keywords.Common.CallSubDelayed(processBA,(Object)("Alertas"),"ActualizarFotos");
 //BA.debugLineNum = 448;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 };
 }else {
 //BA.debugLineNum = 452;BA.debugLine="If Alertas.Alerta_foto1 = \"\" Then";
if ((mostCurrent._alertas._alerta_foto1 /*String*/ ).equals("")) { 
 //BA.debugLineNum = 453;BA.debugLine="fotoNombreDestino = Main.strUserEmail.Replace(\"";
mostCurrent._fotonombredestino = mostCurrent._main._struseremail /*String*/ .replace(".","_");
 //BA.debugLineNum = 454;BA.debugLine="fotoNombreDestino = fotoNombreDestino.Replace(\"";
mostCurrent._fotonombredestino = mostCurrent._fotonombredestino.replace("@","_")+"_"+anywheresoftware.b4a.keywords.Common.DateTime.Date(anywheresoftware.b4a.keywords.Common.DateTime.getNow())+_rndstr+"_1";
 }else if((mostCurrent._alertas._alerta_foto2 /*String*/ ).equals("")) { 
 //BA.debugLineNum = 456;BA.debugLine="btnContinuar.Visible = True";
mostCurrent._btncontinuar.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 457;BA.debugLine="fotoNombreDestino = Main.strUserEmail.Replace(\"";
mostCurrent._fotonombredestino = mostCurrent._main._struseremail /*String*/ .replace(".","_");
 //BA.debugLineNum = 458;BA.debugLine="fotoNombreDestino = fotoNombreDestino.Replace(\"";
mostCurrent._fotonombredestino = mostCurrent._fotonombredestino.replace("@","_")+"_"+anywheresoftware.b4a.keywords.Common.DateTime.Date(anywheresoftware.b4a.keywords.Common.DateTime.getNow())+_rndstr+"_2";
 }else if((mostCurrent._alertas._alerta_foto3 /*String*/ ).equals("")) { 
 //BA.debugLineNum = 460;BA.debugLine="btnContinuar.Visible = True";
mostCurrent._btncontinuar.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 461;BA.debugLine="fotoNombreDestino = Main.strUserEmail.Replace(\"";
mostCurrent._fotonombredestino = mostCurrent._main._struseremail /*String*/ .replace(".","_");
 //BA.debugLineNum = 462;BA.debugLine="fotoNombreDestino = fotoNombreDestino.Replace(\"";
mostCurrent._fotonombredestino = mostCurrent._fotonombredestino.replace("@","_")+"_"+anywheresoftware.b4a.keywords.Common.DateTime.Date(anywheresoftware.b4a.keywords.Common.DateTime.getNow())+_rndstr+"_3";
 }else if((mostCurrent._alertas._alerta_foto4 /*String*/ ).equals("")) { 
 //BA.debugLineNum = 464;BA.debugLine="btnContinuar.Visible = True";
mostCurrent._btncontinuar.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 465;BA.debugLine="fotoNombreDestino = Main.strUserEmail.Replace(\"";
mostCurrent._fotonombredestino = mostCurrent._main._struseremail /*String*/ .replace(".","_");
 //BA.debugLineNum = 466;BA.debugLine="fotoNombreDestino = fotoNombreDestino.Replace(\"";
mostCurrent._fotonombredestino = mostCurrent._fotonombredestino.replace("@","_")+"_"+anywheresoftware.b4a.keywords.Common.DateTime.Date(anywheresoftware.b4a.keywords.Common.DateTime.getNow())+_rndstr+"_4";
 }else {
 //BA.debugLineNum = 469;BA.debugLine="cam.Stop";
mostCurrent._cam._stop /*String*/ ();
 //BA.debugLineNum = 470;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 471;BA.debugLine="CallSubDelayed(\"Alertas\", \"ActualizarFotos\")";
anywheresoftware.b4a.keywords.Common.CallSubDelayed(processBA,(Object)("Alertas"),"ActualizarFotos");
 //BA.debugLineNum = 472;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 };
 };
 //BA.debugLineNum = 478;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 //BA.debugLineNum = 480;BA.debugLine="End Sub";
return "";
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 13;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 18;BA.debugLine="Private Panel1 As Panel";
mostCurrent._panel1 = new anywheresoftware.b4a.objects.PanelWrapper();
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
 //BA.debugLineNum = 25;BA.debugLine="Private imgMenu As ImageView";
mostCurrent._imgmenu = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 28;BA.debugLine="Dim fotoNombreDestino As String";
mostCurrent._fotonombredestino = "";
 //BA.debugLineNum = 32;BA.debugLine="Private xui As XUI";
mostCurrent._xui = new anywheresoftware.b4a.objects.B4XViewWrapper.XUI();
 //BA.debugLineNum = 33;BA.debugLine="Private frontCamera As Boolean = False";
_frontcamera = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 34;BA.debugLine="Private VideoMode As Boolean = False";
_videomode = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 36;BA.debugLine="Private MyTaskIndex As Int";
_mytaskindex = 0;
 //BA.debugLineNum = 37;BA.debugLine="Private rp As RuntimePermissions";
mostCurrent._rp = new anywheresoftware.b4a.objects.RuntimePermissions();
 //BA.debugLineNum = 38;BA.debugLine="Private cam As CamEx2";
mostCurrent._cam = new appear.pnud.preservamos.camex2();
 //BA.debugLineNum = 39;BA.debugLine="Private pnlCamera As B4XView";
mostCurrent._pnlcamera = new anywheresoftware.b4a.objects.B4XViewWrapper();
 //BA.debugLineNum = 40;BA.debugLine="Private pnlBackground As B4XView";
mostCurrent._pnlbackground = new anywheresoftware.b4a.objects.B4XViewWrapper();
 //BA.debugLineNum = 41;BA.debugLine="Private btnEffects As Button";
mostCurrent._btneffects = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 42;BA.debugLine="Private btnScene As Button";
mostCurrent._btnscene = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 44;BA.debugLine="Private btnAutoExposure As Button";
mostCurrent._btnautoexposure = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 45;BA.debugLine="Private btnFocus As Button";
mostCurrent._btnfocus = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 46;BA.debugLine="Private ProgressBar1 As ProgressBar";
mostCurrent._progressbar1 = new anywheresoftware.b4a.objects.ProgressBarWrapper();
 //BA.debugLineNum = 47;BA.debugLine="Private openstate, busystate As Boolean";
_openstate = false;
_busystate = false;
 //BA.debugLineNum = 48;BA.debugLine="Private btnRecord As Button";
mostCurrent._btnrecord = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 49;BA.debugLine="Private btnMode As Button";
mostCurrent._btnmode = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 50;BA.debugLine="Private btnCamera As Button";
mostCurrent._btncamera = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 51;BA.debugLine="Private barZoom As SeekBar";
mostCurrent._barzoom = new anywheresoftware.b4a.objects.SeekBarWrapper();
 //BA.debugLineNum = 52;BA.debugLine="Private B4XImageView1 As B4XImageView";
mostCurrent._b4ximageview1 = new appear.pnud.preservamos.b4ximageview();
 //BA.debugLineNum = 54;BA.debugLine="Private pnlInstrucciones As Panel";
mostCurrent._pnlinstrucciones = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 55;BA.debugLine="End Sub";
return "";
}
public static String  _handleerror(anywheresoftware.b4a.objects.B4AException _error) throws Exception{
 //BA.debugLineNum = 207;BA.debugLine="Sub HandleError (Error As Exception)";
 //BA.debugLineNum = 208;BA.debugLine="Log(\"Error: \" & Error)";
anywheresoftware.b4a.keywords.Common.LogImpl("534340865","Error: "+BA.ObjectToString(_error),0);
 //BA.debugLineNum = 209;BA.debugLine="ToastMessageShow(Error, True)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence(_error.getObject()),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 210;BA.debugLine="OpenCamera(frontCamera)";
_opencamera(_frontcamera);
 //BA.debugLineNum = 211;BA.debugLine="End Sub";
return "";
}
public static String  _imgmenu_click() throws Exception{
 //BA.debugLineNum = 336;BA.debugLine="Sub imgMenu_Click";
 //BA.debugLineNum = 337;BA.debugLine="Activity.OpenMenu";
mostCurrent._activity.OpenMenu();
 //BA.debugLineNum = 338;BA.debugLine="End Sub";
return "";
}
public static String  _lblcomofuncionaentendido_click() throws Exception{
 //BA.debugLineNum = 125;BA.debugLine="Private Sub lblComoFuncionaEntendido_Click";
 //BA.debugLineNum = 126;BA.debugLine="pnlInstrucciones.Visible = False";
mostCurrent._pnlinstrucciones.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 127;BA.debugLine="cam.Initialize(pnlCamera)";
mostCurrent._cam._initialize /*String*/ (mostCurrent.activityBA,(anywheresoftware.b4a.objects.PanelWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.PanelWrapper(), (android.view.ViewGroup)(mostCurrent._pnlcamera.getObject())));
 //BA.debugLineNum = 128;BA.debugLine="Log(cam.SupportedHardwareLevel)";
anywheresoftware.b4a.keywords.Common.LogImpl("534013187",mostCurrent._cam._getsupportedhardwarelevel /*String*/ (),0);
 //BA.debugLineNum = 130;BA.debugLine="SetState(False, False, VideoMode)";
_setstate(anywheresoftware.b4a.keywords.Common.False,anywheresoftware.b4a.keywords.Common.False,_videomode);
 //BA.debugLineNum = 134;BA.debugLine="Activity.AddMenuItem(\"Adjuntar foto\", \"mnuAdjunta";
mostCurrent._activity.AddMenuItem(BA.ObjectToCharSequence("Adjuntar foto"),"mnuAdjuntar");
 //BA.debugLineNum = 135;BA.debugLine="lblInstruccion.Text = \"Toma hasta 4 fotos del eve";
mostCurrent._lblinstruccion.setText(BA.ObjectToCharSequence("Toma hasta 4 fotos del evento"));
 //BA.debugLineNum = 136;BA.debugLine="DesignaFoto";
_designafoto();
 //BA.debugLineNum = 137;BA.debugLine="OpenCamera(frontCamera)";
_opencamera(_frontcamera);
 //BA.debugLineNum = 138;BA.debugLine="End Sub";
return "";
}
public static String  _mnuadjuntar_click() throws Exception{
 //BA.debugLineNum = 339;BA.debugLine="Sub mnuAdjuntar_Click";
 //BA.debugLineNum = 341;BA.debugLine="cam.Stop";
mostCurrent._cam._stop /*String*/ ();
 //BA.debugLineNum = 342;BA.debugLine="DesignaFoto";
_designafoto();
 //BA.debugLineNum = 343;BA.debugLine="CC.Initialize(\"CC\")";
_cc.Initialize("CC");
 //BA.debugLineNum = 344;BA.debugLine="CC.Show(\"image/\", \"Elija la foto\")";
_cc.Show(processBA,"image/","Elija la foto");
 //BA.debugLineNum = 345;BA.debugLine="End Sub";
return "";
}
public static void  _opencamera(boolean _front) throws Exception{
ResumableSub_OpenCamera rsub = new ResumableSub_OpenCamera(null,_front);
rsub.resume(processBA, null);
}
public static class ResumableSub_OpenCamera extends BA.ResumableSub {
public ResumableSub_OpenCamera(appear.pnud.preservamos.alerta_fotos parent,boolean _front) {
this.parent = parent;
this._front = _front;
}
appear.pnud.preservamos.alerta_fotos parent;
boolean _front;
String _permission = "";
boolean _result = false;
int _taskindex = 0;
boolean _success = false;

@Override
public void resume(BA ba, Object[] result) throws Exception{

    while (true) {
        switch (state) {
            case -1:
return;

case 0:
//C
this.state = 1;
 //BA.debugLineNum = 151;BA.debugLine="rp.CheckAndRequest(rp.PERMISSION_CAMERA)";
parent.mostCurrent._rp.CheckAndRequest(processBA,parent.mostCurrent._rp.PERMISSION_CAMERA);
 //BA.debugLineNum = 152;BA.debugLine="Wait For Activity_PermissionResult (Permission As";
anywheresoftware.b4a.keywords.Common.WaitFor("activity_permissionresult", processBA, this, null);
this.state = 13;
return;
case 13:
//C
this.state = 1;
_permission = (String) result[0];
_result = (Boolean) result[1];
;
 //BA.debugLineNum = 153;BA.debugLine="If Result = False Then";
if (true) break;

case 1:
//if
this.state = 4;
if (_result==anywheresoftware.b4a.keywords.Common.False) { 
this.state = 3;
}if (true) break;

case 3:
//C
this.state = 4;
 //BA.debugLineNum = 154;BA.debugLine="ToastMessageShow(\"No permission!\", True)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("No permission!"),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 155;BA.debugLine="Return";
if (true) return ;
 if (true) break;

case 4:
//C
this.state = 5;
;
 //BA.debugLineNum = 158;BA.debugLine="SetState(False, False, VideoMode)";
_setstate(anywheresoftware.b4a.keywords.Common.False,anywheresoftware.b4a.keywords.Common.False,parent._videomode);
 //BA.debugLineNum = 159;BA.debugLine="Wait For (cam.OpenCamera(front)) Complete (TaskIn";
anywheresoftware.b4a.keywords.Common.WaitFor("complete", processBA, this, parent.mostCurrent._cam._opencamera /*anywheresoftware.b4a.keywords.Common.ResumableSubWrapper*/ (_front));
this.state = 14;
return;
case 14:
//C
this.state = 5;
_taskindex = (Integer) result[0];
;
 //BA.debugLineNum = 160;BA.debugLine="If TaskIndex > 0 Then";
if (true) break;

case 5:
//if
this.state = 8;
if (_taskindex>0) { 
this.state = 7;
}if (true) break;

case 7:
//C
this.state = 8;
 //BA.debugLineNum = 161;BA.debugLine="MyTaskIndex = TaskIndex 'hold this index. It wil";
parent._mytaskindex = _taskindex;
 //BA.debugLineNum = 162;BA.debugLine="Wait For(PrepareSurface) Complete (Success As Bo";
anywheresoftware.b4a.keywords.Common.WaitFor("complete", processBA, this, _preparesurface());
this.state = 15;
return;
case 15:
//C
this.state = 8;
_success = (Boolean) result[0];
;
 if (true) break;

case 8:
//C
this.state = 9;
;
 //BA.debugLineNum = 164;BA.debugLine="Log(\"Start success: \" & Success)";
anywheresoftware.b4a.keywords.Common.LogImpl("534078734","Start success: "+BA.ObjectToString(_success),0);
 //BA.debugLineNum = 165;BA.debugLine="SetState(Success, False, VideoMode)";
_setstate(_success,anywheresoftware.b4a.keywords.Common.False,parent._videomode);
 //BA.debugLineNum = 166;BA.debugLine="If Success = False Then";
if (true) break;

case 9:
//if
this.state = 12;
if (_success==anywheresoftware.b4a.keywords.Common.False) { 
this.state = 11;
}if (true) break;

case 11:
//C
this.state = 12;
 //BA.debugLineNum = 167;BA.debugLine="ToastMessageShow(\"Failed to open camera\", True)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Failed to open camera"),anywheresoftware.b4a.keywords.Common.True);
 if (true) break;

case 12:
//C
this.state = -1;
;
 //BA.debugLineNum = 169;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public static void  _activity_permissionresult(String _permission,boolean _result) throws Exception{
}
public static void  _complete(int _taskindex) throws Exception{
}
public static String  _pnlbackground_click() throws Exception{
 //BA.debugLineNum = 262;BA.debugLine="Sub pnlBackground_Click";
 //BA.debugLineNum = 263;BA.debugLine="pnlBackground.Visible = False";
mostCurrent._pnlbackground.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 264;BA.debugLine="End Sub";
return "";
}
public static anywheresoftware.b4a.keywords.Common.ResumableSubWrapper  _preparesurface() throws Exception{
ResumableSub_PrepareSurface rsub = new ResumableSub_PrepareSurface(null);
rsub.resume(processBA, null);
return (anywheresoftware.b4a.keywords.Common.ResumableSubWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.keywords.Common.ResumableSubWrapper(), rsub);
}
public static class ResumableSub_PrepareSurface extends BA.ResumableSub {
public ResumableSub_PrepareSurface(appear.pnud.preservamos.alerta_fotos parent) {
this.parent = parent;
}
appear.pnud.preservamos.alerta_fotos parent;
String _videofiledir = "";
String _videofilename = "";
boolean _success = false;

@Override
public void resume(BA ba, Object[] result) throws Exception{

    while (true) {
        switch (state) {
            case -1:
{
anywheresoftware.b4a.keywords.Common.ReturnFromResumableSub(this,null);return;}
case 0:
//C
this.state = 1;
 //BA.debugLineNum = 172;BA.debugLine="SetState(False, busystate, VideoMode)";
_setstate(anywheresoftware.b4a.keywords.Common.False,parent._busystate,parent._videomode);
 //BA.debugLineNum = 174;BA.debugLine="If VideoMode Then";
if (true) break;

case 1:
//if
this.state = 6;
if (parent._videomode) { 
this.state = 3;
}else {
this.state = 5;
}if (true) break;

case 3:
//C
this.state = 6;
 //BA.debugLineNum = 175;BA.debugLine="cam.PreviewSize.Initialize(640, 480)";
parent.mostCurrent._cam._previewsize /*anywheresoftware.b4a.objects.Camera2.CameraSizeWrapper*/ .Initialize((int) (640),(int) (480));
 //BA.debugLineNum = 176;BA.debugLine="ResizePreviewPanelBasedPreviewSize";
_resizepreviewpanelbasedpreviewsize();
 //BA.debugLineNum = 178;BA.debugLine="Wait For (cam.PrepareSurfaceForVideo(MyTaskIndex";
anywheresoftware.b4a.keywords.Common.WaitFor("complete", processBA, this, parent.mostCurrent._cam._preparesurfaceforvideo /*anywheresoftware.b4a.keywords.Common.ResumableSubWrapper*/ (parent._mytaskindex,_videofiledir,"temp-"+_videofilename));
this.state = 12;
return;
case 12:
//C
this.state = 6;
_success = (Boolean) result[0];
;
 if (true) break;

case 5:
//C
this.state = 6;
 //BA.debugLineNum = 180;BA.debugLine="cam.PreviewSize.Initialize(1920, 1080)";
parent.mostCurrent._cam._previewsize /*anywheresoftware.b4a.objects.Camera2.CameraSizeWrapper*/ .Initialize((int) (1920),(int) (1080));
 //BA.debugLineNum = 181;BA.debugLine="ResizePreviewPanelBasedPreviewSize";
_resizepreviewpanelbasedpreviewsize();
 //BA.debugLineNum = 185;BA.debugLine="Wait For (cam.PrepareSurface(MyTaskIndex)) Compl";
anywheresoftware.b4a.keywords.Common.WaitFor("complete", processBA, this, parent.mostCurrent._cam._preparesurface /*anywheresoftware.b4a.keywords.Common.ResumableSubWrapper*/ (parent._mytaskindex));
this.state = 13;
return;
case 13:
//C
this.state = 6;
_success = (Boolean) result[0];
;
 if (true) break;
;
 //BA.debugLineNum = 188;BA.debugLine="If Success Then cam.StartPreview(MyTaskIndex, Vid";

case 6:
//if
this.state = 11;
if (_success) { 
this.state = 8;
;}if (true) break;

case 8:
//C
this.state = 11;
parent.mostCurrent._cam._startpreview /*String*/ (parent._mytaskindex,parent._videomode);
if (true) break;

case 11:
//C
this.state = -1;
;
 //BA.debugLineNum = 189;BA.debugLine="SetState(Success, busystate, VideoMode)";
_setstate(_success,parent._busystate,parent._videomode);
 //BA.debugLineNum = 190;BA.debugLine="Return Success";
if (true) {
anywheresoftware.b4a.keywords.Common.ReturnFromResumableSub(this,(Object)(_success));return;};
 //BA.debugLineNum = 191;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
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
public static String  _resizepreviewpanelbasedpreviewsize() throws Exception{
int _pw = 0;
int _ph = 0;
float _r = 0f;
int _w = 0;
int _h = 0;
 //BA.debugLineNum = 193;BA.debugLine="Private Sub ResizePreviewPanelBasedPreviewSize";
 //BA.debugLineNum = 194;BA.debugLine="Dim pw = cam.PreviewSize.Height, ph = cam.Preview";
_pw = mostCurrent._cam._previewsize /*anywheresoftware.b4a.objects.Camera2.CameraSizeWrapper*/ .getHeight();
_ph = mostCurrent._cam._previewsize /*anywheresoftware.b4a.objects.Camera2.CameraSizeWrapper*/ .getWidth();
 //BA.debugLineNum = 195;BA.debugLine="Dim r As Float = Max(Activity.Width / pw, Activit";
_r = (float) (anywheresoftware.b4a.keywords.Common.Max(mostCurrent._activity.getWidth()/(double)_pw,mostCurrent._activity.getHeight()/(double)_ph));
 //BA.debugLineNum = 196;BA.debugLine="Dim w As Int = pw * r";
_w = (int) (_pw*_r);
 //BA.debugLineNum = 197;BA.debugLine="Dim h As Int = ph * r";
_h = (int) (_ph*_r);
 //BA.debugLineNum = 198;BA.debugLine="pnlCamera.SetLayoutAnimated(0, Round(Activity.Wid";
mostCurrent._pnlcamera.SetLayoutAnimated((int) (0),(int) (anywheresoftware.b4a.keywords.Common.Round(mostCurrent._activity.getWidth()/(double)2-_w/(double)2)),(int) (anywheresoftware.b4a.keywords.Common.Round(mostCurrent._activity.getHeight()/(double)2-_h/(double)2)),(int) (anywheresoftware.b4a.keywords.Common.Round(_w)),(int) (anywheresoftware.b4a.keywords.Common.Round(_h)));
 //BA.debugLineNum = 199;BA.debugLine="End Sub";
return "";
}
public static anywheresoftware.b4a.objects.B4XViewWrapper.B4XBitmapWrapper  _rotatejpegifneeded(anywheresoftware.b4a.objects.B4XViewWrapper.B4XBitmapWrapper _bmp,byte[] _data) throws Exception{
anywheresoftware.b4a.phone.Phone _p = null;
anywheresoftware.b4j.object.JavaObject _exifinterface = null;
anywheresoftware.b4a.objects.streams.File.InputStreamWrapper _in = null;
int _orientation = 0;
 //BA.debugLineNum = 304;BA.debugLine="Private Sub RotateJpegIfNeeded (bmp As B4XBitmap,";
 //BA.debugLineNum = 305;BA.debugLine="Dim p As Phone";
_p = new anywheresoftware.b4a.phone.Phone();
 //BA.debugLineNum = 306;BA.debugLine="If p.SdkVersion >= 24 Then";
if (_p.getSdkVersion()>=24) { 
 //BA.debugLineNum = 307;BA.debugLine="Dim ExifInterface As JavaObject";
_exifinterface = new anywheresoftware.b4j.object.JavaObject();
 //BA.debugLineNum = 308;BA.debugLine="Dim in As InputStream";
_in = new anywheresoftware.b4a.objects.streams.File.InputStreamWrapper();
 //BA.debugLineNum = 309;BA.debugLine="in.InitializeFromBytesArray(Data, 0, Data.Length";
_in.InitializeFromBytesArray(_data,(int) (0),_data.length);
 //BA.debugLineNum = 310;BA.debugLine="ExifInterface.InitializeNewInstance(\"android.med";
_exifinterface.InitializeNewInstance("android.media.ExifInterface",new Object[]{(Object)(_in.getObject())});
 //BA.debugLineNum = 311;BA.debugLine="Dim orientation As Int = ExifInterface.RunMethod";
_orientation = (int)(BA.ObjectToNumber(_exifinterface.RunMethod("getAttribute",new Object[]{(Object)("Orientation")})));
 //BA.debugLineNum = 312;BA.debugLine="Select orientation";
switch (_orientation) {
case 3: {
 //BA.debugLineNum = 314;BA.debugLine="bmp = bmp.Rotate(180)";
_bmp = _bmp.Rotate((int) (180));
 break; }
case 6: {
 //BA.debugLineNum = 316;BA.debugLine="bmp = bmp.Rotate(90)";
_bmp = _bmp.Rotate((int) (90));
 break; }
case 8: {
 //BA.debugLineNum = 318;BA.debugLine="bmp = bmp.Rotate(270)";
_bmp = _bmp.Rotate((int) (270));
 break; }
}
;
 //BA.debugLineNum = 320;BA.debugLine="in.Close";
_in.Close();
 };
 //BA.debugLineNum = 322;BA.debugLine="Return bmp";
if (true) return _bmp;
 //BA.debugLineNum = 323;BA.debugLine="End Sub";
return null;
}
public static String  _setstate(boolean _open,boolean _busy,boolean _video) throws Exception{
String _btnrecordtext = "";
 //BA.debugLineNum = 268;BA.debugLine="Sub SetState(Open As Boolean, Busy As Boolean, Vid";
 //BA.debugLineNum = 272;BA.debugLine="btnCamera.Visible = Not(Busy)";
mostCurrent._btncamera.setVisible(anywheresoftware.b4a.keywords.Common.Not(_busy));
 //BA.debugLineNum = 273;BA.debugLine="btnRecord.Visible = Open And (Video Or Not(Busy))";
mostCurrent._btnrecord.setVisible(_open && (_video || anywheresoftware.b4a.keywords.Common.Not(_busy)));
 //BA.debugLineNum = 274;BA.debugLine="openstate = Open";
_openstate = _open;
 //BA.debugLineNum = 275;BA.debugLine="ProgressBar1.Visible = Busy";
mostCurrent._progressbar1.setVisible(_busy);
 //BA.debugLineNum = 276;BA.debugLine="busystate = Busy";
_busystate = _busy;
 //BA.debugLineNum = 277;BA.debugLine="VideoMode = Video";
_videomode = _video;
 //BA.debugLineNum = 278;BA.debugLine="barZoom.Visible = Open";
mostCurrent._barzoom.setVisible(_open);
 //BA.debugLineNum = 279;BA.debugLine="Dim btnRecordText As String";
_btnrecordtext = "";
 //BA.debugLineNum = 280;BA.debugLine="If VideoMode Then";
if (_videomode) { 
 //BA.debugLineNum = 281;BA.debugLine="If Busy Then";
if (_busy) { 
 //BA.debugLineNum = 282;BA.debugLine="btnRecordText = Chr(0xF04D)";
_btnrecordtext = BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr(((int)0xf04d)));
 }else {
 //BA.debugLineNum = 284;BA.debugLine="btnRecordText = Chr(0xF03D)";
_btnrecordtext = BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr(((int)0xf03d)));
 };
 }else {
 //BA.debugLineNum = 287;BA.debugLine="btnRecordText = Chr(0xF030)";
_btnrecordtext = BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr(((int)0xf030)));
 };
 //BA.debugLineNum = 289;BA.debugLine="btnRecord.Text = btnRecordText";
mostCurrent._btnrecord.setText(BA.ObjectToCharSequence(_btnrecordtext));
 //BA.debugLineNum = 290;BA.debugLine="End Sub";
return "";
}
public static void  _takepicture() throws Exception{
ResumableSub_TakePicture rsub = new ResumableSub_TakePicture(null);
rsub.resume(processBA, null);
}
public static class ResumableSub_TakePicture extends BA.ResumableSub {
public ResumableSub_TakePicture(appear.pnud.preservamos.alerta_fotos parent) {
this.parent = parent;
}
appear.pnud.preservamos.alerta_fotos parent;
byte[] _data = null;
anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper _bmp = null;

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
 //BA.debugLineNum = 218;BA.debugLine="Try";
if (true) break;

case 1:
//try
this.state = 16;
this.catchState = 15;
this.state = 3;
if (true) break;

case 3:
//C
this.state = 4;
this.catchState = 15;
 //BA.debugLineNum = 219;BA.debugLine="SetState(openstate, True, VideoMode)";
_setstate(parent._openstate,anywheresoftware.b4a.keywords.Common.True,parent._videomode);
 //BA.debugLineNum = 220;BA.debugLine="Wait For(cam.FocusAndTakePicture(MyTaskIndex)) C";
anywheresoftware.b4a.keywords.Common.WaitFor("complete", processBA, this, parent.mostCurrent._cam._focusandtakepicture /*anywheresoftware.b4a.keywords.Common.ResumableSubWrapper*/ (parent._mytaskindex));
this.state = 17;
return;
case 17:
//C
this.state = 4;
_data = (byte[]) result[0];
;
 //BA.debugLineNum = 221;BA.debugLine="SetState(openstate, False, VideoMode)";
_setstate(parent._openstate,anywheresoftware.b4a.keywords.Common.False,parent._videomode);
 //BA.debugLineNum = 224;BA.debugLine="cam.DataToFile(Data, Starter.savedir, fotoNombre";
parent.mostCurrent._cam._datatofile /*String*/ (_data,parent.mostCurrent._starter._savedir /*String*/ ,parent.mostCurrent._fotonombredestino+".jpg");
 //BA.debugLineNum = 226;BA.debugLine="Dim bmp As Bitmap = cam.DataToBitmap(Data)";
_bmp = new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper();
_bmp = parent.mostCurrent._cam._datatobitmap /*anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper*/ (_data);
 //BA.debugLineNum = 227;BA.debugLine="Log(\"Picture taken: \" & bmp) 'ignore";
anywheresoftware.b4a.keywords.Common.LogImpl("534471946","Picture taken: "+BA.ObjectToString(_bmp),0);
 //BA.debugLineNum = 228;BA.debugLine="pnlBackground.SetVisibleAnimated(100, True)";
parent.mostCurrent._pnlbackground.SetVisibleAnimated((int) (100),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 229;BA.debugLine="B4XImageView1.Bitmap = RotateJpegIfNeeded(bmp, D";
parent.mostCurrent._b4ximageview1._setbitmap /*anywheresoftware.b4a.objects.B4XViewWrapper.B4XBitmapWrapper*/ (_rotatejpegifneeded((anywheresoftware.b4a.objects.B4XViewWrapper.B4XBitmapWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.B4XViewWrapper.B4XBitmapWrapper(), (android.graphics.Bitmap)(_bmp.getObject())),_data));
 //BA.debugLineNum = 230;BA.debugLine="Sleep(1000)";
anywheresoftware.b4a.keywords.Common.Sleep(mostCurrent.activityBA,this,(int) (1000));
this.state = 18;
return;
case 18:
//C
this.state = 4;
;
 //BA.debugLineNum = 231;BA.debugLine="pnlBackground.SetVisibleAnimated(500, False)";
parent.mostCurrent._pnlbackground.SetVisibleAnimated((int) (500),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 237;BA.debugLine="If fotoNombreDestino.EndsWith(\"_1\") Then";
if (true) break;

case 4:
//if
this.state = 13;
if (parent.mostCurrent._fotonombredestino.endsWith("_1")) { 
this.state = 6;
}else if(parent.mostCurrent._fotonombredestino.endsWith("_2")) { 
this.state = 8;
}else if(parent.mostCurrent._fotonombredestino.endsWith("_3")) { 
this.state = 10;
}else if(parent.mostCurrent._fotonombredestino.endsWith("_4")) { 
this.state = 12;
}if (true) break;

case 6:
//C
this.state = 13;
 //BA.debugLineNum = 238;BA.debugLine="Alertas.Alerta_foto1 = fotoNombreDestino";
parent.mostCurrent._alertas._alerta_foto1 /*String*/  = parent.mostCurrent._fotonombredestino;
 if (true) break;

case 8:
//C
this.state = 13;
 //BA.debugLineNum = 241;BA.debugLine="Alertas.Alerta_foto2 = fotoNombreDestino";
parent.mostCurrent._alertas._alerta_foto2 /*String*/  = parent.mostCurrent._fotonombredestino;
 if (true) break;

case 10:
//C
this.state = 13;
 //BA.debugLineNum = 244;BA.debugLine="Alertas.Alerta_foto3 = fotoNombreDestino";
parent.mostCurrent._alertas._alerta_foto3 /*String*/  = parent.mostCurrent._fotonombredestino;
 if (true) break;

case 12:
//C
this.state = 13;
 //BA.debugLineNum = 247;BA.debugLine="Alertas.Alerta_foto4 = fotoNombreDestino";
parent.mostCurrent._alertas._alerta_foto4 /*String*/  = parent.mostCurrent._fotonombredestino;
 if (true) break;

case 13:
//C
this.state = 16;
;
 //BA.debugLineNum = 253;BA.debugLine="DesignaFoto";
_designafoto();
 if (true) break;

case 15:
//C
this.state = 16;
this.catchState = 0;
 //BA.debugLineNum = 255;BA.debugLine="HandleError(LastException)";
_handleerror(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA));
 if (true) break;
if (true) break;

case 16:
//C
this.state = -1;
this.catchState = 0;
;
 //BA.debugLineNum = 258;BA.debugLine="End Sub";
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
}
