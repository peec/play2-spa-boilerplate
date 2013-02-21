define([
'jquery',
'underscore',
'backbone',
'marionette',
'vent',
'tpl!templates/users/register.html'
],
function ($, _, Backbone, Marionette, vent, tmpl) {
	
	
	var RegisterUserView = Backbone.Marionette.ItemView.extend({
		template: tmpl,
		ui: {
			email: '#rEmail',
			password: '#rPassword',
			confirmPassword: '#rConfirmPassword'
		},
		events: {
			'click #bCreateAccount': 'createAccount'
		},
		createAccount: function(ev){
			ev.preventDefault();
			console.log("TODO with ", 
					this.ui.email.val(), 
					this.ui.password.val(), 
					this.ui.confirmPassword.val());
		}
	});
	
	
	return RegisterUserView;
});