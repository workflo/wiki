
<li class="nav-header">Spaces</li>

<g:each in="${spaceInstances}">
    <li><g:link controller="s" action="${it.name}">${it.title.encodeAsHTML()}</g:link></li>
</g:each>