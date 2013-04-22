
<%@ page import="de.donuz.wiki.Authority"%>
<!DOCTYPE html>
<html>
<head>
<meta name="layout" content="main">
<g:set var="entityName" value="${message(code: 'authority.label', default: 'Authority')}" />
<title><g:message code="default.list.label" args="[entityName]" /></title>
</head>
<body>
    <div id="list-authority" class="content scaffold-list" role="main">
        <h1>
            <g:message code="default.list.label" args="[entityName]" />
        </h1>
        <g:if test="${flash.message}">
            <div class="alert alert-info" role="status">${flash.message}</div>
        </g:if>

        <table class="table table-striped">
            <tbody>
                <g:each in="${authorityInstanceList}" status="i" var="authorityInstance">
                    <tr>
                        <td>${fieldValue(bean: authorityInstance, field: "authority")}</td>
                        <td><g:form style="margin: 0px;" method="post">
                                <g:hiddenField name="id" value="${authorityInstance?.id}" />
                                <g:actionSubmit class="btn btn-danger btn-small" action="delete" id="${authorityInstance?.id}"
                                    value="${message(code: 'default.button.delete.label', default: 'Delete')}"
                                    onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
                            </g:form></td>
                    </tr>
                </g:each>
            </tbody>
        </table>

        <div class="pagination">
            <g:paginate total="${authorityInstanceTotal}" />
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
