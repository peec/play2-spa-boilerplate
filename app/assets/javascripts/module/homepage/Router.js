define([
'backbone', 
'marionette'
], function(Backbone, Marionette){

	var Router = Backbone.Marionette.AppRouter.extend({
		// Route configuration
		appRoutes : {
			"": "index",
			"uploads": "uploads",
			"uploads/images": "uploadedImages"
			// Add routes here.
		}
	});

	
	return Router;
});