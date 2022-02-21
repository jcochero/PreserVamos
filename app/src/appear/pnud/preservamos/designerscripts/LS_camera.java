package appear.pnud.preservamos.designerscripts;
import anywheresoftware.b4a.objects.TextViewWrapper;
import anywheresoftware.b4a.objects.ImageViewWrapper;
import anywheresoftware.b4a.BA;


public class LS_camera{

public static void LS_320x480_1(java.util.LinkedHashMap<String, anywheresoftware.b4a.keywords.LayoutBuilder.ViewWrapperAndAnchor> views, int width, int height, float scale) {
anywheresoftware.b4a.keywords.LayoutBuilder.setScaleRate(0.3);
((anywheresoftware.b4a.keywords.LayoutBuilder.DesignerTextSizeMethod)views.get("lblinstruccion").vw).setTextSize((float)(16d));

}
public static void LS_general(java.util.LinkedHashMap<String, anywheresoftware.b4a.keywords.LayoutBuilder.ViewWrapperAndAnchor> views, int width, int height, float scale) {
anywheresoftware.b4a.keywords.LayoutBuilder.setScaleRate(0.3);
String _gap="";
String _w="";
views.get("btntakepicture").vw.setLeft((int)((50d / 100 * width)-(views.get("btntakepicture").vw.getWidth())/2d));
views.get("btntakepicture").vw.setLeft((int)((50d / 100 * width)-(views.get("btntakepicture").vw.getWidth())/2d));
_gap = BA.NumberToString((5d * scale));
_w = BA.NumberToString(((100d / 100 * width)-6d*Double.parseDouble(_gap))/5d);
views.get("imgsitio1").vw.setWidth((int)(Double.parseDouble(_w)));
views.get("imgsitio2").vw.setWidth((int)(Double.parseDouble(_w)));
//BA.debugLineNum = 8;BA.debugLine="imgSitio3.Width = w"[camera/General script]
views.get("imgsitio3").vw.setWidth((int)(Double.parseDouble(_w)));
//BA.debugLineNum = 9;BA.debugLine="imgSitio4.Width = w"[camera/General script]
views.get("imgsitio4").vw.setWidth((int)(Double.parseDouble(_w)));
//BA.debugLineNum = 10;BA.debugLine="imgSitio5.Width = w"[camera/General script]
views.get("imgsitio5").vw.setWidth((int)(Double.parseDouble(_w)));
//BA.debugLineNum = 11;BA.debugLine="imgSitio1.Left = gap"[camera/General script]
views.get("imgsitio1").vw.setLeft((int)(Double.parseDouble(_gap)));
//BA.debugLineNum = 12;BA.debugLine="imgSitio2.Left = imgSitio1.Right + gap"[camera/General script]
views.get("imgsitio2").vw.setLeft((int)((views.get("imgsitio1").vw.getLeft() + views.get("imgsitio1").vw.getWidth())+Double.parseDouble(_gap)));
//BA.debugLineNum = 13;BA.debugLine="imgSitio3.Left = imgSitio2.Right + gap"[camera/General script]
views.get("imgsitio3").vw.setLeft((int)((views.get("imgsitio2").vw.getLeft() + views.get("imgsitio2").vw.getWidth())+Double.parseDouble(_gap)));
//BA.debugLineNum = 14;BA.debugLine="imgSitio4.Left = imgSitio3.Right + gap"[camera/General script]
views.get("imgsitio4").vw.setLeft((int)((views.get("imgsitio3").vw.getLeft() + views.get("imgsitio3").vw.getWidth())+Double.parseDouble(_gap)));
//BA.debugLineNum = 15;BA.debugLine="imgSitio5.Left = imgSitio4.Right + gap"[camera/General script]
views.get("imgsitio5").vw.setLeft((int)((views.get("imgsitio4").vw.getLeft() + views.get("imgsitio4").vw.getWidth())+Double.parseDouble(_gap)));

}
}