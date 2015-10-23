/**
 * Created by cbarne02 on 5/11/15.
 */

(function() {

    //container controller for the modal dialog
    angular.module("explorerControllers").controller("EditProjectsCtrl", ['$scope', '$routeParams', '$location', '$modal',
        'projectService', function ($scope, $routeParams, $location, $modal, apiService) {

            //entity object
        $scope.project = {};

            //path params
        $scope.params = $routeParams;
        //$scope.params.projectId;

            //populate the form
        apiService.find($scope.params.projectId).success(function(data){
            $scope.project = data;
            $scope.populateCourse();
        });



        $scope.deleteItem = function (item) {
            apiService.delete(item).success(function (data) {
                console.log(data);
            });
        };



        $scope.populateCourse = function () {
            apiService.getCourse($scope.project).success(function (response) {
                $scope.project.course = response;
            });
        };




            $scope.openPosterForm = function(){
                $scope.openResourceForm('poster', 'templates/addPoster.html', 'AddPosterCtrl');
            };

            $scope.openPaperForm = function(){
                $scope.openResourceForm('paper', 'templates/addPaper.html', 'AddPaperCtrl');
            };

            $scope.openResourceForm = function (resourceType, templateUrl, controller){
                if (typeof $scope.edit === "undefined"){
                    $scope.edit = {};
                }

                $scope.edit[resourceType] = {};

                if ($scope.project[resourceType] !== null){

                    //we're editing
                    angular.copy($scope.project[resourceType], $scope.edit[resourceType]);

                }


                if (typeof $scope.modalInstance === "undefined"){
                    $scope.modalInstance = {};
                }

                $scope.modalInstance[resourceType] = $modal.open({
                    templateUrl: templateUrl,
                    controller: controller,
                    scope: $scope
                });


                //return $scope.modalInstance[resourceType].result;
            };




        //buttons (per resource dialog)
        $scope.save = function () {
            //do some validation? test to see if edited course differs from current course?
            apiService.update($scope.project).success(function(){
                $location.url("/projects");
            });

        };

        $scope.reset = function () {
            $scope.project = {};
        };

        $scope.cancel = function () {
            $location.url("/projects");
        };

    }]);

})();