package ru.runa.gpd.settings;

import java.awt.Font;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.graphics.FontData;

import ru.runa.gpd.Activator;
import ru.runa.gpd.Localization;
import ru.runa.gpd.lang.Language;

/**
 * Class used to initialize default preference values.
 */
public class PreferenceInitializer extends AbstractPreferenceInitializer implements PrefConstants {
    private final String DEFAULT_CONNECTOR_ID = "jboss7.ws";

    @Override
    public void initializeDefaultPreferences() {
        IPreferenceStore store = Activator.getDefault().getPreferenceStore();
        store.setDefault(P_BPMN_SHOW_SWIMLANE, true);
        store.setDefault(P_BPMN_FONT, new FontData("Arial", 8, Font.PLAIN).toString());
        store.setDefault(P_BPMN_COLOR_FONT, "100,100,100");
        store.setDefault(P_BPMN_COLOR_BACKGROUND, "246, 247, 255");
        store.setDefault(P_BPMN_COLOR_BASE, "3, 104, 154");
        store.setDefault(P_BPMN_COLOR_TRANSITION, "0,0,0");
        store.setDefault(P_DEFAULT_LANGUAGE, Language.BPMN.toString());
        store.setDefault(P_FORM_DEFAULT_FCK_EDITOR, FORM_CK_EDITOR4);
        store.setDefault(P_FORM_WEB_SERVER_PORT, "48780");
        store.setDefault(P_FORM_EXTERNAL_EDITOR_PATH, "");
        store.setDefault(P_FORM_USE_EXTERNAL_EDITOR, false);
        store.setDefault(P_WFE_CONNECTION_TYPE, DEFAULT_CONNECTOR_ID);
        store.setDefault(P_WFE_CONNECTION_HOST, "localhost");
        store.setDefault(P_WFE_CONNECTION_PORT, "8080");
        store.setDefault(P_WFE_CONNECTION_VERSION, "auto");
        store.setDefault(P_WFE_CONNECTION_LOGIN_MODE, LOGIN_MODE_LOGIN_PASSWORD);
        store.setDefault(P_WFE_CONNECTION_LOGIN, "Administrator");
        store.setDefault(P_WFE_CONNECTION_PASSWORD, "wf");
        store.setDefault(P_WFE_LOAD_PROCESS_DEFINITIONS_HISTORY, false);
        store.setDefault(P_LDAP_CONNECTION_PROVIDER_URL, "ldap://192.168.0.1/dc=domain,dc=com");
        store.setDefault(P_DATE_FORMAT_PATTERN, "dd.MM.yyyy");
        store.setDefault(P_ENABLE_REGULATIONS_MENU_ITEMS, Localization.getString("disable"));
        
        store.setDefault(P_BPMN_STATE_FONT, new FontData("Arial", 8, Font.PLAIN).toString());
        store.setDefault(P_BPMN_STATE_FONT, "100,100,100");
        store.setDefault(P_BPMN_STATE_BACKGROUND_COLOR, "246, 247, 255");
        store.setDefault(P_BPMN_STATE_BASE_COLOR, "3, 104, 154");

        store.setDefault(P_BPMN_ENDTOKEN_FONT, new FontData("Arial", 8, Font.PLAIN).toString());
        store.setDefault(P_BPMN_ENDTOKEN_FONT, "100,100,100");

        store.setDefault(P_BPMN_SCRIPTTASK_FONT, new FontData("Arial", 8, Font.PLAIN).toString());
        store.setDefault(P_BPMN_SCRIPTTASK_FONT, "100,100,100");
        store.setDefault(P_BPMN_SCRIPTTASK_BACKGROUND_COLOR, "246, 247, 255");
        store.setDefault(P_BPMN_SCRIPTTASK_BASE_COLOR, "3, 104, 154");

        store.setDefault(P_BPMN_MULTITASKSTATE_FONT, new FontData("Arial", 8, Font.PLAIN).toString());
        store.setDefault(P_BPMN_MULTITASKSTATE_FONT, "100,100,100");
        store.setDefault(P_BPMN_MULTITASKSTATE_BACKGROUND_COLOR, "246, 247, 255");
        store.setDefault(P_BPMN_MULTITASKSTATE_BASE_COLOR, "3, 104, 154");

        store.setDefault(P_BPMN_MULTISUBPROCESS_FONT, new FontData("Arial", 8, Font.PLAIN).toString());
        store.setDefault(P_BPMN_MULTISUBPROCESS_FONT, "100,100,100");
        store.setDefault(P_BPMN_MULTISUBPROCESS_BACKGROUND_COLOR, "246, 247, 255");
        store.setDefault(P_BPMN_MULTISUBPROCESS_BASE_COLOR, "3, 104, 154");

        store.setDefault(P_BPMN_STARTSTATE_FONT, new FontData("Arial", 8, Font.PLAIN).toString());
        store.setDefault(P_BPMN_STARTSTATE_FONT, "100,100,100");

        store.setDefault(P_BPMN_END_FONT, new FontData("Arial", 8, Font.PLAIN).toString());
        store.setDefault(P_BPMN_END_FONT, "100,100,100");

        store.setDefault(P_BPMN_SUBPROCESS_FONT, new FontData("Arial", 8, Font.PLAIN).toString());
        store.setDefault(P_BPMN_SUBPROCESS_FONT, "100,100,100");
        store.setDefault(P_BPMN_SUBPROCESS_BACKGROUND_COLOR, "246, 247, 255");
        store.setDefault(P_BPMN_SUBPROCESS_BASE_COLOR, "3, 104, 154");
    }
}
