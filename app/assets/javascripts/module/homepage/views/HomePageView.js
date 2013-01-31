define([
'jquery',
'underscore',
'backbone',
'marionette',
'handlebars',
'text!templates/homepage/index.html'
],
function ($, _, Backbone, Marionette, Handlebars, tmpl) {
	
	var View = Backbone.Marionette.Layout.extend({
		template: tmpl,
		ui: {
			clickableHide: '#clickToHide'
		},
		events: {
			'click  #clickToHide': 'hideDiv'
		},
		hideDiv: function(){
			this.ui.clickableHide.hide();
		}
	});
	
	
	return new View();
});