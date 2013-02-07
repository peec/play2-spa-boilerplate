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
					console.log("Signed upload complete, trying to upload.");
					self.uploadToS3(file, data);
				},
				error : function(xhr, response){
					self.onError(response);
				}
			});
		};

		self.uploadToS3 = function(file, req) {
			var xhr = self.createCORSRequest(),
				public_url = req.fileUrl;
			
			
			if (!xhr) {
				self.onError('CORS not supported');
			} else {
				var fd = new FormData();
				
				fd.append('key', req.key);
				fd.append('acl', req.acl); 
				fd.append('Content-Type', req.contentType);      
				fd.append('AWSAccessKeyId', req.accessKey);
				fd.append('policy', req.policy)
				fd.append('signature',req.signature);
				fd.append("file",file);

				xhr.addEventListener("load", function(e) {
					self.onProgress(100, 'Upload completed.', public_url, file);
					self.onFinish(public_url, file);
				}, false);
				
				xhr.addEventListener("abort", function(e) {
					self.onAbort('XHR error.', file, e);
				}, false);
				xhr.addEventListener("error", function(e) {
					self.onError('XHR error.', file, e);
				}, false);
				
				xhr.upload.addEventListener("progress", function(e) {
					var percentLoaded;
					if (e.lengthComputable) {
						percentLoaded = Math.round((e.loaded / e.total) * 100);
						return self.onProgress(percentLoaded,
								(percentLoaded === 100 ? 'Finalizing.'
										: 'Uploading.'), public_url, file);
					}
				}, false);

				
				xhr.open('POST', req.uploadUrl, true);
				return xhr.send(fd);				
			}

		};

		self.createCORSRequest = function () {
			var xhr = new XMLHttpRequest();
			if ("withCredentials" in xhr) {
				// Check if the XMLHttpRequest object has a "withCredentials"
				// property.
				// "withCredentials" only exists on XMLHTTPRequest2 objects.

			} else if (typeof XDomainRequest != "undefined") {
				// Otherwise, check if XDomainRequest.
				// XDomainRequest only exists in IE, and is IE's way of making
				// CORS requests.
				xhr = new XDomainRequest();

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

	Upload.prototype.onError = function(status, file, e) {
		return console.log('onError()', status, file, e);
	};

	Upload.prototype.onFinish = function(public_url, file) {
		return console.log('onFinish()', public_url, file);
	};
	Upload.prototype.onAbort = function(file, e) {
		return console.log('onAbort()', public_url, file, e);
	};
	

	return Upload;
});