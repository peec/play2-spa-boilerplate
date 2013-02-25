define(['underscore', 'Model', 'vent'], function(_, Model, vent) {
	var ForgotPasswordModel = Model.extend({
		url: function () {
			if (!this.isNew()){
				return '/api/user/forgotpassword/' + this.get("id") + "/" + this.get("accessCode");
			}else{
				return '/api/user/forgotpassword';
			}
			
		}
	});

	return ForgotPasswordModel;
});