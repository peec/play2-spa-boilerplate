define(['underscore', 'jquery', 'Model', 'vent', 'cookie'], function(_, $, Model, vent) {
	
	
	var UserSession = Model.extend({
		url : "/api/auth",
		isAuthenticated : function() {
			return Boolean($.cookie('logged_in'));
		},
		// Only retrieve user object.
		parse: function (response) {
			return response.result.user;
		},
		login: function(username, password, success, error){
			this.save({username: username, password: password},{
				success: function(model, response, options){
					$.cookie('logged_in', true);
					vent.trigger('auth:login');
					vent.trigger('auth:update');
				},
				error: error
			});
		},
		logout: function(success, error){
			var that = this;
			$.removeCookie('logged_in');
			this.destroy({
				success: function (model, response, options) {
					model.clear();
					vent.trigger('auth:logout');
					vent.trigger('auth:update');
					success(model, response, options);
				},
				error: error
			});
		}
	});

	return new UserSession();

});