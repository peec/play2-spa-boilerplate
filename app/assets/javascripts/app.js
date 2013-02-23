define([
'jquery',
'underscore',
'backbone',
'marionette',
'module/core/views/AppLayout',
'vent',
'commands',
'module/core/models/configuration',
'userSession'
],
function ($, _, Backbone, Marionette, AppLayout, vent, commands, configuration, userSession) {

	
	Backbone.Marionette.TemplateCache.prototype.compileTemplate = function(rawTemplate) {
		return rawTemplate;
	};

	var app = new Backbone.Marionette.Application();
	// App variables.
	app.routers = {}; // Holds all the Backbone.Marionette.AppRouters.
	app.layout = null;
	
	
	app.addRegions({
		main: '#appStub'
	});

	
	app.config = configuration;
	app.config.fetch({async:false});
	// Build the user session.
	if (app.config.get("user")){
		userSession.set(app.config.get("user"), {silent: true});
	}
	
	
	// Construct main layout.
	app.layout = new AppLayout();
	app.main.show(app.layout);
	
	
	// After all initializers , bootstrap app.
	app.on("initialize:after", function(){
		
		// Set name of each router.
		_.each(app.routers, function(value, key){
			app.routers[key].name = key;
		});
		
		Backbone.history.bind("route", function (router, route) {
			app.currentRouter = router;
			if (typeof router !== 'undefined'){
				commands.execute("core:route:update", app.currentRouter, app.layout);
			}
		});
		
		if(Backbone.history) {		
			console.log("Starting history.");
			Backbone.history.start({ pushState: false });
			
		}
	});
	


	return app;

});