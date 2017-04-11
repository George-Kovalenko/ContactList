package edu.itechart.contactlist.util.email;

import edu.itechart.contactlist.entity.Contact;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupFile;

import java.util.*;

public class EmailTemplateManager {
    private static final String PARAM_NAME = "contact";
    public static final String TEMPLATES_PATH
            = ResourceBundle.getBundle("emailconfig").getObject("template_file_path").toString();
    public static final String SCHEDULE_TEMPLATES_PATH
            = ResourceBundle.getBundle("emailconfig").getObject("schedule_template_file_path").toString();

    public static Map<String, ST> getTemplates(String path) {
        STGroup stGroup = new STGroupFile(path, "UTF-8");
        Map<String, ST> templates = new HashMap<>();
        Set<String> templateNames = stGroup.getTemplateNames();
        for (String templateName : templateNames) {
            templateName = templateName.substring(1);
            ST st = stGroup.getInstanceOf(templateName);
            templates.put(templateName, st);
        }
        return templates;
    }

    public static Map<String, String> getGenericTemplates(Map<String, ST> templates) {
        Contact contact = new Contact();
        contact.setFirstName("%ИМЯ%");
        contact.setLastName("%ФАМИЛИЯ%");
        Map<String, String> genericTemplates = new HashMap<>();
        templates.keySet().forEach(key -> genericTemplates.put(key,
                templates.get(key).add(PARAM_NAME, contact).render()));
        return genericTemplates;
    }

    public static Map<Long, String> getFinalTemplates(String path, String templateName, ArrayList<Contact> contacts) {
        STGroup stGroup = new STGroupFile(path, "UTF-8");
        Map<Long, String> templates = new HashMap<>();
        contacts.forEach(item -> templates.put(item.getId(),
                stGroup.getInstanceOf(templateName).add(PARAM_NAME, item).render()));
        return templates;
    }
}
