<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>Контакт</title>
    <link rel="stylesheet" type="text/css" href="css/table-style.css">
    <link rel="stylesheet" type="text/css" href="css/contact-style.css">
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
                <div class="radio-buttons">
                    <input type="radio"><label>Мужской</label>
                    <input type="radio"><label>Женский</label>
                </div>
            </label>
            <label>
                Гражданство
                <input type="text" value="${contact.nationality}">
            </label>
            <label>
                Семейное положение
                <input type="text">
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
                <caption>
                    <h3>Список телефонов</h3>
                </caption>
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
                <tr>
                    <td>
                        <input type="checkbox" name="check" id="1">
                    </td>
                    <td></td>
                    <td></td>
                    <td></td>
                </tr>
                </tbody>
            </table>
        </div>
        <div class="attachment-list">
            <table>
                <caption>
                    <h3>Список присоединений</h3>
                </caption>
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
                <tr>
                    <td>
                        <input type="checkbox" name="check" id="2">
                    </td>
                    <td></td>
                    <td></td>
                    <td></td>
                </tr>
                </tbody>
            </table>
        </div>
    </div>
</body>
</html>
