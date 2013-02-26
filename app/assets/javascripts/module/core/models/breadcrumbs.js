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
		addCrumb: function(url, name, options){
			// Put it in the back.
			var m = new Model({
				url: url,
				name: name
			});
			
			if (typeof options !== 'undefined'){
				_.each(options, function(k, v){
					m.set(v, k);
				});
			}

			this.unshift(m);
		}
	});
	
	
	return new Collection();
});