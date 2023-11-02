package appear.pnud.preservamos.designerscripts;
import anywheresoftware.b4a.objects.TextViewWrapper;
import anywheresoftware.b4a.objects.ImageViewWrapper;
import anywheresoftware.b4a.BA;


public class LS_laychecklists_home{

public static void LS_general(anywheresoftware.b4a.BA ba, android.view.View parent, anywheresoftware.b4a.keywords.LayoutValues lv, java.util.Map props,
java.util.Map<String, anywheresoftware.b4a.keywords.LayoutBuilder.ViewWrapperAndAnchor> views, int width, int height, float scale) throws Exception {
anywheresoftware.b4a.keywords.LayoutBuilder.setScaleRate(0.3);
views.get("butplantas").vw.setHeight((int)((15d / 100 * height)));
views.get("butplantas").vw.setWidth((int)((views.get("butplantas").vw.getHeight())));
views.get("butbugs").vw.setHeight((int)((views.get("butplantas").vw.getHeight())));
views.get("butbugs").vw.setWidth((int)((views.get("butplantas").vw.getWidth())));
views.get("butpeces").vw.setHeight((int)((views.get("butplantas").vw.getHeight())));
views.get("butpeces").vw.setWidth((int)((views.get("butplantas").vw.getWidth())));
views.get("butaves").vw.setHeight((int)((views.get("butplantas").vw.getHeight())));
views.get("butaves").vw.setWidth((int)((views.get("butplantas").vw.getWidth())));
views.get("butmammals").vw.setHeight((int)((views.get("butplantas").vw.getHeight())));
views.get("butmammals").vw.setWidth((int)((views.get("butplantas").vw.getWidth())));
views.get("butfungi").vw.setHeight((int)((views.get("butplantas").vw.getHeight())));
views.get("butfungi").vw.setWidth((int)((views.get("butplantas").vw.getWidth())));
views.get("lblhongos").vw.setLeft((int)((50d / 100 * width) - (views.get("lblhongos").vw.getWidth() / 2)));
views.get("butfungi").vw.setLeft((int)((50d / 100 * width) - (views.get("butfungi").vw.getWidth() / 2)));
views.get("lblaves").vw.setLeft((int)((50d / 100 * width) - (views.get("lblaves").vw.getWidth() / 2)));
views.get("butaves").vw.setLeft((int)((50d / 100 * width) - (views.get("butaves").vw.getWidth() / 2)));
views.get("lblplantas").vw.setLeft((int)((views.get("butfungi").vw.getLeft())-(10d * scale) - (views.get("lblplantas").vw.getWidth())));
views.get("butplantas").vw.setLeft((int)((views.get("lblplantas").vw.getLeft() + views.get("lblplantas").vw.getWidth()/2) - (views.get("butplantas").vw.getWidth() / 2)));
views.get("lblbugs").vw.setLeft((int)((views.get("butfungi").vw.getLeft() + views.get("butfungi").vw.getWidth())+(10d * scale)));
views.get("butbugs").vw.setLeft((int)((views.get("lblbugs").vw.getLeft() + views.get("lblbugs").vw.getWidth()/2) - (views.get("butbugs").vw.getWidth() / 2)));
//BA.debugLineNum = 23;BA.debugLine="lblPeces.HorizontalCenter = lblPlantas.HorizontalCenter"[laychecklists_home/General script]
views.get("lblpeces").vw.setLeft((int)((views.get("lblplantas").vw.getLeft() + views.get("lblplantas").vw.getWidth()/2) - (views.get("lblpeces").vw.getWidth() / 2)));
//BA.debugLineNum = 24;BA.debugLine="butPeces.HorizontalCenter = lblPlantas.HorizontalCenter"[laychecklists_home/General script]
views.get("butpeces").vw.setLeft((int)((views.get("lblplantas").vw.getLeft() + views.get("lblplantas").vw.getWidth()/2) - (views.get("butpeces").vw.getWidth() / 2)));
//BA.debugLineNum = 25;BA.debugLine="lblMammals.HorizontalCenter = lblBugs.HorizontalCenter"[laychecklists_home/General script]
views.get("lblmammals").vw.setLeft((int)((views.get("lblbugs").vw.getLeft() + views.get("lblbugs").vw.getWidth()/2) - (views.get("lblmammals").vw.getWidth() / 2)));
//BA.debugLineNum = 26;BA.debugLine="butMammals.HorizontalCenter = lblBugs.HorizontalCenter"[laychecklists_home/General script]
views.get("butmammals").vw.setLeft((int)((views.get("lblbugs").vw.getLeft() + views.get("lblbugs").vw.getWidth()/2) - (views.get("butmammals").vw.getWidth() / 2)));
//BA.debugLineNum = 27;BA.debugLine="butPlantas.Top = lblTitleChecklistHome.Bottom + 5dip"[laychecklists_home/General script]
views.get("butplantas").vw.setTop((int)((views.get("lbltitlechecklisthome").vw.getTop() + views.get("lbltitlechecklisthome").vw.getHeight())+(5d * scale)));
//BA.debugLineNum = 28;BA.debugLine="butFungi.Top = lblTitleChecklistHome.Bottom + 5dip"[laychecklists_home/General script]
views.get("butfungi").vw.setTop((int)((views.get("lbltitlechecklisthome").vw.getTop() + views.get("lbltitlechecklisthome").vw.getHeight())+(5d * scale)));
//BA.debugLineNum = 29;BA.debugLine="butBugs.Top = lblTitleChecklistHome.Bottom + 5dip"[laychecklists_home/General script]
views.get("butbugs").vw.setTop((int)((views.get("lbltitlechecklisthome").vw.getTop() + views.get("lbltitlechecklisthome").vw.getHeight())+(5d * scale)));
//BA.debugLineNum = 30;BA.debugLine="lblPlantas.Top = butPlantas.Bottom + 3dip"[laychecklists_home/General script]
views.get("lblplantas").vw.setTop((int)((views.get("butplantas").vw.getTop() + views.get("butplantas").vw.getHeight())+(3d * scale)));
//BA.debugLineNum = 31;BA.debugLine="lblHongos.Top = butPlantas.Bottom + 3dip"[laychecklists_home/General script]
views.get("lblhongos").vw.setTop((int)((views.get("butplantas").vw.getTop() + views.get("butplantas").vw.getHeight())+(3d * scale)));
//BA.debugLineNum = 32;BA.debugLine="lblBugs.Top = butPlantas.Bottom + 3dip"[laychecklists_home/General script]
views.get("lblbugs").vw.setTop((int)((views.get("butplantas").vw.getTop() + views.get("butplantas").vw.getHeight())+(3d * scale)));
//BA.debugLineNum = 33;BA.debugLine="butPeces.Top = lblPlantas.Bottom + 5dip"[laychecklists_home/General script]
views.get("butpeces").vw.setTop((int)((views.get("lblplantas").vw.getTop() + views.get("lblplantas").vw.getHeight())+(5d * scale)));
//BA.debugLineNum = 34;BA.debugLine="butAves.Top = lblPlantas.Bottom + 5dip"[laychecklists_home/General script]
views.get("butaves").vw.setTop((int)((views.get("lblplantas").vw.getTop() + views.get("lblplantas").vw.getHeight())+(5d * scale)));
//BA.debugLineNum = 35;BA.debugLine="butMammals.Top = lblPlantas.Bottom + 5dip"[laychecklists_home/General script]
views.get("butmammals").vw.setTop((int)((views.get("lblplantas").vw.getTop() + views.get("lblplantas").vw.getHeight())+(5d * scale)));
//BA.debugLineNum = 36;BA.debugLine="lblPeces.Top = butPeces.Bottom + 3dip"[laychecklists_home/General script]
views.get("lblpeces").vw.setTop((int)((views.get("butpeces").vw.getTop() + views.get("butpeces").vw.getHeight())+(3d * scale)));
//BA.debugLineNum = 37;BA.debugLine="lblAves.Top = butPeces.Bottom + 3dip"[laychecklists_home/General script]
views.get("lblaves").vw.setTop((int)((views.get("butpeces").vw.getTop() + views.get("butpeces").vw.getHeight())+(3d * scale)));
//BA.debugLineNum = 38;BA.debugLine="lblMammals.Top = butPeces.Bottom + 3dip"[laychecklists_home/General script]
views.get("lblmammals").vw.setTop((int)((views.get("butpeces").vw.getTop() + views.get("butpeces").vw.getHeight())+(3d * scale)));

}
}