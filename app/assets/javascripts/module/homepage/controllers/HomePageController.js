define([
'jquery',
'underscore',
'backbone',
'marionette',
'app',
'userSession'
],
function ($, _, Backbone, Marionette, app, userSession) {
	
	var main = app.main.currentView;
	
	return {
		uploads: function(){
			// Require login, redirect if not.
			if (!userSession.requireLogin())return;
			
			require(['homepage/views/FileUploaderView'], function(View){
				main.content.show(new View());
			});
		},
		index: function(){
			require(['homepage/views/HomePageView'], function(View){
				main.content.show(new View({model: new Backbone.Model({testVar: "This is a var from a model.."})}));
			});
		}
		
	};
	
});