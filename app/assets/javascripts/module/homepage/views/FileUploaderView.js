define([
'jquery',
'underscore',
'backbone',
'marionette',
'tpl!templates/homepage/file_uploader.html',
'utils/S3Upload',
'userSession',
'vent'
],
function ($, _, Backbone, Marionette, tmpl, S3Upload, userSession, vent) {
	
	var View = Backbone.Marionette.Layout.extend({
		template: tmpl,
		ui: {
			uploadBar: '#fileUploadBar',
			fileDisplay: '#fileDisplay'
		},
		initialize: function(){
			var that = this;
			
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
			'click #uploadFile': 'uploadStuff'
		},
		uploadStuff: function(){
			this.ui.uploadBar.show();
			this.file.upload();
		}
	});
	
	
	return View;
});