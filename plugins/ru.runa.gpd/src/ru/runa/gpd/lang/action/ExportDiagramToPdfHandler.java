package ru.runa.gpd.lang.action;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.internal.Workbench;

import ru.runa.gpd.Localization;
import ru.runa.gpd.PluginLogger;
import ru.runa.gpd.editor.ProcessEditorBase;
import ru.runa.gpd.util.DiagramPdfExporter;

public class ExportDiagramToPdfHandler extends AbstractHandler {

    @Override
    public Object execute(ExecutionEvent event) throws ExecutionException {
        FileDialog fd = new FileDialog(HandlerUtil.getActiveShellChecked(event), SWT.SAVE);
        fd.setText(Localization.getString("ExportDiagram.dialog.title"));
        ProcessEditorBase editor = (ProcessEditorBase) Workbench.getInstance().getActiveWorkbenchWindow().getActivePage().getActiveEditor();
        String paperSize = event.getParameter("paperSize");
        fd.setFileName(editor.getDefinition().getName() + "." + paperSize + ".pdf");
        String filePath = fd.open();
        if (filePath != null) {
            try {
                DiagramPdfExporter.go(editor, filePath, paperSize);
            } catch (Exception e) {
                PluginLogger.logError(e);
            }
        }
        return null;
    }

}
