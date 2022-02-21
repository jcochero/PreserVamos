package appear.pnud.preservamos;


import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.B4AClass;
import anywheresoftware.b4a.BALayout;
import anywheresoftware.b4a.debug.*;

public class circularprogressbar extends B4AClass.ImplB4AClass implements BA.SubDelegator{
    private static java.util.HashMap<String, java.lang.reflect.Method> htSubs;
    private void innerInitialize(BA _ba) throws Exception {
        if (ba == null) {
            ba = new BA(_ba, this, htSubs, "appear.pnud.preservamos.circularprogressbar");
            if (htSubs == null) {
                ba.loadHtSubs(this.getClass());
                htSubs = ba.htSubs;
            }
            
        }
        if (BA.isShellModeRuntimeCheck(ba)) 
			   this.getClass().getMethod("_class_globals", appear.pnud.preservamos.circularprogressbar.class).invoke(this, new Object[] {null});
        else
            ba.raiseEvent2(null, true, "class_globals", false);
    }

 public anywheresoftware.b4a.keywords.Common __c = null;
public String _meventname = "";
public Object _mcallback = null;
public anywheresoftware.b4a.objects.B4XCanvas _cvs = null;
public anywheresoftware.b4a.objects.B4XViewWrapper.XUI _xui = null;
public anywheresoftware.b4a.objects.B4XViewWrapper _mlbl = null;
public float _cx = 0f;
public float _cy = 0f;
public float _radius = 0f;
public float _stroke = 0f;
public int _clrfull = 0;
public int _clrempty = 0;
public anywheresoftware.b4a.objects.B4XViewWrapper _mbase = null;
public float _currentvalue = 0f;
public int _durationfromzeroto100 = 0;
public String _munit = "";
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
public void  _animatevalueto(float _newvalue) throws Exception{
ResumableSub_AnimateValueTo rsub = new ResumableSub_AnimateValueTo(this,_newvalue);
rsub.resume(ba, null);
}
public static class ResumableSub_AnimateValueTo extends BA.ResumableSub {
public ResumableSub_AnimateValueTo(appear.pnud.preservamos.circularprogressbar parent,float _newvalue) {
this.parent = parent;
this._newvalue = _newvalue;
}
appear.pnud.preservamos.circularprogressbar parent;
float _newvalue;
long _n = 0L;
int _duration = 0;
float _start = 0f;
float _tempvalue = 0f;

@Override
public void resume(BA ba, Object[] result) throws Exception{

    while (true) {
        switch (state) {
            case -1:
return;

case 0:
//C
this.state = 1;
 //BA.debugLineNum = 98;BA.debugLine="Dim n As Long = DateTime.Now";
_n = parent.__c.DateTime.getNow();
 //BA.debugLineNum = 99;BA.debugLine="Dim duration As Int = Abs(currentValue - NewValue";
_duration = (int) (parent.__c.Abs(parent._currentvalue-_newvalue)/(double)100*parent._durationfromzeroto100+1000);
 //BA.debugLineNum = 100;BA.debugLine="Dim start As Float = currentValue";
_start = parent._currentvalue;
 //BA.debugLineNum = 101;BA.debugLine="currentValue = NewValue";
parent._currentvalue = _newvalue;
 //BA.debugLineNum = 102;BA.debugLine="Dim tempValue As Float";
_tempvalue = 0f;
 //BA.debugLineNum = 103;BA.debugLine="Do While DateTime.Now < n + duration";
if (true) break;

case 1:
//do while
this.state = 10;
while (parent.__c.DateTime.getNow()<_n+_duration) {
this.state = 3;
if (true) break;
}
if (true) break;

case 3:
//C
this.state = 4;
 //BA.debugLineNum = 104;BA.debugLine="tempValue = ValueFromTimeEaseInOut(DateTime.Now";
_tempvalue = parent._valuefromtimeeaseinout((float) (parent.__c.DateTime.getNow()-_n),_start,(float) (_newvalue-_start),_duration);
 //BA.debugLineNum = 105;BA.debugLine="DrawValue(tempValue)";
parent._drawvalue(_tempvalue);
 //BA.debugLineNum = 106;BA.debugLine="Sleep(10)";
parent.__c.Sleep(ba,this,(int) (10));
this.state = 11;
return;
case 11:
//C
this.state = 4;
;
 //BA.debugLineNum = 107;BA.debugLine="If NewValue <> currentValue Then Return 'will ha";
if (true) break;

case 4:
//if
this.state = 9;
if (_newvalue!=parent._currentvalue) { 
this.state = 6;
;}if (true) break;

case 6:
//C
this.state = 9;
if (true) return ;
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
 //BA.debugLineNum = 109;BA.debugLine="DrawValue(currentValue)";
parent._drawvalue(parent._currentvalue);
 //BA.debugLineNum = 110;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public String  _base_resize(double _width,double _height) throws Exception{
 //BA.debugLineNum = 58;BA.debugLine="Private Sub Base_Resize (Width As Double, Height A";
 //BA.debugLineNum = 59;BA.debugLine="cx = Width / 2";
_cx = (float) (_width/(double)2);
 //BA.debugLineNum = 60;BA.debugLine="cy = Height / 2";
_cy = (float) (_height/(double)2);
 //BA.debugLineNum = 61;BA.debugLine="radius = cx - 10dip";
_radius = (float) (_cx-__c.DipToCurrent((int) (10)));
 //BA.debugLineNum = 62;BA.debugLine="mBase.SetLayoutAnimated(0, mBase.Left, mBase.Top,";
_mbase.SetLayoutAnimated((int) (0),_mbase.getLeft(),_mbase.getTop(),(int) (__c.Min(_width,_height)),(int) (__c.Min(_width,_height)));
 //BA.debugLineNum = 63;BA.debugLine="cvs.Resize(Width, Height)";
_cvs.Resize((float) (_width),(float) (_height));
 //BA.debugLineNum = 64;BA.debugLine="mLbl.SetLayoutAnimated(0, 0, cy - 20dip, Width, 4";
_mlbl.SetLayoutAnimated((int) (0),(int) (0),(int) (_cy-__c.DipToCurrent((int) (20))),(int) (_width),__c.DipToCurrent((int) (40)));
 //BA.debugLineNum = 65;BA.debugLine="DrawValue(currentValue)";
_drawvalue(_currentvalue);
 //BA.debugLineNum = 66;BA.debugLine="End Sub";
return "";
}
public String  _class_globals() throws Exception{
 //BA.debugLineNum = 14;BA.debugLine="Sub Class_Globals";
 //BA.debugLineNum = 15;BA.debugLine="Private mEventName As String 'ignore";
_meventname = "";
 //BA.debugLineNum = 16;BA.debugLine="Private mCallBack As Object 'ignore";
_mcallback = new Object();
 //BA.debugLineNum = 17;BA.debugLine="Private cvs As B4XCanvas";
_cvs = new anywheresoftware.b4a.objects.B4XCanvas();
 //BA.debugLineNum = 18;BA.debugLine="Private xui As XUI";
_xui = new anywheresoftware.b4a.objects.B4XViewWrapper.XUI();
 //BA.debugLineNum = 19;BA.debugLine="Private mLbl As B4XView";
_mlbl = new anywheresoftware.b4a.objects.B4XViewWrapper();
 //BA.debugLineNum = 20;BA.debugLine="Private cx, cy, radius As Float";
_cx = 0f;
_cy = 0f;
_radius = 0f;
 //BA.debugLineNum = 21;BA.debugLine="Private stroke As Float";
_stroke = 0f;
 //BA.debugLineNum = 22;BA.debugLine="Private clrFull, clrEmpty As Int";
_clrfull = 0;
_clrempty = 0;
 //BA.debugLineNum = 23;BA.debugLine="Private mBase As B4XView";
_mbase = new anywheresoftware.b4a.objects.B4XViewWrapper();
 //BA.debugLineNum = 24;BA.debugLine="Private currentValue As Float=0";
_currentvalue = (float) (0);
 //BA.debugLineNum = 25;BA.debugLine="Private DurationFromZeroTo100 As Int";
_durationfromzeroto100 = 0;
 //BA.debugLineNum = 26;BA.debugLine="Private mUnit As String=\"\"";
_munit = "";
 //BA.debugLineNum = 28;BA.debugLine="End Sub";
return "";
}
public String  _designercreateview(Object _base,anywheresoftware.b4a.objects.LabelWrapper _lbl,anywheresoftware.b4a.objects.collections.Map _props) throws Exception{
 //BA.debugLineNum = 35;BA.debugLine="Public Sub DesignerCreateView (Base As Object, Lbl";
 //BA.debugLineNum = 36;BA.debugLine="mBase = Base";
_mbase = (anywheresoftware.b4a.objects.B4XViewWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.B4XViewWrapper(), (java.lang.Object)(_base));
 //BA.debugLineNum = 37;BA.debugLine="mBase.SetLayoutAnimated(0, mBase.Left, mBase.Top,";
_mbase.SetLayoutAnimated((int) (0),_mbase.getLeft(),_mbase.getTop(),(int) (__c.Min(_mbase.getWidth(),_mbase.getHeight())),(int) (__c.Min(_mbase.getWidth(),_mbase.getHeight())));
 //BA.debugLineNum = 38;BA.debugLine="clrFull = xui.PaintOrColorToColor(Props.Get(\"Colo";
_clrfull = _xui.PaintOrColorToColor(_props.Get((Object)("ColorFull")));
 //BA.debugLineNum = 39;BA.debugLine="clrEmpty = xui.PaintOrColorToColor(Props.Get(\"Col";
_clrempty = _xui.PaintOrColorToColor(_props.Get((Object)("ColorEmpty")));
 //BA.debugLineNum = 40;BA.debugLine="stroke = DipToCurrent(Props.Get(\"StrokeWidth\"))";
_stroke = (float) (__c.DipToCurrent((int)(BA.ObjectToNumber(_props.Get((Object)("StrokeWidth"))))));
 //BA.debugLineNum = 41;BA.debugLine="DurationFromZeroTo100 = Props.Get(\"Duration\")";
_durationfromzeroto100 = (int)(BA.ObjectToNumber(_props.Get((Object)("Duration"))));
 //BA.debugLineNum = 42;BA.debugLine="mLbl = Lbl";
_mlbl = (anywheresoftware.b4a.objects.B4XViewWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.B4XViewWrapper(), (java.lang.Object)(_lbl.getObject()));
 //BA.debugLineNum = 43;BA.debugLine="cx = mBase.Width / 2";
_cx = (float) (_mbase.getWidth()/(double)2);
 //BA.debugLineNum = 44;BA.debugLine="cy = mBase.Height / 2";
_cy = (float) (_mbase.getHeight()/(double)2);
 //BA.debugLineNum = 47;BA.debugLine="radius = cx-stroke/2";
_radius = (float) (_cx-_stroke/(double)2);
 //BA.debugLineNum = 48;BA.debugLine="cvs.Initialize(mBase)";
_cvs.Initialize(_mbase);
 //BA.debugLineNum = 49;BA.debugLine="mLbl.SetTextAlignment(\"CENTER\", \"CENTER\")";
_mlbl.SetTextAlignment("CENTER","CENTER");
 //BA.debugLineNum = 52;BA.debugLine="mBase.AddView(mLbl, stroke, stroke, mBase.Width-2";
_mbase.AddView((android.view.View)(_mlbl.getObject()),(int) (_stroke),(int) (_stroke),(int) (_mbase.getWidth()-2*_stroke),(int) (_mbase.getHeight()-2*_stroke));
 //BA.debugLineNum = 54;BA.debugLine="cvs.Initialize(mBase)";
_cvs.Initialize(_mbase);
 //BA.debugLineNum = 55;BA.debugLine="DrawValue(currentValue)";
_drawvalue(_currentvalue);
 //BA.debugLineNum = 56;BA.debugLine="End Sub";
return "";
}
public String  _drawvalue(float _value) throws Exception{
float _startangle = 0f;
float _sweepangle = 0f;
anywheresoftware.b4a.objects.B4XCanvas.B4XPath _p = null;
 //BA.debugLineNum = 123;BA.debugLine="Private Sub DrawValue(Value As Float)";
 //BA.debugLineNum = 125;BA.debugLine="cvs.ClearRect(cvs.TargetRect)";
_cvs.ClearRect(_cvs.getTargetRect());
 //BA.debugLineNum = 126;BA.debugLine="cvs.DrawCircle(cx, cy, radius, clrEmpty, False, s";
_cvs.DrawCircle(_cx,_cy,_radius,_clrempty,__c.False,_stroke);
 //BA.debugLineNum = 127;BA.debugLine="mLbl.Text = $\"$1.0{Value}\"$ & mUnit";
_mlbl.setText(BA.ObjectToCharSequence((""+__c.SmartStringFormatter("1.0",(Object)(_value))+"")+_munit));
 //BA.debugLineNum = 128;BA.debugLine="Dim startAngle = -90, sweepAngle = Value / 100 *";
_startangle = (float) (-90);
_sweepangle = (float) (_value/(double)100*360);
 //BA.debugLineNum = 130;BA.debugLine="If Value < 100 Then";
if (_value<100) { 
 //BA.debugLineNum = 131;BA.debugLine="Dim p As B4XPath";
_p = new anywheresoftware.b4a.objects.B4XCanvas.B4XPath();
 //BA.debugLineNum = 132;BA.debugLine="p.InitializeArc(cx, cy, radius + stroke + 1dip,";
_p.InitializeArc(_cx,_cy,(float) (_radius+_stroke+__c.DipToCurrent((int) (1))),_startangle,_sweepangle);
 //BA.debugLineNum = 133;BA.debugLine="cvs.ClipPath(p)";
_cvs.ClipPath(_p);
 //BA.debugLineNum = 134;BA.debugLine="cvs.DrawCircle(cx, cy, radius - 0.5dip, clrFull,";
_cvs.DrawCircle(_cx,_cy,(float) (_radius-__c.DipToCurrent((int) (0.5))),_clrfull,__c.False,(float) (_stroke+__c.DipToCurrent((int) (1))));
 //BA.debugLineNum = 135;BA.debugLine="cvs.RemoveClip";
_cvs.RemoveClip();
 }else {
 //BA.debugLineNum = 137;BA.debugLine="cvs.DrawCircle(cx, cy, radius - 0.5dip, clrFull,";
_cvs.DrawCircle(_cx,_cy,(float) (_radius-__c.DipToCurrent((int) (0.5))),_clrfull,__c.False,(float) (_stroke+__c.DipToCurrent((int) (1))));
 };
 //BA.debugLineNum = 139;BA.debugLine="cvs.Invalidate";
_cvs.Invalidate();
 //BA.debugLineNum = 140;BA.debugLine="End Sub";
return "";
}
public float  _getvalue() throws Exception{
 //BA.debugLineNum = 72;BA.debugLine="Public Sub getValue As Float";
 //BA.debugLineNum = 73;BA.debugLine="Return currentValue";
if (true) return _currentvalue;
 //BA.debugLineNum = 74;BA.debugLine="End Sub";
return 0f;
}
public boolean  _getvisible() throws Exception{
 //BA.debugLineNum = 81;BA.debugLine="public Sub getVisible As Boolean";
 //BA.debugLineNum = 82;BA.debugLine="Return mBase.Visible";
if (true) return _mbase.getVisible();
 //BA.debugLineNum = 83;BA.debugLine="End Sub";
return false;
}
public String  _initialize(anywheresoftware.b4a.BA _ba,Object _callback,String _eventname) throws Exception{
innerInitialize(_ba);
 //BA.debugLineNum = 30;BA.debugLine="Public Sub Initialize (Callback As Object, EventNa";
 //BA.debugLineNum = 31;BA.debugLine="mEventName = EventName";
_meventname = _eventname;
 //BA.debugLineNum = 32;BA.debugLine="mCallBack = Callback";
_mcallback = _callback;
 //BA.debugLineNum = 33;BA.debugLine="End Sub";
return "";
}
public String  _reset() throws Exception{
 //BA.debugLineNum = 92;BA.debugLine="public Sub Reset";
 //BA.debugLineNum = 93;BA.debugLine="currentValue=0";
_currentvalue = (float) (0);
 //BA.debugLineNum = 94;BA.debugLine="DrawValue(currentValue)";
_drawvalue(_currentvalue);
 //BA.debugLineNum = 95;BA.debugLine="End Sub";
return "";
}
public String  _setvalue(float _newvalue) throws Exception{
 //BA.debugLineNum = 68;BA.debugLine="Public Sub setValue(NewValue As Float)";
 //BA.debugLineNum = 69;BA.debugLine="AnimateValueTo(NewValue)";
_animatevalueto(_newvalue);
 //BA.debugLineNum = 70;BA.debugLine="End Sub";
return "";
}
public String  _setvalueunit(String _unit) throws Exception{
 //BA.debugLineNum = 86;BA.debugLine="public Sub setValueUnit (Unit As String)";
 //BA.debugLineNum = 87;BA.debugLine="mUnit=Unit";
_munit = _unit;
 //BA.debugLineNum = 88;BA.debugLine="DrawValue(currentValue)";
_drawvalue(_currentvalue);
 //BA.debugLineNum = 89;BA.debugLine="End Sub";
return "";
}
public String  _setvisible(boolean _visible) throws Exception{
 //BA.debugLineNum = 77;BA.debugLine="public Sub setVisible(Visible As Boolean)";
 //BA.debugLineNum = 78;BA.debugLine="mBase.Visible=Visible";
_mbase.setVisible(_visible);
 //BA.debugLineNum = 79;BA.debugLine="End Sub";
return "";
}
public float  _valuefromtimeeaseinout(float _time,float _start,float _changeinvalue,int _duration) throws Exception{
 //BA.debugLineNum = 113;BA.debugLine="Private Sub ValueFromTimeEaseInOut(Time As Float,";
 //BA.debugLineNum = 114;BA.debugLine="Time = Time / (Duration / 2)";
_time = (float) (_time/(double)(_duration/(double)2));
 //BA.debugLineNum = 115;BA.debugLine="If Time < 1 Then";
if (_time<1) { 
 //BA.debugLineNum = 116;BA.debugLine="Return ChangeInValue / 2 * Time * Time * Time *";
if (true) return (float) (_changeinvalue/(double)2*_time*_time*_time*_time+_start);
 }else {
 //BA.debugLineNum = 118;BA.debugLine="Time = Time - 2";
_time = (float) (_time-2);
 //BA.debugLineNum = 119;BA.debugLine="Return -ChangeInValue / 2 * (Time * Time * Time";
if (true) return (float) (-_changeinvalue/(double)2*(_time*_time*_time*_time-2)+_start);
 };
 //BA.debugLineNum = 121;BA.debugLine="End Sub";
return 0f;
}
public Object callSub(String sub, Object sender, Object[] args) throws Exception {
BA.senderHolder.set(sender);
return BA.SubDelegator.SubNotFound;
}
}
