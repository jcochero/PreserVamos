package appear.pnud.preservamos;


import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.B4AClass;
import anywheresoftware.b4a.BALayout;
import anywheresoftware.b4a.debug.*;

public class b4xdrawer extends B4AClass.ImplB4AClass implements BA.SubDelegator{
    private static java.util.HashMap<String, java.lang.reflect.Method> htSubs;
    private void innerInitialize(BA _ba) throws Exception {
        if (ba == null) {
            ba = new BA(_ba, this, htSubs, "appear.pnud.preservamos.b4xdrawer");
            if (htSubs == null) {
                ba.loadHtSubs(this.getClass());
                htSubs = ba.htSubs;
            }
            
        }
        if (BA.isShellModeRuntimeCheck(ba)) 
			   this.getClass().getMethod("_class_globals", appear.pnud.preservamos.b4xdrawer.class).invoke(this, new Object[] {null});
        else
            ba.raiseEvent2(null, true, "class_globals", false);
    }

 public anywheresoftware.b4a.keywords.Common __c = null;
public String _meventname = "";
public Object _mcallback = null;
public anywheresoftware.b4a.objects.B4XViewWrapper.XUI _xui = null;
public int _msidewidth = 0;
public anywheresoftware.b4a.objects.B4XViewWrapper _mleftpanel = null;
public anywheresoftware.b4a.objects.B4XViewWrapper _mdarkpanel = null;
public anywheresoftware.b4a.objects.B4XViewWrapper _mbasepanel = null;
public anywheresoftware.b4a.objects.B4XViewWrapper _mcenterpanel = null;
public int _extrawidth = 0;
public float _touchxstart = 0f;
public float _touchystart = 0f;
public boolean _isopen = false;
public boolean _handlingswipe = false;
public boolean _startatscrim = false;
public boolean _menabled = false;
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
public appear.pnud.preservamos.imagedownloader _imagedownloader = null;
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
public boolean  _base_onintercepttouchevent(int _action,float _x,float _y,Object _motionevent) throws Exception{
float _dx = 0f;
float _dy = 0f;
 //BA.debugLineNum = 93;BA.debugLine="Private Sub Base_OnInterceptTouchEvent (Action As";
 //BA.debugLineNum = 94;BA.debugLine="If mEnabled = False Then Return False";
if (_menabled==__c.False) { 
if (true) return __c.False;};
 //BA.debugLineNum = 95;BA.debugLine="If IsOpen = False And x > mLeftPanel.Left + mLeft";
if (_isopen==__c.False && _x>_mleftpanel.getLeft()+_mleftpanel.getWidth()+_extrawidth) { 
 //BA.debugLineNum = 96;BA.debugLine="Return False";
if (true) return __c.False;
 };
 //BA.debugLineNum = 98;BA.debugLine="If IsOpen And x > mLeftPanel.Left + mLeftPanel.Wi";
if (_isopen && _x>_mleftpanel.getLeft()+_mleftpanel.getWidth()) { 
 //BA.debugLineNum = 100;BA.debugLine="Return True";
if (true) return __c.True;
 };
 //BA.debugLineNum = 102;BA.debugLine="If HandlingSwipe Then Return True";
if (_handlingswipe) { 
if (true) return __c.True;};
 //BA.debugLineNum = 103;BA.debugLine="Select Action";
switch (BA.switchObjectToInt(_action,_mbasepanel.TOUCH_ACTION_DOWN,_mbasepanel.TOUCH_ACTION_MOVE)) {
case 0: {
 //BA.debugLineNum = 105;BA.debugLine="TouchXStart = X";
_touchxstart = _x;
 //BA.debugLineNum = 106;BA.debugLine="TouchYStart = Y";
_touchystart = _y;
 //BA.debugLineNum = 107;BA.debugLine="HandlingSwipe = False";
_handlingswipe = __c.False;
 break; }
case 1: {
 //BA.debugLineNum = 109;BA.debugLine="Dim dx As Float = Abs(x - TouchXStart)";
_dx = (float) (__c.Abs(_x-_touchxstart));
 //BA.debugLineNum = 110;BA.debugLine="Dim dy As Float = Abs(y - TouchYStart)";
_dy = (float) (__c.Abs(_y-_touchystart));
 //BA.debugLineNum = 111;BA.debugLine="If dy < 20dip And dx > 10dip Then";
if (_dy<__c.DipToCurrent((int) (20)) && _dx>__c.DipToCurrent((int) (10))) { 
 //BA.debugLineNum = 112;BA.debugLine="HandlingSwipe = True";
_handlingswipe = __c.True;
 };
 break; }
}
;
 //BA.debugLineNum = 115;BA.debugLine="Return HandlingSwipe";
if (true) return _handlingswipe;
 //BA.debugLineNum = 116;BA.debugLine="End Sub";
return false;
}
public boolean  _base_ontouchevent(int _action,float _x,float _y,Object _motionevent) throws Exception{
int _leftpanelrightside = 0;
float _dx = 0f;
 //BA.debugLineNum = 62;BA.debugLine="Private Sub Base_OnTouchEvent (Action As Int, X As";
 //BA.debugLineNum = 63;BA.debugLine="If mEnabled = False Then Return False";
if (_menabled==__c.False) { 
if (true) return __c.False;};
 //BA.debugLineNum = 64;BA.debugLine="Dim LeftPanelRightSide As Int = mLeftPanel.Left +";
_leftpanelrightside = (int) (_mleftpanel.getLeft()+_mleftpanel.getWidth());
 //BA.debugLineNum = 65;BA.debugLine="If HandlingSwipe = False And x > LeftPanelRightSi";
if (_handlingswipe==__c.False && _x>_leftpanelrightside) { 
 //BA.debugLineNum = 66;BA.debugLine="If IsOpen Then";
if (_isopen) { 
 //BA.debugLineNum = 67;BA.debugLine="TouchXStart = X";
_touchxstart = _x;
 //BA.debugLineNum = 68;BA.debugLine="If Action = mBasePanel.TOUCH_ACTION_UP Then set";
if (_action==_mbasepanel.TOUCH_ACTION_UP) { 
_setleftopen(__c.False);};
 //BA.debugLineNum = 69;BA.debugLine="Return True";
if (true) return __c.True;
 };
 //BA.debugLineNum = 71;BA.debugLine="If IsOpen = False And x > LeftPanelRightSide + E";
if (_isopen==__c.False && _x>_leftpanelrightside+_extrawidth) { 
 //BA.debugLineNum = 72;BA.debugLine="Return False";
if (true) return __c.False;
 };
 };
 //BA.debugLineNum = 75;BA.debugLine="Select Action";
switch (BA.switchObjectToInt(_action,_mbasepanel.TOUCH_ACTION_MOVE,_mbasepanel.TOUCH_ACTION_UP)) {
case 0: {
 //BA.debugLineNum = 77;BA.debugLine="Dim dx As Float = x - TouchXStart";
_dx = (float) (_x-_touchxstart);
 //BA.debugLineNum = 78;BA.debugLine="TouchXStart = X";
_touchxstart = _x;
 //BA.debugLineNum = 79;BA.debugLine="If HandlingSwipe Or Abs(dx) > 3dip Then";
if (_handlingswipe || __c.Abs(_dx)>__c.DipToCurrent((int) (3))) { 
 //BA.debugLineNum = 80;BA.debugLine="HandlingSwipe = True";
_handlingswipe = __c.True;
 //BA.debugLineNum = 81;BA.debugLine="ChangeOffset(mLeftPanel.Left + dx, True, False";
_changeoffset((float) (_mleftpanel.getLeft()+_dx),__c.True,__c.False);
 };
 break; }
case 1: {
 //BA.debugLineNum = 84;BA.debugLine="If HandlingSwipe Then";
if (_handlingswipe) { 
 //BA.debugLineNum = 85;BA.debugLine="ChangeOffset(mLeftPanel.Left, False, False)";
_changeoffset((float) (_mleftpanel.getLeft()),__c.False,__c.False);
 };
 //BA.debugLineNum = 87;BA.debugLine="HandlingSwipe = False";
_handlingswipe = __c.False;
 break; }
}
;
 //BA.debugLineNum = 89;BA.debugLine="Return True";
if (true) return __c.True;
 //BA.debugLineNum = 90;BA.debugLine="End Sub";
return false;
}
public String  _changeoffset(float _x,boolean _currentlytouching,boolean _noanimation) throws Exception{
int _visibleoffset = 0;
int _dx = 0;
int _duration = 0;
 //BA.debugLineNum = 172;BA.debugLine="Private Sub ChangeOffset (x As Float, CurrentlyTou";
 //BA.debugLineNum = 173;BA.debugLine="x = Max(-mSideWidth, Min(0, x))";
_x = (float) (__c.Max(-_msidewidth,__c.Min(0,_x)));
 //BA.debugLineNum = 174;BA.debugLine="Dim VisibleOffset As Int = mSideWidth + x";
_visibleoffset = (int) (_msidewidth+_x);
 //BA.debugLineNum = 183;BA.debugLine="If CurrentlyTouching = False Then";
if (_currentlytouching==__c.False) { 
 //BA.debugLineNum = 184;BA.debugLine="If (IsOpen And VisibleOffset < 0.8 * mSideWidth)";
if ((_isopen && _visibleoffset<0.8*_msidewidth) || (_isopen==__c.False && _visibleoffset<0.2*_msidewidth)) { 
 //BA.debugLineNum = 185;BA.debugLine="x = -mSideWidth";
_x = (float) (-_msidewidth);
 //BA.debugLineNum = 186;BA.debugLine="IsOpen = False";
_isopen = __c.False;
 }else {
 //BA.debugLineNum = 188;BA.debugLine="x = 0";
_x = (float) (0);
 //BA.debugLineNum = 189;BA.debugLine="IsOpen = True";
_isopen = __c.True;
 };
 //BA.debugLineNum = 191;BA.debugLine="Dim dx As Int = Abs(mLeftPanel.Left - x)";
_dx = (int) (__c.Abs(_mleftpanel.getLeft()-_x));
 //BA.debugLineNum = 192;BA.debugLine="Dim duration As Int = Max(0, 200 * dx / mSideWid";
_duration = (int) (__c.Max(0,200*_dx/(double)_msidewidth));
 //BA.debugLineNum = 193;BA.debugLine="If NoAnimation Then duration = 0";
if (_noanimation) { 
_duration = (int) (0);};
 //BA.debugLineNum = 194;BA.debugLine="mLeftPanel.SetLayoutAnimated(duration, x, 0, mLe";
_mleftpanel.SetLayoutAnimated(_duration,(int) (_x),(int) (0),_mleftpanel.getWidth(),_mleftpanel.getHeight());
 //BA.debugLineNum = 195;BA.debugLine="mDarkPanel.SetColorAnimated(duration, mDarkPanel";
_mdarkpanel.SetColorAnimated(_duration,_mdarkpanel.getColor(),_offsettocolor((int) (_x)));
 }else {
 //BA.debugLineNum = 203;BA.debugLine="mDarkPanel.Color = OffsetToColor(x)";
_mdarkpanel.setColor(_offsettocolor((int) (_x)));
 //BA.debugLineNum = 204;BA.debugLine="mLeftPanel.Left = x";
_mleftpanel.setLeft((int) (_x));
 };
 //BA.debugLineNum = 207;BA.debugLine="End Sub";
return "";
}
public String  _class_globals() throws Exception{
 //BA.debugLineNum = 2;BA.debugLine="Sub Class_Globals";
 //BA.debugLineNum = 3;BA.debugLine="Private mEventName As String 'ignore";
_meventname = "";
 //BA.debugLineNum = 4;BA.debugLine="Private mCallBack As Object 'ignore";
_mcallback = new Object();
 //BA.debugLineNum = 5;BA.debugLine="Private xui As XUI 'ignore";
_xui = new anywheresoftware.b4a.objects.B4XViewWrapper.XUI();
 //BA.debugLineNum = 6;BA.debugLine="Private mSideWidth As Int";
_msidewidth = 0;
 //BA.debugLineNum = 7;BA.debugLine="Private mLeftPanel As B4XView";
_mleftpanel = new anywheresoftware.b4a.objects.B4XViewWrapper();
 //BA.debugLineNum = 8;BA.debugLine="Private mDarkPanel As B4XView";
_mdarkpanel = new anywheresoftware.b4a.objects.B4XViewWrapper();
 //BA.debugLineNum = 9;BA.debugLine="Private mBasePanel As B4XView";
_mbasepanel = new anywheresoftware.b4a.objects.B4XViewWrapper();
 //BA.debugLineNum = 10;BA.debugLine="Private mCenterPanel As B4XView";
_mcenterpanel = new anywheresoftware.b4a.objects.B4XViewWrapper();
 //BA.debugLineNum = 11;BA.debugLine="Private ExtraWidth As Int = 50dip";
_extrawidth = __c.DipToCurrent((int) (50));
 //BA.debugLineNum = 12;BA.debugLine="Private TouchXStart, TouchYStart As Float 'ignore";
_touchxstart = 0f;
_touchystart = 0f;
 //BA.debugLineNum = 13;BA.debugLine="Private IsOpen As Boolean";
_isopen = false;
 //BA.debugLineNum = 14;BA.debugLine="Private HandlingSwipe As Boolean";
_handlingswipe = false;
 //BA.debugLineNum = 15;BA.debugLine="Private StartAtScrim As Boolean 'ignore";
_startatscrim = false;
 //BA.debugLineNum = 16;BA.debugLine="Private mEnabled As Boolean = True";
_menabled = __c.True;
 //BA.debugLineNum = 17;BA.debugLine="End Sub";
return "";
}
public anywheresoftware.b4a.objects.B4XViewWrapper  _getcenterpanel() throws Exception{
 //BA.debugLineNum = 231;BA.debugLine="Public Sub getCenterPanel As B4XView";
 //BA.debugLineNum = 232;BA.debugLine="Return mCenterPanel";
if (true) return _mcenterpanel;
 //BA.debugLineNum = 233;BA.debugLine="End Sub";
return null;
}
public boolean  _getgestureenabled() throws Exception{
 //BA.debugLineNum = 243;BA.debugLine="Public Sub getGestureEnabled As Boolean";
 //BA.debugLineNum = 244;BA.debugLine="Return mEnabled";
if (true) return _menabled;
 //BA.debugLineNum = 245;BA.debugLine="End Sub";
return false;
}
public boolean  _getleftopen() throws Exception{
 //BA.debugLineNum = 214;BA.debugLine="Public Sub getLeftOpen As Boolean";
 //BA.debugLineNum = 215;BA.debugLine="Return IsOpen";
if (true) return _isopen;
 //BA.debugLineNum = 216;BA.debugLine="End Sub";
return false;
}
public anywheresoftware.b4a.objects.B4XViewWrapper  _getleftpanel() throws Exception{
 //BA.debugLineNum = 227;BA.debugLine="Public Sub getLeftPanel As B4XView";
 //BA.debugLineNum = 228;BA.debugLine="Return mLeftPanel";
if (true) return _mleftpanel;
 //BA.debugLineNum = 229;BA.debugLine="End Sub";
return null;
}
public String  _initialize(anywheresoftware.b4a.BA _ba,Object _callback,String _eventname,anywheresoftware.b4a.objects.B4XViewWrapper _parent,int _sidewidth) throws Exception{
innerInitialize(_ba);
anywheresoftware.b4a.objects.TouchPanelCreator _creator = null;
anywheresoftware.b4a.objects.PanelWrapper _p = null;
 //BA.debugLineNum = 19;BA.debugLine="Public Sub Initialize (Callback As Object, EventNa";
 //BA.debugLineNum = 20;BA.debugLine="mEventName = EventName";
_meventname = _eventname;
 //BA.debugLineNum = 21;BA.debugLine="mCallBack = Callback";
_mcallback = _callback;
 //BA.debugLineNum = 22;BA.debugLine="mSideWidth = SideWidth";
_msidewidth = _sidewidth;
 //BA.debugLineNum = 24;BA.debugLine="Dim creator As TouchPanelCreator";
_creator = new anywheresoftware.b4a.objects.TouchPanelCreator();
 //BA.debugLineNum = 25;BA.debugLine="mBasePanel = creator.CreateTouchPanel(\"base\")";
_mbasepanel = (anywheresoftware.b4a.objects.B4XViewWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.B4XViewWrapper(), (java.lang.Object)(_creator.CreateTouchPanel(ba,"base").getObject()));
 //BA.debugLineNum = 32;BA.debugLine="Parent.AddView(mBasePanel, 0, 0, Parent.Width, Pa";
_parent.AddView((android.view.View)(_mbasepanel.getObject()),(int) (0),(int) (0),_parent.getWidth(),_parent.getHeight());
 //BA.debugLineNum = 33;BA.debugLine="mCenterPanel = xui.CreatePanel(\"\")";
_mcenterpanel = _xui.CreatePanel(ba,"");
 //BA.debugLineNum = 34;BA.debugLine="mBasePanel.AddView(mCenterPanel, 0, 0, mBasePanel";
_mbasepanel.AddView((android.view.View)(_mcenterpanel.getObject()),(int) (0),(int) (0),_mbasepanel.getWidth(),_mbasepanel.getHeight());
 //BA.debugLineNum = 35;BA.debugLine="mDarkPanel = xui.CreatePanel(\"dark\")";
_mdarkpanel = _xui.CreatePanel(ba,"dark");
 //BA.debugLineNum = 36;BA.debugLine="mBasePanel.AddView(mDarkPanel, 0, 0, mBasePanel.W";
_mbasepanel.AddView((android.view.View)(_mdarkpanel.getObject()),(int) (0),(int) (0),_mbasepanel.getWidth(),_mbasepanel.getHeight());
 //BA.debugLineNum = 37;BA.debugLine="mLeftPanel = xui.CreatePanel(\"LeftPanel\")";
_mleftpanel = _xui.CreatePanel(ba,"LeftPanel");
 //BA.debugLineNum = 38;BA.debugLine="mBasePanel.AddView(mLeftPanel, -SideWidth, 0, Sid";
_mbasepanel.AddView((android.view.View)(_mleftpanel.getObject()),(int) (-_sidewidth),(int) (0),_sidewidth,_mbasepanel.getHeight());
 //BA.debugLineNum = 39;BA.debugLine="mLeftPanel.Color = xui.Color_Red";
_mleftpanel.setColor(_xui.Color_Red);
 //BA.debugLineNum = 41;BA.debugLine="Dim p As Panel = mLeftPanel";
_p = new anywheresoftware.b4a.objects.PanelWrapper();
_p = (anywheresoftware.b4a.objects.PanelWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.PanelWrapper(), (android.view.ViewGroup)(_mleftpanel.getObject()));
 //BA.debugLineNum = 42;BA.debugLine="p.Elevation = 4dip";
_p.setElevation((float) (__c.DipToCurrent((int) (4))));
 //BA.debugLineNum = 54;BA.debugLine="End Sub";
return "";
}
public String  _leftpanel_click() throws Exception{
 //BA.debugLineNum = 56;BA.debugLine="Private Sub LeftPanel_Click";
 //BA.debugLineNum = 58;BA.debugLine="End Sub";
return "";
}
public int  _offsettocolor(int _x) throws Exception{
float _visible = 0f;
 //BA.debugLineNum = 209;BA.debugLine="Private Sub OffsetToColor (x As Int) As Int";
 //BA.debugLineNum = 210;BA.debugLine="Dim Visible As Float = (mSideWidth + x) / mSideWi";
_visible = (float) ((_msidewidth+_x)/(double)_msidewidth);
 //BA.debugLineNum = 211;BA.debugLine="Return xui.Color_ARGB(100 * Visible, 0, 0, 0)";
if (true) return _xui.Color_ARGB((int) (100*_visible),(int) (0),(int) (0),(int) (0));
 //BA.debugLineNum = 212;BA.debugLine="End Sub";
return 0;
}
public String  _resize(int _width,int _height) throws Exception{
 //BA.debugLineNum = 235;BA.debugLine="Public Sub Resize(Width As Int, Height As Int)";
 //BA.debugLineNum = 236;BA.debugLine="If IsOpen Then ChangeOffset(-mSideWidth, False, T";
if (_isopen) { 
_changeoffset((float) (-_msidewidth),__c.False,__c.True);};
 //BA.debugLineNum = 237;BA.debugLine="mBasePanel.SetLayoutAnimated(0, 0, 0, Width, Heig";
_mbasepanel.SetLayoutAnimated((int) (0),(int) (0),(int) (0),_width,_height);
 //BA.debugLineNum = 238;BA.debugLine="mLeftPanel.SetLayoutAnimated(0, mLeftPanel.Left,";
_mleftpanel.SetLayoutAnimated((int) (0),_mleftpanel.getLeft(),(int) (0),_mleftpanel.getWidth(),_mbasepanel.getHeight());
 //BA.debugLineNum = 239;BA.debugLine="mDarkPanel.SetLayoutAnimated(0, 0, 0, Width, Heig";
_mdarkpanel.SetLayoutAnimated((int) (0),(int) (0),(int) (0),_width,_height);
 //BA.debugLineNum = 240;BA.debugLine="mCenterPanel.SetLayoutAnimated(0, 0, 0, Width, He";
_mcenterpanel.SetLayoutAnimated((int) (0),(int) (0),(int) (0),_width,_height);
 //BA.debugLineNum = 241;BA.debugLine="End Sub";
return "";
}
public String  _setgestureenabled(boolean _b) throws Exception{
 //BA.debugLineNum = 247;BA.debugLine="Public Sub setGestureEnabled (b As Boolean)";
 //BA.debugLineNum = 248;BA.debugLine="mEnabled = b";
_menabled = _b;
 //BA.debugLineNum = 249;BA.debugLine="End Sub";
return "";
}
public String  _setleftopen(boolean _b) throws Exception{
float _x = 0f;
 //BA.debugLineNum = 218;BA.debugLine="Public Sub setLeftOpen (b As Boolean)";
 //BA.debugLineNum = 219;BA.debugLine="If b = IsOpen Then";
if (_b==_isopen) { 
 //BA.debugLineNum = 220;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 222;BA.debugLine="Dim x As Float";
_x = 0f;
 //BA.debugLineNum = 223;BA.debugLine="If b Then x = 0 Else x = -mSideWidth";
if (_b) { 
_x = (float) (0);}
else {
_x = (float) (-_msidewidth);};
 //BA.debugLineNum = 224;BA.debugLine="ChangeOffset(x, False, False)";
_changeoffset(_x,__c.False,__c.False);
 //BA.debugLineNum = 225;BA.debugLine="End Sub";
return "";
}
public Object callSub(String sub, Object sender, Object[] args) throws Exception {
BA.senderHolder.set(sender);
return BA.SubDelegator.SubNotFound;
}
}
