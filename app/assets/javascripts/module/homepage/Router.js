define(['backbone', 'marionette', 'app', 'homepage/controllers/HomePageController'], function(Backbone, Marionette, App, controller){
	

	var Router = Backbone.Marionette.AppRouter.extend({
		appRoutes : {
			"": "index"
			// Add routes here.
		}
	});

	App.addInitializer(function() {
		console.log("Init module:homepage.");
		new Router({
			controller: controller
		});
		
	});
	
	

});