define([
'jquery',
'underscore',
'backbone',
'marionette',
'vent',
'tpl!templates/users/forgot_password_step1.html',
'utils/bootstrapUtils'
],
function ($, _, Backbone, Marionette, vent, tmpl, bootstrapUtils) {
	
	
	var ForgotPasswordStep1 = Backbone.Marionette.ItemView.extend({
		template: tmpl
	});
	
	
	return ForgotPasswordStep1;
});