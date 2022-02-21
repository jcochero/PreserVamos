package appear.pnud.preservamos;


import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.BALayout;
import anywheresoftware.b4a.debug.*;

public class utilidades {
private static utilidades mostCurrent = new utilidades();
public static Object getObject() {
    throw new RuntimeException("Code module does not support this method.");
}
 public anywheresoftware.b4a.keywords.Common __c = null;
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
public appear.pnud.preservamos.xuiviewsutils _xuiviewsutils = null;
public static String  _bmsgbox3_click(anywheresoftware.b4a.BA _ba) throws Exception{
anywheresoftware.b4a.objects.ButtonWrapper _b = null;
anywheresoftware.b4a.objects.PanelWrapper _p = null;
 //BA.debugLineNum = 124;BA.debugLine="Sub BMsgBox3_Click";
 //BA.debugLineNum = 125;BA.debugLine="Dim B As Button = Sender";
_b = new anywheresoftware.b4a.objects.ButtonWrapper();
_b = (anywheresoftware.b4a.objects.ButtonWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.ButtonWrapper(), (android.widget.Button)(anywheresoftware.b4a.keywords.Common.Sender(_ba)));
 //BA.debugLineNum = 126;BA.debugLine="Dim P As Panel = B.Parent";
_p = new anywheresoftware.b4a.objects.PanelWrapper();
_p = (anywheresoftware.b4a.objects.PanelWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.PanelWrapper(), (android.view.ViewGroup)(_b.getParent()));
 //BA.debugLineNum = 128;BA.debugLine="P.Tag=B.Tag";
_p.setTag(_b.getTag());
 //BA.debugLineNum = 129;BA.debugLine="End Sub";
return "";
}
public static void  _createhaloeffect(anywheresoftware.b4a.BA _ba,anywheresoftware.b4a.objects.B4XViewWrapper _parent,anywheresoftware.b4a.objects.ButtonWrapper _objeto,int _clr) throws Exception{
ResumableSub_CreateHaloEffect rsub = new ResumableSub_CreateHaloEffect(null,_ba,_parent,_objeto,_clr);
rsub.resume((_ba.processBA == null ? _ba : _ba.processBA), null);
}
public static class ResumableSub_CreateHaloEffect extends BA.ResumableSub {
public ResumableSub_CreateHaloEffect(appear.pnud.preservamos.utilidades parent,anywheresoftware.b4a.BA _ba,anywheresoftware.b4a.objects.B4XViewWrapper _parent,anywheresoftware.b4a.objects.ButtonWrapper _objeto,int _clr) {
this.parent = parent;
this._ba = _ba;
this._parent = _parent;
this._objeto = _objeto;
this._clr = _clr;
}
appear.pnud.preservamos.utilidades parent;
anywheresoftware.b4a.BA _ba;
anywheresoftware.b4a.objects.B4XViewWrapper _parent;
anywheresoftware.b4a.objects.ButtonWrapper _objeto;
int _clr;
anywheresoftware.b4a.objects.B4XCanvas _cvs = null;
anywheresoftware.b4a.objects.B4XViewWrapper.XUI _xui = null;
anywheresoftware.b4a.objects.B4XViewWrapper _p = null;
int _radius = 0;
int _x = 0;
int _y = 0;
anywheresoftware.b4a.objects.B4XViewWrapper.B4XBitmapWrapper _bmp = null;
int _i = 0;
int step12;
int limit12;

@Override
public void resume(BA ba, Object[] result) throws Exception{

    while (true) {
        switch (state) {
            case -1:
return;

case 0:
//C
this.state = 1;
 //BA.debugLineNum = 82;BA.debugLine="Dim cvs As B4XCanvas";
_cvs = new anywheresoftware.b4a.objects.B4XCanvas();
 //BA.debugLineNum = 83;BA.debugLine="Dim xui As XUI";
_xui = new anywheresoftware.b4a.objects.B4XViewWrapper.XUI();
 //BA.debugLineNum = 84;BA.debugLine="Dim p As B4XView = xui.CreatePanel(\"\")";
_p = new anywheresoftware.b4a.objects.B4XViewWrapper();
_p = _xui.CreatePanel((_ba.processBA == null ? _ba : _ba.processBA),"");
 //BA.debugLineNum = 85;BA.debugLine="Dim radius As Int = 150dip";
_radius = anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (150));
 //BA.debugLineNum = 86;BA.debugLine="Dim x,y As Int";
_x = 0;
_y = 0;
 //BA.debugLineNum = 87;BA.debugLine="x = objeto.left + (objeto.Width / 2)";
_x = (int) (_objeto.getLeft()+(_objeto.getWidth()/(double)2));
 //BA.debugLineNum = 88;BA.debugLine="y = objeto.top + (objeto.Height / 2)";
_y = (int) (_objeto.getTop()+(_objeto.getHeight()/(double)2));
 //BA.debugLineNum = 90;BA.debugLine="p.SetLayoutAnimated(0, 0, 0, radius * 2, radius *";
_p.SetLayoutAnimated((int) (0),(int) (0),(int) (0),(int) (_radius*2),(int) (_radius*2));
 //BA.debugLineNum = 91;BA.debugLine="cvs.Initialize(p)";
_cvs.Initialize(_p);
 //BA.debugLineNum = 92;BA.debugLine="cvs.DrawCircle(cvs.TargetRect.CenterX, cvs.Target";
_cvs.DrawCircle(_cvs.getTargetRect().getCenterX(),_cvs.getTargetRect().getCenterY(),(float) (_cvs.getTargetRect().getWidth()/(double)2),_clr,anywheresoftware.b4a.keywords.Common.True,(float) (0));
 //BA.debugLineNum = 93;BA.debugLine="Dim bmp As B4XBitmap = cvs.CreateBitmap";
_bmp = new anywheresoftware.b4a.objects.B4XViewWrapper.B4XBitmapWrapper();
_bmp = _cvs.CreateBitmap();
 //BA.debugLineNum = 94;BA.debugLine="For i = 1 To 2";
if (true) break;

case 1:
//for
this.state = 4;
step12 = 1;
limit12 = (int) (2);
_i = (int) (1) ;
this.state = 5;
if (true) break;

case 5:
//C
this.state = 4;
if ((step12 > 0 && _i <= limit12) || (step12 < 0 && _i >= limit12)) this.state = 3;
if (true) break;

case 6:
//C
this.state = 5;
_i = ((int)(0 + _i + step12)) ;
if (true) break;

case 3:
//C
this.state = 6;
 //BA.debugLineNum = 95;BA.debugLine="CreateHaloEffectHelper(Parent,bmp, x, y, clr, ra";
_createhaloeffecthelper(_ba,_parent,_bmp,_x,_y,_clr,_radius);
 //BA.debugLineNum = 96;BA.debugLine="Sleep(200)";
anywheresoftware.b4a.keywords.Common.Sleep(_ba,this,(int) (200));
this.state = 7;
return;
case 7:
//C
this.state = 6;
;
 if (true) break;
if (true) break;

case 4:
//C
this.state = -1;
;
 //BA.debugLineNum = 98;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public static void  _createhaloeffecthelper(anywheresoftware.b4a.BA _ba,anywheresoftware.b4a.objects.B4XViewWrapper _parent,anywheresoftware.b4a.objects.B4XViewWrapper.B4XBitmapWrapper _bmp,int _x,int _y,int _clr,int _radius) throws Exception{
ResumableSub_CreateHaloEffectHelper rsub = new ResumableSub_CreateHaloEffectHelper(null,_ba,_parent,_bmp,_x,_y,_clr,_radius);
rsub.resume((_ba.processBA == null ? _ba : _ba.processBA), null);
}
public static class ResumableSub_CreateHaloEffectHelper extends BA.ResumableSub {
public ResumableSub_CreateHaloEffectHelper(appear.pnud.preservamos.utilidades parent,anywheresoftware.b4a.BA _ba,anywheresoftware.b4a.objects.B4XViewWrapper _parent,anywheresoftware.b4a.objects.B4XViewWrapper.B4XBitmapWrapper _bmp,int _x,int _y,int _clr,int _radius) {
this.parent = parent;
this._ba = _ba;
this._parent = _parent;
this._bmp = _bmp;
this._x = _x;
this._y = _y;
this._clr = _clr;
this._radius = _radius;
}
appear.pnud.preservamos.utilidades parent;
anywheresoftware.b4a.BA _ba;
anywheresoftware.b4a.objects.B4XViewWrapper _parent;
anywheresoftware.b4a.objects.B4XViewWrapper.B4XBitmapWrapper _bmp;
int _x;
int _y;
int _clr;
int _radius;
anywheresoftware.b4a.objects.ImageViewWrapper _iv = null;
anywheresoftware.b4a.objects.B4XViewWrapper _p = null;
int _duration = 0;

@Override
public void resume(BA ba, Object[] result) throws Exception{

    while (true) {
        switch (state) {
            case -1:
return;

case 0:
//C
this.state = -1;
 //BA.debugLineNum = 101;BA.debugLine="Dim iv As ImageView";
_iv = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 102;BA.debugLine="iv.Initialize(\"\")";
_iv.Initialize(_ba,"");
 //BA.debugLineNum = 103;BA.debugLine="Dim p As B4XView = iv";
_p = new anywheresoftware.b4a.objects.B4XViewWrapper();
_p = (anywheresoftware.b4a.objects.B4XViewWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.B4XViewWrapper(), (java.lang.Object)(_iv.getObject()));
 //BA.debugLineNum = 104;BA.debugLine="p.SetBitmap(bmp)";
_p.SetBitmap((android.graphics.Bitmap)(_bmp.getObject()));
 //BA.debugLineNum = 105;BA.debugLine="Parent.AddView(p, x, y, 0, 0)";
_parent.AddView((android.view.View)(_p.getObject()),_x,_y,(int) (0),(int) (0));
 //BA.debugLineNum = 106;BA.debugLine="Dim duration As Int = 1000";
_duration = (int) (1000);
 //BA.debugLineNum = 107;BA.debugLine="p.SetLayoutAnimated(duration, x - radius, y - rad";
_p.SetLayoutAnimated(_duration,(int) (_x-_radius),(int) (_y-_radius),(int) (2*_radius),(int) (2*_radius));
 //BA.debugLineNum = 108;BA.debugLine="p.SetVisibleAnimated(duration, False)";
_p.SetVisibleAnimated(_duration,anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 109;BA.debugLine="Sleep(duration)";
anywheresoftware.b4a.keywords.Common.Sleep(_ba,this,_duration);
this.state = 1;
return;
case 1:
//C
this.state = -1;
;
 //BA.debugLineNum = 110;BA.debugLine="p.RemoveViewFromParent";
_p.RemoveViewFromParent();
 //BA.debugLineNum = 111;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public static String  _getdeviceid(anywheresoftware.b4a.BA _ba) throws Exception{
String _deviceid = "";
anywheresoftware.b4a.objects.collections.Map _wherefields = null;
 //BA.debugLineNum = 17;BA.debugLine="Sub GetDeviceId As String";
 //BA.debugLineNum = 19;BA.debugLine="Dim deviceID As String";
_deviceid = "";
 //BA.debugLineNum = 20;BA.debugLine="deviceID = FirebaseMessaging.fm.Token";
_deviceid = mostCurrent._firebasemessaging._fm /*anywheresoftware.b4a.objects.FirebaseNotificationsService.FirebaseMessageWrapper*/ .getToken();
 //BA.debugLineNum = 23;BA.debugLine="Dim WhereFields As Map";
_wherefields = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 24;BA.debugLine="WhereFields.Initialize";
_wherefields.Initialize();
 //BA.debugLineNum = 25;BA.debugLine="WhereFields.Put(\"configid\", \"1\")";
_wherefields.Put((Object)("configid"),(Object)("1"));
 //BA.debugLineNum = 26;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"appconfig\",";
mostCurrent._dbutils._updaterecord /*String*/ (_ba,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"appconfig","deviceID",(Object)(_deviceid),_wherefields);
 //BA.debugLineNum = 27;BA.debugLine="Return deviceID";
if (true) return _deviceid;
 //BA.debugLineNum = 28;BA.debugLine="End Sub";
return "";
}
public static String  _panelmsgbox3_touch(anywheresoftware.b4a.BA _ba,int _action,float _x,float _y) throws Exception{
anywheresoftware.b4a.objects.PanelWrapper _p = null;
 //BA.debugLineNum = 118;BA.debugLine="Sub PanelMsgBox3_Touch (Action As Int, X As Float,";
 //BA.debugLineNum = 119;BA.debugLine="Dim P As Panel = Sender";
_p = new anywheresoftware.b4a.objects.PanelWrapper();
_p = (anywheresoftware.b4a.objects.PanelWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.PanelWrapper(), (android.view.ViewGroup)(anywheresoftware.b4a.keywords.Common.Sender(_ba)));
 //BA.debugLineNum = 121;BA.debugLine="P.RemoveView";
_p.RemoveView();
 //BA.debugLineNum = 122;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 3;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 7;BA.debugLine="End Sub";
return "";
}
public static String  _setprogressdrawable(anywheresoftware.b4a.BA _ba,anywheresoftware.b4a.objects.ProgressBarWrapper _p,Object _drawable,Object _backgrounddrawable) throws Exception{
anywheresoftware.b4a.agraham.reflection.Reflection _r = null;
Object _clipdrawable = null;
 //BA.debugLineNum = 42;BA.debugLine="Sub SetProgressDrawable(p As ProgressBar, drawable";
 //BA.debugLineNum = 43;BA.debugLine="Dim r As Reflector";
_r = new anywheresoftware.b4a.agraham.reflection.Reflection();
 //BA.debugLineNum = 45;BA.debugLine="Dim clipDrawable As Object";
_clipdrawable = new Object();
 //BA.debugLineNum = 46;BA.debugLine="clipDrawable = r.CreateObject2(\"android.graphics.";
_clipdrawable = _r.CreateObject2("android.graphics.drawable.ClipDrawable",new Object[]{_drawable,(Object)(anywheresoftware.b4a.keywords.Common.Gravity.LEFT),(Object)(1)},new String[]{"android.graphics.drawable.Drawable","java.lang.int","java.lang.int"});
 //BA.debugLineNum = 49;BA.debugLine="r.Target = p";
_r.Target = (Object)(_p.getObject());
 //BA.debugLineNum = 50;BA.debugLine="r.Target = r.RunMethod(\"getProgressDrawable\") 'Ge";
_r.Target = _r.RunMethod("getProgressDrawable");
 //BA.debugLineNum = 51;BA.debugLine="r.RunMethod4(\"setDrawableByLayerId\", _       Arra";
_r.RunMethod4("setDrawableByLayerId",new Object[]{(Object)(16908288),_backgrounddrawable},new String[]{"java.lang.int","android.graphics.drawable.Drawable"});
 //BA.debugLineNum = 54;BA.debugLine="r.RunMethod4(\"setDrawableByLayerId\", _       Arra";
_r.RunMethod4("setDrawableByLayerId",new Object[]{_r.GetStaticField("android.R$id","progress"),_clipdrawable},new String[]{"java.lang.int","android.graphics.drawable.Drawable"});
 //BA.debugLineNum = 58;BA.debugLine="End Sub";
return "";
}
}
