define(['underscore', 'Model', 'vent'], function(_, Model, vent) {
	var ActivationModel = Model.extend({
		url: '/api/user/activate'
	});

	return ActivationModel;
});