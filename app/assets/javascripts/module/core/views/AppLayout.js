define([
'jquery',
'underscore',
'backbone',
'marionette',
'tpl!templates/app_view.html',
'userSession',
'./UserTopBarGuest',
'./UserTopBarAuthenticated',
'vent',
'commands'
],
function ($, _, Backbone, Marionette, tmpl, userSession, UserTopBarGuest,UserTopBarAuthenticated, vent, commands) {
	
	
	var AppLayout = Backbone.Marionette.Layout.extend({
		template: tmpl,
		regions: {
			content: "#content",
			userbar: '#regionUserTop'
		},
		initialize: function(){
			// Bind for auth token change..
			vent.on("auth:update", this.toggleUserGenerics, this);
			// Bind .userOnly + .guestOnly classes.
			commands.execute('core:auth:gui:change', this);
		},
		toggleUserGenerics: function(){
			if (!userSession.isAuthenticated()){
				this.userbar.show(new UserTopBarGuest({model: userSession}));
			}else{
				this.userbar.show(new UserTopBarAuthenticated({model: userSession}));
			}
		},
		onRender: function(){
			this.toggleUserGenerics();
		}
	});
	
	
	return AppLayout;
});