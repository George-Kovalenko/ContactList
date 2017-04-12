package edu.itechart.contactlist.util;

import edu.itechart.contactlist.dto.DateSearchType;
import edu.itechart.contactlist.dto.SearchParameters;
import edu.itechart.contactlist.service.MaritalStatusService;
import edu.itechart.contactlist.service.ServiceException;
import org.apache.commons.lang3.StringUtils;

import java.sql.Date;
import java.util.LinkedHashMap;
import java.util.Map;

public class SearchParametersUtils {
    public static Map<String, String> searchParamsToMap(SearchParameters parameters) throws ServiceException {
        Map<String, String> parametersMap = new LinkedHashMap<>();
        addStringField(parametersMap, "Имя", parameters.getFirstName());
        addStringField(parametersMap, "Фамилия", parameters.getLastName());
        addStringField(parametersMap, "Отчество", parameters.getMiddleName());
        addGenderField(parametersMap, parameters.getGender());
        addMaritalStatus(parametersMap, parameters.getMaritalStatus());
        addStringField(parametersMap, "Национальность", parameters.getNationality());
        addDateField(parametersMap, parameters.getBirthDate());
        addDateSearchTypeField(parametersMap, parameters.getDateSearchType());
        addStringField(parametersMap, "Страна", parameters.getCountry());
        addStringField(parametersMap, "Город", parameters.getCity());
        addStringField(parametersMap, "Улица", parameters.getStreet());
        addStringField(parametersMap, "Дом", parameters.getHouseNumber());
        addStringField(parametersMap, "Квартира", parameters.getFlatNumber());
        addStringField(parametersMap, "Почтовый индекс", parameters.getPostcode());
        return parametersMap;
    }

    private static void addStringField(Map<String, String> map, String key, String value) {
        if (StringUtils.isNotEmpty(value)) {
            map.put(key, value);
        }
    }

    private static void addGenderField(Map<String, String> map, String value) {
        String key = "Пол";
        if (StringUtils.equals(value, "m")) {
            map.put(key, "Мужской");
        } else if (StringUtils.equals(value, "f")) {
            map.put(key, "Женский");
        }
    }

    private static void addMaritalStatus(Map<String, String> map, Integer id) throws ServiceException {
        if (id != null) {
            String value = MaritalStatusService.findById(id).getName();
            if (StringUtils.isNotEmpty(value)) {
                map.put("Семейное положение", value);
            }
        }
    }

    private static void addDateField(Map<String, String> map, Date value) {
        if (value != null) {
            map.put("Дата", value.toString());
        }
    }

    private static void addDateSearchTypeField(Map<String, String> map, DateSearchType value) {
        String key = "Тип поиска по дате";
        if (value != null) {
            switch (value) {
                case OLDER:
                    map.put(key, "Старше");
                    break;
                case YOUNGER:
                    map.put(key, "Младше");
                    break;
                case EQUALS:
                    map.put(key, "Точная дата");
                    break;
                default:
                    break;
            }
        }
    }
}
