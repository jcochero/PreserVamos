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

public class reporte_envio extends Activity implements B4AActivity{
	public static reporte_envio mostCurrent;
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
			processBA = new BA(this.getApplicationContext(), null, null, "ilpla.appear", "ilpla.appear.reporte_envio");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
            
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (reporte_envio).");
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
		activityBA = new BA(this, layout, processBA, "ilpla.appear", "ilpla.appear.reporte_envio");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "ilpla.appear.reporte_envio", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density, mostCurrent);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (reporte_envio) Create, isFirst = " + isFirst + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (reporte_envio) Resume **");
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
		return reporte_envio.class;
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
            BA.LogInfo("** Activity (reporte_envio) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
        else
            BA.LogInfo("** Activity (reporte_envio) Pause event (activity is not paused). **");
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
            reporte_envio mc = mostCurrent;
			if (mc == null || mc != activity.get())
				return;
			processBA.setActivityPaused(false);
            BA.LogInfo("** Activity (reporte_envio) Resume **");
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
public static com.spinter.uploadfilephp.UploadFilePhp _up1 = null;
public static anywheresoftware.b4a.objects.Timer _timerenvio = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btncontinuar = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnenviar = null;
public static int _numfotosenviadas = 0;
public static String _tiporio = "";
public static String _puntosfotos = "";
public static String _puntosevals = "";
public static String _puntostotal = "";
public static String _numriollanura = "";
public static String _numriomontana = "";
public static String _numlaguna = "";
public static String _numestuario = "";
public static String _proyectoidenviar = "";
public anywheresoftware.b4a.objects.collections.List _files = null;
public anywheresoftware.b4a.objects.Timer _timer1 = null;
public anywheresoftware.b4a.objects.LabelWrapper _label1 = null;
public anywheresoftware.b4a.objects.LabelWrapper _label2 = null;
public anywheresoftware.b4a.objects.LabelWrapper _label3 = null;
public anywheresoftware.b4a.objects.LabelWrapper _label4 = null;
public anywheresoftware.b4a.objects.LabelWrapper _label5 = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblfinalizado1 = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblyatienestodo = null;
public static String _foto1 = "";
public static String _foto2 = "";
public static String _foto3 = "";
public static String _foto4 = "";
public static String _foto5 = "";
public static int _totalfotos = 0;
public static boolean _foto1sent = false;
public static boolean _foto2sent = false;
public static boolean _foto3sent = false;
public static boolean _foto4sent = false;
public static boolean _foto5sent = false;
public anywheresoftware.b4a.objects.ProgressBarWrapper _progressbar1 = null;
public anywheresoftware.b4a.objects.ProgressBarWrapper _progressbar2 = null;
public anywheresoftware.b4a.objects.ProgressBarWrapper _progressbar3 = null;
public anywheresoftware.b4a.objects.ProgressBarWrapper _progressbar4 = null;
public anywheresoftware.b4a.objects.ProgressBarWrapper _progressbar5 = null;
public ilpla.appear.circularprogressbar _foto1progressbar = null;
public ilpla.appear.circularprogressbar _foto2progressbar = null;
public ilpla.appear.circularprogressbar _foto3progressbar = null;
public ilpla.appear.circularprogressbar _foto4progressbar = null;
public static int _fotosenviadas = 0;
public anywheresoftware.b4a.phone.Phone.PhoneWakeState _pw = null;
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
 //BA.debugLineNum = 66;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
 //BA.debugLineNum = 68;BA.debugLine="Activity.LoadLayout(\"layReporte_Envio_Resumen\")";
mostCurrent._activity.LoadLayout("layReporte_Envio_Resumen",mostCurrent.activityBA);
 //BA.debugLineNum = 70;BA.debugLine="Label1.Visible = False";
mostCurrent._label1.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 71;BA.debugLine="Label2.Visible = False";
mostCurrent._label2.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 72;BA.debugLine="Label3.Visible = False";
mostCurrent._label3.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 73;BA.debugLine="Label4.Visible = False";
mostCurrent._label4.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 74;BA.debugLine="Label5.Visible = False";
mostCurrent._label5.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 75;BA.debugLine="lblFinalizado1.Visible = False";
mostCurrent._lblfinalizado1.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 76;BA.debugLine="lblYaTienesTodo.Visible= False";
mostCurrent._lblyatienestodo.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 77;BA.debugLine="Timer1.Initialize(\"Timer1\",250)";
mostCurrent._timer1.Initialize(processBA,"Timer1",(long) (250));
 //BA.debugLineNum = 78;BA.debugLine="Timer1.Enabled = True";
mostCurrent._timer1.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 80;BA.debugLine="End Sub";
return "";
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 86;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 88;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 82;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 84;BA.debugLine="End Sub";
return "";
}
public static void  _btnenviar_click() throws Exception{
ResumableSub_btnEnviar_Click rsub = new ResumableSub_btnEnviar_Click(null);
rsub.resume(processBA, null);
}
public static class ResumableSub_btnEnviar_Click extends BA.ResumableSub {
public ResumableSub_btnEnviar_Click(ilpla.appear.reporte_envio parent) {
this.parent = parent;
}
ilpla.appear.reporte_envio parent;
String _msg = "";
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
 //BA.debugLineNum = 139;BA.debugLine="Label1.Visible= False";
parent.mostCurrent._label1.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 140;BA.debugLine="Label2.Visible= False";
parent.mostCurrent._label2.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 141;BA.debugLine="Label3.Visible= False";
parent.mostCurrent._label3.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 146;BA.debugLine="If Main.username = \"guest\" Or Main.username = \"No";
if (true) break;

case 1:
//if
this.state = 13;
if ((parent.mostCurrent._main._username /*String*/ ).equals("guest") || (parent.mostCurrent._main._username /*String*/ ).equals("None")) { 
this.state = 3;
}if (true) break;

case 3:
//C
this.state = 4;
 //BA.debugLineNum = 147;BA.debugLine="Dim msg As String";
_msg = "";
 //BA.debugLineNum = 148;BA.debugLine="If Main.lang = \"es\" Then";
if (true) break;

case 4:
//if
this.state = 9;
if ((parent.mostCurrent._main._lang /*String*/ ).equals("es")) { 
this.state = 6;
}else if((parent.mostCurrent._main._lang /*String*/ ).equals("en")) { 
this.state = 8;
}if (true) break;

case 6:
//C
this.state = 9;
 //BA.debugLineNum = 149;BA.debugLine="msg = utilidades.Mensaje(\"Educación\", \"MsgIcon.";
_msg = parent.mostCurrent._utilidades._mensaje /*String*/ (mostCurrent.activityBA,"Educación","MsgIcon.png","Modo Educación","Has elegido participar en Modo de Educación, no se enviarán datos al servidor","Ok, gracias!","","",anywheresoftware.b4a.keywords.Common.True);
 if (true) break;

case 8:
//C
this.state = 9;
 //BA.debugLineNum = 151;BA.debugLine="msg = utilidades.Mensaje(\"Test\", \"MsgIcon.png\",";
_msg = parent.mostCurrent._utilidades._mensaje /*String*/ (mostCurrent.activityBA,"Test","MsgIcon.png","Log in as test user","You have chosen to participate as a Test user, no data will be sent to the server","Yes, continue","","",anywheresoftware.b4a.keywords.Common.True);
 if (true) break;
;
 //BA.debugLineNum = 153;BA.debugLine="If msg = DialogResponse.POSITIVE Then";

case 9:
//if
this.state = 12;
if ((_msg).equals(BA.NumberToString(anywheresoftware.b4a.keywords.Common.DialogResponse.POSITIVE))) { 
this.state = 11;
}if (true) break;

case 11:
//C
this.state = 12;
 //BA.debugLineNum = 154;BA.debugLine="Activity.RemoveAllViews";
parent.mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 155;BA.debugLine="Activity.Finish";
parent.mostCurrent._activity.Finish();
 //BA.debugLineNum = 156;BA.debugLine="StartActivity(Form_Main)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(parent.mostCurrent._form_main.getObject()));
 if (true) break;

case 12:
//C
this.state = 13;
;
 //BA.debugLineNum = 158;BA.debugLine="Return";
if (true) return ;
 if (true) break;

case 13:
//C
this.state = 14;
;
 //BA.debugLineNum = 163;BA.debugLine="lblFinalizado1.Text = \"Chequeando internet...\"";
parent.mostCurrent._lblfinalizado1.setText(BA.ObjectToCharSequence("Chequeando internet..."));
 //BA.debugLineNum = 164;BA.debugLine="lblFinalizado1.TextSize = 16";
parent.mostCurrent._lblfinalizado1.setTextSize((float) (16));
 //BA.debugLineNum = 165;BA.debugLine="lblYaTienesTodo.Visible = False";
parent.mostCurrent._lblyatienestodo.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 167;BA.debugLine="ProgressBar1.Progress = 0";
parent.mostCurrent._progressbar1.setProgress((int) (0));
 //BA.debugLineNum = 168;BA.debugLine="ProgressBar2.Progress = 0";
parent.mostCurrent._progressbar2.setProgress((int) (0));
 //BA.debugLineNum = 169;BA.debugLine="ProgressBar3.Progress = 0";
parent.mostCurrent._progressbar3.setProgress((int) (0));
 //BA.debugLineNum = 171;BA.debugLine="ProgressBar1.Left = 5000dip";
parent.mostCurrent._progressbar1.setLeft(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5000)));
 //BA.debugLineNum = 172;BA.debugLine="ProgressBar2.Left = 5000dip";
parent.mostCurrent._progressbar2.setLeft(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5000)));
 //BA.debugLineNum = 173;BA.debugLine="ProgressBar3.Left = 5000dip";
parent.mostCurrent._progressbar3.setLeft(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5000)));
 //BA.debugLineNum = 176;BA.debugLine="CheckInternet";
_checkinternet();
 //BA.debugLineNum = 177;BA.debugLine="Wait For(CheckInternet) Complete (Result As Int)";
anywheresoftware.b4a.keywords.Common.WaitFor("complete", processBA, this, (Object)(_checkinternet()));
this.state = 28;
return;
case 28:
//C
this.state = 14;
_result = (Integer) result[0];
;
 //BA.debugLineNum = 178;BA.debugLine="If Result = 0 Then";
if (true) break;

case 14:
//if
this.state = 17;
if (_result==0) { 
this.state = 16;
}if (true) break;

case 16:
//C
this.state = 17;
 //BA.debugLineNum = 179;BA.debugLine="MsgboxAsync(\"No hay conexión a internet, prueba";
anywheresoftware.b4a.keywords.Common.MsgboxAsync(BA.ObjectToCharSequence("No hay conexión a internet, prueba cuando estés conectado!"),BA.ObjectToCharSequence("No hay internet"),processBA);
 //BA.debugLineNum = 181;BA.debugLine="Activity.Finish";
parent.mostCurrent._activity.Finish();
 //BA.debugLineNum = 182;BA.debugLine="Activity.RemoveAllViews";
parent.mostCurrent._activity.RemoveAllViews();
 if (true) break;
;
 //BA.debugLineNum = 185;BA.debugLine="If Main.modooffline = True Then";

case 17:
//if
this.state = 24;
if (parent.mostCurrent._main._modooffline /*boolean*/ ==anywheresoftware.b4a.keywords.Common.True) { 
this.state = 19;
}if (true) break;

case 19:
//C
this.state = 20;
 //BA.debugLineNum = 186;BA.debugLine="If Main.lang = \"es\" Then";
if (true) break;

case 20:
//if
this.state = 23;
if ((parent.mostCurrent._main._lang /*String*/ ).equals("es")) { 
this.state = 22;
}if (true) break;

case 22:
//C
this.state = 23;
 //BA.debugLineNum = 187;BA.debugLine="ToastMessageShow(\"Estas en modo sin conexión, s";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Estas en modo sin conexión, se guardarán los datos para que los envíes después"),anywheresoftware.b4a.keywords.Common.False);
 if (true) break;

case 23:
//C
this.state = 24;
;
 //BA.debugLineNum = 189;BA.debugLine="Activity.Finish";
parent.mostCurrent._activity.Finish();
 //BA.debugLineNum = 190;BA.debugLine="Activity.RemoveAllViews";
parent.mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 191;BA.debugLine="Return";
if (true) return ;
 if (true) break;
;
 //BA.debugLineNum = 196;BA.debugLine="If Main.lang = \"es\" Then";

case 24:
//if
this.state = 27;
if ((parent.mostCurrent._main._lang /*String*/ ).equals("es")) { 
this.state = 26;
}if (true) break;

case 26:
//C
this.state = 27;
 //BA.debugLineNum = 197;BA.debugLine="ProgressDialogShow2(\"Enviando proyecto, esto tar";
anywheresoftware.b4a.keywords.Common.ProgressDialogShow2(mostCurrent.activityBA,BA.ObjectToCharSequence("Enviando proyecto, esto tardará unos minutos"),anywheresoftware.b4a.keywords.Common.False);
 if (true) break;

case 27:
//C
this.state = -1;
;
 //BA.debugLineNum = 201;BA.debugLine="CheckInternet";
_checkinternet();
 //BA.debugLineNum = 202;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public static void  _complete(int _result) throws Exception{
}
public static String  _checkinternet() throws Exception{
ilpla.appear.downloadservice._downloaddata _dd = null;
 //BA.debugLineNum = 206;BA.debugLine="Sub CheckInternet";
 //BA.debugLineNum = 207;BA.debugLine="Dim dd As DownloadData";
_dd = new ilpla.appear.downloadservice._downloaddata();
 //BA.debugLineNum = 208;BA.debugLine="dd.url = Main.serverPath &  \"/\" & Main.serverConn";
_dd.url /*String*/  = mostCurrent._main._serverpath /*String*/ +"/"+mostCurrent._main._serverconnectionfolder /*String*/ +"/connecttest.php";
 //BA.debugLineNum = 209;BA.debugLine="dd.EventName = \"TestInternet\"";
_dd.EventName /*String*/  = "TestInternet";
 //BA.debugLineNum = 210;BA.debugLine="dd.Target = Me";
_dd.Target /*Object*/  = reporte_envio.getObject();
 //BA.debugLineNum = 211;BA.debugLine="CallSubDelayed2(DownloadService, \"StartDownload\",";
anywheresoftware.b4a.keywords.Common.CallSubDelayed2(processBA,(Object)(mostCurrent._downloadservice.getObject()),"StartDownload",(Object)(_dd));
 //BA.debugLineNum = 212;BA.debugLine="End Sub";
return "";
}
public static String  _enviardatos_complete(ilpla.appear.httpjob _job) throws Exception{
String _ret = "";
String _act = "";
anywheresoftware.b4a.objects.collections.JSONParser _parser = null;
anywheresoftware.b4a.objects.collections.Map _nd = null;
String _serverid = "";
anywheresoftware.b4a.objects.collections.Map _map1 = null;
 //BA.debugLineNum = 382;BA.debugLine="Sub EnviarDatos_Complete(Job As HttpJob)";
 //BA.debugLineNum = 383;BA.debugLine="Log(\"Datos enviados : \" & Job.Success)";
anywheresoftware.b4a.keywords.Common.LogImpl("225952257","Datos enviados : "+BA.ObjectToString(_job._success /*boolean*/ ),0);
 //BA.debugLineNum = 384;BA.debugLine="If Job.Success = True Then";
if (_job._success /*boolean*/ ==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 385;BA.debugLine="Dim ret As String";
_ret = "";
 //BA.debugLineNum = 386;BA.debugLine="Dim act As String";
_act = "";
 //BA.debugLineNum = 387;BA.debugLine="ret = Job.GetString";
_ret = _job._getstring /*String*/ ();
 //BA.debugLineNum = 388;BA.debugLine="Dim parser As JSONParser";
_parser = new anywheresoftware.b4a.objects.collections.JSONParser();
 //BA.debugLineNum = 389;BA.debugLine="parser.Initialize(ret)";
_parser.Initialize(_ret);
 //BA.debugLineNum = 390;BA.debugLine="act = parser.NextValue";
_act = BA.ObjectToString(_parser.NextValue());
 //BA.debugLineNum = 391;BA.debugLine="If act = \"Not Found\" Then";
if ((_act).equals("Not Found")) { 
 //BA.debugLineNum = 392;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 393;BA.debugLine="ToastMessageShow(\"Error en la carga de marcado";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Error en la carga de marcadores"),anywheresoftware.b4a.keywords.Common.True);
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 395;BA.debugLine="ToastMessageShow(\"Error loading markers\", True";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Error loading markers"),anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 397;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 }else if((_act).equals("MarcadorAgregado")) { 
 //BA.debugLineNum = 400;BA.debugLine="Dim nd As Map";
_nd = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 401;BA.debugLine="nd = parser.NextObject";
_nd = _parser.NextObject();
 //BA.debugLineNum = 402;BA.debugLine="Dim serverID As String";
_serverid = "";
 //BA.debugLineNum = 403;BA.debugLine="serverID = nd.Get(\"serverId\")";
_serverid = BA.ObjectToString(_nd.Get((Object)("serverId")));
 //BA.debugLineNum = 406;BA.debugLine="Dim Map1 As Map";
_map1 = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 407;BA.debugLine="Map1.Initialize";
_map1.Initialize();
 //BA.debugLineNum = 408;BA.debugLine="Map1.Put(\"Id\", Main.currentproject)";
_map1.Put((Object)("Id"),(Object)(mostCurrent._main._currentproject /*String*/ ));
 //BA.debugLineNum = 409;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"evals\", \"e";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals","evalsent",(Object)("si"),_map1);
 //BA.debugLineNum = 410;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"evals\", \"s";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals","serverId",(Object)(_serverid),_map1);
 //BA.debugLineNum = 411;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 412;BA.debugLine="ToastMessageShow(\"Datos enviados, enviando fot";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Datos enviados, enviando fotos"),anywheresoftware.b4a.keywords.Common.False);
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 414;BA.debugLine="ToastMessageShow(\"Report sent, sending photos\"";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Report sent, sending photos"),anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 418;BA.debugLine="EnviarFotos";
_enviarfotos();
 };
 }else {
 //BA.debugLineNum = 421;BA.debugLine="Log(\"envio datos not ok\")";
anywheresoftware.b4a.keywords.Common.LogImpl("225952295","envio datos not ok",0);
 //BA.debugLineNum = 422;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 423;BA.debugLine="MsgboxAsync(\"Al parecer hay un problema en nues";
anywheresoftware.b4a.keywords.Common.MsgboxAsync(BA.ObjectToCharSequence("Al parecer hay un problema en nuestros servidores, lo solucionaremos pronto!"),BA.ObjectToCharSequence("Mala mía"),processBA);
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 425;BA.debugLine="MsgboxAsync(\"There seems to be a problem with o";
anywheresoftware.b4a.keywords.Common.MsgboxAsync(BA.ObjectToCharSequence("There seems to be a problem with our servers, we will solve it soon!"),BA.ObjectToCharSequence("My bad"),processBA);
 };
 };
 //BA.debugLineNum = 429;BA.debugLine="Job.Release";
_job._release /*String*/ ();
 //BA.debugLineNum = 430;BA.debugLine="End Sub";
return "";
}
public static String  _enviarfotos() throws Exception{
String _usr = "";
String _pss = "";
 //BA.debugLineNum = 436;BA.debugLine="Sub EnviarFotos";
 //BA.debugLineNum = 439;BA.debugLine="If foto1 <> \"null\" Then";
if ((mostCurrent._foto1).equals("null") == false) { 
 //BA.debugLineNum = 440;BA.debugLine="totalFotos = totalFotos + 1";
_totalfotos = (int) (_totalfotos+1);
 //BA.debugLineNum = 441;BA.debugLine="Label1.Text = \"Foto 1\"";
mostCurrent._label1.setText(BA.ObjectToCharSequence("Foto 1"));
 //BA.debugLineNum = 442;BA.debugLine="Label1.Visible= True";
mostCurrent._label1.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 443;BA.debugLine="ProgressBar1.Visible = False";
mostCurrent._progressbar1.setVisible(anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 445;BA.debugLine="If foto2 <>  \"null\" Then";
if ((mostCurrent._foto2).equals("null") == false) { 
 //BA.debugLineNum = 446;BA.debugLine="totalFotos = totalFotos + 1";
_totalfotos = (int) (_totalfotos+1);
 //BA.debugLineNum = 447;BA.debugLine="Label2.Text = \"Foto 2\"";
mostCurrent._label2.setText(BA.ObjectToCharSequence("Foto 2"));
 //BA.debugLineNum = 448;BA.debugLine="Label2.Visible= True";
mostCurrent._label2.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 449;BA.debugLine="ProgressBar2.Visible = False";
mostCurrent._progressbar2.setVisible(anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 451;BA.debugLine="If foto3 <>  \"null\" Then";
if ((mostCurrent._foto3).equals("null") == false) { 
 //BA.debugLineNum = 452;BA.debugLine="totalFotos = totalFotos + 1";
_totalfotos = (int) (_totalfotos+1);
 //BA.debugLineNum = 453;BA.debugLine="Label3.Text = \"Foto 3\"";
mostCurrent._label3.setText(BA.ObjectToCharSequence("Foto 3"));
 //BA.debugLineNum = 454;BA.debugLine="Label3.Visible= True";
mostCurrent._label3.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 455;BA.debugLine="ProgressBar3.Visible = False";
mostCurrent._progressbar3.setVisible(anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 457;BA.debugLine="If foto4 <>  \"null\" Then";
if ((mostCurrent._foto4).equals("null") == false) { 
 //BA.debugLineNum = 458;BA.debugLine="totalFotos = totalFotos + 1";
_totalfotos = (int) (_totalfotos+1);
 //BA.debugLineNum = 459;BA.debugLine="Label4.Text = \"Foto 4\"";
mostCurrent._label4.setText(BA.ObjectToCharSequence("Foto 4"));
 //BA.debugLineNum = 460;BA.debugLine="Label4.Visible= True";
mostCurrent._label4.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 461;BA.debugLine="ProgressBar4.Visible = False";
mostCurrent._progressbar4.setVisible(anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 463;BA.debugLine="If foto5 <>  \"null\" Then";
if ((mostCurrent._foto5).equals("null") == false) { 
 //BA.debugLineNum = 464;BA.debugLine="totalFotos = totalFotos + 1";
_totalfotos = (int) (_totalfotos+1);
 //BA.debugLineNum = 465;BA.debugLine="Label5.Text = \"Foto 5\"";
mostCurrent._label5.setText(BA.ObjectToCharSequence("Foto 5"));
 //BA.debugLineNum = 466;BA.debugLine="Label5.Visible= True";
mostCurrent._label5.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 467;BA.debugLine="ProgressBar5.Visible = False";
mostCurrent._progressbar5.setVisible(anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 470;BA.debugLine="lblFinalizado1.Text = \"Enviando fotos...\"";
mostCurrent._lblfinalizado1.setText(BA.ObjectToCharSequence("Enviando fotos..."));
 //BA.debugLineNum = 471;BA.debugLine="lblFinalizado1.TextSize = 16";
mostCurrent._lblfinalizado1.setTextSize((float) (16));
 //BA.debugLineNum = 474;BA.debugLine="TimerEnvio.Initialize(\"TimerEnvio\", 1000)";
_timerenvio.Initialize(processBA,"TimerEnvio",(long) (1000));
 //BA.debugLineNum = 475;BA.debugLine="TimerEnvio.Enabled = True";
_timerenvio.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 477;BA.debugLine="Dim usr As String";
_usr = "";
 //BA.debugLineNum = 478;BA.debugLine="Dim pss As String";
_pss = "";
 //BA.debugLineNum = 479;BA.debugLine="usr = \"\"";
_usr = "";
 //BA.debugLineNum = 480;BA.debugLine="pss = \"\"";
_pss = "";
 //BA.debugLineNum = 482;BA.debugLine="Up1.B4A_log=True";
_up1.B4A_log = anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 485;BA.debugLine="Up1.Initialize(\"Up1\")";
_up1.Initialize(processBA,"Up1");
 //BA.debugLineNum = 490;BA.debugLine="If File.Exists(File.DirRootExternal & \"/PreserVam";
if (anywheresoftware.b4a.keywords.Common.File.Exists(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/PreserVamos/",mostCurrent._foto1+".jpg") && _foto1sent==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 491;BA.debugLine="Log(\"Enviando foto 1 \")";
anywheresoftware.b4a.keywords.Common.LogImpl("226017847","Enviando foto 1 ",0);
 //BA.debugLineNum = 493;BA.debugLine="foto1ProgressBar.Visible = True";
mostCurrent._foto1progressbar._setvisible /*boolean*/ (anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 495;BA.debugLine="Up1.doFileUpload(ProgressBar1,Null,File.DirRootE";
_up1.doFileUpload(processBA,(android.widget.ProgressBar)(mostCurrent._progressbar1.getObject()),(android.widget.TextView)(anywheresoftware.b4a.keywords.Common.Null),anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/PreserVamos/"+mostCurrent._foto1+".jpg",mostCurrent._main._serverpath /*String*/ +"/"+mostCurrent._main._serverconnectionfolder /*String*/ +"/upload_file.php?usr="+_usr+"&pss="+_pss);
 //BA.debugLineNum = 496;BA.debugLine="ProgressBar1.Visible = False";
mostCurrent._progressbar1.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 498;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 499;BA.debugLine="lblFinalizado1.Text = \"Enviando foto 1...\"";
mostCurrent._lblfinalizado1.setText(BA.ObjectToCharSequence("Enviando foto 1..."));
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 501;BA.debugLine="lblFinalizado1.Text = \"Uploading foto 1...\"";
mostCurrent._lblfinalizado1.setText(BA.ObjectToCharSequence("Uploading foto 1..."));
 };
 }else {
 //BA.debugLineNum = 504;BA.debugLine="Log(\"no foto 1\")";
anywheresoftware.b4a.keywords.Common.LogImpl("226017860","no foto 1",0);
 };
 //BA.debugLineNum = 507;BA.debugLine="End Sub";
return "";
}
public static String  _enviarpuntos(String _tipo,String _cantidadfotos) throws Exception{
ilpla.appear.downloadservice._downloaddata _dd = null;
 //BA.debugLineNum = 663;BA.debugLine="Sub EnviarPuntos(tipo As String, cantidadfotos As";
 //BA.debugLineNum = 664;BA.debugLine="Log(Main.puntostotales)";
anywheresoftware.b4a.keywords.Common.LogImpl("226345473",mostCurrent._main._puntostotales /*String*/ ,0);
 //BA.debugLineNum = 665;BA.debugLine="PuntosFotos = Main.puntosnumfotos";
mostCurrent._puntosfotos = mostCurrent._main._puntosnumfotos /*String*/ ;
 //BA.debugLineNum = 666;BA.debugLine="PuntosEvals = Main.puntosnumevals";
mostCurrent._puntosevals = mostCurrent._main._puntosnumevals /*String*/ ;
 //BA.debugLineNum = 667;BA.debugLine="PuntosTotal = Main.puntostotales";
mostCurrent._puntostotal = mostCurrent._main._puntostotales /*String*/ ;
 //BA.debugLineNum = 668;BA.debugLine="numriollanura = Main.numriollanura";
mostCurrent._numriollanura = mostCurrent._main._numriollanura /*String*/ ;
 //BA.debugLineNum = 669;BA.debugLine="numriomontana = Main.numriomontana";
mostCurrent._numriomontana = mostCurrent._main._numriomontana /*String*/ ;
 //BA.debugLineNum = 670;BA.debugLine="numlaguna = Main.numlaguna";
mostCurrent._numlaguna = mostCurrent._main._numlaguna /*String*/ ;
 //BA.debugLineNum = 671;BA.debugLine="numestuario = Main.numestuario";
mostCurrent._numestuario = mostCurrent._main._numestuario /*String*/ ;
 //BA.debugLineNum = 674;BA.debugLine="If tipo = \"llanura\" Then";
if ((_tipo).equals("llanura")) { 
 //BA.debugLineNum = 675;BA.debugLine="numriollanura = numriollanura + 1";
mostCurrent._numriollanura = BA.NumberToString((double)(Double.parseDouble(mostCurrent._numriollanura))+1);
 //BA.debugLineNum = 676;BA.debugLine="Main.numriollanura = numriollanura";
mostCurrent._main._numriollanura /*String*/  = mostCurrent._numriollanura;
 }else if((_tipo).equals("montana")) { 
 //BA.debugLineNum = 678;BA.debugLine="numriomontana = numriomontana + 1";
mostCurrent._numriomontana = BA.NumberToString((double)(Double.parseDouble(mostCurrent._numriomontana))+1);
 //BA.debugLineNum = 679;BA.debugLine="Main.numriomontana = numriomontana";
mostCurrent._main._numriomontana /*String*/  = mostCurrent._numriomontana;
 }else if((_tipo).equals("laguna")) { 
 //BA.debugLineNum = 681;BA.debugLine="numlaguna = numlaguna + 1";
mostCurrent._numlaguna = BA.NumberToString((double)(Double.parseDouble(mostCurrent._numlaguna))+1);
 //BA.debugLineNum = 682;BA.debugLine="Main.numlaguna = numlaguna";
mostCurrent._main._numlaguna /*String*/  = mostCurrent._numlaguna;
 }else if((_tipo).equals("estuario")) { 
 //BA.debugLineNum = 684;BA.debugLine="numestuario = numestuario + 1";
mostCurrent._numestuario = BA.NumberToString((double)(Double.parseDouble(mostCurrent._numestuario))+1);
 //BA.debugLineNum = 685;BA.debugLine="Main.numestuario = numestuario";
mostCurrent._main._numestuario /*String*/  = mostCurrent._numestuario;
 };
 //BA.debugLineNum = 689;BA.debugLine="PuntosTotal = PuntosTotal + 60 + (10 * cantidadfo";
mostCurrent._puntostotal = BA.NumberToString((double)(Double.parseDouble(mostCurrent._puntostotal))+60+(10*(double)(Double.parseDouble(_cantidadfotos))));
 //BA.debugLineNum = 692;BA.debugLine="Dim dd As DownloadData";
_dd = new ilpla.appear.downloadservice._downloaddata();
 //BA.debugLineNum = 693;BA.debugLine="dd.url = Main.serverPath &  \"/\" & Main.serverConn";
_dd.url /*String*/  = mostCurrent._main._serverpath /*String*/ +"/"+mostCurrent._main._serverconnectionfolder /*String*/ +"/recpuntos.php?"+"UserID="+mostCurrent._main._struserid /*String*/ +"&"+"PuntosFotos="+mostCurrent._puntosfotos+"&"+"PuntosEvals="+mostCurrent._puntosevals+"&"+"PuntosTotal="+mostCurrent._puntostotal+"&"+"numriollanura="+mostCurrent._main._numriollanura /*String*/ +"&"+"numriomontana="+mostCurrent._main._numriomontana /*String*/ +"&"+"numlaguna="+mostCurrent._main._numlaguna /*String*/ +"&"+"numestuario="+mostCurrent._main._numestuario /*String*/ ;
 //BA.debugLineNum = 702;BA.debugLine="dd.EventName = \"envioPuntos\"";
_dd.EventName /*String*/  = "envioPuntos";
 //BA.debugLineNum = 703;BA.debugLine="dd.Target = Me";
_dd.Target /*Object*/  = reporte_envio.getObject();
 //BA.debugLineNum = 704;BA.debugLine="CallSubDelayed2(DownloadService, \"StartDownload\",";
anywheresoftware.b4a.keywords.Common.CallSubDelayed2(processBA,(Object)(mostCurrent._downloadservice.getObject()),"StartDownload",(Object)(_dd));
 //BA.debugLineNum = 705;BA.debugLine="End Sub";
return "";
}
public static String  _enviodatos(String _proyectonumero) throws Exception{
String _username = "";
String _dateandtime = "";
String _nombresitio = "";
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
String _ind_pvm_1 = "";
String _ind_pvm_2 = "";
String _ind_pvm_3 = "";
String _ind_pvm_4 = "";
String _ind_pvm_5 = "";
String _ind_pvm_6 = "";
String _ind_pvm_7 = "";
String _ind_pvm_8 = "";
String _ind_pvm_9 = "";
String _ind_pvm_10 = "";
String _ind_pvm_11 = "";
String _ind_pvm_12 = "";
String _notas = "";
String _bingo = "";
String _terminado = "";
String _privado = "";
String _estadovalidacion = "";
String _deviceid = "";
anywheresoftware.b4a.objects.collections.Map _datosmap = null;
ilpla.appear.downloadservice._downloaddata _dd = null;
 //BA.debugLineNum = 237;BA.debugLine="Sub EnvioDatos(proyectonumero As String)";
 //BA.debugLineNum = 239;BA.debugLine="proyectoIDenviar = proyectonumero";
mostCurrent._proyectoidenviar = _proyectonumero;
 //BA.debugLineNum = 241;BA.debugLine="Dim username,dateandtime,nombresitio,tiporio,lat,";
_username = "";
_dateandtime = "";
_nombresitio = "";
mostCurrent._tiporio = "";
_lat = "";
_lng = "";
_gpsdetect = "";
_wifidetect = "";
_mapadetect = "";
 //BA.debugLineNum = 242;BA.debugLine="Dim valorcalidad, valorNS, valorind1,valorind2,va";
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
 //BA.debugLineNum = 244;BA.debugLine="Dim ind_pvm_1, ind_pvm_2, ind_pvm_3, ind_pvm_4, i";
_ind_pvm_1 = "";
_ind_pvm_2 = "";
_ind_pvm_3 = "";
_ind_pvm_4 = "";
_ind_pvm_5 = "";
_ind_pvm_6 = "";
_ind_pvm_7 = "";
_ind_pvm_8 = "";
_ind_pvm_9 = "";
_ind_pvm_10 = "";
_ind_pvm_11 = "";
_ind_pvm_12 = "";
 //BA.debugLineNum = 246;BA.debugLine="Dim notas,bingo As String";
_notas = "";
_bingo = "";
 //BA.debugLineNum = 247;BA.debugLine="Dim terminado, privado,estadovalidacion, deviceID";
_terminado = "";
_privado = "";
_estadovalidacion = "";
_deviceid = "";
 //BA.debugLineNum = 249;BA.debugLine="Dim datosMap As Map";
_datosmap = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 250;BA.debugLine="datosMap.Initialize";
_datosmap.Initialize();
 //BA.debugLineNum = 251;BA.debugLine="datosMap = DBUtils.ExecuteMap(Starter.sqlDB, \"SEL";
_datosmap = mostCurrent._dbutils._executemap /*anywheresoftware.b4a.objects.collections.Map*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"SELECT * FROM evals WHERE Id=?",new String[]{_proyectonumero});
 //BA.debugLineNum = 253;BA.debugLine="If datosMap = Null Or datosMap.IsInitialized = Fa";
if (_datosmap== null || _datosmap.IsInitialized()==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 254;BA.debugLine="ToastMessageShow(\"Error cargando el análisis\", F";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Error cargando el análisis"),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 255;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 //BA.debugLineNum = 256;BA.debugLine="Return";
if (true) return "";
 }else {
 //BA.debugLineNum = 258;BA.debugLine="username = datosMap.Get(\"usuario\")";
_username = BA.ObjectToString(_datosmap.Get((Object)("usuario")));
 //BA.debugLineNum = 259;BA.debugLine="dateandtime = datosMap.Get(\"georeferenceddate\")";
_dateandtime = BA.ObjectToString(_datosmap.Get((Object)("georeferenceddate")));
 //BA.debugLineNum = 260;BA.debugLine="nombresitio = datosMap.Get(\"nombresitio\")";
_nombresitio = BA.ObjectToString(_datosmap.Get((Object)("nombresitio")));
 //BA.debugLineNum = 261;BA.debugLine="tiporio = datosMap.Get(\"tiporio\")";
mostCurrent._tiporio = BA.ObjectToString(_datosmap.Get((Object)("tiporio")));
 //BA.debugLineNum = 262;BA.debugLine="lat = datosMap.Get(\"decimallatitude\")";
_lat = BA.ObjectToString(_datosmap.Get((Object)("decimallatitude")));
 //BA.debugLineNum = 263;BA.debugLine="lng = datosMap.Get(\"decimallongitude\")";
_lng = BA.ObjectToString(_datosmap.Get((Object)("decimallongitude")));
 //BA.debugLineNum = 264;BA.debugLine="gpsdetect = datosMap.Get(\"gpsdetect\")";
_gpsdetect = BA.ObjectToString(_datosmap.Get((Object)("gpsdetect")));
 //BA.debugLineNum = 265;BA.debugLine="wifidetect = datosMap.Get(\"wifidetect\")";
_wifidetect = BA.ObjectToString(_datosmap.Get((Object)("wifidetect")));
 //BA.debugLineNum = 266;BA.debugLine="mapadetect = datosMap.Get(\"mapadetect\")";
_mapadetect = BA.ObjectToString(_datosmap.Get((Object)("mapadetect")));
 //BA.debugLineNum = 267;BA.debugLine="valorcalidad = datosMap.Get(\"valorcalidad\")";
_valorcalidad = BA.ObjectToString(_datosmap.Get((Object)("valorcalidad")));
 //BA.debugLineNum = 268;BA.debugLine="valorNS = datosMap.Get(\"valorind20\")";
_valorns = BA.ObjectToString(_datosmap.Get((Object)("valorind20")));
 //BA.debugLineNum = 269;BA.debugLine="valorind1 = datosMap.Get(\"valorind1\")";
_valorind1 = BA.ObjectToString(_datosmap.Get((Object)("valorind1")));
 //BA.debugLineNum = 270;BA.debugLine="valorind2 = datosMap.Get(\"valorind2\")";
_valorind2 = BA.ObjectToString(_datosmap.Get((Object)("valorind2")));
 //BA.debugLineNum = 271;BA.debugLine="valorind3 = datosMap.Get(\"valorind3\")";
_valorind3 = BA.ObjectToString(_datosmap.Get((Object)("valorind3")));
 //BA.debugLineNum = 272;BA.debugLine="valorind4 = datosMap.Get(\"valorind4\")";
_valorind4 = BA.ObjectToString(_datosmap.Get((Object)("valorind4")));
 //BA.debugLineNum = 273;BA.debugLine="valorind5 = datosMap.Get(\"valorind5\")";
_valorind5 = BA.ObjectToString(_datosmap.Get((Object)("valorind5")));
 //BA.debugLineNum = 274;BA.debugLine="valorind6 = datosMap.Get(\"valorind6\")";
_valorind6 = BA.ObjectToString(_datosmap.Get((Object)("valorind6")));
 //BA.debugLineNum = 275;BA.debugLine="valorind7 = datosMap.Get(\"valorind7\")";
_valorind7 = BA.ObjectToString(_datosmap.Get((Object)("valorind7")));
 //BA.debugLineNum = 276;BA.debugLine="valorind8 = datosMap.Get(\"valorind8\")";
_valorind8 = BA.ObjectToString(_datosmap.Get((Object)("valorind8")));
 //BA.debugLineNum = 277;BA.debugLine="valorind9 = datosMap.Get(\"valorind9\")";
_valorind9 = BA.ObjectToString(_datosmap.Get((Object)("valorind9")));
 //BA.debugLineNum = 278;BA.debugLine="valorind10 = datosMap.Get(\"valorind10\")";
_valorind10 = BA.ObjectToString(_datosmap.Get((Object)("valorind10")));
 //BA.debugLineNum = 279;BA.debugLine="valorind11 = datosMap.Get(\"valorind11\")";
_valorind11 = BA.ObjectToString(_datosmap.Get((Object)("valorind11")));
 //BA.debugLineNum = 280;BA.debugLine="valorind12 = datosMap.Get(\"valorind12\")";
_valorind12 = BA.ObjectToString(_datosmap.Get((Object)("valorind12")));
 //BA.debugLineNum = 281;BA.debugLine="valorind13 = datosMap.Get(\"valorind13\")";
_valorind13 = BA.ObjectToString(_datosmap.Get((Object)("valorind13")));
 //BA.debugLineNum = 282;BA.debugLine="valorind14 = datosMap.Get(\"valorind14\")";
_valorind14 = BA.ObjectToString(_datosmap.Get((Object)("valorind14")));
 //BA.debugLineNum = 283;BA.debugLine="valorind15 = datosMap.Get(\"valorind15\")";
_valorind15 = BA.ObjectToString(_datosmap.Get((Object)("valorind15")));
 //BA.debugLineNum = 284;BA.debugLine="valorind16 = datosMap.Get(\"valorind16\")";
_valorind16 = BA.ObjectToString(_datosmap.Get((Object)("valorind16")));
 //BA.debugLineNum = 285;BA.debugLine="valorind17 = datosMap.Get(\"valorind17\")";
_valorind17 = BA.ObjectToString(_datosmap.Get((Object)("valorind17")));
 //BA.debugLineNum = 286;BA.debugLine="valorind18 = datosMap.Get(\"valorind18\")";
_valorind18 = BA.ObjectToString(_datosmap.Get((Object)("valorind18")));
 //BA.debugLineNum = 287;BA.debugLine="valorind19 = datosMap.Get(\"valorind19\")";
_valorind19 = BA.ObjectToString(_datosmap.Get((Object)("valorind19")));
 //BA.debugLineNum = 288;BA.debugLine="ind_pvm_1 = datosMap.Get(\"ind_pvm_1\")";
_ind_pvm_1 = BA.ObjectToString(_datosmap.Get((Object)("ind_pvm_1")));
 //BA.debugLineNum = 289;BA.debugLine="ind_pvm_2 = datosMap.Get(\"ind_pvm_2\")";
_ind_pvm_2 = BA.ObjectToString(_datosmap.Get((Object)("ind_pvm_2")));
 //BA.debugLineNum = 290;BA.debugLine="ind_pvm_3 = datosMap.Get(\"ind_pvm_3\")";
_ind_pvm_3 = BA.ObjectToString(_datosmap.Get((Object)("ind_pvm_3")));
 //BA.debugLineNum = 291;BA.debugLine="ind_pvm_4 = datosMap.Get(\"ind_pvm_4\")";
_ind_pvm_4 = BA.ObjectToString(_datosmap.Get((Object)("ind_pvm_4")));
 //BA.debugLineNum = 292;BA.debugLine="ind_pvm_5 = datosMap.Get(\"ind_pvm_5\")";
_ind_pvm_5 = BA.ObjectToString(_datosmap.Get((Object)("ind_pvm_5")));
 //BA.debugLineNum = 293;BA.debugLine="ind_pvm_6 = datosMap.Get(\"ind_pvm_6\")";
_ind_pvm_6 = BA.ObjectToString(_datosmap.Get((Object)("ind_pvm_6")));
 //BA.debugLineNum = 294;BA.debugLine="ind_pvm_7 = datosMap.Get(\"ind_pvm_7\")";
_ind_pvm_7 = BA.ObjectToString(_datosmap.Get((Object)("ind_pvm_7")));
 //BA.debugLineNum = 295;BA.debugLine="ind_pvm_8 = datosMap.Get(\"ind_pvm_8\")";
_ind_pvm_8 = BA.ObjectToString(_datosmap.Get((Object)("ind_pvm_8")));
 //BA.debugLineNum = 296;BA.debugLine="ind_pvm_9 = datosMap.Get(\"ind_pvm_9\")";
_ind_pvm_9 = BA.ObjectToString(_datosmap.Get((Object)("ind_pvm_9")));
 //BA.debugLineNum = 297;BA.debugLine="ind_pvm_10 = datosMap.Get(\"ind_pvm_10\")";
_ind_pvm_10 = BA.ObjectToString(_datosmap.Get((Object)("ind_pvm_10")));
 //BA.debugLineNum = 298;BA.debugLine="ind_pvm_11 = datosMap.Get(\"ind_pvm_11\")";
_ind_pvm_11 = BA.ObjectToString(_datosmap.Get((Object)("ind_pvm_11")));
 //BA.debugLineNum = 299;BA.debugLine="ind_pvm_12 = datosMap.Get(\"ind_pvm_12\")";
_ind_pvm_12 = BA.ObjectToString(_datosmap.Get((Object)("ind_pvm_12")));
 //BA.debugLineNum = 301;BA.debugLine="notas = datosMap.Get(\"notas\")";
_notas = BA.ObjectToString(_datosmap.Get((Object)("notas")));
 //BA.debugLineNum = 302;BA.debugLine="bingo = datosMap.Get(\"bingo\")";
_bingo = BA.ObjectToString(_datosmap.Get((Object)("bingo")));
 //BA.debugLineNum = 303;BA.debugLine="foto1 = datosMap.Get(\"foto1\")";
mostCurrent._foto1 = BA.ObjectToString(_datosmap.Get((Object)("foto1")));
 //BA.debugLineNum = 304;BA.debugLine="foto2 = datosMap.Get(\"foto2\")";
mostCurrent._foto2 = BA.ObjectToString(_datosmap.Get((Object)("foto2")));
 //BA.debugLineNum = 305;BA.debugLine="foto3 = datosMap.Get(\"foto3\")";
mostCurrent._foto3 = BA.ObjectToString(_datosmap.Get((Object)("foto3")));
 //BA.debugLineNum = 306;BA.debugLine="foto4 = datosMap.Get(\"foto4\")";
mostCurrent._foto4 = BA.ObjectToString(_datosmap.Get((Object)("foto4")));
 //BA.debugLineNum = 307;BA.debugLine="foto5 = datosMap.Get(\"foto5\")";
mostCurrent._foto5 = BA.ObjectToString(_datosmap.Get((Object)("foto5")));
 //BA.debugLineNum = 308;BA.debugLine="terminado = datosMap.Get(\"terminado\")";
_terminado = BA.ObjectToString(_datosmap.Get((Object)("terminado")));
 //BA.debugLineNum = 309;BA.debugLine="privado = datosMap.Get(\"privado\")";
_privado = BA.ObjectToString(_datosmap.Get((Object)("privado")));
 //BA.debugLineNum = 310;BA.debugLine="If privado = Null Or privado = \"null\" Then";
if (_privado== null || (_privado).equals("null")) { 
 //BA.debugLineNum = 311;BA.debugLine="privado = \"no\"";
_privado = "no";
 };
 //BA.debugLineNum = 313;BA.debugLine="estadovalidacion = datosMap.Get(\"estadovalidacio";
_estadovalidacion = BA.ObjectToString(_datosmap.Get((Object)("estadovalidacion")));
 //BA.debugLineNum = 314;BA.debugLine="If estadovalidacion = \"null\" Then";
if ((_estadovalidacion).equals("null")) { 
 //BA.debugLineNum = 315;BA.debugLine="estadovalidacion = \"No Verificado\"";
_estadovalidacion = "No Verificado";
 };
 //BA.debugLineNum = 317;BA.debugLine="deviceID = datosMap.Get(\"deviceID\")";
_deviceid = BA.ObjectToString(_datosmap.Get((Object)("deviceID")));
 //BA.debugLineNum = 318;BA.debugLine="If deviceID = Null Or deviceID = \"\" Or deviceID";
if (_deviceid== null || (_deviceid).equals("") || (_deviceid).equals("null")) { 
 //BA.debugLineNum = 319;BA.debugLine="deviceID = utilidades.GetDeviceId";
_deviceid = mostCurrent._utilidades._getdeviceid /*String*/ (mostCurrent.activityBA);
 };
 };
 //BA.debugLineNum = 327;BA.debugLine="Log(\"Comienza envio de datos\")";
anywheresoftware.b4a.keywords.Common.LogImpl("225886810","Comienza envio de datos",0);
 //BA.debugLineNum = 328;BA.debugLine="Dim dd As DownloadData";
_dd = new ilpla.appear.downloadservice._downloaddata();
 //BA.debugLineNum = 329;BA.debugLine="dd.url = Main.serverPath & \"/\" & Main.serverConne";
_dd.url /*String*/  = mostCurrent._main._serverpath /*String*/ +"/"+mostCurrent._main._serverconnectionfolder /*String*/ +"/addpuntomapa.php?"+"username="+_username+"&"+"deviceID="+mostCurrent._main._deviceid /*String*/ +"&"+"dateandtime="+_dateandtime+"&"+"nombresitio="+_nombresitio+"&"+"lat="+_lat+"&"+"lng="+_lng+"&"+"tiporio="+mostCurrent._tiporio+"&"+"indice="+_valorcalidad+"&"+"precision="+_valorns+"&"+"valorind1="+_valorind1+"&"+"valorind2="+_valorind2+"&"+"valorind3="+_valorind3+"&"+"valorind4="+_valorind4+"&"+"valorind5="+_valorind5+"&"+"valorind6="+_valorind6+"&"+"valorind7="+_valorind7+"&"+"valorind8="+_valorind8+"&"+"valorind9="+_valorind9+"&"+"valorind10="+_valorind10+"&"+"valorind11="+_valorind11+"&"+"valorind12="+_valorind12+"&"+"valorind13="+_valorind13+"&"+"valorind14="+_valorind14+"&"+"valorind15="+_valorind15+"&"+"valorind16="+_valorind16+"&"+"valorind17="+_valorind17+"&"+"valorind18="+_valorind18+"&"+"valorind19="+_valorind19+"&"+"ind_pvm_1="+_ind_pvm_1+"&"+"ind_pvm_2="+_ind_pvm_2+"&"+"ind_pvm_3="+_ind_pvm_3+"&"+"ind_pvm_4="+_ind_pvm_4+"&"+"ind_pvm_5="+_ind_pvm_5+"&"+"ind_pvm_6="+_ind_pvm_6+"&"+"ind_pvm_7="+_ind_pvm_7+"&"+"ind_pvm_8="+_ind_pvm_8+"&"+"ind_pvm_9="+_ind_pvm_9+"&"+"ind_pvm_10="+_ind_pvm_10+"&"+"ind_pvm_11="+_ind_pvm_11+"&"+"ind_pvm_12="+_ind_pvm_12+"&"+"foto1path="+mostCurrent._foto1+"&"+"foto2path="+mostCurrent._foto2+"&"+"foto3path="+mostCurrent._foto3+"&"+"foto4path="+mostCurrent._foto4+"&"+"foto5path="+mostCurrent._foto5+"terminado="+_terminado+"&"+"verificado="+_estadovalidacion+"&"+"bingo="+_bingo+"&"+"notas="+_notas+"&"+"mapadetect="+_mapadetect+"&"+"wifidetect="+_wifidetect+"&"+"gpsdetect="+_gpsdetect;
 //BA.debugLineNum = 378;BA.debugLine="dd.EventName = \"EnviarDatos\"";
_dd.EventName /*String*/  = "EnviarDatos";
 //BA.debugLineNum = 379;BA.debugLine="dd.Target = Me";
_dd.Target /*Object*/  = reporte_envio.getObject();
 //BA.debugLineNum = 380;BA.debugLine="CallSubDelayed2(DownloadService, \"StartDownload\",";
anywheresoftware.b4a.keywords.Common.CallSubDelayed2(processBA,(Object)(mostCurrent._downloadservice.getObject()),"StartDownload",(Object)(_dd));
 //BA.debugLineNum = 381;BA.debugLine="End Sub";
return "";
}
public static String  _enviopuntos_complete(ilpla.appear.httpjob _job) throws Exception{
String _ret = "";
String _act = "";
anywheresoftware.b4a.objects.collections.JSONParser _parser = null;
anywheresoftware.b4a.objects.collections.Map _map1 = null;
 //BA.debugLineNum = 707;BA.debugLine="Sub envioPuntos_Complete(Job As HttpJob)";
 //BA.debugLineNum = 708;BA.debugLine="Log(\"Datos enviados : \" & Job.Success)";
anywheresoftware.b4a.keywords.Common.LogImpl("226411009","Datos enviados : "+BA.ObjectToString(_job._success /*boolean*/ ),0);
 //BA.debugLineNum = 709;BA.debugLine="If Job.Success = True Then";
if (_job._success /*boolean*/ ==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 710;BA.debugLine="Dim ret As String";
_ret = "";
 //BA.debugLineNum = 711;BA.debugLine="Dim act As String";
_act = "";
 //BA.debugLineNum = 712;BA.debugLine="ret = Job.GetString";
_ret = _job._getstring /*String*/ ();
 //BA.debugLineNum = 713;BA.debugLine="Dim parser As JSONParser";
_parser = new anywheresoftware.b4a.objects.collections.JSONParser();
 //BA.debugLineNum = 714;BA.debugLine="parser.Initialize(ret)";
_parser.Initialize(_ret);
 //BA.debugLineNum = 715;BA.debugLine="act = parser.NextValue";
_act = BA.ObjectToString(_parser.NextValue());
 //BA.debugLineNum = 716;BA.debugLine="If act = \"Not Found\" Then";
if ((_act).equals("Not Found")) { 
 //BA.debugLineNum = 717;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 718;BA.debugLine="ToastMessageShow(\"Error en la carga de marcado";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Error en la carga de marcadores"),anywheresoftware.b4a.keywords.Common.True);
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 720;BA.debugLine="ToastMessageShow(\"Error loading markers\", True";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Error loading markers"),anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 722;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 }else if((_act).equals("Puntos Cargados")) { 
 //BA.debugLineNum = 724;BA.debugLine="Dim Map1 As Map";
_map1 = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 725;BA.debugLine="Map1.Initialize";
_map1.Initialize();
 //BA.debugLineNum = 726;BA.debugLine="Map1.Put(\"Id\", proyectoIDenviar)";
_map1.Put((Object)("Id"),(Object)(mostCurrent._proyectoidenviar));
 //BA.debugLineNum = 728;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"evals\", \"e";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals","evalsent",(Object)("si"),_map1);
 //BA.debugLineNum = 729;BA.debugLine="ToastMessageShow(\"Evaluación enviada\", False)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Evaluación enviada"),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 730;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 //BA.debugLineNum = 733;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 734;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 735;BA.debugLine="Main.puntostotales = PuntosTotal";
mostCurrent._main._puntostotales /*String*/  = mostCurrent._puntostotal;
 //BA.debugLineNum = 737;BA.debugLine="frmFelicitaciones.numfotosenviadas = numfotosen";
mostCurrent._frmfelicitaciones._numfotosenviadas /*int*/  = _numfotosenviadas;
 //BA.debugLineNum = 738;BA.debugLine="frmFelicitaciones.tiporio = tiporio";
mostCurrent._frmfelicitaciones._tiporio /*String*/  = mostCurrent._tiporio;
 //BA.debugLineNum = 739;BA.debugLine="StartActivity(frmFelicitaciones)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._frmfelicitaciones.getObject()));
 };
 }else {
 //BA.debugLineNum = 743;BA.debugLine="Log(\"envio datos not ok\")";
anywheresoftware.b4a.keywords.Common.LogImpl("226411044","envio datos not ok",0);
 //BA.debugLineNum = 744;BA.debugLine="MsgboxAsync(\"Al parecer hay un problema en nuest";
anywheresoftware.b4a.keywords.Common.MsgboxAsync(BA.ObjectToCharSequence("Al parecer hay un problema en nuestros servidores, lo solucionaremos pronto!"),BA.ObjectToCharSequence("Mala mía"),processBA);
 };
 //BA.debugLineNum = 746;BA.debugLine="Job.Release";
_job._release /*String*/ ();
 //BA.debugLineNum = 747;BA.debugLine="End Sub";
return "";
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 14;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 16;BA.debugLine="Private btnContinuar As Button";
mostCurrent._btncontinuar = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 17;BA.debugLine="Private btnEnviar As Button";
mostCurrent._btnenviar = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 20;BA.debugLine="Dim numfotosenviadas As Int";
_numfotosenviadas = 0;
 //BA.debugLineNum = 21;BA.debugLine="Dim tiporio As String";
mostCurrent._tiporio = "";
 //BA.debugLineNum = 22;BA.debugLine="Dim PuntosFotos As String";
mostCurrent._puntosfotos = "";
 //BA.debugLineNum = 23;BA.debugLine="Dim PuntosEvals As String";
mostCurrent._puntosevals = "";
 //BA.debugLineNum = 24;BA.debugLine="Dim PuntosTotal As String";
mostCurrent._puntostotal = "";
 //BA.debugLineNum = 25;BA.debugLine="Dim numriollanura As String";
mostCurrent._numriollanura = "";
 //BA.debugLineNum = 26;BA.debugLine="Dim numriomontana As String";
mostCurrent._numriomontana = "";
 //BA.debugLineNum = 27;BA.debugLine="Dim numlaguna As String";
mostCurrent._numlaguna = "";
 //BA.debugLineNum = 28;BA.debugLine="Dim numestuario As String";
mostCurrent._numestuario = "";
 //BA.debugLineNum = 29;BA.debugLine="Dim proyectoIDenviar As String";
mostCurrent._proyectoidenviar = "";
 //BA.debugLineNum = 30;BA.debugLine="Dim files As List";
mostCurrent._files = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 34;BA.debugLine="Dim Timer1 As Timer";
mostCurrent._timer1 = new anywheresoftware.b4a.objects.Timer();
 //BA.debugLineNum = 35;BA.debugLine="Private Label1 As Label";
mostCurrent._label1 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 36;BA.debugLine="Private Label2 As Label";
mostCurrent._label2 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 37;BA.debugLine="Private Label3 As Label";
mostCurrent._label3 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 38;BA.debugLine="Private Label4 As Label";
mostCurrent._label4 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 39;BA.debugLine="Private Label5 As Label";
mostCurrent._label5 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 40;BA.debugLine="Private lblFinalizado1 As Label";
mostCurrent._lblfinalizado1 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 41;BA.debugLine="Private lblYaTienesTodo As Label";
mostCurrent._lblyatienestodo = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 46;BA.debugLine="Dim foto1, foto2, foto3, foto4,foto5 As String";
mostCurrent._foto1 = "";
mostCurrent._foto2 = "";
mostCurrent._foto3 = "";
mostCurrent._foto4 = "";
mostCurrent._foto5 = "";
 //BA.debugLineNum = 47;BA.debugLine="Private totalFotos As Int";
_totalfotos = 0;
 //BA.debugLineNum = 48;BA.debugLine="Private foto1Sent As Boolean = False";
_foto1sent = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 49;BA.debugLine="Private foto2Sent As Boolean = False";
_foto2sent = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 50;BA.debugLine="Private foto3Sent As Boolean = False";
_foto3sent = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 51;BA.debugLine="Private foto4Sent As Boolean = False";
_foto4sent = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 52;BA.debugLine="Private foto5Sent As Boolean = False";
_foto5sent = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 53;BA.debugLine="Private ProgressBar1 As ProgressBar";
mostCurrent._progressbar1 = new anywheresoftware.b4a.objects.ProgressBarWrapper();
 //BA.debugLineNum = 54;BA.debugLine="Private ProgressBar2 As ProgressBar";
mostCurrent._progressbar2 = new anywheresoftware.b4a.objects.ProgressBarWrapper();
 //BA.debugLineNum = 55;BA.debugLine="Private ProgressBar3 As ProgressBar";
mostCurrent._progressbar3 = new anywheresoftware.b4a.objects.ProgressBarWrapper();
 //BA.debugLineNum = 56;BA.debugLine="Private ProgressBar4 As ProgressBar";
mostCurrent._progressbar4 = new anywheresoftware.b4a.objects.ProgressBarWrapper();
 //BA.debugLineNum = 57;BA.debugLine="Private ProgressBar5 As ProgressBar";
mostCurrent._progressbar5 = new anywheresoftware.b4a.objects.ProgressBarWrapper();
 //BA.debugLineNum = 58;BA.debugLine="Private foto1ProgressBar As CircularProgressBar";
mostCurrent._foto1progressbar = new ilpla.appear.circularprogressbar();
 //BA.debugLineNum = 59;BA.debugLine="Private foto2ProgressBar As CircularProgressBar";
mostCurrent._foto2progressbar = new ilpla.appear.circularprogressbar();
 //BA.debugLineNum = 60;BA.debugLine="Private foto3ProgressBar As CircularProgressBar";
mostCurrent._foto3progressbar = new ilpla.appear.circularprogressbar();
 //BA.debugLineNum = 61;BA.debugLine="Private foto4ProgressBar As CircularProgressBar";
mostCurrent._foto4progressbar = new ilpla.appear.circularprogressbar();
 //BA.debugLineNum = 62;BA.debugLine="Dim fotosEnviadas As Int";
_fotosenviadas = 0;
 //BA.debugLineNum = 63;BA.debugLine="Dim pw As PhoneWakeState";
mostCurrent._pw = new anywheresoftware.b4a.phone.Phone.PhoneWakeState();
 //BA.debugLineNum = 64;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 6;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 9;BA.debugLine="Dim Up1 As UploadFilePhp";
_up1 = new com.spinter.uploadfilephp.UploadFilePhp();
 //BA.debugLineNum = 11;BA.debugLine="Private TimerEnvio As Timer";
_timerenvio = new anywheresoftware.b4a.objects.Timer();
 //BA.debugLineNum = 12;BA.debugLine="End Sub";
return "";
}
public static String  _testinternet_complete(ilpla.appear.httpjob _job) throws Exception{
 //BA.debugLineNum = 213;BA.debugLine="Sub TestInternet_Complete(Job As HttpJob)";
 //BA.debugLineNum = 214;BA.debugLine="Log(\"Chequeo de internet: \" & Job.Success)";
anywheresoftware.b4a.keywords.Common.LogImpl("225821185","Chequeo de internet: "+BA.ObjectToString(_job._success /*boolean*/ ),0);
 //BA.debugLineNum = 215;BA.debugLine="If Job.Success = True Then";
if (_job._success /*boolean*/ ==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 217;BA.debugLine="Main.modooffline = False";
mostCurrent._main._modooffline /*boolean*/  = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 218;BA.debugLine="Job.Release";
_job._release /*String*/ ();
 //BA.debugLineNum = 220;BA.debugLine="EnvioDatos(Form_Reporte.currentproject)";
_enviodatos(mostCurrent._form_reporte._currentproject /*String*/ );
 }else {
 //BA.debugLineNum = 223;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 224;BA.debugLine="MsgboxAsync(\"No hay conexión a internet, prueba";
anywheresoftware.b4a.keywords.Common.MsgboxAsync(BA.ObjectToCharSequence("No hay conexión a internet, prueba cuando estés conectado!"),BA.ObjectToCharSequence("No hay internet"),processBA);
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 226;BA.debugLine="MsgboxAsync(\"No internet connection, try again";
anywheresoftware.b4a.keywords.Common.MsgboxAsync(BA.ObjectToCharSequence("No internet connection, try again later!"),BA.ObjectToCharSequence("No internet"),processBA);
 };
 //BA.debugLineNum = 228;BA.debugLine="Main.modooffline = True";
mostCurrent._main._modooffline /*boolean*/  = anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 229;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 //BA.debugLineNum = 230;BA.debugLine="Job.Release";
_job._release /*String*/ ();
 //BA.debugLineNum = 231;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 232;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 };
 //BA.debugLineNum = 234;BA.debugLine="End Sub";
return "";
}
public static String  _timer1_tick() throws Exception{
 //BA.debugLineNum = 91;BA.debugLine="Sub Timer1_Tick";
 //BA.debugLineNum = 92;BA.debugLine="If Label1.Visible = False Then";
if (mostCurrent._label1.getVisible()==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 93;BA.debugLine="Label1.Visible = True";
mostCurrent._label1.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 94;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 96;BA.debugLine="If Label1.Visible = True And Label2.Visible = Fal";
if (mostCurrent._label1.getVisible()==anywheresoftware.b4a.keywords.Common.True && mostCurrent._label2.getVisible()==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 97;BA.debugLine="Label2.Visible = True";
mostCurrent._label2.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 98;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 100;BA.debugLine="If Label2.Visible = True And Label3.Visible = Fal";
if (mostCurrent._label2.getVisible()==anywheresoftware.b4a.keywords.Common.True && mostCurrent._label3.getVisible()==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 101;BA.debugLine="Label3.Visible = True";
mostCurrent._label3.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 102;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 117;BA.debugLine="If Label3.Visible = True Then";
if (mostCurrent._label3.getVisible()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 118;BA.debugLine="lblYaTienesTodo.Visible = True";
mostCurrent._lblyatienestodo.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 119;BA.debugLine="lblFinalizado1.Visible = True";
mostCurrent._lblfinalizado1.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 120;BA.debugLine="btnEnviar.Visible = True";
mostCurrent._btnenviar.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 121;BA.debugLine="Timer1.Enabled = False";
mostCurrent._timer1.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 124;BA.debugLine="End Sub";
return "";
}
public static String  _timerenvio_tick() throws Exception{
anywheresoftware.b4a.objects.collections.Map _map1 = null;
 //BA.debugLineNum = 510;BA.debugLine="Sub TimerEnvio_Tick";
 //BA.debugLineNum = 511;BA.debugLine="foto1ProgressBar.Value = ProgressBar1.Progress";
mostCurrent._foto1progressbar._setvalue /*float*/ ((float) (mostCurrent._progressbar1.getProgress()));
 //BA.debugLineNum = 512;BA.debugLine="foto2ProgressBar.Value = ProgressBar2.Progress";
mostCurrent._foto2progressbar._setvalue /*float*/ ((float) (mostCurrent._progressbar2.getProgress()));
 //BA.debugLineNum = 513;BA.debugLine="foto3ProgressBar.Value = ProgressBar3.Progress";
mostCurrent._foto3progressbar._setvalue /*float*/ ((float) (mostCurrent._progressbar3.getProgress()));
 //BA.debugLineNum = 514;BA.debugLine="foto4ProgressBar.Value = ProgressBar4.Progress";
mostCurrent._foto4progressbar._setvalue /*float*/ ((float) (mostCurrent._progressbar4.getProgress()));
 //BA.debugLineNum = 515;BA.debugLine="If fotosEnviadas = totalFotos Then";
if (_fotosenviadas==_totalfotos) { 
 //BA.debugLineNum = 516;BA.debugLine="Log(\"TODAS LAS FOTOS FUERON ENVIADAS CORRECTAMEN";
anywheresoftware.b4a.keywords.Common.LogImpl("226083334","TODAS LAS FOTOS FUERON ENVIADAS CORRECTAMENTE",0);
 //BA.debugLineNum = 517;BA.debugLine="lblFinalizado1.Text = \"Fotos enviadas!\"";
mostCurrent._lblfinalizado1.setText(BA.ObjectToCharSequence("Fotos enviadas!"));
 //BA.debugLineNum = 518;BA.debugLine="Up1.UploadKill";
_up1.UploadKill(processBA);
 //BA.debugLineNum = 524;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 525;BA.debugLine="ToastMessageShow(\"Evaluación enviada\", False)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Evaluación enviada"),anywheresoftware.b4a.keywords.Common.False);
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 527;BA.debugLine="ToastMessageShow(\"Report sent\", False)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Report sent"),anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 531;BA.debugLine="Dim Map1 As Map";
_map1 = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 532;BA.debugLine="Map1.Initialize";
_map1.Initialize();
 //BA.debugLineNum = 533;BA.debugLine="Map1.Put(\"Id\", Main.currentproject)";
_map1.Put((Object)("Id"),(Object)(mostCurrent._main._currentproject /*String*/ ));
 //BA.debugLineNum = 534;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"evals\", \"fo";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals","foto1sent",(Object)("si"),_map1);
 //BA.debugLineNum = 535;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"evals\", \"fo";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals","foto2sent",(Object)("si"),_map1);
 //BA.debugLineNum = 536;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"evals\", \"fo";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals","foto3sent",(Object)("si"),_map1);
 //BA.debugLineNum = 537;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"evals\", \"fo";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals","foto4sent",(Object)("si"),_map1);
 //BA.debugLineNum = 538;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"evals\", \"fo";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals","foto5sent",(Object)("si"),_map1);
 //BA.debugLineNum = 540;BA.debugLine="lblFinalizado1.Text = \"Enviando puntos\"";
mostCurrent._lblfinalizado1.setText(BA.ObjectToCharSequence("Enviando puntos"));
 //BA.debugLineNum = 542;BA.debugLine="frmFelicitaciones.tiporio = tiporio";
mostCurrent._frmfelicitaciones._tiporio /*String*/  = mostCurrent._tiporio;
 //BA.debugLineNum = 543;BA.debugLine="StartActivity(frmFelicitaciones)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._frmfelicitaciones.getObject()));
 //BA.debugLineNum = 545;BA.debugLine="TimerEnvio.Enabled = False";
_timerenvio.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 549;BA.debugLine="End Sub";
return "";
}
public static String  _up1_sendfile(String _value) throws Exception{
 //BA.debugLineNum = 556;BA.debugLine="Sub Up1_sendFile (value As String)";
 //BA.debugLineNum = 557;BA.debugLine="Log(\"sendfile event:\" & value)";
anywheresoftware.b4a.keywords.Common.LogImpl("226214401","sendfile event:"+_value,0);
 //BA.debugLineNum = 558;BA.debugLine="If value = \"success\" Then";
if ((_value).equals("success")) { 
 //BA.debugLineNum = 560;BA.debugLine="If fotosEnviadas = 0 And totalFotos = 1 Then";
if (_fotosenviadas==0 && _totalfotos==1) { 
 //BA.debugLineNum = 561;BA.debugLine="fotosEnviadas = 1";
_fotosenviadas = (int) (1);
 //BA.debugLineNum = 562;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 564;BA.debugLine="If fotosEnviadas = 1 And totalFotos = 2 Then";
if (_fotosenviadas==1 && _totalfotos==2) { 
 //BA.debugLineNum = 565;BA.debugLine="fotosEnviadas = 2";
_fotosenviadas = (int) (2);
 //BA.debugLineNum = 566;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 568;BA.debugLine="If fotosEnviadas = 2 And totalFotos = 3 Then";
if (_fotosenviadas==2 && _totalfotos==3) { 
 //BA.debugLineNum = 569;BA.debugLine="fotosEnviadas = 3";
_fotosenviadas = (int) (3);
 //BA.debugLineNum = 570;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 572;BA.debugLine="If fotosEnviadas = 3 And totalFotos = 4 Then";
if (_fotosenviadas==3 && _totalfotos==4) { 
 //BA.debugLineNum = 573;BA.debugLine="fotosEnviadas = 4";
_fotosenviadas = (int) (4);
 //BA.debugLineNum = 574;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 576;BA.debugLine="If fotosEnviadas = 4 And totalFotos = 5 Then";
if (_fotosenviadas==4 && _totalfotos==5) { 
 //BA.debugLineNum = 577;BA.debugLine="fotosEnviadas = 5";
_fotosenviadas = (int) (5);
 //BA.debugLineNum = 578;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 582;BA.debugLine="If fotosEnviadas = 0 And totalFotos > 1 Then";
if (_fotosenviadas==0 && _totalfotos>1) { 
 //BA.debugLineNum = 583;BA.debugLine="fotosEnviadas = 1";
_fotosenviadas = (int) (1);
 //BA.debugLineNum = 584;BA.debugLine="If File.Exists(File.DirRootExternal & \"/PreserV";
if (anywheresoftware.b4a.keywords.Common.File.Exists(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/PreserVamos/",mostCurrent._foto2+".jpg") && _foto2sent==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 585;BA.debugLine="Log(\"Enviando foto 2 \")";
anywheresoftware.b4a.keywords.Common.LogImpl("226214429","Enviando foto 2 ",0);
 //BA.debugLineNum = 586;BA.debugLine="lblFinalizado1.Text = \"Enviando foto 2...\"";
mostCurrent._lblfinalizado1.setText(BA.ObjectToCharSequence("Enviando foto 2..."));
 //BA.debugLineNum = 587;BA.debugLine="ProgressBar2.Progress  = 0";
mostCurrent._progressbar2.setProgress((int) (0));
 //BA.debugLineNum = 589;BA.debugLine="foto2ProgressBar.Visible = True";
mostCurrent._foto2progressbar._setvisible /*boolean*/ (anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 590;BA.debugLine="Up1.doFileUpload(ProgressBar2,Null,File.DirRoo";
_up1.doFileUpload(processBA,(android.widget.ProgressBar)(mostCurrent._progressbar2.getObject()),(android.widget.TextView)(anywheresoftware.b4a.keywords.Common.Null),anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/PreserVamos/"+mostCurrent._foto2+".jpg",mostCurrent._main._serverpath /*String*/ +"/"+mostCurrent._main._serverconnectionfolder /*String*/ +"/upload_file.php");
 //BA.debugLineNum = 591;BA.debugLine="ProgressBar2.Visible = False";
mostCurrent._progressbar2.setVisible(anywheresoftware.b4a.keywords.Common.False);
 }else {
 //BA.debugLineNum = 593;BA.debugLine="Log(\"no foto 2\")";
anywheresoftware.b4a.keywords.Common.LogImpl("226214437","no foto 2",0);
 };
 };
 //BA.debugLineNum = 596;BA.debugLine="If fotosEnviadas = 1 And totalFotos > 2 Then";
if (_fotosenviadas==1 && _totalfotos>2) { 
 //BA.debugLineNum = 597;BA.debugLine="fotosEnviadas = 2";
_fotosenviadas = (int) (2);
 //BA.debugLineNum = 598;BA.debugLine="If File.Exists(File.DirRootExternal & \"/PreserV";
if (anywheresoftware.b4a.keywords.Common.File.Exists(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/PreserVamos/",mostCurrent._foto3+".jpg") && _foto3sent==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 599;BA.debugLine="Log(\"Enviando foto 3 \")";
anywheresoftware.b4a.keywords.Common.LogImpl("226214443","Enviando foto 3 ",0);
 //BA.debugLineNum = 600;BA.debugLine="lblFinalizado1.Text = \"Enviando foto 3...\"";
mostCurrent._lblfinalizado1.setText(BA.ObjectToCharSequence("Enviando foto 3..."));
 //BA.debugLineNum = 601;BA.debugLine="ProgressBar3.Progress  = 0";
mostCurrent._progressbar3.setProgress((int) (0));
 //BA.debugLineNum = 602;BA.debugLine="ProgressBar3.Visible = False";
mostCurrent._progressbar3.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 603;BA.debugLine="foto3ProgressBar.Visible = True";
mostCurrent._foto3progressbar._setvisible /*boolean*/ (anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 604;BA.debugLine="Up1.doFileUpload(ProgressBar3,Null,File.DirRoo";
_up1.doFileUpload(processBA,(android.widget.ProgressBar)(mostCurrent._progressbar3.getObject()),(android.widget.TextView)(anywheresoftware.b4a.keywords.Common.Null),anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/PreserVamos/"+mostCurrent._foto3+".jpg",mostCurrent._main._serverpath /*String*/ +"/"+mostCurrent._main._serverconnectionfolder /*String*/ +"/upload_file.php");
 }else {
 //BA.debugLineNum = 606;BA.debugLine="Log(\"no foto 3\")";
anywheresoftware.b4a.keywords.Common.LogImpl("226214450","no foto 3",0);
 };
 };
 //BA.debugLineNum = 609;BA.debugLine="If fotosEnviadas = 2 And totalFotos > 3 Then";
if (_fotosenviadas==2 && _totalfotos>3) { 
 //BA.debugLineNum = 610;BA.debugLine="fotosEnviadas = 3";
_fotosenviadas = (int) (3);
 //BA.debugLineNum = 611;BA.debugLine="If File.Exists(File.DirRootExternal & \"/PreserV";
if (anywheresoftware.b4a.keywords.Common.File.Exists(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/PreserVamos/",mostCurrent._foto4+".jpg") && _foto4sent==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 612;BA.debugLine="Log(\"Enviando foto 4 \")";
anywheresoftware.b4a.keywords.Common.LogImpl("226214456","Enviando foto 4 ",0);
 //BA.debugLineNum = 613;BA.debugLine="lblFinalizado1.Text = \"Enviando foto 4...\"";
mostCurrent._lblfinalizado1.setText(BA.ObjectToCharSequence("Enviando foto 4..."));
 //BA.debugLineNum = 614;BA.debugLine="ProgressBar4.Progress  = 0";
mostCurrent._progressbar4.setProgress((int) (0));
 //BA.debugLineNum = 615;BA.debugLine="ProgressBar4.Visible = False";
mostCurrent._progressbar4.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 616;BA.debugLine="foto4ProgressBar.Visible = True";
mostCurrent._foto4progressbar._setvisible /*boolean*/ (anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 617;BA.debugLine="Up1.doFileUpload(ProgressBar4,Null,File.DirRoo";
_up1.doFileUpload(processBA,(android.widget.ProgressBar)(mostCurrent._progressbar4.getObject()),(android.widget.TextView)(anywheresoftware.b4a.keywords.Common.Null),anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/PreserVamos/"+mostCurrent._foto4+".jpg",mostCurrent._main._serverpath /*String*/ +"/"+mostCurrent._main._serverconnectionfolder /*String*/ +"/upload_file.php");
 }else {
 //BA.debugLineNum = 619;BA.debugLine="Log(\"no foto 4\")";
anywheresoftware.b4a.keywords.Common.LogImpl("226214463","no foto 4",0);
 };
 };
 //BA.debugLineNum = 622;BA.debugLine="If fotosEnviadas = 3 And totalFotos > 4 Then";
if (_fotosenviadas==3 && _totalfotos>4) { 
 //BA.debugLineNum = 623;BA.debugLine="fotosEnviadas = 4";
_fotosenviadas = (int) (4);
 //BA.debugLineNum = 624;BA.debugLine="If File.Exists(File.DirRootExternal & \"/PreserV";
if (anywheresoftware.b4a.keywords.Common.File.Exists(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/PreserVamos/",mostCurrent._foto5+".jpg") && _foto5sent==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 625;BA.debugLine="Log(\"Enviando foto 5 \")";
anywheresoftware.b4a.keywords.Common.LogImpl("226214469","Enviando foto 5 ",0);
 //BA.debugLineNum = 626;BA.debugLine="lblFinalizado1.Text = \"Enviando foto 5...\"";
mostCurrent._lblfinalizado1.setText(BA.ObjectToCharSequence("Enviando foto 5..."));
 //BA.debugLineNum = 627;BA.debugLine="ProgressBar5.Progress  = 0";
mostCurrent._progressbar5.setProgress((int) (0));
 //BA.debugLineNum = 628;BA.debugLine="ProgressBar5.Visible = False";
mostCurrent._progressbar5.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 629;BA.debugLine="Up1.doFileUpload(ProgressBar5,Null,File.DirRoo";
_up1.doFileUpload(processBA,(android.widget.ProgressBar)(mostCurrent._progressbar5.getObject()),(android.widget.TextView)(anywheresoftware.b4a.keywords.Common.Null),anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/PreserVamos/"+mostCurrent._foto5+".jpg",mostCurrent._main._serverpath /*String*/ +"/"+mostCurrent._main._serverconnectionfolder /*String*/ +"/upload_file.php");
 }else {
 //BA.debugLineNum = 631;BA.debugLine="Log(\"no foto 5\")";
anywheresoftware.b4a.keywords.Common.LogImpl("226214475","no foto 5",0);
 };
 };
 }else if((_value).equals("Error!")) { 
 //BA.debugLineNum = 637;BA.debugLine="Log(\"FOTO error\")";
anywheresoftware.b4a.keywords.Common.LogImpl("226214481","FOTO error",0);
 //BA.debugLineNum = 638;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 639;BA.debugLine="MsgboxAsync(\"Ha habido un error en el envío. Re";
anywheresoftware.b4a.keywords.Common.MsgboxAsync(BA.ObjectToCharSequence("Ha habido un error en el envío. Revisa tu conexión a Internet e intenta de nuevo desde 'Datos Anteriores'"),BA.ObjectToCharSequence("Oops!"),processBA);
 };
 //BA.debugLineNum = 641;BA.debugLine="Up1.UploadKill";
_up1.UploadKill(processBA);
 //BA.debugLineNum = 646;BA.debugLine="TimerEnvio.Enabled = False";
_timerenvio.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 647;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 648;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 649;BA.debugLine="Activity_Create(False)";
_activity_create(anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 652;BA.debugLine="End Sub";
return "";
}
public static String  _up1_statusupload(String _value) throws Exception{
 //BA.debugLineNum = 552;BA.debugLine="Sub Up1_statusUpload (value As String)";
 //BA.debugLineNum = 554;BA.debugLine="End Sub";
return "";
}
public static String  _up1_statusvisible(boolean _onoff,String _value) throws Exception{
 //BA.debugLineNum = 653;BA.debugLine="Sub Up1_statusVISIBLE (onoff As Boolean,value As S";
 //BA.debugLineNum = 655;BA.debugLine="End Sub";
return "";
}
}
