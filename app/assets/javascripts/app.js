define([
'jquery',
'underscore',
'backbone',
'marionette',
'handlebars',
'module/core/views/AppLayout',
'vent'
],
function ($, _, Backbone, Marionette, Handlebars, AppLayout, vent) {

	// Setup global global ajax handler.
	$.ajaxSetup({
		statusCode: {
			// Unauthorized - requires login, but not logged in.
			401: function() {
				// If cookie for logged_in is still alive, fire logout vents and remove cookie.
				if ($.cookie('logged_in')){
					$.removeCookie('logged_in');
					vent.trigger('auth:logout');
					vent.trigger('auth:update');
				}
			}
		}
	});

	
	Backbone.Marionette.TemplateCache.prototype.compileTemplate = function(rawTemplate) {
		return rawTemplate;
	};

	var app = new Backbone.Marionette.Application();

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