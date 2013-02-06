define([
'jquery',
'underscore',
'backbone',
'marionette',
'module/core/views/AppLayout',
'vent'
],
function ($, _, Backbone, Marionette, AppLayout, vent) {

	

	
	Backbone.Marionette.TemplateCache.prototype.compileTemplate = function(rawTemplate) {
		return rawTemplate;
	};

	var app = new Backbone.Marionette.Application();
	app.loggedIn = false;
	
	app.addRegions({
		main: '#appStub'
	});



	app.on("initialize:after", function(){
		if(Backbone.history) {
			console.log("Starting history.");
			Backbone.history.start({ pushState: true });
		}
	});

	
	
	var layout = new AppLayout();
	
	app.main.show(layout);

	
	return app;

});