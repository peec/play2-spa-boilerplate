define([
'jquery',
'underscore',
'backbone',
'marionette',
'handlebars',
'app'
],
function ($, _, Backbone, Marionette, Handlebars, app) {
	
	var main = app.main.currentView;
	
	return {
		
		index: function(){
			require(['homepage/views/HomePageView'], function(view){
				main.content.show(view);
			});
		}
		
	};
	
});