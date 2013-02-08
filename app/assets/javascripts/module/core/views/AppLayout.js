define([
'jquery',
'underscore',
'backbone',
'marionette',
'tpl!templates/app_view.html',
'userSession',
'./UserTopBarGuest',
'./UserTopBarAuthenticated',
'vent'
],
function ($, _, Backbone, Marionette, tmpl, userSession, UserTopBarGuest,UserTopBarAuthenticated, vent) {
	
	
	var AppLayout = Backbone.Marionette.Layout.extend({
		template: tmpl,
		regions: {
			content: "#content",
			userbar: '#regionUserTop'
		},
		initialize: function(){
			// Bind for auth token change..
			vent.on("auth:update", this.toggleUserGenerics, this);
		},
		toggleUserGenerics: function(){
			if (!userSession.isAuthenticated()){
				this.userbar.show(new UserTopBarGuest({model: userSession}));
				this.$('.userOnly').hide();
			}else{
				this.userbar.show(new UserTopBarAuthenticated({model: userSession}));
				this.$('.userOnly').show();
			}
		},
		onRender: function(){
			this.toggleUserGenerics();
		}
	});
	
	
	return AppLayout;
});