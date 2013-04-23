
<%@ page import="de.donuz.wiki.Page"%>
<!DOCTYPE html>
<html>
<head>
<meta name="layout" content="main">
<title>${pageInstance?.title.encodeAsHTML()}</title>
</head>
<body>
    <div id="show-page" class="content scaffold-show" role="main">
        <g:if test="${flash.message}">
            <div class="alert alert-info" role="status">${flash.message}</div>
        </g:if>

        <h1>${pageInstance?.title.encodeAsHTML()}</h1>
        <g:if test="${pageInstance?.body}">
            <div class="markdown">
                <markdown:renderHtml text="${pageInstance?.body}"/>
            </div>
        </g:if>

        <sec:ifLoggedIn>
            <hr />
    
            <g:form>
                <fieldset class="buttons">
                    <g:hiddenField name="id" value="${pageInstance?.id}" />
                    <g:link class="edit" action="edit" id="${pageInstance?.id}" class="btn">
                        <g:message code="default.button.edit.label" default="Edit" />
                    </g:link>
                    <g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}"
                        onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" class="btn btn-danger pull-right" />
                </fieldset>
            </g:form>
        </sec:ifLoggedIn>

        <!--
        <div class="btn-group">
            <button class="btn dropdown-toggle" data-toggle="dropdown">
                <i class="icon-cog "></i> <span class="caret"></span>
            </button>
            <ul class="dropdown-menu">
                <li><a href="#"><i class="icon-pencil"></i> Edit</a></li>
                <li><a href="#"><i class="icon-trash"></i> Delete</a></li>
                <li><a href="#"><i class="icon-ban-circle"></i> Ban</a></li>
            </ul>
        </div>
    -->
    </div>
    <script language="JavaScript">
    jQuery('body').keypress(function (ev) {
        if (!$('input').is(":focus")) { 
            if (ev.which == 101) {
    	        window.location.href="${createLink(controller: 'page', action: 'edit', id: pageInstance?.id)}";
                event.preventDefault();
            } else if (ev.which == 99) {
                window.location.href="${createLink(controller: 'page', action: 'create', params: [parent: pageInstance?.id])}";
                event.preventDefault();
            }
    	}
    	//console.log(ev)
    });
    </script>
</body>
</html>
