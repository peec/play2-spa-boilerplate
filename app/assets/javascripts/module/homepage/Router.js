define([
'backbone', 
'marionette', 
'app', 
'./controllers/HomePageController', 
'vent'
], function(Backbone, Marionette, app, controller, vent){

	
	var Router = Backbone.Marionette.AppRouter.extend({
		// Name of this router used to give the router a meaningful name, used in conjunction with active states in html.
		name: 'Homepage',
		
		// Route configuration
		appRoutes : {
			"": "index",
			"uploads": "uploads",
			"uploads/images": "uploadedImages"
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