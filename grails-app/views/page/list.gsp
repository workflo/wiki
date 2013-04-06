<%@ page import="de.donuz.wiki.Page"%>
<!DOCTYPE html>
<html>
<head>
<meta name="layout" content="main">
<g:set var="entityName" value="${message(code: 'page.label', default: 'Page')}" />
<title><g:message code="default.list.label" args="[entityName]" /></title>
</head>
<body>
    <div id="list-page" class="content scaffold-list" role="main">
        <h1>
            <g:message code="default.list.label" args="[entityName]" />
        </h1>
        <g:if test="${flash.message}">
            <div class="message" role="status">${flash.message}</div>
        </g:if>
        <table>
            <thead>
                <tr>
                    <g:sortableColumn property="title" title="${message(code: 'page.title.label', default: 'Title')}" />
                    <g:sortableColumn property="dateCreated" title="${message(code: 'page.dateCreated.label', default: 'Date Created')}" />
                    <g:sortableColumn property="lastUpdated" title="${message(code: 'page.lastUpdated.label', default: 'Last Updated')}" />
                </tr>
            </thead>
            <tbody>
                <g:each in="${pageInstanceList}" status="i" var="pageInstance">
                    <tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
                        <td><g:link action="show" id="${pageInstance.id}">${fieldValue(bean: pageInstance, field: "title")}</g:link></td>
                        <td><g:formatDate date="${pageInstance.dateCreated}" /></td>
                        <td><g:formatDate date="${pageInstance.lastUpdated}" /></td>
                    </tr>
                </g:each>
            </tbody>
        </table>
        <div class="pagination">
            <g:paginate total="${pageInstanceTotal}" />
        </div>
    </div>
</body>
</html>
