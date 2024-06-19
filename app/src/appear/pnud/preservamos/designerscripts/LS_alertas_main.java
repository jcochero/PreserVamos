package appear.pnud.preservamos.designerscripts;
import anywheresoftware.b4a.objects.TextViewWrapper;
import anywheresoftware.b4a.objects.ImageViewWrapper;
import anywheresoftware.b4a.BA;


public class LS_alertas_main{

public static void LS_800x1280_1(anywheresoftware.b4a.BA ba, android.view.View parent, anywheresoftware.b4a.keywords.LayoutValues lv, java.util.Map props,
java.util.Map<String, anywheresoftware.b4a.keywords.LayoutBuilder.ViewWrapperAndAnchor> views, int width, int height, float scale) throws Exception {
anywheresoftware.b4a.keywords.LayoutBuilder.setScaleRate(0.3);
//BA.debugLineNum = 2;BA.debugLine="imgInstrucciones.Height = 75%y"[Alertas_Main/800x1280,scale=1]
views.get("imginstrucciones").vw.setHeight((int)((75d / 100 * height)));
//BA.debugLineNum = 3;BA.debugLine="btnInfoCianosemaforo.Top= imgInstrucciones.Bottom + 10dip"[Alertas_Main/800x1280,scale=1]
views.get("btninfocianosemaforo").vw.setTop((int)((views.get("imginstrucciones").vw.getTop() + views.get("imginstrucciones").vw.getHeight())+(10d * scale)));

}
public static void LS_general(anywheresoftware.b4a.BA ba, android.view.View parent, anywheresoftware.b4a.keywords.LayoutValues lv, java.util.Map props,
java.util.Map<String, anywheresoftware.b4a.keywords.LayoutBuilder.ViewWrapperAndAnchor> views, int width, int height, float scale) throws Exception {
anywheresoftware.b4a.keywords.LayoutBuilder.setScaleRate(0.3);
views.get("lblcirculopos2").vw.setLeft((int)((50d / 100 * width) - (views.get("lblcirculopos2").vw.getWidth() / 2)));
//BA.debugLineNum = 3;BA.debugLine="lblCirculoPos1.Left = lblCirculoPos2.Left - lblCirculoPos1.Width - 5dip"[Alertas_Main/General script]
views.get("lblcirculopos1").vw.setLeft((int)((views.get("lblcirculopos2").vw.getLeft())-(views.get("lblcirculopos1").vw.getWidth())-(5d * scale)));
//BA.debugLineNum = 4;BA.debugLine="lblCirculoPos3.Left = lblCirculoPos2.Left + lblCirculoPos3.Width + 5dip"[Alertas_Main/General script]
views.get("lblcirculopos3").vw.setLeft((int)((views.get("lblcirculopos2").vw.getLeft())+(views.get("lblcirculopos3").vw.getWidth())+(5d * scale)));
//BA.debugLineNum = 5;BA.debugLine="imgIconAlerta.HorizontalCenter = 50%x"[Alertas_Main/General script]
views.get("imgiconalerta").vw.setLeft((int)((50d / 100 * width) - (views.get("imgiconalerta").vw.getWidth() / 2)));

}
}