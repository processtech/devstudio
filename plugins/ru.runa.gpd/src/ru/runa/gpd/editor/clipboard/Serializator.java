package ru.runa.gpd.editor.clipboard;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import ru.runa.gpd.lang.model.ProcessDefinition;
import ru.runa.gpd.lang.model.Swimlane;
import ru.runa.gpd.lang.model.Variable;
import ru.runa.gpd.lang.model.VariableUserType;

import com.google.common.base.Strings;

/**
 * Recursive serialization for {@link org.eclipse.swt.dnd.Clipboard}.
 * 
 * @author KuchmaMA
 * 
 */
final class Serializator {

    private Serializator() {
        // no action
    }

    static void write(ObjectOutputStream out, Swimlane swimlane) throws IOException {
        out.writeObject(Strings.nullToEmpty(swimlane.getEditorPath()));
        write(out, (Variable) swimlane);
    }

    static void read(ObjectInputStream in, Swimlane swimlane, ProcessDefinition processDefinition) throws IOException, ClassNotFoundException {
        swimlane.setEditorPath((String) in.readObject());
        read(in, (Variable) swimlane, processDefinition);
    }

    static void write(ObjectOutputStream out, Variable variable) throws IOException {
        out.writeObject(variable.getScriptingName());
        out.writeObject(variable.getFormat());
        out.writeBoolean(variable.isPublicVisibility());
        out.writeObject(Strings.nullToEmpty(variable.getDefaultValue()));
        out.writeObject(variable.getName());
        out.writeObject(Strings.nullToEmpty(variable.getDescription()));
        out.writeObject(variable.getUserType() == null ? "" : variable.getUserType().getName());
        out.writeBoolean(variable.isComplex());
        if (variable.isComplex()) {
            write(out, variable.getUserType());
        }
        out.writeBoolean(variable.getUserType1() != null);
        if (variable.getUserType1() != null) {
            write(out, variable.getUserType1());
        }
        out.writeBoolean(variable.getUserType2() != null);
        if (variable.getUserType2() != null) {
            write(out, variable.getUserType2());
        }
    }

    static void read(ObjectInputStream in, Variable variable, ProcessDefinition processDefinition) throws IOException, ClassNotFoundException {
        variable.setScriptingName((String) in.readObject());
        variable.setFormat((String) in.readObject());
        String format = variable.getFormat();
        variable.setPublicVisibility(in.readBoolean());
        variable.setDefaultValue((String) in.readObject());
        variable.setName((String) in.readObject());
        variable.setDescription((String) in.readObject());
        String label = (String) in.readObject();
        if (!label.isEmpty()) {
            variable.setUserType(processDefinition.getVariableUserType(label));
            variable.setFormat(format);
        }
        if (in.readBoolean()) {
            VariableUserType type = new VariableUserType();
            read(in, type, processDefinition);
            variable.setUserType(type);
            variable.setFormat(format);
        }
        if (in.readBoolean()) {
            VariableUserType type1 = new VariableUserType();
            read(in, type1, processDefinition);
            variable.setUserType1(type1);
        }
        if (in.readBoolean()) {
            VariableUserType type2 = new VariableUserType();
            read(in, type2, processDefinition);
            variable.setUserType2(type2);
        }
    }

    static void write(ObjectOutputStream out, VariableUserType type) throws IOException {
        out.writeObject(type.getName());
        out.writeInt(type.getAttributes().size());
        for (Variable var : type.getAttributes()) {
            write(out, var);
        }
    }

    static void read(ObjectInputStream in, VariableUserType type, ProcessDefinition processDefinition) throws IOException, ClassNotFoundException {
        type.setName((String) in.readObject());
        int attrLength = in.readInt();
        for (int j = 0; j < attrLength; j++) {
            Variable var = new Variable();
            read(in, var, processDefinition);
            type.getAttributes().add(var);
        }

    }
}
