package appear.pnud.preservamos.designerscripts;
import anywheresoftware.b4a.objects.TextViewWrapper;
import anywheresoftware.b4a.objects.ImageViewWrapper;
import anywheresoftware.b4a.BA;


public class LS_reporte_habitat_rio_q2p4{

public static void LS_general(java.util.LinkedHashMap<String, anywheresoftware.b4a.keywords.LayoutBuilder.ViewWrapperAndAnchor> views, int width, int height, float scale) {
anywheresoftware.b4a.keywords.LayoutBuilder.setScaleRate(0.3);
views.get("pnlcrums").vw.setLeft((int)((50d / 100 * width)-(views.get("pnlcrums").vw.getWidth())/2d));
views.get("logopreservamoscheck").vw.setLeft((int)((50d / 100 * width) - (views.get("logopreservamoscheck").vw.getWidth() / 2)));
views.get("imgq2_4_si").vw.setLeft((int)((50d / 100 * width)-((views.get("imgq2_4_si").vw.getWidth())/2d)-(5d * scale) - (views.get("imgq2_4_si").vw.getWidth() / 2)));
views.get("imgq2_4_no").vw.setLeft((int)((50d / 100 * width)+((views.get("imgq2_4_no").vw.getWidth())/2d)+(5d * scale) - (views.get("imgq2_4_no").vw.getWidth() / 2)));
views.get("lblq2_4_a").vw.setLeft((int)((views.get("imgq2_4_si").vw.getLeft() + views.get("imgq2_4_si").vw.getWidth()/2) - (views.get("lblq2_4_a").vw.getWidth() / 2)));
views.get("lblq2_4_b").vw.setLeft((int)((views.get("imgq2_4_no").vw.getLeft() + views.get("imgq2_4_no").vw.getWidth()/2) - (views.get("lblq2_4_b").vw.getWidth() / 2)));

}
}