define([
'backbone', 
'marionette'
], function(Backbone, Marionette){

	var Router = Backbone.Marionette.AppRouter.extend({
		// Route configuration
		appRoutes : {
			"user/signup": "signup",
			"user/signup/confirmation/:userId": "signupConfirmation",
			"user/forgotpassword": "forgotPassword",
			"user/profile": "userCP",
			"user/confirm/:userId/:code": "confirmAccount"
		}
	});

	return Router;
});