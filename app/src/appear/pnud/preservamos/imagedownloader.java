package appear.pnud.preservamos;


import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.objects.ServiceHelper;
import anywheresoftware.b4a.debug.*;

public class imagedownloader extends android.app.Service{
	public static class imagedownloader_BR extends android.content.BroadcastReceiver {

		@Override
		public void onReceive(android.content.Context context, android.content.Intent intent) {
            BA.LogInfo("** Receiver (imagedownloader) OnReceive **");
			android.content.Intent in = new android.content.Intent(context, imagedownloader.class);
			if (intent != null)
				in.putExtra("b4a_internal_intent", intent);
            ServiceHelper.StarterHelper.startServiceFromReceiver (context, in, false, BA.class);
		}

	}
    static imagedownloader mostCurrent;
	public static BA processBA;
    private ServiceHelper _service;
    public static Class<?> getObject() {
		return imagedownloader.class;
	}
	@Override
	public void onCreate() {
        super.onCreate();
        mostCurrent = this;
        if (processBA == null) {
		    processBA = new BA(this, null, null, "appear.pnud.preservamos", "appear.pnud.preservamos.imagedownloader");
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
			processBA.raiseEvent2(null, true, "CREATE", true, "appear.pnud.preservamos.imagedownloader", processBA, _service, anywheresoftware.b4a.keywords.Common.Density);
		}
        if (!false && ServiceHelper.StarterHelper.startFromServiceCreate(processBA, false) == false) {
				
		}
		else {
            processBA.setActivityPaused(false);
            BA.LogInfo("*** Service (imagedownloader) Create ***");
            processBA.raiseEvent(null, "service_create");
        }
        processBA.runHook("oncreate", this, null);
        if (false) {
			if (ServiceHelper.StarterHelper.runWaitForLayouts() == false) {
                BA.LogInfo("stopping spontaneous created service");
                stopSelf();
            }
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
                    BA.LogInfo("** Service (imagedownloader) Create **");
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
        if (false)
            processBA.raiseEvent(null, "service_taskremoved");
            
    }
    private void handleStart(android.content.Intent intent) {
    	BA.LogInfo("** Service (imagedownloader) Start **");
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
        if (false) {
            BA.LogInfo("** Service (imagedownloader) Destroy (ignored)**");
        }
        else {
            BA.LogInfo("** Service (imagedownloader) Destroy **");
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
public static anywheresoftware.b4a.objects.collections.Map _cache = null;
public static anywheresoftware.b4a.objects.collections.Map _tasks = null;
public static anywheresoftware.b4a.objects.collections.Map _ongoingtasks = null;
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
public appear.pnud.preservamos.character_creation _character_creation = null;
public appear.pnud.preservamos.register _register = null;
public appear.pnud.preservamos.xuiviewsutils _xuiviewsutils = null;
public appear.pnud.preservamos.httputils2service _httputils2service = null;
public static String  _activityispaused() throws Exception{
 //BA.debugLineNum = 142;BA.debugLine="Sub ActivityIsPaused";
 //BA.debugLineNum = 143;BA.debugLine="tasks.Clear";
_tasks.Clear();
 //BA.debugLineNum = 144;BA.debugLine="End Sub";
return "";
}
public static String  _canceleverything() throws Exception{
 //BA.debugLineNum = 146;BA.debugLine="Sub CancelEverything";
 //BA.debugLineNum = 147;BA.debugLine="tasks.Clear";
_tasks.Clear();
 //BA.debugLineNum = 148;BA.debugLine="ongoingTasks.Clear";
_ongoingtasks.Clear();
 //BA.debugLineNum = 149;BA.debugLine="End Sub";
return "";
}
public static void  _download(anywheresoftware.b4a.objects.collections.Map _imageviewsmap) throws Exception{
ResumableSub_Download rsub = new ResumableSub_Download(null,_imageviewsmap);
rsub.resume(processBA, null);
}
public static class ResumableSub_Download extends BA.ResumableSub {
public ResumableSub_Download(appear.pnud.preservamos.imagedownloader parent,anywheresoftware.b4a.objects.collections.Map _imageviewsmap) {
this.parent = parent;
this._imageviewsmap = _imageviewsmap;
}
appear.pnud.preservamos.imagedownloader parent;
anywheresoftware.b4a.objects.collections.Map _imageviewsmap;
int _i = 0;
String _link = "";
anywheresoftware.b4a.objects.ImageViewWrapper _iv = null;
boolean _completed = false;
int step1;
int limit1;

@Override
public void resume(BA ba, Object[] result) throws Exception{

    while (true) {
        switch (state) {
            case -1:
return;

case 0:
//C
this.state = 1;
 //BA.debugLineNum = 26;BA.debugLine="For i = 0 To ImageViewsMap.Size - 1";
if (true) break;

case 1:
//for
this.state = 16;
step1 = 1;
limit1 = (int) (_imageviewsmap.getSize()-1);
_i = (int) (0) ;
this.state = 17;
if (true) break;

case 17:
//C
this.state = 16;
if ((step1 > 0 && _i <= limit1) || (step1 < 0 && _i >= limit1)) this.state = 3;
if (true) break;

case 18:
//C
this.state = 17;
_i = ((int)(0 + _i + step1)) ;
if (true) break;

case 3:
//C
this.state = 4;
 //BA.debugLineNum = 27;BA.debugLine="tasks.Put(ImageViewsMap.GetKeyAt(i), ImageViewsM";
parent._tasks.Put(_imageviewsmap.GetKeyAt(_i),_imageviewsmap.GetValueAt(_i));
 //BA.debugLineNum = 28;BA.debugLine="Dim link As String = ImageViewsMap.GetValueAt(i)";
_link = BA.ObjectToString(_imageviewsmap.GetValueAt(_i));
 //BA.debugLineNum = 29;BA.debugLine="If cache.ContainsKey(link) Then";
if (true) break;

case 4:
//if
this.state = 15;
if (parent._cache.ContainsKey((Object)(_link))) { 
this.state = 6;
}else if(parent._ongoingtasks.ContainsKey((Object)(_link))==anywheresoftware.b4a.keywords.Common.False) { 
this.state = 8;
}if (true) break;

case 6:
//C
this.state = 15;
 //BA.debugLineNum = 30;BA.debugLine="Dim iv As ImageView = ImageViewsMap.GetKeyAt(i)";
_iv = new anywheresoftware.b4a.objects.ImageViewWrapper();
_iv = (anywheresoftware.b4a.objects.ImageViewWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.ImageViewWrapper(), (android.widget.ImageView)(_imageviewsmap.GetKeyAt(_i)));
 //BA.debugLineNum = 31;BA.debugLine="iv.SetBackgroundImage(cache.Get(link))";
_iv.SetBackgroundImageNew((android.graphics.Bitmap)(parent._cache.Get((Object)(_link))));
 if (true) break;

case 8:
//C
this.state = 9;
 //BA.debugLineNum = 33;BA.debugLine="ongoingTasks.Put(link, \"\")";
parent._ongoingtasks.Put((Object)(_link),(Object)(""));
 //BA.debugLineNum = 48;BA.debugLine="Log(\"Getting image\")";
anywheresoftware.b4a.keywords.Common.LogImpl("039583767","Getting image",0);
 //BA.debugLineNum = 50;BA.debugLine="Wait For (imgDownload(link)) Complete (Complete";
anywheresoftware.b4a.keywords.Common.WaitFor("complete", processBA, this, _imgdownload(_link));
this.state = 19;
return;
case 19:
//C
this.state = 9;
_completed = (Boolean) result[0];
;
 //BA.debugLineNum = 51;BA.debugLine="If Completed = True Then";
if (true) break;

case 9:
//if
this.state = 14;
if (_completed==anywheresoftware.b4a.keywords.Common.True) { 
this.state = 11;
}else {
this.state = 13;
}if (true) break;

case 11:
//C
this.state = 14;
 if (true) break;

case 13:
//C
this.state = 14;
 if (true) break;

case 14:
//C
this.state = 15;
;
 if (true) break;

case 15:
//C
this.state = 18;
;
 if (true) break;
if (true) break;

case 16:
//C
this.state = -1;
;
 //BA.debugLineNum = 59;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public static void  _complete(boolean _completed) throws Exception{
}
public static anywheresoftware.b4a.keywords.Common.ResumableSubWrapper  _imgdownload(String _link) throws Exception{
ResumableSub_imgDownload rsub = new ResumableSub_imgDownload(null,_link);
rsub.resume(processBA, null);
return (anywheresoftware.b4a.keywords.Common.ResumableSubWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.keywords.Common.ResumableSubWrapper(), rsub);
}
public static class ResumableSub_imgDownload extends BA.ResumableSub {
public ResumableSub_imgDownload(appear.pnud.preservamos.imagedownloader parent,String _link) {
this.parent = parent;
this._link = _link;
}
appear.pnud.preservamos.imagedownloader parent;
String _link;
appear.pnud.preservamos.httpjob _j = null;
String _loginpath = "";
anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper _bmp = null;
int _i = 0;
String _linktask = "";
anywheresoftware.b4a.objects.ImageViewWrapper _iv = null;
int step14;
int limit14;

@Override
public void resume(BA ba, Object[] result) throws Exception{

    while (true) {
        switch (state) {
            case -1:
{
anywheresoftware.b4a.keywords.Common.ReturnFromResumableSub(this,null);return;}
case 0:
//C
this.state = 1;
 //BA.debugLineNum = 63;BA.debugLine="If ongoingTasks.Size = 0 Then";
if (true) break;

case 1:
//if
this.state = 4;
if (parent._ongoingtasks.getSize()==0) { 
this.state = 3;
}if (true) break;

case 3:
//C
this.state = 4;
 //BA.debugLineNum = 64;BA.debugLine="Return False";
if (true) {
anywheresoftware.b4a.keywords.Common.ReturnFromResumableSub(this,(Object)(anywheresoftware.b4a.keywords.Common.False));return;};
 if (true) break;

case 4:
//C
this.state = 5;
;
 //BA.debugLineNum = 66;BA.debugLine="ongoingTasks.Remove(link)";
parent._ongoingtasks.Remove((Object)(_link));
 //BA.debugLineNum = 68;BA.debugLine="Dim j As HttpJob";
_j = new appear.pnud.preservamos.httpjob();
 //BA.debugLineNum = 69;BA.debugLine="j.Initialize(\"\", Me)";
_j._initialize /*String*/ (processBA,"",imagedownloader.getObject());
 //BA.debugLineNum = 70;BA.debugLine="Dim loginPath As String = link";
_loginpath = _link;
 //BA.debugLineNum = 71;BA.debugLine="j.Download(loginPath)";
_j._download /*String*/ (_loginpath);
 //BA.debugLineNum = 72;BA.debugLine="Wait For (j) JobDone(j As HttpJob)";
anywheresoftware.b4a.keywords.Common.WaitFor("jobdone", processBA, this, (Object)(_j));
this.state = 23;
return;
case 23:
//C
this.state = 5;
_j = (appear.pnud.preservamos.httpjob) result[0];
;
 //BA.debugLineNum = 74;BA.debugLine="If j.Success Then";
if (true) break;

case 5:
//if
this.state = 22;
if (_j._success /*boolean*/ ) { 
this.state = 7;
}else {
this.state = 21;
}if (true) break;

case 7:
//C
this.state = 8;
 //BA.debugLineNum = 75;BA.debugLine="Dim bmp As Bitmap = j.GetBitmap";
_bmp = new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper();
_bmp = _j._getbitmap /*anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper*/ ();
 //BA.debugLineNum = 76;BA.debugLine="cache.Put(j.JobName, bmp)";
parent._cache.Put((Object)(_j._jobname /*String*/ ),(Object)(_bmp.getObject()));
 //BA.debugLineNum = 78;BA.debugLine="If tasks.IsInitialized Then";
if (true) break;

case 8:
//if
this.state = 19;
if (parent._tasks.IsInitialized()) { 
this.state = 10;
}if (true) break;

case 10:
//C
this.state = 11;
 //BA.debugLineNum = 79;BA.debugLine="For i = 0 To tasks.Size - 1";
if (true) break;

case 11:
//for
this.state = 18;
step14 = 1;
limit14 = (int) (parent._tasks.getSize()-1);
_i = (int) (0) ;
this.state = 24;
if (true) break;

case 24:
//C
this.state = 18;
if ((step14 > 0 && _i <= limit14) || (step14 < 0 && _i >= limit14)) this.state = 13;
if (true) break;

case 25:
//C
this.state = 24;
_i = ((int)(0 + _i + step14)) ;
if (true) break;

case 13:
//C
this.state = 14;
 //BA.debugLineNum = 80;BA.debugLine="Dim linkTask As String = tasks.GetValueAt(i)";
_linktask = BA.ObjectToString(parent._tasks.GetValueAt(_i));
 //BA.debugLineNum = 81;BA.debugLine="If linkTask = link Then";
if (true) break;

case 14:
//if
this.state = 17;
if ((_linktask).equals(_link)) { 
this.state = 16;
}if (true) break;

case 16:
//C
this.state = 17;
 //BA.debugLineNum = 82;BA.debugLine="Dim iv As ImageView = tasks.GetKeyAt(i)";
_iv = new anywheresoftware.b4a.objects.ImageViewWrapper();
_iv = (anywheresoftware.b4a.objects.ImageViewWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.ImageViewWrapper(), (android.widget.ImageView)(parent._tasks.GetKeyAt(_i)));
 //BA.debugLineNum = 83;BA.debugLine="iv.SetBackgroundImage(bmp)";
_iv.SetBackgroundImageNew((android.graphics.Bitmap)(_bmp.getObject()));
 if (true) break;

case 17:
//C
this.state = 25;
;
 if (true) break;
if (true) break;

case 18:
//C
this.state = 19;
;
 if (true) break;

case 19:
//C
this.state = 22;
;
 //BA.debugLineNum = 87;BA.debugLine="Return True";
if (true) {
anywheresoftware.b4a.keywords.Common.ReturnFromResumableSub(this,(Object)(anywheresoftware.b4a.keywords.Common.True));return;};
 if (true) break;

case 21:
//C
this.state = 22;
 //BA.debugLineNum = 89;BA.debugLine="Log(\"Error downloading image: \" & j.JobName & CR";
anywheresoftware.b4a.keywords.Common.LogImpl("039649307","Error downloading image: "+_j._jobname /*String*/ +anywheresoftware.b4a.keywords.Common.CRLF+_j._errormessage /*String*/ ,0);
 //BA.debugLineNum = 90;BA.debugLine="Return False";
if (true) {
anywheresoftware.b4a.keywords.Common.ReturnFromResumableSub(this,(Object)(anywheresoftware.b4a.keywords.Common.False));return;};
 if (true) break;

case 22:
//C
this.state = -1;
;
 //BA.debugLineNum = 92;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public static void  _jobdone(appear.pnud.preservamos.httpjob _j) throws Exception{
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 5;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 6;BA.debugLine="Private cache As Map";
_cache = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 7;BA.debugLine="Private tasks As Map";
_tasks = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 8;BA.debugLine="Private ongoingTasks As Map";
_ongoingtasks = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 9;BA.debugLine="End Sub";
return "";
}
public static String  _service_create() throws Exception{
 //BA.debugLineNum = 11;BA.debugLine="Sub Service_Create";
 //BA.debugLineNum = 12;BA.debugLine="tasks.Initialize";
_tasks.Initialize();
 //BA.debugLineNum = 13;BA.debugLine="cache.Initialize";
_cache.Initialize();
 //BA.debugLineNum = 14;BA.debugLine="ongoingTasks.Initialize";
_ongoingtasks.Initialize();
 //BA.debugLineNum = 15;BA.debugLine="End Sub";
return "";
}
public static String  _service_destroy() throws Exception{
 //BA.debugLineNum = 21;BA.debugLine="Sub Service_Destroy";
 //BA.debugLineNum = 23;BA.debugLine="End Sub";
return "";
}
public static String  _service_start(anywheresoftware.b4a.objects.IntentWrapper _startingintent) throws Exception{
 //BA.debugLineNum = 17;BA.debugLine="Sub Service_Start (StartingIntent As Intent)";
 //BA.debugLineNum = 19;BA.debugLine="End Sub";
return "";
}
}
