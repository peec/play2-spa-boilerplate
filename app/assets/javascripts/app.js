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


	
	// This command will update active links on the whole page when there is a route change.
	// This command is bound to history change of backbone router.
	commands.addHandler('core:route:update', function(view){
		var router = app.currentRouter;
		var fragment = Backbone.history.fragment;
		if (router){
			var route = (router.name + "." + router.appRoutes[fragment]);
			
			console.log("Recieve command core:route:update with " + route + " (fragment:" + fragment + ") ");
			
			view.$('.routerLink').each(function(index, item){
				$(this).removeClass('active');
			});
	
			var blackList = [];
			
			var base = view.$(".routerLink[data-route='" + route + "']");
			base.addClass('active');
			
			blackList.push(route);
			
			// Recursion of element active.
			var Finder = function (base) { 
				var	elParents = base.data('parent-routes') || "";
				if (elParents){
					elParents = elParents.split(',');
					
					_.each(elParents, function(elem){
						if (!_.contains(blackList, elem)){
							blackList.push(elem);
							
							var el = view.$(".routerLink[data-route='" + elem + "']");
							el.addClass('active');
							Finder(el);
						}
					});
				}
			}
			Finder(base);
		}
	});
	
	
	
	app.on("initialize:after", function(){
		
		Backbone.history.bind("route", function (router, route) {
			app.currentRouter = router;
			if (typeof route !== 'undefined'){
				commands.execute("core:route:update", app.layout);
			}
		});
		
		if(Backbone.history) {		
			console.log("Starting history.");
			Backbone.history.start({ pushState: false });
		}
	});
	


	return app;

});