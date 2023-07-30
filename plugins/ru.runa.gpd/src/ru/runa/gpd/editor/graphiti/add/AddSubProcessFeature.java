package ru.runa.gpd.editor.graphiti.add;

import org.eclipse.graphiti.features.context.IAddContext;
import org.eclipse.graphiti.mm.GraphicsAlgorithmContainer;
import org.eclipse.graphiti.mm.algorithms.GraphicsAlgorithm;
import org.eclipse.graphiti.mm.algorithms.Image;
import org.eclipse.graphiti.mm.algorithms.RoundedRectangle;
import org.eclipse.graphiti.mm.algorithms.styles.LineStyle;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.services.Graphiti;
import ru.runa.gpd.editor.graphiti.GaProperty;
import ru.runa.gpd.editor.graphiti.PropertyUtil;
import ru.runa.gpd.editor.graphiti.StyleUtil;
import ru.runa.gpd.editor.graphiti.layout.LayoutSubprocessNodeFeature;
import ru.runa.gpd.lang.model.EventSubprocess;
import ru.runa.gpd.lang.model.Node;
import ru.runa.gpd.lang.model.Subprocess;
import ru.runa.gpd.lang.model.Synchronizable;

public class AddSubprocessFeature extends AddStateNodeFeature {
    @Override
    protected void addCustomGraphics(Node node, IAddContext context, GraphicsAlgorithmContainer container, ContainerShape containerShape) {
        Image image;
        LineStyle lineStyle;
        if (node instanceof EventSubprocess) {
            image = Graphiti.getGaService().createImage(container, "graph/event_subprocess.png");
            lineStyle = LineStyle.DOT;
        } else {
            image = Graphiti.getGaService().createImage(container, "graph/subprocess.png");
            lineStyle = LineStyle.SOLID;
        }
        image.getProperties().add(new GaProperty(GaProperty.ID, GaProperty.SUBPROCESS));

        RoundedRectangle secondBorder = Graphiti.getGaService().createPlainRoundedRectangle(container, 14, 14);
        secondBorder.getProperties().add(new GaProperty(GaProperty.ID, LayoutSubprocessNodeFeature.SECOND_BORDER_RECT));
        secondBorder.setStyle(StyleUtil.getSubprocessNodeTransactionalStyle(getDiagram(), (Subprocess) node));
        secondBorder.setLineStyle(lineStyle);
        if (((Subprocess) node).isTransactional()) {
            containerShape.getProperties().add(new GaProperty(GaProperty.TRANSACTIONAL, GaProperty.TRUE));
            secondBorder.setLineVisible(true);
        } else {
            containerShape.getProperties().add(new GaProperty(GaProperty.TRANSACTIONAL, GaProperty.FALSE));
            secondBorder.setLineVisible(false);
        }

        GraphicsAlgorithm borderRect = PropertyUtil.findGaRecursiveByName(containerShape.getGraphicsAlgorithm(),
                LayoutSubprocessNodeFeature.BORDER_RECT);
        if (((Subprocess) node).isEmbedded()) {
            containerShape.getProperties().add(new GaProperty(GaProperty.EMBEDDED, GaProperty.TRUE));
        } else {
            containerShape.getProperties().add(new GaProperty(GaProperty.EMBEDDED, GaProperty.FALSE));
            borderRect.setLineWidth(borderRect.getStyle().getLineWidth() + 1);
        }
        borderRect.setLineStyle(lineStyle);

        Graphiti.getGaService().setLocation(image, node.getConstraint().width / 2 - 7, node.getConstraint().height - 3 * GRID_SIZE);
        addAsyncImage(node, container, containerShape);
    }

    private void addAsyncImage(Node node, GraphicsAlgorithmContainer container, ContainerShape containerShape) {
        Image asyncImage = Graphiti.getGaService().createImage(container, "graph/async.png");
        asyncImage.getProperties().add(new GaProperty(GaProperty.ID, GaProperty.ASYNC));
        Graphiti.getGaService().setLocation(asyncImage, node.getConstraint().width - 2 * GRID_SIZE, node.getConstraint().height - 2 * GRID_SIZE - 1);
        boolean async = ((Synchronizable) node).isAsync();
        containerShape.getProperties().add(new GaProperty(GaProperty.ASYNC, String.valueOf(async)));
    }
}
