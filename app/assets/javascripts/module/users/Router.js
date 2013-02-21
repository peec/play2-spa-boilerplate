define([
'backbone', 
'marionette'
], function(Backbone, Marionette){

	var Router = Backbone.Marionette.AppRouter.extend({
		// Route configuration
		appRoutes : {
			"users/signup": "signup",
			"users/forgotpassword": "forgotPassword",
			"users/:name/profile": "userCP"
		}
	});

	return Router;
});