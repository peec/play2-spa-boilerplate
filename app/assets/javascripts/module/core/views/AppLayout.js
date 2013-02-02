define([
'jquery',
'underscore',
'backbone',
'marionette',
'handlebars',
'hbs!templates/app_view',
'userSession',
'./UserTopBar',
'vent'
],
function ($, _, Backbone, Marionette, Handlebars, tmpl, userSession, UserTopBar, vent) {
	
	
	
	var AppLayout = Backbone.Marionette.Layout.extend({
		template: tmpl,
		regions: {
			content: "#content",
			userbar: '#regionUserTop'
		},
		initialize: function(){
			// Bind for auth token change..
			vent.on("auth:update", this.toggleUserBar, this);
		},
		toggleUserBar: function(){
			var that = this;
			if (!userSession.isAuthenticated()){
				that.userbar.show(new UserTopBar({model: userSession}));
			}else{
				console.log("User is logged in.");
			}
		},
		onRender: function(){
			this.toggleUserBar();
		}
	});
	
	
	return AppLayout;
});