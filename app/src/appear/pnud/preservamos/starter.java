package appear.pnud.preservamos;


import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.objects.ServiceHelper;
import anywheresoftware.b4a.debug.*;

public class starter extends android.app.Service{
	public static class starter_BR extends android.content.BroadcastReceiver {

		@Override
		public void onReceive(android.content.Context context, android.content.Intent intent) {
            BA.LogInfo("** Receiver (starter) OnReceive **");
			android.content.Intent in = new android.content.Intent(context, starter.class);
			if (intent != null)
				in.putExtra("b4a_internal_intent", intent);
            ServiceHelper.StarterHelper.startServiceFromReceiver (context, in, true, BA.class);
		}

	}
    static starter mostCurrent;
	public static BA processBA;
    private ServiceHelper _service;
    public static Class<?> getObject() {
		return starter.class;
	}
	@Override
	public void onCreate() {
        super.onCreate();
        mostCurrent = this;
        if (processBA == null) {
		    processBA = new BA(this, null, null, "appear.pnud.preservamos", "appear.pnud.preservamos.starter");
            if (BA.isShellModeRuntimeCheck(processBA)) {
                processBA.raiseEvent2(null, true, "SHELL", false);
		    }
            try {
                Class.forName(BA.applicationContext.getPackageName() + ".main").getMethod("initializeProcessGlobals").invoke(null, null);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            processBA.loadHtSubs(this.getClass());
            ServiceHelper.init();
        }
        _service = new ServiceHelper(this);
        processBA.service = this;
        
        if (BA.isShellModeRuntimeCheck(processBA)) {
			processBA.raiseEvent2(null, true, "CREATE", true, "appear.pnud.preservamos.starter", processBA, _service, anywheresoftware.b4a.keywords.Common.Density);
		}
        if (!true && ServiceHelper.StarterHelper.startFromServiceCreate(processBA, false) == false) {
				
		}
		else {
            processBA.setActivityPaused(false);
            BA.LogInfo("*** Service (starter) Create ***");
            processBA.raiseEvent(null, "service_create");
        }
        processBA.runHook("oncreate", this, null);
        if (true) {
			ServiceHelper.StarterHelper.runWaitForLayouts();
		}
    }
		@Override
	public void onStart(android.content.Intent intent, int startId) {
		onStartCommand(intent, 0, 0);
    }
    @Override
    public int onStartCommand(final android.content.Intent intent, int flags, int startId) {
    	if (ServiceHelper.StarterHelper.onStartCommand(processBA, new Runnable() {
            public void run() {
                handleStart(intent);
            }}))
			;
		else {
			ServiceHelper.StarterHelper.addWaitForLayout (new Runnable() {
				public void run() {
                    processBA.setActivityPaused(false);
                    BA.LogInfo("** Service (starter) Create **");
                    processBA.raiseEvent(null, "service_create");
					handleStart(intent);
                    ServiceHelper.StarterHelper.removeWaitForLayout();
				}
			});
		}
        processBA.runHook("onstartcommand", this, new Object[] {intent, flags, startId});
		return android.app.Service.START_NOT_STICKY;
    }
    public void onTaskRemoved(android.content.Intent rootIntent) {
        super.onTaskRemoved(rootIntent);
        if (true)
            processBA.raiseEvent(null, "service_taskremoved");
            
    }
    private void handleStart(android.content.Intent intent) {
    	BA.LogInfo("** Service (starter) Start **");
    	java.lang.reflect.Method startEvent = processBA.htSubs.get("service_start");
    	if (startEvent != null) {
    		if (startEvent.getParameterTypes().length > 0) {
    			anywheresoftware.b4a.objects.IntentWrapper iw = ServiceHelper.StarterHelper.handleStartIntent(intent, _service, processBA);
    			processBA.raiseEvent(null, "service_start", iw);
    		}
    		else {
    			processBA.raiseEvent(null, "service_start");
    		}
    	}
    }
	
	@Override
	public void onDestroy() {
        super.onDestroy();
        if (true) {
            BA.LogInfo("** Service (starter) Destroy (ignored)**");
        }
        else {
            BA.LogInfo("** Service (starter) Destroy **");
		    processBA.raiseEvent(null, "service_destroy");
            processBA.service = null;
		    mostCurrent = null;
		    processBA.setActivityPaused(true);
            processBA.runHook("ondestroy", this, null);
        }
	}

@Override
	public android.os.IBinder onBind(android.content.Intent intent) {
		return null;
	}public anywheresoftware.b4a.keywords.Common __c = null;
public static String _savedir = "";
public static anywheresoftware.b4a.sql.SQL _sqldb = null;
public static anywheresoftware.b4a.sql.SQL _modulo_residuos_sqldb = null;
public static anywheresoftware.b4a.sql.SQL _modulo_hidro_sqldb = null;
public static String _dbdir = "";
public static String _modulo_residuos_dbdir = "";
public static String _modulo_hidro_dbdir = "";
public static anywheresoftware.b4a.objects.FirebaseAuthWrapper _auth = null;
public static boolean _isloggedin = false;
public static anywheresoftware.b4a.objects.FirebaseNotificationsService.FirebaseMessageWrapper _fm = null;
public static anywheresoftware.b4a.objects.RuntimePermissions _rp = null;
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
public appear.pnud.preservamos.reporte_habitat_laguna _reporte_habitat_laguna = null;
public appear.pnud.preservamos.reporte_habitat_rio _reporte_habitat_rio = null;
public appear.pnud.preservamos.reporte_habitat_rio_bu _reporte_habitat_rio_bu = null;
public appear.pnud.preservamos.reporte_habitat_rio_sierras _reporte_habitat_rio_sierras = null;
public appear.pnud.preservamos.uploadfiles _uploadfiles = null;
public appear.pnud.preservamos.utilidades _utilidades = null;
public appear.pnud.preservamos.xuiviewsutils _xuiviewsutils = null;
public static boolean  _application_error(anywheresoftware.b4a.objects.B4AException _error,String _stacktrace) throws Exception{
 //BA.debugLineNum = 74;BA.debugLine="Sub Application_Error (Error As Exception, StackTr";
 //BA.debugLineNum = 75;BA.debugLine="Return True";
if (true) return anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 76;BA.debugLine="End Sub";
return false;
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 7;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 9;BA.debugLine="Dim savedir As String";
_savedir = "";
 //BA.debugLineNum = 10;BA.debugLine="Dim sqlDB As SQL 'DB principal";
_sqldb = new anywheresoftware.b4a.sql.SQL();
 //BA.debugLineNum = 12;BA.debugLine="Dim modulo_residuos_sqlDB As SQL 'DB modulo:resid";
_modulo_residuos_sqldb = new anywheresoftware.b4a.sql.SQL();
 //BA.debugLineNum = 13;BA.debugLine="Dim modulo_hidro_sqlDB As SQL 'DB modulo:residuos";
_modulo_hidro_sqldb = new anywheresoftware.b4a.sql.SQL();
 //BA.debugLineNum = 14;BA.debugLine="Dim dbdir As String 'DB principal";
_dbdir = "";
 //BA.debugLineNum = 15;BA.debugLine="Dim modulo_residuos_dbdir As String 'DB modulo:re";
_modulo_residuos_dbdir = "";
 //BA.debugLineNum = 16;BA.debugLine="Dim modulo_hidro_dbdir As String 'DB modulo:resid";
_modulo_hidro_dbdir = "";
 //BA.debugLineNum = 20;BA.debugLine="Public auth As FirebaseAuth";
_auth = new anywheresoftware.b4a.objects.FirebaseAuthWrapper();
 //BA.debugLineNum = 21;BA.debugLine="Public isLoggedIn As Boolean";
_isloggedin = false;
 //BA.debugLineNum = 22;BA.debugLine="Dim fm As FirebaseMessaging";
_fm = new anywheresoftware.b4a.objects.FirebaseNotificationsService.FirebaseMessageWrapper();
 //BA.debugLineNum = 24;BA.debugLine="Dim rp As RuntimePermissions";
_rp = new anywheresoftware.b4a.objects.RuntimePermissions();
 //BA.debugLineNum = 25;BA.debugLine="End Sub";
return "";
}
public static String  _service_create() throws Exception{
 //BA.debugLineNum = 27;BA.debugLine="Sub Service_Create";
 //BA.debugLineNum = 45;BA.debugLine="dbdir = DBUtils.CopyDBFromAssets(\"preservamosdb.d";
_dbdir = mostCurrent._dbutils._copydbfromassets /*String*/ (processBA,"preservamosdb.db");
 //BA.debugLineNum = 46;BA.debugLine="sqlDB.Initialize(dbdir, \"preservamosdb.db\", False";
_sqldb.Initialize(_dbdir,"preservamosdb.db",anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 51;BA.debugLine="modulo_residuos_dbdir = DBUtils.CopyDBFromAssets(";
_modulo_residuos_dbdir = mostCurrent._dbutils._copydbfromassets /*String*/ (processBA,"preservamos_residuos_db.db");
 //BA.debugLineNum = 52;BA.debugLine="modulo_residuos_sqlDB.Initialize(modulo_residuos_";
_modulo_residuos_sqldb.Initialize(_modulo_residuos_dbdir,"preservamos_residuos_db.db",anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 54;BA.debugLine="modulo_hidro_dbdir = DBUtils.CopyDBFromAssets(\"pr";
_modulo_hidro_dbdir = mostCurrent._dbutils._copydbfromassets /*String*/ (processBA,"preservamos_hidro_db.db");
 //BA.debugLineNum = 55;BA.debugLine="modulo_hidro_sqlDB.Initialize(modulo_hidro_dbdir,";
_modulo_hidro_sqldb.Initialize(_modulo_hidro_dbdir,"preservamos_hidro_db.db",anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 57;BA.debugLine="CallSubDelayed(FirebaseMessaging, \"SubscribeToTop";
anywheresoftware.b4a.keywords.Common.CallSubDelayed(processBA,(Object)(mostCurrent._firebasemessaging.getObject()),"SubscribeToTopics");
 //BA.debugLineNum = 58;BA.debugLine="auth.Initialize(\"auth\")";
_auth.Initialize(processBA,"auth");
 //BA.debugLineNum = 59;BA.debugLine="auth.SignOutFromGoogle";
_auth.SignOutFromGoogle();
 //BA.debugLineNum = 61;BA.debugLine="fm.Initialize(\"fm\")";
_fm.Initialize(processBA,"fm");
 //BA.debugLineNum = 62;BA.debugLine="End Sub";
return "";
}
public static String  _service_destroy() throws Exception{
 //BA.debugLineNum = 78;BA.debugLine="Sub Service_Destroy";
 //BA.debugLineNum = 79;BA.debugLine="End Sub";
return "";
}
public static String  _service_start(anywheresoftware.b4a.objects.IntentWrapper _startingintent) throws Exception{
 //BA.debugLineNum = 64;BA.debugLine="Sub Service_Start (StartingIntent As Intent)";
 //BA.debugLineNum = 65;BA.debugLine="Service.StopAutomaticForeground 'Starter service";
mostCurrent._service.StopAutomaticForeground();
 //BA.debugLineNum = 66;BA.debugLine="End Sub";
return "";
}
public static String  _service_taskremoved() throws Exception{
 //BA.debugLineNum = 69;BA.debugLine="Sub Service_TaskRemoved";
 //BA.debugLineNum = 71;BA.debugLine="End Sub";
return "";
}
public static String  _updatefcmtoken() throws Exception{
 //BA.debugLineNum = 83;BA.debugLine="Public Sub UpdateFCMToken";
 //BA.debugLineNum = 85;BA.debugLine="fm.SubscribeToTopic(\"general\") 'you can subscribe";
_fm.SubscribeToTopic("general");
 //BA.debugLineNum = 86;BA.debugLine="Log (\"NewToken: \" & fm.Token)";
anywheresoftware.b4a.keywords.Common.LogImpl("497255427","NewToken: "+_fm.getToken(),0);
 //BA.debugLineNum = 87;BA.debugLine="End Sub";
return "";
}
}
