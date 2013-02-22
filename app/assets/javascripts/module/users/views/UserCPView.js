define([
'jquery',
'underscore',
'backbone',
'marionette',
'vent',
'tpl!templates/users/user_cp.html',
'utils/bootstrapUtils'
],
function ($, _, Backbone, Marionette, vent, tmpl, bootstrapUtils) {
	
	
	var UserCPView = Backbone.Marionette.ItemView.extend({
		template: tmpl
	});
	
	
	return UserCPView;
});