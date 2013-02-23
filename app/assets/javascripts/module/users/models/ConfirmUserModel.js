define(['underscore', 'Model', 'vent'], function(_, Model, vent) {
	var ConfirmUserModel = Model.extend({
		urlRoot: '/api/user/send-confirm'
	});

	return ConfirmUserModel;
});