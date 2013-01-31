define([
'jquery',
'underscore',
'backbone',
'marionette',
'handlebars',
'text!templates/app_view.html'
],
function ($, _, Backbone, Marionette, Handlebars, tmpl) {

	Backbone.Marionette.TemplateCache.prototype.compileTemplate = function(rawTemplate) {
		return Handlebars.compile(rawTemplate);
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

	var layout = new AppLayout({model: new Backbone.Model({test: "This is a attribute printed by the model."})});
	
	App.main.show(layout);

	return App;

});