define([
'jquery',
'underscore',
'backbone',
'marionette',
'vent',
'tpl!templates/users/register.html',
'utils/bootstrapUtils',
'module/core/models/configuration',
'app'
],
function ($, _, Backbone, Marionette, vent, tmpl, bootstrapUtils, config, app) {
	
	
	var RegisterUserView = Backbone.Marionette.ItemView.extend({
		template: tmpl,
		initialize: function () {
			_.bindAll(this, 'success', 'error'); // Set context for success / error callbacks.
		},
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
				success: this.success,
				error: this.error
			});
		},
		success: function(model, resp){
			var that = this;
			
			if (config.get("auth").require_email_activation){
				app.routers.Users.navigate('user/signup/confirmation/' + this.model.get("id"), { trigger: true });
			} else {
				require(['tpl!templates/users/register_success.html'], function (tmpl) {
					that.template = tmpl;
					that.render();
				});
			}
		},
		error: function(model, resp){
			var msg = $.parseJSON(resp.responseText);
			bootstrapUtils.inputError(this.ui.email, msg.message || "Unknown error");
		}
	});
	
	
	return RegisterUserView;
});