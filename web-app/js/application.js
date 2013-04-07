(function($) {
	$('#spinner').ajaxStart(function() {
		$(this).fadeIn();
	}).ajaxStop(function() {
		$(this).fadeOut();
	});

	// Auto-focus:
	jQuery('.focus').focus();
	jQuery('.select').select();
})(jQuery);

$.fn.extend({
	insertAtCaret : function(myValue) {
		var obj;
		if (typeof this[0].name != 'undefined')
			obj = this[0];
		else
			obj = this;

		if ($.browser.msie) {
			obj.focus();
			sel = document.selection.createRange();
			sel.text = myValue;
			obj.focus();
		} else if ($.browser.mozilla || $.browser.webkit) {
			var startPos = obj.selectionStart;
			var endPos = obj.selectionEnd;
			var scrollTop = obj.scrollTop;
			obj.value = obj.value.substring(0, startPos) + myValue
					+ obj.value.substring(endPos, obj.value.length);
			obj.focus();
			obj.selectionStart = startPos + myValue.length;
			obj.selectionEnd = startPos + myValue.length;
			obj.scrollTop = scrollTop;
		} else {
			obj.value += myValue;
			obj.focus();
		}
	},
	
	setCaretPosition : function(x) {
		var obj;
		if (typeof this[0].name != 'undefined')
			obj = this[0];
		else
			obj = this;

		if (obj.createTextRange) {
            var range = obj.createTextRange();
            range.move('character', x);
            range.select();
        } else {
            if (obj.selectionStart) {
                obj.focus();
                obj.setSelectionRange(x, x);
            }
            else
                obj.focus();
        }
	},
	
	getCaretPosition : function(x) {
		var obj;
		if (typeof this[0].name != 'undefined')
			obj = this[0];
		else
			obj = this;

		if (obj.createTextRange) {
//            var range = obj.createTextRange();
//            range.move('character', x);
//            range.select();
        } else {
         	return obj.selectionStart;
        }
	}
});