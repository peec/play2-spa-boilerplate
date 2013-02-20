define([
'jquery',
'underscore',
'backbone',
'marionette',
'vent',
'../models/breadcrumbs',
'tpl!templates/core/breadcrumb_view.html'
],
function ($, _, Backbone, Marionette, vent, collection, tmpl) {
	
	
	var BreadcrumbView = Backbone.Marionette.ItemView.extend({
		template: tmpl,
		initialize: function(options){
			this.collection = collection;
			vent.on('core:breadcrumbs:update', this.onUpdateCrumb, this);
		},
		onUpdateCrumb: function (collection) {
			this.render();
		},
		onClose: function(){
			vent.off('core:breadcrumbs:update', this.onUpdateCrumb);
		}
	});
	
	
	return BreadcrumbView;
});