define([
'jquery',
'underscore',
'backbone',
'marionette',
'tpl!templates/homepage/index.html',
'utils/S3Upload',
'userSession',
'vent',
'commands'
],
function ($, _, Backbone, Marionette, tmpl, S3Upload, userSession, vent, commands) {
	
	var View = Backbone.Marionette.Layout.extend({
		template: tmpl,
		initialize: function(){
			// Bind .userOnly + .guestOnly
			commands.execute('core:auth:gui:change', this);
		},
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