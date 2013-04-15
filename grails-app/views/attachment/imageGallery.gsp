<g:each in="${images}">
    <div class="image" data-toggle="tooltip" title="${it.name.encodeAsHTML()}" data-image-name="${it.name.encodeAsHTML()}">
        <img src="${createLink(uri: "/a/${pageInstance.id}/${it.name.encodeAsHTML()}?size=120x120")}" border="0"/>
    </div>
</g:each>