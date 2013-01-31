define([
"app"
], 
function (App) {
	"use strict";
	
	$(function() {
		
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