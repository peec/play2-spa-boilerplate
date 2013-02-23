define(['underscore', 'Model', 'vent'], function(_, Model, vent) {
	var ConfirmUserModel = Model.extend({
		url: function(){
			return '/api/user/send-confirm/' + this.get("id") + "/" + this.get("accessCode");
		}
	});

	return ConfirmUserModel;
});