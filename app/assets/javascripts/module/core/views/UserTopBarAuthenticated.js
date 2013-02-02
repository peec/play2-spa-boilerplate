define([
'jquery',
'underscore',
'backbone',
'marionette',
'handlebars',
'hbs!templates/auth/user_logged_in_top'
],
function ($, _, Backbone, Marionette, Handlebars, tmpl) {
	
	
	var UserTopBarAuthenticated = Backbone.Marionette.ItemView.extend({
		template: tmpl,
		events: {
			'click #bLogout': 'logout'
		},
		logout: function(e) {
			e.preventDefault();
			this.model.logout(function(){
				
			}, function(){
				
			});
		}
	});
	
	
	return UserTopBarAuthenticated;
});