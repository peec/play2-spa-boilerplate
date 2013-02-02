define(['underscore', 'jquery', 'Model', 'vent', 'cookie'], function(_, $, Model, vent) {
	
	
	var UserSession = Model.extend({
		url : "/api/auth",
		isAuthenticated : function() {
			return Boolean($.cookie('logged_in'));
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
			this.destroy({
				success: function () {
					$.removeCookie('logged_in');
					
					vent.trigger('auth:logout');
					vent.trigger('auth:update');
					
				},
				error: error
			});
		}
	});

	return new UserSession();

});