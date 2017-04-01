<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset="utf-8">
    <title>Отправка Email</title>
    <link rel="stylesheet" type="text/css" href="css/contact-style.css">
    <link rel="stylesheet" type="text/css" href="css/email-page-style.css">
</head>
<body>
    <div class="email-container">
        <form method="post" id="send-email" action="">
            <div class="email-info">
                <label>
                    Получатели
                </label>
                <label>
                    Тема
                    <input name="email-subject" type="text" id="email-subject">
                </label>
                <label>
                    Шаблон
                    <select name="email-template" id="email-template">
                        <option>Не выбран</option>
                    </select>
                </label>
                <label>
                    Текст
                    <textarea name="email-text" id="email-text" rows="7" maxlength="254"></textarea>
                </label>
                <input type="submit" class="button-style" id="send-email-button" value="Отправить">
                <input type="button" class="button-style" id="back-button" value="Назад">
            </div>
        </form>
    </div>
    <script src="js/back-button-script.js"></script>
</body>
</html>
