package appear.pnud.preservamos.designerscripts;
import anywheresoftware.b4a.objects.TextViewWrapper;
import anywheresoftware.b4a.objects.ImageViewWrapper;
import anywheresoftware.b4a.BA;


public class LS_pnlanimales{

public static void LS_general(java.util.LinkedHashMap<String, anywheresoftware.b4a.keywords.LayoutBuilder.ViewWrapperAndAnchor> views, int width, int height, float scale) {
anywheresoftware.b4a.keywords.LayoutBuilder.setScaleRate(0.3);
views.get("imgq4_a").vw.setLeft((int)((50d / 100 * width)-((views.get("imgq4_a").vw.getWidth())/2d)-(5d * scale) - (views.get("imgq4_a").vw.getWidth() / 2)));
views.get("imgq4_c").vw.setLeft((int)((50d / 100 * width)-((views.get("imgq4_a").vw.getWidth())/2d)-(5d * scale) - (views.get("imgq4_c").vw.getWidth() / 2)));
views.get("imgq4_e").vw.setLeft((int)((50d / 100 * width)-((views.get("imgq4_a").vw.getWidth())/2d)-(5d * scale) - (views.get("imgq4_e").vw.getWidth() / 2)));
views.get("imgq4_b").vw.setLeft((int)((50d / 100 * width)+((views.get("imgq4_a").vw.getWidth())/2d)+(5d * scale) - (views.get("imgq4_b").vw.getWidth() / 2)));
views.get("imgq4_d").vw.setLeft((int)((50d / 100 * width)+((views.get("imgq4_a").vw.getWidth())/2d)+(5d * scale) - (views.get("imgq4_d").vw.getWidth() / 2)));
views.get("imgq4_f").vw.setLeft((int)((50d / 100 * width)+((views.get("imgq4_a").vw.getWidth())/2d)+(5d * scale) - (views.get("imgq4_f").vw.getWidth() / 2)));
views.get("lblq4_a").vw.setLeft((int)((views.get("imgq4_a").vw.getLeft() + views.get("imgq4_a").vw.getWidth()/2) - (views.get("lblq4_a").vw.getWidth() / 2)));
views.get("lblq4_c").vw.setLeft((int)((views.get("imgq4_a").vw.getLeft() + views.get("imgq4_a").vw.getWidth()/2) - (views.get("lblq4_c").vw.getWidth() / 2)));
views.get("lblq4_e").vw.setLeft((int)((views.get("imgq4_a").vw.getLeft() + views.get("imgq4_a").vw.getWidth()/2) - (views.get("lblq4_e").vw.getWidth() / 2)));
views.get("lblq4_b").vw.setLeft((int)((views.get("imgq4_b").vw.getLeft() + views.get("imgq4_b").vw.getWidth()/2) - (views.get("lblq4_b").vw.getWidth() / 2)));
views.get("lblq4_d").vw.setLeft((int)((views.get("imgq4_b").vw.getLeft() + views.get("imgq4_b").vw.getWidth()/2) - (views.get("lblq4_d").vw.getWidth() / 2)));
views.get("lblq4_f").vw.setLeft((int)((views.get("imgq4_b").vw.getLeft() + views.get("imgq4_b").vw.getWidth()/2) - (views.get("lblq4_f").vw.getWidth() / 2)));
views.get("lblrojo_q4_a").vw.setLeft((int)((views.get("imgq4_a").vw.getLeft() + views.get("imgq4_a").vw.getWidth()/2) - (views.get("lblrojo_q4_a").vw.getWidth() / 2)));
//BA.debugLineNum = 15;BA.debugLine="lblRojo_Q4_b.HorizontalCenter = imgQ4_b.HorizontalCenter"[pnlAnimales/General script]
views.get("lblrojo_q4_b").vw.setLeft((int)((views.get("imgq4_b").vw.getLeft() + views.get("imgq4_b").vw.getWidth()/2) - (views.get("lblrojo_q4_b").vw.getWidth() / 2)));
//BA.debugLineNum = 16;BA.debugLine="lblRojo_Q4_c.HorizontalCenter = imgQ4_c.HorizontalCenter"[pnlAnimales/General script]
views.get("lblrojo_q4_c").vw.setLeft((int)((views.get("imgq4_c").vw.getLeft() + views.get("imgq4_c").vw.getWidth()/2) - (views.get("lblrojo_q4_c").vw.getWidth() / 2)));
//BA.debugLineNum = 17;BA.debugLine="lblRojo_Q4_d.HorizontalCenter = imgQ4_d.HorizontalCenter"[pnlAnimales/General script]
views.get("lblrojo_q4_d").vw.setLeft((int)((views.get("imgq4_d").vw.getLeft() + views.get("imgq4_d").vw.getWidth()/2) - (views.get("lblrojo_q4_d").vw.getWidth() / 2)));
//BA.debugLineNum = 18;BA.debugLine="lblRojo_Q4_e.HorizontalCenter = imgQ4_e.HorizontalCenter"[pnlAnimales/General script]
views.get("lblrojo_q4_e").vw.setLeft((int)((views.get("imgq4_e").vw.getLeft() + views.get("imgq4_e").vw.getWidth()/2) - (views.get("lblrojo_q4_e").vw.getWidth() / 2)));
//BA.debugLineNum = 19;BA.debugLine="lblRojo_Q4_f.HorizontalCenter = imgQ4_f.HorizontalCenter"[pnlAnimales/General script]
views.get("lblrojo_q4_f").vw.setLeft((int)((views.get("imgq4_f").vw.getLeft() + views.get("imgq4_f").vw.getWidth()/2) - (views.get("lblrojo_q4_f").vw.getWidth() / 2)));

}
}