package appear.pnud.preservamos.designerscripts;
import anywheresoftware.b4a.objects.TextViewWrapper;
import anywheresoftware.b4a.objects.ImageViewWrapper;
import anywheresoftware.b4a.BA;


public class LS_laymainmunicipio_estadisticas{

public static void LS_general(anywheresoftware.b4a.BA ba, android.view.View parent, anywheresoftware.b4a.keywords.LayoutValues lv, java.util.Map props,
java.util.Map<String, anywheresoftware.b4a.keywords.LayoutBuilder.ViewWrapperAndAnchor> views, int width, int height, float scale) throws Exception {
anywheresoftware.b4a.keywords.LayoutBuilder.setScaleRate(0.3);
views.get("gauge_exoticas").vw.setTop((int)((views.get("panel1").vw.getTop() + views.get("panel1").vw.getHeight()) - (views.get("gauge_exoticas").vw.getHeight())));
views.get("gauge_bio").vw.setTop((int)((views.get("panel1").vw.getTop() + views.get("panel1").vw.getHeight()/2) - (views.get("gauge_bio").vw.getHeight() / 2)));
views.get("gauge_agua").vw.setTop((int)((((views.get("gauge_bio").vw.getTop())-(views.get("gauge_hidro").vw.getTop() + views.get("gauge_hidro").vw.getHeight()))/2d)+(views.get("gauge_hidro").vw.getTop() + views.get("gauge_hidro").vw.getHeight()) - (views.get("gauge_agua").vw.getHeight() / 2)));
views.get("gauge_usos").vw.setTop((int)((((views.get("gauge_exoticas").vw.getTop())-(views.get("gauge_bio").vw.getTop() + views.get("gauge_bio").vw.getHeight()))/2d)+(views.get("gauge_bio").vw.getTop() + views.get("gauge_bio").vw.getHeight()) - (views.get("gauge_usos").vw.getHeight() / 2)));
views.get("lblgaugehidro").vw.setTop((int)((views.get("gauge_hidro").vw.getTop())));
views.get("label1").vw.setTop((int)((views.get("lblgaugehidro").vw.getTop() + views.get("lblgaugehidro").vw.getHeight())));
views.get("lblgaugeagua").vw.setTop((int)((views.get("gauge_agua").vw.getTop())));
views.get("label2").vw.setTop((int)((views.get("lblgaugeagua").vw.getTop() + views.get("lblgaugeagua").vw.getHeight())));
views.get("lblgaugebio").vw.setTop((int)((views.get("gauge_bio").vw.getTop())));
views.get("label3").vw.setTop((int)((views.get("lblgaugebio").vw.getTop() + views.get("lblgaugebio").vw.getHeight())));
views.get("lblgaugeusos").vw.setTop((int)((views.get("gauge_usos").vw.getTop())));
views.get("label4").vw.setTop((int)((views.get("lblgaugeusos").vw.getTop() + views.get("lblgaugeusos").vw.getHeight())));
views.get("lblgaugeexoticas").vw.setTop((int)((views.get("gauge_exoticas").vw.getTop())));
views.get("label5").vw.setTop((int)((views.get("lblgaugeexoticas").vw.getTop() + views.get("lblgaugeexoticas").vw.getHeight())));

}
}