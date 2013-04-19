<%@ page import="de.donuz.wiki.Page"%>
<!DOCTYPE html>
<html>
<head>
<meta name="layout" content="main">
<title>Neue Seite</title>
<r:require modules="uploadr"/>
<r:require modules="editPage" />
</head>
<body>
    <div id="create-page" class="content scaffold-create" role="main">
        <g:if test="${flash.message}">
            <bootstrap:alert class="alert-info">
                    ${flash.message}
                </bootstrap:alert>
        </g:if>

        <g:hasErrors bean="${pageInstance}">
            <ul class="errors" role="alert">
                <g:eachError bean="${pageInstance}" var="error">
                    <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message
                            error="${error}" /></li>
                </g:eachError>
            </ul>
        </g:hasErrors>
        <g:form action="update">
            <g:hiddenField name="id" value="${pageInstance?.id}" />
            <g:hiddenField name="version" value="${pageInstance?.version}" />
            <fieldset class="form">
                <g:render template="form" model="[isNew: true]"/>
            </fieldset>
            <fieldset class="buttons">
                <g:submitButton name="create" class="btn btn-success" value="Speichern" />
                <g:link uri="/" class="btn">Abbrechen</g:link>
            </fieldset>
        </g:form>
        <g:render template="linkDialog" />
        <g:render template="imageDialog" />
    </div>    
</body>
</html>
