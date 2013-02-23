define([
'jquery',
'underscore',
'backbone',
'marionette',
'vent',
'tpl!templates/users/register_success_activate.html',
'utils/bootstrapUtils',
'module/core/models/configuration'
],
function ($, _, Backbone, Marionette, vent, tmpl, bootstrapUtils, config) {
	
	
	var RegisterUserConfirmationView = Backbone.Marionette.ItemView.extend({
		template: tmpl,
		initialize: function () {
			_.bindAll(this, 'success', 'error');
		},
		events: {
			'click .resendEmail': 'sendEmail'
		},
		sendEmail: function(ev){
			ev.preventDefault();
			this.model.save({}, {
				success: this.success,
				error: this.error
			});
		},
		success: function (model, resp) {
			console.log("Resend key");
		},
		error: function (model, resp) {
			console.log("Resend key error");
		}
	});
	
	
	return RegisterUserConfirmationView;
});