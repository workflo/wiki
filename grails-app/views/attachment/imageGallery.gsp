<g:each in="${images}">
    <div class="image" data-toggle="tooltip" title="tooltip" data-image-name="${it.name.encodeAsHTML()}">
        <img title="${it.name.encodeAsHTML()}" src="${createLink(uri: "/a/${pageInstance.id}/${it.name.encodeAsHTML()}")}" />
    </div>
</g:each>