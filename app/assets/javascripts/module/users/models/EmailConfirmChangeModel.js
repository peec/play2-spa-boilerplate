define(['underscore', 'Model', 'vent'], function(_, Model, vent) {
	var EmailConfirmChangeModel = Model.extend({
		url: function () {
			return '/api/user/profile/confirm-email/' + this.get("id") + "/" + this.get("secretCode");
		}
	
	});

	return EmailConfirmChangeModel;
});