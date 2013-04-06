function EditPage(bodyId)
{
    this.bodyId = bodyId;
    this.jqBody = $('#' + bodyId);
    this._init();
};

EditPage.prototype._init = function()
{
	var jqBody = this.jqBody;
	
    $('#linkDialog').on('shown', function () {
        $('#linkDialog-searchField').focus();
  	});
    $('#linkDialog').on('hidden', function () {
        jqBody.focus();
  	});

  	jQuery('body').keypress(function (ev) {
    	if (ev.which == 91) {
            ev.preventDefault();
            
            $('#linkDialog-searchField').val('');
            $('#linkDialog-inputLinkUrl').val('');
            $('#linkDialog-inputLinkText').val('');
            $('#linkDialog-inputLinkTitle').val('');
            $('#linkDialog').modal({});
    	}
    	//console.log(ev)
    });
};

EditPage.prototype.insertInternalLink = function(id, title)
{
	this.jqBody.insertAtCaret('[' + title + '](' + id + ')');
};
	

EditPage.prototype.insertWebLink = function()
{
	var url = $('#linkDialog-inputLinkUrl').val();
	var text = $('#linkDialog-inputLinkText').val();
	var title = $('#linkDialog-inputLinkTitle').val();
	
	var text = '[' + text + '](' + url;
	if (title) {
		text += ' "' + title + '"';
	}
	text += ')';
	
	$('#linkDialog').modal('hide');
	this.jqBody.insertAtCaret(text);
	
	return false;
};


var editPage = new EditPage('bodyField');


$('#linkDialog-tabs a').click(function(e) {
	e.preventDefault();
	$(this).tab('show');
	$('#linkDialog-searchField').focus();
});

$('#linkDialog-tabs a').on('shown', function(e) {
	if (e.target) {
		if (e.target.hash == '#linkDialogTabInternal') {
			$('#linkDialog-searchField').focus();
		} else if (e.target.hash == '#linkDialogTabWeb') {
			$('#linkDialog-inputLinkUrl').focus();
		}
	}
});