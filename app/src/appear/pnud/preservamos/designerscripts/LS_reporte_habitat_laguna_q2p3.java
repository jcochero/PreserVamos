package appear.pnud.preservamos.designerscripts;
import anywheresoftware.b4a.objects.TextViewWrapper;
import anywheresoftware.b4a.objects.ImageViewWrapper;
import anywheresoftware.b4a.BA;


public class LS_reporte_habitat_laguna_q2p3{

public static void LS_general(java.util.LinkedHashMap<String, anywheresoftware.b4a.keywords.LayoutBuilder.ViewWrapperAndAnchor> views, int width, int height, float scale) {
anywheresoftware.b4a.keywords.LayoutBuilder.setScaleRate(0.3);
views.get("pnlcrums").vw.setLeft((int)((50d / 100 * width)-(views.get("pnlcrums").vw.getWidth())/2d));
views.get("logopreservamoscheck").vw.setLeft((int)((50d / 100 * width) - (views.get("logopreservamoscheck").vw.getWidth() / 2)));
views.get("imgq2_3_si").vw.setLeft((int)((50d / 100 * width)-((views.get("imgq2_3_si").vw.getWidth())/2d)-(5d * scale) - (views.get("imgq2_3_si").vw.getWidth() / 2)));
views.get("imgq2_3_no").vw.setLeft((int)((50d / 100 * width)+((views.get("imgq2_3_no").vw.getWidth())/2d)+(5d * scale) - (views.get("imgq2_3_no").vw.getWidth() / 2)));
views.get("lblq2_3_si").vw.setLeft((int)((views.get("imgq2_3_si").vw.getLeft() + views.get("imgq2_3_si").vw.getWidth()/2) - (views.get("lblq2_3_si").vw.getWidth() / 2)));
views.get("lblq2_3_no").vw.setLeft((int)((views.get("imgq2_3_no").vw.getLeft() + views.get("imgq2_3_no").vw.getWidth()/2) - (views.get("lblq2_3_no").vw.getWidth() / 2)));

}
}