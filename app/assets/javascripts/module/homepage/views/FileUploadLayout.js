define([
'jquery',
'underscore',
'backbone',
'marionette',
'tpl!templates/homepage/file_uploader_layout.html',
'utils/S3Upload',
'userSession',
'vent',
'commands'
],
function ($, _, Backbone, Marionette, tmpl, S3Upload, userSession, vent, commands) {
	
	var View = Backbone.Marionette.Layout.extend({
		template: tmpl,
		regions: {
			'content': '.content'
		}
	});
	
	
	return View;
});