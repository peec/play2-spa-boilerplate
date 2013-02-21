define([
'jquery'
],
function ($) {
	
	
	return {
		/**
		 * @param el The input element wrapped in a .control-group .controls.
		 * @param message Optional error message.
		 */
		inputError: function(el, message){
			var controlGroup = el.closest('.control-group');
			var controls = el.closest('.controls');
			var helpBlock = controls.find('.help-block');
			
			controlGroup.addClass('error');
			
			if (typeof message !== 'undefined'){
				if (helpBlock.length === 0){
					helpBlock = $('<span class="help-block"></span>');
					controls.append(helpBlock);
				} else {
					helpBlock.data('original-message', helpBlock.text());
				}
				helpBlock.text(message);
			}
		},
		/**
		 * Clears errors set from inputError method.
		 */
		inputErrorClear: function(el){
			var controlGroup = el.closest('.control-group');
			var controls = el.closest('.controls');
			var helpBlock = controls.find('.help-block');
			
			controlGroup.removeClass('error');
			if (helpBlock.length > 0){
				if ( helpBlock.data('original-message')){
					helpBlock.text(helpBlock.data('original-message'));
					helpBlock.removeData('original-message');
				} else {
					helpBlock.remove();
				}
			}
		}
	}
});