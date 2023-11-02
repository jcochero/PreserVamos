package appear.pnud.preservamos.designerscripts;
import anywheresoftware.b4a.objects.TextViewWrapper;
import anywheresoftware.b4a.objects.ImageViewWrapper;
import anywheresoftware.b4a.BA;


public class LS_reporte_habitat_laguna_q7{

public static void LS_general(anywheresoftware.b4a.BA ba, android.view.View parent, anywheresoftware.b4a.keywords.LayoutValues lv, java.util.Map props,
java.util.Map<String, anywheresoftware.b4a.keywords.LayoutBuilder.ViewWrapperAndAnchor> views, int width, int height, float scale) throws Exception {
anywheresoftware.b4a.keywords.LayoutBuilder.setScaleRate(0.3);
views.get("pnlcrums").vw.setLeft((int)((50d / 100 * width)-(views.get("pnlcrums").vw.getWidth())/2d));
views.get("logopreservamoscheck").vw.setLeft((int)((50d / 100 * width) - (views.get("logopreservamoscheck").vw.getWidth() / 2)));
views.get("imgq7_a").vw.setLeft((int)((50d / 100 * width)-((views.get("imgq7_a").vw.getWidth())/2d)-(5d * scale) - (views.get("imgq7_a").vw.getWidth() / 2)));
views.get("imgq7_c").vw.setLeft((int)((50d / 100 * width)-((views.get("imgq7_a").vw.getWidth())/2d)-(5d * scale) - (views.get("imgq7_c").vw.getWidth() / 2)));
views.get("imgq7_b").vw.setLeft((int)((50d / 100 * width)+((views.get("imgq7_a").vw.getWidth())/2d)+(5d * scale) - (views.get("imgq7_b").vw.getWidth() / 2)));
views.get("imgq7_d").vw.setLeft((int)((50d / 100 * width)+((views.get("imgq7_a").vw.getWidth())/2d)+(5d * scale) - (views.get("imgq7_d").vw.getWidth() / 2)));
views.get("lblq7_a").vw.setLeft((int)((views.get("imgq7_a").vw.getLeft() + views.get("imgq7_a").vw.getWidth()/2) - (views.get("lblq7_a").vw.getWidth() / 2)));
views.get("lblq7_c").vw.setLeft((int)((views.get("imgq7_c").vw.getLeft() + views.get("imgq7_c").vw.getWidth()/2) - (views.get("lblq7_c").vw.getWidth() / 2)));
views.get("lblq7_b").vw.setLeft((int)((views.get("imgq7_b").vw.getLeft() + views.get("imgq7_b").vw.getWidth()/2) - (views.get("lblq7_b").vw.getWidth() / 2)));
views.get("lblq7_d").vw.setLeft((int)((views.get("imgq7_d").vw.getLeft() + views.get("imgq7_d").vw.getWidth()/2) - (views.get("lblq7_d").vw.getWidth() / 2)));
views.get("lblrojo_q7_a").vw.setLeft((int)((views.get("imgq7_a").vw.getLeft() + views.get("imgq7_a").vw.getWidth()/2) - (views.get("lblrojo_q7_a").vw.getWidth() / 2)));
views.get("lblrojo_q7_b").vw.setLeft((int)((views.get("imgq7_b").vw.getLeft() + views.get("imgq7_b").vw.getWidth()/2) - (views.get("lblrojo_q7_b").vw.getWidth() / 2)));
views.get("lblrojo_q7_c").vw.setLeft((int)((views.get("imgq7_c").vw.getLeft() + views.get("imgq7_c").vw.getWidth()/2) - (views.get("lblrojo_q7_c").vw.getWidth() / 2)));
views.get("lblrojo_q7_d").vw.setLeft((int)((views.get("imgq7_d").vw.getLeft() + views.get("imgq7_d").vw.getWidth()/2) - (views.get("lblrojo_q7_d").vw.getWidth() / 2)));

}
}