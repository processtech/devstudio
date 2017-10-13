package ru.runa.gpd.lang.action;

import org.eclipse.jface.action.IAction;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;

import ru.runa.gpd.Localization;
import ru.runa.gpd.PluginLogger;
import ru.runa.gpd.util.DiagramPngExporter;

public class ExportDiagramToPngAction extends BaseModelActionDelegate {

    @Override
    public void run(IAction action) {
        FileDialog fd = new FileDialog(getActiveEditor().getSite().getShell(), SWT.SAVE);
        fd.setText(Localization.getString("ExportDiagram.dialog.title"));
        fd.setFileName(getActiveDesignerEditor().getDefinition().getName() + ".png");
        String filePath = fd.open();
        if (filePath != null) {
            try {
                DiagramPngExporter.go(getActiveDesignerEditor(), filePath);
            } catch (Exception e) {
                PluginLogger.logError(e);
            }
        }
    }

}
