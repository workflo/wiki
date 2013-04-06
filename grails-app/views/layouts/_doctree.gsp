<%@page import="de.donuz.wiki.*"%>

<div class="well sidebar-nav">
    <ul class="nav nav-list">
        <li class="nav-header">Seiten</li>
        <g:each in="${Page.getTopLevelPages()}">
            <li><g:link controller="page" action="show" id="${it.id}">${it.title.encodeAsHTML()}</g:link></li>
        </g:each>
    </ul>
</div>
<!--/.well -->


