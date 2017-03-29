<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Поиск контактов</title>
    <link rel="stylesheet" type="text/css" href="css/contact-style.css">
</head>
<body>
    <div class="contact-container">
        <form method="post" id="contact-search" action="" enctype="text/plain">
            <div class="contact-info">
                <h3>Основная информация</h3>
                <label>
                    Фамилия
                    <input type="text" name="last-name">
                </label>
                <label>
                    Имя
                    <input type="text" name="first-name">
                </label>
                <label>
                    Отчество
                    <input type="text" name="middle-name">
                </label>
                <label>
                    Дата рождения
                    <input type="text" name="birth-date">
                </label>
                <label>
                    Пол
                    <select class="select-field" name="gender">
                        <option value="x">Не выбран</option>
                        <option value="m">Мужской</option>
                        <option value="f">Женский</option>
                    </select>
                </label>
                <label>
                    Гражданство
                    <input type="text" name="nationality">
                </label>
                <label>
                    Семейное положение
                    <select class="select-field" name="marital-status">
                        <option value="0">
                            Не выбрано
                        </option>
                        <c:forEach var="maritalStatus" items="${maritalStatuses}">
                            <option value="${maritalStatus.id}">
                                    ${maritalStatus.name}
                            </option>
                        </c:forEach>
                    </select>
                </label>
                <h3>Адрес</h3>
                <label>
                    Страна
                    <input type="text" name="country">
                </label>
                <label>
                    Город
                    <input type="text" name="city">
                </label>
                <label>
                    Улица
                    <input type="text" name="street">
                </label>
                <label>
                    Дом
                    <input type="text" name="house">
                </label>
                <label>
                    Квартира
                    <input type="text" name="flat">
                </label>
                <label>
                    Почтовый индекс
                    <input type="text" name="postcode">
                </label>
            </div>
            <input type="button" id="search-contact-button" value="Поиск">
        </form>
    </div>
</body>
</html>
