define([
'jquery',
'underscore',
'backbone',
'marionette',
'module/core/views/AppLayout',
'vent',
'commands'
],
function ($, _, Backbone, Marionette, AppLayout, vent, commands) {

	
	Backbone.Marionette.TemplateCache.prototype.compileTemplate = function(rawTemplate) {
		return rawTemplate;
	};

	var app = new Backbone.Marionette.Application();
	app.routers = {}; // Holds all the Backbone.Marionette.AppRouters.
	
	app.addRegions({
		main: '#appStub'
	});

	var layout = new AppLayout();

	app.layout = new AppLayout();
	app.main.show(app.layout);

	vent.on("auth:update", function(){
		commands.execute('core:auth:gui:change', app.layout);
	});

	app.on("initialize:after", function(){
		
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