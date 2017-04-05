<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Поиск контактов</title>
    <link rel="stylesheet" href="css/contact-style.css">
    <link rel="stylesheet" href="css/popup-window-style.css">
</head>
<body>
    <div class="contact-container">
        <form method="post" id="contact-search" action="controller?command=search_contacts">
            <div class="contact-info">
                <h3>Основная информация</h3>
                <label>
                    Фамилия
                    <input type="text" id="last-name" name="last-name">
                </label>
                <label>
                    Имя
                    <input type="text" id="first-name" name="first-name">
                </label>
                <label>
                    Отчество
                    <input type="text" id="middle-name" name="middle-name">
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
                    <input type="text" id="nationality" name="nationality">
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
                <h3>Дата рождения</h3>
                <label>
                    Тип поиска
                    <select class="select-field" name="date-params">
                        <option value="equals">Точная дата</option>
                        <option value="younger">Младше</option>
                        <option value="older">Старше</option>
                    </select>
                </label>
                <label>
                    Дата рождения
                    <input type="text" id="year" class="birth-date" name="birth-date-year" placeholder="Год">
                    <input type="text" id="month" class="birth-date" name="birth-date-month" placeholder="Месяц">
                    <input type="text" id="day" class="birth-date" name="birth-date-day" placeholder="День">
                </label>
                <h3>Адрес</h3>
                <label>
                    Страна
                    <input type="text" id="country" name="country">
                </label>
                <label>
                    Город
                    <input type="text" id="city" name="city">
                </label>
                <label>
                    Улица
                    <input type="text" id="street" name="street">
                </label>
                <label>
                    Дом
                    <input type="text" id="house" name="house">
                </label>
                <label>
                    Квартира
                    <input type="text" id="flat" name="flat">
                </label>
                <label>
                    Почтовый индекс
                    <input type="text" id="postcode" name="postcode">
                </label>
            </div>
            <input type="button" class="button-style" id="search-contact-button" value="Поиск">
            <input type="button" class="button-style" id="back-button" value="Назад">
        </form>
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
    <script src="js/contact-search-script.js"></script>
</body>
</html>
