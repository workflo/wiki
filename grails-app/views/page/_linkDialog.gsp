
<div id="linkDialog" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
        <h3>Link einfügen</h3>
    </div>
    <div class="modal-body">
        <ul class="nav nav-tabs" id="linkDialog-tabs">
            <li class="active"><a href="#linkDialogTabInternal">Wikiseite</a></li>
            <li><a href="#linkDialogTabWeb">Weblink</a></li>
        </ul>

        <div class="tab-content">
            <div class="tab-pane active" id="linkDialogTabInternal">
                <input type="text" name="q" id="linkDialog-searchField" class="input-block-level" placeholder="Seitentitel..." />
            </div>
            <div class="tab-pane" id="linkDialogTabWeb">
                <form class="form-horizontal" onsubmit="return editPage.insertWebLink()">
                    <div class="control-group">
                        <label class="control-label" for="linkDialog-inputLinkUrl">Adresse</label>
                        <div class="controls">
                            <input type="text" class="input-xlarge" id="linkDialog-inputLinkUrl" placeholder="http://...">
                        </div>
                    </div>
                    <div class="control-group">
                        <label class="control-label" for="linkDialog-inputLinkText">Linktext</label>
                        <div class="controls">
                            <input type="text" class="input-xlarge" id="linkDialog-inputLinkText" placeholder="Text auf dem Link">
                        </div>
                    </div>
                    <div class="control-group">
                        <label class="control-label" for="linkDialog-inputLinkTitle">Tooltip</label>
                        <div class="controls">
                            <input type="text" class="input-xlarge" id="linkDialog-inputLinkTitle" placeholder="Tooltip (optional)">
                        </div>
                    </div>
                    <div class="control-group">
                        <div class="controls">
                            <button class="btn btn-primary" type="submit">Einfügen</button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

<script language="JavaScript">
	$("#linkDialog-searchField")
			.autocomplete(
					{
						minLength : 2,
						source : "${createLink(controller: 'autoComplete', action: 'pageByTitle')}",
						focus : function(event, ui) {
							$("#linkDialog-searchField").val(ui.item.label);
							return false;
						},
						select : function(event, ui) {
							$('#linkDialog').modal('hide');
							editPage.insertInternalLink(ui.item.value,
									ui.item.label);
							return false;
						}
					})
</script>
