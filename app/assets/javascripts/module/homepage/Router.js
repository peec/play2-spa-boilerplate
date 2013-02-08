define([
'backbone', 
'marionette', 
'app', 
'./controllers/HomePageController', 
'vent'
], function(Backbone, Marionette, app, controller, vent){
	

	var Router = Backbone.Marionette.AppRouter.extend({
		appRoutes : {
			"": "index",
			"uploads": "uploads"
			// Add routes here.
		}
	});

	app.addInitializer(function() {
		console.log("Init module:homepage.");
		// Note, setting homepageRouter to app object.
		app.routers.homepage = new Router({
			controller: controller
		});
	});
	

	// If something require login and not logged in require-login is thrown, navigate to home page.
	vent.on('auth:require-login', function(){
		console.log("vent.on!auth:require-login");
		this.routers.homepage.navigate('', { trigger: true });
	}, app);
	
	
});