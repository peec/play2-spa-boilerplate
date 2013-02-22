define(['underscore', 'Model', 'vent'], function(_, Model, vent) {
	var Model = Backbone.Model.extend({
		url: '/api/user/activate'
	});

	return Model;
});