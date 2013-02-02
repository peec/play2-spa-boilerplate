define([
'jquery',
'underscore',
'backbone',
'marionette',
'handlebars',
'hbs!templates/auth/user_not_logged_in'
],
function ($, _, Backbone, Marionette, Handlebars, tmpl) {
	
	
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