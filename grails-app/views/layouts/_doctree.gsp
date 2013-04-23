<%@page import="de.donuz.wiki.*"%>

<g:if test="${spaceInstance}">
    <div class="well sidebar-nav">
        <ul class="nav nav-list">
            <li class="nav-header">Seiten</li>
            <g:each in="${spaceInstance.getTopPages()}">
                <li><g:link controller="page" action="show" id="${it.id}">${it.title.encodeAsHTML()}</g:link></li>
            </g:each>
        </ul>
    </div>
</g:if>

