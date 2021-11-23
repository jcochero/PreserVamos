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

public class register extends Activity implements B4AActivity{
	public static register mostCurrent;
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
			processBA = new BA(this.getApplicationContext(), null, null, "ilpla.appear", "ilpla.appear.register");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
            
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (register).");
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
		activityBA = new BA(this, layout, processBA, "ilpla.appear", "ilpla.appear.register");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "ilpla.appear.register", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density, mostCurrent);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (register) Create, isFirst = " + isFirst + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (register) Resume **");
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
		return register.class;
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
            BA.LogInfo("** Activity (register) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
        else
            BA.LogInfo("** Activity (register) Pause event (activity is not paused). **");
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
            register mc = mostCurrent;
			if (mc == null || mc != activity.get())
				return;
			processBA.setActivityPaused(false);
            BA.LogInfo("** Activity (register) Resume **");
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
public anywheresoftware.b4a.objects.EditTextWrapper _txtuserid = null;
public anywheresoftware.b4a.objects.EditTextWrapper _txtpassword = null;
public anywheresoftware.b4a.objects.EditTextWrapper _txtpassword2 = null;
public anywheresoftware.b4a.objects.EditTextWrapper _txtemail = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblunmaskpass = null;
public anywheresoftware.b4a.objects.SpinnerWrapper _cmbmunicipio = null;
public anywheresoftware.b4a.objects.TabStripViewPager _tabregister = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblcirculopos1 = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblcirculopos2 = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblcirculopos3 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnprev = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnnext = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper _chkempleadomunicipio = null;
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
 //BA.debugLineNum = 26;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
 //BA.debugLineNum = 27;BA.debugLine="Activity.LoadLayout(\"layRegister\")";
mostCurrent._activity.LoadLayout("layRegister",mostCurrent.activityBA);
 //BA.debugLineNum = 29;BA.debugLine="tabRegister.LoadLayout(\"layRegister_Tab1\", \"1\")";
mostCurrent._tabregister.LoadLayout("layRegister_Tab1",BA.ObjectToCharSequence("1"));
 //BA.debugLineNum = 30;BA.debugLine="tabRegister.LoadLayout(\"layRegister_Tab2\", \"2\")";
mostCurrent._tabregister.LoadLayout("layRegister_Tab2",BA.ObjectToCharSequence("2"));
 //BA.debugLineNum = 31;BA.debugLine="tabRegister.LoadLayout(\"layRegister_Tab3\", \"3\")";
mostCurrent._tabregister.LoadLayout("layRegister_Tab3",BA.ObjectToCharSequence("3"));
 //BA.debugLineNum = 32;BA.debugLine="End Sub";
return "";
}
public static boolean  _activity_keypress(int _keycode) throws Exception{
 //BA.debugLineNum = 33;BA.debugLine="Sub Activity_KeyPress (KeyCode As Int) As Boolean";
 //BA.debugLineNum = 34;BA.debugLine="If KeyCode = KeyCodes.KEYCODE_BACK Then";
if (_keycode==anywheresoftware.b4a.keywords.Common.KeyCodes.KEYCODE_BACK) { 
 //BA.debugLineNum = 35;BA.debugLine="closeAppMsgBox";
_closeappmsgbox();
 //BA.debugLineNum = 36;BA.debugLine="Return True";
if (true) return anywheresoftware.b4a.keywords.Common.True;
 }else {
 //BA.debugLineNum = 38;BA.debugLine="Return False";
if (true) return anywheresoftware.b4a.keywords.Common.False;
 };
 //BA.debugLineNum = 40;BA.debugLine="End Sub";
return false;
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 44;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 46;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 41;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 43;BA.debugLine="End Sub";
return "";
}
public static String  _btncancelarregistro_click() throws Exception{
 //BA.debugLineNum = 313;BA.debugLine="Sub btnCancelarRegistro_Click";
 //BA.debugLineNum = 314;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 315;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 316;BA.debugLine="End Sub";
return "";
}
public static String  _btnnext_click() throws Exception{
 //BA.debugLineNum = 86;BA.debugLine="Private Sub btnNext_Click";
 //BA.debugLineNum = 87;BA.debugLine="If tabRegister.CurrentPage = 0 Then";
if (mostCurrent._tabregister.getCurrentPage()==0) { 
 //BA.debugLineNum = 88;BA.debugLine="tabRegister.ScrollTo(1, True)";
mostCurrent._tabregister.ScrollTo((int) (1),anywheresoftware.b4a.keywords.Common.True);
 }else if(mostCurrent._tabregister.getCurrentPage()==1) { 
 //BA.debugLineNum = 90;BA.debugLine="tabRegister.ScrollTo(2, True)";
mostCurrent._tabregister.ScrollTo((int) (2),anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 92;BA.debugLine="End Sub";
return "";
}
public static String  _btnprev_click() throws Exception{
 //BA.debugLineNum = 94;BA.debugLine="Private Sub btnPrev_Click";
 //BA.debugLineNum = 95;BA.debugLine="If tabRegister.CurrentPage = 1 Then";
if (mostCurrent._tabregister.getCurrentPage()==1) { 
 //BA.debugLineNum = 96;BA.debugLine="tabRegister.ScrollTo(0, True)";
mostCurrent._tabregister.ScrollTo((int) (0),anywheresoftware.b4a.keywords.Common.True);
 }else if(mostCurrent._tabregister.getCurrentPage()==2) { 
 //BA.debugLineNum = 98;BA.debugLine="tabRegister.ScrollTo(1, True)";
mostCurrent._tabregister.ScrollTo((int) (1),anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 100;BA.debugLine="End Sub";
return "";
}
public static String  _btnregister_click() throws Exception{
String _struserid = "";
String _stremail = "";
 //BA.debugLineNum = 119;BA.debugLine="Sub btnRegister_Click";
 //BA.debugLineNum = 120;BA.debugLine="Dim strUserID As String = txtUserID.Text.Trim";
_struserid = mostCurrent._txtuserid.getText().trim();
 //BA.debugLineNum = 121;BA.debugLine="If strUserID = \"\" Then";
if ((_struserid).equals("")) { 
 //BA.debugLineNum = 122;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 123;BA.debugLine="MsgboxAsync(\"Ingrese nombre de usuario\", \"Error";
anywheresoftware.b4a.keywords.Common.MsgboxAsync(BA.ObjectToCharSequence("Ingrese nombre de usuario"),BA.ObjectToCharSequence("Error"),processBA);
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 125;BA.debugLine="MsgboxAsync(\"Enter a username\", \"Error\")";
anywheresoftware.b4a.keywords.Common.MsgboxAsync(BA.ObjectToCharSequence("Enter a username"),BA.ObjectToCharSequence("Error"),processBA);
 };
 //BA.debugLineNum = 127;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 158;BA.debugLine="Dim strEmail As String = txtEmail.Text.Trim";
_stremail = mostCurrent._txtemail.getText().trim();
 //BA.debugLineNum = 159;BA.debugLine="If strEmail = \"\" Then";
if ((_stremail).equals("")) { 
 //BA.debugLineNum = 160;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 161;BA.debugLine="MsgboxAsync(\"Ingrese email\", \"Error\")";
anywheresoftware.b4a.keywords.Common.MsgboxAsync(BA.ObjectToCharSequence("Ingrese email"),BA.ObjectToCharSequence("Error"),processBA);
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 163;BA.debugLine="MsgboxAsync(\"Enter your email\", \"Error\")";
anywheresoftware.b4a.keywords.Common.MsgboxAsync(BA.ObjectToCharSequence("Enter your email"),BA.ObjectToCharSequence("Error"),processBA);
 };
 //BA.debugLineNum = 165;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 167;BA.debugLine="If Validate_Email(strEmail) = False Then";
if (_validate_email(_stremail)==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 168;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 169;BA.debugLine="MsgboxAsync(\"Formato de email incorrecto\", \"Err";
anywheresoftware.b4a.keywords.Common.MsgboxAsync(BA.ObjectToCharSequence("Formato de email incorrecto"),BA.ObjectToCharSequence("Error"),processBA);
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 171;BA.debugLine="MsgboxAsync(\"Incorrect format for the email add";
anywheresoftware.b4a.keywords.Common.MsgboxAsync(BA.ObjectToCharSequence("Incorrect format for the email address"),BA.ObjectToCharSequence("Error"),processBA);
 };
 //BA.debugLineNum = 173;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 176;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 177;BA.debugLine="ProgressDialogShow(\"Registrando el usuario...\")";
anywheresoftware.b4a.keywords.Common.ProgressDialogShow(mostCurrent.activityBA,BA.ObjectToCharSequence("Registrando el usuario..."));
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 179;BA.debugLine="ProgressDialogShow(\"Signing up user...\")";
anywheresoftware.b4a.keywords.Common.ProgressDialogShow(mostCurrent.activityBA,BA.ObjectToCharSequence("Signing up user..."));
 };
 //BA.debugLineNum = 183;BA.debugLine="RegisterUser";
_registeruser();
 //BA.debugLineNum = 184;BA.debugLine="End Sub";
return "";
}
public static void  _closeappmsgbox() throws Exception{
ResumableSub_closeAppMsgBox rsub = new ResumableSub_closeAppMsgBox(null);
rsub.resume(processBA, null);
}
public static class ResumableSub_closeAppMsgBox extends BA.ResumableSub {
public ResumableSub_closeAppMsgBox(ilpla.appear.register parent) {
this.parent = parent;
}
ilpla.appear.register parent;
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
 //BA.debugLineNum = 50;BA.debugLine="Msgbox2Async(\"Cerrar PreserVamos?\", \"SALIR\", \"Si\"";
anywheresoftware.b4a.keywords.Common.Msgbox2Async(BA.ObjectToCharSequence("Cerrar PreserVamos?"),BA.ObjectToCharSequence("SALIR"),"Si","","No",(anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper(), (android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null)),processBA,anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 51;BA.debugLine="Wait For Msgbox_Result (Result As Int)";
anywheresoftware.b4a.keywords.Common.WaitFor("msgbox_result", processBA, this, null);
this.state = 5;
return;
case 5:
//C
this.state = 1;
_result = (Integer) result[0];
;
 //BA.debugLineNum = 52;BA.debugLine="If Result = DialogResponse.POSITIVE Then";
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
 //BA.debugLineNum = 53;BA.debugLine="Activity.RemoveAllViews";
parent.mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 54;BA.debugLine="Activity.finish";
parent.mostCurrent._activity.Finish();
 //BA.debugLineNum = 55;BA.debugLine="ExitApplication";
anywheresoftware.b4a.keywords.Common.ExitApplication();
 if (true) break;

case 4:
//C
this.state = -1;
;
 //BA.debugLineNum = 57;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public static void  _msgbox_result(int _result) throws Exception{
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 10;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 11;BA.debugLine="Private txtUserID As EditText";
mostCurrent._txtuserid = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 12;BA.debugLine="Private txtPassword As EditText";
mostCurrent._txtpassword = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 13;BA.debugLine="Private txtPassword2 As EditText";
mostCurrent._txtpassword2 = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 14;BA.debugLine="Private txtEmail As EditText";
mostCurrent._txtemail = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 15;BA.debugLine="Private lblUnmaskPass As Label";
mostCurrent._lblunmaskpass = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 16;BA.debugLine="Private cmbMunicipio As Spinner";
mostCurrent._cmbmunicipio = new anywheresoftware.b4a.objects.SpinnerWrapper();
 //BA.debugLineNum = 17;BA.debugLine="Private tabRegister As TabStrip";
mostCurrent._tabregister = new anywheresoftware.b4a.objects.TabStripViewPager();
 //BA.debugLineNum = 18;BA.debugLine="Private lblCirculoPos1 As Label";
mostCurrent._lblcirculopos1 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 19;BA.debugLine="Private lblCirculoPos2 As Label";
mostCurrent._lblcirculopos2 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 20;BA.debugLine="Private lblCirculoPos3 As Label";
mostCurrent._lblcirculopos3 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 21;BA.debugLine="Private btnPrev As Button";
mostCurrent._btnprev = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 22;BA.debugLine="Private btnNext As Button";
mostCurrent._btnnext = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 23;BA.debugLine="Private chkEmpleadoMunicipio As CheckBox";
mostCurrent._chkempleadomunicipio = new anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper();
 //BA.debugLineNum = 24;BA.debugLine="End Sub";
return "";
}
public static String  _lblleerlegales_click() throws Exception{
 //BA.debugLineNum = 324;BA.debugLine="Sub lblLeerLegales_Click";
 //BA.debugLineNum = 325;BA.debugLine="StartActivity(frmPoliticaDatos)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._frmpoliticadatos.getObject()));
 //BA.debugLineNum = 326;BA.debugLine="End Sub";
return "";
}
public static String  _lblunmaskpass_click() throws Exception{
 //BA.debugLineNum = 107;BA.debugLine="Sub lblUnmaskPass_Click";
 //BA.debugLineNum = 108;BA.debugLine="If lblUnmaskPass.Text = \"\" Then";
if ((mostCurrent._lblunmaskpass.getText()).equals("")) { 
 //BA.debugLineNum = 109;BA.debugLine="txtPassword.PasswordMode = True";
mostCurrent._txtpassword.setPasswordMode(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 110;BA.debugLine="txtPassword2.PasswordMode = True";
mostCurrent._txtpassword2.setPasswordMode(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 111;BA.debugLine="lblUnmaskPass.Text = \"\"";
mostCurrent._lblunmaskpass.setText(BA.ObjectToCharSequence(""));
 }else if((mostCurrent._lblunmaskpass.getText()).equals("")) { 
 //BA.debugLineNum = 113;BA.debugLine="txtPassword.PasswordMode = False";
mostCurrent._txtpassword.setPasswordMode(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 114;BA.debugLine="txtPassword2.PasswordMode = False";
mostCurrent._txtpassword2.setPasswordMode(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 115;BA.debugLine="lblUnmaskPass.Text = \"\"";
mostCurrent._lblunmaskpass.setText(BA.ObjectToCharSequence(""));
 };
 //BA.debugLineNum = 117;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 6;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 8;BA.debugLine="End Sub";
return "";
}
public static String  _registeruser() throws Exception{
 //BA.debugLineNum = 185;BA.debugLine="Sub RegisterUser";
 //BA.debugLineNum = 188;BA.debugLine="Log(\"UserID=\" & txtUserID.Text & \"&\" & _ 	  \"Pass";
anywheresoftware.b4a.keywords.Common.LogImpl("224969219","UserID="+mostCurrent._txtuserid.getText()+"&"+"Password="+mostCurrent._txtpassword.getText()+"&"+"Email="+mostCurrent._txtemail.getText()+"&"+"usergroup="+mostCurrent._cmbmunicipio.getSelectedItem()+"&"+"deviceID="+mostCurrent._main._deviceid /*String*/ ,0);
 //BA.debugLineNum = 194;BA.debugLine="Main.username = txtUserID.Text";
mostCurrent._main._username /*String*/  = mostCurrent._txtuserid.getText();
 //BA.debugLineNum = 195;BA.debugLine="Main.strUserEmail = txtEmail.Text";
mostCurrent._main._struseremail /*String*/  = mostCurrent._txtemail.getText();
 //BA.debugLineNum = 196;BA.debugLine="Main.pass = txtPassword.Text";
mostCurrent._main._pass /*String*/  = mostCurrent._txtpassword.getText();
 //BA.debugLineNum = 197;BA.debugLine="Main.strUserOrg = cmbMunicipio.SelectedItem";
mostCurrent._main._struserorg /*String*/  = mostCurrent._cmbmunicipio.getSelectedItem();
 //BA.debugLineNum = 199;BA.debugLine="If chkEmpleadoMunicipio.Checked = True Then";
if (mostCurrent._chkempleadomunicipio.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 200;BA.debugLine="Main.strUserTipoUsuario = \"municipal\"";
mostCurrent._main._strusertipousuario /*String*/  = "municipal";
 }else {
 //BA.debugLineNum = 202;BA.debugLine="Main.strUserTipoUsuario = \"no municipal\"";
mostCurrent._main._strusertipousuario /*String*/  = "no municipal";
 };
 //BA.debugLineNum = 206;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 207;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 209;BA.debugLine="Main.strUserOrg = cmbMunicipio.SelectedItem";
mostCurrent._main._struserorg /*String*/  = mostCurrent._cmbmunicipio.getSelectedItem();
 //BA.debugLineNum = 225;BA.debugLine="End Sub";
return "";
}
public static String  _registeruser_complete(ilpla.appear.httpjob _job) throws Exception{
String _res = "";
String _action = "";
anywheresoftware.b4a.objects.collections.JSONParser _parser = null;
anywheresoftware.b4a.objects.collections.Map _mapreset = null;
anywheresoftware.b4a.objects.collections.Map _map1 = null;
 //BA.debugLineNum = 226;BA.debugLine="Sub RegisterUser_Complete(Job As HttpJob)";
 //BA.debugLineNum = 227;BA.debugLine="Log(\"Register user: \" & Job.Success)";
anywheresoftware.b4a.keywords.Common.LogImpl("225034753","Register user: "+BA.ObjectToString(_job._success /*boolean*/ ),0);
 //BA.debugLineNum = 228;BA.debugLine="If Job.Success = True Then";
if (_job._success /*boolean*/ ==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 229;BA.debugLine="Dim res As String, action As String";
_res = "";
_action = "";
 //BA.debugLineNum = 230;BA.debugLine="res = Job.GetString";
_res = _job._getstring /*String*/ ();
 //BA.debugLineNum = 231;BA.debugLine="Dim parser As JSONParser";
_parser = new anywheresoftware.b4a.objects.collections.JSONParser();
 //BA.debugLineNum = 232;BA.debugLine="parser.Initialize(res)";
_parser.Initialize(_res);
 //BA.debugLineNum = 233;BA.debugLine="action = parser.NextValue";
_action = BA.ObjectToString(_parser.NextValue());
 //BA.debugLineNum = 234;BA.debugLine="If action = \"Mail\" Then";
if ((_action).equals("Mail")) { 
 //BA.debugLineNum = 235;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 237;BA.debugLine="utilidades.Mensaje(\"\", \"\", \"Que alegría!!!\", \"";
mostCurrent._utilidades._mensaje /*String*/ (mostCurrent.activityBA,"","","Que alegría!!!","Has completado el registro, ya puedes participar de la experiencia PreserVamos","Que bien!","","",anywheresoftware.b4a.keywords.Common.True);
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 239;BA.debugLine="MsgboxAsync(\"User correctly registered!\", \"Con";
anywheresoftware.b4a.keywords.Common.MsgboxAsync(BA.ObjectToCharSequence("User correctly registered!"),BA.ObjectToCharSequence("Congrats"),processBA);
 };
 //BA.debugLineNum = 243;BA.debugLine="Main.username = txtUserID.Text";
mostCurrent._main._username /*String*/  = mostCurrent._txtuserid.getText();
 //BA.debugLineNum = 244;BA.debugLine="Main.pass = txtPassword.Text";
mostCurrent._main._pass /*String*/  = mostCurrent._txtpassword.getText();
 //BA.debugLineNum = 245;BA.debugLine="Main.strUserEmail = txtEmail.Text";
mostCurrent._main._struseremail /*String*/  = mostCurrent._txtemail.getText();
 //BA.debugLineNum = 246;BA.debugLine="Main.strUserOrg = cmbMunicipio.SelectedItem";
mostCurrent._main._struserorg /*String*/  = mostCurrent._cmbmunicipio.getSelectedItem();
 //BA.debugLineNum = 248;BA.debugLine="If chkEmpleadoMunicipio.Checked = True Then";
if (mostCurrent._chkempleadomunicipio.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 249;BA.debugLine="Main.strUserTipoUsuario = \"municipal\"";
mostCurrent._main._strusertipousuario /*String*/  = "municipal";
 }else {
 //BA.debugLineNum = 251;BA.debugLine="Main.strUserTipoUsuario = \"no municipal\"";
mostCurrent._main._strusertipousuario /*String*/  = "no municipal";
 };
 //BA.debugLineNum = 256;BA.debugLine="Dim MapReset As Map";
_mapreset = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 257;BA.debugLine="MapReset.Initialize";
_mapreset.Initialize();
 //BA.debugLineNum = 258;BA.debugLine="MapReset.Put(\"lastuser\", \"si\")";
_mapreset.Put((Object)("lastuser"),(Object)("si"));
 //BA.debugLineNum = 259;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"userconfig";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"userconfig","lastuser",(Object)("no"),_mapreset);
 //BA.debugLineNum = 261;BA.debugLine="Dim Map1 As Map";
_map1 = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 262;BA.debugLine="Map1.Initialize";
_map1.Initialize();
 //BA.debugLineNum = 263;BA.debugLine="Map1.Put(\"username\", Main.username)";
_map1.Put((Object)("username"),(Object)(mostCurrent._main._username /*String*/ ));
 //BA.debugLineNum = 264;BA.debugLine="Main.strUserName = Main.username";
mostCurrent._main._strusername /*String*/  = mostCurrent._main._username /*String*/ ;
 //BA.debugLineNum = 265;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"userconfig";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"userconfig","lastuser",(Object)("si"),_map1);
 //BA.debugLineNum = 266;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"userconfig";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"userconfig","userLocation",(Object)(mostCurrent._main._struserlocation /*String*/ ),_map1);
 //BA.debugLineNum = 267;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"userconfig";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"userconfig","userEmail",(Object)(mostCurrent._main._struseremail /*String*/ ),_map1);
 //BA.debugLineNum = 268;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"userconfig";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"userconfig","userFullName",(Object)(mostCurrent._main._struserfullname /*String*/ ),_map1);
 //BA.debugLineNum = 269;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"userconfig";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"userconfig","userOrg",(Object)(mostCurrent._main._struserorg /*String*/ ),_map1);
 //BA.debugLineNum = 270;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"userconfig";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"userconfig","UserTipoUsuario",(Object)(mostCurrent._main._strusertipousuario /*String*/ ),_map1);
 //BA.debugLineNum = 272;BA.debugLine="Log(\"DB interna actualizada\")";
anywheresoftware.b4a.keywords.Common.LogImpl("225034798","DB interna actualizada",0);
 //BA.debugLineNum = 274;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 275;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 }else if((_action).equals("MailInUse")) { 
 //BA.debugLineNum = 277;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 278;BA.debugLine="MsgboxAsync(\"El usuario '\" & txtUserID.Text &";
anywheresoftware.b4a.keywords.Common.MsgboxAsync(BA.ObjectToCharSequence("El usuario '"+mostCurrent._txtuserid.getText()+"' o el email ("+mostCurrent._txtemail.getText()+") ya están en uso"),BA.ObjectToCharSequence("Registro"),processBA);
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 280;BA.debugLine="MsgboxAsync(\"The username '\" & txtUserID.Text";
anywheresoftware.b4a.keywords.Common.MsgboxAsync(BA.ObjectToCharSequence("The username '"+mostCurrent._txtuserid.getText()+"' or the email ("+mostCurrent._txtemail.getText()+") already exist"),BA.ObjectToCharSequence("Sign up"),processBA);
 };
 }else {
 //BA.debugLineNum = 283;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 284;BA.debugLine="MsgboxAsync(\"El servidor no devolvió los valor";
anywheresoftware.b4a.keywords.Common.MsgboxAsync(BA.ObjectToCharSequence("El servidor no devolvió los valores esperados."+_job._errormessage /*String*/ ),BA.ObjectToCharSequence("Registro"),processBA);
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 286;BA.debugLine="MsgboxAsync(\"The server did not return the exp";
anywheresoftware.b4a.keywords.Common.MsgboxAsync(BA.ObjectToCharSequence("The server did not return the expected values."+_job._errormessage /*String*/ ),BA.ObjectToCharSequence("Sign up"),processBA);
 };
 };
 }else {
 //BA.debugLineNum = 291;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 292;BA.debugLine="ToastMessageShow(\"No se pudo registrar el usuar";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("No se pudo registrar el usuario, hay un error de servidor"),anywheresoftware.b4a.keywords.Common.False);
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 294;BA.debugLine="ToastMessageShow(\"Couldn't register user, serve";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Couldn't register user, server error"),anywheresoftware.b4a.keywords.Common.False);
 };
 };
 //BA.debugLineNum = 297;BA.debugLine="Job.Release";
_job._release /*String*/ ();
 //BA.debugLineNum = 298;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 //BA.debugLineNum = 299;BA.debugLine="End Sub";
return "";
}
public static String  _tabregister_pageselected(int _position) throws Exception{
anywheresoftware.b4a.objects.collections.List _list1 = null;
 //BA.debugLineNum = 59;BA.debugLine="Private Sub tabRegister_PageSelected (Position As";
 //BA.debugLineNum = 60;BA.debugLine="If Position = 2 Then";
if (_position==2) { 
 //BA.debugLineNum = 61;BA.debugLine="btnNext.Visible = False";
mostCurrent._btnnext.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 62;BA.debugLine="btnPrev.Visible = True";
mostCurrent._btnprev.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 63;BA.debugLine="lblCirculoPos3.Color = Colors.RGB(174,228,174)";
mostCurrent._lblcirculopos3.setColor(anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (174),(int) (228),(int) (174)));
 //BA.debugLineNum = 64;BA.debugLine="lblCirculoPos2.Color = Colors.RGB(62,119,104)";
mostCurrent._lblcirculopos2.setColor(anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (62),(int) (119),(int) (104)));
 //BA.debugLineNum = 65;BA.debugLine="lblCirculoPos1.Color = Colors.RGB(62,119,104)";
mostCurrent._lblcirculopos1.setColor(anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (62),(int) (119),(int) (104)));
 //BA.debugLineNum = 67;BA.debugLine="Dim List1 As List";
_list1 = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 68;BA.debugLine="List1 = File.ReadList(File.DirAssets, \"partidos.";
_list1 = anywheresoftware.b4a.keywords.Common.File.ReadList(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"partidos.txt");
 //BA.debugLineNum = 69;BA.debugLine="cmbMunicipio.AddAll(List1)";
mostCurrent._cmbmunicipio.AddAll(_list1);
 }else if(_position==0) { 
 //BA.debugLineNum = 71;BA.debugLine="btnPrev.Visible = False";
mostCurrent._btnprev.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 72;BA.debugLine="btnNext.Visible = True";
mostCurrent._btnnext.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 73;BA.debugLine="lblCirculoPos1.Color = Colors.RGB(174,228,174)";
mostCurrent._lblcirculopos1.setColor(anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (174),(int) (228),(int) (174)));
 //BA.debugLineNum = 74;BA.debugLine="lblCirculoPos2.Color = Colors.RGB(62,119,104)";
mostCurrent._lblcirculopos2.setColor(anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (62),(int) (119),(int) (104)));
 //BA.debugLineNum = 75;BA.debugLine="lblCirculoPos3.Color = Colors.RGB(62,119,104)";
mostCurrent._lblcirculopos3.setColor(anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (62),(int) (119),(int) (104)));
 }else if(_position==1) { 
 //BA.debugLineNum = 77;BA.debugLine="btnPrev.Visible = True";
mostCurrent._btnprev.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 78;BA.debugLine="btnNext.Visible = True";
mostCurrent._btnnext.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 79;BA.debugLine="lblCirculoPos2.Color = Colors.RGB(174,228,174)";
mostCurrent._lblcirculopos2.setColor(anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (174),(int) (228),(int) (174)));
 //BA.debugLineNum = 80;BA.debugLine="lblCirculoPos3.Color = Colors.RGB(62,119,104)";
mostCurrent._lblcirculopos3.setColor(anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (62),(int) (119),(int) (104)));
 //BA.debugLineNum = 81;BA.debugLine="lblCirculoPos1.Color = Colors.RGB(62,119,104)";
mostCurrent._lblcirculopos1.setColor(anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (62),(int) (119),(int) (104)));
 };
 //BA.debugLineNum = 83;BA.debugLine="End Sub";
return "";
}
public static boolean  _validate_email(String _emailaddress) throws Exception{
anywheresoftware.b4a.keywords.Regex.MatcherWrapper _matchemail = null;
 //BA.debugLineNum = 300;BA.debugLine="Sub Validate_Email(EmailAddress As String) As Bool";
 //BA.debugLineNum = 301;BA.debugLine="Dim MatchEmail As Matcher = Regex.Matcher(\"^(?";
_matchemail = new anywheresoftware.b4a.keywords.Regex.MatcherWrapper();
_matchemail = anywheresoftware.b4a.keywords.Common.Regex.Matcher("^(?i)[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])$",_emailaddress);
 //BA.debugLineNum = 303;BA.debugLine="If MatchEmail.Find = True Then";
if (_matchemail.Find()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 305;BA.debugLine="Return True";
if (true) return anywheresoftware.b4a.keywords.Common.True;
 }else {
 //BA.debugLineNum = 308;BA.debugLine="Return False";
if (true) return anywheresoftware.b4a.keywords.Common.False;
 };
 //BA.debugLineNum = 310;BA.debugLine="End Sub";
return false;
}
}
