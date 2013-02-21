define([
'jquery',
'underscore',
'backbone',
'marionette',
'vent',
'tpl!templates/users/register.html',
'utils/bootstrapUtils'
],
function ($, _, Backbone, Marionette, vent, tmpl, bootstrapUtils) {
	
	
	var RegisterUserView = Backbone.Marionette.ItemView.extend({
		template: tmpl,
		ui: {
			email: '#rEmail',
			password: '#rPassword',
			confirmPassword: '#rConfirmPassword',
			registerForm: '#registerForm'
		},
		events: {
			'click #bCreateAccount': 'createAccount'
		},
		modelEvents: {
			'invalid': 'invalidModel'
		},
		invalidModel: function(model, error, options){
			// Clear errors first.
			bootstrapUtils.formErrorClear(this.ui.registerForm);
			
			var el;
			switch(error.type){
			case "email":
				el = this.ui.email;
				break;
			case "password":
				el = this.ui.password;
				break;
			case "passwordConfirm":
				el = this.ui.confirmPassword;
				break;
			}
			el.focus();
			
			bootstrapUtils.inputError(el, error.msg);
		},
		createAccount: function(ev){
			ev.preventDefault();
			var that = this;
			this.model.save({
				email: this.ui.email.val(),
				password: this.ui.password.val(),
				passwordConfirm: this.ui.confirmPassword.val()
			}, {
				success: function(model, resp){	
					bootstrapUtils.formErrorClear(that.ui.registerForm);
				},
				error: function(model, resp){
					var msg = $.parseJSON(resp.responseText);
					bootstrapUtils.inputError(that.ui.email, msg.message || "Unknown error");
				}
			});
		}
	});
	
	
	return RegisterUserView;
});