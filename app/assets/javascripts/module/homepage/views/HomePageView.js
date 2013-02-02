define([
'jquery',
'underscore',
'backbone',
'marionette',
'handlebars',
'hbs!templates/homepage/index'
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
	
	
	return View;
});