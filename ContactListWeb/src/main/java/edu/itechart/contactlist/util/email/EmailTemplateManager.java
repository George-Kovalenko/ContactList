package edu.itechart.contactlist.util.email;

import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupFile;

import java.util.ArrayList;
import java.util.ResourceBundle;

public class EmailTemplateManager {
    private static final String TEMPLATE_PREFIX = "t";
    private static final String PARAM_NAME = "name";
    private static final String PARAM_VALUE = "%имя получателя%";
    private STGroup stGroup;

    public EmailTemplateManager() {
        String path = ResourceBundle.getBundle("emailconfig").getObject("template_file_path").toString();
        stGroup = new STGroupFile(path);
    }

    public ArrayList<String> getAllTemplates() {
        ArrayList<String> templates = new ArrayList<>();
        int i = 1;
        ST st;
        while ((st = stGroup.getInstanceOf(TEMPLATE_PREFIX + i++)) != null){
            st.add(PARAM_NAME, PARAM_VALUE);
            templates.add(st.render());
        }
        return templates;
    }

    public String makeEmailText(int index, String value) {
        ST st = stGroup.getInstanceOf(TEMPLATE_PREFIX + index);
        st.add(PARAM_NAME, value);
        return st.render();
    }
}
