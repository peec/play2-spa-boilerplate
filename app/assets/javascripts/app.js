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
			
			
			view.$('.routerLink').each(function(index, item){
				$(this).removeClass('active');
			});
	
			var blackList = [];
			
			var base = view.$(".routerLink[data-route='" + route + "']");
			base.addClass('active');
			
			blackList.push(route);
			
			// Recursion of element active.
			var Finder = function (base) {
				if (!base){
					return;
				}
				
				// Find parent routes for this current link.
				var	elParents = base.data('parent-routes') || "";
				if (elParents){
					// Split all the parents.
					elParents = elParents.split(',');
					// For each parent link, eg. data-route('Homepage.uploads')
					_.each(elParents, function(elem){
						// Check if the blackList already contains this, then its no need to do it again.
						if (!_.contains(blackList, elem)){
							// Push it to the blacklist.
							blackList.push(elem);
							// Find the link and put active state on it.
							var el = view.$(".routerLink[data-route='" + elem + "']");
							el.each(function(){
								var par = $(this);
								var parents = (par.data('parent-routes') || "").split(',');
								if (!_.contains(parents, elem)){
									par.addClass('active');
								}
							});
							
							// Recursive: Now check the parent link for parents again.
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