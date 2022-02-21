package appear.pnud.preservamos.designerscripts;
import anywheresoftware.b4a.objects.TextViewWrapper;
import anywheresoftware.b4a.objects.ImageViewWrapper;
import anywheresoftware.b4a.BA;


public class LS_pnlexoticas{

public static void LS_general(java.util.LinkedHashMap<String, anywheresoftware.b4a.keywords.LayoutBuilder.ViewWrapperAndAnchor> views, int width, int height, float scale) {
anywheresoftware.b4a.keywords.LayoutBuilder.setScaleRate(0.3);
views.get("imgq6_1_a").vw.setLeft((int)((50d / 100 * width)-((views.get("imgq6_1_a").vw.getWidth())/2d)-(5d * scale) - (views.get("imgq6_1_a").vw.getWidth() / 2)));
views.get("imgq6_1_c").vw.setLeft((int)((50d / 100 * width)-((views.get("imgq6_1_a").vw.getWidth())/2d)-(5d * scale) - (views.get("imgq6_1_c").vw.getWidth() / 2)));
views.get("imgq6_1_b").vw.setLeft((int)((50d / 100 * width)+((views.get("imgq6_1_a").vw.getWidth())/2d)+(5d * scale) - (views.get("imgq6_1_b").vw.getWidth() / 2)));
views.get("imgq6_1_d").vw.setLeft((int)((50d / 100 * width)+((views.get("imgq6_1_a").vw.getWidth())/2d)+(5d * scale) - (views.get("imgq6_1_d").vw.getWidth() / 2)));
views.get("lblq6_a").vw.setLeft((int)((views.get("imgq6_1_a").vw.getLeft() + views.get("imgq6_1_a").vw.getWidth()/2) - (views.get("lblq6_a").vw.getWidth() / 2)));
views.get("lblq6_c").vw.setLeft((int)((views.get("imgq6_1_a").vw.getLeft() + views.get("imgq6_1_a").vw.getWidth()/2) - (views.get("lblq6_c").vw.getWidth() / 2)));
views.get("lblq6_b").vw.setLeft((int)((views.get("imgq6_1_b").vw.getLeft() + views.get("imgq6_1_b").vw.getWidth()/2) - (views.get("lblq6_b").vw.getWidth() / 2)));
views.get("lblq6_d").vw.setLeft((int)((views.get("imgq6_1_d").vw.getLeft() + views.get("imgq6_1_d").vw.getWidth()/2) - (views.get("lblq6_d").vw.getWidth() / 2)));

}
}