define([
'jquery',
'underscore',
'backbone',
'marionette',
'utils/bootstrapUtils',
'tpl!templates/core/user_not_logged_in.html'
],
function ($, _, Backbone, Marionette, bootstrapUtils, tmpl) {
	
	
	var UserTopBarGuest = Backbone.Marionette.ItemView.extend({
		template: tmpl,
		ui: {
			username: '#username',
			password: '#password'
		},
		events: {
			'click #bLogin': 'login'
		},
		login: function(e) {
			e.preventDefault();
			var that = this;
			
			this.model.login(this.ui.username.val(), this.ui.password.val(), function(){
				bootstrapUtils.inputErrorClear(that.ui.username);
				bootstrapUtils.inputErrorClear(that.ui.password);
			}, function(model, resp){
				var msg = $.parseJSON(resp.responseText);
				bootstrapUtils.inputError(that.ui.username);
				bootstrapUtils.inputError(that.ui.password, msg.message || "Unknown error");
			});
		}
	});
	
	
	return UserTopBarGuest;
});