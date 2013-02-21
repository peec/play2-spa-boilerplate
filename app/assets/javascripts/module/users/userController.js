define([
'jquery',
'underscore',
'backbone',
'marionette',
'app',
'userSession'
],
function ($, _, Backbone, Marionette, app, userSession) {
	var main = app.main.currentView;
	return {
		signup: function(){
			require(['users/views/RegisterUserView', 'users/models/RegisterUserModel'], function(View, RegisterUserModel){
				main.content.show(new View({model: new RegisterUserModel()}));
			});
		},
		
		forgotPassword: function(){
			require(['users/views/ForgotPasswordStep1View'], function(View){
				main.content.show(new View());
			});
		},
		
		userCP: function(){
			// Require login, redirect if not.
			if (!userSession.requireLogin())return;
			
			require(['users/views/UserCPView'], function(View){
				main.content.show(new View());
			});
		}
	};
});