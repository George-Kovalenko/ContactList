<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:choose>
    <c:when test="${empty contact}">
        <c:set var="title" value="Создание контакта"/>
        <c:set var="submitAction" value="controller?command=create_contact"/>
    </c:when>
    <c:otherwise>
        <c:set var="title" value="Редактирование контакта"/>
        <c:set var="submitAction" value="controller?command=update_contact&id=${contact.id}"/>
    </c:otherwise>
</c:choose>
<c:choose>
    <c:when test="${not empty photo}">
        <c:set var="photoPath" value="controller?command=get_photo&path=${photo}"/>
    </c:when>
    <c:otherwise>
        <c:set var="photoPath" value="icons/default_contact_icon.jpeg"/>
    </c:otherwise>
</c:choose>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title><c:out value="${title}"/></title>
    <link rel="stylesheet" href="css/table-style.css">
    <link rel="stylesheet" href="css/contact-style.css">
    <link rel="stylesheet" href="css/nav-button-style.css">
</head>
<body>
    <div class="contact-container">
        <form method="post" id="contact-form" action="${submitAction}" enctype="multipart/form-data">
            <div class="contact-info">
                <div class="contact-photo" id="contact-photo">
                    <img src="${photoPath}" alt="contact photo" id="contact-photo-image" class="contact-photo-image">
                    <input type="file" name="photo-field" id="photo-input-field" style="display: none;">
                </div>
                <h3>Основная информация</h3>
                <label>
                    Фамилия*
                    <input id="last-name" type="text" name="last-name" value="${contact.lastName}">
                </label>
                <label>
                    Имя*
                    <input id="first-name" type="text" name="first-name" value="${contact.firstName}">
                </label>
                <label>
                    Отчество
                    <input id="middle-name" type="text" name="middle-name" value="${contact.middleName}">
                </label>
                <label>
                    Дата рождения
                    <input type="text" name="birth-date" value="${contact.birthDate}">
                </label>
                <label>
                    Пол
                    <select class="select-field" name="gender">
                        <option ${empty contact or empty contact.gender ? 'selected' : ''} value="x">Не выбран</option>
                        <option ${not empty contact and contact.gender == 'm' ? 'selected' : ''} value="m">Мужской</option>
                        <option ${not empty contact and contact.gender == 'f' ? 'selected' : ''} value="f">Женский</option>
                    </select>
                </label>
                <label>
                    Гражданство
                    <input id="nationality" type="text" name="nationality" value="${contact.nationality}">
                </label>
                <label>
                    Семейное положение
                    <select class="select-field" name="marital-status">
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
                    <input id="website" type="text" name="website" value="${contact.website}">
                </label>
                <label>
                    Email
                    <input id="email" type="text" name="email" value="${contact.email}">
                </label>
                <label>
                    Текущее место работы
                    <input id="job" type="text" name="job" value="${contact.job}">
                </label>
                <h3>Адрес</h3>
                <label>
                    Страна
                    <input id="country" type="text" name="country" value="${contact.address.country}">
                </label>
                <label>
                    Город
                    <input id="city" type="text" name="city" value="${contact.address.city}">
                </label>
                <label>
                    Улица
                    <input id="street" type="text" name="street" value="${contact.address.street}">
                </label>
                <label>
                    Дом
                    <input id="house" type="text" name="house" value="${contact.address.houseNumber}">
                </label>
                <label>
                    Квартира
                    <input id="flat" type="text" name="flat" value="${contact.address.flatNumber}">
                </label>
                <label>
                    Почтовый индекс
                    <input id="postcode" type="text" name="postcode" value="${contact.address.postcode}">
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
                        <th>Номер телефона</th>
                        <th>Тип телефона</th>
                        <th>Комментарий</th>
                    </tr>
                    </thead>
                    <tbody id="phone-body">
                        <c:forEach var="phone" items="${contact.phones}">
                            <tr id="phone-${phone.id}" class="phone">
                                <td>
                                    <input type="checkbox" name="check-phone" value="${phone.id}">
                                </td>
                                <td id="phone-number-${phone.id}">
                                    <c:out value="${phone.countryCode} ${phone.operatorCode} ${phone.number}"/>
                                </td>
                                <td id="phone-type-${phone.id}">
                                    <c:out value="${not empty phone.phoneType ? phoneTypes[phone.phoneType - 1].name : ''}"/>
                                </td>
                                <td id="phone-comment-${phone.id}">
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
                    <col class="column-1">
                    <thead>
                    <tr>
                        <th></th>
                        <th>Имя файла</th>
                        <th>Дата присоединения</th>
                        <th>Комментарий</th>
                        <th>Скачать</th>
                    </tr>
                    </thead>
                    <tbody id="attachment-body">
                        <c:forEach var="attachment" items="${contact.attachments}">
                            <tr id="attachment-${attachment.id}" class="attachment">
                                <td>
                                    <input type="checkbox" name="check-attachment" value="${attachment.id}">
                                </td>
                                <td id="attachment-file-name-${attachment.id}">
                                    <c:out value="${attachment.fileName}"/>
                                </td>
                                <td id="attachment-upload-date-${attachment.id}">
                                    <c:out value="${attachment.uploadDate}"/>
                                </td>
                                <td id="attachment-comment-${attachment.id}">
                                    <c:out value="${attachment.comment}"/>
                                </td>
                                <td id="attachment-download-link-${attachment.id}">
                                    <a class="nav-button download"
                                       href="controller?command=download_attachment&id=${attachment.id}"></a>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
            <div id="attachment-input-fields"></div>
            <input type="button" class="button-style" id="save-contact-button" value="Сохранить">
            <input type="button" class="button-style" id="back-button" value="Домой">
        </form>
    </div>
    <div class="popup-window" id="photo-popup">
        <div class="popup-content">
            <div class="popup-title">
                Редактирование фотографии
            </div>
            <div id="photo-path-field">
                <input type="file" id="photo-path" accept="image/jpeg, image/png">
            </div>
            <div class="popup-buttons">
                <div class="button-style" id="submit-photo-button">Сохранить</div>
                <div class="button-style" id="cancel-photo-button">Отмена</div>
                <div class="button-style" id="delete-photo-button">Удалить</div>
            </div>
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
                <select class="select-field" id="phone-type-select">
                    <option value="">Не выбран</option>
                    <c:forEach var="phoneType" items="${phoneTypes}">
                        <option value="${phoneType.name}" id="${phoneType.name}">${phoneType.name}</option>
                    </c:forEach>
                </select>
            </label>
            <label>Коментарий
                <textarea rows="5" maxlength="255" id="phone-comment"></textarea>
            </label>
            <div class="popup-buttons">
                <div class="button-style" id="submit-phone-button">Сохранить</div>
                <div class="button-style" id="cancel-phone-button">Отмена</div>
            </div>
        </div>
    </div>
    <div class="popup-window" id="attachment-popup">
        <div class="popup-content">
            <div class="popup-title">
                Редактирование присоединений
            </div>
            <label id="path-to-file">Имя файла
                <input type="file" id="file-path">
            </label>
            <label>Комментарий
                <textarea rows="5" maxlength="255" id="attachment-comment"></textarea>
            </label>
            <div class="popup-buttons">
                <div class="button-style" id="submit-attachment-button">Сохранить</div>
                <div class="button-style" id="cancel-attachment-button">Отмена</div>
            </div>
        </div>
    </div>
    <div class="popup-window" id="error-message-popup">
        <div class="popup-content">
            <div class="popup-title">
                Некорректный ввод
            </div>
            <div id="error-message"></div>
            <div class="popup-buttons">
                <div class="button-style" id="submit-error-message-button">Принять</div>
            </div>
        </div>
    </div>
    <script src="js/contact-page-script.js"></script>
    <script src="js/back-button-script.js"></script>
</body>
</html>
