<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>Контакт</title>
    <link rel="stylesheet" type="text/css" href="css/table-style.css">
    <link rel="stylesheet" type="text/css" href="css/contact-style.css">
    <link rel="stylesheet" type="text/css" href="css/nav-button-style.css">
</head>
<body>
    <div class="contact-container">
        <div class="contact-info">
            <h3>Основная информация</h3>
            <label>
                Фамилия
                <input type="text" value="${contact.lastName}">
            </label>
            <label>
                Имя
                <input type="text" value="${contact.firstName}">
            </label>
            <label>
                Отчество
                <input type="text" value="${contact.middleName}">
            </label>
            <label>
                Дата рождения
                <input type="text" value="${contact.birthDate}">
            </label>
            <label>
                Пол
                <select class="select-field">
                    <option ${empty contact or empty contact.gender ? 'selected' : ''} value="x">Не выбран</option>
                    <option ${not empty contact and contact.gender == 'm' ? 'selected' : ''} value="m">Мужчина</option>
                    <option ${not empty contact and contact.gender == 'f' ? 'selected' : ''} value="f">Женщина</option>
                </select>
            </label>
            <label>
                Гражданство
                <input type="text" value="${contact.nationality}">
            </label>
            <label>
                Семейное положение
                <select class="select-field">
                    <option ${empty contact or empty contact.maritalStatus ? 'selected' : ''} value="0">
                        Не выбрано
                    </option>
                    <c:forEach var="maritalStatus" items="${maritalStatuses}">
                        <option ${empty contact or contact.maritalStatus != maritalStatus.id ? '' : 'selected'}
                                value="${maritalStatus.id}">
                                ${maritalStatus.name}
                        </option>
                    </c:forEach>
                </select>
            </label>
            <label>
                Website
                <input type="text" value="${contact.website}">
            </label>
            <label>
                Email
                <input type="text" value="${contact.email}">
            </label>
            <label>
                Текущее место работы
                <input type="text" value="${contact.job}">
            </label>
            <h3>Адрес</h3>
            <label>
                Страна
                <input type="text" value="${contact.address.country}">
            </label>
            <label>
                Город
                <input type="text" value="${contact.address.city}">
            </label>
            <label>
                Улица
                <input type="text" value="${contact.address.street}">
            </label>
            <label>
                Дом
                <input type="text" value="${contact.address.houseNumber}">
            </label>
            <label>
                Квартира
                <input type="text" value="${contact.address.flatNumber}">
            </label>
            <label>
                Почтовый индекс
                <input type="text" value="${contact.address.postcode}">
            </label>
        </div>
        <div class="phone-list">
            <table>
                <div class="nav-buttons">
                    <div class="nav-button add" title="add" id="add-phone-button"></div>
                    <div class="nav-button edit" title="edit" id="edit-phone-button"></div>
                    <div class="nav-button delete" title="delete" id="delete-phone-button"></div>
                </div>
                <h3>
                    Список телефонов
                </h3>
                <col class="column-1">
                <col class="column-2">
                <col class="column-3">
                <col class="column-4">
                <thead>
                <tr>
                    <th></th>
                    <th>Номер телефон</th>
                    <th>Тип телефона</th>
                    <th>Комментарий</th>
                </tr>
                </thead>
                <tbody>
                    <c:forEach var="phone" items="${contact.phones}">
                        <tr>
                            <td>
                                <input type="checkbox" name="check-phone-${phone.id}" id="${phone.id}">
                            </td>
                            <td>
                                <c:out value="${phone.countryCode} ${phone.operatorCode} ${phone.number}"/>
                            </td>
                            <td>
                                <c:out value="${not empty phone.phoneType ? phoneTypes[phone.phoneType - 1].name : ''}"/>
                            </td>
                            <td>
                                <c:out value="${phone.comment}"/>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
        <div class="attachment-list">
            <table>
                <div class="nav-buttons">
                    <div class="nav-button add" title="add" id="add-attachment-button"></div>
                    <div class="nav-button edit" title="edit" id="edit-attachment-button"></div>
                    <div class="nav-button delete" title="delete" id="delete-attachment-button"></div>
                </div>
                <h3>
                    Список присоединений
                </h3>
                <col class="column-1">
                <col class="column-2">
                <col class="column-3">
                <col class="column-4">
                <thead>
                <tr>
                    <th></th>
                    <th>Имя файла</th>
                    <th>Дата присоединения</th>
                    <th>Комментарий</th>
                </tr>
                </thead>
                <tbody>
                    <c:forEach var="attachment" items="${contact.attachments}">
                        <tr>
                            <td>
                                <input type="checkbox" name="check-attachment-${attachment.id}" id="${attachment.id}">
                            </td>
                            <td>
                                <c:out value="${attachment.fileName}"/>
                            </td>
                            <td>
                                <c:out value="${attachment.uploadDate}"/>
                            </td>
                            <td>
                                <c:out value="${attachment.comment}"/>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
    <div class="popup-window" id="phone-popup">
        <div class="popup-content">
            <div class="popup-title">
                Редактирование телефона
            </div>
            <label>Код страны
                <input type="text" id="country-code">
            </label>
            <label>Код оператора
                <input type="text" id="operator-code">
            </label>
            <label>Номер
                <input type="text" id="phone-number">
            </label>
            <label>Тип
                <select class="select-field" id="phone-type">
                    <option value="0">Не выбран</option>
                    <c:forEach var="phoneType" items="${phoneTypes}">
                        <option value="${phoneType.id}">${phoneType.name}</option>
                    </c:forEach>
                </select>
            </label>
            <label>Коментарий
                <textarea rows="5" maxlength="255" id="phone-comment"></textarea>
            </label>
            <div class="popup-buttons">
                <div class="popup-button" id="submit-phone-button">Сохранить</div>
                <div class="popup-button" id="cancel-phone-button">Отмена</div>
            </div>
        </div>
    </div>
    <div class="popup-window" id="attachment-popup">
        <div class="popup-content">
            <div class="popup-title">
                Редактирование присоединений
            </div>
            <label>Имя файла
                <input type="file" id="file-path">
            </label>
            <label>Комментарий
                <textarea rows="5" maxlength="255" id="attachment-comment"></textarea>
            </label>
            <div class="popup-buttons">
                <div class="popup-button" id="submit-attachment-button">Сохранить</div>
                <div class="popup-button" id="cancel-attachment-button">Отмена</div>
            </div>
        </div>
    </div>
    <script type="text/javascript" src="js/contact-list-script.js"></script>
</body>
</html>
