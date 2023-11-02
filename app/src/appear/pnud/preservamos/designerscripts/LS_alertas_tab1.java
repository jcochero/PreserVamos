package appear.pnud.preservamos.designerscripts;
import anywheresoftware.b4a.objects.TextViewWrapper;
import anywheresoftware.b4a.objects.ImageViewWrapper;
import anywheresoftware.b4a.BA;


public class LS_alertas_tab1{

public static void LS_general(anywheresoftware.b4a.BA ba, android.view.View parent, anywheresoftware.b4a.keywords.LayoutValues lv, java.util.Map props,
java.util.Map<String, anywheresoftware.b4a.keywords.LayoutBuilder.ViewWrapperAndAnchor> views, int width, int height, float scale) throws Exception {
anywheresoftware.b4a.keywords.LayoutBuilder.setScaleRate(0.3);
views.get("imgicon").vw.setLeft((int)((50d / 100 * width) - (views.get("imgicon").vw.getWidth() / 2)));
views.get("imgicon").vw.setTop((int)((25d / 100 * height)));
views.get("lblinstrucciones").vw.setLeft((int)((50d / 100 * width) - (views.get("lblinstrucciones").vw.getWidth() / 2)));
//BA.debugLineNum = 5;BA.debugLine="lblInstrucciones.Top = imgIcon.Bottom + 10dip"[Alertas_Tab1/General script]
views.get("lblinstrucciones").vw.setTop((int)((views.get("imgicon").vw.getTop() + views.get("imgicon").vw.getHeight())+(10d * scale)));
//BA.debugLineNum = 6;BA.debugLine="lblInstrucciones2.HorizontalCenter = 50%x"[Alertas_Tab1/General script]
views.get("lblinstrucciones2").vw.setLeft((int)((50d / 100 * width) - (views.get("lblinstrucciones2").vw.getWidth() / 2)));
//BA.debugLineNum = 7;BA.debugLine="lblInstrucciones2.Top = lblInstrucciones.Bottom + 10dip"[Alertas_Tab1/General script]
views.get("lblinstrucciones2").vw.setTop((int)((views.get("lblinstrucciones").vw.getTop() + views.get("lblinstrucciones").vw.getHeight())+(10d * scale)));

}
}