define([
'jquery',
'underscore',
'backbone',
'marionette',
'tpl!templates/homepage/index.html',
'utils/S3Upload',
'userSession',
'vent'
],
function ($, _, Backbone, Marionette, tmpl, S3Upload, userSession, vent) {
	
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