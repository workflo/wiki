
<div class="well sidebar-nav">
    <ul class="nav nav-list" id="spacesList">
        <g:render template="/space/list" model="[spaceInstances: de.donuz.wiki.Space.getVisibleSpaces()]"/>
    </ul>
    <sec:ifAllGranted roles="ROLE_ADMINISTRATORS">
        <button class="btn btn-small" id="spacesList-addSpace-btn">
            <i class="icon-plus-sign"></i>
        </button>
    </sec:ifAllGranted>
</div>


<div id="spacesList-addSpace-dlg" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
        <h3>Space anlegen</h3>
    </div>
    <div class="modal-body">    
        <form class="form-horizontal" onsubmit="return spacesList.addSpace()">
            <div class="control-group">
                <label class="control-label" for="spacesList-addSpace-dlg-name">URL-Name</label>
                <div class="controls">
                    <input type="text" class="input-xlarge" id="spacesList-addSpace-dlg-name" placeholder="url-name">
                </div>
            </div>
            <div class="control-group">
                <label class="control-label" for="spacesList-addSpace-dlg-title">Titel</label>
                <div class="controls">
                    <input type="text" class="input-xlarge" id=spacesList-addSpace-dlg-title placeholder="Titel">
                </div>
            </div>
            <div class="control-group">
                <div class="controls">
                    <button class="btn btn-primary" type="submit">Anlegen</button>
                </div>
            </div>
        </form>
    </div>
</div>


<script language="JavaScript">
$(document).ready(function() {
    spacesList = new SpacesList({
        createSpaceUrl: "${createLink(controller: 'space', action: 'ajaxCreate')}",
        spacesListUrl: "${createLink(controller: 'space', action: 'list')}",
        deleteSpaceUrl: "${createLink(controller: 'space', action: 'ajaxDelete')}"
    });
    spacesList.init();
});
</script>
