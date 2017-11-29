package ru.runa.gpd.editor.graphiti.add;

import org.eclipse.graphiti.features.context.IAddContext;
import org.eclipse.graphiti.mm.algorithms.Rectangle;
import org.eclipse.graphiti.mm.algorithms.Text;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.graphiti.services.IGaService;

import ru.runa.gpd.editor.graphiti.StyleUtil;
import ru.runa.gpd.lang.model.bpmn.StartTextDecoration;

public class AddStartTextDecorationFeature extends AddNodeFeature {

    @Override
    public PictogramElement add(IAddContext context) {
        StartTextDecoration node = (StartTextDecoration) context.getNewObject();
        String labelName = node.getTarget().getName();
        String labelSwimline = new String();
        if (node.getTarget().getSwimlaneLabel() != null) {
            labelSwimline = node.getTarget().getSwimlaneLabel();
        }

        ContainerShape containerShape = Graphiti.getPeCreateService().createContainerShape(context.getTargetContainer(), true);
        
        IGaService gaService = Graphiti.getGaService();

        // create UI element for definition
        Rectangle rect = gaService.createInvisibleRectangle(containerShape);
        gaService.setLocation(rect, context.getX(), context.getY());
        Text textSwimlane = gaService.createText(rect, labelSwimline);
        String bpmnName = node.getTypeDefinition().getBpmnElementName();
        textSwimlane.setStyle(StyleUtil.getStyleForText(getDiagram(), bpmnName));
        Text textName = gaService.createText(rect, labelName);
        textName.setStyle(StyleUtil.getStyleForText(getDiagram(), bpmnName));
        node.setUiContainer(node.new StartDefinitionUI(containerShape, rect, textName, textSwimlane));

        link(containerShape, node);
        Graphiti.getPeCreateService().createChopboxAnchor(containerShape);
        layoutPictogramElement(containerShape);

        return containerShape;
    }

}
