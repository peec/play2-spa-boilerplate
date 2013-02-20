define([
'underscore',
'backbone'
],
function (_, Backbone) {
	// private
	var Model = Backbone.Model.extend({
		
	});
	
	// private
	var Collection = Backbone.Collection.extend({
		model: Model,
		addCrumb: function(url, name){
			// Put it in the back.
			this.unshift(new Model({
				url: url,
				name: name
			}));
		}
	});
	
	
	return new Collection();
});