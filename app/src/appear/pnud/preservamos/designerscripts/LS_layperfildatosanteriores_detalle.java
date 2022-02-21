package appear.pnud.preservamos.designerscripts;
import anywheresoftware.b4a.objects.TextViewWrapper;
import anywheresoftware.b4a.objects.ImageViewWrapper;
import anywheresoftware.b4a.BA;


public class LS_layperfildatosanteriores_detalle{

public static void LS_general(java.util.LinkedHashMap<String, anywheresoftware.b4a.keywords.LayoutBuilder.ViewWrapperAndAnchor> views, int width, int height, float scale) {
anywheresoftware.b4a.keywords.LayoutBuilder.setScaleRate(0.3);
String _w="";
String _gap="";
views.get("lbllat").vw.setWidth((int)(((views.get("lblfecha").vw.getWidth())/2d)-(10d * scale)));
views.get("lbllng").vw.setWidth((int)(((views.get("lblfecha").vw.getWidth())/2d)-(10d * scale)));
views.get("lbllng").vw.setLeft((int)((views.get("lbllat").vw.getLeft() + views.get("lbllat").vw.getWidth())+(10d * scale)));
_w = BA.NumberToString(((100d / 100 * width)/3d)-(15d * scale));
views.get("label3").vw.setWidth((int)(Double.parseDouble(_w)));
views.get("label2").vw.setWidth((int)(Double.parseDouble(_w)));
views.get("label13").vw.setWidth((int)(Double.parseDouble(_w)));
_gap = BA.NumberToString((10d * scale));
views.get("label3").vw.setLeft((int)(Double.parseDouble(_gap)));
views.get("label2").vw.setLeft((int)((views.get("label3").vw.getLeft() + views.get("label3").vw.getWidth())+Double.parseDouble(_gap)));
views.get("label13").vw.setLeft((int)((views.get("label2").vw.getLeft() + views.get("label2").vw.getWidth())+Double.parseDouble(_gap)));
views.get("chkenviadobar").vw.setLeft((int)((views.get("label3").vw.getLeft() + views.get("label3").vw.getWidth()/2) - (views.get("chkenviadobar").vw.getWidth() / 2)));
views.get("chkonline").vw.setLeft((int)((views.get("label2").vw.getLeft() + views.get("label2").vw.getWidth()/2) - (views.get("chkonline").vw.getWidth() / 2)));
//BA.debugLineNum = 16;BA.debugLine="chkValidadoExpertos.HorizontalCenter = Label13.HorizontalCenter"[layPerfilDatosAnteriores_Detalle/General script]
views.get("chkvalidadoexpertos").vw.setLeft((int)((views.get("label13").vw.getLeft() + views.get("label13").vw.getWidth()/2) - (views.get("chkvalidadoexpertos").vw.getWidth() / 2)));

}
}