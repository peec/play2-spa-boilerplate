define([
'jquery',
'underscore',
'backbone',
'marionette',
'vent',
'tpl!templates/users/forgot_password_step2.html',
'utils/bootstrapUtils'
],
function ($, _, Backbone, Marionette, vent, tmpl, bootstrapUtils) {
	
	
	var ForgotPasswordStep2 = Backbone.Marionette.ItemView.extend({
		template: tmpl,
		ui: {
			password: '#nPassword',
			passwordConfirm: '#nConfirmPassword'
		},
		events: {
			'click #bSendNewPassword': 'sendNewPw'
		},
		sendNewPw: function (ev) {
			ev.preventDefault();
			this.model.save({password: this.ui.password.val()}, {
				success: function (model, resp) {
					console.log("New password is set.");
				},
				error: function (model, resp) {
					// TODO
					console.log("Error.");
				}
			});
		}
	});
	
	
	return ForgotPasswordStep2;
});