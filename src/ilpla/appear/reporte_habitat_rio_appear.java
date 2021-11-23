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

public class reporte_habitat_rio_appear extends Activity implements B4AActivity{
	public static reporte_habitat_rio_appear mostCurrent;
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
			processBA = new BA(this.getApplicationContext(), null, null, "ilpla.appear", "ilpla.appear.reporte_habitat_rio_appear");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
            
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (reporte_habitat_rio_appear).");
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
		activityBA = new BA(this, layout, processBA, "ilpla.appear", "ilpla.appear.reporte_habitat_rio_appear");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "ilpla.appear.reporte_habitat_rio_appear", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density, mostCurrent);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (reporte_habitat_rio_appear) Create, isFirst = " + isFirst + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (reporte_habitat_rio_appear) Resume **");
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
		return reporte_habitat_rio_appear.class;
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
            BA.LogInfo("** Activity (reporte_habitat_rio_appear) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
        else
            BA.LogInfo("** Activity (reporte_habitat_rio_appear) Pause event (activity is not paused). **");
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
            reporte_habitat_rio_appear mc = mostCurrent;
			if (mc == null || mc != activity.get())
				return;
			processBA.setActivityPaused(false);
            BA.LogInfo("** Activity (reporte_habitat_rio_appear) Resume **");
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
public static String _tiporio = "";
public anywheresoftware.b4a.objects.PanelWrapper _pnlquestions = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnpregunta1 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnpregunta2 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnpregunta3 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnpregunta4 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnpregunta5 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnpregunta6 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnpregunta7 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnpregunta8 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnpregunta9 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnpregunta10 = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblpregunta1 = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblpregunta2 = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblpregunta3 = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblpregunta4 = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblpregunta5 = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblpregunta6 = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblpregunta7 = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblpregunta8 = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblpregunta9 = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblpregunta10 = null;
public static String _minimagen = "";
public anywheresoftware.b4a.objects.ImageViewWrapper _minipregunta1 = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _minipregunta2 = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _minipregunta3 = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _minipregunta4 = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _minipregunta5 = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _minipregunta6 = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _minipregunta7 = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _minipregunta8 = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _minipregunta9 = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _minipregunta10 = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblpregunta = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnsiguiente = null;
public anywheresoftware.b4a.objects.PanelWrapper _pnlopciones = null;
public anywheresoftware.b4a.objects.PanelWrapper _pnlchecks = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper _rdopcion1 = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper _rdopcion2 = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper _rdopcion3 = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper _rdopcion4 = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper _rdopcion5 = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper _rdopcion6 = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper _chkopcion3 = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper _chkopcion2 = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper _chkopcion1 = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper _chkopcion4 = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper _chkopcion5 = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper _chkopcion6 = null;
public static String _imgej1 = "";
public anywheresoftware.b4a.objects.WebViewWrapper _explicacionviewer = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnrdopcion1 = null;
public anywheresoftware.b4a.objects.drawable.CanvasWrapper _csv = null;
public anywheresoftware.b4a.objects.drawable.CanvasWrapper.RectWrapper _rec = null;
public anywheresoftware.b4a.objects.drawable.CanvasWrapper _csvchk = null;
public anywheresoftware.b4a.objects.drawable.CanvasWrapper.RectWrapper _recchk = null;
public static int _pgbpreguntas = 0;
public static int _cantidadpreguntas = 0;
public static int _currentpregunta = 0;
public anywheresoftware.b4a.objects.PanelWrapper _pnlpreguntas = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblestado = null;
public anywheresoftware.b4a.objects.PanelWrapper _pnlresultados = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnterminar = null;
public anywheresoftware.b4a.objects.ScrollViewWrapper _scrresultados = null;
public anywheresoftware.b4a.objects.PanelWrapper _panel1 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btncerrar = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _minipreguntaentubado = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbltituloresultados = null;
public anywheresoftware.b4a.objects.LabelWrapper _label2 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnshare = null;
public static String _langlocal = "";
public static String _valorind1 = "";
public static String _valorind2 = "";
public static String _valorind3 = "";
public static String _valorind4 = "";
public static String _valorind5 = "";
public static String _valorind6 = "";
public static String _valorind7 = "";
public static String _valorind8 = "";
public static String _valorind9 = "";
public static String _valorind10 = "";
public static String _valorind11 = "";
public static String _valorind12 = "";
public static String _valorind13 = "";
public static String _valorind14 = "";
public static String _valorind15 = "";
public static String _valorind16 = "";
public static String _preguntanumero = "";
public static String _dateandtime = "";
public static int _valorcalidad = 0;
public static int _valorns = 0;
public anywheresoftware.b4a.objects.ButtonWrapper _btnmasdetalle = null;
public ilpla.appear.gauge _gauge1 = null;
public anywheresoftware.b4a.objects.B4XViewWrapper.XUI _xui = null;
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
public ilpla.appear.reporte_fotos _reporte_fotos = null;
public ilpla.appear.reporte_habitat_laguna _reporte_habitat_laguna = null;
public ilpla.appear.reporte_habitat_rio _reporte_habitat_rio = null;
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
 //BA.debugLineNum = 133;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
 //BA.debugLineNum = 134;BA.debugLine="Activity.LoadLayout(\"HabitatRio\")";
mostCurrent._activity.LoadLayout("HabitatRio",mostCurrent.activityBA);
 //BA.debugLineNum = 135;BA.debugLine="If pnlQuestions.IsInitialized = False Then";
if (mostCurrent._pnlquestions.IsInitialized()==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 136;BA.debugLine="pnlQuestions.Initialize(\"pnlQuestions\")";
mostCurrent._pnlquestions.Initialize(mostCurrent.activityBA,"pnlQuestions");
 };
 //BA.debugLineNum = 139;BA.debugLine="pnlQuestions.Visible = False";
mostCurrent._pnlquestions.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 140;BA.debugLine="pnlQuestions.LoadLayout(\"layPregunta\")";
mostCurrent._pnlquestions.LoadLayout("layPregunta",mostCurrent.activityBA);
 //BA.debugLineNum = 142;BA.debugLine="csv.Initialize(pnlOpciones)";
mostCurrent._csv.Initialize((android.view.View)(mostCurrent._pnlopciones.getObject()));
 //BA.debugLineNum = 143;BA.debugLine="csvChk.Initialize(pnlChecks)";
mostCurrent._csvchk.Initialize((android.view.View)(mostCurrent._pnlchecks.getObject()));
 //BA.debugLineNum = 149;BA.debugLine="preguntanumero = 1";
mostCurrent._preguntanumero = BA.NumberToString(1);
 //BA.debugLineNum = 150;BA.debugLine="valorind1 = \"\"";
mostCurrent._valorind1 = "";
 //BA.debugLineNum = 151;BA.debugLine="valorind2 = \"\"";
mostCurrent._valorind2 = "";
 //BA.debugLineNum = 152;BA.debugLine="valorind3 = \"\"";
mostCurrent._valorind3 = "";
 //BA.debugLineNum = 153;BA.debugLine="valorind4 = \"\"";
mostCurrent._valorind4 = "";
 //BA.debugLineNum = 154;BA.debugLine="valorind5 = \"\"";
mostCurrent._valorind5 = "";
 //BA.debugLineNum = 155;BA.debugLine="valorind6 = \"\"";
mostCurrent._valorind6 = "";
 //BA.debugLineNum = 156;BA.debugLine="valorind7 = \"\"";
mostCurrent._valorind7 = "";
 //BA.debugLineNum = 157;BA.debugLine="valorind8 = \"\"";
mostCurrent._valorind8 = "";
 //BA.debugLineNum = 158;BA.debugLine="valorind9 = \"\"";
mostCurrent._valorind9 = "";
 //BA.debugLineNum = 159;BA.debugLine="valorind10 = \"\"";
mostCurrent._valorind10 = "";
 //BA.debugLineNum = 160;BA.debugLine="valorind11 = \"\"";
mostCurrent._valorind11 = "";
 //BA.debugLineNum = 161;BA.debugLine="valorind12 = \"\"";
mostCurrent._valorind12 = "";
 //BA.debugLineNum = 162;BA.debugLine="valorind13 = \"\"";
mostCurrent._valorind13 = "";
 //BA.debugLineNum = 163;BA.debugLine="valorind14 = \"\"";
mostCurrent._valorind14 = "";
 //BA.debugLineNum = 164;BA.debugLine="valorind15 = \"\"";
mostCurrent._valorind15 = "";
 //BA.debugLineNum = 165;BA.debugLine="valorind16 = \"\"";
mostCurrent._valorind16 = "";
 //BA.debugLineNum = 166;BA.debugLine="miniPregunta1.SetBackgroundImage(Null)";
mostCurrent._minipregunta1.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null));
 //BA.debugLineNum = 167;BA.debugLine="miniPregunta2.SetBackgroundImage(Null)";
mostCurrent._minipregunta2.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null));
 //BA.debugLineNum = 168;BA.debugLine="miniPregunta3.SetBackgroundImage(Null)";
mostCurrent._minipregunta3.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null));
 //BA.debugLineNum = 169;BA.debugLine="miniPregunta4.SetBackgroundImage(Null)";
mostCurrent._minipregunta4.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null));
 //BA.debugLineNum = 170;BA.debugLine="miniPregunta5.SetBackgroundImage(Null)";
mostCurrent._minipregunta5.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null));
 //BA.debugLineNum = 171;BA.debugLine="miniPregunta6.SetBackgroundImage(Null)";
mostCurrent._minipregunta6.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null));
 //BA.debugLineNum = 172;BA.debugLine="miniPregunta7.SetBackgroundImage(Null)";
mostCurrent._minipregunta7.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null));
 //BA.debugLineNum = 173;BA.debugLine="miniPregunta8.SetBackgroundImage(Null)";
mostCurrent._minipregunta8.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null));
 //BA.debugLineNum = 174;BA.debugLine="miniPregunta9.SetBackgroundImage(Null)";
mostCurrent._minipregunta9.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null));
 //BA.debugLineNum = 175;BA.debugLine="miniPregunta10.SetBackgroundImage(Null)";
mostCurrent._minipregunta10.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null));
 //BA.debugLineNum = 176;BA.debugLine="miniPreguntaEntubado.SetBackgroundImage(Null)";
mostCurrent._minipreguntaentubado.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null));
 //BA.debugLineNum = 178;BA.debugLine="DateTime.DateFormat = \"dd-MM-yyyy\"";
anywheresoftware.b4a.keywords.Common.DateTime.setDateFormat("dd-MM-yyyy");
 //BA.debugLineNum = 179;BA.debugLine="DateTime.TimeFormat = \"HH:mm\"";
anywheresoftware.b4a.keywords.Common.DateTime.setTimeFormat("HH:mm");
 //BA.debugLineNum = 180;BA.debugLine="dateandtime = DateTime.Date(DateTime.now)";
mostCurrent._dateandtime = anywheresoftware.b4a.keywords.Common.DateTime.Date(anywheresoftware.b4a.keywords.Common.DateTime.getNow());
 //BA.debugLineNum = 181;BA.debugLine="valorcalidad = 0";
_valorcalidad = (int) (0);
 //BA.debugLineNum = 182;BA.debugLine="valorNS = 0";
_valorns = (int) (0);
 //BA.debugLineNum = 186;BA.debugLine="utilidades.CreateHaloEffect(Activity, btnPregunta";
mostCurrent._utilidades._createhaloeffect /*void*/ (mostCurrent.activityBA,(anywheresoftware.b4a.objects.B4XViewWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.B4XViewWrapper(), (java.lang.Object)(mostCurrent._activity.getObject())),mostCurrent._btnpregunta1,anywheresoftware.b4a.keywords.Common.Colors.Red);
 //BA.debugLineNum = 188;BA.debugLine="End Sub";
return "";
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 195;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 197;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 190;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 193;BA.debugLine="End Sub";
return "";
}
public static String  _btncerrar_click() throws Exception{
 //BA.debugLineNum = 1541;BA.debugLine="Sub btnCerrar_Click";
 //BA.debugLineNum = 1544;BA.debugLine="pnlResultados.Visible = False";
mostCurrent._pnlresultados.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1545;BA.debugLine="GenerarScreenshot";
_generarscreenshot();
 //BA.debugLineNum = 1548;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 1549;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 1550;BA.debugLine="StartActivity(Reporte_Fotos)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._reporte_fotos.getObject()));
 //BA.debugLineNum = 1551;BA.debugLine="End Sub";
return "";
}
public static String  _btnmasdetalle_click() throws Exception{
 //BA.debugLineNum = 1535;BA.debugLine="Sub btnMasDetalle_Click";
 //BA.debugLineNum = 1536;BA.debugLine="scrResultados.Visible = True";
mostCurrent._scrresultados.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1537;BA.debugLine="btnMasDetalle.Visible = False";
mostCurrent._btnmasdetalle.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1538;BA.debugLine="End Sub";
return "";
}
public static String  _btnpregunta1_click() throws Exception{
 //BA.debugLineNum = 445;BA.debugLine="Sub btnPregunta1_Click";
 //BA.debugLineNum = 446;BA.debugLine="CargarPregunta(1)";
_cargarpregunta((int) (1));
 //BA.debugLineNum = 447;BA.debugLine="currentpregunta = 1";
_currentpregunta = (int) (1);
 //BA.debugLineNum = 448;BA.debugLine="End Sub";
return "";
}
public static String  _btnpregunta10_click() throws Exception{
 //BA.debugLineNum = 481;BA.debugLine="Sub btnPregunta10_Click";
 //BA.debugLineNum = 482;BA.debugLine="CargarPregunta(13)";
_cargarpregunta((int) (13));
 //BA.debugLineNum = 483;BA.debugLine="currentpregunta = 13";
_currentpregunta = (int) (13);
 //BA.debugLineNum = 484;BA.debugLine="End Sub";
return "";
}
public static String  _btnpregunta2_click() throws Exception{
 //BA.debugLineNum = 449;BA.debugLine="Sub btnPregunta2_Click";
 //BA.debugLineNum = 450;BA.debugLine="CargarPregunta(2)";
_cargarpregunta((int) (2));
 //BA.debugLineNum = 451;BA.debugLine="currentpregunta = 2";
_currentpregunta = (int) (2);
 //BA.debugLineNum = 452;BA.debugLine="End Sub";
return "";
}
public static String  _btnpregunta3_click() throws Exception{
 //BA.debugLineNum = 453;BA.debugLine="Sub btnPregunta3_Click";
 //BA.debugLineNum = 454;BA.debugLine="CargarPregunta(3)";
_cargarpregunta((int) (3));
 //BA.debugLineNum = 455;BA.debugLine="currentpregunta = 3";
_currentpregunta = (int) (3);
 //BA.debugLineNum = 456;BA.debugLine="End Sub";
return "";
}
public static String  _btnpregunta4_click() throws Exception{
 //BA.debugLineNum = 457;BA.debugLine="Sub btnPregunta4_Click";
 //BA.debugLineNum = 458;BA.debugLine="CargarPregunta(4)";
_cargarpregunta((int) (4));
 //BA.debugLineNum = 459;BA.debugLine="currentpregunta = 4";
_currentpregunta = (int) (4);
 //BA.debugLineNum = 460;BA.debugLine="End Sub";
return "";
}
public static String  _btnpregunta5_click() throws Exception{
 //BA.debugLineNum = 461;BA.debugLine="Sub btnPregunta5_Click";
 //BA.debugLineNum = 462;BA.debugLine="CargarPregunta(5)";
_cargarpregunta((int) (5));
 //BA.debugLineNum = 463;BA.debugLine="currentpregunta = 5";
_currentpregunta = (int) (5);
 //BA.debugLineNum = 464;BA.debugLine="End Sub";
return "";
}
public static String  _btnpregunta6_click() throws Exception{
 //BA.debugLineNum = 465;BA.debugLine="Sub btnPregunta6_Click";
 //BA.debugLineNum = 466;BA.debugLine="CargarPregunta(7)";
_cargarpregunta((int) (7));
 //BA.debugLineNum = 467;BA.debugLine="currentpregunta = 7";
_currentpregunta = (int) (7);
 //BA.debugLineNum = 468;BA.debugLine="End Sub";
return "";
}
public static String  _btnpregunta7_click() throws Exception{
 //BA.debugLineNum = 469;BA.debugLine="Sub btnPregunta7_Click";
 //BA.debugLineNum = 470;BA.debugLine="CargarPregunta(9)";
_cargarpregunta((int) (9));
 //BA.debugLineNum = 471;BA.debugLine="currentpregunta = 9";
_currentpregunta = (int) (9);
 //BA.debugLineNum = 472;BA.debugLine="End Sub";
return "";
}
public static String  _btnpregunta8_click() throws Exception{
 //BA.debugLineNum = 473;BA.debugLine="Sub btnPregunta8_Click";
 //BA.debugLineNum = 474;BA.debugLine="CargarPregunta(11)";
_cargarpregunta((int) (11));
 //BA.debugLineNum = 475;BA.debugLine="currentpregunta = 11";
_currentpregunta = (int) (11);
 //BA.debugLineNum = 476;BA.debugLine="End Sub";
return "";
}
public static String  _btnpregunta9_click() throws Exception{
 //BA.debugLineNum = 477;BA.debugLine="Sub btnPregunta9_Click";
 //BA.debugLineNum = 478;BA.debugLine="CargarPregunta(12)";
_cargarpregunta((int) (12));
 //BA.debugLineNum = 479;BA.debugLine="currentpregunta = 12";
_currentpregunta = (int) (12);
 //BA.debugLineNum = 480;BA.debugLine="End Sub";
return "";
}
public static String  _btnrdopcion1_click() throws Exception{
anywheresoftware.b4a.objects.ImageViewWrapper _fondogris = null;
String _htmlej = "";
anywheresoftware.b4a.objects.ButtonWrapper _butongris = null;
 //BA.debugLineNum = 246;BA.debugLine="Sub btnRdOpcion1_Click";
 //BA.debugLineNum = 248;BA.debugLine="Dim fondogris As ImageView";
_fondogris = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 249;BA.debugLine="fondogris.Initialize(\"fondogris\")";
_fondogris.Initialize(mostCurrent.activityBA,"fondogris");
 //BA.debugLineNum = 250;BA.debugLine="fondogris.Color = Colors.ARGB(150, 64,64,64)";
_fondogris.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (150),(int) (64),(int) (64),(int) (64)));
 //BA.debugLineNum = 251;BA.debugLine="Activity.AddView(fondogris, 0,0, 100%x, 100%y)";
mostCurrent._activity.AddView((android.view.View)(_fondogris.getObject()),(int) (0),(int) (0),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA));
 //BA.debugLineNum = 254;BA.debugLine="Dim htmlej As String";
_htmlej = "";
 //BA.debugLineNum = 255;BA.debugLine="htmlej = File.GetText(File.DirAssets, imgej1)";
_htmlej = anywheresoftware.b4a.keywords.Common.File.GetText(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._imgej1);
 //BA.debugLineNum = 257;BA.debugLine="explicacionviewer.Initialize(\"explicacionviewer\")";
mostCurrent._explicacionviewer.Initialize(mostCurrent.activityBA,"explicacionviewer");
 //BA.debugLineNum = 258;BA.debugLine="explicacionviewer.LoadHTML(htmlej)";
mostCurrent._explicacionviewer.LoadHtml(_htmlej);
 //BA.debugLineNum = 259;BA.debugLine="Activity.AddView(explicacionviewer, 15%x, 15%y, 7";
mostCurrent._activity.AddView((android.view.View)(mostCurrent._explicacionviewer.getObject()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (15),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (15),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (75),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (75),mostCurrent.activityBA));
 //BA.debugLineNum = 262;BA.debugLine="Dim butongris As Button";
_butongris = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 263;BA.debugLine="butongris.Initialize(\"fondogris\")";
_butongris.Initialize(mostCurrent.activityBA,"fondogris");
 //BA.debugLineNum = 264;BA.debugLine="butongris.Color = Colors.ARGB(255, 255,255,255)";
_butongris.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (255),(int) (255),(int) (255)));
 //BA.debugLineNum = 265;BA.debugLine="butongris.TextColor = Colors.Black";
_butongris.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 266;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 267;BA.debugLine="butongris.Text = \"Cerrar\"";
_butongris.setText(BA.ObjectToCharSequence("Cerrar"));
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 269;BA.debugLine="butongris.Text = \"Close\"";
_butongris.setText(BA.ObjectToCharSequence("Close"));
 };
 //BA.debugLineNum = 271;BA.debugLine="Activity.AddView(butongris, 15%x, 90%y, 75%x, 5%y";
mostCurrent._activity.AddView((android.view.View)(_butongris.getObject()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (15),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (90),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (75),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (5),mostCurrent.activityBA));
 //BA.debugLineNum = 272;BA.debugLine="End Sub";
return "";
}
public static String  _btnsiguiente_click() throws Exception{
String _valresp = "";
int _promagua = 0;
int _divagua = 0;
int _promdesb = 0;
int _didesb = 0;
 //BA.debugLineNum = 1147;BA.debugLine="Sub btnSiguiente_Click";
 //BA.debugLineNum = 1148;BA.debugLine="Dim valresp As String = \"\"";
_valresp = "";
 //BA.debugLineNum = 1149;BA.debugLine="valresp = ValidarRespuesta";
_valresp = _validarrespuesta();
 //BA.debugLineNum = 1151;BA.debugLine="If valresp = \"no\" Then";
if ((_valresp).equals("no")) { 
 //BA.debugLineNum = 1152;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 1153;BA.debugLine="MsgboxAsync(\"Debe seleccionar una opción para c";
anywheresoftware.b4a.keywords.Common.MsgboxAsync(BA.ObjectToCharSequence("Debe seleccionar una opción para continuar"),BA.ObjectToCharSequence("Atención!"),processBA);
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 1155;BA.debugLine="MsgboxAsync(\"You have to select an option to co";
anywheresoftware.b4a.keywords.Common.MsgboxAsync(BA.ObjectToCharSequence("You have to select an option to continue"),BA.ObjectToCharSequence("Attention"),processBA);
 };
 }else {
 //BA.debugLineNum = 1158;BA.debugLine="If btnSiguiente.Text = \"Ok\" Then";
if ((mostCurrent._btnsiguiente.getText()).equals("Ok")) { 
 //BA.debugLineNum = 1159;BA.debugLine="pnlQuestions.Visible = False";
mostCurrent._pnlquestions.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1161;BA.debugLine="Dim promagua As Int";
_promagua = 0;
 //BA.debugLineNum = 1162;BA.debugLine="Dim divagua As Int";
_divagua = 0;
 //BA.debugLineNum = 1163;BA.debugLine="If valorind5 <> \"\" And valorind5 <> \"NS\" Then";
if ((mostCurrent._valorind5).equals("") == false && (mostCurrent._valorind5).equals("NS") == false) { 
 //BA.debugLineNum = 1164;BA.debugLine="promagua = promagua + valorind5";
_promagua = (int) (_promagua+(double)(Double.parseDouble(mostCurrent._valorind5)));
 //BA.debugLineNum = 1165;BA.debugLine="divagua = divagua + 1";
_divagua = (int) (_divagua+1);
 };
 //BA.debugLineNum = 1167;BA.debugLine="If valorind6 <> \"\" And valorind6 <> \"NS\" Then";
if ((mostCurrent._valorind6).equals("") == false && (mostCurrent._valorind6).equals("NS") == false) { 
 //BA.debugLineNum = 1168;BA.debugLine="promagua = promagua + valorind6";
_promagua = (int) (_promagua+(double)(Double.parseDouble(mostCurrent._valorind6)));
 //BA.debugLineNum = 1169;BA.debugLine="divagua = divagua + 1";
_divagua = (int) (_divagua+1);
 };
 //BA.debugLineNum = 1171;BA.debugLine="If valorind8 <> \"\" And valorind8 <> \"NS\" Then";
if ((mostCurrent._valorind8).equals("") == false && (mostCurrent._valorind8).equals("NS") == false) { 
 //BA.debugLineNum = 1172;BA.debugLine="promagua = promagua + valorind8";
_promagua = (int) (_promagua+(double)(Double.parseDouble(mostCurrent._valorind8)));
 //BA.debugLineNum = 1173;BA.debugLine="divagua = divagua + 1";
_divagua = (int) (_divagua+1);
 };
 //BA.debugLineNum = 1175;BA.debugLine="If divagua <> 0 Then";
if (_divagua!=0) { 
 //BA.debugLineNum = 1176;BA.debugLine="promagua = promagua / divagua";
_promagua = (int) (_promagua/(double)_divagua);
 //BA.debugLineNum = 1177;BA.debugLine="utilidades.ColorearCirculos(btnPregunta5, prom";
mostCurrent._utilidades._colorearcirculos /*String*/ (mostCurrent.activityBA,(anywheresoftware.b4a.objects.LabelWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.LabelWrapper(), (android.widget.TextView)(mostCurrent._btnpregunta5.getObject())),BA.NumberToString(_promagua));
 }else {
 //BA.debugLineNum = 1179;BA.debugLine="If valorind5 <> \"NS\" And valorind6 <> \"NS\" And";
if ((mostCurrent._valorind5).equals("NS") == false && (mostCurrent._valorind6).equals("NS") == false && (mostCurrent._valorind8).equals("NS") == false) { 
 //BA.debugLineNum = 1180;BA.debugLine="utilidades.ColorearCirculos(btnPregunta5, \"\")";
mostCurrent._utilidades._colorearcirculos /*String*/ (mostCurrent.activityBA,(anywheresoftware.b4a.objects.LabelWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.LabelWrapper(), (android.widget.TextView)(mostCurrent._btnpregunta5.getObject())),"");
 }else {
 //BA.debugLineNum = 1182;BA.debugLine="utilidades.ColorearCirculos(btnPregunta5, \"NS";
mostCurrent._utilidades._colorearcirculos /*String*/ (mostCurrent.activityBA,(anywheresoftware.b4a.objects.LabelWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.LabelWrapper(), (android.widget.TextView)(mostCurrent._btnpregunta5.getObject())),"NS");
 };
 };
 //BA.debugLineNum = 1188;BA.debugLine="Dim promdesb As Int";
_promdesb = 0;
 //BA.debugLineNum = 1189;BA.debugLine="Dim didesb As Int";
_didesb = 0;
 //BA.debugLineNum = 1190;BA.debugLine="If valorind9 <> \"\" And valorind9 <> \"NS\" Then";
if ((mostCurrent._valorind9).equals("") == false && (mostCurrent._valorind9).equals("NS") == false) { 
 //BA.debugLineNum = 1191;BA.debugLine="promdesb = promdesb + valorind9";
_promdesb = (int) (_promdesb+(double)(Double.parseDouble(mostCurrent._valorind9)));
 //BA.debugLineNum = 1192;BA.debugLine="didesb = didesb + 1";
_didesb = (int) (_didesb+1);
 };
 //BA.debugLineNum = 1194;BA.debugLine="If valorind10 <> \"\" And valorind10 <> \"NS\" Then";
if ((mostCurrent._valorind10).equals("") == false && (mostCurrent._valorind10).equals("NS") == false) { 
 //BA.debugLineNum = 1195;BA.debugLine="promdesb = promdesb + valorind10";
_promdesb = (int) (_promdesb+(double)(Double.parseDouble(mostCurrent._valorind10)));
 //BA.debugLineNum = 1196;BA.debugLine="didesb = didesb + 1";
_didesb = (int) (_didesb+1);
 };
 //BA.debugLineNum = 1199;BA.debugLine="If didesb <> 0 Then";
if (_didesb!=0) { 
 //BA.debugLineNum = 1200;BA.debugLine="promdesb = promdesb / didesb";
_promdesb = (int) (_promdesb/(double)_didesb);
 //BA.debugLineNum = 1201;BA.debugLine="utilidades.ColorearCirculos(btnPregunta7, prom";
mostCurrent._utilidades._colorearcirculos /*String*/ (mostCurrent.activityBA,(anywheresoftware.b4a.objects.LabelWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.LabelWrapper(), (android.widget.TextView)(mostCurrent._btnpregunta7.getObject())),BA.NumberToString(_promdesb));
 }else {
 //BA.debugLineNum = 1203;BA.debugLine="If valorind9 <> \"NS\" And valorind10 <> \"NS\" Th";
if ((mostCurrent._valorind9).equals("NS") == false && (mostCurrent._valorind10).equals("NS") == false) { 
 //BA.debugLineNum = 1204;BA.debugLine="utilidades.ColorearCirculos(btnPregunta7, \"\")";
mostCurrent._utilidades._colorearcirculos /*String*/ (mostCurrent.activityBA,(anywheresoftware.b4a.objects.LabelWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.LabelWrapper(), (android.widget.TextView)(mostCurrent._btnpregunta7.getObject())),"");
 }else {
 //BA.debugLineNum = 1206;BA.debugLine="utilidades.ColorearCirculos(btnPregunta7, \"NS";
mostCurrent._utilidades._colorearcirculos /*String*/ (mostCurrent.activityBA,(anywheresoftware.b4a.objects.LabelWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.LabelWrapper(), (android.widget.TextView)(mostCurrent._btnpregunta7.getObject())),"NS");
 };
 };
 //BA.debugLineNum = 1212;BA.debugLine="cantidadpreguntas = 0";
_cantidadpreguntas = (int) (0);
 //BA.debugLineNum = 1213;BA.debugLine="If valorind1 <> \"\" Then";
if ((mostCurrent._valorind1).equals("") == false) { 
 //BA.debugLineNum = 1214;BA.debugLine="cantidadpreguntas = cantidadpreguntas + 1";
_cantidadpreguntas = (int) (_cantidadpreguntas+1);
 };
 //BA.debugLineNum = 1216;BA.debugLine="If valorind2 <> \"\" Then";
if ((mostCurrent._valorind2).equals("") == false) { 
 //BA.debugLineNum = 1217;BA.debugLine="cantidadpreguntas = cantidadpreguntas + 1";
_cantidadpreguntas = (int) (_cantidadpreguntas+1);
 };
 //BA.debugLineNum = 1219;BA.debugLine="If valorind3 <> \"\" Then";
if ((mostCurrent._valorind3).equals("") == false) { 
 //BA.debugLineNum = 1220;BA.debugLine="cantidadpreguntas = cantidadpreguntas + 1";
_cantidadpreguntas = (int) (_cantidadpreguntas+1);
 };
 //BA.debugLineNum = 1222;BA.debugLine="If valorind4 <> \"\" Then";
if ((mostCurrent._valorind4).equals("") == false) { 
 //BA.debugLineNum = 1223;BA.debugLine="cantidadpreguntas = cantidadpreguntas + 1";
_cantidadpreguntas = (int) (_cantidadpreguntas+1);
 };
 //BA.debugLineNum = 1225;BA.debugLine="If valorind5 <> \"\" Then";
if ((mostCurrent._valorind5).equals("") == false) { 
 //BA.debugLineNum = 1226;BA.debugLine="cantidadpreguntas = cantidadpreguntas + 1";
_cantidadpreguntas = (int) (_cantidadpreguntas+1);
 };
 //BA.debugLineNum = 1228;BA.debugLine="If valorind6 <> \"\" Then";
if ((mostCurrent._valorind6).equals("") == false) { 
 //BA.debugLineNum = 1229;BA.debugLine="cantidadpreguntas = cantidadpreguntas + 1";
_cantidadpreguntas = (int) (_cantidadpreguntas+1);
 };
 //BA.debugLineNum = 1231;BA.debugLine="If valorind7 <> \"\" Then";
if ((mostCurrent._valorind7).equals("") == false) { 
 //BA.debugLineNum = 1232;BA.debugLine="cantidadpreguntas = cantidadpreguntas + 1";
_cantidadpreguntas = (int) (_cantidadpreguntas+1);
 };
 //BA.debugLineNum = 1234;BA.debugLine="If valorind8 <> \"\" Then";
if ((mostCurrent._valorind8).equals("") == false) { 
 //BA.debugLineNum = 1235;BA.debugLine="cantidadpreguntas = cantidadpreguntas + 1";
_cantidadpreguntas = (int) (_cantidadpreguntas+1);
 };
 //BA.debugLineNum = 1237;BA.debugLine="If valorind9 <> \"\" Then";
if ((mostCurrent._valorind9).equals("") == false) { 
 //BA.debugLineNum = 1238;BA.debugLine="cantidadpreguntas = cantidadpreguntas + 1";
_cantidadpreguntas = (int) (_cantidadpreguntas+1);
 };
 //BA.debugLineNum = 1240;BA.debugLine="If valorind10 <> \"\" Then";
if ((mostCurrent._valorind10).equals("") == false) { 
 //BA.debugLineNum = 1241;BA.debugLine="cantidadpreguntas = cantidadpreguntas + 1";
_cantidadpreguntas = (int) (_cantidadpreguntas+1);
 };
 //BA.debugLineNum = 1243;BA.debugLine="If valorind11 <> \"\" Then";
if ((mostCurrent._valorind11).equals("") == false) { 
 //BA.debugLineNum = 1244;BA.debugLine="cantidadpreguntas = cantidadpreguntas + 1";
_cantidadpreguntas = (int) (_cantidadpreguntas+1);
 };
 //BA.debugLineNum = 1246;BA.debugLine="If valorind12 <> \"\" Then";
if ((mostCurrent._valorind12).equals("") == false) { 
 //BA.debugLineNum = 1247;BA.debugLine="cantidadpreguntas = cantidadpreguntas + 1";
_cantidadpreguntas = (int) (_cantidadpreguntas+1);
 };
 //BA.debugLineNum = 1249;BA.debugLine="If valorind13 <> \"\" Then";
if ((mostCurrent._valorind13).equals("") == false) { 
 //BA.debugLineNum = 1250;BA.debugLine="cantidadpreguntas = cantidadpreguntas + 1";
_cantidadpreguntas = (int) (_cantidadpreguntas+1);
 };
 //BA.debugLineNum = 1252;BA.debugLine="If valorind14 <> \"\" Then";
if ((mostCurrent._valorind14).equals("") == false) { 
 //BA.debugLineNum = 1253;BA.debugLine="cantidadpreguntas = cantidadpreguntas + 1";
_cantidadpreguntas = (int) (_cantidadpreguntas+1);
 };
 //BA.debugLineNum = 1255;BA.debugLine="If valorind15 <> \"\" Then";
if ((mostCurrent._valorind15).equals("") == false) { 
 //BA.debugLineNum = 1256;BA.debugLine="cantidadpreguntas = cantidadpreguntas + 1";
_cantidadpreguntas = (int) (_cantidadpreguntas+1);
 };
 //BA.debugLineNum = 1258;BA.debugLine="If valorind16 <> \"\" Then";
if ((mostCurrent._valorind16).equals("") == false) { 
 //BA.debugLineNum = 1259;BA.debugLine="cantidadpreguntas = cantidadpreguntas + 1";
_cantidadpreguntas = (int) (_cantidadpreguntas+1);
 };
 //BA.debugLineNum = 1264;BA.debugLine="If tiporio = \"llanura\" Then";
if ((_tiporio).equals("llanura")) { 
 //BA.debugLineNum = 1265;BA.debugLine="pgbPreguntas = (cantidadpreguntas * 100) / 12";
_pgbpreguntas = (int) ((_cantidadpreguntas*100)/(double)12);
 }else if((_tiporio).equals("montana")) { 
 //BA.debugLineNum = 1267;BA.debugLine="pgbPreguntas = (cantidadpreguntas * 100) / 13";
_pgbpreguntas = (int) ((_cantidadpreguntas*100)/(double)13);
 };
 //BA.debugLineNum = 1272;BA.debugLine="If preguntanumero = 1 Then";
if ((mostCurrent._preguntanumero).equals(BA.NumberToString(1))) { 
 //BA.debugLineNum = 1273;BA.debugLine="btnPregunta2.Visible = True";
mostCurrent._btnpregunta2.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1274;BA.debugLine="utilidades.CreateHaloEffect(Activity, btnPregu";
mostCurrent._utilidades._createhaloeffect /*void*/ (mostCurrent.activityBA,(anywheresoftware.b4a.objects.B4XViewWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.B4XViewWrapper(), (java.lang.Object)(mostCurrent._activity.getObject())),mostCurrent._btnpregunta2,anywheresoftware.b4a.keywords.Common.Colors.Red);
 //BA.debugLineNum = 1275;BA.debugLine="lblPregunta2.Visible = True";
mostCurrent._lblpregunta2.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1276;BA.debugLine="lblPregunta1.Visible = False";
mostCurrent._lblpregunta1.setVisible(anywheresoftware.b4a.keywords.Common.False);
 }else if((mostCurrent._preguntanumero).equals(BA.NumberToString(2))) { 
 //BA.debugLineNum = 1278;BA.debugLine="btnPregunta3.Visible = True";
mostCurrent._btnpregunta3.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1279;BA.debugLine="utilidades.CreateHaloEffect(Activity, btnPregu";
mostCurrent._utilidades._createhaloeffect /*void*/ (mostCurrent.activityBA,(anywheresoftware.b4a.objects.B4XViewWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.B4XViewWrapper(), (java.lang.Object)(mostCurrent._activity.getObject())),mostCurrent._btnpregunta3,anywheresoftware.b4a.keywords.Common.Colors.Red);
 //BA.debugLineNum = 1280;BA.debugLine="lblPregunta3.Visible = True";
mostCurrent._lblpregunta3.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1281;BA.debugLine="lblPregunta2.Visible = False";
mostCurrent._lblpregunta2.setVisible(anywheresoftware.b4a.keywords.Common.False);
 }else if((mostCurrent._preguntanumero).equals(BA.NumberToString(3))) { 
 //BA.debugLineNum = 1283;BA.debugLine="btnPregunta4.Visible = True";
mostCurrent._btnpregunta4.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1284;BA.debugLine="utilidades.CreateHaloEffect(Activity, btnPregu";
mostCurrent._utilidades._createhaloeffect /*void*/ (mostCurrent.activityBA,(anywheresoftware.b4a.objects.B4XViewWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.B4XViewWrapper(), (java.lang.Object)(mostCurrent._activity.getObject())),mostCurrent._btnpregunta4,anywheresoftware.b4a.keywords.Common.Colors.Red);
 //BA.debugLineNum = 1285;BA.debugLine="lblPregunta4.Visible = True";
mostCurrent._lblpregunta4.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1286;BA.debugLine="lblPregunta3.Visible = False";
mostCurrent._lblpregunta3.setVisible(anywheresoftware.b4a.keywords.Common.False);
 }else if((mostCurrent._preguntanumero).equals(BA.NumberToString(4))) { 
 //BA.debugLineNum = 1288;BA.debugLine="btnPregunta5.Visible = True";
mostCurrent._btnpregunta5.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1289;BA.debugLine="utilidades.CreateHaloEffect(Activity, btnPregu";
mostCurrent._utilidades._createhaloeffect /*void*/ (mostCurrent.activityBA,(anywheresoftware.b4a.objects.B4XViewWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.B4XViewWrapper(), (java.lang.Object)(mostCurrent._activity.getObject())),mostCurrent._btnpregunta5,anywheresoftware.b4a.keywords.Common.Colors.Red);
 //BA.debugLineNum = 1290;BA.debugLine="lblPregunta5.Visible = True";
mostCurrent._lblpregunta5.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1291;BA.debugLine="lblPregunta4.Visible = False";
mostCurrent._lblpregunta4.setVisible(anywheresoftware.b4a.keywords.Common.False);
 }else if((mostCurrent._preguntanumero).equals(BA.NumberToString(5))) { 
 //BA.debugLineNum = 1293;BA.debugLine="btnPregunta6.Visible = True";
mostCurrent._btnpregunta6.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1294;BA.debugLine="utilidades.CreateHaloEffect(Activity, btnPregu";
mostCurrent._utilidades._createhaloeffect /*void*/ (mostCurrent.activityBA,(anywheresoftware.b4a.objects.B4XViewWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.B4XViewWrapper(), (java.lang.Object)(mostCurrent._activity.getObject())),mostCurrent._btnpregunta6,anywheresoftware.b4a.keywords.Common.Colors.Red);
 //BA.debugLineNum = 1295;BA.debugLine="lblPregunta6.Visible = True";
mostCurrent._lblpregunta6.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1296;BA.debugLine="lblPregunta5.Visible = False";
mostCurrent._lblpregunta5.setVisible(anywheresoftware.b4a.keywords.Common.False);
 }else if((mostCurrent._preguntanumero).equals(BA.NumberToString(6))) { 
 //BA.debugLineNum = 1298;BA.debugLine="btnPregunta7.Visible = True";
mostCurrent._btnpregunta7.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1299;BA.debugLine="utilidades.CreateHaloEffect(Activity, btnPregu";
mostCurrent._utilidades._createhaloeffect /*void*/ (mostCurrent.activityBA,(anywheresoftware.b4a.objects.B4XViewWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.B4XViewWrapper(), (java.lang.Object)(mostCurrent._activity.getObject())),mostCurrent._btnpregunta7,anywheresoftware.b4a.keywords.Common.Colors.Red);
 //BA.debugLineNum = 1300;BA.debugLine="lblPregunta7.Visible = True";
mostCurrent._lblpregunta7.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1301;BA.debugLine="lblPregunta6.Visible = False";
mostCurrent._lblpregunta6.setVisible(anywheresoftware.b4a.keywords.Common.False);
 }else if((mostCurrent._preguntanumero).equals(BA.NumberToString(7))) { 
 //BA.debugLineNum = 1303;BA.debugLine="btnPregunta8.Visible = True";
mostCurrent._btnpregunta8.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1304;BA.debugLine="utilidades.CreateHaloEffect(Activity, btnPregu";
mostCurrent._utilidades._createhaloeffect /*void*/ (mostCurrent.activityBA,(anywheresoftware.b4a.objects.B4XViewWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.B4XViewWrapper(), (java.lang.Object)(mostCurrent._activity.getObject())),mostCurrent._btnpregunta8,anywheresoftware.b4a.keywords.Common.Colors.Red);
 //BA.debugLineNum = 1305;BA.debugLine="lblPregunta8.Visible = True";
mostCurrent._lblpregunta8.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1306;BA.debugLine="lblPregunta7.Visible = False";
mostCurrent._lblpregunta7.setVisible(anywheresoftware.b4a.keywords.Common.False);
 }else if((mostCurrent._preguntanumero).equals(BA.NumberToString(8))) { 
 //BA.debugLineNum = 1308;BA.debugLine="btnPregunta9.Visible = True";
mostCurrent._btnpregunta9.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1309;BA.debugLine="utilidades.CreateHaloEffect(Activity, btnPregu";
mostCurrent._utilidades._createhaloeffect /*void*/ (mostCurrent.activityBA,(anywheresoftware.b4a.objects.B4XViewWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.B4XViewWrapper(), (java.lang.Object)(mostCurrent._activity.getObject())),mostCurrent._btnpregunta9,anywheresoftware.b4a.keywords.Common.Colors.Red);
 //BA.debugLineNum = 1310;BA.debugLine="lblPregunta9.Visible = True";
mostCurrent._lblpregunta9.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1311;BA.debugLine="lblPregunta8.Visible = False";
mostCurrent._lblpregunta8.setVisible(anywheresoftware.b4a.keywords.Common.False);
 }else if((mostCurrent._preguntanumero).equals(BA.NumberToString(9)) && (_tiporio).equals("montana")) { 
 //BA.debugLineNum = 1313;BA.debugLine="btnPregunta10.Visible = True";
mostCurrent._btnpregunta10.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1314;BA.debugLine="utilidades.CreateHaloEffect(Activity, btnPregu";
mostCurrent._utilidades._createhaloeffect /*void*/ (mostCurrent.activityBA,(anywheresoftware.b4a.objects.B4XViewWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.B4XViewWrapper(), (java.lang.Object)(mostCurrent._activity.getObject())),mostCurrent._btnpregunta10,anywheresoftware.b4a.keywords.Common.Colors.Red);
 //BA.debugLineNum = 1315;BA.debugLine="lblPregunta10.Visible = True";
mostCurrent._lblpregunta10.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1316;BA.debugLine="lblPregunta9.Visible = False";
mostCurrent._lblpregunta9.setVisible(anywheresoftware.b4a.keywords.Common.False);
 }else if((mostCurrent._preguntanumero).equals(BA.NumberToString(9)) && (_tiporio).equals("llanura")) { 
 //BA.debugLineNum = 1318;BA.debugLine="btnPregunta9.Visible = False";
mostCurrent._btnpregunta9.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1319;BA.debugLine="utilidades.CreateHaloEffect(Activity, btnTermi";
mostCurrent._utilidades._createhaloeffect /*void*/ (mostCurrent.activityBA,(anywheresoftware.b4a.objects.B4XViewWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.B4XViewWrapper(), (java.lang.Object)(mostCurrent._activity.getObject())),mostCurrent._btnterminar,anywheresoftware.b4a.keywords.Common.Colors.Red);
 //BA.debugLineNum = 1320;BA.debugLine="lblPregunta9.Visible = False";
mostCurrent._lblpregunta9.setVisible(anywheresoftware.b4a.keywords.Common.False);
 }else if((mostCurrent._preguntanumero).equals(BA.NumberToString(10))) { 
 //BA.debugLineNum = 1322;BA.debugLine="btnPregunta10.Visible = False";
mostCurrent._btnpregunta10.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1323;BA.debugLine="utilidades.CreateHaloEffect(Activity, btnTermi";
mostCurrent._utilidades._createhaloeffect /*void*/ (mostCurrent.activityBA,(anywheresoftware.b4a.objects.B4XViewWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.B4XViewWrapper(), (java.lang.Object)(mostCurrent._activity.getObject())),mostCurrent._btnterminar,anywheresoftware.b4a.keywords.Common.Colors.Red);
 //BA.debugLineNum = 1324;BA.debugLine="lblPregunta10.Visible = False";
mostCurrent._lblpregunta10.setVisible(anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 1326;BA.debugLine="preguntanumero = preguntanumero + 1";
mostCurrent._preguntanumero = BA.NumberToString((double)(Double.parseDouble(mostCurrent._preguntanumero))+1);
 //BA.debugLineNum = 1329;BA.debugLine="If pgbPreguntas = 100 Then";
if (_pgbpreguntas==100) { 
 //BA.debugLineNum = 1330;BA.debugLine="btnTerminar.Visible = True";
mostCurrent._btnterminar.setVisible(anywheresoftware.b4a.keywords.Common.True);
 };
 }else {
 //BA.debugLineNum = 1333;BA.debugLine="currentpregunta = btnSiguiente.tag";
_currentpregunta = (int)(BA.ObjectToNumber(mostCurrent._btnsiguiente.getTag()));
 //BA.debugLineNum = 1334;BA.debugLine="CargarPregunta(btnSiguiente.Tag)";
_cargarpregunta((int)(BA.ObjectToNumber(mostCurrent._btnsiguiente.getTag())));
 };
 };
 //BA.debugLineNum = 1339;BA.debugLine="rdOpcion1.Checked = False";
mostCurrent._rdopcion1.setChecked(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1340;BA.debugLine="rdOpcion2.Checked = False";
mostCurrent._rdopcion2.setChecked(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1341;BA.debugLine="rdOpcion3.Checked = False";
mostCurrent._rdopcion3.setChecked(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1342;BA.debugLine="rdOpcion4.Checked = False";
mostCurrent._rdopcion4.setChecked(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1343;BA.debugLine="rdOpcion5.Checked = False";
mostCurrent._rdopcion5.setChecked(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1344;BA.debugLine="rdOpcion6.Checked = False";
mostCurrent._rdopcion6.setChecked(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1345;BA.debugLine="End Sub";
return "";
}
public static String  _btnterminar_click() throws Exception{
 //BA.debugLineNum = 1354;BA.debugLine="Sub btnTerminar_Click";
 //BA.debugLineNum = 1355;BA.debugLine="btnTerminar.Visible = False";
mostCurrent._btnterminar.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1356;BA.debugLine="TerminarEval";
_terminareval();
 //BA.debugLineNum = 1357;BA.debugLine="GuardarEval";
_guardareval();
 //BA.debugLineNum = 1359;BA.debugLine="End Sub";
return "";
}
public static String  _butongris_click() throws Exception{
 //BA.debugLineNum = 279;BA.debugLine="Sub butongris_click";
 //BA.debugLineNum = 280;BA.debugLine="Activity.RemoveViewAt(Activity.NumberOfViews - 1)";
mostCurrent._activity.RemoveViewAt((int) (mostCurrent._activity.getNumberOfViews()-1));
 //BA.debugLineNum = 281;BA.debugLine="Activity.RemoveViewAt(Activity.NumberOfViews - 1)";
mostCurrent._activity.RemoveViewAt((int) (mostCurrent._activity.getNumberOfViews()-1));
 //BA.debugLineNum = 282;BA.debugLine="Activity.RemoveViewAt(Activity.NumberOfViews - 1)";
mostCurrent._activity.RemoveViewAt((int) (mostCurrent._activity.getNumberOfViews()-1));
 //BA.debugLineNum = 283;BA.debugLine="End Sub";
return "";
}
public static String  _cargarpregunta(int _numpregunta) throws Exception{
anywheresoftware.b4a.objects.drawable.ColorDrawable _cd = null;
 //BA.debugLineNum = 493;BA.debugLine="Sub CargarPregunta (numpregunta As Int)";
 //BA.debugLineNum = 494;BA.debugLine="pnlQuestions.Visible = True";
mostCurrent._pnlquestions.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 495;BA.debugLine="pnlQuestions.BringToFront";
mostCurrent._pnlquestions.BringToFront();
 //BA.debugLineNum = 499;BA.debugLine="btnPregunta1.Visible = False";
mostCurrent._btnpregunta1.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 500;BA.debugLine="btnPregunta2.Visible = False";
mostCurrent._btnpregunta2.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 501;BA.debugLine="btnPregunta3.Visible = False";
mostCurrent._btnpregunta3.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 502;BA.debugLine="btnPregunta4.Visible = False";
mostCurrent._btnpregunta4.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 503;BA.debugLine="btnPregunta5.Visible = False";
mostCurrent._btnpregunta5.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 504;BA.debugLine="btnPregunta6.Visible = False";
mostCurrent._btnpregunta6.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 505;BA.debugLine="btnPregunta7.Visible = False";
mostCurrent._btnpregunta7.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 506;BA.debugLine="btnPregunta8.Visible = False";
mostCurrent._btnpregunta8.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 507;BA.debugLine="btnPregunta9.Visible = False";
mostCurrent._btnpregunta9.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 508;BA.debugLine="btnPregunta10.Visible = False";
mostCurrent._btnpregunta10.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 511;BA.debugLine="Dim cd As ColorDrawable";
_cd = new anywheresoftware.b4a.objects.drawable.ColorDrawable();
 //BA.debugLineNum = 512;BA.debugLine="If csv.Bitmap = Null Then";
if (mostCurrent._csv.getBitmap()== null) { 
 //BA.debugLineNum = 513;BA.debugLine="csv.Initialize(pnlOpciones)";
mostCurrent._csv.Initialize((android.view.View)(mostCurrent._pnlopciones.getObject()));
 //BA.debugLineNum = 514;BA.debugLine="csvChk.Initialize(pnlChecks)";
mostCurrent._csvchk.Initialize((android.view.View)(mostCurrent._pnlchecks.getObject()));
 //BA.debugLineNum = 515;BA.debugLine="csv.DrawColor(0)";
mostCurrent._csv.DrawColor((int) (0));
 //BA.debugLineNum = 516;BA.debugLine="cd.Initialize(Colors.ARGB(0,255,255,255), 0)";
_cd.Initialize(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (255),(int) (255),(int) (255)),(int) (0));
 //BA.debugLineNum = 517;BA.debugLine="rec.Initialize(rdOpcion1.Left, rdOpcion1.Top, rd";
mostCurrent._rec.Initialize(mostCurrent._rdopcion1.getLeft(),mostCurrent._rdopcion1.getTop(),(int) (mostCurrent._rdopcion6.getWidth()+mostCurrent._rdopcion6.getLeft()),(int) (mostCurrent._rdopcion6.getHeight()+mostCurrent._rdopcion6.getTop()));
 //BA.debugLineNum = 518;BA.debugLine="csv.DrawDrawable(cd, rec)";
mostCurrent._csv.DrawDrawable((android.graphics.drawable.Drawable)(_cd.getObject()),(android.graphics.Rect)(mostCurrent._rec.getObject()));
 }else {
 //BA.debugLineNum = 520;BA.debugLine="csv.DrawColor(0)";
mostCurrent._csv.DrawColor((int) (0));
 //BA.debugLineNum = 521;BA.debugLine="cd.Initialize(Colors.ARGB(0,255,255,255), 0)";
_cd.Initialize(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (255),(int) (255),(int) (255)),(int) (0));
 //BA.debugLineNum = 522;BA.debugLine="rec.Initialize(rdOpcion1.Left, rdOpcion1.Top, rd";
mostCurrent._rec.Initialize(mostCurrent._rdopcion1.getLeft(),mostCurrent._rdopcion1.getTop(),(int) (mostCurrent._rdopcion6.getWidth()+mostCurrent._rdopcion6.getLeft()),(int) (mostCurrent._rdopcion6.getHeight()+mostCurrent._rdopcion6.getTop()));
 //BA.debugLineNum = 523;BA.debugLine="csv.DrawDrawable(cd, rec)";
mostCurrent._csv.DrawDrawable((android.graphics.drawable.Drawable)(_cd.getObject()),(android.graphics.Rect)(mostCurrent._rec.getObject()));
 };
 //BA.debugLineNum = 526;BA.debugLine="If numpregunta = 1 Then";
if (_numpregunta==1) { 
 //BA.debugLineNum = 527;BA.debugLine="TipoPreguntaRad(6, False)";
_tipopreguntarad((int) (6),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 528;BA.debugLine="lblPregunta.Text = \"¿Que hay alrededor del lugar";
mostCurrent._lblpregunta.setText(BA.ObjectToCharSequence("¿Que hay alrededor del lugar?"));
 //BA.debugLineNum = 529;BA.debugLine="rdOpcion1.SetBackgroundImage(LoadBitmapSample(Fi";
mostCurrent._rdopcion1.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._langlocal+"1a.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 530;BA.debugLine="rdOpcion2.SetBackgroundImage(LoadBitmapSample(Fi";
mostCurrent._rdopcion2.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._langlocal+"1b.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 531;BA.debugLine="rdOpcion3.SetBackgroundImage(LoadBitmapSample(Fi";
mostCurrent._rdopcion3.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._langlocal+"1c.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 532;BA.debugLine="rdOpcion4.SetBackgroundImage(LoadBitmapSample(Fi";
mostCurrent._rdopcion4.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._langlocal+"1d.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 533;BA.debugLine="rdOpcion5.SetBackgroundImage(LoadBitmapSample(Fi";
mostCurrent._rdopcion5.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._langlocal+"1e.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 534;BA.debugLine="rdOpcion6.SetBackgroundImage(LoadBitmapSample(Fi";
mostCurrent._rdopcion6.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._langlocal+"skipq.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 535;BA.debugLine="btnSiguiente.Text = \"Ok\"";
mostCurrent._btnsiguiente.setText(BA.ObjectToCharSequence("Ok"));
 }else if(_numpregunta==2) { 
 //BA.debugLineNum = 537;BA.debugLine="TipoPreguntaRad(5, True)";
_tipopreguntarad((int) (5),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 538;BA.debugLine="lblPregunta.Text = \"¿Hay ganado cerca del sitio?";
mostCurrent._lblpregunta.setText(BA.ObjectToCharSequence("¿Hay ganado cerca del sitio?"));
 //BA.debugLineNum = 539;BA.debugLine="rdOpcion1.SetBackgroundImage(LoadBitmapSample(Fi";
mostCurrent._rdopcion1.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._langlocal+"2a.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 540;BA.debugLine="rdOpcion2.SetBackgroundImage(LoadBitmapSample(Fi";
mostCurrent._rdopcion2.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._langlocal+"2b.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 541;BA.debugLine="rdOpcion3.SetBackgroundImage(LoadBitmapSample(Fi";
mostCurrent._rdopcion3.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._langlocal+"2c.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 542;BA.debugLine="rdOpcion4.SetBackgroundImage(LoadBitmapSample(Fi";
mostCurrent._rdopcion4.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._langlocal+"2d.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 543;BA.debugLine="rdOpcion5.SetBackgroundImage(LoadBitmapSample(Fi";
mostCurrent._rdopcion5.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._langlocal+"skipq.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 545;BA.debugLine="imgej1 = \"ej1.html\"";
mostCurrent._imgej1 = "ej1.html";
 //BA.debugLineNum = 546;BA.debugLine="btnSiguiente.Text = \"Ok\"";
mostCurrent._btnsiguiente.setText(BA.ObjectToCharSequence("Ok"));
 }else if(_numpregunta==3) { 
 //BA.debugLineNum = 548;BA.debugLine="TipoPreguntaRad(4, False)";
_tipopreguntarad((int) (4),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 549;BA.debugLine="lblPregunta.Text = \"¿Cómo son los árboles?\"";
mostCurrent._lblpregunta.setText(BA.ObjectToCharSequence("¿Cómo son los árboles?"));
 //BA.debugLineNum = 550;BA.debugLine="rdOpcion1.SetBackgroundImage(LoadBitmapSample(Fi";
mostCurrent._rdopcion1.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._langlocal+"3a.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 551;BA.debugLine="rdOpcion2.SetBackgroundImage(LoadBitmapSample(Fi";
mostCurrent._rdopcion2.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._langlocal+"3b.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 552;BA.debugLine="rdOpcion3.SetBackgroundImage(LoadBitmapSample(Fi";
mostCurrent._rdopcion3.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._langlocal+"3c.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 553;BA.debugLine="rdOpcion4.SetBackgroundImage(LoadBitmapSample(Fi";
mostCurrent._rdopcion4.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._langlocal+"skipq.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 554;BA.debugLine="btnSiguiente.Text = \"Ok\"";
mostCurrent._btnsiguiente.setText(BA.ObjectToCharSequence("Ok"));
 }else if(_numpregunta==4) { 
 //BA.debugLineNum = 556;BA.debugLine="TipoPreguntaRad(4, False)";
_tipopreguntarad((int) (4),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 557;BA.debugLine="lblPregunta.Text = \"¿Hay plantas acuáticas?\"";
mostCurrent._lblpregunta.setText(BA.ObjectToCharSequence("¿Hay plantas acuáticas?"));
 //BA.debugLineNum = 558;BA.debugLine="rdOpcion1.SetBackgroundImage(LoadBitmapSample(Fi";
mostCurrent._rdopcion1.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._langlocal+"4a.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 559;BA.debugLine="rdOpcion2.SetBackgroundImage(LoadBitmapSample(Fi";
mostCurrent._rdopcion2.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._langlocal+"4b.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 560;BA.debugLine="rdOpcion3.SetBackgroundImage(LoadBitmapSample(Fi";
mostCurrent._rdopcion3.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._langlocal+"4c.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 561;BA.debugLine="rdOpcion4.SetBackgroundImage(LoadBitmapSample(Fi";
mostCurrent._rdopcion4.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._langlocal+"skipq.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 562;BA.debugLine="btnSiguiente.Text = \"Ok\"";
mostCurrent._btnsiguiente.setText(BA.ObjectToCharSequence("Ok"));
 }else if(_numpregunta==5) { 
 //BA.debugLineNum = 564;BA.debugLine="lblPregunta.Text = \"¿Es transparente el agua?\"";
mostCurrent._lblpregunta.setText(BA.ObjectToCharSequence("¿Es transparente el agua?"));
 //BA.debugLineNum = 565;BA.debugLine="TipoPreguntaRad(4, True)";
_tipopreguntarad((int) (4),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 566;BA.debugLine="rdOpcion1.SetBackgroundImage(LoadBitmapSample(Fi";
mostCurrent._rdopcion1.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._langlocal+"5a.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 567;BA.debugLine="rdOpcion2.SetBackgroundImage(LoadBitmapSample(Fi";
mostCurrent._rdopcion2.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._langlocal+"5b.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 568;BA.debugLine="rdOpcion3.SetBackgroundImage(LoadBitmapSample(Fi";
mostCurrent._rdopcion3.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._langlocal+"5c.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 569;BA.debugLine="rdOpcion4.SetBackgroundImage(LoadBitmapSample(Fi";
mostCurrent._rdopcion4.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._langlocal+"skipq.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 571;BA.debugLine="imgej1 = \"ej5.html\"";
mostCurrent._imgej1 = "ej5.html";
 //BA.debugLineNum = 572;BA.debugLine="btnSiguiente.Text = \"Siguiente pregunta\"";
mostCurrent._btnsiguiente.setText(BA.ObjectToCharSequence("Siguiente pregunta"));
 //BA.debugLineNum = 573;BA.debugLine="btnSiguiente.Tag = \"6\"";
mostCurrent._btnsiguiente.setTag((Object)("6"));
 }else if(_numpregunta==6) { 
 //BA.debugLineNum = 575;BA.debugLine="TipoPreguntaRad(3,False)";
_tipopreguntarad((int) (3),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 576;BA.debugLine="lblPregunta.Text = \"¿Tiene mal olor?\"";
mostCurrent._lblpregunta.setText(BA.ObjectToCharSequence("¿Tiene mal olor?"));
 //BA.debugLineNum = 577;BA.debugLine="rdOpcion1.SetBackgroundImage(LoadBitmapSample(Fi";
mostCurrent._rdopcion1.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._langlocal+"6a.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 578;BA.debugLine="rdOpcion2.SetBackgroundImage(LoadBitmapSample(Fi";
mostCurrent._rdopcion2.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._langlocal+"6b.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 579;BA.debugLine="rdOpcion3.SetBackgroundImage(LoadBitmapSample(Fi";
mostCurrent._rdopcion3.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._langlocal+"skipq.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 580;BA.debugLine="btnSiguiente.Text = \"Siguiente pregunta\"";
mostCurrent._btnsiguiente.setText(BA.ObjectToCharSequence("Siguiente pregunta"));
 //BA.debugLineNum = 581;BA.debugLine="btnSiguiente.Tag = \"8\"";
mostCurrent._btnsiguiente.setTag((Object)("8"));
 }else if(_numpregunta==7) { 
 //BA.debugLineNum = 583;BA.debugLine="TipoPreguntaRad(4,True)";
_tipopreguntarad((int) (4),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 584;BA.debugLine="lblPregunta.Text = \"¿Hay basura en la costa?\"";
mostCurrent._lblpregunta.setText(BA.ObjectToCharSequence("¿Hay basura en la costa?"));
 //BA.debugLineNum = 585;BA.debugLine="rdOpcion1.SetBackgroundImage(LoadBitmapSample(Fi";
mostCurrent._rdopcion1.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._langlocal+"7a.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 586;BA.debugLine="rdOpcion2.SetBackgroundImage(LoadBitmapSample(Fi";
mostCurrent._rdopcion2.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._langlocal+"7b.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 587;BA.debugLine="rdOpcion3.SetBackgroundImage(LoadBitmapSample(Fi";
mostCurrent._rdopcion3.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._langlocal+"7c.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 588;BA.debugLine="rdOpcion4.SetBackgroundImage(LoadBitmapSample(Fi";
mostCurrent._rdopcion4.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._langlocal+"skipq.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 590;BA.debugLine="imgej1 = \"ej7.html\"";
mostCurrent._imgej1 = "ej7.html";
 //BA.debugLineNum = 591;BA.debugLine="btnSiguiente.Text = \"Ok\"";
mostCurrent._btnsiguiente.setText(BA.ObjectToCharSequence("Ok"));
 }else if(_numpregunta==8) { 
 //BA.debugLineNum = 596;BA.debugLine="TipoPreguntaRad(3, True)";
_tipopreguntarad((int) (3),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 597;BA.debugLine="lblPregunta.Text = \"¿Corre rápido o lento el agu";
mostCurrent._lblpregunta.setText(BA.ObjectToCharSequence("¿Corre rápido o lento el agua?"));
 //BA.debugLineNum = 598;BA.debugLine="rdOpcion1.SetBackgroundImage(LoadBitmapSample(Fi";
mostCurrent._rdopcion1.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._langlocal+"rio1a.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 599;BA.debugLine="rdOpcion2.SetBackgroundImage(LoadBitmapSample(Fi";
mostCurrent._rdopcion2.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._langlocal+"rio1b.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 600;BA.debugLine="rdOpcion3.SetBackgroundImage(LoadBitmapSample(Fi";
mostCurrent._rdopcion3.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._langlocal+"skipq.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 602;BA.debugLine="imgej1 = \"rioej1.html\"";
mostCurrent._imgej1 = "rioej1.html";
 //BA.debugLineNum = 603;BA.debugLine="btnSiguiente.Text = \"Ok\"";
mostCurrent._btnsiguiente.setText(BA.ObjectToCharSequence("Ok"));
 }else if(_numpregunta==9) { 
 //BA.debugLineNum = 605;BA.debugLine="TipoPreguntaRad(3, True)";
_tipopreguntarad((int) (3),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 606;BA.debugLine="lblPregunta.Text = \"¿Está desbordado el cauce?\"";
mostCurrent._lblpregunta.setText(BA.ObjectToCharSequence("¿Está desbordado el cauce?"));
 //BA.debugLineNum = 607;BA.debugLine="rdOpcion1.SetBackgroundImage(LoadBitmapSample(Fi";
mostCurrent._rdopcion1.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._langlocal+"rio2a.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 608;BA.debugLine="rdOpcion2.SetBackgroundImage(LoadBitmapSample(Fi";
mostCurrent._rdopcion2.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._langlocal+"rio2b.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 609;BA.debugLine="rdOpcion3.SetBackgroundImage(LoadBitmapSample(Fi";
mostCurrent._rdopcion3.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._langlocal+"skipq.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 611;BA.debugLine="imgej1 = \"rioej2.html\"";
mostCurrent._imgej1 = "rioej2.html";
 //BA.debugLineNum = 612;BA.debugLine="btnSiguiente.Text = \"Siguiente pregunta\"";
mostCurrent._btnsiguiente.setText(BA.ObjectToCharSequence("Siguiente pregunta"));
 //BA.debugLineNum = 613;BA.debugLine="btnSiguiente.Tag = \"10\"";
mostCurrent._btnsiguiente.setTag((Object)("10"));
 }else if(_numpregunta==10) { 
 //BA.debugLineNum = 615;BA.debugLine="TipoPreguntaRad(3, True)";
_tipopreguntarad((int) (3),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 616;BA.debugLine="lblPregunta.Text = \"¿Está canalizado o entubado?";
mostCurrent._lblpregunta.setText(BA.ObjectToCharSequence("¿Está canalizado o entubado?"));
 //BA.debugLineNum = 617;BA.debugLine="rdOpcion1.SetBackgroundImage(LoadBitmapSample(Fi";
mostCurrent._rdopcion1.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._langlocal+"rio3a.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 618;BA.debugLine="rdOpcion2.SetBackgroundImage(LoadBitmapSample(Fi";
mostCurrent._rdopcion2.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._langlocal+"rio3b.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 619;BA.debugLine="rdOpcion3.SetBackgroundImage(LoadBitmapSample(Fi";
mostCurrent._rdopcion3.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._langlocal+"skipq.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 621;BA.debugLine="imgej1 = \"rioej3.html\"";
mostCurrent._imgej1 = "rioej3.html";
 //BA.debugLineNum = 622;BA.debugLine="btnSiguiente.Text = \"Ok\"";
mostCurrent._btnsiguiente.setText(BA.ObjectToCharSequence("Ok"));
 }else if(_numpregunta==11) { 
 //BA.debugLineNum = 625;BA.debugLine="TipoPreguntaChk(6, False)";
_tipopreguntachk((int) (6),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 626;BA.debugLine="lblPregunta.Text = \"¿Cómo es el fondo?\"";
mostCurrent._lblpregunta.setText(BA.ObjectToCharSequence("¿Cómo es el fondo?"));
 //BA.debugLineNum = 627;BA.debugLine="chkOpcion1.SetBackgroundImage(LoadBitmapSample(F";
mostCurrent._chkopcion1.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._langlocal+"rio4a.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 628;BA.debugLine="chkOpcion2.SetBackgroundImage(LoadBitmapSample(F";
mostCurrent._chkopcion2.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._langlocal+"rio4b.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 629;BA.debugLine="chkOpcion3.SetBackgroundImage(LoadBitmapSample(F";
mostCurrent._chkopcion3.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._langlocal+"rio4c.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 630;BA.debugLine="chkOpcion4.SetBackgroundImage(LoadBitmapSample(F";
mostCurrent._chkopcion4.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._langlocal+"rio4d.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 631;BA.debugLine="chkOpcion5.SetBackgroundImage(LoadBitmapSample(F";
mostCurrent._chkopcion5.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._langlocal+"rio4e.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 632;BA.debugLine="chkOpcion6.SetBackgroundImage(LoadBitmapSample(F";
mostCurrent._chkopcion6.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._langlocal+"skipq.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 633;BA.debugLine="btnSiguiente.Text = \"Ok\"";
mostCurrent._btnsiguiente.setText(BA.ObjectToCharSequence("Ok"));
 }else if(_numpregunta==12) { 
 //BA.debugLineNum = 636;BA.debugLine="lblPregunta.Text = \"¿Cómo son las márgenes?\"";
mostCurrent._lblpregunta.setText(BA.ObjectToCharSequence("¿Cómo son las márgenes?"));
 //BA.debugLineNum = 638;BA.debugLine="TipoPreguntaRad(3, True)";
_tipopreguntarad((int) (3),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 639;BA.debugLine="rdOpcion1.SetBackgroundImage(LoadBitmapSample(Fi";
mostCurrent._rdopcion1.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._langlocal+"rio5a.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 640;BA.debugLine="rdOpcion2.SetBackgroundImage(LoadBitmapSample(Fi";
mostCurrent._rdopcion2.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._langlocal+"rio5b.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 641;BA.debugLine="rdOpcion3.SetBackgroundImage(LoadBitmapSample(Fi";
mostCurrent._rdopcion3.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._langlocal+"skipq.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 643;BA.debugLine="imgej1 = \"rioej5.html\"";
mostCurrent._imgej1 = "rioej5.html";
 //BA.debugLineNum = 644;BA.debugLine="btnSiguiente.Text = \"Ok\"";
mostCurrent._btnsiguiente.setText(BA.ObjectToCharSequence("Ok"));
 }else if(_numpregunta==13) { 
 //BA.debugLineNum = 646;BA.debugLine="lblPregunta.Text = \"¿Está sombreado el río?\"";
mostCurrent._lblpregunta.setText(BA.ObjectToCharSequence("¿Está sombreado el río?"));
 //BA.debugLineNum = 647;BA.debugLine="TipoPreguntaRad(4, True)";
_tipopreguntarad((int) (4),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 648;BA.debugLine="rdOpcion1.SetBackgroundImage(LoadBitmapSample(Fi";
mostCurrent._rdopcion1.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._langlocal+"rio6a.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 649;BA.debugLine="rdOpcion2.SetBackgroundImage(LoadBitmapSample(Fi";
mostCurrent._rdopcion2.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._langlocal+"rio6b.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 650;BA.debugLine="rdOpcion3.SetBackgroundImage(LoadBitmapSample(Fi";
mostCurrent._rdopcion3.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._langlocal+"rio6c.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 651;BA.debugLine="rdOpcion4.SetBackgroundImage(LoadBitmapSample(Fi";
mostCurrent._rdopcion4.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._langlocal+"skipq.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 653;BA.debugLine="imgej1 = \"rioej6.html\"";
mostCurrent._imgej1 = "rioej6.html";
 //BA.debugLineNum = 654;BA.debugLine="btnSiguiente.Text = \"Ok\"";
mostCurrent._btnsiguiente.setText(BA.ObjectToCharSequence("Ok"));
 };
 //BA.debugLineNum = 657;BA.debugLine="End Sub";
return "";
}
public static String  _fondogris_click() throws Exception{
 //BA.debugLineNum = 274;BA.debugLine="Sub fondogris_click";
 //BA.debugLineNum = 275;BA.debugLine="Activity.RemoveViewAt(Activity.NumberOfViews - 1)";
mostCurrent._activity.RemoveViewAt((int) (mostCurrent._activity.getNumberOfViews()-1));
 //BA.debugLineNum = 276;BA.debugLine="Activity.RemoveViewAt(Activity.NumberOfViews - 1)";
mostCurrent._activity.RemoveViewAt((int) (mostCurrent._activity.getNumberOfViews()-1));
 //BA.debugLineNum = 277;BA.debugLine="Activity.RemoveViewAt(Activity.NumberOfViews - 1)";
mostCurrent._activity.RemoveViewAt((int) (mostCurrent._activity.getNumberOfViews()-1));
 //BA.debugLineNum = 278;BA.debugLine="End Sub";
return "";
}
public static String  _generarscreenshot() throws Exception{
String _filename = "";
anywheresoftware.b4a.agraham.reflection.Reflection _obj1 = null;
anywheresoftware.b4a.agraham.reflection.Reflection _obj2 = null;
anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper _bmp = null;
anywheresoftware.b4a.objects.drawable.CanvasWrapper _c = null;
long _now = 0L;
Object[] _args = null;
String[] _types = null;
anywheresoftware.b4a.objects.streams.File.OutputStreamWrapper _out = null;
 //BA.debugLineNum = 1563;BA.debugLine="Sub GenerarScreenshot";
 //BA.debugLineNum = 1565;BA.debugLine="Dim filename As String";
_filename = "";
 //BA.debugLineNum = 1566;BA.debugLine="filename = Main.username & \"_\" & Form_Reporte.cur";
_filename = mostCurrent._main._username /*String*/ +"_"+mostCurrent._form_reporte._currentproject /*String*/ ;
 //BA.debugLineNum = 1567;BA.debugLine="Dim Obj1, Obj2 As Reflector";
_obj1 = new anywheresoftware.b4a.agraham.reflection.Reflection();
_obj2 = new anywheresoftware.b4a.agraham.reflection.Reflection();
 //BA.debugLineNum = 1568;BA.debugLine="Dim bmp As Bitmap";
_bmp = new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper();
 //BA.debugLineNum = 1569;BA.debugLine="Dim c As Canvas";
_c = new anywheresoftware.b4a.objects.drawable.CanvasWrapper();
 //BA.debugLineNum = 1570;BA.debugLine="Dim now As Long";
_now = 0L;
 //BA.debugLineNum = 1571;BA.debugLine="DateTime.DateFormat = \"yyMMddHHmmss\"";
anywheresoftware.b4a.keywords.Common.DateTime.setDateFormat("yyMMddHHmmss");
 //BA.debugLineNum = 1572;BA.debugLine="now = DateTime.Now";
_now = anywheresoftware.b4a.keywords.Common.DateTime.getNow();
 //BA.debugLineNum = 1573;BA.debugLine="Obj1.Target = Obj1.GetActivityBA";
_obj1.Target = (Object)(_obj1.GetActivityBA(processBA));
 //BA.debugLineNum = 1574;BA.debugLine="Obj1.Target = Obj1.GetField(\"vg\")";
_obj1.Target = _obj1.GetField("vg");
 //BA.debugLineNum = 1575;BA.debugLine="bmp.InitializeMutable(Activity.Width, Activity.He";
_bmp.InitializeMutable(mostCurrent._activity.getWidth(),mostCurrent._activity.getHeight());
 //BA.debugLineNum = 1576;BA.debugLine="c.Initialize2(bmp)";
_c.Initialize2((android.graphics.Bitmap)(_bmp.getObject()));
 //BA.debugLineNum = 1577;BA.debugLine="Dim args(1) As Object";
_args = new Object[(int) (1)];
{
int d0 = _args.length;
for (int i0 = 0;i0 < d0;i0++) {
_args[i0] = new Object();
}
}
;
 //BA.debugLineNum = 1578;BA.debugLine="Dim types(1) As String";
_types = new String[(int) (1)];
java.util.Arrays.fill(_types,"");
 //BA.debugLineNum = 1579;BA.debugLine="Obj2.Target = c";
_obj2.Target = (Object)(_c);
 //BA.debugLineNum = 1580;BA.debugLine="Obj2.Target = Obj2.GetField(\"canvas\")";
_obj2.Target = _obj2.GetField("canvas");
 //BA.debugLineNum = 1581;BA.debugLine="args(0) = Obj2.Target";
_args[(int) (0)] = _obj2.Target;
 //BA.debugLineNum = 1582;BA.debugLine="types(0) = \"android.graphics.Canvas\"";
_types[(int) (0)] = "android.graphics.Canvas";
 //BA.debugLineNum = 1583;BA.debugLine="Obj1.RunMethod4(\"draw\", args, types)";
_obj1.RunMethod4("draw",_args,_types);
 //BA.debugLineNum = 1584;BA.debugLine="Dim out As OutputStream";
_out = new anywheresoftware.b4a.objects.streams.File.OutputStreamWrapper();
 //BA.debugLineNum = 1585;BA.debugLine="out = File.OpenOutput(Starter.savedir & \"/PreserV";
_out = anywheresoftware.b4a.keywords.Common.File.OpenOutput(mostCurrent._starter._savedir /*String*/ +"/PreserVamos/","Final-"+_filename+".png",anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1586;BA.debugLine="bmp.WriteToStream(out, 100, \"PNG\")";
_bmp.WriteToStream((java.io.OutputStream)(_out.getObject()),(int) (100),BA.getEnumFromString(android.graphics.Bitmap.CompressFormat.class,"PNG"));
 //BA.debugLineNum = 1587;BA.debugLine="out.Close";
_out.Close();
 //BA.debugLineNum = 1588;BA.debugLine="Main.screenshotpath = Starter.savedir & \"/PreserV";
mostCurrent._main._screenshotpath /*String*/  = mostCurrent._starter._savedir /*String*/ +"/PreserVamos/"+"Final-"+_filename+".png";
 //BA.debugLineNum = 1589;BA.debugLine="End Sub";
return "";
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 13;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 14;BA.debugLine="Private pnlQuestions As Panel";
mostCurrent._pnlquestions = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 16;BA.debugLine="Private btnPregunta1 As Button";
mostCurrent._btnpregunta1 = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 17;BA.debugLine="Private btnPregunta2 As Button";
mostCurrent._btnpregunta2 = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 18;BA.debugLine="Private btnPregunta3 As Button";
mostCurrent._btnpregunta3 = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 19;BA.debugLine="Private btnPregunta4 As Button";
mostCurrent._btnpregunta4 = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 20;BA.debugLine="Private btnPregunta5 As Button";
mostCurrent._btnpregunta5 = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 21;BA.debugLine="Private btnPregunta6 As Button";
mostCurrent._btnpregunta6 = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 22;BA.debugLine="Private btnPregunta7 As Button";
mostCurrent._btnpregunta7 = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 23;BA.debugLine="Private btnPregunta8 As Button";
mostCurrent._btnpregunta8 = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 24;BA.debugLine="Private btnPregunta9 As Button";
mostCurrent._btnpregunta9 = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 25;BA.debugLine="Private btnPregunta10 As Button";
mostCurrent._btnpregunta10 = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 26;BA.debugLine="Private lblPregunta1 As Label";
mostCurrent._lblpregunta1 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 27;BA.debugLine="Private lblPregunta2 As Label";
mostCurrent._lblpregunta2 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 28;BA.debugLine="Private lblPregunta3 As Label";
mostCurrent._lblpregunta3 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 29;BA.debugLine="Private lblPregunta4 As Label";
mostCurrent._lblpregunta4 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 30;BA.debugLine="Private lblPregunta5 As Label";
mostCurrent._lblpregunta5 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 31;BA.debugLine="Private lblPregunta6 As Label";
mostCurrent._lblpregunta6 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 32;BA.debugLine="Private lblPregunta7 As Label";
mostCurrent._lblpregunta7 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 33;BA.debugLine="Private lblPregunta8 As Label";
mostCurrent._lblpregunta8 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 34;BA.debugLine="Private lblPregunta9 As Label";
mostCurrent._lblpregunta9 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 35;BA.debugLine="Private lblPregunta10 As Label";
mostCurrent._lblpregunta10 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 40;BA.debugLine="Private minimagen As String";
mostCurrent._minimagen = "";
 //BA.debugLineNum = 41;BA.debugLine="Private miniPregunta1 As ImageView";
mostCurrent._minipregunta1 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 42;BA.debugLine="Private miniPregunta2 As ImageView";
mostCurrent._minipregunta2 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 43;BA.debugLine="Private miniPregunta3 As ImageView";
mostCurrent._minipregunta3 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 44;BA.debugLine="Private miniPregunta4 As ImageView";
mostCurrent._minipregunta4 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 45;BA.debugLine="Private miniPregunta5 As ImageView";
mostCurrent._minipregunta5 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 46;BA.debugLine="Private miniPregunta6 As ImageView";
mostCurrent._minipregunta6 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 47;BA.debugLine="Private miniPregunta7 As ImageView";
mostCurrent._minipregunta7 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 48;BA.debugLine="Private miniPregunta8 As ImageView";
mostCurrent._minipregunta8 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 49;BA.debugLine="Private miniPregunta9 As ImageView";
mostCurrent._minipregunta9 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 50;BA.debugLine="Private miniPregunta10 As ImageView";
mostCurrent._minipregunta10 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 52;BA.debugLine="Private lblPregunta As Label";
mostCurrent._lblpregunta = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 54;BA.debugLine="Private btnSiguiente As Button";
mostCurrent._btnsiguiente = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 55;BA.debugLine="Private pnlOpciones As Panel";
mostCurrent._pnlopciones = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 56;BA.debugLine="Private pnlChecks As Panel";
mostCurrent._pnlchecks = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 59;BA.debugLine="Private rdOpcion1 As RadioButton";
mostCurrent._rdopcion1 = new anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper();
 //BA.debugLineNum = 60;BA.debugLine="Private rdOpcion2 As RadioButton";
mostCurrent._rdopcion2 = new anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper();
 //BA.debugLineNum = 61;BA.debugLine="Private rdOpcion3 As RadioButton";
mostCurrent._rdopcion3 = new anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper();
 //BA.debugLineNum = 62;BA.debugLine="Private rdOpcion4 As RadioButton";
mostCurrent._rdopcion4 = new anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper();
 //BA.debugLineNum = 63;BA.debugLine="Private rdOpcion5 As RadioButton";
mostCurrent._rdopcion5 = new anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper();
 //BA.debugLineNum = 64;BA.debugLine="Private rdOpcion6 As RadioButton";
mostCurrent._rdopcion6 = new anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper();
 //BA.debugLineNum = 65;BA.debugLine="Private chkOpcion3 As CheckBox";
mostCurrent._chkopcion3 = new anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper();
 //BA.debugLineNum = 66;BA.debugLine="Private chkOpcion2 As CheckBox";
mostCurrent._chkopcion2 = new anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper();
 //BA.debugLineNum = 67;BA.debugLine="Private chkOpcion1 As CheckBox";
mostCurrent._chkopcion1 = new anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper();
 //BA.debugLineNum = 68;BA.debugLine="Private chkOpcion4 As CheckBox";
mostCurrent._chkopcion4 = new anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper();
 //BA.debugLineNum = 69;BA.debugLine="Private chkOpcion5 As CheckBox";
mostCurrent._chkopcion5 = new anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper();
 //BA.debugLineNum = 70;BA.debugLine="Private chkOpcion6 As CheckBox";
mostCurrent._chkopcion6 = new anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper();
 //BA.debugLineNum = 73;BA.debugLine="Dim imgej1 As String = \"\"";
mostCurrent._imgej1 = "";
 //BA.debugLineNum = 75;BA.debugLine="Dim explicacionviewer As WebView";
mostCurrent._explicacionviewer = new anywheresoftware.b4a.objects.WebViewWrapper();
 //BA.debugLineNum = 76;BA.debugLine="Private btnRdOpcion1 As Button";
mostCurrent._btnrdopcion1 = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 79;BA.debugLine="Dim csv As Canvas";
mostCurrent._csv = new anywheresoftware.b4a.objects.drawable.CanvasWrapper();
 //BA.debugLineNum = 80;BA.debugLine="Dim rec As Rect";
mostCurrent._rec = new anywheresoftware.b4a.objects.drawable.CanvasWrapper.RectWrapper();
 //BA.debugLineNum = 81;BA.debugLine="Dim csvChk As Canvas";
mostCurrent._csvchk = new anywheresoftware.b4a.objects.drawable.CanvasWrapper();
 //BA.debugLineNum = 82;BA.debugLine="Dim recChk As Rect";
mostCurrent._recchk = new anywheresoftware.b4a.objects.drawable.CanvasWrapper.RectWrapper();
 //BA.debugLineNum = 85;BA.debugLine="Private pgbPreguntas As Int";
_pgbpreguntas = 0;
 //BA.debugLineNum = 86;BA.debugLine="Dim cantidadpreguntas As Int";
_cantidadpreguntas = 0;
 //BA.debugLineNum = 87;BA.debugLine="Dim currentpregunta As Int";
_currentpregunta = 0;
 //BA.debugLineNum = 89;BA.debugLine="Private pnlPreguntas As Panel";
mostCurrent._pnlpreguntas = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 90;BA.debugLine="Private lblEstado As Label";
mostCurrent._lblestado = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 91;BA.debugLine="Private pnlResultados As Panel";
mostCurrent._pnlresultados = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 92;BA.debugLine="Private btnTerminar As Button";
mostCurrent._btnterminar = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 94;BA.debugLine="Private scrResultados As ScrollView";
mostCurrent._scrresultados = new anywheresoftware.b4a.objects.ScrollViewWrapper();
 //BA.debugLineNum = 95;BA.debugLine="Private Panel1 As Panel";
mostCurrent._panel1 = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 96;BA.debugLine="Private btnCerrar As Button";
mostCurrent._btncerrar = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 97;BA.debugLine="Private miniPreguntaEntubado As ImageView";
mostCurrent._minipreguntaentubado = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 98;BA.debugLine="Private lblTituloResultados As Label";
mostCurrent._lbltituloresultados = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 99;BA.debugLine="Private Label2 As Label";
mostCurrent._label2 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 100;BA.debugLine="Private btnShare As Button";
mostCurrent._btnshare = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 102;BA.debugLine="Private langLocal As String";
mostCurrent._langlocal = "";
 //BA.debugLineNum = 105;BA.debugLine="Dim valorind1 As String";
mostCurrent._valorind1 = "";
 //BA.debugLineNum = 106;BA.debugLine="Dim valorind2 As String";
mostCurrent._valorind2 = "";
 //BA.debugLineNum = 107;BA.debugLine="Dim valorind3 As String";
mostCurrent._valorind3 = "";
 //BA.debugLineNum = 108;BA.debugLine="Dim valorind4 As String";
mostCurrent._valorind4 = "";
 //BA.debugLineNum = 109;BA.debugLine="Dim valorind5 As String";
mostCurrent._valorind5 = "";
 //BA.debugLineNum = 110;BA.debugLine="Dim valorind6 As String";
mostCurrent._valorind6 = "";
 //BA.debugLineNum = 111;BA.debugLine="Dim valorind7 As String";
mostCurrent._valorind7 = "";
 //BA.debugLineNum = 112;BA.debugLine="Dim valorind8 As String";
mostCurrent._valorind8 = "";
 //BA.debugLineNum = 113;BA.debugLine="Dim valorind9 As String";
mostCurrent._valorind9 = "";
 //BA.debugLineNum = 114;BA.debugLine="Dim valorind10 As String";
mostCurrent._valorind10 = "";
 //BA.debugLineNum = 115;BA.debugLine="Dim valorind11 As String";
mostCurrent._valorind11 = "";
 //BA.debugLineNum = 116;BA.debugLine="Dim valorind12 As String";
mostCurrent._valorind12 = "";
 //BA.debugLineNum = 117;BA.debugLine="Dim valorind13 As String";
mostCurrent._valorind13 = "";
 //BA.debugLineNum = 118;BA.debugLine="Dim valorind14 As String";
mostCurrent._valorind14 = "";
 //BA.debugLineNum = 119;BA.debugLine="Dim valorind15 As String";
mostCurrent._valorind15 = "";
 //BA.debugLineNum = 120;BA.debugLine="Dim valorind16 As String";
mostCurrent._valorind16 = "";
 //BA.debugLineNum = 121;BA.debugLine="Dim preguntanumero As String";
mostCurrent._preguntanumero = "";
 //BA.debugLineNum = 122;BA.debugLine="Dim dateandtime As String";
mostCurrent._dateandtime = "";
 //BA.debugLineNum = 123;BA.debugLine="Dim valorcalidad As Int";
_valorcalidad = 0;
 //BA.debugLineNum = 124;BA.debugLine="Dim valorNS As Int";
_valorns = 0;
 //BA.debugLineNum = 127;BA.debugLine="Private btnMasDetalle As Button";
mostCurrent._btnmasdetalle = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 128;BA.debugLine="Private Gauge1 As Gauge";
mostCurrent._gauge1 = new ilpla.appear.gauge();
 //BA.debugLineNum = 129;BA.debugLine="Private xui As XUI";
mostCurrent._xui = new anywheresoftware.b4a.objects.B4XViewWrapper.XUI();
 //BA.debugLineNum = 131;BA.debugLine="End Sub";
return "";
}
public static String  _guardareval() throws Exception{
anywheresoftware.b4a.objects.collections.Map _map1 = null;
 //BA.debugLineNum = 1505;BA.debugLine="Sub GuardarEval";
 //BA.debugLineNum = 1507;BA.debugLine="Dim Map1 As Map";
_map1 = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 1508;BA.debugLine="Map1.Initialize";
_map1.Initialize();
 //BA.debugLineNum = 1509;BA.debugLine="Map1.Put(\"Id\", Form_Reporte.currentproject)";
_map1.Put((Object)("Id"),(Object)(mostCurrent._form_reporte._currentproject /*String*/ ));
 //BA.debugLineNum = 1510;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"evals\", \"val";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals","valorind1",(Object)(mostCurrent._valorind1),_map1);
 //BA.debugLineNum = 1511;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"evals\", \"val";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals","valorind2",(Object)(mostCurrent._valorind2),_map1);
 //BA.debugLineNum = 1512;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"evals\", \"val";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals","valorind3",(Object)(mostCurrent._valorind3),_map1);
 //BA.debugLineNum = 1513;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"evals\", \"val";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals","valorind4",(Object)(mostCurrent._valorind4),_map1);
 //BA.debugLineNum = 1514;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"evals\", \"val";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals","valorind5",(Object)(mostCurrent._valorind5),_map1);
 //BA.debugLineNum = 1515;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"evals\", \"val";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals","valorind6",(Object)(mostCurrent._valorind6),_map1);
 //BA.debugLineNum = 1516;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"evals\", \"val";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals","valorind7",(Object)(mostCurrent._valorind7),_map1);
 //BA.debugLineNum = 1517;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"evals\", \"val";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals","valorind8",(Object)(mostCurrent._valorind8),_map1);
 //BA.debugLineNum = 1518;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"evals\", \"val";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals","valorind9",(Object)(mostCurrent._valorind9),_map1);
 //BA.debugLineNum = 1519;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"evals\", \"val";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals","valorind10",(Object)(mostCurrent._valorind10),_map1);
 //BA.debugLineNum = 1520;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"evals\", \"val";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals","valorind11",(Object)(mostCurrent._valorind11),_map1);
 //BA.debugLineNum = 1521;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"evals\", \"val";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals","valorind12",(Object)(mostCurrent._valorind12),_map1);
 //BA.debugLineNum = 1522;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"evals\", \"val";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals","valorind13",(Object)(mostCurrent._valorind13),_map1);
 //BA.debugLineNum = 1523;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"evals\", \"val";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals","valorind14",(Object)(mostCurrent._valorind14),_map1);
 //BA.debugLineNum = 1524;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"evals\", \"val";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals","valorind15",(Object)(mostCurrent._valorind15),_map1);
 //BA.debugLineNum = 1525;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"evals\", \"val";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals","valorind15",(Object)(mostCurrent._valorind15),_map1);
 //BA.debugLineNum = 1527;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"evals\", \"val";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals","valorind20",(Object)(_valorns),_map1);
 //BA.debugLineNum = 1528;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"evals\", \"val";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals","valorcalidad",(Object)(_valorcalidad),_map1);
 //BA.debugLineNum = 1529;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"evals\", \"geo";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals","georeferencedDate",(Object)(mostCurrent._dateandtime),_map1);
 //BA.debugLineNum = 1531;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"evals\", \"ter";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals","terminado",(Object)("si"),_map1);
 //BA.debugLineNum = 1533;BA.debugLine="End Sub";
return "";
}
public static String  _pnlresultados_click() throws Exception{
 //BA.debugLineNum = 1552;BA.debugLine="Sub pnlResultados_Click";
 //BA.debugLineNum = 1553;BA.debugLine="Return True";
if (true) return BA.ObjectToString(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1554;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 7;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 9;BA.debugLine="Dim tiporio As String";
_tiporio = "";
 //BA.debugLineNum = 11;BA.debugLine="End Sub";
return "";
}
public static String  _rdopcion1_checkedchange(boolean _checked) throws Exception{
anywheresoftware.b4a.objects.drawable.ColorDrawable _cd = null;
 //BA.debugLineNum = 200;BA.debugLine="Sub rdOpcion1_CheckedChange(Checked As Boolean)";
 //BA.debugLineNum = 201;BA.debugLine="Dim cd As ColorDrawable";
_cd = new anywheresoftware.b4a.objects.drawable.ColorDrawable();
 //BA.debugLineNum = 203;BA.debugLine="csv.DrawColor(0)";
mostCurrent._csv.DrawColor((int) (0));
 //BA.debugLineNum = 204;BA.debugLine="cd.Initialize(Colors.ARGB(100,255,255,255), 0)";
_cd.Initialize(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (100),(int) (255),(int) (255),(int) (255)),(int) (0));
 //BA.debugLineNum = 205;BA.debugLine="rec.Initialize(rdOpcion1.Left, rdOpcion1.Top, rdO";
mostCurrent._rec.Initialize(mostCurrent._rdopcion1.getLeft(),mostCurrent._rdopcion1.getTop(),(int) (mostCurrent._rdopcion1.getWidth()+mostCurrent._rdopcion1.getLeft()),(int) (mostCurrent._rdopcion1.getHeight()+mostCurrent._rdopcion1.getTop()));
 //BA.debugLineNum = 206;BA.debugLine="csv.DrawDrawable(cd, rec)";
mostCurrent._csv.DrawDrawable((android.graphics.drawable.Drawable)(_cd.getObject()),(android.graphics.Rect)(mostCurrent._rec.getObject()));
 //BA.debugLineNum = 208;BA.debugLine="End Sub";
return "";
}
public static String  _rdopcion2_checkedchange(boolean _checked) throws Exception{
anywheresoftware.b4a.objects.drawable.ColorDrawable _cd = null;
 //BA.debugLineNum = 209;BA.debugLine="Sub rdOpcion2_CheckedChange(Checked As Boolean)";
 //BA.debugLineNum = 210;BA.debugLine="Dim cd As ColorDrawable";
_cd = new anywheresoftware.b4a.objects.drawable.ColorDrawable();
 //BA.debugLineNum = 211;BA.debugLine="csv.DrawColor(0)";
mostCurrent._csv.DrawColor((int) (0));
 //BA.debugLineNum = 212;BA.debugLine="cd.Initialize(Colors.ARGB(100,255,255,255), 0)";
_cd.Initialize(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (100),(int) (255),(int) (255),(int) (255)),(int) (0));
 //BA.debugLineNum = 213;BA.debugLine="rec.Initialize(rdOpcion2.Left, rdOpcion2.Top, rd";
mostCurrent._rec.Initialize(mostCurrent._rdopcion2.getLeft(),mostCurrent._rdopcion2.getTop(),(int) (mostCurrent._rdopcion2.getWidth()+mostCurrent._rdopcion2.getLeft()),(int) (mostCurrent._rdopcion2.getHeight()+mostCurrent._rdopcion2.getTop()));
 //BA.debugLineNum = 214;BA.debugLine="csv.DrawDrawable(cd, rec)";
mostCurrent._csv.DrawDrawable((android.graphics.drawable.Drawable)(_cd.getObject()),(android.graphics.Rect)(mostCurrent._rec.getObject()));
 //BA.debugLineNum = 215;BA.debugLine="End Sub";
return "";
}
public static String  _rdopcion3_checkedchange(boolean _checked) throws Exception{
anywheresoftware.b4a.objects.drawable.ColorDrawable _cd = null;
 //BA.debugLineNum = 216;BA.debugLine="Sub rdOpcion3_CheckedChange(Checked As Boolean)";
 //BA.debugLineNum = 217;BA.debugLine="Dim cd As ColorDrawable";
_cd = new anywheresoftware.b4a.objects.drawable.ColorDrawable();
 //BA.debugLineNum = 218;BA.debugLine="csv.DrawColor(0)";
mostCurrent._csv.DrawColor((int) (0));
 //BA.debugLineNum = 219;BA.debugLine="cd.Initialize(Colors.ARGB(100,255,255,255), 0)";
_cd.Initialize(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (100),(int) (255),(int) (255),(int) (255)),(int) (0));
 //BA.debugLineNum = 220;BA.debugLine="rec.Initialize(rdOpcion3.Left, rdOpcion3.Top, rd";
mostCurrent._rec.Initialize(mostCurrent._rdopcion3.getLeft(),mostCurrent._rdopcion3.getTop(),(int) (mostCurrent._rdopcion3.getWidth()+mostCurrent._rdopcion3.getLeft()),(int) (mostCurrent._rdopcion3.getHeight()+mostCurrent._rdopcion3.getTop()));
 //BA.debugLineNum = 221;BA.debugLine="csv.DrawDrawable(cd, rec)";
mostCurrent._csv.DrawDrawable((android.graphics.drawable.Drawable)(_cd.getObject()),(android.graphics.Rect)(mostCurrent._rec.getObject()));
 //BA.debugLineNum = 222;BA.debugLine="End Sub";
return "";
}
public static String  _rdopcion4_checkedchange(boolean _checked) throws Exception{
anywheresoftware.b4a.objects.drawable.ColorDrawable _cd = null;
 //BA.debugLineNum = 223;BA.debugLine="Sub rdOpcion4_CheckedChange(Checked As Boolean)";
 //BA.debugLineNum = 224;BA.debugLine="Dim cd As ColorDrawable";
_cd = new anywheresoftware.b4a.objects.drawable.ColorDrawable();
 //BA.debugLineNum = 225;BA.debugLine="csv.DrawColor(0)";
mostCurrent._csv.DrawColor((int) (0));
 //BA.debugLineNum = 226;BA.debugLine="cd.Initialize(Colors.ARGB(100,255,255,255), 0)";
_cd.Initialize(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (100),(int) (255),(int) (255),(int) (255)),(int) (0));
 //BA.debugLineNum = 227;BA.debugLine="rec.Initialize(rdOpcion4.Left, rdOpcion4.Top, rd";
mostCurrent._rec.Initialize(mostCurrent._rdopcion4.getLeft(),mostCurrent._rdopcion4.getTop(),(int) (mostCurrent._rdopcion4.getWidth()+mostCurrent._rdopcion4.getLeft()),(int) (mostCurrent._rdopcion4.getHeight()+mostCurrent._rdopcion4.getTop()));
 //BA.debugLineNum = 228;BA.debugLine="csv.DrawDrawable(cd, rec)";
mostCurrent._csv.DrawDrawable((android.graphics.drawable.Drawable)(_cd.getObject()),(android.graphics.Rect)(mostCurrent._rec.getObject()));
 //BA.debugLineNum = 229;BA.debugLine="End Sub";
return "";
}
public static String  _rdopcion5_checkedchange(boolean _checked) throws Exception{
anywheresoftware.b4a.objects.drawable.ColorDrawable _cd = null;
 //BA.debugLineNum = 230;BA.debugLine="Sub rdOpcion5_CheckedChange(Checked As Boolean)";
 //BA.debugLineNum = 231;BA.debugLine="Dim cd As ColorDrawable";
_cd = new anywheresoftware.b4a.objects.drawable.ColorDrawable();
 //BA.debugLineNum = 232;BA.debugLine="csv.DrawColor(0)";
mostCurrent._csv.DrawColor((int) (0));
 //BA.debugLineNum = 233;BA.debugLine="cd.Initialize(Colors.ARGB(100,255,255,255), 0)";
_cd.Initialize(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (100),(int) (255),(int) (255),(int) (255)),(int) (0));
 //BA.debugLineNum = 234;BA.debugLine="rec.Initialize(rdOpcion5.Left, rdOpcion5.Top, rd";
mostCurrent._rec.Initialize(mostCurrent._rdopcion5.getLeft(),mostCurrent._rdopcion5.getTop(),(int) (mostCurrent._rdopcion5.getWidth()+mostCurrent._rdopcion5.getLeft()),(int) (mostCurrent._rdopcion5.getHeight()+mostCurrent._rdopcion5.getTop()));
 //BA.debugLineNum = 235;BA.debugLine="csv.DrawDrawable(cd, rec)";
mostCurrent._csv.DrawDrawable((android.graphics.drawable.Drawable)(_cd.getObject()),(android.graphics.Rect)(mostCurrent._rec.getObject()));
 //BA.debugLineNum = 236;BA.debugLine="End Sub";
return "";
}
public static String  _rdopcion6_checkedchange(boolean _checked) throws Exception{
anywheresoftware.b4a.objects.drawable.ColorDrawable _cd = null;
 //BA.debugLineNum = 237;BA.debugLine="Sub rdOpcion6_CheckedChange(Checked As Boolean)";
 //BA.debugLineNum = 238;BA.debugLine="Dim cd As ColorDrawable";
_cd = new anywheresoftware.b4a.objects.drawable.ColorDrawable();
 //BA.debugLineNum = 239;BA.debugLine="csv.DrawColor(0)";
mostCurrent._csv.DrawColor((int) (0));
 //BA.debugLineNum = 240;BA.debugLine="cd.Initialize(Colors.ARGB(100,255,255,255), 0)";
_cd.Initialize(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (100),(int) (255),(int) (255),(int) (255)),(int) (0));
 //BA.debugLineNum = 241;BA.debugLine="rec.Initialize(rdOpcion6.Left, rdOpcion6.Top, rd";
mostCurrent._rec.Initialize(mostCurrent._rdopcion6.getLeft(),mostCurrent._rdopcion6.getTop(),(int) (mostCurrent._rdopcion6.getWidth()+mostCurrent._rdopcion6.getLeft()),(int) (mostCurrent._rdopcion6.getHeight()+mostCurrent._rdopcion6.getTop()));
 //BA.debugLineNum = 242;BA.debugLine="csv.DrawDrawable(cd, rec)";
mostCurrent._csv.DrawDrawable((android.graphics.drawable.Drawable)(_cd.getObject()),(android.graphics.Rect)(mostCurrent._rec.getObject()));
 //BA.debugLineNum = 243;BA.debugLine="End Sub";
return "";
}
public static String  _terminareval() throws Exception{
int _totpreg = 0;
 //BA.debugLineNum = 1360;BA.debugLine="Sub TerminarEval";
 //BA.debugLineNum = 1363;BA.debugLine="pnlResultados.Visible = True";
mostCurrent._pnlresultados.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1364;BA.debugLine="pnlResultados.BringToFront";
mostCurrent._pnlresultados.BringToFront();
 //BA.debugLineNum = 1366;BA.debugLine="Dim totpreg As Int";
_totpreg = 0;
 //BA.debugLineNum = 1367;BA.debugLine="If tiporio = \"llanura\" Then";
if ((_tiporio).equals("llanura")) { 
 //BA.debugLineNum = 1368;BA.debugLine="totpreg = 120";
_totpreg = (int) (120);
 }else if((_tiporio).equals("montana")) { 
 //BA.debugLineNum = 1370;BA.debugLine="totpreg = 130";
_totpreg = (int) (130);
 };
 //BA.debugLineNum = 1373;BA.debugLine="If valorind1 <> \"NS\" Then";
if ((mostCurrent._valorind1).equals("NS") == false) { 
 //BA.debugLineNum = 1374;BA.debugLine="valorind1  = Round2(((valorind1 * 100) / totpr";
mostCurrent._valorind1 = BA.NumberToString(anywheresoftware.b4a.keywords.Common.Round2((((double)(Double.parseDouble(mostCurrent._valorind1))*100)/(double)_totpreg),(int) (1)));
 //BA.debugLineNum = 1375;BA.debugLine="valorcalidad = valorcalidad + valorind1";
_valorcalidad = (int) (_valorcalidad+(double)(Double.parseDouble(mostCurrent._valorind1)));
 }else {
 //BA.debugLineNum = 1377;BA.debugLine="valorNS = valorNS + 1";
_valorns = (int) (_valorns+1);
 };
 //BA.debugLineNum = 1379;BA.debugLine="If valorind2 <> \"NS\" Then";
if ((mostCurrent._valorind2).equals("NS") == false) { 
 //BA.debugLineNum = 1380;BA.debugLine="valorind2  = Round2(((valorind2 * 100) / totpr";
mostCurrent._valorind2 = BA.NumberToString(anywheresoftware.b4a.keywords.Common.Round2((((double)(Double.parseDouble(mostCurrent._valorind2))*100)/(double)_totpreg),(int) (1)));
 //BA.debugLineNum = 1381;BA.debugLine="valorcalidad = valorcalidad + valorind2";
_valorcalidad = (int) (_valorcalidad+(double)(Double.parseDouble(mostCurrent._valorind2)));
 }else {
 //BA.debugLineNum = 1383;BA.debugLine="valorNS = valorNS + 1";
_valorns = (int) (_valorns+1);
 };
 //BA.debugLineNum = 1385;BA.debugLine="If valorind3 <> \"NS\" Then";
if ((mostCurrent._valorind3).equals("NS") == false) { 
 //BA.debugLineNum = 1386;BA.debugLine="valorind3  = Round2(((valorind3 * 100) / totpr";
mostCurrent._valorind3 = BA.NumberToString(anywheresoftware.b4a.keywords.Common.Round2((((double)(Double.parseDouble(mostCurrent._valorind3))*100)/(double)_totpreg),(int) (1)));
 //BA.debugLineNum = 1387;BA.debugLine="valorcalidad = valorcalidad + valorind3";
_valorcalidad = (int) (_valorcalidad+(double)(Double.parseDouble(mostCurrent._valorind3)));
 }else {
 //BA.debugLineNum = 1389;BA.debugLine="valorNS = valorNS + 1";
_valorns = (int) (_valorns+1);
 };
 //BA.debugLineNum = 1391;BA.debugLine="If valorind4 <> \"NS\" Then";
if ((mostCurrent._valorind4).equals("NS") == false) { 
 //BA.debugLineNum = 1392;BA.debugLine="valorind4  = Round2(((valorind4 * 100) / totpr";
mostCurrent._valorind4 = BA.NumberToString(anywheresoftware.b4a.keywords.Common.Round2((((double)(Double.parseDouble(mostCurrent._valorind4))*100)/(double)_totpreg),(int) (1)));
 //BA.debugLineNum = 1393;BA.debugLine="valorcalidad = valorcalidad + valorind4";
_valorcalidad = (int) (_valorcalidad+(double)(Double.parseDouble(mostCurrent._valorind4)));
 }else {
 //BA.debugLineNum = 1395;BA.debugLine="valorNS = valorNS + 1";
_valorns = (int) (_valorns+1);
 };
 //BA.debugLineNum = 1397;BA.debugLine="If valorind5 <> \"NS\" Then";
if ((mostCurrent._valorind5).equals("NS") == false) { 
 //BA.debugLineNum = 1398;BA.debugLine="valorind5  = Round2(((valorind5 * 100) / totpr";
mostCurrent._valorind5 = BA.NumberToString(anywheresoftware.b4a.keywords.Common.Round2((((double)(Double.parseDouble(mostCurrent._valorind5))*100)/(double)_totpreg),(int) (1)));
 //BA.debugLineNum = 1399;BA.debugLine="valorcalidad = valorcalidad + valorind5";
_valorcalidad = (int) (_valorcalidad+(double)(Double.parseDouble(mostCurrent._valorind5)));
 }else {
 //BA.debugLineNum = 1401;BA.debugLine="valorNS = valorNS + 1";
_valorns = (int) (_valorns+1);
 };
 //BA.debugLineNum = 1403;BA.debugLine="If valorind6 <> \"NS\" Then";
if ((mostCurrent._valorind6).equals("NS") == false) { 
 //BA.debugLineNum = 1404;BA.debugLine="valorind6  = Round2(((valorind6 * 100) / totpr";
mostCurrent._valorind6 = BA.NumberToString(anywheresoftware.b4a.keywords.Common.Round2((((double)(Double.parseDouble(mostCurrent._valorind6))*100)/(double)_totpreg),(int) (1)));
 //BA.debugLineNum = 1405;BA.debugLine="valorcalidad = valorcalidad + valorind6";
_valorcalidad = (int) (_valorcalidad+(double)(Double.parseDouble(mostCurrent._valorind6)));
 }else {
 //BA.debugLineNum = 1407;BA.debugLine="valorNS = valorNS + 1";
_valorns = (int) (_valorns+1);
 };
 //BA.debugLineNum = 1409;BA.debugLine="If valorind7 <> \"NS\" Then";
if ((mostCurrent._valorind7).equals("NS") == false) { 
 //BA.debugLineNum = 1410;BA.debugLine="valorind7  = Round2(((valorind7 * 100) / totpr";
mostCurrent._valorind7 = BA.NumberToString(anywheresoftware.b4a.keywords.Common.Round2((((double)(Double.parseDouble(mostCurrent._valorind7))*100)/(double)_totpreg),(int) (1)));
 //BA.debugLineNum = 1411;BA.debugLine="valorcalidad = valorcalidad + valorind7";
_valorcalidad = (int) (_valorcalidad+(double)(Double.parseDouble(mostCurrent._valorind7)));
 }else {
 //BA.debugLineNum = 1413;BA.debugLine="valorNS = valorNS + 1";
_valorns = (int) (_valorns+1);
 };
 //BA.debugLineNum = 1415;BA.debugLine="If valorind8 <> \"NS\" Then";
if ((mostCurrent._valorind8).equals("NS") == false) { 
 //BA.debugLineNum = 1416;BA.debugLine="valorind8  = Round2(((valorind8 * 100) / totpr";
mostCurrent._valorind8 = BA.NumberToString(anywheresoftware.b4a.keywords.Common.Round2((((double)(Double.parseDouble(mostCurrent._valorind8))*100)/(double)_totpreg),(int) (1)));
 //BA.debugLineNum = 1417;BA.debugLine="valorcalidad = valorcalidad + valorind8";
_valorcalidad = (int) (_valorcalidad+(double)(Double.parseDouble(mostCurrent._valorind8)));
 }else {
 //BA.debugLineNum = 1419;BA.debugLine="valorNS = valorNS + 1";
_valorns = (int) (_valorns+1);
 };
 //BA.debugLineNum = 1421;BA.debugLine="If valorind9 <> \"NS\" Then";
if ((mostCurrent._valorind9).equals("NS") == false) { 
 //BA.debugLineNum = 1422;BA.debugLine="valorind9  = Round2(((valorind9 * 100) / totpr";
mostCurrent._valorind9 = BA.NumberToString(anywheresoftware.b4a.keywords.Common.Round2((((double)(Double.parseDouble(mostCurrent._valorind9))*100)/(double)_totpreg),(int) (1)));
 //BA.debugLineNum = 1423;BA.debugLine="valorcalidad = valorcalidad + valorind9";
_valorcalidad = (int) (_valorcalidad+(double)(Double.parseDouble(mostCurrent._valorind9)));
 }else {
 //BA.debugLineNum = 1425;BA.debugLine="valorNS = valorNS + 1";
_valorns = (int) (_valorns+1);
 };
 //BA.debugLineNum = 1427;BA.debugLine="If valorind10 <> \"NS\" Then";
if ((mostCurrent._valorind10).equals("NS") == false) { 
 //BA.debugLineNum = 1428;BA.debugLine="valorind10  = Round2(((valorind10 * 100) / tot";
mostCurrent._valorind10 = BA.NumberToString(anywheresoftware.b4a.keywords.Common.Round2((((double)(Double.parseDouble(mostCurrent._valorind10))*100)/(double)_totpreg),(int) (1)));
 //BA.debugLineNum = 1429;BA.debugLine="valorcalidad = valorcalidad + valorind10";
_valorcalidad = (int) (_valorcalidad+(double)(Double.parseDouble(mostCurrent._valorind10)));
 }else {
 //BA.debugLineNum = 1431;BA.debugLine="valorNS = valorNS + 1";
_valorns = (int) (_valorns+1);
 };
 //BA.debugLineNum = 1433;BA.debugLine="If valorind11 <> \"NS\" Then";
if ((mostCurrent._valorind11).equals("NS") == false) { 
 //BA.debugLineNum = 1434;BA.debugLine="valorind11  = Round2(((valorind11 * 100) / tot";
mostCurrent._valorind11 = BA.NumberToString(anywheresoftware.b4a.keywords.Common.Round2((((double)(Double.parseDouble(mostCurrent._valorind11))*100)/(double)_totpreg),(int) (1)));
 //BA.debugLineNum = 1435;BA.debugLine="valorcalidad = valorcalidad + valorind11";
_valorcalidad = (int) (_valorcalidad+(double)(Double.parseDouble(mostCurrent._valorind11)));
 }else {
 //BA.debugLineNum = 1437;BA.debugLine="valorNS = valorNS + 1";
_valorns = (int) (_valorns+1);
 };
 //BA.debugLineNum = 1439;BA.debugLine="If valorind12 <> \"NS\" Then";
if ((mostCurrent._valorind12).equals("NS") == false) { 
 //BA.debugLineNum = 1440;BA.debugLine="valorind12  = Round2(((valorind12 * 100) / tot";
mostCurrent._valorind12 = BA.NumberToString(anywheresoftware.b4a.keywords.Common.Round2((((double)(Double.parseDouble(mostCurrent._valorind12))*100)/(double)_totpreg),(int) (1)));
 //BA.debugLineNum = 1441;BA.debugLine="valorcalidad = valorcalidad + valorind12";
_valorcalidad = (int) (_valorcalidad+(double)(Double.parseDouble(mostCurrent._valorind12)));
 }else {
 //BA.debugLineNum = 1443;BA.debugLine="valorNS = valorNS + 1";
_valorns = (int) (_valorns+1);
 };
 //BA.debugLineNum = 1446;BA.debugLine="If tiporio = \"montana\" And valorind13 <> \"NS\" Th";
if ((_tiporio).equals("montana") && (mostCurrent._valorind13).equals("NS") == false) { 
 //BA.debugLineNum = 1447;BA.debugLine="valorind13  = Round2(((valorind13 * 100) / tot";
mostCurrent._valorind13 = BA.NumberToString(anywheresoftware.b4a.keywords.Common.Round2((((double)(Double.parseDouble(mostCurrent._valorind13))*100)/(double)_totpreg),(int) (1)));
 //BA.debugLineNum = 1448;BA.debugLine="valorcalidad = valorcalidad + valorind13";
_valorcalidad = (int) (_valorcalidad+(double)(Double.parseDouble(mostCurrent._valorind13)));
 }else {
 //BA.debugLineNum = 1450;BA.debugLine="valorNS = valorNS + 1";
_valorns = (int) (_valorns+1);
 };
 //BA.debugLineNum = 1455;BA.debugLine="If (valorNS * 100) / (totpreg / 10) > 50  Then";
if ((_valorns*100)/(double)(_totpreg/(double)10)>50) { 
 //BA.debugLineNum = 1456;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 1457;BA.debugLine="MsgboxAsync(\"Ha salteado muchas opciones en la";
anywheresoftware.b4a.keywords.Common.MsgboxAsync(BA.ObjectToCharSequence("Ha salteado muchas opciones en la encuesta, el estado del hábitat no puede ser calculado con precisión"),BA.ObjectToCharSequence("Oops!"),processBA);
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 1459;BA.debugLine="MsgboxAsync(\"You have skipped too many questio";
anywheresoftware.b4a.keywords.Common.MsgboxAsync(BA.ObjectToCharSequence("You have skipped too many questions, the habitat status cannot be calculated accurately"),BA.ObjectToCharSequence("Oops!"),processBA);
 };
 //BA.debugLineNum = 1461;BA.debugLine="pnlResultados.Visible = False";
mostCurrent._pnlresultados.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1462;BA.debugLine="Return True";
if (true) return BA.ObjectToString(anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 1465;BA.debugLine="valorNS = Round2((valorNS * 100) / (totpreg/10)";
_valorns = (int) (anywheresoftware.b4a.keywords.Common.Round2((_valorns*100)/(double)(_totpreg/(double)10),(int) (1)));
 //BA.debugLineNum = 1467;BA.debugLine="valorcalidad = Round2(valorcalidad,1)";
_valorcalidad = (int) (anywheresoftware.b4a.keywords.Common.Round2(_valorcalidad,(int) (1)));
 //BA.debugLineNum = 1471;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 1472;BA.debugLine="If valorcalidad < 20 Then";
if (_valorcalidad<20) { 
 //BA.debugLineNum = 1473;BA.debugLine="lblEstado.Text = \"Muy malo\"";
mostCurrent._lblestado.setText(BA.ObjectToCharSequence("Muy malo"));
 }else if(_valorcalidad>=20 && _valorcalidad<40) { 
 //BA.debugLineNum = 1475;BA.debugLine="lblEstado.Text = \"Malo\"";
mostCurrent._lblestado.setText(BA.ObjectToCharSequence("Malo"));
 }else if(_valorcalidad>=40 && _valorcalidad<60) { 
 //BA.debugLineNum = 1477;BA.debugLine="lblEstado.Text = \"Regular\"";
mostCurrent._lblestado.setText(BA.ObjectToCharSequence("Regular"));
 }else if(_valorcalidad>=60 && _valorcalidad<80) { 
 //BA.debugLineNum = 1479;BA.debugLine="lblEstado.Text = \"Bueno\"";
mostCurrent._lblestado.setText(BA.ObjectToCharSequence("Bueno"));
 }else if(_valorcalidad>=80) { 
 //BA.debugLineNum = 1481;BA.debugLine="lblEstado.Text = \"Muy bueno!\"";
mostCurrent._lblestado.setText(BA.ObjectToCharSequence("Muy bueno!"));
 };
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 1484;BA.debugLine="If valorcalidad < 20 Then";
if (_valorcalidad<20) { 
 //BA.debugLineNum = 1485;BA.debugLine="lblEstado.Text = \"Very bad\"";
mostCurrent._lblestado.setText(BA.ObjectToCharSequence("Very bad"));
 }else if(_valorcalidad>=20 && _valorcalidad<40) { 
 //BA.debugLineNum = 1487;BA.debugLine="lblEstado.Text = \"Bad\"";
mostCurrent._lblestado.setText(BA.ObjectToCharSequence("Bad"));
 }else if(_valorcalidad>=40 && _valorcalidad<60) { 
 //BA.debugLineNum = 1489;BA.debugLine="lblEstado.Text = \"Regular\"";
mostCurrent._lblestado.setText(BA.ObjectToCharSequence("Regular"));
 }else if(_valorcalidad>=60 && _valorcalidad<80) { 
 //BA.debugLineNum = 1491;BA.debugLine="lblEstado.Text = \"Good\"";
mostCurrent._lblestado.setText(BA.ObjectToCharSequence("Good"));
 }else if(_valorcalidad>=80) { 
 //BA.debugLineNum = 1493;BA.debugLine="lblEstado.Text = \"Very good!\"";
mostCurrent._lblestado.setText(BA.ObjectToCharSequence("Very good!"));
 };
 };
 //BA.debugLineNum = 1498;BA.debugLine="Gauge1.SetRanges(Array(Gauge1.CreateRange(0, 20,";
mostCurrent._gauge1._setranges /*String*/ (anywheresoftware.b4a.keywords.Common.ArrayToList(new Object[]{(Object)(mostCurrent._gauge1._createrange /*ilpla.appear.gauge._gaugerange*/ ((float) (0),(float) (20),mostCurrent._xui.Color_DarkGray)),(Object)(mostCurrent._gauge1._createrange /*ilpla.appear.gauge._gaugerange*/ ((float) (20),(float) (40),mostCurrent._xui.Color_Red)),(Object)(mostCurrent._gauge1._createrange /*ilpla.appear.gauge._gaugerange*/ ((float) (40),(float) (60),mostCurrent._xui.Color_Yellow)),(Object)(mostCurrent._gauge1._createrange /*ilpla.appear.gauge._gaugerange*/ ((float) (60),(float) (80),mostCurrent._xui.Color_Green)),(Object)(mostCurrent._gauge1._createrange /*ilpla.appear.gauge._gaugerange*/ ((float) (80),(float) (100),mostCurrent._xui.Color_Blue))}));
 //BA.debugLineNum = 1499;BA.debugLine="Gauge1.CurrentValue = 0";
mostCurrent._gauge1._setcurrentvalue /*float*/ ((float) (0));
 //BA.debugLineNum = 1500;BA.debugLine="Gauge1.CurrentValue = valorcalidad";
mostCurrent._gauge1._setcurrentvalue /*float*/ ((float) (_valorcalidad));
 //BA.debugLineNum = 1503;BA.debugLine="End Sub";
return "";
}
public static String  _tipopreguntachk(int _cantidadopciones,boolean _imagenvisible) throws Exception{
anywheresoftware.b4a.objects.drawable.ColorDrawable _chk = null;
 //BA.debugLineNum = 346;BA.debugLine="Sub TipoPreguntaChk (cantidadopciones As Int, imag";
 //BA.debugLineNum = 347;BA.debugLine="pnlChecks.Visible = True";
mostCurrent._pnlchecks.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 348;BA.debugLine="pnlOpciones.Visible = False";
mostCurrent._pnlopciones.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 349;BA.debugLine="chkOpcion1.SetBackgroundImage(LoadBitmap(File.Di";
mostCurrent._chkopcion1.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"blank.gif").getObject()));
 //BA.debugLineNum = 350;BA.debugLine="chkOpcion2.SetBackgroundImage(LoadBitmap(File.Di";
mostCurrent._chkopcion2.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"blank.gif").getObject()));
 //BA.debugLineNum = 351;BA.debugLine="chkOpcion3.SetBackgroundImage(LoadBitmap(File.Di";
mostCurrent._chkopcion3.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"blank.gif").getObject()));
 //BA.debugLineNum = 352;BA.debugLine="chkOpcion4.SetBackgroundImage(LoadBitmap(File.Di";
mostCurrent._chkopcion4.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"blank.gif").getObject()));
 //BA.debugLineNum = 353;BA.debugLine="chkOpcion5.SetBackgroundImage(LoadBitmap(File.Di";
mostCurrent._chkopcion5.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"blank.gif").getObject()));
 //BA.debugLineNum = 354;BA.debugLine="chkOpcion6.SetBackgroundImage(LoadBitmap(File.Di";
mostCurrent._chkopcion6.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"blank.gif").getObject()));
 //BA.debugLineNum = 355;BA.debugLine="chkOpcion1.Text = \"\"";
mostCurrent._chkopcion1.setText(BA.ObjectToCharSequence(""));
 //BA.debugLineNum = 356;BA.debugLine="chkOpcion2.Text = \"\"";
mostCurrent._chkopcion2.setText(BA.ObjectToCharSequence(""));
 //BA.debugLineNum = 357;BA.debugLine="chkOpcion3.Text = \"\"";
mostCurrent._chkopcion3.setText(BA.ObjectToCharSequence(""));
 //BA.debugLineNum = 358;BA.debugLine="chkOpcion4.Text = \"\"";
mostCurrent._chkopcion4.setText(BA.ObjectToCharSequence(""));
 //BA.debugLineNum = 359;BA.debugLine="chkOpcion5.Text = \"\"";
mostCurrent._chkopcion5.setText(BA.ObjectToCharSequence(""));
 //BA.debugLineNum = 360;BA.debugLine="chkOpcion6.Text = \"\"";
mostCurrent._chkopcion6.setText(BA.ObjectToCharSequence(""));
 //BA.debugLineNum = 361;BA.debugLine="Dim chk As ColorDrawable";
_chk = new anywheresoftware.b4a.objects.drawable.ColorDrawable();
 //BA.debugLineNum = 362;BA.debugLine="chk.initialize2(Colors.ARGB(0,0,0,0),5dip,0dip,C";
_chk.Initialize2(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (0),(int) (0),(int) (0)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (0)),anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (0),(int) (0),(int) (0)));
 //BA.debugLineNum = 363;BA.debugLine="chkOpcion1.Background = chk";
mostCurrent._chkopcion1.setBackground((android.graphics.drawable.Drawable)(_chk.getObject()));
 //BA.debugLineNum = 364;BA.debugLine="chkOpcion2.Background = chk";
mostCurrent._chkopcion2.setBackground((android.graphics.drawable.Drawable)(_chk.getObject()));
 //BA.debugLineNum = 365;BA.debugLine="chkOpcion3.Background = chk";
mostCurrent._chkopcion3.setBackground((android.graphics.drawable.Drawable)(_chk.getObject()));
 //BA.debugLineNum = 366;BA.debugLine="chkOpcion4.Background = chk";
mostCurrent._chkopcion4.setBackground((android.graphics.drawable.Drawable)(_chk.getObject()));
 //BA.debugLineNum = 367;BA.debugLine="chkOpcion5.Background = chk";
mostCurrent._chkopcion5.setBackground((android.graphics.drawable.Drawable)(_chk.getObject()));
 //BA.debugLineNum = 368;BA.debugLine="chkOpcion6.Background = chk";
mostCurrent._chkopcion6.setBackground((android.graphics.drawable.Drawable)(_chk.getObject()));
 //BA.debugLineNum = 369;BA.debugLine="chkOpcion1.width = rdOpcion1.Width";
mostCurrent._chkopcion1.setWidth(mostCurrent._rdopcion1.getWidth());
 //BA.debugLineNum = 370;BA.debugLine="chkOpcion2.width = rdOpcion1.Width";
mostCurrent._chkopcion2.setWidth(mostCurrent._rdopcion1.getWidth());
 //BA.debugLineNum = 371;BA.debugLine="chkOpcion3.width = rdOpcion1.Width";
mostCurrent._chkopcion3.setWidth(mostCurrent._rdopcion1.getWidth());
 //BA.debugLineNum = 372;BA.debugLine="chkOpcion4.width = rdOpcion1.Width";
mostCurrent._chkopcion4.setWidth(mostCurrent._rdopcion1.getWidth());
 //BA.debugLineNum = 373;BA.debugLine="chkOpcion5.width = rdOpcion1.Width";
mostCurrent._chkopcion5.setWidth(mostCurrent._rdopcion1.getWidth());
 //BA.debugLineNum = 374;BA.debugLine="chkOpcion6.width = rdOpcion1.Width";
mostCurrent._chkopcion6.setWidth(mostCurrent._rdopcion1.getWidth());
 //BA.debugLineNum = 375;BA.debugLine="chkOpcion1.Height = rdOpcion1.Height";
mostCurrent._chkopcion1.setHeight(mostCurrent._rdopcion1.getHeight());
 //BA.debugLineNum = 376;BA.debugLine="chkOpcion2.Height = rdOpcion1.Height";
mostCurrent._chkopcion2.setHeight(mostCurrent._rdopcion1.getHeight());
 //BA.debugLineNum = 377;BA.debugLine="chkOpcion3.Height = rdOpcion1.Height";
mostCurrent._chkopcion3.setHeight(mostCurrent._rdopcion1.getHeight());
 //BA.debugLineNum = 378;BA.debugLine="chkOpcion4.Height = rdOpcion1.Height";
mostCurrent._chkopcion4.setHeight(mostCurrent._rdopcion1.getHeight());
 //BA.debugLineNum = 379;BA.debugLine="chkOpcion5.Height = rdOpcion1.Height";
mostCurrent._chkopcion5.setHeight(mostCurrent._rdopcion1.getHeight());
 //BA.debugLineNum = 380;BA.debugLine="chkOpcion6.Height = rdOpcion1.Height";
mostCurrent._chkopcion6.setHeight(mostCurrent._rdopcion1.getHeight());
 //BA.debugLineNum = 382;BA.debugLine="chkOpcion1.Checked = False";
mostCurrent._chkopcion1.setChecked(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 383;BA.debugLine="chkOpcion2.Checked = False";
mostCurrent._chkopcion2.setChecked(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 384;BA.debugLine="chkOpcion3.Checked = False";
mostCurrent._chkopcion3.setChecked(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 385;BA.debugLine="chkOpcion4.Checked = False";
mostCurrent._chkopcion4.setChecked(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 386;BA.debugLine="chkOpcion5.Checked = False";
mostCurrent._chkopcion5.setChecked(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 387;BA.debugLine="chkOpcion6.Checked = False";
mostCurrent._chkopcion6.setChecked(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 388;BA.debugLine="chkOpcion1.Top = rdOpcion1.top";
mostCurrent._chkopcion1.setTop(mostCurrent._rdopcion1.getTop());
 //BA.debugLineNum = 389;BA.debugLine="chkOpcion2.Top = rdOpcion2.top";
mostCurrent._chkopcion2.setTop(mostCurrent._rdopcion2.getTop());
 //BA.debugLineNum = 390;BA.debugLine="chkOpcion3.Top = rdOpcion3.top";
mostCurrent._chkopcion3.setTop(mostCurrent._rdopcion3.getTop());
 //BA.debugLineNum = 391;BA.debugLine="chkOpcion4.Top = rdOpcion4.top";
mostCurrent._chkopcion4.setTop(mostCurrent._rdopcion4.getTop());
 //BA.debugLineNum = 392;BA.debugLine="chkOpcion5.Top = rdOpcion5.top";
mostCurrent._chkopcion5.setTop(mostCurrent._rdopcion5.getTop());
 //BA.debugLineNum = 393;BA.debugLine="chkOpcion6.Top = rdOpcion6.top";
mostCurrent._chkopcion6.setTop(mostCurrent._rdopcion6.getTop());
 //BA.debugLineNum = 394;BA.debugLine="chkOpcion1.Left = rdOpcion1.left";
mostCurrent._chkopcion1.setLeft(mostCurrent._rdopcion1.getLeft());
 //BA.debugLineNum = 395;BA.debugLine="chkOpcion2.Left = rdOpcion2.left";
mostCurrent._chkopcion2.setLeft(mostCurrent._rdopcion2.getLeft());
 //BA.debugLineNum = 396;BA.debugLine="chkOpcion3.Left = rdOpcion3.left";
mostCurrent._chkopcion3.setLeft(mostCurrent._rdopcion3.getLeft());
 //BA.debugLineNum = 397;BA.debugLine="chkOpcion4.Left = rdOpcion4.left";
mostCurrent._chkopcion4.setLeft(mostCurrent._rdopcion4.getLeft());
 //BA.debugLineNum = 398;BA.debugLine="chkOpcion5.Left = rdOpcion5.left";
mostCurrent._chkopcion5.setLeft(mostCurrent._rdopcion5.getLeft());
 //BA.debugLineNum = 399;BA.debugLine="chkOpcion6.Left = rdOpcion6.left";
mostCurrent._chkopcion6.setLeft(mostCurrent._rdopcion6.getLeft());
 //BA.debugLineNum = 400;BA.debugLine="If cantidadopciones = 2 Then";
if (_cantidadopciones==2) { 
 //BA.debugLineNum = 401;BA.debugLine="chkOpcion1.Visible = True";
mostCurrent._chkopcion1.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 402;BA.debugLine="chkOpcion2.Visible = True";
mostCurrent._chkopcion2.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 403;BA.debugLine="chkOpcion3.Visible = False";
mostCurrent._chkopcion3.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 404;BA.debugLine="chkOpcion4.Visible = False";
mostCurrent._chkopcion4.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 405;BA.debugLine="chkOpcion5.Visible = False";
mostCurrent._chkopcion5.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 406;BA.debugLine="chkOpcion6.Visible = False";
mostCurrent._chkopcion6.setVisible(anywheresoftware.b4a.keywords.Common.False);
 }else if(_cantidadopciones==3) { 
 //BA.debugLineNum = 408;BA.debugLine="chkOpcion1.Visible = True";
mostCurrent._chkopcion1.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 409;BA.debugLine="chkOpcion2.Visible = True";
mostCurrent._chkopcion2.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 410;BA.debugLine="chkOpcion3.Visible = True";
mostCurrent._chkopcion3.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 411;BA.debugLine="chkOpcion4.Visible = False";
mostCurrent._chkopcion4.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 412;BA.debugLine="chkOpcion5.Visible = False";
mostCurrent._chkopcion5.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 413;BA.debugLine="chkOpcion6.Visible = False";
mostCurrent._chkopcion6.setVisible(anywheresoftware.b4a.keywords.Common.False);
 }else if(_cantidadopciones==4) { 
 //BA.debugLineNum = 415;BA.debugLine="chkOpcion1.Visible = True";
mostCurrent._chkopcion1.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 416;BA.debugLine="chkOpcion2.Visible = True";
mostCurrent._chkopcion2.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 417;BA.debugLine="chkOpcion3.Visible = True";
mostCurrent._chkopcion3.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 418;BA.debugLine="chkOpcion4.Visible = True";
mostCurrent._chkopcion4.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 419;BA.debugLine="chkOpcion5.Visible = False";
mostCurrent._chkopcion5.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 420;BA.debugLine="chkOpcion6.Visible = False";
mostCurrent._chkopcion6.setVisible(anywheresoftware.b4a.keywords.Common.False);
 }else if(_cantidadopciones==5) { 
 //BA.debugLineNum = 422;BA.debugLine="chkOpcion1.Visible = True";
mostCurrent._chkopcion1.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 423;BA.debugLine="chkOpcion2.Visible = True";
mostCurrent._chkopcion2.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 424;BA.debugLine="chkOpcion3.Visible = True";
mostCurrent._chkopcion3.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 425;BA.debugLine="chkOpcion4.Visible = True";
mostCurrent._chkopcion4.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 426;BA.debugLine="chkOpcion5.Visible = True";
mostCurrent._chkopcion5.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 427;BA.debugLine="chkOpcion6.Visible = False";
mostCurrent._chkopcion6.setVisible(anywheresoftware.b4a.keywords.Common.False);
 }else if(_cantidadopciones==6) { 
 //BA.debugLineNum = 429;BA.debugLine="chkOpcion1.Visible = True";
mostCurrent._chkopcion1.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 430;BA.debugLine="chkOpcion2.Visible = True";
mostCurrent._chkopcion2.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 431;BA.debugLine="chkOpcion3.Visible = True";
mostCurrent._chkopcion3.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 432;BA.debugLine="chkOpcion4.Visible = True";
mostCurrent._chkopcion4.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 433;BA.debugLine="chkOpcion5.Visible = True";
mostCurrent._chkopcion5.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 434;BA.debugLine="chkOpcion6.Visible = True";
mostCurrent._chkopcion6.setVisible(anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 436;BA.debugLine="End Sub";
return "";
}
public static String  _tipopreguntarad(int _cantidadopciones,boolean _imagenvisible) throws Exception{
 //BA.debugLineNum = 286;BA.debugLine="Sub TipoPreguntaRad (cantidadopciones As Int, imag";
 //BA.debugLineNum = 288;BA.debugLine="pnlChecks.Visible = False";
mostCurrent._pnlchecks.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 289;BA.debugLine="pnlOpciones.Visible = True";
mostCurrent._pnlopciones.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 293;BA.debugLine="rdOpcion1.Checked = False";
mostCurrent._rdopcion1.setChecked(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 294;BA.debugLine="rdOpcion2.Checked = False";
mostCurrent._rdopcion2.setChecked(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 295;BA.debugLine="rdOpcion3.Checked = False";
mostCurrent._rdopcion3.setChecked(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 296;BA.debugLine="rdOpcion4.Checked = False";
mostCurrent._rdopcion4.setChecked(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 297;BA.debugLine="rdOpcion5.Checked = False";
mostCurrent._rdopcion5.setChecked(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 298;BA.debugLine="rdOpcion6.Checked = False";
mostCurrent._rdopcion6.setChecked(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 299;BA.debugLine="rdOpcion1.Visible = False";
mostCurrent._rdopcion1.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 300;BA.debugLine="rdOpcion2.Visible = False";
mostCurrent._rdopcion2.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 301;BA.debugLine="rdOpcion3.Visible = False";
mostCurrent._rdopcion3.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 302;BA.debugLine="rdOpcion4.Visible = False";
mostCurrent._rdopcion4.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 303;BA.debugLine="rdOpcion5.Visible = False";
mostCurrent._rdopcion5.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 304;BA.debugLine="rdOpcion6.Visible = False";
mostCurrent._rdopcion6.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 307;BA.debugLine="If imagenvisible = True Then";
if (_imagenvisible==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 308;BA.debugLine="btnRdOpcion1.Visible = True";
mostCurrent._btnrdopcion1.setVisible(anywheresoftware.b4a.keywords.Common.True);
 }else {
 //BA.debugLineNum = 310;BA.debugLine="btnRdOpcion1.Visible = False";
mostCurrent._btnrdopcion1.setVisible(anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 315;BA.debugLine="If cantidadopciones = 2 Then";
if (_cantidadopciones==2) { 
 //BA.debugLineNum = 316;BA.debugLine="rdOpcion1.Visible = True";
mostCurrent._rdopcion1.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 317;BA.debugLine="rdOpcion2.Visible = True";
mostCurrent._rdopcion2.setVisible(anywheresoftware.b4a.keywords.Common.True);
 }else if(_cantidadopciones==3) { 
 //BA.debugLineNum = 320;BA.debugLine="rdOpcion1.Visible = True";
mostCurrent._rdopcion1.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 321;BA.debugLine="rdOpcion2.Visible = True";
mostCurrent._rdopcion2.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 322;BA.debugLine="rdOpcion3.Visible = True";
mostCurrent._rdopcion3.setVisible(anywheresoftware.b4a.keywords.Common.True);
 }else if(_cantidadopciones==4) { 
 //BA.debugLineNum = 324;BA.debugLine="rdOpcion1.Visible = True";
mostCurrent._rdopcion1.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 325;BA.debugLine="rdOpcion2.Visible = True";
mostCurrent._rdopcion2.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 326;BA.debugLine="rdOpcion3.Visible = True";
mostCurrent._rdopcion3.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 327;BA.debugLine="rdOpcion4.Visible = True";
mostCurrent._rdopcion4.setVisible(anywheresoftware.b4a.keywords.Common.True);
 }else if(_cantidadopciones==5) { 
 //BA.debugLineNum = 329;BA.debugLine="rdOpcion1.Visible = True";
mostCurrent._rdopcion1.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 330;BA.debugLine="rdOpcion2.Visible = True";
mostCurrent._rdopcion2.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 331;BA.debugLine="rdOpcion3.Visible = True";
mostCurrent._rdopcion3.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 332;BA.debugLine="rdOpcion4.Visible = True";
mostCurrent._rdopcion4.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 333;BA.debugLine="rdOpcion5.Visible = True";
mostCurrent._rdopcion5.setVisible(anywheresoftware.b4a.keywords.Common.True);
 }else if(_cantidadopciones==6) { 
 //BA.debugLineNum = 335;BA.debugLine="rdOpcion1.Visible = True";
mostCurrent._rdopcion1.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 336;BA.debugLine="rdOpcion2.Visible = True";
mostCurrent._rdopcion2.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 337;BA.debugLine="rdOpcion3.Visible = True";
mostCurrent._rdopcion3.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 338;BA.debugLine="rdOpcion4.Visible = True";
mostCurrent._rdopcion4.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 339;BA.debugLine="rdOpcion5.Visible = True";
mostCurrent._rdopcion5.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 340;BA.debugLine="rdOpcion6.Visible = True";
mostCurrent._rdopcion6.setVisible(anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 343;BA.debugLine="End Sub";
return "";
}
public static String  _validarrespuesta() throws Exception{
anywheresoftware.b4a.objects.LabelWrapper _pregresultadoview = null;
int _preg = 0;
anywheresoftware.b4a.objects.ImageViewWrapper _pregresultadoimg = null;
 //BA.debugLineNum = 665;BA.debugLine="Sub ValidarRespuesta";
 //BA.debugLineNum = 667;BA.debugLine="Dim pregResultadoView As Label";
_pregresultadoview = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 668;BA.debugLine="pregResultadoView.Initialize(\"\")";
_pregresultadoview.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 669;BA.debugLine="pregResultadoView.Color = Colors.White";
_pregresultadoview.setColor(anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 671;BA.debugLine="If currentpregunta = 1 Then";
if (_currentpregunta==1) { 
 //BA.debugLineNum = 672;BA.debugLine="If rdOpcion1.Checked = True Then";
if (mostCurrent._rdopcion1.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 673;BA.debugLine="valorind1 = \"0\"";
mostCurrent._valorind1 = "0";
 //BA.debugLineNum = 674;BA.debugLine="miniPregunta1.SetBackgroundImage(LoadBitmapSamp";
mostCurrent._minipregunta1.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"mini-usoindustrial.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 675;BA.debugLine="pregResultadoView.Text = \"Las industrias pueden";
_pregresultadoview.setText(BA.ObjectToCharSequence("Las industrias pueden volcar contaminantes directamente al agua"));
 //BA.debugLineNum = 676;BA.debugLine="pregResultadoView.Tag = \"MM\"";
_pregresultadoview.setTag((Object)("MM"));
 }else if(mostCurrent._rdopcion2.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 678;BA.debugLine="valorind1 = \"2\"";
mostCurrent._valorind1 = "2";
 //BA.debugLineNum = 679;BA.debugLine="miniPregunta1.SetBackgroundImage(LoadBitmapSamp";
mostCurrent._minipregunta1.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"mini-usourbano.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 680;BA.debugLine="pregResultadoView.Text = \"Las áreas urbanas gen";
_pregresultadoview.setText(BA.ObjectToCharSequence("Las áreas urbanas generan gran cantidad de contaminantes que ingresan al agua"));
 //BA.debugLineNum = 681;BA.debugLine="pregResultadoView.Tag = \"M\"";
_pregresultadoview.setTag((Object)("M"));
 }else if(mostCurrent._rdopcion3.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 683;BA.debugLine="valorind1 = \"4\"";
mostCurrent._valorind1 = "4";
 //BA.debugLineNum = 684;BA.debugLine="miniPregunta1.SetBackgroundImage(LoadBitmapSamp";
mostCurrent._minipregunta1.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"mini-usouburbano.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 685;BA.debugLine="pregResultadoView.Text = \"Las áreas suburbanas";
_pregresultadoview.setText(BA.ObjectToCharSequence("Las áreas suburbanas generan algunos contaminantes que ingresan al agua"));
 //BA.debugLineNum = 686;BA.debugLine="pregResultadoView.Tag = \"R\"";
_pregresultadoview.setTag((Object)("R"));
 }else if(mostCurrent._rdopcion4.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 688;BA.debugLine="valorind1 = \"4\"";
mostCurrent._valorind1 = "4";
 //BA.debugLineNum = 689;BA.debugLine="miniPregunta1.SetBackgroundImage(LoadBitmapSamp";
mostCurrent._minipregunta1.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"mini-usoagricola.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 690;BA.debugLine="pregResultadoView.Text = \"Los fertilizantes y p";
_pregresultadoview.setText(BA.ObjectToCharSequence("Los fertilizantes y pesticidas usados en la agricultura pueden afectar a los organismos acuáticos"));
 //BA.debugLineNum = 691;BA.debugLine="pregResultadoView.Tag = \"R\"";
_pregresultadoview.setTag((Object)("R"));
 }else if(mostCurrent._rdopcion5.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 693;BA.debugLine="valorind1 = \"10\"";
mostCurrent._valorind1 = "10";
 //BA.debugLineNum = 694;BA.debugLine="miniPregunta1.SetBackgroundImage(LoadBitmapSamp";
mostCurrent._minipregunta1.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"mini-usonatural.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 695;BA.debugLine="pregResultadoView.Text = \"Las áreas naturales s";
_pregresultadoview.setText(BA.ObjectToCharSequence("Las áreas naturales suelen tener sus cuerpos de agua bien preservados"));
 //BA.debugLineNum = 696;BA.debugLine="pregResultadoView.Tag = \"MB\"";
_pregresultadoview.setTag((Object)("MB"));
 }else if(mostCurrent._rdopcion6.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 698;BA.debugLine="valorind1 = \"NS\"";
mostCurrent._valorind1 = "NS";
 //BA.debugLineNum = 699;BA.debugLine="pregResultadoView.Text = \"No has ingresado info";
_pregresultadoview.setText(BA.ObjectToCharSequence("No has ingresado información sobre el uso del suelo"));
 //BA.debugLineNum = 700;BA.debugLine="pregResultadoView.Tag = \"NS\"";
_pregresultadoview.setTag((Object)("NS"));
 //BA.debugLineNum = 701;BA.debugLine="miniPregunta1.SetBackgroundImage(Null)";
mostCurrent._minipregunta1.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null));
 }else {
 //BA.debugLineNum = 703;BA.debugLine="Return(\"no\")";
if (true) return ("no");
 };
 }else if(_currentpregunta==2) { 
 //BA.debugLineNum = 706;BA.debugLine="If rdOpcion1.Checked = True Then";
if (mostCurrent._rdopcion1.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 707;BA.debugLine="valorind2 = \"0\"";
mostCurrent._valorind2 = "0";
 //BA.debugLineNum = 708;BA.debugLine="miniPregunta2.SetBackgroundImage(LoadBitmapSamp";
mostCurrent._minipregunta2.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"mini-feedlot.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 709;BA.debugLine="pregResultadoView.Text = \"Los feedlot ganaderos";
_pregresultadoview.setText(BA.ObjectToCharSequence("Los feedlot ganaderos vuelcan una gran cantidad de nutrientes en el agua"));
 //BA.debugLineNum = 710;BA.debugLine="pregResultadoView.Tag = \"MM\"";
_pregresultadoview.setTag((Object)("MM"));
 }else if(mostCurrent._rdopcion2.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 712;BA.debugLine="valorind2 = \"2\"";
mostCurrent._valorind2 = "2";
 //BA.debugLineNum = 713;BA.debugLine="miniPregunta2.SetBackgroundImage(LoadBitmapSamp";
mostCurrent._minipregunta2.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"mini-ganadodisperso.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 714;BA.debugLine="pregResultadoView.Text = \"El ganado pisotea las";
_pregresultadoview.setText(BA.ObjectToCharSequence("El ganado pisotea las márgenes y, mediante sus deshechos, aumentan la cantidad de nutrientes en el agua"));
 //BA.debugLineNum = 715;BA.debugLine="pregResultadoView.Tag = \"M\"";
_pregresultadoview.setTag((Object)("M"));
 }else if(mostCurrent._rdopcion3.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 718;BA.debugLine="valorind2 = \"4\"";
mostCurrent._valorind2 = "4";
 //BA.debugLineNum = 719;BA.debugLine="miniPregunta2.SetBackgroundImage(LoadBitmapSamp";
mostCurrent._minipregunta2.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"mini-ganadopoco.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 720;BA.debugLine="pregResultadoView.Text = \"El ganado pisotea las";
_pregresultadoview.setText(BA.ObjectToCharSequence("El ganado pisotea las márgenes y, mediante sus deshechos, aumentan la cantidad de nutrientes en el agua"));
 //BA.debugLineNum = 721;BA.debugLine="pregResultadoView.Tag = \"R\"";
_pregresultadoview.setTag((Object)("R"));
 }else if(mostCurrent._rdopcion4.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 723;BA.debugLine="valorind2 = \"10\"";
mostCurrent._valorind2 = "10";
 //BA.debugLineNum = 724;BA.debugLine="miniPregunta2.SetBackgroundImage(LoadBitmapSamp";
mostCurrent._minipregunta2.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"mini-ganadonada.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 725;BA.debugLine="pregResultadoView.Text = \"No hay ganado cerca q";
_pregresultadoview.setText(BA.ObjectToCharSequence("No hay ganado cerca que pisotee las márgenes"));
 //BA.debugLineNum = 726;BA.debugLine="pregResultadoView.Tag = \"MB\"";
_pregresultadoview.setTag((Object)("MB"));
 //BA.debugLineNum = 727;BA.debugLine="miniPregunta2.SetBackgroundImage(Null)";
mostCurrent._minipregunta2.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null));
 }else if(mostCurrent._rdopcion5.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 729;BA.debugLine="valorind2 = \"NS\"";
mostCurrent._valorind2 = "NS";
 //BA.debugLineNum = 730;BA.debugLine="pregResultadoView.Text = \"No has ingresado info";
_pregresultadoview.setText(BA.ObjectToCharSequence("No has ingresado información sobre ganado"));
 //BA.debugLineNum = 731;BA.debugLine="pregResultadoView.Tag = \"NS\"";
_pregresultadoview.setTag((Object)("NS"));
 //BA.debugLineNum = 732;BA.debugLine="miniPregunta2.SetBackgroundImage(Null)";
mostCurrent._minipregunta2.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null));
 }else {
 //BA.debugLineNum = 734;BA.debugLine="Return(\"no\")";
if (true) return ("no");
 };
 }else if(_currentpregunta==3) { 
 //BA.debugLineNum = 738;BA.debugLine="If rdOpcion1.Checked = True Then";
if (mostCurrent._rdopcion1.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 739;BA.debugLine="valorind3 = \"10\"";
mostCurrent._valorind3 = "10";
 //BA.debugLineNum = 740;BA.debugLine="miniPregunta3.SetBackgroundImage(LoadBitmapSamp";
mostCurrent._minipregunta3.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"mini-vegetacionmucha.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 741;BA.debugLine="If tiporio = \"montana\" Then";
if ((_tiporio).equals("montana")) { 
 //BA.debugLineNum = 742;BA.debugLine="pregResultadoView.Text = \"La variada vegetació";
_pregresultadoview.setText(BA.ObjectToCharSequence("La variada vegetación  suele indicar un buen estado del hábitat"));
 //BA.debugLineNum = 743;BA.debugLine="pregResultadoView.Tag = \"MB\"";
_pregresultadoview.setTag((Object)("MB"));
 }else if((_tiporio).equals("llanura")) { 
 //BA.debugLineNum = 745;BA.debugLine="pregResultadoView.Text = \"La presencia de árbo";
_pregresultadoview.setText(BA.ObjectToCharSequence("La presencia de árboles en las llanuras no siempre indica un buen hábitat"));
 //BA.debugLineNum = 746;BA.debugLine="pregResultadoView.Tag = \"R\"";
_pregresultadoview.setTag((Object)("R"));
 };
 }else if(mostCurrent._rdopcion2.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 749;BA.debugLine="valorind3 = \"5\"";
mostCurrent._valorind3 = "5";
 //BA.debugLineNum = 750;BA.debugLine="miniPregunta3.SetBackgroundImage(LoadBitmapSamp";
mostCurrent._minipregunta3.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"mini-vegetacionpoca.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 751;BA.debugLine="If tiporio = \"montana\" Then";
if ((_tiporio).equals("montana")) { 
 //BA.debugLineNum = 752;BA.debugLine="pregResultadoView.Text = \"La ausencia de veget";
_pregresultadoview.setText(BA.ObjectToCharSequence("La ausencia de vegetación suele indicar un hábitat degradado"));
 //BA.debugLineNum = 753;BA.debugLine="pregResultadoView.Tag = \"M\"";
_pregresultadoview.setTag((Object)("M"));
 };
 }else if(mostCurrent._rdopcion3.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 756;BA.debugLine="valorind3 = \"1\"";
mostCurrent._valorind3 = "1";
 //BA.debugLineNum = 757;BA.debugLine="miniPregunta3.SetBackgroundImage(LoadBitmapSamp";
mostCurrent._minipregunta3.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"mini-vegetacionnada.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 758;BA.debugLine="If tiporio = \"montana\" Then";
if ((_tiporio).equals("montana")) { 
 //BA.debugLineNum = 759;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 760;BA.debugLine="pregResultadoView.Text = \"La ausencia de vege";
_pregresultadoview.setText(BA.ObjectToCharSequence("La ausencia de vegetación suele indicar un hábitat degradado"));
 };
 //BA.debugLineNum = 762;BA.debugLine="pregResultadoView.Tag = \"MM\"";
_pregresultadoview.setTag((Object)("MM"));
 }else if((_tiporio).equals("llanura")) { 
 //BA.debugLineNum = 764;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 765;BA.debugLine="pregResultadoView.Text = \"La ausencia de vege";
_pregresultadoview.setText(BA.ObjectToCharSequence("La ausencia de vegetación en las llanuras no siempre indica un hábitat degradado"));
 };
 //BA.debugLineNum = 767;BA.debugLine="pregResultadoView.Tag = \"R\"";
_pregresultadoview.setTag((Object)("R"));
 };
 }else if(mostCurrent._rdopcion4.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 770;BA.debugLine="valorind3 = \"NS\"";
mostCurrent._valorind3 = "NS";
 //BA.debugLineNum = 771;BA.debugLine="miniPregunta3.SetBackgroundImage(Null)";
mostCurrent._minipregunta3.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null));
 //BA.debugLineNum = 772;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 773;BA.debugLine="pregResultadoView.Text = \"No has ingresado inf";
_pregresultadoview.setText(BA.ObjectToCharSequence("No has ingresado información sobre la vegetación"));
 };
 //BA.debugLineNum = 775;BA.debugLine="pregResultadoView.Tag = \"NS\"";
_pregresultadoview.setTag((Object)("NS"));
 }else {
 //BA.debugLineNum = 777;BA.debugLine="Return(\"no\")";
if (true) return ("no");
 };
 }else if(_currentpregunta==4) { 
 //BA.debugLineNum = 781;BA.debugLine="If rdOpcion1.Checked = True Then";
if (mostCurrent._rdopcion1.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 782;BA.debugLine="valorind4 = \"10\"";
mostCurrent._valorind4 = "10";
 //BA.debugLineNum = 783;BA.debugLine="miniPregunta4.SetBackgroundImage(LoadBitmapSamp";
mostCurrent._minipregunta4.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"mini-vegacuaticavarios.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 784;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 785;BA.debugLine="pregResultadoView.Text = \"La presencia de vari";
_pregresultadoview.setText(BA.ObjectToCharSequence("La presencia de varias plantas acuáticas puede indicar un muy buen hábitat"));
 };
 //BA.debugLineNum = 787;BA.debugLine="pregResultadoView.Tag = \"B\"";
_pregresultadoview.setTag((Object)("B"));
 }else if(mostCurrent._rdopcion2.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 789;BA.debugLine="valorind4 = \"5\"";
mostCurrent._valorind4 = "5";
 //BA.debugLineNum = 790;BA.debugLine="miniPregunta4.SetBackgroundImage(LoadBitmapSamp";
mostCurrent._minipregunta4.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"mini-vegacuaticapocos.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 791;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 792;BA.debugLine="pregResultadoView.Text = \"La presencia de alg";
_pregresultadoview.setText(BA.ObjectToCharSequence("La presencia de algunas plantas acuáticas suele indicar un hábitat conservado"));
 };
 //BA.debugLineNum = 794;BA.debugLine="pregResultadoView.Tag = \"R\"";
_pregresultadoview.setTag((Object)("R"));
 }else if(mostCurrent._rdopcion3.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 796;BA.debugLine="valorind4 = \"0\"";
mostCurrent._valorind4 = "0";
 //BA.debugLineNum = 797;BA.debugLine="miniPregunta4.SetBackgroundImage(LoadBitmapSamp";
mostCurrent._minipregunta4.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"mini-vegacuaticanada.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 798;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 799;BA.debugLine="pregResultadoView.Text = \"La ausencia de plan";
_pregresultadoview.setText(BA.ObjectToCharSequence("La ausencia de plantas acuáticas suele indicar un hábitat degradado"));
 };
 //BA.debugLineNum = 801;BA.debugLine="pregResultadoView.Tag = \"MM\"";
_pregresultadoview.setTag((Object)("MM"));
 }else if(mostCurrent._rdopcion4.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 803;BA.debugLine="valorind4 = \"NS\"";
mostCurrent._valorind4 = "NS";
 //BA.debugLineNum = 804;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 805;BA.debugLine="pregResultadoView.Text = \"No has ingresado inf";
_pregresultadoview.setText(BA.ObjectToCharSequence("No has ingresado información sobre las plantas acuáticas"));
 };
 //BA.debugLineNum = 807;BA.debugLine="pregResultadoView.Tag = \"NS\"";
_pregresultadoview.setTag((Object)("NS"));
 //BA.debugLineNum = 808;BA.debugLine="miniPregunta4.SetBackgroundImage(Null)";
mostCurrent._minipregunta4.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null));
 }else {
 //BA.debugLineNum = 810;BA.debugLine="Return(\"no\")";
if (true) return ("no");
 };
 }else if(_currentpregunta==5) { 
 //BA.debugLineNum = 814;BA.debugLine="If rdOpcion1.Checked = True Then";
if (mostCurrent._rdopcion1.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 815;BA.debugLine="valorind5 = \"10\"";
mostCurrent._valorind5 = "10";
 //BA.debugLineNum = 816;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 817;BA.debugLine="pregResultadoView.Text = \"El color transparent";
_pregresultadoview.setText(BA.ObjectToCharSequence("El color transparente del agua en estos ríos es normal"));
 };
 //BA.debugLineNum = 819;BA.debugLine="pregResultadoView.Tag = \"MB\"";
_pregresultadoview.setTag((Object)("MB"));
 //BA.debugLineNum = 820;BA.debugLine="miniPregunta5.SetBackgroundImage(LoadBitmapSamp";
mostCurrent._minipregunta5.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"mini-aguabuena.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 }else if(mostCurrent._rdopcion2.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 822;BA.debugLine="valorind5 = \"10\"";
mostCurrent._valorind5 = "10";
 //BA.debugLineNum = 823;BA.debugLine="If tiporio = \"montana\" Then";
if ((_tiporio).equals("montana")) { 
 //BA.debugLineNum = 824;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 825;BA.debugLine="pregResultadoView.Text = \"El color marrón del";
_pregresultadoview.setText(BA.ObjectToCharSequence("El color marrón del agua en estos ríos puede ser anormal"));
 };
 //BA.debugLineNum = 827;BA.debugLine="miniPregunta5.SetBackgroundImage(LoadBitmapSam";
mostCurrent._minipregunta5.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"mini-aguamarron.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 828;BA.debugLine="pregResultadoView.Tag = \"MB\"";
_pregresultadoview.setTag((Object)("MB"));
 }else if((_tiporio).equals("llanura")) { 
 //BA.debugLineNum = 830;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 831;BA.debugLine="pregResultadoView.Text = \"El color marrón del";
_pregresultadoview.setText(BA.ObjectToCharSequence("El color marrón del agua en estos ríos es normal"));
 };
 //BA.debugLineNum = 833;BA.debugLine="miniPregunta5.SetBackgroundImage(LoadBitmapSam";
mostCurrent._minipregunta5.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"mini-aguamarron.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 834;BA.debugLine="pregResultadoView.Tag = \"MB\"";
_pregresultadoview.setTag((Object)("MB"));
 };
 }else if(mostCurrent._rdopcion3.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 837;BA.debugLine="valorind5 = \"2\"";
mostCurrent._valorind5 = "2";
 //BA.debugLineNum = 838;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 839;BA.debugLine="pregResultadoView.Text = \"El color oscuro del";
_pregresultadoview.setText(BA.ObjectToCharSequence("El color oscuro del agua puede indicar una contaminación fuerte"));
 };
 //BA.debugLineNum = 841;BA.debugLine="miniPregunta5.SetBackgroundImage(LoadBitmapSamp";
mostCurrent._minipregunta5.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"mini-aguaoscura.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 842;BA.debugLine="pregResultadoView.Tag = \"MM\"";
_pregresultadoview.setTag((Object)("MM"));
 }else if(mostCurrent._rdopcion4.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 844;BA.debugLine="valorind5 = \"NS\"";
mostCurrent._valorind5 = "NS";
 //BA.debugLineNum = 845;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 846;BA.debugLine="pregResultadoView.Text = \"No has ingresado inf";
_pregresultadoview.setText(BA.ObjectToCharSequence("No has ingresado información sobre el color del agua"));
 };
 //BA.debugLineNum = 848;BA.debugLine="pregResultadoView.Tag = \"NS\"";
_pregresultadoview.setTag((Object)("NS"));
 }else {
 //BA.debugLineNum = 850;BA.debugLine="Return(\"no\")";
if (true) return ("no");
 };
 }else if(_currentpregunta==6) { 
 //BA.debugLineNum = 854;BA.debugLine="If rdOpcion1.Checked = True Then";
if (mostCurrent._rdopcion1.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 855;BA.debugLine="valorind6 = \"10\"";
mostCurrent._valorind6 = "10";
 //BA.debugLineNum = 856;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 857;BA.debugLine="pregResultadoView.Text = \"El agua tiene buen o";
_pregresultadoview.setText(BA.ObjectToCharSequence("El agua tiene buen olor"));
 };
 //BA.debugLineNum = 859;BA.debugLine="pregResultadoView.Tag = \"MB\"";
_pregresultadoview.setTag((Object)("MB"));
 }else if(mostCurrent._rdopcion2.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 861;BA.debugLine="valorind6 = \"0\"";
mostCurrent._valorind6 = "0";
 //BA.debugLineNum = 862;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 863;BA.debugLine="pregResultadoView.Text = \"El agua con olor feo";
_pregresultadoview.setText(BA.ObjectToCharSequence("El agua con olor feo puede indicar una contaminación fuerte"));
 };
 //BA.debugLineNum = 865;BA.debugLine="pregResultadoView.Tag = \"MM\"";
_pregresultadoview.setTag((Object)("MM"));
 }else if(mostCurrent._rdopcion3.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 867;BA.debugLine="valorind6 = \"NS\"";
mostCurrent._valorind6 = "NS";
 //BA.debugLineNum = 868;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 869;BA.debugLine="pregResultadoView.Text = \"No has ingresado inf";
_pregresultadoview.setText(BA.ObjectToCharSequence("No has ingresado información sobre el olor del agua"));
 };
 //BA.debugLineNum = 871;BA.debugLine="pregResultadoView.Tag = \"NS\"";
_pregresultadoview.setTag((Object)("NS"));
 }else {
 //BA.debugLineNum = 873;BA.debugLine="Return(\"no\")";
if (true) return ("no");
 };
 }else if(_currentpregunta==7) { 
 //BA.debugLineNum = 876;BA.debugLine="If rdOpcion1.Checked = True Then";
if (mostCurrent._rdopcion1.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 877;BA.debugLine="valorind7 = \"10\"";
mostCurrent._valorind7 = "10";
 //BA.debugLineNum = 878;BA.debugLine="miniPregunta6.SetBackgroundImage(LoadBitmapSamp";
mostCurrent._minipregunta6.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"mini-basuranada.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 879;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 880;BA.debugLine="pregResultadoView.Text = \"No hay basura!\"";
_pregresultadoview.setText(BA.ObjectToCharSequence("No hay basura!"));
 };
 //BA.debugLineNum = 882;BA.debugLine="pregResultadoView.Tag = \"MB\"";
_pregresultadoview.setTag((Object)("MB"));
 }else if(mostCurrent._rdopcion2.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 884;BA.debugLine="valorind7 = \"5\"";
mostCurrent._valorind7 = "5";
 //BA.debugLineNum = 885;BA.debugLine="miniPregunta6.SetBackgroundImage(LoadBitmapSamp";
mostCurrent._minipregunta6.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"mini-basurapoca.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 886;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 887;BA.debugLine="pregResultadoView.Text = \"La basura contamina,";
_pregresultadoview.setText(BA.ObjectToCharSequence("La basura contamina, aunque sea en poca cantidad!"));
 };
 //BA.debugLineNum = 889;BA.debugLine="pregResultadoView.Tag = \"M\"";
_pregresultadoview.setTag((Object)("M"));
 }else if(mostCurrent._rdopcion3.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 891;BA.debugLine="valorind7 = \"0\"";
mostCurrent._valorind7 = "0";
 //BA.debugLineNum = 892;BA.debugLine="miniPregunta6.SetBackgroundImage(LoadBitmapSamp";
mostCurrent._minipregunta6.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"mini-basuramucha.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 893;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 894;BA.debugLine="pregResultadoView.Text = \"La basura contamina!";
_pregresultadoview.setText(BA.ObjectToCharSequence("La basura contamina!"));
 };
 //BA.debugLineNum = 896;BA.debugLine="pregResultadoView.Tag = \"MM\"";
_pregresultadoview.setTag((Object)("MM"));
 }else if(mostCurrent._rdopcion4.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 898;BA.debugLine="valorind7 = \"NS\"";
mostCurrent._valorind7 = "NS";
 //BA.debugLineNum = 899;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 900;BA.debugLine="pregResultadoView.Text = \"No has ingresado inf";
_pregresultadoview.setText(BA.ObjectToCharSequence("No has ingresado información sobre basura"));
 };
 //BA.debugLineNum = 902;BA.debugLine="pregResultadoView.Tag = \"NS\"";
_pregresultadoview.setTag((Object)("NS"));
 //BA.debugLineNum = 903;BA.debugLine="miniPregunta6.SetBackgroundImage(Null)";
mostCurrent._minipregunta6.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null));
 }else {
 //BA.debugLineNum = 905;BA.debugLine="Return(\"no\")";
if (true) return ("no");
 };
 }else if(_currentpregunta==8) { 
 //BA.debugLineNum = 909;BA.debugLine="If rdOpcion1.Checked = True Then";
if (mostCurrent._rdopcion1.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 910;BA.debugLine="valorind8 = \"10\"";
mostCurrent._valorind8 = "10";
 //BA.debugLineNum = 911;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 912;BA.debugLine="pregResultadoView.Text = \"La velocidad de la c";
_pregresultadoview.setText(BA.ObjectToCharSequence("La velocidad de la corriente es normal"));
 };
 //BA.debugLineNum = 914;BA.debugLine="pregResultadoView.Tag = \"B\"";
_pregresultadoview.setTag((Object)("B"));
 }else if(mostCurrent._rdopcion2.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 916;BA.debugLine="valorind8 = \"10\"";
mostCurrent._valorind8 = "10";
 //BA.debugLineNum = 917;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 918;BA.debugLine="pregResultadoView.Text = \"La velocidad de la c";
_pregresultadoview.setText(BA.ObjectToCharSequence("La velocidad de la corriente es normal"));
 };
 //BA.debugLineNum = 920;BA.debugLine="pregResultadoView.Tag = \"B\"";
_pregresultadoview.setTag((Object)("B"));
 }else if(mostCurrent._rdopcion3.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 922;BA.debugLine="valorind8 = \"NS\"";
mostCurrent._valorind8 = "NS";
 //BA.debugLineNum = 923;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 924;BA.debugLine="pregResultadoView.Text = \"No has ingresado inf";
_pregresultadoview.setText(BA.ObjectToCharSequence("No has ingresado información sobre la velocidad"));
 };
 //BA.debugLineNum = 926;BA.debugLine="pregResultadoView.Tag = \"NS\"";
_pregresultadoview.setTag((Object)("NS"));
 }else {
 //BA.debugLineNum = 928;BA.debugLine="Return(\"no\")";
if (true) return ("no");
 };
 }else if(_currentpregunta==9) { 
 //BA.debugLineNum = 931;BA.debugLine="If rdOpcion1.Checked = True Then";
if (mostCurrent._rdopcion1.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 932;BA.debugLine="valorind9 = \"0\"";
mostCurrent._valorind9 = "0";
 //BA.debugLineNum = 933;BA.debugLine="miniPregunta7.SetBackgroundImage(LoadBitmapSamp";
mostCurrent._minipregunta7.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"mini-desbordado.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 934;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 935;BA.debugLine="pregResultadoView.Text = \"Los ríos desbordados";
_pregresultadoview.setText(BA.ObjectToCharSequence("Los ríos desbordados pueden generar inundaciones en pueblos y ciudades!"));
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 937;BA.debugLine="pregResultadoView.Text = \"Overflowing rivers c";
_pregresultadoview.setText(BA.ObjectToCharSequence("Overflowing rivers can cause floods in cities and villages!"));
 };
 //BA.debugLineNum = 939;BA.debugLine="pregResultadoView.Tag = \"MM\"";
_pregresultadoview.setTag((Object)("MM"));
 }else if(mostCurrent._rdopcion2.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 941;BA.debugLine="miniPregunta7.SetBackgroundImage(LoadBitmapSamp";
mostCurrent._minipregunta7.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"mini-desbordadonada.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 942;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 943;BA.debugLine="pregResultadoView.Text = \"El río está en su ca";
_pregresultadoview.setText(BA.ObjectToCharSequence("El río está en su cauce, no debería generar inundaciones en pueblos y ciudades"));
 };
 //BA.debugLineNum = 945;BA.debugLine="pregResultadoView.Tag = \"MB\"";
_pregresultadoview.setTag((Object)("MB"));
 //BA.debugLineNum = 946;BA.debugLine="valorind9 = \"10\"";
mostCurrent._valorind9 = "10";
 }else if(mostCurrent._rdopcion3.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 948;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 949;BA.debugLine="pregResultadoView.Text = \"No has ingresado inf";
_pregresultadoview.setText(BA.ObjectToCharSequence("No has ingresado información sobre el estado del cauce"));
 };
 //BA.debugLineNum = 951;BA.debugLine="pregResultadoView.Tag = \"NS\"";
_pregresultadoview.setTag((Object)("NS"));
 //BA.debugLineNum = 952;BA.debugLine="valorind9 = \"NS\"";
mostCurrent._valorind9 = "NS";
 }else {
 //BA.debugLineNum = 954;BA.debugLine="Return(\"no\")";
if (true) return ("no");
 };
 }else if(_currentpregunta==10) { 
 //BA.debugLineNum = 957;BA.debugLine="If rdOpcion1.Checked = True Then";
if (mostCurrent._rdopcion1.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 958;BA.debugLine="valorind10 = \"0\"";
mostCurrent._valorind10 = "0";
 //BA.debugLineNum = 959;BA.debugLine="miniPreguntaEntubado.SetBackgroundImage(LoadBit";
mostCurrent._minipreguntaentubado.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"mini-entubado.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 960;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 961;BA.debugLine="pregResultadoView.Text = \"Las canalizaciones y";
_pregresultadoview.setText(BA.ObjectToCharSequence("Las canalizaciones y entubamientos destruyen el hábitat para los organismos acuáticos"));
 };
 //BA.debugLineNum = 964;BA.debugLine="pregResultadoView.Tag = \"MM\"";
_pregresultadoview.setTag((Object)("MM"));
 }else if(mostCurrent._rdopcion2.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 966;BA.debugLine="miniPreguntaEntubado.SetBackgroundImage(LoadBit";
mostCurrent._minipreguntaentubado.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"mini-entubadonada.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 967;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 968;BA.debugLine="pregResultadoView.Text = \"El río no está canal";
_pregresultadoview.setText(BA.ObjectToCharSequence("El río no está canalizado ni entubado, hay conexión entre el río y el agua subterránea"));
 };
 //BA.debugLineNum = 970;BA.debugLine="pregResultadoView.Tag = \"MB\"";
_pregresultadoview.setTag((Object)("MB"));
 //BA.debugLineNum = 971;BA.debugLine="valorind10 = \"10\"";
mostCurrent._valorind10 = "10";
 }else if(mostCurrent._rdopcion3.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 973;BA.debugLine="valorind10 = \"NS\"";
mostCurrent._valorind10 = "NS";
 //BA.debugLineNum = 974;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 975;BA.debugLine="pregResultadoView.Text = \"No has ingresado inf";
_pregresultadoview.setText(BA.ObjectToCharSequence("No has ingresado información sobre canalizaciones o dragados"));
 };
 //BA.debugLineNum = 977;BA.debugLine="pregResultadoView.Tag = \"NS\"";
_pregresultadoview.setTag((Object)("NS"));
 }else {
 //BA.debugLineNum = 979;BA.debugLine="Return(\"no\")";
if (true) return ("no");
 };
 }else if(_currentpregunta==11) { 
 //BA.debugLineNum = 982;BA.debugLine="Dim preg As Int = 0";
_preg = (int) (0);
 //BA.debugLineNum = 983;BA.debugLine="If chkOpcion1.Checked = True Then";
if (mostCurrent._chkopcion1.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 984;BA.debugLine="preg = preg + 1";
_preg = (int) (_preg+1);
 };
 //BA.debugLineNum = 986;BA.debugLine="If chkOpcion2.Checked = True Then";
if (mostCurrent._chkopcion2.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 987;BA.debugLine="preg = preg + 1";
_preg = (int) (_preg+1);
 };
 //BA.debugLineNum = 989;BA.debugLine="If chkOpcion3.Checked = True Then";
if (mostCurrent._chkopcion3.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 990;BA.debugLine="preg = preg + 1";
_preg = (int) (_preg+1);
 };
 //BA.debugLineNum = 992;BA.debugLine="If chkOpcion4.Checked = True Then";
if (mostCurrent._chkopcion4.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 993;BA.debugLine="preg = preg + 1";
_preg = (int) (_preg+1);
 };
 //BA.debugLineNum = 995;BA.debugLine="If chkOpcion5.Checked = True Then";
if (mostCurrent._chkopcion5.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 996;BA.debugLine="preg = 0";
_preg = (int) (0);
 //BA.debugLineNum = 997;BA.debugLine="valorind11 = \"0\"";
mostCurrent._valorind11 = "0";
 //BA.debugLineNum = 998;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 999;BA.debugLine="pregResultadoView.Text = \"El fondo es artifici";
_pregresultadoview.setText(BA.ObjectToCharSequence("El fondo es artificial, no hay hábitats para los organismos del fondo"));
 };
 //BA.debugLineNum = 1003;BA.debugLine="miniPregunta8.SetBackgroundImage(LoadBitmapSamp";
mostCurrent._minipregunta8.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"mini-fondoartificial.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 1004;BA.debugLine="pregResultadoView.Tag = \"MM\"";
_pregresultadoview.setTag((Object)("MM"));
 };
 //BA.debugLineNum = 1006;BA.debugLine="If chkOpcion6.Checked = True Then";
if (mostCurrent._chkopcion6.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 1007;BA.debugLine="preg = 0";
_preg = (int) (0);
 //BA.debugLineNum = 1008;BA.debugLine="valorind11 = \"NS\"";
mostCurrent._valorind11 = "NS";
 //BA.debugLineNum = 1009;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 1010;BA.debugLine="pregResultadoView.Text = \"No has ingresado in";
_pregresultadoview.setText(BA.ObjectToCharSequence("No has ingresado información sobre el fondo del río"));
 };
 //BA.debugLineNum = 1012;BA.debugLine="pregResultadoView.Tag = \"NS\"";
_pregresultadoview.setTag((Object)("NS"));
 };
 //BA.debugLineNum = 1015;BA.debugLine="If preg = 4 Then";
if (_preg==4) { 
 //BA.debugLineNum = 1016;BA.debugLine="valorind11 = \"10\"";
mostCurrent._valorind11 = "10";
 //BA.debugLineNum = 1017;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 1018;BA.debugLine="pregResultadoView.Text = \"Hay una gran varied";
_pregresultadoview.setText(BA.ObjectToCharSequence("Hay una gran variedad de sustratos en el fondo"));
 };
 //BA.debugLineNum = 1020;BA.debugLine="miniPregunta8.SetBackgroundImage(LoadBitmapSamp";
mostCurrent._minipregunta8.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"mini-fondoarena.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 1021;BA.debugLine="pregResultadoView.Tag = \"MB\"";
_pregresultadoview.setTag((Object)("MB"));
 }else if(_preg==3) { 
 //BA.debugLineNum = 1023;BA.debugLine="valorind11 = \"7.5\"";
mostCurrent._valorind11 = "7.5";
 //BA.debugLineNum = 1024;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 1025;BA.debugLine="pregResultadoView.Text = \"Hay una buena varie";
_pregresultadoview.setText(BA.ObjectToCharSequence("Hay una buena variedad de sustratos en el fondo"));
 };
 //BA.debugLineNum = 1028;BA.debugLine="miniPregunta8.SetBackgroundImage(LoadBitmapSamp";
mostCurrent._minipregunta8.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"mini-fondopiedras.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 1029;BA.debugLine="pregResultadoView.Tag = \"B\"";
_pregresultadoview.setTag((Object)("B"));
 }else if(_preg==2) { 
 //BA.debugLineNum = 1031;BA.debugLine="valorind11 = \"5\"";
mostCurrent._valorind11 = "5";
 //BA.debugLineNum = 1032;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 1033;BA.debugLine="pregResultadoView.Text = \"Hay poca variedad d";
_pregresultadoview.setText(BA.ObjectToCharSequence("Hay poca variedad de sustratos en el fondo"));
 };
 //BA.debugLineNum = 1035;BA.debugLine="miniPregunta8.SetBackgroundImage(LoadBitmapSamp";
mostCurrent._minipregunta8.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"mini-fondoguijarros.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 1036;BA.debugLine="pregResultadoView.Tag = \"R\"";
_pregresultadoview.setTag((Object)("R"));
 }else if(_preg==1) { 
 //BA.debugLineNum = 1038;BA.debugLine="valorind11 = \"2.5\"";
mostCurrent._valorind11 = "2.5";
 //BA.debugLineNum = 1039;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 1040;BA.debugLine="pregResultadoView.Text = \"Hay muy poca varied";
_pregresultadoview.setText(BA.ObjectToCharSequence("Hay muy poca variedad de sustratos en el fondo"));
 };
 //BA.debugLineNum = 1042;BA.debugLine="miniPregunta8.SetBackgroundImage(LoadBitmapSamp";
mostCurrent._minipregunta8.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"mini-fondobarro.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 1043;BA.debugLine="pregResultadoView.Tag = \"M\"";
_pregresultadoview.setTag((Object)("M"));
 };
 }else if(_currentpregunta==12) { 
 //BA.debugLineNum = 1047;BA.debugLine="If rdOpcion1.Checked = True Then";
if (mostCurrent._rdopcion1.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 1048;BA.debugLine="valorind12 = \"0\"";
mostCurrent._valorind12 = "0";
 //BA.debugLineNum = 1049;BA.debugLine="miniPregunta9.SetBackgroundImage(LoadBitmapSamp";
mostCurrent._minipregunta9.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"mini-margenmucha.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 1050;BA.debugLine="If tiporio = \"montana\" Then";
if ((_tiporio).equals("montana")) { 
 //BA.debugLineNum = 1051;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 1052;BA.debugLine="pregResultadoView.Text = \"Las márgenes empina";
_pregresultadoview.setText(BA.ObjectToCharSequence("Las márgenes empinadas en los ríos de montaña pueden ser normales"));
 };
 //BA.debugLineNum = 1054;BA.debugLine="pregResultadoView.Tag = \"R\"";
_pregresultadoview.setTag((Object)("R"));
 }else if((_tiporio).equals("llanura")) { 
 //BA.debugLineNum = 1056;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 1057;BA.debugLine="pregResultadoView.Text = \"Las márgenes empina";
_pregresultadoview.setText(BA.ObjectToCharSequence("Las márgenes empinadas en los ríos de llanura indican una alteración del hábitat"));
 };
 //BA.debugLineNum = 1059;BA.debugLine="pregResultadoView.Tag = \"M\"";
_pregresultadoview.setTag((Object)("M"));
 };
 }else if(mostCurrent._rdopcion2.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 1062;BA.debugLine="valorind12 = \"10\"";
mostCurrent._valorind12 = "10";
 //BA.debugLineNum = 1063;BA.debugLine="miniPregunta9.SetBackgroundImage(LoadBitmapSamp";
mostCurrent._minipregunta9.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"mini-margenpoca.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 1064;BA.debugLine="If tiporio = \"montana\" Then";
if ((_tiporio).equals("montana")) { 
 //BA.debugLineNum = 1065;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 1066;BA.debugLine="pregResultadoView.Text = \"Las márgenes llanas";
_pregresultadoview.setText(BA.ObjectToCharSequence("Las márgenes llanas en los ríos de montaña pueden ser normales"));
 };
 //BA.debugLineNum = 1068;BA.debugLine="pregResultadoView.Tag = \"R\"";
_pregresultadoview.setTag((Object)("R"));
 }else if((_tiporio).equals("llanura")) { 
 //BA.debugLineNum = 1070;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 1071;BA.debugLine="pregResultadoView.Text = \"Las márgenes llanas";
_pregresultadoview.setText(BA.ObjectToCharSequence("Las márgenes llanas en los ríos de llanura son normales"));
 };
 //BA.debugLineNum = 1073;BA.debugLine="pregResultadoView.Tag = \"R\"";
_pregresultadoview.setTag((Object)("R"));
 };
 }else if(mostCurrent._rdopcion3.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 1076;BA.debugLine="valorind12 = \"NS\"";
mostCurrent._valorind12 = "NS";
 //BA.debugLineNum = 1077;BA.debugLine="miniPregunta9.SetBackgroundImage(Null)";
mostCurrent._minipregunta9.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null));
 //BA.debugLineNum = 1078;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 1079;BA.debugLine="pregResultadoView.Text = \"No has ingresado in";
_pregresultadoview.setText(BA.ObjectToCharSequence("No has ingresado información sobre las márgenes"));
 };
 //BA.debugLineNum = 1081;BA.debugLine="pregResultadoView.Tag = \"NS\"";
_pregresultadoview.setTag((Object)("NS"));
 }else {
 //BA.debugLineNum = 1083;BA.debugLine="Return(\"no\")";
if (true) return ("no");
 };
 }else if(_currentpregunta==13) { 
 //BA.debugLineNum = 1086;BA.debugLine="If rdOpcion1.Checked = True Then";
if (mostCurrent._rdopcion1.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 1087;BA.debugLine="valorind13 = \"5\"";
mostCurrent._valorind13 = "5";
 //BA.debugLineNum = 1088;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 1089;BA.debugLine="pregResultadoView.Text = \"El sombreado total";
_pregresultadoview.setText(BA.ObjectToCharSequence("El sombreado total de los ríos afecta al desarrollo de la comunidad de algas"));
 };
 //BA.debugLineNum = 1091;BA.debugLine="pregResultadoView.Tag = \"R\"";
_pregresultadoview.setTag((Object)("R"));
 //BA.debugLineNum = 1092;BA.debugLine="miniPregunta10.SetBackgroundImage(LoadBitmapSa";
mostCurrent._minipregunta10.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"mini-totalsombra.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 }else if(mostCurrent._rdopcion2.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 1094;BA.debugLine="valorind13 = \"10\"";
mostCurrent._valorind13 = "10";
 //BA.debugLineNum = 1095;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 1096;BA.debugLine="pregResultadoView.Text = \"Un sombreado inter";
_pregresultadoview.setText(BA.ObjectToCharSequence("Un sombreado intermedio es óptimo para el desarrollo de la comunidad de algas"));
 };
 //BA.debugLineNum = 1098;BA.debugLine="miniPregunta10.SetBackgroundImage(LoadBitmapS";
mostCurrent._minipregunta10.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"mini-parcialsombra.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 1099;BA.debugLine="pregResultadoView.Tag = \"MB\"";
_pregresultadoview.setTag((Object)("MB"));
 }else if(mostCurrent._rdopcion3.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 1101;BA.debugLine="valorind13 = \"5\"";
mostCurrent._valorind13 = "5";
 //BA.debugLineNum = 1102;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 1103;BA.debugLine="pregResultadoView.Text = \"La ausencia de somb";
_pregresultadoview.setText(BA.ObjectToCharSequence("La ausencia de sombreado puede indicar una degradación del hábitat"));
 };
 //BA.debugLineNum = 1105;BA.debugLine="pregResultadoView.Tag = \"R\"";
_pregresultadoview.setTag((Object)("R"));
 //BA.debugLineNum = 1106;BA.debugLine="miniPregunta10.SetBackgroundImage(LoadBitmapSa";
mostCurrent._minipregunta10.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"mini-sombranada.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 }else if(mostCurrent._rdopcion4.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 1108;BA.debugLine="valorind13 = \"NS\"";
mostCurrent._valorind13 = "NS";
 //BA.debugLineNum = 1109;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 1110;BA.debugLine="pregResultadoView.Text = \"No has ingresado in";
_pregresultadoview.setText(BA.ObjectToCharSequence("No has ingresado información sobre el sombreado del río"));
 };
 //BA.debugLineNum = 1113;BA.debugLine="pregResultadoView.Tag = \"NS\"";
_pregresultadoview.setTag((Object)("NS"));
 }else {
 //BA.debugLineNum = 1115;BA.debugLine="Return(\"no\")";
if (true) return ("no");
 };
 };
 //BA.debugLineNum = 1120;BA.debugLine="Dim pregResultadoImg As ImageView";
_pregresultadoimg = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 1121;BA.debugLine="pregResultadoImg.Initialize(\"\")";
_pregresultadoimg.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 1122;BA.debugLine="If pregResultadoView.Tag <> Null Then";
if (_pregresultadoview.getTag()!= null) { 
 //BA.debugLineNum = 1123;BA.debugLine="If pregResultadoView.Tag = \"MM\" Then";
if ((_pregresultadoview.getTag()).equals((Object)("MM"))) { 
 //BA.debugLineNum = 1124;BA.debugLine="pregResultadoImg.Bitmap = LoadBitmapSample(File";
_pregresultadoimg.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"vinetaMM.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 }else if((_pregresultadoview.getTag()).equals((Object)("M"))) { 
 //BA.debugLineNum = 1126;BA.debugLine="pregResultadoImg.Bitmap = LoadBitmapSample(File";
_pregresultadoimg.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"vinetaM.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 }else if((_pregresultadoview.getTag()).equals((Object)("R"))) { 
 //BA.debugLineNum = 1128;BA.debugLine="pregResultadoImg.Bitmap = LoadBitmapSample(File";
_pregresultadoimg.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"vinetaR.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 }else if((_pregresultadoview.getTag()).equals((Object)("B"))) { 
 //BA.debugLineNum = 1130;BA.debugLine="pregResultadoImg.Bitmap = LoadBitmapSample(File";
_pregresultadoimg.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"vinetaB.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 }else if((_pregresultadoview.getTag()).equals((Object)("MB"))) { 
 //BA.debugLineNum = 1132;BA.debugLine="pregResultadoImg.Bitmap = LoadBitmapSample(File";
_pregresultadoimg.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"vinetaMB.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 }else if((_pregresultadoview.getTag()).equals((Object)("NS"))) { 
 //BA.debugLineNum = 1134;BA.debugLine="pregResultadoImg.Bitmap = LoadBitmapSample(File";
_pregresultadoimg.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"vinetaNS.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 };
 };
 //BA.debugLineNum = 1139;BA.debugLine="pregResultadoView.TextColor = Colors.Black";
_pregresultadoview.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 1140;BA.debugLine="scrResultados.Panel.AddView(pregResultadoImg, 10d";
mostCurrent._scrresultados.getPanel().AddView((android.view.View)(_pregresultadoimg.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10)),(int) (_currentpregunta*anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (60))-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (30))),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (25)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (25)));
 //BA.debugLineNum = 1141;BA.debugLine="scrResultados.Panel.AddView(pregResultadoView, 50";
mostCurrent._scrresultados.getPanel().AddView((android.view.View)(_pregresultadoview.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (50)),(int) (_currentpregunta*anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (60))-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (30))),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (70),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (60)));
 //BA.debugLineNum = 1142;BA.debugLine="scrResultados.Panel.Height = 12 * 90dip";
mostCurrent._scrresultados.getPanel().setHeight((int) (12*anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (90))));
 //BA.debugLineNum = 1144;BA.debugLine="End Sub";
return "";
}
}
