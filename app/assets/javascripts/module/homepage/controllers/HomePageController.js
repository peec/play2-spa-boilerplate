define([
'jquery',
'underscore',
'backbone',
'marionette',
'app'
],
function ($, _, Backbone, Marionette, app) {
	
	var main = app.main.currentView;
	
	return {
		
		index: function(){
			require(['homepage/views/HomePageView'], function(View){
				main.content.show(new View({model: new Backbone.Model({testVar: "This is a var from a model.."})}));
			});
		}
		
	};
	
});