package appear.pnud.preservamos.designerscripts;
import anywheresoftware.b4a.objects.TextViewWrapper;
import anywheresoftware.b4a.objects.ImageViewWrapper;
import anywheresoftware.b4a.BA;


public class LS_alertas_tab3{

public static void LS_general(anywheresoftware.b4a.BA ba, android.view.View parent, anywheresoftware.b4a.keywords.LayoutValues lv, java.util.Map props,
java.util.Map<String, anywheresoftware.b4a.keywords.LayoutBuilder.ViewWrapperAndAnchor> views, int width, int height, float scale) throws Exception {
anywheresoftware.b4a.keywords.LayoutBuilder.setScaleRate(0.3);
views.get("lblalertastitle").vw.setLeft((int)((40d / 100 * width)));
views.get("lblfotostitle").vw.setTop((int)((views.get("lblcoordenadastitle").vw.getTop() + views.get("lblcoordenadastitle").vw.getHeight())+(10d * scale)));
views.get("imgfoto1").vw.setTop((int)((views.get("lblfotostitle").vw.getTop() + views.get("lblfotostitle").vw.getHeight())+(10d * scale)));
views.get("imgfoto2").vw.setTop((int)((views.get("lblfotostitle").vw.getTop() + views.get("lblfotostitle").vw.getHeight())+(10d * scale)));
views.get("imgfoto3").vw.setTop((int)((views.get("lblfotostitle").vw.getTop() + views.get("lblfotostitle").vw.getHeight())+(10d * scale)));
//BA.debugLineNum = 7;BA.debugLine="imgFoto4.Top = lblFotosTitle.Bottom + 10dip"[Alertas_Tab3/General script]
views.get("imgfoto4").vw.setTop((int)((views.get("lblfotostitle").vw.getTop() + views.get("lblfotostitle").vw.getHeight())+(10d * scale)));
//BA.debugLineNum = 8;BA.debugLine="imgIcon.Right = lblAlertasTitle.Left"[Alertas_Tab3/General script]
views.get("imgicon").vw.setLeft((int)((views.get("lblalertastitle").vw.getLeft()) - (views.get("imgicon").vw.getWidth())));
//BA.debugLineNum = 9;BA.debugLine="imgFoto2.Right = 50%x - 5dip"[Alertas_Tab3/General script]
views.get("imgfoto2").vw.setLeft((int)((50d / 100 * width)-(5d * scale) - (views.get("imgfoto2").vw.getWidth())));
//BA.debugLineNum = 10;BA.debugLine="imgFoto1.Right = imgFoto2.Left  - 10dip"[Alertas_Tab3/General script]
views.get("imgfoto1").vw.setLeft((int)((views.get("imgfoto2").vw.getLeft())-(10d * scale) - (views.get("imgfoto1").vw.getWidth())));
//BA.debugLineNum = 11;BA.debugLine="imgFoto3.Left = 50%x + 5dip"[Alertas_Tab3/General script]
views.get("imgfoto3").vw.setLeft((int)((50d / 100 * width)+(5d * scale)));
//BA.debugLineNum = 12;BA.debugLine="imgFoto4.left = imgFoto3.Right  + 10dip"[Alertas_Tab3/General script]
views.get("imgfoto4").vw.setLeft((int)((views.get("imgfoto3").vw.getLeft() + views.get("imgfoto3").vw.getWidth())+(10d * scale)));
//BA.debugLineNum = 13;BA.debugLine="ProgressBar1.SetLeftAndRight(imgFoto1.Left, imgFoto1.Right)"[Alertas_Tab3/General script]
views.get("progressbar1").vw.setLeft((int)((views.get("imgfoto1").vw.getLeft())));
views.get("progressbar1").vw.setWidth((int)((views.get("imgfoto1").vw.getLeft() + views.get("imgfoto1").vw.getWidth()) - ((views.get("imgfoto1").vw.getLeft()))));
//BA.debugLineNum = 14;BA.debugLine="ProgressBar2.SetLeftAndRight(imgFoto2.Left, imgFoto2.Right)"[Alertas_Tab3/General script]
views.get("progressbar2").vw.setLeft((int)((views.get("imgfoto2").vw.getLeft())));
views.get("progressbar2").vw.setWidth((int)((views.get("imgfoto2").vw.getLeft() + views.get("imgfoto2").vw.getWidth()) - ((views.get("imgfoto2").vw.getLeft()))));
//BA.debugLineNum = 15;BA.debugLine="ProgressBar3.SetLeftAndRight(imgFoto3.Left, imgFoto3.Right)"[Alertas_Tab3/General script]
views.get("progressbar3").vw.setLeft((int)((views.get("imgfoto3").vw.getLeft())));
views.get("progressbar3").vw.setWidth((int)((views.get("imgfoto3").vw.getLeft() + views.get("imgfoto3").vw.getWidth()) - ((views.get("imgfoto3").vw.getLeft()))));
//BA.debugLineNum = 16;BA.debugLine="ProgressBar4.SetLeftAndRight(imgFoto3.Left, imgFoto4.Right)"[Alertas_Tab3/General script]
views.get("progressbar4").vw.setLeft((int)((views.get("imgfoto3").vw.getLeft())));
views.get("progressbar4").vw.setWidth((int)((views.get("imgfoto4").vw.getLeft() + views.get("imgfoto4").vw.getWidth()) - ((views.get("imgfoto3").vw.getLeft()))));
//BA.debugLineNum = 17;BA.debugLine="borderFotos1.HorizontalCenter = imgFoto1.HorizontalCenter"[Alertas_Tab3/General script]
views.get("borderfotos1").vw.setLeft((int)((views.get("imgfoto1").vw.getLeft() + views.get("imgfoto1").vw.getWidth()/2) - (views.get("borderfotos1").vw.getWidth() / 2)));
//BA.debugLineNum = 18;BA.debugLine="borderFotos1.VerticalCenter = imgFoto1.VerticalCenter"[Alertas_Tab3/General script]
views.get("borderfotos1").vw.setTop((int)((views.get("imgfoto1").vw.getTop() + views.get("imgfoto1").vw.getHeight()/2) - (views.get("borderfotos1").vw.getHeight() / 2)));
//BA.debugLineNum = 19;BA.debugLine="borderFotos2.HorizontalCenter = imgFoto2.HorizontalCenter"[Alertas_Tab3/General script]
views.get("borderfotos2").vw.setLeft((int)((views.get("imgfoto2").vw.getLeft() + views.get("imgfoto2").vw.getWidth()/2) - (views.get("borderfotos2").vw.getWidth() / 2)));
//BA.debugLineNum = 20;BA.debugLine="borderFotos2.VerticalCenter = imgFoto2.VerticalCenter"[Alertas_Tab3/General script]
views.get("borderfotos2").vw.setTop((int)((views.get("imgfoto2").vw.getTop() + views.get("imgfoto2").vw.getHeight()/2) - (views.get("borderfotos2").vw.getHeight() / 2)));
//BA.debugLineNum = 21;BA.debugLine="borderFotos3.HorizontalCenter = imgFoto3.HorizontalCenter"[Alertas_Tab3/General script]
views.get("borderfotos3").vw.setLeft((int)((views.get("imgfoto3").vw.getLeft() + views.get("imgfoto3").vw.getWidth()/2) - (views.get("borderfotos3").vw.getWidth() / 2)));
//BA.debugLineNum = 22;BA.debugLine="borderFotos3.VerticalCenter = imgFoto3.VerticalCenter"[Alertas_Tab3/General script]
views.get("borderfotos3").vw.setTop((int)((views.get("imgfoto3").vw.getTop() + views.get("imgfoto3").vw.getHeight()/2) - (views.get("borderfotos3").vw.getHeight() / 2)));
//BA.debugLineNum = 23;BA.debugLine="borderFotos4.HorizontalCenter = imgFoto4.HorizontalCenter"[Alertas_Tab3/General script]
views.get("borderfotos4").vw.setLeft((int)((views.get("imgfoto4").vw.getLeft() + views.get("imgfoto4").vw.getWidth()/2) - (views.get("borderfotos4").vw.getWidth() / 2)));
//BA.debugLineNum = 24;BA.debugLine="borderFotos4.VerticalCenter = imgFoto4.VerticalCenter"[Alertas_Tab3/General script]
views.get("borderfotos4").vw.setTop((int)((views.get("imgfoto4").vw.getTop() + views.get("imgfoto4").vw.getHeight()/2) - (views.get("borderfotos4").vw.getHeight() / 2)));
//BA.debugLineNum = 25;BA.debugLine="lblTelefonoTitle.Top = borderFotos1.Bottom + 40dip"[Alertas_Tab3/General script]
views.get("lbltelefonotitle").vw.setTop((int)((views.get("borderfotos1").vw.getTop() + views.get("borderfotos1").vw.getHeight())+(40d * scale)));
//BA.debugLineNum = 26;BA.debugLine="txtTelefono.Top = lblTelefonoTitle.Bottom + 5dip"[Alertas_Tab3/General script]
views.get("txttelefono").vw.setTop((int)((views.get("lbltelefonotitle").vw.getTop() + views.get("lbltelefonotitle").vw.getHeight())+(5d * scale)));
//BA.debugLineNum = 28;BA.debugLine="txtNotas.top = lblNotasTitle.Bottom + 5dip"[Alertas_Tab3/General script]
views.get("txtnotas").vw.setTop((int)((views.get("lblnotastitle").vw.getTop() + views.get("lblnotastitle").vw.getHeight())+(5d * scale)));

}
}