define([
'app',
'./Router',
'./userController'
], function(app, Router, controller){
	
	app.addInitializer(function() {
		console.log("Init module:users.");
		app.routers.Users = new Router({
			controller: controller
		});
	});
	
});