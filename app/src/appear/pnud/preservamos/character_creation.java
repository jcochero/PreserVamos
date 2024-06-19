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

public class character_creation extends Activity implements B4AActivity{
	public static character_creation mostCurrent;
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
			processBA = new BA(this.getApplicationContext(), null, null, "appear.pnud.preservamos", "appear.pnud.preservamos.character_creation");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
            
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (character_creation).");
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
		activityBA = new BA(this, layout, processBA, "appear.pnud.preservamos", "appear.pnud.preservamos.character_creation");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "appear.pnud.preservamos.character_creation", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density, mostCurrent);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (character_creation) Create " + (isFirst ? "(first time)" : "") + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (character_creation) Resume **");
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
		return character_creation.class;
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
            BA.LogInfo("** Activity (character_creation) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
        else
            BA.LogInfo("** Activity (character_creation) Pause event (activity is not paused). **");
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
            character_creation mc = mostCurrent;
			if (mc == null || mc != activity.get())
				return;
			processBA.setActivityPaused(false);
            BA.LogInfo("** Activity (character_creation) Resume **");
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
public static String _playername = "";
public static String _playerusername = "";
public static String _playerpassword = "";
public static String _playeremail = "";
public static String _playeruserid = "";
public anywheresoftware.b4a.objects.TabStripViewPager _tabcharacter = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnnextavatar = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnprevavatar = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imgavatarsidenav = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnokname = null;
public anywheresoftware.b4a.objects.EditTextWrapper _txtplayername = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblpregunta = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnregister = null;
public static int _avatarnumber = 0;
public anywheresoftware.b4a.objects.ButtonWrapper _btnokavatar = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnokemail = null;
public anywheresoftware.b4a.objects.EditTextWrapper _txtemail = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnokpassword = null;
public anywheresoftware.b4a.objects.EditTextWrapper _txtpassword1 = null;
public anywheresoftware.b4a.objects.EditTextWrapper _txtpassword2 = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblhola = null;
public anywheresoftware.b4a.phone.Phone _p = null;
public static int _currentstage = 0;
public b4a.example.dateutils _dateutils = null;
public appear.pnud.preservamos.main _main = null;
public appear.pnud.preservamos.form_main _form_main = null;
public appear.pnud.preservamos.form_reporte _form_reporte = null;
public appear.pnud.preservamos.frmlocalizacion _frmlocalizacion = null;
public appear.pnud.preservamos.reporte_habitat_rio _reporte_habitat_rio = null;
public appear.pnud.preservamos.utilidades _utilidades = null;
public appear.pnud.preservamos.frmdatossinenviar _frmdatossinenviar = null;
public appear.pnud.preservamos.reporte_envio _reporte_envio = null;
public appear.pnud.preservamos.alerta_fotos _alerta_fotos = null;
public appear.pnud.preservamos.alertas _alertas = null;
public appear.pnud.preservamos.aprender_muestreo _aprender_muestreo = null;
public appear.pnud.preservamos.dbutils _dbutils = null;
public appear.pnud.preservamos.downloadservice _downloadservice = null;
public appear.pnud.preservamos.firebasemessaging _firebasemessaging = null;
public appear.pnud.preservamos.frmabout _frmabout = null;
public appear.pnud.preservamos.frmdatosanteriores _frmdatosanteriores = null;
public appear.pnud.preservamos.frmeditprofile _frmeditprofile = null;
public appear.pnud.preservamos.frmfelicitaciones _frmfelicitaciones = null;
public appear.pnud.preservamos.frmmapa _frmmapa = null;
public appear.pnud.preservamos.frmmunicipioestadisticas _frmmunicipioestadisticas = null;
public appear.pnud.preservamos.frmpoliticadatos _frmpoliticadatos = null;
public appear.pnud.preservamos.frmtiporeporte _frmtiporeporte = null;
public appear.pnud.preservamos.imagedownloader _imagedownloader = null;
public appear.pnud.preservamos.inatcheck _inatcheck = null;
public appear.pnud.preservamos.mod_hidro _mod_hidro = null;
public appear.pnud.preservamos.mod_hidro_fotos _mod_hidro_fotos = null;
public appear.pnud.preservamos.mod_residuos _mod_residuos = null;
public appear.pnud.preservamos.mod_residuos_fotos _mod_residuos_fotos = null;
public appear.pnud.preservamos.reporte_fotos _reporte_fotos = null;
public appear.pnud.preservamos.reporte_habitat_laguna _reporte_habitat_laguna = null;
public appear.pnud.preservamos.reporte_habitat_rio_sierras _reporte_habitat_rio_sierras = null;
public appear.pnud.preservamos.reporte_habitat_rio_sierras_bu _reporte_habitat_rio_sierras_bu = null;
public appear.pnud.preservamos.starter _starter = null;
public appear.pnud.preservamos.uploadfiles _uploadfiles = null;
public appear.pnud.preservamos.register _register = null;
public appear.pnud.preservamos.xuiviewsutils _xuiviewsutils = null;
public appear.pnud.preservamos.httputils2service _httputils2service = null;

public static void initializeProcessGlobals() {
             try {
                Class.forName(BA.applicationContext.getPackageName() + ".main").getMethod("initializeProcessGlobals").invoke(null, null);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
}
public static String  _activity_create(boolean _firsttime) throws Exception{
 //BA.debugLineNum = 40;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
 //BA.debugLineNum = 41;BA.debugLine="Activity.LoadLayout(\"character_creation_container";
mostCurrent._activity.LoadLayout("character_creation_container",mostCurrent.activityBA);
 //BA.debugLineNum = 42;BA.debugLine="tabCharacter.LoadLayout(\"character_creation_inner";
mostCurrent._tabcharacter.LoadLayout("character_creation_inner",BA.ObjectToCharSequence("1"));
 //BA.debugLineNum = 43;BA.debugLine="lblPregunta.Text = \"¿Cómo te llamás?\"";
mostCurrent._lblpregunta.setText(BA.ObjectToCharSequence("¿Cómo te llamás?"));
 //BA.debugLineNum = 44;BA.debugLine="currentStage = 0";
_currentstage = (int) (0);
 //BA.debugLineNum = 45;BA.debugLine="End Sub";
return "";
}
public static boolean  _activity_keypress(int _keycode) throws Exception{
 //BA.debugLineNum = 54;BA.debugLine="Sub Activity_KeyPress (KeyCode As Int) As Boolean";
 //BA.debugLineNum = 55;BA.debugLine="If KeyCode = KeyCodes.KEYCODE_BACK Then";
if (_keycode==anywheresoftware.b4a.keywords.Common.KeyCodes.KEYCODE_BACK) { 
 //BA.debugLineNum = 56;BA.debugLine="If currentStage = 0 Then";
if (_currentstage==0) { 
 //BA.debugLineNum = 58;BA.debugLine="Return False";
if (true) return anywheresoftware.b4a.keywords.Common.False;
 }else if(_currentstage==1) { 
 //BA.debugLineNum = 60;BA.debugLine="lblHola.Text = \"¡Hola!\"";
mostCurrent._lblhola.setText(BA.ObjectToCharSequence("¡Hola!"));
 //BA.debugLineNum = 61;BA.debugLine="lblPregunta.Text = \"¿Cómo te llamarás?\"";
mostCurrent._lblpregunta.setText(BA.ObjectToCharSequence("¿Cómo te llamarás?"));
 //BA.debugLineNum = 62;BA.debugLine="imgAvatarSideNav.SetVisibleAnimated(300, False)";
mostCurrent._imgavatarsidenav.SetVisibleAnimated((int) (300),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 63;BA.debugLine="btnPrevAvatar.SetVisibleAnimated(300, False)";
mostCurrent._btnprevavatar.SetVisibleAnimated((int) (300),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 64;BA.debugLine="btnNextAvatar.SetVisibleAnimated(300, False)";
mostCurrent._btnnextavatar.SetVisibleAnimated((int) (300),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 65;BA.debugLine="btnOkAvatar.SetVisibleAnimated(300, False)";
mostCurrent._btnokavatar.SetVisibleAnimated((int) (300),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 67;BA.debugLine="txtPlayerName.Text = PlayerName";
mostCurrent._txtplayername.setText(BA.ObjectToCharSequence(_playername));
 //BA.debugLineNum = 68;BA.debugLine="txtPlayerName.SetVisibleAnimated(300, True)";
mostCurrent._txtplayername.SetVisibleAnimated((int) (300),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 69;BA.debugLine="btnOkName.SetVisibleAnimated(300, True)";
mostCurrent._btnokname.SetVisibleAnimated((int) (300),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 70;BA.debugLine="currentStage = 0";
_currentstage = (int) (0);
 }else if(_currentstage==2) { 
 //BA.debugLineNum = 72;BA.debugLine="txtEmail.SetVisibleAnimated(300, False)";
mostCurrent._txtemail.SetVisibleAnimated((int) (300),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 73;BA.debugLine="btnOkEmail.SetVisibleAnimated(300, False)";
mostCurrent._btnokemail.SetVisibleAnimated((int) (300),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 74;BA.debugLine="lblPregunta.Text = \"¿Cómo quieres verte en el m";
mostCurrent._lblpregunta.setText(BA.ObjectToCharSequence("¿Cómo quieres verte en el mapa?"));
 //BA.debugLineNum = 76;BA.debugLine="imgAvatarSideNav.SetVisibleAnimated(300, True)";
mostCurrent._imgavatarsidenav.SetVisibleAnimated((int) (300),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 77;BA.debugLine="btnPrevAvatar.SetVisibleAnimated(300, True)";
mostCurrent._btnprevavatar.SetVisibleAnimated((int) (300),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 78;BA.debugLine="btnNextAvatar.SetVisibleAnimated(300, True)";
mostCurrent._btnnextavatar.SetVisibleAnimated((int) (300),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 79;BA.debugLine="btnOkAvatar.SetVisibleAnimated(300, True)";
mostCurrent._btnokavatar.SetVisibleAnimated((int) (300),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 80;BA.debugLine="currentStage = 1";
_currentstage = (int) (1);
 }else if(_currentstage==3) { 
 //BA.debugLineNum = 83;BA.debugLine="txtPassword1.SetVisibleAnimated(300, False)";
mostCurrent._txtpassword1.SetVisibleAnimated((int) (300),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 84;BA.debugLine="txtPassword2.SetVisibleAnimated(300, False)";
mostCurrent._txtpassword2.SetVisibleAnimated((int) (300),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 85;BA.debugLine="btnOkPassword.SetVisibleAnimated(300, False)";
mostCurrent._btnokpassword.SetVisibleAnimated((int) (300),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 86;BA.debugLine="btnOkEmail.Visible = True";
mostCurrent._btnokemail.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 87;BA.debugLine="txtEmail.Enabled = True";
mostCurrent._txtemail.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 88;BA.debugLine="txtEmail.Text = PlayerUsername";
mostCurrent._txtemail.setText(BA.ObjectToCharSequence(_playerusername));
 //BA.debugLineNum = 89;BA.debugLine="currentStage = 2";
_currentstage = (int) (2);
 };
 }else {
 //BA.debugLineNum = 94;BA.debugLine="Return False";
if (true) return anywheresoftware.b4a.keywords.Common.False;
 };
 //BA.debugLineNum = 96;BA.debugLine="Return True";
if (true) return anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 97;BA.debugLine="End Sub";
return false;
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 51;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 53;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 47;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 49;BA.debugLine="End Sub";
return "";
}
public static String  _btnnextavatar_click() throws Exception{
 //BA.debugLineNum = 140;BA.debugLine="Private Sub btnNextAvatar_Click";
 //BA.debugLineNum = 142;BA.debugLine="If avatarNumber = 5 Then";
if (_avatarnumber==5) { 
 }else {
 //BA.debugLineNum = 145;BA.debugLine="avatarNumber = avatarNumber + 1";
_avatarnumber = (int) (_avatarnumber+1);
 //BA.debugLineNum = 146;BA.debugLine="cargarAvatar(avatarNumber)";
_cargaravatar(BA.NumberToString(_avatarnumber));
 };
 //BA.debugLineNum = 148;BA.debugLine="End Sub";
return "";
}
public static String  _btnokavatar_click() throws Exception{
String _playeravatar = "";
 //BA.debugLineNum = 150;BA.debugLine="Private Sub btnOkAvatar_Click";
 //BA.debugLineNum = 151;BA.debugLine="PlayerAvatar = \"avatar_\" & avatarNumber & \".png\"";
_playeravatar = "avatar_"+BA.NumberToString(_avatarnumber)+".png";
 //BA.debugLineNum = 152;BA.debugLine="imgAvatarSideNav.SetVisibleAnimated(300, False)";
mostCurrent._imgavatarsidenav.SetVisibleAnimated((int) (300),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 153;BA.debugLine="btnPrevAvatar.SetVisibleAnimated(300, False)";
mostCurrent._btnprevavatar.SetVisibleAnimated((int) (300),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 154;BA.debugLine="btnNextAvatar.SetVisibleAnimated(300, False)";
mostCurrent._btnnextavatar.SetVisibleAnimated((int) (300),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 155;BA.debugLine="btnOkAvatar.SetVisibleAnimated(300, False)";
mostCurrent._btnokavatar.SetVisibleAnimated((int) (300),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 157;BA.debugLine="txtEmail.SetVisibleAnimated(300, True)";
mostCurrent._txtemail.SetVisibleAnimated((int) (300),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 158;BA.debugLine="btnOkEmail.SetVisibleAnimated(300, True)";
mostCurrent._btnokemail.SetVisibleAnimated((int) (300),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 159;BA.debugLine="lblPregunta.Text = \"Ingresa tu email y una contra";
mostCurrent._lblpregunta.setText(BA.ObjectToCharSequence("Ingresa tu email y una contraseña para registrarte"));
 //BA.debugLineNum = 160;BA.debugLine="currentStage = 2";
_currentstage = (int) (2);
 //BA.debugLineNum = 161;BA.debugLine="End Sub";
return "";
}
public static String  _btnokemail_click() throws Exception{
 //BA.debugLineNum = 163;BA.debugLine="Private Sub btnOkEmail_Click";
 //BA.debugLineNum = 165;BA.debugLine="If txtEmail.Text = \"\" Then";
if ((mostCurrent._txtemail.getText()).equals("")) { 
 //BA.debugLineNum = 166;BA.debugLine="ToastMessageShow(\"Ingresa un email válido\", Fals";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Ingresa un email válido"),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 167;BA.debugLine="txtEmail.RequestFocus";
mostCurrent._txtemail.RequestFocus();
 //BA.debugLineNum = 168;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 171;BA.debugLine="If Validate_Email(txtEmail.Text) = False Then";
if (_validate_email(mostCurrent._txtemail.getText())==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 172;BA.debugLine="ToastMessageShow(\"Ingresa un email válido\", Fals";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Ingresa un email válido"),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 173;BA.debugLine="txtEmail.RequestFocus";
mostCurrent._txtemail.RequestFocus();
 //BA.debugLineNum = 174;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 176;BA.debugLine="PlayerUsername = txtEmail.Text";
_playerusername = mostCurrent._txtemail.getText();
 //BA.debugLineNum = 177;BA.debugLine="PlayerEmail = txtEmail.Text";
_playeremail = mostCurrent._txtemail.getText();
 //BA.debugLineNum = 179;BA.debugLine="btnOkEmail.Visible = False";
mostCurrent._btnokemail.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 180;BA.debugLine="txtEmail.Enabled = False";
mostCurrent._txtemail.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 182;BA.debugLine="txtPassword1.SetVisibleAnimated(300, True)";
mostCurrent._txtpassword1.SetVisibleAnimated((int) (300),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 183;BA.debugLine="txtPassword2.SetVisibleAnimated(300, True)";
mostCurrent._txtpassword2.SetVisibleAnimated((int) (300),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 184;BA.debugLine="btnOkPassword.SetVisibleAnimated(300, True)";
mostCurrent._btnokpassword.SetVisibleAnimated((int) (300),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 186;BA.debugLine="currentStage = 3";
_currentstage = (int) (3);
 //BA.debugLineNum = 187;BA.debugLine="End Sub";
return "";
}
public static String  _btnokname_click() throws Exception{
 //BA.debugLineNum = 105;BA.debugLine="Private Sub btnOkName_Click";
 //BA.debugLineNum = 106;BA.debugLine="p.HideKeyboard(Activity)";
mostCurrent._p.HideKeyboard(mostCurrent._activity);
 //BA.debugLineNum = 107;BA.debugLine="PlayerName = txtPlayerName.Text";
_playername = mostCurrent._txtplayername.getText();
 //BA.debugLineNum = 108;BA.debugLine="txtPlayerName.SetVisibleAnimated(300, False)";
mostCurrent._txtplayername.SetVisibleAnimated((int) (300),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 109;BA.debugLine="btnOkName.SetVisibleAnimated(300, False)";
mostCurrent._btnokname.SetVisibleAnimated((int) (300),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 110;BA.debugLine="lblHola.Text = PlayerName";
mostCurrent._lblhola.setText(BA.ObjectToCharSequence(_playername));
 //BA.debugLineNum = 111;BA.debugLine="lblPregunta.Text = \"¿Cómo quieres verte en el map";
mostCurrent._lblpregunta.setText(BA.ObjectToCharSequence("¿Cómo quieres verte en el mapa?"));
 //BA.debugLineNum = 112;BA.debugLine="avatarNumber = 1";
_avatarnumber = (int) (1);
 //BA.debugLineNum = 113;BA.debugLine="cargarAvatar(avatarNumber)";
_cargaravatar(BA.NumberToString(_avatarnumber));
 //BA.debugLineNum = 114;BA.debugLine="imgAvatarSideNav.SetVisibleAnimated(300, True)";
mostCurrent._imgavatarsidenav.SetVisibleAnimated((int) (300),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 115;BA.debugLine="btnPrevAvatar.SetVisibleAnimated(300, True)";
mostCurrent._btnprevavatar.SetVisibleAnimated((int) (300),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 116;BA.debugLine="btnNextAvatar.SetVisibleAnimated(300, True)";
mostCurrent._btnnextavatar.SetVisibleAnimated((int) (300),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 117;BA.debugLine="btnOkAvatar.SetVisibleAnimated(300, True)";
mostCurrent._btnokavatar.SetVisibleAnimated((int) (300),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 118;BA.debugLine="currentStage = 1";
_currentstage = (int) (1);
 //BA.debugLineNum = 119;BA.debugLine="End Sub";
return "";
}
public static String  _btnokpassword_click() throws Exception{
 //BA.debugLineNum = 200;BA.debugLine="Private Sub btnOkPassword_Click";
 //BA.debugLineNum = 202;BA.debugLine="If txtPassword1.Text <> txtPassword2.Text Then";
if ((mostCurrent._txtpassword1.getText()).equals(mostCurrent._txtpassword2.getText()) == false) { 
 //BA.debugLineNum = 203;BA.debugLine="ToastMessageShow(\"Ambas contraseñas deben coinci";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Ambas contraseñas deben coincidir!"),anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 207;BA.debugLine="If txtEmail.Text = \"\" Then";
if ((mostCurrent._txtemail.getText()).equals("")) { 
 //BA.debugLineNum = 208;BA.debugLine="ToastMessageShow(\"Ingresa un email válido\", Fals";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Ingresa un email válido"),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 209;BA.debugLine="txtEmail.RequestFocus";
mostCurrent._txtemail.RequestFocus();
 //BA.debugLineNum = 210;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 213;BA.debugLine="If Validate_Email(txtEmail.Text) = False Then";
if (_validate_email(mostCurrent._txtemail.getText())==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 214;BA.debugLine="ToastMessageShow(\"Ingresa un email válido\", Fals";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Ingresa un email válido"),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 215;BA.debugLine="txtEmail.RequestFocus";
mostCurrent._txtemail.RequestFocus();
 //BA.debugLineNum = 216;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 219;BA.debugLine="PlayerPassword = txtPassword1.Text.Trim";
_playerpassword = mostCurrent._txtpassword1.getText().trim();
 //BA.debugLineNum = 225;BA.debugLine="End Sub";
return "";
}
public static String  _btnprevavatar_click() throws Exception{
 //BA.debugLineNum = 131;BA.debugLine="Private Sub btnPrevAvatar_Click";
 //BA.debugLineNum = 132;BA.debugLine="If avatarNumber = 1 Then";
if (_avatarnumber==1) { 
 }else {
 //BA.debugLineNum = 135;BA.debugLine="avatarNumber = avatarNumber - 1";
_avatarnumber = (int) (_avatarnumber-1);
 //BA.debugLineNum = 136;BA.debugLine="cargarAvatar(avatarNumber)";
_cargaravatar(BA.NumberToString(_avatarnumber));
 };
 //BA.debugLineNum = 138;BA.debugLine="End Sub";
return "";
}
public static void  _cargaravatar(String _filename) throws Exception{
ResumableSub_cargarAvatar rsub = new ResumableSub_cargarAvatar(null,_filename);
rsub.resume(processBA, null);
}
public static class ResumableSub_cargarAvatar extends BA.ResumableSub {
public ResumableSub_cargarAvatar(appear.pnud.preservamos.character_creation parent,String _filename) {
this.parent = parent;
this._filename = _filename;
}
appear.pnud.preservamos.character_creation parent;
String _filename;

@Override
public void resume(BA ba, Object[] result) throws Exception{

    while (true) {
        switch (state) {
            case -1:
return;

case 0:
//C
this.state = -1;
 //BA.debugLineNum = 123;BA.debugLine="imgAvatarSideNav.SetVisibleAnimated(150, False)";
parent.mostCurrent._imgavatarsidenav.SetVisibleAnimated((int) (150),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 124;BA.debugLine="Sleep(150)";
anywheresoftware.b4a.keywords.Common.Sleep(mostCurrent.activityBA,this,(int) (150));
this.state = 1;
return;
case 1:
//C
this.state = -1;
;
 //BA.debugLineNum = 125;BA.debugLine="imgAvatarSideNav.Bitmap = LoadBitmap(File.DirAsse";
parent.mostCurrent._imgavatarsidenav.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"avatar_"+_filename+".png").getObject()));
 //BA.debugLineNum = 126;BA.debugLine="Sleep(150)";
anywheresoftware.b4a.keywords.Common.Sleep(mostCurrent.activityBA,this,(int) (150));
this.state = 2;
return;
case 2:
//C
this.state = -1;
;
 //BA.debugLineNum = 127;BA.debugLine="imgAvatarSideNav.SetVisibleAnimated(150, True)";
parent.mostCurrent._imgavatarsidenav.SetVisibleAnimated((int) (150),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 129;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 14;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 17;BA.debugLine="Private tabCharacter As TabStrip";
mostCurrent._tabcharacter = new anywheresoftware.b4a.objects.TabStripViewPager();
 //BA.debugLineNum = 18;BA.debugLine="Private btnNextAvatar As Button";
mostCurrent._btnnextavatar = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 19;BA.debugLine="Private btnPrevAvatar As Button";
mostCurrent._btnprevavatar = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 20;BA.debugLine="Private imgAvatarSideNav As ImageView";
mostCurrent._imgavatarsidenav = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 21;BA.debugLine="Private btnOkName As Button";
mostCurrent._btnokname = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 22;BA.debugLine="Private txtPlayerName As EditText";
mostCurrent._txtplayername = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 23;BA.debugLine="Private lblPregunta As Label";
mostCurrent._lblpregunta = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 24;BA.debugLine="Private btnRegister As Button";
mostCurrent._btnregister = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 26;BA.debugLine="Dim avatarNumber As Int";
_avatarnumber = 0;
 //BA.debugLineNum = 27;BA.debugLine="Private btnOkAvatar As Button";
mostCurrent._btnokavatar = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 28;BA.debugLine="Private btnOkEmail As Button";
mostCurrent._btnokemail = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 29;BA.debugLine="Private txtEmail As EditText";
mostCurrent._txtemail = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 30;BA.debugLine="Private btnOkPassword As Button";
mostCurrent._btnokpassword = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 31;BA.debugLine="Private txtPassword1 As EditText";
mostCurrent._txtpassword1 = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 32;BA.debugLine="Private txtPassword2 As EditText";
mostCurrent._txtpassword2 = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 33;BA.debugLine="Private lblHola As Label";
mostCurrent._lblhola = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 35;BA.debugLine="Dim p As Phone";
mostCurrent._p = new anywheresoftware.b4a.phone.Phone();
 //BA.debugLineNum = 37;BA.debugLine="Dim currentStage As Int";
_currentstage = 0;
 //BA.debugLineNum = 38;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 6;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 7;BA.debugLine="Dim PlayerName As String";
_playername = "";
 //BA.debugLineNum = 8;BA.debugLine="Dim PlayerUsername As String";
_playerusername = "";
 //BA.debugLineNum = 9;BA.debugLine="Dim PlayerPassword As String";
_playerpassword = "";
 //BA.debugLineNum = 10;BA.debugLine="Dim PlayerEmail As String";
_playeremail = "";
 //BA.debugLineNum = 11;BA.debugLine="Dim PlayerUserID As String";
_playeruserid = "";
 //BA.debugLineNum = 12;BA.debugLine="End Sub";
return "";
}
public static String  _txtplayername_textchanged(String _old,String _new) throws Exception{
 //BA.debugLineNum = 101;BA.debugLine="Private Sub txtPlayerName_TextChanged (Old As Stri";
 //BA.debugLineNum = 102;BA.debugLine="btnOkName.SetVisibleAnimated(300, True)";
mostCurrent._btnokname.SetVisibleAnimated((int) (300),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 103;BA.debugLine="End Sub";
return "";
}
public static boolean  _validate_email(String _emailaddress) throws Exception{
anywheresoftware.b4a.keywords.Regex.MatcherWrapper _matchemail = null;
 //BA.debugLineNum = 190;BA.debugLine="Sub Validate_Email(EmailAddress As String) As Bool";
 //BA.debugLineNum = 191;BA.debugLine="Dim MatchEmail As Matcher = Regex.Matcher(\"^(?i)[";
_matchemail = new anywheresoftware.b4a.keywords.Regex.MatcherWrapper();
_matchemail = anywheresoftware.b4a.keywords.Common.Regex.Matcher("^(?i)[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])$",_emailaddress);
 //BA.debugLineNum = 192;BA.debugLine="If MatchEmail.Find = True Then";
if (_matchemail.Find()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 193;BA.debugLine="Return True";
if (true) return anywheresoftware.b4a.keywords.Common.True;
 }else {
 //BA.debugLineNum = 195;BA.debugLine="Return False";
if (true) return anywheresoftware.b4a.keywords.Common.False;
 };
 //BA.debugLineNum = 197;BA.debugLine="End Sub";
return false;
}
}
