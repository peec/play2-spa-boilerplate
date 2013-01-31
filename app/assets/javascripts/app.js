define([
'jquery',
'underscore',
'backbone',
'marionette',
'handlebars',
'hbs!templates/app_view'
],
function ($, _, Backbone, Marionette, Handlebars, tmpl) {

	Backbone.Marionette.TemplateCache.prototype.compileTemplate = function(rawTemplate) {
		return rawTemplate;
	};

	var App = new Backbone.Marionette.Application();

	App.addRegions({
		main: '#appStub'
	});



	App.on("initialize:after", function(){
		if(Backbone.history) {
			console.log("Starting history.");
			Backbone.history.start({ pushState: true });
		}
	});

	var AppLayout = Backbone.Marionette.Layout.extend({
		template: tmpl,
		regions: {
			content: "#content"
		}
	});

	var layout = new AppLayout();
	
	App.main.show(layout);

	return App;

});