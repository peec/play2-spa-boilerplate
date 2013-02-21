define(['underscore', 'jquery', 'Model', 'vent','cookie'], function(_, $, Model, vent) {
	
	
	var UserSession = Model.extend({
		url : "/api/auth",
		isAuthenticated : function() {
			return Boolean(this.get("id"));
		},
		initialize: function(){
			this.fetch({async:false});
		},
		// Only retrieve user object.
		parse: function (response) {
			return response.result.user;
		},
		login: function(username, password, success, error){
			this.save({username: username, password: password},{
				success: function(model, response, options){
					success(model, response, options);
					vent.trigger('auth:login');
					vent.trigger('auth:update');
				},
				error: error
			});
		},
		logout: function(success, error){
			this.destroy({
				success: function (model, response, options) {
					model.clear();
					vent.trigger('auth:logout');
					vent.trigger('auth:update');
					success(model, response, options);
				},
				error: error
			});
		},
		requireLogin: function(){
			
			if (!this.isAuthenticated()){
				vent.trigger('auth:require-login');
				return false;
			
			}else{
				// Register current history
				var currentHistory = Backbone.history.fragment;
				var callback = function(){
					if (Backbone.history.fragment === currentHistory){
						console.log("got logout on require-login route: " + currentHistory + " fire auth:require-login.");
						vent.off("auth:logout", this); // Unbind..
						vent.trigger('auth:require-login');
					}else{
						// Not relevant anymore.
						vent.off("auth:logout", this);
					}
				};
				vent.on("auth:logout", callback);
			}
			return true;
		}
	});

	var session = new UserSession();


	// Setup global global ajax handler.
	$.ajaxSetup({
		statusCode: {
			// Unauthorized - requires login, but not logged in.
			// Failsafe, if token is expiered on server.
			401: function() {
				if (session.isAuthenticated()){
					session.clear();
					vent.trigger('auth:expired');
					vent.trigger('auth:logout');
					vent.trigger('auth:update');
				}
			}
		}
	});
	
	
	return session;
});