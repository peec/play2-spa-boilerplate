define([
'jquery',
'underscore',
'backbone',
'marionette',
'tpl!templates/homepage/index.html',
'utils/S3Upload',
'userSession',
'vent'
],
function ($, _, Backbone, Marionette, tmpl, S3Upload, userSession, vent) {
	
	var View = Backbone.Marionette.Layout.extend({
		template: tmpl,
		ui: {
			clickableHide: '#clickToHide',
			uploadBar: '#fileUploadBar',
			fileDisplay: '#fileDisplay',
			userOnly: '.userOnly'
		},
		initialize: function(){
			var that = this;
			
			vent.on("auth:update", this.authUpdate, this);
			
			// Create a upload object.
			this.file = new S3Upload({
				onProgress: function (percent) {
					that.ui.uploadBar.children('div').css('width', percent + "%");
				},
				onError: function(error){
					that.ui.uploadBar.hide();
				},
				onFinish: function(finishedUrl){
					console.log(finishedUrl);
					that.ui.uploadBar.hide();
					that.ui.fileDisplay.show();
					that.ui.fileDisplay.children('img').attr('src', finishedUrl).attr('width', '300px');
				}
			});
		},
		events: {
			'click  #clickToHide': 'hideDiv',
			'click #uploadFile': 'uploadStuff'
		},
		uploadStuff: function(){
			this.ui.uploadBar.show();
			this.file.upload();
		},
		authUpdate: function(){
			if (userSession.isAuthenticated()){
				this.ui.userOnly.show();
			}else{
				this.ui.userOnly.hide();
			}
		},
		hideDiv: function(){
			this.ui.clickableHide.hide();
		},
		onRender: function(){
			this.authUpdate();
		}
	});
	
	
	return View;
});