package ilpla.appear;


import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.BALayout;
import anywheresoftware.b4a.debug.*;

public class uploadfiles {
private static uploadfiles mostCurrent = new uploadfiles();
public static Object getObject() {
    throw new RuntimeException("Code module does not support this method.");
}
 public anywheresoftware.b4a.keywords.Common __c = null;
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
public ilpla.appear.starter _starter = null;
public ilpla.appear.utilidades _utilidades = null;
public static class _filedata{
public boolean IsInitialized;
public String Dir;
public String FileName;
public String KeyName;
public String ContentType;
public void Initialize() {
IsInitialized = true;
Dir = "";
FileName = "";
KeyName = "";
ContentType = "";
}
@Override
		public String toString() {
			return BA.TypeToString(this, false);
		}}
public static anywheresoftware.b4h.okhttp.OkHttpClientWrapper.OkHttpRequest  _createpostrequest(anywheresoftware.b4a.BA _ba,String _url,anywheresoftware.b4a.objects.collections.Map _namevalues,anywheresoftware.b4a.objects.collections.List _files) throws Exception{
String _boundary = "";
anywheresoftware.b4a.objects.streams.File.OutputStreamWrapper _stream = null;
String _eol = "";
byte[] _b = null;
String _key = "";
String _value = "";
int _i = 0;
ilpla.appear.uploadfiles._filedata _fd = null;
anywheresoftware.b4a.objects.streams.File.InputStreamWrapper _in = null;
anywheresoftware.b4h.okhttp.OkHttpClientWrapper.OkHttpRequest _request = null;
 //BA.debugLineNum = 7;BA.debugLine="Sub CreatePostRequest(URL As String, NameValues As";
 //BA.debugLineNum = 8;BA.debugLine="Dim boundary As String";
_boundary = "";
 //BA.debugLineNum = 9;BA.debugLine="boundary = \"---------------------------1461124740";
_boundary = "---------------------------1461124740692";
 //BA.debugLineNum = 10;BA.debugLine="Dim stream As OutputStream";
_stream = new anywheresoftware.b4a.objects.streams.File.OutputStreamWrapper();
 //BA.debugLineNum = 11;BA.debugLine="stream.InitializeToBytesArray(20)";
_stream.InitializeToBytesArray((int) (20));
 //BA.debugLineNum = 12;BA.debugLine="Dim EOL As String";
_eol = "";
 //BA.debugLineNum = 13;BA.debugLine="EOL = Chr(13) & Chr(10) 'CRLF constant matches An";
_eol = BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (13)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)));
 //BA.debugLineNum = 14;BA.debugLine="Dim b() As Byte";
_b = new byte[(int) (0)];
;
 //BA.debugLineNum = 15;BA.debugLine="If NameValues <> Null And NameValues.IsInitialize";
if (_namevalues!= null && _namevalues.IsInitialized()) { 
 //BA.debugLineNum = 17;BA.debugLine="Dim key, value As String";
_key = "";
_value = "";
 //BA.debugLineNum = 18;BA.debugLine="For i = 0 To NameValues.Size - 1";
{
final int step10 = 1;
final int limit10 = (int) (_namevalues.getSize()-1);
_i = (int) (0) ;
for (;_i <= limit10 ;_i = _i + step10 ) {
 //BA.debugLineNum = 19;BA.debugLine="key = NameValues.GetKeyAt(i)";
_key = BA.ObjectToString(_namevalues.GetKeyAt(_i));
 //BA.debugLineNum = 20;BA.debugLine="value = NameValues.GetValueAt(i)";
_value = BA.ObjectToString(_namevalues.GetValueAt(_i));
 //BA.debugLineNum = 21;BA.debugLine="b = (\"--\" & boundary & EOL & \"Content-Dispositi";
_b = ("--"+_boundary+_eol+"Content-Disposition: form-data; name="+anywheresoftware.b4a.keywords.Common.QUOTE+_key+anywheresoftware.b4a.keywords.Common.QUOTE+_eol+_eol+_value+_eol).getBytes("UTF8");
 //BA.debugLineNum = 23;BA.debugLine="stream.WriteBytes(b, 0, b.Length)";
_stream.WriteBytes(_b,(int) (0),_b.length);
 }
};
 };
 //BA.debugLineNum = 26;BA.debugLine="If Files <> Null And Files.IsInitialized Then";
if (_files!= null && _files.IsInitialized()) { 
 //BA.debugLineNum = 28;BA.debugLine="Dim FD As FileData";
_fd = new ilpla.appear.uploadfiles._filedata();
 //BA.debugLineNum = 29;BA.debugLine="For i = 0 To Files.Size - 1";
{
final int step19 = 1;
final int limit19 = (int) (_files.getSize()-1);
_i = (int) (0) ;
for (;_i <= limit19 ;_i = _i + step19 ) {
 //BA.debugLineNum = 30;BA.debugLine="FD = Files.Get(i)";
_fd = (ilpla.appear.uploadfiles._filedata)(_files.Get(_i));
 //BA.debugLineNum = 31;BA.debugLine="b = (\"--\" & boundary & EOL & \"Content-Dispositi";
_b = ("--"+_boundary+_eol+"Content-Disposition: form-data; name="+anywheresoftware.b4a.keywords.Common.QUOTE+_fd.KeyName /*String*/ +anywheresoftware.b4a.keywords.Common.QUOTE+"; filename="+anywheresoftware.b4a.keywords.Common.QUOTE+_fd.FileName /*String*/ +anywheresoftware.b4a.keywords.Common.QUOTE+_eol+"Content-Type: "+_fd.ContentType /*String*/ +_eol+_eol).getBytes("UTF8");
 //BA.debugLineNum = 34;BA.debugLine="stream.WriteBytes(b, 0, b.Length)";
_stream.WriteBytes(_b,(int) (0),_b.length);
 //BA.debugLineNum = 35;BA.debugLine="Log(\"Intentando enviar archivo: \" & i & \" - \" &";
anywheresoftware.b4a.keywords.Common.LogImpl("235323932","Intentando enviar archivo: "+BA.NumberToString(_i)+" - "+_fd.FileName /*String*/ ,0);
 //BA.debugLineNum = 37;BA.debugLine="Dim In As InputStream";
_in = new anywheresoftware.b4a.objects.streams.File.InputStreamWrapper();
 //BA.debugLineNum = 38;BA.debugLine="In = File.OpenInput(FD.Dir, FD.FileName)";
_in = anywheresoftware.b4a.keywords.Common.File.OpenInput(_fd.Dir /*String*/ ,_fd.FileName /*String*/ );
 //BA.debugLineNum = 39;BA.debugLine="File.Copy2(In, stream) 'read the file and write";
anywheresoftware.b4a.keywords.Common.File.Copy2((java.io.InputStream)(_in.getObject()),(java.io.OutputStream)(_stream.getObject()));
 //BA.debugLineNum = 40;BA.debugLine="b = EOL.GetBytes(\"UTF8\")";
_b = _eol.getBytes("UTF8");
 //BA.debugLineNum = 41;BA.debugLine="stream.WriteBytes(b, 0, b.Length)";
_stream.WriteBytes(_b,(int) (0),_b.length);
 //BA.debugLineNum = 43;BA.debugLine="b = (EOL & \"--\" & boundary & \"--\" & EOL).GetByt";
_b = (_eol+"--"+_boundary+"--"+_eol).getBytes("UTF8");
 //BA.debugLineNum = 44;BA.debugLine="stream.WriteBytes(b, 0, b.Length)";
_stream.WriteBytes(_b,(int) (0),_b.length);
 //BA.debugLineNum = 45;BA.debugLine="b = stream.ToBytesArray";
_b = _stream.ToBytesArray();
 //BA.debugLineNum = 47;BA.debugLine="Dim request As OkHttpRequest";
_request = new anywheresoftware.b4h.okhttp.OkHttpClientWrapper.OkHttpRequest();
 //BA.debugLineNum = 48;BA.debugLine="request.InitializePost2(URL, b)";
_request.InitializePost2(_url,_b);
 //BA.debugLineNum = 49;BA.debugLine="request.SetContentType(\"multipart/form-data; bo";
_request.SetContentType("multipart/form-data; boundary="+_boundary);
 //BA.debugLineNum = 50;BA.debugLine="request.SetContentEncoding(\"UTF8\")";
_request.SetContentEncoding("UTF8");
 //BA.debugLineNum = 51;BA.debugLine="Return request";
if (true) return _request;
 }
};
 };
 //BA.debugLineNum = 55;BA.debugLine="b = (EOL & \"--\" & boundary & \"--\" & EOL).GetBytes";
_b = (_eol+"--"+_boundary+"--"+_eol).getBytes("UTF8");
 //BA.debugLineNum = 56;BA.debugLine="stream.WriteBytes(b, 0, b.Length)";
_stream.WriteBytes(_b,(int) (0),_b.length);
 //BA.debugLineNum = 57;BA.debugLine="b = stream.ToBytesArray";
_b = _stream.ToBytesArray();
 //BA.debugLineNum = 59;BA.debugLine="Dim request As OkHttpRequest";
_request = new anywheresoftware.b4h.okhttp.OkHttpClientWrapper.OkHttpRequest();
 //BA.debugLineNum = 60;BA.debugLine="request.InitializePost2(URL, b)";
_request.InitializePost2(_url,_b);
 //BA.debugLineNum = 61;BA.debugLine="request.SetContentType(\"multipart/form-data; boun";
_request.SetContentType("multipart/form-data; boundary="+_boundary);
 //BA.debugLineNum = 62;BA.debugLine="request.SetContentEncoding(\"UTF8\")";
_request.SetContentEncoding("UTF8");
 //BA.debugLineNum = 63;BA.debugLine="Return request";
if (true) return _request;
 //BA.debugLineNum = 65;BA.debugLine="End Sub";
return null;
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 3;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 4;BA.debugLine="Type FileData (Dir As String, FileName As String,";
;
 //BA.debugLineNum = 5;BA.debugLine="End Sub";
return "";
}
}
