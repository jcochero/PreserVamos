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

public class frmpoliticadatos extends Activity implements B4AActivity{
	public static frmpoliticadatos mostCurrent;
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
			processBA = new BA(this.getApplicationContext(), null, null, "ilpla.appear", "ilpla.appear.frmpoliticadatos");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
            
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (frmpoliticadatos).");
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
		activityBA = new BA(this, layout, processBA, "ilpla.appear", "ilpla.appear.frmpoliticadatos");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "ilpla.appear.frmpoliticadatos", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density, mostCurrent);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (frmpoliticadatos) Create, isFirst = " + isFirst + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (frmpoliticadatos) Resume **");
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
		return frmpoliticadatos.class;
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
            BA.LogInfo("** Activity (frmpoliticadatos) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
        else
            BA.LogInfo("** Activity (frmpoliticadatos) Pause event (activity is not paused). **");
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
            frmpoliticadatos mc = mostCurrent;
			if (mc == null || mc != activity.get())
				return;
			processBA.setActivityPaused(false);
            BA.LogInfo("** Activity (frmpoliticadatos) Resume **");
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
public anywheresoftware.b4a.objects.PanelWrapper _pnlpoliticadatos = null;
public anywheresoftware.b4a.objects.ScrollViewWrapper _scrpoliticadatos = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblderechos = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbltitulo = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbltitulotipos = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbltipos = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbltitulocomo = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblcomo = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbltituloterceras = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblterceras = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbltituloderechos = null;
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
 //BA.debugLineNum = 27;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
 //BA.debugLineNum = 28;BA.debugLine="Activity.LoadLayout(\"layPoliticaDatos\")";
mostCurrent._activity.LoadLayout("layPoliticaDatos",mostCurrent.activityBA);
 //BA.debugLineNum = 30;BA.debugLine="pnlPoliticaDatos.RemoveView";
mostCurrent._pnlpoliticadatos.RemoveView();
 //BA.debugLineNum = 31;BA.debugLine="scrPoliticaDatos.Panel.AddView(pnlPoliticaDatos,0";
mostCurrent._scrpoliticadatos.getPanel().AddView((android.view.View)(mostCurrent._pnlpoliticadatos.getObject()),(int) (0),(int) (0),(int) (mostCurrent._lblderechos.getHeight()+mostCurrent._lblderechos.getTop()+mostCurrent._scrpoliticadatos.getTop()),mostCurrent._scrpoliticadatos.getPanel().getWidth());
 //BA.debugLineNum = 33;BA.debugLine="End Sub";
return "";
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 39;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 41;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 35;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 36;BA.debugLine="TranslateGUI";
_translategui();
 //BA.debugLineNum = 37;BA.debugLine="End Sub";
return "";
}
public static String  _btncerrarpoliticadatos_click() throws Exception{
 //BA.debugLineNum = 108;BA.debugLine="Sub btnCerrarPoliticaDatos_Click";
 //BA.debugLineNum = 109;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 110;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 111;BA.debugLine="End Sub";
return "";
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 10;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 11;BA.debugLine="Private pnlPoliticaDatos As Panel";
mostCurrent._pnlpoliticadatos = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 12;BA.debugLine="Private scrPoliticaDatos As ScrollView";
mostCurrent._scrpoliticadatos = new anywheresoftware.b4a.objects.ScrollViewWrapper();
 //BA.debugLineNum = 13;BA.debugLine="Private lblDerechos As Label";
mostCurrent._lblderechos = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 17;BA.debugLine="Private lblTitulo As Label";
mostCurrent._lbltitulo = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 18;BA.debugLine="Private lblTituloTipos As Label";
mostCurrent._lbltitulotipos = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 19;BA.debugLine="Private lblTipos As Label";
mostCurrent._lbltipos = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 20;BA.debugLine="Private lblTituloComo As Label";
mostCurrent._lbltitulocomo = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 21;BA.debugLine="Private lblComo As Label";
mostCurrent._lblcomo = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 22;BA.debugLine="Private lblTituloTerceras As Label";
mostCurrent._lbltituloterceras = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 23;BA.debugLine="Private lblTerceras As Label";
mostCurrent._lblterceras = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 24;BA.debugLine="Private lblTituloDerechos As Label";
mostCurrent._lbltituloderechos = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 25;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 6;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 8;BA.debugLine="End Sub";
return "";
}
public static String  _translategui() throws Exception{
 //BA.debugLineNum = 49;BA.debugLine="Sub TranslateGUI";
 //BA.debugLineNum = 50;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 51;BA.debugLine="lblTitulo.Text = \"   Política de datos\"";
mostCurrent._lbltitulo.setText(BA.ObjectToCharSequence("   Política de datos"));
 //BA.debugLineNum = 52;BA.debugLine="lblTituloTipos.Text = \"   Tipos de datos que re";
mostCurrent._lbltitulotipos.setText(BA.ObjectToCharSequence("   Tipos de datos que recolectamos"));
 //BA.debugLineNum = 53;BA.debugLine="lblTipos.Text = \"- Datos de contacto \" & CRLF &";
mostCurrent._lbltipos.setText(BA.ObjectToCharSequence("- Datos de contacto "+anywheresoftware.b4a.keywords.Common.CRLF+"- Datos de tu localización"+anywheresoftware.b4a.keywords.Common.CRLF+"- Datos que identifican tu dispositivo"+anywheresoftware.b4a.keywords.Common.CRLF+"- Fotografías que asocies a cada envío"+anywheresoftware.b4a.keywords.Common.CRLF+"- Tus respuestas a las encuestas"));
 //BA.debugLineNum = 58;BA.debugLine="lblTituloComo.Text = \"   ¿Para que usamos tus d";
mostCurrent._lbltitulocomo.setText(BA.ObjectToCharSequence("   ¿Para que usamos tus datos? Para..."));
 //BA.debugLineNum = 59;BA.debugLine="lblComo.Text = 	\"... la base de datos ambientale";
mostCurrent._lblcomo.setText(BA.ObjectToCharSequence("... la base de datos ambientales de PreserVamos"+anywheresoftware.b4a.keywords.Common.CRLF+"... el catálogo de imágenes de PreserVamos"+anywheresoftware.b4a.keywords.Common.CRLF+"... poder poner cada reporte en un mapa"+anywheresoftware.b4a.keywords.Common.CRLF+"... contactarte por temas relacionados a tu envío"+anywheresoftware.b4a.keywords.Common.CRLF+"... enviarte información sobre PreserVamos (si lo deseas solamente)"));
 //BA.debugLineNum = 64;BA.debugLine="lblTituloTerceras.Text = \"   Terceras partes qu";
mostCurrent._lbltituloterceras.setText(BA.ObjectToCharSequence("   Terceras partes que tendrán acceso a tus datos"));
 //BA.debugLineNum = 65;BA.debugLine="lblTerceras.Text = \"- Infraestructura: iPage, My";
mostCurrent._lblterceras.setText(BA.ObjectToCharSequence("- Infraestructura: iPage, MySQL"+anywheresoftware.b4a.keywords.Common.CRLF+"- Analíticas: Google Analytics"+anywheresoftware.b4a.keywords.Common.CRLF+"- Mapa: Leaflet"+anywheresoftware.b4a.keywords.Common.CRLF+"- Comunicaciones: Google Firebase"));
 //BA.debugLineNum = 69;BA.debugLine="lblTituloDerechos.Text = \"   Tus derechos\"";
mostCurrent._lbltituloderechos.setText(BA.ObjectToCharSequence("   Tus derechos"));
 //BA.debugLineNum = 70;BA.debugLine="lblDerechos.Text = \"- Acceder a tus datos de con";
mostCurrent._lblderechos.setText(BA.ObjectToCharSequence("- Acceder a tus datos de contacto que tenemos guardados"+anywheresoftware.b4a.keywords.Common.CRLF+"- Descargar, utilizar y publicar los datos que reunimos (citando la fuente!)"+anywheresoftware.b4a.keywords.Common.CRLF+"- Participar y discutir en las redes sociales del proyecto (con respeto siempre)"+anywheresoftware.b4a.keywords.Common.CRLF+"- Pedir que no te contactemos con noticias"+anywheresoftware.b4a.keywords.Common.CRLF+"- Marcar los reportes y fotografías que envíes como “privados”, y que no se muestren ni en el mapa ni en las bases de datos de PreserVamos"+anywheresoftware.b4a.keywords.Common.CRLF+"- Cerrar tu cuenta en PreserVamos"+anywheresoftware.b4a.keywords.Common.CRLF+"- Sugerir como mejorar el proyecto!"));
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 78;BA.debugLine="lblTitulo.Text = \"   Data usage policy\"";
mostCurrent._lbltitulo.setText(BA.ObjectToCharSequence("   Data usage policy"));
 //BA.debugLineNum = 79;BA.debugLine="lblTituloTipos.Text = \"   Types of data we coll";
mostCurrent._lbltitulotipos.setText(BA.ObjectToCharSequence("   Types of data we collect"));
 //BA.debugLineNum = 80;BA.debugLine="lblTipos.Text = \"- Contact information \" & CRLF";
mostCurrent._lbltipos.setText(BA.ObjectToCharSequence("- Contact information "+anywheresoftware.b4a.keywords.Common.CRLF+"- Localization data"+anywheresoftware.b4a.keywords.Common.CRLF+"- Information about your device"+anywheresoftware.b4a.keywords.Common.CRLF+"- Photos you take for each report"+anywheresoftware.b4a.keywords.Common.CRLF+"- Your responses to your reports"));
 //BA.debugLineNum = 85;BA.debugLine="lblTituloComo.Text = \"   What do we use the dat";
mostCurrent._lbltitulocomo.setText(BA.ObjectToCharSequence("   What do we use the data for? To..."));
 //BA.debugLineNum = 86;BA.debugLine="lblComo.Text = 	\"... feed the PreserVamos databa";
mostCurrent._lblcomo.setText(BA.ObjectToCharSequence("... feed the PreserVamos database"+anywheresoftware.b4a.keywords.Common.CRLF+"... feed the PreserVamos image catalog"+anywheresoftware.b4a.keywords.Common.CRLF+"... place each report in the map"+anywheresoftware.b4a.keywords.Common.CRLF+"... contact you regarding your report"+anywheresoftware.b4a.keywords.Common.CRLF+"... send you info about PreserVamos (only if you want)"));
 //BA.debugLineNum = 91;BA.debugLine="lblTituloTerceras.Text = \"   Third parties that";
mostCurrent._lbltituloterceras.setText(BA.ObjectToCharSequence("   Third parties that will have access to some of your data"));
 //BA.debugLineNum = 92;BA.debugLine="lblTerceras.Text = \"- Infrastructure: iPage, MyS";
mostCurrent._lblterceras.setText(BA.ObjectToCharSequence("- Infrastructure: iPage, MySQL"+anywheresoftware.b4a.keywords.Common.CRLF+"- Analytics: Google Analytics"+anywheresoftware.b4a.keywords.Common.CRLF+"- Map: Leaflet"+anywheresoftware.b4a.keywords.Common.CRLF+"- Comunications: Google Firebase"));
 //BA.debugLineNum = 96;BA.debugLine="lblTituloDerechos.Text = \"   Your rights\"";
mostCurrent._lbltituloderechos.setText(BA.ObjectToCharSequence("   Your rights"));
 //BA.debugLineNum = 97;BA.debugLine="lblDerechos.Text = \"- To access all the informat";
mostCurrent._lblderechos.setText(BA.ObjectToCharSequence("- To access all the information we have on you"+anywheresoftware.b4a.keywords.Common.CRLF+"- Download, use and publish all data we collect (by referencing the project!)"+anywheresoftware.b4a.keywords.Common.CRLF+"- Participate and discuss in the projects social networks"+anywheresoftware.b4a.keywords.Common.CRLF+"- Ask us not to contact you with any news"+anywheresoftware.b4a.keywords.Common.CRLF+"- Label your reports and photos as 'private' so they are not shown in the map or in the PreserVamos database"+anywheresoftware.b4a.keywords.Common.CRLF+"- Close your PreserVamos account"+anywheresoftware.b4a.keywords.Common.CRLF+"- Suggest us how to improve the project!"));
 };
 //BA.debugLineNum = 105;BA.debugLine="End Sub";
return "";
}
}
