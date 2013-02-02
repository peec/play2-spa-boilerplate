define([
"app"
], 
function (App) {
	"use strict";
	
	// Document is ready. Starting app here
	$(function() {
		
		
		
		// Array of all the routers in the modules.
		// Routers should be self executing and hooked to the apps initialize.
		var routers = [
		'homepage/Router'
		];
		
		
		// Require all routers before start.
		require(routers, function(){
			console.log("App bootstrapped.. Starting.");
			App.start();
		});
		
		
	});

});