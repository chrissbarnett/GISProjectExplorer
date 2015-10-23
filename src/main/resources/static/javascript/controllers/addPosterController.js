/**
 * Created by cbarne02 on 5/11/15.
 */

(function() {

    angular.module("explorerControllers").controller("AddPosterCtrl", ['$scope', 'FileUploader', function ($scope, FileUploader) {
        //for now the FileUploader seems not to use angular's CSRF protection
        function getCookie(cname) {
            var name = cname + "=";
            var ca = document.cookie.split(';');
            for (var i = 0; i < ca.length; i++) {
                var c = ca[i];
                while (c.charAt(0) == ' ') c = c.substring(1);
                if (c.indexOf(name) == 0) return c.substring(name.length, c.length);
            }
            return "";
        }

        var csrf_token = getCookie("XSRF-TOKEN");

        //api/projects/{projectId}/resources/poster
        //api/projectResources/posters
        var uploader = $scope.uploader = new FileUploader({
            url: 'api/projectResources/posters',
            headers: {
                'X-XSRF-TOKEN': csrf_token
            }
        });



        // FILTERS

        uploader.filters.push(
            {
                name: 'pdfFilter',
                fn: function (item /*{File|FileLikeObject}*/, options) {
                    return item.type.toLowerCase() == "application/pdf";
                }
            });

        uploader.filters.push(
            {
                name: 'sizeFilter',
                fn: function (item /*{File|FileLikeObject}*/, options) {
                    return item.size / 1024 / 1024 < 50;
                }
            });

        // CALLBACKS


        uploader.onAfterAddingFile = function (fileItem) {
            //replace the current file with the new one.
            var q = fileItem.uploader.queue;
            if (q.length > 1) {
                q.shift();
            }
        };

        uploader.onBeforeUploadItem = function (item) {
            //item.formData.push({"winner": $scope.isWinner});
        };

        uploader.onSuccessItem = function (fileItem, response, status, headers) {
            console.log(response);
            $scope.project.poster.id = response.id;


        };
        /*
         uploader.onWhenAddingFileFailed = function(item , filter, options) {
         console.info('onWhenAddingFileFailed', item, filter, options);
         };

         uploader.onAfterAddingAll = function(addedFileItems) {
         console.info('onAfterAddingAll', addedFileItems);
         };

         uploader.onProgressItem = function(fileItem, progress) {
         console.info('onProgressItem', fileItem, progress);
         };
         uploader.onProgressAll = function(progress) {
         console.info('onProgressAll', progress);
         };

         uploader.onErrorItem = function(fileItem, response, status, headers) {
         console.info('onErrorItem', fileItem, response, status, headers);
         };
         uploader.onCancelItem = function(fileItem, response, status, headers) {
         console.info('onCancelItem', fileItem, response, status, headers);
         };
         uploader.onCompleteItem = function(fileItem, response, status, headers) {
         console.info('onCompleteItem', fileItem, response, status, headers);
         };
         uploader.onCompleteAll = function() {
         console.info('onCompleteAll');
         };

         console.info('uploader', uploader);*/

    }]);

})();
