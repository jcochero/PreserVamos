package appear.pnud.preservamos;


import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.B4AClass;
import anywheresoftware.b4a.BALayout;
import anywheresoftware.b4a.debug.*;

public class camex2 extends B4AClass.ImplB4AClass implements BA.SubDelegator{
    private static java.util.HashMap<String, java.lang.reflect.Method> htSubs;
    private void innerInitialize(BA _ba) throws Exception {
        if (ba == null) {
            ba = new BA(_ba, this, htSubs, "appear.pnud.preservamos.camex2");
            if (htSubs == null) {
                ba.loadHtSubs(this.getClass());
                htSubs = ba.htSubs;
            }
            
        }
        if (BA.isShellModeRuntimeCheck(ba)) 
			   this.getClass().getMethod("_class_globals", appear.pnud.preservamos.camex2.class).invoke(this, new Object[] {null});
        else
            ba.raiseEvent2(null, true, "class_globals", false);
    }

 public anywheresoftware.b4a.keywords.Common __c = null;
public anywheresoftware.b4a.objects.Camera2 _camera = null;
public anywheresoftware.b4j.object.JavaObject _jcamera = null;
public String _id = "";
public boolean _mfront = false;
public anywheresoftware.b4a.objects.Camera2.CameraSizeWrapper _previewsize = null;
public anywheresoftware.b4a.objects.Camera2.CameraSizeWrapper _capturesize = null;
public anywheresoftware.b4a.objects.collections.Map _previewsettingsmap = null;
public anywheresoftware.b4a.objects.collections.Map _capturesettingmap = null;
public anywheresoftware.b4a.objects.collections.List _bothmaps = null;
public anywheresoftware.b4a.objects.PanelWrapper _mpanel = null;
public anywheresoftware.b4a.objects.ConcreteViewWrapper _tv = null;
public anywheresoftware.b4j.object.JavaObject _staticcapturerequest = null;
public anywheresoftware.b4j.object.JavaObject _staticcameracharacteristics = null;
public anywheresoftware.b4j.object.JavaObject _staticcaptureresult = null;
public anywheresoftware.b4a.objects.collections.List _af_state = null;
public anywheresoftware.b4a.objects.collections.List _flash_state = null;
public anywheresoftware.b4a.objects.collections.List _af_mode = null;
public anywheresoftware.b4a.objects.collections.List _ae_mode = null;
public anywheresoftware.b4a.objects.collections.List _flash_mode = null;
public anywheresoftware.b4a.objects.collections.List _scene_mode = null;
public anywheresoftware.b4a.objects.collections.List _effect_mode = null;
public anywheresoftware.b4a.objects.collections.List _supported_hardware_level = null;
public anywheresoftware.b4a.objects.collections.List _control_mode = null;
public boolean _printkeys = false;
public anywheresoftware.b4j.object.JavaObject _previewrequest = null;
public String _focusstate = "";
public anywheresoftware.b4j.object.JavaObject _mediarecorder = null;
public boolean _recordingvideo = false;
public int _taskindex = 0;
public b4a.example.dateutils _dateutils = null;
public appear.pnud.preservamos.main _main = null;
public appear.pnud.preservamos.form_main _form_main = null;
public appear.pnud.preservamos.reporte_fotos _reporte_fotos = null;
public appear.pnud.preservamos.dbutils _dbutils = null;
public appear.pnud.preservamos.frmlocalizacion _frmlocalizacion = null;
public appear.pnud.preservamos.reporte_habitat_laguna _reporte_habitat_laguna = null;
public appear.pnud.preservamos.reporte_habitat_rio _reporte_habitat_rio = null;
public appear.pnud.preservamos.aprender_muestreo _aprender_muestreo = null;
public appear.pnud.preservamos.downloadservice _downloadservice = null;
public appear.pnud.preservamos.firebasemessaging _firebasemessaging = null;
public appear.pnud.preservamos.form_reporte _form_reporte = null;
public appear.pnud.preservamos.frmabout _frmabout = null;
public appear.pnud.preservamos.frmdatosanteriores _frmdatosanteriores = null;
public appear.pnud.preservamos.frmeditprofile _frmeditprofile = null;
public appear.pnud.preservamos.frmfelicitaciones _frmfelicitaciones = null;
public appear.pnud.preservamos.frmperfil _frmperfil = null;
public appear.pnud.preservamos.frmpoliticadatos _frmpoliticadatos = null;
public appear.pnud.preservamos.httputils2service _httputils2service = null;
public appear.pnud.preservamos.register _register = null;
public appear.pnud.preservamos.reporte_envio _reporte_envio = null;
public appear.pnud.preservamos.starter _starter = null;
public appear.pnud.preservamos.uploadfiles _uploadfiles = null;
public appear.pnud.preservamos.utilidades _utilidades = null;
public appear.pnud.preservamos.xuiviewsutils _xuiviewsutils = null;
public anywheresoftware.b4a.objects.collections.List  _boolstolist(Object _obj) throws Exception{
anywheresoftware.b4a.objects.collections.List _res = null;
boolean[] _i = null;
boolean _ii = false;
 //BA.debugLineNum = 477;BA.debugLine="Private Sub BoolsToList (Obj As Object) As List";
 //BA.debugLineNum = 478;BA.debugLine="Dim res As List";
_res = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 479;BA.debugLine="res.Initialize";
_res.Initialize();
 //BA.debugLineNum = 480;BA.debugLine="If Obj = Null Then Return res";
if (_obj== null) { 
if (true) return _res;};
 //BA.debugLineNum = 481;BA.debugLine="Dim i() As Boolean = Obj";
_i = (boolean[])(_obj);
 //BA.debugLineNum = 482;BA.debugLine="For Each ii As Boolean In i";
{
final boolean[] group5 = _i;
final int groupLen5 = group5.length
;int index5 = 0;
;
for (; index5 < groupLen5;index5++){
_ii = group5[index5];
 //BA.debugLineNum = 483;BA.debugLine="res.Add(ii)";
_res.Add((Object)(_ii));
 }
};
 //BA.debugLineNum = 485;BA.debugLine="Return res";
if (true) return _res;
 //BA.debugLineNum = 486;BA.debugLine="End Sub";
return null;
}
public anywheresoftware.b4a.objects.collections.List  _bytestolist(Object _obj) throws Exception{
anywheresoftware.b4a.objects.collections.List _res = null;
byte[] _i = null;
byte _ii = (byte)0;
 //BA.debugLineNum = 488;BA.debugLine="Private Sub BytesToList (Obj As Object) As List";
 //BA.debugLineNum = 489;BA.debugLine="Dim res As List";
_res = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 490;BA.debugLine="res.Initialize";
_res.Initialize();
 //BA.debugLineNum = 491;BA.debugLine="If Obj = Null Then Return res";
if (_obj== null) { 
if (true) return _res;};
 //BA.debugLineNum = 492;BA.debugLine="Dim i() As Byte = Obj";
_i = (byte[])(_obj);
 //BA.debugLineNum = 493;BA.debugLine="For Each ii As Byte In i";
{
final byte[] group5 = _i;
final int groupLen5 = group5.length
;int index5 = 0;
;
for (; index5 < groupLen5;index5++){
_ii = group5[index5];
 //BA.debugLineNum = 494;BA.debugLine="res.Add(ii)";
_res.Add((Object)(_ii));
 }
};
 //BA.debugLineNum = 496;BA.debugLine="Return res";
if (true) return _res;
 //BA.debugLineNum = 497;BA.debugLine="End Sub";
return null;
}
public String  _camera_previewcapturecomplete(Object _captureresult) throws Exception{
 //BA.debugLineNum = 308;BA.debugLine="Private Sub Camera_PreviewCaptureComplete (Capture";
 //BA.debugLineNum = 309;BA.debugLine="FocusState = IntToConst(GetFromCaptureResult(Capt";
_focusstate = _inttoconst(_getfromcaptureresult(_captureresult,"CONTROL_AF_STATE"),_af_state);
 //BA.debugLineNum = 312;BA.debugLine="End Sub";
return "";
}
public String  _class_globals() throws Exception{
 //BA.debugLineNum = 2;BA.debugLine="Sub Class_Globals";
 //BA.debugLineNum = 3;BA.debugLine="Public Camera As Camera2";
_camera = new anywheresoftware.b4a.objects.Camera2();
 //BA.debugLineNum = 4;BA.debugLine="Private jcamera As JavaObject";
_jcamera = new anywheresoftware.b4j.object.JavaObject();
 //BA.debugLineNum = 5;BA.debugLine="Private id As String";
_id = "";
 //BA.debugLineNum = 6;BA.debugLine="Private mFront As Boolean = False";
_mfront = __c.False;
 //BA.debugLineNum = 7;BA.debugLine="Public PreviewSize, CaptureSize As CameraSize";
_previewsize = new anywheresoftware.b4a.objects.Camera2.CameraSizeWrapper();
_capturesize = new anywheresoftware.b4a.objects.Camera2.CameraSizeWrapper();
 //BA.debugLineNum = 8;BA.debugLine="Public PreviewSettingsMap, CaptureSettingMap As M";
_previewsettingsmap = new anywheresoftware.b4a.objects.collections.Map();
_capturesettingmap = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 9;BA.debugLine="Private bothMaps As List";
_bothmaps = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 10;BA.debugLine="Private mPanel As Panel";
_mpanel = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 11;BA.debugLine="Private tv As View";
_tv = new anywheresoftware.b4a.objects.ConcreteViewWrapper();
 //BA.debugLineNum = 12;BA.debugLine="Private StaticCaptureRequest As JavaObject";
_staticcapturerequest = new anywheresoftware.b4j.object.JavaObject();
 //BA.debugLineNum = 13;BA.debugLine="Private StaticCameraCharacteristics As JavaObject";
_staticcameracharacteristics = new anywheresoftware.b4j.object.JavaObject();
 //BA.debugLineNum = 14;BA.debugLine="Private StaticCaptureResult As JavaObject";
_staticcaptureresult = new anywheresoftware.b4j.object.JavaObject();
 //BA.debugLineNum = 15;BA.debugLine="Private AF_STATE As List = Array(\"INACTIVE\", \"PAS";
_af_state = new anywheresoftware.b4a.objects.collections.List();
_af_state = anywheresoftware.b4a.keywords.Common.ArrayToList(new Object[]{(Object)("INACTIVE"),(Object)("PASSIVE_SCAN"),(Object)("PASSIVE_FOCUSED"),(Object)("ACTIVE_SCAN"),(Object)("FOCUSED_LOCKED"),(Object)("NOT_FOCUSED_LOCKED"),(Object)("PASSIVE_UNFOCUSED")});
 //BA.debugLineNum = 16;BA.debugLine="Private FLASH_STATE As List = Array(\"UNAVAILABLE\"";
_flash_state = new anywheresoftware.b4a.objects.collections.List();
_flash_state = anywheresoftware.b4a.keywords.Common.ArrayToList(new Object[]{(Object)("UNAVAILABLE"),(Object)("CHARGING"),(Object)("READY"),(Object)("FIRED"),(Object)("PARTIAL")});
 //BA.debugLineNum = 18;BA.debugLine="Private AF_MODE As List = Array(\"OFF\", \"AUTO\", \"M";
_af_mode = new anywheresoftware.b4a.objects.collections.List();
_af_mode = anywheresoftware.b4a.keywords.Common.ArrayToList(new Object[]{(Object)("OFF"),(Object)("AUTO"),(Object)("MACRO"),(Object)("CONTINUOUS_VIDEO"),(Object)("CONTINUOUS_PICTURE"),(Object)("EDOF")});
 //BA.debugLineNum = 20;BA.debugLine="Private AE_MODE As List = Array(\"OFF\", \"ON\", \"ON_";
_ae_mode = new anywheresoftware.b4a.objects.collections.List();
_ae_mode = anywheresoftware.b4a.keywords.Common.ArrayToList(new Object[]{(Object)("OFF"),(Object)("ON"),(Object)("ON_AUTO_FLASH"),(Object)("ON_ALWAYS_FLASH"),(Object)("ON_AUTO_FLASH_REDEYE")});
 //BA.debugLineNum = 21;BA.debugLine="Private FLASH_MODE As List = Array(\"OFF\", \"SINGLE";
_flash_mode = new anywheresoftware.b4a.objects.collections.List();
_flash_mode = anywheresoftware.b4a.keywords.Common.ArrayToList(new Object[]{(Object)("OFF"),(Object)("SINGLE"),(Object)("TORCH")});
 //BA.debugLineNum = 22;BA.debugLine="Private SCENE_MODE As List = Array(\"DISABLED\", \"F";
_scene_mode = new anywheresoftware.b4a.objects.collections.List();
_scene_mode = anywheresoftware.b4a.keywords.Common.ArrayToList(new Object[]{(Object)("DISABLED"),(Object)("FACE_PRIORITY"),(Object)("ACTION"),(Object)("PORTRAIT"),(Object)("LANDSCAPE"),(Object)("NIGHT"),(Object)("PORTRAIT"),(Object)("THEATRE"),(Object)("BEACH"),(Object)("SNOW"),(Object)("SUNSET"),(Object)("STEADYPHOTO"),(Object)("FIREWORKS"),(Object)("SPORTS"),(Object)("PARTY"),(Object)("CANDLELIGHT"),(Object)("BARCODE"),(Object)("HIGH_SPEED_VIDEO"),(Object)("MODE_HDR"),(Object)("FACE_PRIORITY_LOW_LIGHT")});
 //BA.debugLineNum = 24;BA.debugLine="Private EFFECT_MODE As List = Array(\"OFF\", \"MONO\"";
_effect_mode = new anywheresoftware.b4a.objects.collections.List();
_effect_mode = anywheresoftware.b4a.keywords.Common.ArrayToList(new Object[]{(Object)("OFF"),(Object)("MONO"),(Object)("NEGATIVE"),(Object)("SOLARIZE"),(Object)("SEPIA"),(Object)("POSTERIZE"),(Object)("WHITEBOARD"),(Object)("BLACKBOARD"),(Object)("AQUA")});
 //BA.debugLineNum = 25;BA.debugLine="Private SUPPORTED_HARDWARE_LEVEL As List = Array(";
_supported_hardware_level = new anywheresoftware.b4a.objects.collections.List();
_supported_hardware_level = anywheresoftware.b4a.keywords.Common.ArrayToList(new Object[]{(Object)("LIMITED"),(Object)("FULL"),(Object)("LEGACY"),(Object)("LEVEL_3")});
 //BA.debugLineNum = 26;BA.debugLine="Private CONTROL_MODE As List = Array(\"OFF\", \"AUTO";
_control_mode = new anywheresoftware.b4a.objects.collections.List();
_control_mode = anywheresoftware.b4a.keywords.Common.ArrayToList(new Object[]{(Object)("OFF"),(Object)("AUTO"),(Object)("USE_SCENE_MODE"),(Object)("OFF_KEEP_STATE")});
 //BA.debugLineNum = 27;BA.debugLine="Public PrintKeys As Boolean = False";
_printkeys = __c.False;
 //BA.debugLineNum = 28;BA.debugLine="Private PreviewRequest As JavaObject";
_previewrequest = new anywheresoftware.b4j.object.JavaObject();
 //BA.debugLineNum = 29;BA.debugLine="Private FocusState As String";
_focusstate = "";
 //BA.debugLineNum = 30;BA.debugLine="Private MediaRecorder As JavaObject";
_mediarecorder = new anywheresoftware.b4j.object.JavaObject();
 //BA.debugLineNum = 31;BA.debugLine="Public RecordingVideo As Boolean";
_recordingvideo = false;
 //BA.debugLineNum = 32;BA.debugLine="Public TaskIndex As Int = 1";
_taskindex = (int) (1);
 //BA.debugLineNum = 33;BA.debugLine="End Sub";
return "";
}
public String  _closesession() throws Exception{
anywheresoftware.b4j.object.JavaObject _session = null;
 //BA.debugLineNum = 72;BA.debugLine="Private Sub CloseSession";
 //BA.debugLineNum = 73;BA.debugLine="Dim session As JavaObject = jcamera.GetFieldJO(\"c";
_session = new anywheresoftware.b4j.object.JavaObject();
_session = _jcamera.GetFieldJO("captureSession");
 //BA.debugLineNum = 74;BA.debugLine="If session.IsInitialized Then";
if (_session.IsInitialized()) { 
 //BA.debugLineNum = 75;BA.debugLine="session.RunMethod(\"stopRepeating\", Null)";
_session.RunMethod("stopRepeating",(Object[])(__c.Null));
 //BA.debugLineNum = 76;BA.debugLine="session.RunMethod(\"abortCaptures\", Null)";
_session.RunMethod("abortCaptures",(Object[])(__c.Null));
 //BA.debugLineNum = 77;BA.debugLine="jcamera.SetField(\"captureSession\", Null)";
_jcamera.SetField("captureSession",__c.Null);
 };
 //BA.debugLineNum = 79;BA.debugLine="End Sub";
return "";
}
public anywheresoftware.b4a.keywords.Common.ResumableSubWrapper  _createsurface() throws Exception{
ResumableSub_CreateSurface rsub = new ResumableSub_CreateSurface(this);
rsub.resume(ba, null);
return (anywheresoftware.b4a.keywords.Common.ResumableSubWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.keywords.Common.ResumableSubWrapper(), rsub);
}
public static class ResumableSub_CreateSurface extends BA.ResumableSub {
public ResumableSub_CreateSurface(appear.pnud.preservamos.camex2 parent) {
this.parent = parent;
}
appear.pnud.preservamos.camex2 parent;

@Override
public void resume(BA ba, Object[] result) throws Exception{

    while (true) {
        switch (state) {
            case -1:
{
parent.__c.ReturnFromResumableSub(this,null);return;}
case 0:
//C
this.state = 1;
 //BA.debugLineNum = 144;BA.debugLine="If tv.IsInitialized Then tv.RemoveView";
if (true) break;

case 1:
//if
this.state = 6;
if (parent._tv.IsInitialized()) { 
this.state = 3;
;}if (true) break;

case 3:
//C
this.state = 6;
parent._tv.RemoveView();
if (true) break;

case 6:
//C
this.state = -1;
;
 //BA.debugLineNum = 145;BA.debugLine="tv = Camera.CreateSurface";
parent._tv = parent._camera.CreateSurface(ba);
 //BA.debugLineNum = 146;BA.debugLine="mPanel.AddView(tv, 0, 0, mPanel.Width, mPanel.Hei";
parent._mpanel.AddView((android.view.View)(parent._tv.getObject()),(int) (0),(int) (0),parent._mpanel.getWidth(),parent._mpanel.getHeight());
 //BA.debugLineNum = 147;BA.debugLine="tv.SendToBack";
parent._tv.SendToBack();
 //BA.debugLineNum = 148;BA.debugLine="Wait For Camera_SurfaceReady";
parent.__c.WaitFor("camera_surfaceready", ba, this, null);
this.state = 7;
return;
case 7:
//C
this.state = -1;
;
 //BA.debugLineNum = 149;BA.debugLine="Return True";
if (true) {
parent.__c.ReturnFromResumableSub(this,(Object)(parent.__c.True));return;};
 //BA.debugLineNum = 150;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public void  _camera_surfaceready() throws Exception{
}
public anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper  _datatobitmap(byte[] _data) throws Exception{
anywheresoftware.b4a.objects.streams.File.InputStreamWrapper _in = null;
anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper _bmp = null;
 //BA.debugLineNum = 364;BA.debugLine="Public Sub DataToBitmap(Data() As Byte) As Bitmap";
 //BA.debugLineNum = 365;BA.debugLine="Dim in As InputStream";
_in = new anywheresoftware.b4a.objects.streams.File.InputStreamWrapper();
 //BA.debugLineNum = 366;BA.debugLine="in.InitializeFromBytesArray(Data, 0, Data.Length)";
_in.InitializeFromBytesArray(_data,(int) (0),_data.length);
 //BA.debugLineNum = 367;BA.debugLine="Dim bmp As Bitmap";
_bmp = new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper();
 //BA.debugLineNum = 368;BA.debugLine="bmp.Initialize2(in)";
_bmp.Initialize2((java.io.InputStream)(_in.getObject()));
 //BA.debugLineNum = 369;BA.debugLine="in.Close";
_in.Close();
 //BA.debugLineNum = 370;BA.debugLine="Return bmp";
if (true) return _bmp;
 //BA.debugLineNum = 371;BA.debugLine="End Sub";
return null;
}
public String  _datatofile(byte[] _data,String _dir,String _filename) throws Exception{
anywheresoftware.b4a.objects.streams.File.OutputStreamWrapper _out = null;
 //BA.debugLineNum = 374;BA.debugLine="Public Sub DataToFile(Data() As Byte, Dir As Strin";
 //BA.debugLineNum = 375;BA.debugLine="Dim out As OutputStream = File.OpenOutput(Dir, Fi";
_out = new anywheresoftware.b4a.objects.streams.File.OutputStreamWrapper();
_out = __c.File.OpenOutput(_dir,_filename,__c.False);
 //BA.debugLineNum = 376;BA.debugLine="out.WriteBytes(Data, 0, Data.Length)";
_out.WriteBytes(_data,(int) (0),_data.length);
 //BA.debugLineNum = 377;BA.debugLine="out.Close";
_out.Close();
 //BA.debugLineNum = 378;BA.debugLine="End Sub";
return "";
}
public anywheresoftware.b4a.objects.collections.List  _floatstolist(Object _obj) throws Exception{
anywheresoftware.b4a.objects.collections.List _res = null;
float[] _f = null;
float _ff = 0f;
 //BA.debugLineNum = 511;BA.debugLine="Private Sub FloatsToList (Obj As Object) As List";
 //BA.debugLineNum = 512;BA.debugLine="Dim res As List";
_res = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 513;BA.debugLine="res.Initialize";
_res.Initialize();
 //BA.debugLineNum = 514;BA.debugLine="If Obj = Null Then Return res";
if (_obj== null) { 
if (true) return _res;};
 //BA.debugLineNum = 515;BA.debugLine="Dim f() As Float = Obj";
_f = (float[])(_obj);
 //BA.debugLineNum = 516;BA.debugLine="For Each ff As Float In f";
{
final float[] group5 = _f;
final int groupLen5 = group5.length
;int index5 = 0;
;
for (; index5 < groupLen5;index5++){
_ff = group5[index5];
 //BA.debugLineNum = 517;BA.debugLine="res.Add(ff)";
_res.Add((Object)(_ff));
 }
};
 //BA.debugLineNum = 519;BA.debugLine="Return res";
if (true) return _res;
 //BA.debugLineNum = 520;BA.debugLine="End Sub";
return null;
}
public anywheresoftware.b4a.keywords.Common.ResumableSubWrapper  _focusandtakepicture(int _mytaskindex) throws Exception{
ResumableSub_FocusAndTakePicture rsub = new ResumableSub_FocusAndTakePicture(this,_mytaskindex);
rsub.resume(ba, null);
return (anywheresoftware.b4a.keywords.Common.ResumableSubWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.keywords.Common.ResumableSubWrapper(), rsub);
}
public static class ResumableSub_FocusAndTakePicture extends BA.ResumableSub {
public ResumableSub_FocusAndTakePicture(appear.pnud.preservamos.camex2 parent,int _mytaskindex) {
this.parent = parent;
this._mytaskindex = _mytaskindex;
}
appear.pnud.preservamos.camex2 parent;
int _mytaskindex;
String _previewfocusmode = "";
String _previewaemode = "";
anywheresoftware.b4j.object.JavaObject _previewbuilder = null;
boolean _success = false;
byte[] _data = null;
Object _captureresult = null;

@Override
public void resume(BA ba, Object[] result) throws Exception{

    while (true) {
        switch (state) {
            case -1:
{
parent.__c.ReturnFromResumableSub(this,null);return;}
case 0:
//C
this.state = 1;
 //BA.debugLineNum = 271;BA.debugLine="Dim PreviewFocusMode As String = AF_MODE.Get(GetF";
_previewfocusmode = BA.ObjectToString(parent._af_mode.Get((int)(BA.ObjectToNumber(parent._getfromcapturerequest((Object)(parent._previewrequest.getObject()),"CONTROL_AF_MODE")))));
 //BA.debugLineNum = 272;BA.debugLine="Dim PreviewAEMode As String = AE_MODE.Get(GetFrom";
_previewaemode = BA.ObjectToString(parent._ae_mode.Get((int)(BA.ObjectToNumber(parent._getfromcapturerequest((Object)(parent._previewrequest.getObject()),"CONTROL_AE_MODE")))));
 //BA.debugLineNum = 273;BA.debugLine="If PreviewFocusMode = \"OFF\" Or PreviewFocusMode =";
if (true) break;

case 1:
//if
this.state = 12;
if ((_previewfocusmode).equals("OFF") || (_previewfocusmode).equals("EDOF")) { 
this.state = 3;
}else if(_previewfocusmode.contains("CONTINUOUS")==parent.__c.False || (_previewaemode).equals("ON_ALWAYS_FLASH")) { 
this.state = 5;
}if (true) break;

case 3:
//C
this.state = 12;
 //BA.debugLineNum = 274;BA.debugLine="Log(\"Focus not supported\")";
parent.__c.LogImpl("623855108","Focus not supported",0);
 if (true) break;

case 5:
//C
this.state = 6;
 //BA.debugLineNum = 276;BA.debugLine="Dim PreviewBuilder As JavaObject = Camera.Create";
_previewbuilder = new anywheresoftware.b4j.object.JavaObject();
_previewbuilder = (anywheresoftware.b4j.object.JavaObject) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4j.object.JavaObject(), (java.lang.Object)(parent._camera.CreatePreviewBuilder()));
 //BA.debugLineNum = 277;BA.debugLine="PreviewSettingsMap.Put(\"CONTROL_AF_TRIGGER\", 1)";
parent._previewsettingsmap.Put((Object)("CONTROL_AF_TRIGGER"),(Object)(1));
 //BA.debugLineNum = 278;BA.debugLine="SetSettingsFromMap(PreviewBuilder, PreviewSettin";
parent._setsettingsfrommap(_previewbuilder,parent._previewsettingsmap);
 //BA.debugLineNum = 279;BA.debugLine="Camera.AddCaptureRequest(PreviewBuilder)";
parent._camera.AddCaptureRequest((Object)(_previewbuilder.getObject()));
 //BA.debugLineNum = 280;BA.debugLine="Wait For (WaitForFocusWithTimeout(5000)) Complet";
parent.__c.WaitFor("complete", ba, this, parent._waitforfocuswithtimeout((int) (5000)));
this.state = 13;
return;
case 13:
//C
this.state = 6;
_success = (Boolean) result[0];
;
 //BA.debugLineNum = 281;BA.debugLine="If Success = False Then";
if (true) break;

case 6:
//if
this.state = 11;
if (_success==parent.__c.False) { 
this.state = 8;
}else {
this.state = 10;
}if (true) break;

case 8:
//C
this.state = 11;
 //BA.debugLineNum = 282;BA.debugLine="Log(\"Focus failed\")";
parent.__c.LogImpl("623855116","Focus failed",0);
 if (true) break;

case 10:
//C
this.state = 11;
 //BA.debugLineNum = 284;BA.debugLine="Log(\"Focused!\")";
parent.__c.LogImpl("623855118","Focused!",0);
 if (true) break;

case 11:
//C
this.state = 12;
;
 //BA.debugLineNum = 286;BA.debugLine="Wait For (TakePictureNow(MyTaskIndex)) Complete";
parent.__c.WaitFor("complete", ba, this, parent._takepicturenow(_mytaskindex));
this.state = 14;
return;
case 14:
//C
this.state = 12;
_data = (byte[]) result[0];
;
 //BA.debugLineNum = 287;BA.debugLine="Dim PreviewBuilder As JavaObject = Camera.Create";
_previewbuilder = new anywheresoftware.b4j.object.JavaObject();
_previewbuilder = (anywheresoftware.b4j.object.JavaObject) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4j.object.JavaObject(), (java.lang.Object)(parent._camera.CreatePreviewBuilder()));
 //BA.debugLineNum = 288;BA.debugLine="PreviewSettingsMap.Put(\"CONTROL_AF_TRIGGER\", 2)";
parent._previewsettingsmap.Put((Object)("CONTROL_AF_TRIGGER"),(Object)(2));
 //BA.debugLineNum = 289;BA.debugLine="SetSettingsFromMap(PreviewBuilder, PreviewSettin";
parent._setsettingsfrommap(_previewbuilder,parent._previewsettingsmap);
 //BA.debugLineNum = 290;BA.debugLine="Camera.AddCaptureRequest(PreviewBuilder)";
parent._camera.AddCaptureRequest((Object)(_previewbuilder.getObject()));
 //BA.debugLineNum = 291;BA.debugLine="PreviewSettingsMap.Remove(\"CONTROL_AF_TRIGGER\")";
parent._previewsettingsmap.Remove((Object)("CONTROL_AF_TRIGGER"));
 //BA.debugLineNum = 292;BA.debugLine="Wait For (PreviewBuilder) Camera_CaptureComplete";
parent.__c.WaitFor("camera_capturecomplete", ba, this, (Object)(_previewbuilder.getObject()));
this.state = 15;
return;
case 15:
//C
this.state = 12;
_captureresult = (Object) result[0];
;
 //BA.debugLineNum = 293;BA.debugLine="Return data";
if (true) {
parent.__c.ReturnFromResumableSub(this,(Object)(_data));return;};
 if (true) break;

case 12:
//C
this.state = -1;
;
 //BA.debugLineNum = 295;BA.debugLine="Wait For (TakePictureNow(MyTaskIndex)) Complete (";
parent.__c.WaitFor("complete", ba, this, parent._takepicturenow(_mytaskindex));
this.state = 16;
return;
case 16:
//C
this.state = -1;
_data = (byte[]) result[0];
;
 //BA.debugLineNum = 296;BA.debugLine="Return data";
if (true) {
parent.__c.ReturnFromResumableSub(this,(Object)(_data));return;};
 //BA.debugLineNum = 297;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public void  _complete(boolean _success) throws Exception{
}
public void  _camera_capturecomplete(Object _captureresult) throws Exception{
}
public anywheresoftware.b4a.objects.drawable.CanvasWrapper.RectWrapper  _getactivearraysize() throws Exception{
 //BA.debugLineNum = 259;BA.debugLine="Public Sub getActiveArraySize As Rect";
 //BA.debugLineNum = 260;BA.debugLine="Return GetFromCameraCharacteristic(\"SENSOR_INFO_A";
if (true) return (anywheresoftware.b4a.objects.drawable.CanvasWrapper.RectWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.drawable.CanvasWrapper.RectWrapper(), (android.graphics.Rect)(_getfromcameracharacteristic("SENSOR_INFO_ACTIVE_ARRAY_SIZE")));
 //BA.debugLineNum = 261;BA.debugLine="End Sub";
return null;
}
public String  _getautoexposuremode() throws Exception{
 //BA.debugLineNum = 238;BA.debugLine="Public Sub getAutoExposureMode As String";
 //BA.debugLineNum = 239;BA.debugLine="Return IntToConst(GetFromCaptureRequest(PreviewRe";
if (true) return _inttoconst(_getfromcapturerequest((Object)(_previewrequest.getObject()),"CONTROL_AE_MODE"),_ae_mode);
 //BA.debugLineNum = 240;BA.debugLine="End Sub";
return "";
}
public String  _getcontrolmode() throws Exception{
 //BA.debugLineNum = 242;BA.debugLine="Public Sub getControlMode As String";
 //BA.debugLineNum = 243;BA.debugLine="Return IntToConst(GetFromCaptureRequest(PreviewRe";
if (true) return _inttoconst(_getfromcapturerequest((Object)(_previewrequest.getObject()),"CONTROL_MODE"),_control_mode);
 //BA.debugLineNum = 244;BA.debugLine="End Sub";
return "";
}
public String  _geteffectmode() throws Exception{
 //BA.debugLineNum = 194;BA.debugLine="Public Sub getEffectMode As String";
 //BA.debugLineNum = 195;BA.debugLine="Return IntToConst(GetFromCaptureRequest(PreviewRe";
if (true) return _inttoconst(_getfromcapturerequest((Object)(_previewrequest.getObject()),"CONTROL_EFFECT_MODE"),_effect_mode);
 //BA.debugLineNum = 196;BA.debugLine="End Sub";
return "";
}
public String  _getfocusmode() throws Exception{
 //BA.debugLineNum = 219;BA.debugLine="Public Sub getFocusMode As String";
 //BA.debugLineNum = 220;BA.debugLine="Return IntToConst(GetFromCaptureRequest(PreviewRe";
if (true) return _inttoconst(_getfromcapturerequest((Object)(_previewrequest.getObject()),"CONTROL_AF_MODE"),_af_mode);
 //BA.debugLineNum = 221;BA.debugLine="End Sub";
return "";
}
public Object  _getfromcameracharacteristic(String _field) throws Exception{
anywheresoftware.b4j.object.JavaObject _jo = null;
 //BA.debugLineNum = 385;BA.debugLine="Private Sub GetFromCameraCharacteristic (Field As";
 //BA.debugLineNum = 386;BA.debugLine="Dim jo As JavaObject = Camera.GetCameraCharacteri";
_jo = new anywheresoftware.b4j.object.JavaObject();
_jo = (anywheresoftware.b4j.object.JavaObject) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4j.object.JavaObject(), (java.lang.Object)(_camera.GetCameraCharacteristics(_id)));
 //BA.debugLineNum = 387;BA.debugLine="Return jo.RunMethod(\"get\", Array(StaticCameraChar";
if (true) return _jo.RunMethod("get",new Object[]{_staticcameracharacteristics.GetField(_field)});
 //BA.debugLineNum = 388;BA.debugLine="End Sub";
return null;
}
public Object  _getfromcapturerequest(Object _capturerequest,String _field) throws Exception{
anywheresoftware.b4j.object.JavaObject _jo = null;
 //BA.debugLineNum = 395;BA.debugLine="Private Sub GetFromCaptureRequest(CaptureRequest A";
 //BA.debugLineNum = 396;BA.debugLine="Dim jo As JavaObject = CaptureRequest";
_jo = new anywheresoftware.b4j.object.JavaObject();
_jo = (anywheresoftware.b4j.object.JavaObject) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4j.object.JavaObject(), (java.lang.Object)(_capturerequest));
 //BA.debugLineNum = 397;BA.debugLine="Return jo.RunMethod(\"get\", Array(StaticCaptureReq";
if (true) return _jo.RunMethod("get",new Object[]{_staticcapturerequest.GetField(_field)});
 //BA.debugLineNum = 398;BA.debugLine="End Sub";
return null;
}
public Object  _getfromcaptureresult(Object _captureresult,String _field) throws Exception{
anywheresoftware.b4j.object.JavaObject _jresult = null;
 //BA.debugLineNum = 390;BA.debugLine="Private Sub GetFromCaptureResult(CaptureResult As";
 //BA.debugLineNum = 391;BA.debugLine="Dim jresult As JavaObject = CaptureResult";
_jresult = new anywheresoftware.b4j.object.JavaObject();
_jresult = (anywheresoftware.b4j.object.JavaObject) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4j.object.JavaObject(), (java.lang.Object)(_captureresult));
 //BA.debugLineNum = 392;BA.debugLine="Return jresult.RunMethod(\"get\", Array(StaticCaptu";
if (true) return _jresult.RunMethod("get",new Object[]{_staticcaptureresult.GetField(_field)});
 //BA.debugLineNum = 393;BA.debugLine="End Sub";
return null;
}
public int  _gethintorientation() throws Exception{
int _sensororientation = 0;
int _front = 0;
int _orientation = 0;
 //BA.debugLineNum = 117;BA.debugLine="Private Sub GetHintOrientation As Int";
 //BA.debugLineNum = 118;BA.debugLine="Dim SensorOrientation As Int = GetFromCameraChara";
_sensororientation = (int)(BA.ObjectToNumber(_getfromcameracharacteristic("SENSOR_ORIENTATION")));
 //BA.debugLineNum = 119;BA.debugLine="Dim front As Int = -1";
_front = (int) (-1);
 //BA.debugLineNum = 120;BA.debugLine="If getIsFrontFacingCamera Then front = 1";
if (_getisfrontfacingcamera()) { 
_front = (int) (1);};
 //BA.debugLineNum = 121;BA.debugLine="Dim orientation As Int = (SensorOrientation + jca";
_orientation = (int) ((_sensororientation+(double)(BA.ObjectToNumber(_jcamera.GetField("lastKnownOrientation")))*_front+360)%360);
 //BA.debugLineNum = 122;BA.debugLine="Return orientation";
if (true) return _orientation;
 //BA.debugLineNum = 123;BA.debugLine="End Sub";
return 0;
}
public boolean  _getisfrontfacingcamera() throws Exception{
 //BA.debugLineNum = 381;BA.debugLine="Public Sub getIsFrontFacingCamera As Boolean";
 //BA.debugLineNum = 382;BA.debugLine="Return 0 = GetFromCameraCharacteristic(\"LENS_FACI";
if (true) return 0==(double)(BA.ObjectToNumber(_getfromcameracharacteristic("LENS_FACING")));
 //BA.debugLineNum = 383;BA.debugLine="End Sub";
return false;
}
public float  _getmaxdigitalzoom() throws Exception{
 //BA.debugLineNum = 228;BA.debugLine="Public Sub getMaxDigitalZoom As Float";
 //BA.debugLineNum = 229;BA.debugLine="Return GetFromCameraCharacteristic(\"SCALER_AVAILA";
if (true) return (float)(BA.ObjectToNumber(_getfromcameracharacteristic("SCALER_AVAILABLE_MAX_DIGITAL_ZOOM")));
 //BA.debugLineNum = 230;BA.debugLine="End Sub";
return 0f;
}
public anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper  _getpreviewbitmap(int _width,int _height) throws Exception{
anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper _mbmp = null;
anywheresoftware.b4j.object.JavaObject _jo = null;
 //BA.debugLineNum = 356;BA.debugLine="Public Sub GetPreviewBitmap(Width As Int, Height A";
 //BA.debugLineNum = 357;BA.debugLine="Dim mbmp As Bitmap";
_mbmp = new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper();
 //BA.debugLineNum = 358;BA.debugLine="mbmp.InitializeMutable(Width, Height)";
_mbmp.InitializeMutable(_width,_height);
 //BA.debugLineNum = 359;BA.debugLine="Dim jo As JavaObject = tv";
_jo = new anywheresoftware.b4j.object.JavaObject();
_jo = (anywheresoftware.b4j.object.JavaObject) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4j.object.JavaObject(), (java.lang.Object)(_tv.getObject()));
 //BA.debugLineNum = 360;BA.debugLine="Return jo.RunMethod(\"getBitmap\", Array(mbmp))";
if (true) return (anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper(), (android.graphics.Bitmap)(_jo.RunMethod("getBitmap",new Object[]{(Object)(_mbmp.getObject())})));
 //BA.debugLineNum = 361;BA.debugLine="End Sub";
return null;
}
public String  _getscenemode() throws Exception{
 //BA.debugLineNum = 186;BA.debugLine="Public Sub getSceneMode As String";
 //BA.debugLineNum = 187;BA.debugLine="Return IntToConst(GetFromCaptureRequest(PreviewRe";
if (true) return _inttoconst(_getfromcapturerequest((Object)(_previewrequest.getObject()),"CONTROL_SCENE_MODE"),_scene_mode);
 //BA.debugLineNum = 188;BA.debugLine="End Sub";
return "";
}
public anywheresoftware.b4a.objects.collections.List  _getsupportedautoexposuremodes() throws Exception{
 //BA.debugLineNum = 233;BA.debugLine="Public Sub getSupportedAutoExposureModes As List";
 //BA.debugLineNum = 234;BA.debugLine="Return IntsToConstsList(GetFromCameraCharacterist";
if (true) return _intstoconstslist(_getfromcameracharacteristic("CONTROL_AE_AVAILABLE_MODES"),_ae_mode);
 //BA.debugLineNum = 235;BA.debugLine="End Sub";
return null;
}
public anywheresoftware.b4a.objects.collections.List  _getsupportedcapturesizes() throws Exception{
 //BA.debugLineNum = 170;BA.debugLine="Public Sub getSupportedCaptureSizes As List";
 //BA.debugLineNum = 171;BA.debugLine="Return Camera.GetSupportedCaptureSizes(id)";
if (true) return _camera.GetSupportedCaptureSizes(_id);
 //BA.debugLineNum = 172;BA.debugLine="End Sub";
return null;
}
public anywheresoftware.b4a.objects.collections.List  _getsupportedeffectmodes() throws Exception{
 //BA.debugLineNum = 198;BA.debugLine="Public Sub getSupportedEffectModes As List";
 //BA.debugLineNum = 199;BA.debugLine="Return IntsToConstsList(GetFromCameraCharacterist";
if (true) return _intstoconstslist(_getfromcameracharacteristic("CONTROL_AVAILABLE_EFFECTS"),_effect_mode);
 //BA.debugLineNum = 200;BA.debugLine="End Sub";
return null;
}
public anywheresoftware.b4a.objects.collections.List  _getsupportedfocusmodes() throws Exception{
 //BA.debugLineNum = 202;BA.debugLine="Public Sub getSupportedFocusModes As List";
 //BA.debugLineNum = 203;BA.debugLine="Return RemoveDuplicates(IntsToConstsList(GetFromC";
if (true) return _removeduplicates(_intstoconstslist(_getfromcameracharacteristic("CONTROL_AF_AVAILABLE_MODES"),_af_mode));
 //BA.debugLineNum = 204;BA.debugLine="End Sub";
return null;
}
public String  _getsupportedhardwarelevel() throws Exception{
anywheresoftware.b4j.object.JavaObject _jo = null;
 //BA.debugLineNum = 82;BA.debugLine="Public Sub getSupportedHardwareLevel As String";
 //BA.debugLineNum = 83;BA.debugLine="Dim jo As JavaObject = Camera.GetCameraCharacteri";
_jo = new anywheresoftware.b4j.object.JavaObject();
_jo = (anywheresoftware.b4j.object.JavaObject) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4j.object.JavaObject(), (java.lang.Object)(_camera.GetCameraCharacteristics(BA.NumberToString(0))));
 //BA.debugLineNum = 84;BA.debugLine="Return IntToConst(jo.RunMethod(\"get\", Array(Stati";
if (true) return _inttoconst(_jo.RunMethod("get",new Object[]{_staticcameracharacteristics.GetField("INFO_SUPPORTED_HARDWARE_LEVEL")}),_supported_hardware_level);
 //BA.debugLineNum = 85;BA.debugLine="End Sub";
return "";
}
public anywheresoftware.b4a.objects.collections.List  _getsupportedpreviewsizes() throws Exception{
 //BA.debugLineNum = 166;BA.debugLine="Public Sub getSupportedPreviewSizes As List";
 //BA.debugLineNum = 167;BA.debugLine="Return Camera.GetSupportedPreviewSizes(id)";
if (true) return _camera.GetSupportedPreviewSizes(_id);
 //BA.debugLineNum = 168;BA.debugLine="End Sub";
return null;
}
public anywheresoftware.b4a.objects.collections.List  _getsupportedscenemodes() throws Exception{
 //BA.debugLineNum = 178;BA.debugLine="Public Sub getSupportedSceneModes As List";
 //BA.debugLineNum = 179;BA.debugLine="Return RemoveDuplicates(IntsToConstsList(GetFromC";
if (true) return _removeduplicates(_intstoconstslist(_getfromcameracharacteristic("CONTROL_AVAILABLE_SCENE_MODES"),_scene_mode));
 //BA.debugLineNum = 180;BA.debugLine="End Sub";
return null;
}
public anywheresoftware.b4a.objects.collections.List  _getsupportedvideosizes() throws Exception{
 //BA.debugLineNum = 174;BA.debugLine="Public Sub getSupportedVideoSizes As List";
 //BA.debugLineNum = 175;BA.debugLine="Return Camera.GetSupportedVideoSizes(id)";
if (true) return _camera.GetSupportedVideoSizes(_id);
 //BA.debugLineNum = 176;BA.debugLine="End Sub";
return null;
}
public String  _initialize(anywheresoftware.b4a.BA _ba,anywheresoftware.b4a.objects.PanelWrapper _camerapanel) throws Exception{
innerInitialize(_ba);
 //BA.debugLineNum = 35;BA.debugLine="Public Sub Initialize (CameraPanel As Panel)";
 //BA.debugLineNum = 36;BA.debugLine="mPanel = CameraPanel";
_mpanel = _camerapanel;
 //BA.debugLineNum = 37;BA.debugLine="Camera.Initialize(\"Camera\")";
_camera.Initialize(ba,"Camera");
 //BA.debugLineNum = 38;BA.debugLine="jcamera = Camera";
_jcamera = (anywheresoftware.b4j.object.JavaObject) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4j.object.JavaObject(), (java.lang.Object)(_camera));
 //BA.debugLineNum = 39;BA.debugLine="PreviewSize.Initialize(640, 480)";
_previewsize.Initialize((int) (640),(int) (480));
 //BA.debugLineNum = 40;BA.debugLine="CaptureSize.Initialize(1920, 1080)";
_capturesize.Initialize((int) (1920),(int) (1080));
 //BA.debugLineNum = 41;BA.debugLine="StaticCaptureRequest.InitializeStatic(\"android.ha";
_staticcapturerequest.InitializeStatic("android.hardware.camera2.CaptureRequest");
 //BA.debugLineNum = 42;BA.debugLine="StaticCameraCharacteristics.InitializeStatic(\"and";
_staticcameracharacteristics.InitializeStatic("android.hardware.camera2.CameraCharacteristics");
 //BA.debugLineNum = 43;BA.debugLine="StaticCaptureResult.InitializeStatic(\"android.har";
_staticcaptureresult.InitializeStatic("android.hardware.camera2.CaptureResult");
 //BA.debugLineNum = 44;BA.debugLine="PreviewSettingsMap.Initialize";
_previewsettingsmap.Initialize();
 //BA.debugLineNum = 45;BA.debugLine="CaptureSettingMap.Initialize";
_capturesettingmap.Initialize();
 //BA.debugLineNum = 46;BA.debugLine="bothMaps = Array(PreviewSettingsMap, CaptureSetti";
_bothmaps = anywheresoftware.b4a.keywords.Common.ArrayToList(new Object[]{(Object)(_previewsettingsmap.getObject()),(Object)(_capturesettingmap.getObject())});
 //BA.debugLineNum = 47;BA.debugLine="End Sub";
return "";
}
public anywheresoftware.b4a.objects.collections.List  _intstoconstslist(Object _ints,anywheresoftware.b4a.objects.collections.List _consts) throws Exception{
anywheresoftware.b4a.objects.collections.List _res = null;
int[] _f = null;
int _mode = 0;
 //BA.debugLineNum = 413;BA.debugLine="Private Sub IntsToConstsList (Ints As Object, Cons";
 //BA.debugLineNum = 414;BA.debugLine="Dim res As List";
_res = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 415;BA.debugLine="res.Initialize";
_res.Initialize();
 //BA.debugLineNum = 416;BA.debugLine="If Ints = Null Then Return res";
if (_ints== null) { 
if (true) return _res;};
 //BA.debugLineNum = 417;BA.debugLine="Dim f() As Int = Ints";
_f = (int[])(_ints);
 //BA.debugLineNum = 418;BA.debugLine="For Each mode As Int In f";
{
final int[] group5 = _f;
final int groupLen5 = group5.length
;int index5 = 0;
;
for (; index5 < groupLen5;index5++){
_mode = group5[index5];
 //BA.debugLineNum = 419;BA.debugLine="If mode >=0 And mode < Consts.Size Then";
if (_mode>=0 && _mode<_consts.getSize()) { 
 //BA.debugLineNum = 420;BA.debugLine="res.Add(Consts.Get(mode))";
_res.Add(_consts.Get(_mode));
 }else {
 //BA.debugLineNum = 422;BA.debugLine="Log(Consts) 'ignore";
__c.LogImpl("624707081",BA.ObjectToString(_consts),0);
 //BA.debugLineNum = 423;BA.debugLine="Log(\"Unknown consts: \" & mode)";
__c.LogImpl("624707082","Unknown consts: "+BA.NumberToString(_mode),0);
 };
 }
};
 //BA.debugLineNum = 426;BA.debugLine="Return res";
if (true) return _res;
 //BA.debugLineNum = 427;BA.debugLine="End Sub";
return null;
}
public anywheresoftware.b4a.objects.collections.List  _intstolist(Object _obj) throws Exception{
anywheresoftware.b4a.objects.collections.List _res = null;
int[] _i = null;
int _ii = 0;
 //BA.debugLineNum = 500;BA.debugLine="Private Sub IntsToList (Obj As Object) As List";
 //BA.debugLineNum = 501;BA.debugLine="Dim res As List";
_res = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 502;BA.debugLine="res.Initialize";
_res.Initialize();
 //BA.debugLineNum = 503;BA.debugLine="If Obj = Null Then Return res";
if (_obj== null) { 
if (true) return _res;};
 //BA.debugLineNum = 504;BA.debugLine="Dim i() As Int = Obj";
_i = (int[])(_obj);
 //BA.debugLineNum = 505;BA.debugLine="For Each ii As Int In i";
{
final int[] group5 = _i;
final int groupLen5 = group5.length
;int index5 = 0;
;
for (; index5 < groupLen5;index5++){
_ii = group5[index5];
 //BA.debugLineNum = 506;BA.debugLine="res.Add(ii)";
_res.Add((Object)(_ii));
 }
};
 //BA.debugLineNum = 508;BA.debugLine="Return res";
if (true) return _res;
 //BA.debugLineNum = 509;BA.debugLine="End Sub";
return null;
}
public String  _inttoconst(Object _int1,anywheresoftware.b4a.objects.collections.List _consts) throws Exception{
int _i = 0;
 //BA.debugLineNum = 401;BA.debugLine="Private Sub IntToConst (Int1 As Object, Consts As";
 //BA.debugLineNum = 402;BA.debugLine="If Int1 = Null Then Return \"\"";
if (_int1== null) { 
if (true) return "";};
 //BA.debugLineNum = 403;BA.debugLine="Dim i As Int = Int1";
_i = (int)(BA.ObjectToNumber(_int1));
 //BA.debugLineNum = 404;BA.debugLine="If i >= 0 And i < Consts.Size Then";
if (_i>=0 && _i<_consts.getSize()) { 
 //BA.debugLineNum = 405;BA.debugLine="Return Consts.Get(i)";
if (true) return BA.ObjectToString(_consts.Get(_i));
 }else {
 //BA.debugLineNum = 407;BA.debugLine="Log(Consts)'ignore";
__c.LogImpl("624641542",BA.ObjectToString(_consts),0);
 //BA.debugLineNum = 408;BA.debugLine="Log(\"Unknown const: \" & i)";
__c.LogImpl("624641543","Unknown const: "+BA.NumberToString(_i),0);
 //BA.debugLineNum = 409;BA.debugLine="Return \"\"";
if (true) return "";
 };
 //BA.debugLineNum = 411;BA.debugLine="End Sub";
return "";
}
public anywheresoftware.b4a.objects.collections.List  _objectstolist(Object _obj) throws Exception{
anywheresoftware.b4a.objects.collections.List _res = null;
Object[] _o = null;
Object _oo = null;
 //BA.debugLineNum = 466;BA.debugLine="Private Sub ObjectsToList(Obj As Object) As List";
 //BA.debugLineNum = 467;BA.debugLine="Dim res As List";
_res = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 468;BA.debugLine="res.Initialize";
_res.Initialize();
 //BA.debugLineNum = 469;BA.debugLine="If Obj = Null Then Return res";
if (_obj== null) { 
if (true) return _res;};
 //BA.debugLineNum = 470;BA.debugLine="Dim o() As Object = Obj";
_o = (Object[])(_obj);
 //BA.debugLineNum = 471;BA.debugLine="For Each oo As Object In o";
{
final Object[] group5 = _o;
final int groupLen5 = group5.length
;int index5 = 0;
;
for (; index5 < groupLen5;index5++){
_oo = group5[index5];
 //BA.debugLineNum = 472;BA.debugLine="res.Add(oo)";
_res.Add(_oo);
 }
};
 //BA.debugLineNum = 474;BA.debugLine="Return res";
if (true) return _res;
 //BA.debugLineNum = 475;BA.debugLine="End Sub";
return null;
}
public anywheresoftware.b4a.keywords.Common.ResumableSubWrapper  _opencamera(boolean _front) throws Exception{
ResumableSub_OpenCamera rsub = new ResumableSub_OpenCamera(this,_front);
rsub.resume(ba, null);
return (anywheresoftware.b4a.keywords.Common.ResumableSubWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.keywords.Common.ResumableSubWrapper(), rsub);
}
public static class ResumableSub_OpenCamera extends BA.ResumableSub {
public ResumableSub_OpenCamera(appear.pnud.preservamos.camex2 parent,boolean _front) {
this.parent = parent;
this._front = _front;
}
appear.pnud.preservamos.camex2 parent;
boolean _front;
boolean _open = false;

@Override
public void resume(BA ba, Object[] result) throws Exception{

    while (true) {
        switch (state) {
            case -1:
{
parent.__c.ReturnFromResumableSub(this,null);return;}
case 0:
//C
this.state = 1;
 //BA.debugLineNum = 52;BA.debugLine="TaskIndex = TaskIndex + 1";
parent._taskindex = (int) (parent._taskindex+1);
 //BA.debugLineNum = 53;BA.debugLine="If Camera.IsCameraOpen Then";
if (true) break;

case 1:
//if
this.state = 4;
if (parent._camera.getIsCameraOpen()) { 
this.state = 3;
}if (true) break;

case 3:
//C
this.state = 4;
 //BA.debugLineNum = 54;BA.debugLine="Stop";
parent._stop();
 if (true) break;

case 4:
//C
this.state = 5;
;
 //BA.debugLineNum = 56;BA.debugLine="mFront = Front";
parent._mfront = _front;
 //BA.debugLineNum = 57;BA.debugLine="id = Camera.FindCameraId(mFront)";
parent._id = parent._camera.FindCameraId(parent._mfront);
 //BA.debugLineNum = 58;BA.debugLine="If id = \"\" Then";
if (true) break;

case 5:
//if
this.state = 8;
if ((parent._id).equals("")) { 
this.state = 7;
}if (true) break;

case 7:
//C
this.state = 8;
 //BA.debugLineNum = 59;BA.debugLine="Log(\"Camera not found.\")";
parent.__c.LogImpl("621823496","Camera not found.",0);
 //BA.debugLineNum = 60;BA.debugLine="id = Camera.CameraIDs(0)";
parent._id = parent._camera.getCameraIDs()[(int) (0)];
 if (true) break;

case 8:
//C
this.state = 9;
;
 //BA.debugLineNum = 62;BA.debugLine="Camera.OpenCamera(id)";
parent._camera.OpenCamera(parent._id);
 //BA.debugLineNum = 63;BA.debugLine="Wait For Camera_CameraState (Open As Boolean)";
parent.__c.WaitFor("camera_camerastate", ba, this, null);
this.state = 18;
return;
case 18:
//C
this.state = 9;
_open = (Boolean) result[0];
;
 //BA.debugLineNum = 64;BA.debugLine="If Open = False Then";
if (true) break;

case 9:
//if
this.state = 12;
if (_open==parent.__c.False) { 
this.state = 11;
}if (true) break;

case 11:
//C
this.state = 12;
 //BA.debugLineNum = 65;BA.debugLine="Log(\"Failed to open camera\")";
parent.__c.LogImpl("621823502","Failed to open camera",0);
 //BA.debugLineNum = 66;BA.debugLine="Return 0";
if (true) {
parent.__c.ReturnFromResumableSub(this,(Object)(0));return;};
 if (true) break;
;
 //BA.debugLineNum = 68;BA.debugLine="If PrintKeys Then PrintAllKeys(Camera.GetCameraCh";

case 12:
//if
this.state = 17;
if (parent._printkeys) { 
this.state = 14;
;}if (true) break;

case 14:
//C
this.state = 17;
parent._printallkeys(parent._camera.GetCameraCharacteristics(parent._id),"Camera Characteristics");
if (true) break;

case 17:
//C
this.state = -1;
;
 //BA.debugLineNum = 69;BA.debugLine="Return TaskIndex";
if (true) {
parent.__c.ReturnFromResumableSub(this,(Object)(parent._taskindex));return;};
 //BA.debugLineNum = 70;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public void  _camera_camerastate(boolean _open) throws Exception{
}
public anywheresoftware.b4a.keywords.Common.ResumableSubWrapper  _preparesurface(int _mytaskindex) throws Exception{
ResumableSub_PrepareSurface rsub = new ResumableSub_PrepareSurface(this,_mytaskindex);
rsub.resume(ba, null);
return (anywheresoftware.b4a.keywords.Common.ResumableSubWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.keywords.Common.ResumableSubWrapper(), rsub);
}
public static class ResumableSub_PrepareSurface extends BA.ResumableSub {
public ResumableSub_PrepareSurface(appear.pnud.preservamos.camex2 parent,int _mytaskindex) {
this.parent = parent;
this._mytaskindex = _mytaskindex;
}
appear.pnud.preservamos.camex2 parent;
int _mytaskindex;
boolean _result = false;
boolean _success = false;

@Override
public void resume(BA ba, Object[] result) throws Exception{

    while (true) {
        switch (state) {
            case -1:
{
parent.__c.ReturnFromResumableSub(this,null);return;}
case 0:
//C
this.state = 1;
 //BA.debugLineNum = 90;BA.debugLine="If MyTaskIndex <> TaskIndex Then Return False";
if (true) break;

case 1:
//if
this.state = 6;
if (_mytaskindex!=parent._taskindex) { 
this.state = 3;
;}if (true) break;

case 3:
//C
this.state = 6;
if (true) {
parent.__c.ReturnFromResumableSub(this,(Object)(parent.__c.False));return;};
if (true) break;

case 6:
//C
this.state = 7;
;
 //BA.debugLineNum = 91;BA.debugLine="CloseSession";
parent._closesession();
 //BA.debugLineNum = 92;BA.debugLine="Wait For (CreateSurface) Complete (Result As Bool";
parent.__c.WaitFor("complete", ba, this, parent._createsurface());
this.state = 19;
return;
case 19:
//C
this.state = 7;
_result = (Boolean) result[0];
;
 //BA.debugLineNum = 93;BA.debugLine="If MyTaskIndex <> TaskIndex Then Return False";
if (true) break;

case 7:
//if
this.state = 12;
if (_mytaskindex!=parent._taskindex) { 
this.state = 9;
;}if (true) break;

case 9:
//C
this.state = 12;
if (true) {
parent.__c.ReturnFromResumableSub(this,(Object)(parent.__c.False));return;};
if (true) break;

case 12:
//C
this.state = 13;
;
 //BA.debugLineNum = 94;BA.debugLine="Camera.StartSession(tv, PreviewSize, CaptureSize,";
parent._camera.StartSession((android.view.TextureView)(parent._tv.getObject()),parent._previewsize,parent._capturesize,(int) (256),(int) (0),parent.__c.False);
 //BA.debugLineNum = 95;BA.debugLine="Wait For Camera_SessionConfigured (Success As Boo";
parent.__c.WaitFor("camera_sessionconfigured", ba, this, null);
this.state = 20;
return;
case 20:
//C
this.state = 13;
_success = (Boolean) result[0];
;
 //BA.debugLineNum = 96;BA.debugLine="If MyTaskIndex <> TaskIndex Then Return False";
if (true) break;

case 13:
//if
this.state = 18;
if (_mytaskindex!=parent._taskindex) { 
this.state = 15;
;}if (true) break;

case 15:
//C
this.state = 18;
if (true) {
parent.__c.ReturnFromResumableSub(this,(Object)(parent.__c.False));return;};
if (true) break;

case 18:
//C
this.state = -1;
;
 //BA.debugLineNum = 97;BA.debugLine="Return Success";
if (true) {
parent.__c.ReturnFromResumableSub(this,(Object)(_success));return;};
 //BA.debugLineNum = 98;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public void  _camera_sessionconfigured(boolean _success) throws Exception{
}
public anywheresoftware.b4a.keywords.Common.ResumableSubWrapper  _preparesurfaceforvideo(int _mytaskindex,String _dir,String _filename) throws Exception{
ResumableSub_PrepareSurfaceForVideo rsub = new ResumableSub_PrepareSurfaceForVideo(this,_mytaskindex,_dir,_filename);
rsub.resume(ba, null);
return (anywheresoftware.b4a.keywords.Common.ResumableSubWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.keywords.Common.ResumableSubWrapper(), rsub);
}
public static class ResumableSub_PrepareSurfaceForVideo extends BA.ResumableSub {
public ResumableSub_PrepareSurfaceForVideo(appear.pnud.preservamos.camex2 parent,int _mytaskindex,String _dir,String _filename) {
this.parent = parent;
this._mytaskindex = _mytaskindex;
this._dir = _dir;
this._filename = _filename;
}
appear.pnud.preservamos.camex2 parent;
int _mytaskindex;
String _dir;
String _filename;
boolean _result = false;
boolean _success = false;

@Override
public void resume(BA ba, Object[] result) throws Exception{

    while (true) {
        switch (state) {
            case -1:
{
parent.__c.ReturnFromResumableSub(this,null);return;}
case 0:
//C
this.state = 1;
 //BA.debugLineNum = 104;BA.debugLine="If MyTaskIndex <> TaskIndex Then Return False";
if (true) break;

case 1:
//if
this.state = 6;
if (_mytaskindex!=parent._taskindex) { 
this.state = 3;
;}if (true) break;

case 3:
//C
this.state = 6;
if (true) {
parent.__c.ReturnFromResumableSub(this,(Object)(parent.__c.False));return;};
if (true) break;

case 6:
//C
this.state = 7;
;
 //BA.debugLineNum = 105;BA.debugLine="CloseSession";
parent._closesession();
 //BA.debugLineNum = 106;BA.debugLine="Wait For (CreateSurface) Complete (Result As Bool";
parent.__c.WaitFor("complete", ba, this, parent._createsurface());
this.state = 19;
return;
case 19:
//C
this.state = 7;
_result = (Boolean) result[0];
;
 //BA.debugLineNum = 107;BA.debugLine="If MyTaskIndex <> TaskIndex Then Return False";
if (true) break;

case 7:
//if
this.state = 12;
if (_mytaskindex!=parent._taskindex) { 
this.state = 9;
;}if (true) break;

case 9:
//C
this.state = 12;
if (true) {
parent.__c.ReturnFromResumableSub(this,(Object)(parent.__c.False));return;};
if (true) break;

case 12:
//C
this.state = 13;
;
 //BA.debugLineNum = 108;BA.debugLine="File.Delete(Dir, FileName)";
parent.__c.File.Delete(_dir,_filename);
 //BA.debugLineNum = 109;BA.debugLine="MediaRecorder = Camera.CreateMediaRecorder(Previe";
parent._mediarecorder = (anywheresoftware.b4j.object.JavaObject) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4j.object.JavaObject(), (java.lang.Object)(parent._camera.CreateMediaRecorder(parent._previewsize,_dir,_filename)));
 //BA.debugLineNum = 110;BA.debugLine="MediaRecorder.RunMethod(\"setOrientationHint\", Arr";
parent._mediarecorder.RunMethod("setOrientationHint",new Object[]{(Object)(parent._gethintorientation())});
 //BA.debugLineNum = 111;BA.debugLine="Camera.StartSession(tv, PreviewSize, CaptureSize,";
parent._camera.StartSession((android.view.TextureView)(parent._tv.getObject()),parent._previewsize,parent._capturesize,(int) (0),(int) (0),parent.__c.True);
 //BA.debugLineNum = 112;BA.debugLine="Wait For Camera_SessionConfigured (Success As Boo";
parent.__c.WaitFor("camera_sessionconfigured", ba, this, null);
this.state = 20;
return;
case 20:
//C
this.state = 13;
_success = (Boolean) result[0];
;
 //BA.debugLineNum = 113;BA.debugLine="If MyTaskIndex <> TaskIndex Then Return False";
if (true) break;

case 13:
//if
this.state = 18;
if (_mytaskindex!=parent._taskindex) { 
this.state = 15;
;}if (true) break;

case 15:
//C
this.state = 18;
if (true) {
parent.__c.ReturnFromResumableSub(this,(Object)(parent.__c.False));return;};
if (true) break;

case 18:
//C
this.state = -1;
;
 //BA.debugLineNum = 114;BA.debugLine="Return Success";
if (true) {
parent.__c.ReturnFromResumableSub(this,(Object)(_success));return;};
 //BA.debugLineNum = 115;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public String  _printallkeys(Object _cameramap,String _title) throws Exception{
anywheresoftware.b4j.object.JavaObject _jo = null;
anywheresoftware.b4a.objects.collections.List _keys = null;
anywheresoftware.b4j.object.JavaObject _k = null;
Object _value = null;
String _typ = "";
 //BA.debugLineNum = 444;BA.debugLine="Private Sub PrintAllKeys (CameraMap As Object, tit";
 //BA.debugLineNum = 445;BA.debugLine="Log($\"******  ${title} **********\"$)";
__c.LogImpl("624903681",("******  "+__c.SmartStringFormatter("",(Object)(_title))+" **********"),0);
 //BA.debugLineNum = 446;BA.debugLine="Dim jo As JavaObject = CameraMap";
_jo = new anywheresoftware.b4j.object.JavaObject();
_jo = (anywheresoftware.b4j.object.JavaObject) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4j.object.JavaObject(), (java.lang.Object)(_cameramap));
 //BA.debugLineNum = 447;BA.debugLine="Dim keys As List = jo.RunMethod(\"getKeys\", Null)";
_keys = new anywheresoftware.b4a.objects.collections.List();
_keys = (anywheresoftware.b4a.objects.collections.List) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.collections.List(), (java.util.List)(_jo.RunMethod("getKeys",(Object[])(__c.Null))));
 //BA.debugLineNum = 448;BA.debugLine="For Each k As JavaObject In keys";
_k = new anywheresoftware.b4j.object.JavaObject();
{
final anywheresoftware.b4a.BA.IterableList group4 = _keys;
final int groupLen4 = group4.getSize()
;int index4 = 0;
;
for (; index4 < groupLen4;index4++){
_k = (anywheresoftware.b4j.object.JavaObject) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4j.object.JavaObject(), (java.lang.Object)(group4.Get(index4)));
 //BA.debugLineNum = 449;BA.debugLine="Dim value As Object = jo.RunMethod(\"get\", Array(";
_value = _jo.RunMethod("get",new Object[]{(Object)(_k.getObject())});
 //BA.debugLineNum = 450;BA.debugLine="If value = Null Then Continue";
if (_value== null) { 
if (true) continue;};
 //BA.debugLineNum = 451;BA.debugLine="Dim typ As String = GetType(value)";
_typ = __c.GetType(_value);
 //BA.debugLineNum = 452;BA.debugLine="If typ = \"[F\" Then";
if ((_typ).equals("[F")) { 
 //BA.debugLineNum = 453;BA.debugLine="value = FloatsToList(value)";
_value = (Object)(_floatstolist(_value).getObject());
 }else if((_typ).equals("[I")) { 
 //BA.debugLineNum = 455;BA.debugLine="value = IntsToList(value)";
_value = (Object)(_intstolist(_value).getObject());
 }else if((_typ).equals("[Z")) { 
 //BA.debugLineNum = 457;BA.debugLine="value = BoolsToList(value)";
_value = (Object)(_boolstolist(_value).getObject());
 }else if((_typ).equals("[B")) { 
 //BA.debugLineNum = 459;BA.debugLine="value = BytesToList(value)";
_value = (Object)(_bytestolist(_value).getObject());
 }else if(_typ.startsWith("[")) { 
 //BA.debugLineNum = 461;BA.debugLine="value = ObjectsToList(value)";
_value = (Object)(_objectstolist(_value).getObject());
 };
 //BA.debugLineNum = 463;BA.debugLine="Log($\"${k.RunMethod(\"getName\", Null)}: ${value}\"";
__c.LogImpl("624903699",(""+__c.SmartStringFormatter("",_k.RunMethod("getName",(Object[])(__c.Null)))+": "+__c.SmartStringFormatter("",_value)+""),0);
 }
};
 //BA.debugLineNum = 465;BA.debugLine="End Sub";
return "";
}
public anywheresoftware.b4a.objects.collections.List  _removeduplicates(anywheresoftware.b4a.objects.collections.List _raw) throws Exception{
anywheresoftware.b4a.objects.collections.Map _unique = null;
String _f = "";
 //BA.debugLineNum = 206;BA.debugLine="Private Sub RemoveDuplicates(Raw As List) As List";
 //BA.debugLineNum = 207;BA.debugLine="Dim unique As Map";
_unique = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 208;BA.debugLine="unique.Initialize";
_unique.Initialize();
 //BA.debugLineNum = 209;BA.debugLine="For Each f As String In Raw";
{
final anywheresoftware.b4a.BA.IterableList group3 = _raw;
final int groupLen3 = group3.getSize()
;int index3 = 0;
;
for (; index3 < groupLen3;index3++){
_f = BA.ObjectToString(group3.Get(index3));
 //BA.debugLineNum = 210;BA.debugLine="unique.Put(f, \"\")";
_unique.Put((Object)(_f),(Object)(""));
 }
};
 //BA.debugLineNum = 212;BA.debugLine="Raw.Clear";
_raw.Clear();
 //BA.debugLineNum = 213;BA.debugLine="For Each f As String In unique.Keys";
{
final anywheresoftware.b4a.BA.IterableList group7 = _unique.Keys();
final int groupLen7 = group7.getSize()
;int index7 = 0;
;
for (; index7 < groupLen7;index7++){
_f = BA.ObjectToString(group7.Get(index7));
 //BA.debugLineNum = 214;BA.debugLine="Raw.Add(f)";
_raw.Add((Object)(_f));
 }
};
 //BA.debugLineNum = 216;BA.debugLine="Return Raw";
if (true) return _raw;
 //BA.debugLineNum = 217;BA.debugLine="End Sub";
return null;
}
public String  _setautoexposuremode(String _mode) throws Exception{
 //BA.debugLineNum = 245;BA.debugLine="Public Sub setAutoExposureMode (Mode As String)";
 //BA.debugLineNum = 246;BA.debugLine="SetBothMaps(\"CONTROL_AE_MODE\", AE_MODE.IndexOf(Mo";
_setbothmaps("CONTROL_AE_MODE",(Object)(_ae_mode.IndexOf((Object)(_mode))));
 //BA.debugLineNum = 247;BA.debugLine="PreviewSettingsMap.Put(\"FLASH_MODE\", FLASH_MODE.I";
_previewsettingsmap.Put((Object)("FLASH_MODE"),(Object)(_flash_mode.IndexOf((Object)("OFF"))));
 //BA.debugLineNum = 248;BA.debugLine="If Mode = \"ON_ALWAYS_FLASH\" Then";
if ((_mode).equals("ON_ALWAYS_FLASH")) { 
 //BA.debugLineNum = 249;BA.debugLine="CaptureSettingMap.Put(\"FLASH_MODE\", FLASH_MODE.I";
_capturesettingmap.Put((Object)("FLASH_MODE"),(Object)(_flash_mode.IndexOf((Object)("SINGLE"))));
 };
 //BA.debugLineNum = 251;BA.debugLine="End Sub";
return "";
}
public String  _setbothmaps(String _key,Object _value) throws Exception{
anywheresoftware.b4a.objects.collections.Map _m = null;
 //BA.debugLineNum = 263;BA.debugLine="Private Sub SetBothMaps(Key As String, Value As Ob";
 //BA.debugLineNum = 264;BA.debugLine="For Each m As Map In bothMaps";
_m = new anywheresoftware.b4a.objects.collections.Map();
{
final anywheresoftware.b4a.BA.IterableList group1 = _bothmaps;
final int groupLen1 = group1.getSize()
;int index1 = 0;
;
for (; index1 < groupLen1;index1++){
_m = (anywheresoftware.b4a.objects.collections.Map) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.collections.Map(), (anywheresoftware.b4a.objects.collections.Map.MyMap)(group1.Get(index1)));
 //BA.debugLineNum = 265;BA.debugLine="m.Put(Key, Value)";
_m.Put((Object)(_key),_value);
 }
};
 //BA.debugLineNum = 267;BA.debugLine="End Sub";
return "";
}
public String  _seteffectmode(String _mode) throws Exception{
 //BA.debugLineNum = 190;BA.debugLine="Public Sub setEffectMode(Mode As String)";
 //BA.debugLineNum = 191;BA.debugLine="SetBothMaps(\"CONTROL_EFFECT_MODE\", EFFECT_MODE.In";
_setbothmaps("CONTROL_EFFECT_MODE",(Object)(_effect_mode.IndexOf((Object)(_mode))));
 //BA.debugLineNum = 192;BA.debugLine="End Sub";
return "";
}
public String  _setfocusmode(String _mode) throws Exception{
 //BA.debugLineNum = 223;BA.debugLine="Public Sub setFocusMode(mode As String)";
 //BA.debugLineNum = 224;BA.debugLine="SetBothMaps(\"CONTROL_AF_MODE\", AF_MODE.IndexOf(mo";
_setbothmaps("CONTROL_AF_MODE",(Object)(_af_mode.IndexOf((Object)(_mode))));
 //BA.debugLineNum = 225;BA.debugLine="End Sub";
return "";
}
public String  _setpreviewcropregion(anywheresoftware.b4a.objects.drawable.CanvasWrapper.RectWrapper _r) throws Exception{
 //BA.debugLineNum = 254;BA.debugLine="Public Sub setPreviewCropRegion(r As Rect)";
 //BA.debugLineNum = 255;BA.debugLine="PreviewSettingsMap.Put(\"SCALER_CROP_REGION\", r)";
_previewsettingsmap.Put((Object)("SCALER_CROP_REGION"),(Object)(_r.getObject()));
 //BA.debugLineNum = 256;BA.debugLine="End Sub";
return "";
}
public String  _setscenemode(String _mode) throws Exception{
 //BA.debugLineNum = 182;BA.debugLine="Public Sub setSceneMode(Mode As String)";
 //BA.debugLineNum = 183;BA.debugLine="SetBothMaps(\"CONTROL_SCENE_MODE\", SCENE_MODE.Inde";
_setbothmaps("CONTROL_SCENE_MODE",(Object)(_scene_mode.IndexOf((Object)(_mode))));
 //BA.debugLineNum = 184;BA.debugLine="End Sub";
return "";
}
public String  _setsettingsfrommap(anywheresoftware.b4j.object.JavaObject _builder,anywheresoftware.b4a.objects.collections.Map _m) throws Exception{
String _key = "";
 //BA.debugLineNum = 429;BA.debugLine="Private Sub SetSettingsFromMap (Builder As JavaObj";
 //BA.debugLineNum = 430;BA.debugLine="For Each key As String In m.Keys";
{
final anywheresoftware.b4a.BA.IterableList group1 = _m.Keys();
final int groupLen1 = group1.getSize()
;int index1 = 0;
;
for (; index1 < groupLen1;index1++){
_key = BA.ObjectToString(group1.Get(index1));
 //BA.debugLineNum = 431;BA.debugLine="Builder.RunMethod(\"set\", Array(StaticCaptureRequ";
_builder.RunMethod("set",new Object[]{_staticcapturerequest.GetField(_key),_m.Get((Object)(_key))});
 }
};
 //BA.debugLineNum = 433;BA.debugLine="End Sub";
return "";
}
public String  _startpreview(int _mytaskindex,boolean _videorecording) throws Exception{
anywheresoftware.b4j.object.JavaObject _previewbuilder = null;
 //BA.debugLineNum = 153;BA.debugLine="Public Sub StartPreview (MyTaskIndex As Int, Video";
 //BA.debugLineNum = 154;BA.debugLine="Dim PreviewBuilder As JavaObject";
_previewbuilder = new anywheresoftware.b4j.object.JavaObject();
 //BA.debugLineNum = 155;BA.debugLine="If VideoRecording Then";
if (_videorecording) { 
 //BA.debugLineNum = 156;BA.debugLine="PreviewBuilder = Camera.CreateVideoRequestBuilde";
_previewbuilder = (anywheresoftware.b4j.object.JavaObject) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4j.object.JavaObject(), (java.lang.Object)(_camera.CreateVideoRequestBuilder()));
 }else {
 //BA.debugLineNum = 158;BA.debugLine="PreviewBuilder = Camera.CreatePreviewBuilder";
_previewbuilder = (anywheresoftware.b4j.object.JavaObject) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4j.object.JavaObject(), (java.lang.Object)(_camera.CreatePreviewBuilder()));
 };
 //BA.debugLineNum = 160;BA.debugLine="SetSettingsFromMap(PreviewBuilder, PreviewSetting";
_setsettingsfrommap(_previewbuilder,_previewsettingsmap);
 //BA.debugLineNum = 161;BA.debugLine="PreviewRequest = Camera.SetRepeatingRequest(Previ";
_previewrequest = (anywheresoftware.b4j.object.JavaObject) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4j.object.JavaObject(), (java.lang.Object)(_camera.SetRepeatingRequest((Object)(_previewbuilder.getObject()))));
 //BA.debugLineNum = 162;BA.debugLine="If PrintKeys Then PrintAllKeys(PreviewRequest, \"P";
if (_printkeys) { 
_printallkeys((Object)(_previewrequest.getObject()),"Preview Capture Request");};
 //BA.debugLineNum = 163;BA.debugLine="End Sub";
return "";
}
public String  _startvideorecording(int _mytaskindex) throws Exception{
 //BA.debugLineNum = 126;BA.debugLine="Public Sub StartVideoRecording (MyTaskIndex As Int";
 //BA.debugLineNum = 127;BA.debugLine="If MyTaskIndex <> TaskIndex Then Return";
if (_mytaskindex!=_taskindex) { 
if (true) return "";};
 //BA.debugLineNum = 128;BA.debugLine="MediaRecorder.RunMethod(\"start\", Null)";
_mediarecorder.RunMethod("start",(Object[])(__c.Null));
 //BA.debugLineNum = 129;BA.debugLine="RecordingVideo = True";
_recordingvideo = __c.True;
 //BA.debugLineNum = 130;BA.debugLine="End Sub";
return "";
}
public String  _stop() throws Exception{
 //BA.debugLineNum = 435;BA.debugLine="Public Sub Stop";
 //BA.debugLineNum = 436;BA.debugLine="RecordingVideo = False";
_recordingvideo = __c.False;
 //BA.debugLineNum = 437;BA.debugLine="Camera.Stop";
_camera.Stop();
 //BA.debugLineNum = 438;BA.debugLine="TaskIndex = TaskIndex + 1";
_taskindex = (int) (_taskindex+1);
 //BA.debugLineNum = 439;BA.debugLine="End Sub";
return "";
}
public String  _stopvideorecording(int _mytaskindex) throws Exception{
 //BA.debugLineNum = 133;BA.debugLine="Public Sub StopVideoRecording (MyTaskIndex As Int)";
 //BA.debugLineNum = 134;BA.debugLine="CloseSession";
_closesession();
 //BA.debugLineNum = 135;BA.debugLine="Try";
try { //BA.debugLineNum = 136;BA.debugLine="MediaRecorder.RunMethod(\"stop\", Null)";
_mediarecorder.RunMethod("stop",(Object[])(__c.Null));
 } 
       catch (Exception e5) {
			ba.setLastException(e5); //BA.debugLineNum = 138;BA.debugLine="Log(LastException)";
__c.LogImpl("622282245",BA.ObjectToString(__c.LastException(ba)),0);
 };
 //BA.debugLineNum = 140;BA.debugLine="RecordingVideo = False";
_recordingvideo = __c.False;
 //BA.debugLineNum = 141;BA.debugLine="End Sub";
return "";
}
public anywheresoftware.b4a.keywords.Common.ResumableSubWrapper  _takepicturenow(int _mytaskindex) throws Exception{
ResumableSub_TakePictureNow rsub = new ResumableSub_TakePictureNow(this,_mytaskindex);
rsub.resume(ba, null);
return (anywheresoftware.b4a.keywords.Common.ResumableSubWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.keywords.Common.ResumableSubWrapper(), rsub);
}
public static class ResumableSub_TakePictureNow extends BA.ResumableSub {
public ResumableSub_TakePictureNow(appear.pnud.preservamos.camex2 parent,int _mytaskindex) {
this.parent = parent;
this._mytaskindex = _mytaskindex;
}
appear.pnud.preservamos.camex2 parent;
int _mytaskindex;
anywheresoftware.b4j.object.JavaObject _builder = null;
Object _capturerequest = null;
byte[] _data = null;

@Override
public void resume(BA ba, Object[] result) throws Exception{

    while (true) {
        switch (state) {
            case -1:
{
parent.__c.ReturnFromResumableSub(this,null);return;}
case 0:
//C
this.state = 1;
 //BA.debugLineNum = 316;BA.debugLine="If MyTaskIndex <> TaskIndex Then Return False";
if (true) break;

case 1:
//if
this.state = 6;
if (_mytaskindex!=parent._taskindex) { 
this.state = 3;
;}if (true) break;

case 3:
//C
this.state = 6;
if (true) {
parent.__c.ReturnFromResumableSub(this,(Object)(parent.__c.False));return;};
if (true) break;

case 6:
//C
this.state = 7;
;
 //BA.debugLineNum = 317;BA.debugLine="Camera.AbortCaptures";
parent._camera.AbortCaptures();
 //BA.debugLineNum = 318;BA.debugLine="Dim builder As JavaObject = Camera.CreateCaptureB";
_builder = new anywheresoftware.b4j.object.JavaObject();
_builder = (anywheresoftware.b4j.object.JavaObject) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4j.object.JavaObject(), (java.lang.Object)(parent._camera.CreateCaptureBuilder()));
 //BA.debugLineNum = 319;BA.debugLine="CaptureSettingMap.Put(\"JPEG_ORIENTATION\", GetHint";
parent._capturesettingmap.Put((Object)("JPEG_ORIENTATION"),(Object)(parent._gethintorientation()));
 //BA.debugLineNum = 320;BA.debugLine="SetSettingsFromMap(builder, CaptureSettingMap)";
parent._setsettingsfrommap(_builder,parent._capturesettingmap);
 //BA.debugLineNum = 321;BA.debugLine="Dim CaptureRequest As Object = Camera.AddCaptureR";
_capturerequest = parent._camera.AddCaptureRequest((Object)(_builder.getObject()));
 //BA.debugLineNum = 322;BA.debugLine="If PrintKeys Then PrintAllKeys(CaptureRequest, \"C";
if (true) break;

case 7:
//if
this.state = 12;
if (parent._printkeys) { 
this.state = 9;
;}if (true) break;

case 9:
//C
this.state = 12;
parent._printallkeys(_capturerequest,"Capture Request");
if (true) break;

case 12:
//C
this.state = -1;
;
 //BA.debugLineNum = 323;BA.debugLine="Wait For Camera_PictureTaken (Data() As Byte)";
parent.__c.WaitFor("camera_picturetaken", ba, this, null);
this.state = 13;
return;
case 13:
//C
this.state = -1;
_data = (byte[]) result[0];
;
 //BA.debugLineNum = 324;BA.debugLine="StartPreview(MyTaskIndex, False)";
parent._startpreview(_mytaskindex,parent.__c.False);
 //BA.debugLineNum = 325;BA.debugLine="Return Data";
if (true) {
parent.__c.ReturnFromResumableSub(this,(Object)(_data));return;};
 //BA.debugLineNum = 326;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public void  _camera_picturetaken(byte[] _data) throws Exception{
}
public anywheresoftware.b4a.keywords.Common.ResumableSubWrapper  _takepicturesnow(int _mytaskindex,int _numberofpictures) throws Exception{
ResumableSub_TakePicturesNow rsub = new ResumableSub_TakePicturesNow(this,_mytaskindex,_numberofpictures);
rsub.resume(ba, null);
return (anywheresoftware.b4a.keywords.Common.ResumableSubWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.keywords.Common.ResumableSubWrapper(), rsub);
}
public static class ResumableSub_TakePicturesNow extends BA.ResumableSub {
public ResumableSub_TakePicturesNow(appear.pnud.preservamos.camex2 parent,int _mytaskindex,int _numberofpictures) {
this.parent = parent;
this._mytaskindex = _mytaskindex;
this._numberofpictures = _numberofpictures;
}
appear.pnud.preservamos.camex2 parent;
int _mytaskindex;
int _numberofpictures;
anywheresoftware.b4j.object.JavaObject _builder = null;
anywheresoftware.b4a.objects.collections.List _result = null;
int _i = 0;
byte[] _data = null;
int step8;
int limit8;
int step11;
int limit11;

@Override
public void resume(BA ba, Object[] result) throws Exception{

    while (true) {
        switch (state) {
            case -1:
{
parent.__c.ReturnFromResumableSub(this,null);return;}
case 0:
//C
this.state = 1;
 //BA.debugLineNum = 335;BA.debugLine="If MyTaskIndex <> TaskIndex Then Return False";
if (true) break;

case 1:
//if
this.state = 6;
if (_mytaskindex!=parent._taskindex) { 
this.state = 3;
;}if (true) break;

case 3:
//C
this.state = 6;
if (true) {
parent.__c.ReturnFromResumableSub(this,(Object)(parent.__c.False));return;};
if (true) break;

case 6:
//C
this.state = 7;
;
 //BA.debugLineNum = 336;BA.debugLine="Camera.AbortCaptures";
parent._camera.AbortCaptures();
 //BA.debugLineNum = 337;BA.debugLine="Dim builder As JavaObject = Camera.CreateCaptureB";
_builder = new anywheresoftware.b4j.object.JavaObject();
_builder = (anywheresoftware.b4j.object.JavaObject) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4j.object.JavaObject(), (java.lang.Object)(parent._camera.CreateCaptureBuilder()));
 //BA.debugLineNum = 338;BA.debugLine="CaptureSettingMap.Put(\"JPEG_ORIENTATION\", GetHint";
parent._capturesettingmap.Put((Object)("JPEG_ORIENTATION"),(Object)(parent._gethintorientation()));
 //BA.debugLineNum = 339;BA.debugLine="SetSettingsFromMap(builder, CaptureSettingMap)";
parent._setsettingsfrommap(_builder,parent._capturesettingmap);
 //BA.debugLineNum = 340;BA.debugLine="Dim result As List";
_result = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 341;BA.debugLine="result.Initialize";
_result.Initialize();
 //BA.debugLineNum = 342;BA.debugLine="For i = 1 To NumberOfPictures";
if (true) break;

case 7:
//for
this.state = 10;
step8 = 1;
limit8 = _numberofpictures;
_i = (int) (1) ;
this.state = 14;
if (true) break;

case 14:
//C
this.state = 10;
if ((step8 > 0 && _i <= limit8) || (step8 < 0 && _i >= limit8)) this.state = 9;
if (true) break;

case 15:
//C
this.state = 14;
_i = ((int)(0 + _i + step8)) ;
if (true) break;

case 9:
//C
this.state = 15;
 //BA.debugLineNum = 343;BA.debugLine="Camera.AddCaptureRequest(builder)";
parent._camera.AddCaptureRequest((Object)(_builder.getObject()));
 if (true) break;
if (true) break;
;
 //BA.debugLineNum = 345;BA.debugLine="For i = 1 To NumberOfPictures";

case 10:
//for
this.state = 13;
step11 = 1;
limit11 = _numberofpictures;
_i = (int) (1) ;
this.state = 16;
if (true) break;

case 16:
//C
this.state = 13;
if ((step11 > 0 && _i <= limit11) || (step11 < 0 && _i >= limit11)) this.state = 12;
if (true) break;

case 17:
//C
this.state = 16;
_i = ((int)(0 + _i + step11)) ;
if (true) break;

case 12:
//C
this.state = 17;
 //BA.debugLineNum = 346;BA.debugLine="Wait For Camera_PictureTaken (Data() As Byte)";
parent.__c.WaitFor("camera_picturetaken", ba, this, null);
this.state = 18;
return;
case 18:
//C
this.state = 17;
_data = (byte[]) result[0];
;
 //BA.debugLineNum = 347;BA.debugLine="result.Add(Data)";
_result.Add((Object)(_data));
 if (true) break;
if (true) break;

case 13:
//C
this.state = -1;
;
 //BA.debugLineNum = 349;BA.debugLine="StartPreview(MyTaskIndex, False)";
parent._startpreview(_mytaskindex,parent.__c.False);
 //BA.debugLineNum = 350;BA.debugLine="Return result";
if (true) {
parent.__c.ReturnFromResumableSub(this,(Object)(_result));return;};
 //BA.debugLineNum = 351;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public anywheresoftware.b4a.keywords.Common.ResumableSubWrapper  _waitforfocuswithtimeout(int _timeoutms) throws Exception{
ResumableSub_WaitForFocusWithTimeout rsub = new ResumableSub_WaitForFocusWithTimeout(this,_timeoutms);
rsub.resume(ba, null);
return (anywheresoftware.b4a.keywords.Common.ResumableSubWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.keywords.Common.ResumableSubWrapper(), rsub);
}
public static class ResumableSub_WaitForFocusWithTimeout extends BA.ResumableSub {
public ResumableSub_WaitForFocusWithTimeout(appear.pnud.preservamos.camex2 parent,int _timeoutms) {
this.parent = parent;
this._timeoutms = _timeoutms;
}
appear.pnud.preservamos.camex2 parent;
int _timeoutms;
long _start = 0L;

@Override
public void resume(BA ba, Object[] result) throws Exception{

    while (true) {
        switch (state) {
            case -1:
{
parent.__c.ReturnFromResumableSub(this,null);return;}
case 0:
//C
this.state = 1;
 //BA.debugLineNum = 300;BA.debugLine="Dim start As Long = DateTime.Now";
_start = parent.__c.DateTime.getNow();
 //BA.debugLineNum = 301;BA.debugLine="Do Until FocusState = \"FOCUSED_LOCKED\" Or FocusSt";
if (true) break;

case 1:
//do until
this.state = 10;
while (!((parent._focusstate).equals("FOCUSED_LOCKED") || (parent._focusstate).equals("NOT_FOCUSED_LOCKED"))) {
this.state = 3;
if (true) break;
}
if (true) break;

case 3:
//C
this.state = 4;
 //BA.debugLineNum = 302;BA.debugLine="Sleep(50)";
parent.__c.Sleep(ba,this,(int) (50));
this.state = 11;
return;
case 11:
//C
this.state = 4;
;
 //BA.debugLineNum = 303;BA.debugLine="If DateTime.Now - start > TimeoutMs Then Return";
if (true) break;

case 4:
//if
this.state = 9;
if (parent.__c.DateTime.getNow()-_start>_timeoutms) { 
this.state = 6;
;}if (true) break;

case 6:
//C
this.state = 9;
if (true) {
parent.__c.ReturnFromResumableSub(this,(Object)(parent.__c.False));return;};
if (true) break;

case 9:
//C
this.state = 1;
;
 if (true) break;

case 10:
//C
this.state = -1;
;
 //BA.debugLineNum = 305;BA.debugLine="Return FocusState = \"FOCUSED_LOCKED\"";
if (true) {
parent.__c.ReturnFromResumableSub(this,(Object)((parent._focusstate).equals("FOCUSED_LOCKED")));return;};
 //BA.debugLineNum = 306;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public Object callSub(String sub, Object sender, Object[] args) throws Exception {
BA.senderHolder.set(sender);
return BA.SubDelegator.SubNotFound;
}
}
