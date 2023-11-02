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

public class reporte_habitat_laguna extends Activity implements B4AActivity{
	public static reporte_habitat_laguna mostCurrent;
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
			processBA = new BA(this.getApplicationContext(), null, null, "appear.pnud.preservamos", "appear.pnud.preservamos.reporte_habitat_laguna");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
            
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (reporte_habitat_laguna).");
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
		activityBA = new BA(this, layout, processBA, "appear.pnud.preservamos", "appear.pnud.preservamos.reporte_habitat_laguna");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "appear.pnud.preservamos.reporte_habitat_laguna", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density, mostCurrent);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (reporte_habitat_laguna) Create " + (isFirst ? "(first time)" : "") + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (reporte_habitat_laguna) Resume **");
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
		return reporte_habitat_laguna.class;
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
            BA.LogInfo("** Activity (reporte_habitat_laguna) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
        else
            BA.LogInfo("** Activity (reporte_habitat_laguna) Pause event (activity is not paused). **");
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
            reporte_habitat_laguna mc = mostCurrent;
			if (mc == null || mc != activity.get())
				return;
			processBA.setActivityPaused(false);
            BA.LogInfo("** Activity (reporte_habitat_laguna) Resume **");
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
public anywheresoftware.b4a.objects.PanelWrapper _pnlimagen = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imgfondo = null;
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
public anywheresoftware.b4a.objects.ButtonWrapper _btnpregunta11 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnpregunta12 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnpregunta13 = null;
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
public anywheresoftware.b4a.objects.LabelWrapper _lblpregunta11 = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblpregunta12 = null;
public static int _currentpregunta = 0;
public anywheresoftware.b4a.objects.LabelWrapper _lblestado = null;
public anywheresoftware.b4a.objects.PanelWrapper _pnlresultados = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnterminar = null;
public anywheresoftware.b4a.objects.ScrollViewWrapper _scrresultados = null;
public anywheresoftware.b4a.objects.PanelWrapper _panel1 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btncerrar = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbltituloresultados = null;
public anywheresoftware.b4a.objects.LabelWrapper _label2 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnshare = null;
public anywheresoftware.b4a.objects.LabelWrapper _fondogris = null;
public anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper _img1 = null;
public magnifizewrapper.magnifizeWrapper _mag1 = null;
public static String _currentscreen = "";
public anywheresoftware.b4a.objects.ImageViewWrapper _imgq2_1_c = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imgq2_1_b = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imgq2_1_a = null;
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
public static double _valorcalidad = 0;
public static int _valorns = 0;
public static String _checklist = "";
public static String _notas = "";
public anywheresoftware.b4a.objects.ButtonWrapper _btnmasdetalle = null;
public appear.pnud.preservamos.gauge _gauge1 = null;
public anywheresoftware.b4a.objects.B4XViewWrapper.XUI _xui = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnnext = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnprev = null;
public anywheresoftware.b4a.objects.TabStripViewPager _tabreportehabitatrio = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblsection_q1 = null;
public anywheresoftware.b4a.objects.PanelWrapper _pnlq1_1 = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbltitle_q1_1 = null;
public anywheresoftware.b4a.objects.PanelWrapper _pnlq1_2 = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblsection_q2 = null;
public anywheresoftware.b4a.objects.PanelWrapper _pnlq2_1 = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblsection_q3 = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblsection_q4 = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbltitle_q4_2 = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblsection_q5 = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblsection_q5p2 = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblsection_q6 = null;
public anywheresoftware.b4a.objects.PanelWrapper _pnlq6_1 = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbltitle_q6_1 = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbltitle_q6_3 = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblsection_q7 = null;
public static boolean _haycampo = false;
public static boolean _desbordado = false;
public static boolean _haybasura = false;
public anywheresoftware.b4a.objects.ImageViewWrapper _imgq1_1_a = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imgq1_1_b = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imgq1_1_c = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblrojo_q1 = null;
public anywheresoftware.b4a.objects.EditTextWrapper _txtnotas = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imgq1_2_a = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imgq1_2_b = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imgq1_2_c = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblrojo_q1p2 = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblrojo_q2 = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imgq2_1_si = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imgq2_1_no = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblrojo_q2p2 = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblrojo_q2p3 = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imgq2_3_si = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imgq2_3_no = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblrojo_q2p4 = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imgq2_4_si = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imgq2_4_no = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imgq3_a = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imgq3_b = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblrojo_q3 = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblrojo_q3p2 = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imgq3p2_a = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imgq3p2_b = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblrojo_q3p3_a = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblrojo_q3p3_b = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblrojo_q3p3_c = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblrojo_q3p3_d = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imgq3p3_a = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imgq3p3_b = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imgq3p3_c = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imgq3p3_d = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imgq4_2_a = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imgq4_2_b = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imgq4_2_c = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imgq4_2_d = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblrojo_q4_2 = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblrojo_q4p2 = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imgq4p2_a = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imgq4p2_b = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imgq4p2_c = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imgq4p2_d = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imgq5_1_j = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imgq5_1_i = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imgq5_1_g = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imgq5_1_h = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imgq5_1_f = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imgq5_1_e = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imgq5_1_c = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imgq5_1_d = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imgq5_1_b = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imgq5_1_a = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblrojo_q5_1 = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblrojo_q5p2 = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imgq5p2_a = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imgq5p2_b = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imgq5p2_c = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblrojo_q5p3 = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imgq5p3_b = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imgq5p3_a = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imgq5p3_c = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imgq5p3_d = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblrojo_q6_a = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblrojo_q6_c = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblrojo_q6_e = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblrojo_q6_d = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblrojo_q6_b = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imgq6_a = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imgq6_b = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imgq6_c = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imgq6_d = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imgq6_e = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imgq6_1_a = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imgq6_1_b = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imgq6_1_c = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imgq6_1_d = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblrojo_q6_1 = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imgq6_3_a = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imgq6_3_b = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imgq6_3_c = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imgq6_3_d = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblrojo_q6_3 = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblrojo_q4_a = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblrojo_q4_b = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblrojo_q4_c = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblrojo_q4_d = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblrojo_q4_e = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imgq4_a = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imgq4_b = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imgq4_c = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imgq4_d = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imgq4_e = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblrojo_q4_f = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblrojo_q7_a = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblrojo_q7_c = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblrojo_q7_d = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblrojo_q7_b = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imgq7_a = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imgq7_b = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imgq7_c = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imgq7_d = null;
public anywheresoftware.b4a.objects.ScrollViewWrapper _scrollusos = null;
public anywheresoftware.b4a.objects.ScrollViewWrapper _scrollanimales = null;
public anywheresoftware.b4a.objects.ScrollViewWrapper _scrollvegetacion = null;
public anywheresoftware.b4a.objects.ScrollViewWrapper _scrollexoticas1 = null;
public anywheresoftware.b4a.objects.ScrollViewWrapper _scrollexoticas2 = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imgq6_3 = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imgq6_2 = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imgespumas = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imgautopartes = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imgbasura = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imgplantasacuaticas = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imguso = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imguso_campo1 = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imguso_campo2 = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imganimales_patos = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imganimales_peces = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imganimales_roedores = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imganimales_tortugas = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imgjuncos = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imgarboles = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imgarbustos = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imgbasuraribera = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imgligustro = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imgpastolargo = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imgbasuraribera_grande = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imgpasto_corto = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imgentubado = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imgembarcaciones = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imgbaño = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imgacacia = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imgkayak = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imgpesca = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imgrio = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imgrio_desbordado = null;
public anywheresoftware.b4a.objects.LabelWrapper _label1 = null;
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
public appear.pnud.preservamos.inatcheck _inatcheck = null;
public appear.pnud.preservamos.mod_hidro _mod_hidro = null;
public appear.pnud.preservamos.mod_hidro_fotos _mod_hidro_fotos = null;
public appear.pnud.preservamos.mod_residuos _mod_residuos = null;
public appear.pnud.preservamos.mod_residuos_fotos _mod_residuos_fotos = null;
public appear.pnud.preservamos.register _register = null;
public appear.pnud.preservamos.reporte_envio _reporte_envio = null;
public appear.pnud.preservamos.reporte_fotos _reporte_fotos = null;
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
 //BA.debugLineNum = 294;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
 //BA.debugLineNum = 295;BA.debugLine="Activity.LoadLayout(\"Laguna_Main_Large\")";
mostCurrent._activity.LoadLayout("Laguna_Main_Large",mostCurrent.activityBA);
 //BA.debugLineNum = 298;BA.debugLine="preguntanumero = 2";
mostCurrent._preguntanumero = BA.NumberToString(2);
 //BA.debugLineNum = 299;BA.debugLine="valorind1 = \"\"";
mostCurrent._valorind1 = "";
 //BA.debugLineNum = 300;BA.debugLine="valorind2 = \"\"";
mostCurrent._valorind2 = "";
 //BA.debugLineNum = 301;BA.debugLine="valorind3 = \"\"";
mostCurrent._valorind3 = "";
 //BA.debugLineNum = 302;BA.debugLine="valorind4 = \"\"";
mostCurrent._valorind4 = "";
 //BA.debugLineNum = 303;BA.debugLine="valorind5 = \"\"";
mostCurrent._valorind5 = "";
 //BA.debugLineNum = 304;BA.debugLine="valorind6 = \"\"";
mostCurrent._valorind6 = "";
 //BA.debugLineNum = 305;BA.debugLine="valorind7 = \"\"";
mostCurrent._valorind7 = "";
 //BA.debugLineNum = 306;BA.debugLine="valorind8 = \"\"";
mostCurrent._valorind8 = "";
 //BA.debugLineNum = 307;BA.debugLine="valorind9 = \"\"";
mostCurrent._valorind9 = "";
 //BA.debugLineNum = 308;BA.debugLine="valorind10 = \"\"";
mostCurrent._valorind10 = "";
 //BA.debugLineNum = 309;BA.debugLine="valorind11 = \"\"";
mostCurrent._valorind11 = "";
 //BA.debugLineNum = 310;BA.debugLine="valorind12 = \"\"";
mostCurrent._valorind12 = "";
 //BA.debugLineNum = 311;BA.debugLine="valorind13 = \"\"";
mostCurrent._valorind13 = "";
 //BA.debugLineNum = 312;BA.debugLine="valorind14 = \"\"";
mostCurrent._valorind14 = "";
 //BA.debugLineNum = 313;BA.debugLine="valorind15 = \"\"";
mostCurrent._valorind15 = "";
 //BA.debugLineNum = 314;BA.debugLine="valorind16 = \"\"";
mostCurrent._valorind16 = "";
 //BA.debugLineNum = 315;BA.debugLine="ind_pvm_1  = \"\"";
mostCurrent._ind_pvm_1 = "";
 //BA.debugLineNum = 316;BA.debugLine="ind_pvm_2  = \"\"";
mostCurrent._ind_pvm_2 = "";
 //BA.debugLineNum = 317;BA.debugLine="ind_pvm_3  = \"\"";
mostCurrent._ind_pvm_3 = "";
 //BA.debugLineNum = 318;BA.debugLine="ind_pvm_4  = \"\"";
mostCurrent._ind_pvm_4 = "";
 //BA.debugLineNum = 319;BA.debugLine="ind_pvm_5  = \"\"";
mostCurrent._ind_pvm_5 = "";
 //BA.debugLineNum = 320;BA.debugLine="ind_pvm_6  = \"\"";
mostCurrent._ind_pvm_6 = "";
 //BA.debugLineNum = 321;BA.debugLine="ind_pvm_7  = \"\"";
mostCurrent._ind_pvm_7 = "";
 //BA.debugLineNum = 322;BA.debugLine="ind_pvm_8  = \"\"";
mostCurrent._ind_pvm_8 = "";
 //BA.debugLineNum = 323;BA.debugLine="ind_pvm_9  = \"\"";
mostCurrent._ind_pvm_9 = "";
 //BA.debugLineNum = 324;BA.debugLine="ind_pvm_10  = \"\"";
mostCurrent._ind_pvm_10 = "";
 //BA.debugLineNum = 325;BA.debugLine="ind_pvm_11  = \"\"";
mostCurrent._ind_pvm_11 = "";
 //BA.debugLineNum = 326;BA.debugLine="ind_pvm_12  = \"\"";
mostCurrent._ind_pvm_12 = "";
 //BA.debugLineNum = 327;BA.debugLine="haybasura = False";
_haybasura = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 328;BA.debugLine="haycampo = False";
_haycampo = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 330;BA.debugLine="DateTime.DateFormat = \"dd-MM-yyyy\"";
anywheresoftware.b4a.keywords.Common.DateTime.setDateFormat("dd-MM-yyyy");
 //BA.debugLineNum = 331;BA.debugLine="DateTime.TimeFormat = \"HH:mm\"";
anywheresoftware.b4a.keywords.Common.DateTime.setTimeFormat("HH:mm");
 //BA.debugLineNum = 332;BA.debugLine="dateandtime = DateTime.Date(DateTime.now)";
mostCurrent._dateandtime = anywheresoftware.b4a.keywords.Common.DateTime.Date(anywheresoftware.b4a.keywords.Common.DateTime.getNow());
 //BA.debugLineNum = 333;BA.debugLine="valorcalidad = 0";
_valorcalidad = 0;
 //BA.debugLineNum = 334;BA.debugLine="valorNS = 0";
_valorns = (int) (0);
 //BA.debugLineNum = 337;BA.debugLine="tabReporteHabitatRio.LoadLayout(\"reporte_habitat_";
mostCurrent._tabreportehabitatrio.LoadLayout("reporte_habitat_laguna_q1",BA.ObjectToCharSequence(""));
 //BA.debugLineNum = 338;BA.debugLine="tabReporteHabitatRio.LoadLayout(\"reporte_habitat_";
mostCurrent._tabreportehabitatrio.LoadLayout("reporte_habitat_laguna_q1p2",BA.ObjectToCharSequence(""));
 //BA.debugLineNum = 339;BA.debugLine="tabReporteHabitatRio.LoadLayout(\"reporte_habitat_";
mostCurrent._tabreportehabitatrio.LoadLayout("reporte_habitat_laguna_q2",BA.ObjectToCharSequence(""));
 //BA.debugLineNum = 340;BA.debugLine="tabReporteHabitatRio.LoadLayout(\"reporte_habitat_";
mostCurrent._tabreportehabitatrio.LoadLayout("reporte_habitat_laguna_q2p2",BA.ObjectToCharSequence(""));
 //BA.debugLineNum = 341;BA.debugLine="tabReporteHabitatRio.LoadLayout(\"reporte_habitat_";
mostCurrent._tabreportehabitatrio.LoadLayout("reporte_habitat_laguna_q2p3",BA.ObjectToCharSequence(""));
 //BA.debugLineNum = 342;BA.debugLine="tabReporteHabitatRio.LoadLayout(\"reporte_habitat_";
mostCurrent._tabreportehabitatrio.LoadLayout("reporte_habitat_laguna_q2p4",BA.ObjectToCharSequence(""));
 //BA.debugLineNum = 343;BA.debugLine="tabReporteHabitatRio.LoadLayout(\"reporte_habitat_";
mostCurrent._tabreportehabitatrio.LoadLayout("reporte_habitat_laguna_q3",BA.ObjectToCharSequence(""));
 //BA.debugLineNum = 344;BA.debugLine="tabReporteHabitatRio.LoadLayout(\"reporte_habitat_";
mostCurrent._tabreportehabitatrio.LoadLayout("reporte_habitat_laguna_q3p2",BA.ObjectToCharSequence(""));
 //BA.debugLineNum = 345;BA.debugLine="tabReporteHabitatRio.LoadLayout(\"reporte_habitat_";
mostCurrent._tabreportehabitatrio.LoadLayout("reporte_habitat_laguna_q3p3",BA.ObjectToCharSequence(""));
 //BA.debugLineNum = 346;BA.debugLine="tabReporteHabitatRio.LoadLayout(\"reporte_habitat_";
mostCurrent._tabreportehabitatrio.LoadLayout("reporte_habitat_laguna_q4",BA.ObjectToCharSequence(""));
 //BA.debugLineNum = 347;BA.debugLine="tabReporteHabitatRio.LoadLayout(\"reporte_habitat_";
mostCurrent._tabreportehabitatrio.LoadLayout("reporte_habitat_laguna_q4p2",BA.ObjectToCharSequence(""));
 //BA.debugLineNum = 348;BA.debugLine="tabReporteHabitatRio.LoadLayout(\"reporte_habitat_";
mostCurrent._tabreportehabitatrio.LoadLayout("reporte_habitat_laguna_q5",BA.ObjectToCharSequence(""));
 //BA.debugLineNum = 349;BA.debugLine="tabReporteHabitatRio.LoadLayout(\"reporte_habitat_";
mostCurrent._tabreportehabitatrio.LoadLayout("reporte_habitat_laguna_q5p2",BA.ObjectToCharSequence(""));
 //BA.debugLineNum = 350;BA.debugLine="tabReporteHabitatRio.LoadLayout(\"reporte_habitat_";
mostCurrent._tabreportehabitatrio.LoadLayout("reporte_habitat_laguna_q5p3",BA.ObjectToCharSequence(""));
 //BA.debugLineNum = 351;BA.debugLine="tabReporteHabitatRio.LoadLayout(\"reporte_habitat_";
mostCurrent._tabreportehabitatrio.LoadLayout("reporte_habitat_laguna_q6",BA.ObjectToCharSequence(""));
 //BA.debugLineNum = 352;BA.debugLine="tabReporteHabitatRio.LoadLayout(\"reporte_habitat_";
mostCurrent._tabreportehabitatrio.LoadLayout("reporte_habitat_laguna_exoticas",BA.ObjectToCharSequence(""));
 //BA.debugLineNum = 353;BA.debugLine="tabReporteHabitatRio.LoadLayout(\"reporte_habitat_";
mostCurrent._tabreportehabitatrio.LoadLayout("reporte_habitat_laguna_exoticas2",BA.ObjectToCharSequence(""));
 //BA.debugLineNum = 354;BA.debugLine="tabReporteHabitatRio.LoadLayout(\"reporte_habitat_";
mostCurrent._tabreportehabitatrio.LoadLayout("reporte_habitat_laguna_checklist",BA.ObjectToCharSequence(""));
 //BA.debugLineNum = 355;BA.debugLine="tabReporteHabitatRio.LoadLayout(\"reporte_habitat_";
mostCurrent._tabreportehabitatrio.LoadLayout("reporte_habitat_laguna_q7",BA.ObjectToCharSequence(""));
 //BA.debugLineNum = 357;BA.debugLine="pnlQuestions.Visible = False";
mostCurrent._pnlquestions.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 360;BA.debugLine="btnPrev.Visible = True";
mostCurrent._btnprev.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 363;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 //BA.debugLineNum = 364;BA.debugLine="utilidades.CreateHaloEffect(Activity, btnPregunta";
mostCurrent._utilidades._createhaloeffect /*void*/ (mostCurrent.activityBA,(anywheresoftware.b4a.objects.B4XViewWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.B4XViewWrapper(), (java.lang.Object)(mostCurrent._activity.getObject())),mostCurrent._btnpregunta2,anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (93),(int) (221),(int) (140)));
 //BA.debugLineNum = 366;BA.debugLine="End Sub";
return "";
}
public static boolean  _activity_keypress(int _keycode) throws Exception{
 //BA.debugLineNum = 377;BA.debugLine="Sub Activity_KeyPress (KeyCode As Int) As Boolean";
 //BA.debugLineNum = 378;BA.debugLine="If KeyCode = KeyCodes.KEYCODE_BACK Then";
if (_keycode==anywheresoftware.b4a.keywords.Common.KeyCodes.KEYCODE_BACK) { 
 //BA.debugLineNum = 379;BA.debugLine="If currentscreen = \"foto\" Then";
if ((mostCurrent._currentscreen).equals("foto")) { 
 //BA.debugLineNum = 380;BA.debugLine="fondogris.RemoveView";
mostCurrent._fondogris.RemoveView();
 //BA.debugLineNum = 381;BA.debugLine="If mag1.IsInitialized Then";
if (mostCurrent._mag1.IsInitialized()) { 
 //BA.debugLineNum = 382;BA.debugLine="mag1.RemoveView";
mostCurrent._mag1.RemoveView();
 };
 //BA.debugLineNum = 384;BA.debugLine="currentscreen = \"\"";
mostCurrent._currentscreen = "";
 }else {
 //BA.debugLineNum = 386;BA.debugLine="If tabReporteHabitatRio.CurrentPage = 0 Then";
if (mostCurrent._tabreportehabitatrio.getCurrentPage()==0) { 
 //BA.debugLineNum = 387;BA.debugLine="If pnlQuestions.Visible = False Then";
if (mostCurrent._pnlquestions.getVisible()==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 388;BA.debugLine="closeScrMsgBox";
_closescrmsgbox();
 }else {
 //BA.debugLineNum = 391;BA.debugLine="btnPrev_Click";
_btnprev_click();
 };
 }else {
 //BA.debugLineNum = 394;BA.debugLine="btnPrev_Click";
_btnprev_click();
 };
 };
 //BA.debugLineNum = 398;BA.debugLine="Return True";
if (true) return anywheresoftware.b4a.keywords.Common.True;
 }else {
 //BA.debugLineNum = 400;BA.debugLine="Return False";
if (true) return anywheresoftware.b4a.keywords.Common.False;
 };
 //BA.debugLineNum = 402;BA.debugLine="End Sub";
return false;
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 373;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 375;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 368;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 371;BA.debugLine="End Sub";
return "";
}
public static String  _btncerrar_click() throws Exception{
 //BA.debugLineNum = 1728;BA.debugLine="Sub btnCerrar_Click";
 //BA.debugLineNum = 1731;BA.debugLine="pnlResultados.Visible = False";
mostCurrent._pnlresultados.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1734;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 1735;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 1736;BA.debugLine="StartActivity(Reporte_Fotos)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._reporte_fotos.getObject()));
 //BA.debugLineNum = 1738;BA.debugLine="End Sub";
return "";
}
public static String  _btnmasdetalle_click() throws Exception{
 //BA.debugLineNum = 1375;BA.debugLine="Sub btnMasDetalle_Click";
 //BA.debugLineNum = 1376;BA.debugLine="Panel1.Top = 5%y";
mostCurrent._panel1.setTop(anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (5),mostCurrent.activityBA));
 //BA.debugLineNum = 1377;BA.debugLine="Panel1.Height = 90%y";
mostCurrent._panel1.setHeight(anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (90),mostCurrent.activityBA));
 //BA.debugLineNum = 1378;BA.debugLine="scrResultados.Visible = True";
mostCurrent._scrresultados.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1379;BA.debugLine="scrResultados.Height = 40%y";
mostCurrent._scrresultados.setHeight(anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (40),mostCurrent.activityBA));
 //BA.debugLineNum = 1380;BA.debugLine="btnMasDetalle.Visible = False";
mostCurrent._btnmasdetalle.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1382;BA.debugLine="btnCerrar.Top = 75%y";
mostCurrent._btncerrar.setTop(anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (75),mostCurrent.activityBA));
 //BA.debugLineNum = 1384;BA.debugLine="End Sub";
return "";
}
public static String  _btnnext_click() throws Exception{
int _countveg = 0;
 //BA.debugLineNum = 694;BA.debugLine="Private Sub btnNext_Click";
 //BA.debugLineNum = 695;BA.debugLine="btnTerminar.Visible = False";
mostCurrent._btnterminar.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 698;BA.debugLine="If tabReporteHabitatRio.CurrentPage = 0 Then 'q1";
if (mostCurrent._tabreportehabitatrio.getCurrentPage()==0) { 
 //BA.debugLineNum = 699;BA.debugLine="If lblRojo_Q1.Visible = False Then";
if (mostCurrent._lblrojo_q1.getVisible()==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 700;BA.debugLine="ToastMessageShow(\"Debe seleccionar una opción p";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Debe seleccionar una opción para seguir"),anywheresoftware.b4a.keywords.Common.False);
 }else {
 //BA.debugLineNum = 703;BA.debugLine="tabReporteHabitatRio.ScrollTo(1, True)";
mostCurrent._tabreportehabitatrio.ScrollTo((int) (1),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 704;BA.debugLine="currentpregunta = 1";
_currentpregunta = (int) (1);
 };
 }else if(mostCurrent._tabreportehabitatrio.getCurrentPage()==1) { 
 //BA.debugLineNum = 707;BA.debugLine="If lblRojo_Q1p2.Visible = False Then";
if (mostCurrent._lblrojo_q1p2.getVisible()==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 708;BA.debugLine="ToastMessageShow(\"Debe seleccionar una opción p";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Debe seleccionar una opción para seguir"),anywheresoftware.b4a.keywords.Common.False);
 }else {
 //BA.debugLineNum = 711;BA.debugLine="tabReporteHabitatRio.ScrollTo(2, True)";
mostCurrent._tabreportehabitatrio.ScrollTo((int) (2),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 712;BA.debugLine="currentpregunta = 2";
_currentpregunta = (int) (2);
 //BA.debugLineNum = 714;BA.debugLine="pnlQuestions.Visible = False";
mostCurrent._pnlquestions.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 715;BA.debugLine="pnlImagen.Visible = True";
mostCurrent._pnlimagen.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 716;BA.debugLine="btnPregunta1.Visible = False";
mostCurrent._btnpregunta1.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 717;BA.debugLine="btnPregunta2.Visible = True";
mostCurrent._btnpregunta2.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 718;BA.debugLine="lblPregunta2.Visible = True";
mostCurrent._lblpregunta2.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 719;BA.debugLine="lblPregunta1.Visible = False";
mostCurrent._lblpregunta1.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 720;BA.debugLine="utilidades.CreateHaloEffect(Activity, btnPregun";
mostCurrent._utilidades._createhaloeffect /*void*/ (mostCurrent.activityBA,(anywheresoftware.b4a.objects.B4XViewWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.B4XViewWrapper(), (java.lang.Object)(mostCurrent._activity.getObject())),mostCurrent._btnpregunta2,anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (93),(int) (221),(int) (140)));
 };
 }else if(mostCurrent._tabreportehabitatrio.getCurrentPage()==2) { 
 //BA.debugLineNum = 724;BA.debugLine="If valorind5 = \"\" Then";
if ((mostCurrent._valorind5).equals("")) { 
 //BA.debugLineNum = 725;BA.debugLine="If lblRojo_Q2.Visible = False Then";
if (mostCurrent._lblrojo_q2.getVisible()==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 726;BA.debugLine="ToastMessageShow(\"Debe seleccionar una opción";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Debe seleccionar una opción para seguir"),anywheresoftware.b4a.keywords.Common.False);
 }else {
 //BA.debugLineNum = 729;BA.debugLine="tabReporteHabitatRio.ScrollTo(3, True)";
mostCurrent._tabreportehabitatrio.ScrollTo((int) (3),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 730;BA.debugLine="currentpregunta = 3";
_currentpregunta = (int) (3);
 };
 }else {
 //BA.debugLineNum = 734;BA.debugLine="If lblRojo_Q2.Visible = False Then";
if (mostCurrent._lblrojo_q2.getVisible()==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 735;BA.debugLine="ToastMessageShow(\"Debe seleccionar una opción";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Debe seleccionar una opción para seguir"),anywheresoftware.b4a.keywords.Common.False);
 }else {
 //BA.debugLineNum = 738;BA.debugLine="tabReporteHabitatRio.ScrollTo(4, True)";
mostCurrent._tabreportehabitatrio.ScrollTo((int) (4),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 739;BA.debugLine="currentpregunta = 4";
_currentpregunta = (int) (4);
 //BA.debugLineNum = 741;BA.debugLine="pnlQuestions.Visible = False";
mostCurrent._pnlquestions.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 742;BA.debugLine="pnlImagen.Visible = True";
mostCurrent._pnlimagen.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 744;BA.debugLine="btnPregunta2.Visible = False";
mostCurrent._btnpregunta2.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 745;BA.debugLine="btnPregunta3.Visible = True";
mostCurrent._btnpregunta3.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 746;BA.debugLine="lblPregunta2.Visible = False";
mostCurrent._lblpregunta2.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 747;BA.debugLine="lblPregunta3.Visible = True";
mostCurrent._lblpregunta3.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 748;BA.debugLine="utilidades.CreateHaloEffect(Activity, btnPregu";
mostCurrent._utilidades._createhaloeffect /*void*/ (mostCurrent.activityBA,(anywheresoftware.b4a.objects.B4XViewWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.B4XViewWrapper(), (java.lang.Object)(mostCurrent._activity.getObject())),mostCurrent._btnpregunta3,anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (93),(int) (221),(int) (140)));
 };
 };
 }else if(mostCurrent._tabreportehabitatrio.getCurrentPage()==3) { 
 //BA.debugLineNum = 753;BA.debugLine="If lblRojo_Q2p2.Visible = False Then";
if (mostCurrent._lblrojo_q2p2.getVisible()==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 754;BA.debugLine="ToastMessageShow(\"Debe seleccionar una opción p";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Debe seleccionar una opción para seguir"),anywheresoftware.b4a.keywords.Common.False);
 }else {
 //BA.debugLineNum = 757;BA.debugLine="tabReporteHabitatRio.ScrollTo(4, True)";
mostCurrent._tabreportehabitatrio.ScrollTo((int) (4),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 758;BA.debugLine="currentpregunta = 4";
_currentpregunta = (int) (4);
 //BA.debugLineNum = 760;BA.debugLine="pnlQuestions.Visible = False";
mostCurrent._pnlquestions.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 761;BA.debugLine="pnlImagen.Visible = True";
mostCurrent._pnlimagen.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 763;BA.debugLine="btnPregunta2.Visible = False";
mostCurrent._btnpregunta2.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 764;BA.debugLine="btnPregunta3.Visible = True";
mostCurrent._btnpregunta3.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 765;BA.debugLine="lblPregunta2.Visible = False";
mostCurrent._lblpregunta2.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 766;BA.debugLine="lblPregunta3.Visible = True";
mostCurrent._lblpregunta3.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 767;BA.debugLine="utilidades.CreateHaloEffect(Activity, btnPregun";
mostCurrent._utilidades._createhaloeffect /*void*/ (mostCurrent.activityBA,(anywheresoftware.b4a.objects.B4XViewWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.B4XViewWrapper(), (java.lang.Object)(mostCurrent._activity.getObject())),mostCurrent._btnpregunta3,anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (93),(int) (221),(int) (140)));
 };
 }else if(mostCurrent._tabreportehabitatrio.getCurrentPage()==4) { 
 //BA.debugLineNum = 772;BA.debugLine="If lblRojo_Q2p3.Visible = False Then";
if (mostCurrent._lblrojo_q2p3.getVisible()==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 773;BA.debugLine="ToastMessageShow(\"Debe seleccionar una opción p";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Debe seleccionar una opción para seguir"),anywheresoftware.b4a.keywords.Common.False);
 }else {
 //BA.debugLineNum = 776;BA.debugLine="tabReporteHabitatRio.ScrollTo(5, True)";
mostCurrent._tabreportehabitatrio.ScrollTo((int) (5),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 777;BA.debugLine="currentpregunta =5";
_currentpregunta = (int) (5);
 //BA.debugLineNum = 779;BA.debugLine="pnlQuestions.Visible = False";
mostCurrent._pnlquestions.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 780;BA.debugLine="pnlImagen.Visible = True";
mostCurrent._pnlimagen.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 782;BA.debugLine="btnPregunta3.Visible = False";
mostCurrent._btnpregunta3.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 783;BA.debugLine="btnPregunta4.Visible = True";
mostCurrent._btnpregunta4.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 784;BA.debugLine="utilidades.CreateHaloEffect(Activity, btnPregun";
mostCurrent._utilidades._createhaloeffect /*void*/ (mostCurrent.activityBA,(anywheresoftware.b4a.objects.B4XViewWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.B4XViewWrapper(), (java.lang.Object)(mostCurrent._activity.getObject())),mostCurrent._btnpregunta4,anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (93),(int) (221),(int) (140)));
 //BA.debugLineNum = 785;BA.debugLine="lblPregunta3.Visible = False";
mostCurrent._lblpregunta3.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 786;BA.debugLine="lblPregunta4.Visible = True";
mostCurrent._lblpregunta4.setVisible(anywheresoftware.b4a.keywords.Common.True);
 };
 }else if(mostCurrent._tabreportehabitatrio.getCurrentPage()==5) { 
 //BA.debugLineNum = 791;BA.debugLine="If lblRojo_Q2p4.Visible = False Then";
if (mostCurrent._lblrojo_q2p4.getVisible()==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 792;BA.debugLine="ToastMessageShow(\"Debe seleccionar una opción p";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Debe seleccionar una opción para seguir"),anywheresoftware.b4a.keywords.Common.False);
 }else {
 //BA.debugLineNum = 795;BA.debugLine="tabReporteHabitatRio.ScrollTo(6, True)";
mostCurrent._tabreportehabitatrio.ScrollTo((int) (6),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 796;BA.debugLine="currentpregunta =6";
_currentpregunta = (int) (6);
 //BA.debugLineNum = 798;BA.debugLine="pnlQuestions.Visible = False";
mostCurrent._pnlquestions.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 799;BA.debugLine="pnlImagen.Visible = True";
mostCurrent._pnlimagen.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 801;BA.debugLine="btnPregunta4.Visible = False";
mostCurrent._btnpregunta4.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 802;BA.debugLine="btnPregunta5.Visible = True";
mostCurrent._btnpregunta5.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 804;BA.debugLine="lblPregunta4.Visible = False";
mostCurrent._lblpregunta4.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 805;BA.debugLine="lblPregunta5.Visible = True";
mostCurrent._lblpregunta5.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 806;BA.debugLine="utilidades.CreateHaloEffect(Activity, btnPregun";
mostCurrent._utilidades._createhaloeffect /*void*/ (mostCurrent.activityBA,(anywheresoftware.b4a.objects.B4XViewWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.B4XViewWrapper(), (java.lang.Object)(mostCurrent._activity.getObject())),mostCurrent._btnpregunta5,anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (93),(int) (221),(int) (140)));
 };
 }else if(mostCurrent._tabreportehabitatrio.getCurrentPage()==6) { 
 //BA.debugLineNum = 810;BA.debugLine="If lblRojo_Q3.Visible = False Then";
if (mostCurrent._lblrojo_q3.getVisible()==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 811;BA.debugLine="ToastMessageShow(\"Debe seleccionar una opción p";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Debe seleccionar una opción para seguir"),anywheresoftware.b4a.keywords.Common.False);
 }else {
 //BA.debugLineNum = 814;BA.debugLine="tabReporteHabitatRio.ScrollTo(7, True)";
mostCurrent._tabreportehabitatrio.ScrollTo((int) (7),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 815;BA.debugLine="currentpregunta =7";
_currentpregunta = (int) (7);
 };
 }else if(mostCurrent._tabreportehabitatrio.getCurrentPage()==7) { 
 //BA.debugLineNum = 818;BA.debugLine="If valorind7 <> \"10\" Then 'si hay basura o en ag";
if ((mostCurrent._valorind7).equals("10") == false) { 
 //BA.debugLineNum = 819;BA.debugLine="If lblRojo_Q3p2.Visible = False Then";
if (mostCurrent._lblrojo_q3p2.getVisible()==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 820;BA.debugLine="ToastMessageShow(\"Debe seleccionar una opción";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Debe seleccionar una opción para seguir"),anywheresoftware.b4a.keywords.Common.False);
 }else {
 //BA.debugLineNum = 823;BA.debugLine="tabReporteHabitatRio.ScrollTo(8, True)";
mostCurrent._tabreportehabitatrio.ScrollTo((int) (8),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 824;BA.debugLine="currentpregunta =8";
_currentpregunta = (int) (8);
 };
 }else {
 //BA.debugLineNum = 829;BA.debugLine="tabReporteHabitatRio.ScrollTo(9, True)";
mostCurrent._tabreportehabitatrio.ScrollTo((int) (9),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 830;BA.debugLine="currentpregunta =9";
_currentpregunta = (int) (9);
 //BA.debugLineNum = 832;BA.debugLine="pnlQuestions.Visible = False";
mostCurrent._pnlquestions.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 833;BA.debugLine="pnlImagen.Visible = True";
mostCurrent._pnlimagen.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 835;BA.debugLine="btnPregunta5.Visible = False";
mostCurrent._btnpregunta5.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 836;BA.debugLine="btnPregunta6.Visible = True";
mostCurrent._btnpregunta6.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 838;BA.debugLine="lblPregunta5.Visible = False";
mostCurrent._lblpregunta5.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 839;BA.debugLine="lblPregunta6.Visible = True";
mostCurrent._lblpregunta6.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 840;BA.debugLine="utilidades.CreateHaloEffect(Activity, btnPregun";
mostCurrent._utilidades._createhaloeffect /*void*/ (mostCurrent.activityBA,(anywheresoftware.b4a.objects.B4XViewWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.B4XViewWrapper(), (java.lang.Object)(mostCurrent._activity.getObject())),mostCurrent._btnpregunta6,anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (93),(int) (221),(int) (140)));
 };
 }else if(mostCurrent._tabreportehabitatrio.getCurrentPage()==8) { 
 //BA.debugLineNum = 844;BA.debugLine="If lblRojo_Q3p3_a.visible = True And valorind7 >";
if (mostCurrent._lblrojo_q3p3_a.getVisible()==anywheresoftware.b4a.keywords.Common.True && (double)(Double.parseDouble(mostCurrent._valorind7))>0) { 
 //BA.debugLineNum = 845;BA.debugLine="valorind7 = valorind7 -1";
mostCurrent._valorind7 = BA.NumberToString((double)(Double.parseDouble(mostCurrent._valorind7))-1);
 };
 //BA.debugLineNum = 847;BA.debugLine="If lblRojo_Q3p3_b.visible = True  And valorind7";
if (mostCurrent._lblrojo_q3p3_b.getVisible()==anywheresoftware.b4a.keywords.Common.True && (double)(Double.parseDouble(mostCurrent._valorind7))>0) { 
 //BA.debugLineNum = 848;BA.debugLine="valorind7 = valorind7 -1";
mostCurrent._valorind7 = BA.NumberToString((double)(Double.parseDouble(mostCurrent._valorind7))-1);
 };
 //BA.debugLineNum = 850;BA.debugLine="If lblRojo_Q3p3_c.visible = True  And valorind7";
if (mostCurrent._lblrojo_q3p3_c.getVisible()==anywheresoftware.b4a.keywords.Common.True && (double)(Double.parseDouble(mostCurrent._valorind7))>0) { 
 //BA.debugLineNum = 851;BA.debugLine="valorind7 = valorind7 -1";
mostCurrent._valorind7 = BA.NumberToString((double)(Double.parseDouble(mostCurrent._valorind7))-1);
 };
 //BA.debugLineNum = 853;BA.debugLine="If lblRojo_Q3p3_d.visible = True  And valorind7";
if (mostCurrent._lblrojo_q3p3_d.getVisible()==anywheresoftware.b4a.keywords.Common.True && (double)(Double.parseDouble(mostCurrent._valorind7))>0) { 
 //BA.debugLineNum = 854;BA.debugLine="valorind7 = valorind7 -1";
mostCurrent._valorind7 = BA.NumberToString((double)(Double.parseDouble(mostCurrent._valorind7))-1);
 };
 //BA.debugLineNum = 856;BA.debugLine="If valorind7 <= 2 Then";
if ((double)(Double.parseDouble(mostCurrent._valorind7))<=2) { 
 //BA.debugLineNum = 857;BA.debugLine="valorind7 = 0";
mostCurrent._valorind7 = BA.NumberToString(0);
 };
 //BA.debugLineNum = 860;BA.debugLine="tabReporteHabitatRio.ScrollTo(9, True)";
mostCurrent._tabreportehabitatrio.ScrollTo((int) (9),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 861;BA.debugLine="currentpregunta =9";
_currentpregunta = (int) (9);
 //BA.debugLineNum = 863;BA.debugLine="pnlQuestions.Visible = False";
mostCurrent._pnlquestions.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 864;BA.debugLine="pnlImagen.Visible = True";
mostCurrent._pnlimagen.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 866;BA.debugLine="btnPregunta5.Visible = False";
mostCurrent._btnpregunta5.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 867;BA.debugLine="btnPregunta6.Visible = True";
mostCurrent._btnpregunta6.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 869;BA.debugLine="lblPregunta5.Visible = False";
mostCurrent._lblpregunta5.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 870;BA.debugLine="lblPregunta6.Visible = True";
mostCurrent._lblpregunta6.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 871;BA.debugLine="utilidades.CreateHaloEffect(Activity, btnPregunt";
mostCurrent._utilidades._createhaloeffect /*void*/ (mostCurrent.activityBA,(anywheresoftware.b4a.objects.B4XViewWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.B4XViewWrapper(), (java.lang.Object)(mostCurrent._activity.getObject())),mostCurrent._btnpregunta6,anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (93),(int) (221),(int) (140)));
 }else if(mostCurrent._tabreportehabitatrio.getCurrentPage()==9) { 
 //BA.debugLineNum = 873;BA.debugLine="If lblRojo_Q4_2.Visible = False Then";
if (mostCurrent._lblrojo_q4_2.getVisible()==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 874;BA.debugLine="ToastMessageShow(\"Debe seleccionar una opción p";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Debe seleccionar una opción para seguir"),anywheresoftware.b4a.keywords.Common.False);
 }else {
 //BA.debugLineNum = 877;BA.debugLine="tabReporteHabitatRio.ScrollTo(10, True)";
mostCurrent._tabreportehabitatrio.ScrollTo((int) (10),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 878;BA.debugLine="currentpregunta =10";
_currentpregunta = (int) (10);
 //BA.debugLineNum = 880;BA.debugLine="pnlQuestions.Visible = False";
mostCurrent._pnlquestions.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 881;BA.debugLine="pnlImagen.Visible = True";
mostCurrent._pnlimagen.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 883;BA.debugLine="btnPregunta6.Visible = False";
mostCurrent._btnpregunta6.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 884;BA.debugLine="btnPregunta7.Visible = True";
mostCurrent._btnpregunta7.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 886;BA.debugLine="lblPregunta6.Visible = False";
mostCurrent._lblpregunta6.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 887;BA.debugLine="lblPregunta7.Visible = True";
mostCurrent._lblpregunta7.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 888;BA.debugLine="utilidades.CreateHaloEffect(Activity, btnPregun";
mostCurrent._utilidades._createhaloeffect /*void*/ (mostCurrent.activityBA,(anywheresoftware.b4a.objects.B4XViewWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.B4XViewWrapper(), (java.lang.Object)(mostCurrent._activity.getObject())),mostCurrent._btnpregunta7,anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (93),(int) (221),(int) (140)));
 };
 }else if(mostCurrent._tabreportehabitatrio.getCurrentPage()==10) { 
 //BA.debugLineNum = 891;BA.debugLine="If lblRojo_Q4p2.Visible = False Then";
if (mostCurrent._lblrojo_q4p2.getVisible()==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 892;BA.debugLine="ToastMessageShow(\"Debe seleccionar una opción p";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Debe seleccionar una opción para seguir"),anywheresoftware.b4a.keywords.Common.False);
 }else {
 //BA.debugLineNum = 895;BA.debugLine="tabReporteHabitatRio.ScrollTo(11, True)";
mostCurrent._tabreportehabitatrio.ScrollTo((int) (11),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 896;BA.debugLine="currentpregunta =11";
_currentpregunta = (int) (11);
 //BA.debugLineNum = 897;BA.debugLine="scrollUsos.Panel.LoadLayout(\"pnlQ5_usos\")";
mostCurrent._scrollusos.getPanel().LoadLayout("pnlQ5_usos",mostCurrent.activityBA);
 //BA.debugLineNum = 898;BA.debugLine="scrollUsos.Panel.Height = imgQ5_1_i.Height + im";
mostCurrent._scrollusos.getPanel().setHeight((int) (mostCurrent._imgq5_1_i.getHeight()+mostCurrent._imgq5_1_i.getTop()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (50))));
 //BA.debugLineNum = 900;BA.debugLine="pnlQuestions.Visible = False";
mostCurrent._pnlquestions.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 901;BA.debugLine="pnlImagen.Visible = True";
mostCurrent._pnlimagen.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 903;BA.debugLine="btnPregunta7.Visible = False";
mostCurrent._btnpregunta7.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 904;BA.debugLine="btnPregunta8.Visible = True";
mostCurrent._btnpregunta8.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 906;BA.debugLine="lblPregunta7.Visible = False";
mostCurrent._lblpregunta7.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 907;BA.debugLine="lblPregunta8.Visible = True";
mostCurrent._lblpregunta8.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 908;BA.debugLine="utilidades.CreateHaloEffect(Activity, btnPregun";
mostCurrent._utilidades._createhaloeffect /*void*/ (mostCurrent.activityBA,(anywheresoftware.b4a.objects.B4XViewWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.B4XViewWrapper(), (java.lang.Object)(mostCurrent._activity.getObject())),mostCurrent._btnpregunta8,anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (93),(int) (221),(int) (140)));
 };
 }else if(mostCurrent._tabreportehabitatrio.getCurrentPage()==11) { 
 //BA.debugLineNum = 913;BA.debugLine="If haycampo = True Then";
if (_haycampo==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 915;BA.debugLine="tabReporteHabitatRio.ScrollTo(12, True)";
mostCurrent._tabreportehabitatrio.ScrollTo((int) (12),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 916;BA.debugLine="currentpregunta =12";
_currentpregunta = (int) (12);
 }else {
 //BA.debugLineNum = 918;BA.debugLine="If lblRojo_Q5_1.Visible = False Then";
if (mostCurrent._lblrojo_q5_1.getVisible()==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 919;BA.debugLine="ToastMessageShow(\"Debe seleccionar una opción";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Debe seleccionar una opción para seguir"),anywheresoftware.b4a.keywords.Common.False);
 }else {
 //BA.debugLineNum = 922;BA.debugLine="tabReporteHabitatRio.ScrollTo(14, True)";
mostCurrent._tabreportehabitatrio.ScrollTo((int) (14),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 923;BA.debugLine="scrollVegetacion.Panel.LoadLayout(\"pnlQ6_veget";
mostCurrent._scrollvegetacion.getPanel().LoadLayout("pnlQ6_vegetacion",mostCurrent.activityBA);
 //BA.debugLineNum = 924;BA.debugLine="scrollVegetacion.Panel.Height = imgQ6_e.Height";
mostCurrent._scrollvegetacion.getPanel().setHeight((int) (mostCurrent._imgq6_e.getHeight()+mostCurrent._imgq6_e.getTop()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (50))));
 //BA.debugLineNum = 925;BA.debugLine="currentpregunta =14";
_currentpregunta = (int) (14);
 //BA.debugLineNum = 927;BA.debugLine="pnlQuestions.Visible = False";
mostCurrent._pnlquestions.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 928;BA.debugLine="pnlImagen.Visible = True";
mostCurrent._pnlimagen.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 930;BA.debugLine="btnPregunta8.Visible = False";
mostCurrent._btnpregunta8.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 931;BA.debugLine="btnPregunta9.Visible = True";
mostCurrent._btnpregunta9.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 933;BA.debugLine="lblPregunta8.Visible = False";
mostCurrent._lblpregunta8.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 934;BA.debugLine="lblPregunta9.Visible = True";
mostCurrent._lblpregunta9.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 935;BA.debugLine="utilidades.CreateHaloEffect(Activity, btnPregu";
mostCurrent._utilidades._createhaloeffect /*void*/ (mostCurrent.activityBA,(anywheresoftware.b4a.objects.B4XViewWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.B4XViewWrapper(), (java.lang.Object)(mostCurrent._activity.getObject())),mostCurrent._btnpregunta9,anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (93),(int) (221),(int) (140)));
 };
 };
 }else if(mostCurrent._tabreportehabitatrio.getCurrentPage()==12) { 
 //BA.debugLineNum = 941;BA.debugLine="If lblRojo_Q5p2.Visible = False Then";
if (mostCurrent._lblrojo_q5p2.getVisible()==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 942;BA.debugLine="ToastMessageShow(\"Debe seleccionar una opción p";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Debe seleccionar una opción para seguir"),anywheresoftware.b4a.keywords.Common.False);
 }else {
 //BA.debugLineNum = 945;BA.debugLine="tabReporteHabitatRio.ScrollTo(13, True)";
mostCurrent._tabreportehabitatrio.ScrollTo((int) (13),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 946;BA.debugLine="currentpregunta =13";
_currentpregunta = (int) (13);
 };
 }else if(mostCurrent._tabreportehabitatrio.getCurrentPage()==13) { 
 //BA.debugLineNum = 949;BA.debugLine="If lblRojo_Q5p3.Visible = False Then";
if (mostCurrent._lblrojo_q5p3.getVisible()==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 950;BA.debugLine="ToastMessageShow(\"Debe seleccionar una opción p";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Debe seleccionar una opción para seguir"),anywheresoftware.b4a.keywords.Common.False);
 }else {
 //BA.debugLineNum = 953;BA.debugLine="tabReporteHabitatRio.ScrollTo(14, True)";
mostCurrent._tabreportehabitatrio.ScrollTo((int) (14),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 954;BA.debugLine="scrollVegetacion.Panel.LoadLayout(\"pnlQ6_vegeta";
mostCurrent._scrollvegetacion.getPanel().LoadLayout("pnlQ6_vegetacion",mostCurrent.activityBA);
 //BA.debugLineNum = 955;BA.debugLine="scrollVegetacion.Panel.Height = imgQ6_e.Height";
mostCurrent._scrollvegetacion.getPanel().setHeight((int) (mostCurrent._imgq6_e.getHeight()+mostCurrent._imgq6_e.getTop()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (50))));
 //BA.debugLineNum = 956;BA.debugLine="currentpregunta =14";
_currentpregunta = (int) (14);
 //BA.debugLineNum = 958;BA.debugLine="pnlQuestions.Visible = False";
mostCurrent._pnlquestions.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 959;BA.debugLine="pnlImagen.Visible = True";
mostCurrent._pnlimagen.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 961;BA.debugLine="btnPregunta8.Visible = False";
mostCurrent._btnpregunta8.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 962;BA.debugLine="btnPregunta9.Visible = True";
mostCurrent._btnpregunta9.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 964;BA.debugLine="lblPregunta8.Visible = False";
mostCurrent._lblpregunta8.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 965;BA.debugLine="lblPregunta9.Visible = True";
mostCurrent._lblpregunta9.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 966;BA.debugLine="utilidades.CreateHaloEffect(Activity, btnPregun";
mostCurrent._utilidades._createhaloeffect /*void*/ (mostCurrent.activityBA,(anywheresoftware.b4a.objects.B4XViewWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.B4XViewWrapper(), (java.lang.Object)(mostCurrent._activity.getObject())),mostCurrent._btnpregunta9,anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (93),(int) (221),(int) (140)));
 };
 }else if(mostCurrent._tabreportehabitatrio.getCurrentPage()==14) { 
 //BA.debugLineNum = 971;BA.debugLine="Dim countveg As Int";
_countveg = 0;
 //BA.debugLineNum = 972;BA.debugLine="If lblRojo_Q6_a.visible = True Then";
if (mostCurrent._lblrojo_q6_a.getVisible()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 973;BA.debugLine="countveg = countveg + 1";
_countveg = (int) (_countveg+1);
 };
 //BA.debugLineNum = 975;BA.debugLine="If lblRojo_Q6_b.visible = True Then";
if (mostCurrent._lblrojo_q6_b.getVisible()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 976;BA.debugLine="countveg = countveg + 1";
_countveg = (int) (_countveg+1);
 };
 //BA.debugLineNum = 978;BA.debugLine="If lblRojo_Q6_c.visible = True Then";
if (mostCurrent._lblrojo_q6_c.getVisible()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 979;BA.debugLine="countveg = countveg + 1";
_countveg = (int) (_countveg+1);
 };
 //BA.debugLineNum = 981;BA.debugLine="If lblRojo_Q6_d.visible = True Then";
if (mostCurrent._lblrojo_q6_d.getVisible()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 982;BA.debugLine="countveg = countveg + 1";
_countveg = (int) (_countveg+1);
 };
 //BA.debugLineNum = 984;BA.debugLine="If lblRojo_Q6_e.visible = True Then";
if (mostCurrent._lblrojo_q6_e.getVisible()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 985;BA.debugLine="countveg = 0";
_countveg = (int) (0);
 };
 //BA.debugLineNum = 987;BA.debugLine="If countveg >= 3 Then";
if (_countveg>=3) { 
 //BA.debugLineNum = 988;BA.debugLine="valorind3 = 10";
mostCurrent._valorind3 = BA.NumberToString(10);
 }else if(_countveg==2) { 
 //BA.debugLineNum = 990;BA.debugLine="valorind3 = 8";
mostCurrent._valorind3 = BA.NumberToString(8);
 }else if(_countveg==1) { 
 //BA.debugLineNum = 992;BA.debugLine="valorind3 = 4";
mostCurrent._valorind3 = BA.NumberToString(4);
 }else if(_countveg==0) { 
 //BA.debugLineNum = 994;BA.debugLine="valorind3 = 0";
mostCurrent._valorind3 = BA.NumberToString(0);
 };
 //BA.debugLineNum = 996;BA.debugLine="If lblRojo_Q6_a.Visible = False And lblRojo_Q6_b";
if (mostCurrent._lblrojo_q6_a.getVisible()==anywheresoftware.b4a.keywords.Common.False && mostCurrent._lblrojo_q6_b.getVisible()==anywheresoftware.b4a.keywords.Common.False && mostCurrent._lblrojo_q6_c.getVisible()==anywheresoftware.b4a.keywords.Common.False && mostCurrent._lblrojo_q6_d.getVisible()==anywheresoftware.b4a.keywords.Common.False && mostCurrent._lblrojo_q6_e.getVisible()==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 998;BA.debugLine="ToastMessageShow(\"Debe seleccionar una opción p";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Debe seleccionar una opción para seguir"),anywheresoftware.b4a.keywords.Common.False);
 }else {
 //BA.debugLineNum = 1001;BA.debugLine="tabReporteHabitatRio.ScrollTo(15, True)";
mostCurrent._tabreportehabitatrio.ScrollTo((int) (15),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1002;BA.debugLine="scrollExoticas1.Panel.LoadLayout(\"pnlExoticas_A";
mostCurrent._scrollexoticas1.getPanel().LoadLayout("pnlExoticas_Acacia",mostCurrent.activityBA);
 //BA.debugLineNum = 1003;BA.debugLine="scrollExoticas1.Panel.Height = imgQ6_1_c.Height";
mostCurrent._scrollexoticas1.getPanel().setHeight((int) (mostCurrent._imgq6_1_c.getHeight()+mostCurrent._imgq6_1_c.getTop()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (50))));
 //BA.debugLineNum = 1004;BA.debugLine="currentpregunta =15";
_currentpregunta = (int) (15);
 };
 }else if(mostCurrent._tabreportehabitatrio.getCurrentPage()==15) { 
 //BA.debugLineNum = 1009;BA.debugLine="If lblRojo_Q6_1.Visible = False Then";
if (mostCurrent._lblrojo_q6_1.getVisible()==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 1010;BA.debugLine="ToastMessageShow(\"Debe seleccionar una opción p";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Debe seleccionar una opción para seguir"),anywheresoftware.b4a.keywords.Common.False);
 }else {
 //BA.debugLineNum = 1013;BA.debugLine="tabReporteHabitatRio.ScrollTo(16, True)";
mostCurrent._tabreportehabitatrio.ScrollTo((int) (16),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1014;BA.debugLine="scrollExoticas2.Panel.LoadLayout(\"pnlExoticas_L";
mostCurrent._scrollexoticas2.getPanel().LoadLayout("pnlExoticas_Ligustro",mostCurrent.activityBA);
 //BA.debugLineNum = 1015;BA.debugLine="scrollExoticas2.Panel.Height = imgQ6_3_c.Height";
mostCurrent._scrollexoticas2.getPanel().setHeight((int) (mostCurrent._imgq6_3_c.getHeight()+mostCurrent._imgq6_3_c.getTop()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (50))));
 //BA.debugLineNum = 1016;BA.debugLine="currentpregunta =16";
_currentpregunta = (int) (16);
 };
 }else if(mostCurrent._tabreportehabitatrio.getCurrentPage()==16) { 
 //BA.debugLineNum = 1019;BA.debugLine="If lblRojo_Q6_3.Visible = False Then";
if (mostCurrent._lblrojo_q6_3.getVisible()==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 1020;BA.debugLine="ToastMessageShow(\"Debe seleccionar una opción p";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Debe seleccionar una opción para seguir"),anywheresoftware.b4a.keywords.Common.False);
 }else {
 //BA.debugLineNum = 1023;BA.debugLine="tabReporteHabitatRio.ScrollTo(17, True)";
mostCurrent._tabreportehabitatrio.ScrollTo((int) (17),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1024;BA.debugLine="scrollAnimales.Panel.LoadLayout(\"pnlAnimales\")";
mostCurrent._scrollanimales.getPanel().LoadLayout("pnlAnimales",mostCurrent.activityBA);
 //BA.debugLineNum = 1025;BA.debugLine="scrollAnimales.Panel.Height = imgQ4_e.Height +";
mostCurrent._scrollanimales.getPanel().setHeight((int) (mostCurrent._imgq4_e.getHeight()+mostCurrent._imgq4_e.getTop()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (50))));
 //BA.debugLineNum = 1026;BA.debugLine="currentpregunta =17";
_currentpregunta = (int) (17);
 //BA.debugLineNum = 1028;BA.debugLine="pnlQuestions.Visible = False";
mostCurrent._pnlquestions.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1029;BA.debugLine="pnlImagen.Visible = True";
mostCurrent._pnlimagen.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1031;BA.debugLine="btnPregunta9.Visible = False";
mostCurrent._btnpregunta9.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1032;BA.debugLine="btnPregunta10.Visible = True";
mostCurrent._btnpregunta10.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1034;BA.debugLine="lblPregunta9.Visible = False";
mostCurrent._lblpregunta9.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1035;BA.debugLine="lblPregunta10.Visible = True";
mostCurrent._lblpregunta10.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1036;BA.debugLine="utilidades.CreateHaloEffect(Activity, btnPregun";
mostCurrent._utilidades._createhaloeffect /*void*/ (mostCurrent.activityBA,(anywheresoftware.b4a.objects.B4XViewWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.B4XViewWrapper(), (java.lang.Object)(mostCurrent._activity.getObject())),mostCurrent._btnpregunta10,anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (93),(int) (221),(int) (140)));
 };
 }else if(mostCurrent._tabreportehabitatrio.getCurrentPage()==17) { 
 //BA.debugLineNum = 1041;BA.debugLine="checklist = \"\"";
mostCurrent._checklist = "";
 //BA.debugLineNum = 1042;BA.debugLine="If lblRojo_Q4_a.visible  = True Then";
if (mostCurrent._lblrojo_q4_a.getVisible()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 1043;BA.debugLine="checklist = checklist & \";aves\"";
mostCurrent._checklist = mostCurrent._checklist+";aves";
 };
 //BA.debugLineNum = 1045;BA.debugLine="If lblRojo_Q4_b.visible  = True Then";
if (mostCurrent._lblrojo_q4_b.getVisible()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 1046;BA.debugLine="checklist = checklist & \";peces\"";
mostCurrent._checklist = mostCurrent._checklist+";peces";
 };
 //BA.debugLineNum = 1048;BA.debugLine="If lblRojo_Q4_c.visible  = True Then";
if (mostCurrent._lblrojo_q4_c.getVisible()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 1049;BA.debugLine="checklist = checklist & \";libelulas\"";
mostCurrent._checklist = mostCurrent._checklist+";libelulas";
 };
 //BA.debugLineNum = 1051;BA.debugLine="If lblRojo_Q4_d.visible  = True Then";
if (mostCurrent._lblrojo_q4_d.getVisible()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 1052;BA.debugLine="checklist = checklist & \";mosquitos\"";
mostCurrent._checklist = mostCurrent._checklist+";mosquitos";
 };
 //BA.debugLineNum = 1054;BA.debugLine="If lblRojo_Q4_e.visible  = True Then";
if (mostCurrent._lblrojo_q4_e.getVisible()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 1055;BA.debugLine="checklist = checklist & \";roedores\"";
mostCurrent._checklist = mostCurrent._checklist+";roedores";
 };
 //BA.debugLineNum = 1057;BA.debugLine="If lblRojo_Q4_f.visible  = True Then";
if (mostCurrent._lblrojo_q4_f.getVisible()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 1058;BA.debugLine="checklist = checklist & \";tortugas\"";
mostCurrent._checklist = mostCurrent._checklist+";tortugas";
 };
 //BA.debugLineNum = 1061;BA.debugLine="tabReporteHabitatRio.ScrollTo(18, True)";
mostCurrent._tabreportehabitatrio.ScrollTo((int) (18),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1062;BA.debugLine="currentpregunta =18";
_currentpregunta = (int) (18);
 //BA.debugLineNum = 1064;BA.debugLine="pnlQuestions.Visible = False";
mostCurrent._pnlquestions.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1065;BA.debugLine="pnlImagen.Visible = True";
mostCurrent._pnlimagen.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1067;BA.debugLine="btnPregunta10.Visible = False";
mostCurrent._btnpregunta10.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1068;BA.debugLine="btnPregunta11.Visible = True";
mostCurrent._btnpregunta11.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1070;BA.debugLine="lblPregunta10.Visible = False";
mostCurrent._lblpregunta10.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1071;BA.debugLine="lblPregunta11.Visible = True";
mostCurrent._lblpregunta11.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1072;BA.debugLine="utilidades.CreateHaloEffect(Activity, btnPregunt";
mostCurrent._utilidades._createhaloeffect /*void*/ (mostCurrent.activityBA,(anywheresoftware.b4a.objects.B4XViewWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.B4XViewWrapper(), (java.lang.Object)(mostCurrent._activity.getObject())),mostCurrent._btnpregunta11,anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (93),(int) (221),(int) (140)));
 }else if(mostCurrent._tabreportehabitatrio.getCurrentPage()==18) { 
 //BA.debugLineNum = 1075;BA.debugLine="If lblRojo_Q4_a.visible = True Then";
if (mostCurrent._lblrojo_q4_a.getVisible()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 1076;BA.debugLine="ind_pvm_5 = \"si\"";
mostCurrent._ind_pvm_5 = "si";
 }else {
 //BA.debugLineNum = 1078;BA.debugLine="ind_pvm_5 = \"no\"";
mostCurrent._ind_pvm_5 = "no";
 };
 //BA.debugLineNum = 1081;BA.debugLine="If lblRojo_Q4_c.visible = True Then";
if (mostCurrent._lblrojo_q4_c.getVisible()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 1082;BA.debugLine="ind_pvm_6 = \"si\"";
mostCurrent._ind_pvm_6 = "si";
 }else {
 //BA.debugLineNum = 1084;BA.debugLine="ind_pvm_6 = \"no\"";
mostCurrent._ind_pvm_6 = "no";
 };
 //BA.debugLineNum = 1087;BA.debugLine="If lblRojo_Q4_b.visible = True Then";
if (mostCurrent._lblrojo_q4_b.getVisible()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 1088;BA.debugLine="ind_pvm_7 = \"si\"";
mostCurrent._ind_pvm_7 = "si";
 }else {
 //BA.debugLineNum = 1090;BA.debugLine="ind_pvm_7 = \"no\"";
mostCurrent._ind_pvm_7 = "no";
 };
 //BA.debugLineNum = 1093;BA.debugLine="If lblRojo_Q4_d.visible = True Then";
if (mostCurrent._lblrojo_q4_d.getVisible()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 1094;BA.debugLine="ind_pvm_8 = \"si\"";
mostCurrent._ind_pvm_8 = "si";
 }else {
 //BA.debugLineNum = 1096;BA.debugLine="ind_pvm_8 = \"no\"";
mostCurrent._ind_pvm_8 = "no";
 };
 //BA.debugLineNum = 1102;BA.debugLine="pnlQuestions.Visible = False";
mostCurrent._pnlquestions.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1103;BA.debugLine="pnlImagen.Visible = True";
mostCurrent._pnlimagen.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1113;BA.debugLine="btnPregunta11.Visible = False";
mostCurrent._btnpregunta11.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1114;BA.debugLine="lblPregunta11.Visible = False";
mostCurrent._lblpregunta11.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1117;BA.debugLine="btnTerminar.Visible = True";
mostCurrent._btnterminar.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1118;BA.debugLine="btnTerminar.Enabled =True";
mostCurrent._btnterminar.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1119;BA.debugLine="utilidades.CreateHaloEffect(Activity, btnTermina";
mostCurrent._utilidades._createhaloeffect /*void*/ (mostCurrent.activityBA,(anywheresoftware.b4a.objects.B4XViewWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.B4XViewWrapper(), (java.lang.Object)(mostCurrent._activity.getObject())),mostCurrent._btnterminar,anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (93),(int) (221),(int) (140)));
 }else if(mostCurrent._tabreportehabitatrio.getCurrentPage()==19) { 
 };
 //BA.debugLineNum = 1138;BA.debugLine="End Sub";
return "";
}
public static String  _btnpregunta1_click() throws Exception{
 //BA.debugLineNum = 1798;BA.debugLine="Sub btnPregunta1_Click";
 //BA.debugLineNum = 1799;BA.debugLine="pnlQuestions.Visible = True";
mostCurrent._pnlquestions.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1800;BA.debugLine="pnlImagen.Visible = False";
mostCurrent._pnlimagen.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1801;BA.debugLine="btnPregunta1.Visible = False";
mostCurrent._btnpregunta1.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1802;BA.debugLine="lblPregunta1.Visible = False";
mostCurrent._lblpregunta1.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1803;BA.debugLine="End Sub";
return "";
}
public static String  _btnpregunta10_click() throws Exception{
 //BA.debugLineNum = 1854;BA.debugLine="Sub btnPregunta10_Click";
 //BA.debugLineNum = 1855;BA.debugLine="pnlQuestions.Visible = True";
mostCurrent._pnlquestions.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1856;BA.debugLine="pnlImagen.Visible = False";
mostCurrent._pnlimagen.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1857;BA.debugLine="btnPregunta10.Visible = False";
mostCurrent._btnpregunta10.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1858;BA.debugLine="lblPregunta10.Visible = False";
mostCurrent._lblpregunta10.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1859;BA.debugLine="End Sub";
return "";
}
public static String  _btnpregunta11_click() throws Exception{
 //BA.debugLineNum = 1860;BA.debugLine="Sub btnPregunta11_Click";
 //BA.debugLineNum = 1861;BA.debugLine="pnlQuestions.Visible = True";
mostCurrent._pnlquestions.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1862;BA.debugLine="pnlImagen.Visible = False";
mostCurrent._pnlimagen.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1863;BA.debugLine="btnPregunta11.Visible = False";
mostCurrent._btnpregunta11.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1864;BA.debugLine="lblPregunta11.Visible = False";
mostCurrent._lblpregunta11.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1865;BA.debugLine="End Sub";
return "";
}
public static String  _btnpregunta12_click() throws Exception{
 //BA.debugLineNum = 1866;BA.debugLine="Sub btnPregunta12_Click";
 //BA.debugLineNum = 1867;BA.debugLine="pnlQuestions.Visible = True";
mostCurrent._pnlquestions.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1868;BA.debugLine="pnlImagen.Visible = False";
mostCurrent._pnlimagen.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1869;BA.debugLine="btnPregunta12.Visible = False";
mostCurrent._btnpregunta12.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1870;BA.debugLine="lblPregunta12.Visible = False";
mostCurrent._lblpregunta12.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1871;BA.debugLine="End Sub";
return "";
}
public static String  _btnpregunta13_click() throws Exception{
anywheresoftware.b4a.objects.collections.Map _map1 = null;
 //BA.debugLineNum = 1872;BA.debugLine="Sub btnPregunta13_Click";
 //BA.debugLineNum = 1876;BA.debugLine="Panel1.Visible = True";
mostCurrent._panel1.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1877;BA.debugLine="If btnPregunta13.Text = \"Agregar notas al análisi";
if ((mostCurrent._btnpregunta13.getText()).equals("Agregar notas al análisis")) { 
 //BA.debugLineNum = 1878;BA.debugLine="scrResultados.Visible = False";
mostCurrent._scrresultados.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1879;BA.debugLine="Label1.Visible = True";
mostCurrent._label1.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1880;BA.debugLine="txtNotas.Visible = True";
mostCurrent._txtnotas.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1881;BA.debugLine="Label1.Top = Gauge1.mBase.Top + Gauge1.mBase.Hei";
mostCurrent._label1.setTop((int) (mostCurrent._gauge1._mbase /*anywheresoftware.b4a.objects.B4XViewWrapper*/ .getTop()+mostCurrent._gauge1._mbase /*anywheresoftware.b4a.objects.B4XViewWrapper*/ .getHeight()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (15))));
 //BA.debugLineNum = 1882;BA.debugLine="Label1.Height = btnPregunta13.Top - (Gauge1.mBas";
mostCurrent._label1.setHeight((int) (mostCurrent._btnpregunta13.getTop()-(mostCurrent._gauge1._mbase /*anywheresoftware.b4a.objects.B4XViewWrapper*/ .getTop()+mostCurrent._gauge1._mbase /*anywheresoftware.b4a.objects.B4XViewWrapper*/ .getHeight())-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (20))));
 //BA.debugLineNum = 1883;BA.debugLine="txtNotas.Top = Label1.Top";
mostCurrent._txtnotas.setTop(mostCurrent._label1.getTop());
 //BA.debugLineNum = 1884;BA.debugLine="txtNotas.Height = Label1.Height";
mostCurrent._txtnotas.setHeight(mostCurrent._label1.getHeight());
 //BA.debugLineNum = 1885;BA.debugLine="btnMasDetalle.Visible = False";
mostCurrent._btnmasdetalle.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1886;BA.debugLine="btnPregunta13.Text = \"Guardar notas\"";
mostCurrent._btnpregunta13.setText(BA.ObjectToCharSequence("Guardar notas"));
 //BA.debugLineNum = 1887;BA.debugLine="btnCerrar.Visible = False";
mostCurrent._btncerrar.setVisible(anywheresoftware.b4a.keywords.Common.False);
 }else if((mostCurrent._btnpregunta13.getText()).equals("Guardar notas")) { 
 //BA.debugLineNum = 1890;BA.debugLine="Label1.Visible = False";
mostCurrent._label1.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1891;BA.debugLine="txtNotas.Visible = False";
mostCurrent._txtnotas.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1892;BA.debugLine="btnMasDetalle.Visible = True";
mostCurrent._btnmasdetalle.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1893;BA.debugLine="btnPregunta13.Text = \"Agregar notas al análisis\"";
mostCurrent._btnpregunta13.setText(BA.ObjectToCharSequence("Agregar notas al análisis"));
 //BA.debugLineNum = 1894;BA.debugLine="btnCerrar.Visible = True";
mostCurrent._btncerrar.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1896;BA.debugLine="notas = txtNotas.text";
mostCurrent._notas = mostCurrent._txtnotas.getText();
 //BA.debugLineNum = 1899;BA.debugLine="Dim Map1 As Map";
_map1 = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 1900;BA.debugLine="Map1.Initialize";
_map1.Initialize();
 //BA.debugLineNum = 1901;BA.debugLine="Map1.Put(\"Id\", Form_Reporte.currentproject)";
_map1.Put((Object)("Id"),(Object)(mostCurrent._form_reporte._currentproject /*String*/ ));
 //BA.debugLineNum = 1902;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"evals\", \"no";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals","notas",(Object)(mostCurrent._notas),_map1);
 };
 //BA.debugLineNum = 1906;BA.debugLine="End Sub";
return "";
}
public static String  _btnpregunta2_click() throws Exception{
 //BA.debugLineNum = 1804;BA.debugLine="Sub btnPregunta2_Click";
 //BA.debugLineNum = 1805;BA.debugLine="btnPregunta2.Visible = False";
mostCurrent._btnpregunta2.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1806;BA.debugLine="lblPregunta2.Visible = False";
mostCurrent._lblpregunta2.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1807;BA.debugLine="pnlQuestions.Visible = True";
mostCurrent._pnlquestions.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1808;BA.debugLine="pnlImagen.Visible = False";
mostCurrent._pnlimagen.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1809;BA.debugLine="tabReporteHabitatRio.ScrollTo(2, False)";
mostCurrent._tabreportehabitatrio.ScrollTo((int) (2),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1810;BA.debugLine="currentpregunta = 2";
_currentpregunta = (int) (2);
 //BA.debugLineNum = 1811;BA.debugLine="End Sub";
return "";
}
public static String  _btnpregunta3_click() throws Exception{
 //BA.debugLineNum = 1812;BA.debugLine="Sub btnPregunta3_Click";
 //BA.debugLineNum = 1813;BA.debugLine="pnlQuestions.Visible = True";
mostCurrent._pnlquestions.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1814;BA.debugLine="pnlImagen.Visible = False";
mostCurrent._pnlimagen.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1815;BA.debugLine="btnPregunta3.Visible = False";
mostCurrent._btnpregunta3.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1816;BA.debugLine="lblPregunta3.Visible = False";
mostCurrent._lblpregunta3.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1817;BA.debugLine="End Sub";
return "";
}
public static String  _btnpregunta4_click() throws Exception{
 //BA.debugLineNum = 1818;BA.debugLine="Sub btnPregunta4_Click";
 //BA.debugLineNum = 1819;BA.debugLine="pnlQuestions.Visible = True";
mostCurrent._pnlquestions.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1820;BA.debugLine="pnlImagen.Visible = False";
mostCurrent._pnlimagen.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1821;BA.debugLine="btnPregunta4.Visible = False";
mostCurrent._btnpregunta4.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1822;BA.debugLine="lblPregunta4.Visible = False";
mostCurrent._lblpregunta4.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1823;BA.debugLine="End Sub";
return "";
}
public static String  _btnpregunta5_click() throws Exception{
 //BA.debugLineNum = 1824;BA.debugLine="Sub btnPregunta5_Click";
 //BA.debugLineNum = 1825;BA.debugLine="pnlQuestions.Visible = True";
mostCurrent._pnlquestions.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1826;BA.debugLine="pnlImagen.Visible = False";
mostCurrent._pnlimagen.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1827;BA.debugLine="btnPregunta5.Visible = False";
mostCurrent._btnpregunta5.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1828;BA.debugLine="lblPregunta5.Visible = False";
mostCurrent._lblpregunta5.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1829;BA.debugLine="End Sub";
return "";
}
public static String  _btnpregunta6_click() throws Exception{
 //BA.debugLineNum = 1830;BA.debugLine="Sub btnPregunta6_Click";
 //BA.debugLineNum = 1831;BA.debugLine="pnlQuestions.Visible = True";
mostCurrent._pnlquestions.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1832;BA.debugLine="pnlImagen.Visible = False";
mostCurrent._pnlimagen.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1833;BA.debugLine="btnPregunta6.Visible = False";
mostCurrent._btnpregunta6.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1834;BA.debugLine="lblPregunta6.Visible = False";
mostCurrent._lblpregunta6.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1835;BA.debugLine="End Sub";
return "";
}
public static String  _btnpregunta7_click() throws Exception{
 //BA.debugLineNum = 1836;BA.debugLine="Sub btnPregunta7_Click";
 //BA.debugLineNum = 1837;BA.debugLine="pnlQuestions.Visible = True";
mostCurrent._pnlquestions.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1838;BA.debugLine="pnlImagen.Visible = False";
mostCurrent._pnlimagen.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1839;BA.debugLine="btnPregunta7.Visible = False";
mostCurrent._btnpregunta7.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1840;BA.debugLine="lblPregunta7.Visible = False";
mostCurrent._lblpregunta7.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1841;BA.debugLine="End Sub";
return "";
}
public static String  _btnpregunta8_click() throws Exception{
 //BA.debugLineNum = 1842;BA.debugLine="Sub btnPregunta8_Click";
 //BA.debugLineNum = 1843;BA.debugLine="pnlQuestions.Visible = True";
mostCurrent._pnlquestions.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1844;BA.debugLine="pnlImagen.Visible = False";
mostCurrent._pnlimagen.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1845;BA.debugLine="btnPregunta8.Visible = False";
mostCurrent._btnpregunta8.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1846;BA.debugLine="lblPregunta8.Visible = False";
mostCurrent._lblpregunta8.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1847;BA.debugLine="End Sub";
return "";
}
public static String  _btnpregunta9_click() throws Exception{
 //BA.debugLineNum = 1848;BA.debugLine="Sub btnPregunta9_Click";
 //BA.debugLineNum = 1849;BA.debugLine="pnlQuestions.Visible = True";
mostCurrent._pnlquestions.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1850;BA.debugLine="pnlImagen.Visible = False";
mostCurrent._pnlimagen.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1851;BA.debugLine="btnPregunta9.Visible = False";
mostCurrent._btnpregunta9.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1852;BA.debugLine="lblPregunta9.Visible = False";
mostCurrent._lblpregunta9.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1853;BA.debugLine="End Sub";
return "";
}
public static String  _btnprev_click() throws Exception{
 //BA.debugLineNum = 420;BA.debugLine="Private Sub btnPrev_Click";
 //BA.debugLineNum = 421;BA.debugLine="If currentpregunta = 0 Then";
if (_currentpregunta==0) { 
 //BA.debugLineNum = 422;BA.debugLine="pnlQuestions.Visible = False";
mostCurrent._pnlquestions.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 423;BA.debugLine="pnlImagen.Visible = True";
mostCurrent._pnlimagen.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 424;BA.debugLine="btnPregunta1.Visible = True";
mostCurrent._btnpregunta1.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 425;BA.debugLine="lblPregunta1.Visible = True";
mostCurrent._lblpregunta1.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 426;BA.debugLine="currentpregunta = 0";
_currentpregunta = (int) (0);
 }else if(_currentpregunta==1) { 
 //BA.debugLineNum = 428;BA.debugLine="tabReporteHabitatRio.ScrollTo(tabReporteHabitatR";
mostCurrent._tabreportehabitatrio.ScrollTo((int) (mostCurrent._tabreportehabitatrio.getCurrentPage()-1),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 429;BA.debugLine="currentpregunta = 0";
_currentpregunta = (int) (0);
 }else if(_currentpregunta==2) { 
 //BA.debugLineNum = 431;BA.debugLine="If pnlQuestions.Visible = False Then";
if (mostCurrent._pnlquestions.getVisible()==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 432;BA.debugLine="btnPregunta2.Visible = False";
mostCurrent._btnpregunta2.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 433;BA.debugLine="lblPregunta2.Visible = False";
mostCurrent._lblpregunta2.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 434;BA.debugLine="pnlQuestions.Visible = True";
mostCurrent._pnlquestions.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 435;BA.debugLine="pnlImagen.Visible = False";
mostCurrent._pnlimagen.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 436;BA.debugLine="tabReporteHabitatRio.ScrollTo(tabReporteHabitat";
mostCurrent._tabreportehabitatrio.ScrollTo((int) (mostCurrent._tabreportehabitatrio.getCurrentPage()-2),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 437;BA.debugLine="currentpregunta = 0";
_currentpregunta = (int) (0);
 }else {
 //BA.debugLineNum = 439;BA.debugLine="btnPregunta2.Visible = True";
mostCurrent._btnpregunta2.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 440;BA.debugLine="lblPregunta2.Visible = True";
mostCurrent._lblpregunta2.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 441;BA.debugLine="pnlQuestions.Visible = False";
mostCurrent._pnlquestions.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 442;BA.debugLine="pnlImagen.Visible = True";
mostCurrent._pnlimagen.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 443;BA.debugLine="tabReporteHabitatRio.ScrollTo(tabReporteHabitat";
mostCurrent._tabreportehabitatrio.ScrollTo((int) (mostCurrent._tabreportehabitatrio.getCurrentPage()-2),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 444;BA.debugLine="currentpregunta = 0";
_currentpregunta = (int) (0);
 };
 }else if(_currentpregunta==3) { 
 //BA.debugLineNum = 447;BA.debugLine="currentpregunta = 2";
_currentpregunta = (int) (2);
 //BA.debugLineNum = 448;BA.debugLine="tabReporteHabitatRio.ScrollTo(tabReporteHabitatR";
mostCurrent._tabreportehabitatrio.ScrollTo((int) (mostCurrent._tabreportehabitatrio.getCurrentPage()-1),anywheresoftware.b4a.keywords.Common.True);
 }else if(_currentpregunta==4) { 
 //BA.debugLineNum = 451;BA.debugLine="If pnlQuestions.Visible = False Then";
if (mostCurrent._pnlquestions.getVisible()==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 452;BA.debugLine="btnPregunta3.Visible = False";
mostCurrent._btnpregunta3.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 453;BA.debugLine="lblPregunta3.Visible = False";
mostCurrent._lblpregunta3.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 454;BA.debugLine="pnlQuestions.Visible = True";
mostCurrent._pnlquestions.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 455;BA.debugLine="pnlImagen.Visible = False";
mostCurrent._pnlimagen.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 456;BA.debugLine="currentpregunta = 3";
_currentpregunta = (int) (3);
 //BA.debugLineNum = 457;BA.debugLine="tabReporteHabitatRio.ScrollTo(tabReporteHabitat";
mostCurrent._tabreportehabitatrio.ScrollTo((int) (mostCurrent._tabreportehabitatrio.getCurrentPage()-1),anywheresoftware.b4a.keywords.Common.True);
 }else {
 //BA.debugLineNum = 459;BA.debugLine="btnPregunta3.Visible = True";
mostCurrent._btnpregunta3.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 460;BA.debugLine="lblPregunta3.Visible = True";
mostCurrent._lblpregunta3.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 461;BA.debugLine="pnlQuestions.Visible = False";
mostCurrent._pnlquestions.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 462;BA.debugLine="pnlImagen.Visible = True";
mostCurrent._pnlimagen.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 463;BA.debugLine="currentpregunta = 4";
_currentpregunta = (int) (4);
 };
 }else if(_currentpregunta==5) { 
 //BA.debugLineNum = 466;BA.debugLine="If pnlQuestions.Visible = False Then";
if (mostCurrent._pnlquestions.getVisible()==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 467;BA.debugLine="btnPregunta4.Visible = False";
mostCurrent._btnpregunta4.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 468;BA.debugLine="lblPregunta4.Visible = False";
mostCurrent._lblpregunta4.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 469;BA.debugLine="pnlQuestions.Visible = True";
mostCurrent._pnlquestions.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 470;BA.debugLine="pnlImagen.Visible = False";
mostCurrent._pnlimagen.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 471;BA.debugLine="currentpregunta = 4";
_currentpregunta = (int) (4);
 //BA.debugLineNum = 472;BA.debugLine="tabReporteHabitatRio.ScrollTo(tabReporteHabitat";
mostCurrent._tabreportehabitatrio.ScrollTo((int) (mostCurrent._tabreportehabitatrio.getCurrentPage()-1),anywheresoftware.b4a.keywords.Common.True);
 }else {
 //BA.debugLineNum = 474;BA.debugLine="btnPregunta4.Visible = True";
mostCurrent._btnpregunta4.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 475;BA.debugLine="lblPregunta4.Visible = True";
mostCurrent._lblpregunta4.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 476;BA.debugLine="pnlQuestions.Visible = False";
mostCurrent._pnlquestions.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 477;BA.debugLine="pnlImagen.Visible = True";
mostCurrent._pnlimagen.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 478;BA.debugLine="currentpregunta = 5";
_currentpregunta = (int) (5);
 };
 }else if(_currentpregunta==6) { 
 //BA.debugLineNum = 481;BA.debugLine="If pnlQuestions.Visible = False Then";
if (mostCurrent._pnlquestions.getVisible()==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 482;BA.debugLine="btnPregunta5.Visible = False";
mostCurrent._btnpregunta5.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 483;BA.debugLine="lblPregunta5.Visible = False";
mostCurrent._lblpregunta5.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 484;BA.debugLine="pnlQuestions.Visible = True";
mostCurrent._pnlquestions.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 485;BA.debugLine="pnlImagen.Visible = False";
mostCurrent._pnlimagen.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 486;BA.debugLine="currentpregunta = 5";
_currentpregunta = (int) (5);
 //BA.debugLineNum = 487;BA.debugLine="tabReporteHabitatRio.ScrollTo(tabReporteHabitat";
mostCurrent._tabreportehabitatrio.ScrollTo((int) (mostCurrent._tabreportehabitatrio.getCurrentPage()-1),anywheresoftware.b4a.keywords.Common.True);
 }else {
 //BA.debugLineNum = 489;BA.debugLine="btnPregunta5.Visible = True";
mostCurrent._btnpregunta5.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 490;BA.debugLine="lblPregunta5.Visible = True";
mostCurrent._lblpregunta5.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 491;BA.debugLine="pnlQuestions.Visible = False";
mostCurrent._pnlquestions.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 492;BA.debugLine="pnlImagen.Visible = True";
mostCurrent._pnlimagen.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 493;BA.debugLine="currentpregunta = 6";
_currentpregunta = (int) (6);
 };
 }else if(_currentpregunta==7) { 
 //BA.debugLineNum = 496;BA.debugLine="currentpregunta = 6";
_currentpregunta = (int) (6);
 //BA.debugLineNum = 497;BA.debugLine="tabReporteHabitatRio.ScrollTo(tabReporteHabitatR";
mostCurrent._tabreportehabitatrio.ScrollTo((int) (mostCurrent._tabreportehabitatrio.getCurrentPage()-1),anywheresoftware.b4a.keywords.Common.True);
 }else if(_currentpregunta==8) { 
 //BA.debugLineNum = 499;BA.debugLine="currentpregunta = 7";
_currentpregunta = (int) (7);
 //BA.debugLineNum = 500;BA.debugLine="tabReporteHabitatRio.ScrollTo(tabReporteHabitatR";
mostCurrent._tabreportehabitatrio.ScrollTo((int) (mostCurrent._tabreportehabitatrio.getCurrentPage()-1),anywheresoftware.b4a.keywords.Common.True);
 }else if(_currentpregunta==9) { 
 //BA.debugLineNum = 503;BA.debugLine="If pnlQuestions.Visible = False Then";
if (mostCurrent._pnlquestions.getVisible()==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 504;BA.debugLine="btnPregunta6.Visible = False";
mostCurrent._btnpregunta6.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 505;BA.debugLine="lblPregunta6.Visible = False";
mostCurrent._lblpregunta6.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 506;BA.debugLine="pnlQuestions.Visible = True";
mostCurrent._pnlquestions.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 507;BA.debugLine="pnlImagen.Visible = False";
mostCurrent._pnlimagen.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 508;BA.debugLine="If valorind7 <> \"10\" Then";
if ((mostCurrent._valorind7).equals("10") == false) { 
 //BA.debugLineNum = 509;BA.debugLine="currentpregunta = 8";
_currentpregunta = (int) (8);
 //BA.debugLineNum = 510;BA.debugLine="tabReporteHabitatRio.ScrollTo(tabReporteHabita";
mostCurrent._tabreportehabitatrio.ScrollTo((int) (mostCurrent._tabreportehabitatrio.getCurrentPage()-1),anywheresoftware.b4a.keywords.Common.True);
 }else {
 //BA.debugLineNum = 512;BA.debugLine="currentpregunta = 7";
_currentpregunta = (int) (7);
 //BA.debugLineNum = 513;BA.debugLine="tabReporteHabitatRio.ScrollTo(tabReporteHabita";
mostCurrent._tabreportehabitatrio.ScrollTo((int) (mostCurrent._tabreportehabitatrio.getCurrentPage()-2),anywheresoftware.b4a.keywords.Common.True);
 };
 }else {
 //BA.debugLineNum = 516;BA.debugLine="btnPregunta6.Visible = True";
mostCurrent._btnpregunta6.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 517;BA.debugLine="lblPregunta6.Visible = True";
mostCurrent._lblpregunta6.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 518;BA.debugLine="pnlQuestions.Visible = False";
mostCurrent._pnlquestions.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 519;BA.debugLine="pnlImagen.Visible = True";
mostCurrent._pnlimagen.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 520;BA.debugLine="currentpregunta = 9";
_currentpregunta = (int) (9);
 };
 }else if(_currentpregunta==10) { 
 //BA.debugLineNum = 523;BA.debugLine="If pnlQuestions.Visible = False Then";
if (mostCurrent._pnlquestions.getVisible()==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 524;BA.debugLine="btnPregunta7.Visible = False";
mostCurrent._btnpregunta7.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 525;BA.debugLine="lblPregunta7.Visible = False";
mostCurrent._lblpregunta7.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 526;BA.debugLine="pnlQuestions.Visible = True";
mostCurrent._pnlquestions.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 527;BA.debugLine="pnlImagen.Visible = False";
mostCurrent._pnlimagen.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 528;BA.debugLine="currentpregunta = 9";
_currentpregunta = (int) (9);
 //BA.debugLineNum = 529;BA.debugLine="tabReporteHabitatRio.ScrollTo(tabReporteHabitat";
mostCurrent._tabreportehabitatrio.ScrollTo((int) (mostCurrent._tabreportehabitatrio.getCurrentPage()-1),anywheresoftware.b4a.keywords.Common.True);
 }else {
 //BA.debugLineNum = 532;BA.debugLine="btnPregunta7.Visible = True";
mostCurrent._btnpregunta7.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 533;BA.debugLine="lblPregunta7.Visible = True";
mostCurrent._lblpregunta7.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 534;BA.debugLine="pnlQuestions.Visible = False";
mostCurrent._pnlquestions.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 535;BA.debugLine="pnlImagen.Visible = True";
mostCurrent._pnlimagen.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 536;BA.debugLine="currentpregunta = 10";
_currentpregunta = (int) (10);
 };
 }else if(_currentpregunta==11) { 
 //BA.debugLineNum = 539;BA.debugLine="If pnlQuestions.Visible = False Then";
if (mostCurrent._pnlquestions.getVisible()==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 540;BA.debugLine="btnPregunta8.Visible = False";
mostCurrent._btnpregunta8.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 541;BA.debugLine="lblPregunta8.Visible = False";
mostCurrent._lblpregunta8.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 542;BA.debugLine="pnlQuestions.Visible = True";
mostCurrent._pnlquestions.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 543;BA.debugLine="pnlImagen.Visible = False";
mostCurrent._pnlimagen.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 544;BA.debugLine="currentpregunta = 10";
_currentpregunta = (int) (10);
 //BA.debugLineNum = 545;BA.debugLine="tabReporteHabitatRio.ScrollTo(tabReporteHabitat";
mostCurrent._tabreportehabitatrio.ScrollTo((int) (mostCurrent._tabreportehabitatrio.getCurrentPage()-1),anywheresoftware.b4a.keywords.Common.True);
 }else {
 //BA.debugLineNum = 548;BA.debugLine="btnPregunta8.Visible = True";
mostCurrent._btnpregunta8.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 549;BA.debugLine="lblPregunta8.Visible = True";
mostCurrent._lblpregunta8.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 550;BA.debugLine="pnlQuestions.Visible = False";
mostCurrent._pnlquestions.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 551;BA.debugLine="pnlImagen.Visible = True";
mostCurrent._pnlimagen.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 552;BA.debugLine="currentpregunta = 11";
_currentpregunta = (int) (11);
 };
 }else if(_currentpregunta==12) { 
 //BA.debugLineNum = 557;BA.debugLine="currentpregunta = 11";
_currentpregunta = (int) (11);
 //BA.debugLineNum = 558;BA.debugLine="tabReporteHabitatRio.ScrollTo(tabReporteHabitatR";
mostCurrent._tabreportehabitatrio.ScrollTo((int) (mostCurrent._tabreportehabitatrio.getCurrentPage()-1),anywheresoftware.b4a.keywords.Common.True);
 }else if(_currentpregunta==13) { 
 //BA.debugLineNum = 560;BA.debugLine="currentpregunta = 12";
_currentpregunta = (int) (12);
 //BA.debugLineNum = 561;BA.debugLine="tabReporteHabitatRio.ScrollTo(tabReporteHabitatR";
mostCurrent._tabreportehabitatrio.ScrollTo((int) (mostCurrent._tabreportehabitatrio.getCurrentPage()-1),anywheresoftware.b4a.keywords.Common.True);
 }else if(_currentpregunta==14) { 
 //BA.debugLineNum = 564;BA.debugLine="If pnlQuestions.Visible = False Then";
if (mostCurrent._pnlquestions.getVisible()==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 565;BA.debugLine="btnPregunta9.Visible = False";
mostCurrent._btnpregunta9.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 566;BA.debugLine="lblPregunta9.Visible = False";
mostCurrent._lblpregunta9.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 567;BA.debugLine="pnlQuestions.Visible = True";
mostCurrent._pnlquestions.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 568;BA.debugLine="pnlImagen.Visible = False";
mostCurrent._pnlimagen.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 569;BA.debugLine="currentpregunta = 11";
_currentpregunta = (int) (11);
 //BA.debugLineNum = 570;BA.debugLine="tabReporteHabitatRio.ScrollTo(tabReporteHabitat";
mostCurrent._tabreportehabitatrio.ScrollTo((int) (mostCurrent._tabreportehabitatrio.getCurrentPage()-3),anywheresoftware.b4a.keywords.Common.True);
 }else {
 //BA.debugLineNum = 573;BA.debugLine="btnPregunta9.Visible = True";
mostCurrent._btnpregunta9.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 574;BA.debugLine="lblPregunta9.Visible = True";
mostCurrent._lblpregunta9.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 575;BA.debugLine="pnlQuestions.Visible = False";
mostCurrent._pnlquestions.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 576;BA.debugLine="pnlImagen.Visible = True";
mostCurrent._pnlimagen.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 577;BA.debugLine="currentpregunta = 14";
_currentpregunta = (int) (14);
 };
 }else if(_currentpregunta==15) { 
 //BA.debugLineNum = 580;BA.debugLine="currentpregunta = 14";
_currentpregunta = (int) (14);
 //BA.debugLineNum = 581;BA.debugLine="tabReporteHabitatRio.ScrollTo(tabReporteHabitatR";
mostCurrent._tabreportehabitatrio.ScrollTo((int) (mostCurrent._tabreportehabitatrio.getCurrentPage()-1),anywheresoftware.b4a.keywords.Common.True);
 }else if(_currentpregunta==16) { 
 //BA.debugLineNum = 583;BA.debugLine="currentpregunta = 15";
_currentpregunta = (int) (15);
 //BA.debugLineNum = 584;BA.debugLine="tabReporteHabitatRio.ScrollTo(tabReporteHabitatR";
mostCurrent._tabreportehabitatrio.ScrollTo((int) (mostCurrent._tabreportehabitatrio.getCurrentPage()-1),anywheresoftware.b4a.keywords.Common.True);
 }else if(_currentpregunta==17) { 
 //BA.debugLineNum = 586;BA.debugLine="If pnlQuestions.Visible = False Then";
if (mostCurrent._pnlquestions.getVisible()==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 587;BA.debugLine="btnPregunta10.Visible = False";
mostCurrent._btnpregunta10.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 588;BA.debugLine="lblPregunta10.Visible = False";
mostCurrent._lblpregunta10.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 589;BA.debugLine="pnlQuestions.Visible = True";
mostCurrent._pnlquestions.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 590;BA.debugLine="pnlImagen.Visible = False";
mostCurrent._pnlimagen.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 591;BA.debugLine="currentpregunta = 16";
_currentpregunta = (int) (16);
 //BA.debugLineNum = 592;BA.debugLine="tabReporteHabitatRio.ScrollTo(tabReporteHabitat";
mostCurrent._tabreportehabitatrio.ScrollTo((int) (mostCurrent._tabreportehabitatrio.getCurrentPage()-1),anywheresoftware.b4a.keywords.Common.True);
 }else {
 //BA.debugLineNum = 595;BA.debugLine="pnlQuestions.Visible = False";
mostCurrent._pnlquestions.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 596;BA.debugLine="pnlImagen.Visible = True";
mostCurrent._pnlimagen.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 597;BA.debugLine="btnPregunta10.Visible = True";
mostCurrent._btnpregunta10.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 598;BA.debugLine="lblPregunta10.Visible = True";
mostCurrent._lblpregunta10.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 599;BA.debugLine="currentpregunta = 17";
_currentpregunta = (int) (17);
 };
 }else if(_currentpregunta==18) { 
 //BA.debugLineNum = 617;BA.debugLine="If pnlResultados.Visible = False Then";
if (mostCurrent._pnlresultados.getVisible()==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 618;BA.debugLine="If btnTerminar.Visible = False Then";
if (mostCurrent._btnterminar.getVisible()==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 619;BA.debugLine="If pnlQuestions.Visible = False Then";
if (mostCurrent._pnlquestions.getVisible()==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 620;BA.debugLine="btnPregunta12.Visible = False";
mostCurrent._btnpregunta12.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 621;BA.debugLine="lblPregunta12.Visible = False";
mostCurrent._lblpregunta12.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 622;BA.debugLine="pnlQuestions.Visible = True";
mostCurrent._pnlquestions.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 623;BA.debugLine="pnlImagen.Visible = False";
mostCurrent._pnlimagen.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 624;BA.debugLine="currentpregunta = 18";
_currentpregunta = (int) (18);
 //BA.debugLineNum = 625;BA.debugLine="tabReporteHabitatRio.ScrollTo(tabReporteHabit";
mostCurrent._tabreportehabitatrio.ScrollTo((int) (mostCurrent._tabreportehabitatrio.getCurrentPage()-1),anywheresoftware.b4a.keywords.Common.True);
 }else {
 //BA.debugLineNum = 628;BA.debugLine="pnlQuestions.Visible = False";
mostCurrent._pnlquestions.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 629;BA.debugLine="pnlImagen.Visible = True";
mostCurrent._pnlimagen.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 630;BA.debugLine="btnPregunta13.Visible = True";
mostCurrent._btnpregunta13.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 631;BA.debugLine="btnNext.Visible = True";
mostCurrent._btnnext.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 632;BA.debugLine="currentpregunta = 19";
_currentpregunta = (int) (19);
 };
 }else {
 //BA.debugLineNum = 635;BA.debugLine="btnTerminar.Visible = False";
mostCurrent._btnterminar.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 636;BA.debugLine="btnPregunta13.Visible = True";
mostCurrent._btnpregunta13.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 637;BA.debugLine="pnlQuestions.Visible = True";
mostCurrent._pnlquestions.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 638;BA.debugLine="pnlImagen.Visible = False";
mostCurrent._pnlimagen.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 639;BA.debugLine="btnNext.Visible = True";
mostCurrent._btnnext.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 640;BA.debugLine="currentpregunta = 19";
_currentpregunta = (int) (19);
 };
 }else {
 //BA.debugLineNum = 644;BA.debugLine="ToastMessageShow(\"Para volver a comenzar, debe";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Para volver a comenzar, debe empezar un nuevo análisis desde el comienzo"),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 645;BA.debugLine="Return";
if (true) return "";
 };
 }else if(_currentpregunta==19) { 
 //BA.debugLineNum = 649;BA.debugLine="If pnlResultados.Visible = False Then";
if (mostCurrent._pnlresultados.getVisible()==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 650;BA.debugLine="If btnTerminar.Visible = False Then";
if (mostCurrent._btnterminar.getVisible()==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 651;BA.debugLine="If pnlQuestions.Visible = False Then";
if (mostCurrent._pnlquestions.getVisible()==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 652;BA.debugLine="btnPregunta12.Visible = False";
mostCurrent._btnpregunta12.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 653;BA.debugLine="lblPregunta12.Visible = False";
mostCurrent._lblpregunta12.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 654;BA.debugLine="pnlQuestions.Visible = True";
mostCurrent._pnlquestions.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 655;BA.debugLine="pnlImagen.Visible = False";
mostCurrent._pnlimagen.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 656;BA.debugLine="currentpregunta = 18";
_currentpregunta = (int) (18);
 //BA.debugLineNum = 657;BA.debugLine="tabReporteHabitatRio.ScrollTo(tabReporteHabit";
mostCurrent._tabreportehabitatrio.ScrollTo((int) (mostCurrent._tabreportehabitatrio.getCurrentPage()-1),anywheresoftware.b4a.keywords.Common.True);
 }else {
 //BA.debugLineNum = 660;BA.debugLine="pnlQuestions.Visible = False";
mostCurrent._pnlquestions.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 661;BA.debugLine="pnlImagen.Visible = True";
mostCurrent._pnlimagen.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 662;BA.debugLine="btnPregunta13.Visible = True";
mostCurrent._btnpregunta13.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 663;BA.debugLine="btnNext.Visible = True";
mostCurrent._btnnext.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 664;BA.debugLine="currentpregunta = 19";
_currentpregunta = (int) (19);
 };
 }else {
 //BA.debugLineNum = 667;BA.debugLine="btnTerminar.Visible = False";
mostCurrent._btnterminar.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 668;BA.debugLine="btnPregunta13.Visible = True";
mostCurrent._btnpregunta13.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 669;BA.debugLine="pnlQuestions.Visible = True";
mostCurrent._pnlquestions.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 670;BA.debugLine="pnlImagen.Visible = False";
mostCurrent._pnlimagen.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 671;BA.debugLine="btnNext.Visible = True";
mostCurrent._btnnext.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 672;BA.debugLine="currentpregunta = 19";
_currentpregunta = (int) (19);
 };
 }else {
 //BA.debugLineNum = 676;BA.debugLine="ToastMessageShow(\"Para volver a comenzar, debe";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Para volver a comenzar, debe empezar un nuevo análisis desde el comienzo"),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 677;BA.debugLine="Return";
if (true) return "";
 };
 };
 //BA.debugLineNum = 686;BA.debugLine="End Sub";
return "";
}
public static String  _btnterminar_click() throws Exception{
 //BA.debugLineNum = 1146;BA.debugLine="Sub btnTerminar_Click";
 //BA.debugLineNum = 1149;BA.debugLine="btnTerminar.Enabled =False";
mostCurrent._btnterminar.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1150;BA.debugLine="TerminarEval";
_terminareval();
 //BA.debugLineNum = 1153;BA.debugLine="End Sub";
return "";
}
public static String  _cargardetalle() throws Exception{
anywheresoftware.b4a.objects.LabelWrapper _lbl_valorind9 = null;
anywheresoftware.b4a.objects.ImageViewWrapper _img_valorind9 = null;
anywheresoftware.b4a.objects.LabelWrapper _lbl_valorind10 = null;
anywheresoftware.b4a.objects.ImageViewWrapper _img_valorind10 = null;
anywheresoftware.b4a.objects.LabelWrapper _lbl_valorind5 = null;
anywheresoftware.b4a.objects.ImageViewWrapper _img_valorind5 = null;
anywheresoftware.b4a.objects.LabelWrapper _lbl_valorind6 = null;
anywheresoftware.b4a.objects.ImageViewWrapper _img_valorind6 = null;
anywheresoftware.b4a.objects.LabelWrapper _lbl_ind_pvm_1 = null;
anywheresoftware.b4a.objects.ImageViewWrapper _img_ind_pvm_1 = null;
anywheresoftware.b4a.objects.LabelWrapper _lbl_valorind7 = null;
anywheresoftware.b4a.objects.ImageViewWrapper _img_valorind7 = null;
anywheresoftware.b4a.objects.LabelWrapper _lbl_ind_pvm_2 = null;
anywheresoftware.b4a.objects.ImageViewWrapper _img_ind_pvm_2 = null;
anywheresoftware.b4a.objects.LabelWrapper _lbl_valorind4 = null;
anywheresoftware.b4a.objects.ImageViewWrapper _img_valorind4 = null;
anywheresoftware.b4a.objects.LabelWrapper _lbl_valorind1 = null;
anywheresoftware.b4a.objects.ImageViewWrapper _img_valorind1 = null;
anywheresoftware.b4a.objects.LabelWrapper _lbl_valorind2 = null;
anywheresoftware.b4a.objects.ImageViewWrapper _img_valorind2 = null;
anywheresoftware.b4a.objects.LabelWrapper _lbl_valorind3 = null;
anywheresoftware.b4a.objects.ImageViewWrapper _img_valorind3 = null;
anywheresoftware.b4a.objects.LabelWrapper _lbl_ind_pvm_3 = null;
anywheresoftware.b4a.objects.ImageViewWrapper _img_ind_pvm_3 = null;
anywheresoftware.b4a.objects.LabelWrapper _lbl_ind_pvm_4 = null;
anywheresoftware.b4a.objects.ImageViewWrapper _img_ind_pvm_4 = null;
int _orden = 0;
 //BA.debugLineNum = 1387;BA.debugLine="Sub CargarDetalle";
 //BA.debugLineNum = 1388;BA.debugLine="Dim lbl_valorind9 As Label";
_lbl_valorind9 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 1389;BA.debugLine="Dim img_valorind9 As ImageView";
_img_valorind9 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 1390;BA.debugLine="Dim lbl_valorind10 As Label";
_lbl_valorind10 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 1391;BA.debugLine="Dim img_valorind10 As ImageView";
_img_valorind10 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 1392;BA.debugLine="Dim lbl_valorind5 As Label";
_lbl_valorind5 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 1393;BA.debugLine="Dim img_valorind5 As ImageView";
_img_valorind5 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 1394;BA.debugLine="Dim lbl_valorind6 As Label";
_lbl_valorind6 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 1395;BA.debugLine="Dim img_valorind6 As ImageView";
_img_valorind6 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 1396;BA.debugLine="Dim lbl_ind_pvm_1 As Label";
_lbl_ind_pvm_1 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 1397;BA.debugLine="Dim img_ind_pvm_1 As ImageView";
_img_ind_pvm_1 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 1398;BA.debugLine="Dim lbl_valorind7 As Label";
_lbl_valorind7 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 1399;BA.debugLine="Dim img_valorind7 As ImageView";
_img_valorind7 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 1400;BA.debugLine="Dim lbl_ind_pvm_2 As Label";
_lbl_ind_pvm_2 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 1401;BA.debugLine="Dim img_ind_pvm_2 As ImageView";
_img_ind_pvm_2 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 1402;BA.debugLine="Dim lbl_valorind4 As Label";
_lbl_valorind4 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 1403;BA.debugLine="Dim img_valorind4 As ImageView";
_img_valorind4 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 1404;BA.debugLine="Dim lbl_valorind1 As Label";
_lbl_valorind1 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 1405;BA.debugLine="Dim img_valorind1 As ImageView";
_img_valorind1 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 1406;BA.debugLine="Dim lbl_valorind2 As Label";
_lbl_valorind2 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 1407;BA.debugLine="Dim img_valorind2 As ImageView";
_img_valorind2 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 1408;BA.debugLine="Dim lbl_valorind3 As Label";
_lbl_valorind3 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 1409;BA.debugLine="Dim img_valorind3 As ImageView";
_img_valorind3 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 1410;BA.debugLine="Dim lbl_ind_pvm_3 As Label";
_lbl_ind_pvm_3 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 1411;BA.debugLine="Dim img_ind_pvm_3 As ImageView";
_img_ind_pvm_3 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 1412;BA.debugLine="Dim lbl_ind_pvm_4 As Label";
_lbl_ind_pvm_4 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 1413;BA.debugLine="Dim img_ind_pvm_4 As ImageView";
_img_ind_pvm_4 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 1415;BA.debugLine="lbl_valorind10.Initialize(\"\")";
_lbl_valorind10.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 1416;BA.debugLine="img_valorind10.Initialize(\"\")";
_img_valorind10.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 1417;BA.debugLine="lbl_valorind9.Initialize(\"\")";
_lbl_valorind9.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 1418;BA.debugLine="img_valorind9.Initialize(\"\")";
_img_valorind9.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 1419;BA.debugLine="lbl_valorind5.Initialize(\"\")";
_lbl_valorind5.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 1420;BA.debugLine="img_valorind5.Initialize(\"\")";
_img_valorind5.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 1421;BA.debugLine="lbl_valorind6.Initialize(\"\")";
_lbl_valorind6.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 1422;BA.debugLine="img_valorind6.Initialize(\"\")";
_img_valorind6.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 1423;BA.debugLine="lbl_ind_pvm_1.Initialize(\"\")";
_lbl_ind_pvm_1.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 1424;BA.debugLine="img_ind_pvm_1.Initialize(\"\")";
_img_ind_pvm_1.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 1425;BA.debugLine="lbl_valorind7.Initialize(\"\")";
_lbl_valorind7.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 1426;BA.debugLine="img_valorind7.Initialize(\"\")";
_img_valorind7.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 1427;BA.debugLine="lbl_ind_pvm_2.Initialize(\"\")";
_lbl_ind_pvm_2.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 1428;BA.debugLine="img_ind_pvm_2.Initialize(\"\")";
_img_ind_pvm_2.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 1429;BA.debugLine="lbl_valorind4.Initialize(\"\")";
_lbl_valorind4.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 1430;BA.debugLine="img_valorind4.Initialize(\"\")";
_img_valorind4.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 1431;BA.debugLine="lbl_valorind1.Initialize(\"\")";
_lbl_valorind1.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 1432;BA.debugLine="img_valorind1.Initialize(\"\")";
_img_valorind1.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 1433;BA.debugLine="lbl_valorind2.Initialize(\"\")";
_lbl_valorind2.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 1434;BA.debugLine="img_valorind2.Initialize(\"\")";
_img_valorind2.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 1435;BA.debugLine="lbl_valorind3.Initialize(\"\")";
_lbl_valorind3.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 1436;BA.debugLine="img_valorind3.Initialize(\"\")";
_img_valorind3.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 1437;BA.debugLine="lbl_ind_pvm_3.Initialize(\"\")";
_lbl_ind_pvm_3.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 1438;BA.debugLine="img_ind_pvm_3.Initialize(\"\")";
_img_ind_pvm_3.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 1439;BA.debugLine="lbl_ind_pvm_4.Initialize(\"\")";
_lbl_ind_pvm_4.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 1440;BA.debugLine="img_ind_pvm_4.Initialize(\"\")";
_img_ind_pvm_4.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 1442;BA.debugLine="lbl_valorind10.Color = Colors.White";
_lbl_valorind10.setColor(anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 1443;BA.debugLine="lbl_valorind10.TextColor = Colors.Black";
_lbl_valorind10.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 1444;BA.debugLine="lbl_valorind9.Color = Colors.White";
_lbl_valorind9.setColor(anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 1445;BA.debugLine="lbl_valorind9.TextColor = Colors.Black";
_lbl_valorind9.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 1446;BA.debugLine="lbl_valorind5.Color = Colors.White";
_lbl_valorind5.setColor(anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 1447;BA.debugLine="lbl_valorind5.TextColor = Colors.Black";
_lbl_valorind5.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 1448;BA.debugLine="lbl_valorind6.Color = Colors.White";
_lbl_valorind6.setColor(anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 1449;BA.debugLine="lbl_valorind6.TextColor = Colors.Black";
_lbl_valorind6.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 1450;BA.debugLine="lbl_ind_pvm_1.Color = Colors.White";
_lbl_ind_pvm_1.setColor(anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 1451;BA.debugLine="lbl_ind_pvm_1.TextColor = Colors.Black";
_lbl_ind_pvm_1.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 1452;BA.debugLine="lbl_valorind7.Color = Colors.White";
_lbl_valorind7.setColor(anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 1453;BA.debugLine="lbl_valorind7.TextColor = Colors.Black";
_lbl_valorind7.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 1454;BA.debugLine="lbl_ind_pvm_2.Color = Colors.White";
_lbl_ind_pvm_2.setColor(anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 1455;BA.debugLine="lbl_ind_pvm_2.TextColor = Colors.Black";
_lbl_ind_pvm_2.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 1456;BA.debugLine="lbl_valorind4.Color = Colors.White";
_lbl_valorind4.setColor(anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 1457;BA.debugLine="lbl_valorind4.TextColor = Colors.Black";
_lbl_valorind4.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 1458;BA.debugLine="lbl_valorind1.Color = Colors.White";
_lbl_valorind1.setColor(anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 1459;BA.debugLine="lbl_valorind1.TextColor = Colors.Black";
_lbl_valorind1.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 1460;BA.debugLine="lbl_valorind2.Color = Colors.White";
_lbl_valorind2.setColor(anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 1461;BA.debugLine="lbl_valorind2.TextColor = Colors.Black";
_lbl_valorind2.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 1462;BA.debugLine="lbl_valorind3.Color = Colors.White";
_lbl_valorind3.setColor(anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 1463;BA.debugLine="lbl_valorind3.TextColor = Colors.Black";
_lbl_valorind3.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 1464;BA.debugLine="lbl_ind_pvm_3.Color = Colors.White";
_lbl_ind_pvm_3.setColor(anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 1465;BA.debugLine="lbl_ind_pvm_3.TextColor = Colors.Black";
_lbl_ind_pvm_3.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 1466;BA.debugLine="lbl_ind_pvm_4.Color = Colors.White";
_lbl_ind_pvm_4.setColor(anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 1467;BA.debugLine="lbl_ind_pvm_4.TextColor = Colors.Black";
_lbl_ind_pvm_4.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 1469;BA.debugLine="Dim orden As Int = 1";
_orden = (int) (1);
 //BA.debugLineNum = 1472;BA.debugLine="If valorind9 = 0 Then";
if ((mostCurrent._valorind9).equals(BA.NumberToString(0))) { 
 //BA.debugLineNum = 1473;BA.debugLine="lbl_valorind9.Text = \"Los ríos desbordados puede";
_lbl_valorind9.setText(BA.ObjectToCharSequence("Los ríos desbordados pueden generar inundaciones en pueblos y ciudades!"));
 //BA.debugLineNum = 1474;BA.debugLine="img_valorind9.Bitmap = LoadBitmapSample(File.Dir";
_img_valorind9.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"vinetaMM.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 1475;BA.debugLine="scrResultados.Panel.AddView(img_valorind9, 10dip";
mostCurrent._scrresultados.getPanel().AddView((android.view.View)(_img_valorind9.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10)),(int) (_orden*anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (60))-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (30))),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (25)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (25)));
 //BA.debugLineNum = 1476;BA.debugLine="scrResultados.Panel.AddView(lbl_valorind9, 50dip";
mostCurrent._scrresultados.getPanel().AddView((android.view.View)(_lbl_valorind9.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (50)),(int) (_orden*anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (60))-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (30))),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (70),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (60)));
 //BA.debugLineNum = 1477;BA.debugLine="scrResultados.Panel.Height = orden * 90dip";
mostCurrent._scrresultados.getPanel().setHeight((int) (_orden*anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (90))));
 //BA.debugLineNum = 1478;BA.debugLine="orden = orden +1";
_orden = (int) (_orden+1);
 }else if((mostCurrent._valorind9).equals(BA.NumberToString(10))) { 
 //BA.debugLineNum = 1480;BA.debugLine="lbl_valorind9.Text = \"El río está en su cauce, n";
_lbl_valorind9.setText(BA.ObjectToCharSequence("El río está en su cauce, no debería generar inundaciones en pueblos y ciudades"));
 //BA.debugLineNum = 1481;BA.debugLine="img_valorind9.Bitmap = LoadBitmapSample(File.Dir";
_img_valorind9.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"vinetaMB.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 1482;BA.debugLine="scrResultados.Panel.AddView(img_valorind9, 10dip";
mostCurrent._scrresultados.getPanel().AddView((android.view.View)(_img_valorind9.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10)),(int) (_orden*anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (60))-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (30))),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (25)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (25)));
 //BA.debugLineNum = 1483;BA.debugLine="scrResultados.Panel.AddView(lbl_valorind9, 50dip";
mostCurrent._scrresultados.getPanel().AddView((android.view.View)(_lbl_valorind9.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (50)),(int) (_orden*anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (60))-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (30))),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (70),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (60)));
 //BA.debugLineNum = 1484;BA.debugLine="scrResultados.Panel.Height = orden * 90dip";
mostCurrent._scrresultados.getPanel().setHeight((int) (_orden*anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (90))));
 //BA.debugLineNum = 1485;BA.debugLine="orden = orden +1";
_orden = (int) (_orden+1);
 };
 //BA.debugLineNum = 1489;BA.debugLine="If valorind10 = 0 Then";
if ((mostCurrent._valorind10).equals(BA.NumberToString(0))) { 
 //BA.debugLineNum = 1490;BA.debugLine="lbl_valorind10.Text = \"El río está canalizado o";
_lbl_valorind10.setText(BA.ObjectToCharSequence("El río está canalizado o entubado, no hay conexión entre el río y el agua subterránea"));
 //BA.debugLineNum = 1491;BA.debugLine="img_valorind10.Bitmap = LoadBitmapSample(File.Di";
_img_valorind10.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"vinetaMM.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 1492;BA.debugLine="scrResultados.Panel.AddView(img_valorind10, 10di";
mostCurrent._scrresultados.getPanel().AddView((android.view.View)(_img_valorind10.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10)),(int) (_orden*anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (60))-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (30))),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (25)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (25)));
 //BA.debugLineNum = 1493;BA.debugLine="scrResultados.Panel.AddView(lbl_valorind10, 50di";
mostCurrent._scrresultados.getPanel().AddView((android.view.View)(_lbl_valorind10.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (50)),(int) (_orden*anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (60))-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (30))),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (70),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (60)));
 //BA.debugLineNum = 1494;BA.debugLine="scrResultados.Panel.Height = orden * 90dip";
mostCurrent._scrresultados.getPanel().setHeight((int) (_orden*anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (90))));
 //BA.debugLineNum = 1495;BA.debugLine="orden = orden +1";
_orden = (int) (_orden+1);
 }else if((mostCurrent._valorind10).equals(BA.NumberToString(10))) { 
 //BA.debugLineNum = 1497;BA.debugLine="lbl_valorind10.Text = \"El río no está canalizado";
_lbl_valorind10.setText(BA.ObjectToCharSequence("El río no está canalizado ni entubado, hay conexión entre el río y el agua subterránea"));
 //BA.debugLineNum = 1498;BA.debugLine="img_valorind10.Bitmap = LoadBitmapSample(File.Di";
_img_valorind10.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"vinetaMB.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 1499;BA.debugLine="scrResultados.Panel.AddView(img_valorind10, 10di";
mostCurrent._scrresultados.getPanel().AddView((android.view.View)(_img_valorind10.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10)),(int) (_orden*anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (60))-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (30))),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (25)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (25)));
 //BA.debugLineNum = 1500;BA.debugLine="scrResultados.Panel.AddView(lbl_valorind10, 50di";
mostCurrent._scrresultados.getPanel().AddView((android.view.View)(_lbl_valorind10.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (50)),(int) (_orden*anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (60))-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (30))),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (70),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (60)));
 //BA.debugLineNum = 1501;BA.debugLine="scrResultados.Panel.Height = orden * 90dip";
mostCurrent._scrresultados.getPanel().setHeight((int) (_orden*anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (90))));
 //BA.debugLineNum = 1502;BA.debugLine="orden = orden +1";
_orden = (int) (_orden+1);
 };
 //BA.debugLineNum = 1505;BA.debugLine="If valorind5 = 0 Then";
if ((mostCurrent._valorind5).equals(BA.NumberToString(0))) { 
 //BA.debugLineNum = 1506;BA.debugLine="lbl_valorind5.Text = \"El color oscuro del agua p";
_lbl_valorind5.setText(BA.ObjectToCharSequence("El color oscuro del agua puede indicar una contaminación fuerte"));
 //BA.debugLineNum = 1507;BA.debugLine="img_valorind5.Bitmap = LoadBitmapSample(File.Dir";
_img_valorind5.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"vinetaMM.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 1508;BA.debugLine="scrResultados.Panel.AddView(img_valorind5, 10dip";
mostCurrent._scrresultados.getPanel().AddView((android.view.View)(_img_valorind5.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10)),(int) (_orden*anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (60))-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (30))),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (25)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (25)));
 //BA.debugLineNum = 1509;BA.debugLine="scrResultados.Panel.AddView(lbl_valorind5, 50dip";
mostCurrent._scrresultados.getPanel().AddView((android.view.View)(_lbl_valorind5.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (50)),(int) (_orden*anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (60))-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (30))),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (70),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (60)));
 //BA.debugLineNum = 1510;BA.debugLine="scrResultados.Panel.Height = orden * 90dip";
mostCurrent._scrresultados.getPanel().setHeight((int) (_orden*anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (90))));
 //BA.debugLineNum = 1511;BA.debugLine="orden = orden +1";
_orden = (int) (_orden+1);
 }else if((mostCurrent._valorind5).equals(BA.NumberToString(10))) { 
 //BA.debugLineNum = 1513;BA.debugLine="lbl_valorind5.Text = \"El color transparente o ma";
_lbl_valorind5.setText(BA.ObjectToCharSequence("El color transparente o marrón del agua en estos ríos es normal"));
 //BA.debugLineNum = 1514;BA.debugLine="img_valorind5.Bitmap = LoadBitmapSample(File.Dir";
_img_valorind5.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"vinetaMB.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 1515;BA.debugLine="scrResultados.Panel.AddView(img_valorind5, 10dip";
mostCurrent._scrresultados.getPanel().AddView((android.view.View)(_img_valorind5.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10)),(int) (_orden*anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (60))-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (30))),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (25)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (25)));
 //BA.debugLineNum = 1516;BA.debugLine="scrResultados.Panel.AddView(lbl_valorind5, 50dip";
mostCurrent._scrresultados.getPanel().AddView((android.view.View)(_lbl_valorind5.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (50)),(int) (_orden*anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (60))-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (30))),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (70),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (60)));
 //BA.debugLineNum = 1517;BA.debugLine="scrResultados.Panel.Height = orden * 90dip";
mostCurrent._scrresultados.getPanel().setHeight((int) (_orden*anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (90))));
 //BA.debugLineNum = 1518;BA.debugLine="orden = orden +1";
_orden = (int) (_orden+1);
 };
 //BA.debugLineNum = 1522;BA.debugLine="If valorind6 = 0 Then";
if ((mostCurrent._valorind6).equals(BA.NumberToString(0))) { 
 //BA.debugLineNum = 1523;BA.debugLine="lbl_valorind6.Text = \"El agua con olor feo puede";
_lbl_valorind6.setText(BA.ObjectToCharSequence("El agua con olor feo puede indicar una contaminación fuerte"));
 //BA.debugLineNum = 1524;BA.debugLine="img_valorind6.Bitmap = LoadBitmapSample(File.Dir";
_img_valorind6.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"vinetaMM.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 1525;BA.debugLine="scrResultados.Panel.AddView(img_valorind6, 10dip";
mostCurrent._scrresultados.getPanel().AddView((android.view.View)(_img_valorind6.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10)),(int) (_orden*anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (60))-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (30))),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (25)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (25)));
 //BA.debugLineNum = 1526;BA.debugLine="scrResultados.Panel.AddView(lbl_valorind6, 50dip";
mostCurrent._scrresultados.getPanel().AddView((android.view.View)(_lbl_valorind6.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (50)),(int) (_orden*anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (60))-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (30))),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (70),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (60)));
 //BA.debugLineNum = 1527;BA.debugLine="scrResultados.Panel.Height = orden * 90dip";
mostCurrent._scrresultados.getPanel().setHeight((int) (_orden*anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (90))));
 //BA.debugLineNum = 1528;BA.debugLine="orden = orden +1";
_orden = (int) (_orden+1);
 }else if((mostCurrent._valorind6).equals(BA.NumberToString(10))) { 
 //BA.debugLineNum = 1530;BA.debugLine="lbl_valorind6.Text = \"El agua tiene buen olor\"";
_lbl_valorind6.setText(BA.ObjectToCharSequence("El agua tiene buen olor"));
 //BA.debugLineNum = 1531;BA.debugLine="img_valorind6.Bitmap = LoadBitmapSample(File.Dir";
_img_valorind6.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"vinetaMB.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 1532;BA.debugLine="scrResultados.Panel.AddView(img_valorind6, 10dip";
mostCurrent._scrresultados.getPanel().AddView((android.view.View)(_img_valorind6.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10)),(int) (_orden*anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (60))-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (30))),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (25)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (25)));
 //BA.debugLineNum = 1533;BA.debugLine="scrResultados.Panel.AddView(lbl_valorind6, 50dip";
mostCurrent._scrresultados.getPanel().AddView((android.view.View)(_lbl_valorind6.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (50)),(int) (_orden*anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (60))-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (30))),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (70),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (60)));
 //BA.debugLineNum = 1534;BA.debugLine="scrResultados.Panel.Height = orden * 90dip";
mostCurrent._scrresultados.getPanel().setHeight((int) (_orden*anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (90))));
 //BA.debugLineNum = 1535;BA.debugLine="orden = orden +1";
_orden = (int) (_orden+1);
 };
 //BA.debugLineNum = 1538;BA.debugLine="If ind_pvm_1 = 0 Then";
if ((mostCurrent._ind_pvm_1).equals(BA.NumberToString(0))) { 
 //BA.debugLineNum = 1539;BA.debugLine="lbl_ind_pvm_1.Text = \"La presencia de espumas o";
_lbl_ind_pvm_1.setText(BA.ObjectToCharSequence("La presencia de espumas o aceites son indicadores de contaminación"));
 //BA.debugLineNum = 1540;BA.debugLine="img_ind_pvm_1.Bitmap = LoadBitmapSample(File.Dir";
_img_ind_pvm_1.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"vinetaMM.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 1541;BA.debugLine="scrResultados.Panel.AddView(img_ind_pvm_1, 10dip";
mostCurrent._scrresultados.getPanel().AddView((android.view.View)(_img_ind_pvm_1.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10)),(int) (_orden*anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (60))-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (30))),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (25)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (25)));
 //BA.debugLineNum = 1542;BA.debugLine="scrResultados.Panel.AddView(lbl_ind_pvm_1, 50dip";
mostCurrent._scrresultados.getPanel().AddView((android.view.View)(_lbl_ind_pvm_1.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (50)),(int) (_orden*anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (60))-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (30))),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (70),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (60)));
 //BA.debugLineNum = 1543;BA.debugLine="scrResultados.Panel.Height = orden * 90dip";
mostCurrent._scrresultados.getPanel().setHeight((int) (_orden*anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (90))));
 //BA.debugLineNum = 1544;BA.debugLine="orden = orden +1";
_orden = (int) (_orden+1);
 }else if((mostCurrent._ind_pvm_1).equals(BA.NumberToString(10))) { 
 //BA.debugLineNum = 1546;BA.debugLine="lbl_ind_pvm_1.Text = \"No hay espumas o aceites e";
_lbl_ind_pvm_1.setText(BA.ObjectToCharSequence("No hay espumas o aceites en el agua"));
 //BA.debugLineNum = 1547;BA.debugLine="img_ind_pvm_1.Bitmap = LoadBitmapSample(File.Dir";
_img_ind_pvm_1.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"vinetaMB.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 1548;BA.debugLine="scrResultados.Panel.AddView(img_ind_pvm_1, 10dip";
mostCurrent._scrresultados.getPanel().AddView((android.view.View)(_img_ind_pvm_1.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10)),(int) (_orden*anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (60))-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (30))),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (25)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (25)));
 //BA.debugLineNum = 1549;BA.debugLine="scrResultados.Panel.AddView(lbl_ind_pvm_1, 50dip";
mostCurrent._scrresultados.getPanel().AddView((android.view.View)(_lbl_ind_pvm_1.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (50)),(int) (_orden*anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (60))-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (30))),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (70),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (60)));
 //BA.debugLineNum = 1550;BA.debugLine="scrResultados.Panel.Height = orden * 90dip";
mostCurrent._scrresultados.getPanel().setHeight((int) (_orden*anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (90))));
 //BA.debugLineNum = 1551;BA.debugLine="orden = orden +1";
_orden = (int) (_orden+1);
 };
 //BA.debugLineNum = 1555;BA.debugLine="If valorind7 >= 0 And valorind7 <= 5 Then";
if ((double)(Double.parseDouble(mostCurrent._valorind7))>=0 && (double)(Double.parseDouble(mostCurrent._valorind7))<=5) { 
 //BA.debugLineNum = 1556;BA.debugLine="lbl_valorind7.Text = \"Hay demasiada basura!\"";
_lbl_valorind7.setText(BA.ObjectToCharSequence("Hay demasiada basura!"));
 //BA.debugLineNum = 1557;BA.debugLine="img_valorind7.Bitmap = LoadBitmapSample(File.Dir";
_img_valorind7.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"vinetaMM.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 1558;BA.debugLine="scrResultados.Panel.AddView(img_valorind7, 10dip";
mostCurrent._scrresultados.getPanel().AddView((android.view.View)(_img_valorind7.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10)),(int) (_orden*anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (60))-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (30))),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (25)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (25)));
 //BA.debugLineNum = 1559;BA.debugLine="scrResultados.Panel.AddView(lbl_valorind7, 50dip";
mostCurrent._scrresultados.getPanel().AddView((android.view.View)(_lbl_valorind7.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (50)),(int) (_orden*anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (60))-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (30))),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (70),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (60)));
 //BA.debugLineNum = 1560;BA.debugLine="scrResultados.Panel.Height = orden * 90dip";
mostCurrent._scrresultados.getPanel().setHeight((int) (_orden*anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (90))));
 //BA.debugLineNum = 1561;BA.debugLine="orden = orden +1";
_orden = (int) (_orden+1);
 }else if((double)(Double.parseDouble(mostCurrent._valorind7))>5 && (double)(Double.parseDouble(mostCurrent._valorind7))<=8) { 
 //BA.debugLineNum = 1563;BA.debugLine="lbl_valorind7.Text = \"Hay algo de basura... ¡no";
_lbl_valorind7.setText(BA.ObjectToCharSequence("Hay algo de basura... ¡no es muy bueno!"));
 //BA.debugLineNum = 1564;BA.debugLine="img_valorind7.Bitmap = LoadBitmapSample(File.Dir";
_img_valorind7.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"vinetaR.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 1565;BA.debugLine="scrResultados.Panel.AddView(img_valorind7, 10dip";
mostCurrent._scrresultados.getPanel().AddView((android.view.View)(_img_valorind7.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10)),(int) (_orden*anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (60))-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (30))),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (25)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (25)));
 //BA.debugLineNum = 1566;BA.debugLine="scrResultados.Panel.AddView(lbl_valorind7, 50dip";
mostCurrent._scrresultados.getPanel().AddView((android.view.View)(_lbl_valorind7.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (50)),(int) (_orden*anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (60))-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (30))),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (70),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (60)));
 //BA.debugLineNum = 1567;BA.debugLine="scrResultados.Panel.Height = orden * 90dip";
mostCurrent._scrresultados.getPanel().setHeight((int) (_orden*anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (90))));
 //BA.debugLineNum = 1568;BA.debugLine="orden = orden +1";
_orden = (int) (_orden+1);
 }else if((mostCurrent._valorind7).equals(BA.NumberToString(10))) { 
 //BA.debugLineNum = 1570;BA.debugLine="lbl_valorind7.Text = \"No hay basura, ¡qué bien!\"";
_lbl_valorind7.setText(BA.ObjectToCharSequence("No hay basura, ¡qué bien!"));
 //BA.debugLineNum = 1571;BA.debugLine="img_valorind7.Bitmap = LoadBitmapSample(File.Dir";
_img_valorind7.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"vinetaMB.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 1572;BA.debugLine="scrResultados.Panel.AddView(img_valorind7, 10dip";
mostCurrent._scrresultados.getPanel().AddView((android.view.View)(_img_valorind7.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10)),(int) (_orden*anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (60))-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (30))),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (25)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (25)));
 //BA.debugLineNum = 1573;BA.debugLine="scrResultados.Panel.AddView(lbl_valorind7, 50dip";
mostCurrent._scrresultados.getPanel().AddView((android.view.View)(_lbl_valorind7.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (50)),(int) (_orden*anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (60))-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (30))),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (70),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (60)));
 //BA.debugLineNum = 1574;BA.debugLine="scrResultados.Panel.Height = orden * 90dip";
mostCurrent._scrresultados.getPanel().setHeight((int) (_orden*anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (90))));
 //BA.debugLineNum = 1575;BA.debugLine="orden = orden +1";
_orden = (int) (_orden+1);
 };
 //BA.debugLineNum = 1579;BA.debugLine="If ind_pvm_2 = 0 Then";
if ((mostCurrent._ind_pvm_2).equals(BA.NumberToString(0))) { 
 //BA.debugLineNum = 1580;BA.debugLine="lbl_ind_pvm_2.Text = \"La ausencia de juncos o to";
_lbl_ind_pvm_2.setText(BA.ObjectToCharSequence("La ausencia de juncos o totoras puede indicar degradación del hábitat"));
 //BA.debugLineNum = 1581;BA.debugLine="img_ind_pvm_2.Bitmap = LoadBitmapSample(File.Dir";
_img_ind_pvm_2.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"vinetaMM.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 1582;BA.debugLine="scrResultados.Panel.AddView(img_ind_pvm_2, 10dip";
mostCurrent._scrresultados.getPanel().AddView((android.view.View)(_img_ind_pvm_2.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10)),(int) (_orden*anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (60))-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (30))),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (25)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (25)));
 //BA.debugLineNum = 1583;BA.debugLine="scrResultados.Panel.AddView(lbl_ind_pvm_2, 50dip";
mostCurrent._scrresultados.getPanel().AddView((android.view.View)(_lbl_ind_pvm_2.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (50)),(int) (_orden*anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (60))-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (30))),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (70),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (60)));
 //BA.debugLineNum = 1584;BA.debugLine="scrResultados.Panel.Height = orden * 90dip";
mostCurrent._scrresultados.getPanel().setHeight((int) (_orden*anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (90))));
 //BA.debugLineNum = 1585;BA.debugLine="orden = orden +1";
_orden = (int) (_orden+1);
 }else if((mostCurrent._ind_pvm_2).equals(BA.NumberToString(8))) { 
 //BA.debugLineNum = 1587;BA.debugLine="lbl_ind_pvm_2.Text = \"La presencia de algunos ju";
_lbl_ind_pvm_2.setText(BA.ObjectToCharSequence("La presencia de algunos juncos o totoras provee de un buen hábitat para los animales"));
 //BA.debugLineNum = 1588;BA.debugLine="img_ind_pvm_2.Bitmap = LoadBitmapSample(File.Dir";
_img_ind_pvm_2.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"vinetaR.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 1589;BA.debugLine="scrResultados.Panel.AddView(img_ind_pvm_2, 10dip";
mostCurrent._scrresultados.getPanel().AddView((android.view.View)(_img_ind_pvm_2.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10)),(int) (_orden*anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (60))-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (30))),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (25)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (25)));
 //BA.debugLineNum = 1590;BA.debugLine="scrResultados.Panel.AddView(lbl_ind_pvm_2, 50dip";
mostCurrent._scrresultados.getPanel().AddView((android.view.View)(_lbl_ind_pvm_2.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (50)),(int) (_orden*anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (60))-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (30))),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (70),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (60)));
 //BA.debugLineNum = 1591;BA.debugLine="scrResultados.Panel.Height = orden * 90dip";
mostCurrent._scrresultados.getPanel().setHeight((int) (_orden*anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (90))));
 //BA.debugLineNum = 1592;BA.debugLine="orden = orden +1";
_orden = (int) (_orden+1);
 }else if((mostCurrent._ind_pvm_2).equals(BA.NumberToString(10))) { 
 //BA.debugLineNum = 1594;BA.debugLine="lbl_ind_pvm_2.Text = \"La presencia de juncos o t";
_lbl_ind_pvm_2.setText(BA.ObjectToCharSequence("La presencia de juncos o totoras provee de un buen hábitat para los animales"));
 //BA.debugLineNum = 1595;BA.debugLine="img_ind_pvm_2.Bitmap = LoadBitmapSample(File.Dir";
_img_ind_pvm_2.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"vinetaMB.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 1596;BA.debugLine="scrResultados.Panel.AddView(img_ind_pvm_2, 10dip";
mostCurrent._scrresultados.getPanel().AddView((android.view.View)(_img_ind_pvm_2.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10)),(int) (_orden*anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (60))-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (30))),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (25)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (25)));
 //BA.debugLineNum = 1597;BA.debugLine="scrResultados.Panel.AddView(lbl_ind_pvm_2, 50dip";
mostCurrent._scrresultados.getPanel().AddView((android.view.View)(_lbl_ind_pvm_2.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (50)),(int) (_orden*anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (60))-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (30))),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (70),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (60)));
 //BA.debugLineNum = 1598;BA.debugLine="scrResultados.Panel.Height = orden * 90dip";
mostCurrent._scrresultados.getPanel().setHeight((int) (_orden*anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (90))));
 //BA.debugLineNum = 1599;BA.debugLine="orden = orden +1";
_orden = (int) (_orden+1);
 };
 //BA.debugLineNum = 1603;BA.debugLine="If valorind4 = 0 Then";
if ((mostCurrent._valorind4).equals(BA.NumberToString(0))) { 
 //BA.debugLineNum = 1604;BA.debugLine="lbl_valorind4.Text = \"La aplantas acuáticas ayud";
_lbl_valorind4.setText(BA.ObjectToCharSequence("La aplantas acuáticas ayudan a depurar el agua"));
 //BA.debugLineNum = 1605;BA.debugLine="img_valorind4.Bitmap = LoadBitmapSample(File.Dir";
_img_valorind4.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"vinetaMM.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 1606;BA.debugLine="scrResultados.Panel.AddView(img_valorind4, 10dip";
mostCurrent._scrresultados.getPanel().AddView((android.view.View)(_img_valorind4.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10)),(int) (_orden*anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (60))-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (30))),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (25)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (25)));
 //BA.debugLineNum = 1607;BA.debugLine="scrResultados.Panel.AddView(lbl_valorind4, 50dip";
mostCurrent._scrresultados.getPanel().AddView((android.view.View)(_lbl_valorind4.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (50)),(int) (_orden*anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (60))-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (30))),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (70),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (60)));
 //BA.debugLineNum = 1608;BA.debugLine="scrResultados.Panel.Height = orden * 90dip";
mostCurrent._scrresultados.getPanel().setHeight((int) (_orden*anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (90))));
 //BA.debugLineNum = 1609;BA.debugLine="orden = orden +1";
_orden = (int) (_orden+1);
 }else if((mostCurrent._valorind4).equals(BA.NumberToString(8))) { 
 //BA.debugLineNum = 1611;BA.debugLine="lbl_valorind4.Text = \"La presencia de plantas ac";
_lbl_valorind4.setText(BA.ObjectToCharSequence("La presencia de plantas acuáticas provee de un buen hábitat para los animales"));
 //BA.debugLineNum = 1612;BA.debugLine="img_valorind4.Bitmap = LoadBitmapSample(File.Dir";
_img_valorind4.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"vinetaR.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 1613;BA.debugLine="scrResultados.Panel.AddView(img_valorind4, 10dip";
mostCurrent._scrresultados.getPanel().AddView((android.view.View)(_img_valorind4.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10)),(int) (_orden*anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (60))-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (30))),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (25)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (25)));
 //BA.debugLineNum = 1614;BA.debugLine="scrResultados.Panel.AddView(lbl_valorind4, 50dip";
mostCurrent._scrresultados.getPanel().AddView((android.view.View)(_lbl_valorind4.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (50)),(int) (_orden*anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (60))-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (30))),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (70),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (60)));
 //BA.debugLineNum = 1615;BA.debugLine="scrResultados.Panel.Height = orden * 90dip";
mostCurrent._scrresultados.getPanel().setHeight((int) (_orden*anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (90))));
 //BA.debugLineNum = 1616;BA.debugLine="orden = orden +1";
_orden = (int) (_orden+1);
 }else if((mostCurrent._valorind4).equals(BA.NumberToString(10))) { 
 //BA.debugLineNum = 1618;BA.debugLine="lbl_valorind4.Text = \"La presencia de plantas ac";
_lbl_valorind4.setText(BA.ObjectToCharSequence("La presencia de plantas acuáticas de un buen hábitat para los animales"));
 //BA.debugLineNum = 1619;BA.debugLine="img_valorind4.Bitmap = LoadBitmapSample(File.Dir";
_img_valorind4.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"vinetaMB.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 1620;BA.debugLine="scrResultados.Panel.AddView(img_valorind4, 10dip";
mostCurrent._scrresultados.getPanel().AddView((android.view.View)(_img_valorind4.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10)),(int) (_orden*anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (60))-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (30))),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (25)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (25)));
 //BA.debugLineNum = 1621;BA.debugLine="scrResultados.Panel.AddView(lbl_valorind4, 50dip";
mostCurrent._scrresultados.getPanel().AddView((android.view.View)(_lbl_valorind4.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (50)),(int) (_orden*anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (60))-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (30))),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (70),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (60)));
 //BA.debugLineNum = 1622;BA.debugLine="scrResultados.Panel.Height = orden * 90dip";
mostCurrent._scrresultados.getPanel().setHeight((int) (_orden*anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (90))));
 //BA.debugLineNum = 1623;BA.debugLine="orden = orden +1";
_orden = (int) (_orden+1);
 };
 //BA.debugLineNum = 1626;BA.debugLine="If valorind1 >= 0 And valorind1 <= 5  Then";
if ((double)(Double.parseDouble(mostCurrent._valorind1))>=0 && (double)(Double.parseDouble(mostCurrent._valorind1))<=5) { 
 //BA.debugLineNum = 1627;BA.debugLine="lbl_valorind1.Text = \"El uso intensivo del suelo";
_lbl_valorind1.setText(BA.ObjectToCharSequence("El uso intensivo del suelo impacta directamente en los arroyos"));
 //BA.debugLineNum = 1628;BA.debugLine="img_valorind1.Bitmap = LoadBitmapSample(File.Dir";
_img_valorind1.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"vinetaMM.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 1629;BA.debugLine="scrResultados.Panel.AddView(img_valorind1, 10dip";
mostCurrent._scrresultados.getPanel().AddView((android.view.View)(_img_valorind1.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10)),(int) (_orden*anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (60))-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (30))),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (25)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (25)));
 //BA.debugLineNum = 1630;BA.debugLine="scrResultados.Panel.AddView(lbl_valorind1, 50dip";
mostCurrent._scrresultados.getPanel().AddView((android.view.View)(_lbl_valorind1.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (50)),(int) (_orden*anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (60))-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (30))),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (70),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (60)));
 //BA.debugLineNum = 1631;BA.debugLine="scrResultados.Panel.Height = orden * 90dip";
mostCurrent._scrresultados.getPanel().setHeight((int) (_orden*anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (90))));
 //BA.debugLineNum = 1632;BA.debugLine="orden = orden +1";
_orden = (int) (_orden+1);
 }else if((double)(Double.parseDouble(mostCurrent._valorind1))>5 && (double)(Double.parseDouble(mostCurrent._valorind1))<=8) { 
 //BA.debugLineNum = 1634;BA.debugLine="lbl_valorind1.Text = \"Hay ciertos usos del suelo";
_lbl_valorind1.setText(BA.ObjectToCharSequence("Hay ciertos usos del suelo que no tienen tanto impacto sobre los arroyos"));
 //BA.debugLineNum = 1635;BA.debugLine="img_valorind1.Bitmap = LoadBitmapSample(File.Dir";
_img_valorind1.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"vinetaR.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 1636;BA.debugLine="scrResultados.Panel.AddView(img_valorind1, 10dip";
mostCurrent._scrresultados.getPanel().AddView((android.view.View)(_img_valorind1.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10)),(int) (_orden*anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (60))-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (30))),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (25)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (25)));
 //BA.debugLineNum = 1637;BA.debugLine="scrResultados.Panel.AddView(lbl_valorind1, 50dip";
mostCurrent._scrresultados.getPanel().AddView((android.view.View)(_lbl_valorind1.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (50)),(int) (_orden*anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (60))-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (30))),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (70),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (60)));
 //BA.debugLineNum = 1638;BA.debugLine="scrResultados.Panel.Height = orden * 90dip";
mostCurrent._scrresultados.getPanel().setHeight((int) (_orden*anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (90))));
 //BA.debugLineNum = 1639;BA.debugLine="orden = orden +1";
_orden = (int) (_orden+1);
 }else if((mostCurrent._valorind1).equals(BA.NumberToString(10))) { 
 //BA.debugLineNum = 1641;BA.debugLine="lbl_valorind1.Text = \"Reservas o montes naturale";
_lbl_valorind1.setText(BA.ObjectToCharSequence("Reservas o montes naturales generan arroyos naturales y sanos"));
 //BA.debugLineNum = 1642;BA.debugLine="img_valorind1.Bitmap = LoadBitmapSample(File.Dir";
_img_valorind1.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"vinetaMB.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 1643;BA.debugLine="scrResultados.Panel.AddView(img_valorind1, 10dip";
mostCurrent._scrresultados.getPanel().AddView((android.view.View)(_img_valorind1.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10)),(int) (_orden*anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (60))-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (30))),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (25)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (25)));
 //BA.debugLineNum = 1644;BA.debugLine="scrResultados.Panel.AddView(lbl_valorind1, 50dip";
mostCurrent._scrresultados.getPanel().AddView((android.view.View)(_lbl_valorind1.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (50)),(int) (_orden*anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (60))-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (30))),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (70),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (60)));
 //BA.debugLineNum = 1645;BA.debugLine="scrResultados.Panel.Height = orden * 90dip";
mostCurrent._scrresultados.getPanel().setHeight((int) (_orden*anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (90))));
 //BA.debugLineNum = 1646;BA.debugLine="orden = orden +1";
_orden = (int) (_orden+1);
 };
 //BA.debugLineNum = 1650;BA.debugLine="If valorind2 = 0 Then";
if ((mostCurrent._valorind2).equals(BA.NumberToString(0))) { 
 //BA.debugLineNum = 1651;BA.debugLine="lbl_valorind2.Text = \"El ganado impacta fuerteme";
_lbl_valorind2.setText(BA.ObjectToCharSequence("El ganado impacta fuertemente en los arroyos, por pisoteo y aportando nutrientes"));
 //BA.debugLineNum = 1652;BA.debugLine="img_valorind2.Bitmap = LoadBitmapSample(File.Dir";
_img_valorind2.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"vinetaMM.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 1653;BA.debugLine="scrResultados.Panel.AddView(img_valorind2, 10dip";
mostCurrent._scrresultados.getPanel().AddView((android.view.View)(_img_valorind2.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10)),(int) (_orden*anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (60))-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (30))),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (25)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (25)));
 //BA.debugLineNum = 1654;BA.debugLine="scrResultados.Panel.AddView(lbl_valorind2, 50dip";
mostCurrent._scrresultados.getPanel().AddView((android.view.View)(_lbl_valorind2.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (50)),(int) (_orden*anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (60))-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (30))),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (70),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (60)));
 //BA.debugLineNum = 1655;BA.debugLine="scrResultados.Panel.Height = orden * 90dip";
mostCurrent._scrresultados.getPanel().setHeight((int) (_orden*anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (90))));
 //BA.debugLineNum = 1656;BA.debugLine="orden = orden +1";
_orden = (int) (_orden+1);
 }else if((mostCurrent._valorind2).equals(BA.NumberToString(10))) { 
 //BA.debugLineNum = 1658;BA.debugLine="lbl_valorind2.Text = \"No hay ganado intensivo\"";
_lbl_valorind2.setText(BA.ObjectToCharSequence("No hay ganado intensivo"));
 //BA.debugLineNum = 1659;BA.debugLine="img_valorind2.Bitmap = LoadBitmapSample(File.Dir";
_img_valorind2.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"vinetaMB.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 1660;BA.debugLine="scrResultados.Panel.AddView(img_valorind2, 10dip";
mostCurrent._scrresultados.getPanel().AddView((android.view.View)(_img_valorind2.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10)),(int) (_orden*anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (60))-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (30))),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (25)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (25)));
 //BA.debugLineNum = 1661;BA.debugLine="scrResultados.Panel.AddView(lbl_valorind2, 50dip";
mostCurrent._scrresultados.getPanel().AddView((android.view.View)(_lbl_valorind2.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (50)),(int) (_orden*anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (60))-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (30))),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (70),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (60)));
 //BA.debugLineNum = 1662;BA.debugLine="scrResultados.Panel.Height = orden * 90dip";
mostCurrent._scrresultados.getPanel().setHeight((int) (_orden*anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (90))));
 //BA.debugLineNum = 1663;BA.debugLine="orden = orden +1";
_orden = (int) (_orden+1);
 };
 //BA.debugLineNum = 1667;BA.debugLine="If valorind3 >= 0 And valorind3 <= 5  Then";
if ((double)(Double.parseDouble(mostCurrent._valorind3))>=0 && (double)(Double.parseDouble(mostCurrent._valorind3))<=5) { 
 //BA.debugLineNum = 1668;BA.debugLine="lbl_valorind3.Text = \"Hay una pobre vegetación d";
_lbl_valorind3.setText(BA.ObjectToCharSequence("Hay una pobre vegetación de ribera"));
 //BA.debugLineNum = 1669;BA.debugLine="img_valorind3.Bitmap = LoadBitmapSample(File.Dir";
_img_valorind3.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"vinetaMM.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 1670;BA.debugLine="scrResultados.Panel.AddView(img_valorind3, 10dip";
mostCurrent._scrresultados.getPanel().AddView((android.view.View)(_img_valorind3.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10)),(int) (_orden*anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (60))-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (30))),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (25)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (25)));
 //BA.debugLineNum = 1671;BA.debugLine="scrResultados.Panel.AddView(lbl_valorind3, 50dip";
mostCurrent._scrresultados.getPanel().AddView((android.view.View)(_lbl_valorind3.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (50)),(int) (_orden*anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (60))-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (30))),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (70),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (60)));
 //BA.debugLineNum = 1672;BA.debugLine="scrResultados.Panel.Height = orden * 90dip";
mostCurrent._scrresultados.getPanel().setHeight((int) (_orden*anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (90))));
 //BA.debugLineNum = 1673;BA.debugLine="orden = orden +1";
_orden = (int) (_orden+1);
 }else if((double)(Double.parseDouble(mostCurrent._valorind3))>5 && (double)(Double.parseDouble(mostCurrent._valorind3))<=8) { 
 //BA.debugLineNum = 1675;BA.debugLine="lbl_valorind3.Text = \"Hay una moderada vegetació";
_lbl_valorind3.setText(BA.ObjectToCharSequence("Hay una moderada vegetación de ribera"));
 //BA.debugLineNum = 1676;BA.debugLine="img_valorind3.Bitmap = LoadBitmapSample(File.Dir";
_img_valorind3.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"vinetaR.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 1677;BA.debugLine="scrResultados.Panel.AddView(img_valorind3, 10dip";
mostCurrent._scrresultados.getPanel().AddView((android.view.View)(_img_valorind3.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10)),(int) (_orden*anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (60))-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (30))),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (25)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (25)));
 //BA.debugLineNum = 1678;BA.debugLine="scrResultados.Panel.AddView(lbl_valorind3, 50dip";
mostCurrent._scrresultados.getPanel().AddView((android.view.View)(_lbl_valorind3.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (50)),(int) (_orden*anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (60))-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (30))),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (70),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (60)));
 //BA.debugLineNum = 1679;BA.debugLine="scrResultados.Panel.Height = orden * 90dip";
mostCurrent._scrresultados.getPanel().setHeight((int) (_orden*anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (90))));
 //BA.debugLineNum = 1680;BA.debugLine="orden = orden +1";
_orden = (int) (_orden+1);
 }else if((double)(Double.parseDouble(mostCurrent._valorind3))>8) { 
 //BA.debugLineNum = 1682;BA.debugLine="lbl_valorind3.Text = \"Hay una buen desarrollo de";
_lbl_valorind3.setText(BA.ObjectToCharSequence("Hay una buen desarrollo de vegetación de ribera"));
 //BA.debugLineNum = 1683;BA.debugLine="img_valorind3.Bitmap = LoadBitmapSample(File.Dir";
_img_valorind3.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"vinetaMB.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 1684;BA.debugLine="scrResultados.Panel.AddView(img_valorind3, 10dip";
mostCurrent._scrresultados.getPanel().AddView((android.view.View)(_img_valorind3.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10)),(int) (_orden*anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (60))-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (30))),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (25)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (25)));
 //BA.debugLineNum = 1685;BA.debugLine="scrResultados.Panel.AddView(lbl_valorind3, 50dip";
mostCurrent._scrresultados.getPanel().AddView((android.view.View)(_lbl_valorind3.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (50)),(int) (_orden*anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (60))-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (30))),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (70),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (60)));
 //BA.debugLineNum = 1686;BA.debugLine="scrResultados.Panel.Height = orden * 90dip";
mostCurrent._scrresultados.getPanel().setHeight((int) (_orden*anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (90))));
 //BA.debugLineNum = 1687;BA.debugLine="orden = orden +1";
_orden = (int) (_orden+1);
 };
 //BA.debugLineNum = 1690;BA.debugLine="If ind_pvm_3 < 10 Then";
if ((double)(Double.parseDouble(mostCurrent._ind_pvm_3))<10) { 
 //BA.debugLineNum = 1691;BA.debugLine="lbl_ind_pvm_3.Text = \"La acacia negra es una esp";
_lbl_ind_pvm_3.setText(BA.ObjectToCharSequence("La acacia negra es una especie altamente invasora!"));
 //BA.debugLineNum = 1692;BA.debugLine="img_ind_pvm_3.Bitmap = LoadBitmapSample(File.Dir";
_img_ind_pvm_3.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"vinetaMM.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 1693;BA.debugLine="scrResultados.Panel.AddView(img_ind_pvm_3, 10dip";
mostCurrent._scrresultados.getPanel().AddView((android.view.View)(_img_ind_pvm_3.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10)),(int) (_orden*anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (60))-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (30))),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (25)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (25)));
 //BA.debugLineNum = 1694;BA.debugLine="scrResultados.Panel.AddView(lbl_ind_pvm_3, 50dip";
mostCurrent._scrresultados.getPanel().AddView((android.view.View)(_lbl_ind_pvm_3.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (50)),(int) (_orden*anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (60))-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (30))),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (70),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (60)));
 //BA.debugLineNum = 1695;BA.debugLine="scrResultados.Panel.Height = orden * 90dip";
mostCurrent._scrresultados.getPanel().setHeight((int) (_orden*anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (90))));
 //BA.debugLineNum = 1696;BA.debugLine="orden = orden +1";
_orden = (int) (_orden+1);
 }else {
 //BA.debugLineNum = 1698;BA.debugLine="lbl_ind_pvm_3.Text = \"No hay acacia negra, una e";
_lbl_ind_pvm_3.setText(BA.ObjectToCharSequence("No hay acacia negra, una especie altamente invasora"));
 //BA.debugLineNum = 1699;BA.debugLine="img_ind_pvm_3.Bitmap = LoadBitmapSample(File.Dir";
_img_ind_pvm_3.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"vinetaMB.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 1700;BA.debugLine="scrResultados.Panel.AddView(img_ind_pvm_3, 10dip";
mostCurrent._scrresultados.getPanel().AddView((android.view.View)(_img_ind_pvm_3.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10)),(int) (_orden*anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (60))-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (30))),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (25)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (25)));
 //BA.debugLineNum = 1701;BA.debugLine="scrResultados.Panel.AddView(lbl_ind_pvm_3, 50dip";
mostCurrent._scrresultados.getPanel().AddView((android.view.View)(_lbl_ind_pvm_3.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (50)),(int) (_orden*anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (60))-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (30))),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (70),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (60)));
 //BA.debugLineNum = 1702;BA.debugLine="scrResultados.Panel.Height = orden * 90dip";
mostCurrent._scrresultados.getPanel().setHeight((int) (_orden*anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (90))));
 //BA.debugLineNum = 1703;BA.debugLine="orden = orden +1";
_orden = (int) (_orden+1);
 };
 //BA.debugLineNum = 1706;BA.debugLine="If ind_pvm_4 < 10 Then";
if ((double)(Double.parseDouble(mostCurrent._ind_pvm_4))<10) { 
 //BA.debugLineNum = 1707;BA.debugLine="lbl_ind_pvm_4.Text = \"Hay ligustros, una especie";
_lbl_ind_pvm_4.setText(BA.ObjectToCharSequence("Hay ligustros, una especie altamente invasora!"));
 //BA.debugLineNum = 1708;BA.debugLine="img_ind_pvm_4.Bitmap = LoadBitmapSample(File.Dir";
_img_ind_pvm_4.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"vinetaMM.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 1709;BA.debugLine="scrResultados.Panel.AddView(img_ind_pvm_4, 10dip";
mostCurrent._scrresultados.getPanel().AddView((android.view.View)(_img_ind_pvm_4.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10)),(int) (_orden*anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (60))-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (30))),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (25)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (25)));
 //BA.debugLineNum = 1710;BA.debugLine="scrResultados.Panel.AddView(lbl_ind_pvm_4, 50dip";
mostCurrent._scrresultados.getPanel().AddView((android.view.View)(_lbl_ind_pvm_4.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (50)),(int) (_orden*anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (60))-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (30))),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (70),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (60)));
 //BA.debugLineNum = 1711;BA.debugLine="scrResultados.Panel.Height = orden * 90dip";
mostCurrent._scrresultados.getPanel().setHeight((int) (_orden*anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (90))));
 //BA.debugLineNum = 1712;BA.debugLine="orden = orden +1";
_orden = (int) (_orden+1);
 }else {
 //BA.debugLineNum = 1714;BA.debugLine="lbl_ind_pvm_4.Text = \"No hay ligustros, una espe";
_lbl_ind_pvm_4.setText(BA.ObjectToCharSequence("No hay ligustros, una especie altamente invasora"));
 //BA.debugLineNum = 1715;BA.debugLine="img_ind_pvm_4.Bitmap = LoadBitmapSample(File.Dir";
_img_ind_pvm_4.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"vinetaMB.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 1716;BA.debugLine="scrResultados.Panel.AddView(img_ind_pvm_4, 10dip";
mostCurrent._scrresultados.getPanel().AddView((android.view.View)(_img_ind_pvm_4.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10)),(int) (_orden*anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (60))-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (30))),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (25)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (25)));
 //BA.debugLineNum = 1717;BA.debugLine="scrResultados.Panel.AddView(lbl_ind_pvm_4, 50dip";
mostCurrent._scrresultados.getPanel().AddView((android.view.View)(_lbl_ind_pvm_4.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (50)),(int) (_orden*anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (60))-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (30))),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (70),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (60)));
 //BA.debugLineNum = 1718;BA.debugLine="scrResultados.Panel.Height = orden * 90dip";
mostCurrent._scrresultados.getPanel().setHeight((int) (_orden*anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (90))));
 //BA.debugLineNum = 1719;BA.debugLine="orden = orden +1";
_orden = (int) (_orden+1);
 };
 //BA.debugLineNum = 1723;BA.debugLine="Log(scrResultados.Panel.Height)";
anywheresoftware.b4a.keywords.Common.LogImpl("470189392",BA.NumberToString(mostCurrent._scrresultados.getPanel().getHeight()),0);
 //BA.debugLineNum = 1724;BA.debugLine="End Sub";
return "";
}
public static void  _closescrmsgbox() throws Exception{
ResumableSub_closeScrMsgBox rsub = new ResumableSub_closeScrMsgBox(null);
rsub.resume(processBA, null);
}
public static class ResumableSub_closeScrMsgBox extends BA.ResumableSub {
public ResumableSub_closeScrMsgBox(appear.pnud.preservamos.reporte_habitat_laguna parent) {
this.parent = parent;
}
appear.pnud.preservamos.reporte_habitat_laguna parent;
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
 //BA.debugLineNum = 405;BA.debugLine="Msgbox2Async(\"Cerrar la encuesta? No se grabará e";
anywheresoftware.b4a.keywords.Common.Msgbox2Async(BA.ObjectToCharSequence("Cerrar la encuesta? No se grabará el progreso!"),BA.ObjectToCharSequence("SALIR"),"Si","","No",(anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper(), (android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null)),processBA,anywheresoftware.b4a.keywords.Common.False);
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
 //BA.debugLineNum = 408;BA.debugLine="Activity.RemoveAllViews";
parent.mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 409;BA.debugLine="Activity.finish";
parent.mostCurrent._activity.Finish();
 if (true) break;

case 4:
//C
this.state = -1;
;
 //BA.debugLineNum = 411;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public static void  _msgbox_result(int _result) throws Exception{
}
public static String  _fondogris_click() throws Exception{
 //BA.debugLineNum = 1746;BA.debugLine="Sub fondogris_Click";
 //BA.debugLineNum = 1747;BA.debugLine="fondogris.RemoveView";
mostCurrent._fondogris.RemoveView();
 //BA.debugLineNum = 1748;BA.debugLine="If mag1.IsInitialized Then";
if (mostCurrent._mag1.IsInitialized()) { 
 //BA.debugLineNum = 1749;BA.debugLine="mag1.RemoveView";
mostCurrent._mag1.RemoveView();
 };
 //BA.debugLineNum = 1751;BA.debugLine="End Sub";
return "";
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 13;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 14;BA.debugLine="Private pnlQuestions As Panel";
mostCurrent._pnlquestions = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 15;BA.debugLine="Private pnlImagen As Panel";
mostCurrent._pnlimagen = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 16;BA.debugLine="Private imgFondo As ImageView";
mostCurrent._imgfondo = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 18;BA.debugLine="Private btnPregunta1 As Button";
mostCurrent._btnpregunta1 = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 19;BA.debugLine="Private btnPregunta2 As Button";
mostCurrent._btnpregunta2 = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 20;BA.debugLine="Private btnPregunta3 As Button";
mostCurrent._btnpregunta3 = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 21;BA.debugLine="Private btnPregunta4 As Button";
mostCurrent._btnpregunta4 = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 22;BA.debugLine="Private btnPregunta5 As Button";
mostCurrent._btnpregunta5 = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 23;BA.debugLine="Private btnPregunta6 As Button";
mostCurrent._btnpregunta6 = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 24;BA.debugLine="Private btnPregunta7 As Button";
mostCurrent._btnpregunta7 = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 25;BA.debugLine="Private btnPregunta8 As Button";
mostCurrent._btnpregunta8 = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 26;BA.debugLine="Private btnPregunta9 As Button";
mostCurrent._btnpregunta9 = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 27;BA.debugLine="Private btnPregunta10 As Button";
mostCurrent._btnpregunta10 = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 28;BA.debugLine="Private btnPregunta11 As Button";
mostCurrent._btnpregunta11 = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 29;BA.debugLine="Private btnPregunta12 As Button";
mostCurrent._btnpregunta12 = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 30;BA.debugLine="Private btnPregunta13 As Button";
mostCurrent._btnpregunta13 = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 32;BA.debugLine="Private lblPregunta1 As Label";
mostCurrent._lblpregunta1 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 33;BA.debugLine="Private lblPregunta2 As Label";
mostCurrent._lblpregunta2 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 34;BA.debugLine="Private lblPregunta3 As Label";
mostCurrent._lblpregunta3 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 35;BA.debugLine="Private lblPregunta4 As Label";
mostCurrent._lblpregunta4 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 36;BA.debugLine="Private lblPregunta5 As Label";
mostCurrent._lblpregunta5 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 37;BA.debugLine="Private lblPregunta6 As Label";
mostCurrent._lblpregunta6 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 38;BA.debugLine="Private lblPregunta7 As Label";
mostCurrent._lblpregunta7 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 39;BA.debugLine="Private lblPregunta8 As Label";
mostCurrent._lblpregunta8 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 40;BA.debugLine="Private lblPregunta9 As Label";
mostCurrent._lblpregunta9 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 41;BA.debugLine="Private lblPregunta10 As Label";
mostCurrent._lblpregunta10 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 42;BA.debugLine="Private lblPregunta11 As Label";
mostCurrent._lblpregunta11 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 43;BA.debugLine="Private lblPregunta12 As Label";
mostCurrent._lblpregunta12 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 46;BA.debugLine="Dim currentpregunta As Int";
_currentpregunta = 0;
 //BA.debugLineNum = 48;BA.debugLine="Private lblEstado As Label";
mostCurrent._lblestado = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 49;BA.debugLine="Private pnlResultados As Panel";
mostCurrent._pnlresultados = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 50;BA.debugLine="Private btnTerminar As Button";
mostCurrent._btnterminar = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 52;BA.debugLine="Private scrResultados As ScrollView";
mostCurrent._scrresultados = new anywheresoftware.b4a.objects.ScrollViewWrapper();
 //BA.debugLineNum = 53;BA.debugLine="Private Panel1 As Panel";
mostCurrent._panel1 = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 54;BA.debugLine="Private btnCerrar As Button";
mostCurrent._btncerrar = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 55;BA.debugLine="Private lblTituloResultados As Label";
mostCurrent._lbltituloresultados = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 56;BA.debugLine="Private Label2 As Label";
mostCurrent._label2 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 57;BA.debugLine="Private btnShare As Button";
mostCurrent._btnshare = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 60;BA.debugLine="Dim fondogris As Label";
mostCurrent._fondogris = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 61;BA.debugLine="Dim img1 As Bitmap";
mostCurrent._img1 = new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper();
 //BA.debugLineNum = 62;BA.debugLine="Dim mag1 As Magnifize";
mostCurrent._mag1 = new magnifizewrapper.magnifizeWrapper();
 //BA.debugLineNum = 63;BA.debugLine="Dim currentscreen As String";
mostCurrent._currentscreen = "";
 //BA.debugLineNum = 65;BA.debugLine="Private imgQ2_1_c As ImageView";
mostCurrent._imgq2_1_c = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 66;BA.debugLine="Private imgQ2_1_b As ImageView";
mostCurrent._imgq2_1_b = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 67;BA.debugLine="Private imgQ2_1_a As ImageView";
mostCurrent._imgq2_1_a = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 71;BA.debugLine="Dim valorind1 As String";
mostCurrent._valorind1 = "";
 //BA.debugLineNum = 72;BA.debugLine="Dim valorind2 As String";
mostCurrent._valorind2 = "";
 //BA.debugLineNum = 73;BA.debugLine="Dim valorind3 As String";
mostCurrent._valorind3 = "";
 //BA.debugLineNum = 74;BA.debugLine="Dim valorind4 As String";
mostCurrent._valorind4 = "";
 //BA.debugLineNum = 75;BA.debugLine="Dim valorind5 As String";
mostCurrent._valorind5 = "";
 //BA.debugLineNum = 76;BA.debugLine="Dim valorind6 As String";
mostCurrent._valorind6 = "";
 //BA.debugLineNum = 77;BA.debugLine="Dim valorind7 As String";
mostCurrent._valorind7 = "";
 //BA.debugLineNum = 78;BA.debugLine="Dim valorind8 As String";
mostCurrent._valorind8 = "";
 //BA.debugLineNum = 79;BA.debugLine="Dim valorind9 As String";
mostCurrent._valorind9 = "";
 //BA.debugLineNum = 80;BA.debugLine="Dim valorind10 As String";
mostCurrent._valorind10 = "";
 //BA.debugLineNum = 81;BA.debugLine="Dim valorind11 As String";
mostCurrent._valorind11 = "";
 //BA.debugLineNum = 82;BA.debugLine="Dim valorind12 As String";
mostCurrent._valorind12 = "";
 //BA.debugLineNum = 83;BA.debugLine="Dim valorind13 As String";
mostCurrent._valorind13 = "";
 //BA.debugLineNum = 84;BA.debugLine="Dim valorind14 As String";
mostCurrent._valorind14 = "";
 //BA.debugLineNum = 85;BA.debugLine="Dim valorind15 As String";
mostCurrent._valorind15 = "";
 //BA.debugLineNum = 86;BA.debugLine="Dim valorind16 As String";
mostCurrent._valorind16 = "";
 //BA.debugLineNum = 87;BA.debugLine="Dim ind_pvm_1 As String";
mostCurrent._ind_pvm_1 = "";
 //BA.debugLineNum = 88;BA.debugLine="Dim ind_pvm_2 As String";
mostCurrent._ind_pvm_2 = "";
 //BA.debugLineNum = 89;BA.debugLine="Dim ind_pvm_3 As String";
mostCurrent._ind_pvm_3 = "";
 //BA.debugLineNum = 90;BA.debugLine="Dim ind_pvm_4 As String";
mostCurrent._ind_pvm_4 = "";
 //BA.debugLineNum = 91;BA.debugLine="Dim ind_pvm_5 As String";
mostCurrent._ind_pvm_5 = "";
 //BA.debugLineNum = 92;BA.debugLine="Dim ind_pvm_6 As String";
mostCurrent._ind_pvm_6 = "";
 //BA.debugLineNum = 93;BA.debugLine="Dim ind_pvm_7 As String";
mostCurrent._ind_pvm_7 = "";
 //BA.debugLineNum = 94;BA.debugLine="Dim ind_pvm_8 As String";
mostCurrent._ind_pvm_8 = "";
 //BA.debugLineNum = 95;BA.debugLine="Dim ind_pvm_9 As String";
mostCurrent._ind_pvm_9 = "";
 //BA.debugLineNum = 96;BA.debugLine="Dim ind_pvm_10 As String";
mostCurrent._ind_pvm_10 = "";
 //BA.debugLineNum = 97;BA.debugLine="Dim ind_pvm_11 As String";
mostCurrent._ind_pvm_11 = "";
 //BA.debugLineNum = 98;BA.debugLine="Dim ind_pvm_12 As String";
mostCurrent._ind_pvm_12 = "";
 //BA.debugLineNum = 99;BA.debugLine="Dim ind_pvm_13 As String";
mostCurrent._ind_pvm_13 = "";
 //BA.debugLineNum = 100;BA.debugLine="Dim preguntanumero As String";
mostCurrent._preguntanumero = "";
 //BA.debugLineNum = 101;BA.debugLine="Dim dateandtime As String";
mostCurrent._dateandtime = "";
 //BA.debugLineNum = 102;BA.debugLine="Dim valorcalidad As Double";
_valorcalidad = 0;
 //BA.debugLineNum = 103;BA.debugLine="Dim valorNS As Int";
_valorns = 0;
 //BA.debugLineNum = 104;BA.debugLine="Dim checklist As String";
mostCurrent._checklist = "";
 //BA.debugLineNum = 105;BA.debugLine="Dim notas As String";
mostCurrent._notas = "";
 //BA.debugLineNum = 109;BA.debugLine="Private btnMasDetalle As Button";
mostCurrent._btnmasdetalle = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 110;BA.debugLine="Private Gauge1 As Gauge";
mostCurrent._gauge1 = new appear.pnud.preservamos.gauge();
 //BA.debugLineNum = 111;BA.debugLine="Private xui As XUI";
mostCurrent._xui = new anywheresoftware.b4a.objects.B4XViewWrapper.XUI();
 //BA.debugLineNum = 115;BA.debugLine="Private btnNext As Button";
mostCurrent._btnnext = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 116;BA.debugLine="Private btnPrev As Button";
mostCurrent._btnprev = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 117;BA.debugLine="Private tabReporteHabitatRio As TabStrip";
mostCurrent._tabreportehabitatrio = new anywheresoftware.b4a.objects.TabStripViewPager();
 //BA.debugLineNum = 118;BA.debugLine="Private lblSection_Q1 As Label";
mostCurrent._lblsection_q1 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 119;BA.debugLine="Private pnlQ1_1 As Panel";
mostCurrent._pnlq1_1 = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 120;BA.debugLine="Private lblTitle_Q1_1 As Label";
mostCurrent._lbltitle_q1_1 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 121;BA.debugLine="Private pnlQ1_2 As Panel";
mostCurrent._pnlq1_2 = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 122;BA.debugLine="Private lblSection_Q2 As Label";
mostCurrent._lblsection_q2 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 123;BA.debugLine="Private pnlQ2_1 As Panel";
mostCurrent._pnlq2_1 = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 124;BA.debugLine="Private lblSection_Q3 As Label";
mostCurrent._lblsection_q3 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 125;BA.debugLine="Private lblSection_Q4 As Label";
mostCurrent._lblsection_q4 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 126;BA.debugLine="Private lblTitle_Q4_2 As Label";
mostCurrent._lbltitle_q4_2 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 127;BA.debugLine="Private lblSection_Q5 As Label";
mostCurrent._lblsection_q5 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 128;BA.debugLine="Private lblSection_Q5p2 As Label";
mostCurrent._lblsection_q5p2 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 129;BA.debugLine="Private lblSection_Q6 As Label";
mostCurrent._lblsection_q6 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 130;BA.debugLine="Private pnlQ6_1 As Panel";
mostCurrent._pnlq6_1 = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 131;BA.debugLine="Private lblTitle_Q6_1 As Label";
mostCurrent._lbltitle_q6_1 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 132;BA.debugLine="Private lblTitle_Q6_3 As Label";
mostCurrent._lbltitle_q6_3 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 133;BA.debugLine="Private lblSection_Q7 As Label";
mostCurrent._lblsection_q7 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 135;BA.debugLine="Dim haycampo As Boolean";
_haycampo = false;
 //BA.debugLineNum = 137;BA.debugLine="Dim desbordado As Boolean";
_desbordado = false;
 //BA.debugLineNum = 139;BA.debugLine="Dim haybasura As Boolean";
_haybasura = false;
 //BA.debugLineNum = 142;BA.debugLine="Private imgQ1_1_a As ImageView";
mostCurrent._imgq1_1_a = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 143;BA.debugLine="Private imgQ1_1_b As ImageView";
mostCurrent._imgq1_1_b = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 144;BA.debugLine="Private imgQ1_1_c As ImageView";
mostCurrent._imgq1_1_c = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 145;BA.debugLine="Private lblRojo_Q1 As Label";
mostCurrent._lblrojo_q1 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 149;BA.debugLine="Private txtNotas As EditText";
mostCurrent._txtnotas = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 152;BA.debugLine="Private imgQ1_2_a As ImageView";
mostCurrent._imgq1_2_a = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 153;BA.debugLine="Private imgQ1_2_b As ImageView";
mostCurrent._imgq1_2_b = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 154;BA.debugLine="Private imgQ1_2_c As ImageView";
mostCurrent._imgq1_2_c = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 155;BA.debugLine="Private lblRojo_Q1p2 As Label";
mostCurrent._lblrojo_q1p2 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 156;BA.debugLine="Private lblRojo_Q2 As Label";
mostCurrent._lblrojo_q2 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 157;BA.debugLine="Private imgQ2_1_si As ImageView";
mostCurrent._imgq2_1_si = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 158;BA.debugLine="Private imgQ2_1_no As ImageView";
mostCurrent._imgq2_1_no = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 159;BA.debugLine="Private lblRojo_Q2p2 As Label";
mostCurrent._lblrojo_q2p2 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 160;BA.debugLine="Private lblRojo_Q2p3 As Label";
mostCurrent._lblrojo_q2p3 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 161;BA.debugLine="Private imgQ2_3_si As ImageView";
mostCurrent._imgq2_3_si = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 162;BA.debugLine="Private imgQ2_3_no As ImageView";
mostCurrent._imgq2_3_no = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 163;BA.debugLine="Private lblRojo_Q2p4 As Label";
mostCurrent._lblrojo_q2p4 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 164;BA.debugLine="Private imgQ2_4_si As ImageView";
mostCurrent._imgq2_4_si = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 165;BA.debugLine="Private imgQ2_4_no As ImageView";
mostCurrent._imgq2_4_no = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 166;BA.debugLine="Private imgQ3_a As ImageView";
mostCurrent._imgq3_a = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 167;BA.debugLine="Private imgQ3_b As ImageView";
mostCurrent._imgq3_b = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 168;BA.debugLine="Private lblRojo_Q3 As Label";
mostCurrent._lblrojo_q3 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 169;BA.debugLine="Private lblRojo_Q3p2 As Label";
mostCurrent._lblrojo_q3p2 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 170;BA.debugLine="Private imgQ3p2_a As ImageView";
mostCurrent._imgq3p2_a = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 171;BA.debugLine="Private imgQ3p2_b As ImageView";
mostCurrent._imgq3p2_b = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 172;BA.debugLine="Private lblRojo_Q3p3_a As Label";
mostCurrent._lblrojo_q3p3_a = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 173;BA.debugLine="Private lblRojo_Q3p3_b As Label";
mostCurrent._lblrojo_q3p3_b = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 174;BA.debugLine="Private lblRojo_Q3p3_c As Label";
mostCurrent._lblrojo_q3p3_c = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 175;BA.debugLine="Private lblRojo_Q3p3_d As Label";
mostCurrent._lblrojo_q3p3_d = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 176;BA.debugLine="Private imgQ3p3_a As ImageView";
mostCurrent._imgq3p3_a = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 177;BA.debugLine="Private imgQ3p3_b As ImageView";
mostCurrent._imgq3p3_b = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 178;BA.debugLine="Private imgQ3p3_c As ImageView";
mostCurrent._imgq3p3_c = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 179;BA.debugLine="Private imgQ3p3_d As ImageView";
mostCurrent._imgq3p3_d = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 180;BA.debugLine="Private imgQ4_2_a As ImageView";
mostCurrent._imgq4_2_a = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 181;BA.debugLine="Private imgQ4_2_b As ImageView";
mostCurrent._imgq4_2_b = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 182;BA.debugLine="Private imgQ4_2_c As ImageView";
mostCurrent._imgq4_2_c = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 183;BA.debugLine="Private imgQ4_2_d As ImageView";
mostCurrent._imgq4_2_d = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 184;BA.debugLine="Private lblRojo_Q4_2 As Label";
mostCurrent._lblrojo_q4_2 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 185;BA.debugLine="Private lblRojo_Q4p2 As Label";
mostCurrent._lblrojo_q4p2 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 186;BA.debugLine="Private imgQ4p2_a As ImageView";
mostCurrent._imgq4p2_a = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 187;BA.debugLine="Private imgQ4p2_b As ImageView";
mostCurrent._imgq4p2_b = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 188;BA.debugLine="Private imgQ4p2_c As ImageView";
mostCurrent._imgq4p2_c = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 189;BA.debugLine="Private imgQ4p2_d As ImageView";
mostCurrent._imgq4p2_d = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 190;BA.debugLine="Private imgQ5_1_j As ImageView";
mostCurrent._imgq5_1_j = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 191;BA.debugLine="Private imgQ5_1_i As ImageView";
mostCurrent._imgq5_1_i = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 192;BA.debugLine="Private imgQ5_1_g As ImageView";
mostCurrent._imgq5_1_g = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 193;BA.debugLine="Private imgQ5_1_h As ImageView";
mostCurrent._imgq5_1_h = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 194;BA.debugLine="Private imgQ5_1_f As ImageView";
mostCurrent._imgq5_1_f = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 195;BA.debugLine="Private imgQ5_1_e As ImageView";
mostCurrent._imgq5_1_e = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 196;BA.debugLine="Private imgQ5_1_c As ImageView";
mostCurrent._imgq5_1_c = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 197;BA.debugLine="Private imgQ5_1_d As ImageView";
mostCurrent._imgq5_1_d = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 198;BA.debugLine="Private imgQ5_1_b As ImageView";
mostCurrent._imgq5_1_b = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 199;BA.debugLine="Private imgQ5_1_a As ImageView";
mostCurrent._imgq5_1_a = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 200;BA.debugLine="Private lblRojo_Q5_1 As Label";
mostCurrent._lblrojo_q5_1 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 201;BA.debugLine="Private lblRojo_Q5p2 As Label";
mostCurrent._lblrojo_q5p2 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 202;BA.debugLine="Private imgQ5p2_a As ImageView";
mostCurrent._imgq5p2_a = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 203;BA.debugLine="Private imgQ5p2_b As ImageView";
mostCurrent._imgq5p2_b = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 204;BA.debugLine="Private imgQ5p2_c As ImageView";
mostCurrent._imgq5p2_c = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 205;BA.debugLine="Private lblRojo_Q5p3 As Label";
mostCurrent._lblrojo_q5p3 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 206;BA.debugLine="Private imgQ5p3_b As ImageView";
mostCurrent._imgq5p3_b = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 207;BA.debugLine="Private imgQ5p3_a As ImageView";
mostCurrent._imgq5p3_a = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 208;BA.debugLine="Private imgQ5p3_c As ImageView";
mostCurrent._imgq5p3_c = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 209;BA.debugLine="Private imgQ5p3_d As ImageView";
mostCurrent._imgq5p3_d = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 210;BA.debugLine="Private lblRojo_Q6_a As Label";
mostCurrent._lblrojo_q6_a = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 211;BA.debugLine="Private lblRojo_Q6_c As Label";
mostCurrent._lblrojo_q6_c = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 212;BA.debugLine="Private lblRojo_Q6_e As Label";
mostCurrent._lblrojo_q6_e = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 213;BA.debugLine="Private lblRojo_Q6_d As Label";
mostCurrent._lblrojo_q6_d = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 214;BA.debugLine="Private lblRojo_Q6_b As Label";
mostCurrent._lblrojo_q6_b = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 215;BA.debugLine="Private imgQ6_a As ImageView";
mostCurrent._imgq6_a = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 216;BA.debugLine="Private imgQ6_b As ImageView";
mostCurrent._imgq6_b = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 217;BA.debugLine="Private imgQ6_c As ImageView";
mostCurrent._imgq6_c = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 218;BA.debugLine="Private imgQ6_d As ImageView";
mostCurrent._imgq6_d = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 219;BA.debugLine="Private imgQ6_e As ImageView";
mostCurrent._imgq6_e = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 220;BA.debugLine="Private imgQ6_1_a As ImageView";
mostCurrent._imgq6_1_a = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 221;BA.debugLine="Private imgQ6_1_b As ImageView";
mostCurrent._imgq6_1_b = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 222;BA.debugLine="Private imgQ6_1_c As ImageView";
mostCurrent._imgq6_1_c = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 223;BA.debugLine="Private imgQ6_1_d As ImageView";
mostCurrent._imgq6_1_d = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 224;BA.debugLine="Private lblRojo_Q6_1 As Label";
mostCurrent._lblrojo_q6_1 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 225;BA.debugLine="Private imgQ6_3_a As ImageView";
mostCurrent._imgq6_3_a = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 226;BA.debugLine="Private imgQ6_3_b As ImageView";
mostCurrent._imgq6_3_b = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 227;BA.debugLine="Private imgQ6_3_c As ImageView";
mostCurrent._imgq6_3_c = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 228;BA.debugLine="Private imgQ6_3_d As ImageView";
mostCurrent._imgq6_3_d = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 229;BA.debugLine="Private lblRojo_Q6_3 As Label";
mostCurrent._lblrojo_q6_3 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 230;BA.debugLine="Private lblRojo_Q4_a As Label";
mostCurrent._lblrojo_q4_a = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 231;BA.debugLine="Private lblRojo_Q4_b As Label";
mostCurrent._lblrojo_q4_b = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 232;BA.debugLine="Private lblRojo_Q4_c As Label";
mostCurrent._lblrojo_q4_c = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 233;BA.debugLine="Private lblRojo_Q4_d As Label";
mostCurrent._lblrojo_q4_d = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 234;BA.debugLine="Private lblRojo_Q4_e As Label";
mostCurrent._lblrojo_q4_e = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 235;BA.debugLine="Private imgQ4_a As ImageView";
mostCurrent._imgq4_a = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 236;BA.debugLine="Private imgQ4_b As ImageView";
mostCurrent._imgq4_b = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 237;BA.debugLine="Private imgQ4_c As ImageView";
mostCurrent._imgq4_c = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 238;BA.debugLine="Private imgQ4_d As ImageView";
mostCurrent._imgq4_d = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 239;BA.debugLine="Private imgQ4_e As ImageView";
mostCurrent._imgq4_e = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 240;BA.debugLine="Private lblRojo_Q4_f As Label";
mostCurrent._lblrojo_q4_f = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 241;BA.debugLine="Private lblRojo_Q7_a As Label";
mostCurrent._lblrojo_q7_a = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 242;BA.debugLine="Private lblRojo_Q7_c As Label";
mostCurrent._lblrojo_q7_c = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 243;BA.debugLine="Private lblRojo_Q7_d As Label";
mostCurrent._lblrojo_q7_d = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 244;BA.debugLine="Private lblRojo_Q7_b As Label";
mostCurrent._lblrojo_q7_b = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 245;BA.debugLine="Private imgQ7_a As ImageView";
mostCurrent._imgq7_a = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 246;BA.debugLine="Private imgQ7_b As ImageView";
mostCurrent._imgq7_b = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 247;BA.debugLine="Private imgQ7_c As ImageView";
mostCurrent._imgq7_c = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 248;BA.debugLine="Private imgQ7_d As ImageView";
mostCurrent._imgq7_d = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 249;BA.debugLine="Private scrollUsos As ScrollView";
mostCurrent._scrollusos = new anywheresoftware.b4a.objects.ScrollViewWrapper();
 //BA.debugLineNum = 250;BA.debugLine="Private scrollAnimales As ScrollView";
mostCurrent._scrollanimales = new anywheresoftware.b4a.objects.ScrollViewWrapper();
 //BA.debugLineNum = 251;BA.debugLine="Private scrollVegetacion As ScrollView";
mostCurrent._scrollvegetacion = new anywheresoftware.b4a.objects.ScrollViewWrapper();
 //BA.debugLineNum = 252;BA.debugLine="Private scrollExoticas1 As ScrollView";
mostCurrent._scrollexoticas1 = new anywheresoftware.b4a.objects.ScrollViewWrapper();
 //BA.debugLineNum = 253;BA.debugLine="Private scrollExoticas2 As ScrollView";
mostCurrent._scrollexoticas2 = new anywheresoftware.b4a.objects.ScrollViewWrapper();
 //BA.debugLineNum = 258;BA.debugLine="Private imgQ6_3 As ImageView";
mostCurrent._imgq6_3 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 259;BA.debugLine="Private imgQ6_2 As ImageView";
mostCurrent._imgq6_2 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 263;BA.debugLine="Private imgEspumas As ImageView";
mostCurrent._imgespumas = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 264;BA.debugLine="Private imgAutopartes As ImageView";
mostCurrent._imgautopartes = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 265;BA.debugLine="Private imgBasura As ImageView";
mostCurrent._imgbasura = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 266;BA.debugLine="Private imgPlantasAcuaticas As ImageView";
mostCurrent._imgplantasacuaticas = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 267;BA.debugLine="Private imgUso As ImageView";
mostCurrent._imguso = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 268;BA.debugLine="Private imgUso_Campo1 As ImageView";
mostCurrent._imguso_campo1 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 269;BA.debugLine="Private imgUso_Campo2 As ImageView";
mostCurrent._imguso_campo2 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 270;BA.debugLine="Private imgAnimales_patos As ImageView";
mostCurrent._imganimales_patos = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 271;BA.debugLine="Private imgAnimales_peces As ImageView";
mostCurrent._imganimales_peces = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 272;BA.debugLine="Private imgAnimales_roedores As ImageView";
mostCurrent._imganimales_roedores = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 273;BA.debugLine="Private imgAnimales_tortugas As ImageView";
mostCurrent._imganimales_tortugas = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 274;BA.debugLine="Private imgJuncos As ImageView";
mostCurrent._imgjuncos = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 275;BA.debugLine="Private imgArboles As ImageView";
mostCurrent._imgarboles = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 276;BA.debugLine="Private imgArbustos As ImageView";
mostCurrent._imgarbustos = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 277;BA.debugLine="Private imgBasuraRibera As ImageView";
mostCurrent._imgbasuraribera = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 278;BA.debugLine="Private imgLigustro As ImageView";
mostCurrent._imgligustro = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 279;BA.debugLine="Private imgPastoLargo As ImageView";
mostCurrent._imgpastolargo = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 280;BA.debugLine="Private imgBasuraRibera_Grande As ImageView";
mostCurrent._imgbasuraribera_grande = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 281;BA.debugLine="Private imgPasto_Corto As ImageView";
mostCurrent._imgpasto_corto = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 282;BA.debugLine="Private imgEntubado As ImageView";
mostCurrent._imgentubado = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 283;BA.debugLine="Private imgEmbarcaciones As ImageView";
mostCurrent._imgembarcaciones = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 284;BA.debugLine="Private imgBaño As ImageView";
mostCurrent._imgbaño = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 285;BA.debugLine="Private imgAcacia As ImageView";
mostCurrent._imgacacia = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 286;BA.debugLine="Private imgKayak As ImageView";
mostCurrent._imgkayak = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 287;BA.debugLine="Private imgPesca As ImageView";
mostCurrent._imgpesca = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 288;BA.debugLine="Private imgRio As ImageView";
mostCurrent._imgrio = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 289;BA.debugLine="Private imgRio_desbordado As ImageView";
mostCurrent._imgrio_desbordado = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 291;BA.debugLine="Private Label1 As Label";
mostCurrent._label1 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 292;BA.debugLine="End Sub";
return "";
}
public static String  _guardareval() throws Exception{
anywheresoftware.b4a.objects.collections.Map _map1 = null;
 //BA.debugLineNum = 1329;BA.debugLine="Sub GuardarEval";
 //BA.debugLineNum = 1331;BA.debugLine="Dim Map1 As Map";
_map1 = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 1332;BA.debugLine="Map1.Initialize";
_map1.Initialize();
 //BA.debugLineNum = 1333;BA.debugLine="Map1.Put(\"Id\", Form_Reporte.currentproject)";
_map1.Put((Object)("Id"),(Object)(mostCurrent._form_reporte._currentproject /*String*/ ));
 //BA.debugLineNum = 1334;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"evals\", \"val";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals","valorind1",(Object)(mostCurrent._valorind1),_map1);
 //BA.debugLineNum = 1335;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"evals\", \"val";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals","valorind2",(Object)(mostCurrent._valorind2),_map1);
 //BA.debugLineNum = 1336;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"evals\", \"val";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals","valorind3",(Object)(mostCurrent._valorind3),_map1);
 //BA.debugLineNum = 1337;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"evals\", \"val";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals","valorind4",(Object)(mostCurrent._valorind4),_map1);
 //BA.debugLineNum = 1338;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"evals\", \"val";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals","valorind5",(Object)(mostCurrent._valorind5),_map1);
 //BA.debugLineNum = 1339;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"evals\", \"val";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals","valorind6",(Object)(mostCurrent._valorind6),_map1);
 //BA.debugLineNum = 1340;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"evals\", \"val";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals","valorind7",(Object)(mostCurrent._valorind7),_map1);
 //BA.debugLineNum = 1341;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"evals\", \"val";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals","valorind8",(Object)(mostCurrent._valorind8),_map1);
 //BA.debugLineNum = 1342;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"evals\", \"val";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals","valorind9",(Object)(mostCurrent._valorind9),_map1);
 //BA.debugLineNum = 1343;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"evals\", \"val";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals","valorind10",(Object)(mostCurrent._valorind10),_map1);
 //BA.debugLineNum = 1344;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"evals\", \"val";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals","valorind11",(Object)(mostCurrent._valorind11),_map1);
 //BA.debugLineNum = 1345;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"evals\", \"val";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals","valorind12",(Object)(mostCurrent._valorind12),_map1);
 //BA.debugLineNum = 1346;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"evals\", \"val";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals","valorind13",(Object)(mostCurrent._valorind13),_map1);
 //BA.debugLineNum = 1347;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"evals\", \"val";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals","valorind14",(Object)(mostCurrent._valorind14),_map1);
 //BA.debugLineNum = 1348;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"evals\", \"val";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals","valorind15",(Object)(mostCurrent._valorind15),_map1);
 //BA.debugLineNum = 1349;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"evals\", \"val";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals","valorind16",(Object)(mostCurrent._valorind16),_map1);
 //BA.debugLineNum = 1350;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"evals\", \"bin";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals","bingo",(Object)(mostCurrent._checklist),_map1);
 //BA.debugLineNum = 1351;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"evals\", \"ind";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals","ind_pvm_1",(Object)(mostCurrent._ind_pvm_1),_map1);
 //BA.debugLineNum = 1352;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"evals\", \"ind";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals","ind_pvm_2",(Object)(mostCurrent._ind_pvm_2),_map1);
 //BA.debugLineNum = 1353;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"evals\", \"ind";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals","ind_pvm_3",(Object)(mostCurrent._ind_pvm_3),_map1);
 //BA.debugLineNum = 1354;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"evals\", \"ind";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals","ind_pvm_4",(Object)(mostCurrent._ind_pvm_4),_map1);
 //BA.debugLineNum = 1355;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"evals\", \"ind";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals","ind_pvm_5",(Object)(mostCurrent._ind_pvm_5),_map1);
 //BA.debugLineNum = 1356;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"evals\", \"ind";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals","ind_pvm_6",(Object)(mostCurrent._ind_pvm_6),_map1);
 //BA.debugLineNum = 1357;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"evals\", \"ind";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals","ind_pvm_7",(Object)(mostCurrent._ind_pvm_7),_map1);
 //BA.debugLineNum = 1358;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"evals\", \"ind";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals","ind_pvm_8",(Object)(mostCurrent._ind_pvm_8),_map1);
 //BA.debugLineNum = 1359;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"evals\", \"ind";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals","ind_pvm_9",(Object)(mostCurrent._ind_pvm_9),_map1);
 //BA.debugLineNum = 1360;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"evals\", \"ind";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals","ind_pvm_10",(Object)(mostCurrent._ind_pvm_10),_map1);
 //BA.debugLineNum = 1361;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"evals\", \"ind";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals","ind_pvm_11",(Object)(mostCurrent._ind_pvm_11),_map1);
 //BA.debugLineNum = 1362;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"evals\", \"ind";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals","ind_pvm_12",(Object)(mostCurrent._ind_pvm_12),_map1);
 //BA.debugLineNum = 1363;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"evals\", \"ind";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals","ind_pvm_13",(Object)(mostCurrent._ind_pvm_13),_map1);
 //BA.debugLineNum = 1367;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"evals\", \"val";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals","valorind20",(Object)(_valorns),_map1);
 //BA.debugLineNum = 1368;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"evals\", \"val";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals","valorcalidad",(Object)(_valorcalidad),_map1);
 //BA.debugLineNum = 1369;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"evals\", \"geo";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals","georeferencedDate",(Object)(mostCurrent._dateandtime),_map1);
 //BA.debugLineNum = 1371;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"evals\", \"ter";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals","terminado",(Object)("si"),_map1);
 //BA.debugLineNum = 1373;BA.debugLine="End Sub";
return "";
}
public static String  _imgq1_1_a_click() throws Exception{
 //BA.debugLineNum = 1915;BA.debugLine="Private Sub imgQ1_1_a_Click";
 //BA.debugLineNum = 1917;BA.debugLine="valorind9 = 0";
mostCurrent._valorind9 = BA.NumberToString(0);
 //BA.debugLineNum = 1918;BA.debugLine="lblRojo_Q1.Left = imgQ1_1_a.Left";
mostCurrent._lblrojo_q1.setLeft(mostCurrent._imgq1_1_a.getLeft());
 //BA.debugLineNum = 1919;BA.debugLine="lblRojo_Q1.Top = imgQ1_1_a.Top + imgQ1_1_a.Height";
mostCurrent._lblrojo_q1.setTop((int) (mostCurrent._imgq1_1_a.getTop()+mostCurrent._imgq1_1_a.getHeight()/(double)2));
 //BA.debugLineNum = 1920;BA.debugLine="lblRojo_Q1.Visible = True";
mostCurrent._lblrojo_q1.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1921;BA.debugLine="imgRio_desbordado.Visible = True";
mostCurrent._imgrio_desbordado.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1922;BA.debugLine="desbordado = True";
_desbordado = anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 1923;BA.debugLine="End Sub";
return "";
}
public static String  _imgq1_1_b_click() throws Exception{
 //BA.debugLineNum = 1924;BA.debugLine="Private Sub imgQ1_1_b_Click";
 //BA.debugLineNum = 1925;BA.debugLine="valorind9 = 10";
mostCurrent._valorind9 = BA.NumberToString(10);
 //BA.debugLineNum = 1926;BA.debugLine="lblRojo_Q1.Left = imgQ1_1_b.Left";
mostCurrent._lblrojo_q1.setLeft(mostCurrent._imgq1_1_b.getLeft());
 //BA.debugLineNum = 1927;BA.debugLine="lblRojo_Q1.Top = imgQ1_1_b.Top + imgQ1_1_b.Height";
mostCurrent._lblrojo_q1.setTop((int) (mostCurrent._imgq1_1_b.getTop()+mostCurrent._imgq1_1_b.getHeight()/(double)2));
 //BA.debugLineNum = 1928;BA.debugLine="lblRojo_Q1.Visible = True";
mostCurrent._lblrojo_q1.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1929;BA.debugLine="imgRio_desbordado.Visible = False";
mostCurrent._imgrio_desbordado.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1930;BA.debugLine="desbordado = False";
_desbordado = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 1931;BA.debugLine="End Sub";
return "";
}
public static String  _imgq1_1_c_click() throws Exception{
 //BA.debugLineNum = 1932;BA.debugLine="Private Sub imgQ1_1_c_Click";
 //BA.debugLineNum = 1933;BA.debugLine="valorind9 = \"NS\"";
mostCurrent._valorind9 = "NS";
 //BA.debugLineNum = 1934;BA.debugLine="lblRojo_Q1.Left = imgQ1_1_c.Left";
mostCurrent._lblrojo_q1.setLeft(mostCurrent._imgq1_1_c.getLeft());
 //BA.debugLineNum = 1935;BA.debugLine="lblRojo_Q1.Top = imgQ1_1_c.Top + imgQ1_1_c.Height";
mostCurrent._lblrojo_q1.setTop((int) (mostCurrent._imgq1_1_c.getTop()+mostCurrent._imgq1_1_c.getHeight()/(double)2));
 //BA.debugLineNum = 1936;BA.debugLine="lblRojo_Q1.Visible = True";
mostCurrent._lblrojo_q1.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1937;BA.debugLine="imgRio_desbordado.Visible = False";
mostCurrent._imgrio_desbordado.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1938;BA.debugLine="desbordado = False";
_desbordado = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 1939;BA.debugLine="End Sub";
return "";
}
public static String  _imgq1_2_a_click() throws Exception{
 //BA.debugLineNum = 1942;BA.debugLine="Private Sub imgQ1_2_a_Click";
 //BA.debugLineNum = 1943;BA.debugLine="valorind10 = 0";
mostCurrent._valorind10 = BA.NumberToString(0);
 //BA.debugLineNum = 1944;BA.debugLine="lblRojo_Q1p2.Left = imgQ1_2_a.Left";
mostCurrent._lblrojo_q1p2.setLeft(mostCurrent._imgq1_2_a.getLeft());
 //BA.debugLineNum = 1945;BA.debugLine="lblRojo_Q1p2.Top = imgQ1_2_a.Top + imgQ1_2_a.Heig";
mostCurrent._lblrojo_q1p2.setTop((int) (mostCurrent._imgq1_2_a.getTop()+mostCurrent._imgq1_2_a.getHeight()/(double)2));
 //BA.debugLineNum = 1946;BA.debugLine="lblRojo_Q1p2.Visible = True";
mostCurrent._lblrojo_q1p2.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1947;BA.debugLine="imgEntubado.Visible = True";
mostCurrent._imgentubado.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1948;BA.debugLine="End Sub";
return "";
}
public static String  _imgq1_2_b_click() throws Exception{
 //BA.debugLineNum = 1949;BA.debugLine="Private Sub imgQ1_2_b_Click";
 //BA.debugLineNum = 1950;BA.debugLine="valorind10 = 10";
mostCurrent._valorind10 = BA.NumberToString(10);
 //BA.debugLineNum = 1951;BA.debugLine="lblRojo_Q1p2.Left = imgQ1_2_b.Left";
mostCurrent._lblrojo_q1p2.setLeft(mostCurrent._imgq1_2_b.getLeft());
 //BA.debugLineNum = 1952;BA.debugLine="lblRojo_Q1p2.Top = imgQ1_2_b.Top + imgQ1_2_b.Heig";
mostCurrent._lblrojo_q1p2.setTop((int) (mostCurrent._imgq1_2_b.getTop()+mostCurrent._imgq1_2_b.getHeight()/(double)2));
 //BA.debugLineNum = 1953;BA.debugLine="lblRojo_Q1p2.Visible = True";
mostCurrent._lblrojo_q1p2.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1954;BA.debugLine="imgEntubado.Visible = False";
mostCurrent._imgentubado.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1955;BA.debugLine="End Sub";
return "";
}
public static String  _imgq1_2_c_click() throws Exception{
 //BA.debugLineNum = 1956;BA.debugLine="Private Sub imgQ1_2_c_Click";
 //BA.debugLineNum = 1957;BA.debugLine="valorind10 = \"NS\"";
mostCurrent._valorind10 = "NS";
 //BA.debugLineNum = 1958;BA.debugLine="lblRojo_Q1p2.Left = imgQ1_2_c.Left";
mostCurrent._lblrojo_q1p2.setLeft(mostCurrent._imgq1_2_c.getLeft());
 //BA.debugLineNum = 1959;BA.debugLine="lblRojo_Q1p2.Top = imgQ1_2_c.Top + imgQ1_2_c.Heig";
mostCurrent._lblrojo_q1p2.setTop((int) (mostCurrent._imgq1_2_c.getTop()+mostCurrent._imgq1_2_c.getHeight()/(double)2));
 //BA.debugLineNum = 1960;BA.debugLine="lblRojo_Q1p2.Visible = True";
mostCurrent._lblrojo_q1p2.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1961;BA.debugLine="imgEntubado.Visible = False";
mostCurrent._imgentubado.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1962;BA.debugLine="End Sub";
return "";
}
public static String  _imgq2_1_a_click() throws Exception{
 //BA.debugLineNum = 1980;BA.debugLine="Private Sub imgQ2_1_a_Click";
 //BA.debugLineNum = 1981;BA.debugLine="valorind5 = 10";
mostCurrent._valorind5 = BA.NumberToString(10);
 //BA.debugLineNum = 1982;BA.debugLine="lblRojo_Q2p2.Left = imgQ2_1_a.Left";
mostCurrent._lblrojo_q2p2.setLeft(mostCurrent._imgq2_1_a.getLeft());
 //BA.debugLineNum = 1983;BA.debugLine="lblRojo_Q2p2.Top = imgQ2_1_a.Top + imgQ2_1_a.Heig";
mostCurrent._lblrojo_q2p2.setTop((int) (mostCurrent._imgq2_1_a.getTop()+mostCurrent._imgq2_1_a.getHeight()/(double)2));
 //BA.debugLineNum = 1984;BA.debugLine="lblRojo_Q2p2.Visible = True";
mostCurrent._lblrojo_q2p2.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1985;BA.debugLine="imgRio.Bitmap = LoadBitmap(File.DirAssets, \"Lagun";
mostCurrent._imgrio.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"Laguna_Grande_marron.png").getObject()));
 //BA.debugLineNum = 1986;BA.debugLine="If desbordado = True Then";
if (_desbordado==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 1987;BA.debugLine="imgRio_desbordado.Bitmap = LoadBitmap(File.DirAs";
mostCurrent._imgrio_desbordado.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"Laguna_Grande_marron.png").getObject()));
 };
 //BA.debugLineNum = 1989;BA.debugLine="End Sub";
return "";
}
public static String  _imgq2_1_b_click() throws Exception{
 //BA.debugLineNum = 1990;BA.debugLine="Private Sub imgQ2_1_b_Click";
 //BA.debugLineNum = 1991;BA.debugLine="valorind5 = 0";
mostCurrent._valorind5 = BA.NumberToString(0);
 //BA.debugLineNum = 1992;BA.debugLine="lblRojo_Q2p2.Left = imgQ2_1_b.Left";
mostCurrent._lblrojo_q2p2.setLeft(mostCurrent._imgq2_1_b.getLeft());
 //BA.debugLineNum = 1993;BA.debugLine="lblRojo_Q2p2.Top = imgQ2_1_b.Top + imgQ2_1_b.Heig";
mostCurrent._lblrojo_q2p2.setTop((int) (mostCurrent._imgq2_1_b.getTop()+mostCurrent._imgq2_1_b.getHeight()/(double)2));
 //BA.debugLineNum = 1994;BA.debugLine="lblRojo_Q2p2.Visible = True";
mostCurrent._lblrojo_q2p2.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1995;BA.debugLine="imgRio.Bitmap = LoadBitmap(File.DirAssets, \"Lagun";
mostCurrent._imgrio.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"Laguna_Grande_negro.png").getObject()));
 //BA.debugLineNum = 1996;BA.debugLine="If desbordado = True Then";
if (_desbordado==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 1997;BA.debugLine="imgRio_desbordado.Bitmap = LoadBitmap(File.DirAs";
mostCurrent._imgrio_desbordado.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"Laguna_Grande_negro.png").getObject()));
 };
 //BA.debugLineNum = 1999;BA.debugLine="End Sub";
return "";
}
public static String  _imgq2_1_c_click() throws Exception{
 //BA.debugLineNum = 2000;BA.debugLine="Private Sub imgQ2_1_c_Click";
 //BA.debugLineNum = 2001;BA.debugLine="valorind5 = \"NS\"";
mostCurrent._valorind5 = "NS";
 //BA.debugLineNum = 2002;BA.debugLine="lblRojo_Q2p2.Left = imgQ2_1_c.Left";
mostCurrent._lblrojo_q2p2.setLeft(mostCurrent._imgq2_1_c.getLeft());
 //BA.debugLineNum = 2003;BA.debugLine="lblRojo_Q2p2.Top = imgQ2_1_c.Top + imgQ2_1_c.Heig";
mostCurrent._lblrojo_q2p2.setTop((int) (mostCurrent._imgq2_1_c.getTop()+mostCurrent._imgq2_1_c.getHeight()/(double)2));
 //BA.debugLineNum = 2004;BA.debugLine="lblRojo_Q2p2.Visible = True";
mostCurrent._lblrojo_q2p2.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 2005;BA.debugLine="imgRio.Bitmap = LoadBitmap(File.DirAssets, \"Lagun";
mostCurrent._imgrio.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"Laguna_Grande.png").getObject()));
 //BA.debugLineNum = 2006;BA.debugLine="If desbordado = True Then";
if (_desbordado==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 2007;BA.debugLine="imgRio_desbordado.Bitmap = LoadBitmap(File.DirAs";
mostCurrent._imgrio_desbordado.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"Laguna_Grande.png").getObject()));
 };
 //BA.debugLineNum = 2009;BA.debugLine="End Sub";
return "";
}
public static String  _imgq2_1_no_click() throws Exception{
 //BA.debugLineNum = 1972;BA.debugLine="Private Sub imgQ2_1_no_Click";
 //BA.debugLineNum = 1973;BA.debugLine="valorind5 = \"\"";
mostCurrent._valorind5 = "";
 //BA.debugLineNum = 1974;BA.debugLine="lblRojo_Q2.Left = imgQ2_1_no.Left";
mostCurrent._lblrojo_q2.setLeft(mostCurrent._imgq2_1_no.getLeft());
 //BA.debugLineNum = 1975;BA.debugLine="lblRojo_Q2.Top = imgQ2_1_no.Top + imgQ2_1_no.Heig";
mostCurrent._lblrojo_q2.setTop((int) (mostCurrent._imgq2_1_no.getTop()+mostCurrent._imgq2_1_no.getHeight()/(double)2));
 //BA.debugLineNum = 1976;BA.debugLine="lblRojo_Q2.Visible = True";
mostCurrent._lblrojo_q2.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1977;BA.debugLine="End Sub";
return "";
}
public static String  _imgq2_1_si_click() throws Exception{
 //BA.debugLineNum = 1965;BA.debugLine="Private Sub imgQ2_1_si_Click";
 //BA.debugLineNum = 1966;BA.debugLine="valorind5 = 10";
mostCurrent._valorind5 = BA.NumberToString(10);
 //BA.debugLineNum = 1967;BA.debugLine="lblRojo_Q2.Left = imgQ2_1_si.Left";
mostCurrent._lblrojo_q2.setLeft(mostCurrent._imgq2_1_si.getLeft());
 //BA.debugLineNum = 1968;BA.debugLine="lblRojo_Q2.Top = imgQ2_1_si.Top + imgQ2_1_si.Heig";
mostCurrent._lblrojo_q2.setTop((int) (mostCurrent._imgq2_1_si.getTop()+mostCurrent._imgq2_1_si.getHeight()/(double)2));
 //BA.debugLineNum = 1969;BA.debugLine="lblRojo_Q2.Visible = True";
mostCurrent._lblrojo_q2.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1970;BA.debugLine="imgRio.Bitmap = LoadBitmap(File.DirAssets, \"Lagun";
mostCurrent._imgrio.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"Laguna_Grande.png").getObject()));
 //BA.debugLineNum = 1971;BA.debugLine="End Sub";
return "";
}
public static String  _imgq2_3_no_click() throws Exception{
 //BA.debugLineNum = 2017;BA.debugLine="Private Sub imgQ2_3_no_Click";
 //BA.debugLineNum = 2018;BA.debugLine="valorind6 = 10";
mostCurrent._valorind6 = BA.NumberToString(10);
 //BA.debugLineNum = 2019;BA.debugLine="lblRojo_Q2p3.Left = imgQ2_3_no.Left";
mostCurrent._lblrojo_q2p3.setLeft(mostCurrent._imgq2_3_no.getLeft());
 //BA.debugLineNum = 2020;BA.debugLine="lblRojo_Q2p3.Top = imgQ2_3_no.Top + imgQ2_3_no.He";
mostCurrent._lblrojo_q2p3.setTop((int) (mostCurrent._imgq2_3_no.getTop()+mostCurrent._imgq2_3_no.getHeight()/(double)2));
 //BA.debugLineNum = 2021;BA.debugLine="lblRojo_Q2p3.Visible = True";
mostCurrent._lblrojo_q2p3.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 2022;BA.debugLine="End Sub";
return "";
}
public static String  _imgq2_3_si_click() throws Exception{
 //BA.debugLineNum = 2011;BA.debugLine="Private Sub imgQ2_3_si_Click";
 //BA.debugLineNum = 2012;BA.debugLine="valorind6 = 0";
mostCurrent._valorind6 = BA.NumberToString(0);
 //BA.debugLineNum = 2013;BA.debugLine="lblRojo_Q2p3.Left = imgQ2_3_si.Left";
mostCurrent._lblrojo_q2p3.setLeft(mostCurrent._imgq2_3_si.getLeft());
 //BA.debugLineNum = 2014;BA.debugLine="lblRojo_Q2p3.Top = imgQ2_3_si.Top + imgQ2_3_si.He";
mostCurrent._lblrojo_q2p3.setTop((int) (mostCurrent._imgq2_3_si.getTop()+mostCurrent._imgq2_3_si.getHeight()/(double)2));
 //BA.debugLineNum = 2015;BA.debugLine="lblRojo_Q2p3.Visible = True";
mostCurrent._lblrojo_q2p3.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 2016;BA.debugLine="End Sub";
return "";
}
public static String  _imgq2_4_no_click() throws Exception{
 //BA.debugLineNum = 2031;BA.debugLine="Private Sub imgQ2_4_no_Click";
 //BA.debugLineNum = 2032;BA.debugLine="ind_pvm_1 = 10";
mostCurrent._ind_pvm_1 = BA.NumberToString(10);
 //BA.debugLineNum = 2033;BA.debugLine="lblRojo_Q2p4.Left = imgQ2_4_no.Left";
mostCurrent._lblrojo_q2p4.setLeft(mostCurrent._imgq2_4_no.getLeft());
 //BA.debugLineNum = 2034;BA.debugLine="lblRojo_Q2p4.Top = imgQ2_4_no.Top + imgQ2_4_no.He";
mostCurrent._lblrojo_q2p4.setTop((int) (mostCurrent._imgq2_4_no.getTop()+mostCurrent._imgq2_4_no.getHeight()/(double)2));
 //BA.debugLineNum = 2035;BA.debugLine="lblRojo_Q2p4.Visible = True";
mostCurrent._lblrojo_q2p4.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 2036;BA.debugLine="imgEspumas.Visible = False";
mostCurrent._imgespumas.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 2037;BA.debugLine="End Sub";
return "";
}
public static String  _imgq2_4_si_click() throws Exception{
 //BA.debugLineNum = 2024;BA.debugLine="Private Sub imgQ2_4_si_Click";
 //BA.debugLineNum = 2025;BA.debugLine="ind_pvm_1 = 0";
mostCurrent._ind_pvm_1 = BA.NumberToString(0);
 //BA.debugLineNum = 2026;BA.debugLine="lblRojo_Q2p4.Left = imgQ2_4_si.Left";
mostCurrent._lblrojo_q2p4.setLeft(mostCurrent._imgq2_4_si.getLeft());
 //BA.debugLineNum = 2027;BA.debugLine="lblRojo_Q2p4.Top = imgQ2_4_si.Top + imgQ2_4_si.He";
mostCurrent._lblrojo_q2p4.setTop((int) (mostCurrent._imgq2_4_si.getTop()+mostCurrent._imgq2_4_si.getHeight()/(double)2));
 //BA.debugLineNum = 2028;BA.debugLine="lblRojo_Q2p4.Visible = True";
mostCurrent._lblrojo_q2p4.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 2029;BA.debugLine="imgEspumas.Visible = True";
mostCurrent._imgespumas.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 2030;BA.debugLine="End Sub";
return "";
}
public static String  _imgq3_a_click() throws Exception{
 //BA.debugLineNum = 2040;BA.debugLine="Private Sub imgQ3_a_Click";
 //BA.debugLineNum = 2041;BA.debugLine="valorind7 = 7";
mostCurrent._valorind7 = BA.NumberToString(7);
 //BA.debugLineNum = 2042;BA.debugLine="lblRojo_Q3.Left = imgQ3_a.Left";
mostCurrent._lblrojo_q3.setLeft(mostCurrent._imgq3_a.getLeft());
 //BA.debugLineNum = 2043;BA.debugLine="lblRojo_Q3.Top = imgQ3_a.Top + imgQ3_a.Height / 2";
mostCurrent._lblrojo_q3.setTop((int) (mostCurrent._imgq3_a.getTop()+mostCurrent._imgq3_a.getHeight()/(double)2));
 //BA.debugLineNum = 2044;BA.debugLine="lblRojo_Q3.Visible = True";
mostCurrent._lblrojo_q3.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 2045;BA.debugLine="imgBasura.Visible = True";
mostCurrent._imgbasura.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 2046;BA.debugLine="End Sub";
return "";
}
public static String  _imgq3_b_click() throws Exception{
 //BA.debugLineNum = 2047;BA.debugLine="Private Sub imgQ3_b_Click";
 //BA.debugLineNum = 2048;BA.debugLine="valorind7 = 10";
mostCurrent._valorind7 = BA.NumberToString(10);
 //BA.debugLineNum = 2049;BA.debugLine="lblRojo_Q3.Left = imgQ3_b.Left";
mostCurrent._lblrojo_q3.setLeft(mostCurrent._imgq3_b.getLeft());
 //BA.debugLineNum = 2050;BA.debugLine="lblRojo_Q3.Top = imgQ3_b.Top + imgQ3_b.Height / 2";
mostCurrent._lblrojo_q3.setTop((int) (mostCurrent._imgq3_b.getTop()+mostCurrent._imgq3_b.getHeight()/(double)2));
 //BA.debugLineNum = 2051;BA.debugLine="lblRojo_Q3.Visible = True";
mostCurrent._lblrojo_q3.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 2052;BA.debugLine="imgBasura.Visible = False";
mostCurrent._imgbasura.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 2053;BA.debugLine="End Sub";
return "";
}
public static String  _imgq3p2_a_click() throws Exception{
 //BA.debugLineNum = 2055;BA.debugLine="Private Sub imgQ3p2_a_Click";
 //BA.debugLineNum = 2056;BA.debugLine="valorind7 = valorind7 - 3";
mostCurrent._valorind7 = BA.NumberToString((double)(Double.parseDouble(mostCurrent._valorind7))-3);
 //BA.debugLineNum = 2057;BA.debugLine="lblRojo_Q3p2.Left = imgQ3p2_a.Left";
mostCurrent._lblrojo_q3p2.setLeft(mostCurrent._imgq3p2_a.getLeft());
 //BA.debugLineNum = 2058;BA.debugLine="lblRojo_Q3p2.Top = imgQ3p2_a.Top + imgQ3p2_a.Heig";
mostCurrent._lblrojo_q3p2.setTop((int) (mostCurrent._imgq3p2_a.getTop()+mostCurrent._imgq3p2_a.getHeight()/(double)2));
 //BA.debugLineNum = 2059;BA.debugLine="lblRojo_Q3p2.Visible = True";
mostCurrent._lblrojo_q3p2.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 2060;BA.debugLine="haybasura = True";
_haybasura = anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 2061;BA.debugLine="frmFelicitaciones.destino = \"residuos\"";
mostCurrent._frmfelicitaciones._destino /*String*/  = "residuos";
 //BA.debugLineNum = 2062;BA.debugLine="End Sub";
return "";
}
public static String  _imgq3p2_b_click() throws Exception{
 //BA.debugLineNum = 2063;BA.debugLine="Private Sub imgQ3p2_b_Click";
 //BA.debugLineNum = 2064;BA.debugLine="lblRojo_Q3p2.Left = imgQ3p2_b.Left";
mostCurrent._lblrojo_q3p2.setLeft(mostCurrent._imgq3p2_b.getLeft());
 //BA.debugLineNum = 2065;BA.debugLine="lblRojo_Q3p2.Top = imgQ3p2_b.Top + imgQ3p2_b.Heig";
mostCurrent._lblrojo_q3p2.setTop((int) (mostCurrent._imgq3p2_b.getTop()+mostCurrent._imgq3p2_b.getHeight()/(double)2));
 //BA.debugLineNum = 2066;BA.debugLine="lblRojo_Q3p2.Visible = True";
mostCurrent._lblrojo_q3p2.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 2067;BA.debugLine="End Sub";
return "";
}
public static String  _imgq3p3_a_click() throws Exception{
 //BA.debugLineNum = 2069;BA.debugLine="Private Sub imgQ3p3_a_Click";
 //BA.debugLineNum = 2070;BA.debugLine="If lblRojo_Q3p3_a.visible = True Then";
if (mostCurrent._lblrojo_q3p3_a.getVisible()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 2071;BA.debugLine="lblRojo_Q3p3_a.visible = False";
mostCurrent._lblrojo_q3p3_a.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 2072;BA.debugLine="imgBasuraRibera.Visible = False";
mostCurrent._imgbasuraribera.setVisible(anywheresoftware.b4a.keywords.Common.False);
 }else {
 //BA.debugLineNum = 2074;BA.debugLine="lblRojo_Q3p3_a.visible = True";
mostCurrent._lblrojo_q3p3_a.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 2075;BA.debugLine="imgBasuraRibera.Visible = True";
mostCurrent._imgbasuraribera.setVisible(anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 2077;BA.debugLine="End Sub";
return "";
}
public static String  _imgq3p3_b_click() throws Exception{
 //BA.debugLineNum = 2078;BA.debugLine="Private Sub imgQ3p3_b_Click";
 //BA.debugLineNum = 2079;BA.debugLine="If lblRojo_Q3p3_b.visible = True Then";
if (mostCurrent._lblrojo_q3p3_b.getVisible()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 2080;BA.debugLine="lblRojo_Q3p3_b.visible = False";
mostCurrent._lblrojo_q3p3_b.setVisible(anywheresoftware.b4a.keywords.Common.False);
 }else {
 //BA.debugLineNum = 2083;BA.debugLine="lblRojo_Q3p3_b.visible = True";
mostCurrent._lblrojo_q3p3_b.setVisible(anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 2086;BA.debugLine="End Sub";
return "";
}
public static String  _imgq3p3_c_click() throws Exception{
 //BA.debugLineNum = 2087;BA.debugLine="Private Sub imgQ3p3_c_Click";
 //BA.debugLineNum = 2088;BA.debugLine="If lblRojo_Q3p3_c.visible = True Then";
if (mostCurrent._lblrojo_q3p3_c.getVisible()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 2089;BA.debugLine="lblRojo_Q3p3_c.visible = False";
mostCurrent._lblrojo_q3p3_c.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 2090;BA.debugLine="imgBasuraRibera_Grande.Visible = False";
mostCurrent._imgbasuraribera_grande.setVisible(anywheresoftware.b4a.keywords.Common.False);
 }else {
 //BA.debugLineNum = 2092;BA.debugLine="lblRojo_Q3p3_c.visible = True";
mostCurrent._lblrojo_q3p3_c.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 2093;BA.debugLine="imgBasuraRibera_Grande.Visible = True";
mostCurrent._imgbasuraribera_grande.setVisible(anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 2095;BA.debugLine="End Sub";
return "";
}
public static String  _imgq3p3_d_click() throws Exception{
 //BA.debugLineNum = 2096;BA.debugLine="Private Sub imgQ3p3_d_Click";
 //BA.debugLineNum = 2097;BA.debugLine="If lblRojo_Q3p3_d.visible = True Then";
if (mostCurrent._lblrojo_q3p3_d.getVisible()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 2098;BA.debugLine="lblRojo_Q3p3_d.visible = False";
mostCurrent._lblrojo_q3p3_d.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 2099;BA.debugLine="imgAutopartes.Visible = False";
mostCurrent._imgautopartes.setVisible(anywheresoftware.b4a.keywords.Common.False);
 }else {
 //BA.debugLineNum = 2101;BA.debugLine="lblRojo_Q3p3_d.visible = True";
mostCurrent._lblrojo_q3p3_d.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 2102;BA.debugLine="imgAutopartes.Visible = True";
mostCurrent._imgautopartes.setVisible(anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 2104;BA.debugLine="End Sub";
return "";
}
public static String  _imgq4_2_a_click() throws Exception{
 //BA.debugLineNum = 2106;BA.debugLine="Private Sub imgQ4_2_a_Click";
 //BA.debugLineNum = 2107;BA.debugLine="ind_pvm_2 = 10";
mostCurrent._ind_pvm_2 = BA.NumberToString(10);
 //BA.debugLineNum = 2108;BA.debugLine="lblRojo_Q4_2.Left = imgQ4_2_a.Left";
mostCurrent._lblrojo_q4_2.setLeft(mostCurrent._imgq4_2_a.getLeft());
 //BA.debugLineNum = 2109;BA.debugLine="lblRojo_Q4_2.Top = imgQ4_2_a.Top + imgQ4_2_a.Heig";
mostCurrent._lblrojo_q4_2.setTop((int) (mostCurrent._imgq4_2_a.getTop()+mostCurrent._imgq4_2_a.getHeight()/(double)2));
 //BA.debugLineNum = 2110;BA.debugLine="lblRojo_Q4_2.Visible = True";
mostCurrent._lblrojo_q4_2.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 2111;BA.debugLine="imgJuncos.Visible = True";
mostCurrent._imgjuncos.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 2112;BA.debugLine="End Sub";
return "";
}
public static String  _imgq4_2_b_click() throws Exception{
 //BA.debugLineNum = 2113;BA.debugLine="Private Sub imgQ4_2_b_Click";
 //BA.debugLineNum = 2114;BA.debugLine="ind_pvm_2 = 8";
mostCurrent._ind_pvm_2 = BA.NumberToString(8);
 //BA.debugLineNum = 2115;BA.debugLine="lblRojo_Q4_2.Left = imgQ4_2_b.Left";
mostCurrent._lblrojo_q4_2.setLeft(mostCurrent._imgq4_2_b.getLeft());
 //BA.debugLineNum = 2116;BA.debugLine="lblRojo_Q4_2.Top = imgQ4_2_b.Top + imgQ4_2_b.Heig";
mostCurrent._lblrojo_q4_2.setTop((int) (mostCurrent._imgq4_2_b.getTop()+mostCurrent._imgq4_2_b.getHeight()/(double)2));
 //BA.debugLineNum = 2117;BA.debugLine="lblRojo_Q4_2.Visible = True";
mostCurrent._lblrojo_q4_2.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 2118;BA.debugLine="imgJuncos.Visible = True";
mostCurrent._imgjuncos.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 2119;BA.debugLine="End Sub";
return "";
}
public static String  _imgq4_2_c_click() throws Exception{
 //BA.debugLineNum = 2120;BA.debugLine="Private Sub imgQ4_2_c_Click";
 //BA.debugLineNum = 2121;BA.debugLine="ind_pvm_2 = 0";
mostCurrent._ind_pvm_2 = BA.NumberToString(0);
 //BA.debugLineNum = 2122;BA.debugLine="lblRojo_Q4_2.Left = imgQ4_2_c.Left";
mostCurrent._lblrojo_q4_2.setLeft(mostCurrent._imgq4_2_c.getLeft());
 //BA.debugLineNum = 2123;BA.debugLine="lblRojo_Q4_2.Top = imgQ4_2_c.Top + imgQ4_2_c.Heig";
mostCurrent._lblrojo_q4_2.setTop((int) (mostCurrent._imgq4_2_c.getTop()+mostCurrent._imgq4_2_c.getHeight()/(double)2));
 //BA.debugLineNum = 2124;BA.debugLine="lblRojo_Q4_2.Visible = True";
mostCurrent._lblrojo_q4_2.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 2125;BA.debugLine="imgJuncos.Visible = False";
mostCurrent._imgjuncos.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 2126;BA.debugLine="End Sub";
return "";
}
public static String  _imgq4_2_d_click() throws Exception{
 //BA.debugLineNum = 2127;BA.debugLine="Private Sub imgQ4_2_d_Click";
 //BA.debugLineNum = 2128;BA.debugLine="ind_pvm_2 = \"NS\"";
mostCurrent._ind_pvm_2 = "NS";
 //BA.debugLineNum = 2129;BA.debugLine="lblRojo_Q4_2.Left = imgQ4_2_d.Left";
mostCurrent._lblrojo_q4_2.setLeft(mostCurrent._imgq4_2_d.getLeft());
 //BA.debugLineNum = 2130;BA.debugLine="lblRojo_Q4_2.Top = imgQ4_2_d.Top + imgQ4_2_d.Heig";
mostCurrent._lblrojo_q4_2.setTop((int) (mostCurrent._imgq4_2_d.getTop()+mostCurrent._imgq4_2_d.getHeight()/(double)2));
 //BA.debugLineNum = 2131;BA.debugLine="lblRojo_Q4_2.Visible = True";
mostCurrent._lblrojo_q4_2.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 2132;BA.debugLine="imgJuncos.Visible = False";
mostCurrent._imgjuncos.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 2133;BA.debugLine="End Sub";
return "";
}
public static String  _imgq4_a_click() throws Exception{
 //BA.debugLineNum = 2425;BA.debugLine="Private Sub imgQ4_a_Click";
 //BA.debugLineNum = 2426;BA.debugLine="If lblRojo_Q4_a.visible = True Then";
if (mostCurrent._lblrojo_q4_a.getVisible()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 2427;BA.debugLine="lblRojo_Q4_a.visible = False";
mostCurrent._lblrojo_q4_a.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 2428;BA.debugLine="imgAnimales_patos.Visible = False";
mostCurrent._imganimales_patos.setVisible(anywheresoftware.b4a.keywords.Common.False);
 }else {
 //BA.debugLineNum = 2430;BA.debugLine="lblRojo_Q4_a.visible = True";
mostCurrent._lblrojo_q4_a.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 2431;BA.debugLine="imgAnimales_patos.Visible = True";
mostCurrent._imganimales_patos.setVisible(anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 2433;BA.debugLine="End Sub";
return "";
}
public static String  _imgq4_b_click() throws Exception{
 //BA.debugLineNum = 2434;BA.debugLine="Private Sub imgQ4_b_Click";
 //BA.debugLineNum = 2435;BA.debugLine="If lblRojo_Q4_b.visible = True Then";
if (mostCurrent._lblrojo_q4_b.getVisible()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 2436;BA.debugLine="lblRojo_Q4_b.visible = False";
mostCurrent._lblrojo_q4_b.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 2437;BA.debugLine="imgAnimales_peces.Visible = False";
mostCurrent._imganimales_peces.setVisible(anywheresoftware.b4a.keywords.Common.False);
 }else {
 //BA.debugLineNum = 2439;BA.debugLine="lblRojo_Q4_b.visible = True";
mostCurrent._lblrojo_q4_b.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 2440;BA.debugLine="imgAnimales_peces.Visible = True";
mostCurrent._imganimales_peces.setVisible(anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 2442;BA.debugLine="End Sub";
return "";
}
public static String  _imgq4_c_click() throws Exception{
 //BA.debugLineNum = 2443;BA.debugLine="Private Sub imgQ4_c_Click";
 //BA.debugLineNum = 2444;BA.debugLine="If lblRojo_Q4_c.visible = True Then";
if (mostCurrent._lblrojo_q4_c.getVisible()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 2445;BA.debugLine="lblRojo_Q4_c.visible = False";
mostCurrent._lblrojo_q4_c.setVisible(anywheresoftware.b4a.keywords.Common.False);
 }else {
 //BA.debugLineNum = 2448;BA.debugLine="lblRojo_Q4_c.visible = True";
mostCurrent._lblrojo_q4_c.setVisible(anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 2451;BA.debugLine="End Sub";
return "";
}
public static String  _imgq4_d_click() throws Exception{
 //BA.debugLineNum = 2452;BA.debugLine="Private Sub imgQ4_d_Click";
 //BA.debugLineNum = 2453;BA.debugLine="If lblRojo_Q4_d.visible = True Then";
if (mostCurrent._lblrojo_q4_d.getVisible()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 2454;BA.debugLine="lblRojo_Q4_d.visible = False";
mostCurrent._lblrojo_q4_d.setVisible(anywheresoftware.b4a.keywords.Common.False);
 }else {
 //BA.debugLineNum = 2457;BA.debugLine="lblRojo_Q4_d.visible = True";
mostCurrent._lblrojo_q4_d.setVisible(anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 2460;BA.debugLine="End Sub";
return "";
}
public static String  _imgq4_e_click() throws Exception{
 //BA.debugLineNum = 2461;BA.debugLine="Private Sub imgQ4_e_Click";
 //BA.debugLineNum = 2462;BA.debugLine="If lblRojo_Q4_e.visible = True Then";
if (mostCurrent._lblrojo_q4_e.getVisible()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 2463;BA.debugLine="lblRojo_Q4_e.visible = False";
mostCurrent._lblrojo_q4_e.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 2464;BA.debugLine="imgAnimales_roedores.Visible = False";
mostCurrent._imganimales_roedores.setVisible(anywheresoftware.b4a.keywords.Common.False);
 }else {
 //BA.debugLineNum = 2466;BA.debugLine="lblRojo_Q4_e.visible = True";
mostCurrent._lblrojo_q4_e.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 2467;BA.debugLine="imgAnimales_roedores.Visible = True";
mostCurrent._imganimales_roedores.setVisible(anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 2469;BA.debugLine="End Sub";
return "";
}
public static String  _imgq4_f_click() throws Exception{
 //BA.debugLineNum = 2470;BA.debugLine="Private Sub imgQ4_f_Click";
 //BA.debugLineNum = 2471;BA.debugLine="If lblRojo_Q4_f.visible = True Then";
if (mostCurrent._lblrojo_q4_f.getVisible()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 2472;BA.debugLine="lblRojo_Q4_f.visible = False";
mostCurrent._lblrojo_q4_f.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 2473;BA.debugLine="imgAnimales_tortugas.Visible = False";
mostCurrent._imganimales_tortugas.setVisible(anywheresoftware.b4a.keywords.Common.False);
 }else {
 //BA.debugLineNum = 2475;BA.debugLine="lblRojo_Q4_f.visible = True";
mostCurrent._lblrojo_q4_f.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 2476;BA.debugLine="imgAnimales_tortugas.Visible = True";
mostCurrent._imganimales_tortugas.setVisible(anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 2478;BA.debugLine="End Sub";
return "";
}
public static String  _imgq4p2_a_click() throws Exception{
 //BA.debugLineNum = 2135;BA.debugLine="Private Sub imgQ4p2_a_Click";
 //BA.debugLineNum = 2136;BA.debugLine="valorind4 = 10";
mostCurrent._valorind4 = BA.NumberToString(10);
 //BA.debugLineNum = 2137;BA.debugLine="lblRojo_Q4p2.Left = imgQ4p2_a.Left";
mostCurrent._lblrojo_q4p2.setLeft(mostCurrent._imgq4p2_a.getLeft());
 //BA.debugLineNum = 2138;BA.debugLine="lblRojo_Q4p2.Top = imgQ4p2_a.Top + imgQ4p2_a.Heig";
mostCurrent._lblrojo_q4p2.setTop((int) (mostCurrent._imgq4p2_a.getTop()+mostCurrent._imgq4p2_a.getHeight()/(double)2));
 //BA.debugLineNum = 2139;BA.debugLine="lblRojo_Q4p2.Visible = True";
mostCurrent._lblrojo_q4p2.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 2140;BA.debugLine="imgPlantasAcuaticas.Visible = True";
mostCurrent._imgplantasacuaticas.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 2141;BA.debugLine="End Sub";
return "";
}
public static String  _imgq4p2_b_click() throws Exception{
 //BA.debugLineNum = 2142;BA.debugLine="Private Sub imgQ4p2_b_Click";
 //BA.debugLineNum = 2143;BA.debugLine="valorind4 = 8";
mostCurrent._valorind4 = BA.NumberToString(8);
 //BA.debugLineNum = 2144;BA.debugLine="lblRojo_Q4p2.Left = imgQ4p2_b.Left";
mostCurrent._lblrojo_q4p2.setLeft(mostCurrent._imgq4p2_b.getLeft());
 //BA.debugLineNum = 2145;BA.debugLine="lblRojo_Q4p2.Top = imgQ4p2_b.Top + imgQ4p2_b.Heig";
mostCurrent._lblrojo_q4p2.setTop((int) (mostCurrent._imgq4p2_b.getTop()+mostCurrent._imgq4p2_b.getHeight()/(double)2));
 //BA.debugLineNum = 2146;BA.debugLine="lblRojo_Q4p2.Visible = True";
mostCurrent._lblrojo_q4p2.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 2147;BA.debugLine="imgPlantasAcuaticas.Visible = True";
mostCurrent._imgplantasacuaticas.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 2148;BA.debugLine="End Sub";
return "";
}
public static String  _imgq4p2_c_click() throws Exception{
 //BA.debugLineNum = 2149;BA.debugLine="Private Sub imgQ4p2_c_Click";
 //BA.debugLineNum = 2150;BA.debugLine="valorind4 = 0";
mostCurrent._valorind4 = BA.NumberToString(0);
 //BA.debugLineNum = 2151;BA.debugLine="lblRojo_Q4p2.Left = imgQ4p2_c.Left";
mostCurrent._lblrojo_q4p2.setLeft(mostCurrent._imgq4p2_c.getLeft());
 //BA.debugLineNum = 2152;BA.debugLine="lblRojo_Q4p2.Top = imgQ4p2_c.Top + imgQ4p2_c.Heig";
mostCurrent._lblrojo_q4p2.setTop((int) (mostCurrent._imgq4p2_c.getTop()+mostCurrent._imgq4p2_c.getHeight()/(double)2));
 //BA.debugLineNum = 2153;BA.debugLine="lblRojo_Q4p2.Visible = True";
mostCurrent._lblrojo_q4p2.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 2154;BA.debugLine="imgPlantasAcuaticas.Visible = False";
mostCurrent._imgplantasacuaticas.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 2155;BA.debugLine="End Sub";
return "";
}
public static String  _imgq4p2_d_click() throws Exception{
 //BA.debugLineNum = 2156;BA.debugLine="Private Sub imgQ4p2_d_Click";
 //BA.debugLineNum = 2157;BA.debugLine="valorind4 = \"NS\"";
mostCurrent._valorind4 = "NS";
 //BA.debugLineNum = 2158;BA.debugLine="lblRojo_Q4p2.Left = imgQ4p2_d.Left";
mostCurrent._lblrojo_q4p2.setLeft(mostCurrent._imgq4p2_d.getLeft());
 //BA.debugLineNum = 2159;BA.debugLine="lblRojo_Q4p2.Top = imgQ4p2_d.Top + imgQ4p2_d.Heig";
mostCurrent._lblrojo_q4p2.setTop((int) (mostCurrent._imgq4p2_d.getTop()+mostCurrent._imgq4p2_d.getHeight()/(double)2));
 //BA.debugLineNum = 2160;BA.debugLine="lblRojo_Q4p2.Visible = True";
mostCurrent._lblrojo_q4p2.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 2161;BA.debugLine="imgPlantasAcuaticas.Visible = False";
mostCurrent._imgplantasacuaticas.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 2162;BA.debugLine="End Sub";
return "";
}
public static String  _imgq5_1_a_click() throws Exception{
 //BA.debugLineNum = 2164;BA.debugLine="Private Sub imgQ5_1_a_Click";
 //BA.debugLineNum = 2165;BA.debugLine="valorind1 = 0";
mostCurrent._valorind1 = BA.NumberToString(0);
 //BA.debugLineNum = 2166;BA.debugLine="haycampo = False";
_haycampo = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 2167;BA.debugLine="lblRojo_Q5_1.Left = imgQ5_1_a.Left";
mostCurrent._lblrojo_q5_1.setLeft(mostCurrent._imgq5_1_a.getLeft());
 //BA.debugLineNum = 2168;BA.debugLine="lblRojo_Q5_1.Top = imgQ5_1_a.Top + imgQ5_1_a.Heig";
mostCurrent._lblrojo_q5_1.setTop((int) (mostCurrent._imgq5_1_a.getTop()+mostCurrent._imgq5_1_a.getHeight()/(double)2));
 //BA.debugLineNum = 2169;BA.debugLine="lblRojo_Q5_1.Visible = True";
mostCurrent._lblrojo_q5_1.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 2170;BA.debugLine="imgUso.Visible = True";
mostCurrent._imguso.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 2171;BA.debugLine="imgUso.Bitmap = LoadBitmap(File.DirAssets, \"Lagun";
mostCurrent._imguso.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"Laguna_Grande_ambiente_Industrias.png").getObject()));
 //BA.debugLineNum = 2172;BA.debugLine="End Sub";
return "";
}
public static String  _imgq5_1_b_click() throws Exception{
 //BA.debugLineNum = 2173;BA.debugLine="Private Sub imgQ5_1_b_Click";
 //BA.debugLineNum = 2174;BA.debugLine="valorind1 = 2";
mostCurrent._valorind1 = BA.NumberToString(2);
 //BA.debugLineNum = 2175;BA.debugLine="haycampo = False";
_haycampo = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 2176;BA.debugLine="lblRojo_Q5_1.Left = imgQ5_1_b.Left";
mostCurrent._lblrojo_q5_1.setLeft(mostCurrent._imgq5_1_b.getLeft());
 //BA.debugLineNum = 2177;BA.debugLine="lblRojo_Q5_1.Top = imgQ5_1_b.Top + imgQ5_1_b.Heig";
mostCurrent._lblrojo_q5_1.setTop((int) (mostCurrent._imgq5_1_b.getTop()+mostCurrent._imgq5_1_b.getHeight()/(double)2));
 //BA.debugLineNum = 2178;BA.debugLine="lblRojo_Q5_1.Visible = True";
mostCurrent._lblrojo_q5_1.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 2179;BA.debugLine="imgUso.Visible = True";
mostCurrent._imguso.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 2180;BA.debugLine="imgUso.Bitmap = LoadBitmap(File.DirAssets, \"Lagun";
mostCurrent._imguso.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"Laguna_Grande_ambiente_Ciudad.png").getObject()));
 //BA.debugLineNum = 2181;BA.debugLine="End Sub";
return "";
}
public static String  _imgq5_1_c_click() throws Exception{
 //BA.debugLineNum = 2182;BA.debugLine="Private Sub imgQ5_1_c_Click";
 //BA.debugLineNum = 2183;BA.debugLine="valorind1 = 4";
mostCurrent._valorind1 = BA.NumberToString(4);
 //BA.debugLineNum = 2184;BA.debugLine="haycampo = False";
_haycampo = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 2185;BA.debugLine="lblRojo_Q5_1.Left = imgQ5_1_c.Left";
mostCurrent._lblrojo_q5_1.setLeft(mostCurrent._imgq5_1_c.getLeft());
 //BA.debugLineNum = 2186;BA.debugLine="lblRojo_Q5_1.Top = imgQ5_1_c.Top + imgQ5_1_c.Heig";
mostCurrent._lblrojo_q5_1.setTop((int) (mostCurrent._imgq5_1_c.getTop()+mostCurrent._imgq5_1_c.getHeight()/(double)2));
 //BA.debugLineNum = 2187;BA.debugLine="lblRojo_Q5_1.Visible = True";
mostCurrent._lblrojo_q5_1.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 2188;BA.debugLine="imgUso.Visible = True";
mostCurrent._imguso.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 2189;BA.debugLine="imgUso.Bitmap = LoadBitmap(File.DirAssets, \"Lagun";
mostCurrent._imguso.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"Laguna_Grande_ambiente_barrio.png").getObject()));
 //BA.debugLineNum = 2190;BA.debugLine="End Sub";
return "";
}
public static String  _imgq5_1_d_click() throws Exception{
 //BA.debugLineNum = 2191;BA.debugLine="Private Sub imgQ5_1_d_Click";
 //BA.debugLineNum = 2192;BA.debugLine="valorind1 = 10";
mostCurrent._valorind1 = BA.NumberToString(10);
 //BA.debugLineNum = 2193;BA.debugLine="haycampo = False";
_haycampo = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 2194;BA.debugLine="lblRojo_Q5_1.Left = imgQ5_1_d.Left";
mostCurrent._lblrojo_q5_1.setLeft(mostCurrent._imgq5_1_d.getLeft());
 //BA.debugLineNum = 2195;BA.debugLine="lblRojo_Q5_1.Top = imgQ5_1_d.Top + imgQ5_1_d.Heig";
mostCurrent._lblrojo_q5_1.setTop((int) (mostCurrent._imgq5_1_d.getTop()+mostCurrent._imgq5_1_d.getHeight()/(double)2));
 //BA.debugLineNum = 2196;BA.debugLine="lblRojo_Q5_1.Visible = True";
mostCurrent._lblrojo_q5_1.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 2197;BA.debugLine="imgUso.Visible = True";
mostCurrent._imguso.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 2198;BA.debugLine="imgUso.Bitmap = LoadBitmap(File.DirAssets, \"Lagun";
mostCurrent._imguso.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"Laguna_Grande_ambiente_ReservaNaturalk.png").getObject()));
 //BA.debugLineNum = 2199;BA.debugLine="End Sub";
return "";
}
public static String  _imgq5_1_e_click() throws Exception{
 //BA.debugLineNum = 2200;BA.debugLine="Private Sub imgQ5_1_e_Click";
 //BA.debugLineNum = 2201;BA.debugLine="valorind1 = 10";
mostCurrent._valorind1 = BA.NumberToString(10);
 //BA.debugLineNum = 2202;BA.debugLine="haycampo = False";
_haycampo = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 2203;BA.debugLine="lblRojo_Q5_1.Left = imgQ5_1_e.Left";
mostCurrent._lblrojo_q5_1.setLeft(mostCurrent._imgq5_1_e.getLeft());
 //BA.debugLineNum = 2204;BA.debugLine="lblRojo_Q5_1.Top = imgQ5_1_e.Top + imgQ5_1_e.Heig";
mostCurrent._lblrojo_q5_1.setTop((int) (mostCurrent._imgq5_1_e.getTop()+mostCurrent._imgq5_1_e.getHeight()/(double)2));
 //BA.debugLineNum = 2205;BA.debugLine="lblRojo_Q5_1.Visible = True";
mostCurrent._lblrojo_q5_1.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 2206;BA.debugLine="imgUso.Visible = True";
mostCurrent._imguso.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 2207;BA.debugLine="imgUso.Bitmap = LoadBitmap(File.DirAssets, \"Lagun";
mostCurrent._imguso.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"Laguna_Grande_ambiente_MontePastizal.png").getObject()));
 //BA.debugLineNum = 2208;BA.debugLine="End Sub";
return "";
}
public static String  _imgq5_1_f_click() throws Exception{
 //BA.debugLineNum = 2209;BA.debugLine="Private Sub imgQ5_1_f_Click";
 //BA.debugLineNum = 2210;BA.debugLine="valorind1 = 4";
mostCurrent._valorind1 = BA.NumberToString(4);
 //BA.debugLineNum = 2211;BA.debugLine="haycampo = False";
_haycampo = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 2212;BA.debugLine="lblRojo_Q5_1.Left = imgQ5_1_f.Left";
mostCurrent._lblrojo_q5_1.setLeft(mostCurrent._imgq5_1_f.getLeft());
 //BA.debugLineNum = 2213;BA.debugLine="lblRojo_Q5_1.Top = imgQ5_1_f.Top + imgQ5_1_f.Heig";
mostCurrent._lblrojo_q5_1.setTop((int) (mostCurrent._imgq5_1_f.getTop()+mostCurrent._imgq5_1_f.getHeight()/(double)2));
 //BA.debugLineNum = 2214;BA.debugLine="lblRojo_Q5_1.Visible = True";
mostCurrent._lblrojo_q5_1.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 2215;BA.debugLine="imgUso.Visible = True";
mostCurrent._imguso.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 2216;BA.debugLine="imgUso.Bitmap = LoadBitmap(File.DirAssets, \"Lagun";
mostCurrent._imguso.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"Laguna_Grande_ambiente_PlazaParque.png").getObject()));
 //BA.debugLineNum = 2217;BA.debugLine="End Sub";
return "";
}
public static String  _imgq5_1_g_click() throws Exception{
 //BA.debugLineNum = 2218;BA.debugLine="Private Sub imgQ5_1_g_Click";
 //BA.debugLineNum = 2219;BA.debugLine="valorind1 = 2";
mostCurrent._valorind1 = BA.NumberToString(2);
 //BA.debugLineNum = 2220;BA.debugLine="haycampo = False";
_haycampo = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 2221;BA.debugLine="lblRojo_Q5_1.Left = imgQ5_1_g.Left";
mostCurrent._lblrojo_q5_1.setLeft(mostCurrent._imgq5_1_g.getLeft());
 //BA.debugLineNum = 2222;BA.debugLine="lblRojo_Q5_1.Top = imgQ5_1_g.Top + imgQ5_1_g.Heig";
mostCurrent._lblrojo_q5_1.setTop((int) (mostCurrent._imgq5_1_g.getTop()+mostCurrent._imgq5_1_g.getHeight()/(double)2));
 //BA.debugLineNum = 2223;BA.debugLine="lblRojo_Q5_1.Visible = True";
mostCurrent._lblrojo_q5_1.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 2224;BA.debugLine="imgUso.Visible = True";
mostCurrent._imguso.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 2225;BA.debugLine="imgUso.Bitmap = LoadBitmap(File.DirAssets, \"Lagun";
mostCurrent._imguso.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"Laguna_Grande_ambiente_basural.png").getObject()));
 //BA.debugLineNum = 2226;BA.debugLine="End Sub";
return "";
}
public static String  _imgq5_1_h_click() throws Exception{
 //BA.debugLineNum = 2227;BA.debugLine="Private Sub imgQ5_1_h_Click";
 //BA.debugLineNum = 2228;BA.debugLine="haycampo = True";
_haycampo = anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 2229;BA.debugLine="lblRojo_Q5_1.Left = imgQ5_1_h.Left";
mostCurrent._lblrojo_q5_1.setLeft(mostCurrent._imgq5_1_h.getLeft());
 //BA.debugLineNum = 2230;BA.debugLine="lblRojo_Q5_1.Top = imgQ5_1_h.Top + imgQ5_1_h.Heig";
mostCurrent._lblrojo_q5_1.setTop((int) (mostCurrent._imgq5_1_h.getTop()+mostCurrent._imgq5_1_h.getHeight()/(double)2));
 //BA.debugLineNum = 2231;BA.debugLine="lblRojo_Q5_1.Visible = True";
mostCurrent._lblrojo_q5_1.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 2232;BA.debugLine="imgUso.Visible = True";
mostCurrent._imguso.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 2233;BA.debugLine="imgUso.Bitmap = LoadBitmap(File.DirAssets, \"Lagun";
mostCurrent._imguso.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"Laguna_Grande_ambiente_campo.png").getObject()));
 //BA.debugLineNum = 2234;BA.debugLine="End Sub";
return "";
}
public static String  _imgq5_1_i_click() throws Exception{
 //BA.debugLineNum = 2235;BA.debugLine="Private Sub imgQ5_1_i_Click";
 //BA.debugLineNum = 2236;BA.debugLine="valorind1 = \"NS\"";
mostCurrent._valorind1 = "NS";
 //BA.debugLineNum = 2237;BA.debugLine="haycampo = False";
_haycampo = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 2238;BA.debugLine="lblRojo_Q5_1.Left = imgQ5_1_i.Left";
mostCurrent._lblrojo_q5_1.setLeft(mostCurrent._imgq5_1_i.getLeft());
 //BA.debugLineNum = 2239;BA.debugLine="lblRojo_Q5_1.Top = imgQ5_1_i.Top + imgQ5_1_i.Heig";
mostCurrent._lblrojo_q5_1.setTop((int) (mostCurrent._imgq5_1_i.getTop()+mostCurrent._imgq5_1_i.getHeight()/(double)2));
 //BA.debugLineNum = 2240;BA.debugLine="lblRojo_Q5_1.Visible = True";
mostCurrent._lblrojo_q5_1.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 2241;BA.debugLine="imgUso.Visible = False";
mostCurrent._imguso.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 2242;BA.debugLine="End Sub";
return "";
}
public static String  _imgq5_1_j_click() throws Exception{
 //BA.debugLineNum = 2243;BA.debugLine="Private Sub imgQ5_1_j_Click";
 //BA.debugLineNum = 2244;BA.debugLine="valorind1 = 0";
mostCurrent._valorind1 = BA.NumberToString(0);
 //BA.debugLineNum = 2245;BA.debugLine="haycampo = False";
_haycampo = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 2246;BA.debugLine="lblRojo_Q5_1.Left = imgQ5_1_j.Left";
mostCurrent._lblrojo_q5_1.setLeft(mostCurrent._imgq5_1_j.getLeft());
 //BA.debugLineNum = 2247;BA.debugLine="lblRojo_Q5_1.Top = imgQ5_1_j.Top + imgQ5_1_j.Heig";
mostCurrent._lblrojo_q5_1.setTop((int) (mostCurrent._imgq5_1_j.getTop()+mostCurrent._imgq5_1_j.getHeight()/(double)2));
 //BA.debugLineNum = 2248;BA.debugLine="lblRojo_Q5_1.Visible = True";
mostCurrent._lblrojo_q5_1.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 2249;BA.debugLine="imgUso.Bitmap = LoadBitmap(File.DirAssets, \"Rio_G";
mostCurrent._imguso.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"Rio_Grande_Ambiente_campo.png").getObject()));
 //BA.debugLineNum = 2250;BA.debugLine="End Sub";
return "";
}
public static String  _imgq5p2_a_click() throws Exception{
 //BA.debugLineNum = 2254;BA.debugLine="Private Sub imgQ5p2_a_Click";
 //BA.debugLineNum = 2255;BA.debugLine="valorind2 = 0";
mostCurrent._valorind2 = BA.NumberToString(0);
 //BA.debugLineNum = 2256;BA.debugLine="lblRojo_Q5p2.Left = imgQ5p2_a.Left";
mostCurrent._lblrojo_q5p2.setLeft(mostCurrent._imgq5p2_a.getLeft());
 //BA.debugLineNum = 2257;BA.debugLine="lblRojo_Q5p2.Top = imgQ5p2_a.Top + imgQ5p2_a.Heig";
mostCurrent._lblrojo_q5p2.setTop((int) (mostCurrent._imgq5p2_a.getTop()+mostCurrent._imgq5p2_a.getHeight()/(double)2));
 //BA.debugLineNum = 2258;BA.debugLine="lblRojo_Q5p2.Visible = True";
mostCurrent._lblrojo_q5p2.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 2259;BA.debugLine="imgUso_Campo2.Visible = True";
mostCurrent._imguso_campo2.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 2260;BA.debugLine="imgUso_Campo2.Bitmap = LoadBitmap(File.DirAssets,";
mostCurrent._imguso_campo2.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"Laguna_Grande_ambiente_campo_muchoganado.png").getObject()));
 //BA.debugLineNum = 2261;BA.debugLine="End Sub";
return "";
}
public static String  _imgq5p2_b_click() throws Exception{
 //BA.debugLineNum = 2262;BA.debugLine="Private Sub imgQ5p2_b_Click";
 //BA.debugLineNum = 2263;BA.debugLine="valorind2 = 4";
mostCurrent._valorind2 = BA.NumberToString(4);
 //BA.debugLineNum = 2264;BA.debugLine="lblRojo_Q5p2.Left = imgQ5p2_b.Left";
mostCurrent._lblrojo_q5p2.setLeft(mostCurrent._imgq5p2_b.getLeft());
 //BA.debugLineNum = 2265;BA.debugLine="lblRojo_Q5p2.Top = imgQ5p2_b.Top + imgQ5p2_b.Heig";
mostCurrent._lblrojo_q5p2.setTop((int) (mostCurrent._imgq5p2_b.getTop()+mostCurrent._imgq5p2_b.getHeight()/(double)2));
 //BA.debugLineNum = 2266;BA.debugLine="lblRojo_Q5p2.Visible = True";
mostCurrent._lblrojo_q5p2.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 2267;BA.debugLine="imgUso_Campo2.Visible = True";
mostCurrent._imguso_campo2.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 2268;BA.debugLine="imgUso_Campo2.Bitmap = LoadBitmap(File.DirAssets,";
mostCurrent._imguso_campo2.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"Laguna_Grande_ambiente_campo_pocoGanado.png").getObject()));
 //BA.debugLineNum = 2269;BA.debugLine="End Sub";
return "";
}
public static String  _imgq5p2_c_click() throws Exception{
 //BA.debugLineNum = 2270;BA.debugLine="Private Sub imgQ5p2_c_Click";
 //BA.debugLineNum = 2271;BA.debugLine="valorind2 = 10";
mostCurrent._valorind2 = BA.NumberToString(10);
 //BA.debugLineNum = 2272;BA.debugLine="lblRojo_Q5p2.Left = imgQ5p2_c.Left";
mostCurrent._lblrojo_q5p2.setLeft(mostCurrent._imgq5p2_c.getLeft());
 //BA.debugLineNum = 2273;BA.debugLine="lblRojo_Q5p2.Top = imgQ5p2_c.Top + imgQ5p2_c.Heig";
mostCurrent._lblrojo_q5p2.setTop((int) (mostCurrent._imgq5p2_c.getTop()+mostCurrent._imgq5p2_c.getHeight()/(double)2));
 //BA.debugLineNum = 2274;BA.debugLine="lblRojo_Q5p2.Visible = True";
mostCurrent._lblrojo_q5p2.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 2275;BA.debugLine="imgUso_Campo2.Visible = False";
mostCurrent._imguso_campo2.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 2276;BA.debugLine="imgUso_Campo2.Visible = False";
mostCurrent._imguso_campo2.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 2278;BA.debugLine="End Sub";
return "";
}
public static String  _imgq5p3_a_click() throws Exception{
 //BA.debugLineNum = 2281;BA.debugLine="Private Sub imgQ5p3_a_Click";
 //BA.debugLineNum = 2282;BA.debugLine="valorind1 = 2";
mostCurrent._valorind1 = BA.NumberToString(2);
 //BA.debugLineNum = 2283;BA.debugLine="lblRojo_Q5p3.Left = imgQ5p3_a.Left";
mostCurrent._lblrojo_q5p3.setLeft(mostCurrent._imgq5p3_a.getLeft());
 //BA.debugLineNum = 2284;BA.debugLine="lblRojo_Q5p3.Top = imgQ5p3_a.Top + imgQ5p3_a.Heig";
mostCurrent._lblrojo_q5p3.setTop((int) (mostCurrent._imgq5p3_a.getTop()+mostCurrent._imgq5p3_a.getHeight()/(double)2));
 //BA.debugLineNum = 2285;BA.debugLine="lblRojo_Q5p3.Visible = True";
mostCurrent._lblrojo_q5p3.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 2286;BA.debugLine="imgUso_Campo1.Visible = True";
mostCurrent._imguso_campo1.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 2287;BA.debugLine="imgUso_Campo1.Bitmap = LoadBitmap(File.DirAssets,";
mostCurrent._imguso_campo1.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"Laguna_Grande_ambiente_campo_invernaderos.png").getObject()));
 //BA.debugLineNum = 2288;BA.debugLine="End Sub";
return "";
}
public static String  _imgq5p3_b_click() throws Exception{
 //BA.debugLineNum = 2289;BA.debugLine="Private Sub imgQ5p3_b_Click";
 //BA.debugLineNum = 2290;BA.debugLine="valorind1 = 2";
mostCurrent._valorind1 = BA.NumberToString(2);
 //BA.debugLineNum = 2291;BA.debugLine="lblRojo_Q5p3.Left = imgQ5p3_b.Left";
mostCurrent._lblrojo_q5p3.setLeft(mostCurrent._imgq5p3_b.getLeft());
 //BA.debugLineNum = 2292;BA.debugLine="lblRojo_Q5p3.Top = imgQ5p3_b.Top + imgQ5p3_b.Heig";
mostCurrent._lblrojo_q5p3.setTop((int) (mostCurrent._imgq5p3_b.getTop()+mostCurrent._imgq5p3_b.getHeight()/(double)2));
 //BA.debugLineNum = 2293;BA.debugLine="lblRojo_Q5p3.Visible = True";
mostCurrent._lblrojo_q5p3.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 2294;BA.debugLine="imgUso_Campo1.Visible = True";
mostCurrent._imguso_campo1.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 2295;BA.debugLine="imgUso_Campo1.Bitmap = LoadBitmap(File.DirAssets,";
mostCurrent._imguso_campo1.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"Laguna_Grande_ambiente_campo_cultivos.png").getObject()));
 //BA.debugLineNum = 2296;BA.debugLine="End Sub";
return "";
}
public static String  _imgq5p3_c_click() throws Exception{
 //BA.debugLineNum = 2297;BA.debugLine="Private Sub imgQ5p3_c_Click";
 //BA.debugLineNum = 2298;BA.debugLine="valorind1 = 6";
mostCurrent._valorind1 = BA.NumberToString(6);
 //BA.debugLineNum = 2299;BA.debugLine="lblRojo_Q5p3.Left = imgQ5p3_c.Left";
mostCurrent._lblrojo_q5p3.setLeft(mostCurrent._imgq5p3_c.getLeft());
 //BA.debugLineNum = 2300;BA.debugLine="lblRojo_Q5p3.Top = imgQ5p3_c.Top + imgQ5p3_c.Heig";
mostCurrent._lblrojo_q5p3.setTop((int) (mostCurrent._imgq5p3_c.getTop()+mostCurrent._imgq5p3_c.getHeight()/(double)2));
 //BA.debugLineNum = 2301;BA.debugLine="lblRojo_Q5p3.Visible = True";
mostCurrent._lblrojo_q5p3.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 2302;BA.debugLine="imgUso_Campo1.Visible = True";
mostCurrent._imguso_campo1.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 2303;BA.debugLine="imgUso_Campo1.Bitmap = LoadBitmap(File.DirAssets,";
mostCurrent._imguso_campo1.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"Laguna_Grande_ambiente_campo_otrosCultivos.png").getObject()));
 //BA.debugLineNum = 2304;BA.debugLine="End Sub";
return "";
}
public static String  _imgq5p3_d_click() throws Exception{
 //BA.debugLineNum = 2305;BA.debugLine="Private Sub imgQ5p3_d_Click";
 //BA.debugLineNum = 2306;BA.debugLine="valorind1 = 10";
mostCurrent._valorind1 = BA.NumberToString(10);
 //BA.debugLineNum = 2307;BA.debugLine="lblRojo_Q5p3.Left = imgQ5p3_d.Left";
mostCurrent._lblrojo_q5p3.setLeft(mostCurrent._imgq5p3_d.getLeft());
 //BA.debugLineNum = 2308;BA.debugLine="lblRojo_Q5p3.Top = imgQ5p3_d.Top + imgQ5p3_d.Heig";
mostCurrent._lblrojo_q5p3.setTop((int) (mostCurrent._imgq5p3_d.getTop()+mostCurrent._imgq5p3_d.getHeight()/(double)2));
 //BA.debugLineNum = 2309;BA.debugLine="lblRojo_Q5p3.Visible = True";
mostCurrent._lblrojo_q5p3.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 2310;BA.debugLine="imgUso_Campo1.Visible = False";
mostCurrent._imguso_campo1.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 2311;BA.debugLine="End Sub";
return "";
}
public static String  _imgq6_1_a_click() throws Exception{
 //BA.debugLineNum = 2367;BA.debugLine="Private Sub imgQ6_1_a_Click";
 //BA.debugLineNum = 2368;BA.debugLine="ind_pvm_3 = 0";
mostCurrent._ind_pvm_3 = BA.NumberToString(0);
 //BA.debugLineNum = 2369;BA.debugLine="lblRojo_Q6_1.Left = imgQ6_1_a.Left";
mostCurrent._lblrojo_q6_1.setLeft(mostCurrent._imgq6_1_a.getLeft());
 //BA.debugLineNum = 2370;BA.debugLine="lblRojo_Q6_1.Top = imgQ6_1_a.Top + imgQ6_1_a.Heig";
mostCurrent._lblrojo_q6_1.setTop((int) (mostCurrent._imgq6_1_a.getTop()+mostCurrent._imgq6_1_a.getHeight()/(double)2));
 //BA.debugLineNum = 2371;BA.debugLine="lblRojo_Q6_1.Visible = True";
mostCurrent._lblrojo_q6_1.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 2372;BA.debugLine="imgAcacia.Visible = True";
mostCurrent._imgacacia.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 2373;BA.debugLine="End Sub";
return "";
}
public static String  _imgq6_1_b_click() throws Exception{
 //BA.debugLineNum = 2374;BA.debugLine="Private Sub imgQ6_1_b_Click";
 //BA.debugLineNum = 2375;BA.debugLine="ind_pvm_3 = 2";
mostCurrent._ind_pvm_3 = BA.NumberToString(2);
 //BA.debugLineNum = 2376;BA.debugLine="lblRojo_Q6_1.Left = imgQ6_1_b.Left";
mostCurrent._lblrojo_q6_1.setLeft(mostCurrent._imgq6_1_b.getLeft());
 //BA.debugLineNum = 2377;BA.debugLine="lblRojo_Q6_1.Top = imgQ6_1_b.Top + imgQ6_1_b.Heig";
mostCurrent._lblrojo_q6_1.setTop((int) (mostCurrent._imgq6_1_b.getTop()+mostCurrent._imgq6_1_b.getHeight()/(double)2));
 //BA.debugLineNum = 2378;BA.debugLine="lblRojo_Q6_1.Visible = True";
mostCurrent._lblrojo_q6_1.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 2379;BA.debugLine="imgAcacia.Visible = True";
mostCurrent._imgacacia.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 2380;BA.debugLine="End Sub";
return "";
}
public static String  _imgq6_1_c_click() throws Exception{
 //BA.debugLineNum = 2381;BA.debugLine="Private Sub imgQ6_1_c_Click";
 //BA.debugLineNum = 2382;BA.debugLine="ind_pvm_3 = 10";
mostCurrent._ind_pvm_3 = BA.NumberToString(10);
 //BA.debugLineNum = 2383;BA.debugLine="lblRojo_Q6_1.Left = imgQ6_1_c.Left";
mostCurrent._lblrojo_q6_1.setLeft(mostCurrent._imgq6_1_c.getLeft());
 //BA.debugLineNum = 2384;BA.debugLine="lblRojo_Q6_1.Top = imgQ6_1_c.Top + imgQ6_1_c.Heig";
mostCurrent._lblrojo_q6_1.setTop((int) (mostCurrent._imgq6_1_c.getTop()+mostCurrent._imgq6_1_c.getHeight()/(double)2));
 //BA.debugLineNum = 2385;BA.debugLine="lblRojo_Q6_1.Visible = True";
mostCurrent._lblrojo_q6_1.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 2386;BA.debugLine="imgAcacia.Visible = False";
mostCurrent._imgacacia.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 2387;BA.debugLine="End Sub";
return "";
}
public static String  _imgq6_1_d_click() throws Exception{
 //BA.debugLineNum = 2388;BA.debugLine="Private Sub imgQ6_1_d_Click";
 //BA.debugLineNum = 2389;BA.debugLine="ind_pvm_3 = \"NS\"";
mostCurrent._ind_pvm_3 = "NS";
 //BA.debugLineNum = 2390;BA.debugLine="lblRojo_Q6_1.Left = imgQ6_1_d.Left";
mostCurrent._lblrojo_q6_1.setLeft(mostCurrent._imgq6_1_d.getLeft());
 //BA.debugLineNum = 2391;BA.debugLine="lblRojo_Q6_1.Top = imgQ6_1_d.Top + imgQ6_1_d.Heig";
mostCurrent._lblrojo_q6_1.setTop((int) (mostCurrent._imgq6_1_d.getTop()+mostCurrent._imgq6_1_d.getHeight()/(double)2));
 //BA.debugLineNum = 2392;BA.debugLine="lblRojo_Q6_1.Visible = True";
mostCurrent._lblrojo_q6_1.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 2393;BA.debugLine="imgAcacia.Visible = False";
mostCurrent._imgacacia.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 2394;BA.debugLine="End Sub";
return "";
}
public static String  _imgq6_3_a_click() throws Exception{
 //BA.debugLineNum = 2396;BA.debugLine="Private Sub imgQ6_3_a_Click";
 //BA.debugLineNum = 2397;BA.debugLine="ind_pvm_4 = 0";
mostCurrent._ind_pvm_4 = BA.NumberToString(0);
 //BA.debugLineNum = 2398;BA.debugLine="lblRojo_Q6_3.Left = imgQ6_3_a.Left";
mostCurrent._lblrojo_q6_3.setLeft(mostCurrent._imgq6_3_a.getLeft());
 //BA.debugLineNum = 2399;BA.debugLine="lblRojo_Q6_3.Top = imgQ6_3_a.Top + imgQ6_3_a.Heig";
mostCurrent._lblrojo_q6_3.setTop((int) (mostCurrent._imgq6_3_a.getTop()+mostCurrent._imgq6_3_a.getHeight()/(double)2));
 //BA.debugLineNum = 2400;BA.debugLine="lblRojo_Q6_3.Visible = True";
mostCurrent._lblrojo_q6_3.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 2401;BA.debugLine="imgLigustro.Visible = True";
mostCurrent._imgligustro.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 2402;BA.debugLine="End Sub";
return "";
}
public static String  _imgq6_3_b_click() throws Exception{
 //BA.debugLineNum = 2403;BA.debugLine="Private Sub imgQ6_3_b_Click";
 //BA.debugLineNum = 2404;BA.debugLine="ind_pvm_4 = 2";
mostCurrent._ind_pvm_4 = BA.NumberToString(2);
 //BA.debugLineNum = 2405;BA.debugLine="lblRojo_Q6_3.Left = imgQ6_3_b.Left";
mostCurrent._lblrojo_q6_3.setLeft(mostCurrent._imgq6_3_b.getLeft());
 //BA.debugLineNum = 2406;BA.debugLine="lblRojo_Q6_3.Top = imgQ6_3_b.Top + imgQ6_3_b.Heig";
mostCurrent._lblrojo_q6_3.setTop((int) (mostCurrent._imgq6_3_b.getTop()+mostCurrent._imgq6_3_b.getHeight()/(double)2));
 //BA.debugLineNum = 2407;BA.debugLine="lblRojo_Q6_3.Visible = True";
mostCurrent._lblrojo_q6_3.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 2408;BA.debugLine="imgLigustro.Visible = True";
mostCurrent._imgligustro.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 2409;BA.debugLine="End Sub";
return "";
}
public static String  _imgq6_3_c_click() throws Exception{
 //BA.debugLineNum = 2410;BA.debugLine="Private Sub imgQ6_3_c_Click";
 //BA.debugLineNum = 2411;BA.debugLine="ind_pvm_4 = 10";
mostCurrent._ind_pvm_4 = BA.NumberToString(10);
 //BA.debugLineNum = 2412;BA.debugLine="lblRojo_Q6_3.Left = imgQ6_3_c.Left";
mostCurrent._lblrojo_q6_3.setLeft(mostCurrent._imgq6_3_c.getLeft());
 //BA.debugLineNum = 2413;BA.debugLine="lblRojo_Q6_3.Top = imgQ6_3_c.Top + imgQ6_3_c.Heig";
mostCurrent._lblrojo_q6_3.setTop((int) (mostCurrent._imgq6_3_c.getTop()+mostCurrent._imgq6_3_c.getHeight()/(double)2));
 //BA.debugLineNum = 2414;BA.debugLine="lblRojo_Q6_3.Visible = True";
mostCurrent._lblrojo_q6_3.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 2415;BA.debugLine="imgLigustro.Visible = False";
mostCurrent._imgligustro.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 2416;BA.debugLine="End Sub";
return "";
}
public static String  _imgq6_3_d_click() throws Exception{
 //BA.debugLineNum = 2417;BA.debugLine="Private Sub imgQ6_3_d_Click";
 //BA.debugLineNum = 2418;BA.debugLine="ind_pvm_4 = \"NS\"";
mostCurrent._ind_pvm_4 = "NS";
 //BA.debugLineNum = 2419;BA.debugLine="lblRojo_Q6_3.Left = imgQ6_3_d.Left";
mostCurrent._lblrojo_q6_3.setLeft(mostCurrent._imgq6_3_d.getLeft());
 //BA.debugLineNum = 2420;BA.debugLine="lblRojo_Q6_3.Top = imgQ6_3_d.Top + imgQ6_3_d.Heig";
mostCurrent._lblrojo_q6_3.setTop((int) (mostCurrent._imgq6_3_d.getTop()+mostCurrent._imgq6_3_d.getHeight()/(double)2));
 //BA.debugLineNum = 2421;BA.debugLine="lblRojo_Q6_3.Visible = True";
mostCurrent._lblrojo_q6_3.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 2422;BA.debugLine="imgLigustro.Visible = False";
mostCurrent._imgligustro.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 2423;BA.debugLine="End Sub";
return "";
}
public static String  _imgq6_a_click() throws Exception{
 //BA.debugLineNum = 2314;BA.debugLine="Private Sub imgQ6_a_Click";
 //BA.debugLineNum = 2315;BA.debugLine="If lblRojo_Q6_a.visible = True Then";
if (mostCurrent._lblrojo_q6_a.getVisible()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 2316;BA.debugLine="lblRojo_Q6_a.visible = False";
mostCurrent._lblrojo_q6_a.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 2317;BA.debugLine="imgArboles.Visible = False";
mostCurrent._imgarboles.setVisible(anywheresoftware.b4a.keywords.Common.False);
 }else {
 //BA.debugLineNum = 2319;BA.debugLine="lblRojo_Q6_a.visible = True";
mostCurrent._lblrojo_q6_a.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 2320;BA.debugLine="imgArboles.Visible = True";
mostCurrent._imgarboles.setVisible(anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 2322;BA.debugLine="End Sub";
return "";
}
public static String  _imgq6_b_click() throws Exception{
 //BA.debugLineNum = 2323;BA.debugLine="Private Sub imgQ6_b_Click";
 //BA.debugLineNum = 2324;BA.debugLine="If lblRojo_Q6_b.visible = True Then";
if (mostCurrent._lblrojo_q6_b.getVisible()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 2325;BA.debugLine="lblRojo_Q6_b.visible = False";
mostCurrent._lblrojo_q6_b.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 2326;BA.debugLine="imgArbustos.Visible = False";
mostCurrent._imgarbustos.setVisible(anywheresoftware.b4a.keywords.Common.False);
 }else {
 //BA.debugLineNum = 2328;BA.debugLine="lblRojo_Q6_b.visible = True";
mostCurrent._lblrojo_q6_b.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 2329;BA.debugLine="imgArbustos.Visible = True";
mostCurrent._imgarbustos.setVisible(anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 2331;BA.debugLine="End Sub";
return "";
}
public static String  _imgq6_c_click() throws Exception{
 //BA.debugLineNum = 2332;BA.debugLine="Private Sub imgQ6_c_Click";
 //BA.debugLineNum = 2333;BA.debugLine="If lblRojo_Q6_c.visible = True Then";
if (mostCurrent._lblrojo_q6_c.getVisible()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 2334;BA.debugLine="lblRojo_Q6_c.visible = False";
mostCurrent._lblrojo_q6_c.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 2335;BA.debugLine="imgPastoLargo.Visible = False";
mostCurrent._imgpastolargo.setVisible(anywheresoftware.b4a.keywords.Common.False);
 }else {
 //BA.debugLineNum = 2337;BA.debugLine="lblRojo_Q6_c.visible = True";
mostCurrent._lblrojo_q6_c.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 2338;BA.debugLine="imgPastoLargo.Visible = True";
mostCurrent._imgpastolargo.setVisible(anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 2340;BA.debugLine="End Sub";
return "";
}
public static String  _imgq6_d_click() throws Exception{
 //BA.debugLineNum = 2341;BA.debugLine="Private Sub imgQ6_d_Click";
 //BA.debugLineNum = 2342;BA.debugLine="If lblRojo_Q6_d.visible = True Then";
if (mostCurrent._lblrojo_q6_d.getVisible()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 2343;BA.debugLine="lblRojo_Q6_d.visible = False";
mostCurrent._lblrojo_q6_d.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 2344;BA.debugLine="imgPasto_Corto.Visible = False";
mostCurrent._imgpasto_corto.setVisible(anywheresoftware.b4a.keywords.Common.False);
 }else {
 //BA.debugLineNum = 2346;BA.debugLine="lblRojo_Q6_d.visible = True";
mostCurrent._lblrojo_q6_d.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 2347;BA.debugLine="imgPasto_Corto.Visible = True";
mostCurrent._imgpasto_corto.setVisible(anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 2349;BA.debugLine="End Sub";
return "";
}
public static String  _imgq6_e_click() throws Exception{
 //BA.debugLineNum = 2350;BA.debugLine="Private Sub imgQ6_e_Click";
 //BA.debugLineNum = 2351;BA.debugLine="lblRojo_Q6_a.visible = False";
mostCurrent._lblrojo_q6_a.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 2352;BA.debugLine="lblRojo_Q6_b.visible = False";
mostCurrent._lblrojo_q6_b.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 2353;BA.debugLine="lblRojo_Q6_c.visible = False";
mostCurrent._lblrojo_q6_c.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 2354;BA.debugLine="lblRojo_Q6_d.visible = False";
mostCurrent._lblrojo_q6_d.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 2355;BA.debugLine="imgPasto_Corto.Visible = False";
mostCurrent._imgpasto_corto.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 2356;BA.debugLine="imgPastoLargo.Visible = False";
mostCurrent._imgpastolargo.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 2357;BA.debugLine="imgArbustos.Visible = False";
mostCurrent._imgarbustos.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 2358;BA.debugLine="imgArboles.Visible = False";
mostCurrent._imgarboles.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 2360;BA.debugLine="If lblRojo_Q6_e.visible = True Then";
if (mostCurrent._lblrojo_q6_e.getVisible()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 2361;BA.debugLine="lblRojo_Q6_e.visible = False";
mostCurrent._lblrojo_q6_e.setVisible(anywheresoftware.b4a.keywords.Common.False);
 }else {
 //BA.debugLineNum = 2363;BA.debugLine="lblRojo_Q6_e.visible = True";
mostCurrent._lblrojo_q6_e.setVisible(anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 2365;BA.debugLine="End Sub";
return "";
}
public static String  _imgq7_a_click() throws Exception{
 //BA.debugLineNum = 2481;BA.debugLine="Private Sub imgQ7_a_Click";
 //BA.debugLineNum = 2482;BA.debugLine="If lblRojo_Q7_a.visible = True Then";
if (mostCurrent._lblrojo_q7_a.getVisible()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 2483;BA.debugLine="lblRojo_Q7_a.visible = False";
mostCurrent._lblrojo_q7_a.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 2484;BA.debugLine="imgBaño.Visible = False";
mostCurrent._imgbaño.setVisible(anywheresoftware.b4a.keywords.Common.False);
 }else {
 //BA.debugLineNum = 2486;BA.debugLine="lblRojo_Q7_a.visible = True";
mostCurrent._lblrojo_q7_a.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 2487;BA.debugLine="imgBaño.Visible = True";
mostCurrent._imgbaño.setVisible(anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 2489;BA.debugLine="End Sub";
return "";
}
public static String  _imgq7_b_click() throws Exception{
 //BA.debugLineNum = 2490;BA.debugLine="Private Sub imgQ7_b_Click";
 //BA.debugLineNum = 2491;BA.debugLine="If lblRojo_Q7_b.visible = True Then";
if (mostCurrent._lblrojo_q7_b.getVisible()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 2492;BA.debugLine="lblRojo_Q7_b.visible = False";
mostCurrent._lblrojo_q7_b.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 2493;BA.debugLine="imgPesca.Visible = False";
mostCurrent._imgpesca.setVisible(anywheresoftware.b4a.keywords.Common.False);
 }else {
 //BA.debugLineNum = 2495;BA.debugLine="lblRojo_Q7_b.visible = True";
mostCurrent._lblrojo_q7_b.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 2496;BA.debugLine="imgPesca.Visible = True";
mostCurrent._imgpesca.setVisible(anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 2498;BA.debugLine="End Sub";
return "";
}
public static String  _imgq7_c_click() throws Exception{
 //BA.debugLineNum = 2499;BA.debugLine="Private Sub imgQ7_c_Click";
 //BA.debugLineNum = 2500;BA.debugLine="If lblRojo_Q7_c.visible = True Then";
if (mostCurrent._lblrojo_q7_c.getVisible()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 2501;BA.debugLine="lblRojo_Q7_c.visible = False";
mostCurrent._lblrojo_q7_c.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 2502;BA.debugLine="imgKayak.Visible = False";
mostCurrent._imgkayak.setVisible(anywheresoftware.b4a.keywords.Common.False);
 }else {
 //BA.debugLineNum = 2504;BA.debugLine="lblRojo_Q7_c.visible = True";
mostCurrent._lblrojo_q7_c.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 2505;BA.debugLine="imgKayak.Visible = True";
mostCurrent._imgkayak.setVisible(anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 2507;BA.debugLine="End Sub";
return "";
}
public static String  _imgq7_d_click() throws Exception{
 //BA.debugLineNum = 2508;BA.debugLine="Private Sub imgQ7_d_Click";
 //BA.debugLineNum = 2509;BA.debugLine="If lblRojo_Q7_d.visible = True Then";
if (mostCurrent._lblrojo_q7_d.getVisible()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 2510;BA.debugLine="lblRojo_Q7_d.visible = False";
mostCurrent._lblrojo_q7_d.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 2511;BA.debugLine="imgEmbarcaciones.Visible = False";
mostCurrent._imgembarcaciones.setVisible(anywheresoftware.b4a.keywords.Common.False);
 }else {
 //BA.debugLineNum = 2513;BA.debugLine="lblRojo_Q7_d.visible = True";
mostCurrent._lblrojo_q7_d.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 2514;BA.debugLine="imgEmbarcaciones.Visible = True";
mostCurrent._imgembarcaciones.setVisible(anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 2516;BA.debugLine="End Sub";
return "";
}
public static String  _pnlresultados_click() throws Exception{
 //BA.debugLineNum = 1739;BA.debugLine="Sub pnlResultados_Click";
 //BA.debugLineNum = 1740;BA.debugLine="Return True";
if (true) return BA.ObjectToString(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1741;BA.debugLine="End Sub";
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
 //BA.debugLineNum = 689;BA.debugLine="Sub tabReporteHabitatRio_PageSelected (Position As";
 //BA.debugLineNum = 690;BA.debugLine="tabReporteHabitatRio.ScrollTo(currentpregunta,Fal";
mostCurrent._tabreportehabitatrio.ScrollTo(_currentpregunta,anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 692;BA.debugLine="End Sub";
return "";
}
public static String  _terminareval() throws Exception{
int _totpreg = 0;
String _vegetacion = "";
String _agua = "";
String _usos = "";
 //BA.debugLineNum = 1154;BA.debugLine="Sub TerminarEval";
 //BA.debugLineNum = 1155;BA.debugLine="Log(\"valorind1:\" & valorind1)";
anywheresoftware.b4a.keywords.Common.LogImpl("469992449","valorind1:"+mostCurrent._valorind1,0);
 //BA.debugLineNum = 1156;BA.debugLine="Log(\"valorind2:\" &valorind2)";
anywheresoftware.b4a.keywords.Common.LogImpl("469992450","valorind2:"+mostCurrent._valorind2,0);
 //BA.debugLineNum = 1157;BA.debugLine="Log(\"valorind3:\" &valorind3)";
anywheresoftware.b4a.keywords.Common.LogImpl("469992451","valorind3:"+mostCurrent._valorind3,0);
 //BA.debugLineNum = 1158;BA.debugLine="Log(\"valorind4:\" &valorind4)";
anywheresoftware.b4a.keywords.Common.LogImpl("469992452","valorind4:"+mostCurrent._valorind4,0);
 //BA.debugLineNum = 1159;BA.debugLine="Log(\"valorind5:\" &valorind5)";
anywheresoftware.b4a.keywords.Common.LogImpl("469992453","valorind5:"+mostCurrent._valorind5,0);
 //BA.debugLineNum = 1160;BA.debugLine="Log(\"valorind6:\" &valorind6)";
anywheresoftware.b4a.keywords.Common.LogImpl("469992454","valorind6:"+mostCurrent._valorind6,0);
 //BA.debugLineNum = 1161;BA.debugLine="Log(\"valorind7:\" &valorind7)";
anywheresoftware.b4a.keywords.Common.LogImpl("469992455","valorind7:"+mostCurrent._valorind7,0);
 //BA.debugLineNum = 1162;BA.debugLine="Log(\"valorind8:\" &valorind8)";
anywheresoftware.b4a.keywords.Common.LogImpl("469992456","valorind8:"+mostCurrent._valorind8,0);
 //BA.debugLineNum = 1163;BA.debugLine="Log(\"valorind9:\" &valorind9)";
anywheresoftware.b4a.keywords.Common.LogImpl("469992457","valorind9:"+mostCurrent._valorind9,0);
 //BA.debugLineNum = 1164;BA.debugLine="Log(\"valorind10:\" &valorind10)";
anywheresoftware.b4a.keywords.Common.LogImpl("469992458","valorind10:"+mostCurrent._valorind10,0);
 //BA.debugLineNum = 1165;BA.debugLine="Log(\"valorind11:\" &valorind11)";
anywheresoftware.b4a.keywords.Common.LogImpl("469992459","valorind11:"+mostCurrent._valorind11,0);
 //BA.debugLineNum = 1166;BA.debugLine="Log(\"valorind12:\" &valorind12)";
anywheresoftware.b4a.keywords.Common.LogImpl("469992460","valorind12:"+mostCurrent._valorind12,0);
 //BA.debugLineNum = 1167;BA.debugLine="Log(\"valorind13:\" &valorind13)";
anywheresoftware.b4a.keywords.Common.LogImpl("469992461","valorind13:"+mostCurrent._valorind13,0);
 //BA.debugLineNum = 1168;BA.debugLine="Log(\"valorind14:\" &valorind14)";
anywheresoftware.b4a.keywords.Common.LogImpl("469992462","valorind14:"+mostCurrent._valorind14,0);
 //BA.debugLineNum = 1169;BA.debugLine="Log(\"valorind15:\" &valorind15)";
anywheresoftware.b4a.keywords.Common.LogImpl("469992463","valorind15:"+mostCurrent._valorind15,0);
 //BA.debugLineNum = 1170;BA.debugLine="Log(\"valorind16:\" &valorind16)";
anywheresoftware.b4a.keywords.Common.LogImpl("469992464","valorind16:"+mostCurrent._valorind16,0);
 //BA.debugLineNum = 1171;BA.debugLine="Log(\"checklist:\" &checklist)";
anywheresoftware.b4a.keywords.Common.LogImpl("469992465","checklist:"+mostCurrent._checklist,0);
 //BA.debugLineNum = 1172;BA.debugLine="Log(\"ind_pvm_1:\" &ind_pvm_1)";
anywheresoftware.b4a.keywords.Common.LogImpl("469992466","ind_pvm_1:"+mostCurrent._ind_pvm_1,0);
 //BA.debugLineNum = 1173;BA.debugLine="Log(\"ind_pvm_2:\" &ind_pvm_2)";
anywheresoftware.b4a.keywords.Common.LogImpl("469992467","ind_pvm_2:"+mostCurrent._ind_pvm_2,0);
 //BA.debugLineNum = 1174;BA.debugLine="Log(\"ind_pvm_3:\" &ind_pvm_3)";
anywheresoftware.b4a.keywords.Common.LogImpl("469992468","ind_pvm_3:"+mostCurrent._ind_pvm_3,0);
 //BA.debugLineNum = 1175;BA.debugLine="Log(\"ind_pvm_4:\" &ind_pvm_4)";
anywheresoftware.b4a.keywords.Common.LogImpl("469992469","ind_pvm_4:"+mostCurrent._ind_pvm_4,0);
 //BA.debugLineNum = 1176;BA.debugLine="Log(\"ind_pvm_5:\" &ind_pvm_5)";
anywheresoftware.b4a.keywords.Common.LogImpl("469992470","ind_pvm_5:"+mostCurrent._ind_pvm_5,0);
 //BA.debugLineNum = 1177;BA.debugLine="Log(\"ind_pvm_6:\" &ind_pvm_6)";
anywheresoftware.b4a.keywords.Common.LogImpl("469992471","ind_pvm_6:"+mostCurrent._ind_pvm_6,0);
 //BA.debugLineNum = 1178;BA.debugLine="Log(\"ind_pvm_7:\" &ind_pvm_7)";
anywheresoftware.b4a.keywords.Common.LogImpl("469992472","ind_pvm_7:"+mostCurrent._ind_pvm_7,0);
 //BA.debugLineNum = 1179;BA.debugLine="Log(\"ind_pvm_8:\" &ind_pvm_8)";
anywheresoftware.b4a.keywords.Common.LogImpl("469992473","ind_pvm_8:"+mostCurrent._ind_pvm_8,0);
 //BA.debugLineNum = 1180;BA.debugLine="Log(\"ind_pvm_9:\" &ind_pvm_9)";
anywheresoftware.b4a.keywords.Common.LogImpl("469992474","ind_pvm_9:"+mostCurrent._ind_pvm_9,0);
 //BA.debugLineNum = 1181;BA.debugLine="Log(\"ind_pvm_10:\" &ind_pvm_10)";
anywheresoftware.b4a.keywords.Common.LogImpl("469992475","ind_pvm_10:"+mostCurrent._ind_pvm_10,0);
 //BA.debugLineNum = 1182;BA.debugLine="Log(\"ind_pvm_11:\" &ind_pvm_11)";
anywheresoftware.b4a.keywords.Common.LogImpl("469992476","ind_pvm_11:"+mostCurrent._ind_pvm_11,0);
 //BA.debugLineNum = 1183;BA.debugLine="Log(\"ind_pvm_12:\" &ind_pvm_12)";
anywheresoftware.b4a.keywords.Common.LogImpl("469992477","ind_pvm_12:"+mostCurrent._ind_pvm_12,0);
 //BA.debugLineNum = 1184;BA.debugLine="Log(\"ind_pvm_13:\" &ind_pvm_13)";
anywheresoftware.b4a.keywords.Common.LogImpl("469992478","ind_pvm_13:"+mostCurrent._ind_pvm_13,0);
 //BA.debugLineNum = 1185;BA.debugLine="Log(\"notas:\" & notas)";
anywheresoftware.b4a.keywords.Common.LogImpl("469992479","notas:"+mostCurrent._notas,0);
 //BA.debugLineNum = 1187;BA.debugLine="Log(\"dateandtime:\" &dateandtime)";
anywheresoftware.b4a.keywords.Common.LogImpl("469992481","dateandtime:"+mostCurrent._dateandtime,0);
 //BA.debugLineNum = 1190;BA.debugLine="pnlResultados.Visible = True";
mostCurrent._pnlresultados.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1191;BA.debugLine="pnlResultados.BringToFront";
mostCurrent._pnlresultados.BringToFront();
 //BA.debugLineNum = 1193;BA.debugLine="Dim totpreg As Int = 0";
_totpreg = (int) (0);
 //BA.debugLineNum = 1194;BA.debugLine="valorNS = 0";
_valorns = (int) (0);
 //BA.debugLineNum = 1197;BA.debugLine="CargarDetalle";
_cargardetalle();
 //BA.debugLineNum = 1200;BA.debugLine="If valorind1 = \"NS\" Or valorind1 = \"\" Then ' usos";
if ((mostCurrent._valorind1).equals("NS") || (mostCurrent._valorind1).equals("")) { 
 //BA.debugLineNum = 1201;BA.debugLine="valorind1 = \"0\"";
mostCurrent._valorind1 = "0";
 //BA.debugLineNum = 1202;BA.debugLine="valorNS = valorNS + 1";
_valorns = (int) (_valorns+1);
 }else {
 //BA.debugLineNum = 1204;BA.debugLine="totpreg = totpreg + 4";
_totpreg = (int) (_totpreg+4);
 };
 //BA.debugLineNum = 1207;BA.debugLine="If valorind2 = \"NS\" Or valorind2 = \"\" Then 'ganad";
if ((mostCurrent._valorind2).equals("NS") || (mostCurrent._valorind2).equals("")) { 
 //BA.debugLineNum = 1208;BA.debugLine="valorind2 = \"0\"";
mostCurrent._valorind2 = "0";
 //BA.debugLineNum = 1209;BA.debugLine="valorNS = valorNS + 1";
_valorns = (int) (_valorns+1);
 }else {
 //BA.debugLineNum = 1211;BA.debugLine="totpreg = totpreg + 1";
_totpreg = (int) (_totpreg+1);
 };
 //BA.debugLineNum = 1213;BA.debugLine="If valorind3 = \"NS\" Or valorind3 = \"\" Then ' veg";
if ((mostCurrent._valorind3).equals("NS") || (mostCurrent._valorind3).equals("")) { 
 //BA.debugLineNum = 1214;BA.debugLine="valorind3 = \"0\"";
mostCurrent._valorind3 = "0";
 //BA.debugLineNum = 1215;BA.debugLine="valorNS = valorNS + 1";
_valorns = (int) (_valorns+1);
 }else {
 //BA.debugLineNum = 1217;BA.debugLine="totpreg = totpreg + 2";
_totpreg = (int) (_totpreg+2);
 };
 //BA.debugLineNum = 1219;BA.debugLine="If valorind4 = \"NS\" Or valorind4 = \"\" Then 'acuat";
if ((mostCurrent._valorind4).equals("NS") || (mostCurrent._valorind4).equals("")) { 
 //BA.debugLineNum = 1220;BA.debugLine="valorind4 = \"0\"";
mostCurrent._valorind4 = "0";
 //BA.debugLineNum = 1221;BA.debugLine="valorNS = valorNS + 1";
_valorns = (int) (_valorns+1);
 }else {
 //BA.debugLineNum = 1223;BA.debugLine="totpreg = totpreg + 2";
_totpreg = (int) (_totpreg+2);
 };
 //BA.debugLineNum = 1226;BA.debugLine="If valorind5 = \"NS\" Or valorind5 = \"\" Then ' colo";
if ((mostCurrent._valorind5).equals("NS") || (mostCurrent._valorind5).equals("")) { 
 //BA.debugLineNum = 1227;BA.debugLine="valorind5 = \"0\"";
mostCurrent._valorind5 = "0";
 //BA.debugLineNum = 1228;BA.debugLine="valorNS = valorNS + 1";
_valorns = (int) (_valorns+1);
 }else {
 //BA.debugLineNum = 1230;BA.debugLine="totpreg = totpreg + 1";
_totpreg = (int) (_totpreg+1);
 };
 //BA.debugLineNum = 1232;BA.debugLine="If valorind6 = \"NS\" Or valorind6 = \"\" Then ' olor";
if ((mostCurrent._valorind6).equals("NS") || (mostCurrent._valorind6).equals("")) { 
 //BA.debugLineNum = 1233;BA.debugLine="valorind6 = \"0\"";
mostCurrent._valorind6 = "0";
 //BA.debugLineNum = 1234;BA.debugLine="valorNS = valorNS + 1";
_valorns = (int) (_valorns+1);
 }else {
 //BA.debugLineNum = 1236;BA.debugLine="totpreg = totpreg + 1";
_totpreg = (int) (_totpreg+1);
 };
 //BA.debugLineNum = 1238;BA.debugLine="If valorind7 = \"NS\" Or valorind7 = \"\" Then 'basur";
if ((mostCurrent._valorind7).equals("NS") || (mostCurrent._valorind7).equals("")) { 
 //BA.debugLineNum = 1239;BA.debugLine="valorind7 = \"0\"";
mostCurrent._valorind7 = "0";
 //BA.debugLineNum = 1240;BA.debugLine="valorNS = valorNS + 1";
_valorns = (int) (_valorns+1);
 }else {
 //BA.debugLineNum = 1242;BA.debugLine="totpreg = totpreg + 2";
_totpreg = (int) (_totpreg+2);
 };
 //BA.debugLineNum = 1245;BA.debugLine="If ind_pvm_1 = \"NS\" Or ind_pvm_1 = \"\" Then ' espu";
if ((mostCurrent._ind_pvm_1).equals("NS") || (mostCurrent._ind_pvm_1).equals("")) { 
 //BA.debugLineNum = 1246;BA.debugLine="ind_pvm_1 = \"0\"";
mostCurrent._ind_pvm_1 = "0";
 //BA.debugLineNum = 1247;BA.debugLine="valorNS = valorNS + 1";
_valorns = (int) (_valorns+1);
 }else {
 //BA.debugLineNum = 1249;BA.debugLine="totpreg = totpreg + 2";
_totpreg = (int) (_totpreg+2);
 };
 //BA.debugLineNum = 1251;BA.debugLine="If ind_pvm_2 = \"NS\" Or ind_pvm_2 = \"\" Then 'junco";
if ((mostCurrent._ind_pvm_2).equals("NS") || (mostCurrent._ind_pvm_2).equals("")) { 
 //BA.debugLineNum = 1252;BA.debugLine="ind_pvm_2 = \"0\"";
mostCurrent._ind_pvm_2 = "0";
 //BA.debugLineNum = 1253;BA.debugLine="valorNS = valorNS + 1";
_valorns = (int) (_valorns+1);
 }else {
 //BA.debugLineNum = 1255;BA.debugLine="totpreg = totpreg + 1";
_totpreg = (int) (_totpreg+1);
 };
 //BA.debugLineNum = 1257;BA.debugLine="If ind_pvm_3 = \"NS\" Or ind_pvm_3 = \"\" Then 'acaci";
if ((mostCurrent._ind_pvm_3).equals("NS") || (mostCurrent._ind_pvm_3).equals("")) { 
 //BA.debugLineNum = 1258;BA.debugLine="ind_pvm_3 = \"0\"";
mostCurrent._ind_pvm_3 = "0";
 //BA.debugLineNum = 1259;BA.debugLine="valorNS = valorNS + 1";
_valorns = (int) (_valorns+1);
 }else {
 //BA.debugLineNum = 1261;BA.debugLine="totpreg = totpreg + 1";
_totpreg = (int) (_totpreg+1);
 };
 //BA.debugLineNum = 1263;BA.debugLine="If ind_pvm_4 = \"NS\" Or ind_pvm_4 = \"\" Then' ligus";
if ((mostCurrent._ind_pvm_4).equals("NS") || (mostCurrent._ind_pvm_4).equals("")) { 
 //BA.debugLineNum = 1264;BA.debugLine="ind_pvm_4 = \"0\"";
mostCurrent._ind_pvm_4 = "0";
 //BA.debugLineNum = 1265;BA.debugLine="valorNS = valorNS + 1";
_valorns = (int) (_valorns+1);
 }else {
 //BA.debugLineNum = 1267;BA.debugLine="totpreg = totpreg + 1";
_totpreg = (int) (_totpreg+1);
 };
 //BA.debugLineNum = 1272;BA.debugLine="If valorNS >= 6  Then";
if (_valorns>=6) { 
 //BA.debugLineNum = 1273;BA.debugLine="MsgboxAsync(\"Has seleccionado 'No sé' en muchas";
anywheresoftware.b4a.keywords.Common.MsgboxAsync(BA.ObjectToCharSequence("Has seleccionado 'No sé' en muchas opciones en la encuesta, es probable que el estado del hábitat no sea calculado con precisión"),BA.ObjectToCharSequence("Cuidado!"),processBA);
 };
 //BA.debugLineNum = 1275;BA.debugLine="valorNS = Round((valorNS*100)/11) 'NS";
_valorns = (int) (anywheresoftware.b4a.keywords.Common.Round((_valorns*100)/(double)11));
 //BA.debugLineNum = 1277;BA.debugLine="Dim vegetacion, agua, usos As String";
_vegetacion = "";
_agua = "";
_usos = "";
 //BA.debugLineNum = 1278;BA.debugLine="vegetacion = valorind3*2 + valorind4*2 + ind_pvm_";
_vegetacion = BA.NumberToString((double)(Double.parseDouble(mostCurrent._valorind3))*2+(double)(Double.parseDouble(mostCurrent._valorind4))*2+(double)(Double.parseDouble(mostCurrent._ind_pvm_2))+(double)(Double.parseDouble(mostCurrent._ind_pvm_3))+(double)(Double.parseDouble(mostCurrent._ind_pvm_4)));
 //BA.debugLineNum = 1279;BA.debugLine="agua = valorind5 + valorind6 + valorind7*2 + ind_";
_agua = BA.NumberToString((double)(Double.parseDouble(mostCurrent._valorind5))+(double)(Double.parseDouble(mostCurrent._valorind6))+(double)(Double.parseDouble(mostCurrent._valorind7))*2+(double)(Double.parseDouble(mostCurrent._ind_pvm_1))*2);
 //BA.debugLineNum = 1280;BA.debugLine="usos = valorind1*4+ valorind2";
_usos = BA.NumberToString((double)(Double.parseDouble(mostCurrent._valorind1))*4+(double)(Double.parseDouble(mostCurrent._valorind2)));
 //BA.debugLineNum = 1282;BA.debugLine="valorcalidad = ((vegetacion + agua + usos)/(totpr";
_valorcalidad = (((double)(Double.parseDouble(_vegetacion))+(double)(Double.parseDouble(_agua))+(double)(Double.parseDouble(_usos)))/(double)(_totpreg*10))*100;
 //BA.debugLineNum = 1286;BA.debugLine="valorcalidad = Round2(valorcalidad,1)";
_valorcalidad = anywheresoftware.b4a.keywords.Common.Round2(_valorcalidad,(int) (1));
 //BA.debugLineNum = 1291;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 1292;BA.debugLine="If valorcalidad < 20 Then";
if (_valorcalidad<20) { 
 //BA.debugLineNum = 1293;BA.debugLine="lblEstado.Text = \"Muy malo\"";
mostCurrent._lblestado.setText(BA.ObjectToCharSequence("Muy malo"));
 }else if(_valorcalidad>=20 && _valorcalidad<40) { 
 //BA.debugLineNum = 1295;BA.debugLine="lblEstado.Text = \"Malo\"";
mostCurrent._lblestado.setText(BA.ObjectToCharSequence("Malo"));
 }else if(_valorcalidad>=40 && _valorcalidad<60) { 
 //BA.debugLineNum = 1297;BA.debugLine="lblEstado.Text = \"Regular\"";
mostCurrent._lblestado.setText(BA.ObjectToCharSequence("Regular"));
 }else if(_valorcalidad>=60 && _valorcalidad<80) { 
 //BA.debugLineNum = 1299;BA.debugLine="lblEstado.Text = \"Bueno\"";
mostCurrent._lblestado.setText(BA.ObjectToCharSequence("Bueno"));
 }else if(_valorcalidad>=80) { 
 //BA.debugLineNum = 1301;BA.debugLine="lblEstado.Text = \"Muy bueno!\"";
mostCurrent._lblestado.setText(BA.ObjectToCharSequence("Muy bueno!"));
 };
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 1304;BA.debugLine="If valorcalidad < 20 Then";
if (_valorcalidad<20) { 
 //BA.debugLineNum = 1305;BA.debugLine="lblEstado.Text = \"Very bad\"";
mostCurrent._lblestado.setText(BA.ObjectToCharSequence("Very bad"));
 }else if(_valorcalidad>=20 && _valorcalidad<40) { 
 //BA.debugLineNum = 1307;BA.debugLine="lblEstado.Text = \"Bad\"";
mostCurrent._lblestado.setText(BA.ObjectToCharSequence("Bad"));
 }else if(_valorcalidad>=40 && _valorcalidad<60) { 
 //BA.debugLineNum = 1309;BA.debugLine="lblEstado.Text = \"Regular\"";
mostCurrent._lblestado.setText(BA.ObjectToCharSequence("Regular"));
 }else if(_valorcalidad>=60 && _valorcalidad<80) { 
 //BA.debugLineNum = 1311;BA.debugLine="lblEstado.Text = \"Good\"";
mostCurrent._lblestado.setText(BA.ObjectToCharSequence("Good"));
 }else if(_valorcalidad>=80) { 
 //BA.debugLineNum = 1313;BA.debugLine="lblEstado.Text = \"Very good!\"";
mostCurrent._lblestado.setText(BA.ObjectToCharSequence("Very good!"));
 };
 };
 //BA.debugLineNum = 1317;BA.debugLine="Log(\"valorNS:\" &valorNS)";
anywheresoftware.b4a.keywords.Common.LogImpl("469992611","valorNS:"+BA.NumberToString(_valorns),0);
 //BA.debugLineNum = 1318;BA.debugLine="Log(\"valorcalidad:\" &valorcalidad)";
anywheresoftware.b4a.keywords.Common.LogImpl("469992612","valorcalidad:"+BA.NumberToString(_valorcalidad),0);
 //BA.debugLineNum = 1321;BA.debugLine="Gauge1.SetRanges(Array(Gauge1.CreateRange(0, 20,";
mostCurrent._gauge1._setranges /*String*/ (anywheresoftware.b4a.keywords.Common.ArrayToList(new Object[]{(Object)(mostCurrent._gauge1._createrange /*appear.pnud.preservamos.gauge._gaugerange*/ ((float) (0),(float) (20),mostCurrent._xui.Color_DarkGray)),(Object)(mostCurrent._gauge1._createrange /*appear.pnud.preservamos.gauge._gaugerange*/ ((float) (20),(float) (40),mostCurrent._xui.Color_Red)),(Object)(mostCurrent._gauge1._createrange /*appear.pnud.preservamos.gauge._gaugerange*/ ((float) (40),(float) (60),mostCurrent._xui.Color_Yellow)),(Object)(mostCurrent._gauge1._createrange /*appear.pnud.preservamos.gauge._gaugerange*/ ((float) (60),(float) (80),mostCurrent._xui.Color_Green)),(Object)(mostCurrent._gauge1._createrange /*appear.pnud.preservamos.gauge._gaugerange*/ ((float) (80),(float) (100),mostCurrent._xui.Color_Blue))}));
 //BA.debugLineNum = 1322;BA.debugLine="Gauge1.CurrentValue = 0";
mostCurrent._gauge1._setcurrentvalue /*float*/ ((float) (0));
 //BA.debugLineNum = 1323;BA.debugLine="Gauge1.CurrentValue = valorcalidad";
mostCurrent._gauge1._setcurrentvalue /*float*/ ((float) (_valorcalidad));
 //BA.debugLineNum = 1326;BA.debugLine="GuardarEval";
_guardareval();
 //BA.debugLineNum = 1327;BA.debugLine="End Sub";
return "";
}
public static String  _zoomq6_2_click() throws Exception{
anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper _bbmp = null;
 //BA.debugLineNum = 1755;BA.debugLine="Private Sub zoomQ6_2_Click";
 //BA.debugLineNum = 1757;BA.debugLine="currentscreen = \"foto\"";
mostCurrent._currentscreen = "foto";
 //BA.debugLineNum = 1759;BA.debugLine="fondogris.Initialize(\"fondogris\")";
mostCurrent._fondogris.Initialize(mostCurrent.activityBA,"fondogris");
 //BA.debugLineNum = 1760;BA.debugLine="fondogris.Color = Colors.ARGB(150,0,0,0)";
mostCurrent._fondogris.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (150),(int) (0),(int) (0),(int) (0)));
 //BA.debugLineNum = 1761;BA.debugLine="Activity.AddView(fondogris, 0, 0, 100%x, 100%y)";
mostCurrent._activity.AddView((android.view.View)(mostCurrent._fondogris.getObject()),(int) (0),(int) (0),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA));
 //BA.debugLineNum = 1764;BA.debugLine="img1 = imgQ6_2.Bitmap";
mostCurrent._img1 = (anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper(), (android.graphics.Bitmap)(mostCurrent._imgq6_2.getBitmap()));
 //BA.debugLineNum = 1765;BA.debugLine="mag1.Initialize(\"mag1\")";
mostCurrent._mag1.Initialize(mostCurrent.activityBA,"mag1");
 //BA.debugLineNum = 1767;BA.debugLine="Activity.AddView(mag1, 0, Activity.Height / 6, Ac";
mostCurrent._activity.AddView((android.view.View)(mostCurrent._mag1.getObject()),(int) (0),(int) (mostCurrent._activity.getHeight()/(double)6),mostCurrent._activity.getWidth(),(int) (mostCurrent._activity.getHeight()/(double)1.5));
 //BA.debugLineNum = 1768;BA.debugLine="Dim bbmp As Bitmap";
_bbmp = new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper();
 //BA.debugLineNum = 1769;BA.debugLine="bbmp.Initialize3(img1)";
_bbmp.Initialize3((android.graphics.Bitmap)(mostCurrent._img1.getObject()));
 //BA.debugLineNum = 1770;BA.debugLine="mag1.MagnifizeBitmap = bbmp";
mostCurrent._mag1.setMagnifizeBitmap((android.graphics.Bitmap)(_bbmp.getObject()));
 //BA.debugLineNum = 1771;BA.debugLine="End Sub";
return "";
}
public static String  _zoomq6_3_click() throws Exception{
anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper _bbmp = null;
 //BA.debugLineNum = 1773;BA.debugLine="Private Sub zoomQ6_3_Click";
 //BA.debugLineNum = 1775;BA.debugLine="currentscreen = \"foto\"";
mostCurrent._currentscreen = "foto";
 //BA.debugLineNum = 1777;BA.debugLine="fondogris.Initialize(\"fondogris\")";
mostCurrent._fondogris.Initialize(mostCurrent.activityBA,"fondogris");
 //BA.debugLineNum = 1778;BA.debugLine="fondogris.Color = Colors.ARGB(150,0,0,0)";
mostCurrent._fondogris.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (150),(int) (0),(int) (0),(int) (0)));
 //BA.debugLineNum = 1779;BA.debugLine="Activity.AddView(fondogris, 0, 0, 100%x, 100%y)";
mostCurrent._activity.AddView((android.view.View)(mostCurrent._fondogris.getObject()),(int) (0),(int) (0),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA));
 //BA.debugLineNum = 1782;BA.debugLine="img1 = imgQ6_3.Bitmap";
mostCurrent._img1 = (anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper(), (android.graphics.Bitmap)(mostCurrent._imgq6_3.getBitmap()));
 //BA.debugLineNum = 1783;BA.debugLine="mag1.Initialize(\"mag1\")";
mostCurrent._mag1.Initialize(mostCurrent.activityBA,"mag1");
 //BA.debugLineNum = 1785;BA.debugLine="Activity.AddView(mag1, 0, Activity.Height / 6, Ac";
mostCurrent._activity.AddView((android.view.View)(mostCurrent._mag1.getObject()),(int) (0),(int) (mostCurrent._activity.getHeight()/(double)6),mostCurrent._activity.getWidth(),(int) (mostCurrent._activity.getHeight()/(double)1.5));
 //BA.debugLineNum = 1786;BA.debugLine="Dim bbmp As Bitmap";
_bbmp = new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper();
 //BA.debugLineNum = 1787;BA.debugLine="bbmp.Initialize3(img1)";
_bbmp.Initialize3((android.graphics.Bitmap)(mostCurrent._img1.getObject()));
 //BA.debugLineNum = 1788;BA.debugLine="mag1.MagnifizeBitmap = bbmp";
mostCurrent._mag1.setMagnifizeBitmap((android.graphics.Bitmap)(_bbmp.getObject()));
 //BA.debugLineNum = 1789;BA.debugLine="End Sub";
return "";
}
}
