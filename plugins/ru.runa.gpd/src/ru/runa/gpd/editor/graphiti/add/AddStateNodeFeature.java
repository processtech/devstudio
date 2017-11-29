package ru.runa.gpd.editor.graphiti.add;

import org.eclipse.graphiti.features.context.IAddContext;
import org.eclipse.graphiti.mm.GraphicsAlgorithmContainer;
import org.eclipse.graphiti.mm.algorithms.Image;
import org.eclipse.graphiti.mm.algorithms.MultiText;
import org.eclipse.graphiti.mm.algorithms.Rectangle;
import org.eclipse.graphiti.mm.algorithms.RoundedRectangle;
import org.eclipse.graphiti.mm.algorithms.Text;
import org.eclipse.graphiti.mm.algorithms.styles.Orientation;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.services.Graphiti;

import ru.runa.gpd.editor.graphiti.GaProperty;
import ru.runa.gpd.editor.graphiti.StyleUtil;
import ru.runa.gpd.editor.graphiti.layout.LayoutStateNodeFeature;
import ru.runa.gpd.lang.NodeRegistry;
import ru.runa.gpd.lang.model.Node;
import ru.runa.gpd.lang.model.SwimlanedNode;
import ru.runa.gpd.util.SwimlaneDisplayMode;

public class AddStateNodeFeature extends AddNodeFeature {

    @Override
    public PictogramElement add(IAddContext context) {
        Node node = (Node) context.getNewObject();
        String bpmnName = node.getTypeDefinition().getBpmnElementName();
        //
        ContainerShape containerShape = Graphiti.getPeCreateService().createContainerShape(context.getTargetContainer(), true);
        Rectangle main = Graphiti.getGaService().createInvisibleRectangle(containerShape);
        Graphiti.getGaService().setLocationAndSize(main, context.getX(), context.getY(), context.getWidth(), context.getHeight());
        main.getProperties().add(new GaProperty(GaProperty.ID, LayoutStateNodeFeature.MAIN_RECT));
        //
        RoundedRectangle border = Graphiti.getGaService().createRoundedRectangle(main, 20, 20);
        border.getProperties().add(new GaProperty(GaProperty.ID, LayoutStateNodeFeature.BORDER_RECT));
        border.setLineWidth(2);
        border.setStyle(StyleUtil.getStyleForEvent(getDiagram(), bpmnName));
        if (node instanceof SwimlanedNode && node.getProcessDefinition().getSwimlaneDisplayMode() == SwimlaneDisplayMode.none) {
            Text swimlaneText = Graphiti.getGaService().createText(border, ((SwimlanedNode) node).getSwimlaneLabel());
            swimlaneText.getProperties().add(new GaProperty(GaProperty.ID, GaProperty.SWIMLANE_NAME));
            swimlaneText.setHorizontalAlignment(Orientation.ALIGNMENT_CENTER);
            swimlaneText.setStyle(StyleUtil.getStyleForText(getDiagram(), bpmnName));
        }
        MultiText nameText = Graphiti.getGaService().createMultiText(border, node.getName());
        nameText.getProperties().add(new GaProperty(GaProperty.ID, GaProperty.NAME));
        nameText.setHorizontalAlignment(Orientation.ALIGNMENT_CENTER);
        nameText.setStyle(StyleUtil.getStyleForText(getDiagram(), bpmnName));

        containerShape.getProperties().add(new GaProperty(GaProperty.MINIMAZED_VIEW, String.valueOf(node.isMinimizedView())));
        //
        Image image = Graphiti.getGaService().createImage(main, NodeRegistry.getNodeTypeDefinition(node.getClass()).getPaletteIcon());
        image.getProperties().add(new GaProperty(GaProperty.ID, GaProperty.ICON));
        image.setTransparency(node.isMinimizedView() ? .0 : 1.0);
        Graphiti.getGaService().setLocationAndSize(image, MINIMAZED_ICON_X, MINIMAZED_ICON_Y, ICON_WIDTH, ICON_HEIGHT);
        //
        addCustomGraphics(node, context, main, containerShape);
        //
        link(containerShape, node);
        //
        Graphiti.getPeCreateService().createChopboxAnchor(containerShape);
        layoutPictogramElement(containerShape);
        return containerShape;
    }

    protected void addCustomGraphics(Node node, IAddContext context, GraphicsAlgorithmContainer container, ContainerShape containerShape) {
    }
}
