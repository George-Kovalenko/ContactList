<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>Контакты</title>
    <link type="text/css" rel="stylesheet" href="css/table-style.css">
</head>
<body>
    <div class="contact-list">
        <table>
            <col class="column-1">
            <col class="column-2">
            <col class="column-3">
            <col class="column-4">
            <col class="column-5">
            <thead>
            <tr>
                <th></th>
                <th>Полное имя</th>
                <th>Дата рождения</th>
                <th>Адрес</th>
                <th>Место работы</th>
            </tr>
            </thead>
            <tbody>
                <c:forEach var="contact" items="${contacts}">
                <tr>
                    <td>
                        <input type="checkbox" name="check-${contact.id}" id="${contact.id}" value="${contact.id}">
                    </td>
                    <td>
                        <a href="controller?command=show_contact&contact_id=${contact.id}">
                            <c:out value="${contact.lastName} ${contact.firstName} ${contact.middleName}"/>
                        </a>
                    </td>
                    <td>
                        <c:out value="${contact.birthDate}"/>
                    </td>
                    <td>
                        <c:out value="${contact.address.country}, г.
                                      ${contact.address.city}, ул.
                                      ${contact.address.street}, д.
                                      ${contact.address.houseNumber}, кв.
                                      ${contact.address.flatNumber}"/>
                    </td>
                    <td>
                        <c:out value="${contact.job}"/>
                    </td>
                </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</body>
</html>
