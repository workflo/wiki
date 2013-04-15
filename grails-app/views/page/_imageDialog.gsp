
<div id="imageDialog" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
        <h3>Bild einfügen</h3>
    </div>
    <div class="modal-body">
    
        <div class="image-selector">
        
            <uploadr:add name="fileupload" params="[page_id: pageInstance?.id]" controller="wikiUpload" action="handle" viewable="false" downloadable="false" deletable="false" direction="up" maxVisible="999" maxSize="33554432">
                <uploadr:onSuccess>
                    callback();
                    editPage.reloadImageGallery(); 
                </uploadr:onSuccess>
            </uploadr:add>
            
            <div id="imageDialog-gallery">
            </div>
        </div>
    </div>
</div>

<script language="JavaScript">
(function() {
    editPage = new EditPage("${pageInstance?.id}", "bodyField", {
    	imageGalleryUrl: "${createLink(controller: 'attachment', action: 'imageGallery')}"
    });
    editPage.init();
})();
</script>
