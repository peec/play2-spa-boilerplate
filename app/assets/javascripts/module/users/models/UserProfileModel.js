define(['underscore', 'Model', 'vent'], function(_, Model, vent) {
	var UserProfileModel = Model.extend({
		url: '/api/user/profile',
		validate: function (attrs) {
			var error = {};
			
			if (typeof attrs.password !== 'undefined'){
				if (!attrs.password || attrs.password !== attrs.confirmPassword){
					error.msg = "Password confirmation is not equal to the password.";
					error.type = 'confirmPassword';
				}
			}

			if (typeof error.msg !== 'undefined')return error;
		}
	});

	return UserProfileModel;
});