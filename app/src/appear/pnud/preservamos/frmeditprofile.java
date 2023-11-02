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

public class frmeditprofile extends Activity implements B4AActivity{
	public static frmeditprofile mostCurrent;
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
			processBA = new BA(this.getApplicationContext(), null, null, "appear.pnud.preservamos", "appear.pnud.preservamos.frmeditprofile");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
            
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (frmeditprofile).");
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
		activityBA = new BA(this, layout, processBA, "appear.pnud.preservamos", "appear.pnud.preservamos.frmeditprofile");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "appear.pnud.preservamos.frmeditprofile", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density, mostCurrent);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (frmeditprofile) Create " + (isFirst ? "(first time)" : "") + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (frmeditprofile) Resume **");
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
		return frmeditprofile.class;
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
            BA.LogInfo("** Activity (frmeditprofile) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
        else
            BA.LogInfo("** Activity (frmeditprofile) Pause event (activity is not paused). **");
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
            frmeditprofile mc = mostCurrent;
			if (mc == null || mc != activity.get())
				return;
			processBA.setActivityPaused(false);
            BA.LogInfo("** Activity (frmeditprofile) Resume **");
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
public anywheresoftware.b4a.objects.EditTextWrapper _txtpassword = null;
public anywheresoftware.b4a.objects.EditTextWrapper _txtpassword2 = null;
public anywheresoftware.b4a.objects.EditTextWrapper _txtfullname = null;
public anywheresoftware.b4a.objects.EditTextWrapper _txtemail = null;
public anywheresoftware.b4a.objects.EditTextWrapper _txtlocation = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblunmaskpass = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbltitle = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btneditpassword = null;
public anywheresoftware.b4a.objects.SpinnerWrapper _spnperfil = null;
public anywheresoftware.b4a.objects.SpinnerWrapper _spnorganizaciones = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblorg = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblagregarorg = null;
public anywheresoftware.b4a.objects.SpinnerWrapper _spnsexo = null;
public static String _nombreorg = "";
public static String _idorg = "";
public static String _localidadorg = "";
public static String _provinciaorg = "";
public static String _tipoorg = "";
public static String _adminorg = "";
public static String _idorgelegida = "";
public anywheresoftware.b4a.objects.collections.Map _listaorgs = null;
public anywheresoftware.b4a.agraham.dialogs.InputDialog.DateDialog _dated = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblfullname = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbllocation = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblperfil = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblcambiarpass = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btncancelaredit = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btneditprofile = null;
public static boolean _hubocambios = false;
public anywheresoftware.b4a.objects.LabelWrapper _lblfechadenacimiento = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btneditfullname = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btneditsexo = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btneditfechadenacimiento = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper _chkempleadomunicipio = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btneditempleadomunicipal = null;
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
public appear.pnud.preservamos.reporte_habitat_laguna _reporte_habitat_laguna = null;
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
anywheresoftware.b4a.objects.collections.List _list1 = null;
 //BA.debugLineNum = 61;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
 //BA.debugLineNum = 62;BA.debugLine="Activity.LoadLayout(\"layEditProfile\")";
mostCurrent._activity.LoadLayout("layEditProfile",mostCurrent.activityBA);
 //BA.debugLineNum = 63;BA.debugLine="hubocambios = False";
_hubocambios = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 65;BA.debugLine="spnSexo.Add(\"Femenino\")";
mostCurrent._spnsexo.Add("Femenino");
 //BA.debugLineNum = 66;BA.debugLine="spnSexo.Add(\"Masculino\")";
mostCurrent._spnsexo.Add("Masculino");
 //BA.debugLineNum = 67;BA.debugLine="spnSexo.Add(\"X\")";
mostCurrent._spnsexo.Add("X");
 //BA.debugLineNum = 68;BA.debugLine="spnSexo.Visible = True";
mostCurrent._spnsexo.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 70;BA.debugLine="Dim List1 As List";
_list1 = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 71;BA.debugLine="List1 = File.ReadList(File.DirAssets, \"partidos.t";
_list1 = anywheresoftware.b4a.keywords.Common.File.ReadList(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"partidos.txt");
 //BA.debugLineNum = 72;BA.debugLine="spnOrganizaciones.AddAll(List1)";
mostCurrent._spnorganizaciones.AddAll(_list1);
 //BA.debugLineNum = 74;BA.debugLine="txtFullName.Text = Main.strUserFullName";
mostCurrent._txtfullname.setText(BA.ObjectToCharSequence(mostCurrent._main._struserfullname /*String*/ ));
 //BA.debugLineNum = 75;BA.debugLine="lblTitle.Text = Main.username";
mostCurrent._lbltitle.setText(BA.ObjectToCharSequence(mostCurrent._main._username /*String*/ ));
 //BA.debugLineNum = 76;BA.debugLine="txtLocation.Text = Main.strUserLocation";
mostCurrent._txtlocation.setText(BA.ObjectToCharSequence(mostCurrent._main._struserlocation /*String*/ ));
 //BA.debugLineNum = 77;BA.debugLine="lblFechadeNacimiento.Text = Main.userFechadeNacim";
mostCurrent._lblfechadenacimiento.setText(BA.ObjectToCharSequence(mostCurrent._main._userfechadenacimiento /*String*/ ));
 //BA.debugLineNum = 78;BA.debugLine="txtEmail.Text = Main.strUserEmail";
mostCurrent._txtemail.setText(BA.ObjectToCharSequence(mostCurrent._main._struseremail /*String*/ ));
 //BA.debugLineNum = 79;BA.debugLine="spnSexo.SelectedIndex = spnSexo.IndexOf(Main.user";
mostCurrent._spnsexo.setSelectedIndex(mostCurrent._spnsexo.IndexOf(mostCurrent._main._usersexo /*String*/ ));
 //BA.debugLineNum = 80;BA.debugLine="spnOrganizaciones.SelectedIndex = spnOrganizacion";
mostCurrent._spnorganizaciones.setSelectedIndex(mostCurrent._spnorganizaciones.IndexOf(mostCurrent._main._struserorg /*String*/ ));
 //BA.debugLineNum = 82;BA.debugLine="If Main.strUserTipoUsuario = \"municipal\" Then";
if ((mostCurrent._main._strusertipousuario /*String*/ ).equals("municipal")) { 
 //BA.debugLineNum = 83;BA.debugLine="chkEmpleadoMunicipio.Checked = True";
mostCurrent._chkempleadomunicipio.setChecked(anywheresoftware.b4a.keywords.Common.True);
 }else {
 //BA.debugLineNum = 85;BA.debugLine="chkEmpleadoMunicipio.Checked = False";
mostCurrent._chkempleadomunicipio.setChecked(anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 89;BA.debugLine="End Sub";
return "";
}
public static boolean  _activity_keypress(int _keycode) throws Exception{
 //BA.debugLineNum = 90;BA.debugLine="Sub Activity_KeyPress (KeyCode As Int) As Boolean";
 //BA.debugLineNum = 91;BA.debugLine="If KeyCode = KeyCodes.KEYCODE_BACK Then";
if (_keycode==anywheresoftware.b4a.keywords.Common.KeyCodes.KEYCODE_BACK) { 
 //BA.debugLineNum = 92;BA.debugLine="Activity.finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 93;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 };
 //BA.debugLineNum = 96;BA.debugLine="End Sub";
return false;
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 100;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 102;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 97;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 99;BA.debugLine="End Sub";
return "";
}
public static String  _btncancelaredit_click() throws Exception{
 //BA.debugLineNum = 193;BA.debugLine="Sub btnCancelarEdit_Click";
 //BA.debugLineNum = 194;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 195;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 196;BA.debugLine="End Sub";
return "";
}
public static String  _btneditempleadomunicipal_click() throws Exception{
 //BA.debugLineNum = 189;BA.debugLine="Private Sub btnEditEmpleadoMunicipal_Click";
 //BA.debugLineNum = 190;BA.debugLine="chkEmpleadoMunicipio.Enabled = True";
mostCurrent._chkempleadomunicipio.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 191;BA.debugLine="End Sub";
return "";
}
public static String  _btneditfechadenacimiento_click() throws Exception{
String _fechanueva = "";
 //BA.debugLineNum = 142;BA.debugLine="Sub btnEditFechaDeNacimiento_Click";
 //BA.debugLineNum = 143;BA.debugLine="Dim fechanueva As String";
_fechanueva = "";
 //BA.debugLineNum = 144;BA.debugLine="dated.ShowCalendar = True";
mostCurrent._dated.ShowCalendar = anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 145;BA.debugLine="dated.Year = DateTime.GetYear(DateTime.now)     '";
mostCurrent._dated.setYear(anywheresoftware.b4a.keywords.Common.DateTime.GetYear(anywheresoftware.b4a.keywords.Common.DateTime.getNow()));
 //BA.debugLineNum = 146;BA.debugLine="dated.Month = DateTime.GetMonth(DateTime.now)";
mostCurrent._dated.setMonth(anywheresoftware.b4a.keywords.Common.DateTime.GetMonth(anywheresoftware.b4a.keywords.Common.DateTime.getNow()));
 //BA.debugLineNum = 147;BA.debugLine="dated.DayOfMonth = DateTime.GetDayOfMonth(DateTim";
mostCurrent._dated.setDayOfMonth(anywheresoftware.b4a.keywords.Common.DateTime.GetDayOfMonth(anywheresoftware.b4a.keywords.Common.DateTime.getNow()));
 //BA.debugLineNum = 148;BA.debugLine="dated.Show(\"Elija su fecha de nacimiento\", \"Fecha";
mostCurrent._dated.Show("Elija su fecha de nacimiento","Fecha de nacimiento","Ok","Cancelar","",mostCurrent.activityBA,(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null));
 //BA.debugLineNum = 149;BA.debugLine="fechanueva = dated.DayOfMonth & \"-\" & dated.Month";
_fechanueva = BA.NumberToString(mostCurrent._dated.getDayOfMonth())+"-"+BA.NumberToString(mostCurrent._dated.getMonth())+"-"+BA.NumberToString(mostCurrent._dated.getYear());
 //BA.debugLineNum = 150;BA.debugLine="lblFechadeNacimiento.Text = fechanueva";
mostCurrent._lblfechadenacimiento.setText(BA.ObjectToCharSequence(_fechanueva));
 //BA.debugLineNum = 151;BA.debugLine="btnEditFechaDeNacimiento.visible = False";
mostCurrent._btneditfechadenacimiento.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 152;BA.debugLine="hubocambios = True";
_hubocambios = anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 153;BA.debugLine="End Sub";
return "";
}
public static String  _btneditfullname_click() throws Exception{
 //BA.debugLineNum = 121;BA.debugLine="Sub btnEditFullName_Click";
 //BA.debugLineNum = 122;BA.debugLine="txtFullName.Enabled = True";
mostCurrent._txtfullname.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 123;BA.debugLine="hubocambios = True";
_hubocambios = anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 124;BA.debugLine="txtFullName.RequestFocus";
mostCurrent._txtfullname.RequestFocus();
 //BA.debugLineNum = 125;BA.debugLine="txtFullName.TextColor = Colors.Black";
mostCurrent._txtfullname.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 126;BA.debugLine="btnEditFullName.visible = False";
mostCurrent._btneditfullname.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 127;BA.debugLine="End Sub";
return "";
}
public static String  _btneditlocation_click() throws Exception{
 //BA.debugLineNum = 129;BA.debugLine="Sub btnEditLocation_Click";
 //BA.debugLineNum = 130;BA.debugLine="txtLocation.Enabled = True";
mostCurrent._txtlocation.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 131;BA.debugLine="hubocambios = True";
_hubocambios = anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 132;BA.debugLine="End Sub";
return "";
}
public static String  _btneditpassword_click() throws Exception{
 //BA.debugLineNum = 155;BA.debugLine="Sub btnEditPassword_Click";
 //BA.debugLineNum = 156;BA.debugLine="txtPassword.Enabled = True";
mostCurrent._txtpassword.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 157;BA.debugLine="txtPassword2.Enabled = True";
mostCurrent._txtpassword2.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 158;BA.debugLine="txtPassword.Visible = True";
mostCurrent._txtpassword.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 159;BA.debugLine="txtPassword2.Visible = True";
mostCurrent._txtpassword2.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 160;BA.debugLine="btnEditPassword.Visible = False";
mostCurrent._btneditpassword.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 161;BA.debugLine="lblUnmaskPass.Visible = True";
mostCurrent._lblunmaskpass.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 162;BA.debugLine="hubocambios = True";
_hubocambios = anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 163;BA.debugLine="End Sub";
return "";
}
public static String  _btneditprofile_click() throws Exception{
 //BA.debugLineNum = 203;BA.debugLine="Sub btnEditProfile_Click";
 //BA.debugLineNum = 231;BA.debugLine="If hubocambios = True Then";
if (_hubocambios==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 232;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 233;BA.debugLine="ProgressDialogShow(\"Guardando cambios...\")";
anywheresoftware.b4a.keywords.Common.ProgressDialogShow(mostCurrent.activityBA,BA.ObjectToCharSequence("Guardando cambios..."));
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 235;BA.debugLine="ProgressDialogShow(\"Saving changes...\")";
anywheresoftware.b4a.keywords.Common.ProgressDialogShow(mostCurrent.activityBA,BA.ObjectToCharSequence("Saving changes..."));
 };
 //BA.debugLineNum = 237;BA.debugLine="Log(\"Chequeando internet\")";
anywheresoftware.b4a.keywords.Common.LogImpl("445416482","Chequeando internet",0);
 //BA.debugLineNum = 238;BA.debugLine="CheckInternet";
_checkinternet();
 };
 //BA.debugLineNum = 243;BA.debugLine="End Sub";
return "";
}
public static String  _btneditsexo_click() throws Exception{
 //BA.debugLineNum = 134;BA.debugLine="Sub btnEditSexo_Click";
 //BA.debugLineNum = 135;BA.debugLine="hubocambios = True";
_hubocambios = anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 136;BA.debugLine="spnSexo.Enabled = True";
mostCurrent._spnsexo.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 137;BA.debugLine="spnSexo.Visible = True";
mostCurrent._spnsexo.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 138;BA.debugLine="spnSexo.TextColor = Colors.Black";
mostCurrent._spnsexo.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 139;BA.debugLine="btnEditSexo.visible = False";
mostCurrent._btneditsexo.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 140;BA.debugLine="End Sub";
return "";
}
public static String  _cargargrupos() throws Exception{
appear.pnud.preservamos.downloadservice._downloaddata _dd = null;
 //BA.debugLineNum = 415;BA.debugLine="Sub CargarGrupos";
 //BA.debugLineNum = 416;BA.debugLine="listaOrgs.Initialize";
mostCurrent._listaorgs.Initialize();
 //BA.debugLineNum = 417;BA.debugLine="Dim dd As DownloadData";
_dd = new appear.pnud.preservamos.downloadservice._downloaddata();
 //BA.debugLineNum = 418;BA.debugLine="dd.url = Main.serverPath & \"/\" & Main.serverConne";
_dd.url /*String*/  = mostCurrent._main._serverpath /*String*/ +"/"+mostCurrent._main._serverconnectionfolder /*String*/ +"/retrieve_orgs.php";
 //BA.debugLineNum = 419;BA.debugLine="dd.EventName = \"RetrieveOrgs\"";
_dd.EventName /*String*/  = "RetrieveOrgs";
 //BA.debugLineNum = 420;BA.debugLine="dd.Target = Me";
_dd.Target /*Object*/  = frmeditprofile.getObject();
 //BA.debugLineNum = 421;BA.debugLine="CallSubDelayed2(DownloadService, \"StartDownload\",";
anywheresoftware.b4a.keywords.Common.CallSubDelayed2(processBA,(Object)(mostCurrent._downloadservice.getObject()),"StartDownload",(Object)(_dd));
 //BA.debugLineNum = 423;BA.debugLine="End Sub";
return "";
}
public static String  _checkinternet() throws Exception{
appear.pnud.preservamos.downloadservice._downloaddata _dd = null;
 //BA.debugLineNum = 246;BA.debugLine="Sub CheckInternet";
 //BA.debugLineNum = 247;BA.debugLine="Dim dd As DownloadData";
_dd = new appear.pnud.preservamos.downloadservice._downloaddata();
 //BA.debugLineNum = 248;BA.debugLine="dd.url = Main.serverPath &  \"/\" & Main.serverConn";
_dd.url /*String*/  = mostCurrent._main._serverpath /*String*/ +"/"+mostCurrent._main._serverconnectionfolder /*String*/ +"/connecttest.php";
 //BA.debugLineNum = 249;BA.debugLine="dd.EventName = \"TestInternet\"";
_dd.EventName /*String*/  = "TestInternet";
 //BA.debugLineNum = 250;BA.debugLine="dd.Target = Me";
_dd.Target /*Object*/  = frmeditprofile.getObject();
 //BA.debugLineNum = 251;BA.debugLine="CallSubDelayed2(DownloadService, \"StartDownload\",";
anywheresoftware.b4a.keywords.Common.CallSubDelayed2(processBA,(Object)(mostCurrent._downloadservice.getObject()),"StartDownload",(Object)(_dd));
 //BA.debugLineNum = 252;BA.debugLine="End Sub";
return "";
}
public static String  _chkempleadomunicipio_checkedchange(boolean _checked) throws Exception{
 //BA.debugLineNum = 179;BA.debugLine="Private Sub chkEmpleadoMunicipio_CheckedChange(Che";
 //BA.debugLineNum = 180;BA.debugLine="hubocambios = True";
_hubocambios = anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 182;BA.debugLine="If chkEmpleadoMunicipio.Checked = True Then";
if (mostCurrent._chkempleadomunicipio.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 183;BA.debugLine="Main.strUserTipoUsuario = \"municipal\"";
mostCurrent._main._strusertipousuario /*String*/  = "municipal";
 }else {
 //BA.debugLineNum = 185;BA.debugLine="Main.strUserTipoUsuario = \"no municipal\"";
mostCurrent._main._strusertipousuario /*String*/  = "no municipal";
 };
 //BA.debugLineNum = 187;BA.debugLine="End Sub";
return "";
}
public static String  _edituser() throws Exception{
appear.pnud.preservamos.downloadservice._downloaddata _dd = null;
 //BA.debugLineNum = 281;BA.debugLine="Sub EditUser";
 //BA.debugLineNum = 292;BA.debugLine="Main.userSexo = spnSexo.SelectedItem";
mostCurrent._main._usersexo /*String*/  = mostCurrent._spnsexo.getSelectedItem();
 //BA.debugLineNum = 293;BA.debugLine="Main.userFechadeNacimiento = lblFechadeNacimiento";
mostCurrent._main._userfechadenacimiento /*String*/  = mostCurrent._lblfechadenacimiento.getText();
 //BA.debugLineNum = 294;BA.debugLine="Main.strUserFullName = txtFullName.Text";
mostCurrent._main._struserfullname /*String*/  = mostCurrent._txtfullname.getText();
 //BA.debugLineNum = 297;BA.debugLine="If chkEmpleadoMunicipio.Checked = True Then";
if (mostCurrent._chkempleadomunicipio.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 298;BA.debugLine="Main.strUserTipoUsuario = \"municipal\"";
mostCurrent._main._strusertipousuario /*String*/  = "municipal";
 }else {
 //BA.debugLineNum = 300;BA.debugLine="Main.strUserTipoUsuario = \"no municipal\"";
mostCurrent._main._strusertipousuario /*String*/  = "no municipal";
 };
 //BA.debugLineNum = 303;BA.debugLine="Dim dd As DownloadData";
_dd = new appear.pnud.preservamos.downloadservice._downloaddata();
 //BA.debugLineNum = 306;BA.debugLine="dd.url = Main.serverPath & \"/\" & Main.serverConn";
_dd.url /*String*/  = mostCurrent._main._serverpath /*String*/ +"/"+mostCurrent._main._serverconnectionfolder /*String*/ +"/editUser.php?Action=EditPass&"+"UserID="+mostCurrent._main._username /*String*/ +"&"+"useremail="+mostCurrent._main._struseremail /*String*/ +"&"+"FullName="+mostCurrent._txtfullname.getText()+"&"+"Location="+mostCurrent._txtlocation.getText()+"&"+"userOrg="+mostCurrent._main._struserorg /*String*/ +"&"+"userTipoUsuario="+mostCurrent._main._strusertipousuario /*String*/ +"&"+"fechadenacimiento="+mostCurrent._lblfechadenacimiento.getText()+"&"+"sexo="+mostCurrent._spnsexo.getSelectedItem()+"&"+"deviceID="+mostCurrent._main._deviceid /*String*/ ;
 //BA.debugLineNum = 329;BA.debugLine="dd.EventName = \"EditUser\"";
_dd.EventName /*String*/  = "EditUser";
 //BA.debugLineNum = 330;BA.debugLine="dd.Target = Me";
_dd.Target /*Object*/  = frmeditprofile.getObject();
 //BA.debugLineNum = 331;BA.debugLine="CallSubDelayed2(DownloadService, \"StartDownload\",";
anywheresoftware.b4a.keywords.Common.CallSubDelayed2(processBA,(Object)(mostCurrent._downloadservice.getObject()),"StartDownload",(Object)(_dd));
 //BA.debugLineNum = 333;BA.debugLine="End Sub";
return "";
}
public static String  _edituser_complete(appear.pnud.preservamos.httpjob _job) throws Exception{
String _res = "";
String _action = "";
anywheresoftware.b4a.objects.collections.JSONParser _parser = null;
anywheresoftware.b4a.objects.collections.Map _map1 = null;
 //BA.debugLineNum = 334;BA.debugLine="Sub EditUser_Complete(Job As HttpJob)";
 //BA.debugLineNum = 335;BA.debugLine="Log(\"EditUser user: \" & Job.Success)";
anywheresoftware.b4a.keywords.Common.LogImpl("445678593","EditUser user: "+BA.ObjectToString(_job._success /*boolean*/ ),0);
 //BA.debugLineNum = 336;BA.debugLine="If Job.Success = True Then";
if (_job._success /*boolean*/ ==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 338;BA.debugLine="Dim res As String, action As String";
_res = "";
_action = "";
 //BA.debugLineNum = 339;BA.debugLine="res = Job.GetString";
_res = _job._getstring /*String*/ ();
 //BA.debugLineNum = 340;BA.debugLine="Dim parser As JSONParser";
_parser = new anywheresoftware.b4a.objects.collections.JSONParser();
 //BA.debugLineNum = 341;BA.debugLine="parser.Initialize(res)";
_parser.Initialize(_res);
 //BA.debugLineNum = 342;BA.debugLine="action = parser.NextValue";
_action = BA.ObjectToString(_parser.NextValue());
 //BA.debugLineNum = 343;BA.debugLine="If action = \"EditOK\" Then";
if ((_action).equals("EditOK")) { 
 //BA.debugLineNum = 345;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 346;BA.debugLine="ToastMessageShow(\"Perfil editado\", False)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Perfil editado"),anywheresoftware.b4a.keywords.Common.False);
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 348;BA.debugLine="ToastMessageShow(\"Profile edited\", False)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Profile edited"),anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 351;BA.debugLine="Main.strUserFullName = txtFullName.Text";
mostCurrent._main._struserfullname /*String*/  = mostCurrent._txtfullname.getText();
 //BA.debugLineNum = 352;BA.debugLine="Main.strUserLocation = txtLocation.Text";
mostCurrent._main._struserlocation /*String*/  = mostCurrent._txtlocation.getText();
 //BA.debugLineNum = 353;BA.debugLine="Main.strUserTipoUsuario = spnPerfil.SelectedIte";
mostCurrent._main._strusertipousuario /*String*/  = mostCurrent._spnperfil.getSelectedItem();
 //BA.debugLineNum = 356;BA.debugLine="Dim Map1 As Map";
_map1 = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 357;BA.debugLine="Map1.Initialize";
_map1.Initialize();
 //BA.debugLineNum = 358;BA.debugLine="Map1.Put(\"useremail\", Main.strUserEmail)";
_map1.Put((Object)("useremail"),(Object)(mostCurrent._main._struseremail /*String*/ ));
 //BA.debugLineNum = 359;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"userconfig";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"userconfig","lastuser",(Object)("si"),_map1);
 //BA.debugLineNum = 360;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"userconfig";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"userconfig","userLocation",(Object)(mostCurrent._main._struserlocation /*String*/ ),_map1);
 //BA.debugLineNum = 361;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"userconfig";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"userconfig","username",(Object)(mostCurrent._main._struserfullname /*String*/ ),_map1);
 //BA.debugLineNum = 362;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"userconfig";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"userconfig","userOrg",(Object)(mostCurrent._main._struserorg /*String*/ ),_map1);
 //BA.debugLineNum = 363;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"userconfig";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"userconfig","userTipoUsuario",(Object)(mostCurrent._main._strusertipousuario /*String*/ ),_map1);
 //BA.debugLineNum = 364;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"userconfig";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"userconfig","fechadenacimiento",(Object)(mostCurrent._main._userfechadenacimiento /*String*/ ),_map1);
 //BA.debugLineNum = 365;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"userconfig";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"userconfig","sexo",(Object)(mostCurrent._main._usersexo /*String*/ ),_map1);
 //BA.debugLineNum = 370;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 371;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 }else {
 //BA.debugLineNum = 373;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 374;BA.debugLine="MsgboxAsync(\"El servidor no devolvió los valor";
anywheresoftware.b4a.keywords.Common.MsgboxAsync(BA.ObjectToCharSequence("El servidor no devolvió los valores esperados."+_job._errormessage /*String*/ ),BA.ObjectToCharSequence("Error"),processBA);
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 376;BA.debugLine="MsgboxAsync(\"Server error.\" & Job.ErrorMessage";
anywheresoftware.b4a.keywords.Common.MsgboxAsync(BA.ObjectToCharSequence("Server error."+_job._errormessage /*String*/ ),BA.ObjectToCharSequence("Error"),processBA);
 };
 };
 };
 //BA.debugLineNum = 380;BA.debugLine="Job.Release";
_job._release /*String*/ ();
 //BA.debugLineNum = 381;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 //BA.debugLineNum = 382;BA.debugLine="End Sub";
return "";
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 10;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 11;BA.debugLine="Private txtPassword As EditText";
mostCurrent._txtpassword = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 12;BA.debugLine="Private txtPassword2 As EditText";
mostCurrent._txtpassword2 = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 13;BA.debugLine="Private txtFullName As EditText";
mostCurrent._txtfullname = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 14;BA.debugLine="Private txtEmail As EditText";
mostCurrent._txtemail = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 15;BA.debugLine="Private txtLocation As EditText";
mostCurrent._txtlocation = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 16;BA.debugLine="Private lblUnmaskPass As Label";
mostCurrent._lblunmaskpass = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 17;BA.debugLine="Private lblTitle As Label";
mostCurrent._lbltitle = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 18;BA.debugLine="Private btnEditPassword As Button";
mostCurrent._btneditpassword = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 21;BA.debugLine="Private spnPerfil As Spinner";
mostCurrent._spnperfil = new anywheresoftware.b4a.objects.SpinnerWrapper();
 //BA.debugLineNum = 22;BA.debugLine="Private spnOrganizaciones As Spinner";
mostCurrent._spnorganizaciones = new anywheresoftware.b4a.objects.SpinnerWrapper();
 //BA.debugLineNum = 23;BA.debugLine="Private lblOrg As Label";
mostCurrent._lblorg = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 24;BA.debugLine="Private lblAgregarOrg As Label";
mostCurrent._lblagregarorg = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 26;BA.debugLine="Private spnSexo As Spinner";
mostCurrent._spnsexo = new anywheresoftware.b4a.objects.SpinnerWrapper();
 //BA.debugLineNum = 28;BA.debugLine="Dim nombreOrg As String";
mostCurrent._nombreorg = "";
 //BA.debugLineNum = 29;BA.debugLine="Dim idOrg As String";
mostCurrent._idorg = "";
 //BA.debugLineNum = 30;BA.debugLine="Dim localidadOrg As String";
mostCurrent._localidadorg = "";
 //BA.debugLineNum = 31;BA.debugLine="Dim provinciaOrg As String";
mostCurrent._provinciaorg = "";
 //BA.debugLineNum = 32;BA.debugLine="Dim tipoOrg As String";
mostCurrent._tipoorg = "";
 //BA.debugLineNum = 33;BA.debugLine="Dim adminOrg As String";
mostCurrent._adminorg = "";
 //BA.debugLineNum = 34;BA.debugLine="Dim idOrgElegida As String";
mostCurrent._idorgelegida = "";
 //BA.debugLineNum = 35;BA.debugLine="Dim listaOrgs As Map";
mostCurrent._listaorgs = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 38;BA.debugLine="Dim dated As DateDialog";
mostCurrent._dated = new anywheresoftware.b4a.agraham.dialogs.InputDialog.DateDialog();
 //BA.debugLineNum = 42;BA.debugLine="Private lblFullName As Label";
mostCurrent._lblfullname = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 43;BA.debugLine="Private lblLocation As Label";
mostCurrent._lbllocation = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 44;BA.debugLine="Private lblPerfil As Label";
mostCurrent._lblperfil = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 45;BA.debugLine="Private lblCambiarPass As Label";
mostCurrent._lblcambiarpass = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 46;BA.debugLine="Private btnCancelarEdit As Button";
mostCurrent._btncancelaredit = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 47;BA.debugLine="Private btnEditProfile As Button";
mostCurrent._btneditprofile = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 50;BA.debugLine="Dim hubocambios As Boolean";
_hubocambios = false;
 //BA.debugLineNum = 52;BA.debugLine="Private lblFechadeNacimiento As Label";
mostCurrent._lblfechadenacimiento = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 53;BA.debugLine="Private spnSexo As Spinner";
mostCurrent._spnsexo = new anywheresoftware.b4a.objects.SpinnerWrapper();
 //BA.debugLineNum = 54;BA.debugLine="Private btnEditFullName As Button";
mostCurrent._btneditfullname = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 55;BA.debugLine="Private btnEditSexo As Button";
mostCurrent._btneditsexo = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 56;BA.debugLine="Private btnEditFechaDeNacimiento As Button";
mostCurrent._btneditfechadenacimiento = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 57;BA.debugLine="Private chkEmpleadoMunicipio As CheckBox";
mostCurrent._chkempleadomunicipio = new anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper();
 //BA.debugLineNum = 58;BA.debugLine="Private btnEditEmpleadoMunicipal As Button";
mostCurrent._btneditempleadomunicipal = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 59;BA.debugLine="End Sub";
return "";
}
public static void  _lblagregarorg_click() throws Exception{
ResumableSub_lblAgregarOrg_Click rsub = new ResumableSub_lblAgregarOrg_Click(null);
rsub.resume(processBA, null);
}
public static class ResumableSub_lblAgregarOrg_Click extends BA.ResumableSub {
public ResumableSub_lblAgregarOrg_Click(appear.pnud.preservamos.frmeditprofile parent) {
this.parent = parent;
}
appear.pnud.preservamos.frmeditprofile parent;
int _result = 0;
anywheresoftware.b4a.phone.Phone.PhoneIntents _p = null;

@Override
public void resume(BA ba, Object[] result) throws Exception{

    while (true) {
        switch (state) {
            case -1:
return;

case 0:
//C
this.state = 1;
 //BA.debugLineNum = 464;BA.debugLine="If Main.lang = \"es\" Then";
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
 //BA.debugLineNum = 465;BA.debugLine="Msgbox2Async(\"Las organizaciones (grupos, escuel";
anywheresoftware.b4a.keywords.Common.Msgbox2Async(BA.ObjectToCharSequence("Las organizaciones (grupos, escuelas, empresas, ONGs) utilizan PreserVamos en grupo. Para registrar una nueva, debes contactarte con el equipo PreserVamos mediante el formulario en la web"),BA.ObjectToCharSequence("Grupos"),"Ir al formulario","","Volver",(anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper(), (android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null)),processBA,anywheresoftware.b4a.keywords.Common.False);
 if (true) break;

case 5:
//C
this.state = 6;
 //BA.debugLineNum = 467;BA.debugLine="Msgbox2Async(\"To use PreserVamos as a group of p";
anywheresoftware.b4a.keywords.Common.Msgbox2Async(BA.ObjectToCharSequence("To use PreserVamos as a group of people (schools, organizations, NGOs) you have to contact PreserVamos through the web form"),BA.ObjectToCharSequence("Groups"),"Go to the form","","Volver",(anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper(), (android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null)),processBA,anywheresoftware.b4a.keywords.Common.False);
 if (true) break;

case 6:
//C
this.state = 7;
;
 //BA.debugLineNum = 469;BA.debugLine="Wait For Msgbox_Result (Result As Int)";
anywheresoftware.b4a.keywords.Common.WaitFor("msgbox_result", processBA, this, null);
this.state = 11;
return;
case 11:
//C
this.state = 7;
_result = (Integer) result[0];
;
 //BA.debugLineNum = 470;BA.debugLine="If Result = DialogResponse.POSITIVE Then";
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
 //BA.debugLineNum = 471;BA.debugLine="Dim p As PhoneIntents";
_p = new anywheresoftware.b4a.phone.Phone.PhoneIntents();
 //BA.debugLineNum = 472;BA.debugLine="StartActivity(p.OpenBrowser(\"https://preservamo";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(_p.OpenBrowser("https://preservamos.ar/")));
 if (true) break;

case 10:
//C
this.state = -1;
;
 //BA.debugLineNum = 476;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public static void  _msgbox_result(int _result) throws Exception{
}
public static String  _lblunmaskpass_click() throws Exception{
 //BA.debugLineNum = 165;BA.debugLine="Sub lblUnmaskPass_Click";
 //BA.debugLineNum = 166;BA.debugLine="If lblUnmaskPass.Text = \"\" Then";
if ((mostCurrent._lblunmaskpass.getText()).equals("")) { 
 //BA.debugLineNum = 167;BA.debugLine="txtPassword.PasswordMode = True";
mostCurrent._txtpassword.setPasswordMode(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 168;BA.debugLine="txtPassword2.PasswordMode = True";
mostCurrent._txtpassword2.setPasswordMode(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 169;BA.debugLine="lblUnmaskPass.Text = \"\"";
mostCurrent._lblunmaskpass.setText(BA.ObjectToCharSequence(""));
 }else if((mostCurrent._lblunmaskpass.getText()).equals("")) { 
 //BA.debugLineNum = 171;BA.debugLine="txtPassword.PasswordMode = False";
mostCurrent._txtpassword.setPasswordMode(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 172;BA.debugLine="txtPassword2.PasswordMode = False";
mostCurrent._txtpassword2.setPasswordMode(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 173;BA.debugLine="lblUnmaskPass.Text = \"\"";
mostCurrent._lblunmaskpass.setText(BA.ObjectToCharSequence(""));
 };
 //BA.debugLineNum = 177;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 6;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 8;BA.debugLine="End Sub";
return "";
}
public static String  _retrieveorgs_complete(appear.pnud.preservamos.httpjob _job) throws Exception{
String _res = "";
String _action = "";
anywheresoftware.b4a.objects.collections.JSONParser _parser = null;
String _numresults = "";
int _i = 0;
anywheresoftware.b4a.objects.collections.Map _newpunto = null;
 //BA.debugLineNum = 424;BA.debugLine="Sub RetrieveOrgs_Complete(Job As HttpJob)";
 //BA.debugLineNum = 425;BA.debugLine="Log(\"Cargagrupos: \" & Job.Success)";
anywheresoftware.b4a.keywords.Common.LogImpl("445875201","Cargagrupos: "+BA.ObjectToString(_job._success /*boolean*/ ),0);
 //BA.debugLineNum = 426;BA.debugLine="If Job.Success = True Then";
if (_job._success /*boolean*/ ==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 427;BA.debugLine="Dim res As String, action As String";
_res = "";
_action = "";
 //BA.debugLineNum = 428;BA.debugLine="res = Job.GetString";
_res = _job._getstring /*String*/ ();
 //BA.debugLineNum = 429;BA.debugLine="Dim parser As JSONParser";
_parser = new anywheresoftware.b4a.objects.collections.JSONParser();
 //BA.debugLineNum = 430;BA.debugLine="parser.Initialize(res)";
_parser.Initialize(_res);
 //BA.debugLineNum = 431;BA.debugLine="action = parser.NextValue";
_action = BA.ObjectToString(_parser.NextValue());
 //BA.debugLineNum = 432;BA.debugLine="If action = \"OrgsOK\" Then";
if ((_action).equals("OrgsOK")) { 
 //BA.debugLineNum = 433;BA.debugLine="Dim numresults As String";
_numresults = "";
 //BA.debugLineNum = 434;BA.debugLine="numresults = parser.NextValue";
_numresults = BA.ObjectToString(_parser.NextValue());
 //BA.debugLineNum = 436;BA.debugLine="For i = 0 To numresults - 1";
{
final int step11 = 1;
final int limit11 = (int) ((double)(Double.parseDouble(_numresults))-1);
_i = (int) (0) ;
for (;_i <= limit11 ;_i = _i + step11 ) {
 //BA.debugLineNum = 437;BA.debugLine="Dim newpunto As Map";
_newpunto = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 438;BA.debugLine="newpunto = parser.NextObject";
_newpunto = _parser.NextObject();
 //BA.debugLineNum = 439;BA.debugLine="idOrg = newpunto.Get(\"id\")";
mostCurrent._idorg = BA.ObjectToString(_newpunto.Get((Object)("id")));
 //BA.debugLineNum = 440;BA.debugLine="nombreOrg = newpunto.Get(\"nombre\")";
mostCurrent._nombreorg = BA.ObjectToString(_newpunto.Get((Object)("nombre")));
 //BA.debugLineNum = 441;BA.debugLine="localidadOrg = newpunto.Get(\"localidad\")";
mostCurrent._localidadorg = BA.ObjectToString(_newpunto.Get((Object)("localidad")));
 //BA.debugLineNum = 442;BA.debugLine="provinciaOrg = newpunto.Get(\"provincia\")";
mostCurrent._provinciaorg = BA.ObjectToString(_newpunto.Get((Object)("provincia")));
 //BA.debugLineNum = 443;BA.debugLine="tipoOrg = newpunto.Get(\"tipo\")";
mostCurrent._tipoorg = BA.ObjectToString(_newpunto.Get((Object)("tipo")));
 //BA.debugLineNum = 444;BA.debugLine="adminOrg = newpunto.Get(\"admin\")";
mostCurrent._adminorg = BA.ObjectToString(_newpunto.Get((Object)("admin")));
 //BA.debugLineNum = 445;BA.debugLine="spnOrganizaciones.Add(nombreOrg)";
mostCurrent._spnorganizaciones.Add(mostCurrent._nombreorg);
 //BA.debugLineNum = 446;BA.debugLine="listaOrgs.Put(nombreOrg, idOrg)";
mostCurrent._listaorgs.Put((Object)(mostCurrent._nombreorg),(Object)(mostCurrent._idorg));
 //BA.debugLineNum = 447;BA.debugLine="spnOrganizaciones.Enabled = True";
mostCurrent._spnorganizaciones.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 }
};
 //BA.debugLineNum = 449;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 }else {
 //BA.debugLineNum = 452;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 453;BA.debugLine="MsgboxAsync(\"El servidor no devolvió los valor";
anywheresoftware.b4a.keywords.Common.MsgboxAsync(BA.ObjectToCharSequence("El servidor no devolvió los valores esperados."+_job._errormessage /*String*/ ),BA.ObjectToCharSequence("Registro"),processBA);
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 455;BA.debugLine="MsgboxAsync(\"Server error.\" & Job.ErrorMessage";
anywheresoftware.b4a.keywords.Common.MsgboxAsync(BA.ObjectToCharSequence("Server error."+_job._errormessage /*String*/ ),BA.ObjectToCharSequence("Error"),processBA);
 };
 };
 };
 //BA.debugLineNum = 459;BA.debugLine="Job.Release";
_job._release /*String*/ ();
 //BA.debugLineNum = 460;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 //BA.debugLineNum = 461;BA.debugLine="End Sub";
return "";
}
public static String  _spnperfil_itemclick(int _position,Object _value) throws Exception{
 //BA.debugLineNum = 393;BA.debugLine="Sub spnPerfil_ItemClick (Position As Int, Value As";
 //BA.debugLineNum = 394;BA.debugLine="hubocambios = True";
_hubocambios = anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 395;BA.debugLine="If spnPerfil.SelectedItem = \"Grupo\" Or spnPerfil.";
if ((mostCurrent._spnperfil.getSelectedItem()).equals("Grupo") || (mostCurrent._spnperfil.getSelectedItem()).equals("Group")) { 
 //BA.debugLineNum = 396;BA.debugLine="lblOrg.Visible = True";
mostCurrent._lblorg.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 397;BA.debugLine="lblAgregarOrg.Visible = True";
mostCurrent._lblagregarorg.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 398;BA.debugLine="spnOrganizaciones.Visible = True";
mostCurrent._spnorganizaciones.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 399;BA.debugLine="spnOrganizaciones.Clear";
mostCurrent._spnorganizaciones.Clear();
 //BA.debugLineNum = 400;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 401;BA.debugLine="ProgressDialogShow(\"Buscando grupos...\")";
anywheresoftware.b4a.keywords.Common.ProgressDialogShow(mostCurrent.activityBA,BA.ObjectToCharSequence("Buscando grupos..."));
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 403;BA.debugLine="ProgressDialogShow(\"Retrieving groups...\")";
anywheresoftware.b4a.keywords.Common.ProgressDialogShow(mostCurrent.activityBA,BA.ObjectToCharSequence("Retrieving groups..."));
 };
 //BA.debugLineNum = 405;BA.debugLine="CargarGrupos";
_cargargrupos();
 //BA.debugLineNum = 407;BA.debugLine="Return";
if (true) return "";
 }else {
 //BA.debugLineNum = 409;BA.debugLine="lblOrg.Visible = False";
mostCurrent._lblorg.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 410;BA.debugLine="lblAgregarOrg.Visible = False";
mostCurrent._lblagregarorg.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 411;BA.debugLine="spnOrganizaciones.Visible = False";
mostCurrent._spnorganizaciones.setVisible(anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 414;BA.debugLine="End Sub";
return "";
}
public static String  _spnsexo_itemclick(int _position,Object _value) throws Exception{
 //BA.debugLineNum = 479;BA.debugLine="Sub spnSexo_ItemClick(Position As Int, Value As Ob";
 //BA.debugLineNum = 480;BA.debugLine="hubocambios = True";
_hubocambios = anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 482;BA.debugLine="Log(\"sexo=\" & Main.userSexo)";
anywheresoftware.b4a.keywords.Common.LogImpl("446006275","sexo="+mostCurrent._main._usersexo /*String*/ ,0);
 //BA.debugLineNum = 483;BA.debugLine="End Sub";
return "";
}
public static String  _testinternet_complete(appear.pnud.preservamos.httpjob _job) throws Exception{
 //BA.debugLineNum = 255;BA.debugLine="Sub TestInternet_Complete(Job As HttpJob)";
 //BA.debugLineNum = 256;BA.debugLine="Log(\"Chequeo de internet: \" & Job.Success)";
anywheresoftware.b4a.keywords.Common.LogImpl("445547521","Chequeo de internet: "+BA.ObjectToString(_job._success /*boolean*/ ),0);
 //BA.debugLineNum = 257;BA.debugLine="If Job.Success = True Then";
if (_job._success /*boolean*/ ==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 259;BA.debugLine="Main.hayinternet = True";
mostCurrent._main._hayinternet /*boolean*/  = anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 261;BA.debugLine="Job.Release";
_job._release /*String*/ ();
 //BA.debugLineNum = 263;BA.debugLine="EditUser";
_edituser();
 }else {
 //BA.debugLineNum = 268;BA.debugLine="Main.hayinternet = False";
mostCurrent._main._hayinternet /*boolean*/  = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 269;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 270;BA.debugLine="MsgboxAsync(\"No hay conexión a internet, prueba";
anywheresoftware.b4a.keywords.Common.MsgboxAsync(BA.ObjectToCharSequence("No hay conexión a internet, prueba cuando estés conectado!"),BA.ObjectToCharSequence("No hay internet"),processBA);
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 272;BA.debugLine="MsgboxAsync(\"There is no internet, try again wh";
anywheresoftware.b4a.keywords.Common.MsgboxAsync(BA.ObjectToCharSequence("There is no internet, try again when you're connected!"),BA.ObjectToCharSequence("No internet"),processBA);
 };
 //BA.debugLineNum = 274;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 //BA.debugLineNum = 275;BA.debugLine="Job.Release";
_job._release /*String*/ ();
 //BA.debugLineNum = 276;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 277;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 };
 //BA.debugLineNum = 279;BA.debugLine="End Sub";
return "";
}
}
