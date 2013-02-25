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
		template: tmpl,
		ui: {
			email: '#fEmail'
		},
		events: {
			'click #bForgotPassword': 'submit'
		},
		submit: function(ev){
			ev.preventDefault();
			var email = this.ui.email.val();
			this.model.save({email: email}, {
				success: function(model, resp){
					
				},
				error: function(model, resp){
					
				}
			});
		}
	});
	
	
	return ForgotPasswordStep1;
});