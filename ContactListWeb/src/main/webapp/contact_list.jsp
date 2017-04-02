<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>Контакты</title>
    <link rel="stylesheet" href="css/table-style.css">
    <link rel="stylesheet" href="css/nav-button-style.css">
</head>
<body>
    <div class="contact-list">
        <div class="nav-buttons">
            <a class="nav-button add" title="add" id="add-contact-button" href="controller?command=show_contact"></a>
            <a class="nav-button delete" title="delete" id="delete-contact-button"></a>
            <a class="nav-button search" title="search" id="search-contact-button"
               href="controller?command=show_contact_search_page"></a>
            <a class="nav-button mail" title="mail" id="send-mail-button"></a>
            <a class="nav-button home" title="home" id="home-button" href="controller?command=show_contact_list&page=1"></a>
        </div>
        <c:if test="${not empty pagination and not empty contacts}">
            <c:set var="firstPage" value="${1}"/>
            <c:set var="lastPage" value="${pagination.pageCount}"/>
            <c:set var="activePage" value="${pagination.activePage}"/>
            <div class="pagination">
                <ul>
                    <li>
                        <a href="controller?command=show_contact_list&page=${firstPage}">
                        <div class="page first-page">«</div>
                        </a>
                    </li>
                    <li>
                        <a href="controller?command=show_contact_list&page=${activePage <= 1 ? 1 : activePage - 1}">
                        <div class="page prev-page"><</div>
                        </a>
                    </li>
                    <c:forEach var="pageNumber" begin="${firstPage}" end="${activePage - 1}">
                        <li>
                            <a href="controller?command=show_contact_list&page=${pageNumber}">
                            <div class="page">
                                <c:out value="${pageNumber}"/>
                            </div>
                            </a>
                        </li>
                    </c:forEach>
                    <li>
                        <a href="controller?command=show_contact_list&page=${activePage}">
                            <div class="page active-page">
                                <c:out value="${activePage}"/>
                            </div>
                        </a>
                    </li>
                    <c:forEach var="pageNumber" begin="${activePage + 1}" end="${lastPage}">
                        <li>
                            <a href="controller?command=show_contact_list&page=${pageNumber}">
                                <div class="page">
                                    <c:out value="${pageNumber}"/>
                                </div>
                            </a>
                        </li>
                    </c:forEach>
                    <li>
                        <a href="controller?command=show_contact_list&page=${activePage >= lastPage ? lastPage : activePage + 1}">
                        <div class="page next-page">></div>
                        </a>
                    </li>
                    <li>
                        <a href="controller?command=show_contact_list&page=${lastPage}">
                        <div class="page last-page">»</div>
                        </a>
                    </li>
                </ul>
            </div>
        </c:if>
        <form id="contact-list-form" method="post">
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
                            <input type="checkbox" name="check-contact" id="check-contact-${contact.id}" value="${contact.id}">
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
                            <c:set var="address" value="${contact.address}"/>
                            <c:if test="${not empty address.country}">
                                <c:out value="${address.country} "/>
                            </c:if>
                            <c:if test="${not empty address.city}">
                                <c:out value="г. ${address.city} " />
                            </c:if>
                            <c:if test="${not empty address.street}">
                                <c:out value="ул. ${address.street} " />
                            </c:if>
                            <c:if test="${not empty address.houseNumber}">
                                <c:out value="д. ${address.houseNumber} " />
                            </c:if>
                            <c:if test="${not empty address.flatNumber}">
                                <c:out value="кв. ${address.flatNumber}" />
                            </c:if>
                        </td>
                        <td>
                            <c:out value="${contact.job}"/>
                        </td>
                    </tr>
                    </c:forEach>
                </tbody>
            </table>
        </form>
    </div>
    <script src="js/contact-list-script.js"></script>
</body>
</html>
