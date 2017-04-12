<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset="utf-8">
    <title>Отправка Email</title>
    <link rel="stylesheet" href="css/contact-style.css">
    <link rel="stylesheet" href="css/email-page-style.css">
    <link rel="stylesheet" href="css/popup-window-style.css">
</head>
<body>
    <div class="email-container">
        <form method="post" id="send-email" action="controller?command=send_email">
            <div class="email-info">
                <label>
                    Получатели
                    <c:if test="${not empty recipients}">
                        <ul>
                            <c:forEach var="recipient" items="${recipients}">
                                <li>
                                    <c:out value="${recipient.email}"/>
                                </li>
                                <input type="hidden" value="${recipient.id}" name="recipient-id">
                            </c:forEach>
                        </ul>
                    </c:if>
                </label>
                <label>
                    Тема
                    <input name="email-subject" type="text" id="email-subject">
                </label>
                <label>
                    Шаблон
                    <select name="email-template" id="email-template">
                        <option value="">Не выбран</option>
                        <option value="birthday">День рождения</option>
                        <option value="newYear">Новый год</option>
                    </select>
                </label>
                <c:forEach var="template" items="${templates}">
                    <input type="hidden" value="${template.value}" id="${template.key}">
                </c:forEach>
                <label>
                    Текст
                    <textarea name="email-text" id="email-text" rows="7"></textarea>
                </label>
                <input type="button" class="button-style" id="send-email-button" value="Отправить">
                <input type="button" class="button-style" id="back-button" value="Назад">
            </div>
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
    <script src="js/email-page-script.js"></script>
</body>
</html>
