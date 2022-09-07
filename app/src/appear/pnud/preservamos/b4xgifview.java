package appear.pnud.preservamos;


import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.B4AClass;
import anywheresoftware.b4a.BALayout;
import anywheresoftware.b4a.debug.*;

public class b4xgifview extends B4AClass.ImplB4AClass implements BA.SubDelegator{
    private static java.util.HashMap<String, java.lang.reflect.Method> htSubs;
    private void innerInitialize(BA _ba) throws Exception {
        if (ba == null) {
            ba = new BA(_ba, this, htSubs, "appear.pnud.preservamos.b4xgifview");
            if (htSubs == null) {
                ba.loadHtSubs(this.getClass());
                htSubs = ba.htSubs;
            }
            
        }
        if (BA.isShellModeRuntimeCheck(ba)) 
			   this.getClass().getMethod("_class_globals", appear.pnud.preservamos.b4xgifview.class).invoke(this, new Object[] {null});
        else
            ba.raiseEvent2(null, true, "class_globals", false);
    }

 public anywheresoftware.b4a.keywords.Common __c = null;
public String _meventname = "";
public Object _mcallback = null;
public anywheresoftware.b4a.objects.B4XViewWrapper _mbase = null;
public anywheresoftware.b4a.objects.B4XViewWrapper.XUI _xui = null;
public Object _tag = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _iv = null;
public anywheresoftware.b4j.object.JavaObject _gifdrawable = null;
public b4a.example.dateutils _dateutils = null;
public appear.pnud.preservamos.main _main = null;
public appear.pnud.preservamos.form_main _form_main = null;
public appear.pnud.preservamos.starter _starter = null;
public appear.pnud.preservamos.inatcheck _inatcheck = null;
public appear.pnud.preservamos.frmlocalizacion _frmlocalizacion = null;
public appear.pnud.preservamos.reporte_envio _reporte_envio = null;
public appear.pnud.preservamos.alertas _alertas = null;
public appear.pnud.preservamos.register _register = null;
public appear.pnud.preservamos.frmeditprofile _frmeditprofile = null;
public appear.pnud.preservamos.alerta_fotos _alerta_fotos = null;
public appear.pnud.preservamos.form_reporte _form_reporte = null;
public appear.pnud.preservamos.aprender_muestreo _aprender_muestreo = null;
public appear.pnud.preservamos.dbutils _dbutils = null;
public appear.pnud.preservamos.downloadservice _downloadservice = null;
public appear.pnud.preservamos.firebasemessaging _firebasemessaging = null;
public appear.pnud.preservamos.frmabout _frmabout = null;
public appear.pnud.preservamos.frmdatosanteriores _frmdatosanteriores = null;
public appear.pnud.preservamos.frmfelicitaciones _frmfelicitaciones = null;
public appear.pnud.preservamos.frmmapa _frmmapa = null;
public appear.pnud.preservamos.frmperfil _frmperfil = null;
public appear.pnud.preservamos.frmpoliticadatos _frmpoliticadatos = null;
public appear.pnud.preservamos.httputils2service _httputils2service = null;
public appear.pnud.preservamos.imagedownloader _imagedownloader = null;
public appear.pnud.preservamos.reporte_fotos _reporte_fotos = null;
public appear.pnud.preservamos.reporte_habitat_laguna _reporte_habitat_laguna = null;
public appear.pnud.preservamos.reporte_habitat_rio _reporte_habitat_rio = null;
public appear.pnud.preservamos.uploadfiles _uploadfiles = null;
public appear.pnud.preservamos.utilidades _utilidades = null;
public appear.pnud.preservamos.xuiviewsutils _xuiviewsutils = null;
public String  _base_resize(double _width,double _height) throws Exception{
 //BA.debugLineNum = 106;BA.debugLine="Public Sub Base_Resize (Width As Double, Height As";
 //BA.debugLineNum = 108;BA.debugLine="iv.SetLayoutAnimated(0, 0, 0, Width, Height)";
_iv.SetLayoutAnimated((int) (0),(int) (0),(int) (0),(int) (_width),(int) (_height));
 //BA.debugLineNum = 117;BA.debugLine="End Sub";
return "";
}
public String  _class_globals() throws Exception{
 //BA.debugLineNum = 1;BA.debugLine="Sub Class_Globals";
 //BA.debugLineNum = 2;BA.debugLine="Private mEventName As String 'ignore";
_meventname = "";
 //BA.debugLineNum = 3;BA.debugLine="Private mCallBack As Object 'ignore";
_mcallback = new Object();
 //BA.debugLineNum = 4;BA.debugLine="Public mBase As B4XView";
_mbase = new anywheresoftware.b4a.objects.B4XViewWrapper();
 //BA.debugLineNum = 5;BA.debugLine="Private xui As XUI 'ignore";
_xui = new anywheresoftware.b4a.objects.B4XViewWrapper.XUI();
 //BA.debugLineNum = 6;BA.debugLine="Public Tag As Object";
_tag = new Object();
 //BA.debugLineNum = 10;BA.debugLine="Private iv As ImageView";
_iv = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 11;BA.debugLine="Public GifDrawable As JavaObject";
_gifdrawable = new anywheresoftware.b4j.object.JavaObject();
 //BA.debugLineNum = 15;BA.debugLine="End Sub";
return "";
}
public String  _designercreateview(Object _base,anywheresoftware.b4a.objects.LabelWrapper _lbl,anywheresoftware.b4a.objects.collections.Map _props) throws Exception{
 //BA.debugLineNum = 22;BA.debugLine="Public Sub DesignerCreateView (Base As Object, Lbl";
 //BA.debugLineNum = 23;BA.debugLine="mBase = Base";
_mbase = (anywheresoftware.b4a.objects.B4XViewWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.B4XViewWrapper(), (java.lang.Object)(_base));
 //BA.debugLineNum = 24;BA.debugLine="Tag = mBase.Tag";
_tag = _mbase.getTag();
 //BA.debugLineNum = 25;BA.debugLine="mBase.Tag = Me";
_mbase.setTag(this);
 //BA.debugLineNum = 36;BA.debugLine="iv.Initialize(\"\")";
_iv.Initialize(ba,"");
 //BA.debugLineNum = 37;BA.debugLine="mBase.AddView(iv, 0, 0, mBase.Width, mBase.Height";
_mbase.AddView((android.view.View)(_iv.getObject()),(int) (0),(int) (0),_mbase.getWidth(),_mbase.getHeight());
 //BA.debugLineNum = 39;BA.debugLine="End Sub";
return "";
}
public String  _initialize(anywheresoftware.b4a.BA _ba,Object _callback,String _eventname) throws Exception{
innerInitialize(_ba);
 //BA.debugLineNum = 17;BA.debugLine="Public Sub Initialize (Callback As Object, EventNa";
 //BA.debugLineNum = 18;BA.debugLine="mEventName = EventName";
_meventname = _eventname;
 //BA.debugLineNum = 19;BA.debugLine="mCallBack = Callback";
_mcallback = _callback;
 //BA.debugLineNum = 20;BA.debugLine="End Sub";
return "";
}
public String  _resizebasedonimage(anywheresoftware.b4a.objects.B4XViewWrapper _xiv,float _bmpratio) throws Exception{
float _viewratio = 0f;
int _height = 0;
int _width = 0;
 //BA.debugLineNum = 93;BA.debugLine="Private Sub ResizeBasedOnImage(xiv As B4XView, Bmp";
 //BA.debugLineNum = 94;BA.debugLine="Dim viewRatio As Float = mBase.Width / mBase.Heig";
_viewratio = (float) (_mbase.getWidth()/(double)_mbase.getHeight());
 //BA.debugLineNum = 95;BA.debugLine="Dim Height, Width As Int";
_height = 0;
_width = 0;
 //BA.debugLineNum = 96;BA.debugLine="If viewRatio > BmpRatio Then";
if (_viewratio>_bmpratio) { 
 //BA.debugLineNum = 97;BA.debugLine="Height = mBase.Height";
_height = _mbase.getHeight();
 //BA.debugLineNum = 98;BA.debugLine="Width = mBase.Height * BmpRatio";
_width = (int) (_mbase.getHeight()*_bmpratio);
 }else {
 //BA.debugLineNum = 100;BA.debugLine="Width = mBase.Width";
_width = _mbase.getWidth();
 //BA.debugLineNum = 101;BA.debugLine="Height = mBase.Width / BmpRatio";
_height = (int) (_mbase.getWidth()/(double)_bmpratio);
 };
 //BA.debugLineNum = 103;BA.debugLine="xiv.SetLayoutAnimated(0, mBase.Width / 2 - Width";
_xiv.SetLayoutAnimated((int) (0),(int) (_mbase.getWidth()/(double)2-_width/(double)2),(int) (_mbase.getHeight()/(double)2-_height/(double)2),_width,_height);
 //BA.debugLineNum = 104;BA.debugLine="End Sub";
return "";
}
public String  _setbitmap(Object _obj) throws Exception{
anywheresoftware.b4j.object.JavaObject _jo = null;
int _w = 0;
int _h = 0;
 //BA.debugLineNum = 81;BA.debugLine="Private Sub SetBitmap(obj As Object)";
 //BA.debugLineNum = 82;BA.debugLine="Dim GifDrawable As JavaObject";
_gifdrawable = new anywheresoftware.b4j.object.JavaObject();
 //BA.debugLineNum = 83;BA.debugLine="GifDrawable.InitializeNewInstance(\"pl.droidsonroi";
_gifdrawable.InitializeNewInstance("pl.droidsonroids.gif.GifDrawable",new Object[]{_obj});
 //BA.debugLineNum = 84;BA.debugLine="iv.Background = GifDrawable";
_iv.setBackground((android.graphics.drawable.Drawable)(_gifdrawable.getObject()));
 //BA.debugLineNum = 85;BA.debugLine="Dim jo As JavaObject = GifDrawable";
_jo = new anywheresoftware.b4j.object.JavaObject();
_jo = _gifdrawable;
 //BA.debugLineNum = 86;BA.debugLine="Dim w As Int = jo.RunMethod(\"getIntrinsicWidth\",";
_w = (int)(BA.ObjectToNumber(_jo.RunMethod("getIntrinsicWidth",(Object[])(__c.Null))));
 //BA.debugLineNum = 87;BA.debugLine="Dim h As Int = jo.RunMethod(\"getIntrinsicHeight\",";
_h = (int)(BA.ObjectToNumber(_jo.RunMethod("getIntrinsicHeight",(Object[])(__c.Null))));
 //BA.debugLineNum = 88;BA.debugLine="ResizeBasedOnImage(iv, w / h)";
_resizebasedonimage((anywheresoftware.b4a.objects.B4XViewWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.B4XViewWrapper(), (java.lang.Object)(_iv.getObject())),(float) (_w/(double)_h));
 //BA.debugLineNum = 89;BA.debugLine="End Sub";
return "";
}
public String  _setgif(String _dir,String _filename) throws Exception{
 //BA.debugLineNum = 41;BA.debugLine="Public Sub SetGif(Dir As String, FileName As Strin";
 //BA.debugLineNum = 47;BA.debugLine="If Dir = File.DirAssets Then";
if ((_dir).equals(__c.File.getDirAssets())) { 
 //BA.debugLineNum = 48;BA.debugLine="SetGif2(File.ReadBytes(Dir, FileName))";
_setgif2(__c.File.ReadBytes(_dir,_filename));
 }else {
 //BA.debugLineNum = 50;BA.debugLine="SetBitmap(File.Combine(Dir, FileName))";
_setbitmap((Object)(__c.File.Combine(_dir,_filename)));
 };
 //BA.debugLineNum = 53;BA.debugLine="End Sub";
return "";
}
public String  _setgif2(byte[] _data) throws Exception{
 //BA.debugLineNum = 55;BA.debugLine="Public Sub SetGif2 (Data() As Byte)";
 //BA.debugLineNum = 69;BA.debugLine="SetBitmap(Data)";
_setbitmap((Object)(_data));
 //BA.debugLineNum = 71;BA.debugLine="End Sub";
return "";
}
public Object callSub(String sub, Object sender, Object[] args) throws Exception {
BA.senderHolder.set(sender);
return BA.SubDelegator.SubNotFound;
}
}
