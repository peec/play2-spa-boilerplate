define([
'jquery',
'underscore',
'backbone',
'marionette',
'handlebars',
'hbs!templates/homepage/index',
'utils/S3Upload'
],
function ($, _, Backbone, Marionette, Handlebars, tmpl, S3Upload) {
	
	var View = Backbone.Marionette.Layout.extend({
		template: tmpl,
		ui: {
			clickableHide: '#clickToHide'
		},
		initialize: function(){
			this.file = new S3Upload({
				onProgress: function (percent) {
					console.log(percent);
				},
				onError: function(error){
					console.log(error);
				},
				onFinish: function(finishedUrl){
					console.log(finishedUrl);
				}
			});
		},
		events: {
			'click  #clickToHide': 'hideDiv'
		},
		hideDiv: function(){
			this.ui.clickableHide.hide();
			this.file.upload();
		}
	});
	
	
	return View;
});