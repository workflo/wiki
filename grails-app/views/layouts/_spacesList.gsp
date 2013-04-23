
<li class="nav-header">Spaces</li>

<g:each in="${de.donuz.wiki.Space.getVisibleSpaces()}">
    <li><g:link controller="s" action="${it.name}">${it.title.encodeAsHTML()}</g:link></li>
</g:each>