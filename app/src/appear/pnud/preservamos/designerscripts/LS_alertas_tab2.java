package appear.pnud.preservamos.designerscripts;
import anywheresoftware.b4a.objects.TextViewWrapper;
import anywheresoftware.b4a.objects.ImageViewWrapper;
import anywheresoftware.b4a.BA;


public class LS_alertas_tab2{

public static void LS_general(java.util.LinkedHashMap<String, anywheresoftware.b4a.keywords.LayoutBuilder.ViewWrapperAndAnchor> views, int width, int height, float scale) {
anywheresoftware.b4a.keywords.LayoutBuilder.setScaleRate(0.3);
views.get("imgicon").vw.setLeft((int)((50d / 100 * width) - (views.get("imgicon").vw.getWidth() / 2)));
views.get("imgicon").vw.setTop((int)((views.get("lblalertastitle").vw.getTop() + views.get("lblalertastitle").vw.getHeight())));
views.get("lbltitle_alerta_tab2").vw.setTop((int)((views.get("imgicon").vw.getTop() + views.get("imgicon").vw.getHeight())));
views.get("imgpeces").vw.setLeft((int)((50d / 100 * width)-((views.get("imgpeces").vw.getWidth())/2d)-(10d * scale) - (views.get("imgpeces").vw.getWidth() / 2)));
views.get("imgderrame").vw.setLeft((int)((50d / 100 * width)+((views.get("imgderrame").vw.getWidth())/2d)+(10d * scale) - (views.get("imgderrame").vw.getWidth() / 2)));
//BA.debugLineNum = 7;BA.debugLine="lblDerrame.HorizontalCenter = imgDerrame.HorizontalCenter"[Alertas_Tab2/General script]
views.get("lblderrame").vw.setLeft((int)((views.get("imgderrame").vw.getLeft() + views.get("imgderrame").vw.getWidth()/2) - (views.get("lblderrame").vw.getWidth() / 2)));
//BA.debugLineNum = 8;BA.debugLine="lblPeces.HorizontalCenter = imgPeces.HorizontalCenter"[Alertas_Tab2/General script]
views.get("lblpeces").vw.setLeft((int)((views.get("imgpeces").vw.getLeft() + views.get("imgpeces").vw.getWidth()/2) - (views.get("lblpeces").vw.getWidth() / 2)));
//BA.debugLineNum = 9;BA.debugLine="imgAlgas.HorizontalCenter = imgPeces.HorizontalCenter"[Alertas_Tab2/General script]
views.get("imgalgas").vw.setLeft((int)((views.get("imgpeces").vw.getLeft() + views.get("imgpeces").vw.getWidth()/2) - (views.get("imgalgas").vw.getWidth() / 2)));
//BA.debugLineNum = 10;BA.debugLine="lblAlgas.HorizontalCenter = lblPeces.HorizontalCenter"[Alertas_Tab2/General script]
views.get("lblalgas").vw.setLeft((int)((views.get("lblpeces").vw.getLeft() + views.get("lblpeces").vw.getWidth()/2) - (views.get("lblalgas").vw.getWidth() / 2)));
//BA.debugLineNum = 11;BA.debugLine="imgCaza.HorizontalCenter = imgDerrame.HorizontalCenter"[Alertas_Tab2/General script]
views.get("imgcaza").vw.setLeft((int)((views.get("imgderrame").vw.getLeft() + views.get("imgderrame").vw.getWidth()/2) - (views.get("imgcaza").vw.getWidth() / 2)));
//BA.debugLineNum = 12;BA.debugLine="lblCaza.HorizontalCenter = lblDerrame.HorizontalCenter"[Alertas_Tab2/General script]
views.get("lblcaza").vw.setLeft((int)((views.get("lblderrame").vw.getLeft() + views.get("lblderrame").vw.getWidth()/2) - (views.get("lblcaza").vw.getWidth() / 2)));
//BA.debugLineNum = 13;BA.debugLine="imgOtraAlerta.HorizontalCenter = imgAlgas.HorizontalCenter"[Alertas_Tab2/General script]
views.get("imgotraalerta").vw.setLeft((int)((views.get("imgalgas").vw.getLeft() + views.get("imgalgas").vw.getWidth()/2) - (views.get("imgotraalerta").vw.getWidth() / 2)));
//BA.debugLineNum = 14;BA.debugLine="lblOtraAlerta.HorizontalCenter = lblAlgas.HorizontalCenter"[Alertas_Tab2/General script]
views.get("lblotraalerta").vw.setLeft((int)((views.get("lblalgas").vw.getLeft() + views.get("lblalgas").vw.getWidth()/2) - (views.get("lblotraalerta").vw.getWidth() / 2)));

}
}