define(['underscore', 'Model'], function(_, Model, vent) {

	var Configuration = Model.extend({
		url: '/api/config'
	});
	

	return Configuration;
});