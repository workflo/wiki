function SpacesList(options) {
	this.options = options;
	this.spacesList = $('#spacesList');
	this.addSpaceButton = $('#spacesList-addSpace-btn');
	this.addSpaceDialog = $('#spacesList-addSpace-dlg');
	this.addSpaceDialogNameField = $('#spacesList-addSpace-dlg-name');
	this.addSpaceDialogTitleField = $('#spacesList-addSpace-dlg-title');
};

SpacesList.prototype.init = function() {
	var self = this;
	this.addSpaceButton.click(function() {
		self.addSpaceDialog.modal({});
	});
	
	this.addSpaceDialog.on('shown', function() {
		self.addSpaceDialogNameField.val('');
		self.addSpaceDialogTitleField.val('');
		self.addSpaceDialogNameField.focus();
	});			
};

SpacesList.prototype.addSpace = function() {
	var self = this;
	var name = this.addSpaceDialogNameField.val();
	var title = this.addSpaceDialogTitleField.val();
	
	$.post(this.options.createSpaceUrl, {name: name, title: title}, function(res) {
		console.log(res);
		if (res && res.success === true) {
			self.addSpaceDialog.modal('hide');
			self.reloadSpacesList();
		}
	});
	
	return false;
};

SpacesList.prototype.reloadSpacesList = function() {
	this.spacesList.load(this.options.spacesListUrl);
};
