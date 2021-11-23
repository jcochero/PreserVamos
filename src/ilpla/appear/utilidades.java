package ilpla.appear;


import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.BALayout;
import anywheresoftware.b4a.debug.*;

public class utilidades {
private static utilidades mostCurrent = new utilidades();
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
public ilpla.appear.uploadfiles _uploadfiles = null;
public static String  _bmsgbox3_click(anywheresoftware.b4a.BA _ba) throws Exception{
anywheresoftware.b4a.objects.ButtonWrapper _b = null;
anywheresoftware.b4a.objects.PanelWrapper _p = null;
 //BA.debugLineNum = 234;BA.debugLine="Sub BMsgBox3_Click";
 //BA.debugLineNum = 235;BA.debugLine="Dim B As Button = Sender";
_b = new anywheresoftware.b4a.objects.ButtonWrapper();
_b = (anywheresoftware.b4a.objects.ButtonWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.ButtonWrapper(), (android.widget.Button)(anywheresoftware.b4a.keywords.Common.Sender(_ba)));
 //BA.debugLineNum = 236;BA.debugLine="Dim P As Panel = B.Parent";
_p = new anywheresoftware.b4a.objects.PanelWrapper();
_p = (anywheresoftware.b4a.objects.PanelWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.PanelWrapper(), (android.view.ViewGroup)(_b.getParent()));
 //BA.debugLineNum = 238;BA.debugLine="P.Tag=B.Tag";
_p.setTag(_b.getTag());
 //BA.debugLineNum = 239;BA.debugLine="End Sub";
return "";
}
public static String  _colorearcirculos(anywheresoftware.b4a.BA _ba,anywheresoftware.b4a.objects.LabelWrapper _label,String _valor) throws Exception{
anywheresoftware.b4a.objects.drawable.ColorDrawable _labelborder = null;
anywheresoftware.b4a.objects.drawable.StateListDrawable _sldwhite = null;
anywheresoftware.b4a.objects.drawable.ColorDrawable _labelborderclicked = null;
int _valorind = 0;
 //BA.debugLineNum = 134;BA.debugLine="Sub ColorearCirculos (label As Label, valor As Str";
 //BA.debugLineNum = 135;BA.debugLine="Dim LabelBorder As ColorDrawable";
_labelborder = new anywheresoftware.b4a.objects.drawable.ColorDrawable();
 //BA.debugLineNum = 136;BA.debugLine="Dim sldWhite As StateListDrawable";
_sldwhite = new anywheresoftware.b4a.objects.drawable.StateListDrawable();
 //BA.debugLineNum = 137;BA.debugLine="Dim LabelBorderClicked As ColorDrawable";
_labelborderclicked = new anywheresoftware.b4a.objects.drawable.ColorDrawable();
 //BA.debugLineNum = 139;BA.debugLine="LabelBorderClicked.Initialize2(Colors.ARGB(255,25";
_labelborderclicked.Initialize2(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (255),(int) (255),(int) (255)),(int) (255),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (3)),anywheresoftware.b4a.keywords.Common.Colors.Red);
 //BA.debugLineNum = 140;BA.debugLine="sldWhite.Initialize";
_sldwhite.Initialize();
 //BA.debugLineNum = 142;BA.debugLine="If valor = \"NS\" Then";
if ((_valor).equals("NS")) { 
 //BA.debugLineNum = 143;BA.debugLine="LabelBorder.initialize2(Colors.ARGB(0,255,255,25";
_labelborder.Initialize2(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (255),(int) (255),(int) (255)),(int) (255),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (3)),anywheresoftware.b4a.keywords.Common.Colors.DarkGray);
 //BA.debugLineNum = 145;BA.debugLine="label.background = LabelBorder";
_label.setBackground((android.graphics.drawable.Drawable)(_labelborder.getObject()));
 //BA.debugLineNum = 146;BA.debugLine="label.TextColor = Colors.White";
_label.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.White);
 }else if((_valor).equals("")) { 
 //BA.debugLineNum = 148;BA.debugLine="LabelBorder.initialize2(Colors.ARGB(0,255,255,25";
_labelborder.Initialize2(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (255),(int) (255),(int) (255)),(int) (255),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (3)),anywheresoftware.b4a.keywords.Common.Colors.Red);
 //BA.debugLineNum = 149;BA.debugLine="sldWhite.AddState(sldWhite.State_Pressed, LabelB";
_sldwhite.AddState(_sldwhite.State_Pressed,(android.graphics.drawable.Drawable)(_labelborderclicked.getObject()));
 //BA.debugLineNum = 150;BA.debugLine="sldWhite.AddState (sldWhite.State_Enabled,LabelB";
_sldwhite.AddState(_sldwhite.State_Enabled,(android.graphics.drawable.Drawable)(_labelborder.getObject()));
 //BA.debugLineNum = 151;BA.debugLine="label.background = sldWhite";
_label.setBackground((android.graphics.drawable.Drawable)(_sldwhite.getObject()));
 //BA.debugLineNum = 152;BA.debugLine="label.TextColor = Colors.Gray";
_label.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Gray);
 }else {
 //BA.debugLineNum = 154;BA.debugLine="LabelBorder.initialize2(Colors.ARGB(0,125,15,19)";
_labelborder.Initialize2(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (125),(int) (15),(int) (19)),(int) (255),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (1)),anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (125),(int) (15),(int) (19)));
 //BA.debugLineNum = 155;BA.debugLine="label.background = LabelBorder";
_label.setBackground((android.graphics.drawable.Drawable)(_labelborder.getObject()));
 //BA.debugLineNum = 157;BA.debugLine="Dim valorind As Int";
_valorind = 0;
 //BA.debugLineNum = 158;BA.debugLine="valorind = valor";
_valorind = (int)(Double.parseDouble(_valor));
 //BA.debugLineNum = 159;BA.debugLine="label.TextColor = Colors.White";
_label.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 160;BA.debugLine="If valorind < 2 Then";
if (_valorind<2) { 
 //BA.debugLineNum = 162;BA.debugLine="LabelBorder.initialize2(Colors.ARGB(0,125,15,19";
_labelborder.Initialize2(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (125),(int) (15),(int) (19)),(int) (255),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (3)),anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (125),(int) (15),(int) (19)));
 //BA.debugLineNum = 163;BA.debugLine="label.background = LabelBorder";
_label.setBackground((android.graphics.drawable.Drawable)(_labelborder.getObject()));
 }else if(_valorind<4 && _valorind>=2) { 
 //BA.debugLineNum = 166;BA.debugLine="LabelBorder.initialize2(Colors.ARGB(0,125,15,19";
_labelborder.Initialize2(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (125),(int) (15),(int) (19)),(int) (255),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (3)),anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (125),(int) (15),(int) (19)));
 //BA.debugLineNum = 167;BA.debugLine="label.background = LabelBorder";
_label.setBackground((android.graphics.drawable.Drawable)(_labelborder.getObject()));
 }else if(_valorind<6 && _valorind>=4) { 
 //BA.debugLineNum = 170;BA.debugLine="LabelBorder.initialize2(Colors.ARGB(0,179,191,0";
_labelborder.Initialize2(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (179),(int) (191),(int) (0)),(int) (255),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (3)),anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (179),(int) (191),(int) (0)));
 //BA.debugLineNum = 171;BA.debugLine="label.background = LabelBorder";
_label.setBackground((android.graphics.drawable.Drawable)(_labelborder.getObject()));
 }else if(_valorind<8 && _valorind>=6) { 
 //BA.debugLineNum = 174;BA.debugLine="LabelBorder.initialize2(Colors.ARGB(0,66,191,41";
_labelborder.Initialize2(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (66),(int) (191),(int) (41)),(int) (255),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (3)),anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (66),(int) (191),(int) (41)));
 //BA.debugLineNum = 175;BA.debugLine="label.background = LabelBorder";
_label.setBackground((android.graphics.drawable.Drawable)(_labelborder.getObject()));
 }else if(_valorind>8) { 
 //BA.debugLineNum = 178;BA.debugLine="LabelBorder.initialize2(Colors.ARGB(0,36,73,191";
_labelborder.Initialize2(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (36),(int) (73),(int) (191)),(int) (255),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (3)),anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (36),(int) (73),(int) (191)));
 //BA.debugLineNum = 179;BA.debugLine="label.background = LabelBorder";
_label.setBackground((android.graphics.drawable.Drawable)(_labelborder.getObject()));
 };
 };
 //BA.debugLineNum = 182;BA.debugLine="End Sub";
return "";
}
public static void  _createhaloeffect(anywheresoftware.b4a.BA _ba,anywheresoftware.b4a.objects.B4XViewWrapper _parent,anywheresoftware.b4a.objects.ButtonWrapper _objeto,int _clr) throws Exception{
ResumableSub_CreateHaloEffect rsub = new ResumableSub_CreateHaloEffect(null,_ba,_parent,_objeto,_clr);
rsub.resume((_ba.processBA == null ? _ba : _ba.processBA), null);
}
public static class ResumableSub_CreateHaloEffect extends BA.ResumableSub {
public ResumableSub_CreateHaloEffect(ilpla.appear.utilidades parent,anywheresoftware.b4a.BA _ba,anywheresoftware.b4a.objects.B4XViewWrapper _parent,anywheresoftware.b4a.objects.ButtonWrapper _objeto,int _clr) {
this.parent = parent;
this._ba = _ba;
this._parent = _parent;
this._objeto = _objeto;
this._clr = _clr;
}
ilpla.appear.utilidades parent;
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
 //BA.debugLineNum = 192;BA.debugLine="Dim cvs As B4XCanvas";
_cvs = new anywheresoftware.b4a.objects.B4XCanvas();
 //BA.debugLineNum = 193;BA.debugLine="Dim xui As XUI";
_xui = new anywheresoftware.b4a.objects.B4XViewWrapper.XUI();
 //BA.debugLineNum = 194;BA.debugLine="Dim p As B4XView = xui.CreatePanel(\"\")";
_p = new anywheresoftware.b4a.objects.B4XViewWrapper();
_p = _xui.CreatePanel((_ba.processBA == null ? _ba : _ba.processBA),"");
 //BA.debugLineNum = 195;BA.debugLine="Dim radius As Int = 150dip";
_radius = anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (150));
 //BA.debugLineNum = 196;BA.debugLine="Dim x,y As Int";
_x = 0;
_y = 0;
 //BA.debugLineNum = 197;BA.debugLine="x = objeto.left + (objeto.Width / 2)";
_x = (int) (_objeto.getLeft()+(_objeto.getWidth()/(double)2));
 //BA.debugLineNum = 198;BA.debugLine="y = objeto.top + (objeto.Height / 2)";
_y = (int) (_objeto.getTop()+(_objeto.getHeight()/(double)2));
 //BA.debugLineNum = 200;BA.debugLine="p.SetLayoutAnimated(0, 0, 0, radius * 2, radius *";
_p.SetLayoutAnimated((int) (0),(int) (0),(int) (0),(int) (_radius*2),(int) (_radius*2));
 //BA.debugLineNum = 201;BA.debugLine="cvs.Initialize(p)";
_cvs.Initialize(_p);
 //BA.debugLineNum = 202;BA.debugLine="cvs.DrawCircle(cvs.TargetRect.CenterX, cvs.Target";
_cvs.DrawCircle(_cvs.getTargetRect().getCenterX(),_cvs.getTargetRect().getCenterY(),(float) (_cvs.getTargetRect().getWidth()/(double)2),_clr,anywheresoftware.b4a.keywords.Common.True,(float) (0));
 //BA.debugLineNum = 203;BA.debugLine="Dim bmp As B4XBitmap = cvs.CreateBitmap";
_bmp = new anywheresoftware.b4a.objects.B4XViewWrapper.B4XBitmapWrapper();
_bmp = _cvs.CreateBitmap();
 //BA.debugLineNum = 204;BA.debugLine="For i = 1 To 2";
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
 //BA.debugLineNum = 205;BA.debugLine="CreateHaloEffectHelper(Parent,bmp, x, y, clr, ra";
_createhaloeffecthelper(_ba,_parent,_bmp,_x,_y,_clr,_radius);
 //BA.debugLineNum = 206;BA.debugLine="Sleep(200)";
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
 //BA.debugLineNum = 208;BA.debugLine="End Sub";
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
public ResumableSub_CreateHaloEffectHelper(ilpla.appear.utilidades parent,anywheresoftware.b4a.BA _ba,anywheresoftware.b4a.objects.B4XViewWrapper _parent,anywheresoftware.b4a.objects.B4XViewWrapper.B4XBitmapWrapper _bmp,int _x,int _y,int _clr,int _radius) {
this.parent = parent;
this._ba = _ba;
this._parent = _parent;
this._bmp = _bmp;
this._x = _x;
this._y = _y;
this._clr = _clr;
this._radius = _radius;
}
ilpla.appear.utilidades parent;
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
 //BA.debugLineNum = 211;BA.debugLine="Dim iv As ImageView";
_iv = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 212;BA.debugLine="iv.Initialize(\"\")";
_iv.Initialize(_ba,"");
 //BA.debugLineNum = 213;BA.debugLine="Dim p As B4XView = iv";
_p = new anywheresoftware.b4a.objects.B4XViewWrapper();
_p = (anywheresoftware.b4a.objects.B4XViewWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.B4XViewWrapper(), (java.lang.Object)(_iv.getObject()));
 //BA.debugLineNum = 214;BA.debugLine="p.SetBitmap(bmp)";
_p.SetBitmap((android.graphics.Bitmap)(_bmp.getObject()));
 //BA.debugLineNum = 215;BA.debugLine="Parent.AddView(p, x, y, 0, 0)";
_parent.AddView((android.view.View)(_p.getObject()),_x,_y,(int) (0),(int) (0));
 //BA.debugLineNum = 216;BA.debugLine="Dim duration As Int = 1000";
_duration = (int) (1000);
 //BA.debugLineNum = 217;BA.debugLine="p.SetLayoutAnimated(duration, x - radius, y - rad";
_p.SetLayoutAnimated(_duration,(int) (_x-_radius),(int) (_y-_radius),(int) (2*_radius),(int) (2*_radius));
 //BA.debugLineNum = 218;BA.debugLine="p.SetVisibleAnimated(duration, False)";
_p.SetVisibleAnimated(_duration,anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 219;BA.debugLine="Sleep(duration)";
anywheresoftware.b4a.keywords.Common.Sleep(_ba,this,_duration);
this.state = 1;
return;
case 1:
//C
this.state = -1;
;
 //BA.debugLineNum = 220;BA.debugLine="p.RemoveViewFromParent";
_p.RemoveViewFromParent();
 //BA.debugLineNum = 221;BA.debugLine="End Sub";
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
public static String  _mensaje(anywheresoftware.b4a.BA _ba,String _titulo,String _imagen,String _text,String _textsub,String _botontextoyes,String _botontextocan,String _botontextono,boolean _textolargo) throws Exception{
flm.b4a.betterdialogs.BetterDialogs _dial = null;
anywheresoftware.b4a.objects.ImageViewWrapper _msgbottomimg = null;
anywheresoftware.b4a.objects.PanelWrapper _panelcontent = null;
anywheresoftware.b4a.objects.LabelWrapper _titulolbl = null;
anywheresoftware.b4a.objects.ImageViewWrapper _msgtopimg = null;
anywheresoftware.b4a.objects.LabelWrapper _contenido = null;
anywheresoftware.b4a.objects.LabelWrapper _textosub = null;
String _msg = "";
anywheresoftware.b4a.objects.ImageViewWrapper _imgbitmap = null;
 //BA.debugLineNum = 37;BA.debugLine="Sub Mensaje (titulo As String, imagen As String, t";
 //BA.debugLineNum = 38;BA.debugLine="Dim dial As BetterDialogs";
_dial = new flm.b4a.betterdialogs.BetterDialogs();
 //BA.debugLineNum = 39;BA.debugLine="Dim MsgBottomImg As ImageView";
_msgbottomimg = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 40;BA.debugLine="Dim panelcontent As Panel";
_panelcontent = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 41;BA.debugLine="Dim titulolbl As Label";
_titulolbl = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 42;BA.debugLine="Dim MsgTopImg As ImageView";
_msgtopimg = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 43;BA.debugLine="Dim contenido As Label";
_contenido = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 44;BA.debugLine="Dim textoSub As Label";
_textosub = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 45;BA.debugLine="Dim msg As String";
_msg = "";
 //BA.debugLineNum = 47;BA.debugLine="MsgBottomImg.Initialize(\"\")";
_msgbottomimg.Initialize(_ba,"");
 //BA.debugLineNum = 48;BA.debugLine="MsgTopImg.Initialize(\"\")";
_msgtopimg.Initialize(_ba,"");
 //BA.debugLineNum = 49;BA.debugLine="panelcontent.Initialize(\"\")";
_panelcontent.Initialize(_ba,"");
 //BA.debugLineNum = 50;BA.debugLine="titulolbl.Initialize(\"\")";
_titulolbl.Initialize(_ba,"");
 //BA.debugLineNum = 51;BA.debugLine="contenido.Initialize(\"\")";
_contenido.Initialize(_ba,"");
 //BA.debugLineNum = 52;BA.debugLine="textoSub.Initialize(\"\")";
_textosub.Initialize(_ba,"");
 //BA.debugLineNum = 53;BA.debugLine="MsgTopImg.Gravity = Gravity.FILL";
_msgtopimg.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.FILL);
 //BA.debugLineNum = 54;BA.debugLine="MsgBottomImg.Gravity = Gravity.FILL";
_msgbottomimg.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.FILL);
 //BA.debugLineNum = 56;BA.debugLine="titulolbl.Text = titulo";
_titulolbl.setText(BA.ObjectToCharSequence(_titulo));
 //BA.debugLineNum = 57;BA.debugLine="titulolbl.TextColor = Colors.Black";
_titulolbl.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 58;BA.debugLine="titulolbl.Gravity = Gravity.CENTER_HORIZONTAL";
_titulolbl.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.CENTER_HORIZONTAL);
 //BA.debugLineNum = 59;BA.debugLine="titulolbl.TextSize = 22";
_titulolbl.setTextSize((float) (22));
 //BA.debugLineNum = 60;BA.debugLine="contenido.Text = text";
_contenido.setText(BA.ObjectToCharSequence(_text));
 //BA.debugLineNum = 61;BA.debugLine="contenido.TextColor = Colors.Black";
_contenido.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 62;BA.debugLine="contenido.TextSize = 18";
_contenido.setTextSize((float) (18));
 //BA.debugLineNum = 63;BA.debugLine="contenido.Gravity = Gravity.CENTER_HORIZONTAL";
_contenido.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.CENTER_HORIZONTAL);
 //BA.debugLineNum = 64;BA.debugLine="contenido.Color = Colors.white";
_contenido.setColor(anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 65;BA.debugLine="textoSub.Text = textsub";
_textosub.setText(BA.ObjectToCharSequence(_textsub));
 //BA.debugLineNum = 66;BA.debugLine="textoSub.TextColor = Colors.DarkGray";
_textosub.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.DarkGray);
 //BA.debugLineNum = 67;BA.debugLine="textoSub.TextSize = 16";
_textosub.setTextSize((float) (16));
 //BA.debugLineNum = 68;BA.debugLine="textoSub.Gravity = Gravity.CENTER_HORIZONTAL";
_textosub.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.CENTER_HORIZONTAL);
 //BA.debugLineNum = 69;BA.debugLine="textoSub.Color = Colors.white";
_textosub.setColor(anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 70;BA.debugLine="panelcontent.Color = Colors.white";
_panelcontent.setColor(anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 73;BA.debugLine="Dim imgbitmap As ImageView";
_imgbitmap = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 74;BA.debugLine="imgbitmap.Initialize(\"\")";
_imgbitmap.Initialize(_ba,"");
 //BA.debugLineNum = 75;BA.debugLine="If imagen <> \"Null\" Then";
if ((_imagen).equals("Null") == false) { 
 //BA.debugLineNum = 76;BA.debugLine="imgbitmap.Bitmap =  LoadBitmapSample(File.DirAss";
_imgbitmap.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),_imagen,anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),_ba),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),_ba)).getObject()));
 }else {
 //BA.debugLineNum = 78;BA.debugLine="imgbitmap.Bitmap =  LoadBitmapSample(File.DirAss";
_imgbitmap.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"MsgIcon.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),_ba),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),_ba)).getObject()));
 };
 //BA.debugLineNum = 81;BA.debugLine="panelcontent.AddView(MsgTopImg, 0, 0,90%x, 60dip)";
_panelcontent.AddView((android.view.View)(_msgtopimg.getObject()),(int) (0),(int) (0),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (90),_ba),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (60)));
 //BA.debugLineNum = 82;BA.debugLine="panelcontent.AddView(imgbitmap, 5%x, 3%y, 80%x, 3";
_panelcontent.AddView((android.view.View)(_imgbitmap.getObject()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (5),_ba),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (3),_ba),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (80),_ba),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (35),_ba));
 //BA.debugLineNum = 83;BA.debugLine="panelcontent.AddView(titulolbl, 0, 35%y, 90%x, 60";
_panelcontent.AddView((android.view.View)(_titulolbl.getObject()),(int) (0),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (35),_ba),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (90),_ba),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (60)));
 //BA.debugLineNum = 84;BA.debugLine="panelcontent.AddView(contenido, 5%x, titulolbl.To";
_panelcontent.AddView((android.view.View)(_contenido.getObject()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (5),_ba),(int) (_titulolbl.getTop()+anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (5),_ba)),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (80),_ba),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (25),_ba));
 //BA.debugLineNum = 85;BA.debugLine="If textolargo = True Then";
if (_textolargo==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 86;BA.debugLine="panelcontent.AddView(textoSub, 5%x, contenido.To";
_panelcontent.AddView((android.view.View)(_textosub.getObject()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (5),_ba),(int) (_contenido.getTop()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (50))),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (80),_ba),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (25),_ba));
 }else {
 //BA.debugLineNum = 88;BA.debugLine="panelcontent.AddView(textoSub, 5%x, contenido.To";
_panelcontent.AddView((android.view.View)(_textosub.getObject()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (5),_ba),(int) (_contenido.getTop()+_contenido.getHeight()+anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (3),_ba)),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (80),_ba),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (15),_ba));
 };
 //BA.debugLineNum = 91;BA.debugLine="panelcontent.AddView(MsgBottomImg, 0, 280dip, 90%";
_panelcontent.AddView((android.view.View)(_msgbottomimg.getObject()),(int) (0),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (280)),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (90),_ba),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (60)));
 //BA.debugLineNum = 93;BA.debugLine="msg = dial.CustomDialog(\"\", 90%x, 60dip, panelcon";
_msg = BA.NumberToString(_dial.CustomDialog((Object)(""),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (90),_ba),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (60)),(anywheresoftware.b4a.objects.ConcreteViewWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.ConcreteViewWrapper(), (android.view.View)(_panelcontent.getObject())),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (90),_ba),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (80),_ba),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (5),_ba),anywheresoftware.b4a.keywords.Common.Null,(Object)(_botontextoyes),(Object)(_botontextocan),(Object)(_botontextono),anywheresoftware.b4a.keywords.Common.False,"",_ba));
 //BA.debugLineNum = 94;BA.debugLine="Return msg";
if (true) return _msg;
 //BA.debugLineNum = 95;BA.debugLine="End Sub";
return "";
}
public static String  _panelmsgbox3_touch(anywheresoftware.b4a.BA _ba,int _action,float _x,float _y) throws Exception{
anywheresoftware.b4a.objects.PanelWrapper _p = null;
 //BA.debugLineNum = 228;BA.debugLine="Sub PanelMsgBox3_Touch (Action As Int, X As Float,";
 //BA.debugLineNum = 229;BA.debugLine="Dim P As Panel = Sender";
_p = new anywheresoftware.b4a.objects.PanelWrapper();
_p = (anywheresoftware.b4a.objects.PanelWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.PanelWrapper(), (android.view.ViewGroup)(anywheresoftware.b4a.keywords.Common.Sender(_ba)));
 //BA.debugLineNum = 231;BA.debugLine="P.RemoveView";
_p.RemoveView();
 //BA.debugLineNum = 232;BA.debugLine="End Sub";
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
 //BA.debugLineNum = 103;BA.debugLine="Sub SetProgressDrawable(p As ProgressBar, drawable";
 //BA.debugLineNum = 104;BA.debugLine="Dim r As Reflector";
_r = new anywheresoftware.b4a.agraham.reflection.Reflection();
 //BA.debugLineNum = 106;BA.debugLine="Dim clipDrawable As Object";
_clipdrawable = new Object();
 //BA.debugLineNum = 107;BA.debugLine="clipDrawable = r.CreateObject2(\"android.graphics.";
_clipdrawable = _r.CreateObject2("android.graphics.drawable.ClipDrawable",new Object[]{_drawable,(Object)(anywheresoftware.b4a.keywords.Common.Gravity.LEFT),(Object)(1)},new String[]{"android.graphics.drawable.Drawable","java.lang.int","java.lang.int"});
 //BA.debugLineNum = 110;BA.debugLine="r.Target = p";
_r.Target = (Object)(_p.getObject());
 //BA.debugLineNum = 111;BA.debugLine="r.Target = r.RunMethod(\"getProgressDrawable\") 'Ge";
_r.Target = _r.RunMethod("getProgressDrawable");
 //BA.debugLineNum = 112;BA.debugLine="r.RunMethod4(\"setDrawableByLayerId\", _       Arra";
_r.RunMethod4("setDrawableByLayerId",new Object[]{(Object)(16908288),_backgrounddrawable},new String[]{"java.lang.int","android.graphics.drawable.Drawable"});
 //BA.debugLineNum = 115;BA.debugLine="r.RunMethod4(\"setDrawableByLayerId\", _       Arra";
_r.RunMethod4("setDrawableByLayerId",new Object[]{_r.GetStaticField("android.R$id","progress"),_clipdrawable},new String[]{"java.lang.int","android.graphics.drawable.Drawable"});
 //BA.debugLineNum = 119;BA.debugLine="End Sub";
return "";
}
}
