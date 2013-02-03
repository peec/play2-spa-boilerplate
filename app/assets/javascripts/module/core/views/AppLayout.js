define([
'jquery',
'underscore',
'backbone',
'marionette',
'handlebars',
'hbs!templates/app_view',
'userSession',
'./UserTopBarGuest',
'./UserTopBarAuthenticated',
'vent'
],
function ($, _, Backbone, Marionette, Handlebars, tmpl, userSession, UserTopBarGuest,UserTopBarAuthenticated, vent) {
	
	
	
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
				that.userbar.show(new UserTopBarGuest({model: userSession}));
			}else{
				that.userbar.show(new UserTopBarAuthenticated({model: userSession}));
			}
		},
		onRender: function(){
			this.toggleUserBar();
		}
	});
	
	
	return AppLayout;
});