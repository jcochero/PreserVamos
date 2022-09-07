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

public class frmperfil extends Activity implements B4AActivity{
	public static frmperfil mostCurrent;
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
			processBA = new BA(this.getApplicationContext(), null, null, "appear.pnud.preservamos", "appear.pnud.preservamos.frmperfil");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
            
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (frmperfil).");
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
		activityBA = new BA(this, layout, processBA, "appear.pnud.preservamos", "appear.pnud.preservamos.frmperfil");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "appear.pnud.preservamos.frmperfil", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density, mostCurrent);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (frmperfil) Create, isFirst = " + isFirst + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (frmperfil) Resume **");
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
		return frmperfil.class;
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
            BA.LogInfo("** Activity (frmperfil) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
        else
            BA.LogInfo("** Activity (frmperfil) Pause event (activity is not paused). **");
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
            frmperfil mc = mostCurrent;
			if (mc == null || mc != activity.get())
				return;
			processBA.setActivityPaused(false);
            BA.LogInfo("** Activity (frmperfil) Resume **");
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
public anywheresoftware.b4a.objects.PanelWrapper _pnlbadges = null;
public anywheresoftware.b4a.objects.ProgressBarWrapper _pgblevel = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblnivel = null;
public anywheresoftware.b4a.objects.ListViewWrapper _lstbadges = null;
public anywheresoftware.b4a.objects.ListViewWrapper _lstachievements = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imgbadge = null;
public static String _imagetoshare = "";
public anywheresoftware.b4a.objects.LabelWrapper _lbltusmedallas = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbltuslogros = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblpuntostotales = null;
public anywheresoftware.b4a.objects.LabelWrapper _txtpuntostotales = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblevalsok = null;
public anywheresoftware.b4a.objects.LabelWrapper _txtevalsok = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btncerrar = null;
public anywheresoftware.b4a.objects.TabStripViewPager _tabstripperfil = null;
public anywheresoftware.b4a.objects.SpinnerWrapper _spnuserlevel = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbltitulo = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnshare = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnshareothers = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblnocompartir = null;
public static String _nivel = "";
public anywheresoftware.b4a.objects.PanelWrapper _fondogris = null;
public anywheresoftware.b4a.objects.PanelWrapper _pnlshare = null;
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
 //BA.debugLineNum = 47;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
 //BA.debugLineNum = 49;BA.debugLine="Activity.LoadLayout(\"layPerfilBadges\")";
mostCurrent._activity.LoadLayout("layPerfilBadges",mostCurrent.activityBA);
 //BA.debugLineNum = 51;BA.debugLine="TraducirGUI";
_traducirgui();
 //BA.debugLineNum = 55;BA.debugLine="If Main.lang = \"en\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 56;BA.debugLine="ProgressDialogShow2(\"Retrieving medals...\",False";
anywheresoftware.b4a.keywords.Common.ProgressDialogShow2(mostCurrent.activityBA,BA.ObjectToCharSequence("Retrieving medals..."),anywheresoftware.b4a.keywords.Common.False);
 }else {
 //BA.debugLineNum = 58;BA.debugLine="ProgressDialogShow2(\"Buscando medallas...\",False";
anywheresoftware.b4a.keywords.Common.ProgressDialogShow2(mostCurrent.activityBA,BA.ObjectToCharSequence("Buscando medallas..."),anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 62;BA.debugLine="CheckInternet";
_checkinternet();
 //BA.debugLineNum = 63;BA.debugLine="End Sub";
return "";
}
public static boolean  _activity_keypress(int _keycode) throws Exception{
 //BA.debugLineNum = 70;BA.debugLine="Sub Activity_KeyPress (KeyCode As Int) As Boolean";
 //BA.debugLineNum = 72;BA.debugLine="If KeyCode = KeyCodes.KEYCODE_BACK Then";
if (_keycode==anywheresoftware.b4a.keywords.Common.KeyCodes.KEYCODE_BACK) { 
 //BA.debugLineNum = 73;BA.debugLine="Activity.finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 74;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 };
 //BA.debugLineNum = 76;BA.debugLine="End Sub";
return false;
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 67;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 69;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 65;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 66;BA.debugLine="End Sub";
return "";
}
public static String  _btncerrarbadges_click() throws Exception{
 //BA.debugLineNum = 94;BA.debugLine="Sub btnCerrarBadges_Click";
 //BA.debugLineNum = 95;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 96;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 97;BA.debugLine="End Sub";
return "";
}
public static String  _cargarmedallas() throws Exception{
 //BA.debugLineNum = 228;BA.debugLine="Sub cargarMedallas";
 //BA.debugLineNum = 231;BA.debugLine="lstBadges.Clear";
mostCurrent._lstbadges.Clear();
 //BA.debugLineNum = 232;BA.debugLine="lstAchievements.Clear";
mostCurrent._lstachievements.Clear();
 //BA.debugLineNum = 234;BA.debugLine="lstBadges.TwoLinesAndBitmap.ItemHeight = 160dip";
mostCurrent._lstbadges.getTwoLinesAndBitmap().setItemHeight(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (160)));
 //BA.debugLineNum = 235;BA.debugLine="lstBadges.TwoLinesAndBitmap.ImageView.Left = 20di";
mostCurrent._lstbadges.getTwoLinesAndBitmap().ImageView.setLeft(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (20)));
 //BA.debugLineNum = 236;BA.debugLine="lstBadges.TwoLinesAndBitmap.ImageView.Gravity = G";
mostCurrent._lstbadges.getTwoLinesAndBitmap().ImageView.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.FILL);
 //BA.debugLineNum = 237;BA.debugLine="lstBadges.TwoLinesAndBitmap.ImageView.Width = 130";
mostCurrent._lstbadges.getTwoLinesAndBitmap().ImageView.setWidth(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (130)));
 //BA.debugLineNum = 238;BA.debugLine="lstBadges.TwoLinesAndBitmap.ImageView.Height = 13";
mostCurrent._lstbadges.getTwoLinesAndBitmap().ImageView.setHeight(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (130)));
 //BA.debugLineNum = 239;BA.debugLine="lstBadges.TwoLinesAndBitmap.ImageView.top = 5dip";
mostCurrent._lstbadges.getTwoLinesAndBitmap().ImageView.setTop(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5)));
 //BA.debugLineNum = 240;BA.debugLine="lstBadges.TwoLinesAndBitmap.Label.Visible = False";
mostCurrent._lstbadges.getTwoLinesAndBitmap().Label.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 241;BA.debugLine="lstBadges.TwoLinesAndBitmap.Secondlabel.Visible =";
mostCurrent._lstbadges.getTwoLinesAndBitmap().SecondLabel.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 243;BA.debugLine="lstAchievements.TwoLinesAndBitmap.ItemHeight = 16";
mostCurrent._lstachievements.getTwoLinesAndBitmap().setItemHeight(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (160)));
 //BA.debugLineNum = 244;BA.debugLine="lstAchievements.TwoLinesAndBitmap.ImageView.Left";
mostCurrent._lstachievements.getTwoLinesAndBitmap().ImageView.setLeft(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (20)));
 //BA.debugLineNum = 245;BA.debugLine="lstAchievements.TwoLinesAndBitmap.ImageView.Gravi";
mostCurrent._lstachievements.getTwoLinesAndBitmap().ImageView.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.FILL);
 //BA.debugLineNum = 246;BA.debugLine="lstAchievements.TwoLinesAndBitmap.ImageView.Width";
mostCurrent._lstachievements.getTwoLinesAndBitmap().ImageView.setWidth(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (130)));
 //BA.debugLineNum = 247;BA.debugLine="lstAchievements.TwoLinesAndBitmap.ImageView.Heigh";
mostCurrent._lstachievements.getTwoLinesAndBitmap().ImageView.setHeight(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (130)));
 //BA.debugLineNum = 248;BA.debugLine="lstAchievements.TwoLinesAndBitmap.ImageView.top =";
mostCurrent._lstachievements.getTwoLinesAndBitmap().ImageView.setTop(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5)));
 //BA.debugLineNum = 249;BA.debugLine="lstAchievements.TwoLinesAndBitmap.Label.Visible =";
mostCurrent._lstachievements.getTwoLinesAndBitmap().Label.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 250;BA.debugLine="lstAchievements.TwoLinesAndBitmap.Secondlabel.Vis";
mostCurrent._lstachievements.getTwoLinesAndBitmap().SecondLabel.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 252;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 254;BA.debugLine="If Main.numevalsok = 1 Then";
if ((mostCurrent._main._numevalsok /*String*/ ).equals(BA.NumberToString(1))) { 
 //BA.debugLineNum = 255;BA.debugLine="lstBadges.AddTwoLinesAndBitmap2(\"Novato\", \"Una";
mostCurrent._lstbadges.AddTwoLinesAndBitmap2(BA.ObjectToCharSequence("Novato"),BA.ObjectToCharSequence("Una evaluación completa"),(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._main._lang /*String*/ +"-novato.png").getObject()),(Object)("novato"));
 };
 //BA.debugLineNum = 257;BA.debugLine="If Main.numevalsok >= 3 Then";
if ((double)(Double.parseDouble(mostCurrent._main._numevalsok /*String*/ ))>=3) { 
 //BA.debugLineNum = 258;BA.debugLine="lstBadges.AddTwoLinesAndBitmap2(\"Aprendiz\", \"Tr";
mostCurrent._lstbadges.AddTwoLinesAndBitmap2(BA.ObjectToCharSequence("Aprendiz"),BA.ObjectToCharSequence("Tres evaluaciones completas"),(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._main._lang /*String*/ +"-aprendiz.png").getObject()),(Object)("aprendiz"));
 };
 //BA.debugLineNum = 260;BA.debugLine="If Main.numevalsok >= 5 Then";
if ((double)(Double.parseDouble(mostCurrent._main._numevalsok /*String*/ ))>=5) { 
 //BA.debugLineNum = 261;BA.debugLine="lstBadges.AddTwoLinesAndBitmap2(\"Aventurero\", \"";
mostCurrent._lstbadges.AddTwoLinesAndBitmap2(BA.ObjectToCharSequence("Aventurero"),BA.ObjectToCharSequence("Cinco evaluaciones completas"),(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._main._lang /*String*/ +"-aventurero.png").getObject()),(Object)("aventurero"));
 };
 //BA.debugLineNum = 263;BA.debugLine="If Main.numevalsok >= 15 Then";
if ((double)(Double.parseDouble(mostCurrent._main._numevalsok /*String*/ ))>=15) { 
 //BA.debugLineNum = 264;BA.debugLine="lstBadges.AddTwoLinesAndBitmap2(\"Explorador\", \"";
mostCurrent._lstbadges.AddTwoLinesAndBitmap2(BA.ObjectToCharSequence("Explorador"),BA.ObjectToCharSequence("Quince evaluaciones completas"),(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._main._lang /*String*/ +"-explorador.png").getObject()),(Object)("explorador"));
 };
 //BA.debugLineNum = 266;BA.debugLine="If Main.numevalsok >= 30 Then";
if ((double)(Double.parseDouble(mostCurrent._main._numevalsok /*String*/ ))>=30) { 
 //BA.debugLineNum = 267;BA.debugLine="lstBadges.AddTwoLinesAndBitmap2(\"Legendario\", \"";
mostCurrent._lstbadges.AddTwoLinesAndBitmap2(BA.ObjectToCharSequence("Legendario"),BA.ObjectToCharSequence("Treinta evaluaciones completas!"),(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._main._lang /*String*/ +"-legendario.png").getObject()),(Object)("legendario"));
 };
 //BA.debugLineNum = 272;BA.debugLine="If Main.numfotosok >= 16 Then";
if ((double)(Double.parseDouble(mostCurrent._main._numfotosok /*String*/ ))>=16) { 
 //BA.debugLineNum = 273;BA.debugLine="lstBadges.AddTwoLinesAndBitmap2(\"Fotogénico\", \"";
mostCurrent._lstbadges.AddTwoLinesAndBitmap2(BA.ObjectToCharSequence("Fotogénico"),BA.ObjectToCharSequence("16 fotos enviadas"),(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._main._lang /*String*/ +"-fotogenico.png").getObject()),(Object)("fotogenico"));
 };
 //BA.debugLineNum = 275;BA.debugLine="If Main.numfotosok >= 120 Then";
if ((double)(Double.parseDouble(mostCurrent._main._numfotosok /*String*/ ))>=120) { 
 //BA.debugLineNum = 276;BA.debugLine="lstBadges.AddTwoLinesAndBitmap2(\"Púlitzer fotog";
mostCurrent._lstbadges.AddTwoLinesAndBitmap2(BA.ObjectToCharSequence("Púlitzer fotográfico"),BA.ObjectToCharSequence("120 fotos enviadas!!!"),(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._main._lang /*String*/ +"-pulitzer.png").getObject()),(Object)("pulitzer"));
 };
 //BA.debugLineNum = 280;BA.debugLine="If Main.numshares >= 10 Then";
if ((double)(Double.parseDouble(mostCurrent._main._numshares /*String*/ ))>=10) { 
 //BA.debugLineNum = 281;BA.debugLine="lstBadges.AddTwoLinesAndBitmap2(\"Sociable\", \"10";
mostCurrent._lstbadges.AddTwoLinesAndBitmap2(BA.ObjectToCharSequence("Sociable"),BA.ObjectToCharSequence("10 evaluaciones compartidas en Facebook"),(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._main._lang /*String*/ +"-sociable.png").getObject()),(Object)("sociable"));
 };
 //BA.debugLineNum = 285;BA.debugLine="If Main.numriollanura >= 1 Then";
if ((double)(Double.parseDouble(mostCurrent._main._numriollanura /*String*/ ))>=1) { 
 //BA.debugLineNum = 286;BA.debugLine="lstAchievements.AddTwoLinesAndBitmap2(\"Llanura\"";
mostCurrent._lstachievements.AddTwoLinesAndBitmap2(BA.ObjectToCharSequence("Llanura"),BA.ObjectToCharSequence("Completaste tu primera evaluación de un río de llanura"),(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._main._lang /*String*/ +"-llanura.png").getObject()),(Object)("llanura"));
 };
 //BA.debugLineNum = 288;BA.debugLine="If Main.numriomontana >= 1 Then";
if ((double)(Double.parseDouble(mostCurrent._main._numriomontana /*String*/ ))>=1) { 
 //BA.debugLineNum = 289;BA.debugLine="lstAchievements.AddTwoLinesAndBitmap2(\"Montaña\"";
mostCurrent._lstachievements.AddTwoLinesAndBitmap2(BA.ObjectToCharSequence("Montaña"),BA.ObjectToCharSequence("Completaste tu primera evaluación de un río de montaña"),(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._main._lang /*String*/ +"-montana.png").getObject()),(Object)("montana"));
 };
 //BA.debugLineNum = 291;BA.debugLine="If Main.numestuario >= 1 Then";
if ((double)(Double.parseDouble(mostCurrent._main._numestuario /*String*/ ))>=1) { 
 //BA.debugLineNum = 292;BA.debugLine="lstAchievements.AddTwoLinesAndBitmap2(\"Estuario";
mostCurrent._lstachievements.AddTwoLinesAndBitmap2(BA.ObjectToCharSequence("Estuario"),BA.ObjectToCharSequence("Completaste tu primera evaluación de un estuario"),(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._main._lang /*String*/ +"-estuario.png").getObject()),(Object)("estuario"));
 };
 //BA.debugLineNum = 294;BA.debugLine="If Main.numlaguna >= 1 Then";
if ((double)(Double.parseDouble(mostCurrent._main._numlaguna /*String*/ ))>=1) { 
 //BA.debugLineNum = 295;BA.debugLine="lstAchievements.AddTwoLinesAndBitmap2(\"Laguna\",";
mostCurrent._lstachievements.AddTwoLinesAndBitmap2(BA.ObjectToCharSequence("Laguna"),BA.ObjectToCharSequence("Completaste tu primera evaluación de una laguna"),(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._main._lang /*String*/ +"-laguna.png").getObject()),(Object)("laguna"));
 };
 //BA.debugLineNum = 297;BA.debugLine="If Main.numlaguna >= 1 And Main.numestuario >= 1";
if ((double)(Double.parseDouble(mostCurrent._main._numlaguna /*String*/ ))>=1 && (double)(Double.parseDouble(mostCurrent._main._numestuario /*String*/ ))>=1 && (double)(Double.parseDouble(mostCurrent._main._numriomontana /*String*/ ))>=1 && (double)(Double.parseDouble(mostCurrent._main._numriollanura /*String*/ ))>=1) { 
 //BA.debugLineNum = 298;BA.debugLine="lstAchievements.AddTwoLinesAndBitmap2(\"Maestro";
mostCurrent._lstachievements.AddTwoLinesAndBitmap2(BA.ObjectToCharSequence("Maestro de todos los ambientes"),BA.ObjectToCharSequence("Completaste una evaluación de cada ambiente"),(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._main._lang /*String*/ +"-maestrodeambientes.png").getObject()),(Object)("maestrodeambientes"));
 };
 //BA.debugLineNum = 301;BA.debugLine="If Main.puntostotales >= 5000 Then";
if ((double)(Double.parseDouble(mostCurrent._main._puntostotales /*String*/ ))>=5000) { 
 //BA.debugLineNum = 302;BA.debugLine="lstAchievements.AddTwoLinesAndBitmap2(\"Goleador";
mostCurrent._lstachievements.AddTwoLinesAndBitmap2(BA.ObjectToCharSequence("Goleador"),BA.ObjectToCharSequence("Alcanzaste los 5000 puntos"),(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._main._lang /*String*/ +"-goleador.png").getObject()),(Object)("goleador"));
 };
 //BA.debugLineNum = 304;BA.debugLine="If Main.puntostotales >= 10000 Then";
if ((double)(Double.parseDouble(mostCurrent._main._puntostotales /*String*/ ))>=10000) { 
 //BA.debugLineNum = 305;BA.debugLine="lstAchievements.AddTwoLinesAndBitmap2(\"Pichichi";
mostCurrent._lstachievements.AddTwoLinesAndBitmap2(BA.ObjectToCharSequence("Pichichi"),BA.ObjectToCharSequence("Alcanzaste los 10000 puntos"),(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._main._lang /*String*/ +"-pichichi.png").getObject()),(Object)("pichichi"));
 };
 //BA.debugLineNum = 307;BA.debugLine="If Main.numevalsok >= 1 Then";
if ((double)(Double.parseDouble(mostCurrent._main._numevalsok /*String*/ ))>=1) { 
 //BA.debugLineNum = 308;BA.debugLine="lstAchievements.AddTwoLinesAndBitmap2(\"Primera";
mostCurrent._lstachievements.AddTwoLinesAndBitmap2(BA.ObjectToCharSequence("Primera validación"),BA.ObjectToCharSequence("Validaron tu primera evaluación!"),(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._main._lang /*String*/ +"-primeraeval.png").getObject()),(Object)("primeraeval"));
 };
 //BA.debugLineNum = 310;BA.debugLine="If Main.numfotosok >= 1 Then";
if ((double)(Double.parseDouble(mostCurrent._main._numfotosok /*String*/ ))>=1) { 
 //BA.debugLineNum = 311;BA.debugLine="lstAchievements.AddTwoLinesAndBitmap2(\"Primera";
mostCurrent._lstachievements.AddTwoLinesAndBitmap2(BA.ObjectToCharSequence("Primera foto validada"),BA.ObjectToCharSequence("Validaron tu primera foto!"),(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._main._lang /*String*/ +"-primerafoto.png").getObject()),(Object)("primerafoto"));
 };
 //BA.debugLineNum = 313;BA.debugLine="If Main.numshares >= 1 Then";
if ((double)(Double.parseDouble(mostCurrent._main._numshares /*String*/ ))>=1) { 
 //BA.debugLineNum = 314;BA.debugLine="lstAchievements.AddTwoLinesAndBitmap2(\"Primer s";
mostCurrent._lstachievements.AddTwoLinesAndBitmap2(BA.ObjectToCharSequence("Primer share"),BA.ObjectToCharSequence("Compartiste tu primera evaluación en Facebook!"),(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._main._lang /*String*/ +"-primershare.png").getObject()),(Object)("primershare"));
 };
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 };
 //BA.debugLineNum = 318;BA.debugLine="End Sub";
return "";
}
public static String  _cargarnivel() throws Exception{
anywheresoftware.b4a.objects.drawable.ColorDrawable _gd = null;
anywheresoftware.b4a.objects.drawable.ColorDrawable _cd = null;
double _nivelfull = 0;
int _nivela = 0;
double _resto = 0;
 //BA.debugLineNum = 204;BA.debugLine="Sub cargarNivel";
 //BA.debugLineNum = 207;BA.debugLine="Dim gd As ColorDrawable";
_gd = new anywheresoftware.b4a.objects.drawable.ColorDrawable();
 //BA.debugLineNum = 208;BA.debugLine="Dim cd As ColorDrawable";
_cd = new anywheresoftware.b4a.objects.drawable.ColorDrawable();
 //BA.debugLineNum = 209;BA.debugLine="gd.Initialize(Colors.DarkGray,10dip)";
_gd.Initialize(anywheresoftware.b4a.keywords.Common.Colors.DarkGray,anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10)));
 //BA.debugLineNum = 210;BA.debugLine="cd.Initialize(Colors.LightGray, 10dip)";
_cd.Initialize(anywheresoftware.b4a.keywords.Common.Colors.LightGray,anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10)));
 //BA.debugLineNum = 211;BA.debugLine="utilidades.SetProgressDrawable(pgbLevel, gd, cd)";
mostCurrent._utilidades._setprogressdrawable /*String*/ (mostCurrent.activityBA,mostCurrent._pgblevel,(Object)(_gd.getObject()),(Object)(_cd.getObject()));
 //BA.debugLineNum = 214;BA.debugLine="Dim nivelfull As Double";
_nivelfull = 0;
 //BA.debugLineNum = 215;BA.debugLine="Dim nivela As Int";
_nivela = 0;
 //BA.debugLineNum = 216;BA.debugLine="Dim resto As Double";
_resto = 0;
 //BA.debugLineNum = 217;BA.debugLine="nivelfull = (Sqrt(Main.puntostotales) * 0.25)";
_nivelfull = (anywheresoftware.b4a.keywords.Common.Sqrt((double)(Double.parseDouble(mostCurrent._main._puntostotales /*String*/ )))*0.25);
 //BA.debugLineNum = 218;BA.debugLine="nivela = Floor(nivelfull)";
_nivela = (int) (anywheresoftware.b4a.keywords.Common.Floor(_nivelfull));
 //BA.debugLineNum = 219;BA.debugLine="resto = Round2(Abs(nivelfull - nivela) * 100,0)";
_resto = anywheresoftware.b4a.keywords.Common.Round2(anywheresoftware.b4a.keywords.Common.Abs(_nivelfull-_nivela)*100,(int) (0));
 //BA.debugLineNum = 220;BA.debugLine="pgbLevel.Progress = resto";
mostCurrent._pgblevel.setProgress((int) (_resto));
 //BA.debugLineNum = 221;BA.debugLine="lblNivel.Text = nivela";
mostCurrent._lblnivel.setText(BA.ObjectToCharSequence(_nivela));
 //BA.debugLineNum = 224;BA.debugLine="cargarMedallas";
_cargarmedallas();
 //BA.debugLineNum = 226;BA.debugLine="End Sub";
return "";
}
public static String  _cargarusuario() throws Exception{
appear.pnud.preservamos.downloadservice._downloaddata _dd = null;
 //BA.debugLineNum = 131;BA.debugLine="Sub CargarUsuario";
 //BA.debugLineNum = 132;BA.debugLine="Dim dd As DownloadData";
_dd = new appear.pnud.preservamos.downloadservice._downloaddata();
 //BA.debugLineNum = 133;BA.debugLine="dd.url = Main.serverPath & \"/\" & Main.serverConne";
_dd.url /*String*/  = mostCurrent._main._serverpath /*String*/ +"/"+mostCurrent._main._serverconnectionfolder /*String*/ +"/getpuntosNew.php?user_id="+mostCurrent._main._strusername /*String*/ ;
 //BA.debugLineNum = 134;BA.debugLine="dd.EventName = \"CargarUsuario\"";
_dd.EventName /*String*/  = "CargarUsuario";
 //BA.debugLineNum = 135;BA.debugLine="dd.Target = Me";
_dd.Target /*Object*/  = frmperfil.getObject();
 //BA.debugLineNum = 136;BA.debugLine="CallSubDelayed2(DownloadService, \"StartDownload\",";
anywheresoftware.b4a.keywords.Common.CallSubDelayed2(processBA,(Object)(mostCurrent._downloadservice.getObject()),"StartDownload",(Object)(_dd));
 //BA.debugLineNum = 137;BA.debugLine="End Sub";
return "";
}
public static String  _cargarusuario_complete(appear.pnud.preservamos.httpjob _job) throws Exception{
String _ret = "";
String _act = "";
anywheresoftware.b4a.objects.collections.JSONParser _parser = null;
anywheresoftware.b4a.objects.collections.Map _newpunto = null;
 //BA.debugLineNum = 138;BA.debugLine="Sub CargarUsuario_Complete(Job As HttpJob)";
 //BA.debugLineNum = 139;BA.debugLine="Log(\"Conexion LOGIN: \" & Job.Success)";
anywheresoftware.b4a.keywords.Common.LogImpl("550659329","Conexion LOGIN: "+BA.ObjectToString(_job._success /*boolean*/ ),0);
 //BA.debugLineNum = 140;BA.debugLine="If Job.Success = True Then";
if (_job._success /*boolean*/ ==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 141;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 //BA.debugLineNum = 143;BA.debugLine="Dim ret As String";
_ret = "";
 //BA.debugLineNum = 144;BA.debugLine="Dim act As String";
_act = "";
 //BA.debugLineNum = 145;BA.debugLine="ret = Job.GetString";
_ret = _job._getstring /*String*/ ();
 //BA.debugLineNum = 146;BA.debugLine="Dim parser As JSONParser";
_parser = new anywheresoftware.b4a.objects.collections.JSONParser();
 //BA.debugLineNum = 147;BA.debugLine="parser.Initialize(ret)";
_parser.Initialize(_ret);
 //BA.debugLineNum = 148;BA.debugLine="act = parser.NextValue";
_act = BA.ObjectToString(_parser.NextValue());
 //BA.debugLineNum = 150;BA.debugLine="If act = \"Not Found\" Then";
if ((_act).equals("Not Found")) { 
 //BA.debugLineNum = 151;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 152;BA.debugLine="ToastMessageShow(\"Error recuperando los puntos";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Error recuperando los puntos"),anywheresoftware.b4a.keywords.Common.False);
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 154;BA.debugLine="ToastMessageShow(\"Error recovering points\", Fa";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Error recovering points"),anywheresoftware.b4a.keywords.Common.False);
 };
 }else if((_act).equals("GetPuntos OK")) { 
 //BA.debugLineNum = 160;BA.debugLine="Dim newpunto As Map";
_newpunto = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 161;BA.debugLine="newpunto = parser.NextObject";
_newpunto = _parser.NextObject();
 //BA.debugLineNum = 162;BA.debugLine="Main.puntostotales = newpunto.Get(\"puntostotale";
mostCurrent._main._puntostotales /*String*/  = BA.ObjectToString(_newpunto.Get((Object)("puntostotales")));
 //BA.debugLineNum = 163;BA.debugLine="Main.puntosnumevals = newpunto.Get(\"puntosevals";
mostCurrent._main._puntosnumevals /*String*/  = BA.ObjectToString(_newpunto.Get((Object)("puntosevals")));
 //BA.debugLineNum = 164;BA.debugLine="Main.numevalsok = newpunto.Get(\"numevalsok\")";
mostCurrent._main._numevalsok /*String*/  = BA.ObjectToString(_newpunto.Get((Object)("numevalsok")));
 //BA.debugLineNum = 165;BA.debugLine="Main.numriollanura = newpunto.Get(\"numriollanur";
mostCurrent._main._numriollanura /*String*/  = BA.ObjectToString(_newpunto.Get((Object)("numriollanura")));
 //BA.debugLineNum = 166;BA.debugLine="Main.numriomontana = newpunto.Get(\"numriomontan";
mostCurrent._main._numriomontana /*String*/  = BA.ObjectToString(_newpunto.Get((Object)("numriomontana")));
 //BA.debugLineNum = 167;BA.debugLine="Main.numlaguna = newpunto.Get(\"numlaguna\")";
mostCurrent._main._numlaguna /*String*/  = BA.ObjectToString(_newpunto.Get((Object)("numlaguna")));
 //BA.debugLineNum = 168;BA.debugLine="Main.numestuario = newpunto.Get(\"numestuario\")";
mostCurrent._main._numestuario /*String*/  = BA.ObjectToString(_newpunto.Get((Object)("numestuario")));
 //BA.debugLineNum = 169;BA.debugLine="Main.numshares = newpunto.Get(\"numshares\")";
mostCurrent._main._numshares /*String*/  = BA.ObjectToString(_newpunto.Get((Object)("numshares")));
 //BA.debugLineNum = 171;BA.debugLine="txtPuntosTotales.Text = Main.puntostotales";
mostCurrent._txtpuntostotales.setText(BA.ObjectToCharSequence(mostCurrent._main._puntostotales /*String*/ ));
 //BA.debugLineNum = 172;BA.debugLine="txtEvalsOk.Text = Main.numevalsok";
mostCurrent._txtevalsok.setText(BA.ObjectToCharSequence(mostCurrent._main._numevalsok /*String*/ ));
 //BA.debugLineNum = 176;BA.debugLine="cargarNivel";
_cargarnivel();
 };
 }else {
 //BA.debugLineNum = 180;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 181;BA.debugLine="MsgboxAsync(\"Compruebe su conexión a Internet!\"";
anywheresoftware.b4a.keywords.Common.MsgboxAsync(BA.ObjectToCharSequence("Compruebe su conexión a Internet!"),BA.ObjectToCharSequence("Oops!"),processBA);
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 183;BA.debugLine="MsgboxAsync(\"Check your internet connection!\",";
anywheresoftware.b4a.keywords.Common.MsgboxAsync(BA.ObjectToCharSequence("Check your internet connection!"),BA.ObjectToCharSequence("Oops!"),processBA);
 };
 //BA.debugLineNum = 186;BA.debugLine="txtPuntosTotales.Text = Main.puntostotales";
mostCurrent._txtpuntostotales.setText(BA.ObjectToCharSequence(mostCurrent._main._puntostotales /*String*/ ));
 //BA.debugLineNum = 187;BA.debugLine="txtEvalsOk.Text = Main.evalsOK";
mostCurrent._txtevalsok.setText(BA.ObjectToCharSequence(mostCurrent._main._evalsok /*String*/ ));
 //BA.debugLineNum = 188;BA.debugLine="cargarNivel";
_cargarnivel();
 };
 //BA.debugLineNum = 192;BA.debugLine="Job.Release";
_job._release /*String*/ ();
 //BA.debugLineNum = 193;BA.debugLine="End Sub";
return "";
}
public static String  _checkinternet() throws Exception{
appear.pnud.preservamos.downloadservice._downloaddata _dd = null;
 //BA.debugLineNum = 106;BA.debugLine="Sub CheckInternet";
 //BA.debugLineNum = 107;BA.debugLine="Dim dd As DownloadData";
_dd = new appear.pnud.preservamos.downloadservice._downloaddata();
 //BA.debugLineNum = 108;BA.debugLine="dd.url = Main.serverPath &  \"/\" & Main.serverConn";
_dd.url /*String*/  = mostCurrent._main._serverpath /*String*/ +"/"+mostCurrent._main._serverconnectionfolder /*String*/ +"/connecttest.php";
 //BA.debugLineNum = 109;BA.debugLine="dd.EventName = \"CheckInternet\"";
_dd.EventName /*String*/  = "CheckInternet";
 //BA.debugLineNum = 110;BA.debugLine="dd.Target = Me";
_dd.Target /*Object*/  = frmperfil.getObject();
 //BA.debugLineNum = 111;BA.debugLine="CallSubDelayed2(DownloadService, \"StartDownload\",";
anywheresoftware.b4a.keywords.Common.CallSubDelayed2(processBA,(Object)(mostCurrent._downloadservice.getObject()),"StartDownload",(Object)(_dd));
 //BA.debugLineNum = 112;BA.debugLine="End Sub";
return "";
}
public static String  _checkinternet_complete(appear.pnud.preservamos.httpjob _job) throws Exception{
 //BA.debugLineNum = 113;BA.debugLine="Sub CheckInternet_Complete(Job As HttpJob)";
 //BA.debugLineNum = 114;BA.debugLine="Log(\"Job completed: \" & Job.Success)";
anywheresoftware.b4a.keywords.Common.LogImpl("550528257","Job completed: "+BA.ObjectToString(_job._success /*boolean*/ ),0);
 //BA.debugLineNum = 115;BA.debugLine="If Job.Success = True Then";
if (_job._success /*boolean*/ ==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 116;BA.debugLine="Job.Release";
_job._release /*String*/ ();
 //BA.debugLineNum = 117;BA.debugLine="CargarUsuario";
_cargarusuario();
 }else {
 //BA.debugLineNum = 119;BA.debugLine="Job.Release";
_job._release /*String*/ ();
 };
 //BA.debugLineNum = 121;BA.debugLine="End Sub";
return "";
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 10;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 13;BA.debugLine="Private pnlBadges As Panel";
mostCurrent._pnlbadges = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 14;BA.debugLine="Private pgbLevel As ProgressBar";
mostCurrent._pgblevel = new anywheresoftware.b4a.objects.ProgressBarWrapper();
 //BA.debugLineNum = 15;BA.debugLine="Private lblNivel As Label";
mostCurrent._lblnivel = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 16;BA.debugLine="Private lstBadges As ListView";
mostCurrent._lstbadges = new anywheresoftware.b4a.objects.ListViewWrapper();
 //BA.debugLineNum = 17;BA.debugLine="Private lstAchievements As ListView";
mostCurrent._lstachievements = new anywheresoftware.b4a.objects.ListViewWrapper();
 //BA.debugLineNum = 18;BA.debugLine="Private imgBadge As ImageView";
mostCurrent._imgbadge = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 19;BA.debugLine="Dim imagetoshare As String";
mostCurrent._imagetoshare = "";
 //BA.debugLineNum = 20;BA.debugLine="Private lblTusMedallas As Label";
mostCurrent._lbltusmedallas = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 21;BA.debugLine="Private lblTusLogros As Label";
mostCurrent._lbltuslogros = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 22;BA.debugLine="Private lblPuntosTotales As Label";
mostCurrent._lblpuntostotales = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 23;BA.debugLine="Private txtPuntosTotales As Label";
mostCurrent._txtpuntostotales = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 24;BA.debugLine="Private lblEvalsOk As Label";
mostCurrent._lblevalsok = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 25;BA.debugLine="Private txtEvalsOk As Label";
mostCurrent._txtevalsok = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 28;BA.debugLine="Private btnCerrar As Button";
mostCurrent._btncerrar = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 31;BA.debugLine="Private tabStripPerfil As TabStrip";
mostCurrent._tabstripperfil = new anywheresoftware.b4a.objects.TabStripViewPager();
 //BA.debugLineNum = 32;BA.debugLine="Private spnUserLevel As Spinner";
mostCurrent._spnuserlevel = new anywheresoftware.b4a.objects.SpinnerWrapper();
 //BA.debugLineNum = 35;BA.debugLine="Private lblTitulo As Label";
mostCurrent._lbltitulo = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 36;BA.debugLine="Private imgBadge As ImageView";
mostCurrent._imgbadge = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 37;BA.debugLine="Private btnShare As Button";
mostCurrent._btnshare = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 38;BA.debugLine="Private btnShareOthers As Button";
mostCurrent._btnshareothers = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 39;BA.debugLine="Private lblNoCompartir As Label";
mostCurrent._lblnocompartir = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 40;BA.debugLine="Dim nivel As String";
mostCurrent._nivel = "";
 //BA.debugLineNum = 41;BA.debugLine="Dim imagetoshare As String";
mostCurrent._imagetoshare = "";
 //BA.debugLineNum = 42;BA.debugLine="Private fondogris As Panel";
mostCurrent._fondogris = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 43;BA.debugLine="Private pnlShare As Panel";
mostCurrent._pnlshare = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 45;BA.debugLine="End Sub";
return "";
}
public static String  _lstachievements_itemclick(int _position,Object _value) throws Exception{
 //BA.debugLineNum = 324;BA.debugLine="Sub lstAchievements_ItemClick (Position As Int, Va";
 //BA.debugLineNum = 325;BA.debugLine="Activity.LoadLayout(\"layShare\")";
mostCurrent._activity.LoadLayout("layShare",mostCurrent.activityBA);
 //BA.debugLineNum = 326;BA.debugLine="imgBadge.Bitmap = LoadBitmap(File.DirAssets, Main";
mostCurrent._imgbadge.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._main._lang /*String*/ +"-"+BA.ObjectToString(_value)+".png").getObject()));
 //BA.debugLineNum = 327;BA.debugLine="imagetoshare =  Main.lang & \"-\" & Value & \".png\"";
mostCurrent._imagetoshare = mostCurrent._main._lang /*String*/ +"-"+BA.ObjectToString(_value)+".png";
 //BA.debugLineNum = 328;BA.debugLine="End Sub";
return "";
}
public static String  _lstbadges_itemclick(int _position,Object _value) throws Exception{
 //BA.debugLineNum = 319;BA.debugLine="Sub lstBadges_ItemClick (Position As Int, Value As";
 //BA.debugLineNum = 320;BA.debugLine="Activity.LoadLayout(\"layShare\")";
mostCurrent._activity.LoadLayout("layShare",mostCurrent.activityBA);
 //BA.debugLineNum = 321;BA.debugLine="imgBadge.Bitmap = LoadBitmap(File.DirAssets, Main";
mostCurrent._imgbadge.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._main._lang /*String*/ +"-"+BA.ObjectToString(_value)+".png").getObject()));
 //BA.debugLineNum = 322;BA.debugLine="imagetoshare =  Main.lang & \"-\" & Value & \".png\"";
mostCurrent._imagetoshare = mostCurrent._main._lang /*String*/ +"-"+BA.ObjectToString(_value)+".png";
 //BA.debugLineNum = 323;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 6;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 8;BA.debugLine="End Sub";
return "";
}
public static String  _traducirgui() throws Exception{
 //BA.debugLineNum = 84;BA.debugLine="Sub TraducirGUI";
 //BA.debugLineNum = 85;BA.debugLine="If Main.lang = \"en\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 86;BA.debugLine="lblPuntosTotales.Text = \"Total points\"";
mostCurrent._lblpuntostotales.setText(BA.ObjectToCharSequence("Total points"));
 //BA.debugLineNum = 87;BA.debugLine="lblEvalsOk.Text = \"Valid reports\"";
mostCurrent._lblevalsok.setText(BA.ObjectToCharSequence("Valid reports"));
 //BA.debugLineNum = 88;BA.debugLine="lblTusLogros.Text = \"Your achievements\"";
mostCurrent._lbltuslogros.setText(BA.ObjectToCharSequence("Your achievements"));
 //BA.debugLineNum = 89;BA.debugLine="lblTusMedallas.Text = \"Your medals\"";
mostCurrent._lbltusmedallas.setText(BA.ObjectToCharSequence("Your medals"));
 };
 //BA.debugLineNum = 91;BA.debugLine="End Sub";
return "";
}
}
