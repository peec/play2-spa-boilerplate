define([
'jquery',
'underscore',
'backbone',
'marionette',
'app',
'userSession',
'../views/FileUploadLayout'
],
function ($, _, Backbone, Marionette, app, userSession, UploadLayout) {
	
	var main = app.main.currentView;
	
	var uploadLayout = new UploadLayout();
	
	return {
		uploads: function(){
			// Require login, redirect if not.
			if (!userSession.requireLogin())return;
			
			if (!(app.main.currentView instanceof UploadLayout)){
				main.content.show(uploadLayout);
			}
			require(['homepage/views/FileUploaderView'], function(View){
				uploadLayout.content.show(new View());
			});
			
		},
		uploadedImages: function(){
			// Require login, redirect if not.
			if (!userSession.requireLogin())return;
			

			if (!(app.main.currentView instanceof UploadLayout)){
				main.content.show(uploadLayout);
			}
			
			require(['homepage/views/FileUploadImagesView'], function(View){
				uploadLayout.content.show(new View());
			});
		},
		index: function(){
			require(['homepage/views/HomePageView'], function(View){
				main.content.show(new View({model: new Backbone.Model({testVar: "This is a var from a model.."})}));
			});
		}
		
	};
	
});