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
views.get("imgsitio3").vw.setWidth((int)(Double.parseDouble(_w)));
views.get("imgsitio4").vw.setWidth((int)(Double.parseDouble(_w)));
views.get("imgsitio5").vw.setWidth((int)(Double.parseDouble(_w)));
views.get("imgsitio1").vw.setLeft((int)(Double.parseDouble(_gap)));
views.get("imgsitio2").vw.setLeft((int)((views.get("imgsitio1").vw.getLeft() + views.get("imgsitio1").vw.getWidth())+Double.parseDouble(_gap)));
views.get("imgsitio3").vw.setLeft((int)((views.get("imgsitio2").vw.getLeft() + views.get("imgsitio2").vw.getWidth())+Double.parseDouble(_gap)));
views.get("imgsitio4").vw.setLeft((int)((views.get("imgsitio3").vw.getLeft() + views.get("imgsitio3").vw.getWidth())+Double.parseDouble(_gap)));
views.get("imgsitio5").vw.setLeft((int)((views.get("imgsitio4").vw.getLeft() + views.get("imgsitio4").vw.getWidth())+Double.parseDouble(_gap)));

}
}