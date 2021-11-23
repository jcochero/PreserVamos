package ilpla.appear;


import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.objects.ServiceHelper;
import anywheresoftware.b4a.debug.*;

public class starter extends  android.app.Service{
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
		    processBA = new BA(this, null, null, "ilpla.appear", "ilpla.appear.starter");
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
			processBA.raiseEvent2(null, true, "CREATE", true, "ilpla.appear.starter", processBA, _service, anywheresoftware.b4a.keywords.Common.Density);
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
public static String _dbdir = "";
public static anywheresoftware.b4a.objects.FirebaseAuthWrapper _auth = null;
public static boolean _isloggedin = false;
public static anywheresoftware.b4a.objects.FirebaseNotificationsService.FirebaseMessageWrapper _fm = null;
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
public ilpla.appear.reporte_habitat_rio_appear _reporte_habitat_rio_appear = null;
public ilpla.appear.uploadfiles _uploadfiles = null;
public ilpla.appear.utilidades _utilidades = null;
public static boolean  _application_error(anywheresoftware.b4a.objects.B4AException _error,String _stacktrace) throws Exception{
 //BA.debugLineNum = 53;BA.debugLine="Sub Application_Error (Error As Exception, StackTr";
 //BA.debugLineNum = 54;BA.debugLine="Return True";
if (true) return anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 55;BA.debugLine="End Sub";
return false;
}
public static String  _auth_signedin(anywheresoftware.b4a.objects.FirebaseAuthWrapper.FirebaseUserWrapper _user) throws Exception{
 //BA.debugLineNum = 60;BA.debugLine="Sub Auth_SignedIn (User As FirebaseUser)";
 //BA.debugLineNum = 64;BA.debugLine="Log(\"signed in with google!\")";
anywheresoftware.b4a.keywords.Common.LogImpl("235127300","signed in with google!",0);
 //BA.debugLineNum = 65;BA.debugLine="Main.username = User.Email";
mostCurrent._main._username /*String*/  = _user.getEmail();
 //BA.debugLineNum = 66;BA.debugLine="Main.strUserID = User.Email";
mostCurrent._main._struserid /*String*/  = _user.getEmail();
 //BA.debugLineNum = 67;BA.debugLine="Main.strUserFullName = User.DisplayName";
mostCurrent._main._struserfullname /*String*/  = _user.getDisplayName();
 //BA.debugLineNum = 68;BA.debugLine="CallSubDelayed(frmLogin, \"LoginOk_Firebase\")";
anywheresoftware.b4a.keywords.Common.CallSubDelayed(processBA,(Object)(mostCurrent._frmlogin.getObject()),"LoginOk_Firebase");
 //BA.debugLineNum = 70;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 7;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 9;BA.debugLine="Dim savedir As String";
_savedir = "";
 //BA.debugLineNum = 10;BA.debugLine="Dim sqlDB As SQL";
_sqldb = new anywheresoftware.b4a.sql.SQL();
 //BA.debugLineNum = 11;BA.debugLine="Dim dbdir As String";
_dbdir = "";
 //BA.debugLineNum = 14;BA.debugLine="Public auth As FirebaseAuth";
_auth = new anywheresoftware.b4a.objects.FirebaseAuthWrapper();
 //BA.debugLineNum = 15;BA.debugLine="Public isLoggedIn As Boolean";
_isloggedin = false;
 //BA.debugLineNum = 16;BA.debugLine="Dim fm As FirebaseMessaging";
_fm = new anywheresoftware.b4a.objects.FirebaseNotificationsService.FirebaseMessageWrapper();
 //BA.debugLineNum = 17;BA.debugLine="End Sub";
return "";
}
public static String  _service_create() throws Exception{
 //BA.debugLineNum = 19;BA.debugLine="Sub Service_Create";
 //BA.debugLineNum = 33;BA.debugLine="dbdir = DBUtils.CopyDBFromAssets(\"preservamosdb.d";
_dbdir = mostCurrent._dbutils._copydbfromassets /*String*/ (processBA,"preservamosdb.db");
 //BA.debugLineNum = 34;BA.debugLine="sqlDB.Initialize(dbdir, \"preservamosdb.db\", False";
_sqldb.Initialize(_dbdir,"preservamosdb.db",anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 36;BA.debugLine="CallSubDelayed(FirebaseMessaging, \"SubscribeToTop";
anywheresoftware.b4a.keywords.Common.CallSubDelayed(processBA,(Object)(mostCurrent._firebasemessaging.getObject()),"SubscribeToTopics");
 //BA.debugLineNum = 37;BA.debugLine="auth.Initialize(\"auth\")";
_auth.Initialize(processBA,"auth");
 //BA.debugLineNum = 38;BA.debugLine="auth.SignOutFromGoogle";
_auth.SignOutFromGoogle();
 //BA.debugLineNum = 40;BA.debugLine="fm.Initialize(\"fm\")";
_fm.Initialize(processBA,"fm");
 //BA.debugLineNum = 41;BA.debugLine="End Sub";
return "";
}
public static String  _service_destroy() throws Exception{
 //BA.debugLineNum = 57;BA.debugLine="Sub Service_Destroy";
 //BA.debugLineNum = 58;BA.debugLine="End Sub";
return "";
}
public static String  _service_start(anywheresoftware.b4a.objects.IntentWrapper _startingintent) throws Exception{
 //BA.debugLineNum = 43;BA.debugLine="Sub Service_Start (StartingIntent As Intent)";
 //BA.debugLineNum = 45;BA.debugLine="End Sub";
return "";
}
public static String  _service_taskremoved() throws Exception{
 //BA.debugLineNum = 48;BA.debugLine="Sub Service_TaskRemoved";
 //BA.debugLineNum = 50;BA.debugLine="End Sub";
return "";
}
public static String  _updatefcmtoken() throws Exception{
 //BA.debugLineNum = 74;BA.debugLine="Public Sub UpdateFCMToken";
 //BA.debugLineNum = 76;BA.debugLine="fm.SubscribeToTopic(\"general\") 'you can subscribe";
_fm.SubscribeToTopic("general");
 //BA.debugLineNum = 77;BA.debugLine="Log (\"NewToken: \" & fm.Token)";
anywheresoftware.b4a.keywords.Common.LogImpl("235192835","NewToken: "+_fm.getToken(),0);
 //BA.debugLineNum = 78;BA.debugLine="End Sub";
return "";
}
}
