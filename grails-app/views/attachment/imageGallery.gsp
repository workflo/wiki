<g:each in="${images}">
    <div class="entry" data-toggle="tooltip" title="${it.name.encodeAsHTML()}" data-image-name="${it.name.encodeAsHTML()}">
        <div class="image"><img src="${createLink(uri: "/a/${pageInstance.id}/${it.name.encodeAsHTML()}?size=140x120")}" border="0"/></div>
        <div class="image-name">${it.name.encodeAsHTML()}</div>
    </div>
</g:each>