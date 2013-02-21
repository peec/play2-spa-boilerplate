define([
'jquery',
'underscore',
'backbone',
'marionette',
'tpl!templates/core/user_logged_in_top.html'
],
function ($, _, Backbone, Marionette, tmpl) {
	
	
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