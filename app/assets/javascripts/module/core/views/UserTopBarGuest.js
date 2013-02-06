define([
'jquery',
'underscore',
'backbone',
'marionette',
'tpl!templates/auth/user_not_logged_in.html'
],
function ($, _, Backbone, Marionette, tmpl) {
	
	
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
			this.model.login(this.ui.username.val(), this.ui.password.val(), function(){
				
			}, function(){
				
			});
		}
	});
	
	
	return UserTopBarGuest;
});