define(['underscore', 'Model', 'vent'], function(_, Model, vent) {
	var Model = Backbone.Model.extend({
		url: '/api/user/create',
		validate: function(attrs, options){
			var error = {};
			
			if (!attrs.email || !/\S+@\S+/.test(attrs.email)){
				error.msg = "Email does not validate, please check your email.";
				error.type = 'email';
			}else if (!attrs.password){
				error.msg = "Password may not be empty.";
				error.type = 'password';
			}else if (attrs.password !== attrs.passwordConfirm){
				error.msg = "Password confirmation is not equal to the password.";
				error.type = 'passwordConfirm';
			}
			
			
			if (typeof error.msg !== 'undefined')return error;
		}
	});

	return Model;
});