define([
'jquery',
'underscore',
'backbone',
'marionette',
'vent',
'tpl!templates/users/email_confirm_change.html',
'utils/bootstrapUtils',
'userSession'
],
function ($, _, Backbone, Marionette, vent, tmpl, bootstrapUtils, userSession) {
	
	
	var EmailConfirmChangeView = Backbone.Marionette.ItemView.extend({
		template: tmpl,
		initialize: function () {
			_.bindAll(this, 'success', 'error');
		},
		confirmChange: function () {
			this.model.save({}, {
				success: this.success,
				error: this.error
			});
		},
		success: function (model, resp) {
			userSession.set("email", model.get("email"));
			console.log("Email changed.");
		},
		error: function (model, resp) {
			console.log("Error.");
		}
	});
	
	
	return EmailConfirmChangeView;
});