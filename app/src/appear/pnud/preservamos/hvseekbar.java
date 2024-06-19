package appear.pnud.preservamos;


import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.B4AClass;
import anywheresoftware.b4a.BALayout;
import anywheresoftware.b4a.debug.*;

public class hvseekbar extends B4AClass.ImplB4AClass implements BA.SubDelegator{
    private static java.util.HashMap<String, java.lang.reflect.Method> htSubs;
    private void innerInitialize(BA _ba) throws Exception {
        if (ba == null) {
            ba = new BA(_ba, this, htSubs, "appear.pnud.preservamos.hvseekbar");
            if (htSubs == null) {
                ba.loadHtSubs(this.getClass());
                htSubs = ba.htSubs;
            }
            
        }
        if (BA.isShellModeRuntimeCheck(ba)) 
			   this.getClass().getMethod("_class_globals", appear.pnud.preservamos.hvseekbar.class).invoke(this, new Object[] {null});
        else
            ba.raiseEvent2(null, true, "class_globals", false);
    }

 public anywheresoftware.b4a.keywords.Common __c = null;
public Object _vmodule = null;
public anywheresoftware.b4a.objects.ActivityWrapper _destobject = null;
public anywheresoftware.b4a.objects.PanelWrapper _panelbase = null;
public anywheresoftware.b4a.objects.PanelWrapper _paneldraw = null;
public anywheresoftware.b4a.objects.PanelWrapper _paneltouch = null;
public String _barname = "";
public anywheresoftware.b4a.objects.LabelWrapper _cursor = null;
public int _linewidth = 0;
public int _cornerradius = 0;
public String _orient = "";
public int _deltax = 0;
public int _deltay = 0;
public anywheresoftware.b4a.objects.drawable.CanvasWrapper _canvasseek = null;
public int _linecol = 0;
public int _valuelinecol = 0;
public int _cursorcol = 0;
public int _cursorprescol = 0;
public int _vmax = 0;
public int _vmin = 0;
public int _vini = 0;
public int _cursortop = 0;
public int _cursorleft = 0;
public int _cursorwidth = 0;
public int _cursorheight = 0;
public boolean _colorsset = false;
public boolean _shapeset = false;
public boolean _valuesset = false;
public int _x0line = 0;
public int _y0line = 0;
public int _x1line = 0;
public int _y1line = 0;
public int _linelenght = 0;
public int _backcol = 0;
public int _seekvalue = 0;
public anywheresoftware.b4a.objects.drawable.ColorDrawable _cdcursor = null;
public anywheresoftware.b4a.objects.drawable.ColorDrawable _cdcursorpressed = null;
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
public String  _backcolor(int _c) throws Exception{
 //BA.debugLineNum = 328;BA.debugLine="Sub BackColor (c As Int)";
 //BA.debugLineNum = 329;BA.debugLine="panelBase.Color = c";
_panelbase.setColor(_c);
 //BA.debugLineNum = 330;BA.debugLine="End Sub";
return "";
}
public String  _class_globals() throws Exception{
 //BA.debugLineNum = 38;BA.debugLine="Private Sub Class_Globals";
 //BA.debugLineNum = 40;BA.debugLine="Private vModule As Object";
_vmodule = new Object();
 //BA.debugLineNum = 41;BA.debugLine="Private destObject As Activity";
_destobject = new anywheresoftware.b4a.objects.ActivityWrapper();
 //BA.debugLineNum = 42;BA.debugLine="Private panelBase, panelDraw, panelTouch As Panel";
_panelbase = new anywheresoftware.b4a.objects.PanelWrapper();
_paneldraw = new anywheresoftware.b4a.objects.PanelWrapper();
_paneltouch = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 43;BA.debugLine="Private barName As String";
_barname = "";
 //BA.debugLineNum = 45;BA.debugLine="Private cursor As Label";
_cursor = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 47;BA.debugLine="Private lineWidth As Int";
_linewidth = 0;
 //BA.debugLineNum = 48;BA.debugLine="Private cornerRadius As Int";
_cornerradius = 0;
 //BA.debugLineNum = 49;BA.debugLine="Private orient As String";
_orient = "";
 //BA.debugLineNum = 50;BA.debugLine="Private deltaX, deltaY As Int";
_deltax = 0;
_deltay = 0;
 //BA.debugLineNum = 51;BA.debugLine="Private canvasSeek As Canvas";
_canvasseek = new anywheresoftware.b4a.objects.drawable.CanvasWrapper();
 //BA.debugLineNum = 52;BA.debugLine="Private lineCol, valueLineCol, cursorCol, cursorP";
_linecol = 0;
_valuelinecol = 0;
_cursorcol = 0;
_cursorprescol = 0;
 //BA.debugLineNum = 53;BA.debugLine="Private vMax, vMin, vIni As Int";
_vmax = 0;
_vmin = 0;
_vini = 0;
 //BA.debugLineNum = 54;BA.debugLine="Private cursorTop, cursorLeft, cursorWidth, curso";
_cursortop = 0;
_cursorleft = 0;
_cursorwidth = 0;
_cursorheight = 0;
 //BA.debugLineNum = 56;BA.debugLine="Private colorsSet As Boolean = False";
_colorsset = __c.False;
 //BA.debugLineNum = 57;BA.debugLine="Private shapeSet  As Boolean = False";
_shapeset = __c.False;
 //BA.debugLineNum = 58;BA.debugLine="Private valuesSet As Boolean = False";
_valuesset = __c.False;
 //BA.debugLineNum = 60;BA.debugLine="Private x0Line, y0Line, x1Line, y1Line As Int";
_x0line = 0;
_y0line = 0;
_x1line = 0;
_y1line = 0;
 //BA.debugLineNum = 61;BA.debugLine="Dim lineLenght As Int";
_linelenght = 0;
 //BA.debugLineNum = 62;BA.debugLine="Private backCol As Int";
_backcol = 0;
 //BA.debugLineNum = 63;BA.debugLine="Dim seekValue As Int";
_seekvalue = 0;
 //BA.debugLineNum = 65;BA.debugLine="Dim cdCursor, cdCursorPressed As ColorDrawable";
_cdcursor = new anywheresoftware.b4a.objects.drawable.ColorDrawable();
_cdcursorpressed = new anywheresoftware.b4a.objects.drawable.ColorDrawable();
 //BA.debugLineNum = 67;BA.debugLine="End Sub";
return "";
}
public String  _cursortype_rectangle() throws Exception{
 //BA.debugLineNum = 345;BA.debugLine="Sub cursorType_RECTANGLE As String";
 //BA.debugLineNum = 346;BA.debugLine="Return \"RECTANGLE\"";
if (true) return "RECTANGLE";
 //BA.debugLineNum = 347;BA.debugLine="End Sub";
return "";
}
public String  _cursortype_rounded() throws Exception{
 //BA.debugLineNum = 341;BA.debugLine="Sub cursorType_ROUNDED As String";
 //BA.debugLineNum = 342;BA.debugLine="Return \"ROUNDED\"";
if (true) return "ROUNDED";
 //BA.debugLineNum = 343;BA.debugLine="End Sub";
return "";
}
public String  _defaultvalues() throws Exception{
 //BA.debugLineNum = 242;BA.debugLine="Sub DefaultValues";
 //BA.debugLineNum = 244;BA.debugLine="If Not(shapeSet) Then 'default \"ROUNDED\" with 20d";
if (__c.Not(_shapeset)) { 
 //BA.debugLineNum = 246;BA.debugLine="cursorWidth    = 20dip		  '20dip diameter circle";
_cursorwidth = __c.DipToCurrent((int) (20));
 //BA.debugLineNum = 247;BA.debugLine="cursorHeight   = cursorWidth";
_cursorheight = _cursorwidth;
 //BA.debugLineNum = 248;BA.debugLine="cornerRadius   = cursorWidth/2";
_cornerradius = (int) (_cursorwidth/(double)2);
 //BA.debugLineNum = 249;BA.debugLine="lineWidth      = 3dip";
_linewidth = __c.DipToCurrent((int) (3));
 };
 //BA.debugLineNum = 253;BA.debugLine="If Not(colorsSet) Then";
if (__c.Not(_colorsset)) { 
 //BA.debugLineNum = 255;BA.debugLine="lineCol       = Colors.Gray";
_linecol = __c.Colors.Gray;
 //BA.debugLineNum = 256;BA.debugLine="valueLineCol  = Colors.Cyan";
_valuelinecol = __c.Colors.Cyan;
 //BA.debugLineNum = 257;BA.debugLine="cursorCol     = Colors.Cyan";
_cursorcol = __c.Colors.Cyan;
 //BA.debugLineNum = 258;BA.debugLine="cursorPresCol = lineCol";
_cursorprescol = _linecol;
 //BA.debugLineNum = 259;BA.debugLine="backCol       = Colors.Transparent";
_backcol = __c.Colors.Transparent;
 };
 //BA.debugLineNum = 263;BA.debugLine="If Not(valuesSet) Then";
if (__c.Not(_valuesset)) { 
 //BA.debugLineNum = 265;BA.debugLine="vMin = 0";
_vmin = (int) (0);
 //BA.debugLineNum = 266;BA.debugLine="vMax = 100";
_vmax = (int) (100);
 //BA.debugLineNum = 267;BA.debugLine="vIni = 0";
_vini = (int) (0);
 };
 //BA.debugLineNum = 271;BA.debugLine="End Sub";
return "";
}
public String  _enabled(boolean _e) throws Exception{
 //BA.debugLineNum = 332;BA.debugLine="Sub Enabled(e As Boolean)";
 //BA.debugLineNum = 333;BA.debugLine="panelBase.Enabled  = e";
_panelbase.setEnabled(_e);
 //BA.debugLineNum = 334;BA.debugLine="panelDraw.Enabled  = e";
_paneldraw.setEnabled(_e);
 //BA.debugLineNum = 335;BA.debugLine="panelTouch.Enabled = e";
_paneltouch.setEnabled(_e);
 //BA.debugLineNum = 336;BA.debugLine="End Sub";
return "";
}
public int  _getseekvalue() throws Exception{
 //BA.debugLineNum = 313;BA.debugLine="Sub getSeekValue As Int";
 //BA.debugLineNum = 315;BA.debugLine="Return seekValue";
if (true) return _seekvalue;
 //BA.debugLineNum = 317;BA.debugLine="End Sub";
return 0;
}
public String  _initialize(anywheresoftware.b4a.BA _ba,Object _module,Object _dest_object,String _eventname) throws Exception{
innerInitialize(_ba);
 //BA.debugLineNum = 70;BA.debugLine="Public Sub Initialize(Module As Object, dest_Objec";
 //BA.debugLineNum = 72;BA.debugLine="vModule	   = Module";
_vmodule = _module;
 //BA.debugLineNum = 73;BA.debugLine="destObject = dest_Object";
_destobject = (anywheresoftware.b4a.objects.ActivityWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.ActivityWrapper(), (anywheresoftware.b4a.BALayout)(_dest_object));
 //BA.debugLineNum = 74;BA.debugLine="barName    = Eventname";
_barname = _eventname;
 //BA.debugLineNum = 76;BA.debugLine="End Sub";
return "";
}
public String  _orientation_horizontal() throws Exception{
 //BA.debugLineNum = 353;BA.debugLine="Sub orientation_HORIZONTAL As String";
 //BA.debugLineNum = 354;BA.debugLine="Return \"HORIZONTAL\"";
if (true) return "HORIZONTAL";
 //BA.debugLineNum = 355;BA.debugLine="End Sub";
return "";
}
public String  _orientation_vertical() throws Exception{
 //BA.debugLineNum = 349;BA.debugLine="Sub orientation_VERTICAL As String";
 //BA.debugLineNum = 350;BA.debugLine="Return \"VERTICAL\"";
if (true) return "VERTICAL";
 //BA.debugLineNum = 351;BA.debugLine="End Sub";
return "";
}
public boolean  _paneltouch_touch(int _action,float _x,float _y) throws Exception{
int _auxx = 0;
int _auxy = 0;
int _distx = 0;
int _disty = 0;
 //BA.debugLineNum = 208;BA.debugLine="Private Sub panelTouch_Touch(Action As Int, X As F";
 //BA.debugLineNum = 210;BA.debugLine="Select Action";
switch (BA.switchObjectToInt(_action,_paneltouch.ACTION_DOWN,_paneltouch.ACTION_UP)) {
case 0: {
 //BA.debugLineNum = 213;BA.debugLine="cursor.Background = cdCursorPressed";
_cursor.setBackground((android.graphics.drawable.Drawable)(_cdcursorpressed.getObject()));
 break; }
case 1: {
 //BA.debugLineNum = 216;BA.debugLine="cursor.Background = cdCursor";
_cursor.setBackground((android.graphics.drawable.Drawable)(_cdcursor.getObject()));
 break; }
}
;
 //BA.debugLineNum = 221;BA.debugLine="Dim auxX As Int = X";
_auxx = (int) (_x);
 //BA.debugLineNum = 222;BA.debugLine="Dim auxY As Int = Y";
_auxy = (int) (_y);
 //BA.debugLineNum = 224;BA.debugLine="If X < x0Line Then auxX = x0Line";
if (_x<_x0line) { 
_auxx = _x0line;};
 //BA.debugLineNum = 225;BA.debugLine="If X > x1Line Then auxX = x1Line";
if (_x>_x1line) { 
_auxx = _x1line;};
 //BA.debugLineNum = 226;BA.debugLine="If Y < y1Line Then auxY = y1Line";
if (_y<_y1line) { 
_auxy = _y1line;};
 //BA.debugLineNum = 227;BA.debugLine="If Y > y0Line Then auxY = y0Line";
if (_y>_y0line) { 
_auxy = _y0line;};
 //BA.debugLineNum = 229;BA.debugLine="Dim distX As Int = auxX";
_distx = _auxx;
 //BA.debugLineNum = 230;BA.debugLine="Dim distY As Int = panelDraw.Height - auxY";
_disty = (int) (_paneldraw.getHeight()-_auxy);
 //BA.debugLineNum = 232;BA.debugLine="setPosition(distX, distY)";
_setposition(_distx,_disty);
 //BA.debugLineNum = 234;BA.debugLine="Return True";
if (true) return __c.True;
 //BA.debugLineNum = 236;BA.debugLine="End Sub";
return false;
}
public String  _setcolors(int _linecolor,int _valuelinecolor,int _cursor_color,int _cursorpressed_color,int _back_color) throws Exception{
 //BA.debugLineNum = 290;BA.debugLine="Sub SetColors(lineColor As Int, valueLineColor As";
 //BA.debugLineNum = 292;BA.debugLine="lineCol       = lineColor";
_linecol = _linecolor;
 //BA.debugLineNum = 293;BA.debugLine="valueLineCol  = valueLineColor";
_valuelinecol = _valuelinecolor;
 //BA.debugLineNum = 294;BA.debugLine="cursorCol     = cursor_Color";
_cursorcol = _cursor_color;
 //BA.debugLineNum = 295;BA.debugLine="backCol       = back_Color";
_backcol = _back_color;
 //BA.debugLineNum = 296;BA.debugLine="cursorPresCol = cursorPressed_Color";
_cursorprescol = _cursorpressed_color;
 //BA.debugLineNum = 298;BA.debugLine="colorsSet = True";
_colorsset = __c.True;
 //BA.debugLineNum = 300;BA.debugLine="End Sub";
return "";
}
public String  _setinitvalues(int _valuemin,int _valuemax,int _inivalue) throws Exception{
 //BA.debugLineNum = 302;BA.debugLine="Sub SetInitValues(valueMin As Int, valueMax As Int";
 //BA.debugLineNum = 304;BA.debugLine="vMin = valueMin";
_vmin = _valuemin;
 //BA.debugLineNum = 305;BA.debugLine="vMax = valueMax";
_vmax = _valuemax;
 //BA.debugLineNum = 306;BA.debugLine="vIni = iniValue";
_vini = _inivalue;
 //BA.debugLineNum = 308;BA.debugLine="valuesSet = True";
_valuesset = __c.True;
 //BA.debugLineNum = 310;BA.debugLine="End Sub";
return "";
}
public String  _setposition(int _dx,int _dy) throws Exception{
int _x2line = 0;
int _y2line = 0;
 //BA.debugLineNum = 163;BA.debugLine="Private Sub setPosition(dX As Int, dY As Int)";
 //BA.debugLineNum = 165;BA.debugLine="Dim x2Line, y2Line As Int";
_x2line = 0;
_y2line = 0;
 //BA.debugLineNum = 167;BA.debugLine="If orient = orientation_HORIZONTAL Then";
if ((_orient).equals(_orientation_horizontal())) { 
 //BA.debugLineNum = 169;BA.debugLine="x2Line = dX";
_x2line = _dx;
 //BA.debugLineNum = 170;BA.debugLine="y2Line = y0Line";
_y2line = _y0line;
 //BA.debugLineNum = 171;BA.debugLine="deltaX = x2Line - x0Line";
_deltax = (int) (_x2line-_x0line);
 //BA.debugLineNum = 172;BA.debugLine="deltaY = 0";
_deltay = (int) (0);
 }else {
 //BA.debugLineNum = 176;BA.debugLine="x2Line = x0Line";
_x2line = _x0line;
 //BA.debugLineNum = 177;BA.debugLine="y2Line = panelDraw.Height - dY";
_y2line = (int) (_paneldraw.getHeight()-_dy);
 //BA.debugLineNum = 178;BA.debugLine="deltaX = 0";
_deltax = (int) (0);
 //BA.debugLineNum = 179;BA.debugLine="deltaY = y0Line - y2Line";
_deltay = (int) (_y0line-_y2line);
 };
 //BA.debugLineNum = 184;BA.debugLine="canvasSeek.DrawLine(x0Line, y0Line, x1Line, y1Lin";
_canvasseek.DrawLine((float) (_x0line),(float) (_y0line),(float) (_x1line),(float) (_y1line),_linecol,(float) (_linewidth));
 //BA.debugLineNum = 185;BA.debugLine="canvasSeek.DrawLine(x0Line, y0Line, x2Line, y2Lin";
_canvasseek.DrawLine((float) (_x0line),(float) (_y0line),(float) (_x2line),(float) (_y2line),_valuelinecol,(float) (_linewidth));
 //BA.debugLineNum = 186;BA.debugLine="panelDraw.Invalidate";
_paneldraw.Invalidate();
 //BA.debugLineNum = 188;BA.debugLine="cursorLeft = x2Line - cursorWidth  / 2";
_cursorleft = (int) (_x2line-_cursorwidth/(double)2);
 //BA.debugLineNum = 189;BA.debugLine="cursorTop  = y2Line - cursorHeight / 2";
_cursortop = (int) (_y2line-_cursorheight/(double)2);
 //BA.debugLineNum = 191;BA.debugLine="cursor.SetLayout(cursorLeft, cursorTop, cursorWid";
_cursor.SetLayout(_cursorleft,_cursortop,_cursorwidth,_cursorheight);
 //BA.debugLineNum = 196;BA.debugLine="seekValue = vMin + Max(deltaX, deltaY)/lineLenght";
_seekvalue = (int) (_vmin+__c.Max(_deltax,_deltay)/(double)_linelenght*(_vmax-_vmin));
 //BA.debugLineNum = 198;BA.debugLine="If SubExists(vModule, barName & \"_ValueChanged\")";
if (__c.SubExists(ba,_vmodule,_barname+"_ValueChanged")) { 
 //BA.debugLineNum = 200;BA.debugLine="CallSub2(vModule,barName & \"_ValueChanged\",";
__c.CallSubNew2(ba,_vmodule,_barname+"_ValueChanged",(Object)(_seekvalue));
 };
 //BA.debugLineNum = 205;BA.debugLine="End Sub";
return "";
}
public String  _setshape(String _cursor_type,int _cursor_width,int _cursor_height,int _line_width) throws Exception{
 //BA.debugLineNum = 273;BA.debugLine="Sub SetShape(cursor_Type As String, cursor_Width A";
 //BA.debugLineNum = 275;BA.debugLine="cursorHeight = cursor_Height";
_cursorheight = _cursor_height;
 //BA.debugLineNum = 276;BA.debugLine="cursorWidth	 = cursor_Width";
_cursorwidth = _cursor_width;
 //BA.debugLineNum = 277;BA.debugLine="lineWidth    = line_Width";
_linewidth = _line_width;
 //BA.debugLineNum = 279;BA.debugLine="If cursor_Type = cursorType_ROUNDED Then";
if ((_cursor_type).equals(_cursortype_rounded())) { 
 //BA.debugLineNum = 280;BA.debugLine="cornerRadius = Min(cursorHeight, cursorWidth) *";
_cornerradius = (int) (__c.Min(_cursorheight,_cursorwidth)*.5);
 }else {
 //BA.debugLineNum = 282;BA.debugLine="cornerRadius = Min(cursorHeight, cursorWidth) *";
_cornerradius = (int) (__c.Min(_cursorheight,_cursorwidth)*.2);
 };
 //BA.debugLineNum = 285;BA.debugLine="shapeSet = True";
_shapeset = __c.True;
 //BA.debugLineNum = 288;BA.debugLine="End Sub";
return "";
}
public String  _showbar(int _barleft,int _bartop,int _barwidth,int _barheight,String _orientation) throws Exception{
int _xinivalue = 0;
int _yinivalue = 0;
anywheresoftware.b4a.objects.drawable.CanvasWrapper.RectWrapper _rect1 = null;
 //BA.debugLineNum = 79;BA.debugLine="Public Sub ShowBar(barLeft As Int, barTop As Int,";
 //BA.debugLineNum = 81;BA.debugLine="orient = orientation";
_orient = _orientation;
 //BA.debugLineNum = 83;BA.debugLine="Dim xIniValue, yIniValue As Int";
_xinivalue = 0;
_yinivalue = 0;
 //BA.debugLineNum = 85;BA.debugLine="DefaultValues";
_defaultvalues();
 //BA.debugLineNum = 87;BA.debugLine="If orient = orientation_HORIZONTAL Then";
if ((_orient).equals(_orientation_horizontal())) { 
 //BA.debugLineNum = 89;BA.debugLine="If barHeight < cursorHeight Then cursorHeight =";
if (_barheight<_cursorheight) { 
_cursorheight = _barheight;};
 //BA.debugLineNum = 91;BA.debugLine="x0Line = cursorWidth/2";
_x0line = (int) (_cursorwidth/(double)2);
 //BA.debugLineNum = 92;BA.debugLine="y0Line = barHeight/2";
_y0line = (int) (_barheight/(double)2);
 //BA.debugLineNum = 93;BA.debugLine="x1Line = barWidth - cursorWidth/2";
_x1line = (int) (_barwidth-_cursorwidth/(double)2);
 //BA.debugLineNum = 94;BA.debugLine="y1Line = y0Line";
_y1line = _y0line;
 //BA.debugLineNum = 96;BA.debugLine="lineLenght = x1Line - x0Line";
_linelenght = (int) (_x1line-_x0line);
 //BA.debugLineNum = 98;BA.debugLine="xIniValue = (x1Line - x0Line) * (vIni - vMin) /";
_xinivalue = (int) ((_x1line-_x0line)*(_vini-_vmin)/(double)(_vmax-_vmin));
 //BA.debugLineNum = 99;BA.debugLine="yIniValue = 0";
_yinivalue = (int) (0);
 //BA.debugLineNum = 101;BA.debugLine="cursorTop = (barHeight - cursorHeight) / 2";
_cursortop = (int) ((_barheight-_cursorheight)/(double)2);
 }else {
 //BA.debugLineNum = 106;BA.debugLine="If barWidth < cursorWidth Then cursorWidth = bar";
if (_barwidth<_cursorwidth) { 
_cursorwidth = _barwidth;};
 //BA.debugLineNum = 108;BA.debugLine="x0Line = barWidth / 2";
_x0line = (int) (_barwidth/(double)2);
 //BA.debugLineNum = 109;BA.debugLine="y0Line = barHeight - cursorHeight / 2";
_y0line = (int) (_barheight-_cursorheight/(double)2);
 //BA.debugLineNum = 110;BA.debugLine="x1Line = x0Line";
_x1line = _x0line;
 //BA.debugLineNum = 111;BA.debugLine="y1Line = cursorHeight / 2";
_y1line = (int) (_cursorheight/(double)2);
 //BA.debugLineNum = 113;BA.debugLine="lineLenght = y0Line - y1Line";
_linelenght = (int) (_y0line-_y1line);
 //BA.debugLineNum = 115;BA.debugLine="xIniValue = 0";
_xinivalue = (int) (0);
 //BA.debugLineNum = 116;BA.debugLine="yIniValue = (y0Line - y1Line) * (vIni - vMin)  /";
_yinivalue = (int) ((_y0line-_y1line)*(_vini-_vmin)/(double)(_vmax-_vmin));
 //BA.debugLineNum = 118;BA.debugLine="cursorTop = barHeight - yIniValue - cursorHeight";
_cursortop = (int) (_barheight-_yinivalue-_cursorheight);
 };
 //BA.debugLineNum = 124;BA.debugLine="panelBase.initialize(\"panelBase\")";
_panelbase.Initialize(ba,"panelBase");
 //BA.debugLineNum = 125;BA.debugLine="panelDraw.Initialize(\"panelDraw\")";
_paneldraw.Initialize(ba,"panelDraw");
 //BA.debugLineNum = 126;BA.debugLine="panelTouch.Initialize(\"panelTouch\")";
_paneltouch.Initialize(ba,"panelTouch");
 //BA.debugLineNum = 128;BA.debugLine="destObject.AddView(panelBase,  barLeft, barTop, b";
_destobject.AddView((android.view.View)(_panelbase.getObject()),_barleft,_bartop,_barwidth,_barheight);
 //BA.debugLineNum = 129;BA.debugLine="destObject.AddView(panelDraw,  barLeft, barTop, b";
_destobject.AddView((android.view.View)(_paneldraw.getObject()),_barleft,_bartop,_barwidth,_barheight);
 //BA.debugLineNum = 130;BA.debugLine="destObject.AddView(panelTouch, barLeft, barTop, b";
_destobject.AddView((android.view.View)(_paneltouch.getObject()),_barleft,_bartop,_barwidth,_barheight);
 //BA.debugLineNum = 132;BA.debugLine="panelBase.Elevation  = 0";
_panelbase.setElevation((float) (0));
 //BA.debugLineNum = 133;BA.debugLine="panelDraw.Elevation  = 2dip";
_paneldraw.setElevation((float) (__c.DipToCurrent((int) (2))));
 //BA.debugLineNum = 134;BA.debugLine="panelTouch.Elevation = 4dip";
_paneltouch.setElevation((float) (__c.DipToCurrent((int) (4))));
 //BA.debugLineNum = 136;BA.debugLine="panelBase.Color  = backCol";
_panelbase.setColor(_backcol);
 //BA.debugLineNum = 137;BA.debugLine="panelDraw.Color  = Colors.Transparent";
_paneldraw.setColor(__c.Colors.Transparent);
 //BA.debugLineNum = 138;BA.debugLine="panelTouch.Color = Colors.Transparent";
_paneltouch.setColor(__c.Colors.Transparent);
 //BA.debugLineNum = 140;BA.debugLine="cdCursor.Initialize(cursorCol, cornerRadius)";
_cdcursor.Initialize(_cursorcol,_cornerradius);
 //BA.debugLineNum = 141;BA.debugLine="cdCursorPressed.Initialize(cursorPresCol, cornerR";
_cdcursorpressed.Initialize(_cursorprescol,_cornerradius);
 //BA.debugLineNum = 143;BA.debugLine="cursor.Initialize(\"cursor\")";
_cursor.Initialize(ba,"cursor");
 //BA.debugLineNum = 144;BA.debugLine="cursor.Background = cdCursor";
_cursor.setBackground((android.graphics.drawable.Drawable)(_cdcursor.getObject()));
 //BA.debugLineNum = 146;BA.debugLine="cursorLeft = x0Line + xIniValue - cursorWidth / 2";
_cursorleft = (int) (_x0line+_xinivalue-_cursorwidth/(double)2);
 //BA.debugLineNum = 148;BA.debugLine="canvasSeek.Initialize(panelDraw)";
_canvasseek.Initialize((android.view.View)(_paneldraw.getObject()));
 //BA.debugLineNum = 150;BA.debugLine="Dim rect1 As Rect";
_rect1 = new anywheresoftware.b4a.objects.drawable.CanvasWrapper.RectWrapper();
 //BA.debugLineNum = 151;BA.debugLine="rect1.Initialize(0,0,panelBase.Width, panelBase.";
_rect1.Initialize((int) (0),(int) (0),_panelbase.getWidth(),_panelbase.getHeight());
 //BA.debugLineNum = 153;BA.debugLine="canvasSeek.DrawLine(x0Line, y0Line, x1Line,";
_canvasseek.DrawLine((float) (_x0line),(float) (_y0line),(float) (_x1line),(float) (_y1line),_linecol,(float) (_linewidth));
 //BA.debugLineNum = 154;BA.debugLine="canvasSeek.DrawLine(x0Line, y0Line, x0Line + xIni";
_canvasseek.DrawLine((float) (_x0line),(float) (_y0line),(float) (_x0line+_xinivalue),(float) (_y0line-_yinivalue),_valuelinecol,(float) (_linewidth));
 //BA.debugLineNum = 156;BA.debugLine="panelDraw.AddView(cursor, cursorLeft, cursorTop,";
_paneldraw.AddView((android.view.View)(_cursor.getObject()),_cursorleft,_cursortop,_cursorwidth,_cursorheight);
 //BA.debugLineNum = 158;BA.debugLine="seekValue = vIni";
_seekvalue = _vini;
 //BA.debugLineNum = 160;BA.debugLine="End Sub";
return "";
}
public String  _visible(boolean _v) throws Exception{
 //BA.debugLineNum = 322;BA.debugLine="Sub Visible (v As Boolean)";
 //BA.debugLineNum = 323;BA.debugLine="panelBase.Visible  = v";
_panelbase.setVisible(_v);
 //BA.debugLineNum = 324;BA.debugLine="panelDraw.Visible  = v";
_paneldraw.setVisible(_v);
 //BA.debugLineNum = 325;BA.debugLine="panelTouch.Visible = v";
_paneltouch.setVisible(_v);
 //BA.debugLineNum = 326;BA.debugLine="End Sub";
return "";
}
public Object callSub(String sub, Object sender, Object[] args) throws Exception {
BA.senderHolder.set(sender);
return BA.SubDelegator.SubNotFound;
}
}
