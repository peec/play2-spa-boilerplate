/**
 * This file contains the command handler.
 * Global core commands is also added to this object.
 */
define([
'backbone',
'underscore',
'userSession',
'vent',
'module/core/models/breadcrumbs',
'backbone.wreqr'
], 
function(Backbone, _, userSession, vent, breadcrumbs) {
	var commands = new Backbone.Wreqr.Commands();
	
	/**
	 * Command: Updates gui based on login.
	 * .userOnly , shows if logged in
	 * .guestOnly , shows if user is not logged in.
	 * 
	 * More classes can be added based on what user group is on the user etc.
	 */
	commands.addHandler('core:auth:gui:change', function(view){
		
		var handler = function(){
			if (userSession.isAuthenticated()){
				view.$('.userOnly').show();
				view.$('.guestOnly').hide();
			}else{
				view.$('.userOnly').hide();
				view.$('.guestOnly').show();
			}
		}
		view.on("render", function(){
			handler();
			view.listenTo(userSession, "change:id", handler);
		});
		
		
	});

	
	/**
	 * Command: Make links active when current url is correct based on data elements in the html.
	 * @param router The current router.
	 * @param view The view that should be checked. Note it will also check sub-views, normally you would put the super view here. 
	 */
	commands.addHandler('core:route:update', function(router, view){

		breadcrumbs.reset();
		var fragment = Backbone.history.fragment;
		var route = (router.name + "." + router.appRoutes[fragment]);

		// Remove all .routerLink's active class.
		view.$('.routerLink').each(function(index, item){
			$(this).removeClass('active');
		});

		// Create blackList.
		var blackList = [];

		var base = view.$(".routerLink[data-route='" + route + "']");
		base.addClass('active');

		var link = base.children(base.data('breadcrumb-element') || "a").first();
		if (link.length > 0){
			// Append breadcrumb.
			breadcrumbs.addCrumb(link.attr('href'), link.text());
		}
		
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
					// Check if the blackList already contains this, 
					// then its no need to do it again.
					if (!_.contains(blackList, elem)){
						
						// Push it to the blacklist.
						blackList.push(elem);
						// Find the link and put active state on it.
						var el = view.$(".routerLink[data-route='" + elem + "']");
						var link = el.children(el.data('breadcrumb-element') || "a").first();
						if (link.length > 0){
							breadcrumbs.addCrumb(link.attr('href'), link.text());
						}
						
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
		
		var lastModel = breadcrumbs.last();
		if (lastModel){
			lastModel.set('active', true);
		}
		
		vent.trigger('core:breadcrumbs:update', breadcrumbs);
		
	});
	


	
	return commands;
});