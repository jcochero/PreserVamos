package appear.pnud.preservamos.designerscripts;
import anywheresoftware.b4a.objects.TextViewWrapper;
import anywheresoftware.b4a.objects.ImageViewWrapper;
import anywheresoftware.b4a.BA;


public class LS_aprender_muestreo{

public static void LS_general(java.util.LinkedHashMap<String, anywheresoftware.b4a.keywords.LayoutBuilder.ViewWrapperAndAnchor> views, int width, int height, float scale) {
anywheresoftware.b4a.keywords.LayoutBuilder.setScaleRate(0.3);
//BA.debugLineNum = 2;BA.debugLine="imgElementos.HorizontalCenter = 50%x"[Aprender_Muestreo/General script]
views.get("imgelementos").vw.setLeft((int)((50d / 100 * width) - (views.get("imgelementos").vw.getWidth() / 2)));
//BA.debugLineNum = 3;BA.debugLine="imgElementos2.HorizontalCenter = 50%x"[Aprender_Muestreo/General script]
views.get("imgelementos2").vw.setLeft((int)((50d / 100 * width) - (views.get("imgelementos2").vw.getWidth() / 2)));
//BA.debugLineNum = 4;BA.debugLine="lblTitulo.Top = imgElementos.Bottom + 5dip"[Aprender_Muestreo/General script]
views.get("lbltitulo").vw.setTop((int)((views.get("imgelementos").vw.getTop() + views.get("imgelementos").vw.getHeight())+(5d * scale)));
//BA.debugLineNum = 5;BA.debugLine="lblTexto1.Top = lblTitulo.Bottom + 5dip"[Aprender_Muestreo/General script]
views.get("lbltexto1").vw.setTop((int)((views.get("lbltitulo").vw.getTop() + views.get("lbltitulo").vw.getHeight())+(5d * scale)));
//BA.debugLineNum = 6;BA.debugLine="imgElementos2.Top = lblTexto1.Bottom + 5dip"[Aprender_Muestreo/General script]
views.get("imgelementos2").vw.setTop((int)((views.get("lbltexto1").vw.getTop() + views.get("lbltexto1").vw.getHeight())+(5d * scale)));
//BA.debugLineNum = 7;BA.debugLine="lblTitulo2.Top = imgElementos2.Bottom + 5dip"[Aprender_Muestreo/General script]
views.get("lbltitulo2").vw.setTop((int)((views.get("imgelementos2").vw.getTop() + views.get("imgelementos2").vw.getHeight())+(5d * scale)));
//BA.debugLineNum = 8;BA.debugLine="lblTexto2.Top = lblTitulo2.Bottom + 5dip"[Aprender_Muestreo/General script]
views.get("lbltexto2").vw.setTop((int)((views.get("lbltitulo2").vw.getTop() + views.get("lbltitulo2").vw.getHeight())+(5d * scale)));

}
}