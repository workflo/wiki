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
		var elem = this[0];
		if (elem == null) return;
		
		if ($.browser.msie) {
			elem.focus();
			sel = document.selection.createRange();
			sel.text = myValue;
			elem.focus();
		} else if ($.browser.mozilla || $.browser.webkit) {
			var startPos = elem.selectionStart;
			var endPos = elem.selectionEnd;
			var scrollTop = elem.scrollTop;
			elem.value = elem.value.substring(0, startPos) + myValue
					+ elem.value.substring(endPos, elem.value.length);
			elem.focus();
			elem.selectionStart = startPos + myValue.length;
			elem.selectionEnd = startPos + myValue.length;
			elem.scrollTop = scrollTop;
		} else {
			elem.value += myValue;
			elem.focus();
		}
	},
	
	encloseBy : function(left, right) {
		var elem = this[0];
		if (elem == null) return;

		if (elem.setRangeText) {
			var startPos = elem.selectionStart;
			var endPos = elem.selectionEnd;
			var selection = elem.value.substring(startPos, endPos);

			elem.setRangeText(left + selection + right);
			if (startPos == endPos) {
				elem.setSelectionRange(startPos + left.length, startPos + left.length);
			}
		}		
	},
	
	setCaretPosition : function(x) {
		var elem = this[0];
		if (elem == null) return;

		if (elem.createTextRange) {
            var range = elem.createTextRange();
            range.move('character', x);
            range.select();
        } else {
            if (elem.selectionStart) {
            	elem.focus();
                elem.setSelectionRange(x, x);
            } else {
            	elem.focus();
            }
        }
	},
	
	getCaretPosition : function(x) {
		var elem = this[0];
		if (elem == null) return;

		if (elem.createTextRange) {
//            var range = elem.createTextRange();
//            range.move('character', x);
//            range.select();
        } else {
         	return elem.selectionStart;
        }
	}
});