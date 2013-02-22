define([
'jquery',
'underscore',
'backbone',
'marionette',
'vent',
'tpl!templates/users/activation_step1.html',
'tpl!templates/users/activation_step2.html',
'tpl!templates/users/activation_error.html',
'utils/bootstrapUtils'
],
function ($, _, Backbone, Marionette, vent, tmpl1, tmpl2, errorTemplate, bootstrapUtils) {
	
	
	var ActivationStep1View = Backbone.Marionette.ItemView.extend({
		template: tmpl1,
		activate: function () {
			var that = this;
			this.model.save({}, {
				success: function(model, resp) {
					that.template = tmpl2;
					that.render();
				},
				error: function(model, resp) {
					that.template = errorTemplate;
					that.render();
				}
			});
		}
	});
	
	
	return ActivationStep1View;
});