
<div id="imageDialog" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
        <h3>Bild einfügen</h3>
    </div>
    <div class="modal-body">
            <div class="tab-pane" style="height: 300px;">
                <uploadr:add name="fileupload" params="[page_id: pageInstance?.id]" controller="wikiUpload" action="handle" viewable="false" downloadable="false" deletable="false" direction="up" maxVisible="999" maxSize="33554432">
                    <uploadr:onSuccess>
                        callback(); 
                    </uploadr:onSuccess>
                </uploadr:add>
            </div>
    </div>
</div>

<script language="JavaScript">
</script>
