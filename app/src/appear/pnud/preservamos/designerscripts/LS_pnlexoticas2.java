package appear.pnud.preservamos.designerscripts;
import anywheresoftware.b4a.objects.TextViewWrapper;
import anywheresoftware.b4a.objects.ImageViewWrapper;
import anywheresoftware.b4a.BA;


public class LS_pnlexoticas2{

public static void LS_general(java.util.LinkedHashMap<String, anywheresoftware.b4a.keywords.LayoutBuilder.ViewWrapperAndAnchor> views, int width, int height, float scale) {
anywheresoftware.b4a.keywords.LayoutBuilder.setScaleRate(0.3);
views.get("imgq6_3_a").vw.setLeft((int)((50d / 100 * width)-((views.get("imgq6_3_a").vw.getWidth())/2d)-(5d * scale) - (views.get("imgq6_3_a").vw.getWidth() / 2)));
views.get("imgq6_3_c").vw.setLeft((int)((50d / 100 * width)-((views.get("imgq6_3_a").vw.getWidth())/2d)-(5d * scale) - (views.get("imgq6_3_c").vw.getWidth() / 2)));
views.get("imgq6_3_b").vw.setLeft((int)((50d / 100 * width)+((views.get("imgq6_3_a").vw.getWidth())/2d)+(5d * scale) - (views.get("imgq6_3_b").vw.getWidth() / 2)));
views.get("imgq6_3_d").vw.setLeft((int)((50d / 100 * width)+((views.get("imgq6_3_a").vw.getWidth())/2d)+(5d * scale) - (views.get("imgq6_3_d").vw.getWidth() / 2)));
views.get("lblq6_3_a").vw.setLeft((int)((views.get("imgq6_3_a").vw.getLeft() + views.get("imgq6_3_a").vw.getWidth()/2) - (views.get("lblq6_3_a").vw.getWidth() / 2)));
views.get("lblq6_3_c").vw.setLeft((int)((views.get("imgq6_3_a").vw.getLeft() + views.get("imgq6_3_a").vw.getWidth()/2) - (views.get("lblq6_3_c").vw.getWidth() / 2)));
views.get("lblq6_3_b").vw.setLeft((int)((views.get("imgq6_3_b").vw.getLeft() + views.get("imgq6_3_b").vw.getWidth()/2) - (views.get("lblq6_3_b").vw.getWidth() / 2)));
views.get("lblq6_3_d").vw.setLeft((int)((views.get("imgq6_3_d").vw.getLeft() + views.get("imgq6_3_d").vw.getWidth()/2) - (views.get("lblq6_3_d").vw.getWidth() / 2)));

}
}