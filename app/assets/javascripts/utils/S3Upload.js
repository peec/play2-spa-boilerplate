define([ "jquery" ], function($) {
	"use strict";

	var Upload = function(options) {
		var self = this;
		options = options || {};

		$.extend(this, options);
		
		self.signUrl = options.signUrl || "/api/s3/sign";
		self.file = options.file || "#file";

		self.upload = function() {
			self.sign($(self.file).get(0).files[0]);
		};

		self.sign = function(file) {
			console.log(file);
			$.ajax({
				url : self.signUrl,
				type : 'POST',
				contentType : 'application/json',
				dataType : 'json',
				data : JSON.stringify({
					fileName : file.name,
					contentType : file.type,
					size : file.size
				}),
				success : function(data) {
					console.log("Signed upload complete, trying to upload. " + data.signed_request);
					self.uploadToS3(file, data.signed_request, data.url);
				},
				error : function(xhr, response){
					self.onError(response);
				}
			});
		};

		self.uploadToS3 = function(file, url, public_url) {
			var xhr;
			xhr = self.createCORSRequest('PUT', url);

			if (!xhr) {
				self.onError('CORS not supported');
			} else {
				xhr.onload = function() {
					if (xhr.status === 200) {
						self.onProgress(100, 'Upload completed.', public_url, file);
						return self.onFinish(public_url, file);
					} else {
						return self.onError('Upload error: '
								+ xhr.status, file);
					}
				};
				xhr.onerror = function() {
					return self.onError('XHR error.', file, xhr);
				};
				xhr.upload.onprogress = function(e) {
					var percentLoaded;
					if (e.lengthComputable) {
						percentLoaded = Math.round((e.loaded / e.total) * 100);
						return self.onProgress(percentLoaded,
								(percentLoaded === 100 ? 'Finalizing.'
										: 'Uploading.'), public_url, file);
					}
				};
			}
			xhr.setRequestHeader('Content-Type', file.type);
			xhr.setRequestHeader('x-amz-acl', 'public-read');

			return xhr.send(file);
		};

		self.createCORSRequest = function (method, url) {
			var xhr = new XMLHttpRequest();
			if ("withCredentials" in xhr) {
				// Check if the XMLHttpRequest object has a "withCredentials"
				// property.
				// "withCredentials" only exists on XMLHTTPRequest2 objects.
				xhr.open(method, url, true);

			} else if (typeof XDomainRequest != "undefined") {
				// Otherwise, check if XDomainRequest.
				// XDomainRequest only exists in IE, and is IE's way of making
				// CORS requests.
				xhr = new XDomainRequest();
				xhr.open(method, url);

			} else {

				// Otherwise, CORS is not supported by the browser.
				xhr = null;
			}
			return xhr;
		};
	}

	Upload.prototype.onProgress = function(percent, status, public_url, file) {
		return console.log('onProgress()', percent, status, public_url, file);
	};

	Upload.prototype.onError = function(status, file, xhr) {
		return console.log('onError()', status, file, xhr);
	};

	Upload.prototype.onFinish = function(public_url, file) {
		return console.log('onFinish()', public_url, file);
	};

	return Upload;
});