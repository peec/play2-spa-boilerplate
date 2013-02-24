define([
'jquery',
'underscore',
'backbone',
'marionette',
'utils/bootstrapUtils',
'tpl!templates/core/user_not_logged_in.html'
],
function ($, _, Backbone, Marionette, bootstrapUtils, tmpl) {
	
	
	var UserTopBarGuest = Backbone.Marionette.ItemView.extend({
		template: tmpl,
		ui: {
			email: '#email',
			password: '#password'
		},
		events: {
			'click #bLogin': 'login',
			'click .resendActEmail': 'resendActEmail'
		},
		resendActEmail: function(ev) {
			ev.preventDefault();
			var that = this;
			require(['users/models/ConfirmUserModel'], function(ConfirmUserModel){
				var actModel = new ConfirmUserModel({id: that.actUid, accessCode: that.actCode});
				actModel.save({}, {
					success: function(model, resp){
						console.log("Activation email sent.");
					},
					error: function(model, resp){
						
					}
					
				});
			});
		},
		login: function(e) {
			e.preventDefault();
			var that = this;
			
			this.model.login(this.ui.email.val(), this.ui.password.val(), function(){
				bootstrapUtils.inputErrorClear(that.ui.email);
				bootstrapUtils.inputErrorClear(that.ui.password);
			}, function(model, resp){
				var msg = $.parseJSON(resp.responseText);
				
				if (msg.result && msg.result.accessCodes){
					that.actCode = msg.result.accessCodes[0].accessCode;
					that.actUid = msg.result.uid;
					bootstrapUtils.inputError(that.ui.email, 'Account not confirmed, <a class="resendActEmail">resend activation email</a>.');
				}else{
					bootstrapUtils.inputError(that.ui.email);
					bootstrapUtils.inputError(that.ui.password, msg.message || "Unknown error");	
				}
			});
		}
	});
	
	
	return UserTopBarGuest;
});