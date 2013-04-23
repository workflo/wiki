
<div id="imageDialog" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
        <h3>Bild einfügen</h3>
    </div>
    <div class="modal-body">
    
        <div class="image-selector">
        
            <!-- TODO: Max. Groesse aus Konfig; Typen einschraenken -->
        
        
            <uploadr:add name="fileupload" params="[page_id: pageInstance?.id]" controller="wikiUpload" action="handle" viewable="false" downloadable="false" deletable="false" direction="up" maxVisible="999" maxSize="${grailsApplication.config.wiki.attachments.maxSize}">
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
$(document).ready(function() {
    editPage = new EditPage("${pageInstance?.id}", "bodyField", {
    	imageGalleryUrl: "${createLink(controller: 'attachment', action: 'imageGallery')}",
    	deleteAttachmentUrl: "${createLink(controller: 'attachment', action: 'delete')}",
    	pageTitleAutoCompleteUrl: "${createLink(controller: 'autoComplete', action: 'pageByTitle')}"
    });
    editPage.init();
});
</script>
