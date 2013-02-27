define([
'jquery',
'underscore',
'backbone',
'marionette',
'app',
'userSession',
'module/core/models/breadcrumbs',
'vent'
],
function ($, _, Backbone, Marionette, app, userSession, breadcrumbs, vent) {
	var main = app.main.currentView;
	return {
		signup: function () {
			require(['users/views/RegisterUserView', 'users/models/RegisterUserModel'], function(View, RegisterUserModel){
				main.content.show(new View({model: new RegisterUserModel()}));
			});
		},
		signupConfirmation: function (userId, accessCode) {
			require(['users/views/RegisterUserConfirmationView', 'users/models/ConfirmUserModel'], function(View, ConfirmUserModel){
				main.content.show(new View({model: new ConfirmUserModel({id: userId, accessCode: accessCode})}));
				breadcrumbs.reset();
				breadcrumbs.addCrumb('#user/signup/confirmation/'+userId+'/'+accessCode, 'Email confirmation', {active: true});
				breadcrumbs.addCrumb('#/user/signup', 'Sign Up');
				vent.trigger('core:breadcrumbs:update', breadcrumbs);
				
			});
		},
		forgotPassword: function () {
			require(['users/views/ForgotPasswordStep1View', 'users/models/ForgotPasswordModel'], function(View, ForgotPasswordModel){
				main.content.show(new View({model: new ForgotPasswordModel()}));
			});
		},
		submitForgotPasswordRequest: function (userId, accessCode) {
			require(['users/views/ForgotPasswordStep2View', 'users/models/ForgotPasswordModel'], function(View, ForgotPasswordModel){
				var m = new ForgotPasswordModel({id: userId, accessCode: accessCode});
				m.fetch({
					success: function(model, resp){
						var view = new View({model: model});
						main.content.show(view);
					},
					error: function (model, resp) {
						// TODO.
						console.log("Not a valid response.");
					}
				});
				
			});
		},
		userCP: function () {
			// Require login, redirect if not.
			if (!userSession.requireLogin())return;
			
			require(['users/views/UserCPView', 'users/models/UserProfileModel'], function(View, UserProfileModel){
				var m = new UserProfileModel({id: userSession.get("id")});
				m.fetch({
					success: function (model, resp) {
						main.content.show(new View({model: model}));
					},
					error: function () {
						console.error("Could not find user."); 
					}
				});
				
			});
		},
		confirmEmailChange: function (userId, secretCode) {
			require(['users/views/EmailConfirmChangeView', 'users/models/EmailConfirmChangeModel'], function(View, EmailConfirmChangeModel){
				var view = new View({
					model: new EmailConfirmChangeModel({id: userId, secretCode: secretCode})
				});

				main.content.show(view);
				console.log("Trying to confirm email change.");
				view.confirmChange();
				
			});
		},
		confirmAccount: function (userId, activationCode) {
			require(['users/views/ActivationStep1View', 'users/models/ActivationModel'], function(View, ActivationModel){
				var actView = new View({
					model: new ActivationModel({userId: userId, activationCode: activationCode})
				});

				main.content.show(actView);
				console.log("Activating user.");
				actView.activate();
			});
		}
	};
});