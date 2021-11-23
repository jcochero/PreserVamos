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
			processBA = new BA(this.getApplicationContext(), null, null, "ilpla.appear", "ilpla.appear.frmdatosanteriores");
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
		activityBA = new BA(this, layout, processBA, "ilpla.appear", "ilpla.appear.frmdatosanteriores");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "ilpla.appear.frmdatosanteriores", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density, mostCurrent);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (frmdatosanteriores) Create, isFirst = " + isFirst + " **");
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
public static anywheresoftware.b4h.okhttp.OkHttpClientWrapper _hc = null;
public static anywheresoftware.b4a.objects.streams.File.OutputStreamWrapper _out = null;
public static anywheresoftware.b4a.phone.Phone.ContentChooser _cc = null;
public static String _nuevalatlng = "";
public static boolean _notificacion = false;
public static String _serveridnum = "";
public static boolean _hayanteriores = false;
public static com.spinter.uploadfilephp.UploadFilePhp _up1 = null;
public static String _foto1 = "";
public static String _foto2 = "";
public static String _foto3 = "";
public static String _foto4 = "";
public static String _foto5 = "";
public anywheresoftware.b4a.objects.ListViewWrapper _listview1 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btncerrar = null;
public anywheresoftware.b4a.objects.TabStripViewPager _tabstripdatosanteriores = null;
public static String _tabactual = "";
public anywheresoftware.b4a.objects.LabelWrapper _lblfecha = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbltipo = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbllat = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblcalidad = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblnotas = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbllng = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper _chkpublico = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper _chkenviado = null;
public anywheresoftware.b4a.objects.LabelWrapper _chkvalidadoexpertos = null;
public anywheresoftware.b4a.objects.LabelWrapper _chkonline = null;
public anywheresoftware.b4a.objects.LabelWrapper _chkenviadobar = null;
public anywheresoftware.b4a.objects.HorizontalScrollViewWrapper _scvfotos = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblnombreenvio = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblbarra = null;
public static String _currentdatoid = "";
public anywheresoftware.b4a.objects.ImageViewWrapper _foto1view = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _foto2view = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _foto3view = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _foto4view = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _foto5view = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _fotogrande = null;
public anywheresoftware.b4a.objects.LabelWrapper _fondogris = null;
public anywheresoftware.b4a.objects.ButtonWrapper _foto1borrar = null;
public anywheresoftware.b4a.objects.ButtonWrapper _foto2borrar = null;
public anywheresoftware.b4a.objects.ButtonWrapper _foto3borrar = null;
public anywheresoftware.b4a.objects.ButtonWrapper _foto4borrar = null;
public anywheresoftware.b4a.objects.ButtonWrapper _foto5borrar = null;
public static String _fotoadjuntar = "";
public anywheresoftware.b4a.objects.ButtonWrapper _btnenviar = null;
public static String _idproyectoenviar = "";
public anywheresoftware.b4a.objects.ButtonWrapper _btncambiarpublico = null;
public static boolean _newmarcador = false;
public static int _numbertasks = 0;
public anywheresoftware.b4a.agraham.dialogs.InputDialog.DateDialog _dated = null;
public static boolean _hubocambiosdatos = false;
public static boolean _hubocambiosfotos = false;
public anywheresoftware.b4a.objects.LabelWrapper _fondoblanco = null;
public anywheresoftware.b4a.objects.LabelWrapper _foto1prg = null;
public anywheresoftware.b4a.objects.LabelWrapper _foto2prg = null;
public anywheresoftware.b4a.objects.LabelWrapper _foto3prg = null;
public anywheresoftware.b4a.objects.LabelWrapper _foto4prg = null;
public anywheresoftware.b4a.objects.LabelWrapper _foto5prg = null;
public anywheresoftware.b4a.objects.ProgressBarWrapper _progressbar1 = null;
public anywheresoftware.b4a.objects.ProgressBarWrapper _progressbar2 = null;
public anywheresoftware.b4a.objects.ProgressBarWrapper _progressbar3 = null;
public anywheresoftware.b4a.objects.ProgressBarWrapper _progressbar4 = null;
public anywheresoftware.b4a.objects.ProgressBarWrapper _progressbar5 = null;
public static int _totalfotos = 0;
public static boolean _foto1sent = false;
public static boolean _foto2sent = false;
public static boolean _foto3sent = false;
public static boolean _foto4sent = false;
public static boolean _foto5sent = false;
public anywheresoftware.b4a.objects.Timer _timerenvio = null;
public static int _fotosenviadas = 0;
public anywheresoftware.b4a.objects.LabelWrapper _label9 = null;
public anywheresoftware.b4a.objects.LabelWrapper _label6 = null;
public anywheresoftware.b4a.objects.LabelWrapper _label7 = null;
public anywheresoftware.b4a.objects.LabelWrapper _label8 = null;
public anywheresoftware.b4a.objects.LabelWrapper _label10 = null;
public anywheresoftware.b4a.objects.LabelWrapper _label12 = null;
public anywheresoftware.b4a.objects.LabelWrapper _label3 = null;
public anywheresoftware.b4a.objects.LabelWrapper _label2 = null;
public anywheresoftware.b4a.objects.LabelWrapper _label13 = null;
public ilpla.appear.main _main = null;
public ilpla.appear.form_main _form_main = null;
public ilpla.appear.aprender_muestreo _aprender_muestreo = null;
public ilpla.appear.dbutils _dbutils = null;
public ilpla.appear.downloadservice _downloadservice = null;
public ilpla.appear.firebasemessaging _firebasemessaging = null;
public ilpla.appear.form_reporte _form_reporte = null;
public ilpla.appear.frmabout _frmabout = null;
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
 //BA.debugLineNum = 127;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
 //BA.debugLineNum = 128;BA.debugLine="Activity.LoadLayout(\"layPerfilDatosAnteriores\")";
mostCurrent._activity.LoadLayout("layPerfilDatosAnteriores",mostCurrent.activityBA);
 //BA.debugLineNum = 131;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 132;BA.debugLine="tabStripDatosAnteriores.LoadLayout(\"layPerfilDat";
mostCurrent._tabstripdatosanteriores.LoadLayout("layPerfilDatosAnteriores_Lista",BA.ObjectToCharSequence("Datos anteriores"));
 //BA.debugLineNum = 133;BA.debugLine="tabStripDatosAnteriores.LoadLayout(\"layPerfilDat";
mostCurrent._tabstripdatosanteriores.LoadLayout("layPerfilDatosAnteriores_Detalle",BA.ObjectToCharSequence("Detalle"));
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 135;BA.debugLine="tabStripDatosAnteriores.LoadLayout(\"layPerfilDat";
mostCurrent._tabstripdatosanteriores.LoadLayout("layPerfilDatosAnteriores_Lista",BA.ObjectToCharSequence("Previous reports"));
 //BA.debugLineNum = 136;BA.debugLine="tabStripDatosAnteriores.LoadLayout(\"layPerfilDat";
mostCurrent._tabstripdatosanteriores.LoadLayout("layPerfilDatosAnteriores_Detalle",BA.ObjectToCharSequence("Details"));
 };
 //BA.debugLineNum = 141;BA.debugLine="foto1view.Initialize(\"foto1view\")";
mostCurrent._foto1view.Initialize(mostCurrent.activityBA,"foto1view");
 //BA.debugLineNum = 142;BA.debugLine="foto2view.Initialize(\"foto2view\")";
mostCurrent._foto2view.Initialize(mostCurrent.activityBA,"foto2view");
 //BA.debugLineNum = 143;BA.debugLine="foto3view.Initialize(\"foto2view\")";
mostCurrent._foto3view.Initialize(mostCurrent.activityBA,"foto2view");
 //BA.debugLineNum = 144;BA.debugLine="foto4view.Initialize(\"foto2view\")";
mostCurrent._foto4view.Initialize(mostCurrent.activityBA,"foto2view");
 //BA.debugLineNum = 145;BA.debugLine="foto5view.Initialize(\"foto2view\")";
mostCurrent._foto5view.Initialize(mostCurrent.activityBA,"foto2view");
 //BA.debugLineNum = 146;BA.debugLine="foto1Borrar.Initialize(\"foto1Borrar\")";
mostCurrent._foto1borrar.Initialize(mostCurrent.activityBA,"foto1Borrar");
 //BA.debugLineNum = 147;BA.debugLine="foto2Borrar.Initialize(\"foto2Borrar\")";
mostCurrent._foto2borrar.Initialize(mostCurrent.activityBA,"foto2Borrar");
 //BA.debugLineNum = 148;BA.debugLine="foto3Borrar.Initialize(\"foto2Borrar\")";
mostCurrent._foto3borrar.Initialize(mostCurrent.activityBA,"foto2Borrar");
 //BA.debugLineNum = 149;BA.debugLine="foto4Borrar.Initialize(\"foto2Borrar\")";
mostCurrent._foto4borrar.Initialize(mostCurrent.activityBA,"foto2Borrar");
 //BA.debugLineNum = 150;BA.debugLine="foto5Borrar.Initialize(\"foto2Borrar\")";
mostCurrent._foto5borrar.Initialize(mostCurrent.activityBA,"foto2Borrar");
 //BA.debugLineNum = 152;BA.debugLine="scvFotos.Panel.AddView(foto1view, 0dip, 0dip,scvF";
mostCurrent._scvfotos.getPanel().AddView((android.view.View)(mostCurrent._foto1view.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (0)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (0)),(int) (mostCurrent._scvfotos.getWidth()/(double)2),mostCurrent._scvfotos.getHeight());
 //BA.debugLineNum = 153;BA.debugLine="scvFotos.Panel.AddView(foto2view, (scvFotos.Width";
mostCurrent._scvfotos.getPanel().AddView((android.view.View)(mostCurrent._foto2view.getObject()),(int) ((mostCurrent._scvfotos.getWidth()/(double)2)*1),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (0)),(int) (mostCurrent._scvfotos.getWidth()/(double)2),mostCurrent._scvfotos.getHeight());
 //BA.debugLineNum = 154;BA.debugLine="scvFotos.Panel.AddView(foto3view, (scvFotos.Width";
mostCurrent._scvfotos.getPanel().AddView((android.view.View)(mostCurrent._foto3view.getObject()),(int) ((mostCurrent._scvfotos.getWidth()/(double)2)*2),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (0)),(int) (mostCurrent._scvfotos.getWidth()/(double)2),mostCurrent._scvfotos.getHeight());
 //BA.debugLineNum = 155;BA.debugLine="scvFotos.Panel.AddView(foto4view, (scvFotos.Width";
mostCurrent._scvfotos.getPanel().AddView((android.view.View)(mostCurrent._foto4view.getObject()),(int) ((mostCurrent._scvfotos.getWidth()/(double)2)*3),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (0)),(int) (mostCurrent._scvfotos.getWidth()/(double)2),mostCurrent._scvfotos.getHeight());
 //BA.debugLineNum = 156;BA.debugLine="scvFotos.Panel.AddView(foto5view, (scvFotos.Width";
mostCurrent._scvfotos.getPanel().AddView((android.view.View)(mostCurrent._foto5view.getObject()),(int) ((mostCurrent._scvfotos.getWidth()/(double)2)*4),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (0)),(int) (mostCurrent._scvfotos.getWidth()/(double)2),mostCurrent._scvfotos.getHeight());
 //BA.debugLineNum = 158;BA.debugLine="If hc.IsInitialized = False Then";
if (_hc.IsInitialized()==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 159;BA.debugLine="hc.Initialize(\"hc\")";
_hc.Initialize("hc");
 };
 //BA.debugLineNum = 163;BA.debugLine="Up1.B4A_log=True";
_up1.B4A_log = anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 168;BA.debugLine="Up1.Initialize(\"Up1\")";
_up1.Initialize(processBA,"Up1");
 //BA.debugLineNum = 175;BA.debugLine="End Sub";
return "";
}
public static boolean  _activity_keypress(int _keycode) throws Exception{
 //BA.debugLineNum = 185;BA.debugLine="Sub Activity_KeyPress (KeyCode As Int) As Boolean";
 //BA.debugLineNum = 187;BA.debugLine="If KeyCode = KeyCodes.KEYCODE_BACK Then";
if (_keycode==anywheresoftware.b4a.keywords.Common.KeyCodes.KEYCODE_BACK) { 
 //BA.debugLineNum = 188;BA.debugLine="If tabActual = \"Lista\" Then";
if ((mostCurrent._tabactual).equals("Lista")) { 
 //BA.debugLineNum = 189;BA.debugLine="Activity.finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 190;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 }else if((mostCurrent._tabactual).equals("Detalle")) { 
 //BA.debugLineNum = 192;BA.debugLine="tabStripDatosAnteriores.ScrollTo(0,True)";
mostCurrent._tabstripdatosanteriores.ScrollTo((int) (0),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 193;BA.debugLine="tabActual = \"Lista\"";
mostCurrent._tabactual = "Lista";
 //BA.debugLineNum = 194;BA.debugLine="Return True";
if (true) return anywheresoftware.b4a.keywords.Common.True;
 }else {
 //BA.debugLineNum = 196;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 197;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 };
 };
 //BA.debugLineNum = 200;BA.debugLine="End Sub";
return false;
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 182;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 184;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 176;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 177;BA.debugLine="If nuevalatlng = \"\" And notificacion = False Then";
if ((_nuevalatlng).equals("") && _notificacion==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 178;BA.debugLine="ListarDatos";
_listardatos();
 };
 //BA.debugLineNum = 180;BA.debugLine="TranslateGUI";
_translategui();
 //BA.debugLineNum = 181;BA.debugLine="End Sub";
return "";
}
public static void  _btncambiarfecha_click() throws Exception{
ResumableSub_btnCambiarFecha_Click rsub = new ResumableSub_btnCambiarFecha_Click(null);
rsub.resume(processBA, null);
}
public static class ResumableSub_btnCambiarFecha_Click extends BA.ResumableSub {
public ResumableSub_btnCambiarFecha_Click(ilpla.appear.frmdatosanteriores parent) {
this.parent = parent;
}
ilpla.appear.frmdatosanteriores parent;
int _result = 0;
String _fechanueva = "";
anywheresoftware.b4a.objects.collections.Map _map1 = null;

@Override
public void resume(BA ba, Object[] result) throws Exception{

    while (true) {
        switch (state) {
            case -1:
return;

case 0:
//C
this.state = 1;
 //BA.debugLineNum = 971;BA.debugLine="If Main.lang = \"es\" Then";
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
 //BA.debugLineNum = 972;BA.debugLine="Msgbox2Async(\"Desea cambiar la fecha de este dat";
anywheresoftware.b4a.keywords.Common.Msgbox2Async(BA.ObjectToCharSequence("Desea cambiar la fecha de este dato?"),BA.ObjectToCharSequence("Cambiar fecha"),"Si","No","",(anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper(), (android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null)),processBA,anywheresoftware.b4a.keywords.Common.False);
 if (true) break;

case 5:
//C
this.state = 6;
 //BA.debugLineNum = 974;BA.debugLine="Msgbox2Async(\"Do you want to change the date for";
anywheresoftware.b4a.keywords.Common.Msgbox2Async(BA.ObjectToCharSequence("Do you want to change the date for this report?"),BA.ObjectToCharSequence("Change date"),"Yes","No","",(anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper(), (android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null)),processBA,anywheresoftware.b4a.keywords.Common.False);
 if (true) break;

case 6:
//C
this.state = 7;
;
 //BA.debugLineNum = 976;BA.debugLine="Wait For Msgbox_Result (Result As Int)";
anywheresoftware.b4a.keywords.Common.WaitFor("msgbox_result", processBA, this, null);
this.state = 13;
return;
case 13:
//C
this.state = 7;
_result = (Integer) result[0];
;
 //BA.debugLineNum = 977;BA.debugLine="If Result = DialogResponse.POSITIVE Then	'Muestra";
if (true) break;

case 7:
//if
this.state = 12;
if (_result==anywheresoftware.b4a.keywords.Common.DialogResponse.POSITIVE) { 
this.state = 9;
}else {
this.state = 11;
}if (true) break;

case 9:
//C
this.state = 12;
 //BA.debugLineNum = 978;BA.debugLine="Dim fechanueva As String";
_fechanueva = "";
 //BA.debugLineNum = 979;BA.debugLine="dated.ShowCalendar = True";
parent.mostCurrent._dated.ShowCalendar = anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 980;BA.debugLine="dated.Year = DateTime.GetYear(DateTime.now)";
parent.mostCurrent._dated.setYear(anywheresoftware.b4a.keywords.Common.DateTime.GetYear(anywheresoftware.b4a.keywords.Common.DateTime.getNow()));
 //BA.debugLineNum = 981;BA.debugLine="dated.Month = DateTime.GetMonth(DateTime.now)";
parent.mostCurrent._dated.setMonth(anywheresoftware.b4a.keywords.Common.DateTime.GetMonth(anywheresoftware.b4a.keywords.Common.DateTime.getNow()));
 //BA.debugLineNum = 982;BA.debugLine="dated.DayOfMonth = DateTime.GetDayOfMonth(DateTi";
parent.mostCurrent._dated.setDayOfMonth(anywheresoftware.b4a.keywords.Common.DateTime.GetDayOfMonth(anywheresoftware.b4a.keywords.Common.DateTime.getNow()));
 //BA.debugLineNum = 983;BA.debugLine="dated.Show(\"Elija la nueva fecha para este dato\"";
parent.mostCurrent._dated.Show("Elija la nueva fecha para este dato","Cambio de fecha","Ok","Cancelar","",mostCurrent.activityBA,(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null));
 //BA.debugLineNum = 984;BA.debugLine="fechanueva = dated.DayOfMonth & \"-\" & dated.Mont";
_fechanueva = BA.NumberToString(parent.mostCurrent._dated.getDayOfMonth())+"-"+BA.NumberToString(parent.mostCurrent._dated.getMonth())+"-"+BA.NumberToString(parent.mostCurrent._dated.getYear());
 //BA.debugLineNum = 985;BA.debugLine="ToastMessageShow(\"Nueva fecha: \" & fechanueva, F";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Nueva fecha: "+_fechanueva),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 987;BA.debugLine="Dim Map1 As Map";
_map1 = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 988;BA.debugLine="Map1.Initialize";
_map1.Initialize();
 //BA.debugLineNum = 989;BA.debugLine="Map1.Put(\"Id\", currentDatoId)";
_map1.Put((Object)("Id"),(Object)(parent.mostCurrent._currentdatoid));
 //BA.debugLineNum = 990;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"evals\", \"ge";
parent.mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,parent.mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals","georeferencedDate",(Object)(_fechanueva),_map1);
 //BA.debugLineNum = 992;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"evals\", \"ev";
parent.mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,parent.mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals","evalsent",(Object)("no"),_map1);
 //BA.debugLineNum = 993;BA.debugLine="lblFecha.Text = fechanueva";
parent.mostCurrent._lblfecha.setText(BA.ObjectToCharSequence(_fechanueva));
 //BA.debugLineNum = 994;BA.debugLine="btnEnviar.Visible = True";
parent.mostCurrent._btnenviar.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 995;BA.debugLine="NewMarcador = False";
parent._newmarcador = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 996;BA.debugLine="hubocambiosDatos = True";
parent._hubocambiosdatos = anywheresoftware.b4a.keywords.Common.True;
 if (true) break;

case 11:
//C
this.state = 12;
 //BA.debugLineNum = 998;BA.debugLine="Return";
if (true) return ;
 if (true) break;

case 12:
//C
this.state = -1;
;
 //BA.debugLineNum = 1000;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public static void  _msgbox_result(int _result) throws Exception{
}
public static String  _btncambiarpublico_click() throws Exception{
anywheresoftware.b4a.objects.collections.Map _map1 = null;
 //BA.debugLineNum = 1047;BA.debugLine="Sub btnCambiarPublico_Click";
 //BA.debugLineNum = 1049;BA.debugLine="If chkPublico.Checked = True Then";
if (mostCurrent._chkpublico.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 1050;BA.debugLine="chkPublico.Checked = False";
mostCurrent._chkpublico.setChecked(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1051;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 1052;BA.debugLine="chkPublico.Text = \"Dato privado\"";
mostCurrent._chkpublico.setText(BA.ObjectToCharSequence("Dato privado"));
 //BA.debugLineNum = 1053;BA.debugLine="btnCambiarPublico.Text = \"Convertir en público\"";
mostCurrent._btncambiarpublico.setText(BA.ObjectToCharSequence("Convertir en público"));
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 1055;BA.debugLine="chkPublico.Text = \"Private report\"";
mostCurrent._chkpublico.setText(BA.ObjectToCharSequence("Private report"));
 //BA.debugLineNum = 1056;BA.debugLine="btnCambiarPublico.Text = \"Make report public\"";
mostCurrent._btncambiarpublico.setText(BA.ObjectToCharSequence("Make report public"));
 };
 //BA.debugLineNum = 1058;BA.debugLine="Dim Map1 As Map";
_map1 = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 1059;BA.debugLine="Map1.Initialize";
_map1.Initialize();
 //BA.debugLineNum = 1060;BA.debugLine="Map1.Put(\"Id\", currentDatoId)";
_map1.Put((Object)("Id"),(Object)(mostCurrent._currentdatoid));
 //BA.debugLineNum = 1061;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"evals\", \"pr";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals","privado",(Object)("si"),_map1);
 //BA.debugLineNum = 1062;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"evals\", \"ev";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals","evalsent",(Object)("no"),_map1);
 //BA.debugLineNum = 1063;BA.debugLine="NewMarcador = False";
_newmarcador = anywheresoftware.b4a.keywords.Common.False;
 }else {
 //BA.debugLineNum = 1065;BA.debugLine="chkPublico.Checked = True";
mostCurrent._chkpublico.setChecked(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1066;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 1067;BA.debugLine="chkPublico.Text = \"Dato público\"";
mostCurrent._chkpublico.setText(BA.ObjectToCharSequence("Dato público"));
 //BA.debugLineNum = 1068;BA.debugLine="btnCambiarPublico.Text = \"Convertir en privado\"";
mostCurrent._btncambiarpublico.setText(BA.ObjectToCharSequence("Convertir en privado"));
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 1070;BA.debugLine="chkPublico.Text = \"Public report\"";
mostCurrent._chkpublico.setText(BA.ObjectToCharSequence("Public report"));
 //BA.debugLineNum = 1071;BA.debugLine="btnCambiarPublico.Text = \"Make report private\"";
mostCurrent._btncambiarpublico.setText(BA.ObjectToCharSequence("Make report private"));
 };
 //BA.debugLineNum = 1073;BA.debugLine="Dim Map1 As Map";
_map1 = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 1074;BA.debugLine="Map1.Initialize";
_map1.Initialize();
 //BA.debugLineNum = 1075;BA.debugLine="Map1.Put(\"Id\", currentDatoId)";
_map1.Put((Object)("Id"),(Object)(mostCurrent._currentdatoid));
 //BA.debugLineNum = 1076;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"evals\", \"pr";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals","privado",(Object)("no"),_map1);
 //BA.debugLineNum = 1077;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"evals\", \"ev";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals","evalsent",(Object)("no"),_map1);
 //BA.debugLineNum = 1078;BA.debugLine="NewMarcador = False";
_newmarcador = anywheresoftware.b4a.keywords.Common.False;
 };
 //BA.debugLineNum = 1080;BA.debugLine="btnEnviar.Visible = True";
mostCurrent._btnenviar.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1081;BA.debugLine="hubocambiosDatos = True";
_hubocambiosdatos = anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 1082;BA.debugLine="End Sub";
return "";
}
public static void  _btncambiarubicacion_click() throws Exception{
ResumableSub_btnCambiarUbicacion_Click rsub = new ResumableSub_btnCambiarUbicacion_Click(null);
rsub.resume(processBA, null);
}
public static class ResumableSub_btnCambiarUbicacion_Click extends BA.ResumableSub {
public ResumableSub_btnCambiarUbicacion_Click(ilpla.appear.frmdatosanteriores parent) {
this.parent = parent;
}
ilpla.appear.frmdatosanteriores parent;
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
 //BA.debugLineNum = 1002;BA.debugLine="If Main.lang = \"es\" Then";
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
 //BA.debugLineNum = 1003;BA.debugLine="Msgbox2Async(\"Desea cambiar la ubicación de este";
anywheresoftware.b4a.keywords.Common.Msgbox2Async(BA.ObjectToCharSequence("Desea cambiar la ubicación de este dato?"),BA.ObjectToCharSequence("Cambiar ubicación"),"Si","No","",(anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper(), (android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null)),processBA,anywheresoftware.b4a.keywords.Common.False);
 if (true) break;

case 5:
//C
this.state = 6;
 //BA.debugLineNum = 1005;BA.debugLine="Msgbox2Async(\"Do you want to change the location";
anywheresoftware.b4a.keywords.Common.Msgbox2Async(BA.ObjectToCharSequence("Do you want to change the location for this report?"),BA.ObjectToCharSequence("Change location"),"Yes","No","",(anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper(), (android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null)),processBA,anywheresoftware.b4a.keywords.Common.False);
 if (true) break;

case 6:
//C
this.state = 7;
;
 //BA.debugLineNum = 1007;BA.debugLine="Wait For Msgbox_Result (Result As Int)";
anywheresoftware.b4a.keywords.Common.WaitFor("msgbox_result", processBA, this, null);
this.state = 13;
return;
case 13:
//C
this.state = 7;
_result = (Integer) result[0];
;
 //BA.debugLineNum = 1008;BA.debugLine="If Result = DialogResponse.POSITIVE Then";
if (true) break;

case 7:
//if
this.state = 12;
if (_result==anywheresoftware.b4a.keywords.Common.DialogResponse.POSITIVE) { 
this.state = 9;
}else {
this.state = 11;
}if (true) break;

case 9:
//C
this.state = 12;
 //BA.debugLineNum = 1009;BA.debugLine="nuevalatlng = \"\"";
parent._nuevalatlng = "";
 //BA.debugLineNum = 1010;BA.debugLine="frmLocalizacion.origen = \"cambio\"";
parent.mostCurrent._frmlocalizacion._origen /*String*/  = "cambio";
 //BA.debugLineNum = 1011;BA.debugLine="hubocambiosDatos = True";
parent._hubocambiosdatos = anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 1012;BA.debugLine="StartActivity(frmLocalizacion)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(parent.mostCurrent._frmlocalizacion.getObject()));
 if (true) break;

case 11:
//C
this.state = 12;
 //BA.debugLineNum = 1015;BA.debugLine="Return";
if (true) return ;
 if (true) break;

case 12:
//C
this.state = -1;
;
 //BA.debugLineNum = 1017;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public static String  _btncerrar_click() throws Exception{
 //BA.debugLineNum = 255;BA.debugLine="Sub btnCerrar_Click";
 //BA.debugLineNum = 256;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 257;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 258;BA.debugLine="End Sub";
return "";
}
public static String  _btnenviar_click() throws Exception{
 //BA.debugLineNum = 1101;BA.debugLine="Sub btnEnviar_Click";
 //BA.debugLineNum = 1103;BA.debugLine="If Main.modooffline = True Then";
if (mostCurrent._main._modooffline /*boolean*/ ==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 1104;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 1105;BA.debugLine="MsgboxAsync(\"Estas trabajando en modo fuera de";
anywheresoftware.b4a.keywords.Common.MsgboxAsync(BA.ObjectToCharSequence("Estas trabajando en modo fuera de línea. Conectate a internet e inicia sesión para cambiar el estado de los datos"),BA.ObjectToCharSequence("Modo fuera de línea"),processBA);
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 1107;BA.debugLine="MsgboxAsync(\"You are working offline. Connect t";
anywheresoftware.b4a.keywords.Common.MsgboxAsync(BA.ObjectToCharSequence("You are working offline. Connect to the internet and log in to change the reports."),BA.ObjectToCharSequence("Offline mode"),processBA);
 };
 //BA.debugLineNum = 1109;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 1112;BA.debugLine="Log(\"Chequeando internet\")";
anywheresoftware.b4a.keywords.Common.LogImpl("214549003","Chequeando internet",0);
 //BA.debugLineNum = 1113;BA.debugLine="CheckInternet";
_checkinternet();
 //BA.debugLineNum = 1115;BA.debugLine="End Sub";
return "";
}
public static String  _cambiarubicacion() throws Exception{
String _newlat = "";
String _newlng = "";
String[] _arr = null;
anywheresoftware.b4a.objects.collections.Map _map1 = null;
 //BA.debugLineNum = 1018;BA.debugLine="Sub CambiarUbicacion";
 //BA.debugLineNum = 1019;BA.debugLine="Dim newlat As String";
_newlat = "";
 //BA.debugLineNum = 1020;BA.debugLine="Dim newlng As String";
_newlng = "";
 //BA.debugLineNum = 1022;BA.debugLine="If nuevalatlng = \"\" Then";
if ((_nuevalatlng).equals("")) { 
 //BA.debugLineNum = 1023;BA.debugLine="Return";
if (true) return "";
 }else {
 //BA.debugLineNum = 1025;BA.debugLine="Dim arr() As String = Regex.Split(\"_\", nuevalatl";
_arr = anywheresoftware.b4a.keywords.Common.Regex.Split("_",_nuevalatlng);
 //BA.debugLineNum = 1026;BA.debugLine="newlat = arr(0)";
_newlat = _arr[(int) (0)];
 //BA.debugLineNum = 1027;BA.debugLine="newlng = arr(1)";
_newlng = _arr[(int) (1)];
 };
 //BA.debugLineNum = 1031;BA.debugLine="Dim Map1 As Map";
_map1 = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 1032;BA.debugLine="Map1.Initialize";
_map1.Initialize();
 //BA.debugLineNum = 1033;BA.debugLine="Map1.Put(\"Id\", currentDatoId)";
_map1.Put((Object)("Id"),(Object)(mostCurrent._currentdatoid));
 //BA.debugLineNum = 1034;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"evals\", \"dec";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals","decimalLatitude",(Object)(_newlat),_map1);
 //BA.debugLineNum = 1035;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"evals\", \"dec";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals","decimalLongitude",(Object)(_newlng),_map1);
 //BA.debugLineNum = 1036;BA.debugLine="lblLat.text = newlat";
mostCurrent._lbllat.setText(BA.ObjectToCharSequence(_newlat));
 //BA.debugLineNum = 1037;BA.debugLine="lblLng.text = newlng";
mostCurrent._lbllng.setText(BA.ObjectToCharSequence(_newlng));
 //BA.debugLineNum = 1038;BA.debugLine="nuevalatlng = \"\"";
_nuevalatlng = "";
 //BA.debugLineNum = 1040;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"evals\", \"eva";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals","evalsent",(Object)("no"),_map1);
 //BA.debugLineNum = 1041;BA.debugLine="btnEnviar.Visible = True";
mostCurrent._btnenviar.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1042;BA.debugLine="NewMarcador = False";
_newmarcador = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 1043;BA.debugLine="End Sub";
return "";
}
public static String  _cc_result(boolean _success,String _dir,String _filename) throws Exception{
anywheresoftware.b4a.objects.collections.Map _map1 = null;
String _fotonombredestino = "";
 //BA.debugLineNum = 917;BA.debugLine="Sub CC_Result (Success As Boolean, Dir As String,";
 //BA.debugLineNum = 918;BA.debugLine="If Success = True Then";
if (_success==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 921;BA.debugLine="Dim Map1 As Map";
_map1 = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 922;BA.debugLine="Map1.Initialize";
_map1.Initialize();
 //BA.debugLineNum = 923;BA.debugLine="Map1.Put(\"Id\", IdProyectoEnviar)";
_map1.Put((Object)("Id"),(Object)(mostCurrent._idproyectoenviar));
 //BA.debugLineNum = 924;BA.debugLine="DateTime.DateFormat = \"dd-MM-yyyy\"";
anywheresoftware.b4a.keywords.Common.DateTime.setDateFormat("dd-MM-yyyy");
 //BA.debugLineNum = 925;BA.debugLine="Dim fotoNombreDestino As String";
_fotonombredestino = "";
 //BA.debugLineNum = 926;BA.debugLine="hubocambiosFotos = True";
_hubocambiosfotos = anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 927;BA.debugLine="btnEnviar.Visible = True";
mostCurrent._btnenviar.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 928;BA.debugLine="If fotoAdjuntar = \"foto1\" Then";
if ((mostCurrent._fotoadjuntar).equals("foto1")) { 
 //BA.debugLineNum = 929;BA.debugLine="fotoNombreDestino = Form_Main.fullidcurrentproj";
_fotonombredestino = mostCurrent._form_main._fullidcurrentproject /*String*/ +"_1";
 //BA.debugLineNum = 930;BA.debugLine="File.Copy(Dir, FileName, File.DirRootExternal &";
anywheresoftware.b4a.keywords.Common.File.Copy(_dir,_filename,anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/PreserVamos/",_fotonombredestino+".jpg");
 //BA.debugLineNum = 931;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"evals\", \"f";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals","foto1",(Object)(_fotonombredestino),_map1);
 //BA.debugLineNum = 932;BA.debugLine="foto1view.Bitmap= LoadBitmapSample(File.DirRoot";
mostCurrent._foto1view.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/PreserVamos/",_fotonombredestino+".jpg",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 }else if((mostCurrent._fotoadjuntar).equals("foto2")) { 
 //BA.debugLineNum = 935;BA.debugLine="fotoNombreDestino = Form_Main.fullidcurrentproj";
_fotonombredestino = mostCurrent._form_main._fullidcurrentproject /*String*/ +"_2";
 //BA.debugLineNum = 936;BA.debugLine="File.Copy(Dir, FileName, File.DirRootExternal &";
anywheresoftware.b4a.keywords.Common.File.Copy(_dir,_filename,anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/PreserVamos/",_fotonombredestino+".jpg");
 //BA.debugLineNum = 937;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"evals\", \"f";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals","foto2",(Object)(_fotonombredestino),_map1);
 //BA.debugLineNum = 938;BA.debugLine="foto2view.Bitmap= LoadBitmapSample(File.DirRoot";
mostCurrent._foto2view.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/PreserVamos/",_fotonombredestino+".jpg",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 }else if((mostCurrent._fotoadjuntar).equals("foto3")) { 
 //BA.debugLineNum = 940;BA.debugLine="fotoNombreDestino = Form_Main.fullidcurrentproj";
_fotonombredestino = mostCurrent._form_main._fullidcurrentproject /*String*/ +"_3";
 //BA.debugLineNum = 941;BA.debugLine="File.Copy(Dir, FileName, File.DirRootExternal &";
anywheresoftware.b4a.keywords.Common.File.Copy(_dir,_filename,anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/PreserVamos/",_fotonombredestino+".jpg");
 //BA.debugLineNum = 942;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"evals\", \"f";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals","foto3",(Object)(_fotonombredestino),_map1);
 //BA.debugLineNum = 943;BA.debugLine="foto3view.Bitmap= LoadBitmapSample(File.DirRoot";
mostCurrent._foto3view.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/PreserVamos/",_fotonombredestino+".jpg",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 }else if((mostCurrent._fotoadjuntar).equals("foto4")) { 
 //BA.debugLineNum = 945;BA.debugLine="fotoNombreDestino = Form_Main.fullidcurrentproj";
_fotonombredestino = mostCurrent._form_main._fullidcurrentproject /*String*/ +"_4";
 //BA.debugLineNum = 946;BA.debugLine="File.Copy(Dir, FileName, File.DirRootExternal &";
anywheresoftware.b4a.keywords.Common.File.Copy(_dir,_filename,anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/PreserVamos/",_fotonombredestino+".jpg");
 //BA.debugLineNum = 947;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"evals\", \"f";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals","foto4",(Object)(_fotonombredestino),_map1);
 //BA.debugLineNum = 948;BA.debugLine="foto4view.Bitmap= LoadBitmapSample(File.DirRoot";
mostCurrent._foto4view.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/PreserVamos/",_fotonombredestino+".jpg",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 }else if((mostCurrent._fotoadjuntar).equals("foto5")) { 
 //BA.debugLineNum = 950;BA.debugLine="fotoNombreDestino = Form_Main.fullidcurrentproj";
_fotonombredestino = mostCurrent._form_main._fullidcurrentproject /*String*/ +"_5";
 //BA.debugLineNum = 951;BA.debugLine="File.Copy(Dir, FileName, File.DirRootExternal &";
anywheresoftware.b4a.keywords.Common.File.Copy(_dir,_filename,anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/PreserVamos/",_fotonombredestino+".jpg");
 //BA.debugLineNum = 952;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"evals\", \"f";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals","foto5",(Object)(_fotonombredestino),_map1);
 //BA.debugLineNum = 953;BA.debugLine="foto5view.Bitmap= LoadBitmapSample(File.DirRoot";
mostCurrent._foto5view.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/PreserVamos/",_fotonombredestino+".jpg",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 };
 };
 //BA.debugLineNum = 958;BA.debugLine="End Sub";
return "";
}
public static String  _checkinternet() throws Exception{
ilpla.appear.downloadservice._downloaddata _dd = null;
 //BA.debugLineNum = 1120;BA.debugLine="Sub CheckInternet";
 //BA.debugLineNum = 1121;BA.debugLine="Dim dd As DownloadData";
_dd = new ilpla.appear.downloadservice._downloaddata();
 //BA.debugLineNum = 1122;BA.debugLine="dd.url = Main.serverPath &  \"/\" & Main.serverConn";
_dd.url /*String*/  = mostCurrent._main._serverpath /*String*/ +"/"+mostCurrent._main._serverconnectionfolder /*String*/ +"/connecttest.php";
 //BA.debugLineNum = 1123;BA.debugLine="dd.EventName = \"TestInternet\"";
_dd.EventName /*String*/  = "TestInternet";
 //BA.debugLineNum = 1124;BA.debugLine="dd.Target = Me";
_dd.Target /*Object*/  = frmdatosanteriores.getObject();
 //BA.debugLineNum = 1125;BA.debugLine="CallSubDelayed2(DownloadService, \"StartDownload\",";
anywheresoftware.b4a.keywords.Common.CallSubDelayed2(processBA,(Object)(mostCurrent._downloadservice.getObject()),"StartDownload",(Object)(_dd));
 //BA.debugLineNum = 1126;BA.debugLine="End Sub";
return "";
}
public static String  _enviardatos(String _proyectonumero) throws Exception{
String _username = "";
String _dateandtime = "";
String _nombresitio = "";
String _tiporio = "";
String _lat = "";
String _lng = "";
String _gpsdetect = "";
String _wifidetect = "";
String _mapadetect = "";
String _valorcalidad = "";
String _valorns = "";
String _valorind1 = "";
String _valorind2 = "";
String _valorind3 = "";
String _valorind4 = "";
String _valorind5 = "";
String _valorind6 = "";
String _valorind7 = "";
String _valorind8 = "";
String _valorind9 = "";
String _valorind10 = "";
String _valorind11 = "";
String _valorind12 = "";
String _valorind13 = "";
String _valorind14 = "";
String _valorind15 = "";
String _valorind16 = "";
String _valorind17 = "";
String _valorind18 = "";
String _valorind19 = "";
String _notas = "";
String _bingo = "";
String _terminado = "";
String _privado = "";
String _estadovalidacion = "";
String _deviceid = "";
String _serveridupdate = "";
anywheresoftware.b4a.objects.collections.Map _datosmap = null;
int _conf = 0;
String _dirweb = "";
ilpla.appear.downloadservice._downloaddata _dd = null;
 //BA.debugLineNum = 1154;BA.debugLine="Sub EnviarDatos(proyectonumero As String)";
 //BA.debugLineNum = 1157;BA.debugLine="Dim username,dateandtime,nombresitio,tiporio,lat,";
_username = "";
_dateandtime = "";
_nombresitio = "";
_tiporio = "";
_lat = "";
_lng = "";
_gpsdetect = "";
_wifidetect = "";
_mapadetect = "";
 //BA.debugLineNum = 1158;BA.debugLine="Dim valorcalidad, valorNS, valorind1,valorind2,va";
_valorcalidad = "";
_valorns = "";
_valorind1 = "";
_valorind2 = "";
_valorind3 = "";
_valorind4 = "";
_valorind5 = "";
_valorind6 = "";
_valorind7 = "";
_valorind8 = "";
_valorind9 = "";
_valorind10 = "";
_valorind11 = "";
_valorind12 = "";
_valorind13 = "";
_valorind14 = "";
_valorind15 = "";
_valorind16 = "";
_valorind17 = "";
_valorind18 = "";
_valorind19 = "";
 //BA.debugLineNum = 1159;BA.debugLine="Dim notas,bingo As String";
_notas = "";
_bingo = "";
 //BA.debugLineNum = 1160;BA.debugLine="Dim terminado, privado,estadovalidacion, deviceID";
_terminado = "";
_privado = "";
_estadovalidacion = "";
_deviceid = "";
_serveridupdate = "";
 //BA.debugLineNum = 1162;BA.debugLine="Dim datosMap As Map";
_datosmap = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 1163;BA.debugLine="datosMap.Initialize";
_datosmap.Initialize();
 //BA.debugLineNum = 1164;BA.debugLine="datosMap = DBUtils.ExecuteMap(Starter.sqlDB, \"SEL";
_datosmap = mostCurrent._dbutils._executemap /*anywheresoftware.b4a.objects.collections.Map*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"SELECT * FROM evals WHERE Id=?",new String[]{_proyectonumero});
 //BA.debugLineNum = 1166;BA.debugLine="If datosMap = Null Or datosMap.IsInitialized = Fa";
if (_datosmap== null || _datosmap.IsInitialized()==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 1167;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 1168;BA.debugLine="ToastMessageShow(\"Error cargando el análisis\",";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Error cargando el análisis"),anywheresoftware.b4a.keywords.Common.False);
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 1170;BA.debugLine="ToastMessageShow(\"Error loading the report\", Fa";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Error loading the report"),anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 1172;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 //BA.debugLineNum = 1173;BA.debugLine="Return";
if (true) return "";
 }else {
 //BA.debugLineNum = 1175;BA.debugLine="username = datosMap.Get(\"usuario\")";
_username = BA.ObjectToString(_datosmap.Get((Object)("usuario")));
 //BA.debugLineNum = 1176;BA.debugLine="dateandtime = datosMap.Get(\"georeferenceddate\")";
_dateandtime = BA.ObjectToString(_datosmap.Get((Object)("georeferenceddate")));
 //BA.debugLineNum = 1177;BA.debugLine="nombresitio = datosMap.Get(\"nombresitio\")";
_nombresitio = BA.ObjectToString(_datosmap.Get((Object)("nombresitio")));
 //BA.debugLineNum = 1178;BA.debugLine="tiporio = datosMap.Get(\"tiporio\")";
_tiporio = BA.ObjectToString(_datosmap.Get((Object)("tiporio")));
 //BA.debugLineNum = 1179;BA.debugLine="lat = datosMap.Get(\"decimallatitude\")";
_lat = BA.ObjectToString(_datosmap.Get((Object)("decimallatitude")));
 //BA.debugLineNum = 1180;BA.debugLine="lng = datosMap.Get(\"decimallongitude\")";
_lng = BA.ObjectToString(_datosmap.Get((Object)("decimallongitude")));
 //BA.debugLineNum = 1181;BA.debugLine="gpsdetect = datosMap.Get(\"gpsdetect\")";
_gpsdetect = BA.ObjectToString(_datosmap.Get((Object)("gpsdetect")));
 //BA.debugLineNum = 1182;BA.debugLine="wifidetect = datosMap.Get(\"wifidetect\")";
_wifidetect = BA.ObjectToString(_datosmap.Get((Object)("wifidetect")));
 //BA.debugLineNum = 1183;BA.debugLine="mapadetect = datosMap.Get(\"mapadetect\")";
_mapadetect = BA.ObjectToString(_datosmap.Get((Object)("mapadetect")));
 //BA.debugLineNum = 1184;BA.debugLine="valorcalidad = datosMap.Get(\"valorcalidad\")";
_valorcalidad = BA.ObjectToString(_datosmap.Get((Object)("valorcalidad")));
 //BA.debugLineNum = 1185;BA.debugLine="valorNS = datosMap.Get(\"valorind20\")";
_valorns = BA.ObjectToString(_datosmap.Get((Object)("valorind20")));
 //BA.debugLineNum = 1186;BA.debugLine="valorind1 = datosMap.Get(\"valorind1\")";
_valorind1 = BA.ObjectToString(_datosmap.Get((Object)("valorind1")));
 //BA.debugLineNum = 1187;BA.debugLine="valorind2 = datosMap.Get(\"valorind2\")";
_valorind2 = BA.ObjectToString(_datosmap.Get((Object)("valorind2")));
 //BA.debugLineNum = 1188;BA.debugLine="valorind3 = datosMap.Get(\"valorind3\")";
_valorind3 = BA.ObjectToString(_datosmap.Get((Object)("valorind3")));
 //BA.debugLineNum = 1189;BA.debugLine="valorind4 = datosMap.Get(\"valorind4\")";
_valorind4 = BA.ObjectToString(_datosmap.Get((Object)("valorind4")));
 //BA.debugLineNum = 1190;BA.debugLine="valorind5 = datosMap.Get(\"valorind5\")";
_valorind5 = BA.ObjectToString(_datosmap.Get((Object)("valorind5")));
 //BA.debugLineNum = 1191;BA.debugLine="valorind6 = datosMap.Get(\"valorind6\")";
_valorind6 = BA.ObjectToString(_datosmap.Get((Object)("valorind6")));
 //BA.debugLineNum = 1192;BA.debugLine="valorind7 = datosMap.Get(\"valorind7\")";
_valorind7 = BA.ObjectToString(_datosmap.Get((Object)("valorind7")));
 //BA.debugLineNum = 1193;BA.debugLine="valorind8 = datosMap.Get(\"valorind8\")";
_valorind8 = BA.ObjectToString(_datosmap.Get((Object)("valorind8")));
 //BA.debugLineNum = 1194;BA.debugLine="valorind9 = datosMap.Get(\"valorind9\")";
_valorind9 = BA.ObjectToString(_datosmap.Get((Object)("valorind9")));
 //BA.debugLineNum = 1195;BA.debugLine="valorind10 = datosMap.Get(\"valorind10\")";
_valorind10 = BA.ObjectToString(_datosmap.Get((Object)("valorind10")));
 //BA.debugLineNum = 1196;BA.debugLine="valorind11 = datosMap.Get(\"valorind11\")";
_valorind11 = BA.ObjectToString(_datosmap.Get((Object)("valorind11")));
 //BA.debugLineNum = 1197;BA.debugLine="valorind12 = datosMap.Get(\"valorind12\")";
_valorind12 = BA.ObjectToString(_datosmap.Get((Object)("valorind12")));
 //BA.debugLineNum = 1198;BA.debugLine="valorind13 = datosMap.Get(\"valorind13\")";
_valorind13 = BA.ObjectToString(_datosmap.Get((Object)("valorind13")));
 //BA.debugLineNum = 1199;BA.debugLine="valorind14 = datosMap.Get(\"valorind14\")";
_valorind14 = BA.ObjectToString(_datosmap.Get((Object)("valorind14")));
 //BA.debugLineNum = 1200;BA.debugLine="valorind15 = datosMap.Get(\"valorind15\")";
_valorind15 = BA.ObjectToString(_datosmap.Get((Object)("valorind15")));
 //BA.debugLineNum = 1201;BA.debugLine="valorind16 = datosMap.Get(\"valorind16\")";
_valorind16 = BA.ObjectToString(_datosmap.Get((Object)("valorind16")));
 //BA.debugLineNum = 1202;BA.debugLine="valorind17 = datosMap.Get(\"valorind17\")";
_valorind17 = BA.ObjectToString(_datosmap.Get((Object)("valorind17")));
 //BA.debugLineNum = 1203;BA.debugLine="valorind18 = datosMap.Get(\"valorind18\")";
_valorind18 = BA.ObjectToString(_datosmap.Get((Object)("valorind18")));
 //BA.debugLineNum = 1204;BA.debugLine="valorind19 = datosMap.Get(\"valorind19\")";
_valorind19 = BA.ObjectToString(_datosmap.Get((Object)("valorind19")));
 //BA.debugLineNum = 1205;BA.debugLine="notas = datosMap.Get(\"notas\")";
_notas = BA.ObjectToString(_datosmap.Get((Object)("notas")));
 //BA.debugLineNum = 1206;BA.debugLine="bingo = datosMap.Get(\"bingo\")";
_bingo = BA.ObjectToString(_datosmap.Get((Object)("bingo")));
 //BA.debugLineNum = 1207;BA.debugLine="foto1 = datosMap.Get(\"foto1\")";
_foto1 = BA.ObjectToString(_datosmap.Get((Object)("foto1")));
 //BA.debugLineNum = 1208;BA.debugLine="foto2 = datosMap.Get(\"foto2\")";
_foto2 = BA.ObjectToString(_datosmap.Get((Object)("foto2")));
 //BA.debugLineNum = 1209;BA.debugLine="foto3 = datosMap.Get(\"foto3\")";
_foto3 = BA.ObjectToString(_datosmap.Get((Object)("foto3")));
 //BA.debugLineNum = 1210;BA.debugLine="foto4 = datosMap.Get(\"foto4\")";
_foto4 = BA.ObjectToString(_datosmap.Get((Object)("foto4")));
 //BA.debugLineNum = 1211;BA.debugLine="foto5 = datosMap.Get(\"foto5\")";
_foto5 = BA.ObjectToString(_datosmap.Get((Object)("foto5")));
 //BA.debugLineNum = 1212;BA.debugLine="terminado = datosMap.Get(\"terminado\")";
_terminado = BA.ObjectToString(_datosmap.Get((Object)("terminado")));
 //BA.debugLineNum = 1213;BA.debugLine="privado = datosMap.Get(\"privado\")";
_privado = BA.ObjectToString(_datosmap.Get((Object)("privado")));
 //BA.debugLineNum = 1215;BA.debugLine="If privado = Null Or privado = \"null\" Or privado";
if (_privado== null || (_privado).equals("null") || (_privado).equals("")) { 
 //BA.debugLineNum = 1216;BA.debugLine="privado = \"no\"";
_privado = "no";
 };
 //BA.debugLineNum = 1218;BA.debugLine="estadovalidacion = datosMap.Get(\"estadovalidacio";
_estadovalidacion = BA.ObjectToString(_datosmap.Get((Object)("estadovalidacion")));
 //BA.debugLineNum = 1219;BA.debugLine="If estadovalidacion = \"null\" Then";
if ((_estadovalidacion).equals("null")) { 
 //BA.debugLineNum = 1220;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 1221;BA.debugLine="estadovalidacion = \"No Verificado\"";
_estadovalidacion = "No Verificado";
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 1223;BA.debugLine="estadovalidacion = \"Unverified\"";
_estadovalidacion = "Unverified";
 };
 };
 //BA.debugLineNum = 1227;BA.debugLine="serverIdupdate = datosMap.Get(\"serverid\")";
_serveridupdate = BA.ObjectToString(_datosmap.Get((Object)("serverid")));
 //BA.debugLineNum = 1228;BA.debugLine="If NewMarcador = False And serverIdupdate = Null";
if (_newmarcador==anywheresoftware.b4a.keywords.Common.False && _serveridupdate== null) { 
 //BA.debugLineNum = 1229;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 1230;BA.debugLine="ToastMessageShow(\"No se puede actualizar el da";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("No se puede actualizar el dato"),anywheresoftware.b4a.keywords.Common.False);
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 1232;BA.debugLine="ToastMessageShow(\"The report cannot be updated";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("The report cannot be updated"),anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 1235;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 1237;BA.debugLine="If serverIdupdate <> \"null\" Or serverIdupdate <>";
if ((_serveridupdate).equals("null") == false || (_serveridupdate).equals("") == false || (_serveridupdate).equals("")) { 
 //BA.debugLineNum = 1238;BA.debugLine="NewMarcador = False";
_newmarcador = anywheresoftware.b4a.keywords.Common.False;
 };
 };
 //BA.debugLineNum = 1243;BA.debugLine="If terminado <> \"si\" Then";
if ((_terminado).equals("si") == false) { 
 //BA.debugLineNum = 1244;BA.debugLine="Dim conf As Int";
_conf = 0;
 //BA.debugLineNum = 1246;BA.debugLine="Select conf";
switch (BA.switchObjectToInt(_conf,anywheresoftware.b4a.keywords.Common.DialogResponse.NEGATIVE,anywheresoftware.b4a.keywords.Common.DialogResponse.POSITIVE)) {
case 0: {
 //BA.debugLineNum = 1248;BA.debugLine="Return";
if (true) return "";
 break; }
case 1: {
 //BA.debugLineNum = 1250;BA.debugLine="Return";
if (true) return "";
 break; }
}
;
 };
 //BA.debugLineNum = 1254;BA.debugLine="Dim dirWeb As String";
_dirweb = "";
 //BA.debugLineNum = 1255;BA.debugLine="If NewMarcador = True Then";
if (_newmarcador==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 1256;BA.debugLine="dirWeb = Main.serverPath &  \"/\" & Main.serverCon";
_dirweb = mostCurrent._main._serverpath /*String*/ +"/"+mostCurrent._main._serverconnectionfolder /*String*/ +"/addpuntomapa.php";
 }else {
 //BA.debugLineNum = 1258;BA.debugLine="dirWeb = Main.serverPath &  \"/\" & Main.serverCon";
_dirweb = mostCurrent._main._serverpath /*String*/ +"/"+mostCurrent._main._serverconnectionfolder /*String*/ +"/updatepuntomapa.php";
 };
 //BA.debugLineNum = 1261;BA.debugLine="Log(\"Comienza envio de datos\")";
anywheresoftware.b4a.keywords.Common.LogImpl("214745707","Comienza envio de datos",0);
 //BA.debugLineNum = 1263;BA.debugLine="Dim dd As DownloadData";
_dd = new ilpla.appear.downloadservice._downloaddata();
 //BA.debugLineNum = 1265;BA.debugLine="dd.url = dirWeb & \"?\" & _ 	\"username=\" & username";
_dd.url /*String*/  = _dirweb+"?"+"username="+_username+"&"+"deviceID="+mostCurrent._main._deviceid /*String*/ +"&"+"dateandtime="+_dateandtime+"&"+"nombresitio="+_nombresitio+"&"+"lat="+_lat+"&"+"lng="+_lng+"&"+"tiporio="+_tiporio+"&"+"indice="+_valorcalidad+"&"+"precision="+_valorns+"&"+"valorind1="+_valorind1+"&"+"valorind2="+_valorind2+"&"+"valorind3="+_valorind3+"&"+"valorind4="+_valorind4+"&"+"valorind5="+_valorind5+"&"+"valorind6="+_valorind6+"&"+"valorind7="+_valorind7+"&"+"valorind8="+_valorind8+"&"+"valorind9="+_valorind9+"&"+"valorind10="+_valorind10+"&"+"valorind11="+_valorind11+"&"+"valorind12="+_valorind12+"&"+"valorind13="+_valorind13+"&"+"valorind14="+_valorind14+"&"+"valorind15="+_valorind15+"&"+"valorind16="+_valorind16+"&"+"valorind17="+_valorind17+"&"+"valorind18="+_valorind18+"&"+"valorind19="+_valorind19+"&"+"foto1path="+_foto1+"&"+"foto2path="+_foto2+"&"+"foto3path="+_foto3+"&"+"foto4path="+_foto4+"&"+"foto5path="+_foto5+"terminado="+_terminado+"&"+"verificado="+_estadovalidacion+"&"+"bingo="+_bingo+"&"+"notas="+_notas+"&"+"mapadetect="+_mapadetect+"&"+"wifidetect="+_wifidetect+"&"+"gpsdetect="+_gpsdetect;
 //BA.debugLineNum = 1302;BA.debugLine="dd.EventName = \"EnviarDatos\"";
_dd.EventName /*String*/  = "EnviarDatos";
 //BA.debugLineNum = 1303;BA.debugLine="dd.Target = Me";
_dd.Target /*Object*/  = frmdatosanteriores.getObject();
 //BA.debugLineNum = 1304;BA.debugLine="CallSubDelayed2(DownloadService, \"StartDownload\",";
anywheresoftware.b4a.keywords.Common.CallSubDelayed2(processBA,(Object)(mostCurrent._downloadservice.getObject()),"StartDownload",(Object)(_dd));
 //BA.debugLineNum = 1322;BA.debugLine="End Sub";
return "";
}
public static String  _enviardatos_complete(ilpla.appear.httpjob _job) throws Exception{
String _ret = "";
String _act = "";
anywheresoftware.b4a.objects.collections.JSONParser _parser = null;
anywheresoftware.b4a.objects.collections.Map _nd = null;
String _serverid = "";
anywheresoftware.b4a.objects.collections.Map _map1 = null;
 //BA.debugLineNum = 1323;BA.debugLine="Sub EnviarDatos_Complete(Job As HttpJob)";
 //BA.debugLineNum = 1324;BA.debugLine="Log(\"Datos enviados : \" & Job.Success)";
anywheresoftware.b4a.keywords.Common.LogImpl("214811137","Datos enviados : "+BA.ObjectToString(_job._success /*boolean*/ ),0);
 //BA.debugLineNum = 1325;BA.debugLine="If Job.Success = True Then";
if (_job._success /*boolean*/ ==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 1326;BA.debugLine="Dim ret As String";
_ret = "";
 //BA.debugLineNum = 1327;BA.debugLine="Dim act As String";
_act = "";
 //BA.debugLineNum = 1328;BA.debugLine="ret = Job.GetString";
_ret = _job._getstring /*String*/ ();
 //BA.debugLineNum = 1329;BA.debugLine="Dim parser As JSONParser";
_parser = new anywheresoftware.b4a.objects.collections.JSONParser();
 //BA.debugLineNum = 1330;BA.debugLine="parser.Initialize(ret)";
_parser.Initialize(_ret);
 //BA.debugLineNum = 1331;BA.debugLine="act = parser.NextValue";
_act = BA.ObjectToString(_parser.NextValue());
 //BA.debugLineNum = 1332;BA.debugLine="Log(act)";
anywheresoftware.b4a.keywords.Common.LogImpl("214811145",_act,0);
 //BA.debugLineNum = 1333;BA.debugLine="If act = \"Not Found\" Then";
if ((_act).equals("Not Found")) { 
 //BA.debugLineNum = 1334;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 1335;BA.debugLine="ToastMessageShow(\"Error en la carga de marcado";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Error en la carga de marcadores"),anywheresoftware.b4a.keywords.Common.True);
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 1337;BA.debugLine="ToastMessageShow(\"Error in loading reports\", T";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Error in loading reports"),anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 1340;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 }else if((_act).equals("Marcadores")) { 
 //BA.debugLineNum = 1342;BA.debugLine="Dim nd As Map";
_nd = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 1343;BA.debugLine="nd = parser.NextObject";
_nd = _parser.NextObject();
 //BA.debugLineNum = 1344;BA.debugLine="Dim serverID As String";
_serverid = "";
 //BA.debugLineNum = 1345;BA.debugLine="serverID = nd.Get(\"serverId\")";
_serverid = BA.ObjectToString(_nd.Get((Object)("serverId")));
 //BA.debugLineNum = 1348;BA.debugLine="Dim Map1 As Map";
_map1 = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 1349;BA.debugLine="Map1.Initialize";
_map1.Initialize();
 //BA.debugLineNum = 1350;BA.debugLine="Map1.Put(\"Id\", IdProyectoEnviar)";
_map1.Put((Object)("Id"),(Object)(mostCurrent._idproyectoenviar));
 //BA.debugLineNum = 1351;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"evals\", \"e";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals","evalsent",(Object)("si"),_map1);
 //BA.debugLineNum = 1352;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"evals\", \"s";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals","serverId",(Object)(_serverid),_map1);
 //BA.debugLineNum = 1353;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 1354;BA.debugLine="ToastMessageShow(\"Datos enviados, enviando fot";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Datos enviados, enviando fotos"),anywheresoftware.b4a.keywords.Common.False);
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 1356;BA.debugLine="ToastMessageShow(\"Report sent, sending photos\"";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Report sent, sending photos"),anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 1359;BA.debugLine="If hubocambiosFotos = True Then";
if (_hubocambiosfotos==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 1360;BA.debugLine="EnviarFotos";
_enviarfotos();
 };
 }else if((_act).equals("MarcadorActualizado")) { 
 //BA.debugLineNum = 1367;BA.debugLine="Dim Map1 As Map";
_map1 = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 1368;BA.debugLine="Map1.Initialize";
_map1.Initialize();
 //BA.debugLineNum = 1369;BA.debugLine="Map1.Put(\"Id\", IdProyectoEnviar)";
_map1.Put((Object)("Id"),(Object)(mostCurrent._idproyectoenviar));
 //BA.debugLineNum = 1370;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"evals\", \"e";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals","evalsent",(Object)("si"),_map1);
 //BA.debugLineNum = 1371;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 };
 //BA.debugLineNum = 1376;BA.debugLine="ToastMessageShow(\"Datos enviados, enviando foto";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Datos enviados, enviando fotos"),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1380;BA.debugLine="If hubocambiosFotos = True Then";
if (_hubocambiosfotos==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 1381;BA.debugLine="EnviarFotos";
_enviarfotos();
 };
 //BA.debugLineNum = 1383;BA.debugLine="Job.Release";
_job._release /*String*/ ();
 };
 }else {
 //BA.debugLineNum = 1386;BA.debugLine="Log(\"envio datos not ok\")";
anywheresoftware.b4a.keywords.Common.LogImpl("214811199","envio datos not ok",0);
 //BA.debugLineNum = 1387;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 1388;BA.debugLine="MsgboxAsync(\"Al parecer hay un problema en nues";
anywheresoftware.b4a.keywords.Common.MsgboxAsync(BA.ObjectToCharSequence("Al parecer hay un problema en nuestros servidores, lo solucionaremos pronto!"),BA.ObjectToCharSequence("Mala mía"),processBA);
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 1390;BA.debugLine="MsgboxAsync(\"There seems to be a problem with o";
anywheresoftware.b4a.keywords.Common.MsgboxAsync(BA.ObjectToCharSequence("There seems to be a problem with our servers, we will solve it soon!"),BA.ObjectToCharSequence("My bad"),processBA);
 };
 };
 //BA.debugLineNum = 1395;BA.debugLine="Job.Release";
_job._release /*String*/ ();
 //BA.debugLineNum = 1396;BA.debugLine="End Sub";
return "";
}
public static String  _enviarfotos() throws Exception{
 //BA.debugLineNum = 1403;BA.debugLine="Sub EnviarFotos";
 //BA.debugLineNum = 1407;BA.debugLine="fondoblanco.Initialize(\"fondoblanco\")";
mostCurrent._fondoblanco.Initialize(mostCurrent.activityBA,"fondoblanco");
 //BA.debugLineNum = 1408;BA.debugLine="fondoblanco.Color = Colors.ARGB(190, 255,255,255)";
mostCurrent._fondoblanco.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (190),(int) (255),(int) (255),(int) (255)));
 //BA.debugLineNum = 1409;BA.debugLine="Activity.AddView(fondoblanco, 0,0, 100%x, 100%y)";
mostCurrent._activity.AddView((android.view.View)(mostCurrent._fondoblanco.getObject()),(int) (0),(int) (0),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA));
 //BA.debugLineNum = 1411;BA.debugLine="If foto1 <> \"null\" Then";
if ((_foto1).equals("null") == false) { 
 //BA.debugLineNum = 1412;BA.debugLine="ProgressBar1.Initialize(\"ProgressBar1\")";
mostCurrent._progressbar1.Initialize(mostCurrent.activityBA,"ProgressBar1");
 //BA.debugLineNum = 1413;BA.debugLine="foto1Prg.Initialize(\"\")";
mostCurrent._foto1prg.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 1414;BA.debugLine="Activity.AddView(ProgressBar1, 50%x, 30%y, 30%x,";
mostCurrent._activity.AddView((android.view.View)(mostCurrent._progressbar1.getObject()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (50),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (30),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (30),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5)));
 //BA.debugLineNum = 1415;BA.debugLine="Activity.AddView(foto1Prg, 15%x, 30%y, 50%x, 20d";
mostCurrent._activity.AddView((android.view.View)(mostCurrent._foto1prg.getObject()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (15),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (30),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (50),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (20)));
 //BA.debugLineNum = 1416;BA.debugLine="foto1Prg.Text = \"Subiendo Foto 1:\"";
mostCurrent._foto1prg.setText(BA.ObjectToCharSequence("Subiendo Foto 1:"));
 //BA.debugLineNum = 1417;BA.debugLine="foto1Prg.TextColor = Colors.Black";
mostCurrent._foto1prg.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 1418;BA.debugLine="foto1Prg.TextSize = 14";
mostCurrent._foto1prg.setTextSize((float) (14));
 //BA.debugLineNum = 1419;BA.debugLine="totalFotos = totalFotos + 1";
_totalfotos = (int) (_totalfotos+1);
 };
 //BA.debugLineNum = 1421;BA.debugLine="If foto2 <>  \"null\" Then";
if ((_foto2).equals("null") == false) { 
 //BA.debugLineNum = 1422;BA.debugLine="ProgressBar2.Initialize(\"ProgressBar2\")";
mostCurrent._progressbar2.Initialize(mostCurrent.activityBA,"ProgressBar2");
 //BA.debugLineNum = 1423;BA.debugLine="foto2Prg.Initialize(\"\")";
mostCurrent._foto2prg.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 1424;BA.debugLine="Activity.AddView(ProgressBar2, 50%x, 40%y, 30%x,";
mostCurrent._activity.AddView((android.view.View)(mostCurrent._progressbar2.getObject()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (50),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (40),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (30),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5)));
 //BA.debugLineNum = 1425;BA.debugLine="Activity.AddView(foto2Prg, 15%x, 40%y, 50%x, 20d";
mostCurrent._activity.AddView((android.view.View)(mostCurrent._foto2prg.getObject()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (15),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (40),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (50),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (20)));
 //BA.debugLineNum = 1426;BA.debugLine="foto2Prg.Text = \"Subiendo Foto 2:\"";
mostCurrent._foto2prg.setText(BA.ObjectToCharSequence("Subiendo Foto 2:"));
 //BA.debugLineNum = 1427;BA.debugLine="foto2Prg.TextColor = Colors.Black";
mostCurrent._foto2prg.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 1428;BA.debugLine="foto2Prg.TextSize = 14";
mostCurrent._foto2prg.setTextSize((float) (14));
 //BA.debugLineNum = 1429;BA.debugLine="totalFotos = totalFotos + 1";
_totalfotos = (int) (_totalfotos+1);
 };
 //BA.debugLineNum = 1431;BA.debugLine="If foto3 <> \"null\" Then";
if ((_foto3).equals("null") == false) { 
 //BA.debugLineNum = 1432;BA.debugLine="ProgressBar3.Initialize(\"ProgressBar3\")";
mostCurrent._progressbar3.Initialize(mostCurrent.activityBA,"ProgressBar3");
 //BA.debugLineNum = 1433;BA.debugLine="foto3Prg.Initialize(\"\")";
mostCurrent._foto3prg.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 1434;BA.debugLine="Activity.AddView(ProgressBar3, 50%x, 30%y, 30%x,";
mostCurrent._activity.AddView((android.view.View)(mostCurrent._progressbar3.getObject()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (50),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (30),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (30),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5)));
 //BA.debugLineNum = 1435;BA.debugLine="Activity.AddView(foto3Prg, 15%x, 50%y, 50%x, 20d";
mostCurrent._activity.AddView((android.view.View)(mostCurrent._foto3prg.getObject()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (15),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (50),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (50),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (20)));
 //BA.debugLineNum = 1436;BA.debugLine="foto3Prg.Text = \"Subiendo Foto 3:\"";
mostCurrent._foto3prg.setText(BA.ObjectToCharSequence("Subiendo Foto 3:"));
 //BA.debugLineNum = 1437;BA.debugLine="foto3Prg.TextColor = Colors.Black";
mostCurrent._foto3prg.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 1438;BA.debugLine="foto3Prg.TextSize = 14";
mostCurrent._foto3prg.setTextSize((float) (14));
 //BA.debugLineNum = 1439;BA.debugLine="totalFotos = totalFotos + 1";
_totalfotos = (int) (_totalfotos+1);
 };
 //BA.debugLineNum = 1441;BA.debugLine="If foto4 <> \"null\" Then";
if ((_foto4).equals("null") == false) { 
 //BA.debugLineNum = 1442;BA.debugLine="ProgressBar4.Initialize(\"ProgressBar4\")";
mostCurrent._progressbar4.Initialize(mostCurrent.activityBA,"ProgressBar4");
 //BA.debugLineNum = 1443;BA.debugLine="foto4Prg.Initialize(\"\")";
mostCurrent._foto4prg.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 1444;BA.debugLine="Activity.AddView(ProgressBar4, 50%x, 30%y, 30%x,";
mostCurrent._activity.AddView((android.view.View)(mostCurrent._progressbar4.getObject()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (50),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (30),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (30),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5)));
 //BA.debugLineNum = 1445;BA.debugLine="Activity.AddView(foto4Prg, 15%x, 60%y, 50%x, 20d";
mostCurrent._activity.AddView((android.view.View)(mostCurrent._foto4prg.getObject()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (15),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (60),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (50),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (20)));
 //BA.debugLineNum = 1446;BA.debugLine="foto4Prg.Text = \"Subiendo Foto 4:\"";
mostCurrent._foto4prg.setText(BA.ObjectToCharSequence("Subiendo Foto 4:"));
 //BA.debugLineNum = 1447;BA.debugLine="foto4Prg.TextColor = Colors.Black";
mostCurrent._foto4prg.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 1448;BA.debugLine="foto4Prg.TextSize = 14";
mostCurrent._foto4prg.setTextSize((float) (14));
 //BA.debugLineNum = 1449;BA.debugLine="totalFotos = totalFotos + 1";
_totalfotos = (int) (_totalfotos+1);
 };
 //BA.debugLineNum = 1451;BA.debugLine="If foto5 <> \"null\" Then";
if ((_foto5).equals("null") == false) { 
 //BA.debugLineNum = 1452;BA.debugLine="ProgressBar5.Initialize(\"ProgressBar5\")";
mostCurrent._progressbar5.Initialize(mostCurrent.activityBA,"ProgressBar5");
 //BA.debugLineNum = 1453;BA.debugLine="foto5Prg.Initialize(\"\")";
mostCurrent._foto5prg.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 1454;BA.debugLine="Activity.AddView(ProgressBar1, 50%x, 30%y, 30%x,";
mostCurrent._activity.AddView((android.view.View)(mostCurrent._progressbar1.getObject()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (50),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (30),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (30),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5)));
 //BA.debugLineNum = 1455;BA.debugLine="Activity.AddView(foto1Prg, 15%x, 70%y, 50%x, 20d";
mostCurrent._activity.AddView((android.view.View)(mostCurrent._foto1prg.getObject()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (15),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (70),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (50),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (20)));
 //BA.debugLineNum = 1456;BA.debugLine="foto5Prg.Text = \"Subiendo Foto 5:\"";
mostCurrent._foto5prg.setText(BA.ObjectToCharSequence("Subiendo Foto 5:"));
 //BA.debugLineNum = 1457;BA.debugLine="foto5Prg.TextColor = Colors.Black";
mostCurrent._foto5prg.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 1458;BA.debugLine="foto5Prg.TextSize = 14";
mostCurrent._foto5prg.setTextSize((float) (14));
 //BA.debugLineNum = 1459;BA.debugLine="totalFotos = totalFotos + 1";
_totalfotos = (int) (_totalfotos+1);
 };
 //BA.debugLineNum = 1463;BA.debugLine="TimerEnvio.Initialize(\"TimerEnvio\", 1000)";
mostCurrent._timerenvio.Initialize(processBA,"TimerEnvio",(long) (1000));
 //BA.debugLineNum = 1464;BA.debugLine="TimerEnvio.Enabled = True";
mostCurrent._timerenvio.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1466;BA.debugLine="If File.Exists(File.DirRootExternal & \"/PreserVam";
if (anywheresoftware.b4a.keywords.Common.File.Exists(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/PreserVamos/",_foto1+".jpg") && _foto1sent==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 1467;BA.debugLine="Log(\"Enviando foto 1 \")";
anywheresoftware.b4a.keywords.Common.LogImpl("214876736","Enviando foto 1 ",0);
 //BA.debugLineNum = 1468;BA.debugLine="Up1.doFileUpload(ProgressBar1,Null,File.DirRootE";
_up1.doFileUpload(processBA,(android.widget.ProgressBar)(mostCurrent._progressbar1.getObject()),(android.widget.TextView)(anywheresoftware.b4a.keywords.Common.Null),anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/PreserVamos/"+_foto1+".jpg",mostCurrent._main._serverpath /*String*/ +"/"+mostCurrent._main._serverconnectionfolder /*String*/ +"/upload_file.php");
 }else {
 //BA.debugLineNum = 1470;BA.debugLine="Log(\"no foto 1\")";
anywheresoftware.b4a.keywords.Common.LogImpl("214876739","no foto 1",0);
 };
 //BA.debugLineNum = 1473;BA.debugLine="End Sub";
return "";
}
public static void  _fondoblanco_click() throws Exception{
ResumableSub_fondoblanco_Click rsub = new ResumableSub_fondoblanco_Click(null);
rsub.resume(processBA, null);
}
public static class ResumableSub_fondoblanco_Click extends BA.ResumableSub {
public ResumableSub_fondoblanco_Click(ilpla.appear.frmdatosanteriores parent) {
this.parent = parent;
}
ilpla.appear.frmdatosanteriores parent;
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
 //BA.debugLineNum = 1476;BA.debugLine="If Main.lang = \"es\" Then";
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
 //BA.debugLineNum = 1477;BA.debugLine="Msgbox2Async(\"Se están enviando las fotografías,";
anywheresoftware.b4a.keywords.Common.Msgbox2Async(BA.ObjectToCharSequence("Se están enviando las fotografías, desea cancelar?"),BA.ObjectToCharSequence("Cancelar?"),"Si, cancelar","No!",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Null),(anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper(), (android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null)),processBA,anywheresoftware.b4a.keywords.Common.False);
 if (true) break;

case 5:
//C
this.state = 6;
 //BA.debugLineNum = 1479;BA.debugLine="Msgbox2Async(\"Photos are being uploaded, do you";
anywheresoftware.b4a.keywords.Common.Msgbox2Async(BA.ObjectToCharSequence("Photos are being uploaded, do you wish to cancel?"),BA.ObjectToCharSequence("Cancel?"),"Yes, cancel","No!",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Null),(anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper(), (android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null)),processBA,anywheresoftware.b4a.keywords.Common.False);
 if (true) break;

case 6:
//C
this.state = 7;
;
 //BA.debugLineNum = 1481;BA.debugLine="Wait For Msgbox_Result (Result As Int)";
anywheresoftware.b4a.keywords.Common.WaitFor("msgbox_result", processBA, this, null);
this.state = 11;
return;
case 11:
//C
this.state = 7;
_result = (Integer) result[0];
;
 //BA.debugLineNum = 1482;BA.debugLine="If Result = DialogResponse.POSITIVE Then";
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
 //BA.debugLineNum = 1483;BA.debugLine="Up1.UploadKill";
parent._up1.UploadKill(processBA);
 //BA.debugLineNum = 1485;BA.debugLine="Activity.RemoveAllViews";
parent.mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 1486;BA.debugLine="Activity.Finish";
parent.mostCurrent._activity.Finish();
 //BA.debugLineNum = 1487;BA.debugLine="StartActivity(Form_Main)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(parent.mostCurrent._form_main.getObject()));
 if (true) break;

case 10:
//C
this.state = -1;
;
 //BA.debugLineNum = 1489;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public static String  _fondogris_click() throws Exception{
 //BA.debugLineNum = 793;BA.debugLine="Sub fondogris_Click";
 //BA.debugLineNum = 794;BA.debugLine="fotogrande.RemoveView";
mostCurrent._fotogrande.RemoveView();
 //BA.debugLineNum = 795;BA.debugLine="foto1Borrar.RemoveView";
mostCurrent._foto1borrar.RemoveView();
 //BA.debugLineNum = 796;BA.debugLine="foto2Borrar.RemoveView";
mostCurrent._foto2borrar.RemoveView();
 //BA.debugLineNum = 797;BA.debugLine="fondogris.RemoveView";
mostCurrent._fondogris.RemoveView();
 //BA.debugLineNum = 798;BA.debugLine="End Sub";
return "";
}
public static void  _foto1borrar_click() throws Exception{
ResumableSub_foto1Borrar_Click rsub = new ResumableSub_foto1Borrar_Click(null);
rsub.resume(processBA, null);
}
public static class ResumableSub_foto1Borrar_Click extends BA.ResumableSub {
public ResumableSub_foto1Borrar_Click(ilpla.appear.frmdatosanteriores parent) {
this.parent = parent;
}
ilpla.appear.frmdatosanteriores parent;
int _result = 0;
anywheresoftware.b4a.objects.collections.Map _map1 = null;

@Override
public void resume(BA ba, Object[] result) throws Exception{

    while (true) {
        switch (state) {
            case -1:
return;

case 0:
//C
this.state = 1;
 //BA.debugLineNum = 807;BA.debugLine="If Main.lang = \"es\" Then";
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
 //BA.debugLineNum = 808;BA.debugLine="Msgbox2Async(\"Seguro que desea quitar esta foto";
anywheresoftware.b4a.keywords.Common.Msgbox2Async(BA.ObjectToCharSequence("Seguro que desea quitar esta foto de este registro?"),BA.ObjectToCharSequence("Quitar foto"),"Si","No","",(anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper(), (android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null)),processBA,anywheresoftware.b4a.keywords.Common.False);
 if (true) break;

case 5:
//C
this.state = 6;
 //BA.debugLineNum = 810;BA.debugLine="Msgbox2Async(\"Sure that you want to remove this";
anywheresoftware.b4a.keywords.Common.Msgbox2Async(BA.ObjectToCharSequence("Sure that you want to remove this photo from this report?"),BA.ObjectToCharSequence("Remove photo"),"Yes","No","",(anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper(), (android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null)),processBA,anywheresoftware.b4a.keywords.Common.False);
 if (true) break;

case 6:
//C
this.state = 7;
;
 //BA.debugLineNum = 812;BA.debugLine="Wait For Msgbox_Result (Result As Int)";
anywheresoftware.b4a.keywords.Common.WaitFor("msgbox_result", processBA, this, null);
this.state = 13;
return;
case 13:
//C
this.state = 7;
_result = (Integer) result[0];
;
 //BA.debugLineNum = 813;BA.debugLine="If Result = DialogResponse.POSITIVE Then";
if (true) break;

case 7:
//if
this.state = 12;
if (_result==anywheresoftware.b4a.keywords.Common.DialogResponse.POSITIVE) { 
this.state = 9;
}else {
this.state = 11;
}if (true) break;

case 9:
//C
this.state = 12;
 //BA.debugLineNum = 814;BA.debugLine="Dim Map1 As Map";
_map1 = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 815;BA.debugLine="Map1.Initialize";
_map1.Initialize();
 //BA.debugLineNum = 816;BA.debugLine="Map1.Put(\"Id\", currentDatoId)";
_map1.Put((Object)("Id"),(Object)(parent.mostCurrent._currentdatoid));
 //BA.debugLineNum = 817;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"evals\", \"fo";
parent.mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,parent.mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals","foto1",anywheresoftware.b4a.keywords.Common.Null,_map1);
 //BA.debugLineNum = 818;BA.debugLine="foto1view.Bitmap = Null";
parent.mostCurrent._foto1view.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null));
 //BA.debugLineNum = 819;BA.debugLine="foto1Borrar.RemoveView";
parent.mostCurrent._foto1borrar.RemoveView();
 //BA.debugLineNum = 820;BA.debugLine="fotogrande.RemoveView";
parent.mostCurrent._fotogrande.RemoveView();
 //BA.debugLineNum = 821;BA.debugLine="hubocambiosFotos = True";
parent._hubocambiosfotos = anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 822;BA.debugLine="btnEnviar.Visible = True";
parent.mostCurrent._btnenviar.setVisible(anywheresoftware.b4a.keywords.Common.True);
 if (true) break;

case 11:
//C
this.state = 12;
 //BA.debugLineNum = 824;BA.debugLine="Return";
if (true) return ;
 if (true) break;

case 12:
//C
this.state = -1;
;
 //BA.debugLineNum = 827;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public static String  _foto1view_click() throws Exception{
 //BA.debugLineNum = 674;BA.debugLine="Sub foto1view_Click";
 //BA.debugLineNum = 677;BA.debugLine="If foto1view.Bitmap = Null Then";
if (mostCurrent._foto1view.getBitmap()== null) { 
 //BA.debugLineNum = 678;BA.debugLine="fotoAdjuntar = \"foto1\"";
mostCurrent._fotoadjuntar = "foto1";
 //BA.debugLineNum = 679;BA.debugLine="CC.Initialize(\"CC\")";
_cc.Initialize("CC");
 //BA.debugLineNum = 680;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 681;BA.debugLine="CC.Show(\"image/\", \"Elija la foto\")";
_cc.Show(processBA,"image/","Elija la foto");
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 683;BA.debugLine="CC.Show(\"image/\", \"Choose the photo\")";
_cc.Show(processBA,"image/","Choose the photo");
 };
 }else {
 //BA.debugLineNum = 687;BA.debugLine="fondogris.Initialize(\"fondogris\")";
mostCurrent._fondogris.Initialize(mostCurrent.activityBA,"fondogris");
 //BA.debugLineNum = 688;BA.debugLine="fondogris.Color = Colors.ARGB(150,0,0,0)";
mostCurrent._fondogris.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (150),(int) (0),(int) (0),(int) (0)));
 //BA.debugLineNum = 689;BA.debugLine="Activity.AddView(fondogris, 0, 0, 100%x, 100%y)";
mostCurrent._activity.AddView((android.view.View)(mostCurrent._fondogris.getObject()),(int) (0),(int) (0),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA));
 //BA.debugLineNum = 691;BA.debugLine="fotogrande.Initialize(\"fotogrande\")";
mostCurrent._fotogrande.Initialize(mostCurrent.activityBA,"fotogrande");
 //BA.debugLineNum = 692;BA.debugLine="fotogrande.Bitmap = foto1view.Bitmap";
mostCurrent._fotogrande.setBitmap(mostCurrent._foto1view.getBitmap());
 //BA.debugLineNum = 693;BA.debugLine="Log(foto1view.Width)";
anywheresoftware.b4a.keywords.Common.LogImpl("213434899",BA.NumberToString(mostCurrent._foto1view.getWidth()),0);
 //BA.debugLineNum = 694;BA.debugLine="Activity.AddView(fotogrande, 0, 0, 100%x, 100%y)";
mostCurrent._activity.AddView((android.view.View)(mostCurrent._fotogrande.getObject()),(int) (0),(int) (0),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA));
 //BA.debugLineNum = 696;BA.debugLine="Activity.AddView(foto1Borrar, 10dip, 10dip, 40di";
mostCurrent._activity.AddView((android.view.View)(mostCurrent._foto1borrar.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (40)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (40)));
 };
 //BA.debugLineNum = 699;BA.debugLine="End Sub";
return "";
}
public static void  _foto2borrar_click() throws Exception{
ResumableSub_foto2Borrar_Click rsub = new ResumableSub_foto2Borrar_Click(null);
rsub.resume(processBA, null);
}
public static class ResumableSub_foto2Borrar_Click extends BA.ResumableSub {
public ResumableSub_foto2Borrar_Click(ilpla.appear.frmdatosanteriores parent) {
this.parent = parent;
}
ilpla.appear.frmdatosanteriores parent;
int _result = 0;
anywheresoftware.b4a.objects.collections.Map _map1 = null;

@Override
public void resume(BA ba, Object[] result) throws Exception{

    while (true) {
        switch (state) {
            case -1:
return;

case 0:
//C
this.state = 1;
 //BA.debugLineNum = 829;BA.debugLine="If Main.lang = \"es\" Then";
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
 //BA.debugLineNum = 830;BA.debugLine="Msgbox2Async(\"Seguro que desea quitar esta foto";
anywheresoftware.b4a.keywords.Common.Msgbox2Async(BA.ObjectToCharSequence("Seguro que desea quitar esta foto de este registro?"),BA.ObjectToCharSequence("Quitar foto"),"Si","No","",(anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper(), (android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null)),processBA,anywheresoftware.b4a.keywords.Common.False);
 if (true) break;

case 5:
//C
this.state = 6;
 //BA.debugLineNum = 832;BA.debugLine="Msgbox2Async(\"Sure that you want to remove this";
anywheresoftware.b4a.keywords.Common.Msgbox2Async(BA.ObjectToCharSequence("Sure that you want to remove this photo from this report?"),BA.ObjectToCharSequence("Remove photo"),"Yes","No","",(anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper(), (android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null)),processBA,anywheresoftware.b4a.keywords.Common.False);
 if (true) break;

case 6:
//C
this.state = 7;
;
 //BA.debugLineNum = 834;BA.debugLine="Wait For Msgbox_Result (Result As Int)";
anywheresoftware.b4a.keywords.Common.WaitFor("msgbox_result", processBA, this, null);
this.state = 13;
return;
case 13:
//C
this.state = 7;
_result = (Integer) result[0];
;
 //BA.debugLineNum = 835;BA.debugLine="If Result = DialogResponse.POSITIVE Then";
if (true) break;

case 7:
//if
this.state = 12;
if (_result==anywheresoftware.b4a.keywords.Common.DialogResponse.POSITIVE) { 
this.state = 9;
}else {
this.state = 11;
}if (true) break;

case 9:
//C
this.state = 12;
 //BA.debugLineNum = 836;BA.debugLine="Dim Map1 As Map";
_map1 = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 837;BA.debugLine="Map1.Initialize";
_map1.Initialize();
 //BA.debugLineNum = 838;BA.debugLine="Map1.Put(\"Id\", currentDatoId)";
_map1.Put((Object)("Id"),(Object)(parent.mostCurrent._currentdatoid));
 //BA.debugLineNum = 839;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"evals\", \"fo";
parent.mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,parent.mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals","foto2",anywheresoftware.b4a.keywords.Common.Null,_map1);
 //BA.debugLineNum = 840;BA.debugLine="foto2view.Bitmap = Null";
parent.mostCurrent._foto2view.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null));
 //BA.debugLineNum = 841;BA.debugLine="foto2Borrar.RemoveView";
parent.mostCurrent._foto2borrar.RemoveView();
 //BA.debugLineNum = 842;BA.debugLine="fotogrande.RemoveView";
parent.mostCurrent._fotogrande.RemoveView();
 //BA.debugLineNum = 843;BA.debugLine="hubocambiosFotos = True";
parent._hubocambiosfotos = anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 844;BA.debugLine="btnEnviar.Visible = True";
parent.mostCurrent._btnenviar.setVisible(anywheresoftware.b4a.keywords.Common.True);
 if (true) break;

case 11:
//C
this.state = 12;
 //BA.debugLineNum = 846;BA.debugLine="Return";
if (true) return ;
 if (true) break;

case 12:
//C
this.state = -1;
;
 //BA.debugLineNum = 849;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public static String  _foto2view_click() throws Exception{
 //BA.debugLineNum = 700;BA.debugLine="Sub foto2view_Click";
 //BA.debugLineNum = 701;BA.debugLine="If foto2view.Bitmap = Null Then";
if (mostCurrent._foto2view.getBitmap()== null) { 
 //BA.debugLineNum = 702;BA.debugLine="fotoAdjuntar = \"foto2\"";
mostCurrent._fotoadjuntar = "foto2";
 //BA.debugLineNum = 703;BA.debugLine="CC.Initialize(\"CC\")";
_cc.Initialize("CC");
 //BA.debugLineNum = 704;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 705;BA.debugLine="CC.Show(\"image/\", \"Elija la foto\")";
_cc.Show(processBA,"image/","Elija la foto");
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 707;BA.debugLine="CC.Show(\"image/\", \"Choose the photo\")";
_cc.Show(processBA,"image/","Choose the photo");
 };
 }else {
 //BA.debugLineNum = 712;BA.debugLine="fondogris.Initialize(\"fondogris\")";
mostCurrent._fondogris.Initialize(mostCurrent.activityBA,"fondogris");
 //BA.debugLineNum = 713;BA.debugLine="fondogris.Color = Colors.ARGB(150,0,0,0)";
mostCurrent._fondogris.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (150),(int) (0),(int) (0),(int) (0)));
 //BA.debugLineNum = 714;BA.debugLine="Activity.AddView(fondogris, 0, 0, 100%x, 100%y)";
mostCurrent._activity.AddView((android.view.View)(mostCurrent._fondogris.getObject()),(int) (0),(int) (0),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA));
 //BA.debugLineNum = 716;BA.debugLine="fotogrande.Initialize(\"fotogrande\")";
mostCurrent._fotogrande.Initialize(mostCurrent.activityBA,"fotogrande");
 //BA.debugLineNum = 717;BA.debugLine="fotogrande.Bitmap = foto2view.Bitmap";
mostCurrent._fotogrande.setBitmap(mostCurrent._foto2view.getBitmap());
 //BA.debugLineNum = 718;BA.debugLine="Activity.AddView(fotogrande, 0, 0, 100%x, 100%y)";
mostCurrent._activity.AddView((android.view.View)(mostCurrent._fotogrande.getObject()),(int) (0),(int) (0),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA));
 //BA.debugLineNum = 720;BA.debugLine="Activity.AddView(foto2Borrar, 10dip, 10dip, 40di";
mostCurrent._activity.AddView((android.view.View)(mostCurrent._foto2borrar.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (40)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (40)));
 };
 //BA.debugLineNum = 722;BA.debugLine="End Sub";
return "";
}
public static void  _foto3borrar_click() throws Exception{
ResumableSub_foto3Borrar_Click rsub = new ResumableSub_foto3Borrar_Click(null);
rsub.resume(processBA, null);
}
public static class ResumableSub_foto3Borrar_Click extends BA.ResumableSub {
public ResumableSub_foto3Borrar_Click(ilpla.appear.frmdatosanteriores parent) {
this.parent = parent;
}
ilpla.appear.frmdatosanteriores parent;
int _result = 0;
anywheresoftware.b4a.objects.collections.Map _map1 = null;

@Override
public void resume(BA ba, Object[] result) throws Exception{

    while (true) {
        switch (state) {
            case -1:
return;

case 0:
//C
this.state = 1;
 //BA.debugLineNum = 851;BA.debugLine="If Main.lang = \"es\" Then";
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
 //BA.debugLineNum = 852;BA.debugLine="Msgbox2Async(\"Seguro que desea quitar esta foto";
anywheresoftware.b4a.keywords.Common.Msgbox2Async(BA.ObjectToCharSequence("Seguro que desea quitar esta foto de este registro?"),BA.ObjectToCharSequence("Quitar foto"),"Si","No","",(anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper(), (android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null)),processBA,anywheresoftware.b4a.keywords.Common.False);
 if (true) break;

case 5:
//C
this.state = 6;
 //BA.debugLineNum = 854;BA.debugLine="Msgbox2Async(\"Sure that you want to remove this";
anywheresoftware.b4a.keywords.Common.Msgbox2Async(BA.ObjectToCharSequence("Sure that you want to remove this photo from this report?"),BA.ObjectToCharSequence("Remove photo"),"Yes","No","",(anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper(), (android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null)),processBA,anywheresoftware.b4a.keywords.Common.False);
 if (true) break;

case 6:
//C
this.state = 7;
;
 //BA.debugLineNum = 856;BA.debugLine="Wait For Msgbox_Result (Result As Int)";
anywheresoftware.b4a.keywords.Common.WaitFor("msgbox_result", processBA, this, null);
this.state = 13;
return;
case 13:
//C
this.state = 7;
_result = (Integer) result[0];
;
 //BA.debugLineNum = 857;BA.debugLine="If Result = DialogResponse.POSITIVE Then";
if (true) break;

case 7:
//if
this.state = 12;
if (_result==anywheresoftware.b4a.keywords.Common.DialogResponse.POSITIVE) { 
this.state = 9;
}else {
this.state = 11;
}if (true) break;

case 9:
//C
this.state = 12;
 //BA.debugLineNum = 858;BA.debugLine="Dim Map1 As Map";
_map1 = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 859;BA.debugLine="Map1.Initialize";
_map1.Initialize();
 //BA.debugLineNum = 860;BA.debugLine="Map1.Put(\"Id\", currentDatoId)";
_map1.Put((Object)("Id"),(Object)(parent.mostCurrent._currentdatoid));
 //BA.debugLineNum = 861;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"evals\", \"fo";
parent.mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,parent.mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals","foto3",anywheresoftware.b4a.keywords.Common.Null,_map1);
 //BA.debugLineNum = 862;BA.debugLine="foto3view.Bitmap = Null";
parent.mostCurrent._foto3view.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null));
 //BA.debugLineNum = 863;BA.debugLine="foto3Borrar.RemoveView";
parent.mostCurrent._foto3borrar.RemoveView();
 //BA.debugLineNum = 864;BA.debugLine="fotogrande.RemoveView";
parent.mostCurrent._fotogrande.RemoveView();
 //BA.debugLineNum = 865;BA.debugLine="hubocambiosFotos = True";
parent._hubocambiosfotos = anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 866;BA.debugLine="btnEnviar.Visible = True";
parent.mostCurrent._btnenviar.setVisible(anywheresoftware.b4a.keywords.Common.True);
 if (true) break;

case 11:
//C
this.state = 12;
 //BA.debugLineNum = 868;BA.debugLine="Return";
if (true) return ;
 if (true) break;

case 12:
//C
this.state = -1;
;
 //BA.debugLineNum = 871;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public static String  _foto3view_click() throws Exception{
 //BA.debugLineNum = 723;BA.debugLine="Sub foto3view_Click";
 //BA.debugLineNum = 724;BA.debugLine="If foto3view.Bitmap = Null Then";
if (mostCurrent._foto3view.getBitmap()== null) { 
 //BA.debugLineNum = 725;BA.debugLine="fotoAdjuntar = \"foto3\"";
mostCurrent._fotoadjuntar = "foto3";
 //BA.debugLineNum = 726;BA.debugLine="CC.Initialize(\"CC\")";
_cc.Initialize("CC");
 //BA.debugLineNum = 727;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 728;BA.debugLine="CC.Show(\"image/\", \"Elija la foto\")";
_cc.Show(processBA,"image/","Elija la foto");
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 730;BA.debugLine="CC.Show(\"image/\", \"Choose the photo\")";
_cc.Show(processBA,"image/","Choose the photo");
 };
 }else {
 //BA.debugLineNum = 735;BA.debugLine="fondogris.Initialize(\"fondogris\")";
mostCurrent._fondogris.Initialize(mostCurrent.activityBA,"fondogris");
 //BA.debugLineNum = 736;BA.debugLine="fondogris.Color = Colors.ARGB(150,0,0,0)";
mostCurrent._fondogris.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (150),(int) (0),(int) (0),(int) (0)));
 //BA.debugLineNum = 737;BA.debugLine="Activity.AddView(fondogris, 0, 0, 100%x, 100%y)";
mostCurrent._activity.AddView((android.view.View)(mostCurrent._fondogris.getObject()),(int) (0),(int) (0),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA));
 //BA.debugLineNum = 739;BA.debugLine="fotogrande.Initialize(\"fotogrande\")";
mostCurrent._fotogrande.Initialize(mostCurrent.activityBA,"fotogrande");
 //BA.debugLineNum = 740;BA.debugLine="fotogrande.Bitmap = foto2view.Bitmap";
mostCurrent._fotogrande.setBitmap(mostCurrent._foto2view.getBitmap());
 //BA.debugLineNum = 741;BA.debugLine="Activity.AddView(fotogrande, 0, 0, 100%x, 100%y)";
mostCurrent._activity.AddView((android.view.View)(mostCurrent._fotogrande.getObject()),(int) (0),(int) (0),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA));
 //BA.debugLineNum = 743;BA.debugLine="Activity.AddView(foto3Borrar, 10dip, 10dip, 40di";
mostCurrent._activity.AddView((android.view.View)(mostCurrent._foto3borrar.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (40)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (40)));
 };
 //BA.debugLineNum = 745;BA.debugLine="End Sub";
return "";
}
public static void  _foto4borrar_click() throws Exception{
ResumableSub_foto4Borrar_Click rsub = new ResumableSub_foto4Borrar_Click(null);
rsub.resume(processBA, null);
}
public static class ResumableSub_foto4Borrar_Click extends BA.ResumableSub {
public ResumableSub_foto4Borrar_Click(ilpla.appear.frmdatosanteriores parent) {
this.parent = parent;
}
ilpla.appear.frmdatosanteriores parent;
int _result = 0;
anywheresoftware.b4a.objects.collections.Map _map1 = null;

@Override
public void resume(BA ba, Object[] result) throws Exception{

    while (true) {
        switch (state) {
            case -1:
return;

case 0:
//C
this.state = 1;
 //BA.debugLineNum = 873;BA.debugLine="If Main.lang = \"es\" Then";
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
 //BA.debugLineNum = 874;BA.debugLine="Msgbox2Async(\"Seguro que desea quitar esta foto";
anywheresoftware.b4a.keywords.Common.Msgbox2Async(BA.ObjectToCharSequence("Seguro que desea quitar esta foto de este registro?"),BA.ObjectToCharSequence("Quitar foto"),"Si","No","",(anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper(), (android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null)),processBA,anywheresoftware.b4a.keywords.Common.False);
 if (true) break;

case 5:
//C
this.state = 6;
 //BA.debugLineNum = 876;BA.debugLine="Msgbox2Async(\"Sure that you want to remove this";
anywheresoftware.b4a.keywords.Common.Msgbox2Async(BA.ObjectToCharSequence("Sure that you want to remove this photo from this report?"),BA.ObjectToCharSequence("Remove photo"),"Yes","No","",(anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper(), (android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null)),processBA,anywheresoftware.b4a.keywords.Common.False);
 if (true) break;

case 6:
//C
this.state = 7;
;
 //BA.debugLineNum = 878;BA.debugLine="Wait For Msgbox_Result (Result As Int)";
anywheresoftware.b4a.keywords.Common.WaitFor("msgbox_result", processBA, this, null);
this.state = 13;
return;
case 13:
//C
this.state = 7;
_result = (Integer) result[0];
;
 //BA.debugLineNum = 879;BA.debugLine="If Result = DialogResponse.POSITIVE Then";
if (true) break;

case 7:
//if
this.state = 12;
if (_result==anywheresoftware.b4a.keywords.Common.DialogResponse.POSITIVE) { 
this.state = 9;
}else {
this.state = 11;
}if (true) break;

case 9:
//C
this.state = 12;
 //BA.debugLineNum = 881;BA.debugLine="Dim Map1 As Map";
_map1 = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 882;BA.debugLine="Map1.Initialize";
_map1.Initialize();
 //BA.debugLineNum = 883;BA.debugLine="Map1.Put(\"Id\", currentDatoId)";
_map1.Put((Object)("Id"),(Object)(parent.mostCurrent._currentdatoid));
 //BA.debugLineNum = 884;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"evals\", \"fo";
parent.mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,parent.mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals","foto4",anywheresoftware.b4a.keywords.Common.Null,_map1);
 //BA.debugLineNum = 885;BA.debugLine="foto4view.Bitmap = Null";
parent.mostCurrent._foto4view.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null));
 //BA.debugLineNum = 886;BA.debugLine="foto4Borrar.RemoveView";
parent.mostCurrent._foto4borrar.RemoveView();
 //BA.debugLineNum = 887;BA.debugLine="fotogrande.RemoveView";
parent.mostCurrent._fotogrande.RemoveView();
 //BA.debugLineNum = 888;BA.debugLine="hubocambiosFotos = True";
parent._hubocambiosfotos = anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 889;BA.debugLine="btnEnviar.Visible = True";
parent.mostCurrent._btnenviar.setVisible(anywheresoftware.b4a.keywords.Common.True);
 if (true) break;

case 11:
//C
this.state = 12;
 //BA.debugLineNum = 891;BA.debugLine="Return";
if (true) return ;
 if (true) break;

case 12:
//C
this.state = -1;
;
 //BA.debugLineNum = 894;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public static String  _foto4view_click() throws Exception{
 //BA.debugLineNum = 746;BA.debugLine="Sub foto4view_Click";
 //BA.debugLineNum = 747;BA.debugLine="If foto4view.Bitmap = Null Then";
if (mostCurrent._foto4view.getBitmap()== null) { 
 //BA.debugLineNum = 748;BA.debugLine="fotoAdjuntar = \"foto4\"";
mostCurrent._fotoadjuntar = "foto4";
 //BA.debugLineNum = 749;BA.debugLine="CC.Initialize(\"CC\")";
_cc.Initialize("CC");
 //BA.debugLineNum = 750;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 751;BA.debugLine="CC.Show(\"image/\", \"Elija la foto\")";
_cc.Show(processBA,"image/","Elija la foto");
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 753;BA.debugLine="CC.Show(\"image/\", \"Choose the photo\")";
_cc.Show(processBA,"image/","Choose the photo");
 };
 }else {
 //BA.debugLineNum = 758;BA.debugLine="fondogris.Initialize(\"fondogris\")";
mostCurrent._fondogris.Initialize(mostCurrent.activityBA,"fondogris");
 //BA.debugLineNum = 759;BA.debugLine="fondogris.Color = Colors.ARGB(150,0,0,0)";
mostCurrent._fondogris.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (150),(int) (0),(int) (0),(int) (0)));
 //BA.debugLineNum = 760;BA.debugLine="Activity.AddView(fondogris, 0, 0, 100%x, 100%y)";
mostCurrent._activity.AddView((android.view.View)(mostCurrent._fondogris.getObject()),(int) (0),(int) (0),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA));
 //BA.debugLineNum = 762;BA.debugLine="fotogrande.Initialize(\"fotogrande\")";
mostCurrent._fotogrande.Initialize(mostCurrent.activityBA,"fotogrande");
 //BA.debugLineNum = 763;BA.debugLine="fotogrande.Bitmap = foto4view.Bitmap";
mostCurrent._fotogrande.setBitmap(mostCurrent._foto4view.getBitmap());
 //BA.debugLineNum = 764;BA.debugLine="Activity.AddView(fotogrande, 0, 0, 100%x, 100%y)";
mostCurrent._activity.AddView((android.view.View)(mostCurrent._fotogrande.getObject()),(int) (0),(int) (0),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA));
 //BA.debugLineNum = 766;BA.debugLine="Activity.AddView(foto4Borrar, 10dip, 10dip, 40di";
mostCurrent._activity.AddView((android.view.View)(mostCurrent._foto4borrar.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (40)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (40)));
 };
 //BA.debugLineNum = 768;BA.debugLine="End Sub";
return "";
}
public static void  _foto5borrar_click() throws Exception{
ResumableSub_foto5Borrar_Click rsub = new ResumableSub_foto5Borrar_Click(null);
rsub.resume(processBA, null);
}
public static class ResumableSub_foto5Borrar_Click extends BA.ResumableSub {
public ResumableSub_foto5Borrar_Click(ilpla.appear.frmdatosanteriores parent) {
this.parent = parent;
}
ilpla.appear.frmdatosanteriores parent;
int _result = 0;
anywheresoftware.b4a.objects.collections.Map _map1 = null;

@Override
public void resume(BA ba, Object[] result) throws Exception{

    while (true) {
        switch (state) {
            case -1:
return;

case 0:
//C
this.state = 1;
 //BA.debugLineNum = 896;BA.debugLine="If Main.lang = \"es\" Then";
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
 //BA.debugLineNum = 897;BA.debugLine="Msgbox2Async(\"Seguro que desea quitar esta foto";
anywheresoftware.b4a.keywords.Common.Msgbox2Async(BA.ObjectToCharSequence("Seguro que desea quitar esta foto de este registro?"),BA.ObjectToCharSequence("Quitar foto"),"Si","No","",(anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper(), (android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null)),processBA,anywheresoftware.b4a.keywords.Common.False);
 if (true) break;

case 5:
//C
this.state = 6;
 //BA.debugLineNum = 899;BA.debugLine="Msgbox2Async(\"Sure that you want to remove this";
anywheresoftware.b4a.keywords.Common.Msgbox2Async(BA.ObjectToCharSequence("Sure that you want to remove this photo from this report?"),BA.ObjectToCharSequence("Remove photo"),"Yes","No","",(anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper(), (android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null)),processBA,anywheresoftware.b4a.keywords.Common.False);
 if (true) break;

case 6:
//C
this.state = 7;
;
 //BA.debugLineNum = 901;BA.debugLine="Wait For Msgbox_Result (Result As Int)";
anywheresoftware.b4a.keywords.Common.WaitFor("msgbox_result", processBA, this, null);
this.state = 13;
return;
case 13:
//C
this.state = 7;
_result = (Integer) result[0];
;
 //BA.debugLineNum = 902;BA.debugLine="If Result = DialogResponse.POSITIVE Then";
if (true) break;

case 7:
//if
this.state = 12;
if (_result==anywheresoftware.b4a.keywords.Common.DialogResponse.POSITIVE) { 
this.state = 9;
}else {
this.state = 11;
}if (true) break;

case 9:
//C
this.state = 12;
 //BA.debugLineNum = 903;BA.debugLine="Dim Map1 As Map";
_map1 = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 904;BA.debugLine="Map1.Initialize";
_map1.Initialize();
 //BA.debugLineNum = 905;BA.debugLine="Map1.Put(\"Id\", currentDatoId)";
_map1.Put((Object)("Id"),(Object)(parent.mostCurrent._currentdatoid));
 //BA.debugLineNum = 906;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"evals\", \"fo";
parent.mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,parent.mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals","foto5",anywheresoftware.b4a.keywords.Common.Null,_map1);
 //BA.debugLineNum = 907;BA.debugLine="foto5view.Bitmap = Null";
parent.mostCurrent._foto5view.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null));
 //BA.debugLineNum = 908;BA.debugLine="foto5Borrar.RemoveView";
parent.mostCurrent._foto5borrar.RemoveView();
 //BA.debugLineNum = 909;BA.debugLine="fotogrande.RemoveView";
parent.mostCurrent._fotogrande.RemoveView();
 //BA.debugLineNum = 910;BA.debugLine="hubocambiosFotos = True";
parent._hubocambiosfotos = anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 911;BA.debugLine="btnEnviar.Visible = True";
parent.mostCurrent._btnenviar.setVisible(anywheresoftware.b4a.keywords.Common.True);
 if (true) break;

case 11:
//C
this.state = 12;
 //BA.debugLineNum = 913;BA.debugLine="Return";
if (true) return ;
 if (true) break;

case 12:
//C
this.state = -1;
;
 //BA.debugLineNum = 916;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public static String  _foto5view_click() throws Exception{
 //BA.debugLineNum = 769;BA.debugLine="Sub foto5view_Click";
 //BA.debugLineNum = 770;BA.debugLine="If foto5view.Bitmap = Null Then";
if (mostCurrent._foto5view.getBitmap()== null) { 
 //BA.debugLineNum = 771;BA.debugLine="fotoAdjuntar = \"foto5\"";
mostCurrent._fotoadjuntar = "foto5";
 //BA.debugLineNum = 772;BA.debugLine="CC.Initialize(\"CC\")";
_cc.Initialize("CC");
 //BA.debugLineNum = 773;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 774;BA.debugLine="CC.Show(\"image/\", \"Elija la foto\")";
_cc.Show(processBA,"image/","Elija la foto");
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 776;BA.debugLine="CC.Show(\"image/\", \"Choose the photo\")";
_cc.Show(processBA,"image/","Choose the photo");
 };
 }else {
 //BA.debugLineNum = 781;BA.debugLine="fondogris.Initialize(\"fondogris\")";
mostCurrent._fondogris.Initialize(mostCurrent.activityBA,"fondogris");
 //BA.debugLineNum = 782;BA.debugLine="fondogris.Color = Colors.ARGB(150,0,0,0)";
mostCurrent._fondogris.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (150),(int) (0),(int) (0),(int) (0)));
 //BA.debugLineNum = 783;BA.debugLine="Activity.AddView(fondogris, 0, 0, 100%x, 100%y)";
mostCurrent._activity.AddView((android.view.View)(mostCurrent._fondogris.getObject()),(int) (0),(int) (0),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA));
 //BA.debugLineNum = 785;BA.debugLine="fotogrande.Initialize(\"fotogrande\")";
mostCurrent._fotogrande.Initialize(mostCurrent.activityBA,"fotogrande");
 //BA.debugLineNum = 786;BA.debugLine="fotogrande.Bitmap = foto5view.Bitmap";
mostCurrent._fotogrande.setBitmap(mostCurrent._foto5view.getBitmap());
 //BA.debugLineNum = 787;BA.debugLine="Activity.AddView(fotogrande, 0, 0, 100%x, 100%y)";
mostCurrent._activity.AddView((android.view.View)(mostCurrent._fotogrande.getObject()),(int) (0),(int) (0),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA));
 //BA.debugLineNum = 789;BA.debugLine="Activity.AddView(foto5Borrar, 10dip, 10dip, 40di";
mostCurrent._activity.AddView((android.view.View)(mostCurrent._foto5borrar.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (40)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (40)));
 };
 //BA.debugLineNum = 791;BA.debugLine="End Sub";
return "";
}
public static String  _fotogrande_click() throws Exception{
 //BA.debugLineNum = 799;BA.debugLine="Sub fotogrande_Click";
 //BA.debugLineNum = 800;BA.debugLine="fotogrande.RemoveView";
mostCurrent._fotogrande.RemoveView();
 //BA.debugLineNum = 801;BA.debugLine="foto1Borrar.RemoveView";
mostCurrent._foto1borrar.RemoveView();
 //BA.debugLineNum = 802;BA.debugLine="foto2Borrar.RemoveView";
mostCurrent._foto2borrar.RemoveView();
 //BA.debugLineNum = 803;BA.debugLine="fondogris.RemoveView";
mostCurrent._fondogris.RemoveView();
 //BA.debugLineNum = 804;BA.debugLine="End Sub";
return "";
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 33;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 36;BA.debugLine="Dim ListView1 As ListView";
mostCurrent._listview1 = new anywheresoftware.b4a.objects.ListViewWrapper();
 //BA.debugLineNum = 38;BA.debugLine="Private btnCerrar As Button";
mostCurrent._btncerrar = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 41;BA.debugLine="Private tabStripDatosAnteriores As TabStrip";
mostCurrent._tabstripdatosanteriores = new anywheresoftware.b4a.objects.TabStripViewPager();
 //BA.debugLineNum = 42;BA.debugLine="Dim tabActual As String";
mostCurrent._tabactual = "";
 //BA.debugLineNum = 45;BA.debugLine="Private lblFecha As Label";
mostCurrent._lblfecha = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 46;BA.debugLine="Private lblTipo As Label";
mostCurrent._lbltipo = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 47;BA.debugLine="Private lblLat As Label";
mostCurrent._lbllat = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 48;BA.debugLine="Private lblCalidad As Label";
mostCurrent._lblcalidad = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 49;BA.debugLine="Private lblNotas As Label";
mostCurrent._lblnotas = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 50;BA.debugLine="Private lblLng As Label";
mostCurrent._lbllng = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 51;BA.debugLine="Private chkPublico As CheckBox";
mostCurrent._chkpublico = new anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper();
 //BA.debugLineNum = 52;BA.debugLine="Private chkEnviado As CheckBox";
mostCurrent._chkenviado = new anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper();
 //BA.debugLineNum = 53;BA.debugLine="Private chkValidadoExpertos As Label";
mostCurrent._chkvalidadoexpertos = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 54;BA.debugLine="Private chkOnline As Label";
mostCurrent._chkonline = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 55;BA.debugLine="Private chkEnviadoBar As Label";
mostCurrent._chkenviadobar = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 56;BA.debugLine="Private scvFotos As HorizontalScrollView";
mostCurrent._scvfotos = new anywheresoftware.b4a.objects.HorizontalScrollViewWrapper();
 //BA.debugLineNum = 57;BA.debugLine="Private lblNombreEnvio As Label";
mostCurrent._lblnombreenvio = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 58;BA.debugLine="Private lblBarra As Label";
mostCurrent._lblbarra = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 59;BA.debugLine="Dim currentDatoId As String";
mostCurrent._currentdatoid = "";
 //BA.debugLineNum = 62;BA.debugLine="Dim foto1view As ImageView";
mostCurrent._foto1view = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 63;BA.debugLine="Dim foto2view As ImageView";
mostCurrent._foto2view = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 64;BA.debugLine="Dim foto3view As ImageView";
mostCurrent._foto3view = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 65;BA.debugLine="Dim foto4view As ImageView";
mostCurrent._foto4view = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 66;BA.debugLine="Dim foto5view As ImageView";
mostCurrent._foto5view = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 67;BA.debugLine="Dim fotogrande As ImageView";
mostCurrent._fotogrande = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 68;BA.debugLine="Dim fondogris As Label";
mostCurrent._fondogris = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 69;BA.debugLine="Dim foto1Borrar As Button";
mostCurrent._foto1borrar = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 70;BA.debugLine="Dim foto2Borrar As Button";
mostCurrent._foto2borrar = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 71;BA.debugLine="Dim foto3Borrar As Button";
mostCurrent._foto3borrar = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 72;BA.debugLine="Dim foto4Borrar As Button";
mostCurrent._foto4borrar = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 73;BA.debugLine="Dim foto5Borrar As Button";
mostCurrent._foto5borrar = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 74;BA.debugLine="Dim fotoAdjuntar As String";
mostCurrent._fotoadjuntar = "";
 //BA.debugLineNum = 77;BA.debugLine="Private btnEnviar As Button";
mostCurrent._btnenviar = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 78;BA.debugLine="Dim IdProyectoEnviar As String";
mostCurrent._idproyectoenviar = "";
 //BA.debugLineNum = 79;BA.debugLine="Private btnCambiarPublico As Button";
mostCurrent._btncambiarpublico = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 80;BA.debugLine="Dim NewMarcador As Boolean = True";
_newmarcador = anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 81;BA.debugLine="Dim numberTasks As Int";
_numbertasks = 0;
 //BA.debugLineNum = 83;BA.debugLine="Dim dated As DateDialog";
mostCurrent._dated = new anywheresoftware.b4a.agraham.dialogs.InputDialog.DateDialog();
 //BA.debugLineNum = 87;BA.debugLine="Private hubocambiosDatos As Boolean";
_hubocambiosdatos = false;
 //BA.debugLineNum = 88;BA.debugLine="Private hubocambiosFotos As Boolean";
_hubocambiosfotos = false;
 //BA.debugLineNum = 89;BA.debugLine="Private fondoblanco As Label";
mostCurrent._fondoblanco = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 90;BA.debugLine="Private foto1Prg As Label";
mostCurrent._foto1prg = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 91;BA.debugLine="Private foto2Prg As Label";
mostCurrent._foto2prg = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 92;BA.debugLine="Private foto3Prg As Label";
mostCurrent._foto3prg = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 93;BA.debugLine="Private foto4Prg As Label";
mostCurrent._foto4prg = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 94;BA.debugLine="Private foto5Prg As Label";
mostCurrent._foto5prg = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 95;BA.debugLine="Private ProgressBar1 As ProgressBar";
mostCurrent._progressbar1 = new anywheresoftware.b4a.objects.ProgressBarWrapper();
 //BA.debugLineNum = 96;BA.debugLine="Private ProgressBar2 As ProgressBar";
mostCurrent._progressbar2 = new anywheresoftware.b4a.objects.ProgressBarWrapper();
 //BA.debugLineNum = 97;BA.debugLine="Private ProgressBar3 As ProgressBar";
mostCurrent._progressbar3 = new anywheresoftware.b4a.objects.ProgressBarWrapper();
 //BA.debugLineNum = 98;BA.debugLine="Private ProgressBar4 As ProgressBar";
mostCurrent._progressbar4 = new anywheresoftware.b4a.objects.ProgressBarWrapper();
 //BA.debugLineNum = 99;BA.debugLine="Private ProgressBar5 As ProgressBar";
mostCurrent._progressbar5 = new anywheresoftware.b4a.objects.ProgressBarWrapper();
 //BA.debugLineNum = 101;BA.debugLine="Private totalFotos As Int";
_totalfotos = 0;
 //BA.debugLineNum = 102;BA.debugLine="Private foto1Sent As Boolean";
_foto1sent = false;
 //BA.debugLineNum = 103;BA.debugLine="Private foto2Sent As Boolean";
_foto2sent = false;
 //BA.debugLineNum = 104;BA.debugLine="Private foto3Sent As Boolean";
_foto3sent = false;
 //BA.debugLineNum = 105;BA.debugLine="Private foto4Sent As Boolean";
_foto4sent = false;
 //BA.debugLineNum = 106;BA.debugLine="Private foto5Sent As Boolean";
_foto5sent = false;
 //BA.debugLineNum = 107;BA.debugLine="Private TimerEnvio As Timer";
mostCurrent._timerenvio = new anywheresoftware.b4a.objects.Timer();
 //BA.debugLineNum = 108;BA.debugLine="Dim fotosEnviadas As Int";
_fotosenviadas = 0;
 //BA.debugLineNum = 110;BA.debugLine="Private Label9 As Label";
mostCurrent._label9 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 115;BA.debugLine="Private Label6 As Label";
mostCurrent._label6 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 116;BA.debugLine="Private Label7 As Label";
mostCurrent._label7 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 117;BA.debugLine="Private Label8 As Label";
mostCurrent._label8 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 118;BA.debugLine="Private Label10 As Label";
mostCurrent._label10 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 119;BA.debugLine="Private Label12 As Label";
mostCurrent._label12 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 120;BA.debugLine="Private Label3 As Label";
mostCurrent._label3 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 121;BA.debugLine="Private Label2 As Label";
mostCurrent._label2 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 122;BA.debugLine="Private Label13 As Label";
mostCurrent._label13 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 125;BA.debugLine="End Sub";
return "";
}
public static String  _listardatos() throws Exception{
anywheresoftware.b4a.objects.collections.List _listmaps = null;
int _r = 0;
anywheresoftware.b4a.objects.collections.Map _rmap = null;
String _esent = "";
String _latdato = "";
String _lngdato = "";
String _fechadato = "";
String _iddato = "";
 //BA.debugLineNum = 266;BA.debugLine="Sub ListarDatos";
 //BA.debugLineNum = 267;BA.debugLine="ListView1.Clear";
mostCurrent._listview1.Clear();
 //BA.debugLineNum = 268;BA.debugLine="ListView1.Color = Colors.White";
mostCurrent._listview1.setColor(anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 269;BA.debugLine="ListView1.TwoLinesAndBitmap.Label.Width = ListVie";
mostCurrent._listview1.getTwoLinesAndBitmap().Label.setWidth((int) (mostCurrent._listview1.getWidth()-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (50))));
 //BA.debugLineNum = 270;BA.debugLine="ListView1.TwoLinesAndBitmap.SecondLabel.Width = L";
mostCurrent._listview1.getTwoLinesAndBitmap().SecondLabel.setWidth((int) (mostCurrent._listview1.getWidth()-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (50))));
 //BA.debugLineNum = 271;BA.debugLine="ListView1.TwoLinesAndBitmap.Label.TextColor = Col";
mostCurrent._listview1.getTwoLinesAndBitmap().Label.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Gray);
 //BA.debugLineNum = 272;BA.debugLine="ListView1.TwoLinesAndBitmap.SecondLabel.TextColor";
mostCurrent._listview1.getTwoLinesAndBitmap().SecondLabel.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 273;BA.debugLine="ListView1.TwoLinesAndBitmap.Label.TextSize = 14";
mostCurrent._listview1.getTwoLinesAndBitmap().Label.setTextSize((float) (14));
 //BA.debugLineNum = 274;BA.debugLine="ListView1.TwoLinesAndBitmap.SecondLabel.TextSize";
mostCurrent._listview1.getTwoLinesAndBitmap().SecondLabel.setTextSize((float) (12));
 //BA.debugLineNum = 275;BA.debugLine="ListView1.TwoLinesAndBitmap.Label.left = 20dip";
mostCurrent._listview1.getTwoLinesAndBitmap().Label.setLeft(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (20)));
 //BA.debugLineNum = 276;BA.debugLine="ListView1.TwoLinesAndBitmap.ImageView.Left = List";
mostCurrent._listview1.getTwoLinesAndBitmap().ImageView.setLeft((int) (mostCurrent._listview1.getWidth()-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (50))));
 //BA.debugLineNum = 277;BA.debugLine="ListView1.TwoLinesAndBitmap.ImageView.Gravity = G";
mostCurrent._listview1.getTwoLinesAndBitmap().ImageView.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.CENTER);
 //BA.debugLineNum = 278;BA.debugLine="ListView1.TwoLinesAndBitmap.ImageView.Width = 20d";
mostCurrent._listview1.getTwoLinesAndBitmap().ImageView.setWidth(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (20)));
 //BA.debugLineNum = 279;BA.debugLine="ListView1.TwoLinesAndBitmap.ImageView.Height = 20";
mostCurrent._listview1.getTwoLinesAndBitmap().ImageView.setHeight(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (20)));
 //BA.debugLineNum = 280;BA.debugLine="ListView1.TwoLinesAndBitmap.ImageView.top = (List";
mostCurrent._listview1.getTwoLinesAndBitmap().ImageView.setTop((int) ((mostCurrent._listview1.getTwoLinesAndBitmap().getItemHeight()/(double)2)-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10))));
 //BA.debugLineNum = 285;BA.debugLine="Dim listmaps As List";
_listmaps = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 286;BA.debugLine="listmaps.Initialize";
_listmaps.Initialize();
 //BA.debugLineNum = 287;BA.debugLine="listmaps = DBUtils.ExecuteListOfMaps(Starter.sqlD";
_listmaps = mostCurrent._dbutils._executelistofmaps /*anywheresoftware.b4a.objects.collections.List*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"SELECT * FROM evals WHERE usuario=?",new String[]{mostCurrent._main._username /*String*/ },(int) (0));
 //BA.debugLineNum = 289;BA.debugLine="If listmaps = Null Or listmaps.IsInitialized = Fa";
if (_listmaps== null || _listmaps.IsInitialized()==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 290;BA.debugLine="hayAnteriores = False";
_hayanteriores = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 291;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 292;BA.debugLine="ToastMessageShow(\"No tienes datos anteriores...";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("No tienes datos anteriores..."),anywheresoftware.b4a.keywords.Common.False);
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 294;BA.debugLine="ToastMessageShow(\"There are no previous reports";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("There are no previous reports..."),anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 296;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 297;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 298;BA.debugLine="Return";
if (true) return "";
 }else {
 //BA.debugLineNum = 300;BA.debugLine="If listmaps.Size = 0 Then";
if (_listmaps.getSize()==0) { 
 //BA.debugLineNum = 302;BA.debugLine="hayAnteriores = False";
_hayanteriores = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 303;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 304;BA.debugLine="ToastMessageShow(\"No tienes datos anteriores..";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("No tienes datos anteriores..."),anywheresoftware.b4a.keywords.Common.False);
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 306;BA.debugLine="ToastMessageShow(\"There are no previous report";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("There are no previous reports..."),anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 308;BA.debugLine="tabStripDatosAnteriores.ScrollTo(0,True)";
mostCurrent._tabstripdatosanteriores.ScrollTo((int) (0),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 309;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 310;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 }else {
 //BA.debugLineNum = 313;BA.debugLine="hayAnteriores = True";
_hayanteriores = anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 314;BA.debugLine="Try";
try { //BA.debugLineNum = 315;BA.debugLine="For r = 0 To listmaps.Size -1";
{
final int step42 = 1;
final int limit42 = (int) (_listmaps.getSize()-1);
_r = (int) (0) ;
for (;_r <= limit42 ;_r = _r + step42 ) {
 //BA.debugLineNum = 316;BA.debugLine="Dim rmap As Map";
_rmap = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 317;BA.debugLine="rmap = listmaps.Get(r)";
_rmap = (anywheresoftware.b4a.objects.collections.Map) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.collections.Map(), (anywheresoftware.b4a.objects.collections.Map.MyMap)(_listmaps.Get(_r)));
 //BA.debugLineNum = 318;BA.debugLine="Dim esent As String = rmap.Get(\"evalsent\")";
_esent = BA.ObjectToString(_rmap.Get((Object)("evalsent")));
 //BA.debugLineNum = 319;BA.debugLine="Dim latdato As String = rmap.Get(\"decimallati";
_latdato = BA.ObjectToString(_rmap.Get((Object)("decimallatitude")));
 //BA.debugLineNum = 320;BA.debugLine="Dim lngdato As String = rmap.Get(\"decimallong";
_lngdato = BA.ObjectToString(_rmap.Get((Object)("decimallongitude")));
 //BA.debugLineNum = 321;BA.debugLine="Dim fechadato As String = rmap.Get(\"georefere";
_fechadato = BA.ObjectToString(_rmap.Get((Object)("georeferenceddate")));
 //BA.debugLineNum = 322;BA.debugLine="Dim iddato As String = rmap.Get(\"id\")";
_iddato = BA.ObjectToString(_rmap.Get((Object)("id")));
 //BA.debugLineNum = 324;BA.debugLine="If fechadato = \"\" Or fechadato = \"null\" Then";
if ((_fechadato).equals("") || (_fechadato).equals("null")) { 
 //BA.debugLineNum = 325;BA.debugLine="fechadato = \"Sin fecha\"";
_fechadato = "Sin fecha";
 };
 //BA.debugLineNum = 327;BA.debugLine="If latdato = \"null\" Or latdato = \"\" Then";
if ((_latdato).equals("null") || (_latdato).equals("")) { 
 //BA.debugLineNum = 328;BA.debugLine="latdato = \"Sin latitud\"";
_latdato = "Sin latitud";
 };
 //BA.debugLineNum = 330;BA.debugLine="If lngdato = \"null\"  Or lngdato = \"\" Then";
if ((_lngdato).equals("null") || (_lngdato).equals("")) { 
 //BA.debugLineNum = 331;BA.debugLine="lngdato = \"Sin longitud\"";
_lngdato = "Sin longitud";
 };
 //BA.debugLineNum = 333;BA.debugLine="If esent = \"si\" Then";
if ((_esent).equals("si")) { 
 //BA.debugLineNum = 335;BA.debugLine="ListView1.AddTwoLinesAndBitmap2(\"Fecha: \" &";
mostCurrent._listview1.AddTwoLinesAndBitmap2(BA.ObjectToCharSequence("Fecha: "+_fechadato),BA.ObjectToCharSequence(_latdato+"/"+_lngdato),(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"check.png").getObject()),(Object)(_iddato));
 }else {
 //BA.debugLineNum = 338;BA.debugLine="ListView1.AddTwoLinesAndBitmap2(\"Fecha: \" &";
mostCurrent._listview1.AddTwoLinesAndBitmap2(BA.ObjectToCharSequence("Fecha: "+_fechadato),BA.ObjectToCharSequence(_latdato+"/"+_lngdato),(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"not_sent.png").getObject()),(Object)(_iddato));
 };
 }
};
 } 
       catch (Exception e66) {
			processBA.setLastException(e66); //BA.debugLineNum = 343;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 344;BA.debugLine="ToastMessageShow(\"Ha habido un error cargando";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Ha habido un error cargando sus datos anteriores, intente de nuevo!"),anywheresoftware.b4a.keywords.Common.False);
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 346;BA.debugLine="ToastMessageShow(\"There's been an error, plea";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("There's been an error, please try again!"),anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 348;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 349;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 };
 };
 };
 //BA.debugLineNum = 421;BA.debugLine="End Sub";
return "";
}
public static String  _listview1_itemclick(int _position,Object _value) throws Exception{
 //BA.debugLineNum = 423;BA.debugLine="Sub ListView1_ItemClick (Position As Int, Value As";
 //BA.debugLineNum = 425;BA.debugLine="currentDatoId = Value";
mostCurrent._currentdatoid = BA.ObjectToString(_value);
 //BA.debugLineNum = 426;BA.debugLine="VerDetalles(currentDatoId, False)";
_verdetalles(mostCurrent._currentdatoid,anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 427;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 6;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 7;BA.debugLine="Dim hc As OkHttpClient";
_hc = new anywheresoftware.b4h.okhttp.OkHttpClientWrapper();
 //BA.debugLineNum = 8;BA.debugLine="Dim out As OutputStream";
_out = new anywheresoftware.b4a.objects.streams.File.OutputStreamWrapper();
 //BA.debugLineNum = 9;BA.debugLine="Dim CC As ContentChooser";
_cc = new anywheresoftware.b4a.phone.Phone.ContentChooser();
 //BA.debugLineNum = 12;BA.debugLine="Dim nuevalatlng As String";
_nuevalatlng = "";
 //BA.debugLineNum = 15;BA.debugLine="Dim notificacion As Boolean";
_notificacion = false;
 //BA.debugLineNum = 16;BA.debugLine="Dim serverIdNum As String";
_serveridnum = "";
 //BA.debugLineNum = 17;BA.debugLine="Dim hayAnteriores As Boolean";
_hayanteriores = false;
 //BA.debugLineNum = 21;BA.debugLine="Dim Up1 As UploadFilePhp";
_up1 = new com.spinter.uploadfilephp.UploadFilePhp();
 //BA.debugLineNum = 26;BA.debugLine="Private foto1 As String";
_foto1 = "";
 //BA.debugLineNum = 27;BA.debugLine="Private foto2 As String";
_foto2 = "";
 //BA.debugLineNum = 28;BA.debugLine="Private foto3 As String";
_foto3 = "";
 //BA.debugLineNum = 29;BA.debugLine="Private foto4 As String";
_foto4 = "";
 //BA.debugLineNum = 30;BA.debugLine="Private foto5 As String";
_foto5 = "";
 //BA.debugLineNum = 31;BA.debugLine="End Sub";
return "";
}
public static String  _tabstripdatosanteriores_pageselected(int _position) throws Exception{
 //BA.debugLineNum = 236;BA.debugLine="Sub tabStripDatosAnteriores_PageSelected (Position";
 //BA.debugLineNum = 237;BA.debugLine="If Position = 0 Then";
if (_position==0) { 
 //BA.debugLineNum = 238;BA.debugLine="foto1Borrar.RemoveView";
mostCurrent._foto1borrar.RemoveView();
 //BA.debugLineNum = 239;BA.debugLine="foto2Borrar.RemoveView";
mostCurrent._foto2borrar.RemoveView();
 //BA.debugLineNum = 240;BA.debugLine="ListarDatos";
_listardatos();
 //BA.debugLineNum = 241;BA.debugLine="NewMarcador = True";
_newmarcador = anywheresoftware.b4a.keywords.Common.True;
 }else if(_position==1) { 
 //BA.debugLineNum = 243;BA.debugLine="If hayAnteriores = False Then";
if (_hayanteriores==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 244;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 245;BA.debugLine="ToastMessageShow(\"No tienes datos anteriores..";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("No tienes datos anteriores..."),anywheresoftware.b4a.keywords.Common.False);
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 247;BA.debugLine="ToastMessageShow(\"There are no previous report";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("There are no previous reports..."),anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 249;BA.debugLine="tabStripDatosAnteriores.ScrollTo(0,True)";
mostCurrent._tabstripdatosanteriores.ScrollTo((int) (0),anywheresoftware.b4a.keywords.Common.True);
 };
 };
 //BA.debugLineNum = 252;BA.debugLine="End Sub";
return "";
}
public static String  _testinternet_complete(ilpla.appear.httpjob _job) throws Exception{
 //BA.debugLineNum = 1127;BA.debugLine="Sub TestInternet_Complete(Job As HttpJob)";
 //BA.debugLineNum = 1128;BA.debugLine="Log(\"Chequeo de internet: \" & Job.Success)";
anywheresoftware.b4a.keywords.Common.LogImpl("214680065","Chequeo de internet: "+BA.ObjectToString(_job._success /*boolean*/ ),0);
 //BA.debugLineNum = 1129;BA.debugLine="If Job.Success = True Then";
if (_job._success /*boolean*/ ==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 1131;BA.debugLine="Main.modooffline = False";
mostCurrent._main._modooffline /*boolean*/  = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 1132;BA.debugLine="Job.Release";
_job._release /*String*/ ();
 //BA.debugLineNum = 1134;BA.debugLine="If hubocambiosDatos = True Then";
if (_hubocambiosdatos==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 1135;BA.debugLine="EnviarDatos(IdProyectoEnviar)";
_enviardatos(mostCurrent._idproyectoenviar);
 };
 }else {
 //BA.debugLineNum = 1140;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 1141;BA.debugLine="MsgboxAsync(\"No hay conexión a internet, prueba";
anywheresoftware.b4a.keywords.Common.MsgboxAsync(BA.ObjectToCharSequence("No hay conexión a internet, prueba cuando estés conectado!"),BA.ObjectToCharSequence("No hay internet"),processBA);
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 1143;BA.debugLine="MsgboxAsync(\"There is no internet, try again wh";
anywheresoftware.b4a.keywords.Common.MsgboxAsync(BA.ObjectToCharSequence("There is no internet, try again when you're connected!"),BA.ObjectToCharSequence("No internet"),processBA);
 };
 //BA.debugLineNum = 1145;BA.debugLine="Main.modooffline = True";
mostCurrent._main._modooffline /*boolean*/  = anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 1146;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 //BA.debugLineNum = 1147;BA.debugLine="Job.Release";
_job._release /*String*/ ();
 //BA.debugLineNum = 1148;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 1149;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 };
 //BA.debugLineNum = 1151;BA.debugLine="End Sub";
return "";
}
public static String  _timerenvio_tick() throws Exception{
anywheresoftware.b4a.objects.collections.Map _map1 = null;
 //BA.debugLineNum = 1492;BA.debugLine="Sub TimerEnvio_Tick";
 //BA.debugLineNum = 1493;BA.debugLine="If fotosEnviadas = totalFotos Then";
if (_fotosenviadas==_totalfotos) { 
 //BA.debugLineNum = 1494;BA.debugLine="Log(\"TODAS LAS FOTOS FUERON ENVIADAS CORRECTAMEN";
anywheresoftware.b4a.keywords.Common.LogImpl("215007746","TODAS LAS FOTOS FUERON ENVIADAS CORRECTAMENTE",0);
 //BA.debugLineNum = 1495;BA.debugLine="Up1.UploadKill";
_up1.UploadKill(processBA);
 //BA.debugLineNum = 1500;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 };
 //BA.debugLineNum = 1505;BA.debugLine="ToastMessageShow(\"Evaluación enviada\", False)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Evaluación enviada"),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1507;BA.debugLine="Dim Map1 As Map";
_map1 = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 1508;BA.debugLine="Map1.Initialize";
_map1.Initialize();
 //BA.debugLineNum = 1509;BA.debugLine="Map1.Put(\"Id\", IdProyectoEnviar)";
_map1.Put((Object)("Id"),(Object)(mostCurrent._idproyectoenviar));
 //BA.debugLineNum = 1510;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"evals\", \"fo";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals","foto1sent",(Object)("si"),_map1);
 //BA.debugLineNum = 1511;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"evals\", \"fo";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals","foto2sent",(Object)("si"),_map1);
 //BA.debugLineNum = 1512;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"evals\", \"fo";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals","foto3sent",(Object)("si"),_map1);
 //BA.debugLineNum = 1513;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"evals\", \"fo";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals","foto4sent",(Object)("si"),_map1);
 //BA.debugLineNum = 1514;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"evals\", \"fo";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals","foto5sent",(Object)("si"),_map1);
 //BA.debugLineNum = 1515;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 1516;BA.debugLine="ToastMessageShow(\"Evaluación enviada\", False)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Evaluación enviada"),anywheresoftware.b4a.keywords.Common.False);
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 1518;BA.debugLine="ToastMessageShow(\"Report sent\", False)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Report sent"),anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 1521;BA.debugLine="TimerEnvio.Enabled = False";
mostCurrent._timerenvio.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1522;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 1523;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 };
 //BA.debugLineNum = 1526;BA.debugLine="End Sub";
return "";
}
public static String  _translategui() throws Exception{
 //BA.debugLineNum = 207;BA.debugLine="Sub TranslateGUI";
 //BA.debugLineNum = 208;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 209;BA.debugLine="Label6.Text = \" Fecha:\"";
mostCurrent._label6.setText(BA.ObjectToCharSequence(" Fecha:"));
 //BA.debugLineNum = 210;BA.debugLine="Label7.Text = \" Tipo:\"";
mostCurrent._label7.setText(BA.ObjectToCharSequence(" Tipo:"));
 //BA.debugLineNum = 211;BA.debugLine="Label9.Text = \" Calidad:\"";
mostCurrent._label9.setText(BA.ObjectToCharSequence(" Calidad:"));
 //BA.debugLineNum = 212;BA.debugLine="Label8.Text = \" Ubicación:\"";
mostCurrent._label8.setText(BA.ObjectToCharSequence(" Ubicación:"));
 //BA.debugLineNum = 213;BA.debugLine="Label10.Text = \" Notas:\"";
mostCurrent._label10.setText(BA.ObjectToCharSequence(" Notas:"));
 //BA.debugLineNum = 214;BA.debugLine="Label12.Text = \" Enviado:\"";
mostCurrent._label12.setText(BA.ObjectToCharSequence(" Enviado:"));
 //BA.debugLineNum = 215;BA.debugLine="Label3.Text = \"Enviado\"";
mostCurrent._label3.setText(BA.ObjectToCharSequence("Enviado"));
 //BA.debugLineNum = 216;BA.debugLine="Label2.Text = \"En línea\"";
mostCurrent._label2.setText(BA.ObjectToCharSequence("En línea"));
 //BA.debugLineNum = 217;BA.debugLine="Label13.Text = \"Verificado\"";
mostCurrent._label13.setText(BA.ObjectToCharSequence("Verificado"));
 //BA.debugLineNum = 218;BA.debugLine="btnCambiarPublico.Text = \"Cambiar a dato privado";
mostCurrent._btncambiarpublico.setText(BA.ObjectToCharSequence("Cambiar a dato privado"));
 //BA.debugLineNum = 219;BA.debugLine="btnEnviar.Text = \" Enviar\"";
mostCurrent._btnenviar.setText(BA.ObjectToCharSequence(" Enviar"));
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 221;BA.debugLine="Label6.Text = \" Date:\"";
mostCurrent._label6.setText(BA.ObjectToCharSequence(" Date:"));
 //BA.debugLineNum = 222;BA.debugLine="Label7.Text = \" Type:\"";
mostCurrent._label7.setText(BA.ObjectToCharSequence(" Type:"));
 //BA.debugLineNum = 223;BA.debugLine="Label9.Text = \" Quality:\"";
mostCurrent._label9.setText(BA.ObjectToCharSequence(" Quality:"));
 //BA.debugLineNum = 224;BA.debugLine="Label8.Text = \" Location:\"";
mostCurrent._label8.setText(BA.ObjectToCharSequence(" Location:"));
 //BA.debugLineNum = 225;BA.debugLine="Label10.Text = \" Notes:\"";
mostCurrent._label10.setText(BA.ObjectToCharSequence(" Notes:"));
 //BA.debugLineNum = 226;BA.debugLine="Label12.Text = \" Sent:\"";
mostCurrent._label12.setText(BA.ObjectToCharSequence(" Sent:"));
 //BA.debugLineNum = 227;BA.debugLine="Label3.Text = \"Sent\"";
mostCurrent._label3.setText(BA.ObjectToCharSequence("Sent"));
 //BA.debugLineNum = 228;BA.debugLine="Label2.Text = \"Online\"";
mostCurrent._label2.setText(BA.ObjectToCharSequence("Online"));
 //BA.debugLineNum = 229;BA.debugLine="Label13.Text = \"Verified\"";
mostCurrent._label13.setText(BA.ObjectToCharSequence("Verified"));
 //BA.debugLineNum = 230;BA.debugLine="btnEnviar.Text = \" Upload\"";
mostCurrent._btnenviar.setText(BA.ObjectToCharSequence(" Upload"));
 //BA.debugLineNum = 231;BA.debugLine="btnCambiarPublico.Text = \"Make report private\"";
mostCurrent._btncambiarpublico.setText(BA.ObjectToCharSequence("Make report private"));
 };
 //BA.debugLineNum = 233;BA.debugLine="End Sub";
return "";
}
public static String  _up1_sendfile(String _value) throws Exception{
 //BA.debugLineNum = 1533;BA.debugLine="Sub Up1_sendFile (value As String)";
 //BA.debugLineNum = 1534;BA.debugLine="Log(\"sendfile event:\" & value)";
anywheresoftware.b4a.keywords.Common.LogImpl("215138817","sendfile event:"+_value,0);
 //BA.debugLineNum = 1535;BA.debugLine="If value = \"success\" Then";
if ((_value).equals("success")) { 
 //BA.debugLineNum = 1538;BA.debugLine="If fotosEnviadas = 0 And totalFotos = 1 Then";
if (_fotosenviadas==0 && _totalfotos==1) { 
 //BA.debugLineNum = 1539;BA.debugLine="fotosEnviadas = 1";
_fotosenviadas = (int) (1);
 //BA.debugLineNum = 1540;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 1542;BA.debugLine="If fotosEnviadas = 1 And totalFotos = 2 Then";
if (_fotosenviadas==1 && _totalfotos==2) { 
 //BA.debugLineNum = 1543;BA.debugLine="fotosEnviadas = 2";
_fotosenviadas = (int) (2);
 //BA.debugLineNum = 1544;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 1546;BA.debugLine="If fotosEnviadas = 2 And totalFotos = 3 Then";
if (_fotosenviadas==2 && _totalfotos==3) { 
 //BA.debugLineNum = 1547;BA.debugLine="fotosEnviadas = 3";
_fotosenviadas = (int) (3);
 //BA.debugLineNum = 1548;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 1550;BA.debugLine="If fotosEnviadas = 3 And totalFotos = 4 Then";
if (_fotosenviadas==3 && _totalfotos==4) { 
 //BA.debugLineNum = 1551;BA.debugLine="fotosEnviadas = 4";
_fotosenviadas = (int) (4);
 //BA.debugLineNum = 1552;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 1554;BA.debugLine="If fotosEnviadas = 4 And totalFotos = 5 Then";
if (_fotosenviadas==4 && _totalfotos==5) { 
 //BA.debugLineNum = 1555;BA.debugLine="fotosEnviadas = 5";
_fotosenviadas = (int) (5);
 //BA.debugLineNum = 1556;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 1560;BA.debugLine="If fotosEnviadas = 0 And totalFotos > 1 Then";
if (_fotosenviadas==0 && _totalfotos>1) { 
 //BA.debugLineNum = 1561;BA.debugLine="fotosEnviadas = 1";
_fotosenviadas = (int) (1);
 //BA.debugLineNum = 1562;BA.debugLine="If File.Exists(File.DirRootExternal & \"/PreserV";
if (anywheresoftware.b4a.keywords.Common.File.Exists(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/PreserVamos/",_foto2+".jpg") && _foto2sent==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 1563;BA.debugLine="Log(\"Enviando foto 2 \")";
anywheresoftware.b4a.keywords.Common.LogImpl("215138846","Enviando foto 2 ",0);
 //BA.debugLineNum = 1564;BA.debugLine="ProgressBar2.Progress  = 0";
mostCurrent._progressbar2.setProgress((int) (0));
 //BA.debugLineNum = 1565;BA.debugLine="Up1.doFileUpload(ProgressBar2,Null,File.DirRoo";
_up1.doFileUpload(processBA,(android.widget.ProgressBar)(mostCurrent._progressbar2.getObject()),(android.widget.TextView)(anywheresoftware.b4a.keywords.Common.Null),anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/PreserVamos/"+_foto2+".jpg",mostCurrent._main._serverpath /*String*/ +"/"+mostCurrent._main._serverconnectionfolder /*String*/ +"/upload_file.php");
 }else {
 //BA.debugLineNum = 1567;BA.debugLine="Log(\"no foto 2\")";
anywheresoftware.b4a.keywords.Common.LogImpl("215138850","no foto 2",0);
 };
 };
 //BA.debugLineNum = 1570;BA.debugLine="If fotosEnviadas = 1 And totalFotos > 2 Then";
if (_fotosenviadas==1 && _totalfotos>2) { 
 //BA.debugLineNum = 1571;BA.debugLine="fotosEnviadas = 2";
_fotosenviadas = (int) (2);
 //BA.debugLineNum = 1572;BA.debugLine="If File.Exists(File.DirRootExternal & \"/PreserV";
if (anywheresoftware.b4a.keywords.Common.File.Exists(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/PreserVamos/",_foto3+".jpg") && _foto3sent==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 1573;BA.debugLine="Log(\"Enviando foto 3 \")";
anywheresoftware.b4a.keywords.Common.LogImpl("215138856","Enviando foto 3 ",0);
 //BA.debugLineNum = 1574;BA.debugLine="ProgressBar3.Progress  = 0";
mostCurrent._progressbar3.setProgress((int) (0));
 //BA.debugLineNum = 1575;BA.debugLine="Up1.doFileUpload(ProgressBar3,Null,File.DirRoo";
_up1.doFileUpload(processBA,(android.widget.ProgressBar)(mostCurrent._progressbar3.getObject()),(android.widget.TextView)(anywheresoftware.b4a.keywords.Common.Null),anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/PreserVamos/"+_foto3+".jpg",mostCurrent._main._serverpath /*String*/ +"/"+mostCurrent._main._serverconnectionfolder /*String*/ +"/upload_file.php");
 }else {
 //BA.debugLineNum = 1577;BA.debugLine="Log(\"no foto 3\")";
anywheresoftware.b4a.keywords.Common.LogImpl("215138860","no foto 3",0);
 };
 };
 //BA.debugLineNum = 1580;BA.debugLine="If fotosEnviadas = 2 And totalFotos > 3 Then";
if (_fotosenviadas==2 && _totalfotos>3) { 
 //BA.debugLineNum = 1581;BA.debugLine="fotosEnviadas = 3";
_fotosenviadas = (int) (3);
 //BA.debugLineNum = 1582;BA.debugLine="If File.Exists(File.DirRootExternal & \"/PreserV";
if (anywheresoftware.b4a.keywords.Common.File.Exists(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/PreserVamos/",_foto4+".jpg") && _foto4sent==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 1583;BA.debugLine="Log(\"Enviando foto 4 \")";
anywheresoftware.b4a.keywords.Common.LogImpl("215138866","Enviando foto 4 ",0);
 //BA.debugLineNum = 1584;BA.debugLine="ProgressBar4.Progress  = 0";
mostCurrent._progressbar4.setProgress((int) (0));
 //BA.debugLineNum = 1585;BA.debugLine="Up1.doFileUpload(ProgressBar4,Null,File.DirRoo";
_up1.doFileUpload(processBA,(android.widget.ProgressBar)(mostCurrent._progressbar4.getObject()),(android.widget.TextView)(anywheresoftware.b4a.keywords.Common.Null),anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/PreserVamos/"+_foto4+".jpg",mostCurrent._main._serverpath /*String*/ +"/"+mostCurrent._main._serverconnectionfolder /*String*/ +"/upload_file.php");
 }else {
 //BA.debugLineNum = 1587;BA.debugLine="Log(\"no foto 4\")";
anywheresoftware.b4a.keywords.Common.LogImpl("215138870","no foto 4",0);
 };
 };
 //BA.debugLineNum = 1590;BA.debugLine="If fotosEnviadas = 3 And totalFotos > 4 Then";
if (_fotosenviadas==3 && _totalfotos>4) { 
 //BA.debugLineNum = 1591;BA.debugLine="fotosEnviadas = 4";
_fotosenviadas = (int) (4);
 //BA.debugLineNum = 1592;BA.debugLine="If File.Exists(File.DirRootExternal & \"/PreserV";
if (anywheresoftware.b4a.keywords.Common.File.Exists(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/PreserVamos/",_foto5+".jpg") && _foto5sent==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 1593;BA.debugLine="Log(\"Enviando foto 5 \")";
anywheresoftware.b4a.keywords.Common.LogImpl("215138876","Enviando foto 5 ",0);
 //BA.debugLineNum = 1594;BA.debugLine="ProgressBar5.Progress  = 0";
mostCurrent._progressbar5.setProgress((int) (0));
 //BA.debugLineNum = 1595;BA.debugLine="Up1.doFileUpload(ProgressBar5,Null,File.DirRoo";
_up1.doFileUpload(processBA,(android.widget.ProgressBar)(mostCurrent._progressbar5.getObject()),(android.widget.TextView)(anywheresoftware.b4a.keywords.Common.Null),anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/PreserVamos/"+_foto5+".jpg",mostCurrent._main._serverpath /*String*/ +"/"+mostCurrent._main._serverconnectionfolder /*String*/ +"/upload_file.php");
 }else {
 //BA.debugLineNum = 1597;BA.debugLine="Log(\"no foto 5\")";
anywheresoftware.b4a.keywords.Common.LogImpl("215138880","no foto 5",0);
 };
 };
 }else if((_value).equals("Error!")) { 
 //BA.debugLineNum = 1630;BA.debugLine="Log(\"FOTO error\")";
anywheresoftware.b4a.keywords.Common.LogImpl("215138913","FOTO error",0);
 //BA.debugLineNum = 1631;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 1632;BA.debugLine="MsgboxAsync(\"Ha habido un error en el envío. Re";
anywheresoftware.b4a.keywords.Common.MsgboxAsync(BA.ObjectToCharSequence("Ha habido un error en el envío. Revisa tu conexión a Internet e intenta de nuevo desde 'Datos Anteriores'"),BA.ObjectToCharSequence("Oops!"),processBA);
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 1634;BA.debugLine="MsgboxAsync(\"There has been an error during the";
anywheresoftware.b4a.keywords.Common.MsgboxAsync(BA.ObjectToCharSequence("There has been an error during the upload. Check your internet connection and try again"),BA.ObjectToCharSequence("Oops!"),processBA);
 };
 //BA.debugLineNum = 1636;BA.debugLine="Up1.UploadKill";
_up1.UploadKill(processBA);
 //BA.debugLineNum = 1641;BA.debugLine="TimerEnvio.Enabled = False";
mostCurrent._timerenvio.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1642;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 1643;BA.debugLine="Activity_Create(False)";
_activity_create(anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 1646;BA.debugLine="End Sub";
return "";
}
public static String  _up1_statusupload(String _value) throws Exception{
 //BA.debugLineNum = 1529;BA.debugLine="Sub Up1_statusUpload (value As String)";
 //BA.debugLineNum = 1531;BA.debugLine="End Sub";
return "";
}
public static String  _up1_statusvisible(boolean _onoff,String _value) throws Exception{
 //BA.debugLineNum = 1647;BA.debugLine="Sub Up1_statusVISIBLE (onoff As Boolean,value As S";
 //BA.debugLineNum = 1649;BA.debugLine="End Sub";
return "";
}
public static String  _verdetalles(String _datoid,boolean _serverid) throws Exception{
anywheresoftware.b4a.objects.collections.Map _datomap = null;
String _datoenviado = "";
String _datorevisado = "";
String _datoprivado = "";
String _foto1path = "";
String _foto2path = "";
String _foto3path = "";
String _foto4path = "";
String _foto5path = "";
anywheresoftware.b4a.objects.drawable.ColorDrawable _cd = null;
anywheresoftware.b4a.objects.LabelWrapper _cdplus1 = null;
anywheresoftware.b4a.objects.LabelWrapper _cdplus2 = null;
 //BA.debugLineNum = 430;BA.debugLine="Sub VerDetalles(datoID As String, serverId As Bool";
 //BA.debugLineNum = 432;BA.debugLine="Dim datoMap As Map";
_datomap = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 433;BA.debugLine="datoMap.Initialize";
_datomap.Initialize();
 //BA.debugLineNum = 434;BA.debugLine="If serverId = False Then";
if (_serverid==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 435;BA.debugLine="datoMap = DBUtils.ExecuteMap(Starter.sqlDB, \"SEL";
_datomap = mostCurrent._dbutils._executemap /*anywheresoftware.b4a.objects.collections.Map*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"SELECT * FROM evals WHERE Id=?",new String[]{_datoid});
 }else {
 //BA.debugLineNum = 437;BA.debugLine="datoMap = DBUtils.ExecuteMap(Starter.sqlDB, \"SEL";
_datomap = mostCurrent._dbutils._executemap /*anywheresoftware.b4a.objects.collections.Map*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"SELECT * FROM evals WHERE serverid=?",new String[]{_datoid});
 //BA.debugLineNum = 438;BA.debugLine="hayAnteriores = True";
_hayanteriores = anywheresoftware.b4a.keywords.Common.True;
 };
 //BA.debugLineNum = 442;BA.debugLine="Dim datoenviado As String";
_datoenviado = "";
 //BA.debugLineNum = 443;BA.debugLine="datoenviado = datoMap.Get(\"evalsent\")";
_datoenviado = BA.ObjectToString(_datomap.Get((Object)("evalsent")));
 //BA.debugLineNum = 444;BA.debugLine="If datoenviado = \"si\" Then";
if ((_datoenviado).equals("si")) { 
 //BA.debugLineNum = 445;BA.debugLine="chkEnviado.Visible = True";
mostCurrent._chkenviado.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 446;BA.debugLine="btnEnviar.Visible = False";
mostCurrent._btnenviar.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 447;BA.debugLine="chkEnviado.Checked = True";
mostCurrent._chkenviado.setChecked(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 448;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 449;BA.debugLine="chkEnviado.Text = \"Enviado\"";
mostCurrent._chkenviado.setText(BA.ObjectToCharSequence("Enviado"));
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 451;BA.debugLine="chkEnviado.Text = \"Sent\"";
mostCurrent._chkenviado.setText(BA.ObjectToCharSequence("Sent"));
 };
 //BA.debugLineNum = 453;BA.debugLine="lblBarra.Visible = True";
mostCurrent._lblbarra.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 454;BA.debugLine="lblBarra.Width = chkOnline.Left + chkOnline.Widt";
mostCurrent._lblbarra.setWidth((int) (mostCurrent._chkonline.getLeft()+mostCurrent._chkonline.getWidth()-mostCurrent._chkenviadobar.getLeft()));
 //BA.debugLineNum = 455;BA.debugLine="Label9.Color = Colors.ARGB(255,247,150,71)";
mostCurrent._label9.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (247),(int) (150),(int) (71)));
 }else {
 //BA.debugLineNum = 457;BA.debugLine="Label9.Color = Colors.ARGB(0,247,150,71)";
mostCurrent._label9.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (247),(int) (150),(int) (71)));
 //BA.debugLineNum = 458;BA.debugLine="lblBarra.Visible = False";
mostCurrent._lblbarra.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 459;BA.debugLine="btnEnviar.Visible = True";
mostCurrent._btnenviar.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 460;BA.debugLine="chkEnviado.Visible = False";
mostCurrent._chkenviado.setVisible(anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 463;BA.debugLine="If datoMap = Null Or datoMap.IsInitialized = Fals";
if (_datomap== null || _datomap.IsInitialized()==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 464;BA.debugLine="tabStripDatosAnteriores.ScrollTo(0,True)";
mostCurrent._tabstripdatosanteriores.ScrollTo((int) (0),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 465;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 466;BA.debugLine="ToastMessageShow(\"Dato no encontrado...\", False";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Dato no encontrado..."),anywheresoftware.b4a.keywords.Common.False);
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 468;BA.debugLine="ToastMessageShow(\"Report not found...\", False)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Report not found..."),anywheresoftware.b4a.keywords.Common.False);
 };
 }else {
 //BA.debugLineNum = 472;BA.debugLine="IdProyectoEnviar = datoID";
mostCurrent._idproyectoenviar = _datoid;
 //BA.debugLineNum = 474;BA.debugLine="lblNombreEnvio.Text = datoMap.Get(\"georeferenced";
mostCurrent._lblnombreenvio.setText(BA.ObjectToCharSequence(_datomap.Get((Object)("georeferenceddate"))));
 //BA.debugLineNum = 475;BA.debugLine="lblFecha.Text = datoMap.Get(\"georeferenceddate\")";
mostCurrent._lblfecha.setText(BA.ObjectToCharSequence(_datomap.Get((Object)("georeferenceddate"))));
 //BA.debugLineNum = 476;BA.debugLine="lblCalidad.Text = datoMap.Get(\"valorcalidad\")";
mostCurrent._lblcalidad.setText(BA.ObjectToCharSequence(_datomap.Get((Object)("valorcalidad"))));
 //BA.debugLineNum = 477;BA.debugLine="lblNotas.Text = datoMap.Get(\"notas\")";
mostCurrent._lblnotas.setText(BA.ObjectToCharSequence(_datomap.Get((Object)("notas"))));
 //BA.debugLineNum = 478;BA.debugLine="lblLat.Text = datoMap.Get(\"decimallatitude\")";
mostCurrent._lbllat.setText(BA.ObjectToCharSequence(_datomap.Get((Object)("decimallatitude"))));
 //BA.debugLineNum = 479;BA.debugLine="lblLng.Text = datoMap.Get(\"decimallongitude\")";
mostCurrent._lbllng.setText(BA.ObjectToCharSequence(_datomap.Get((Object)("decimallongitude"))));
 //BA.debugLineNum = 480;BA.debugLine="lblTipo.Text = datoMap.Get(\"tiporio\")";
mostCurrent._lbltipo.setText(BA.ObjectToCharSequence(_datomap.Get((Object)("tiporio"))));
 //BA.debugLineNum = 482;BA.debugLine="lblFecha.TextColor = Colors.Black";
mostCurrent._lblfecha.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 483;BA.debugLine="lblCalidad.TextColor = Colors.Black";
mostCurrent._lblcalidad.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 484;BA.debugLine="lblLat.TextColor = Colors.Black";
mostCurrent._lbllat.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 485;BA.debugLine="lblLng.TextColor = Colors.Black";
mostCurrent._lbllng.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 486;BA.debugLine="lblTipo.TextColor = Colors.Black";
mostCurrent._lbltipo.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 488;BA.debugLine="If lblNombreEnvio.Text = \"null\" Then";
if ((mostCurrent._lblnombreenvio.getText()).equals("null")) { 
 //BA.debugLineNum = 489;BA.debugLine="lblNombreEnvio.Text = \"Sin nombre\"";
mostCurrent._lblnombreenvio.setText(BA.ObjectToCharSequence("Sin nombre"));
 };
 //BA.debugLineNum = 491;BA.debugLine="If lblFecha.Text = \"null\" Then";
if ((mostCurrent._lblfecha.getText()).equals("null")) { 
 //BA.debugLineNum = 492;BA.debugLine="lblFecha.Text = \"Sin fecha\"";
mostCurrent._lblfecha.setText(BA.ObjectToCharSequence("Sin fecha"));
 //BA.debugLineNum = 493;BA.debugLine="lblFecha.TextColor = Colors.red";
mostCurrent._lblfecha.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Red);
 };
 //BA.debugLineNum = 495;BA.debugLine="If lblCalidad.Text = \"null\" Then";
if ((mostCurrent._lblcalidad.getText()).equals("null")) { 
 //BA.debugLineNum = 496;BA.debugLine="lblCalidad.Text = \"Calidad no establecida\"";
mostCurrent._lblcalidad.setText(BA.ObjectToCharSequence("Calidad no establecida"));
 //BA.debugLineNum = 497;BA.debugLine="lblCalidad.TextColor = Colors.red";
mostCurrent._lblcalidad.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Red);
 };
 //BA.debugLineNum = 499;BA.debugLine="If lblNotas.Text = \"null\" Then";
if ((mostCurrent._lblnotas.getText()).equals("null")) { 
 //BA.debugLineNum = 500;BA.debugLine="lblNotas.Text = \"Sin notas\"";
mostCurrent._lblnotas.setText(BA.ObjectToCharSequence("Sin notas"));
 };
 //BA.debugLineNum = 502;BA.debugLine="If lblLat.Text = \"null\" Then";
if ((mostCurrent._lbllat.getText()).equals("null")) { 
 //BA.debugLineNum = 503;BA.debugLine="lblLat.Text = \"Sin latitud\"";
mostCurrent._lbllat.setText(BA.ObjectToCharSequence("Sin latitud"));
 //BA.debugLineNum = 504;BA.debugLine="lblLat.TextColor = Colors.red";
mostCurrent._lbllat.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Red);
 };
 //BA.debugLineNum = 506;BA.debugLine="If lblLng.Text = \"null\" Then";
if ((mostCurrent._lbllng.getText()).equals("null")) { 
 //BA.debugLineNum = 507;BA.debugLine="lblLng.Text = \"Sin longitud\"";
mostCurrent._lbllng.setText(BA.ObjectToCharSequence("Sin longitud"));
 //BA.debugLineNum = 508;BA.debugLine="lblLng.TextColor = Colors.red";
mostCurrent._lbllng.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Red);
 };
 //BA.debugLineNum = 510;BA.debugLine="If lblTipo.Text = \"null\" Then";
if ((mostCurrent._lbltipo.getText()).equals("null")) { 
 //BA.debugLineNum = 511;BA.debugLine="lblTipo.Text = \"Tipo no establecido\"";
mostCurrent._lbltipo.setText(BA.ObjectToCharSequence("Tipo no establecido"));
 //BA.debugLineNum = 512;BA.debugLine="lblTipo.TextColor = Colors.red";
mostCurrent._lbltipo.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Red);
 };
 //BA.debugLineNum = 515;BA.debugLine="Dim datorevisado As String";
_datorevisado = "";
 //BA.debugLineNum = 516;BA.debugLine="datorevisado = datoMap.Get(\"estadovalidacion\")";
_datorevisado = BA.ObjectToString(_datomap.Get((Object)("estadovalidacion")));
 //BA.debugLineNum = 517;BA.debugLine="If datorevisado = \"validado\" Then";
if ((_datorevisado).equals("validado")) { 
 //BA.debugLineNum = 518;BA.debugLine="chkEnviado.Visible = True";
mostCurrent._chkenviado.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 519;BA.debugLine="btnEnviar.Visible = False";
mostCurrent._btnenviar.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 520;BA.debugLine="chkEnviado.Checked = True";
mostCurrent._chkenviado.setChecked(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 521;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 522;BA.debugLine="chkEnviado.Text = \"Enviado\"";
mostCurrent._chkenviado.setText(BA.ObjectToCharSequence("Enviado"));
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 524;BA.debugLine="chkEnviado.Text = \"Sent\"";
mostCurrent._chkenviado.setText(BA.ObjectToCharSequence("Sent"));
 };
 //BA.debugLineNum = 526;BA.debugLine="lblBarra.Visible = True";
mostCurrent._lblbarra.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 527;BA.debugLine="lblBarra.Width = chkValidadoExpertos.Left + chk";
mostCurrent._lblbarra.setWidth((int) (mostCurrent._chkvalidadoexpertos.getLeft()+mostCurrent._chkvalidadoexpertos.getWidth()-mostCurrent._chkenviadobar.getLeft()));
 };
 //BA.debugLineNum = 532;BA.debugLine="Dim datoprivado As String";
_datoprivado = "";
 //BA.debugLineNum = 533;BA.debugLine="datoprivado = datoMap.Get(\"privado\")";
_datoprivado = BA.ObjectToString(_datomap.Get((Object)("privado")));
 //BA.debugLineNum = 534;BA.debugLine="If datoprivado = \"no\" Or datoprivado = \"\" Or dat";
if ((_datoprivado).equals("no") || (_datoprivado).equals("") || (_datoprivado).equals("null")) { 
 //BA.debugLineNum = 535;BA.debugLine="chkPublico.Checked = True";
mostCurrent._chkpublico.setChecked(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 536;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 537;BA.debugLine="chkPublico.Text = \"Dato público\"";
mostCurrent._chkpublico.setText(BA.ObjectToCharSequence("Dato público"));
 //BA.debugLineNum = 538;BA.debugLine="btnCambiarPublico.Text = \"Cambiar a dato priva";
mostCurrent._btncambiarpublico.setText(BA.ObjectToCharSequence("Cambiar a dato privado"));
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 540;BA.debugLine="chkPublico.Text = \"Public report\"";
mostCurrent._chkpublico.setText(BA.ObjectToCharSequence("Public report"));
 //BA.debugLineNum = 541;BA.debugLine="btnCambiarPublico.Text = \"Make report private\"";
mostCurrent._btncambiarpublico.setText(BA.ObjectToCharSequence("Make report private"));
 };
 }else {
 //BA.debugLineNum = 544;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 545;BA.debugLine="chkPublico.Text = \"Dato privado\"";
mostCurrent._chkpublico.setText(BA.ObjectToCharSequence("Dato privado"));
 //BA.debugLineNum = 546;BA.debugLine="btnCambiarPublico.Text = \"Cambiar a dato públi";
mostCurrent._btncambiarpublico.setText(BA.ObjectToCharSequence("Cambiar a dato público"));
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 548;BA.debugLine="chkPublico.Text = \"Private report\"";
mostCurrent._chkpublico.setText(BA.ObjectToCharSequence("Private report"));
 //BA.debugLineNum = 549;BA.debugLine="btnCambiarPublico.Text = \"Make report public\"";
mostCurrent._btncambiarpublico.setText(BA.ObjectToCharSequence("Make report public"));
 };
 //BA.debugLineNum = 552;BA.debugLine="chkPublico.Checked = False";
mostCurrent._chkpublico.setChecked(anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 558;BA.debugLine="foto1view.Bitmap = Null";
mostCurrent._foto1view.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null));
 //BA.debugLineNum = 559;BA.debugLine="foto2view.Bitmap = Null";
mostCurrent._foto2view.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null));
 //BA.debugLineNum = 560;BA.debugLine="foto3view.Bitmap = Null";
mostCurrent._foto3view.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null));
 //BA.debugLineNum = 561;BA.debugLine="foto4view.Bitmap = Null";
mostCurrent._foto4view.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null));
 //BA.debugLineNum = 562;BA.debugLine="foto5view.Bitmap = Null";
mostCurrent._foto5view.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null));
 //BA.debugLineNum = 563;BA.debugLine="Dim foto1path As String";
_foto1path = "";
 //BA.debugLineNum = 564;BA.debugLine="Dim foto2path As String";
_foto2path = "";
 //BA.debugLineNum = 565;BA.debugLine="Dim foto3path As String";
_foto3path = "";
 //BA.debugLineNum = 566;BA.debugLine="Dim foto4path As String";
_foto4path = "";
 //BA.debugLineNum = 567;BA.debugLine="Dim foto5path As String";
_foto5path = "";
 //BA.debugLineNum = 568;BA.debugLine="foto1path = datoMap.Get(\"foto1\") & \".jpg\"";
_foto1path = BA.ObjectToString(_datomap.Get((Object)("foto1")))+".jpg";
 //BA.debugLineNum = 569;BA.debugLine="foto2path = datoMap.Get(\"foto2\") & \".jpg\"";
_foto2path = BA.ObjectToString(_datomap.Get((Object)("foto2")))+".jpg";
 //BA.debugLineNum = 570;BA.debugLine="foto3path = datoMap.Get(\"foto2\") & \".jpg\"";
_foto3path = BA.ObjectToString(_datomap.Get((Object)("foto2")))+".jpg";
 //BA.debugLineNum = 571;BA.debugLine="foto4path = datoMap.Get(\"foto4\") & \".jpg\"";
_foto4path = BA.ObjectToString(_datomap.Get((Object)("foto4")))+".jpg";
 //BA.debugLineNum = 572;BA.debugLine="foto5path = datoMap.Get(\"foto4\") & \".jpg\"";
_foto5path = BA.ObjectToString(_datomap.Get((Object)("foto4")))+".jpg";
 //BA.debugLineNum = 573;BA.debugLine="scvFotos.Panel.Invalidate";
mostCurrent._scvfotos.getPanel().Invalidate();
 //BA.debugLineNum = 576;BA.debugLine="foto1Borrar.Color = Colors.ARGB(150,255,255,255)";
mostCurrent._foto1borrar.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (150),(int) (255),(int) (255),(int) (255)));
 //BA.debugLineNum = 577;BA.debugLine="foto2Borrar.Color = Colors.ARGB(150,255,255,255)";
mostCurrent._foto2borrar.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (150),(int) (255),(int) (255),(int) (255)));
 //BA.debugLineNum = 578;BA.debugLine="foto3Borrar.Color = Colors.ARGB(150,255,255,255)";
mostCurrent._foto3borrar.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (150),(int) (255),(int) (255),(int) (255)));
 //BA.debugLineNum = 579;BA.debugLine="foto4Borrar.Color = Colors.ARGB(150,255,255,255)";
mostCurrent._foto4borrar.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (150),(int) (255),(int) (255),(int) (255)));
 //BA.debugLineNum = 580;BA.debugLine="foto5Borrar.Color = Colors.ARGB(150,255,255,255)";
mostCurrent._foto5borrar.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (150),(int) (255),(int) (255),(int) (255)));
 //BA.debugLineNum = 581;BA.debugLine="foto1Borrar.Text = \"X Eliminar imagen\"";
mostCurrent._foto1borrar.setText(BA.ObjectToCharSequence("X Eliminar imagen"));
 //BA.debugLineNum = 582;BA.debugLine="foto2Borrar.Text = \"X Eliminar imagen\"";
mostCurrent._foto2borrar.setText(BA.ObjectToCharSequence("X Eliminar imagen"));
 //BA.debugLineNum = 583;BA.debugLine="foto3Borrar.Text = \"X Eliminar imagen\"";
mostCurrent._foto3borrar.setText(BA.ObjectToCharSequence("X Eliminar imagen"));
 //BA.debugLineNum = 584;BA.debugLine="foto4Borrar.Text = \"X Eliminar imagen\"";
mostCurrent._foto4borrar.setText(BA.ObjectToCharSequence("X Eliminar imagen"));
 //BA.debugLineNum = 585;BA.debugLine="foto5Borrar.Text = \"X Eliminar imagen\"";
mostCurrent._foto5borrar.setText(BA.ObjectToCharSequence("X Eliminar imagen"));
 //BA.debugLineNum = 586;BA.debugLine="foto1Borrar.TextColor = Colors.Black";
mostCurrent._foto1borrar.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 587;BA.debugLine="foto2Borrar.TextColor = Colors.Black";
mostCurrent._foto2borrar.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 588;BA.debugLine="foto4Borrar.TextColor = Colors.Black";
mostCurrent._foto4borrar.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 589;BA.debugLine="foto5Borrar.TextColor = Colors.Black";
mostCurrent._foto5borrar.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 592;BA.debugLine="Dim cd As ColorDrawable";
_cd = new anywheresoftware.b4a.objects.drawable.ColorDrawable();
 //BA.debugLineNum = 593;BA.debugLine="cd.Initialize2(Colors.Transparent,10dip,2dip,Col";
_cd.Initialize2(anywheresoftware.b4a.keywords.Common.Colors.Transparent,anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (2)),anywheresoftware.b4a.keywords.Common.Colors.LightGray);
 //BA.debugLineNum = 596;BA.debugLine="If File.Exists(File.DirRootExternal & \"/PreserVa";
if (anywheresoftware.b4a.keywords.Common.File.Exists(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/PreserVamos/",_foto1path)) { 
 //BA.debugLineNum = 597;BA.debugLine="foto1view.Bitmap= LoadBitmapSample(File.DirRoot";
mostCurrent._foto1view.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/PreserVamos/",_foto1path,anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 }else {
 //BA.debugLineNum = 599;BA.debugLine="foto1view.Background=cd";
mostCurrent._foto1view.setBackground((android.graphics.drawable.Drawable)(_cd.getObject()));
 //BA.debugLineNum = 600;BA.debugLine="Dim cdplus1 As Label";
_cdplus1 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 601;BA.debugLine="cdplus1.Initialize(\"\")";
_cdplus1.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 602;BA.debugLine="cdplus1.Text = \"+\"";
_cdplus1.setText(BA.ObjectToCharSequence("+"));
 //BA.debugLineNum = 603;BA.debugLine="cdplus1.TextSize = 48";
_cdplus1.setTextSize((float) (48));
 //BA.debugLineNum = 604;BA.debugLine="cdplus1.TextColor = Colors.LightGray";
_cdplus1.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.LightGray);
 //BA.debugLineNum = 605;BA.debugLine="cdplus1.Gravity = Bit.Or(Gravity.CENTER_VERTICA";
_cdplus1.setGravity(anywheresoftware.b4a.keywords.Common.Bit.Or(anywheresoftware.b4a.keywords.Common.Gravity.CENTER_VERTICAL,anywheresoftware.b4a.keywords.Common.Gravity.CENTER_HORIZONTAL));
 //BA.debugLineNum = 606;BA.debugLine="scvFotos.Panel.AddView(cdplus1, foto1view.Left,";
mostCurrent._scvfotos.getPanel().AddView((android.view.View)(_cdplus1.getObject()),mostCurrent._foto1view.getLeft(),mostCurrent._foto1view.getTop(),mostCurrent._foto1view.getWidth(),mostCurrent._foto1view.getHeight());
 };
 //BA.debugLineNum = 608;BA.debugLine="If File.Exists(File.DirRootExternal & \"/PreserVa";
if (anywheresoftware.b4a.keywords.Common.File.Exists(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/PreserVamos/",_foto2path)) { 
 //BA.debugLineNum = 609;BA.debugLine="foto2view.Bitmap= LoadBitmapSample(File.DirRoot";
mostCurrent._foto2view.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/PreserVamos/",_foto2path,anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (1005),mostCurrent.activityBA)).getObject()));
 }else {
 //BA.debugLineNum = 611;BA.debugLine="foto2view.Background=cd";
mostCurrent._foto2view.setBackground((android.graphics.drawable.Drawable)(_cd.getObject()));
 //BA.debugLineNum = 612;BA.debugLine="Dim cdplus2 As Label";
_cdplus2 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 613;BA.debugLine="cdplus2.Initialize(\"\")";
_cdplus2.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 614;BA.debugLine="cdplus2.Text = \"+\"";
_cdplus2.setText(BA.ObjectToCharSequence("+"));
 //BA.debugLineNum = 615;BA.debugLine="cdplus2.TextSize = 48";
_cdplus2.setTextSize((float) (48));
 //BA.debugLineNum = 616;BA.debugLine="cdplus2.TextColor = Colors.LightGray";
_cdplus2.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.LightGray);
 //BA.debugLineNum = 617;BA.debugLine="cdplus2.Gravity = Bit.Or(Gravity.CENTER_VERTICA";
_cdplus2.setGravity(anywheresoftware.b4a.keywords.Common.Bit.Or(anywheresoftware.b4a.keywords.Common.Gravity.CENTER_VERTICAL,anywheresoftware.b4a.keywords.Common.Gravity.CENTER_HORIZONTAL));
 //BA.debugLineNum = 618;BA.debugLine="scvFotos.Panel.AddView(cdplus2, foto2view.Left,";
mostCurrent._scvfotos.getPanel().AddView((android.view.View)(_cdplus2.getObject()),mostCurrent._foto2view.getLeft(),mostCurrent._foto2view.getTop(),mostCurrent._foto2view.getWidth(),mostCurrent._foto2view.getHeight());
 };
 //BA.debugLineNum = 620;BA.debugLine="If File.Exists(File.DirRootExternal & \"/PreserVa";
if (anywheresoftware.b4a.keywords.Common.File.Exists(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/PreserVamos/",_foto3path)) { 
 //BA.debugLineNum = 621;BA.debugLine="foto3view.Bitmap= LoadBitmapSample(File.DirRoot";
mostCurrent._foto3view.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/PreserVamos/",_foto3path,anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (1005),mostCurrent.activityBA)).getObject()));
 }else {
 //BA.debugLineNum = 623;BA.debugLine="foto3view.Background=cd";
mostCurrent._foto3view.setBackground((android.graphics.drawable.Drawable)(_cd.getObject()));
 //BA.debugLineNum = 624;BA.debugLine="Dim cdplus2 As Label";
_cdplus2 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 625;BA.debugLine="cdplus2.Initialize(\"\")";
_cdplus2.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 626;BA.debugLine="cdplus2.Text = \"+\"";
_cdplus2.setText(BA.ObjectToCharSequence("+"));
 //BA.debugLineNum = 627;BA.debugLine="cdplus2.TextSize = 48";
_cdplus2.setTextSize((float) (48));
 //BA.debugLineNum = 628;BA.debugLine="cdplus2.TextColor = Colors.LightGray";
_cdplus2.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.LightGray);
 //BA.debugLineNum = 629;BA.debugLine="cdplus2.Gravity = Bit.Or(Gravity.CENTER_VERTICA";
_cdplus2.setGravity(anywheresoftware.b4a.keywords.Common.Bit.Or(anywheresoftware.b4a.keywords.Common.Gravity.CENTER_VERTICAL,anywheresoftware.b4a.keywords.Common.Gravity.CENTER_HORIZONTAL));
 //BA.debugLineNum = 630;BA.debugLine="scvFotos.Panel.AddView(cdplus2, foto3view.Left,";
mostCurrent._scvfotos.getPanel().AddView((android.view.View)(_cdplus2.getObject()),mostCurrent._foto3view.getLeft(),mostCurrent._foto3view.getTop(),mostCurrent._foto3view.getWidth(),mostCurrent._foto3view.getHeight());
 };
 //BA.debugLineNum = 632;BA.debugLine="If File.Exists(File.DirRootExternal & \"/PreserVa";
if (anywheresoftware.b4a.keywords.Common.File.Exists(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/PreserVamos/",_foto4path)) { 
 //BA.debugLineNum = 634;BA.debugLine="foto4view.Bitmap= LoadBitmapSample(File.DirRoot";
mostCurrent._foto4view.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/PreserVamos/",_foto4path,anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (1005),mostCurrent.activityBA)).getObject()));
 }else {
 //BA.debugLineNum = 636;BA.debugLine="foto4view.Background=cd";
mostCurrent._foto4view.setBackground((android.graphics.drawable.Drawable)(_cd.getObject()));
 //BA.debugLineNum = 637;BA.debugLine="Dim cdplus2 As Label";
_cdplus2 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 638;BA.debugLine="cdplus2.Initialize(\"\")";
_cdplus2.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 639;BA.debugLine="cdplus2.Text = \"+\"";
_cdplus2.setText(BA.ObjectToCharSequence("+"));
 //BA.debugLineNum = 640;BA.debugLine="cdplus2.TextSize = 48";
_cdplus2.setTextSize((float) (48));
 //BA.debugLineNum = 641;BA.debugLine="cdplus2.TextColor = Colors.LightGray";
_cdplus2.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.LightGray);
 //BA.debugLineNum = 642;BA.debugLine="cdplus2.Gravity = Bit.Or(Gravity.CENTER_VERTICA";
_cdplus2.setGravity(anywheresoftware.b4a.keywords.Common.Bit.Or(anywheresoftware.b4a.keywords.Common.Gravity.CENTER_VERTICAL,anywheresoftware.b4a.keywords.Common.Gravity.CENTER_HORIZONTAL));
 //BA.debugLineNum = 643;BA.debugLine="scvFotos.Panel.AddView(cdplus2, foto4view.Left,";
mostCurrent._scvfotos.getPanel().AddView((android.view.View)(_cdplus2.getObject()),mostCurrent._foto4view.getLeft(),mostCurrent._foto4view.getTop(),mostCurrent._foto4view.getWidth(),mostCurrent._foto4view.getHeight());
 };
 //BA.debugLineNum = 645;BA.debugLine="If File.Exists(File.DirRootExternal & \"/PreserVa";
if (anywheresoftware.b4a.keywords.Common.File.Exists(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/PreserVamos/",_foto5path)) { 
 //BA.debugLineNum = 646;BA.debugLine="foto5view.Bitmap= LoadBitmapSample(File.DirRoot";
mostCurrent._foto5view.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/PreserVamos/",_foto5path,anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (1005),mostCurrent.activityBA)).getObject()));
 }else {
 //BA.debugLineNum = 648;BA.debugLine="foto5view.Background=cd";
mostCurrent._foto5view.setBackground((android.graphics.drawable.Drawable)(_cd.getObject()));
 //BA.debugLineNum = 649;BA.debugLine="Dim cdplus2 As Label";
_cdplus2 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 650;BA.debugLine="cdplus2.Initialize(\"\")";
_cdplus2.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 651;BA.debugLine="cdplus2.Text = \"+\"";
_cdplus2.setText(BA.ObjectToCharSequence("+"));
 //BA.debugLineNum = 652;BA.debugLine="cdplus2.TextSize = 48";
_cdplus2.setTextSize((float) (48));
 //BA.debugLineNum = 653;BA.debugLine="cdplus2.TextColor = Colors.LightGray";
_cdplus2.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.LightGray);
 //BA.debugLineNum = 654;BA.debugLine="cdplus2.Gravity = Bit.Or(Gravity.CENTER_VERTICA";
_cdplus2.setGravity(anywheresoftware.b4a.keywords.Common.Bit.Or(anywheresoftware.b4a.keywords.Common.Gravity.CENTER_VERTICAL,anywheresoftware.b4a.keywords.Common.Gravity.CENTER_HORIZONTAL));
 //BA.debugLineNum = 655;BA.debugLine="scvFotos.Panel.AddView(cdplus2, foto5view.Left,";
mostCurrent._scvfotos.getPanel().AddView((android.view.View)(_cdplus2.getObject()),mostCurrent._foto5view.getLeft(),mostCurrent._foto5view.getTop(),mostCurrent._foto5view.getWidth(),mostCurrent._foto5view.getHeight());
 };
 };
 //BA.debugLineNum = 659;BA.debugLine="tabStripDatosAnteriores.ScrollTo(2,True)";
mostCurrent._tabstripdatosanteriores.ScrollTo((int) (2),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 660;BA.debugLine="tabActual = \"Detalle\"";
mostCurrent._tabactual = "Detalle";
 //BA.debugLineNum = 661;BA.debugLine="notificacion = False";
_notificacion = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 662;BA.debugLine="serverId  = False";
_serverid = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 664;BA.debugLine="End Sub";
return "";
}
}
