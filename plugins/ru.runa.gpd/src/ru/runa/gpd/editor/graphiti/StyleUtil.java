package ru.runa.gpd.editor.graphiti;

import java.awt.Font;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.graphiti.mm.StyleContainer;
import org.eclipse.graphiti.mm.algorithms.styles.AdaptedGradientColoredAreas;
import org.eclipse.graphiti.mm.algorithms.styles.Color;
import org.eclipse.graphiti.mm.algorithms.styles.GradientColoredArea;
import org.eclipse.graphiti.mm.algorithms.styles.GradientColoredAreas;
import org.eclipse.graphiti.mm.algorithms.styles.LocationType;
import org.eclipse.graphiti.mm.algorithms.styles.Style;
import org.eclipse.graphiti.mm.algorithms.styles.StylesFactory;
import org.eclipse.graphiti.mm.pictograms.Diagram;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.graphiti.services.IGaService;
import org.eclipse.graphiti.util.ColorConstant;
import org.eclipse.graphiti.util.ColorUtil;
import org.eclipse.graphiti.util.IColorConstant;
import org.eclipse.graphiti.util.IGradientType;
import org.eclipse.graphiti.util.IPredefinedRenderingStyle;
import org.eclipse.jface.preference.PreferenceConverter;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.RGB;

import ru.runa.gpd.Activator;
import ru.runa.gpd.settings.PrefConstants;

public class StyleUtil implements PrefConstants {
    public static final IColorConstant FOREGROUND = new ColorConstant(100, 100, 100);
    public static final IColorConstant BACKGROUND = new ColorConstant(255, 255, 255);
    public static final IColorConstant VERY_LIGHT_BLUE = new ColorConstant(250, 251, 252);
    public static final IColorConstant LIGHT_BLUE = new ColorConstant(3, 104, 154);
    public static final IColorConstant BPMN_CLASS_FOREGROUND = new ColorConstant(0, 0, 0);
    private static List<String> bpmnNames = new ArrayList<String>();
    private static final String POLYGON_DIAMOND_STYLE_ID = "BPMN-POLYGON-DIAMOND";
    private static final String TASK_STYLE_ID = "TASK";
    private static final String TRANSITION_STYLE_ID = "BPMN-TRANSITION";
    private static final String POLYGON_ARROW_STYLE_ID = "BPMN-POLYGON-ARROW";
    private static IGaService gaService = Graphiti.getGaService();
    static {
        bpmnNames.add("multiTask");
        bpmnNames.add("multiProcess");
        bpmnNames.add("scriptTask");
        bpmnNames.add("userTask");
        bpmnNames.add("subProcess");
        bpmnNames.add("startTextDecoration");
        bpmnNames.add("endTextDecoration");
    }

    public static Style getStyleForEvent(Diagram diagram, String bpmnName) {
        final String styleId = bpmnName + "EVENT"; //$NON-NLS-1$
        Style style = findStyle(diagram, styleId);
        if (style == null) { // style not found - create new style
            style = gaService.createStyle(diagram, styleId);
            initStyleForEvent(diagram, bpmnName, style);
        }
        return style;
    }

    private static void initStyleForEvent(Diagram diagram, String bpmnName, Style style) {
        if (style == null) {
            String styleId = bpmnName + "EVENT";
            style = getStyle(diagram, styleId);
        }
        Color color = null;
        switch (bpmnName) {
        case "multiTask":
            color = initColor(style, P_BPMN_MULTITASKSTATE_BASE_COLOR, P_BPMN_MULTITASKSTATE_BACKGROUND_COLOR, diagram);
            break;
        case "multiProcess":
            color = initColor(style, P_BPMN_MULTISUBPROCESS_BASE_COLOR, P_BPMN_MULTISUBPROCESS_BACKGROUND_COLOR, diagram);
            break;
        case "scriptTask":
            color = initColor(style, P_BPMN_SCRIPTTASK_BASE_COLOR, P_BPMN_SCRIPTTASK_BACKGROUND_COLOR, diagram);
            break;
        case "userTask":
            color = initColor(style, P_BPMN_STATE_BASE_COLOR, P_BPMN_STATE_BACKGROUND_COLOR, diagram);
            break;
        case "subProcess":
            color = initColor(style, P_BPMN_SUBPROCESS_BASE_COLOR, P_BPMN_SUBPROCESS_BACKGROUND_COLOR, diagram);
            break;
        default:
            color = initColor(style, P_BPMN_BASE_COLOR, P_BPMN_BACKGROUND_COLOR, diagram);
            break;
        }
        gaService.setRenderingStyle(style, getDefaultEventColor(diagram, color));
    }

    private static Style getStyle(Diagram diagram, String styleId) {
        Style style = findStyle(diagram, styleId);
        if (style == null) {
            style = gaService.createStyle(diagram, styleId);
        }
        return style;
    }

    private static Color initColor(Style style, String baseColor, String backgroundColor, Diagram diagram) {
        style.setForeground(getColor(baseColor, FOREGROUND, diagram));
        return getColor(backgroundColor, new ColorConstant("FAFBFC"), diagram);
    }

    public static Style getStyleForTask(Diagram diagram) {
        Style style = findStyle(diagram, TASK_STYLE_ID);
        if (style == null) { // style not found - create new style
            style = gaService.createStyle(diagram, TASK_STYLE_ID);
            initStyleForTask(diagram, style);
        }
        return style;
    }

    private static Style initStyleForTask(Diagram diagram, Style style) {
        if (style == null) {
            style = getStyle(diagram, TASK_STYLE_ID);
        }
        style.setForeground(gaService.manageColor(diagram, BPMN_CLASS_FOREGROUND));
        gaService.setRenderingStyle(style, getDefaultTaskColor(diagram));
        style.setLineWidth(2);
        return style;
    }

    private static AdaptedGradientColoredAreas getDefaultTaskColor(final Diagram diagram) {
        final AdaptedGradientColoredAreas agca = StylesFactory.eINSTANCE.createAdaptedGradientColoredAreas();
        agca.setDefinedStyleId("bpmnTaskStyle");
        agca.setGradientType(IGradientType.VERTICAL);
        final GradientColoredAreas defaultGradientColoredAreas = StylesFactory.eINSTANCE.createGradientColoredAreas();
        defaultGradientColoredAreas.setStyleAdaption(IPredefinedRenderingStyle.STYLE_ADAPTATION_DEFAULT);
        final EList<GradientColoredArea> gcas = defaultGradientColoredAreas.getGradientColor();
        addGradientColoredArea(gcas, "FAFBFC", 0, LocationType.LOCATION_TYPE_ABSOLUTE_START, "FFFFCC", 0, //$NON-NLS-1$ //$NON-NLS-2$
                LocationType.LOCATION_TYPE_ABSOLUTE_END, diagram);
        agca.getAdaptedGradientColoredAreas().add(IPredefinedRenderingStyle.STYLE_ADAPTATION_DEFAULT, defaultGradientColoredAreas);
        final GradientColoredAreas primarySelectedGradientColoredAreas = StylesFactory.eINSTANCE.createGradientColoredAreas();
        primarySelectedGradientColoredAreas.setStyleAdaption(IPredefinedRenderingStyle.STYLE_ADAPTATION_DEFAULT);
        final EList<GradientColoredArea> selectedGcas = primarySelectedGradientColoredAreas.getGradientColor();
        addGradientColoredArea(selectedGcas, "E5E5C2", 0, LocationType.LOCATION_TYPE_ABSOLUTE_START, "E5E5C2", 0, //$NON-NLS-1$ //$NON-NLS-2$
                LocationType.LOCATION_TYPE_ABSOLUTE_END, diagram);
        agca.getAdaptedGradientColoredAreas().add(IPredefinedRenderingStyle.STYLE_ADAPTATION_PRIMARY_SELECTED, primarySelectedGradientColoredAreas);
        final GradientColoredAreas secondarySelectedGradientColoredAreas = StylesFactory.eINSTANCE.createGradientColoredAreas();
        secondarySelectedGradientColoredAreas.setStyleAdaption(IPredefinedRenderingStyle.STYLE_ADAPTATION_DEFAULT);
        final EList<GradientColoredArea> secondarySelectedGcas = secondarySelectedGradientColoredAreas.getGradientColor();
        addGradientColoredArea(secondarySelectedGcas, "E5E5C2", 0, LocationType.LOCATION_TYPE_ABSOLUTE_START, "E5E5C2", 0, //$NON-NLS-1$ //$NON-NLS-2$
                LocationType.LOCATION_TYPE_ABSOLUTE_END, diagram);
        agca.getAdaptedGradientColoredAreas().add(IPredefinedRenderingStyle.STYLE_ADAPTATION_SECONDARY_SELECTED,
                secondarySelectedGradientColoredAreas);
        return agca;
    }

    // find the style with a given id in the style-container, can return null
    private static Style findStyle(StyleContainer styleContainer, String id) {
        // find and return style
        Collection<Style> styles = styleContainer.getStyles();
        if (styles != null) {
            for (Style style : styles) {
                if (id.equals(style.getId())) {
                    return style;
                }
            }
        }
        return null;
    }

    private static AdaptedGradientColoredAreas getDefaultEventColor(Diagram diagram, Color color) {
        AdaptedGradientColoredAreas agca = StylesFactory.eINSTANCE.createAdaptedGradientColoredAreas();
        agca.setDefinedStyleId("bpmnEventStyle");
        agca.setGradientType(IGradientType.VERTICAL);
        GradientColoredAreas defaultGradientColoredAreas = StylesFactory.eINSTANCE.createGradientColoredAreas();
        defaultGradientColoredAreas.setStyleAdaption(IPredefinedRenderingStyle.STYLE_ADAPTATION_DEFAULT);
        EList<GradientColoredArea> gcas = defaultGradientColoredAreas.getGradientColor();
        Color colorEnd = gaService.manageColor(diagram,
                new ColorConstant(Math.max(0, color.getRed() - 1), Math.max(0, color.getGreen() - 1), Math.max(0, color.getBlue() - 1)));
        addGradientColoredArea(gcas, color, 0, LocationType.LOCATION_TYPE_ABSOLUTE_START, colorEnd, 0, LocationType.LOCATION_TYPE_ABSOLUTE_END,
                diagram);
        agca.getAdaptedGradientColoredAreas().add(IPredefinedRenderingStyle.STYLE_ADAPTATION_DEFAULT, defaultGradientColoredAreas);
        GradientColoredAreas primarySelectedGradientColoredAreas = StylesFactory.eINSTANCE.createGradientColoredAreas();
        primarySelectedGradientColoredAreas.setStyleAdaption(IPredefinedRenderingStyle.STYLE_ADAPTATION_DEFAULT);
        EList<GradientColoredArea> selectedGcas = primarySelectedGradientColoredAreas.getGradientColor();
        addGradientColoredArea(selectedGcas, "E5E5C2", 0, LocationType.LOCATION_TYPE_ABSOLUTE_START, "E5E5C2", 0, //$NON-NLS-1$ //$NON-NLS-2$
                LocationType.LOCATION_TYPE_ABSOLUTE_END, diagram);
        agca.getAdaptedGradientColoredAreas().add(IPredefinedRenderingStyle.STYLE_ADAPTATION_PRIMARY_SELECTED, primarySelectedGradientColoredAreas);
        GradientColoredAreas secondarySelectedGradientColoredAreas = StylesFactory.eINSTANCE.createGradientColoredAreas();
        secondarySelectedGradientColoredAreas.setStyleAdaption(IPredefinedRenderingStyle.STYLE_ADAPTATION_DEFAULT);
        EList<GradientColoredArea> secondarySelectedGcas = secondarySelectedGradientColoredAreas.getGradientColor();
        addGradientColoredArea(secondarySelectedGcas, "E5E5C2", 0, LocationType.LOCATION_TYPE_ABSOLUTE_START, "E5E5C2", 0, //$NON-NLS-1$ //$NON-NLS-2$
                LocationType.LOCATION_TYPE_ABSOLUTE_END, diagram);
        agca.getAdaptedGradientColoredAreas().add(IPredefinedRenderingStyle.STYLE_ADAPTATION_SECONDARY_SELECTED,
                secondarySelectedGradientColoredAreas);
        return agca;
    }

    private static void addGradientColoredArea(EList<GradientColoredArea> gcas, String colorStart, int locationValueStart,
            LocationType locationTypeStart, String colorEnd, int locationValueEnd, LocationType locationTypeEnd, Diagram diagram) {
        GradientColoredArea gca = StylesFactory.eINSTANCE.createGradientColoredArea();
        gcas.add(gca);
        gca.setStart(StylesFactory.eINSTANCE.createGradientColoredLocation());
        IGaService gaService = Graphiti.getGaService();
        Color startColor = gaService.manageColor(diagram, ColorUtil.getRedFromHex(colorStart), ColorUtil.getGreenFromHex(colorStart),
                ColorUtil.getBlueFromHex(colorStart));
        gca.getStart().setColor(startColor);
        gca.getStart().setLocationType(locationTypeStart);
        gca.getStart().setLocationValue(locationValueStart);
        gca.setEnd(StylesFactory.eINSTANCE.createGradientColoredLocation());
        Color endColor = gaService.manageColor(diagram, ColorUtil.getRedFromHex(colorEnd), ColorUtil.getGreenFromHex(colorEnd),
                ColorUtil.getBlueFromHex(colorEnd));
        gca.getEnd().setColor(endColor);
        gca.getEnd().setLocationType(locationTypeEnd);
        gca.getEnd().setLocationValue(locationValueEnd);
    }

    private static void addGradientColoredArea(EList<GradientColoredArea> gcas, Color startColor, int locationValueStart,
            LocationType locationTypeStart, Color endColor, int locationValueEnd, LocationType locationTypeEnd, Diagram diagram) {
        GradientColoredArea gca = StylesFactory.eINSTANCE.createGradientColoredArea();
        gcas.add(gca);
        gca.setStart(StylesFactory.eINSTANCE.createGradientColoredLocation());
        gca.getStart().setColor(startColor);
        gca.getStart().setLocationType(locationTypeStart);
        gca.getStart().setLocationValue(locationValueStart);
        gca.setEnd(StylesFactory.eINSTANCE.createGradientColoredLocation());
        gca.getEnd().setColor(endColor);
        gca.getEnd().setLocationType(locationTypeEnd);
        gca.getEnd().setLocationValue(locationValueEnd);
    }

    public static Style getStyleForTransition(Diagram diagram) {
        Style style = findStyle(diagram, TRANSITION_STYLE_ID);
        if (style == null) { // style not found - create new style
            style = gaService.createStyle(diagram, TRANSITION_STYLE_ID);
            initStyleForTransition(diagram, style);
        }
        return style;
    }

    private static void initStyleForTransition(Diagram diagram, Style style) {
        if (style == null) {
            style = getStyle(diagram, TRANSITION_STYLE_ID);
        }
        Color color = getColor(P_BPMN_TRANSITION_COLOR, FOREGROUND, diagram);
        style.setForeground(color);
        style.setBackground(color);
        style.setLineWidth(1);
    }

    public static Style getStyleForPolygonArrow(Diagram diagram) {
        Style style = findStyle(diagram, POLYGON_ARROW_STYLE_ID);
        if (style == null) { // style not found - create new style
            style = gaService.createStyle(diagram, POLYGON_ARROW_STYLE_ID);
            initStyleForPolygonArrow(diagram, style);
        }
        return style;
    }

    private static void initStyleForPolygonArrow(Diagram diagram, Style style) {
        if (style == null) {
            style = getStyle(diagram, POLYGON_ARROW_STYLE_ID);
        }
        Color color = getColor(P_BPMN_TRANSITION_COLOR, FOREGROUND, diagram);
        style.setForeground(color);
        style.setBackground(color);
        style.setLineWidth(1);
    }

    public static Style getStyleForPolygonDiamond(Diagram diagram) {
        Style style = findStyle(diagram, POLYGON_DIAMOND_STYLE_ID);
        if (style == null) { // style not found - create new style
            style = gaService.createStyle(diagram, POLYGON_DIAMOND_STYLE_ID);
            initStyleForPolygonDiamond(diagram, style);
        }
        return style;
    }

    private static void initStyleForPolygonDiamond(Diagram diagram, Style style) {
        if (style == null) {
            style = getStyle(diagram, POLYGON_DIAMOND_STYLE_ID);
        }
        style.setForeground(getColor(P_BPMN_TRANSITION_COLOR, FOREGROUND, diagram));
        style.setBackground(getColor(P_BPMN_BACKGROUND_COLOR, BACKGROUND, diagram));
        style.setLineWidth(1);
    }

    private static Color getColor(String property, IColorConstant color, Diagram diagram) {
        if (Activator.getDefault().getPreferenceStore().contains(property)) {
            RGB colorPref = PreferenceConverter.getColor(Activator.getDefault().getPreferenceStore(), property);
            color = new ColorConstant(colorPref.red, colorPref.green, colorPref.blue);
        }
        return gaService.manageColor(diagram, color);
    }

    public static Style getStyleForText(Diagram diagram, String bpmnName) {
        String styleId = bpmnName + "-Text";
        Style style = findStyle(diagram, styleId);
        if (style == null) { // style not found - create new style
            style = gaService.createStyle(diagram, styleId);
            initStyleForText(diagram, bpmnName, style);
        }
        return style;
    }

    private static void initStyleForText(Diagram diagram, String bpmnName, Style style) {
        if (style == null) {
            String styleId = bpmnName + "-Text";
            style = getStyle(diagram, styleId);
        }
        switch (bpmnName) {
        case "scriptTask":
            updateStyleForText(diagram, style, P_BPMN_SCRIPTTASK_FONT, P_BPMN_SCRIPTTASK_FONT_COLOR);
            break;
        case "userTask":
            updateStyleForText(diagram, style, P_BPMN_STATE_FONT, P_BPMN_STATE_FONT_COLOR);
            break;
        case "endTokenEvent":
            updateStyleForText(diagram, style, P_BPMN_ENDTOKEN_FONT, P_BPMN_ENDTOKEN_FONT_COLOR);
            break;
        case "endTextDecoration":
            updateStyleForText(diagram, style, P_BPMN_END_FONT, P_BPMN_END_FONT_COLOR);
            break;
        case "startTextDecoration":
            updateStyleForText(diagram, style, P_BPMN_STARTSTATE_FONT, P_BPMN_STARTSTATE_FONT_COLOR);
            break;
        case "multiTask":
            updateStyleForText(diagram, style, P_BPMN_MULTITASKSTATE_FONT, P_BPMN_MULTITASKSTATE_FONT_COLOR);
            break;
        case "multiProcess":
            updateStyleForText(diagram, style, P_BPMN_MULTISUBPROCESS_FONT, P_BPMN_MULTISUBPROCESS_FONT_COLOR);
            break;
        case "subProcess":
            updateStyleForText(diagram, style, P_BPMN_SUBPROCESS_FONT, P_BPMN_SUBPROCESS_FONT_COLOR);
            break;
        case "transition":
            updateStyleForText(diagram, style, P_BPMN_SUBPROCESS_FONT, P_BPMN_TRANSITION_COLOR);
            break;
        default:
            updateStyleForText(diagram, style, P_BPMN_FONT, P_BPMN_FONT_COLOR);
        }
    }

    private static void updateStyleForText(Diagram diagram, Style style, String font, String fontColor) {
        if (Activator.getDefault().getPreferenceStore().contains(font)) {
            FontData fontData = PreferenceConverter.getFontData(Activator.getDefault().getPreferenceStore(), font);
            org.eclipse.graphiti.mm.algorithms.styles.Font fontS;
            if (fontData.equals(PreferenceConverter.FONTDATA_DEFAULT_DEFAULT)) {
                fontS = gaService.manageFont(diagram, "Arial", 8, false, false);
            } else {
                fontS = gaService.manageFont(diagram, fontData.getName(), fontData.getHeight(), (fontData.getStyle() & Font.ITALIC) != 0,
                        (fontData.getStyle() & Font.BOLD) != 0);
            }
            style.setFont(fontS);
            Color color = getColor(fontColor, FOREGROUND, diagram);
            style.setForeground(color);
            style.setBackground(color);
        }
    }

    public static void resetStyles(Diagram diagram) {
        for (String bpmnName : bpmnNames) {
            initStyleForEvent(diagram, bpmnName, null);
            initStyleForTask(diagram, null);
            initStyleForTransition(diagram, null);
            initStyleForPolygonArrow(diagram, null);
            initStyleForPolygonDiamond(diagram, null);
            initStyleForText(diagram, bpmnName, null);
        }
    }
}
