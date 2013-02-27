define([
'jquery',
'underscore',
'backbone',
'marionette',
'vent',
'tpl!templates/users/user_cp.html',
'utils/bootstrapUtils',
'userSession'
],
function ($, _, Backbone, Marionette, vent, tmpl, bootstrapUtils, userSession) {
	
	
	var UserCPView = Backbone.Marionette.ItemView.extend({
		template: tmpl,
		initialize: function () {
			_.bindAll(this, 'success', 'error'); // Set context for success / error callbacks.
		},
		ui: {
			password: '#ePassword',
			confirmPassword: '#eConfirmPassword',
			currentPassword: '#eCurrentPassword',
			usercpForm: '#usercpForm',
			email: '#eEmail'
		},
		events: {
			'click #bSaveProfile': 'saveProfile',
			'click .regretEmailChange': 'regretEmailChange'
		},
		modelEvents: {
			'invalid': 'invalidModel'
		},
		invalidModel: function(model, error, options){
			// Clear errors first.
			bootstrapUtils.formErrorClear(this.ui.usercpForm);
			
			var el;
			switch(error.type){
			case "confirmPassword":
				el = this.ui.confirmPassword;
				break;
			}
			
			el.focus();
			bootstrapUtils.inputError(el, error.msg);
		},
		saveProfile: function (ev) {
			ev.preventDefault();
			
			var values = {};
			
			if (this.ui.password.val()) {
				values.password = this.ui.password.val();
				values.confirmPassword = this.ui.confirmPassword.val();
				values.currentPassword = this.ui.currentPassword.val();
			}
			
			var email = this.model.get("email");
			values.email = this.ui.email.val();
			
			
			this.model.save(values,{
				success: this.success,
				error: this.error
			});
		},
		success: function (model, resp) {
			bootstrapUtils.formErrorClear(this.ui.usercpForm);
			userSession.set(model.attributes); // Update user session.
			this.render();
		},
		error: function (model, resp) {
			var obj = $.parseJSON(resp.responseText);
			bootstrapUtils.inputError(this.ui.currentPassword, obj.message);
		},
		regretEmailChange: function (ev) {
			ev.preventDefault();
			this.model.unset("emailChange");
			this.saveProfile(ev);
		}
	});
	
	
	return UserCPView;
});