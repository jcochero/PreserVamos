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

public class reporte_habitat_rio extends Activity implements B4AActivity{
	public static reporte_habitat_rio mostCurrent;
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
			processBA = new BA(this.getApplicationContext(), null, null, "ilpla.appear", "ilpla.appear.reporte_habitat_rio");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
            
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (reporte_habitat_rio).");
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
		activityBA = new BA(this, layout, processBA, "ilpla.appear", "ilpla.appear.reporte_habitat_rio");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "ilpla.appear.reporte_habitat_rio", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density, mostCurrent);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (reporte_habitat_rio) Create, isFirst = " + isFirst + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (reporte_habitat_rio) Resume **");
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
		return reporte_habitat_rio.class;
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
            BA.LogInfo("** Activity (reporte_habitat_rio) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
        else
            BA.LogInfo("** Activity (reporte_habitat_rio) Pause event (activity is not paused). **");
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
            reporte_habitat_rio mc = mostCurrent;
			if (mc == null || mc != activity.get())
				return;
			processBA.setActivityPaused(false);
            BA.LogInfo("** Activity (reporte_habitat_rio) Resume **");
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
public static String _ind_pvm_1 = "";
public static String _ind_pvm_2 = "";
public static String _ind_pvm_3 = "";
public static String _ind_pvm_4 = "";
public static String _ind_pvm_5 = "";
public static String _ind_pvm_6 = "";
public static String _ind_pvm_7 = "";
public static String _ind_pvm_8 = "";
public static String _ind_pvm_9 = "";
public static String _ind_pvm_10 = "";
public static String _ind_pvm_11 = "";
public static String _ind_pvm_12 = "";
public static String _ind_pvm_13 = "";
public static String _preguntanumero = "";
public static String _dateandtime = "";
public static int _valorcalidad = 0;
public static int _valorns = 0;
public static String _checklist = "";
public anywheresoftware.b4a.objects.ButtonWrapper _btnmasdetalle = null;
public ilpla.appear.gauge _gauge1 = null;
public anywheresoftware.b4a.objects.B4XViewWrapper.XUI _xui = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnnext = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnprev = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblpercentage = null;
public anywheresoftware.b4a.objects.TabStripViewPager _tabreportehabitatrio = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblsection_q1 = null;
public anywheresoftware.b4a.objects.PanelWrapper _pnlq1_1 = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbltitle_q1_1 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnhelp_q1_1 = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper _radq1_1_a = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper _radq1_1_b = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper _radq1_1_c = null;
public anywheresoftware.b4a.objects.PanelWrapper _pnlq1_2 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnhelp_q1_2 = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbltitle_q1_2 = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper _radq1_2_a = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper _radq1_2_b = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper _radq1_2_c = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper _radq2_1_c = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblsection_q2 = null;
public anywheresoftware.b4a.objects.PanelWrapper _pnlq2_1 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnhelp_q2 = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper _chkq2_4 = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper _chkq2_3 = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper _chkq2_1 = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper _radq2_1_a = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper _radq2_1_b = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper _radq3_1_c = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblsection_q3 = null;
public anywheresoftware.b4a.objects.PanelWrapper _pnlq3_1 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnhelp_q3 = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper _chkq3_3 = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper _chkq3_2 = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper _chkq3_1 = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper _radq3_1_a = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper _radq3_1_b = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblsection_q4 = null;
public anywheresoftware.b4a.objects.PanelWrapper _pnlq4_1 = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper _chkq4_1_c = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper _chkq4_1_b = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper _chkq4_1_a = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper _radq4_2_a = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper _radq4_2_b = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper _radq4_2_c = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbltitle_q4_1 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnhelp_q4_1 = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper _chkq4_1_d = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper _chkq4_1_e = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbltitle_q4_2 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnhelp_q4_2 = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper _radq4_2_d = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbltitle_q4_3 = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper _radq4_3_a = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper _radq4_3_c = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper _radq4_3_d = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnhelp_q4_3 = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper _radq4_3_b = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblsection_q5 = null;
public anywheresoftware.b4a.objects.PanelWrapper _pnlq5_1 = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbltitle_q5_1 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnhelp_q5_1 = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper _radq5_1_a = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper _radq5_1_b = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper _radq5_1_c = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper _radq5_1_d = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper _radq5_1_e = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper _radq5_1_f = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper _radq5_1_g = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper _radq5_1_h = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper _radq5_1_i = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblsection_q5p2 = null;
public anywheresoftware.b4a.objects.PanelWrapper _pnlq5p2_1 = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbltitle_q5p2_1 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnhelp_q5p2_1 = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper _radq5p2_1_a = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper _radq5p2_1_b = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper _radq5p2_1_c = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper _radq5p2_1_d = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbltitle_q5p2_2 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnhelp_q5p2_2 = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper _radq5p2_2_a = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper _radq5p2_2_b = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper _radq5p2_2_c = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper _radq5p2_2_d = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblsection_q6 = null;
public anywheresoftware.b4a.objects.PanelWrapper _pnlq6_1 = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper _chkq6_1_c = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper _chkq6_1_b = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper _chkq6_1_a = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbltitle_q6_1 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnhelp_q6_1 = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper _chkq6_1_d = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper _chkq6_1_e = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbltitle_q6_2 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnhelp_q6_2 = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper _radq6_2_a = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper _radq6_2_c = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper _radq6_2_d = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper _radq6_2_b = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbltitle_q6_3 = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper _radq6_3_a = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper _radq6_3_c = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper _radq6_3_d = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnhelp_q6_3 = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper _radq6_3_b = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblsection_q7 = null;
public anywheresoftware.b4a.objects.PanelWrapper _pnlq7_1 = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper _chkq7_1_c = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper _chkq7_1_b = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper _chkq7_1_a = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbltitle_q7_1 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnhelp_q7_1 = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper _chkq7_1_d = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbltitle_q7_2 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnhelp_q7_2 = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper _chkq7_2_a = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper _chkq7_2_b = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper _chkq7_2_c = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper _chkq7_2_d = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper _radq2_1_d = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper _chkq4_1_f = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblsection_q = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnhelp_q8_1 = null;
public anywheresoftware.b4a.objects.PanelWrapper _pnlq8_1 = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper _chkq8_1_c = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper _chkq8_1_b = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper _chkq8_1_a = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbltitle_q8_1 = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper _chkq8_1_d = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper _chkq8_1_e = null;
public static boolean _haycampo = false;
public anywheresoftware.b4a.objects.LabelWrapper _lblsection_q8 = null;
public anywheresoftware.b4a.objects.PanelWrapper _pnlq8_2 = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbltitle_q8_2 = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper _chkq8_2_a = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper _chkq8_2_b = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper _chkq8_2_c = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper _chkq8_2_d = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper _chkq8_2_e = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper _chkq3_2_a = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper _chkq3_2_b = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper _chkq3_2_c = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper _chkq3_2_d = null;
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
 //BA.debugLineNum = 300;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
 //BA.debugLineNum = 301;BA.debugLine="Activity.LoadLayout(\"Reporte_Habitat_Rio_Main\")";
mostCurrent._activity.LoadLayout("Reporte_Habitat_Rio_Main",mostCurrent.activityBA);
 //BA.debugLineNum = 305;BA.debugLine="preguntanumero = 1";
mostCurrent._preguntanumero = BA.NumberToString(1);
 //BA.debugLineNum = 306;BA.debugLine="valorind1 = \"\"";
mostCurrent._valorind1 = "";
 //BA.debugLineNum = 307;BA.debugLine="valorind2 = \"\"";
mostCurrent._valorind2 = "";
 //BA.debugLineNum = 308;BA.debugLine="valorind3 = \"\"";
mostCurrent._valorind3 = "";
 //BA.debugLineNum = 309;BA.debugLine="valorind4 = \"\"";
mostCurrent._valorind4 = "";
 //BA.debugLineNum = 310;BA.debugLine="valorind5 = \"\"";
mostCurrent._valorind5 = "";
 //BA.debugLineNum = 311;BA.debugLine="valorind6 = \"\"";
mostCurrent._valorind6 = "";
 //BA.debugLineNum = 312;BA.debugLine="valorind7 = \"\"";
mostCurrent._valorind7 = "";
 //BA.debugLineNum = 313;BA.debugLine="valorind8 = \"\"";
mostCurrent._valorind8 = "";
 //BA.debugLineNum = 314;BA.debugLine="valorind9 = \"\"";
mostCurrent._valorind9 = "";
 //BA.debugLineNum = 315;BA.debugLine="valorind10 = \"\"";
mostCurrent._valorind10 = "";
 //BA.debugLineNum = 316;BA.debugLine="valorind11 = \"\"";
mostCurrent._valorind11 = "";
 //BA.debugLineNum = 317;BA.debugLine="valorind12 = \"\"";
mostCurrent._valorind12 = "";
 //BA.debugLineNum = 318;BA.debugLine="valorind13 = \"\"";
mostCurrent._valorind13 = "";
 //BA.debugLineNum = 319;BA.debugLine="valorind14 = \"\"";
mostCurrent._valorind14 = "";
 //BA.debugLineNum = 320;BA.debugLine="valorind15 = \"\"";
mostCurrent._valorind15 = "";
 //BA.debugLineNum = 321;BA.debugLine="valorind16 = \"\"";
mostCurrent._valorind16 = "";
 //BA.debugLineNum = 322;BA.debugLine="ind_pvm_1  = \"\"";
mostCurrent._ind_pvm_1 = "";
 //BA.debugLineNum = 323;BA.debugLine="ind_pvm_2  = \"\"";
mostCurrent._ind_pvm_2 = "";
 //BA.debugLineNum = 324;BA.debugLine="ind_pvm_3  = \"\"";
mostCurrent._ind_pvm_3 = "";
 //BA.debugLineNum = 325;BA.debugLine="ind_pvm_4  = \"\"";
mostCurrent._ind_pvm_4 = "";
 //BA.debugLineNum = 326;BA.debugLine="ind_pvm_5  = \"\"";
mostCurrent._ind_pvm_5 = "";
 //BA.debugLineNum = 327;BA.debugLine="ind_pvm_6  = \"\"";
mostCurrent._ind_pvm_6 = "";
 //BA.debugLineNum = 328;BA.debugLine="ind_pvm_7  = \"\"";
mostCurrent._ind_pvm_7 = "";
 //BA.debugLineNum = 329;BA.debugLine="ind_pvm_8  = \"\"";
mostCurrent._ind_pvm_8 = "";
 //BA.debugLineNum = 330;BA.debugLine="ind_pvm_9  = \"\"";
mostCurrent._ind_pvm_9 = "";
 //BA.debugLineNum = 331;BA.debugLine="ind_pvm_10  = \"\"";
mostCurrent._ind_pvm_10 = "";
 //BA.debugLineNum = 332;BA.debugLine="ind_pvm_11  = \"\"";
mostCurrent._ind_pvm_11 = "";
 //BA.debugLineNum = 333;BA.debugLine="ind_pvm_12  = \"\"";
mostCurrent._ind_pvm_12 = "";
 //BA.debugLineNum = 335;BA.debugLine="DateTime.DateFormat = \"dd-MM-yyyy\"";
anywheresoftware.b4a.keywords.Common.DateTime.setDateFormat("dd-MM-yyyy");
 //BA.debugLineNum = 336;BA.debugLine="DateTime.TimeFormat = \"HH:mm\"";
anywheresoftware.b4a.keywords.Common.DateTime.setTimeFormat("HH:mm");
 //BA.debugLineNum = 337;BA.debugLine="dateandtime = DateTime.Date(DateTime.now)";
mostCurrent._dateandtime = anywheresoftware.b4a.keywords.Common.DateTime.Date(anywheresoftware.b4a.keywords.Common.DateTime.getNow());
 //BA.debugLineNum = 338;BA.debugLine="valorcalidad = 0";
_valorcalidad = (int) (0);
 //BA.debugLineNum = 339;BA.debugLine="valorNS = 0";
_valorns = (int) (0);
 //BA.debugLineNum = 342;BA.debugLine="tabReporteHabitatRio.LoadLayout(\"reporte_habitat_";
mostCurrent._tabreportehabitatrio.LoadLayout("reporte_habitat_rio_q1",BA.ObjectToCharSequence(""));
 //BA.debugLineNum = 343;BA.debugLine="tabReporteHabitatRio.LoadLayout(\"reporte_habitat_";
mostCurrent._tabreportehabitatrio.LoadLayout("reporte_habitat_rio_q2",BA.ObjectToCharSequence(""));
 //BA.debugLineNum = 344;BA.debugLine="tabReporteHabitatRio.LoadLayout(\"reporte_habitat_";
mostCurrent._tabreportehabitatrio.LoadLayout("reporte_habitat_rio_q3",BA.ObjectToCharSequence(""));
 //BA.debugLineNum = 345;BA.debugLine="tabReporteHabitatRio.LoadLayout(\"reporte_habitat_";
mostCurrent._tabreportehabitatrio.LoadLayout("reporte_habitat_rio_q4",BA.ObjectToCharSequence(""));
 //BA.debugLineNum = 346;BA.debugLine="tabReporteHabitatRio.LoadLayout(\"reporte_habitat_";
mostCurrent._tabreportehabitatrio.LoadLayout("reporte_habitat_rio_checklist",BA.ObjectToCharSequence(""));
 //BA.debugLineNum = 347;BA.debugLine="tabReporteHabitatRio.LoadLayout(\"reporte_habitat_";
mostCurrent._tabreportehabitatrio.LoadLayout("reporte_habitat_rio_q5",BA.ObjectToCharSequence(""));
 //BA.debugLineNum = 348;BA.debugLine="tabReporteHabitatRio.LoadLayout(\"reporte_habitat_";
mostCurrent._tabreportehabitatrio.LoadLayout("reporte_habitat_rio_q5p2",BA.ObjectToCharSequence(""));
 //BA.debugLineNum = 349;BA.debugLine="tabReporteHabitatRio.LoadLayout(\"reporte_habitat_";
mostCurrent._tabreportehabitatrio.LoadLayout("reporte_habitat_rio_q6",BA.ObjectToCharSequence(""));
 //BA.debugLineNum = 350;BA.debugLine="tabReporteHabitatRio.LoadLayout(\"reporte_habitat_";
mostCurrent._tabreportehabitatrio.LoadLayout("reporte_habitat_rio_exoticas",BA.ObjectToCharSequence(""));
 //BA.debugLineNum = 351;BA.debugLine="tabReporteHabitatRio.LoadLayout(\"reporte_habitat_";
mostCurrent._tabreportehabitatrio.LoadLayout("reporte_habitat_rio_q7",BA.ObjectToCharSequence(""));
 //BA.debugLineNum = 352;BA.debugLine="tabReporteHabitatRio.LoadLayout(\"reporte_habitat_";
mostCurrent._tabreportehabitatrio.LoadLayout("reporte_habitat_rio_q8",BA.ObjectToCharSequence(""));
 //BA.debugLineNum = 354;BA.debugLine="End Sub";
return "";
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 361;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 363;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 356;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 359;BA.debugLine="End Sub";
return "";
}
public static String  _btncerrar_click() throws Exception{
 //BA.debugLineNum = 1025;BA.debugLine="Sub btnCerrar_Click";
 //BA.debugLineNum = 1028;BA.debugLine="pnlResultados.Visible = False";
mostCurrent._pnlresultados.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1029;BA.debugLine="GenerarScreenshot";
_generarscreenshot();
 //BA.debugLineNum = 1032;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 1033;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 1034;BA.debugLine="StartActivity(Reporte_Fotos)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._reporte_fotos.getObject()));
 //BA.debugLineNum = 1035;BA.debugLine="End Sub";
return "";
}
public static String  _btnhelp_q1_1_click() throws Exception{
 //BA.debugLineNum = 1084;BA.debugLine="Private Sub btnHelp_Q1_1_Click";
 //BA.debugLineNum = 1086;BA.debugLine="End Sub";
return "";
}
public static String  _btnhelp_q1_2_click() throws Exception{
 //BA.debugLineNum = 1080;BA.debugLine="Private Sub btnHelp_Q1_2_Click";
 //BA.debugLineNum = 1082;BA.debugLine="End Sub";
return "";
}
public static String  _btnhelp_q2_click() throws Exception{
 //BA.debugLineNum = 1107;BA.debugLine="Private Sub btnHelp_Q2_Click";
 //BA.debugLineNum = 1109;BA.debugLine="End Sub";
return "";
}
public static String  _btnhelp_q3_click() throws Exception{
 //BA.debugLineNum = 1149;BA.debugLine="Private Sub btnHelp_Q3_Click";
 //BA.debugLineNum = 1151;BA.debugLine="End Sub";
return "";
}
public static String  _btnhelp_q4_1_click() throws Exception{
 //BA.debugLineNum = 1165;BA.debugLine="Private Sub btnHelp_Q4_1_Click";
 //BA.debugLineNum = 1167;BA.debugLine="End Sub";
return "";
}
public static String  _btnhelp_q4_2_click() throws Exception{
 //BA.debugLineNum = 1161;BA.debugLine="Private Sub btnHelp_Q4_2_Click";
 //BA.debugLineNum = 1163;BA.debugLine="End Sub";
return "";
}
public static String  _btnhelp_q4_3_click() throws Exception{
 //BA.debugLineNum = 1157;BA.debugLine="Private Sub btnHelp_Q4_3_Click";
 //BA.debugLineNum = 1159;BA.debugLine="End Sub";
return "";
}
public static String  _btnhelp_q5_1_click() throws Exception{
 //BA.debugLineNum = 1174;BA.debugLine="Private Sub btnHelp_Q5_1_Click";
 //BA.debugLineNum = 1176;BA.debugLine="End Sub";
return "";
}
public static String  _btnhelp_q5p2_1_click() throws Exception{
 //BA.debugLineNum = 1189;BA.debugLine="Private Sub btnHelp_Q5p2_1_Click";
 //BA.debugLineNum = 1191;BA.debugLine="End Sub";
return "";
}
public static String  _btnhelp_q5p2_2_click() throws Exception{
 //BA.debugLineNum = 1185;BA.debugLine="Private Sub btnHelp_Q5p2_2_Click";
 //BA.debugLineNum = 1187;BA.debugLine="End Sub";
return "";
}
public static String  _btnhelp_q6_1_click() throws Exception{
 //BA.debugLineNum = 1209;BA.debugLine="Private Sub btnHelp_Q6_1_Click";
 //BA.debugLineNum = 1211;BA.debugLine="End Sub";
return "";
}
public static String  _btnhelp_q6_2_click() throws Exception{
 //BA.debugLineNum = 1205;BA.debugLine="Private Sub btnHelp_Q6_2_Click";
 //BA.debugLineNum = 1207;BA.debugLine="End Sub";
return "";
}
public static String  _btnhelp_q6_3_click() throws Exception{
 //BA.debugLineNum = 1201;BA.debugLine="Private Sub btnHelp_Q6_3_Click";
 //BA.debugLineNum = 1203;BA.debugLine="End Sub";
return "";
}
public static String  _btnhelp_q7_1_click() throws Exception{
 //BA.debugLineNum = 1223;BA.debugLine="Private Sub btnHelp_Q7_1_Click";
 //BA.debugLineNum = 1225;BA.debugLine="End Sub";
return "";
}
public static String  _btnhelp_q7_2_click() throws Exception{
 //BA.debugLineNum = 1219;BA.debugLine="Private Sub btnHelp_Q7_2_Click";
 //BA.debugLineNum = 1221;BA.debugLine="End Sub";
return "";
}
public static String  _btnhelp_q8_1_click() throws Exception{
 //BA.debugLineNum = 1232;BA.debugLine="Private Sub btnHelp_Q8_1_Click";
 //BA.debugLineNum = 1234;BA.debugLine="End Sub";
return "";
}
public static String  _btnmasdetalle_click() throws Exception{
 //BA.debugLineNum = 1021;BA.debugLine="Sub btnMasDetalle_Click";
 //BA.debugLineNum = 1022;BA.debugLine="scrResultados.Visible = True";
mostCurrent._scrresultados.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1023;BA.debugLine="btnMasDetalle.Visible = False";
mostCurrent._btnmasdetalle.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1024;BA.debugLine="End Sub";
return "";
}
public static String  _btnnext_click() throws Exception{
int _countveg = 0;
 //BA.debugLineNum = 402;BA.debugLine="Private Sub btnNext_Click";
 //BA.debugLineNum = 403;BA.debugLine="btnTerminar.Visible = False";
mostCurrent._btnterminar.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 406;BA.debugLine="If tabReporteHabitatRio.CurrentPage = 0 Then";
if (mostCurrent._tabreportehabitatrio.getCurrentPage()==0) { 
 //BA.debugLineNum = 408;BA.debugLine="If radQ1_1_a.Checked = True Then";
if (mostCurrent._radq1_1_a.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 409;BA.debugLine="valorind9 = 0";
mostCurrent._valorind9 = BA.NumberToString(0);
 }else if(mostCurrent._radq1_1_b.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 411;BA.debugLine="valorind9 = 10";
mostCurrent._valorind9 = BA.NumberToString(10);
 }else {
 //BA.debugLineNum = 413;BA.debugLine="valorind9 = \"NS\"";
mostCurrent._valorind9 = "NS";
 };
 //BA.debugLineNum = 416;BA.debugLine="If radQ1_2_a.Checked = True Then";
if (mostCurrent._radq1_2_a.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 417;BA.debugLine="valorind10 = 0";
mostCurrent._valorind10 = BA.NumberToString(0);
 }else if(mostCurrent._radq1_2_b.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 419;BA.debugLine="valorind10 = 10";
mostCurrent._valorind10 = BA.NumberToString(10);
 }else {
 //BA.debugLineNum = 421;BA.debugLine="valorind10 = \"NS\"";
mostCurrent._valorind10 = "NS";
 };
 }else if(mostCurrent._tabreportehabitatrio.getCurrentPage()==1) { 
 //BA.debugLineNum = 426;BA.debugLine="If chkQ2_3.Checked = True Then";
if (mostCurrent._chkq2_3.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 427;BA.debugLine="valorind6 = 0";
mostCurrent._valorind6 = BA.NumberToString(0);
 }else {
 //BA.debugLineNum = 429;BA.debugLine="valorind6 = 10";
mostCurrent._valorind6 = BA.NumberToString(10);
 };
 //BA.debugLineNum = 432;BA.debugLine="If chkQ2_4.Checked = True Then";
if (mostCurrent._chkq2_4.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 433;BA.debugLine="ind_pvm_1 = 0";
mostCurrent._ind_pvm_1 = BA.NumberToString(0);
 }else {
 //BA.debugLineNum = 435;BA.debugLine="ind_pvm_1 = 10";
mostCurrent._ind_pvm_1 = BA.NumberToString(10);
 };
 //BA.debugLineNum = 438;BA.debugLine="If chkQ2_1.Checked = True Then";
if (mostCurrent._chkq2_1.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 439;BA.debugLine="valorind5 = 10";
mostCurrent._valorind5 = BA.NumberToString(10);
 }else {
 //BA.debugLineNum = 441;BA.debugLine="If radQ2_1_a.Checked = True Then";
if (mostCurrent._radq2_1_a.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 442;BA.debugLine="valorind5 = 10";
mostCurrent._valorind5 = BA.NumberToString(10);
 }else if(mostCurrent._radq2_1_b.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 444;BA.debugLine="valorind5 = 0";
mostCurrent._valorind5 = BA.NumberToString(0);
 }else if(mostCurrent._radq2_1_d.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 446;BA.debugLine="valorind5 = 0";
mostCurrent._valorind5 = BA.NumberToString(0);
 }else {
 //BA.debugLineNum = 448;BA.debugLine="valorind5 = \"NS\"";
mostCurrent._valorind5 = "NS";
 };
 };
 }else if(mostCurrent._tabreportehabitatrio.getCurrentPage()==2) { 
 //BA.debugLineNum = 453;BA.debugLine="valorind7 = 10";
mostCurrent._valorind7 = BA.NumberToString(10);
 //BA.debugLineNum = 454;BA.debugLine="If chkQ3_1.Checked = True Then";
if (mostCurrent._chkq3_1.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 455;BA.debugLine="If valorind7 > 0 Then";
if ((double)(Double.parseDouble(mostCurrent._valorind7))>0) { 
 //BA.debugLineNum = 456;BA.debugLine="valorind7 = valorind7 -2";
mostCurrent._valorind7 = BA.NumberToString((double)(Double.parseDouble(mostCurrent._valorind7))-2);
 };
 };
 //BA.debugLineNum = 459;BA.debugLine="If chkQ3_3.Checked = True Then";
if (mostCurrent._chkq3_3.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 460;BA.debugLine="If valorind7 > 0 Then";
if ((double)(Double.parseDouble(mostCurrent._valorind7))>0) { 
 //BA.debugLineNum = 461;BA.debugLine="valorind7 = valorind7 -2";
mostCurrent._valorind7 = BA.NumberToString((double)(Double.parseDouble(mostCurrent._valorind7))-2);
 };
 };
 //BA.debugLineNum = 465;BA.debugLine="If chkQ3_2_a.Checked = True Then";
if (mostCurrent._chkq3_2_a.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 466;BA.debugLine="valorind7 = valorind7 -1";
mostCurrent._valorind7 = BA.NumberToString((double)(Double.parseDouble(mostCurrent._valorind7))-1);
 };
 //BA.debugLineNum = 468;BA.debugLine="If chkQ3_2_b.Checked = True Then";
if (mostCurrent._chkq3_2_b.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 469;BA.debugLine="valorind7 = valorind7 -1";
mostCurrent._valorind7 = BA.NumberToString((double)(Double.parseDouble(mostCurrent._valorind7))-1);
 }else if(mostCurrent._chkq3_2_a.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 471;BA.debugLine="valorind7 = valorind7 -1";
mostCurrent._valorind7 = BA.NumberToString((double)(Double.parseDouble(mostCurrent._valorind7))-1);
 }else if(mostCurrent._chkq3_2_d.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 473;BA.debugLine="valorind7 = valorind7 -1";
mostCurrent._valorind7 = BA.NumberToString((double)(Double.parseDouble(mostCurrent._valorind7))-1);
 };
 //BA.debugLineNum = 475;BA.debugLine="If valorind7 = 2 Then";
if ((mostCurrent._valorind7).equals(BA.NumberToString(2))) { 
 //BA.debugLineNum = 476;BA.debugLine="valorind7 = 0";
mostCurrent._valorind7 = BA.NumberToString(0);
 };
 }else if(mostCurrent._tabreportehabitatrio.getCurrentPage()==3) { 
 //BA.debugLineNum = 480;BA.debugLine="If radQ4_2_a.Checked = True Then";
if (mostCurrent._radq4_2_a.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 481;BA.debugLine="ind_pvm_2 = 10";
mostCurrent._ind_pvm_2 = BA.NumberToString(10);
 }else if(mostCurrent._radq4_2_b.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 483;BA.debugLine="ind_pvm_2 = 8";
mostCurrent._ind_pvm_2 = BA.NumberToString(8);
 }else if(mostCurrent._radq4_2_c.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 485;BA.debugLine="ind_pvm_2 = 0";
mostCurrent._ind_pvm_2 = BA.NumberToString(0);
 }else {
 //BA.debugLineNum = 487;BA.debugLine="ind_pvm_2 = \"NS\"";
mostCurrent._ind_pvm_2 = "NS";
 };
 //BA.debugLineNum = 490;BA.debugLine="If radQ4_3_a.Checked = True Then";
if (mostCurrent._radq4_3_a.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 491;BA.debugLine="valorind4 = 10";
mostCurrent._valorind4 = BA.NumberToString(10);
 }else if(mostCurrent._radq4_3_b.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 493;BA.debugLine="valorind4 = 8";
mostCurrent._valorind4 = BA.NumberToString(8);
 }else if(mostCurrent._radq4_3_c.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 495;BA.debugLine="valorind4 = 0";
mostCurrent._valorind4 = BA.NumberToString(0);
 }else {
 //BA.debugLineNum = 497;BA.debugLine="valorind4 = \"NS\"";
mostCurrent._valorind4 = "NS";
 };
 }else if(mostCurrent._tabreportehabitatrio.getCurrentPage()==4) { 
 //BA.debugLineNum = 501;BA.debugLine="checklist = \"\"";
mostCurrent._checklist = "";
 //BA.debugLineNum = 502;BA.debugLine="If chkQ4_1_a.Checked  = True Then";
if (mostCurrent._chkq4_1_a.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 503;BA.debugLine="checklist = checklist & \";aves\"";
mostCurrent._checklist = mostCurrent._checklist+";aves";
 };
 //BA.debugLineNum = 505;BA.debugLine="If chkQ4_1_b.Checked  = True Then";
if (mostCurrent._chkq4_1_b.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 506;BA.debugLine="checklist = checklist & \";peces\"";
mostCurrent._checklist = mostCurrent._checklist+";peces";
 };
 //BA.debugLineNum = 508;BA.debugLine="If chkQ4_1_c.Checked  = True Then";
if (mostCurrent._chkq4_1_c.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 509;BA.debugLine="checklist = checklist & \";libelulas\"";
mostCurrent._checklist = mostCurrent._checklist+";libelulas";
 };
 //BA.debugLineNum = 511;BA.debugLine="If chkQ4_1_d.Checked  = True Then";
if (mostCurrent._chkq4_1_d.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 512;BA.debugLine="checklist = checklist & \";mosquitos\"";
mostCurrent._checklist = mostCurrent._checklist+";mosquitos";
 };
 //BA.debugLineNum = 514;BA.debugLine="If chkQ4_1_e.Checked  = True Then";
if (mostCurrent._chkq4_1_e.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 515;BA.debugLine="checklist = checklist & \";roedores\"";
mostCurrent._checklist = mostCurrent._checklist+";roedores";
 };
 //BA.debugLineNum = 517;BA.debugLine="If chkQ4_1_f.Checked  = True Then";
if (mostCurrent._chkq4_1_f.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 518;BA.debugLine="checklist = checklist & \";tortugas\"";
mostCurrent._checklist = mostCurrent._checklist+";tortugas";
 };
 }else if(mostCurrent._tabreportehabitatrio.getCurrentPage()==5) { 
 //BA.debugLineNum = 523;BA.debugLine="haycampo = False";
_haycampo = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 524;BA.debugLine="If radQ5_1_a.Checked = True Then";
if (mostCurrent._radq5_1_a.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 525;BA.debugLine="valorind1 = 0";
mostCurrent._valorind1 = BA.NumberToString(0);
 }else if(mostCurrent._radq5_1_b.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 527;BA.debugLine="valorind1 = 2";
mostCurrent._valorind1 = BA.NumberToString(2);
 }else if(mostCurrent._radq5_1_c.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 529;BA.debugLine="valorind1 = 4";
mostCurrent._valorind1 = BA.NumberToString(4);
 }else if(mostCurrent._radq5_1_d.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 531;BA.debugLine="valorind1 = 10";
mostCurrent._valorind1 = BA.NumberToString(10);
 }else if(mostCurrent._radq5_1_e.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 533;BA.debugLine="valorind1 = 10";
mostCurrent._valorind1 = BA.NumberToString(10);
 }else if(mostCurrent._radq5_1_f.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 535;BA.debugLine="valorind1 = 4";
mostCurrent._valorind1 = BA.NumberToString(4);
 }else if(mostCurrent._radq5_1_g.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 537;BA.debugLine="valorind1 = 2";
mostCurrent._valorind1 = BA.NumberToString(2);
 }else if(mostCurrent._radq5_1_h.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 539;BA.debugLine="haycampo = True";
_haycampo = anywheresoftware.b4a.keywords.Common.True;
 }else if(mostCurrent._radq5_1_i.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 541;BA.debugLine="valorind1 = \"NS\"";
mostCurrent._valorind1 = "NS";
 };
 }else if(mostCurrent._tabreportehabitatrio.getCurrentPage()==6) { 
 //BA.debugLineNum = 549;BA.debugLine="If radQ5p2_1_a.Checked = True Then";
if (mostCurrent._radq5p2_1_a.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 550;BA.debugLine="valorind2 = 0";
mostCurrent._valorind2 = BA.NumberToString(0);
 }else if(mostCurrent._radq5p2_1_b.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 552;BA.debugLine="valorind2 = 4";
mostCurrent._valorind2 = BA.NumberToString(4);
 }else if(mostCurrent._radq5p2_1_c.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 554;BA.debugLine="valorind2 = 10";
mostCurrent._valorind2 = BA.NumberToString(10);
 }else if(mostCurrent._radq5p2_1_d.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 556;BA.debugLine="valorind2 = \"NS\"";
mostCurrent._valorind2 = "NS";
 };
 //BA.debugLineNum = 559;BA.debugLine="If radQ5p2_2_a.Checked = True Then";
if (mostCurrent._radq5p2_2_a.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 560;BA.debugLine="valorind1 = 2";
mostCurrent._valorind1 = BA.NumberToString(2);
 }else if(mostCurrent._radq5p2_2_b.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 562;BA.debugLine="valorind1 = 2";
mostCurrent._valorind1 = BA.NumberToString(2);
 }else if(mostCurrent._radq5p2_2_c.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 564;BA.debugLine="valorind1 = 6";
mostCurrent._valorind1 = BA.NumberToString(6);
 }else if(mostCurrent._radq5p2_2_d.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 566;BA.debugLine="valorind1 = 10";
mostCurrent._valorind1 = BA.NumberToString(10);
 };
 }else if(mostCurrent._tabreportehabitatrio.getCurrentPage()==7) { 
 //BA.debugLineNum = 571;BA.debugLine="Dim countveg As Int";
_countveg = 0;
 //BA.debugLineNum = 572;BA.debugLine="If chkQ6_1_a.Checked = True Then";
if (mostCurrent._chkq6_1_a.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 573;BA.debugLine="countveg = countveg + 1";
_countveg = (int) (_countveg+1);
 };
 //BA.debugLineNum = 575;BA.debugLine="If chkQ6_1_b.Checked = True Then";
if (mostCurrent._chkq6_1_b.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 576;BA.debugLine="countveg = countveg + 1";
_countveg = (int) (_countveg+1);
 };
 //BA.debugLineNum = 578;BA.debugLine="If chkQ6_1_c.Checked = True Then";
if (mostCurrent._chkq6_1_c.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 579;BA.debugLine="countveg = countveg + 1";
_countveg = (int) (_countveg+1);
 };
 //BA.debugLineNum = 581;BA.debugLine="If chkQ6_1_d.Checked = True Then";
if (mostCurrent._chkq6_1_d.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 582;BA.debugLine="countveg = countveg + 1";
_countveg = (int) (_countveg+1);
 };
 //BA.debugLineNum = 584;BA.debugLine="If chkQ6_1_e.Checked = True Then";
if (mostCurrent._chkq6_1_e.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 585;BA.debugLine="countveg = 0";
_countveg = (int) (0);
 };
 //BA.debugLineNum = 587;BA.debugLine="If countveg >= 3 Then";
if (_countveg>=3) { 
 //BA.debugLineNum = 588;BA.debugLine="valorind3 = 10";
mostCurrent._valorind3 = BA.NumberToString(10);
 }else if(_countveg==2) { 
 //BA.debugLineNum = 590;BA.debugLine="valorind3 = 8";
mostCurrent._valorind3 = BA.NumberToString(8);
 }else if(_countveg==1) { 
 //BA.debugLineNum = 592;BA.debugLine="valorind3 = 4";
mostCurrent._valorind3 = BA.NumberToString(4);
 }else if(_countveg==0) { 
 //BA.debugLineNum = 594;BA.debugLine="valorind3 = 0";
mostCurrent._valorind3 = BA.NumberToString(0);
 };
 }else if(mostCurrent._tabreportehabitatrio.getCurrentPage()==8) { 
 //BA.debugLineNum = 600;BA.debugLine="If radQ6_2_a.Checked = True Then";
if (mostCurrent._radq6_2_a.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 601;BA.debugLine="ind_pvm_3 = 0";
mostCurrent._ind_pvm_3 = BA.NumberToString(0);
 }else if(mostCurrent._radq6_2_b.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 603;BA.debugLine="ind_pvm_3 = 2";
mostCurrent._ind_pvm_3 = BA.NumberToString(2);
 }else if(mostCurrent._radq6_2_c.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 605;BA.debugLine="ind_pvm_3 = 10";
mostCurrent._ind_pvm_3 = BA.NumberToString(10);
 }else if(mostCurrent._radq6_2_d.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 607;BA.debugLine="ind_pvm_3 = \"NS\"";
mostCurrent._ind_pvm_3 = "NS";
 };
 //BA.debugLineNum = 610;BA.debugLine="If radQ6_3_a.Checked = True Then";
if (mostCurrent._radq6_3_a.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 611;BA.debugLine="ind_pvm_4 = 0";
mostCurrent._ind_pvm_4 = BA.NumberToString(0);
 }else if(mostCurrent._radq6_3_b.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 613;BA.debugLine="ind_pvm_4 = 2";
mostCurrent._ind_pvm_4 = BA.NumberToString(2);
 }else if(mostCurrent._radq6_3_c.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 615;BA.debugLine="ind_pvm_4 = 10";
mostCurrent._ind_pvm_4 = BA.NumberToString(10);
 }else if(mostCurrent._radq6_3_d.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 617;BA.debugLine="ind_pvm_4 = \"NS\"";
mostCurrent._ind_pvm_4 = "NS";
 };
 }else if(mostCurrent._tabreportehabitatrio.getCurrentPage()==9) { 
 //BA.debugLineNum = 621;BA.debugLine="If chkQ7_1_a.Checked = True Then";
if (mostCurrent._chkq7_1_a.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 622;BA.debugLine="ind_pvm_5 = \"si\"";
mostCurrent._ind_pvm_5 = "si";
 }else {
 //BA.debugLineNum = 624;BA.debugLine="ind_pvm_5 = \"no\"";
mostCurrent._ind_pvm_5 = "no";
 };
 //BA.debugLineNum = 627;BA.debugLine="If chkQ7_1_c.Checked = True Then";
if (mostCurrent._chkq7_1_c.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 628;BA.debugLine="ind_pvm_6 = \"si\"";
mostCurrent._ind_pvm_6 = "si";
 }else {
 //BA.debugLineNum = 630;BA.debugLine="ind_pvm_6 = \"no\"";
mostCurrent._ind_pvm_6 = "no";
 };
 //BA.debugLineNum = 633;BA.debugLine="If chkQ7_1_b.Checked = True Then";
if (mostCurrent._chkq7_1_b.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 634;BA.debugLine="ind_pvm_7 = \"si\"";
mostCurrent._ind_pvm_7 = "si";
 }else {
 //BA.debugLineNum = 636;BA.debugLine="ind_pvm_7 = \"no\"";
mostCurrent._ind_pvm_7 = "no";
 };
 //BA.debugLineNum = 639;BA.debugLine="If chkQ7_1_d.Checked = True Then";
if (mostCurrent._chkq7_1_d.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 640;BA.debugLine="ind_pvm_8 = \"si\"";
mostCurrent._ind_pvm_8 = "si";
 }else {
 //BA.debugLineNum = 642;BA.debugLine="ind_pvm_8 = \"no\"";
mostCurrent._ind_pvm_8 = "no";
 };
 }else if(mostCurrent._tabreportehabitatrio.getCurrentPage()==10) { 
 };
 //BA.debugLineNum = 654;BA.debugLine="If tabReporteHabitatRio.CurrentPage = 0 Or tabRep";
if (mostCurrent._tabreportehabitatrio.getCurrentPage()==0 || mostCurrent._tabreportehabitatrio.getCurrentPage()==1 || mostCurrent._tabreportehabitatrio.getCurrentPage()==2 || mostCurrent._tabreportehabitatrio.getCurrentPage()==3 || mostCurrent._tabreportehabitatrio.getCurrentPage()==4 || mostCurrent._tabreportehabitatrio.getCurrentPage()==6 || mostCurrent._tabreportehabitatrio.getCurrentPage()==7 || mostCurrent._tabreportehabitatrio.getCurrentPage()==8) { 
 //BA.debugLineNum = 658;BA.debugLine="tabReporteHabitatRio.ScrollTo(tabReporteHabitatR";
mostCurrent._tabreportehabitatrio.ScrollTo((int) (mostCurrent._tabreportehabitatrio.getCurrentPage()+1),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 659;BA.debugLine="btnNext.Visible = True";
mostCurrent._btnnext.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 660;BA.debugLine="btnPrev.Visible = True";
mostCurrent._btnprev.setVisible(anywheresoftware.b4a.keywords.Common.True);
 }else if(mostCurrent._tabreportehabitatrio.getCurrentPage()==5) { 
 //BA.debugLineNum = 662;BA.debugLine="If haycampo = True Then";
if (_haycampo==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 663;BA.debugLine="tabReporteHabitatRio.ScrollTo(6, True)";
mostCurrent._tabreportehabitatrio.ScrollTo((int) (6),anywheresoftware.b4a.keywords.Common.True);
 }else {
 //BA.debugLineNum = 665;BA.debugLine="tabReporteHabitatRio.ScrollTo(7,True)";
mostCurrent._tabreportehabitatrio.ScrollTo((int) (7),anywheresoftware.b4a.keywords.Common.True);
 };
 }else if(mostCurrent._tabreportehabitatrio.getCurrentPage()==9) { 
 //BA.debugLineNum = 669;BA.debugLine="tabReporteHabitatRio.ScrollTo(tabReporteHabitatR";
mostCurrent._tabreportehabitatrio.ScrollTo((int) (mostCurrent._tabreportehabitatrio.getCurrentPage()+1),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 670;BA.debugLine="btnNext.Visible = False";
mostCurrent._btnnext.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 671;BA.debugLine="btnTerminar.Visible = True";
mostCurrent._btnterminar.setVisible(anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 674;BA.debugLine="End Sub";
return "";
}
public static String  _btnprev_click() throws Exception{
 //BA.debugLineNum = 379;BA.debugLine="Private Sub btnPrev_Click";
 //BA.debugLineNum = 380;BA.debugLine="If tabReporteHabitatRio.CurrentPage > 1 Then";
if (mostCurrent._tabreportehabitatrio.getCurrentPage()>1) { 
 //BA.debugLineNum = 381;BA.debugLine="tabReporteHabitatRio.ScrollTo(tabReporteHabitatR";
mostCurrent._tabreportehabitatrio.ScrollTo((int) (mostCurrent._tabreportehabitatrio.getCurrentPage()-1),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 382;BA.debugLine="btnNext.Visible = True";
mostCurrent._btnnext.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 383;BA.debugLine="btnPrev.Visible = True";
mostCurrent._btnprev.setVisible(anywheresoftware.b4a.keywords.Common.True);
 }else if(mostCurrent._tabreportehabitatrio.getCurrentPage()==1) { 
 //BA.debugLineNum = 385;BA.debugLine="tabReporteHabitatRio.ScrollTo(tabReporteHabitatR";
mostCurrent._tabreportehabitatrio.ScrollTo((int) (mostCurrent._tabreportehabitatrio.getCurrentPage()-1),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 386;BA.debugLine="btnPrev.Visible = False";
mostCurrent._btnprev.setVisible(anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 389;BA.debugLine="If tabReporteHabitatRio.CurrentPage = 6 Then";
if (mostCurrent._tabreportehabitatrio.getCurrentPage()==6) { 
 //BA.debugLineNum = 391;BA.debugLine="If haycampo = False Then";
if (_haycampo==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 392;BA.debugLine="tabReporteHabitatRio.ScrollTo(5,True)";
mostCurrent._tabreportehabitatrio.ScrollTo((int) (5),anywheresoftware.b4a.keywords.Common.True);
 };
 };
 //BA.debugLineNum = 395;BA.debugLine="If tabReporteHabitatRio.CurrentPage <> 9 Then";
if (mostCurrent._tabreportehabitatrio.getCurrentPage()!=9) { 
 //BA.debugLineNum = 396;BA.debugLine="btnTerminar.Visible =False";
mostCurrent._btnterminar.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 397;BA.debugLine="btnNext.Visible=True";
mostCurrent._btnnext.setVisible(anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 401;BA.debugLine="End Sub";
return "";
}
public static String  _btnterminar_click() throws Exception{
 //BA.debugLineNum = 685;BA.debugLine="Sub btnTerminar_Click";
 //BA.debugLineNum = 689;BA.debugLine="If chkQ8_2_a.Checked = True Then";
if (mostCurrent._chkq8_2_a.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 690;BA.debugLine="ind_pvm_9 = 0";
mostCurrent._ind_pvm_9 = BA.NumberToString(0);
 }else {
 //BA.debugLineNum = 692;BA.debugLine="ind_pvm_9 = 10";
mostCurrent._ind_pvm_9 = BA.NumberToString(10);
 };
 //BA.debugLineNum = 695;BA.debugLine="If chkQ8_2_b.Checked = True Then";
if (mostCurrent._chkq8_2_b.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 696;BA.debugLine="ind_pvm_10 = 0";
mostCurrent._ind_pvm_10 = BA.NumberToString(0);
 }else {
 //BA.debugLineNum = 698;BA.debugLine="ind_pvm_10 = 10";
mostCurrent._ind_pvm_10 = BA.NumberToString(10);
 };
 //BA.debugLineNum = 701;BA.debugLine="If chkQ8_2_c.Checked = True Then";
if (mostCurrent._chkq8_2_c.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 702;BA.debugLine="ind_pvm_11 = 0";
mostCurrent._ind_pvm_11 = BA.NumberToString(0);
 }else {
 //BA.debugLineNum = 704;BA.debugLine="ind_pvm_11 = 10";
mostCurrent._ind_pvm_11 = BA.NumberToString(10);
 };
 //BA.debugLineNum = 707;BA.debugLine="If chkQ8_2_d.Checked = True Then";
if (mostCurrent._chkq8_2_d.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 708;BA.debugLine="ind_pvm_12 = 0";
mostCurrent._ind_pvm_12 = BA.NumberToString(0);
 }else {
 //BA.debugLineNum = 710;BA.debugLine="ind_pvm_12 = 10";
mostCurrent._ind_pvm_12 = BA.NumberToString(10);
 };
 //BA.debugLineNum = 713;BA.debugLine="If chkQ8_2_e.Checked = True Then";
if (mostCurrent._chkq8_2_e.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 714;BA.debugLine="ind_pvm_13 = 0";
mostCurrent._ind_pvm_13 = BA.NumberToString(0);
 }else {
 //BA.debugLineNum = 716;BA.debugLine="ind_pvm_13 = 10";
mostCurrent._ind_pvm_13 = BA.NumberToString(10);
 };
 //BA.debugLineNum = 721;BA.debugLine="TerminarEval";
_terminareval();
 //BA.debugLineNum = 727;BA.debugLine="End Sub";
return "";
}
public static String  _chkq2_1_checkedchange(boolean _checked) throws Exception{
 //BA.debugLineNum = 1091;BA.debugLine="Private Sub chkQ2_1_CheckedChange(Checked As Boole";
 //BA.debugLineNum = 1093;BA.debugLine="If chkQ2_1.Checked = False Then";
if (mostCurrent._chkq2_1.getChecked()==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 1094;BA.debugLine="radQ2_1_a.Visible = True";
mostCurrent._radq2_1_a.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1095;BA.debugLine="radQ2_1_b.Visible = True";
mostCurrent._radq2_1_b.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1096;BA.debugLine="radQ2_1_c.Visible = True";
mostCurrent._radq2_1_c.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1097;BA.debugLine="radQ2_1_d.Visible = True";
mostCurrent._radq2_1_d.setVisible(anywheresoftware.b4a.keywords.Common.True);
 }else {
 //BA.debugLineNum = 1099;BA.debugLine="radQ2_1_a.Visible = False";
mostCurrent._radq2_1_a.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1100;BA.debugLine="radQ2_1_b.Visible = False";
mostCurrent._radq2_1_b.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1101;BA.debugLine="radQ2_1_c.Visible = False";
mostCurrent._radq2_1_c.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1102;BA.debugLine="radQ2_1_d.Visible = False";
mostCurrent._radq2_1_d.setVisible(anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 1105;BA.debugLine="End Sub";
return "";
}
public static String  _chkq3_1_checkedchange(boolean _checked) throws Exception{
 //BA.debugLineNum = 1132;BA.debugLine="Private Sub chkQ3_1_CheckedChange(Checked As Boole";
 //BA.debugLineNum = 1134;BA.debugLine="If chkQ3_1.Checked = True Then";
if (mostCurrent._chkq3_1.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 1135;BA.debugLine="chkQ3_2_a.Visible = True";
mostCurrent._chkq3_2_a.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1136;BA.debugLine="chkQ3_2_b.Visible = True";
mostCurrent._chkq3_2_b.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1137;BA.debugLine="chkQ3_2_c.Visible = True";
mostCurrent._chkq3_2_c.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1138;BA.debugLine="chkQ3_2_d.Visible = True";
mostCurrent._chkq3_2_d.setVisible(anywheresoftware.b4a.keywords.Common.True);
 }else {
 //BA.debugLineNum = 1140;BA.debugLine="If chkQ3_3.Checked = False Then";
if (mostCurrent._chkq3_3.getChecked()==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 1141;BA.debugLine="chkQ3_2_a.Visible = False";
mostCurrent._chkq3_2_a.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1142;BA.debugLine="chkQ3_2_b.Visible = False";
mostCurrent._chkq3_2_b.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1143;BA.debugLine="chkQ3_2_c.Visible = False";
mostCurrent._chkq3_2_c.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1144;BA.debugLine="chkQ3_2_d.Visible = False";
mostCurrent._chkq3_2_d.setVisible(anywheresoftware.b4a.keywords.Common.False);
 };
 };
 //BA.debugLineNum = 1147;BA.debugLine="End Sub";
return "";
}
public static String  _chkq3_3_checkedchange(boolean _checked) throws Exception{
 //BA.debugLineNum = 1115;BA.debugLine="Private Sub chkQ3_3_CheckedChange(Checked As Boole";
 //BA.debugLineNum = 1117;BA.debugLine="If chkQ3_3.Checked = True Then";
if (mostCurrent._chkq3_3.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 1118;BA.debugLine="chkQ3_2_a.Visible = True";
mostCurrent._chkq3_2_a.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1119;BA.debugLine="chkQ3_2_b.Visible = True";
mostCurrent._chkq3_2_b.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1120;BA.debugLine="chkQ3_2_c.Visible = True";
mostCurrent._chkq3_2_c.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1121;BA.debugLine="chkQ3_2_d.Visible = True";
mostCurrent._chkq3_2_d.setVisible(anywheresoftware.b4a.keywords.Common.True);
 }else {
 //BA.debugLineNum = 1123;BA.debugLine="If chkQ3_1.Checked = False Then";
if (mostCurrent._chkq3_1.getChecked()==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 1124;BA.debugLine="chkQ3_2_a.Visible = False";
mostCurrent._chkq3_2_a.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1125;BA.debugLine="chkQ3_2_b.Visible = False";
mostCurrent._chkq3_2_b.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1126;BA.debugLine="chkQ3_2_c.Visible = False";
mostCurrent._chkq3_2_c.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1127;BA.debugLine="chkQ3_2_d.Visible = False";
mostCurrent._chkq3_2_d.setVisible(anywheresoftware.b4a.keywords.Common.False);
 };
 };
 //BA.debugLineNum = 1130;BA.debugLine="End Sub";
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
 //BA.debugLineNum = 1047;BA.debugLine="Sub GenerarScreenshot";
 //BA.debugLineNum = 1049;BA.debugLine="Dim filename As String";
_filename = "";
 //BA.debugLineNum = 1050;BA.debugLine="filename = Main.username & \"_\" & Form_Reporte.cur";
_filename = mostCurrent._main._username /*String*/ +"_"+mostCurrent._form_reporte._currentproject /*String*/ ;
 //BA.debugLineNum = 1051;BA.debugLine="Dim Obj1, Obj2 As Reflector";
_obj1 = new anywheresoftware.b4a.agraham.reflection.Reflection();
_obj2 = new anywheresoftware.b4a.agraham.reflection.Reflection();
 //BA.debugLineNum = 1052;BA.debugLine="Dim bmp As Bitmap";
_bmp = new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper();
 //BA.debugLineNum = 1053;BA.debugLine="Dim c As Canvas";
_c = new anywheresoftware.b4a.objects.drawable.CanvasWrapper();
 //BA.debugLineNum = 1054;BA.debugLine="Dim now As Long";
_now = 0L;
 //BA.debugLineNum = 1055;BA.debugLine="DateTime.DateFormat = \"yyMMddHHmmss\"";
anywheresoftware.b4a.keywords.Common.DateTime.setDateFormat("yyMMddHHmmss");
 //BA.debugLineNum = 1056;BA.debugLine="now = DateTime.Now";
_now = anywheresoftware.b4a.keywords.Common.DateTime.getNow();
 //BA.debugLineNum = 1057;BA.debugLine="Obj1.Target = Obj1.GetActivityBA";
_obj1.Target = (Object)(_obj1.GetActivityBA(processBA));
 //BA.debugLineNum = 1058;BA.debugLine="Obj1.Target = Obj1.GetField(\"vg\")";
_obj1.Target = _obj1.GetField("vg");
 //BA.debugLineNum = 1059;BA.debugLine="bmp.InitializeMutable(Activity.Width, Activity.He";
_bmp.InitializeMutable(mostCurrent._activity.getWidth(),mostCurrent._activity.getHeight());
 //BA.debugLineNum = 1060;BA.debugLine="c.Initialize2(bmp)";
_c.Initialize2((android.graphics.Bitmap)(_bmp.getObject()));
 //BA.debugLineNum = 1061;BA.debugLine="Dim args(1) As Object";
_args = new Object[(int) (1)];
{
int d0 = _args.length;
for (int i0 = 0;i0 < d0;i0++) {
_args[i0] = new Object();
}
}
;
 //BA.debugLineNum = 1062;BA.debugLine="Dim types(1) As String";
_types = new String[(int) (1)];
java.util.Arrays.fill(_types,"");
 //BA.debugLineNum = 1063;BA.debugLine="Obj2.Target = c";
_obj2.Target = (Object)(_c);
 //BA.debugLineNum = 1064;BA.debugLine="Obj2.Target = Obj2.GetField(\"canvas\")";
_obj2.Target = _obj2.GetField("canvas");
 //BA.debugLineNum = 1065;BA.debugLine="args(0) = Obj2.Target";
_args[(int) (0)] = _obj2.Target;
 //BA.debugLineNum = 1066;BA.debugLine="types(0) = \"android.graphics.Canvas\"";
_types[(int) (0)] = "android.graphics.Canvas";
 //BA.debugLineNum = 1067;BA.debugLine="Obj1.RunMethod4(\"draw\", args, types)";
_obj1.RunMethod4("draw",_args,_types);
 //BA.debugLineNum = 1068;BA.debugLine="Dim out As OutputStream";
_out = new anywheresoftware.b4a.objects.streams.File.OutputStreamWrapper();
 //BA.debugLineNum = 1069;BA.debugLine="out = File.OpenOutput(Starter.savedir & \"/PreserV";
_out = anywheresoftware.b4a.keywords.Common.File.OpenOutput(mostCurrent._starter._savedir /*String*/ +"/PreserVamos/","Final-"+_filename+".png",anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1070;BA.debugLine="bmp.WriteToStream(out, 100, \"PNG\")";
_bmp.WriteToStream((java.io.OutputStream)(_out.getObject()),(int) (100),BA.getEnumFromString(android.graphics.Bitmap.CompressFormat.class,"PNG"));
 //BA.debugLineNum = 1071;BA.debugLine="out.Close";
_out.Close();
 //BA.debugLineNum = 1072;BA.debugLine="Main.screenshotpath = Starter.savedir & \"/PreserV";
mostCurrent._main._screenshotpath /*String*/  = mostCurrent._starter._savedir /*String*/ +"/PreserVamos/"+"Final-"+_filename+".png";
 //BA.debugLineNum = 1073;BA.debugLine="End Sub";
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
 //BA.debugLineNum = 121;BA.debugLine="Dim ind_pvm_1 As String";
mostCurrent._ind_pvm_1 = "";
 //BA.debugLineNum = 122;BA.debugLine="Dim ind_pvm_2 As String";
mostCurrent._ind_pvm_2 = "";
 //BA.debugLineNum = 123;BA.debugLine="Dim ind_pvm_3 As String";
mostCurrent._ind_pvm_3 = "";
 //BA.debugLineNum = 124;BA.debugLine="Dim ind_pvm_4 As String";
mostCurrent._ind_pvm_4 = "";
 //BA.debugLineNum = 125;BA.debugLine="Dim ind_pvm_5 As String";
mostCurrent._ind_pvm_5 = "";
 //BA.debugLineNum = 126;BA.debugLine="Dim ind_pvm_6 As String";
mostCurrent._ind_pvm_6 = "";
 //BA.debugLineNum = 127;BA.debugLine="Dim ind_pvm_7 As String";
mostCurrent._ind_pvm_7 = "";
 //BA.debugLineNum = 128;BA.debugLine="Dim ind_pvm_8 As String";
mostCurrent._ind_pvm_8 = "";
 //BA.debugLineNum = 129;BA.debugLine="Dim ind_pvm_9 As String";
mostCurrent._ind_pvm_9 = "";
 //BA.debugLineNum = 130;BA.debugLine="Dim ind_pvm_10 As String";
mostCurrent._ind_pvm_10 = "";
 //BA.debugLineNum = 131;BA.debugLine="Dim ind_pvm_11 As String";
mostCurrent._ind_pvm_11 = "";
 //BA.debugLineNum = 132;BA.debugLine="Dim ind_pvm_12 As String";
mostCurrent._ind_pvm_12 = "";
 //BA.debugLineNum = 133;BA.debugLine="Dim ind_pvm_13 As String";
mostCurrent._ind_pvm_13 = "";
 //BA.debugLineNum = 134;BA.debugLine="Dim preguntanumero As String";
mostCurrent._preguntanumero = "";
 //BA.debugLineNum = 135;BA.debugLine="Dim dateandtime As String";
mostCurrent._dateandtime = "";
 //BA.debugLineNum = 136;BA.debugLine="Dim valorcalidad As Int";
_valorcalidad = 0;
 //BA.debugLineNum = 137;BA.debugLine="Dim valorNS As Int";
_valorns = 0;
 //BA.debugLineNum = 138;BA.debugLine="Dim checklist As String";
mostCurrent._checklist = "";
 //BA.debugLineNum = 142;BA.debugLine="Private btnMasDetalle As Button";
mostCurrent._btnmasdetalle = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 143;BA.debugLine="Private Gauge1 As Gauge";
mostCurrent._gauge1 = new ilpla.appear.gauge();
 //BA.debugLineNum = 144;BA.debugLine="Private xui As XUI";
mostCurrent._xui = new anywheresoftware.b4a.objects.B4XViewWrapper.XUI();
 //BA.debugLineNum = 152;BA.debugLine="Private btnNext As Button";
mostCurrent._btnnext = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 153;BA.debugLine="Private btnPrev As Button";
mostCurrent._btnprev = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 154;BA.debugLine="Private lblPercentage As Label";
mostCurrent._lblpercentage = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 155;BA.debugLine="Private tabReporteHabitatRio As TabStrip";
mostCurrent._tabreportehabitatrio = new anywheresoftware.b4a.objects.TabStripViewPager();
 //BA.debugLineNum = 156;BA.debugLine="Private lblSection_Q1 As Label";
mostCurrent._lblsection_q1 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 157;BA.debugLine="Private pnlQ1_1 As Panel";
mostCurrent._pnlq1_1 = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 158;BA.debugLine="Private lblTitle_Q1_1 As Label";
mostCurrent._lbltitle_q1_1 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 159;BA.debugLine="Private btnHelp_Q1_1 As Button";
mostCurrent._btnhelp_q1_1 = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 160;BA.debugLine="Private radQ1_1_a As RadioButton";
mostCurrent._radq1_1_a = new anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper();
 //BA.debugLineNum = 161;BA.debugLine="Private radQ1_1_b As RadioButton";
mostCurrent._radq1_1_b = new anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper();
 //BA.debugLineNum = 162;BA.debugLine="Private radQ1_1_c As RadioButton";
mostCurrent._radq1_1_c = new anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper();
 //BA.debugLineNum = 163;BA.debugLine="Private pnlQ1_2 As Panel";
mostCurrent._pnlq1_2 = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 164;BA.debugLine="Private btnHelp_Q1_2 As Button";
mostCurrent._btnhelp_q1_2 = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 165;BA.debugLine="Private lblTitle_Q1_2 As Label";
mostCurrent._lbltitle_q1_2 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 166;BA.debugLine="Private radQ1_2_a As RadioButton";
mostCurrent._radq1_2_a = new anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper();
 //BA.debugLineNum = 167;BA.debugLine="Private radQ1_2_b As RadioButton";
mostCurrent._radq1_2_b = new anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper();
 //BA.debugLineNum = 168;BA.debugLine="Private radQ1_2_c As RadioButton";
mostCurrent._radq1_2_c = new anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper();
 //BA.debugLineNum = 169;BA.debugLine="Private radQ2_1_c As RadioButton";
mostCurrent._radq2_1_c = new anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper();
 //BA.debugLineNum = 170;BA.debugLine="Private lblSection_Q2 As Label";
mostCurrent._lblsection_q2 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 171;BA.debugLine="Private pnlQ2_1 As Panel";
mostCurrent._pnlq2_1 = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 172;BA.debugLine="Private btnHelp_Q2 As Button";
mostCurrent._btnhelp_q2 = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 173;BA.debugLine="Private chkQ2_4 As CheckBox";
mostCurrent._chkq2_4 = new anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper();
 //BA.debugLineNum = 174;BA.debugLine="Private chkQ2_3 As CheckBox";
mostCurrent._chkq2_3 = new anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper();
 //BA.debugLineNum = 175;BA.debugLine="Private chkQ2_1 As CheckBox";
mostCurrent._chkq2_1 = new anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper();
 //BA.debugLineNum = 176;BA.debugLine="Private radQ2_1_a As RadioButton";
mostCurrent._radq2_1_a = new anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper();
 //BA.debugLineNum = 177;BA.debugLine="Private radQ2_1_b As RadioButton";
mostCurrent._radq2_1_b = new anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper();
 //BA.debugLineNum = 178;BA.debugLine="Private radQ3_1_c As RadioButton";
mostCurrent._radq3_1_c = new anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper();
 //BA.debugLineNum = 179;BA.debugLine="Private lblSection_Q3 As Label";
mostCurrent._lblsection_q3 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 180;BA.debugLine="Private pnlQ3_1 As Panel";
mostCurrent._pnlq3_1 = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 181;BA.debugLine="Private btnHelp_Q3 As Button";
mostCurrent._btnhelp_q3 = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 182;BA.debugLine="Private chkQ3_3 As CheckBox";
mostCurrent._chkq3_3 = new anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper();
 //BA.debugLineNum = 183;BA.debugLine="Private chkQ3_2 As CheckBox";
mostCurrent._chkq3_2 = new anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper();
 //BA.debugLineNum = 184;BA.debugLine="Private chkQ3_1 As CheckBox";
mostCurrent._chkq3_1 = new anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper();
 //BA.debugLineNum = 185;BA.debugLine="Private radQ3_1_a As RadioButton";
mostCurrent._radq3_1_a = new anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper();
 //BA.debugLineNum = 186;BA.debugLine="Private radQ3_1_b As RadioButton";
mostCurrent._radq3_1_b = new anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper();
 //BA.debugLineNum = 187;BA.debugLine="Private lblSection_Q4 As Label";
mostCurrent._lblsection_q4 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 188;BA.debugLine="Private pnlQ4_1 As Panel";
mostCurrent._pnlq4_1 = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 189;BA.debugLine="Private chkQ4_1_c As CheckBox";
mostCurrent._chkq4_1_c = new anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper();
 //BA.debugLineNum = 190;BA.debugLine="Private chkQ4_1_b As CheckBox";
mostCurrent._chkq4_1_b = new anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper();
 //BA.debugLineNum = 191;BA.debugLine="Private chkQ4_1_a As CheckBox";
mostCurrent._chkq4_1_a = new anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper();
 //BA.debugLineNum = 192;BA.debugLine="Private radQ4_2_a As RadioButton";
mostCurrent._radq4_2_a = new anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper();
 //BA.debugLineNum = 193;BA.debugLine="Private radQ4_2_b As RadioButton";
mostCurrent._radq4_2_b = new anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper();
 //BA.debugLineNum = 194;BA.debugLine="Private radQ4_2_c As RadioButton";
mostCurrent._radq4_2_c = new anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper();
 //BA.debugLineNum = 195;BA.debugLine="Private lblTitle_Q4_1 As Label";
mostCurrent._lbltitle_q4_1 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 196;BA.debugLine="Private btnHelp_Q4_1 As Button";
mostCurrent._btnhelp_q4_1 = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 197;BA.debugLine="Private chkQ4_1_d As CheckBox";
mostCurrent._chkq4_1_d = new anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper();
 //BA.debugLineNum = 198;BA.debugLine="Private chkQ4_1_e As CheckBox";
mostCurrent._chkq4_1_e = new anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper();
 //BA.debugLineNum = 199;BA.debugLine="Private lblTitle_Q4_2 As Label";
mostCurrent._lbltitle_q4_2 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 200;BA.debugLine="Private btnHelp_Q4_2 As Button";
mostCurrent._btnhelp_q4_2 = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 201;BA.debugLine="Private radQ4_2_d As RadioButton";
mostCurrent._radq4_2_d = new anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper();
 //BA.debugLineNum = 202;BA.debugLine="Private lblTitle_Q4_3 As Label";
mostCurrent._lbltitle_q4_3 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 203;BA.debugLine="Private radQ4_3_a As RadioButton";
mostCurrent._radq4_3_a = new anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper();
 //BA.debugLineNum = 204;BA.debugLine="Private radQ4_3_c As RadioButton";
mostCurrent._radq4_3_c = new anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper();
 //BA.debugLineNum = 205;BA.debugLine="Private radQ4_3_d As RadioButton";
mostCurrent._radq4_3_d = new anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper();
 //BA.debugLineNum = 206;BA.debugLine="Private btnHelp_Q4_3 As Button";
mostCurrent._btnhelp_q4_3 = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 207;BA.debugLine="Private radQ4_3_b As RadioButton";
mostCurrent._radq4_3_b = new anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper();
 //BA.debugLineNum = 208;BA.debugLine="Private lblSection_Q5 As Label";
mostCurrent._lblsection_q5 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 209;BA.debugLine="Private pnlQ5_1 As Panel";
mostCurrent._pnlq5_1 = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 210;BA.debugLine="Private lblTitle_Q5_1 As Label";
mostCurrent._lbltitle_q5_1 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 211;BA.debugLine="Private btnHelp_Q5_1 As Button";
mostCurrent._btnhelp_q5_1 = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 212;BA.debugLine="Private radQ5_1_a As RadioButton";
mostCurrent._radq5_1_a = new anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper();
 //BA.debugLineNum = 213;BA.debugLine="Private radQ5_1_b As RadioButton";
mostCurrent._radq5_1_b = new anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper();
 //BA.debugLineNum = 214;BA.debugLine="Private radQ5_1_c As RadioButton";
mostCurrent._radq5_1_c = new anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper();
 //BA.debugLineNum = 215;BA.debugLine="Private radQ5_1_d As RadioButton";
mostCurrent._radq5_1_d = new anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper();
 //BA.debugLineNum = 216;BA.debugLine="Private radQ5_1_e As RadioButton";
mostCurrent._radq5_1_e = new anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper();
 //BA.debugLineNum = 217;BA.debugLine="Private radQ5_1_f As RadioButton";
mostCurrent._radq5_1_f = new anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper();
 //BA.debugLineNum = 218;BA.debugLine="Private radQ5_1_g As RadioButton";
mostCurrent._radq5_1_g = new anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper();
 //BA.debugLineNum = 219;BA.debugLine="Private radQ5_1_h As RadioButton";
mostCurrent._radq5_1_h = new anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper();
 //BA.debugLineNum = 220;BA.debugLine="Private radQ5_1_i As RadioButton";
mostCurrent._radq5_1_i = new anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper();
 //BA.debugLineNum = 221;BA.debugLine="Private lblSection_Q5p2 As Label";
mostCurrent._lblsection_q5p2 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 222;BA.debugLine="Private pnlQ5p2_1 As Panel";
mostCurrent._pnlq5p2_1 = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 223;BA.debugLine="Private lblTitle_Q5p2_1 As Label";
mostCurrent._lbltitle_q5p2_1 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 224;BA.debugLine="Private btnHelp_Q5p2_1 As Button";
mostCurrent._btnhelp_q5p2_1 = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 225;BA.debugLine="Private radQ5p2_1_a As RadioButton";
mostCurrent._radq5p2_1_a = new anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper();
 //BA.debugLineNum = 226;BA.debugLine="Private radQ5p2_1_b As RadioButton";
mostCurrent._radq5p2_1_b = new anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper();
 //BA.debugLineNum = 227;BA.debugLine="Private radQ5p2_1_c As RadioButton";
mostCurrent._radq5p2_1_c = new anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper();
 //BA.debugLineNum = 228;BA.debugLine="Private radQ5p2_1_d As RadioButton";
mostCurrent._radq5p2_1_d = new anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper();
 //BA.debugLineNum = 229;BA.debugLine="Private lblTitle_Q5p2_2 As Label";
mostCurrent._lbltitle_q5p2_2 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 230;BA.debugLine="Private btnHelp_Q5p2_2 As Button";
mostCurrent._btnhelp_q5p2_2 = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 231;BA.debugLine="Private radQ5p2_2_a As RadioButton";
mostCurrent._radq5p2_2_a = new anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper();
 //BA.debugLineNum = 232;BA.debugLine="Private radQ5p2_2_b As RadioButton";
mostCurrent._radq5p2_2_b = new anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper();
 //BA.debugLineNum = 233;BA.debugLine="Private radQ5p2_2_c As RadioButton";
mostCurrent._radq5p2_2_c = new anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper();
 //BA.debugLineNum = 234;BA.debugLine="Private radQ5p2_2_d As RadioButton";
mostCurrent._radq5p2_2_d = new anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper();
 //BA.debugLineNum = 235;BA.debugLine="Private lblSection_Q6 As Label";
mostCurrent._lblsection_q6 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 236;BA.debugLine="Private pnlQ6_1 As Panel";
mostCurrent._pnlq6_1 = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 237;BA.debugLine="Private chkQ6_1_c As CheckBox";
mostCurrent._chkq6_1_c = new anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper();
 //BA.debugLineNum = 238;BA.debugLine="Private chkQ6_1_b As CheckBox";
mostCurrent._chkq6_1_b = new anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper();
 //BA.debugLineNum = 239;BA.debugLine="Private chkQ6_1_a As CheckBox";
mostCurrent._chkq6_1_a = new anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper();
 //BA.debugLineNum = 240;BA.debugLine="Private lblTitle_Q6_1 As Label";
mostCurrent._lbltitle_q6_1 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 241;BA.debugLine="Private btnHelp_Q6_1 As Button";
mostCurrent._btnhelp_q6_1 = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 242;BA.debugLine="Private chkQ6_1_d As CheckBox";
mostCurrent._chkq6_1_d = new anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper();
 //BA.debugLineNum = 243;BA.debugLine="Private chkQ6_1_e As CheckBox";
mostCurrent._chkq6_1_e = new anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper();
 //BA.debugLineNum = 244;BA.debugLine="Private lblTitle_Q6_2 As Label";
mostCurrent._lbltitle_q6_2 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 245;BA.debugLine="Private btnHelp_Q6_2 As Button";
mostCurrent._btnhelp_q6_2 = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 246;BA.debugLine="Private radQ6_2_a As RadioButton";
mostCurrent._radq6_2_a = new anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper();
 //BA.debugLineNum = 247;BA.debugLine="Private radQ6_2_c As RadioButton";
mostCurrent._radq6_2_c = new anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper();
 //BA.debugLineNum = 248;BA.debugLine="Private radQ6_2_d As RadioButton";
mostCurrent._radq6_2_d = new anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper();
 //BA.debugLineNum = 249;BA.debugLine="Private radQ6_2_b As RadioButton";
mostCurrent._radq6_2_b = new anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper();
 //BA.debugLineNum = 250;BA.debugLine="Private lblTitle_Q6_3 As Label";
mostCurrent._lbltitle_q6_3 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 251;BA.debugLine="Private radQ6_3_a As RadioButton";
mostCurrent._radq6_3_a = new anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper();
 //BA.debugLineNum = 252;BA.debugLine="Private radQ6_3_c As RadioButton";
mostCurrent._radq6_3_c = new anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper();
 //BA.debugLineNum = 253;BA.debugLine="Private radQ6_3_d As RadioButton";
mostCurrent._radq6_3_d = new anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper();
 //BA.debugLineNum = 254;BA.debugLine="Private btnHelp_Q6_3 As Button";
mostCurrent._btnhelp_q6_3 = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 255;BA.debugLine="Private radQ6_3_b As RadioButton";
mostCurrent._radq6_3_b = new anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper();
 //BA.debugLineNum = 256;BA.debugLine="Private lblSection_Q7 As Label";
mostCurrent._lblsection_q7 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 257;BA.debugLine="Private pnlQ7_1 As Panel";
mostCurrent._pnlq7_1 = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 258;BA.debugLine="Private chkQ7_1_c As CheckBox";
mostCurrent._chkq7_1_c = new anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper();
 //BA.debugLineNum = 259;BA.debugLine="Private chkQ7_1_b As CheckBox";
mostCurrent._chkq7_1_b = new anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper();
 //BA.debugLineNum = 260;BA.debugLine="Private chkQ7_1_a As CheckBox";
mostCurrent._chkq7_1_a = new anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper();
 //BA.debugLineNum = 261;BA.debugLine="Private lblTitle_Q7_1 As Label";
mostCurrent._lbltitle_q7_1 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 262;BA.debugLine="Private btnHelp_Q7_1 As Button";
mostCurrent._btnhelp_q7_1 = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 263;BA.debugLine="Private chkQ7_1_d As CheckBox";
mostCurrent._chkq7_1_d = new anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper();
 //BA.debugLineNum = 264;BA.debugLine="Private lblTitle_Q7_2 As Label";
mostCurrent._lbltitle_q7_2 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 265;BA.debugLine="Private btnHelp_Q7_2 As Button";
mostCurrent._btnhelp_q7_2 = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 266;BA.debugLine="Private chkQ7_2_a As CheckBox";
mostCurrent._chkq7_2_a = new anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper();
 //BA.debugLineNum = 267;BA.debugLine="Private chkQ7_2_b As CheckBox";
mostCurrent._chkq7_2_b = new anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper();
 //BA.debugLineNum = 268;BA.debugLine="Private chkQ7_2_c As CheckBox";
mostCurrent._chkq7_2_c = new anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper();
 //BA.debugLineNum = 269;BA.debugLine="Private chkQ7_2_d As CheckBox";
mostCurrent._chkq7_2_d = new anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper();
 //BA.debugLineNum = 270;BA.debugLine="Private radQ2_1_d As RadioButton";
mostCurrent._radq2_1_d = new anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper();
 //BA.debugLineNum = 271;BA.debugLine="Private chkQ4_1_f As CheckBox";
mostCurrent._chkq4_1_f = new anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper();
 //BA.debugLineNum = 272;BA.debugLine="Private lblSection_Q As Label";
mostCurrent._lblsection_q = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 273;BA.debugLine="Private btnHelp_Q8_1 As Button";
mostCurrent._btnhelp_q8_1 = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 274;BA.debugLine="Private pnlQ8_1 As Panel";
mostCurrent._pnlq8_1 = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 275;BA.debugLine="Private chkQ8_1_c As CheckBox";
mostCurrent._chkq8_1_c = new anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper();
 //BA.debugLineNum = 276;BA.debugLine="Private chkQ8_1_b As CheckBox";
mostCurrent._chkq8_1_b = new anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper();
 //BA.debugLineNum = 277;BA.debugLine="Private chkQ8_1_a As CheckBox";
mostCurrent._chkq8_1_a = new anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper();
 //BA.debugLineNum = 278;BA.debugLine="Private lblTitle_Q8_1 As Label";
mostCurrent._lbltitle_q8_1 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 279;BA.debugLine="Private chkQ8_1_d As CheckBox";
mostCurrent._chkq8_1_d = new anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper();
 //BA.debugLineNum = 280;BA.debugLine="Private chkQ8_1_e As CheckBox";
mostCurrent._chkq8_1_e = new anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper();
 //BA.debugLineNum = 282;BA.debugLine="Dim haycampo As Boolean";
_haycampo = false;
 //BA.debugLineNum = 286;BA.debugLine="Private lblSection_Q8 As Label";
mostCurrent._lblsection_q8 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 287;BA.debugLine="Private pnlQ8_2 As Panel";
mostCurrent._pnlq8_2 = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 288;BA.debugLine="Private lblTitle_Q8_2 As Label";
mostCurrent._lbltitle_q8_2 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 289;BA.debugLine="Private chkQ8_2_a As CheckBox";
mostCurrent._chkq8_2_a = new anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper();
 //BA.debugLineNum = 290;BA.debugLine="Private chkQ8_2_b As CheckBox";
mostCurrent._chkq8_2_b = new anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper();
 //BA.debugLineNum = 291;BA.debugLine="Private chkQ8_2_c As CheckBox";
mostCurrent._chkq8_2_c = new anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper();
 //BA.debugLineNum = 292;BA.debugLine="Private chkQ8_2_d As CheckBox";
mostCurrent._chkq8_2_d = new anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper();
 //BA.debugLineNum = 293;BA.debugLine="Private chkQ8_2_e As CheckBox";
mostCurrent._chkq8_2_e = new anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper();
 //BA.debugLineNum = 294;BA.debugLine="Private chkQ3_2_a As CheckBox";
mostCurrent._chkq3_2_a = new anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper();
 //BA.debugLineNum = 295;BA.debugLine="Private chkQ3_2_b As CheckBox";
mostCurrent._chkq3_2_b = new anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper();
 //BA.debugLineNum = 296;BA.debugLine="Private chkQ3_2_c As CheckBox";
mostCurrent._chkq3_2_c = new anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper();
 //BA.debugLineNum = 297;BA.debugLine="Private chkQ3_2_d As CheckBox";
mostCurrent._chkq3_2_d = new anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper();
 //BA.debugLineNum = 298;BA.debugLine="End Sub";
return "";
}
public static String  _guardareval() throws Exception{
anywheresoftware.b4a.objects.collections.Map _map1 = null;
 //BA.debugLineNum = 976;BA.debugLine="Sub GuardarEval";
 //BA.debugLineNum = 978;BA.debugLine="Dim Map1 As Map";
_map1 = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 979;BA.debugLine="Map1.Initialize";
_map1.Initialize();
 //BA.debugLineNum = 980;BA.debugLine="Map1.Put(\"Id\", Form_Reporte.currentproject)";
_map1.Put((Object)("Id"),(Object)(mostCurrent._form_reporte._currentproject /*String*/ ));
 //BA.debugLineNum = 981;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"evals\", \"val";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals","valorind1",(Object)(mostCurrent._valorind1),_map1);
 //BA.debugLineNum = 982;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"evals\", \"val";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals","valorind2",(Object)(mostCurrent._valorind2),_map1);
 //BA.debugLineNum = 983;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"evals\", \"val";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals","valorind3",(Object)(mostCurrent._valorind3),_map1);
 //BA.debugLineNum = 984;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"evals\", \"val";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals","valorind4",(Object)(mostCurrent._valorind4),_map1);
 //BA.debugLineNum = 985;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"evals\", \"val";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals","valorind5",(Object)(mostCurrent._valorind5),_map1);
 //BA.debugLineNum = 986;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"evals\", \"val";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals","valorind6",(Object)(mostCurrent._valorind6),_map1);
 //BA.debugLineNum = 987;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"evals\", \"val";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals","valorind7",(Object)(mostCurrent._valorind7),_map1);
 //BA.debugLineNum = 988;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"evals\", \"val";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals","valorind8",(Object)(mostCurrent._valorind8),_map1);
 //BA.debugLineNum = 989;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"evals\", \"val";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals","valorind9",(Object)(mostCurrent._valorind9),_map1);
 //BA.debugLineNum = 990;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"evals\", \"val";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals","valorind10",(Object)(mostCurrent._valorind10),_map1);
 //BA.debugLineNum = 991;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"evals\", \"val";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals","valorind11",(Object)(mostCurrent._valorind11),_map1);
 //BA.debugLineNum = 992;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"evals\", \"val";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals","valorind12",(Object)(mostCurrent._valorind12),_map1);
 //BA.debugLineNum = 993;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"evals\", \"val";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals","valorind13",(Object)(mostCurrent._valorind13),_map1);
 //BA.debugLineNum = 994;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"evals\", \"val";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals","valorind14",(Object)(mostCurrent._valorind14),_map1);
 //BA.debugLineNum = 995;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"evals\", \"val";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals","valorind15",(Object)(mostCurrent._valorind15),_map1);
 //BA.debugLineNum = 996;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"evals\", \"val";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals","valorind16",(Object)(mostCurrent._valorind16),_map1);
 //BA.debugLineNum = 997;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"evals\", \"bin";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals","bingo",(Object)(mostCurrent._checklist),_map1);
 //BA.debugLineNum = 998;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"evals\", \"ind";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals","ind_pvm_1",(Object)(mostCurrent._ind_pvm_1),_map1);
 //BA.debugLineNum = 999;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"evals\", \"ind";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals","ind_pvm_2",(Object)(mostCurrent._ind_pvm_2),_map1);
 //BA.debugLineNum = 1000;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"evals\", \"ind";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals","ind_pvm_3",(Object)(mostCurrent._ind_pvm_3),_map1);
 //BA.debugLineNum = 1001;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"evals\", \"ind";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals","ind_pvm_4",(Object)(mostCurrent._ind_pvm_4),_map1);
 //BA.debugLineNum = 1002;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"evals\", \"ind";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals","ind_pvm_5",(Object)(mostCurrent._ind_pvm_5),_map1);
 //BA.debugLineNum = 1003;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"evals\", \"ind";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals","ind_pvm_6",(Object)(mostCurrent._ind_pvm_6),_map1);
 //BA.debugLineNum = 1004;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"evals\", \"ind";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals","ind_pvm_7",(Object)(mostCurrent._ind_pvm_7),_map1);
 //BA.debugLineNum = 1005;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"evals\", \"ind";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals","ind_pvm_8",(Object)(mostCurrent._ind_pvm_8),_map1);
 //BA.debugLineNum = 1006;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"evals\", \"ind";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals","ind_pvm_9",(Object)(mostCurrent._ind_pvm_9),_map1);
 //BA.debugLineNum = 1007;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"evals\", \"ind";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals","ind_pvm_10",(Object)(mostCurrent._ind_pvm_10),_map1);
 //BA.debugLineNum = 1008;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"evals\", \"ind";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals","ind_pvm_11",(Object)(mostCurrent._ind_pvm_11),_map1);
 //BA.debugLineNum = 1009;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"evals\", \"ind";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals","ind_pvm_12",(Object)(mostCurrent._ind_pvm_12),_map1);
 //BA.debugLineNum = 1010;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"evals\", \"ind";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals","ind_pvm_13",(Object)(mostCurrent._ind_pvm_13),_map1);
 //BA.debugLineNum = 1013;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"evals\", \"val";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals","valorind20",(Object)(_valorns),_map1);
 //BA.debugLineNum = 1014;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"evals\", \"val";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals","valorcalidad",(Object)(_valorcalidad),_map1);
 //BA.debugLineNum = 1015;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"evals\", \"geo";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals","georeferencedDate",(Object)(mostCurrent._dateandtime),_map1);
 //BA.debugLineNum = 1017;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"evals\", \"ter";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals","terminado",(Object)("si"),_map1);
 //BA.debugLineNum = 1019;BA.debugLine="End Sub";
return "";
}
public static String  _pnlresultados_click() throws Exception{
 //BA.debugLineNum = 1036;BA.debugLine="Sub pnlResultados_Click";
 //BA.debugLineNum = 1037;BA.debugLine="Return True";
if (true) return BA.ObjectToString(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1038;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 7;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 9;BA.debugLine="Dim tiporio As String";
_tiporio = "";
 //BA.debugLineNum = 11;BA.debugLine="End Sub";
return "";
}
public static String  _tabreportehabitatrio_pageselected(int _position) throws Exception{
 //BA.debugLineNum = 371;BA.debugLine="Private Sub tabReporteHabitatRio_PageSelected (Pos";
 //BA.debugLineNum = 376;BA.debugLine="End Sub";
return "";
}
public static String  _terminareval() throws Exception{
int _totpreg = 0;
 //BA.debugLineNum = 728;BA.debugLine="Sub TerminarEval";
 //BA.debugLineNum = 729;BA.debugLine="Log(\"valorind1:\" & valorind1)";
anywheresoftware.b4a.keywords.Common.LogImpl("230736385","valorind1:"+mostCurrent._valorind1,0);
 //BA.debugLineNum = 730;BA.debugLine="Log(\"valorind2:\" &valorind2)";
anywheresoftware.b4a.keywords.Common.LogImpl("230736386","valorind2:"+mostCurrent._valorind2,0);
 //BA.debugLineNum = 731;BA.debugLine="Log(\"valorind3:\" &valorind3)";
anywheresoftware.b4a.keywords.Common.LogImpl("230736387","valorind3:"+mostCurrent._valorind3,0);
 //BA.debugLineNum = 732;BA.debugLine="Log(\"valorind4:\" &valorind4)";
anywheresoftware.b4a.keywords.Common.LogImpl("230736388","valorind4:"+mostCurrent._valorind4,0);
 //BA.debugLineNum = 733;BA.debugLine="Log(\"valorind5:\" &valorind5)";
anywheresoftware.b4a.keywords.Common.LogImpl("230736389","valorind5:"+mostCurrent._valorind5,0);
 //BA.debugLineNum = 734;BA.debugLine="Log(\"valorind6:\" &valorind6)";
anywheresoftware.b4a.keywords.Common.LogImpl("230736390","valorind6:"+mostCurrent._valorind6,0);
 //BA.debugLineNum = 735;BA.debugLine="Log(\"valorind7:\" &valorind7)";
anywheresoftware.b4a.keywords.Common.LogImpl("230736391","valorind7:"+mostCurrent._valorind7,0);
 //BA.debugLineNum = 736;BA.debugLine="Log(\"valorind8:\" &valorind8)";
anywheresoftware.b4a.keywords.Common.LogImpl("230736392","valorind8:"+mostCurrent._valorind8,0);
 //BA.debugLineNum = 737;BA.debugLine="Log(\"valorind9:\" &valorind9)";
anywheresoftware.b4a.keywords.Common.LogImpl("230736393","valorind9:"+mostCurrent._valorind9,0);
 //BA.debugLineNum = 738;BA.debugLine="Log(\"valorind10:\" &valorind10)";
anywheresoftware.b4a.keywords.Common.LogImpl("230736394","valorind10:"+mostCurrent._valorind10,0);
 //BA.debugLineNum = 739;BA.debugLine="Log(\"valorind11:\" &valorind11)";
anywheresoftware.b4a.keywords.Common.LogImpl("230736395","valorind11:"+mostCurrent._valorind11,0);
 //BA.debugLineNum = 740;BA.debugLine="Log(\"valorind12:\" &valorind12)";
anywheresoftware.b4a.keywords.Common.LogImpl("230736396","valorind12:"+mostCurrent._valorind12,0);
 //BA.debugLineNum = 741;BA.debugLine="Log(\"valorind13:\" &valorind13)";
anywheresoftware.b4a.keywords.Common.LogImpl("230736397","valorind13:"+mostCurrent._valorind13,0);
 //BA.debugLineNum = 742;BA.debugLine="Log(\"valorind14:\" &valorind14)";
anywheresoftware.b4a.keywords.Common.LogImpl("230736398","valorind14:"+mostCurrent._valorind14,0);
 //BA.debugLineNum = 743;BA.debugLine="Log(\"valorind15:\" &valorind15)";
anywheresoftware.b4a.keywords.Common.LogImpl("230736399","valorind15:"+mostCurrent._valorind15,0);
 //BA.debugLineNum = 744;BA.debugLine="Log(\"valorind16:\" &valorind16)";
anywheresoftware.b4a.keywords.Common.LogImpl("230736400","valorind16:"+mostCurrent._valorind16,0);
 //BA.debugLineNum = 745;BA.debugLine="Log(\"checklist:\" &checklist)";
anywheresoftware.b4a.keywords.Common.LogImpl("230736401","checklist:"+mostCurrent._checklist,0);
 //BA.debugLineNum = 746;BA.debugLine="Log(\"ind_pvm_1:\" &ind_pvm_1)";
anywheresoftware.b4a.keywords.Common.LogImpl("230736402","ind_pvm_1:"+mostCurrent._ind_pvm_1,0);
 //BA.debugLineNum = 747;BA.debugLine="Log(\"ind_pvm_2:\" &ind_pvm_2)";
anywheresoftware.b4a.keywords.Common.LogImpl("230736403","ind_pvm_2:"+mostCurrent._ind_pvm_2,0);
 //BA.debugLineNum = 748;BA.debugLine="Log(\"ind_pvm_3:\" &ind_pvm_3)";
anywheresoftware.b4a.keywords.Common.LogImpl("230736404","ind_pvm_3:"+mostCurrent._ind_pvm_3,0);
 //BA.debugLineNum = 749;BA.debugLine="Log(\"ind_pvm_4:\" &ind_pvm_4)";
anywheresoftware.b4a.keywords.Common.LogImpl("230736405","ind_pvm_4:"+mostCurrent._ind_pvm_4,0);
 //BA.debugLineNum = 750;BA.debugLine="Log(\"ind_pvm_5:\" &ind_pvm_5)";
anywheresoftware.b4a.keywords.Common.LogImpl("230736406","ind_pvm_5:"+mostCurrent._ind_pvm_5,0);
 //BA.debugLineNum = 751;BA.debugLine="Log(\"ind_pvm_6:\" &ind_pvm_6)";
anywheresoftware.b4a.keywords.Common.LogImpl("230736407","ind_pvm_6:"+mostCurrent._ind_pvm_6,0);
 //BA.debugLineNum = 752;BA.debugLine="Log(\"ind_pvm_7:\" &ind_pvm_7)";
anywheresoftware.b4a.keywords.Common.LogImpl("230736408","ind_pvm_7:"+mostCurrent._ind_pvm_7,0);
 //BA.debugLineNum = 753;BA.debugLine="Log(\"ind_pvm_8:\" &ind_pvm_8)";
anywheresoftware.b4a.keywords.Common.LogImpl("230736409","ind_pvm_8:"+mostCurrent._ind_pvm_8,0);
 //BA.debugLineNum = 754;BA.debugLine="Log(\"ind_pvm_9:\" &ind_pvm_9)";
anywheresoftware.b4a.keywords.Common.LogImpl("230736410","ind_pvm_9:"+mostCurrent._ind_pvm_9,0);
 //BA.debugLineNum = 755;BA.debugLine="Log(\"ind_pvm_10:\" &ind_pvm_10)";
anywheresoftware.b4a.keywords.Common.LogImpl("230736411","ind_pvm_10:"+mostCurrent._ind_pvm_10,0);
 //BA.debugLineNum = 756;BA.debugLine="Log(\"ind_pvm_11:\" &ind_pvm_11)";
anywheresoftware.b4a.keywords.Common.LogImpl("230736412","ind_pvm_11:"+mostCurrent._ind_pvm_11,0);
 //BA.debugLineNum = 757;BA.debugLine="Log(\"ind_pvm_12:\" &ind_pvm_12)";
anywheresoftware.b4a.keywords.Common.LogImpl("230736413","ind_pvm_12:"+mostCurrent._ind_pvm_12,0);
 //BA.debugLineNum = 758;BA.debugLine="Log(\"ind_pvm_13:\" &ind_pvm_13)";
anywheresoftware.b4a.keywords.Common.LogImpl("230736414","ind_pvm_13:"+mostCurrent._ind_pvm_13,0);
 //BA.debugLineNum = 760;BA.debugLine="Log(\"dateandtime:\" &dateandtime)";
anywheresoftware.b4a.keywords.Common.LogImpl("230736416","dateandtime:"+mostCurrent._dateandtime,0);
 //BA.debugLineNum = 764;BA.debugLine="pnlResultados.Visible = True";
mostCurrent._pnlresultados.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 765;BA.debugLine="pnlResultados.BringToFront";
mostCurrent._pnlresultados.BringToFront();
 //BA.debugLineNum = 767;BA.debugLine="Dim totpreg As Int";
_totpreg = 0;
 //BA.debugLineNum = 768;BA.debugLine="totpreg = 150";
_totpreg = (int) (150);
 //BA.debugLineNum = 770;BA.debugLine="If haycampo = False Then";
if (_haycampo==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 771;BA.debugLine="totpreg = 12";
_totpreg = (int) (12);
 }else {
 //BA.debugLineNum = 773;BA.debugLine="totpreg = 15";
_totpreg = (int) (15);
 };
 //BA.debugLineNum = 775;BA.debugLine="valorcalidad = 0";
_valorcalidad = (int) (0);
 //BA.debugLineNum = 776;BA.debugLine="If valorind1 <> \"NS\" And valorind1 <> \"\" Then";
if ((mostCurrent._valorind1).equals("NS") == false && (mostCurrent._valorind1).equals("") == false) { 
 //BA.debugLineNum = 777;BA.debugLine="valorind1  = Round2(((valorind1 * 100) / totpr";
mostCurrent._valorind1 = BA.NumberToString(anywheresoftware.b4a.keywords.Common.Round2((((double)(Double.parseDouble(mostCurrent._valorind1))*100)/(double)_totpreg),(int) (1)));
 //BA.debugLineNum = 778;BA.debugLine="valorcalidad = valorcalidad + valorind1";
_valorcalidad = (int) (_valorcalidad+(double)(Double.parseDouble(mostCurrent._valorind1)));
 }else {
 //BA.debugLineNum = 780;BA.debugLine="valorNS = valorNS + 1";
_valorns = (int) (_valorns+1);
 };
 //BA.debugLineNum = 782;BA.debugLine="If valorind2 <> \"NS\" And valorind2 <> \"\" Then";
if ((mostCurrent._valorind2).equals("NS") == false && (mostCurrent._valorind2).equals("") == false) { 
 //BA.debugLineNum = 783;BA.debugLine="valorind2  = Round2(((valorind2 * 100) / totpr";
mostCurrent._valorind2 = BA.NumberToString(anywheresoftware.b4a.keywords.Common.Round2((((double)(Double.parseDouble(mostCurrent._valorind2))*100)/(double)_totpreg),(int) (1)));
 //BA.debugLineNum = 784;BA.debugLine="valorcalidad = valorcalidad + valorind2";
_valorcalidad = (int) (_valorcalidad+(double)(Double.parseDouble(mostCurrent._valorind2)));
 }else {
 //BA.debugLineNum = 786;BA.debugLine="valorNS = valorNS + 1";
_valorns = (int) (_valorns+1);
 };
 //BA.debugLineNum = 788;BA.debugLine="If valorind3 <> \"NS\" And valorind3 <> \"\" Then";
if ((mostCurrent._valorind3).equals("NS") == false && (mostCurrent._valorind3).equals("") == false) { 
 //BA.debugLineNum = 789;BA.debugLine="valorind3  = Round2(((valorind3 * 100) / totpr";
mostCurrent._valorind3 = BA.NumberToString(anywheresoftware.b4a.keywords.Common.Round2((((double)(Double.parseDouble(mostCurrent._valorind3))*100)/(double)_totpreg),(int) (1)));
 //BA.debugLineNum = 790;BA.debugLine="valorcalidad = valorcalidad + valorind3";
_valorcalidad = (int) (_valorcalidad+(double)(Double.parseDouble(mostCurrent._valorind3)));
 }else {
 //BA.debugLineNum = 792;BA.debugLine="valorNS = valorNS + 1";
_valorns = (int) (_valorns+1);
 };
 //BA.debugLineNum = 794;BA.debugLine="If valorind4 <> \"NS\"  And valorind4 <> \"\" Then";
if ((mostCurrent._valorind4).equals("NS") == false && (mostCurrent._valorind4).equals("") == false) { 
 //BA.debugLineNum = 795;BA.debugLine="valorind4  = Round2(((valorind4 * 100) / totpr";
mostCurrent._valorind4 = BA.NumberToString(anywheresoftware.b4a.keywords.Common.Round2((((double)(Double.parseDouble(mostCurrent._valorind4))*100)/(double)_totpreg),(int) (1)));
 //BA.debugLineNum = 796;BA.debugLine="valorcalidad = valorcalidad + valorind4";
_valorcalidad = (int) (_valorcalidad+(double)(Double.parseDouble(mostCurrent._valorind4)));
 }else {
 //BA.debugLineNum = 798;BA.debugLine="valorNS = valorNS + 1";
_valorns = (int) (_valorns+1);
 };
 //BA.debugLineNum = 800;BA.debugLine="If valorind5 <> \"NS\"  And valorind5 <> \"\" Then";
if ((mostCurrent._valorind5).equals("NS") == false && (mostCurrent._valorind5).equals("") == false) { 
 //BA.debugLineNum = 801;BA.debugLine="valorind5  = Round2(((valorind5 * 100) / totpr";
mostCurrent._valorind5 = BA.NumberToString(anywheresoftware.b4a.keywords.Common.Round2((((double)(Double.parseDouble(mostCurrent._valorind5))*100)/(double)_totpreg),(int) (1)));
 //BA.debugLineNum = 802;BA.debugLine="valorcalidad = valorcalidad + valorind5";
_valorcalidad = (int) (_valorcalidad+(double)(Double.parseDouble(mostCurrent._valorind5)));
 }else {
 //BA.debugLineNum = 804;BA.debugLine="valorNS = valorNS + 1";
_valorns = (int) (_valorns+1);
 };
 //BA.debugLineNum = 806;BA.debugLine="If valorind6 <> \"NS\"  And valorind6 <> \"\" Then";
if ((mostCurrent._valorind6).equals("NS") == false && (mostCurrent._valorind6).equals("") == false) { 
 //BA.debugLineNum = 807;BA.debugLine="valorind6  = Round2(((valorind6 * 100) / totpr";
mostCurrent._valorind6 = BA.NumberToString(anywheresoftware.b4a.keywords.Common.Round2((((double)(Double.parseDouble(mostCurrent._valorind6))*100)/(double)_totpreg),(int) (1)));
 //BA.debugLineNum = 808;BA.debugLine="valorcalidad = valorcalidad + valorind6";
_valorcalidad = (int) (_valorcalidad+(double)(Double.parseDouble(mostCurrent._valorind6)));
 }else {
 //BA.debugLineNum = 810;BA.debugLine="valorNS = valorNS + 1";
_valorns = (int) (_valorns+1);
 };
 //BA.debugLineNum = 812;BA.debugLine="If valorind7 <> \"NS\"  And valorind7 <> \"\" Then";
if ((mostCurrent._valorind7).equals("NS") == false && (mostCurrent._valorind7).equals("") == false) { 
 //BA.debugLineNum = 813;BA.debugLine="valorind7  = Round2(((valorind7 * 100) / totpr";
mostCurrent._valorind7 = BA.NumberToString(anywheresoftware.b4a.keywords.Common.Round2((((double)(Double.parseDouble(mostCurrent._valorind7))*100)/(double)_totpreg),(int) (1)));
 //BA.debugLineNum = 814;BA.debugLine="valorcalidad = valorcalidad + valorind7";
_valorcalidad = (int) (_valorcalidad+(double)(Double.parseDouble(mostCurrent._valorind7)));
 }else {
 //BA.debugLineNum = 816;BA.debugLine="valorNS = valorNS + 1";
_valorns = (int) (_valorns+1);
 };
 //BA.debugLineNum = 824;BA.debugLine="If valorind9 <> \"NS\"  And valorind9 <> \"\" Then";
if ((mostCurrent._valorind9).equals("NS") == false && (mostCurrent._valorind9).equals("") == false) { 
 //BA.debugLineNum = 825;BA.debugLine="valorind9  = Round2(((valorind9 * 100) / totpr";
mostCurrent._valorind9 = BA.NumberToString(anywheresoftware.b4a.keywords.Common.Round2((((double)(Double.parseDouble(mostCurrent._valorind9))*100)/(double)_totpreg),(int) (1)));
 //BA.debugLineNum = 826;BA.debugLine="valorcalidad = valorcalidad + valorind9";
_valorcalidad = (int) (_valorcalidad+(double)(Double.parseDouble(mostCurrent._valorind9)));
 }else {
 //BA.debugLineNum = 828;BA.debugLine="valorNS = valorNS + 1";
_valorns = (int) (_valorns+1);
 };
 //BA.debugLineNum = 830;BA.debugLine="If valorind10 <> \"NS\"  And valorind10 <> \"\" Then";
if ((mostCurrent._valorind10).equals("NS") == false && (mostCurrent._valorind10).equals("") == false) { 
 //BA.debugLineNum = 831;BA.debugLine="valorind10  = Round2(((valorind10 * 100) / tot";
mostCurrent._valorind10 = BA.NumberToString(anywheresoftware.b4a.keywords.Common.Round2((((double)(Double.parseDouble(mostCurrent._valorind10))*100)/(double)_totpreg),(int) (1)));
 //BA.debugLineNum = 832;BA.debugLine="valorcalidad = valorcalidad + valorind10";
_valorcalidad = (int) (_valorcalidad+(double)(Double.parseDouble(mostCurrent._valorind10)));
 }else {
 //BA.debugLineNum = 834;BA.debugLine="valorNS = valorNS + 1";
_valorns = (int) (_valorns+1);
 };
 //BA.debugLineNum = 837;BA.debugLine="If ind_pvm_1 <> \"NS\"  And ind_pvm_1 <> \"\" Then";
if ((mostCurrent._ind_pvm_1).equals("NS") == false && (mostCurrent._ind_pvm_1).equals("") == false) { 
 //BA.debugLineNum = 838;BA.debugLine="ind_pvm_1  = Round2(((ind_pvm_1 * 100) / totpreg";
mostCurrent._ind_pvm_1 = BA.NumberToString(anywheresoftware.b4a.keywords.Common.Round2((((double)(Double.parseDouble(mostCurrent._ind_pvm_1))*100)/(double)_totpreg),(int) (1)));
 //BA.debugLineNum = 839;BA.debugLine="valorcalidad = valorcalidad + ind_pvm_1";
_valorcalidad = (int) (_valorcalidad+(double)(Double.parseDouble(mostCurrent._ind_pvm_1)));
 }else {
 //BA.debugLineNum = 841;BA.debugLine="valorNS = valorNS + 1";
_valorns = (int) (_valorns+1);
 };
 //BA.debugLineNum = 844;BA.debugLine="If ind_pvm_2 <> \"NS\"  And ind_pvm_2 <> \"\" Then";
if ((mostCurrent._ind_pvm_2).equals("NS") == false && (mostCurrent._ind_pvm_2).equals("") == false) { 
 //BA.debugLineNum = 845;BA.debugLine="ind_pvm_2  = Round2(((ind_pvm_2 * 100) / totpreg";
mostCurrent._ind_pvm_2 = BA.NumberToString(anywheresoftware.b4a.keywords.Common.Round2((((double)(Double.parseDouble(mostCurrent._ind_pvm_2))*100)/(double)_totpreg),(int) (1)));
 //BA.debugLineNum = 846;BA.debugLine="valorcalidad = valorcalidad + ind_pvm_2";
_valorcalidad = (int) (_valorcalidad+(double)(Double.parseDouble(mostCurrent._ind_pvm_2)));
 }else {
 //BA.debugLineNum = 848;BA.debugLine="valorNS = valorNS + 1";
_valorns = (int) (_valorns+1);
 };
 //BA.debugLineNum = 851;BA.debugLine="If ind_pvm_3 <> \"NS\"  And ind_pvm_3 <> \"\" Then";
if ((mostCurrent._ind_pvm_3).equals("NS") == false && (mostCurrent._ind_pvm_3).equals("") == false) { 
 //BA.debugLineNum = 852;BA.debugLine="ind_pvm_3  = Round2(((ind_pvm_3 * 100) / totpreg";
mostCurrent._ind_pvm_3 = BA.NumberToString(anywheresoftware.b4a.keywords.Common.Round2((((double)(Double.parseDouble(mostCurrent._ind_pvm_3))*100)/(double)_totpreg),(int) (1)));
 //BA.debugLineNum = 853;BA.debugLine="valorcalidad = valorcalidad + ind_pvm_3";
_valorcalidad = (int) (_valorcalidad+(double)(Double.parseDouble(mostCurrent._ind_pvm_3)));
 }else {
 //BA.debugLineNum = 855;BA.debugLine="valorNS = valorNS + 1";
_valorns = (int) (_valorns+1);
 };
 //BA.debugLineNum = 857;BA.debugLine="If ind_pvm_4 <> \"NS\"  And ind_pvm_4 <> \"\" Then";
if ((mostCurrent._ind_pvm_4).equals("NS") == false && (mostCurrent._ind_pvm_4).equals("") == false) { 
 //BA.debugLineNum = 858;BA.debugLine="ind_pvm_4  = Round2(((ind_pvm_4 * 100) / totpreg";
mostCurrent._ind_pvm_4 = BA.NumberToString(anywheresoftware.b4a.keywords.Common.Round2((((double)(Double.parseDouble(mostCurrent._ind_pvm_4))*100)/(double)_totpreg),(int) (1)));
 //BA.debugLineNum = 859;BA.debugLine="valorcalidad = valorcalidad + ind_pvm_4";
_valorcalidad = (int) (_valorcalidad+(double)(Double.parseDouble(mostCurrent._ind_pvm_4)));
 }else {
 //BA.debugLineNum = 861;BA.debugLine="valorNS = valorNS + 1";
_valorns = (int) (_valorns+1);
 };
 //BA.debugLineNum = 865;BA.debugLine="If ind_pvm_9 <> \"NS\"  And ind_pvm_9 <> \"\" Then";
if ((mostCurrent._ind_pvm_9).equals("NS") == false && (mostCurrent._ind_pvm_9).equals("") == false) { 
 //BA.debugLineNum = 866;BA.debugLine="ind_pvm_9  = Round2(((ind_pvm_9 * 100) / totpreg";
mostCurrent._ind_pvm_9 = BA.NumberToString(anywheresoftware.b4a.keywords.Common.Round2((((double)(Double.parseDouble(mostCurrent._ind_pvm_9))*100)/(double)_totpreg),(int) (1)));
 //BA.debugLineNum = 867;BA.debugLine="valorcalidad = valorcalidad + ind_pvm_9";
_valorcalidad = (int) (_valorcalidad+(double)(Double.parseDouble(mostCurrent._ind_pvm_9)));
 }else {
 //BA.debugLineNum = 869;BA.debugLine="valorNS = valorNS + 1";
_valorns = (int) (_valorns+1);
 };
 //BA.debugLineNum = 872;BA.debugLine="If ind_pvm_10 <> \"NS\"  And ind_pvm_10 <> \"\" Then";
if ((mostCurrent._ind_pvm_10).equals("NS") == false && (mostCurrent._ind_pvm_10).equals("") == false) { 
 //BA.debugLineNum = 873;BA.debugLine="ind_pvm_10  = Round2(((ind_pvm_10 * 100) / totpr";
mostCurrent._ind_pvm_10 = BA.NumberToString(anywheresoftware.b4a.keywords.Common.Round2((((double)(Double.parseDouble(mostCurrent._ind_pvm_10))*100)/(double)_totpreg),(int) (1)));
 //BA.debugLineNum = 874;BA.debugLine="valorcalidad = valorcalidad + ind_pvm_10";
_valorcalidad = (int) (_valorcalidad+(double)(Double.parseDouble(mostCurrent._ind_pvm_10)));
 }else {
 //BA.debugLineNum = 876;BA.debugLine="valorNS = valorNS + 1";
_valorns = (int) (_valorns+1);
 };
 //BA.debugLineNum = 879;BA.debugLine="If ind_pvm_11 <> \"NS\"  And ind_pvm_11 <> \"\" Then";
if ((mostCurrent._ind_pvm_11).equals("NS") == false && (mostCurrent._ind_pvm_11).equals("") == false) { 
 //BA.debugLineNum = 880;BA.debugLine="ind_pvm_11  = Round2(((ind_pvm_11 * 100) / totpr";
mostCurrent._ind_pvm_11 = BA.NumberToString(anywheresoftware.b4a.keywords.Common.Round2((((double)(Double.parseDouble(mostCurrent._ind_pvm_11))*100)/(double)_totpreg),(int) (1)));
 //BA.debugLineNum = 881;BA.debugLine="valorcalidad = valorcalidad + ind_pvm_11";
_valorcalidad = (int) (_valorcalidad+(double)(Double.parseDouble(mostCurrent._ind_pvm_11)));
 }else {
 //BA.debugLineNum = 883;BA.debugLine="valorNS = valorNS + 1";
_valorns = (int) (_valorns+1);
 };
 //BA.debugLineNum = 886;BA.debugLine="If ind_pvm_12 <> \"NS\"  And ind_pvm_12 <> \"\" Then";
if ((mostCurrent._ind_pvm_12).equals("NS") == false && (mostCurrent._ind_pvm_12).equals("") == false) { 
 //BA.debugLineNum = 887;BA.debugLine="ind_pvm_12  = Round2(((ind_pvm_12 * 100) / totpr";
mostCurrent._ind_pvm_12 = BA.NumberToString(anywheresoftware.b4a.keywords.Common.Round2((((double)(Double.parseDouble(mostCurrent._ind_pvm_12))*100)/(double)_totpreg),(int) (1)));
 //BA.debugLineNum = 888;BA.debugLine="valorcalidad = valorcalidad + ind_pvm_12";
_valorcalidad = (int) (_valorcalidad+(double)(Double.parseDouble(mostCurrent._ind_pvm_12)));
 }else {
 //BA.debugLineNum = 890;BA.debugLine="valorNS = valorNS + 1";
_valorns = (int) (_valorns+1);
 };
 //BA.debugLineNum = 893;BA.debugLine="If ind_pvm_13 <> \"NS\"  And ind_pvm_13 <> \"\" Then";
if ((mostCurrent._ind_pvm_13).equals("NS") == false && (mostCurrent._ind_pvm_13).equals("") == false) { 
 //BA.debugLineNum = 894;BA.debugLine="ind_pvm_13  = Round2(((ind_pvm_13 * 100) / totpr";
mostCurrent._ind_pvm_13 = BA.NumberToString(anywheresoftware.b4a.keywords.Common.Round2((((double)(Double.parseDouble(mostCurrent._ind_pvm_13))*100)/(double)_totpreg),(int) (1)));
 //BA.debugLineNum = 895;BA.debugLine="valorcalidad = valorcalidad + ind_pvm_13";
_valorcalidad = (int) (_valorcalidad+(double)(Double.parseDouble(mostCurrent._ind_pvm_13)));
 }else {
 //BA.debugLineNum = 897;BA.debugLine="valorNS = valorNS + 1";
_valorns = (int) (_valorns+1);
 };
 //BA.debugLineNum = 912;BA.debugLine="If tiporio = \"montana\" And valorind13 <> \"NS\" Th";
if ((_tiporio).equals("montana") && (mostCurrent._valorind13).equals("NS") == false) { 
 //BA.debugLineNum = 913;BA.debugLine="valorind13  = Round2(((valorind13 * 100) / tot";
mostCurrent._valorind13 = BA.NumberToString(anywheresoftware.b4a.keywords.Common.Round2((((double)(Double.parseDouble(mostCurrent._valorind13))*100)/(double)_totpreg),(int) (1)));
 //BA.debugLineNum = 914;BA.debugLine="valorcalidad = valorcalidad + valorind13";
_valorcalidad = (int) (_valorcalidad+(double)(Double.parseDouble(mostCurrent._valorind13)));
 }else {
 //BA.debugLineNum = 916;BA.debugLine="valorNS = valorNS + 1";
_valorns = (int) (_valorns+1);
 };
 //BA.debugLineNum = 921;BA.debugLine="If valorNS > 7  Then";
if (_valorns>7) { 
 //BA.debugLineNum = 922;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 923;BA.debugLine="MsgboxAsync(\"Ha salteado muchas opciones en la";
anywheresoftware.b4a.keywords.Common.MsgboxAsync(BA.ObjectToCharSequence("Ha salteado muchas opciones en la encuesta, el estado del hábitat no puede ser calculado con precisión"),BA.ObjectToCharSequence("Oops!"),processBA);
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 925;BA.debugLine="MsgboxAsync(\"You have skipped too many questio";
anywheresoftware.b4a.keywords.Common.MsgboxAsync(BA.ObjectToCharSequence("You have skipped too many questions, the habitat status cannot be calculated accurately"),BA.ObjectToCharSequence("Oops!"),processBA);
 };
 //BA.debugLineNum = 927;BA.debugLine="pnlResultados.Visible = False";
mostCurrent._pnlresultados.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 928;BA.debugLine="Return True";
if (true) return BA.ObjectToString(anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 931;BA.debugLine="valorNS = Round2((valorNS * 100) / (totpreg/10)";
_valorns = (int) (anywheresoftware.b4a.keywords.Common.Round2((_valorns*100)/(double)(_totpreg/(double)10),(int) (1)));
 //BA.debugLineNum = 933;BA.debugLine="valorcalidad = Round2(valorcalidad,1)";
_valorcalidad = (int) (anywheresoftware.b4a.keywords.Common.Round2(_valorcalidad,(int) (1)));
 //BA.debugLineNum = 937;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 938;BA.debugLine="If valorcalidad < 20 Then";
if (_valorcalidad<20) { 
 //BA.debugLineNum = 939;BA.debugLine="lblEstado.Text = \"Muy malo\"";
mostCurrent._lblestado.setText(BA.ObjectToCharSequence("Muy malo"));
 }else if(_valorcalidad>=20 && _valorcalidad<40) { 
 //BA.debugLineNum = 941;BA.debugLine="lblEstado.Text = \"Malo\"";
mostCurrent._lblestado.setText(BA.ObjectToCharSequence("Malo"));
 }else if(_valorcalidad>=40 && _valorcalidad<60) { 
 //BA.debugLineNum = 943;BA.debugLine="lblEstado.Text = \"Regular\"";
mostCurrent._lblestado.setText(BA.ObjectToCharSequence("Regular"));
 }else if(_valorcalidad>=60 && _valorcalidad<80) { 
 //BA.debugLineNum = 945;BA.debugLine="lblEstado.Text = \"Bueno\"";
mostCurrent._lblestado.setText(BA.ObjectToCharSequence("Bueno"));
 }else if(_valorcalidad>=80) { 
 //BA.debugLineNum = 947;BA.debugLine="lblEstado.Text = \"Muy bueno!\"";
mostCurrent._lblestado.setText(BA.ObjectToCharSequence("Muy bueno!"));
 };
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 950;BA.debugLine="If valorcalidad < 20 Then";
if (_valorcalidad<20) { 
 //BA.debugLineNum = 951;BA.debugLine="lblEstado.Text = \"Very bad\"";
mostCurrent._lblestado.setText(BA.ObjectToCharSequence("Very bad"));
 }else if(_valorcalidad>=20 && _valorcalidad<40) { 
 //BA.debugLineNum = 953;BA.debugLine="lblEstado.Text = \"Bad\"";
mostCurrent._lblestado.setText(BA.ObjectToCharSequence("Bad"));
 }else if(_valorcalidad>=40 && _valorcalidad<60) { 
 //BA.debugLineNum = 955;BA.debugLine="lblEstado.Text = \"Regular\"";
mostCurrent._lblestado.setText(BA.ObjectToCharSequence("Regular"));
 }else if(_valorcalidad>=60 && _valorcalidad<80) { 
 //BA.debugLineNum = 957;BA.debugLine="lblEstado.Text = \"Good\"";
mostCurrent._lblestado.setText(BA.ObjectToCharSequence("Good"));
 }else if(_valorcalidad>=80) { 
 //BA.debugLineNum = 959;BA.debugLine="lblEstado.Text = \"Very good!\"";
mostCurrent._lblestado.setText(BA.ObjectToCharSequence("Very good!"));
 };
 };
 //BA.debugLineNum = 963;BA.debugLine="Log(\"valorNS:\" &valorNS)";
anywheresoftware.b4a.keywords.Common.LogImpl("230736619","valorNS:"+BA.NumberToString(_valorns),0);
 //BA.debugLineNum = 964;BA.debugLine="Log(\"valorcalidad:\" &valorcalidad)";
anywheresoftware.b4a.keywords.Common.LogImpl("230736620","valorcalidad:"+BA.NumberToString(_valorcalidad),0);
 //BA.debugLineNum = 967;BA.debugLine="Gauge1.SetRanges(Array(Gauge1.CreateRange(0, 20,";
mostCurrent._gauge1._setranges /*String*/ (anywheresoftware.b4a.keywords.Common.ArrayToList(new Object[]{(Object)(mostCurrent._gauge1._createrange /*ilpla.appear.gauge._gaugerange*/ ((float) (0),(float) (20),mostCurrent._xui.Color_DarkGray)),(Object)(mostCurrent._gauge1._createrange /*ilpla.appear.gauge._gaugerange*/ ((float) (20),(float) (40),mostCurrent._xui.Color_Red)),(Object)(mostCurrent._gauge1._createrange /*ilpla.appear.gauge._gaugerange*/ ((float) (40),(float) (60),mostCurrent._xui.Color_Yellow)),(Object)(mostCurrent._gauge1._createrange /*ilpla.appear.gauge._gaugerange*/ ((float) (60),(float) (80),mostCurrent._xui.Color_Green)),(Object)(mostCurrent._gauge1._createrange /*ilpla.appear.gauge._gaugerange*/ ((float) (80),(float) (100),mostCurrent._xui.Color_Blue))}));
 //BA.debugLineNum = 968;BA.debugLine="Gauge1.CurrentValue = 0";
mostCurrent._gauge1._setcurrentvalue /*float*/ ((float) (0));
 //BA.debugLineNum = 969;BA.debugLine="Gauge1.CurrentValue = valorcalidad";
mostCurrent._gauge1._setcurrentvalue /*float*/ ((float) (_valorcalidad));
 //BA.debugLineNum = 974;BA.debugLine="End Sub";
return "";
}
}
