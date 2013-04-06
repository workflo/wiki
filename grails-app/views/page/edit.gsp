<%@ page import="de.donuz.wiki.Page"%>
<!DOCTYPE html>
<html>
<head>
<meta name="layout" content="main">
<title>${pageInstance?.title.encodeAsHTML()}</title>
<r:require modules="uploadr"/>
<r:require modules="editPage" />
</head>
<body>
    <div id="edit-page" class="content scaffold-edit" role="main">
        <g:if test="${flash.message}">
            <div class="message" role="status">${flash.message}</div>
        </g:if>
        <g:hasErrors bean="${pageInstance}">
            <ul class="errors" role="alert">
                <g:eachError bean="${pageInstance}" var="error">
                    <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message
                            error="${error}" /></li>
                </g:eachError>
            </ul>
        </g:hasErrors>

        <g:form method="post" class="">
            <g:hiddenField name="id" value="${pageInstance?.id}" />
            <g:hiddenField name="version" value="${pageInstance?.version}" />
            <g:render template="form" model="[isNew: false]" />
            <fieldset class="buttons">
                <g:actionSubmit class="save" action="update" value="Speichern" class="btn btn-success" />
                <g:link action="show" id="${pageInstance?.id}" class="btn">Abbrechen</g:link>
            </fieldset>
        </g:form>
        <g:render template="linkDialog" />
    </div>
</body>
</html>
