/**
 *  Uploadr, a multi-file uploader plugin
 *  Copyright (C) 2011 Jeroen Wesbeek
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
(function($){
	// methods
	var methods = {
		playNotification: function(options) {
			if (options.workvars.notificationSoundEffect) options.workvars.notificationSoundEffect.play();
		},

		playError: function(options) {
			if (options.workvars.errorSoundEffect) options.workvars.errorSoundEffect.play();
		},

		playDelete: function(options) {
			if (options.workvars.deleteSoundEffect) options.workvars.deleteSoundEffect.play();
		},

		cancel: function(event) {
			// prevent default browser behaviour
  			if (event.preventDefault) {
    			event.preventDefault();
    			event.stopPropagation();
  			}

			// IE requires false
  			return false;
		},

		dragOver: function(event) {
			methods.cancel(event);
		},

		dragEnter: function(event, obj, domObj, hoverClass, options) {
			obj.addClass(hoverClass);
			methods.cancel(event, obj, domObj, hoverClass, options);
//			if (!options.workvars.gotFiles) $('.placeholder', obj).hide(); 
		},

		dragLeave: function(event, obj, domObj, hoverClass, options) {
			if (event.target && event.target == obj[0]) {
				obj.removeClass(hoverClass);
			}
			methods.cancel(event);
			$('.placeholder', obj).html(options.placeholderText);
//			if (!options.workvars.gotFiles) $('.placeholder', obj).show(); 
		},

		addFileElements: function(domObj, file, options, showPercentage) {
			var fileDiv = document.createElement('div');

			return fileDiv;
		},

		addFileTooltip: function(domObj, file) {
			// change unix timestamp into date object
			var date = new Date();
				date.setTime(file.fileDate);

			// attach tooltip
			domObj.tipTip({
				content		: 'name: ' + file.fileName + '<br/>size: ' + methods.bytesToSize(file.fileSize) + ((file.fileDate) ? ('<br/>date: ' + date.toString()) : ''),
				maxWidth	: 600
			});
		},

		drop: function(event, obj, domObj, hoverClass, options) {
			var files = event.dataTransfer.files;

			var dropElement = obj;

			// remove class
			dropElement.removeClass(hoverClass);

			// stops the browser from redirecting off to the text.
  			if (event.preventDefault) {
    			event.preventDefault();
    			event.stopPropagation();
  			}

			// iterate through files
			if (typeof files !== "undefined") {
				// hide the placeholder text
//				$('.placeholder', obj).hide();

				// set work var
				options.workvars.gotFiles = true;

				// iterate through files
				$.each(files, function(index, file) {
					// add file DOM elements
					var fileAttrs = { fileName: ((file.name) ? file.name : file.fileName), fileSize: ((file.size) ? file.size : file.fileSize), startTime: new Date().getTime(), deletable: true }
					var fileDiv = methods.addFileElements(domObj, fileAttrs, options);

					// and start file upload
					methods.startUpload(file, fileAttrs, $(fileDiv), options);
				});
			}

  			return false;
		},
		
		startUpload: function(file, fileAttrs, domObj, options) {
			var status = "";

			// call onStart event handler
			options.onStart(fileAttrs);

			// initialze XML Http Request
			var xhr			= new XMLHttpRequest(),
				upload		= xhr.upload,
				progressBar	= $('.progress', domObj);

			// check for file extension?
			if (options.allowedExtensions.length>0) {
				var allowedExtensions = options.allowedExtensions.split(",");
				var fileName = file.name.split(".");
				var fileExtension = fileName[ fileName.length-1 ];

				// check if extension matched the whitelist
				if ($.inArray(fileExtension, allowedExtensions) < 0) {
					// no, deny upload
					methods.playError(options);

					if (options.onProgress(fileAttrs, domObj, 100)) {
						methods.onProgressHandler(domObj, fileAttrs, 100, options.labelInvalidFileExtension, '', options, true);

						// attach tooltip on status
						var tooltipText = options.fileExtensionNotAllowedText.replace('%s',fileExtension);
						tooltipText = tooltipText.replace('%s',options.allowedExtensions);
						$('div.percentage', domObj).tipTip({content: tooltipText, maxWidth: 600});

						// remember we failed
						fileAttrs.failed = true;
					}

					progressBar.addClass('failed');

					return false;
				}
			}

			// check for filesize?
			if (options.maxSize && (((file.fileSize) ? file.fileSize : file.size) > options.maxSize)) {
				// too big!
				methods.playError(options);

				if (options.onProgress(fileAttrs, domObj, 100)) {
					methods.onProgressHandler(domObj, fileAttrs, 100, options.labelFileTooLarge, '', options, true);

					// attach tooltip on status
					var tooltipText = options.fileTooLargeText.replace('%s',methods.bytesToSize(((file.fileSize) ? file.fileSize : file.size)));
						tooltipText = tooltipText.replace('%s',methods.bytesToSize(options.maxSize));
					$('div.percentage', domObj).tipTip({content: tooltipText, maxWidth: 600});

					// remember we failed
					fileAttrs.failed = true;
				}

				progressBar.addClass('failed');

				return false;
			}

			// attach listeners
			upload.addEventListener("progress", function(ev) {
				if (options.onProgress(fileAttrs, domObj, Math.ceil((ev.loaded / ev.total) * 100))) {
					methods.onProgressHandler(domObj, fileAttrs, Math.ceil((ev.loaded / ev.total) * 100), '', '', options);
				}
			}, false);

			// attach error listener
			upload.addEventListener("error", function (ev) {
				methods.playError(options);

				if (options.onProgress(fileAttrs, domObj, 100)) {
					methods.onProgressHandler(domObj, fileAttrs, 100, options.labelFailed, '', options, true);
				}

				progressBar.addClass('failed');
			}, false);
			
			// attach abort listener
			upload.addEventListener("abort", function (ev) {
				methods.playError(options);

				if (options.onProgress(fileAttrs, domObj, 100)) {
					methods.onProgressHandler(domObj, fileAttrs, 100, options.labelAborted, '', options, true);
				}

				progressBar.addClass('failed');

				// callback after abort
				options.onAbort(fileAttrs, domObj);
			}, false);

			// attach ready state listener
			xhr.onreadystatechange = function() {
				if (xhr.readyState != 4) { return; }

				var response = (xhr.responseText) ? JSON.parse(xhr.responseText) : {}

				// has the fileName changed in the back end?
				if (response.fileName && fileAttrs.fileName != response.fileName) {
					// yes, update it in the front end
					fileAttrs.fileName = response.fileName;
					methods.addFileTooltip($(".fileName",domObj).html(methods.shortenFileName(options.maxFileNameLength, response.fileName)), fileAttrs);
				}

				// check if everything went well
				if (xhr.status == 200) {
					if (options.onProgress(fileAttrs, domObj, 100)) {
						methods.onProgressHandler(domObj, fileAttrs, 100, '', '', options);
					}

					// show the spinner
					var spinner = $('.spinner', domObj);
					spinner.show('slow');

					// callback when done uploading
					options.onSuccess(fileAttrs, domObj, function() {
						// hide the spinner
						spinner.hide();

						// change percentage to 'done'
						methods.onProgressHandler(domObj, fileAttrs, 100, options.labelDone, '', options);
					});
				} else {
					methods.playError(options);

					// whoops, we've got an error!
					if (options.onProgress(fileAttrs, domObj, 100)) {
						methods.onProgressHandler(domObj, fileAttrs, 100, options.labelFailed, response.statusText, options);
					}

					progressBar.addClass('failed');

					// callback after failure
					options.onFailure(fileAttrs, domObj);

					return;
				}
			};
			
			// start data transfer
			xhr.open("POST",options.uri);
        	xhr.setRequestHeader("Cache-Control", "no-cache");
        	xhr.setRequestHeader("X-Requested-With", "Grails Uploadr");
        	xhr.setRequestHeader("X-File-Name", encodeURIComponent((file.name) ? file.name : file.fileName));
        	xhr.setRequestHeader("X-File-Size", (file.size) ? file.size : file.fileSize);
        	xhr.setRequestHeader("X-Uploadr-Name", encodeURIComponent(options.id));
			xhr.setRequestHeader("Content-Type", ((file.type) ? file.type : file.contentType) + '; charset=utf-8');
        	xhr.send(file);
		},

		onProgressHandler: function(domObj, fileAttrs, percentage, text, tooltipText, options, failed) {
			var progressMaxWidth	= domObj.parent().width();
			var progressBar			= $('.progress', domObj);
			var percentageDiv 		= $('.percentage', domObj);
			var speedDiv			= $('.speed', domObj);

			// calculate speed
			var time, seconds, data, speed, average, secondsLeft;
			if (fileAttrs.startTime && percentage < 100) {
				time	= new Date().getTime();
				seconds	= Math.ceil((time - fileAttrs.startTime) / 1000);
				data	= ((fileAttrs.fileSize / 100) * percentage) / seconds;

				// calculate average
				if (fileAttrs.avg) {
					average = Math.round((fileAttrs.avg + data) / 2);
					fileAttrs.avg = average;
				} else {
					fileAttrs.avg = data;
					average = data;
				}

				// time to go
				secondsLeft = Math.ceil((fileAttrs.fileSize / average) - seconds);

				// calculate average upload speed
				speed = methods.bytesToSize(average) + '/s (about '+methods.secondsToTime(secondsLeft)+' to go)';
			} else {
				speed = '';
			}

			// show upload speed
			speedDiv.html(speed);

			// handle progressbar width
			progressBar.width((progressMaxWidth / 100) * percentage);
			percentageDiv.html((text) ? text : percentage + '%');

			// add tooltip?
			if (text && tooltipText) {
				percentageDiv.tipTip({content: tooltipText, maxWidth: 600});
			}

			// are we done uploading?
			if (percentage >= 100) {
				// set progress to complete
				progressBar.addClass('complete');

				// unset speed array to save memory
				fileAttrs.speed = null;
			}
		},
		
		/**
		 * return human readable file sizes
		 * @param int bytes
		 * @returns string human readable filesize
		 */
		bytesToSize: function(bytes) {
			var sizes = ['B', 'KB', 'MB', 'GB', 'TB'];
			if (bytes == 0) return 'n/a';
			var i = parseInt(Math.floor(Math.log(bytes) / Math.log(1024)));
			return Math.round(bytes / Math.pow(1024, i), 2) + ' ' + sizes[i];
		},

		/**
		 * change seconds into seconds, minutes and hours
		 * @param seconds
		 */
		secondsToTime: function(seconds) {
			var sizes = [' seconds', 'minutes', 'hours'];
			if (seconds == 0) return 'n/a';
			var i = parseInt(Math.floor(Math.log(seconds) / Math.log(60)));
			return Math.round(seconds / Math.pow(60, i), 2) + ' ' + sizes[i];
		},

		/**
		 * return a shortened filename
		 * @param length
		 * @param fullFileName
		 */
		shortenFileName: function(length, fullFileName) {
			var fileName    = "",
				extension   = "",
				middle      = "",
				strip       = 0,
				dot         = fullFileName.lastIndexOf(".");

			if (fullFileName.length <= length) {
				return fullFileName;
			} else if (dot) {
				fileName	= fullFileName.substring(0,dot);
				extension	= fullFileName.substring(dot+1,fullFileName.length);
				strip       = (length - 4 - extension.length);

				// check if this is a filename which is made unique
				// (hence, ending with \-[0-9]+
				if (fileName.match(/\-\d+$/)) {
					dot         = fileName.lastIndexOf("-")
					middle      = fileName.substring(dot+1,fileName.length)
					fileName    = fileName.substring(0,dot);
					strip -= middle.length;
				}
			} else {
				fileName	= fullFileName;
			}

			return fileName.substring(0, strip) + ((middle) ? ('...' + middle + '.') : '....') + extension
		},

		addFileUploadField: function(j,domObj,options) {
			// insert upload field
			var fileUploadElement = document.createElement('input');
				fileUploadElement.setAttribute('type', 'file');
				fileUploadElement.multiple = true;
			var messageDiv = document.createElement('div');
				messageDiv.setAttribute('class', 'message');
				messageDiv.innerHTML = options.fileSelectText;
			var fileInputDiv = document.createElement('div');
				fileInputDiv.setAttribute('class','fileinput');
				fileInputDiv.appendChild(messageDiv);
				fileInputDiv.appendChild(fileUploadElement);

			domObj.appendChild(fileInputDiv);
			var inputField = $('input[type=file]',j);

			// bind image click to file input click
			$('.message', j).bind('click.uploadr', function() {
				// trigger click event on file input field
				inputField[0].click();
			});

			// bind file field event handler
			inputField.bind('change.uploadr', function() {
				// iterate through files
				if (typeof this.files !== "undefined") {
//					// hide the placeholder text
//					$('.placeholder', domObj).hide();

					// set work var
					options.workvars.gotFiles = true;

					// iterate through files
					$.each(this.files, function(index,file) {
						// add file DOM elements
						var fileAttrs = { fileName: (file.name) ? file.name : file.fileName, fileSize: (file.size) ? file.size : file.fileSize, startTime: new Date().getTime(), deletable: true }
						var fileDiv = methods.addFileElements(domObj, fileAttrs, options);

						// and start file upload
						methods.startUpload(file, fileAttrs, $(fileDiv), options);
					});
				}
			});
		}
	};

	// define the jquery plugin code
	$.fn.uploadr = function(options) {
		// default settings
		var defaults = {
			placeholderText		: 'drag and drop your files here to upload...',
			fileSelectText 		: 'Select files to upload',
			fileAbortText		: 'Click to abort file transfer',
			fileAbortConfirm	: 'Are you sure you would like to abort this transfer?',
			fileDeleteText		: 'Click to delete this file',
			fileDeleteConfirm	: 'Are you sure you want to delete this file?',
			fileDownloadText	: 'Click to download this file',
			fileViewText		: 'Click to view this file',
			fileTooLargeText	: 'The upload size of %s is larger than allowed maximum of %s',
			fileExtensionNotAllowedText : 'You tried to upload a file with extension "%s" while only files with extensions "%s" are allowed to be uploaded',
			likeText			: 'Click to like',
			unlikeText			: 'Click to unlike',
			colorPickerText		: 'Click to change background color',
			removeFromViewText	: 'Click to remove this aborted transfer from your view',
			labelDone			: 'done',
			labelFailed 		: 'failed',
			labelAborted 		: 'aborted',
			labelFileTooLarge	: 'too large',
			labelInvalidFileExtension : 'invalid',
			dropableClass		: 'uploadr-dropable',
			hoverClass			: 'uploadr-hover',
			uri					: '/upload/uri',
			id					: 'uploadr',
			maxFileNameLength	: 34,
			maxSize				: 0,	// 0 = unlimited
			maxVisible 			: 5,	// 0 = unlimited
			files				: [],
			uploadField 		: true,
			insertDirection 	: 'down',
			allowedExtensions   : "",   // comma ceperated list of allowed extensions, all if empty

			// default sound effects
			notificationSound   : '',
			errorSound          : '',
			deleteSound 		: '',

			// colorpickr colors
			colorPickerColors 	: [
				'#bce08a',	// default green
				'#00a8e1',	// blue
				'#ff6418',	// orange
				'#c78cda',	// purple
				'#ffcb00',	// yellow
				'#e70033'	// red
			],

			// workvariables, internal use only
			workvars 			: {
				gotFiles					: false,
				files						: [],
				notificationSoundEffect		: null,
				errorSoundEffect			: null,
				deleteSoundEffect 			: null,
				viewing						: 0,
				uploading					: 0,
				uploadrDiv 					: null
			},

			// default event handlers
			onStart 			: function(file) {},
			onProgress			: function(file, domObj, percentage) {
				// return false to cancel default progress handler
				return true;
			},
			onSuccess			: function(file, domObj, callback) { callback(); },
			onFailure			: function(file, domObj) { return true; },
			onAbort             : function(file, domObj) { return true; },
		};

		// extend the jQuery options
		var options = $.extend(defaults, options);

		return this.each(function() {
			var obj	= $(this);
			var e	= obj.get(0);

			// add file upload field
			if (options.uploadField) methods.addFileUploadField(obj,e,options);

			// add placeholder text
			var placeholderDiv = document.createElement('div');
				placeholderDiv.setAttribute('class', 'placeholder');
				placeholderDiv.innerHTML = defaults.placeholderText;

			// add files div
			var filesDiv = document.createElement('div');
				filesDiv.setAttribute('class', 'files '+defaults.dropableClass);
				filesDiv.appendChild(placeholderDiv);

			// append divs to uploadr element
			e.appendChild(filesDiv);

			// set workvars
			options.workvars.uploadrDiv = e;

			// register event handlers
			filesDiv.addEventListener('dragover', methods['dragOver'], false);
			filesDiv.addEventListener('dragenter', function(event) { methods['dragEnter'](event, $(this), e, defaults.hoverClass, options); }, false);
			filesDiv.addEventListener('dragleave', function(event) { methods['dragLeave'](event, $(this), e, defaults.hoverClass, options); }, false);
			filesDiv.addEventListener('drop', function(event) { methods['drop'](event, $(this), e, defaults.hoverClass, options); }, false);
		});
	};
})(jQuery);
