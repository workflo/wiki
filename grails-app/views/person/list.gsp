
<%@ page import="de.donuz.wiki.Person"%>
<!DOCTYPE html>
<html>
<head>
<meta name="layout" content="main">
<g:set var="entityName" value="${message(code: 'person.label', default: 'Person')}" />
<title><g:message code="default.list.label" args="[entityName]" /></title>
</head>
<body>
    <div id="list-person" class="content scaffold-list" role="main">
        <h1>
            <g:message code="default.list.label" args="[entityName]" />
        </h1>
        <g:if test="${flash.message}">
            <div class="alert alert-info" role="status">${flash.message}</div>
        </g:if>
        <table class="table table-striped">
            <thead>
                <tr>
                    <g:sortableColumn property="username" title="${message(code: 'person.username.label', default: 'Username')}" />

                    <g:sortableColumn property="fullname" title="${message(code: 'person.fullname.label', default: 'Full name')}" />

                    <g:sortableColumn property="email" title="${message(code: 'person.accountLocked.label', default: 'Email')}" />
                </tr>
            </thead>
            <tbody>
                <g:each in="${personInstanceList}" status="i" var="personInstance">
                    <tr>
                        <td><g:link action="show" id="${personInstance.id}">${fieldValue(bean: personInstance, field: "username")}</g:link></td>

                        <td>${fieldValue(bean: personInstance, field: "fullname")}</td>

                        <td>${fieldValue(bean: personInstance, field: "email")}</td>
                    </tr>
                </g:each>
            </tbody>
        </table>
        <div class="pagination">
            <g:paginate total="${personInstanceTotal}" />
        </div>
        <div class="nav" role="navigation">
            <g:link action="create" class="btn btn-primary pull-right">
                <i class="icon-plus-sign icon-white"> </i>
                <g:message code="default.new.label" args="[entityName]" />
            </g:link>
        </div>
    </div>
</body>
</html>
