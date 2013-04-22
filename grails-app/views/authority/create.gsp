<%@ page import="de.donuz.wiki.Authority"%>
<!DOCTYPE html>
<html>
<head>
<meta name="layout" content="main">
<g:set var="entityName" value="${message(code: 'authority.label', default: 'Authority')}" />
<title><g:message code="default.create.label" args="[entityName]" /></title>
</head>
<body>
    <div id="create-authority" class="content scaffold-create" role="main">
        <h1>
            <g:message code="default.create.label" args="[entityName]" />
        </h1>
        <g:if test="${flash.message}">
            <div class="alert alert-info" role="status">${flash.message}</div>
        </g:if>
        <g:hasErrors bean="${authorityInstance}">
            <ul class="errors" role="alert">
                <g:eachError bean="${authorityInstance}" var="error">
                    <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message
                            error="${error}" /></li>
                </g:eachError>
            </ul>
        </g:hasErrors>
        <g:form action="save">
            <fieldset class="form">
                <g:render template="form" />
            </fieldset>
            <fieldset class="buttons">
                <g:link action="list" class="btn">
                    <i class="icon-arrow-left"> </i>
                    <g:message code="default.list.label" args="[entityName]" />
                </g:link>
                <g:submitButton name="create" class="btn btn-primary" value="${message(code: 'default.button.create.label', default: 'Create')}" />
            </fieldset>
        </g:form>
    </div>
</body>
</html>
