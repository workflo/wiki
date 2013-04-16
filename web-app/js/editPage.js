function EditPage(pageId, bodyId, options)
{
	this.pageId = pageId;
    this.bodyId = bodyId;
    this.jqBody = $('#' + bodyId);
    this.options = options;
};

EditPage.prototype.init = function()
{
	var self = this;
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
            self.openLinkDialog();
    	}
    });
  	
  	$('#' + this.bodyId + '-btnH1').click(function() {
  		jqBody.encloseBy('# ', '');
  	});
  	
  	$('#' + this.bodyId + '-btnH2').click(function() {
  		jqBody.encloseBy('## ', '');
  	});
  	
  	$('#' + this.bodyId + '-btnH3').click(function() {
  		jqBody.encloseBy('### ', '');
  	});
  	
  	$('#' + this.bodyId + '-btnBold').click(function() {
  		jqBody.encloseBy('**', '**');
  	});
  	
  	$('#' + this.bodyId + '-btnItalic').click(function() {
  		jqBody.encloseBy('*', '*');
  	});
  	
  	$('#' + this.bodyId + '-btnQuote').click(function() {
  		jqBody.encloseBy('> ', '');
  	});
  	
  	$('#' + this.bodyId + '-btnCode').click(function() {
  		jqBody.encloseBy('`', '`');
  	});

  	$('#' + this.bodyId + '-btnList').click(function() {
  		jqBody.encloseBy('* ', '');
  	});

  	$('#' + this.bodyId + '-btnNumberedList').click(function() {
  		jqBody.encloseBy('1. ', '');
  	});

  	$('#' + this.bodyId + '-btnLink').click(function() {
  		self.openLinkDialog();
  	});

  	$('#' + this.bodyId + '-btnImage').click(function() {
  		self.openImageDialog();
  	});
};


EditPage.prototype.openLinkDialog = function()
{
    $('#linkDialog-searchField').val('');
    $('#linkDialog-inputLinkUrl').val('');
    $('#linkDialog-inputLinkText').val('');
    $('#linkDialog-inputLinkTitle').val('');
    $('#linkDialog').modal({});
};


EditPage.prototype.openImageDialog = function()
{
	this.reloadImageGallery();
    $('#imageDialog').modal({});
};


EditPage.prototype.reloadImageGallery = function()
{
	var self = this;
	
	$('#imageDialog-gallery').load(this.options.imageGalleryUrl, {pageId: this.pageId, ts: new Date().getTime()}, function() {
		$('#imageDialog-gallery .entry').each(function(index, el) {
			var name = el.getAttribute('data-image-name');
			
			$(el).mouseenter(function() {
				$('.image-button', this).show();
			});
			$(el).mouseleave(function() {
				$('.image-button', this).hide();
			});
			$('.image', el).click(function() {
				self.jqBody.insertAtCaret('![' + name + '](' + self.pageId + '/' + name + ')');		
				$('#imageDialog').modal('hide');
			});
			$('.btn-delete', el).click(function(ev) {
				self.deleteAttachment(name);
				ev.preventDefault();
			});
			$('.btn-zoom', el).click(function(ev) {
				console.log('ZOOM ' + name);
				ev.preventDefault();
			});
		});
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


EditPage.prototype.deleteAttachment = function(name)
{
	var self = this;

	if (confirm('Attachment "' + name + '" wirklich löschen?')) {
		$.post(this.options.deleteAttachmentUrl, {pageId: this.pageId, filename: name}, function(a,b,c) {
			self.reloadImageGallery();
		});
	}
};


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
