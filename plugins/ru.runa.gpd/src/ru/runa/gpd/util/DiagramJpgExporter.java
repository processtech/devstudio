package ru.runa.gpd.util;

import org.eclipse.swt.SWT;

import ru.runa.gpd.editor.ProcessEditorBase;
import ru.runa.gpd.editor.gef.GEFImageHelper;
import ru.runa.gpd.lang.model.ProcessDefinition;

public class DiagramJpgExporter {

    public static void go(ProcessEditorBase editor, String filePath) throws Exception {
        ProcessDefinition pd = editor.getDefinition();
        boolean dirty = pd.isDirty();
        GEFImageHelper.save(editor.getGraphicalViewer(), pd, filePath, SWT.IMAGE_JPEG);
        pd.setDirty(dirty);
    }

}
